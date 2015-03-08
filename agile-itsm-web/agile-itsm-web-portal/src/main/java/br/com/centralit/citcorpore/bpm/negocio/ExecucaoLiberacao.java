package br.com.centralit.citcorpore.bpm.negocio;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.centralit.bpm.dto.EventoFluxoDTO;
import br.com.centralit.bpm.dto.FluxoDTO;
import br.com.centralit.bpm.dto.InstanciaFluxoDTO;
import br.com.centralit.bpm.dto.ItemTrabalhoFluxoDTO;
import br.com.centralit.bpm.dto.ObjetoNegocioFluxoDTO;
import br.com.centralit.bpm.dto.TarefaFluxoDTO;
import br.com.centralit.bpm.dto.TipoFluxoDTO;
import br.com.centralit.bpm.integracao.FluxoDao;
import br.com.centralit.bpm.integracao.ItemTrabalhoFluxoDao;
import br.com.centralit.bpm.integracao.TipoFluxoDao;
import br.com.centralit.bpm.negocio.ExecucaoFluxo;
import br.com.centralit.bpm.negocio.InstanciaFluxo;
import br.com.centralit.bpm.negocio.ItemTrabalho;
import br.com.centralit.bpm.util.Enumerados;
import br.com.centralit.citcorpore.bean.CalculoJornadaDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.ExecucaoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.ModeloEmailDTO;
import br.com.centralit.citcorpore.bean.OcorrenciaLiberacaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.TipoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.ExecucaoLiberacaoDao;
import br.com.centralit.citcorpore.integracao.GrupoDao;
import br.com.centralit.citcorpore.integracao.ModeloEmailDao;
import br.com.centralit.citcorpore.integracao.OcorrenciaLiberacaoDao;
import br.com.centralit.citcorpore.integracao.RequisicaoLiberacaoDao;
import br.com.centralit.citcorpore.integracao.TipoLiberacaoDAO;
import br.com.centralit.citcorpore.integracao.UsuarioDao;
import br.com.centralit.citcorpore.mail.MensagemEmail;
import br.com.centralit.citcorpore.negocio.CalendarioServiceEjb;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.ItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.RequisicaoLiberacaoItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.RequisicaoLiberacaoServiceEjb;
import br.com.centralit.citcorpore.util.Enumerados.FaseRequisicaoLiberacao;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoRequisicaoLiberacao;
import br.com.centralit.citcorpore.util.Enumerados.StatusIC;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.Util;
import br.com.citframework.comparacao.ObjectSimpleComparator;
import br.com.citframework.dto.IDto;
import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;

import com.google.gson.Gson;
@SuppressWarnings({"unchecked","rawtypes","unused"})
public class ExecucaoLiberacao extends ExecucaoFluxo {
	

	private UsuarioDTO usuarioDto = null;
	
	public ExecucaoLiberacao(TransactionControler tc) {
		super(tc);
	}

	public ExecucaoLiberacao() {
		super();
	}

	public ExecucaoLiberacao(RequisicaoLiberacaoDTO requisicaoLiberacaoDto, TransactionControler tc) {
		super(requisicaoLiberacaoDto, tc);
	}
	
	public ExecucaoLiberacao(RequisicaoLiberacaoDTO requisicaoLiberacaoDto, TransactionControler tc, Usuario usuario) {
		super(requisicaoLiberacaoDto, tc, usuario);
	}

	@Override
	public InstanciaFluxo inicia(String nomeFluxo, Integer idFase) throws Exception {
		TipoFluxoDao tipoFluxoDao = new TipoFluxoDao();
		TipoFluxoDTO tipoFluxoDto = tipoFluxoDao.findByNome(nomeFluxo);
		if (tipoFluxoDto == null){
			System.out.println("Fluxo "+nomeFluxo+" não existe");
			throw new LogicException(i18n_Message("citcorpore.comum.fluxoNaoEncontrado"));
		}
		return inicia(new FluxoDao().findByTipoFluxo(tipoFluxoDto.getIdTipoFluxo()), idFase);
	}

	@Override
	public InstanciaFluxo inicia() throws Exception {
		if (getRequisicaoLiberacaoDto().getIdTipoLiberacao() == null)
			throw new LogicException(i18n_Message("requisicaoLiberacao.tipoNaoDefinido"));
		
		InstanciaFluxo result = null;
		InstanciaFluxoDTO instanciaFluxoDto = null;
		
		TipoLiberacaoDTO tipoLiberacaoDto = recuperaTipoLiberacao();
		if (tipoLiberacaoDto.getIdTipoFluxo() != null){ 
			result = inicia(new FluxoDao().findByTipoFluxo(tipoLiberacaoDto.getIdTipoFluxo()), null);
		}else{
			String fluxoPadrao = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.FLUXO_PADRAO_MUDANCAS, null);
			if (fluxoPadrao == null)
				throw new LogicException(i18n_Message("citcorpore.comum.fluxoNaoParametrizado"));
			result = inicia(fluxoPadrao, null);
		}
		
