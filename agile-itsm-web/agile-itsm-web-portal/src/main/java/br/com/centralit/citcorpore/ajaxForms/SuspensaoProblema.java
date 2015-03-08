package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.JustificativaProblemaDTO;
import br.com.centralit.citcorpore.bean.ProblemaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.JustificativaProblemaService;
import br.com.centralit.citcorpore.negocio.ProblemaService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;
@SuppressWarnings("rawtypes")
public class SuspensaoProblema extends AjaxFormAction {

   
	@Override
    public Class getBeanClass() {
	return ProblemaDTO.class;
    }

    @Override
    public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null){
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		HTMLForm form = document.getForm("form");
		form.clear();	
		ProblemaDTO problemaDto = (ProblemaDTO)document.getBean();
		ProblemaService problemaService = (ProblemaService) ServiceLocator.getInstance().getService(ProblemaService.class, WebUtil.getUsuarioSistema(request));
		JustificativaProblemaService justificativaProblemaService = (JustificativaProblemaService) ServiceLocator.getInstance().getService(JustificativaProblemaService.class, null);
		if(problemaDto.getIdProblema()!=null){
			problemaDto = problemaService.restoreAll(problemaDto.getIdProblema());
		}
		
		request.setAttribute("dataHoraCaptura", problemaDto.getDataHoraCaptura());
		
		Collection<JustificativaProblemaDTO> colJustificativas = justificativaProblemaService.listAtivasParaSuspensao();
		
		HTMLSelect comboJustificativa = (HTMLSelect) document.getSelectById("idJustificativaProblema");
		document.getSelectById("idJustificativaProblema").removeAllOptions();
		comboJustificativa.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		if (colJustificativas != null){
			for(JustificativaProblemaDTO justificativa : colJustificativas){
				comboJustificativa.addOption(justificativa.getIdJustificativaProblema().toString(), StringEscapeUtils.escapeJavaScript(justificativa.getDescricaoProblema()));
			}
		}
		form.setValues(problemaDto);
    }
    
    public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
    	UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		
		ProblemaDTO problemaAuxDto = (ProblemaDTO)document.getBean();	
		if (problemaAuxDto.getIdProblema() == null)
			return;

		if (problemaAuxDto.getIdJustificativaProblema() == null) {
			document.alert(UtilI18N.internacionaliza(request,"gerenciaservico.suspensaosolicitacao.validacao.justificanaoinformada"));
			return;
		}

		ProblemaService problemaService = (ProblemaService) ServiceLocator.getInstance().getService(ProblemaService.class, WebUtil.getUsuarioSistema(request));
		ProblemaDTO problemaDto = problemaService.restoreAll(problemaAuxDto.getIdProblema());
		problemaDto.setIdJustificativaProblema(problemaAuxDto.getIdJustificativaProblema());
		problemaDto.setComplementoJustificativa(problemaAuxDto.getComplementoJustificativa());
		problemaService.suspende(usuario, problemaDto);
    	document.executeScript("fechar();");
    	
    }
}
