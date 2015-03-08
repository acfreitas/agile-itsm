/**
 *
 */
package br.com.centralit.citcorpore.ajaxForms;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.allcolor.yahp.converter.CYaHPConverter;
import org.allcolor.yahp.converter.IHtmlToPdfTransformer;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.LinhaBaseProjetoDTO;
import br.com.centralit.citcorpore.bean.ProjetoDTO;
import br.com.centralit.citcorpore.bean.RecursoTarefaLinBaseProjDTO;
import br.com.centralit.citcorpore.bean.TarefaLinhaBaseProjetoDTO;
import br.com.centralit.citcorpore.bean.TimeSheetProjetoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.LinhaBaseProjetoService;
import br.com.centralit.citcorpore.negocio.ProjetoService;
import br.com.centralit.citcorpore.negocio.RecursoTarefaLinBaseProjService;
import br.com.centralit.citcorpore.negocio.TarefaLinhaBaseProjetoService;
import br.com.centralit.citcorpore.negocio.TimeSheetProjetoService;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilFormatacao;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

/**
 * @author valdoilo.damasceno
 *
 */
@SuppressWarnings("rawtypes")
public class CarteiraTrabalho extends AjaxFormAction {

	private UsuarioDTO usuarioDto;

	@Override
	public Class<TimeSheetProjetoDTO> getBeanClass() {
		return TimeSheetProjetoDTO.class;
	}

	@Override
	public void load(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		filtrarGantt(document, request, response);
	}

	public void restore(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

	}

	public void save(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoexpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		TimeSheetProjetoDTO timeSheetProjetoDTO = (TimeSheetProjetoDTO)document.getBean();
		if (timeSheetProjetoDTO.getData() != null){
			if (timeSheetProjetoDTO.getData().after(UtilDatas.getDataAtual())){
				document.alert(UtilI18N.internacionaliza(request, "carteiraTrabalho.dataNaoMaiorAtual"));
				return;
			}
		}
		if (timeSheetProjetoDTO.getPercExecutado() == null){
			document.alert(UtilI18N.internacionaliza(request, "carteiraTrabalho.informePercentual"));
			return;
		}
		if (timeSheetProjetoDTO.getPercExecutado().doubleValue() > 100){
			document.alert(UtilI18N.internacionaliza(request, "carteiraTrabalho.percentualNaoMaior100"));
			return;
		}
		timeSheetProjetoDTO.setDataHoraReg(UtilDatas.getDataHoraAtual());
		TimeSheetProjetoService timeSheetProjetoService = (TimeSheetProjetoService) ServiceLocator.getInstance().getService(TimeSheetProjetoService.class, WebUtil.getUsuarioSistema(request));
		timeSheetProjetoService.create(timeSheetProjetoDTO);
		document.alert(UtilI18N.internacionaliza(request, "carteiraTrabalho.registroIncluido"));
		document.getForm("formCarteira").clear();
		document.getElementById("idRecursoTarefaLinBaseProj").setValue("" + timeSheetProjetoDTO.getIdRecursoTarefaLinBaseProj());
		document.executeScript("$(\'#POPUP_EDITAR\').dialog(\'close\');");
		document.executeScript("reload()");
		//listTimeSheet(document, request, response);
	}

