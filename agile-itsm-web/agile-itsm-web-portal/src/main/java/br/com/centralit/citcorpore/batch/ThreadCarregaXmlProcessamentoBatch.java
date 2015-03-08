package br.com.centralit.citcorpore.batch;

import java.io.File;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import br.com.centralit.citcorpore.bean.ProcessamentoBatchDTO;
import br.com.centralit.citcorpore.negocio.ProcessamentoBatchService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.service.ServiceLocator;

public class ThreadCarregaXmlProcessamentoBatch extends Thread {
	public void run() {
		try {
			sleep(3000);
		} catch (InterruptedException e2) {
			e2.printStackTrace();
		}
		try {
			ProcessamentoBatchService processamentoBatchService = (ProcessamentoBatchService) ServiceLocator.getInstance().getService(ProcessamentoBatchService.class, null);
			String separator = System.getProperty("file.separator");
			String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "XMLs"  + separator;
			File file = new File(diretorioReceita + "processamentoBatch.xml");/*
			Collection<ProcessamentoBatchDTO> colProcessamentoBatchDTOs = (Collection<ProcessamentoBatchDTO>) processamentoBatchService.getAtivos();*/
			SAXBuilder sb = new SAXBuilder();
			Document doc = sb.build(file);
			Element elements = doc.getRootElement();
			List<Element> processametoSuperior = elements.getChild("processamentoBatch").getChildren();
			for (Element batchs : processametoSuperior) {
				ProcessamentoBatchDTO processamentoBatchDTO = new ProcessamentoBatchDTO();
				processamentoBatchDTO.setDescricao(batchs.getChildText("descricao").trim());
				processamentoBatchDTO.setConteudo(batchs.getChildText("conteudo").trim());
				if(!processamentoBatchService.existeDuplicidade(processamentoBatchDTO) && !processamentoBatchService.existeDuplicidadeClasse(processamentoBatchDTO)){
					processamentoBatchDTO.setTipo(batchs.getChildText("tipo").trim());
					processamentoBatchDTO.setSituacao(batchs.getChildText("situacao").trim());
					processamentoBatchDTO.setExpressaoCRON("");
					processamentoBatchService.create(processamentoBatchDTO);
				}
				processamentoBatchDTO = null;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
