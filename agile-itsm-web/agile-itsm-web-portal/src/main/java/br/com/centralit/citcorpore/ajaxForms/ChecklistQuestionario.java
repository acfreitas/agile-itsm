package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.ChecklistQuestionarioDTO;
import br.com.centralit.citcorpore.bean.RequisicaoQuestionarioDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.RequisicaoQuestionarioService;
import br.com.centralit.citcorpore.util.CitCorporeConstantes;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citquestionario.bean.QuestionarioDTO;
import br.com.centralit.citquestionario.negocio.QuestionarioService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

@SuppressWarnings({"rawtypes"})
public class ChecklistQuestionario extends AjaxFormAction {

    @Override
    public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        final ChecklistQuestionarioDTO checklistQuestionarioDTO = (ChecklistQuestionarioDTO) document.getBean();
        carregaComboQuest(document, request, response);
        listarRegistrosQuestionario(document, request, response);
        final HTMLForm form = document.getForm("form");
        form.setValues(checklistQuestionarioDTO);
    }

    @Override
    public Class<ChecklistQuestionarioDTO> getBeanClass() {
        return ChecklistQuestionarioDTO.class;
    }

    public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (document.getBean() == null) {
            document.alert(UtilI18N.internacionaliza(request, "MSG05"));
        } else {
            document.alert(UtilI18N.internacionaliza(request, "MSG06"));
        }

        final HTMLForm form = document.getForm("form");
        form.clear();
    }

    public void carregaComboQuest(DocumentHTML document, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        final QuestionarioService questionarioService = (QuestionarioService) ServiceLocator.getInstance().getService(
                QuestionarioService.class, null);

        final HTMLSelect comboQuestionario = document.getSelectById("idQuestionario");
        @SuppressWarnings("unchecked") final ArrayList<QuestionarioDTO> tipos = (ArrayList) questionarioService.list();
        comboQuestionario.removeAllOptions();
        if (tipos != null && tipos.size() > 0) {
            comboQuestionario.addOption("", "-- " + UtilI18N.internacionaliza(request, "alcada.limite.todos") + " --");
            for (final QuestionarioDTO tipo : tipos) {
                comboQuestionario.addOption(tipo.getIdQuestionario().toString(),
                        StringEscapeUtils.escapeJavaScript(tipo.getNomeQuestionario()));
            }
        }
    }

    /**
     * Metodo colocar status Inativo quando for solicitado a exclusão do usuario.
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     */
    public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        final HTMLForm form = document.getForm("form");
        form.clear();
    }

    /**
     * Metodo para restaura os campos.
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     */
    public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        final HTMLForm form = document.getForm("form");
        form.clear();
    }

    public void listarRegistrosQuestionario(DocumentHTML document, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        final ChecklistQuestionarioDTO checklistQuestionarioDTO = (ChecklistQuestionarioDTO) document.getBean();
        final UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert("Sessão expirada! Favor efetuar logon novamente!");
            return;
        }
        String PRONTUARIO_FORMA_EDICAO = "PRONTUARIO_FORMA_EDICAO";
        if (PRONTUARIO_FORMA_EDICAO == null || PRONTUARIO_FORMA_EDICAO.trim().equalsIgnoreCase("")) {
            PRONTUARIO_FORMA_EDICAO = "P";
        }
        QuestionarioDTO questionarioDTO = new QuestionarioDTO();
        final QuestionarioService questionarioService = (QuestionarioService) ServiceLocator.getInstance().getService(
                QuestionarioService.class, null);
        if (checklistQuestionarioDTO.getIdQuestionario() != null) {
            questionarioDTO.setIdQuestionario(checklistQuestionarioDTO.getIdQuestionario());
            questionarioDTO = (QuestionarioDTO) questionarioService.restore(questionarioDTO);
            checklistQuestionarioDTO.setIdQuestionarioOrigem(questionarioDTO.getIdQuestionarioOrigem());
        }
        final RequisicaoQuestionarioService requisicaoQuestionariosService = (RequisicaoQuestionarioService) ServiceLocator
                .getInstance().getService(RequisicaoQuestionarioService.class, null);
        final Collection colQuestHist = requisicaoQuestionariosService
                .listByIdTipoAbaAndTipoRequisicaoAndQuestionario(checklistQuestionarioDTO);

        Integer seqGeral = new Integer(0);
        String abrir = "";
        String abrirUltimo = "";
        String geralData = "";
        final StringBuilder strTable = new StringBuilder();
        strTable.append("");
        final StringBuilder hist_Pront = new StringBuilder();
        if (colQuestHist != null && colQuestHist.size() > 0) {
            strTable.append("<table width=\"100%\" class=\"tableLess\">");
            strTable.append("<thead>");
            strTable.append("<tr>");
            strTable.append("<th width=\"7%\" >");
            strTable.append("&nbsp;");
            strTable.append("</th>");
            strTable.append("<th>");
            strTable.append(UtilI18N.internacionaliza(request, "itemConfiguracaoTree.versao"));
            strTable.append("</th>");
            strTable.append("<th>");
            strTable.append(UtilI18N.internacionaliza(request, "questionario.dataHora"));
            strTable.append("</th>");
            strTable.append("<th>");
            strTable.append(UtilI18N.internacionaliza(request, "menu.nome.questionario"));
            strTable.append("</th>");
            strTable.append("<th  >");
            strTable.append(UtilI18N.internacionaliza(request, "citcorpore.comum.concluida"));
            strTable.append("</th>");
            strTable.append("</tr>");
            strTable.append("</thead>");

            hist_Pront.append("geral_hist_Pront_Quest_Seq = new Array();");
            hist_Pront.append("geral_hist_Pront_Quest = new Array();");
            hist_Pront.append("geral_hist_Pront_Quest = new Array();");

            seqGeral = colQuestHist.size();
            Integer seq = colQuestHist.size();
            int i = colQuestHist.size() - 1;
            for (final Iterator it = colQuestHist.iterator(); it.hasNext();) {
                final RequisicaoQuestionarioDTO reqQuestQuestDTO = (RequisicaoQuestionarioDTO) it.next();

                hist_Pront.append("geral_hist_Pront_Quest_Seq[" + i + "] = '" + seq.toString() + "';");
                hist_Pront.append("geral_hist_Pront_Quest_Data["
                        + i
                        + "] = '"
                        + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, reqQuestQuestDTO.getDataQuestionario(),
                                WebUtil.getLanguage(request)) + "';");

                final String descricao = "";
                final Collection col = null;

                strTable.append("<tr>");

                strTable.append("<td >");
                abrir = "titulo_Selecionado = '" + descricao.replaceAll("'", "") + "';";
                abrir += "try{cit$('divTituloPOPUPQUEST').innerHTML = titulo_Selecionado;}catch(e){};";
                if ("F".equalsIgnoreCase(reqQuestQuestDTO.getSituacao())) {
                    abrir += "abaSelecionada = '" + reqQuestQuestDTO.getAba() + "'; chamaEdicaoQuestionario("
                            + reqQuestQuestDTO.getIdRequisicao() + "," + reqQuestQuestDTO.getIdQuestionario() + ",0, "
                            + reqQuestQuestDTO.getIdRequisicaoQuestionario() + ", true, 'N', '"
                            + reqQuestQuestDTO.getAba() + "," + reqQuestQuestDTO.getNomeQuestionario() + "')";
                    strTable.append("<img title=\""
                            + UtilI18N.internacionaliza(request, "Questionario.questionarioPreenchido")
                            + "\" src=\""
                            + CitCorporeConstantes.CAMINHO_SERVIDOR
                            + request.getContextPath()
                            + "/template_new/images/icons/large/grey/archive.png\" border=\"0\" onclick=\"try{cit$('div_PQ_Indicador').innerHTML = '--';}catch(e){};try{cit$('div_PQ_Data').innerHTML = '"
                            + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT,
                                    reqQuestQuestDTO.getDataQuestionario(), WebUtil.getLanguage(request))
                                    + "';}catch(e){};" + abrir + "\" style=\"cursor:pointer\" >");
                } else {
                    abrir += "abaSelecionada = '" + reqQuestQuestDTO.getAba() + "'; chamaEdicaoQuestionario("
                            + reqQuestQuestDTO.getIdRequisicao() + "," + reqQuestQuestDTO.getIdQuestionario() + ",0, "
                            + reqQuestQuestDTO.getIdRequisicaoQuestionario() + ", false, 'N', '"
                            + reqQuestQuestDTO.getAba() + "," + reqQuestQuestDTO.getNomeQuestionario() + "')";
                    strTable.append("<img title='"
                            + UtilI18N.internacionaliza(request, "Questionario.questionarioPreenchido")
                            + "' src=\""
                            + CitCorporeConstantes.CAMINHO_SERVIDOR
                            + request.getContextPath()
                            + "/template_new/images/icons/large/grey/archive.png\" border=\"0\" onclick=\"try{cit$('div_PQ_Indicador').innerHTML = '--';}catch(e){};try{cit$('div_PQ_Data').innerHTML = '"
                            + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT,
                                    reqQuestQuestDTO.getDataQuestionario(), WebUtil.getLanguage(request))
                                    + "';}catch(e){};" + abrir + "\" style=\"cursor:pointer\" >");
                }

                strTable.append("</td>");

                strTable.append("<td class='tdPontilhada' style=\"text-align:center\">" + seq.toString() + "</td>");
                strTable.append("<td class='tdPontilhada'>");
                strTable.append(UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS,
                        reqQuestQuestDTO.getDataHoraGrav(), WebUtil.getLanguage(request)));
                strTable.append("</td>");
                strTable.append("<td class='tdPontilhada'>");
                strTable.append(UtilStrings.nullToVazio(reqQuestQuestDTO.getNomeQuestionario()) + "&nbsp;");
                strTable.append("</td>");
                strTable.append("<td class='tdPontilhada' style='text-align: center'>");
                if (reqQuestQuestDTO.getConfirmacao() != null
                        && reqQuestQuestDTO.getConfirmacao().equalsIgnoreCase("S")) {
                    strTable.append("<label style='cursor:pointer'><input type='checkbox' value='S' id='confirma' name='confirma' checked onclick='gravaConfirmacao("
                            + reqQuestQuestDTO.getIdRequisicaoQuestionario() + ", \"S\");'/></label><br>");
                } else {
                    strTable.append("<label style='cursor:pointer'><input type='checkbox' value='N' id='confirma' name='confirma' onclick='gravaConfirmacao("
                            + reqQuestQuestDTO.getIdRequisicaoQuestionario() + ", \"N\");'/></label><br>");
                }
                strTable.append("</td>");
                strTable.append("</tr>");
                seq = seq - 1;

                hist_Pront.append("geral_hist_Pront_Quest_Comando[" + i + "] = \"" + abrir + "\";");
                i--;

                if (abrirUltimo.equalsIgnoreCase("")) {
                    abrirUltimo = abrir;
                }
                if (geralData.equalsIgnoreCase("")) {
                    if (checklistQuestionarioDTO.getTipoApresResumo() == null) {
                        checklistQuestionarioDTO.setTipoApresResumo("");
                    }
                    if (!checklistQuestionarioDTO.getTipoApresResumo().equalsIgnoreCase("E")) {
                        hist_Pront.append("geral_data = '"
                                + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT,
                                        reqQuestQuestDTO.getDataQuestionario(), WebUtil.getLanguage(request)) + "';");
                        geralData = "geral_data = '"
                                + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT,
                                        reqQuestQuestDTO.getDataQuestionario(), WebUtil.getLanguage(request)) + "';";
                    }
                }

            }

            strTable.append("</table>");

            if (PRONTUARIO_FORMA_EDICAO.equalsIgnoreCase("A")) {
                document.executeScript(hist_Pront.toString());
                if (checklistQuestionarioDTO.getTipoApresResumo() == null) {
                    checklistQuestionarioDTO.setTipoApresResumo("");
                }
                if (!checklistQuestionarioDTO.getTipoApresResumo().equalsIgnoreCase("E")) {
                    document.executeScript("geral_seq_Pront_Questionario = " + seqGeral.toString());
                    document.executeScript("geral_seq_Sel_Pront_Questionario = (" + seqGeral.toString() + " - 1)");
                }
                if (!abrir.equalsIgnoreCase("")) {
                    checklistQuestionarioDTO.setUltimoComando(abrirUltimo);
                }
            }
            if (checklistQuestionarioDTO.getTipoApresResumo() == null
                    || checklistQuestionarioDTO.getTipoApresResumo().equalsIgnoreCase("J")) {
                document.getElementById("divRegistros").setInnerHTML(strTable.toString());
            } else {
                if (!checklistQuestionarioDTO.getTipoApresResumo().equalsIgnoreCase("*")) {// O * indica para nao apresentar nada
                    document.getElementById("divHistRes_Conteudo").setInnerHTML(strTable.toString());

                    final HTMLForm form = document.getForm("form");
                    form.setValues(checklistQuestionarioDTO);
                }
            }

        }
    }

    public void gravaConfirmacaoQuestionario(DocumentHTML document, HttpServletRequest request,
            HttpServletResponse response) throws ServiceException, Exception {
        final ChecklistQuestionarioDTO checkQuestionario = (ChecklistQuestionarioDTO) document.getBean();
        final RequisicaoQuestionarioService reqQuestionarioService = (RequisicaoQuestionarioService) ServiceLocator
                .getInstance().getService(RequisicaoQuestionarioService.class, null);

        RequisicaoQuestionarioDTO reqQuestionario = new RequisicaoQuestionarioDTO();
        reqQuestionario.setIdRequisicaoQuestionario(checkQuestionario.getIdRequisicaoQuestionario());
        reqQuestionario = (RequisicaoQuestionarioDTO) reqQuestionarioService.restore(reqQuestionario);
        String confirmacao;
        if (reqQuestionario.getConfirmacao() == null || reqQuestionario.getConfirmacao().equalsIgnoreCase("")) {
            confirmacao = "N";
        } else {
            confirmacao = reqQuestionario.getConfirmacao();
        }

        if (confirmacao.equalsIgnoreCase("N")) {
            document.alert(UtilI18N.internacionaliza(request, "requisicaoLiberacao.validaQuestionarioMarcado"));
        } else {
            document.alert(UtilI18N.internacionaliza(request, "requisicaoLiberacao.validaQuestionarioDesmarcado"));
        }
    }

}
