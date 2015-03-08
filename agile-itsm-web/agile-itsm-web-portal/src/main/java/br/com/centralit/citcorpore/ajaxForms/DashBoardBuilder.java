package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.BIDashBoardDTO;
import br.com.centralit.citcorpore.bean.BIItemDashBoardDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.BIDashBoardService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.dto.Usuario;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

public class DashBoardBuilder extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UsuarioDTO usuarioDto = WebUtil.getUsuario(request);
		if (usuarioDto == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}		
	}
	
	public void saveDash(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Usuario usuarioDto = WebUtil.getUsuarioSistema(request);
		if (usuarioDto == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		BIDashBoardService biDashBoardService = (BIDashBoardService) ServiceLocator.getInstance().getService(BIDashBoardService.class, usuarioDto);
		BIDashBoardDTO biDashBoardDTO = (BIDashBoardDTO)document.getBean();
		Collection colItensDash = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(BIItemDashBoardDTO.class, "colItensSerialize", request);
		if (colItensDash == null || colItensDash.size() == 0){
			document.alert(UtilI18N.internacionaliza(request, "dashboard.selecione") );
			return;
		}
		for (Iterator it = colItensDash.iterator(); it.hasNext();){
			BIItemDashBoardDTO biItemDashBoardDTO = (BIItemDashBoardDTO)it.next();
			if (biItemDashBoardDTO.getIdConsulta() == null){
				document.alert(UtilI18N.internacionaliza(request, "dashboard.existeItemSemDados") );
				return;				
			}
			if (biItemDashBoardDTO.getItemTop() == null){
				biItemDashBoardDTO.setItemTop(0);
			}
			if (biItemDashBoardDTO.getItemLeft() == null){
				biItemDashBoardDTO.setItemLeft(0);
			}
			if (biItemDashBoardDTO.getItemWidth() == null){
				biItemDashBoardDTO.setItemWidth(0);
			}
			if (biItemDashBoardDTO.getItemHeight() == null){
				biItemDashBoardDTO.setItemHeight(0);
			}	
			if (biItemDashBoardDTO.getPosicao() == null){
				biItemDashBoardDTO.setPosicao(0);
			}			
		}
		biDashBoardDTO.setColItens(colItensDash);
		if (biDashBoardDTO.getIdDashBoard() == null || biDashBoardDTO.getIdDashBoard().intValue() == 0){
			biDashBoardDTO = (BIDashBoardDTO) biDashBoardService.create(biDashBoardDTO);
			document.getElementById("idDashBoard").setValue("" + biDashBoardDTO.getIdDashBoard());
			document.alert(UtilI18N.internacionaliza(request, "MSG05") );
		}else{
			biDashBoardService.update(biDashBoardDTO);
			document.alert(UtilI18N.internacionaliza(request, "MSG06") );
		}
		document.executeScript("$( \"#POPUP_SALVAR\" ).dialog( \"close\" );");
	}

	@Override
	public Class getBeanClass() {
		return BIDashBoardDTO.class;
	}

}
