package com.syncnapsis.exec.dev;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.TreeMap;

import org.postgresql.util.PSQLException;

public abstract class DBSetup
{
	private static TreeMap<String, TreeMap<String, String>> queries;
	
	static
	{
		queries = new TreeMap<String, TreeMap<String, String>>();

		TreeMap<String, String> postgresql = new TreeMap<String, String>();
		postgresql.put("table", "SELECT table_name FROM information_schema.tables WHERE table_schema='public';");
		postgresql
				.put("constraint",
						"SELECT constraint_name, table_name FROM information_schema.table_constraints WHERE constraint_schema='public' AND constraint_type='FOREIGN KEY';");
		postgresql.put("sequence", "SELECT sequence_name FROM information_schema.sequences;");
		// TODO PEU rename database
		postgresql.put("createDB", "CREATE DATABASE \"peuDB\" WITH OWNER=\"peuADMIN\" ENCODING='UTF8' TABLESPACE=pg_default;");
		postgresql.put("createUSER", "CREATE ROLE \"{user}\" LOGIN ENCRYPTED PASSWORD '{pw}' NOSUPERUSER INHERIT CREATEDB CREATEROLE;");
		postgresql.put("updateUSER", "UPDATE pg_authid SET rolcatupdate=true WHERE OID=34605::oid;");
		postgresql.put("getUSERS", "SELECT usename FROM pg_catalogger.pg_user;");
		postgresql.put("getDBS", "SELECT datname FROM pg_catalogger.pg_database;");
		queries.put("jdbc:postgresql", postgresql);
	}
	
