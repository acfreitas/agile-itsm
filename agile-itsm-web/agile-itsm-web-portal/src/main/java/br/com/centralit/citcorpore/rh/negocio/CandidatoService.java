package br.com.centralit.citcorpore.rh.negocio;

import java.util.Collection;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.rh.bean.CandidatoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

/**
 * @author thiago.borges
 *
 */
@SuppressWarnings("rawtypes")
public interface CandidatoService extends CrudService {

    /**
     * Exclui candidato.
     *
     * @param model
     * @param document
     * @throws ServiceException
     * @throws Exception
     */
    void deletarCandidato(final IDto model, final DocumentHTML document) throws ServiceException, Exception;

    /**
     * @param obj
     * @return
     * @throws Exception
     * @author Thiago.Borges
     */
    boolean consultarCandidatosAtivos(final CandidatoDTO obj) throws Exception;

    Collection<CandidatoDTO> seCandidatoJaCadastrado(final CandidatoDTO candidatoDTO) throws Exception;

    Collection<CandidatoDTO> listarAtivos() throws Exception;

    Collection findByNome(final String nome) throws Exception;

    Collection findByCpf(final String nome) throws Exception;

    Collection findListTodosCandidatos() throws Exception;

    Collection findByIdCandidatoJoinIdHistorico(final Integer idCandidato) throws Exception;

    Collection recuperaColecaoCandidatos(final CandidatoDTO candidatoDto, final Integer pgAtual, final Integer qtdPaginacao) throws Exception;

    Integer calculaTotalPaginas(final Integer itensPorPagina, final CandidatoDTO candidatoDto) throws Exception;

    // CandidatoDTO create(CandidatoDTO candidatoDto) throws Exception;
    Integer restoreIdCandidato(final Integer idCurriculo) throws Exception;

    CandidatoDTO findByEmail(final String nome) throws Exception;

    CandidatoDTO restoreByCpf(final String nome) throws Exception;

    void updateNotNull(final IDto obj) throws Exception;

    CandidatoDTO findByHashID(final String nome) throws Exception;

    Integer findByCpfCurriculo(final String cpf) throws Exception;

    CandidatoDTO restoreByIdEmpregado(final Integer idEmpregado) throws Exception;

}
