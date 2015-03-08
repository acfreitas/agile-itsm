package br.com.centralit.citcorpore.rh.negocio;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.AtitudeIndividualDTO;
import br.com.centralit.citcorpore.rh.bean.AtribuicaoResponsabilidadeDTO;
import br.com.centralit.citcorpore.rh.bean.CertificacaoDTO;
import br.com.centralit.citcorpore.rh.bean.CursoDTO;
import br.com.centralit.citcorpore.rh.bean.HistAtribuicaoResponsabilidadeDTO;
import br.com.centralit.citcorpore.rh.bean.HistManualCertificacaoDTO;
import br.com.centralit.citcorpore.rh.bean.HistManualCompetenciaTecnicaDTO;
import br.com.centralit.citcorpore.rh.bean.HistManualCursoDTO;
import br.com.centralit.citcorpore.rh.bean.HistManualFuncaoDTO;
import br.com.centralit.citcorpore.rh.bean.HistPerspectivaComportamentalDTO;
import br.com.centralit.citcorpore.rh.bean.ManualCertificacaoDto;
import br.com.centralit.citcorpore.rh.bean.ManualCompetenciaTecnicaDTO;
import br.com.centralit.citcorpore.rh.bean.ManualCursoDTO;
import br.com.centralit.citcorpore.rh.bean.ManualFuncaoCompetenciaDTO;
import br.com.centralit.citcorpore.rh.bean.ManualFuncaoDTO;
import br.com.centralit.citcorpore.rh.bean.PerspectivaComportamentalDTO;
import br.com.centralit.citcorpore.rh.integracao.AtribuicaoResponsabilidadeDao;
import br.com.centralit.citcorpore.rh.integracao.HistAtribuicaoResponsabilidadeDao;
import br.com.centralit.citcorpore.rh.integracao.HistManualCertificacaoDao;
import br.com.centralit.citcorpore.rh.integracao.HistManualCompetenciaTecnicaDao;
import br.com.centralit.citcorpore.rh.integracao.HistManualCursoDao;
import br.com.centralit.citcorpore.rh.integracao.HistManualFuncaoDao;
import br.com.centralit.citcorpore.rh.integracao.HistPerspectivaComportamentalDao;
import br.com.centralit.citcorpore.rh.integracao.ManualCertificacaoDao;
import br.com.centralit.citcorpore.rh.integracao.ManualCompetenciaTecnicaDao;
import br.com.centralit.citcorpore.rh.integracao.ManualCursoDao;
import br.com.centralit.citcorpore.rh.integracao.ManualFuncaoCompetenciaDao;
import br.com.centralit.citcorpore.rh.integracao.ManualFuncaoDao;
import br.com.centralit.citcorpore.rh.integracao.PerspectivaComportamentalDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.Reflexao;
import br.com.citframework.util.UtilDatas;

public class ManualFuncaoServiceEjb extends CrudServiceImpl implements ManualFuncaoService {

    private ManualFuncaoDao dao;

    @Override
    protected ManualFuncaoDao getDao() {
        if (dao == null) {
            dao = new ManualFuncaoDao();
        }
        return dao;
    }

    @Override
    public IDto create(IDto model) throws ServiceException, LogicException {

        // Instancia Objeto controlador de transacao

        final CrudDAO crudDao = this.getDao();

        final ManualFuncaoDao manualFuncaoDao = new ManualFuncaoDao();
        final AtribuicaoResponsabilidadeDao atribuicaoResponsabilidadeDao = new AtribuicaoResponsabilidadeDao();
        final ManualCertificacaoDao certificacaoDao = new ManualCertificacaoDao();
        final ManualCursoDao cursoDao = new ManualCursoDao();
        final ManualCompetenciaTecnicaDao competenciaTecnicaDao = new ManualCompetenciaTecnicaDao();
        final PerspectivaComportamentalDao perspectivaTecnicaDao = new PerspectivaComportamentalDao();

        final TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());

