/**
 *
 */
package br.com.centralit.citcorpore.ajaxForms;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRCsvExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.LinguaDTO;
import br.com.centralit.citcorpore.bean.ParametroCorporeDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.LinguaService;
import br.com.centralit.citcorpore.negocio.ParametroCorporeService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.UtilRelatorio;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

/**
 * @author valdoilo.damasceno
 *
 */
public class ParametroCorpore extends AjaxFormAction {

    private UsuarioDTO usuario;
    private static final int TAMANHO_BUFFER = 32768;

    @Override
    public Class<ParametroCorporeDTO> getBeanClass() {
        return ParametroCorporeDTO.class;
    }

    @Override
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        System.out.println("CITSMART - Criando parametros novos... iniciando.");
        this.getParametroCorporeService().criarParametrosNovos();
        System.out.println("CITSMART - Criando parametros novos... pronto.");

        final HttpSession session = request.getSession();
        LinguaDTO linguaDTO = new LinguaDTO();
        linguaDTO.setSigla((String) session.getAttribute("locale"));
        if (linguaDTO.getSigla() == null) {
            linguaDTO.setSigla(UtilI18N.PORTUGUESE_SIGLA);
        } else if (linguaDTO.getSigla().equals("")) {
            linguaDTO.setSigla(UtilI18N.PORTUGUESE_SIGLA);
        }
        final LinguaService linguaService = (LinguaService) ServiceLocator.getInstance().getService(LinguaService.class, null);
        linguaDTO = linguaService.getIdLingua(linguaDTO);
        document.getElementById("idLingua").setValue("" + linguaDTO.getIdLingua());
    }

    /**
     * Restorna para tela inicial de ParametroCorpore.
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     * @author VMD
     */
    public void restore(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        ParametroCorporeDTO parametroBean = (ParametroCorporeDTO) document.getBean();

        parametroBean = (ParametroCorporeDTO) this.getParametroCorporeService().restore(parametroBean);

        /*
         * Se o parâmetro for do tipo boolean, faz a conversão para o valor correto
         */
        if (parametroBean.getTipoDado() != null && parametroBean.getTipoDado().equalsIgnoreCase("Boolean")) {
            parametroBean.setValor(Internacionalizar.internacionalizaOptionSN(request, "restore", parametroBean.getValor()));
        }

        if (parametroBean.getValor() != null && !StringUtils.isBlank(parametroBean.getValor())) {
            // parametroBean.setValor(StringEscapeUtils.escapeJavaScript(parametroBean.getValor().trim()));
            parametroBean.setValor(UtilStrings.decodeCaracteresEspeciais(parametroBean.getValor()).trim());
        }

        parametroBean.setNome(UtilI18N.internacionaliza(request, parametroBean.getNome()));

        final HTMLForm form = document.getForm("form");
        form.clear();
        form.setValues(parametroBean);
        document.getTextBoxById("idAux").setValue("" + parametroBean.getId());

        final HttpSession session = request.getSession();
        LinguaDTO linguaDTO = new LinguaDTO();
        linguaDTO.setSigla((String) session.getAttribute("locale"));
        if (linguaDTO.getSigla() == null || linguaDTO.getSigla().equals("")) {
            linguaDTO.setSigla(UtilI18N.PORTUGUESE_SIGLA);
        } else if (linguaDTO.getSigla().equals("")) {
            linguaDTO.setSigla(UtilI18N.PORTUGUESE_SIGLA);
        }
        final LinguaService linguaService = (LinguaService) ServiceLocator.getInstance().getService(LinguaService.class, null);
        linguaDTO = linguaService.getIdLingua(linguaDTO);
        document.getElementById("idLingua").setValue("" + linguaDTO.getIdLingua());

        if (parametroBean != null && parametroBean.getTipoDado() != null) {
            if (parametroBean.getTipoDado().trim().equalsIgnoreCase("Senha")) {
                document.executeScript("MudarCampovalorParaTipoSenha();");
            } else {
                document.executeScript("MudarCampovalorParaTipoTexto();");
            }

            if (parametroBean.getTipoDado() != null && parametroBean.getTipoDado().trim().equalsIgnoreCase("Texto")) {
                document.executeScript(" limpaCaracteristica();  $('#valor').addClass('text'); removeAllEventos();");
            } else if (parametroBean.getTipoDado() != null && parametroBean.getTipoDado().trim().equalsIgnoreCase("Boolean")) {
                document.executeScript(" limpaCaracteristica();  $('#valor').addClass('text'); removeAllEventos();");
            } else {
                if (parametroBean.getTipoDado().trim().equalsIgnoreCase("Email")) {
                    document.executeScript("limpaCaracteristica(); $('#valor').addClass('text'); $('#valor').addClass('Valid[" + parametroBean.getTipoDado()
                            + "]'); $('#valor').addClass('Description[citcorpore.comum.valor]'); DEFINEALLPAGES_generateConfiguracaoCampos();");
                } else if (parametroBean.getTipoDado().trim().equalsIgnoreCase("Numero") || parametroBean.getTipoDado().trim().equalsIgnoreCase("Moeda")) {
                    document.executeScript(" limpaCaracteristica(); $('#valor').addClass('text');   $('#valor').addClass('Format[" + parametroBean.getTipoDado()
                            + "]'); DEFINEALLPAGES_generateConfiguracaoCampos();");
                } else if (parametroBean.getTipoDado().trim().equalsIgnoreCase("Telefone") || parametroBean.getTipoDado().trim().equalsIgnoreCase("CEP")) {
                    document.executeScript(" limpaCaracteristica(); mascara('" + parametroBean.getTipoDado()
                            + "');  $('#valor').addClass('text');   $('#valor').addClass('Description[citcorpore.comum.valor]'); DEFINEALLPAGES_generateConfiguracaoCampos();");
                } else {
                    document.executeScript(" limpaCaracteristica(); mascara('" + parametroBean.getTipoDado() + "');  $('#valor').addClass('text');  $('#valor').addClass('Valid["
                            + parametroBean.getTipoDado() + "]'); $('#valor').addClass('Description[citcorpore.comum.valor]'); DEFINEALLPAGES_generateConfiguracaoCampos();");
                }
            }
        }

    }

    /**
     * Inclui novo Parametro do CITCorpore.
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     * @author VMD
     */
    public void save(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final ParametroCorporeDTO parametroBean = (ParametroCorporeDTO) document.getBean();
        boolean gravado = false;

        if (parametroBean.getValor() == null) {
            parametroBean.setValor("");
        }

        /*
         * Se o parâmetro for do tipo boolean, faz a conversão para o valor correto
         */
        if (parametroBean.getTipoDado() != null && parametroBean.getTipoDado().equalsIgnoreCase("Boolean")) {
            parametroBean.setValor(Internacionalizar.internacionalizaOptionSN(request, "save", parametroBean.getValor().trim()));
        } else {
            parametroBean.setValor(parametroBean.getValor().trim());
        }

        if (parametroBean.getId() != null && parametroBean.getId() != 34 && parametroBean.getId() != 35 && parametroBean.getId() != 43 && parametroBean.getId() != 37
                && parametroBean.getId() != 38 && parametroBean.getId() != 67 && parametroBean.getId() != 64 && parametroBean.getId() != 49 && parametroBean.getId() != 39
                && parametroBean.getId() != 45 && parametroBean.getId() != 102 && parametroBean.getId() != 68 && parametroBean.getId() != 121) {

            if (parametroBean.getId() == null || parametroBean.getId() < 0) {
                // parametroBean.setValor(StringUtils.replace(parametroBean.getValor(), "\\", "\\\\"));
                this.getParametroCorporeService().create(parametroBean, request);
                document.alert(UtilI18N.internacionaliza(request, "MSG05"));
            } else {
                // parametroBean.setValor(StringUtils.replace(parametroBean.getValor(), "\\", "\\\\"));
                try {
                    // Setado nulo para não mexer no nome quando for salvo, fazendo um updadeNotNull.
                    parametroBean.setNome(null);
                    // O parametro com id 152 é a nota da pesquisa de satisfação automatica para que ela funcione corretamente é testado somente se for otimo bom regular ou ruim
                    // por isso valido o que o usuario digita no valor do parametro.
                    if (parametroBean.getId() != 152) {
                        this.getParametroCorporeService().updateNotNull(parametroBean);
                        gravado = true;
                    } else {
                        final String valor = parametroBean.getValor();
                        if (valor.equals("BOM") || valor.equals("OTIMO") || valor.equals("REGULAR") || valor.equals("RUIM")) {
                            this.getParametroCorporeService().updateNotNull(parametroBean);
                            gravado = true;
                        } else {
                            document.alert(UtilI18N.internacionaliza(request, "notaAvaliacaoAutomatica.nota"));
                        }
                    }

                } catch (final Exception e) {
                    System.out.println("Problema ao atualizar Parâmetro CITSMart.");
                    e.printStackTrace();
                    gravado = false;
                }

                ParametroUtil.atualizarHashMapParametroCitSmart(parametroBean.getId(), parametroBean.getValor());
                if (gravado) {
                    document.alert(UtilI18N.internacionaliza(request, "MSG06"));
                }
            }
        } else {
            document.alert(UtilI18N.internacionaliza(request, "ldap.configurarParametro"));
        }

    }

    /**
     * Exclui Parâmetro atribuindo sua dataFim.
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     * @author VMD
     */
    public void excluirParametroCorpore(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final ParametroCorporeDTO parametroCorporeBean = (ParametroCorporeDTO) document.getBean();

        if (parametroCorporeBean.getId() != null && parametroCorporeBean.getId() != 0) {

            parametroCorporeBean.setDataFim(UtilDatas.getDataAtual());
            this.getParametroCorporeService().update(parametroCorporeBean);

            final HTMLForm form = document.getForm("form");
            form.clear();
            document.alert(UtilI18N.internacionaliza(request, "MSG07"));
        }
    }

    public void exportarParametroCsv(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final HttpSession session = request.getSession();

        usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
            return;
        }
        Collection<ParametroCorporeDTO> listParametro = new ArrayList<ParametroCorporeDTO>();

        listParametro = this.getParametroCorporeService().list();
        final JRDataSource listaParametro = new JRBeanCollectionDataSource(listParametro);

        final Date dt = new Date();
        final String strCompl = "" + dt.getTime();
        final String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
        final String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);

        parametros.put("TITULO_RELATORIO1", "ID");
        parametros.put("TITULO_RELATORIO2", "NOME");
        parametros.put("TITULO_RELATORIO3", "VALOR");

        final JasperDesign desenho = JRXmlLoader.load(CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "ParametroCorporeCsv.jrxml");

        final JasperReport relatorio = JasperCompileManager.compileReport(desenho);

        final JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros, listaParametro);

        final JRCsvExporter exporter = new JRCsvExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, impressao);
        exporter.setParameter(JRCsvExporterParameter.FIELD_DELIMITER, ";");
        exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "ISO-8859-1");
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, diretorioReceita + "/ParametroCorporeCsv" + strCompl + "_" + usuario.getIdUsuario() + ".csv");

        exporter.exportReport();
        int cont;
        final byte[] dados = new byte[TAMANHO_BUFFER];

        BufferedInputStream origem = null;
        FileInputStream streamDeEntrada = null;
        FileOutputStream destino = null;
        ZipOutputStream saida = null;
        ZipEntry entry = null;

        destino = new FileOutputStream(new File(diretorioReceita + "/ParametroCorporeCsv" + strCompl + "_" + usuario.getIdUsuario() + ".zip"));
        saida = new ZipOutputStream(new BufferedOutputStream(destino));
        final File file = new File(diretorioReceita + "/ParametroCorporeCsv" + strCompl + "_" + usuario.getIdUsuario() + ".csv");
        streamDeEntrada = new FileInputStream(file);
        origem = new BufferedInputStream(streamDeEntrada, TAMANHO_BUFFER);
        entry = new ZipEntry(file.getName());
        saida.putNextEntry(entry);

        while ((cont = origem.read(dados, 0, TAMANHO_BUFFER)) != -1) {
            saida.write(dados, 0, cont);
        }
        origem.close();
        saida.close();

        document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url="
                + diretorioRelativoOS + "/ParametroCorporeCsv" + strCompl + "_" + usuario.getIdUsuario() + ".zip')");
        document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
    }

    private ParametroCorporeService parametroService;

    /**
     * Retorna Service de ParametroCorpore.
     *
     * @return
     * @throws ServiceException
     * @throws Exception
     * @author VMD
     */
    private ParametroCorporeService getParametroCorporeService() throws Exception {
        if (parametroService == null) {
            parametroService = (ParametroCorporeService) ServiceLocator.getInstance().getService(ParametroCorporeService.class, null);
        }
        return parametroService;
    }

}
