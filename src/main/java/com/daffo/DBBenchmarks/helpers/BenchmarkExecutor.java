package com.daffo.DBBenchmarks.helpers;

import java.sql.SQLException;

import com.daffo.DBBenchmarks.database.DBManager;


/**
 * Execute the benchmarks and visualize the results
 *
 * @author daffo
 *
 */
public class BenchmarkExecutor {

	public BenchmarkExecutor() throws Exception {
		DBManager.getInstance().setupTable();
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
