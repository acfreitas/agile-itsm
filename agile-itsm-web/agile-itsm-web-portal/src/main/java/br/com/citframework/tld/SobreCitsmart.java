package br.com.citframework.tld;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Locale;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import br.com.centralit.citcorpore.bean.ReleaseDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.CitCorporeConstantes;
import br.com.centralit.citcorpore.versao.Versao;
import br.com.centralit.citgerencial.util.Util;
import br.com.citframework.util.HistoricoAtualizacoesCitsmart;
import br.com.citframework.util.Mensagens;
import br.com.citframework.util.UtilI18N;

/**
 * Imprime as informações relacionadas a versão
 *
 * @author flavio.santana
 * @since 23/10/2013
 */
public class SobreCitsmart extends BodyTagSupport {

    private static final long serialVersionUID = 1L;
    private static Properties props;

    @Override
    public int doStartTag() throws JspException {

        /*
         * Recupera a propriedade do locale
         */
        final HttpSession session = ((HttpServletRequest) pageContext.getRequest()).getSession();
        final StringBuilder stringBuilder = new StringBuilder();
        String locale = UtilI18N.PORTUGUESE_SIGLA;
        if (session.getAttribute("locale") != null) {
            this.getProperties(new Locale(session.getAttribute("locale").toString()));
            locale = session.getAttribute("locale").toString();
        } else {
            this.getProperties(new Locale(""));
            locale = UtilI18N.PORTUGUESE_SIGLA;
        }
        if (locale.equals("")) {
            locale = UtilI18N.PORTUGUESE_SIGLA;
        }

        try {
            final String separator = System.getProperty("file.separator");
            final String path = CITCorporeUtil.CAMINHO_REAL_APP + "XMLs" + separator + "release_" + locale + ".xml";
            HistoricoAtualizacoesCitsmart hc = null;
            Collection<ReleaseDTO> listRelease = null;

            try {
                hc = new HistoricoAtualizacoesCitsmart();
                listRelease = hc.lerXML(path);
            } catch (final FileNotFoundException e) {
                e.printStackTrace();
            }

            final String passoInstalacao = (String) session.getAttribute("passoInstalacao");
            final ServletContext context = ((HttpServletRequest) pageContext.getRequest()).getSession().getServletContext();

            if (passoInstalacao != null || context.getAttribute("instalacao") != null) {
                stringBuilder.append("<form  name='formSobre' id='formSobre'  action='" + CitCorporeConstantes.CAMINHO_SERVIDOR
                        + ((HttpServletRequest) pageContext.getRequest()).getContextPath() + "/pages/start/start'>");
            } else {
                stringBuilder.append("<form  name='formSobre' id='formSobre'  action='" + CitCorporeConstantes.CAMINHO_SERVIDOR
                        + ((HttpServletRequest) pageContext.getRequest()).getContextPath() + "/pages/index/index'>");
            }
            stringBuilder.append("	<div id='sobre-container'>");
            stringBuilder.append("	     <img src='" + CitCorporeConstantes.CAMINHO_SERVIDOR + ((HttpServletRequest) pageContext.getRequest()).getContextPath()
                    + "/imagens/logo/iconeLogo.png'>");
            stringBuilder.append("	     <div id='produto-descricao'>");
            stringBuilder.append("	       <h2>Citsmart</h2>");
            stringBuilder.append("	       <span>" + this.getInternacionalizado("sobre.citsmart") + "</span>");
            stringBuilder.append("	  	 </div>");
            stringBuilder.append("	</div>");
            stringBuilder.append("  <div id='versao-container'>");
            stringBuilder.append("		<div>" + this.getInternacionalizado("login.versao") + "<b>" + Versao.getDataAndVersao() + "</b></div>");
            stringBuilder.append("		<div><a href='javascript:;' class='openHistorico'>" + this.getInternacionalizado("sobre.oQueHaDeNovoNestaVersao") + "</a></b></div>");
            stringBuilder.append("		<div id='historico'>");
            stringBuilder.append("			<h2>" + this.getInternacionalizado("release.historicoAtualizacoes") + "</h2>");
            stringBuilder.append("			<select name='versao' id='versao' onchange='buscaHistoricoPorVersao()'>");
            if (listRelease != null && !listRelease.isEmpty()) {
                for (final ReleaseDTO releaseDto : listRelease) {
                    stringBuilder.append("				<option value='" + releaseDto.getVersao() + "'>" + releaseDto.getVersao() + "</option>");
                }
            }
            stringBuilder.append("			</select>");

            stringBuilder.append("		<div class='slim-scroll box-generic'>");
            stringBuilder.append("			<div  id='divRelease'>");

            if (listRelease != null && !listRelease.isEmpty()) {

                stringBuilder.append("			<div id='historicoRelease' style='overflow: auto; text-align: justify;'>");

                int countRelease = 0;
                for (final ReleaseDTO releaseDto : listRelease) {

                    stringBuilder.append("			<div id='release" + countRelease + "'>");
                    stringBuilder.append("				<div>");
                    stringBuilder.append("					<br>");

                    if (releaseDto.getConteudo() != null && !releaseDto.getConteudo().isEmpty()) {
                        int i = 0;
                        for (final String item : releaseDto.getConteudo()) {
                            ++i;
                            stringBuilder.append("			<div>");
                            stringBuilder.append("				<span  style='font-weight:bold;'>");
                            stringBuilder.append(i);
                            stringBuilder.append(" ");
                            stringBuilder.append("				</span>");
                            stringBuilder.append(Util.encodeHTML(item));
                            stringBuilder.append("			</div>");
                            stringBuilder.append("			<br>");
                        }
                    }
                    stringBuilder.append("					</div>");
                    stringBuilder.append("				</div>");
                    ++countRelease;
                    break;
                }

                stringBuilder.append("				</div>");

            }

            stringBuilder.append("				</div>");
            stringBuilder.append("			</div>");
            stringBuilder.append("		</div>");
            stringBuilder.append("	</div>");

            stringBuilder.append("	<div id='produto-container'>");
            stringBuilder.append("		<div><a target='_blank' href='http://www.citsmart.com.br'>Citsmart</a></div>");
            stringBuilder.append("		<div>© 2014 -  " + UtilI18N.internacionaliza((HttpServletRequest) pageContext.getRequest(), "citcorpore.todosDireitosReservados") + "</div>");
            stringBuilder.append("	</div>");
            stringBuilder.append("</form>");

            pageContext.getOut().println(stringBuilder.toString());

        } catch (final Exception e) {
            e.printStackTrace();
        }

        return SKIP_BODY;
    }

    private String getInternacionalizado(final String texto) {
        String traduzido = props.getProperty(texto);
        if (traduzido == null || traduzido.length() <= 0) {
            traduzido = texto;
        }
        return traduzido;
    }

    private Properties getProperties(final Locale locale) {
        String fileName = "";
        try {
            if (locale != null && !locale.toString().equals("") && !locale.toString().equals("pt_BR")) {
                fileName = "Mensagens_" + locale.toString() + ".properties";
            } else {
                fileName = "Mensagens.properties";
            }

            props = new Properties();
            final ClassLoader load = Mensagens.class.getClassLoader();
            InputStream is = load.getResourceAsStream(fileName);
            if (is == null) {
                is = ClassLoader.getSystemResourceAsStream(fileName);
            }
            if (is == null) {
                is = ClassLoader.getSystemClassLoader().getResourceAsStream(fileName);
            }

            try {
                if (is != null) {
                    props.load(is);
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        } catch (final SecurityException e1) {
            e1.printStackTrace();
        }
        return props;
    }

}
