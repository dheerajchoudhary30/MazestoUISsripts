package com.mazesto.mz.automation.utilities;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.mazesto.automation.listeners.Reporter;


public class DatabaseUtilities {
	
	private static Logger log  = LogManager.getLogger("DatabaseUtilities");
	Utilities utilities = new Utilities();
	
	/**
	 * @param query - SQL query to fetch data
	 * @return - map - ( key - data Columns, Values -  DB values )
	 */
	
	public Map<String, String> getdataFromBigQuerySQLDB(String query) {

		Map<String, String> dbResultSetMap = new LinkedHashMap<String, String>();
		BQConnectionFactory connectionFactory2 = new BQConnectionFactory();
		try {
			connectionFactory2.getConnection();
			ResultSet rs = connectionFactory2.executeQuery(query);
			ResultSetMetaData rsmd = rs.getMetaData();
			//rs.beforeFirst();
			while (rs.next()) {

				for (int i = 1; i <= rsmd.getColumnCount(); i++) {

					int type = rsmd.getColumnType(i);
					if (type == Types.BIT || type == Types.BIGINT || type == Types.SMALLINT || type == Types.INTEGER) {
						dbResultSetMap.put(rsmd.getColumnLabel(i).replace("_"," "), String.valueOf((rs.getLong(i))));
					} else if (type == Types.DECIMAL) {
						dbResultSetMap.put(rsmd.getColumnLabel(i).replace("_"," "), String.valueOf((rs.getBigDecimal(i))));

					}

					else if (type == Types.DATE || type == Types.TIMESTAMP) {
						String pattern = "MM/dd/yyyy";
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
						if (rs.getDate(i) != null) {
							dbResultSetMap.put(rsmd.getColumnLabel(i).replace("_"," "), simpleDateFormat.format(rs.getDate(i)));
						} else {
							dbResultSetMap.put(rsmd.getColumnLabel(i).replace("_"," "), String.valueOf(rs.getInt(i)));
						}
					} else if (type == Types.CHAR || type == Types.VARCHAR) {

						dbResultSetMap.put(rsmd.getColumnLabel(i).replace("_"," "), String.valueOf((rs.getString(i))));
				
					}else {
						dbResultSetMap.put(rsmd.getColumnLabel(i).replace("_"," "), String.valueOf((rs.getString(i))));
					}
				}
			}
			

		} catch (Exception e) {

			log.info(e.getMessage());
			Reporter.addStepLog(e.getMessage());

		} finally {
			try {
				connectionFactory2.closeConnection();
			} catch (SQLException e) {
				log.info(e.getMessage());
			}
		}
		return dbResultSetMap;
	}
	/*public Map<String, String> getdataFromBigQuerySQLDB(String query) {

		Map<String, String> dbResultSetMap = new LinkedHashMap<String, String>();
		BQConnectionFactory connectionFactory2 = new BQConnectionFactory();
		try {
			connectionFactory2.getConnection();
			ResultSet rs = connectionFactory2.executeQuery(query);
			ResultSetMetaData rsmd = rs.getMetaData();
			//rs.beforeFirst();
			while (rs.next()) {

				for (int i = 1; i <= rsmd.getColumnCount(); i++) {

					int type = rsmd.getColumnType(i);
					if (type == Types.BIT || type == Types.BIGINT || type == Types.SMALLINT || type == Types.INTEGER) {
						dbResultSetMap.put(rsmd.getColumnLabel(i), String.valueOf((rs.getLong(i))));
					} else if (type == Types.DECIMAL) {
						dbResultSetMap.put(rsmd.getColumnLabel(i), String.valueOf((rs.getBigDecimal(i))));

					}

					else if (type == Types.DATE || type == Types.TIMESTAMP) {
						String pattern = "MM/dd/yyyy";
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
						if (rs.getDate(i) != null) {
							dbResultSetMap.put(rsmd.getColumnLabel(i), simpleDateFormat.format(rs.getDate(i)));
						} else {
							dbResultSetMap.put(rsmd.getColumnLabel(i), String.valueOf(rs.getInt(i)));
						}
					} else if (type == Types.CHAR || type == Types.VARCHAR) {

						dbResultSetMap.put(rsmd.getColumnLabel(i), String.valueOf((rs.getString(i))));
				
					}else {
						dbResultSetMap.put(rsmd.getColumnLabel(i), String.valueOf((rs.getString(i))));
					}
				}
			}
			dbResultSetMap = utilities.getAutomationUtilities().trimAndRemoveAllSpacialCharKeyMap(dbResultSetMap);

		} catch (Exception e) {

			log.info(e.getMessage());
			Reporter.addStepLog(e.getMessage());

		} finally {
			try {
				connectionFactory2.closeConnection();
			} catch (SQLException e) {
				log.info(e.getMessage());
			}
		}
		return dbResultSetMap;
	}*/

