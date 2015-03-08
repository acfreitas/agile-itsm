/**
 *
 */
package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.AnexoIncidenteDTO;
import br.com.centralit.citcorpore.bean.BarraFerramentasIncidentesDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.negocio.AnexoIncidenteService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;

/**
 * @author breno.guimaraes
 *
 */
public class AnexoIncidente extends AjaxFormAction {

    @Override
    public Class getBeanClass() {
	return BarraFerramentasIncidentesDTO.class;
    }

    @Override
    public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	request.getSession(true).setAttribute("colUploadsGED", null);
	document.executeScript("uploadAnexos.clear()");
    }

    public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	Collection<UploadDTO> arquivosUpados = (Collection<UploadDTO>) request.getSession(true).getAttribute("colUploadsGED");

	//Não está criando os anexos aqui (no BD). Esta sendo feito no save da solicitacao de servico. Emauri - 01/07/2012.
	//getBarraFerramentasService().create(arquivosUpados, 1);

	document.executeScript("uploadAnexos.clear()");

	document.executeScript("$('#POPUP_menuAnexos').dialog('close');");
	restore(document, request, response);

    }

    public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	//System.out.println("Restore de anexos.");
	//restaurarAnexos(request);  //Nao eh mais feito assim. Foi modificado o processo de gravacao de anexos. Emauri - 01/07/2012. AGORA UTILIZA GED!
	document.executeScript("$('#POPUP_menuAnexos').dialog('open');");
	document.executeScript("uploadAnexos.refresh()");
    }


    protected void restaurarAnexos(HttpServletRequest request) throws ServiceException, Exception {
	request.getSession(true).setAttribute("colUploadsGED", null);

	Collection<AnexoIncidenteDTO> anexos = this.getBarraFerramentasService().consultarAnexosIncidentes(1);

	Collection colUploadsGED = new ArrayList();
	String nomeDoArquivo = null;
	if (anexos != null && !anexos.isEmpty()) {
	    for (AnexoIncidenteDTO anexo : anexos) {
		//System.out.println("Listando anexo \"" + anexo.getNomeAnexo() + "\" para os cookies.");
		UploadDTO uploadDTO = new UploadDTO();

		uploadDTO.setDescricao(anexo.getDescricao());
		//nem todos os arquivos têm extensão
		if(anexo.getExtensao() == null || anexo.getExtensao().equals("")){
		    nomeDoArquivo = anexo.getNomeAnexo();
		} else {
		    nomeDoArquivo = anexo.getNomeAnexo() + "." + anexo.getExtensao();
		}
		uploadDTO.setNameFile(nomeDoArquivo);
		uploadDTO.setPath(anexo.getLink().replace("\\", "\\\\"));
		uploadDTO.setSituacao("Publicado");
		uploadDTO.setTemporario("N");

		colUploadsGED.add(uploadDTO);
	    }
	}
	//System.out.println("Gravando cookies");
	request.getSession(true).setAttribute("colUploadsGED", colUploadsGED);

    }


    public AnexoIncidenteService getBarraFerramentasService() throws ServiceException, Exception {
	return (AnexoIncidenteService) ServiceLocator.getInstance().getService(AnexoIncidenteService.class, null);
    }
}
