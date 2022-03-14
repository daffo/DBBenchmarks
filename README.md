# DBBenchmarks
Small java application to run a set of tests against a local database (Postgres)

checkout the repo locally and run `mvn install` to create the executable .jar under /target folder

**IMPORTANT** if the default .jar doesn't run (it's probably the JDBC driver missing) use he fat version `DBBenchmarks-jar-with-dependencies.jar`

##### Configuration
Change parameters in config.properties file, under /src/main/resources/ with the correct database values and rebuild the .jar