package br.com.centralit.bpm.negocio;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.centralit.bpm.dto.AtribuicaoFluxoDTO;
import br.com.centralit.bpm.dto.ElementoFluxoTarefaDTO;
import br.com.centralit.bpm.dto.GrupoBpmDTO;
import br.com.centralit.bpm.dto.ItemTrabalhoFluxoDTO;
import br.com.centralit.bpm.dto.ObjetoInstanciaFluxoDTO;
import br.com.centralit.bpm.dto.ObjetoNegocioFluxoDTO;
import br.com.centralit.bpm.dto.TarefaFluxoDTO;
import br.com.centralit.bpm.dto.UsuarioBpmDTO;
import br.com.centralit.bpm.integracao.AtribuicaoFluxoDao;
import br.com.centralit.bpm.integracao.ItemTrabalhoFluxoDao;
import br.com.centralit.bpm.integracao.ObjetoInstanciaFluxoDao;
import br.com.centralit.bpm.integracao.TarefaFluxoDao;
import br.com.centralit.bpm.util.Enumerados;
import br.com.centralit.bpm.util.Enumerados.AcaoItemTrabalho;
import br.com.centralit.bpm.util.Enumerados.SituacaoItemTrabalho;
import br.com.centralit.bpm.util.Enumerados.TipoAtribuicao;
import br.com.centralit.bpm.util.UtilScript;
import br.com.citframework.util.UtilDatas;

public class Tarefa extends ItemTrabalho {

    private void executaAcaoEntrada(Map<String, Object> objetos) throws Exception {
        if (elementoFluxoDto.getAcaoEntrada() == null || elementoFluxoDto.getAcaoEntrada().trim().length() == 0)
            return;

        if (objetos == null)
            objetos = new HashMap<>();

        this.instanciaFluxo.getExecucaoFluxo().mapObjetoNegocio(objetos);
        this.instanciaFluxo.getObjetos(objetos);

        adicionaObjeto("itemTrabalho", this, objetos);
        UtilScript.executaScript(elementoFluxoDto.getNome(), elementoFluxoDto.getAcaoEntrada(), objetos);
    }

    private void executaAcaoSaida(Map<String, Object> objetos) throws Exception {
        if (elementoFluxoDto.getAcaoSaida() == null || elementoFluxoDto.getAcaoSaida().trim().length() == 0)
            return;

        if (objetos == null)
            objetos = new HashMap<>();

        this.instanciaFluxo.getExecucaoFluxo().mapObjetoNegocio(objetos);
        this.instanciaFluxo.getObjetos(objetos);

        adicionaObjeto("itemTrabalho", this, objetos);
        UtilScript.executaScript(elementoFluxoDto.getNome(), elementoFluxoDto.getAcaoSaida(), objetos);
    }

	public List<ItemTrabalho> resolve() throws Exception {
		registra();
		return null;
	}

	@Override
	public void inicia(String loginUsuario, Map<String, Object> objetos) throws Exception {
		atualizaTarefa(loginUsuario, Enumerados.SituacaoItemTrabalho.EmAndamento, objetos);

		IUsuarioGrupo usuarioGrupo = new UsuarioGrupo();
		UsuarioBpmDTO responsavel = usuarioGrupo.recuperaUsuario(loginUsuario);
		new HistoricoItemTrabalho(this).registra(responsavel, AcaoItemTrabalho.Iniciar);
	}

	@Override
	public void executa(String loginUsuario, Map<String, Object> objetos) throws Exception {
		if (resolvido())
			return;

		atualizaTarefa(loginUsuario, Enumerados.SituacaoItemTrabalho.Executado, objetos);
		IUsuarioGrupo usuarioGrupo = new UsuarioGrupo();
		UsuarioBpmDTO responsavel = usuarioGrupo.recuperaUsuario(loginUsuario);
		new HistoricoItemTrabalho(this).registra(responsavel, AcaoItemTrabalho.Executar);

		executaAcaoSaida(objetos);
	}

