/*
 * Created on 15/07/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package br.com.centralit.citcorpore.util;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


 
/**
 * @author CentralIT
 */
public class Upload {
	@SuppressWarnings("deprecation")
	public void doUpload(HttpServletRequest request, Collection colFilesUpload) throws Exception {
//		DiskFileUpload fu = new DiskFileUpload(); 
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload fu = new ServletFileUpload(factory); 
		fu.setSizeMax(-1);
		fu.setFileSizeMax(-1);
//		fu.setSizeThreshold(4096);
//		fu.setRepositoryPath("");
		

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
	
	
	/**
	 * Modificando a forma de anexar, foi mudado para um método não depreciado.
	 * @param request
	 * @return
	 * @throws Exception
	 * @author mario.haysaki
	 */
	public HashMap[] doUploadAll(HttpServletRequest request) throws Exception {
		HashMap[] hshRetorno =  new HashMap[2];
		DiskFileItemFactory  fact = new DiskFileItemFactory(); 
						
		String DISKFILEUPLOAD_REPOSITORYPATH = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.DISKFILEUPLOAD_REPOSITORYPATH,"");
		if(DISKFILEUPLOAD_REPOSITORYPATH == null){
			DISKFILEUPLOAD_REPOSITORYPATH = "";
		}		
		File repositoryPath = new File(DISKFILEUPLOAD_REPOSITORYPATH);	
		fact.setRepository(repositoryPath);
				
		ServletFileUpload fu = new ServletFileUpload(fact);

		try {
			/**
			 * @author pedro.lino, Danilo.Lisboa
			 * Necessário especificar o encoding, pois quando existe dois ou mais uploads na mesma tela estava vindo com caracteres especiais;
			 * NÃO RETIRAR O TRATAMENTO DE ENCODING.
			 * **/
			fu.setHeaderEncoding("iso-8859-1");
			fu.setSizeMax(-1);
			
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
					//System.err.println(fi.getFieldName().toUpperCase() + ": " + fi.getString());
					hshRetorno[0].put(fi.getFieldName().toUpperCase(), fi.getString());	
					request.setAttribute(fi.getFieldName(), fi.getString());
				}
			}			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return hshRetorno;
	}
	
/*	public HashMap[] doUploadAll(HttpServletRequest request) throws Exception {
		HashMap[] hshRetorno =  new HashMap[2];
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload fu = new ServletFileUpload(factory); 
		
		fu.setSizeMax(-1);

		String DIRETORIO_TEMP_UPLOAD_ARQUIVOS = "";

		String str = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.DISKFILEUPLOAD_REPOSITORYPATH,"");
		if (str == null){
			str = "/tmp";
		}
		if (str == null || str.equalsIgnoreCase("")){
			str = DIRETORIO_TEMP_UPLOAD_ARQUIVOS;
		}
		//fu.setRepositoryPath(str);
		
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
		return hshRetorno;
	}*/
}
