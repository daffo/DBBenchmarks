package com.daffo.DBBenchmarks.helpers;

import java.sql.SQLException;
import java.util.Arrays;
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
		System.out.printf("Absolute min exec time: %dns\n", results.absMinTime);
		System.out.printf("Absolute max exec time: %dns\n", results.absMaxTime);
		System.out.printf("Absolute avg exec time: %dns\n", results.absAvgTime);
		System.out.print("------\n");
		System.out.printf("99percentile min exec time: %dns\n", results.nnpMinTime);
		System.out.printf("99percentile max exec time: %dns\n", results.nnpMaxTime);
		System.out.printf("99percentile avg exec time: %dns\n", results.nnpAvgTime);
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
		Arrays.sort(results.times);
		int l = results.times.length;
		int nnp = l/100*99;
		int gap = (l - nnp)/2;
		results.absMinTime = results.times[0];
		results.absMaxTime = results.times[l-1];
		results.nnpMinTime = results.times[gap];
		results.nnpMaxTime = results.times[l-gap];
		for (int i = 0; i < gap; i++) {
			results.absAvgTime += results.times[i];
		}
		for (int i = gap; i < l - gap; i++) {
			results.absAvgTime += results.times[i];
			results.nnpAvgTime += results.times[i];
		}
		for (int i = l - gap; i < l; i++) {
			results.absAvgTime += results.times[i];
		}
		results.absAvgTime /= l;
		results.nnpAvgTime /= nnp;
	}

	private class TestResults {
		private long[] times;
		private long absMinTime;
		private long absMaxTime;
		private long absAvgTime;
		private long nnpMinTime;
		private long nnpMaxTime;
		private long nnpAvgTime;

		private TestResults(int iterations) {
			times = new long[iterations];
			absMinTime = Long.MAX_VALUE;
			absMaxTime = Long.MIN_VALUE;
			absAvgTime = 0L;
			nnpMinTime = Long.MAX_VALUE;
			nnpMaxTime = Long.MIN_VALUE;
			nnpAvgTime = 0L;
		}
	}
}
