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
 * {@link DBTypes#mssql} specific implementation
 *
 * @author daffo
 *
 */
public class MSSQLDBManager extends AbstractDBManager {

	@Override
	public void setupTable() {
		try (Statement s = conn.createStatement()) {
			s.executeUpdate(Queries.DROP_TABLE_MSSQL);
			s.executeUpdate(Queries.CREATE_TABLE_MSSQL);
		} catch (SQLException e) {
			SystemExceptionHandler.handleException(e);
		}
	}

	@Override
	protected void initConn() throws SQLException {
		PropertiesManager pm = PropertiesManager.getInstance();
		conn = DriverManager.getConnection(
				"jdbc:sqlserver://" + pm.getProperty(DB_SERVER_URL_PROPERTY) + ":"
						+ pm.getProperty(DB_SERVER_PORT_PROPERTY) + ";databaseName="
						+ pm.getProperty(DB_SERVER_NAME_PROPERTY) + ";encrypt=true;trustServerCertificate=true;",
				pm.getProperty(DB_SERVER_USERNAME_PROPERTY), pm.getProperty(DB_SERVER_PASSWORD_PROPERTY));
		conn.setAutoCommit(false);
	}

}
