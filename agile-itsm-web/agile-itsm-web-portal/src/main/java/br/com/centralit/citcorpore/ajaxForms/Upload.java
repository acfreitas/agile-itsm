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
//import bsh.Console;

public class Upload extends AjaxFormAction {

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
			
			String descUploadFile = (String) formItems.get("DESCUPLOADFILE_UPLOADANEXOS");
			String nameUploadFile = (String) formItems.get("NAMEFILE_UPLOADANEXOS");
			
			/**
			 * Verifica��o no caso de dois ou mais uploads na mesma requisi��o.
			 * 
			 * **/
			if (nameUploadFile == null) {
				nameUploadFile = UtilStrings.nullToVazio(nameUploadFile);
			}
			if(nameUploadFile.trim().equalsIgnoreCase("arquivo")){
				
				nameUploadFile = (String) formItems.get("FILE_UPLOADANEXOS");
				nameUploadFile = UtilStrings.decodeCaracteresEspeciais(nameUploadFile);
				nameUploadFile = nameUploadFile.replaceAll("#10##13#", "\n"); //codificacao do CITFramework
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
		
		
		//Flag inserido para verificar se est� no gerenciamento de servi�o, pois ele � inserido no GED2
		//GED2 Na vers�o do novo layout foi criado um ged2 para tratar os anexos do gerenciamento
		//
		String flagGerenciamento = (String) request.getSession(true).getAttribute("flagGerenciamento");
			if (flagGerenciamento != null && flagGerenciamento.equalsIgnoreCase("S")) {
				colUploadsGED = (Collection) request.getSession(true).getAttribute("colUploadsGED2");
			} else {
				colUploadsGED = (Collection) request.getSession(true).getAttribute("colUploadsGED");
			}
		 
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
				
				if (fi.getSize() > 0 && (fi.getFieldName() != null && fi.getFieldName().equalsIgnoreCase("file_uploadAnexos"))){
					boolean isOk = true;
					UploadDTO uploadDTO = new UploadDTO();
					uploadDTO.setDescricao(descUploadFile);
					uploadDTO.setNameFile(nameUploadFile);
					uploadDTO.setSituacao(UtilI18N.internacionaliza(request, "citcorpore.comum.temporario"));
					uploadDTO.setTemporario("S");
					uploadDTO.setNotaTecnicaUpload(notaTecnicaUpload);
					uploadDTO.setPath(CITCorporeUtil.CAMINHO_REAL_APP + "tempUpload/" + uploadDTO.getNameFile());			
					
					/*
					 * Ajuste para atender a iniciativa 483: Anexo de arquivos ao servi�o.
					 * Foi criada um campo idLinhaPai na classe UploadDTO para vincular o anexo a um determinado servico. 
					 * Ezequiel
					 */
					Integer idServicoUp = (Integer) request.getSession().getAttribute("idServicoUp");
					
					if (idServicoUp != null ){
						uploadDTO.setIdLinhaPai(idServicoUp);
					}
					
					/*FIM*/
					
					/* Alterado por: luiz.borges em 16/12/2013 �s 10:35 hrs
					 * Verifica��o se arquivo j� existe na lista.
					 */					
					for (Iterator i = colUploadsGED.iterator(); i.hasNext();) {
						UploadDTO uploadAux = (UploadDTO) i.next();
						if(uploadAux.getNameFile().equalsIgnoreCase(uploadDTO.getNameFile())){
							isOk = false;
							break;
						}
					}
					if(isOk){
						arquivo = new File(uploadDTO.getPath());
						fi.write(arquivo);
						colUploadsGED.add(uploadDTO);
					}
					//fim altera��o - luiz.borges
				}
				
			}
		}
		}		
		//M�rio J�nior - 28/10/2013 - Adicionado para quando for gerenciamento de servi�o.
		String flagGerenciamento = (String) request.getSession(true).getAttribute("flagGerenciamento");
		if (flagGerenciamento != null && flagGerenciamento.equalsIgnoreCase("S")) {
		request.getSession(true).setAttribute("colUploadsGED2", colUploadsGED);
		} else{
			request.getSession(true).setAttribute("colUploadsGED", colUploadsGED);
		}
	}	    
}
