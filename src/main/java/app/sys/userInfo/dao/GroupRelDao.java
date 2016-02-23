package app.sys.userInfo.dao;

import app.common.dao.GenericEntityDao;
import app.sys.userInfo.model.GroupRelEntity;

import javax.inject.Named;

/**
 * Created by weishicong on 2016/1/19.
 */
@Named("GroupRelDao")
public class GroupRelDao extends GenericEntityDao<GroupRelEntity,Integer> {
    @Override
    protected Class<GroupRelEntity> entityClass() {
        return GroupRelEntity.class;
    }
}
