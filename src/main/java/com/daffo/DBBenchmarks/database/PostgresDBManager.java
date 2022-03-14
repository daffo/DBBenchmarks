package com.daffo.DBBenchmarks.database;

import static com.daffo.DBBenchmarks.constants.Constants.DB_SERVER_NAME_PROPERTY;
import static com.daffo.DBBenchmarks.constants.Constants.DB_SERVER_PASSWORD_PROPERTY;
import static com.daffo.DBBenchmarks.constants.Constants.DB_SERVER_PORT_PROPERTY;
import static com.daffo.DBBenchmarks.constants.Constants.DB_SERVER_URL_PROPERTY;
import static com.daffo.DBBenchmarks.constants.Constants.DB_SERVER_USERNAME_PROPERTY;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.daffo.DBBenchmarks.constants.DBTypes;
import com.daffo.DBBenchmarks.constants.Queries;
import com.daffo.DBBenchmarks.helpers.PropertiesManager;
import com.daffo.DBBenchmarks.helpers.SystemExceptionHandler;

/**
 * {@link DBTypes#postgres} specific implementation
 *
 * @author daffo
 *
 */
public class PostgresDBManager extends AbstractDBManager {

	public void setupTable() {
		try (Statement s = conn.createStatement()) {
			s.executeUpdate(Queries.DROP_TABLE_POSTGRES);
			s.executeUpdate(Queries.CREATE_TABLE_POSTGRES);
		} catch (SQLException e) {
			SystemExceptionHandler.handleException(e);
		}
	}

	protected void initConn() {
		try {
			PropertiesManager pm = PropertiesManager.getInstance();
			conn = DriverManager.getConnection(
					"jdbc:postgresql://" + pm.getProperty(DB_SERVER_URL_PROPERTY) + ":"
							+ pm.getProperty(DB_SERVER_PORT_PROPERTY) + "/" + pm.getProperty(DB_SERVER_NAME_PROPERTY),
					pm.getProperty(DB_SERVER_USERNAME_PROPERTY), pm.getProperty(DB_SERVER_PASSWORD_PROPERTY));
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			SystemExceptionHandler.handleException("unable to get db connection", e);
		}
	}
}
