package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.ScriptsDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.bean.VersaoDTO;
import br.com.centralit.citcorpore.negocio.ScriptsService;
import br.com.centralit.citcorpore.negocio.VersaoService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citcorpore.versao.Versao;
import br.com.citframework.dto.Usuario;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings("rawtypes")
public class Scripts extends AjaxFormAction {

	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ScriptsDTO scripts = (ScriptsDTO) document.getBean();
		ScriptsService scriptsService = (ScriptsService) ServiceLocator.getInstance().getService(ScriptsService.class, WebUtil.getUsuarioSistema(request));
		if (scripts.getIdScript().intValue() > 0) {
			scriptsService.deletarScript(scripts, document);
		}
		HTMLForm form = document.getForm("form");
		form.clear();
	}

	public void executar(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ScriptsDTO script = (ScriptsDTO) document.getBean();
		Usuario usuario = WebUtil.getUsuarioSistema(request);
		ScriptsService scriptsService = (ScriptsService) ServiceLocator.getInstance().getService(ScriptsService.class, usuario);
		VersaoService versaoService = (VersaoService) ServiceLocator.getInstance().getService(VersaoService.class, usuario);

		String mensagem = UtilI18N.internacionaliza(request, "scripts.usuario") + ": " + usuario.getNomeUsuario();
		mensagem += " " + UtilI18N.internacionaliza(request, "scripts.datahora") + ": " + UtilDatas.getDataHoraAtual();
		mensagem += " " + UtilI18N.internacionaliza(request, "scripts.resultado") + ": ";

		if (ScriptsDTO.TIPO_UPDATE.equals(script.getTipo())) {
			String retorno = scriptsService.executarScriptUpdate(script);

			if (retorno == null || retorno.isEmpty()) {
				mensagem += UtilI18N.internacionaliza(request, "scripts.scriptExecutadoComSucesso");
			} else {
				retorno = retorno.replaceAll("'", "`");
				retorno = retorno.replaceAll("\"", "`");
				mensagem += "\n" + retorno;
			}
			script = (ScriptsDTO) scriptsService.restore(script);

		} else if (ScriptsDTO.TIPO_CONSULTA.equals(script.getTipo())) {
			List<String[]> retorno = null;
			boolean houveErro = false;
			try {
				retorno = scriptsService.executarScriptConsulta(script);
			} catch (Exception e) {
				houveErro = true;
				mensagem += e.getLocalizedMessage();
				mensagem = mensagem.replaceAll("'", "`");
				mensagem = mensagem.replaceAll("\"", "`");
			}
			if (!houveErro) {
				// retorno = null; // RETIRAR!!!
				String upgrade = request.getParameter("upgrade");
				if ((retorno == null || retorno.isEmpty()) && !"sim".equals(upgrade)) {
					mensagem += UtilI18N.internacionaliza(request, "scripts.consultaNaoRetornouNenhumRegistro");
				} else {
					mensagem += UtilI18N.internacionaliza(request, "scripts.consultaSucesso");

					StringBuilder stringBuilder = new StringBuilder();
					if ("sim".equals(upgrade)) {
						Collection<VersaoDTO> versoesComErrosScripts = versaoService.versoesComErrosScripts();
						String resultadoVerificacaoPermissoes = scriptsService.verificaPermissoesUsuarioBanco(request);

						stringBuilder.append("  <table width='100%'>");
						stringBuilder.append("    <tr>");
						stringBuilder.append("      <td>");
						stringBuilder.append("        <fieldset>");
						stringBuilder.append("          <div style='float: left; width: 40%;'>");
						stringBuilder.append("            <button id='btnExecutar' class='light img_icon has_text' onclick='validaAtualizacao();' type='button'>");
						stringBuilder.append("              <img style='padding-left: 5px;' src='/citsmart/template_new/images/icons/small/grey/checkmark.png'>");
						stringBuilder.append("              <span style='color: #444;'>" + UtilI18N.internacionaliza(request, "citcorpore.comum.validarAtualizacao") + "</span>");
						stringBuilder.append("            </button>");
						stringBuilder.append("          </div>");
						stringBuilder.append("          <div style='float: left; width: 40%;'>");
						if (versoesComErrosScripts != null && !versoesComErrosScripts.isEmpty()) {
							stringBuilder.append("        <label>" + UtilI18N.internacionaliza(request, "citcorpore.comum.baixeDocumentoScriptVersao") + ":</label>");
							stringBuilder.append("        <select id='comboDownload' onchange='if(this.value != \"\")downloadDocumento(this)'>");
							stringBuilder.append("          <option value=''>").append(UtilI18N.internacionaliza(request, "citcorpore.comum.selecione")).append("</option>");
							for (VersaoDTO versao : versoesComErrosScripts) {
								stringBuilder.append("      <option value='" + CITCorporeUtil.CAMINHO_SCRIPTS + versao.getNomeFisicoArquivo() + "'>").append(versao.getNomeVersao())
										.append("</option>");
							}
							stringBuilder.append("        </select>");
						}
						stringBuilder.append("          </div>");
						stringBuilder.append("        </fieldset>");
						stringBuilder.append("      </td>");
						stringBuilder.append("    </tr>");
						if (resultadoVerificacaoPermissoes != null && !resultadoVerificacaoPermissoes.trim().equalsIgnoreCase("sucesso")) {
							stringBuilder.append("<tr>");
							stringBuilder.append("  <td>");
							stringBuilder.append("    <div style='float: left; width: 100%; color: red;' id='divResultadoVerificacaoPermissoes'>");
							stringBuilder.append(resultadoVerificacaoPermissoes);
							stringBuilder.append("    </div>");
							stringBuilder.append("  </td>");
							stringBuilder.append("</tr>");
						}
						stringBuilder.append("  </table>");
					}

					StringBuilder stringBuilderTable = new StringBuilder();
					if (retorno != null) {
						for (int i = 0; i < retorno.size(); i++) {
							if (i == 0) {
								if ("sim".equals(upgrade)) {
									stringBuilderTable.append("<table style='color : red;' width='100%'>");
									stringBuilderTable.append("  <tr>");
									stringBuilderTable.append("    <td>" + UtilI18N.internacionaliza(request, "citcorpore.comum.inconsistenciasDuranteExecucaoScripts") + ".</td>");
									stringBuilderTable.append("  </tr>");
									stringBuilderTable.append("  <tr>");
									stringBuilderTable.append("    <td>" + UtilI18N.internacionaliza(request, "citcorpore.comum.antesDeValidarContateSuporte") + "</td>");
									stringBuilderTable.append("  </tr>");
									stringBuilderTable.append("</table>");
								}
								stringBuilderTable.append("<table class='tableLess'>");
								stringBuilderTable.append("<tr class='th'>");
							} else {
								stringBuilderTable.append("<tr>");
							}

							for (String coluna : retorno.get(i)) {
								if (i == 0){
									stringBuilderTable.append("<th>").append(coluna).append("</th>");
								}else{
									stringBuilderTable.append("<td>").append(coluna).append("</td>");
								}
								
							}
							stringBuilderTable.append("</tr>");
						}
					}
					document.getTableById("headerResultadoConsulta").setInnerHTML(stringBuilder.toString());
					document.getTableById("contentResultadoConsulta").setInnerHTML(stringBuilderTable.toString());

					document.executeScript("$('#POPUP_RESULTADO_CONSULTA').dialog('open');");

					if ("sim".equals(upgrade)) {
						document.executeScript("$('#POPUP_RESULTADO_CONSULTA').parent().children().children('.ui-dialog-titlebar-close').hide();");

						document.executeScript("$('#POPUP_RESULTADO_CONSULTA').dialog('option', 'title', '" + UtilI18N.internacionaliza(request, "citcorpore.comum.citsmartAtualizadoParaVersao") + " "
								+ Versao.getVersao() + "');");
					}
				}
			}
		}
		document.executeScript("JANELA_AGUARDE_MENU.hide();");

		if (script.getHistorico() != null && !script.getHistorico().isEmpty()) {
			mensagem = mensagem + "\n\n##################################\n\n" + script.getHistorico();
		}
		script.setHistorico(mensagem);

		if (script.getIdScript() != null) {
			scriptsService.update(script);
		}

		HTMLForm form = document.getForm("form");
		form.setValues(script);
	}

	@Override
	public Class getBeanClass() {
		return ScriptsDTO.class;
	}

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		document.focusInFirstActivateField(null);
		HTMLSelect comboTipo = (HTMLSelect) document.getSelectById("tipo");
		comboTipo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		comboTipo.addOption(ScriptsDTO.TIPO_UPDATE, UtilI18N.internacionaliza(request, "scripts.tipo.update"));
		comboTipo.addOption(ScriptsDTO.TIPO_CONSULTA, UtilI18N.internacionaliza(request, "scripts.tipo.consulta"));

		String upgrade = request.getParameter("upgrade");
		if ("sim".equalsIgnoreCase(upgrade)) {
			HTMLForm form = document.getForm("form");
			form.clear();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT scripts.descricao, ");
			sql.append("       scripts.sqlquery, ");
			sql.append("       versao.nomeversao ");
			sql.append("FROM   scripts ");
			sql.append("       INNER JOIN versao ");
			sql.append("               ON scripts.idversao = versao.idversao ");
			sql.append("WHERE  scripts.idversao IS NOT NULL ");
			sql.append("       AND scripts.descricao LIKE 'ERRO%' ");

			ScriptsDTO scriptsDTO = new ScriptsDTO();
			scriptsDTO.setSqlQuery(sql.toString());
			scriptsDTO.setTipo(ScriptsDTO.TIPO_CONSULTA);

			document.setBean(scriptsDTO);
			executar(document, request, response);

			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacaoDeAtualizacaoNecessaria"));
		}
	}

	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ScriptsDTO script = (ScriptsDTO) document.getBean();
		ScriptsService scriptsService = (ScriptsService) ServiceLocator.getInstance().getService(ScriptsService.class, WebUtil.getUsuarioSistema(request));
		script = (ScriptsDTO) scriptsService.restore(script);
		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(script);
		if (script.getNome().startsWith("deploy_versao_")) {
			document.executeScript("document.getElementById('nome').readOnly = true");
		} else {
			document.executeScript("document.getElementById('nome').readOnly = false");
		}
		document.executeScript("descricaoOuQueryAlterada = false;");
	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ScriptsDTO script = (ScriptsDTO) document.getBean();
		ScriptsService scriptsService = (ScriptsService) ServiceLocator.getInstance().getService(ScriptsService.class, null);
		if (scriptsService.temScriptsAtivos(script)) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
			return;
		}
		if (script.getIdScript() == null || script.getIdScript() == 0) {
			script.setDataInicio(UtilDatas.getDataAtual());
			scriptsService.create(script);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
		} else {
			scriptsService.update(script);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}
		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(script);
		document.executeScript("descricaoOuQueryAlterada = false;");
	}

	public void validaAtualizacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ScriptsService scriptsService = (ScriptsService) ServiceLocator.getInstance().getService(ScriptsService.class, null);
		VersaoService versaoService = (VersaoService) ServiceLocator.getInstance().getService(VersaoService.class, null);

		StringBuilder mensagem = new StringBuilder();

		String resultadoVerificacaoPermissoes = scriptsService.verificaPermissoesUsuarioBanco(request);
		if (resultadoVerificacaoPermissoes != null && !"sucesso".equalsIgnoreCase(resultadoVerificacaoPermissoes.trim())) {
			mensagem.append(resultadoVerificacaoPermissoes + "<br><br>");
		}

		List<ScriptsDTO> scriptsComFaltaPermissao = scriptsService.pesquisaScriptsComFaltaPermissao();
		if (scriptsComFaltaPermissao != null && !scriptsComFaltaPermissao.isEmpty()) {
			mensagem.append(UtilI18N.internacionaliza(request, "citcorpore.comum.seguintesScriptsNaoExecutados") + ":<br>&nbsp;&nbsp;<b>");
			for (ScriptsDTO script : scriptsComFaltaPermissao) {
				mensagem.append(script.getSqlQuery()).append(";<br>&nbsp;&nbsp;");
			}
			mensagem.append("</b>");
		}

		if (!mensagem.toString().isEmpty()) {
			document.getElementById("divPopupVerificacaoPermissoes").setInnerHTML(mensagem.toString());
			document.executeScript("$('#POPUP_MENSAGEM_FALTA_PERMISSAO').dialog('open');");
		} else {
			scriptsService.marcaErrosScriptsComoCorrigidos();
			UsuarioDTO usuario = WebUtil.getUsuario(request);
			versaoService.validaVersoes(usuario);

			document.executeScript("encaminhaParaIndex()");
			document.executeScript("$('#POPUP_RESULTADO_CONSULTA').dialog('close');");
		}
	}
}
