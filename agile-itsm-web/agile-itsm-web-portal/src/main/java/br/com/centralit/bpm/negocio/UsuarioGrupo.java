package br.com.centralit.bpm.negocio;

import java.util.List;

import br.com.centralit.bpm.config.Config;
import br.com.centralit.bpm.dto.GrupoBpmDTO;
import br.com.centralit.bpm.dto.UsuarioBpmDTO;
import br.com.citframework.integracao.TransactionControler;


public class UsuarioGrupo implements IUsuarioGrupo {
	
	private IUsuarioGrupo singleton;
	private TransactionControler transacao;

	private IUsuarioGrupo getInstance() throws Exception {
		if (singleton == null) {
			String classe = Config.getPropriedade("classe.usuario.grupo");
			try {
				singleton = (IUsuarioGrupo) Class.forName(classe).newInstance();
			} catch (Exception e) {
				throw new Exception("Classe de configuração de usuários e grupos não encontrada");
			}
		}
		return singleton;
	}

	@Override
	public UsuarioBpmDTO recuperaUsuario(Integer idUsuario) throws Exception {
		return getInstance().recuperaUsuario(idUsuario);
	}
	
	@Override
	public UsuarioBpmDTO recuperaUsuario(String login) throws Exception {
		return getInstance().recuperaUsuario(login);
	}

	@Override
	public GrupoBpmDTO recuperaGrupo(String siglaGrupo) throws Exception {
		return getInstance().recuperaGrupo(siglaGrupo);
	}

	@Override
	public boolean existeUsuario(String login) throws Exception {
		return getInstance().existeUsuario(login);
	}

	@Override
	public boolean existeGrupo(String siglaGrupo) throws Exception {
		return getInstance().existeGrupo(siglaGrupo);
	}

	@Override
	public List<GrupoBpmDTO> getGruposDoUsuario(String login) throws Exception {
		return getInstance().getGruposDoUsuario(login);
	}

	@Override
	public GrupoBpmDTO recuperaGrupo(Integer idGrupo) throws Exception {
		return getInstance().recuperaGrupo(idGrupo);
	}

	@Override
	public boolean pertenceAoGrupo(String login, String siglaGrupo) throws Exception {
		return getInstance().pertenceAoGrupo(login, siglaGrupo);
	}

	@Override
	public void setTransacao(TransactionControler transacao) throws Exception {
		this.transacao = transacao;
		getInstance().setTransacao(this.transacao);
	}
}
