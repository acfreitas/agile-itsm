package br.com.centralit.citcorpore.negocio;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import br.com.centralit.bpm.dto.ElementoFluxoDTO;
import br.com.centralit.bpm.dto.ItemTrabalhoFluxoDTO;
import br.com.centralit.bpm.dto.TarefaFluxoDTO;
import br.com.centralit.bpm.integracao.ElementoFluxoDao;
import br.com.centralit.bpm.integracao.ItemTrabalhoFluxoDao;
import br.com.centralit.bpm.integracao.TarefaFluxoDao;
import br.com.centralit.bpm.negocio.ItemTrabalho;
import br.com.centralit.citcorpore.bean.AlcadaDTO;
import br.com.centralit.citcorpore.bean.CentroResultadoDTO;
import br.com.centralit.citcorpore.bean.CotacaoItemRequisicaoDTO;
import br.com.centralit.citcorpore.bean.EnderecoDTO;
import br.com.centralit.citcorpore.bean.EntregaItemRequisicaoDTO;
import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.bean.InspecaoEntregaItemDTO;
import br.com.centralit.citcorpore.bean.ItemRequisicaoProdutoDTO;
import br.com.centralit.citcorpore.bean.ParecerDTO;
import br.com.centralit.citcorpore.bean.ProdutoDTO;
import br.com.centralit.citcorpore.bean.ProjetoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoProdutoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bpm.negocio.ExecucaoRequisicaoProduto;
import br.com.centralit.citcorpore.integracao.CentroResultadoDao;
import br.com.centralit.citcorpore.integracao.CotacaoItemRequisicaoDao;
import br.com.centralit.citcorpore.integracao.EnderecoDao;
import br.com.centralit.citcorpore.integracao.GrupoEmpregadoDao;
import br.com.centralit.citcorpore.integracao.InspecaoEntregaItemDao;
import br.com.centralit.citcorpore.integracao.ItemRequisicaoProdutoDao;
import br.com.centralit.citcorpore.integracao.ParecerDao;
import br.com.centralit.citcorpore.integracao.ProdutoDao;
import br.com.centralit.citcorpore.integracao.ProjetoDao;
import br.com.centralit.citcorpore.integracao.RequisicaoProdutoDao;
import br.com.centralit.citcorpore.integracao.SolicitacaoServicoDao;
import br.com.centralit.citcorpore.negocio.alcada.AlcadaCompras;
import br.com.centralit.citcorpore.util.Enumerados.AcaoItemRequisicaoProduto;
import br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia;
import br.com.centralit.citcorpore.util.Enumerados.OrigemOcorrencia;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoCotacaoItemRequisicao;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoEntregaItemRequisicao;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoItemRequisicaoProduto;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServico;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Reflexao;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilFormatacao;
import br.com.citframework.util.UtilStrings;
import br.com.citframework.util.WebUtil;

public class RequisicaoProdutoServiceEjb extends ComplemInfSolicitacaoServicoServiceEjb implements RequisicaoProdutoService {

    private RequisicaoProdutoDao dao;

    @Override
    protected RequisicaoProdutoDao getDao() {
        if (dao == null) {
            dao = new RequisicaoProdutoDao();
        }
        return dao;
    }

    @Override
    public IDto deserializaObjeto(final String serialize) throws Exception {
        RequisicaoProdutoDTO requisicaoProdutoDto = null;

        if (serialize != null) {
            requisicaoProdutoDto = (RequisicaoProdutoDTO) WebUtil.deserializeObject(RequisicaoProdutoDTO.class, serialize);
            if (requisicaoProdutoDto != null && requisicaoProdutoDto.getItensRequisicao_serialize() != null) {
                requisicaoProdutoDto
                        .setItensRequisicao(WebUtil.deserializeCollectionFromString(ItemRequisicaoProdutoDTO.class, requisicaoProdutoDto.getItensRequisicao_serialize()));
            }
            if (requisicaoProdutoDto != null && requisicaoProdutoDto.getItensCotacao_serialize() != null) {
                requisicaoProdutoDto.setItensCotacao(WebUtil.deserializeCollectionFromString(CotacaoItemRequisicaoDTO.class, requisicaoProdutoDto.getItensCotacao_serialize()));
            }
            if (requisicaoProdutoDto != null && requisicaoProdutoDto.getItensEntrega_serialize() != null) {
                requisicaoProdutoDto.setItensEntrega(WebUtil.deserializeCollectionFromString(EntregaItemRequisicaoDTO.class, requisicaoProdutoDto.getItensEntrega_serialize()));
            }
        }

        return requisicaoProdutoDto;
    }

