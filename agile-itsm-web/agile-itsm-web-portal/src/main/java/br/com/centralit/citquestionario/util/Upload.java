/*
 * Created on 15/07/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package br.com.centralit.citquestionario.util;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.UploadItem;

/**
 * @author CentralIT
 */
public class Upload {
	public void doUpload(HttpServletRequest request, Collection colFilesUpload) throws Exception {
		DiskFileUpload fu = new DiskFileUpload(); 
		fu.setSizeMax(-1);
		fu.setSizeThreshold(4096);
		fu.setRepositoryPath("");

		List fileItems = fu.parseRequest(request);
		Iterator i = fileItems.iterator();
		FileItem fi;
		UploadItem upIt;
		File arquivo;
		Iterator itAux = colFilesUpload.iterator();
		while(itAux.hasNext()){
			upIt = (UploadItem)itAux.next();
			while(i.hasNext()){
				fi = (FileItem)i.next();
				if (upIt.getNomeArquivo().toUpperCase().trim().equals(fi.getName().toUpperCase().trim())){
					arquivo = new File(upIt.getPathArquivo() + "\\" + upIt.getNomeArquivo());
					fi.write(arquivo);
				}
			}
		}
	}
	
	public HashMap[] doUploadAll(HttpServletRequest request) throws Exception {
		HashMap[] hshRetorno =  new HashMap[2];
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload fu = new ServletFileUpload(factory); 
		try {
			/**
			 * @author pedro.lino, Danilo.Lisboa
			 * Necessário especificar o encoding, pois quando existe dois ou mais uploads na mesma tela estava vindo com caracteres especiais;
			 * NÃO RETIRAR O TRATAMENTO DE ENCODING.
			 * **/
			fu.setHeaderEncoding("iso-8859-1");
			fu.setSizeMax(-1);
			
			String DIRETORIO_TEMP_UPLOAD_ARQUIVOS = "";
			
			String DISKFILEUPLOAD_REPOSITORYPATH = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.DISKFILEUPLOAD_REPOSITORYPATH,"");
			if(DISKFILEUPLOAD_REPOSITORYPATH == null){
				DISKFILEUPLOAD_REPOSITORYPATH = "";
			}
			
			String str = DISKFILEUPLOAD_REPOSITORYPATH.trim();
			if (str == null){
				str = "";
			}
			if (str == null || str.equalsIgnoreCase("")){
				str = DIRETORIO_TEMP_UPLOAD_ARQUIVOS;
			}
			hshRetorno[0] = new HashMap();  //Retorna os campos de formulário
			hshRetorno[1] = new HashMap();  //Retorna os nomes de arquivos
			
			List fileItems = fu.parseRequest(request);
			Iterator i = fileItems.iterator();
			FileItem fi;
			while(i.hasNext()){
				fi = (FileItem)i.next();
				if (!fi.isFormField()){
					hshRetorno[1].put(CITCorporeUtil.getNameFile(fi.getName()), fi);
					hshRetorno[0].put(fi.getFieldName().toUpperCase(), CITCorporeUtil.getNameFile(fi.getName()));
					request.setAttribute(fi.getFieldName(), CITCorporeUtil.getNameFile(fi.getName()));
				} else {
					hshRetorno[0].put(fi.getFieldName().toUpperCase(), fi.getString());	
					request.setAttribute(fi.getFieldName(), fi.getString());
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return hshRetorno;
	}
}