        final ManualFuncaoDTO manualFuncaoDto = (ManualFuncaoDTO) model;
        try {

            // Faz validacao, caso exista.

            this.validaCreate(model);

            // Seta o TransactionController para os DAOs

            crudDao.setTransactionControler(tc);
            manualFuncaoDao.setTransactionControler(tc);
            atribuicaoResponsabilidadeDao.setTransactionControler(tc);
            certificacaoDao.setTransactionControler(tc);
            cursoDao.setTransactionControler(tc);
            competenciaTecnicaDao.setTransactionControler(tc);
            perspectivaTecnicaDao.setTransactionControler(tc);

            // Inicia transacao
            tc.start();
            // Executa operacoes pertinentes ao negocio.

            model = crudDao.create(manualFuncaoDto);
            if (manualFuncaoDto.getColAtribuicaoResponsabilidadeDTO() != null) {
                for (final AtribuicaoResponsabilidadeDTO atribuicaoResponsabilidadeDTO2 : manualFuncaoDto.getColAtribuicaoResponsabilidadeDTO()) {
                    final AtribuicaoResponsabilidadeDTO atribuicaoResponsabilidadeDto = atribuicaoResponsabilidadeDTO2;
                    atribuicaoResponsabilidadeDto.setIdManualFuncao(manualFuncaoDto.getIdManualFuncao());
                    atribuicaoResponsabilidadeDao.create(atribuicaoResponsabilidadeDto);
                }

            }

            if (manualFuncaoDto.getColCertificacaoDTORA() != null) {
                for (final Object element : manualFuncaoDto.getColCertificacaoDTORA()) {
                    final CertificacaoDTO certificadoDto = (CertificacaoDTO) element;

                    final ManualCertificacaoDto manualCertificado = new ManualCertificacaoDto();
                    manualCertificado.setDescricao(certificadoDto.getDescricao());
                    manualCertificado.setDetalhe(certificadoDto.getDetalhe());
                    manualCertificado.setRAouRF("RA");
                    manualCertificado.setIdManualFuncao(manualFuncaoDto.getIdManualFuncao());
                    certificacaoDao.create(manualCertificado);
                }
            }

            if (manualFuncaoDto.getColCursoDTORA() != null) {
                for (final Object element : manualFuncaoDto.getColCursoDTORA()) {
                    final CursoDTO cursoDto = (CursoDTO) element;

                    final ManualCursoDTO manualCursoDto = new ManualCursoDTO();
                    manualCursoDto.setDescricao(cursoDto.getDescricao());
                    manualCursoDto.setDetalhe(cursoDto.getDetalhe());
                    manualCursoDto.setRAouRF("RA");
                    manualCursoDto.setIdManualFuncao(manualFuncaoDto.getIdManualFuncao());
                    cursoDao.create(manualCursoDto);
                }
            }

            if (manualFuncaoDto.getColCertificacaoDTORF() != null) {
                for (final Object element : manualFuncaoDto.getColCertificacaoDTORF()) {
                    final CertificacaoDTO certificadoDto = (CertificacaoDTO) element;

                    final ManualCertificacaoDto manualCertificado = new ManualCertificacaoDto();
                    manualCertificado.setDescricao(certificadoDto.getDescricao());
                    manualCertificado.setDetalhe(certificadoDto.getDetalhe());
                    manualCertificado.setRAouRF("RF");
                    manualCertificado.setIdManualFuncao(manualFuncaoDto.getIdManualFuncao());
                    certificacaoDao.create(manualCertificado);
                }
            }

            if (manualFuncaoDto.getColCursoDTORF() != null) {
                for (final Object element : manualFuncaoDto.getColCursoDTORF()) {
                    final CursoDTO cursoDto = (CursoDTO) element;

                    final ManualCursoDTO manualCursoDto = new ManualCursoDTO();
                    manualCursoDto.setDescricao(cursoDto.getDescricao());
                    manualCursoDto.setDetalhe(cursoDto.getDetalhe());
                    manualCursoDto.setRAouRF("RF");
                    manualCursoDto.setIdManualFuncao(manualFuncaoDto.getIdManualFuncao());
                    cursoDao.create(manualCursoDto);
                }
            }

            if (manualFuncaoDto.getColCompetenciaTecnicaDTO() != null) {
                for (final ManualCompetenciaTecnicaDTO manualCompetenciaDto : manualFuncaoDto.getColCompetenciaTecnicaDTO()) {
                    manualCompetenciaDto.setIdManualFuncao(manualFuncaoDto.getIdManualFuncao());
                    competenciaTecnicaDao.create(manualCompetenciaDto);
                }
            }

            if (manualFuncaoDto.getColPerspectivaComportamentalDTO() != null) {
                for (final Object element : manualFuncaoDto.getColPerspectivaComportamentalDTO()) {
                    final AtitudeIndividualDTO atitudeIndividual = (AtitudeIndividualDTO) element;

                    final PerspectivaComportamentalDTO perspectivaCompDto = new PerspectivaComportamentalDTO();
                    perspectivaCompDto.setComportamento(atitudeIndividual.getComportamento());
                    perspectivaCompDto.setCmbCompetenciaComportamental(atitudeIndividual.getDescricaoCmbCompetenciaComportamental());
                    perspectivaCompDto.setIdManualFuncao(manualFuncaoDto.getIdManualFuncao());
                    perspectivaTecnicaDao.create(perspectivaCompDto);
                }
            }

            // Faz commit e fecha a transacao.

            tc.commit();
            tc.close();
            return model;

        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
        return model;

    }

