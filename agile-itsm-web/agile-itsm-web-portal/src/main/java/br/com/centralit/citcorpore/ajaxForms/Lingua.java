package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.LinguaDTO;
import br.com.centralit.citcorpore.negocio.LinguaService;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;
@SuppressWarnings("rawtypes")
public class Lingua extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		
	}

	
	@Override
	public Class getBeanClass() {
		return LinguaDTO.class;
	}
	
	
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		LinguaDTO linguaDto = (LinguaDTO) document.getBean();
		
		LinguaService linguaService = (LinguaService) ServiceLocator.getInstance().getService(LinguaService.class, null);
		
		if(linguaDto.getIdLingua()==null || linguaDto.getIdLingua() == 0){
			if(linguaService.consultarLinguaAtivas(linguaDto)){
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
				return;
			}
			linguaDto.setDataInicio(UtilDatas.getDataAtual());
			linguaService.create(linguaDto);
			document.alert(UtilI18N.internacionaliza(request,"MSG05"));
			
		}else{
			if(linguaService.consultarLinguaAtivas(linguaDto)){
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
				return;
			}
			linguaService.update(linguaDto);
			document.alert(UtilI18N.internacionaliza(request,"MSG06"));
		}
		HTMLForm form = document.getForm("form");
		form.clear();
		
	}
	
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		LinguaDTO linguaDto = (LinguaDTO) document.getBean();
		
		LinguaService linguaService = (LinguaService) ServiceLocator.getInstance().getService(LinguaService.class, null);
		
		linguaDto = (LinguaDTO) linguaService.restore(linguaDto);
		
		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(linguaDto);
	}
	
	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		LinguaDTO linguaDto = (LinguaDTO) document.getBean();
		
		LinguaService linguaService = (LinguaService) ServiceLocator.getInstance().getService(LinguaService.class, null);
		
		if (linguaDto.getIdLingua().intValue() > 0 || linguaDto.getIdLingua()!=null) {
			linguaDto.setDataFim(UtilDatas.getDataAtual());
			linguaService.update(linguaDto);
			document.alert(UtilI18N.internacionaliza(request,"MSG07"));
		}
		
		HTMLForm form = document.getForm("form");
		form.clear();
		
	}

}
