package br.com.centralit.citcorpore.ajaxForms;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.ConexaoBIDTO;
import br.com.centralit.citcorpore.bean.LogImportacaoBIDTO;
import br.com.centralit.citcorpore.negocio.ConexaoBIService;
import br.com.centralit.citcorpore.negocio.LogImportacaoBIService;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;

@SuppressWarnings("rawtypes")
public class GetLogImportacaoBI extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LogImportacaoBIDTO logImportacaoDto = (LogImportacaoBIDTO) document.getBean();
		
		LogImportacaoBIService logImportacaoService = (LogImportacaoBIService)  ServiceLocator.getInstance().getService(LogImportacaoBIService.class, null);
		ConexaoBIService conexaoBIService = (ConexaoBIService)  ServiceLocator.getInstance().getService(ConexaoBIService.class, null);
		
		try {
			logImportacaoDto = (LogImportacaoBIDTO) logImportacaoService.restore(logImportacaoDto);
			ConexaoBIDTO conexaoBIDTO = new ConexaoBIDTO();
			conexaoBIDTO.setIdConexaoBI(logImportacaoDto.getIdConexaoBI());
			conexaoBIDTO = (ConexaoBIDTO) conexaoBIService.restore(conexaoBIDTO);
			
			byte[] buffer;
			String log = "";
			
			log += "CONEXÃO : \n";
			log += "ID : " + logImportacaoDto.getIdConexaoBI() + "\n";
			log += "NOME : " + conexaoBIDTO.getNome() + "\n";
			log += "LINK : " + conexaoBIDTO.getLink() + "\n";
			log += "USUÁRIO : " + conexaoBIDTO.getLogin() + "\n";
			
			log += "\nLOG : \n";
			log += "ID : " + logImportacaoDto.getIdLogImportacao() + "\n";
			log += "DATA DE INICIO : " + UtilDatas.dateToSTRWithFormat(logImportacaoDto.getDataHoraInicio(), "dd/MM/yyyy HH:mm:ss.SSS") + "\n";
			log += "DATA DE TÉRMINO : " + UtilDatas.dateToSTRWithFormat(logImportacaoDto.getDataHoraFim(), "dd/MM/yyyy HH:mm:ss.SSS") + "\n";
			log += "STATUS : " + (logImportacaoDto.getStatus().equalsIgnoreCase("F") ? "Falha" : "Sucesso") + "\n";
			log += "TIPO : " + (logImportacaoDto.getTipo().equalsIgnoreCase("A") ? "Automático" : "Manual") + "\n";
			log += "DETALHAMENTO : \n" + logImportacaoDto.getDetalhamento() + "\n";
			
			buffer = log.getBytes();
			
			response.setContentLength(buffer.length);
			response.setContentType("text/plain");
			response.setHeader("Content-Disposition", "attachment; filename=bi_citsmart_log_" + logImportacaoDto.getIdLogImportacao() + "_conexao_" + logImportacaoDto.getIdConexaoBI() + ".txt");
			
			ServletOutputStream outputStream = response.getOutputStream();
			outputStream.write(buffer);
			outputStream.flush();
			outputStream.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Class getBeanClass() {
		return LogImportacaoBIDTO.class;
	}

}