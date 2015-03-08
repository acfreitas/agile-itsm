package br.com.centralit.citcorpore.ajaxForms;

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

import br.com.centralit.citcorpore.bean.InformacoesContratoConfigDTO;
import br.com.centralit.citcorpore.bean.InformacoesContratoItem;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.InformacoesContratoConfigService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;

public class InformacoesContratoConfig {
	private static final Logger LOGGER = Logger.getLogger(InformacoesContratoConfig.class);
	private static InformacoesContratoConfig	singleton;
	private Document doc = null;
	
	private Collection prontuarioItens;
	
	public static InformacoesContratoConfig getInstance() throws Exception {
		return getInstance(null);
	}
	public static InformacoesContratoConfig getInstance(UsuarioDTO user) throws Exception {
		String idEmpStr = Constantes.getValue("ID_EMPRESA_PROC_BATCH");
		Integer idUsuario;
		if (idEmpStr == null || idEmpStr.trim().equalsIgnoreCase("")){
			idUsuario = new Integer(1);
		}else{
			idUsuario = new Integer(idEmpStr);
		}		
		// POR BD.
		InformacoesContratoConfigService prontuarioEletronicoConfigService = (InformacoesContratoConfigService) ServiceLocator.getInstance().getService(InformacoesContratoConfigService.class, null);
		Collection colItensProntuariosSemPai = prontuarioEletronicoConfigService.findSemPai(idUsuario);
		singleton = new InformacoesContratoConfig(colItensProntuariosSemPai, user);
		return singleton;
	}
	public InformacoesContratoConfig(InputStream ioos, String fileNameConfig){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            if (ioos == null){
            	throw new Exception("ARQUIVO (PRONTUARIOELETRONICO_CONFIG): " + fileNameConfig + " NAO ENCONTRADO!!!!!!!!!");
            }
            doc = builder.parse(ioos);
            load();
        } catch (Exception e) {
            e.printStackTrace();
            doc = null;
        }		
	}
	public InformacoesContratoConfig(Collection colItensDB, UsuarioDTO user) throws ServiceException, Exception{
		doc = null;
		loadFromDB(colItensDB, user);
	}
	public void load(){
		if (doc == null) return;
		String nome = "", descricao = "", path = "";
		prontuarioItens = new ArrayList();
		InformacoesContratoItem item;
		Node noRoot = doc.getChildNodes().item(0);
		for(int j = 0; j < noRoot.getChildNodes().getLength(); j++){
            Node noItem = noRoot.getChildNodes().item(j);
            if(noItem.getNodeName().equals("#text")) continue;

            NamedNodeMap map = noItem.getAttributes();
            nome = map.getNamedItem("nome").getNodeValue();
            descricao = map.getNamedItem("descricao").getNodeValue();
            path = map.getNamedItem("path").getNodeValue();
            
            item = new InformacoesContratoItem();
            item.setNome(nome);
            item.setDescricao(descricao);
            item.setPath(path);
            item.setGrupo(false);
            
            item = getSubTree(item, noItem);
            prontuarioItens.add(item);
		}
	}
	public void loadFromDB(Collection colItensEmDB, UsuarioDTO user) throws ServiceException, Exception{
		if (colItensEmDB == null) return; 
		prontuarioItens = new ArrayList();
		Collection prontuarioTratamento = new ArrayList();
		InformacoesContratoItem item;
		
		if (user == null){
			return;
		}
		
		//ProntuarioEletronicoPerfSegService prontuarioEletronicoPerfSegService = (ProntuarioEletronicoPerfSegService) ServiceLocator.getInstance().getService(ProntuarioEletronicoPerfSegService.class, null);
		for(Iterator it = colItensEmDB.iterator(); it.hasNext();){
			InformacoesContratoConfigDTO prontuarioEletronicoConfigDTO = (InformacoesContratoConfigDTO)it.next();
			if (prontuarioEletronicoConfigDTO.getFuncItem().equalsIgnoreCase("J")){ //Nao eh para ser apresentado no prontuario normal
				continue;
			}
			
			//Collection colPerfisAssociados = prontuarioEletronicoPerfSegService.findByIdProntuarioEletronicoConfig(prontuarioEletronicoConfigDTO.getIdInformacoesContratoConfig());
			//if (isPerfilUsuarioLogadoInCollection(colPerfisAssociados, user)){
	            item = new InformacoesContratoItem();
	            item.setNome(prontuarioEletronicoConfigDTO.getNome());
	            item.setDescricao(prontuarioEletronicoConfigDTO.getDescricao());
	            item.setPath(prontuarioEletronicoConfigDTO.getFuncionalidadeCompleta());
	            item.setIdQuestionario(prontuarioEletronicoConfigDTO.getIdQuestionario());
	            item.setFuncItem(prontuarioEletronicoConfigDTO.getFuncItem());
	            
	            item = getSubTreeDB(item, prontuarioEletronicoConfigDTO, user);
	            prontuarioTratamento.add(item);
			//}
		}
		prontuarioItens = retiraGruposVazios(prontuarioTratamento);
	}	
	
	private Collection retiraGruposVazios(Collection col){
		if (col == null){
			return null;
		}
		Collection colAjustada = new ArrayList();
		for(Iterator it = col.iterator(); it.hasNext();){
			InformacoesContratoItem prontuarioEletronicoItem = (InformacoesContratoItem)it.next();
			
			if (prontuarioEletronicoItem.isGrupo()){
				Collection colAux = retiraGruposVazios(prontuarioEletronicoItem.getColSubItens());
				if (colAux != null && colAux.size() > 0){
					colAjustada.add(prontuarioEletronicoItem);
				}
			}else{
				colAjustada.add(prontuarioEletronicoItem);
			}
		}	
		return colAjustada;
	}
	private boolean isPerfilUsuarioLogadoInCollection(Collection colPerfisAssociados, UsuarioDTO user){
	    return true;
	}
	/*
	private boolean isPerfilUsuarioLogadoInCollection(Collection colPerfisAssociados, UsuarioDTO user){
		if (colPerfisAssociados != null){
			for(Iterator it = colPerfisAssociados.iterator(); it.hasNext();){
				InformacoesContratoPerfSegDTO prontuarioEletronicoPerfSegDTO = (InformacoesContratoPerfSegDTO) it.next();
				if (user.getColPerfis() != null){
					for(Iterator itPerfUser = user.getColPerfis().iterator(); itPerfUser.hasNext();){
						PessoasPerfilSegurancaDTO pessoasPerfilSegurancaDTO = (PessoasPerfilSegurancaDTO) itPerfUser.next();
						if (pessoasPerfilSegurancaDTO.getIdPerfilSeguranca().intValue() == prontuarioEletronicoPerfSegDTO.getIdPerfilSeguranca().intValue()){
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	*/
	public InformacoesContratoItem getSubTree(InformacoesContratoItem p, Node noItem){
		if (noItem == null) return p;
		
		String nome = "", descricao = "", path = "";
		InformacoesContratoItem item;
        if (noItem.getChildNodes() != null){
            for (int i = 0; i < noItem.getChildNodes().getLength(); i++){
            	Node noMenuItem = noItem.getChildNodes().item(i);
            	if(noMenuItem.getNodeName().equals("#text")) continue;
            	
                NamedNodeMap map = noMenuItem.getAttributes();
                nome = map.getNamedItem("nome").getNodeValue();
                descricao = map.getNamedItem("descricao").getNodeValue();
                path = map.getNamedItem("path").getNodeValue();
                
                item = new InformacoesContratoItem();
                item.setNome(nome);
                item.setDescricao(descricao);
                item.setPath(path);   
                item.setGrupo(false);
                
                item = getSubTree(item, noMenuItem);
                Collection col = p.getColSubItens();
                if (col == null){
                	col = new ArrayList();
                	p.setColSubItens(col);
                }
                col.add(item);
                
                p.setGrupo(true);
            }
        }	
        return p;
	}
	public InformacoesContratoItem getSubTreeDB(InformacoesContratoItem prontuarioEletronicoItem, InformacoesContratoConfigDTO prontuarioEletronicoConfigDTO, UsuarioDTO user) throws ServiceException, Exception{
		if (prontuarioEletronicoConfigDTO == null) return prontuarioEletronicoItem;
		
		InformacoesContratoItem item;
		
		InformacoesContratoConfigService prontuarioEletronicoConfigService = (InformacoesContratoConfigService) ServiceLocator.getInstance().getService(InformacoesContratoConfigService.class, null);
		Collection colItensProntuariosDoPai = prontuarioEletronicoConfigService.findByPai(prontuarioEletronicoConfigDTO.getIdInformacoesContratoConfig());
		
		//ProntuarioEletronicoPerfSegService prontuarioEletronicoPerfSegService = (ProntuarioEletronicoPerfSegService) ServiceLocator.getInstance().getService(ProntuarioEletronicoPerfSegService.class, null);
		if (colItensProntuariosDoPai != null){
			for (Iterator it = colItensProntuariosDoPai.iterator(); it.hasNext();){
				InformacoesContratoConfigDTO informacoesContratoConfigDTOAux = (InformacoesContratoConfigDTO)it.next();
				if (informacoesContratoConfigDTOAux.getFuncItem().equalsIgnoreCase("J")){ //Nao eh para ser apresentado no prontuario normal
					continue;
				}				
				//Collection colPerfisAssociados = prontuarioEletronicoPerfSegService.findByIdProntuarioEletronicoConfig(prontuarioEletronicoConfigDTOAux.getIdInformacoesContratoConfig());
				//if (isPerfilUsuarioLogadoInCollection(colPerfisAssociados, user)){				
					item = new InformacoesContratoItem();
					item.setNome(informacoesContratoConfigDTOAux.getNome());
					item.setDescricao(informacoesContratoConfigDTOAux.getDescricao());
					item.setPath(informacoesContratoConfigDTOAux.getFuncionalidadeCompleta());   
					item.setIdQuestionario(informacoesContratoConfigDTOAux.getIdQuestionario());
					item.setFuncItem(informacoesContratoConfigDTOAux.getFuncItem());
					item.setGrupo(false);
					
					item = getSubTreeDB(item, informacoesContratoConfigDTOAux, user);
					Collection col = prontuarioEletronicoItem.getColSubItens();
					if (col == null){
						col = new ArrayList();
						prontuarioEletronicoItem.setColSubItens(col);
					}
					col.add(item);
				//}
				prontuarioEletronicoItem.setGrupo(true);
			}
		}
		return prontuarioEletronicoItem;
	}
	public Collection getProntuarioItens() {
		return prontuarioItens;
	}
	public void setProntuarioItens(Collection prontuarioItens) {
		this.prontuarioItens = prontuarioItens;
	}	
}

