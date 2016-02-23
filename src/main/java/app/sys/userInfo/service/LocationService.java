package app.sys.userInfo.service;

import app.sys.userInfo.model.CurrentRecordEntity;
import app.sys.userInfo.model.HisLocationEntity;
import app.sys.userInfo.structs.LocationRecord;

import java.util.List;

/**
 * Created by weishicong on 2016/1/16.
 */
public interface LocationService {
    boolean saveHisLocationEntity(HisLocationEntity hisLocationEntity);
    boolean saveCurrLocationEntity(CurrentRecordEntity currentRecordEntity);
    boolean saveLocationRecords(List<LocationRecord> data, int userId);
    boolean updateLocationRecord(LocationRecord data, int userId);
    boolean updateCurrRecord(LocationRecord data, int userId);
    List<HisLocationEntity> getRecordsBetween(int userId, long beginTime, long endTime);
}
