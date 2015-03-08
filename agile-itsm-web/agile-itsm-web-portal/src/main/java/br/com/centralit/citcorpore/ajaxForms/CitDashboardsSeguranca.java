package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.BIDashBoardSegurDTO;
import br.com.centralit.citcorpore.negocio.BIDashBoardSegurService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.citframework.service.ServiceLocator;

public class CitDashboardsSeguranca extends AjaxFormAction{
	@Override
	public void load(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
        GrupoService grupoService = (GrupoService)ServiceLocator.getInstance().getService(GrupoService.class, null);
        Collection perfil = grupoService.list();
        request.setAttribute("perfil", perfil);
	}
	
	public void mostraSeguranca(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BIDashBoardSegurDTO biDashBoardSegurDTO = (BIDashBoardSegurDTO)document.getBean();
		BIDashBoardSegurService biDashBoardSegurService = (BIDashBoardSegurService)ServiceLocator.getInstance().getService(BIDashBoardSegurService.class, null);
		HTMLForm form = document.getForm("formPainel");
		document.executeScript("clearAllCheckBox()");
		
		Collection col = biDashBoardSegurService.findByIdDashBoard(biDashBoardSegurDTO.getIdDashBoard());
		if (col != null && col.size() > 0){
			biDashBoardSegurDTO.setPerfilSelecionado(new Integer[col.size()]);
			Integer[] idPerfs = new Integer[col.size()];
			int i = 0;
			for(Iterator it = col.iterator(); it.hasNext();){
				BIDashBoardSegurDTO citGerencialSegurancaDTO = (BIDashBoardSegurDTO)it.next();
				idPerfs[i] = citGerencialSegurancaDTO.getIdGrupo();
				i++;
			}
			biDashBoardSegurDTO.setPerfilSelecionado(idPerfs);
		}
		if(biDashBoardSegurDTO.getPerfilSelecionado() != null && biDashBoardSegurDTO.getPerfilSelecionado().length > 0){
		    for (int i = 0; i < biDashBoardSegurDTO.getPerfilSelecionado().length; i++) {
		        document.executeScript("selectCheckBoxByValue('" + biDashBoardSegurDTO.getPerfilSelecionado()[i] + "')");
            }
		}
		document.executeScript("hideAguarde()");
		//document.alert("Registro recuperado ! Defina os grupos de acesso e clique em Gravar!");        
	}
	
	public void gravarSeguranca(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BIDashBoardSegurDTO biDashBoardSegurDTO = (BIDashBoardSegurDTO)document.getBean();
		BIDashBoardSegurService biDashBoardSegurService = (BIDashBoardSegurService)ServiceLocator.getInstance().getService(BIDashBoardSegurService.class, null);
		biDashBoardSegurService.deleteByIdDashBoard(biDashBoardSegurDTO.getIdDashBoard());
		if (biDashBoardSegurDTO.getPerfilSelecionado() != null){
			for(int i = 0; i < biDashBoardSegurDTO.getPerfilSelecionado().length; i++){
				BIDashBoardSegurDTO citGerencialSegurancaDTO = new BIDashBoardSegurDTO();
				citGerencialSegurancaDTO.setIdDashBoard(biDashBoardSegurDTO.getIdDashBoard());
				citGerencialSegurancaDTO.setIdGrupo(biDashBoardSegurDTO.getPerfilSelecionado()[i]);
				biDashBoardSegurService.create(citGerencialSegurancaDTO);
			}
		}
		document.executeScript("hideAguarde()");
		document.alert("Perfil de segurança aplicado com sucesso!");    
	}	

	@Override
	public Class getBeanClass() {
		return BIDashBoardSegurDTO.class;
	}	
}
