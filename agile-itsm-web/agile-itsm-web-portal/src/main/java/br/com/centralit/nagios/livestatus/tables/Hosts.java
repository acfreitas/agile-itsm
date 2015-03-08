/*****************************************************************************
 * Hosts.java - 
 *
 * Copyright (c) 2014 Projeto citsmart (Contact: adenir.gomes@centralit.com.br)
 *
 * License:
 *****************************************************************************/
package br.com.centralit.nagios.livestatus.tables;

import java.util.Date;
import java.util.Map;

/**
 * Class Hosts is the main class for obtain all columns of table "hosts" from a Livestatus TCP-socket/file status.dat.
 * 
 * @author Adenir Ribeiro Gomes
 */

public class Hosts extends HostsBase {
	/**
	 * Constructor of table Hosts
	 * 
	 * @param path
	 *            = "tcp://host:port" File : where path is the path to the file
	 */
	public Hosts(String path) {
		super(path);
		initializeMaps();
		tableName = "hosts";
	}

	/**
	 * create the map for all columns description of table Hosts. Key=column name, Value=column description
	 * 
	 */
	public final void initializeMaps() {
		mapComments.put("accept_passive_checks", "Whether passive host checks are accepted (0/1)");
		mapComments.put("acknowledged", "Whether the current host problem has been acknowledged (0/1)");
		mapComments.put("acknowledgement_type", "Type of acknowledgement (0: none, 1: normal, 2: stick)");
		mapComments.put("action_url", "An optional URL to custom actions or information about this host");
		mapComments.put("action_url_expanded", "The same as action_url, but with the most important macros expanded");
		mapComments.put("active_checks_enabled", "Whether active checks are enabled for the host (0/1)");
		mapComments.put("address", "IP address");
		mapComments.put("alias", "An alias name for the host");
		mapComments.put("check_command", "Nagios command for active host check of this host");
		mapComments.put("check_command_expanded", "Nagios command for active host check of this host with the macros expanded");
		mapComments.put("check_flapping_recovery_notification", "Whether to check to send a recovery notification when flapping stops (0/1)");
		mapComments.put("check_freshness", "Whether freshness checks are activated (0/1)");
		mapComments.put("check_interval", "Number of basic interval lengths between two scheduled checks of the host");
		mapComments.put("check_options", "The current check option, forced, normal, freshness... (0-2)");
		mapComments.put("check_period", "Time period in which this host will be checked. If empty then the host will always be checked.");
		mapComments.put("check_type", "Type of check (0: active, 1: passive)");
		mapComments.put("checks_enabled", "Whether checks of the host are enabled (0/1)");
		mapComments.put("childs", "A list of all direct childs of the host");
		mapComments.put("comments", "A list of the ids of all comments of this host");
		mapComments.put("comments_with_extra_info", "A list of all comments of the host with id, author, comment, entry type and entry time");
		mapComments.put("comments_with_info", "A list of all comments of the host with id, author and comment");
		mapComments.put("contact_groups", "A list of all contact groups this host is in");
		mapComments.put("contacts", "A list of all contacts of this host, either direct or via a contact group");
		mapComments.put("current_attempt", "Number of the current check attempts");
		mapComments.put("current_notification_number", "Number of the current notification");
		mapComments.put("custom_variable_names", "A list of the names of all custom variables");
		mapComments.put("custom_variable_values", "A list of the values of the custom variables");
		mapComments.put("custom_variables", "A dictionary of the custom variables");
		mapComments.put("display_name", "Optional display name of the host - not used by Nagios' web interface");
		mapComments.put("downtimes", "A list of the ids of all scheduled downtimes of this host");
		mapComments.put("downtimes_with_info", "A list of the all scheduled downtimes of the host with id, author and comment");
		mapComments.put("event_handler", "Nagios command used as event handler");
		mapComments.put("event_handler_enabled", "Whether event handling is enabled (0/1)");
		mapComments.put("execution_time", "Time the host check needed for execution");
		mapComments.put("filename", "The value of the custom variable FILENAME");
		mapComments.put("first_notification_delay", "Delay before the first notification");
		mapComments.put("flap_detection_enabled", "Whether flap detection is enabled (0/1)");
		mapComments.put("groups", "A list of all host groups this host is in");
		mapComments.put("hard_state", "The effective hard state of the host (eliminates a problem in hard_state)");
		mapComments.put("has_been_checked", "Whether the host has already been checked (0/1)");
		mapComments.put("high_flap_threshold", "High threshold of flap detection");
		mapComments.put("icon_image", "The name of an image file to be used in the web pages");
		mapComments.put("icon_image_alt", "Alternative text for the icon_image");
		mapComments.put("icon_image_expanded", "The same as icon_image, but with the most important macros expanded");
		mapComments.put("in_check_period", "Whether this host is currently in its check period (0/1)");
		mapComments.put("in_notification_period", "Whether this host is currently in its notification period (0/1)");
		mapComments.put("in_service_period", "Whether this host is currently in its service period (0/1)");
		mapComments.put("initial_state", "Initial host state");
		mapComments.put("is_executing", "is there a host check currently running... (0/1)");
		mapComments.put("is_flapping", "Whether the host state is flapping (0/1)");
		mapComments.put("last_check", "Time of the last check (Unix timestamp)");
		mapComments.put("last_hard_state", "Last hard state");
		mapComments.put("last_hard_state_change", "Time of the last hard state change (Unix timestamp)");
		mapComments.put("last_notification", "Time of the last notification (Unix timestamp)");
		mapComments.put("last_state", "State before last state change");
		mapComments.put("last_state_change", "Time of the last state change - soft or hard (Unix timestamp)");
		mapComments.put("last_time_down", "The last time the host was DOWN (Unix timestamp)");
		mapComments.put("last_time_unreachable", "The last time the host was UNREACHABLE (Unix timestamp)");
		mapComments.put("last_time_up", "The last time the host was UP (Unix timestamp)");
		mapComments.put("latency", "Time difference between scheduled check time and actual check time");
		mapComments.put("long_plugin_output", "Complete output from check plugin");
		mapComments.put("low_flap_threshold", "Low threshold of flap detection");
		mapComments.put("max_check_attempts", "Max check attempts for active host checks");
		mapComments.put("modified_attributes", "A bitmask specifying which attributes have been modified");
		mapComments.put("modified_attributes_list", "A list of all modified attributes");
		mapComments.put("name", "Host name");
		mapComments.put("next_check", "Scheduled time for the next check (Unix timestamp)");
		mapComments.put("next_notification", "Time of the next notification (Unix timestamp)");
		mapComments.put("no_more_notifications", "Whether to stop sending notifications (0/1)");
		mapComments.put("notes", "Optional notes for this host");
		mapComments.put("notes_expanded", "The same as notes, but with the most important macros expanded");
		mapComments.put("notes_url", "An optional URL with further information about the host");
		mapComments.put("notes_url_expanded", "Same es notes_url, but with the most important macros expanded");
		mapComments.put("notification_interval", "Interval of periodic notification or 0 if its off");
		mapComments.put("notification_period", "Time period in which problems of this host will be notified. If empty then notification will be always");
		mapComments.put("notifications_enabled", "Whether notifications of the host are enabled (0/1)");
		mapComments.put("num_services", "The total number of services of the host");
		mapComments.put("num_services_crit", "The number of the host's services with the soft state CRIT");
		mapComments.put("num_services_hard_crit", "The number of the host's services with the hard state CRIT");
		mapComments.put("num_services_hard_ok", "The number of the host's services with the hard state OK");
		mapComments.put("num_services_hard_unknown", "The number of the host's services with the hard state UNKNOWN");
		mapComments.put("num_services_hard_warn", "The number of the host's services with the hard state WARN");
		mapComments.put("num_services_ok", "The number of the host's services with the soft state OK");
		mapComments.put("num_services_pending", "The number of the host's services which have not been checked yet (pending)");
		mapComments.put("num_services_unknown", "The number of the host's services with the soft state UNKNOWN");
		mapComments.put("num_services_warn", "The number of the host's services with the soft state WARN");
		mapComments.put("obsess_over_host", "The current obsess_over_host setting... (0/1)");
		mapComments.put("parents", "A list of all direct parents of the host");
		mapComments.put("pending_flex_downtime", "Whether a flex downtime is pending (0/1)");
		mapComments.put("percent_state_change", "Percent state change");
		mapComments.put("perf_data", "Optional performance data of the last host check");
		mapComments.put("plugin_output", "Output of the last host check");
		mapComments.put("pnpgraph_present", "Whether there is a PNP4Nagios graph present for this host (0/1)");
		mapComments.put("process_performance_data", "Whether processing of performance data is enabled (0/1)");
		mapComments.put("retry_interval", "Number of basic interval lengths between checks when retrying after a soft error");
		mapComments.put("scheduled_downtime_depth", "The number of downtimes this host is currently in");
		mapComments.put("service_period", "The name of the service period of the host");
		mapComments.put("services", "A list of all services of the host");
		mapComments.put("services_with_info", "A list of all services including detailed information about each service");
		mapComments.put("services_with_state", "A list of all services of the host together with state and has_been_checked");
		mapComments.put("staleness", "Staleness indicator for this host");
		mapComments.put("state", "The current state of the host (0: up, 1: down, 2: unreachable)");
		mapComments.put("state_type", "Type of the current state (0: soft, 1: hard)");
		mapComments.put("statusmap_image", "The name of in image file for the status map");
		mapComments.put("total_services", "The total number of services of the host");
		mapComments.put("worst_service_hard_state", "The worst hard state of all of the host's services (OK <= WARN <= UNKNOWN <= CRIT)");
		mapComments.put("worst_service_state", "The worst soft state of all of the host's services (OK <= WARN <= UNKNOWN <= CRIT)");
		mapComments.put("x_3d", "3D-Coordinates: X");
		mapComments.put("y_3d", "3D-Coordinates: Y");
		mapComments.put("z_3d", "3D-Coordinates: Z");
	}

