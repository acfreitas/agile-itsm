package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.GerenciamentoRotasDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.bean.result.GerenciamentoRotasResultDTO;
import br.com.centralit.citcorpore.util.Enumerados.TipoSolicitacaoServico;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.core.Page;
import br.com.citframework.integracao.core.Pageable;
import br.com.citframework.service.CrudService;

/**
 * Servi�os para as consultas de {@link SolicitacaoServicoDTO} realizadas pelo mobile V2
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 09/10/2014
 *
 */
public interface SolicitacaoServicoServiceForMobileV2 extends CrudService {

    /**
     * Lista {@link SolicitacaoServicoDTO} de acordo com a mais recente presente no APP
     *
     * @param newestNumber
     *            n�mero de solicita��o mais recente presente no mobile
     * @param usuario
     *            usu�rio que est� solicitando a listagem
     * @param tiposSolicitacao
     *            tipos de solicita��o a serem consideradas na listagem
     * @param aprovacao
     *            identificador de se a solicita��o est� ou n�o em aprova��o
     * @return {@link Page} contendo os registros resultantes da consulta
     * @throws ServiceException
     */
    Page<SolicitacaoServicoDTO> listNewest(final Integer newestNumber, final UsuarioDTO usuario, final TipoSolicitacaoServico[] tiposSolicitacao, final String aprovacao)
            throws ServiceException;

    /**
     * Lista {@link SolicitacaoServicoDTO} de acordo com a mais antiga presente no APP
     *
     * @param oldestNumber
     *            n�mero de solicita��o mais antiga presente no mobile
     * @param usuario
     *            usu�rio que est� solicitando a listagem
     * @param tiposSolicitacao
     *            tipos de solicita��o a serem consideradas na listagem
     * @param aprovacao
     *            identificador de se a solicita��o est� ou n�o em aprova��o
     * @return {@link Page} contendo os registros resultantes da consulta
     * @throws ServiceException
     */
    Page<SolicitacaoServicoDTO> listOldest(final Integer oldestNumber, final UsuarioDTO usuario, final TipoSolicitacaoServico[] tiposSolicitacao, final String aprovacao)
            throws ServiceException;

    /**
     * Lista {@link SolicitacaoServicoDTO} de acordo com o posicionamento geogr�fico do usu�rio
     *
     * @param latitude
     *            latitude do posicionamento do usu�rio
     * @param longitude
     *            longitude do posicionamento do usu�rio
     * @param usuario
     *            usu�rio que est� solicitando a listagem
     * @param tiposSolicitacao
     *            tipos de solicita��o a serem consideradas na listagem
     * @param aprovacao
     *            identificador de se a solicita��o est� ou n�o em aprova��o
     * @param pageable
     *            informa��o sobre pagina��o
     * @return {@link Page} contendo os registros resultantes da consulta
     * @throws ServiceException
     */
    Page<SolicitacaoServicoDTO> listByCoordinates(final Double latitude, final Double longitude, final UsuarioDTO usuario, final TipoSolicitacaoServico[] tiposSolicitacao,
            final String aprovacao, final Pageable pageable) throws ServiceException;

    /**
     * Lista solicita��es de servi�os para roteiriza��o
     *
     * @param filter
     *            informa��es para filtro
     * @param pageable
     *            informa��es para pagina��o
     * @return {@link Page} contendo os registros resultantes da consulta
     * @throws ServiceException
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @date 17/11/2014
     */
    Page<GerenciamentoRotasResultDTO> listSolicitacoesParaRoteirizacao(final GerenciamentoRotasDTO filter, final Pageable pageable) throws ServiceException;

    Page<SolicitacaoServicoDTO> listNotificationByNumberAndUser(final Integer number, final UsuarioDTO user) throws ServiceException;

}
