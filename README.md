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

 ##### Output example:
 
```
executing insert test
end execution
RESULTS:
Absolute min exec time: 1035300ns
Absolute max exec time: 32197200ns
Absolute avg exec time: 1177160ns
------
99percentile min exec time: 1047000ns
99percentile max exec time: 1785000ns
99percentile avg exec time: 1142241ns
------
executing select test
end execution
RESULTS:
Absolute min exec time: 26800ns
Absolute max exec time: 6459400ns
Absolute avg exec time: 41488ns
------
99percentile min exec time: 29400ns
99percentile max exec time: 66300ns
99percentile avg exec time: 41196ns
------
executing update test
end execution
RESULTS:
Absolute min exec time: 1316000ns
Absolute max exec time: 17859200ns
Absolute avg exec time: 1594443ns
------
99percentile min exec time: 1422000ns
99percentile max exec time: 2240500ns
99percentile avg exec time: 1586981ns
------
 ```
