package app.sys.userInfo.service;

import app.sys.userInfo.model.GeoGroupEntity;
import app.sys.userInfo.model.GroupRelEntity;

import java.util.List;

/**
 * Created by weishicong on 2016/1/15.
 */
public interface GeoGroupService {
    boolean saveGeoGroupEntity(GeoGroupEntity geoGroupEntity);
    boolean saveGroupRelEntity(GroupRelEntity groupRelEntity);
    /**
     *检查组名是否已经被使用
     *@param groupName 组名
     *@return 当组名已被使用返回true，未使用返回false
     * *************/
    boolean checkGroupExisted(String groupName);

    /**
     *检查用户是否已经在组内
     *@param groupId 组Id
     *@param userId  需要检查的用户Id
     *@return 用户已存在则返回true，否则返回false
     **************/
    boolean checkMemberExisted(int groupId, int userId);

    /**
     *检查用户是否为该组的创建者，用以判断是否有权限进行增删组员操作
     *@param groupId 组Id
     *@param userId  需要检查的用户Id
     *@return 用户是创建者返回true，否则返回false
     **************/
    boolean checkIsFounder(int groupId, int userId);

    /**
     *删除指定的组员(不用于在删除组时进行成员删除)
     *@param entity 待删除的用户实体，是经由传入的memberUserId查询后得到的实体
     *@return 操作成功返回true，否则返回false
     *@throws DeleteFounderException 当用户非创建者而试图删除组的创建者时抛出
     **************/
    boolean removeGroupMember(GroupRelEntity entity) throws DeleteFounderException;

    /**
     *获取组成员列表
     *@param groupId 组Id
     *@return List<GroupRelEntity> 类型的List，GroupRelEntity包含组成员的Id
     **************/
    List<GroupRelEntity> getGroupRel(int groupId);

    List<GeoGroupEntity> getUserGroups(int userId);

    GeoGroupEntity getGroupInfoById(int groupId);
    GroupRelEntity getGroupMemberById(int userId);

    /**
     *根据组名获取组Id
     *@param groupName 组名
     *@return 组Id
     **************/
    int getGroupIdByName(String groupName);

    class DeleteFounderException extends Exception
    {
        public DeleteFounderException()
        {
            super("The member to delete should not be the founder of the group.");
        }
    }
}
