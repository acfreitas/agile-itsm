package br.com.centralit.citcorpore.bean;

import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.IDto;

public class VersaoDTO implements IDto {
	private static final long serialVersionUID = 1L;
	private Integer idUsuario;
	private Integer idVersao;
	private String nomeVersao;

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public Integer getIdVersao() {
		return idVersao;
	}

	public String getNomeFisicoArquivo() {
		String nomeFisicoArquivo = new String();
		if (getNomeVersao() != null) {
			nomeFisicoArquivo = "deploy_versao_" + getNomeVersao() + "_" + CITCorporeUtil.SGBD_PRINCIPAL + ".sql";
		}
		return nomeFisicoArquivo;
	}

	public String getNomeVersao() {
		return nomeVersao;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public void setIdVersao(Integer idVersao) {
		this.idVersao = idVersao;
	}

	public void setNomeVersao(String nomeVersao) {
		this.nomeVersao = nomeVersao;
	}
}
