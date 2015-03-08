package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.SolucaoContornoDTO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudService;

public interface SolucaoContornoService extends CrudService{
	
	public SolucaoContornoDTO findSolucaoContorno(SolucaoContornoDTO solucaoContorno) throws Exception;
	
	public SolucaoContornoDTO create(SolucaoContornoDTO solucaoContornoDto, TransactionControler tc) throws Exception;
	
	public SolucaoContornoDTO findByIdProblema(SolucaoContornoDTO solucaoContorno) throws Exception;

}