    @Override
    public void validaCreate(final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {
        this.validaAtualizacao(solicitacaoServicoDto, model, null);
    }

    @Override
    public void validaDelete(final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {

    }

    @Override
    public void validaUpdate(final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {
        this.validaAtualizacao(solicitacaoServicoDto, model, null);
    }

    public void validaSolicitanteAutorizado(final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model, final TransactionControler tc) throws Exception {
        String permiteRequisicao = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.PEMITE_REQUISICAO_EMPREGADO_COMPRAS, "N");
        if (permiteRequisicao == null || permiteRequisicao.equals("")) {
            permiteRequisicao = "N";
        }
        if (permiteRequisicao.equals("S")) {
            return;
        }

        final String idGrupo = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_REQ_PRODUTOS, null);
        if (idGrupo == null || idGrupo.trim().equals("")) {
            throw new LogicException("Grupo padrão de requisição de produtos não parametrizado");
        }

        final GrupoEmpregadoDao grupoEmpregadoDao = new GrupoEmpregadoDao();
        if (tc != null) {
            grupoEmpregadoDao.setTransactionControler(tc);
        }

        final Collection<GrupoEmpregadoDTO> colGrupos = grupoEmpregadoDao.findByIdEmpregado(solicitacaoServicoDto.getIdSolicitante());
        if (colGrupos != null) {
            final int id = new Integer(idGrupo).intValue();
            for (final GrupoEmpregadoDTO grupoEmpregadoDto : colGrupos) {
                if (grupoEmpregadoDto.getIdGrupo().intValue() == id) {
                    throw new LogicException("não é permitido que um empregado do grupo de Compras faça uma requisição de produtos");
                }
            }
        }
    }

    public void validaAtualizacao(final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model, final TransactionControler tc) throws Exception {
        this.validaSolicitanteAutorizado(solicitacaoServicoDto, model, tc);
        final RequisicaoProdutoDTO requisicaoProdutoDto = (RequisicaoProdutoDTO) model;

        if (requisicaoProdutoDto.getIdSolicitacaoServico() != null) {
            final SolicitacaoServicoDao solicitacaoServicoDao = new SolicitacaoServicoDao();
            if (tc != null) {
                solicitacaoServicoDao.setTransactionControler(tc);
            }
            final SolicitacaoServicoDTO solicitacaoAuxDto = solicitacaoServicoDao.restoreAll(solicitacaoServicoDto.getIdSolicitacaoServico());

            Reflexao.copyPropertyValues(solicitacaoAuxDto, requisicaoProdutoDto);
            final RequisicaoProdutoDao requisicaoProdutoDao = new RequisicaoProdutoDao();

            if (tc != null) {
                requisicaoProdutoDao.setTransactionControler(tc);
            }
            RequisicaoProdutoDTO requisicaoAnteriorDto = new RequisicaoProdutoDTO();
            requisicaoAnteriorDto.setIdSolicitacaoServico(requisicaoProdutoDto.getIdSolicitacaoServico());
            requisicaoAnteriorDto = (RequisicaoProdutoDTO) requisicaoProdutoDao.restore(requisicaoAnteriorDto);
            requisicaoProdutoDto.setRequisicaoAnteriorDto(requisicaoAnteriorDto);
            Reflexao.copyPropertyValues(solicitacaoAuxDto, requisicaoAnteriorDto);
        }

        this.validaCentroResultado(requisicaoProdutoDto, tc);
        this.validaProjeto(requisicaoProdutoDto, tc);

        final ItemRequisicaoProdutoDao itemRequisicaoProdutoDao = new ItemRequisicaoProdutoDao();
        if (tc != null) {
            itemRequisicaoProdutoDao.setTransactionControler(tc);
        }

        if (requisicaoProdutoDto.getRejeitada() == null || requisicaoProdutoDto.getRejeitada().trim().length() == 0) {
            requisicaoProdutoDto.setRejeitada("N");
        }
        requisicaoProdutoDto.setExigeNovaAprovacao("N");
        requisicaoProdutoDto.setItemAlterado("N");
        if (UtilStrings.nullToVazio(requisicaoProdutoDto.getAcao()).equalsIgnoreCase(RequisicaoProdutoDTO.ACAO_APROVACAO)) {
            this.verificaAlcada(requisicaoProdutoDto, tc);
            if (requisicaoProdutoDto.getItensCotacao() != null && requisicaoProdutoDto.getExigeNovaAprovacao().equalsIgnoreCase("N")) {
                final Collection<CotacaoItemRequisicaoDTO> colItens = requisicaoProdutoDto.getItensCotacao();
                for (final CotacaoItemRequisicaoDTO itemDto : colItens) {
                    if (itemDto.getAprovado() == null || itemDto.getAprovado().trim().length() == 0) {
                        throw new LogicException("Parecer não informado para o item '" + itemDto.getDescricaoItem() + "'");
                    }
                    if (itemDto.getAprovado().equalsIgnoreCase("N") && itemDto.getIdJustificativa() == null) {
                        throw new LogicException("Justificativa não informada para o item '" + itemDto.getDescricaoItem() + "'");
                    }
                    if (itemDto.getAprovado().equalsIgnoreCase("S") && itemDto.getPercVariacaoPreco() == null) {
                        throw new LogicException("Percentual variação de preço a maior não informado para o item '" + itemDto.getDescricaoItem() + "'");
                    }
                }
            }
        } else if (UtilStrings.nullToVazio(requisicaoProdutoDto.getAcao()).equalsIgnoreCase(RequisicaoProdutoDTO.ACAO_INSPECAO)) {
            final Collection<EntregaItemRequisicaoDTO> colEntregas = requisicaoProdutoDto.getItensEntrega();
            if (colEntregas != null) {
                final InspecaoEntregaItemDao inspecaoEntregaDao = new InspecaoEntregaItemDao();
                for (final EntregaItemRequisicaoDTO entregaDto : colEntregas) {
                    if (entregaDto.getAprovado() == null || entregaDto.getAprovado().trim().length() == 0) {
                        throw new LogicException("Parecer não informado para a entrega '" + entregaDto.getDescricaoItem() + "'");
                    }
                    if (entregaDto.getAprovado().equalsIgnoreCase("N") && entregaDto.getIdJustificativa() == null) {
                        throw new LogicException("Justificativa não informada para a entrega '" + entregaDto.getDescricaoItem() + "'");
                    }
                    if (entregaDto.getAprovado().equalsIgnoreCase("N")) {
                        continue;
                    }
                    final Collection<InspecaoEntregaItemDTO> colInspecoes = inspecaoEntregaDao.findByIdEntrega(entregaDto.getIdEntrega());
                    if (colInspecoes == null || colInspecoes.size() == 0) {
                        throw new LogicException("Existe pelo menos uma entrega não avaliada");
                    }
                }
            }
        } else {
            final ProdutoDao produtoDao = new ProdutoDao();
            if (tc != null) {
                produtoDao.setTransactionControler(tc);
            }

            if (UtilStrings.nullToVazio(requisicaoProdutoDto.getAcao()).equalsIgnoreCase(RequisicaoProdutoDTO.ACAO_CRIACAO)) {
                if (requisicaoProdutoDto.getItensRequisicao() == null) {
                    throw new LogicException("não foi informado nenhum item da requisição");
                }
                String percVariacaoPrecoStr = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.PERC_MAX_VAR_PRECO_COTACAO, "0,0");
                if (percVariacaoPrecoStr == null || percVariacaoPrecoStr.equals("")) {
                    percVariacaoPrecoStr = "0,0";
                }
                for (final ItemRequisicaoProdutoDTO itemRequisicaoDto : requisicaoProdutoDto.getItensRequisicao()) {
                    if (itemRequisicaoDto.getIdItemRequisicaoProduto() != null) {
                        ItemRequisicaoProdutoDTO itemAuxDto = new ItemRequisicaoProdutoDTO();
                        itemAuxDto.setIdItemRequisicaoProduto(itemRequisicaoDto.getIdItemRequisicaoProduto());
                        itemAuxDto = (ItemRequisicaoProdutoDTO) itemRequisicaoProdutoDao.restore(itemAuxDto);
                        itemRequisicaoDto.setItemAnteriorDto(itemAuxDto);
                    }

                    if (itemRequisicaoDto.getIdProduto() != null && itemRequisicaoDto.getIdProduto().intValue() < 0) {
                        itemRequisicaoDto.setIdProduto(null);
                    }
                    if (percVariacaoPrecoStr != null) {
                        itemRequisicaoDto.setPercVariacaoPreco(new Double(percVariacaoPrecoStr.replaceAll(",", ".")));
                    }
                    itemRequisicaoDto.setQtdeAprovada(itemRequisicaoDto.getQuantidade());
                    itemRequisicaoDto.setTipoIdentificacao("D");
                    if (itemRequisicaoDto.getIdProduto() != null && itemRequisicaoDto.getIdItemRequisicaoProduto() == null) {
                        ProdutoDTO produtoDto = new ProdutoDTO();
                        produtoDto.setIdProduto(itemRequisicaoDto.getIdProduto());
                        produtoDto = (ProdutoDTO) produtoDao.restore(produtoDto);
                        if (produtoDto != null) {
                            itemRequisicaoDto.setIdCategoriaProduto(produtoDto.getIdCategoria());
                            itemRequisicaoDto.setDescricaoItem(produtoDto.getIdentificacao());
                            itemRequisicaoDto.setIdUnidadeMedida(produtoDto.getIdUnidadeMedida());
                            itemRequisicaoDto.setEspecificacoes(produtoDto.getDetalhes());
                            itemRequisicaoDto.setPrecoAproximado(produtoDto.getPrecoMercado());
                            itemRequisicaoDto.setMarcaPreferencial(produtoDto.getNomeMarca());
                            itemRequisicaoDto.setTipoIdentificacao("S");
                            itemRequisicaoDto.setValorAprovado(new Double(0));
                        }
                    }
                    if (itemRequisicaoDto.getPrecoAproximado() == null) {
                        itemRequisicaoDto.setPrecoAproximado(new Double(0));
                    }
                }
            }
            if (requisicaoProdutoDto.getRejeitada().equalsIgnoreCase("S")) {
                if (UtilStrings.nullToVazio(requisicaoProdutoDto.getAcao()).equalsIgnoreCase(RequisicaoProdutoDTO.ACAO_ACOMPANHAMENTO)) {
                    final Collection<ItemRequisicaoProdutoDTO> colItens = new ItemRequisicaoProdutoDao().findByIdSolicitacaoServico(requisicaoProdutoDto.getIdSolicitacaoServico());
                    requisicaoProdutoDto.setItensRequisicao(colItens);
                    for (final ItemRequisicaoProdutoDTO itemDto : colItens) {
                        if (itemDto.cotacaoIniciada()) {
                            throw new LogicException("O item '" + itemDto.getDescricaoItem().trim() + "' está em processo de cotação.\nA requisição não pode ser rejeitada.");
                        }
                    }
                }
            }
            if (UtilStrings.nullToVazio(requisicaoProdutoDto.getAcao()).equalsIgnoreCase(RequisicaoProdutoDTO.ACAO_VALIDACAO)) {
                final Collection<ItemRequisicaoProdutoDTO> colItens = requisicaoProdutoDto.getItensRequisicao();
                for (final ItemRequisicaoProdutoDTO itemDto : colItens) {
                    ItemRequisicaoProdutoDTO itemAuxDto = new ItemRequisicaoProdutoDTO();
                    itemAuxDto.setIdItemRequisicaoProduto(itemDto.getIdItemRequisicaoProduto());
                    itemAuxDto = (ItemRequisicaoProdutoDTO) itemRequisicaoProdutoDao.restore(itemAuxDto);
                    itemDto.setItemAnteriorDto(itemAuxDto);

                    if (requisicaoProdutoDto.getRejeitada().equalsIgnoreCase("S")) {
                        continue;
                    }

                    if (!itemDto.dadosAlterados() && !itemDto.getSituacao().equals(SituacaoItemRequisicaoProduto.AguardandoAutorizacaoCompra.name())) {
                        if (itemDto.getValidado() == null || itemDto.getValidado().trim().length() == 0) {
                            throw new LogicException("Parecer não informado para o item '" + itemDto.getDescricaoItem() + "'");
                        }
                        if (itemDto.getValidado().equalsIgnoreCase("S") && itemDto.getIdProduto() == null && itemDto.getIdCategoriaProduto() == null) {
                            throw new LogicException("Categoria do produto não informada para o item '" + itemDto.getDescricaoItem() + "'");
                        }
                        if (itemDto.getValidado().equalsIgnoreCase("N") && itemDto.getIdJustificativaValidacao() == null) {
                            throw new LogicException("Justificativa não informada para o item '" + itemDto.getDescricaoItem() + "'");
                        }
                        if (itemDto.getValidado().equalsIgnoreCase("S") && itemDto.getTipoAtendimento() == null) {
                            throw new LogicException("Tipo de atendimento não informado para o item '" + itemDto.getDescricaoItem() + "'");
                        }
                    }
                    if (itemDto.getIdProduto() != null && itemDto.getIdCategoriaProduto() == null) {
                        ProdutoDTO produtoDto = new ProdutoDTO();
                        produtoDto.setIdProduto(itemDto.getIdProduto());
                        produtoDto = (ProdutoDTO) produtoDao.restore(produtoDto);
                        itemDto.setIdCategoriaProduto(produtoDto.getIdCategoria());
                    }
                }
            }
            if (UtilStrings.nullToVazio(requisicaoProdutoDto.getAcao()).equalsIgnoreCase(RequisicaoProdutoDTO.ACAO_AUTORIZACAO)) {
                this.verificaAlcada(requisicaoProdutoDto, tc);
                if (requisicaoProdutoDto.getExigeNovaAprovacao().equalsIgnoreCase("N")) {
                    final Collection<ItemRequisicaoProdutoDTO> colItens = requisicaoProdutoDto.getItensRequisicao();
                    for (final ItemRequisicaoProdutoDTO itemDto : colItens) {
                        ItemRequisicaoProdutoDTO itemAuxDto = new ItemRequisicaoProdutoDTO();
                        itemAuxDto.setIdItemRequisicaoProduto(itemDto.getIdItemRequisicaoProduto());
                        itemAuxDto = (ItemRequisicaoProdutoDTO) itemRequisicaoProdutoDao.restore(itemAuxDto);
                        itemDto.setItemAnteriorDto(itemAuxDto);

                        if (requisicaoProdutoDto.getRejeitada().equalsIgnoreCase("S")) {
                            continue;
                        }

                        if (itemDto.getAutorizado() == null || itemDto.getAutorizado().trim().length() == 0) {
                            throw new LogicException("Parecer não informado para o item '" + itemDto.getDescricaoItem() + "'");
                        }
                        if (itemDto.getAutorizado().equalsIgnoreCase("N") && itemDto.getIdJustificativaAutorizacao() == null) {
                            throw new LogicException("Justificativa não informada para o item '" + itemDto.getDescricaoItem() + "'");
                        }
                        if (itemDto.getAutorizado().equalsIgnoreCase("S")) {
                            if (itemDto.getQtdeAprovada() == null || itemDto.getQtdeAprovada().intValue() == 0) {
                                throw new LogicException("Quantidade aprovada não informada para o item '" + itemDto.getDescricaoItem() + "'");
                            }
                        }
                    }
                }
            }
        }
    }

