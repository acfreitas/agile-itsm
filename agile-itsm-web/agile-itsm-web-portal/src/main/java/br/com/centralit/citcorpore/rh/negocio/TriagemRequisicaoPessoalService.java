package br.com.centralit.citcorpore.rh.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.rh.bean.CurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoPessoalDTO;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.service.CrudService;

public interface TriagemRequisicaoPessoalService extends CrudService {

    Collection findByIdSolicitacaoServico(final Integer parm) throws Exception;

    void deleteByIdSolicitacaoServico(final Integer parm) throws Exception;

    Collection<CurriculoDTO> sugereCurriculos(final RequisicaoPessoalDTO requisicaoPessoalDto) throws Exception;

    Collection<CurriculoDTO> triagemManual() throws Exception;

    Collection<CurriculoDTO> triagemManualPorCriterios(final RequisicaoPessoalDTO requisicaoPessoalDTO, final String idsCurriculosTriados, final Integer pagSelecionada,
            final Integer itensPorPagina) throws Exception;

    Collection findByIdSolicitacaoServicoAndIdTarefa(final Integer idSolicitacaoServico, final Integer idTarefa) throws Exception;

    boolean candidatoParticipaProcessoSelecao(final Integer idCurriculo, final Integer idSolicitacaoServico) throws PersistenceException;

}
