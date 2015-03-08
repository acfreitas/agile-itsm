package br.com.centralit.citcorpore.negocio;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.HistoricoItemRequisicaoDTO;
import br.com.centralit.citcorpore.bean.ItemCotacaoDTO;
import br.com.centralit.citcorpore.bean.ItemRequisicaoProdutoDTO;
import br.com.centralit.citcorpore.bean.JustificativaParecerDTO;
import br.com.centralit.citcorpore.bean.ParecerDTO;
import br.com.centralit.citcorpore.bean.ProdutoDTO;
import br.com.centralit.citcorpore.bean.UnidadeMedidaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.HistoricoItemRequisicaoDao;
import br.com.centralit.citcorpore.integracao.ItemRequisicaoProdutoDao;
import br.com.centralit.citcorpore.integracao.JustificativaParecerDao;
import br.com.centralit.citcorpore.integracao.ParecerDao;
import br.com.centralit.citcorpore.integracao.ProdutoDao;
import br.com.centralit.citcorpore.integracao.UnidadeMedidaDao;
import br.com.centralit.citcorpore.util.Enumerados.AcaoItemRequisicaoProduto;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoItemRequisicaoProduto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilDatas;

import com.google.gson.Gson;

public class ItemRequisicaoProdutoServiceEjb extends CrudServiceImpl implements ItemRequisicaoProdutoService {

    private ItemRequisicaoProdutoDao dao;

    @Override
    protected ItemRequisicaoProdutoDao getDao() {
        if (dao == null) {
            dao = new ItemRequisicaoProdutoDao();
        }
        return dao;
    }

    public void alteraSituacao(final UsuarioDTO usuarioDto, final Integer idItemRequisicao, final AcaoItemRequisicaoProduto acao, final SituacaoItemRequisicaoProduto novaSituacao,
            final String complemento, final TransactionControler tc) throws Exception {
        final ItemRequisicaoProdutoDao itemRequisicaoDao = new ItemRequisicaoProdutoDao();
        ItemRequisicaoProdutoDTO itemRequisicaoDto = new ItemRequisicaoProdutoDTO();
        itemRequisicaoDto.setIdItemRequisicaoProduto(idItemRequisicao);
        itemRequisicaoDto = (ItemRequisicaoProdutoDTO) itemRequisicaoDao.restore(itemRequisicaoDto);

        itemRequisicaoDao.setTransactionControler(tc);

        itemRequisicaoDto.setSituacao(novaSituacao.name());
        itemRequisicaoDao.update(itemRequisicaoDto);
        this.geraHistorico(tc, usuarioDto, itemRequisicaoDto, acao, complemento, novaSituacao);
    }

    public void geraHistorico(final TransactionControler tc, final UsuarioDTO usuarioDto, final ItemRequisicaoProdutoDTO itemRequisicaoDto, final AcaoItemRequisicaoProduto acao,
            final String complemento, final SituacaoItemRequisicaoProduto situacao) throws Exception {
        final HistoricoItemRequisicaoDTO historicoItemRequisicaoDto = new HistoricoItemRequisicaoDTO();
        historicoItemRequisicaoDto.setIdItemRequisicao(itemRequisicaoDto.getIdItemRequisicaoProduto());
        if (usuarioDto != null && usuarioDto.getIdEmpregado() != null) {
            historicoItemRequisicaoDto.setIdResponsavel(usuarioDto.getIdEmpregado());
        }
        historicoItemRequisicaoDto.setDataHora(UtilDatas.getDataHoraAtual());
        historicoItemRequisicaoDto.setComplemento(complemento);
        historicoItemRequisicaoDto.setAcao(acao.name());
        historicoItemRequisicaoDto.setSituacao(situacao.name());
        final HistoricoItemRequisicaoDao historicoItemRequisicaoDao = new HistoricoItemRequisicaoDao();
        historicoItemRequisicaoDao.setTransactionControler(tc);
        historicoItemRequisicaoDao.create(historicoItemRequisicaoDto);
    }

    public void geraHistorico(final TransactionControler tc, final UsuarioDTO usuarioDto, final ItemRequisicaoProdutoDTO itemRequisicaoDto,
            final ItemRequisicaoProdutoDTO itemAnteriorDto, final AcaoItemRequisicaoProduto acao, final String complemento, final SituacaoItemRequisicaoProduto situacao)
            throws Exception {
        final Gson gson = new Gson();
        final HistoricoItemRequisicaoDTO historicoItemRequisicaoDto = new HistoricoItemRequisicaoDTO();
        historicoItemRequisicaoDto.setIdItemRequisicao(itemRequisicaoDto.getIdItemRequisicaoProduto());
        if (usuarioDto != null && usuarioDto.getIdEmpregado() != null) {
            historicoItemRequisicaoDto.setIdResponsavel(usuarioDto.getIdEmpregado());
        }
        historicoItemRequisicaoDto.setDataHora(UtilDatas.getDataHoraAtual());
        historicoItemRequisicaoDto.setComplemento(complemento);
        historicoItemRequisicaoDto.setAcao(acao.name());
        historicoItemRequisicaoDto.setSituacao(situacao.name());
        historicoItemRequisicaoDto.setAtributosAtuais(gson.toJson(itemRequisicaoDto));
        historicoItemRequisicaoDto.setAtributosAnteriores(gson.toJson(itemAnteriorDto));

        final HistoricoItemRequisicaoDao historicoItemRequisicaoDao = new HistoricoItemRequisicaoDao();
        historicoItemRequisicaoDao.setTransactionControler(tc);
        historicoItemRequisicaoDao.create(historicoItemRequisicaoDto);
    }

