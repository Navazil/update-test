package app.sys.userInfo.dao;

import app.common.dao.GenericEntityDao;
import app.sys.userInfo.model.CurrentRecordEntity;

import javax.inject.Named;

/**
 * Created by weishicong on 2016/1/20.
 */
@Named("CurrLocationDao")
public class CurrLocationDao extends GenericEntityDao<CurrentRecordEntity,Integer> {
    @Override
    protected Class<CurrentRecordEntity> entityClass() {
        return CurrentRecordEntity.class;
    }
}
