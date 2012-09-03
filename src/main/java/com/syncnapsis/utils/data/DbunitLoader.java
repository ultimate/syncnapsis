package com.syncnapsis.utils.data;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.DatabaseSequenceFilter;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.FilteredDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.filter.ExcludeTableFilter;
import org.dbunit.dataset.filter.ITableFilter;
import org.dbunit.dataset.xml.FlatDtdDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.dataset.xml.FlatXmlWriter;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.operation.DatabaseOperation;

/**
 * Klasse um über Dbunit die Datenbank zu exportieren, oder um Daten in die
 * Datenbank zu importieren
 * 
 * @author ultimate
 */
public class DbunitLoader
{
	/**
	 * Herstellen einer Dbunit Datenbankverbindung.
	 * 
	 * @param driverClass - Treiber-Klasse für die DB-Verbindung
	 * @param jdbcConnection - JDBC-URL für die DB-Verbindung
	 * @param username - Benutzername für die DB-Verbindung (kann null sein)
	 * @param password - Password für die DB-Verbindung (kann mit username
	 *            zusammen null sein)
	 * @return eine IDatabaseConnection für die Verwendung in Dbunit
	 * @throws ClassNotFoundException - wenn der Treiber nicht gefunden wird
	 * @throws SQLException - wenn keine Verbindung zur DB hergestellt werden
	 *             kann
	 * @throws DatabaseUnitException - wenn keine Dbunit Verbindung zur DB
	 *             hergestellt werden kann
	 */
	public static IDatabaseConnection getConnection(String driverClass, String jdbcConnection, String username, String password)
			throws ClassNotFoundException, SQLException, DatabaseUnitException
	{
		Class.forName(driverClass);
		Connection connection;
		if(username != null && password != null)
			connection = DriverManager.getConnection(jdbcConnection, username, password);
		else
			connection = DriverManager.getConnection(jdbcConnection);
		return new DatabaseConnection(connection);
	}

	/**
	 * Erstellt eine IDatabaseConnection mittels
	 * {@link DbunitLoader#getConnection(String, String, String, String)} und ruft
	 * {@link DbunitLoader#fullDatabaseImport(IDatabaseConnection, File, String...)} auf.
	 * 
	 * @param driverClass - Treiber-Klasse für die DB-Verbindung
	 * @param jdbcConnection - JDBC-URL für die DB-Verbindung
	 * @param username - Benutzername für die DB-Verbindung (kann null sein)
	 * @param password - Password für die DB-Verbindung (kann mit username
	 *            zusammen null sein)
	 * @param file - die Eingabeatei für den Import
	 * @param excludeTableNames - eine optionale Liste der ausgeschlossenen
	 *            Tabellennamen. Wenn sie leer ist werden alle Tabellen
	 *            behandelt.
	 * @throws ClassNotFoundException - wenn der Treiber nicht gefunden wird
	 * @throws SQLException - wenn keine Verbindung zur DB hergestellt werden
	 *             kann
	 * @throws DatabaseUnitException - wenn ein Fehler beim Import auftritt
	 * @throws IOException - wenn die Importdatei ungültig ist oder nicht
	 *             gelesen werden kann
	 */
	public static void fullDatabaseImport(String driverClass, String jdbcConnection, String username, String password, File file,
			String... excludeTableNames) throws ClassNotFoundException, SQLException, IOException, DatabaseUnitException
	{
		fullDatabaseImport(getConnection(driverClass, jdbcConnection, username, password), file, excludeTableNames);
	}