    public void recuperaRelacionamentos(final Collection<ItemRequisicaoProdutoDTO> colItens) throws Exception {
        if (colItens != null) {
            final UnidadeMedidaDao unidadeMedidaDao = new UnidadeMedidaDao();
            final ProdutoDao produtoDao = new ProdutoDao();
            final ParecerDao parecerDao = new ParecerDao();
            final JustificativaParecerDao justificativaParecerDao = new JustificativaParecerDao();
            UnidadeMedidaDTO unidDto = null;
            ProdutoDTO produtoDto = null;
            ParecerDTO parecerDto = null;
            JustificativaParecerDTO justificativaDto = null;
            for (final ItemRequisicaoProdutoDTO itemRequisicaoProdutoDto : colItens) {
                if (itemRequisicaoProdutoDto.getIdUnidadeMedida() != null) {
                    unidDto = new UnidadeMedidaDTO();
                    unidDto.setIdUnidadeMedida(itemRequisicaoProdutoDto.getIdUnidadeMedida());
                    unidDto = (UnidadeMedidaDTO) unidadeMedidaDao.restore(unidDto);
                    itemRequisicaoProdutoDto.setSiglaUnidadeMedida(unidDto.getSiglaUnidadeMedida());
                }
                if (itemRequisicaoProdutoDto.getIdProduto() != null) {
                    produtoDto = new ProdutoDTO();
                    produtoDto.setIdProduto(itemRequisicaoProdutoDto.getIdProduto());
                    produtoDto = (ProdutoDTO) produtoDao.restore(produtoDto);
                    if (produtoDto != null) {
                        itemRequisicaoProdutoDto.setCodigoProduto(produtoDto.getCodigoProduto());
                        itemRequisicaoProdutoDto.setNomeProduto(produtoDto.getNomeProduto());
                    }
                }
                if (itemRequisicaoProdutoDto.getIdParecerValidacao() != null) {
                    parecerDto = new ParecerDTO();
                    parecerDto.setIdParecer(itemRequisicaoProdutoDto.getIdParecerValidacao());
                    parecerDto = (ParecerDTO) parecerDao.restore(parecerDto);
                    if (parecerDto != null) {
                        if (parecerDto.getIdJustificativa() != null) {
                            justificativaDto = new JustificativaParecerDTO();
                            justificativaDto.setIdJustificativa(parecerDto.getIdJustificativa());
                            justificativaDto = (JustificativaParecerDTO) justificativaParecerDao.restore(justificativaDto);
                            itemRequisicaoProdutoDto.setIdJustificativaValidacao(parecerDto.getIdJustificativa());
                            if (justificativaDto != null) {
                                itemRequisicaoProdutoDto.setDescrJustificativaValidacao(justificativaDto.getDescricaoJustificativa());
                            }
                            itemRequisicaoProdutoDto.setComplemJustificativaValidacao(parecerDto.getComplementoJustificativa());
                        }
                        itemRequisicaoProdutoDto.setValidado(parecerDto.getAprovado());
                    }
                } else if (itemRequisicaoProdutoDto.getIdParecerAutorizacao() != null) {
                    parecerDto = new ParecerDTO();
                    parecerDto.setIdParecer(itemRequisicaoProdutoDto.getIdParecerAutorizacao());
                    parecerDto = (ParecerDTO) parecerDao.restore(parecerDto);
                    if (parecerDto != null) {
                        if (parecerDto.getIdJustificativa() != null) {
                            justificativaDto = new JustificativaParecerDTO();
                            justificativaDto.setIdJustificativa(parecerDto.getIdJustificativa());
                            justificativaDto = (JustificativaParecerDTO) justificativaParecerDao.restore(justificativaDto);
                            itemRequisicaoProdutoDto.setIdJustificativaAutorizacao(parecerDto.getIdJustificativa());
                            if (justificativaDto != null) {
                                itemRequisicaoProdutoDto.setDescrJustificativaAutorizacao(justificativaDto.getDescricaoJustificativa());
                            }
                            itemRequisicaoProdutoDto.setComplemJustificativaAutorizacao(parecerDto.getComplementoJustificativa());
                        }
                        itemRequisicaoProdutoDto.setAutorizado(parecerDto.getAprovado());
                    }
                }
            }
        }
    }

