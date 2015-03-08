package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.InformacoesContratoConfigDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.InformacoesContratoConfigService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citquestionario.negocio.QuestionarioService;
import br.com.citframework.dto.Usuario;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;

public class InformacoesContratosConfig extends AjaxFormAction{

	public Class getBeanClass() {
		return InformacoesContratoConfigDTO.class;
	}

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuarioDto = WebUtil.getUsuario(request);
		if (usuarioDto == null){
			document.alert("Sessão expirada!!! Favor efetuar logon novamente!");
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}		
        
        QuestionarioService questionarioService = (QuestionarioService)ServiceLocator.getInstance().getService(QuestionarioService.class, null);
        Collection collection = questionarioService.listByIdEmpresa(usuarioDto.getIdEmpresa());
        HTMLSelect combo = document.getSelectById("idQuestionario");
        combo.removeAllOptions();
        combo.addOption("", "-- Selecione um Questionário --");        
        if(collection != null && !collection.isEmpty()){
        	combo.addOptions(collection, "idQuestionario", "nomeQuestionario", null);                            
        }	
        
        InformacoesContratoConfigService prontuarioService = (InformacoesContratoConfigService)ServiceLocator.getInstance().getService(InformacoesContratoConfigService.class, null);
        Collection collection1 = prontuarioService.list();
        HTMLSelect combo1 = document.getSelectById("idInformacoesContratoConfigPai");
        combo1.removeAllOptions();
        combo1.addOption("", "-- Selecione -- ");
        if(collection1 != null && !collection1.isEmpty()){
            combo1.addOptions(collection1, "idInformacoesContratoConfig", "nome", null);
        }

        
        /*PerfilSegurancaService perfilService = (PerfilSegurancaService)ServiceLocator.getInstance().getService(PerfilSegurancaService.class, null);
        Collection perfil = perfilService.list();
        request.setAttribute("perfil", perfil);
        document.executeScript("clearAllCheckBox");*/
	}
	
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse arg2) throws Exception {
		Usuario user = (Usuario) request.getSession().getAttribute(Constantes.getValue("USUARIO_SESSAO"));
		if (user == null){
			document.alert("O usuário não está logado! Favor logar no sistema!");
			return;
		}			
	    InformacoesContratoConfigDTO contratoBean = (InformacoesContratoConfigDTO) document.getBean();
		InformacoesContratoConfigService prontuarioService = (InformacoesContratoConfigService) ServiceLocator.getInstance().getService(InformacoesContratoConfigService.class, null);
		
		contratoBean.setIdEmpresa(user.getIdEmpresa());
		contratoBean.setValidacoes("");
		String val = "";
		if (contratoBean.getValidacoesAux() != null){
			for(int i = 0; i < contratoBean.getValidacoesAux().length; i++){
				if (!val.equalsIgnoreCase("")){
					val += ",";
				}
				val += contratoBean.getValidacoesAux()[i];
			}
		}
		contratoBean.setValidacoes(val);
		if (contratoBean.getIdInformacoesContratoConfig()!=null && contratoBean.getIdInformacoesContratoConfig().intValue()>0){
		    prontuarioService.update(contratoBean);
		}else{
		    prontuarioService.create(contratoBean);
		}
		load(document, request, arg2);
		
		HTMLForm form = document.getForm("form");
		form.clear();
		
		document.alert("Registro gravado com sucesso!");		
	}
	
	public void restore(DocumentHTML document, HttpServletRequest arg1,	HttpServletResponse arg2) throws Exception {
	    InformacoesContratoConfigDTO prontuarioBean = (InformacoesContratoConfigDTO) document.getBean();
        InformacoesContratoConfigService prontuarioService = (InformacoesContratoConfigService) ServiceLocator.getInstance().getService(InformacoesContratoConfigService.class, null);
        Collection collection1 = prontuarioService.list();
        HTMLSelect combo1 = document.getSelectById("idInformacoesContratoConfigPai");
        combo1.removeAllOptions();
        combo1.addOption("", "-- Selecione -- ");
        if(collection1 != null && !collection1.isEmpty()){
            combo1.addOptions(collection1, "idInformacoesContratoConfig", "nome", null);
        }
        prontuarioBean = (InformacoesContratoConfigDTO) prontuarioService.restore(prontuarioBean);
        
        QuestionarioService questionarioService = (QuestionarioService)ServiceLocator.getInstance().getService(QuestionarioService.class, null);
        Collection collection = questionarioService.list();
        HTMLSelect combo = document.getSelectById("idQuestionario");
        combo.removeAllOptions();
        combo.addOption("", "-- Selecione --");        
        if(collection != null && !collection.isEmpty()){
            combo.addOptions(collection, "idQuestionario", "nomeQuestionario", null);
        }
        
		HTMLForm form = document.getForm("form");
		form.clear();	
		if (prontuarioBean.getValidacoes() != null && !prontuarioBean.getValidacoes().trim().equalsIgnoreCase("")){
			String strAux = prontuarioBean.getValidacoes() + ",";
			String[] str = strAux.split(",");
			prontuarioBean.setValidacoesAux(str);
			document.getCheckboxById("validacoesAux").setValue(str);
		}
		form.setValues(prontuarioBean);
		document.executeScript("clearAllCheckBox()");
		if(prontuarioBean.getPerfilSelecionado() != null && prontuarioBean.getPerfilSelecionado().length > 0){
		    for (int i = 0; i < prontuarioBean.getPerfilSelecionado().length; i++) {
		        document.executeScript("selectCheckBoxByValue('" + prontuarioBean.getPerfilSelecionado()[i] + "')");
            }
		}
		document.alert("Registro recuperado !");
	}	

}

