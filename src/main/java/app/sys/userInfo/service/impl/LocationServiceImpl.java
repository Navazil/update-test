package app.sys.userInfo.service.impl;

import app.sys.userInfo.dao.CurrLocationDao;
import app.sys.userInfo.dao.HisLocationDao;
import app.sys.userInfo.model.CurrentRecordEntity;
import app.sys.userInfo.model.HisLocationEntity;
import app.sys.userInfo.service.LocationService;
import app.sys.userInfo.structs.LocationRecord;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by weishicong on 2016/1/16.
 */
@Named(value= "LocationService")
@Component
@Transactional
public class LocationServiceImpl implements LocationService {
    @Autowired
    HisLocationDao hisLocationDao;
    @Autowired
    CurrLocationDao currLocationDao;
    @Override
    public boolean saveHisLocationEntity(HisLocationEntity hisLocationEntity) {
        hisLocationDao.save(hisLocationEntity);
        return true;
    }

    @Override
    public boolean saveCurrLocationEntity(CurrentRecordEntity currentRecordEntity) {
        currLocationDao.save(currentRecordEntity);
        return true;
    }

    @Override
    public boolean saveLocationRecords(List<LocationRecord> data, int userId) {
        List<HisLocationEntity> list=new ArrayList<HisLocationEntity>();
        for(LocationRecord record : data)
        {
            HisLocationEntity newRecord=new HisLocationEntity();
            newRecord.setUserId(userId);
            newRecord.setLatitude(record.getLatitude());
            newRecord.setLongitude(record.getLongitude());
            newRecord.setTime(record.getTime());
            newRecord.setIsShared(record.getShared());
            list.add(newRecord);
        }
        hisLocationDao.saveOrUpdate(list);
        return true;
    }

    @Override
    public boolean updateLocationRecord(LocationRecord data, int userId) {
        HisLocationEntity newRecord=new HisLocationEntity();
        newRecord.setIsShared(data.getShared());
        newRecord.setUserId(userId);
        newRecord.setLongitude(data.getLongitude());
        newRecord.setLatitude(data.getLatitude());
        newRecord.setTime(data.getTime());
        hisLocationDao.save(newRecord);
        return true;
    }

    @Override
    public boolean updateCurrRecord(LocationRecord data, int userId) {
        Session session=currLocationDao.getCurrentSession();
        int currId=getRecordIdByUserId(userId);
        if(currId!=0)
        {
            CurrentRecordEntity entity = getCurrRecordByUserId(userId);
            if(entity==null)
                return false;
            entity.setIsShared(data.getShared());
            entity.setLongitude(data.getLongitude());
            entity.setLatitude(data.getLatitude());
            entity.setTime(data.getTime());
            currLocationDao.update(entity);
        }
        else
        {
            CurrentRecordEntity entity=new CurrentRecordEntity();
            entity.setUserId(userId);
            entity.setIsShared(data.getShared());
            entity.setLongitude(data.getLongitude());
            entity.setLatitude(data.getLatitude());
            entity.setTime(data.getTime());
            currLocationDao.save(entity);
        }
        return true;
    }

    @Override
    public List<HisLocationEntity> getRecordsBetween(int userId, long beginTime, long endTime) {
        String sql="from HisLocationEntity o where o.userId = :userId and o.time between :beginTime and :endTime";
        List list=null;
        try {
            list = hisLocationDao.createQuery(sql).setInteger("userId", userId).setLong("beginTime", beginTime).setLong("endTime", endTime).list();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }

    private int getRecordIdByUserId(int userId)
    {
        String sql="from CurrentRecordEntity o where o.userId = :userId";
        List list=currLocationDao.createQuery(sql).setInteger("userId",userId).list();
        if(list.size()==0)
            return 0;
        return ((CurrentRecordEntity)list.get(0)).getCurrId();
    }

    private CurrentRecordEntity getCurrRecordByUserId(int userId)
    {
        String sql="from CurrentRecordEntity o where o.userId= :userId";
        List list=currLocationDao.createQuery(sql).setInteger("userId",userId).list();
        if(list.size()>0)
        {
            return (CurrentRecordEntity)list.get(0);
        }
        return null;
    }
}
