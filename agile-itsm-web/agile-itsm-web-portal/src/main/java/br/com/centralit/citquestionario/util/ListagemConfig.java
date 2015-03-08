package br.com.centralit.citquestionario.util;

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

import br.com.centralit.citajax.util.CitAjaxUtil;
import br.com.centralit.citquestionario.bean.ListagemDTO;
import br.com.centralit.citquestionario.bean.ListagemItemDTO;
import br.com.citframework.util.Constantes;

public class ListagemConfig {
	private static final Logger LOGGER = Logger.getLogger(ListagemConfig.class);
	private static ListagemConfig	singleton;
	private Document doc = null;
	
	private Collection listagens;
	
    public static ListagemConfig getInstance() throws Exception {
		if (singleton == null) {
			String fileNameConfig = "listagem-config.xml";
			if (Constantes.getValue("LISTAGEM_QUESTIONARIO_CFG") != null && !Constantes.getValue("LISTAGEM_QUESTIONARIO_CFG").trim().equalsIgnoreCase("")){
				fileNameConfig = Constantes.getValue("LISTAGEM_QUESTIONARIO_CFG");
			}
			InputStream listagemConfigFile = ListagemConfig.class.getResourceAsStream(fileNameConfig); 
			if (listagemConfigFile == null){
			    listagemConfigFile = new FileInputStream(CitAjaxUtil.CAMINHO_REAL_APP + "/WEB-INF/" + fileNameConfig);
			}
			if (listagemConfigFile == null){
				listagemConfigFile = new FileInputStream(Constantes.getValue("CAMINHO_LISTAGEM_QUESTIONARIO_CFG") + fileNameConfig);
			}
			LOGGER.info("CITAJAX_CONFIG: " + fileNameConfig);
			singleton = new ListagemConfig(listagemConfigFile, fileNameConfig);
		}
		return singleton;
	}
	public ListagemConfig(InputStream ioos, String fileNameConfig){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            if (ioos == null){
            	throw new Exception("ARQUIVO (LISTAGEM_QUESTIONARIO_CFG): " + fileNameConfig + " NAO ENCONTRADO!!!!!!!!!");
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
		String nome = "", descricao = "", SQL = "";
		listagens = new ArrayList();
		ListagemDTO listagem;
		Node noRoot = doc.getChildNodes().item(0);
		for(int j = 0; j < noRoot.getChildNodes().getLength(); j++){
            Node noItem = noRoot.getChildNodes().item(j);
            if(noItem.getNodeName().equals("#text")) continue;

            NamedNodeMap map = noItem.getAttributes();
            nome = map.getNamedItem("nome").getNodeValue();
            descricao = map.getNamedItem("descricao").getNodeValue();
           
            listagem = new ListagemDTO();
            listagem.setNome(nome);
            listagem.setDescricao(descricao);
            
            listagem = getCampos(listagem, noItem);
            listagens.add(listagem);
		}
	}
	public ListagemDTO getCampos(ListagemDTO listagem, Node noItem){
		if (noItem == null) return listagem;
		
		String nome = "", descricao = "";
		ListagemItemDTO item;
        if (noItem.getChildNodes() != null){
            for (int i = 0; i < noItem.getChildNodes().getLength(); i++){
            	Node noListagemItem = noItem.getChildNodes().item(i);
            	if (noListagemItem.getNodeName().equals("SQL")) {
            	    listagem.setSQL(noListagemItem.getTextContent());
            	    continue;
            	}
            	
            	if (noListagemItem.getNodeName().equals("campos") && noListagemItem.getChildNodes() != null){
            	    for (int c = 0; c < noListagemItem.getChildNodes().getLength(); c++){
            	        Node noCampo = noListagemItem.getChildNodes().item(c);
            	        if(noCampo.getNodeName().equals("#text")) continue;
            	        
                        NamedNodeMap map = noCampo.getAttributes();
                        nome = map.getNamedItem("nome").getNodeValue();
                        descricao = "";
                        if (map.getNamedItem("descricao") != null) {
                            descricao = map.getNamedItem("descricao").getNodeValue();
                        }
                        
                        item = new ListagemItemDTO();
                        item.setNome(nome);
                        item.setDescricao(descricao);
                        
                        Collection col = listagem.getCampos();
                        if (col == null){
                            col = new ArrayList();
                            listagem.setCampos(col);
                        }
                        col.add(item);
            	    }
            	}
            	
            }
        }	
        return listagem;
	}
	
    public Collection getListagens() {
        return listagens;
    }
    
    public void setListagens(Collection listagens) {
        this.listagens = listagens;
    }
    
    public ListagemDTO find(String nomeListagem) {
        ListagemDTO result = null;
        for (Iterator it = listagens.iterator(); it.hasNext();){
            ListagemDTO listagem = (ListagemDTO) it.next();
            if (listagem.getNome().equalsIgnoreCase(nomeListagem)) {
                result = listagem;
                break;
            }
        }        
        return result;
    }
}

