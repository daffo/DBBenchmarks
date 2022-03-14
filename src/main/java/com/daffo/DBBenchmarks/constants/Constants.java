package com.daffo.DBBenchmarks.constants;

public class Constants {
	// benchmark config
	public static final String TABLE_NAME = "benchmarkTable";
	public static final String TABLE_FIELD_ID = "id";
	public static final String TABLE_FIELD_1 = "field1";

	public static final int INSERT_ITERATIONS = 10000;
	public static final int SELECT_ITERATIONS = 1000000;
	public static final int UPDATE_ITERATIONS = 10000;
	public static final int COMMIT_BATCH_SIZE = 100;

	// db connection
	public static final String DB_SERVER_URL_PROPERTY = "com.daffo.DBBenchmarks.DB_SERVER_URL";
	public static final String DB_SERVER_PORT_PROPERTY = "com.daffo.DBBenchmarks.DB_SERVER_PORT";
	public static final String DB_SERVER_NAME_PROPERTY = "com.daffo.DBBenchmarks.DB_SERVER_NAME";
	public static final String DB_SERVER_USERNAME_PROPERTY = "com.daffo.DBBenchmarks.DB_SERVER_USERNAME";
	public static final String DB_SERVER_PASSWORD_PROPERTY = "com.daffo.DBBenchmarks.DB_SERVER_PASSWORD";
}
