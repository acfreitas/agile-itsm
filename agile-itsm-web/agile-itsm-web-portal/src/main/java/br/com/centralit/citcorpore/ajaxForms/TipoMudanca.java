package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import br.com.centralit.bpm.dto.TipoFluxoDTO;
import br.com.centralit.bpm.servico.TipoFluxoService;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.CalendarioDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.ModeloEmailDTO;
import br.com.centralit.citcorpore.bean.TipoMudancaDTO;
import br.com.centralit.citcorpore.negocio.CalendarioService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.ModeloEmailService;
import br.com.centralit.citcorpore.negocio.RequisicaoMudancaService;
import br.com.centralit.citcorpore.negocio.TipoMudancaService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

/**
 * @author geber.costa
 *
 */
@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
public class TipoMudanca extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		document.focusInFirstActivateField(null);


		// Opções de Fluxo
		this.preencherComboTipoFluxo(document, request, response);

		// Email de Criação
		this.preencherComboEmailCriacao(document, request, response);

		// Email de Finalização
		this.preencherComboEmailFinalizacao(document, request, response);

		// Email de Ações
		this.preencherComboEmailAcoes(document, request, response);

		// Grupo Executor
		this.preencherComboGrupoExecutor(document, request, response);

		// Grupo Calendário
		this.preencherComboCalendario(document, request, response);

	}

	@Override
	public Class getBeanClass() {

		return TipoMudancaDTO.class;
	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TipoMudancaDTO tipoMudancaDTO = (TipoMudancaDTO) document.getBean();
		TipoMudancaService tipoMudancaService = (TipoMudancaService) ServiceLocator.getInstance().getService(TipoMudancaService.class, WebUtil.getUsuarioSistema(request));

		/*
		 * if(tipoMudancaDTO.getNomeTipoMudanca()==null || tipoMudancaDTO.getNomeTipoMudanca().equals("")){ document.alert("Selecione uma opção de nome"); return; }
		 */
		// Se já existir gravado no sistema o tipo selecionado mostrará a mensagem de tipo já cadastrado
		if (tipoMudancaService.verificarTipoMudancaAtivos(tipoMudancaDTO)) {
			document.alert(UtilI18N.internacionaliza(request, "MSE01"));
			return;
		}

		if (tipoMudancaDTO.getIdTipoMudanca() == null || tipoMudancaDTO.getIdTipoMudanca() == 0) {
			// Insere a data atual no campo dataFim, então o dado não é apagado, ele apenas é setado um fim,
			// e os dados carregados devem ter datafim = null

			tipoMudancaDTO.setDataInicio(UtilDatas.getDataAtual());
			tipoMudancaService.create(tipoMudancaDTO);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
		} else {
			tipoMudancaService.update(tipoMudancaDTO);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
			return;
		}
		HTMLForm form = document.getForm("form");
		form.clear();

	}

	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TipoMudancaDTO tipoMudancaDTO = (TipoMudancaDTO) document.getBean();
		TipoMudancaService tipoMudancaService = (TipoMudancaService) ServiceLocator.getInstance().getService(TipoMudancaService.class, null);
		tipoMudancaDTO = (TipoMudancaDTO) tipoMudancaService.restore(tipoMudancaDTO);
		if (tipoMudancaDTO != null) {

			HTMLForm form = document.getForm("form");
			form.clear();
			form.setValues(tipoMudancaDTO);
		}
	}

	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TipoMudancaDTO tipoMudancaDTO = (TipoMudancaDTO) document.getBean();
		TipoMudancaService tipoMudancaService = (TipoMudancaService) ServiceLocator.getInstance().getService(TipoMudancaService.class, WebUtil.getUsuarioSistema(request));
		RequisicaoMudancaService requisicaoMudancaService = (RequisicaoMudancaService) ServiceLocator.getInstance().getService(RequisicaoMudancaService.class, null);

		if (tipoMudancaDTO.getIdTipoMudanca().intValue() > 0) {

			if (requisicaoMudancaService.verificarSeRequisicaoMudancaPossuiTipoMudanca(tipoMudancaDTO.getIdTipoMudanca())) {
				document.alert(UtilI18N.internacionaliza(request, "MSG08"));
				return;
			}
			// Setar dataFIm
			tipoMudancaDTO.setDataFim(UtilDatas.getDataAtual());
			tipoMudancaService.update(tipoMudancaDTO);
			// tipoMudancaService.deleteByIdTipoMudanca(tipoMudancaDTO.getIdTipoMudanca());
			document.alert(UtilI18N.internacionaliza(request, "MSG07"));

		}

		HTMLForm form = document.getForm("form");
		form.clear();
	}

	/**
	 * preencher combo de tipo fluxo
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author geber.costa
	 */
	public void preencherComboTipoFluxo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// Opções do Fluxo
		TipoFluxoService tipoFluxoService = (TipoFluxoService) ServiceLocator.getInstance().getService(TipoFluxoService.class, null);
		HTMLSelect comboFluxo = (HTMLSelect) document.getSelectById("idTipoFluxo");
		ArrayList<TipoFluxoDTO> fluxo = (ArrayList) tipoFluxoService.list();

		comboFluxo.removeAllOptions();
		comboFluxo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

		for (TipoFluxoDTO tipoFluxoDTO : fluxo) {
			if (tipoFluxoDTO.getIdTipoFluxo() != null || tipoFluxoDTO.getIdTipoFluxo() > 0) {
				// Se a datafim for nula ele adiciona a id do fluxo e joga na tela o nome do fluxo
				comboFluxo.addOption(tipoFluxoDTO.getIdTipoFluxo().toString(), StringEscapeUtils.escapeJavaScript(tipoFluxoDTO.getNomeFluxo().toString()));
			}
		}
	}

	/**
	 * preencher comobo email de criação
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author geber.costa
	 */
	public void preencherComboEmailCriacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		ModeloEmailService modeloEmailService = (ModeloEmailService) ServiceLocator.getInstance().getService(ModeloEmailService.class, null);
		HTMLSelect comboEmailCriacao = (HTMLSelect) document.getSelectById("idModeloEmailCriacao");
		ArrayList<ModeloEmailDTO> emailCriacao = (ArrayList) modeloEmailService.getAtivos();

		comboEmailCriacao.removeAllOptions();
		comboEmailCriacao.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

		if (emailCriacao != null) {
			for (ModeloEmailDTO modeloEmailDTO : emailCriacao) {

				comboEmailCriacao.addOption(modeloEmailDTO.getIdModeloEmail().toString(), StringEscapeUtils.escapeJavaScript(modeloEmailDTO.getTitulo().toString()));
			}
		}
	}

	/**
	 * preencher comobo email de finalização
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author geber.costa
	 */
	public void preencherComboEmailFinalizacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		ModeloEmailService modeloEmailService = (ModeloEmailService) ServiceLocator.getInstance().getService(ModeloEmailService.class, null);
		HTMLSelect comboEmailFinalizacao = (HTMLSelect) document.getSelectById("idModeloEmailFinalizacao");
		ArrayList<ModeloEmailDTO> emailFinalizacao = (ArrayList) modeloEmailService.getAtivos();

		comboEmailFinalizacao.removeAllOptions();
		comboEmailFinalizacao.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

		if (emailFinalizacao != null) {
			for (ModeloEmailDTO modeloEmailDTO : emailFinalizacao) {

				comboEmailFinalizacao.addOption(modeloEmailDTO.getIdModeloEmail().toString(), StringEscapeUtils.escapeJavaScript(modeloEmailDTO.getTitulo().toString()));

			}
		}

	}

	/**
	 * preencher comobo email de acoes
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author geber.costa
	 */
	public void preencherComboEmailAcoes(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		ModeloEmailService modeloEmailService = (ModeloEmailService) ServiceLocator.getInstance().getService(ModeloEmailService.class, null);
		HTMLSelect comboEmailAcoes = (HTMLSelect) document.getSelectById("idModeloEmailAcoes");

		ArrayList<ModeloEmailDTO> emailAcoes = (ArrayList) modeloEmailService.getAtivos();

		comboEmailAcoes.removeAllOptions();
		comboEmailAcoes.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

		if (emailAcoes != null) {
			for (ModeloEmailDTO modeloEmailDTO : emailAcoes) {
				comboEmailAcoes.addOption(modeloEmailDTO.getIdModeloEmail().toString(), StringEscapeUtils.escapeJavaScript(modeloEmailDTO.getTitulo().toString()));
			}
		}

	}

	/**
	 * preencher comobo do grupo executor
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author geber.costa
	 */
	public void preencherComboGrupoExecutor(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		HTMLSelect comboGrupoExecutor = document.getSelectById("idGrupoExecutor");
		ArrayList<GrupoDTO> grupoDTO = (ArrayList<GrupoDTO>) grupoService.listarGruposAtivos();

		comboGrupoExecutor.removeAllOptions();
		comboGrupoExecutor.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

		if (grupoDTO != null) {
			for (GrupoDTO grupo : grupoDTO) {
				comboGrupoExecutor.addOption(grupo.getIdGrupo().toString(), StringEscapeUtils.escapeJavaScript(grupo.getNome().toString()));
			}
		}

	}

	/**
	 * preencher comobo de calendario
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author geber.costa
	 */
	public void preencherComboCalendario(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CalendarioService calendarioService = (CalendarioService) ServiceLocator.getInstance().getService(CalendarioService.class, null);
		HTMLSelect comboCalendario = document.getSelectById("idCalendario");
		Collection<CalendarioDTO> calendarioDTO = calendarioService.list();

		comboCalendario.removeAllOptions();
		comboCalendario.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

		if (calendarioDTO != null) {
			for (CalendarioDTO calendario : calendarioDTO) {
				comboCalendario.addOption(calendario.getIdCalendario().toString(), StringEscapeUtils.escapeJavaScript(calendario.getDescricao().toString()));
			}
		}

	}
}
