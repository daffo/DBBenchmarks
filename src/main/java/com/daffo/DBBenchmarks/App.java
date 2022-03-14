package com.daffo.DBBenchmarks;

import com.daffo.DBBenchmarks.helpers.BenchmarkExecutor;
import com.daffo.DBBenchmarks.helpers.PropertiesManager;
import com.daffo.DBBenchmarks.helpers.SystemExceptionHandler;

/**
 * Entrypoint for DBBenchmarks tool
 *
 * @author daffo
 *
 */
public class App {
	public static void main(String[] args) {
		try {
		PropertiesManager.getInstance().overrideProperties(args);
			new BenchmarkExecutor().run();
		} catch (Throwable e) {
			SystemExceptionHandler.handleException(e);
		}
	}
}