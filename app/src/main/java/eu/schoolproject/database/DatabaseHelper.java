package eu.schoolproject.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import eu.schoolproject.R;
import eu.schoolproject.api.model.IpInfo;
import eu.schoolproject.database.dao.IpInfoDao;
import timber.log.Timber;

/**
 * Created by BP on 04/04/2017.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    // the DAO object we use to access the SimpleData table
    private IpInfoDao mIpInfoDao = null;

    public DatabaseHelper(Context context) {
        super(context, "ipInfoDatabase", null,2017122901, R.raw.ormlite_config);
    }

    /**
     * This is called when the database is first created. Usually you should call createTable statements here to create
     * the tables that will store your data.
     */
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Timber.d(DatabaseHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, IpInfo.class);
        } catch (SQLException e) {
            Timber.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
     * the various data to match the new version number.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Timber.i(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, IpInfo.class, true);

            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Timber.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the Database Access Object (DAO) for our ProductDetail class. It will create it or just give the cached
     * value.
     */
    public IpInfoDao getIpInfoDao() {
        if (mIpInfoDao == null) {
            try {
                final Dao<IpInfo, Long> dao = getDao(IpInfo.class);
                mIpInfoDao = new IpInfoDao(dao, this);
            } catch (SQLException e) {
                Timber.e(DatabaseHelper.class.getName(), "Can't obtain ProductDetailDao", e);
            }
        }
        return mIpInfoDao;
    }

    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
        mIpInfoDao = null;
    }
}
