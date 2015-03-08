package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.AcordoNivelServicoDTO;
import br.com.centralit.citcorpore.bean.AcordoNivelServicoHistoricoDTO;
import br.com.centralit.citcorpore.bean.ClienteDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.FornecedorDTO;
import br.com.centralit.citcorpore.bean.ModeloEmailDTO;
import br.com.centralit.citcorpore.bean.PrioridadeAcordoNivelServicoDTO;
import br.com.centralit.citcorpore.bean.PrioridadeServicoUsuarioDTO;
import br.com.centralit.citcorpore.bean.RequisitoSLADTO;
import br.com.centralit.citcorpore.bean.RevisarSlaDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.bean.SlaRequisitoSlaDTO;
import br.com.centralit.citcorpore.bean.TempoAcordoNivelServicoDTO;
import br.com.centralit.citcorpore.bean.UnidadeDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.AcordoNivelServicoHistoricoDao;
import br.com.centralit.citcorpore.metainfo.complementos.ComplementoSLA_Grupos;
import br.com.centralit.citcorpore.metainfo.complementos.ComplementoSLA_Prioridade;
import br.com.centralit.citcorpore.metainfo.complementos.ComplementoSLA_TempoAuto;
import br.com.centralit.citcorpore.negocio.AcordoNivelServicoService;
import br.com.centralit.citcorpore.negocio.ClienteService;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.FornecedorService;
import br.com.centralit.citcorpore.negocio.ModeloEmailService;
import br.com.centralit.citcorpore.negocio.PrioridadeAcordoNivelServicoService;
import br.com.centralit.citcorpore.negocio.PrioridadeServicoUsuarioService;
import br.com.centralit.citcorpore.negocio.RequisitoSLAService;
import br.com.centralit.citcorpore.negocio.RevisarSlaService;
import br.com.centralit.citcorpore.negocio.ServicoContratoService;
import br.com.centralit.citcorpore.negocio.ServicoService;
import br.com.centralit.citcorpore.negocio.SlaRequisitoSlaService;
import br.com.centralit.citcorpore.negocio.TempoAcordoNivelServicoService;
import br.com.centralit.citcorpore.negocio.UnidadeService;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citgerencial.util.Util;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.Reflexao;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilHTML;
import br.com.citframework.util.UtilHashMaps;
import br.com.citframework.util.UtilI18N;

