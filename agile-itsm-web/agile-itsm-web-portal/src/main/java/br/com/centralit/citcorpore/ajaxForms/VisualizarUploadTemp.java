package br.com.centralit.citcorpore.ajaxForms;

import java.io.File;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.util.UtilStrings;
import br.com.citframework.util.UtilTratamentoArquivos;

public class VisualizarUploadTemp extends AjaxFormAction {

	@Override
	public Class getBeanClass() {
		return UploadDTO.class;
	}

	@Override
	public void load(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	    UploadDTO uploadDto = (UploadDTO) document.getBean();
	    if (uploadDto.getPath() != null){
		if (uploadDto.getPath().startsWith("ID=")){
		    String strId = UtilStrings.apenasNumeros(uploadDto.getPath());
		    Integer idControleGed = null;
		    idControleGed = new Integer(strId);
		    if (idControleGed != null){
				String pathRecuperado = getFromGed(idControleGed);
				
				byte[] bytes = UtilTratamentoArquivos.getBytesFromFile(new File(pathRecuperado));
				
				if (bytes != null) {
					ServletOutputStream outputStream = response.getOutputStream();
					response.setHeader("Content-Disposition", "attachment; filename=GED.CITSMART." + CITCorporeUtil.getNameFile(pathRecuperado));					
					response.setContentLength(bytes.length);
					outputStream.write(bytes);
					outputStream.flush();
					outputStream.close();
				}	
			
		    }
		}else{
		    	String pathRecuperado = uploadDto.getPath();
			
			byte[] bytes = UtilTratamentoArquivos.getBytesFromFile(new File(pathRecuperado));
			ServletOutputStream outputStream = response.getOutputStream();
			response.setHeader("Content-Disposition", "attachment; filename=TEMPORARIO." + CITCorporeUtil.getNameFile(pathRecuperado));
			if (bytes!= null){
			    response.setContentLength(bytes.length);
			    outputStream.write(bytes);
			}
			outputStream.flush();
			outputStream.close();
		}
	    }
	}
}
