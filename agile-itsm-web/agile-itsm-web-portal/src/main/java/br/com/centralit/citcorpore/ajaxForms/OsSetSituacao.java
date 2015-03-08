package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.AcordoNivelServicoDTO;
import br.com.centralit.citcorpore.bean.AtividadesOSDTO;
import br.com.centralit.citcorpore.bean.AtividadesServicoContratoDTO;
import br.com.centralit.citcorpore.bean.FormulaDTO;
import br.com.centralit.citcorpore.bean.GlosaOSDTO;
import br.com.centralit.citcorpore.bean.GlosaServicoContratoDTO;
import br.com.centralit.citcorpore.bean.OSDTO;
import br.com.centralit.citcorpore.bean.ResultadosEsperadosDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.bean.VinculaOsIncidenteDTO;
import br.com.centralit.citcorpore.metainfo.script.ScriptRhinoJSExecute;
import br.com.centralit.citcorpore.metainfo.util.RuntimeScript;
import br.com.centralit.citcorpore.negocio.AcordoNivelServicoContratoService;
import br.com.centralit.citcorpore.negocio.AcordoNivelServicoService;
import br.com.centralit.citcorpore.negocio.AtividadesOSService;
import br.com.centralit.citcorpore.negocio.AtividadesServicoContratoService;
import br.com.centralit.citcorpore.negocio.FormulaService;
import br.com.centralit.citcorpore.negocio.GlosaOSService;
import br.com.centralit.citcorpore.negocio.GlosaServicoContratoService;
import br.com.centralit.citcorpore.negocio.OSService;
import br.com.centralit.citcorpore.negocio.ServicoContratoService;
import br.com.centralit.citcorpore.negocio.ServicoService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.VinculaOsIncidenteService;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.FormulasUtil;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilFormatacao;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;
import br.com.citframework.util.WebUtil;
@SuppressWarnings({"rawtypes","unused"})
public class OsSetSituacao extends Os {

    @Override
    public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        OSDTO os = (OSDTO) document.getBean();
        OSService osService = (OSService) ServiceLocator.getInstance().getService(OSService.class, null);
        AtividadesOSService atividadesOSService = (AtividadesOSService) ServiceLocator.getInstance().getService(AtividadesOSService.class, null);
        GlosaOSService glosaOSService = (GlosaOSService) ServiceLocator.getInstance().getService(GlosaOSService.class, null);

        os = (OSDTO)osService.restore(os);

        HTMLForm form = document.getForm("form");
        form.clear();
        form.setValues(os);

        document.executeScript("GRID_ITENS.deleteAllRows()");
        document.executeScript("GRID_GLOSAS.deleteAllRows()");

        Collection col = null;
        Collection colGlosas = null;

        Double custoTotalPrevisto = 0.0;
        Double custoTotalExecutado = 0.0;

        if (os != null){
            int i = 0;
            col = atividadesOSService.findByIdOS(os.getIdOS());
            if (col != null){
                for(Iterator it = col.iterator(); it.hasNext();){
                    i++;
                    AtividadesOSDTO atividadesOSDTO = (AtividadesOSDTO)it.next();
                    document.executeScript("GRID_ITENS.addRow()");
                    document.executeScript("seqSelecionada = NumberUtil.zerosAEsquerda(GRID_ITENS.getMaxIndex(),5)");
                    String strQtde = UtilFormatacao.formatDouble(atividadesOSDTO.getCustoAtividade(), 2);
                    String strGlosa = "";
                    String strFormula = atividadesOSDTO.getFormula();
                    String exibirBotao = "";
                    String strIdServicoContratoContabil = "";
                    StringBuilder strBtnServicoContabil = new StringBuilder();
                    StringBuilder strPopUpAssociacao = new StringBuilder();

                    if (atividadesOSDTO.getGlosaAtividade() != null){
                        strGlosa = UtilFormatacao.formatDouble(atividadesOSDTO.getGlosaAtividade(), 2);
                    }
                    String strQtdeExec = "";
                    if (atividadesOSDTO.getQtdeExecutada() != null){
                        strQtdeExec = UtilFormatacao.formatDouble(atividadesOSDTO.getQtdeExecutada(), 2);
                    }
                    String strDet = atividadesOSDTO.getDescricaoAtividade();
                    String strObs = atividadesOSDTO.getObsAtividade();
                    if (strDet != null){
                        strDet = strDet.replaceAll("'", "");
                    }else{
                        strDet = "";
                    }
                    if (strObs != null){
                        strObs = strObs.replaceAll("'", "");
                    }else{
                        strObs = "";
                    }

                    if (atividadesOSDTO.getCustoAtividade() != null) {
                        custoTotalPrevisto = custoTotalPrevisto + atividadesOSDTO.getCustoAtividade().doubleValue();
                        os.setTotalUstPrevista(custoTotalPrevisto);
                    }

                    if(os.getSituacaoOS().equals(OSDTO.EM_EXECUCAO) && strQtdeExec.equals("")){
                        strQtdeExec = strQtde;
                    }

                    if(atividadesOSDTO.getContabilizar() != null && atividadesOSDTO.getContabilizar().equalsIgnoreCase("S")){
                        ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);

                        Integer idServicoContratoContabil = atividadesOSDTO.getIdServicoContratoContabil();
                        if(idServicoContratoContabil != null){
                            strIdServicoContratoContabil = idServicoContratoContabil.toString();
                        }
                        if(idServicoContratoContabil != null){
                            document.getElementById("idServicoContratoContabil").setValue(strIdServicoContratoContabil);
                            ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
                            ServicoDTO servicoDTO = new ServicoDTO();
                            servicoDTO.setIdServico(idServicoContratoContabil);
                            servicoDTO = (ServicoDTO) servicoService.restore(servicoDTO);
                            if(servicoDTO != null){
                                strBtnServicoContabil.append("<input type='hidden' name='servicoContrato"+i+"'/>")
                                .append("<div id='divServicoContrato"+i+"' style='width: 600px; height: 37px;'>")
                                .append("<button type='button' name='btnAddIncidentes' style='margin-top: 3px;' class='light img_icon has_text clsAddIncidente' onclick='mostrarIncidentesParaAssociar("+idServicoContratoContabil+")'>")
                                .append("<img src='"+ Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() +"/template_new/images/icons/small/grey/document.png'>")
                                .append("<span>"+ UtilI18N.internacionaliza(request, "citcorpore.comum.associarIncidente") +"</span>")
                                .append("</button>")
                                .append("<div id='nomeServicoContratoContabil' style='font-weight: bold; padding-top: 2px;'>"+UtilStrings.nullToVazio(servicoDTO.getNomeServico())+"</div>")
                                .append("</div>");

                                exibirBotao = "true";
                            }
                        }
                    }

                    document.executeScript("setaRestoreItem('" + atividadesOSDTO.getComplexidade().trim() + "'," +
                            "'" + br.com.citframework.util.WebUtil.codificaEnter(strDet) + "'," +
                            "'" + br.com.citframework.util.WebUtil.codificaEnter(strObs) + "'," +
                            "'" + UtilStrings.nullToVazio(strFormula) + "'," +
                            "'" + UtilStrings.nullToVazio(strQtde) + "'," +
                            "'" + UtilStrings.nullToVazio(strGlosa) + "'," +
                            "'" + UtilStrings.nullToVazio(strQtdeExec) + "'," +
                            "'" + atividadesOSDTO.getIdAtividadesOS() + "'," +
                            "'" + UtilStrings.nullToVazio(exibirBotao) + "'," +
                            "'" + UtilStrings.nullToVazio(strIdServicoContratoContabil) + "'," +
                            "'" + StringEscapeUtils.escapeJavaScript(UtilStrings.nullToVazio(strBtnServicoContabil.toString())) + "'" +
                            ")");
                }

                document.executeScript("preencheNumeracaoItens()");
                document.executeScript("DEFINEALLPAGES_generateConfiguracaoCampos()");

            }else{

            	document.alert(UtilI18N.internacionaliza(request, "os.nenhumaAtividadeVinculada"));
            }