    @Override
    public void update(final IDto model) throws ServiceException, LogicException {
        // Instancia Objeto controlador de transacao

        final CrudDAO crudDao = this.getDao();

        final ManualFuncaoDao manualFuncaoDao = new ManualFuncaoDao();
        final AtribuicaoResponsabilidadeDao atribuicaoResponsabilidadeDao = new AtribuicaoResponsabilidadeDao();
        final ManualCertificacaoDao certificacaoDao = new ManualCertificacaoDao();
        final ManualCursoDao cursoDao = new ManualCursoDao();
        final ManualCompetenciaTecnicaDao competenciaTecnicaDao = new ManualCompetenciaTecnicaDao();
        final PerspectivaComportamentalDao perspectivaTecnicaDao = new PerspectivaComportamentalDao();

        final TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());

        final ManualFuncaoDTO manualFuncaoDto = (ManualFuncaoDTO) model;
        try {

            // Faz validacao, caso exista.

            this.validaUpdate(manualFuncaoDto);

            // Seta o TransactionController para os DAOs

            crudDao.setTransactionControler(tc);
            manualFuncaoDao.setTransactionControler(tc);
            atribuicaoResponsabilidadeDao.setTransactionControler(tc);
            certificacaoDao.setTransactionControler(tc);
            cursoDao.setTransactionControler(tc);
            competenciaTecnicaDao.setTransactionControler(tc);
            perspectivaTecnicaDao.setTransactionControler(tc);

            // Inicia transacao
            tc.start();
            // Executa operacoes pertinentes ao negocio.

            atribuicaoResponsabilidadeDao.deleteByIdManualFuncao(manualFuncaoDto.getIdManualFuncao());
            if (manualFuncaoDto.getColAtribuicaoResponsabilidadeDTO() != null) {
                for (final AtribuicaoResponsabilidadeDTO atribuicaoResponsabilidadeDTO2 : manualFuncaoDto.getColAtribuicaoResponsabilidadeDTO()) {
                    final AtribuicaoResponsabilidadeDTO atribuicaoResponsabilidadeDto = atribuicaoResponsabilidadeDTO2;
                    atribuicaoResponsabilidadeDto.setIdManualFuncao(manualFuncaoDto.getIdManualFuncao());
                    atribuicaoResponsabilidadeDao.create(atribuicaoResponsabilidadeDto);
                }

            }

            certificacaoDao.deleteByIdManualFuncaoRA(manualFuncaoDto.getIdManualFuncao());
            if (manualFuncaoDto.getColCertificacaoDTORA() != null) {
                for (final Object element : manualFuncaoDto.getColCertificacaoDTORA()) {
                    final CertificacaoDTO certificadoDto = (CertificacaoDTO) element;

                    final ManualCertificacaoDto manualCertificado = new ManualCertificacaoDto();
                    manualCertificado.setDescricao(certificadoDto.getDescricao());
                    manualCertificado.setDetalhe(certificadoDto.getDetalhe());
                    manualCertificado.setRAouRF("RA");
                    manualCertificado.setIdManualFuncao(manualFuncaoDto.getIdManualFuncao());
                    certificacaoDao.create(manualCertificado);
                }
            }

            cursoDao.deleteByIdManualFuncaoRA(manualFuncaoDto.getIdManualFuncao());
            if (manualFuncaoDto.getColCursoDTORA() != null) {
                for (final Object element : manualFuncaoDto.getColCursoDTORA()) {
                    final CursoDTO cursoDto = (CursoDTO) element;

                    final ManualCursoDTO manualCursoDto = new ManualCursoDTO();
                    manualCursoDto.setDescricao(cursoDto.getDescricao());
                    manualCursoDto.setDetalhe(cursoDto.getDetalhe());
                    manualCursoDto.setRAouRF("RA");
                    manualCursoDto.setIdManualFuncao(manualFuncaoDto.getIdManualFuncao());
                    cursoDao.create(manualCursoDto);
                }
            }

            certificacaoDao.deleteByIdManualFuncaoRF(manualFuncaoDto.getIdManualFuncao());
            if (manualFuncaoDto.getColCertificacaoDTORF() != null) {
                for (final Object element : manualFuncaoDto.getColCertificacaoDTORF()) {
                    final CertificacaoDTO certificadoDto = (CertificacaoDTO) element;

                    final ManualCertificacaoDto manualCertificado = new ManualCertificacaoDto();
                    manualCertificado.setDescricao(certificadoDto.getDescricao());
                    manualCertificado.setDetalhe(certificadoDto.getDetalhe());
                    manualCertificado.setRAouRF("RF");
                    manualCertificado.setIdManualFuncao(manualFuncaoDto.getIdManualFuncao());
                    certificacaoDao.create(manualCertificado);
                }
            }

            cursoDao.deleteByIdManualFuncaoRF(manualFuncaoDto.getIdManualFuncao());
            if (manualFuncaoDto.getColCursoDTORF() != null) {
                for (final Object element : manualFuncaoDto.getColCursoDTORF()) {
                    final CursoDTO cursoDto = (CursoDTO) element;

                    final ManualCursoDTO manualCursoDto = new ManualCursoDTO();
                    manualCursoDto.setDescricao(cursoDto.getDescricao());
                    manualCursoDto.setDetalhe(cursoDto.getDetalhe());
                    manualCursoDto.setRAouRF("RF");
                    manualCursoDto.setIdManualFuncao(manualFuncaoDto.getIdManualFuncao());
                    cursoDao.create(manualCursoDto);
                }
            }

            competenciaTecnicaDao.deleteByIdManualFuncao(manualFuncaoDto.getIdManualFuncao());
            if (manualFuncaoDto.getColCompetenciaTecnicaDTO() != null) {
                for (final ManualCompetenciaTecnicaDTO manualCompetenciaDto : manualFuncaoDto.getColCompetenciaTecnicaDTO()) {
                    manualCompetenciaDto.setIdManualFuncao(manualFuncaoDto.getIdManualFuncao());
                    competenciaTecnicaDao.create(manualCompetenciaDto);
                }
            }

            perspectivaTecnicaDao.deleteByIdManualFuncao(manualFuncaoDto.getIdManualFuncao());
            if (manualFuncaoDto.getColPerspectivaComportamentalDTO() != null) {
                for (final Object element : manualFuncaoDto.getColPerspectivaComportamentalDTO()) {
                    final AtitudeIndividualDTO atitudeIndividual = (AtitudeIndividualDTO) element;

                    final PerspectivaComportamentalDTO perspectivaCompDto = new PerspectivaComportamentalDTO();
                    perspectivaCompDto.setComportamento(atitudeIndividual.getComportamento());
                    perspectivaCompDto.setCmbCompetenciaComportamental(atitudeIndividual.getDescricaoCmbCompetenciaComportamental());
                    perspectivaCompDto.setIdManualFuncao(manualFuncaoDto.getIdManualFuncao());
                    perspectivaTecnicaDao.create(perspectivaCompDto);
                }
            }

            crudDao.update(manualFuncaoDto);

            // Faz commit e fecha a transacao.

            tc.commit();
            tc.close();

        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
    }

