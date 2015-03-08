/**
 * CentralIT - CITSmart.
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citcorpore.bean.CaracteristicaDTO;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

/**
 * Service de Caracter�stica.
 * 
 * @author valdoilo.damasceno
 * 
 */
public interface CaracteristicaService extends CrudService {

	/**
	 * Cria Nova Caracter�stica.
	 * 
	 * @param caracteristica
	 * @param request
	 * @throws ServiceException
	 * @throws LogicException
	 * @author valdoilo.damasceno
	 */
	public void create(CaracteristicaDTO caracteristica, HttpServletRequest request) throws ServiceException, LogicException;

	/**
	 * Consulta Caracter�sticas Ativas.
	 * 
	 * @param id
	 * @return Collection - Cole��o de Caracter�sticas.
	 * @throws ServiceException
	 * @author valdoilo.damasceno
	 */
	public Collection<CaracteristicaDTO> consultarCaracteristicasAtivas(Integer idTipoItemConfiguracao) throws ServiceException;
	
	/**
	 * Exclui caracter�stica.
	 * 
	 * @param caracteristica
	 *            - Bean de Caracter�stica.
	 * @throws ServiceException
	 * @throws LogicException
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public void excluirCaracteristica(CaracteristicaDTO caracteristica) throws ServiceException, LogicException, Exception;

	/**
	 * Consulta Caracter�sticas com seus respectivos valores.
	 * 
	 * @param idTipoItemConfiguracao
	 * @param idBaseItemConfiguracao
	 * @return Collection - Cole��o de Caracter�sticas.
	 * @throws LogicException
	 * @throws ServiceException
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public Collection<CaracteristicaDTO> consultarCaracteristicasComValores(Integer idTipoItemConfiguracao, Integer idBaseItemConfiguracao) throws LogicException,
			ServiceException, Exception;

	/**
	 * Consulta Caracter�sticas com seus respectivos valores.
	 * 
	 * @param idTipoItemConfiguracao
	 * @param idBaseItemConfiguracao
	 * @return Collection - Cole��o de Caracter�sticas.
	 * @throws LogicException
	 * @throws ServiceException
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public Collection<CaracteristicaDTO> consultarCaracteristicasComValoresItemConfiguracao(Integer idTipoItemConfiguracao, Integer idItemConfiguracao) throws LogicException,
			ServiceException, Exception;
	
	/**
	 * Consulta Caracter�sticas com seus respectivos valores.
	 * 
	 * @param idTipoItemConfiguracao
	 * @param idBaseItemConfiguracao
	 * @param arrCaracteristicas
	 * @return Collection - Cole��o de Caracter�sticas.
	 * @throws LogicException
	 * @throws ServiceException
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public Collection<CaracteristicaDTO> consultarCaracteristicasComValoresItemConfiguracao(Integer idTipoItemConfiguracao, Integer idItemConfiguracao, String [] arrCaracteristicas) throws LogicException,
	ServiceException, Exception;

	/**
	 * Verifica se Caracteristica informada existe.
	 * 
	 * @param grupo
	 * @return true - existe; false - n�o existe;
	 * @throws PersistenceException
	 * @author Thays.araujo
	 */
	public boolean verificarSeCaracteristicaExiste(CaracteristicaDTO caracteristica) throws PersistenceException;

	
	/**
	 * Consulta Caracter�sticas Ativas com array.
	 * 
	 * @param id
	 * @return Collection - Cole��o de Caracter�sticas.
	 * @throws ServiceException
	 * @author flavio.santana
	 */
	public Collection<CaracteristicaDTO> consultarCaracteristicasAtivas(Integer idTipoItemConfiguracao, String[] arrCaracteristicas) throws ServiceException;

}
