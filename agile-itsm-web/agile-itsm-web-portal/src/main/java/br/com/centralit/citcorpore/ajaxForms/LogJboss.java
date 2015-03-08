package br.com.centralit.citcorpore.ajaxForms;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.citframework.util.UtilTratamentoArquivos;

@SuppressWarnings("rawtypes")
public class LogJboss extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String diretorioCorrente = System.getProperty("jboss.server.log.dir");

			File arquivo = new File(diretorioCorrente + File.separator + "server.log");

			byte[] buffer = (UtilTratamentoArquivos.getBytesFromFile(arquivo));

			response.setContentLength(buffer.length);
			response.setContentType("text/plain");
			response.setHeader("Content-Disposition", "attachment; filename=server.log");

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
		return Object.class;
	}

}