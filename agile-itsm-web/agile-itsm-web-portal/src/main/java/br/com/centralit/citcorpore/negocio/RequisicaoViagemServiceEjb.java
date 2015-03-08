package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.CentroResultadoDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.IntegranteViagemDTO;
import br.com.centralit.citcorpore.bean.ParecerDTO;
import br.com.centralit.citcorpore.bean.ProjetoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoViagemDTO;
import br.com.centralit.citcorpore.bean.RoteiroViagemDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.integracao.CentroResultadoDao;
import br.com.centralit.citcorpore.integracao.EmpregadoDao;
import br.com.centralit.citcorpore.integracao.IntegranteViagemDao;
import br.com.centralit.citcorpore.integracao.ParecerDao;
import br.com.centralit.citcorpore.integracao.ProjetoDao;
import br.com.centralit.citcorpore.integracao.RequisicaoViagemDAO;
import br.com.centralit.citcorpore.integracao.RoteiroViagemDAO;
import br.com.centralit.citcorpore.integracao.SolicitacaoServicoDao;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilStrings;
import br.com.citframework.util.WebUtil;

@SuppressWarnings("unchecked")
public class RequisicaoViagemServiceEjb extends ComplemInfSolicitacaoServicoServiceEjb implements RequisicaoViagemService {

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
    public void validaCreate(final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {
        this.validaAtualizacao(solicitacaoServicoDto, model);

    }

    @Override
    public void validaDelete(final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {}

    @Override
    public void validaUpdate(final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {
        this.validaAtualizacao(solicitacaoServicoDto, model);
    }

    public void validaAtualizacao(final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {
        final RequisicaoViagemDTO requisicaoViagemDto = (RequisicaoViagemDTO) model;
        this.validaCentroResultado(requisicaoViagemDto);
        this.validaProjeto(requisicaoViagemDto);
        this.validaObrigatoriedade(requisicaoViagemDto);
    }

    public void validaObrigatoriedade(final RequisicaoViagemDTO requisicaoViagemDto) throws Exception {
        if (requisicaoViagemDto.getIdCidadeOrigem() == null) {
            throw new LogicException(this.i18nMessage("rh.alertOCampo") + " [" + this.i18nMessage("citcorpore.comum.origem") + "] " + this.i18nMessage("rh.alertEObrigatorio")
                    + "!");
        }
        if (requisicaoViagemDto.getIdCidadeDestino() == null) {
            throw new LogicException(this.i18nMessage("rh.alertOCampo") + " [" + this.i18nMessage("importmanager.destino") + "] " + this.i18nMessage("rh.alertEObrigatorio")
                    + "!");
        }
        if (requisicaoViagemDto.getIdCidadeOrigem().equals(requisicaoViagemDto.getIdCidadeDestino())) {
            throw new LogicException(this.i18nMessage("rh.alertOCampo") + " [" + this.i18nMessage("citcorpore.comum.origem") + "] "
                    + this.i18nMessage("si.comum.deveSerDiferente") + " [" + this.i18nMessage("importmanager.destino") + "] ");
        }
        if (requisicaoViagemDto.getDataInicioViagem() == null) {
            throw new LogicException(this.i18nMessage("rh.alertOCampo") + " [" + this.i18nMessage("itemControleFinanceiroViagem.ida") + "] "
                    + this.i18nMessage("rh.alertEObrigatorio") + "!");
        }
        if (requisicaoViagemDto.getIdMotivoViagem() == null) {
            throw new LogicException(this.i18nMessage("rh.alertOCampo") + " [" + this.i18nMessage("requisicaoViagem.justificativa") + "] "
                    + this.i18nMessage("rh.alertEObrigatorio") + "!");
        }
        if (requisicaoViagemDto.getDescricaoMotivo().equalsIgnoreCase("")) {
            throw new LogicException(this.i18nMessage("rh.alertOCampo") + " [" + this.i18nMessage("requisicaoViagem.motivo") + "] " + this.i18nMessage("rh.alertEObrigatorio")
                    + "!");
        }
        if (requisicaoViagemDto.getDataInicioViagem().compareTo(UtilDatas.getDataAtual()) < 0) {
            throw new LogicException(this.i18nMessage("solicitacaoliberacao.validacao.datainiciomenoratual"));
        }
        if (UtilStrings.nullToVazio(requisicaoViagemDto.getEstado()).equalsIgnoreCase(RequisicaoViagemDTO.AGUARDANDO_PLANEJAMENTO)) {
            if (requisicaoViagemDto.getIntegranteViagem() == null) {
                throw new LogicException(this.i18nMessage("rh.alertOCampo") + " [" + this.i18nMessage("requisicaoViagem.integranteFuncionario") + "] "
                        + this.i18nMessage("rh.alertEObrigatorio") + "!");
            }

        }
    }

    private void validaProjeto(final RequisicaoViagemDTO requisicaoViagemDto) throws Exception {
        ProjetoDTO projetoDto = null;
        if (requisicaoViagemDto.getIdProjeto() != null) {
            projetoDto = new ProjetoDTO();
            projetoDto.setIdProjeto(requisicaoViagemDto.getIdProjeto());
            projetoDto = (ProjetoDTO) new ProjetoDao().restore(projetoDto);
        }
        if (projetoDto == null) {
            throw new LogicException(this.i18nMessage("rh.alertOCampo") + " [" + this.i18nMessage("lookup.projeto") + "] " + this.i18nMessage("rh.alertEObrigatorio") + "!");
        }
        if (projetoDto.getIdProjetoPai() == null) {
            throw new LogicException(this.i18nMessage("requisicaoViagem.mensagemProjeto"));
        }
    }

    private void validaCentroResultado(final RequisicaoViagemDTO requisicaoViagemDto) throws Exception {
        CentroResultadoDTO centroCustoDto = null;
        if (requisicaoViagemDto.getIdCentroCusto() != null) {
            centroCustoDto = new CentroResultadoDTO();
            centroCustoDto.setIdCentroResultado(requisicaoViagemDto.getIdCentroCusto());
            centroCustoDto = (CentroResultadoDTO) new CentroResultadoDao().restore(centroCustoDto);
        }
        if (centroCustoDto == null) {
            throw new LogicException(this.i18nMessage("rh.alertOCampo") + " [" + this.i18nMessage("centroResultado") + "] " + this.i18nMessage("rh.alertEObrigatorio") + "!");
        }
        if (centroCustoDto.getIdCentroResultadoPai() == null || centroCustoDto.getPermiteRequisicaoProduto() == null
                || !centroCustoDto.getPermiteRequisicaoProduto().equalsIgnoreCase("S")) {
            throw new LogicException(this.i18nMessage("requisicaoViagem.mensagemCentroResultado"));
        }
    }

    @Override
    public IDto create(final TransactionControler tc, final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {
        RequisicaoViagemDTO requisicaoViagemDto = (RequisicaoViagemDTO) model;
        requisicaoViagemDto.setEstado(RequisicaoViagemDTO.AGUARDANDO_PLANEJAMENTO);

        final SolicitacaoServicoDao solicitacaoServicoDao = new SolicitacaoServicoDao();
        final RequisicaoViagemDAO requisicaoViagemDao = this.getDao();
        final IntegranteViagemDao integranteViagemDao = new IntegranteViagemDao();
        final RoteiroViagemDAO roteiroViagemDAO = new RoteiroViagemDAO();

        this.validaCreate(solicitacaoServicoDto, model);

        requisicaoViagemDao.setTransactionControler(tc);
        integranteViagemDao.setTransactionControler(tc);
        solicitacaoServicoDao.setTransactionControler(tc);
        roteiroViagemDAO.setTransactionControler(tc);

        if (solicitacaoServicoDto.getIdSolicitacaoServico() != null) {
            requisicaoViagemDto.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
            requisicaoViagemDto.setRemarcacao("N");

            if (requisicaoViagemDto.getDataFimViagem() == null) {
                requisicaoViagemDto.setDataFimViagem(requisicaoViagemDto.getDataInicioViagem());
            }

            requisicaoViagemDto = (RequisicaoViagemDTO) requisicaoViagemDao.create(requisicaoViagemDto);
        }

        if (requisicaoViagemDto.getIntegranteViagemSerialize() != null) {
            for (IntegranteViagemDTO integranteViagemDto : requisicaoViagemDto.getIntegranteViagem()) {

                integranteViagemDto.setIdSolicitacaoServico(requisicaoViagemDto.getIdSolicitacaoServico());
                integranteViagemDto.setIdEmpregado(integranteViagemDto.getIdEmpregado());
                integranteViagemDto.setRemarcacao("N");
                if (integranteViagemDto.getIdRespPrestacaoContas() == null || integranteViagemDto.getIdRespPrestacaoContas() == 0) {
                    integranteViagemDto.setIdRespPrestacaoContas(integranteViagemDto.getIdEmpregado());
                }

                integranteViagemDto.setEstado(RequisicaoViagemDTO.AGUARDANDO_PLANEJAMENTO);
                integranteViagemDto = (IntegranteViagemDTO) integranteViagemDao.create(integranteViagemDto);

                final RoteiroViagemDTO roteiroViagemDTO = new RoteiroViagemDTO();

                roteiroViagemDTO.setDataInicio(UtilDatas.getDataAtual());
                roteiroViagemDTO.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
                roteiroViagemDTO.setIdIntegrante(integranteViagemDto.getIdIntegranteViagem());
                roteiroViagemDTO.setOrigem(requisicaoViagemDto.getIdCidadeOrigem());
                roteiroViagemDTO.setDestino(requisicaoViagemDto.getIdCidadeDestino());
                roteiroViagemDTO.setIda(requisicaoViagemDto.getDataInicioViagem());

                if (requisicaoViagemDto.getDataFimViagem() != null) {
                    roteiroViagemDTO.setVolta(requisicaoViagemDto.getDataFimViagem());
                } else {
                    roteiroViagemDTO.setVolta(requisicaoViagemDto.getDataInicioViagem());
                }

                roteiroViagemDAO.create(roteiroViagemDTO);
            }
        }

        return requisicaoViagemDto;
    }

    @Override
    public void update(final TransactionControler tc, final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {

        final RequisicaoViagemDTO requisicaoViagemDto = (RequisicaoViagemDTO) model;
        ParecerDTO parecerDto = new ParecerDTO();

        final ParecerDao parecerDao = new ParecerDao();
        final RequisicaoViagemDAO requisicaoViagemDao = this.getDao();
        final IntegranteViagemDao integranteViagemDao = new IntegranteViagemDao();

        parecerDao.setTransactionControler(tc);
        requisicaoViagemDao.setTransactionControler(tc);
        integranteViagemDao.setTransactionControler(tc);

        final RequisicaoViagemDTO bean = (RequisicaoViagemDTO) model;
        if (bean != null && bean.getCancelarRequisicao() != null && bean.getCancelarRequisicao().equalsIgnoreCase("S")) {
            solicitacaoServicoDto.setSituacao(Enumerados.SituacaoSolicitacaoServico.Cancelada.name());
            requisicaoViagemDao.updateNotNull(bean);
            return;
        }

        if (solicitacaoServicoDto.getIdSolicitante().intValue() == solicitacaoServicoDto.getUsuarioDto().getIdEmpregado().intValue()
                && !solicitacaoServicoDto.getSituacao().equalsIgnoreCase(Enumerados.SituacaoSolicitacaoServico.Cancelada.name())) {
            throw new LogicException("Usuário sem permissão para Execução!");
        }

        this.validaUpdate(solicitacaoServicoDto, model);
        requisicaoViagemDto.setEstado(RequisicaoViagemDTO.AGUARDANDO_PLANEJAMENTO);

        parecerDto.setIdJustificativa(requisicaoViagemDto.getIdJustificativaAutorizacao());
        parecerDto.setIdResponsavel(solicitacaoServicoDto.getUsuarioDto().getIdEmpregado());
        parecerDto.setObservacoes(requisicaoViagemDto.getObservacoes());
        parecerDto.setComplementoJustificativa(requisicaoViagemDto.getComplemJustificativaAutorizacao());
        parecerDto.setAprovado(requisicaoViagemDto.getAutorizado());
        parecerDto.setDataHoraParecer(UtilDatas.getDataHoraAtual());

        parecerDto = (ParecerDTO) parecerDao.create(parecerDto);

        if (parecerDto != null) {
            requisicaoViagemDto.setIdAprovacao(parecerDto.getIdParecer());
            if (requisicaoViagemDto.getRemarcacao() == null || requisicaoViagemDto.getRemarcacao().trim().equals("")) {
                requisicaoViagemDto.setRemarcacao("N");
            }

            requisicaoViagemDao.updateNotNull(requisicaoViagemDto);
        }

        if (requisicaoViagemDto.getIntegranteViagemSerialize() != null) {
            for (final IntegranteViagemDTO integranteViagemDto : requisicaoViagemDto.getIntegranteViagem()) {

                integranteViagemDto.setIdSolicitacaoServico(requisicaoViagemDto.getIdSolicitacaoServico());
                integranteViagemDto.setIdEmpregado(integranteViagemDto.getIdEmpregado());

                if (integranteViagemDto.getIntegranteFuncionario().equals("S")) {
                    if (!integranteViagemDao.verificarSeIntegranteViagemExiste(integranteViagemDto.getIdSolicitacaoServico(), integranteViagemDto.getIdEmpregado())) {
                        integranteViagemDto.setEstado(RequisicaoViagemDTO.AGUARDANDO_PLANEJAMENTO);
                        integranteViagemDao.create(integranteViagemDto);
                    }
                }
            }
        }

    }

    @Override
    public void update(final IDto model) throws ServiceException {
        // Instancia Objeto controlador de transacao
        final CrudDAO crudDao = this.getDao();
        TransactionControler tc = null;

        try {

            tc = this.getDao().getTransactionControler();

            // Faz validacao, caso exista.
            this.validaUpdate(model);

            // Seta o TransactionController para os DAOs
            crudDao.setTransactionControler(tc);

            // Executa operacoes pertinentes ao negocio.
            crudDao.update(model);

        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * TODO Este metodo esta em desuso, pode ser removido na proxima versão
     */
    public void atualizarRemarcacaoDeViagem(final TransactionControler tc, final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {

        final RequisicaoViagemDTO requisicaoViagemDto = (RequisicaoViagemDTO) model;

        ParecerDTO parecerDto = new ParecerDTO();
        final ParecerDao parecerDao = new ParecerDao();
        final RequisicaoViagemDAO requisicaoViagemDao = this.getDao();
        final IntegranteViagemDao integranteViagemDao = new IntegranteViagemDao();

        this.validaUpdate(solicitacaoServicoDto, model);

        parecerDao.setTransactionControler(tc);
        requisicaoViagemDao.setTransactionControler(tc);
        integranteViagemDao.setTransactionControler(tc);

        requisicaoViagemDto.setEstado(RequisicaoViagemDTO.REMARCADO);

        parecerDto.setIdJustificativa(requisicaoViagemDto.getIdJustificativaAutorizacao());
        parecerDto.setIdResponsavel(solicitacaoServicoDto.getUsuarioDto().getIdEmpregado());
        parecerDto.setObservacoes(requisicaoViagemDto.getObservacoes());
        parecerDto.setComplementoJustificativa(requisicaoViagemDto.getComplemJustificativaAutorizacao());
        parecerDto.setAprovado(requisicaoViagemDto.getAutorizado());
        parecerDto.setDataHoraParecer(UtilDatas.getDataHoraAtual());

        parecerDto = (ParecerDTO) parecerDao.create(parecerDto);

        if (parecerDto != null) {
            requisicaoViagemDto.setIdAprovacao(parecerDto.getIdParecer());
            requisicaoViagemDao.updateNotNull(requisicaoViagemDto);
        }

        if (requisicaoViagemDto.getIntegranteViagemSerialize() != null) {
            for (final IntegranteViagemDTO integranteViagemDto : requisicaoViagemDto.getIntegranteViagem()) {

                integranteViagemDto.setIdSolicitacaoServico(requisicaoViagemDto.getIdSolicitacaoServico());
                integranteViagemDto.setIdEmpregado(integranteViagemDto.getIdEmpregado());

                if (integranteViagemDto.getIntegranteFuncionario().equals("S")) {
                    if (!integranteViagemDao.verificarSeIntegranteViagemExiste(integranteViagemDto.getIdSolicitacaoServico(), integranteViagemDto.getIdEmpregado())) {
                        integranteViagemDao.create(integranteViagemDto);
                    }
                }

            }
        }

    }

    @Override
    public void delete(final TransactionControler tc, final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {

    }

    /**
     * Retorna uma coleção com todos os integrantes adicionados em uma solicitação de viagem.
     *
     * @param idSolicitacaoServico
     * @return
     * @throws Exception
     */
    @Override
    public Collection<IntegranteViagemDTO> recuperaIntegrantesViagemBySolicitacao(final Integer idSolicitacao) throws Exception {
        final Collection<IntegranteViagemDTO> colIntegrantesViagem = new ArrayList<IntegranteViagemDTO>();

        final IntegranteViagemDao integranteDao = new IntegranteViagemDao();
        final EmpregadoDao empregadoDao = new EmpregadoDao();
        EmpregadoDTO empregado;

        final Collection<IntegranteViagemDTO> colIntegrantAux = integranteDao.findAllByIdSolicitacao(idSolicitacao);

        if (colIntegrantAux != null) {
            for (final IntegranteViagemDTO integrante : colIntegrantAux) {

                empregado = empregadoDao.restoreByIdEmpregado(integrante.getIdEmpregado());

                integrante.setNome(empregado.getNome());
                integrante.setEmail(empregado.getEmail());

                if (integrante.getIdRespPrestacaoContas() == null) {
                    integrante.setRespPrestacaoContas(empregado.getNome());
                } else {
                    integrante.setRespPrestacaoContas(this.recuperarNome(integrante.getIdRespPrestacaoContas()));
                }

                colIntegrantesViagem.add(integrante);
            }
        }
        return colIntegrantesViagem;
    }

    public String recuperarNome(final Integer idEmpregado) throws Exception {
        final EmpregadoDao dao = new EmpregadoDao();
        EmpregadoDTO empregadoDto = new EmpregadoDTO();

        empregadoDto = dao.restoreByIdEmpregado(idEmpregado);

        return empregadoDto.getNome();
    }

    @Override
    public RequisicaoViagemDTO recuperaRequisicaoPelaSolicitacao(final Integer idSolicitacao) throws Exception {
        return this.getDao().findByIdSolicitacao(idSolicitacao);
    }

}