	/**
	 * Whether passive host checks are accepted (0/1)
	 * 
	 * @return returns the value of the "accept_passive_checks" column as int
	 */
	public int Accept_passive_checks() throws NumberFormatException {
		return getAsInt("accept_passive_checks");
	}

	/**
	 * Whether the current host problem has been acknowledged (0/1)
	 * 
	 * @return returns the value of the "acknowledged" column as int
	 */
	public int Acknowledged() throws NumberFormatException {
		return getAsInt("acknowledged");
	}

	/**
	 * Type of acknowledgement (0: none, 1: normal, 2: stick)
	 * 
	 * @return returns the value of the "acknowledgement_type" column as int
	 */
	public int Acknowledgement_type() throws NumberFormatException {
		return getAsInt("acknowledgement_type");
	}

	/**
	 * An optional URL to custom actions or information about this host
	 * 
	 * @return returns the value of the "action_url" column as string
	 */
	public String Action_url() {
		return getAsString("action_url");
	}

	/**
	 * The same as action_url, but with the most important macros expanded
	 * 
	 * @return returns the value of the "action_url_expanded" column as string
	 */
	public String Action_url_expanded() {
		return getAsString("action_url_expanded");
	}

	/**
	 * Whether active checks are enabled for the host (0/1)
	 * 
	 * @return returns the value of the "active_checks_enabled" column as int
	 */
	public int Active_checks_enabled() throws NumberFormatException {
		return getAsInt("active_checks_enabled");
	}

