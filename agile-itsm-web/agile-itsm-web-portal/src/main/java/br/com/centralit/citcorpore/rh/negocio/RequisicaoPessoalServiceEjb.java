package br.com.centralit.citcorpore.rh.negocio;

import java.util.Collection;

import br.com.centralit.bpm.negocio.ItemTrabalho;
import br.com.centralit.citcorpore.bean.CidadesDTO;
import br.com.centralit.citcorpore.bean.ParecerDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UfDTO;
import br.com.centralit.citcorpore.integracao.CidadesDao;
import br.com.centralit.citcorpore.integracao.UfDao;
import br.com.centralit.citcorpore.negocio.ParecerServiceEjb;
import br.com.centralit.citcorpore.rh.bean.AtitudeIndividualDTO;
import br.com.centralit.citcorpore.rh.bean.CandidatoDTO;
import br.com.centralit.citcorpore.rh.bean.CertificacaoDTO;
import br.com.centralit.citcorpore.rh.bean.ConhecimentoDTO;
import br.com.centralit.citcorpore.rh.bean.CursoDTO;
import br.com.centralit.citcorpore.rh.bean.EntrevistaCandidatoDTO;
import br.com.centralit.citcorpore.rh.bean.ExperienciaInformaticaDTO;
import br.com.centralit.citcorpore.rh.bean.FormacaoAcademicaDTO;
import br.com.centralit.citcorpore.rh.bean.HabilidadeDTO;
import br.com.centralit.citcorpore.rh.bean.HistoricoFuncionalDTO;
import br.com.centralit.citcorpore.rh.bean.IdiomaDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoAtitudeIndividualDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoCertificacaoDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoConhecimentoDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoCursoDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoExperienciaAnteriorDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoExperienciaInformaticaDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoFormacaoAcademicaDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoHabilidadeDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoIdiomaDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoPessoalDTO;
import br.com.centralit.citcorpore.rh.bean.TriagemRequisicaoPessoalDTO;
import br.com.centralit.citcorpore.rh.integracao.AtitudeIndividualDao;
import br.com.centralit.citcorpore.rh.integracao.CandidatoDao;
import br.com.centralit.citcorpore.rh.integracao.CertificacaoDao;
import br.com.centralit.citcorpore.rh.integracao.ConhecimentoDao;
import br.com.centralit.citcorpore.rh.integracao.CursoDao;
import br.com.centralit.citcorpore.rh.integracao.EntrevistaCandidatoDao;
import br.com.centralit.citcorpore.rh.integracao.ExperienciaInformaticaDao;
import br.com.centralit.citcorpore.rh.integracao.FormacaoAcademicaDao;
import br.com.centralit.citcorpore.rh.integracao.HabilidadeDao;
import br.com.centralit.citcorpore.rh.integracao.HistoricoFuncionalDao;
import br.com.centralit.citcorpore.rh.integracao.IdiomaDao;
import br.com.centralit.citcorpore.rh.integracao.ItemHistoricoFuncionalDao;
import br.com.centralit.citcorpore.rh.integracao.RequisicaoAtitudeIndividualDao;
import br.com.centralit.citcorpore.rh.integracao.RequisicaoCertificacaoDao;
import br.com.centralit.citcorpore.rh.integracao.RequisicaoConhecimentoDao;
import br.com.centralit.citcorpore.rh.integracao.RequisicaoCursoDao;
import br.com.centralit.citcorpore.rh.integracao.RequisicaoExperienciaAnteriorDao;
import br.com.centralit.citcorpore.rh.integracao.RequisicaoExperienciaInformaticaDao;
import br.com.centralit.citcorpore.rh.integracao.RequisicaoFormacaoAcademicaDao;
import br.com.centralit.citcorpore.rh.integracao.RequisicaoHabilidadeDao;
import br.com.centralit.citcorpore.rh.integracao.RequisicaoIdiomaDao;
import br.com.centralit.citcorpore.rh.integracao.RequisicaoPessoalDao;
import br.com.centralit.citcorpore.rh.integracao.TriagemRequisicaoPessoalDao;
import br.com.centralit.citcorpore.util.Enumerados.TipoEntrevista;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilStrings;
import br.com.citframework.util.WebUtil;

@SuppressWarnings({"unchecked", "rawtypes"})
public class RequisicaoPessoalServiceEjb extends CrudServiceImpl implements RequisicaoPessoalService {

    private RequisicaoPessoalDao dao;

    @Override
    protected RequisicaoPessoalDao getDao() {
        if (dao == null) {
            dao = new RequisicaoPessoalDao();
        }
        return dao;
    }

