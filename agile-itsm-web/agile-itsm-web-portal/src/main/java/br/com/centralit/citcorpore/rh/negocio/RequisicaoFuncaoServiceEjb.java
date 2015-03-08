package br.com.centralit.citcorpore.rh.negocio;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.bpm.negocio.ItemTrabalho;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.SolicitacaoServicoDao;
import br.com.centralit.citcorpore.rh.bean.CompetenciasTecnicasDTO;
import br.com.centralit.citcorpore.rh.bean.PerspectivaComplexidadeDTO;
import br.com.centralit.citcorpore.rh.bean.PerspectivaComportamentalFuncaoDTO;
import br.com.centralit.citcorpore.rh.bean.PerspectivaTecnicaCertificacaoDTO;
import br.com.centralit.citcorpore.rh.bean.PerspectivaTecnicaCursoDTO;
import br.com.centralit.citcorpore.rh.bean.PerspectivaTecnicaExperienciaDTO;
import br.com.centralit.citcorpore.rh.bean.PerspectivaTecnicaFormacaoAcademicaDTO;
import br.com.centralit.citcorpore.rh.bean.PerspectivaTecnicaIdiomaDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoFuncaoDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoPessoalDTO;
import br.com.centralit.citcorpore.rh.integracao.CompetenciasTecnicasDao;
import br.com.centralit.citcorpore.rh.integracao.PerspectivaComplexidadeDao;
import br.com.centralit.citcorpore.rh.integracao.PerspectivaComportamentalFuncaoDao;
import br.com.centralit.citcorpore.rh.integracao.PerspectivaTecnicaCertificacaoDao;
import br.com.centralit.citcorpore.rh.integracao.PerspectivaTecnicaCursoDao;
import br.com.centralit.citcorpore.rh.integracao.PerspectivaTecnicaExperienciaDao;
import br.com.centralit.citcorpore.rh.integracao.PerspectivaTecnicaFormacaoAcademicaDao;
import br.com.centralit.citcorpore.rh.integracao.PerspectivaTecnicaIdiomaDao;
import br.com.centralit.citcorpore.rh.integracao.RequisicaoAtitudeIndividualDao;
import br.com.centralit.citcorpore.rh.integracao.RequisicaoCertificacaoDao;
import br.com.centralit.citcorpore.rh.integracao.RequisicaoConhecimentoDao;
import br.com.centralit.citcorpore.rh.integracao.RequisicaoCursoDao;
import br.com.centralit.citcorpore.rh.integracao.RequisicaoExperienciaAnteriorDao;
import br.com.centralit.citcorpore.rh.integracao.RequisicaoExperienciaInformaticaDao;
import br.com.centralit.citcorpore.rh.integracao.RequisicaoFormacaoAcademicaDao;
import br.com.centralit.citcorpore.rh.integracao.RequisicaoFuncaoDao;
import br.com.centralit.citcorpore.rh.integracao.RequisicaoHabilidadeDao;
import br.com.centralit.citcorpore.rh.integracao.RequisicaoIdiomaDao;
import br.com.centralit.citcorpore.rh.integracao.RequisicaoPessoalDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.WebUtil;

@SuppressWarnings({"unchecked"})
public class RequisicaoFuncaoServiceEjb extends CrudServiceImpl implements RequisicaoFuncaoService {

    private RequisicaoFuncaoDao dao;

    @Override
    protected RequisicaoFuncaoDao getDao() {
        if (dao == null) {
            dao = new RequisicaoFuncaoDao();
        }
        return dao;
    }

    public String i18n_Message(final UsuarioDTO usuario, final String key) {
        if (usuario != null) {
            if (UtilI18N.internacionaliza(usuario.getLocale(), key) != null) {
                return UtilI18N.internacionaliza(usuario.getLocale(), key);
            }
            return key;
        }
        return key;
    }

    @Override
    public IDto deserializaObjeto(final String serialize) throws Exception {
        RequisicaoFuncaoDTO requisicaoFuncaoDTO = null;

        if (serialize != null) {
            requisicaoFuncaoDTO = (RequisicaoFuncaoDTO) WebUtil.deserializeObject(RequisicaoFuncaoDTO.class, serialize);
        }

        return requisicaoFuncaoDTO;
    }

