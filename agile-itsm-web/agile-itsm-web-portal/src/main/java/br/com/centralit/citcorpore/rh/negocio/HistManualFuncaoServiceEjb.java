package br.com.centralit.citcorpore.rh.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.CertificacaoDTO;
import br.com.centralit.citcorpore.rh.bean.CursoDTO;
import br.com.centralit.citcorpore.rh.bean.HistAtribuicaoResponsabilidadeDTO;
import br.com.centralit.citcorpore.rh.bean.HistManualCertificacaoDTO;
import br.com.centralit.citcorpore.rh.bean.HistManualCompetenciaTecnicaDTO;
import br.com.centralit.citcorpore.rh.bean.HistManualCursoDTO;
import br.com.centralit.citcorpore.rh.bean.HistManualFuncaoDTO;
import br.com.centralit.citcorpore.rh.bean.HistPerspectivaComportamentalDTO;
import br.com.centralit.citcorpore.rh.integracao.HistAtribuicaoResponsabilidadeDao;
import br.com.centralit.citcorpore.rh.integracao.HistManualCertificacaoDao;
import br.com.centralit.citcorpore.rh.integracao.HistManualCompetenciaTecnicaDao;
import br.com.centralit.citcorpore.rh.integracao.HistManualCursoDao;
import br.com.centralit.citcorpore.rh.integracao.HistManualFuncaoDao;
import br.com.centralit.citcorpore.rh.integracao.HistPerspectivaComportamentalDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

@SuppressWarnings("unchecked")
public class HistManualFuncaoServiceEjb extends CrudServiceImpl implements HistManualFuncaoService {

    private HistManualFuncaoDao dao;

    @Override
    protected HistManualFuncaoDao getDao() {
        if (dao == null) {
            dao = new HistManualFuncaoDao();
        }
        return dao;
    }

    @Override
    public IDto restore(final IDto model) throws ServiceException, LogicException {
        final HistManualFuncaoDTO histManualFuncaoDTO = (HistManualFuncaoDTO) super.restore(model);

        final HistAtribuicaoResponsabilidadeDao atribuicaoResponsabilidadeDao = new HistAtribuicaoResponsabilidadeDao();
        final HistManualCertificacaoDao certificacaoDao = new HistManualCertificacaoDao();
        final HistManualCursoDao cursoDao = new HistManualCursoDao();
        final HistManualCompetenciaTecnicaDao competenciaTecnicaDao = new HistManualCompetenciaTecnicaDao();
        final HistPerspectivaComportamentalDao perspectivaCompDao = new HistPerspectivaComportamentalDao();

        // tratamento para atribuicaoResposabilidade
        Collection<HistAtribuicaoResponsabilidadeDTO> colResponsabilidades = null;
        try {
            colResponsabilidades = atribuicaoResponsabilidadeDao.findByIdHistManualFuncao(histManualFuncaoDTO.getIdhistManualFuncao());
        } catch (final Exception e) {
            e.printStackTrace();
        }
        histManualFuncaoDTO.setColAtribuicaoResponsabilidadeDTO(colResponsabilidades);

        // tratamento para certificados
        Collection<HistManualCertificacaoDTO> colCertificados = null;
        try {
            colCertificados = certificacaoDao.findByIdHistManualFuncao(histManualFuncaoDTO.getIdhistManualFuncao());
        } catch (final Exception e) {
            e.printStackTrace();
        }
        final List<CertificacaoDTO> certificadosRA = new ArrayList<>();
        final List<CertificacaoDTO> certificadosRF = new ArrayList<>();
        if (colCertificados != null) {
            for (final HistManualCertificacaoDTO manualCertificacaoDto : colCertificados) {
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
        histManualFuncaoDTO.setColCertificacaoDTORA(certificadosRA);
        histManualFuncaoDTO.setColCertificacaoDTORF(certificadosRF);

        // tratamento para Cursos
        Collection<HistManualCursoDTO> colCursos = null;
        try {
            colCursos = cursoDao.findByIdHistManualFuncao(histManualFuncaoDTO.getIdhistManualFuncao());
        } catch (final Exception e) {
            e.printStackTrace();
        }
        final List<CursoDTO> cursosRA = new ArrayList<>();
        final List<CursoDTO> cursosRF = new ArrayList<>();
        if (colCursos != null) {
            for (final HistManualCursoDTO manualCurso : colCursos) {
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
        histManualFuncaoDTO.setColCursoDTORA(cursosRA);
        histManualFuncaoDTO.setColCursoDTORF(cursosRF);

        // tratamento para competencias tecnicas
        Collection<HistManualCompetenciaTecnicaDTO> colCompetencias = null;
        try {
            colCompetencias = competenciaTecnicaDao.findByIdHistManualFuncao(histManualFuncaoDTO.getIdhistManualFuncao());
        } catch (final Exception e) {
            e.printStackTrace();
        }

        if (colCompetencias != null) {
            for (final HistManualCompetenciaTecnicaDTO manualCompetenciaTecnicaDTO : colCompetencias) {

                try {
                    manualCompetenciaTecnicaDTO.setIdNivelCompetenciaFuncao(manualCompetenciaTecnicaDTO.getIdNivelCompetenciaAcesso());
                } catch (final Exception e) {
                    e.printStackTrace();
                }

                try {
                    manualCompetenciaTecnicaDTO.setIdNivelCompetenciaAcesso(manualCompetenciaTecnicaDTO.getIdNivelCompetenciaFuncao());
                } catch (final Exception e) {
                    e.printStackTrace();
                }

            }
        }
        histManualFuncaoDTO.setColCompetenciaTecnicaDTO(colCompetencias);

        // tratamento para Perspectiva Comportamental
        Collection<HistPerspectivaComportamentalDTO> colPerspComportamental = null;
        try {
            colPerspComportamental = perspectivaCompDao.findByIdHistManualFuncao(histManualFuncaoDTO.getIdhistManualFuncao());
        } catch (final Exception e) {
            e.printStackTrace();
        }
        histManualFuncaoDTO.setColPerspectivaComportamentalDTO(colPerspComportamental);

        return histManualFuncaoDTO;
    }

}