	/**
	 * Löscht alle existierenden Daten aus denen in der Eingabeatei enthaltenen
	 * Tabellen und fügt die neuen Daten ein.
	 * 
	 * @param connection - eine IDatabaseConnection für die Verwendung in Dbunit
	 * @param file - die Eingabeatei für den Import
	 * @param excludeTableNames - eine optionale Liste der ausgeschlossenen
	 *            Tabellennamen. Wenn sie leer ist werden alle Tabellen
	 *            behandelt.
	 * @throws IOException - wenn die Importdatei ungültig ist oder nicht
	 *             gelesen werden kann
	 * @throws DatabaseUnitException - wenn ein Fehler beim Import auftritt
	 * @throws SQLException - wenn keine Verbindung zur DB hergestellt werden
	 *             kann oder der Import schief läuft
	 */
	public static void fullDatabaseImport(IDatabaseConnection connection, File file, String... excludeTableNames) throws IOException,
			DatabaseUnitException, SQLException
	{
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));

		ITableFilter excludeFilter;
		if(excludeTableNames != null && excludeTableNames.length != 0)
			excludeFilter = new ExcludeTableFilter(excludeTableNames);
		else
			excludeFilter = new ExcludeTableFilter();
		FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
		builder.setColumnSensing(true);
		IDataSet excludeDataset = new FilteredDataSet(excludeFilter, builder.build(bis));
		IDataSet dataSet = new FilteredDataSet(new DatabaseSequenceFilter(connection), excludeDataset);

		bis.close();
		DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
	}

	/**
	 * Erstellt eine IDatabaseConnection mittels
	 * {@link DbunitLoader#getConnection(String, String, String, String)} und ruft
	 * {@link DbunitLoader#cleanDatabase(IDatabaseConnection, File, String...)} auf.
	 * 
	 * @param driverClass - Treiber-Klasse für die DB-Verbindung
	 * @param jdbcConnection - JDBC-URL für die DB-Verbindung
	 * @param username - Benutzername für die DB-Verbindung (kann null sein)
	 * @param password - Password für die DB-Verbindung (kann mit username
	 *            zusammen null sein)
	 * @param file - die Eingabeatei für den Import
	 * @param excludeTableNames - eine optionale Liste der ausgeschlossenen
	 *            Tabellennamen. Wenn sie leer ist werden alle Tabellen
	 *            behandelt.
	 * @throws IOException - wenn die Importdatei ungültig ist oder nicht
	 *             gelesen werden kann
	 * @throws DatabaseUnitException - wenn ein Fehler beim Import auftritt
	 * @throws SQLException - wenn keine Verbindung zur DB hergestellt werden
	 *             kann oder der Import schief läuft
	 */
	public static void cleanDatabase(String driverClass, String jdbcConnection, String username, String password, File file,
			String... excludeTableNames) throws IOException, DatabaseUnitException, SQLException
	{

	}

	/**
	 * Löscht alle existierenden Daten aus denen in der Eingabeatei enthaltenen
	 * Tabellen.
	 * 
	 * @param connection - eine IDatabaseConnection für die Verwendung in Dbunit
	 * @param file - die Eingabeatei für den Import
	 * @param excludeTableNames - eine optionale Liste der ausgeschlossenen
	 *            Tabellennamen. Wenn sie leer ist werden alle Tabellen
	 *            behandelt.
	 * @throws IOException - wenn die Importdatei ungültig ist oder nicht
	 *             gelesen werden kann
	 * @throws DatabaseUnitException - wenn ein Fehler beim Import auftritt
	 * @throws SQLException - wenn keine Verbindung zur DB hergestellt werden
	 *             kann oder der Import schief läuft
	 */
	public static void cleanDatabase(IDatabaseConnection connection, File file, String... excludeTableNames) throws IOException,
			DatabaseUnitException, SQLException
	{
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));

		ITableFilter excludeFilter;
		if(excludeTableNames != null && excludeTableNames.length != 0)
			excludeFilter = new ExcludeTableFilter(excludeTableNames);
		else
			excludeFilter = new ExcludeTableFilter();
		IDataSet excludeDataset = new FilteredDataSet(excludeFilter, new XmlDataSet(bis));
		IDataSet dataSet = new FilteredDataSet(new DatabaseSequenceFilter(connection), excludeDataset);

		bis.close();
		DatabaseOperation.DELETE_ALL.execute(connection, dataSet);
	}

	/**
	 * Erstellt eine IDatabaseConnection mittels
	 * {@link DbunitLoader#getConnection(String, String, String, String)} und ruft
	 * {@link DbunitLoader#fullDatabaseExport(IDatabaseConnection, File, String, String...)} auf.
	 * 
	 * @param driverClass - Treiber-Klasse für die DB-Verbindung
	 * @param jdbcConnection - JDBC-URL für die DB-Verbindung
	 * @param username - Benutzername für die DB-Verbindung (kann null sein)
	 * @param password - Password für die DB-Verbindung (kann mit username
	 *            zusammen null sein)
	 * @param file - die Ausgabedatei für den Export
	 * @param docTypeSystemId - eine optionale Angabe einer DTD
	 * @param excludeTableNames - eine optionale Liste der ausgeschlossenen
	 *            Tabellennamen. Wenn sie leer ist werden alle Tabellen
	 *            behandelt.
	 * @throws ClassNotFoundException - wenn der Treiber nicht gefunden wird
	 * @throws SQLException - wenn keine Verbindung zur DB hergestellt werden
	 *             kann
	 * @throws IOException - wenn die Exportdatei ungültig ist oder nicht
	 *             geschrieben werden kann
	 * @throws DatabaseUnitException - wenn keine Dbunit Verbindung zur DB
	 *             hergestellt werden kann
	 */
	public static void fullDatabaseExport(String driverClass, String jdbcConnection, String username, String password, File file,
			String docTypeSystemId, String... excludeTableNames) throws ClassNotFoundException, SQLException, IOException, DatabaseUnitException
	{
		fullDatabaseExport(getConnection(driverClass, jdbcConnection, username, password), file, docTypeSystemId, excludeTableNames);
	}

	/**
	 * Speichert alle in der Datenbank vorhandenen Datensätze im xml-Format in
	 * der Ausgabedatei.
	 * 
	 * @param connection - eine IDatabaseConnection für die Verwendung in Dbunit
	 * @param file - die Ausgabedatei für den Export
	 * @param docTypeSystemId - eine optionale Angabe einer DTD
	 * @param excludeTableNames - eine optionale Liste der ausgeschlossenen
	 *            Tabellennamen. Wenn sie leer ist werden alle Tabellen
	 *            behandelt.
	 * @throws DataSetException - wenn ein Fehler beim Export auftritt
	 * @throws SQLException - wenn keine Verbindung zur DB hergestellt werden
	 *             kann oder der Export schief läuft
	 * @throws IOException - wenn die Exportdatei ungültig ist oder nicht
	 *             geschrieben werden kann
	 */
	public static void fullDatabaseExport(IDatabaseConnection connection, File file, String docTypeSystemId, String... excludeTableNames)
			throws DataSetException, SQLException, IOException
	{
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));

		ITableFilter excludeFilter;
		if(excludeTableNames != null && excludeTableNames.length != 0)
			excludeFilter = new ExcludeTableFilter(excludeTableNames);
		else
			excludeFilter = new ExcludeTableFilter();
		IDataSet excludeDataset = new FilteredDataSet(excludeFilter, connection.createDataSet());
		IDataSet dataset = new FilteredDataSet(new DatabaseSequenceFilter(connection), excludeDataset);

		if(docTypeSystemId != null)
		{
			BufferedOutputStream dtdbos = new BufferedOutputStream(new FileOutputStream(file.getAbsoluteFile().getParent() + "\\" + docTypeSystemId));
			FlatDtdDataSet.write(dataset, dtdbos);
			dtdbos.flush();
			dtdbos.close();
		}

		FlatXmlWriter datasetWriter = new FlatXmlWriter(bos);
		datasetWriter.setDocType(docTypeSystemId);
		datasetWriter.setIncludeEmptyTable(true);
		datasetWriter.write(dataset);

		bos.flush();
		bos.close();
	}
}
