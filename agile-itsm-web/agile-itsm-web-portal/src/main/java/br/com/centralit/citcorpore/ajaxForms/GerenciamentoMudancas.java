package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import br.com.centralit.bpm.dto.TarefaFluxoDTO;
import br.com.centralit.bpm.util.Enumerados;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.GerenciamentoMudancasDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ExecucaoMudancaService;
import br.com.centralit.citcorpore.negocio.RequisicaoMudancaService;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({"rawtypes", "unchecked"})
public class GerenciamentoMudancas extends AjaxFormAction {

    @Override
    public Class<GerenciamentoMudancasDTO> getBeanClass() {
        return GerenciamentoMudancasDTO.class;
    }

    @Override
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        this.exibeTarefas(document, request, response);
    }

    /**
     * Exibe as tarefas para o gerenciamento de mudanças
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     */
    public void exibeTarefas(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert("Sessao expirada! Favor efetuar logon novamente!");
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }

        final GerenciamentoMudancasDTO gerenciamentoBean = (GerenciamentoMudancasDTO) document.getBean();

        final ExecucaoMudancaService execucaoMudancaService = (ExecucaoMudancaService) ServiceLocator.getInstance().getService(ExecucaoMudancaService.class, null);
        final List<TarefaFluxoDTO> colTarefas = execucaoMudancaService.recuperaTarefas(usuario.getLogin());
        if (colTarefas == null) {
            return;
        }

        final boolean bFiltroPorSolicitacao = gerenciamentoBean.getIdRequisicaoSel() != null && gerenciamentoBean.getIdRequisicaoSel().length() > 0;
        final List<TarefaFluxoDTO> colTarefasFiltradas = new ArrayList();
        if (!bFiltroPorSolicitacao) {
            colTarefasFiltradas.addAll(colTarefas);
        } else {
            for (final TarefaFluxoDTO tarefaDto : colTarefas) {
                boolean bAdicionar = false;
                final String idRequisicao = "" + ((RequisicaoMudancaDTO) tarefaDto.getRequisicaoMudancaDto()).getIdRequisicaoMudanca();
                bAdicionar = idRequisicao.indexOf(gerenciamentoBean.getIdRequisicaoSel()) >= 0;
                if (bAdicionar) {
                    colTarefasFiltradas.add(tarefaDto);
                }
            }
        }
        final List colTarefasFiltradasFinal = new ArrayList();
        final HashMap mapAtr = new HashMap();
        mapAtr.put("-- Sem Atribuição --", "-- Sem Atribuição --");
        for (final TarefaFluxoDTO tarefaDto : colTarefasFiltradas) {
            final RequisicaoMudancaDTO requisicaoMudancaDto = (RequisicaoMudancaDTO) tarefaDto.getRequisicaoMudancaDto();
            requisicaoMudancaDto.setDataHoraLimiteToString(""); // Apenas forca atualizacao
            requisicaoMudancaDto.setDataHoraInicioToString(""); // Apenas forca atualizacao
            requisicaoMudancaDto.setDescricao("");

            requisicaoMudancaDto.setDataHoraInicioStr(UtilDatas.convertDateToString(TipoDate.TIMESTAMP_DEFAULT, requisicaoMudancaDto.getDataHoraInicio(),
                    WebUtil.getLanguage(request)));
            requisicaoMudancaDto.setDataHoraTerminoStr(UtilDatas.convertDateToString(TipoDate.TIMESTAMP_DEFAULT, requisicaoMudancaDto.getDataHoraTermino(),
                    WebUtil.getLanguage(request)));

            if (requisicaoMudancaDto.getResponsavelAtual() != null) {
                if (!mapAtr.containsKey(requisicaoMudancaDto.getResponsavelAtual())) {
                    mapAtr.put(requisicaoMudancaDto.getResponsavelAtual(), requisicaoMudancaDto.getResponsavelAtual());
                }
            }

            if (gerenciamentoBean.getAtribuidaCompartilhada() == null || gerenciamentoBean.getAtribuidaCompartilhada().trim().equalsIgnoreCase("")) {
                colTarefasFiltradasFinal.add(tarefaDto);
            } else {
                if (gerenciamentoBean.getAtribuidaCompartilhada().trim().equalsIgnoreCase(UtilI18N.internacionaliza(request, "citcorpore.comum.sematribuicao"))) {
                    if (requisicaoMudancaDto.getResponsavelAtual() == null || requisicaoMudancaDto.getResponsavelAtual().trim().equalsIgnoreCase("")) {
                        colTarefasFiltradasFinal.add(tarefaDto);
                    }
                } else {
                    if (gerenciamentoBean.getAtribuidaCompartilhada().trim().equalsIgnoreCase(requisicaoMudancaDto.getResponsavelAtual())) {
                        colTarefasFiltradasFinal.add(tarefaDto);
                    }
                }
            }
        }
        final String tarefasStr = this.serializaTarefas(colTarefasFiltradasFinal, request);

        document.executeScript("exibirTarefas('" + tarefasStr + "');");

        document.getSelectById("atribuidaCompartilhada").removeAllOptions();
        document.getSelectById("atribuidaCompartilhada").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");
        for (final Iterator it = mapAtr.values().iterator(); it.hasNext();) {
            final String str = (String) it.next();
            document.getSelectById("atribuidaCompartilhada").addOption(str, str);
        }
        document.getSelectById("atribuidaCompartilhada").setValue(gerenciamentoBean.getAtribuidaCompartilhada());
    }

    public void preparaExecucaoTarefa(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert("Sessão expirada! Favor efetuar logon novamente!");
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }

        final GerenciamentoMudancasDTO gerenciamentoBean = (GerenciamentoMudancasDTO) document.getBean();
        if (gerenciamentoBean.getIdTarefa() == null) {
            return;
        }

        final ExecucaoMudancaService execucaoMudancaService = (ExecucaoMudancaService) ServiceLocator.getInstance().getService(ExecucaoMudancaService.class, null);
        final TarefaFluxoDTO tarefaDto = execucaoMudancaService.recuperaTarefa(usuario.getLogin(), gerenciamentoBean.getIdTarefa());
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
            if (tarefaDto.getElementoFluxoDto().getUrl().indexOf("?") > -1) {
                caracterParmURL = "&";
            }
            document.executeScript("exibirUrl('Executar tarefa " + tarefaDto.getElementoFluxoDto().getDocumentacao() + "','" + tarefaDto.getElementoFluxoDto().getUrl()
                    + caracterParmURL + "idRequisicaoMudanca=" + ((RequisicaoMudancaDTO) tarefaDto.getRequisicaoMudancaDto()).getIdRequisicaoMudanca() + "&idTarefa="
                    + tarefaDto.getIdItemTrabalho() + "&acaoFluxo=" + gerenciamentoBean.getAcaoFluxo() + "');");
        }
    }

    public void reativaRequisicao(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert("Sessão expirada! Favor efetuar logon novamente!");
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }

        final GerenciamentoMudancasDTO gerenciamentoBean = (GerenciamentoMudancasDTO) document.getBean();
        if (gerenciamentoBean.getIdRequisicao() == null) {
            return;
        }

        final RequisicaoMudancaService requisicaoMudancaService = (RequisicaoMudancaService) ServiceLocator.getInstance().getService(RequisicaoMudancaService.class, null);
        final RequisicaoMudancaDTO requisicaoMudancaDto = requisicaoMudancaService.restoreAll(gerenciamentoBean.getIdRequisicao());
        requisicaoMudancaService.reativa(usuario, requisicaoMudancaDto);
        this.exibeTarefas(document, request, response);
    }

    private String serializaTarefas(final List<TarefaFluxoDTO> colTarefas, final HttpServletRequest request) throws Exception {
        if (colTarefas == null) {
            return null;
        }
        for (final TarefaFluxoDTO tarefaDto : colTarefas) {
            final String elementoFluxo_serialize = StringEscapeUtils.escapeJavaScript(br.com.citframework.util.WebUtil.serializeObject(tarefaDto.getElementoFluxoDto(),
                    WebUtil.getLanguage(request)));
            final String requisicaoMudanca_serialize = StringEscapeUtils.escapeJavaScript(br.com.citframework.util.WebUtil.serializeObject(tarefaDto.getRequisicaoMudancaDto(),
                    WebUtil.getLanguage(request)));

            tarefaDto.setElementoFluxo_serialize(elementoFluxo_serialize);
            tarefaDto.setSolicitacao_serialize(requisicaoMudanca_serialize);
        }
        return br.com.citframework.util.WebUtil.serializeObjects(colTarefas, WebUtil.getLanguage(request));
    }

    public void capturaTarefa(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }

        GerenciamentoMudancasDTO requisicaoBean = (GerenciamentoMudancasDTO) document.getBean();

        if (requisicaoBean.getIdTarefa() == null) {
            return;
        }

        final RequisicaoMudancaService requisicaoMudancaService = (RequisicaoMudancaService) ServiceLocator.getInstance().getService(RequisicaoMudancaService.class, null);

        RequisicaoMudancaDTO requisicaoDto = new RequisicaoMudancaDTO();
        requisicaoDto.setIdRequisicaoMudanca(requisicaoBean.getIdRequisicao());
        requisicaoDto = (RequisicaoMudancaDTO) requisicaoMudancaService.restore(requisicaoDto);

        requisicaoDto.setIdProprietario(usuario.getIdEmpregado());
        requisicaoMudancaService.updateNotNull(requisicaoDto);

        final ExecucaoMudancaService execucaoMudancaService = (ExecucaoMudancaService) ServiceLocator.getInstance().getService(ExecucaoMudancaService.class, null);
        execucaoMudancaService.executa(usuario, requisicaoBean.getIdTarefa(), Enumerados.ACAO_INICIAR);
        this.exibeTarefas(document, request, response);

        requisicaoBean = null;
    }

}
