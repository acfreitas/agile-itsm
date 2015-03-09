package br.com.centralit.citcorpore.rh.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.rh.bean.AtitudeIndividualDTO;
import br.com.centralit.citcorpore.rh.bean.AtribuicaoResponsabilidadeDTO;
import br.com.centralit.citcorpore.rh.bean.CertificacaoDTO;
import br.com.centralit.citcorpore.rh.bean.CursoDTO;
import br.com.centralit.citcorpore.rh.bean.DescricaoAtribuicaoResponsabilidadeDTO;
import br.com.centralit.citcorpore.rh.bean.HistManualFuncaoDTO;
import br.com.centralit.citcorpore.rh.bean.IdiomaDTO;
import br.com.centralit.citcorpore.rh.bean.ManualCompetenciaTecnicaDTO;
import br.com.centralit.citcorpore.rh.bean.ManualFuncaoCompetenciaDTO;
import br.com.centralit.citcorpore.rh.bean.ManualFuncaoComplexidadeDTO;
import br.com.centralit.citcorpore.rh.bean.ManualFuncaoDTO;
import br.com.centralit.citcorpore.rh.integracao.HistManualFuncaoDao;
import br.com.centralit.citcorpore.rh.negocio.AtitudeIndividualService;
import br.com.centralit.citcorpore.rh.negocio.DescricaoAtribuicaoResponsabilidadeService;
import br.com.centralit.citcorpore.rh.negocio.IdiomaService;
import br.com.centralit.citcorpore.rh.negocio.ManualFuncaoCompetenciaService;
import br.com.centralit.citcorpore.rh.negocio.ManualFuncaoComplexidadeService;
import br.com.centralit.citcorpore.rh.negocio.ManualFuncaoService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({"rawtypes", "unchecked"})
public class ManualFuncao extends AjaxFormAction {

    @Override
    public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        WebUtil.validarSeUsuarioEstaNaSessao(request, document);

        // Carregamaneto combobox Perspectiva Complexidade
        preencherComboNivel(document, request, response);

        // Carregamaneto combobox Perspectiva Tecnica - Requisitos de Acesso
        preencherComboIdiomaRA(document, request, response);

        // Carregamaneto combobox Perspectiva Tecnica - Requisitos da Função
        preencherComboIdiomaRF(document, request, response);