	public void print(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CYaHPConverter converter = new CYaHPConverter();
		File fout = new File("c:\\teste.pdf");
		FileOutputStream out = new FileOutputStream(fout);
		List			 headerFooterList = new ArrayList();

		Map properties = new HashMap();

		headerFooterList.add(new IHtmlToPdfTransformer.CHeaderFooter(
				"<table width=\"100%\"><tbody><tr><td align=\"left\">Generated with YaHPConverter.</td><td align=\"right\">"+UtilI18N.internacionaliza(request, "citcorpore.comum.pagina")+" <pagenumber>/<pagecount></td></tr></tbody></table>",
				IHtmlToPdfTransformer.CHeaderFooter.HEADER));
		headerFooterList.add(new IHtmlToPdfTransformer.CHeaderFooter(
				"© 2011 Quentin Anciaux",
				IHtmlToPdfTransformer.CHeaderFooter.FOOTER));

		String str = "<HTML><HEAD></HEAD><BODY><H1>Testing</H1><FORM>" +
                "check : <INPUT TYPE='checkbox' checked=checked/><br/><br><table><tr><td style='border:1px solid black'>TESTE</td><td style='border:1px solid black'>TESTE 2</td></tr></table>"   +
                "</FORM></BODY></HTML>";

   properties.put(IHtmlToPdfTransformer.PDF_RENDERER_CLASS,
                  IHtmlToPdfTransformer.FLYINGSAUCER_PDF_RENDERER);
   //properties.put(IHtmlToPdfTransformer.FOP_TTF_FONT_PATH, fontPath);
   converter.convertToPdf(str,
         IHtmlToPdfTransformer.A4P,
         headerFooterList,
         "file:///temp/", // root for relative external CSS and IMAGE
         out,
         properties);
   /*
		properties.put(IHtmlToPdfTransformer.PDF_RENDERER_CLASS,
				IHtmlToPdfTransformer.FLYINGSAUCER_PDF_RENDERER);

		converter.convertToPdf(new URL("http://localhost:8080/citsmart/pages/index/index.load"),
				IHtmlToPdfTransformer.A4P, headerFooterList, out,
				properties);
			System.out.println("after conversion");*/
			out.flush();
			out.close();
		document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.situacaoFinalizado"));
	}

