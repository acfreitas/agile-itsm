package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.UnidadeDTO;
import br.com.centralit.citcorpore.bean.UnidadesAccServicosDTO;
import br.com.centralit.citcorpore.util.Arvore;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

@SuppressWarnings("rawtypes")
public interface UnidadeService extends CrudService {

    Collection findByIdUnidade() throws Exception;

    Collection findById(final Integer idUnidade) throws Exception;

    Collection findByIdEmpregado() throws Exception;

    Collection listHierarquia() throws Exception;

    Collection listHierarquiaMultiContratos(final Integer idContrato) throws Exception;

    boolean jaExisteUnidadeComMesmoNome(final UnidadeDTO unidade);

    /**
     * Metodo pra fazer a exclus�o l�gica de Unidade
     *
     * @param model
     * @param document
     * @throws ServiceException
     * @throws Exception
     * @author thays.araujo
     */
    void deletarUnidade(final IDto model, final DocumentHTML document, final HttpServletRequest request) throws ServiceException, Exception;

    /**
     * Restaura GRID de Servi�os.
     *
     * @author rodrigo.oliveira
     */
    void restaurarGridServicos(final DocumentHTML document, final Collection<UnidadesAccServicosDTO> servicos);

    Collection listarAtivasPorContrato(final Integer idContrato);

    Collection<UnidadeDTO> listUnidadePorContrato(final Integer idcontrato) throws Exception;

    Collection<UnidadeDTO> findByNomeEcontrato(final String nome, final Integer idContrato, final Integer limite) throws Exception;

    /**
	 * @author euler.ramos
	 * � importante que esta lista se mantenha ordenando os n�s na hierarquia, seguindo a oirenta��o de n�s pai para os n�s filhos
	 * @param unidadeDTO
	 * @return
	 * @throws Exception
	 */
    List<UnidadeDTO> recuperaHierarquiaUnidade(final UnidadeDTO unidadeDTO) throws Exception;

    String retornaNomeUnidadeByID(final Integer id) throws Exception;

    Collection findByIdEcontrato(final Integer idUnidade, final Integer idContrato) throws Exception;

    /**
     * Atualiza as coordenadas geogr�ficas de uma unidade
     *
     * @param locale
     *            locale para recupera��o de mensagem internacionalizada
     * @param unidade
     *            unidade contendo os valores das coordenadas para serem atualizadas
     * @throws ServiceException
     *             caso os atributos estejam nulos ou n�o seja valores v�lidos
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 22/09/2014
     */
    void updateCoordenadas(final String locale, final UnidadeDTO unidade) throws Exception;

    /**
	 * @author euler.ramos
	 * @param nome - Nome da Unidade - Se for Null ou Vazio o sistema trar� todas as unidades independentemente do nome 
	 * @param idContrato - Para filtrar apenas as unidades vinculadas ao contrato - Se for Null ou Vazio o sistema n�o filtrar� por contrato
	 * @param idUnidadeColaborador - Trazer apenas a �rvore de unidades referente ao colaborador, conforme a hierarquia
	 * @param tipoHierarquia - se haver� restri��o dos n�s da �rvore ou como ser� a orienta��o dos n�s retornados 
	 * @param limite - Limite do n�mero de registros que podem ser retornados na pesquisa do banco; Para n�o limitar a consulta informe Null ou 0 (Zero);  
	 * @return �rvore das unidades, de acordo com a pesquisa e configura��o dos par�metros
	 * @throws ServiceException
	 * @throws Exception
	 */
	Arvore obtemArvoreUnidades(String nome, Integer idContrato, Integer idUnidadeColaborador, String tipoHierarquia, Integer limite) throws ServiceException, Exception;

}
