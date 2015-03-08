package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class ExportManualBIDTO implements IDto {

	private static final long serialVersionUID = -5911968771759841485L;

	private String pasta;

	public String getPasta() {
		return pasta;
	}

	public void setPasta(String pasta) {
		this.pasta = pasta;
	}

}
