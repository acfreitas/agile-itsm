/*****************************************************************************
 * Log.java - 
 *
 * Copyright (c) 2014 Projeto citsmart (Contact: adenir.gomes@centralit.com.br)
 *
 * License:
 *****************************************************************************/
package br.com.centralit.nagios.livestatus.tables;

import java.util.Date;
import java.util.Map;


	/**
 	* Class Log is the main class for obtain all columns of table "log" 
from a Livestatus TCP-socket/file status.dat.
 	*
 	* @author	Adenir Ribeiro Gomes
 	*/ 

	public class Log extends LiveStatusBase
	{
		/**
	 	* Constructor of table Log
	 	*
	 	* @param path = "tcp://host:port" File : where path is the path to the file
	 	*/
	public Log(String path)
	{
		super(path);
		initializeMaps();
		tableName = "log";
	}
		/**
	 	* create the map for all columns description of table Log. Key=column name, Value=column description
	 	*
	 	*/
	public final void initializeMaps()
	{
		mapComments.put("attempt", "The number of the check attempt");
		mapComments.put("class", "The class of the message as integer (0:info, 1:state, 2:program, 3:notification, 4:passive, 5:command)");
		mapComments.put("command_name", "The name of the command of the log entry (e.g. for notifications)");
		mapComments.put("comment", "A comment field used in various message types");
		mapComments.put("contact_name", "The name of the contact the log entry is about (might be empty)");
		mapComments.put("current_command_line", "The shell command line");
		mapComments.put("current_command_name", "The name of the command");
		mapComments.put("current_contact_address1", "The additional field address1");
		mapComments.put("current_contact_address2", "The additional field address2");
		mapComments.put("current_contact_address3", "The additional field address3");
		mapComments.put("current_contact_address4", "The additional field address4");
		mapComments.put("current_contact_address5", "The additional field address5");
		mapComments.put("current_contact_address6", "The additional field address6");
		mapComments.put("current_contact_alias", "The full name of the contact");
		mapComments.put("current_contact_can_submit_commands", "Wether the contact is allowed to submit commands (0/1)");
		mapComments.put("current_contact_custom_variable_names", "A list of all custom variables of the contact");
		mapComments.put("current_contact_custom_variable_values", "A list of the values of all custom variables of the contact");
		mapComments.put("current_contact_custom_variables", "A dictionary of the custom variables");
		mapComments.put("current_contact_email", "The email address of the contact");
		mapComments.put("current_contact_host_notification_period", "The time period in which the contact will be notified about host problems");
		mapComments.put("current_contact_host_notifications_enabled", "Wether the contact will be notified about host problems in general (0/1)");
		mapComments.put("current_contact_in_host_notification_period", "Wether the contact is currently in his/her host notification period (0/1)");
		mapComments.put("current_contact_in_service_notification_period", "Wether the contact is currently in his/her service notification period (0/1)");
		mapComments.put("current_contact_modified_attributes", "A bitmask specifying which attributes have been modified");
		mapComments.put("current_contact_modified_attributes_list", "A list of all modified attributes");
		mapComments.put("current_contact_name", "The login name of the contact person");
		mapComments.put("current_contact_pager", "The pager address of the contact");
		mapComments.put("current_contact_service_notification_period", "The time period in which the contact will be notified about service problems");
		mapComments.put("current_contact_service_notifications_enabled", "Wether the contact will be notified about service problems in general (0/1)");
		mapComments.put("current_host_accept_passive_checks", "Whether passive host checks are accepted (0/1)");
		mapComments.put("current_host_acknowledged", "Whether the current host problem has been acknowledged (0/1)");
		mapComments.put("current_host_acknowledgement_type", "Type of acknowledgement (0: none, 1: normal, 2: stick)");
		mapComments.put("current_host_action_url", "An optional URL to custom actions or information about this host");
		mapComments.put("current_host_action_url_expanded", "The same as action_url, but with the most important macros expanded");
		mapComments.put("current_host_active_checks_enabled", "Whether active checks are enabled for the host (0/1)");
		mapComments.put("current_host_address", "IP address");
		mapComments.put("current_host_alias", "An alias name for the host");
		mapComments.put("current_host_check_command", "Nagios command for active host check of this host");
		mapComments.put("current_host_check_command_expanded", "Nagios command for active host check of this host with the macros expanded");
		mapComments.put("current_host_check_flapping_recovery_notification", "Whether to check to send a recovery notification when flapping stops (0/1)");
		mapComments.put("current_host_check_freshness", "Whether freshness checks are activated (0/1)");
		mapComments.put("current_host_check_interval", "Number of basic interval lengths between two scheduled checks of the host");
		mapComments.put("current_host_check_options", "The current check option, forced, normal, freshness... (0-2)");
		mapComments.put("current_host_check_period", "Time period in which this host will be checked. If empty then the host will always be checked.");
		mapComments.put("current_host_check_type", "Type of check (0: active, 1: passive)");
		mapComments.put("current_host_checks_enabled", "Whether checks of the host are enabled (0/1)");
		mapComments.put("current_host_childs", "A list of all direct childs of the host");
		mapComments.put("current_host_comments", "A list of the ids of all comments of this host");
		mapComments.put("current_host_comments_with_extra_info", "A list of all comments of the host with id, author, comment, entry type and entry time");
		mapComments.put("current_host_comments_with_info", "A list of all comments of the host with id, author and comment");
		mapComments.put("current_host_contact_groups", "A list of all contact groups this host is in");
		mapComments.put("current_host_contacts", "A list of all contacts of this host, either direct or via a contact group");
		mapComments.put("current_host_current_attempt", "Number of the current check attempts");
		mapComments.put("current_host_current_notification_number", "Number of the current notification");
		mapComments.put("current_host_custom_variable_names", "A list of the names of all custom variables");
		mapComments.put("current_host_custom_variable_values", "A list of the values of the custom variables");
		mapComments.put("current_host_custom_variables", "A dictionary of the custom variables");
		mapComments.put("current_host_display_name", "Optional display name of the host - not used by Nagios' web interface");
		mapComments.put("current_host_downtimes", "A list of the ids of all scheduled downtimes of this host");
		mapComments.put("current_host_downtimes_with_info", "A list of the all scheduled downtimes of the host with id, author and comment");
		mapComments.put("current_host_event_handler", "Nagios command used as event handler");
		mapComments.put("current_host_event_handler_enabled", "Whether event handling is enabled (0/1)");
		mapComments.put("current_host_execution_time", "Time the host check needed for execution");
		mapComments.put("current_host_filename", "The value of the custom variable FILENAME");
		mapComments.put("current_host_first_notification_delay", "Delay before the first notification");
		mapComments.put("current_host_flap_detection_enabled", "Whether flap detection is enabled (0/1)");
		mapComments.put("current_host_groups", "A list of all host groups this host is in");
		mapComments.put("current_host_hard_state", "The effective hard state of the host (eliminates a problem in hard_state)");
		mapComments.put("current_host_has_been_checked", "Whether the host has already been checked (0/1)");
		mapComments.put("current_host_high_flap_threshold", "High threshold of flap detection");
		mapComments.put("current_host_icon_image", "The name of an image file to be used in the web pages");
		mapComments.put("current_host_icon_image_alt", "Alternative text for the icon_image");
		mapComments.put("current_host_icon_image_expanded", "The same as icon_image, but with the most important macros expanded");
		mapComments.put("current_host_in_check_period", "Whether this host is currently in its check period (0/1)");
		mapComments.put("current_host_in_notification_period", "Whether this host is currently in its notification period (0/1)");
		mapComments.put("current_host_in_service_period", "Whether this host is currently in its service period (0/1)");
		mapComments.put("current_host_initial_state", "Initial host state");
		mapComments.put("current_host_is_executing", "is there a host check currently running... (0/1)");
		mapComments.put("current_host_is_flapping", "Whether the host state is flapping (0/1)");
		mapComments.put("current_host_last_check", "Time of the last check (Unix timestamp)");
		mapComments.put("current_host_last_hard_state", "Last hard state");
		mapComments.put("current_host_last_hard_state_change", "Time of the last hard state change (Unix timestamp)");
		mapComments.put("current_host_last_notification", "Time of the last notification (Unix timestamp)");
		mapComments.put("current_host_last_state", "State before last state change");
		mapComments.put("current_host_last_state_change", "Time of the last state change - soft or hard (Unix timestamp)");
		mapComments.put("current_host_last_time_down", "The last time the host was DOWN (Unix timestamp)");
		mapComments.put("current_host_last_time_unreachable", "The last time the host was UNREACHABLE (Unix timestamp)");
		mapComments.put("current_host_last_time_up", "The last time the host was UP (Unix timestamp)");
		mapComments.put("current_host_latency", "Time difference between scheduled check time and actual check time");
		mapComments.put("current_host_long_plugin_output", "Complete output from check plugin");
		mapComments.put("current_host_low_flap_threshold", "Low threshold of flap detection");
		mapComments.put("current_host_max_check_attempts", "Max check attempts for active host checks");
		mapComments.put("current_host_modified_attributes", "A bitmask specifying which attributes have been modified");
		mapComments.put("current_host_modified_attributes_list", "A list of all modified attributes");
		mapComments.put("current_host_name", "Host name");
		mapComments.put("current_host_next_check", "Scheduled time for the next check (Unix timestamp)");
		mapComments.put("current_host_next_notification", "Time of the next notification (Unix timestamp)");
		mapComments.put("current_host_no_more_notifications", "Whether to stop sending notifications (0/1)");
		mapComments.put("current_host_notes", "Optional notes for this host");
		mapComments.put("current_host_notes_expanded", "The same as notes, but with the most important macros expanded");
		mapComments.put("current_host_notes_url", "An optional URL with further information about the host");
		mapComments.put("current_host_notes_url_expanded", "Same es notes_url, but with the most important macros expanded");
		mapComments.put("current_host_notification_interval", "Interval of periodic notification or 0 if its off");
		mapComments.put("current_host_notification_period", "Time period in which problems of this host will be notified. If empty then notification will be always");
		mapComments.put("current_host_notifications_enabled", "Whether notifications of the host are enabled (0/1)");
		mapComments.put("current_host_num_services", "The total number of services of the host");
		mapComments.put("current_host_num_services_crit", "The number of the host's services with the soft state CRIT");
		mapComments.put("current_host_num_services_hard_crit", "The number of the host's services with the hard state CRIT");
		mapComments.put("current_host_num_services_hard_ok", "The number of the host's services with the hard state OK");
		mapComments.put("current_host_num_services_hard_unknown", "The number of the host's services with the hard state UNKNOWN");
		mapComments.put("current_host_num_services_hard_warn", "The number of the host's services with the hard state WARN");
		mapComments.put("current_host_num_services_ok", "The number of the host's services with the soft state OK");
		mapComments.put("current_host_num_services_pending", "The number of the host's services which have not been checked yet (pending)");
		mapComments.put("current_host_num_services_unknown", "The number of the host's services with the soft state UNKNOWN");
		mapComments.put("current_host_num_services_warn", "The number of the host's services with the soft state WARN");
		mapComments.put("current_host_obsess_over_host", "The current obsess_over_host setting... (0/1)");
		mapComments.put("current_host_parents", "A list of all direct parents of the host");
		mapComments.put("current_host_pending_flex_downtime", "Whether a flex downtime is pending (0/1)");
		mapComments.put("current_host_percent_state_change", "Percent state change");
		mapComments.put("current_host_perf_data", "Optional performance data of the last host check");
		mapComments.put("current_host_plugin_output", "Output of the last host check");
		mapComments.put("current_host_pnpgraph_present", "Whether there is a PNP4Nagios graph present for this host (0/1)");
		mapComments.put("current_host_process_performance_data", "Whether processing of performance data is enabled (0/1)");
		mapComments.put("current_host_retry_interval", "Number of basic interval lengths between checks when retrying after a soft error");
		mapComments.put("current_host_scheduled_downtime_depth", "The number of downtimes this host is currently in");
		mapComments.put("current_host_service_period", "The name of the service period of the host");
		mapComments.put("current_host_services", "A list of all services of the host");
		mapComments.put("current_host_services_with_info", "A list of all services including detailed information about each service");
		mapComments.put("current_host_services_with_state", "A list of all services of the host together with state and has_been_checked");
		mapComments.put("current_host_staleness", "Staleness indicator for this host");
		mapComments.put("current_host_state", "The current state of the host (0: up, 1: down, 2: unreachable)");
		mapComments.put("current_host_state_type", "Type of the current state (0: soft, 1: hard)");
		mapComments.put("current_host_statusmap_image", "The name of in image file for the status map");
		mapComments.put("current_host_total_services", "The total number of services of the host");
		mapComments.put("current_host_worst_service_hard_state", "The worst hard state of all of the host's services (OK <= WARN <= UNKNOWN <= CRIT)");
		mapComments.put("current_host_worst_service_state", "The worst soft state of all of the host's services (OK <= WARN <= UNKNOWN <= CRIT)");
		mapComments.put("current_host_x_3d", "3D-Coordinates: X");
		mapComments.put("current_host_y_3d", "3D-Coordinates: Y");
		mapComments.put("current_host_z_3d", "3D-Coordinates: Z");
		mapComments.put("current_service_accept_passive_checks", "Whether the service accepts passive checks (0/1)");
		mapComments.put("current_service_acknowledged", "Whether the current service problem has been acknowledged (0/1)");
		mapComments.put("current_service_acknowledgement_type", "The type of the acknownledgement (0: none, 1: normal, 2: sticky)");
		mapComments.put("current_service_action_url", "An optional URL for actions or custom information about the service");
		mapComments.put("current_service_action_url_expanded", "The action_url with (the most important) macros expanded");
		mapComments.put("current_service_active_checks_enabled", "Whether active checks are enabled for the service (0/1)");
		mapComments.put("current_service_check_command", "Nagios command used for active checks");
		mapComments.put("current_service_check_command_expanded", "Nagios command used for active checks with the macros expanded");
		mapComments.put("current_service_check_freshness", "Whether freshness checks are activated (0/1)");
		mapComments.put("current_service_check_interval", "Number of basic interval lengths between two scheduled checks of the service");
		mapComments.put("current_service_check_options", "The current check option, forced, normal, freshness... (0/1)");
		mapComments.put("current_service_check_period", "The name of the check period of the service. It this is empty, the service is always checked.");
		mapComments.put("current_service_check_type", "The type of the last check (0: active, 1: passive)");
		mapComments.put("current_service_checks_enabled", "Whether active checks are enabled for the service (0/1)");
		mapComments.put("current_service_comments", "A list of all comment ids of the service");
		mapComments.put("current_service_comments_with_extra_info", "A list of all comments of the service with id, author, comment, entry type and entry time");
		mapComments.put("current_service_comments_with_info", "A list of all comments of the service with id, author and comment");
		mapComments.put("current_service_contact_groups", "A list of all contact groups this service is in");
		mapComments.put("current_service_contacts", "A list of all contacts of the service, either direct or via a contact group");
		mapComments.put("current_service_current_attempt", "The number of the current check attempt");
		mapComments.put("current_service_current_notification_number", "The number of the current notification");
		mapComments.put("current_service_custom_variable_names", "A list of the names of all custom variables of the service");
		mapComments.put("current_service_custom_variable_values", "A list of the values of all custom variable of the service");
		mapComments.put("current_service_custom_variables", "A dictionary of the custom variables");
		mapComments.put("current_service_description", "Description of the service (also used as key)");
		mapComments.put("current_service_display_name", "An optional display name (not used by Nagios standard web pages)");
		mapComments.put("current_service_downtimes", "A list of all downtime ids of the service");
		mapComments.put("current_service_downtimes_with_info", "A list of all downtimes of the service with id, author and comment");
		mapComments.put("current_service_event_handler", "Nagios command used as event handler");
		mapComments.put("current_service_event_handler_enabled", "Whether and event handler is activated for the service (0/1)");
		mapComments.put("current_service_execution_time", "Time the service check needed for execution");
		mapComments.put("current_service_first_notification_delay", "Delay before the first notification");
		mapComments.put("current_service_flap_detection_enabled", "Whether flap detection is enabled for the service (0/1)");
		mapComments.put("current_service_groups", "A list of all service groups the service is in");
		mapComments.put("current_service_has_been_checked", "Whether the service already has been checked (0/1)");
		mapComments.put("current_service_high_flap_threshold", "High threshold of flap detection");
		mapComments.put("current_service_icon_image", "The name of an image to be used as icon in the web interface");
		mapComments.put("current_service_icon_image_alt", "An alternative text for the icon_image for browsers not displaying icons");
		mapComments.put("current_service_icon_image_expanded", "The icon_image with (the most important) macros expanded");
		mapComments.put("current_service_in_check_period", "Whether the service is currently in its check period (0/1)");
		mapComments.put("current_service_in_notification_period", "Whether the service is currently in its notification period (0/1)");
		mapComments.put("current_service_in_service_period", "Whether this service is currently in its service period (0/1)");
		mapComments.put("current_service_initial_state", "The initial state of the service");
		mapComments.put("current_service_is_executing", "is there a service check currently running... (0/1)");
		mapComments.put("current_service_is_flapping", "Whether the service is flapping (0/1)");
		mapComments.put("current_service_last_check", "The time of the last check (Unix timestamp)");
		mapComments.put("current_service_last_hard_state", "The last hard state of the service");
		mapComments.put("current_service_last_hard_state_change", "The time of the last hard state change (Unix timestamp)");
		mapComments.put("current_service_last_notification", "The time of the last notification (Unix timestamp)");
		mapComments.put("current_service_last_state", "The last state of the service");
		mapComments.put("current_service_last_state_change", "The time of the last state change (Unix timestamp)");
		mapComments.put("current_service_last_time_critical", "The last time the service was CRITICAL (Unix timestamp)");
		mapComments.put("current_service_last_time_ok", "The last time the service was OK (Unix timestamp)");
		mapComments.put("current_service_last_time_unknown", "The last time the service was UNKNOWN (Unix timestamp)");
		mapComments.put("current_service_last_time_warning", "The last time the service was in WARNING state (Unix timestamp)");
		mapComments.put("current_service_latency", "Time difference between scheduled check time and actual check time");
		mapComments.put("current_service_long_plugin_output", "Unabbreviated output of the last check plugin");
		mapComments.put("current_service_low_flap_threshold", "Low threshold of flap detection");
		mapComments.put("current_service_max_check_attempts", "The maximum number of check attempts");
		mapComments.put("current_service_modified_attributes", "A bitmask specifying which attributes have been modified");
		mapComments.put("current_service_modified_attributes_list", "A list of all modified attributes");
		mapComments.put("current_service_next_check", "The scheduled time of the next check (Unix timestamp)");
		mapComments.put("current_service_next_notification", "The time of the next notification (Unix timestamp)");
		mapComments.put("current_service_no_more_notifications", "Whether to stop sending notifications (0/1)");
		mapComments.put("current_service_notes", "Optional notes about the service");
		mapComments.put("current_service_notes_expanded", "The notes with (the most important) macros expanded");
		mapComments.put("current_service_notes_url", "An optional URL for additional notes about the service");
		mapComments.put("current_service_notes_url_expanded", "The notes_url with (the most important) macros expanded");
		mapComments.put("current_service_notification_interval", "Interval of periodic notification or 0 if its off");
		mapComments.put("current_service_notification_period", "The name of the notification period of the service. It this is empty, service problems are always notified.");
		mapComments.put("current_service_notifications_enabled", "Whether notifications are enabled for the service (0/1)");
		mapComments.put("current_service_obsess_over_service", "Whether 'obsess_over_service' is enabled for the service (0/1)");
		mapComments.put("current_service_percent_state_change", "Percent state change");
		mapComments.put("current_service_perf_data", "Performance data of the last check plugin");
		mapComments.put("current_service_plugin_output", "Output of the last check plugin");
		mapComments.put("current_service_pnpgraph_present", "Whether there is a PNP4Nagios graph present for this service (0/1)");
		mapComments.put("current_service_process_performance_data", "Whether processing of performance data is enabled for the service (0/1)");
		mapComments.put("current_service_retry_interval", "Number of basic interval lengths between checks when retrying after a soft error");
		mapComments.put("current_service_scheduled_downtime_depth", "The number of scheduled downtimes the service is currently in");
		mapComments.put("current_service_service_period", "The name of the service period of the service");
		mapComments.put("current_service_staleness", "The staleness indicator for this service");
		mapComments.put("current_service_state", "The current state of the service (0: OK, 1: WARN, 2: CRITICAL, 3: UNKNOWN)");
		mapComments.put("current_service_state_type", "The type of the current state (0: soft, 1: hard)");
		mapComments.put("host_name", "The name of the host the log entry is about (might be empty)");
		mapComments.put("lineno", "The number of the line in the log file");
		mapComments.put("message", "The complete message line including the timestamp");
		mapComments.put("options", "The part of the message after the ':'");
		mapComments.put("plugin_output", "The output of the check, if any is associated with the message");
		mapComments.put("service_description", "The description of the service log entry is about (might be empty)");
		mapComments.put("state", "The state of the host or service in question");
		mapComments.put("state_type", "The type of the state (varies on different log classes)");
		mapComments.put("time", "Time of the log event (UNIX timestamp)");
		mapComments.put("type", "The type of the message (text before the colon), the message itself for info messages");
	}
		/**
		 * The number of the check attempt
		 * @return returns the value of the "attempt" column as int
		 */
	public int Attempt() throws NumberFormatException
	{
		return getAsInt("attempt");
	}
		/**
		 * The class of the message as integer (0:info, 1:state, 2:program, 3:notification, 4:passive, 5:command)
		 * @return returns the value of the "class" column as int
		 */
	public int Class() throws NumberFormatException
	{
		return getAsInt("class");
	}
		/**
		 * The name of the command of the log entry (e.g. for notifications)
		 * @return returns the value of the "command_name" column as string
		 */
	public String Command_name() 
	{
		return getAsString("command_name");
	}
		/**
		 * A comment field used in various message types
		 * @return returns the value of the "comment" column as string
		 */
	public String Comment() 
	{
		return getAsString("comment");
	}
		/**
		 * The name of the contact the log entry is about (might be empty)
		 * @return returns the value of the "contact_name" column as string
		 */
	public String Contact_name() 
	{
		return getAsString("contact_name");
	}
		/**
		 * The shell command line
		 * @return returns the value of the "current_command_line" column as string
		 */
	public String Current_command_line() 
	{
		return getAsString("current_command_line");
	}
		/**
		 * The name of the command
		 * @return returns the value of the "current_command_name" column as string
		 */
	public String Current_command_name() 
	{
		return getAsString("current_command_name");
	}
		/**
		 * The additional field address1
		 * @return returns the value of the "current_contact_address1" column as string
		 */
	public String Current_contact_address1() 
	{
		return getAsString("current_contact_address1");
	}
		/**
		 * The additional field address2
		 * @return returns the value of the "current_contact_address2" column as string
		 */
	public String Current_contact_address2() 
	{
		return getAsString("current_contact_address2");
	}
		/**
		 * The additional field address3
		 * @return returns the value of the "current_contact_address3" column as string
		 */
	public String Current_contact_address3() 
	{
		return getAsString("current_contact_address3");
	}
		/**
		 * The additional field address4
		 * @return returns the value of the "current_contact_address4" column as string
		 */
	public String Current_contact_address4() 
	{
		return getAsString("current_contact_address4");
	}
		/**
		 * The additional field address5
		 * @return returns the value of the "current_contact_address5" column as string
		 */
	public String Current_contact_address5() 
	{
		return getAsString("current_contact_address5");
	}
		/**
		 * The additional field address6
		 * @return returns the value of the "current_contact_address6" column as string
		 */
	public String Current_contact_address6() 
	{
		return getAsString("current_contact_address6");
	}
		/**
		 * The full name of the contact
		 * @return returns the value of the "current_contact_alias" column as string
		 */
	public String Current_contact_alias() 
	{
		return getAsString("current_contact_alias");
	}
		/**
		 * Wether the contact is allowed to submit commands (0/1)
		 * @return returns the value of the "current_contact_can_submit_commands" column as int
		 */
	public int Current_contact_can_submit_commands() throws NumberFormatException
	{
		return getAsInt("current_contact_can_submit_commands");
	}
		/**
		 * A list of all custom variables of the contact
		 * @return returns the value of the "current_contact_custom_variable_names" column as list
		 */
	public String Current_contact_custom_variable_names() 
	{
		return getAsList("current_contact_custom_variable_names");
	}
		/**
		 * A list of the values of all custom variables of the contact
		 * @return returns the value of the "current_contact_custom_variable_values" column as list
		 */
	public String Current_contact_custom_variable_values() 
	{
		return getAsList("current_contact_custom_variable_values");
	}
		/**
		 * A dictionary of the custom variables
		 * @return returns the value of the "current_contact_custom_variables" column as dict
		 */
	public String Current_contact_custom_variables() 
	{
		return getAsDict("current_contact_custom_variables");
	}
		/**
		 * The email address of the contact
		 * @return returns the value of the "current_contact_email" column as string
		 */
	public String Current_contact_email() 
	{
		return getAsString("current_contact_email");
	}
		/**
		 * The time period in which the contact will be notified about host problems
		 * @return returns the value of the "current_contact_host_notification_period" column as string
		 */
	public String Current_contact_host_notification_period() 
	{
		return getAsString("current_contact_host_notification_period");
	}
		/**
		 * Wether the contact will be notified about host problems in general (0/1)
		 * @return returns the value of the "current_contact_host_notifications_enabled" column as int
		 */
	public int Current_contact_host_notifications_enabled() throws NumberFormatException
	{
		return getAsInt("current_contact_host_notifications_enabled");
	}
		/**
		 * Wether the contact is currently in his/her host notification period (0/1)
		 * @return returns the value of the "current_contact_in_host_notification_period" column as int
		 */
	public int Current_contact_in_host_notification_period() throws NumberFormatException
	{
		return getAsInt("current_contact_in_host_notification_period");
	}
		/**
		 * Wether the contact is currently in his/her service notification period (0/1)
		 * @return returns the value of the "current_contact_in_service_notification_period" column as int
		 */
	public int Current_contact_in_service_notification_period() throws NumberFormatException
	{
		return getAsInt("current_contact_in_service_notification_period");
	}
		/**
		 * A bitmask specifying which attributes have been modified
		 * @return returns the value of the "current_contact_modified_attributes" column as int
		 */
	public int Current_contact_modified_attributes() throws NumberFormatException
	{
		return getAsInt("current_contact_modified_attributes");
	}
		/**
		 * A list of all modified attributes
		 * @return returns the value of the "current_contact_modified_attributes_list" column as list
		 */
	public String Current_contact_modified_attributes_list() 
	{
		return getAsList("current_contact_modified_attributes_list");
	}
		/**
		 * The login name of the contact person
		 * @return returns the value of the "current_contact_name" column as string
		 */
	public String Current_contact_name() 
	{
		return getAsString("current_contact_name");
	}
		/**
		 * The pager address of the contact
		 * @return returns the value of the "current_contact_pager" column as string
		 */
	public String Current_contact_pager() 
	{
		return getAsString("current_contact_pager");
	}
		/**
		 * The time period in which the contact will be notified about service problems
		 * @return returns the value of the "current_contact_service_notification_period" column as string
		 */
	public String Current_contact_service_notification_period() 
	{
		return getAsString("current_contact_service_notification_period");
	}
		/**
		 * Wether the contact will be notified about service problems in general (0/1)
		 * @return returns the value of the "current_contact_service_notifications_enabled" column as int
		 */
	public int Current_contact_service_notifications_enabled() throws NumberFormatException
	{
		return getAsInt("current_contact_service_notifications_enabled");
	}
		/**
		 * Whether passive host checks are accepted (0/1)
		 * @return returns the value of the "current_host_accept_passive_checks" column as int
		 */
	public int Current_host_accept_passive_checks() throws NumberFormatException
	{
		return getAsInt("current_host_accept_passive_checks");
	}
		/**
		 * Whether the current host problem has been acknowledged (0/1)
		 * @return returns the value of the "current_host_acknowledged" column as int
		 */
	public int Current_host_acknowledged() throws NumberFormatException
	{
		return getAsInt("current_host_acknowledged");
	}
		/**
		 * Type of acknowledgement (0: none, 1: normal, 2: stick)
		 * @return returns the value of the "current_host_acknowledgement_type" column as int
		 */
	public int Current_host_acknowledgement_type() throws NumberFormatException
	{
		return getAsInt("current_host_acknowledgement_type");
	}
		/**
		 * An optional URL to custom actions or information about this host
		 * @return returns the value of the "current_host_action_url" column as string
		 */
	public String Current_host_action_url() 
	{
		return getAsString("current_host_action_url");
	}
		/**
		 * The same as action_url, but with the most important macros expanded
		 * @return returns the value of the "current_host_action_url_expanded" column as string
		 */
	public String Current_host_action_url_expanded() 
	{
		return getAsString("current_host_action_url_expanded");
	}
		/**
		 * Whether active checks are enabled for the host (0/1)
		 * @return returns the value of the "current_host_active_checks_enabled" column as int
		 */
	public int Current_host_active_checks_enabled() throws NumberFormatException
	{
		return getAsInt("current_host_active_checks_enabled");
	}
		/**
		 * IP address
		 * @return returns the value of the "current_host_address" column as string
		 */
	public String Current_host_address() 
	{
		return getAsString("current_host_address");
	}
		/**
		 * An alias name for the host
		 * @return returns the value of the "current_host_alias" column as string
		 */
	public String Current_host_alias() 
	{
		return getAsString("current_host_alias");
	}
		/**
		 * Nagios command for active host check of this host
		 * @return returns the value of the "current_host_check_command" column as string
		 */
	public String Current_host_check_command() 
	{
		return getAsString("current_host_check_command");
	}
		/**
		 * Nagios command for active host check of this host with the macros expanded
		 * @return returns the value of the "current_host_check_command_expanded" column as string
		 */
	public String Current_host_check_command_expanded() 
	{
		return getAsString("current_host_check_command_expanded");
	}
		/**
		 * Whether to check to send a recovery notification when flapping stops (0/1)
		 * @return returns the value of the "current_host_check_flapping_recovery_notification" column as int
		 */
	public int Current_host_check_flapping_recovery_notification() throws NumberFormatException
	{
		return getAsInt("current_host_check_flapping_recovery_notification");
	}
		/**
		 * Whether freshness checks are activated (0/1)
		 * @return returns the value of the "current_host_check_freshness" column as int
		 */
	public int Current_host_check_freshness() throws NumberFormatException
	{
		return getAsInt("current_host_check_freshness");
	}
		/**
		 * Number of basic interval lengths between two scheduled checks of the host
		 * @return returns the value of the "current_host_check_interval" column as float
		 */
	public float Current_host_check_interval() throws NumberFormatException
	{
		return getAsFloat("current_host_check_interval");
	}
		/**
		 * The current check option, forced, normal, freshness... (0-2)
		 * @return returns the value of the "current_host_check_options" column as int
		 */
	public int Current_host_check_options() throws NumberFormatException
	{
		return getAsInt("current_host_check_options");
	}
		/**
		 * Time period in which this host will be checked. If empty then the host will always be checked.
		 * @return returns the value of the "current_host_check_period" column as string
		 */
	public String Current_host_check_period() 
	{
		return getAsString("current_host_check_period");
	}
		/**
		 * Type of check (0: active, 1: passive)
		 * @return returns the value of the "current_host_check_type" column as int
		 */
	public int Current_host_check_type() throws NumberFormatException
	{
		return getAsInt("current_host_check_type");
	}
		/**
		 * Whether checks of the host are enabled (0/1)
		 * @return returns the value of the "current_host_checks_enabled" column as int
		 */
	public int Current_host_checks_enabled() throws NumberFormatException
	{
		return getAsInt("current_host_checks_enabled");
	}
		/**
		 * A list of all direct childs of the host
		 * @return returns the value of the "current_host_childs" column as list
		 */
	public String Current_host_childs() 
	{
		return getAsList("current_host_childs");
	}
		/**
		 * A list of the ids of all comments of this host
		 * @return returns the value of the "current_host_comments" column as list
		 */
	public String Current_host_comments() 
	{
		return getAsList("current_host_comments");
	}
		/**
		 * A list of all comments of the host with id, author, comment, entry type and entry time
		 * @return returns the value of the "current_host_comments_with_extra_info" column as list
		 */
	public String Current_host_comments_with_extra_info() 
	{
		return getAsList("current_host_comments_with_extra_info");
	}
		/**
		 * A list of all comments of the host with id, author and comment
		 * @return returns the value of the "current_host_comments_with_info" column as list
		 */
	public String Current_host_comments_with_info() 
	{
		return getAsList("current_host_comments_with_info");
	}
		/**
		 * A list of all contact groups this host is in
		 * @return returns the value of the "current_host_contact_groups" column as list
		 */
	public String Current_host_contact_groups() 
	{
		return getAsList("current_host_contact_groups");
	}
		/**
		 * A list of all contacts of this host, either direct or via a contact group
		 * @return returns the value of the "current_host_contacts" column as list
		 */
	public String Current_host_contacts() 
	{
		return getAsList("current_host_contacts");
	}
		/**
		 * Number of the current check attempts
		 * @return returns the value of the "current_host_current_attempt" column as int
		 */
	public int Current_host_current_attempt() throws NumberFormatException
	{
		return getAsInt("current_host_current_attempt");
	}
		/**
		 * Number of the current notification
		 * @return returns the value of the "current_host_current_notification_number" column as int
		 */
	public int Current_host_current_notification_number() throws NumberFormatException
	{
		return getAsInt("current_host_current_notification_number");
	}
		/**
		 * A list of the names of all custom variables
		 * @return returns the value of the "current_host_custom_variable_names" column as list
		 */
	public String Current_host_custom_variable_names() 
	{
		return getAsList("current_host_custom_variable_names");
	}
		/**
		 * A list of the values of the custom variables
		 * @return returns the value of the "current_host_custom_variable_values" column as list
		 */
	public String Current_host_custom_variable_values() 
	{
		return getAsList("current_host_custom_variable_values");
	}
		/**
		 * A dictionary of the custom variables
		 * @return returns the value of the "current_host_custom_variables" column as dict
		 */
	public String Current_host_custom_variables() 
	{
		return getAsDict("current_host_custom_variables");
	}
		/**
		 * Optional display name of the host - not used by Nagios' web interface
		 * @return returns the value of the "current_host_display_name" column as string
		 */
	public String Current_host_display_name() 
	{
		return getAsString("current_host_display_name");
	}
		/**
		 * A list of the ids of all scheduled downtimes of this host
		 * @return returns the value of the "current_host_downtimes" column as list
		 */
	public String Current_host_downtimes() 
	{
		return getAsList("current_host_downtimes");
	}
		/**
		 * A list of the all scheduled downtimes of the host with id, author and comment
		 * @return returns the value of the "current_host_downtimes_with_info" column as list
		 */
	public String Current_host_downtimes_with_info() 
	{
		return getAsList("current_host_downtimes_with_info");
	}
		/**
		 * Nagios command used as event handler
		 * @return returns the value of the "current_host_event_handler" column as string
		 */
	public String Current_host_event_handler() 
	{
		return getAsString("current_host_event_handler");
	}
		/**
		 * Whether event handling is enabled (0/1)
		 * @return returns the value of the "current_host_event_handler_enabled" column as int
		 */
	public int Current_host_event_handler_enabled() throws NumberFormatException
	{
		return getAsInt("current_host_event_handler_enabled");
	}
		/**
		 * Time the host check needed for execution
		 * @return returns the value of the "current_host_execution_time" column as float
		 */
	public float Current_host_execution_time() throws NumberFormatException
	{
		return getAsFloat("current_host_execution_time");
	}
		/**
		 * The value of the custom variable FILENAME
		 * @return returns the value of the "current_host_filename" column as string
		 */
	public String Current_host_filename() 
	{
		return getAsString("current_host_filename");
	}
		/**
		 * Delay before the first notification
		 * @return returns the value of the "current_host_first_notification_delay" column as float
		 */
	public float Current_host_first_notification_delay() throws NumberFormatException
	{
		return getAsFloat("current_host_first_notification_delay");
	}
		/**
		 * Whether flap detection is enabled (0/1)
		 * @return returns the value of the "current_host_flap_detection_enabled" column as int
		 */
	public int Current_host_flap_detection_enabled() throws NumberFormatException
	{
		return getAsInt("current_host_flap_detection_enabled");
	}
		/**
		 * A list of all host groups this host is in
		 * @return returns the value of the "current_host_groups" column as list
		 */
	public String Current_host_groups() 
	{
		return getAsList("current_host_groups");
	}
		/**
		 * The effective hard state of the host (eliminates a problem in hard_state)
		 * @return returns the value of the "current_host_hard_state" column as int
		 */
	public int Current_host_hard_state() throws NumberFormatException
	{
		return getAsInt("current_host_hard_state");
	}
		/**
		 * Whether the host has already been checked (0/1)
		 * @return returns the value of the "current_host_has_been_checked" column as int
		 */
	public int Current_host_has_been_checked() throws NumberFormatException
	{
		return getAsInt("current_host_has_been_checked");
	}
		/**
		 * High threshold of flap detection
		 * @return returns the value of the "current_host_high_flap_threshold" column as float
		 */
	public float Current_host_high_flap_threshold() throws NumberFormatException
	{
		return getAsFloat("current_host_high_flap_threshold");
	}
		/**
		 * The name of an image file to be used in the web pages
		 * @return returns the value of the "current_host_icon_image" column as string
		 */
	public String Current_host_icon_image() 
	{
		return getAsString("current_host_icon_image");
	}
		/**
		 * Alternative text for the icon_image
		 * @return returns the value of the "current_host_icon_image_alt" column as string
		 */
	public String Current_host_icon_image_alt() 
	{
		return getAsString("current_host_icon_image_alt");
	}
		/**
		 * The same as icon_image, but with the most important macros expanded
		 * @return returns the value of the "current_host_icon_image_expanded" column as string
		 */
	public String Current_host_icon_image_expanded() 
	{
		return getAsString("current_host_icon_image_expanded");
	}
		/**
		 * Whether this host is currently in its check period (0/1)
		 * @return returns the value of the "current_host_in_check_period" column as int
		 */
	public int Current_host_in_check_period() throws NumberFormatException
	{
		return getAsInt("current_host_in_check_period");
	}
		/**
		 * Whether this host is currently in its notification period (0/1)
		 * @return returns the value of the "current_host_in_notification_period" column as int
		 */
	public int Current_host_in_notification_period() throws NumberFormatException
	{
		return getAsInt("current_host_in_notification_period");
	}
		/**
		 * Whether this host is currently in its service period (0/1)
		 * @return returns the value of the "current_host_in_service_period" column as int
		 */
	public int Current_host_in_service_period() throws NumberFormatException
	{
		return getAsInt("current_host_in_service_period");
	}
		/**
		 * Initial host state
		 * @return returns the value of the "current_host_initial_state" column as int
		 */
	public int Current_host_initial_state() throws NumberFormatException
	{
		return getAsInt("current_host_initial_state");
	}
		/**
		 * is there a host check currently running... (0/1)
		 * @return returns the value of the "current_host_is_executing" column as int
		 */
	public int Current_host_is_executing() throws NumberFormatException
	{
		return getAsInt("current_host_is_executing");
	}
		/**
		 * Whether the host state is flapping (0/1)
		 * @return returns the value of the "current_host_is_flapping" column as int
		 */
	public int Current_host_is_flapping() throws NumberFormatException
	{
		return getAsInt("current_host_is_flapping");
	}
		/**
		 * Time of the last check (Unix timestamp)
		 * @return returns the value of the "current_host_last_check" column as time
		 */
	public Date Current_host_last_check() throws NumberFormatException
	{
		return getAsTime("current_host_last_check");
	}
		/**
		 * Last hard state
		 * @return returns the value of the "current_host_last_hard_state" column as int
		 */
	public int Current_host_last_hard_state() throws NumberFormatException
	{
		return getAsInt("current_host_last_hard_state");
	}
		/**
		 * Time of the last hard state change (Unix timestamp)
		 * @return returns the value of the "current_host_last_hard_state_change" column as time
		 */
	public Date Current_host_last_hard_state_change() throws NumberFormatException
	{
		return getAsTime("current_host_last_hard_state_change");
	}
		/**
		 * Time of the last notification (Unix timestamp)
		 * @return returns the value of the "current_host_last_notification" column as time
		 */
	public Date Current_host_last_notification() throws NumberFormatException
	{
		return getAsTime("current_host_last_notification");
	}
		/**
		 * State before last state change
		 * @return returns the value of the "current_host_last_state" column as int
		 */
	public int Current_host_last_state() throws NumberFormatException
	{
		return getAsInt("current_host_last_state");
	}
		/**
		 * Time of the last state change - soft or hard (Unix timestamp)
		 * @return returns the value of the "current_host_last_state_change" column as time
		 */
	public Date Current_host_last_state_change() throws NumberFormatException
	{
		return getAsTime("current_host_last_state_change");
	}
		/**
		 * The last time the host was DOWN (Unix timestamp)
		 * @return returns the value of the "current_host_last_time_down" column as time
		 */
	public Date Current_host_last_time_down() throws NumberFormatException
	{
		return getAsTime("current_host_last_time_down");
	}
		/**
		 * The last time the host was UNREACHABLE (Unix timestamp)
		 * @return returns the value of the "current_host_last_time_unreachable" column as time
		 */
	public Date Current_host_last_time_unreachable() throws NumberFormatException
	{
		return getAsTime("current_host_last_time_unreachable");
	}
		/**
		 * The last time the host was UP (Unix timestamp)
		 * @return returns the value of the "current_host_last_time_up" column as time
		 */
	public Date Current_host_last_time_up() throws NumberFormatException
	{
		return getAsTime("current_host_last_time_up");
	}
		/**
		 * Time difference between scheduled check time and actual check time
		 * @return returns the value of the "current_host_latency" column as float
		 */
	public float Current_host_latency() throws NumberFormatException
	{
		return getAsFloat("current_host_latency");
	}
		/**
		 * Complete output from check plugin
		 * @return returns the value of the "current_host_long_plugin_output" column as string
		 */
	public String Current_host_long_plugin_output() 
	{
		return getAsString("current_host_long_plugin_output");
	}
		/**
		 * Low threshold of flap detection
		 * @return returns the value of the "current_host_low_flap_threshold" column as float
		 */
	public float Current_host_low_flap_threshold() throws NumberFormatException
	{
		return getAsFloat("current_host_low_flap_threshold");
	}
		/**
		 * Max check attempts for active host checks
		 * @return returns the value of the "current_host_max_check_attempts" column as int
		 */
	public int Current_host_max_check_attempts() throws NumberFormatException
	{
		return getAsInt("current_host_max_check_attempts");
	}
		/**
		 * A bitmask specifying which attributes have been modified
		 * @return returns the value of the "current_host_modified_attributes" column as int
		 */
	public int Current_host_modified_attributes() throws NumberFormatException
	{
		return getAsInt("current_host_modified_attributes");
	}
		/**
		 * A list of all modified attributes
		 * @return returns the value of the "current_host_modified_attributes_list" column as list
		 */
	public String Current_host_modified_attributes_list() 
	{
		return getAsList("current_host_modified_attributes_list");
	}
		/**
		 * Host name
		 * @return returns the value of the "current_host_name" column as string
		 */
	public String Current_host_name() 
	{
		return getAsString("current_host_name");
	}
		/**
		 * Scheduled time for the next check (Unix timestamp)
		 * @return returns the value of the "current_host_next_check" column as time
		 */
	public Date Current_host_next_check() throws NumberFormatException
	{
		return getAsTime("current_host_next_check");
	}
		/**
		 * Time of the next notification (Unix timestamp)
		 * @return returns the value of the "current_host_next_notification" column as time
		 */
	public Date Current_host_next_notification() throws NumberFormatException
	{
		return getAsTime("current_host_next_notification");
	}
		/**
		 * Whether to stop sending notifications (0/1)
		 * @return returns the value of the "current_host_no_more_notifications" column as int
		 */
	public int Current_host_no_more_notifications() throws NumberFormatException
	{
		return getAsInt("current_host_no_more_notifications");
	}
		/**
		 * Optional notes for this host
		 * @return returns the value of the "current_host_notes" column as string
		 */
	public String Current_host_notes() 
	{
		return getAsString("current_host_notes");
	}
		/**
		 * The same as notes, but with the most important macros expanded
		 * @return returns the value of the "current_host_notes_expanded" column as string
		 */
	public String Current_host_notes_expanded() 
	{
		return getAsString("current_host_notes_expanded");
	}
		/**
		 * An optional URL with further information about the host
		 * @return returns the value of the "current_host_notes_url" column as string
		 */
	public String Current_host_notes_url() 
	{
		return getAsString("current_host_notes_url");
	}
		/**
		 * Same es notes_url, but with the most important macros expanded
		 * @return returns the value of the "current_host_notes_url_expanded" column as string
		 */
	public String Current_host_notes_url_expanded() 
	{
		return getAsString("current_host_notes_url_expanded");
	}
		/**
		 * Interval of periodic notification or 0 if its off
		 * @return returns the value of the "current_host_notification_interval" column as float
		 */
	public float Current_host_notification_interval() throws NumberFormatException
	{
		return getAsFloat("current_host_notification_interval");
	}
		/**
		 * Time period in which problems of this host will be notified. If empty then notification will be always
		 * @return returns the value of the "current_host_notification_period" column as string
		 */
	public String Current_host_notification_period() 
	{
		return getAsString("current_host_notification_period");
	}
		/**
		 * Whether notifications of the host are enabled (0/1)
		 * @return returns the value of the "current_host_notifications_enabled" column as int
		 */
	public int Current_host_notifications_enabled() throws NumberFormatException
	{
		return getAsInt("current_host_notifications_enabled");
	}
		/**
		 * The total number of services of the host
		 * @return returns the value of the "current_host_num_services" column as int
		 */
	public int Current_host_num_services() throws NumberFormatException
	{
		return getAsInt("current_host_num_services");
	}
		/**
		 * The number of the host's services with the soft state CRIT
		 * @return returns the value of the "current_host_num_services_crit" column as int
		 */
	public int Current_host_num_services_crit() throws NumberFormatException
	{
		return getAsInt("current_host_num_services_crit");
	}
		/**
		 * The number of the host's services with the hard state CRIT
		 * @return returns the value of the "current_host_num_services_hard_crit" column as int
		 */
	public int Current_host_num_services_hard_crit() throws NumberFormatException
	{
		return getAsInt("current_host_num_services_hard_crit");
	}
		/**
		 * The number of the host's services with the hard state OK
		 * @return returns the value of the "current_host_num_services_hard_ok" column as int
		 */
	public int Current_host_num_services_hard_ok() throws NumberFormatException
	{
		return getAsInt("current_host_num_services_hard_ok");
	}
		/**
		 * The number of the host's services with the hard state UNKNOWN
		 * @return returns the value of the "current_host_num_services_hard_unknown" column as int
		 */
	public int Current_host_num_services_hard_unknown() throws NumberFormatException
	{
		return getAsInt("current_host_num_services_hard_unknown");
	}
		/**
		 * The number of the host's services with the hard state WARN
		 * @return returns the value of the "current_host_num_services_hard_warn" column as int
		 */
	public int Current_host_num_services_hard_warn() throws NumberFormatException
	{
		return getAsInt("current_host_num_services_hard_warn");
	}
		/**
		 * The number of the host's services with the soft state OK
		 * @return returns the value of the "current_host_num_services_ok" column as int
		 */
	public int Current_host_num_services_ok() throws NumberFormatException
	{
		return getAsInt("current_host_num_services_ok");
	}
		/**
		 * The number of the host's services which have not been checked yet (pending)
		 * @return returns the value of the "current_host_num_services_pending" column as int
		 */
	public int Current_host_num_services_pending() throws NumberFormatException
	{
		return getAsInt("current_host_num_services_pending");
	}
		/**
		 * The number of the host's services with the soft state UNKNOWN
		 * @return returns the value of the "current_host_num_services_unknown" column as int
		 */
	public int Current_host_num_services_unknown() throws NumberFormatException
	{
		return getAsInt("current_host_num_services_unknown");
	}
		/**
		 * The number of the host's services with the soft state WARN
		 * @return returns the value of the "current_host_num_services_warn" column as int
		 */
	public int Current_host_num_services_warn() throws NumberFormatException
	{
		return getAsInt("current_host_num_services_warn");
	}
		/**
		 * The current obsess_over_host setting... (0/1)
		 * @return returns the value of the "current_host_obsess_over_host" column as int
		 */
	public int Current_host_obsess_over_host() throws NumberFormatException
	{
		return getAsInt("current_host_obsess_over_host");
	}
		/**
		 * A list of all direct parents of the host
		 * @return returns the value of the "current_host_parents" column as list
		 */
	public String Current_host_parents() 
	{
		return getAsList("current_host_parents");
	}
		/**
		 * Whether a flex downtime is pending (0/1)
		 * @return returns the value of the "current_host_pending_flex_downtime" column as int
		 */
	public int Current_host_pending_flex_downtime() throws NumberFormatException
	{
		return getAsInt("current_host_pending_flex_downtime");
	}
		/**
		 * Percent state change
		 * @return returns the value of the "current_host_percent_state_change" column as float
		 */
	public float Current_host_percent_state_change() throws NumberFormatException
	{
		return getAsFloat("current_host_percent_state_change");
	}
		/**
		 * Optional performance data of the last host check
		 * @return returns the value of the "current_host_perf_data" column as string
		 */
	public String Current_host_perf_data() 
	{
		return getAsString("current_host_perf_data");
	}
		/**
		 * Output of the last host check
		 * @return returns the value of the "current_host_plugin_output" column as string
		 */
	public String Current_host_plugin_output() 
	{
		return getAsString("current_host_plugin_output");
	}
		/**
		 * Whether there is a PNP4Nagios graph present for this host (0/1)
		 * @return returns the value of the "current_host_pnpgraph_present" column as int
		 */
	public int Current_host_pnpgraph_present() throws NumberFormatException
	{
		return getAsInt("current_host_pnpgraph_present");
	}
		/**
		 * Whether processing of performance data is enabled (0/1)
		 * @return returns the value of the "current_host_process_performance_data" column as int
		 */
	public int Current_host_process_performance_data() throws NumberFormatException
	{
		return getAsInt("current_host_process_performance_data");
	}
		/**
		 * Number of basic interval lengths between checks when retrying after a soft error
		 * @return returns the value of the "current_host_retry_interval" column as float
		 */
	public float Current_host_retry_interval() throws NumberFormatException
	{
		return getAsFloat("current_host_retry_interval");
	}
		/**
		 * The number of downtimes this host is currently in
		 * @return returns the value of the "current_host_scheduled_downtime_depth" column as int
		 */
	public int Current_host_scheduled_downtime_depth() throws NumberFormatException
	{
		return getAsInt("current_host_scheduled_downtime_depth");
	}
		/**
		 * The name of the service period of the host
		 * @return returns the value of the "current_host_service_period" column as string
		 */
	public String Current_host_service_period() 
	{
		return getAsString("current_host_service_period");
	}
		/**
		 * A list of all services of the host
		 * @return returns the value of the "current_host_services" column as list
		 */
	public String Current_host_services() 
	{
		return getAsList("current_host_services");
	}
		/**
		 * A list of all services including detailed information about each service
		 * @return returns the value of the "current_host_services_with_info" column as list
		 */
	public String Current_host_services_with_info() 
	{
		return getAsList("current_host_services_with_info");
	}
		/**
		 * A list of all services of the host together with state and has_been_checked
		 * @return returns the value of the "current_host_services_with_state" column as list
		 */
	public String Current_host_services_with_state() 
	{
		return getAsList("current_host_services_with_state");
	}
		/**
		 * Staleness indicator for this host
		 * @return returns the value of the "current_host_staleness" column as float
		 */
	public float Current_host_staleness() throws NumberFormatException
	{
		return getAsFloat("current_host_staleness");
	}
		/**
		 * The current state of the host (0: up, 1: down, 2: unreachable)
		 * @return returns the value of the "current_host_state" column as int
		 */
	public int Current_host_state() throws NumberFormatException
	{
		return getAsInt("current_host_state");
	}
		/**
		 * Type of the current state (0: soft, 1: hard)
		 * @return returns the value of the "current_host_state_type" column as int
		 */
	public int Current_host_state_type() throws NumberFormatException
	{
		return getAsInt("current_host_state_type");
	}
		/**
		 * The name of in image file for the status map
		 * @return returns the value of the "current_host_statusmap_image" column as string
		 */
	public String Current_host_statusmap_image() 
	{
		return getAsString("current_host_statusmap_image");
	}
		/**
		 * The total number of services of the host
		 * @return returns the value of the "current_host_total_services" column as int
		 */
	public int Current_host_total_services() throws NumberFormatException
	{
		return getAsInt("current_host_total_services");
	}
		/**
		 * The worst hard state of all of the host's services (OK <= WARN <= UNKNOWN <= CRIT)
		 * @return returns the value of the "current_host_worst_service_hard_state" column as int
		 */
	public int Current_host_worst_service_hard_state() throws NumberFormatException
	{
		return getAsInt("current_host_worst_service_hard_state");
	}
		/**
		 * The worst soft state of all of the host's services (OK <= WARN <= UNKNOWN <= CRIT)
		 * @return returns the value of the "current_host_worst_service_state" column as int
		 */
	public int Current_host_worst_service_state() throws NumberFormatException
	{
		return getAsInt("current_host_worst_service_state");
	}
		/**
		 * 3D-Coordinates: X
		 * @return returns the value of the "current_host_x_3d" column as float
		 */
	public float Current_host_x_3d() throws NumberFormatException
	{
		return getAsFloat("current_host_x_3d");
	}
		/**
		 * 3D-Coordinates: Y
		 * @return returns the value of the "current_host_y_3d" column as float
		 */
	public float Current_host_y_3d() throws NumberFormatException
	{
		return getAsFloat("current_host_y_3d");
	}
		/**
		 * 3D-Coordinates: Z
		 * @return returns the value of the "current_host_z_3d" column as float
		 */
	public float Current_host_z_3d() throws NumberFormatException
	{
		return getAsFloat("current_host_z_3d");
	}
		/**
		 * Whether the service accepts passive checks (0/1)
		 * @return returns the value of the "current_service_accept_passive_checks" column as int
		 */
	public int Current_service_accept_passive_checks() throws NumberFormatException
	{
		return getAsInt("current_service_accept_passive_checks");
	}
		/**
		 * Whether the current service problem has been acknowledged (0/1)
		 * @return returns the value of the "current_service_acknowledged" column as int
		 */
	public int Current_service_acknowledged() throws NumberFormatException
	{
		return getAsInt("current_service_acknowledged");
	}
		/**
		 * The type of the acknownledgement (0: none, 1: normal, 2: sticky)
		 * @return returns the value of the "current_service_acknowledgement_type" column as int
		 */
	public int Current_service_acknowledgement_type() throws NumberFormatException
	{
		return getAsInt("current_service_acknowledgement_type");
	}
		/**
		 * An optional URL for actions or custom information about the service
		 * @return returns the value of the "current_service_action_url" column as string
		 */
	public String Current_service_action_url() 
	{
		return getAsString("current_service_action_url");
	}
		/**
		 * The action_url with (the most important) macros expanded
		 * @return returns the value of the "current_service_action_url_expanded" column as string
		 */
	public String Current_service_action_url_expanded() 
	{
		return getAsString("current_service_action_url_expanded");
	}
		/**
		 * Whether active checks are enabled for the service (0/1)
		 * @return returns the value of the "current_service_active_checks_enabled" column as int
		 */
	public int Current_service_active_checks_enabled() throws NumberFormatException
	{
		return getAsInt("current_service_active_checks_enabled");
	}
		/**
		 * Nagios command used for active checks
		 * @return returns the value of the "current_service_check_command" column as string
		 */
	public String Current_service_check_command() 
	{
		return getAsString("current_service_check_command");
	}
		/**
		 * Nagios command used for active checks with the macros expanded
		 * @return returns the value of the "current_service_check_command_expanded" column as string
		 */
	public String Current_service_check_command_expanded() 
	{
		return getAsString("current_service_check_command_expanded");
	}
		/**
		 * Whether freshness checks are activated (0/1)
		 * @return returns the value of the "current_service_check_freshness" column as int
		 */
	public int Current_service_check_freshness() throws NumberFormatException
	{
		return getAsInt("current_service_check_freshness");
	}
		/**
		 * Number of basic interval lengths between two scheduled checks of the service
		 * @return returns the value of the "current_service_check_interval" column as float
		 */
	public float Current_service_check_interval() throws NumberFormatException
	{
		return getAsFloat("current_service_check_interval");
	}
		/**
		 * The current check option, forced, normal, freshness... (0/1)
		 * @return returns the value of the "current_service_check_options" column as int
		 */
	public int Current_service_check_options() throws NumberFormatException
	{
		return getAsInt("current_service_check_options");
	}
		/**
		 * The name of the check period of the service. It this is empty, the service is always checked.
		 * @return returns the value of the "current_service_check_period" column as string
		 */
	public String Current_service_check_period() 
	{
		return getAsString("current_service_check_period");
	}
		/**
		 * The type of the last check (0: active, 1: passive)
		 * @return returns the value of the "current_service_check_type" column as int
		 */
	public int Current_service_check_type() throws NumberFormatException
	{
		return getAsInt("current_service_check_type");
	}
		/**
		 * Whether active checks are enabled for the service (0/1)
		 * @return returns the value of the "current_service_checks_enabled" column as int
		 */
	public int Current_service_checks_enabled() throws NumberFormatException
	{
		return getAsInt("current_service_checks_enabled");
	}
		/**
		 * A list of all comment ids of the service
		 * @return returns the value of the "current_service_comments" column as list
		 */
	public String Current_service_comments() 
	{
		return getAsList("current_service_comments");
	}
		/**
		 * A list of all comments of the service with id, author, comment, entry type and entry time
		 * @return returns the value of the "current_service_comments_with_extra_info" column as list
		 */
	public String Current_service_comments_with_extra_info() 
	{
		return getAsList("current_service_comments_with_extra_info");
	}
		/**
		 * A list of all comments of the service with id, author and comment
		 * @return returns the value of the "current_service_comments_with_info" column as list
		 */
	public String Current_service_comments_with_info() 
	{
		return getAsList("current_service_comments_with_info");
	}
		/**
		 * A list of all contact groups this service is in
		 * @return returns the value of the "current_service_contact_groups" column as list
		 */
	public String Current_service_contact_groups() 
	{
		return getAsList("current_service_contact_groups");
	}
		/**
		 * A list of all contacts of the service, either direct or via a contact group
		 * @return returns the value of the "current_service_contacts" column as list
		 */
	public String Current_service_contacts() 
	{
		return getAsList("current_service_contacts");
	}
		/**
		 * The number of the current check attempt
		 * @return returns the value of the "current_service_current_attempt" column as int
		 */
	public int Current_service_current_attempt() throws NumberFormatException
	{
		return getAsInt("current_service_current_attempt");
	}
		/**
		 * The number of the current notification
		 * @return returns the value of the "current_service_current_notification_number" column as int
		 */
	public int Current_service_current_notification_number() throws NumberFormatException
	{
		return getAsInt("current_service_current_notification_number");
	}
		/**
		 * A list of the names of all custom variables of the service
		 * @return returns the value of the "current_service_custom_variable_names" column as list
		 */
	public String Current_service_custom_variable_names() 
	{
		return getAsList("current_service_custom_variable_names");
	}
		/**
		 * A list of the values of all custom variable of the service
		 * @return returns the value of the "current_service_custom_variable_values" column as list
		 */
	public String Current_service_custom_variable_values() 
	{
		return getAsList("current_service_custom_variable_values");
	}
		/**
		 * A dictionary of the custom variables
		 * @return returns the value of the "current_service_custom_variables" column as dict
		 */
	public String Current_service_custom_variables() 
	{
		return getAsDict("current_service_custom_variables");
	}
		/**
		 * Description of the service (also used as key)
		 * @return returns the value of the "current_service_description" column as string
		 */
	public String Current_service_description() 
	{
		return getAsString("current_service_description");
	}
		/**
		 * An optional display name (not used by Nagios standard web pages)
		 * @return returns the value of the "current_service_display_name" column as string
		 */
	public String Current_service_display_name() 
	{
		return getAsString("current_service_display_name");
	}
		/**
		 * A list of all downtime ids of the service
		 * @return returns the value of the "current_service_downtimes" column as list
		 */
	public String Current_service_downtimes() 
	{
		return getAsList("current_service_downtimes");
	}
		/**
		 * A list of all downtimes of the service with id, author and comment
		 * @return returns the value of the "current_service_downtimes_with_info" column as list
		 */
	public String Current_service_downtimes_with_info() 
	{
		return getAsList("current_service_downtimes_with_info");
	}
		/**
		 * Nagios command used as event handler
		 * @return returns the value of the "current_service_event_handler" column as string
		 */
	public String Current_service_event_handler() 
	{
		return getAsString("current_service_event_handler");
	}
		/**
		 * Whether and event handler is activated for the service (0/1)
		 * @return returns the value of the "current_service_event_handler_enabled" column as int
		 */
	public int Current_service_event_handler_enabled() throws NumberFormatException
	{
		return getAsInt("current_service_event_handler_enabled");
	}
		/**
		 * Time the service check needed for execution
		 * @return returns the value of the "current_service_execution_time" column as float
		 */
	public float Current_service_execution_time() throws NumberFormatException
	{
		return getAsFloat("current_service_execution_time");
	}
		/**
		 * Delay before the first notification
		 * @return returns the value of the "current_service_first_notification_delay" column as float
		 */
	public float Current_service_first_notification_delay() throws NumberFormatException
	{
		return getAsFloat("current_service_first_notification_delay");
	}
		/**
		 * Whether flap detection is enabled for the service (0/1)
		 * @return returns the value of the "current_service_flap_detection_enabled" column as int
		 */
	public int Current_service_flap_detection_enabled() throws NumberFormatException
	{
		return getAsInt("current_service_flap_detection_enabled");
	}
		/**
		 * A list of all service groups the service is in
		 * @return returns the value of the "current_service_groups" column as list
		 */
	public String Current_service_groups() 
	{
		return getAsList("current_service_groups");
	}
		/**
		 * Whether the service already has been checked (0/1)
		 * @return returns the value of the "current_service_has_been_checked" column as int
		 */
	public int Current_service_has_been_checked() throws NumberFormatException
	{
		return getAsInt("current_service_has_been_checked");
	}
		/**
		 * High threshold of flap detection
		 * @return returns the value of the "current_service_high_flap_threshold" column as float
		 */
	public float Current_service_high_flap_threshold() throws NumberFormatException
	{
		return getAsFloat("current_service_high_flap_threshold");
	}
		/**
		 * The name of an image to be used as icon in the web interface
		 * @return returns the value of the "current_service_icon_image" column as string
		 */
	public String Current_service_icon_image() 
	{
		return getAsString("current_service_icon_image");
	}
		/**
		 * An alternative text for the icon_image for browsers not displaying icons
		 * @return returns the value of the "current_service_icon_image_alt" column as string
		 */
	public String Current_service_icon_image_alt() 
	{
		return getAsString("current_service_icon_image_alt");
	}
		/**
		 * The icon_image with (the most important) macros expanded
		 * @return returns the value of the "current_service_icon_image_expanded" column as string
		 */
	public String Current_service_icon_image_expanded() 
	{
		return getAsString("current_service_icon_image_expanded");
	}
		/**
		 * Whether the service is currently in its check period (0/1)
		 * @return returns the value of the "current_service_in_check_period" column as int
		 */
	public int Current_service_in_check_period() throws NumberFormatException
	{
		return getAsInt("current_service_in_check_period");
	}
		/**
		 * Whether the service is currently in its notification period (0/1)
		 * @return returns the value of the "current_service_in_notification_period" column as int
		 */
	public int Current_service_in_notification_period() throws NumberFormatException
	{
		return getAsInt("current_service_in_notification_period");
	}
		/**
		 * Whether this service is currently in its service period (0/1)
		 * @return returns the value of the "current_service_in_service_period" column as int
		 */
	public int Current_service_in_service_period() throws NumberFormatException
	{
		return getAsInt("current_service_in_service_period");
	}
		/**
		 * The initial state of the service
		 * @return returns the value of the "current_service_initial_state" column as int
		 */
	public int Current_service_initial_state() throws NumberFormatException
	{
		return getAsInt("current_service_initial_state");
	}
		/**
		 * is there a service check currently running... (0/1)
		 * @return returns the value of the "current_service_is_executing" column as int
		 */
	public int Current_service_is_executing() throws NumberFormatException
	{
		return getAsInt("current_service_is_executing");
	}
		/**
		 * Whether the service is flapping (0/1)
		 * @return returns the value of the "current_service_is_flapping" column as int
		 */
	public int Current_service_is_flapping() throws NumberFormatException
	{
		return getAsInt("current_service_is_flapping");
	}
		/**
		 * The time of the last check (Unix timestamp)
		 * @return returns the value of the "current_service_last_check" column as time
		 */
	public Date Current_service_last_check() throws NumberFormatException
	{
		return getAsTime("current_service_last_check");
	}
		/**
		 * The last hard state of the service
		 * @return returns the value of the "current_service_last_hard_state" column as int
		 */
	public int Current_service_last_hard_state() throws NumberFormatException
	{
		return getAsInt("current_service_last_hard_state");
	}
		/**
		 * The time of the last hard state change (Unix timestamp)
		 * @return returns the value of the "current_service_last_hard_state_change" column as time
		 */
	public Date Current_service_last_hard_state_change() throws NumberFormatException
	{
		return getAsTime("current_service_last_hard_state_change");
	}
		/**
		 * The time of the last notification (Unix timestamp)
		 * @return returns the value of the "current_service_last_notification" column as time
		 */
	public Date Current_service_last_notification() throws NumberFormatException
	{
		return getAsTime("current_service_last_notification");
	}
		/**
		 * The last state of the service
		 * @return returns the value of the "current_service_last_state" column as int
		 */
	public int Current_service_last_state() throws NumberFormatException
	{
		return getAsInt("current_service_last_state");
	}
		/**
		 * The time of the last state change (Unix timestamp)
		 * @return returns the value of the "current_service_last_state_change" column as time
		 */
	public Date Current_service_last_state_change() throws NumberFormatException
	{
		return getAsTime("current_service_last_state_change");
	}
		/**
		 * The last time the service was CRITICAL (Unix timestamp)
		 * @return returns the value of the "current_service_last_time_critical" column as time
		 */
	public Date Current_service_last_time_critical() throws NumberFormatException
	{
		return getAsTime("current_service_last_time_critical");
	}
		/**
		 * The last time the service was OK (Unix timestamp)
		 * @return returns the value of the "current_service_last_time_ok" column as time
		 */
	public Date Current_service_last_time_ok() throws NumberFormatException
	{
		return getAsTime("current_service_last_time_ok");
	}
		/**
		 * The last time the service was UNKNOWN (Unix timestamp)
		 * @return returns the value of the "current_service_last_time_unknown" column as time
		 */
	public Date Current_service_last_time_unknown() throws NumberFormatException
	{
		return getAsTime("current_service_last_time_unknown");
	}
		/**
		 * The last time the service was in WARNING state (Unix timestamp)
		 * @return returns the value of the "current_service_last_time_warning" column as time
		 */
	public Date Current_service_last_time_warning() throws NumberFormatException
	{
		return getAsTime("current_service_last_time_warning");
	}
		/**
		 * Time difference between scheduled check time and actual check time
		 * @return returns the value of the "current_service_latency" column as float
		 */
	public float Current_service_latency() throws NumberFormatException
	{
		return getAsFloat("current_service_latency");
	}
		/**
		 * Unabbreviated output of the last check plugin
		 * @return returns the value of the "current_service_long_plugin_output" column as string
		 */
	public String Current_service_long_plugin_output() 
	{
		return getAsString("current_service_long_plugin_output");
	}
		/**
		 * Low threshold of flap detection
		 * @return returns the value of the "current_service_low_flap_threshold" column as float
		 */
	public float Current_service_low_flap_threshold() throws NumberFormatException
	{
		return getAsFloat("current_service_low_flap_threshold");
	}
		/**
		 * The maximum number of check attempts
		 * @return returns the value of the "current_service_max_check_attempts" column as int
		 */
	public int Current_service_max_check_attempts() throws NumberFormatException
	{
		return getAsInt("current_service_max_check_attempts");
	}
		/**
		 * A bitmask specifying which attributes have been modified
		 * @return returns the value of the "current_service_modified_attributes" column as int
		 */
	public int Current_service_modified_attributes() throws NumberFormatException
	{
		return getAsInt("current_service_modified_attributes");
	}
		/**
		 * A list of all modified attributes
		 * @return returns the value of the "current_service_modified_attributes_list" column as list
		 */
	public String Current_service_modified_attributes_list() 
	{
		return getAsList("current_service_modified_attributes_list");
	}
		/**
		 * The scheduled time of the next check (Unix timestamp)
		 * @return returns the value of the "current_service_next_check" column as time
		 */
	public Date Current_service_next_check() throws NumberFormatException
	{
		return getAsTime("current_service_next_check");
	}
		/**
		 * The time of the next notification (Unix timestamp)
		 * @return returns the value of the "current_service_next_notification" column as time
		 */
	public Date Current_service_next_notification() throws NumberFormatException
	{
		return getAsTime("current_service_next_notification");
	}
		/**
		 * Whether to stop sending notifications (0/1)
		 * @return returns the value of the "current_service_no_more_notifications" column as int
		 */
	public int Current_service_no_more_notifications() throws NumberFormatException
	{
		return getAsInt("current_service_no_more_notifications");
	}
		/**
		 * Optional notes about the service
		 * @return returns the value of the "current_service_notes" column as string
		 */
	public String Current_service_notes() 
	{
		return getAsString("current_service_notes");
	}
		/**
		 * The notes with (the most important) macros expanded
		 * @return returns the value of the "current_service_notes_expanded" column as string
		 */
	public String Current_service_notes_expanded() 
	{
		return getAsString("current_service_notes_expanded");
	}
		/**
		 * An optional URL for additional notes about the service
		 * @return returns the value of the "current_service_notes_url" column as string
		 */
	public String Current_service_notes_url() 
	{
		return getAsString("current_service_notes_url");
	}
		/**
		 * The notes_url with (the most important) macros expanded
		 * @return returns the value of the "current_service_notes_url_expanded" column as string
		 */
	public String Current_service_notes_url_expanded() 
	{
		return getAsString("current_service_notes_url_expanded");
	}
		/**
		 * Interval of periodic notification or 0 if its off
		 * @return returns the value of the "current_service_notification_interval" column as float
		 */
	public float Current_service_notification_interval() throws NumberFormatException
	{
		return getAsFloat("current_service_notification_interval");
	}
		/**
		 * The name of the notification period of the service. It this is empty, service problems are always notified.
		 * @return returns the value of the "current_service_notification_period" column as string
		 */
	public String Current_service_notification_period() 
	{
		return getAsString("current_service_notification_period");
	}
		/**
		 * Whether notifications are enabled for the service (0/1)
		 * @return returns the value of the "current_service_notifications_enabled" column as int
		 */
	public int Current_service_notifications_enabled() throws NumberFormatException
	{
		return getAsInt("current_service_notifications_enabled");
	}
		/**
		 * Whether 'obsess_over_service' is enabled for the service (0/1)
		 * @return returns the value of the "current_service_obsess_over_service" column as int
		 */
	public int Current_service_obsess_over_service() throws NumberFormatException
	{
		return getAsInt("current_service_obsess_over_service");
	}
		/**
		 * Percent state change
		 * @return returns the value of the "current_service_percent_state_change" column as float
		 */
	public float Current_service_percent_state_change() throws NumberFormatException
	{
		return getAsFloat("current_service_percent_state_change");
	}
		/**
		 * Performance data of the last check plugin
		 * @return returns the value of the "current_service_perf_data" column as string
		 */
	public String Current_service_perf_data() 
	{
		return getAsString("current_service_perf_data");
	}
		/**
		 * Output of the last check plugin
		 * @return returns the value of the "current_service_plugin_output" column as string
		 */
	public String Current_service_plugin_output() 
	{
		return getAsString("current_service_plugin_output");
	}
		/**
		 * Whether there is a PNP4Nagios graph present for this service (0/1)
		 * @return returns the value of the "current_service_pnpgraph_present" column as int
		 */
	public int Current_service_pnpgraph_present() throws NumberFormatException
	{
		return getAsInt("current_service_pnpgraph_present");
	}
		/**
		 * Whether processing of performance data is enabled for the service (0/1)
		 * @return returns the value of the "current_service_process_performance_data" column as int
		 */
	public int Current_service_process_performance_data() throws NumberFormatException
	{
		return getAsInt("current_service_process_performance_data");
	}
		/**
		 * Number of basic interval lengths between checks when retrying after a soft error
		 * @return returns the value of the "current_service_retry_interval" column as float
		 */
	public float Current_service_retry_interval() throws NumberFormatException
	{
		return getAsFloat("current_service_retry_interval");
	}
		/**
		 * The number of scheduled downtimes the service is currently in
		 * @return returns the value of the "current_service_scheduled_downtime_depth" column as int
		 */
	public int Current_service_scheduled_downtime_depth() throws NumberFormatException
	{
		return getAsInt("current_service_scheduled_downtime_depth");
	}
		/**
		 * The name of the service period of the service
		 * @return returns the value of the "current_service_service_period" column as string
		 */
	public String Current_service_service_period() 
	{
		return getAsString("current_service_service_period");
	}
		/**
		 * The staleness indicator for this service
		 * @return returns the value of the "current_service_staleness" column as float
		 */
	public float Current_service_staleness() throws NumberFormatException
	{
		return getAsFloat("current_service_staleness");
	}
		/**
		 * The current state of the service (0: OK, 1: WARN, 2: CRITICAL, 3: UNKNOWN)
		 * @return returns the value of the "current_service_state" column as int
		 */
	public int Current_service_state() throws NumberFormatException
	{
		return getAsInt("current_service_state");
	}
		/**
		 * The type of the current state (0: soft, 1: hard)
		 * @return returns the value of the "current_service_state_type" column as int
		 */
	public int Current_service_state_type() throws NumberFormatException
	{
		return getAsInt("current_service_state_type");
	}
		/**
		 * The name of the host the log entry is about (might be empty)
		 * @return returns the value of the "host_name" column as string
		 */
	public String Host_name() 
	{
		return getAsString("host_name");
	}
		/**
		 * The number of the line in the log file
		 * @return returns the value of the "lineno" column as int
		 */
	public int Lineno() throws NumberFormatException
	{
		return getAsInt("lineno");
	}
		/**
		 * The complete message line including the timestamp
		 * @return returns the value of the "message" column as string
		 */
	public String Message() 
	{
		return getAsString("message");
	}
		/**
		 * The part of the message after the ':'
		 * @return returns the value of the "options" column as string
		 */
	public String Options() 
	{
		return getAsString("options");
	}
		/**
		 * The output of the check, if any is associated with the message
		 * @return returns the value of the "plugin_output" column as string
		 */
	public String Plugin_output() 
	{
		return getAsString("plugin_output");
	}
		/**
		 * The description of the service log entry is about (might be empty)
		 * @return returns the value of the "service_description" column as string
		 */
	public String Service_description() 
	{
		return getAsString("service_description");
	}
		/**
		 * The state of the host or service in question
		 * @return returns the value of the "state" column as int
		 */
	public int State() throws NumberFormatException
	{
		return getAsInt("state");
	}
		/**
		 * The type of the state (varies on different log classes)
		 * @return returns the value of the "state_type" column as string
		 */
	public String State_type() 
	{
		return getAsString("state_type");
	}
		/**
		 * Time of the log event (UNIX timestamp)
		 * @return returns the value of the "time" column as time
		 */
	public Date Time() throws NumberFormatException
	{
		return getAsTime("time");
	}
		/**
		 * The type of the message (text before the colon), the message itself for info messages
		 * @return returns the value of the "type" column as string
		 */
	public String Type() 
	{
		return getAsString("type");
	}
		/**
	 	* create the map for all columns of table  Log. Key=column name, Value=column value
	 	*
   	* @param table LiveStatus table
   	* @param filter filter to applay for this table
	 	* @return Map<String, String>
	 	*/

	@Override
	public Map<String, String> asArrayString(String table, String filter) throws NumberFormatException {
		mapKeyValue.clear();
		setMapObjects(table, filter);

		addToHashtable("attempt", getAsString(Attempt()));
		addToHashtable("class", getAsString(Class()));
		addToHashtable("command_name", (Command_name()));
		addToHashtable("comment", (Comment()));
		addToHashtable("contact_name", (Contact_name()));
		addToHashtable("current_command_line", (Current_command_line()));
		addToHashtable("current_command_name", (Current_command_name()));
		addToHashtable("current_contact_address1", (Current_contact_address1()));
		addToHashtable("current_contact_address2", (Current_contact_address2()));
		addToHashtable("current_contact_address3", (Current_contact_address3()));
		addToHashtable("current_contact_address4", (Current_contact_address4()));
		addToHashtable("current_contact_address5", (Current_contact_address5()));
		addToHashtable("current_contact_address6", (Current_contact_address6()));
		addToHashtable("current_contact_alias", (Current_contact_alias()));
		addToHashtable("current_contact_can_submit_commands", getAsString(Current_contact_can_submit_commands()));
		addToHashtable("current_contact_custom_variable_names", (Current_contact_custom_variable_names()));
		addToHashtable("current_contact_custom_variable_values", (Current_contact_custom_variable_values()));
		addToHashtable("current_contact_custom_variables", (Current_contact_custom_variables()));
		addToHashtable("current_contact_email", (Current_contact_email()));
		addToHashtable("current_contact_host_notification_period", (Current_contact_host_notification_period()));
		addToHashtable("current_contact_host_notifications_enabled", getAsString(Current_contact_host_notifications_enabled()));
		addToHashtable("current_contact_in_host_notification_period", getAsString(Current_contact_in_host_notification_period()));
		addToHashtable("current_contact_in_service_notification_period", getAsString(Current_contact_in_service_notification_period()));
		addToHashtable("current_contact_modified_attributes", getAsString(Current_contact_modified_attributes()));
		addToHashtable("current_contact_modified_attributes_list", (Current_contact_modified_attributes_list()));
		addToHashtable("current_contact_name", (Current_contact_name()));
		addToHashtable("current_contact_pager", (Current_contact_pager()));
		addToHashtable("current_contact_service_notification_period", (Current_contact_service_notification_period()));
		addToHashtable("current_contact_service_notifications_enabled", getAsString(Current_contact_service_notifications_enabled()));
		addToHashtable("current_host_accept_passive_checks", getAsString(Current_host_accept_passive_checks()));
		addToHashtable("current_host_acknowledged", getAsString(Current_host_acknowledged()));
		addToHashtable("current_host_acknowledgement_type", getAsString(Current_host_acknowledgement_type()));
		addToHashtable("current_host_action_url", (Current_host_action_url()));
		addToHashtable("current_host_action_url_expanded", (Current_host_action_url_expanded()));
		addToHashtable("current_host_active_checks_enabled", getAsString(Current_host_active_checks_enabled()));
		addToHashtable("current_host_address", (Current_host_address()));
		addToHashtable("current_host_alias", (Current_host_alias()));
		addToHashtable("current_host_check_command", (Current_host_check_command()));
		addToHashtable("current_host_check_command_expanded", (Current_host_check_command_expanded()));
		addToHashtable("current_host_check_flapping_recovery_notification", getAsString(Current_host_check_flapping_recovery_notification()));
		addToHashtable("current_host_check_freshness", getAsString(Current_host_check_freshness()));
		addToHashtable("current_host_check_interval", getAsString(Current_host_check_interval()));
		addToHashtable("current_host_check_options", getAsString(Current_host_check_options()));
		addToHashtable("current_host_check_period", (Current_host_check_period()));
		addToHashtable("current_host_check_type", getAsString(Current_host_check_type()));
		addToHashtable("current_host_checks_enabled", getAsString(Current_host_checks_enabled()));
		addToHashtable("current_host_childs", (Current_host_childs()));
		addToHashtable("current_host_comments", (Current_host_comments()));
		addToHashtable("current_host_comments_with_extra_info", (Current_host_comments_with_extra_info()));
		addToHashtable("current_host_comments_with_info", (Current_host_comments_with_info()));
		addToHashtable("current_host_contact_groups", (Current_host_contact_groups()));
		addToHashtable("current_host_contacts", (Current_host_contacts()));
		addToHashtable("current_host_current_attempt", getAsString(Current_host_current_attempt()));
		addToHashtable("current_host_current_notification_number", getAsString(Current_host_current_notification_number()));
		addToHashtable("current_host_custom_variable_names", (Current_host_custom_variable_names()));
		addToHashtable("current_host_custom_variable_values", (Current_host_custom_variable_values()));
		addToHashtable("current_host_custom_variables", (Current_host_custom_variables()));
		addToHashtable("current_host_display_name", (Current_host_display_name()));
		addToHashtable("current_host_downtimes", (Current_host_downtimes()));
		addToHashtable("current_host_downtimes_with_info", (Current_host_downtimes_with_info()));
		addToHashtable("current_host_event_handler", (Current_host_event_handler()));
		addToHashtable("current_host_event_handler_enabled", getAsString(Current_host_event_handler_enabled()));
		addToHashtable("current_host_execution_time", getAsString(Current_host_execution_time()));
		addToHashtable("current_host_filename", (Current_host_filename()));
		addToHashtable("current_host_first_notification_delay", getAsString(Current_host_first_notification_delay()));
		addToHashtable("current_host_flap_detection_enabled", getAsString(Current_host_flap_detection_enabled()));
		addToHashtable("current_host_groups", (Current_host_groups()));
		addToHashtable("current_host_hard_state", getAsString(Current_host_hard_state()));
		addToHashtable("current_host_has_been_checked", getAsString(Current_host_has_been_checked()));
		addToHashtable("current_host_high_flap_threshold", getAsString(Current_host_high_flap_threshold()));
		addToHashtable("current_host_icon_image", (Current_host_icon_image()));
		addToHashtable("current_host_icon_image_alt", (Current_host_icon_image_alt()));
		addToHashtable("current_host_icon_image_expanded", (Current_host_icon_image_expanded()));
		addToHashtable("current_host_in_check_period", getAsString(Current_host_in_check_period()));
		addToHashtable("current_host_in_notification_period", getAsString(Current_host_in_notification_period()));
		addToHashtable("current_host_in_service_period", getAsString(Current_host_in_service_period()));
		addToHashtable("current_host_initial_state", getAsString(Current_host_initial_state()));
		addToHashtable("current_host_is_executing", getAsString(Current_host_is_executing()));
		addToHashtable("current_host_is_flapping", getAsString(Current_host_is_flapping()));
		addToHashtable("current_host_last_check", getAsString(Current_host_last_check()));
		addToHashtable("current_host_last_hard_state", getAsString(Current_host_last_hard_state()));
		addToHashtable("current_host_last_hard_state_change", getAsString(Current_host_last_hard_state_change()));
		addToHashtable("current_host_last_notification", getAsString(Current_host_last_notification()));
		addToHashtable("current_host_last_state", getAsString(Current_host_last_state()));
		addToHashtable("current_host_last_state_change", getAsString(Current_host_last_state_change()));
		addToHashtable("current_host_last_time_down", getAsString(Current_host_last_time_down()));
		addToHashtable("current_host_last_time_unreachable", getAsString(Current_host_last_time_unreachable()));
		addToHashtable("current_host_last_time_up", getAsString(Current_host_last_time_up()));
		addToHashtable("current_host_latency", getAsString(Current_host_latency()));
		addToHashtable("current_host_long_plugin_output", (Current_host_long_plugin_output()));
		addToHashtable("current_host_low_flap_threshold", getAsString(Current_host_low_flap_threshold()));
		addToHashtable("current_host_max_check_attempts", getAsString(Current_host_max_check_attempts()));
		addToHashtable("current_host_modified_attributes", getAsString(Current_host_modified_attributes()));
		addToHashtable("current_host_modified_attributes_list", (Current_host_modified_attributes_list()));
		addToHashtable("current_host_name", (Current_host_name()));
		addToHashtable("current_host_next_check", getAsString(Current_host_next_check()));
		addToHashtable("current_host_next_notification", getAsString(Current_host_next_notification()));
		addToHashtable("current_host_no_more_notifications", getAsString(Current_host_no_more_notifications()));
		addToHashtable("current_host_notes", (Current_host_notes()));
		addToHashtable("current_host_notes_expanded", (Current_host_notes_expanded()));
		addToHashtable("current_host_notes_url", (Current_host_notes_url()));
		addToHashtable("current_host_notes_url_expanded", (Current_host_notes_url_expanded()));
		addToHashtable("current_host_notification_interval", getAsString(Current_host_notification_interval()));
		addToHashtable("current_host_notification_period", (Current_host_notification_period()));
		addToHashtable("current_host_notifications_enabled", getAsString(Current_host_notifications_enabled()));
		addToHashtable("current_host_num_services", getAsString(Current_host_num_services()));
		addToHashtable("current_host_num_services_crit", getAsString(Current_host_num_services_crit()));
		addToHashtable("current_host_num_services_hard_crit", getAsString(Current_host_num_services_hard_crit()));
		addToHashtable("current_host_num_services_hard_ok", getAsString(Current_host_num_services_hard_ok()));
		addToHashtable("current_host_num_services_hard_unknown", getAsString(Current_host_num_services_hard_unknown()));
		addToHashtable("current_host_num_services_hard_warn", getAsString(Current_host_num_services_hard_warn()));
		addToHashtable("current_host_num_services_ok", getAsString(Current_host_num_services_ok()));
		addToHashtable("current_host_num_services_pending", getAsString(Current_host_num_services_pending()));
		addToHashtable("current_host_num_services_unknown", getAsString(Current_host_num_services_unknown()));
		addToHashtable("current_host_num_services_warn", getAsString(Current_host_num_services_warn()));
		addToHashtable("current_host_obsess_over_host", getAsString(Current_host_obsess_over_host()));
		addToHashtable("current_host_parents", (Current_host_parents()));
		addToHashtable("current_host_pending_flex_downtime", getAsString(Current_host_pending_flex_downtime()));
		addToHashtable("current_host_percent_state_change", getAsString(Current_host_percent_state_change()));
		addToHashtable("current_host_perf_data", (Current_host_perf_data()));
		addToHashtable("current_host_plugin_output", (Current_host_plugin_output()));
		addToHashtable("current_host_pnpgraph_present", getAsString(Current_host_pnpgraph_present()));
		addToHashtable("current_host_process_performance_data", getAsString(Current_host_process_performance_data()));
		addToHashtable("current_host_retry_interval", getAsString(Current_host_retry_interval()));
		addToHashtable("current_host_scheduled_downtime_depth", getAsString(Current_host_scheduled_downtime_depth()));
		addToHashtable("current_host_service_period", (Current_host_service_period()));
		addToHashtable("current_host_services", (Current_host_services()));
		addToHashtable("current_host_services_with_info", (Current_host_services_with_info()));
		addToHashtable("current_host_services_with_state", (Current_host_services_with_state()));
		addToHashtable("current_host_staleness", getAsString(Current_host_staleness()));
		addToHashtable("current_host_state", getAsString(Current_host_state()));
		addToHashtable("current_host_state_type", getAsString(Current_host_state_type()));
		addToHashtable("current_host_statusmap_image", (Current_host_statusmap_image()));
		addToHashtable("current_host_total_services", getAsString(Current_host_total_services()));
		addToHashtable("current_host_worst_service_hard_state", getAsString(Current_host_worst_service_hard_state()));
		addToHashtable("current_host_worst_service_state", getAsString(Current_host_worst_service_state()));
		addToHashtable("current_host_x_3d", getAsString(Current_host_x_3d()));
		addToHashtable("current_host_y_3d", getAsString(Current_host_y_3d()));
		addToHashtable("current_host_z_3d", getAsString(Current_host_z_3d()));
		addToHashtable("current_service_accept_passive_checks", getAsString(Current_service_accept_passive_checks()));
		addToHashtable("current_service_acknowledged", getAsString(Current_service_acknowledged()));
		addToHashtable("current_service_acknowledgement_type", getAsString(Current_service_acknowledgement_type()));
		addToHashtable("current_service_action_url", (Current_service_action_url()));
		addToHashtable("current_service_action_url_expanded", (Current_service_action_url_expanded()));
		addToHashtable("current_service_active_checks_enabled", getAsString(Current_service_active_checks_enabled()));
		addToHashtable("current_service_check_command", (Current_service_check_command()));
		addToHashtable("current_service_check_command_expanded", (Current_service_check_command_expanded()));
		addToHashtable("current_service_check_freshness", getAsString(Current_service_check_freshness()));
		addToHashtable("current_service_check_interval", getAsString(Current_service_check_interval()));
		addToHashtable("current_service_check_options", getAsString(Current_service_check_options()));
		addToHashtable("current_service_check_period", (Current_service_check_period()));
		addToHashtable("current_service_check_type", getAsString(Current_service_check_type()));
		addToHashtable("current_service_checks_enabled", getAsString(Current_service_checks_enabled()));
		addToHashtable("current_service_comments", (Current_service_comments()));
		addToHashtable("current_service_comments_with_extra_info", (Current_service_comments_with_extra_info()));
		addToHashtable("current_service_comments_with_info", (Current_service_comments_with_info()));
		addToHashtable("current_service_contact_groups", (Current_service_contact_groups()));
		addToHashtable("current_service_contacts", (Current_service_contacts()));
		addToHashtable("current_service_current_attempt", getAsString(Current_service_current_attempt()));
		addToHashtable("current_service_current_notification_number", getAsString(Current_service_current_notification_number()));
		addToHashtable("current_service_custom_variable_names", (Current_service_custom_variable_names()));
		addToHashtable("current_service_custom_variable_values", (Current_service_custom_variable_values()));
		addToHashtable("current_service_custom_variables", (Current_service_custom_variables()));
		addToHashtable("current_service_description", (Current_service_description()));
		addToHashtable("current_service_display_name", (Current_service_display_name()));
		addToHashtable("current_service_downtimes", (Current_service_downtimes()));
		addToHashtable("current_service_downtimes_with_info", (Current_service_downtimes_with_info()));
		addToHashtable("current_service_event_handler", (Current_service_event_handler()));
		addToHashtable("current_service_event_handler_enabled", getAsString(Current_service_event_handler_enabled()));
		addToHashtable("current_service_execution_time", getAsString(Current_service_execution_time()));
		addToHashtable("current_service_first_notification_delay", getAsString(Current_service_first_notification_delay()));
		addToHashtable("current_service_flap_detection_enabled", getAsString(Current_service_flap_detection_enabled()));
		addToHashtable("current_service_groups", (Current_service_groups()));
		addToHashtable("current_service_has_been_checked", getAsString(Current_service_has_been_checked()));
		addToHashtable("current_service_high_flap_threshold", getAsString(Current_service_high_flap_threshold()));
		addToHashtable("current_service_icon_image", (Current_service_icon_image()));
		addToHashtable("current_service_icon_image_alt", (Current_service_icon_image_alt()));
		addToHashtable("current_service_icon_image_expanded", (Current_service_icon_image_expanded()));
		addToHashtable("current_service_in_check_period", getAsString(Current_service_in_check_period()));
		addToHashtable("current_service_in_notification_period", getAsString(Current_service_in_notification_period()));
		addToHashtable("current_service_in_service_period", getAsString(Current_service_in_service_period()));
		addToHashtable("current_service_initial_state", getAsString(Current_service_initial_state()));
		addToHashtable("current_service_is_executing", getAsString(Current_service_is_executing()));
		addToHashtable("current_service_is_flapping", getAsString(Current_service_is_flapping()));
		addToHashtable("current_service_last_check", getAsString(Current_service_last_check()));
		addToHashtable("current_service_last_hard_state", getAsString(Current_service_last_hard_state()));
		addToHashtable("current_service_last_hard_state_change", getAsString(Current_service_last_hard_state_change()));
		addToHashtable("current_service_last_notification", getAsString(Current_service_last_notification()));
		addToHashtable("current_service_last_state", getAsString(Current_service_last_state()));
		addToHashtable("current_service_last_state_change", getAsString(Current_service_last_state_change()));
		addToHashtable("current_service_last_time_critical", getAsString(Current_service_last_time_critical()));
		addToHashtable("current_service_last_time_ok", getAsString(Current_service_last_time_ok()));
		addToHashtable("current_service_last_time_unknown", getAsString(Current_service_last_time_unknown()));
		addToHashtable("current_service_last_time_warning", getAsString(Current_service_last_time_warning()));
		addToHashtable("current_service_latency", getAsString(Current_service_latency()));
		addToHashtable("current_service_long_plugin_output", (Current_service_long_plugin_output()));
		addToHashtable("current_service_low_flap_threshold", getAsString(Current_service_low_flap_threshold()));
		addToHashtable("current_service_max_check_attempts", getAsString(Current_service_max_check_attempts()));
		addToHashtable("current_service_modified_attributes", getAsString(Current_service_modified_attributes()));
		addToHashtable("current_service_modified_attributes_list", (Current_service_modified_attributes_list()));
		addToHashtable("current_service_next_check", getAsString(Current_service_next_check()));
		addToHashtable("current_service_next_notification", getAsString(Current_service_next_notification()));
		addToHashtable("current_service_no_more_notifications", getAsString(Current_service_no_more_notifications()));
		addToHashtable("current_service_notes", (Current_service_notes()));
		addToHashtable("current_service_notes_expanded", (Current_service_notes_expanded()));
		addToHashtable("current_service_notes_url", (Current_service_notes_url()));
		addToHashtable("current_service_notes_url_expanded", (Current_service_notes_url_expanded()));
		addToHashtable("current_service_notification_interval", getAsString(Current_service_notification_interval()));
		addToHashtable("current_service_notification_period", (Current_service_notification_period()));
		addToHashtable("current_service_notifications_enabled", getAsString(Current_service_notifications_enabled()));
		addToHashtable("current_service_obsess_over_service", getAsString(Current_service_obsess_over_service()));
		addToHashtable("current_service_percent_state_change", getAsString(Current_service_percent_state_change()));
		addToHashtable("current_service_perf_data", (Current_service_perf_data()));
		addToHashtable("current_service_plugin_output", (Current_service_plugin_output()));
		addToHashtable("current_service_pnpgraph_present", getAsString(Current_service_pnpgraph_present()));
		addToHashtable("current_service_process_performance_data", getAsString(Current_service_process_performance_data()));
		addToHashtable("current_service_retry_interval", getAsString(Current_service_retry_interval()));
		addToHashtable("current_service_scheduled_downtime_depth", getAsString(Current_service_scheduled_downtime_depth()));
		addToHashtable("current_service_service_period", (Current_service_service_period()));
		addToHashtable("current_service_staleness", getAsString(Current_service_staleness()));
		addToHashtable("current_service_state", getAsString(Current_service_state()));
		addToHashtable("current_service_state_type", getAsString(Current_service_state_type()));
		addToHashtable("host_name", (Host_name()));
		addToHashtable("lineno", getAsString(Lineno()));
		addToHashtable("message", (Message()));
		addToHashtable("options", (Options()));
		addToHashtable("plugin_output", (Plugin_output()));
		addToHashtable("service_description", (Service_description()));
		addToHashtable("state", getAsString(State()));
		addToHashtable("state_type", (State_type()));
		addToHashtable("time", getAsString(Time()));
		addToHashtable("type", (Type()));
		return mapKeyValue;
	}
	}
