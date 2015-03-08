package br.com.centralit.citcorpore.rh.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.CargosDTO;
import br.com.centralit.citcorpore.bean.ParecerDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.integracao.CargosDao;
import br.com.centralit.citcorpore.negocio.CargosService;
import br.com.centralit.citcorpore.negocio.ComplemInfSolicitacaoServicoServiceEjb;
import br.com.centralit.citcorpore.negocio.ParecerServiceEjb;
import br.com.centralit.citcorpore.rh.bean.AtitudeIndividualDTO;
import br.com.centralit.citcorpore.rh.bean.CargoAtitudeIndividualDTO;
import br.com.centralit.citcorpore.rh.bean.CargoCertificacaoDTO;
import br.com.centralit.citcorpore.rh.bean.CargoConhecimentoDTO;
import br.com.centralit.citcorpore.rh.bean.CargoCursoDTO;
import br.com.centralit.citcorpore.rh.bean.CargoExperienciaAnteriorDTO;
import br.com.centralit.citcorpore.rh.bean.CargoExperienciaInformaticaDTO;
import br.com.centralit.citcorpore.rh.bean.CargoFormacaoAcademicaDTO;
import br.com.centralit.citcorpore.rh.bean.CargoHabilidadeDTO;
import br.com.centralit.citcorpore.rh.bean.CargoIdiomaDTO;
import br.com.centralit.citcorpore.rh.bean.CertificacaoDTO;
import br.com.centralit.citcorpore.rh.bean.ConhecimentoDTO;
import br.com.centralit.citcorpore.rh.bean.CursoDTO;
import br.com.centralit.citcorpore.rh.bean.DescricaoCargoDTO;
import br.com.centralit.citcorpore.rh.bean.ExperienciaInformaticaDTO;
import br.com.centralit.citcorpore.rh.bean.FormacaoAcademicaDTO;
import br.com.centralit.citcorpore.rh.bean.HabilidadeDTO;
import br.com.centralit.citcorpore.rh.bean.IdiomaDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoPessoalDTO;
import br.com.centralit.citcorpore.rh.integracao.AtitudeIndividualDao;
import br.com.centralit.citcorpore.rh.integracao.CargoAtitudeIndividualDao;
import br.com.centralit.citcorpore.rh.integracao.CargoCertificacaoDao;
import br.com.centralit.citcorpore.rh.integracao.CargoConhecimentoDao;
import br.com.centralit.citcorpore.rh.integracao.CargoCursoDao;
import br.com.centralit.citcorpore.rh.integracao.CargoExperienciaAnteriorDao;
import br.com.centralit.citcorpore.rh.integracao.CargoExperienciaInformaticaDao;
import br.com.centralit.citcorpore.rh.integracao.CargoFormacaoAcademicaDao;
import br.com.centralit.citcorpore.rh.integracao.CargoHabilidadeDao;
import br.com.centralit.citcorpore.rh.integracao.CargoIdiomaDao;
import br.com.centralit.citcorpore.rh.integracao.CertificacaoDao;
import br.com.centralit.citcorpore.rh.integracao.ConhecimentoDao;
import br.com.centralit.citcorpore.rh.integracao.CursoDao;
import br.com.centralit.citcorpore.rh.integracao.DescricaoCargoDao;
import br.com.centralit.citcorpore.rh.integracao.ExperienciaInformaticaDao;
import br.com.centralit.citcorpore.rh.integracao.FormacaoAcademicaDao;
import br.com.centralit.citcorpore.rh.integracao.HabilidadeDao;
import br.com.centralit.citcorpore.rh.integracao.IdiomaDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilStrings;
import br.com.citframework.util.WebUtil;

@SuppressWarnings({"unchecked"})
public class DescricaoCargoServiceEjb extends ComplemInfSolicitacaoServicoServiceEjb implements DescricaoCargoService {

    private DescricaoCargoDao dao;

    @Override
    protected DescricaoCargoDao getDao() {
        if (dao == null) {
            dao = new DescricaoCargoDao();
        }
        return dao;
    }

    @Override
    protected void validaCreate(final Object arg0) throws Exception {
        this.validaColecoes((DescricaoCargoDTO) arg0);
    }

    @Override
    protected void validaUpdate(final Object arg0) throws Exception {
        this.validaColecoes((DescricaoCargoDTO) arg0);
    }

