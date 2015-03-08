package br.com.centralit.citcorpore.bpm.negocio;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.bpm.dto.AtribuicaoFluxoDTO;
import br.com.centralit.bpm.dto.EventoFluxoDTO;
import br.com.centralit.bpm.dto.FluxoDTO;
import br.com.centralit.bpm.dto.InstanciaFluxoDTO;
import br.com.centralit.bpm.dto.ObjetoNegocioFluxoDTO;
import br.com.centralit.bpm.dto.TarefaFluxoDTO;
import br.com.centralit.bpm.dto.TipoFluxoDTO;
import br.com.centralit.bpm.integracao.AtribuicaoFluxoDao;
import br.com.centralit.bpm.integracao.FluxoDao;
import br.com.centralit.bpm.integracao.TipoFluxoDao;
import br.com.centralit.bpm.negocio.ExecucaoFluxo;
import br.com.centralit.bpm.negocio.InstanciaFluxo;
import br.com.centralit.bpm.negocio.ItemTrabalho;
import br.com.centralit.bpm.util.Enumerados;
import br.com.centralit.bpm.util.Enumerados.TipoAtribuicao;
import br.com.centralit.citcorpore.bean.CalculoJornadaDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.ExecucaoMudancaDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.ModeloEmailDTO;
import br.com.centralit.citcorpore.bean.OcorrenciaMudancaDTO;
import br.com.centralit.citcorpore.bean.ReaberturaMudancaDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.TipoMudancaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.ExecucaoMudancaDao;
import br.com.centralit.citcorpore.integracao.GrupoDao;
import br.com.centralit.citcorpore.integracao.ModeloEmailDao;
import br.com.centralit.citcorpore.integracao.OcorrenciaMudancaDao;
import br.com.centralit.citcorpore.integracao.ReaberturaMudancaDao;
import br.com.centralit.citcorpore.integracao.RequisicaoMudancaDao;
import br.com.centralit.citcorpore.integracao.TipoMudancaDAO;
import br.com.centralit.citcorpore.integracao.UsuarioDao;
import br.com.centralit.citcorpore.mail.MensagemEmail;
import br.com.centralit.citcorpore.negocio.CalendarioServiceEjb;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.GrupoEmailService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.RequisicaoMudancaServiceEjb;
import br.com.centralit.citcorpore.util.Enumerados.FaseRequisicaoMudanca;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoRequisicaoMudanca;
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
public class ExecucaoMudanca extends ExecucaoFluxo {
	

	private UsuarioDTO usuarioDto = null;
	
	public ExecucaoMudanca(TransactionControler tc) {
		super(tc);
	}

	public ExecucaoMudanca() {
		super();
	}

	public ExecucaoMudanca(RequisicaoMudancaDTO requisicaoMudancaDto, TransactionControler tc, Usuario usuario) {
		super(requisicaoMudancaDto, tc, usuario);
	}
	
//	public ExecucaoMudanca(RequisicaoMudancaDTO requisicaoMudancaDto, TransactionControler tc) {
//		super(requisicaoMudancaDto, tc);
//	}

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
		if (getRequisicaoMudancaDto().getIdTipoMudanca() == null)
			throw new LogicException(i18n_Message("requisicaoMudanca.tipoNaoDefinido"));
		
		InstanciaFluxo result = null;
		InstanciaFluxoDTO instanciaFluxoDto = null;
		
