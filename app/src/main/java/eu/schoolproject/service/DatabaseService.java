package eu.schoolproject.service;

import android.content.Context;
import android.support.annotation.NonNull;

import eu.schoolproject.database.DatabaseHelper;
import eu.schoolproject.database.dao.IpInfoDao;
import eu.schoolproject.util.sl.IService;

/**
 * Created by BP on 29/12/2017.
 */

public class DatabaseService implements IService {

    private @NonNull final DatabaseHelper mDatabaseHelper;

    public DatabaseService(final @NonNull Context context) {
        mDatabaseHelper = new DatabaseHelper(context);
    }

    public void close() {
        mDatabaseHelper.close();
    }

    public IpInfoDao getIpInfoDao() {
        return mDatabaseHelper.getIpInfoDao();
    }
}

