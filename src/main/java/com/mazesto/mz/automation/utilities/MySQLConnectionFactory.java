package com.mazesto.mz.automation.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mazesto.automation.commons.ThreadTestDataMapper;



public class MySQLConnectionFactory {
	// Fields for DB connections

	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	private String connectionString = "";

	public MySQLConnectionFactory(String envTypeCDEOrNCDE) {
		this.connectionString = setConnectionString(envTypeCDEOrNCDE);
	}

	private String setConnectionString(String envTypeCDEOrNCDE) {

		String environment = ThreadTestDataMapper.getData(Thread.currentThread(), "Enviroment");
		
		
		switch (environment.toUpperCase()) {

		case "SDEV": {
			if (envTypeCDEOrNCDE.equalsIgnoreCase("CDE")) {
				return "jdbc:mysql://127.0.0.1:3307/dims?user=qadev&password=Qa@123$";
			}

			else {
				return "jdbc:mysql://127.0.0.1:3307/dims?user=qadev&password=Qa@123$";
			}


		}
		case "SQA": {
			if (envTypeCDEOrNCDE.equalsIgnoreCase("CDE")) {
				return "jdbc:mysql://127.0.0.1:3323/dims?user=dims_qa&password=B4ZSkH0n0R1xnVGruwxa";
			}

			else {
				return "jdbc:mysql://127.0.0.1:3324/dims?user=dims_qa&password=eKiFU7dMPKS3EQa3opiB";
			}

	
		}
		
		case "MSDEV": {
			if (envTypeCDEOrNCDE.equalsIgnoreCase("CDE")) {
				return "jdbc:mysql://127.0.0.1:3307/dims?user=qadev&password=Qa@123$";
			}

			else {
				return "jdbc:mysql://127.0.0.1:3307/dims?user=qadev&password=Qa@123$";
			}


		}
		case "MSQA": {
			if (envTypeCDEOrNCDE.equalsIgnoreCase("CDE")) {
				return "jdbc:mysql://127.0.0.1:3323/dims?user=dims_qa&password=B4ZSkH0n0R1xnVGruwxa";
			}

			else {
				return "jdbc:mysql://127.0.0.1:3324/dims?user=dims_qa&password=eKiFU7dMPKS3EQa3opiB";
			}

	
		}
		case "MDEV": {
			if (envTypeCDEOrNCDE.equalsIgnoreCase("CDE")) {
				return "jdbc:mysql://127.0.0.1:3312/dims?user=bitwise_qa&password=ne1aphee2fevohTo";
			}

			else {
				return "jdbc:mysql://127.0.0.1:3314/dims?user=bitwise_qa&password=Ahwi9niechaika4s";
			}

			
		}
		case "MQA": {
			if (envTypeCDEOrNCDE.equalsIgnoreCase("CDE")) {
				return "jdbc:mysql://127.0.0.1:3315/dims?user=bitwise_qa&password=dsafaFDS394xoqul";
			}

			else {
				return "jdbc:mysql://127.0.0.1:3316/dims?user=bitwise_qa&password=dsafaFDS394xoqul";
			}

	
		}
		case "CERT": {
			if (envTypeCDEOrNCDE.equalsIgnoreCase("CDE")) {
				return "jdbc:mysql://127.0.0.1:3319/dims?user=bitwise_qa&password=HolidayUnicorn2019!";
			}

			else {
				return "jdbc:mysql://127.0.0.1:3320/dims?user=bitwise_qa&password=HolidayBicycle2019!";
			}

		
		}
		default:
			return "jdbc:mysql://127.0.0.1:3310/dims?user=dims_qa&password=cde_VpyFOMfoSDvmlKC";
		}

	}


	public Connection getConnection() {
		try {
			System.out.println("Above Connection String : ");

			System.out.println("Connection String : " + connectionString);

			// Reporter.addStepLog("Connecting to " + connectionString);
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(connectionString);
			System.out.println("Connection successfull");
			return conn;

		} catch (Exception ex) {
			System.out.println("SQLException: " + ex.getMessage());

		}
		return conn;

	}

	public void setConnection() {

		Connection conn = getConnection();

		try {
			stmt = conn.createStatement();
		} catch (Exception ex) {
			System.out.println("SQLException: " + ex.getMessage());

		}

	}

	public ResultSet executeQuery(String query) {

		try {
			rs = stmt.executeQuery(query);
			return rs;

		} catch (Exception ex) {
			System.out.println("SQLException: " + ex.getMessage());

		}

		return rs;
	}

	public void closeConnection() throws SQLException {

		if (conn != null) {
			conn.close();
		}
		if (stmt != null) {
			stmt.close();
		}
		if (rs != null) {
			rs.close();
		}

	}

	public int executeUpdate(String query) {
		int rowsAffected = 0;
		try {
			rowsAffected = stmt.executeUpdate(query);
			return rowsAffected;

		} catch (Exception ex) {
			System.out.println("SQLException: " + ex.getMessage());

		}

		return rowsAffected;
	}

}
