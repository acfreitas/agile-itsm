/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.centralit.nagios.livestatus.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.centralit.nagios.livestatus.query.LSQuery;
import br.com.centralit.nagios.livestatus.tables.LivestatusSeparator;

/**
 *
 * @author adenir
 */
public class Response {

	private final List<String> string_list;
	private String[] string_array_headers;
	private final LSQuery query;
	private int line_count = 0;

	public Response(LSQuery pQuery) {
		string_list = new ArrayList<>();
		query = pQuery;
	}

	public String[] getListHeaders() {
		return string_array_headers;
	}

	public List<String> asArrayList() {
		return string_list;
	}

	public String[] asArrayString() {
		String[] sizeArr = new String[string_list.size()];
		return string_list.toArray(sizeArr);
	}

	public Map<String, String> getMapColumnsAndValue() {
		int qdlines = size();
		Map<String, String> mapKeyValue = new HashMap<>();
		if (!(qdlines == 1 || string_array_headers.length == 1)) {
			mapKeyValue.put("error", String.format("Size of Columns list or size of headers list differ the 1 , size=(%s)", string_array_headers.length));
			return mapKeyValue;
		}

		for (int k = 0; k < qdlines; k++) {
			String[] cols = string_list.get(k).split(LivestatusSeparator.SEP2());
			if (cols.length != string_array_headers.length) {
				mapKeyValue.put("error", String.format("Headers count(%s) not equal columns count(%s)", string_array_headers.length, cols.length));
				return mapKeyValue;
			}

			for (int i = 0; i < cols.length; i++) {
				mapKeyValue.put(string_array_headers[i], cols[i]);
			}
		}

		return mapKeyValue;
	}

	public String[] getHeaders_and_columns_asArrayString() {
		int qdlines = size();
		if (!(qdlines > 0)) {
			return new String[] { String.format("Don't have columns, count=(%s)", string_array_headers.length) };
		}

		String[] hc = new String[qdlines * string_list.get(0).split(LivestatusSeparator.SEP2()).length];
		for (int k = 0; k < qdlines; k++) {
			String[] cols = string_list.get(k).split(LivestatusSeparator.SEP2());
			if (cols.length != string_array_headers.length) {
				return new String[] { String.format("Headers count(%s) not equal columns count(%s)", string_array_headers.length, cols.length) };
			}

			for (int i = 0; i < cols.length; i++) {
				hc[i] = string_array_headers[i] + " = " + cols[i];
			}
		}

		return hc;
	}

	public String asString() {
		String str = "";

		if (string_list != null && !string_list.isEmpty()) {
			for (String name : string_list) {
				str += name + LivestatusSeparator.SEP1();
			}
		}
		return str;
	}

	public void add(String line) {
		boolean hasColumns = (query.hasColumns() && query.hasColumnHeaders()) || (!query.hasColumns());
		if (hasColumns && line_count == 0) {
			string_array_headers = line.split(LivestatusSeparator.SEP2());
		} else {
			string_list.add(line);
		}
		line_count += 1;
	}

	public int size() {
		return string_list.size();
	}

}
