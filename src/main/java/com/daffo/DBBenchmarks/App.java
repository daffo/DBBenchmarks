package com.daffo.DBBenchmarks;

import com.daffo.DBBenchmarks.database.DBManager;
import com.daffo.DBBenchmarks.helpers.BenchmarkExecutor;

/**
 * Entrypoint for DBBenchmarks tool
 *
 * @author daffo
 *
 */
public class App {
	public static void main(String[] args) {
		try {
			new BenchmarkExecutor().run();
		} catch (Throwable e) {
			System.out.println("Something went wrong: " + e.getMessage());
			e.printStackTrace();
			DBManager.getInstance().closeConnection();
		}
	}
}