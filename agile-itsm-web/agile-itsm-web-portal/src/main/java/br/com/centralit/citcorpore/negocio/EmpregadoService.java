package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.UnidadeDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

/**
 * @author thays.araujo e daniel
 *
 */
@SuppressWarnings("rawtypes")
public interface EmpregadoService extends CrudService {

    void updateNotNull(final IDto dto);

    EmpregadoDTO restoreEmpregadosAtivosById(final Integer idEmpregado);

    EmpregadoDTO restoreEmpregadoSeAtivo(final EmpregadoDTO empregadoDto);

    EmpregadoDTO restoreByNomeEmpregado(final EmpregadoDTO empregadoDTO) throws Exception;

    void delete(final EmpregadoDTO empregado) throws ServiceException, Exception;

    /**
     * Restaura o idEmpregado
     *
     * @param idEmpregado
     * @return Restaura idEmpregado
     * @throws Exception
     */
    EmpregadoDTO restoreByIdEmpregado(final Integer idEmpregado) throws Exception;

    /**
     * Calcula Custo da Hora e o Custo do Mï¿½s do empregado.
     *
     * @param empregado
     * @return EmpregadoDTO
     * @throws ServiceException
     * @throws LogicException
     * @author thays.araujo
     */
    EmpregadoDTO calcularCustos(final EmpregadoDTO empregado) throws ServiceException, LogicException;

    /**
     * Lista empregados.
     *
     * @param idGrupo
     *            Identificador ï¿½nicio do grupo.
     * @return Collection<EmpregadoDTO>
     * @throws ServiceException
     * @author daniel
     */
    Collection<EmpregadoDTO> listEmpregadosByIdGrupo(final Integer idGrupo) throws ServiceException;

    /**
     * @param idUnidade
     * @return Collection<EmpregadoDTO>
     * @throws ServiceException
     * @author daniel
     */
    Collection<EmpregadoDTO> listEmpregadosByIdUnidade(final Integer idUnidade) throws ServiceException;

    Collection<EmpregadoDTO> listEmpregadosGrupo(final Integer idEmpregado, final Integer idGrupo) throws Exception;

    Collection<EmpregadoDTO> listEmailContrato(final Integer idContrato) throws Exception;

    Collection<EmpregadoDTO> listEmailContrato() throws Exception;

    Collection listEmpregadoContrato(final Integer idContrato) throws Exception;

    EmpregadoDTO listEmpregadoContrato(final Integer idContrato, final String email) throws Exception;

    EmpregadoDTO listEmpregadoContrato(final String email) throws Exception;

    Collection<EmpregadoDTO> listEmpregadoByContratoAndUnidadeAndEmpregados(Integer idContrato, Integer idUnidade, Integer[] idEmpregados, UsuarioDTO usuario, 
    		ArrayList<UnidadeDTO> listaUnidadeContrato) throws Exception;

    /**
     * Faz a exclusão logica de empregado.
     *
     * @param model
     * @throws ServiceException
     * @throws Exception
     * @author thays.araujo
     */
    void deleteEmpregado(final IDto model) throws ServiceException, Exception;

    /**
     *
     * @param idEmpregado
     * @return Integer - idUnidade
     * @throws Exception
     * @author rodrigo.oliveira
     */
    Integer consultaUnidadeDoEmpregado(final Integer idEmpregado) throws Exception;

    /**
     * Retorna uma lista de email de empregados que receberão notificações de base de conhecimento
     *
     * @param idConhecimento
     * @return
     * @throws Exception
     * @author cleon.junior
     */
    Collection listarEmailsNotificacoesConhecimento(final Integer idConhecimento) throws Exception;

    /**
     * Retorna verdadeiro para ativo ou falso para inativo de acordo com nome do empregado passado.
     *
     * @param obj
     * @return
     * @throws Exception
     * @author thays.araujo
     */
    boolean verificarEmpregadosAtivos(final EmpregadoDTO obj) throws Exception;