    @Override
    public IDto restore(final IDto model) throws ServiceException, LogicException {
        final ManualFuncaoDTO manualDto = (ManualFuncaoDTO) super.restore(model);

        new ManualFuncaoDao();
        final AtribuicaoResponsabilidadeDao atribuicaoResponsabilidadeDao = new AtribuicaoResponsabilidadeDao();
        final ManualCertificacaoDao certificacaoDao = new ManualCertificacaoDao();
        final ManualCursoDao cursoDao = new ManualCursoDao();
        final ManualCompetenciaTecnicaDao competenciaTecnicaDao = new ManualCompetenciaTecnicaDao();
        final PerspectivaComportamentalDao perspectivaCompDao = new PerspectivaComportamentalDao();

        // tratamento para atribuicaoResposabilidade
        Collection<AtribuicaoResponsabilidadeDTO> colResponsabilidades = null;
        try {
            colResponsabilidades = atribuicaoResponsabilidadeDao.findByIdManualFuncao(manualDto.getIdManualFuncao());
        } catch (final Exception e) {
            e.printStackTrace();
        }
        manualDto.setColAtribuicaoResponsabilidadeDTO(colResponsabilidades);

        // tratamento para certificados
        Collection<ManualCertificacaoDto> colCertificados = null;
        try {
            colCertificados = certificacaoDao.findByIdManualFuncao(manualDto.getIdManualFuncao());
        } catch (final Exception e) {
            e.printStackTrace();
        }
        final List<CertificacaoDTO> certificadosRA = new ArrayList<>();
        final List<CertificacaoDTO> certificadosRF = new ArrayList<>();
        if (colCertificados != null) {
            for (final ManualCertificacaoDto manualCertificacaoDto : colCertificados) {
                if (manualCertificacaoDto.getRAouRF().equalsIgnoreCase("RA")) {
                    final CertificacaoDTO cert = new CertificacaoDTO();

                    cert.setDescricao(manualCertificacaoDto.getDescricao());
                    cert.setDetalhe(manualCertificacaoDto.getDetalhe());

                    certificadosRA.add(cert);
                } else {
                    final CertificacaoDTO cert = new CertificacaoDTO();

                    cert.setDescricao(manualCertificacaoDto.getDescricao());
                    cert.setDetalhe(manualCertificacaoDto.getDetalhe());

                    certificadosRF.add(cert);
                }
            }
        }
        manualDto.setColCertificacaoDTORA(certificadosRA);
        manualDto.setColCertificacaoDTORF(certificadosRF);

        // tratamento para Cursos
        Collection<ManualCursoDTO> colCursos = null;
        try {
            colCursos = cursoDao.findByIdManualFuncao(manualDto.getIdManualFuncao());
        } catch (final Exception e) {
            e.printStackTrace();
        }
        final List<CursoDTO> cursosRA = new ArrayList<>();
        final List<CursoDTO> cursosRF = new ArrayList<>();
        if (colCursos != null) {
            for (final ManualCursoDTO manualCurso : colCursos) {
                if (manualCurso.getRAouRF().equalsIgnoreCase("RA")) {
                    final CursoDTO curso = new CursoDTO();

                    curso.setDescricao(manualCurso.getDescricao());
                    curso.setDetalhe(manualCurso.getDetalhe());

                    cursosRA.add(curso);
                } else {
                    final CursoDTO curso = new CursoDTO();

                    curso.setDescricao(manualCurso.getDescricao());
                    curso.setDetalhe(manualCurso.getDetalhe());

                    cursosRF.add(curso);
                }
            }
        }
        manualDto.setColCursoDTORA(cursosRA);
        manualDto.setColCursoDTORF(cursosRF);

        // tratamento para competencias tecnicas
        Collection<ManualCompetenciaTecnicaDTO> colCompetencias = null;
        try {
            colCompetencias = competenciaTecnicaDao.findByIdManualFuncao(manualDto.getIdManualFuncao());
        } catch (final Exception e) {
            e.printStackTrace();
        }
        final ManualFuncaoCompetenciaDao manualFuncDao = new ManualFuncaoCompetenciaDao();

        if (colCompetencias != null) {
            for (final ManualCompetenciaTecnicaDTO manualCompetenciaTecnicaDTO : colCompetencias) {

                ManualFuncaoCompetenciaDTO manualFuncAcessoDto = new ManualFuncaoCompetenciaDTO();
                manualFuncAcessoDto.setIdNivelCompetencia(manualCompetenciaTecnicaDTO.getIdNivelCompetenciaAcesso());
                try {
                    manualFuncAcessoDto = (ManualFuncaoCompetenciaDTO) manualFuncDao.restore(manualFuncAcessoDto);
                    manualCompetenciaTecnicaDTO.setNomeCompetenciaFuncao(manualFuncAcessoDto.getDescricao());
                } catch (final Exception e) {
                    e.printStackTrace();
                }

                // tratamento para funcap
                ManualFuncaoCompetenciaDTO manualacesso = new ManualFuncaoCompetenciaDTO();
                manualacesso.setIdNivelCompetencia(manualCompetenciaTecnicaDTO.getIdNivelCompetenciaFuncao());
                try {
                    manualacesso = (ManualFuncaoCompetenciaDTO) manualFuncDao.restore(manualacesso);
                    manualCompetenciaTecnicaDTO.setNomeCompetenciaAcesso(manualFuncAcessoDto.getDescricao());
                } catch (final Exception e) {
                    e.printStackTrace();
                }

            }
        }
        manualDto.setColCompetenciaTecnicaDTO(colCompetencias);

        // tratamento para Perspectiva Comportamental
        Collection<PerspectivaComportamentalDTO> colPerspComportamental = null;
        try {
            colPerspComportamental = perspectivaCompDao.findByIdManualFuncao(manualDto.getIdManualFuncao());
        } catch (final Exception e) {
            e.printStackTrace();
        }
        final List<AtitudeIndividualDTO> colAtitudeIndividualDTO = new ArrayList<>();
        if (colPerspComportamental != null) {
            for (final PerspectivaComportamentalDTO perspDto : colPerspComportamental) {
                final AtitudeIndividualDTO compDto = new AtitudeIndividualDTO();
                compDto.setComportamento(perspDto.getComportamento());
                // compDto.setDescricao(perspDto.getdes);
                compDto.setDescricaoCmbCompetenciaComportamental(perspDto.getCmbCompetenciaComportamental());
                // compDto.setSituacao(perspDto.get);

                colAtitudeIndividualDTO.add(compDto);
            }
        }
        manualDto.setColPerspectivaComportamentalDTO(colAtitudeIndividualDTO);

        return manualDto;
    }

