package br.com.centralit.citcorpore.negocio;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import br.com.centralit.bpm.dto.ElementoFluxoDTO;
import br.com.centralit.bpm.negocio.ItemTrabalho;
import br.com.centralit.citcorpore.bean.CidadesDTO;
import br.com.centralit.citcorpore.bean.DespesaViagemDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.IntegranteViagemDTO;
import br.com.centralit.citcorpore.bean.ParecerDTO;
import br.com.centralit.citcorpore.bean.RequisicaoProdutoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoViagemDTO;
import br.com.centralit.citcorpore.bean.RoteiroViagemDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.integracao.DespesaViagemDAO;
import br.com.centralit.citcorpore.integracao.GrupoDao;
import br.com.centralit.citcorpore.integracao.IntegranteViagemDao;
import br.com.centralit.citcorpore.integracao.ParecerDao;
import br.com.centralit.citcorpore.integracao.RequisicaoViagemDAO;
import br.com.centralit.citcorpore.integracao.RoteiroViagemDAO;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;
import br.com.citframework.util.WebUtil;

@SuppressWarnings("unchecked")
public class AutorizacaoViagemServiceEjb extends ComplemInfSolicitacaoServicoServiceEjb implements AutorizacaoViagemService {

    private RequisicaoViagemDAO dao;

    @Override
    protected RequisicaoViagemDAO getDao() {
        if (dao == null) {
            dao = new RequisicaoViagemDAO();
        }
        return dao;
    }

    @Override
    public IDto deserializaObjeto(final String serialize) throws Exception {
        RequisicaoViagemDTO requisicaoViagemDto = null;

        if (serialize != null) {
            requisicaoViagemDto = (RequisicaoViagemDTO) WebUtil.deserializeObject(RequisicaoViagemDTO.class, serialize);
            if (requisicaoViagemDto != null && requisicaoViagemDto.getIntegranteViagemSerialize() != null) {
                requisicaoViagemDto.setIntegranteViagem(WebUtil.deserializeCollectionFromString(IntegranteViagemDTO.class, requisicaoViagemDto.getIntegranteViagemSerialize()));
            }
        }

        return requisicaoViagemDto;
    }