    private void validaProjeto(final RequisicaoProdutoDTO requisicaoProdutoDto, final TransactionControler tc) throws Exception {
        ProjetoDTO projetoDto = null;
        if (requisicaoProdutoDto.getIdProjeto() != null) {
            final ProjetoDao projetoDao = new ProjetoDao();
            if (tc != null) {
                projetoDao.setTransactionControler(tc);
            }

            projetoDto = new ProjetoDTO();
            projetoDto.setIdProjeto(requisicaoProdutoDto.getIdProjeto());
            projetoDto = (ProjetoDTO) projetoDao.restore(projetoDto);
        }
        if (projetoDto == null) {
            throw new LogicException("Projeto não encontrado");
        }
        if (projetoDto.getIdProjetoPai() == null) {
            throw new LogicException("Você não pode escolher um projeto raiz. Selecione um projeto de nível analítico.");
        }
    }

    private void validaCentroResultado(final RequisicaoProdutoDTO requisicaoProdutoDto, final TransactionControler tc) throws Exception {
        CentroResultadoDTO centroCustoDto = null;
        if (requisicaoProdutoDto.getIdCentroCusto() != null) {
            final CentroResultadoDao centroResultadoDao = new CentroResultadoDao();
            if (tc != null) {
                centroResultadoDao.setTransactionControler(tc);
            }

            centroCustoDto = new CentroResultadoDTO();
            centroCustoDto.setIdCentroResultado(requisicaoProdutoDto.getIdCentroCusto());
            centroCustoDto = (CentroResultadoDTO) centroResultadoDao.restore(centroCustoDto);
            requisicaoProdutoDto.setCentroCustoDto(centroCustoDto);
        }
        if (centroCustoDto == null) {
            throw new LogicException("Centro de custo não encontrado");
        }
        if (centroCustoDto.getIdCentroResultadoPai() == null || centroCustoDto.getPermiteRequisicaoProduto() == null
                || !centroCustoDto.getPermiteRequisicaoProduto().equalsIgnoreCase("S")) {
            throw new LogicException("Centro de custo não permite requisição de produtos e serviços");
        }
    }

    private void verificaAlcada(final RequisicaoProdutoDTO requisicaoProdutoDto, final TransactionControler tc) throws Exception {
        if (!UtilStrings.nullToVazio(requisicaoProdutoDto.getRejeitada()).equalsIgnoreCase("S") && requisicaoProdutoDto.centroCustoAlterado()) {
            final CentroResultadoDao centroResultadoDao = new CentroResultadoDao();
            if (tc != null) {
                centroResultadoDao.setTransactionControler(tc);
            }

            CentroResultadoDTO centroCustoDto = new CentroResultadoDTO();
            centroCustoDto.setIdCentroResultado(requisicaoProdutoDto.getRequisicaoAnteriorDto().getIdCentroCusto());
            centroCustoDto = (CentroResultadoDTO) centroResultadoDao.restore(centroCustoDto);
            requisicaoProdutoDto.getRequisicaoAnteriorDto().setCentroCustoDto(centroCustoDto);

            if (!UtilStrings.nullToVazio(requisicaoProdutoDto.getRejeitada()).equalsIgnoreCase("S")) {
                final AlcadaCompras alcada = new AlcadaCompras();
                final AlcadaDTO alcadaAtualDto = alcada.determinaAlcada(requisicaoProdutoDto, requisicaoProdutoDto.getCentroCustoDto(), tc);
                final AlcadaDTO alcadaAnteriorDto = alcada.determinaAlcada(requisicaoProdutoDto.getRequisicaoAnteriorDto(), requisicaoProdutoDto.getRequisicaoAnteriorDto()
                        .getCentroCustoDto(), tc);
                if (!alcadaAtualDto.mesmosResponsaveis(alcadaAnteriorDto.getColResponsaveis())) {
                    requisicaoProdutoDto.setExigeNovaAprovacao("S");
                }
            }
        }
    }

