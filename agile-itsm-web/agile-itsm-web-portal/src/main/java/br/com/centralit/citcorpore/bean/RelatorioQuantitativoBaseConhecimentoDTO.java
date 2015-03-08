package br.com.centralit.citcorpore.bean;

import java.util.Collection;

import br.com.citframework.dto.IDto;

public class RelatorioQuantitativoBaseConhecimentoDTO implements IDto {
	
	private static final long serialVersionUID = -1501060076093192977L;
	
	private Collection<BaseConhecimentoDTO> listaBaseConhecimento;
	private Collection<ComentariosDTO> listaComentarios;
	private Collection<BaseConhecimentoDTO> listaAutores;
	private Collection<BaseConhecimentoDTO> listaAprovadores;
	private Collection<BaseConhecimentoDTO> listaPublicadosPorOrigem;
	private Collection<BaseConhecimentoDTO> listaNaoPublicadosPorOrigem;
	private Collection<BaseConhecimentoDTO> listaConhecimentoQuantitativoEmLista;
	
	public Collection<BaseConhecimentoDTO> getListaBaseConhecimento() {
		return listaBaseConhecimento;
	}
	public void setListaBaseConhecimento(Collection<BaseConhecimentoDTO> listaBaseConhecimento) {
		this.listaBaseConhecimento = listaBaseConhecimento;
	}
	public Collection<ComentariosDTO> getListaComentarios() {
		return listaComentarios;
	}
	public void setListaComentarios(Collection<ComentariosDTO> listaComentarios) {
		this.listaComentarios = listaComentarios;
	}
	public Collection<BaseConhecimentoDTO> getListaAutores() {
		return listaAutores;
	}
	public void setListaAutores(Collection<BaseConhecimentoDTO> listaAutores) {
		this.listaAutores = listaAutores;
	}
	public Collection<BaseConhecimentoDTO> getListaAprovadores() {
		return listaAprovadores;
	}
	public void setListaAprovadores(Collection<BaseConhecimentoDTO> listaAprovadores) {
		this.listaAprovadores = listaAprovadores;
	}
	public Collection<BaseConhecimentoDTO> getListaPublicadosPorOrigem() {
		return listaPublicadosPorOrigem;
	}
	public void setListaPublicadosPorOrigem(Collection<BaseConhecimentoDTO> listaPublicadosPorOrigem) {
		this.listaPublicadosPorOrigem = listaPublicadosPorOrigem;
	}
	public Collection<BaseConhecimentoDTO> getListaNaoPublicadosPorOrigem() {
		return listaNaoPublicadosPorOrigem;
	}
	public void setListaNaoPublicadosPorOrigem(Collection<BaseConhecimentoDTO> listaNaoPublicadosPorOrigem) {
		this.listaNaoPublicadosPorOrigem = listaNaoPublicadosPorOrigem;
	}
	public Collection<BaseConhecimentoDTO> getListaConhecimentoQuantitativoEmLista() {
		return listaConhecimentoQuantitativoEmLista;
	}
	public void setListaConhecimentoQuantitativoEmLista(Collection<BaseConhecimentoDTO> listaConhecimentoQuantitativoEmLista) {
		this.listaConhecimentoQuantitativoEmLista = listaConhecimentoQuantitativoEmLista;
	}
	
}