    @Override
    public HistManualFuncaoDTO createHistManualFuncao(final ManualFuncaoDTO manualFuncaoDto) throws Exception {
        final HistManualFuncaoDTO historico = new HistManualFuncaoDTO();
        Reflexao.copyPropertyValues(manualFuncaoDto, historico);

        final DecimalFormat fmt = new DecimalFormat("0.0");
        HistManualFuncaoDTO ultVersao = new HistManualFuncaoDTO();
        ultVersao = this.getHistManualFuncaoDao().maxIdHistorico(manualFuncaoDto);
        if (ultVersao.getIdhistManualFuncao() != null) {
            ultVersao = (HistManualFuncaoDTO) this.getHistManualFuncaoDao().restore(ultVersao);
            final String sVersaoConvertida = fmt.format(ultVersao.getVersao());
            historico.setVersao(ultVersao.getVersao() == null ? 1d : +new BigDecimal(Double.parseDouble(sVersaoConvertida.replace(",", ".")) + 0.1f).setScale(1,
                    BigDecimal.ROUND_DOWN).floatValue());
        } else {
            historico.setVersao(1d);
        }

        historico.setIdManualFuncao(manualFuncaoDto.getIdManualFuncao());
        historico.setDataAlteracao(UtilDatas.getDataAtual());
        historico.setTituloCargo(manualFuncaoDto.getTituloCargo());
        historico.setTituloFuncao(manualFuncaoDto.getTituloFuncao());
        historico.setCodCBO(manualFuncaoDto.getCodCBO());
        historico.setCodigo(manualFuncaoDto.getCodigo());
        historico.setPesoComplexidade(manualFuncaoDto.getPesoComplexidade());
        historico.setPesoComportamental(manualFuncaoDto.getPesoComportamental());
        historico.setPesoResultados(manualFuncaoDto.getPesoResultados());
        historico.setPesoTecnica(manualFuncaoDto.getPesoTecnica());
        historico.setIdFormacaoRA(manualFuncaoDto.getIdFormacaoRA());
        historico.setIdIdiomaRA(manualFuncaoDto.getIdIdiomaRA());
        historico.setIdNivelEscritaRA(manualFuncaoDto.getIdNivelEscritaRA());
        historico.setIdNivelLeituraRA(manualFuncaoDto.getIdNivelLeituraRA());
        historico.setIdNivelConversaRA(manualFuncaoDto.getIdNivelConversaRA());
        historico.setExpAnteriorRA(manualFuncaoDto.getExpAnteriorRA());
        historico.setIdFormacaoRF(manualFuncaoDto.getIdFormacaoRF());
        historico.setIdIdiomaRF(manualFuncaoDto.getIdIdiomaRF());
        historico.setIdNivelEscritaRF(manualFuncaoDto.getIdNivelEscritaRF());
        historico.setIdNivelLeituraRF(manualFuncaoDto.getIdNivelLeituraRF());
        historico.setIdNivelConversaRF(manualFuncaoDto.getIdNivelConversaRF());
        historico.setExpAnteriorRF(manualFuncaoDto.getExpAnteriorRF());
        // historico.setIdCurriculo(manualFuncalDto.getid)
        this.getHistManualFuncaoDao().create(historico);

        return historico;
    }

