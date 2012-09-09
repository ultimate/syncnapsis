package com.syncnapsis.utils.dev;

import java.util.Properties;

public class PostgreSQLDBManager extends DBManager
{
	public static final String DIALECT = "jdbc:postgresql";

	public PostgreSQLDBManager(Properties connectionProperties)
	{
		super(connectionProperties, DIALECT);
	}

	@Override
	protected String getQuery_getTables()
	{
		return "SELECT table_name FROM information_schema.tables WHERE table_schema='public';";
	}

	@Override
	protected String getQuery_getTableConstraints()
	{
		return "SELECT constraint_name, table_name FROM information_schema.table_constraints WHERE constraint_schema='public' AND constraint_type='FOREIGN KEY';";
	}

	@Override
	protected String getQuery_getSequences()
	{
		return "SELECT sequence_name FROM information_schema.sequences;";
	}

	@Override
	protected String getQuery_createDatabase(String dbName, String dbOwner)
	{
		return "CREATE DATABASE \"" + dbName + "\" WITH OWNER=\"" + dbOwner + "\" ENCODING='UTF8' TABLESPACE=pg_default;";
	}

	@Override
	protected String getQuery_createUser(String user, String pw)
	{
		return "CREATE ROLE \"" + user + "\" LOGIN ENCRYPTED PASSWORD '" + pw + "' NOSUPERUSER INHERIT CREATEDB CREATEROLE;";
	}

	@Override
	protected String getQuery_getUsers()
	{
		return "SELECT usename FROM pg_catalogger.pg_user;";
	}

	@Override
	protected String getQuery_getDatabases()
	{
		return "SELECT datname FROM pg_catalogger.pg_database;";
	}

	// ("updateUSER", "UPDATE pg_authid SET rolcatupdate=true WHERE OID=34605::oid;");
}