    @Override
    public IDto deserializaObjeto(final String serialize) throws Exception {
        RequisicaoPessoalDTO requisicaoPessoalDto = null;

        if (serialize != null) {
            requisicaoPessoalDto = (RequisicaoPessoalDTO) WebUtil.deserializeObject(RequisicaoPessoalDTO.class, serialize);
            if (requisicaoPessoalDto != null && requisicaoPessoalDto.getSerializeFormacaoAcademica() != null) {
                requisicaoPessoalDto.setColFormacaoAcademica(WebUtil.deserializeCollectionFromString(RequisicaoFormacaoAcademicaDTO.class,
                        requisicaoPessoalDto.getSerializeFormacaoAcademica()));
            }
            if (requisicaoPessoalDto != null && requisicaoPessoalDto.getSerializeCertificacao() != null) {
                requisicaoPessoalDto.setColCertificacao(WebUtil.deserializeCollectionFromString(RequisicaoCertificacaoDTO.class, requisicaoPessoalDto.getSerializeCertificacao()));
            }
            if (requisicaoPessoalDto != null && requisicaoPessoalDto.getSerializeCurso() != null) {
                requisicaoPessoalDto.setColCurso(WebUtil.deserializeCollectionFromString(RequisicaoCursoDTO.class, requisicaoPessoalDto.getSerializeCurso()));
            }
            if (requisicaoPessoalDto != null && requisicaoPessoalDto.getSerializeExperienciaInformatica() != null) {
                requisicaoPessoalDto.setColExperienciaInformatica(WebUtil.deserializeCollectionFromString(RequisicaoExperienciaInformaticaDTO.class,
                        requisicaoPessoalDto.getSerializeExperienciaInformatica()));
            }
            if (requisicaoPessoalDto != null && requisicaoPessoalDto.getSerializeIdioma() != null) {
                requisicaoPessoalDto.setColIdioma(WebUtil.deserializeCollectionFromString(RequisicaoIdiomaDTO.class, requisicaoPessoalDto.getSerializeIdioma()));
            }
            if (requisicaoPessoalDto != null && requisicaoPessoalDto.getSerializeExperienciaAnterior() != null) {
                requisicaoPessoalDto.setColExperienciaAnterior(WebUtil.deserializeCollectionFromString(RequisicaoExperienciaAnteriorDTO.class,
                        requisicaoPessoalDto.getSerializeExperienciaAnterior()));
            }
            if (requisicaoPessoalDto != null && requisicaoPessoalDto.getSerializeHabilidade() != null) {
                requisicaoPessoalDto.setColHabilidade(WebUtil.deserializeCollectionFromString(RequisicaoHabilidadeDTO.class, requisicaoPessoalDto.getSerializeHabilidade()));
            }
            if (requisicaoPessoalDto != null && requisicaoPessoalDto.getSerializeAtitudeIndividual() != null) {
                requisicaoPessoalDto.setColAtitudeIndividual(WebUtil.deserializeCollectionFromString(RequisicaoAtitudeIndividualDTO.class,
                        requisicaoPessoalDto.getSerializeAtitudeIndividual()));
            }
            if (requisicaoPessoalDto != null && requisicaoPessoalDto.getSerializeConhecimento() != null) {
                requisicaoPessoalDto.setColConhecimento(WebUtil.deserializeCollectionFromString(RequisicaoConhecimentoDTO.class, requisicaoPessoalDto.getSerializeConhecimento()));
            }
            if (requisicaoPessoalDto != null && requisicaoPessoalDto.getSerializeTriagem() != null) {
                requisicaoPessoalDto.setColTriagem(WebUtil.deserializeCollectionFromString(TriagemRequisicaoPessoalDTO.class, requisicaoPessoalDto.getSerializeTriagem()));
            }
        }

        return requisicaoPessoalDto;
    }

