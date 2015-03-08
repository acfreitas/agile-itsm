package br.com.centralit.citcorpore.rh.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.rh.bean.EntrevistaCandidatoDTO;
import br.com.centralit.citcorpore.rh.bean.TriagemRequisicaoPessoalDTO;
import br.com.citframework.service.CrudService;

@SuppressWarnings("rawtypes")
public interface EntrevistaCandidatoService extends CrudService {

    EntrevistaCandidatoDTO findByIdTriagemAndIdCurriculo(final Integer idTriagem, final Integer idCurriculo) throws Exception;

    Collection listCurriculosAprovadosPorOrdemMaiorNota(final Integer idTriagem) throws Exception;

    Boolean seCandidatoAprovado(final TriagemRequisicaoPessoalDTO triagemRequisicaoPessoalDTO) throws Exception;

    void inserirRegistroHistorico(final Integer idCurriculo, final Integer idEntrevistador, final String titulo, final Integer idSolicitacao, final Double notaAvaliacao,
            final String resultado) throws Exception;

}