    @Override
    public IDto create(final TransactionControler tc, final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {
        RequisicaoProdutoDTO requisicaoProdutoDto = (RequisicaoProdutoDTO) model;
        requisicaoProdutoDto.setAcao(RequisicaoProdutoDTO.ACAO_CRIACAO);

        final SolicitacaoServicoDao solicitacaoServicoDao = new SolicitacaoServicoDao();
        final RequisicaoProdutoDao requisicaoProdutoDao = new RequisicaoProdutoDao();
        final ItemRequisicaoProdutoDao itemRequisicaoProdutoDao = new ItemRequisicaoProdutoDao();

        this.validaAtualizacao(solicitacaoServicoDto, model, tc);

        requisicaoProdutoDao.setTransactionControler(tc);
        itemRequisicaoProdutoDao.setTransactionControler(tc);
        solicitacaoServicoDao.setTransactionControler(tc);

        requisicaoProdutoDto.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
        requisicaoProdutoDto = (RequisicaoProdutoDTO) requisicaoProdutoDao.create(requisicaoProdutoDto);

        String determinaUrgenciaImpacto = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.DETERMINA_URGENCIA_IMPACTO_REQPROD, null);
        if (determinaUrgenciaImpacto == null || determinaUrgenciaImpacto.trim().equals("")) {
            determinaUrgenciaImpacto = "N";
        }

        if (determinaUrgenciaImpacto.equals("S")) {
            if (requisicaoProdutoDto.getFinalidade().equalsIgnoreCase("C")) {
                solicitacaoServicoDto.setUrgencia("A");
                solicitacaoServicoDto.setImpacto("A");
                solicitacaoServicoDto.setIdPrioridade(new Integer(1));
            } else {
                solicitacaoServicoDto.setUrgencia("M");
                solicitacaoServicoDto.setImpacto("M");
                solicitacaoServicoDto.setIdPrioridade(new Integer(2));
            }
            solicitacaoServicoDao.atualizaUrgenciaImpacto(solicitacaoServicoDto);
        }

        if (requisicaoProdutoDto.getItensRequisicao() != null) {
            final ItemRequisicaoProdutoServiceEjb itemRequisicaoService = new ItemRequisicaoProdutoServiceEjb();
            for (final ItemRequisicaoProdutoDTO itemRequisicaoDto : requisicaoProdutoDto.getItensRequisicao()) {
                itemRequisicaoDto.setIdSolicitacaoServico(requisicaoProdutoDto.getIdSolicitacaoServico());
                itemRequisicaoDto.setSituacao(SituacaoItemRequisicaoProduto.AguardandoValidacao.name());
                itemRequisicaoProdutoDao.create(itemRequisicaoDto);
                itemRequisicaoService.geraHistorico(tc, solicitacaoServicoDto.getUsuarioDto(), itemRequisicaoDto, AcaoItemRequisicaoProduto.Criacao, null,
                        SituacaoItemRequisicaoProduto.valueOf(itemRequisicaoDto.getSituacao()));
            }
        }

        return requisicaoProdutoDto;
    }

