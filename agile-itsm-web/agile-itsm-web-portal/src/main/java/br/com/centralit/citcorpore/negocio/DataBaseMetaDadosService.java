/**
 * 
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

/**
 * Atua como a camada de Service para dataBaseMetaDados
 * @author flavio.santana
 *
 */
@SuppressWarnings("rawtypes")
public interface DataBaseMetaDadosService extends CrudService {
	
	public void corrigeTabelaComplexidade() throws ServiceException, Exception;
	
	public void corrigeTabelaSla() throws ServiceException, Exception;
	
	public String carregaTodosMetaDados() throws Exception;
	
	public String carregaTodosMetaDados(Collection colecao) throws Exception;
	
	public Collection getDataBaseMetaDadosUtil() throws Exception;
	
	public void desabilitaTabelas() throws LogicException, ServiceException, Exception;
	
	public void corrigeTabelaFluxoServico() throws Exception;
	
}
