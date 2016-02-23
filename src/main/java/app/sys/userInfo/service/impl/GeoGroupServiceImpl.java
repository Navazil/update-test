package app.sys.userInfo.service.impl;

import app.sys.userInfo.dao.GeoGroupDao;
import app.sys.userInfo.dao.GroupRelDao;
import app.sys.userInfo.dao.UserDao;
import app.sys.userInfo.model.GeoGroupEntity;
import app.sys.userInfo.model.GroupRelEntity;
import app.sys.userInfo.service.GeoGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by weishicong on 2016/1/15.
 */
@Named(value="geoGroupService")
@Component
@Transactional
public class GeoGroupServiceImpl implements GeoGroupService {
    @Autowired
    GeoGroupDao geoGroupDao;
    @Autowired
    GroupRelDao groupRelDao;
    @Autowired
    UserDao userDao;
    @Override
    public boolean saveGeoGroupEntity(GeoGroupEntity geoGroupEntity) {
        geoGroupDao.save(geoGroupEntity);
        return true;
    }

    @Override
    public boolean saveGroupRelEntity(GroupRelEntity groupRelEntity) {
        groupRelDao.save(groupRelEntity);
        return true;
    }

    @Override
    public boolean checkGroupExisted(String groupName) {
        String sql="from GeoGroupEntity o where o.name=:groupName";
        List list=geoGroupDao.createQuery(sql).setString("groupName", groupName).list();
        return list.size()>0;
    }

    @Override
    public boolean checkMemberExisted(int groupId, int userId) {
        String sql="from GroupRelEntity o where o.userId=:userId and groupId=:groupId";
        List list=groupRelDao.createQuery(sql).setInteger("userId", userId).setInteger("groupId", groupId).list();
        return list.size()>0;
    }

    @Override
    public boolean removeGroupMember(GroupRelEntity entity) throws DeleteFounderException {
        if(checkIsFounder(entity.getGroupId(),entity.getUserId()))
        {
            throw new DeleteFounderException();
        }
        groupRelDao.delete(entity);
        return true;
    }


    @Override
    public List<GroupRelEntity> getGroupRel(int groupId) {
        String sql="from GroupRelEntity o where o.groupId=:groupId";
        List list=groupRelDao.createQuery(sql).setInteger("groupId", groupId).list();
        return list;
    }

    @Override
    public List<GeoGroupEntity> getUserGroups(int userId) {
        List<GeoGroupEntity> list=new ArrayList<GeoGroupEntity>();
        String sql="from GroupRelEntity o where o.userId=:userId";
        List<GroupRelEntity> groupRel=groupRelDao.createQuery(sql).setInteger("userId",userId).list();
        for(GroupRelEntity entity:groupRel)
        {
            GeoGroupEntity newEntity=getGroupInfoById(entity.getGroupId());
            list.add(newEntity);
        }
        return list;
    }

    @Override
    public GeoGroupEntity getGroupInfoById(int groupId) {
        return geoGroupDao.get(groupId);
    }

    @Override
    public GroupRelEntity getGroupMemberById(int userId) {
        String sql="from GroupRelEntity o where o.userId=:userId";
        List list=groupRelDao.createQuery(sql).setInteger("userId", userId).list();
        if(list.size()>0)
        {
            return (GroupRelEntity)list.get(0);
        }
        return null;
    }

    @Override
    public int getGroupIdByName(String groupName) {
        String sql="from GeoGroupEntity o where o.name=:groupName";
        List list=geoGroupDao.createQuery(sql).setString("groupName", groupName).list();
        if(list.size()==0)
            return -1;
        return ((GeoGroupEntity)list.get(0)).getGroupId();
    }

    @Override
    public boolean checkIsFounder(int groupId,int userId)
    {
        String sql="from GeoGroupEntity o where o.groupId=:groupId and o.founder=:userId";
        List list=geoGroupDao.createQuery(sql).setInteger("groupId",groupId).setInteger("userId",userId).list();
        return list.size()>0;
    }


}
