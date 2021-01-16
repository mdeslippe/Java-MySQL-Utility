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
import java.sql.SQLException;

/**
 * An abstract SQLConnection.
 *
 * @author Myles Deslippe
 */
public abstract class AbstractSQLConnection {

	/**
	 * The host variable.
	 */
	private final String host;

	/**
	 * The database variable.
	 */
	private final String database;

	/**
	 * The username variable.
	 */
	private final String username;

	/**
	 * The password variable
	 */
	private final String password;

	/**
	 * The port variable.
	 */
	private final Integer port;

	/**
	 * The AbstractSQLConnection constructor.
	 *
	 * @param host     The SQL server host.
	 * @param port     The port the SQL server is listening on.
	 * @param database The database to connect to.
	 * @param username The username to login.
	 * @param password The password to login.
	 */
	public AbstractSQLConnection(String host, Integer port, String database, String username, String password) {
		this.host = host;
		this.port = port;
		this.database = database;
		this.username = username;
		this.password = password;
	}

	/**
	 * Get the connection's host.
	 *
	 * @return Returns the connection's host.
	 */
	public String getHost() {
		return host;
	}

	/**
	 * Get the connection's port.
	 *
	 * @return Returns the connection's port.
	 */
	public Integer getPort() {
		return port;
	}

	/**
	 * Get the connection's database name.
	 *
	 * @return Returns the connection's database name.
	 */
	public String getDatabase() {
		return database;
	}

	/**
	 * Get the connection's username.
	 *
	 * @return Returns the connection's username.
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * Get the connection's password.
	 *
	 * @return Returns the connection's password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Get the connection's host.
	 *
	 * @return Returns the connection's host.
	 */

	/**
	 * Start the connection pool.
	 *
	 * @throws SQLException           the SQL exception.
	 * 
	 * @throws ClassNotFoundException the class not found exception.
	 */
	public abstract void open() throws SQLException, ClassNotFoundException;

	/**
	 * Terminate the connection pool.
	 *
	 * @throws SQLException the SQL exception.
	 */
	public abstract void close() throws SQLException;

	/**
	 * Get the connection.
	 *
	 * @return the connection.
	 */
	public abstract Connection getConnection();

}