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

import com.syncnapsis.utils.HibernateUtil;
import com.syncnapsis.utils.PropertiesUtil;

public abstract class DataGenerator
{
	protected transient final Logger	logger	= LoggerFactory.getLogger(getClass());
	
	protected String projectDirectory;
//	new String[] { "app_userrole", "app_userrole_fallback", "playerrole", "playerrole_fallback", "parameter",
//			"guiwindowparams", "guiaction", "messenger", "menuitem", "menuparent", "news", "authority", "authoritygroup" }
	protected String[] excludeTableList;
	
	public DataGenerator(String projectDirectory, String[] excludeTableList)
	{
		this.projectDirectory = projectDirectory;
		this.excludeTableList = excludeTableList;
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
			DbunitLoader.fullDatabaseExport(connection, tmp, docTypeSystemId, excludeTableList);

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
				DbunitLoader.fullDatabaseExport(connection, dummy, docTypeSystemId, excludeTableList);

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
				DbunitLoader.fullDatabaseExport(connection, generated, docTypeSystemId, excludeTableList);
			}

			// Wiederherstellung der veränderten Tabellen
			logger.info("restoring dbunit-database-backup...");
			DbunitLoader.fullDatabaseImport(connection, tmp, excludeTableList);
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
