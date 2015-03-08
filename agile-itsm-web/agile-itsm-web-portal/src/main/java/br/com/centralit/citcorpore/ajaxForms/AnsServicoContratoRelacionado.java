package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLElement;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.AcordoNivelServicoDTO;
import br.com.centralit.citcorpore.bean.AcordoServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.negocio.AcordoNivelServicoService;
import br.com.centralit.citcorpore.negocio.AcordoServicoContratoService;
import br.com.centralit.citcorpore.negocio.ServicoContratoService;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

/**
 * 
 * @author flavio.santana
 * @since 12/06/2013
 */
public class AnsServicoContratoRelacionado extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		AcordoServicoContratoDTO acordoServicoContratoDTO = (AcordoServicoContratoDTO) document.getBean();
		
		request.setAttribute("idContrato", acordoServicoContratoDTO.getIdContrato());
		
		listarSLAsContrato(document, request, response);
		//listaServicos(document, request, response);
		
	}

	@SuppressWarnings("unchecked")
	@Deprecated
	public void listaServicos(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		document.executeScript("JANELA_AGUARDE_MENU.show();");
		
		ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
		
		AcordoServicoContratoDTO acordoServicoContratoDTO = (AcordoServicoContratoDTO) document.getBean();
		
		HTMLElement divPrincipal = document.getElementById("relacionarServicos");
		StringBuilder subDiv = new StringBuilder();
		subDiv.append("" +
				"<div class='formBody'> " +
				"	<table id='tblServicoContrato' class='tableLess'> 	" +
				"		<thead>" +
				"			<tr>" +
				"				<th><input type='checkbox' id='todos' onclick='check(filtroServicoContrato);' /></th>	" +
				"				<th>"+UtilI18N.internacionaliza(request, "servico.nome")+"</th>	" +
				"			</tr>" +
				"		</thead>" +
				"<tbody>");
		List<ServicoContratoDTO> listServicoContrato = (List<ServicoContratoDTO>) servicoContratoService.findServicoContratoByIdContrato(acordoServicoContratoDTO.getIdContrato());
		
		if(listServicoContrato!=null){
			int count = 0;
			for (ServicoContratoDTO servicoContratoDTO : listServicoContrato) {				
				String checked = "";
				count++;
				subDiv.append(
						"	<tr>"+
						"		<td width='5%' class='center'>" + "<input type='checkbox' "+ checked + " id='idServicoContrato" + count + "'" +
						" name='idServicoContrato' value='0"+servicoContratoDTO.getIdServicoContrato() + "'/></td>" +
						"		<td>" + (servicoContratoDTO.getNomeServico() == null ? "" : servicoContratoDTO.getNomeServico()) + "</td>" +
						"	</tr>");
			}
		}
		subDiv.append(
				"</tbody>" +
			"	</table>" +
			"</div>");
		divPrincipal.setInnerHTML(subDiv.toString());
		
		document.executeScript("JANELA_AGUARDE_MENU.hide();");
	}
	
	@SuppressWarnings("unchecked")
	public void listaServicosRelacionados(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
						
		AcordoServicoContratoDTO acordoServicoContratoDTO = (AcordoServicoContratoDTO) document.getBean();
		
		ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
		AcordoServicoContratoService acordoServicoContratoService = (AcordoServicoContratoService) ServiceLocator.getInstance().getService(AcordoServicoContratoService.class, null);
		AcordoNivelServicoService acordoNivelServicoService = (AcordoNivelServicoService) ServiceLocator.getInstance().getService(AcordoNivelServicoService.class, null);
		
		AcordoNivelServicoDTO bean = new AcordoNivelServicoDTO();
		
		if(acordoServicoContratoDTO.getIdAcordoNivelServico() != null && acordoServicoContratoDTO.getIdAcordoNivelServico() != 0) {
			bean.setIdAcordoNivelServico(acordoServicoContratoDTO.getIdAcordoNivelServico());
			bean = (AcordoNivelServicoDTO) acordoNivelServicoService.restore(bean);
		}
		
		if(bean != null && bean.getTipo()!=null && bean.getTipo().equalsIgnoreCase("T"))
			document.executeScript("mostra();");
		else
			document.executeScript("oculta();");
		
		HTMLElement divPrincipal = document.getElementById("relacionarServicos");
		StringBuilder subDiv = new StringBuilder();
		subDiv.append("" +
				"<div class='formBody'> " +
				"	<table id='tblServicoContrato' class='tableLess'> 	" +
				"		<thead>" +
				"			<tr>" +
				"				<th><input type='checkbox' id='todos' onclick='check(filtroServicoContrato);' /></th>	" +
				"				<th>"+UtilI18N.internacionaliza(request, "servico.nome")+"</th>	" +
				"			</tr>" +
				"		</thead>" +
				"<tbody>");
		List<ServicoContratoDTO> listServicoContrato = (List<ServicoContratoDTO>) servicoContratoService.findServicoContratoByIdContrato(acordoServicoContratoDTO.getIdContrato());
		
		if(listServicoContrato!=null){
			int count = 0;
			for (ServicoContratoDTO servicoContratoDTO : listServicoContrato) {				
				List<AcordoServicoContratoDTO> listaServicosRelacionados = (List<AcordoServicoContratoDTO>) acordoServicoContratoService.findByIdAcordoNivelServicoIdServicoContrato(acordoServicoContratoDTO.getIdAcordoNivelServico(), servicoContratoDTO.getIdServicoContrato());
				String checked = (listaServicosRelacionados != null && !listaServicosRelacionados.isEmpty() ? "checked='checked'" : "");
				count++;
				subDiv.append(
						"	<tr>"+
						"		<td width='5%' class='center'>" + "<input type='checkbox' "+ checked + " id='idServicoContrato" + count + "'" +
						" name='idServicoContrato' value='0"+servicoContratoDTO.getIdServicoContrato() + "'/></td>" +
						"		<td>" + (servicoContratoDTO.getNomeServico() == null ? "" : servicoContratoDTO.getNomeServico()) + "</td>" +
						"	</tr>");
			}
		}
		subDiv.append(
				"</tbody>" +
			"	</table>" +
			"</div>");
		divPrincipal.setInnerHTML(subDiv.toString());
		
		StringBuilder divBusca = new StringBuilder();
		divBusca.append("" +
				"<div > " +
				"	<label style='float: left; padding-top: 10px; padding-right: 20px;'>Buscar: </label> " +
				"	<div style='width: 220px;float: left;'> " +
				"		<input type='text' id='filtroServicoContrato' onkeyup='filtroTableAcNivelServ(this, \"tblServicoContrato\")' name='filtroServicoContrato' class='text'> " +
				"	</div> " +
				"</div> " +
				"");
		document.getElementById("buscaServico").setInnerHTML(divBusca.toString());
		
		document.executeScript("JANELA_AGUARDE_MENU.hide();");
	}
	
	public void listarSLAsContrato(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		AcordoNivelServicoService acordoNivelServicoService = (AcordoNivelServicoService) ServiceLocator.getInstance().getService(AcordoNivelServicoService.class, null);
		List<AcordoNivelServicoDTO> acordoNivelServicoDTOs = acordoNivelServicoService.findAcordosSemVinculacaoDireta();
		
		acordoNivelServicoDTOs = (acordoNivelServicoDTOs != null ? acordoNivelServicoDTOs :  new ArrayList<AcordoNivelServicoDTO>());
		/*Combo de criticidade*/
		HTMLSelect combo = (HTMLSelect) document.getSelectById("idAcordoNivelServico");
		combo.removeAllOptions();
		combo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		for (AcordoNivelServicoDTO acordoNivelServicoDTO : acordoNivelServicoDTOs) {
			combo.addOption(acordoNivelServicoDTO.getIdAcordoNivelServico().toString(), UtilStrings.getParameter(acordoNivelServicoDTO.getTituloSLA().toString()));
		}
		
		document.executeScript("JANELA_AGUARDE_MENU.hide();");
	}
	
	@SuppressWarnings("unchecked")
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		AcordoServicoContratoDTO acordoServicoContratoDTO = (AcordoServicoContratoDTO) document.getBean();
		
		AcordoServicoContratoService acordoServicoContratoService = (AcordoServicoContratoService) ServiceLocator.getInstance().getService(AcordoServicoContratoService.class, null);
		
		List<ServicoContratoDTO> listaServicos = ((ArrayList<ServicoContratoDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(ServicoContratoDTO.class, "servicosSerializados", request));
		if(listaServicos != null){
			acordoServicoContratoDTO.setListaServicoContrato(listaServicos);
		}
		
		acordoServicoContratoDTO.setDataCriacao(UtilDatas.getDataAtual());
		acordoServicoContratoDTO.setDataInicio(UtilDatas.getDataAtual());
		
		if (acordoServicoContratoService.existeAcordoServicoContrato(acordoServicoContratoDTO.getIdAcordoNivelServico(), acordoServicoContratoDTO.getIdContrato())) {
			acordoServicoContratoService.update(acordoServicoContratoDTO);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		} else {
			acordoServicoContratoService.create(acordoServicoContratoDTO);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
		}
		
		document.executeScript("JANELA_AGUARDE_MENU.hide();");
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Class getBeanClass() {
		return AcordoServicoContratoDTO.class;
	}

}