		TipoMudancaDTO tipoMudancaDto = recuperaTipoMudanca();
		if (tipoMudancaDto.getIdTipoFluxo() != null){ 
			result = inicia(new FluxoDao().findByTipoFluxo(tipoMudancaDto.getIdTipoFluxo()), null);
		}else{
			String fluxoPadrao = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.FLUXO_PADRAO_MUDANCAS, null);
			if (fluxoPadrao == null)
				throw new LogicException(i18n_Message("citcorpore.comum.fluxoNaoParametrizado"));
			result = inicia(fluxoPadrao, null);
		}
		
		try {
			String enviarNotificacao = ParametroUtil.getValor(ParametroSistema.NOTIFICAR_GRUPO_RECEPCAO_SOLICITACAO, getTransacao(), "N");
			String IdModeloEmailGrupoDestinoREquisicaoMudanca = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_MODELO_EMAIL_GRUPO_DESTINO_REQUISICAOMUDANCA, "30");
			if (enviarNotificacao.equalsIgnoreCase("S") && getRequisicaoMudancaDto().escalada()) {
				enviaEmailGrupo(Integer.parseInt(IdModeloEmailGrupoDestinoREquisicaoMudanca.trim()),tipoMudancaDto.getIdGrupoExecutor());
			}
		} catch (NumberFormatException e) {
			System.out.println("Não há modelo de e-mail setado nos parâmetros.");
		}
		
		try {
			
			String IdModeloEmailGrupoComiteREquisicaoMudanca = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_MODELO_EMAIL_GRUPO_COMITE_REQUISICAOMUDANCA, "29");
			if (this.getRequisicaoMudancaDto().getEnviaEmailGrupoComite()!=null && this.getRequisicaoMudancaDto().getEnviaEmailGrupoComite().equalsIgnoreCase("S")) {
				enviaEmailGrupo(Integer.parseInt(IdModeloEmailGrupoComiteREquisicaoMudanca),this.getRequisicaoMudancaDto().getIdGrupoComite());
				enviaEmailProprietario(Integer.parseInt(IdModeloEmailGrupoComiteREquisicaoMudanca.trim()));
			}
		} catch (NumberFormatException e) {
			System.out.println("Não há modelo de e-mail setado nos parâmetros.");
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
		
		ExecucaoMudancaDTO execucaoDto = new ExecucaoMudancaDTO();
		execucaoDto.setIdRequisicaoMudanca(getRequisicaoMudancaDto().getIdRequisicaoMudanca());
		execucaoDto.setIdFluxo(instanciaFluxo.getIdFluxo());
		execucaoDto.setIdInstanciaFluxo(instanciaFluxo.getIdInstancia());
		Integer seqReabertura = 0;
		if (getRequisicaoMudancaDto().getSeqReabertura() != null && getRequisicaoMudancaDto().getSeqReabertura().intValue() > 0)
			seqReabertura = getRequisicaoMudancaDto().getSeqReabertura();
		if (seqReabertura.intValue() > 0)
			execucaoDto.setSeqReabertura(getRequisicaoMudancaDto().getSeqReabertura());
		
		ExecucaoMudancaDao execucaoDao = new ExecucaoMudancaDao();
		setTransacaoDao(execucaoDao);
		execucaoFluxoDto = (ExecucaoMudancaDTO) execucaoDao.create(execucaoDto);
		
		
		
		if (seqReabertura.intValue() == 0 && getRequisicaoMudancaDto().getEnviaEmailCriacao() != null && getRequisicaoMudancaDto().getEnviaEmailCriacao().equalsIgnoreCase("S")) {
			TipoMudancaDTO tipoMudancaDto = recuperaTipoMudanca();
			enviaEmail(tipoMudancaDto.getIdModeloEmailCriacao());
		}
		return instanciaFluxo;
	}
	
	public void finalizaItemRelacionadoMudanca(RequisicaoMudancaDTO requisicaoMudancaDTO ) throws ServiceException, Exception{
		new RequisicaoMudancaServiceEjb().fechaRelacionamentoMudanca(super.getTransacao(), requisicaoMudancaDTO);
	}
	
	
	
	@Override
	public void executa(String loginUsuario, ObjetoNegocioFluxoDTO objetoNegocioDto, Integer idItemTrabalho, String acao, HashMap<String, Object> map) throws Exception {
		if (acao.equals(Enumerados.ACAO_DELEGAR))
			return;

		TarefaFluxoDTO tarefaFluxoDto = recuperaTarefa(idItemTrabalho);
		if (tarefaFluxoDto == null)
			return;

		ExecucaoMudancaDao execucaoMudancaDao = new ExecucaoMudancaDao();
		setTransacaoDao(execucaoMudancaDao);
		ExecucaoMudancaDTO execucaoMudancaDto = execucaoMudancaDao.findByIdInstanciaFluxo(tarefaFluxoDto.getIdInstancia());
		if (execucaoMudancaDto == null) 
			return;
		
		recuperaFluxo(execucaoMudancaDto.getIdFluxo());
		
		this.objetoNegocioDto = objetoNegocioDto;
		usuarioDto = new UsuarioDao().restoreByLogin(loginUsuario);

		InstanciaFluxo instanciaFluxo = new InstanciaFluxo(this, tarefaFluxoDto.getIdInstancia());
		mapObjetoNegocio(instanciaFluxo.getObjetos(map));
		if (acao.equals(Enumerados.ACAO_INICIAR)) 
			instanciaFluxo.iniciaItemTrabalho(loginUsuario, tarefaFluxoDto.getIdItemTrabalho(), map);
		else if (acao.equals(Enumerados.ACAO_EXECUTAR)) {
			tarefaFluxoDto = recuperaTarefa(idItemTrabalho);
			OcorrenciaMudancaDao ocorrenciaMudancaDao = new OcorrenciaMudancaDao();
			setTransacaoDao(ocorrenciaMudancaDao);
			OcorrenciaMudancaDTO ocorrenciaMudancaDto = new OcorrenciaMudancaDTO();
			ocorrenciaMudancaDto.setIdRequisicaoMudanca(getRequisicaoMudancaDto().getIdRequisicaoMudanca());
			ocorrenciaMudancaDto.setDataregistro(UtilDatas.getDataAtual());
			ocorrenciaMudancaDto.setHoraregistro(UtilDatas.formatHoraFormatadaStr(UtilDatas.getHoraAtual()));
			Long tempo = new Long(0);
			if (tarefaFluxoDto.getDataHoraFinalizacao() != null)
				tempo = (tarefaFluxoDto.getDataHoraFinalizacao().getTime() - tarefaFluxoDto.getDataHoraCriacao().getTime()) / 1000 / 60;
			ocorrenciaMudancaDto.setTempoGasto(tempo.intValue());
			ocorrenciaMudancaDto.setDescricao(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Execucao.getDescricao());
			ocorrenciaMudancaDto.setDataInicio(UtilDatas.getDataAtual());
			ocorrenciaMudancaDto.setDataFim(UtilDatas.getDataAtual());
			ocorrenciaMudancaDto.setInformacoesContato("não se aplica");
			ocorrenciaMudancaDto.setRegistradopor(loginUsuario);
			ocorrenciaMudancaDto.setDadosMudanca(new Gson().toJson(getRequisicaoMudancaDto()));
			ocorrenciaMudancaDto.setOcorrencia("Execução da tarefa \""+tarefaFluxoDto.getElementoFluxoDto().getDocumentacao()+"\"");
			ocorrenciaMudancaDto.setOrigem(br.com.centralit.citcorpore.util.Enumerados.OrigemOcorrencia.OUTROS.getSigla().toString());
			ocorrenciaMudancaDto.setCategoria(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Execucao.getSigla());
			ocorrenciaMudancaDto.setIdItemTrabalho(idItemTrabalho);
			ocorrenciaMudancaDao.create(ocorrenciaMudancaDto);			    

			instanciaFluxo.executaItemTrabalho(loginUsuario, tarefaFluxoDto.getIdItemTrabalho(), map);
			
			if (getRequisicaoMudancaDto().getFase() != null && getRequisicaoMudancaDto().getFase().trim().length() > 0) {
				SituacaoRequisicaoMudanca novaSituacao = FaseRequisicaoMudanca.valueOf(getRequisicaoMudancaDto().getFase()).getSituacao();
				if (novaSituacao != null) {
					RequisicaoMudancaDao requisicaoDao = new RequisicaoMudancaDao();
					setTransacaoDao(requisicaoDao);
					getRequisicaoMudancaDto().setStatus(novaSituacao.name());
					requisicaoDao.updateNotNull(getRequisicaoMudancaDto());
				}
			}
		}
		
		if (getRequisicaoMudancaDto().getEnviaEmailAcoes() != null && getRequisicaoMudancaDto().getEnviaEmailAcoes().equalsIgnoreCase("S")) {
			getRequisicaoMudancaDto().setNomeTarefa(tarefaFluxoDto.getElementoFluxoDto().getDocumentacao());
			TipoMudancaDTO tipoMudancaDto = recuperaTipoMudanca();
			enviaEmail(tipoMudancaDto.getIdModeloEmailAcoes());
		}
	}


	@Override
	public void delega(String loginUsuario, ObjetoNegocioFluxoDTO objetoNegocioDto, Integer idItemTrabalho, String usuarioDestino, String grupoDestino) throws Exception {
		TarefaFluxoDTO tarefaFluxoDto = recuperaTarefa(idItemTrabalho);
		if (tarefaFluxoDto == null)
			return;

		InstanciaFluxo instanciaFluxo = new InstanciaFluxo(this, tarefaFluxoDto.getIdInstancia());
		instanciaFluxo.delegaItemTrabalho(loginUsuario, idItemTrabalho, usuarioDestino, grupoDestino);
		
		this.objetoNegocioDto = objetoNegocioDto;

		OcorrenciaMudancaDao ocorrenciaMudancaDao = new OcorrenciaMudancaDao();
		setTransacaoDao(ocorrenciaMudancaDao);
		OcorrenciaMudancaDTO ocorrenciaMudancaDto = new OcorrenciaMudancaDTO();
		ocorrenciaMudancaDto.setIdRequisicaoMudanca(getRequisicaoMudancaDto().getIdRequisicaoMudanca());
		ocorrenciaMudancaDto.setDataregistro(UtilDatas.getDataAtual());
		ocorrenciaMudancaDto.setHoraregistro(UtilDatas.formatHoraFormatadaStr(UtilDatas.getHoraAtual()));
		ocorrenciaMudancaDto.setTempoGasto(0);
		ocorrenciaMudancaDto.setDescricao(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Compartilhamento.getDescricao());
		ocorrenciaMudancaDto.setDataInicio(UtilDatas.getDataAtual());
		ocorrenciaMudancaDto.setDataFim(UtilDatas.getDataAtual());
		ocorrenciaMudancaDto.setInformacoesContato("não se aplica");
		ocorrenciaMudancaDto.setRegistradopor(loginUsuario);
		ocorrenciaMudancaDto.setDadosMudanca(new Gson().toJson(getRequisicaoMudancaDto()));
		String ocorr = "Compartilhamento da tarefa \""+tarefaFluxoDto.getElementoFluxoDto().getDocumentacao()+"\"";
		if (usuarioDestino != null)
			ocorr += " com o usuário "+usuarioDestino;
		if (grupoDestino != null)
			ocorr += " com o grupo "+grupoDestino;
		ocorrenciaMudancaDto.setOcorrencia(ocorr);
		ocorrenciaMudancaDto.setOrigem(br.com.centralit.citcorpore.util.Enumerados.OrigemOcorrencia.OUTROS.getSigla().toString());
		ocorrenciaMudancaDto.setCategoria(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Compartilhamento.getSigla());
		ocorrenciaMudancaDto.setIdItemTrabalho(idItemTrabalho);
		ocorrenciaMudancaDao.create(ocorrenciaMudancaDto);			    
	}
	

	@Override
	public void direcionaAtendimento(String loginUsuario, ObjetoNegocioFluxoDTO objetoNegocioDto, String grupoAtendimento) throws Exception {
		if (getRequisicaoMudancaDto() == null)
			return;
		
		if(grupoAtendimento == null)
			return;
		
		GrupoDTO grupoAtendimentoDto = new GrupoDao().restoreBySigla(grupoAtendimento);
		if (grupoAtendimentoDto == null)
			return;
		
		UsuarioDTO usuarioRespDto = new UsuarioDTO();
		usuarioRespDto.setIdUsuario(getRequisicaoMudancaDto().getIdResponsavel());
		usuarioRespDto = (UsuarioDTO) new UsuarioDao().restore(usuarioRespDto);
		
		this.objetoNegocioDto = objetoNegocioDto;
		
		Collection<ExecucaoMudancaDTO> colExecucao = new ExecucaoMudancaDao().listByIdRequisicaoMudanca(getRequisicaoMudancaDto().getIdRequisicaoMudanca());
		if (colExecucao != null) {
			AtribuicaoFluxoDao atribuicaoFluxoDao = new AtribuicaoFluxoDao();
			setTransacaoDao(atribuicaoFluxoDao);
			OcorrenciaMudancaDao ocorrenciaMudancaDao = new OcorrenciaMudancaDao();
			setTransacaoDao(ocorrenciaMudancaDao);
			for (ExecucaoMudancaDTO execucaoSolicitacaoDto : colExecucao) {
				Collection<AtribuicaoFluxoDTO> colAtribuicao = atribuicaoFluxoDao.findByDisponiveisByIdInstancia(execucaoSolicitacaoDto.getIdInstanciaFluxo());
				if (colAtribuicao == null)
					continue;
				
				for (AtribuicaoFluxoDTO atribuicaoFluxoDto : colAtribuicao) {
					if (!atribuicaoFluxoDto.getTipo().equals(TipoAtribuicao.Automatica.name()))
						continue;
					
					if (atribuicaoFluxoDto.getIdGrupo() != null && atribuicaoFluxoDto.getIdGrupo().intValue() == grupoAtendimentoDto.getIdGrupo().intValue())
						continue;
					
					if (atribuicaoFluxoDto.getIdUsuario() != null && atribuicaoFluxoDto.getIdUsuario().intValue() == usuarioRespDto.getIdUsuario().intValue())
						delega(loginUsuario, objetoNegocioDto, atribuicaoFluxoDto.getIdItemTrabalho(), null, grupoAtendimento);
					else{
						atribuicaoFluxoDto.setIdUsuario(null);
						atribuicaoFluxoDto.setIdGrupo(grupoAtendimentoDto.getIdGrupo());
						atribuicaoFluxoDao.update(atribuicaoFluxoDto);
					}

					TarefaFluxoDTO tarefaFluxoDto = recuperaTarefa(atribuicaoFluxoDto.getIdItemTrabalho());
					OcorrenciaMudancaDTO ocorrenciaMudancaDto = new OcorrenciaMudancaDTO();
					ocorrenciaMudancaDto.setIdRequisicaoMudanca(getRequisicaoMudancaDto().getIdRequisicaoMudanca());
					ocorrenciaMudancaDto.setDataregistro(UtilDatas.getDataAtual());
					ocorrenciaMudancaDto.setHoraregistro(UtilDatas.formatHoraFormatadaStr(UtilDatas.getHoraAtual()));
					ocorrenciaMudancaDto.setTempoGasto(0);
					ocorrenciaMudancaDto.setDescricao(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Direcionamento.getDescricao());
					ocorrenciaMudancaDto.setDataInicio(UtilDatas.getDataAtual());
					ocorrenciaMudancaDto.setDataFim(UtilDatas.getDataAtual());
					ocorrenciaMudancaDto.setInformacoesContato("não se aplica");
					ocorrenciaMudancaDto.setRegistradopor(loginUsuario);
					ocorrenciaMudancaDto.setDadosMudanca(new Gson().toJson(getRequisicaoMudancaDto()));
					String ocorr = "Direcionamento da tarefa \""+tarefaFluxoDto.getElementoFluxoDto().getDocumentacao()+"\"";
					ocorr += " para o grupo "+grupoAtendimento;
					ocorrenciaMudancaDto.setOcorrencia(ocorr);
					ocorrenciaMudancaDto.setOrigem(br.com.centralit.citcorpore.util.Enumerados.OrigemOcorrencia.OUTROS.getSigla().toString());
					ocorrenciaMudancaDto.setCategoria(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Direcionamento.getSigla());
					ocorrenciaMudancaDto.setIdItemTrabalho(atribuicaoFluxoDto.getIdItemTrabalho());
					ocorrenciaMudancaDao.create(ocorrenciaMudancaDto);			    
				}
			}
		}
	}	
	
	@Override
	public void mapObjetoNegocio(Map<String, Object> map) throws Exception {
		RequisicaoMudancaDTO requisicaoMudancaDto = (RequisicaoMudancaDTO) objetoNegocioDto;
		RequisicaoMudancaDTO requisicaoAuxDto = new RequisicaoMudancaServiceEjb().restoreAll(requisicaoMudancaDto.getIdRequisicaoMudanca(), getTransacao());
		requisicaoMudancaDto.setNomeGrupoAtual(requisicaoAuxDto.getNomeGrupoAtual());
		requisicaoMudancaDto.setNomeGrupoNivel1(requisicaoAuxDto.getNomeGrupoNivel1());
		
		adicionaObjeto("requisicaoMudanca", requisicaoMudancaDto, map);
		if (usuarioDto != null)
			adicionaObjeto("usuario", usuarioDto, map);
		else if (requisicaoMudancaDto.getUsuarioDto() != null)   
			adicionaObjeto("usuario", requisicaoMudancaDto.getUsuarioDto(), map);

		adicionaObjeto("execucaoFluxo", this, map);
		adicionaObjeto("requisicaoMudancaService", new RequisicaoMudancaServiceEjb(), map);
	}
	
	public RequisicaoMudancaDTO getRequisicaoMudancaDto() {
		return (RequisicaoMudancaDTO) objetoNegocioDto;
	}
	
	@Override
	public List<TarefaFluxoDTO> recuperaTarefas(String loginUsuario) throws Exception {
		List<TarefaFluxoDTO> result = null;
		List<TarefaFluxoDTO> listTarefas = super.recuperaTarefas(loginUsuario);
		if (listTarefas != null) {
			result = new ArrayList();
			RequisicaoMudancaServiceEjb requisicaoService = new RequisicaoMudancaServiceEjb();
			ExecucaoMudancaDao execucaoMudancaDao = new ExecucaoMudancaDao(); 
			for (TarefaFluxoDTO tarefaDto : listTarefas) {
				ExecucaoMudancaDTO execucaoMudancaDto = execucaoMudancaDao.findByIdInstanciaFluxo(tarefaDto.getIdInstancia());
				if (execucaoMudancaDto != null && execucaoMudancaDto.getIdRequisicaoMudanca() != null) {
					RequisicaoMudancaDTO requisicaoMudancaDto = requisicaoService.restoreAll(execucaoMudancaDto.getIdRequisicaoMudanca(),null);
					if (requisicaoMudancaDto != null) {
						tarefaDto.setRequisicaoMudancaDto(requisicaoMudancaDto);
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
		
		RequisicaoMudancaDTO requisicaoAuxDto = new RequisicaoMudancaServiceEjb().restoreAll(getRequisicaoMudancaDto().getIdRequisicaoMudanca(), getTransacao());
		requisicaoAuxDto.setNomeContato(this.getRequisicaoMudancaDto().getNomeContato());
		if (requisicaoAuxDto.getEmailSolicitante() == null)
			return;

		String remetente = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.RemetenteNotificacoesSolicitacao, null);
		if (remetente == null)
			throw new LogicException(i18n_Message("citcorpore.comum.notficacaoEmailParametrizado"));
		
		requisicaoAuxDto.setNomeTarefa(getRequisicaoMudancaDto().getNomeTarefa());
		
		MensagemEmail mensagem = new MensagemEmail(idModeloEmail, new IDto[] {requisicaoAuxDto});
		try {
			mensagem.envia(requisicaoAuxDto.getEmailSolicitante(), remetente, remetente);
		} catch (Exception e) {
		}
	}

	private TipoMudancaDTO recuperaTipoMudanca() throws Exception {
		TipoMudancaDAO tipoMudancaDao = new TipoMudancaDAO();			
		setTransacaoDao(tipoMudancaDao);
		TipoMudancaDTO tipoMudancaDto = new TipoMudancaDTO();
		if(getRequisicaoMudancaDto().getIdTipoMudanca()!=null){
			tipoMudancaDto.setIdTipoMudanca(getRequisicaoMudancaDto().getIdTipoMudanca());
			tipoMudancaDto = (TipoMudancaDTO) tipoMudancaDao.restore(tipoMudancaDto);
		}
		
		if (tipoMudancaDto == null){
			
			 throw new LogicException(i18n_Message("requisicaoMudanca.categoriaMudancaNaoLocalizada"));
		}
		   
		return  tipoMudancaDto;
	}

	@Override
	public void encerra() throws Exception {
		RequisicaoMudancaDTO requisicaoMudancaDto = getRequisicaoMudancaDto();
		if (requisicaoMudancaDto == null)
		    throw new LogicException(i18n_Message("requisicaoMudanca.naoEncontrada"));
		
		if (requisicaoMudancaDto.encerrada())
			return;
		
		//if (!requisicaoMudancaDto.atendida() && !requisicaoMudancaDto.reclassificada())
		//    throw new Exception("Solicitação de serviço não permite encerramento");
	
		Collection<ExecucaoMudancaDTO> colExecucao = new ExecucaoMudancaDao().listByIdRequisicaoMudanca(getRequisicaoMudancaDto().getIdRequisicaoMudanca());
		if (colExecucao != null) {
			for (ExecucaoMudancaDTO execucaoDto : colExecucao) {
				InstanciaFluxo instanciaFluxo = new InstanciaFluxo(this, execucaoDto.getIdInstanciaFluxo());
				instanciaFluxo.encerra();
			}
		}
		if(!requisicaoMudancaDto.getStatus().equalsIgnoreCase(SituacaoRequisicaoMudanca.Cancelada.name()) && !requisicaoMudancaDto.getStatus().equalsIgnoreCase(SituacaoRequisicaoMudanca.Rejeitada.name())){
			requisicaoMudancaDto.setStatus(SituacaoRequisicaoMudanca.Concluida.name());
		}
		requisicaoMudancaDto.setDataHoraConclusao(UtilDatas.getDataHoraAtual());
		//calculaTempoCaptura(requisicaoMudancaDto);
		//calculaTempoAtendimento(requisicaoMudancaDto);
		//calculaTempoAtraso(requisicaoMudancaDto);
		RequisicaoMudancaDao requisicaoDao = new RequisicaoMudancaDao();
		setTransacaoDao(requisicaoDao);
		requisicaoDao.updateNotNull(requisicaoMudancaDto);
		
		OcorrenciaMudancaDao ocorrenciaMudancaDao = new OcorrenciaMudancaDao();
		setTransacaoDao(ocorrenciaMudancaDao);
		OcorrenciaMudancaDTO ocorrenciaMudancaDto = new OcorrenciaMudancaDTO();
		ocorrenciaMudancaDto.setIdRequisicaoMudanca(getRequisicaoMudancaDto().getIdRequisicaoMudanca());
		ocorrenciaMudancaDto.setDataregistro(UtilDatas.getDataAtual());
		ocorrenciaMudancaDto.setHoraregistro(UtilDatas.formatHoraFormatadaStr(UtilDatas.getHoraAtual()));
		ocorrenciaMudancaDto.setTempoGasto(0);
		ocorrenciaMudancaDto.setDescricao(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Encerramento.getDescricao());
		ocorrenciaMudancaDto.setDataInicio(UtilDatas.getDataAtual());
		ocorrenciaMudancaDto.setDataFim(UtilDatas.getDataAtual());
		ocorrenciaMudancaDto.setRegistradopor("Automático");
		ocorrenciaMudancaDto.setDadosMudanca(new Gson().toJson(getRequisicaoMudancaDto()));
		ocorrenciaMudancaDto.setOrigem(br.com.centralit.citcorpore.util.Enumerados.OrigemOcorrencia.OUTROS.getSigla().toString());
		ocorrenciaMudancaDto.setCategoria(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Encerramento.getSigla());
		ocorrenciaMudancaDao.create(ocorrenciaMudancaDto);			    
		
		if (getRequisicaoMudancaDto().getEnviaEmailFinalizacao() != null && getRequisicaoMudancaDto().getEnviaEmailFinalizacao().equalsIgnoreCase("S")) {
			TipoMudancaDTO tipoMudancaDto = recuperaTipoMudanca();
			enviaEmail(tipoMudancaDto.getIdModeloEmailFinalizacao());
		}
	}

	@Override
	public void reabre(String loginUsuario) throws Exception {
		RequisicaoMudancaDTO requisicaoMudancaDto = getRequisicaoMudancaDto();
		if (requisicaoMudancaDto == null)
			throw new LogicException(i18n_Message("requisicaoMudanca.naoEncontrada"));

		if (!requisicaoMudancaDto.encerrada())
			throw new LogicException(i18n_Message("requisicaoMudanca.naoPermiteReabertura"));

		usuarioDto = new UsuarioDao().restoreByLogin(loginUsuario);
		int seqReabertura = 1;
		ReaberturaMudancaDao reaberturaMudancaDao = new ReaberturaMudancaDao();
		setTransacaoDao(reaberturaMudancaDao);
		Collection colReabertura = reaberturaMudancaDao.findByIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
		if (colReabertura != null)
			seqReabertura = colReabertura.size() + 1;

		ReaberturaMudancaDTO reaberturaMudancaDto = new ReaberturaMudancaDTO();
		reaberturaMudancaDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
		reaberturaMudancaDto.setSeqReabertura(seqReabertura);
		reaberturaMudancaDto.setIdResponsavel(usuarioDto.getIdUsuario());
		reaberturaMudancaDto.setDataHora(UtilDatas.getDataHoraAtual());
		reaberturaMudancaDao.create(reaberturaMudancaDto);

		requisicaoMudancaDto.setStatus(SituacaoRequisicaoMudanca.Reaberta.name());
		requisicaoMudancaDto.setSeqReabertura(new Integer(seqReabertura));
		RequisicaoMudancaDao requisicaoDao = new RequisicaoMudancaDao();
		setTransacaoDao(requisicaoDao);
		requisicaoDao.update(requisicaoMudancaDto);

		OcorrenciaMudancaDao ocorrenciaMudancaDao = new OcorrenciaMudancaDao();
		setTransacaoDao(ocorrenciaMudancaDao);
		OcorrenciaMudancaDTO ocorrenciaMudancaDto = new OcorrenciaMudancaDTO();
		ocorrenciaMudancaDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
		ocorrenciaMudancaDto.setDataregistro(UtilDatas.getDataAtual());
		ocorrenciaMudancaDto.setHoraregistro(UtilDatas.formatHoraFormatadaStr(UtilDatas.getHoraAtual()));
		ocorrenciaMudancaDto.setTempoGasto(0);
		ocorrenciaMudancaDto.setDescricao(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Reabertura.getDescricao());
		ocorrenciaMudancaDto.setDataInicio(UtilDatas.getDataAtual());
		ocorrenciaMudancaDto.setDataFim(UtilDatas.getDataAtual());
		ocorrenciaMudancaDto.setRegistradopor(usuarioDto.getLogin());
		ocorrenciaMudancaDto.setDadosMudanca(new Gson().toJson(getRequisicaoMudancaDto()));
		ocorrenciaMudancaDto.setOrigem(br.com.centralit.citcorpore.util.Enumerados.OrigemOcorrencia.OUTROS.getSigla().toString());
		ocorrenciaMudancaDto.setCategoria(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Reabertura.getSigla());
		ocorrenciaMudancaDao.create(ocorrenciaMudancaDto);			    
		
		inicia();
	}

	@Override
	public void suspende(String loginUsuario) throws Exception {
		RequisicaoMudancaDTO requisicaoMudancaDto = getRequisicaoMudancaDto();
		if (requisicaoMudancaDto == null)
			throw new LogicException(i18n_Message("requisicaoMudanca.naoEncontrada"));

		if (!requisicaoMudancaDto.emAtendimento())
			throw new LogicException(i18n_Message("requisicaoMudanca.naoPermiteSuspensao"));

		Timestamp tsAtual = UtilDatas.getDataHoraAtual();
		Timestamp tsInicial = requisicaoMudancaDto.getDataHoraInicio();
		if (requisicaoMudancaDto.getDataHoraReativacao() != null)
			tsInicial = requisicaoMudancaDto.getDataHoraReativacao();
		TipoMudancaDTO tipoMudancaDto = recuperaTipoMudanca();
		if(tipoMudancaDto.getIdCalendario()!=null){
			requisicaoMudancaDto.setIdCalendario(tipoMudancaDto.getIdCalendario());
		}
		CalculoJornadaDTO calculoDto = new CalculoJornadaDTO(requisicaoMudancaDto.getIdCalendario(), tsInicial);
		calculoDto = new CalendarioServiceEjb().calculaPrazoDecorrido(calculoDto, tsAtual, null);
		
		RequisicaoMudancaDao requisicaoDao = new RequisicaoMudancaDao();
		setTransacaoDao(requisicaoDao);

		requisicaoMudancaDto.setStatus(SituacaoRequisicaoMudanca.Suspensa.name());
		if (requisicaoMudancaDto.getTempoDecorridoHH() == null){
		    requisicaoMudancaDto.setTempoDecorridoHH(0);
		}
		if (requisicaoMudancaDto.getTempoDecorridoMM() == null){
		    requisicaoMudancaDto.setTempoDecorridoMM(0);
		}		
		requisicaoMudancaDto.setTempoDecorridoHH(new Integer(requisicaoMudancaDto.getTempoDecorridoHH().intValue() + calculoDto.getTempoDecorridoHH().intValue()));
		requisicaoMudancaDto.setTempoDecorridoMM(new Integer(requisicaoMudancaDto.getTempoDecorridoMM().intValue() + calculoDto.getTempoDecorridoMM().intValue()));
		requisicaoMudancaDto.setDataHoraSuspensao(tsAtual);
		requisicaoMudancaDto.setDataHoraReativacao(null);
		requisicaoDao.update(requisicaoMudancaDto);

		OcorrenciaMudancaDao ocorrenciaMudancaDao = new OcorrenciaMudancaDao();
		setTransacaoDao(ocorrenciaMudancaDao);
		OcorrenciaMudancaDTO ocorrenciaMudancaDto = new OcorrenciaMudancaDTO();
		ocorrenciaMudancaDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
		ocorrenciaMudancaDto.setDataregistro(new Date(tsAtual.getTime()));
		ocorrenciaMudancaDto.setHoraregistro(UtilDatas.formatHoraFormatadaStr(tsAtual));
		ocorrenciaMudancaDto.setTempoGasto(0);
		ocorrenciaMudancaDto.setDescricao(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Suspensao.getDescricao());
		ocorrenciaMudancaDto.setDataInicio(UtilDatas.getDataAtual());
		ocorrenciaMudancaDto.setDataFim(UtilDatas.getDataAtual());
		ocorrenciaMudancaDto.setRegistradopor(loginUsuario);
		ocorrenciaMudancaDto.setDadosMudanca(new Gson().toJson(getRequisicaoMudancaDto()));
		ocorrenciaMudancaDto.setIdJustificativa(requisicaoMudancaDto.getIdJustificativa());
		ocorrenciaMudancaDto.setComplementoJustificativa(requisicaoMudancaDto.getComplementoJustificativa());
		ocorrenciaMudancaDto.setOrigem(br.com.centralit.citcorpore.util.Enumerados.OrigemOcorrencia.OUTROS.getSigla().toString());
		ocorrenciaMudancaDto.setCategoria(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Suspensao.getSigla());
		ocorrenciaMudancaDao.create(ocorrenciaMudancaDto);			    
	}

	@Override
	public void reativa(String loginUsuario) throws Exception {
		RequisicaoMudancaDTO requisicaoMudancaDto = getRequisicaoMudancaDto();
		if (requisicaoMudancaDto == null)
			throw new LogicException(i18n_Message("requisicaoMudanca.naoEncontrada"));

		if (!requisicaoMudancaDto.suspensa())
			throw new LogicException(i18n_Message("requisicaoMudanca.naoPermiteReativacao"));

		Timestamp tsAtual = UtilDatas.getDataHoraAtual();
		double prazo = requisicaoMudancaDto.getPrazoHH() + new Double(requisicaoMudancaDto.getPrazoMM()).doubleValue() / 60;
		double tempo = requisicaoMudancaDto.getTempoDecorridoHH() + new Double(requisicaoMudancaDto.getTempoDecorridoMM()).doubleValue() / 60;
		prazo = prazo - tempo;
		if (prazo > 0){
		    CalculoJornadaDTO calculoDto = new CalculoJornadaDTO(requisicaoMudancaDto.getIdCalendario(), tsAtual, Util.getHora(prazo), Util.getMinuto(prazo));
		
		    calculoDto = new CalendarioServiceEjb().calculaDataHoraFinal(calculoDto, false, null);
		    requisicaoMudancaDto.setDataHoraTermino(calculoDto.getDataHoraFinal());
		}
		RequisicaoMudancaDao requisicaoDao = new RequisicaoMudancaDao();
		setTransacaoDao(requisicaoDao);

		requisicaoMudancaDto.setStatus(SituacaoRequisicaoMudanca.EmExecucao.name());
		requisicaoMudancaDto.setDataHoraSuspensao(null);
		requisicaoMudancaDto.setDataHoraReativacao(tsAtual);
		requisicaoDao.update(requisicaoMudancaDto);

		OcorrenciaMudancaDao ocorrenciaMudancaDao = new OcorrenciaMudancaDao();
		setTransacaoDao(ocorrenciaMudancaDao);
		OcorrenciaMudancaDTO ocorrenciaMudancaDto = new OcorrenciaMudancaDTO();
		ocorrenciaMudancaDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
		ocorrenciaMudancaDto.setDataregistro(new Date(tsAtual.getTime()));
		ocorrenciaMudancaDto.setHoraregistro(UtilDatas.formatHoraFormatadaStr(tsAtual));
		ocorrenciaMudancaDto.setTempoGasto(0);
		ocorrenciaMudancaDto.setDescricao(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Reativacao.getDescricao());
		ocorrenciaMudancaDto.setDataInicio(UtilDatas.getDataAtual());
		ocorrenciaMudancaDto.setDataFim(UtilDatas.getDataAtual());
		ocorrenciaMudancaDto.setRegistradopor(loginUsuario);
		ocorrenciaMudancaDto.setDadosMudanca(new Gson().toJson(getRequisicaoMudancaDto()));
		ocorrenciaMudancaDto.setOrigem(br.com.centralit.citcorpore.util.Enumerados.OrigemOcorrencia.OUTROS.getSigla().toString());
		ocorrenciaMudancaDto.setCategoria(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Reativacao.getSigla());
		ocorrenciaMudancaDao.create(ocorrenciaMudancaDto);			    
	}
	
	private Integer getIdCalendario(RequisicaoMudancaDTO requisicaoMudancaDto) throws Exception {
		Integer idCalendario = requisicaoMudancaDto.getIdCalendario();
		if (requisicaoMudancaDto.getIdCalendario() == null) {
			TipoMudancaDTO tipoMudancaDto = recuperaTipoMudanca();
			idCalendario = tipoMudancaDto.getIdCalendario();
		}
		return idCalendario;
	}
	
	public void calculaTempoAtendimento(RequisicaoMudancaDTO requisicaoMudancaDto) throws Exception {
		Integer idCalendario = getIdCalendario(requisicaoMudancaDto);
		
		Timestamp tsAtual = UtilDatas.getDataHoraAtual();
		if (requisicaoMudancaDto.getStatus().equalsIgnoreCase(SituacaoRequisicaoMudanca.Concluida.name()))
			tsAtual = requisicaoMudancaDto.getDataHoraConclusao();
		
		Timestamp tsInicial = requisicaoMudancaDto.getDataHoraInicio();
		if (requisicaoMudancaDto.getDataHoraReativacao() != null)
			tsInicial = requisicaoMudancaDto.getDataHoraReativacao();
		
		CalculoJornadaDTO calculoDto = new CalculoJornadaDTO(idCalendario, tsInicial);
		calculoDto = new CalendarioServiceEjb().calculaPrazoDecorrido(calculoDto, tsAtual, null);
		
		if (requisicaoMudancaDto.getTempoDecorridoHH() == null){
		    requisicaoMudancaDto.setTempoDecorridoHH(0);
		}
		if (requisicaoMudancaDto.getTempoDecorridoMM() == null){
		    requisicaoMudancaDto.setTempoDecorridoMM(0);
		}		

		requisicaoMudancaDto.setTempoAtendimentoHH(new Integer(requisicaoMudancaDto.getTempoDecorridoHH().intValue() + calculoDto.getTempoDecorridoHH().intValue()));
		requisicaoMudancaDto.setTempoAtendimentoMM(new Integer(requisicaoMudancaDto.getTempoDecorridoMM().intValue() + calculoDto.getTempoDecorridoMM().intValue()));
	}	

	public void calculaTempoCaptura(RequisicaoMudancaDTO requisicaoMudancaDto) throws Exception {
		requisicaoMudancaDto.setTempoCapturaHH(0);
		requisicaoMudancaDto.setTempoCapturaMM(0);
		
		if (requisicaoMudancaDto.getDataHoraCaptura() == null)
			return;
		
		Integer idCalendario = getIdCalendario(requisicaoMudancaDto);
		
		CalculoJornadaDTO calculoDto = new CalculoJornadaDTO(idCalendario, requisicaoMudancaDto.getDataHoraInicio());
		calculoDto = new CalendarioServiceEjb().calculaPrazoDecorrido(calculoDto, requisicaoMudancaDto.getDataHoraCaptura(), null);
		
		requisicaoMudancaDto.setTempoCapturaHH(calculoDto.getTempoDecorridoHH().intValue());
		requisicaoMudancaDto.setTempoCapturaMM(calculoDto.getTempoDecorridoMM().intValue());
	}	
	
	public void calculaTempoAtraso(RequisicaoMudancaDTO requisicaoMudancaDto) throws Exception {
		requisicaoMudancaDto.setTempoAtrasoHH(0);
		requisicaoMudancaDto.setTempoAtrasoMM(0);
		if (requisicaoMudancaDto.getDataHoraTermino() != null) {
			Timestamp dataHoraLimite = requisicaoMudancaDto.getDataHoraTermino();
			Timestamp dataHoraComparacao = UtilDatas.getDataHoraAtual();
			if (requisicaoMudancaDto.encerrada())
				dataHoraComparacao = requisicaoMudancaDto.getDataHoraConclusao();
			if (dataHoraComparacao.compareTo(dataHoraLimite) > 0) {
				long atrasoSLA = UtilDatas.calculaDiferencaTempoEmMilisegundos(dataHoraComparacao, dataHoraLimite) / 1000;

				String hora = Util.getHoraStr(new Double(atrasoSLA) / 3600);
				int tam = hora.length();
				requisicaoMudancaDto.setTempoAtrasoHH(new Integer(hora.substring(0, tam - 2)));
				requisicaoMudancaDto.setTempoAtrasoMM(new Integer(hora.substring(tam - 2, tam)));			
			}
		}
	}

    @Override
    public void executaEvento(EventoFluxoDTO eventoFluxoDto) throws Exception {
        ExecucaoMudancaDao execucaoMudancaDao = new ExecucaoMudancaDao();
        ExecucaoMudancaDTO execucaoMudancaDto = execucaoMudancaDao.findByIdInstanciaFluxo(eventoFluxoDto.getIdInstancia());
        if (execucaoMudancaDto == null){
        	System.out.println("Execução mudança do evento "+eventoFluxoDto.getIdItemTrabalho()+" não encontrada");
            throw new LogicException(i18n_Message("requisicaoMudanca.eventoNaoEncontrado"));
        }       
        RequisicaoMudancaDao requisicaoMudancaDao = new RequisicaoMudancaDao();
        RequisicaoMudancaDTO requisicaoMudancaDto = new RequisicaoMudancaDTO();
        requisicaoMudancaDto.setIdRequisicaoMudanca(execucaoMudancaDto.getIdRequisicaoMudanca());
        requisicaoMudancaDto = (RequisicaoMudancaDTO) requisicaoMudancaDao.restore(requisicaoMudancaDto);
        if (requisicaoMudancaDto == null){
        	System.out.println("Execução mudança do evento "+eventoFluxoDto.getIdItemTrabalho()+" não encontrada");
            throw new LogicException(i18n_Message("requisicaoMudanca.eventoNaoEncontrado"));
        }
        
        TransactionControler tc = new TransactionControlerImpl(execucaoMudancaDao.getAliasDB());
        setTransacao(tc);
        setObjetoNegocioDto(requisicaoMudancaDto);
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
		GrupoEmailService grupoEmailService = (GrupoEmailService) ServiceLocator.getInstance().getService(GrupoEmailService.class, null);

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
			throw new LogicException(i18n_Message("requisicaoMudanca.remetenteNaoParametrizado"));

		RequisicaoMudancaDTO requisicaoMudancaDTO = new RequisicaoMudancaServiceEjb().restoreAll(this.getRequisicaoMudancaDto().getIdRequisicaoMudanca(), getTransacao());
		if (requisicaoMudancaDTO == null) {
			return;
		}
		requisicaoMudancaDTO.setNomeTarefa(getRequisicaoMudancaDto().getNomeTarefa());

		try {

			for (String email : emails) {
				int posArroba = email.indexOf("@");
				if (posArroba > 0 && email.substring(posArroba).contains(".")) {
					try {
						mensagem = new MensagemEmail(idModeloEmail, new IDto[] { requisicaoMudancaDTO });
						mensagem.envia(email, remetente, remetente);
					} catch (Exception e) {
					}
				}
			}
			
		} catch (Exception e) {
		}
	}
	
	
	//envia e-mail para todos do grupo em um agendamento de reunião
	public void enviaEmailReuniaoGrupo(Integer idModeloEmail, Integer idGrupoDestino, Integer idRequisicaoMudanca, Integer idReuniaoRequisicaoMudanca) throws Exception {
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
			emails = (List<String>) grupoService.listarPessoasEmailPorGrupo(idGrupoDestino);
		} catch (Exception e) {
			return;
		}

		if (emails == null || emails.isEmpty()) {
			return;
		}

		String remetente = ParametroUtil.getValor(ParametroSistema.RemetenteNotificacoesSolicitacao, getTransacao(), null);
		if (remetente == null)
			throw new LogicException(i18n_Message("requisicaoMudanca.remetenteNaoParametrizado"));

		RequisicaoMudancaDTO requisicaoMudancaDTO = new RequisicaoMudancaServiceEjb().restoreAllReuniao(idRequisicaoMudanca, idReuniaoRequisicaoMudanca, getTransacao());
		if (requisicaoMudancaDTO == null) {
			return;
		}

		Object[] emailsArray = new Object[(emails.size()/2)];
		int j = 0;
		for (int i = 0; i < emails.size(); i++) {
			if(emails.get(i).contains("@")){
				continue;
			} else{
				emailsArray[j] = emails.get(i);
				j++;
			}
		}
		
		try {
			int i = 0;
			for (String email : emails) {
				int posArroba = email.indexOf("@");
				if (posArroba > 0 && email.substring(posArroba).contains(".")) {
					try {
						if(StringUtils.isNotBlank(emailsArray[i].toString())){
							String nomeContato = emailsArray[i].toString();
							requisicaoMudancaDTO.setNomeContato(nomeContato);
						}
						mensagem = new MensagemEmail(idModeloEmail, new IDto[] { requisicaoMudancaDTO });						
						mensagem.envia(email, remetente, remetente);
						i++;
					} catch (Exception e) {
						// faz nada
					}
				}
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
		
		empregadoDto.setIdEmpregado(this.getRequisicaoMudancaDto().getUsuarioDto().getIdEmpregado());
		
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
			throw new LogicException(i18n_Message("requisicaoMudanca.remetenteNaoParametrizado"));

		RequisicaoMudancaDTO requisicaoMudancaDTO = new RequisicaoMudancaServiceEjb().restoreAll(this.getRequisicaoMudancaDto().getIdRequisicaoMudanca(), getTransacao());
		if (requisicaoMudancaDTO == null) {
			return;
		}
		requisicaoMudancaDTO.setNomeTarefa(getRequisicaoMudancaDto().getNomeTarefa());

		try {
			// EmpregadoDTO aux = null;
				int posArroba = email.indexOf("@");
				if (posArroba > 0 && email.substring(posArroba).contains(".")) {
					try {
						requisicaoMudancaDTO.setNomeContato(empregadoDto.getNome());
						mensagem = new MensagemEmail(idModeloEmail, new IDto[] { requisicaoMudancaDTO });

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
	
}
