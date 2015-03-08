package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.AdiantamentoViagemDTO;
import br.com.centralit.citcorpore.bean.DespesaViagemDTO;
import br.com.centralit.citcorpore.bean.IntegranteViagemDTO;
import br.com.centralit.citcorpore.bean.RequisicaoViagemDTO;
import br.com.centralit.citcorpore.bean.RoteiroViagemDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.TipoMovimFinanceiraViagemDTO;
import br.com.centralit.citcorpore.integracao.AdiantamentoViagemDAO;
import br.com.centralit.citcorpore.integracao.IntegranteViagemDao;
import br.com.centralit.citcorpore.integracao.RequisicaoViagemDAO;
import br.com.centralit.citcorpore.integracao.RoteiroViagemDAO;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.WebUtil;

public class AdiantamentoViagemServiceEjb extends ComplemInfSolicitacaoServicoServiceEjb implements AdiantamentoViagemService {

    private AdiantamentoViagemDAO dao;

    @Override
    protected AdiantamentoViagemDAO getDao() {
        if (dao == null) {
            dao = new AdiantamentoViagemDAO();
        }
        return dao;
    }

    @Override
    public IDto deserializaObjeto(final String serialize) throws Exception {
        DespesaViagemDTO despesaViagemDTO = null;
        if (serialize != null) {
            despesaViagemDTO = (DespesaViagemDTO) WebUtil.deserializeObject(DespesaViagemDTO.class, serialize);
        }
        return despesaViagemDTO;
    }

