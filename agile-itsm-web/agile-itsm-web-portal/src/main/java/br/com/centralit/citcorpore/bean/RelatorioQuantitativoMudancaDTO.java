package br.com.centralit.citcorpore.bean;

import java.util.Collection;

import br.com.citframework.dto.IDto;

public class RelatorioQuantitativoMudancaDTO implements IDto {
	private static final long serialVersionUID = 1L;

	private Collection<RequisicaoMudancaDTO> listaQuantidadeERelacionamentos;
	private Collection<RequisicaoMudancaDTO> listaQuantidadePorImpacto;
	private Collection<RequisicaoMudancaDTO> listaQuantidadePorPeriodo;
	private Collection<RequisicaoMudancaDTO> listaQuantidadePorProprietario;
	private Collection<RequisicaoMudancaDTO> listaQuantidadePorSolicitante;
	private Collection<RequisicaoMudancaDTO> listaQuantidadePorStatus;
	private Collection<RequisicaoMudancaDTO> listaQuantidadePorUrgencia;
	private Collection<RequisicaoMudancaDTO> listaQuantidadeSemAprovacaoPorPeriodo;

	public Collection<RequisicaoMudancaDTO> getListaQuantidadeERelacionamentos() {
		return listaQuantidadeERelacionamentos;
	}

	public Collection<RequisicaoMudancaDTO> getListaQuantidadePorImpacto() {
		return listaQuantidadePorImpacto;
	}

	public Collection<RequisicaoMudancaDTO> getListaQuantidadePorPeriodo() {
		return listaQuantidadePorPeriodo;
	}

	public Collection<RequisicaoMudancaDTO> getListaQuantidadePorProprietario() {
		return listaQuantidadePorProprietario;
	}

	public Collection<RequisicaoMudancaDTO> getListaQuantidadePorSolicitante() {
		return listaQuantidadePorSolicitante;
	}

	public Collection<RequisicaoMudancaDTO> getListaQuantidadePorStatus() {
		return listaQuantidadePorStatus;
	}

	public Collection<RequisicaoMudancaDTO> getListaQuantidadePorUrgencia() {
		return listaQuantidadePorUrgencia;
	}

	public Collection<RequisicaoMudancaDTO> getListaQuantidadeSemAprovacaoPorPeriodo() {
		return listaQuantidadeSemAprovacaoPorPeriodo;
	}

	public void setListaQuantidadeERelacionamentos(Collection<RequisicaoMudancaDTO> listaQuantidadeERelacionamentos) {
		this.listaQuantidadeERelacionamentos = listaQuantidadeERelacionamentos;
	}

	public void setListaQuantidadePorImpacto(Collection<RequisicaoMudancaDTO> listaQuantidadePorImpacto) {
		this.listaQuantidadePorImpacto = listaQuantidadePorImpacto;
	}

	public void setListaQuantidadePorPeriodo(Collection<RequisicaoMudancaDTO> listaQuantidadePorPeriodo) {
		this.listaQuantidadePorPeriodo = listaQuantidadePorPeriodo;
	}

	public void setListaQuantidadePorProprietario(Collection<RequisicaoMudancaDTO> listaQuantidadePorProprietario) {
		this.listaQuantidadePorProprietario = listaQuantidadePorProprietario;
	}

	public void setListaQuantidadePorSolicitante(Collection<RequisicaoMudancaDTO> listaQuantidadePorSolicitante) {
		this.listaQuantidadePorSolicitante = listaQuantidadePorSolicitante;
	}

	public void setListaQuantidadePorStatus(Collection<RequisicaoMudancaDTO> listaQuantidadePorStatus) {
		this.listaQuantidadePorStatus = listaQuantidadePorStatus;
	}

	public void setListaQuantidadePorUrgencia(Collection<RequisicaoMudancaDTO> listaQuantidadePorUrgencia) {
		this.listaQuantidadePorUrgencia = listaQuantidadePorUrgencia;
	}

	public void setListaQuantidadeSemAprovacaoPorPeriodo(Collection<RequisicaoMudancaDTO> listaQuantidadeSemAprovacaoPorPeriodo) {
		this.listaQuantidadeSemAprovacaoPorPeriodo = listaQuantidadeSemAprovacaoPorPeriodo;
	}

}