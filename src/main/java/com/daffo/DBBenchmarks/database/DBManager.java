package com.daffo.DBBenchmarks.database;

import static com.daffo.DBBenchmarks.constants.Constants.DB_SERVER_NAME_PROPERTY;
import static com.daffo.DBBenchmarks.constants.Constants.DB_SERVER_PASSWORD_PROPERTY;
import static com.daffo.DBBenchmarks.constants.Constants.DB_SERVER_PORT_PROPERTY;
import static com.daffo.DBBenchmarks.constants.Constants.DB_SERVER_URL_PROPERTY;
import static com.daffo.DBBenchmarks.constants.Constants.DB_SERVER_USERNAME_PROPERTY;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.locks.ReentrantLock;

import com.daffo.DBBenchmarks.constants.Queries;
import com.daffo.DBBenchmarks.helpers.PropertiesManager;

/**
 * Manage the interactions with the database
 *
 * @author daffo
 *
 */
public class DBManager {

	private static ReentrantLock lock = new ReentrantLock();
	private static DBManager manager = null;

	private Connection conn;
	private PreparedStatement stat;

	private DBManager() {
		init();
	}

	public static DBManager getInstance() {
		if (manager == null) {
			try {
				lock.lock();
				if (manager == null) {
					manager = new DBManager();
				}
			} finally {
				lock.unlock();
			}
		}
		return manager;
	}

	public void setupTable() throws SQLException {
		try (Statement s = conn.createStatement()) {
			s.executeUpdate(Queries.DROP_TABLE);
			s.executeUpdate(Queries.CREATE_TABLE);
		}
	}

	public void initStatement(String query) throws SQLException {
		stat = conn.prepareStatement(query);
	}

	public void addToBatch(String param) throws SQLException {
		stat.setString(1, param);
		stat.addBatch();
	}

	public void addToBatch(int pk, String param) throws SQLException {
		stat.setInt(2, pk);
		stat.setString(1, param);
		stat.addBatch();
	}

	public void executeBatch() throws SQLException {
		stat.executeBatch();
		conn.commit();
	}

	public void executeSelect(int pk) throws SQLException {
		stat.setInt(1, pk);
		stat.executeQuery();
	}

	public void closeConnection() {
		if (this.conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("Unable to close connection: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	private void init() {
		initConn();
	}

	private void initConn() {
		try {
			PropertiesManager pm = PropertiesManager.getInstance();
			conn = DriverManager.getConnection(
					"jdbc:postgresql://" + pm.getProperty(DB_SERVER_URL_PROPERTY) + ":"
							+ pm.getProperty(DB_SERVER_PORT_PROPERTY) + "/" + pm.getProperty(DB_SERVER_NAME_PROPERTY),
					pm.getProperty(DB_SERVER_USERNAME_PROPERTY), pm.getProperty(DB_SERVER_PASSWORD_PROPERTY));
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			throw new RuntimeException("unable to get db connection", e);
		}
	}
}
