package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ColetaPrecoDTO;
import br.com.centralit.citcorpore.bean.CotacaoItemRequisicaoDTO;
import br.com.centralit.citcorpore.bean.ItemRequisicaoProdutoDTO;
import br.com.centralit.citcorpore.bean.ParecerDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.ColetaPrecoDao;
import br.com.centralit.citcorpore.integracao.CotacaoItemRequisicaoDao;
import br.com.centralit.citcorpore.integracao.ItemRequisicaoProdutoDao;
import br.com.centralit.citcorpore.util.Enumerados.AcaoItemRequisicaoProduto;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoCotacaoItemRequisicao;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoItemRequisicaoProduto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudServiceImpl;

public class CotacaoItemRequisicaoServiceEjb extends CrudServiceImpl implements CotacaoItemRequisicaoService {

    private CotacaoItemRequisicaoDao dao;

    @Override
    protected CotacaoItemRequisicaoDao getDao() {
        if (dao == null) {
            dao = new CotacaoItemRequisicaoDao();
        }
        return dao;
    }

    @Override
    public Collection findPendentesByIdCotacao(final Integer idCotacao) throws Exception {
        try {
            return this.getDao().findPendentesByIdCotacao(idCotacao);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdRequisicaoProduto(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdRequisicaoProduto(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdCotacao(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdCotacao(parm);
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
    public Collection findByIdItemTrabalho(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdItemTrabalho(parm);
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

    public void cancelaTarefaAprovacaoCotacao(final Integer idRequisicaoProduto, final String motivo, final UsuarioDTO usuarioDto, final TransactionControler tc) throws Exception {
        final Collection<CotacaoItemRequisicaoDTO> colItens = this.findByIdRequisicaoProduto(idRequisicaoProduto);
        if (colItens != null) {
            final CotacaoItemRequisicaoDao dao = new CotacaoItemRequisicaoDao();
            if (tc != null) {
                dao.setTransactionControler(tc);
            }

            final ItemRequisicaoProdutoDao itemRequisicaoDao = new ItemRequisicaoProdutoDao();
            if (tc != null) {
                itemRequisicaoDao.setTransactionControler(tc);
            }

            final ItemRequisicaoProdutoServiceEjb itemRequisicaoService = new ItemRequisicaoProdutoServiceEjb();
            for (final CotacaoItemRequisicaoDTO cotacaoItemRequisicaoDto : colItens) {
                if (cotacaoItemRequisicaoDto.getSituacao().equalsIgnoreCase(SituacaoCotacaoItemRequisicao.AguardaAprovacao.name())
                        && cotacaoItemRequisicaoDto.getIdItemTrabalho() != null) {
                    ItemRequisicaoProdutoDTO itemRequisicaoDto = new ItemRequisicaoProdutoDTO();
                    itemRequisicaoDto.setIdItemRequisicaoProduto(cotacaoItemRequisicaoDto.getIdItemRequisicaoProduto());
                    itemRequisicaoDto = (ItemRequisicaoProdutoDTO) itemRequisicaoDao.restore(itemRequisicaoDto);

                    cotacaoItemRequisicaoDto.setIdItemTrabalho(null);
                    dao.update(cotacaoItemRequisicaoDto);
                    itemRequisicaoService.geraHistorico(tc, usuarioDto, itemRequisicaoDto, AcaoItemRequisicaoProduto.Alteracao, motivo,
                            SituacaoItemRequisicaoProduto.valueOf(itemRequisicaoDto.getSituacao()));
                }
            }
        }
    }

    public void atualizaAprovacaoCotacao(final CotacaoItemRequisicaoDTO cotacaoItemRequisicaoDto, final UsuarioDTO usuarioDto, final TransactionControler tc) throws Exception {
        final ItemRequisicaoProdutoDao itemRequisicaoDao = new ItemRequisicaoProdutoDao();
        final CotacaoItemRequisicaoDao cotacaoItemRequisicaoDao = new CotacaoItemRequisicaoDao();
        cotacaoItemRequisicaoDao.setTransactionControler(tc);
        itemRequisicaoDao.setTransactionControler(tc);

        CotacaoItemRequisicaoDTO itemAuxDto = new CotacaoItemRequisicaoDTO();
        itemAuxDto.setIdItemRequisicaoProduto(cotacaoItemRequisicaoDto.getIdItemRequisicaoProduto());
        itemAuxDto.setIdColetaPreco(cotacaoItemRequisicaoDto.getIdColetaPreco());
        itemAuxDto = (CotacaoItemRequisicaoDTO) cotacaoItemRequisicaoDao.restore(itemAuxDto);

        ItemRequisicaoProdutoDTO itemRequisicaoDto = new ItemRequisicaoProdutoDTO();
        itemRequisicaoDto.setIdItemRequisicaoProduto(cotacaoItemRequisicaoDto.getIdItemRequisicaoProduto());
        itemRequisicaoDto = (ItemRequisicaoProdutoDTO) itemRequisicaoDao.restore(itemRequisicaoDto);
        itemRequisicaoDto.setPercVariacaoPreco(cotacaoItemRequisicaoDto.getPercVariacaoPreco());

        final ParecerDTO parecerDto = new ParecerServiceEjb().createOrUpdate(tc, itemAuxDto.getIdParecer(), usuarioDto, cotacaoItemRequisicaoDto.getIdJustificativa(),
                cotacaoItemRequisicaoDto.getComplementoJustificativa(), cotacaoItemRequisicaoDto.getAprovado());
        itemAuxDto.setIdParecer(parecerDto.getIdParecer());
        if (parecerDto.getAprovado().equalsIgnoreCase("S")) {
            final ColetaPrecoDao coletaPrecoDao = new ColetaPrecoDao();
            coletaPrecoDao.setTransactionControler(tc);
            ColetaPrecoDTO coletaPrecoDto = new ColetaPrecoDTO();
            coletaPrecoDto.setIdColetaPreco(cotacaoItemRequisicaoDto.getIdColetaPreco());
            coletaPrecoDto = (ColetaPrecoDTO) coletaPrecoDao.restore(coletaPrecoDto);
            final double valor = (coletaPrecoDto.getPreco() - coletaPrecoDto.getValorDesconto() + coletaPrecoDto.getValorAcrescimo()) / coletaPrecoDto.getQuantidadeCotada();

            itemAuxDto.setSituacao(SituacaoCotacaoItemRequisicao.Aprovado.name());
            itemRequisicaoDto.setValorAprovado(valor);
            itemRequisicaoDto.setSituacao(SituacaoItemRequisicaoProduto.AguardandoPedido.name());
        } else if (parecerDto.getAprovado().equalsIgnoreCase("N")) {
            itemAuxDto.setSituacao(SituacaoCotacaoItemRequisicao.NaoAprovado.name());
            itemRequisicaoDto.setValorAprovado(new Double(0));
            itemRequisicaoDto.setSituacao(SituacaoItemRequisicaoProduto.CotacaoNaoAprovada.name());
        }

        itemRequisicaoDao.update(itemRequisicaoDto);
        cotacaoItemRequisicaoDao.update(itemAuxDto);

        final ItemRequisicaoProdutoServiceEjb itemRequisicaoService = new ItemRequisicaoProdutoServiceEjb();
        itemRequisicaoService.geraHistorico(tc, usuarioDto, itemRequisicaoDto, AcaoItemRequisicaoProduto.Aprovacao, "Cotação No. " + cotacaoItemRequisicaoDto.getIdCotacao()
                + ", Coleta No. " + cotacaoItemRequisicaoDto.getIdColetaPreco(), SituacaoItemRequisicaoProduto.valueOf(itemRequisicaoDto.getSituacao()));

        if (parecerDto.getAprovado().equalsIgnoreCase("S")) {
            final ColetaPrecoDao coletaPrecoDao = new ColetaPrecoDao();
            coletaPrecoDao.setTransactionControler(tc);
            ColetaPrecoDTO coletaPrecoDto = new ColetaPrecoDTO();
            coletaPrecoDto.setIdColetaPreco(cotacaoItemRequisicaoDto.getIdColetaPreco());
            coletaPrecoDto = (ColetaPrecoDTO) coletaPrecoDao.restore(coletaPrecoDto);
            double qtdeAprovada = coletaPrecoDto.getQuantidadeAprovada();
            qtdeAprovada += itemAuxDto.getQuantidade();
            coletaPrecoDto.setQuantidadeAprovada(qtdeAprovada);
            coletaPrecoDao.update(coletaPrecoDto);
        }
    }

}
