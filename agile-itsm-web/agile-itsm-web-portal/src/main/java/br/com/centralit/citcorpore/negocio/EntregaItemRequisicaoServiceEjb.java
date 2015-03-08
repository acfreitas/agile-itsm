package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.EntregaItemRequisicaoDTO;
import br.com.centralit.citcorpore.bean.InspecaoEntregaItemDTO;
import br.com.centralit.citcorpore.bean.ItemRequisicaoProdutoDTO;
import br.com.centralit.citcorpore.bean.ParecerDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.EntregaItemRequisicaoDao;
import br.com.centralit.citcorpore.integracao.InspecaoEntregaItemDao;
import br.com.centralit.citcorpore.integracao.ItemRequisicaoProdutoDao;
import br.com.centralit.citcorpore.util.Enumerados.AcaoItemRequisicaoProduto;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoEntregaItemRequisicao;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoItemRequisicaoProduto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilDatas;

public class EntregaItemRequisicaoServiceEjb extends CrudServiceImpl implements EntregaItemRequisicaoService {

    private EntregaItemRequisicaoDao dao;

    @Override
    protected EntregaItemRequisicaoDao getDao() {
        if (dao == null) {
            dao = new EntregaItemRequisicaoDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdPedido(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdPedido(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdPedido(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdPedido(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdColetaPreco(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdColetaPreco(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdColetaPreco(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdColetaPreco(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdItemRequisicaoProduto(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdItemRequisicaoProduto(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdItemRequisicaoProduto(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdItemRequisicaoProduto(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdItemTrabalho(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdItemTrabalho(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection<EntregaItemRequisicaoDTO> findNaoAprovadasByIdSolicitacaoServico(final Integer idSolicitacaoServico) throws Exception {
        try {
            return this.getDao().findNaoAprovadasByIdSolicitacaoServico(idSolicitacaoServico);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void atualizaInspecao(final EntregaItemRequisicaoDTO entregaItemRequisicaoDto) throws Exception {
        final InspecaoEntregaItemDao inspecaoEntregaItemDao = new InspecaoEntregaItemDao();
        final TransactionControler tc = new TransactionControlerImpl(inspecaoEntregaItemDao.getAliasDB());

        try {
            tc.start();
            inspecaoEntregaItemDao.setTransactionControler(tc);

            inspecaoEntregaItemDao.deleteByIdEntrega(entregaItemRequisicaoDto.getIdEntrega());
            if (entregaItemRequisicaoDto.getColInspecao() != null) {
                for (final InspecaoEntregaItemDTO inspecaoDto : entregaItemRequisicaoDto.getColInspecao()) {
                    if (inspecaoDto.getAvaliacao() == null || inspecaoDto.getAvaliacao().trim().length() == 0) {
                        throw new Exception("Avaliação não informada");
                    }
                    inspecaoDto.setIdResponsavel(entregaItemRequisicaoDto.getUsuarioDto().getIdEmpregado());
                    inspecaoDto.setDataHoraInspecao(UtilDatas.getDataHoraAtual());
                    inspecaoDto.setIdEntrega(entregaItemRequisicaoDto.getIdEntrega());
                    inspecaoEntregaItemDao.create(inspecaoDto);
                }
            }

            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
    }

    public void atualizaSituacao(final EntregaItemRequisicaoDTO entregaItemRequisicaoDto, final UsuarioDTO usuarioDto, final TransactionControler tc) throws Exception {
        final ItemRequisicaoProdutoDao itemRequisicaoDao = new ItemRequisicaoProdutoDao();
        itemRequisicaoDao.setTransactionControler(tc);
        final ItemRequisicaoProdutoServiceEjb itemRequisicaoService = new ItemRequisicaoProdutoServiceEjb();

        ItemRequisicaoProdutoDTO itemRequisicaoDto = new ItemRequisicaoProdutoDTO();
        itemRequisicaoDto.setIdItemRequisicaoProduto(entregaItemRequisicaoDto.getIdItemRequisicaoProduto());
        itemRequisicaoDto = (ItemRequisicaoProdutoDTO) itemRequisicaoDao.restore(itemRequisicaoDto);

        final EntregaItemRequisicaoDao entregaItemRequisicaoDao = new EntregaItemRequisicaoDao();
        entregaItemRequisicaoDao.setTransactionControler(tc);

        EntregaItemRequisicaoDTO entregaAuxDto = new EntregaItemRequisicaoDTO();
        entregaAuxDto.setIdEntrega(entregaItemRequisicaoDto.getIdEntrega());
        entregaAuxDto = (EntregaItemRequisicaoDTO) entregaItemRequisicaoDao.restore(entregaAuxDto);
        final ParecerDTO parecerDto = new ParecerServiceEjb().createOrUpdate(tc, entregaAuxDto.getIdParecer(), usuarioDto, entregaItemRequisicaoDto.getIdJustificativa(),
                entregaItemRequisicaoDto.getComplementoJustificativa(), entregaItemRequisicaoDto.getAprovado());
        entregaAuxDto.setIdParecer(parecerDto.getIdParecer());
        if (parecerDto.getAprovado().equalsIgnoreCase("S")) {
            entregaAuxDto.setSituacao(SituacaoEntregaItemRequisicao.Aprovada.name());
            itemRequisicaoDto.setSituacao(SituacaoItemRequisicaoProduto.Finalizado.name());
        } else if (parecerDto.getAprovado().equalsIgnoreCase("N")) {
            entregaAuxDto.setIdItemTrabalho(null);
            entregaAuxDto.setSituacao(SituacaoEntregaItemRequisicao.NaoAprovada.name());
            itemRequisicaoDto.setSituacao(SituacaoItemRequisicaoProduto.InspecaoRejeitada.name());
        }
        entregaItemRequisicaoDao.update(entregaAuxDto);
        itemRequisicaoDao.update(itemRequisicaoDto);
        itemRequisicaoService.geraHistorico(tc, usuarioDto, itemRequisicaoDto, AcaoItemRequisicaoProduto.Inspecao, null,
                SituacaoItemRequisicaoProduto.valueOf(itemRequisicaoDto.getSituacao()));
    }

    public void finalizaDecursoPrazo(final EntregaItemRequisicaoDTO entregaItemRequisicaoDto, final TransactionControler tc) throws Exception {
        final ItemRequisicaoProdutoDao itemRequisicaoDao = new ItemRequisicaoProdutoDao();
        itemRequisicaoDao.setTransactionControler(tc);
        final ItemRequisicaoProdutoServiceEjb itemRequisicaoService = new ItemRequisicaoProdutoServiceEjb();

        ItemRequisicaoProdutoDTO itemRequisicaoDto = new ItemRequisicaoProdutoDTO();
        itemRequisicaoDto.setIdItemRequisicaoProduto(entregaItemRequisicaoDto.getIdItemRequisicaoProduto());
        itemRequisicaoDto = (ItemRequisicaoProdutoDTO) itemRequisicaoDao.restore(itemRequisicaoDto);

        final EntregaItemRequisicaoDao entregaItemRequisicaoDao = new EntregaItemRequisicaoDao();
        entregaItemRequisicaoDao.setTransactionControler(tc);

        entregaItemRequisicaoDto.setSituacao(SituacaoEntregaItemRequisicao.AprovadaPrazo.name());
        entregaItemRequisicaoDao.updateNotNull(entregaItemRequisicaoDto);

        double qtde = 0;
        final Collection<EntregaItemRequisicaoDTO> colEntregas = entregaItemRequisicaoDao.findByIdItemRequisicaoProduto(entregaItemRequisicaoDto.getIdItemRequisicaoProduto());
        for (final EntregaItemRequisicaoDTO entregaDto : colEntregas) {
            if (entregaDto.getSituacao().equals(SituacaoEntregaItemRequisicao.Aprovada.name())
                    || entregaDto.getSituacao().equals(SituacaoEntregaItemRequisicao.AprovadaPrazo.name())) {
                qtde += entregaDto.getQuantidadeEntregue().doubleValue();
            }
        }

        if (itemRequisicaoDto.getQtdeAprovada().doubleValue() == qtde) {
            itemRequisicaoDto.setSituacao(SituacaoItemRequisicaoProduto.Finalizado.name());
            itemRequisicaoDao.update(itemRequisicaoDto);
            itemRequisicaoService.geraHistorico(tc, null, itemRequisicaoDto, AcaoItemRequisicaoProduto.Inspecao, "Finalização automática por decurso de prazo",
                    SituacaoItemRequisicaoProduto.valueOf(itemRequisicaoDto.getSituacao()));
        }
    }

    public void atualizaAcionamentoGarantia(final EntregaItemRequisicaoDTO entregaItemRequisicaoDto, final UsuarioDTO usuarioDto, final TransactionControler tc) throws Exception {
        final EntregaItemRequisicaoDao entregaItemRequisicaoDao = new EntregaItemRequisicaoDao();
        entregaItemRequisicaoDao.setTransactionControler(tc);

        EntregaItemRequisicaoDTO entregaAuxDto = new EntregaItemRequisicaoDTO();
        entregaAuxDto.setIdEntrega(entregaItemRequisicaoDto.getIdEntrega());
        entregaAuxDto = (EntregaItemRequisicaoDTO) entregaItemRequisicaoDao.restore(entregaAuxDto);
        entregaAuxDto.setSituacao(SituacaoEntregaItemRequisicao.Aguarda.name());
        entregaAuxDto.setIdItemTrabalho(null);
        entregaItemRequisicaoDao.update(entregaAuxDto);

        final ItemRequisicaoProdutoDao itemRequisicaoDao = new ItemRequisicaoProdutoDao();
        itemRequisicaoDao.setTransactionControler(tc);
        final ItemRequisicaoProdutoServiceEjb itemRequisicaoService = new ItemRequisicaoProdutoServiceEjb();

        ItemRequisicaoProdutoDTO itemRequisicaoDto = new ItemRequisicaoProdutoDTO();
        itemRequisicaoDto.setIdItemRequisicaoProduto(entregaItemRequisicaoDto.getIdItemRequisicaoProduto());
        itemRequisicaoDto = (ItemRequisicaoProdutoDTO) itemRequisicaoDao.restore(itemRequisicaoDto);
        itemRequisicaoDto.setSituacao(SituacaoItemRequisicaoProduto.AguardandoInspecao.name());
        entregaItemRequisicaoDao.update(entregaAuxDto);
        itemRequisicaoService.geraHistorico(tc, usuarioDto, itemRequisicaoDto, AcaoItemRequisicaoProduto.Garantia, null, SituacaoItemRequisicaoProduto.AguardandoInspecaoGarantia);
    }

}