    @Override
    public Collection findByIdSolicitacaoServico(final Integer parm) throws Exception {
        try {
            final Collection<ItemRequisicaoProdutoDTO> col = this.getDao().findByIdSolicitacaoServico(parm);
            this.recuperaRelacionamentos(col);
            return col;
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdSolicitacaoServicoAndSituacao(final Integer parm, final SituacaoItemRequisicaoProduto[] situacao) throws Exception {
        try {
            final Collection<ItemRequisicaoProdutoDTO> result = new ArrayList();
            if (situacao != null && situacao.length > 0) {
                for (final SituacaoItemRequisicaoProduto situacaoItem : situacao) {
                    final Collection<ItemRequisicaoProdutoDTO> col = this.getDao().findByIdSolicitacaoServicoAndSituacao(parm, situacaoItem);
                    if (col != null) {
                        this.recuperaRelacionamentos(col);
                        result.addAll(col);
                    }
                }
            }
            return result;
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdSolicitacaoAndSituacaoAndTipoAtendimento(final Integer parm, final SituacaoItemRequisicaoProduto[] situacao, final String tipoAtendimento)
            throws Exception {
        try {
            final Collection<ItemRequisicaoProdutoDTO> result = new ArrayList();
            if (situacao != null && situacao.length > 0) {
                for (final SituacaoItemRequisicaoProduto situacaoItem : situacao) {
                    final Collection<ItemRequisicaoProdutoDTO> col = this.getDao().findByIdSolicitacaoAndSituacaoAndTipoAtendimento(parm, situacaoItem, tipoAtendimento);
                    if (col != null) {
                        this.recuperaRelacionamentos(col);
                        result.addAll(col);
                    }
                }
            }
            return result;
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdSolicitacaoServico(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdSolicitacaoServico(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdProduto(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdProduto(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdItemCotacao(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdItemCotacao(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection<ItemRequisicaoProdutoDTO> recuperaItensParaCotacao(final Date dataInicio, final Date dataFim, final Integer idCentroCusto, final Integer idProjeto,
            final Integer idEnderecoEntrega, final Integer idSolicitacaoServico) throws Exception {
        try {
            return this.getDao().recuperaItensParaCotacao(dataInicio, dataFim, idCentroCusto, idProjeto, idEnderecoEntrega, idSolicitacaoServico);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    public void atualizaIdItemCotacao(final UsuarioDTO usuarioDto, final ItemRequisicaoProdutoDTO itemRequisicaoDto, final ItemCotacaoDTO itemCotacaoDto,
            final TransactionControler tc) throws Exception {
        final ItemRequisicaoProdutoDao itemRequisicaoDao = new ItemRequisicaoProdutoDao();
        itemRequisicaoDao.setTransactionControler(tc);

        ItemRequisicaoProdutoDTO itemRequisicaoAuxDto = new ItemRequisicaoProdutoDTO();
        itemRequisicaoAuxDto.setIdItemRequisicaoProduto(itemRequisicaoDto.getIdItemRequisicaoProduto());
        itemRequisicaoAuxDto = (ItemRequisicaoProdutoDTO) itemRequisicaoDao.restore(itemRequisicaoAuxDto);
        itemRequisicaoAuxDto.setIdItemCotacao(itemCotacaoDto.getIdItemCotacao());
        itemRequisicaoAuxDto.setQtdeCotada(new Double(0.0));
        // itemRequisicaoAuxDto.setSituacao(SituacaoItemRequisicaoProduto.AguardandoPedido.name());
        itemRequisicaoDao.update(itemRequisicaoAuxDto);

        // geraHistorico(tc, usuarioDto, itemRequisicaoDto, "Cotação No. "+itemCotacaoDto.getIdCotacao(), SituacaoItemRequisicaoProduto.AguardandoPedido);
    }

    public void desassociaItemCotacao(final UsuarioDTO usuarioDto, final ItemCotacaoDTO itemCotacaoDto, final AcaoItemRequisicaoProduto acao, final TransactionControler tc)
            throws Exception {
        final ItemRequisicaoProdutoDao itemRequisicaoDao = new ItemRequisicaoProdutoDao();
        itemRequisicaoDao.setTransactionControler(tc);
        final Collection<ItemRequisicaoProdutoDTO> colItens = itemRequisicaoDao.findByIdItemCotacao(itemCotacaoDto.getIdItemCotacao());
        if (colItens != null) {
            for (final ItemRequisicaoProdutoDTO itemRequisicaoDto : colItens) {
                if (!itemRequisicaoDto.getSituacao().equals(SituacaoItemRequisicaoProduto.AguardandoCotacao.name())) {
                    itemRequisicaoDto.setSituacao(SituacaoItemRequisicaoProduto.AguardandoCotacao.name());
                    this.geraHistorico(tc, usuarioDto, itemRequisicaoDto, acao, null, SituacaoItemRequisicaoProduto.AguardandoCotacao);
                }
                itemRequisicaoDto.setIdItemCotacao(null);
                itemRequisicaoDto.setQtdeCotada(new Double(0.0));
                itemRequisicaoDao.update(itemRequisicaoDto);
            }
        }
    }

}