    @Override
    public void delete(final TransactionControler tc, final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {}

    @Override
    public void validaCreate(final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {}

    @Override
    public void validaDelete(final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {}

    @Override
    public void validaUpdate(final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {
        this.validaAprovacao(solicitacaoServicoDto, model);
    }

    @Override
    public IDto create(final TransactionControler tc, final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {
        return null;
    }

    @Override
    public void update(final TransactionControler tc, final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {
        final RequisicaoViagemDTO requisicaoViagemDto = (RequisicaoViagemDTO) model;

        this.getDao().setTransactionControler(tc);

        if (requisicaoViagemDto != null && !solicitacaoServicoDto.getDescrSituacao().equalsIgnoreCase(Enumerados.SituacaoSolicitacaoServico.Cancelada.name())) {
            ParecerDTO parecerDto = new ParecerDTO();
            final ParecerDao parecerDao = new ParecerDao();

            parecerDao.setTransactionControler(tc);

            final RequisicaoViagemDTO bean = (RequisicaoViagemDTO) model;

            if (bean != null && bean.getCancelarRequisicao() != null && bean.getCancelarRequisicao().equalsIgnoreCase("S")) {
                solicitacaoServicoDto.setSituacao(Enumerados.SituacaoSolicitacaoServico.Cancelada.name());
                this.getDao().updateNotNull(bean);
                return;
            }

            if (solicitacaoServicoDto.getIdSolicitante().intValue() == solicitacaoServicoDto.getUsuarioDto().getIdEmpregado().intValue()
                    && !(bean != null && bean.getCancelarRequisicao() != null && bean.getCancelarRequisicao().equalsIgnoreCase("S"))) {
                final GrupoDao grupoDao = new GrupoDao();
                final Collection<GrupoDTO> colGrupos = grupoDao.getGruposByIdEmpregado(solicitacaoServicoDto.getIdSolicitante().intValue());
                boolean result = true;

                if (colGrupos != null && !colGrupos.isEmpty()) {

                    for (final GrupoDTO grupoDTO : colGrupos) {
                        if (grupoDTO.getNome().equalsIgnoreCase("CENTRALIT - DIRETORES")) {
                            result = false;
                        }
                    }
                }

                if (result) {
                    throw new LogicException("Usuário sem permissão para Autorização!");
                }
            }

            if (requisicaoViagemDto != null && requisicaoViagemDto.getAutorizado().equalsIgnoreCase("S")) {
                parecerDto.setIdJustificativa(null);
            } else {
                parecerDto.setIdJustificativa(requisicaoViagemDto.getIdJustificativaAutorizacao());
            }
            parecerDto.setIdResponsavel(solicitacaoServicoDto.getUsuarioDto().getIdEmpregado());
            parecerDto.setObservacoes(requisicaoViagemDto.getObservacoes());
            parecerDto.setComplementoJustificativa(requisicaoViagemDto.getComplemJustificativaAutorizacao());
            parecerDto.setAprovado(requisicaoViagemDto.getAutorizado());
            parecerDto.setDataHoraParecer(UtilDatas.getDataHoraAtual());

            parecerDto = (ParecerDTO) parecerDao.create(parecerDto);

            final IntegranteViagemDao integranteViagemDao = new IntegranteViagemDao();
            final IntegranteViagemService integranteViagemService = (IntegranteViagemService) ServiceLocator.getInstance().getService(IntegranteViagemService.class, null);
            final Collection<IntegranteViagemDTO> colIntegrantes = integranteViagemService.recuperaIntegrantesViagemByIdSolicitacaoEstado(
                    requisicaoViagemDto.getIdSolicitacaoServico(), RequisicaoViagemDTO.EM_AUTORIZACAO);

            if (solicitacaoServicoDto.getAcaoFluxo().equalsIgnoreCase("E")
                    && !solicitacaoServicoDto.getSituacao().equalsIgnoreCase(Enumerados.SituacaoSolicitacaoServico.Cancelada.name())) {
                this.validaItensDespesa(solicitacaoServicoDto, model);
                this.validaUpdate(solicitacaoServicoDto, model);
                if (requisicaoViagemDto.getAutorizado().equalsIgnoreCase("N")) {
                    requisicaoViagemDto.setEstado(RequisicaoViagemDTO.REJEITADA_PLANEJAMENTO);
                    if (colIntegrantes != null && colIntegrantes.size() > 0) {
                        for (final IntegranteViagemDTO integranteViagemDTO : colIntegrantes) {
                            integranteViagemDTO.setEstado(RequisicaoViagemDTO.AGUARDANDO_PLANEJAMENTO);
                            integranteViagemDao.updateNotNull(integranteViagemDTO);
                        }
                    }
                } else {
                    requisicaoViagemDto.setEstado(RequisicaoViagemDTO.AGUARDANDO_COMPRAS);
                    if (colIntegrantes != null && colIntegrantes.size() > 0) {
                        for (final IntegranteViagemDTO integranteViagemDTO : colIntegrantes) {
                            integranteViagemDTO.setEstado(RequisicaoViagemDTO.AGUARDANDO_COMPRAS);
                            integranteViagemDao.updateNotNull(integranteViagemDTO);
                        }
                    }
                }
            }

            if (parecerDto != null) {
                requisicaoViagemDto.setIdAprovacao(parecerDto.getIdParecer());
                this.getDao().updateNotNull(requisicaoViagemDto);
            }
        } else {
            requisicaoViagemDto.setEstado(Enumerados.SituacaoSolicitacaoServico.Cancelada.name());
            this.getDao().update(requisicaoViagemDto);
        }

    }

    /**
     * Verifica se o autorizador realmente autorizou a solicitação de viagem clicando no checkbox de autorização
     *
     * @param solicitacaoServicoDto
     * @param model
     * @throws Exception
     */
    public void validaAprovacao(final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {

        final RequisicaoViagemDTO requisicaoViagemDto = (RequisicaoViagemDTO) model;

        if (requisicaoViagemDto.getAutorizado() == null || requisicaoViagemDto.getAutorizado().equalsIgnoreCase("")) {
            throw new LogicException(this.i18nMessage("autorizacaoViagem.autorizacaoCampoObrigatorio"));
        } else {

            if (requisicaoViagemDto.getAutorizado().equalsIgnoreCase("N")) {
                if (requisicaoViagemDto.getIdJustificativaAutorizacao() == null) {
                    throw new LogicException(this.i18nMessage("autorizacaoViagem.justificativaCampoObrigatorio"));
                }
            }
        }

    }

    /**
     * Retorna as informacoes da solicitacao (@param: solicitacaoDTO) que serao exibidas na tela do mobile
     *
     * @param solicitacaoDto
     * @param itemTrabalho
     *
     */
    @Override
    public String getInformacoesComplementaresFmtTexto(final SolicitacaoServicoDTO solicitacaoDto, final ItemTrabalho itemTrabalho) throws Exception {
        RoteiroViagemDTO roteiroViagemDTO = new RoteiroViagemDTO();
        RequisicaoViagemDTO requisicaoViagemDto = new RequisicaoViagemDTO();

        final RoteiroViagemService roteiroViagemService = (RoteiroViagemService) ServiceLocator.getInstance().getService(RoteiroViagemService.class, null);
        final DespesaViagemService despesaViagemService = (DespesaViagemService) ServiceLocator.getInstance().getService(DespesaViagemService.class, null);
        final IntegranteViagemService integranteViagemService = (IntegranteViagemService) ServiceLocator.getInstance().getService(IntegranteViagemService.class, null);
        final RequisicaoViagemService reqViagemService = (RequisicaoViagemService) ServiceLocator.getInstance().getService(RequisicaoViagemService.class, null);
        final CidadesService cidadesService = (CidadesService) ServiceLocator.getInstance().getService(CidadesService.class, null);

        final NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        final DecimalFormat decimal = (DecimalFormat) nf;
        decimal.applyPattern("#,##0.00");

        CidadesDTO origem = null;
        CidadesDTO destino = null;

        requisicaoViagemDto.setIdSolicitacaoServico(solicitacaoDto.getIdSolicitacaoServico());
        requisicaoViagemDto = (RequisicaoViagemDTO) reqViagemService.restore(requisicaoViagemDto);

        new ArrayList<DespesaViagemDTO>();

        String result = "";
        result += "\n------------------ " + this.i18nMessage("requisicaoViagem.dadosGerais") + " ------------------\n";

        if (solicitacaoDto.getSolicitante() != null && !solicitacaoDto.getSolicitante().trim().equals("")) {
            result = "Solicitante: " + solicitacaoDto.getSolicitante() + "\n";
        }

        result += "\n" + "Descrição: " + solicitacaoDto.getDescricaoSemFormatacao() + "\n";

        result += "\n" + "Motivo: " + requisicaoViagemDto.getDescricaoMotivo() + "\n";

        result += "\n------------------ " + this.i18nMessage("requisicaoViagem.integrantes") + " ------------------\n";

        final Collection<IntegranteViagemDTO> integrantes = integranteViagemService.recuperaIntegrantesViagemByIdSolicitacaoEstado(solicitacaoDto.getIdSolicitacaoServico(),
                RequisicaoViagemDTO.EM_AUTORIZACAO);

        if (integrantes != null && !integrantes.isEmpty()) {
            for (final IntegranteViagemDTO integranteViagemDTO : integrantes) {
                result += "\n" + integranteViagemDTO.getNome();

                roteiroViagemDTO = roteiroViagemService.findByIdIntegrante(integranteViagemDTO.getIdIntegranteViagem());
                origem = (CidadesDTO) ((List) cidadesService.findNomeByIdCidade(roteiroViagemDTO.getOrigem())).get(0);
                destino = (CidadesDTO) ((List) cidadesService.findNomeByIdCidade(roteiroViagemDTO.getDestino())).get(0);

                despesaViagemService.findDespesasAtivasViagemByIdRoteiro(roteiroViagemDTO.getIdRoteiroViagem());

                result += " - " + this.i18nMessage("si.comum.ida") + " " + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, roteiroViagemDTO.getIda(), UtilI18N.getLocale())
                        + " - " + this.i18nMessage("si.comum.volta") + " "
                        + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, roteiroViagemDTO.getVolta(), UtilI18N.getLocale()) + " - " + origem.getNomeCidade() + "/"
                        + origem.getNomeUf() + " - " + destino.getNomeCidade() + "/" + destino.getNomeUf() + "\n";
            }
        }

        result += "\n" + "Valor total a aprovar: R$ " + decimal.format(despesaViagemService.buscaValorTotalViagemAtivo(solicitacaoDto.getIdSolicitacaoServico()));
        result += "\n" + "Valor total já aprovado: R$ " + decimal.format(despesaViagemService.buscaValorTotalViagemInativo(solicitacaoDto.getIdSolicitacaoServico()));
        result += "\n" + this.i18nMessage("requisicaoViagem.custoTotalViagem") + ": R$ "
                + decimal.format(despesaViagemService.buscarDespesaTotalViagem(solicitacaoDto.getIdSolicitacaoServico()));

        return result;
    }

    @Override
    public void preparaSolicitacaoParaAprovacao(final SolicitacaoServicoDTO solicitacaoDto, final ItemTrabalho itemTrabalho, final String aprovacao, final Integer idJustificativa,
            final String observacoes) throws Exception {
        RequisicaoViagemDTO requisicaoViagemDTO = new RequisicaoViagemDTO();
        requisicaoViagemDTO.setIdSolicitacaoServico(solicitacaoDto.getIdSolicitacaoServico());
        requisicaoViagemDTO = (RequisicaoViagemDTO) this.restore(requisicaoViagemDTO);

        final ElementoFluxoDTO elementoDto = itemTrabalho.getElementoFluxoDto();

        if (UtilStrings.nullToVazio(elementoDto.getTemplate()).toUpperCase().indexOf("AUTORIZACAO") >= 0) {

            requisicaoViagemDTO.setAcaoFluxo(RequisicaoProdutoDTO.ACAO_AUTORIZACAO);

            if (aprovacao.equalsIgnoreCase("N")) {
                requisicaoViagemDTO.setRejeitada("S");
            }

            requisicaoViagemDTO.setAutorizado(aprovacao);

            requisicaoViagemDTO.setComplemJustificativaAutorizacao(observacoes);
            requisicaoViagemDTO.setIdJustificativaAutorizacao(idJustificativa);
            this.getDao().update(requisicaoViagemDTO);

            // ItemRequisicaoProdutoService itemRequisicaoProdutoService = (ItemRequisicaoProdutoService)
            // ServiceLocator.getInstance().getService(ItemRequisicaoProdutoService.class, this.usuario);
        }

        if (requisicaoViagemDTO.getAcaoFluxo() != null) {
            solicitacaoDto.setInformacoesComplementares(requisicaoViagemDTO);
            solicitacaoDto.setAcaoFluxo(br.com.centralit.bpm.util.Enumerados.ACAO_EXECUTAR);
            solicitacaoDto.setIdTarefa(itemTrabalho.getIdItemTrabalho());
        }
    }

    /**
     * Verifica se os itens estão com a cotação vencida
     *
     * @param solicitacaoDto
     * @param model
     * @throws Exception
     */
    public void validaItensDespesa(final SolicitacaoServicoDTO solicitacaoDto, final IDto model) throws Exception {
        final IntegranteViagemDao integranteViagemDao = new IntegranteViagemDao();
        final DespesaViagemDAO despesaViagemDAO = new DespesaViagemDAO();
        final RoteiroViagemDAO roteiroViagemDAO = new RoteiroViagemDAO();
        RoteiroViagemDTO roteiroViagemDTO = new RoteiroViagemDTO();
        final Timestamp dataHoraAtual = UtilDatas.getDataHoraAtual();
        Collection<DespesaViagemDTO> colDespesaViagemDTOs = new ArrayList<DespesaViagemDTO>();
        final Collection<IntegranteViagemDTO> colIntegranteViagemDTOs = integranteViagemDao.recuperaIntegrantesViagemByIdSolicitacaoEstado(
                solicitacaoDto.getIdSolicitacaoServico(), RequisicaoViagemDTO.getAguardandoAprovacao());

        if (colIntegranteViagemDTOs != null && !colIntegranteViagemDTOs.isEmpty()) {
            for (final IntegranteViagemDTO dto : colIntegranteViagemDTOs) {
                roteiroViagemDTO = roteiroViagemDAO.findByIdIntegrante(dto.getIdIntegranteViagem());
                if (roteiroViagemDTO != null) {
                    colDespesaViagemDTOs = despesaViagemDAO.findDespesasAtivasViagemByIdRoteiro(roteiroViagemDTO.getIdRoteiroViagem());
                    if (colDespesaViagemDTOs != null && !colDespesaViagemDTOs.isEmpty()) {
                        for (final DespesaViagemDTO dtoDespesa : colDespesaViagemDTOs) {
                            if (dtoDespesa.getValidade() != null && dtoDespesa.getValidade().compareTo(dataHoraAtual) < 0) {
                                throw new LogicException(this.i18nMessage("requisicaoViagem.itensVencidos"));
                            }
                        }
                    }
                }
            }
        }
    }
}
