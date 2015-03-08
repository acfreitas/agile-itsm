package br.com.centralit.citcorpore.ajaxForms;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.util.UtilTratamentoArquivos;

@SuppressWarnings("rawtypes")
public class GetFileConfig extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			CITCorporeUtil.fazLeituraArquivoConfiguracao();

			File arquivo = new File(CITCorporeUtil.CAMINHO_REAL_CONFIG_FILE);

			byte[] buffer = (UtilTratamentoArquivos.getBytesFromFile(arquivo));
			if (buffer == null){
				buffer = "ARQUIVO NAO EXISTE".getBytes();
			}
			response.setContentLength(buffer.length);
			response.setContentType("text/plain");
			response.setHeader("Content-Disposition", "attachment; filename=citsmart.cfg");

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