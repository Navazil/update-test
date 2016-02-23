package app.sys.userInfo.service;

import app.sys.userInfo.model.UserEntity;
import app.sys.userInfo.structs.SimUserInfo;

/**
 * Created by weishicong on 2016/1/13.
 */
public interface UserService {
    boolean saveUserEntity(UserEntity userEntity);
    UserEntity upDateUserInfo(UserEntity userEntity);
    /**
     * 判断用户名是否已经存在
     * @param userId 用户Id
     * @param password 密码
     * @return 当用户已存在时返回true，否则返回false
     * **********/
    boolean checkPassword(int userId, String password);

    /**
     * 判断用户名是否已经存在
     * @param userName 用户名
     * @return 当用户已存在时返回true，否则返回false
     * **********/
    boolean checkUserExist(String userName);

    /**
     * 判断用户是否存在
     * @param userId 用户名
     * @return 当用户存在时返回true，否则返回false
     * **********/
    boolean checkUserExist(int userId);

    /**
     * 检验用户的登陆信息
     * @param userName  用户名
     * @param password  密码
     * @return 当登陆验证通过时返回用户信息，否则返回null
     * **********/
    UserEntity loginCheck(String userName, String password);

    /**
     * 通过传过来的SessionID判断用户的登陆情况
     * @param userId  用户Id
     * @param sessionID 登录时的存根信息
     * @return 当登陆验证通过时返回true，否则返回false
     * **********/
    boolean CheckOnlineStatus(int userId, String sessionID);

    /**
     * 通过用户名查询用户信息
     * @param userName  用户名
     * @return 用户信息(userEntity)
     * **********/
    UserEntity queryUserByName(String userName);

    /**
     * 通过用户Id查询用户信息
     * @param userId  用户Id
     * @return 用户信息(userEntity)
     * **********/
    UserEntity queryUserById(int userId);

    /**
     * 通过用户Id查询用户昵称
     * @param userId  用户Id
     * @return 用户昵称
     * **********/
    String getNickNameById(int userId);

    /**
     * 通过用户Id查询用户基本信息
     * @param userId  用户Id
     * @return 用户基本信息(不包括密码)
     * **********/
    SimUserInfo querySimpleInfoById(int userId);

    int getIdByName(String userName);
}
