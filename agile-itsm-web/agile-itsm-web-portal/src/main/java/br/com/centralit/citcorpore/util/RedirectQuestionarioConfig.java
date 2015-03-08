package br.com.centralit.citcorpore.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import br.com.citframework.util.Constantes;

public class RedirectQuestionarioConfig {
	private static final Logger LOGGER = Logger.getLogger(RedirectQuestionarioConfig.class);
	private static RedirectQuestionarioConfig	singleton;
	private Document doc = null;
	
	private Collection redirectItens;
	
	public static RedirectQuestionarioConfig getInstance() throws Exception {
		if (singleton == null) {
			String fileNameConfig = "redirectQuestionario.xml";
			if (Constantes.getValue("REDIRECTQUESTIONARIO_CONFIG") != null && !Constantes.getValue("REDIRECTQUESTIONARIO_CONFIG").trim().equalsIgnoreCase("")){
				fileNameConfig = Constantes.getValue("REDIRECTQUESTIONARIO_CONFIG");
			}
			InputStream questionarioConfigFile = RedirectQuestionarioConfig.class.getClassLoader().getResourceAsStream(fileNameConfig); 
			if (questionarioConfigFile == null){
			    questionarioConfigFile = new FileInputStream(CITCorporeUtil.CAMINHO_REAL_APP + "/WEB-INF/" + fileNameConfig);
			}
			if (questionarioConfigFile == null){
			    questionarioConfigFile = new FileInputStream(Constantes.getValue("CAMINHO_REDIRECTQUESTIONARIO_CONFIG") + fileNameConfig);
			}
			LOGGER.info("REDIRECTQUESTIONARIO_CONFIG: " + fileNameConfig);
			singleton = new RedirectQuestionarioConfig(questionarioConfigFile, fileNameConfig);
		}
		return singleton;
	}
	public RedirectQuestionarioConfig(InputStream ioos, String fileNameConfig){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            if (ioos == null){
            	throw new Exception("ARQUIVO (REDIRECTQUESTIONARIO_CONFIG): " + fileNameConfig + " NAO ENCONTRADO!!!!!!!!!");
            }
            doc = builder.parse(ioos);
            load();
        } catch (Exception e) {
            e.printStackTrace();
            doc = null;
        }		
	}
	public void load(){
		if (doc == null) return;
		String aba = "", situacao = "", include = "";
		redirectItens = new ArrayList();
		RedirectQuestionarioItem item;
		Node noRoot = doc.getChildNodes().item(0);
		for(int j = 0; j < noRoot.getChildNodes().getLength(); j++){
            Node noItem = noRoot.getChildNodes().item(j);
            if(noItem.getNodeName().equals("#text")) continue;

            NamedNodeMap map = noItem.getAttributes();
            aba = map.getNamedItem("aba").getNodeValue();
            situacao = map.getNamedItem("situacao").getNodeValue();
            include = map.getNamedItem("include").getNodeValue();
            
            include = include.replaceAll("\\{SERVER_ADDRESS\\}", Constantes.getValue("SERVER_ADDRESS"));
            include = include.replaceAll("\\{CONTEXTO_APLICACAO\\}", Constantes.getValue("CONTEXTO_APLICACAO"));
            
            item = new RedirectQuestionarioItem();
            item.setAba(aba);
            item.setSituacao(situacao);
            item.setInclude(include);
            
            redirectItens.add(item);
		}
	}
	public Collection getRedirectItens() {
		return redirectItens;
	}
	public void setRedirectItens(Collection redirectItens) {
		this.redirectItens = redirectItens;
	}
	public String getIncludeCorrespondente(String aba, String situacao){
		Collection colItens = getRedirectItens();
		if (colItens != null){
			for(Iterator it = colItens.iterator(); it.hasNext();){
				RedirectQuestionarioItem item = (RedirectQuestionarioItem)it.next();
				if (item.getAba().equalsIgnoreCase(aba) && item.getSituacao().equalsIgnoreCase(situacao)){
					return item.getInclude();
				}
			}
		}
		return null;
	}
}

