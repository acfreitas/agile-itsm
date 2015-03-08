package br.com.centralit.citcorpore.ajaxForms;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLElement;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.Upload;
import br.com.centralit.citcorpore.util.Util;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citquestionario.bean.ArquivoMultimidiaDTO;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

public class UploadArquivoMultimidia extends AjaxFormAction {

	@SuppressWarnings("rawtypes")
	public Class getBeanClass() {
		return ArquivoMultimidiaDTO.class;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO user = WebUtil.getUsuario(request);
		if (user == null){
			document.alert("O usuário não está logado! Favor logar no sistema!");
			return;
		}
		Upload upload = new Upload();
		HashMap hshRetorno[] = upload.doUploadAll(request);

		Collection fileItems = hshRetorno[1].values();
		HashMap formItems = hshRetorno[0];

		String obs = (String) formItems.get("OBSERVACAO");
		String idQuestaoQuestStr = (String) formItems.get("IDQUESTAOQUEST");

		Integer idQuestaoQuest = new Integer(Integer.parseInt(idQuestaoQuestStr));

		String fileName= "";
		FileItem fi;
		if (!fileItems.isEmpty()){
			Iterator it = fileItems.iterator();
			File arquivo;
			while(it.hasNext()){
				fi = (FileItem)it.next();

				File fileDir = new File(CITCorporeUtil.CAMINHO_REAL_APP + "/tempUpload");
				if (!fileDir.exists()){
				    fileDir.mkdirs();
				}
				fileDir = new File(CITCorporeUtil.CAMINHO_REAL_APP + "/tempUpload/" + user.getIdEmpresa());
				if (!fileDir.exists()){
					fileDir.mkdirs();
				}
				fileDir = new File(CITCorporeUtil.CAMINHO_REAL_APP + "/tempUpload/" + user.getIdEmpresa() + "/" + user.getIdUsuario());
				if (!fileDir.exists()){
				    fileDir.mkdirs();
				}

				fileName = "ANEXO_" + br.com.citframework.util.Util.geraSenhaAleatoria(6) + "_" + Util.getNameFile(fi.getName());
				arquivo = new File(CITCorporeUtil.CAMINHO_REAL_APP + "/tempUpload/" + user.getIdEmpresa() + "/" + user.getIdUsuario() + "/" + fileName);

				String url = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempUpload/" + user.getIdEmpresa() + "/" + user.getIdUsuario() + "/" + fileName;
				try{
					fi.write(arquivo);

					ArquivoMultimidiaDTO arquivoMultimidia = new ArquivoMultimidiaDTO();
					arquivoMultimidia.setCaminhoArquivo(CITCorporeUtil.CAMINHO_REAL_APP + "/tempUpload/" + user.getIdEmpresa() + "/" + user.getIdUsuario() + "/" + fileName);
					arquivoMultimidia.setNomeArquivo(Util.getNameFile(fi.getName()));
					arquivoMultimidia.setObservacao(obs);
					arquivoMultimidia.setUrlArquivo(url);
					arquivoMultimidia.setIdQuestaoQuest(idQuestaoQuest);

					List lst = (List) request.getSession(true).getAttribute("TEMP_LISTA_ARQ_MULTIMIDIA");
					if (lst == null){
						lst = new ArrayList();
					}
					lst.add(arquivoMultimidia);

					request.getSession(true).setAttribute("TEMP_LISTA_ARQ_MULTIMIDIA", lst);
				} catch (Exception e){
					e.printStackTrace();
					throw new Exception(e);
				}
			}
		}
	}

