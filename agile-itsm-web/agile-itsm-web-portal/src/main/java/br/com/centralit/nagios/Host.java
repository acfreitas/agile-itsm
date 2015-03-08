/* Released under the GPL2. See license.txt for details. */
package br.com.centralit.nagios;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Host contains all host- and servicechecks results for one host.
 * 
 * @author Folkert van Heusden
 * @version %I%, %G%
 * @since 0.1
 */
public class Host {

	String hostName;

	String nagiosSource;

	List<ParameterEntry> hostEntries = new ArrayList<ParameterEntry>();

	List<Service> services = new ArrayList<Service>();

	/**
	 * @param hostName
	 *            Name of the host this object describes.
	 */
	public Host(String nagiosSource, String hostName) {
		this.nagiosSource = nagiosSource;
		this.hostName = hostName;
	}

	public String getNagiosSource() {
		return nagiosSource;
	}

	/**
	 * @return The hostname set in the constructor.
	 */
	public String getHostName() {
		return hostName;
	}

	/**
	 * Adiciona um novo serviço ao host.
	 * 
	 * @param serviceName
	 *            Nome do serviço.
	 * @return Service - Serviço.
	 */
	Service addAndOrFindService(String serviceName) {
		for (Service currentService : services) {
			if (currentService.getServiceName().equals(serviceName))
				return currentService;
		}

		Service newService = new Service(serviceName);

		services.add(newService);

		return newService;
	}

	/**
	 * @return Returns a list of all services checked for this host.
	 */
	public List<Service> getServices() {
		return services;
	}

	/**
	 * Each host has a couple of parameters as well. These can be obtained using this method.
	 * 
	 * @return A List of 'ParameterEntry'-objects defining each parameter of this host
	 */
	public List<ParameterEntry> getParameters() {
		return hostEntries;
	}

	/**
	 * Get the value of a host-parameter.
	 * 
	 * @return A string with the value. Indeed, also values are returned as a string.
	 */
	public String getParameter(String parameter) {
		for (ParameterEntry parameterEntry : hostEntries) {
			if (parameterEntry.getParameterName().equals(parameter))
				return parameterEntry.getParameterValue();
		}

		return null;
	}

	/**
	 * Adiciona um novo ParameterEntry (Parametro/Valor) ao Host.
	 * 
	 * @param hostParameterName
	 *            Nome do parâmetro do host.
	 * @param hostParameterValue
	 *            Valor do parâmetro do host.
	 * @return ParameterEntry - Parametro/Valor.
	 */
	public ParameterEntry addParameter(String hostParameterName, String hostParameterValue) {

		for (ParameterEntry currentHostEntry : hostEntries) {
			if (currentHostEntry.getParameterName().equals(hostParameterName))
				return currentHostEntry;
		}

		ParameterEntry currentHostEntry = new ParameterEntry(hostParameterName, hostParameterValue);

		hostEntries.add(currentHostEntry);

		return currentHostEntry;
	}

	/**
	 * Add a parameter/value pair to a service of this host.
	 * 
	 * @param serviceName
	 *            Service to which this parameter/value pair applies.
	 * @param serviceParameterName
	 *            Name of the parameter.
	 * @param serviceParameterValue
	 *            Parameter value.
	 * @return Altered service
	 */
	public Service addServiceEntry(String serviceName, String serviceParameterName, String serviceParameterValue) {
		ParameterEntry parameterEntry = new ParameterEntry(serviceParameterName, serviceParameterValue);

		for (Service curService : services) {
			if (curService.getServiceName().equals(serviceName)) {
				curService.addParameter(parameterEntry);
				return curService;
			}
		}

		Service newService = new Service(serviceName);
		newService.addParameter(parameterEntry);
		services.add(newService);

		return newService;
	}

	public Service getService(String serviceName) {
		for (Service currentService : services) {
			if (currentService.getServiceName().equals(serviceName))
				return currentService;
		}

		return null;
	}
}
