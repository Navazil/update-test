package app.sys.userInfo.dao;

import app.common.dao.GenericEntityDao;
import app.sys.userInfo.model.HisLocationEntity;

import javax.inject.Named;

/**
 * Created by weishicong on 2016/1/16.
 */
@Named("HisLocationDao")
public class HisLocationDao extends GenericEntityDao<HisLocationEntity,Integer> {
    @Override
    protected Class<HisLocationEntity> entityClass() {
        return HisLocationEntity.class;
    }
}
