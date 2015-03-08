/* Released under the GPL2. See license.txt for details. */
package br.com.centralit.nagios;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Service
 * Contains all parameters/values for a service.
 *
 * @author	Folkert van Heusden
 * @version	%I%, %G%
 * @since	0.1 
 */
public class Service
{
	List<ParameterEntry> parameterEntries = new ArrayList<ParameterEntry>();

	String serviceName;

	/**
	 * Sets the service name.
	 *
	 * @param serviceName	Name of the service.
	 */
	public Service(String serviceName)
	{
		this.serviceName = serviceName;
	}

	/**
	 * Returns the service name.
	 */
	public String getServiceName()
	{
		return serviceName;
	}

	/**
	 * Adds a parameter+value.
	 */
	public void addParameter(ParameterEntry parameterEntry)
	{
		parameterEntries.add(parameterEntry);
	}

	/**
	 * Returns all parameters (and their values) for this service.
	 *
	 * @return	All parameters.
	 */
	public List<ParameterEntry> getParameters()
	{
		return parameterEntries;
	}

        /**
         * Get the value of a service-parameter.
         *
         * @return      A string with the value. Indeed, also values are returned as a string.
         */
        public String getParameter(String parameter)
        {
                for(ParameterEntry parameterEntry : parameterEntries)
                {
                        if (parameterEntry.getParameterName().equals(parameter))
                                return parameterEntry.getParameterValue();
                }

                return null;
        }
}