		try {
			String enviarNotificacao = ParametroUtil.getValor(ParametroSistema.NOTIFICAR_GRUPO_RECEPCAO_SOLICITACAO, getTransacao(), "N");
			String IdModeloEmailGrupoDestinoREquisicaoLiberacao = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_MODELO_EMAIL_GRUPO_DESTINO_REQUISICAOMUDANCA, "30");
			if (enviarNotificacao.equalsIgnoreCase("S") && getRequisicaoLiberacaoDto().escalada()) {
				enviaEmailGrupo(Integer.parseInt(IdModeloEmailGrupoDestinoREquisicaoLiberacao.trim()),tipoLiberacaoDto.getIdGrupoExecutor());
			}
		} catch (NumberFormatException e) {
			System.out.println(i18n_Message("requisicaoLiberacao.emailNaoDefinido"));
		}
		
		return result;
	}

	
	@Override
	public InstanciaFluxo inicia(FluxoDTO fluxoDto, Integer idFase) throws Exception {
		if (fluxoDto == null)
			throw new LogicException(i18n_Message("citcorpore.comum.fluxoNaoEncontrado"));
		
		this.fluxoDto = fluxoDto;
		
		HashMap<String, Object> map = new HashMap();
		mapObjetoNegocio(map);
		InstanciaFluxo instanciaFluxo = new InstanciaFluxo(this,map);
		
		ExecucaoLiberacaoDTO execucaoDto = new ExecucaoLiberacaoDTO();
		execucaoDto.setIdRequisicaoLiberacao(getRequisicaoLiberacaoDto().getIdRequisicaoLiberacao());
		execucaoDto.setIdFluxo(instanciaFluxo.getIdFluxo());
		execucaoDto.setIdInstanciaFluxo(instanciaFluxo.getIdInstancia());
		Integer seqReabertura = 0;
		if (getRequisicaoLiberacaoDto().getSeqReabertura() != null && getRequisicaoLiberacaoDto().getSeqReabertura().intValue() > 0)
			seqReabertura = getRequisicaoLiberacaoDto().getSeqReabertura();
		if (seqReabertura.intValue() > 0)
			execucaoDto.setSeqReabertura(getRequisicaoLiberacaoDto().getSeqReabertura());
		
		ExecucaoLiberacaoDao execucaoDao = new ExecucaoLiberacaoDao();
		setTransacaoDao(execucaoDao);
		execucaoFluxoDto = (ExecucaoLiberacaoDTO) execucaoDao.create(execucaoDto);
		
		if (seqReabertura.intValue() == 0 && getRequisicaoLiberacaoDto().getEnviaEmailCriacao() != null && getRequisicaoLiberacaoDto().getEnviaEmailCriacao().equalsIgnoreCase("S")) {
			TipoLiberacaoDTO tipoLiberacaoDto = recuperaTipoLiberacao();
			enviaEmail(tipoLiberacaoDto.getIdModeloEmailCriacao());
		}
		return instanciaFluxo;
	}
	
	@Override
	public void executa(String loginUsuario, ObjetoNegocioFluxoDTO objetoNegocioDto, Integer idItemTrabalho, String acao, HashMap<String, Object> map) throws Exception {
		if (acao.equals(Enumerados.ACAO_DELEGAR))
			return;

		TarefaFluxoDTO tarefaFluxoDto = recuperaTarefa(idItemTrabalho);
		if (tarefaFluxoDto == null)
			return;

		ExecucaoLiberacaoDao execucaoLiberacaoDao = new ExecucaoLiberacaoDao();
		setTransacaoDao(execucaoLiberacaoDao);
		ExecucaoLiberacaoDTO execucaoLiberacaoDto = execucaoLiberacaoDao.findByIdInstanciaFluxo(tarefaFluxoDto.getIdInstancia());
		if (execucaoLiberacaoDto == null) 
			return;
		
		recuperaFluxo(execucaoLiberacaoDto.getIdFluxo());
		
		this.objetoNegocioDto = objetoNegocioDto;
		usuarioDto = new UsuarioDao().restoreByLogin(loginUsuario);

		InstanciaFluxo instanciaFluxo = new InstanciaFluxo(this, tarefaFluxoDto.getIdInstancia());
		mapObjetoNegocio(instanciaFluxo.getObjetos(map));
		
		if (acao.equals(Enumerados.ACAO_INICIAR)) 
			instanciaFluxo.iniciaItemTrabalho(loginUsuario, tarefaFluxoDto.getIdItemTrabalho(), map);
		else if (acao.equals(Enumerados.ACAO_EXECUTAR)) {
			tarefaFluxoDto = recuperaTarefa(idItemTrabalho);
			OcorrenciaLiberacaoDao ocorrenciaLiberacaoDao = new OcorrenciaLiberacaoDao();
			setTransacaoDao(ocorrenciaLiberacaoDao);
			OcorrenciaLiberacaoDTO ocorrenciaLiberacaoDto = new OcorrenciaLiberacaoDTO();
			ocorrenciaLiberacaoDto.setIdRequisicaoLiberacao(getRequisicaoLiberacaoDto().getIdRequisicaoLiberacao());
			ocorrenciaLiberacaoDto.setDataregistro(UtilDatas.getDataAtual());
			ocorrenciaLiberacaoDto.setHoraregistro(UtilDatas.formatHoraFormatadaStr(UtilDatas.getHoraAtual()));
			Long tempo = new Long(0);
			if (tarefaFluxoDto.getDataHoraFinalizacao() != null)
				tempo = (tarefaFluxoDto.getDataHoraFinalizacao().getTime() - tarefaFluxoDto.getDataHoraCriacao().getTime()) / 1000 / 60;
			ocorrenciaLiberacaoDto.setTempoGasto(tempo.intValue());
			ocorrenciaLiberacaoDto.setDescricao(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Execucao.getDescricao());
			ocorrenciaLiberacaoDto.setDataInicio(UtilDatas.getDataAtual());
			ocorrenciaLiberacaoDto.setDataFim(UtilDatas.getDataAtual());
			ocorrenciaLiberacaoDto.setInformacoesContato("não se aplica");
			ocorrenciaLiberacaoDto.setRegistradopor(loginUsuario);
			ocorrenciaLiberacaoDto.setDadosLiberacao(new Gson().toJson(getRequisicaoLiberacaoDto()));
			ocorrenciaLiberacaoDto.setOcorrencia("Execução da tarefa \""+tarefaFluxoDto.getElementoFluxoDto().getDocumentacao()+"\"");
			ocorrenciaLiberacaoDto.setOrigem(br.com.centralit.citcorpore.util.Enumerados.OrigemOcorrencia.OUTROS.getSigla().toString());
			ocorrenciaLiberacaoDto.setCategoria(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Execucao.getSigla());
			ocorrenciaLiberacaoDto.setIdItemTrabalho(idItemTrabalho);
			ocorrenciaLiberacaoDao.create(ocorrenciaLiberacaoDto);			    

			instanciaFluxo.executaItemTrabalho(loginUsuario, tarefaFluxoDto.getIdItemTrabalho(), map);
			
			if(getRequisicaoLiberacaoDto().getStatus().equalsIgnoreCase(br.com.centralit.citcorpore.util.Enumerados.SituacaoRequisicaoLiberacao.NaoResolvida.name())){
				GrupoDao grupoDao = new GrupoDao();
				 List<GrupoDTO> listGrupo  =(List<GrupoDTO>) grupoDao.getGruposByIdEmpregado(getRequisicaoLiberacaoDto().getIdSolicitante());
				 GrupoDTO grupoDto = listGrupo.get(0);
			}
			
			if (getRequisicaoLiberacaoDto().getFase() != null && getRequisicaoLiberacaoDto().getFase().trim().length() > 0) {
				SituacaoRequisicaoLiberacao novaSituacao = FaseRequisicaoLiberacao.valueOf(getRequisicaoLiberacaoDto().getFase()).getSituacao();
				if (novaSituacao != null) {
					RequisicaoLiberacaoDao requisicaoDao = new RequisicaoLiberacaoDao();
					setTransacaoDao(requisicaoDao);
					getRequisicaoLiberacaoDto().setStatus(novaSituacao.name());
					requisicaoDao.updateNotNull(getRequisicaoLiberacaoDto());
				}
			}
		}

		if(tarefaFluxoDto.getElementoFluxoDto().getDocumentacao().equals("Liberado")){
			RequisicaoLiberacaoDao requisicaoDao = new RequisicaoLiberacaoDao();
			setTransacaoDao(requisicaoDao);
			getRequisicaoLiberacaoDto().setIdAprovador(usuarioDto.getIdUsuario());
			getRequisicaoLiberacaoDto().setDatahoraAprovacao(UtilDatas.getDataHoraAtual());
			requisicaoDao.updateNotNull(getRequisicaoLiberacaoDto());
		}
		
		if (getRequisicaoLiberacaoDto().getEnviaEmailAcoes() != null && getRequisicaoLiberacaoDto().getEnviaEmailAcoes().equalsIgnoreCase("S")) {
			getRequisicaoLiberacaoDto().setNomeTarefa(tarefaFluxoDto.getElementoFluxoDto().getDocumentacao());
			TipoLiberacaoDTO tipoLiberacaoDto = recuperaTipoLiberacao();
			enviaEmail(tipoLiberacaoDto.getIdModeloEmailAcoes());
		}
		
		/*if(getRequisicaoLiberacaoDto().getStatus().equalsIgnoreCase("Concluida")){
			this.finalizaIC(getRequisicaoLiberacaoDto());
		}*/
	}


	@Override
	public void delega(String loginUsuario, ObjetoNegocioFluxoDTO objetoNegocioDto, Integer idItemTrabalho, String usuarioDestino, String grupoDestino) throws Exception {
		TarefaFluxoDTO tarefaFluxoDto = recuperaTarefa(idItemTrabalho);
		if (tarefaFluxoDto == null)
			return;

		InstanciaFluxo instanciaFluxo = new InstanciaFluxo(this, tarefaFluxoDto.getIdInstancia());
		instanciaFluxo.delegaItemTrabalho(loginUsuario, idItemTrabalho, usuarioDestino, grupoDestino);
		
		this.objetoNegocioDto = objetoNegocioDto;

		OcorrenciaLiberacaoDao ocorrenciaLiberacaoDao = new OcorrenciaLiberacaoDao();
		setTransacaoDao(ocorrenciaLiberacaoDao);
		OcorrenciaLiberacaoDTO ocorrenciaLiberacaoDto = new OcorrenciaLiberacaoDTO();
		ocorrenciaLiberacaoDto.setIdRequisicaoLiberacao(getRequisicaoLiberacaoDto().getIdRequisicaoLiberacao());
		ocorrenciaLiberacaoDto.setDataregistro(UtilDatas.getDataAtual());
		ocorrenciaLiberacaoDto.setHoraregistro(UtilDatas.formatHoraFormatadaStr(UtilDatas.getHoraAtual()));
		ocorrenciaLiberacaoDto.setTempoGasto(0);
		ocorrenciaLiberacaoDto.setDescricao(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Compartilhamento.getDescricao());
		ocorrenciaLiberacaoDto.setDataInicio(UtilDatas.getDataAtual());
		ocorrenciaLiberacaoDto.setDataFim(UtilDatas.getDataAtual());
		ocorrenciaLiberacaoDto.setInformacoesContato("não se aplica");
		ocorrenciaLiberacaoDto.setRegistradopor(loginUsuario);
		ocorrenciaLiberacaoDto.setDadosLiberacao(new Gson().toJson(getRequisicaoLiberacaoDto()));
		String ocorr = "Compartilhamento da tarefa \""+tarefaFluxoDto.getElementoFluxoDto().getDocumentacao()+"\"";
		if (usuarioDestino != null)
			ocorr += " com o usuário "+usuarioDestino;
		if (grupoDestino != null)
			ocorr += " com o grupo "+grupoDestino;
		ocorrenciaLiberacaoDto.setOcorrencia(ocorr);
		ocorrenciaLiberacaoDto.setOrigem(br.com.centralit.citcorpore.util.Enumerados.OrigemOcorrencia.OUTROS.getSigla().toString());
		ocorrenciaLiberacaoDto.setCategoria(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Compartilhamento.getSigla());
		ocorrenciaLiberacaoDto.setIdItemTrabalho(idItemTrabalho);
		ocorrenciaLiberacaoDao.create(ocorrenciaLiberacaoDto);			 
	}
	

	@Override
	public void direcionaAtendimento(String loginUsuario, ObjetoNegocioFluxoDTO objetoNegocioDto, String grupoAtendimento) throws Exception {
		if (getRequisicaoLiberacaoDto() == null)
			return;

		if (grupoAtendimento == null)
			return;

		GrupoDao grupoDao  = new GrupoDao();
		GrupoDTO grupoAtendimentoDto = grupoDao.restoreBySigla(grupoAtendimento);
		if (grupoAtendimentoDto == null)
			return;
		
		UsuarioDao usuarioDao = new UsuarioDao();
		setTransacaoDao(usuarioDao);
		UsuarioDTO usuarioRespDto = new UsuarioDTO();
		usuarioRespDto.setIdUsuario(getRequisicaoLiberacaoDto().getIdResponsavel());
		usuarioRespDto = (UsuarioDTO) usuarioDao.restore(usuarioRespDto);

		this.objetoNegocioDto = objetoNegocioDto;

		ExecucaoLiberacaoDao execucaoLiberacaoDao = new ExecucaoLiberacaoDao();
		setTransacaoDao(execucaoLiberacaoDao);
		
		Collection<ExecucaoLiberacaoDTO> colExecucao = execucaoLiberacaoDao.listByIdRequisicaoLiberacao(getRequisicaoLiberacaoDto().getIdRequisicaoLiberacao());
		if (colExecucao != null) {
			ItemTrabalhoFluxoDao itemTrabalhoFluxoDao = new ItemTrabalhoFluxoDao();
			setTransacaoDao(itemTrabalhoFluxoDao);
			OcorrenciaLiberacaoDao ocorrenciaLiberacaoDao = new OcorrenciaLiberacaoDao();
			setTransacaoDao(ocorrenciaLiberacaoDao);
			for (ExecucaoLiberacaoDTO execucaoLiberacaoDto : colExecucao) {
				InstanciaFluxo instanciaFluxo = new InstanciaFluxo(this, execucaoLiberacaoDto.getIdInstanciaFluxo());
				Collection<ItemTrabalhoFluxoDTO> colItens = itemTrabalhoFluxoDao.findDisponiveisByIdInstancia(execucaoLiberacaoDto.getIdInstanciaFluxo());
				if (colItens != null) {
					for (ItemTrabalhoFluxoDTO itemTrabalhoFluxoDto : colItens) {
						ItemTrabalho itemTrabalho = ItemTrabalho.getItemTrabalho(instanciaFluxo, itemTrabalhoFluxoDto.getIdItemTrabalho());
						itemTrabalho.redireciona(loginUsuario, null, grupoAtendimento);

						usuarioDto = usuarioDao.restoreByLogin(loginUsuario);
						
/*						TarefaFluxoDTO tarefaFluxoDto = recuperaTarefa(atribuicaoFluxoDto.getIdItemTrabalho());*/
						OcorrenciaLiberacaoDTO ocorrenciaLiberacaoDto = new OcorrenciaLiberacaoDTO();
						ocorrenciaLiberacaoDto.setIdRequisicaoLiberacao(getRequisicaoLiberacaoDto().getIdRequisicaoLiberacao());
						ocorrenciaLiberacaoDto.setDataregistro(UtilDatas.getDataAtual());
						ocorrenciaLiberacaoDto.setHoraregistro(UtilDatas.formatHoraFormatadaStr(UtilDatas.getHoraAtual()));
						ocorrenciaLiberacaoDto.setTempoGasto(0);
						ocorrenciaLiberacaoDto.setDescricao(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Direcionamento.getDescricao());
						ocorrenciaLiberacaoDto.setDataInicio(UtilDatas.getDataAtual());
						ocorrenciaLiberacaoDto.setDataFim(UtilDatas.getDataAtual());
						ocorrenciaLiberacaoDto.setInformacoesContato("não se aplica");
						ocorrenciaLiberacaoDto.setRegistradopor(loginUsuario);
						ocorrenciaLiberacaoDto.setDadosLiberacao(new Gson().toJson(getRequisicaoLiberacaoDto()));
						  String ocorr = "Direcionamento da tarefa \"" + itemTrabalho.getElementoFluxoDto().getDocumentacao() + "\"";
	                        ocorr += " para o grupo " + grupoAtendimento;
/*						ocorr += " para o grupo "+grupoAtendimento;*/
						ocorrenciaLiberacaoDto.setOcorrencia(ocorr);
						ocorrenciaLiberacaoDto.setOrigem(br.com.centralit.citcorpore.util.Enumerados.OrigemOcorrencia.OUTROS.getSigla().toString());
						ocorrenciaLiberacaoDto.setCategoria(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Direcionamento.getSigla());
					/*	ocorrenciaLiberacaoDto.setIdItemTrabalho(atribuicaoFluxoDto.getIdItemTrabalho());*/
						ocorrenciaLiberacaoDao.create(ocorrenciaLiberacaoDto);	



					}
				}
			}
		}

		try {
			String enviarNotificacao = ParametroUtil.getValor(ParametroSistema.NOTIFICAR_GRUPO_RECEPCAO_SOLICITACAO, getTransacao(), "N");
			if (enviarNotificacao.equalsIgnoreCase("S")) {
				enviaEmailGrupo(Integer.parseInt(ParametroUtil.getValor(ParametroSistema.ID_MODELO_EMAIL_GRUPO_DESTINO, getTransacao(), null)), grupoAtendimentoDto.getIdGrupo());
			}
		} catch (NumberFormatException e) {
			System.out.println(i18n_Message("requisicaoLiberacao.emailNaoDefinido"));
		}
	}
	
	