    @Override
	public void delega(String loginUsuario, String usuarioDestino, String grupoDestino) throws Exception {
		boolean bDelegacaoUsuario = usuarioDestino != null && usuarioDestino.trim().length() > 0;
		boolean bDelegacaoGrupo = grupoDestino != null && grupoDestino.trim().length() > 0;

		if (bDelegacaoUsuario || bDelegacaoGrupo) {
			IUsuarioGrupo usuarioGrupo = new UsuarioGrupo();
			UsuarioBpmDTO responsavel = usuarioGrupo.recuperaUsuario(loginUsuario);
			UsuarioBpmDTO usuario = null;
			GrupoBpmDTO grupo = null;

			if (bDelegacaoUsuario)
				usuario = usuarioGrupo.recuperaUsuario(usuarioDestino);
			else
				grupo = usuarioGrupo.recuperaGrupo(grupoDestino);

			new AtribuicaoFluxo(this).registraDelegacao(responsavel, usuario, grupo);

			TarefaFluxoDao tarefaFluxoDao = new TarefaFluxoDao();
			setTransacaoDao(tarefaFluxoDao);
			/*
			 * Removido: o responsável atual continua o mesmo
			 */
			//itemTrabalhoDto.setIdResponsavelAtual(responsavel.getIdUsuario());
			itemTrabalhoDto.setSituacao(SituacaoItemTrabalho.Disponivel.name());
			tarefaFluxoDao.updateNotNull(itemTrabalhoDto);
		}
	}

	private void atualizaTarefa(String loginUsuario, SituacaoItemTrabalho situacao, Map<String, Object> objetos) throws Exception {
		TarefaFluxoDao tarefaFluxoDao = new TarefaFluxoDao();
		setTransacaoDao(tarefaFluxoDao);

		IUsuarioGrupo usuarioGrupo = new UsuarioGrupo();
		UsuarioBpmDTO usuarioDto = usuarioGrupo.recuperaUsuario(loginUsuario);
		itemTrabalhoDto.setSituacao(situacao.name());
		itemTrabalhoDto.setIdResponsavelAtual(usuarioDto.getIdUsuario());
		if (itemTrabalhoDto.getDataHoraInicio() == null)
			itemTrabalhoDto.setDataHoraInicio(UtilDatas.getDataHoraAtual());
		if (situacao.name().equals(Enumerados.SituacaoItemTrabalho.Executado.name()))
			itemTrabalhoDto.setDataHoraFinalizacao(UtilDatas.getDataHoraAtual());
		tarefaFluxoDao.update(itemTrabalhoDto);

		if (objetos != null) {
			ObjetoInstanciaFluxoDao objetoInstanciaFluxoDao = new ObjetoInstanciaFluxoDao();
			setTransacaoDao(objetoInstanciaFluxoDao);

			for(String key: objetos.keySet()) {
				Object objeto = objetos.get(key);
				if (!ObjetoInstanciaFluxoDTO.class.isInstance(objeto))
					continue;

				objetoInstanciaFluxoDao.deleteByIdInstanciaAndNome(instanciaFluxo.getIdInstancia(), key);
				ObjetoInstanciaFluxoDTO campoInstanciaDto = (ObjetoInstanciaFluxoDTO) objeto;
				campoInstanciaDto.setIdObjetoInstancia(null);
				campoInstanciaDto.setIdItemTrabalho(itemTrabalhoDto.getIdItemTrabalho());
				campoInstanciaDto.setTipoAssociacao(Enumerados.TIPO_ASSOCIACAO_TAREFA);
				campoInstanciaDto.setIdInstancia(itemTrabalhoDto.getIdInstancia());
				campoInstanciaDto = (ObjetoInstanciaFluxoDTO) objetoInstanciaFluxoDao.create(campoInstanciaDto);
			}
		}
	}

