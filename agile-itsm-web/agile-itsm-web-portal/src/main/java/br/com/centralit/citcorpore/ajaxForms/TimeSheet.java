package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citajax.html.HTMLTextBox;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.TimeSheetDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.TimeSheetService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;

public class TimeSheet extends AjaxFormAction {

	public Class getBeanClass() {
		return TimeSheetDTO.class;
	}

	public void load(DocumentHTML arg0, HttpServletRequest arg1,
			HttpServletResponse arg2) throws Exception {
	}

	public void registrarTimeSheet(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeSheetService timeSheetService = (TimeSheetService) ServiceLocator.getInstance().getService(TimeSheetService.class, null);
		TimeSheetDTO timeSheetBean = (TimeSheetDTO)document.getBean();
		
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		timeSheetBean.setIdEmpregado(new Integer(usuario.getIdUsuario()));
		
		EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
		EmpregadoDTO empregadoBean = new EmpregadoDTO();
		empregadoBean.setIdEmpregado(timeSheetBean.getIdEmpregado());
		empregadoBean = (EmpregadoDTO) empregadoService.restore(empregadoBean);
		
		if (empregadoBean == null){
			document.alert("ERRO: Não foi possivel recuperar dados do empregado logado!");
			return;
		}
		
		timeSheetBean.setCustoPorHora(empregadoBean.getCustoPorHora());
		
		timeSheetService.create(timeSheetBean);
		
		document.alert("TimeSheet registrado com sucesso!");
		
		HTMLTextBox dataTimeSheet = document.getTextBoxById("dataTimeSheet");
		HTMLTextBox qtdeHorasTimeSheet = document.getTextBoxById("qtdeHorasTimeSheet");
		HTMLTextBox detalhamentoTimeSheet = document.getTextBoxById("detalhamentoTimeSheet");
		
		dataTimeSheet.setValue("");
		qtdeHorasTimeSheet.setValue("");
		detalhamentoTimeSheet.setValue("");
	}
	
	public void consultarTimeSheet(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeSheetService timeSheetService = (TimeSheetService) ServiceLocator.getInstance().getService(TimeSheetService.class, null);
		TimeSheetDTO timeSheetBean = (TimeSheetDTO)document.getBean();
		
		Collection col = timeSheetService.findByDemanda(timeSheetBean.getIdDemanda());
		
		HTMLTable tabelaConsultaTimeSheet = document.getTableById("tabelaConsultaTimeSheet");
		
		tabelaConsultaTimeSheet.deleteAllRows();
		tabelaConsultaTimeSheet.addRowsByCollection(col, 
				new String[] {"dataStr", "qtdeHorasStr", "nomeEmpregado", "detalhamento"}, 
				null, 
				"Já existe registrado este registro na tabela", 
				null, 
				null, 
				null);
		tabelaConsultaTimeSheet.applyStyleClassInAllCells("tamanho10");		
	}	
}
