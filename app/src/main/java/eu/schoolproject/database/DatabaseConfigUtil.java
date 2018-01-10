package eu.schoolproject.database;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.IOException;
import java.sql.SQLException;

import eu.schoolproject.api.model.IpInfo;

/**
 * Created by BP on 04/04/2017.
 */

public class DatabaseConfigUtil extends OrmLiteConfigUtil {

    private static final Class<?>[] g_classes = new Class[]{
            IpInfo.class
    };

    public static void main(String[] args) throws SQLException, IOException {
        writeConfigFile("ormlite_config.txt", g_classes);
    }

}
