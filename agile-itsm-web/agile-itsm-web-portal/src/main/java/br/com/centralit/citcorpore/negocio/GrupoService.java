package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

@SuppressWarnings("rawtypes")
public interface GrupoService extends CrudService {

    /**
     * Retorna lista de GRUPO que ainda não estï¿½o associados a EMPREGADOS.
     *
     * @return
     * @throws Exception
     */
    Collection listaGrupoEmpregado() throws Exception;

    /**
     * Retorna lista de GRUPO que ainda não estï¿½o associados a USUï¿½RIO.
     *
     * @return
     * @throws Exception
     */
    Collection listaGrupoUsuario() throws Exception;

    Collection getGruposByPessoa(Integer idEmpregado) throws LogicException, ServiceException;

    Collection getGruposByEmpregado(Integer idEmpregado) throws LogicException, ServiceException;

    Collection<GrupoDTO> listGruposServiceDesk() throws Exception;

    IDto create(IDto model, HttpServletRequest request) throws ServiceException, LogicException;

    void update(IDto grupoDto, HttpServletRequest request) throws ServiceException, LogicException;

    void delete(IDto model, DocumentHTML document) throws ServiceException, LogicException;

    Collection<GrupoDTO> listGruposPorUsuario(int idUsuario);

    Collection findGruposAtivos();

    /**
     * Verifica se grupo informado já existe;
     *
     * @param grupo
     * @return true - existe; false - não existe;
     * @throws PersistenceException
     */
    boolean verificarSeGrupoExiste(GrupoDTO grupo) throws PersistenceException;

    Collection<GrupoDTO> listGrupoByIdContrato(Integer idContrato) throws Exception;

    Collection<GrupoDTO> listGrupoAtivosByIdContrato(Integer idContrato) throws Exception;

    Collection<GrupoDTO> listGruposServiceDeskByIdContrato(Integer idContratoParm) throws Exception;

    /**
     * Retorna Lista de Grupos do Empregado.
     *
     * @param idEmpregado
     *            - Identificador do Empregado.
     * @return Collection<GrupoDTO> - Lista de Grupos.
     * @throws Exception
     */
    Collection<GrupoDTO> getGruposByIdEmpregado(Integer idEmpregado) throws Exception;

    Collection listarGruposAtivos() throws Exception;

    /**
     * Retorna lista de e-mails que estão cadastrados para receber notificação
     *
     * @param idGrupo
     * @return
     * @throws Exception
     */
    Collection<String> listarEmailsPorGrupo(Integer idGrupo) throws Exception;

    Collection<String> listarPessoasEmailPorGrupo(Integer idGrupo) throws Exception;

    /**
     * Retorna lista de GRUPO que são do Comite de Controle de Mudança
     *
     * @author Riubbe Oliveira
     *
     */
    Collection<GrupoDTO> listGruposComite() throws Exception;

    /**
     * Retorna lista de GRUPO que não são do Comite de Controle de Mudança
     *
     * @author Riubbe Oliveira
     *
     */
    Collection<GrupoDTO> listGruposNaoComite() throws Exception;

    /**
     * @param idGrupo
     * @return
     * @throws Exception
     */
    GrupoDTO listGrupoById(Integer idGrupo) throws Exception;

    Collection<GrupoDTO> listAllGrupos() throws Exception;

    /**
     * Retorna uma lista de grupos ativos
     *
     * @return
     * @throws Exception
     * @author thays.araujo
     */
    Collection<GrupoDTO> listaGruposAtivos() throws Exception;

    Collection<GrupoDTO> listaGrupoEmpregado(Integer idEmpregado) throws Exception;

}