    @Override
    public void validaCreate(final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {
        final RequisicaoPessoalDTO requisicaoPessoalDto = (RequisicaoPessoalDTO) model;
        this.validaAtualizacao(solicitacaoServicoDto, requisicaoPessoalDto);
    }

    private void validaAtualizacao(final SolicitacaoServicoDTO solicitacaoServicoDto, final RequisicaoPessoalDTO requisicaoPessoalDto) throws Exception {
        final String acao = UtilStrings.nullToVazio(requisicaoPessoalDto.getAcao());
        requisicaoPessoalDto.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
        if (acao.equalsIgnoreCase(RequisicaoPessoalDTO.ACAO_ANALISE)) {
            if (requisicaoPessoalDto.getRejeitada() != null && requisicaoPessoalDto.getRejeitada().equalsIgnoreCase("S")
                    && requisicaoPessoalDto.getIdJustificativaValidacao() == null) {
                throw new LogicException("Justificativa não informada");
            }
        }
        if (acao.equalsIgnoreCase(RequisicaoPessoalDTO.ACAO_TRIAGEM)) {
            if ((requisicaoPessoalDto.getColTriagem() == null || requisicaoPessoalDto.getColTriagem().size() == 0)
                    && (requisicaoPessoalDto.getRejeitada() == null || !requisicaoPessoalDto.getRejeitada().equalsIgnoreCase("S"))) {
                throw new LogicException("Currículos não informados");
            }
        }
        if (acao.equalsIgnoreCase(RequisicaoPessoalDTO.ACAO_ENTREVISTA_RH) || acao.equalsIgnoreCase(RequisicaoPessoalDTO.ACAO_ENTREVISTA_GESTOR)) {
            final Collection<TriagemRequisicaoPessoalDTO> colTriagens = new TriagemRequisicaoPessoalServiceEjb().findByIdSolicitacaoServicoAndIdTarefa(
                    solicitacaoServicoDto.getIdSolicitacaoServico(), solicitacaoServicoDto.getIdTarefa());
            if (colTriagens != null) {
                TipoEntrevista.RH.name();
                if (acao.equalsIgnoreCase(RequisicaoPessoalDTO.ACAO_ENTREVISTA_GESTOR)) {
                    TipoEntrevista.Gestor.name();
                }
                final EntrevistaCandidatoDao entrevistaDao = new EntrevistaCandidatoDao();
                for (final TriagemRequisicaoPessoalDTO triagemDto : colTriagens) {
                    final EntrevistaCandidatoDTO entrevistaDto = entrevistaDao.findByIdTriagemAndIdCurriculo(triagemDto.getIdTriagem(), triagemDto.getIdCurriculo());
                    if (requisicaoPessoalDto.getAcaoManterGravarTarefa().equalsIgnoreCase("E")) {
                        if (entrevistaDto == null) {
                            throw new LogicException("Entrevista(s) não realizada(s)");
                        }
                    }
                }
            }
        }
    }

    @Override
    public void validaDelete(final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {

    }

    @Override
    public void validaUpdate(final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {
        final RequisicaoPessoalDTO requisicaoPessoalDto = (RequisicaoPessoalDTO) model;
        if (solicitacaoServicoDto != null && solicitacaoServicoDto.getAcaoFluxo() != null) {
            requisicaoPessoalDto.setAcaoManterGravarTarefa(solicitacaoServicoDto.getAcaoFluxo());
        }
        this.validaAtualizacao(solicitacaoServicoDto, requisicaoPessoalDto);
    }

    @Override
    public IDto create(final TransactionControler tc, final SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {

        this.validaCreate(solicitacaoServicoDto, model);

        // Instancia Objeto controlador de transacao
        final RequisicaoPessoalDao requisicaoPessoalDao = new RequisicaoPessoalDao();
        final RequisicaoFormacaoAcademicaDao requisicaoFormacaoAcademicaDao = new RequisicaoFormacaoAcademicaDao();
        final RequisicaoCertificacaoDao requisicaoCertificacaoDao = new RequisicaoCertificacaoDao();
        final RequisicaoCursoDao requisicaoCursoDao = new RequisicaoCursoDao();
        final RequisicaoExperienciaInformaticaDao requisicaoExperienciaInformaticaDao = new RequisicaoExperienciaInformaticaDao();
        final RequisicaoIdiomaDao requisicaoIdiomaDao = new RequisicaoIdiomaDao();
        final RequisicaoExperienciaAnteriorDao requisicaoExperienciaAnteriorDao = new RequisicaoExperienciaAnteriorDao();
        final RequisicaoConhecimentoDao requisicaoConhecimentoDao = new RequisicaoConhecimentoDao();
        final RequisicaoHabilidadeDao requisicaoHabilidadeDao = new RequisicaoHabilidadeDao();
        final RequisicaoAtitudeIndividualDao requisicaoAtitudeIndividualDao = new RequisicaoAtitudeIndividualDao();

        // Seta o TransactionController para os DAOs
        requisicaoPessoalDao.setTransactionControler(tc);
        requisicaoFormacaoAcademicaDao.setTransactionControler(tc);
        requisicaoCertificacaoDao.setTransactionControler(tc);
        requisicaoCursoDao.setTransactionControler(tc);
        requisicaoExperienciaInformaticaDao.setTransactionControler(tc);
        requisicaoIdiomaDao.setTransactionControler(tc);
        requisicaoExperienciaAnteriorDao.setTransactionControler(tc);
        requisicaoConhecimentoDao.setTransactionControler(tc);
        requisicaoHabilidadeDao.setTransactionControler(tc);
        requisicaoAtitudeIndividualDao.setTransactionControler(tc);

        // Executa operacoes pertinentes ao negocio.
        model = requisicaoPessoalDao.create(model);
        final RequisicaoPessoalDTO requisicaoPessoalDto = (RequisicaoPessoalDTO) model;

        if (requisicaoPessoalDto.getColFormacaoAcademica() != null) {
            for (final RequisicaoFormacaoAcademicaDTO requisicaoFormacaoAcademicaDto : requisicaoPessoalDto.getColFormacaoAcademica()) {
                requisicaoFormacaoAcademicaDto.setIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
                requisicaoFormacaoAcademicaDao.create(requisicaoFormacaoAcademicaDto);
            }
        }

        if (requisicaoPessoalDto.getColCertificacao() != null) {
            for (final RequisicaoCertificacaoDTO requisicaoCertificacaoDto : requisicaoPessoalDto.getColCertificacao()) {
                requisicaoCertificacaoDto.setIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
                requisicaoCertificacaoDao.create(requisicaoCertificacaoDto);
            }
        }

        if (requisicaoPessoalDto.getColCurso() != null) {
            for (final RequisicaoCursoDTO requisicaoCursoDto : requisicaoPessoalDto.getColCurso()) {
                requisicaoCursoDto.setIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
                requisicaoCursoDao.create(requisicaoCursoDto);
            }
        }

        if (requisicaoPessoalDto.getColExperienciaInformatica() != null) {
            for (final RequisicaoExperienciaInformaticaDTO requisicaoExperienciaInformaticaDto : requisicaoPessoalDto.getColExperienciaInformatica()) {
                requisicaoExperienciaInformaticaDto.setIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
                requisicaoExperienciaInformaticaDao.create(requisicaoExperienciaInformaticaDto);
            }
        }

        if (requisicaoPessoalDto.getColIdioma() != null) {
            for (final RequisicaoIdiomaDTO requisicaoIdiomaDto : requisicaoPessoalDto.getColIdioma()) {
                requisicaoIdiomaDto.setIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
                requisicaoIdiomaDao.create(requisicaoIdiomaDto);
            }
        }

        if (requisicaoPessoalDto.getColExperienciaAnterior() != null) {
            for (final RequisicaoExperienciaAnteriorDTO requisicaoExperienciaAnteriorDto : requisicaoPessoalDto.getColExperienciaAnterior()) {
                requisicaoExperienciaAnteriorDto.setIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
                requisicaoExperienciaAnteriorDao.create(requisicaoExperienciaAnteriorDto);
            }
        }

        if (requisicaoPessoalDto.getColConhecimento() != null) {
            for (final RequisicaoConhecimentoDTO requisicaoConhecimentoDto : requisicaoPessoalDto.getColConhecimento()) {
                requisicaoConhecimentoDto.setIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
                requisicaoConhecimentoDao.create(requisicaoConhecimentoDto);
            }
        }

        if (requisicaoPessoalDto.getColHabilidade() != null) {
            for (final RequisicaoHabilidadeDTO requisicaoHabilidadeDto : requisicaoPessoalDto.getColHabilidade()) {
                requisicaoHabilidadeDto.setIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
                requisicaoHabilidadeDao.create(requisicaoHabilidadeDto);
            }
        }

        if (requisicaoPessoalDto.getColAtitudeIndividual() != null) {
            for (final RequisicaoAtitudeIndividualDTO requisicaoAtitudeIndividualDto : requisicaoPessoalDto.getColAtitudeIndividual()) {
                requisicaoAtitudeIndividualDto.setIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
                requisicaoAtitudeIndividualDao.create(requisicaoAtitudeIndividualDto);
            }
        } else {
            throw new LogicException(this.i18nMessage("solicitacaoCargo.necessarioAtitudeIndividual"));
        }

        return model;
    }

    @Override
    public void update(final TransactionControler tc, final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {
        this.validaUpdate(solicitacaoServicoDto, model);

        // Instancia Objeto controlador de transacao
        final RequisicaoPessoalDao requisicaoPessoalDao = new RequisicaoPessoalDao();
        final RequisicaoFormacaoAcademicaDao requisicaoFormacaoAcademicaDao = new RequisicaoFormacaoAcademicaDao();
        final RequisicaoCertificacaoDao requisicaoCertificacaoDao = new RequisicaoCertificacaoDao();
        final RequisicaoCursoDao requisicaoCursoDao = new RequisicaoCursoDao();
        final RequisicaoExperienciaInformaticaDao requisicaoExperienciaInformaticaDao = new RequisicaoExperienciaInformaticaDao();
        final RequisicaoIdiomaDao requisicaoIdiomaDao = new RequisicaoIdiomaDao();
        final RequisicaoExperienciaAnteriorDao requisicaoExperienciaAnteriorDao = new RequisicaoExperienciaAnteriorDao();
        final RequisicaoConhecimentoDao requisicaoConhecimentoDao = new RequisicaoConhecimentoDao();
        final RequisicaoHabilidadeDao requisicaoHabilidadeDao = new RequisicaoHabilidadeDao();
        final RequisicaoAtitudeIndividualDao requisicaoAtitudeIndividualDao = new RequisicaoAtitudeIndividualDao();
        final TriagemRequisicaoPessoalDao triagemRequisicaoPessoalDao = new TriagemRequisicaoPessoalDao();
        ServiceLocator.getInstance().getService(RequisicaoPessoalService.class, null);

        // Seta o TransactionController para os DAOs
        requisicaoPessoalDao.setTransactionControler(tc);
        requisicaoPessoalDao.setTransactionControler(tc);
        requisicaoFormacaoAcademicaDao.setTransactionControler(tc);
        requisicaoCertificacaoDao.setTransactionControler(tc);
        requisicaoCursoDao.setTransactionControler(tc);
        requisicaoExperienciaInformaticaDao.setTransactionControler(tc);
        requisicaoIdiomaDao.setTransactionControler(tc);
        requisicaoExperienciaAnteriorDao.setTransactionControler(tc);
        requisicaoConhecimentoDao.setTransactionControler(tc);
        requisicaoHabilidadeDao.setTransactionControler(tc);
        requisicaoAtitudeIndividualDao.setTransactionControler(tc);
        triagemRequisicaoPessoalDao.setTransactionControler(tc);

        // Executa operacoes pertinentes ao negocio.
        final RequisicaoPessoalDTO requisicaoPessoalDto = (RequisicaoPessoalDTO) model;

        requisicaoPessoalDto.getAcao();
        final String entrevistaGestor = requisicaoPessoalDto.getPreRequisitoEntrevistaGestor();

        /*
         * if (requisicaoPessoalDto != null && requisicaoPessoalDto.getIdSolicitacaoServico() != null) {
         * requisicaoPessoalDto = (RequisicaoPessoalDTO) requisicaoPessoalService.restore(requisicaoPessoalDto);
         * model = requisicaoPessoalDto;
         * requisicaoPessoalDto.setAcao(acao);
         * }
         */

        if (requisicaoPessoalDto != null && requisicaoPessoalDto.getAcao() != null && requisicaoPessoalDto.getAcao().equals(RequisicaoPessoalDTO.ACAO_ANALISE)) {

            final ParecerServiceEjb parecerService = new ParecerServiceEjb();

            String aprovado = "S";

            if (requisicaoPessoalDto.getRejeitada().equalsIgnoreCase("S")) {

                aprovado = "N";
            }

            /*
             * Se está marcando como aprovado
             * então limpa uma possível justificativa de Não aprovada.
             * uelen.pereira
             */
            if (aprovado.equalsIgnoreCase("S")) {

                requisicaoPessoalDto.setIdJustificativaValidacao(null);
                requisicaoPessoalDto.setComplemJustificativaValidacao("");
            }

            final ParecerDTO parecerDto = parecerService.createOrUpdate(tc, requisicaoPessoalDto.getIdParecerValidacao(), solicitacaoServicoDto.getUsuarioDto(),
                    requisicaoPessoalDto.getIdJustificativaValidacao(), requisicaoPessoalDto.getComplemJustificativaValidacao(), aprovado);
            requisicaoPessoalDto.setIdParecerValidacao(parecerDto.getIdParecer());

        } else if (requisicaoPessoalDto != null && requisicaoPessoalDto.getAcao() != null && requisicaoPessoalDto.getAcao().equals(RequisicaoPessoalDTO.ACAO_CRIACAO)) {

            requisicaoFormacaoAcademicaDao.deleteByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());

            if (requisicaoPessoalDto.getColFormacaoAcademica() != null) {
                for (final RequisicaoFormacaoAcademicaDTO requisicaoFormacaoAcademicaDto : requisicaoPessoalDto.getColFormacaoAcademica()) {
                    requisicaoFormacaoAcademicaDto.setIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
                    requisicaoFormacaoAcademicaDao.create(requisicaoFormacaoAcademicaDto);
                }
            }

            requisicaoCertificacaoDao.deleteByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
            if (requisicaoPessoalDto.getColCertificacao() != null) {
                for (final RequisicaoCertificacaoDTO requisicaoCertificacaoDto : requisicaoPessoalDto.getColCertificacao()) {
                    requisicaoCertificacaoDto.setIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
                    requisicaoCertificacaoDao.create(requisicaoCertificacaoDto);
                }
            }

            requisicaoCursoDao.deleteByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
            if (requisicaoPessoalDto.getColCurso() != null) {
                for (final RequisicaoCursoDTO requisicaoCursoDto : requisicaoPessoalDto.getColCurso()) {
                    requisicaoCursoDto.setIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
                    requisicaoCursoDao.create(requisicaoCursoDto);
                }
            }

            requisicaoExperienciaInformaticaDao.deleteByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
            if (requisicaoPessoalDto.getColExperienciaInformatica() != null) {
                for (final RequisicaoExperienciaInformaticaDTO requisicaoExperienciaInformaticaDto : requisicaoPessoalDto.getColExperienciaInformatica()) {
                    requisicaoExperienciaInformaticaDto.setIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
                    requisicaoExperienciaInformaticaDao.create(requisicaoExperienciaInformaticaDto);
                }
            }

            requisicaoIdiomaDao.deleteByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
            if (requisicaoPessoalDto.getColIdioma() != null) {
                for (final RequisicaoIdiomaDTO requisicaoIdiomaDto : requisicaoPessoalDto.getColIdioma()) {
                    requisicaoIdiomaDto.setIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
                    requisicaoIdiomaDao.create(requisicaoIdiomaDto);
                }
            }

            requisicaoExperienciaAnteriorDao.deleteByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
            if (requisicaoPessoalDto.getColExperienciaAnterior() != null) {
                for (final RequisicaoExperienciaAnteriorDTO requisicaoExperienciaAnteriorDto : requisicaoPessoalDto.getColExperienciaAnterior()) {
                    requisicaoExperienciaAnteriorDto.setIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
                    requisicaoExperienciaAnteriorDao.create(requisicaoExperienciaAnteriorDto);
                }
            }

            requisicaoConhecimentoDao.deleteByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
            if (requisicaoPessoalDto.getColConhecimento() != null) {
                for (final RequisicaoConhecimentoDTO requisicaoConhecimentoDto : requisicaoPessoalDto.getColConhecimento()) {
                    requisicaoConhecimentoDto.setIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
                    requisicaoConhecimentoDao.create(requisicaoConhecimentoDto);
                }
            }

            requisicaoHabilidadeDao.deleteByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
            if (requisicaoPessoalDto.getColHabilidade() != null) {
                for (final RequisicaoHabilidadeDTO requisicaoHabilidadeDto : requisicaoPessoalDto.getColHabilidade()) {
                    requisicaoHabilidadeDto.setIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
                    requisicaoHabilidadeDao.create(requisicaoHabilidadeDto);
                }
            }

            requisicaoAtitudeIndividualDao.deleteByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
            if (requisicaoPessoalDto.getColAtitudeIndividual() != null) {
                for (final RequisicaoAtitudeIndividualDTO requisicaoAtitudeIndividualDto : requisicaoPessoalDto.getColAtitudeIndividual()) {
                    requisicaoAtitudeIndividualDto.setIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
                    requisicaoAtitudeIndividualDao.create(requisicaoAtitudeIndividualDto);
                }
            }
        } else if (requisicaoPessoalDto != null && requisicaoPessoalDto.getAcao() != null && requisicaoPessoalDto.getAcao().equals(RequisicaoPessoalDTO.ACAO_TRIAGEM)) {
            triagemRequisicaoPessoalDao.deleteByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
            if (requisicaoPessoalDto.getColTriagem() != null) {
                for (final TriagemRequisicaoPessoalDTO triagemRequisicaoPessoalDto : requisicaoPessoalDto.getColTriagem()) {
                    triagemRequisicaoPessoalDto.setIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());

                    /**
                     * Desenvolvedor: David Rodrigues - Data: 26/03/2014 - Horário: 14:34 - ID Citsmart: 0
                     * Motivo/Comentário: Adaptação no codido para funcionamento do Historico Funcional (Item Historico Funcional)
                     * Inclusão Item Historico Funcional do Candidato.
                     */
                    this.inserirRegistroHistorico(triagemRequisicaoPessoalDto.getIdCurriculo(), triagemRequisicaoPessoalDto.getIdSolicitacaoServico(), tc, solicitacaoServicoDto
                            .getUsuarioDto().getIdUsuario());

                    triagemRequisicaoPessoalDao.create(triagemRequisicaoPessoalDto);
                }
            }
        }
        requisicaoPessoalDto.setPreRequisitoEntrevistaGestor(entrevistaGestor);
        if (requisicaoPessoalDto.getIdCargo() != null || requisicaoPessoalDto.getIdFuncao() != null || requisicaoPessoalDto.getVagas() != null
                || requisicaoPessoalDto.getTipoContratacao() != null || requisicaoPessoalDto.getMotivoContratacao() != null || requisicaoPessoalDto.getSalario() != null
                || requisicaoPessoalDto.getIdCentroCusto() != null || requisicaoPessoalDto.getIdProjeto() != null || requisicaoPessoalDto.getIdParecerValidacao() != null
                || requisicaoPessoalDto.getRejeitada() != null || requisicaoPessoalDto.getConfidencial() != null || requisicaoPessoalDto.getBeneficios() != null
                || requisicaoPessoalDto.getFolgas() != null || requisicaoPessoalDto.getIdJornada() != null || requisicaoPessoalDto.getIdCidade() != null
                || requisicaoPessoalDto.getIdUnidade() != null || requisicaoPessoalDto.getIdLotacao() != null || requisicaoPessoalDto.getPreRequisitoEntrevistaGestor() != null
                || requisicaoPessoalDto.getIdUf() != null || requisicaoPessoalDto.getIdPais() != null || requisicaoPessoalDto.getQtdCandidatosAprovados() != null
                || requisicaoPessoalDto.getObservacoes() != null || requisicaoPessoalDto.getJustificativaRejeicao() != null
                || requisicaoPessoalDto.getMotivoDesistenciaCandidato() != null) {
            requisicaoPessoalDao.updateNotNull(requisicaoPessoalDto);
        }
    }

    @Override
    public void delete(final TransactionControler tc, final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {

        final RequisicaoPessoalDao requisicaoPessoalDao = new RequisicaoPessoalDao();
        final RequisicaoFormacaoAcademicaDao requisicaoFormacaoAcademicaDao = new RequisicaoFormacaoAcademicaDao();
        final RequisicaoCertificacaoDao requisicaoCertificacaoDao = new RequisicaoCertificacaoDao();
        final RequisicaoCursoDao requisicaoCursoDao = new RequisicaoCursoDao();
        final RequisicaoExperienciaInformaticaDao requisicaoExperienciaInformaticaDao = new RequisicaoExperienciaInformaticaDao();
        final RequisicaoIdiomaDao requisicaoIdiomaDao = new RequisicaoIdiomaDao();
        final RequisicaoExperienciaAnteriorDao requisicaoExperienciaAnteriorDao = new RequisicaoExperienciaAnteriorDao();
        final RequisicaoConhecimentoDao requisicaoConhecimentoDao = new RequisicaoConhecimentoDao();
        final RequisicaoHabilidadeDao requisicaoHabilidadeDao = new RequisicaoHabilidadeDao();
        final RequisicaoAtitudeIndividualDao requisicaoAtitudeIndividualDao = new RequisicaoAtitudeIndividualDao();

        // Faz validacao, caso exista.
        this.validaDelete(solicitacaoServicoDto, model);

        // Instancia ou obtem os DAOs necessarios.

        // Seta o TransactionController para os DAOs
        requisicaoPessoalDao.setTransactionControler(tc);
        requisicaoFormacaoAcademicaDao.setTransactionControler(tc);
        requisicaoCertificacaoDao.setTransactionControler(tc);
        requisicaoCursoDao.setTransactionControler(tc);
        requisicaoExperienciaInformaticaDao.setTransactionControler(tc);
        requisicaoIdiomaDao.setTransactionControler(tc);
        requisicaoExperienciaAnteriorDao.setTransactionControler(tc);
        requisicaoConhecimentoDao.setTransactionControler(tc);
        requisicaoHabilidadeDao.setTransactionControler(tc);
        requisicaoAtitudeIndividualDao.setTransactionControler(tc);

        final RequisicaoPessoalDTO requisicaoPessoalDto = (RequisicaoPessoalDTO) model;

        requisicaoFormacaoAcademicaDao.deleteByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
        requisicaoCertificacaoDao.deleteByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
        requisicaoCursoDao.deleteByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
        requisicaoExperienciaInformaticaDao.deleteByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
        requisicaoIdiomaDao.deleteByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
        requisicaoExperienciaAnteriorDao.deleteByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
        requisicaoConhecimentoDao.deleteByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
        requisicaoHabilidadeDao.deleteByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
        requisicaoAtitudeIndividualDao.deleteByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
        requisicaoPessoalDao.delete(requisicaoPessoalDto);

    }

    @Override
    public IDto restore(final IDto model) throws ServiceException {
        RequisicaoPessoalDTO requisicaoPessoalDto = null;
        try {
            requisicaoPessoalDto = (RequisicaoPessoalDTO) super.restore(model);
            if (requisicaoPessoalDto != null) {

                if (requisicaoPessoalDto.getSalario() == null || requisicaoPessoalDto.getSalario().doubleValue() == 0) {
                    requisicaoPessoalDto.setSalarioACombinar("S");
                }

                this.recuperaRelacionamentos(requisicaoPessoalDto);

            }

        } catch (final Exception e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
        return requisicaoPessoalDto;
    }

    /**
     * Desenvolvedor: David Rodrigues - Data: 26/11/2013 - Horário: 15:58 - ID Citsmart: 0
     *
     * Motivo/Comentário: Tratamento de NullPointerException
     *
     */

    public void recuperaRelacionamentos(final RequisicaoPessoalDTO requisicaoPessoalDto) throws ServiceException, LogicException {
        try {
            final Collection<RequisicaoFormacaoAcademicaDTO> colFormacaoAcademica = new RequisicaoFormacaoAcademicaDao().findByIdSolicitacaoServico(requisicaoPessoalDto
                    .getIdSolicitacaoServico());
            if (colFormacaoAcademica != null) {
                final FormacaoAcademicaDao formacaoAcademicaDao = new FormacaoAcademicaDao();
                for (final RequisicaoFormacaoAcademicaDTO requisicaoFormacaoAcademicaDto : colFormacaoAcademica) {
                    FormacaoAcademicaDTO formacaoAcademicaDto = new FormacaoAcademicaDTO();
                    formacaoAcademicaDto.setIdFormacaoAcademica(requisicaoFormacaoAcademicaDto.getIdFormacaoAcademica());
                    formacaoAcademicaDto = (FormacaoAcademicaDTO) formacaoAcademicaDao.restore(formacaoAcademicaDto);
                    if (formacaoAcademicaDto != null) {
                        requisicaoFormacaoAcademicaDto.setDescricao(formacaoAcademicaDto.getDescricao());
                        requisicaoFormacaoAcademicaDto.setDetalhe(formacaoAcademicaDto.getDetalhe());
                    }
                }
                requisicaoPessoalDto.setColFormacaoAcademica(colFormacaoAcademica);
            }

            final Collection<RequisicaoCertificacaoDTO> colCertificacao = new RequisicaoCertificacaoDao()
                    .findByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
            if (colCertificacao != null) {
                final CertificacaoDao certificacaoDao = new CertificacaoDao();
                for (final RequisicaoCertificacaoDTO requisicaoCertificacaoDto : colCertificacao) {
                    CertificacaoDTO certificacaoDto = new CertificacaoDTO();
                    certificacaoDto.setIdCertificacao(requisicaoCertificacaoDto.getIdCertificacao());
                    certificacaoDto = (CertificacaoDTO) certificacaoDao.restore(certificacaoDto);
                    if (certificacaoDto != null) {
                        requisicaoCertificacaoDto.setDescricao(certificacaoDto.getDescricao());
                        requisicaoCertificacaoDto.setDetalhe(certificacaoDto.getDetalhe());
                    }
                }
                requisicaoPessoalDto.setColCertificacao(colCertificacao);
            }

            final Collection<RequisicaoCursoDTO> colCurso = new RequisicaoCursoDao().findByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
            if (colCurso != null) {
                final CursoDao cursoDao = new CursoDao();
                for (final RequisicaoCursoDTO requisicaoCursoDto : colCurso) {
                    CursoDTO cursoDto = new CursoDTO();
                    cursoDto.setIdCurso(requisicaoCursoDto.getIdCurso());
                    cursoDto = (CursoDTO) cursoDao.restore(cursoDto);
                    if (cursoDto != null) {
                        requisicaoCursoDto.setDescricao(cursoDto.getDescricao());
                        requisicaoCursoDto.setDetalhe(cursoDto.getDetalhe());
                    }
                }
                requisicaoPessoalDto.setColCurso(colCurso);
            }

            final Collection<RequisicaoExperienciaInformaticaDTO> colExperienciaInformatica = new RequisicaoExperienciaInformaticaDao()
                    .findByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
            if (colExperienciaInformatica != null) {
                final ExperienciaInformaticaDao experienciaInformaticaDao = new ExperienciaInformaticaDao();
                for (final RequisicaoExperienciaInformaticaDTO requisicaoExperienciaInformaticaDto : colExperienciaInformatica) {
                    ExperienciaInformaticaDTO experienciaInformaticaDto = new ExperienciaInformaticaDTO();
                    experienciaInformaticaDto.setIdExperienciaInformatica(requisicaoExperienciaInformaticaDto.getIdExperienciaInformatica());
                    experienciaInformaticaDto = (ExperienciaInformaticaDTO) experienciaInformaticaDao.restore(experienciaInformaticaDto);
                    if (experienciaInformaticaDto != null) {
                        requisicaoExperienciaInformaticaDto.setDescricao(experienciaInformaticaDto.getDescricao());
                        requisicaoExperienciaInformaticaDto.setDetalhe(experienciaInformaticaDto.getDetalhe());
                    }
                }
                requisicaoPessoalDto.setColExperienciaInformatica(colExperienciaInformatica);
            }

            final Collection<RequisicaoIdiomaDTO> colIdioma = new RequisicaoIdiomaDao().findByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
            if (colIdioma != null) {
                final IdiomaDao idiomaDao = new IdiomaDao();
                for (final RequisicaoIdiomaDTO requisicaoIdiomaDto : colIdioma) {
                    IdiomaDTO idiomaDto = new IdiomaDTO();
                    idiomaDto.setIdIdioma(requisicaoIdiomaDto.getIdIdioma());
                    idiomaDto = (IdiomaDTO) idiomaDao.restore(idiomaDto);
                    if (idiomaDto != null) {
                        requisicaoIdiomaDto.setDescricao(idiomaDto.getDescricao());
                        requisicaoIdiomaDto.setDetalhe(idiomaDto.getDetalhe());
                    }
                }
                requisicaoPessoalDto.setColIdioma(colIdioma);
            }

            final Collection<RequisicaoExperienciaAnteriorDTO> colExperienciaAnterior = new RequisicaoExperienciaAnteriorDao().findByIdSolicitacaoServico(requisicaoPessoalDto
                    .getIdSolicitacaoServico());
            if (colExperienciaAnterior != null) {
                final ConhecimentoDao conhecimentoDao = new ConhecimentoDao();
                for (final RequisicaoExperienciaAnteriorDTO requisicaoExperienciaAnteriorDto : colExperienciaAnterior) {
                    ConhecimentoDTO conhecimentoDto = new ConhecimentoDTO();
                    conhecimentoDto.setIdConhecimento(requisicaoExperienciaAnteriorDto.getIdConhecimento());
                    conhecimentoDto = (ConhecimentoDTO) conhecimentoDao.restore(conhecimentoDto);
                    if (conhecimentoDto != null) {
                        requisicaoExperienciaAnteriorDto.setDescricao(conhecimentoDto.getDescricao());
                        requisicaoExperienciaAnteriorDto.setDetalhe(conhecimentoDto.getDetalhe());
                    }
                }
                requisicaoPessoalDto.setColExperienciaAnterior(colExperienciaAnterior);
            }

            final Collection<RequisicaoConhecimentoDTO> colConhecimento = new RequisicaoConhecimentoDao()
                    .findByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
            if (colConhecimento != null) {
                final ConhecimentoDao conhecimentoDao = new ConhecimentoDao();
                for (final RequisicaoConhecimentoDTO requisicaoConhecimentoDto : colConhecimento) {
                    ConhecimentoDTO conhecimentoDto = new ConhecimentoDTO();
                    conhecimentoDto.setIdConhecimento(requisicaoConhecimentoDto.getIdConhecimento());
                    conhecimentoDto = (ConhecimentoDTO) conhecimentoDao.restore(conhecimentoDto);
                    if (requisicaoConhecimentoDto != null) {
                        requisicaoConhecimentoDto.setDescricao(conhecimentoDto.getDescricao());
                        requisicaoConhecimentoDto.setDetalhe(conhecimentoDto.getDetalhe());
                    }
                }
                requisicaoPessoalDto.setColConhecimento(colConhecimento);
            }

            final Collection<RequisicaoHabilidadeDTO> colHabilidade = new RequisicaoHabilidadeDao().findByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
            if (colHabilidade != null) {
                final HabilidadeDao habilidadeDao = new HabilidadeDao();
                for (final RequisicaoHabilidadeDTO requisicaoHabilidadeDto : colHabilidade) {
                    HabilidadeDTO habilidadeDto = new HabilidadeDTO();
                    habilidadeDto.setIdHabilidade(requisicaoHabilidadeDto.getIdHabilidade());
                    habilidadeDto = (HabilidadeDTO) habilidadeDao.restore(habilidadeDto);
                    if (habilidadeDto != null) {
                        requisicaoHabilidadeDto.setDescricao(habilidadeDto.getDescricao());
                        requisicaoHabilidadeDto.setDetalhe(habilidadeDto.getDetalhe());
                    }
                }
                requisicaoPessoalDto.setColHabilidade(colHabilidade);
            }

            final Collection<RequisicaoAtitudeIndividualDTO> colAtitudeIndividual = new RequisicaoAtitudeIndividualDao().findByIdSolicitacaoServico(requisicaoPessoalDto
                    .getIdSolicitacaoServico());
            if (colAtitudeIndividual != null) {
                final AtitudeIndividualDao atitudeIndividualDao = new AtitudeIndividualDao();
                for (final RequisicaoAtitudeIndividualDTO requisicaoAtitudeIndividualDto : colAtitudeIndividual) {
                    AtitudeIndividualDTO atitudeIndividualDto = new AtitudeIndividualDTO();
                    atitudeIndividualDto.setIdAtitudeIndividual(requisicaoAtitudeIndividualDto.getIdAtitudeIndividual());
                    atitudeIndividualDto = (AtitudeIndividualDTO) atitudeIndividualDao.restore(atitudeIndividualDto);
                    if (atitudeIndividualDto != null) {
                        requisicaoAtitudeIndividualDto.setDescricao(atitudeIndividualDto.getDescricao());
                        requisicaoAtitudeIndividualDto.setDetalhe(atitudeIndividualDto.getDetalhe());
                    }
                }
                requisicaoPessoalDto.setColAtitudeIndividual(colAtitudeIndividual);
            }

            if (requisicaoPessoalDto.getIdCidade() != null) {
                CidadesDTO cidadeDto = new CidadesDTO();
                cidadeDto.setIdCidade(requisicaoPessoalDto.getIdCidade());
                cidadeDto = (CidadesDTO) new CidadesDao().restore(cidadeDto);
                if (cidadeDto != null && cidadeDto.getIdUf() != null) {
                    requisicaoPessoalDto.setIdUf(cidadeDto.getIdUf());
                    final UfDTO ufDto = new UfDao().findByIdUf(cidadeDto.getIdUf());
                    if (ufDto != null) {
                        requisicaoPessoalDto.setIdPais(ufDto.getIdPais());
                    } else {
                        requisicaoPessoalDto.setIdPais(new Integer(1)); // retirar
                    }
                }
            }

            final Collection colTriados = new TriagemRequisicaoPessoalDao().findByIdSolicitacaoServico(requisicaoPessoalDto.getIdSolicitacaoServico());
            if (colTriados != null) {
                requisicaoPessoalDto.setColTriagem(colTriados);
            }

        } catch (final Exception e) {
            e.printStackTrace();
            throw new LogicException(e);
        }
    }

    @Override
    public void preparaSolicitacaoParaAprovacao(final SolicitacaoServicoDTO solicitacaoDto, final ItemTrabalho itemTrabalho, final String aprovacao, final Integer idJustificativa,
            final String observacoes) throws Exception {

    }

    @Override
    public String getInformacoesComplementaresFmtTexto(final SolicitacaoServicoDTO solicitacaoDto, final ItemTrabalho itemTrabalho) throws Exception {
        return solicitacaoDto.getDescricaoSemFormatacao();
    }

    /**
     * @param idCurriculo
     * @param idRequisicao
     *            - ID da solicitação de serviço - Requisição Pessoal
     * @param tc
     * @param idResponsavel
     *
     * @author david.silva
     */
    private void inserirRegistroHistorico(final Integer idCurriculo, final Integer idRequisicao, final TransactionControler tc, final Integer idResponsavel) throws Exception {
        final HistoricoFuncionalDao funcionalDao = new HistoricoFuncionalDao();
        HistoricoFuncionalDTO funcionalDTO = new HistoricoFuncionalDTO();

        final ItemHistoricoFuncionalDao dao = new ItemHistoricoFuncionalDao();

        CandidatoDTO candidatoDto = new CandidatoDTO();
        final CandidatoDao candidatoDao = new CandidatoDao();

        funcionalDTO = funcionalDao.restoreByIdCurriculo(idCurriculo);
        if (funcionalDTO != null && funcionalDTO.getIdCandidato() != null) {
            candidatoDto = candidatoDao.restoreByID(funcionalDTO.getIdCandidato());
            if (candidatoDto != null) {
                final StringBuilder descricao = new StringBuilder();
                descricao.append("Candidato ");
                descricao.append(candidatoDto.getNome());
                descricao.append(" foi incluido no processo de seleção ");
                descricao.append("referente a Requisição Pessoal Nº " + idRequisicao);
                dao.inserirRegistroHistoricoAutomatico(funcionalDTO.getIdCandidato(), idResponsavel, "Inclusão do Currículo em Processo de Seleção", descricao.toString(), tc);
            }
        }
    }

}
