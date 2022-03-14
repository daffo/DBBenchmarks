package com.daffo.DBBenchmarks.constants;

import java.util.function.Supplier;

import com.daffo.DBBenchmarks.database.DBManager;
import com.daffo.DBBenchmarks.database.MSSQLDBManager;
import com.daffo.DBBenchmarks.database.PostgresDBManager;

/**
 * Supported databases with relative DBManager Implementations
 * @author daffo
 *
 */
public enum DBTypes {
	postgres(PostgresDBManager::new), mssql(MSSQLDBManager::new);

	private Supplier<DBManager> impl;

	DBTypes(Supplier<DBManager> impl) {
		this.impl = impl;
	}

	public DBManager getImplementation() {
		return impl.get();
	}
}
