package br.com.centralit.citcorpore.ajaxForms;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.UnidadeDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CargaCatalagoServicoService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({ "rawtypes" })
public class CargaCatalagoServico extends AjaxFormAction {

    @Override
    public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

        UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS")
                    + request.getContextPath() + "'");
            return;
        }

        br.com.centralit.citcorpore.util.Upload upload = new br.com.centralit.citcorpore.util.Upload();
        HashMap hshRetorno[] = null;

        try {
            hshRetorno = upload.doUploadAll(request);
        } catch (Exception e) {
        }

        if (hshRetorno != null) {

            Collection fileItems = hshRetorno[1].values();
            String fileName = "";
            FileItem fi;
            if (fileItems != null && !fileItems.isEmpty()) {
                File arquivo = null;
                Iterator it = fileItems.iterator();
                while (it.hasNext()) {
                    fi = (FileItem) it.next();

                    if (fi.getName().length() < 1) {
                        document.alert(UtilI18N.internacionaliza(request, "cargaCatalagoServico.selecionarArquivo"));
                        document.executeScript("JANELA_AGUARDE_MENU.hide();");
                        return;
                    }

                    String extensao = br.com.centralit.citcorpore.util.Util.getFileExtension(fi.getName());
                    if (!extensao.equalsIgnoreCase("csv")) {
                        document.alert(UtilI18N.internacionaliza(request,
                                "cargaCatalagoServico.selecionarArquivoExtensaoCsv"));
                        document.executeScript("JANELA_AGUARDE_MENU.hide();");
                        return;
                    }
                    fileName = fi.getName();
                    arquivo = new File(CITCorporeUtil.CAMINHO_REAL_APP + "tempFiles/" + fileName);
                    fi.write(arquivo);
                    arquivo = null;
                }
                CargaCatalagoServicoService cargaCatalagoServicoService = (CargaCatalagoServicoService) ServiceLocator
                        .getInstance().getService(CargaCatalagoServicoService.class, null);

                Thread.sleep(5000);
                System.out.println("Vai carregar o arquivo de Catalogo de Servicos..."
                        + CITCorporeUtil.CAMINHO_REAL_APP + "tempFiles/" + fileName);
                arquivo = new File(CITCorporeUtil.CAMINHO_REAL_APP + "tempFiles/" + fileName);
                cargaCatalagoServicoService.gerarCarga(arquivo, WebUtil.getIdEmpresa(request));
                document.alert(UtilI18N.internacionaliza(request, "cargaCatalagoServico.cargaGerada"));
            } else {
                document.alert(UtilI18N.internacionaliza(request, "cargaCatalagoServico.naoEPossivelgerarCarga"));
                return;
            }
            document.executeScript("JANELA_AGUARDE_MENU.hide();");
        }
    }

    @Override
    public Class getBeanClass() {
        return UnidadeDTO.class;
    }

}
