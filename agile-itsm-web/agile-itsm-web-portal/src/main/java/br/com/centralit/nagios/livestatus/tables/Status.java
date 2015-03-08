/*****************************************************************************
 * Status.java - 
 *
 * Copyright (c) 2014 Projeto citsmart (Contact: adenir.gomes@centralit.com.br)
 *
 * License:
 *****************************************************************************/
package br.com.centralit.nagios.livestatus.tables;

import java.util.Date;
import java.util.Map;


	/**
 	* Class Status is the main class for obtain all columns of table "status" 
from a Livestatus TCP-socket/file status.dat.
 	*
 	* @author	Adenir Ribeiro Gomes
 	*/ 

	public class Status extends LiveStatusBase
	{
		/**
	 	* Constructor of table Status
	 	*
	 	* @param path = "tcp://host:port" File : where path is the path to the file
	 	*/
	public Status(String path)
	{
		super(path);
		initializeMaps();
		tableName = "status";
	}
		/**
	 	* create the map for all columns description of table Status. Key=column name, Value=column description
	 	*
	 	*/
	public final void initializeMaps()
	{
		mapComments.put("accept_passive_host_checks", "Whether passive host checks are accepted in general (0/1)");
		mapComments.put("accept_passive_service_checks", "Whether passive service checks are activated in general (0/1)");
		mapComments.put("cached_log_messages", "The current number of log messages MK Livestatus keeps in memory");
		mapComments.put("check_external_commands", "Whether Nagios checks for external commands at its command pipe (0/1)");
		mapComments.put("check_host_freshness", "Whether host freshness checking is activated in general (0/1)");
		mapComments.put("check_service_freshness", "Whether service freshness checking is activated in general (0/1)");
		mapComments.put("connections", "The number of client connections to Livestatus since program start");
		mapComments.put("connections_rate", "The averaged number of new client connections to Livestatus per second");
		mapComments.put("enable_event_handlers", "Whether event handlers are activated in general (0/1)");
		mapComments.put("enable_flap_detection", "Whether flap detection is activated in general (0/1)");
		mapComments.put("enable_notifications", "Whether notifications are enabled in general (0/1)");
		mapComments.put("execute_host_checks", "Whether host checks are executed in general (0/1)");
		mapComments.put("execute_service_checks", "Whether active service checks are activated in general (0/1)");
		mapComments.put("external_command_buffer_max", "The maximum number of slots used in the external command buffer (placeholder)");
		mapComments.put("external_command_buffer_slots", "The size of the buffer for the external commands (placeholder)");
		mapComments.put("external_command_buffer_usage", "The number of slots in use of the external command buffer (placeholder)");
		mapComments.put("external_commands", "The number of external commands since program start");
		mapComments.put("external_commands_rate", "the averaged number of external commands per second");
		mapComments.put("forks", "The number of process creations since program start");
		mapComments.put("forks_rate", "the averaged number of forks checks per second");
		mapComments.put("host_checks", "The number of host checks since program start");
		mapComments.put("host_checks_rate", "the averaged number of host checks per second");
		mapComments.put("interval_length", "The default interval length from nagios.cfg");
		mapComments.put("last_command_check", "The time of the last check for a command as UNIX timestamp (placeholder)");
		mapComments.put("last_log_rotation", "Time time of the last log file rotation");
		mapComments.put("livecheck_overflows", "The number of times a check could not be executed because now livecheck helper was free");
		mapComments.put("livecheck_overflows_rate", "The number of livecheck overflows per second");
		mapComments.put("livechecks", "The number of checks executed via livecheck");
		mapComments.put("livechecks_rate", "The averaged number of livechecks executes per second");
		mapComments.put("livestatus_active_connections", "The current number of active connections to MK Livestatus");
		mapComments.put("livestatus_queued_connections", "The current number of queued connections to MK Livestatus (that wait for a free thread)");
		mapComments.put("livestatus_threads", "The maximum number of connections to MK Livestatus that can be handled in parallel");
		mapComments.put("livestatus_version", "The version of the MK Livestatus module");
		mapComments.put("log_messages", "The number of new log messages since program start");
		mapComments.put("log_messages_rate", "the averaged number of new log messages per second");
		mapComments.put("nagios_pid", "The process ID of the Nagios main process");
		mapComments.put("neb_callbacks", "The number of NEB call backs since program start");
		mapComments.put("neb_callbacks_rate", "The averaged number of NEB call backs per second");
		mapComments.put("num_hosts", "The total number of hosts");
		mapComments.put("num_services", "The total number of services");
		mapComments.put("obsess_over_hosts", "Whether Nagios will obsess over host checks (0/1)");
		mapComments.put("obsess_over_services", "Whether Nagios will obsess over service checks and run the ocsp_command (0/1)");
		mapComments.put("process_performance_data", "Whether processing of performance data is activated in general (0/1)");
		mapComments.put("program_start", "The time of the last program start as UNIX timestamp");
		mapComments.put("program_version", "The version of the monitoring daemon");
		mapComments.put("requests", "The number of requests to Livestatus since program start");
		mapComments.put("requests_rate", "The averaged number of request to Livestatus per second");
		mapComments.put("service_checks", "The number of completed service checks since program start");
		mapComments.put("service_checks_rate", "The averaged number of service checks per second");
	}
		/**
		 * Whether passive host checks are accepted in general (0/1)
		 * @return returns the value of the "accept_passive_host_checks" column as int
		 */
	public int Accept_passive_host_checks() throws NumberFormatException
	{
		return getAsInt("accept_passive_host_checks");
	}
		/**
		 * Whether passive service checks are activated in general (0/1)
		 * @return returns the value of the "accept_passive_service_checks" column as int
		 */
	public int Accept_passive_service_checks() throws NumberFormatException
	{
		return getAsInt("accept_passive_service_checks");
	}
		/**
		 * The current number of log messages MK Livestatus keeps in memory
		 * @return returns the value of the "cached_log_messages" column as int
		 */
	public int Cached_log_messages() throws NumberFormatException
	{
		return getAsInt("cached_log_messages");
	}
		/**
		 * Whether Nagios checks for external commands at its command pipe (0/1)
		 * @return returns the value of the "check_external_commands" column as int
		 */
	public int Check_external_commands() throws NumberFormatException
	{
		return getAsInt("check_external_commands");
	}
		/**
		 * Whether host freshness checking is activated in general (0/1)
		 * @return returns the value of the "check_host_freshness" column as int
		 */
	public int Check_host_freshness() throws NumberFormatException
	{
		return getAsInt("check_host_freshness");
	}
		/**
		 * Whether service freshness checking is activated in general (0/1)
		 * @return returns the value of the "check_service_freshness" column as int
		 */
	public int Check_service_freshness() throws NumberFormatException
	{
		return getAsInt("check_service_freshness");
	}
		/**
		 * The number of client connections to Livestatus since program start
		 * @return returns the value of the "connections" column as int
		 */
	public int Connections() throws NumberFormatException
	{
		return getAsInt("connections");
	}
		/**
		 * The averaged number of new client connections to Livestatus per second
		 * @return returns the value of the "connections_rate" column as float
		 */
	public float Connections_rate() throws NumberFormatException
	{
		return getAsFloat("connections_rate");
	}
		/**
		 * Whether event handlers are activated in general (0/1)
		 * @return returns the value of the "enable_event_handlers" column as int
		 */
	public int Enable_event_handlers() throws NumberFormatException
	{
		return getAsInt("enable_event_handlers");
	}
		/**
		 * Whether flap detection is activated in general (0/1)
		 * @return returns the value of the "enable_flap_detection" column as int
		 */
	public int Enable_flap_detection() throws NumberFormatException
	{
		return getAsInt("enable_flap_detection");
	}
		/**
		 * Whether notifications are enabled in general (0/1)
		 * @return returns the value of the "enable_notifications" column as int
		 */
	public int Enable_notifications() throws NumberFormatException
	{
		return getAsInt("enable_notifications");
	}
		/**
		 * Whether host checks are executed in general (0/1)
		 * @return returns the value of the "execute_host_checks" column as int
		 */
	public int Execute_host_checks() throws NumberFormatException
	{
		return getAsInt("execute_host_checks");
	}
		/**
		 * Whether active service checks are activated in general (0/1)
		 * @return returns the value of the "execute_service_checks" column as int
		 */
	public int Execute_service_checks() throws NumberFormatException
	{
		return getAsInt("execute_service_checks");
	}
		/**
		 * The maximum number of slots used in the external command buffer (placeholder)
		 * @return returns the value of the "external_command_buffer_max" column as int
		 */
	public int External_command_buffer_max() throws NumberFormatException
	{
		return getAsInt("external_command_buffer_max");
	}
		/**
		 * The size of the buffer for the external commands (placeholder)
		 * @return returns the value of the "external_command_buffer_slots" column as int
		 */
	public int External_command_buffer_slots() throws NumberFormatException
	{
		return getAsInt("external_command_buffer_slots");
	}
		/**
		 * The number of slots in use of the external command buffer (placeholder)
		 * @return returns the value of the "external_command_buffer_usage" column as int
		 */
	public int External_command_buffer_usage() throws NumberFormatException
	{
		return getAsInt("external_command_buffer_usage");
	}
		/**
		 * The number of external commands since program start
		 * @return returns the value of the "external_commands" column as int
		 */
	public int External_commands() throws NumberFormatException
	{
		return getAsInt("external_commands");
	}
		/**
		 * the averaged number of external commands per second
		 * @return returns the value of the "external_commands_rate" column as float
		 */
	public float External_commands_rate() throws NumberFormatException
	{
		return getAsFloat("external_commands_rate");
	}
		/**
		 * The number of process creations since program start
		 * @return returns the value of the "forks" column as int
		 */
	public int Forks() throws NumberFormatException
	{
		return getAsInt("forks");
	}
		/**
		 * the averaged number of forks checks per second
		 * @return returns the value of the "forks_rate" column as float
		 */
	public float Forks_rate() throws NumberFormatException
	{
		return getAsFloat("forks_rate");
	}
		/**
		 * The number of host checks since program start
		 * @return returns the value of the "host_checks" column as int
		 */
	public int Host_checks() throws NumberFormatException
	{
		return getAsInt("host_checks");
	}
		/**
		 * the averaged number of host checks per second
		 * @return returns the value of the "host_checks_rate" column as float
		 */
	public float Host_checks_rate() throws NumberFormatException
	{
		return getAsFloat("host_checks_rate");
	}
		/**
		 * The default interval length from nagios.cfg
		 * @return returns the value of the "interval_length" column as int
		 */
	public int Interval_length() throws NumberFormatException
	{
		return getAsInt("interval_length");
	}
		/**
		 * The time of the last check for a command as UNIX timestamp (placeholder)
		 * @return returns the value of the "last_command_check" column as time
		 */
	public Date Last_command_check() throws NumberFormatException
	{
		return getAsTime("last_command_check");
	}
		/**
		 * Time time of the last log file rotation
		 * @return returns the value of the "last_log_rotation" column as time
		 */
	public Date Last_log_rotation() throws NumberFormatException
	{
		return getAsTime("last_log_rotation");
	}
		/**
		 * The number of times a check could not be executed because now livecheck helper was free
		 * @return returns the value of the "livecheck_overflows" column as int
		 */
	public int Livecheck_overflows() throws NumberFormatException
	{
		return getAsInt("livecheck_overflows");
	}
		/**
		 * The number of livecheck overflows per second
		 * @return returns the value of the "livecheck_overflows_rate" column as float
		 */
	public float Livecheck_overflows_rate() throws NumberFormatException
	{
		return getAsFloat("livecheck_overflows_rate");
	}
		/**
		 * The number of checks executed via livecheck
		 * @return returns the value of the "livechecks" column as int
		 */
	public int Livechecks() throws NumberFormatException
	{
		return getAsInt("livechecks");
	}
		/**
		 * The averaged number of livechecks executes per second
		 * @return returns the value of the "livechecks_rate" column as float
		 */
	public float Livechecks_rate() throws NumberFormatException
	{
		return getAsFloat("livechecks_rate");
	}
		/**
		 * The current number of active connections to MK Livestatus
		 * @return returns the value of the "livestatus_active_connections" column as int
		 */
	public int Livestatus_active_connections() throws NumberFormatException
	{
		return getAsInt("livestatus_active_connections");
	}
		/**
		 * The current number of queued connections to MK Livestatus (that wait for a free thread)
		 * @return returns the value of the "livestatus_queued_connections" column as int
		 */
	public int Livestatus_queued_connections() throws NumberFormatException
	{
		return getAsInt("livestatus_queued_connections");
	}
		/**
		 * The maximum number of connections to MK Livestatus that can be handled in parallel
		 * @return returns the value of the "livestatus_threads" column as int
		 */
	public int Livestatus_threads() throws NumberFormatException
	{
		return getAsInt("livestatus_threads");
	}
		/**
		 * The version of the MK Livestatus module
		 * @return returns the value of the "livestatus_version" column as string
		 */
	public String Livestatus_version() 
	{
		return getAsString("livestatus_version");
	}
		/**
		 * The number of new log messages since program start
		 * @return returns the value of the "log_messages" column as int
		 */
	public int Log_messages() throws NumberFormatException
	{
		return getAsInt("log_messages");
	}
		/**
		 * the averaged number of new log messages per second
		 * @return returns the value of the "log_messages_rate" column as float
		 */
	public float Log_messages_rate() throws NumberFormatException
	{
		return getAsFloat("log_messages_rate");
	}
		/**
		 * The process ID of the Nagios main process
		 * @return returns the value of the "nagios_pid" column as int
		 */
	public int Nagios_pid() throws NumberFormatException
	{
		return getAsInt("nagios_pid");
	}
		/**
		 * The number of NEB call backs since program start
		 * @return returns the value of the "neb_callbacks" column as int
		 */
	public int Neb_callbacks() throws NumberFormatException
	{
		return getAsInt("neb_callbacks");
	}
		/**
		 * The averaged number of NEB call backs per second
		 * @return returns the value of the "neb_callbacks_rate" column as float
		 */
	public float Neb_callbacks_rate() throws NumberFormatException
	{
		return getAsFloat("neb_callbacks_rate");
	}
		/**
		 * The total number of hosts
		 * @return returns the value of the "num_hosts" column as int
		 */
	public int Num_hosts() throws NumberFormatException
	{
		return getAsInt("num_hosts");
	}
		/**
		 * The total number of services
		 * @return returns the value of the "num_services" column as int
		 */
	public int Num_services() throws NumberFormatException
	{
		return getAsInt("num_services");
	}
		/**
		 * Whether Nagios will obsess over host checks (0/1)
		 * @return returns the value of the "obsess_over_hosts" column as int
		 */
	public int Obsess_over_hosts() throws NumberFormatException
	{
		return getAsInt("obsess_over_hosts");
	}
		/**
		 * Whether Nagios will obsess over service checks and run the ocsp_command (0/1)
		 * @return returns the value of the "obsess_over_services" column as int
		 */
	public int Obsess_over_services() throws NumberFormatException
	{
		return getAsInt("obsess_over_services");
	}
		/**
		 * Whether processing of performance data is activated in general (0/1)
		 * @return returns the value of the "process_performance_data" column as int
		 */
	public int Process_performance_data() throws NumberFormatException
	{
		return getAsInt("process_performance_data");
	}
		/**
		 * The time of the last program start as UNIX timestamp
		 * @return returns the value of the "program_start" column as time
		 */
	public Date Program_start() throws NumberFormatException
	{
		return getAsTime("program_start");
	}
		/**
		 * The version of the monitoring daemon
		 * @return returns the value of the "program_version" column as string
		 */
	public String Program_version() 
	{
		return getAsString("program_version");
	}
		/**
		 * The number of requests to Livestatus since program start
		 * @return returns the value of the "requests" column as int
		 */
	public int Requests() throws NumberFormatException
	{
		return getAsInt("requests");
	}
		/**
		 * The averaged number of request to Livestatus per second
		 * @return returns the value of the "requests_rate" column as float
		 */
	public float Requests_rate() throws NumberFormatException
	{
		return getAsFloat("requests_rate");
	}
		/**
		 * The number of completed service checks since program start
		 * @return returns the value of the "service_checks" column as int
		 */
	public int Service_checks() throws NumberFormatException
	{
		return getAsInt("service_checks");
	}
		/**
		 * The averaged number of service checks per second
		 * @return returns the value of the "service_checks_rate" column as float
		 */
	public float Service_checks_rate() throws NumberFormatException
	{
		return getAsFloat("service_checks_rate");
	}
		/**
	 	* create the map for all columns of table  Status. Key=column name, Value=column value
	 	*
   	* @param table LiveStatus table
   	* @param filter filter to applay for this table
	 	* @return Map<String, String>
	 	*/

	@Override
	public Map<String, String> asArrayString(String table, String filter) throws NumberFormatException {
		mapKeyValue.clear();
		setMapObjects(table, filter);

		addToHashtable("accept_passive_host_checks", getAsString(Accept_passive_host_checks()));
		addToHashtable("accept_passive_service_checks", getAsString(Accept_passive_service_checks()));
		addToHashtable("cached_log_messages", getAsString(Cached_log_messages()));
		addToHashtable("check_external_commands", getAsString(Check_external_commands()));
		addToHashtable("check_host_freshness", getAsString(Check_host_freshness()));
		addToHashtable("check_service_freshness", getAsString(Check_service_freshness()));
		addToHashtable("connections", getAsString(Connections()));
		addToHashtable("connections_rate", getAsString(Connections_rate()));
		addToHashtable("enable_event_handlers", getAsString(Enable_event_handlers()));
		addToHashtable("enable_flap_detection", getAsString(Enable_flap_detection()));
		addToHashtable("enable_notifications", getAsString(Enable_notifications()));
		addToHashtable("execute_host_checks", getAsString(Execute_host_checks()));
		addToHashtable("execute_service_checks", getAsString(Execute_service_checks()));
		addToHashtable("external_command_buffer_max", getAsString(External_command_buffer_max()));
		addToHashtable("external_command_buffer_slots", getAsString(External_command_buffer_slots()));
		addToHashtable("external_command_buffer_usage", getAsString(External_command_buffer_usage()));
		addToHashtable("external_commands", getAsString(External_commands()));
		addToHashtable("external_commands_rate", getAsString(External_commands_rate()));
		addToHashtable("forks", getAsString(Forks()));
		addToHashtable("forks_rate", getAsString(Forks_rate()));
		addToHashtable("host_checks", getAsString(Host_checks()));
		addToHashtable("host_checks_rate", getAsString(Host_checks_rate()));
		addToHashtable("interval_length", getAsString(Interval_length()));
		addToHashtable("last_command_check", getAsString(Last_command_check()));
		addToHashtable("last_log_rotation", getAsString(Last_log_rotation()));
		addToHashtable("livecheck_overflows", getAsString(Livecheck_overflows()));
		addToHashtable("livecheck_overflows_rate", getAsString(Livecheck_overflows_rate()));
		addToHashtable("livechecks", getAsString(Livechecks()));
		addToHashtable("livechecks_rate", getAsString(Livechecks_rate()));
		addToHashtable("livestatus_active_connections", getAsString(Livestatus_active_connections()));
		addToHashtable("livestatus_queued_connections", getAsString(Livestatus_queued_connections()));
		addToHashtable("livestatus_threads", getAsString(Livestatus_threads()));
		addToHashtable("livestatus_version", (Livestatus_version()));
		addToHashtable("log_messages", getAsString(Log_messages()));
		addToHashtable("log_messages_rate", getAsString(Log_messages_rate()));
		addToHashtable("nagios_pid", getAsString(Nagios_pid()));
		addToHashtable("neb_callbacks", getAsString(Neb_callbacks()));
		addToHashtable("neb_callbacks_rate", getAsString(Neb_callbacks_rate()));
		addToHashtable("num_hosts", getAsString(Num_hosts()));
		addToHashtable("num_services", getAsString(Num_services()));
		addToHashtable("obsess_over_hosts", getAsString(Obsess_over_hosts()));
		addToHashtable("obsess_over_services", getAsString(Obsess_over_services()));
		addToHashtable("process_performance_data", getAsString(Process_performance_data()));
		addToHashtable("program_start", getAsString(Program_start()));
		addToHashtable("program_version", (Program_version()));
		addToHashtable("requests", getAsString(Requests()));
		addToHashtable("requests_rate", getAsString(Requests_rate()));
		addToHashtable("service_checks", getAsString(Service_checks()));
		addToHashtable("service_checks_rate", getAsString(Service_checks_rate()));
		return mapKeyValue;
	}
	}