    @Override
    public void validaCreate(final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {
        final RequisicaoFuncaoDTO requisicaoFuncaoDTO = (RequisicaoFuncaoDTO) model;

        this.validaAtualizacao(solicitacaoServicoDto, requisicaoFuncaoDTO);
    }

    private void validaAtualizacao(final SolicitacaoServicoDTO solicitacaoServicoDto, final RequisicaoFuncaoDTO requisicaoFuncaoDTO) throws Exception {

    }

    @Override
    public void validaDelete(final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void validaUpdate(final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {
        // RequisicaoFuncaoDTO requisicaoFuncaoDTO = (RequisicaoFuncaoDTO) model;
        // if (solicitacaoServicoDto != null && solicitacaoServicoDto.getAcaoFluxo() != null) {
        // requisicaoFuncaoDTO.setAcaoManterGravarTarefa(solicitacaoServicoDto.getAcaoFluxo());
        // }
        // validaAtualizacao(solicitacaoServicoDto, requisicaoFuncaoDTO);
    }

    @Override
    public IDto create(final TransactionControler tc, final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {
        this.validaCreate(solicitacaoServicoDto, model);

        // Instancia Objeto controlador de transacao
        final RequisicaoFuncaoDao requisicaoFuncaoDao = new RequisicaoFuncaoDao();
        final SolicitacaoServicoDao solicitacaoServicoDao = new SolicitacaoServicoDao();

        solicitacaoServicoDao.setTransactionControler(tc);

        // Seta o TransactionController para os DAOs
        requisicaoFuncaoDao.setTransactionControler(tc);

        // Executa operacoes pertinentes ao negocio.
        RequisicaoFuncaoDTO requisicaoFuncaoDTO = (RequisicaoFuncaoDTO) model;
        if (requisicaoFuncaoDTO.getIdSolicitacaoServico() == null) {
            requisicaoFuncaoDTO.setFase("etapa1");
        }
        if (solicitacaoServicoDto.getIdSolicitacaoServico() != null) {
            requisicaoFuncaoDTO.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());

            if (requisicaoFuncaoDTO.getNomeFuncao() == null || requisicaoFuncaoDTO.getNomeFuncao().equals("")) {
                throw new Exception(this.i18n_Message(solicitacaoServicoDto.getUsuarioDto(), "requisicaoFuncao.obrigatorioFuncao"));
            }
            if (requisicaoFuncaoDTO.getNumeroPessoas() == null || requisicaoFuncaoDTO.getNumeroPessoas().equals("")) {
                throw new Exception(this.i18n_Message(solicitacaoServicoDto.getUsuarioDto(), "requisicaoFuncao.obrigatorioNumeroPessoa"));
            }
            if (requisicaoFuncaoDTO.getPossuiSubordinados() == null || requisicaoFuncaoDTO.getPossuiSubordinados().equals("")) {
                throw new Exception(this.i18n_Message(solicitacaoServicoDto.getUsuarioDto(), "requisicaoFuncao.obrigatorioPossuiSubordinados"));
            }
            if (requisicaoFuncaoDTO.getJustificativaFuncao() == null || requisicaoFuncaoDTO.getJustificativaFuncao().equals("")) {
                throw new Exception(this.i18n_Message(solicitacaoServicoDto.getUsuarioDto(), "requisicaoFuncao.obrigatorioJustificativa"));
            }
            if (requisicaoFuncaoDTO.getResumoAtividades() == null || requisicaoFuncaoDTO.getResumoAtividades().equals("")) {
                throw new Exception(this.i18n_Message(solicitacaoServicoDto.getUsuarioDto(), "requisicaoFuncao.obrigatorioResumoAtividades"));
            }
            requisicaoFuncaoDTO.setFuncao(requisicaoFuncaoDTO.getNomeFuncao());
            requisicaoFuncaoDTO.setResumoFuncao(requisicaoFuncaoDTO.getResumoAtividades());
            requisicaoFuncaoDTO = (RequisicaoFuncaoDTO) requisicaoFuncaoDao.create(requisicaoFuncaoDTO);
        }

        return model;

    }

    @Override
    public void update(final TransactionControler tc, final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {
        this.validaUpdate(solicitacaoServicoDto, model);

        // Instancia Objeto controlador de transacao
        final RequisicaoFuncaoDao requisicaoFuncaoDao = new RequisicaoFuncaoDao();

        // Seta o TransactionController para os DAOs
        requisicaoFuncaoDao.setTransactionControler(tc);

        // Executa operacoes pertinentes ao negocio.
        final RequisicaoFuncaoDTO requisicaoFuncaoDTO = (RequisicaoFuncaoDTO) model;
        final String etapaAtual = requisicaoFuncaoDTO.getFase();

        // Validação campos vazio, etapa 2
        if (etapaAtual.equals("etapa1")) {
            if (requisicaoFuncaoDTO.getRequisicaoValida() == null || requisicaoFuncaoDTO.getRequisicaoValida().equals("")) {
                throw new Exception(this.i18n_Message(solicitacaoServicoDto.getUsuarioDto(), "requisicaoFuncao.obrigatorioRequisicaoValida"));
            }

            if (requisicaoFuncaoDTO.getJustificativaValidacao() == null || requisicaoFuncaoDTO.getJustificativaValidacao().equals("")
                    && requisicaoFuncaoDTO.getRequisicaoValida().equals("N")) {
                throw new Exception(this.i18n_Message(solicitacaoServicoDto.getUsuarioDto(), "requisicaoFuncao.obrigatorioJustificativaNaoValida"));
            }
        }

        // Validação campos vazio, etapa 3
        if (etapaAtual.equals("etapa2")) {
            if (requisicaoFuncaoDTO.getCargo() == null || requisicaoFuncaoDTO.getCargo().equals("")) {
                throw new Exception(this.i18n_Message(solicitacaoServicoDto.getUsuarioDto(), "requisicaoFuncao.obrigatorioCargo"));
            }

            if (requisicaoFuncaoDTO.getIdCargo() == null || requisicaoFuncaoDTO.getIdCargo().equals("")) {
                throw new Exception(this.i18n_Message(solicitacaoServicoDto.getUsuarioDto(), "requisicaoFuncao.cargoNaoCadastrado"));
            }

            if (requisicaoFuncaoDTO.getFuncao() == null || requisicaoFuncaoDTO.getFuncao().equals("")) {
                throw new Exception(this.i18n_Message(solicitacaoServicoDto.getUsuarioDto(), "requisicaoFuncao.obrigatorioFuncao"));
            }

            if (requisicaoFuncaoDTO.getResumoFuncao() == null || requisicaoFuncaoDTO.getResumoFuncao().equals("")) {
                throw new Exception(this.i18n_Message(solicitacaoServicoDto.getUsuarioDto(), "requisicaoFuncao.obrigatorioResumoFuncao"));
            }
        }

        // Validação campos vazio, etapa 4
        if (etapaAtual.equals("etapa3")) {
            if (requisicaoFuncaoDTO.getDescricaoValida() == null || requisicaoFuncaoDTO.getDescricaoValida().equals("")) {
                throw new Exception(this.i18n_Message(solicitacaoServicoDto.getUsuarioDto(), "requisicaoFuncao.obrigatorioDescricaoValida"));
            }

            if (requisicaoFuncaoDTO.getJustificativaDescricaoFuncao() == null || requisicaoFuncaoDTO.getJustificativaDescricaoFuncao().equals("")
                    && requisicaoFuncaoDTO.getDescricaoValida().equals("N")) {
                throw new Exception(this.i18n_Message(solicitacaoServicoDto.getUsuarioDto(), "requisicaoFuncao.obrigatorioJustificativaNaoValida"));
            }
        }

        if (etapaAtual.equals("etapa1") && requisicaoFuncaoDTO.getRequisicaoValida().equals("S") && solicitacaoServicoDto.getAcaoFluxo().equals("E")) {
            requisicaoFuncaoDTO.setFase("etapa2");
        } else {
            if (etapaAtual.equals("etapa1") && requisicaoFuncaoDTO.getRequisicaoValida().equals("N") && solicitacaoServicoDto.getAcaoFluxo().equals("E")) {
                requisicaoFuncaoDTO.setFase("finalizado");
            }
        }

        if (etapaAtual.equals("etapa2") && solicitacaoServicoDto.getAcaoFluxo().equals("E")) {
            requisicaoFuncaoDTO.setFase("etapa3");
        }

        if (etapaAtual.equals("etapa3") && requisicaoFuncaoDTO.getDescricaoValida() != null && requisicaoFuncaoDTO.getDescricaoValida().equals("S")
                && solicitacaoServicoDto.getAcaoFluxo().equals("E")) {
            requisicaoFuncaoDTO.setFase("finalizado");
        } else {
            if (etapaAtual.equals("etapa3") && requisicaoFuncaoDTO.getDescricaoValida() != null && requisicaoFuncaoDTO.getDescricaoValida().equals("N")
                    && solicitacaoServicoDto.getAcaoFluxo().equals("E")) {
                requisicaoFuncaoDTO.setFase("etapa2");
            }
        }

        // Adiciona perspectiva comportamental a função
        if (requisicaoFuncaoDTO.getColPerspectivaComportamental() != null && requisicaoFuncaoDTO.getColPerspectivaComportamental().size() > 0) {
            final PerspectivaComportamentalFuncaoDao dao = new PerspectivaComportamentalFuncaoDao();
            final Collection<PerspectivaComportamentalFuncaoDTO> collection = dao.findByidSolicitacao(requisicaoFuncaoDTO.getIdSolicitacaoServico());

            if (collection != null && collection.size() > 0) {
                dao.deleteByIdSolicitacao(requisicaoFuncaoDTO.getIdSolicitacaoServico());
            }

            for (final PerspectivaComportamentalFuncaoDTO perspectivaComportamentalDTO : requisicaoFuncaoDTO.getColPerspectivaComportamental()) {
                perspectivaComportamentalDTO.setIdSolicitacaoServico(requisicaoFuncaoDTO.getIdSolicitacaoServico());
                dao.create(perspectivaComportamentalDTO);
            }
        }

        // Adiciona perspectiva complexidade a função
        if (requisicaoFuncaoDTO.getColPerspectivaComplexidade() != null && requisicaoFuncaoDTO.getColPerspectivaComplexidade().size() > 0) {
            final PerspectivaComplexidadeDao dao = new PerspectivaComplexidadeDao();
            final Collection<PerspectivaComplexidadeDTO> collection = dao.findByidSolicitacao(requisicaoFuncaoDTO.getIdSolicitacaoServico());

            if (collection != null && collection.size() > 0) {
                dao.deleteByIdSolicitacao(requisicaoFuncaoDTO.getIdSolicitacaoServico());
            }
            for (final PerspectivaComplexidadeDTO perspectivaComplexidadeDTO : requisicaoFuncaoDTO.getColPerspectivaComplexidade()) {
                perspectivaComplexidadeDTO.setIdSolicitacaoServico(requisicaoFuncaoDTO.getIdSolicitacaoServico());
                dao.create(perspectivaComplexidadeDTO);
            }
        }

        // Adiciona perspectiva tecnica formação academica a função
        if (requisicaoFuncaoDTO.getColPerspectivaTecnicaFormacaoAcademica() != null && requisicaoFuncaoDTO.getColPerspectivaTecnicaFormacaoAcademica().size() > 0) {
            final PerspectivaTecnicaFormacaoAcademicaDao dao = new PerspectivaTecnicaFormacaoAcademicaDao();
            final Collection<PerspectivaTecnicaFormacaoAcademicaDTO> collection = dao.findByidSolicitacao(requisicaoFuncaoDTO.getIdSolicitacaoServico());

            if (collection != null && collection.size() > 0) {
                dao.deleteByIdSolicitacao(requisicaoFuncaoDTO.getIdSolicitacaoServico());
            }

            for (final PerspectivaTecnicaFormacaoAcademicaDTO perspectivaTecnicaFormacaoAcademicaDTO : requisicaoFuncaoDTO.getColPerspectivaTecnicaFormacaoAcademica()) {
                perspectivaTecnicaFormacaoAcademicaDTO.setIdSolicitacaoServico(requisicaoFuncaoDTO.getIdSolicitacaoServico());
                dao.create(perspectivaTecnicaFormacaoAcademicaDTO);
            }
        }

        // Adiciona perspectiva tecnica certificação a função
        if (requisicaoFuncaoDTO.getColPerspectivaTecnicaCertificacao() != null && requisicaoFuncaoDTO.getColPerspectivaTecnicaCertificacao().size() > 0) {
            final PerspectivaTecnicaCertificacaoDao dao = new PerspectivaTecnicaCertificacaoDao();

            final Collection<PerspectivaTecnicaCertificacaoDTO> collection = dao.findByidSolicitacao(requisicaoFuncaoDTO.getIdSolicitacaoServico());

            if (collection != null && collection.size() > 0) {
                dao.deleteByIdSolicitacao(requisicaoFuncaoDTO.getIdSolicitacaoServico());
            }
            for (final PerspectivaTecnicaCertificacaoDTO perspectivaTecnicaCertificacaoDTO : requisicaoFuncaoDTO.getColPerspectivaTecnicaCertificacao()) {
                perspectivaTecnicaCertificacaoDTO.setIdSolicitacaoServico(requisicaoFuncaoDTO.getIdSolicitacaoServico());
                dao.create(perspectivaTecnicaCertificacaoDTO);
            }
        }

        // Adiciona perspectiva tecnica curso a função
        if (requisicaoFuncaoDTO.getColPerspectivaTecnicaCurso() != null && requisicaoFuncaoDTO.getColPerspectivaTecnicaCurso().size() > 0) {
            final PerspectivaTecnicaCursoDao dao = new PerspectivaTecnicaCursoDao();

            final Collection<PerspectivaTecnicaCursoDTO> collection = dao.findByidSolicitacao(requisicaoFuncaoDTO.getIdSolicitacaoServico());

            if (collection != null && collection.size() > 0) {
                dao.deleteByIdSolicitacao(requisicaoFuncaoDTO.getIdSolicitacaoServico());
            }
            for (final PerspectivaTecnicaCursoDTO perspectivaTecnicaCursoDTO : requisicaoFuncaoDTO.getColPerspectivaTecnicaCurso()) {
                perspectivaTecnicaCursoDTO.setIdSolicitacaoServico(requisicaoFuncaoDTO.getIdSolicitacaoServico());
                dao.create(perspectivaTecnicaCursoDTO);
            }
        }

        // Adiciona perspectiva tecnica idioma a função
        if (requisicaoFuncaoDTO.getColPerspectivaTecnicaIdioma() != null && requisicaoFuncaoDTO.getColPerspectivaTecnicaIdioma().size() > 0) {
            final PerspectivaTecnicaIdiomaDao dao = new PerspectivaTecnicaIdiomaDao();

            final Collection<PerspectivaTecnicaIdiomaDTO> collection = dao.findByidSolicitacao(requisicaoFuncaoDTO.getIdSolicitacaoServico());

            if (collection != null && collection.size() > 0) {
                dao.deleteByIdSolicitacao(requisicaoFuncaoDTO.getIdSolicitacaoServico());
            }
            for (final PerspectivaTecnicaIdiomaDTO perspectivaTecnicaIdiomaDTO : requisicaoFuncaoDTO.getColPerspectivaTecnicaIdioma()) {
                perspectivaTecnicaIdiomaDTO.setIdSolicitacaoServico(requisicaoFuncaoDTO.getIdSolicitacaoServico());
                dao.create(perspectivaTecnicaIdiomaDTO);
            }
        }

        // Adiciona perspectiva tecnica experiencia a função
        if (requisicaoFuncaoDTO.getColPerspectivaTecnicaExperiencia() != null && requisicaoFuncaoDTO.getColPerspectivaTecnicaExperiencia().size() > 0) {
            final PerspectivaTecnicaExperienciaDao dao = new PerspectivaTecnicaExperienciaDao();

            final Collection<PerspectivaTecnicaExperienciaDTO> collection = dao.findByidSolicitacao(requisicaoFuncaoDTO.getIdSolicitacaoServico());

            if (collection != null && collection.size() > 0) {
                dao.deleteByIdSolicitacao(requisicaoFuncaoDTO.getIdSolicitacaoServico());
            }
            for (final PerspectivaTecnicaExperienciaDTO perspectivaTecnicaExperienciaDTO : requisicaoFuncaoDTO.getColPerspectivaTecnicaExperiencia()) {
                perspectivaTecnicaExperienciaDTO.setIdSolicitacaoServico(requisicaoFuncaoDTO.getIdSolicitacaoServico());
                dao.create(perspectivaTecnicaExperienciaDTO);
            }
        }

        // Adiciona competencia tecnica a função
        if (requisicaoFuncaoDTO.getColCompetenciasTecnicas() != null && requisicaoFuncaoDTO.getColCompetenciasTecnicas().size() > 0) {
            final CompetenciasTecnicasDao dao = new CompetenciasTecnicasDao();

            final Collection<CompetenciasTecnicasDTO> collection = dao.findByidSolicitacao(requisicaoFuncaoDTO.getIdSolicitacaoServico());

            if (collection != null && collection.size() > 0) {
                dao.deleteByIdSolicitacao(requisicaoFuncaoDTO.getIdSolicitacaoServico());
            }
            for (final CompetenciasTecnicasDTO competenciasTecnicasDTO : requisicaoFuncaoDTO.getColCompetenciasTecnicas()) {
                competenciasTecnicasDTO.setIdSolicitacaoServico(requisicaoFuncaoDTO.getIdSolicitacaoServico());
                dao.create(competenciasTecnicasDTO);
            }
        }

        requisicaoFuncaoDao.update(requisicaoFuncaoDTO);

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
    public void preparaSolicitacaoParaAprovacao(final SolicitacaoServicoDTO solicitacaoDto, final ItemTrabalho itemTrabalho, final String aprovacao, final Integer idJustificativa,
            final String observacoes) throws Exception {

    }

    @Override
    public String getInformacoesComplementaresFmtTexto(final SolicitacaoServicoDTO solicitacaoDto, final ItemTrabalho itemTrabalho) throws Exception {
        return solicitacaoDto.getDescricaoSemFormatacao();
    }

    @Override
    public Collection<PerspectivaComplexidadeDTO> restoreComplexidade(final RequisicaoFuncaoDTO requisicaoPessoalDto) throws Exception {
        final PerspectivaComplexidadeDao perspectivaComplexidadeDao = new PerspectivaComplexidadeDao();
        Collection<PerspectivaComplexidadeDTO> list = new ArrayList<>();
        list = perspectivaComplexidadeDao.findByidSolicitacao(requisicaoPessoalDto.getIdSolicitacaoServico());
        return list;
    }

    @Override
    public Collection<PerspectivaTecnicaFormacaoAcademicaDTO> restoreFormacao(final RequisicaoFuncaoDTO requisicaoPessoalDto) throws Exception {
        final PerspectivaTecnicaFormacaoAcademicaDao perspectivaTecnicaFormacaoAcademicaDao = new PerspectivaTecnicaFormacaoAcademicaDao();
        Collection<PerspectivaTecnicaFormacaoAcademicaDTO> list = new ArrayList<>();
        list = perspectivaTecnicaFormacaoAcademicaDao.findByidSolicitacao(requisicaoPessoalDto.getIdSolicitacaoServico());
        return list;
    }

    @Override
    public Collection<PerspectivaTecnicaCertificacaoDTO> restoreCertificacao(final RequisicaoFuncaoDTO requisicaoPessoalDto) throws Exception {
        final PerspectivaTecnicaCertificacaoDao perspectivaTecnicaCertificacaoDao = new PerspectivaTecnicaCertificacaoDao();
        Collection<PerspectivaTecnicaCertificacaoDTO> list = new ArrayList<>();
        list = perspectivaTecnicaCertificacaoDao.findByidSolicitacao(requisicaoPessoalDto.getIdSolicitacaoServico());
        return list;
    }

    @Override
    public Collection<PerspectivaTecnicaCursoDTO> restoreCurso(final RequisicaoFuncaoDTO requisicaoPessoalDto) throws Exception {
        final PerspectivaTecnicaCursoDao perspectivaTecnicaCertificacaoDao = new PerspectivaTecnicaCursoDao();
        Collection<PerspectivaTecnicaCursoDTO> list = new ArrayList<>();
        list = perspectivaTecnicaCertificacaoDao.findByidSolicitacao(requisicaoPessoalDto.getIdSolicitacaoServico());
        return list;
    }

    @Override
    public Collection<PerspectivaTecnicaIdiomaDTO> restoreIdiomas(final RequisicaoFuncaoDTO requisicaoPessoalDto) throws Exception {
        final PerspectivaTecnicaIdiomaDao perspectivaTecnicaIdiomaDao = new PerspectivaTecnicaIdiomaDao();
        Collection<PerspectivaTecnicaIdiomaDTO> list = new ArrayList<>();
        list = perspectivaTecnicaIdiomaDao.findByidSolicitacao(requisicaoPessoalDto.getIdSolicitacaoServico());
        return list;
    }

    @Override
    public Collection<PerspectivaTecnicaExperienciaDTO> restoreExperiencia(final RequisicaoFuncaoDTO requisicaoPessoalDto) throws Exception {
        final PerspectivaTecnicaExperienciaDao perspectivaTecnicaExperienciaDao = new PerspectivaTecnicaExperienciaDao();
        Collection<PerspectivaTecnicaExperienciaDTO> list = new ArrayList<>();
        list = perspectivaTecnicaExperienciaDao.findByidSolicitacao(requisicaoPessoalDto.getIdSolicitacaoServico());
        return list;
    }

    @Override
    public Collection<CompetenciasTecnicasDTO> restoreCompetencia(final RequisicaoFuncaoDTO requisicaoPessoalDto) throws Exception {
        final CompetenciasTecnicasDao competenciasTecnicasDao = new CompetenciasTecnicasDao();
        Collection<CompetenciasTecnicasDTO> list = new ArrayList<>();
        list = competenciasTecnicasDao.findByidSolicitacao(requisicaoPessoalDto.getIdSolicitacaoServico());
        return list;
    }

    @Override
    public Collection<PerspectivaComportamentalFuncaoDTO> restoreComportamental(final RequisicaoFuncaoDTO requisicaoPessoalDto) throws Exception {
        final PerspectivaComportamentalFuncaoDao perspectivaComportamentalDao = new PerspectivaComportamentalFuncaoDao();
        Collection<PerspectivaComportamentalFuncaoDTO> list = new ArrayList<>();
        list = perspectivaComportamentalDao.findByidSolicitacao(requisicaoPessoalDto.getIdSolicitacaoServico());
        return list;
    }

    @Override
    public Collection<RequisicaoFuncaoDTO> retornaFuncoesAprovadas() throws Exception {
        return this.getDao().retornaFuncoesAprovadas();
    }

    @Override
    public RequisicaoFuncaoDTO restoreWithNomeCargo(final RequisicaoFuncaoDTO requisicaoFuncaoDto) throws Exception {
        return this.getDao().restoreWithNomeCargo(requisicaoFuncaoDto);
    }

    /**
     * @param idSolicitacaoServico
     * @return
     * @throws Exception
     * @author euler.ramos
     */
    @Override
    public RequisicaoFuncaoDTO findByIdSolicitacao(final Integer idSolicitacaoServico) throws Exception {
        final Collection<RequisicaoFuncaoDTO> col = this.getDao().findByIdSolicitacao(idSolicitacaoServico);
        RequisicaoFuncaoDTO reqDto = new RequisicaoFuncaoDTO();
        if (col != null) {
            for (final RequisicaoFuncaoDTO dto : col) {
                reqDto = dto;
                break;
            }
        }
        return reqDto;
    }

}