/**
 * Classe que representa o form do Cadastro de Acordos de Nível de Serviço Geral
 * 
 * @author rodrigo.oliveira
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class AcordoNivelServico extends AjaxFormAction {

	private ComplementoSLA_TempoAuto complementoSLA_TempoAuto;
	private ComplementoSLA_Prioridade complementoSLA_Prioridade;
	private ComplementoSLA_Grupos complementoSLA_Grupos;
	private AcordoNivelServicoService acordoNivelServicoService;
	private PrioridadeAcordoNivelServicoService prioridadeAcordoNivelServicoService;
	private PrioridadeServicoUsuarioService prioridadeServicoUsuarioService;
	private SlaRequisitoSlaService slaRequisitoSlaService;
	private RevisarSlaService revisarSlaService;
	private TempoAcordoNivelServicoService tempoAcordoNivelServicoService;
	private UnidadeService unidadeService;
	private EmpregadoService empregadoService;
	private RequisitoSLAService requisitoSLAService;

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		UsuarioDTO usuarioDto = WebUtil.getUsuario(request);
		if (usuarioDto == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		String idrequisito = request.getParameter("idRequisitoSla");
		String assunto = request.getParameter("assunto");

		if (idrequisito != null && assunto != null) {
			document.executeScript("insereRowRequisitoSLA(" + idrequisito + ", '" + assunto + "', '" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, new Date(), WebUtil.getLanguage(request)) + "');");
		}

		// Método para montar as grids da parte inferior
		mostraContratoClienteSLA(document, request, response);
		mostraContratoAnoSLA(document, request, response);
		mostraContratoTerceiroSLA(document, request, response);
		mostraHistoricoAuditoriaSLA(document, request, response);

		montaTelaTempoAuto(document, request, response);

		preencheComboPrioridadAuto(document, request, response);
		preencheComboGrupo(document, request, response);
		preencheComboEmails(document, request, response);

		alimentaCamposIniciais(document);

	}

	@Override
	public Class getBeanClass() {
		return AcordoNivelServicoDTO.class;
	}

	private void alimentaCamposIniciais(DocumentHTML document) throws Exception {
		document.executeScript("$('#pertmiteMudarImpUrgN').attr('checked',true)");
		document.executeScript("$('#situacaoAtivo').attr('checked',true)");
		document.executeScript("document.getElementById('impacto').options[0].selected = 'selected'");
		document.executeScript("document.getElementById('urgencia').options[0].selected = 'selected'");
		document.executeScript("document.getElementById('tipo').options[0].selected = 'selected'");
		document.executeScript("avaliaTipoSLA()");
		valoresPadroes(document);
	}

	private void valoresPadroes(DocumentHTML document) throws Exception {
		for (int i = 1; i <= 5; i++) {
			if (document.getElementById("HH-1-" + i).getValue() == null)
				document.getElementById("HH-1-" + i).setValue("0");

			if (document.getElementById("HH-2-" + i).getValue() == null)
				document.getElementById("HH-2-" + i).setValue("0");

			if (document.getElementById("MM-1-" + i).getValue() == null)
				document.getElementById("MM-1-" + i).setValue("0");

			if (document.getElementById("MM-2-" + i).getValue() == null)
				document.getElementById("MM-2-" + i).setValue("0");
		}
	}

	/**
	 * Limpa formulário.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void limpar(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.load(document, request, response);
	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		AcordoNivelServicoDTO acordoNivelServicoDTO = (AcordoNivelServicoDTO) document.getBean();

		Integer[] hhCaptura = new Integer[5];
		Integer[] hhResolucao = new Integer[5];
		Integer[] mmCaptura = new Integer[5];
		Integer[] mmResolucao = new Integer[5];

		for (int i = 1; i <= 5; i++) {
			hhCaptura[i - 1] = 0;
			hhResolucao[i - 1] = 0;

			mmCaptura[i - 1] = 0;
			mmResolucao[i - 1] = 0;

			try {
				hhCaptura[i - 1] = new Integer((String) document.getValuesForm().get("HH-1-" + i));
				acordoNivelServicoDTO.setHhCaptura(hhCaptura);
			} catch (Exception e) {
			}
			try {
				hhResolucao[i - 1] = new Integer((String) document.getValuesForm().get("HH-2-" + i));
				acordoNivelServicoDTO.setHhResolucao(hhResolucao);
			} catch (Exception e) {
			}
			try {
				mmCaptura[i - 1] = new Integer((String) document.getValuesForm().get("MM-1-" + i));
				acordoNivelServicoDTO.setMmCaptura(mmCaptura);
			} catch (Exception e) {
			}
			try {
				mmResolucao[i - 1] = new Integer((String) document.getValuesForm().get("MM-2-" + i));
				acordoNivelServicoDTO.setMmResolucao(mmResolucao);
			} catch (Exception e) {
			}
		}

		// Preenche dados do histórico
		AcordoNivelServicoHistoricoDTO acordoNivelServicoHistoricoDTO = new AcordoNivelServicoHistoricoDTO();

		UsuarioDTO usuarioDto = WebUtil.getUsuario(request);

		Reflexao.copyPropertyValues(acordoNivelServicoDTO, acordoNivelServicoHistoricoDTO);

		acordoNivelServicoHistoricoDTO.setCriadoEm(UtilDatas.getDataHoraAtual());
		acordoNivelServicoHistoricoDTO.setModificadoEm(UtilDatas.getDataHoraAtual());
		acordoNivelServicoHistoricoDTO.setCriadoPor(usuarioDto.getNomeUsuario());
		acordoNivelServicoHistoricoDTO.setModificadoPor(usuarioDto.getNomeUsuario());
		acordoNivelServicoHistoricoDTO.setConteudodados(UtilHashMaps.generateString(document.getValuesForm()));

		// Deserializando PrioridadeUnidade
		List<PrioridadeAcordoNivelServicoDTO> listaPrioridadeUnidadeSLA = ((ArrayList<PrioridadeAcordoNivelServicoDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(
				PrioridadeAcordoNivelServicoDTO.class, "prioridadeUnidadeSerializados", request));
		if (listaPrioridadeUnidadeSLA != null) {
			acordoNivelServicoDTO.setListaPrioridadeUnidade(listaPrioridadeUnidadeSLA);
		}

		// Deserializando PrioridadeUsuario
		List<PrioridadeServicoUsuarioDTO> listaPrioridadeUsuarioSLA = ((ArrayList<PrioridadeServicoUsuarioDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(
				PrioridadeServicoUsuarioDTO.class, "prioridadeUsuarioSerializados", request));
		if (listaPrioridadeUsuarioSLA != null) {
			acordoNivelServicoDTO.setListaPrioridadeUsuario(listaPrioridadeUsuarioSLA);
		}

		// Deserializando RequisitosSLA
		List<SlaRequisitoSlaDTO> listaSlaRequisitoSLA = ((ArrayList<SlaRequisitoSlaDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(SlaRequisitoSlaDTO.class,
				"requisitoSlaSerializados", request));
		if (listaSlaRequisitoSLA != null) {
			acordoNivelServicoDTO.setListaSlaRequisitoSlaDTO(listaSlaRequisitoSLA);
		}

		// Deserializando RevisarSLA
		List<RevisarSlaDTO> listaRevisarSLA = ((ArrayList<RevisarSlaDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(RevisarSlaDTO.class, "revisarSlaSerializados", request));
		if (listaRevisarSLA != null) {
			acordoNivelServicoDTO.setListaRevisarSlaDTO(listaRevisarSLA);
		}

		if (acordoNivelServicoDTO.getIdAcordoNivelServico() != null) {
			try {
				// Preenche informações
				acordoNivelServicoDTO.setModificadoEm(UtilDatas.getDataHoraAtual());
				acordoNivelServicoDTO.setModificadoPor(usuarioDto.getNomeUsuario());

				// Atualiza Acordo
				getAcordoNivelServicoService().update(acordoNivelServicoDTO, acordoNivelServicoHistoricoDTO);

				document.alert(UtilI18N.internacionaliza(request, "MSG06"));

				document.executeScript("limpar()");
				HTMLForm form = document.getForm("form");
				form.clear();

			} catch (Exception e) {
				document.alert(UtilI18N.internacionaliza(request, "MSE02"));
				e.printStackTrace();
			} finally {
				document.executeScript("fechar_aguarde()");
			}

		} else {
			try {
				// Preenche informações
				acordoNivelServicoDTO.setCriadoEm(UtilDatas.getDataHoraAtual());
				acordoNivelServicoDTO.setModificadoEm(UtilDatas.getDataHoraAtual());
				acordoNivelServicoDTO.setCriadoPor(usuarioDto.getNomeUsuario());
				acordoNivelServicoDTO.setModificadoPor(usuarioDto.getNomeUsuario());

				// Cria Acordo
				getAcordoNivelServicoService().create(acordoNivelServicoDTO, acordoNivelServicoHistoricoDTO);

				document.alert(UtilI18N.internacionaliza(request, "MSG05"));

				document.executeScript("limpar()");
				HTMLForm form = document.getForm("form");
				form.clear();

			} catch (Exception e) {
				document.alert(UtilI18N.internacionaliza(request, "MSE02"));
				e.printStackTrace();
			} finally {
				document.executeScript("fechar_aguarde()");
			}

		}

	}

	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").show();
		AcordoNivelServicoDTO acordoNivelServicoDTO = (AcordoNivelServicoDTO) document.getBean();

		acordoNivelServicoDTO = (AcordoNivelServicoDTO) getAcordoNivelServicoService().restore(acordoNivelServicoDTO);

		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(acordoNivelServicoDTO);
		document.executeScript("deleteAllRowsPrioridadeUnidade()");
		document.executeScript("deleteAllRowsPrioridadeUsuario()");
		document.executeScript("deleteAllRowsRequisitoSLA()");
		document.executeScript("deleteAllRowsRevisarSLA()");

		Integer idAcordoNivelServico = acordoNivelServicoDTO.getIdAcordoNivelServico();

		if (idAcordoNivelServico != null) {
			for (int i = 1; i <= 5; i++) {
				Collection colAux1 = getTempoAcordoNivelServicoService().findByIdAcordoAndFaseAndIdPrioridade(idAcordoNivelServico.intValue(), 1, i);
				if (colAux1 != null && colAux1.size() > 0) {
					TempoAcordoNivelServicoDTO tempoAcordoNivelServicoDTO = (TempoAcordoNivelServicoDTO) ((List) colAux1).get(0);
					document.getElementById("HH-1-" + i).setValue(tempoAcordoNivelServicoDTO.getTempoHH().toString());
					document.getElementById("MM-1-" + i).setValue(tempoAcordoNivelServicoDTO.getTempoMM().toString());
				}
				colAux1 = getTempoAcordoNivelServicoService().findByIdAcordoAndFaseAndIdPrioridade(idAcordoNivelServico.intValue(), 2, i);
				if (colAux1 != null && colAux1.size() > 0) {
					TempoAcordoNivelServicoDTO tempoAcordoNivelServicoDTO = (TempoAcordoNivelServicoDTO) ((List) colAux1).get(0);
					document.getElementById("HH-2-" + i).setValue(tempoAcordoNivelServicoDTO.getTempoHH().toString());
					document.getElementById("MM-2-" + i).setValue(tempoAcordoNivelServicoDTO.getTempoMM().toString());
				}
			}

			List<PrioridadeAcordoNivelServicoDTO> listAcordoNivelServicoDTOs = (List<PrioridadeAcordoNivelServicoDTO>) getAcordoPrioridadeAcordoNivelServicoService().findByIdAcordoNivelServico(
					idAcordoNivelServico);
			if (listAcordoNivelServicoDTOs != null && !listAcordoNivelServicoDTOs.isEmpty()) {
				for (PrioridadeAcordoNivelServicoDTO prioridadeAcordoNivelServicoDTO : listAcordoNivelServicoDTOs) {
					if (prioridadeAcordoNivelServicoDTO.getIdUnidade() != null && prioridadeAcordoNivelServicoDTO.getDataFim() == null) {
						UnidadeDTO unidadeDTO = new UnidadeDTO();
						List<UnidadeDTO> listaUnidadeDTOs = (List<UnidadeDTO>) getUnidadeService().findById(prioridadeAcordoNivelServicoDTO.getIdUnidade());
						if (listaUnidadeDTOs != null) {
							unidadeDTO = listaUnidadeDTOs.get(0);
						}
						document.executeScript("insereRowUnidade(" + prioridadeAcordoNivelServicoDTO.getIdUnidade() + ", '" + unidadeDTO.getNome() + "', "
								+ prioridadeAcordoNivelServicoDTO.getIdPrioridade() + ");");
					}
				}

			}

			List<PrioridadeServicoUsuarioDTO> lisPrioridadeServicoUsuarioDTOs = (List<PrioridadeServicoUsuarioDTO>) getPrioridadeServicoUsuarioService().findByIdAcordoNivelServico(
					idAcordoNivelServico);
			if (lisPrioridadeServicoUsuarioDTOs != null && !lisPrioridadeServicoUsuarioDTOs.isEmpty()) {
				for (PrioridadeServicoUsuarioDTO prioridadeServicoUsuarioDTO : lisPrioridadeServicoUsuarioDTOs) {
					if (prioridadeServicoUsuarioDTO.getIdUsuario() != null && prioridadeServicoUsuarioDTO.getDataFim() == null) {
						EmpregadoDTO empregadoDTO = new EmpregadoDTO();
						empregadoDTO = getEmpregadoService().restoreByIdEmpregado(prioridadeServicoUsuarioDTO.getIdUsuario());

						document.executeScript("insereRowUsuario(" + prioridadeServicoUsuarioDTO.getIdUsuario() + ", '" + empregadoDTO.getNome() + "', "
								+ prioridadeServicoUsuarioDTO.getIdPrioridade() + ");");
					}
				}
			}

			List<SlaRequisitoSlaDTO> listSlaRequisitoSlaDTOs = (List<SlaRequisitoSlaDTO>) getSlaRequisitoSlaService().findByIdAcordoNivelServico(idAcordoNivelServico);
			if (listSlaRequisitoSlaDTOs != null && !listSlaRequisitoSlaDTOs.isEmpty()) {
				for (SlaRequisitoSlaDTO slaRequisitoSlaDTO : listSlaRequisitoSlaDTOs) {
					if (slaRequisitoSlaDTO.getIdAcordoNivelServico() != null) {
						RequisitoSLADTO requisitoSLADTO = new RequisitoSLADTO();
						List<RequisitoSLADTO> listaRequisitoSLADTOs = (List<RequisitoSLADTO>) getRequisitoSLAService().findById(slaRequisitoSlaDTO.getIdRequisitoSLA());
						if (listaRequisitoSLADTOs != null && !listaRequisitoSLADTOs.isEmpty()) {
							requisitoSLADTO = listaRequisitoSLADTOs.get(0);
						}
						if (slaRequisitoSlaDTO.getDeleted() == null || !slaRequisitoSlaDTO.getDeleted().equalsIgnoreCase("y") && requisitoSLADTO.getAssunto()!= null) {
							document.executeScript("insereRowRequisitoSLA(" + slaRequisitoSlaDTO.getIdRequisitoSLA() + ", '" + requisitoSLADTO.getAssunto() + "', '"
									+ UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, slaRequisitoSlaDTO.getDataVinculacao(), WebUtil.getLanguage(request))+ "');");
						}
					}
				}
			}

			List<RevisarSlaDTO> lisRevisarSlaDTOs = (List<RevisarSlaDTO>) getRevisarSlaService().findByIdAcordoNivelServico(idAcordoNivelServico);
			if (lisRevisarSlaDTOs != null && !lisRevisarSlaDTOs.isEmpty()) {
				for (RevisarSlaDTO revisarSlaDTO : lisRevisarSlaDTOs) {
					if (revisarSlaDTO.getIdAcordoNivelServico() != null) {
						if (revisarSlaDTO.getDeleted() == null || !revisarSlaDTO.getDeleted().equalsIgnoreCase("y")) {
							document.executeScript("insereRevisarSLARow('" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, revisarSlaDTO.getDataRevisao(), WebUtil.getLanguage(request)) + "', '" + revisarSlaDTO.getDetalheRevisao() + "', '" + revisarSlaDTO.getObservacao() + "');");
						}
					}
				}
			}

			if (acordoNivelServicoDTO.getIdPrioridadeAuto1() != null) {
				document.getElementById("IDPRIORIDADEAUTO1").setValue(acordoNivelServicoDTO.getIdPrioridadeAuto1().toString());
			}

			if (acordoNivelServicoDTO.getIdGrupo1() != null) {
				document.getElementById("IDGRUPO1").setValue(acordoNivelServicoDTO.getIdGrupo1().toString());
			}

			if (acordoNivelServicoDTO.getIdEmail() != null) {
				document.getElementById("idEmail").setValue(acordoNivelServicoDTO.getIdEmail().toString());
			}

			if (acordoNivelServicoDTO.getTempoAuto() != null) {
				document.getElementById("TEMPOAUTO").setValue(Util.formatMoney(acordoNivelServicoDTO.getTempoAuto(), "###,###,##0.00"));
			}

		}

		// Método para montar as grids da parte inferior
		mostraContratoClienteSLA(document, request, response);
		mostraContratoAnoSLA(document, request, response);
		mostraContratoTerceiroSLA(document, request, response);
		mostraHistoricoAuditoriaSLA(document, request, response);
		valoresPadroes(document);

		document.executeScript("avaliaTipoSLA()");
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();

	}

	public void excluir(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		AcordoNivelServicoDTO acordoNivelServicoDTO = (AcordoNivelServicoDTO) document.getBean();

		UsuarioDTO usuarioDto = WebUtil.getUsuario(request);

		try {
			// Preenche informações
			acordoNivelServicoDTO.setModificadoEm(UtilDatas.getDataHoraAtual());
			acordoNivelServicoDTO.setModificadoPor(usuarioDto.getNomeUsuario());

			// Exclui acordos
			getAcordoNivelServicoService().excluir(acordoNivelServicoDTO);

			document.alert(UtilI18N.internacionaliza(request, "MSG07"));

			document.executeScript("limpar()");
			HTMLForm form = document.getForm("form");
			form.clear();

		} catch (Exception e) {
			document.alert(UtilI18N.internacionaliza(request, "MSE02"));
			e.printStackTrace();
		} finally {
			document.executeScript("fechar_aguarde()");
		}

	}

	private void preencheComboPrioridadAuto(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String resp = getComplementoSLA_Prioridade().execute(null, request, response);

		HTMLSelect combo = (HTMLSelect) document.getSelectById("IDPRIORIDADEAUTO1");
		combo.removeAllOptions();
		combo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

		combo.setInnerHTML(resp);

	}

	private void preencheComboGrupo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String resp = getComplementoSLA_Grupos().execute(null, request, response);

		HTMLSelect combo = (HTMLSelect) document.getSelectById("IDGRUPO1");
		combo.removeAllOptions();
		combo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

		combo.setInnerHTML(resp);
	}

	private void preencheComboEmails(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HTMLSelect combo = (HTMLSelect) document.getSelectById("idEmail");
		combo.removeAllOptions();
		combo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		ModeloEmailService modeloEmailService = (ModeloEmailService) ServiceLocator.getInstance().getService(ModeloEmailService.class, null);
		List<ModeloEmailDTO> listaEmails = (List<ModeloEmailDTO>) modeloEmailService.getAtivos();
		if (listaEmails != null) {
			for (ModeloEmailDTO lista : listaEmails) {
				combo.addOption(lista.getIdModeloEmail().toString(), lista.getTitulo());
			}
		}
	}

	private void montaTelaTempoAuto(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String resp = getComplementoSLA_TempoAuto().execute(null, request, response);
		document.getElementById("divTempoAuto").setInnerHTML(resp);
	}

	/**
	 * Método para exibir o histório de contrato SLA
	 * 
	 * @throws Exception
	 */
	public void mostraContratoClienteSLA(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		AcordoNivelServicoDTO acordoNivelServicoDTO = (AcordoNivelServicoDTO) document.getBean();

		String resp = montaGridContratoVincSLA(request, response, acordoNivelServicoDTO.getIdAcordoNivelServico(), "C");
		document.getElementById("contratoVincCliente").setInnerHTML(resp);
	}

	/**
	 * Método para exibir o histório de Ano SLA
	 * 
	 * @throws Exception
	 */
	public void mostraContratoAnoSLA(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		AcordoNivelServicoDTO acordoNivelServicoDTO = (AcordoNivelServicoDTO) document.getBean();

		String resp = montaGridContratoVincSLA(request, response, acordoNivelServicoDTO.getIdAcordoNivelServico(), "A");
		document.getElementById("contratoVincAno").setInnerHTML(resp);
	}

	/**
	 * Método para exibir o histório de Terceiros SLA
	 * 
	 * @throws Exception
	 */
	public void mostraContratoTerceiroSLA(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		AcordoNivelServicoDTO acordoNivelServicoDTO = (AcordoNivelServicoDTO) document.getBean();

		String resp = montaGridContratoVincSLA(request, response, acordoNivelServicoDTO.getIdAcordoNivelServico(), "U");
		document.getElementById("contratoVincTerceiro").setInnerHTML(resp);
	}

	/**
	 * Método para exibir o histório de Auditoria SLA
	 * 
	 * @throws Exception
	 */
	public void mostraHistoricoAuditoriaSLA(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		AcordoNivelServicoDTO acordoNivelServicoDTO = (AcordoNivelServicoDTO) document.getBean();

		String resp = montaGridHistoricoAuditoriaSLA(request, response, acordoNivelServicoDTO.getIdAcordoNivelServico());
		document.getElementById("historicoAuditoria").setInnerHTML(resp);

	}

	private String montaGridContratoVincSLA(HttpServletRequest request, HttpServletResponse response, Integer idAcordoNivelServico, String tipoContrato) throws Exception {

		String out = "";

		try {
			if (idAcordoNivelServico == null) {
				idAcordoNivelServico = 0;
			}

			StringBuilder strTable = new StringBuilder();
			strTable.append("<table width='100%'>").append("<tr>").append("<td style='border:1px solid black'>").append("&nbsp;").append("</td>").append("<td style='border:1px solid black'>")
					.append("<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "sla.contrato.numero")) + "</b>").append("</td>").append("<td style='border:1px solid black'>")
					.append("<b>" + UtilI18N.internacionaliza(request, "sla.contrato.data") + "</b>").append("</td>").append("<td style='border:1px solid black'>")
					.append("<b>" + UtilI18N.internacionaliza(request, "sla.contrato.cliente") + "</b>").append("</td>").append("<td style='border:1px solid black'>")
					.append("<b>" + UtilI18N.internacionaliza(request, "sla.contrato.fornecedor") + "</b>").append("</td>").append("<td style='border:1px solid black'>")
					.append("<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "sla.contrato.situacao")) + "</b>").append("</td>").append("</tr>");
			if (idAcordoNivelServico.intValue() > 0) {
				ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
				ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
				ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
				ClienteService clienteService = (ClienteService) ServiceLocator.getInstance().getService(ClienteService.class, null);
				FornecedorService fornecedorService = (FornecedorService) ServiceLocator.getInstance().getService(FornecedorService.class, null);
				List lstContratosCliente = (List) contratoService.listByIdAcordoNivelServicoAndTipo(idAcordoNivelServico.intValue(), tipoContrato);
				if (lstContratosCliente != null) {
					for (Iterator it = lstContratosCliente.iterator(); it.hasNext();) {
						ContratoDTO contratoDTO = (ContratoDTO) it.next();
						String nomeCliente = "";
						String nomeFornecedor = "";

						ClienteDTO clientDto = new ClienteDTO();
						clientDto.setIdCliente(contratoDTO.getIdCliente());
						clientDto = (ClienteDTO) clienteService.restore(clientDto);
						if (clientDto != null) {
							nomeCliente = clientDto.getNomeRazaoSocial();
						}

						FornecedorDTO fornecedorDto = new FornecedorDTO();
						fornecedorDto.setIdFornecedor(contratoDTO.getIdFornecedor());
						fornecedorDto = (FornecedorDTO) fornecedorService.restore(fornecedorDto);
						if (fornecedorDto != null) {
							nomeFornecedor = fornecedorDto.getRazaoSocial();
						}

						String situacao = contratoDTO.getSituacao();
						if (situacao.equalsIgnoreCase("A")) {
							situacao = UtilI18N.internacionaliza(request, "sla.contrato.ativo");
						} else if (situacao.equalsIgnoreCase("F")) {
							situacao = UtilI18N.internacionaliza(request, "sla.contrato.finalizado");
						} else if (situacao.equalsIgnoreCase("C")) {
							situacao = UtilI18N.internacionaliza(request, "sla.contrato.cancelado");
						} else if (situacao.equalsIgnoreCase("P")) {
							situacao = UtilI18N.internacionaliza(request, "sla.contrato.paralisado");
						}

						strTable.append("<tr>")
								.append("<td style='border:1px solid black'>")
								.append("<img id='img_tr_" + contratoDTO.getIdContrato() + "_" + tipoContrato + "' src='" + Constantes.getValue("SERVER_ADDRESS")
										+ Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/mais.jpg' border='0' onclick=\"abreFechaMaisMenos(this, 'tr_" + contratoDTO.getIdContrato() + "_"
										+ tipoContrato + "')\"/>").append("</td>").append("<td style='border:1px solid black'>").append(contratoDTO.getNumero()).append("</td>")
								.append("<td style='border:1px solid black'>").append(UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, contratoDTO.getDataContrato(), WebUtil.getLanguage(request))).append("</td>").append("<td style='border:1px solid black'>")
								.append(UtilHTML.encodeHTML(nomeCliente)).append("</td>").append("<td style='border:1px solid black'>").append(UtilHTML.encodeHTML(nomeFornecedor)).append("</td>")
								.append("<td style='border:1px solid black'>").append(situacao).append("</td>").append("</tr>");

						Collection colServicos = servicoContratoService.findByIdContrato(contratoDTO.getIdContrato());
						if (colServicos != null && colServicos.size() > 0) {
							strTable.append("<tr>").append("<td colspan='6' style='border:1px solid black'>")
									.append("<div id='tr_" + contratoDTO.getIdContrato() + "_" + tipoContrato + "' style='display:none'>").append("<table width='100%'>").append("<tr>")
									.append("<td colspan='2'>").append("<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "sla.servicosdocontrato")) + "</b>").append("</td>")
									.append("</tr>");
							for (Iterator itServ = colServicos.iterator(); itServ.hasNext();) {
								ServicoContratoDTO servContratoDto = (ServicoContratoDTO) itServ.next();
								if ((servContratoDto.getDataFim() == null || UtilDatas.getDataAtual().before(servContratoDto.getDataFim()))
										&& (servContratoDto.getDeleted() == null || servContratoDto.getDeleted().equalsIgnoreCase("n"))) {
									ServicoDTO servicoDto = new ServicoDTO();
									servicoDto.setIdServico(servContratoDto.getIdServico());
									servicoDto = (ServicoDTO) servicoService.restore(servicoDto);
									if (servicoDto != null) {
										strTable.append("<tr>").append("<td>")
												.append("<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/seta_link1.gif' border='0'/>")
												.append("</td>").append("<td>").append(UtilHTML.encodeHTML(servicoDto.getNomeServico())).append("</td>").append("</tr>");
									}
								}
							}
							strTable.append("</table>").append("</div>").append("</td>").append("</tr>");
						}
					}
				}
			}
			strTable.append("</table>");

			out = strTable.toString();

		} catch (Exception eX) {
			eX.printStackTrace();
		}

		response.setContentType("text/html; charset=UTF-8");

		return out;

	}

	private String montaGridHistoricoAuditoriaSLA(HttpServletRequest request, HttpServletResponse response, Integer idAcordoNivelServico) throws Exception {
		String out = "";
		StringBuilder strTable = new StringBuilder();
		try {
			if (idAcordoNivelServico == null) {
				idAcordoNivelServico = 0;
			}

			strTable.append("<table width='100%'>").append("<tr>").append("<td style='border:1px solid black'>").append("&nbsp;").append("</td>").append("<td style='border:1px solid black'>")
					.append("<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "sla.titulo")) + "</b>").append("</td>").append("<td style='border:1px solid black'>")
					.append("<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "sla.datainicio")) + "</b>").append("</td>").append("<td style='border:1px solid black'>")
					.append("<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "sla.datafim")) + "</b>").append("</td>").append("<td style='border:1px solid black'>")
					.append("<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "sla.avaliarem")) + "</b>").append("</td>").append("<td style='border:1px solid black'>")
					.append("<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "sla.situacao")) + "</b>").append("</td>").append("<td style='border:1px solid black'>")
					.append("<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "sla.modificadoPor")) + "</b>").append("</td>").append("<td style='border:1px solid black'>")
					.append("<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "sla.modificadoEm")) + "</b>").append("</td>").append("</tr>");
			if (idAcordoNivelServico.intValue() > 0) {
				AcordoNivelServicoHistoricoDao acordoNivelServicoHistoricoDao = new AcordoNivelServicoHistoricoDao();
				Collection col = acordoNivelServicoHistoricoDao.findByIdAcordoNivelServico(idAcordoNivelServico.intValue());
				if (col != null) {
					for (Iterator it = col.iterator(); it.hasNext();) {
						AcordoNivelServicoHistoricoDTO acordoNivelServicoHistoricoDTO = (AcordoNivelServicoHistoricoDTO) it.next();
						strTable.append("<tr>")
								.append("<td style='border:1px solid black'>")
								.append("<img id='img_trHISTSLA_" + acordoNivelServicoHistoricoDTO.getIdAcordoNivelServico_Hist() + "' src='" + Constantes.getValue("SERVER_ADDRESS")
										+ Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/mais.jpg' border='0' onclick=\"abreFechaMaisMenos(this, 'trHISTSLA_"
										+ acordoNivelServicoHistoricoDTO.getIdAcordoNivelServico_Hist() + "')\"/>").append("</td>").append("<td style='border:1px solid black'>")
								.append("" + UtilHTML.encodeHTML(acordoNivelServicoHistoricoDTO.getTituloSLA()) + "").append("</td>").append("<td style='border:1px solid black'>")
								.append("" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, acordoNivelServicoHistoricoDTO.getDataInicio(), WebUtil.getLanguage(request)) + "").append("</td>").append("<td style='border:1px solid black'>");
						if (acordoNivelServicoHistoricoDTO.getDataFim() != null) {
							strTable.append("" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, acordoNivelServicoHistoricoDTO.getDataFim(), WebUtil.getLanguage(request)) + "");
						} else {
							strTable.append("&nbsp;");
						}
						strTable.append("</td>").append("<td style='border:1px solid black'>").append("" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, acordoNivelServicoHistoricoDTO.getAvaliarEm(), WebUtil.getLanguage(request)) + "").append("</td>")
								.append("<td style='border:1px solid black'>");
						String situacao = acordoNivelServicoHistoricoDTO.getSituacao();
						if (acordoNivelServicoHistoricoDTO.getSituacao().equalsIgnoreCase("A")) {
							situacao = UtilI18N.internacionaliza(request, "sla.ativo");
						}
						if (acordoNivelServicoHistoricoDTO.getSituacao().equalsIgnoreCase("I")) {
							situacao = UtilI18N.internacionaliza(request, "sla.inativo");
						}
						strTable.append("" + UtilHTML.encodeHTML(situacao) + "").append("</td>").append("<td style='border:1px solid black'>")
								.append("" + UtilHTML.encodeHTML(acordoNivelServicoHistoricoDTO.getModificadoPor()) + "").append("</td>").append("<td style='border:1px solid black'>")
								.append("" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, acordoNivelServicoHistoricoDTO.getModificadoEm(), WebUtil.getLanguage(request)) + "").append("</td>").append("</tr>")

								.append("<tr>").append("<td colspan='8' style='border:1px solid black'>")
								.append("<div id='trHISTSLA_" + acordoNivelServicoHistoricoDTO.getIdAcordoNivelServico_Hist() + "' style='display:none'>").append("<table width='100%'>")
								.append("<tr>").append("<td colspan='2'>").append("<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "sla.descricao")) + "</b>").append("</td>")
								.append("</tr>").append("<tr>").append("<td colspan='2'>").append("" + UtilHTML.encodeHTML(acordoNivelServicoHistoricoDTO.getDescricaoSLA()) + "").append("</td>")
								.append("</tr>").append("<tr>").append("<td colspan='2'>").append("<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "sla.escopo")) + "</b>")
								.append("</td>").append("</tr>").append("<tr>").append("<td colspan='2'>").append("" + UtilHTML.encodeHTML(acordoNivelServicoHistoricoDTO.getEscopoSLA()) + "")
								.append("</td>").append("</tr>").append("<tr>").append("<td colspan='2'>")
								.append("<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "sla.tipoeprioridade")) + "</b>").append("</td>").append("</tr>").append("<tr>")
								.append("<td colspan='2'>");
						String tipo = acordoNivelServicoHistoricoDTO.getTipo();
						if (acordoNivelServicoHistoricoDTO.getTipo().equalsIgnoreCase("D")) {
							tipo = UtilI18N.internacionaliza(request, "sla.tipo.disponibilidade");
						}
						if (acordoNivelServicoHistoricoDTO.getTipo().equalsIgnoreCase("T")) {
							tipo = UtilI18N.internacionaliza(request, "sla.tipo.tempo");
						}
						if (acordoNivelServicoHistoricoDTO.getTipo().equalsIgnoreCase("V")) {
							tipo = UtilI18N.internacionaliza(request, "sla.tipo.outros");
						}
						String prioridade = "";
						if (acordoNivelServicoHistoricoDTO.getIdPrioridadePadrao() != null) {
							prioridade = ", " + acordoNivelServicoHistoricoDTO.getIdPrioridadePadrao();
						}
						strTable.append("" + UtilHTML.encodeHTML(tipo) + " " + prioridade).append("</td>").append("</tr>").append("<tr>").append("<td colspan='2'>")
								.append("<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "sla.dadoslog")) + "</b>").append("</td>").append("</tr>").append("<tr>")
								.append("<td colspan='2'>");
						if (acordoNivelServicoHistoricoDTO.getConteudodados() != null) {
							strTable.append(acordoNivelServicoHistoricoDTO.getConteudodados().replaceAll("\n", "<br>"));
						} else {
							strTable.append("&nbsp;");
						}
						strTable.append("</td>").append("</tr>").append("</table>").append("</div>").append("</td>").append("</tr>");
					}
				}
			}
			strTable.append("</table>");

			response.setContentType("text/html; charset=UTF-8");

			out = strTable.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return out;

	}

	// public void enviaEmail(Integer idModeloEmail, DocumentHTML document) throws Exception {
	//
	// SolicitacaoServicoDTO solicitacaoDto = (SolicitacaoServicoDTO) document.getBean();
	// MensagemEmail mensagem = new MensagemEmail(idModeloEmail, new IDto[] {solicitacaoDto});
	// try {
	// mensagem.envia(solicitacaoDto.getEmailcontato(), solicitacaoDto.getNome(), solicitacaoDto.getNome());
	// } catch (Exception e) {
	// }
	// }

	public ComplementoSLA_TempoAuto getComplementoSLA_TempoAuto() {
		if (complementoSLA_TempoAuto == null) {
			return new ComplementoSLA_TempoAuto();
		} else {
			return complementoSLA_TempoAuto;
		}
	}

	public ComplementoSLA_Prioridade getComplementoSLA_Prioridade() {
		if (complementoSLA_Prioridade == null) {
			return new ComplementoSLA_Prioridade();
		} else {
			return complementoSLA_Prioridade;
		}
	}

	public ComplementoSLA_Grupos getComplementoSLA_Grupos() {
		if (complementoSLA_Grupos == null) {
			return new ComplementoSLA_Grupos();
		} else {
			return complementoSLA_Grupos;
		}
	}

	public AcordoNivelServicoService getAcordoNivelServicoService() throws ServiceException, Exception {
		if (acordoNivelServicoService == null) {
			return (AcordoNivelServicoService) ServiceLocator.getInstance().getService(AcordoNivelServicoService.class, null);
		} else {
			return acordoNivelServicoService;
		}
	}

	public PrioridadeAcordoNivelServicoService getAcordoPrioridadeAcordoNivelServicoService() throws ServiceException, Exception {
		if (prioridadeAcordoNivelServicoService == null) {
			return (PrioridadeAcordoNivelServicoService) ServiceLocator.getInstance().getService(PrioridadeAcordoNivelServicoService.class, null);
		} else {
			return prioridadeAcordoNivelServicoService;
		}
	}

	public TempoAcordoNivelServicoService getTempoAcordoNivelServicoService() throws ServiceException, Exception {
		if (tempoAcordoNivelServicoService == null) {
			return (TempoAcordoNivelServicoService) ServiceLocator.getInstance().getService(TempoAcordoNivelServicoService.class, null);
		} else {
			return tempoAcordoNivelServicoService;
		}
	}

	public PrioridadeServicoUsuarioService getPrioridadeServicoUsuarioService() throws ServiceException, Exception {
		if (prioridadeServicoUsuarioService == null) {
			return (PrioridadeServicoUsuarioService) ServiceLocator.getInstance().getService(PrioridadeServicoUsuarioService.class, null);
		} else {
			return prioridadeServicoUsuarioService;
		}
	}

	public SlaRequisitoSlaService getSlaRequisitoSlaService() throws ServiceException, Exception {
		if (slaRequisitoSlaService == null) {
			return (SlaRequisitoSlaService) ServiceLocator.getInstance().getService(SlaRequisitoSlaService.class, null);
		} else {
			return slaRequisitoSlaService;
		}
	}

	public RevisarSlaService getRevisarSlaService() throws ServiceException, Exception {
		if (revisarSlaService == null) {
			return (RevisarSlaService) ServiceLocator.getInstance().getService(RevisarSlaService.class, null);
		} else {
			return revisarSlaService;
		}
	}

	public UnidadeService getUnidadeService() throws ServiceException, Exception {
		if (unidadeService == null) {
			return (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);
		} else {
			return unidadeService;
		}
	}

	public RequisitoSLAService getRequisitoSLAService() throws ServiceException, Exception {
		if (requisitoSLAService == null) {
			return (RequisitoSLAService) ServiceLocator.getInstance().getService(RequisitoSLAService.class, null);
		} else {
			return requisitoSLAService;
		}
	}

	public EmpregadoService getEmpregadoService() throws ServiceException, Exception {
		if (empregadoService == null) {
			return (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
		} else {
			return empregadoService;
		}
	}

}