	/**
	 * IP address
	 * 
	 * @return returns the value of the "address" column as string
	 */
	public String Address() {
		return getAsString("address");
	}

	/**
	 * An alias name for the host
	 * 
	 * @return returns the value of the "alias" column as string
	 */
	public String Alias() {
		return getAsString("alias");
	}

	/**
	 * Nagios command for active host check of this host
	 * 
	 * @return returns the value of the "check_command" column as string
	 */
	public String Check_command() {
		return getAsString("check_command");
	}

	/**
	 * Nagios command for active host check of this host with the macros expanded
	 * 
	 * @return returns the value of the "check_command_expanded" column as string
	 */
	public String Check_command_expanded() {
		return getAsString("check_command_expanded");
	}

	/**
	 * Whether to check to send a recovery notification when flapping stops (0/1)
	 * 
	 * @return returns the value of the "check_flapping_recovery_notification" column as int
	 */
	public int Check_flapping_recovery_notification() throws NumberFormatException {
		return getAsInt("check_flapping_recovery_notification");
	}

	/**
	 * Whether freshness checks are activated (0/1)
	 * 
	 * @return returns the value of the "check_freshness" column as int
	 */
	public int Check_freshness() throws NumberFormatException {
		return getAsInt("check_freshness");
	}

	/**
	 * Number of basic interval lengths between two scheduled checks of the host
	 * 
	 * @return returns the value of the "check_interval" column as float
	 */
	public float Check_interval() throws NumberFormatException {
		return getAsFloat("check_interval");
	}

	/**
	 * The current check option, forced, normal, freshness... (0-2)
	 * 
	 * @return returns the value of the "check_options" column as int
	 */
	public int Check_options() throws NumberFormatException {
		return getAsInt("check_options");
	}

	/**
	 * Time period in which this host will be checked. If empty then the host will always be checked.
	 * 
	 * @return returns the value of the "check_period" column as string
	 */
	public String Check_period() {
		return getAsString("check_period");
	}

	/**
	 * Type of check (0: active, 1: passive)
	 * 
	 * @return returns the value of the "check_type" column as int
	 */
	public int Check_type() throws NumberFormatException {
		return getAsInt("check_type");
	}

	/**
	 * Whether checks of the host are enabled (0/1)
	 * 
	 * @return returns the value of the "checks_enabled" column as int
	 */
	public int Checks_enabled() throws NumberFormatException {
		return getAsInt("checks_enabled");
	}

	/**
	 * A list of all direct childs of the host
	 * 
	 * @return returns the value of the "childs" column as list
	 */
	public String Childs() {
		return getAsList("childs");
	}

	/**
	 * A list of the ids of all comments of this host
	 * 
	 * @return returns the value of the "comments" column as list
	 */
	public String Comments() {
		return getAsList("comments");
	}

	/**
	 * A list of all comments of the host with id, author, comment, entry type and entry time
	 * 
	 * @return returns the value of the "comments_with_extra_info" column as list
	 */
	public String Comments_with_extra_info() {
		return getAsList("comments_with_extra_info");
	}

	/**
	 * A list of all comments of the host with id, author and comment
	 * 
	 * @return returns the value of the "comments_with_info" column as list
	 */
	public String Comments_with_info() {
		return getAsList("comments_with_info");
	}

