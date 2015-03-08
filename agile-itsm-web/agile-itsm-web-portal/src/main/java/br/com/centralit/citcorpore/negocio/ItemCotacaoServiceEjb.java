package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import br.com.centralit.citcorpore.bean.CategoriaProdutoDTO;
import br.com.centralit.citcorpore.bean.ColetaPrecoDTO;
import br.com.centralit.citcorpore.bean.CotacaoDTO;
import br.com.centralit.citcorpore.bean.CriterioCotacaoCategoriaDTO;
import br.com.centralit.citcorpore.bean.CriterioItemCotacaoDTO;
import br.com.centralit.citcorpore.bean.ItemCotacaoDTO;
import br.com.centralit.citcorpore.bean.ItemRequisicaoProdutoDTO;
import br.com.centralit.citcorpore.bean.MensagemRegraNegocioDTO;
import br.com.centralit.citcorpore.bean.RequisicaoProdutoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.CategoriaProdutoDao;
import br.com.centralit.citcorpore.integracao.ColetaPrecoDao;
import br.com.centralit.citcorpore.integracao.CotacaoDao;
import br.com.centralit.citcorpore.integracao.CriterioCotacaoCategoriaDao;
import br.com.centralit.citcorpore.integracao.CriterioItemCotacaoDao;
import br.com.centralit.citcorpore.integracao.ItemCotacaoDao;
import br.com.centralit.citcorpore.integracao.ItemRequisicaoProdutoDao;
import br.com.centralit.citcorpore.integracao.RequisicaoProdutoDao;
import br.com.centralit.citcorpore.util.Enumerados.AcaoItemRequisicaoProduto;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.Enumerados.ResultadoValidacao;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoCotacao;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;

public class ItemCotacaoServiceEjb extends CrudServiceImpl implements ItemCotacaoService {

    private ItemCotacaoDao dao;

    @Override
    protected ItemCotacaoDao getDao() {
        if (dao == null) {
            dao = new ItemCotacaoDao();
        }
        return dao;
    }

    @Override
    protected void validaDelete(final Object arg0) throws Exception {
        final ItemCotacaoDTO itemCotacaoDto = (ItemCotacaoDTO) this.restore((ItemCotacaoDTO) arg0);
        CotacaoDTO cotacaoDto = new CotacaoDTO();
        cotacaoDto.setIdCotacao(itemCotacaoDto.getIdCotacao());
        cotacaoDto = (CotacaoDTO) new CotacaoDao().restore(cotacaoDto);
        if (!cotacaoDto.getSituacao().equals(SituacaoCotacao.EmAndamento.name())) {
            throw new LogicException("A situação da cotação não permite a exclusão do item.");
        }

        final Collection colItens = new ColetaPrecoDao().findByIdItemCotacao(itemCotacaoDto.getIdItemCotacao());
        if (colItens != null && !colItens.isEmpty()) {
            throw new LogicException("Exclusão não permitida. Existe pelo menos uma coleta de preços associada ao item de cotação.");
        }
    }