            document.getElementById("custoTotalPrevisto").setInnerHTML("<b>" + UtilFormatacao.formatDouble(custoTotalPrevisto, 2) + "</b>");
            document.getElementById("executadoOS").setValue(UtilFormatacao.formatDouble(custoTotalExecutado, 2));

            //Carrega as glosas
            i = 0;
            colGlosas = glosaOSService.findByIdOs(os.getIdOS());

            Double custoTotalGlosa = 0.0;

            if (colGlosas != null){
                for(Iterator it = colGlosas.iterator(); it.hasNext();){
                    i++;
                    GlosaOSDTO glosaOSDTO = (GlosaOSDTO) it.next();
                    document.executeScript("GRID_GLOSAS.addRow()");
                    document.executeScript("seqSelecionadaGlosa = NumberUtil.zerosAEsquerda(GRID_GLOSAS.getMaxIndex(),5)");
                    String strPerc = UtilFormatacao.formatDouble(glosaOSDTO.getPercAplicado(), 2);
                    String strGlosa = "";
                    if (glosaOSDTO.getCustoGlosa() != null){
                        strGlosa = UtilFormatacao.formatDouble(glosaOSDTO.getCustoGlosa(), 2);
                    }
                    //Setando valores vazios caso seja null para evitar erro no Oracle
                    String strDet = glosaOSDTO.getDescricaoGlosa();
                    if(strDet == null){
                        strDet = "";
                    }
                    //Setando valores vazios caso seja null para evitar erro no Oracle
                    String strObs = glosaOSDTO.getOcorrencias();
                    if(strObs == null){
                        strObs = "";
                    }
                    //Removendo aspas simples
                    if (strDet != null){
                        strDet = strDet.replaceAll("'", "");
                    }
                    //Removendo aspas simples
                    if (strObs != null){
                        strObs = strObs.replaceAll("'", "");
                    }
                    String numOc = "";
                    if (glosaOSDTO.getNumeroOcorrencias() != null){
                        numOc = "" + glosaOSDTO.getNumeroOcorrencias().intValue();
                    }

                    if(glosaOSDTO.getCustoGlosa() != null){
                        custoTotalGlosa = custoTotalGlosa + glosaOSDTO.getCustoGlosa().doubleValue();
                        os.setTotalglosasAtividades(custoTotalGlosa);
                    }

                    document.executeScript("setaRestoreItemGlosa('" + glosaOSDTO.getIdGlosaOS() + "'," +
                            "'" + br.com.citframework.util.WebUtil.codificaEnter(strDet) + "'," +
                            "'" + br.com.citframework.util.WebUtil.codificaEnter(strObs) + "'," +
                            "'" + numOc + "'," +
                            "'" + UtilStrings.nullToVazio(strPerc) + "'," +
                            "'" + UtilStrings.nullToVazio(strGlosa) + "'" +
                            ")");
                }

                document.getElementById("custoTotalGlosa").setInnerHTML("<b>" + UtilFormatacao.formatDouble(custoTotalGlosa, 2) + "</b>");

            }
        }

        document.getForm("form").lockForm();

        if (os != null){
            if (os.getSituacaoOS().intValue() == OSDTO.EM_EXECUCAO){  //somente se em execucao.
                int i = 0;
                if (col != null){
                    i = 0;
                    for(Iterator it = col.iterator(); it.hasNext();){
                        i++;
                        AtividadesOSDTO atividadesOSDTO = (AtividadesOSDTO)it.next();
                        String objHtml = "document.form.qtdeExecutada" + UtilFormatacao.formatInt(i, "00000");
                        document.executeScript(objHtml + ".disabled = false");
                        objHtml = "document.form.idServicoContratoContabil" + UtilFormatacao.formatInt(i, "00000");
                        document.executeScript(objHtml + ".disabled = false");
                    }
                    String objHtml = "document.form.quantidade";
                    document.executeScript(objHtml + ".disabled = false");
                    document.executeScript(objHtml + ".style.width = '40px !important;'");
                }
                if (colGlosas != null){
                    i = 0;
                    for(Iterator it = colGlosas.iterator(); it.hasNext();){
                        i++;
                        GlosaOSDTO glosaOSDTO = (GlosaOSDTO)it.next();
                        String objHtml = "document.form.idGlosaOS" + UtilFormatacao.formatInt(i, "00000");
                        document.executeScript(objHtml + ".disabled = false");
                        objHtml = "document.form.descricaoGlosa" + UtilFormatacao.formatInt(i, "00000");
                        document.executeScript(objHtml + ".disabled = false");
                        objHtml = "document.form.numeroOcorrencias" + UtilFormatacao.formatInt(i, "00000");
                        document.executeScript(objHtml + ".disabled = false");
                        objHtml = "document.form.ocorrencias" + UtilFormatacao.formatInt(i, "00000");
                        document.executeScript(objHtml + ".disabled = false");
                        objHtml = "document.form.percAplicado" + UtilFormatacao.formatInt(i, "00000");
                        document.executeScript(objHtml + ".disabled = false");
                        objHtml = "document.form.custoGlosa" + UtilFormatacao.formatInt(i, "00000");
                        document.executeScript(objHtml + ".readOnly = true");
                    }
                }

                document.getElementById("divBotaoGlosaOS").setVisible(true);
                document.getElementById("btnAddGlosaOS").setDisabled(false);
                document.executeScript("unlockGlosas()");
            }

            if (os.getSituacaoOS().equals(OSDTO.APROVADA)){  //somente se liberado para homologacao.
                int i = 0;
                if (col != null){
                    i = 0;
                    for(Iterator it = col.iterator(); it.hasNext();){
                        i++;
                        AtividadesOSDTO atividadesOSDTO = (AtividadesOSDTO)it.next();
                        String objHtml = "document.form.glosa" + UtilFormatacao.formatInt(i, "00000");
                        document.executeScript(objHtml + ".disabled = false");
                        objHtml = "document.form.qtdeExecutada" + UtilFormatacao.formatInt(i, "00000");
                        document.executeScript(objHtml + ".disabled = false");
                    }
                }
                if (colGlosas != null){
                    i = 0;
                    for(Iterator it = colGlosas.iterator(); it.hasNext();){
                        i++;
                        GlosaOSDTO glosaOSDTO = (GlosaOSDTO)it.next();
                        String objHtml = "document.form.idGlosaOS" + UtilFormatacao.formatInt(i, "00000");
                        document.executeScript(objHtml + ".disabled = false");
                        objHtml = "document.form.descricaoGlosa" + UtilFormatacao.formatInt(i, "00000");
                        document.executeScript(objHtml + ".disabled = false");
                        objHtml = "document.form.numeroOcorrencias" + UtilFormatacao.formatInt(i, "00000");
                        document.executeScript(objHtml + ".disabled = false");
                        objHtml = "document.form.ocorrencias" + UtilFormatacao.formatInt(i, "00000");
                        document.executeScript(objHtml + ".disabled = false");
                        objHtml = "document.form.percAplicado" + UtilFormatacao.formatInt(i, "00000");
                        document.executeScript(objHtml + ".disabled = false");
                        objHtml = "document.form.custoGlosa" + UtilFormatacao.formatInt(i, "00000");
                        document.executeScript(objHtml + ".readOnly = true");
                    }
                }

                document.getElementById("divBotaoGlosaOS").setVisible(true);
                document.getElementById("btnAddGlosaOS").setDisabled(false);
                document.executeScript("unlockGlosas()");
            }

            if (os.getSituacaoOS().intValue() == OSDTO.APROVADA ||
                    os.getSituacaoOS().intValue() == OSDTO.EXECUTADA ||
                    os.getSituacaoOS().intValue() == OSDTO.EM_EXECUCAO){
                document.getElementById("divGlosas").setVisible(true);
            }
        }
        document.getSelectById("situacaoOS").setDisabled(false);
        document.getElementById("btnGravar").setDisabled(false);
        document.getElementById("idOS").setDisabled(false);
        document.getElementById("idOSPai").setDisabled(false);
        document.getElementById("seqANS").setDisabled(false);
        document.getElementById("fieldANS").setDisabled(false);
        document.getElementById("idContrato").setDisabled(false);

        if (os != null && os.getSituacaoOS() != null && os.getSituacaoOS().intValue() != OSDTO.EXECUTADA){
            document.executeScript("HTMLUtils.unlockField('obsFinalizacao')");
            document.executeScript("desabilitaObsFinal()");
        }

        if(os != null && os.getSituacaoOS() != null && os.getSituacaoOS().intValue() == OSDTO.EXECUTADA){
            document.getElementById("flagGlosa").setValue("S");
            //document.executeScript("$('#btnAssociarIncidente').attr('disabled',true)");
        }else{
            document.getElementById("flagGlosa").setValue("N");
        }

        lockAllFields(document);

        document.getElementById("colItens_Serialize").setDisabled(false);
        document.getElementById("colGlosas_Serialize").setDisabled(false);
        document.getElementById("colQtdExec_Serialize").setDisabled(false);

        document.getElementById("executadoOS").setDisabled(false);
        document.getElementById("executadoOS").setReadonly(true);
        document.getElementById("flagGlosa").setDisabled(false);
        document.getElementById("flagGlosa").setReadonly(true);
        document.getElementById("idServicoContratoContabil").setDisabled(false);
        document.getElementById("idServicoContratoContabil").setReadonly(true);

        document.executeScript("$('.clsAddIncidente').attr('disabled',false)");
        document.executeScript("serealizaCustoExecutado()");

        if(colGlosas == null){
            preencheItensGlosa(document, request, response);
        }

    }

    public void atualizaTotalExecutado(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{

        Collection colItens = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(AtividadesOSDTO.class, "colQtdExec_Serialize", request);

        Double qtdExecutadaTotal = 0.0;

        if (colItens != null){
            for(Iterator it = colItens.iterator(); it.hasNext();){
                AtividadesOSDTO atividadesOSDTO = (AtividadesOSDTO)it.next();
                AtividadesOSDTO atividadesOSAux = new AtividadesOSDTO();
                Double qtdExecutada = atividadesOSDTO.getQtdeExecutada();
                if(qtdExecutada != null){
                    qtdExecutadaTotal = qtdExecutadaTotal + qtdExecutada.doubleValue();
                }
            }
        }

        document.getElementById("executadoOS").setValue("");

        document.getElementById("executadoOS").setValue(UtilFormatacao.formatDouble(qtdExecutadaTotal, 2));


    }

    public void preencheItensGlosa(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{

        OSDTO os = (OSDTO) document.getBean();
        OSService osService = (OSService) ServiceLocator.getInstance().getService(OSService.class, null);
        AcordoNivelServicoContratoService acordoNivelServicoContratoService = (AcordoNivelServicoContratoService) ServiceLocator.getInstance().getService(AcordoNivelServicoContratoService.class, null);

        os = (OSDTO)osService.restore(os);

        if (os!= null && os.getIdServicoContrato() == null) {
            document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
            return;
        }

        ResultadosEsperadosDTO resultadosEspedadosDTO = new ResultadosEsperadosDTO();
        if(os != null){
            resultadosEspedadosDTO.setIdServicoContrato(os.getIdServicoContrato());
        }

        Collection col = null;

        document.executeScript("GRID_GLOSAS.deleteAllRows()");

        if (os != null) {
            int i = 0;
            col = acordoNivelServicoContratoService.consultaResultadosEsperados(resultadosEspedadosDTO);
            if (col != null && col.size() > 0) {
                for (Iterator it = col.iterator(); it.hasNext();) {
                    i++;
                    ResultadosEsperadosDTO resultadosEsperadosDTO = (ResultadosEsperadosDTO) it.next();
                    document.executeScript("GRID_GLOSAS.addRow()");
                    document.executeScript("seqSelecionada = NumberUtil.zerosAEsquerda(GRID_GLOSAS.getMaxIndex(),5)");
                    String strDesc = resultadosEsperadosDTO.getDescricaoResultados();
                    if (strDesc != null) {
                        strDesc = strDesc.replaceAll("'", "");
                    }else{
                        strDesc = "";
                    }
                    document.executeScript("setaGlosaItem('" + br.com.citframework.util.WebUtil.codificaEnter(strDesc) + "'" + ")");
                }
            }else{
            	document.alert(UtilI18N.internacionaliza(request, "os.nenhumResultadoEsperado"));
            }
        }

    }

    public void listaIncidentesParaVincular(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{

        OSDTO os = (OSDTO) document.getBean();

        OSService osService = (OSService) ServiceLocator.getInstance().getService(OSService.class, null);
        SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
        VinculaOsIncidenteService vinculaOsIncidenteService = (VinculaOsIncidenteService) ServiceLocator.getInstance().getService(VinculaOsIncidenteService.class, null);

        Collection<SolicitacaoServicoDTO> listSolicitacaoServico = null;

        Integer idServicoContratoContabil = os.getIdServicoContratoContabil();
        if(idServicoContratoContabil != null){
            listSolicitacaoServico = solicitacaoServicoService.listaSolicitacaoServicoPorServicoContrato(idServicoContratoContabil);
        }

        boolean checked = false;
        boolean disabled = true;

        String strTable = "<table width='98%' id='tableIncidentes' >";
        strTable += "<thead>";
        strTable += "<tr>";
        strTable += "<td style='border: 1px solid black' class='linhaSubtituloGrid'>";
        strTable += "&nbsp;";
        strTable += "</td>";
        strTable += "<td style='border: 1px solid black' class='linhaSubtituloGrid'>";
        strTable += UtilI18N.internacionaliza(request, "solicitacaoServico.numerosolicitacao");
        strTable += "</td>";
        strTable += "<td style='border: 1px solid black' class='linhaSubtituloGrid'>";
        strTable += UtilI18N.internacionaliza(request, "solicitacaoServico.descricao");
        strTable += "</td>";
        strTable += "</tr>";
        strTable += "</thead>";
        strTable += "<tbody>";
        if(listSolicitacaoServico != null){
            int count = 1;
            for (SolicitacaoServicoDTO solicitacaoServicoDTO : listSolicitacaoServico) {
                boolean flag = vinculaOsIncidenteService.verificaServicoJaVinculado(os.getIdOS(), solicitacaoServicoDTO.getIdSolicitacaoServico());
                if(flag){
                    continue;
                }else{
                    checked = vinculaOsIncidenteService.verificaServicoSelecionado(solicitacaoServicoDTO.getIdSolicitacaoServico());
                    os = (OSDTO) osService.restore(os);
                    if(OSDTO.EM_EXECUCAO.equals(os.getSituacaoOS())){
                        disabled = false;
                    }
                    strTable += "<tr style='border-bottom: 1px solid black; border-right: 1px solid black;'>";
                    strTable += "<td style='border-left: 1px solid black;'>";
                    strTable += "<input type='checkbox' "+ (checked ? "checked='checked'" : "" ) + (disabled ? "disabled='true'":"")+" name='idSolicitacaoServico' value='" + solicitacaoServicoDTO.getIdSolicitacaoServico() + "' id='idSolicitacaoServico"+count+"' />";
                    strTable += "</td>";
                    strTable += "<td style='font-weight: bold;border-left: 1px solid black;padding-left: 5px;'>";
                    strTable += " " + solicitacaoServicoDTO.getIdSolicitacaoServico();
                    strTable += "</td>";
                    strTable += "<td style='font-weight: bold;border-left: 1px solid black;padding-left: 5px;'>";
                    strTable += " " + solicitacaoServicoDTO.getDescricao();
                    strTable += "</td>";
                    strTable += "</tr>";
                    count++;
                }
            }
        } else {
            strTable += "<tr>";
            strTable += "<td colspan='20'>";
            strTable += UtilI18N.internacionaliza(request, "citcorpore.comum.naoHaIncidenteParaAssociar");
            strTable += "</td>";
            strTable += "</tr>";
        }
        strTable += "</tbody>";
        strTable += "</table>";

        StringBuilder strFormAssociar = new StringBuilder();
        strFormAssociar.append("<input type='hidden' name='idOS'/>")
        .append("<input type='hidden' name='idServicoContratoContabil'/>")
        .append("<input type='hidden' name='incidentesSerializadas' id='incidentesSerializadas'/>")
        .append("<div id='divOsSelecao' style='height: 75%; width: 770px; overflow: auto;'>"+UtilStrings.nullToVazio(strTable)+"</div>")
        .append("<table>")
        .append("<tr>")
        .append("<td>")
        .append("<input type='button' name='btnAssociarIncidente' value='Salvar Associação' onclick='associarIncidentes()'/>")
        .append("</td>")
        .append("<td>")
        .append("&nbsp;")
        .append("</td>")
        .append("</tr>")
        .append("</table>");

        document.executeScript("$('#POPUP_LISTA_INCIDENTES').dialog('open');");
        document.getElementById("conteudoPopUp").setInnerHTML(UtilStrings.nullToVazio(strFormAssociar.toString()));
    }

    @SuppressWarnings("unchecked")
    public void associarOSIncidente(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{

        OSDTO os = (OSDTO) document.getBean();
        int count = 0;

        Integer idOS = os.getIdOS();
        Integer idServicoContratoContabil = os.getIdServicoContratoContabil();
        Integer idAtividadesOSDTO = null;

        VinculaOsIncidenteService vinculaOsIncidenteService = (VinculaOsIncidenteService) ServiceLocator.getInstance().getService(VinculaOsIncidenteService.class, null);
        AtividadesOSService atividadesOSService = (AtividadesOSService) ServiceLocator.getInstance().getService(AtividadesOSService.class, null);
        List<VinculaOsIncidenteDTO> listaIncidentes = (List<VinculaOsIncidenteDTO>) WebUtil.deserializeCollectionFromRequest(VinculaOsIncidenteDTO.class, "incidentesSerializadas", request);

        Collection<AtividadesOSDTO> listaAtividades = atividadesOSService.findByIdOsServicoContratoContabil(idOS, idServicoContratoContabil);
        if(listaAtividades != null){
            for (AtividadesOSDTO atividadesOSDTO : listaAtividades) {
                idAtividadesOSDTO = atividadesOSDTO.getIdAtividadesOS();
            }
        }

        vinculaOsIncidenteService.deleteByIdAtividadeOS(idAtividadesOSDTO);
        if(listaIncidentes != null && idOS != null && idAtividadesOSDTO != null){
            for (VinculaOsIncidenteDTO vinculaOsIncidenteDTO : listaIncidentes) {
                vinculaOsIncidenteDTO.setIdOS(idOS);
                Collection<AtividadesOSDTO> listAtividadesOs = atividadesOSService.findByIdOsServicoContratoContabil(idOS, os.getIdServicoContratoContabil());
                if(listAtividadesOs != null){
                    for (AtividadesOSDTO atividadesOSDTO : listAtividadesOs) {
                        vinculaOsIncidenteDTO.setIdAtividadesOS(atividadesOSDTO.getIdAtividadesOS());
                    }
                }
                vinculaOsIncidenteService.create(vinculaOsIncidenteDTO);
                count++;
            }
            if(count > 0){
                document.alert(count + " Incidente(s) associado(s) com sucesso! \nAtenção! Os valores serão atualizados somente após a gravação da OS!");
            }
        }
        document.executeScript("$('#POPUP_LISTA_INCIDENTES').dialog('close');");
    }

    private void lockAllFields(DocumentHTML document) throws Exception {
        document.getElementById("dataInicio").setReadonly(true);
        document.getElementById("dataFim").setReadonly(true);
        document.getElementById("quantidade").setReadonly(true);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
        OSDTO os = (OSDTO) document.getBean();
        OSService osService = (OSService) ServiceLocator.getInstance().getService(OSService.class, null);
        VinculaOsIncidenteService vinculaOsIncidenteService = (VinculaOsIncidenteService) ServiceLocator.getInstance().getService(VinculaOsIncidenteService.class, null);
        AtividadesServicoContratoService atividadesServicoContratoService = (AtividadesServicoContratoService) ServiceLocator.getInstance().getService(AtividadesServicoContratoService.class, null);
        AtividadesOSService atividadesOSService = (AtividadesOSService) ServiceLocator.getInstance().getService(AtividadesOSService.class, null);
        Collection colGlosas = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(GlosaOSDTO.class, "colGlosas_Serialize", request);
        Collection colItens = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(AtividadesOSDTO.class, "colItens_Serialize", request);

        //os = (OSDTO)osService.restore(os);

        String permiteValorZeroAtv = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.OS_VALOR_ZERO, "N");
        if (permiteValorZeroAtv == null){
            permiteValorZeroAtv = "N";
        }

        Integer idContrato = -1;
        try{
            idContrato = Integer.parseInt((String)request.getSession(true).getAttribute("NUMERO_CONTRATO_EDICAO"));
        }catch (Exception e) {
        }
        if (idContrato == -1){
            document.alert("Não foi possível identificar o contrato, por favor, feche esta tela e faça logon novamente!");
            return;
        }
        if (os.getIdOS()==null || os.getIdOS().intValue()==0){
            document.alert("Não foi possível identificar a OS, por favor, feche esta tela e faça logon novamente!");
            return;
        }

        Double custoGlosaTotal = 0.0;
        Double custoTotalExecutado = os.getExecutadoOS();
        Double glosaAtividade = 0.0;
        Double glosaPercAplicadoTotal = 0.0;

        if (colItens != null){
            for(Iterator it = colItens.iterator(); it.hasNext();){

                glosaAtividade = 0.0;
                custoGlosaTotal = 0.0;
                glosaPercAplicadoTotal = 0.0;

                AtividadesOSDTO atividadesOSDTO = (AtividadesOSDTO)it.next();
                AtividadesOSDTO atividadesOSAux = new AtividadesOSDTO();
                atividadesOSAux.setIdAtividadesOS(atividadesOSDTO.getIdAtividadesOS());

                //Multiplica o valor da atividade pelo número de incidentes associados, caso exista.
                Collection<VinculaOsIncidenteDTO> listaAtividadesOSDTOs = vinculaOsIncidenteService.findByIdAtividadeOS(atividadesOSDTO.getIdAtividadesOS());

                if(listaAtividadesOSDTOs != null && listaAtividadesOSDTOs.size() > 0){
                    AtividadesOSDTO atividadesOSDTO2 = new AtividadesOSDTO();
                    atividadesOSDTO2 = (AtividadesOSDTO) atividadesOSService.restore(atividadesOSAux);

                    if(atividadesOSDTO2 != null){
                        AtividadesServicoContratoDTO atividadesServicoContratoDTO = new AtividadesServicoContratoDTO();
                        atividadesServicoContratoDTO.setIdAtividadeServicoContrato(atividadesOSDTO2.getIdAtividadeServicoContrato());
                        atividadesServicoContratoDTO = (AtividadesServicoContratoDTO) atividadesServicoContratoService.restore(atividadesServicoContratoDTO);

                        if(atividadesServicoContratoDTO != null){
                            atividadesOSDTO.setCustoAtividade(atividadesServicoContratoDTO.getCustoAtividade());
                            int count = listaAtividadesOSDTOs.size();

                            atividadesOSDTO.setCustoAtividade((atividadesOSDTO.getCustoAtividade() * count) * os.getQuantidade());
                        }
                    }
                }

                if(colGlosas != null && !colGlosas.isEmpty()){
                    for (Iterator it2 = colGlosas.iterator(); it2.hasNext();) {
                        GlosaOSDTO glosaOSDTO = (GlosaOSDTO) it2.next();
                        if(glosaOSDTO.getPercAplicado() != null && custoTotalExecutado != null){
                            Double custoGlosa = (glosaOSDTO.getPercAplicado() * custoTotalExecutado) / 100;
                            glosaOSDTO.setCustoGlosa(custoGlosa);
                            custoGlosaTotal = custoGlosaTotal + glosaOSDTO.getCustoGlosa();
                            glosaPercAplicadoTotal = glosaPercAplicadoTotal + glosaOSDTO.getPercAplicado();
                        }
                    }
                    //Rateia o valor total da glosa pelo número de atividades
                    if(atividadesOSDTO.getQtdeExecutada()!=null){
                        glosaAtividade = (glosaPercAplicadoTotal * atividadesOSDTO.getQtdeExecutada()) / 100;
                    }

                    //if(custoGlosaTotal != null && custoGlosaTotal > 0.0){
                    //glosaAtividade = custoGlosaTotal / colItens.size();
                    //}
                }

                if (os.getSituacaoOS() != null && (os.getSituacaoOS().equals(OSDTO.APROVADA) || os.getSituacaoOS().equals(OSDTO.EXECUTADA))){
                    if (!permiteValorZeroAtv.equalsIgnoreCase("S")){
                        if (atividadesOSDTO.getQtdeExecutada() == null){
                            document.alert("Qtde executada de todas atividades deve ser preenchida!");
                            return;
                        }
                        if (atividadesOSDTO.getQtdeExecutada().doubleValue() <= 0){
                            document.alert("Qtde executada de todas atividades deve ser preenchida!");
                            return;
                        }
                    }
                }

                atividadesOSDTO.setGlosaAtividade(glosaAtividade);
            }
        }

        try {
            if(os.getSituacaoOS().equals(OSDTO.EM_EXECUCAO)){
                osService.updateSituacao(os, colGlosas, colItens);
            }else{
                osService.updateSituacao(os.getIdOS(), os.getSituacaoOS(), colGlosas, colItens, os.getObsFinalizacao());

                //Verifica se a OS está em condição de registrar uma contagem de glosa
                String flag = os.getFlagGlosa().toString();
                if(os.getSituacaoOS().equals(OSDTO.EXECUTADA) && (flag!=null && flag.equalsIgnoreCase("N"))){
                    //Chama conta glosa se houver alguma ocorrência de glosa
                    if(colGlosas != null && colGlosas.size() > 0){
                        contaServicoGlosa(os, colGlosas, document);
                    }
                }
            }

            document.alert("Registro atualizado com sucesso!");
            document.executeScript("parent.atualizaOSs()");
            document.executeScript("parent.fecharVisao()");
        } catch (Exception e) {
            document.executeScript("alert('" + UtilI18N.internacionaliza(request, "citcorpore.comum.erroGravacao") + "');");
        }
    }


    private void contaServicoGlosa(OSDTO os, Collection colGlosas, DocumentHTML document) throws ServiceException, Exception{
        if(colGlosas != null && colGlosas.size() > 0){
            GlosaServicoContratoService glosaService = (GlosaServicoContratoService) ServiceLocator.getInstance().getService(GlosaServicoContratoService.class, null);
            OSService osService = (OSService) ServiceLocator.getInstance().getService(OSService.class, null);

            OSDTO osDto = (OSDTO)osService.restore(os);

            Integer quantidade = glosaService.quantidadeGlosaServico(osDto.getIdServicoContrato());

            GlosaServicoContratoDTO glosaServicoContratoDTO = new GlosaServicoContratoDTO();

            boolean flag = false;
            for (Iterator it = colGlosas.iterator(); it.hasNext();){
                GlosaOSDTO glosa = (GlosaOSDTO) it.next();
                Double percentual = glosa.getPercAplicado();
                if(percentual > 0.0){
                    flag = true;
                }
            }
            //Conta glosa se houver algum percentual acima de 0,0%
            if(flag){
                if(quantidade != null && quantidade > 0){
                    quantidade = quantidade + 1;
                    glosaServicoContratoDTO.setQuantidadeGlosa(quantidade);
                    glosaServicoContratoDTO.setIdServicoContrato(osDto.getIdServicoContrato());
                    glosaService.atualizaQuantidadeGlosa(quantidade, osDto.getIdServicoContrato());
                }else{
                    glosaServicoContratoDTO.setQuantidadeGlosa(1);
                    glosaServicoContratoDTO.setIdServicoContrato(osDto.getIdServicoContrato());
                    glosaService.create(glosaServicoContratoDTO);
                    quantidade = 1;
                }
            }
        }
    }

    public void calculaValorTotalAtividade(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        OSDTO os = (OSDTO) document.getBean();

        if(os != null && os.getQuantidade() != null && os.getQuantidade() > 0){
            Collection colItens = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(AtividadesOSDTO.class, "colItens_Serialize", request);
            OSService osService =  (OSService) ServiceLocator.getInstance().getService(OSService.class, null);
            osService.retornaAtividadeCadastradaByPai(os);

            if (colItens != null){
                int i = 0;
                document.executeScript("GRID_ITENS.deleteAllRows();");
                for(Iterator it = colItens.iterator(); it.hasNext();){
                    i++;
                    AtividadesOSDTO atividadesOSDTO = (AtividadesOSDTO)it.next();

                    document.executeScript("GRID_ITENS.addRow()");
                    document.executeScript("seqSelecionada = NumberUtil.zerosAEsquerda(GRID_ITENS.getMaxIndex(),5)");
                    String strQtde = "";
                    if(os.getColItens() != null){
                        for(Iterator itItens = os.getColItens().iterator(); itItens.hasNext();){
                            AtividadesOSDTO atividadesOSPaiDTO = (AtividadesOSDTO)itItens.next();
                            if(atividadesOSPaiDTO.getDescricaoAtividade().equalsIgnoreCase(atividadesOSDTO.getDescricaoAtividade())){
                                strQtde = UtilFormatacao.formatDouble((atividadesOSPaiDTO.getCustoAtividade()), 2);
                                break;
                            }
                        }
                    }

                    String strGlosa = "";
                    if (atividadesOSDTO.getGlosaAtividade() != null){
                        strGlosa = UtilFormatacao.formatDouble(atividadesOSDTO.getGlosaAtividade(), 2);
                    }

                    String strQtdeExec = "";
                    if (atividadesOSDTO.getQtdeExecutada() != null){
                        strQtdeExec = UtilFormatacao.formatDouble(atividadesOSDTO.getQtdeExecutada(), 2);
                    }
                    String strDet = atividadesOSDTO.getDescricaoAtividade();
                    String strObs = atividadesOSDTO.getObsAtividade();
                    if (strDet != null){
                        strDet = strDet.replaceAll("'", "");
                    }
                    if (strObs != null){
                        strObs = strObs.replaceAll("'", "");
                    }

                    String strIdServicoContratoContabil = "";
                    StringBuilder strBtnServicoContabil = new StringBuilder();
                    String exibirBotao = "";
                    if(atividadesOSDTO.getContabilizar() != null && atividadesOSDTO.getContabilizar().equalsIgnoreCase("S")){
                        ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);

                        Integer idServicoContratoContabil = atividadesOSDTO.getIdServicoContratoContabil();
                        if(idServicoContratoContabil != null){
                            strIdServicoContratoContabil = idServicoContratoContabil.toString();
                        }
                        if(idServicoContratoContabil != null){
                            document.getElementById("idServicoContratoContabil").setValue(strIdServicoContratoContabil);
                            ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
                            ServicoDTO servicoDTO = new ServicoDTO();
                            servicoDTO.setIdServico(idServicoContratoContabil);
                            servicoDTO = (ServicoDTO) servicoService.restore(servicoDTO);
                            if(servicoDTO != null){
                                strBtnServicoContabil.append("<input type='hidden' name='servicoContrato"+i+"'/>")
                                .append("<div id='divServicoContrato"+i+"' style='width: 600px; height: 37px;'>")
                                .append("<button type='button' name='btnAddIncidentes' style='margin-top: 3px;' class='light img_icon has_text clsAddIncidente' onclick='mostrarIncidentesParaAssociar("+idServicoContratoContabil+")'>")
                                .append("<img src='"+ Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() +"/template_new/images/icons/small/grey/document.png'>")
                                .append("<span>"+ UtilI18N.internacionaliza(request, "citcorpore.comum.associarIncidente") +"</span>")
                                .append("</button>")
                                .append("<div id='nomeServicoContratoContabil' style='font-weight: bold; padding-top: 2px;'>"+UtilStrings.nullToVazio(servicoDTO.getNomeServico())+"</div>")
                                .append("</div>");

                                exibirBotao = "true";
                            }
                        }
                    }

                    document.executeScript("setaRestoreItem('" + atividadesOSDTO.getComplexidade().trim() + "'," +
                            "'" + br.com.citframework.util.WebUtil.codificaEnter(strDet) + "'," +
                            "'" + br.com.citframework.util.WebUtil.codificaEnter(strObs) + "'," +
                            "'" + UtilStrings.nullToVazio(atividadesOSDTO.getFormula()) + "'," +
                            "'" + UtilStrings.nullToVazio(strQtde) + "'," +
                            "'" + UtilStrings.nullToVazio(strGlosa) + "'," +
                            "'" + UtilStrings.nullToVazio(strQtdeExec) + "'," +
                            "'" + atividadesOSDTO.getIdAtividadesOS() + "'," +
                            "'" + UtilStrings.nullToVazio(exibirBotao) + "'," +
                            "'" + UtilStrings.nullToVazio(strIdServicoContratoContabil) + "'," +
                            "'" + StringEscapeUtils.escapeJavaScript(UtilStrings.nullToVazio(strBtnServicoContabil.toString())) + "'" +
                            ")");

                }
            }

            if (os != null){
                if (os.getSituacaoOS().equals(OSDTO.EM_EXECUCAO)){  //somente se em execucao.
                    int i = 0;
                    if (colItens != null){
                        i = 0;
                        for(Iterator it = colItens.iterator(); it.hasNext();){
                            i++;
                            AtividadesOSDTO atividadesOSDTO = (AtividadesOSDTO)it.next();
                            String objHtml = "document.form.qtdeExecutada" + UtilFormatacao.formatInt(i, "00000");
                            document.executeScript(objHtml + ".disabled = false");
                        }
                    }
                    String objHtml = "document.form.quantidade";
                    document.executeScript(objHtml + ".disabled = false");
                    document.executeScript(objHtml + ".style.width = '40px !important;'");

                }
                if (os.getSituacaoOS().intValue() == OSDTO.APROVADA){  //somente se liberado para homologacao.
                    int i = 0;
                    if (colItens != null){
                        i = 0;
                        for(Iterator it = colItens.iterator(); it.hasNext();){
                            i++;
                            AtividadesOSDTO atividadesOSDTO = (AtividadesOSDTO)it.next();
                            String objHtml = "document.form.glosa" + UtilFormatacao.formatInt(i, "00000");
                            document.executeScript(objHtml + ".disabled = false");
                            objHtml = "document.form.qtdeExecutada" + UtilFormatacao.formatInt(i, "00000");
                            document.executeScript(objHtml + ".disabled = false");
                        }
                    }

                }
            }
        }else{
            document.alert("Digite um valor maior que zero para quantidade!");
        }
    }

    public void calculaFormulaANS(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuario = br.com.centralit.citcorpore.util.WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert("Sessão expirada! Favor efetuar logon novamente!");
            document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
            return;
        }
        OSDTO osDTO = (OSDTO) document.getBean();
        Integer idContrato = -1;
        try {
            idContrato = Integer.parseInt((String) request.getSession(true).getAttribute("NUMERO_CONTRATO_EDICAO"));
        } catch (Exception e) {
        }
        if (idContrato == -1) {
            document.alert("Não foi possível identificar o contrato, por favor, feche esta tela e faça logon novamente!");
            document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
            return;
        }
        AtividadesOSService atividadesOSService = (AtividadesOSService) ServiceLocator.getInstance().getService(AtividadesOSService.class, null);
        AcordoNivelServicoService acordoNivelServicoService = (AcordoNivelServicoService) ServiceLocator.getInstance().getService(AcordoNivelServicoService.class, null);
        ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
        FormulaService formulaService = (FormulaService) ServiceLocator.getInstance().getService(FormulaService.class, null);
        OSService oSService = (OSService) ServiceLocator.getInstance().getService(OSService.class, null);
        GlosaOSService glosaOSService = (GlosaOSService) ServiceLocator.getInstance().getService(GlosaOSService.class, null);

        if (osDTO.getIdANS() != null) {
            AcordoNivelServicoDTO acordoNivelServicoDTO = new AcordoNivelServicoDTO();
            acordoNivelServicoDTO.setIdAcordoNivelServico(osDTO.getIdANS());
            acordoNivelServicoDTO = (AcordoNivelServicoDTO) acordoNivelServicoService.restore(acordoNivelServicoDTO);

            Collection col = atividadesOSService.findByIdOS(osDTO.getIdOS());
            double qtdeTotal = 0;
            double qtdeExecutada = 0;
            double qtdeGlosada = 0;

            if (col != null){
                for(Iterator it = col.iterator(); it.hasNext();){
                    AtividadesOSDTO atividadesOSDTO = (AtividadesOSDTO)it.next();
                    qtdeTotal = qtdeTotal + atividadesOSDTO.getCustoAtividade();
                    qtdeGlosada = qtdeGlosada + atividadesOSDTO.getGlosaAtividade();
                    qtdeExecutada = qtdeExecutada + atividadesOSDTO.getQtdeExecutada();
                }
            }

            osDTO.setCustoOS(qtdeTotal);
            osDTO.setGlosaOS(qtdeGlosada);
            osDTO.setExecutadoOS(qtdeExecutada);

            //Falta implementacao e entendimento

            if (acordoNivelServicoDTO.getIdFormula() == null) {
                document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
                return;
            }
        }else{
            String FORMULA_CALCULO_GLOSA_OS_STR = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.FORMULA_CALCULO_GLOSA_OS, " ");
            Integer FORMULA_CALCULO_GLOSA_OS = null;
            try{
                FORMULA_CALCULO_GLOSA_OS = new Integer(FORMULA_CALCULO_GLOSA_OS_STR);
            }catch (Exception e) {
            }
            if (FORMULA_CALCULO_GLOSA_OS != null){
                Collection col = atividadesOSService.findByIdOS(osDTO.getIdOS());
                double qtdeTotal = 0;
                double qtdeExecutada = 0;
                double qtdeGlosada = 0;
                if (col != null){
                    for(Iterator it = col.iterator(); it.hasNext();){
                        AtividadesOSDTO atividadesOSDTO = (AtividadesOSDTO)it.next();
                        qtdeTotal = qtdeTotal + atividadesOSDTO.getCustoAtividade();
                        if (atividadesOSDTO.getGlosaAtividade() != null){
                            qtdeGlosada = qtdeGlosada + atividadesOSDTO.getGlosaAtividade();
                        }
                        if (atividadesOSDTO.getQtdeExecutada() != null){
                            qtdeExecutada = qtdeExecutada + atividadesOSDTO.getQtdeExecutada();
                        }
                    }
                }

                osDTO.setCustoOS(qtdeTotal);
                osDTO.setGlosaOS(qtdeGlosada);
                osDTO.setExecutadoOS(qtdeExecutada);

                FormulaDTO formulaDto = new FormulaDTO();
                formulaDto.setIdFormula(FORMULA_CALCULO_GLOSA_OS);
                formulaDto = (FormulaDTO) formulaService.restore(formulaDto);
                if (formulaDto != null) {
                    ScriptRhinoJSExecute scriptExecute = new ScriptRhinoJSExecute();
                    RuntimeScript runtimeScript = new RuntimeScript();
                    Context cx = Context.enter();
                    Scriptable scope = cx.initStandardObjects();
                    scope.put("document", scope, document);
                    scope.put("request", scope, request);
                    scope.put("response", scope, response);
                    scope.put("osDTO", scope, osDTO);
                    scope.put("colAtividades", scope, col);
                    scope.put("FormulasUtil", scope, new FormulasUtil());
                    scope.put("ACTION", scope, "calculaFormulaANS");
                    scope.put("userLogged", scope, usuario);
                    scope.put("RuntimeScript", scope, runtimeScript);
                    try {
                        Object retorno = scriptExecute.processScript(cx, scope, formulaDto.getConteudo(), OsSetSituacao.class.getName() + "_calculaFormulaANS");
                    } catch (Exception e) {
                        document.alert("ERRO AO EXECUTAR A FORMULA!");
                        e.printStackTrace();
                    }
                }
            }
        }
        document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
    }
}
