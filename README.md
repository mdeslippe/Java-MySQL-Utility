# Java MySQL Utility
This is a simple utility that allows you to easily connect to and manage any MySQL database!

## Installation
1) Add the following repository to your pom.xml file
```xml
<repository>
	<id>jitpack.io</id>
	<url>https://jitpack.io</url>
</repository>
```
2) Add the following dependency to your pom.xml file
```xml
<dependency>
	<groupId>com.github.mdeslippe</groupId>
	<artifactId>Java-MySQL-Utility</artifactId>
	<version>${VERSION}</version>
	<scope>provided</scope>
</dependency>
```
3) You are good to go!
## How to open a connection
Opening a connection is very simple, and can be done very easily as follows:
```java
SQLConnection connection = new SQLConnection(
	host, 
	port,
	database, 
	username,
	password
	);
		
connection.open();
```
## How to manipulate a database
### Queries
Running queries is very easy with this utility, simply do the following:
```java
ResultSet results = connection.runQuery("query");
```
After running that, the ResultSet will contain all of the rows retreived.

### Updates
Running queries is also very easy with this utility, simply do the following:
```java
ResultSet results = connection.runUpdate("query");
```
## Common Pitfalls
### Updates / Queries
You must ensure that you are using the correct method to run the statement, if you use runQuery() to run an update or runUpdate() to run a query, they will not work.

## Contributions
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.