/*	@Override
	public void mapObjetoNegocio(Map<String, Object> map) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) objetoNegocioDto;
		SolicitacaoServicoDTO solicitacaoAuxDto = new SolicitacaoServicoServiceEjb().restoreAll(solicitacaoServicoDto.getIdSolicitacaoServico(), getTransacao());
		if(solicitacaoAuxDto != null){
			solicitacaoServicoDto.setGrupoAtual(solicitacaoAuxDto.getGrupoAtual());		
			solicitacaoServicoDto.setGrupoNivel1(solicitacaoAuxDto.getGrupoNivel1());
		}

		adicionaObjeto("solicitacaoServico", solicitacaoServicoDto, map);
		if (usuarioDTO != null)
			adicionaObjeto("usuario", usuarioDTO, map);
		else if (solicitacaoServicoDto.getUsuarioDto() != null)
			adicionaObjeto("usuario", solicitacaoServicoDto.getUsuarioDto(), map);

		adicionaObjeto("execucaoFluxo", this, map);
		adicionaObjeto("solicitacaoServicoService", new SolicitacaoServicoServiceEjb(), map);
	}*/
	
	@Override
	public void mapObjetoNegocio(Map<String, Object> map) throws Exception {
		RequisicaoLiberacaoDTO requisicaoLiberacaoDto = (RequisicaoLiberacaoDTO) objetoNegocioDto;
		RequisicaoLiberacaoDTO requisicaoAuxDto = new RequisicaoLiberacaoServiceEjb().restoreAll(requisicaoLiberacaoDto.getIdRequisicaoLiberacao(), getTransacao());
		requisicaoLiberacaoDto.setIdGrupoAtual(requisicaoAuxDto.getIdGrupoAtual());
		requisicaoLiberacaoDto.setIdGrupoNivel1(requisicaoAuxDto.getIdGrupoNivel1());
		requisicaoLiberacaoDto.setNomeGrupoAtual(requisicaoAuxDto.getNomeGrupoAtual());
		requisicaoLiberacaoDto.setNomeGrupoNivel1(requisicaoAuxDto.getNomeGrupoNivel1());
		UsuarioDTO usuario = usuarioSolicitante(requisicaoAuxDto);
		if( usuario != null)
			requisicaoLiberacaoDto.setUsuarioSolicitante(usuario.getLogin());
		
		adicionaObjeto("requisicaoLiberacao", requisicaoLiberacaoDto, map);
		if (usuarioDto != null)
			adicionaObjeto("usuario", usuarioDto, map);
		else if (requisicaoLiberacaoDto.getUsuarioDto() != null)   
			adicionaObjeto("usuario", requisicaoLiberacaoDto.getUsuarioDto(), map);

		adicionaObjeto("execucaoFluxo", this, map);
		adicionaObjeto("requisicaoLiberacaoService", new RequisicaoLiberacaoServiceEjb(), map);
	}
	
	public RequisicaoLiberacaoDTO getRequisicaoLiberacaoDto() {
		return (RequisicaoLiberacaoDTO) objetoNegocioDto;
	}
	
	@Override
	public List<TarefaFluxoDTO> recuperaTarefas(String loginUsuario) throws Exception {
		RequisicaoLiberacaoDTO requisicaoLiberacaoDto = new RequisicaoLiberacaoDTO();
		List<TarefaFluxoDTO> result = null;
		List<TarefaFluxoDTO> listTarefas = super.recuperaTarefas(loginUsuario);
		if (listTarefas != null) {
			result = new ArrayList();
			RequisicaoLiberacaoServiceEjb requisicaoService = new RequisicaoLiberacaoServiceEjb();
			ExecucaoLiberacaoDao execucaoLiberacaoDao = new ExecucaoLiberacaoDao(); 
			for (TarefaFluxoDTO tarefaDto : listTarefas) {
				ExecucaoLiberacaoDTO execucaoLiberacaoDto = execucaoLiberacaoDao.findByIdInstanciaFluxo(tarefaDto.getIdInstancia());
				if (execucaoLiberacaoDto != null && execucaoLiberacaoDto.getIdRequisicaoLiberacao() != null) {
					if(execucaoLiberacaoDto.getIdRequisicaoLiberacao() != null){
						requisicaoLiberacaoDto = requisicaoService.restoreAll(execucaoLiberacaoDto.getIdRequisicaoLiberacao(),null);
					}
					if (requisicaoLiberacaoDto != null) {
						tarefaDto.setRequisicaoLiberacaoDto(requisicaoLiberacaoDto);
						result.add(tarefaDto);
					}
				}
			}
			Collections.sort(result, new ObjectSimpleComparator("getDataHoraInicio", ObjectSimpleComparator.ASC));
		}
		return result;
	}
	
	@Override	
	public void enviaEmail(String identificador) throws Exception {
		if (identificador == null)
			return;
	
		ModeloEmailDTO modeloEmailDto = new ModeloEmailDao().findByIdentificador(identificador);
		if (modeloEmailDto != null)
			enviaEmail(modeloEmailDto.getIdModeloEmail());
	}
	
	@Override	
	public void enviaEmail(Integer idModeloEmail) throws Exception {
		if (idModeloEmail == null)
			return;
		
		String enviaEmail = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.EnviaEmailFluxo, "N");
		if (!enviaEmail.equalsIgnoreCase("S"))
			return;
		
		RequisicaoLiberacaoDTO requisicaoAuxDto = new RequisicaoLiberacaoServiceEjb().restoreAll(getRequisicaoLiberacaoDto().getIdRequisicaoLiberacao(), getTransacao());
		requisicaoAuxDto.setNomeContato(this.getRequisicaoLiberacaoDto().getNomeContato());
		if (requisicaoAuxDto.getEmailSolicitante() == null)
			return;

		String remetente = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.RemetenteNotificacoesSolicitacao, null);
		if (remetente == null)
			throw new LogicException(i18n_Message("requisicaoLiberacao.remetenteNaoDefinido"));
		
		
		
		requisicaoAuxDto.setNomeTarefa(getRequisicaoLiberacaoDto().getNomeTarefa());
		
		MensagemEmail mensagem = new MensagemEmail(idModeloEmail, new IDto[] {requisicaoAuxDto});
		try {
			mensagem.envia(requisicaoAuxDto.getEmailSolicitante(), remetente, remetente);
		} catch (Exception e) {
		}
	}

	private TipoLiberacaoDTO recuperaTipoLiberacao() throws Exception {
		TipoLiberacaoDAO tipoLiberacaoDao = new TipoLiberacaoDAO();			
		setTransacaoDao(tipoLiberacaoDao);
		TipoLiberacaoDTO tipoLiberacaoDto = new TipoLiberacaoDTO();
		if(getRequisicaoLiberacaoDto().getIdTipoLiberacao()!=null){
			tipoLiberacaoDto.setIdTipoLiberacao(getRequisicaoLiberacaoDto().getIdTipoLiberacao());
			tipoLiberacaoDto = (TipoLiberacaoDTO) tipoLiberacaoDao.restore(tipoLiberacaoDto);
		}
		
		if (tipoLiberacaoDto == null){
			
			 throw new LogicException(i18n_Message("requisicaoLiberacao.categoriaLiberacaoNaoLocalizada"));
		}
		   
		return  tipoLiberacaoDto;
	}

	@Override
	public void encerra() throws Exception {
		RequisicaoLiberacaoDTO requisicaoLiberacaoDto = getRequisicaoLiberacaoDto();
		if (requisicaoLiberacaoDto == null)
		    throw new LogicException(i18n_Message("requisicaoLiberacao.naoEncontrada"));
		
		if (requisicaoLiberacaoDto.encerrada())
			return;
		
		//if (!requisicaoLiberacaoDto.atendida() && !requisicaoLiberacaoDto.reclassificada())
		//    throw new LogicException("Solicitação de serviço não permite encerramento");
	
		Collection<ExecucaoLiberacaoDTO> colExecucao = new ExecucaoLiberacaoDao().listByIdRequisicaoLiberacao(getRequisicaoLiberacaoDto().getIdRequisicaoLiberacao());
		if (colExecucao != null) {
			for (ExecucaoLiberacaoDTO execucaoDto : colExecucao) {
				InstanciaFluxo instanciaFluxo = new InstanciaFluxo(this, execucaoDto.getIdInstanciaFluxo());
				instanciaFluxo.encerra();
			}
		}
		if(!requisicaoLiberacaoDto.getStatus().equalsIgnoreCase(SituacaoRequisicaoLiberacao.Cancelada.name())){
			requisicaoLiberacaoDto.setStatus(SituacaoRequisicaoLiberacao.Concluida.name());
		}
		requisicaoLiberacaoDto.setDataHoraConclusao(UtilDatas.getDataHoraAtual());
		calculaTempoCaptura(requisicaoLiberacaoDto);
		calculaTempoAtendimento(requisicaoLiberacaoDto);
		calculaTempoAtraso(requisicaoLiberacaoDto);
		RequisicaoLiberacaoDao requisicaoLiberacaoDao = new RequisicaoLiberacaoDao();
		setTransacaoDao(requisicaoLiberacaoDao);
		requisicaoLiberacaoDao.updateNotNull(requisicaoLiberacaoDto);
		
		OcorrenciaLiberacaoDao ocorrenciaLiberacaoDao = new OcorrenciaLiberacaoDao();
		setTransacaoDao(ocorrenciaLiberacaoDao);
		OcorrenciaLiberacaoDTO ocorrenciaLiberacaoDto = new OcorrenciaLiberacaoDTO();
		ocorrenciaLiberacaoDto.setIdRequisicaoLiberacao(getRequisicaoLiberacaoDto().getIdRequisicaoLiberacao());
		ocorrenciaLiberacaoDto.setDataregistro(UtilDatas.getDataAtual());
		ocorrenciaLiberacaoDto.setHoraregistro(UtilDatas.formatHoraFormatadaStr(UtilDatas.getHoraAtual()));
		ocorrenciaLiberacaoDto.setTempoGasto(0);
		ocorrenciaLiberacaoDto.setDescricao(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Encerramento.getDescricao());
		ocorrenciaLiberacaoDto.setDataInicio(UtilDatas.getDataAtual());
		ocorrenciaLiberacaoDto.setDataFim(UtilDatas.getDataAtual());
		ocorrenciaLiberacaoDto.setRegistradopor("Automático");
		ocorrenciaLiberacaoDto.setDadosLiberacao(new Gson().toJson(getRequisicaoLiberacaoDto()));
		ocorrenciaLiberacaoDto.setOrigem(br.com.centralit.citcorpore.util.Enumerados.OrigemOcorrencia.OUTROS.getSigla().toString());
		ocorrenciaLiberacaoDto.setCategoria(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Encerramento.getSigla());
		ocorrenciaLiberacaoDao.create(ocorrenciaLiberacaoDto);			    
		
		if (getRequisicaoLiberacaoDto().getEnviaEmailFinalizacao() != null && getRequisicaoLiberacaoDto().getEnviaEmailFinalizacao().equalsIgnoreCase("S")) {
			TipoLiberacaoDTO tipoLiberacaoDto = recuperaTipoLiberacao();
			enviaEmail(tipoLiberacaoDto.getIdModeloEmailFinalizacao());
		}
	}

	@Override
	public void reabre(String loginUsuario) throws Exception {
		RequisicaoLiberacaoDTO requisicaoLiberacaoDto = getRequisicaoLiberacaoDto();
		if (requisicaoLiberacaoDto == null)
			throw new LogicException(i18n_Message("requisicaoLiberacao.naoEncontrada"));

		if (!requisicaoLiberacaoDto.encerrada())
			throw new LogicException(i18n_Message("requisicaoLiberacao.reabertura"));

		usuarioDto = new UsuarioDao().restoreByLogin(loginUsuario);
	int seqReabertura = 1;
	/*ReaberturaLiberacaoDao reaberturaLiberacaoDao = new ReaberturaLiberacaoDao();
		setTransacaoDao(reaberturaLiberacaoDao);
		Collection colReabertura = reaberturaLiberacaoDao.findByIdRequisicaoLiberacao(requisicaoLiberacaoDto.getIdRequisicaoLiberacao());
		if (colReabertura != null)
			seqReabertura = colReabertura.size() + 1;

		ReaberturaRequisicaoLiberacaoDTO reaberturaLiberacaoDto = new ReaberturaRequisicaoLiberacaoDTO();
		reaberturaLiberacaoDto.setIdRequisicaoLiberacao(requisicaoLiberacaoDto.getIdRequisicaoLiberacao());
		reaberturaLiberacaoDto.setSeqReabertura(seqReabertura);
		reaberturaLiberacaoDto.setIdResponsavel(usuarioDto.getIdUsuario());
		reaberturaLiberacaoDto.setDataHora(UtilDatas.getDataHoraAtual());
		reaberturaLiberacaoDao.create(reaberturaLiberacaoDto);*/

		requisicaoLiberacaoDto.setStatus(SituacaoRequisicaoLiberacao.Reaberta.name());
		requisicaoLiberacaoDto.setSeqReabertura(new Integer(seqReabertura));
		RequisicaoLiberacaoDao requisicaoDao = new RequisicaoLiberacaoDao();
		setTransacaoDao(requisicaoDao);
		requisicaoDao.update(requisicaoLiberacaoDto);

		OcorrenciaLiberacaoDao ocorrenciaLiberacaoDao = new OcorrenciaLiberacaoDao();
		setTransacaoDao(ocorrenciaLiberacaoDao);
		OcorrenciaLiberacaoDTO ocorrenciaLiberacaoDto = new OcorrenciaLiberacaoDTO();
		ocorrenciaLiberacaoDto.setIdRequisicaoLiberacao(requisicaoLiberacaoDto.getIdRequisicaoLiberacao());
		ocorrenciaLiberacaoDto.setDataregistro(UtilDatas.getDataAtual());
		ocorrenciaLiberacaoDto.setHoraregistro(UtilDatas.formatHoraFormatadaStr(UtilDatas.getHoraAtual()));
		ocorrenciaLiberacaoDto.setTempoGasto(0);
		ocorrenciaLiberacaoDto.setDescricao(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Reabertura.getDescricao());
		ocorrenciaLiberacaoDto.setDataInicio(UtilDatas.getDataAtual());
		ocorrenciaLiberacaoDto.setDataFim(UtilDatas.getDataAtual());
		ocorrenciaLiberacaoDto.setRegistradopor(usuarioDto.getLogin());
		ocorrenciaLiberacaoDto.setDadosLiberacao(new Gson().toJson(getRequisicaoLiberacaoDto()));
		ocorrenciaLiberacaoDto.setOrigem(br.com.centralit.citcorpore.util.Enumerados.OrigemOcorrencia.OUTROS.getSigla().toString());
		ocorrenciaLiberacaoDto.setCategoria(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Reabertura.getSigla());
		ocorrenciaLiberacaoDao.create(ocorrenciaLiberacaoDto);			    

		inicia();
	}

	@Override
	public void suspende(String loginUsuario) throws Exception {
		RequisicaoLiberacaoDTO requisicaoLiberacaoDto = getRequisicaoLiberacaoDto();
		if (requisicaoLiberacaoDto == null)
			throw new LogicException(i18n_Message("requisicaoLiberacao.naoEncontrada"));

		if (!requisicaoLiberacaoDto.emAtendimento() && !requisicaoLiberacaoDto.naoResolvida())
			throw new LogicException(i18n_Message("requisicaoLiberacao.suspensao"));

		Timestamp tsAtual = UtilDatas.getDataHoraAtual();
		Timestamp tsInicial = requisicaoLiberacaoDto.getDataHoraInicio();
		if (requisicaoLiberacaoDto.getDataHoraReativacao() != null)
			tsInicial = requisicaoLiberacaoDto.getDataHoraReativacao();
		TipoLiberacaoDTO tipoLiberacaoDto = recuperaTipoLiberacao();
		if(tipoLiberacaoDto.getIdCalendario()!=null){
			requisicaoLiberacaoDto.setIdCalendario(tipoLiberacaoDto.getIdCalendario());
		}
		CalculoJornadaDTO calculoDto = new CalculoJornadaDTO(requisicaoLiberacaoDto.getIdCalendario(), tsInicial);
		calculoDto = new CalendarioServiceEjb().calculaPrazoDecorrido(calculoDto, tsAtual, null);
		
		RequisicaoLiberacaoDao requisicaoDao = new RequisicaoLiberacaoDao();
		setTransacaoDao(requisicaoDao);

		requisicaoLiberacaoDto.setStatus(SituacaoRequisicaoLiberacao.Suspensa.name());
		if (requisicaoLiberacaoDto.getTempoDecorridoHH() == null){
		    requisicaoLiberacaoDto.setTempoDecorridoHH(0);
		}
		if (requisicaoLiberacaoDto.getTempoDecorridoMM() == null){
		    requisicaoLiberacaoDto.setTempoDecorridoMM(0);
		}		
		requisicaoLiberacaoDto.setTempoDecorridoHH(new Integer(requisicaoLiberacaoDto.getTempoDecorridoHH().intValue() + calculoDto.getTempoDecorridoHH().intValue()));
		requisicaoLiberacaoDto.setTempoDecorridoMM(new Integer(requisicaoLiberacaoDto.getTempoDecorridoMM().intValue() + calculoDto.getTempoDecorridoMM().intValue()));
		requisicaoLiberacaoDto.setDataHoraSuspensao(tsAtual);
		requisicaoLiberacaoDto.setDataHoraReativacao(null);
		requisicaoDao.update(requisicaoLiberacaoDto);

		OcorrenciaLiberacaoDao ocorrenciaLiberacaoDao = new OcorrenciaLiberacaoDao();
		setTransacaoDao(ocorrenciaLiberacaoDao);
		OcorrenciaLiberacaoDTO ocorrenciaLiberacaoDto = new OcorrenciaLiberacaoDTO();
		ocorrenciaLiberacaoDto.setIdRequisicaoLiberacao(requisicaoLiberacaoDto.getIdRequisicaoLiberacao());
		ocorrenciaLiberacaoDto.setDataregistro(new Date(tsAtual.getTime()));
		ocorrenciaLiberacaoDto.setHoraregistro(UtilDatas.formatHoraFormatadaStr(tsAtual));
		ocorrenciaLiberacaoDto.setTempoGasto(0);
		ocorrenciaLiberacaoDto.setDescricao(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Suspensao.getDescricao());
		ocorrenciaLiberacaoDto.setDataInicio(UtilDatas.getDataAtual());
		ocorrenciaLiberacaoDto.setDataFim(UtilDatas.getDataAtual());
		ocorrenciaLiberacaoDto.setRegistradopor(loginUsuario);
		ocorrenciaLiberacaoDto.setDadosLiberacao(new Gson().toJson(getRequisicaoLiberacaoDto()));
		ocorrenciaLiberacaoDto.setIdJustificativa(requisicaoLiberacaoDto.getIdJustificativa());
		ocorrenciaLiberacaoDto.setComplementoJustificativa(requisicaoLiberacaoDto.getComplementoJustificativa());
		ocorrenciaLiberacaoDto.setOrigem(br.com.centralit.citcorpore.util.Enumerados.OrigemOcorrencia.OUTROS.getSigla().toString());
		ocorrenciaLiberacaoDto.setCategoria(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Suspensao.getSigla());
		ocorrenciaLiberacaoDao.create(ocorrenciaLiberacaoDto);	
		if (getRequisicaoLiberacaoDto().getEnviaEmailAcoes() != null && getRequisicaoLiberacaoDto().getEnviaEmailAcoes().equalsIgnoreCase("S")) {
			enviaEmail(tipoLiberacaoDto.getIdModeloEmailAcoes());
		}
	}

	@Override
	public void reativa(String loginUsuario) throws Exception {
		RequisicaoLiberacaoDTO requisicaoLiberacaoDto = getRequisicaoLiberacaoDto();
		if (requisicaoLiberacaoDto == null)
			throw new LogicException(i18n_Message("requisicaoLiberacao.naoEncontrada"));

		if (!requisicaoLiberacaoDto.suspensa())
			throw new LogicException(i18n_Message("requisicaoLiberacao.reativacao"));

		Timestamp tsAtual = UtilDatas.getDataHoraAtual();
		
		if(requisicaoLiberacaoDto.getPrazoHH() != null && requisicaoLiberacaoDto.getPrazoMM() != null && requisicaoLiberacaoDto.getTempoDecorridoHH() != null && requisicaoLiberacaoDto.getTempoDecorridoMM() !=null ){
			double prazo = requisicaoLiberacaoDto.getPrazoHH() + new Double(requisicaoLiberacaoDto.getPrazoMM()).doubleValue() / 60;
			double tempo = requisicaoLiberacaoDto.getTempoDecorridoHH() + new Double(requisicaoLiberacaoDto.getTempoDecorridoMM()).doubleValue() / 60;
			prazo = prazo - tempo;
			if (prazo > 0){
			    CalculoJornadaDTO calculoDto = new CalculoJornadaDTO(requisicaoLiberacaoDto.getIdCalendario(), tsAtual, Util.getHora(prazo), Util.getMinuto(prazo));
			
			    calculoDto = new CalendarioServiceEjb().calculaDataHoraFinal(calculoDto, false, null);
			    requisicaoLiberacaoDto.setDataHoraTermino(calculoDto.getDataHoraFinal());
			}
			
		}
		

		RequisicaoLiberacaoDao requisicaoDao = new RequisicaoLiberacaoDao();
		setTransacaoDao(requisicaoDao);

		requisicaoLiberacaoDto.setStatus(SituacaoRequisicaoLiberacao.EmExecucao.name());
		requisicaoLiberacaoDto.setDataHoraSuspensao(null);
		requisicaoLiberacaoDto.setDataHoraReativacao(tsAtual);
		requisicaoDao.update(requisicaoLiberacaoDto);

		OcorrenciaLiberacaoDao ocorrenciaLiberacaoDao = new OcorrenciaLiberacaoDao();
		setTransacaoDao(ocorrenciaLiberacaoDao);
		OcorrenciaLiberacaoDTO ocorrenciaLiberacaoDto = new OcorrenciaLiberacaoDTO();
		ocorrenciaLiberacaoDto.setIdRequisicaoLiberacao(requisicaoLiberacaoDto.getIdRequisicaoLiberacao());
		ocorrenciaLiberacaoDto.setDataregistro(new Date(tsAtual.getTime()));
		ocorrenciaLiberacaoDto.setHoraregistro(UtilDatas.formatHoraFormatadaStr(tsAtual));
		ocorrenciaLiberacaoDto.setTempoGasto(0);
		ocorrenciaLiberacaoDto.setDescricao(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Reativacao.getDescricao());
		ocorrenciaLiberacaoDto.setDataInicio(UtilDatas.getDataAtual());
		ocorrenciaLiberacaoDto.setDataFim(UtilDatas.getDataAtual());
		ocorrenciaLiberacaoDto.setRegistradopor(loginUsuario);
		ocorrenciaLiberacaoDto.setDadosLiberacao(new Gson().toJson(getRequisicaoLiberacaoDto()));
		ocorrenciaLiberacaoDto.setOrigem(br.com.centralit.citcorpore.util.Enumerados.OrigemOcorrencia.OUTROS.getSigla().toString());
		ocorrenciaLiberacaoDto.setCategoria(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Reativacao.getSigla());
		ocorrenciaLiberacaoDao.create(ocorrenciaLiberacaoDto);		 
		if (getRequisicaoLiberacaoDto().getEnviaEmailAcoes() != null && getRequisicaoLiberacaoDto().getEnviaEmailAcoes().equalsIgnoreCase("S")) {
			TipoLiberacaoDTO tipoLiberacaoDto = recuperaTipoLiberacao();
			enviaEmail(tipoLiberacaoDto.getIdModeloEmailAcoes());
		}
	}
	
	private Integer getIdCalendario(RequisicaoLiberacaoDTO requisicaoLiberacaoDto) throws Exception {
		Integer idCalendario = requisicaoLiberacaoDto.getIdCalendario();
		if (requisicaoLiberacaoDto.getIdCalendario() == null) {
			TipoLiberacaoDTO tipoLiberacaoDto = recuperaTipoLiberacao();
			idCalendario = tipoLiberacaoDto.getIdCalendario();
		}
		return idCalendario;
	}
	
	public UsuarioDTO usuarioSolicitante(RequisicaoLiberacaoDTO requisicaoLiberacaoDto) throws Exception{
		UsuarioDao usuarioDao = new UsuarioDao();
		UsuarioDTO usuario =  usuarioDao.restoreByIdEmpregado(requisicaoLiberacaoDto.getIdSolicitante());
		
		return usuario;
	}
	
	public void calculaTempoAtendimento(RequisicaoLiberacaoDTO requisicaoLiberacaoDto) throws Exception {
		Integer idCalendario = getIdCalendario(requisicaoLiberacaoDto);
		
		Timestamp tsAtual = UtilDatas.getDataHoraAtual();
		if (requisicaoLiberacaoDto.getStatus().equalsIgnoreCase(SituacaoRequisicaoLiberacao.Concluida.name()))
			tsAtual = requisicaoLiberacaoDto.getDataHoraConclusao();
		
		Timestamp tsInicial = requisicaoLiberacaoDto.getDataHoraInicio();
		if (requisicaoLiberacaoDto.getDataHoraReativacao() != null)
			tsInicial = requisicaoLiberacaoDto.getDataHoraReativacao();
		
		CalculoJornadaDTO calculoDto = new CalculoJornadaDTO(idCalendario, tsInicial);
		calculoDto = new CalendarioServiceEjb().calculaPrazoDecorrido(calculoDto, tsAtual, null);
		
		if (requisicaoLiberacaoDto.getTempoDecorridoHH() == null){
		    requisicaoLiberacaoDto.setTempoDecorridoHH(0);
		}
		if (requisicaoLiberacaoDto.getTempoDecorridoMM() == null){
		    requisicaoLiberacaoDto.setTempoDecorridoMM(0);
		}		

		requisicaoLiberacaoDto.setTempoAtendimentoHH(new Integer(requisicaoLiberacaoDto.getTempoDecorridoHH().intValue() + calculoDto.getTempoDecorridoHH().intValue()));
		requisicaoLiberacaoDto.setTempoAtendimentoMM(new Integer(requisicaoLiberacaoDto.getTempoDecorridoMM().intValue() + calculoDto.getTempoDecorridoMM().intValue()));
	}	

	public void calculaTempoCaptura(RequisicaoLiberacaoDTO requisicaoLiberacaoDto) throws Exception {
		requisicaoLiberacaoDto.setTempoCapturaHH(0);
		requisicaoLiberacaoDto.setTempoCapturaMM(0);
		
		if (requisicaoLiberacaoDto.getDataHoraCaptura() == null)
			return;
		
		Integer idCalendario = getIdCalendario(requisicaoLiberacaoDto);
		
		CalculoJornadaDTO calculoDto = new CalculoJornadaDTO(idCalendario, requisicaoLiberacaoDto.getDataHoraInicio());
		calculoDto = new CalendarioServiceEjb().calculaPrazoDecorrido(calculoDto, requisicaoLiberacaoDto.getDataHoraCaptura(), null);
		
		requisicaoLiberacaoDto.setTempoCapturaHH(calculoDto.getTempoDecorridoHH().intValue());
		requisicaoLiberacaoDto.setTempoCapturaMM(calculoDto.getTempoDecorridoMM().intValue());
	}	
	
	public void calculaTempoAtraso(RequisicaoLiberacaoDTO requisicaoLiberacaoDto) throws Exception {
		requisicaoLiberacaoDto.setTempoAtrasoHH(0);
		requisicaoLiberacaoDto.setTempoAtrasoMM(0);
		if (requisicaoLiberacaoDto.getDataHoraTermino() != null) {
			Timestamp dataHoraLimite = requisicaoLiberacaoDto.getDataHoraTermino();
			Timestamp dataHoraComparacao = UtilDatas.getDataHoraAtual();
			if (requisicaoLiberacaoDto.encerrada())
				dataHoraComparacao = requisicaoLiberacaoDto.getDataHoraConclusao();
			if (dataHoraComparacao.compareTo(dataHoraLimite) > 0) {
				long atrasoSLA = UtilDatas.calculaDiferencaTempoEmMilisegundos(dataHoraComparacao, dataHoraLimite) / 1000;

				String hora = Util.getHoraStr(new Double(atrasoSLA) / 3600);
				int tam = hora.length();
				requisicaoLiberacaoDto.setTempoAtrasoHH(new Integer(hora.substring(0, tam - 2)));
				requisicaoLiberacaoDto.setTempoAtrasoMM(new Integer(hora.substring(tam - 2, tam)));			
			}
		}
	}

    @Override
    public void executaEvento(EventoFluxoDTO eventoFluxoDto) throws Exception {
        ExecucaoLiberacaoDao execucaoLiberacaoDao = new ExecucaoLiberacaoDao();
        ExecucaoLiberacaoDTO execucaoLiberacaoDto = execucaoLiberacaoDao.findByIdInstanciaFluxo(eventoFluxoDto.getIdInstancia());
        if (execucaoLiberacaoDto == null){
        	System.out.println("Execução liberação do evento "+eventoFluxoDto.getIdItemTrabalho()+" não encontrada");
            throw new LogicException(i18n_Message("requisicaoLiberacao.evento"));
        }
        RequisicaoLiberacaoDao requisicaoLiberacaoDao = new RequisicaoLiberacaoDao();
        RequisicaoLiberacaoDTO requisicaoLiberacaoDto = new RequisicaoLiberacaoDTO();
        requisicaoLiberacaoDto.setIdRequisicaoLiberacao(execucaoLiberacaoDto.getIdRequisicaoLiberacao());
        requisicaoLiberacaoDto = (RequisicaoLiberacaoDTO) requisicaoLiberacaoDao.restore(requisicaoLiberacaoDto);
        if (requisicaoLiberacaoDto == null){
        	System.out.println("Execução liberação do evento "+eventoFluxoDto.getIdItemTrabalho()+" não encontrada");
        	throw new LogicException(i18n_Message("requisicaoLiberacao.evento"));
        }
        TransactionControler tc = new TransactionControlerImpl(execucaoLiberacaoDao.getAliasDB());
        setTransacao(tc);
        setObjetoNegocioDto(requisicaoLiberacaoDto);
        InstanciaFluxo instanciaFluxo = new InstanciaFluxo(this, eventoFluxoDto.getIdInstancia());
        
        HashMap<String, Object> map = new HashMap();
        mapObjetoNegocio(instanciaFluxo.getObjetos(map));
        instanciaFluxo.executaEvento(eventoFluxoDto.getIdItemTrabalho(), map);
        
    }

    @Override
    public void validaEncerramento() throws Exception {
        // TODO Auto-generated method stub
    }

	@Override
	public void enviaEmail(String identificador, String[] destinatarios) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Notifica todos os Empregados de um grupo.
	 * 
	 * @param idModeloEmail
	 * @throws Exception
	 */
	public void enviaEmailGrupo(Integer idModeloEmail, Integer idGrupoDestino) throws Exception {
		MensagemEmail mensagem = null;

		if (idGrupoDestino == null) {
			return;
		}

		if (idModeloEmail == null) {
			return;
		}

		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);

		List<String> emails = null;

		try {
			emails = (List<String>) grupoService.listarEmailsPorGrupo(idGrupoDestino);
		} catch (Exception e) {
			return;
		}

		if (emails == null || emails.isEmpty()) {
			return;
		}

		String remetente = ParametroUtil.getValor(ParametroSistema.RemetenteNotificacoesSolicitacao, getTransacao(), null);
		if (remetente == null)
			throw new LogicException(i18n_Message("requisicaoLiberacao.remetenteNaoDefinido"));

		RequisicaoLiberacaoDTO requisicaoRequisicaoLiberacaoDTO = new RequisicaoLiberacaoServiceEjb().restoreAll(this.getRequisicaoLiberacaoDto().getIdRequisicaoLiberacao(), getTransacao());
		if (requisicaoRequisicaoLiberacaoDTO == null) {
			return;
		}
		requisicaoRequisicaoLiberacaoDTO.setNomeTarefa(getRequisicaoLiberacaoDto().getNomeTarefa());

		try {

			// EmpregadoDTO aux = null;
			for (String email : emails) {
				int posArroba = email.indexOf("@");
				if (posArroba > 0 && email.substring(posArroba).contains(".")) {
					try {
						mensagem = new MensagemEmail(idModeloEmail, new IDto[] { requisicaoRequisicaoLiberacaoDTO });

						// aux = (EmpregadoDTO) getEmpregadoService().restore(e);
						// if(aux != null && aux.getEmail() != null && !aux.getEmail().trim().equalsIgnoreCase("") ){
						mensagem.envia(email, remetente, remetente);
					} catch (Exception e) {
						// faz nada
					}
				}
				// }
			}
		} catch (Exception e) {
		}
	}
	
	/**
	 * Notifica proprietario da Requisição mudança.
	 * 
	 * @param idModeloEmail
	 * @throws Exception
	 * @author thays.araujo
	 */
	public void enviaEmailProprietario(Integer idModeloEmail) throws Exception {
		MensagemEmail mensagem = null;

		if (idModeloEmail == null) {
			return;
		}

		EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
		
		EmpregadoDTO empregadoDto = new EmpregadoDTO();
		
		empregadoDto.setIdEmpregado(this.getRequisicaoLiberacaoDto().getUsuarioDto().getIdEmpregado());
		
		empregadoDto =  (EmpregadoDTO) empregadoService.restore(empregadoDto);

		String email = "";

		try {
			email = empregadoDto.getEmail();
		} catch (Exception e) {
			return;
		}

		if (email == null || email.isEmpty() || email.equalsIgnoreCase("")) {
			return;
		}

		String remetente = ParametroUtil.getValor(ParametroSistema.RemetenteNotificacoesSolicitacao, getTransacao(), null);
		if (remetente == null)
			throw new LogicException(i18n_Message("requisicaoLiberacao.remetenteNaoDefinido"));

		RequisicaoLiberacaoDTO requisicaoRequisicaoLiberacaoDTO = new RequisicaoLiberacaoServiceEjb().restoreAll(this.getRequisicaoLiberacaoDto().getIdRequisicaoLiberacao(), getTransacao());
		if (requisicaoRequisicaoLiberacaoDTO == null) {
			return;
		}
		requisicaoRequisicaoLiberacaoDTO.setNomeTarefa(getRequisicaoLiberacaoDto().getNomeTarefa());

		try {
			// EmpregadoDTO aux = null;
				int posArroba = email.indexOf("@");
				if (posArroba > 0 && email.substring(posArroba).contains(".")) {
					try {
						mensagem = new MensagemEmail(idModeloEmail, new IDto[] { requisicaoRequisicaoLiberacaoDTO });

						// aux = (EmpregadoDTO) getEmpregadoService().restore(e);
						// if(aux != null && aux.getEmail() != null && !aux.getEmail().trim().equalsIgnoreCase("") ){
						mensagem.envia(email, remetente, remetente);
					} catch (Exception e) {
						// faz nada
					}
				}
				// }
		} catch (Exception e) {
		}
	}

	@Override
	public void verificaSLA(ItemTrabalho itemTrabalho) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public void finalizaIC(RequisicaoLiberacaoDTO liberacao) throws ServiceException, Exception{
		RequisicaoLiberacaoItemConfiguracaoService liberacaoICService = (RequisicaoLiberacaoItemConfiguracaoService) ServiceLocator.getInstance().getService(RequisicaoLiberacaoItemConfiguracaoService.class, null);
		ItemConfiguracaoService itemService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
		List<RequisicaoLiberacaoItemConfiguracaoDTO> lista = liberacaoICService.listByIdRequisicaoLiberacao(liberacao.getIdRequisicaoLiberacao());
		
		if(lista != null && lista.size() > 0){
			for(RequisicaoLiberacaoItemConfiguracaoDTO req : lista){
				itemService.atualizaParaGrupoProducao(req.getIdItemConfiguracao());
				this.atualizaHistoricoIC(req.getIdItemConfiguracao(), liberacao);
			}
		}
	}
	
	public void atualizaHistoricoIC(Integer idItemConfiguracao, RequisicaoLiberacaoDTO liberacao) throws br.com.citframework.excecao.ServiceException, Exception{
		ItemConfiguracaoService itemService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
		ItemConfiguracaoDTO item = new ItemConfiguracaoDTO();
		item.setIdItemConfiguracao(idItemConfiguracao);
		item = (ItemConfiguracaoDTO) itemService.restore(item);
		
		try{
			itemService.createHistoricoItemComOrigem(item, liberacao, "L");
			itemService.atualizaStatus(item.getIdItemConfiguracao(), StatusIC.VALIDAR.getItem());
		}catch(Exception e){
			
		}
	}
	
}