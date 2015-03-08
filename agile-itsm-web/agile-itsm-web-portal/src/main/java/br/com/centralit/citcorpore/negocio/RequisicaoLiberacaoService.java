package br.com.centralit.citcorpore.negocio;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import br.com.centralit.bpm.dto.FluxoDTO;
import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.PesquisaRequisicaoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoQuestionarioDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudService;

public interface RequisicaoLiberacaoService extends CrudService {

    Collection findByIdSolicitante(Integer parm) throws Exception;

    List<RequisicaoLiberacaoDTO> listLiberacoes() throws Exception;

    RequisicaoLiberacaoDTO restoreAll(Integer idRequisicaoLiberacao) throws Exception;

    void reativa(UsuarioDTO usuarioDto, RequisicaoLiberacaoDTO requisicaoLiberacaoDTO) throws Exception;

    void gravaInformacoesGED(RequisicaoLiberacaoDTO requisicaoLiberacaoDTO, TransactionControler tc) throws Exception;

    List<RequisicaoLiberacaoDTO> findByConhecimento(BaseConhecimentoDTO baseConhecimentoDto) throws Exception;

    void updateSimples(IDto model);

    /**
     * suspende a requisição mudança
     *
     * @param usuarioDto
     * @param solicitacaoServicoDto
     * @throws Exception
     * @author maycon.fernandes
     */
    void suspende(UsuarioDTO usuarioDto, RequisicaoLiberacaoDTO requisicaoLiberacaoDTO) throws Exception;

    List<RequisicaoLiberacaoItemConfiguracaoDTO> listItensRelacionadosRequisicaoLiberacao(
            RequisicaoLiberacaoDTO requisicaoLiberacaoDTO) throws ServiceException, Exception;

    /**
     * @param problemaDto
     * @return Template Liberacao
     * @throws Exception
     */
    String getUrlInformacoesComplementares(RequisicaoLiberacaoDTO requisicaoLiberacaoDTO) throws Exception;

    /**
     * @param problemaDto
     * @return Template Liberacao
     * @throws Exception
     */
    String getUrlInformacoesQuestionario(RequisicaoLiberacaoDTO requisicaoLiberacaoDTO) throws Exception;

    /**
     * @param RequisicaoQuestionarioDTO
     * @throws Exception
     */
    void atualizaInformacoesQuestionario(RequisicaoQuestionarioDTO requisicaoQuestionarioDTO) throws Exception;

    /**
     * O metodo atualiza somente os campos setados os campos anteriores continuará intacto.
     *
     * @param RequisicaoLiberacaoDTO
     * @throws Exception
     */
    void updateLiberacaoAprovada(IDto obj) throws Exception;

    Collection<RequisicaoLiberacaoDTO> listaRequisicaoLiberacaoPorCriterios(
            PesquisaRequisicaoLiberacaoDTO pesquisaRequisicaoLiberacaoDto) throws Exception;

    FluxoDTO recuperaFluxo(RequisicaoLiberacaoDTO requisicaoLiberacaoDto) throws Exception;

    void reabre(UsuarioDTO usuarioDto, RequisicaoLiberacaoDTO requisicaoLiberacaoDto) throws Exception;

    /**
     * Retorna uma lista de Liberaçoes que estejam relacionada a um determinado item de cofiguração.
     *
     * @param Integer
     * @return List<RequisicaoLiberacaoDTO>
     * @throws Exception
     */
    List<RequisicaoLiberacaoDTO> listLiberacaoByItemConfiugracao(Integer idItemConfiguracao) throws Exception;

    Timestamp MontardataHoraAgendamentoInicial(RequisicaoLiberacaoDTO requisicaoLiberacaoDto);

    Timestamp MontardataHoraAgendamentoFinal(RequisicaoLiberacaoDTO requisicaoLiberacaoDto);

    void calculaTempoAtraso(RequisicaoLiberacaoDTO requisicaoLiberacaoDto) throws Exception;

    Boolean seHoraInicialMenorQAtual(RequisicaoLiberacaoDTO requisicaoLiberacaoDto);

    Boolean seHoraFinalMenorQAtual(RequisicaoLiberacaoDTO requisicaoLiberacaoDto);

    Boolean seHoraFinalMenorQHoraInicial(RequisicaoLiberacaoDTO requisicaoLiberacaoDto);

    Boolean verificaPermissaoGrupoCancelar(Integer idTipoLiberacao, Integer idGrupo) throws ServiceException, Exception;

}