    private void validaColecoes(final DescricaoCargoDTO descricaoCargoDto) throws Exception {
        if (descricaoCargoDto.getColFormacaoAcademica() == null || descricaoCargoDto.getColFormacaoAcademica().isEmpty()) {
            throw new LogicException(this.i18nMessage("solicitacaoCargo.necessarioFormacaoAcademica"));
        }
        if (descricaoCargoDto.getColConhecimento() == null || descricaoCargoDto.getColConhecimento().isEmpty()) {
            throw new LogicException(this.i18nMessage("solicitacaoCargo.necessarioConhecimento"));
        }
        if (descricaoCargoDto.getColHabilidade() == null || descricaoCargoDto.getColHabilidade().isEmpty()) {
            throw new LogicException(this.i18nMessage("solicitacaoCargo.necessarioHabilidade"));
        }
        if (descricaoCargoDto.getColAtitudeIndividual() == null || descricaoCargoDto.getColAtitudeIndividual().isEmpty()) {
            throw new LogicException(this.i18nMessage("solicitacaoCargo.necessarioAtitudeIndividual"));
        }
    }

    @Override
    public IDto restore(final IDto model) throws ServiceException, LogicException {
        final DescricaoCargoDTO descricaoCargoDto = (DescricaoCargoDTO) super.restore(model);
        this.recuperaRelacionamentos(descricaoCargoDto);
        return descricaoCargoDto;
    }

    /**
     * Desenvolvedor: David Rodrigues - Data: 26/11/2013 - Horário: 16:08 - ID Citsmart: 0
     *
     * Motivo/Comentário: Tratamento de NullPointerException
     *
     */

