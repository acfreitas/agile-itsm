package br.com.centralit.citcorpore.ajaxForms;

import java.util.Calendar;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.BICitsmartResultRotinaDTO;
import br.com.centralit.citcorpore.bean.ExportManualBIDTO;
import br.com.centralit.citcorpore.bi.operation.BICitsmartOperation;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.util.UtilDatas;

@SuppressWarnings("rawtypes")
public class GetExportBI extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BICitsmartOperation biCitsmartOperation = new BICitsmartOperation();
		String idConexaoBI = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.BICITSMART_ID_CONEXAO, "");
		byte[] buffer;
		Calendar c = Calendar.getInstance();
		
		BICitsmartResultRotinaDTO result = biCitsmartOperation.exportacaoManualDownloadBICitsmart();
		if (result.isResultado()) {
			buffer = result.getMensagem().getBytes("ISO-8859-1");
		} else {
			buffer = "<xml></xml>".getBytes();
		}
		
		response.setContentLength(buffer.length);
		response.setContentType("text/xml");
		response.setHeader("Content-Disposition", "attachment; filename=bi_citsmart_exportacao_" + idConexaoBI + "_" + UtilDatas.getDataAtual() + "_" + c.get(Calendar.HOUR_OF_DAY) + "h" + c.get(Calendar.MINUTE) + ".xml");
		
		ServletOutputStream outputStream = response.getOutputStream();
		outputStream.write(buffer);
		outputStream.flush();
		outputStream.close();
	}

	@Override
	public Class getBeanClass() {
		return ExportManualBIDTO.class;
	}

}