    @Override
    public void createHistManualCompetenciaTecnica(final ManualFuncaoDTO manualFuncalDto, final Integer idHistManualFuncao) throws Exception {
        final HistManualCompetenciaTecnicaDTO historico = new HistManualCompetenciaTecnicaDTO();

        historico.setIdhistManualFuncao(idHistManualFuncao);

        historico.setIdManualFuncao(manualFuncalDto.getIdManualFuncao());
        historico.setDataAlteracao(UtilDatas.getDataAtual());
        if (manualFuncalDto.getColCompetenciaTecnicaDTO() != null) {
            for (final ManualCompetenciaTecnicaDTO competenciaTecnica : manualFuncalDto.getColCompetenciaTecnicaDTO()) {
                historico.setDescricao(competenciaTecnica.getNomeCompetenciaTecnica());
                historico.setIdNivelCompetenciaAcesso(competenciaTecnica.getNomeCompetenciaAcesso());
                historico.setIdNivelCompetenciaFuncao(competenciaTecnica.getNomeCompetenciaFuncao());
                this.getHistManualCompetenciaTecnicaDao().create(historico);
            }
        }
    }

    @Override
    public void createHistManualCurso(final ManualFuncaoDTO manualFuncalDto, final Integer idHistManualFuncao) throws Exception {
        final HistManualCursoDTO historico = new HistManualCursoDTO();

        historico.setIdhistManualFuncao(idHistManualFuncao);
        historico.setIdManualFuncao(manualFuncalDto.getIdManualFuncao());
        historico.setDataAlteracao(UtilDatas.getDataAtual());
        if (manualFuncalDto.getColCursoDTORA() != null) {
            for (final CursoDTO cursoAux : manualFuncalDto.getColCursoDTORA()) {
                historico.setDescricao(cursoAux.getDescricao());
                historico.setRAouRF("RA");
                this.getHistManualCursoDao().create(historico);
            }
        }
        if (manualFuncalDto.getColCursoDTORF() != null) {
            for (final CursoDTO cursoAux : manualFuncalDto.getColCursoDTORF()) {
                historico.setDescricao(cursoAux.getDescricao());
                historico.setRAouRF("RF");
                this.getHistManualCursoDao().create(historico);
            }
        }
    }

