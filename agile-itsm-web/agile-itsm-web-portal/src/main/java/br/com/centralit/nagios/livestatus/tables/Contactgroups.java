/*****************************************************************************
 * Contactgroups.java - 
 *
 * Copyright (c) 2014 Projeto citsmart (Contact: adenir.gomes@centralit.com.br)
 *
 * License:
 *****************************************************************************/
package br.com.centralit.nagios.livestatus.tables;

import java.util.Map;


	/**
 	* Class Contactgroups is the main class for obtain all columns of table "contactgroups" 
from a Livestatus TCP-socket/file status.dat.
 	*
 	* @author	Adenir Ribeiro Gomes
 	*/ 

	public class Contactgroups extends LiveStatusBase
	{
		/**
	 	* Constructor of table Contactgroups
	 	*
	 	* @param path = "tcp://host:port" File : where path is the path to the file
	 	*/
	public Contactgroups(String path)
	{
		super(path);
		initializeMaps();
		tableName = "contactgroups";
	}
		/**
	 	* create the map for all columns description of table Contactgroups. Key=column name, Value=column description
	 	*
	 	*/
	public final void initializeMaps()
	{
		mapComments.put("alias", "The alias of the contactgroup");
		mapComments.put("members", "A list of all members of this contactgroup");
		mapComments.put("name", "The name of the contactgroup");
	}
		/**
		 * The alias of the contactgroup
		 * @return returns the value of the "alias" column as string
		 */
	public String Alias() 
	{
		return getAsString("alias");
	}
		/**
		 * A list of all members of this contactgroup
		 * @return returns the value of the "members" column as list
		 */
	public String Members() 
	{
		return getAsList("members");
	}
		/**
		 * The name of the contactgroup
		 * @return returns the value of the "name" column as string
		 */
	public String Name() 
	{
		return getAsString("name");
	}
		/**
	 	* create the map for all columns of table  Contactgroups. Key=column name, Value=column value
	 	*
   	* @param table LiveStatus table
   	* @param filter filter to applay for this table
	 	* @return Map<String, String>
	 	*/

	@Override
	public Map<String, String> asArrayString(String table, String filter) throws NumberFormatException {
		mapKeyValue.clear();
		setMapObjects(table, filter);

		addToHashtable("alias", (Alias()));
		addToHashtable("members", (Members()));
		addToHashtable("name", (Name()));
		return mapKeyValue;
	}
	}
