/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.centralit.nagios.livestatus.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.centralit.nagios.livestatus.tables.LivestatusSeparator;

/**
 * 
 * @author adenir
 */
public final class LSQuery {

	private boolean has_columns = false;

	public boolean hasColumns() {
		return has_columns;
	}

	private boolean has_columnHeaders = false;

	public boolean hasColumnHeaders() {
		return has_columnHeaders;
	}

	private boolean has_filter = false;

	public boolean hasFilter() {
		return has_filter;
	}

	public String command = null;
	public String before_wait = null;
	public String after_wait = null;
	public List<String> query;
	public List<String> columns;
	public List<String> filters;
	public List<String> stats;

	// Constructor of the Query instance
	// base is by default null but if its filled the base if associated to the GET of the query
	public LSQuery(String pBase, String pColumns, String pFilters) {
		setCommand(pBase);
		for (String c : pColumns.split(" ")) {
			addColumn(c);
		}

		for (String f : pFilters.split(",")) {
			addFilters(f);
		}

	}

	// Get method is used to set the object to search for in \nagios \mklivestatus
	public final void setCommand(String pGet) {
		if (pGet != null && !pGet.isEmpty()) {
			command = pGet;
		}
	}

	// Add a field to columns in the query for the GET object
	public void addColumn(String pName) {
		String[] pcols = {};
		if (!(pName == null || pName.isEmpty())) {
			has_columns = true;
			pcols = pName.split(" ");
			if (columns == null) {
				columns = new ArrayList<>();
			}

			for (String c : pcols) {
				columns.add(c);
			}
		}

	}

	// Add a field to filters in the query for the GET object
	public void addFilters(String pName) {
		String[] pfilters = {};
		if (!(pName == null || pName.isEmpty())) {
			if (filters == null) {
				filters = new ArrayList<>();
			}
			pfilters = pName.split(",");

			for (String f : pfilters) {
				filters.add(f);
			}
		}
	}

	// Add a field to stats in the query for the GET object
	public void addStats(String pName) {
		if (!(pName == null || pName.isEmpty())) {
			if (stats == null) {
				stats = new ArrayList<>();
			}
		}

		stats.add(pName);
	}

	// get as signed int 32 - 4 byte ---------------------- int SIGNED
	public int getAsInt(String column) throws NumberFormatException {

		try {
			return Integer.parseInt(column);
		} catch (NumberFormatException e) {
			return -1;
		}

	}

	public String to_s(Map<String, String> options) {
		String strQuery = "";

		if (command == null) {
			return "";
		}
		strQuery = "GET " + command + "\n";

		if (columns != null && columns.size() > 0) {
			strQuery += "Columns: ";
			for (String c : columns) {
				strQuery += c + " ";
			}
			strQuery += "\n";
		}

		if (filters != null && filters.size() > 0) {
			has_filter = true;
			for (String f : filters) {
				strQuery += "Filter: " + f.trim() + "\n";
			}
		}

		if (stats != null && stats.size() > 0) {
			for (String s : stats) {

				strQuery += s + "\n";
			}
		}

		if (before_wait != null && before_wait.length() > 0) {
			// @before_wait.each do |wait|
			// query += wait.to_s+"\n"
			// }
		}

		if (after_wait != null && after_wait.length() > 0) {
			// @after_wait.each do |wait|
			// query += wait.to_s+"\n"
		}

		// //set column headers
		boolean column_headers = false;
		if (options.containsKey("columns_headers") && !options.get("column_headers").isEmpty()) {
			column_headers = true;
		}

		// set user
		String user = null;
		if (options.containsKey("user") && options.get("user").isEmpty()) {
			user = options.get("user");
		}

		// set limit
		int limit = -1;
		if (options.containsKey("limit") && getAsInt(options.get("limit")) > 0) {
			limit = getAsInt(options.get("limit"));
		}

		// set localtime
		int localtime = -1;
		if (options.containsKey("localtime") && getAsInt(options.get("localtime")) > 0) {
			localtime = getAsInt(options.get("localtime"));
		}

		// set separator
		String separator = LivestatusSeparator.getSeparators();
		if (options.containsKey("separator") && !options.get("separator").isEmpty()) {
			separator = options.get("separator");
		}

		// set output
		String output = "";
		if (options.containsKey("output") && getAsInt(options.get("output")) > 0) {
			String s = options.get("output");
			if (s.equals("json") || s.equals("python")) {
				output = options.get("output");
			} else {
				throw new IllegalArgumentException("Output must be one of 'json' or'python'");
			}
		}

		// the }line must be empty
		if (!strQuery.endsWith("\n")) {
			strQuery += "\n";
		}

		// set user if needed
		if ((user != null) && (!user.isEmpty()) && (strQuery.matches("/^GET\\s+(hosts|hostgroups|services|servicegroup|log)\\s*$/"))) {
			strQuery += "AuthUser: " + user + "\n";
		}

		if (localtime != -1) {
			strQuery += "LocalTime: " + localtime + "\n";
		}

		// set columns headers
		if (column_headers) {
			strQuery += "ColumnHeaders: on\n";
			has_columnHeaders = true;
		} else {
			strQuery += "ColumnHeaders: off\n";
			has_columnHeaders = false;
		}

		// set the limit
		if (limit > -1) {
			strQuery += "Limit: " + limit + "\n";
		}

		// set the output format
		if (!output.isEmpty()) {
			strQuery += "OutputFormat: " + output + "\n";
		}

		// set the output format
		if (!separator.isEmpty()) {
			strQuery += "Separators: " + separator + "\n";
		}

		// get error message if some are given
		// strQuery += "ResponseHeader: fixed16\n";
		// add wait to query but before filter predicats
		System.out.println(strQuery);

		return strQuery;
	}

	public void options(Map<String, String> options) {

	}

	public void teste() {
		options(new HashMap<String, String>() {
			{
				put("k1", "Foo");
				put("k2", "Bar");
				put("k2", "Bla");
			}
		});
	}
}
