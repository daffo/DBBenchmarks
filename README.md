# DBBenchmarks
Small java application to run a set of tests against a local database (Postgres)

checkout the repo locally and run `mvn install` to create the executable .jar under /target folder

**IMPORTANT** use DBBenchmarks.jar which is the fat version containing JDBC drivers, otherwise you'll get `No suitable driver found for jdbc:...` error.

##### Configuration
Change parameters in config.properties file, under /src/main/resources/ with the correct database values and rebuild the .jar

Alternatively you can pass custom configuration parameters as input (i.e. `java -jar DBBenchmarks.jar -u dbusername -p dbpassword`)

**usage:**

 -n,--dbname <arg>      database name
 
 -p,--password <arg>    database password
 
 -s,--serverurl <arg>   database server url
 
 -t,--dbtype <arg>      database server type (supported values: mssql, postgres)
 
 -u,--username <arg>    database username
 
 -v,--verbose           enable verbose logs
