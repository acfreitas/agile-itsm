/**
 * 
 */
package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLElement;
import br.com.centralit.citcorpore.bean.ConexaoBIDTO;
import br.com.centralit.citcorpore.bean.LogImportacaoBIDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ConexaoBIService;
import br.com.centralit.citcorpore.negocio.LogImportacaoBIService;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

/**
 * @author thiago.barbosa
 * 
 * 
 */
@SuppressWarnings( { "rawtypes" } )
public class LogImportacaoBI extends AjaxFormAction {

	public Class getBeanClass() {
    	return LogImportacaoBIDTO.class;
    }

    public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		
		listInfoLogConexaoBI(document, request, response);		
    }
    
	public void listInfoLogConexaoBI(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		LogImportacaoBIDTO logExecucaoBIDTO = (LogImportacaoBIDTO) document.getBean();
		LogImportacaoBIService logImportacaoService = (LogImportacaoBIService)  ServiceLocator.getInstance().getService(LogImportacaoBIService.class, null);
		ConexaoBIService conexaoBIService = (ConexaoBIService)  ServiceLocator.getInstance().getService(ConexaoBIService.class, null);
		
		Integer itensPorPagina = Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.QUANT_RETORNO_PESQUISA, "10"));
		Integer totalPaginas = logImportacaoService.calculaTotalPaginas(logExecucaoBIDTO.getIdConexaoBI(), itensPorPagina);
		Integer paginaSelecionadaColaborador = logExecucaoBIDTO.getPaginaSelecionada();
        if (paginaSelecionadaColaborador == null) {
        	paginaSelecionadaColaborador = 1;
        }

        paginacaoLog(totalPaginas, paginaSelecionadaColaborador, request, document);
		
        Collection<LogImportacaoBIDTO> logImportacao = (Collection<LogImportacaoBIDTO>) logImportacaoService.paginacaoLog(logExecucaoBIDTO.getIdConexaoBI(), paginaSelecionadaColaborador, itensPorPagina);

        String strBuffer = "";
		if (logImportacao != null && !logImportacao.isEmpty()) {
			strBuffer += "<tr>";
			strBuffer += "	<th>" + UtilI18N.internacionaliza(request, "conexaoBI.conexaoBI") + "</th>";
			strBuffer += "	<th style='width:20%;'>" + UtilI18N.internacionaliza(request, "citcorpore.comum.datainicio") + "</th>";
			strBuffer += "	<th>" + UtilI18N.internacionaliza(request, "citcorpore.comum.datafim") + "</th>";
			strBuffer += "	<th>" + UtilI18N.internacionaliza(request, "citcorpore.comum.status") + "</th>";
			strBuffer += "	<th>" + UtilI18N.internacionaliza(request, "conexaoBI.log.detalhamento") + "</th>";
			strBuffer += "	<th>" + UtilI18N.internacionaliza(request, "citcorpore.comum.tipo") + "</th>";
			strBuffer += "	<th></th>";
			strBuffer += "</tr>";
			for (LogImportacaoBIDTO logImportacaoDto : logImportacao) {
				ConexaoBIDTO conexaoBIDTO = new ConexaoBIDTO();
				conexaoBIDTO.setIdConexaoBI(logExecucaoBIDTO.getIdConexaoBI());
				conexaoBIDTO = (ConexaoBIDTO) conexaoBIService.restore(conexaoBIDTO);
				
				strBuffer += "<tr>";
				strBuffer += "	<td>" + conexaoBIDTO.getNome() + "</td>";
				strBuffer += "	<td>" + UtilDatas.dateToSTRWithFormat(logImportacaoDto.getDataHoraInicio(), "dd/MM/yyyy HH:mm:ss.SSS") + "</td>";
				strBuffer += "	<td>" + UtilDatas.dateToSTRWithFormat(logImportacaoDto.getDataHoraFim(), "dd/MM/yyyy HH:mm:ss.SSS") + "</td>";
				strBuffer += "	<td>" + (logImportacaoDto.getStatus().equalsIgnoreCase("F") ? "Falha" : "Sucesso") + "</td>";
				strBuffer += "	<td>" + logImportacaoDto.getDetalhamento().replaceAll("\n", "<br/>") + "</td>";
				strBuffer += "	<td>" + (logImportacaoDto.getTipo().equalsIgnoreCase("A") ? "Automático" : (logImportacaoDto.getTipo().equalsIgnoreCase("M") ? "Manual" : "Teste de conexão")) + "</td>";
				strBuffer += "	<td><a href='javascript:baixarLog(" + logImportacaoDto.getIdLogImportacao() + ");'>" + UtilI18N.internacionaliza(request, "downloadagente.download") + "</a></td>";
				strBuffer += "</tr>";
			}
		} else {
			strBuffer += UtilI18N.internacionaliza(request, "conexaoBI.log.naoExisteLog");
		}

		document.getElementById("tblLog").setInnerHTML(strBuffer.toString());
	}

	//Insere os elementos de paginação
	public void paginacaoLog(Integer totalPaginas, Integer paginaSelecionada, HttpServletRequest request, DocumentHTML document) throws Exception {
		HTMLElement divPrincipal = document.getElementById("paginas");
		StringBuilder sb = new StringBuilder();
		if (totalPaginas>1){
			final Integer adjacentes = 2;
			if (paginaSelecionada == null)
				paginaSelecionada = 1;
			sb.append(" <div id='itenPaginacao' class='pagination pagination-right margin-none' > ");
			sb.append(" <ul>");
			sb.append(" <li " + (paginaSelecionada == 1 ? "class='disabled'" : "value='1' onclick='paginarItens(this.value);return false;'") + " ><a href='#'>"+UtilI18N.internacionaliza(request, "citcorpore.comum.primeiro")+"</a></li>");
			sb.append(" <li " + ((totalPaginas == 1 || paginaSelecionada == 1) ? "class='disabled'" : "value='"+(paginaSelecionada-1)+"' onclick='paginarItens(this.value);return false;'") + "><a href='#'>"+UtilI18N.internacionaliza(request, "citcorpore.comum.anterior")+"</a></li>");
			if(totalPaginas <= 5) {
				for (int i = 1; i <= totalPaginas; i++) {
					if (i == paginaSelecionada) {
						sb.append("<li id='"+i+"' value='"+i+"' onclick='paginarItens(this.value);return false;' class='active'><a href='#'>"+i+"</a></li>");
					} else {
						sb.append("<li id='"+i+"' value='"+i+"' onclick='paginarItens(this.value);return false;'><a href='#'>"+i+"</a></li>");
					}
				}
			} else {
				if (totalPaginas > 5) {
					if (paginaSelecionada < 1 + (2 * adjacentes)) {
						for (int i=1; i< 2 + (2 * adjacentes); i++) {
							if (i == paginaSelecionada) {
								sb.append("<li id='"+i+"' value='"+i+"' onclick='paginarItens(this.value);return false;' class='active'><a href='#'>"+i+"</a></li>");
							} else {
								sb.append("<li id='"+i+"' value='"+i+"' onclick='paginarItens(this.value);return false;'><a href='#'>"+i+"</a></li>");
							}
						}
					} else if (paginaSelecionada > (2 * adjacentes) && paginaSelecionada < totalPaginas - 3) {
						for (int i = paginaSelecionada-adjacentes; i<= paginaSelecionada + adjacentes; i++) {
							if (i == paginaSelecionada) {
								sb.append("<li id='"+i+"' value='"+i+"' onclick='paginarItens(this.value);return false;' class='active'><a href='#'>"+i+"</a></li>");
							} else {
								sb.append("<li id='"+i+"' value='"+i+"' onclick='paginarItens(this.value);return false;'><a href='#'>"+i+"</a></li>");
							}
						}
					} else {
						for (int i = totalPaginas - (0 + (2 * adjacentes)); i <= totalPaginas; i++) {
							if (i == paginaSelecionada) {
								sb.append("<li id='"+i+"' value='"+i+"' onclick='paginarItens(this.value);return false;' class='active'><a href='#'>"+i+"</a></li>");
							} else {
								sb.append("<li id='"+i+"' value='"+i+"' onclick='paginarItens(this.value);return false;'><a href='#'>"+i+"</a></li>");
							}
						}
					}
				}
			}
			sb.append(" <li " + ((totalPaginas == 1 || paginaSelecionada.equals(totalPaginas)) ? "class='disabled'" : "value='"+(paginaSelecionada+1)+"' onclick='paginarItens(this.value);return false;'") + " ><a href='#'>"+UtilI18N.internacionaliza(request, "citcorpore.comum.proximo")+"</a></li>");
			sb.append(" <li id='"+totalPaginas+"' value='"+totalPaginas+"' onclick='paginarItens(this.value);return false;'><a href='#'>"+UtilI18N.internacionaliza(request, "citcorpore.comum.ultimo")+"</a></li>");
			sb.append(" </ul>");
			sb.append(" </div>");
		}
		divPrincipal.setInnerHTML(sb.toString());
	}
}
