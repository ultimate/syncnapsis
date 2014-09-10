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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PostgreSQLDBManager extends DBManager
{
	public static final String DIALECT = "jdbc:postgresql";

	public PostgreSQLDBManager(Properties connectionProperties) throws ClassNotFoundException
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

//	@Override
//	protected String getQuery_createDatabase(String dbName, String dbOwner)
//	{
//		return "CREATE DATABASE \"" + dbName + "\" WITH OWNER=\"" + dbOwner + "\" ENCODING='UTF8' TABLESPACE=pg_default;";
//	}
//
//	@Override
//	protected String getQuery_createUser(String user, String pw)
//	{
//		return "CREATE ROLE \"" + user + "\" LOGIN ENCRYPTED PASSWORD '" + pw + "' NOSUPERUSER INHERIT CREATEDB CREATEROLE;";
//	}
//
//	@Override
//	protected String getQuery_getUsers()
//	{
//		return "SELECT usename FROM pg_catalogger.pg_user;";
//	}
//
//	@Override
//	protected String getQuery_getDatabases()
//	{
//		return "SELECT datname FROM pg_catalogger.pg_database;";
//	}

	public static void main(String[] args)
	{
		String properties = DEFAULT_PROPERTIES;
		
		// parse args
		List<String> argsList = new ArrayList<String>(args.length);
		for(String arg : args)
		{
			if(arg.endsWith(".properties"))
			{
				properties = arg;
			}
			else
			{
				argsList.add(arg.toLowerCase());
			}
		}
		
		try
		{
			execute(argsList, new PostgreSQLDBManager(DBManager.loadProperties(properties)));
		}
		catch(ClassNotFoundException e)
		{
			System.out.println("could not load jdbc-driver-class: " + e.getMessage());
			e.printStackTrace();
		}
		catch(IOException e)
		{
			System.out.println("could not read properties-file: " + properties);
			e.printStackTrace();
		}
	}
}