    public void recuperaRelacionamentos(final DescricaoCargoDTO descricaoCargoDto) throws ServiceException, LogicException {
        try {
            final Collection<CargoFormacaoAcademicaDTO> colFormacaoAcademica = new CargoFormacaoAcademicaDao().findByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
            if (colFormacaoAcademica != null) {
                final FormacaoAcademicaDao formacaoAcademicaDao = new FormacaoAcademicaDao();
                for (final CargoFormacaoAcademicaDTO cargoFormacaoAcademicaDto : colFormacaoAcademica) {
                    FormacaoAcademicaDTO formacaoAcademicaDto = new FormacaoAcademicaDTO();
                    formacaoAcademicaDto.setIdFormacaoAcademica(cargoFormacaoAcademicaDto.getIdFormacaoAcademica());
                    formacaoAcademicaDto = (FormacaoAcademicaDTO) formacaoAcademicaDao.restore(formacaoAcademicaDto);
                    if (formacaoAcademicaDto != null) {
                        cargoFormacaoAcademicaDto.setDescricao(formacaoAcademicaDto.getDescricao());
                        cargoFormacaoAcademicaDto.setDetalhe(formacaoAcademicaDto.getDetalhe());
                    }
                }
                descricaoCargoDto.setColFormacaoAcademica(colFormacaoAcademica);
            }

            final Collection<CargoCertificacaoDTO> colCertificacao = new CargoCertificacaoDao().findByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
            if (colCertificacao != null) {
                final CertificacaoDao certificacaoDao = new CertificacaoDao();
                for (final CargoCertificacaoDTO cargoCertificacaoDto : colCertificacao) {
                    CertificacaoDTO certificacaoDto = new CertificacaoDTO();
                    certificacaoDto.setIdCertificacao(cargoCertificacaoDto.getIdCertificacao());
                    certificacaoDto = (CertificacaoDTO) certificacaoDao.restore(certificacaoDto);
                    if (certificacaoDto != null) {
                        cargoCertificacaoDto.setDescricao(certificacaoDto.getDescricao());
                        cargoCertificacaoDto.setDetalhe(certificacaoDto.getDetalhe());
                    }
                }
                descricaoCargoDto.setColCertificacao(colCertificacao);
            }

            final Collection<CargoCursoDTO> colCurso = new CargoCursoDao().findByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
            if (colCurso != null) {
                final CursoDao cursoDao = new CursoDao();
                for (final CargoCursoDTO cargoCursoDto : colCurso) {
                    CursoDTO cursoDto = new CursoDTO();
                    cursoDto.setIdCurso(cargoCursoDto.getIdCurso());
                    cursoDto = (CursoDTO) cursoDao.restore(cursoDto);
                    if (cursoDto != null) {
                        cargoCursoDto.setDescricao(cursoDto.getDescricao());
                        cargoCursoDto.setDetalhe(cursoDto.getDetalhe());
                    }
                }
                descricaoCargoDto.setColCurso(colCurso);
            }

            final Collection<CargoExperienciaInformaticaDTO> colExperienciaInformatica = new CargoExperienciaInformaticaDao().findByIdDescricaoCargo(descricaoCargoDto
                    .getIdDescricaoCargo());
            if (colExperienciaInformatica != null) {
                final ExperienciaInformaticaDao experienciaInformaticaDao = new ExperienciaInformaticaDao();
                for (final CargoExperienciaInformaticaDTO cargoExperienciaInformaticaDto : colExperienciaInformatica) {
                    ExperienciaInformaticaDTO experienciaInformaticaDto = new ExperienciaInformaticaDTO();
                    experienciaInformaticaDto.setIdExperienciaInformatica(cargoExperienciaInformaticaDto.getIdExperienciaInformatica());
                    experienciaInformaticaDto = (ExperienciaInformaticaDTO) experienciaInformaticaDao.restore(experienciaInformaticaDto);
                    if (experienciaInformaticaDto != null) {
                        cargoExperienciaInformaticaDto.setDescricao(experienciaInformaticaDto.getDescricao());
                        cargoExperienciaInformaticaDto.setDetalhe(experienciaInformaticaDto.getDetalhe());
                    }
                }
                descricaoCargoDto.setColExperienciaInformatica(colExperienciaInformatica);
            }

            final Collection<CargoIdiomaDTO> colIdioma = new CargoIdiomaDao().findByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
            if (colIdioma != null) {
                final IdiomaDao idiomaDao = new IdiomaDao();
                for (final CargoIdiomaDTO cargoIdiomaDto : colIdioma) {
                    IdiomaDTO idiomaDto = new IdiomaDTO();
                    idiomaDto.setIdIdioma(cargoIdiomaDto.getIdIdioma());
                    idiomaDto = (IdiomaDTO) idiomaDao.restore(idiomaDto);
                    if (idiomaDto != null) {
                        cargoIdiomaDto.setDescricao(idiomaDto.getDescricao());
                        cargoIdiomaDto.setDetalhe(idiomaDto.getDetalhe());
                    }
                }
                descricaoCargoDto.setColIdioma(colIdioma);
            }

            final Collection<CargoExperienciaAnteriorDTO> colExperienciaAnterior = new CargoExperienciaAnteriorDao()
                    .findByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
            if (colExperienciaAnterior != null) {
                final ConhecimentoDao conhecimentoDao = new ConhecimentoDao();
                for (final CargoExperienciaAnteriorDTO cargoExperienciaAnteriorDto : colExperienciaAnterior) {
                    ConhecimentoDTO conhecimentoDto = new ConhecimentoDTO();
                    conhecimentoDto.setIdConhecimento(cargoExperienciaAnteriorDto.getIdConhecimento());
                    conhecimentoDto = (ConhecimentoDTO) conhecimentoDao.restore(conhecimentoDto);
                    if (conhecimentoDto != null) {
                        cargoExperienciaAnteriorDto.setDescricao(conhecimentoDto.getDescricao());
                        cargoExperienciaAnteriorDto.setDetalhe(conhecimentoDto.getDetalhe());
                    }
                }
                descricaoCargoDto.setColExperienciaAnterior(colExperienciaAnterior);
            }

            final Collection<CargoConhecimentoDTO> colConhecimento = new CargoConhecimentoDao().findByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
            if (colConhecimento != null) {
                final ConhecimentoDao conhecimentoDao = new ConhecimentoDao();
                for (final CargoConhecimentoDTO cargoConhecimentoDto : colConhecimento) {
                    ConhecimentoDTO conhecimentoDto = new ConhecimentoDTO();
                    conhecimentoDto.setIdConhecimento(cargoConhecimentoDto.getIdConhecimento());
                    conhecimentoDto = (ConhecimentoDTO) conhecimentoDao.restore(conhecimentoDto);
                    if (conhecimentoDto != null) {
                        cargoConhecimentoDto.setDescricao(conhecimentoDto.getDescricao());
                        cargoConhecimentoDto.setDetalhe(conhecimentoDto.getDetalhe());
                    }
                }
                descricaoCargoDto.setColConhecimento(colConhecimento);
            }

            final Collection<CargoHabilidadeDTO> colHabilidade = new CargoHabilidadeDao().findByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
            if (colHabilidade != null) {
                final HabilidadeDao habilidadeDao = new HabilidadeDao();
                for (final CargoHabilidadeDTO cargoHabilidadeDto : colHabilidade) {
                    HabilidadeDTO habilidadeDto = new HabilidadeDTO();
                    habilidadeDto.setIdHabilidade(cargoHabilidadeDto.getIdHabilidade());
                    habilidadeDto = (HabilidadeDTO) habilidadeDao.restore(habilidadeDto);
                    if (cargoHabilidadeDto != null) {
                        cargoHabilidadeDto.setDescricao(habilidadeDto.getDescricao());
                        cargoHabilidadeDto.setDetalhe(habilidadeDto.getDetalhe());
                    }
                }
                descricaoCargoDto.setColHabilidade(colHabilidade);
            }

            final Collection<CargoAtitudeIndividualDTO> colAtitudeIndividual = new CargoAtitudeIndividualDao().findByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
            if (colAtitudeIndividual != null) {
                final AtitudeIndividualDao atitudeIndividualDao = new AtitudeIndividualDao();
                for (final CargoAtitudeIndividualDTO cargoAtitudeIndividualDto : colAtitudeIndividual) {
                    AtitudeIndividualDTO atitudeIndividualDto = new AtitudeIndividualDTO();
                    atitudeIndividualDto.setIdAtitudeIndividual(cargoAtitudeIndividualDto.getIdAtitudeIndividual());
                    atitudeIndividualDto = (AtitudeIndividualDTO) atitudeIndividualDao.restore(atitudeIndividualDto);
                    if (atitudeIndividualDto != null) {
                        cargoAtitudeIndividualDto.setDescricao(atitudeIndividualDto.getDescricao());
                        cargoAtitudeIndividualDto.setDetalhe(atitudeIndividualDto.getDetalhe());
                    }
                }
                descricaoCargoDto.setColAtitudeIndividual(colAtitudeIndividual);
            }
        } catch (final Exception e) {
            e.printStackTrace();
            throw new LogicException(e);
        }
    }

