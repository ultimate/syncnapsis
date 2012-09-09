package com.syncnapsis.utils.dev;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public abstract class DBManager
{
	protected transient final Logger	logger	= LoggerFactory.getLogger(getClass());

	protected Properties				connectionProperties;

	protected String					driverClassName;
	protected String					username;
	protected String					url;
	protected String					password;
	protected String					dialect;
	protected String					dbName;

	public DBManager(Properties connectionProperties, String expectedDialect)
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

		Assert.isTrue(expectedDialect.equals(dialect), "dialect '" + dialect + "' does not match expected '" + expectedDialect + "'");
	}

	public void openConnection()
	{

	}

	public void closeConnection()
	{

	}

	public void dropAll()
	{

	}

	protected abstract String getQuery_getTables();

	protected abstract String getQuery_getTableConstraints();

	protected abstract String getQuery_getSequences();

	protected abstract String getQuery_createDatabase(String dbName, String dbOwner);

	protected abstract String getQuery_createUser(String user, String pw);

	protected abstract String getQuery_getUsers();

	protected abstract String getQuery_getDatabases();
}
