package com.daffo.DBBenchmarks.helpers;

import java.sql.SQLException;

import com.daffo.DBBenchmarks.database.DBManagerFactory;

/**
 * Execute the benchmarks and visualize the results
 *
 * @author daffo
 *
 */
public class BenchmarkExecutor {

	public BenchmarkExecutor() throws SQLException {
		DBManagerFactory.getDBManagerInstance().setupTable();
	}

	public void run() throws SQLException {
		TestManager testManager = new TestManager();
		testManager.executeInsertTest();
		testManager.printResults();
		testManager.executeSelectTest();
		testManager.printResults();
		testManager.executeUpdateTest();
		testManager.printResults();
	}

}
