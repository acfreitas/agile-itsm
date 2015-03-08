package br.com.centralit.citcorpore.negocio;

import java.sql.Timestamp;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.DespesaViagemDTO;
import br.com.centralit.citcorpore.bean.IntegranteViagemDTO;
import br.com.centralit.citcorpore.bean.RequisicaoViagemDTO;
import br.com.centralit.citcorpore.bean.RoteiroViagemDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.integracao.DespesaViagemDAO;
import br.com.centralit.citcorpore.integracao.IntegranteViagemDao;
import br.com.centralit.citcorpore.integracao.RequisicaoViagemDAO;
import br.com.centralit.citcorpore.integracao.RoteiroViagemDAO;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.WebUtil;

public class DespesaViagemServiceEjb extends ComplemInfSolicitacaoServicoServiceEjb implements DespesaViagemService {

    private DespesaViagemDAO dao;

    @Override
    protected DespesaViagemDAO getDao() {
        if (dao == null) {
            dao = new DespesaViagemDAO();
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
    public void validaCreate(final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {
        if (!this.validaPrazoItens(solicitacaoServicoDto.getIdSolicitacaoServico())) {
            throw new LogicException("Prazo cotação expirado ou maior que a data fim viagem, favor refazer a cotação dos itens novamente!");
        }
    }

    @Override
    public void validaDelete(final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {

    }

    @Override
    public void validaUpdate(final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {
        if (!this.validaPrazoItens(solicitacaoServicoDto.getIdSolicitacaoServico())) {
            throw new LogicException("Prazo cotação expirado ou maior que a data fim viagem, favor refazer a cotação dos itens novamente!");
        }
    }

    @Override
    public IDto create(final TransactionControler tc, final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {
        return null;
    }

    @Override
    public IDto create(IDto model) throws ServiceException, LogicException {

        try {
            model = this.getDao().create(model);
        } catch (final Exception e) {
            e.printStackTrace();
        }

        return model;
    }

    @Override
    public void update(final TransactionControler tc, final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {
        final RequisicaoViagemDAO requisicaoViagemDAO = new RequisicaoViagemDAO();

        requisicaoViagemDAO.setTransactionControler(tc);

        if (!(solicitacaoServicoDto.getAlterarSituacao().equalsIgnoreCase("S") && solicitacaoServicoDto.getDescrSituacao().equalsIgnoreCase("Cancelada"))) {
            final DespesaViagemDTO despesaViagemDTO = (DespesaViagemDTO) model;
            RequisicaoViagemDTO requisicaoViagemDTO = new RequisicaoViagemDTO();
            Collection<DespesaViagemDTO> colDespesaViagemDTO = null;

            final DespesaViagemDAO despesaViagemDAO = this.getDao();
            final IntegranteViagemDao integranteViagemDAO = new IntegranteViagemDao();
            final RoteiroViagemDAO roteiroViagemDAO = new RoteiroViagemDAO();

            if (solicitacaoServicoDto.getIdSolicitante().intValue() == solicitacaoServicoDto.getUsuarioDto().getIdEmpregado().intValue()
                    && !(despesaViagemDTO != null && despesaViagemDTO.getCancelarRequisicao() != null && despesaViagemDTO.getCancelarRequisicao().equalsIgnoreCase("S"))) {
                throw new LogicException("Usuário sem permissão para Execução!");
            }

            despesaViagemDAO.setTransactionControler(tc);
            integranteViagemDAO.setTransactionControler(tc);
            roteiroViagemDAO.setTransactionControler(tc);

            final Collection<IntegranteViagemDTO> colIntegrante = integranteViagemDAO.recuperaIntegrantesViagemByIdSolicitacaoEstado(despesaViagemDTO.getIdSolicitacaoServico(),
                    RequisicaoViagemDTO.AGUARDANDO_PLANEJAMENTO);

            if (colIntegrante != null && !colIntegrante.isEmpty()) {
                for (final IntegranteViagemDTO dto : colIntegrante) {
                    final RoteiroViagemDTO roteiroViagemDTO = roteiroViagemDAO.findByIdIntegrante(dto.getIdIntegranteViagem());
                    colDespesaViagemDTO = despesaViagemDAO.findDespesasAtivasViagemByIdRoteiro(roteiroViagemDTO.getIdRoteiroViagem());

                    if (colDespesaViagemDTO == null || colDespesaViagemDTO.isEmpty()) {
                        throw new LogicException("Deve ser atribuido a um integrante ao menos um item de despesa!");
                    }
                }
            }

            requisicaoViagemDTO.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
            requisicaoViagemDTO = (RequisicaoViagemDTO) requisicaoViagemDAO.restore(requisicaoViagemDTO);
            requisicaoViagemDTO.setIdAprovacao(null);
            requisicaoViagemDAO.update(requisicaoViagemDTO);

            if (despesaViagemDTO != null && despesaViagemDTO.getCancelarRequisicao() != null && despesaViagemDTO.getCancelarRequisicao().equalsIgnoreCase("S")) {
                if (requisicaoViagemDTO.getIdSolicitacaoServico() != null) {
                    requisicaoViagemDTO.setCancelarRequisicao("S");
                    solicitacaoServicoDto.setSituacao(Enumerados.SituacaoSolicitacaoServico.Cancelada.name());
                    requisicaoViagemDAO.updateNotNull(requisicaoViagemDTO);
                    return;
                }
            }

            this.validaUpdate(solicitacaoServicoDto, model);
        } else {
            RequisicaoViagemDTO requisicaoViagemDto = new RequisicaoViagemDTO();

            requisicaoViagemDto.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
            requisicaoViagemDto = (RequisicaoViagemDTO) requisicaoViagemDAO.restore(requisicaoViagemDto);

            if (requisicaoViagemDto != null) {
                requisicaoViagemDto.setEstado(Enumerados.SituacaoSolicitacaoServico.Cancelada.name());
                requisicaoViagemDAO.update(requisicaoViagemDto);
            }
        }

    }

    @Override
    public void delete(final TransactionControler tc, final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {

    }

    @Override
    public Collection<DespesaViagemDTO> findDespesaViagemByIdRoteiro(final Integer idRoteiro) throws Exception {
        return this.getDao().findDespesaViagemByIdRoteiro(idRoteiro);
    }

    @Override
    public Double buscarDespesaTotalViagem(final Integer idSolicitacao) throws Exception {
        return this.getDao().buscaValorTotalViagem(idSolicitacao);
    }

    /**
     * Valida se os itens de despesa estão com a cotação vencida
     *
     * @param idSolicitacaoServico
     * @return
     * @throws Exception
     * @author renato.jesus
     */
    public boolean validaPrazoItens(final Integer idSolicitacaoServico) throws Exception {
        final IntegranteViagemDao integranteViagemDao = new IntegranteViagemDao();
        RoteiroViagemDTO roteiroViagemDTO = new RoteiroViagemDTO();
        final RoteiroViagemDAO roteiroViagemDAO = new RoteiroViagemDAO();
        final DespesaViagemDAO despesaViagemDAO = new DespesaViagemDAO();
        final Timestamp dataHoraAtual = UtilDatas.getDataHoraAtual();
        Timestamp volta = null;

        final Collection<IntegranteViagemDTO> colIntegrantes = integranteViagemDao.recuperaIntegrantesViagemByIdSolicitacaoEstado(idSolicitacaoServico,
                RequisicaoViagemDTO.AGUARDANDO_PLANEJAMENTO);

        if (colIntegrantes != null) {
            for (final IntegranteViagemDTO integranteViagemDto : colIntegrantes) {
                roteiroViagemDTO = roteiroViagemDAO.findByIdIntegrante(integranteViagemDto.getIdIntegranteViagem());
                volta = UtilDatas.getTimeStampComUltimaHoraDoDia(roteiroViagemDTO.getVolta());

                final Collection<DespesaViagemDTO> colDespesas = despesaViagemDAO.findDespesasAtivasViagemByIdRoteiro(roteiroViagemDTO.getIdRoteiroViagem());

                if (colDespesas != null) {
                    for (final DespesaViagemDTO despesaViagemDTO : colDespesas) {
                        if (despesaViagemDTO.getValidade() != null && despesaViagemDTO.getValidade().compareTo(dataHoraAtual) < 0 || despesaViagemDTO.getValidade() != null
                                && despesaViagemDTO.getValidade().compareTo(volta) > 0) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    @Override
    public Double getTotalDespesaViagemPrestacaoContas(final Integer idSolicitacao, final Integer idEmpregado) throws Exception {
        return this.getDao().getTotalDespesaViagemPrestacaoContas(idSolicitacao, idEmpregado);
    }

    @Override
    public Collection<DespesaViagemDTO> findDespesaViagemByIdRoteiroAndPermiteAdiantamento(final Integer idRoteiro, final String permiteAdiantamento) throws Exception {
        return this.getDao().findDespesaViagemByIdRoteiroAndPermiteAdiantamento(idRoteiro, permiteAdiantamento);
    }

    @Override
    public Collection<DespesaViagemDTO> findDespesasAtivasViagemByIdRoteiro(final Integer idRoteiro) throws Exception {
        return this.getDao().findDespesasAtivasViagemByIdRoteiro(idRoteiro);
    }

    @Override
    public Collection<DespesaViagemDTO> findTodasDespesasViagemByIdRoteiro(final Integer idRoteiro) throws Exception {
        return this.getDao().findTodasDespesasViagemByIdRoteiro(idRoteiro);
    }

    /**
     * TODO Este metodo esta em desuso, pode ser removido na proxima versão
     */
    @Override
    public Collection<DespesaViagemDTO> findHitoricoDespesaViagemByIdRoteiro(final Integer idRoteiro) throws Exception {
        return this.getDao().findHitoricoDespesaViagemByIdRoteiro(idRoteiro);
    }

    @Override
    public Double buscaValorTotalViagemAtivo(final Integer idSolicitacao) throws Exception {
        return this.getDao().buscaValorTotalViagemAtivo(idSolicitacao);
    }

    @Override
    public Double buscaValorTotalViagemInativo(final Integer idSolicitacao) throws Exception {
        return this.getDao().buscaValorTotalViagemInativo(idSolicitacao);
    }

    @Override
    public Double buscaValorViagemHistorico(final Integer idSolicitacao) throws Exception {
        return this.getDao().buscaValorViagemHistorico(idSolicitacao);
    }

}
