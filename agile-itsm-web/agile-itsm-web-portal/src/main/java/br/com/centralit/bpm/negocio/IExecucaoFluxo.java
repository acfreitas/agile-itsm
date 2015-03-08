package br.com.centralit.bpm.negocio;

import java.util.HashMap;
import java.util.Map;

import br.com.centralit.bpm.dto.EventoFluxoDTO;
import br.com.centralit.bpm.dto.FluxoDTO;
import br.com.centralit.bpm.dto.ObjetoNegocioFluxoDTO;

public interface IExecucaoFluxo {
	
	public InstanciaFluxo inicia() throws Exception;
	public InstanciaFluxo inicia(String nomeFluxo, Integer idFase) throws Exception;
	public InstanciaFluxo inicia(FluxoDTO fluxoDto, Integer idFase) throws Exception;
	public void executa(String loginUsuario, ObjetoNegocioFluxoDTO objetoNegocioDto, Integer idItemTrabalho, String acao, HashMap<String, Object> map) throws Exception;
	public void mapObjetoNegocio(Map<String, Object> map) throws Exception;

	public void encerra() throws Exception;
	public void reabre(String loginUsuario) throws Exception;
	public void suspende(String loginUsuario) throws Exception;
	public void reativa(String loginUsuario) throws Exception;
	
	public void enviaEmail(Integer idModeloEmail) throws Exception;
	public void enviaEmail(String identificador) throws Exception;
	public void enviaEmail(String identificador, String[] destinatarios) throws Exception;
	
	public void delega(String loginUsuario, ObjetoNegocioFluxoDTO objetoNegocioDto, Integer idItemTrabalho, String usuarioDestino, String grupoDestino) throws Exception;
	public void direcionaAtendimento(String loginUsuario, ObjetoNegocioFluxoDTO objetoNegocioDto, String grupoAtendimento) throws Exception;
    public void executaEvento(EventoFluxoDTO eventoFluxoDto) throws Exception;
    
    public void validaEncerramento() throws Exception;
    public void atribuiAcompanhamento(ItemTrabalho itemTrabalho, String usuario, String grupo) throws Exception;
    public void verificaSLA(ItemTrabalho itemTrabalho) throws Exception;
}
