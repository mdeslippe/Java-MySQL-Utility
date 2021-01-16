/*
 * 
 * Copyright 2021 Myles Deslippe
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package me.myles.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A utility to create and manage SQL connections.
 *
 * @author Myles Deslippe
 */
public final class SQLConnection extends AbstractSQLConnection {

	/**
	 * The Connection variable.
	 */
	private Connection connection;

	/**
	 * The SQLConnection constructor.
	 *
	 * @param host     The SQL server host.
	 * @param port     The port the SQL server is listening on.
	 * @param database The database to connect to.
	 * @param username The username to login.
	 * @param password The password to login.
	 */
	public SQLConnection(String host, Integer port, String database, String username, String password) {
		super(host, port, database, username, password);
	}

	/**
	 * Get the connection.
	 *
	 * @return Returns the SQL connection.
	 */
	@Override
	public Connection getConnection() {
		return this.connection;
	}

	/**
	 * Start the connection pool.
	 *
	 * @throws SQLException           the SQL exception.
	 * @throws ClassNotFoundException the class not found exception.
	 */
	@Override
	public synchronized void open() throws SQLException, ClassNotFoundException {

		// If the connection is already open there is no need to open it again.
		if (this.connection != null && !this.connection.isClosed())
			return;

		// Attempt to open the connection.
		Class.forName("com.mysql.jdbc.Driver");
		this.connection = DriverManager.getConnection("jdbc:mysql://" + getHost() + "/" + getDatabase(), getUsername(),
				getPassword());
		this.connection.setAutoCommit(false); // IMPORTANT: DO NOT REMOVE!!
	}

	/**
	 * Terminate the connection pool.
	 *
	 * @throws SQLException the SQL exception.
	 */
	@Override
	public synchronized void close() throws SQLException {

		// If the connection is already closed there is no need to close it again.
		if (this.connection == null || this.connection.isClosed())
			return;

		this.connection.close();
	}

	/**
	 * Push a new commit to the database.
	 * 
	 * @throws SQLException The SQL exception.
	 */
	public synchronized void commit() throws SQLException {
		this.connection.commit();
	}

	/**
	 * Run a statement.
	 * 
	 * <p>
	 * <strong>Note:</strong> This method will automatically {@link #open() open}
	 * the SQL connection if it is not open.
	 * </p>
	 * 
	 * @param statement The statement to run.
	 * 
	 * @throws SQLException           The SQL exception.
	 * @throws ClassNotFoundException
	 */
	public synchronized void runStatement(String statement) throws SQLException, ClassNotFoundException {
		this.runStatement(this.connection.prepareStatement(statement));
	}

	/**
	 * Run a statement.
	 * 
	 * <p>
	 * <strong>Note:</strong> This method will automatically {@link #open() open}
	 * the SQL connection if it is not open.
	 * </p>
	 * 
	 * @param statement The statement to run.
	 * 
	 * @throws SQLException           The SQL exception.
	 * @throws ClassNotFoundException
	 */
	public synchronized void runStatement(PreparedStatement statement) throws SQLException, ClassNotFoundException {

		// Ensure the connection is open.
		if (this.connection != null && this.connection.isClosed())
			this.open();

		// Run the statement.
		statement.execute();
		this.commit();
		statement.close();
	}

	/**
	 * Run a query.
	 * 
	 * <p>
	 * <strong>Note:</strong> This method will automatically {@link #open() open}
	 * the SQL connection if it is not open.
	 * </p>
	 * 
	 * @param statement The query to execute.
	 * 
	 * @return A {@link java.sql.ResultSet ResultSet} that contains the data
	 *         generated by the query; never null.
	 * 
	 * @throws SQLException           The SQL exception.
	 * @throws ClassNotFoundException
	 */
	public synchronized ResultSet runQuery(String statement) throws SQLException, ClassNotFoundException {
		return this.runQuery(this.connection.prepareStatement(statement));
	}

	/**
	 * Run a query.
	 * 
	 * <p>
	 * <strong>Note:</strong> This method will automatically {@link #open() open}
	 * the SQL connection if it is not open.
	 * </p>
	 * 
	 * @param statement The query to execute.
	 * 
	 * @return A {@link java.sql.ResultSet ResultSet} that contains the data
	 *         generated by the query; never null.
	 * 
	 * @throws SQLException           The SQL exception.
	 * @throws ClassNotFoundException
	 */
	public synchronized ResultSet runQuery(PreparedStatement statement) throws SQLException, ClassNotFoundException {

		// Ensure the connection is open.
		if (this.connection != null && this.connection.isClosed())
			this.open();

		// Run the query.
		ResultSet results = statement.executeQuery();

		return results;
	}

	/**
	 * Run an update.
	 * 
	 * <p>
	 * <strong>Note:</strong> This method will automatically {@link #open() open}
	 * the SQL connection if it is not open.
	 * </p>
	 * 
	 * @param statement The statement to execute.
	 * 
	 * @throws SQLException           The SQL Exception.
	 * @throws ClassNotFoundException
	 */
	public synchronized void runUpdate(String statement) throws SQLException, ClassNotFoundException {
		this.runUpdate(this.connection.prepareStatement(statement));
	}

	/**
	 * Run an update.
	 * 
	 * <p>
	 * <strong>Note:</strong> This method will automatically {@link #open() open}
	 * the SQL connection if it is not open.
	 * </p>
	 * 
	 * @param statement The statement to execute.
	 * 
	 * @throws SQLException           The SQL Exception.
	 * @throws ClassNotFoundException
	 */
	public synchronized void runUpdate(PreparedStatement statement) throws SQLException, ClassNotFoundException {

		// Ensure the connection is open.
		if (this.connection != null && this.connection.isClosed())
			this.open();

		// Run the update.
		statement.executeUpdate();
		this.commit();
		statement.close();
	}

}