    @Override
    public Collection findByIdCotacao(final Integer parm) throws Exception {
        try {
            final Collection<ItemCotacaoDTO> itensCotacao = this.getDao().findByIdCotacao(parm);
            if (itensCotacao != null) {
                final ItemRequisicaoProdutoDao itemRequisicaoDao = new ItemRequisicaoProdutoDao();
                for (final ItemCotacaoDTO itemCotacaoDto : itensCotacao) {
                    itemCotacaoDto.getMensagensFmtHTML(); // Só para atualizar o atributo
                    itemCotacaoDto.getImagem(); // Só para atualizar o atributo
                    String solicitacoes = "";
                    final Collection<ItemRequisicaoProdutoDTO> itensRequisicao = itemRequisicaoDao.findByIdItemCotacao(itemCotacaoDto.getIdItemCotacao());
                    if (itensRequisicao != null) {
                        final HashMap<String, String> mapSolicitacoes = new HashMap();
                        for (final ItemRequisicaoProdutoDTO itemRequisicaoDto : itensRequisicao) {
                            if (mapSolicitacoes.get("" + itemRequisicaoDto.getIdSolicitacaoServico()) == null) {
                                if (!solicitacoes.equals("")) {
                                    solicitacoes += ", ";
                                }
                                solicitacoes += "" + itemRequisicaoDto.getIdSolicitacaoServico();
                                mapSolicitacoes.put("" + itemRequisicaoDto.getIdSolicitacaoServico(), "" + itemRequisicaoDto.getIdSolicitacaoServico());
                            }
                        }
                    }
                    itemCotacaoDto.setSolicitacoesAtendidas(solicitacoes);
                }
            }
            return itensCotacao;
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdCotacao(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdCotacao(parm);
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

    public ItemCotacaoDTO create(final UsuarioDTO usuario, ItemCotacaoDTO itemCotacaoDto, final List<ItemRequisicaoProdutoDTO> itensRequisicao, final TransactionControler tc)
            throws Exception {
        if (itemCotacaoDto.getIdCategoriaProduto() == null) {
            throw new Exception("Categoria do produto não foi informada");
        }

        String exigeFornecedorQualificado = "N";
        if (itensRequisicao != null) {
            final RequisicaoProdutoDao requisicaoProdutoDao = new RequisicaoProdutoDao();
            requisicaoProdutoDao.setTransactionControler(tc);
            for (final ItemRequisicaoProdutoDTO itemRequisicaoDto : itensRequisicao) {
                RequisicaoProdutoDTO requisicaoDto = new RequisicaoProdutoDTO();
                requisicaoDto.setIdSolicitacaoServico(itemRequisicaoDto.getIdSolicitacaoServico());
                requisicaoDto = (RequisicaoProdutoDTO) requisicaoProdutoDao.restore(requisicaoDto);
                if (requisicaoDto.getFinalidade().equalsIgnoreCase("C")) {
                    exigeFornecedorQualificado = "S";
                    break;
                }
            }
        }

        final CategoriaProdutoDao categoriaProdutoDao = new CategoriaProdutoDao();
        CategoriaProdutoDTO categoriaProdutoDto = new CategoriaProdutoDTO();
        categoriaProdutoDto.setIdCategoria(itemCotacaoDto.getIdCategoriaProduto());
        categoriaProdutoDto = (CategoriaProdutoDTO) categoriaProdutoDao.restore(categoriaProdutoDto);

        itemCotacaoDto.setPesoPrazoEntrega(categoriaProdutoDto.getPesoCotacaoPrazoEntrega());
        itemCotacaoDto.setPesoPrazoGarantia(categoriaProdutoDto.getPesoCotacaoPrazoGarantia());
        itemCotacaoDto.setPesoPrazoPagto(categoriaProdutoDto.getPesoCotacaoPrazoPagto());
        itemCotacaoDto.setPesoPreco(categoriaProdutoDto.getPesoCotacaoPreco());
        itemCotacaoDto.setPesoTaxaJuros(categoriaProdutoDto.getPesoCotacaoTaxaJuros());

        String pesoPadrao = null;
        if (itemCotacaoDto.getPesoPreco() == null) {
            pesoPadrao = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.COTACAO_PESO_PRECO, null);
            if (pesoPadrao != null && pesoPadrao.trim().length() > 0) {
                itemCotacaoDto.setPesoPreco(new Integer(pesoPadrao));
            }
        }
        if (itemCotacaoDto.getPesoPrazoEntrega() == null) {
            pesoPadrao = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.COTACAO_PESO_PRAZO_ENTREGA, null);
            if (pesoPadrao != null && pesoPadrao.trim().length() > 0) {
                itemCotacaoDto.setPesoPrazoEntrega(new Integer(pesoPadrao));
            }
        }
        if (itemCotacaoDto.getPesoPrazoGarantia() == null) {
            pesoPadrao = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.COTACAO_PESO_GARANTIA, null);
            if (pesoPadrao != null && pesoPadrao.trim().length() > 0) {
                itemCotacaoDto.setPesoPrazoGarantia(new Integer(pesoPadrao));
            }
        }
        if (itemCotacaoDto.getPesoTaxaJuros() == null) {
            pesoPadrao = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.COTACAO_PESO_JUROS, null);
            if (pesoPadrao != null && pesoPadrao.trim().length() > 0) {
                itemCotacaoDto.setPesoTaxaJuros(new Integer(pesoPadrao));
            }
        }
        if (itemCotacaoDto.getPesoPrazoPagto() == null) {
            pesoPadrao = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.COTACAO_PESO_PRAZO_PAGTO, null);
            if (pesoPadrao != null && pesoPadrao.trim().length() > 0) {
                itemCotacaoDto.setPesoPrazoPagto(new Integer(pesoPadrao));
            }
        }

        final ItemCotacaoDao itemCotacaoDao = new ItemCotacaoDao();
        itemCotacaoDao.setTransactionControler(tc);
        itemCotacaoDto.setExigeFornecedorQualificado(exigeFornecedorQualificado);
        itemCotacaoDto = (ItemCotacaoDTO) itemCotacaoDao.create(itemCotacaoDto);

        final Collection<CriterioCotacaoCategoriaDTO> colCriterios = new CriterioCotacaoCategoriaDao().findByIdCategoria(categoriaProdutoDto.getIdCategoria());
        if (colCriterios != null) {
            final CriterioItemCotacaoDao criterioItemCotacaoDao = new CriterioItemCotacaoDao();
            criterioItemCotacaoDao.setTransactionControler(tc);
            for (final CriterioCotacaoCategoriaDTO criterioCotacaoCategoriaDto : colCriterios) {
                final CriterioItemCotacaoDTO criterioItemCotacaoDto = new CriterioItemCotacaoDTO();
                criterioItemCotacaoDto.setIdItemCotacao(itemCotacaoDto.getIdItemCotacao());
                criterioItemCotacaoDto.setIdCriterio(criterioCotacaoCategoriaDto.getIdCriterio());
                criterioItemCotacaoDto.setPeso(criterioCotacaoCategoriaDto.getPesoCotacao());
                criterioItemCotacaoDao.create(criterioItemCotacaoDto);
            }
        }

        if (itensRequisicao != null) {
            final ItemRequisicaoProdutoServiceEjb itemRequisicaoService = new ItemRequisicaoProdutoServiceEjb();
            for (final ItemRequisicaoProdutoDTO itemRequisicaoProdutoDto : itensRequisicao) {
                itemRequisicaoService.atualizaIdItemCotacao(usuario, itemRequisicaoProdutoDto, itemCotacaoDto, tc);
            }
        }

        return itemCotacaoDto;
    }

    @Override
    public void delete(final IDto model) throws ServiceException, LogicException {
        ItemCotacaoDTO itemCotacaoDto = (ItemCotacaoDTO) model;
        final UsuarioDTO usuarioDto = itemCotacaoDto.getUsuarioDto();
        itemCotacaoDto = (ItemCotacaoDTO) this.restore(itemCotacaoDto);
        itemCotacaoDto.setUsuarioDto(usuarioDto);

        final CriterioItemCotacaoDao criterioItemCotacaoDao = new CriterioItemCotacaoDao();
        final ItemCotacaoDao itemCotacaoDao = new ItemCotacaoDao();
        final TransactionControler tc = new TransactionControlerImpl(itemCotacaoDao.getAliasDB());

        try {
            this.validaDelete(model);

            itemCotacaoDao.setTransactionControler(tc);
            criterioItemCotacaoDao.setTransactionControler(tc);

            tc.start();

            final ItemRequisicaoProdutoServiceEjb itemRequisicaoService = new ItemRequisicaoProdutoServiceEjb();
            itemRequisicaoService.desassociaItemCotacao(itemCotacaoDto.getUsuarioDto(), itemCotacaoDto, AcaoItemRequisicaoProduto.ExclusaoItemCotacao, tc);
            criterioItemCotacaoDao.deleteByIdItemCotacao(itemCotacaoDto.getIdItemCotacao());
            itemCotacaoDao.delete(itemCotacaoDto);

            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
    }

    @Override
    public void valida(final TransactionControler tc, final ItemCotacaoDTO itemCotacaoDto) throws Exception {
        final ColetaPrecoDao coletaPrecoDao = new ColetaPrecoDao();
        if (tc != null) {
            coletaPrecoDao.setTransactionControler(tc);
        }
        final Collection<ColetaPrecoDTO> colColetas = coletaPrecoDao.findByIdItemCotacao(itemCotacaoDto.getIdItemCotacao());
        final Collection<MensagemRegraNegocioDTO> colMensagens = new ArrayList<>();
        ResultadoValidacao resultado = ResultadoValidacao.V;

        try {
            if (colColetas == null || colColetas.isEmpty()) {
                colMensagens.add(new MensagemRegraNegocioDTO(MensagemRegraNegocioDTO.ERRO, "Nenhuma coleta de preços cadastrada"));
                resultado = ResultadoValidacao.E;
                return;
            }

            double qtde = 0;
            boolean bEmpate = false;
            for (final ColetaPrecoDTO coletaPrecoDto : colColetas) {
                if (coletaPrecoDto.getResultadoFinal() == null) {
                    continue;
                }
                if (!bEmpate) {
                    bEmpate = coletaPrecoDto.getResultadoFinal().equals(ColetaPrecoDTO.RESULT_EMPATE);
                }
                if (coletaPrecoDto.getResultadoFinal().equals(ColetaPrecoDTO.RESULT_MELHOR_COTACAO)) {
                    qtde += coletaPrecoDto.getQuantidadeCompra().doubleValue();
                }
            }

            if (bEmpate) {
                colMensagens.add(new MensagemRegraNegocioDTO(MensagemRegraNegocioDTO.ERRO, "Existe(m) empate(s)"));
                resultado = ResultadoValidacao.E;
            }

            if (qtde > itemCotacaoDto.getQuantidade().doubleValue()) {
                colMensagens.add(new MensagemRegraNegocioDTO(MensagemRegraNegocioDTO.ERRO, "Quantidade para compra é maior que a quantidade solicitada"));
                resultado = ResultadoValidacao.E;
            }

            if (!bEmpate && qtde < itemCotacaoDto.getQuantidade().doubleValue()) {
                colMensagens.add(new MensagemRegraNegocioDTO(MensagemRegraNegocioDTO.AVISO, "Quantidade para compra é menor que a quantidade solicitada"));
                if (resultado.equals(ResultadoValidacao.V)) {
                    resultado = ResultadoValidacao.A;
                }
            }
        } finally {
            itemCotacaoDto.setColMensagensValidacao(colMensagens);
            itemCotacaoDto.setResultadoValidacao(resultado.name());
        }
    }

    public void validaEAtualiza(final TransactionControler tc, final Integer idItemCotacao) throws Exception {
        final ItemCotacaoDao itemCotacaoDao = new ItemCotacaoDao();
        itemCotacaoDao.setTransactionControler(tc);
        ItemCotacaoDTO itemCotacaoDto = new ItemCotacaoDTO();
        itemCotacaoDto.setIdItemCotacao(idItemCotacao);
        itemCotacaoDto = (ItemCotacaoDTO) itemCotacaoDao.restore(itemCotacaoDto);
        this.valida(tc, itemCotacaoDto);
        itemCotacaoDao.update(itemCotacaoDto);
    }

}
