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
 * Service de Característica.
 * 
 * @author valdoilo.damasceno
 * 
 */
public interface CaracteristicaService extends CrudService {

	/**
	 * Cria Nova Característica.
	 * 
	 * @param caracteristica
	 * @param request
	 * @throws ServiceException
	 * @throws LogicException
	 * @author valdoilo.damasceno
	 */
	public void create(CaracteristicaDTO caracteristica, HttpServletRequest request) throws ServiceException, LogicException;

	/**
	 * Consulta Características Ativas.
	 * 
	 * @param id
	 * @return Collection - Coleção de Características.
	 * @throws ServiceException
	 * @author valdoilo.damasceno
	 */
	public Collection<CaracteristicaDTO> consultarCaracteristicasAtivas(Integer idTipoItemConfiguracao) throws ServiceException;
	
	/**
	 * Exclui característica.
	 * 
	 * @param caracteristica
	 *            - Bean de Característica.
	 * @throws ServiceException
	 * @throws LogicException
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public void excluirCaracteristica(CaracteristicaDTO caracteristica) throws ServiceException, LogicException, Exception;

	/**
	 * Consulta Características com seus respectivos valores.
	 * 
	 * @param idTipoItemConfiguracao
	 * @param idBaseItemConfiguracao
	 * @return Collection - Coleção de Características.
	 * @throws LogicException
	 * @throws ServiceException
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public Collection<CaracteristicaDTO> consultarCaracteristicasComValores(Integer idTipoItemConfiguracao, Integer idBaseItemConfiguracao) throws LogicException,
			ServiceException, Exception;

	/**
	 * Consulta Características com seus respectivos valores.
	 * 
	 * @param idTipoItemConfiguracao
	 * @param idBaseItemConfiguracao
	 * @return Collection - Coleção de Características.
	 * @throws LogicException
	 * @throws ServiceException
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public Collection<CaracteristicaDTO> consultarCaracteristicasComValoresItemConfiguracao(Integer idTipoItemConfiguracao, Integer idItemConfiguracao) throws LogicException,
			ServiceException, Exception;
	
	/**
	 * Consulta Características com seus respectivos valores.
	 * 
	 * @param idTipoItemConfiguracao
	 * @param idBaseItemConfiguracao
	 * @param arrCaracteristicas
	 * @return Collection - Coleção de Características.
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
	 * @return true - existe; false - não existe;
	 * @throws PersistenceException
	 * @author Thays.araujo
	 */
	public boolean verificarSeCaracteristicaExiste(CaracteristicaDTO caracteristica) throws PersistenceException;

	
	/**
	 * Consulta Características Ativas com array.
	 * 
	 * @param id
	 * @return Collection - Coleção de Características.
	 * @throws ServiceException
	 * @author flavio.santana
	 */
	public Collection<CaracteristicaDTO> consultarCaracteristicasAtivas(Integer idTipoItemConfiguracao, String[] arrCaracteristicas) throws ServiceException;

}