	@Override
	public void cancela(String loginUsuario) throws Exception {
		TarefaFluxoDao tarefaFluxoDao = new TarefaFluxoDao();
		setTransacaoDao(tarefaFluxoDao);

		itemTrabalhoDto.setSituacao(SituacaoItemTrabalho.Cancelado.name());
		itemTrabalhoDto.setDataHoraFinalizacao(UtilDatas.getDataHoraAtual());
		tarefaFluxoDao.update(itemTrabalhoDto);

		if (loginUsuario != null) {
            IUsuarioGrupo usuarioGrupo = new UsuarioGrupo();
            UsuarioBpmDTO usuarioDto = usuarioGrupo.recuperaUsuario(loginUsuario);
    	    new HistoricoItemTrabalho(this).registra(usuarioDto, AcaoItemTrabalho.Cancelar);
		}
	}

	public void registra(String[] grupos, String[] usuarios) throws Exception {
		TarefaFluxoDao tarefaFluxoDao = new TarefaFluxoDao();
		setTransacaoDao(tarefaFluxoDao);

		TarefaFluxoDTO tarefaFluxoDto = new TarefaFluxoDTO();
		tarefaFluxoDto.setIdElemento(elementoFluxoDto.getIdElemento());
		tarefaFluxoDto.setIdInstancia(instanciaFluxo.getIdInstancia());
		tarefaFluxoDto.setDataHoraCriacao(UtilDatas.getDataHoraAtual());
		tarefaFluxoDto.setSituacao(Enumerados.SituacaoItemTrabalho.Disponivel.name());
		tarefaFluxoDto = (TarefaFluxoDTO) tarefaFluxoDao.create(tarefaFluxoDto);

		itemTrabalhoDto = tarefaFluxoDto;

		atribui(grupos, usuarios, TipoAtribuicao.Automatica);
		executaAcaoEntrada(instanciaFluxo.getObjetos());
	}

	@Override
	public void registra() throws Exception {
		if (existe())
			return;

		TarefaFluxoDao tarefaFluxoDao = new TarefaFluxoDao();
		setTransacaoDao(tarefaFluxoDao);

		Collection<ItemTrabalhoFluxoDTO> colItens = tarefaFluxoDao.findByIdInstanciaAndIdElemento(instanciaFluxo.getIdInstancia(), elementoFluxoDto.getIdElemento());
		if (colItens != null) {
			for (ItemTrabalhoFluxoDTO itemDto : colItens) {
			    ItemTrabalho itemTrabalho = ItemTrabalho.getItemTrabalho(this.instanciaFluxo,itemDto.getIdItemTrabalho());
				if (itemTrabalho.pendente())
					return;
			}
		}

		String[] grupos = substituiParametros(((ElementoFluxoTarefaDTO) elementoFluxoDto).getColGrupos(), instanciaFluxo.getObjetos(), ";");
		String[] usuarios = substituiParametros(((ElementoFluxoTarefaDTO) elementoFluxoDto).getColUsuarios(), instanciaFluxo.getObjetos(), ";");
        if (elementoFluxoDto.getMultiplasInstancias() != null && elementoFluxoDto.getMultiplasInstancias().equals("U")) {
        	for (String usuario : usuarios) {
        		String[] usrAtribuicao = new String[] {usuario};
        		Tarefa tarefa = (Tarefa) ItemTrabalho.getItemTrabalhoDeElemento(instanciaFluxo,elementoFluxoDto.getIdElemento());
        		tarefa.registra(grupos, usrAtribuicao);
			}
        }else{
        	registra(grupos, usuarios);
        }

		instanciaFluxo.verificaSLA(this);
	}

