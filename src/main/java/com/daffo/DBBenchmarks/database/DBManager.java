package com.daffo.DBBenchmarks.database;

/**
 * Manage the interactions with the database
 * @author daffo
 *
 */
public interface DBManager {

	void setupTable();

	void initStatement(String query);

	void addToBatch(String param);

	void addToBatch(int pk, String param);

	void executeBatch();

	void executeSelect(int pk);

	void closeConnection();
}
