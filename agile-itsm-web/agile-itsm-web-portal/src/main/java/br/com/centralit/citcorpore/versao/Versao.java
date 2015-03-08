package br.com.centralit.citcorpore.versao;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import br.com.centralit.citcorpore.util.CITCorporeUtil;

public class Versao {
//    public static String VERSAO_MAIOR = "2";
//    public static String VERSAO_MENOR = "0";
//    public static String VERSAO_REVISAO_CORRECOES = "7";
    
    public static String VERSAO_DATA_GERACAO = "";
    
    public static String getVersao(){
    	
    	return lerXmlDeVersoes();
		
    	//return Versao.VERSAO_MAIOR + "." + Versao.VERSAO_MENOR + "." + Versao.VERSAO_REVISAO_CORRECOES;
    }
    
    public static String getDataAndVersao(){
    	return Versao.VERSAO_DATA_GERACAO + " " + lerXmlDeVersoes();
    }
    
    private static String lerXmlDeVersoes(){
    	// LEITURA PROGRESSIVA DO XML
    	
    	String versaoStr = "2.0.7";
    	
		String separator = System.getProperty("file.separator");
		String diretorio = CITCorporeUtil.CAMINHO_REAL_APP + "XMLs" + separator;
		File file = new File(diretorio + "historicoDeVersoes.xml");
		SAXBuilder sb = new SAXBuilder();
		Document doc = new Document();
		
		try {
			doc = sb.build(file);
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Element historicoDeVersoes = doc.getRootElement();
		
		List<Element> versoes = historicoDeVersoes.getChildren();
		
		if(versoes != null && versoes.size() > 0){
			return versaoStr = versoes.get(versoes.size() -1).getText();
		}else{
			return versaoStr;
		}
    }
}