    @Override
	public void redireciona(String loginUsuario, String usuarioDestino, String grupoDestino) throws Exception {
		AtribuicaoFluxoDao atribuicaoFluxoDao = new AtribuicaoFluxoDao();
		setTransacaoDao(atribuicaoFluxoDao);
		Collection<AtribuicaoFluxoDTO> colAtribuicao = atribuicaoFluxoDao.findByDisponiveisByIdItemTrabalho(itemTrabalhoDto.getIdItemTrabalho());
		if (colAtribuicao != null) {
			for (AtribuicaoFluxoDTO atribuicaoFluxoDto : colAtribuicao) {
				atribuicaoFluxoDao.delete(atribuicaoFluxoDto);
			}
		}

		AtribuicaoFluxo atribuicaoFluxo = new AtribuicaoFluxo(this);
		IUsuarioGrupo usuarioGrupo = new UsuarioGrupo();
		if (usuarioDestino != null) {
			UsuarioBpmDTO usuarioDto = usuarioGrupo.recuperaUsuario(usuarioDestino);
			if (usuarioDto != null)
				atribuicaoFluxo.registraAtribuicao(usuarioDto, null, TipoAtribuicao.Automatica);
		}else{
			GrupoBpmDTO grupoDto = usuarioGrupo.recuperaGrupo(grupoDestino);
			if (grupoDto != null)
				atribuicaoFluxo.registraAtribuicao(null, grupoDto, TipoAtribuicao.Automatica);
		}

		boolean bAtribuidoAoUsuario = loginUsuario != null && usuarioDestino != null && loginUsuario.equalsIgnoreCase(usuarioDestino);
		boolean bPertenceAoGrupo = usuarioGrupo.pertenceAoGrupo(loginUsuario, grupoDestino);

		TarefaFluxoDao tarefaFluxoDao = new TarefaFluxoDao();
		setTransacaoDao(tarefaFluxoDao);

		if (bAtribuidoAoUsuario || bPertenceAoGrupo) {
			UsuarioBpmDTO usuarioDto = usuarioGrupo.recuperaUsuario(loginUsuario);
			itemTrabalhoDto.setIdResponsavelAtual(usuarioDto.getIdUsuario());
		}else{
			itemTrabalhoDto.setIdResponsavelAtual(null);
		}
		tarefaFluxoDao.update(itemTrabalhoDto);

		Map<String, GrupoBpmDTO> mapGrupos = new HashMap<>();
		Map<String, UsuarioBpmDTO> mapUsuarios = new HashMap<>();
		atribuiAcompanhamento(mapGrupos, mapUsuarios);
	}

	public void atribui(String[] grupos, String[] usuarios, TipoAtribuicao tipoAtribuicao) throws Exception {
		Map<String, GrupoBpmDTO> mapGrupos = new HashMap<>();
		Map<String, UsuarioBpmDTO> mapUsuarios = new HashMap<>();

		AtribuicaoFluxo atribuicaoFluxo = new AtribuicaoFluxo(this);
		IUsuarioGrupo usuarioGrupo = new UsuarioGrupo();

		if (grupos != null) {
			for (String grupo : grupos) {
				GrupoBpmDTO grupoDto = usuarioGrupo.recuperaGrupo(grupo);
				if (grupoDto != null) {
					atribuicaoFluxo.registraAtribuicao(null, grupoDto, tipoAtribuicao);
					mapGrupos.put(""+grupoDto.getIdGrupo(), grupoDto);
				}
			}
		}

		if (usuarios != null) {
			for (String usuario : usuarios) {
				UsuarioBpmDTO usuarioDto = usuarioGrupo.recuperaUsuario(usuario);
				if (usuarioDto != null) {
					boolean bAtribui = true;
					Collection<GrupoBpmDTO> colGrupos = usuarioGrupo.getGruposDoUsuario(usuario);
					if (colGrupos != null) {
						for (GrupoBpmDTO grupoBpmDto : colGrupos) {
							if (mapGrupos.get(""+grupoBpmDto.getIdGrupo()) != null) {
								bAtribui = false;
								break;
							}
						}
					}
					if (bAtribui) {
						atribuicaoFluxo.registraAtribuicao(usuarioDto, null, tipoAtribuicao);
						mapUsuarios.put(""+usuarioDto.getIdUsuario(), usuarioDto);
					}
				}
			}
		}

		if (!tipoAtribuicao.equals(TipoAtribuicao.Acompanhamento))
			atribuiAcompanhamento(mapGrupos, mapUsuarios);
	}

