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
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

public class UploadDocsGerais extends AjaxFormAction {

	@Override
	public Class getBeanClass() {
		return UploadDTO.class;
	}

	@Override
	public void load(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		br.com.centralit.citcorpore.util.Upload upload = new br.com.centralit.citcorpore.util.Upload();
		Collection colUploadsGED = null;
		
		HashMap hshRetorno[] = new HashMap[2];
		hshRetorno = upload.doUploadAll(request);
		
		
		if(hshRetorno[0]!= null){
			Collection fileItems = hshRetorno[1].values();
			HashMap formItems = hshRetorno[0];	

			String descUploadFile = (String) formItems.get("DESCUPLOADFILE_UPLOADANEXOSDOCSGERAIS");
			String nameUploadFile = (String) formItems.get("FILE_UPLOADANEXOSDOCSGERAIS");
			if(nameUploadFile == null){
			nameUploadFile = UtilStrings.nullToVazio(nameUploadFile);
			}
			
			if(nameUploadFile.trim().equalsIgnoreCase("arquivo")){
				
				nameUploadFile = (String) formItems.get("FILE_UPLOADANEXOS");
				nameUploadFile = UtilStrings.decodeCaracteresEspeciais(nameUploadFile);
				//nameUploadFile = nameUploadFile.replaceAll("#10##13#", "\n"); //codificacao do CITFramework
			}
		
		if (descUploadFile != null){
			descUploadFile = UtilStrings.decodeCaracteresEspeciais(descUploadFile);
			descUploadFile = descUploadFile.replaceAll("#10##13#", "\n"); //codificacao do CITFramework
		}
		
		if (nameUploadFile != null){
			nameUploadFile = CITCorporeUtil.getNameFile(nameUploadFile);
		}
		
		String notaTecnicaUpload = (String) formItems.get("NOTATECNICAUPLOAD");
		if (notaTecnicaUpload == null){
			notaTecnicaUpload = "N";
		}
		
		 colUploadsGED = (Collection)request.getSession(true).getAttribute("colUploadGeraisGED");
		if (colUploadsGED == null){
			colUploadsGED = new ArrayList();
		}
		
		File f = new File(CITCorporeUtil.CAMINHO_REAL_APP + "tempUpload");
		if (!f.exists()){
			f.mkdirs();
		}		
		
		String fileName= "";
		FileItem fi;
		if (!fileItems.isEmpty()){
			File arquivo;
			List filesDel = new ArrayList();
			Iterator it = fileItems.iterator();
			while(it.hasNext()){
				fi = (FileItem)it.next();	
				
				if (fi.getSize() > 0 && (fi.getFieldName() != null && fi.getFieldName().equalsIgnoreCase("file_uploadAnexosDocsGerais"))){
					UploadDTO uploadDTO = new UploadDTO();
					uploadDTO.setDescricao(descUploadFile);
					uploadDTO.setNameFile(nameUploadFile);
					uploadDTO.setSituacao(UtilI18N.internacionaliza(request, "citcorpore.comum.temporario"));
					uploadDTO.setTemporario("S");
					uploadDTO.setNotaTecnicaUpload(notaTecnicaUpload);
					uploadDTO.setPath(CITCorporeUtil.CAMINHO_REAL_APP + "tempUpload/" + uploadDTO.getNameFile());
					
					arquivo = new File(uploadDTO.getPath());
					fi.write(arquivo);
					
					colUploadsGED.add(uploadDTO);
				}
			}
		}
		}
		request.getSession(true).setAttribute("colUploadGeraisGED", colUploadsGED);

		}
}
