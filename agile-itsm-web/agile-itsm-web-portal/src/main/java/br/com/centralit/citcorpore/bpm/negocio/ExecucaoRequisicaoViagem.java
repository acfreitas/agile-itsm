package br.com.centralit.citcorpore.bpm.negocio;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;

import br.com.centralit.bpm.dto.AtribuicaoFluxoDTO;
import br.com.centralit.bpm.dto.EventoFluxoDTO;
import br.com.centralit.bpm.dto.FluxoDTO;
import br.com.centralit.bpm.dto.TarefaFluxoDTO;
import br.com.centralit.bpm.integracao.AtribuicaoFluxoDao;
import br.com.centralit.bpm.integracao.TarefaFluxoDao;
import br.com.centralit.bpm.negocio.InstanciaFluxo;
import br.com.centralit.bpm.negocio.Tarefa;
import br.com.centralit.citcorpore.bean.AlcadaDTO;
import br.com.centralit.citcorpore.bean.AlcadaProcessoNegocioDTO;
import br.com.centralit.citcorpore.bean.CentroResultadoDTO;
import br.com.centralit.citcorpore.bean.CidadesDTO;
import br.com.centralit.citcorpore.bean.DespesaViagemDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.IntegranteViagemDTO;
import br.com.centralit.citcorpore.bean.ModeloEmailDTO;
import br.com.centralit.citcorpore.bean.ParecerDTO;
import br.com.centralit.citcorpore.bean.PrestacaoContasViagemDTO;
import br.com.centralit.citcorpore.bean.ProjetoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoViagemDTO;
import br.com.centralit.citcorpore.bean.RoteiroViagemDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.CentroResultadoDao;
import br.com.centralit.citcorpore.integracao.CidadesDao;
import br.com.centralit.citcorpore.integracao.DespesaViagemDAO;
import br.com.centralit.citcorpore.integracao.EmpregadoDao;
import br.com.centralit.citcorpore.integracao.IntegranteViagemDao;
import br.com.centralit.citcorpore.integracao.ModeloEmailDao;
import br.com.centralit.citcorpore.integracao.ParecerDao;
import br.com.centralit.citcorpore.integracao.PrestacaoContasViagemDao;
import br.com.centralit.citcorpore.integracao.ProjetoDao;
import br.com.centralit.citcorpore.integracao.RequisicaoViagemDAO;
import br.com.centralit.citcorpore.integracao.RoteiroViagemDAO;
import br.com.centralit.citcorpore.integracao.SolicitacaoServicoDao;
import br.com.centralit.citcorpore.integracao.UsuarioDao;
import br.com.centralit.citcorpore.mail.MensagemEmail;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.IntegranteViagemService;
import br.com.centralit.citcorpore.negocio.RequisicaoViagemService;
import br.com.centralit.citcorpore.negocio.RoteiroViagemService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoServiceEjb;
import br.com.centralit.citcorpore.negocio.alcada.AlcadaRequisicaoViagem;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Reflexao;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

public class ExecucaoRequisicaoViagem extends ExecucaoSolicitacao {

	public ExecucaoRequisicaoViagem() {
		super();
	}

	public String i18n_Message(UsuarioDTO usuario, String key) {
		if (usuario != null) {
			if (UtilI18N.internacionaliza(usuario.getLocale(), key) != null) {
				return UtilI18N.internacionaliza(usuario.getLocale(), key);
			}
			return key;
		}
		return key;
	}

	@Override
	public InstanciaFluxo inicia() throws Exception {
		return super.inicia();
	}

