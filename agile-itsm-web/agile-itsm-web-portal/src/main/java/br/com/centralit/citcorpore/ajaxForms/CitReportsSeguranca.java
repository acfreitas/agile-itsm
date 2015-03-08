package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.BIConsultaSegurDTO;
import br.com.centralit.citcorpore.negocio.BIConsultaSegurService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.citframework.service.ServiceLocator;

public class CitReportsSeguranca extends AjaxFormAction{
	@Override
	public void load(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
        GrupoService grupoService = (GrupoService)ServiceLocator.getInstance().getService(GrupoService.class, null);
        Collection perfil = grupoService.list();
        request.setAttribute("perfil", perfil);
	}
	
	public void mostraSeguranca(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BIConsultaSegurDTO biConsultaSegurDTO = (BIConsultaSegurDTO)document.getBean();
		BIConsultaSegurService biConsultaSegurService = (BIConsultaSegurService)ServiceLocator.getInstance().getService(BIConsultaSegurService.class, null);
		HTMLForm form = document.getForm("formPainel");
		document.executeScript("clearAllCheckBox()");
		
		Collection col = biConsultaSegurService.findByIdConsulta(biConsultaSegurDTO.getIdConsulta());
		if (col != null && col.size() > 0){
			biConsultaSegurDTO.setPerfilSelecionado(new Integer[col.size()]);
			Integer[] idPerfs = new Integer[col.size()];
			int i = 0;
			for(Iterator it = col.iterator(); it.hasNext();){
				BIConsultaSegurDTO citGerencialSegurancaDTO = (BIConsultaSegurDTO)it.next();
				idPerfs[i] = citGerencialSegurancaDTO.getIdGrupo();
				i++;
			}
			biConsultaSegurDTO.setPerfilSelecionado(idPerfs);
		}
		if(biConsultaSegurDTO.getPerfilSelecionado() != null && biConsultaSegurDTO.getPerfilSelecionado().length > 0){
		    for (int i = 0; i < biConsultaSegurDTO.getPerfilSelecionado().length; i++) {
		        document.executeScript("selectCheckBoxByValue('" + biConsultaSegurDTO.getPerfilSelecionado()[i] + "')");
            }
		}
		document.executeScript("hideAguarde()");
		//document.alert("Registro recuperado ! Defina os grupos de acesso e clique em Gravar!");        
	}
	
	public void gravarSeguranca(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BIConsultaSegurDTO biConsultaSegurDTO = (BIConsultaSegurDTO)document.getBean();
		BIConsultaSegurService biConsultaSegurService = (BIConsultaSegurService)ServiceLocator.getInstance().getService(BIConsultaSegurService.class, null);
		biConsultaSegurService.deleteByIdConsulta(biConsultaSegurDTO.getIdConsulta());
		if (biConsultaSegurDTO.getPerfilSelecionado() != null){
			for(int i = 0; i < biConsultaSegurDTO.getPerfilSelecionado().length; i++){
				BIConsultaSegurDTO citGerencialSegurancaDTO = new BIConsultaSegurDTO();
				citGerencialSegurancaDTO.setIdConsulta(biConsultaSegurDTO.getIdConsulta());
				citGerencialSegurancaDTO.setIdGrupo(biConsultaSegurDTO.getPerfilSelecionado()[i]);
				biConsultaSegurService.create(citGerencialSegurancaDTO);
			}
		}
		document.executeScript("hideAguarde()");
		document.alert("Perfil de segurança aplicado com sucesso!");    
	}	

	@Override
	public Class getBeanClass() {
		return BIConsultaSegurDTO.class;
	}	
}
