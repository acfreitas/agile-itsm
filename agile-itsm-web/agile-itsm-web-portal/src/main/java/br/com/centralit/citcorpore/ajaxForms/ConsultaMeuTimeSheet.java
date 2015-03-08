package br.com.centralit.citcorpore.ajaxForms;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citajax.util.JavaScriptUtil;
import br.com.centralit.citcorpore.bean.ConsultaMeuTimeSheetDTO;
import br.com.centralit.citcorpore.bean.TimeSheetDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.TimeSheetService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilHTML;

public class ConsultaMeuTimeSheet extends AjaxFormAction {

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null){
			document.alert("Sessão expirada! Favor efetuar logon novamente!");
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}		
		document.focusInFirstActivateField(null);
	}
	public void pesquisaTimeSheet(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ConsultaMeuTimeSheetDTO consultaTimeSheet = (ConsultaMeuTimeSheetDTO) document.getBean();
		TimeSheetService timeSheetService = (TimeSheetService) ServiceLocator.getInstance().getService(TimeSheetService.class, null);
		
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null){
			document.alert("Sessão expirada! Favor efetuar logon novamente!");
			document.executeScript("window.location = '" + request.getContextPath() + "'");
			return;
		}		
		Integer idEmpregado = new Integer(usuario.getIdUsuario());		
		
		Collection col = timeSheetService.findByPessoaAndPeriodo(idEmpregado, consultaTimeSheet.getDataInicio(), consultaTimeSheet.getDataFim());
		
		HTMLTable tblMeuTimeSheet = (HTMLTable) document.getTableById("tblMeuTimeSheet");
		tblMeuTimeSheet.deleteAllRows();
		
		Date d = consultaTimeSheet.getDataInicio();
		Collection colAux = new ArrayList();
		while(d.compareTo(consultaTimeSheet.getDataFim()) <= 0){
			Collection colRet = null;
			if (col != null){
				colRet = comparaDataNaColecao(d, col);
			}
			if (colRet != null){
				colAux.addAll(colRet);
			}
			
			d = new Date(UtilDatas.incrementaDiasEmData(d, 1).getTime());
		}
		
		if (colAux != null){
			tblMeuTimeSheet.addRowsByCollection(colAux, 
					new String[] {"dataStrDet", "nomeProjeto", "detalhamentoDemanda", "detalhamento", "qtdeHorasStr2"}, 
					null, 
					"Já existe registrado esta demanda na tabela", 
					null, 
					null, 
					null);
		}
		
		TimeSheetDTO timeSheetBean; 
		double qtdeHorasTotal = 0;
		
		if (col != null){
			Iterator it = col.iterator();
			while(it.hasNext()){
				timeSheetBean = (TimeSheetDTO)it.next();
				qtdeHorasTotal = qtdeHorasTotal + timeSheetBean.getQtdeHoras().doubleValue();
			}
		}
		
		timeSheetBean = new TimeSheetDTO();
		timeSheetBean.setNomeCliente("");
		timeSheetBean.setNomeProjeto(" <b>Total--></b>");
		timeSheetBean.setData(null);
		timeSheetBean.setDetalhamentoDemanda("");
		timeSheetBean.setQtdeHoras(new Double(qtdeHorasTotal));
		
		tblMeuTimeSheet.addRow(timeSheetBean, 
				new String[] {"dataStr", "nomeCliente", "nomeProjeto", "detalhamentoDemanda", "qtdeHorasStr"}, 
				null, 
				"Já existe registrado esta demanda na tabela", 
				new String[] {"ExecutaInsercaoTabela"}, 
				null, 
				null);
	}
	public Class getBeanClass(){
		return ConsultaMeuTimeSheetDTO.class;
	}
	
	private Collection comparaDataNaColecao(Date d, Collection col){
		Collection colRet = new ArrayList();
		for(Iterator it = col.iterator(); it.hasNext();){
			TimeSheetDTO timeSheet = (TimeSheetDTO)it.next();
			//Por incrivel que pareça a simples comparação de datas não funciona, por isto foi colocado a comparação de strings
			if (UtilDatas.dateToSTR(timeSheet.getData()).equalsIgnoreCase(UtilDatas.dateToSTR(d))){
				colRet.add(timeSheet);
			}
		}
		
		if (colRet.size()>0){
			return colRet;
		}
		
		if (UtilDatas.verificaDiaUtil(d)){
			TimeSheetDTO timeSheet = new TimeSheetDTO(); 
			timeSheet.setData(d);
			timeSheet.setNomeCliente(JavaScriptUtil.escapeJavaScript(UtilHTML.encodeHTML("<font color='red'><b>Não há registro de timesheet</b></font>")));
			timeSheet.setNomeProjeto(JavaScriptUtil.escapeJavaScript(UtilHTML.encodeHTML("<font color='red'><b>Não há registro de timesheet</b></font>")));
			timeSheet.setQtdeHoras(new Double(0));
			colRet.add(timeSheet);
			
			return colRet;
		}
		return null;
	}
}
