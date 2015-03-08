package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.ClienteDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.ContratosGruposDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.EscalonamentoDTO;
import br.com.centralit.citcorpore.bean.FornecedorDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.PrioridadeDTO;
import br.com.centralit.citcorpore.bean.RegraEscalonamentoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.bean.TipoDemandaServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.PrioridadeDao;
import br.com.centralit.citcorpore.integracao.TipoDemandaServicoDao;
import br.com.centralit.citcorpore.negocio.ClienteService;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.ContratosGruposService;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.EscalonamentoService;
import br.com.centralit.citcorpore.negocio.FornecedorService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.RegraEscalonamentoService;
import br.com.centralit.citcorpore.negocio.ServicoService;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.Util;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

public class RegraEscalonamento extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request,"citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '"+ Constantes.getValue("SERVER_ADDRESS")+ request.getContextPath() + "'");
			return;
		}
		
		HTMLSelect idTipoGerenciamento = (HTMLSelect) document.getSelectById("idTipoGerenciamento");
		idTipoGerenciamento.removeAllOptions();
		idTipoGerenciamento.addOption("1", UtilI18N.internacionaliza(request, "citcorpore.comum.solicitacao"));
		idTipoGerenciamento.addOption("2", UtilI18N.internacionaliza(request, "problema.problema"));
		idTipoGerenciamento.addOption("3", UtilI18N.internacionaliza(request, "menu.relatorio.gerMudanca"));
		
		
		HTMLSelect tipoDataEscalonamento = (HTMLSelect) document.getSelectById("tipoDataEscalonamento");
		tipoDataEscalonamento.removeAllOptions();
		tipoDataEscalonamento.addOption("1", UtilI18N.internacionaliza(request, "citcorpore.comum.dataCriacao"));
		tipoDataEscalonamento.addOption("2", UtilI18N.internacionaliza(request, "citcorpore.comum.dataUltimaOcorrencia"));
		
		preencherComboGrupoExecutor(document, request, response);
		
		HTMLSelect idTipoDemandaServico = (HTMLSelect) document.getSelectById("idTipoDemandaServico");
		idTipoDemandaServico.removeAllOptions();
		ArrayList<TipoDemandaServicoDTO> listaTipoDemandaServico;
		TipoDemandaServicoDao tipoDemandaServicoDao = new TipoDemandaServicoDao();
		listaTipoDemandaServico = (ArrayList<TipoDemandaServicoDTO>) tipoDemandaServicoDao.findByClassificacao("'R','I'");
		idTipoDemandaServico.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		for (TipoDemandaServicoDTO tipoDemandaServicoDTO : listaTipoDemandaServico) {
			idTipoDemandaServico.addOption(String.valueOf(tipoDemandaServicoDTO.getIdTipoDemandaServico()), tipoDemandaServicoDTO.getNomeTipoDemandaServico());
		}
		
		HTMLSelect idPrioridade = (HTMLSelect) document.getSelectById("idPrioridade");
		idPrioridade.removeAllOptions();
		ArrayList<PrioridadeDTO> listaPrioridades;
		PrioridadeDao prioridadeDao = new PrioridadeDao();
		listaPrioridades = (ArrayList<PrioridadeDTO>) prioridadeDao.list();
		idPrioridade.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		for (PrioridadeDTO prioridadeDTO : listaPrioridades) {
			idPrioridade.addOption(String.valueOf(prioridadeDTO.getIdPrioridade()), Util.tratarAspasSimples(Util.retiraBarraInvertida(prioridadeDTO.getNomePrioridade())));
		}		
		
		HTMLSelect urgencia = (HTMLSelect) document.getSelectById("urgencia");
		urgencia.removeAllOptions();
		urgencia.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		urgencia.addOption("B", UtilI18N.internacionaliza(request, "citcorpore.comum.baixa"));
		urgencia.addOption("M", UtilI18N.internacionaliza(request, "citcorpore.comum.media"));
		urgencia.addOption("A", UtilI18N.internacionaliza(request, "citcorpore.comum.alta"));

		HTMLSelect impacto = (HTMLSelect) document.getSelectById("impacto");
		impacto.removeAllOptions();
		impacto.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		impacto.addOption("B", UtilI18N.internacionaliza(request, "citcorpore.comum.baixa"));
		impacto.addOption("M", UtilI18N.internacionaliza(request, "citcorpore.comum.media"));
		impacto.addOption("A", UtilI18N.internacionaliza(request, "citcorpore.comum.alta"));
		
		HTMLSelect intervaloNotificacao = (HTMLSelect) document.getSelectById("intervaloNotificacao");
		intervaloNotificacao.removeAllOptions();
		//intervaloNotificacao.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione")); Retirado o critério de obrigatório da tela, mas o banco necessita de informação
		intervaloNotificacao.addOption("15", "15");
		intervaloNotificacao.addOption("30", "30");
		intervaloNotificacao.addOption("45", "45");
		intervaloNotificacao.addOption("60", "60   (1:00h)");
		intervaloNotificacao.addOption("75", "75   (1:15h)");
		intervaloNotificacao.addOption("90", "90   (1:30h)");
		intervaloNotificacao.addOption("105", "105 (1:45h)");
		intervaloNotificacao.addOption("120", "120 (2:00h)");
		intervaloNotificacao.addOption("135", "135 (2:15h)");
		intervaloNotificacao.addOption("150", "150 (2:30h)");
		intervaloNotificacao.addOption("165", "165 (2:45h)");
		intervaloNotificacao.addOption("180", "180 (3:00h)");
		intervaloNotificacao.addOption("195", "195 (3:15h)");
		intervaloNotificacao.addOption("210", "210 (3:30h)");
		intervaloNotificacao.addOption("225", "225 (3:45h)");
		intervaloNotificacao.addOption("240", "240 (4:00h)");
		intervaloNotificacao.addOption("255", "255 (4:15h)");
		intervaloNotificacao.addOption("270", "270 (4:30h)");
		intervaloNotificacao.addOption("285", "285 (4:45h)");
		intervaloNotificacao.addOption("300", "300 (5:00h)");
		intervaloNotificacao.addOption("315", "315 (5:15h)");
		intervaloNotificacao.addOption("330", "330 (5:30h)");
		intervaloNotificacao.addOption("345", "345 (5:45h)");
		intervaloNotificacao.addOption("360", "360 (6:00h)");
		
		HTMLSelect prazoExecucao = (HTMLSelect) document.getSelectById("prazoExecucao");
		prazoExecucao.removeAllOptions();
		prazoExecucao.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		prazoExecucao.addOption("15", "15");
		prazoExecucao.addOption("30", "30");
		prazoExecucao.addOption("45", "45");
		prazoExecucao.addOption("60", "60   (1:00h)");
		prazoExecucao.addOption("75", "75   (1:15h)");
		prazoExecucao.addOption("90", "90   (1:30h)");
		prazoExecucao.addOption("105", "105 (1:45h)");
		prazoExecucao.addOption("120", "120 (2:00h)");
		prazoExecucao.addOption("135", "135 (2:15h)");
		prazoExecucao.addOption("150", "150 (2:30h)");
		prazoExecucao.addOption("165", "165 (2:45h)");
		prazoExecucao.addOption("180", "180 (3:00h)");
		prazoExecucao.addOption("195", "195 (3:15h)");
		prazoExecucao.addOption("210", "210 (3:30h)");
		prazoExecucao.addOption("225", "225 (3:45h)");
		prazoExecucao.addOption("240", "240 (4:00h)");
		prazoExecucao.addOption("255", "255 (4:15h)");
		prazoExecucao.addOption("270", "270 (4:30h)");
		prazoExecucao.addOption("285", "285 (4:45h)");
		prazoExecucao.addOption("300", "300 (5:00h)");
		prazoExecucao.addOption("315", "315 (5:15h)");
		prazoExecucao.addOption("330", "330 (5:30h)");
		prazoExecucao.addOption("345", "345 (5:45h)");
		prazoExecucao.addOption("360", "360 (6:00h)");
		
		HTMLSelect prazoCriarProblema = (HTMLSelect) document.getSelectById("prazoCriarProblema");
		prazoCriarProblema.removeAllOptions();
		//prazoCriarProblema.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		prazoCriarProblema.addOption("15", "15");
		prazoCriarProblema.addOption("30", "30");
		prazoCriarProblema.addOption("45", "45");
		prazoCriarProblema.addOption("60", "60   (1:00h)");
		prazoCriarProblema.addOption("75", "75   (1:15h)");
		prazoCriarProblema.addOption("90", "90   (1:30h)");
		prazoCriarProblema.addOption("105", "105 (1:45h)");
		prazoCriarProblema.addOption("120", "120 (2:00h)");
		prazoCriarProblema.addOption("135", "135 (2:15h)");
		prazoCriarProblema.addOption("150", "150 (2:30h)");
		prazoCriarProblema.addOption("165", "165 (2:45h)");
		prazoCriarProblema.addOption("180", "180 (3:00h)");
		prazoCriarProblema.addOption("195", "195 (3:15h)");
		prazoCriarProblema.addOption("210", "210 (3:30h)");
		prazoCriarProblema.addOption("225", "225 (3:45h)");
		prazoCriarProblema.addOption("240", "240 (4:00h)");
		prazoCriarProblema.addOption("255", "255 (4:15h)");
		prazoCriarProblema.addOption("270", "270 (4:30h)");
		prazoCriarProblema.addOption("285", "285 (4:45h)");
		prazoCriarProblema.addOption("300", "300 (5:00h)");
		prazoCriarProblema.addOption("315", "315 (5:15h)");
		prazoCriarProblema.addOption("330", "330 (5:30h)");
		prazoCriarProblema.addOption("345", "345 (5:45h)");
		prazoCriarProblema.addOption("360", "360 (6:00h)");
		
		preencherComboContrato(document, request, response);
	}
	
	public void preencherComboGrupoExecutor(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception{

		HTMLSelect idGrupoAtual = (HTMLSelect) document.getSelectById("idGrupoAtual");
		idGrupoAtual.removeAllOptions();
		idGrupoAtual.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		GrupoService grupoSegurancaService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		Collection<GrupoDTO> colGrupos = grupoSegurancaService.listGruposServiceDesk();
		if (colGrupos != null)
			idGrupoAtual.addOptions(colGrupos, "idGrupo", "nome", null);
	}

	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		RegraEscalonamentoDTO regraEscalonamentoDTO = (RegraEscalonamentoDTO) document.getBean();
		RegraEscalonamentoService service = (RegraEscalonamentoService) ServiceLocator.getInstance().getService(RegraEscalonamentoService.class, WebUtil.getUsuarioSistema(request));
		regraEscalonamentoDTO = (RegraEscalonamentoDTO) service.restore(regraEscalonamentoDTO);
		
		if ((regraEscalonamentoDTO!=null)&&(regraEscalonamentoDTO.getIdRegraEscalonamento()!=null)){
			
			EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, WebUtil.getUsuarioSistema(request));
			if(regraEscalonamentoDTO.getIdSolicitante() != null){
				EmpregadoDTO empregadoDTO = empregadoService.restoreByIdEmpregado(regraEscalonamentoDTO.getIdSolicitante());
				regraEscalonamentoDTO.setNomeSolicitante(((empregadoDTO==null)||(empregadoDTO.getNome()==null))?"":empregadoDTO.getNome());
			}
			ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, WebUtil.getUsuarioSistema(request));
			if(regraEscalonamentoDTO.getIdServico() != null){
				ServicoDTO servicoDTO = servicoService.findById(regraEscalonamentoDTO.getIdServico());
				regraEscalonamentoDTO.setServico(((servicoDTO==null)||(servicoDTO.getNomeServico()==null))?"":servicoDTO.getNomeServico());				
			}

			if (regraEscalonamentoDTO.getIdGrupo() != null) {
				GrupoService grupoService = (GrupoService) ServiceLocator
						.getInstance().getService(GrupoService.class,
								WebUtil.getUsuarioSistema(request));
				GrupoDTO grupoDTO = grupoService
						.listGrupoById(regraEscalonamentoDTO.getIdGrupo());
				regraEscalonamentoDTO.setGrupo(((grupoDTO == null) || (grupoDTO
						.getDescricao() == null)) ? "" : grupoDTO
						.getDescricao());
			}

		}
		atualizaTblGrupoExecutor(document, request, response,regraEscalonamentoDTO);	
		HTMLForm form = document.getForm("form");
		form.clear();
		if(regraEscalonamentoDTO != null){
			form.setValues(regraEscalonamentoDTO);
		}
	}
	
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		RegraEscalonamentoDTO regraEscalonamentoDTO = (RegraEscalonamentoDTO) document.getBean();
		RegraEscalonamentoService service = (RegraEscalonamentoService) ServiceLocator.getInstance().getService(RegraEscalonamentoService.class, null);
		Collection<EscalonamentoDTO> grupos_serialize = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(EscalonamentoDTO.class, "grupos_serialize", request);
		if (grupos_serialize != null) {
			regraEscalonamentoDTO.setColEscalonamentoDTOs(grupos_serialize);
		}
		if (regraEscalonamentoDTO != null && service != null) {
			regraEscalonamentoDTO.setCriaProblema(regraEscalonamentoDTO.getCriaProblema()==null ? "N" : regraEscalonamentoDTO.getCriaProblema());
			regraEscalonamentoDTO.setEnviarEmail(regraEscalonamentoDTO.getEnviarEmail() ==null ? "N" : regraEscalonamentoDTO.getEnviarEmail());
			if (regraEscalonamentoDTO.getIdRegraEscalonamento() == null) {
				service.create(regraEscalonamentoDTO);
				document.alert(UtilI18N.internacionaliza(request, "MSG05"));
			} else {
				service.update(regraEscalonamentoDTO);
				document.alert(UtilI18N.internacionaliza(request, "MSG06"));
			}
			HTMLForm form = document.getForm("form");
			form.clear();
			document.setBean(new RegraEscalonamentoDTO());
			HTMLTable tblGrupoExecutor = document.getTableById("tblGrupoExecutor");
			tblGrupoExecutor.deleteAllRows();
			load(document, request, response);
		}
	}
	
	public void excluir(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		RegraEscalonamentoDTO regraEscalonamentoDTO = (RegraEscalonamentoDTO) document.getBean();
		RegraEscalonamentoService service = (RegraEscalonamentoService) ServiceLocator.getInstance().getService(RegraEscalonamentoService.class, null);
		if (regraEscalonamentoDTO.getIdRegraEscalonamento() != null) {
			service.delete(regraEscalonamentoDTO);
			document.alert(UtilI18N.internacionaliza(request, "regraEscalonamento.excluida"));
		}
		HTMLForm form = document.getForm("form");
		form.clear();
		document.setBean(new RegraEscalonamentoDTO());
		HTMLTable tblGrupoExecutor = document.getTableById("tblGrupoExecutor");
		tblGrupoExecutor.deleteAllRows();
	}
	
	@Override
	public Class getBeanClass() {
		return RegraEscalonamentoDTO.class;
	}
	
	private boolean isContratoInList(Integer idContrato, Collection colContratosColab) {
		if (colContratosColab != null) {
			for (Iterator it = colContratosColab.iterator(); it.hasNext();) {
				ContratosGruposDTO contratosGruposDTO = (ContratosGruposDTO) it.next();
				if (contratosGruposDTO.getIdContrato().intValue() == idContrato.intValue()) {
					return true;
				}
			}
		}
		return false;
	}	
	
	public void preencherComboContrato(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception{
		RegraEscalonamentoDTO regraEscalonamentoDTO = (RegraEscalonamentoDTO) document.getBean();
		
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		ClienteService clienteService = (ClienteService) ServiceLocator.getInstance().getService(ClienteService.class, null);
		FornecedorService fornecedorService = (FornecedorService) ServiceLocator.getInstance().getService(FornecedorService.class, null);
		ContratosGruposService contratosGruposService = (ContratosGruposService) ServiceLocator.getInstance().getService(ContratosGruposService.class, null);
		Collection colContratos = contratoService.list();
		ContratoDTO contratoDtoAux = new ContratoDTO();
		
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request,"citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '"+ Constantes.getValue("SERVER_ADDRESS")+ request.getContextPath() + "'");
			return;
		}
		
		String COLABORADORES_VINC_CONTRATOS = ParametroUtil.getValorParametroCitSmartHashMap(br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.COLABORADORES_VINC_CONTRATOS, "N");
		if (COLABORADORES_VINC_CONTRATOS == null) {
			COLABORADORES_VINC_CONTRATOS = "N";
		}
		Collection colContratosColab = null;
		if (COLABORADORES_VINC_CONTRATOS.equalsIgnoreCase("S")) {
			colContratosColab = contratosGruposService.findByIdEmpregado(usuario.getIdEmpregado());
		}
		Collection<ContratoDTO> listaContratos = new ArrayList<ContratoDTO>();
		((HTMLSelect) document.getSelectById("idContrato")).removeAllOptions();
		if (colContratos != null) {
			if (colContratos.size() > 1) {
				((HTMLSelect) document.getSelectById("idContrato")).addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
			}

			for (Iterator it = colContratos.iterator(); it.hasNext();) {
				ContratoDTO contratoDto = (ContratoDTO) it.next();
				if (contratoDto.getDeleted() == null || !contratoDto.getDeleted().equalsIgnoreCase("y")) {
					if (COLABORADORES_VINC_CONTRATOS.equalsIgnoreCase("S")) { // Se parametro de colaboradores por contrato ativo, entao filtra.
						if (colContratosColab == null) {
							continue;
						}
						if (!isContratoInList(contratoDto.getIdContrato(), colContratosColab)) {
							continue;
						}
					}
		
					if (regraEscalonamentoDTO != null && regraEscalonamentoDTO.getIdRegraEscalonamento() != null) {
						this.restore(document, request, response);
					}
				
					String nomeCliente = "";
					String nomeForn = "";
					ClienteDTO clienteDto = new ClienteDTO();
					clienteDto.setIdCliente(contratoDto.getIdCliente());
					clienteDto = (ClienteDTO) clienteService.restore(clienteDto);
					if (clienteDto != null) {
						nomeCliente = clienteDto.getNomeRazaoSocial();
					}
					FornecedorDTO fornecedorDto = new FornecedorDTO();
					fornecedorDto.setIdFornecedor(contratoDto.getIdFornecedor());
					fornecedorDto = (FornecedorDTO) fornecedorService.restore(fornecedorDto);
					if (fornecedorDto != null) {
						nomeForn = fornecedorDto.getRazaoSocial();
					}
					contratoDtoAux.setIdContrato(contratoDto.getIdContrato());
					if (contratoDto.getSituacao().equalsIgnoreCase("A")) {
						String nomeContrato = "" + contratoDto.getNumero() + " de " + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, contratoDto.getDataContrato(), WebUtil.getLanguage(request)) + " (" + nomeCliente + " - " + nomeForn + ")";
						((HTMLSelect) document.getSelectById("idContrato")).addOption("" + contratoDto.getIdContrato(), nomeContrato);
						contratoDto.setNome(nomeContrato);
						listaContratos.add(contratoDto);
					}
				}
			}
		}
	}
	
	public void atualizaTblGrupoExecutor(DocumentHTML document, HttpServletRequest request, HttpServletResponse response,RegraEscalonamentoDTO regraEscalonamentoDTO) throws Exception{
		//Restaurar tabela de escalonamento de grupos
		HTMLTable tblGrupoExecutor = document.getTableById("tblGrupoExecutor");
		tblGrupoExecutor.deleteAllRows();
		
		EscalonamentoService escalonamentoService = (EscalonamentoService) ServiceLocator.getInstance().getService(EscalonamentoService.class, WebUtil.getUsuarioSistema(request));
		Collection<EscalonamentoDTO> colEscalonamentoDTOs = escalonamentoService.findByRegraEscalonamento(regraEscalonamentoDTO);
		regraEscalonamentoDTO.setColEscalonamentoDTOs(colEscalonamentoDTOs);
		
		if ((tblGrupoExecutor != null)&&(colEscalonamentoDTOs != null)) {
			tblGrupoExecutor.addRowsByCollection(colEscalonamentoDTOs, new String[] { "", "idGrupoExecutor", "descricao", "prazoExecucao", "idPrioridade", "descrPrioridade" }, null, "", new String[] { "gerarImgDelGrupoExecutor" }, null, null);
		}
		document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblGrupoExecutor', 'tblGrupoExecutor');");
	}
}