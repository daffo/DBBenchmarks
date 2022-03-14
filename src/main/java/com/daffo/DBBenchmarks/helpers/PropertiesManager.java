package com.daffo.DBBenchmarks.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Helper to manage configuration parameters
 * @author daffo
 *
 */
public class PropertiesManager {
	private static ReentrantLock lock = new ReentrantLock();
	private static PropertiesManager manager = null;

	private Properties props;

	private PropertiesManager() {
		loadProperties();
	}

	public static PropertiesManager getInstance() {
		if (manager == null) {
			try {
				lock.lock();
				if (manager == null) {
					manager = new PropertiesManager();
				}
			} finally {
				lock.unlock();
			}
		}
		return manager;
	}

	public void loadProperties() {
		Properties props = new Properties();
		String propFileName = "config.properties";

		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

		if (inputStream != null) {
			try {
				props.load(inputStream);
			} catch (IOException e) {
				throw new RuntimeException("unable to load properties file", e);
			}
		} else {
			throw new RuntimeException("properties file not present");
		}
		this.props = props;
	}

	public String getProperty(String propName) {
		return props.getProperty(propName);
	}
}