	/**
	 * A list of all contact groups this host is in
	 * 
	 * @return returns the value of the "contact_groups" column as list
	 */
	public String Contact_groups() {
		return getAsList("contact_groups");
	}

	/**
	 * A list of all contacts of this host, either direct or via a contact group
	 * 
	 * @return returns the value of the "contacts" column as list
	 */
	public String Contacts() {
		return getAsList("contacts");
	}

	/**
	 * Number of the current check attempts
	 * 
	 * @return returns the value of the "current_attempt" column as int
	 */
	public int Current_attempt() throws NumberFormatException {
		return getAsInt("current_attempt");
	}

	/**
	 * Number of the current notification
	 * 
	 * @return returns the value of the "current_notification_number" column as int
	 */
	public int Current_notification_number() throws NumberFormatException {
		return getAsInt("current_notification_number");
	}

	/**
	 * A list of the names of all custom variables
	 * 
	 * @return returns the value of the "custom_variable_names" column as list
	 */
	public String Custom_variable_names() {
		return getAsList("custom_variable_names");
	}

	/**
	 * A list of the values of the custom variables
	 * 
	 * @return returns the value of the "custom_variable_values" column as list
	 */
	public String Custom_variable_values() {
		return getAsList("custom_variable_values");
	}

	/**
	 * A dictionary of the custom variables
	 * 
	 * @return returns the value of the "custom_variables" column as dict
	 */
	public String Custom_variables() {
		return getAsDict("custom_variables");
	}

	/**
	 * Optional display name of the host - not used by Nagios' web interface
	 * 
	 * @return returns the value of the "display_name" column as string
	 */
	public String Display_name() {
		return getAsString("display_name");
	}

	/**
	 * A list of the ids of all scheduled downtimes of this host
	 * 
	 * @return returns the value of the "downtimes" column as list
	 */
	public String Downtimes() {
		return getAsList("downtimes");
	}

	/**
	 * A list of the all scheduled downtimes of the host with id, author and comment
	 * 
	 * @return returns the value of the "downtimes_with_info" column as list
	 */
	public String Downtimes_with_info() {
		return getAsList("downtimes_with_info");
	}

	/**
	 * Nagios command used as event handler
	 * 
	 * @return returns the value of the "event_handler" column as string
	 */
	public String Event_handler() {
		return getAsString("event_handler");
	}

	/**
	 * Whether event handling is enabled (0/1)
	 * 
	 * @return returns the value of the "event_handler_enabled" column as int
	 */
	public int Event_handler_enabled() throws NumberFormatException {
		return getAsInt("event_handler_enabled");
	}

	/**
	 * Time the host check needed for execution
	 * 
	 * @return returns the value of the "execution_time" column as float
	 */
	public float Execution_time() throws NumberFormatException {
		return getAsFloat("execution_time");
	}

	/**
	 * The value of the custom variable FILENAME
	 * 
	 * @return returns the value of the "filename" column as string
	 */
	public String Filename() {
		return getAsString("filename");
	}

	/**
	 * Delay before the first notification
	 * 
	 * @return returns the value of the "first_notification_delay" column as float
	 */
	public float First_notification_delay() throws NumberFormatException {
		return getAsFloat("first_notification_delay");
	}

	/**
	 * Whether flap detection is enabled (0/1)
	 * 
	 * @return returns the value of the "flap_detection_enabled" column as int
	 */
	public int Flap_detection_enabled() throws NumberFormatException {
		return getAsInt("flap_detection_enabled");
	}

	/**
	 * A list of all host groups this host is in
	 * 
	 * @return returns the value of the "groups" column as list
	 */
	public String Groups() {
		return getAsList("groups");
	}

	/**
	 * The effective hard state of the host (eliminates a problem in hard_state)
	 * 
	 * @return returns the value of the "hard_state" column as int
	 */
	public int Hard_state() throws NumberFormatException {
		return getAsInt("hard_state");
	}

	/**
	 * Whether the host has already been checked (0/1)
	 * 
	 * @return returns the value of the "has_been_checked" column as int
	 */
	public int Has_been_checked() throws NumberFormatException {
		return getAsInt("has_been_checked");
	}

	/**
	 * High threshold of flap detection
	 * 
	 * @return returns the value of the "high_flap_threshold" column as float
	 */
	public float High_flap_threshold() throws NumberFormatException {
		return getAsFloat("high_flap_threshold");
	}

	/**
	 * The name of an image file to be used in the web pages
	 * 
	 * @return returns the value of the "icon_image" column as string
	 */
	public String Icon_image() {
		return getAsString("icon_image");
	}

	/**
	 * Alternative text for the icon_image
	 * 
	 * @return returns the value of the "icon_image_alt" column as string
	 */
	public String Icon_image_alt() {
		return getAsString("icon_image_alt");
	}

	/**
	 * The same as icon_image, but with the most important macros expanded
	 * 
	 * @return returns the value of the "icon_image_expanded" column as string
	 */
	public String Icon_image_expanded() {
		return getAsString("icon_image_expanded");
	}