	public static void main(String[] args)
	{		
		// convert args zu lower-case-list
		List<String> argsList = new ArrayList<String>(args.length);
		for(String arg: args)
		{
			argsList.add(arg.toLowerCase());
		}
		
		try
		{
			copyConfigurationFilesFromMainProject();
			
			if(argsList.contains("-drop"))
			{
				executeDrop();
			}
			if(argsList.contains("-sequence"))
			{
				executeSequence();
			}
		}
		catch(Exception e)
		{
			printLine("ERROR: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void executeDrop() throws Exception
	{
		String fileName = "target/classes/jdbc.properties";
		File file = new File(fileName);
		if(!file.exists())
			throw new Exception("jdbc.properties existiert nicht!");
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream bis = new BufferedInputStream(fis);
		Properties prop = new Properties();
		prop.load(bis);
		String driverClassName = prop.getProperty("jdbc.driverClassName");
		String username = prop.getProperty("jdbc.username");
		String url = prop.getProperty("jdbc.url");
		String password = prop.getProperty("jdbc.password");
		String dialect = url.substring(0, url.indexOf("://"));
		String dbName = url.substring(url.lastIndexOf("/") + 1);

		if(dialect.equals("jdbc:postgresql"))
		{
			Class.forName(driverClassName);
			Connection conn = null;
			boolean dbExisted = true;
			boolean connError = true;
			try
			{
				conn = DriverManager.getConnection(url, username, password);
				connError = false;
			}
			catch(PSQLException e)
			{
				conn = DriverManager.getConnection(url.substring(0, url.lastIndexOf("/")), "postgres", "postgres");
				connError = true;
			}
			if(connError)
			{
				Statement st;
				ResultSet rs;

				boolean userFound = false;
				String getUsersQuery = getQuery(dialect, "getUSERS");
				st = conn.createStatement();
				rs = st.executeQuery(getUsersQuery);
				while(rs.next())
				{
					if(rs.getString(1).equals(username))
					{
						userFound = true;
						break;
					}
				}
				if(!userFound)
				{
					printLine("User existiert nicht.");

					String createUserQuery = getQuery(dialect, "createUSER");
					createUserQuery = createUserQuery.replace("{user}", username);
					createUserQuery = createUserQuery.replace("{pw}", password);
					st.addBatch(createUserQuery);
					int[] res = st.executeBatch();
					st.clearBatch();
					if(res[0] != Statement.EXECUTE_FAILED)
					{
						printLine("User wurde neu erstellt.");
					}
					else
					{
						throw new Exception("User konnte nicht neu erstellt werden.");
					}
				}

				boolean dbFound = false;
				String getDBsQuery = getQuery(dialect, "getDBS");
				st = conn.createStatement();
				rs = st.executeQuery(getDBsQuery);
				while(rs.next())
				{
					if(rs.getString(1).equals(dbName))
					{
						dbFound = true;
						break;
					}
				}
				if(!dbFound)
				{
					printLine("Datenbank existiert nicht.");

					String createDBQuery = getQuery(dialect, "createDB");
					st.addBatch(createDBQuery);
					int[] res = st.executeBatch();
					st.clearBatch();
					if(res[0] != Statement.EXECUTE_FAILED)
					{
						printLine("Datenbank wurde neu erstellt.");
					}
					else
					{
						throw new Exception("Datenbank konnte nicht neu erstellt werden.");
					}
					dbExisted = false;
				}
			}
			if(dbExisted)
			{
				dropElements(conn, "constraint", dialect, true);
				dropElements(conn, "table", dialect, false);
				dropElements(conn, "sequence", dialect, false);
			}
		}
		else
		{
			throw new Exception("Unbekannter Dialect: " + formatString(dialect, 20, ' ', true));
		}
	}

	public static void dropElements(Connection conn, String identifier, String dialect, boolean tableDependent) throws Exception
	{
		Statement st = conn.createStatement();
		Statement stDrop = conn.createStatement();
		ResultSet rs;
		String nameQuery = getQuery(dialect, identifier);
		String query;

		int elementsDropped = 0;
		int elementsTotal = 0;

		rs = st.executeQuery(nameQuery);
		while(rs.next())
		{
			elementsTotal++;
			query = "drop " + identifier + " " + rs.getString(1) + ";";
			if(tableDependent)
				query = "alter table " + rs.getString(2) + " " + query;
			stDrop.addBatch(query);
		}
		int[] rsDrop = stDrop.executeBatch();
		for(int i : rsDrop)
		{
			if(i != Statement.EXECUTE_FAILED)
				elementsDropped++;
		}
		String title = identifier.substring(0, 1).toUpperCase() + identifier.substring(1) + "s";
		printLine(formatString(title + " dropped: ", 30, ' ', false) + formatString(elementsDropped + "/" + elementsTotal, 10, ' ', true));
		if(elementsDropped != elementsTotal)
			throw new Exception("not all elements dropped!");
	}
	
	public static void executeSequence() throws Exception
	{
		String fileName = "target/classes/jdbc.properties";
		File file = new File(fileName);
		if(!file.exists())
			throw new Exception("jdbc.properties existiert nicht!");
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream bis = new BufferedInputStream(fis);
		Properties prop = new Properties();
		prop.load(bis);
		String driverClassName = prop.getProperty("jdbc.driverClassName");
		String username = prop.getProperty("jdbc.username");
		String url = prop.getProperty("jdbc.url");
		String password = prop.getProperty("jdbc.password");
		String dialect = url.substring(0, url.indexOf("://"));
		
		Class.forName(driverClassName);
		Connection conn = null;
		
		conn = DriverManager.getConnection(url, username, password);
		
		updateSequence(conn, dialect);
	}
		
	public static void updateSequence(Connection conn, String dialect) throws Exception
	{
		Statement st = conn.createStatement();
		Statement stGet = conn.createStatement();
		ResultSet rs;
		ResultSet rsGet;		
		String nameQuery = getQuery(dialect, "table");
		String query;

		int tablesScanned = 0;
		int tablesWithId = 0;
		int max = -1;
		int curr;

		rs = st.executeQuery(nameQuery);
		while(rs.next())
		{
			tablesScanned++;
			query = "select max(id) from " + rs.getString(1) + ";";
			try
			{
				stGet.execute(query);
				rsGet = stGet.getResultSet();
				rsGet.next();
				curr = rsGet.getInt(1);
				if(curr > max)
					max = curr;
				tablesWithId++;
			}
			catch(Exception e)
			{
				//ignore
			}
		}
		if(max <= 0)
			max = 1;
		query = "select setval('hibernate_sequence', " + max + ")";
		stGet.execute(query);
		query = "select nextval('hibernate_sequence')";
		stGet.execute(query);
		rsGet = stGet.getResultSet();
		rsGet.next();
		curr = rsGet.getInt(1);
		
		printLine(formatString("tables scanned: ", 30, ' ', false) + formatString("" + tablesScanned, 10, ' ', true));
		printLine(formatString("tables with id: ", 30, ' ', false) + formatString("" + tablesWithId, 10, ' ', true));
		printLine(formatString("maximum id is: ", 30, ' ', false) + formatString("" + max, 10, ' ', true));
		printLine(formatString("sequence set to: ", 30, ' ', false) + formatString("" + curr, 10, ' ', true));
		if(max < 0)
			throw new Exception("could not update sequence!");
	}
	
	public static String getQuery(String dialect, String identifier)
	{
		return queries.get(dialect).get(identifier);
	}
}
