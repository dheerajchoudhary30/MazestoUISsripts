package com.mazesto.mz.automation.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.mazesto.automation.commons.ThreadTestDataMapper;

public class BQConnectionFactory {

	Utilities utilities = new Utilities();
	private static Logger log  = LogManager.getLogger("BQConnectionFactory");
	private Statement stmt = null;
	private Connection con = null;
	private ResultSet rs  = null;


	/*
	 * Columns Fields available on UI Page For all fields getters and setters are
	 * created below.
	 * 
	 */
	// Connect to all tables in Database
	public void getConnection() {
		try {
			String connectionString = ThreadTestDataMapper.getData(Thread.currentThread(), "bqConnectionURL");
			log.info("Connecting to " + connectionString);
			con = DriverManager.getConnection(connectionString);
			stmt = con.createStatement();
			log.info("Connecting2 to " + connectionString);
			
			
		} catch(Exception e) {
			System.out.println("Error Connecting : " + e.getMessage());
		}
	}
	
	public ResultSet executeQuery(String query) {

		try {
			String connectionString = ThreadTestDataMapper.getData(Thread.currentThread(), "bqConnectionURL");
			if(connectionString.contains("b-dlak")) {
				query = query.replace("pid-gousenaiq-dlak-res01", "prj-gousenaib-dlak-res01");
			} else if (connectionString.contains("q-dlak")) {
				query = query.replace("prj-gousenaib-dlak-res01", "pid-gousenaiq-dlak-res01");
			}
			
			log.info("Query : " + query);
			
			rs = stmt.executeQuery(query);
			log.info("Query Executed!! " + rs);
			return rs;
		} catch (Exception ex) {
			log.info("SQLException: " + ex.getMessage());
		}
		return rs;
	}
	
	public int executeUpdate(String query) {
		int rowsAffected=0;
		try {
			String connectionString = ThreadTestDataMapper.getData(Thread.currentThread(), "bqConnectionURL");
			if(connectionString.contains("b-dlak")) {
				query = query.replace("pid-gousenaiq-dlak-res01", "prj-gousenaib-dlak-res01");
			} else if (connectionString.contains("q-dlak")) {
				query = query.replace("prj-gousenaib-dlak-res01", "pid-gousenaiq-dlak-res01");
			}
			log.info("Waiting for maximum of 2 mins for BigQuery to update table data");
			rowsAffected = stmt.executeUpdate(query);
			log.info("Data updated");
			return rowsAffected;

		} catch (Exception ex) {
			log.info("SQLException: " + ex.getMessage());

		}

		return rowsAffected;
	}
	
	public String getAuthAccessToken() throws IOException {

        ProcessBuilder builder = new ProcessBuilder(
                    "cmd.exe", "/c", "cd \"" + System.getProperty("user.dir") + "\" && gcloud auth application-default print-access-token");
                builder.redirectErrorStream(true);
                Process p = builder.start();
                BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line;
                while (true) {
                    line = r.readLine();
                    if (line == null) { break; }
                   ThreadTestDataMapper.addData(Thread.currentThread(), "AccessToken",line);
                    
                    return line;
                    
                }
          
          return null;
      }

	
	public void closeConnection() throws SQLException {

		if (con != null) {
			con.close();
		}
		if (rs != null) {
			rs.close();
		}

	}

}