	@Override
	public InstanciaFluxo inicia(FluxoDTO fluxoDto, Integer idFase) throws Exception {

		String idGrupo = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_REQ_VIAGEM_EXECUCAO, null);
		if (idGrupo == null || idGrupo.trim().equals(""))
			throw new Exception(i18n_Message("citcorpore.comum.grupoPadraoNaoParametrizado"));
		getSolicitacaoServicoDto().setIdGrupoAtual(new Integer(idGrupo.trim()));
		return super.inicia(fluxoDto, idFase);

	}

	@Override
	public void mapObjetoNegocio(Map<String, Object> map) throws Exception {
		super.mapObjetoNegocio(map);
	}

	@Override
	public void executaEvento(EventoFluxoDTO eventoFluxoDto) throws Exception {
		super.executaEvento(eventoFluxoDto);
	}

	@Override
	public void complementaInformacoesEmail(SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception {
		super.complementaInformacoesEmail(solicitacaoServicoDto);

		RequisicaoViagemDTO requisicaoViagemDto = recuperaRequisicaoViagem();
		CentroResultadoDTO centroResultado = new CentroResultadoDTO();
		ProjetoDTO projetoDto = new ProjetoDTO();
		CidadesDTO cidade = new CidadesDTO();
		StringBuilder strItens = new StringBuilder();

		if (requisicaoViagemDto != null) {
			centroResultado = recuperaCentroCusto(requisicaoViagemDto);
			projetoDto = recuperaProjeto(requisicaoViagemDto);
			strItens.append("<b>Data Inicio: </b>" + UtilDatas.dateToSTR(requisicaoViagemDto.getDataInicioViagem()) + "<br>");
			strItens.append("<b>Data Fim: </b>" + UtilDatas.dateToSTR(requisicaoViagemDto.getDataFimViagem()) + "<br>");
			if (requisicaoViagemDto.getIdCidadeOrigem() != null) {
				cidade = recuperaCidade(requisicaoViagemDto.getIdCidadeOrigem());
				strItens.append("<b>Cidade Origem: </b>" + cidade.getNomeCidade() + "<br>");
			}
			if (requisicaoViagemDto.getIdCidadeDestino() != null) {
				cidade = recuperaCidade(requisicaoViagemDto.getIdCidadeDestino());
				strItens.append("<b>Cidade Destino: </b>" + cidade.getNomeCidade() + "<br>");
			}
			if (centroResultado != null) {
				strItens.append("<b>Centro de custo: </b>" + centroResultado.getNomeCentroResultado() + "<br>");
			}
			if (projetoDto != null) {
				strItens.append("<b>Projeto: </b>" + projetoDto.getNomeProjeto() + "<br>");
			}
			strItens.append("<b>Motivo: </b><br>");
			strItens.append("" + requisicaoViagemDto.getDescricaoMotivo() + "");
			solicitacaoServicoDto.setInformacoesComplementaresHTML(strItens.toString());
			
			if(requisicaoViagemDto.getEstado().equalsIgnoreCase(RequisicaoViagemDTO.getAguardandoAprovacao())){
				solicitacaoServicoDto.setTituloEmail(this.retornaMenorPrazoContacao(solicitacaoServicoDto.getIdSolicitacaoServico()));
			}
		}
	}

	public RequisicaoViagemDTO recuperaRequisicaoViagem() throws Exception {
		RequisicaoViagemDAO requisicaoViagemDao = new RequisicaoViagemDAO();
		setTransacaoDao(requisicaoViagemDao);
		SolicitacaoServicoDTO solicitacaoDto = getSolicitacaoServicoDto();
		RequisicaoViagemDTO requisicaoViagemDto = new RequisicaoViagemDTO();
		requisicaoViagemDto.setIdSolicitacaoServico(solicitacaoDto.getIdSolicitacaoServico());
		requisicaoViagemDto = (RequisicaoViagemDTO) requisicaoViagemDao.restore(requisicaoViagemDto);
		Reflexao.copyPropertyValues(solicitacaoDto, requisicaoViagemDto);
		return requisicaoViagemDto;
	}
	
	public String retornaMenorPrazoContacao(Integer idSolicitacaoServico) throws Exception{
		DespesaViagemDAO despesaViagemDAO = new DespesaViagemDAO();
		List<DespesaViagemDTO> listaDespesa = (List<DespesaViagemDTO>) despesaViagemDAO.findDespesaViagemByIdSolicitacao(idSolicitacaoServico);
		return  UtilDatas.formatTimestamp(listaDespesa.get(0).getValidade());
	}

	public CentroResultadoDTO recuperaCentroCusto(RequisicaoViagemDTO requisicaoViagemDto) throws Exception {
		CentroResultadoDTO centroCustoDto = new CentroResultadoDTO();
		centroCustoDto.setIdCentroResultado(requisicaoViagemDto.getIdCentroCusto());
		return (CentroResultadoDTO) new CentroResultadoDao().restore(centroCustoDto);
	}

	public ProjetoDTO recuperaProjeto(RequisicaoViagemDTO requisicaoViagemDto) throws Exception {
		ProjetoDTO projetoDto = new ProjetoDTO();
		if (requisicaoViagemDto.getIdProjeto() != null) {
			projetoDto.setIdProjeto(requisicaoViagemDto.getIdProjeto());
			return (ProjetoDTO) new ProjetoDao().restore(projetoDto);

		}
		return null;

	}

	public CidadesDTO recuperaCidade(Integer idCidade) throws Exception {
		CidadesDTO cidadeDto = new CidadesDTO();
		if (idCidade != null) {
			cidadeDto.setIdCidade(idCidade);
			return (CidadesDTO) new CidadesDao().restore(cidadeDto);
		}
		return null;
	}

	public AlcadaDTO recuperaAlcada(RequisicaoViagemDTO requisicaoViagemDto) throws Exception {
		return new AlcadaRequisicaoViagem().determinaAlcada(requisicaoViagemDto, recuperaCentroCusto(requisicaoViagemDto), getTransacao());
	}

	public StringBuilder recuperaLoginAutorizadores() throws Exception {
		RequisicaoViagemDTO requisicaoViagemDto = recuperaRequisicaoViagem();
		return recuperaLoginAutorizadores(requisicaoViagemDto);
	}

	public StringBuilder recuperaLoginAutorizadores(RequisicaoViagemDTO requisicaoViagemDto) throws Exception {
		StringBuilder result = new StringBuilder();
		AlcadaDTO alcadaDto = recuperaAlcada(requisicaoViagemDto);
		if (alcadaDto != null && alcadaDto.getColResponsaveis() != null) {
			int i = 0;
			UsuarioDao usuarioDao = new UsuarioDao();
			for (EmpregadoDTO empregadoDto : alcadaDto.getColResponsaveis()) {
				UsuarioDTO usuarioDto = usuarioDao.restoreAtivoByIdEmpregado(empregadoDto.getIdEmpregado());
				if (usuarioDto != null) {
					if (i > 0)
						result.append(";");
					result.append(usuarioDto.getLogin());
					i++;
				}
			}
		}
		if (result.length() == 0)
			throw new LogicException("Não foi encontrado nenhum autorizador da requisição");
		return result;
	}

	public boolean exigeAutorizacao() throws Exception {
		RequisicaoViagemDTO requisicaoViagemDto = this.recuperaRequisicaoViagem();
		return exigeAutorizacao(requisicaoViagemDto);		
	}
	
	public void alteraEstadoCompra() throws Exception {
		RequisicaoViagemDTO requisicaoViagemDto = this.recuperaRequisicaoViagem();
		RequisicaoViagemDAO requisicaoViagemDAO = new RequisicaoViagemDAO();
		requisicaoViagemDAO.setTransactionControler(getTransacao());
		IntegranteViagemDao integranteViagemDao = new IntegranteViagemDao();
		integranteViagemDao.setTransactionControler(getTransacao());
		Collection<IntegranteViagemDTO> colIntegrantes =  integranteViagemDao.recuperaIntegrantesViagemByIdSolicitacaoEstado(requisicaoViagemDto.getIdSolicitacaoServico(), RequisicaoViagemDTO.AGUARDANDO_PLANEJAMENTO);

		if(colIntegrantes != null && colIntegrantes.size() > 0){
			for(IntegranteViagemDTO integranteViagemDTO : colIntegrantes){
				integranteViagemDTO.setEstado(RequisicaoViagemDTO.AGUARDANDO_COMPRAS);
				integranteViagemDao.updateNotNull(integranteViagemDTO);
			}
		}	
		
		requisicaoViagemDto = (RequisicaoViagemDTO) requisicaoViagemDAO.restore(requisicaoViagemDto);
		requisicaoViagemDto.setEstado(RequisicaoViagemDTO.AGUARDANDO_COMPRAS);
		requisicaoViagemDAO.updateNotNull(requisicaoViagemDto);
	}
	
	
		 

	/**
	 * Metodo que valida a exigencia da autorização da requisição de viagem
	 * 
	 * @param requisicaoViagemDto
	 * @return
	 * @throws Exception
	 */
	public boolean exigeAutorizacao(RequisicaoViagemDTO requisicaoViagemDto) throws Exception {
		DespesaViagemDAO despesaViagemDAO = new DespesaViagemDAO();
		IntegranteViagemDao integranteViagemDao = new IntegranteViagemDao();
		RequisicaoViagemDTO requisicaoViagem = this.recuperaRequisicaoViagem();
		
		Collection<IntegranteViagemDTO> colIntegrantes = integranteViagemDao.recuperaIntegrantesViagemByIdSolicitacaoEstado(requisicaoViagem.getIdSolicitacaoServico(), RequisicaoViagemDTO.AGUARDANDO_PLANEJAMENTO);
		if(colIntegrantes != null && !colIntegrantes.isEmpty()){
			for(IntegranteViagemDTO dto: colIntegrantes){
				if(dto.getRemarcacao() != null && dto.getRemarcacao().equalsIgnoreCase("S")){
					Double vlrTotalNovo = despesaViagemDAO.buscaValorTotalViagem(requisicaoViagem.getIdSolicitacaoServico());
					
					Double vlrTotalAntigo = despesaViagemDAO.buscaValorViagemHistorico(requisicaoViagem.getIdSolicitacaoServico());
					
					String valorAlcadaSemAutorizacao = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.VALOR_ALCADA_SEM_NESSIDADE_AUTORIZACAO, "30");
					if(valorAlcadaSemAutorizacao == null)
						throw new LogicException(i18n_Message("requisicaoViagem.percentualDeAceitacaoParaRemarcacaoDeViagem"));
					
					Double percentualPermissao = new Double(valorAlcadaSemAutorizacao);
					
					if(vlrTotalNovo > vlrTotalAntigo){
						Double vlrTotal = vlrTotalNovo - vlrTotalAntigo;
						Double percentualRemarcado = vlrTotal * 100 / vlrTotalAntigo;
						
						if(percentualRemarcado > percentualPermissao)
							return true;
						else
							return false;
					}else{
						return false;
					}
					
				}
			}
		}
		
		AlcadaDTO alcadaDto = recuperaAlcada(requisicaoViagem);
		boolean result = false;
		
		if (alcadaDto != null) {
			if (alcadaDto.getColResponsaveis() != null) {
				result = true;
				for (EmpregadoDTO empregadoDto : alcadaDto.getColResponsaveis()) {
					if (getSolicitacaoServicoDto().getIdSolicitante().intValue() == empregadoDto.getIdEmpregado().intValue()) {
						result = false;
					}
				}
			}
		}
		return result;		
	}
	

	public ExecucaoRequisicaoViagem(RequisicaoViagemDTO requisicaoViagemDto, TransactionControler tc) {
		super(requisicaoViagemDto, tc);
	}

	public ExecucaoRequisicaoViagem(TransactionControler tc) {
		super(tc);
	}

	public boolean requisicaoAutorizadaSim() throws Exception {
		RequisicaoViagemDTO requisicaoDto = recuperaRequisicaoViagem();
		boolean autorizado = false;
		
		if(!requisicaoDto.getEstado().equalsIgnoreCase(Enumerados.SituacaoSolicitacaoServico.Cancelada.name())) {
			ParecerDTO parecerDto = new ParecerDTO();
			
			if (requisicaoDto.getIdAprovacao() != null) {
				parecerDto = recuperaParecer(requisicaoDto);
				if (parecerDto != null) {
					requisicaoDto.setAutorizado(parecerDto.getAprovado());
					autorizado = requisicaoDto.getAutorizado() != null && requisicaoDto.getAutorizado().equalsIgnoreCase("S");
				}
			}
		}
		
		return autorizado;
	}
	
	public boolean requisicaoAutorizadaNao() throws Exception {
		RequisicaoViagemDTO requisicaoDto = recuperaRequisicaoViagem();
		boolean autorizado = false;
		
		if(!requisicaoDto.getEstado().equalsIgnoreCase(Enumerados.SituacaoSolicitacaoServico.Cancelada.name())) {
			ParecerDTO parecerDto = new ParecerDTO();
			
			if (requisicaoDto.getIdAprovacao() != null) {
				parecerDto = recuperaParecer(requisicaoDto);
				if (parecerDto != null) {
					requisicaoDto.setAutorizado(parecerDto.getAprovado());
					autorizado = requisicaoDto.getAutorizado() != null && requisicaoDto.getAutorizado().equalsIgnoreCase("N");
				}
			}
		}
		
		return autorizado;
	}

	public ParecerDTO recuperaParecer(RequisicaoViagemDTO requisicaoViagemDto) throws Exception {
		
		ParecerDTO parecerDto = new ParecerDTO();
		if (requisicaoViagemDto.getIdAprovacao() != null) {
			parecerDto.setIdParecer(requisicaoViagemDto.getIdAprovacao());
			
			ParecerDao parecerDao = new ParecerDao();
			parecerDao.setTransactionControler(getTransacao());
			
			return (ParecerDTO) parecerDao.restore(parecerDto);

		}
		
		return null;

	}

	public boolean validaPrazoItens() throws Exception {
		RequisicaoViagemDTO requisicaoViagemDto = this.recuperaRequisicaoViagem();
		RoteiroViagemDTO roteiroViagemDTO = new RoteiroViagemDTO();

		Timestamp dataHoraAtual = UtilDatas.getDataHoraAtual();

		Collection<IntegranteViagemDTO> colIntegrantes = new IntegranteViagemDao().recuperaIntegrantesViagemByIdSolicitacaoEstado(requisicaoViagemDto.getIdSolicitacaoServico(), RequisicaoViagemDTO.EM_AUTORIZACAO);
		if (colIntegrantes != null && !colIntegrantes.isEmpty() ) {
			for (IntegranteViagemDTO integranteViagemDto : colIntegrantes) {
				roteiroViagemDTO = new RoteiroViagemDAO().findByIdIntegrante(integranteViagemDto.getIdIntegranteViagem());
				Collection<DespesaViagemDTO> colDespesa = new DespesaViagemDAO().findDespesasAtivasViagemByIdRoteiro(roteiroViagemDTO.getIdRoteiroViagem());					
				if (colDespesa != null && !colDespesa.isEmpty()) {
					for (DespesaViagemDTO despesaViagemDTO : colDespesa) {
						if (despesaViagemDTO.getValidade() != null && !despesaViagemDTO.getValidade().equals("")){
							return despesaViagemDTO.getValidade().compareTo(dataHoraAtual) < 0;
						}
					}
				}
			}
		}		
		return false;
	}

	public StringBuilder recuperaIntegrantesEmPrestacaoContas() throws Exception {
		RequisicaoViagemDTO requisicaoViagemDto = recuperaRequisicaoViagem();
		return recuperaLoginIntegrantesPrestacaoContas(requisicaoViagemDto);
	}

	public StringBuilder recuperaLoginIntegrantesPrestacaoContas(RequisicaoViagemDTO requisicaoViagemDto) throws Exception {
		IntegranteViagemDao dao = new IntegranteViagemDao();
		
		try {
			Collection<IntegranteViagemDTO> colIntegrantes = (Collection<IntegranteViagemDTO>) dao.recuperaIntegrantesViagemByIdSolicitacaoEstadoPrestConta(requisicaoViagemDto.getIdSolicitacaoServico(), RequisicaoViagemDTO.EM_PRESTACAOCONTAS, "S");
			return this.montarUsuarios(colIntegrantes);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public StringBuilder montarUsuarios(Collection<IntegranteViagemDTO> colIntegrantes) throws Exception {
		StringBuilder result = new StringBuilder();
		try {
			if (colIntegrantes != null) {
				int i = 0;
				for (IntegranteViagemDTO integrantes : colIntegrantes) {
					UsuarioDao usuarioDao = new UsuarioDao();

					Integer idEmpregado = integrantes.getIdRespPrestacaoContas() == null ? integrantes.getIdEmpregado() : integrantes.getIdRespPrestacaoContas();

					UsuarioDTO usuarioDto = usuarioDao.restoreAtivoByIdEmpregado(idEmpregado);
					if (usuarioDto != null) {
						if (i > 0)
							result.append(";");
						result.append(usuarioDto.getLogin());
						i++;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result.length() == 0)
			throw new LogicException("Não foi encontrado nenhum Integrante da requisição");
		
		return result;
	}

	public void setaPrestacaoContaSimIntegrante(Tarefa tarefa) throws Exception {
		IntegranteViagemDao integranteViagemDao = new IntegranteViagemDao();
		integranteViagemDao.setTransactionControler(getTransacao());
		RequisicaoViagemDTO requisicaoViagemDto = recuperaRequisicaoViagem();
		List<IntegranteViagemDTO> listIntegranteViagemDTO = (List<IntegranteViagemDTO>) integranteViagemDao.recuperaIntegrantesViagemByIdSolicitacaoEstado(requisicaoViagemDto.getIdSolicitacaoServico(), RequisicaoViagemDTO.EM_PRESTACAOCONTAS);
		if(listIntegranteViagemDTO != null && listIntegranteViagemDTO.size() > 0){
			for (IntegranteViagemDTO integranteViagemDTO : listIntegranteViagemDTO) {
				integranteViagemDTO.setEmPrestacaoContas("S");
				integranteViagemDao.updateNotNull(integranteViagemDTO);
				break;
			}
		}
	}
	
	
	public void setaIdTarefaAoIntegranteViagem(Tarefa tarefa) throws Exception{
		
		IntegranteViagemDao integranteViagemDao = new IntegranteViagemDao();
		integranteViagemDao.setTransactionControler(getTransacao());
		RequisicaoViagemDTO requisicaoViagemDto = recuperaRequisicaoViagem();
		List<IntegranteViagemDTO> listIntegranteViagemDTO = (List<IntegranteViagemDTO>) integranteViagemDao.recuperaIntegrantesViagemByIdSolicitacaoEstado(requisicaoViagemDto.getIdSolicitacaoServico(), RequisicaoViagemDTO.EM_PRESTACAOCONTAS);
		
		for (IntegranteViagemDTO integranteViagemDTO : listIntegranteViagemDTO) {
			if(integranteViagemDTO.getIdTarefa() == null || integranteViagemDTO.getIdTarefa().equals("")){
				integranteViagemDTO.setIdTarefa(tarefa.getIdItemTrabalho());
				integranteViagemDao.update(integranteViagemDTO);
				break;
			}
		}
	}
	
	public void setaIdTarefaAoIntegranteViagemCorrecao(Tarefa tarefa) throws Exception{
		
		IntegranteViagemDao integranteViagemDao = new IntegranteViagemDao();
		integranteViagemDao.setTransactionControler(getTransacao());
		RequisicaoViagemDTO requisicaoViagemDto = recuperaRequisicaoViagem();
		List<IntegranteViagemDTO> listIntegranteViagemDTO = (List<IntegranteViagemDTO>) integranteViagemDao.recuperaIntegrantesViagemByIdSolicitacaoEstado(requisicaoViagemDto.getIdSolicitacaoServico(), RequisicaoViagemDTO.AGUARDANDO_CORRECAO);
		
		for (IntegranteViagemDTO integranteViagemDTO : listIntegranteViagemDTO) {
			if(integranteViagemDTO.getIdTarefa() == null || integranteViagemDTO.getIdTarefa().equals("")){
				integranteViagemDTO.setIdTarefa(tarefa.getIdItemTrabalho());
				integranteViagemDao.update(integranteViagemDTO);
				break;
			}
		}
	}
	
	public void setaIdTarefaAoIntegranteConferencia(Tarefa tarefa) throws Exception{
		
		IntegranteViagemDao integranteViagemDao = new IntegranteViagemDao();
		integranteViagemDao.setTransactionControler(getTransacao());
		RequisicaoViagemDTO requisicaoViagemDto = recuperaRequisicaoViagem();
		List<IntegranteViagemDTO> listIntegranteViagemDTO = (List<IntegranteViagemDTO>) integranteViagemDao.recuperaIntegrantesViagemByIdSolicitacaoEstado(requisicaoViagemDto.getIdSolicitacaoServico(), RequisicaoViagemDTO.AGUARDANDO_CONFERENCIA);
		
		for (IntegranteViagemDTO integranteViagemDTO : listIntegranteViagemDTO) {
			integranteViagemDTO.setIdTarefa(tarefa.getIdItemTrabalho());
			integranteViagemDTO.setEstado(RequisicaoViagemDTO.EM_CONFERENCIA);
			integranteViagemDao.updateNotNull(integranteViagemDTO);
			break;
		}
	}
	
	public void setaPrestacaoContaNaoIntegrante(Tarefa tarefa) throws Exception {
		IntegranteViagemDao integranteViagemDao = new IntegranteViagemDao();
		integranteViagemDao.setTransactionControler(getTransacao());
		RequisicaoViagemDTO requisicaoViagemDto = recuperaRequisicaoViagem();
		List<IntegranteViagemDTO> listIntegranteViagemDTO = (List<IntegranteViagemDTO>) integranteViagemDao.findAllPrestacaoContasByIdSolicitacao(requisicaoViagemDto.getIdSolicitacaoServico());
		if(listIntegranteViagemDTO != null && listIntegranteViagemDTO.size() > 0){
			for (IntegranteViagemDTO integranteViagemDTO : listIntegranteViagemDTO) {
				integranteViagemDTO.setEmPrestacaoContas("N");
				integranteViagemDao.updateNotNull(integranteViagemDTO);
			}
		}
	}

	public void associaItemTrabalhoAoIntegrante(Tarefa tarefa) throws Exception {
		IntegranteViagemDao integranteViagemDao = new IntegranteViagemDao();
		integranteViagemDao.setTransactionControler(getTransacao());
		List<IntegranteViagemDTO> listaIntegrantes = (List<IntegranteViagemDTO>) integranteViagemDao.recuperaIntegrantesViagemByIdSolicitacaoEstado(getSolicitacaoServicoDto().getIdSolicitacaoServico(), RequisicaoViagemDTO.AGUARDANDO_ADIANTAMENTO);
		if (listaIntegrantes != null && !listaIntegrantes.isEmpty()) {
			for(IntegranteViagemDTO dto: listaIntegrantes){
				dto.setIdTarefa(tarefa.getIdItemTrabalho());
				integranteViagemDao.updateNotNull(dto);
			}
		}
	}
	
	public void associaItemTrabalhoAutorizacaoRequisicao(Tarefa tarefa) throws Exception {
		RequisicaoViagemDTO requisicaoViagemDto = recuperaRequisicaoViagem();
		RequisicaoViagemDAO dao = new RequisicaoViagemDAO();
		setTransacaoDao(dao);
		if(requisicaoViagemDto != null && requisicaoViagemDto.getIdSolicitacaoServico() != null){
			requisicaoViagemDto.setIdItemTrabalho(tarefa.getIdItemTrabalho());
			dao.updateNotNull(requisicaoViagemDto);
		}
	}
	
	public void setEstadoEmAutorizacao() throws Exception {
		RequisicaoViagemDTO requisicaoViagemDto = recuperaRequisicaoViagem();
		IntegranteViagemDao integranteViagemDao = new IntegranteViagemDao();
		integranteViagemDao.setTransactionControler(getTransacao());
		List<IntegranteViagemDTO> listIntegranteViagemDTO = (List<IntegranteViagemDTO>) integranteViagemDao.recuperaIntegrantesViagemByIdSolicitacaoEstado(requisicaoViagemDto.getIdSolicitacaoServico(), RequisicaoViagemDTO.AGUARDANDO_APROVACAO);
		
		RequisicaoViagemDAO dao = new RequisicaoViagemDAO();
		setTransacaoDao(dao);
		
		if(requisicaoViagemDto != null && requisicaoViagemDto.getIdSolicitacaoServico() != null){
			requisicaoViagemDto.setEscalar(RequisicaoViagemDTO.EM_AUTORIZACAO);	
			dao.updateNotNull(requisicaoViagemDto);
		}
		
		
		if(listIntegranteViagemDTO != null && !listIntegranteViagemDTO.isEmpty()){
			for (IntegranteViagemDTO integranteViagemDTO : listIntegranteViagemDTO) {
				integranteViagemDTO.setEstado(RequisicaoViagemDTO.EM_AUTORIZACAO);
				integranteViagemDao.updateNotNull(integranteViagemDTO);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void executaItemTrabalhoPrestacaoConferencia(Tarefa tarefa) throws Exception {
		PrestacaoContasViagemDao dao = new PrestacaoContasViagemDao();
		setTransacaoDao(dao);
		List<PrestacaoContasViagemDTO> listaItens = (List<PrestacaoContasViagemDTO>) dao.findByTarefa(tarefa.getIdItemTrabalho());
		if (listaItens != null) {
			PrestacaoContasViagemDTO prestacaoContas = listaItens.get(0);
			prestacaoContas.setIdItemTrabalho(null);
			dao.update(prestacaoContas);
		}
	}

	@SuppressWarnings("unchecked")
	public void associaItemTrabalhoPrestacaoCorrecao(Tarefa tarefa) throws Exception {
		PrestacaoContasViagemDao dao = new PrestacaoContasViagemDao();
		Integer idSolicitacaoServico = getSolicitacaoServicoDto().getIdSolicitacaoServico();
		setTransacaoDao(dao);
		List<PrestacaoContasViagemDTO> listaItens = (List<PrestacaoContasViagemDTO>) dao.findBySituacao(idSolicitacaoServico, PrestacaoContasViagemDTO.AGUARDANDO_CORRECAO);
		if (listaItens != null) {
			PrestacaoContasViagemDTO prestacaoContas = listaItens.get(0);
			prestacaoContas.setIdItemTrabalho(tarefa.getIdItemTrabalho());
			prestacaoContas.setSituacao(PrestacaoContasViagemDTO.EM_CORRECAO);
			atribuiUsuarioCorrecao(prestacaoContas, tarefa);
			dao.update(prestacaoContas);
		}
	}
	
	public void cancelaTarefaPrestacaoContas(Integer idTarefa, String login) throws Exception{
		SolicitacaoServicoDTO solicitacaoDto = getSolicitacaoServicoDto();
		TarefaFluxoDTO tarefaDto = recuperaTarefa(idTarefa);
		String motivo = "Remarcação";
		this.cancelaTarefa(login, solicitacaoDto, tarefaDto, motivo);
	}
	
	@SuppressWarnings("unchecked")
	public boolean corrigirPrestacaoContas() throws Exception {
		PrestacaoContasViagemDao dao = new PrestacaoContasViagemDao();
		dao.setTransactionControler(getTransacao());
		Integer idSolicitacao = getSolicitacaoServicoDto().getIdSolicitacaoServico();
		List<PrestacaoContasViagemDTO> listaItens = (List<PrestacaoContasViagemDTO>) dao.findBySituacao(idSolicitacao, PrestacaoContasViagemDTO.NAO_APROVADA);
		boolean isOk = (listaItens != null && listaItens.size() > 0);
		if (isOk) {
			PrestacaoContasViagemDTO prestacaoDto = listaItens.get(0);
			prestacaoDto.setSituacao(PrestacaoContasViagemDTO.AGUARDANDO_CORRECAO);
			dao.update(prestacaoDto);
		}
		return isOk;
	}
	
	@SuppressWarnings({"unchecked"})
	public boolean isTarefaConferencia() throws Exception {
		PrestacaoContasViagemDao dao = new PrestacaoContasViagemDao();
		dao.setTransactionControler(getTransacao());
		Integer idSolicitacao = getSolicitacaoServicoDto().getIdSolicitacaoServico();
		List<PrestacaoContasViagemDTO> listaItens = (List<PrestacaoContasViagemDTO>) dao.findBySituacaoAndNull(idSolicitacao, PrestacaoContasViagemDTO.AGUARDANDO_CONFERENCIA);
		boolean isOk = (listaItens != null && listaItens.size() > 0);
		if (isOk) {
			PrestacaoContasViagemDTO dto = listaItens.get(0);
			dto.setSituacao(PrestacaoContasViagemDTO.EM_CONFERENCIA);
			dao.update(dto);
			this.setaInicioTarefa();
		}
		return isOk;
	}
	
	public boolean isCancelamentoRequisicaoViagem() throws Exception{
		RequisicaoViagemDTO requisicaoViagemDto = recuperaRequisicaoViagem();
		return (requisicaoViagemDto != null && requisicaoViagemDto.getCancelarRequisicao() != null && requisicaoViagemDto.getCancelarRequisicao().equalsIgnoreCase("S"));
	}
	
	public boolean isCancelada() throws Exception {
		RequisicaoViagemDTO requisicaoViagemDTO = recuperaRequisicaoViagem();
		
		if(requisicaoViagemDTO != null && requisicaoViagemDTO.getEstado().equalsIgnoreCase(Enumerados.SituacaoSolicitacaoServico.Cancelada.name())) {
			return true;
		}
		
		return false;
	}
	
	public boolean isHouveRemarcacaoViagem() throws Exception{
		IntegranteViagemDao integranteViagemDao = new IntegranteViagemDao();
		RequisicaoViagemDTO requisicaoViagemDto = recuperaRequisicaoViagem();
		boolean isOK =  integranteViagemDao.isHouveRemarcacaoViagem(requisicaoViagemDto.getIdSolicitacaoServico());

		if(isOK){
			return true;
		}else{			
			return false;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public boolean verificaEtapaDoFluxo(Integer idSolicitacaoServico, String template )throws Exception{
		RequisicaoViagemDAO requisicaoViagemDAO = new RequisicaoViagemDAO();
		
		List listaRequisicaoViagem  =  requisicaoViagemDAO.retornaRequisicaoByTemplateAndIdsolicitacao(idSolicitacaoServico, template);
		if (listaRequisicaoViagem != null && listaRequisicaoViagem.size() > 0)
			return true;
		else
			return false;
	}

	public boolean isEstadoPrestacaoContas() throws Exception {
		PrestacaoContasViagemDao dao = new PrestacaoContasViagemDao();
		RequisicaoViagemDTO requisicaoViagemDto = recuperaRequisicaoViagem();
		boolean isOk = dao.isEstadoPrestacaoContas(requisicaoViagemDto);
		if (isOk)
			this.setaInicioTarefa();
		return isOk;
	}
	
	public boolean isEstadoAutorizacao() throws Exception {
		IntegranteViagemDao integranteViagemDao = new IntegranteViagemDao();
		integranteViagemDao.setTransactionControler(getTransacao());
		RequisicaoViagemDTO requisicaoViagemDto = recuperaRequisicaoViagem();
		Collection<IntegranteViagemDTO> colIntegrantes = integranteViagemDao.recuperaIntegrantesViagemByIdSolicitacaoEstado(requisicaoViagemDto.getIdSolicitacaoServico(), RequisicaoViagemDTO.AGUARDANDO_APROVACAO);
		
		if(colIntegrantes != null && colIntegrantes.size() > 0){
			for(IntegranteViagemDTO integranteViagemDTO: colIntegrantes){
				integranteViagemDTO.setEstado(RequisicaoViagemDTO.EM_AUTORIZACAO);
				integranteViagemDao.update(integranteViagemDTO);
			}
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isCotacaoVencida() throws Exception {
		RequisicaoViagemDTO requisicaoViagemDto = recuperaRequisicaoViagem();
		IntegranteViagemDao integranteViagemDao = new IntegranteViagemDao();
		integranteViagemDao.setTransactionControler(getTransacao());
		boolean isOk = false;
			
			isOk = this.validaPrazoItens();
		
			if(isOk){
				this.alteraEstadoRequisicaoViagem(requisicaoViagemDto ,RequisicaoViagemDTO.EM_PLANEJAMENTO);
				Collection<IntegranteViagemDTO> colIntegrantesAux = integranteViagemDao.recuperaIntegrantesViagemByIdSolicitacaoEstado(requisicaoViagemDto.getIdSolicitacaoServico(), RequisicaoViagemDTO.EM_AUTORIZACAO);
				
				if(colIntegrantesAux != null && colIntegrantesAux.size() > 0){
					for(IntegranteViagemDTO integranteViagemDTO: colIntegrantesAux){
						integranteViagemDTO.setEstado(RequisicaoViagemDTO.AGUARDANDO_PLANEJAMENTO);
						integranteViagemDao.updateNotNull(integranteViagemDTO);
					}
				}
				
				if(requisicaoViagemDto.getIdItemTrabalho() != null){
					String motivo = "Data da contação forado prazo de validade.";
					TarefaFluxoDTO tarefaDto = recuperaTarefa(requisicaoViagemDto.getIdItemTrabalho());
					this.cancelaTarefa(null,  this.getSolicitacaoServicoDto(), tarefaDto, motivo);
				}
			}
			
		return isOk;
	}

	public boolean requisicaoViagemFinalizada() throws Exception {
		IntegranteViagemDao dao = new IntegranteViagemDao();

		Collection<IntegranteViagemDTO> colIntegrantes = dao.findAllByIdSolicitacao(getSolicitacaoServicoDto().getIdSolicitacaoServico());
		if(colIntegrantes != null && !colIntegrantes.isEmpty()){
			for(IntegranteViagemDTO dto: colIntegrantes){
				if(!dto.getEstado().equalsIgnoreCase(RequisicaoViagemDTO.FINALIZADA)){
					return false;
				}
			}
		}
		return true;
	}
	
	public void alteraEstadoRequisicaoViagem(RequisicaoViagemDTO requisicaoViagemDto, String estado) throws Exception {
			RequisicaoViagemDAO reqViagemDao = new RequisicaoViagemDAO();
			setTransacaoDao(reqViagemDao);
			requisicaoViagemDto.setEstado(estado);
			requisicaoViagemDto.setTarefaIniciada("N");			
			reqViagemDao.updateNotNull(requisicaoViagemDto);
	}

	public void setaInicioTarefa() throws Exception {
		RequisicaoViagemDAO dao = new RequisicaoViagemDAO();
		setTransacaoDao(dao);
		RequisicaoViagemDTO requisicaoViagemDto = recuperaRequisicaoViagem();
		requisicaoViagemDto.setTarefaIniciada("S");
		dao.update(requisicaoViagemDto);
	}

	public void setaFimTarefa() throws Exception {
		RequisicaoViagemDAO dao = new RequisicaoViagemDAO();
		setTransacaoDao(dao);
		RequisicaoViagemDTO requisicaoViagemDto = recuperaRequisicaoViagem();
		requisicaoViagemDto.setTarefaIniciada("N");
		dao.update(requisicaoViagemDto);
	}

	public StringBuilder recuperaLoginResponsaveisAdiantamento() throws Exception {
		StringBuilder result = new StringBuilder();

		Integer idGrupo = Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_RESPONSAVEL_ADIANTAMENTO_VIAGEM, getSolicitacaoServicoDto()
				.getIdGrupoAtual().toString()));
		EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
		Collection<EmpregadoDTO> colEmpregado = empregadoService.listEmpregadosByIdGrupo(idGrupo);

		if (colEmpregado != null) {
			int i = 0;
			UsuarioDao usuarioDao = new UsuarioDao();
			for (EmpregadoDTO empregadoDto : colEmpregado) {
				UsuarioDTO usuarioDto = usuarioDao.restoreAtivoByIdEmpregado(empregadoDto.getIdEmpregado());
				if (usuarioDto != null) {
					if (i > 0)
						result.append(";");
					result.append(usuarioDto.getLogin());
					i++;
				}
			}
		}
		if (result.length() == 0)
			throw new LogicException("Não foi encontrado nenhum responsavel para o Adiantamento");

		return result;
	}

	public StringBuilder recuperaLoginResponsaveisConferencia() throws Exception {
		StringBuilder result = new StringBuilder();

		Integer idGrupo = Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_RESPONSAVEL_CONFERENCIA_VIAGEM, getSolicitacaoServicoDto().getIdGrupoAtual()
				.toString()));

		EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
		Collection<EmpregadoDTO> colEmpregado = empregadoService.listEmpregadosByIdGrupo(idGrupo);

		if (colEmpregado != null) {
			int i = 0;
			UsuarioDao usuarioDao = new UsuarioDao();
			for (EmpregadoDTO empregadoDto : colEmpregado) {
				UsuarioDTO usuarioDto = usuarioDao.restoreAtivoByIdEmpregado(empregadoDto.getIdEmpregado());
				if (usuarioDto != null) {
					if (i > 0)
						result.append(";");
					result.append(usuarioDto.getLogin());
					i++;
				}
			}
		}
		if (result.length() == 0)
			throw new LogicException("Não foi encontrado nenhum responsavel para a Conferência");

		return result;
	}

	public StringBuilder recuperaLoginResponsaveisCotacao() throws Exception {
		StringBuilder result = new StringBuilder();

		Integer idGrupo = Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_RESPONSAVEL_COTACAO_VIAGEM, getSolicitacaoServicoDto().getIdGrupoAtual()
				.toString()));
		EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
		Collection<EmpregadoDTO> colEmpregado = empregadoService.listEmpregadosByIdGrupo(idGrupo);

		if (colEmpregado != null) {
			int i = 0;
			UsuarioDao usuarioDao = new UsuarioDao();
			for (EmpregadoDTO empregadoDto : colEmpregado) {
				UsuarioDTO usuarioDto = usuarioDao.restoreAtivoByIdEmpregado(empregadoDto.getIdEmpregado());
				if (usuarioDto != null) {
					if (i > 0)
						result.append(";");
					result.append(usuarioDto.getLogin());
					i++;
				}
			}
		}
		if (result.length() == 0)
			throw new LogicException("Não foi encontrado nenhum responsavel pela Cotação");

		return result;
	}

	public StringBuilder recuperaLoginIntegrante() throws Exception {
		
		RequisicaoViagemDTO requisicaoViagemDto = recuperaRequisicaoViagem();
		StringBuilder result = new StringBuilder();
		IntegranteViagemDao integranteViagemDao = new IntegranteViagemDao();
		Collection<IntegranteViagemDTO> colIntegrantes = integranteViagemDao.findAllByIdSolicitacao(requisicaoViagemDto.getIdSolicitacaoServico());
		UsuarioDTO usuarioDto;
		
		try {
			
			if (colIntegrantes != null) {
				int i = 0;
				
				for (IntegranteViagemDTO integrante : colIntegrantes) {
					
					UsuarioDao usuarioDao = new UsuarioDao();
					
					if (integrante.getIdRespPrestacaoContas() != null) {
						usuarioDto = usuarioDao.restoreAtivoByIdEmpregado(integrante.getIdRespPrestacaoContas());
					} else {
						usuarioDto = usuarioDao.restoreAtivoByIdEmpregado(integrante.getIdEmpregado());
					}
					if (usuarioDto != null) {
						if (i > 0)
							result.append(";");
						result.append(usuarioDto.getLogin());
						i++;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result.length() == 0)
			throw new LogicException("Não foi encontrado nenhum Integrante da requisição");
		return result;
	}

	@SuppressWarnings("unchecked")
	public StringBuilder recuperaLoginIntegranteCorrecao() throws Exception {
		
		RequisicaoViagemDTO requisicaoViagemDto = recuperaRequisicaoViagem();
		StringBuilder result = new StringBuilder();
		PrestacaoContasViagemDao dao = new PrestacaoContasViagemDao();
		PrestacaoContasViagemDTO prestacaoContasDto = new PrestacaoContasViagemDTO();
		List<PrestacaoContasViagemDTO> lista = (List<PrestacaoContasViagemDTO>) dao.findBySituacao(requisicaoViagemDto.getIdSolicitacaoServico(),  PrestacaoContasViagemDTO.AGUARDANDO_CORRECAO);
				
		if (lista == null) {
			return recuperaLoginIntegrante();
		} else {
			try {
				
				prestacaoContasDto = lista.get(0);
				
				Integer idEmpregado = prestacaoContasDto.getIdEmpregado();

				IntegranteViagemDao integranteViagemDao = new IntegranteViagemDao();
				IntegranteViagemDTO integranteViagemDto = integranteViagemDao.recuperaIntegrante(requisicaoViagemDto.getIdSolicitacaoServico(), idEmpregado);

				if (integranteViagemDto != null) {
					if (integranteViagemDto.getIdRespPrestacaoContas() != null) {
						idEmpregado = integranteViagemDto.getIdRespPrestacaoContas();
					}
				}

				UsuarioDao usuarioDao = new UsuarioDao();
				UsuarioDTO usuarioDto = usuarioDao.restoreAtivoByIdEmpregado(prestacaoContasDto.getIdEmpregado());
				if (usuarioDto != null) {
					result.append(usuarioDto.getLogin());
					result.append(";");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (result.length() == 0)
				throw new LogicException("Não foi encontrado nenhum Integrante da requisição");
			return result;
		}
	}
		
	@SuppressWarnings("unchecked")
	public StringBuilder recuperaLoginIntegranteCorrecao(Tarefa tarefa) throws Exception {
		StringBuilder result = new StringBuilder();	
		
		PrestacaoContasViagemDao dao = new PrestacaoContasViagemDao();
		Integer idSolicitacaoServico = getSolicitacaoServicoDto().getIdSolicitacaoServico();
		setTransacaoDao(dao);
		List<PrestacaoContasViagemDTO> listaItens = (List<PrestacaoContasViagemDTO>) dao.findBySituacao(idSolicitacaoServico, PrestacaoContasViagemDTO.AGUARDANDO_CORRECAO);
		if (listaItens != null) {
		
			PrestacaoContasViagemDTO prestacaoContas = listaItens.get(0);
			UsuarioDao usuarioDao = new UsuarioDao();
			
			Integer idEmpregado = prestacaoContas.getIdEmpregado();

			IntegranteViagemDao integranteViagemDao = new IntegranteViagemDao();
			IntegranteViagemDTO integranteViagemDto = integranteViagemDao.recuperaIntegrante(prestacaoContas.getIdSolicitacaoServico(), idEmpregado);

			if (integranteViagemDto != null && integranteViagemDto.getIdRespPrestacaoContas() != null)
				idEmpregado = integranteViagemDto.getIdRespPrestacaoContas();
			
			UsuarioDTO usuarioDto = usuarioDao.restoreAtivoByIdEmpregado(idEmpregado);
			if (usuarioDto != null) {
				result.append(usuarioDto.getLogin());
				result.append(";");
			}
		}
		
		if (result.length() == 0)
			throw new LogicException("Não foi encontrado nenhum Integrante da requisição");
		
		return result;
	}

	public void enviaEmailNaoAprovado(Integer idModeloEmail, RequisicaoViagemDTO requisicaoViagemDto, PrestacaoContasViagemDTO prestacaoContasViagemDto, TransactionControler tc) throws Exception {
		if (idModeloEmail == null)
			return;

		if (prestacaoContasViagemDto == null)
			return;

		EmpregadoDTO empregadoDto = new EmpregadoDTO();
		EmpregadoDao empregadoDao = new EmpregadoDao();
		empregadoDto.setIdEmpregado(prestacaoContasViagemDto.getIdEmpregado());
		empregadoDto = (EmpregadoDTO) empregadoDao.restore(empregadoDto);

		if (empregadoDto == null)
			return;

		String remetente = getRemetenteEmail();

		SolicitacaoServicoDTO solicitacaoAuxDto = new SolicitacaoServicoServiceEjb().restoreAll(requisicaoViagemDto.getIdSolicitacaoServico(), tc);
		if (solicitacaoAuxDto != null)
			solicitacaoAuxDto.setNomeTarefa(requisicaoViagemDto.getNomeTarefa());

		/* Decodifica a mensagem a ser enviada */
		if (solicitacaoAuxDto != null) {
			solicitacaoAuxDto.setDescricao(StringEscapeUtils.unescapeJavaScript(solicitacaoAuxDto.getDescricao()));
			solicitacaoAuxDto.setResposta(StringEscapeUtils.unescapeJavaScript(solicitacaoAuxDto.getResposta()));
			solicitacaoAuxDto.setComplementoJustificativa(prestacaoContasViagemDto.getComplemJustificativaAutorizacao());
			solicitacaoAuxDto.setNomecontato(empregadoDto.getNome());
		}

		MensagemEmail mensagem = new MensagemEmail(idModeloEmail, new IDto[] { solicitacaoAuxDto });
		try {
			mensagem.envia(empregadoDto.getEmail(), null, remetente);
		} catch (Exception e) {
		}
	}

	private void atribuiUsuarioCorrecao(PrestacaoContasViagemDTO prestacaoContasViagemDto, Tarefa tarefa) throws Exception{
		
		AtribuicaoFluxoDTO atribuicaoFluxoDto = new AtribuicaoFluxoDTO();
		AtribuicaoFluxoDao atribuicaoFluxoDao = new AtribuicaoFluxoDao();
		atribuicaoFluxoDao.setTransactionControler(getTransacao());
		UsuarioDao usuarioDao = new UsuarioDao();
		
		Integer idEmpregado = prestacaoContasViagemDto.getIdEmpregado();

		IntegranteViagemDao integranteViagemDao = new IntegranteViagemDao();
		IntegranteViagemDTO integranteViagemDto = integranteViagemDao.recuperaIntegrante(prestacaoContasViagemDto.getIdSolicitacaoServico(), idEmpregado);

		if (integranteViagemDto != null && integranteViagemDto.getIdRespPrestacaoContas() != null)
			idEmpregado = integranteViagemDto.getIdRespPrestacaoContas();
		
		UsuarioDTO usuarioDto = usuarioDao.restoreByIdEmpregado(idEmpregado);
		
		atribuicaoFluxoDto.setIdItemTrabalho(tarefa.getIdItemTrabalho());
		atribuicaoFluxoDto.setIdUsuario(usuarioDto.getIdUsuario());
		atribuicaoFluxoDto.setTipo("Automatica");
		atribuicaoFluxoDto.setDataHora(UtilDatas.getDataHoraAtual());
		
		Collection<AtribuicaoFluxoDTO> itensCadastrados = atribuicaoFluxoDao.findByIdItemTrabalhoAndIdUsuarioAndTipo(atribuicaoFluxoDto.getIdItemTrabalho(), atribuicaoFluxoDto.getIdUsuario(), atribuicaoFluxoDto.getTipo());
		
		if(itensCadastrados == null || itensCadastrados.isEmpty())
			atribuicaoFluxoDao.create(atribuicaoFluxoDto);
		
	}
	
	@Override
    public boolean permiteAprovacaoAlcada(AlcadaProcessoNegocioDTO alcadaProcessoNegocioDto, SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception {
    	Integer idEmpregado = alcadaProcessoNegocioDto.getEmpregadoDto().getIdEmpregado();
    	
    	if (idEmpregado.intValue() == solicitacaoServicoDto.getIdSolicitante().intValue()) {
        	alcadaProcessoNegocioDto.setComplementoRejeicao("Aprovador não pode ser o solicitante");
            return false;
    	}
	        
        String idGrupoViagem = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_REQ_VIAGEM_EXECUCAO, null);
        if (idGrupoViagem == null || idGrupoViagem.trim().equals(""))
            throw new Exception("Grupo padrão de requisição de produtos não parametrizado");
        
        if (alcadaProcessoNegocioDto.getMapGruposEmpregado().get(idGrupoViagem.trim()) != null) {
        	alcadaProcessoNegocioDto.setComplementoRejeicao("Aprovador não pode pertencer ao grupo responsável por viagens");
        	return false;
        }

   	 	Collection<IntegranteViagemDTO> listaIntegratesViagem = new IntegranteViagemDao().findAllByIdSolicitacao(solicitacaoServicoDto.getIdSolicitacaoServico());
        if (listaIntegratesViagem!=null){
        	for(IntegranteViagemDTO integranteViagemDto : listaIntegratesViagem){
        		if (integranteViagemDto.getIdEmpregado().intValue() ==  idEmpregado.intValue()) {
                	alcadaProcessoNegocioDto.setComplementoRejeicao("Aprovador não pode ser um dos integrantes da viagem");
                    return false;
        		}
        	}
	    }
	    return true;
    }
	
	public String recuperaLoginByIdEmpregado(Integer idEmpregado) throws Exception{
		UsuarioDao usuarioDao = new UsuarioDao();
		return usuarioDao.restoreAtivoByIdEmpregado(idEmpregado).getLogin();
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
		
	}
	
    @Override
    public void calculaValorAprovadoAnual(CentroResultadoDTO centroResultadoDto, int anoRef, TransactionControler tc) throws Exception {
        valorAnualAtendCliente = 0.0;
        valorAnualUsoInterno = 0.0;
        RequisicaoViagemDAO requisicaoDao = new RequisicaoViagemDAO();
        if (tc != null)
            requisicaoDao.setTransactionControler(tc);
        Collection<RequisicaoViagemDTO> colRequisicoes = requisicaoDao.findByIdCentroCusto(centroResultadoDto.getIdCentroResultado());
        if (colRequisicoes != null) {
        	SolicitacaoServicoDao solicitacaoServicoDao = new SolicitacaoServicoDao();
        	if (tc != null)
        		solicitacaoServicoDao.setTransactionControler(tc);
            for (RequisicaoViagemDTO requisicaoViagemDto : colRequisicoes) {
            	SolicitacaoServicoDTO solicitacaoServicoDto = new SolicitacaoServicoDTO();
            	solicitacaoServicoDto.setIdSolicitacaoServico(requisicaoViagemDto.getIdRequisicaoViagem());
            	solicitacaoServicoDto = (SolicitacaoServicoDTO) solicitacaoServicoDao.restore(solicitacaoServicoDto);
                Date dataAux = new Date(requisicaoViagemDto.getDataHoraSolicitacao().getTime());
                int ano = UtilDatas.getYear(dataAux);
                if (ano != anoRef)
                    continue;
                valorAnualUsoInterno += calculaValorAprovado(requisicaoViagemDto, tc);
            }
        }
    }
    
    @Override
    public void calculaValorAprovadoMensal(CentroResultadoDTO centroResultadoDto, int mesRef, int anoRef, TransactionControler tc) throws Exception {
        valorMensalAtendCliente = 0.0;
        valorMensalUsoInterno = 0.0;
        RequisicaoViagemDAO requisicaoDao = new RequisicaoViagemDAO();
        if (tc != null)
            requisicaoDao.setTransactionControler(tc);
        Collection<RequisicaoViagemDTO> colRequisicoes = requisicaoDao.findByIdCentroCusto(centroResultadoDto.getIdCentroResultado());
        if (colRequisicoes != null) {
        	SolicitacaoServicoDao solicitacaoServicoDao = new SolicitacaoServicoDao();
        	if (tc != null)
        		solicitacaoServicoDao.setTransactionControler(tc);
            for (RequisicaoViagemDTO requisicaoViagemDto : colRequisicoes) {
            	/*if (getSolicitacaoServicoDto() != null && getSolicitacaoServicoDto().getIdSolicitacaoServico() != null &&
                	getSolicitacaoServicoDto().getIdSolicitacaoServico().intValue() == requisicaoViagemDto.getIdSolicitacaoServico().intValue())
                	continue;*/
            	SolicitacaoServicoDTO solicitacaoServicoDto = new SolicitacaoServicoDTO();
            	solicitacaoServicoDto.setIdSolicitacaoServico(requisicaoViagemDto.getIdRequisicaoViagem());
            	solicitacaoServicoDto = (SolicitacaoServicoDTO) solicitacaoServicoDao.restore(solicitacaoServicoDto);
                Date dataAux = new Date(requisicaoViagemDto.getDataHoraSolicitacao().getTime());
                int mes = UtilDatas.getMonth(dataAux);
                int ano = UtilDatas.getYear(dataAux);
                if (ano != anoRef || mes != mesRef)
                    continue;
                valorMensalUsoInterno += calculaValorAprovado(requisicaoViagemDto, tc);
            }
        }
    }
    
    @Override
    public double calculaValorAprovado(SolicitacaoServicoDTO solicitacaoServicoDto, TransactionControler tc) throws Exception {
		DespesaViagemDAO despesaViagemDAO = new DespesaViagemDAO();
		
		if (tc != null)
			despesaViagemDAO.setTransactionControler(tc);
		
		return despesaViagemDAO.buscaValorTotalViagem(solicitacaoServicoDto.getIdSolicitacaoServico());
    }
    
    
    /**
     * Valida se a requisição é uma requisição de viagem remarcado, caso seja, 
     * valida se todos os integrantes da viagem já foram remarcadas, caso sim
     * seta que a requisição de viagem não é mais rearcada
     * 
     * @throws Exception
     *  
     */
    public void verificarRequisicaoRemarcada() throws Exception{
    	RequisicaoViagemDAO dao = new RequisicaoViagemDAO();
    	setTransacaoDao(dao);
    	RequisicaoViagemDTO requisicaoDto = recuperaRequisicaoViagem();
    	
		if(requisicaoDto.getRemarcacao().equals("S")){
			
			RequisicaoViagemService requisicaoViagemService = (RequisicaoViagemService) ServiceLocator.getInstance().getService(RequisicaoViagemService.class, null);
			RequisicaoViagemDTO requisicao = requisicaoViagemService.recuperaRequisicaoPelaSolicitacao(requisicaoDto.getIdSolicitacaoServico());

			IntegranteViagemService integranteViagemService = (IntegranteViagemService) ServiceLocator.getInstance().getService(IntegranteViagemService.class, null);
			Collection<IntegranteViagemDTO> integrantes = integranteViagemService.findAllRemarcacaoByIdSolicitacao(requisicaoDto.getIdSolicitacaoServico());
			
			if(integrantes == null || integrantes.size() < 1){
				requisicao.setRemarcacao("N");
				requisicao.setTarefaIniciada("N");
				dao.update(requisicao);
			}
		
		}
    }
    
    /**
     * Verifica se a viagem ja aconteceu para avançar o fluxo para prestação de contas
     * 
     * @throws Exception
     *  
     */
    public boolean viagemOk() throws Exception{
    	RequisicaoViagemDTO requisicaoViagemDto = this.recuperaRequisicaoViagem();
    	IntegranteViagemService integranteViagemService = (IntegranteViagemService) ServiceLocator.getInstance().getService(IntegranteViagemService.class, null);
//    	IntegranteViagemDao integranteViagemDao = new IntegranteViagemDao();
//    	integranteViagemDao.setTransactionControler(getTransacao());
    	RoteiroViagemService roteiroViagemService = (RoteiroViagemService) ServiceLocator.getInstance().getService(RoteiroViagemService.class, null);
    	RoteiroViagemDTO roteiroViagemDTO = new RoteiroViagemDTO();
		Collection<IntegranteViagemDTO> integrantes = integranteViagemService.recuperaIntegrantesViagemByIdSolicitacaoEstado(requisicaoViagemDto.getIdSolicitacaoServico(), RequisicaoViagemDTO.AGUARDANDO_PRESTACAOCONTAS);
		
		Date dataAtual = UtilDatas.getDataAtual();
		Date dataFimViagem = requisicaoViagemDto.getDataFimViagem();
		
		if(integrantes != null && integrantes.size() > 0){
			for(IntegranteViagemDTO integranteViagemDTO: integrantes){
				roteiroViagemDTO = roteiroViagemService.findByIdIntegrante(integranteViagemDTO.getIdIntegranteViagem());
				
				if(roteiroViagemDTO.getVolta().compareTo(dataAtual) < 0 && integranteViagemDTO.getEstado() != null && integranteViagemDTO.getEstado().equalsIgnoreCase(RequisicaoViagemDTO.AGUARDANDO_PRESTACAOCONTAS)){
					integranteViagemDTO = (IntegranteViagemDTO) integranteViagemService.restore(integranteViagemDTO);
					integranteViagemDTO.setEstado(RequisicaoViagemDTO.EM_PRESTACAOCONTAS);
					integranteViagemDTO.setEmPrestacaoContas("S");
					integranteViagemDTO.setRemarcacao("N");
					integranteViagemDTO.setIdTarefa(null);
					integranteViagemService.update(integranteViagemDTO);
					return true;
				}
			}
		}
		return false;
    }	
    
    /**
     * cancela intancia se itens com contação vencida
     * 
     * @throws Exception
     *  
     */
    public void cancelaAutorizacao() throws Exception{
    	SolicitacaoServicoDTO solicitacaoDto = getSolicitacaoServicoDto();
    	
    	Collection<TarefaFluxoDTO> colTarefas = new TarefaFluxoDao().findDisponiveisByIdTarefaEstado(solicitacaoDto.getIdSolicitacaoServico(), "Autorizar requisição");
    	if(colTarefas != null && !colTarefas.isEmpty()){
    		String motivo = "Requisição com itens vencidos!";
    		for(TarefaFluxoDTO tarefaDto: colTarefas){
				this.cancelaTarefa(null, solicitacaoDto, tarefaDto, motivo);
    		}
    	}
    }
    
    
    /**
     * Valida se a requisição foi criada para executar planejamento
     *  
     */
    public boolean isRequisicaoCriada() throws Exception{
    	RequisicaoViagemDTO requisicaoViagemDto = this.recuperaRequisicaoViagem();
    	RequisicaoViagemDAO requisicaoViagemDAO = new RequisicaoViagemDAO();
    	requisicaoViagemDAO.setTransactionControler(getTransacao());
				
    	if(requisicaoViagemDto != null && requisicaoViagemDto.getEstado().equalsIgnoreCase("Aguardando Planejamento") 
    			&& requisicaoViagemDto.getTarefaIniciada().equalsIgnoreCase("") || requisicaoViagemDto.getTarefaIniciada() == null){
    		requisicaoViagemDto.setEstado(RequisicaoViagemDTO.EM_PLANEJAMENTO);
    		requisicaoViagemDAO.updateNotNull(requisicaoViagemDto);
    		return true;
    	}else{
    		return false;
    	}
    }		
    
    
    /**
     * Valida se viagem foi remarcada
     * @return
     * @throws Exception
     */
    public boolean isRemarcada() throws Exception{
    	IntegranteViagemDao integranteViagemDao = new IntegranteViagemDao();
    	integranteViagemDao.setTransactionControler(getTransacao());
    	IntegranteViagemService integranteViagemService = (IntegranteViagemService) ServiceLocator.getInstance().getService(IntegranteViagemService.class, null);
    	Collection<IntegranteViagemDTO> colIntegrantes = new ArrayList<IntegranteViagemDTO>();
    	SolicitacaoServicoDTO solicitacaoDto = getSolicitacaoServicoDto();
    	Collection<IntegranteViagemDTO> colIntegrantesAux = new ArrayList<IntegranteViagemDTO>();
    	RequisicaoViagemDTO requisicaoViagemDto = this.recuperaRequisicaoViagem();
    	RequisicaoViagemDAO requisicaoViagemDAO = new RequisicaoViagemDAO();
    	requisicaoViagemDAO.setTransactionControler(getTransacao());
    	boolean todosRemarcados = true;
    	TarefaFluxoDTO tarefaDto = new TarefaFluxoDTO();
    
    	colIntegrantes = integranteViagemService.recuperaIntegrantesViagemByIdSolicitacaoEstado(requisicaoViagemDto.getIdSolicitacaoServico(), RequisicaoViagemDTO.REMARCADO);
    	if(colIntegrantes != null && !colIntegrantes.isEmpty()){
    		for(IntegranteViagemDTO dto: colIntegrantes){
    			dto.setEstado(RequisicaoViagemDTO.AGUARDANDO_PLANEJAMENTO);
    			integranteViagemDao.update(dto);
    		}
    		
    		requisicaoViagemDto.setEstado(RequisicaoViagemDTO.EM_PLANEJAMENTO);
    		requisicaoViagemDAO.updateNotNull(requisicaoViagemDto);
    		
    		colIntegrantesAux = integranteViagemService.recuperaIntegrantesViagemByIdSolicitacao(requisicaoViagemDto.getIdSolicitacaoServico());
        	if(colIntegrantesAux != null && !colIntegrantesAux.isEmpty()){
    	    	for(IntegranteViagemDTO dto: colIntegrantesAux){
    				if((dto.getEstado() != null  && dto.getEstado().equalsIgnoreCase(RequisicaoViagemDTO.AGUARDANDO_ADIANTAMENTO))){
    					todosRemarcados = false;
    				}
    			}
        	}
        	
        	if(todosRemarcados){
        		String motivo = "Todos integrantes foram remarcados";
        		if(colIntegrantesAux != null && !colIntegrantesAux.isEmpty()){
        	    	for(IntegranteViagemDTO dto: colIntegrantesAux){
        	    		if(dto.getIdTarefa() != null){
        	    			tarefaDto = this.recuperaTarefa(dto.getIdTarefa());
    	    				if(tarefaDto != null && !tarefaDto.getSituacao().equalsIgnoreCase("Executado")){
    	    					this.cancelaTarefa(null, solicitacaoDto, tarefaDto, motivo);
    	    					break;
    	    				}
        	    		}
        			}
            	}
        	}
    		
    		return true;
    	}else{
    		return false;
    	}
    }	
 	
    
    @SuppressWarnings({"unchecked"})
	public void setaTarefaConferencia(Tarefa tarefa) throws Exception {
    	PrestacaoContasViagemDao dao = new PrestacaoContasViagemDao();
		setTransacaoDao(dao);
		List<PrestacaoContasViagemDTO> listaItens = (List<PrestacaoContasViagemDTO>) dao.findBySolicitacaoAndTaferaConferencia(getSolicitacaoServicoDto().getIdSolicitacaoServico());
		IntegranteViagemDao integranteViagemDao = new IntegranteViagemDao();
		integranteViagemDao.setTransactionControler(getTransacao());
		Integer idSolicitacao = getSolicitacaoServicoDto().getIdSolicitacaoServico();
		Collection<IntegranteViagemDTO> colIntegrantes = integranteViagemDao.recuperaIntegrantesViagemByIdSolicitacaoEstado(idSolicitacao, RequisicaoViagemDTO.AGUARDANDO_CONFERENCIA);
		Collection<IntegranteViagemDTO> colIntegrantesAux = integranteViagemDao.recuperaIntegrantesViagemByIdSolicitacaoEstado(idSolicitacao, RequisicaoViagemDTO.EM_CONFERENCIA);
		
		if (listaItens != null) {
			PrestacaoContasViagemDTO prestacaoContas = listaItens.get(0);
			prestacaoContas.setIdItemTrabalho(tarefa.getIdItemTrabalho());
			prestacaoContas.setSituacao(PrestacaoContasViagemDTO.EM_CONFERENCIA);
			dao.update(prestacaoContas);
			this.setaInicioTarefa();
		}
    	
		
		if(colIntegrantes != null && colIntegrantes.size() > 0){
			for(IntegranteViagemDTO integranteViagemDTO: colIntegrantes){
				if(integranteViagemDTO.getIdTarefa() == null || integranteViagemDTO.getIdTarefa().equals("")){
					integranteViagemDTO.setIdTarefa(tarefa.getIdItemTrabalho());
					integranteViagemDTO.setEstado(RequisicaoViagemDTO.EM_CONFERENCIA);
					integranteViagemDao.update(integranteViagemDTO);
					break;
				}
			}
		}
		
		if(colIntegrantesAux != null && colIntegrantesAux.size() > 0){
			for(IntegranteViagemDTO integranteViagemDTO: colIntegrantesAux){
				if(integranteViagemDTO.getIdTarefa() == null || integranteViagemDTO.getIdTarefa().equals("")){
					integranteViagemDTO.setIdTarefa(tarefa.getIdItemTrabalho());
					integranteViagemDTO.setEstado(RequisicaoViagemDTO.EM_CONFERENCIA);
					integranteViagemDao.update(integranteViagemDTO);
					break;
				}
			}
		}
	}
    
    public boolean alteraEstadoIntegrantes() throws Exception {
		RequisicaoViagemDTO requisicaoViagemDto = this.recuperaRequisicaoViagem();
		RequisicaoViagemDAO requisicaoViagemDAO = new RequisicaoViagemDAO();
		requisicaoViagemDAO.setTransactionControler(getTransacao());
		IntegranteViagemDao integranteViagemDao = new IntegranteViagemDao();
		integranteViagemDao.setTransactionControler(getTransacao());
		Collection<IntegranteViagemDTO> colIntegrantes =  integranteViagemDao.recuperaIntegrantesViagemByIdSolicitacaoEstado(requisicaoViagemDto.getIdSolicitacaoServico(), RequisicaoViagemDTO.AGUARDANDO_PLANEJAMENTO);
		
		if(colIntegrantes != null && colIntegrantes.size() > 0){
			for(IntegranteViagemDTO integranteViagemDTO : colIntegrantes){
				integranteViagemDTO.setEstado(RequisicaoViagemDTO.AGUARDANDO_APROVACAO);
				integranteViagemDao.updateNotNull(integranteViagemDTO);
			}
		}	
		requisicaoViagemDto = (RequisicaoViagemDTO) requisicaoViagemDAO.restore(requisicaoViagemDto);
		requisicaoViagemDto.setEstado(RequisicaoViagemDTO.AGUARDANDO_APROVACAO);
		requisicaoViagemDAO.updateNotNull(requisicaoViagemDto);
		
		return true;
	}
    
    public void cancelaTarefasDuplicadas(Tarefa tarefa) throws Exception{
		SolicitacaoServicoDTO solicitacaoDto = getSolicitacaoServicoDto();
		
		Collection<TarefaFluxoDTO> colTarefas = new TarefaFluxoDao().findDisponiveisByIdTarefaEstado(solicitacaoDto.getIdSolicitacaoServico(), tarefa.getElementoFluxoDto().getNome());
		String motivo = "cancela tarefa duplicada na mesma etapa";
		if(colTarefas != null && !colTarefas.isEmpty()){
			for(TarefaFluxoDTO tarefaDto: colTarefas){
				if(tarefaDto != null && !tarefaDto.getIdItemTrabalho().equals(tarefa.getIdItemTrabalho())){
					tarefaDto = this.recuperaTarefa(tarefaDto.getIdItemTrabalho());
					this.cancelaTarefa(null, solicitacaoDto, tarefaDto, motivo);
				}
			}
		}
	}
  
    /**
     * Valida se alguma prestação de contas aguarda conferência
     * @return
     * @throws Exception
     */
    public boolean isConferencia() throws Exception{
    	IntegranteViagemService integranteViagemService = (IntegranteViagemService) ServiceLocator.getInstance().getService(IntegranteViagemService.class, null);
    	Collection<IntegranteViagemDTO> colIntegrantes = new ArrayList<IntegranteViagemDTO>();
    	RequisicaoViagemDTO requisicaoViagemDto = this.recuperaRequisicaoViagem();
    
    	colIntegrantes = integranteViagemService.recuperaIntegrantesViagemByIdSolicitacaoEstado(requisicaoViagemDto.getIdSolicitacaoServico(), RequisicaoViagemDTO.AGUARDANDO_CONFERENCIA);
    	if(colIntegrantes != null && !colIntegrantes.isEmpty()){
    		return true;
    	}else{
    		return false;
    	}
    }	
}