/**
 * CentralIT - CITSmart
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.HistoricoMudancaDTO;
import br.com.centralit.citcorpore.bean.PesquisaRequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudService;

@SuppressWarnings("rawtypes")
public interface RequisicaoMudancaService extends CrudService {

    /**
     * Retorna Requisições de Mudança associados ao conhecimento informado.
     *
     * @param baseConhecimentoDto
     * @return Collection
     * @throws ServiceException
     * @throws LogicException
     * @author Vadoilo Damasceno
     */
    Collection findByConhecimento(final BaseConhecimentoDTO baseConhecimentoDto) throws ServiceException, LogicException;

    ServicoContratoDTO findByIdContratoAndIdServico(final Integer idContrato, final Integer idServico) throws Exception;

    Collection findBySolictacaoServico(final RequisicaoMudancaDTO bean) throws ServiceException, LogicException;

    void gravaInformacoesGED(final Collection colArquivosUpload, final int idEmpresa, final RequisicaoMudancaDTO requisicaoMudancaDto, final TransactionControler tc)
            throws Exception;

    void gravaPlanoDeReversaoGED(final RequisicaoMudancaDTO requisicaomudacaDTO, final TransactionControler tc, final HistoricoMudancaDTO historicoMudancaDTO) throws Exception;

    Collection<RequisicaoMudancaDTO> listaMudancaPorBaseConhecimento(final RequisicaoMudancaDTO mudanca) throws Exception;

    Collection listaQuantidadeMudancaPorImpacto(final RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception;

    Collection listaQuantidadeMudancaPorPeriodo(final RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception;

    Collection listaQuantidadeMudancaPorProprietario(final RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception;

    Collection listaQuantidadeMudancaPorSolicitante(final RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception;

    Collection listaQuantidadeMudancaPorStatus(final RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception;

    Collection listaQuantidadeMudancaPorUrgencia(final RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception;

    Collection listaQuantidadeSemAprovacaoPorPeriodo(final RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception;

    List<RequisicaoMudancaDTO> listMudancaByIdItemConfiguracao(final Integer idItemConfiguracao) throws Exception;

    String getUrlInformacoesComplementares(final RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception;

    void updateNotNull(final IDto obj) throws Exception;

    /**
     * Retorna uma lista de solicitacao servico associada a requisição mudanca
     *
     * @param bean
     * @return
     * @throws Exception
     * @author thays.araujo
     */
    List<RequisicaoMudancaDTO> listMudancaByIdSolicitacao(final RequisicaoMudancaDTO bean) throws Exception;

    /**
     * Retorna uma lista de requisicao mudanca de acordo com os criterios passados.
     *
     * @param requisicaoMudancaDto
     * @return Collection<RequisicaoMudancaDTO>
     * @throws Exception
     * @author thays.araujo
     */
    Collection<PesquisaRequisicaoMudancaDTO> listRequisicaoMudancaByCriterios(final PesquisaRequisicaoMudancaDTO requisicaoMudancaDto) throws Exception;

    Collection<RequisicaoMudancaDTO> quantidadeMudancaPorBaseConhecimento(final RequisicaoMudancaDTO mudanca) throws Exception;

    /**
     * reativa requisição servico
     *
     * @param usuarioDto
     * @param solicitacaoServicoDto
     * @throws Exception
     * @author thays.araujo
     */
    void reativa(final UsuarioDTO usuarioDto, final RequisicaoMudancaDTO RequisicaoMudancaDto) throws Exception;

    RequisicaoMudancaDTO restoreAll(final Integer idRequisicaoMudanca) throws Exception;

    /**
     * suspende a requisição mudança
     *
     * @param usuarioDto
     * @param solicitacaoServicoDto
     * @throws Exception
     * @author thays.araujo
     */
    void suspende(final UsuarioDTO usuarioDto, final RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception;

    /**
     * Retorna verdadeiro ou falso caso a requisição esteja aprovada
     *
     * @param requisicaoMudancaDto
     * @param tc
     * @return
     * @throws Exception
     * @author thays.araujo
     */
    boolean validacaoAvancaFluxo(final RequisicaoMudancaDTO requisicaoMudancaDto, final TransactionControler tc) throws Exception;

    /**
     * Retorna se a requisição mudança esta aprovada, reprovada ou aguardando votação
     * 
     * @param requisicaoMudancaDto
     * @param tc
     * @return
     * @throws Exception
     * @author thiago.oliveira
     */
    String verificaAprovacaoProposta(final RequisicaoMudancaDTO requisicaoMudancaDto, final TransactionControler tc) throws Exception;

    String verificaAprovacaoMudanca(final RequisicaoMudancaDTO requisicaoMudancaDto, final TransactionControler tc) throws Exception;

    /**
     * Retorna verdadeiro ou falso caso tipo mudanca esteja associado a requisição mudança
     *
     * @param idTipoMudanca
     * @return
     * @throws Exception
     * @author geber.costa
     */
    boolean verificarSeRequisicaoMudancaPossuiTipoMudanca(final Integer idTipoMudanca) throws Exception;

    Collection listaQuantidadeERelacionamentos(final HttpServletRequest request, final RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception;

    Collection listaIdETituloMudancasPorPeriodo(final RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception;

    void update(final IDto requisicaoMudancaDto, final HttpServletRequest request) throws ServiceException, LogicException;

    boolean verificaPermissaoGrupoCancelar(final Integer idTipoMudança, final Integer idGrupo) throws ServiceException, Exception;

    boolean verificarItensRelacionados(final RequisicaoMudancaDTO requisicaoMudancaDto) throws ServiceException, Exception;

    boolean planoDeReversaoInformado(final RequisicaoMudancaDTO requisicaoMudancaDto, final HttpServletRequest request) throws Exception;

}
