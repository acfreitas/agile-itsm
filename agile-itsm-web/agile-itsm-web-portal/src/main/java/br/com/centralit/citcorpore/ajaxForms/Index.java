/**
 *
 */
package br.com.centralit.citcorpore.ajaxForms;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.ReleaseDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.CitBrowser;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citgerencial.util.Util;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author valdoilo.damasceno
 *
 */
@SuppressWarnings("unchecked")
public class Index extends AjaxFormAction {

    private static final Logger LOGGER = Logger.getLogger(Index.class);

    private String locale = UtilI18N.PORTUGUESE_SIGLA;

    @Override
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final UsuarioDTO usuario = WebUtil.getUsuario(request);

        if (usuario == null) {
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }

        final HttpSession session = request.getSession();
        if (session.getAttribute("locale") != null && StringUtils.isNotBlank(session.getAttribute("locale").toString())) {
            locale = session.getAttribute("locale").toString();
        }

        final String separator = System.getProperty("file.separator");
        final String path = CITCorporeUtil.CAMINHO_REAL_APP + "XMLs" + separator + "release_" + locale + ".xml";

        Reader reader = null;
        try {
            reader = new InputStreamReader(new FileInputStream(path), "ISO-8859-1");
        } catch (final FileNotFoundException e) {
            LOGGER.warn(e.getMessage(), e);
        }

        final XStream x = new XStream(new DomDriver("ISO-8859-1"));
        final Collection<ReleaseDTO> listRelease = (Collection<ReleaseDTO>) x.fromXML(reader);

        this.preencherComboVersao(document, request, response, listRelease);

        try {
            if (reader != null) {
                reader.close();
            }
        } catch (final IOException e) {
            LOGGER.warn(e.getMessage(), e);
        }

        if (listRelease != null && !listRelease.isEmpty()) {
            final StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("<div id='historicoRelease' style='overflow: auto; text-align: justify;'>");

            int countRelease = 0;
            for (final ReleaseDTO releaseDto : listRelease) {
                stringBuilder.append("<div id='release");
                stringBuilder.append(countRelease);
                stringBuilder.append("' style='height:100%' >");
                stringBuilder.append("<div style='overflow: auto;' >");
                stringBuilder.append("<br>");

                if (releaseDto.getConteudo() != null && !releaseDto.getConteudo().isEmpty()) {
                    int i = 0;
                    for (final String item : releaseDto.getConteudo()) {
                        ++i;
                        stringBuilder.append("<div>");
                        stringBuilder.append("<span  style='font-weight:bold;'>");
                        stringBuilder.append(i);
                        stringBuilder.append(" ");
                        stringBuilder.append("</span>");
                        stringBuilder.append(Util.encodeHTML(item));
                        stringBuilder.append("</div>");
                        stringBuilder.append("<br>");
                    }
                }
                stringBuilder.append("</div>");
                stringBuilder.append("</div>");
                ++countRelease;
                break;
            }

            stringBuilder.append("</div>");

            document.getElementById("divRelease").setInnerHTML(stringBuilder.toString());
        }

        final String mensagem = request.getParameter("mensagem");
        if (mensagem != null && !mensagem.isEmpty()) {
            document.alert(UtilI18N.internacionaliza(request, mensagem));
        }

        CitBrowser browser = null;
        if (request != null) {
            try {
                browser = new CitBrowser(request);
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
        if (browser != null && browser.valido() < 1) {
            document.getElementById("content").setInnerHTML(
                    "<label style='font-size: 14px !important; line-height: 24px; color:red !important;'>"
                            + UtilI18N.internacionaliza(WebUtil.getUsuarioSistema(request).getLocale(), "login.incompativelComNavegador") + "</label>");
            request.getSession().invalidate();
        }
    }

    public void preencherComboVersao(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response, final Collection<ReleaseDTO> listRelease)
            throws Exception {
        if (listRelease != null && !listRelease.isEmpty()) {
            final HTMLSelect comboVersao = document.getSelectById("versao");

            if (comboVersao != null) {
                comboVersao.removeAllOptions();
                comboVersao.addOptions(listRelease, "versao", "versao", null);
            }
        }
    }

    public void buscaHistoricoPorVersao(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final ReleaseDTO releaseDTO = (ReleaseDTO) document.getBean();

        final String separator = System.getProperty("file.separator");
        final String path = CITCorporeUtil.CAMINHO_REAL_APP + "XMLs" + separator + "release_" + locale + ".xml";

        Reader reader = null;
        try {
            reader = new InputStreamReader(new FileInputStream(path), "ISO-8859-1");
        } catch (final FileNotFoundException e) {
            LOGGER.warn(e.getMessage(), e);
        }

        final XStream x = new XStream(new DomDriver("ISO-8859-1"));
        final Collection<ReleaseDTO> listRelease = (Collection<ReleaseDTO>) x.fromXML(reader);

        try {
            if (reader != null) {
                reader.close();
            }
        } catch (final IOException e) {
            LOGGER.warn(e.getMessage(), e);
        }

        if (listRelease != null && !listRelease.isEmpty()) {

            final StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("<div id='historicoRelease' style='overflow: auto;'>");

            int countRelease = 0;

            for (final ReleaseDTO releaseDto : listRelease) {

                if (releaseDTO.getVersao().equals(releaseDto.getVersao())) {
                    stringBuilder.append("<div id='release");
                    stringBuilder.append(countRelease);
                    stringBuilder.append("' style='width: 100%; text-align: justify;'>");
                    stringBuilder.append("<div>");
                    stringBuilder.append("<br>");

                    if (releaseDto.getConteudo() != null && !releaseDto.getConteudo().isEmpty()) {
                        int i = 0;
                        for (final String item : releaseDto.getConteudo()) {
                            ++i;
                            stringBuilder.append("<div>");
                            stringBuilder.append("<span  style='font-weight:bold;'>");
                            stringBuilder.append(i);
                            stringBuilder.append(" ");
                            stringBuilder.append("</span>");
                            stringBuilder.append(Util.encodeHTML(item));
                            stringBuilder.append("</div>");
                            stringBuilder.append("<br>");
                        }
                    }
                    stringBuilder.append("</div>");
                    stringBuilder.append("</div>");

                    ++countRelease;
                }

                stringBuilder.append("</div>");

                document.getElementById("divRelease").setInnerHTML(stringBuilder.toString());
            }
        }
    }

    @Override
    public Class<ReleaseDTO> getBeanClass() {
        return ReleaseDTO.class;
    }

}
