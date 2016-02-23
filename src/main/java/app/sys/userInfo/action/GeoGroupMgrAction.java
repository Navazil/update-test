package app.sys.userInfo.action;

import app.common.action.GenericActionSupport;
import app.sys.userInfo.model.GeoGroupEntity;
import app.sys.userInfo.model.GroupRelEntity;
import app.sys.userInfo.model.UserEntity;
import app.sys.userInfo.service.GeoGroupService;
import app.sys.userInfo.service.UserService;
import app.sys.userInfo.structs.GroupInfo;
import app.sys.userInfo.structs.GroupMember;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by weishicong on 2016/1/15.
 */
public class GeoGroupMgrAction extends GenericActionSupport {

    private int groupId;
    private String groupName;
    private byte admin;
    private String profile;
    private String sessionId;
    private String userName;
    private int userId;
    private int memberUserId;
    @Autowired
    GeoGroupService geoGroupService;
    @Autowired
    UserService userService;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public byte getAdmin() {
        return admin;
    }

    public void setAdmin(byte admin) {
        this.admin = admin;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMemberUserId() {
        return memberUserId;
    }

    public void setMemberUserId(int memberUserId) {
        this.memberUserId = memberUserId;
    }
    @Override
    public String execute()
    {
        return SUCCESS;
    }

    public String createGeoGroup()
    {
        GeoGroupEntity groupInfo=new GeoGroupEntity();
        groupInfo.setName(groupName);
        groupInfo.setProfile(profile);
        groupInfo.setFounder(userId);
        if(!geoGroupService.saveGeoGroupEntity(groupInfo))
        {
            super.writeJson(generateReturnData("status", 100));
            return ERROR;
        }
        GroupRelEntity groupRelEntity=new GroupRelEntity();
        groupRelEntity.setUserId(userId);
        int groupId=geoGroupService.getGroupIdByName(groupName);
        groupRelEntity.setGroupId(groupId);
        if(!geoGroupService.saveGroupRelEntity(groupRelEntity))
        {
            super.writeJson(generateReturnData("status", 100));
            return ERROR;
        }
        super.writeJson(generateReturnData("status&groupId&groupName",200,groupId,groupName));
        return SUCCESS;
    }
    public String addGroupMember()
    {
        try {
            if (!geoGroupService.checkIsFounder(groupId, userId)) {
                super.writeJson(generateReturnData("status", 302));
                return ERROR;
            }
            else if (!userService.checkUserExist(memberUserId)) {
                super.writeJson(generateReturnData("status", 303));
                return ERROR;
            }
            else if (geoGroupService.checkMemberExisted(groupId, getMemberUserId())) {
                super.writeJson(generateReturnData("status", 304));
                return ERROR;
            }
            GroupRelEntity groupRelEntity = new GroupRelEntity();
            groupRelEntity.setUserId(memberUserId);
            groupRelEntity.setGroupId(groupId);
            if (geoGroupService.saveGroupRelEntity(groupRelEntity)) {
                super.writeJson(generateReturnData("status", 200));
                return SUCCESS;
            }
        }catch(Exception e)
        {
            super.writeJson(generateReturnData("status",100));
            return ERROR;
        }
        super.writeJson(generateReturnData("status",100));
        return ERROR;
    }
    public String queryGroupInfo()
    {
        GeoGroupEntity groupEntity=geoGroupService.getGroupInfoById(groupId);
        List<GroupRelEntity> groupRel=geoGroupService.getGroupRel(groupId);
        if(groupEntity==null)
        {
            super.writeJson(generateReturnData("status",100));
            return ERROR;
        }
        GroupInfo groupInfo=convertToGroupInfo(groupEntity, groupRel);
        super.writeJson(generateReturnData("status&group",200,groupInfo));
        return SUCCESS;
    }
    public String removeGroupMember()
    {
        try {
            if(!geoGroupService.checkIsFounder(groupId,userId))
            {
                super.writeJson(generateReturnData("status",302));
                return ERROR;
            }
            GroupRelEntity entity = geoGroupService.getGroupMemberById(memberUserId);
            geoGroupService.removeGroupMember(entity);
        }
        catch(Exception e)
        {
            super.writeJson(generateReturnData("status",100));
            return ERROR;
        }
        super.writeJson(generateReturnData("status",200));
        return SUCCESS;
    }

    public String queryUserGroupInfo()
    {
        try {
            List<GroupInfo> groupInfos = new ArrayList<GroupInfo>();
            List<GeoGroupEntity> groupInfo = geoGroupService.getUserGroups(userId);
            for (GeoGroupEntity info : groupInfo) {
                List<GroupRelEntity> groupRel = geoGroupService.getGroupRel(info.getGroupId());
                GroupInfo record = convertToGroupInfo(info, groupRel);
                groupInfos.add(record);
            }
            super.writeJson(generateReturnData("status&groups",200,groupInfos));
            return SUCCESS;
        }catch(Exception e)
        {
            super.writeJson(generateReturnData("status",100));
            return ERROR;
        }

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

    private GroupInfo convertToGroupInfo(GeoGroupEntity groupEntities,List<GroupRelEntity> groupRelEntities)
    {
        GroupInfo groupInfo=new GroupInfo();
        List<GroupMember> memberList=new ArrayList<GroupMember>();
        groupInfo.setGroupName(groupEntities.getName());
        groupInfo.setProfile(groupEntities.getProfile());
        groupInfo.setFounder(groupEntities.getFounder());
        groupInfo.setGroupId(groupEntities.getGroupId());
        for(GroupRelEntity entity:groupRelEntities)
        {
            GroupMember member=new GroupMember();
            UserEntity user=userService.queryUserById(entity.getUserId());
            member.setUserId(user.getUserId());
            member.setNickName(user.getNickname());
            member.setAvatar(user.getAvatar());
            member.setSignature(user.getSignature());
            member.setIs_shared(user.getEnableShare());
            member.setGender(user.getGender());
            memberList.add(member);
        }
        groupInfo.setGroupMemberList(memberList);
        return groupInfo;
    }

}
