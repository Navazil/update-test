package app.sys.userInfo.dao;

import app.common.dao.GenericEntityDao;
import app.sys.userInfo.model.UserEntity;

import javax.inject.Named;

/**
 * Created by weishicong on 2016/1/13.
 */
@Named("UserDao")
public class UserDao extends GenericEntityDao<UserEntity,Integer> {

    @Override
    protected Class<UserEntity> entityClass() {
        return UserEntity.class;
    }
}
