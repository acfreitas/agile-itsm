package br.com.centralit.bpm.negocio;

import java.util.List;
import java.util.Map;

import br.com.centralit.bpm.util.Enumerados.TipoAtribuicao;

public interface IItemTrabalho {
	
	public List<ItemTrabalho> resolve() throws Exception; 
	public void registra() throws Exception;
	public void inicia(String loginUsuario, Map<String, Object> objetos) throws Exception;
	public void executa(String loginUsuario, Map<String, Object> objetos) throws Exception;
	public void delega(String loginUsuario, String usuarioDestino, String grupoDestino) throws Exception;
	public void redireciona(String loginUsuario, String usuarioDestino, String grupoDestino) throws Exception;
	public void cancela(String loginUsuario) throws Exception;
    public void encerra() throws Exception;
    public boolean iniciado() throws Exception;
    public boolean pendente() throws Exception;
    public boolean resolvido() throws Exception;
    public boolean finalizado() throws Exception;
    public void atribui(String usuario, String grupo, TipoAtribuicao tipoAtribuicao) throws Exception;
}