    @Override
    public IDto deserializaObjeto(final String serialize) throws Exception {
        DescricaoCargoDTO descricaoCargoDto = null;

        if (serialize != null) {
            descricaoCargoDto = (DescricaoCargoDTO) WebUtil.deserializeObject(DescricaoCargoDTO.class, serialize);
            if (descricaoCargoDto != null && descricaoCargoDto.getSerializeFormacaoAcademica() != null) {
                descricaoCargoDto.setColFormacaoAcademica(WebUtil.deserializeCollectionFromString(CargoFormacaoAcademicaDTO.class,
                        descricaoCargoDto.getSerializeFormacaoAcademica()));
            }
            if (descricaoCargoDto != null && descricaoCargoDto.getSerializeCertificacao() != null) {
                descricaoCargoDto.setColCertificacao(WebUtil.deserializeCollectionFromString(CargoCertificacaoDTO.class, descricaoCargoDto.getSerializeCertificacao()));
            }
            if (descricaoCargoDto != null && descricaoCargoDto.getSerializeCurso() != null) {
                descricaoCargoDto.setColCurso(WebUtil.deserializeCollectionFromString(CargoCursoDTO.class, descricaoCargoDto.getSerializeCurso()));
            }
            if (descricaoCargoDto != null && descricaoCargoDto.getSerializeExperienciaInformatica() != null) {
                descricaoCargoDto.setColExperienciaInformatica(WebUtil.deserializeCollectionFromString(CargoExperienciaInformaticaDTO.class,
                        descricaoCargoDto.getSerializeExperienciaInformatica()));
            }
            if (descricaoCargoDto != null && descricaoCargoDto.getSerializeIdioma() != null) {
                descricaoCargoDto.setColIdioma(WebUtil.deserializeCollectionFromString(CargoIdiomaDTO.class, descricaoCargoDto.getSerializeIdioma()));
            }
            if (descricaoCargoDto != null && descricaoCargoDto.getSerializeExperienciaAnterior() != null) {
                descricaoCargoDto.setColExperienciaAnterior(WebUtil.deserializeCollectionFromString(CargoExperienciaAnteriorDTO.class,
                        descricaoCargoDto.getSerializeExperienciaAnterior()));
            }
            if (descricaoCargoDto != null && descricaoCargoDto.getSerializeHabilidade() != null) {
                descricaoCargoDto.setColHabilidade(WebUtil.deserializeCollectionFromString(CargoHabilidadeDTO.class, descricaoCargoDto.getSerializeHabilidade()));
            }
            if (descricaoCargoDto != null && descricaoCargoDto.getSerializeAtitudeIndividual() != null) {
                descricaoCargoDto.setColAtitudeIndividual(WebUtil.deserializeCollectionFromString(CargoAtitudeIndividualDTO.class,
                        descricaoCargoDto.getSerializeAtitudeIndividual()));
            }
            if (descricaoCargoDto != null && descricaoCargoDto.getSerializeConhecimento() != null) {
                descricaoCargoDto.setColConhecimento(WebUtil.deserializeCollectionFromString(CargoConhecimentoDTO.class, descricaoCargoDto.getSerializeConhecimento()));
            }
        }

        return descricaoCargoDto;
    }

