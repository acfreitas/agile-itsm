package br.com.centralit.citcorpore.ajaxForms;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.reflexao.CitAjaxReflexao;
import br.com.centralit.citcorpore.bean.ImagemHistoricoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.bean.ValidacaoCertificadoDigitalDTO;
import br.com.centralit.citcorpore.integracao.ImagemHistoricoDao;
import br.com.centralit.citcorpore.negocio.ImagemHistoricoService;
import br.com.centralit.citcorpore.util.CriptoUtils;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.Util;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.bean.ControleGEDExternoDTO;
import br.com.centralit.citged.negocio.ControleGEDService;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.Reflexao;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilStrings;
import br.com.citframework.util.UtilTratamentoArquivos;
import br.com.citframework.util.cripto.CITAssinadorDigital;

public class ImagemHistorico extends AjaxFormAction {

    private static final String dirSys = System.getProperty("user.dir");

    @Override
    public Class getBeanClass() {
        return ImagemHistoricoDTO.class;
    }

    @Override
    public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            final UsuarioDTO user = WebUtil.getUsuario(request);
            if (user == null) {
                document.alert("O usuário não está logado! Favor logar no sistema!");
                return;
            }
            String PRONTUARIO_TIPO_CAPT_CERT_DIGITAL = "";
            PRONTUARIO_TIPO_CAPT_CERT_DIGITAL = "APPLET";

            String PRONTUARIO_GED_DIRETORIO = ParametroUtil.getValorParametroCitSmartHashMap(
                    Enumerados.ParametroSistema.GedDiretorio, " ");
            if (PRONTUARIO_GED_DIRETORIO == null || PRONTUARIO_GED_DIRETORIO.trim().equalsIgnoreCase("")) {
                PRONTUARIO_GED_DIRETORIO = "";
            }

            if (PRONTUARIO_GED_DIRETORIO.equalsIgnoreCase("")) {
                PRONTUARIO_GED_DIRETORIO = Constantes.getValue("DIRETORIO_GED");
            }

            if (PRONTUARIO_GED_DIRETORIO == null || PRONTUARIO_GED_DIRETORIO.equalsIgnoreCase("")) {
                PRONTUARIO_GED_DIRETORIO = "/ged";
            }

            request.getSession().setAttribute("HASH_CONTEUDO", null);
            request.getSession().setAttribute("TABELA_ASS_DIGITAL", null);
            request.getSession().setAttribute("KEY_ASS_DIGITAL", null);

            byte[] bytesAssinar = null;

            ImagemHistoricoDTO imagemDto = new ImagemHistoricoDTO();
            final br.com.centralit.citcorpore.util.Upload upload = new br.com.centralit.citcorpore.util.Upload();
            final HashMap hshRetorno[] = upload.doUploadAll(request);

            final Collection fileItems = hshRetorno[1].values();
            final HashMap formItems = hshRetorno[0];

            final String idContratoStr = (String) formItems.get("IDCONTRATO");
            int idContrato = 0;
            try {
                idContrato = Integer.parseInt(idContratoStr);
            } catch (final Exception e) {}

            if (idContrato == 0) {
                return;
            }
            String obs = (String) formItems.get("OBSERVACAO");
            if (obs != null) {
                obs = UtilStrings.decodeCaracteresEspeciais(obs);
                obs = obs.replaceAll("#10##13#", "\n");
            }
            String aba = (String) formItems.get("ABA");
            if (aba != null) {
                aba = UtilStrings.decodeCaracteresEspeciais(aba);
                aba = aba.replaceAll("#10##13#", "\n");
            }

            final Collection TEMP_LISTA_CERTIFICADO_DIGITAL = (Collection) request.getSession().getAttribute(
                    "TEMP_LISTA_CERTIFICADO_DIGITAL");

