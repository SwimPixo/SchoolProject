package eu.schoolproject.database.dao;

import com.j256.ormlite.dao.Dao;

import eu.schoolproject.api.model.IpInfo;
import eu.schoolproject.database.DatabaseHelper;

/**
 * Created by BP on 29/12/2017.
 */

public class IpInfoDao extends BaseDao<IpInfo, Long> {

    public IpInfoDao(Dao<IpInfo, Long> dao, DatabaseHelper dbHelper) {
        super(dao, dbHelper);
    }
}
