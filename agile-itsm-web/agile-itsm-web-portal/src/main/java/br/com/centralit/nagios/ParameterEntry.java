/* Released under the GPL2. See license.txt for details. */
package br.com.centralit.nagios;

/**
 * Class ParameterEntry The most basic class for storing a parameter(name) + value pair.
 * 
 * @author Folkert van Heusden
 * @version %I%, %G%
 * @since 0.1
 */
public class ParameterEntry {
	String name, value;

	/**
	 * Sets the name and value.
	 * 
	 * @param name
	 *            parameter/name
	 * @param value
	 *            value for this parameter/name
	 */
	public ParameterEntry(String name, String value) {
		this.name = name;
		this.value = value;
	}

	/**
	 * Returns the parameter name.
	 * 
	 * @return String
	 */
	public String getParameterName() {
		return name;
	}

	/**
	 * Returns the parameter value.
	 * 
	 * @return String
	 */
	public String getParameterValue() {
		return value;
	}
}
