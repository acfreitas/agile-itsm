package br.com.centralit.nagios.livestatus.tables;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.centralit.nagios.livestatus.query.LSQuery;
import br.com.centralit.nagios.livestatus.response.Response;

public class LiveStatusBase {

	public static final Map<String, String> mapComments = new HashMap<>();
	public Map<String, String> mapObjects = new HashMap<>();
	public final Map<String, String> mapKeyValue = new HashMap<>();
	private final Map<String, String> mapVazio = new HashMap<String, String>() {

		private static final long serialVersionUID = -1494959981052297712L;

	};

	protected int count = 0;
	protected String host = "";
	protected int port = -1;
	protected String tableName = "hosts";
	protected String socket_type = "tcp";
	protected String path_or_host = "";

	/*
	 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
	 */
	/**
	 *
	 * @author adenir
	 */
	/**
	 * Initialize the nagios mklivestatus socket informations.
	 *
	 * @param path
	 *            = Two type of socket are supported for now: * TCP : path equal to "tcp://host:port" File : where path is the path to the file
	 */
	public LiveStatusBase(String path) {

		// check socket type
		// if the path start with tcp:// => tcp socket
		if (path.startsWith("tcp://")) {
			String[] table = path.split(":");
			socket_type = "tcp";
			path_or_host = table[1].split("//")[1];
			port = Integer.parseInt(table[2]);

			// default socket type is set to file
		} else {
			File f = new File(path);
			if (f.exists() && !f.isDirectory()) {
				path_or_host = path;
				socket_type = "file";
			} else {
				throw new IllegalArgumentException("Socket error : socket type not recognized for \"" + path + "\"");
			}
		}
	}

	public Socket connectToSocket(String host, int port) throws Exception {
		Socket socket = new Socket();

		socket.connect(new InetSocketAddress(host, port));

		return socket;
	}

	/**
	 * Realiza a conexão e executa a query.
	 *
	 * @param request
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public Response executeQuery(String request, LSQuery query) throws Exception {
		if (path_or_host.isEmpty() || port == -1) {
			throw new IllegalArgumentException("Server/host or/and port is invalid");
		}

		Response response = new Response(query);

		try (Socket socket = connectToSocket(path_or_host, port)) {
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

			// System.out.println(socket);
			System.out.println(request);

			out.write(request, 0, request.length());
			out.flush();
			socket.shutdownOutput();

			while (true) {
				String line = in.readLine();
				if (line == null) {
					break;
				}

				response.add(line);
			}
		}

		return response;
	}

	public Response execute_query(String table, String columns, String filters) throws Exception {
		LSQuery query = new LSQuery(table, columns, filters);

		return executeQuery(query.to_s(mapVazio), query);
	}

	public String[] getHeaders(String table) throws Exception {
		LSQuery query = new LSQuery(table, "", "");
		return executeQuery(query.to_s(new HashMap<String, String>() {
			{
				put("limit", "0");
			}
		}), query).getListHeaders();
	}

	public void setMapObjects(String table, String filter) {
		mapObjects.clear();
		try {
			mapObjects = execute_query(table, "", filter).getMapColumnsAndValue();
		} catch (Exception ex) {
			Logger.getLogger(Hosts.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	// get as String ---------------------- Date
	public String getAsString(Date d) {
		// Create an instance of SimpleDateFormat used for formatting
		// the string representation of date (year/day/month)
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

		// Using DateFormat format method we can create a string
		// representation of a date with the defined format.
		return df.format(d);
	}

	// get as String ---------------------- Int
	public String getAsString(int d) {
		return Integer.toString(d);
	}

	// get as String ---------------------- Int
	public String getAsString(Float d) {
		return Float.toString(d);
	}

	// get as String ---------------------- STRING
	public String getAsString(String column) {
		return mapObjects.get(column);

	}

	// get as signed int 32 - 4 byte ---------------------- int SIGNED
	public int getAsInt(String column) throws NumberFormatException {

		try {
			return Integer.parseInt(mapObjects.get(column));
		} catch (NumberFormatException e) {
			return -1;
		}

	}

	// get as signed int 32 - 4 byte ---------------------- int SIGNED
	public String getAsList(String column) {
		return mapObjects.get(column);
	}

	// get as signed int 32 - 4 byte ---------------------- int SIGNED
	public String getAsDict(String column) {
		return mapObjects.get(column);
	}

	// get as signed int 32 - 4 byte ---------------------- int SIGNED
	public Date getAsTime(String column) throws NumberFormatException {
		String value = mapObjects.get(column);
		if (value == null || value.isEmpty() || value.equals("")) {
			System.out.println("................ value = 1000 para " + column);
			value = "0";
		}

		return new java.util.Date(Long.parseLong(value) * 1000);
	}

	// get as signed int 32 - 4 byte ---------------------- int SIGNED
	public float getAsFloat(String column) throws NumberFormatException {
		String value = mapObjects.get(column);
		if (value == null || value.isEmpty() || value.equals("")) {
			System.out.println("................ value = -255 para " + column);
			value = "-255";
		}
		return Float.parseFloat(value);
	}

	protected Boolean addToHashtable(String key, Object ob) {
		// The Add method throws an exception if the new key is
		// already in the hash table.
		try {
			mapKeyValue.put(key, ob.toString());
			return true;
		} catch (NullPointerException e) {
			System.out.println(String.format("An element with Key = %s already exists.", key));
			return false;
		}

	}

	protected Boolean addToHashtable(String key, String ob) {
		// The Add method throws an exception if the new key is
		// already in the hash table.
		try {
			mapKeyValue.put(key, ob);
			return true;
		} catch (NullPointerException e) {
			System.out.println(String.format("An element with Key = %s already exists.", key));
			return false;
		}

	}

	public String getComment(String key) {
		if (mapComments.containsKey(key)) {
			return mapComments.get(key);
		} else {
			return "s/c";
		}
	}

	public String toStringKeyValueComment(String key) {
		return key + " = " + mapObjects.get(key) + " | " + getComment(key);
	}

	public String toStringKeyValue(String key) {
		return key + " = " + mapObjects.get(key);
	}

	public String toStringValue(String key) {
		return mapObjects.get(key);
	}

	public String getValue(String key) {
		return mapObjects.get(key);
	}

	public Map<String, String> asArrayString(String table, String filter) throws ParseException {
		return mapKeyValue;
	}

	public String[] asArrayStringKeyValues(String table, String filter) throws ParseException {
		asArrayString(table, filter);

		String[] array = new String[mapKeyValue.keySet().size()];
		// Sorted - classificando
		Map<String, String> treeMap = new TreeMap<>(mapKeyValue);
		Set<Map.Entry<String, String>> entrySet = treeMap.entrySet();

		int idcc = 0;
		for (Map.Entry<String, String> entry : entrySet) {
			array[idcc++] = entry.getKey() + "  =  " + entry.getValue();
		}

		return array;
	}
}
