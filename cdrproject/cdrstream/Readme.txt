Pre-requisites 
---------------
1. J2SE 1.7 (for Dev) JRE 1.7 for Just Run

2. MongoDb 2.4


Configuration:
---------------
at the bare minimum  the following must be configured, configuration is in src/main/resources/config.properties(in dev) or config.properties (in jar)
	- The location of files property CDRFiles.BaseDirectory
	- CDRFiles.FilesExtension
	- CDRFiles.WorkingDirectory ( temporary work area must be configured)
	- eclipselink.nosql.property.mongo.port
	- eclipselink.nosql.property.mongo.host

Additional Optional Configuration 
1. General Logging config in log4j.properties
2. Turn Storm Debug mode on or off by setting  Debug property in config.properties file

