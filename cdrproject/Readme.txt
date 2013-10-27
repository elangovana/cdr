Pre-requisites 
---------------
1. J2SE 1.7 (for Dev) JRE 1.7 for Just Run
2. MongoDb 2.4

Configuration 
------------
For CDR Steam configuration, see cdrstream/readme.txt
For CDR WebApp configuration, see cdrweb/readme.txt

Maven build and Run Commands
---------------------------
	
	1. mvn clean compile install  (This is a multi module project, so this command must be run to run the stream or the web project)
	
	2. Run the projects
	To run stream
		cd ./cdrstream
		mvn exec:java exec:java -Dexec.classpathScope=compile -Dexec.mainClass=com.ae.assignment.cdrproject.cdrstream.CDRTopology
	
	To Run Web in standalone Tomcat 7
	   cd ./cdrweb
	   mvn tomcat7:run
	   Access the website on http://localhost:9091
	   ** Any issues with port number, the port can be changed in pom.xml
	  ......
	   <groupId>org.apache.tomcat.maven</groupId>
				<artifactId>tomcat7-maven-plugin</artifactId>
				<version>2.1</version>
				<configuration>
					<!-- http port -->
					<port>9091</port>
					<!-- application path always starts with / -->
					<path>/</path>
					............
	
