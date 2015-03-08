/**
 * CentralIT - CITSmart
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.TipoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.ValorDTO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

/**
 * Service de Valor.
 * 
 * @author valdoilo.damasceno
 * 
 */
@SuppressWarnings("rawtypes")
public interface ValorService extends CrudService {

    /**
     * Consulta valor por idItemConfiguracao.
     * 
     * @param parm
     * @return Collection
     * @throws Exception
     * @author valdoilo.damasceno
     */
    public Collection findByIdItemConfiguracao(Integer parm) throws Exception;

    /**
     * Consulta Valor por idCaracteristica.
     * 
     * @param idCaracteristica
     * @return Collection
     * @throws Exception
     * @author valdoilo.damasceno
     */
    public Collection findByIdCaracteristica(Integer idCaracteristica) throws Exception;

    /**
     * Consulta valor por ItemConfiguracao e TipoItemConfiguracao.
     * 
     * @param itemConfiguracao
     * @param tipoItemConfiguracao
     * @return Collection<ValorDTO>
     * @author valdoilo.damasceno
     * @throws Exception
     * @throws ServiceException
     */
    public Collection<ValorDTO> findByItemAndTipoItemConfiguracao(ItemConfiguracaoDTO itemConfiguracao, TipoItemConfiguracaoDTO tipoItemConfiguracao) throws ServiceException, Exception;

    /**
     * Deleta valor por idItemConfiguracao.
     * 
     * @param idItemConfiguracao
     * @throws Exception
     * @author valdoilo.damasceno
     */
    public void deleteByIdItemConfiguracao(Integer idItemConfiguracao) throws Exception;

    /**
     * Deleta Valor por idCaracteristica.
     * 
     * @param idCaracteristica
     * @throws Exception
     * @author valdoilo.damasceno
     */
    public void deleteByIdCaracteristica(Integer idCaracteristica) throws Exception;

    /**
     * Recupera Valor da Característica do Item Configuracao.
     * 
     * @param idBaseItemConfiguracao
     * @param idCaracteristica
     * @return ValorDTO
     * @throws Exception
     * @author valdoilo.damasceno
     */
    public ValorDTO restore(Integer idBaseItemConfiguracao, Integer idCaracteristica) throws Exception;
    
    public Collection<ValorDTO> findByItemAndTipoItemConfiguracaoSofware(ItemConfiguracaoDTO itemConfiguracao, TipoItemConfiguracaoDTO tipoItemConfiguracao)  throws Exception;
    
    public ValorDTO restoreItemConfiguracao(Integer idItemConfiguracao, Integer idCaracteristica) throws Exception ;
    public Collection listByItemConfiguracaoAndTagCaracteristica(Integer idItemConfiguracao, String tag) throws Exception;
    public Collection listUniqueValuesByTagCaracteristica(String tag) throws Exception;
}
