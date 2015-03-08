package br.com.centralit.citcorpore.negocio;

import java.sql.Date;
import java.util.List;

import br.com.centralit.citcorpore.bean.AtribuicaoSolicitacaoAtendenteDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

/**
 * Servi�os para {@link AtribuicaoSolicitacaoAtendenteDTO}
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 27/10/2014
 *
 */
public interface AtribuicaoSolicitacaoAtendenteService extends CrudService {

    /**
     * Verifica a exist�ncia de uma atribui��o para o usu�rio para um determinando solicita��o
     * 
     * @param taskId
     * @param user
     * @return
     * @throws Exception
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @date 20/11/2014
     */
    boolean existeAtribuicao(final Integer taskId, final UsuarioDTO user) throws Exception;

    /**
     * Consultra {@link AtribuicaoSolicitacaoAtendenteDTO} de acordo a atribui��o passada como argumento
     *
     * @param atribuicao
     *            atribui��o a ser consultada
     * @return {@link List} lista contendo as solicita��es de acordo com a filtrada. Vazia, caso n�o encontre nenhuma
     * @throws ServiceException
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @date 20/11/2014
     */
    List<AtribuicaoSolicitacaoAtendenteDTO> findByIDUsuarioAndIDSolicitacao(final AtribuicaoSolicitacaoAtendenteDTO atribuicao) throws ServiceException;

    /**
     * Cria uma nova {@link AtribuicaoSolicitacaoAtendenteDTO}
     *
     * @param atribuicao
     *            atribui��o a ser criada
     * @return {@link AtribuicaoSolicitacaoAtendenteDTO} criada
     * @throws ServiceException
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 12/12/2014
     */
    AtribuicaoSolicitacaoAtendenteDTO criarAtribuicao(final AtribuicaoSolicitacaoAtendenteDTO atribuicao) throws ServiceException;

    /**
     * Cria uma lista de atribui��o de solicita��o e envia mensage push para o usu�rio
     *
     * @param atribuicoes
     *            atribui��o a ser criada
     * @param dataExecucao
     *            data para execu��o da solicita��o
     * @param connection
     *            "conex�o" no mobile, que � a URI acessada
     * @return atribui��es criadas
     * @throws Exception
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 15/11/2014
     */
    List<AtribuicaoSolicitacaoAtendenteDTO> criaAtribuicaoEmBatch(final List<AtribuicaoSolicitacaoAtendenteDTO> atribuicoes, final Date dataExecucao, final String connection)
            throws ServiceException;

}