    @Override
    public void delete(final TransactionControler tc, final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {

    }

    public void cancelaSolicitacao(final TransactionControler tc, final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {
        final SolicitacaoServicoDao solicitacaoDao = new SolicitacaoServicoDao();
        solicitacaoDao.setTransactionControler(tc);
        solicitacaoServicoDto.setSituacao(SituacaoSolicitacaoServico.Cancelada.name());
        solicitacaoDao.atualizaSituacao(solicitacaoServicoDto);
    }

    @Override
    public void update(final TransactionControler tc, final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {
        final RequisicaoProdutoDTO requisicaoProdutoDto = (RequisicaoProdutoDTO) model;
        requisicaoProdutoDto.setIdSolicitante(solicitacaoServicoDto.getIdSolicitante());

        final RequisicaoProdutoDao requisicaoProdutoDao = new RequisicaoProdutoDao();
        final ItemRequisicaoProdutoDao itemRequisicaoProdutoDao = new ItemRequisicaoProdutoDao();
        final CotacaoItemRequisicaoServiceEjb cotacaoItemRequisicaoService = new CotacaoItemRequisicaoServiceEjb();
        final EntregaItemRequisicaoServiceEjb entregaItemRequisicaoService = new EntregaItemRequisicaoServiceEjb();

        this.validaAtualizacao(solicitacaoServicoDto, model, tc);

        final ParecerServiceEjb parecerService = new ParecerServiceEjb();
        requisicaoProdutoDao.setTransactionControler(tc);
        itemRequisicaoProdutoDao.setTransactionControler(tc);

        if (UtilStrings.nullToVazio(requisicaoProdutoDto.getAcao()).equalsIgnoreCase(RequisicaoProdutoDTO.ACAO_APROVACAO)) {
            if (requisicaoProdutoDto.getExigeNovaAprovacao().equalsIgnoreCase("N")) {
                final Collection<CotacaoItemRequisicaoDTO> colItens = requisicaoProdutoDto.getItensCotacao();
                for (final CotacaoItemRequisicaoDTO itemDto : colItens) {
                    cotacaoItemRequisicaoService.atualizaAprovacaoCotacao(itemDto, solicitacaoServicoDto.getUsuarioDto(), tc);
                }
            } else {
                new CotacaoItemRequisicaoServiceEjb().cancelaTarefaAprovacaoCotacao(requisicaoProdutoDto.getIdSolicitacaoServico(), "Alteração do centro de custo",
                        solicitacaoServicoDto.getUsuarioDto(), tc);
                OcorrenciaSolicitacaoServiceEjb.create(solicitacaoServicoDto, null, "Alteração do centro de custo pelo aprovador", OrigemOcorrencia.OUTROS,
                        CategoriaOcorrencia.Atualizacao, null, CategoriaOcorrencia.Atualizacao.getDescricao(), solicitacaoServicoDto.getUsuarioDto(), 0, null, tc);
            }
        } else if (UtilStrings.nullToVazio(requisicaoProdutoDto.getAcao()).equalsIgnoreCase(RequisicaoProdutoDTO.ACAO_INSPECAO)) {
            final Collection<EntregaItemRequisicaoDTO> colEntregas = requisicaoProdutoDto.getItensEntrega();
            for (final EntregaItemRequisicaoDTO entregaDto : colEntregas) {
                entregaItemRequisicaoService.atualizaSituacao(entregaDto, solicitacaoServicoDto.getUsuarioDto(), tc);
            }
        } else if (UtilStrings.nullToVazio(requisicaoProdutoDto.getAcao()).equalsIgnoreCase(RequisicaoProdutoDTO.ACAO_GARANTIA)) {
            final Collection<EntregaItemRequisicaoDTO> colEntregas = requisicaoProdutoDto.getItensEntrega();
            if (colEntregas != null) {
                for (final EntregaItemRequisicaoDTO entregaDto : colEntregas) {
                    entregaItemRequisicaoService.atualizaAcionamentoGarantia(entregaDto, solicitacaoServicoDto.getUsuarioDto(), tc);
                }
            }
        } else if (requisicaoProdutoDto.getItensRequisicao() != null) {
            final ItemRequisicaoProdutoServiceEjb itemRequisicaoService = new ItemRequisicaoProdutoServiceEjb();
            if (UtilStrings.nullToVazio(requisicaoProdutoDto.getAcao()).equalsIgnoreCase(RequisicaoProdutoDTO.ACAO_VALIDACAO)) {
                final boolean bExigeAutorizacao = new ExecucaoRequisicaoProduto(requisicaoProdutoDto, tc).exigeAutorizacao(requisicaoProdutoDto);
                int naoValidos = 0;
                int alterados = 0;
                final Collection<ItemRequisicaoProdutoDTO> colItens = requisicaoProdutoDto.getItensRequisicao();
                final Collection<ItemRequisicaoProdutoDTO> colItensAtuais = new ArrayList();
                for (final ItemRequisicaoProdutoDTO itemDto : colItens) {
                    final ItemRequisicaoProdutoDTO itemAuxDto = itemDto.getItemAnteriorDto();
                    if (itemDto.dadosAlterados()) {
                        alterados++;
                        itemRequisicaoService.geraHistorico(tc, solicitacaoServicoDto.getUsuarioDto(), itemDto, itemAuxDto, AcaoItemRequisicaoProduto.Validacao, "",
                                SituacaoItemRequisicaoProduto.AlteradoCompras);
                        itemDto.setSituacao(SituacaoItemRequisicaoProduto.AlteradoCompras.name());
                        itemRequisicaoProdutoDao.updateNotNull(itemDto);
                        colItensAtuais.add(itemDto);
                    } else {
                        itemAuxDto.setIdProduto(itemDto.getIdProduto());
                        itemAuxDto.setTipoAtendimento(itemDto.getTipoAtendimento());
                        itemAuxDto.setIdCategoriaProduto(itemDto.getIdCategoriaProduto());
                        if (requisicaoProdutoDto.getRejeitada() != null && requisicaoProdutoDto.getRejeitada().equalsIgnoreCase("S")) {
                            itemAuxDto.setSituacao(SituacaoItemRequisicaoProduto.RejeitadoCompras.name());
                        } else {
                            final ParecerDTO parecerDto = parecerService.createOrUpdate(tc, itemAuxDto.getIdParecerValidacao(), solicitacaoServicoDto.getUsuarioDto(),
                                    itemDto.getIdJustificativaValidacao(), itemDto.getComplemJustificativaValidacao(), itemDto.getValidado());
                            itemAuxDto.setIdParecerValidacao(parecerDto.getIdParecer());
                            if (parecerDto.getAprovado().equalsIgnoreCase("S")) {
                                if (bExigeAutorizacao) {
                                    itemAuxDto.setSituacao(SituacaoItemRequisicaoProduto.AguardandoAutorizacaoCompra.name());
                                    itemAuxDto.setAprovaCotacao("S");
                                } else {
                                    itemAuxDto.setSituacao(SituacaoItemRequisicaoProduto.AguardandoCotacao.name());
                                    itemAuxDto.setAprovaCotacao("N");
                                }

                            } else if (parecerDto.getAprovado().equalsIgnoreCase("N")) {
                                itemAuxDto.setSituacao(SituacaoItemRequisicaoProduto.Inviabilizado.name());
                                naoValidos++;
                            }
                        }
                        itemRequisicaoProdutoDao.update(itemAuxDto);
                        colItensAtuais.add(itemAuxDto);
                    }
                }
                if (alterados > 0) {
                    requisicaoProdutoDto.setItemAlterado("S");
                }
                if (requisicaoProdutoDto.getRejeitada().equalsIgnoreCase("N") && (alterados > 0 || naoValidos == colItens.size())) {
                    requisicaoProdutoDto.setRejeitada("S");
                }
                if (requisicaoProdutoDto.getRejeitada().equalsIgnoreCase("S")) {
                    for (final ItemRequisicaoProdutoDTO itemDto : colItensAtuais) {
                        if (!itemDto.getSituacao().equals(SituacaoItemRequisicaoProduto.AlteradoCompras.name())) {
                            itemRequisicaoService.geraHistorico(tc, solicitacaoServicoDto.getUsuarioDto(), itemDto, AcaoItemRequisicaoProduto.Validacao, "",
                                    SituacaoItemRequisicaoProduto.RejeitadoCompras);
                        }
                    }
                } else {
                    for (final ItemRequisicaoProdutoDTO itemDto : colItensAtuais) {
                        itemRequisicaoService.geraHistorico(tc, solicitacaoServicoDto.getUsuarioDto(), itemDto, AcaoItemRequisicaoProduto.Validacao, null,
                                SituacaoItemRequisicaoProduto.valueOf(itemDto.getSituacao()));
                    }
                }
                if (requisicaoProdutoDto.dadosAlterados()) {
                    OcorrenciaSolicitacaoServiceEjb.create(solicitacaoServicoDto, null, "Alteração dos dados da requisição pela área de compras", OrigemOcorrencia.OUTROS,
                            CategoriaOcorrencia.Atualizacao, null, CategoriaOcorrencia.Atualizacao.getDescricao(), solicitacaoServicoDto.getUsuarioDto(), 0, null, tc);
                }
            } else if (UtilStrings.nullToVazio(requisicaoProdutoDto.getAcao()).equalsIgnoreCase(RequisicaoProdutoDTO.ACAO_AUTORIZACAO)) {
                if (requisicaoProdutoDto.dadosAlterados()) {
                    String descricao = "Alteração dos dados da requisição pelo autorizador";
                    if (requisicaoProdutoDto.centroCustoAlterado()) {
                        descricao = "Alteração do centro de custo pelo autorizador";
                    }
                    OcorrenciaSolicitacaoServiceEjb.create(solicitacaoServicoDto, null, descricao, OrigemOcorrencia.OUTROS, CategoriaOcorrencia.Atualizacao, null,
                            CategoriaOcorrencia.Atualizacao.getDescricao(), solicitacaoServicoDto.getUsuarioDto(), 0, null, tc);
                }
                if (requisicaoProdutoDto.getExigeNovaAprovacao().equalsIgnoreCase("N")) {
                    int alterados = 0;
                    int naoAutorizados = 0;
                    final Collection<ItemRequisicaoProdutoDTO> colItens = requisicaoProdutoDto.getItensRequisicao();
                    final Collection<ItemRequisicaoProdutoDTO> colItensAtuais = new ArrayList();
                    for (final ItemRequisicaoProdutoDTO itemDto : colItens) {
                        final ItemRequisicaoProdutoDTO itemAuxDto = itemDto.getItemAnteriorDto();
                        if (itemDto.dadosAlterados()) {
                            alterados++;
                            itemRequisicaoService.geraHistorico(tc, solicitacaoServicoDto.getUsuarioDto(), itemDto, itemAuxDto, AcaoItemRequisicaoProduto.Autorizacao, "",
                                    SituacaoItemRequisicaoProduto.AlteradoAutorizador);
                            itemDto.setSituacao(SituacaoItemRequisicaoProduto.AlteradoAutorizador.name());
                            itemRequisicaoProdutoDao.updateNotNull(itemDto);
                            itemDto.atribuiDadosAtuais();
                        }
                        itemAuxDto.setIdProduto(itemDto.getIdProduto());
                        if (requisicaoProdutoDto.getRejeitada() != null && requisicaoProdutoDto.getRejeitada().equalsIgnoreCase("S")) {
                            itemAuxDto.setSituacao(SituacaoItemRequisicaoProduto.RejeitadoAutorizador.name());
                        } else {
                            final ParecerDTO parecerDto = parecerService.createOrUpdate(tc, itemAuxDto.getIdParecerAutorizacao(), solicitacaoServicoDto.getUsuarioDto(),
                                    itemDto.getIdJustificativaAutorizacao(), itemDto.getComplemJustificativaAutorizacao(), itemDto.getAutorizado());
                            itemAuxDto.setIdParecerAutorizacao(parecerDto.getIdParecer());
                            if (parecerDto.getAprovado().equalsIgnoreCase("S")) {
                                itemAuxDto.setSituacao(SituacaoItemRequisicaoProduto.AguardandoCotacao.name());
                            } else if (parecerDto.getAprovado().equalsIgnoreCase("N")) {
                                itemAuxDto.setSituacao(SituacaoItemRequisicaoProduto.CompraNaoAutorizada.name());
                                naoAutorizados++;
                            }
                            itemAuxDto.setQtdeAprovada(itemDto.getQtdeAprovada());
                            itemAuxDto.setAprovaCotacao(itemDto.getAprovaCotacao());
                        }
                        itemRequisicaoProdutoDao.update(itemAuxDto);
                        colItensAtuais.add(itemAuxDto);
                    }
                    if (alterados > 0) {
                        requisicaoProdutoDto.setItemAlterado("S");
                    }
                    if (requisicaoProdutoDto.getRejeitada().equalsIgnoreCase("N") && naoAutorizados == colItens.size()) {
                        requisicaoProdutoDto.setRejeitada("S");
                    }
                    if (requisicaoProdutoDto.getRejeitada().equalsIgnoreCase("S")) {
                        for (final ItemRequisicaoProdutoDTO itemDto : colItensAtuais) {
                            itemRequisicaoService.geraHistorico(tc, solicitacaoServicoDto.getUsuarioDto(), itemDto, AcaoItemRequisicaoProduto.Autorizacao, "",
                                    SituacaoItemRequisicaoProduto.RejeitadoAutorizador);
                        }
                    } else {
                        for (final ItemRequisicaoProdutoDTO itemDto : colItensAtuais) {
                            itemRequisicaoService.geraHistorico(tc, solicitacaoServicoDto.getUsuarioDto(), itemDto, AcaoItemRequisicaoProduto.Autorizacao, null,
                                    SituacaoItemRequisicaoProduto.valueOf(itemDto.getSituacao()));
                        }
                    }
                }
            } else if (UtilStrings.nullToVazio(requisicaoProdutoDto.getAcao()).equalsIgnoreCase(RequisicaoProdutoDTO.ACAO_CRIACAO)) {
                final HashMap<String, ItemRequisicaoProdutoDTO> mapItens = new HashMap();
                for (final ItemRequisicaoProdutoDTO itemRequisicaoDto : requisicaoProdutoDto.getItensRequisicao()) {
                    if (itemRequisicaoDto.getIdItemRequisicaoProduto() == null) {
                        continue;
                    }
                    mapItens.put("" + itemRequisicaoDto.getIdItemRequisicaoProduto(), itemRequisicaoDto);
                }
                final Collection<ItemRequisicaoProdutoDTO> colItensExistentes = itemRequisicaoProdutoDao
                        .findByIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
                if (colItensExistentes != null) {
                    for (final ItemRequisicaoProdutoDTO itemRequisicaoDto : colItensExistentes) {
                        if (mapItens.get("" + itemRequisicaoDto.getIdItemRequisicaoProduto()) == null) {
                            itemRequisicaoDto.setSituacao(SituacaoItemRequisicaoProduto.Cancelado.name());
                            itemRequisicaoProdutoDao.update(itemRequisicaoDto);
                            itemRequisicaoService.geraHistorico(tc, solicitacaoServicoDto.getUsuarioDto(), itemRequisicaoDto, AcaoItemRequisicaoProduto.Cancelamento, null,
                                    SituacaoItemRequisicaoProduto.valueOf(itemRequisicaoDto.getSituacao()));
                        }
                    }
                }
                for (ItemRequisicaoProdutoDTO itemRequisicaoDto : requisicaoProdutoDto.getItensRequisicao()) {
                    itemRequisicaoDto.setIdSolicitacaoServico(requisicaoProdutoDto.getIdSolicitacaoServico());
                    if (mapItens.get("" + itemRequisicaoDto.getIdItemRequisicaoProduto()) == null) {
                        itemRequisicaoDto.setSituacao(SituacaoItemRequisicaoProduto.AguardandoValidacao.name());
                        itemRequisicaoDto = (ItemRequisicaoProdutoDTO) itemRequisicaoProdutoDao.create(itemRequisicaoDto);
                        itemRequisicaoService.geraHistorico(tc, solicitacaoServicoDto.getUsuarioDto(), itemRequisicaoDto, AcaoItemRequisicaoProduto.Criacao, null,
                                SituacaoItemRequisicaoProduto.valueOf(itemRequisicaoDto.getSituacao()));
                    } else if (itemRequisicaoDto.dadosAlterados()) {
                        itemRequisicaoDto.setSituacao(SituacaoItemRequisicaoProduto.AguardandoValidacao.name());
                        itemRequisicaoProdutoDao.update(itemRequisicaoDto);
                        itemRequisicaoService.geraHistorico(tc, solicitacaoServicoDto.getUsuarioDto(), itemRequisicaoDto, AcaoItemRequisicaoProduto.Alteracao, null,
                                SituacaoItemRequisicaoProduto.valueOf(itemRequisicaoDto.getSituacao()));
                    } else {
                        itemRequisicaoDto.setSituacao(SituacaoItemRequisicaoProduto.AguardandoAutorizacaoCompra.name());
                        itemRequisicaoProdutoDao.update(itemRequisicaoDto);
                    }
                }
            } else if (UtilStrings.nullToVazio(requisicaoProdutoDto.getAcao()).equalsIgnoreCase(RequisicaoProdutoDTO.ACAO_ACOMPANHAMENTO)
                    && requisicaoProdutoDto.getRejeitada() != null && requisicaoProdutoDto.getRejeitada().equalsIgnoreCase("S")) {
                for (final ItemRequisicaoProdutoDTO itemRequisicaoDto : requisicaoProdutoDto.getItensRequisicao()) {
                    if (itemRequisicaoDto.getSituacao().equals(SituacaoItemRequisicaoProduto.Cancelado.name())
                            || itemRequisicaoDto.getSituacao().equals(SituacaoItemRequisicaoProduto.Finalizado.name())) {
                        continue;
                    }
                    itemRequisicaoDto.setSituacao(SituacaoItemRequisicaoProduto.RejeitadoCompras.name());
                    itemRequisicaoProdutoDao.updateNotNull(itemRequisicaoDto);
                    itemRequisicaoService.geraHistorico(tc, solicitacaoServicoDto.getUsuarioDto(), itemRequisicaoDto, AcaoItemRequisicaoProduto.Alteracao, null,
                            SituacaoItemRequisicaoProduto.RejeitadoCompras);
                }
            }
        }

        requisicaoProdutoDao.update(requisicaoProdutoDto);
    }

    public Collection<ItemRequisicaoProdutoDTO> recuperaItensValidos(final RequisicaoProdutoDTO requisicaoProdutoDto, final SituacaoItemRequisicaoProduto[] situacoes,
            final TransactionControler tc) throws Exception {
        final ItemRequisicaoProdutoDao itemRequisicaoDao = new ItemRequisicaoProdutoDao();
        if (tc != null) {
            itemRequisicaoDao.setTransactionControler(tc);
        }
        final Collection<ItemRequisicaoProdutoDTO> result = new ArrayList();
        for (final SituacaoItemRequisicaoProduto situacao : situacoes) {
            final Collection<ItemRequisicaoProdutoDTO> col = itemRequisicaoDao.findByIdSolicitacaoServicoAndSituacao(requisicaoProdutoDto.getIdSolicitacaoServico(), situacao);
            if (col != null) {
                result.addAll(col);
            }
        }
        return result;
    }

    public Double calculaValorAprovadoAnual(final CentroResultadoDTO centroResultadoDto, final int anoRef, final TransactionControler tc) throws Exception {
        double valor = 0;
        final Collection<RequisicaoProdutoDTO> colRequisicoes = this.getDao().findByIdCentroCusto(centroResultadoDto.getIdCentroResultado());
        if (colRequisicoes != null) {
            final CotacaoItemRequisicaoDao cotacaoItemRequisicaoDao = new CotacaoItemRequisicaoDao();
            if (tc != null) {
                cotacaoItemRequisicaoDao.setTransactionControler(tc);
            }

            final ItemTrabalhoFluxoDao itemTrabalhoFluxoDao = new ItemTrabalhoFluxoDao();
            if (tc != null) {
                itemTrabalhoFluxoDao.setTransactionControler(tc);
            }

            final ItemRequisicaoProdutoDao itemRequisicaoDao = new ItemRequisicaoProdutoDao();
            if (tc != null) {
                itemRequisicaoDao.setTransactionControler(tc);
            }

            final ParecerDao parecerDao = new ParecerDao();
            if (tc != null) {
                parecerDao.setTransactionControler(tc);
            }

            for (final RequisicaoProdutoDTO requisicaoProdutoDto : colRequisicoes) {
                final Collection<CotacaoItemRequisicaoDTO> colItens = cotacaoItemRequisicaoDao.findByIdRequisicaoProduto(requisicaoProdutoDto.getIdSolicitacaoServico());
                if (colItens != null) {
                    for (final CotacaoItemRequisicaoDTO cotacaoItemRequisicaoDto : colItens) {
                        if (!cotacaoItemRequisicaoDto.getSituacao().equals(SituacaoCotacaoItemRequisicao.Aprovado.name())
                                && !cotacaoItemRequisicaoDto.getSituacao().equals(SituacaoCotacaoItemRequisicao.PreAprovado.name())) {
                            continue;
                        }
                        if (cotacaoItemRequisicaoDto.getValorTotal() == null) {
                            continue;
                        }

                        if (cotacaoItemRequisicaoDto.getSituacao().equals(SituacaoCotacaoItemRequisicao.Aprovado.name())) {
                            ItemTrabalhoFluxoDTO itemTrabalhoDto = new ItemTrabalhoFluxoDTO();
                            itemTrabalhoDto.setIdItemTrabalho(cotacaoItemRequisicaoDto.getIdItemTrabalho());
                            itemTrabalhoDto = (ItemTrabalhoFluxoDTO) itemTrabalhoFluxoDao.restore(itemTrabalhoDto);
                            if (itemTrabalhoDto != null && itemTrabalhoDto.getDataHoraFinalizacao() != null) {
                                final Date dataAux = new Date(itemTrabalhoDto.getDataHoraFinalizacao().getTime());
                                final int ano = UtilDatas.getYear(dataAux);
                                if (ano != anoRef) {
                                    continue;
                                }
                                valor += cotacaoItemRequisicaoDto.getValorTotal().doubleValue();
                            }
                        } else {
                            ItemRequisicaoProdutoDTO itemDto = new ItemRequisicaoProdutoDTO();
                            itemDto.setIdItemRequisicaoProduto(cotacaoItemRequisicaoDto.getIdItemRequisicaoProduto());
                            itemDto = (ItemRequisicaoProdutoDTO) itemRequisicaoDao.restore(itemDto);
                            if (itemDto.getIdParecerAutorizacao() != null) {
                                ParecerDTO parecerDto = new ParecerDTO();
                                parecerDto.setIdParecer(itemDto.getIdParecerAutorizacao());
                                parecerDto = (ParecerDTO) parecerDao.restore(parecerDto);
                                if (parecerDto != null) {
                                    final Date dataAux = new Date(parecerDto.getDataHoraParecer().getTime());
                                    final int ano = UtilDatas.getYear(dataAux);
                                    if (ano != anoRef) {
                                        continue;
                                    }
                                    valor += cotacaoItemRequisicaoDto.getValorTotal().doubleValue();
                                }
                            }
                        }
                    }
                }
            }
        }
        return valor;
    }

    public double calculaValorAprovado(final SolicitacaoServicoDTO solicitacaoServicoDto, final TransactionControler tc) throws Exception {
        double valor = 0.0;
        final CotacaoItemRequisicaoDao cotacaoItemRequisicaoDao = new CotacaoItemRequisicaoDao();
        if (tc != null) {
            cotacaoItemRequisicaoDao.setTransactionControler(tc);
        }
        final Collection<CotacaoItemRequisicaoDTO> colItens = cotacaoItemRequisicaoDao.findByIdRequisicaoProduto(solicitacaoServicoDto.getIdSolicitacaoServico());
        if (colItens != null) {
            final ItemTrabalhoFluxoDao itemTrabalhoFluxoDao = new ItemTrabalhoFluxoDao();
            if (tc != null) {
                itemTrabalhoFluxoDao.setTransactionControler(tc);
            }
            final ItemRequisicaoProdutoDao itemRequisicaoDao = new ItemRequisicaoProdutoDao();
            if (tc != null) {
                itemRequisicaoDao.setTransactionControler(tc);
            }

            for (final CotacaoItemRequisicaoDTO cotacaoItemRequisicaoDto : colItens) {
                if (!cotacaoItemRequisicaoDto.getSituacao().equals(SituacaoCotacaoItemRequisicao.Aprovado.name())
                        && !cotacaoItemRequisicaoDto.getSituacao().equals(SituacaoCotacaoItemRequisicao.PreAprovado.name())) {
                    continue;
                }
                if (cotacaoItemRequisicaoDto.getValorTotal() == null) {
                    continue;
                }

                ItemRequisicaoProdutoDTO itemDto = new ItemRequisicaoProdutoDTO();
                itemDto.setIdItemRequisicaoProduto(cotacaoItemRequisicaoDto.getIdItemRequisicaoProduto());
                itemDto = (ItemRequisicaoProdutoDTO) itemRequisicaoDao.restore(itemDto);
                if (itemDto == null || itemDto.getSituacao().equalsIgnoreCase(SituacaoItemRequisicaoProduto.Cancelado.name())) {
                    continue;
                }

                ItemTrabalhoFluxoDTO itemTrabalhoDto = new ItemTrabalhoFluxoDTO();
                itemTrabalhoDto.setIdItemTrabalho(cotacaoItemRequisicaoDto.getIdItemTrabalho());
                itemTrabalhoDto = (ItemTrabalhoFluxoDTO) itemTrabalhoFluxoDao.restore(itemTrabalhoDto);
                if (itemTrabalhoDto != null && itemTrabalhoDto.getDataHoraFinalizacao() != null && itemTrabalhoDto.getIdResponsavelAtual() != null) {
                    valor += cotacaoItemRequisicaoDto.getValorTotal().doubleValue();
                }
            }
        }
        return valor;
    }

    public Double calculaValorAprovadoMensal(final CentroResultadoDTO centroCustoDto, final int mesRef, final int anoRef, final TransactionControler tc) throws Exception {
        double valor = 0;
        final CotacaoItemRequisicaoDao cotacaoItemRequisicaoDao = new CotacaoItemRequisicaoDao();
        if (tc != null) {
            cotacaoItemRequisicaoDao.setTransactionControler(tc);
        }

        final Collection<CotacaoItemRequisicaoDTO> colItens = cotacaoItemRequisicaoDao.findAprovadasByIdCentroResultado(centroCustoDto.getIdCentroResultado());
        if (colItens != null) {
            final ParecerDao parecerDao = new ParecerDao();
            if (tc != null) {
                parecerDao.setTransactionControler(tc);
            }
            final ItemTrabalhoFluxoDao itemTrabalhoFluxoDao = new ItemTrabalhoFluxoDao();
            if (tc != null) {
                itemTrabalhoFluxoDao.setTransactionControler(tc);
            }

            for (final CotacaoItemRequisicaoDTO cotacaoItemRequisicaoDto : colItens) {
                if (cotacaoItemRequisicaoDto.getValorTotal() == null) {
                    continue;
                }

                if (cotacaoItemRequisicaoDto.getSituacao().equals(SituacaoCotacaoItemRequisicao.Aprovado.name()) && cotacaoItemRequisicaoDto.getIdItemTrabalho() != null) {
                    ItemTrabalhoFluxoDTO itemTrabalhoDto = new ItemTrabalhoFluxoDTO();
                    itemTrabalhoDto.setIdItemTrabalho(cotacaoItemRequisicaoDto.getIdItemTrabalho());
                    itemTrabalhoDto = (ItemTrabalhoFluxoDTO) itemTrabalhoFluxoDao.restore(itemTrabalhoDto);
                    if (itemTrabalhoDto != null && itemTrabalhoDto.getDataHoraFinalizacao() != null) {
                        final Date dataAux = new Date(itemTrabalhoDto.getDataHoraFinalizacao().getTime());
                        final int mes = UtilDatas.getMonth(dataAux);
                        final int ano = UtilDatas.getYear(dataAux);
                        if (mes == mesRef && ano == anoRef) {
                            valor += cotacaoItemRequisicaoDto.getValorTotal().doubleValue();
                        }
                    }
                } else if (cotacaoItemRequisicaoDto.getIdParecerAutorizacao() != null) {
                    ParecerDTO parecerDto = new ParecerDTO();
                    parecerDto.setIdParecer(cotacaoItemRequisicaoDto.getIdParecerAutorizacao());
                    parecerDto = (ParecerDTO) parecerDao.restore(parecerDto);
                    if (parecerDto != null) {
                        final Date dataAux = new Date(parecerDto.getDataHoraParecer().getTime());
                        final int mes = UtilDatas.getMonth(dataAux);
                        final int ano = UtilDatas.getYear(dataAux);
                        if (mes == mesRef && ano == anoRef) {
                            valor += cotacaoItemRequisicaoDto.getValorTotal().doubleValue();
                        }
                    }
                }
            }
        }
        return valor;
    }

    @Override
    public StringBuilder recuperaLoginAutorizadores(final Integer idSolicitacaoServico) throws Exception {
        final SolicitacaoServicoDTO solicitacaoServicoDto = new SolicitacaoServicoServiceEjb().restoreAll(idSolicitacaoServico, null);
        RequisicaoProdutoDTO requisicaoProdutoDto = new RequisicaoProdutoDTO();
        requisicaoProdutoDto.setIdSolicitacaoServico(idSolicitacaoServico);
        requisicaoProdutoDto = (RequisicaoProdutoDTO) this.getDao().restore(requisicaoProdutoDto);
        Reflexao.copyPropertyValues(solicitacaoServicoDto, requisicaoProdutoDto);
        final ExecucaoRequisicaoProduto execucaoRequisicaoProduto = new ExecucaoRequisicaoProduto(requisicaoProdutoDto, new TransactionControlerImpl(this.getDao().getAliasDB()));
        return execucaoRequisicaoProduto.recuperaLoginAutorizadores(requisicaoProdutoDto);
    }

    @Override
    public void preparaSolicitacaoParaAprovacao(final SolicitacaoServicoDTO solicitacaoDto, final ItemTrabalho itemTrabalho, final String aprovacao, final Integer idJustificativa,
            final String observacoes) throws Exception {
        RequisicaoProdutoDTO requisicaoProdutoDto = new RequisicaoProdutoDTO();
        requisicaoProdutoDto.setIdSolicitacaoServico(solicitacaoDto.getIdSolicitacaoServico());
        requisicaoProdutoDto = (RequisicaoProdutoDTO) this.restore(requisicaoProdutoDto);

        final ElementoFluxoDTO elementoDto = itemTrabalho.getElementoFluxoDto();
        if (UtilStrings.nullToVazio(elementoDto.getTemplate()).toUpperCase().indexOf("APROVACAO") >= 0) {
            requisicaoProdutoDto.setAcao(RequisicaoProdutoDTO.ACAO_APROVACAO);
            final CotacaoItemRequisicaoService cotacaoItemRequisicaoService = (CotacaoItemRequisicaoService) ServiceLocator.getInstance().getService(
                    CotacaoItemRequisicaoService.class, usuario);
            final Collection<CotacaoItemRequisicaoDTO> itensCotacao = cotacaoItemRequisicaoService.findByIdItemTrabalho(itemTrabalho.getIdItemTrabalho());
            if (itensCotacao != null) {
                requisicaoProdutoDto.setItensCotacao(itensCotacao);
                for (final CotacaoItemRequisicaoDTO cotacaoItemDto : itensCotacao) {
                    cotacaoItemDto.setAprovado(aprovacao);
                    if (aprovacao.equalsIgnoreCase("N")) {
                        cotacaoItemDto.setIdJustificativa(idJustificativa);
                        cotacaoItemDto.setComplementoJustificativa(observacoes);
                    }
                }
            }
        } else if (UtilStrings.nullToVazio(elementoDto.getTemplate()).toUpperCase().indexOf("AUTORIZACAO") >= 0) {
            requisicaoProdutoDto.setAcao(RequisicaoProdutoDTO.ACAO_AUTORIZACAO);
            if (aprovacao.equalsIgnoreCase("N")) {
                requisicaoProdutoDto.setRejeitada("S");
            }
            final ItemRequisicaoProdutoService itemRequisicaoProdutoService = (ItemRequisicaoProdutoService) ServiceLocator.getInstance().getService(
                    ItemRequisicaoProdutoService.class, usuario);
            requisicaoProdutoDto.setItensRequisicao(itemRequisicaoProdutoService.findByIdSolicitacaoServico(requisicaoProdutoDto.getIdSolicitacaoServico()));
            for (final ItemRequisicaoProdutoDTO itemRequisicaoDto : requisicaoProdutoDto.getItensRequisicao()) {
                itemRequisicaoDto.setAutorizado(aprovacao);
                if (aprovacao.equalsIgnoreCase("N")) {
                    itemRequisicaoDto.setIdJustificativaAutorizacao(idJustificativa);
                    itemRequisicaoDto.setComplemJustificativaAutorizacao(observacoes);
                }
            }
        }
        if (requisicaoProdutoDto.getAcao() != null) {
            solicitacaoDto.setInformacoesComplementares(requisicaoProdutoDto);
            solicitacaoDto.setAcaoFluxo(br.com.centralit.bpm.util.Enumerados.ACAO_EXECUTAR);
            solicitacaoDto.setIdTarefa(itemTrabalho.getIdItemTrabalho());
        }
    }

    @Override
    public String getInformacoesComplementaresFmtTexto(final SolicitacaoServicoDTO solicitacaoDto, final ItemTrabalho itemTrabalho) throws Exception {
        RequisicaoProdutoDTO requisicaoDto = new RequisicaoProdutoDTO();
        requisicaoDto.setIdSolicitacaoServico(solicitacaoDto.getIdSolicitacaoServico());
        requisicaoDto = (RequisicaoProdutoDTO) this.getDao().restore(requisicaoDto);
        Reflexao.copyPropertyValues(solicitacaoDto, requisicaoDto);

        final EnderecoDTO enderecoDto = new EnderecoDao().recuperaEnderecoComUnidade(requisicaoDto.getIdEnderecoEntrega());

        String descricao = "";
        if (requisicaoDto.getSolicitante() != null && !requisicaoDto.getSolicitante().trim().equals("")) {
            descricao += "Solicitante: " + requisicaoDto.getSolicitante() + "\n";
        }
        descricao += "Centro de Custo: " + requisicaoDto.getCentroCusto() + "\n";
        descricao += "Projeto: " + requisicaoDto.getProjeto() + "\n";
        if (enderecoDto != null && enderecoDto.getIdentificacao() != null) {
            descricao += "Unidade destino: " + enderecoDto.getIdentificacao() + "\n";
        }
        if (requisicaoDto.getFinalidade().equals("I")) {
            descricao += "Finalidade: Uso interno\n";
        } else {
            descricao += "Finalidade: Atendimento ao cliente\n";
        }

        final String descr = solicitacaoDto.getDescricaoSemFormatacao();
        if (descr != null && !descr.trim().isEmpty()) {
            descricao += "\n--------------------- DESCRIÇÃO -------------------\n";
            descricao += descr + "\n";
        }

        ElementoFluxoDTO elementoDto = itemTrabalho.getElementoFluxoDto();
        if (elementoDto == null) {
            TarefaFluxoDTO tarefaFluxoDto = new TarefaFluxoDTO();
            tarefaFluxoDto.setIdItemTrabalho(itemTrabalho.getIdItemTrabalho());
            tarefaFluxoDto = (TarefaFluxoDTO) new TarefaFluxoDao().restore(tarefaFluxoDto);
            elementoDto = new ElementoFluxoDao().restore(tarefaFluxoDto.getIdElemento());
        }
        if (elementoDto != null && !elementoDto.getTemplate().trim().equals("")) {
            if (UtilStrings.nullToVazio(elementoDto.getTemplate()).toUpperCase().indexOf("APROVACAO") >= 0) {
                final CotacaoItemRequisicaoService cotacaoItemRequisicaoService = (CotacaoItemRequisicaoService) ServiceLocator.getInstance().getService(
                        CotacaoItemRequisicaoService.class, usuario);
                final Collection<CotacaoItemRequisicaoDTO> itensCotacao = cotacaoItemRequisicaoService.findByIdItemTrabalho(itemTrabalho.getIdItemTrabalho());
                if (itensCotacao != null) {
                    descricao += "\n---------------- ITENS DA APROVAÇÃO --------------\n";
                    for (final CotacaoItemRequisicaoDTO cotacaoItemDto : itensCotacao) {
                        if (!cotacaoItemDto.getSituacao().equals(SituacaoCotacaoItemRequisicao.AguardaAprovacao.name())) {
                            continue;
                        }
                        descricao += cotacaoItemDto.getDescricaoItem().toUpperCase() + "\n";
                        descricao += "Fornecedor: " + cotacaoItemDto.getNomeFornecedor() + "\n";
                        descricao += "Qtde: " + UtilFormatacao.formatDouble(cotacaoItemDto.getQuantidade(), 2) + "\n";
                        descricao += "Preço: " + UtilFormatacao.formatDouble(cotacaoItemDto.getPreco(), 2) + "\n\n";
                    }
                }
            } else if (UtilStrings.nullToVazio(elementoDto.getTemplate()).toUpperCase().indexOf("INSPECAO") >= 0) {
                final EntregaItemRequisicaoService entregaItemRequisicaoService = (EntregaItemRequisicaoService) ServiceLocator.getInstance().getService(
                        EntregaItemRequisicaoService.class, usuario);
                final Collection<EntregaItemRequisicaoDTO> itensInspecao = entregaItemRequisicaoService.findByIdItemTrabalho(itemTrabalho.getIdItemTrabalho());
                if (itensInspecao != null) {
                    descricao += "\n----------------- ITENS DA INSPEÇÃO --------------\n";
                    for (final EntregaItemRequisicaoDTO entregaItemDto : itensInspecao) {
                        if (!entregaItemDto.getSituacao().equals(SituacaoEntregaItemRequisicao.Aguarda.name())) {
                            continue;
                        }
                        descricao += entregaItemDto.getDescricaoItem().toUpperCase() + "\n";
                        descricao += "Fornecedor: " + entregaItemDto.getNomeFornecedor() + "\n";
                        descricao += "Qtde: " + UtilFormatacao.formatDouble(entregaItemDto.getQuantidadeEntregue(), 2) + "\n\n";
                    }
                }
            } else {
                final ItemRequisicaoProdutoService itemRequisicaoProdutoService = (ItemRequisicaoProdutoService) ServiceLocator.getInstance().getService(
                        ItemRequisicaoProdutoService.class, usuario);
                final Collection<ItemRequisicaoProdutoDTO> colItens = itemRequisicaoProdutoService.findByIdSolicitacaoServico(solicitacaoDto.getIdSolicitacaoServico());
                descricao += "\n---------------- ITENS DA REQUISIÇÃO -------------\n";
                for (final ItemRequisicaoProdutoDTO itemRequisicaoDto : colItens) {
                    descricao += itemRequisicaoDto.getDescricaoItem().toUpperCase() + "\n";
                    descricao += "Qtde: " + UtilFormatacao.formatDouble(itemRequisicaoDto.getQtdeAprovada(), 2) + "\n";
                    if (itemRequisicaoDto.getPrecoAproximado() != null && itemRequisicaoDto.getPrecoAproximado().doubleValue() > 0) {
                        descricao += "Valor aproximado: " + UtilFormatacao.formatDouble(itemRequisicaoDto.getPrecoAproximado(), 2) + "\n";
                    }
                    descricao += "Situação: " + itemRequisicaoDto.getDescrSituacao() + "\n\n";
                }
            }
        }

        if (descricao == null) {
            descricao = solicitacaoDto.getDescricaoSemFormatacao();
        }
        return descricao;
    }
}
