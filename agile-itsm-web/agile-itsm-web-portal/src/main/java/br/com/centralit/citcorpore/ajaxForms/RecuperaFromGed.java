package br.com.centralit.citcorpore.ajaxForms;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.util.CriptoUtils;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.negocio.ControleGEDService;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilStrings;

public class RecuperaFromGed extends AjaxFormAction {

    @Override
    public Class getBeanClass() {
	return ControleGEDDTO.class;
    }

    @Override
    public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	UsuarioDTO user = WebUtil.getUsuario(request);
	if (user == null) {
	    document.alert("O usuário não está logado! Favor logar no sistema!");
	    return;
	}
	ControleGEDDTO controleGEDDTO = (ControleGEDDTO) document.getBean();
	if (controleGEDDTO.getIdControleGED() == null) {
	    if (controleGEDDTO.getNomeArquivo() != null) {
		try {
		    controleGEDDTO.setIdControleGED(new Integer(controleGEDDTO.getNomeArquivo()));
		} catch (Exception e) {
		}
	    }
	}

	String PRONTUARIO_ABRIR_PDF_ANEXO = (String) request.getAttribute("PRONTUARIO_ABRIR_PDF_ANEXO");
	if (PRONTUARIO_ABRIR_PDF_ANEXO == null) {
	    PRONTUARIO_ABRIR_PDF_ANEXO = "1";
	}

