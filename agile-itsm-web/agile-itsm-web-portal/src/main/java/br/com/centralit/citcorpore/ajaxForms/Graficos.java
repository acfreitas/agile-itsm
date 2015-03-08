/**
 * 
 */
package br.com.centralit.citcorpore.ajaxForms;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.GraficoPizzaDTO;
import br.com.centralit.citcorpore.bean.GraficosDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.negocio.GraficosService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;

/**
 * @author breno.guimaraes
 * 
 */
public class Graficos extends AjaxFormAction{

    private GraficosService getService() throws ServiceException, Exception {
	return (GraficosService) ServiceLocator.getInstance().getService(GraficosService.class, null);
    }

    @Override
    public Class getBeanClass() {
	return GraficosDTO.class;
    }

    @Override
    public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
    }

    public void renderizaGraficos(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	ArrayList<GraficoPizzaDTO> graficos = getService().getRelatorioPorSituacao();
	ArrayList<GraficoPizzaDTO> graficos2 = getService().getRelatorioPorNomeCategoria();
	ArrayList<GraficoPizzaDTO> graficos3 = getService().getRelatorioPorGrupo();

	String script = getScriptGrafico(graficos, "chart1");
	if (script != null && !script.equalsIgnoreCase("")){
	    document.executeScript(script);
	}
	script = getScriptGrafico(graficos2, "chart2");
	if (script != null && !script.equalsIgnoreCase("")){
	    document.executeScript(script);
	}
	script = getScriptGrafico(graficos3, "chart3");
	if (script != null && !script.equalsIgnoreCase("")){
	    document.executeScript(script);
	}	
	
	String buffer = "<b>INCIDENTES COM QUEBRA DE SLA</b>";
	    buffer += "<table>";	
	    buffer += "<tr>";
	    buffer += "<td class='linhaSubtituloGridOcorr'>";
	    	buffer += "Número";
	    buffer += "</td>";
	    buffer += "<td class='linhaSubtituloGridOcorr'>";
	    	buffer += "Data/hora solicitação";
	    buffer += "</td>";
	    buffer += "<td class='linhaSubtituloGridOcorr'>";
    		buffer += "Data/hora limite";
    	    buffer += "</td>";
	    buffer += "<td class='linhaSubtituloGridOcorr'>";
    		buffer += "Atraso";
    	    buffer += "</td>";		    	    
    	    buffer += "</tr>";	
	SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
	Collection listaSolicitacaoServico = solicitacaoServicoService.listIncidentesNaoFinalizados();
	if (listaSolicitacaoServico != null){
	    for (Iterator it = listaSolicitacaoServico.iterator(); it.hasNext();){
		SolicitacaoServicoDTO solicitacao = (SolicitacaoServicoDTO)it.next();
		if (solicitacao.getDataHoraLimite() != null && solicitacao.getDataHoraLimite().before(UtilDatas.getDataHoraAtual())){
		    solicitacao = solicitacaoServicoService.verificaSituacaoSLA(solicitacao);
		    buffer += "<tr>";
        		    buffer += "<td class='celulaGridPesq'>";
        		    	buffer += "" + solicitacao.getIdSolicitacaoServico();
        		    buffer += "</td>";
        		    buffer += "<td class='celulaGridPesq'>";
    		    		buffer += "" + formataDataHora(solicitacao.getDataHoraSolicitacao());
    		    	    buffer += "</td>";
        		    buffer += "<td class='celulaGridPesq'>";
		    		buffer += "" + formataDataHora(solicitacao.getDataHoraLimite());
		    	    buffer += "</td>";
        		    buffer += "<td class='celulaGridPesq'>";
		    		buffer += "" + solicitacao.getAtrasoSLAStr();
		    	    buffer += "</td>";		    	    
		    buffer += "</tr>";
		}		
	    }
	}
	buffer += "</table>";
	document.getElementById("paiChart4").setInnerHTML(buffer);
    }

    private String getScriptGrafico(ArrayList<GraficoPizzaDTO> graficos, String componenteDestino){
	if (graficos == null || graficos.size() == 0){
	    return "";
	}
	StringBuilder sb = new StringBuilder();
	sb.append("plotaGrafico(");
	sb.append("[");
	boolean prim = true;
	for (GraficoPizzaDTO g : graficos) {
	    if (!prim){
		sb.append(",");
	    }
	    sb.append("['" + g.getCampo() + "', " + g.getValor()+ "]");
	    prim = false;
	}
	sb.append("]");
	sb.append(", '" + componenteDestino + "');");
	return sb.toString();
	
    }
    
    private String formataDataHora(Timestamp dateDate){
	SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return df.format(dateDate);
    }
}
