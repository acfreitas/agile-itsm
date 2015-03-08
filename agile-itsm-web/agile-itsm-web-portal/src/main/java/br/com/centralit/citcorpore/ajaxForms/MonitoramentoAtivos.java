package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.CaracteristicaDTO;
import br.com.centralit.citcorpore.bean.CaracteristicaMonitDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.MonitoramentoAtivosDTO;
import br.com.centralit.citcorpore.bean.NotificacaoGrupoMonitDTO;
import br.com.centralit.citcorpore.bean.NotificacaoUsuarioMonitDTO;
import br.com.centralit.citcorpore.bean.ScriptMonitDTO;
import br.com.centralit.citcorpore.bean.TipoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CaracteristicaMonitService;
import br.com.centralit.citcorpore.negocio.CaracteristicaService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.MonitoramentoAtivosService;
import br.com.centralit.citcorpore.negocio.NotificacaoGrupoMonitService;
import br.com.centralit.citcorpore.negocio.NotificacaoUsuarioMonitService;
import br.com.centralit.citcorpore.negocio.ScriptMonitService;
import br.com.centralit.citcorpore.negocio.TipoItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({ "rawtypes" })
public class MonitoramentoAtivos extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		limpaForm(document, request, response);
	}

	/**
	 * Método que preenche a grid de características do tipo de item de configuração selecionado.
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author rodrigo.acorse
	 * @since 13.06.2014
	 */
	public void preencheGridCaracteristicas(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		MonitoramentoAtivosDTO monitoramentoAtivosDto = (MonitoramentoAtivosDTO) document.getBean();

		HTMLTable tblCaracteristicas = document.getTableById("tblCaracteristicas");
		tblCaracteristicas.deleteAllRows();

		if (monitoramentoAtivosDto != null && monitoramentoAtivosDto.getIdTipoItemConfiguracao() != null) {
			CaracteristicaService caracteristicaService = (CaracteristicaService) ServiceLocator.getInstance().getService(CaracteristicaService.class, null);

			Collection<CaracteristicaDTO> colCaracteristicas = caracteristicaService.consultarCaracteristicasAtivas(monitoramentoAtivosDto.getIdTipoItemConfiguracao());

			if (colCaracteristicas != null && colCaracteristicas.size() > 0) {
				tblCaracteristicas.addRowsByCollection(colCaracteristicas, new String[] { "", "nome", "descricao" }, null, "", new String[] { "gerarSelecaoCaracteristicas" }, null, null);
			}
		}
	}

	/**
	 * Método que realiza a recuperação do item selecionado na pesquisa de monitoramento de ativos.
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author rodrigo.acorse
	 * @since 13.06.2014
	 */
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		MonitoramentoAtivosDTO monitoramentoAtivosDto = (MonitoramentoAtivosDTO) document.getBean();
		MonitoramentoAtivosService monitoramentoAtivosService = (MonitoramentoAtivosService) ServiceLocator.getInstance().getService(MonitoramentoAtivosService.class, null);

		if (monitoramentoAtivosDto != null && monitoramentoAtivosDto.getIdMonitoramentoAtivos() != null) {
			monitoramentoAtivosDto = (MonitoramentoAtivosDTO) monitoramentoAtivosService.restore(monitoramentoAtivosDto);

			limpaForm(document, request, response);
			HTMLForm form = document.getForm("form");

			if (monitoramentoAtivosDto != null) {
				form.setValues(monitoramentoAtivosDto);
				document.setBean(monitoramentoAtivosDto);

				TipoItemConfiguracaoService tipoItemConfiguracaoService = (TipoItemConfiguracaoService) ServiceLocator.getInstance().getService(TipoItemConfiguracaoService.class, null);
				CaracteristicaMonitService caracteristicaMonitService = (CaracteristicaMonitService) ServiceLocator.getInstance().getService(CaracteristicaMonitService.class, null);
				ScriptMonitService scriptMonitService = (ScriptMonitService) ServiceLocator.getInstance().getService(ScriptMonitService.class, null);
				NotificacaoUsuarioMonitService notificacaoUsuarioMonitService = (NotificacaoUsuarioMonitService) ServiceLocator.getInstance().getService(NotificacaoUsuarioMonitService.class, null);
				NotificacaoGrupoMonitService notificacaoGrupoMonitService = (NotificacaoGrupoMonitService) ServiceLocator.getInstance().getService(NotificacaoGrupoMonitService.class, null);

				TipoItemConfiguracaoDTO tipoItemConfiguracaoDto = new TipoItemConfiguracaoDTO();
				tipoItemConfiguracaoDto.setId(monitoramentoAtivosDto.getIdTipoItemConfiguracao());
				tipoItemConfiguracaoDto = (TipoItemConfiguracaoDTO) tipoItemConfiguracaoService.restore(tipoItemConfiguracaoDto);

				document.executeScript("$('#tipoItemConfiguracao').attr('value', '" + StringEscapeUtils.escapeJavaScript(tipoItemConfiguracaoDto.getNome()) + "');");

				this.preencheGridCaracteristicas(document, request, response);

				if (monitoramentoAtivosDto.getTipoRegra() != null) {
					if (monitoramentoAtivosDto.getTipoRegra().equalsIgnoreCase("c")) {
						document.executeScript("$('#tipoRegraCaracteristicas').prop('checked',true);");
						document.executeScript("$('#tipoRegraCaracteristicas').parent().addClass('checked');");

						// Preenche a caracteristica se existir...
						Collection<CaracteristicaMonitDTO> colCaracteristicaMonit = caracteristicaMonitService.restoreByIdMonitoramentoAtivos(monitoramentoAtivosDto.getIdMonitoramentoAtivos());
						if (colCaracteristicaMonit != null && colCaracteristicaMonit.size() > 0) {
							for (CaracteristicaMonitDTO caracteristicaMonitDto : colCaracteristicaMonit) {
								document.executeScript("$('#idCaracteristica_" + caracteristicaMonitDto.getIdCaracteristica() + "').attr('checked', true);");
							}
						}

						document.executeScript("$('.divCaracteristicas').show();$('.divScriptRhino').hide();");
					} else if (monitoramentoAtivosDto.getTipoRegra().equalsIgnoreCase("s")) {
						document.executeScript("$('#tipoRegraScriptRhino').prop('checked',true);");
						document.executeScript("$('#tipoRegraScriptRhino').parent().addClass('checked');");

						// Preenche o script se existir...
						Collection<ScriptMonitDTO> colScriptMonit = scriptMonitService.restoreByIdMonitoramentoAtivos(monitoramentoAtivosDto.getIdMonitoramentoAtivos());
						if (colScriptMonit != null && colScriptMonit.size() > 0) {
							for (ScriptMonitDTO scriptMonitDto : colScriptMonit) {
								document.getTextAreaById("script").setValue(scriptMonitDto.getScript());
							}
						}

						document.executeScript("$('.divCaracteristicas').hide();$('.divScriptRhino').show();");
					}
				}

				if (monitoramentoAtivosDto.getEnviarEmail() != null && monitoramentoAtivosDto.getEnviarEmail().equalsIgnoreCase("y")) {
					document.executeScript("$('.divEnviarEmail').show();");
				}

				// Preenche usuários de notificação se existir...
				HTMLTable tblNotificacaoUsuarios = document.getTableById("tblNotificacaoUsuarios");
				tblNotificacaoUsuarios.deleteAllRows();

				Collection<NotificacaoUsuarioMonitDTO> colNotificacaoUsuarioMonit = notificacaoUsuarioMonitService.restoreByIdMonitoramentoAtivos(monitoramentoAtivosDto.getIdMonitoramentoAtivos());
				if (colNotificacaoUsuarioMonit != null && colNotificacaoUsuarioMonit.size() > 0) {
					UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);

					for (NotificacaoUsuarioMonitDTO notificacaoUsuarioMonitDto : colNotificacaoUsuarioMonit) {
						UsuarioDTO usuarioDto = new UsuarioDTO();
						usuarioDto.setIdUsuario(notificacaoUsuarioMonitDto.getIdUsuario());
						usuarioDto = (UsuarioDTO) usuarioService.restore(usuarioDto);

						document.executeScript("addLinhaTabelaNotificacaoUsuarios(" + usuarioDto.getIdUsuario() + ", '" + StringEscapeUtils.escapeJavaScript(usuarioDto.getNomeUsuario()) + "');");
					}
				}

				// Preenche grupos de notificação se existir...
				HTMLTable tblNotificacaoGrupos = document.getTableById("tblNotificacaoGrupos");
				tblNotificacaoGrupos.deleteAllRows();

				Collection<NotificacaoGrupoMonitDTO> colNotificacaoGrupoMonit = notificacaoGrupoMonitService.restoreByIdMonitoramentoAtivos(monitoramentoAtivosDto.getIdMonitoramentoAtivos());
				if (colNotificacaoGrupoMonit != null && colNotificacaoGrupoMonit.size() > 0) {
					GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);

					for (NotificacaoGrupoMonitDTO notificacaoGrupoMonitDto : colNotificacaoGrupoMonit) {
						GrupoDTO grupoDto = new GrupoDTO();
						grupoDto.setIdGrupo(notificacaoGrupoMonitDto.getIdGrupo());
						grupoDto = (GrupoDTO) grupoService.restore(grupoDto);

						document.executeScript("addLinhaTabelaNotificacaoGrupos(" + grupoDto.getIdGrupo() + ", '" + StringEscapeUtils.escapeJavaScript(grupoDto.getNome()) + "');");
					}
				}

			}
		}

		document.executeScript("JANELA_AGUARDE_MENU.hide();");
	}

	/**
	 * Método que realiza a gravação do monitoramento.
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author rodrigo.acorse
	 * @since 13.06.2014
	 */
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		MonitoramentoAtivosDTO monitoramentoAtivosDto = (MonitoramentoAtivosDTO) document.getBean();
		MonitoramentoAtivosService monitoramentoAtivosService = (MonitoramentoAtivosService) ServiceLocator.getInstance().getService(MonitoramentoAtivosService.class, null);

		if (monitoramentoAtivosDto != null) {
			monitoramentoAtivosDto.setCriarIncidente( (monitoramentoAtivosDto.getCriarIncidente() == null ? "n" : monitoramentoAtivosDto.getCriarIncidente()) );
			monitoramentoAtivosDto.setCriarProblema( (monitoramentoAtivosDto.getCriarProblema() == null ? "n" : monitoramentoAtivosDto.getCriarProblema()) );
			monitoramentoAtivosDto.setEnviarEmail( (monitoramentoAtivosDto.getEnviarEmail() == null ? "n" : monitoramentoAtivosDto.getEnviarEmail()) );

			if (monitoramentoAtivosDto.getIdMonitoramentoAtivos() == null) {
				monitoramentoAtivosService.create(monitoramentoAtivosDto);
				document.alert(UtilI18N.internacionaliza(request, "MSG05"));
			} else {
				monitoramentoAtivosService.update(monitoramentoAtivosDto);
				document.alert(UtilI18N.internacionaliza(request, "MSG06"));
			}
		}

		document.executeScript("JANELA_AGUARDE_MENU.hide();");

		HTMLForm form = document.getForm("form");
		form.clear();

		load(document, request, response);
	}

	/**
	 * Método que realiza a exclusão do monitoramento selecionado.
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author rodrigo.acorse
	 * @since 13.06.2014
	 *
	 */
	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		MonitoramentoAtivosDTO monitoramentoAtivosDto = (MonitoramentoAtivosDTO) document.getBean();
		MonitoramentoAtivosService monitoramentoAtivosService = (MonitoramentoAtivosService) ServiceLocator.getInstance().getService(MonitoramentoAtivosService.class, null);

		if (monitoramentoAtivosDto != null && monitoramentoAtivosDto.getIdMonitoramentoAtivos() != null) {
			monitoramentoAtivosService.delete(monitoramentoAtivosDto);
			document.alert(UtilI18N.internacionaliza(request, "MSG07") + ".");
		}

		document.executeScript("JANELA_AGUARDE_MENU.hide();");

		HTMLForm form = document.getForm("form");
		form.clear();

		load(document, request, response);
	}

	/**
	 * Realiza a validação da existência de um monitoramento com uma característica específica.
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author rodrigo.acorse
	 * @since 18.06.2014
	 */
	public void validaExistenciaScriptTipoItemConfiguracao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		MonitoramentoAtivosDTO monitoramentoAtivosDto = (MonitoramentoAtivosDTO) document.getBean();
		MonitoramentoAtivosService monitoramentoAtivosService = (MonitoramentoAtivosService) ServiceLocator.getInstance().getService(MonitoramentoAtivosService.class, null);

		if (monitoramentoAtivosDto != null) {
			if (monitoramentoAtivosDto.getIdTipoItemConfiguracao() != null) {
				MonitoramentoAtivosDTO monitoramentoAtivosScript = monitoramentoAtivosService.obterMonitorametoAtivoDoTipoItemConfiguracao(monitoramentoAtivosDto.getIdTipoItemConfiguracao());
				if (monitoramentoAtivosScript != null && monitoramentoAtivosScript.getIdMonitoramentoAtivos() != null) {
					if (monitoramentoAtivosDto.getIdMonitoramentoAtivos() == null
							|| (monitoramentoAtivosDto.getIdMonitoramentoAtivos() != null && !monitoramentoAtivosDto.getIdMonitoramentoAtivos().equals(monitoramentoAtivosScript.getIdMonitoramentoAtivos()))) {
						document.executeScript("informaRegistroExistente('s');");
					}
				}
			}
		}
	}

	/**
	 * Realiza a validação da existência de um monitoramento com script.
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author rodrigo.acorse
	 * @since 18.06.2014
	 */
	public void validaExistenciaCaracteristicaTipoItemConfiguracao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		MonitoramentoAtivosDTO monitoramentoAtivosDto = (MonitoramentoAtivosDTO) document.getBean();
		MonitoramentoAtivosService monitoramentoAtivosService = (MonitoramentoAtivosService) ServiceLocator.getInstance().getService(MonitoramentoAtivosService.class, null);


		if (monitoramentoAtivosDto != null) {
			if (monitoramentoAtivosDto.getIdTipoItemConfiguracao() != null && monitoramentoAtivosDto.getIdCaracteristica() != null) {
				MonitoramentoAtivosDTO monitoramentoAtivosCaracteristica = monitoramentoAtivosService.obterMonitorametoAtivoDaCaracteristica(monitoramentoAtivosDto.getIdTipoItemConfiguracao(), monitoramentoAtivosDto.getIdCaracteristica());
				if (monitoramentoAtivosCaracteristica != null && monitoramentoAtivosCaracteristica.getIdMonitoramentoAtivos() != null) {
					if (monitoramentoAtivosDto.getIdMonitoramentoAtivos() == null
							|| (monitoramentoAtivosDto.getIdMonitoramentoAtivos() != null && !monitoramentoAtivosDto.getIdMonitoramentoAtivos().equals(monitoramentoAtivosCaracteristica.getIdMonitoramentoAtivos()))) {
						document.executeScript("informaRegistroExistente('c');");
					}
				}
			}
		}
	}

	/**
	 * Realiza a limpeza dos campos do form.
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author rodrigo.acorse
	 * @since 18.06.2014
	 */
	public void limpaForm(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HTMLForm form = document.getForm("form");

		//Limpa o form
		form.clear();

		//Esconde as divs
		document.executeScript("$('.divCaracteristicas, .divScriptRhino, .divEnviarEmail').hide();");

		//Limpa a tabela de características
		HTMLTable tblCaracteristicas = document.getTableById("tblCaracteristicas");
		tblCaracteristicas.deleteAllRows();

		//Limpa a tabela de notificação de usuários
		HTMLTable tblNotificacaoUsuarios = document.getTableById("tblNotificacaoUsuarios");
		tblNotificacaoUsuarios.deleteAllRows();

		//Limpa a tabela de notificação de grupos
		HTMLTable tblNotificacaoGrupos = document.getTableById("tblNotificacaoGrupos");
		tblNotificacaoGrupos.deleteAllRows();
	}

	@Override
	public Class getBeanClass() {
		return MonitoramentoAtivosDTO.class;
	}

}