	/**
	 * Whether this host is currently in its check period (0/1)
	 * 
	 * @return returns the value of the "in_check_period" column as int
	 */
	public int In_check_period() throws NumberFormatException {
		return getAsInt("in_check_period");
	}

	/**
	 * Whether this host is currently in its notification period (0/1)
	 * 
	 * @return returns the value of the "in_notification_period" column as int
	 */
	public int In_notification_period() throws NumberFormatException {
		return getAsInt("in_notification_period");
	}

	/**
	 * Whether this host is currently in its service period (0/1)
	 * 
	 * @return returns the value of the "in_service_period" column as int
	 */
	public int In_service_period() throws NumberFormatException {
		return getAsInt("in_service_period");
	}

	/**
	 * Initial host state
	 * 
	 * @return returns the value of the "initial_state" column as int
	 */
	public int Initial_state() throws NumberFormatException {
		return getAsInt("initial_state");
	}

	/**
	 * is there a host check currently running... (0/1)
	 * 
	 * @return returns the value of the "is_executing" column as int
	 */
	public int Is_executing() throws NumberFormatException {
		return getAsInt("is_executing");
	}

	/**
	 * Whether the host state is flapping (0/1)
	 * 
	 * @return returns the value of the "is_flapping" column as int
	 */
	public int Is_flapping() throws NumberFormatException {
		return getAsInt("is_flapping");
	}

	/**
	 * Time of the last check (Unix timestamp)
	 * 
	 * @return returns the value of the "last_check" column as time
	 */
	public Date Last_check() throws NumberFormatException {
		return getAsTime("last_check");
	}

	/**
	 * Last hard state
	 * 
	 * @return returns the value of the "last_hard_state" column as int
	 */
	public int Last_hard_state() throws NumberFormatException {
		return getAsInt("last_hard_state");
	}

	/**
	 * Time of the last hard state change (Unix timestamp)
	 * 
	 * @return returns the value of the "last_hard_state_change" column as time
	 */
	public Date Last_hard_state_change() throws NumberFormatException {
		return getAsTime("last_hard_state_change");
	}

	/**
	 * Time of the last notification (Unix timestamp)
	 * 
	 * @return returns the value of the "last_notification" column as time
	 */
	public Date Last_notification() throws NumberFormatException {
		return getAsTime("last_notification");
	}

	/**
	 * State before last state change
	 * 
	 * @return returns the value of the "last_state" column as int
	 */
	public int Last_state() throws NumberFormatException {
		return getAsInt("last_state");
	}

	/**
	 * Time of the last state change - soft or hard (Unix timestamp)
	 * 
	 * @return returns the value of the "last_state_change" column as time
	 */
	public Date Last_state_change() throws NumberFormatException {
		return getAsTime("last_state_change");
	}

	/**
	 * The last time the host was DOWN (Unix timestamp)
	 * 
	 * @return returns the value of the "last_time_down" column as time
	 */
	public String Last_time_down() throws NumberFormatException {
		return getAsString("last_time_down");
	}

	/**
	 * The last time the host was UNREACHABLE (Unix timestamp)
	 * 
	 * @return returns the value of the "last_time_unreachable" column as time
	 */
	public Date Last_time_unreachable() throws NumberFormatException {
		return getAsTime("last_time_unreachable");
	}

	/**
	 * The last time the host was UP (Unix timestamp)
	 * 
	 * @return returns the value of the "last_time_up" column as time
	 */
	public Date Last_time_up() throws NumberFormatException {
		return getAsTime("last_time_up");
	}

	/**
	 * Time difference between scheduled check time and actual check time
	 * 
	 * @return returns the value of the "latency" column as float
	 */
	public float Latency() throws NumberFormatException {
		return getAsFloat("latency");
	}

	/**
	 * Complete output from check plugin
	 * 
	 * @return returns the value of the "long_plugin_output" column as string
	 */
	public String Long_plugin_output() {
		return getAsString("long_plugin_output");
	}

	/**
	 * Low threshold of flap detection
	 * 
	 * @return returns the value of the "low_flap_threshold" column as float
	 */
	public float Low_flap_threshold() throws NumberFormatException {
		return getAsFloat("low_flap_threshold");
	}

	/**
	 * Max check attempts for active host checks
	 * 
	 * @return returns the value of the "max_check_attempts" column as int
	 */
	public int Max_check_attempts() throws NumberFormatException {
		return getAsInt("max_check_attempts");
	}

	/**
	 * A bitmask specifying which attributes have been modified
	 * 
	 * @return returns the value of the "modified_attributes" column as int
	 */
	public int Modified_attributes() throws NumberFormatException {
		return getAsInt("modified_attributes");
	}

	/**
	 * A list of all modified attributes
	 * 
	 * @return returns the value of the "modified_attributes_list" column as list
	 */
	public String Modified_attributes_list() {
		return getAsList("modified_attributes_list");
	}