	public void atribuiAcompanhamento(Map<String, GrupoBpmDTO> mapGrupos, Map<String, UsuarioBpmDTO> mapUsuarios) throws Exception {
		AtribuicaoFluxo atribuicaoFluxo = new AtribuicaoFluxo(this);
		IUsuarioGrupo usuarioGrupo = new UsuarioGrupo();
		ObjetoNegocioFluxoDTO objetoNegocioDto = instanciaFluxo.getExecucaoFluxo().getObjetoNegocioDto();
		if (objetoNegocioDto != null) {
			if (objetoNegocioDto.getIdGrupoNivel1() != null && mapGrupos.get(""+objetoNegocioDto.getIdGrupoNivel1()) == null) {
				GrupoBpmDTO grupoDto = usuarioGrupo.recuperaGrupo(objetoNegocioDto.getIdGrupoNivel1());
				if (grupoDto != null){
				    atribuicaoFluxo.registraAtribuicao(null, grupoDto, TipoAtribuicao.Acompanhamento);
				    mapGrupos.put(""+grupoDto.getIdGrupo(), grupoDto);
				}
			}
			if (objetoNegocioDto.getIdResponsavel() != null && mapUsuarios.get(""+objetoNegocioDto.getIdResponsavel()) == null) {
				UsuarioBpmDTO usuarioDto = usuarioGrupo.recuperaUsuario(objetoNegocioDto.getIdResponsavel());
				boolean bAtribui = true;
				Collection<GrupoBpmDTO> colGrupos = usuarioGrupo.getGruposDoUsuario(usuarioDto.getLogin());
				if (colGrupos != null) {
					for (GrupoBpmDTO grupoBpmDto : colGrupos) {
						if (mapGrupos.get(""+grupoBpmDto.getIdGrupo()) != null) {
							bAtribui = false;
							break;
						}
					}
				}
				if (bAtribui)
					atribuicaoFluxo.registraAtribuicao(usuarioDto, null, TipoAtribuicao.Acompanhamento);
			}
		}
	}

    @Override
    public void atribui(String usuario, String grupo, TipoAtribuicao tipoAtribuicao) throws Exception {
    	String[] usuarios = null;
    	if (usuario != null)
    		usuarios = usuario.split(";");

    	String[] grupos = null;
    	if (grupo != null)
    		grupos = grupo.split(";");

    	if (usuarios == null && grupos == null)
    		return;

    	atribui(grupos, usuarios, tipoAtribuicao);
    }

	public String getTipoInteracao() {
		return elementoFluxoDto.getTipoInteracao();
	}

	public String getUrl() {
		return elementoFluxoDto.getUrl();
	}

	public String getVisao() {
		return elementoFluxoDto.getVisao();
	}

	@Override
	public boolean finalizado() throws Exception {
		if (getSituacao() == null)
			return false;

		boolean result = false;
		if (resolvido()) {
			result = true;
			if (elementoFluxoDto.getMultiplasInstancias() != null && elementoFluxoDto.getMultiplasInstancias().equalsIgnoreCase("U")) {
				ItemTrabalhoFluxoDao itemTrabalhoDao = new ItemTrabalhoFluxoDao();
				setTransacaoDao(itemTrabalhoDao);
				Collection<ItemTrabalhoFluxoDTO> colItens = itemTrabalhoDao.findByIdInstanciaAndIdElemento(instanciaFluxo.getIdInstancia(), itemTrabalhoDto.getIdElemento());
				if (colItens != null) {
					for (ItemTrabalhoFluxoDTO itemTrabalhoAuxDto : colItens) {
						if (itemTrabalhoAuxDto.getIdItemTrabalho().intValue() == itemTrabalhoDto.getIdItemTrabalho().intValue())
							continue;
						if (!itemTrabalhoAuxDto.getSituacao().trim().equalsIgnoreCase(Enumerados.SituacaoItemTrabalho.Executado.name()) && !itemTrabalhoAuxDto.getSituacao().trim().equalsIgnoreCase(Enumerados.SituacaoItemTrabalho.Cancelado.name())) {
							result = false;
							break;
						}
					}
				}
			}
		}
		return result;
	}

	@Override
	public boolean executavel() {
		return true;
	}

}
