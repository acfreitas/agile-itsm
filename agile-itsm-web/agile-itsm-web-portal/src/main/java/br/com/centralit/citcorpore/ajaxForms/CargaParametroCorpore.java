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
import br.com.centralit.citcorpore.negocio.CargaParametroCorporeService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;

@SuppressWarnings("rawtypes")
public class CargaParametroCorpore extends AjaxFormAction {

    @Override
    public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert("Sessão expirada! Favor efetuar logon novamente!");
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
                        document.alert("Favor Selecionar um Arquivo!");
                        document.executeScript("JANELA_AGUARDE_MENU.hide();");
                        return;
                    }

                    String extensao = br.com.centralit.citcorpore.util.Util.getFileExtension(fi.getName());
                    if (!extensao.equalsIgnoreCase("csv")) {
                        document.alert("Favor selecionar uma extensão de aquivo válido: Exemplo: arquivo.csv!");
                        document.executeScript("JANELA_AGUARDE_MENU.hide();");
                        return;
                    }
                    fileName = fi.getName();
                    arquivo = new File(CITCorporeUtil.CAMINHO_REAL_APP + "tempFiles/" + fileName);
                    fi.write(arquivo);
                    arquivo = null;
                }
                CargaParametroCorporeService cargaParametroCorporeService = (CargaParametroCorporeService) ServiceLocator
                        .getInstance().getService(CargaParametroCorporeService.class, null);

                Thread.sleep(5000);
                System.out.println("Vai carregar o arquivo de Parâmetros..." + CITCorporeUtil.CAMINHO_REAL_APP
                        + "tempFiles/" + fileName);
                arquivo = new File(CITCorporeUtil.CAMINHO_REAL_APP + "tempFiles/" + fileName);
                cargaParametroCorporeService.gerarCarga(arquivo, WebUtil.getIdEmpresa(request));
                document.alert("Carga gerada com sucesso!");
            }
            document.executeScript("JANELA_AGUARDE_MENU.hide();");
        }
    }

    @Override
    public Class<UnidadeDTO> getBeanClass() {
        return UnidadeDTO.class;
    }

}