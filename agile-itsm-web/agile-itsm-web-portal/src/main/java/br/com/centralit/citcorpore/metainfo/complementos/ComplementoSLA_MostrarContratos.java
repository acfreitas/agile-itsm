package br.com.centralit.citcorpore.metainfo.complementos;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

import br.com.centralit.citcorpore.bean.ClienteDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.FornecedorDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.negocio.ClienteService;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.FornecedorService;
import br.com.centralit.citcorpore.negocio.ServicoContratoService;
import br.com.centralit.citcorpore.negocio.ServicoService;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilHTML;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

public class ComplementoSLA_MostrarContratos {

    private static final Logger LOGGER = Logger.getLogger(ComplementoSLA_MostrarContratos.class);

    public void execute(final HttpServletRequest request, final HttpServletResponse response, final String tipoContrato) {
        final Map<String, Object> map = this.getValuesFromRequest(request);
        this.debugValuesFromRequest(map);

        try {
            final PrintWriter out = response.getWriter();

            final String idAcordoNivelServicoString = request.getParameter("IDACORDONIVELSERVICO");
            final int idAcordoNivelServico = NumberUtils.toInt(idAcordoNivelServicoString, 0);

            final StringBuilder strTable = new StringBuilder();
            strTable.append("<table width='100%'>");
            strTable.append("<tr>");
            strTable.append("<td style='border:1px solid black'>");
            strTable.append("&nbsp;");
            strTable.append("</td>");
            strTable.append("<td style='border:1px solid black'>");
            strTable.append("<b>");
            strTable.append(UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "sla.contrato.numero")));
            strTable.append("</b>");
            strTable.append("</td>");
            strTable.append("<td style='border:1px solid black'>");
            strTable.append("<b>");
            strTable.append(UtilI18N.internacionaliza(request, "sla.contrato.data"));
            strTable.append("</b>");
            strTable.append("</td>");
            strTable.append("<td style='border:1px solid black'>");
            strTable.append("<b>");
            strTable.append(UtilI18N.internacionaliza(request, "sla.contrato.cliente"));
            strTable.append("</b>");
            strTable.append("</td>");
            strTable.append("<td style='border:1px solid black'>");
            strTable.append("<b>");
            strTable.append(UtilI18N.internacionaliza(request, "sla.contrato.fornecedor"));
            strTable.append("</b>");
            strTable.append("</td>");
            strTable.append("<td style='border:1px solid black'>");
            strTable.append("<b>");
            strTable.append(UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "sla.contrato.situacao")));
            strTable.append("</b>");
            strTable.append("</td>");
            strTable.append("</tr>");
            if (idAcordoNivelServico > 0) {
                final ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
                final ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
                final ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
                final ClienteService clienteService = (ClienteService) ServiceLocator.getInstance().getService(ClienteService.class, null);
                final FornecedorService fornecedorService = (FornecedorService) ServiceLocator.getInstance().getService(FornecedorService.class, null);
                final List lstContratosCliente = (List) contratoService.listByIdAcordoNivelServicoAndTipo(idAcordoNivelServico, tipoContrato);
                if (lstContratosCliente != null) {
                    for (final Iterator it = lstContratosCliente.iterator(); it.hasNext();) {
                        final ContratoDTO contratoDTO = (ContratoDTO) it.next();
                        String nomeCliente = "";
                        String nomeFornecedor = "";

                        ClienteDTO clientDto = new ClienteDTO();
                        clientDto.setIdCliente(contratoDTO.getIdCliente());
                        clientDto = (ClienteDTO) clienteService.restore(clientDto);
                        if (clientDto != null) {
                            nomeCliente = clientDto.getNomeRazaoSocial();
                        }

                        FornecedorDTO fornecedorDto = new FornecedorDTO();
                        fornecedorDto.setIdFornecedor(contratoDTO.getIdFornecedor());
                        fornecedorDto = (FornecedorDTO) fornecedorService.restore(fornecedorDto);
                        if (fornecedorDto != null) {
                            nomeFornecedor = fornecedorDto.getRazaoSocial();
                        }

                        String situacao = contratoDTO.getSituacao();
                        if (situacao.equalsIgnoreCase("A")) {
                            situacao = UtilI18N.internacionaliza(request, "sla.contrato.ativo");
                        } else if (situacao.equalsIgnoreCase("F")) {
                            situacao = UtilI18N.internacionaliza(request, "sla.contrato.finalizado");
                        } else if (situacao.equalsIgnoreCase("C")) {
                            situacao = UtilI18N.internacionaliza(request, "sla.contrato.cancelado");
                        } else if (situacao.equalsIgnoreCase("P")) {
                            situacao = UtilI18N.internacionaliza(request, "sla.contrato.paralisado");
                        }

                        strTable.append("<tr>");
                        strTable.append("<td style='border:1px solid black'>");
                        strTable.append("<img id='img_tr_");
                        strTable.append(contratoDTO.getIdContrato());
                        strTable.append("_");
                        strTable.append(tipoContrato);
                        strTable.append("' src='");
                        strTable.append(Constantes.getValue("SERVER_ADDRESS"));
                        strTable.append(Constantes.getValue("CONTEXTO_APLICACAO"));
                        strTable.append("/imagens/mais.jpg' border='0' onclick=\"abreFechaMaisMenos(this, 'tr_");
                        strTable.append(contratoDTO.getIdContrato());
                        strTable.append("_");
                        strTable.append(tipoContrato);
                        strTable.append("')\"/>");
                        strTable.append("</td>");
                        strTable.append("<td style='border:1px solid black'>");
                        strTable.append(contratoDTO.getNumero());
                        strTable.append("</td>");
                        strTable.append("<td style='border:1px solid black'>");
                        strTable.append(UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, contratoDTO.getDataContrato(), WebUtil.getLanguage(request)));
                        strTable.append("</td>");
                        strTable.append("<td style='border:1px solid black'>");
                        strTable.append(UtilHTML.encodeHTML(nomeCliente));
                        strTable.append("</td>");
                        strTable.append("<td style='border:1px solid black'>");
                        strTable.append(UtilHTML.encodeHTML(nomeFornecedor));
                        strTable.append("</td>");
                        strTable.append("<td style='border:1px solid black'>");
                        strTable.append(situacao);
                        strTable.append("</td>");
                        strTable.append("</tr>");

                        final Collection colServicos = servicoContratoService.findByIdContrato(contratoDTO.getIdContrato());
                        if (colServicos != null && colServicos.size() > 0) {
                            strTable.append("<tr>");
                            strTable.append("<td colspan='6' style='border:1px solid black'>");
                            strTable.append("<div id='tr_");
                            strTable.append(contratoDTO.getIdContrato());
                            strTable.append("_");
                            strTable.append(tipoContrato);
                            strTable.append("' style='display:none'>");
                            strTable.append("<table width='100%'>");
                            strTable.append("<tr>");
                            strTable.append("<td colspan='2'>");
                            strTable.append("<b>");
                            strTable.append(UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "sla.servicosdocontrato")));
                            strTable.append("</b>");
                            strTable.append("</td>");
                            strTable.append("</tr>");

                            final Iterator itServ = colServicos.iterator();
                            while (itServ.hasNext()) {
                                final ServicoContratoDTO servContratoDto = (ServicoContratoDTO) itServ.next();
                                if ((servContratoDto.getDataFim() == null || UtilDatas.getDataAtual().before(servContratoDto.getDataFim()))
                                        && (servContratoDto.getDeleted() == null || servContratoDto.getDeleted().equalsIgnoreCase("n"))) {
                                    ServicoDTO servico = new ServicoDTO();
                                    servico.setIdServico(servContratoDto.getIdServico());
                                    servico = (ServicoDTO) servicoService.restore(servico);
                                    if (servico != null) {
                                        strTable.append("<tr>");
                                        strTable.append("<td>");
                                        strTable.append("<img src='");
                                        strTable.append(Constantes.getValue("CONTEXTO_APLICACAO"));
                                        strTable.append("/imagens/seta_link1.gif' border='0'/>");
                                        strTable.append("</td>");
                                        strTable.append("<td>");
                                        strTable.append(UtilHTML.encodeHTML(servico.getNomeServico()));
                                        strTable.append("</td>");
                                        strTable.append("</tr>");
                                    }
                                }
                            }
                            strTable.append("</table>");
                            strTable.append("</div>");
                            strTable.append("</td>");
                            strTable.append("</tr>");
                        }
                    }
                }
            }
            strTable.append("</table>");

            out.write(strTable.toString());
        } catch (final Exception e) {
            LOGGER.warn(e.getMessage(), e);
        }

        response.setContentType("text/html; charset=UTF-8");
    }

    public Map<String, Object> getValuesFromRequest(final HttpServletRequest req) {
        final Enumeration<String> en = req.getParameterNames();
        String[] strValores;
        final Map<String, Object> formFields = new HashMap<>();
        while (en.hasMoreElements()) {
            final String nomeCampo = en.nextElement();
            strValores = req.getParameterValues(nomeCampo);
            if (strValores.length == 0) {
                formFields.put(nomeCampo.toUpperCase(), UtilStrings.decodeCaracteresEspeciais(req.getParameter(nomeCampo)));
            } else {
                if (strValores.length == 1) {
                    formFields.put(nomeCampo.toUpperCase(), UtilStrings.decodeCaracteresEspeciais(strValores[0]));
                } else {
                    formFields.put(nomeCampo.toUpperCase(), strValores);
                }
            }
        }
        return formFields;
    }

    private void debugValuesFromRequest(final Map<String, Object> hashValores) {
        final Set<Entry<String, Object>> set = hashValores.entrySet();
        final Iterator<Entry<String, Object>> i = set.iterator();

        LOGGER.debug("------- ServletDinamic ------ VALORES DO REQUEST: -------");
        while (i.hasNext()) {
            final Entry<String, Object> me = i.next();
            LOGGER.debug("-------------> [" + me.getKey() + "]: [" + me.getValue() + "]");
        }
    }

}
