/**
 * CentralIT - CITSmart.
 */
package br.com.centralit.citcorpore.negocio;

import java.util.List;

import br.com.centralit.citcorpore.bean.BaseItemConfiguracaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

/**
 * Service de BaseItemConfiguracao.
 * 
 * @author valdoilo.damasceno
 * 
 */
public interface BaseItemConfiguracaoService extends CrudService {

    /**
     * Inclui Novo Item de Configuração.
     * 
     * @param baseItemConfiguracaoBean
     * @param request
     * @return IDto
     * @throws ServiceException
     * @throws LogicException
     * @author valdoilo.damasceno
     * @throws FileUploadException
     */
    /*public IDto create(BaseItemConfiguracaoDTO baseItemConfiguracaoBean, HttpServletRequest request) throws ServiceException, LogicException,
	    FileUploadException;*/

    /**
     * Verifica se existe cadastro pra BaseItemConfiguracão.
     * 
     * @param obj
     * @param nomePai
     * @return
     * @throws Exception
     */
    public boolean existBaseItemConfiguracao(BaseItemConfiguracaoDTO dto) throws Exception;

    /**
     * Método que persiste nova BaseItemConfiguração
     * 
     * @param baseItemConfiguracao
     * @return
     * @throws ServiceException
     * @throws LogicException
     */
	public IDto[] create(BaseItemConfiguracaoDTO[] baseItemConfiguracao)
			throws ServiceException, LogicException;

	List<IDto> restoreChildren(BaseItemConfiguracaoDTO baseItemConfiguracaoBean)
			throws Exception;

	public void update(BaseItemConfiguracaoDTO[] vetorBaseItemConfiguracao) throws ServiceException, LogicException;

}
