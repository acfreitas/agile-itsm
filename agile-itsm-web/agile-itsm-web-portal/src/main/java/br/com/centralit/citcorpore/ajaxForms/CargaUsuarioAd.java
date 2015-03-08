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
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;

/*
 * Desenvolvedor: Euler Data: 28/10/2013 Horário: 09h45min ID Citsmart: 120393 Motivo/Comentário: Eliminar mensagem de
 * erro antes de carregar a tela
 */
@SuppressWarnings("rawtypes")
public class CargaUsuarioAd extends AjaxFormAction {

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
            e.printStackTrace();
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
                        document.alert("Selecione um Arquivo!");
                        document.executeScript("JANELA_AGUARDE_MENU.hide();");
                        return;
                    }

                    String extensao = br.com.centralit.citcorpore.util.Util.getFileExtension(fi.getName());
                    if (!extensao.equalsIgnoreCase("csv")) {
                        document.alert("Arquivo inválido. Importar arquivo.csv");
                        document.executeScript("JANELA_AGUARDE_MENU.hide();");
                        return;
                    }

                    fileName = fi.getName();

                    arquivo = new File(CITCorporeUtil.CAMINHO_REAL_APP + "tempFiles/" + fileName);

                    fi.write(arquivo);

                    arquivo = null;
                }

                UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(
                        UsuarioService.class, null);

                Thread.sleep(5000);

                System.out.println(">>> Carga de Usuários e Empregados Iniciada..." + CITCorporeUtil.CAMINHO_REAL_APP
                        + "tempFiles/" + fileName);

                arquivo = new File(CITCorporeUtil.CAMINHO_REAL_APP + "tempFiles/" + fileName);

                usuarioService.gerarCarga(arquivo);

                document.alert("Carga de Usuários e Empregados realizada com sucesso!");
            }
            document.executeScript("JANELA_AGUARDE_MENU.hide();");
        }
    }

    @Override
    public Class<UnidadeDTO> getBeanClass() {
        return UnidadeDTO.class;
    }

}