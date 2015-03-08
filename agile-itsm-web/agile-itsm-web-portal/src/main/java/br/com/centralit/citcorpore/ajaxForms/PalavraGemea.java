/**
 * 
 */
package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.PalavraGemeaDTO;
import br.com.centralit.citcorpore.negocio.PalavraGemeaService;
import br.com.centralit.lucene.Lucene;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;

/**
 * @author Vadoilo Damasceno
 * 
 */
@SuppressWarnings("rawtypes")
public class PalavraGemea extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

	}

	private void indexaPalavra(PalavraGemeaDTO palavraGemeaDto){
		//Indexando no Lucene
		Lucene lucene = new Lucene();
		lucene.indexarPalavraGemea(palavraGemeaDto);
		lucene = null;
	}
	
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse respose) throws Exception {
		PalavraGemeaDTO palavraGemeaDto = (PalavraGemeaDTO) document.getBean();

		PalavraGemeaService service = (PalavraGemeaService) ServiceLocator.getInstance().getService(PalavraGemeaService.class, null);

		if (palavraGemeaDto.getIdPalavraGemea() == null) {
			if (service.VerificaSeCadastrado(palavraGemeaDto)) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
				return;
			}else if (service.VerificaSePalavraCorrespondenteExiste(palavraGemeaDto)) {
				document.alert(UtilI18N.internacionaliza(request, "palavraGemea.palavraCorrespondenteJaExiste"));
				return;
			}
			palavraGemeaDto = (PalavraGemeaDTO) service.create(palavraGemeaDto);
			this.indexaPalavra(palavraGemeaDto);
			document.alert(UtilI18N.internacionaliza(request, "palavraGemea.cadastrado"));
		} else {
			service.update(palavraGemeaDto);
			this.indexaPalavra(palavraGemeaDto);
			document.alert(UtilI18N.internacionaliza(request, "palavraGemea.atualizado"));
		}

		HTMLForm form = document.getForm("form");
		form.clear();
	}

	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PalavraGemeaDTO palavraGemeaDto = (PalavraGemeaDTO) document.getBean();

		PalavraGemeaService service = (PalavraGemeaService) ServiceLocator.getInstance().getService(PalavraGemeaService.class, null);

		palavraGemeaDto = (PalavraGemeaDTO) service.restore(palavraGemeaDto);

		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(palavraGemeaDto);
	}

	public void excluir(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PalavraGemeaDTO palavraGemeaDto = (PalavraGemeaDTO) document.getBean();

		PalavraGemeaService service = (PalavraGemeaService) ServiceLocator.getInstance().getService(PalavraGemeaService.class, null);

		if (palavraGemeaDto.getIdPalavraGemea() != null) {
			service.delete(palavraGemeaDto);
			Lucene lucene = new Lucene();
			lucene.excluirPalavraGemea(palavraGemeaDto);
			document.alert(UtilI18N.internacionaliza(request, "palavraGemea.excluido"));
		}

		HTMLForm form = document.getForm("form");

		form.clear();

	}

	@Override
	public Class getBeanClass() {
		return PalavraGemeaDTO.class;
	}

}
