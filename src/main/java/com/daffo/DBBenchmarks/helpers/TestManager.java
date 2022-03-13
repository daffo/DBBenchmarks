package com.daffo.DBBenchmarks.helpers;

import java.sql.SQLException;
import java.util.Random;

import com.daffo.DBBenchmarks.constants.Constants;
import com.daffo.DBBenchmarks.constants.Queries;
import com.daffo.DBBenchmarks.database.DBManager;

/**
 * Logic to run tests and collect relevant data
 *
 * @author daffo
 *
 */
public class TestManager {
	private TestResults results;

	public void executeInsertTest() throws SQLException {
		System.out.print("executing insert test\n");
		results = new TestResults(Constants.INSERT_ITERATIONS);

		DBManager dbManager = DBManager.getInstance();
		dbManager.initStatement(Queries.INSERT_INTO_TABLE);

		for (int i = 0; i < Constants.INSERT_ITERATIONS; i++) {
			String[] randomStrings = new String[Constants.COMMIT_BATCH_SIZE];
			for (int j = 0; j < Constants.COMMIT_BATCH_SIZE; j++) {
				randomStrings[j] = generateRandomWord(20, 200);
			}
			long start = System.nanoTime();
			for (int j = 0; j < Constants.COMMIT_BATCH_SIZE; j++) {
				dbManager.addToBatch(randomStrings[j]);
			}
			dbManager.executeBatch();
			long end = System.nanoTime();
			results.times[i] = end - start;
		}
		System.out.print("end execution\n");
	}

	public void executeSelectTest() throws SQLException {
		System.out.print("executing select test\n");
		results = new TestResults(Constants.SELECT_ITERATIONS);

		DBManager dbManager = DBManager.getInstance();
		dbManager.initStatement(Queries.SELECT_FROM_TABLE);

		int pkRange = Constants.INSERT_ITERATIONS * Constants.COMMIT_BATCH_SIZE * 2;
		Random r = new Random();
		for (int i = 0; i < Constants.SELECT_ITERATIONS; i++) {
			long start = System.nanoTime();
			dbManager.executeSelect(r.nextInt(pkRange) + 1);
			long end = System.nanoTime();
			results.times[i] = end - start;
		}
		System.out.print("end execution\n");
	}

	public void executeUpdateTest() throws SQLException {
		System.out.print("executing update test\n");
		results = new TestResults(Constants.UPDATE_ITERATIONS);

		DBManager dbManager = DBManager.getInstance();
		dbManager.initStatement(Queries.UPDATE_TABLE);

		int pkRange = Constants.INSERT_ITERATIONS * Constants.COMMIT_BATCH_SIZE * 2;
		for (int i = 0; i < Constants.UPDATE_ITERATIONS; i++) {
			String[] randomStrings = new String[Constants.COMMIT_BATCH_SIZE];
			int[] randomPks = new int[Constants.COMMIT_BATCH_SIZE];
			Random r = new Random();
			for (int j = 0; j < Constants.COMMIT_BATCH_SIZE; j++) {
				randomStrings[j] = generateRandomWord(20, 200);
				randomPks[j] = r.nextInt(pkRange) + 1;
			}
			long start = System.nanoTime();
			for (int j = 0; j < Constants.COMMIT_BATCH_SIZE; j++) {
				dbManager.addToBatch(randomPks[j], randomStrings[j]);
			}
			dbManager.executeBatch();
			long end = System.nanoTime();
			results.times[i] = end - start;
		}
		System.out.print("end execution\n");
	}

	public void printResults() {
		computeResults();
		System.out.print("RESULTS:\n");
		System.out.printf("Min exec time: %dns, achieved in iteration n %d\n", results.minTime, results.minTimeIteration + 1);
		System.out.printf("Max exec time: %dns, achieved in iteration n %d\n", results.maxTime, results.maxTimeIteration + 1);
		System.out.printf("Avg exec time: %dns\n", results.avgTime);
		System.out.print("------\n");
	}

	private String generateRandomWord(int min, int max) {
		StringBuilder sb = new StringBuilder();
		Random r = new Random();
		for (int i = 0; i < r.nextInt(max - min + 1) + min; i++) {
			sb.append((char) (r.nextInt(26) + 97));
		}
		return sb.toString();
	}

	private void computeResults() {
		for (int i = 0; i < results.times.length; i++) {
			results.avgTime += results.times[i];
			if (results.times[i] > results.maxTime) {
				results.maxTime = results.times[i];
				results.maxTimeIteration = i;
			}
			if (results.times[i] < results.minTime) {
				results.minTime = results.times[i];
				results.minTimeIteration = i;
			}
		}
		results.avgTime /= results.times.length;
	}

	private class TestResults {
		private long[] times;
		private long minTime;
		private int minTimeIteration;
		private long maxTime;
		private int maxTimeIteration;
		private long avgTime;

		private TestResults(int iterations) {
			times = new long[iterations];
			minTime = Long.MAX_VALUE;
			minTimeIteration = 0;
			maxTime = Long.MIN_VALUE;
			maxTimeIteration = 0;
			avgTime = 0L;
		}
	}
}
