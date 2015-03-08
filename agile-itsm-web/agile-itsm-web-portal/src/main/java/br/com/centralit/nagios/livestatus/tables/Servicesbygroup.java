/*****************************************************************************
 * Servicesbygroup.java - 
 *
 * Copyright (c) 2014 Projeto citsmart (Contact: adenir.gomes@centralit.com.br)
 *
 * License:
 *****************************************************************************/
package br.com.centralit.nagios.livestatus.tables;

import java.util.Date;
import java.util.Map;


	/**
 	* Class Servicesbygroup is the main class for obtain all columns of table "servicesbygroup" 
from a Livestatus TCP-socket/file status.dat.
 	*
 	* @author	Adenir Ribeiro Gomes
 	*/ 

	public class Servicesbygroup extends LiveStatusBase
	{
		/**
	 	* Constructor of table Servicesbygroup
	 	*
	 	* @param path = "tcp://host:port" File : where path is the path to the file
	 	*/
	public Servicesbygroup(String path)
	{
		super(path);
		initializeMaps();
		tableName = "servicesbygroup";
	}
		/**
	 	* create the map for all columns description of table Servicesbygroup. Key=column name, Value=column description
	 	*
	 	*/
	public final void initializeMaps()
	{
		mapComments.put("accept_passive_checks", "Whether the service accepts passive checks (0/1)");
		mapComments.put("acknowledged", "Whether the current service problem has been acknowledged (0/1)");
		mapComments.put("acknowledgement_type", "The type of the acknownledgement (0: none, 1: normal, 2: sticky)");
		mapComments.put("action_url", "An optional URL for actions or custom information about the service");
		mapComments.put("action_url_expanded", "The action_url with (the most important) macros expanded");
		mapComments.put("active_checks_enabled", "Whether active checks are enabled for the service (0/1)");
		mapComments.put("check_command", "Nagios command used for active checks");
		mapComments.put("check_command_expanded", "Nagios command used for active checks with the macros expanded");
		mapComments.put("check_freshness", "Whether freshness checks are activated (0/1)");
		mapComments.put("check_interval", "Number of basic interval lengths between two scheduled checks of the service");
		mapComments.put("check_options", "The current check option, forced, normal, freshness... (0/1)");
		mapComments.put("check_period", "The name of the check period of the service. It this is empty, the service is always checked.");
		mapComments.put("check_type", "The type of the last check (0: active, 1: passive)");
		mapComments.put("checks_enabled", "Whether active checks are enabled for the service (0/1)");
		mapComments.put("comments", "A list of all comment ids of the service");
		mapComments.put("comments_with_extra_info", "A list of all comments of the service with id, author, comment, entry type and entry time");
		mapComments.put("comments_with_info", "A list of all comments of the service with id, author and comment");
		mapComments.put("contact_groups", "A list of all contact groups this service is in");
		mapComments.put("contacts", "A list of all contacts of the service, either direct or via a contact group");
		mapComments.put("current_attempt", "The number of the current check attempt");
		mapComments.put("current_notification_number", "The number of the current notification");
		mapComments.put("custom_variable_names", "A list of the names of all custom variables of the service");
		mapComments.put("custom_variable_values", "A list of the values of all custom variable of the service");
		mapComments.put("custom_variables", "A dictionary of the custom variables");
		mapComments.put("description", "Description of the service (also used as key)");
		mapComments.put("display_name", "An optional display name (not used by Nagios standard web pages)");
		mapComments.put("downtimes", "A list of all downtime ids of the service");
		mapComments.put("downtimes_with_info", "A list of all downtimes of the service with id, author and comment");
		mapComments.put("event_handler", "Nagios command used as event handler");
		mapComments.put("event_handler_enabled", "Whether and event handler is activated for the service (0/1)");
		mapComments.put("execution_time", "Time the service check needed for execution");
		mapComments.put("first_notification_delay", "Delay before the first notification");
		mapComments.put("flap_detection_enabled", "Whether flap detection is enabled for the service (0/1)");
		mapComments.put("groups", "A list of all service groups the service is in");
		mapComments.put("has_been_checked", "Whether the service already has been checked (0/1)");
		mapComments.put("high_flap_threshold", "High threshold of flap detection");
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
		mapComments.put("icon_image", "The name of an image to be used as icon in the web interface");
		mapComments.put("icon_image_alt", "An alternative text for the icon_image for browsers not displaying icons");
		mapComments.put("icon_image_expanded", "The icon_image with (the most important) macros expanded");
		mapComments.put("in_check_period", "Whether the service is currently in its check period (0/1)");
		mapComments.put("in_notification_period", "Whether the service is currently in its notification period (0/1)");
		mapComments.put("in_service_period", "Whether this service is currently in its service period (0/1)");
		mapComments.put("initial_state", "The initial state of the service");
		mapComments.put("is_executing", "is there a service check currently running... (0/1)");
		mapComments.put("is_flapping", "Whether the service is flapping (0/1)");
		mapComments.put("last_check", "The time of the last check (Unix timestamp)");
		mapComments.put("last_hard_state", "The last hard state of the service");
		mapComments.put("last_hard_state_change", "The time of the last hard state change (Unix timestamp)");
		mapComments.put("last_notification", "The time of the last notification (Unix timestamp)");
		mapComments.put("last_state", "The last state of the service");
		mapComments.put("last_state_change", "The time of the last state change (Unix timestamp)");
		mapComments.put("last_time_critical", "The last time the service was CRITICAL (Unix timestamp)");
		mapComments.put("last_time_ok", "The last time the service was OK (Unix timestamp)");
		mapComments.put("last_time_unknown", "The last time the service was UNKNOWN (Unix timestamp)");
		mapComments.put("last_time_warning", "The last time the service was in WARNING state (Unix timestamp)");
		mapComments.put("latency", "Time difference between scheduled check time and actual check time");
		mapComments.put("long_plugin_output", "Unabbreviated output of the last check plugin");
		mapComments.put("low_flap_threshold", "Low threshold of flap detection");
		mapComments.put("max_check_attempts", "The maximum number of check attempts");
		mapComments.put("modified_attributes", "A bitmask specifying which attributes have been modified");
		mapComments.put("modified_attributes_list", "A list of all modified attributes");
		mapComments.put("next_check", "The scheduled time of the next check (Unix timestamp)");
		mapComments.put("next_notification", "The time of the next notification (Unix timestamp)");
		mapComments.put("no_more_notifications", "Whether to stop sending notifications (0/1)");
		mapComments.put("notes", "Optional notes about the service");
		mapComments.put("notes_expanded", "The notes with (the most important) macros expanded");
		mapComments.put("notes_url", "An optional URL for additional notes about the service");
		mapComments.put("notes_url_expanded", "The notes_url with (the most important) macros expanded");
		mapComments.put("notification_interval", "Interval of periodic notification or 0 if its off");
		mapComments.put("notification_period", "The name of the notification period of the service. It this is empty, service problems are always notified.");
		mapComments.put("notifications_enabled", "Whether notifications are enabled for the service (0/1)");
		mapComments.put("obsess_over_service", "Whether 'obsess_over_service' is enabled for the service (0/1)");
		mapComments.put("percent_state_change", "Percent state change");
		mapComments.put("perf_data", "Performance data of the last check plugin");
		mapComments.put("plugin_output", "Output of the last check plugin");
		mapComments.put("pnpgraph_present", "Whether there is a PNP4Nagios graph present for this service (0/1)");
		mapComments.put("process_performance_data", "Whether processing of performance data is enabled for the service (0/1)");
		mapComments.put("retry_interval", "Number of basic interval lengths between checks when retrying after a soft error");
		mapComments.put("scheduled_downtime_depth", "The number of scheduled downtimes the service is currently in");
		mapComments.put("service_period", "The name of the service period of the service");
		mapComments.put("servicegroup_action_url", "An optional URL to custom notes or actions on the service group");
		mapComments.put("servicegroup_alias", "An alias of the service group");
		mapComments.put("servicegroup_members", "A list of all members of the service group as host/service pairs");
		mapComments.put("servicegroup_members_with_state", "A list of all members of the service group with state and has_been_checked");
		mapComments.put("servicegroup_name", "The name of the service group");
		mapComments.put("servicegroup_notes", "Optional additional notes about the service group");
		mapComments.put("servicegroup_notes_url", "An optional URL to further notes on the service group");
		mapComments.put("servicegroup_num_services", "The total number of services in the group");
		mapComments.put("servicegroup_num_services_crit", "The number of services in the group that are CRIT");
		mapComments.put("servicegroup_num_services_hard_crit", "The number of services in the group that are CRIT");
		mapComments.put("servicegroup_num_services_hard_ok", "The number of services in the group that are OK");
		mapComments.put("servicegroup_num_services_hard_unknown", "The number of services in the group that are UNKNOWN");
		mapComments.put("servicegroup_num_services_hard_warn", "The number of services in the group that are WARN");
		mapComments.put("servicegroup_num_services_ok", "The number of services in the group that are OK");
		mapComments.put("servicegroup_num_services_pending", "The number of services in the group that are PENDING");
		mapComments.put("servicegroup_num_services_unknown", "The number of services in the group that are UNKNOWN");
		mapComments.put("servicegroup_num_services_warn", "The number of services in the group that are WARN");
		mapComments.put("servicegroup_worst_service_state", "The worst soft state of all of the groups services (OK <= WARN <= UNKNOWN <= CRIT)");
		mapComments.put("staleness", "The staleness indicator for this service");
		mapComments.put("state", "The current state of the service (0: OK, 1: WARN, 2: CRITICAL, 3: UNKNOWN)");
		mapComments.put("state_type", "The type of the current state (0: soft, 1: hard)");
	}
		/**
		 * Whether the service accepts passive checks (0/1)
		 * @return returns the value of the "accept_passive_checks" column as int
		 */
	public int Accept_passive_checks() throws NumberFormatException
	{
		return getAsInt("accept_passive_checks");
	}
		/**
		 * Whether the current service problem has been acknowledged (0/1)
		 * @return returns the value of the "acknowledged" column as int
		 */
	public int Acknowledged() throws NumberFormatException
	{
		return getAsInt("acknowledged");
	}
		/**
		 * The type of the acknownledgement (0: none, 1: normal, 2: sticky)
		 * @return returns the value of the "acknowledgement_type" column as int
		 */
	public int Acknowledgement_type() throws NumberFormatException
	{
		return getAsInt("acknowledgement_type");
	}
		/**
		 * An optional URL for actions or custom information about the service
		 * @return returns the value of the "action_url" column as string
		 */
	public String Action_url() 
	{
		return getAsString("action_url");
	}
		/**
		 * The action_url with (the most important) macros expanded
		 * @return returns the value of the "action_url_expanded" column as string
		 */
	public String Action_url_expanded() 
	{
		return getAsString("action_url_expanded");
	}
		/**
		 * Whether active checks are enabled for the service (0/1)
		 * @return returns the value of the "active_checks_enabled" column as int
		 */
	public int Active_checks_enabled() throws NumberFormatException
	{
		return getAsInt("active_checks_enabled");
	}
		/**
		 * Nagios command used for active checks
		 * @return returns the value of the "check_command" column as string
		 */
	public String Check_command() 
	{
		return getAsString("check_command");
	}
		/**
		 * Nagios command used for active checks with the macros expanded
		 * @return returns the value of the "check_command_expanded" column as string
		 */
	public String Check_command_expanded() 
	{
		return getAsString("check_command_expanded");
	}
		/**
		 * Whether freshness checks are activated (0/1)
		 * @return returns the value of the "check_freshness" column as int
		 */
	public int Check_freshness() throws NumberFormatException
	{
		return getAsInt("check_freshness");
	}
		/**
		 * Number of basic interval lengths between two scheduled checks of the service
		 * @return returns the value of the "check_interval" column as float
		 */
	public float Check_interval() throws NumberFormatException
	{
		return getAsFloat("check_interval");
	}
		/**
		 * The current check option, forced, normal, freshness... (0/1)
		 * @return returns the value of the "check_options" column as int
		 */
	public int Check_options() throws NumberFormatException
	{
		return getAsInt("check_options");
	}
		/**
		 * The name of the check period of the service. It this is empty, the service is always checked.
		 * @return returns the value of the "check_period" column as string
		 */
	public String Check_period() 
	{
		return getAsString("check_period");
	}
		/**
		 * The type of the last check (0: active, 1: passive)
		 * @return returns the value of the "check_type" column as int
		 */
	public int Check_type() throws NumberFormatException
	{
		return getAsInt("check_type");
	}
		/**
		 * Whether active checks are enabled for the service (0/1)
		 * @return returns the value of the "checks_enabled" column as int
		 */
	public int Checks_enabled() throws NumberFormatException
	{
		return getAsInt("checks_enabled");
	}
		/**
		 * A list of all comment ids of the service
		 * @return returns the value of the "comments" column as list
		 */
	public String Comments() 
	{
		return getAsList("comments");
	}
		/**
		 * A list of all comments of the service with id, author, comment, entry type and entry time
		 * @return returns the value of the "comments_with_extra_info" column as list
		 */
	public String Comments_with_extra_info() 
	{
		return getAsList("comments_with_extra_info");
	}
		/**
		 * A list of all comments of the service with id, author and comment
		 * @return returns the value of the "comments_with_info" column as list
		 */
	public String Comments_with_info() 
	{
		return getAsList("comments_with_info");
	}
		/**
		 * A list of all contact groups this service is in
		 * @return returns the value of the "contact_groups" column as list
		 */
	public String Contact_groups() 
	{
		return getAsList("contact_groups");
	}
		/**
		 * A list of all contacts of the service, either direct or via a contact group
		 * @return returns the value of the "contacts" column as list
		 */
	public String Contacts() 
	{
		return getAsList("contacts");
	}
		/**
		 * The number of the current check attempt
		 * @return returns the value of the "current_attempt" column as int
		 */
	public int Current_attempt() throws NumberFormatException
	{
		return getAsInt("current_attempt");
	}
		/**
		 * The number of the current notification
		 * @return returns the value of the "current_notification_number" column as int
		 */
	public int Current_notification_number() throws NumberFormatException
	{
		return getAsInt("current_notification_number");
	}
		/**
		 * A list of the names of all custom variables of the service
		 * @return returns the value of the "custom_variable_names" column as list
		 */
	public String Custom_variable_names() 
	{
		return getAsList("custom_variable_names");
	}
		/**
		 * A list of the values of all custom variable of the service
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
		 * Description of the service (also used as key)
		 * @return returns the value of the "description" column as string
		 */
	public String Description() 
	{
		return getAsString("description");
	}
		/**
		 * An optional display name (not used by Nagios standard web pages)
		 * @return returns the value of the "display_name" column as string
		 */
	public String Display_name() 
	{
		return getAsString("display_name");
	}
		/**
		 * A list of all downtime ids of the service
		 * @return returns the value of the "downtimes" column as list
		 */
	public String Downtimes() 
	{
		return getAsList("downtimes");
	}
		/**
		 * A list of all downtimes of the service with id, author and comment
		 * @return returns the value of the "downtimes_with_info" column as list
		 */
	public String Downtimes_with_info() 
	{
		return getAsList("downtimes_with_info");
	}
		/**
		 * Nagios command used as event handler
		 * @return returns the value of the "event_handler" column as string
		 */
	public String Event_handler() 
	{
		return getAsString("event_handler");
	}
		/**
		 * Whether and event handler is activated for the service (0/1)
		 * @return returns the value of the "event_handler_enabled" column as int
		 */
	public int Event_handler_enabled() throws NumberFormatException
	{
		return getAsInt("event_handler_enabled");
	}
		/**
		 * Time the service check needed for execution
		 * @return returns the value of the "execution_time" column as float
		 */
	public float Execution_time() throws NumberFormatException
	{
		return getAsFloat("execution_time");
	}
		/**
		 * Delay before the first notification
		 * @return returns the value of the "first_notification_delay" column as float
		 */
	public float First_notification_delay() throws NumberFormatException
	{
		return getAsFloat("first_notification_delay");
	}
		/**
		 * Whether flap detection is enabled for the service (0/1)
		 * @return returns the value of the "flap_detection_enabled" column as int
		 */
	public int Flap_detection_enabled() throws NumberFormatException
	{
		return getAsInt("flap_detection_enabled");
	}
		/**
		 * A list of all service groups the service is in
		 * @return returns the value of the "groups" column as list
		 */
	public String Groups() 
	{
		return getAsList("groups");
	}
		/**
		 * Whether the service already has been checked (0/1)
		 * @return returns the value of the "has_been_checked" column as int
		 */
	public int Has_been_checked() throws NumberFormatException
	{
		return getAsInt("has_been_checked");
	}
		/**
		 * High threshold of flap detection
		 * @return returns the value of the "high_flap_threshold" column as float
		 */
	public float High_flap_threshold() throws NumberFormatException
	{
		return getAsFloat("high_flap_threshold");
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
		 * The name of an image to be used as icon in the web interface
		 * @return returns the value of the "icon_image" column as string
		 */
	public String Icon_image() 
	{
		return getAsString("icon_image");
	}
		/**
		 * An alternative text for the icon_image for browsers not displaying icons
		 * @return returns the value of the "icon_image_alt" column as string
		 */
	public String Icon_image_alt() 
	{
		return getAsString("icon_image_alt");
	}
		/**
		 * The icon_image with (the most important) macros expanded
		 * @return returns the value of the "icon_image_expanded" column as string
		 */
	public String Icon_image_expanded() 
	{
		return getAsString("icon_image_expanded");
	}
		/**
		 * Whether the service is currently in its check period (0/1)
		 * @return returns the value of the "in_check_period" column as int
		 */
	public int In_check_period() throws NumberFormatException
	{
		return getAsInt("in_check_period");
	}
		/**
		 * Whether the service is currently in its notification period (0/1)
		 * @return returns the value of the "in_notification_period" column as int
		 */
	public int In_notification_period() throws NumberFormatException
	{
		return getAsInt("in_notification_period");
	}
		/**
		 * Whether this service is currently in its service period (0/1)
		 * @return returns the value of the "in_service_period" column as int
		 */
	public int In_service_period() throws NumberFormatException
	{
		return getAsInt("in_service_period");
	}
		/**
		 * The initial state of the service
		 * @return returns the value of the "initial_state" column as int
		 */
	public int Initial_state() throws NumberFormatException
	{
		return getAsInt("initial_state");
	}
		/**
		 * is there a service check currently running... (0/1)
		 * @return returns the value of the "is_executing" column as int
		 */
	public int Is_executing() throws NumberFormatException
	{
		return getAsInt("is_executing");
	}
		/**
		 * Whether the service is flapping (0/1)
		 * @return returns the value of the "is_flapping" column as int
		 */
	public int Is_flapping() throws NumberFormatException
	{
		return getAsInt("is_flapping");
	}
		/**
		 * The time of the last check (Unix timestamp)
		 * @return returns the value of the "last_check" column as time
		 */
	public Date Last_check() throws NumberFormatException
	{
		return getAsTime("last_check");
	}
		/**
		 * The last hard state of the service
		 * @return returns the value of the "last_hard_state" column as int
		 */
	public int Last_hard_state() throws NumberFormatException
	{
		return getAsInt("last_hard_state");
	}
		/**
		 * The time of the last hard state change (Unix timestamp)
		 * @return returns the value of the "last_hard_state_change" column as time
		 */
	public Date Last_hard_state_change() throws NumberFormatException
	{
		return getAsTime("last_hard_state_change");
	}
		/**
		 * The time of the last notification (Unix timestamp)
		 * @return returns the value of the "last_notification" column as time
		 */
	public Date Last_notification() throws NumberFormatException
	{
		return getAsTime("last_notification");
	}
		/**
		 * The last state of the service
		 * @return returns the value of the "last_state" column as int
		 */
	public int Last_state() throws NumberFormatException
	{
		return getAsInt("last_state");
	}
		/**
		 * The time of the last state change (Unix timestamp)
		 * @return returns the value of the "last_state_change" column as time
		 */
	public Date Last_state_change() throws NumberFormatException
	{
		return getAsTime("last_state_change");
	}
		/**
		 * The last time the service was CRITICAL (Unix timestamp)
		 * @return returns the value of the "last_time_critical" column as time
		 */
	public Date Last_time_critical() throws NumberFormatException
	{
		return getAsTime("last_time_critical");
	}
		/**
		 * The last time the service was OK (Unix timestamp)
		 * @return returns the value of the "last_time_ok" column as time
		 */
	public Date Last_time_ok() throws NumberFormatException
	{
		return getAsTime("last_time_ok");
	}
		/**
		 * The last time the service was UNKNOWN (Unix timestamp)
		 * @return returns the value of the "last_time_unknown" column as time
		 */
	public Date Last_time_unknown() throws NumberFormatException
	{
		return getAsTime("last_time_unknown");
	}
		/**
		 * The last time the service was in WARNING state (Unix timestamp)
		 * @return returns the value of the "last_time_warning" column as time
		 */
	public Date Last_time_warning() throws NumberFormatException
	{
		return getAsTime("last_time_warning");
	}
		/**
		 * Time difference between scheduled check time and actual check time
		 * @return returns the value of the "latency" column as float
		 */
	public float Latency() throws NumberFormatException
	{
		return getAsFloat("latency");
	}
		/**
		 * Unabbreviated output of the last check plugin
		 * @return returns the value of the "long_plugin_output" column as string
		 */
	public String Long_plugin_output() 
	{
		return getAsString("long_plugin_output");
	}
		/**
		 * Low threshold of flap detection
		 * @return returns the value of the "low_flap_threshold" column as float
		 */
	public float Low_flap_threshold() throws NumberFormatException
	{
		return getAsFloat("low_flap_threshold");
	}
		/**
		 * The maximum number of check attempts
		 * @return returns the value of the "max_check_attempts" column as int
		 */
	public int Max_check_attempts() throws NumberFormatException
	{
		return getAsInt("max_check_attempts");
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
		 * The scheduled time of the next check (Unix timestamp)
		 * @return returns the value of the "next_check" column as time
		 */
	public Date Next_check() throws NumberFormatException
	{
		return getAsTime("next_check");
	}
		/**
		 * The time of the next notification (Unix timestamp)
		 * @return returns the value of the "next_notification" column as time
		 */
	public Date Next_notification() throws NumberFormatException
	{
		return getAsTime("next_notification");
	}
		/**
		 * Whether to stop sending notifications (0/1)
		 * @return returns the value of the "no_more_notifications" column as int
		 */
	public int No_more_notifications() throws NumberFormatException
	{
		return getAsInt("no_more_notifications");
	}
		/**
		 * Optional notes about the service
		 * @return returns the value of the "notes" column as string
		 */
	public String Notes() 
	{
		return getAsString("notes");
	}
		/**
		 * The notes with (the most important) macros expanded
		 * @return returns the value of the "notes_expanded" column as string
		 */
	public String Notes_expanded() 
	{
		return getAsString("notes_expanded");
	}
		/**
		 * An optional URL for additional notes about the service
		 * @return returns the value of the "notes_url" column as string
		 */
	public String Notes_url() 
	{
		return getAsString("notes_url");
	}
		/**
		 * The notes_url with (the most important) macros expanded
		 * @return returns the value of the "notes_url_expanded" column as string
		 */
	public String Notes_url_expanded() 
	{
		return getAsString("notes_url_expanded");
	}
		/**
		 * Interval of periodic notification or 0 if its off
		 * @return returns the value of the "notification_interval" column as float
		 */
	public float Notification_interval() throws NumberFormatException
	{
		return getAsFloat("notification_interval");
	}
		/**
		 * The name of the notification period of the service. It this is empty, service problems are always notified.
		 * @return returns the value of the "notification_period" column as string
		 */
	public String Notification_period() 
	{
		return getAsString("notification_period");
	}
		/**
		 * Whether notifications are enabled for the service (0/1)
		 * @return returns the value of the "notifications_enabled" column as int
		 */
	public int Notifications_enabled() throws NumberFormatException
	{
		return getAsInt("notifications_enabled");
	}
		/**
		 * Whether 'obsess_over_service' is enabled for the service (0/1)
		 * @return returns the value of the "obsess_over_service" column as int
		 */
	public int Obsess_over_service() throws NumberFormatException
	{
		return getAsInt("obsess_over_service");
	}
		/**
		 * Percent state change
		 * @return returns the value of the "percent_state_change" column as float
		 */
	public float Percent_state_change() throws NumberFormatException
	{
		return getAsFloat("percent_state_change");
	}
		/**
		 * Performance data of the last check plugin
		 * @return returns the value of the "perf_data" column as string
		 */
	public String Perf_data() 
	{
		return getAsString("perf_data");
	}
		/**
		 * Output of the last check plugin
		 * @return returns the value of the "plugin_output" column as string
		 */
	public String Plugin_output() 
	{
		return getAsString("plugin_output");
	}
		/**
		 * Whether there is a PNP4Nagios graph present for this service (0/1)
		 * @return returns the value of the "pnpgraph_present" column as int
		 */
	public int Pnpgraph_present() throws NumberFormatException
	{
		return getAsInt("pnpgraph_present");
	}
		/**
		 * Whether processing of performance data is enabled for the service (0/1)
		 * @return returns the value of the "process_performance_data" column as int
		 */
	public int Process_performance_data() throws NumberFormatException
	{
		return getAsInt("process_performance_data");
	}
		/**
		 * Number of basic interval lengths between checks when retrying after a soft error
		 * @return returns the value of the "retry_interval" column as float
		 */
	public float Retry_interval() throws NumberFormatException
	{
		return getAsFloat("retry_interval");
	}
		/**
		 * The number of scheduled downtimes the service is currently in
		 * @return returns the value of the "scheduled_downtime_depth" column as int
		 */
	public int Scheduled_downtime_depth() throws NumberFormatException
	{
		return getAsInt("scheduled_downtime_depth");
	}
		/**
		 * The name of the service period of the service
		 * @return returns the value of the "service_period" column as string
		 */
	public String Service_period() 
	{
		return getAsString("service_period");
	}
		/**
		 * An optional URL to custom notes or actions on the service group
		 * @return returns the value of the "servicegroup_action_url" column as string
		 */
	public String Servicegroup_action_url() 
	{
		return getAsString("servicegroup_action_url");
	}
		/**
		 * An alias of the service group
		 * @return returns the value of the "servicegroup_alias" column as string
		 */
	public String Servicegroup_alias() 
	{
		return getAsString("servicegroup_alias");
	}
		/**
		 * A list of all members of the service group as host/service pairs
		 * @return returns the value of the "servicegroup_members" column as list
		 */
	public String Servicegroup_members() 
	{
		return getAsList("servicegroup_members");
	}
		/**
		 * A list of all members of the service group with state and has_been_checked
		 * @return returns the value of the "servicegroup_members_with_state" column as list
		 */
	public String Servicegroup_members_with_state() 
	{
		return getAsList("servicegroup_members_with_state");
	}
		/**
		 * The name of the service group
		 * @return returns the value of the "servicegroup_name" column as string
		 */
	public String Servicegroup_name() 
	{
		return getAsString("servicegroup_name");
	}
		/**
		 * Optional additional notes about the service group
		 * @return returns the value of the "servicegroup_notes" column as string
		 */
	public String Servicegroup_notes() 
	{
		return getAsString("servicegroup_notes");
	}
		/**
		 * An optional URL to further notes on the service group
		 * @return returns the value of the "servicegroup_notes_url" column as string
		 */
	public String Servicegroup_notes_url() 
	{
		return getAsString("servicegroup_notes_url");
	}
		/**
		 * The total number of services in the group
		 * @return returns the value of the "servicegroup_num_services" column as int
		 */
	public int Servicegroup_num_services() throws NumberFormatException
	{
		return getAsInt("servicegroup_num_services");
	}
		/**
		 * The number of services in the group that are CRIT
		 * @return returns the value of the "servicegroup_num_services_crit" column as int
		 */
	public int Servicegroup_num_services_crit() throws NumberFormatException
	{
		return getAsInt("servicegroup_num_services_crit");
	}
		/**
		 * The number of services in the group that are CRIT
		 * @return returns the value of the "servicegroup_num_services_hard_crit" column as int
		 */
	public int Servicegroup_num_services_hard_crit() throws NumberFormatException
	{
		return getAsInt("servicegroup_num_services_hard_crit");
	}
		/**
		 * The number of services in the group that are OK
		 * @return returns the value of the "servicegroup_num_services_hard_ok" column as int
		 */
	public int Servicegroup_num_services_hard_ok() throws NumberFormatException
	{
		return getAsInt("servicegroup_num_services_hard_ok");
	}
		/**
		 * The number of services in the group that are UNKNOWN
		 * @return returns the value of the "servicegroup_num_services_hard_unknown" column as int
		 */
	public int Servicegroup_num_services_hard_unknown() throws NumberFormatException
	{
		return getAsInt("servicegroup_num_services_hard_unknown");
	}
		/**
		 * The number of services in the group that are WARN
		 * @return returns the value of the "servicegroup_num_services_hard_warn" column as int
		 */
	public int Servicegroup_num_services_hard_warn() throws NumberFormatException
	{
		return getAsInt("servicegroup_num_services_hard_warn");
	}
		/**
		 * The number of services in the group that are OK
		 * @return returns the value of the "servicegroup_num_services_ok" column as int
		 */
	public int Servicegroup_num_services_ok() throws NumberFormatException
	{
		return getAsInt("servicegroup_num_services_ok");
	}
		/**
		 * The number of services in the group that are PENDING
		 * @return returns the value of the "servicegroup_num_services_pending" column as int
		 */
	public int Servicegroup_num_services_pending() throws NumberFormatException
	{
		return getAsInt("servicegroup_num_services_pending");
	}
		/**
		 * The number of services in the group that are UNKNOWN
		 * @return returns the value of the "servicegroup_num_services_unknown" column as int
		 */
	public int Servicegroup_num_services_unknown() throws NumberFormatException
	{
		return getAsInt("servicegroup_num_services_unknown");
	}
		/**
		 * The number of services in the group that are WARN
		 * @return returns the value of the "servicegroup_num_services_warn" column as int
		 */
	public int Servicegroup_num_services_warn() throws NumberFormatException
	{
		return getAsInt("servicegroup_num_services_warn");
	}
		/**
		 * The worst soft state of all of the groups services (OK <= WARN <= UNKNOWN <= CRIT)
		 * @return returns the value of the "servicegroup_worst_service_state" column as int
		 */
	public int Servicegroup_worst_service_state() throws NumberFormatException
	{
		return getAsInt("servicegroup_worst_service_state");
	}
		/**
		 * The staleness indicator for this service
		 * @return returns the value of the "staleness" column as float
		 */
	public float Staleness() throws NumberFormatException
	{
		return getAsFloat("staleness");
	}
		/**
		 * The current state of the service (0: OK, 1: WARN, 2: CRITICAL, 3: UNKNOWN)
		 * @return returns the value of the "state" column as int
		 */
	public int State() throws NumberFormatException
	{
		return getAsInt("state");
	}
		/**
		 * The type of the current state (0: soft, 1: hard)
		 * @return returns the value of the "state_type" column as int
		 */
	public int State_type() throws NumberFormatException
	{
		return getAsInt("state_type");
	}
		/**
	 	* create the map for all columns of table  Servicesbygroup. Key=column name, Value=column value
	 	*
   	* @param table LiveStatus table
   	* @param filter filter to applay for this table
	 	* @return Map<String, String>
	 	*/

	@Override
	public Map<String, String> asArrayString(String table, String filter) throws NumberFormatException {
		mapKeyValue.clear();
		setMapObjects(table, filter);

		addToHashtable("accept_passive_checks", getAsString(Accept_passive_checks()));
		addToHashtable("acknowledged", getAsString(Acknowledged()));
		addToHashtable("acknowledgement_type", getAsString(Acknowledgement_type()));
		addToHashtable("action_url", (Action_url()));
		addToHashtable("action_url_expanded", (Action_url_expanded()));
		addToHashtable("active_checks_enabled", getAsString(Active_checks_enabled()));
		addToHashtable("check_command", (Check_command()));
		addToHashtable("check_command_expanded", (Check_command_expanded()));
		addToHashtable("check_freshness", getAsString(Check_freshness()));
		addToHashtable("check_interval", getAsString(Check_interval()));
		addToHashtable("check_options", getAsString(Check_options()));
		addToHashtable("check_period", (Check_period()));
		addToHashtable("check_type", getAsString(Check_type()));
		addToHashtable("checks_enabled", getAsString(Checks_enabled()));
		addToHashtable("comments", (Comments()));
		addToHashtable("comments_with_extra_info", (Comments_with_extra_info()));
		addToHashtable("comments_with_info", (Comments_with_info()));
		addToHashtable("contact_groups", (Contact_groups()));
		addToHashtable("contacts", (Contacts()));
		addToHashtable("current_attempt", getAsString(Current_attempt()));
		addToHashtable("current_notification_number", getAsString(Current_notification_number()));
		addToHashtable("custom_variable_names", (Custom_variable_names()));
		addToHashtable("custom_variable_values", (Custom_variable_values()));
		addToHashtable("custom_variables", (Custom_variables()));
		addToHashtable("description", (Description()));
		addToHashtable("display_name", (Display_name()));
		addToHashtable("downtimes", (Downtimes()));
		addToHashtable("downtimes_with_info", (Downtimes_with_info()));
		addToHashtable("event_handler", (Event_handler()));
		addToHashtable("event_handler_enabled", getAsString(Event_handler_enabled()));
		addToHashtable("execution_time", getAsString(Execution_time()));
		addToHashtable("first_notification_delay", getAsString(First_notification_delay()));
		addToHashtable("flap_detection_enabled", getAsString(Flap_detection_enabled()));
		addToHashtable("groups", (Groups()));
		addToHashtable("has_been_checked", getAsString(Has_been_checked()));
		addToHashtable("high_flap_threshold", getAsString(High_flap_threshold()));
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
		addToHashtable("icon_image", (Icon_image()));
		addToHashtable("icon_image_alt", (Icon_image_alt()));
		addToHashtable("icon_image_expanded", (Icon_image_expanded()));
		addToHashtable("in_check_period", getAsString(In_check_period()));
		addToHashtable("in_notification_period", getAsString(In_notification_period()));
		addToHashtable("in_service_period", getAsString(In_service_period()));
		addToHashtable("initial_state", getAsString(Initial_state()));
		addToHashtable("is_executing", getAsString(Is_executing()));
		addToHashtable("is_flapping", getAsString(Is_flapping()));
		addToHashtable("last_check", getAsString(Last_check()));
		addToHashtable("last_hard_state", getAsString(Last_hard_state()));
		addToHashtable("last_hard_state_change", getAsString(Last_hard_state_change()));
		addToHashtable("last_notification", getAsString(Last_notification()));
		addToHashtable("last_state", getAsString(Last_state()));
		addToHashtable("last_state_change", getAsString(Last_state_change()));
		addToHashtable("last_time_critical", getAsString(Last_time_critical()));
		addToHashtable("last_time_ok", getAsString(Last_time_ok()));
		addToHashtable("last_time_unknown", getAsString(Last_time_unknown()));
		addToHashtable("last_time_warning", getAsString(Last_time_warning()));
		addToHashtable("latency", getAsString(Latency()));
		addToHashtable("long_plugin_output", (Long_plugin_output()));
		addToHashtable("low_flap_threshold", getAsString(Low_flap_threshold()));
		addToHashtable("max_check_attempts", getAsString(Max_check_attempts()));
		addToHashtable("modified_attributes", getAsString(Modified_attributes()));
		addToHashtable("modified_attributes_list", (Modified_attributes_list()));
		addToHashtable("next_check", getAsString(Next_check()));
		addToHashtable("next_notification", getAsString(Next_notification()));
		addToHashtable("no_more_notifications", getAsString(No_more_notifications()));
		addToHashtable("notes", (Notes()));
		addToHashtable("notes_expanded", (Notes_expanded()));
		addToHashtable("notes_url", (Notes_url()));
		addToHashtable("notes_url_expanded", (Notes_url_expanded()));
		addToHashtable("notification_interval", getAsString(Notification_interval()));
		addToHashtable("notification_period", (Notification_period()));
		addToHashtable("notifications_enabled", getAsString(Notifications_enabled()));
		addToHashtable("obsess_over_service", getAsString(Obsess_over_service()));
		addToHashtable("percent_state_change", getAsString(Percent_state_change()));
		addToHashtable("perf_data", (Perf_data()));
		addToHashtable("plugin_output", (Plugin_output()));
		addToHashtable("pnpgraph_present", getAsString(Pnpgraph_present()));
		addToHashtable("process_performance_data", getAsString(Process_performance_data()));
		addToHashtable("retry_interval", getAsString(Retry_interval()));
		addToHashtable("scheduled_downtime_depth", getAsString(Scheduled_downtime_depth()));
		addToHashtable("service_period", (Service_period()));
		addToHashtable("servicegroup_action_url", (Servicegroup_action_url()));
		addToHashtable("servicegroup_alias", (Servicegroup_alias()));
		addToHashtable("servicegroup_members", (Servicegroup_members()));
		addToHashtable("servicegroup_members_with_state", (Servicegroup_members_with_state()));
		addToHashtable("servicegroup_name", (Servicegroup_name()));
		addToHashtable("servicegroup_notes", (Servicegroup_notes()));
		addToHashtable("servicegroup_notes_url", (Servicegroup_notes_url()));
		addToHashtable("servicegroup_num_services", getAsString(Servicegroup_num_services()));
		addToHashtable("servicegroup_num_services_crit", getAsString(Servicegroup_num_services_crit()));
		addToHashtable("servicegroup_num_services_hard_crit", getAsString(Servicegroup_num_services_hard_crit()));
		addToHashtable("servicegroup_num_services_hard_ok", getAsString(Servicegroup_num_services_hard_ok()));
		addToHashtable("servicegroup_num_services_hard_unknown", getAsString(Servicegroup_num_services_hard_unknown()));
		addToHashtable("servicegroup_num_services_hard_warn", getAsString(Servicegroup_num_services_hard_warn()));
		addToHashtable("servicegroup_num_services_ok", getAsString(Servicegroup_num_services_ok()));
		addToHashtable("servicegroup_num_services_pending", getAsString(Servicegroup_num_services_pending()));
		addToHashtable("servicegroup_num_services_unknown", getAsString(Servicegroup_num_services_unknown()));
		addToHashtable("servicegroup_num_services_warn", getAsString(Servicegroup_num_services_warn()));
		addToHashtable("servicegroup_worst_service_state", getAsString(Servicegroup_worst_service_state()));
		addToHashtable("staleness", getAsString(Staleness()));
		addToHashtable("state", getAsString(State()));
		addToHashtable("state_type", getAsString(State_type()));
		return mapKeyValue;
	}
	}