	/**
	 * Host name
	 * 
	 * @return returns the value of the "name" column as string
	 */
	public String Name() {
		return getAsString("name");
	}

	/**
	 * Scheduled time for the next check (Unix timestamp)
	 * 
	 * @return returns the value of the "next_check" column as time
	 */
	public Date Next_check() throws NumberFormatException {
		return getAsTime("next_check");
	}

	/**
	 * Time of the next notification (Unix timestamp)
	 * 
	 * @return returns the value of the "next_notification" column as time
	 */
	public Date Next_notification() throws NumberFormatException {
		return getAsTime("next_notification");
	}

	/**
	 * Whether to stop sending notifications (0/1)
	 * 
	 * @return returns the value of the "no_more_notifications" column as int
	 */
	public int No_more_notifications() throws NumberFormatException {
		return getAsInt("no_more_notifications");
	}

	/**
	 * Optional notes for this host
	 * 
	 * @return returns the value of the "notes" column as string
	 */
	public String Notes() {
		return getAsString("notes");
	}

	/**
	 * The same as notes, but with the most important macros expanded
	 * 
	 * @return returns the value of the "notes_expanded" column as string
	 */
	public String Notes_expanded() {
		return getAsString("notes_expanded");
	}

	/**
	 * An optional URL with further information about the host
	 * 
	 * @return returns the value of the "notes_url" column as string
	 */
	public String Notes_url() {
		return getAsString("notes_url");
	}

	/**
	 * Same es notes_url, but with the most important macros expanded
	 * 
	 * @return returns the value of the "notes_url_expanded" column as string
	 */
	public String Notes_url_expanded() {
		return getAsString("notes_url_expanded");
	}

	/**
	 * Interval of periodic notification or 0 if its off
	 * 
	 * @return returns the value of the "notification_interval" column as float
	 */
	public float Notification_interval() throws NumberFormatException {
		return getAsFloat("notification_interval");
	}

	/**
	 * Time period in which problems of this host will be notified. If empty then notification will be always
	 * 
	 * @return returns the value of the "notification_period" column as string
	 */
	public String Notification_period() {
		return getAsString("notification_period");
	}

	/**
	 * Whether notifications of the host are enabled (0/1)
	 * 
	 * @return returns the value of the "notifications_enabled" column as int
	 */
	public int Notifications_enabled() throws NumberFormatException {
		return getAsInt("notifications_enabled");
	}

	/**
	 * The total number of services of the host
	 * 
	 * @return returns the value of the "num_services" column as int
	 */
	public int Num_services() throws NumberFormatException {
		return getAsInt("num_services");
	}

	/**
	 * The number of the host's services with the soft state CRIT
	 * 
	 * @return returns the value of the "num_services_crit" column as int
	 */
	public int Num_services_crit() throws NumberFormatException {
		return getAsInt("num_services_crit");
	}

	/**
	 * The number of the host's services with the hard state CRIT
	 * 
	 * @return returns the value of the "num_services_hard_crit" column as int
	 */
	public int Num_services_hard_crit() throws NumberFormatException {
		return getAsInt("num_services_hard_crit");
	}

	/**
	 * The number of the host's services with the hard state OK
	 * 
	 * @return returns the value of the "num_services_hard_ok" column as int
	 */
	public int Num_services_hard_ok() throws NumberFormatException {
		return getAsInt("num_services_hard_ok");
	}

	/**
	 * The number of the host's services with the hard state UNKNOWN
	 * 
	 * @return returns the value of the "num_services_hard_unknown" column as int
	 */
	public int Num_services_hard_unknown() throws NumberFormatException {
		return getAsInt("num_services_hard_unknown");
	}

	/**
	 * The number of the host's services with the hard state WARN
	 * 
	 * @return returns the value of the "num_services_hard_warn" column as int
	 */
	public int Num_services_hard_warn() throws NumberFormatException {
		return getAsInt("num_services_hard_warn");
	}

	/**
	 * The number of the host's services with the soft state OK
	 * 
	 * @return returns the value of the "num_services_ok" column as int
	 */
	public int Num_services_ok() throws NumberFormatException {
		return getAsInt("num_services_ok");
	}

	/**
	 * The number of the host's services which have not been checked yet (pending)
	 * 
	 * @return returns the value of the "num_services_pending" column as int
	 */
	public int Num_services_pending() throws NumberFormatException {
		return getAsInt("num_services_pending");
	}

	/**
	 * The number of the host's services with the soft state UNKNOWN
	 * 
	 * @return returns the value of the "num_services_unknown" column as int
	 */
	public int Num_services_unknown() throws NumberFormatException {
		return getAsInt("num_services_unknown");
	}

	/**
	 * The number of the host's services with the soft state WARN
	 * 
	 * @return returns the value of the "num_services_warn" column as int
	 */
	public int Num_services_warn() throws NumberFormatException {
		return getAsInt("num_services_warn");
	}

