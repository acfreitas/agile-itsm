/**
 * CentralIT - CITSmart.
 */
package br.com.centralit.citcorpore.negocio;

import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

/**
 * Service de CaracteristicaTipoItemConfiguracao.
 * 
 * @author valdoilo.damasceno
 * 
 */
public interface CaracteristicaTipoItemConfiguracaoService extends CrudService {

    /**
     * Exclui associa��o da Caracter�stica com o Tipo de Item Configura��o.
     * 
     * @throws ServiceException
     * @throws PersistenceException
     * @author valdoilo.damasceno
     * @param idCaracteristica
     * @throws Exception
     */
    public void excluirAssociacaoCaracteristicaTipoItemConfiguracao(Integer idTipoItemConfiguracao, Integer idCaracteristica)
	    throws PersistenceException, ServiceException, Exception;

}
