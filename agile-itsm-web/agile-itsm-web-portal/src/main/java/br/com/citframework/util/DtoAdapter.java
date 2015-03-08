package br.com.citframework.util;

import java.io.File;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.IDto;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class DtoAdapter extends XmlAdapter<String, IDto> {
	
	private String getPath() {
		String strTempUpload = CITCorporeUtil.CAMINHO_REAL_APP
				+ "tempUpload";

		File fileDir = new File(strTempUpload);
		if (!fileDir.exists()) {
			fileDir.mkdir();
		}
		fileDir = new File(strTempUpload);
		if (!fileDir.exists()) {
			fileDir.mkdir();
		}
		
		String fileName = strTempUpload + "/temp_"+UtilDatas.getDataHoraAtual().getTime()+".xml";
		return fileName;
	}

    @Override
    public String marshal(IDto obj) throws Exception {
    	//String fileName = getPath();
		//OutputStream os = new FileOutputStream(fileName);
		//JAXB.marshal(obj, os);
		//return UtilTratamentoArquivos.getStringTextFromFileTxt(fileName,"UTF-8");
		
		/*String result = UtilTratamentoArquivos.getStringTextFromFileTxt(fileName,"UTF-8");
		if (result.indexOf("<?xml") >= 0) {
			result = result.substring(result.indexOf(">")+1);
		}
		result = result.substring(result.indexOf(">")+1);
		int p = result.length() - 1;
		for (int i = result.length() - 1; i >= 0; i--) {
			if (result.charAt(i) == '<') {
				p = i-1;
				break;
			}
		}
		result = result.substring(0,p);
		return result;*/

    	XStream x = new XStream(new DomDriver("UTF-8"));
		return x.toXML(obj);
    }

    @Override
    public IDto unmarshal(String str) throws Exception {
    	//String fileName = getPath();
    	//UtilTratamentoArquivos.geraFileTxtFromString(fileName, str);
    	//return JAXB.unmarshal(fileName, IDto.class);
		XStream x = new XStream(new DomDriver("UTF-8"));
		return (IDto) x.fromXML(str);
	}

}
