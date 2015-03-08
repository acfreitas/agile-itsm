/*****************************************************************************
 * Contacts.java - 
 *
 * Copyright (c) 2014 Projeto citsmart (Contact: adenir.gomes@centralit.com.br)
 *
 * License:
 *****************************************************************************/
package br.com.centralit.nagios.livestatus.tables;

import java.util.Map;


	/**
 	* Class Contacts is the main class for obtain all columns of table "contacts" 
from a Livestatus TCP-socket/file status.dat.
 	*
 	* @author	Adenir Ribeiro Gomes
 	*/ 

	public class Contacts extends LiveStatusBase
	{
		/**
	 	* Constructor of table Contacts
	 	*
	 	* @param path = "tcp://host:port" File : where path is the path to the file
	 	*/
	public Contacts(String path)
	{
		super(path);
		initializeMaps();
		tableName = "contacts";
	}
		/**
	 	* create the map for all columns description of table Contacts. Key=column name, Value=column description
	 	*
	 	*/
	public final void initializeMaps()
	{
		mapComments.put("address1", "The additional field address1");
		mapComments.put("address2", "The additional field address2");
		mapComments.put("address3", "The additional field address3");
		mapComments.put("address4", "The additional field address4");
		mapComments.put("address5", "The additional field address5");
		mapComments.put("address6", "The additional field address6");
		mapComments.put("alias", "The full name of the contact");
		mapComments.put("can_submit_commands", "Wether the contact is allowed to submit commands (0/1)");
		mapComments.put("custom_variable_names", "A list of all custom variables of the contact");
		mapComments.put("custom_variable_values", "A list of the values of all custom variables of the contact");
		mapComments.put("custom_variables", "A dictionary of the custom variables");
		mapComments.put("email", "The email address of the contact");
		mapComments.put("host_notification_period", "The time period in which the contact will be notified about host problems");
		mapComments.put("host_notifications_enabled", "Wether the contact will be notified about host problems in general (0/1)");
		mapComments.put("in_host_notification_period", "Wether the contact is currently in his/her host notification period (0/1)");
		mapComments.put("in_service_notification_period", "Wether the contact is currently in his/her service notification period (0/1)");
		mapComments.put("modified_attributes", "A bitmask specifying which attributes have been modified");
		mapComments.put("modified_attributes_list", "A list of all modified attributes");
		mapComments.put("name", "The login name of the contact person");
		mapComments.put("pager", "The pager address of the contact");
		mapComments.put("service_notification_period", "The time period in which the contact will be notified about service problems");
		mapComments.put("service_notifications_enabled", "Wether the contact will be notified about service problems in general (0/1)");
	}
		/**
		 * The additional field address1
		 * @return returns the value of the "address1" column as string
		 */
	public String Address1() 
	{
		return getAsString("address1");
	}
		/**
		 * The additional field address2
		 * @return returns the value of the "address2" column as string
		 */
	public String Address2() 
	{
		return getAsString("address2");
	}
		/**
		 * The additional field address3
		 * @return returns the value of the "address3" column as string
		 */
	public String Address3() 
	{
		return getAsString("address3");
	}
		/**
		 * The additional field address4
		 * @return returns the value of the "address4" column as string
		 */
	public String Address4() 
	{
		return getAsString("address4");
	}
		/**
		 * The additional field address5
		 * @return returns the value of the "address5" column as string
		 */
	public String Address5() 
	{
		return getAsString("address5");
	}
		/**
		 * The additional field address6
		 * @return returns the value of the "address6" column as string
		 */
	public String Address6() 
	{
		return getAsString("address6");
	}
		/**
		 * The full name of the contact
		 * @return returns the value of the "alias" column as string
		 */
	public String Alias() 
	{
		return getAsString("alias");
	}
		/**
		 * Wether the contact is allowed to submit commands (0/1)
		 * @return returns the value of the "can_submit_commands" column as int
		 */
	public int Can_submit_commands() throws NumberFormatException
	{
		return getAsInt("can_submit_commands");
	}
		/**
		 * A list of all custom variables of the contact
		 * @return returns the value of the "custom_variable_names" column as list
		 */
	public String Custom_variable_names() 
	{
		return getAsList("custom_variable_names");
	}
		/**
		 * A list of the values of all custom variables of the contact
		 * @return returns the value of the "custom_variable_values" column as list
		 */
	public String Custom_variable_values() 
	{
		return getAsList("custom_variable_values");
	}
		/**
		 * A dictionary of the custom variables
		 * @return returns the value of the "custom_variables" column as dict
		 */
	public String Custom_variables() 
	{
		return getAsDict("custom_variables");
	}
		/**
		 * The email address of the contact
		 * @return returns the value of the "email" column as string
		 */
	public String Email() 
	{
		return getAsString("email");
	}
		/**
		 * The time period in which the contact will be notified about host problems
		 * @return returns the value of the "host_notification_period" column as string
		 */
	public String Host_notification_period() 
	{
		return getAsString("host_notification_period");
	}
		/**
		 * Wether the contact will be notified about host problems in general (0/1)
		 * @return returns the value of the "host_notifications_enabled" column as int
		 */
	public int Host_notifications_enabled() throws NumberFormatException
	{
		return getAsInt("host_notifications_enabled");
	}
		/**
		 * Wether the contact is currently in his/her host notification period (0/1)
		 * @return returns the value of the "in_host_notification_period" column as int
		 */
	public int In_host_notification_period() throws NumberFormatException
	{
		return getAsInt("in_host_notification_period");
	}
		/**
		 * Wether the contact is currently in his/her service notification period (0/1)
		 * @return returns the value of the "in_service_notification_period" column as int
		 */
	public int In_service_notification_period() throws NumberFormatException
	{
		return getAsInt("in_service_notification_period");
	}
		/**
		 * A bitmask specifying which attributes have been modified
		 * @return returns the value of the "modified_attributes" column as int
		 */
	public int Modified_attributes() throws NumberFormatException
	{
		return getAsInt("modified_attributes");
	}
		/**
		 * A list of all modified attributes
		 * @return returns the value of the "modified_attributes_list" column as list
		 */
	public String Modified_attributes_list() 
	{
		return getAsList("modified_attributes_list");
	}
		/**
		 * The login name of the contact person
		 * @return returns the value of the "name" column as string
		 */
	public String Name() 
	{
		return getAsString("name");
	}
		/**
		 * The pager address of the contact
		 * @return returns the value of the "pager" column as string
		 */
	public String Pager() 
	{
		return getAsString("pager");
	}
		/**
		 * The time period in which the contact will be notified about service problems
		 * @return returns the value of the "service_notification_period" column as string
		 */
	public String Service_notification_period() 
	{
		return getAsString("service_notification_period");
	}
		/**
		 * Wether the contact will be notified about service problems in general (0/1)
		 * @return returns the value of the "service_notifications_enabled" column as int
		 */
	public int Service_notifications_enabled() throws NumberFormatException
	{
		return getAsInt("service_notifications_enabled");
	}
		/**
	 	* create the map for all columns of table  Contacts. Key=column name, Value=column value
	 	*
   	* @param table LiveStatus table
   	* @param filter filter to applay for this table
	 	* @return Map<String, String>
	 	*/

	@Override
	public Map<String, String> asArrayString(String table, String filter) throws NumberFormatException {
		mapKeyValue.clear();
		setMapObjects(table, filter);

		addToHashtable("address1", (Address1()));
		addToHashtable("address2", (Address2()));
		addToHashtable("address3", (Address3()));
		addToHashtable("address4", (Address4()));
		addToHashtable("address5", (Address5()));
		addToHashtable("address6", (Address6()));
		addToHashtable("alias", (Alias()));
		addToHashtable("can_submit_commands", getAsString(Can_submit_commands()));
		addToHashtable("custom_variable_names", (Custom_variable_names()));
		addToHashtable("custom_variable_values", (Custom_variable_values()));
		addToHashtable("custom_variables", (Custom_variables()));
		addToHashtable("email", (Email()));
		addToHashtable("host_notification_period", (Host_notification_period()));
		addToHashtable("host_notifications_enabled", getAsString(Host_notifications_enabled()));
		addToHashtable("in_host_notification_period", getAsString(In_host_notification_period()));
		addToHashtable("in_service_notification_period", getAsString(In_service_notification_period()));
		addToHashtable("modified_attributes", getAsString(Modified_attributes()));
		addToHashtable("modified_attributes_list", (Modified_attributes_list()));
		addToHashtable("name", (Name()));
		addToHashtable("pager", (Pager()));
		addToHashtable("service_notification_period", (Service_notification_period()));
		addToHashtable("service_notifications_enabled", getAsString(Service_notifications_enabled()));
		return mapKeyValue;
	}
	}
