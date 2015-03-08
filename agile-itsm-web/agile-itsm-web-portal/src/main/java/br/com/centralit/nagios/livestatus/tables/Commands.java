/*****************************************************************************
 * Commands.java - 
 *
 * Copyright (c) 2014 Projeto citsmart (Contact: adenir.gomes@centralit.com.br)
 *
 * License:
 *****************************************************************************/
package br.com.centralit.nagios.livestatus.tables;

import java.util.Map;


/**
 * Class Commands is the main class for obtain all columns of table "commands" from a Livestatus TCP-socket/file status.dat.
 * 
 * @author Adenir Ribeiro Gomes
 */

public class Commands extends LiveStatusBase {
	/**
	 * Constructor of table Commands
	 * 
	 * @param path
	 *            = "tcp://host:port" File : where path is the path to the file
	 */
	public Commands(String path) {
		super(path);
		initializeMaps();
		tableName = "commands";
	}

	/**
	 * create the map for all columns description of table Commands. Key=column name, Value=column description
	 * 
	 */
	public final void initializeMaps() {
		mapComments.put("line", "The shell command line");
		mapComments.put("name", "The name of the command");
	}

	/**
	 * The shell command line
	 * 
	 * @return returns the value of the "line" column as string
	 */
	public String Line() {
		return getAsString("line");
	}

	/**
	 * The name of the command
	 * 
	 * @return returns the value of the "name" column as string
	 */
	public String Name() {
		return getAsString("name");
	}

	/**
	 * create the map for all columns of table Commands. Key=column name, Value=column value
	 * 
	 * @param table
	 *            LiveStatus table
	 * @param filter
	 *            filter to applay for this table
	 * @return Map<String, String>
	 */

	@Override
	public Map<String, String> asArrayString(String table, String filter) throws NumberFormatException {
		mapKeyValue.clear();
		setMapObjects(table, filter);

		addToHashtable("line", (Line()));
		addToHashtable("name", (Name()));
		return mapKeyValue;
	}
}