	    String PRONTUARIO_GED_DIRETORIO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedDiretorio, " ");
	    if (PRONTUARIO_GED_DIRETORIO == null || PRONTUARIO_GED_DIRETORIO.trim().equalsIgnoreCase("")) {
		PRONTUARIO_GED_DIRETORIO = "";
	    }

	    if (PRONTUARIO_GED_DIRETORIO.equalsIgnoreCase("")) {
		PRONTUARIO_GED_DIRETORIO = Constantes.getValue("DIRETORIO_GED");
	    }

	    if (PRONTUARIO_GED_DIRETORIO == null || PRONTUARIO_GED_DIRETORIO.equalsIgnoreCase("")) {
		PRONTUARIO_GED_DIRETORIO = "/ged";
	    }

	boolean isGedInterno = false;
	    String PRONTUARIO_GED_INTERNO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedInterno, "S");
	    if (PRONTUARIO_GED_INTERNO == null || PRONTUARIO_GED_INTERNO.trim().equalsIgnoreCase("")) {
		PRONTUARIO_GED_INTERNO = "S";
	    }
	String gedInternoStr = PRONTUARIO_GED_INTERNO;
	if (gedInternoStr == null || "S".equalsIgnoreCase(gedInternoStr)) {
	    isGedInterno = true;
	    gedInternoStr = "S";
	}

	Boolean isGedInternoBancoDados = false;
	String gedInternoBancoDadosStr = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedInternoBD, "N");
	if (UtilStrings.isNotVazio(gedInternoBancoDadosStr) && "S".equalsIgnoreCase(gedInternoBancoDadosStr))
	    isGedInternoBancoDados = true;

	if (isGedInterno) {
	    ControleGEDService controleGEDService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
	    controleGEDDTO = (ControleGEDDTO) controleGEDService.restore(controleGEDDTO);
	}

	FileInputStream fis = null;
	List filesDel = new ArrayList();
	try {
	    if (controleGEDDTO != null) {
		try {
		    File fileDir = new File(System.getProperty("user.dir") + "tempFiles" + "/" + user.getIdEmpresa());
		    if (!fileDir.exists())
			fileDir.mkdirs();

		    CriptoUtils.decryptFile(PRONTUARIO_GED_DIRETORIO + "/" + user.getIdEmpresa() + "/" + controleGEDDTO.getPasta() + "/" + controleGEDDTO.getIdControleGED() + ".ged",
			    System.getProperty("user.dir") + "tempFiles" + "/" + user.getIdEmpresa() + "/" + controleGEDDTO.getIdControleGED() + ".decript", System.getProperties().get("user.dir")
				    + Constantes.getValue("CAMINHO_CHAVE_PRIVADA"));

		    if (PRONTUARIO_ABRIR_PDF_ANEXO.equalsIgnoreCase("2")) {
			if (controleGEDDTO.getExtensaoArquivo().trim().equalsIgnoreCase("PDF")) {
			    CriptoUtils.decryptFile(PRONTUARIO_GED_DIRETORIO + "/" + user.getIdEmpresa() + "/" + controleGEDDTO.getPasta() + "/" + controleGEDDTO.getIdControleGED() + ".ged",
				    System.getProperty("user.dir") + "tempFiles" + "/" + user.getIdEmpresa() + "/" + controleGEDDTO.getIdControleGED() + ".pdf", System.getProperties().get("user.dir")
					    + Constantes.getValue("CAMINHO_CHAVE_PRIVADA"));
			}
		    }
		    fis = new FileInputStream(System.getProperty("user.dir") + "tempFiles" + "/" + user.getIdEmpresa() + "/" + controleGEDDTO.getIdControleGED() + ".decript");
		    if (isGedInternoBancoDados.booleanValue()) {
			filesDel.add(PRONTUARIO_GED_DIRETORIO + "/" + user.getIdEmpresa() + "/" + controleGEDDTO.getPasta() + "/" + controleGEDDTO.getIdControleGED() + ".ged");
		    }
		    filesDel.add(System.getProperty("user.dir") + "tempFiles" + "/" + user.getIdEmpresa() + "/" + controleGEDDTO.getIdControleGED() + ".decript");
		} catch (Exception e) {
		    e.printStackTrace();
		    PrintWriter out = response.getWriter();
		    String msg = "PROBLEMAS AO ABRIR O DOCUMENTO DO GED! " + PRONTUARIO_GED_DIRETORIO + "/" + user.getIdEmpresa() + "/" + controleGEDDTO.getPasta() + "/"
			    + controleGEDDTO.getIdControleGED() + ".ged";
		    msg += "\r\nErro: " + e.getMessage();
		    response.setHeader("Content-Disposition", "attachment; filename=ERRO_DOCUMENTO_GED.txt");
		    response.setContentType("application/x-msdownload");
		    response.setContentLength(msg.getBytes().length);
		    out.write(msg);
		    response.flushBuffer();
		    return;
		}

		if (controleGEDDTO.getExtensaoArquivo() == null) {
		    controleGEDDTO.setExtensaoArquivo("");
		}
		try {
		    if (controleGEDDTO.getExtensaoArquivo().trim().equalsIgnoreCase("XLS")) {
			response.setContentType("application/vnd.ms-excel");
		    } else if (controleGEDDTO.getExtensaoArquivo().trim().equalsIgnoreCase("PPS") || controleGEDDTO.getExtensaoArquivo().trim().equalsIgnoreCase("PPT")) {
			response.setContentType("application/powerpoint");
		    } else if (controleGEDDTO.getExtensaoArquivo().trim().equalsIgnoreCase("DOC") || controleGEDDTO.getExtensaoArquivo().trim().equalsIgnoreCase("DOCX")
			    || controleGEDDTO.getExtensaoArquivo().trim().equalsIgnoreCase("RTF")) {
			response.setContentType("application/msword");
		    } else if (controleGEDDTO.getExtensaoArquivo().trim().equalsIgnoreCase("PDF")) {
			if (!PRONTUARIO_ABRIR_PDF_ANEXO.equalsIgnoreCase("2")) {
			    response.setContentType("application/pdf");
			}
		    } else if (controleGEDDTO.getExtensaoArquivo().trim().equalsIgnoreCase("TXT")) {
			response.setContentType("text/plain");
		    } else if (controleGEDDTO.getExtensaoArquivo().trim().equalsIgnoreCase("ZIP")) {
			response.setContentType("application/zip");
		    } else if (controleGEDDTO.getExtensaoArquivo().trim().equalsIgnoreCase("RTF")) {
			response.setContentType("application/rtf");
		    } else if (controleGEDDTO.getExtensaoArquivo().trim().equalsIgnoreCase("JPG") || controleGEDDTO.getExtensaoArquivo().trim().equalsIgnoreCase("JPEG")
			    || controleGEDDTO.getExtensaoArquivo().trim().equalsIgnoreCase("JPE")) {
			response.setContentType("image/jpeg");
		    } else if (controleGEDDTO.getExtensaoArquivo().trim().equalsIgnoreCase("GIF")) {
			response.setContentType("image/gif");
		    } else if (controleGEDDTO.getExtensaoArquivo().trim().equalsIgnoreCase("PNG")) {
			response.setContentType("image/png");
		    } else if (controleGEDDTO.getExtensaoArquivo().trim().equalsIgnoreCase("HTM") || controleGEDDTO.getExtensaoArquivo().trim().equalsIgnoreCase("HTML")) {
			response.setContentType("text/html");
		    } else if (controleGEDDTO.getExtensaoArquivo().trim().equalsIgnoreCase("")) {
			response.setContentType("application/x-msdownload");
		    }
		} catch (Exception e) {
		}
	    }

	    if (fis != null && fis.available() > 0) {
		if (PRONTUARIO_ABRIR_PDF_ANEXO.equalsIgnoreCase("2") && controleGEDDTO.getExtensaoArquivo().trim().equalsIgnoreCase("PDF")) {
		    String diretorioRelativoReceita = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles/" + user.getIdEmpresa() + "/"
			    + controleGEDDTO.getIdControleGED() + ".pdf";
		    document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url="
			    + diretorioRelativoReceita + "')");
		} else {
		    response.setHeader("Content-Disposition", "attachment; filename=" + controleGEDDTO.getNomeArquivo());
		    ServletOutputStream outputStream = response.getOutputStream();
		    response.setContentLength(fis.available());
		    byte[] byteFile = null;
		    while (fis.available() > 0) {
			if (fis.available() < 1024)
			    byteFile = new byte[fis.available()];
			else
			    byteFile = new byte[1024];
			fis.read(byteFile);
			outputStream.write(byteFile);
			outputStream.flush();
		    }
		    response.flushBuffer();
		}
	    }
	} finally {
	    if (fis != null)
		try {
		    fis.close();
		} catch (IOException ioe) {
		}
	}
	if (filesDel != null && !filesDel.isEmpty()) {
	    File f = null;
	    for (int i = 0; i < filesDel.size(); i++) {
		try {
		    f = new File(filesDel.get(i).toString());
		    if (f.exists())
			f.delete();
		} catch (Exception e) {
		}
	    }
	}
    }

    public void abrePDF(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	ControleGEDDTO controleGEDDTO = (ControleGEDDTO) document.getBean();

	request.setAttribute("PRONTUARIO_ABRIR_PDF_ANEXO", "2");

	load(document, request, response);
    }
}
