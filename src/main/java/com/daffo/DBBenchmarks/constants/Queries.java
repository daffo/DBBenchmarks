package com.daffo.DBBenchmarks.constants;

import static com.daffo.DBBenchmarks.constants.Constants.TABLE_FIELD_1;
import static com.daffo.DBBenchmarks.constants.Constants.TABLE_FIELD_ID;
import static com.daffo.DBBenchmarks.constants.Constants.TABLE_NAME;

public class Queries {
	public static final String DROP_TABLE_POSTGRES = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
	public static final String CREATE_TABLE_POSTGRES = "CREATE TABLE " + TABLE_NAME + " (" + TABLE_FIELD_ID
			+ " SERIAL PRIMARY KEY, " + TABLE_FIELD_1 + " VARCHAR(255));";
	public static final String DROP_TABLE_MSSQL = "IF OBJECT_ID('" + TABLE_NAME + "', 'U') IS NOT NULL DROP TABLE "
			+ TABLE_NAME + ";";
	public static final String CREATE_TABLE_MSSQL = "CREATE TABLE " + TABLE_NAME + " (" + TABLE_FIELD_ID
			+ " INT NOT NULL IDENTITY(1,1) PRIMARY KEY, " + TABLE_FIELD_1 + " VARCHAR(255));";
	public static final String INSERT_INTO_TABLE = "INSERT INTO " + TABLE_NAME + " (" + TABLE_FIELD_1 + ") VALUES (?);";
	public static final String SELECT_FROM_TABLE = "SELECT " + TABLE_FIELD_ID + ", " + TABLE_FIELD_1 + " FROM "
			+ TABLE_NAME + " WHERE " + TABLE_FIELD_ID + " = ?;";
	public static final String UPDATE_TABLE = "UPDATE " + TABLE_NAME + " SET " + TABLE_FIELD_1 + " = ? WHERE "
			+ TABLE_FIELD_ID + " = ?;";
}
