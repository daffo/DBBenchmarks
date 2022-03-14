package com.daffo.DBBenchmarks.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.daffo.DBBenchmarks.constants.Constants;

/**
 * Helper to manage configuration and input parameters
 *
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

	public void overrideProperties(String[] args) {
		Options options = new Options();

		options.addOption(new Option("v", "verbose", false, "enable verbose logs"));

		options.addOption(new Option("t", "dbtype", true, "database server type"));
		options.addOption(new Option("s", "serverurl", true, "database server url"));
		options.addOption(new Option("p", "serverport", true, "database server port"));
		options.addOption(new Option("n", "dbname", true, "database name"));
		options.addOption(new Option("u", "username", true, "database username"));
		options.addOption(new Option("p", "password", true, "database password"));

		try {
			CommandLine cmd = new DefaultParser().parse(options, args);

			if (cmd.hasOption("verbose"))
				props.setProperty(Constants.VERBOSE, "true");

			if (cmd.getOptionValue("dbtype") != null)
				props.setProperty(Constants.DB_TYPE_PROPERTY, cmd.getOptionValue("dbtype"));
			if (cmd.getOptionValue("serverurl") != null)
				props.setProperty(Constants.DB_SERVER_URL_PROPERTY, cmd.getOptionValue("serverurl"));
			if (cmd.getOptionValue("serverport") != null)
				props.setProperty(Constants.DB_SERVER_PORT_PROPERTY, cmd.getOptionValue("serverport"));
			if (cmd.getOptionValue("dbname") != null)
				props.setProperty(Constants.DB_SERVER_NAME_PROPERTY, cmd.getOptionValue("dbname"));
			if (cmd.getOptionValue("username") != null)
				props.setProperty(Constants.DB_SERVER_USERNAME_PROPERTY, cmd.getOptionValue("username"));
			if (cmd.getOptionValue("password") != null)
				props.setProperty(Constants.DB_SERVER_PASSWORD_PROPERTY, cmd.getOptionValue("password"));
		} catch (ParseException e) {
			new HelpFormatter().printHelp("error in params syntax, please refer to documentation", options);
			SystemExceptionHandler.handleException(e);
		}

	}
}
