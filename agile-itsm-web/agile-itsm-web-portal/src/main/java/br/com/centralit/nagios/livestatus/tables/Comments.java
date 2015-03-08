/*****************************************************************************
 * Comments.java - 
 *
 * Copyright (c) 2014 Projeto citsmart (Contact: adenir.gomes@centralit.com.br)
 *
 * License:
 *****************************************************************************/
package br.com.centralit.nagios.livestatus.tables;

import java.util.Date;
import java.util.Map;


	/**
 	* Class Comments is the main class for obtain all columns of table "comments" 
from a Livestatus TCP-socket/file status.dat.
 	*
 	* @author	Adenir Ribeiro Gomes
 	*/ 

	public class Comments extends LiveStatusBase
	{
		/**
	 	* Constructor of table Comments
	 	*
	 	* @param path = "tcp://host:port" File : where path is the path to the file
	 	*/
	public Comments(String path)
	{
		super(path);
		initializeMaps();
		tableName = "comments";
	}
		/**
	 	* create the map for all columns description of table Comments. Key=column name, Value=column description
	 	*
	 	*/
	public final void initializeMaps()
	{
		mapComments.put("author", "The contact that entered the comment");
		mapComments.put("comment", "A comment text");
		mapComments.put("entry_time", "The time the entry was made as UNIX timestamp");
		mapComments.put("entry_type", "The type of the comment: 1 is user, 2 is downtime, 3 is flap and 4 is acknowledgement");
		mapComments.put("expire_time", "The time of expiry of this comment as a UNIX timestamp");
		mapComments.put("expires", "Whether this comment expires");
		mapComments.put("host_accept_passive_checks", "Whether passive host checks are accepted (0/1)");
		mapComments.put("host_acknowledged", "Whether the current host problem has been acknowledged (0/1)");
		mapComments.put("host_acknowledgement_type", "Type of acknowledgement (0: none, 1: normal, 2: stick)");
		mapComments.put("host_action_url", "An optional URL to custom actions or information about this host");
		mapComments.put("host_action_url_expanded", "The same as action_url, but with the most important macros expanded");
		mapComments.put("host_active_checks_enabled", "Whether active checks are enabled for the host (0/1)");
		mapComments.put("host_address", "IP address");
		mapComments.put("host_alias", "An alias name for the host");
		mapComments.put("host_check_command", "Nagios command for active host check of this host");
		mapComments.put("host_check_command_expanded", "Nagios command for active host check of this host with the macros expanded");
		mapComments.put("host_check_flapping_recovery_notification", "Whether to check to send a recovery notification when flapping stops (0/1)");
		mapComments.put("host_check_freshness", "Whether freshness checks are activated (0/1)");
		mapComments.put("host_check_interval", "Number of basic interval lengths between two scheduled checks of the host");
		mapComments.put("host_check_options", "The current check option, forced, normal, freshness... (0-2)");
		mapComments.put("host_check_period", "Time period in which this host will be checked. If empty then the host will always be checked.");
		mapComments.put("host_check_type", "Type of check (0: active, 1: passive)");
		mapComments.put("host_checks_enabled", "Whether checks of the host are enabled (0/1)");
		mapComments.put("host_childs", "A list of all direct childs of the host");
		mapComments.put("host_comments", "A list of the ids of all comments of this host");
		mapComments.put("host_comments_with_extra_info", "A list of all comments of the host with id, author, comment, entry type and entry time");
		mapComments.put("host_comments_with_info", "A list of all comments of the host with id, author and comment");
		mapComments.put("host_contact_groups", "A list of all contact groups this host is in");
		mapComments.put("host_contacts", "A list of all contacts of this host, either direct or via a contact group");
		mapComments.put("host_current_attempt", "Number of the current check attempts");
		mapComments.put("host_current_notification_number", "Number of the current notification");
		mapComments.put("host_custom_variable_names", "A list of the names of all custom variables");
		mapComments.put("host_custom_variable_values", "A list of the values of the custom variables");
		mapComments.put("host_custom_variables", "A dictionary of the custom variables");
		mapComments.put("host_display_name", "Optional display name of the host - not used by Nagios' web interface");
		mapComments.put("host_downtimes", "A list of the ids of all scheduled downtimes of this host");
		mapComments.put("host_downtimes_with_info", "A list of the all scheduled downtimes of the host with id, author and comment");
		mapComments.put("host_event_handler", "Nagios command used as event handler");
		mapComments.put("host_event_handler_enabled", "Whether event handling is enabled (0/1)");
		mapComments.put("host_execution_time", "Time the host check needed for execution");
		mapComments.put("host_filename", "The value of the custom variable FILENAME");
		mapComments.put("host_first_notification_delay", "Delay before the first notification");
		mapComments.put("host_flap_detection_enabled", "Whether flap detection is enabled (0/1)");
		mapComments.put("host_groups", "A list of all host groups this host is in");
		mapComments.put("host_hard_state", "The effective hard state of the host (eliminates a problem in hard_state)");
		mapComments.put("host_has_been_checked", "Whether the host has already been checked (0/1)");
		mapComments.put("host_high_flap_threshold", "High threshold of flap detection");
		mapComments.put("host_icon_image", "The name of an image file to be used in the web pages");
		mapComments.put("host_icon_image_alt", "Alternative text for the icon_image");
		mapComments.put("host_icon_image_expanded", "The same as icon_image, but with the most important macros expanded");
		mapComments.put("host_in_check_period", "Whether this host is currently in its check period (0/1)");
		mapComments.put("host_in_notification_period", "Whether this host is currently in its notification period (0/1)");
		mapComments.put("host_in_service_period", "Whether this host is currently in its service period (0/1)");
		mapComments.put("host_initial_state", "Initial host state");
		mapComments.put("host_is_executing", "is there a host check currently running... (0/1)");
		mapComments.put("host_is_flapping", "Whether the host state is flapping (0/1)");
		mapComments.put("host_last_check", "Time of the last check (Unix timestamp)");
		mapComments.put("host_last_hard_state", "Last hard state");
		mapComments.put("host_last_hard_state_change", "Time of the last hard state change (Unix timestamp)");
		mapComments.put("host_last_notification", "Time of the last notification (Unix timestamp)");
		mapComments.put("host_last_state", "State before last state change");
		mapComments.put("host_last_state_change", "Time of the last state change - soft or hard (Unix timestamp)");
		mapComments.put("host_last_time_down", "The last time the host was DOWN (Unix timestamp)");
		mapComments.put("host_last_time_unreachable", "The last time the host was UNREACHABLE (Unix timestamp)");
		mapComments.put("host_last_time_up", "The last time the host was UP (Unix timestamp)");
		mapComments.put("host_latency", "Time difference between scheduled check time and actual check time");
		mapComments.put("host_long_plugin_output", "Complete output from check plugin");
		mapComments.put("host_low_flap_threshold", "Low threshold of flap detection");
		mapComments.put("host_max_check_attempts", "Max check attempts for active host checks");
		mapComments.put("host_modified_attributes", "A bitmask specifying which attributes have been modified");
		mapComments.put("host_modified_attributes_list", "A list of all modified attributes");
		mapComments.put("host_name", "Host name");
		mapComments.put("host_next_check", "Scheduled time for the next check (Unix timestamp)");
		mapComments.put("host_next_notification", "Time of the next notification (Unix timestamp)");
		mapComments.put("host_no_more_notifications", "Whether to stop sending notifications (0/1)");
		mapComments.put("host_notes", "Optional notes for this host");
		mapComments.put("host_notes_expanded", "The same as notes, but with the most important macros expanded");
		mapComments.put("host_notes_url", "An optional URL with further information about the host");
		mapComments.put("host_notes_url_expanded", "Same es notes_url, but with the most important macros expanded");
		mapComments.put("host_notification_interval", "Interval of periodic notification or 0 if its off");
		mapComments.put("host_notification_period", "Time period in which problems of this host will be notified. If empty then notification will be always");
		mapComments.put("host_notifications_enabled", "Whether notifications of the host are enabled (0/1)");
		mapComments.put("host_num_services", "The total number of services of the host");
		mapComments.put("host_num_services_crit", "The number of the host's services with the soft state CRIT");
		mapComments.put("host_num_services_hard_crit", "The number of the host's services with the hard state CRIT");
		mapComments.put("host_num_services_hard_ok", "The number of the host's services with the hard state OK");
		mapComments.put("host_num_services_hard_unknown", "The number of the host's services with the hard state UNKNOWN");
		mapComments.put("host_num_services_hard_warn", "The number of the host's services with the hard state WARN");
		mapComments.put("host_num_services_ok", "The number of the host's services with the soft state OK");
		mapComments.put("host_num_services_pending", "The number of the host's services which have not been checked yet (pending)");
		mapComments.put("host_num_services_unknown", "The number of the host's services with the soft state UNKNOWN");
		mapComments.put("host_num_services_warn", "The number of the host's services with the soft state WARN");
		mapComments.put("host_obsess_over_host", "The current obsess_over_host setting... (0/1)");
		mapComments.put("host_parents", "A list of all direct parents of the host");
		mapComments.put("host_pending_flex_downtime", "Whether a flex downtime is pending (0/1)");
		mapComments.put("host_percent_state_change", "Percent state change");
		mapComments.put("host_perf_data", "Optional performance data of the last host check");
		mapComments.put("host_plugin_output", "Output of the last host check");
		mapComments.put("host_pnpgraph_present", "Whether there is a PNP4Nagios graph present for this host (0/1)");
		mapComments.put("host_process_performance_data", "Whether processing of performance data is enabled (0/1)");
		mapComments.put("host_retry_interval", "Number of basic interval lengths between checks when retrying after a soft error");
		mapComments.put("host_scheduled_downtime_depth", "The number of downtimes this host is currently in");
		mapComments.put("host_service_period", "The name of the service period of the host");
		mapComments.put("host_services", "A list of all services of the host");
		mapComments.put("host_services_with_info", "A list of all services including detailed information about each service");
		mapComments.put("host_services_with_state", "A list of all services of the host together with state and has_been_checked");
		mapComments.put("host_staleness", "Staleness indicator for this host");
		mapComments.put("host_state", "The current state of the host (0: up, 1: down, 2: unreachable)");
		mapComments.put("host_state_type", "Type of the current state (0: soft, 1: hard)");
		mapComments.put("host_statusmap_image", "The name of in image file for the status map");
		mapComments.put("host_total_services", "The total number of services of the host");
		mapComments.put("host_worst_service_hard_state", "The worst hard state of all of the host's services (OK <= WARN <= UNKNOWN <= CRIT)");
		mapComments.put("host_worst_service_state", "The worst soft state of all of the host's services (OK <= WARN <= UNKNOWN <= CRIT)");
		mapComments.put("host_x_3d", "3D-Coordinates: X");
		mapComments.put("host_y_3d", "3D-Coordinates: Y");
		mapComments.put("host_z_3d", "3D-Coordinates: Z");
		mapComments.put("id", "The id of the comment");
		mapComments.put("is_service", "0, if this entry is for a host, 1 if it is for a service");
		mapComments.put("persistent", "Whether this comment is persistent (0/1)");
		mapComments.put("service_accept_passive_checks", "Whether the service accepts passive checks (0/1)");
		mapComments.put("service_acknowledged", "Whether the current service problem has been acknowledged (0/1)");
		mapComments.put("service_acknowledgement_type", "The type of the acknownledgement (0: none, 1: normal, 2: sticky)");
		mapComments.put("service_action_url", "An optional URL for actions or custom information about the service");
		mapComments.put("service_action_url_expanded", "The action_url with (the most important) macros expanded");
		mapComments.put("service_active_checks_enabled", "Whether active checks are enabled for the service (0/1)");
		mapComments.put("service_check_command", "Nagios command used for active checks");
		mapComments.put("service_check_command_expanded", "Nagios command used for active checks with the macros expanded");
		mapComments.put("service_check_freshness", "Whether freshness checks are activated (0/1)");
		mapComments.put("service_check_interval", "Number of basic interval lengths between two scheduled checks of the service");
		mapComments.put("service_check_options", "The current check option, forced, normal, freshness... (0/1)");
		mapComments.put("service_check_period", "The name of the check period of the service. It this is empty, the service is always checked.");
		mapComments.put("service_check_type", "The type of the last check (0: active, 1: passive)");
		mapComments.put("service_checks_enabled", "Whether active checks are enabled for the service (0/1)");
		mapComments.put("service_comments", "A list of all comment ids of the service");
		mapComments.put("service_comments_with_extra_info", "A list of all comments of the service with id, author, comment, entry type and entry time");
		mapComments.put("service_comments_with_info", "A list of all comments of the service with id, author and comment");
		mapComments.put("service_contact_groups", "A list of all contact groups this service is in");
		mapComments.put("service_contacts", "A list of all contacts of the service, either direct or via a contact group");
		mapComments.put("service_current_attempt", "The number of the current check attempt");
		mapComments.put("service_current_notification_number", "The number of the current notification");
		mapComments.put("service_custom_variable_names", "A list of the names of all custom variables of the service");
		mapComments.put("service_custom_variable_values", "A list of the values of all custom variable of the service");
		mapComments.put("service_custom_variables", "A dictionary of the custom variables");
		mapComments.put("service_description", "Description of the service (also used as key)");
		mapComments.put("service_display_name", "An optional display name (not used by Nagios standard web pages)");
		mapComments.put("service_downtimes", "A list of all downtime ids of the service");
		mapComments.put("service_downtimes_with_info", "A list of all downtimes of the service with id, author and comment");
		mapComments.put("service_event_handler", "Nagios command used as event handler");
		mapComments.put("service_event_handler_enabled", "Whether and event handler is activated for the service (0/1)");
		mapComments.put("service_execution_time", "Time the service check needed for execution");
		mapComments.put("service_first_notification_delay", "Delay before the first notification");
		mapComments.put("service_flap_detection_enabled", "Whether flap detection is enabled for the service (0/1)");
		mapComments.put("service_groups", "A list of all service groups the service is in");
		mapComments.put("service_has_been_checked", "Whether the service already has been checked (0/1)");
		mapComments.put("service_high_flap_threshold", "High threshold of flap detection");
		mapComments.put("service_icon_image", "The name of an image to be used as icon in the web interface");
		mapComments.put("service_icon_image_alt", "An alternative text for the icon_image for browsers not displaying icons");
		mapComments.put("service_icon_image_expanded", "The icon_image with (the most important) macros expanded");
		mapComments.put("service_in_check_period", "Whether the service is currently in its check period (0/1)");
		mapComments.put("service_in_notification_period", "Whether the service is currently in its notification period (0/1)");
		mapComments.put("service_in_service_period", "Whether this service is currently in its service period (0/1)");
		mapComments.put("service_initial_state", "The initial state of the service");
		mapComments.put("service_is_executing", "is there a service check currently running... (0/1)");
		mapComments.put("service_is_flapping", "Whether the service is flapping (0/1)");
		mapComments.put("service_last_check", "The time of the last check (Unix timestamp)");
		mapComments.put("service_last_hard_state", "The last hard state of the service");
		mapComments.put("service_last_hard_state_change", "The time of the last hard state change (Unix timestamp)");
		mapComments.put("service_last_notification", "The time of the last notification (Unix timestamp)");
		mapComments.put("service_last_state", "The last state of the service");
		mapComments.put("service_last_state_change", "The time of the last state change (Unix timestamp)");
		mapComments.put("service_last_time_critical", "The last time the service was CRITICAL (Unix timestamp)");
		mapComments.put("service_last_time_ok", "The last time the service was OK (Unix timestamp)");
		mapComments.put("service_last_time_unknown", "The last time the service was UNKNOWN (Unix timestamp)");
		mapComments.put("service_last_time_warning", "The last time the service was in WARNING state (Unix timestamp)");
		mapComments.put("service_latency", "Time difference between scheduled check time and actual check time");
		mapComments.put("service_long_plugin_output", "Unabbreviated output of the last check plugin");
		mapComments.put("service_low_flap_threshold", "Low threshold of flap detection");
		mapComments.put("service_max_check_attempts", "The maximum number of check attempts");
		mapComments.put("service_modified_attributes", "A bitmask specifying which attributes have been modified");
		mapComments.put("service_modified_attributes_list", "A list of all modified attributes");
		mapComments.put("service_next_check", "The scheduled time of the next check (Unix timestamp)");
		mapComments.put("service_next_notification", "The time of the next notification (Unix timestamp)");
		mapComments.put("service_no_more_notifications", "Whether to stop sending notifications (0/1)");
		mapComments.put("service_notes", "Optional notes about the service");
		mapComments.put("service_notes_expanded", "The notes with (the most important) macros expanded");
		mapComments.put("service_notes_url", "An optional URL for additional notes about the service");
		mapComments.put("service_notes_url_expanded", "The notes_url with (the most important) macros expanded");
		mapComments.put("service_notification_interval", "Interval of periodic notification or 0 if its off");
		mapComments.put("service_notification_period", "The name of the notification period of the service. It this is empty, service problems are always notified.");
		mapComments.put("service_notifications_enabled", "Whether notifications are enabled for the service (0/1)");
		mapComments.put("service_obsess_over_service", "Whether 'obsess_over_service' is enabled for the service (0/1)");
		mapComments.put("service_percent_state_change", "Percent state change");
		mapComments.put("service_perf_data", "Performance data of the last check plugin");
		mapComments.put("service_plugin_output", "Output of the last check plugin");
		mapComments.put("service_pnpgraph_present", "Whether there is a PNP4Nagios graph present for this service (0/1)");
		mapComments.put("service_process_performance_data", "Whether processing of performance data is enabled for the service (0/1)");
		mapComments.put("service_retry_interval", "Number of basic interval lengths between checks when retrying after a soft error");
		mapComments.put("service_scheduled_downtime_depth", "The number of scheduled downtimes the service is currently in");
		mapComments.put("service_service_period", "The name of the service period of the service");
		mapComments.put("service_staleness", "The staleness indicator for this service");
		mapComments.put("service_state", "The current state of the service (0: OK, 1: WARN, 2: CRITICAL, 3: UNKNOWN)");
		mapComments.put("service_state_type", "The type of the current state (0: soft, 1: hard)");
		mapComments.put("source", "The source of the comment (0 is internal and 1 is external)");
		mapComments.put("type", "The type of the comment: 1 is host, 2 is service");
	}
		/**
		 * The contact that entered the comment
		 * @return returns the value of the "author" column as string
		 */
	public String Author() 
	{
		return getAsString("author");
	}
		/**
		 * A comment text
		 * @return returns the value of the "comment" column as string
		 */
	public String Comment() 
	{
		return getAsString("comment");
	}
		/**
		 * The time the entry was made as UNIX timestamp
		 * @return returns the value of the "entry_time" column as time
		 */
	public Date Entry_time() throws NumberFormatException
	{
		return getAsTime("entry_time");
	}
		/**
		 * The type of the comment: 1 is user, 2 is downtime, 3 is flap and 4 is acknowledgement
		 * @return returns the value of the "entry_type" column as int
		 */
	public int Entry_type() throws NumberFormatException
	{
		return getAsInt("entry_type");
	}
		/**
		 * The time of expiry of this comment as a UNIX timestamp
		 * @return returns the value of the "expire_time" column as time
		 */
	public Date Expire_time() throws NumberFormatException
	{
		return getAsTime("expire_time");
	}
		/**
		 * Whether this comment expires
		 * @return returns the value of the "expires" column as int
		 */
	public int Expires() throws NumberFormatException
	{
		return getAsInt("expires");
	}
		/**
		 * Whether passive host checks are accepted (0/1)
		 * @return returns the value of the "host_accept_passive_checks" column as int
		 */
	public int Host_accept_passive_checks() throws NumberFormatException
	{
		return getAsInt("host_accept_passive_checks");
	}
		/**
		 * Whether the current host problem has been acknowledged (0/1)
		 * @return returns the value of the "host_acknowledged" column as int
		 */
	public int Host_acknowledged() throws NumberFormatException
	{
		return getAsInt("host_acknowledged");
	}
		/**
		 * Type of acknowledgement (0: none, 1: normal, 2: stick)
		 * @return returns the value of the "host_acknowledgement_type" column as int
		 */
	public int Host_acknowledgement_type() throws NumberFormatException
	{
		return getAsInt("host_acknowledgement_type");
	}
		/**
		 * An optional URL to custom actions or information about this host
		 * @return returns the value of the "host_action_url" column as string
		 */
	public String Host_action_url() 
	{
		return getAsString("host_action_url");
	}
		/**
		 * The same as action_url, but with the most important macros expanded
		 * @return returns the value of the "host_action_url_expanded" column as string
		 */
	public String Host_action_url_expanded() 
	{
		return getAsString("host_action_url_expanded");
	}
		/**
		 * Whether active checks are enabled for the host (0/1)
		 * @return returns the value of the "host_active_checks_enabled" column as int
		 */
	public int Host_active_checks_enabled() throws NumberFormatException
	{
		return getAsInt("host_active_checks_enabled");
	}
		/**
		 * IP address
		 * @return returns the value of the "host_address" column as string
		 */
	public String Host_address() 
	{
		return getAsString("host_address");
	}
		/**
		 * An alias name for the host
		 * @return returns the value of the "host_alias" column as string
		 */
	public String Host_alias() 
	{
		return getAsString("host_alias");
	}
		/**
		 * Nagios command for active host check of this host
		 * @return returns the value of the "host_check_command" column as string
		 */
	public String Host_check_command() 
	{
		return getAsString("host_check_command");
	}
		/**
		 * Nagios command for active host check of this host with the macros expanded
		 * @return returns the value of the "host_check_command_expanded" column as string
		 */
	public String Host_check_command_expanded() 
	{
		return getAsString("host_check_command_expanded");
	}
		/**
		 * Whether to check to send a recovery notification when flapping stops (0/1)
		 * @return returns the value of the "host_check_flapping_recovery_notification" column as int
		 */
	public int Host_check_flapping_recovery_notification() throws NumberFormatException
	{
		return getAsInt("host_check_flapping_recovery_notification");
	}
		/**
		 * Whether freshness checks are activated (0/1)
		 * @return returns the value of the "host_check_freshness" column as int
		 */
	public int Host_check_freshness() throws NumberFormatException
	{
		return getAsInt("host_check_freshness");
	}
		/**
		 * Number of basic interval lengths between two scheduled checks of the host
		 * @return returns the value of the "host_check_interval" column as float
		 */
	public float Host_check_interval() throws NumberFormatException
	{
		return getAsFloat("host_check_interval");
	}
		/**
		 * The current check option, forced, normal, freshness... (0-2)
		 * @return returns the value of the "host_check_options" column as int
		 */
	public int Host_check_options() throws NumberFormatException
	{
		return getAsInt("host_check_options");
	}
		/**
		 * Time period in which this host will be checked. If empty then the host will always be checked.
		 * @return returns the value of the "host_check_period" column as string
		 */
	public String Host_check_period() 
	{
		return getAsString("host_check_period");
	}
		/**
		 * Type of check (0: active, 1: passive)
		 * @return returns the value of the "host_check_type" column as int
		 */
	public int Host_check_type() throws NumberFormatException
	{
		return getAsInt("host_check_type");
	}
		/**
		 * Whether checks of the host are enabled (0/1)
		 * @return returns the value of the "host_checks_enabled" column as int
		 */
	public int Host_checks_enabled() throws NumberFormatException
	{
		return getAsInt("host_checks_enabled");
	}
		/**
		 * A list of all direct childs of the host
		 * @return returns the value of the "host_childs" column as list
		 */
	public String Host_childs() 
	{
		return getAsList("host_childs");
	}
		/**
		 * A list of the ids of all comments of this host
		 * @return returns the value of the "host_comments" column as list
		 */
	public String Host_comments() 
	{
		return getAsList("host_comments");
	}
		/**
		 * A list of all comments of the host with id, author, comment, entry type and entry time
		 * @return returns the value of the "host_comments_with_extra_info" column as list
		 */
	public String Host_comments_with_extra_info() 
	{
		return getAsList("host_comments_with_extra_info");
	}
		/**
		 * A list of all comments of the host with id, author and comment
		 * @return returns the value of the "host_comments_with_info" column as list
		 */
	public String Host_comments_with_info() 
	{
		return getAsList("host_comments_with_info");
	}
		/**
		 * A list of all contact groups this host is in
		 * @return returns the value of the "host_contact_groups" column as list
		 */
	public String Host_contact_groups() 
	{
		return getAsList("host_contact_groups");
	}
		/**
		 * A list of all contacts of this host, either direct or via a contact group
		 * @return returns the value of the "host_contacts" column as list
		 */
	public String Host_contacts() 
	{
		return getAsList("host_contacts");
	}
		/**
		 * Number of the current check attempts
		 * @return returns the value of the "host_current_attempt" column as int
		 */
	public int Host_current_attempt() throws NumberFormatException
	{
		return getAsInt("host_current_attempt");
	}
		/**
		 * Number of the current notification
		 * @return returns the value of the "host_current_notification_number" column as int
		 */
	public int Host_current_notification_number() throws NumberFormatException
	{
		return getAsInt("host_current_notification_number");
	}
		/**
		 * A list of the names of all custom variables
		 * @return returns the value of the "host_custom_variable_names" column as list
		 */
	public String Host_custom_variable_names() 
	{
		return getAsList("host_custom_variable_names");
	}
		/**
		 * A list of the values of the custom variables
		 * @return returns the value of the "host_custom_variable_values" column as list
		 */
	public String Host_custom_variable_values() 
	{
		return getAsList("host_custom_variable_values");
	}
		/**
		 * A dictionary of the custom variables
		 * @return returns the value of the "host_custom_variables" column as dict
		 */
	public String Host_custom_variables() 
	{
		return getAsDict("host_custom_variables");
	}
		/**
		 * Optional display name of the host - not used by Nagios' web interface
		 * @return returns the value of the "host_display_name" column as string
		 */
	public String Host_display_name() 
	{
		return getAsString("host_display_name");
	}
		/**
		 * A list of the ids of all scheduled downtimes of this host
		 * @return returns the value of the "host_downtimes" column as list
		 */
	public String Host_downtimes() 
	{
		return getAsList("host_downtimes");
	}
		/**
		 * A list of the all scheduled downtimes of the host with id, author and comment
		 * @return returns the value of the "host_downtimes_with_info" column as list
		 */
	public String Host_downtimes_with_info() 
	{
		return getAsList("host_downtimes_with_info");
	}
		/**
		 * Nagios command used as event handler
		 * @return returns the value of the "host_event_handler" column as string
		 */
	public String Host_event_handler() 
	{
		return getAsString("host_event_handler");
	}
		/**
		 * Whether event handling is enabled (0/1)
		 * @return returns the value of the "host_event_handler_enabled" column as int
		 */
	public int Host_event_handler_enabled() throws NumberFormatException
	{
		return getAsInt("host_event_handler_enabled");
	}
		/**
		 * Time the host check needed for execution
		 * @return returns the value of the "host_execution_time" column as float
		 */
	public float Host_execution_time() throws NumberFormatException
	{
		return getAsFloat("host_execution_time");
	}
		/**
		 * The value of the custom variable FILENAME
		 * @return returns the value of the "host_filename" column as string
		 */
	public String Host_filename() 
	{
		return getAsString("host_filename");
	}
		/**
		 * Delay before the first notification
		 * @return returns the value of the "host_first_notification_delay" column as float
		 */
	public float Host_first_notification_delay() throws NumberFormatException
	{
		return getAsFloat("host_first_notification_delay");
	}
		/**
		 * Whether flap detection is enabled (0/1)
		 * @return returns the value of the "host_flap_detection_enabled" column as int
		 */
	public int Host_flap_detection_enabled() throws NumberFormatException
	{
		return getAsInt("host_flap_detection_enabled");
	}
		/**
		 * A list of all host groups this host is in
		 * @return returns the value of the "host_groups" column as list
		 */
	public String Host_groups() 
	{
		return getAsList("host_groups");
	}
		/**
		 * The effective hard state of the host (eliminates a problem in hard_state)
		 * @return returns the value of the "host_hard_state" column as int
		 */
	public int Host_hard_state() throws NumberFormatException
	{
		return getAsInt("host_hard_state");
	}
		/**
		 * Whether the host has already been checked (0/1)
		 * @return returns the value of the "host_has_been_checked" column as int
		 */
	public int Host_has_been_checked() throws NumberFormatException
	{
		return getAsInt("host_has_been_checked");
	}
		/**
		 * High threshold of flap detection
		 * @return returns the value of the "host_high_flap_threshold" column as float
		 */
	public float Host_high_flap_threshold() throws NumberFormatException
	{
		return getAsFloat("host_high_flap_threshold");
	}
		/**
		 * The name of an image file to be used in the web pages
		 * @return returns the value of the "host_icon_image" column as string
		 */
	public String Host_icon_image() 
	{
		return getAsString("host_icon_image");
	}
		/**
		 * Alternative text for the icon_image
		 * @return returns the value of the "host_icon_image_alt" column as string
		 */
	public String Host_icon_image_alt() 
	{
		return getAsString("host_icon_image_alt");
	}
		/**
		 * The same as icon_image, but with the most important macros expanded
		 * @return returns the value of the "host_icon_image_expanded" column as string
		 */
	public String Host_icon_image_expanded() 
	{
		return getAsString("host_icon_image_expanded");
	}
		/**
		 * Whether this host is currently in its check period (0/1)
		 * @return returns the value of the "host_in_check_period" column as int
		 */
	public int Host_in_check_period() throws NumberFormatException
	{
		return getAsInt("host_in_check_period");
	}
		/**
		 * Whether this host is currently in its notification period (0/1)
		 * @return returns the value of the "host_in_notification_period" column as int
		 */
	public int Host_in_notification_period() throws NumberFormatException
	{
		return getAsInt("host_in_notification_period");
	}
		/**
		 * Whether this host is currently in its service period (0/1)
		 * @return returns the value of the "host_in_service_period" column as int
		 */
	public int Host_in_service_period() throws NumberFormatException
	{
		return getAsInt("host_in_service_period");
	}
		/**
		 * Initial host state
		 * @return returns the value of the "host_initial_state" column as int
		 */
	public int Host_initial_state() throws NumberFormatException
	{
		return getAsInt("host_initial_state");
	}
		/**
		 * is there a host check currently running... (0/1)
		 * @return returns the value of the "host_is_executing" column as int
		 */
	public int Host_is_executing() throws NumberFormatException
	{
		return getAsInt("host_is_executing");
	}
		/**
		 * Whether the host state is flapping (0/1)
		 * @return returns the value of the "host_is_flapping" column as int
		 */
	public int Host_is_flapping() throws NumberFormatException
	{
		return getAsInt("host_is_flapping");
	}
		/**
		 * Time of the last check (Unix timestamp)
		 * @return returns the value of the "host_last_check" column as time
		 */
	public Date Host_last_check() throws NumberFormatException
	{
		return getAsTime("host_last_check");
	}
		/**
		 * Last hard state
		 * @return returns the value of the "host_last_hard_state" column as int
		 */
	public int Host_last_hard_state() throws NumberFormatException
	{
		return getAsInt("host_last_hard_state");
	}
		/**
		 * Time of the last hard state change (Unix timestamp)
		 * @return returns the value of the "host_last_hard_state_change" column as time
		 */
	public Date Host_last_hard_state_change() throws NumberFormatException
	{
		return getAsTime("host_last_hard_state_change");
	}
		/**
		 * Time of the last notification (Unix timestamp)
		 * @return returns the value of the "host_last_notification" column as time
		 */
	public Date Host_last_notification() throws NumberFormatException
	{
		return getAsTime("host_last_notification");
	}
		/**
		 * State before last state change
		 * @return returns the value of the "host_last_state" column as int
		 */
	public int Host_last_state() throws NumberFormatException
	{
		return getAsInt("host_last_state");
	}
		/**
		 * Time of the last state change - soft or hard (Unix timestamp)
		 * @return returns the value of the "host_last_state_change" column as time
		 */
	public Date Host_last_state_change() throws NumberFormatException
	{
		return getAsTime("host_last_state_change");
	}
		/**
		 * The last time the host was DOWN (Unix timestamp)
		 * @return returns the value of the "host_last_time_down" column as time
		 */
	public Date Host_last_time_down() throws NumberFormatException
	{
		return getAsTime("host_last_time_down");
	}
		/**
		 * The last time the host was UNREACHABLE (Unix timestamp)
		 * @return returns the value of the "host_last_time_unreachable" column as time
		 */
	public Date Host_last_time_unreachable() throws NumberFormatException
	{
		return getAsTime("host_last_time_unreachable");
	}
		/**
		 * The last time the host was UP (Unix timestamp)
		 * @return returns the value of the "host_last_time_up" column as time
		 */
	public Date Host_last_time_up() throws NumberFormatException
	{
		return getAsTime("host_last_time_up");
	}
		/**
		 * Time difference between scheduled check time and actual check time
		 * @return returns the value of the "host_latency" column as float
		 */
	public float Host_latency() throws NumberFormatException
	{
		return getAsFloat("host_latency");
	}
		/**
		 * Complete output from check plugin
		 * @return returns the value of the "host_long_plugin_output" column as string
		 */
	public String Host_long_plugin_output() 
	{
		return getAsString("host_long_plugin_output");
	}
		/**
		 * Low threshold of flap detection
		 * @return returns the value of the "host_low_flap_threshold" column as float
		 */
	public float Host_low_flap_threshold() throws NumberFormatException
	{
		return getAsFloat("host_low_flap_threshold");
	}
		/**
		 * Max check attempts for active host checks
		 * @return returns the value of the "host_max_check_attempts" column as int
		 */
	public int Host_max_check_attempts() throws NumberFormatException
	{
		return getAsInt("host_max_check_attempts");
	}
		/**
		 * A bitmask specifying which attributes have been modified
		 * @return returns the value of the "host_modified_attributes" column as int
		 */
	public int Host_modified_attributes() throws NumberFormatException
	{
		return getAsInt("host_modified_attributes");
	}
		/**
		 * A list of all modified attributes
		 * @return returns the value of the "host_modified_attributes_list" column as list
		 */
	public String Host_modified_attributes_list() 
	{
		return getAsList("host_modified_attributes_list");
	}
		/**
		 * Host name
		 * @return returns the value of the "host_name" column as string
		 */
	public String Host_name() 
	{
		return getAsString("host_name");
	}
		/**
		 * Scheduled time for the next check (Unix timestamp)
		 * @return returns the value of the "host_next_check" column as time
		 */
	public Date Host_next_check() throws NumberFormatException
	{
		return getAsTime("host_next_check");
	}
		/**
		 * Time of the next notification (Unix timestamp)
		 * @return returns the value of the "host_next_notification" column as time
		 */
	public Date Host_next_notification() throws NumberFormatException
	{
		return getAsTime("host_next_notification");
	}
		/**
		 * Whether to stop sending notifications (0/1)
		 * @return returns the value of the "host_no_more_notifications" column as int
		 */
	public int Host_no_more_notifications() throws NumberFormatException
	{
		return getAsInt("host_no_more_notifications");
	}
		/**
		 * Optional notes for this host
		 * @return returns the value of the "host_notes" column as string
		 */
	public String Host_notes() 
	{
		return getAsString("host_notes");
	}
		/**
		 * The same as notes, but with the most important macros expanded
		 * @return returns the value of the "host_notes_expanded" column as string
		 */
	public String Host_notes_expanded() 
	{
		return getAsString("host_notes_expanded");
	}
		/**
		 * An optional URL with further information about the host
		 * @return returns the value of the "host_notes_url" column as string
		 */
	public String Host_notes_url() 
	{
		return getAsString("host_notes_url");
	}
		/**
		 * Same es notes_url, but with the most important macros expanded
		 * @return returns the value of the "host_notes_url_expanded" column as string
		 */
	public String Host_notes_url_expanded() 
	{
		return getAsString("host_notes_url_expanded");
	}
		/**
		 * Interval of periodic notification or 0 if its off
		 * @return returns the value of the "host_notification_interval" column as float
		 */
	public float Host_notification_interval() throws NumberFormatException
	{
		return getAsFloat("host_notification_interval");
	}
		/**
		 * Time period in which problems of this host will be notified. If empty then notification will be always
		 * @return returns the value of the "host_notification_period" column as string
		 */
	public String Host_notification_period() 
	{
		return getAsString("host_notification_period");
	}
		/**
		 * Whether notifications of the host are enabled (0/1)
		 * @return returns the value of the "host_notifications_enabled" column as int
		 */
	public int Host_notifications_enabled() throws NumberFormatException
	{
		return getAsInt("host_notifications_enabled");
	}
		/**
		 * The total number of services of the host
		 * @return returns the value of the "host_num_services" column as int
		 */
	public int Host_num_services() throws NumberFormatException
	{
		return getAsInt("host_num_services");
	}
		/**
		 * The number of the host's services with the soft state CRIT
		 * @return returns the value of the "host_num_services_crit" column as int
		 */
	public int Host_num_services_crit() throws NumberFormatException
	{
		return getAsInt("host_num_services_crit");
	}
		/**
		 * The number of the host's services with the hard state CRIT
		 * @return returns the value of the "host_num_services_hard_crit" column as int
		 */
	public int Host_num_services_hard_crit() throws NumberFormatException
	{
		return getAsInt("host_num_services_hard_crit");
	}
		/**
		 * The number of the host's services with the hard state OK
		 * @return returns the value of the "host_num_services_hard_ok" column as int
		 */
	public int Host_num_services_hard_ok() throws NumberFormatException
	{
		return getAsInt("host_num_services_hard_ok");
	}
		/**
		 * The number of the host's services with the hard state UNKNOWN
		 * @return returns the value of the "host_num_services_hard_unknown" column as int
		 */
	public int Host_num_services_hard_unknown() throws NumberFormatException
	{
		return getAsInt("host_num_services_hard_unknown");
	}
		/**
		 * The number of the host's services with the hard state WARN
		 * @return returns the value of the "host_num_services_hard_warn" column as int
		 */
	public int Host_num_services_hard_warn() throws NumberFormatException
	{
		return getAsInt("host_num_services_hard_warn");
	}
		/**
		 * The number of the host's services with the soft state OK
		 * @return returns the value of the "host_num_services_ok" column as int
		 */
	public int Host_num_services_ok() throws NumberFormatException
	{
		return getAsInt("host_num_services_ok");
	}
		/**
		 * The number of the host's services which have not been checked yet (pending)
		 * @return returns the value of the "host_num_services_pending" column as int
		 */
	public int Host_num_services_pending() throws NumberFormatException
	{
		return getAsInt("host_num_services_pending");
	}
		/**
		 * The number of the host's services with the soft state UNKNOWN
		 * @return returns the value of the "host_num_services_unknown" column as int
		 */
	public int Host_num_services_unknown() throws NumberFormatException
	{
		return getAsInt("host_num_services_unknown");
	}
		/**
		 * The number of the host's services with the soft state WARN
		 * @return returns the value of the "host_num_services_warn" column as int
		 */
	public int Host_num_services_warn() throws NumberFormatException
	{
		return getAsInt("host_num_services_warn");
	}
		/**
		 * The current obsess_over_host setting... (0/1)
		 * @return returns the value of the "host_obsess_over_host" column as int
		 */
	public int Host_obsess_over_host() throws NumberFormatException
	{
		return getAsInt("host_obsess_over_host");
	}
		/**
		 * A list of all direct parents of the host
		 * @return returns the value of the "host_parents" column as list
		 */
	public String Host_parents() 
	{
		return getAsList("host_parents");
	}
		/**
		 * Whether a flex downtime is pending (0/1)
		 * @return returns the value of the "host_pending_flex_downtime" column as int
		 */
	public int Host_pending_flex_downtime() throws NumberFormatException
	{
		return getAsInt("host_pending_flex_downtime");
	}
		/**
		 * Percent state change
		 * @return returns the value of the "host_percent_state_change" column as float
		 */
	public float Host_percent_state_change() throws NumberFormatException
	{
		return getAsFloat("host_percent_state_change");
	}
		/**
		 * Optional performance data of the last host check
		 * @return returns the value of the "host_perf_data" column as string
		 */
	public String Host_perf_data() 
	{
		return getAsString("host_perf_data");
	}
		/**
		 * Output of the last host check
		 * @return returns the value of the "host_plugin_output" column as string
		 */
	public String Host_plugin_output() 
	{
		return getAsString("host_plugin_output");
	}
		/**
		 * Whether there is a PNP4Nagios graph present for this host (0/1)
		 * @return returns the value of the "host_pnpgraph_present" column as int
		 */
	public int Host_pnpgraph_present() throws NumberFormatException
	{
		return getAsInt("host_pnpgraph_present");
	}
		/**
		 * Whether processing of performance data is enabled (0/1)
		 * @return returns the value of the "host_process_performance_data" column as int
		 */
	public int Host_process_performance_data() throws NumberFormatException
	{
		return getAsInt("host_process_performance_data");
	}
		/**
		 * Number of basic interval lengths between checks when retrying after a soft error
		 * @return returns the value of the "host_retry_interval" column as float
		 */
	public float Host_retry_interval() throws NumberFormatException
	{
		return getAsFloat("host_retry_interval");
	}
		/**
		 * The number of downtimes this host is currently in
		 * @return returns the value of the "host_scheduled_downtime_depth" column as int
		 */
	public int Host_scheduled_downtime_depth() throws NumberFormatException
	{
		return getAsInt("host_scheduled_downtime_depth");
	}
		/**
		 * The name of the service period of the host
		 * @return returns the value of the "host_service_period" column as string
		 */
	public String Host_service_period() 
	{
		return getAsString("host_service_period");
	}
		/**
		 * A list of all services of the host
		 * @return returns the value of the "host_services" column as list
		 */
	public String Host_services() 
	{
		return getAsList("host_services");
	}
		/**
		 * A list of all services including detailed information about each service
		 * @return returns the value of the "host_services_with_info" column as list
		 */
	public String Host_services_with_info() 
	{
		return getAsList("host_services_with_info");
	}
		/**
		 * A list of all services of the host together with state and has_been_checked
		 * @return returns the value of the "host_services_with_state" column as list
		 */
	public String Host_services_with_state() 
	{
		return getAsList("host_services_with_state");
	}
		/**
		 * Staleness indicator for this host
		 * @return returns the value of the "host_staleness" column as float
		 */
	public float Host_staleness() throws NumberFormatException
	{
		return getAsFloat("host_staleness");
	}
		/**
		 * The current state of the host (0: up, 1: down, 2: unreachable)
		 * @return returns the value of the "host_state" column as int
		 */
	public int Host_state() throws NumberFormatException
	{
		return getAsInt("host_state");
	}
		/**
		 * Type of the current state (0: soft, 1: hard)
		 * @return returns the value of the "host_state_type" column as int
		 */
	public int Host_state_type() throws NumberFormatException
	{
		return getAsInt("host_state_type");
	}
		/**
		 * The name of in image file for the status map
		 * @return returns the value of the "host_statusmap_image" column as string
		 */
	public String Host_statusmap_image() 
	{
		return getAsString("host_statusmap_image");
	}
		/**
		 * The total number of services of the host
		 * @return returns the value of the "host_total_services" column as int
		 */
	public int Host_total_services() throws NumberFormatException
	{
		return getAsInt("host_total_services");
	}
		/**
		 * The worst hard state of all of the host's services (OK <= WARN <= UNKNOWN <= CRIT)
		 * @return returns the value of the "host_worst_service_hard_state" column as int
		 */
	public int Host_worst_service_hard_state() throws NumberFormatException
	{
		return getAsInt("host_worst_service_hard_state");
	}
		/**
		 * The worst soft state of all of the host's services (OK <= WARN <= UNKNOWN <= CRIT)
		 * @return returns the value of the "host_worst_service_state" column as int
		 */
	public int Host_worst_service_state() throws NumberFormatException
	{
		return getAsInt("host_worst_service_state");
	}
		/**
		 * 3D-Coordinates: X
		 * @return returns the value of the "host_x_3d" column as float
		 */
	public float Host_x_3d() throws NumberFormatException
	{
		return getAsFloat("host_x_3d");
	}
		/**
		 * 3D-Coordinates: Y
		 * @return returns the value of the "host_y_3d" column as float
		 */
	public float Host_y_3d() throws NumberFormatException
	{
		return getAsFloat("host_y_3d");
	}
		/**
		 * 3D-Coordinates: Z
		 * @return returns the value of the "host_z_3d" column as float
		 */
	public float Host_z_3d() throws NumberFormatException
	{
		return getAsFloat("host_z_3d");
	}
		/**
		 * The id of the comment
		 * @return returns the value of the "id" column as int
		 */
	public int Id() throws NumberFormatException
	{
		return getAsInt("id");
	}
		/**
		 * 0, if this entry is for a host, 1 if it is for a service
		 * @return returns the value of the "is_service" column as int
		 */
	public int Is_service() throws NumberFormatException
	{
		return getAsInt("is_service");
	}
		/**
		 * Whether this comment is persistent (0/1)
		 * @return returns the value of the "persistent" column as int
		 */
	public int Persistent() throws NumberFormatException
	{
		return getAsInt("persistent");
	}
		/**
		 * Whether the service accepts passive checks (0/1)
		 * @return returns the value of the "service_accept_passive_checks" column as int
		 */
	public int Service_accept_passive_checks() throws NumberFormatException
	{
		return getAsInt("service_accept_passive_checks");
	}
		/**
		 * Whether the current service problem has been acknowledged (0/1)
		 * @return returns the value of the "service_acknowledged" column as int
		 */
	public int Service_acknowledged() throws NumberFormatException
	{
		return getAsInt("service_acknowledged");
	}
		/**
		 * The type of the acknownledgement (0: none, 1: normal, 2: sticky)
		 * @return returns the value of the "service_acknowledgement_type" column as int
		 */
	public int Service_acknowledgement_type() throws NumberFormatException
	{
		return getAsInt("service_acknowledgement_type");
	}
		/**
		 * An optional URL for actions or custom information about the service
		 * @return returns the value of the "service_action_url" column as string
		 */
	public String Service_action_url() 
	{
		return getAsString("service_action_url");
	}
		/**
		 * The action_url with (the most important) macros expanded
		 * @return returns the value of the "service_action_url_expanded" column as string
		 */
	public String Service_action_url_expanded() 
	{
		return getAsString("service_action_url_expanded");
	}
		/**
		 * Whether active checks are enabled for the service (0/1)
		 * @return returns the value of the "service_active_checks_enabled" column as int
		 */
	public int Service_active_checks_enabled() throws NumberFormatException
	{
		return getAsInt("service_active_checks_enabled");
	}
		/**
		 * Nagios command used for active checks
		 * @return returns the value of the "service_check_command" column as string
		 */
	public String Service_check_command() 
	{
		return getAsString("service_check_command");
	}
		/**
		 * Nagios command used for active checks with the macros expanded
		 * @return returns the value of the "service_check_command_expanded" column as string
		 */
	public String Service_check_command_expanded() 
	{
		return getAsString("service_check_command_expanded");
	}
		/**
		 * Whether freshness checks are activated (0/1)
		 * @return returns the value of the "service_check_freshness" column as int
		 */
	public int Service_check_freshness() throws NumberFormatException
	{
		return getAsInt("service_check_freshness");
	}
		/**
		 * Number of basic interval lengths between two scheduled checks of the service
		 * @return returns the value of the "service_check_interval" column as float
		 */
	public float Service_check_interval() throws NumberFormatException
	{
		return getAsFloat("service_check_interval");
	}
		/**
		 * The current check option, forced, normal, freshness... (0/1)
		 * @return returns the value of the "service_check_options" column as int
		 */
	public int Service_check_options() throws NumberFormatException
	{
		return getAsInt("service_check_options");
	}
		/**
		 * The name of the check period of the service. It this is empty, the service is always checked.
		 * @return returns the value of the "service_check_period" column as string
		 */
	public String Service_check_period() 
	{
		return getAsString("service_check_period");
	}
		/**
		 * The type of the last check (0: active, 1: passive)
		 * @return returns the value of the "service_check_type" column as int
		 */
	public int Service_check_type() throws NumberFormatException
	{
		return getAsInt("service_check_type");
	}
		/**
		 * Whether active checks are enabled for the service (0/1)
		 * @return returns the value of the "service_checks_enabled" column as int
		 */
	public int Service_checks_enabled() throws NumberFormatException
	{
		return getAsInt("service_checks_enabled");
	}
		/**
		 * A list of all comment ids of the service
		 * @return returns the value of the "service_comments" column as list
		 */
	public String Service_comments() 
	{
		return getAsList("service_comments");
	}
		/**
		 * A list of all comments of the service with id, author, comment, entry type and entry time
		 * @return returns the value of the "service_comments_with_extra_info" column as list
		 */
	public String Service_comments_with_extra_info() 
	{
		return getAsList("service_comments_with_extra_info");
	}
		/**
		 * A list of all comments of the service with id, author and comment
		 * @return returns the value of the "service_comments_with_info" column as list
		 */
	public String Service_comments_with_info() 
	{
		return getAsList("service_comments_with_info");
	}
		/**
		 * A list of all contact groups this service is in
		 * @return returns the value of the "service_contact_groups" column as list
		 */
	public String Service_contact_groups() 
	{
		return getAsList("service_contact_groups");
	}
		/**
		 * A list of all contacts of the service, either direct or via a contact group
		 * @return returns the value of the "service_contacts" column as list
		 */
	public String Service_contacts() 
	{
		return getAsList("service_contacts");
	}
		/**
		 * The number of the current check attempt
		 * @return returns the value of the "service_current_attempt" column as int
		 */
	public int Service_current_attempt() throws NumberFormatException
	{
		return getAsInt("service_current_attempt");
	}
		/**
		 * The number of the current notification
		 * @return returns the value of the "service_current_notification_number" column as int
		 */
	public int Service_current_notification_number() throws NumberFormatException
	{
		return getAsInt("service_current_notification_number");
	}
		/**
		 * A list of the names of all custom variables of the service
		 * @return returns the value of the "service_custom_variable_names" column as list
		 */
	public String Service_custom_variable_names() 
	{
		return getAsList("service_custom_variable_names");
	}
		/**
		 * A list of the values of all custom variable of the service
		 * @return returns the value of the "service_custom_variable_values" column as list
		 */
	public String Service_custom_variable_values() 
	{
		return getAsList("service_custom_variable_values");
	}
		/**
		 * A dictionary of the custom variables
		 * @return returns the value of the "service_custom_variables" column as dict
		 */
	public String Service_custom_variables() 
	{
		return getAsDict("service_custom_variables");
	}
		/**
		 * Description of the service (also used as key)
		 * @return returns the value of the "service_description" column as string
		 */
	public String Service_description() 
	{
		return getAsString("service_description");
	}
		/**
		 * An optional display name (not used by Nagios standard web pages)
		 * @return returns the value of the "service_display_name" column as string
		 */
	public String Service_display_name() 
	{
		return getAsString("service_display_name");
	}
		/**
		 * A list of all downtime ids of the service
		 * @return returns the value of the "service_downtimes" column as list
		 */
	public String Service_downtimes() 
	{
		return getAsList("service_downtimes");
	}
		/**
		 * A list of all downtimes of the service with id, author and comment
		 * @return returns the value of the "service_downtimes_with_info" column as list
		 */
	public String Service_downtimes_with_info() 
	{
		return getAsList("service_downtimes_with_info");
	}
		/**
		 * Nagios command used as event handler
		 * @return returns the value of the "service_event_handler" column as string
		 */
	public String Service_event_handler() 
	{
		return getAsString("service_event_handler");
	}
		/**
		 * Whether and event handler is activated for the service (0/1)
		 * @return returns the value of the "service_event_handler_enabled" column as int
		 */
	public int Service_event_handler_enabled() throws NumberFormatException
	{
		return getAsInt("service_event_handler_enabled");
	}
		/**
		 * Time the service check needed for execution
		 * @return returns the value of the "service_execution_time" column as float
		 */
	public float Service_execution_time() throws NumberFormatException
	{
		return getAsFloat("service_execution_time");
	}
		/**
		 * Delay before the first notification
		 * @return returns the value of the "service_first_notification_delay" column as float
		 */
	public float Service_first_notification_delay() throws NumberFormatException
	{
		return getAsFloat("service_first_notification_delay");
	}
		/**
		 * Whether flap detection is enabled for the service (0/1)
		 * @return returns the value of the "service_flap_detection_enabled" column as int
		 */
	public int Service_flap_detection_enabled() throws NumberFormatException
	{
		return getAsInt("service_flap_detection_enabled");
	}
		/**
		 * A list of all service groups the service is in
		 * @return returns the value of the "service_groups" column as list
		 */
	public String Service_groups() 
	{
		return getAsList("service_groups");
	}
		/**
		 * Whether the service already has been checked (0/1)
		 * @return returns the value of the "service_has_been_checked" column as int
		 */
	public int Service_has_been_checked() throws NumberFormatException
	{
		return getAsInt("service_has_been_checked");
	}
		/**
		 * High threshold of flap detection
		 * @return returns the value of the "service_high_flap_threshold" column as float
		 */
	public float Service_high_flap_threshold() throws NumberFormatException
	{
		return getAsFloat("service_high_flap_threshold");
	}
		/**
		 * The name of an image to be used as icon in the web interface
		 * @return returns the value of the "service_icon_image" column as string
		 */
	public String Service_icon_image() 
	{
		return getAsString("service_icon_image");
	}
		/**
		 * An alternative text for the icon_image for browsers not displaying icons
		 * @return returns the value of the "service_icon_image_alt" column as string
		 */
	public String Service_icon_image_alt() 
	{
		return getAsString("service_icon_image_alt");
	}
		/**
		 * The icon_image with (the most important) macros expanded
		 * @return returns the value of the "service_icon_image_expanded" column as string
		 */
	public String Service_icon_image_expanded() 
	{
		return getAsString("service_icon_image_expanded");
	}
		/**
		 * Whether the service is currently in its check period (0/1)
		 * @return returns the value of the "service_in_check_period" column as int
		 */
	public int Service_in_check_period() throws NumberFormatException
	{
		return getAsInt("service_in_check_period");
	}
		/**
		 * Whether the service is currently in its notification period (0/1)
		 * @return returns the value of the "service_in_notification_period" column as int
		 */
	public int Service_in_notification_period() throws NumberFormatException
	{
		return getAsInt("service_in_notification_period");
	}
		/**
		 * Whether this service is currently in its service period (0/1)
		 * @return returns the value of the "service_in_service_period" column as int
		 */
	public int Service_in_service_period() throws NumberFormatException
	{
		return getAsInt("service_in_service_period");
	}
		/**
		 * The initial state of the service
		 * @return returns the value of the "service_initial_state" column as int
		 */
	public int Service_initial_state() throws NumberFormatException
	{
		return getAsInt("service_initial_state");
	}
		/**
		 * is there a service check currently running... (0/1)
		 * @return returns the value of the "service_is_executing" column as int
		 */
	public int Service_is_executing() throws NumberFormatException
	{
		return getAsInt("service_is_executing");
	}
		/**
		 * Whether the service is flapping (0/1)
		 * @return returns the value of the "service_is_flapping" column as int
		 */
	public int Service_is_flapping() throws NumberFormatException
	{
		return getAsInt("service_is_flapping");
	}
		/**
		 * The time of the last check (Unix timestamp)
		 * @return returns the value of the "service_last_check" column as time
		 */
	public Date Service_last_check() throws NumberFormatException
	{
		return getAsTime("service_last_check");
	}
		/**
		 * The last hard state of the service
		 * @return returns the value of the "service_last_hard_state" column as int
		 */
	public int Service_last_hard_state() throws NumberFormatException
	{
		return getAsInt("service_last_hard_state");
	}
		/**
		 * The time of the last hard state change (Unix timestamp)
		 * @return returns the value of the "service_last_hard_state_change" column as time
		 */
	public Date Service_last_hard_state_change() throws NumberFormatException
	{
		return getAsTime("service_last_hard_state_change");
	}
		/**
		 * The time of the last notification (Unix timestamp)
		 * @return returns the value of the "service_last_notification" column as time
		 */
	public Date Service_last_notification() throws NumberFormatException
	{
		return getAsTime("service_last_notification");
	}
		/**
		 * The last state of the service
		 * @return returns the value of the "service_last_state" column as int
		 */
	public int Service_last_state() throws NumberFormatException
	{
		return getAsInt("service_last_state");
	}
		/**
		 * The time of the last state change (Unix timestamp)
		 * @return returns the value of the "service_last_state_change" column as time
		 */
	public Date Service_last_state_change() throws NumberFormatException
	{
		return getAsTime("service_last_state_change");
	}
		/**
		 * The last time the service was CRITICAL (Unix timestamp)
		 * @return returns the value of the "service_last_time_critical" column as time
		 */
	public Date Service_last_time_critical() throws NumberFormatException
	{
		return getAsTime("service_last_time_critical");
	}
		/**
		 * The last time the service was OK (Unix timestamp)
		 * @return returns the value of the "service_last_time_ok" column as time
		 */
	public Date Service_last_time_ok() throws NumberFormatException
	{
		return getAsTime("service_last_time_ok");
	}
		/**
		 * The last time the service was UNKNOWN (Unix timestamp)
		 * @return returns the value of the "service_last_time_unknown" column as time
		 */
	public Date Service_last_time_unknown() throws NumberFormatException
	{
		return getAsTime("service_last_time_unknown");
	}
		/**
		 * The last time the service was in WARNING state (Unix timestamp)
		 * @return returns the value of the "service_last_time_warning" column as time
		 */
	public Date Service_last_time_warning() throws NumberFormatException
	{
		return getAsTime("service_last_time_warning");
	}
		/**
		 * Time difference between scheduled check time and actual check time
		 * @return returns the value of the "service_latency" column as float
		 */
	public float Service_latency() throws NumberFormatException
	{
		return getAsFloat("service_latency");
	}
		/**
		 * Unabbreviated output of the last check plugin
		 * @return returns the value of the "service_long_plugin_output" column as string
		 */
	public String Service_long_plugin_output() 
	{
		return getAsString("service_long_plugin_output");
	}
		/**
		 * Low threshold of flap detection
		 * @return returns the value of the "service_low_flap_threshold" column as float
		 */
	public float Service_low_flap_threshold() throws NumberFormatException
	{
		return getAsFloat("service_low_flap_threshold");
	}
		/**
		 * The maximum number of check attempts
		 * @return returns the value of the "service_max_check_attempts" column as int
		 */
	public int Service_max_check_attempts() throws NumberFormatException
	{
		return getAsInt("service_max_check_attempts");
	}
		/**
		 * A bitmask specifying which attributes have been modified
		 * @return returns the value of the "service_modified_attributes" column as int
		 */
	public int Service_modified_attributes() throws NumberFormatException
	{
		return getAsInt("service_modified_attributes");
	}
		/**
		 * A list of all modified attributes
		 * @return returns the value of the "service_modified_attributes_list" column as list
		 */
	public String Service_modified_attributes_list() 
	{
		return getAsList("service_modified_attributes_list");
	}
		/**
		 * The scheduled time of the next check (Unix timestamp)
		 * @return returns the value of the "service_next_check" column as time
		 */
	public Date Service_next_check() throws NumberFormatException
	{
		return getAsTime("service_next_check");
	}
		/**
		 * The time of the next notification (Unix timestamp)
		 * @return returns the value of the "service_next_notification" column as time
		 */
	public Date Service_next_notification() throws NumberFormatException
	{
		return getAsTime("service_next_notification");
	}
		/**
		 * Whether to stop sending notifications (0/1)
		 * @return returns the value of the "service_no_more_notifications" column as int
		 */
	public int Service_no_more_notifications() throws NumberFormatException
	{
		return getAsInt("service_no_more_notifications");
	}
		/**
		 * Optional notes about the service
		 * @return returns the value of the "service_notes" column as string
		 */
	public String Service_notes() 
	{
		return getAsString("service_notes");
	}
		/**
		 * The notes with (the most important) macros expanded
		 * @return returns the value of the "service_notes_expanded" column as string
		 */
	public String Service_notes_expanded() 
	{
		return getAsString("service_notes_expanded");
	}
		/**
		 * An optional URL for additional notes about the service
		 * @return returns the value of the "service_notes_url" column as string
		 */
	public String Service_notes_url() 
	{
		return getAsString("service_notes_url");
	}
		/**
		 * The notes_url with (the most important) macros expanded
		 * @return returns the value of the "service_notes_url_expanded" column as string
		 */
	public String Service_notes_url_expanded() 
	{
		return getAsString("service_notes_url_expanded");
	}
		/**
		 * Interval of periodic notification or 0 if its off
		 * @return returns the value of the "service_notification_interval" column as float
		 */
	public float Service_notification_interval() throws NumberFormatException
	{
		return getAsFloat("service_notification_interval");
	}
		/**
		 * The name of the notification period of the service. It this is empty, service problems are always notified.
		 * @return returns the value of the "service_notification_period" column as string
		 */
	public String Service_notification_period() 
	{
		return getAsString("service_notification_period");
	}
		/**
		 * Whether notifications are enabled for the service (0/1)
		 * @return returns the value of the "service_notifications_enabled" column as int
		 */
	public int Service_notifications_enabled() throws NumberFormatException
	{
		return getAsInt("service_notifications_enabled");
	}
		/**
		 * Whether 'obsess_over_service' is enabled for the service (0/1)
		 * @return returns the value of the "service_obsess_over_service" column as int
		 */
	public int Service_obsess_over_service() throws NumberFormatException
	{
		return getAsInt("service_obsess_over_service");
	}
		/**
		 * Percent state change
		 * @return returns the value of the "service_percent_state_change" column as float
		 */
	public float Service_percent_state_change() throws NumberFormatException
	{
		return getAsFloat("service_percent_state_change");
	}
		/**
		 * Performance data of the last check plugin
		 * @return returns the value of the "service_perf_data" column as string
		 */
	public String Service_perf_data() 
	{
		return getAsString("service_perf_data");
	}
		/**
		 * Output of the last check plugin
		 * @return returns the value of the "service_plugin_output" column as string
		 */
	public String Service_plugin_output() 
	{
		return getAsString("service_plugin_output");
	}
		/**
		 * Whether there is a PNP4Nagios graph present for this service (0/1)
		 * @return returns the value of the "service_pnpgraph_present" column as int
		 */
	public int Service_pnpgraph_present() throws NumberFormatException
	{
		return getAsInt("service_pnpgraph_present");
	}
		/**
		 * Whether processing of performance data is enabled for the service (0/1)
		 * @return returns the value of the "service_process_performance_data" column as int
		 */
	public int Service_process_performance_data() throws NumberFormatException
	{
		return getAsInt("service_process_performance_data");
	}
		/**
		 * Number of basic interval lengths between checks when retrying after a soft error
		 * @return returns the value of the "service_retry_interval" column as float
		 */
	public float Service_retry_interval() throws NumberFormatException
	{
		return getAsFloat("service_retry_interval");
	}
		/**
		 * The number of scheduled downtimes the service is currently in
		 * @return returns the value of the "service_scheduled_downtime_depth" column as int
		 */
	public int Service_scheduled_downtime_depth() throws NumberFormatException
	{
		return getAsInt("service_scheduled_downtime_depth");
	}
		/**
		 * The name of the service period of the service
		 * @return returns the value of the "service_service_period" column as string
		 */
	public String Service_service_period() 
	{
		return getAsString("service_service_period");
	}
		/**
		 * The staleness indicator for this service
		 * @return returns the value of the "service_staleness" column as float
		 */
	public float Service_staleness() throws NumberFormatException
	{
		return getAsFloat("service_staleness");
	}
		/**
		 * The current state of the service (0: OK, 1: WARN, 2: CRITICAL, 3: UNKNOWN)
		 * @return returns the value of the "service_state" column as int
		 */
	public int Service_state() throws NumberFormatException
	{
		return getAsInt("service_state");
	}
		/**
		 * The type of the current state (0: soft, 1: hard)
		 * @return returns the value of the "service_state_type" column as int
		 */
	public int Service_state_type() throws NumberFormatException
	{
		return getAsInt("service_state_type");
	}
		/**
		 * The source of the comment (0 is internal and 1 is external)
		 * @return returns the value of the "source" column as int
		 */
	public int Source() throws NumberFormatException
	{
		return getAsInt("source");
	}
		/**
		 * The type of the comment: 1 is host, 2 is service
		 * @return returns the value of the "type" column as int
		 */
	public int Type() throws NumberFormatException
	{
		return getAsInt("type");
	}
		/**
	 	* create the map for all columns of table  Comments. Key=column name, Value=column value
	 	*
   	* @param table LiveStatus table
   	* @param filter filter to applay for this table
	 	* @return Map<String, String>
	 	*/

	@Override
	public Map<String, String> asArrayString(String table, String filter) throws NumberFormatException {
		mapKeyValue.clear();
		setMapObjects(table, filter);

		addToHashtable("author", (Author()));
		addToHashtable("comment", (Comment()));
		addToHashtable("entry_time", getAsString(Entry_time()));
		addToHashtable("entry_type", getAsString(Entry_type()));
		addToHashtable("expire_time", getAsString(Expire_time()));
		addToHashtable("expires", getAsString(Expires()));
		addToHashtable("host_accept_passive_checks", getAsString(Host_accept_passive_checks()));
		addToHashtable("host_acknowledged", getAsString(Host_acknowledged()));
		addToHashtable("host_acknowledgement_type", getAsString(Host_acknowledgement_type()));
		addToHashtable("host_action_url", (Host_action_url()));
		addToHashtable("host_action_url_expanded", (Host_action_url_expanded()));
		addToHashtable("host_active_checks_enabled", getAsString(Host_active_checks_enabled()));
		addToHashtable("host_address", (Host_address()));
		addToHashtable("host_alias", (Host_alias()));
		addToHashtable("host_check_command", (Host_check_command()));
		addToHashtable("host_check_command_expanded", (Host_check_command_expanded()));
		addToHashtable("host_check_flapping_recovery_notification", getAsString(Host_check_flapping_recovery_notification()));
		addToHashtable("host_check_freshness", getAsString(Host_check_freshness()));
		addToHashtable("host_check_interval", getAsString(Host_check_interval()));
		addToHashtable("host_check_options", getAsString(Host_check_options()));
		addToHashtable("host_check_period", (Host_check_period()));
		addToHashtable("host_check_type", getAsString(Host_check_type()));
		addToHashtable("host_checks_enabled", getAsString(Host_checks_enabled()));
		addToHashtable("host_childs", (Host_childs()));
		addToHashtable("host_comments", (Host_comments()));
		addToHashtable("host_comments_with_extra_info", (Host_comments_with_extra_info()));
		addToHashtable("host_comments_with_info", (Host_comments_with_info()));
		addToHashtable("host_contact_groups", (Host_contact_groups()));
		addToHashtable("host_contacts", (Host_contacts()));
		addToHashtable("host_current_attempt", getAsString(Host_current_attempt()));
		addToHashtable("host_current_notification_number", getAsString(Host_current_notification_number()));
		addToHashtable("host_custom_variable_names", (Host_custom_variable_names()));
		addToHashtable("host_custom_variable_values", (Host_custom_variable_values()));
		addToHashtable("host_custom_variables", (Host_custom_variables()));
		addToHashtable("host_display_name", (Host_display_name()));
		addToHashtable("host_downtimes", (Host_downtimes()));
		addToHashtable("host_downtimes_with_info", (Host_downtimes_with_info()));
		addToHashtable("host_event_handler", (Host_event_handler()));
		addToHashtable("host_event_handler_enabled", getAsString(Host_event_handler_enabled()));
		addToHashtable("host_execution_time", getAsString(Host_execution_time()));
		addToHashtable("host_filename", (Host_filename()));
		addToHashtable("host_first_notification_delay", getAsString(Host_first_notification_delay()));
		addToHashtable("host_flap_detection_enabled", getAsString(Host_flap_detection_enabled()));
		addToHashtable("host_groups", (Host_groups()));
		addToHashtable("host_hard_state", getAsString(Host_hard_state()));
		addToHashtable("host_has_been_checked", getAsString(Host_has_been_checked()));
		addToHashtable("host_high_flap_threshold", getAsString(Host_high_flap_threshold()));
		addToHashtable("host_icon_image", (Host_icon_image()));
		addToHashtable("host_icon_image_alt", (Host_icon_image_alt()));
		addToHashtable("host_icon_image_expanded", (Host_icon_image_expanded()));
		addToHashtable("host_in_check_period", getAsString(Host_in_check_period()));
		addToHashtable("host_in_notification_period", getAsString(Host_in_notification_period()));
		addToHashtable("host_in_service_period", getAsString(Host_in_service_period()));
		addToHashtable("host_initial_state", getAsString(Host_initial_state()));
		addToHashtable("host_is_executing", getAsString(Host_is_executing()));
		addToHashtable("host_is_flapping", getAsString(Host_is_flapping()));
		addToHashtable("host_last_check", getAsString(Host_last_check()));
		addToHashtable("host_last_hard_state", getAsString(Host_last_hard_state()));
		addToHashtable("host_last_hard_state_change", getAsString(Host_last_hard_state_change()));
		addToHashtable("host_last_notification", getAsString(Host_last_notification()));
		addToHashtable("host_last_state", getAsString(Host_last_state()));
		addToHashtable("host_last_state_change", getAsString(Host_last_state_change()));
		addToHashtable("host_last_time_down", getAsString(Host_last_time_down()));
		addToHashtable("host_last_time_unreachable", getAsString(Host_last_time_unreachable()));
		addToHashtable("host_last_time_up", getAsString(Host_last_time_up()));
		addToHashtable("host_latency", getAsString(Host_latency()));
		addToHashtable("host_long_plugin_output", (Host_long_plugin_output()));
		addToHashtable("host_low_flap_threshold", getAsString(Host_low_flap_threshold()));
		addToHashtable("host_max_check_attempts", getAsString(Host_max_check_attempts()));
		addToHashtable("host_modified_attributes", getAsString(Host_modified_attributes()));
		addToHashtable("host_modified_attributes_list", (Host_modified_attributes_list()));
		addToHashtable("host_name", (Host_name()));
		addToHashtable("host_next_check", getAsString(Host_next_check()));
		addToHashtable("host_next_notification", getAsString(Host_next_notification()));
		addToHashtable("host_no_more_notifications", getAsString(Host_no_more_notifications()));
		addToHashtable("host_notes", (Host_notes()));
		addToHashtable("host_notes_expanded", (Host_notes_expanded()));
		addToHashtable("host_notes_url", (Host_notes_url()));
		addToHashtable("host_notes_url_expanded", (Host_notes_url_expanded()));
		addToHashtable("host_notification_interval", getAsString(Host_notification_interval()));
		addToHashtable("host_notification_period", (Host_notification_period()));
		addToHashtable("host_notifications_enabled", getAsString(Host_notifications_enabled()));
		addToHashtable("host_num_services", getAsString(Host_num_services()));
		addToHashtable("host_num_services_crit", getAsString(Host_num_services_crit()));
		addToHashtable("host_num_services_hard_crit", getAsString(Host_num_services_hard_crit()));
		addToHashtable("host_num_services_hard_ok", getAsString(Host_num_services_hard_ok()));
		addToHashtable("host_num_services_hard_unknown", getAsString(Host_num_services_hard_unknown()));
		addToHashtable("host_num_services_hard_warn", getAsString(Host_num_services_hard_warn()));
		addToHashtable("host_num_services_ok", getAsString(Host_num_services_ok()));
		addToHashtable("host_num_services_pending", getAsString(Host_num_services_pending()));
		addToHashtable("host_num_services_unknown", getAsString(Host_num_services_unknown()));
		addToHashtable("host_num_services_warn", getAsString(Host_num_services_warn()));
		addToHashtable("host_obsess_over_host", getAsString(Host_obsess_over_host()));
		addToHashtable("host_parents", (Host_parents()));
		addToHashtable("host_pending_flex_downtime", getAsString(Host_pending_flex_downtime()));
		addToHashtable("host_percent_state_change", getAsString(Host_percent_state_change()));
		addToHashtable("host_perf_data", (Host_perf_data()));
		addToHashtable("host_plugin_output", (Host_plugin_output()));
		addToHashtable("host_pnpgraph_present", getAsString(Host_pnpgraph_present()));
		addToHashtable("host_process_performance_data", getAsString(Host_process_performance_data()));
		addToHashtable("host_retry_interval", getAsString(Host_retry_interval()));
		addToHashtable("host_scheduled_downtime_depth", getAsString(Host_scheduled_downtime_depth()));
		addToHashtable("host_service_period", (Host_service_period()));
		addToHashtable("host_services", (Host_services()));
		addToHashtable("host_services_with_info", (Host_services_with_info()));
		addToHashtable("host_services_with_state", (Host_services_with_state()));
		addToHashtable("host_staleness", getAsString(Host_staleness()));
		addToHashtable("host_state", getAsString(Host_state()));
		addToHashtable("host_state_type", getAsString(Host_state_type()));
		addToHashtable("host_statusmap_image", (Host_statusmap_image()));
		addToHashtable("host_total_services", getAsString(Host_total_services()));
		addToHashtable("host_worst_service_hard_state", getAsString(Host_worst_service_hard_state()));
		addToHashtable("host_worst_service_state", getAsString(Host_worst_service_state()));
		addToHashtable("host_x_3d", getAsString(Host_x_3d()));
		addToHashtable("host_y_3d", getAsString(Host_y_3d()));
		addToHashtable("host_z_3d", getAsString(Host_z_3d()));
		addToHashtable("id", getAsString(Id()));
		addToHashtable("is_service", getAsString(Is_service()));
		addToHashtable("persistent", getAsString(Persistent()));
		addToHashtable("service_accept_passive_checks", getAsString(Service_accept_passive_checks()));
		addToHashtable("service_acknowledged", getAsString(Service_acknowledged()));
		addToHashtable("service_acknowledgement_type", getAsString(Service_acknowledgement_type()));
		addToHashtable("service_action_url", (Service_action_url()));
		addToHashtable("service_action_url_expanded", (Service_action_url_expanded()));
		addToHashtable("service_active_checks_enabled", getAsString(Service_active_checks_enabled()));
		addToHashtable("service_check_command", (Service_check_command()));
		addToHashtable("service_check_command_expanded", (Service_check_command_expanded()));
		addToHashtable("service_check_freshness", getAsString(Service_check_freshness()));
		addToHashtable("service_check_interval", getAsString(Service_check_interval()));
		addToHashtable("service_check_options", getAsString(Service_check_options()));
		addToHashtable("service_check_period", (Service_check_period()));
		addToHashtable("service_check_type", getAsString(Service_check_type()));
		addToHashtable("service_checks_enabled", getAsString(Service_checks_enabled()));
		addToHashtable("service_comments", (Service_comments()));
		addToHashtable("service_comments_with_extra_info", (Service_comments_with_extra_info()));
		addToHashtable("service_comments_with_info", (Service_comments_with_info()));
		addToHashtable("service_contact_groups", (Service_contact_groups()));
		addToHashtable("service_contacts", (Service_contacts()));
		addToHashtable("service_current_attempt", getAsString(Service_current_attempt()));
		addToHashtable("service_current_notification_number", getAsString(Service_current_notification_number()));
		addToHashtable("service_custom_variable_names", (Service_custom_variable_names()));
		addToHashtable("service_custom_variable_values", (Service_custom_variable_values()));
		addToHashtable("service_custom_variables", (Service_custom_variables()));
		addToHashtable("service_description", (Service_description()));
		addToHashtable("service_display_name", (Service_display_name()));
		addToHashtable("service_downtimes", (Service_downtimes()));
		addToHashtable("service_downtimes_with_info", (Service_downtimes_with_info()));
		addToHashtable("service_event_handler", (Service_event_handler()));
		addToHashtable("service_event_handler_enabled", getAsString(Service_event_handler_enabled()));
		addToHashtable("service_execution_time", getAsString(Service_execution_time()));
		addToHashtable("service_first_notification_delay", getAsString(Service_first_notification_delay()));
		addToHashtable("service_flap_detection_enabled", getAsString(Service_flap_detection_enabled()));
		addToHashtable("service_groups", (Service_groups()));
		addToHashtable("service_has_been_checked", getAsString(Service_has_been_checked()));
		addToHashtable("service_high_flap_threshold", getAsString(Service_high_flap_threshold()));
		addToHashtable("service_icon_image", (Service_icon_image()));
		addToHashtable("service_icon_image_alt", (Service_icon_image_alt()));
		addToHashtable("service_icon_image_expanded", (Service_icon_image_expanded()));
		addToHashtable("service_in_check_period", getAsString(Service_in_check_period()));
		addToHashtable("service_in_notification_period", getAsString(Service_in_notification_period()));
		addToHashtable("service_in_service_period", getAsString(Service_in_service_period()));
		addToHashtable("service_initial_state", getAsString(Service_initial_state()));
		addToHashtable("service_is_executing", getAsString(Service_is_executing()));
		addToHashtable("service_is_flapping", getAsString(Service_is_flapping()));
		addToHashtable("service_last_check", getAsString(Service_last_check()));
		addToHashtable("service_last_hard_state", getAsString(Service_last_hard_state()));
		addToHashtable("service_last_hard_state_change", getAsString(Service_last_hard_state_change()));
		addToHashtable("service_last_notification", getAsString(Service_last_notification()));
		addToHashtable("service_last_state", getAsString(Service_last_state()));
		addToHashtable("service_last_state_change", getAsString(Service_last_state_change()));
		addToHashtable("service_last_time_critical", getAsString(Service_last_time_critical()));
		addToHashtable("service_last_time_ok", getAsString(Service_last_time_ok()));
		addToHashtable("service_last_time_unknown", getAsString(Service_last_time_unknown()));
		addToHashtable("service_last_time_warning", getAsString(Service_last_time_warning()));
		addToHashtable("service_latency", getAsString(Service_latency()));
		addToHashtable("service_long_plugin_output", (Service_long_plugin_output()));
		addToHashtable("service_low_flap_threshold", getAsString(Service_low_flap_threshold()));
		addToHashtable("service_max_check_attempts", getAsString(Service_max_check_attempts()));
		addToHashtable("service_modified_attributes", getAsString(Service_modified_attributes()));
		addToHashtable("service_modified_attributes_list", (Service_modified_attributes_list()));
		addToHashtable("service_next_check", getAsString(Service_next_check()));
		addToHashtable("service_next_notification", getAsString(Service_next_notification()));
		addToHashtable("service_no_more_notifications", getAsString(Service_no_more_notifications()));
		addToHashtable("service_notes", (Service_notes()));
		addToHashtable("service_notes_expanded", (Service_notes_expanded()));
		addToHashtable("service_notes_url", (Service_notes_url()));
		addToHashtable("service_notes_url_expanded", (Service_notes_url_expanded()));
		addToHashtable("service_notification_interval", getAsString(Service_notification_interval()));
		addToHashtable("service_notification_period", (Service_notification_period()));
		addToHashtable("service_notifications_enabled", getAsString(Service_notifications_enabled()));
		addToHashtable("service_obsess_over_service", getAsString(Service_obsess_over_service()));
		addToHashtable("service_percent_state_change", getAsString(Service_percent_state_change()));
		addToHashtable("service_perf_data", (Service_perf_data()));
		addToHashtable("service_plugin_output", (Service_plugin_output()));
		addToHashtable("service_pnpgraph_present", getAsString(Service_pnpgraph_present()));
		addToHashtable("service_process_performance_data", getAsString(Service_process_performance_data()));
		addToHashtable("service_retry_interval", getAsString(Service_retry_interval()));
		addToHashtable("service_scheduled_downtime_depth", getAsString(Service_scheduled_downtime_depth()));
		addToHashtable("service_service_period", (Service_service_period()));
		addToHashtable("service_staleness", getAsString(Service_staleness()));
		addToHashtable("service_state", getAsString(Service_state()));
		addToHashtable("service_state_type", getAsString(Service_state_type()));
		addToHashtable("source", getAsString(Source()));
		addToHashtable("type", getAsString(Type()));
		return mapKeyValue;
	}
	}
