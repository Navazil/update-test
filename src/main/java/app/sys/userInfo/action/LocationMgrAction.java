package app.sys.userInfo.action;

import app.common.action.GenericActionSupport;
import app.sys.userInfo.model.HisLocationEntity;
import app.sys.userInfo.service.LocationService;
import app.sys.userInfo.service.UserService;
import app.sys.userInfo.structs.LocationRecord;
import app.sys.userInfo.structs.SimLocationRecord;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by weishicong on 2016/1/16.
 */
public class LocationMgrAction extends GenericActionSupport {
    int userId;
    String location_record;
    private String sessionId;
    private String userName;
    private long beginTime;
    private long endTime;
    private int targetUserId;
    @Autowired
    LocationService locationService;
    @Autowired
    UserService userService;
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLocation_record() {
        return location_record;
    }

    public void setLocation_record(String location_record) {
        this.location_record = location_record;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(long beginTime) {
        this.beginTime = beginTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public int getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(int targetUserId) {
        this.targetUserId = targetUserId;
    }
    @Override
    public String execute()
    {
        return SUCCESS;
    }

    public String saveLocations()
    {
        if(location_record.length()==0)
        {
            super.writeJson(generateReturnData("status",100));
        }
        try {
            JSONObject object=JSON.parseObject(location_record);
            String recordString=object.get("location_record").toString();
            List<LocationRecord> locations = JSON.parseArray(recordString, LocationRecord.class);
            LocationRecord latest=locations.get(0);
            for(LocationRecord record:locations) {
                if(record.getTime()>latest.getTime())
                    latest=record;
                locationService.updateLocationRecord(record,userId);
            }
            locationService.updateCurrRecord(latest,userId);
            super.writeJson(generateReturnData("status", 200));
        }
        catch(Exception e)
        {
            e.printStackTrace();
            super.writeJson(generateReturnData("status", 100));
            return ERROR;
        }
        return SUCCESS;
    }

    public String getRecordsBetween()
    {
        if(targetUserId!=userId&&(userService.queryUserById(targetUserId).getEnableShare()==0))
        {
            super.writeJson(generateReturnData("status&targetUserId",401,targetUserId));
            return ERROR;
        }
        if(beginTime<=0||endTime<=0||beginTime>endTime) {
            super.writeJson(generateReturnData("status&targetUserId",100,targetUserId));
            return ERROR;
        }
        List records=locationService.getRecordsBetween(targetUserId,beginTime,endTime);
        String nickName=userService.getNickNameById(targetUserId);
        super.writeJson(generateReturnData("status&nickName&targetUserId&records",200,nickName,targetUserId,convertToSimRecord(records)));
        return SUCCESS;
    }

    private Map<String,Object> generateReturnData(String keys,Object...args)
    {
        Map<String,Object> data=new HashMap<String,Object>();
        String[] keyArray=keys.split("&");
        for(int i=0,j=0;(i<keyArray.length&&j<args.length);i++,j++)
        {
            data.put(keyArray[i],args[j]);
        }
        return data;
    }

    private List<SimLocationRecord> convertToSimRecord(List<HisLocationEntity> entities)
    {
        List<SimLocationRecord> simLocationRecords=new ArrayList<SimLocationRecord>();
        for(HisLocationEntity entity:entities)
        {
            SimLocationRecord record=new SimLocationRecord();
            record.setLongitude(entity.getLongitude());
            record.setLatitude(entity.getLatitude());
            record.setTime(entity.getTime());
            simLocationRecords.add(record);
        }

        return simLocationRecords;
    }
}
