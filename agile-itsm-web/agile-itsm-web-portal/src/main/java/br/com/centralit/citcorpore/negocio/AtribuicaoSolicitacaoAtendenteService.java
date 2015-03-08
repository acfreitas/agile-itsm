package br.com.centralit.citcorpore.negocio;

import java.sql.Date;
import java.util.List;

import br.com.centralit.citcorpore.bean.AtribuicaoSolicitacaoAtendenteDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

/**
 * Serviços para {@link AtribuicaoSolicitacaoAtendenteDTO}
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 27/10/2014
 *
 */
public interface AtribuicaoSolicitacaoAtendenteService extends CrudService {

    /**
     * Verifica a existência de uma atribuição para o usuário para um determinando solicitação
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
     * Consultra {@link AtribuicaoSolicitacaoAtendenteDTO} de acordo a atribuição passada como argumento
     *
     * @param atribuicao
     *            atribuição a ser consultada
     * @return {@link List} lista contendo as solicitações de acordo com a filtrada. Vazia, caso não encontre nenhuma
     * @throws ServiceException
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @date 20/11/2014
     */
    List<AtribuicaoSolicitacaoAtendenteDTO> findByIDUsuarioAndIDSolicitacao(final AtribuicaoSolicitacaoAtendenteDTO atribuicao) throws ServiceException;

    /**
     * Cria uma nova {@link AtribuicaoSolicitacaoAtendenteDTO}
     *
     * @param atribuicao
     *            atribuição a ser criada
     * @return {@link AtribuicaoSolicitacaoAtendenteDTO} criada
     * @throws ServiceException
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 12/12/2014
     */
    AtribuicaoSolicitacaoAtendenteDTO criarAtribuicao(final AtribuicaoSolicitacaoAtendenteDTO atribuicao) throws ServiceException;

    /**
     * Cria uma lista de atribuição de solicitação e envia mensage push para o usuário
     *
     * @param atribuicoes
     *            atribuição a ser criada
     * @param dataExecucao
     *            data para execução da solicitação
     * @param connection
     *            "conexão" no mobile, que é a URI acessada
     * @return atribuições criadas
     * @throws Exception
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 15/11/2014
     */
    List<AtribuicaoSolicitacaoAtendenteDTO> criaAtribuicaoEmBatch(final List<AtribuicaoSolicitacaoAtendenteDTO> atribuicoes, final Date dataExecucao, final String connection)
            throws ServiceException;

}
