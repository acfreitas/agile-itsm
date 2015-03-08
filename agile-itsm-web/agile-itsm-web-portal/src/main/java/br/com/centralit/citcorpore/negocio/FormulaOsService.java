package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;

import br.com.centralit.citcorpore.bean.FormulaOsDTO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

public interface FormulaOsService extends CrudService {

	public ArrayList<FormulaOsDTO> listar(int idContrato) throws ServiceException;

	public FormulaOsDTO buscarPorFormula(String formula) throws Exception;
	
	public boolean verificaSerExisteFormulaIgual(String formula, int idFormula) throws Exception;
}