	public boolean updatedataInMySQLDB(String query,String envTypeCDEOrNCDE ) {

		MySQLConnectionFactory mysqldb = new MySQLConnectionFactory(envTypeCDEOrNCDE);
		try {

			mysqldb.setConnection();
			int rowsAffected = mysqldb.executeUpdate(query);
			System.out.println("Update Query Executed!! & Number of Rows updated: " +rowsAffected );
            return (rowsAffected!=0?true:false); 
		} catch (Exception e) {

			System.out.println(e.getMessage());
			Reporter.addStepLog(e.getMessage());
			
		} finally {
			try {
				mysqldb.closeConnection();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return false;
	}
	
	
	public boolean updatedataInBigQuerySQLDB(String query) {

		BQConnectionFactory connectionFactory2 = new BQConnectionFactory();
		
		try {
			connectionFactory2.getConnection();
			int rowsAffected = connectionFactory2.executeUpdate(query);
			System.out.println("Update Query Executed!! & Number of Rows updated: " +rowsAffected );
            return (rowsAffected!=0?true:false); 
		} catch (Exception e) {
			System.out.println(e.getMessage());
			Reporter.addStepLog(e.getMessage());
			
		} finally {
			try {
				connectionFactory2.closeConnection();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return false;
	}
	
	/**
	 * This method will get one row in a list
	 * @param query
	 * @param envTypeCDEOrNCDE
	 * @return
	 */
	public List<String> getDataListFromMySQLDB(String query,String envTypeCDEOrNCDE) {
		List<String> dbResultSetList = new ArrayList<String>();

		MySQLConnectionFactory mysqldb = new MySQLConnectionFactory(envTypeCDEOrNCDE);
		try {

			log.info("Query Used : " + query);
			mysqldb.setConnection();
			ResultSet rs = mysqldb.executeQuery(query);
			log.info("Query Executed!! ");

			ResultSetMetaData rsmd = rs.getMetaData();

			rs.beforeFirst();
			while (rs.next()) {
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					log.debug(rs.getString(i));
					dbResultSetList.add(rs.getString(i));
				}
			}
		} catch (Exception e) {

			log.info(e.getMessage());
			Reporter.addStepLog(e.getMessage());

		} finally {
			try {
				mysqldb.closeConnection();
			} catch (SQLException e) {
				log.info(e.getMessage());
			}
		}
		return dbResultSetList;
	}
	
	public int getExpectedTotalCountofRecords(String queryBasic,String envTypeCDEOrNCDE) {

		MySQLConnectionFactory mysqldb = new MySQLConnectionFactory(envTypeCDEOrNCDE);
		try {

			mysqldb.setConnection();
			ResultSet rs = mysqldb.executeQuery(queryBasic);
			log.debug("Query Executed!! ");
			rs.next();
			Integer number = rs.getInt(1);

			log.debug("Count from DB " + number);

			return number;
		} catch (Exception e) {
			log.debug(e.getMessage());
			return 0;
		} finally {
			try {
				mysqldb.closeConnection();
			} catch (SQLException e) {
				log.debug(e.getMessage());
			}
		}
	}
	
	/**
	 * @param query - SQL query to fetch data
	 * @return - map - ( key - data Columns, Values -  DB values )
	 */
	public Map<String, String> getDataMapFromMySQLDB(String query,String envTypeCDEOrNCDE) {

		Map<String, String> dbResultSetMap = new LinkedHashMap<String, String>();
		MySQLConnectionFactory mysqldb = new MySQLConnectionFactory(envTypeCDEOrNCDE);
		try {

			mysqldb.setConnection();
			ResultSet rs = mysqldb.executeQuery(query);
			ResultSetMetaData rsmd = rs.getMetaData();
			rs.beforeFirst();
			while (rs.next()) {

				for (int i = 1; i <= rsmd.getColumnCount(); i++) {

					int type = rsmd.getColumnType(i);
					if (type == Types.BIT || type == Types.BIGINT || type == Types.SMALLINT || type == Types.INTEGER) {
						dbResultSetMap.put(rsmd.getColumnLabel(i), String.valueOf((rs.getLong(i))).trim());
					} else if (type == Types.DECIMAL) {
						dbResultSetMap.put(rsmd.getColumnLabel(i), String.valueOf((rs.getBigDecimal(i))).trim());

					}

					else if (type == Types.DATE || type == Types.TIMESTAMP) {
						String pattern = "MM/dd/yyyy";
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
						if (rs.getDate(i) != null) {
							dbResultSetMap.put(rsmd.getColumnLabel(i), simpleDateFormat.format(rs.getDate(i)).trim());
						} else {
							dbResultSetMap.put(rsmd.getColumnLabel(i), String.valueOf(rs.getInt(i)).trim());
						}
					} else if (type == Types.CHAR || type == Types.VARCHAR) {

						dbResultSetMap.put(rsmd.getColumnLabel(i), String.valueOf((rs.getString(i))).trim());
				
					}else {
						dbResultSetMap.put(rsmd.getColumnLabel(i), String.valueOf((rs.getString(i))).trim());
					}
				}
			}
			
			//dbResultSetMap = utilities.getAutomationUtilities().trimAndRemoveAllSpacialCharKeyMap(dbResultSetMap);

		} catch (Exception e) {

			log.info(e.getMessage());
			Reporter.addStepLog(e.getMessage());

		} finally {
			try {
				mysqldb.closeConnection();
			} catch (SQLException e) {
				log.info(e.getMessage());
			}
		}
		return dbResultSetMap;
	}
	
	
	/**
	 * 
	 * Use this method where keys are labels.
	 * 
	 * @param uiValues
	 * @param query
	 * @return True if UI and DB data matches
	 */
	public boolean compareDatafromUIandMySQLDB(Map<String, String> uiValues, String query, String envTypeCDEOrNCDE ) {

		Map<String, String> dbResultSetMap = new LinkedHashMap<String, String>();
		Map<String,String> valuesMismatch=new HashMap<>();
		MySQLConnectionFactory mysqldb = new MySQLConnectionFactory(envTypeCDEOrNCDE);
		try {

			mysqldb.setConnection();
			ResultSet rs = mysqldb.executeQuery(query + " LIMIT 1");
			log.debug("Query Executed!! ");

			ResultSetMetaData rsmd = rs.getMetaData();

			rs.beforeFirst();
			while (rs.next()) {

				for (int i = 1; i <= rsmd.getColumnCount(); i++) {

					int type = rsmd.getColumnType(i);
					if (type == Types.BIT || type == Types.BIGINT || type == Types.SMALLINT || type == Types.INTEGER) {
						dbResultSetMap.put(rsmd.getColumnLabel(i), String.valueOf((rs.getLong(i))));
					} else if (type == Types.DECIMAL) {
						dbResultSetMap.put(rsmd.getColumnLabel(i), String.valueOf((rs.getBigDecimal(i))));

					}

					else if (type == Types.DATE || type == Types.TIMESTAMP) {
						String pattern = "MM/dd/yyyy";
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
						if (rs.getDate(i) != null) {
							dbResultSetMap.put(rsmd.getColumnLabel(i), simpleDateFormat.format(rs.getDate(i)));
						} else {
							dbResultSetMap.put(rsmd.getColumnLabel(i), String.valueOf(rs.getInt(i)));
						}
					} else if (type == Types.CHAR || type == Types.VARCHAR) {

						dbResultSetMap.put(rsmd.getColumnLabel(i), String.valueOf((rs.getString(i))));
					} else {
						dbResultSetMap.put(rsmd.getColumnLabel(i), String.valueOf((rs.getString(i))));
					}
				}
			}
			
			dbResultSetMap = utilities.getAutomationUtilities().trimAndRemoveAllSpacialCharKeyMap(dbResultSetMap);
			uiValues = utilities.getAutomationUtilities().trimAndRemoveAllSpacialCharKeyMap(uiValues);
			Iterator<Entry<String, String>> dbResultSetMapItr = dbResultSetMap.entrySet().iterator();
			Iterator<Entry<String, String>> uiValuesItr = uiValues.entrySet().iterator();
			log.info("DB:"+ dbResultSetMap);
			log.info("DB Values: " + dbResultSetMap.size());
			Reporter.addStepLog("DB Values: " + dbResultSetMap);
			log.info("Ui Values Map: " + uiValues.size());
			Reporter.addStepLog("Ui Values Map: " + uiValues);

			if (uiValues.isEmpty() || dbResultSetMap.isEmpty()) {
				return false;
			} else if (dbResultSetMap.size() >= uiValues.size()) {
				while (uiValuesItr.hasNext()) {
					Entry<String, String> pair = uiValuesItr.next();
					if (dbResultSetMap.containsKey(pair.getKey())) {
						if (!dbResultSetMap.get(pair.getKey()).equalsIgnoreCase(pair.getValue())) {
							log.info("dbResultSetMapKey :" + pair.getKey() + " has value : "
									+ dbResultSetMap.get(pair.getKey()) + " while ");
							log.info("uiMapkey :" + pair.getKey() + " has value : " + pair.getValue());
							valuesMismatch.put(pair.getKey(), pair.getValue());
							//return false;
						}
					} else {
						log.info("Key : " + pair.getKey() + "Not present in database Map.");
						return false;
					}
				}

			} else if (dbResultSetMap.size() < uiValues.size()) {
				while (dbResultSetMapItr.hasNext()) {
					Entry<String, String> pair = dbResultSetMapItr.next();
					log.debug("dbkey :" + pair.getKey());
					Reporter.addStepLog("dbkey :" + pair.getKey());

					if (uiValues.containsKey(pair.getKey())) {

						if (!(uiValues.get(pair.getKey()).equalsIgnoreCase(pair.getValue().trim()))) {
							log.info("dbResultSetMapKey :" + pair.getKey() + " has value : " + pair.getValue()
									+ " while ");
							log.info("uiMapkey :" + pair.getKey() + " has value : " + uiValues.get(pair.getKey()));
							return false;
						}
					} else {
						log.info("Key : " + pair.getKey() + " Not present in UI Map.");
						return false;
					}
				}

			}
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;

		} finally {
			try {
				mysqldb.closeConnection();
			} catch (SQLException e) {
				log.info(e.getMessage());
			}
		}
		if(valuesMismatch.size()!=0) {
			log.info("DB Values not matched with UI: " +  valuesMismatch);
			return false;
		}
			
		else
			return true;
		//return true;
	}
	
	/**
	 * 
	 * @param query - in format of key and value ( ex - select key, value from
	 *              table.. )
	 * @return map - result from DB
	 */
	public Map<String, String> getMapFromMySQLDBWithFirstColumnAsKey(String query,String envTypeCDEOrNCDE) {
		Map<String, String> dbResultSetMap = new LinkedHashMap<String, String>();
		String key = "";
		String value = "";
		String master = "";

		MySQLConnectionFactory mysqldb = new MySQLConnectionFactory(envTypeCDEOrNCDE);
		try {

			mysqldb.setConnection();
			ResultSet rs = mysqldb.executeQuery(query);
			log.info(query + ": Query Executed!! ");

			ResultSetMetaData rsmd = rs.getMetaData();

			rs.beforeFirst();

			while (rs.next()) {

				for (int i = 1; i <= 2; i++) {

					int type = rsmd.getColumnType(i);
					if (type == Types.BIT || type == Types.BIGINT || type == Types.SMALLINT || type == Types.INTEGER) {
						master = String.valueOf((rs.getLong(i)));
					} else if (type == Types.DECIMAL) {
						master = String.valueOf((rs.getBigDecimal(i)));
					}

					else if (type == Types.DATE || type == Types.TIMESTAMP) {
						String pattern = "MM/dd/yyyy";
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
						if (rs.getDate(i) != null) {
							master = simpleDateFormat.format(rs.getDate(i));
						} else {
							master = String.valueOf(rs.getInt(i));
						}
					} else if (type == Types.CHAR || type == Types.VARCHAR) {

						master = String.valueOf((rs.getString(i)));
					} else {
						master = String.valueOf((rs.getString(i)));
					}

					if (i == 1) {
						key = master;
					} else {
						value = master;
					}
				}
				dbResultSetMap.put(key, value);

			}

			dbResultSetMap = utilities.getAutomationUtilities().trimAndRemoveAllSpacialCharKeyMap(dbResultSetMap);
		} catch (Exception e) {

			log.debug(e.getMessage());
			Reporter.addStepLog(e.getMessage());

		} finally {
			try {
				mysqldb.closeConnection();
			} catch (SQLException e) {
				log.debug(e.getMessage());
			}
		}
		return dbResultSetMap;

	}


	public List<String> getDataListFromBigQuery(String query,String envTypeCDEOrNCDE) {
		List<String> dbResultSetList = new ArrayList<String>();

		BQConnectionFactory connectionFactory2 = new BQConnectionFactory();
		try {
			connectionFactory2.getConnection();
			ResultSet rs = connectionFactory2.executeQuery(query);
			log.info("Query Executed!! ");

			ResultSetMetaData rsmd = rs.getMetaData();

			//rs.beforeFirst();
			while (rs.next()) {
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					log.debug(rs.getString(i));
					dbResultSetList.add(rs.getString(i));
				}
			}
		} catch (Exception e) {

			log.info(e.getMessage());
			Reporter.addStepLog(e.getMessage());

		} finally {
			try {
				connectionFactory2.closeConnection();
			} catch (SQLException e) {
				log.info(e.getMessage());
			}
		}
		return dbResultSetList;
	}
	
	/**
	 * This method will get multiple rows in lists
	 * @param query
	 * @param envTypeCDEOrNCDE
	 * @return List of lists of rows
	 */
	public List<List<String>> getMultipleRowsDataListFromMySQLDB(String query,String envTypeCDEOrNCDE) {
		
		List<List<String>> dbResultSetList = new ArrayList<List<String>>();

		MySQLConnectionFactory mysqldb = new MySQLConnectionFactory(envTypeCDEOrNCDE);
		try {

			log.info("Query Used : " + query);
			mysqldb.setConnection();
			ResultSet rs = mysqldb.executeQuery(query);
			log.info("Query Executed!! ");

			ResultSetMetaData rsmd = rs.getMetaData();

			rs.beforeFirst();
			while (rs.next()) {
				List<String> singleRowList = new ArrayList<String>();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					log.debug(rs.getString(i));
					singleRowList.add(rs.getString(i));
				}
				/*tempsingleRowList.addAll(singleRowList);*/
				dbResultSetList.add(singleRowList);
				//log.info("dbResultSetList  "+dbResultSetList);
				//tempsingleRowList.addAll(singleRowList);
				//singleRowList.removeAll(singleRowList);
				//log.info("AfterdbResultSetList  "+dbResultSetList);
			}
		} catch (Exception e) {

			log.info(e.getMessage());
			Reporter.addStepLog(e.getMessage());

		} finally {
			try {
				mysqldb.closeConnection();
			} catch (SQLException e) {
				log.info(e.getMessage());
			}
		}
		return dbResultSetList;
	}
	
}
	