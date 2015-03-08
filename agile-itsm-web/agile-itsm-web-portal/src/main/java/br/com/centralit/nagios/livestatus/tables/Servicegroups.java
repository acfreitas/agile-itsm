/*****************************************************************************
 * Servicegroups.java - 
 *
 * Copyright (c) 2014 Projeto citsmart (Contact: adenir.gomes@centralit.com.br)
 *
 * License:
 *****************************************************************************/
package br.com.centralit.nagios.livestatus.tables;

import java.util.Map;


	/**
 	* Class Servicegroups is the main class for obtain all columns of table "servicegroups" 
from a Livestatus TCP-socket/file status.dat.
 	*
 	* @author	Adenir Ribeiro Gomes
 	*/ 

	public class Servicegroups extends LiveStatusBase
	{
		/**
	 	* Constructor of table Servicegroups
	 	*
	 	* @param path = "tcp://host:port" File : where path is the path to the file
	 	*/
	public Servicegroups(String path)
	{
		super(path);
		initializeMaps();
		tableName = "servicegroups";
	}
		/**
	 	* create the map for all columns description of table Servicegroups. Key=column name, Value=column description
	 	*
	 	*/
	public final void initializeMaps()
	{
		mapComments.put("action_url", "An optional URL to custom notes or actions on the service group");
		mapComments.put("alias", "An alias of the service group");
		mapComments.put("members", "A list of all members of the service group as host/service pairs");
		mapComments.put("members_with_state", "A list of all members of the service group with state and has_been_checked");
		mapComments.put("name", "The name of the service group");
		mapComments.put("notes", "Optional additional notes about the service group");
		mapComments.put("notes_url", "An optional URL to further notes on the service group");
		mapComments.put("num_services", "The total number of services in the group");
		mapComments.put("num_services_crit", "The number of services in the group that are CRIT");
		mapComments.put("num_services_hard_crit", "The number of services in the group that are CRIT");
		mapComments.put("num_services_hard_ok", "The number of services in the group that are OK");
		mapComments.put("num_services_hard_unknown", "The number of services in the group that are UNKNOWN");
		mapComments.put("num_services_hard_warn", "The number of services in the group that are WARN");
		mapComments.put("num_services_ok", "The number of services in the group that are OK");
		mapComments.put("num_services_pending", "The number of services in the group that are PENDING");
		mapComments.put("num_services_unknown", "The number of services in the group that are UNKNOWN");
		mapComments.put("num_services_warn", "The number of services in the group that are WARN");
		mapComments.put("worst_service_state", "The worst soft state of all of the groups services (OK <= WARN <= UNKNOWN <= CRIT)");
	}
		/**
		 * An optional URL to custom notes or actions on the service group
		 * @return returns the value of the "action_url" column as string
		 */
	public String Action_url() 
	{
		return getAsString("action_url");
	}
		/**
		 * An alias of the service group
		 * @return returns the value of the "alias" column as string
		 */
	public String Alias() 
	{
		return getAsString("alias");
	}
		/**
		 * A list of all members of the service group as host/service pairs
		 * @return returns the value of the "members" column as list
		 */
	public String Members() 
	{
		return getAsList("members");
	}
		/**
		 * A list of all members of the service group with state and has_been_checked
		 * @return returns the value of the "members_with_state" column as list
		 */
	public String Members_with_state() 
	{
		return getAsList("members_with_state");
	}
		/**
		 * The name of the service group
		 * @return returns the value of the "name" column as string
		 */
	public String Name() 
	{
		return getAsString("name");
	}
		/**
		 * Optional additional notes about the service group
		 * @return returns the value of the "notes" column as string
		 */
	public String Notes() 
	{
		return getAsString("notes");
	}
		/**
		 * An optional URL to further notes on the service group
		 * @return returns the value of the "notes_url" column as string
		 */
	public String Notes_url() 
	{
		return getAsString("notes_url");
	}
		/**
		 * The total number of services in the group
		 * @return returns the value of the "num_services" column as int
		 */
	public int Num_services() throws NumberFormatException
	{
		return getAsInt("num_services");
	}
		/**
		 * The number of services in the group that are CRIT
		 * @return returns the value of the "num_services_crit" column as int
		 */
	public int Num_services_crit() throws NumberFormatException
	{
		return getAsInt("num_services_crit");
	}
		/**
		 * The number of services in the group that are CRIT
		 * @return returns the value of the "num_services_hard_crit" column as int
		 */
	public int Num_services_hard_crit() throws NumberFormatException
	{
		return getAsInt("num_services_hard_crit");
	}
		/**
		 * The number of services in the group that are OK
		 * @return returns the value of the "num_services_hard_ok" column as int
		 */
	public int Num_services_hard_ok() throws NumberFormatException
	{
		return getAsInt("num_services_hard_ok");
	}
		/**
		 * The number of services in the group that are UNKNOWN
		 * @return returns the value of the "num_services_hard_unknown" column as int
		 */
	public int Num_services_hard_unknown() throws NumberFormatException
	{
		return getAsInt("num_services_hard_unknown");
	}
		/**
		 * The number of services in the group that are WARN
		 * @return returns the value of the "num_services_hard_warn" column as int
		 */
	public int Num_services_hard_warn() throws NumberFormatException
	{
		return getAsInt("num_services_hard_warn");
	}
		/**
		 * The number of services in the group that are OK
		 * @return returns the value of the "num_services_ok" column as int
		 */
	public int Num_services_ok() throws NumberFormatException
	{
		return getAsInt("num_services_ok");
	}
		/**
		 * The number of services in the group that are PENDING
		 * @return returns the value of the "num_services_pending" column as int
		 */
	public int Num_services_pending() throws NumberFormatException
	{
		return getAsInt("num_services_pending");
	}
		/**
		 * The number of services in the group that are UNKNOWN
		 * @return returns the value of the "num_services_unknown" column as int
		 */
	public int Num_services_unknown() throws NumberFormatException
	{
		return getAsInt("num_services_unknown");
	}
		/**
		 * The number of services in the group that are WARN
		 * @return returns the value of the "num_services_warn" column as int
		 */
	public int Num_services_warn() throws NumberFormatException
	{
		return getAsInt("num_services_warn");
	}
		/**
		 * The worst soft state of all of the groups services (OK <= WARN <= UNKNOWN <= CRIT)
		 * @return returns the value of the "worst_service_state" column as int
		 */
	public int Worst_service_state() throws NumberFormatException
	{
		return getAsInt("worst_service_state");
	}
		/**
	 	* create the map for all columns of table  Servicegroups. Key=column name, Value=column value
	 	*
   	* @param table LiveStatus table
   	* @param filter filter to applay for this table
	 	* @return Map<String, String>
	 	*/

	@Override
	public Map<String, String> asArrayString(String table, String filter) throws NumberFormatException {
		mapKeyValue.clear();
		setMapObjects(table, filter);

		addToHashtable("action_url", (Action_url()));
		addToHashtable("alias", (Alias()));
		addToHashtable("members", (Members()));
		addToHashtable("members_with_state", (Members_with_state()));
		addToHashtable("name", (Name()));
		addToHashtable("notes", (Notes()));
		addToHashtable("notes_url", (Notes_url()));
		addToHashtable("num_services", getAsString(Num_services()));
		addToHashtable("num_services_crit", getAsString(Num_services_crit()));
		addToHashtable("num_services_hard_crit", getAsString(Num_services_hard_crit()));
		addToHashtable("num_services_hard_ok", getAsString(Num_services_hard_ok()));
		addToHashtable("num_services_hard_unknown", getAsString(Num_services_hard_unknown()));
		addToHashtable("num_services_hard_warn", getAsString(Num_services_hard_warn()));
		addToHashtable("num_services_ok", getAsString(Num_services_ok()));
		addToHashtable("num_services_pending", getAsString(Num_services_pending()));
		addToHashtable("num_services_unknown", getAsString(Num_services_unknown()));
		addToHashtable("num_services_warn", getAsString(Num_services_warn()));
		addToHashtable("worst_service_state", getAsString(Worst_service_state()));
		return mapKeyValue;
	}
	}