    @Override
    public DescricaoCargoDTO findByIdSolicitacaoServico(final Integer parm) throws Exception {
        final DescricaoCargoDTO descricaoCargoDto = this.getDao().findByIdSolicitacaoServico(parm);
        if (descricaoCargoDto != null) {
            this.recuperaRelacionamentos(descricaoCargoDto);
        }
        return descricaoCargoDto;
    }

    @Override
    public void validaCreate(final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {
        this.validaCreate(model);
        final DescricaoCargoDTO descricaoCargoDto = (DescricaoCargoDTO) model;
        this.validaAtualizacao(solicitacaoServicoDto, descricaoCargoDto);
    }

    private void validaAtualizacao(final SolicitacaoServicoDTO solicitacaoServicoDto, final DescricaoCargoDTO descricaoCargoDto) throws Exception {
        descricaoCargoDto.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
        if (UtilStrings.nullToVazio(descricaoCargoDto.getAcao()).equalsIgnoreCase(RequisicaoPessoalDTO.ACAO_ANALISE)) {
            if (descricaoCargoDto.getSituacao() == null || descricaoCargoDto.getSituacao().trim().length() == 0) {
                throw new LogicException("Parecer não informado");
            }
            if (descricaoCargoDto.getSituacao().equalsIgnoreCase("X") && descricaoCargoDto.getIdJustificativaValidacao() == null) {
                throw new LogicException("Justificativa não informada");
            }
        }
    }

    @Override
    public void validaDelete(final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void validaUpdate(final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {
        final DescricaoCargoDTO descricaoCargoDto = (DescricaoCargoDTO) model;
        this.validaAtualizacao(solicitacaoServicoDto, descricaoCargoDto);
    }

    @Override
    public IDto create(final TransactionControler tc, final SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {
        this.validaCreate(solicitacaoServicoDto, model);

        // Instancia Objeto controlador de transacao
        final DescricaoCargoDao descricaoCargoDao = new DescricaoCargoDao();
        final CargoFormacaoAcademicaDao cargoFormacaoAcademicaDao = new CargoFormacaoAcademicaDao();
        final CargoCertificacaoDao cargoCertificacaoDao = new CargoCertificacaoDao();
        final CargoCursoDao cargoCursoDao = new CargoCursoDao();
        final CargoExperienciaInformaticaDao cargoExperienciaInformaticaDao = new CargoExperienciaInformaticaDao();
        final CargoIdiomaDao cargoIdiomaDao = new CargoIdiomaDao();
        final CargoExperienciaAnteriorDao cargoExperienciaAnteriorDao = new CargoExperienciaAnteriorDao();
        final CargoConhecimentoDao cargoConhecimentoDao = new CargoConhecimentoDao();
        final CargoHabilidadeDao cargoHabilidadeDao = new CargoHabilidadeDao();
        final CargoAtitudeIndividualDao cargoAtitudeIndividualDao = new CargoAtitudeIndividualDao();

        // Seta o TransactionController para os DAOs
        descricaoCargoDao.setTransactionControler(tc);
        cargoFormacaoAcademicaDao.setTransactionControler(tc);
        cargoCertificacaoDao.setTransactionControler(tc);
        cargoCursoDao.setTransactionControler(tc);
        cargoExperienciaInformaticaDao.setTransactionControler(tc);
        cargoIdiomaDao.setTransactionControler(tc);
        cargoExperienciaAnteriorDao.setTransactionControler(tc);
        cargoConhecimentoDao.setTransactionControler(tc);
        cargoHabilidadeDao.setTransactionControler(tc);
        cargoAtitudeIndividualDao.setTransactionControler(tc);

        // Executa operacoes pertinentes ao negocio.
        model = descricaoCargoDao.create(model);
        final DescricaoCargoDTO descricaoCargoDto = (DescricaoCargoDTO) model;

        if (descricaoCargoDto.getColFormacaoAcademica() != null) {
            for (final CargoFormacaoAcademicaDTO cargoFormacaoAcademicaDto : descricaoCargoDto.getColFormacaoAcademica()) {
                cargoFormacaoAcademicaDto.setIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
                cargoFormacaoAcademicaDao.create(cargoFormacaoAcademicaDto);
            }
        }

        if (descricaoCargoDto.getColCertificacao() != null) {
            for (final CargoCertificacaoDTO cargoCertificacaoDto : descricaoCargoDto.getColCertificacao()) {
                cargoCertificacaoDto.setIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
                cargoCertificacaoDao.create(cargoCertificacaoDto);
            }
        }

        if (descricaoCargoDto.getColCurso() != null) {
            for (final CargoCursoDTO cargoCursoDto : descricaoCargoDto.getColCurso()) {
                cargoCursoDto.setIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
                cargoCursoDao.create(cargoCursoDto);
            }
        }

        if (descricaoCargoDto.getColExperienciaInformatica() != null) {
            for (final CargoExperienciaInformaticaDTO cargoExperienciaInformaticaDto : descricaoCargoDto.getColExperienciaInformatica()) {
                cargoExperienciaInformaticaDto.setIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
                cargoExperienciaInformaticaDao.create(cargoExperienciaInformaticaDto);
            }
        }

        if (descricaoCargoDto.getColIdioma() != null) {
            for (final CargoIdiomaDTO cargoIdiomaDto : descricaoCargoDto.getColIdioma()) {
                cargoIdiomaDto.setIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
                cargoIdiomaDao.create(cargoIdiomaDto);
            }
        }

        if (descricaoCargoDto.getColExperienciaAnterior() != null) {
            for (final CargoExperienciaAnteriorDTO cargoExperienciaAnteriorDto : descricaoCargoDto.getColExperienciaAnterior()) {
                cargoExperienciaAnteriorDto.setIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
                cargoExperienciaAnteriorDao.create(cargoExperienciaAnteriorDto);
            }
        }

        if (descricaoCargoDto.getColConhecimento() != null) {
            for (final CargoConhecimentoDTO cargoConhecimentoDto : descricaoCargoDto.getColConhecimento()) {
                cargoConhecimentoDto.setIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
                cargoConhecimentoDao.create(cargoConhecimentoDto);
            }
        }

        if (descricaoCargoDto.getColHabilidade() != null) {
            for (final CargoHabilidadeDTO cargoHabilidadeDto : descricaoCargoDto.getColHabilidade()) {
                cargoHabilidadeDto.setIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
                cargoHabilidadeDao.create(cargoHabilidadeDto);
            }
        }

        if (descricaoCargoDto.getColAtitudeIndividual() != null) {
            for (final CargoAtitudeIndividualDTO cargoAtitudeIndividualDto : descricaoCargoDto.getColAtitudeIndividual()) {
                cargoAtitudeIndividualDto.setIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
                cargoAtitudeIndividualDao.create(cargoAtitudeIndividualDto);
            }
        }

        return model;
    }

    @Override
    public void update(final TransactionControler tc, final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {
        this.validaUpdate(solicitacaoServicoDto, model);

        // Instancia Objeto controlador de transacao
        final DescricaoCargoDao descricaoCargoDao = new DescricaoCargoDao();
        final CargoFormacaoAcademicaDao cargoFormacaoAcademicaDao = new CargoFormacaoAcademicaDao();
        final CargoCertificacaoDao cargoCertificacaoDao = new CargoCertificacaoDao();
        final CargoCursoDao cargoCursoDao = new CargoCursoDao();
        final CargoExperienciaInformaticaDao cargoExperienciaInformaticaDao = new CargoExperienciaInformaticaDao();
        final CargoIdiomaDao cargoIdiomaDao = new CargoIdiomaDao();
        final CargoExperienciaAnteriorDao cargoExperienciaAnteriorDao = new CargoExperienciaAnteriorDao();
        final CargoConhecimentoDao cargoConhecimentoDao = new CargoConhecimentoDao();
        final CargoHabilidadeDao cargoHabilidadeDao = new CargoHabilidadeDao();
        final CargoAtitudeIndividualDao cargoAtitudeIndividualDao = new CargoAtitudeIndividualDao();
        final CargosDao cargosDao = new CargosDao();

        // Seta o TransactionController para os DAOs
        descricaoCargoDao.setTransactionControler(tc);
        cargoFormacaoAcademicaDao.setTransactionControler(tc);
        cargoCertificacaoDao.setTransactionControler(tc);
        cargoCursoDao.setTransactionControler(tc);
        cargoExperienciaInformaticaDao.setTransactionControler(tc);
        cargoIdiomaDao.setTransactionControler(tc);
        cargoExperienciaAnteriorDao.setTransactionControler(tc);
        cargoConhecimentoDao.setTransactionControler(tc);
        cargoHabilidadeDao.setTransactionControler(tc);
        cargoAtitudeIndividualDao.setTransactionControler(tc);
        cargosDao.setTransactionControler(tc);

        // Executa operacoes pertinentes ao negocio.
        descricaoCargoDao.update(model);
        final DescricaoCargoDTO descricaoCargoDto = (DescricaoCargoDTO) model;

        if (descricaoCargoDto.getAcao().equals(RequisicaoPessoalDTO.ACAO_ANALISE)) {
            String aprovado = "N";
            if (descricaoCargoDto.getSituacao().equalsIgnoreCase("A")) {
                aprovado = "S";
            }
            final ParecerServiceEjb parecerService = new ParecerServiceEjb();
            final ParecerDTO parecerDto = parecerService.createOrUpdate(tc, descricaoCargoDto.getIdParecerValidacao(), solicitacaoServicoDto.getUsuarioDto(),
                    descricaoCargoDto.getIdJustificativaValidacao(), descricaoCargoDto.getComplemJustificativaValidacao(), aprovado);
            descricaoCargoDto.setIdParecerValidacao(parecerDto.getIdParecer());
            if (descricaoCargoDto.getSituacao().equalsIgnoreCase("A")) {
                final CargosDTO cargoDto = new CargosDTO();
                cargoDto.setNomeCargo(descricaoCargoDto.getNomeCargo());
                final CargosService cargosService = (CargosService) ServiceLocator.getInstance().getService(CargosService.class, null);
                final Collection<CargosDTO> col = cargosService.seCargoJaCadastrado(cargoDto);
                if (col == null || col.size() <= 0) {
                    cargoDto.setDataInicio(UtilDatas.getDataAtual());
                    cargoDto.setIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
                    cargosDao.create(cargoDto);
                }
            }
        } else if (descricaoCargoDto.getAcao().equals(RequisicaoPessoalDTO.ACAO_CRIACAO)) {
            cargoFormacaoAcademicaDao.deleteByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
            if (descricaoCargoDto.getColFormacaoAcademica() != null) {
                for (final CargoFormacaoAcademicaDTO cargoFormacaoAcademicaDto : descricaoCargoDto.getColFormacaoAcademica()) {
                    cargoFormacaoAcademicaDto.setIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
                    cargoFormacaoAcademicaDao.create(cargoFormacaoAcademicaDto);
                }
            }

            cargoCertificacaoDao.deleteByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
            if (descricaoCargoDto.getColCertificacao() != null) {
                for (final CargoCertificacaoDTO cargoCertificacaoDto : descricaoCargoDto.getColCertificacao()) {
                    cargoCertificacaoDto.setIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
                    cargoCertificacaoDao.create(cargoCertificacaoDto);
                }
            }

            cargoCursoDao.deleteByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
            if (descricaoCargoDto.getColCurso() != null) {
                for (final CargoCursoDTO cargoCursoDto : descricaoCargoDto.getColCurso()) {
                    cargoCursoDto.setIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
                    cargoCursoDao.create(cargoCursoDto);
                }
            }

            cargoExperienciaInformaticaDao.deleteByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
            if (descricaoCargoDto.getColExperienciaInformatica() != null) {
                for (final CargoExperienciaInformaticaDTO cargoExperienciaInformaticaDto : descricaoCargoDto.getColExperienciaInformatica()) {
                    cargoExperienciaInformaticaDto.setIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
                    cargoExperienciaInformaticaDao.create(cargoExperienciaInformaticaDto);
                }
            }

            cargoIdiomaDao.deleteByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
            if (descricaoCargoDto.getColIdioma() != null) {
                for (final CargoIdiomaDTO cargoIdiomaDto : descricaoCargoDto.getColIdioma()) {
                    cargoIdiomaDto.setIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
                    cargoIdiomaDao.create(cargoIdiomaDto);
                }
            }

            cargoExperienciaAnteriorDao.deleteByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
            if (descricaoCargoDto.getColExperienciaAnterior() != null) {
                for (final CargoExperienciaAnteriorDTO cargoExperienciaAnteriorDto : descricaoCargoDto.getColExperienciaAnterior()) {
                    cargoExperienciaAnteriorDto.setIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
                    cargoExperienciaAnteriorDao.create(cargoExperienciaAnteriorDto);
                }
            }

            cargoConhecimentoDao.deleteByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
            if (descricaoCargoDto.getColConhecimento() != null) {
                for (final CargoConhecimentoDTO cargoConhecimentoDto : descricaoCargoDto.getColConhecimento()) {
                    cargoConhecimentoDto.setIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
                    cargoConhecimentoDao.create(cargoConhecimentoDto);
                }
            }

            cargoHabilidadeDao.deleteByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
            if (descricaoCargoDto.getColHabilidade() != null) {
                for (final CargoHabilidadeDTO cargoHabilidadeDto : descricaoCargoDto.getColHabilidade()) {
                    cargoHabilidadeDto.setIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
                    cargoHabilidadeDao.create(cargoHabilidadeDto);
                }
            }

            cargoAtitudeIndividualDao.deleteByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
            if (descricaoCargoDto.getColAtitudeIndividual() != null) {
                for (final CargoAtitudeIndividualDTO cargoAtitudeIndividualDto : descricaoCargoDto.getColAtitudeIndividual()) {
                    cargoAtitudeIndividualDto.setIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
                    cargoAtitudeIndividualDao.create(cargoAtitudeIndividualDto);
                }
            }
        }

    }

    @Override
    public void delete(final TransactionControler tc, final SolicitacaoServicoDTO solicitacaoServicoDto, final IDto model) throws Exception {

        final DescricaoCargoDao descricaoCargoDao = new DescricaoCargoDao();
        final CargoFormacaoAcademicaDao cargoFormacaoAcademicaDao = new CargoFormacaoAcademicaDao();
        final CargoCertificacaoDao cargoCertificacaoDao = new CargoCertificacaoDao();
        final CargoCursoDao cargoCursoDao = new CargoCursoDao();
        final CargoExperienciaInformaticaDao cargoExperienciaInformaticaDao = new CargoExperienciaInformaticaDao();
        final CargoIdiomaDao cargoIdiomaDao = new CargoIdiomaDao();
        final CargoExperienciaAnteriorDao cargoExperienciaAnteriorDao = new CargoExperienciaAnteriorDao();
        final CargoConhecimentoDao cargoConhecimentoDao = new CargoConhecimentoDao();
        final CargoHabilidadeDao cargoHabilidadeDao = new CargoHabilidadeDao();
        final CargoAtitudeIndividualDao cargoAtitudeIndividualDao = new CargoAtitudeIndividualDao();

        // Faz validacao, caso exista.
        this.validaDelete(solicitacaoServicoDto, model);

        // Instancia ou obtem os DAOs necessarios.

        // Seta o TransactionController para os DAOs
        descricaoCargoDao.setTransactionControler(tc);
        cargoFormacaoAcademicaDao.setTransactionControler(tc);
        cargoCertificacaoDao.setTransactionControler(tc);
        cargoCursoDao.setTransactionControler(tc);
        cargoExperienciaInformaticaDao.setTransactionControler(tc);
        cargoIdiomaDao.setTransactionControler(tc);
        cargoExperienciaAnteriorDao.setTransactionControler(tc);
        cargoConhecimentoDao.setTransactionControler(tc);
        cargoHabilidadeDao.setTransactionControler(tc);
        cargoAtitudeIndividualDao.setTransactionControler(tc);

        final DescricaoCargoDTO descricaoCargoDto = (DescricaoCargoDTO) model;

        cargoFormacaoAcademicaDao.deleteByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
        cargoCertificacaoDao.deleteByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
        cargoCursoDao.deleteByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
        cargoExperienciaInformaticaDao.deleteByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
        cargoIdiomaDao.deleteByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
        cargoExperienciaAnteriorDao.deleteByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
        cargoConhecimentoDao.deleteByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
        cargoHabilidadeDao.deleteByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
        cargoAtitudeIndividualDao.deleteByIdDescricaoCargo(descricaoCargoDto.getIdDescricaoCargo());
        descricaoCargoDao.delete(descricaoCargoDto);

    }

}
