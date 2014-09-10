/**
 * Syncnapsis Framework - Copyright (c) 2012 ultimate
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation; either version
 * 3 of the License, or any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MECHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Plublic License along with this program;
 * if not, see <http://www.gnu.org/licenses/>.
 */
package com.syncnapsis.utils.dev;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.syncnapsis.utils.PropertiesUtil;
import com.syncnapsis.utils.StringUtil;

public abstract class DBManager
{
	public static final String			DEFAULT_PROPERTIES	= "jdbc.properties";
	
	protected transient final Logger	logger				= LoggerFactory.getLogger(getClass());

	protected Properties				connectionProperties;

	protected String					driverClassName;
	protected String					username;
	protected String					url;
	protected String					password;
	protected String					dialect;
	protected String					dbName;
	protected String					sequence;

	public DBManager(Properties connectionProperties, String expectedDialect) throws ClassNotFoundException
	{
		super();
		Assert.notNull(connectionProperties, "connectionProperties must not be null!");
		this.connectionProperties = connectionProperties;

		this.driverClassName = connectionProperties.getProperty("jdbc.driverClassName");
		this.username = connectionProperties.getProperty("jdbc.username");
		this.url = connectionProperties.getProperty("jdbc.url");
		this.password = connectionProperties.getProperty("jdbc.password");
		this.dialect = url.substring(0, url.indexOf("://"));
		this.dbName = url.substring(url.lastIndexOf("/") + 1);

		this.sequence = connectionProperties.getProperty("sequence", "hibernate_sequence");

		Assert.isTrue(expectedDialect.equals(dialect), "dialect '" + dialect + "' does not match expected '" + expectedDialect + "'");

		Class.forName(driverClassName);
	}

	protected Connection openConnection() throws SQLException
	{
		return DriverManager.getConnection(url, username, password);
	}

	public void dropAll()
	{
		try
		{
			Connection conn = openConnection();
			logger.info("dropping database...");

			dropElements(conn, "constraint", getQuery_getTableConstraints(), true);
			dropElements(conn, "table", getQuery_getTables(), false);
			dropElements(conn, "sequence", getQuery_getSequences(), false);

			conn.close();
		}
		catch(SQLException e)
		{
			logger.error("connection error:" + e);
		}
	}

	protected boolean dropElements(Connection conn, String identifier, String nameQuery, boolean tableDependent)
	{
		try
		{
			Statement st = conn.createStatement();
			Statement stDrop = conn.createStatement();
			ResultSet rs;
			String query;

			int elementsDropped = 0;
			int elementsTotal = 0;

			rs = st.executeQuery(nameQuery);
			while(rs.next())
			{
				elementsTotal++;
				query = getQuery_drop(identifier, rs.getString(1), tableDependent ? rs.getString(2) : null);
				stDrop.addBatch(query);
			}
			int[] rsDrop = stDrop.executeBatch();
			for(int i : rsDrop)
			{
				if(i != Statement.EXECUTE_FAILED)
					elementsDropped++;
			}

			logger.info(StringUtil.fillup(identifier + "s dropped: ", 30, ' ', false)
					+ StringUtil.fillup(elementsDropped + "/" + elementsTotal, 10, ' ', true));

			if(elementsDropped != elementsTotal)
				throw new Exception("not all elements dropped!");

			return true;
		}
		catch(SQLException e)
		{
			logger.error("SQLException occurred", e);
			return false;
		}
		catch(Exception e)
		{
			logger.error("Unexpected Exception occurred", e);
			return false;
		}
	}

	public void updateSequence()
	{
		try
		{
			Connection conn = openConnection();

			logger.info("updating sequence...");

			updateSequence(conn);

			conn.close();
		}
		catch(SQLException e)
		{
			logger.error("connection error:" + e);
		}
	}

	protected boolean updateSequence(Connection conn)
	{
		try
		{
			Statement st = conn.createStatement();
			Statement stGet = conn.createStatement();
			ResultSet rs;
			ResultSet rsGet;
			String nameQuery = getQuery_getTables();
			String query;

			int tablesScanned = 0;
			int tablesWithId = 0;
			int max = -1;
			int curr;

			rs = st.executeQuery(nameQuery);
			while(rs.next())
			{
				tablesScanned++;
				query = getQuery_getMaxId(rs.getString(1));
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
					// ignore
				}
			}
			if(max <= 0)
				max = 1;
			query = getQuery_setSequenceValue(max);
			stGet.execute(query);
			query = getQuery_getSequenceValue();
			stGet.execute(query);
			rsGet = stGet.getResultSet();
			rsGet.next();
			curr = rsGet.getInt(1);

			logger.info(StringUtil.fillup("tables scanned: ", 30, ' ', false) + StringUtil.fillup("" + tablesScanned, 10, ' ', true));
			logger.info(StringUtil.fillup("tables with id: ", 30, ' ', false) + StringUtil.fillup("" + tablesWithId, 10, ' ', true));
			logger.info(StringUtil.fillup("maximum id is: ", 30, ' ', false) + StringUtil.fillup("" + max, 10, ' ', true));
			logger.info(StringUtil.fillup("sequence set to: ", 30, ' ', false) + StringUtil.fillup("" + curr, 10, ' ', true));

			if(max < 0)
				throw new Exception("could not update sequence!");

			return true;
		}
		catch(SQLException e)
		{
			logger.error("SQLException occurred", e);
			return false;
		}
		catch(Exception e)
		{
			logger.error("Unexpected Exception occurred", e);
			return false;
		}
	}

	// public void createUser() throws Exception
	// {
	//
	// }

	// public void createDatabase() throws Exception
	// {
	//
	// }

	protected abstract String getQuery_getTables();

	protected abstract String getQuery_getTableConstraints();

	protected abstract String getQuery_getSequences();

	// protected abstract String getQuery_createDatabase(String dbName, String dbOwner);
	//
	// protected abstract String getQuery_createUser(String user, String pw);
	//
	// protected abstract String getQuery_getUsers();
	//
	// protected abstract String getQuery_getDatabases();

	protected String getQuery_drop(String identifier, String name, String table)
	{

		return (table != null ? "alter table " + table + " " : "") + "drop " + identifier + " " + name + ";";
	}

	protected String getQuery_getMaxId(String table)
	{
		return "select max(id) from " + table + ";";
	}

	protected String getQuery_setSequenceValue(int value)
	{
		return "select setval('" + sequence + "', " + value + ")";
	}

	protected String getQuery_getSequenceValue()
	{
		return "select nextval('" + sequence + "')";
	}

	protected static void execute(List<String> argsList, DBManager dbm)
	{
		dbm.logger.info(argsList.size() + " operations to perform: " + argsList);

		if(argsList.contains("-drop"))
		{
			dbm.dropAll();
		}
		if(argsList.contains("-sequence"))
		{
			dbm.updateSequence();
		}
	}
	
	public static Properties loadProperties(String properties) throws IOException
	{
		File propertiesFile = new File(properties);
		if(!propertiesFile.exists())
			propertiesFile = new File("classpath*:" + properties);
		if(!propertiesFile.exists())
			propertiesFile = new File("target/classes/" + properties);
		if(!propertiesFile.exists())
			propertiesFile = new File("target/test-classes/" + properties);
		if(!propertiesFile.exists())
			propertiesFile = new File(DBManager.class.getResource("/" + properties).getFile());
		if(!propertiesFile.exists())
			throw new IOException("Neither '" + properties + "' nor 'target/classes/" + properties + "' nor 'target/test-classes/" + properties + "' found!");
		return PropertiesUtil.loadProperties(propertiesFile); 
	}
}
