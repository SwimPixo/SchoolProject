package eu.schoolproject.database.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.List;

import eu.schoolproject.database.DatabaseHelper;

/**
 * Created by BP on 29/12/2017.
 */

public class BaseDao<T, ID> extends RuntimeExceptionDao<T, ID> {

    protected DatabaseHelper aHelper;

    public BaseDao(Dao<T, ID> dao, DatabaseHelper dbHelper) {
        super(dao);
        aHelper = dbHelper;
    }

    public int deleteAll() {
        try {
            return delete(deleteBuilder().prepare());
        } catch (Exception e) {
            return 0;
        }
    }

    public void createAll(List<T> list) {
        for (T t : list) {
            create(t);
        }
    }

    public void updateAll(List<T> list) {
        for (T t : list) {
            update(t);
        }
    }

}
