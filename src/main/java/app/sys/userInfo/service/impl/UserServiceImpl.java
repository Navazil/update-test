package app.sys.userInfo.service.impl;

import app.sys.userInfo.dao.UserDao;
import app.sys.userInfo.model.UserEntity;
import app.sys.userInfo.service.UserService;
import app.sys.userInfo.structs.SimUserInfo;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import java.util.List;

/**
 * Created by weishicong on 2016/1/13.
 */
@Named(value= "userService")
@Component
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Override
    public boolean saveUserEntity(UserEntity userEntity) {
        if(checkUserExist(userEntity.getUsername())||userEntity.getPassword()==null||userEntity.getPassword().length()<6)
            return false;
        userDao.save(userEntity);
        return true;
    }


    @Override
    public boolean checkUserExist(String userName) {
        String sql="from UserEntity o " + "where o.username = :userName";
        List list = userDao.createQuery(sql).setString("userName", userName).list();
        return list.size()>0;
    }

    @Override
    public boolean checkUserExist(int userId) {
        String sql="from UserEntity o where o.userId = :userId";
        List list = userDao.createQuery(sql).setInteger("userId", userId).list();
        return list.size()>0;
    }

    @Override
    public UserEntity loginCheck(String userName, String password) {
        String sql="from UserEntity o where o.username = :userName and o.password=:password";
        List list=userDao.createQuery(sql).setString("userName",userName).setString("password",password).list();
        if(list.size()>0)
            return (UserEntity)list.get(0);
        else
            return null;

    }

    @Override
    public boolean CheckOnlineStatus(int userId, String sessionId) {
        String sql="from UserEntity o where o.userId = :userId and sessionId = :sessionId"; //当用户Id和sessionId匹配的时候返回的搜索结果list的size大于0
        List list=userDao.createQuery(sql).setInteger("userId",userId).setString("sessionId",sessionId).list();
        return list.size()>0;
    }


    @Override
    public UserEntity upDateUserInfo(UserEntity userEntity) {
        return userDao.update(userEntity);
    }

    @Override
    public boolean checkPassword(int userId, String password) {
        String sql="from UserEntity o where o.userId = :userId and password = :password";
        List list=userDao.createQuery(sql).setInteger("userId",userId).setString("password",password).list();
        return list.size()>0;
    }

    @Override
    public UserEntity queryUserByName(String userName) {
        String sql="from UserEntity o where o.username = :userName";
        List list=userDao.createQuery(sql).setString("userName",userName).list();
        if(list.size()==0)
            return null;
        return (UserEntity)list.get(0);
    }

    @Override
    public UserEntity queryUserById(int userId) {
        String sql="from UserEntity o where o.userId = :userId";
        List list=userDao.createQuery(sql).setInteger("userId", userId).list();
        if(list.size()==0)
            return null;
        return (UserEntity)list.get(0);
    }

    @Override
    public String getNickNameById(int userId) {
        String sql="from UserEntity o where o.userId = :userId";
        List list=userDao.createQuery(sql).setInteger("userId", userId).list();
        if(list.size()==0)
            return null;
        return ((UserEntity)list.get(0)).getNickname();
    }

    @Override
    public SimUserInfo querySimpleInfoById(int userId) {
        SimUserInfo userInfo=new SimUserInfo();
        Session session= userDao.getCurrentSession();
        List list=session.createQuery("select from user where user_id="+userId).list();
        if(list.size()==0)
            return null;
        UserEntity entity=(UserEntity)list.get(0);
        userInfo.setUserId(entity.getUserId());
        userInfo.setUserName(entity.getUsername());
        userInfo.setNickName(entity.getNickname());
        userInfo.setImagePath(entity.getAvatar());
        userInfo.setPhoneNum(entity.getTel());
        userInfo.setGender(entity.getGender());
        userInfo.setSignature(entity.getSignature());
        return userInfo;
    }

    @Override
    public int getIdByName(String userName) {
        String sql="from UserEntity o where o.username = :userName";
        List list=userDao.createQuery(sql).setString("userName", userName).list();
        if(list.size()==0)
            return 0;
        return ((UserEntity)list.get(0)).getUserId();
    }
}