	/**
	 * The current obsess_over_host setting... (0/1)
	 * 
	 * @return returns the value of the "obsess_over_host" column as int
	 */
	public int Obsess_over_host() throws NumberFormatException {
		return getAsInt("obsess_over_host");
	}

	/**
	 * A list of all direct parents of the host
	 * 
	 * @return returns the value of the "parents" column as list
	 */
	public String Parents() {
		return getAsList("parents");
	}

	/**
	 * Whether a flex downtime is pending (0/1)
	 * 
	 * @return returns the value of the "pending_flex_downtime" column as int
	 */
	public int Pending_flex_downtime() throws NumberFormatException {
		return getAsInt("pending_flex_downtime");
	}

	/**
	 * Percent state change
	 * 
	 * @return returns the value of the "percent_state_change" column as float
	 */
	public float Percent_state_change() throws NumberFormatException {
		return getAsFloat("percent_state_change");
	}

	/**
	 * Optional performance data of the last host check
	 * 
	 * @return returns the value of the "perf_data" column as string
	 */
	public String Perf_data() {
		return getAsString("perf_data");
	}

	/**
	 * Output of the last host check
	 * 
	 * @return returns the value of the "plugin_output" column as string
	 */
	public String Plugin_output() {
		return getAsString("plugin_output");
	}

	/**
	 * Whether there is a PNP4Nagios graph present for this host (0/1)
	 * 
	 * @return returns the value of the "pnpgraph_present" column as int
	 */
	public int Pnpgraph_present() throws NumberFormatException {
		return getAsInt("pnpgraph_present");
	}

	/**
	 * Whether processing of performance data is enabled (0/1)
	 * 
	 * @return returns the value of the "process_performance_data" column as int
	 */
	public int Process_performance_data() throws NumberFormatException {
		return getAsInt("process_performance_data");
	}

	/**
	 * Number of basic interval lengths between checks when retrying after a soft error
	 * 
	 * @return returns the value of the "retry_interval" column as float
	 */
	public float Retry_interval() throws NumberFormatException {
		return getAsFloat("retry_interval");
	}

	/**
	 * The number of downtimes this host is currently in
	 * 
	 * @return returns the value of the "scheduled_downtime_depth" column as int
	 */
	public int Scheduled_downtime_depth() throws NumberFormatException {
		return getAsInt("scheduled_downtime_depth");
	}

	/**
	 * The name of the service period of the host
	 * 
	 * @return returns the value of the "service_period" column as string
	 */
	public String Service_period() {
		return getAsString("service_period");
	}

	/**
	 * A list of all services of the host
	 * 
	 * @return returns the value of the "services" column as list
	 */
	public String Services() {
		return getAsList("services");
	}

	/**
	 * A list of all services including detailed information about each service
	 * 
	 * @return returns the value of the "services_with_info" column as list
	 */
	public String Services_with_info() {
		return getAsList("services_with_info");
	}

	/**
	 * A list of all services of the host together with state and has_been_checked
	 * 
	 * @return returns the value of the "services_with_state" column as list
	 */
	public String Services_with_state() {
		return getAsList("services_with_state");
	}

	/**
	 * Staleness indicator for this host
	 * 
	 * @return returns the value of the "staleness" column as float
	 */
	public float Staleness() throws NumberFormatException {
		return getAsFloat("staleness");
	}

	/**
	 * The current state of the host (0: up, 1: down, 2: unreachable)
	 * 
	 * @return returns the value of the "state" column as int
	 */
	public int State() throws NumberFormatException {
		return getAsInt("state");
	}

	/**
	 * Type of the current state (0: soft, 1: hard)
	 * 
	 * @return returns the value of the "state_type" column as int
	 */
	public int State_type() throws NumberFormatException {
		return getAsInt("state_type");
	}

	/**
	 * The name of in image file for the status map
	 * 
	 * @return returns the value of the "statusmap_image" column as string
	 */
	public String Statusmap_image() {
		return getAsString("statusmap_image");
	}

	/**
	 * The total number of services of the host
	 * 
	 * @return returns the value of the "total_services" column as int
	 */
	public int Total_services() throws NumberFormatException {
		return getAsInt("total_services");
	}

	/**
	 * The worst hard state of all of the host's services (OK <= WARN <= UNKNOWN <= CRIT)
	 * 
	 * @return returns the value of the "worst_service_hard_state" column as int
	 */
	public int Worst_service_hard_state() throws NumberFormatException {
		return getAsInt("worst_service_hard_state");
	}

	/**
	 * The worst soft state of all of the host's services (OK <= WARN <= UNKNOWN <= CRIT)
	 * 
	 * @return returns the value of the "worst_service_state" column as int
	 */
	public int Worst_service_state() throws NumberFormatException {
		return getAsInt("worst_service_state");
	}

	/**
	 * 3D-Coordinates: X
	 * 
	 * @return returns the value of the "x_3d" column as float
	 */
	public float X_3d() throws NumberFormatException {
		return getAsFloat("x_3d");
	}

