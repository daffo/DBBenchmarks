package com.daffo.DBBenchmarks.constants;

import static com.daffo.DBBenchmarks.constants.Constants.TABLE_FIELD_1;
import static com.daffo.DBBenchmarks.constants.Constants.TABLE_FIELD_ID;
import static com.daffo.DBBenchmarks.constants.Constants.TABLE_NAME;

public class Queries {
	public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
	public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + TABLE_FIELD_ID
			+ " SERIAL PRIMARY KEY, " + TABLE_FIELD_1 + " VARCHAR(255));";
	public static final String INSERT_INTO_TABLE = "INSERT INTO " + TABLE_NAME + " (" + TABLE_FIELD_1 + ") VALUES (?);";
	public static final String SELECT_FROM_TABLE = "SELECT " + TABLE_FIELD_ID + ", " + TABLE_FIELD_1 + " FROM "
			+ TABLE_NAME + " WHERE " + TABLE_FIELD_ID + " = ?;";
	public static final String UPDATE_TABLE = "UPDATE " + TABLE_NAME + " SET " + TABLE_FIELD_1 + " = ? WHERE "
			+ TABLE_FIELD_ID + " = ?;";
}
