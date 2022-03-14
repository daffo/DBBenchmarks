package com.daffo.DBBenchmarks.helpers;

import com.daffo.DBBenchmarks.constants.Constants;
import com.daffo.DBBenchmarks.database.DBManagerFactory;

/**
 * Handle exception and terminate program execution
 *
 * @author serrad
 *
 */
public class SystemExceptionHandler {

	public static void handleException(String message, Throwable e) {
		System.out.println(message);
		handleException(e);
	}

	public static void handleException(Throwable e) {
		System.out.println(e.getMessage());
		if (Boolean.parseBoolean(PropertiesManager.getInstance().getProperty(Constants.VERBOSE)))
			e.printStackTrace();
		try {
			DBManagerFactory.getDBManagerInstance().closeConnection();
		} catch (Throwable x) {
			System.out.println("error closing clonnection before exiting");
			System.out.println(x.getMessage());
		}
		System.exit(-1);
	}
}