	public void listarUploads(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArquivoMultimidiaDTO arquivoMultiDto = (ArquivoMultimidiaDTO) document.getBean();

		List lst = (List) request.getSession(true).getAttribute("TEMP_LISTA_ARQ_MULTIMIDIA");
		if (lst == null){
			lst = new ArrayList();
		}

		String strTable = "<table width=\"100%\">";

		strTable += "<tr>";

			strTable += "<td>";
				strTable += "&nbsp;";
			strTable += "</td>";
			strTable += "<td>";
				strTable += "&nbsp;";
			strTable += "</td>";
			strTable += "<td>";
				strTable += "&nbsp;";
			strTable += "</td>";
			strTable += "<td>";
				strTable += "&nbsp;";
			strTable += "</td>";
			strTable += "<td>";
				strTable += "&nbsp;";
			strTable += "</td>";
			strTable += "<td>";
				strTable += "&nbsp;";
			strTable += "</td>";

		strTable += "</tr>";

		int iContador = 0;
		boolean booleanBorda = false;
		String caminhoAnexo = "";
		for(Iterator it = lst.iterator(); it.hasNext();){
			ArquivoMultimidiaDTO arquivoMultimidia = (ArquivoMultimidiaDTO)it.next();

			if (Util.getFileExtension(arquivoMultimidia.getNomeArquivo()).equalsIgnoreCase("JPG") ||
					Util.getFileExtension(arquivoMultimidia.getNomeArquivo()).equalsIgnoreCase("gif") ||
					Util.getFileExtension(arquivoMultimidia.getNomeArquivo()).equalsIgnoreCase("png") ||
					Util.getFileExtension(arquivoMultimidia.getNomeArquivo()).equalsIgnoreCase("BMP")){
				caminhoAnexo = arquivoMultimidia.getUrlArquivo();
			}else{
				booleanBorda = true;
				if (Util.getFileExtension(arquivoMultimidia.getNomeArquivo()).equalsIgnoreCase("MPG") ||
						Util.getFileExtension(arquivoMultimidia.getNomeArquivo()).equalsIgnoreCase("MPEG") ||
						Util.getFileExtension(arquivoMultimidia.getNomeArquivo()).equalsIgnoreCase("WAV") ||
						Util.getFileExtension(arquivoMultimidia.getNomeArquivo()).equalsIgnoreCase("MP3") ||
						Util.getFileExtension(arquivoMultimidia.getNomeArquivo()).equalsIgnoreCase("FLV") ||
						Util.getFileExtension(arquivoMultimidia.getNomeArquivo()).equalsIgnoreCase("WMV") ||
						Util.getFileExtension(arquivoMultimidia.getNomeArquivo()).equalsIgnoreCase("AVI")){
					caminhoAnexo = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/produtos/citsaude/imagens/multimidia.jpg";
				}else{
					caminhoAnexo = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/produtos/citsaude/imagens/arquivo.jpg";
				}
			}
			if (iContador == 0){
				strTable += "<tr>";
			}

			String borda = "";
			if (booleanBorda){
				borda = " border=\"1\" ";
			}
			String strObs = "Não há";
			if (arquivoMultimidia.getObservacao() != null){
				strObs = arquivoMultimidia.getObservacao();
			}

			strTable += "<td>";
				strTable += "<a href=\"" + arquivoMultimidia.getUrlArquivo() + "\" target=_blank><img style=\"cursor:hand\" src=\""  + caminhoAnexo + "\" width=\"150px\" height=\"150px\" " + borda + " title=\"header=[Observações:] body=[" + strObs + "]\"/></a> <br> ";
			strTable += "</td>";
			strTable += "<td>";
				strTable += "&nbsp;";
			strTable += "</td>";

			iContador++;

			if (iContador >= 3){
				iContador = 0;
			}
		}
		strTable += "</table>";

		HTMLElement div = document.getElementById(arquivoMultiDto.getIdDIV());
		div.setInnerHTML(strTable);
	}

	public void verificarParametroAnexos(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{

		String DISKFILEUPLOAD_REPOSITORYPATH = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.DISKFILEUPLOAD_REPOSITORYPATH, "");
		if(DISKFILEUPLOAD_REPOSITORYPATH == null){
			DISKFILEUPLOAD_REPOSITORYPATH = "";
		}
		if(DISKFILEUPLOAD_REPOSITORYPATH.equals("")){
			throw new LogicException(UtilI18N.internacionaliza(request,"citcorpore.comum.anexosUploadSemParametro"));
		}
		File pasta = new File(DISKFILEUPLOAD_REPOSITORYPATH);
		if(!pasta.exists()){
			throw new LogicException(UtilI18N.internacionaliza(request,"citcorpore.comum.pastaIndicadaNaoExiste"));
		}
		else{
			document.executeScript("adicionarImagemLiberada();");
		}
	}



}
