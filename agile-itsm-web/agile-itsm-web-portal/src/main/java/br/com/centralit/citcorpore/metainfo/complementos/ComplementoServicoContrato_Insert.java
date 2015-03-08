package br.com.centralit.citcorpore.metainfo.complementos;

import java.util.HashMap;

import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.metainfo.bean.DinamicViewsDTO;
import br.com.centralit.citcorpore.metainfo.bean.VisaoDTO;
import br.com.centralit.citcorpore.metainfo.negocio.DinamicViewsService;
import br.com.centralit.citcorpore.metainfo.negocio.VisaoService;
import br.com.centralit.citcorpore.negocio.ServicoContratoService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.ServiceLocator;

public class ComplementoServicoContrato_Insert {
    public String execute(DinamicViewsDTO dinamicViewDto, HashMap mapFields, TransactionControler transactionControler) throws ServiceException, Exception{
	Object obj = mapFields.get("IDSERVICO");
	String[] idServicos = null;
	if (obj != null){
        	if (String[].class.isInstance(obj)){
        	    idServicos = (String[])obj;
        	}else if (String.class.isInstance(obj)){
        	    idServicos = new String[1];
        	    idServicos[0] = (String)obj;
        	}
	}
	if (idServicos == null){ //Provavelmente nenhum serviço selecionado.
	    return "OK";
	}
	
	String idContratoStr = (String) mapFields.get("IDCONTRATO");
	Integer idContrato = null;
	try{
	    idContrato = Integer.parseInt(idContratoStr);
	}catch (Exception e) {
	}
	if (idContrato == null){
	    return "ERROR";
	}
	
	VisaoService visaoService = (VisaoService) ServiceLocator.getInstance().getService(VisaoService.class, null);
	ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
	
	VisaoDTO visaoDto = visaoService.findByIdentificador("Servicos_Contrato");
	if (visaoDto != null){
	    for (int i = 0; i < idServicos.length; i++){
		dinamicViewDto.setDinamicViewsIdVisao(visaoDto.getIdVisao());
		DinamicViewsService dinamicViewsService = (DinamicViewsService) ServiceLocator.getInstance().getService(DinamicViewsService.class, null);
		dinamicViewDto.setAbortFuncaoPrincipal(false);
		mapFields.put("IDSERVICO", idServicos[i]);
		mapFields.put("IDSERVICOCONTRATO", null);
		
		String idServicoStr = idServicos[i];
		Integer idServico = null;
		try{
		    idServico = Integer.parseInt(idServicoStr);
		}catch (Exception e) {
		}
		
		ServicoContratoDTO servicoContratoDTO = servicoContratoService.findByIdContratoAndIdServico(idContrato, idServico);
		if (servicoContratoDTO == null){ //So faz se o servico ainda nao estiver criado para o contrato.
        		try{
        		    dinamicViewsService.save(null, dinamicViewDto, mapFields, null);
        		}catch (Exception e) {
        		    e.printStackTrace();
        		    return "ERROR";
        		}
		}
		transactionControler.commit();
	    }
	}else{
	    return "ERROR";
	}
	return "OK";
    }
}