    @Override
    public void createHistManualCertificacao(final ManualFuncaoDTO manualFuncalDto, final Integer idHistManualFuncao) throws Exception {
        final HistManualCertificacaoDTO historico = new HistManualCertificacaoDTO();

        historico.setIdhistManualFuncao(idHistManualFuncao);
        historico.setIdManualFuncao(manualFuncalDto.getIdManualFuncao());
        historico.setDataAlteracao(UtilDatas.getDataAtual());
        if (manualFuncalDto.getColCertificacaoDTORA() != null) {
            for (final CertificacaoDTO manualCertificacao : manualFuncalDto.getColCertificacaoDTORA()) {
                historico.setDescricao(manualCertificacao.getDescricao());
                historico.setRAouRF("RA");
                this.getHistManualCertificacaoDao().create(historico);
            }
        }
        if (manualFuncalDto.getColCertificacaoDTORF() != null) {
            for (final CertificacaoDTO manualCertificacao : manualFuncalDto.getColCertificacaoDTORA()) {
                historico.setDescricao(manualCertificacao.getDescricao());
                historico.setRAouRF("RF");
                this.getHistManualCertificacaoDao().create(historico);
            }
        }
    }

    @Override
    public void createHistPerspectivaComportamental(final ManualFuncaoDTO manualFuncao, final Integer idHistManualFuncao) throws Exception {
        final HistPerspectivaComportamentalDTO historico = new HistPerspectivaComportamentalDTO();

        historico.setIdhistManualFuncao(idHistManualFuncao);
        historico.setIdManualFuncao(manualFuncao.getIdManualFuncao());
        historico.setDataAlteracao(UtilDatas.getDataAtual());
        if (manualFuncao.getColPerspectivaComportamentalDTO() != null) {
            for (final AtitudeIndividualDTO perspectivaComportamental : manualFuncao.getColPerspectivaComportamentalDTO()) {
                historico.setCmbCompetenciaComportamental(perspectivaComportamental.getDescricaoCmbCompetenciaComportamental());
                historico.setComportamento(perspectivaComportamental.getComportamento());
                this.getHistPerspectivaComportamentalDao().create(historico);
            }
        }

    }

