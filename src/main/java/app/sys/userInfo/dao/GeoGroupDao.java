package app.sys.userInfo.dao;

import app.common.dao.GenericEntityDao;
import app.sys.userInfo.model.GeoGroupEntity;

import javax.inject.Named;

/**
 * Created by weishicong on 2016/1/15.
 */
@Named("geoGroupDao")
public class GeoGroupDao extends GenericEntityDao<GeoGroupEntity,Integer> {
    @Override
    protected Class<GeoGroupEntity> entityClass() {
        return GeoGroupEntity.class;
    }
}