	public void listTimeSheet(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoexpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		TimeSheetProjetoDTO timeSheetProjetoDTO = (TimeSheetProjetoDTO)document.getBean();
		RecursoTarefaLinBaseProjService recursoTarefaLinBaseProjService = (RecursoTarefaLinBaseProjService) ServiceLocator.getInstance().getService(RecursoTarefaLinBaseProjService.class, null);
		TimeSheetProjetoService timeSheetProjetoService = (TimeSheetProjetoService) ServiceLocator.getInstance().getService(TimeSheetProjetoService.class, null);
		TarefaLinhaBaseProjetoService tarefaLinhaBaseProjetoService = (TarefaLinhaBaseProjetoService) ServiceLocator.getInstance().getService(TarefaLinhaBaseProjetoService.class, null);
		Collection colTimes = timeSheetProjetoService.findByIdRecursoTarefaLinBaseProj(timeSheetProjetoDTO.getIdRecursoTarefaLinBaseProj(), usuario.getIdEmpregado());

		RecursoTarefaLinBaseProjDTO recursoTarefaLinBaseProjDTO = new RecursoTarefaLinBaseProjDTO();
		recursoTarefaLinBaseProjDTO.setIdRecursoTarefaLinBaseProj(timeSheetProjetoDTO.getIdRecursoTarefaLinBaseProj());
		recursoTarefaLinBaseProjDTO = (RecursoTarefaLinBaseProjDTO) recursoTarefaLinBaseProjService.restore(recursoTarefaLinBaseProjDTO);

		String strDetTarefa = "";
		document.executeScript("indicaGauge(0)");
		if (recursoTarefaLinBaseProjDTO != null){
			TarefaLinhaBaseProjetoDTO tarefaLinhaBaseProjetoDTO = new TarefaLinhaBaseProjetoDTO();
			tarefaLinhaBaseProjetoDTO.setIdTarefaLinhaBaseProjeto(recursoTarefaLinBaseProjDTO.getIdTarefaLinhaBaseProjeto());
			tarefaLinhaBaseProjetoDTO = (TarefaLinhaBaseProjetoDTO) tarefaLinhaBaseProjetoService.restore(tarefaLinhaBaseProjetoDTO);
			if (tarefaLinhaBaseProjetoDTO != null){
				strDetTarefa = "<b>" + tarefaLinhaBaseProjetoDTO.getNomeTarefa() + "</b>";
				Double tempoAlocMin = recursoTarefaLinBaseProjDTO.getTempoAlocMinutos();
				double tempo = 0;
				if (tempoAlocMin != null){
					tempo = tempoAlocMin.doubleValue();
					tempo = tempo / 60;
				}
				strDetTarefa += "<br>"+UtilI18N.internacionaliza(request, "carteiraTrabalho.trabalhoEstimado")+"" + UtilFormatacao.formatDouble(tempo, 2) + " h";
				strDetTarefa += "<br>" + UtilStrings.nullToVazio(tarefaLinhaBaseProjetoDTO.getDetalhamentoTarefa());
			}
		}
		document.getElementById("divInfoTarefa").setInnerHTML(strDetTarefa);

		String strTables = "<table width='100%'>";
		strTables += "<tr>" +
				"<td><b>"+UtilI18N.internacionaliza(request, "carteiraTrabalho.linhaBase")+"</b></td>" +
				"<td><b>"+UtilI18N.internacionaliza(request, "carteiraTrabalho.data")+"</b></td>" +
				"<td><b>"+UtilI18N.internacionaliza(request, "carteiraTrabalho.hora")+"</b></td>" +
				"<td><b>"+UtilI18N.internacionaliza(request, "carteiraTrabalho.qtdeHoras")+"</b></td>" +
				"<td><b>"+UtilI18N.internacionaliza(request, "carteiraTrabalho.porcExecutado")+"</b></td>" +
				"<td><b>"+UtilI18N.internacionaliza(request, "carteiraTrabalho.registradoEm")+"</b></td>" +
				"<td><b>"+UtilI18N.internacionaliza(request, "citcorpore.ui.tabela.coluna.Detalhamento")+"</b></td>" +
		"</tr>";
		String strPerc = "0";
		double percMaior = 0;
		if (colTimes != null){
			for (Iterator it = colTimes.iterator(); it.hasNext();){
				TimeSheetProjetoDTO timeSheetProjetoAux = (TimeSheetProjetoDTO)it.next();
				String strLinhaBase = "Atual";
				if (timeSheetProjetoAux.getIdLinhaBaseProjeto() != null){
					strLinhaBase = "" + timeSheetProjetoAux.getIdLinhaBaseProjeto();
				}

				//quebrar linha do detalhamento caso seja muito grande
				String detalhamentoAuxiliar = "";
				if(timeSheetProjetoAux.getDetalhamento().length() > 70){
					detalhamentoAuxiliar = timeSheetProjetoAux.getDetalhamento();
					detalhamentoAuxiliar = timeSheetProjetoAux.getDetalhamento().substring(0, 70) + "<br>";
					detalhamentoAuxiliar += timeSheetProjetoAux.getDetalhamento().substring(71);
				} else {
					detalhamentoAuxiliar = timeSheetProjetoAux.getDetalhamento();
				}

				strTables += "<tr>" +
						"<td>" + strLinhaBase  + "</td>" +
						"<td>" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, timeSheetProjetoAux.getData(), WebUtil.getLanguage(request)) + "</td>" +
						"<td>" + timeSheetProjetoAux.getHora() + "</td>" +
						"<td>" + UtilFormatacao.formatDouble(timeSheetProjetoAux.getQtdeHoras(), 2) + "</td>" +
						"<td>" + UtilFormatacao.formatDouble(timeSheetProjetoAux.getPercExecutado(), 2) + "</td>" +
						"<td>" + UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, timeSheetProjetoAux.getDataHoraReg(), WebUtil.getLanguage(request)) + "</td>" +
						"<td>" + detalhamentoAuxiliar + "</td>" +
				"</tr>";
				if (timeSheetProjetoAux.getDataHoraReg().getTime() > percMaior){
					strPerc = UtilFormatacao.formatDouble(timeSheetProjetoAux.getPercExecutado(), 0);
					percMaior = timeSheetProjetoAux.getDataHoraReg().getTime();
				}
			}
		}
		strTables += "</table>";
		document.getElementById("divInfoTimeSheet").setInnerHTML(strTables);
		document.executeScript("indicaGauge(" + strPerc + ")");
		//print(document, request, response);
	}

	/**
	 * Filtra GANTT de acordo com Tipo de Solicitacao e Grupo de Seguranï¿½a
	 * selecionado.
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author valdoilo
	 */
	public void filtrarGantt(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoexpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		TarefaLinhaBaseProjetoService tarefaLinhaBaseProjetoService = (TarefaLinhaBaseProjetoService) ServiceLocator.getInstance().getService(TarefaLinhaBaseProjetoService.class, null);
		Collection colCarteira = tarefaLinhaBaseProjetoService.findCarteiraByIdEmpregado(usuario.getIdEmpregado());

		StringBuilder strBuff = new StringBuilder();
		if (colCarteira != null && colCarteira.size() > 0){
			strBuff = gerarGantt(colCarteira, document, request);
		}
		document.executeScript(strBuff.toString());
		document.getElementById("loading_overlay").setVisible(false);
	}

	/**
	 * Gera GANTT a partir da Lista de Solicitaï¿½ï¿½es de Serviço.
	 *
	 * @param listaSolicitacaoServico
	 * @return <code>StringBuilder</code>
	 * @author valdoilo
	 * @throws Exception
	 * @throws ServiceException
	 */
	private StringBuilder gerarGantt(
			Collection<TarefaLinhaBaseProjetoDTO> lista, DocumentHTML document, HttpServletRequest request) throws ServiceException, Exception {
		ProjetoService projetoService = (ProjetoService) ServiceLocator.getInstance().getService(ProjetoService.class, null);
		LinhaBaseProjetoService linhaBaseProjetoService = (LinhaBaseProjetoService) ServiceLocator.getInstance().getService(LinhaBaseProjetoService.class, null);
		StringBuilder gantt = new StringBuilder();

		StringBuilder atvAtrasadas = new StringBuilder();
		boolean existeAtrasada = false;
		atvAtrasadas.append("<table width='100%'>");
		atvAtrasadas.append("<tr><td colspan='20'><b>");
		atvAtrasadas.append(UtilI18N.internacionaliza(request, "carteiraTrabalho.listaTarefaAtrasada"));
		atvAtrasadas.append("</b></td></tr>");
		atvAtrasadas.append("<tr><td>&nbsp;</td><td><b>");
		atvAtrasadas.append(UtilI18N.internacionaliza(request, "carteiraTrabalho.projeto"));
		atvAtrasadas.append("</b></td><td><b>");
		atvAtrasadas.append(UtilI18N.internacionaliza(request, "carteiraTrabalho.tarefa"));
		atvAtrasadas.append("</b></td><td><b>");
		atvAtrasadas.append(UtilI18N.internacionaliza(request, "citcorpore.comum.datafim"));
		atvAtrasadas.append("</b></td></tr>");

		gantt.append("$(function() {");
		gantt.append("'use strict';");
		gantt.append("$('.gantt').gantt({");

		if (lista != null && lista.size() > 0){
			gantt.append("source: [");
			for (TarefaLinhaBaseProjetoDTO tarefaLinhaBaseProjetoDTO : lista) {
				LinhaBaseProjetoDTO linhaBaseProjetoDTO = new LinhaBaseProjetoDTO();
				linhaBaseProjetoDTO.setIdLinhaBaseProjeto(tarefaLinhaBaseProjetoDTO.getIdLinhaBaseProjeto());
				linhaBaseProjetoDTO = (LinhaBaseProjetoDTO) linhaBaseProjetoService.restore(linhaBaseProjetoDTO);
				String nomeProjeto = "";
				if (linhaBaseProjetoDTO != null){
					ProjetoDTO projetoDto = new ProjetoDTO();
					projetoDto.setIdProjeto(linhaBaseProjetoDTO.getIdProjeto());
					projetoDto = (ProjetoDTO) projetoService.restore(projetoDto);
					if (projetoDto != null){
						nomeProjeto = projetoDto.getNomeProjeto();
					}
				}

				nomeProjeto = nomeProjeto.replaceAll("\'", "");
				String nomeTarefa = "";
				nomeTarefa = tarefaLinhaBaseProjetoDTO.getNomeTarefa().replaceAll("\'", "").replaceAll("\"", "");

				gantt.append("{");
				gantt.append("name: '" + nomeProjeto + "',");
				gantt.append("desc: '" + nomeTarefa + "', ");
				gantt.append("values:[{ ");
				String styleCustomClass = "ganttRed";
				if (tarefaLinhaBaseProjetoDTO.getDataFim() != null
						&& tarefaLinhaBaseProjetoDTO.getDataFim().after(UtilDatas.getDataHoraAtual())) {
					styleCustomClass = "ganttBlue";
				}else{
					existeAtrasada = true;
					atvAtrasadas.append("<tr><td><img src=\"" + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/b.gif\" border=\"0\"/></td><td>" + nomeProjeto + "</td><td>" + tarefaLinhaBaseProjetoDTO.getNomeTarefa() + "</td><td>" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, tarefaLinhaBaseProjetoDTO.getDataFim(), document.getLanguage()) + "</td></tr>");
				}
				Date d1 = UtilDatas.alteraData(tarefaLinhaBaseProjetoDTO.getDataInicio(), -1, Calendar.DAY_OF_MONTH);
				Date d2 = UtilDatas.alteraData(tarefaLinhaBaseProjetoDTO.getDataFim(), -1, Calendar.DAY_OF_MONTH);
				gantt.append("from: '/Date(" + d1.getTime() + ")/',");
				gantt.append("to: '/Date(" + d2.getTime() + ")/', ");
				gantt.append("label: '" + tarefaLinhaBaseProjetoDTO.getNomeTarefa().replaceAll("\'", "") + "',");
				gantt.append("desc: '" + nomeTarefa + "', ");
				gantt.append("customClass: '" + styleCustomClass + "',");
				gantt.append("dataObj: {idTarefaLinhaBaseProjeto: '" + tarefaLinhaBaseProjetoDTO.getIdTarefaLinhaBaseProjeto() + "',idRecursoTarefaLinBaseProj: '" + tarefaLinhaBaseProjetoDTO.getIdRecursoTarefaLinBaseProj() + "'}");
				gantt.append("}]");
				gantt.append("},");
			}
			gantt.append("],");
		}
		if (!existeAtrasada){
			atvAtrasadas.append("<tr><td colspan='20'>");
			atvAtrasadas.append(UtilI18N.internacionaliza(request, "carteiraTrabalho.naoExisteTarefaAtrasada"));
			atvAtrasadas.append("</td></td>");
		}
		atvAtrasadas.append("</table>");

		document.getElementById("atrasadas").setInnerHTML(atvAtrasadas.toString());

		gantt.append("navigate: 'scroll',");
		gantt.append("scale: 'days',");
		gantt.append("maxScale: 'months',");
		gantt.append("minScale: 'days',");
		gantt.append("itemsPerPage: 10,");
		gantt.append("onItemClick: function(data) {");
		gantt.append("editaAtividade(data)},");
		gantt.append("onAddClick: function(dt, rowId) {");
		gantt.append("}");
		gantt.append("});");
		gantt.append("});");
		return gantt;
	}

	/**
	 * @return valor do atributo usuarioDto.
	 */
	public UsuarioDTO getUsuarioDto() {
		return usuarioDto;
	}

	/**
	 * Define valor do atributo usuarioDto.
	 *
	 * @param usuarioDto
	 */
	public void setUsuarioDto(UsuarioDTO usuarioDto) {
		this.usuarioDto = usuarioDto;
	}

}