    @Override
    public void createHistAtribuicaoResponsabilidade(final ManualFuncaoDTO manualFuncalDto, final Integer idHistManualFuncao) throws Exception {
        final HistAtribuicaoResponsabilidadeDTO historico = new HistAtribuicaoResponsabilidadeDTO();

        historico.setIdhistManualFuncao(idHistManualFuncao);
        historico.setIdManualFuncao(manualFuncalDto.getIdManualFuncao());
        historico.setDataAlteracao(UtilDatas.getDataAtual());
        // historico.setIdUsuarioAlteracao(idUsuarioAlteracao);
        if (manualFuncalDto.getColAtribuicaoResponsabilidadeDTO() != null) {
            for (final AtribuicaoResponsabilidadeDTO atribuicaoResponsabilidade : manualFuncalDto.getColAtribuicaoResponsabilidadeDTO()) {
                historico.setDescricaoPerspectivaComplexidade(atribuicaoResponsabilidade.getDescricaoPerspectivaComplexidade());
                historico.setIdNivel(atribuicaoResponsabilidade.getIdNivel());
                // historico.setIdUsuarioAlteracao(null);
                this.getHistAtribuicaoResponsabilidadeDao().create(historico);
            }
        }
    }

    private HistManualFuncaoDao histManualFuncaoDao;

    private HistManualFuncaoDao getHistManualFuncaoDao() {
        if (histManualFuncaoDao == null) {
            histManualFuncaoDao = new HistManualFuncaoDao();
        }
        return histManualFuncaoDao;
    }

    private HistAtribuicaoResponsabilidadeDao histAtribuicaoResponsabilidadeDao;

    private HistAtribuicaoResponsabilidadeDao getHistAtribuicaoResponsabilidadeDao() {
        if (histAtribuicaoResponsabilidadeDao == null) {
            histAtribuicaoResponsabilidadeDao = new HistAtribuicaoResponsabilidadeDao();
        }
        return histAtribuicaoResponsabilidadeDao;
    }

    private HistPerspectivaComportamentalDao histPerspectivaComportamentalDao;

    private HistPerspectivaComportamentalDao getHistPerspectivaComportamentalDao() {
        if (histPerspectivaComportamentalDao == null) {
            histPerspectivaComportamentalDao = new HistPerspectivaComportamentalDao();
        }
        return histPerspectivaComportamentalDao;
    }

    private HistManualCertificacaoDao histManualCertificacaoDao;

    private HistManualCertificacaoDao getHistManualCertificacaoDao() {
        if (histManualCertificacaoDao == null) {
            histManualCertificacaoDao = new HistManualCertificacaoDao();
        }
        return histManualCertificacaoDao;
    }

    private HistManualCursoDao histManualCursoDao;

    private HistManualCursoDao getHistManualCursoDao() {
        if (histManualCursoDao == null) {
            histManualCursoDao = new HistManualCursoDao();
        }
        return histManualCursoDao;
    }

    private HistManualCompetenciaTecnicaDao histManualCompetenciaTecnicaDao;

    private HistManualCompetenciaTecnicaDao getHistManualCompetenciaTecnicaDao() {
        if (histManualCompetenciaTecnicaDao == null) {
            histManualCompetenciaTecnicaDao = new HistManualCompetenciaTecnicaDao();
        }
        return histManualCompetenciaTecnicaDao;
    }

}
