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
package com.syncnapsis.utils.data;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.syncnapsis.utils.HibernateUtil;
import com.syncnapsis.utils.PropertiesUtil;

public abstract class DataGenerator implements InitializingBean
{
	protected transient final Logger	logger	= LoggerFactory.getLogger(getClass());

	public static final boolean			flat	= true;

	/**
	 * The extended random number generator used to generate random values
	 */
	protected final ExtendedRandom		random	= new ExtendedRandom("syncnapsis".hashCode());

	protected String					projectDirectory;
	protected String[]					excludeTableList;

	// "app_userrole"
	// "app_userrole_fallback"
	// "playerrole"
	// "playerrole_fallback"
	// "parameter"
	// "guiwindowparams"
	// "guiaction"
	// "messenger"
	// "menuitem"
	// "menuparent"
	// "news"
	// "authority"
	// "authoritygroup"

	public String getProjectDirectory()
	{
		return projectDirectory;
	}

	public void setProjectDirectory(String projectDirectory)
	{
		this.projectDirectory = projectDirectory;
	}

	public String[] getExcludeTableList()
	{
		return excludeTableList;
	}

	public void setExcludeTableList(String[] excludeTableList)
	{
		this.excludeTableList = excludeTableList;
	}

	@Override
	public void afterPropertiesSet() throws Exception
	{
		Assert.notNull(projectDirectory, "projectDirectory must not be null!");
		Assert.notNull(excludeTableList, "excludeTableList must not be null!");
	}

	public void generateData()
	{
		File tmp = null;
		IDatabaseConnection connection = null;
		try
		{
			// Files
			tmp = new File("tmp" + System.currentTimeMillis() + ".xml");
			File tables = new File(projectDirectory + "src/test/resources/data-tables.xml");
			File dummy = new File(projectDirectory + "src/test/resources/data-dummy.xml");
			File generated = new File(projectDirectory + "src/test/resources/data-generated.xml");
			File hibernatePropertiesFile = new File("target/classes/jdbc.properties");
			File testDataPropertiesFile = new File("target/classes/testData.properties");
			String docTypeSystemId = "flatdataset.dtd";

			// Properties laden
			logger.info("loading properties...");
			Properties hibernateProperties = PropertiesUtil.loadProperties(hibernatePropertiesFile);
			Properties testDataProperties = PropertiesUtil.loadProperties(testDataPropertiesFile);
			String driverClass = hibernateProperties.getProperty("hibernate.connection.driver_class");
			String jdbcConnection = hibernateProperties.getProperty("hibernate.connection.url");
			String username = hibernateProperties.getProperty("hibernate.connection.username");
			String password = hibernateProperties.getProperty("hibernate.connection.password");

			// Dbunit Connection herstellen
			logger.info("creating dbunit-connection...");
			connection = DbunitLoader.getConnection(driverClass, jdbcConnection, username, password);
			connection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new PostgresqlDataTypeFactory());

			// Backup der zu verändernden Tabellen
			logger.info("creating dbunit-database-backup...");
			DbunitLoader.fullDatabaseExport(connection, tmp, docTypeSystemId, flat, excludeTableList);

			// Leeren der zu verändernden Tabellen
			logger.info("deleting database content...");
			DbunitLoader.cleanDatabase(connection, tables, excludeTableList);

			initHibernate(hibernateProperties);

			if("1".equals(testDataProperties.getProperty("dummyData.on")))
			{
				// Erzeugen Dummydata
				logger.info("creating dummydata...");
				Transaction transaction1 = HibernateUtil.currentSession().beginTransaction();
				generateDummyData(Integer.parseInt(testDataProperties.getProperty("dummyData.amount")));
				transaction1.commit();

				// Exportieren der veränderten Tabellen mit Dbunit
				logger.info("exporting testdata...");
				DbunitLoader.fullDatabaseExport(connection, dummy, docTypeSystemId, flat, excludeTableList);

				// Leeren der zu verändernden Tabellen
				logger.info("deleting database content...");
				DbunitLoader.cleanDatabase(connection, tables, excludeTableList);
			}
			if("1".equals(testDataProperties.getProperty("testData.on")))
			{
				// Erzeugen Testdata
				logger.info("creating testdata...");
				Transaction transaction2 = HibernateUtil.currentSession().beginTransaction();
				generateTestData(testDataProperties);
				transaction2.commit();

				// Exportieren der veränderten Tabellen mit Dbunit
				logger.info("exporting testdata...");
				DbunitLoader.fullDatabaseExport(connection, generated, docTypeSystemId, flat, excludeTableList);
			}

			// Wiederherstellung der veränderten Tabellen
			logger.info("restoring dbunit-database-backup...");
			DbunitLoader.fullDatabaseImport(connection, tmp, flat, excludeTableList);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		catch(DataSetException e)
		{
			e.printStackTrace();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(DatabaseUnitException e)
		{
			e.printStackTrace();
		}
		finally
		{
			// Löschen der Backup-Datei
			logger.info("deleting dbunit-database-backup-file...");
			tmp.delete();

			// Schließen der Verbindung
			if(connection != null)
			{
				try
				{
					connection.close();
				}
				catch(Exception e)
				{

				}
			}
		}
	}

	private void initHibernate(Properties additionalProperties)
	{
		logger.info("initializing hibernate SessionFactory...");

		Configuration configuration = new Configuration().configure();

		additionalProperties.put("hibernate.current_session_context_class", "thread");

		configuration.addProperties(additionalProperties);

		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();

		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);

		HibernateUtil.getInstance().setSessionFactory(sessionFactory);
	}

	public abstract void generateDummyData(int amount);

	public abstract void generateTestData(Properties testDataProperties);
}
