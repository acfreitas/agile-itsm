/**
 * CentralIT - CITSmart.
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.AnexoBaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

/**
 * Service de AnexoBaseConhecimento.
 * 
 * @author valdoilo.damasceno
 */
public interface AnexoBaseConhecimentoService extends CrudService {

    /**
     * Retorna os Anexos da Base de Conhecimento informada.
     * 
     * @param baseConhecimentoBean
     * @return listaDeAnexos
     * @throws Exception
     * @throws ServiceException
     * @author valdoilo.damasceno
     */
    public Collection<AnexoBaseConhecimentoDTO> consultarAnexosDaBaseConhecimento(BaseConhecimentoDTO baseConhecimentoBean) throws ServiceException, Exception;

}