	/**
	 * 3D-Coordinates: Y
	 * 
	 * @return returns the value of the "y_3d" column as float
	 */
	public float Y_3d() throws NumberFormatException {
		return getAsFloat("y_3d");
	}

	/**
	 * 3D-Coordinates: Z
	 * 
	 * @return returns the value of the "z_3d" column as float
	 */
	public float Z_3d() throws NumberFormatException {
		return getAsFloat("z_3d");
	}

	/**
	 * create the map for all columns of table Hosts. Key=column name, Value=column value
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

		addToHashtable("accept_passive_checks", getAsString(Accept_passive_checks()));
		addToHashtable("acknowledged", getAsString(Acknowledged()));
		addToHashtable("acknowledgement_type", getAsString(Acknowledgement_type()));
		addToHashtable("action_url", (Action_url()));
		addToHashtable("action_url_expanded", (Action_url_expanded()));
		addToHashtable("active_checks_enabled", getAsString(Active_checks_enabled()));
		addToHashtable("address", (Address()));
		addToHashtable("alias", (Alias()));
		addToHashtable("check_command", (Check_command()));
		addToHashtable("check_command_expanded", (Check_command_expanded()));
		addToHashtable("check_flapping_recovery_notification", getAsString(Check_flapping_recovery_notification()));
		addToHashtable("check_freshness", getAsString(Check_freshness()));
		addToHashtable("check_interval", getAsString(Check_interval()));
		addToHashtable("check_options", getAsString(Check_options()));
		addToHashtable("check_period", (Check_period()));
		addToHashtable("check_type", getAsString(Check_type()));
		addToHashtable("checks_enabled", getAsString(Checks_enabled()));
		addToHashtable("childs", (Childs()));
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
		addToHashtable("display_name", (Display_name()));
		addToHashtable("downtimes", (Downtimes()));
		addToHashtable("downtimes_with_info", (Downtimes_with_info()));
		addToHashtable("event_handler", (Event_handler()));
		addToHashtable("event_handler_enabled", getAsString(Event_handler_enabled()));
		addToHashtable("execution_time", getAsString(Execution_time()));
		addToHashtable("filename", (Filename()));
		addToHashtable("first_notification_delay", getAsString(First_notification_delay()));
		addToHashtable("flap_detection_enabled", getAsString(Flap_detection_enabled()));
		addToHashtable("groups", (Groups()));
		addToHashtable("hard_state", getAsString(Hard_state()));
		addToHashtable("has_been_checked", getAsString(Has_been_checked()));
		addToHashtable("high_flap_threshold", getAsString(High_flap_threshold()));
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
		addToHashtable("last_time_down", Last_time_down());
		addToHashtable("last_time_unreachable", getAsString(Last_time_unreachable()));
		addToHashtable("last_time_up", getAsString(Last_time_up()));
		addToHashtable("latency", getAsString(Latency()));
		addToHashtable("long_plugin_output", (Long_plugin_output()));
		addToHashtable("low_flap_threshold", getAsString(Low_flap_threshold()));
		addToHashtable("max_check_attempts", getAsString(Max_check_attempts()));
		addToHashtable("modified_attributes", getAsString(Modified_attributes()));
		addToHashtable("modified_attributes_list", (Modified_attributes_list()));
		addToHashtable("name", (Name()));
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
		addToHashtable("obsess_over_host", getAsString(Obsess_over_host()));
		addToHashtable("parents", (Parents()));
		addToHashtable("pending_flex_downtime", getAsString(Pending_flex_downtime()));
		addToHashtable("percent_state_change", getAsString(Percent_state_change()));
		addToHashtable("perf_data", (Perf_data()));
		addToHashtable("plugin_output", (Plugin_output()));
		addToHashtable("pnpgraph_present", getAsString(Pnpgraph_present()));
		addToHashtable("process_performance_data", getAsString(Process_performance_data()));
		addToHashtable("retry_interval", getAsString(Retry_interval()));
		addToHashtable("scheduled_downtime_depth", getAsString(Scheduled_downtime_depth()));
		addToHashtable("service_period", (Service_period()));
		addToHashtable("services", (Services()));
		addToHashtable("services_with_info", (Services_with_info()));
		addToHashtable("services_with_state", (Services_with_state()));
		addToHashtable("staleness", getAsString(Staleness()));
		addToHashtable("state", getAsString(State()));
		addToHashtable("state_type", getAsString(State_type()));
		addToHashtable("statusmap_image", (Statusmap_image()));
		addToHashtable("total_services", getAsString(Total_services()));
		addToHashtable("worst_service_hard_state", getAsString(Worst_service_hard_state()));
		addToHashtable("worst_service_state", getAsString(Worst_service_state()));
		addToHashtable("x_3d", getAsString(X_3d()));
		addToHashtable("y_3d", getAsString(Y_3d()));
		addToHashtable("z_3d", getAsString(Z_3d()));
		return mapKeyValue;
	}
}