        // Carregamaneto combobox Competência Tecnica
        preencherComboCompetenciasAcesso(document, request, response);
        preencherComboCompetenciasFuncao(document, request, response);
    }

    public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        final ManualFuncaoDTO manualDto = (ManualFuncaoDTO) document.getBean();
        final ManualFuncaoService manualService = (ManualFuncaoService) ServiceLocator.getInstance().getService(
                ManualFuncaoService.class, WebUtil.getUsuarioSistema(request));

        final Collection colResponsabilidades = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(
                AtribuicaoResponsabilidadeDTO.class, "colResponsabilidades_Serialize", request);
        final Collection colCertificacoesRA = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(
                CertificacaoDTO.class, "colCertificacoes_Serialize", request);
        final Collection colCursosRA = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(
                CursoDTO.class, "colCursos_Serialize", request);
        final Collection colCertificacoesRF = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(
                CertificacaoDTO.class, "colCertificacoesRF_Serialize", request);
        final Collection colCursosRF = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(
                CursoDTO.class, "colCursosRF_Serialize", request);
        final Collection colCompetencias = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(
                ManualCompetenciaTecnicaDTO.class, "colCompetencias_Serialize", request);
        final Collection colPerspectivaComportamental = br.com.citframework.util.WebUtil
                .deserializeCollectionFromRequest(AtitudeIndividualDTO.class, "colPerspectivaComportamental_Serialize",
                        request);

        manualDto.setColAtribuicaoResponsabilidadeDTO(colResponsabilidades);
        manualDto.setColCertificacaoDTORA(colCertificacoesRA);
        manualDto.setColCursoDTORA(colCursosRA);
        manualDto.setColCertificacaoDTORF(colCertificacoesRF);
        manualDto.setColCursoDTORF(colCursosRF);
        manualDto.setColCompetenciaTecnicaDTO(colCompetencias);
        manualDto.setColPerspectivaComportamentalDTO(colPerspectivaComportamental);

        if (manualDto.getIdManualFuncao() == null || manualDto.getIdManualFuncao().intValue() == 0) {
            manualService.create(manualDto);
            document.alert(UtilI18N.internacionaliza(request, "MSG05"));
        } else {
            manualService.update(manualDto);
            document.alert(UtilI18N.internacionaliza(request, "MSG06"));
        }

        // Gravar historico
        final HistManualFuncaoDTO histManualFuncao = manualService.createHistManualFuncao(manualDto);
        manualService.createHistAtribuicaoResponsabilidade(manualDto, histManualFuncao.getIdhistManualFuncao());
        manualService.createHistPerspectivaComportamental(manualDto, histManualFuncao.getIdhistManualFuncao());
        manualService.createHistManualCompetenciaTecnica(manualDto, histManualFuncao.getIdhistManualFuncao());
        manualService.createHistManualCurso(manualDto, histManualFuncao.getIdhistManualFuncao());
        manualService.createHistManualCertificacao(manualDto, histManualFuncao.getIdhistManualFuncao());

        final HTMLForm form = document.getForm("form");
        form.clear();
        document.executeScript("limparDados();");
    }

    private void preencherComboNivel(DocumentHTML document, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        final HTMLSelect cmbJustificativa = document.getSelectById("idNivel");

        if (cmbJustificativa != null) {

            final ManualFuncaoComplexidadeService complexidadeService = (ManualFuncaoComplexidadeService) ServiceLocator
                    .getInstance().getService(ManualFuncaoComplexidadeService.class, null);

            final ArrayList<ManualFuncaoComplexidadeDTO> manualFuncaoComplexidade = (ArrayList<ManualFuncaoComplexidadeDTO>) complexidadeService
                    .listAtivos();

            inicializarCombo(cmbJustificativa, request);

            for (final ManualFuncaoComplexidadeDTO complexidadeDto : manualFuncaoComplexidade) {
                cmbJustificativa.addOption(complexidadeDto.getIdComplexidade().toString(),
                        StringEscapeUtils.escapeJavaScript(complexidadeDto.getDescricao()));
            }
        }
    }

    private void preencherComboIdiomaRA(DocumentHTML document, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        final HTMLSelect comboIdioma = document.getSelectById("idIdiomaRA");

        if (comboIdioma != null) {
            inicializarCombo(comboIdioma, request);

            final IdiomaService idiomaService = (IdiomaService) ServiceLocator.getInstance().getService(
                    IdiomaService.class, null);
            final HTMLSelect cmbIdioma = document.getSelectById("idIdiomaRA");
            final Collection<IdiomaDTO> idiomaDto = idiomaService.list();

            inicializarCombo(cmbIdioma, request);

            for (final IdiomaDTO idiomaDto2 : idiomaDto) {
                cmbIdioma.addOption(idiomaDto2.getIdIdioma().toString(),
                        StringEscapeUtils.escapeJavaScript(idiomaDto2.getDescricao()));
            }
        }
    }

    private void preencherComboIdiomaRF(DocumentHTML document, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        final HTMLSelect comboIdioma = document.getSelectById("idIdiomaRF");

        if (comboIdioma != null) {
            inicializarCombo(comboIdioma, request);

            final IdiomaService idiomaService = (IdiomaService) ServiceLocator.getInstance().getService(
                    IdiomaService.class, null);
            final HTMLSelect cmbIdioma = document.getSelectById("idIdiomaRF");
            final Collection<IdiomaDTO> idiomaDto = idiomaService.list();

            inicializarCombo(cmbIdioma, request);

            for (final IdiomaDTO idiomaDto2 : idiomaDto) {
                cmbIdioma.addOption(idiomaDto2.getIdIdioma().toString(),
                        StringEscapeUtils.escapeJavaScript(idiomaDto2.getDescricao()));
            }
        }
    }

    private void preencherComboCompetenciasAcesso(DocumentHTML document, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        final HTMLSelect cmbCompetencia = document.getSelectById("idNivelCompetenciaAcesso");

        if (cmbCompetencia != null) {

            final ManualFuncaoCompetenciaService competenciaService = (ManualFuncaoCompetenciaService) ServiceLocator
                    .getInstance().getService(ManualFuncaoCompetenciaService.class, null);
            final ArrayList<ManualFuncaoCompetenciaDTO> competencia = (ArrayList<ManualFuncaoCompetenciaDTO>) competenciaService
                    .listAtivos();

            inicializarCombo(cmbCompetencia, request);

            for (final ManualFuncaoCompetenciaDTO competenciaDto : competencia) {
                cmbCompetencia.addOption(competenciaDto.getIdNivelCompetencia().toString(),
                        StringEscapeUtils.escapeJavaScript(competenciaDto.getDescricao()));
            }
        }
    }

    private void preencherComboCompetenciasFuncao(DocumentHTML document, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        final HTMLSelect cmbCompetencia = document.getSelectById("idNivelCompetenciaFuncao");
        if (cmbCompetencia != null) {
            inicializarCombo(cmbCompetencia, request);

            final ManualFuncaoCompetenciaService competenciaService = (ManualFuncaoCompetenciaService) ServiceLocator
                    .getInstance().getService(ManualFuncaoCompetenciaService.class, null);
            final ArrayList<ManualFuncaoCompetenciaDTO> competencia = (ArrayList<ManualFuncaoCompetenciaDTO>) competenciaService
                    .listAtivos();

            inicializarCombo(cmbCompetencia, request);

            for (final ManualFuncaoCompetenciaDTO competenciaDto : competencia) {
                cmbCompetencia.addOption(competenciaDto.getIdNivelCompetencia().toString(),
                        StringEscapeUtils.escapeJavaScript(competenciaDto.getDescricao()));
            }
        }
    }

    private void inicializarCombo(HTMLSelect componenteCombo, HttpServletRequest request) {
        componenteCombo.removeAllOptions();
        componenteCombo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
    }

    public void gravarManualFuncaoComplexidade(DocumentHTML document, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        final ManualFuncaoDTO manualDto = (ManualFuncaoDTO) document.getBean();

        if (manualDto.getDescricaoPerspectivaComplexidade().isEmpty()) {
            document.alert("Campo vazio.");
            return;
        }

        final DescricaoAtribuicaoResponsabilidadeDTO descricaoAtribuicao = new DescricaoAtribuicaoResponsabilidadeDTO();
        descricaoAtribuicao.setSituacao("A");
        descricaoAtribuicao.setDescricao(manualDto.getDescricaoPerspectivaComplexidade());

        final DescricaoAtribuicaoResponsabilidadeService descricaoService = (DescricaoAtribuicaoResponsabilidadeService) ServiceLocator
                .getInstance().getService(DescricaoAtribuicaoResponsabilidadeService.class,
                        WebUtil.getUsuarioSistema(request));

        final Collection colecaoDescricaoAux = descricaoService.findByNome(descricaoAtribuicao.getDescricao());
        if (colecaoDescricaoAux != null && !colecaoDescricaoAux.isEmpty()) {
            document.alert("Registro já existe!");
        } else {
            descricaoService.create(descricaoAtribuicao);
            document.alert(UtilI18N.internacionaliza(request, "MSG05"));
        }

    }

    public void gravarComportamento(DocumentHTML document, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        final ManualFuncaoDTO manualDto = (ManualFuncaoDTO) document.getBean();

        if (manualDto.getComportamento().isEmpty()) {
            document.alert("Campo vazio.");
            return;
        }

        final AtitudeIndividualDTO atitudeIndividual = new AtitudeIndividualDTO();
        atitudeIndividual.setDescricao(manualDto.getComportamento());

        final AtitudeIndividualService atitudeIndividualService = (AtitudeIndividualService) ServiceLocator
                .getInstance().getService(AtitudeIndividualService.class, WebUtil.getUsuarioSistema(request));

        final Collection colecaoDescricaoAux = atitudeIndividualService.findByNome(atitudeIndividual.getDescricao());
        if (colecaoDescricaoAux != null && !colecaoDescricaoAux.isEmpty()) {
            document.alert("Registro já existe!");
        } else {
            atitudeIndividualService.create(atitudeIndividual);
            document.alert(UtilI18N.internacionaliza(request, "MSG05"));
        }

    }

    public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        ManualFuncaoDTO manualFuncaoDto = (ManualFuncaoDTO) document.getBean();
        final ManualFuncaoService manualService = (ManualFuncaoService) ServiceLocator.getInstance().getService(
                ManualFuncaoService.class, WebUtil.getUsuarioSistema(request));
        final HistManualFuncaoDao historicoFuncaoDao = new HistManualFuncaoDao();

        manualFuncaoDto = (ManualFuncaoDTO) manualService.restore(manualFuncaoDto);

        final HTMLForm form = document.getForm("form");
        form.clear();
        form.setValues(manualFuncaoDto);

        // tratamento para atribuicaoresponsabilidade
        final HTMLTable tblResponsabilidades = document.getTableById("tblResponsabilidades");
        tblResponsabilidades.deleteAllRows();

        if (manualFuncaoDto.getColAtribuicaoResponsabilidadeDTO() != null
                && !manualFuncaoDto.getColAtribuicaoResponsabilidadeDTO().isEmpty()) {
            tblResponsabilidades.addRowsByCollection(manualFuncaoDto.getColAtribuicaoResponsabilidadeDTO(),
                    new String[] {"descricaoPerspectivaComplexidade", "idNivel", ""}, null,
                    "Já existe registrado esta demanda na tabela", new String[] {"gerarButtonDeleteResponsabilidade"},
                    null, null);
        }

        // tratamento para certificados
        final HTMLTable tblCertificacaoRA = document.getTableById("tblCertificacoesRA");
        tblCertificacaoRA.deleteAllRows();

        if (manualFuncaoDto.getColCertificacaoDTORA() != null && !manualFuncaoDto.getColCertificacaoDTORA().isEmpty()) {
            tblCertificacaoRA.addRowsByCollection(manualFuncaoDto.getColCertificacaoDTORA(), new String[] {"descricao",
                    ""}, null, "Já existe registrado esta demanda na tabela",
                    new String[] {"gerarButtonDeleteCertificacao"}, null, null);
        }
        final HTMLTable tblCertificacaoRF = document.getTableById("tblCertificacoesRF");
        tblCertificacaoRF.deleteAllRows();

        if (manualFuncaoDto.getColCertificacaoDTORF() != null && !manualFuncaoDto.getColCertificacaoDTORF().isEmpty()) {
            tblCertificacaoRF.addRowsByCollection(manualFuncaoDto.getColCertificacaoDTORF(), new String[] {"descricao",
                    ""}, null, "Já existe registrado esta demanda na tabela",
                    new String[] {"gerarButtonDeleteCertificacaoRF"}, null, null);
        }

        // tratamento para cursos
        final HTMLTable tblCursoRA = document.getTableById("tblCursosRA");
        tblCursoRA.deleteAllRows();

        if (manualFuncaoDto.getColCursoDTORA() != null && !manualFuncaoDto.getColCursoDTORA().isEmpty()) {
            tblCursoRA.addRowsByCollection(manualFuncaoDto.getColCursoDTORA(), new String[] {"descricao", ""}, null,
                    "Já existe registrado esta demanda na tabela", new String[] {"gerarButtonDeleteCurso"}, null, null);
        }
        final HTMLTable tblCursoRF = document.getTableById("tblCursoRF");
        tblCursoRF.deleteAllRows();

        if (manualFuncaoDto.getColCursoDTORF() != null && !manualFuncaoDto.getColCursoDTORF().isEmpty()) {
            tblCursoRF.addRowsByCollection(manualFuncaoDto.getColCursoDTORF(), new String[] {"descricao", ""}, null,
                    "Já existe registrado esta demanda na tabela", new String[] {"gerarButtonDeleteCursoRF"}, null,
                    null);
        }
        // tratamento para competencias
        final HTMLTable tblCompetencias = document.getTableById("tblCompetencias");
        tblCompetencias.deleteAllRows();

        final Collection<ManualCompetenciaTecnicaDTO> colCompetencias = manualFuncaoDto.getColCompetenciaTecnicaDTO();
        if (manualFuncaoDto.getColCompetenciaTecnicaDTO() != null
                && !manualFuncaoDto.getColCompetenciaTecnicaDTO().isEmpty()) {
            document.executeScript("addCompetenciaJava('"
                    + br.com.citframework.util.WebUtil.serializeObjects(colCompetencias) + "')");
        }

        // tratamento para perspectiva comportamental
        final HTMLTable tblPerspComp = document.getTableById("tblPerspectivaComportamental");
        tblPerspComp.deleteAllRows();

        if (manualFuncaoDto.getColCompetenciaTecnicaDTO() != null) {
            tblPerspComp.addRowsByCollection(manualFuncaoDto.getColPerspectivaComportamentalDTO(), new String[] {
                    "descricaoCmbCompetenciaComportamental", "comportamento", ""}, null,
                    "Já existe registrado esta demanda na tabela",
                    new String[] {"gerarButtonDeletePerspectivaComportamental"}, null, null);
        }

        final HTMLTable tblHistorivoVersao = document.getTableById("tblHistoricoVersoes");
        tblHistorivoVersao.deleteAllRows();
        final Collection<HistManualFuncaoDTO> colHistoricoVersoes = historicoFuncaoDao
                .findByIdManualFuncao(manualFuncaoDto.getIdManualFuncao());
        if (colHistoricoVersoes != null) {
            tblHistorivoVersao.addRowsByCollection(colHistoricoVersoes, new String[] {"tituloCargo", "tituloFuncao",
                    "codCBO", "codigo", "versao", ""}, null, "Já existe registrado esta demanda na tabela",
                    new String[] {"gerarButtonVisualizaHistorico"}, null, null);
        }
    }

    @Override
    public Class getBeanClass() {
        return ManualFuncaoDTO.class;
    }

}
