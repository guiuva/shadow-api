package org.gui.shadow.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author David Soler <aensoler@gmail.com>
 */
public class SQLiteHandler implements DatabaseHandler {

	private final static boolean DEBUG = true;

	private Connection connection;
	private String database;

	public SQLiteHandler(String database) {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(1);
		}
		this.database = database;
		open();
	}

	@Override
	public void open() {
		Boolean isClosed = true;

		try {
			if (connection != null) isClosed = connection.isClosed();
		} catch (SQLException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(2);
		}

		if (isClosed) {
			try {
				Class.forName("org.sqlite.JDBC");
				connection = DriverManager.getConnection("jdbc:sqlite:" + database);
			} catch (Exception e) {
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
				System.exit(3);
			} finally {
				if (SQLiteHandler.DEBUG) System.out.println("Opened database successfully.");
			}
		} else {
			if (SQLiteHandler.DEBUG) System.out.println("The database is already opened.");
		}
	}

	@Override
	@SuppressWarnings("finally")
	public ResultSet retrieve(String sql) {
		Statement stmt = null;
		ResultSet resultSet = null;

		try {
			stmt = connection.createStatement();
			resultSet = stmt.executeQuery(sql);
			if (SQLiteHandler.DEBUG) System.out.println("Operation done successfully.");
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(4);
		} finally {
			return resultSet;
		}
	}

	@Override
	public int update(String sql) {
		Statement stmt = null;
		int rowCount = 0;

		try {
			stmt = connection.createStatement();
			rowCount = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(5);
		}

		return rowCount;
	}

	@Override
	public void close() {
		try {
			connection.close();
			if (SQLiteHandler.DEBUG) System.out.println("Database closed successfully");
		} catch (SQLException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(6);
		}
	}

}
