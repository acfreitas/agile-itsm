/**
 * CentalIT - CTISMart
 */
package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

/**
 * @author Vadoilo Damasceno
 * 
 */
public class PalavraGemeaDTO implements IDto {

	private static final long serialVersionUID = 7907826613317683397L;

	private Integer idPalavraGemea;

	private String palavra;

	private String palavraCorrespondente;

	public Integer getIdPalavraGemea() {
		return idPalavraGemea;
	}

	public void setIdPalavraGemea(Integer idPalavraGemea) {
		this.idPalavraGemea = idPalavraGemea;
	}

	public String getPalavra() {
		return palavra;
	}

	public void setPalavra(String palavra) {
		this.palavra = palavra;
	}

	public String getPalavraCorrespondente() {
		return palavraCorrespondente;
	}

	public void setPalavraCorrespondente(String palavraCorrespondente) {
		this.palavraCorrespondente = palavraCorrespondente;
	}

}
