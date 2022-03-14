package com.daffo.DBBenchmarks.database;

import java.sql.SQLException;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.EnumUtils;

import com.daffo.DBBenchmarks.constants.Constants;
import com.daffo.DBBenchmarks.constants.DBTypes;
import com.daffo.DBBenchmarks.helpers.PropertiesManager;

/**
 * Factory to get the correct DBManager implementation following the configuration
 * @author daffo
 *
 */
public class DBManagerFactory {

	private static ReentrantLock lock = new ReentrantLock();
	private static DBManager manager;

	public static DBManager getDBManagerInstance() throws SQLException {
		if (manager == null) {
			try {
				lock.lock();
				if (manager == null) {
					String dbType = PropertiesManager.getInstance().getProperty(Constants.DB_TYPE_PROPERTY);
					if (!EnumUtils.isValidEnum(DBTypes.class, dbType)) {
						throw new RuntimeException(
								String.format("dbtype [%s] not valid: please check configuration", dbType));
					} else {
						manager = DBTypes.valueOf(dbType).getImplementation();
						((AbstractDBManager) manager).initConn();
					}
				}
			} finally {
				lock.unlock();
			}
		}
		return manager;
	}
}
