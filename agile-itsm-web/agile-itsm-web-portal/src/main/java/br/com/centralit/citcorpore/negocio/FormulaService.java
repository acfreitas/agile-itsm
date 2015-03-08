package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.FormulaDTO;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.service.CrudService;
public interface FormulaService extends CrudService {
	public Collection findByIdentificador(String parm) throws Exception;
	public void deleteByIdentificador(String parm) throws Exception;
	public boolean existeRegistro(String nome);
	public boolean verificarSeIdentificadorExiste(FormulaDTO formula) throws PersistenceException;
	public boolean verificarSeNomeExiste(FormulaDTO formula) throws PersistenceException;
}
