
package br.com.centralit.citajax.util;

import java.io.Serializable;

public class Enumerados implements Serializable {

	private static final long serialVersionUID = 2466679077707063321L;

	/**
	 * Enumerado para armazenar os Tipos de Data.
	 * 
	 * DATE_DEFAULT: dd/MM/yyyy ou MM/dd/yyyy, TIMESTAMP_DEFAULT: dd/MM/yyyy HH:mm ou MM/dd/yyyy HH:mm, TIMESTAMP_WITH_SECONDS: dd/MM/yyyy HH:mm:ss ou MM/dd/yyyy HH:mm:ss, FORMAT_DATABASE: yyyy-MM-dd
	 * 
	 * @author valdoilo.damasceno
	 * @since 04.02.2013
	 */
	public enum TipoDate {
		DATE_DEFAULT("DATE_DEFAULT"), TIMESTAMP_DEFAULT("TIMESTAMP_DEFAULT"), TIMESTAMP_WITH_SECONDS("TIMESTAMP_WITH_SECONDS"), FORMAT_DATABASE("FORMAT_DATABASE");

		private String tipoData;

		TipoDate(String tipoDate) {
			this.tipoData = tipoDate;
		}

		public String getTipoDate() {
			return tipoData;
		}
	}

}
