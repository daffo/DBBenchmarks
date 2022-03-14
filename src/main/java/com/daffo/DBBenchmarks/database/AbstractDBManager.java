package com.daffo.DBBenchmarks.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.daffo.DBBenchmarks.helpers.SystemExceptionHandler;

/**
 * Implements BDManager methods not database specific
 * @author daffo
 *
 */
public abstract class AbstractDBManager implements DBManager {

	protected Connection conn;
	protected PreparedStatement stat;

	abstract protected void initConn() throws SQLException;

	public void initStatement(String query) {
		try {
			stat = conn.prepareStatement(query);
		} catch (SQLException e) {
			SystemExceptionHandler.handleException(e);
		}
	}

	public void addToBatch(String param) {
		try {
			stat.setString(1, param);
			stat.addBatch();
		} catch (SQLException e) {
			SystemExceptionHandler.handleException(e);
		}
	}

	public void addToBatch(int pk, String param) {
		try {
			stat.setInt(2, pk);
			stat.setString(1, param);
			stat.addBatch();
		} catch (SQLException e) {
			SystemExceptionHandler.handleException(e);
		}
	}

	public void executeBatch() {
		try {
			stat.executeBatch();
			conn.commit();
		} catch (SQLException e) {
			SystemExceptionHandler.handleException(e);
		}
	}

	public void executeSelect(int pk) {
		try {
			stat.setInt(1, pk);
			stat.executeQuery();
		} catch (SQLException e) {
			SystemExceptionHandler.handleException(e);
		}
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
}
