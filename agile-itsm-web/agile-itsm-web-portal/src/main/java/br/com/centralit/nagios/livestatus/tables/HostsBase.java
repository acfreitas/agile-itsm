/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.centralit.nagios.livestatus.tables;

/**
 * 
 * @author adenir
 */
public class HostsBase extends LiveStatusBase {

	private final String TABLE = "hosts";

	public HostsBase(String pPath) {
		super(pPath);
	}

	public String[] get_hosts() throws Exception {
		return execute_query(TABLE, "name", "").asArrayString();
	}

	public String[] hosts() throws Exception {
		return get_hosts();
	}

	public int state(String host) throws Exception {
		return getAsInt(execute_query(TABLE, "state", "name = " + host).asString());
	}

	// os vários serviço do host
	public String[] services(String host) throws Exception {
		return execute_query(TABLE, "services", "name = " + host).asString().split(LivestatusSeparator.SEP3());
	}

	// os vários serviço do host com informações
	public String[] services_with_info(String host) throws Exception {
		return execute_query(TABLE, "services_with_info", "name = " + host).asArrayString();
	}

	// um serviço do host com informações
	public String service_with_info(String host, String service) throws Exception {
		for (String ss : execute_query(TABLE, "services_with_info", "name = " + host).asArrayList()) {
			String[] st = ss.split("|");
			if (service.equals(st[0])) {
				return ss;
			}
		}

		return "";
	}

	public int service_state(String host, String service) throws Exception {
		return getAsInt(service_with_info(host, service));
	}

}