            String fileName = "";
            FileItem fi;
            if (!fileItems.isEmpty()) {
                File arquivo;
                final List filesDel = new ArrayList();
                final ImagemHistoricoService imagemHistoricoService = (ImagemHistoricoService) ServiceLocator
                        .getInstance().getService(ImagemHistoricoService.class, null);
                final Iterator it = fileItems.iterator();
                while (it.hasNext()) {
                    fi = (FileItem) it.next();

                    String PRONTUARIO_GED_INTERNO = ParametroUtil.getValorParametroCitSmartHashMap(
                            Enumerados.ParametroSistema.GedInterno, "S");
                    if (PRONTUARIO_GED_INTERNO == null || PRONTUARIO_GED_INTERNO.trim().equalsIgnoreCase("")) {
                        PRONTUARIO_GED_INTERNO = "S";
                    }
                    String prontuarioGedInternoBancoDados = ParametroUtil.getValorParametroCitSmartHashMap(
                            Enumerados.ParametroSistema.GedInternoBD, "N");
                    if (!UtilStrings.isNotVazio(prontuarioGedInternoBancoDados)) {
                        prontuarioGedInternoBancoDados = "N";
                    }
                    final ControleGEDService controleGEDService = (ControleGEDService) ServiceLocator.getInstance()
                            .getService(ControleGEDService.class, null);
                    String pasta = "";
                    if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S")) {
                        pasta = controleGEDService.getProximaPastaArmazenar();
                        File fileDir = new File(PRONTUARIO_GED_DIRETORIO);
                        if (!fileDir.exists()) {
                            fileDir.mkdirs();
                        }
                        fileDir = new File(PRONTUARIO_GED_DIRETORIO + "/" + user.getIdEmpresa());
                        if (!fileDir.exists()) {
                            fileDir.mkdirs();
                        }
                        fileDir = new File(PRONTUARIO_GED_DIRETORIO + "/" + user.getIdEmpresa() + "/" + pasta);
                        if (!fileDir.exists()) {
                            fileDir.mkdirs();
                        }
                    }

                    ControleGEDDTO controleGEDDTO = new ControleGEDDTO();
                    controleGEDDTO.setIdTabela(ControleGEDDTO.TABELA_IMAGEMHISTORICO);
                    controleGEDDTO.setId(new Integer(0));
                    controleGEDDTO.setDataHora(UtilDatas.getDataAtual());
                    controleGEDDTO.setDescricaoArquivo(fi.getName());
                    controleGEDDTO.setExtensaoArquivo(Util.getFileExtension(fi.getName()));
                    controleGEDDTO.setPasta(pasta);
                    controleGEDDTO.setNomeArquivo(fi.getName());

                    if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S")
                            && "S".equalsIgnoreCase(prontuarioGedInternoBancoDados)) {
                        final Date now = new Date();
                        final String pathFonte = PRONTUARIO_GED_DIRETORIO + "/" + user.getIdEmpresa() + "/" + pasta
                                + "/" + now.getTime() + "." + Util.getFileExtension(fi.getName()), pathDestino = PRONTUARIO_GED_DIRETORIO
                                + "/" + user.getIdEmpresa() + "/" + pasta + "/" + now.getTime() + ".ged", pathChave = System
                                .getProperties().get("user.dir") + Constantes.getValue("CAMINHO_CHAVE_PUBLICA"), pathAssinatura = PRONTUARIO_GED_DIRETORIO
                                + "/" + user.getIdEmpresa() + "/" + pasta + "/" + now.getTime() + ".signed";
                        arquivo = new File(pathFonte);
                        filesDel.add(pathFonte);
                        fi.write(arquivo);
                        try {
                            bytesAssinar = UtilTratamentoArquivos.getBytesFromFile(arquivo);
                        } catch (final Exception e) {
                            e.printStackTrace();
                        }
                        int qtdAssinatura = 0;
                        CriptoUtils.encryptFile(pathFonte, pathDestino, pathChave);
                        controleGEDDTO.setPathArquivo(pathDestino);
                        filesDel.add(pathDestino);
                        if (TEMP_LISTA_CERTIFICADO_DIGITAL != null && TEMP_LISTA_CERTIFICADO_DIGITAL.size() > 0) {
                            final CITAssinadorDigital d = new CITAssinadorDigital();
                            d.inicializar();
                            for (final Iterator itCert = TEMP_LISTA_CERTIFICADO_DIGITAL.iterator(); itCert.hasNext(); qtdAssinatura++) {
                                final ValidacaoCertificadoDigitalDTO validacaoCertificadoDigitalDTO = (ValidacaoCertificadoDigitalDTO) itCert
                                        .next();

                                if (PRONTUARIO_TIPO_CAPT_CERT_DIGITAL.equalsIgnoreCase("APPLET")) {
                                    d.assinar(validacaoCertificadoDigitalDTO.getInfoCertificadoDigital().getCpf(),
                                            pathFonte, pathAssinatura + qtdAssinatura,
                                            validacaoCertificadoDigitalDTO.getCert(), false);
                                } else {
                                    final InputStream inStream = new FileInputStream(
                                            validacaoCertificadoDigitalDTO.getCaminhoCompleto());
                                    final CertificateFactory cf = CertificateFactory.getInstance("X.509", "BC");
                                    final X509Certificate cert = (X509Certificate) cf.generateCertificate(inStream);
                                    d.assinar(validacaoCertificadoDigitalDTO.getInfoCertificadoDigital().getCpf(),
                                            pathFonte, pathAssinatura + qtdAssinatura, cert, false);
                                }
                                controleGEDDTO.getPathsAssinaturas().add(pathAssinatura + qtdAssinatura);
                                filesDel.add(pathAssinatura + qtdAssinatura);
                            }
                        }
                    }
                    controleGEDDTO = controleGEDService.create(controleGEDDTO);
                    if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S")
                            && !"S".equalsIgnoreCase(prontuarioGedInternoBancoDados)) { // Se utiliza GED
                        // interno
                        if (controleGEDDTO != null) {
                            arquivo = new File(PRONTUARIO_GED_DIRETORIO + "/" + user.getIdEmpresa() + "/" + pasta + "/"
                                    + controleGEDDTO.getIdControleGED() + "." + Util.getFileExtension(fi.getName()));
                            fi.write(arquivo);
                            try {
                                bytesAssinar = UtilTratamentoArquivos.getBytesFromFile(arquivo);
                            } catch (final Exception e) {
                                e.printStackTrace();
                            }
                            CriptoUtils.encryptFile(
                                    PRONTUARIO_GED_DIRETORIO + "/" + user.getIdEmpresa() + "/" + pasta + "/"
                                            + controleGEDDTO.getIdControleGED() + "."
                                            + Util.getFileExtension(fi.getName()),
                                    PRONTUARIO_GED_DIRETORIO + "/" + user.getIdEmpresa() + "/" + pasta + "/"
                                            + controleGEDDTO.getIdControleGED() + ".ged",
                                    System.getProperties().get("user.dir")
                                            + Constantes.getValue("CAMINHO_CHAVE_PUBLICA"));

                            if (TEMP_LISTA_CERTIFICADO_DIGITAL != null && TEMP_LISTA_CERTIFICADO_DIGITAL.size() > 0) {
                                int ordem = 1;
                                final CITAssinadorDigital d = new CITAssinadorDigital();
                                d.inicializar();
                                for (final Iterator itCert = TEMP_LISTA_CERTIFICADO_DIGITAL.iterator(); itCert
                                        .hasNext();) {
                                    final ValidacaoCertificadoDigitalDTO validacaoCertificadoDigitalDTO = (ValidacaoCertificadoDigitalDTO) itCert
                                            .next();

                                    if (PRONTUARIO_TIPO_CAPT_CERT_DIGITAL.equalsIgnoreCase("APPLET")) {
                                        d.assinar(
                                                validacaoCertificadoDigitalDTO.getInfoCertificadoDigital().getCpf(),
                                                PRONTUARIO_GED_DIRETORIO + "/" + user.getIdEmpresa() + "/" + pasta
                                                        + "/" + controleGEDDTO.getIdControleGED() + "."
                                                        + Util.getFileExtension(fi.getName()), PRONTUARIO_GED_DIRETORIO
                                                        + "/" + user.getIdEmpresa() + "/" + pasta + "/"
                                                        + controleGEDDTO.getIdControleGED() + ".signed" + ordem,
                                                validacaoCertificadoDigitalDTO.getCert(), false);
                                    } else {
                                        final InputStream inStream = new FileInputStream(
                                                validacaoCertificadoDigitalDTO.getCaminhoCompleto());
                                        final CertificateFactory cf = CertificateFactory.getInstance("X.509", "BC");
                                        final X509Certificate cert = (X509Certificate) cf.generateCertificate(inStream);
                                        d.assinar(
                                                validacaoCertificadoDigitalDTO.getInfoCertificadoDigital().getCpf(),
                                                PRONTUARIO_GED_DIRETORIO + "/" + user.getIdEmpresa() + "/" + pasta
                                                        + "/" + controleGEDDTO.getIdControleGED() + "."
                                                        + Util.getFileExtension(fi.getName()), PRONTUARIO_GED_DIRETORIO
                                                        + "/" + user.getIdEmpresa() + "/" + pasta + "/"
                                                        + controleGEDDTO.getIdControleGED() + ".signed" + ordem, cert,
                                                false);
                                    }
                                    ordem++;
                                }
                            }

                            arquivo.delete();
                        }
                    } else if (!PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S")) { // Se utiliza GED externo
                        System.out.println("CITSMART -> ImagemHistorico -> GED Externo");
                        final String PRONTUARIO_CLASSE_GED_EXTERNO = ParametroUtil.getValorParametroCitSmartHashMap(
                                Enumerados.ParametroSistema.GedExternoClasse, "");
                        final Class classe = Class.forName(PRONTUARIO_CLASSE_GED_EXTERNO);
                        System.out.println("CITSMART -> ImagemHistorico -> GED Externo -> "
                                + PRONTUARIO_CLASSE_GED_EXTERNO);
                        final Object objeto = classe.newInstance();
                        final Method mtd = CitAjaxReflexao.findMethod("create", objeto);

                        final ControleGEDExternoDTO controleGedExternoDto = new ControleGEDExternoDTO();
                        Reflexao.copyPropertyValues(controleGEDDTO, controleGedExternoDto);

                        final HashMap hshInfo = new HashMap();

                        fileName = "ANEXO_" + br.com.citframework.util.Util.geraSenhaAleatoria(6) + "_"
                                + Util.getNameFile(fi.getName());
                        final String fileNameGed = "ANEXO_" + br.com.citframework.util.Util.geraSenhaAleatoria(6) + "_"
                                + Util.getNameFile(fi.getName());
                        File fileDir = new File(dirSys + Constantes.getValue("CAMINHO_ARQUIVOS_IMAGENS_EMPRESAS") + "/"
                                + user.getIdEmpresa());
                        if (!fileDir.exists()) {
                            fileDir.mkdirs();
                        }
                        fileDir = new File(dirSys + Constantes.getValue("CAMINHO_ARQUIVOS_IMAGENS_EMPRESAS") + "/"
                                + user.getIdEmpresa());
                        if (!fileDir.exists()) {
                            fileDir.mkdirs();
                        }
                        System.out.println("CITSMART -> ImagemHistorico -> Diretorio -> " + dirSys
                                + Constantes.getValue("CAMINHO_ARQUIVOS_IMAGENS_EMPRESAS") + "/" + user.getIdEmpresa()
                                + "/" + fileName);
                        arquivo = new File(dirSys + Constantes.getValue("CAMINHO_ARQUIVOS_IMAGENS_EMPRESAS") + "/"
                                + user.getIdEmpresa() + "/" + fileName);
                        if (!arquivo.exists()) {
                            arquivo.createNewFile();
                        }
                        fi.write(arquivo);
                        try {
                            bytesAssinar = UtilTratamentoArquivos.getBytesFromFile(arquivo);
                        } catch (final Exception e) {
                            e.printStackTrace();
                        }

                        CriptoUtils.encryptFile(
                                dirSys + Constantes.getValue("CAMINHO_ARQUIVOS_IMAGENS_EMPRESAS") + "/"
                                        + user.getIdEmpresa() + "/" + fileName,
                                dirSys + Constantes.getValue("CAMINHO_ARQUIVOS_IMAGENS_EMPRESAS") + "/"
                                        + user.getIdEmpresa() + "/" + fileNameGed,
                                System.getProperties().get("user.dir") + Constantes.getValue("CAMINHO_CHAVE_PUBLICA"));

                        final File fAux = new File(dirSys + Constantes.getValue("CAMINHO_ARQUIVOS_IMAGENS_EMPRESAS")
                                + "/" + user.getIdEmpresa() + "/" + fileName);
                        fAux.delete();

                        final File f = new File(dirSys + Constantes.getValue("CAMINHO_ARQUIVOS_IMAGENS_EMPRESAS") + "/"
                                + user.getIdEmpresa() + "/" + fileNameGed);
                        controleGedExternoDto.setConteudoDocumento(UtilTratamentoArquivos.getBytesFromFile(f));
                        controleGedExternoDto.setCaminhoCompletoDocumento(f.getAbsolutePath());
                        byte[] conteudoAssinaturaDigital = null;
                        if (TEMP_LISTA_CERTIFICADO_DIGITAL != null && TEMP_LISTA_CERTIFICADO_DIGITAL.size() > 0) {
                            int ordem = 1;
                            final CITAssinadorDigital d = new CITAssinadorDigital();
                            d.inicializar();
                            for (final Iterator itCert = TEMP_LISTA_CERTIFICADO_DIGITAL.iterator(); itCert.hasNext();) {
                                final ValidacaoCertificadoDigitalDTO validacaoCertificadoDigitalDTO = (ValidacaoCertificadoDigitalDTO) itCert
                                        .next();

                                if (PRONTUARIO_TIPO_CAPT_CERT_DIGITAL.equalsIgnoreCase("APPLET")) {
                                    conteudoAssinaturaDigital = d.assinar(validacaoCertificadoDigitalDTO
                                            .getInfoCertificadoDigital().getCpf(),
                                            dirSys + Constantes.getValue("CAMINHO_ARQUIVOS_IMAGENS_EMPRESAS") + "/"
                                                    + user.getIdEmpresa() + "/" + idContrato + "/" + fileNameGed,
                                            validacaoCertificadoDigitalDTO.getCert(), false);
                                } else {
                                    final InputStream inStream = new FileInputStream(
                                            validacaoCertificadoDigitalDTO.getCaminhoCompleto());
                                    final CertificateFactory cf = CertificateFactory.getInstance("X.509", "BC");
                                    final X509Certificate cert = (X509Certificate) cf.generateCertificate(inStream);
                                    conteudoAssinaturaDigital = d.assinar(validacaoCertificadoDigitalDTO
                                            .getInfoCertificadoDigital().getCpf(),
                                            dirSys + Constantes.getValue("CAMINHO_ARQUIVOS_IMAGENS_EMPRESAS") + "/"
                                                    + user.getIdEmpresa() + "/" + idContrato + "/" + fileNameGed, cert,
                                            false);
                                }
                                ordem++;
                                break; // Faz so a primeira.
                            }
                        }
                        controleGedExternoDto.setConteudoAssinaturaDigital(conteudoAssinaturaDigital);

                        mtd.invoke(objeto, new Object[] {controleGedExternoDto, hshInfo});
                        // Se utiliza GED externo
                        imagemDto.setNomeArquivo(controleGedExternoDto.getNumeroRetorno());

                        f.delete();
                    }

                    try {
                        imagemDto.setIdEmpresa(user.getIdEmpresa());
                        imagemDto.setIdProfissional(user.getIdEmpregado());
                        imagemDto.setData(UtilDatas.getDataAtual());
                        // Se utiliza GED interno
                        if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S")) {
                            imagemDto.setNomeArquivo(controleGEDDTO.getIdControleGED().toString());
                        }
                        imagemDto.setIdContrato(new Integer(idContrato));
                        imagemDto.setObservacao(obs);
                        imagemDto.setAba(aba);

                        imagemDto = imagemHistoricoService.create(imagemDto);

                        String algoritmo = br.com.citframework.util.Constantes.getValue("ALGORITMO_CRIPTOGRAFIA_SENHA");
                        if (algoritmo == null || !algoritmo.trim().equalsIgnoreCase("")) {
                            algoritmo = "SHA-1";
                        }
                        final String hash = CriptoUtils.generateHash(bytesAssinar, algoritmo);
                        request.getSession().setAttribute("HASH_CONTEUDO", hash);
                        request.getSession().setAttribute("TABELA_ASS_DIGITAL",
                                ImagemHistoricoDao.getTableNameAssDigital().toUpperCase());
                        request.getSession().setAttribute("KEY_ASS_DIGITAL", imagemDto.getKey());

                        // imagemDto.setNomeArquivo(fileName);
                        // imagemHistoricoService.update(imagemDto);
                    } catch (final Exception e) {
                        e.printStackTrace();
                        throw new Exception(e);
                    }
                }
                if (filesDel != null && !filesDel.isEmpty()) {
                    File f = null;
                    for (int i = 0; i < filesDel.size(); i++) {
                        try {
                            f = new File(filesDel.get(i).toString());
                            if (f.exists()) {
                                f.delete();
                            }
                        } catch (final Exception e) {}
                    }
                }
            }

            request.getSession(true).setAttribute("msg", null);

            request.setAttribute("ERRO", null);
            /*
             * String msg = "MENSAGEM CITSMART:\r\nARQUIVO TRANSFERIDO COM SUCESSO\r\n\r\nClique em fechar!";
             * ServletOutputStream outputStream = response.getOutputStream(); response.setContentType("text/plain");
             * response.setHeader("Content-Disposition", "attachment; filename=ARQUIVO_TRANSFERIDO_COM_SUCESSO.txt");
             * response.setContentLength(msg.getBytes().length); outputStream.write(msg.getBytes()); outputStream.flush();
             * outputStream.close();
             */
        } catch (final Exception e) {
            e.printStackTrace();
            request.setAttribute("ERRO", "OCORREU ERRO AO TRANSFERIR O ARQUIVO: " + e.getMessage());
            /*
             * request.getSession(true).setAttribute("msg", null); String msg =
             * "MENSAGEM CITSMART:\r\nOCORREU ERRO AO TRANSFERIR O ARQUIVO\r\n\r\nClique em fechar!\r\n\r\n"; msg += e.getMessage();
             * if(e.getStackTrace() != null && e.getStackTrace().length > 0) for(int i = 0; i < e.getStackTrace().length; i++) msg +=
             * e.getStackTrace()[i] + "\r\n"; ServletOutputStream outputStream = response.getOutputStream();
             * response.setContentType("text/plain");
             * response.setHeader("Content-Disposition", "attachment; filename=ERRO_TRANSFERENCIA_ARQUIVO.txt");
             * response.setContentLength(msg.getBytes().length); outputStream.write(msg.getBytes()); outputStream.flush();
             * outputStream.close(); throw
             * new Exception(e);
             */
        }
    }

}
