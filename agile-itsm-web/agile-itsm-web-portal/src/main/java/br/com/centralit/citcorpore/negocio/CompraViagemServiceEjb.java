package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.bpm.negocio.ItemTrabalho;
import br.com.centralit.citcorpore.bean.DespesaViagemDTO;
import br.com.centralit.citcorpore.bean.IntegranteViagemDTO;
import br.com.centralit.citcorpore.bean.RequisicaoViagemDTO;
import br.com.centralit.citcorpore.bean.RoteiroViagemDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.TipoMovimFinanceiraViagemDTO;
import br.com.centralit.citcorpore.integracao.DespesaViagemDAO;
import br.com.centralit.citcorpore.integracao.IntegranteViagemDao;
import br.com.centralit.citcorpore.integracao.RequisicaoViagemDAO;
import br.com.centralit.citcorpore.integracao.RoteiroViagemDAO;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.WebUtil;

public class CompraViagemServiceEjb extends ComplemInfSolicitacaoServicoServiceEjb implements CompraViagemService {

    @Override
    protected CrudDAO getDao() {
        return null;
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

    }

    @Override
    public void validaDelete(final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {

    }

    @Override
    public void validaUpdate(final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {

    }

    @Override
    public IDto create(final TransactionControler tc, final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {
        return null;
    }

    @Override
    public void update(final TransactionControler tc, final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {
        final RequisicaoViagemDAO requisicaoViagemDao = new RequisicaoViagemDAO();
        TipoMovimFinanceiraViagemDTO tipoMovimFinanceiraViagemDTO = new TipoMovimFinanceiraViagemDTO();
        final TipoMovimFinanceiraViagemService tipoMovimFinanceiraViagemService = (TipoMovimFinanceiraViagemService) ServiceLocator.getInstance().getService(
                TipoMovimFinanceiraViagemService.class, null);

        requisicaoViagemDao.setTransactionControler(tc);

        if (!(solicitacaoServicoDto.getAlterarSituacao().equalsIgnoreCase("S") && solicitacaoServicoDto.getDescrSituacao().equalsIgnoreCase("Cancelada"))) {
            final DespesaViagemDTO despesaViagemDTO = (DespesaViagemDTO) model;
            final DespesaViagemDAO despesaViagemDAO = new DespesaViagemDAO();
            RoteiroViagemDTO roteiroViagemDTO = new RoteiroViagemDTO();
            final RoteiroViagemDAO roteiroViagemDAO = new RoteiroViagemDAO();

            if (solicitacaoServicoDto.getIdSolicitante().intValue() == solicitacaoServicoDto.getUsuarioDto().getIdEmpregado().intValue()
                    && !(despesaViagemDTO != null && despesaViagemDTO.getCancelarRequisicao() != null && despesaViagemDTO.getCancelarRequisicao().equalsIgnoreCase("S"))) {
                throw new LogicException("Usuário sem permissão para Executar Compras!");
            }

            despesaViagemDAO.setTransactionControler(tc);

            if (despesaViagemDTO != null && despesaViagemDTO.getCancelarRequisicao() != null && despesaViagemDTO.getCancelarRequisicao().equalsIgnoreCase("S")) {
                RequisicaoViagemDTO requisicaoViagemDto = new RequisicaoViagemDTO();
                requisicaoViagemDto.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
                requisicaoViagemDto = (RequisicaoViagemDTO) requisicaoViagemDao.restore(requisicaoViagemDto);

                if (requisicaoViagemDto.getIdSolicitacaoServico() != null) {
                    requisicaoViagemDto.setCancelarRequisicao("S");
                    solicitacaoServicoDto.setSituacao(Enumerados.SituacaoSolicitacaoServico.Cancelada.name());
                    requisicaoViagemDao.updateNotNull(requisicaoViagemDto);
                    return;
                }
            }

            if (solicitacaoServicoDto.getAcaoFluxo().equalsIgnoreCase("E")
                    && !solicitacaoServicoDto.getSituacao().equalsIgnoreCase(Enumerados.SituacaoSolicitacaoServico.Cancelada.name())) {
                if (despesaViagemDTO.getConfirma() == null || !despesaViagemDTO.getConfirma().equalsIgnoreCase("S")
                        && !solicitacaoServicoDto.getSituacao().equalsIgnoreCase(Enumerados.SituacaoSolicitacaoServico.Cancelada.name())) {
                    throw new LogicException("Necessária a Confirmação da Compra dos Itens para Avançar o fluxo");
                }
            }

            final IntegranteViagemService integranteViagemService = (IntegranteViagemService) ServiceLocator.getInstance().getService(IntegranteViagemService.class, null);
            final IntegranteViagemDao integranteViagemDao = new IntegranteViagemDao();

            final Collection<IntegranteViagemDTO> colIntegrantes = integranteViagemService.recuperaIntegrantesViagemByIdSolicitacaoEstado(
                    solicitacaoServicoDto.getIdSolicitacaoServico(), RequisicaoViagemDTO.AGUARDANDO_COMPRAS);
            Collection<DespesaViagemDTO> colItens;

            if (despesaViagemDTO.getConfirma() != null && despesaViagemDTO.getConfirma().equalsIgnoreCase("S")) {
                if (colIntegrantes != null) {
                    for (final IntegranteViagemDTO integrante : colIntegrantes) {
                        roteiroViagemDTO = roteiroViagemDAO.findByIdIntegrante(integrante.getIdIntegranteViagem());
                        colItens = despesaViagemDAO.listaItensCompra(solicitacaoServicoDto.getIdSolicitacaoServico(), roteiroViagemDTO.getIdRoteiroViagem());
                        integrante.setEstado(RequisicaoViagemDTO.AGUARDANDO_ADIANTAMENTO);
                        integranteViagemDao.updateNotNull(integrante);

                        if (colItens != null) {
                            for (final DespesaViagemDTO itemDespesaDto : colItens) {
                                tipoMovimFinanceiraViagemDTO.setIdtipoMovimFinanceiraViagem(itemDespesaDto.getIdTipo());
                                tipoMovimFinanceiraViagemDTO = (TipoMovimFinanceiraViagemDTO) tipoMovimFinanceiraViagemService.restore(tipoMovimFinanceiraViagemDTO);
                                if (tipoMovimFinanceiraViagemDTO.getPermiteAdiantamento().equalsIgnoreCase("N")) {
                                    itemDespesaDto.setSituacao("Comprado");
                                    itemDespesaDto.setDataHoraCompra(UtilDatas.getDataHoraAtual());
                                    itemDespesaDto.setIdResponsavelCompra(solicitacaoServicoDto.getUsuarioDto().getIdEmpregado());
                                    despesaViagemDAO.update(itemDespesaDto);
                                }
                            }
                        }
                    }
                }

                RequisicaoViagemDTO requisicaoViagemDto = new RequisicaoViagemDTO();
                requisicaoViagemDto.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
                requisicaoViagemDto = (RequisicaoViagemDTO) requisicaoViagemDao.restore(requisicaoViagemDto);

                if (requisicaoViagemDto != null) {
                    requisicaoViagemDto.setEstado(RequisicaoViagemDTO.AGUARDANDO_ADIANTAMENTO);
                    requisicaoViagemDao.updateNotNull(requisicaoViagemDto);
                }
            }
        } else {
            RequisicaoViagemDTO requisicaoViagemDto = new RequisicaoViagemDTO();

            requisicaoViagemDto.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
            requisicaoViagemDto = (RequisicaoViagemDTO) requisicaoViagemDao.restore(requisicaoViagemDto);

            if (requisicaoViagemDto != null) {
                requisicaoViagemDto.setEstado(Enumerados.SituacaoSolicitacaoServico.Cancelada.name());
                requisicaoViagemDao.update(requisicaoViagemDto);
            }
        }

    }

    @Override
    public void delete(final TransactionControler tc, final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {

    }

    @Override
    public String getInformacoesComplementaresFmtTexto(final SolicitacaoServicoDTO solicitacaoDto, final ItemTrabalho itemTrabalho) throws Exception {
        return solicitacaoDto.getDescricaoSemFormatacao();
    }

}
