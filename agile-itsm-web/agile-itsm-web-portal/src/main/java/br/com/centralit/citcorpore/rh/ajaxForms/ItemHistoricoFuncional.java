package br.com.centralit.citcorpore.rh.ajaxForms;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.rh.bean.ItemHistoricoFuncionalDTO;
import br.com.centralit.citcorpore.rh.negocio.ItemHistoricoFuncionalService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

/**
 * @author david.silva
 *
 */
@SuppressWarnings("rawtypes")
public class ItemHistoricoFuncional extends AjaxFormAction{

	@Override
	public void load(DocumentHTML document, HttpServletRequest request,	HttpServletResponse response) throws Exception {
		WebUtil.validarSeUsuarioEstaNaSessao(request, document);
		
		if(!(isUserInGroup(request, "RH"))){
			document.executeScript("alert('Voce não tem permição para usar essa Funcionalidade. Apenas Participantes do Grupo RH');");
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "/pages/index/index.jsp'"); 
			return; 
		}
		
		ItemHistoricoFuncionalDTO itemHistoricoFuncionalDTO = (ItemHistoricoFuncionalDTO) document.getBean();		
		
		String idHistorico = request.getParameter("idHistoricoFuncional");
		
		if(idHistorico != null && idHistorico != ""){
			itemHistoricoFuncionalDTO.setIdHistoricoFuncional(Integer.parseInt(idHistorico));
		}
		
		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(itemHistoricoFuncionalDTO);
	}
	
	public void save(DocumentHTML document, HttpServletRequest request,	HttpServletResponse response) throws Exception {
		WebUtil.validarSeUsuarioEstaNaSessao(request, document);
		
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		ItemHistoricoFuncionalDTO itemHistoricoFuncionalDTO = (ItemHistoricoFuncionalDTO) document.getBean();		
		ItemHistoricoFuncionalService itemHistoricoFuncionalService = (ItemHistoricoFuncionalService) ServiceLocator.getInstance().getService(ItemHistoricoFuncionalService.class,null);
		
		if(itemHistoricoFuncionalDTO.getTitulo().equalsIgnoreCase("") || itemHistoricoFuncionalDTO.getTitulo() == null){
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
			return;
		}
		
		Date dataAtual = UtilDatas.getDataAtual();
		Integer idUsuario = usuario.getIdUsuario();
		
		if(dataAtual != null && !dataAtual.equals("") && idUsuario != null && !idUsuario.equals("")){
			itemHistoricoFuncionalDTO.setDtCriacao(dataAtual);
			itemHistoricoFuncionalDTO.setIdResponsavel(idUsuario);
		}
		
		itemHistoricoFuncionalDTO.setTipo("M");
		
		if(itemHistoricoFuncionalDTO.getIdItemHistorico() == null || itemHistoricoFuncionalDTO.getIdItemHistorico().equals(0)){
			itemHistoricoFuncionalService.create(itemHistoricoFuncionalDTO);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
		}else {
			itemHistoricoFuncionalService.update(itemHistoricoFuncionalDTO);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}

		HTMLForm form = document.getForm("form");
		form.clear();
		document.executeScript("parent.fecharModalItemHistorico();");
	}
	
	public boolean isUserInGroup(HttpServletRequest req, String grupo) {
		UsuarioDTO usuario = WebUtil.getUsuario(req);
		if (usuario == null) {
			return false;
		}

		String[] grupos = usuario.getGrupos();
		String grpAux = UtilStrings.nullToVazio(grupo);
		for (int i = 0; i < grupos.length; i++) {
			if (grupos[i] != null) {
				if (grupos[i].trim().indexOf(grpAux.trim()) > 0) {
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public Class getBeanClass() {
		return ItemHistoricoFuncionalDTO.class;
	}

}
