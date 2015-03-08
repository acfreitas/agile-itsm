package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import br.com.centralit.bpm.dto.TarefaFluxoDTO;
import br.com.centralit.bpm.util.Enumerados;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.GerenciamentoProblemasDTO;
import br.com.centralit.citcorpore.bean.ProblemaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.ProblemaDAO;
import br.com.centralit.citcorpore.negocio.ExecucaoProblemaService;
import br.com.centralit.citcorpore.negocio.ProblemaService;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoProblema;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoRequisicaoProblema;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({"rawtypes", "unchecked"})
public class GerenciamentoProblemas extends AjaxFormAction {

    @Override
    public Class<GerenciamentoProblemasDTO> getBeanClass() {
        return GerenciamentoProblemasDTO.class;
    }

    @Override
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        this.exibeTarefas(document, request, response);
    }

    public void exibeTarefas(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        ProblemaDTO problemaDto = new ProblemaDTO();

        final UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert("Sessao expirada! Favor efetuar logon novamente!");
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }

        final GerenciamentoProblemasDTO gerenciamentoBean = (GerenciamentoProblemasDTO) document.getBean();

        final List<TarefaFluxoDTO> colTarefas = this.getExecucaoProblemaService().recuperaTarefas(usuario.getLogin());
        if (colTarefas == null) {
            return;
        }

        boolean bFiltroPorSolicitacao = false;
        if (gerenciamentoBean != null) {
            bFiltroPorSolicitacao = gerenciamentoBean.getIdProblemaSel() != null && gerenciamentoBean.getIdProblemaSel().length() > 0;
        }
        final List<TarefaFluxoDTO> colTarefasFiltradas = new ArrayList<>();
        if (!bFiltroPorSolicitacao) {
            colTarefasFiltradas.addAll(colTarefas);
        } else {
            for (final TarefaFluxoDTO tarefaDto : colTarefas) {
                boolean bAdicionar = false;
                final String idProblema = "" + ((ProblemaDTO) tarefaDto.getProblemaDto()).getIdProblema();
                bAdicionar = idProblema.indexOf(gerenciamentoBean.getIdProblemaSel()) >= 0;
                if (bAdicionar) {
                    colTarefasFiltradas.add(tarefaDto);
                }
            }
        }
        final List colTarefasFiltradasFinal = new ArrayList();
        final HashMap mapAtr = new HashMap();
        mapAtr.put("-- Sem Atribuição --", "-- Sem Atribuição --");
        for (final TarefaFluxoDTO tarefaDto : colTarefasFiltradas) {
            problemaDto = (ProblemaDTO) tarefaDto.getProblemaDto();
            problemaDto.setDataHoraInicioSLAStr("");
            problemaDto.setDescricao("");
            if (problemaDto.getPrazoHH() != null) {
                problemaDto.getPrazoHH();
            }
            if (problemaDto.getPrazoMM() != null) {
                problemaDto.getPrazoMM();
            }

            problemaDto.setDataHoraLimiteStr(UtilDatas.convertDateToString(TipoDate.TIMESTAMP_DEFAULT, problemaDto.getDataHoraLimite(), WebUtil.getLanguage(request)));
            problemaDto.setDataHoraInicioSLAStr(UtilDatas.convertDateToString(TipoDate.TIMESTAMP_DEFAULT, problemaDto.getDataHoraInicioSLA(), WebUtil.getLanguage(request)));
            problemaDto.setDataHoraCapturaStr(UtilDatas.convertDateToString(TipoDate.TIMESTAMP_DEFAULT, problemaDto.getDataHoraCaptura(), WebUtil.getLanguage(request)));

            if (problemaDto.getResponsavel() != null) {
                if (!mapAtr.containsKey(problemaDto.getResponsavel())) {
                    mapAtr.put(problemaDto.getResponsavel(), problemaDto.getResponsavel());
                }
            }

            if (gerenciamentoBean.getAtribuidaCompartilhada() == null || gerenciamentoBean.getAtribuidaCompartilhada().trim().equalsIgnoreCase("")) {
                colTarefasFiltradasFinal.add(tarefaDto);
            } else {
                if (gerenciamentoBean.getAtribuidaCompartilhada().trim().equalsIgnoreCase(UtilI18N.internacionaliza(request, "citcorpore.comum.sematribuicao"))) {
                    if (tarefaDto.getResponsavelAtual() == null || tarefaDto.getResponsavelAtual().trim().equalsIgnoreCase("")) {
                        colTarefasFiltradasFinal.add(tarefaDto);
                    }
                } else {
                    if (gerenciamentoBean.getAtribuidaCompartilhada().trim().equalsIgnoreCase(tarefaDto.getResponsavelAtual())) {
                        colTarefasFiltradasFinal.add(tarefaDto);
                    }
                }
            }

            final String status = this.setStatusInternacionalidados(document, request, response, problemaDto);
            if (status != null) {
                problemaDto.setStatus(status);
            }

        }

        if (gerenciamentoBean != null && gerenciamentoBean.getIdProblemaSel() != null && !gerenciamentoBean.getIdProblemaSel().trim().equalsIgnoreCase("")) {
            problemaDto.setIdProblema(new Integer(gerenciamentoBean.getIdProblemaSel()));
        }

        final String tarefasStr = this.serializaTarefas(colTarefasFiltradasFinal, request);

        document.executeScript("exibirTarefas('" + tarefasStr + "');");

    }

    private String serializaTarefas(final List<TarefaFluxoDTO> colTarefas, final HttpServletRequest request) throws Exception {
        if (colTarefas == null) {
            return null;
        }
        for (final TarefaFluxoDTO tarefaDto : colTarefas) {
            final String elementoFluxo_serialize = StringEscapeUtils.escapeJavaScript(br.com.citframework.util.WebUtil.serializeObject(tarefaDto.getElementoFluxoDto(),
                    WebUtil.getLanguage(request)));
            final String problema_serialize = StringEscapeUtils.escapeJavaScript(br.com.citframework.util.WebUtil.serializeObject(tarefaDto.getProblemaDto(),
                    WebUtil.getLanguage(request)));

            tarefaDto.setElementoFluxo_serialize(elementoFluxo_serialize);
            tarefaDto.setProblema_serialize(problema_serialize);
        }
        return br.com.citframework.util.WebUtil.serializeObjects(colTarefas, WebUtil.getLanguage(request));
    }

    public void preparaExecucaoTarefa(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert("Sessão expirada! Favor efetuar logon novamente!");
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }

        final GerenciamentoProblemasDTO gerenciamentoBean = (GerenciamentoProblemasDTO) document.getBean();
        if (gerenciamentoBean.getIdTarefa() == null) {
            return;
        }

        if (gerenciamentoBean.getIdProblema() == null) {
            return;
        }

        final TarefaFluxoDTO tarefaDto = this.getExecucaoProblemaService().recuperaTarefa(usuario.getLogin(), gerenciamentoBean.getIdTarefa());
        if (tarefaDto == null || tarefaDto.getElementoFluxoDto() == null || !tarefaDto.getExecutar().equals("S") || tarefaDto.getElementoFluxoDto().getTipoInteracao() == null) {
            return;
        }

        if (tarefaDto.getElementoFluxoDto().getTipoInteracao().equals(Enumerados.INTERACAO_VISAO)) {
            if (tarefaDto.getIdVisao() != null) {
                document.executeScript("exibirVisao('Executar tarefa " + tarefaDto.getElementoFluxoDto().getDocumentacao() + "','" + tarefaDto.getIdVisao() + "','"
                        + tarefaDto.getElementoFluxoDto().getIdFluxo() + "','" + tarefaDto.getIdItemTrabalho() + "','" + gerenciamentoBean.getAcaoFluxo() + "');");
            } else {
                document.alert("Visão para tarefa \"" + tarefaDto.getElementoFluxoDto().getDocumentacao() + "\" não encontrada");
            }
        } else {
            String caracterParmURL = "?";
            if (tarefaDto.getElementoFluxoDto().getUrl().indexOf("?") > -1) { // Se na URL ja tiver ?, entao colocar &
                caracterParmURL = "&";
            }
            document.executeScript("exibirUrl('Executar tarefa " + tarefaDto.getElementoFluxoDto().getDocumentacao() + "','" + tarefaDto.getElementoFluxoDto().getUrl()
                    + caracterParmURL + "idProblema=" + gerenciamentoBean.getIdProblema() + "&idTarefa=" + tarefaDto.getIdItemTrabalho() + "&acaoFluxo="
                    + gerenciamentoBean.getAcaoFluxo() + "');");
        }
    }

    public void reativaProblema(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert("Sessão expirada! Favor efetuar logon novamente!");
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }

        final GerenciamentoProblemasDTO gerenciamentoBean = (GerenciamentoProblemasDTO) document.getBean();
        if (gerenciamentoBean.getIdProblema() == null) {
            return;
        }

        final ProblemaService problemaService = this.getProblemaService();
        final ProblemaDTO problemaDto = problemaService.restoreAll(gerenciamentoBean.getIdProblema());
        problemaService.reativa(usuario, problemaDto);
        this.exibeTarefas(document, request, response);
    }

    public void capturaTarefa(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }

        GerenciamentoProblemasDTO problemaBean = (GerenciamentoProblemasDTO) document.getBean();

        if (problemaBean.getIdTarefa() == null) {
            return;
        }

        if (problemaBean.getIdProblema() == null) {
            return;
        }

        final ProblemaDTO problemaDto = new ProblemaDTO();
        final ProblemaDAO problemaDao = new ProblemaDAO();
        problemaDto.setIdProblema(problemaBean.getIdProblema());
        problemaDto.setIdProprietario(usuario.getIdUsuario());
        problemaDao.updateNotNull(problemaDto);

        this.getExecucaoProblemaService().executa(usuario, problemaBean.getIdTarefa(), Enumerados.ACAO_INICIAR);
        this.exibeTarefas(document, request, response);

        problemaBean = null;
    }

    private ExecucaoProblemaService execucaoProblemaService;
    private ProblemaService problemaService;

    private ExecucaoProblemaService getExecucaoProblemaService() throws ServiceException {
        if (execucaoProblemaService == null) {
            execucaoProblemaService = (ExecucaoProblemaService) ServiceLocator.getInstance().getService(ExecucaoProblemaService.class, null);
        }
        return execucaoProblemaService;
    }

    private ProblemaService getProblemaService() throws ServiceException {
        if (problemaService == null) {
            problemaService = (ProblemaService) ServiceLocator.getInstance().getService(ProblemaService.class, null);
        }
        return problemaService;
    }

    /**
     * Metodo para internacionalizar status de problema
     *
     * @param document
     * @param request
     * @param response
     * @param problemaDto
     * @return
     * @throws Exception
     * @author thays.araujo
     */
    public String setStatusInternacionalidados(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response, final ProblemaDTO problemaDto)
            throws Exception {
        String status = "";

        if (problemaDto.getStatus().equalsIgnoreCase(SituacaoRequisicaoProblema.Registrada.getDescricao())) {
            status = UtilI18N.internacionaliza(request, "citcorpore.comum.registrada");
            return status;
        }
        if (problemaDto.getStatus().equalsIgnoreCase(SituacaoRequisicaoProblema.EmInvestigacao.getDescricao())) {
            status = UtilI18N.internacionaliza(request, "gerenciamentoProblema.emInvestigacao");
            return status;
        }
        if (problemaDto.getStatus().equalsIgnoreCase(SituacaoRequisicaoProblema.Aprovada.getDescricao())) {
            status = UtilI18N.internacionaliza(request, "citcorpore.comum.aprovacao");
            return status;
        }
        if (problemaDto.getStatus().equalsIgnoreCase(SituacaoRequisicaoProblema.Planejada.getDescricao())) {
            status = UtilI18N.internacionaliza(request, "citcorpore.comum.planejada");
            return status;
        }
        if (problemaDto.getStatus().equalsIgnoreCase(SituacaoRequisicaoProblema.EmExecucao.getDescricao())) {
            status = UtilI18N.internacionaliza(request, "citcorpore.comum.emExecucao");
            return status;
        }
        if (problemaDto.getStatus().equalsIgnoreCase(SituacaoRequisicaoProblema.Executada.getDescricao())) {
            status = UtilI18N.internacionaliza(request, "perfil.executada");
            return status;
        }
        if (problemaDto.getStatus().equalsIgnoreCase(SituacaoRequisicaoProblema.Suspensa.getDescricao())) {
            status = UtilI18N.internacionaliza(request, "citcorpore.comum.suspensa");
            return status;
        }
        if (problemaDto.getStatus().equalsIgnoreCase(SituacaoRequisicaoProblema.Cancelada.getDescricao())) {
            status = UtilI18N.internacionaliza(request, "citcorpore.comum.cancelada");
            return status;
        }
        if (problemaDto.getStatus().equalsIgnoreCase(SituacaoRequisicaoProblema.Rejeitada.getDescricao())) {
            status = UtilI18N.internacionaliza(request, "citcorpore.comum.rejeitada");
            return status;
        }
        if (problemaDto.getStatus().equalsIgnoreCase(SituacaoRequisicaoProblema.Resolvida.getDescricao())) {
            status = UtilI18N.internacionaliza(request, "citcorpore.comum.resolvida");
            return status;
        }
        if (problemaDto.getStatus().equalsIgnoreCase(SituacaoRequisicaoProblema.Reaberta.getDescricao())) {
            status = UtilI18N.internacionaliza(request, "citcorpore.comum.reaberta");
            return status;
        }
        if (problemaDto.getStatus().equalsIgnoreCase(SituacaoRequisicaoProblema.Concluida.getDescricao())) {
            status = UtilI18N.internacionaliza(request, "citcorpore.comum.concluida");
            return status;
        }
        if (problemaDto.getStatus().equalsIgnoreCase(SituacaoRequisicaoProblema.SolucaoContorno.getDescricao())) {
            status = UtilI18N.internacionaliza(request, "problema.solucao_contorno");
            return status;
        }
        if (problemaDto.getStatus().equalsIgnoreCase(SituacaoRequisicaoProblema.Revisado.getDescricao())) {
            status = UtilI18N.internacionaliza(request, "problema.revisado");
            return status;
        }
        if (problemaDto.getStatus().equalsIgnoreCase(SituacaoRequisicaoProblema.Resolucao.getDescricao())) {
            status = UtilI18N.internacionaliza(request, "pesquisaProblema.resolucao");
            return status;
        }
        if (problemaDto.getStatus().equalsIgnoreCase(SituacaoRequisicaoProblema.Encerramento.getDescricao())) {
            status = UtilI18N.internacionaliza(request, "grupo.encerramento");
            return status;
        }
        if (problemaDto.getStatus().equalsIgnoreCase(SituacaoRequisicaoProblema.Revisar.getDescricao())) {
            status = UtilI18N.internacionaliza(request, "gerenciamentoProblema.revisar");
            return status;
        }
        if (problemaDto.getStatus().equalsIgnoreCase(SituacaoRequisicaoProblema.RegistroErroConhecido.getDescricao())) {
            status = UtilI18N.internacionaliza(request, "problema.registroErroConhecido");
            return status;
        }
        if (problemaDto.getStatus().equalsIgnoreCase(SituacaoProblema.EmAndamento.getDescricao())) {
            status = UtilI18N.internacionaliza(request, "citcorpore.comum.emandamento");
            return status;
        }
        if (problemaDto.getStatus().equalsIgnoreCase(SituacaoProblema.Fechada.getDescricao())) {
            status = UtilI18N.internacionaliza(request, "citcorpore.comum.fechada");
            return status;
        }
        if (problemaDto.getStatus().equalsIgnoreCase(SituacaoProblema.ReClassificada.getDescricao())) {
            status = UtilI18N.internacionaliza(request, "citcorpore.comum.reclassificada");
            return status;
        }
        return null;
    }

}