    Collection findByNomeEmpregado(final EmpregadoDTO empregadoDTO) throws Exception;

    /**
     * Busca empregados pelo nome e id do grupo
     *
     * @param nomeEmpregado
     *            nome do empregado a ser buscado
     * @param idGrupo
     *            id do grupo em que o empregado deve estar
     * @return {@link Collection}
     * @throws Exception
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 31/10/2014
     */
    List<EmpregadoDTO> findByNomeEmpregadoAndGrupo(final String nomeEmpregado, final Integer idGrupo) throws Exception;

    EmpregadoDTO restoreByEmail(final String email) throws Exception;

    Collection listarIdEmpregados(final Integer limit, final Integer offset) throws Exception;

    EmpregadoDTO restauraTodos(final EmpregadoDTO param) throws Exception;

    /**
     * Retorna EmpregadoDTO (idEmpregado e Nome). Esta consulta é a mesma da LOOKUP_SOLICITANTE_CONTRATO.
     *
     * @param nome
     *            - Nome do Empregado (Campo Nome da tabela Empregados)
     * @param idContrato
     *            - Identificador do Contrato.
     * @return Collection<EmpregadoDTO> - Lista de Empregados com Id e Nome.
     * @throws Exception
     * @author valdoilo.damasceno 29.10.2013
     */
    Collection<EmpregadoDTO> findSolicitanteByNomeAndIdContratoAndIdUnidade(String nome, Integer contrato, Integer unidade) throws Exception;

    /**
     * Pesquisa Empregado por Telefone ou Ramal. Retorna o primeiro Empregado encontrado para o Ramal ou Telefone
     * informado. <<< ATENÇÃO >> o parâmetro Telefone antes de ser enviado para o método, deve ser tratado com o Método
     * mascaraProcuraSql() da Classe Utilitária br.com.centralit.citcorpore.util.Telefone.java;
     *
     * @param telefone
     * @return EmpregadoDTO
     * @author valdoilo.damasceno
     */
    EmpregadoDTO findByTelefoneOrRamal(final String telefone) throws Exception;

    /**
     * Restaura o EmpregadoDTO com o ID do Contrato Padrão (Primeiro contrato encontrado para o Empregado) a partir do
     * ID Empregado informado.
     *
     * @param idEmpregado
     * @return EmpregadoDTO com IDContrato
     * @author valdoilo.damasceno
     */
    EmpregadoDTO restoreEmpregadoWithIdContratoPadraoByIdEmpregado(final Integer idEmpregado) throws Exception;

    /**
     * Restaura o EmpregadoDTO com o Nome cargo a partir do ID Empregado informado.
     *
     * @param idEmpregado
     * @return EmpregadoDTO com NomeCargo
     * @author maycon.fernandes
     */
    EmpregadoDTO restoreEmpregadoAndNomeCargoByIdEmpegado(final Integer idEmpregado) throws Exception;

    /**
     * Consulta o nome dos empregados com nome diferente de Administrador e data fim diferente de zero
     *
     * @param nomeEmpregado
     * @return Collection<EmpregadoDTO> Com empregados com nome diferente de Administrador e data fim diferente de zero
     * @throws Exception
     */
    Collection<EmpregadoDTO> consultarNomeEmpregadoSemAdministrador(final String nomeEmpregado) throws Exception;

    EmpregadoDTO restoreByCPF(final String cpf) throws Exception;

    Collection<EmpregadoDTO> consultarNomeNaoEmpregado(final String nome) throws Exception;

    /**
     * Restaura Empregado pelo ID do Usuário.
     * 
     * @param idUsuario
     *            - Identificador do Usuário.
     * @return EmpregadoDTO - Empregado do Usuário.
     * @author valdoilo.damasceno
     * @since 16.06.2014
     */
    EmpregadoDTO restoreByIdUsuario(Integer idUsuario);

}
