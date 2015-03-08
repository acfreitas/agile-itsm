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
import br.com.centralit.citcorpore.bean.AnexoMudancaDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.negocio.AnexoMudancaService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;

/**
 * @author breno.guimaraes
 *
 */
public class AnexoMudanca extends AjaxFormAction {

    @Override
    public Class getBeanClass() {
        return AnexoMudancaDTO.class;
    }

    @Override
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        request.getSession(true).setAttribute("colUploadsGED", null);
        document.executeScript("uploadAnexos.clear()");
    }

    public void save(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        request.getSession(true).getAttribute("colUploadsGED");

        // Não está criando os anexos aqui (no BD). Esta sendo feito no save da solicitacao de servico. Emauri - 01/07/2012.
        // getBarraFerramentasService().create(arquivosUpados, 1);

        document.executeScript("uploadAnexos.clear()");

        document.executeScript("$('#POPUP_menuAnexos').dialog('close');");
        this.restore(document, request, response);

    }

    public void restore(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        // System.out.println("Restore de anexos.");
        // restaurarAnexos(request); //Nao eh mais feito assim. Foi modificado o processo de gravacao de anexos. Emauri - 01/07/2012. AGORA UTILIZA GED!
        document.executeScript("$('#POPUP_menuAnexos').dialog('open');");
        document.executeScript("uploadAnexos.refresh()");
    }

    protected void restaurarAnexos(final HttpServletRequest request) throws ServiceException, Exception {
        request.getSession(true).setAttribute("colUploadsGED", null);

        final Collection<AnexoMudancaDTO> anexos = this.getBarraFerramentasService().consultarAnexosMudanca(1);

        final Collection colUploadsGED = new ArrayList<>();
        String nomeDoArquivo = null;
        if (anexos != null && !anexos.isEmpty()) {
            for (final AnexoMudancaDTO anexo : anexos) {
                // System.out.println("Listando anexo \"" + anexo.getNomeAnexo() + "\" para os cookies.");
                final UploadDTO uploadDTO = new UploadDTO();

                uploadDTO.setDescricao(anexo.getDescricao());
                // nem todos os arquivos têm extensão
                if (anexo.getExtensao() == null || anexo.getExtensao().equals("")) {
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
        // System.out.println("Gravando cookies");
        request.getSession(true).setAttribute("colUploadsGED", colUploadsGED);

    }

    public AnexoMudancaService getBarraFerramentasService() throws ServiceException, Exception {
        return (AnexoMudancaService) ServiceLocator.getInstance().getService(AnexoMudancaService.class, null);
    }

}
