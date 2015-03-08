package br.com.centralit.citcorpore.rh.negocio;

import br.com.centralit.citcorpore.rh.bean.HistManualFuncaoDTO;
import br.com.centralit.citcorpore.rh.bean.ManualFuncaoDTO;
import br.com.citframework.service.CrudService;

public interface ManualFuncaoService extends CrudService {

    void createHistAtribuicaoResponsabilidade(final ManualFuncaoDTO manualFuncalDto, final Integer idHistManualFuncao) throws Exception;

    void createHistPerspectivaComportamental(final ManualFuncaoDTO manualFuncao, final Integer idHistManualFuncao) throws Exception;

    void createHistManualCertificacao(final ManualFuncaoDTO manualFuncalDto, final Integer idHistManualFuncao) throws Exception;

    void createHistManualCurso(final ManualFuncaoDTO manualFuncalDto, final Integer idHistManualFuncao) throws Exception;

    void createHistManualCompetenciaTecnica(final ManualFuncaoDTO manualFuncalDto, final Integer idHistManualFuncao) throws Exception;

    HistManualFuncaoDTO createHistManualFuncao(final ManualFuncaoDTO manualFuncalDto) throws Exception;

}
