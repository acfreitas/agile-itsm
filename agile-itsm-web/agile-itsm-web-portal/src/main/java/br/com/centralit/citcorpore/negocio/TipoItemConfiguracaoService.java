/**
 * CentralIT - CITSmart.
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.CaracteristicaDTO;
import br.com.centralit.citcorpore.bean.TipoItemConfiguracaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

/**
 * Service de TipoItemConfiguracao.
 * 
 * @author valdoilo.damasceno
 * 
 */
public interface TipoItemConfiguracaoService extends CrudService {

	/**
	 * Cria novo TipoItemConfiguracao.
	 * 
	 * @param tipo
	 * @param request
	 * @return IDto
	 * @throws LogicException
	 * @throws ServiceException
	 * @author valdoilo.damasceno
	 */
	public IDto create(IDto tipo, HttpServletRequest request) throws LogicException, ServiceException;

	/**
	 * Restaura GRID de Caracter�stica.
	 * 
	 * @param document
	 *            - DocumentHTML
	 * @param caracteristicas
	 *            - Collection
	 * @author valdoilo.damasceno
	 */
	public void restaurarGridCaracteristicas(DocumentHTML document, Collection<CaracteristicaDTO> caracteristicas);

	/**
	 * Restura GRID de caracter�sticas do Item de Configura��o Filho selecionado na tela de Patrim�nio.
	 * 
	 * @param document
	 *            - DocumentHTML
	 * @param caracteristicas
	 *            -Collection
	 * @author valdoilo.damasceno
	 */
	public void restaurarGridCaracteristicasItemConfiguracaoFilho(DocumentHTML document, Collection<CaracteristicaDTO> caracteristicas);

	/**
	 * Verifica se Tipo Item Configura��o informada existe.
	 * 
	 * @param tipoItemConfiguracao
	 * @return
	 * @throws PersistenceException
	 * @author Thays.araujo
	 */
	public boolean verificarSeTipoItemConfiguracaoExiste(TipoItemConfiguracaoDTO tipoItemConfiguracao) throws PersistenceException;
	
	/**
	 * Verifica se o Tipo Item Configura��o est� associado a algum Item Configura��o
	 * @param tipoItemConfiguracao
	 * @return
	 * @throws PersistenceException
	 * @author thyen.chang 
	 */
	public boolean verificaAssociacaoItemConfiguracao(TipoItemConfiguracaoDTO tipoItemConfiguracao) throws PersistenceException;

}
