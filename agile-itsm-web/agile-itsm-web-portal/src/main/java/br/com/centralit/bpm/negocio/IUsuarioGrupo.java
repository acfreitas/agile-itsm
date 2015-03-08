package br.com.centralit.bpm.negocio;

import java.util.List;

import br.com.centralit.bpm.dto.GrupoBpmDTO;
import br.com.centralit.bpm.dto.UsuarioBpmDTO;
import br.com.citframework.integracao.TransactionControler;

public interface IUsuarioGrupo {
	
	public UsuarioBpmDTO recuperaUsuario(Integer idUsuario) throws Exception;
	public UsuarioBpmDTO recuperaUsuario(String login) throws Exception;
	public GrupoBpmDTO recuperaGrupo(Integer idGrupo) throws Exception;
	public GrupoBpmDTO recuperaGrupo(String siglaGrupo) throws Exception;

	public boolean existeUsuario(String login) throws Exception;
	public boolean existeGrupo(String siglaGrupo) throws Exception;
	public List<GrupoBpmDTO> getGruposDoUsuario(String login) throws Exception;
	
	public boolean pertenceAoGrupo(String login, String siglaGrupo) throws Exception;
	public void setTransacao(TransactionControler transacao) throws Exception;
	
}
