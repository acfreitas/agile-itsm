package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.List;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.ExternalConnectionDTO;
import br.com.centralit.citcorpore.bean.ImportManagerDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

/**
 * @author thiago.borges
 *
 */
public interface ExternalConnectionService extends CrudService {

    Collection getTables(final Integer idExternalConnection) throws Exception;

    Collection getLocalTables() throws Exception;

    Collection getFieldsTable(final Integer idExternalConnection, final String tableName) throws Exception;

    Collection getFieldsLocalTable(final String tableName) throws Exception;

    void processImport(final ImportManagerDTO importManagerDTO, final List colMatrizTratada) throws Exception;

    /**
     * Exclui Conexao
     *
     * @param model
     * @param document
     * @throws ServiceException
     * @throws Exception
     */
    public void deletarConexao(final IDto model, final DocumentHTML document) throws ServiceException, Exception;

    /**
     * Consultar Conexoes Ativas
     *
     * @param obj
     * @return
     * @throws Exception
     * @author Thiago.Borges
     */
    boolean consultarConexoesAtivas(final ExternalConnectionDTO obj) throws Exception;

    Collection<ExternalConnectionDTO> seConexaoJaCadastrada(final ExternalConnectionDTO conexoesDTO) throws Exception;

    Collection<ExternalConnectionDTO> listarAtivas() throws Exception;

}