    @Override
    public void validaCreate(final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {}

    @Override
    public void validaDelete(final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {}

    @Override
    public void validaUpdate(final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {}

    @Override
    public IDto create(final TransactionControler tc, final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {
        return null;
    }

    @Override
    public void update(final TransactionControler tc, final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {
        final DespesaViagemDTO despesaViagemDTO = (DespesaViagemDTO) model;
        final DespesaViagemService despesaViagemService = (DespesaViagemService) ServiceLocator.getInstance().getService(DespesaViagemService.class, null);
        final RoteiroViagemDAO roteiroViagemDAO = new RoteiroViagemDAO();
        Collection<DespesaViagemDTO> colDespesas = new ArrayList<DespesaViagemDTO>();
        RoteiroViagemDTO roteiro = new RoteiroViagemDTO();
        TipoMovimFinanceiraViagemDTO tipoMovimFinanceiraViagemDTO = new TipoMovimFinanceiraViagemDTO();
        final TipoMovimFinanceiraViagemService tipoMovimFinanceiraViagemService = (TipoMovimFinanceiraViagemService) ServiceLocator.getInstance().getService(
                TipoMovimFinanceiraViagemService.class, null);

        if (despesaViagemDTO.getConfirma() == null || despesaViagemDTO.getConfirma().equals("N")) {
            throw new LogicException("É preciso confirmar o adiantamento para os integrantes para avançar o fluxo!");
        }

        RequisicaoViagemDTO requisicaoViagemDto = new RequisicaoViagemDTO();
        final RequisicaoViagemDAO requisicaoViagemDAO = new RequisicaoViagemDAO();

        final IntegranteViagemDao integranteViagemDao = new IntegranteViagemDao();
        Collection<IntegranteViagemDTO> integrantesViagem = null;

        requisicaoViagemDAO.setTransactionControler(tc);
        integranteViagemDao.setTransactionControler(tc);

        if (despesaViagemDTO != null && despesaViagemDTO.getCancelarRequisicao() != null && despesaViagemDTO.getCancelarRequisicao().equalsIgnoreCase("S")) {
            requisicaoViagemDto.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
            requisicaoViagemDto = (RequisicaoViagemDTO) requisicaoViagemDAO.restore(requisicaoViagemDto);
            requisicaoViagemDto.setCancelarRequisicao("S");
            solicitacaoServicoDto.setSituacao(Enumerados.SituacaoSolicitacaoServico.Cancelada.name());
            requisicaoViagemDAO.updateNotNull(requisicaoViagemDto);
            return;
        }

        if (solicitacaoServicoDto.getAcaoFluxo().equalsIgnoreCase("E")) {
            this.validaUpdate(solicitacaoServicoDto, model);
            requisicaoViagemDto.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
            requisicaoViagemDto = (RequisicaoViagemDTO) requisicaoViagemDAO.restore(requisicaoViagemDto);
            requisicaoViagemDto.setEstado(RequisicaoViagemDTO.AGUARDANDO_PRESTACAOCONTAS);
            requisicaoViagemDAO.update(requisicaoViagemDto);

            integrantesViagem = integranteViagemDao.recuperaIntegrantesViagemByIdSolicitacaoEstado(despesaViagemDTO.getIdSolicitacaoServico(),
                    RequisicaoViagemDTO.AGUARDANDO_ADIANTAMENTO);
            if (integrantesViagem != null && !integrantesViagem.isEmpty()) {
                for (final IntegranteViagemDTO integranteViagemDTO : integrantesViagem) {
                    roteiro = roteiroViagemDAO.findByIdIntegrante(integranteViagemDTO.getIdIntegranteViagem());
                    if (roteiro != null) {
                        colDespesas = despesaViagemService.findDespesasAtivasViagemByIdRoteiro(roteiro.getIdRoteiroViagem());
                    }

                    if (colDespesas != null && !colDespesas.isEmpty()) {
                        for (final DespesaViagemDTO dto : colDespesas) {
                            tipoMovimFinanceiraViagemDTO.setIdtipoMovimFinanceiraViagem(dto.getIdTipo());
                            tipoMovimFinanceiraViagemDTO = (TipoMovimFinanceiraViagemDTO) tipoMovimFinanceiraViagemService.restore(tipoMovimFinanceiraViagemDTO);
                            if (tipoMovimFinanceiraViagemDTO.getPermiteAdiantamento().equalsIgnoreCase("S")) {
                                dto.setDataHoraCompra(UtilDatas.getDataHoraAtual());
                                dto.setIdResponsavelCompra(solicitacaoServicoDto.getUsuarioDto().getIdEmpregado());
                                dto.setSituacao("Adiantado");
                                despesaViagemService.update(dto);
                            }
                        }
                    }

                    integranteViagemDTO.setEstado(RequisicaoViagemDTO.AGUARDANDO_PRESTACAOCONTAS);
                    integranteViagemDao.update(integranteViagemDTO);
                }
            }
        }
    }

    @Override
    public void delete(final TransactionControler tc, final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {

    }

    @Override
    public IDto create(final IDto model) throws ServiceException, LogicException {
        return null;
    }

    @Override
    public void update(final IDto model) throws ServiceException, LogicException {

    }

    /**
     * TODO Este metodo esta em desuso, pode ser removido na proxima versão
     */
    @Override
    public Integer recuperaIdAdiantamentoSeExistir(AdiantamentoViagemDTO adiantamentoViagemDto) throws Exception {

        final List<AdiantamentoViagemDTO> lista = this.getDao().findBySolicitacaoAndEmpregado(adiantamentoViagemDto);
        if (lista != null && !lista.isEmpty()) {
            adiantamentoViagemDto = lista.get(0);
            if (adiantamentoViagemDto.getIdAdiantamentoViagem() != null && adiantamentoViagemDto.getIdAdiantamentoViagem() > 0) {
                return adiantamentoViagemDto.getIdAdiantamentoViagem();
            }
        }
        return null;
    }

    /**
     * Consultar entidade AdiantamentoViagemDTO pelos parametros
     *
     * @param IdSolicitacaoServico
     * @param idEmpregado
     * @return
     * @throws Exception
     */
    @Override
    public AdiantamentoViagemDTO consultarAdiantamentoViagem(final Integer IdSolicitacaoServico, final Integer idEmpregado) throws Exception {

        final List<AdiantamentoViagemDTO> lista = this.getDao().consultarPorSolicitacaoEEmpregado(IdSolicitacaoServico, idEmpregado);

        if (lista != null && !lista.isEmpty()) {
            return lista.get(0);
        }

        return null;

    }

    /**
     * TODO Este metodo esta em desuso, pode ser removido na proxima versão
     */
    @Override
    public AdiantamentoViagemDTO consultarAdiantamentoViagemByIdSolicitacao(final Integer IdSolicitacaoServico) throws Exception {

        final List<AdiantamentoViagemDTO> lista = this.getDao().consultarPorSolicitacao(IdSolicitacaoServico);

        if (lista != null && !lista.isEmpty()) {
            return lista.get(0);
        }

        return null;

    }

    /**
     * TODO Este metodo esta em desuso, pode ser removido na proxima versão
     */
    @Override
    public AdiantamentoViagemDTO consultarAdiantamentoViagem(final Integer IdSolicitacaoServico, final String nomeFunc) throws Exception {

        final List<AdiantamentoViagemDTO> lista = this.getDao().consultarPorSolicitacaoENomeNaoFuncionario(IdSolicitacaoServico, nomeFunc);

        if (lista != null && !lista.isEmpty()) {
            return lista.get(0);
        }

        return null;

    }
}
