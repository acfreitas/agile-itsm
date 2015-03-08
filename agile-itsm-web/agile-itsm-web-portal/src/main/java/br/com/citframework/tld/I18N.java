package br.com.citframework.tld;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import br.com.citframework.util.UtilI18N;

/**
 *
 * @author Cleison Ferreira de Melo
 * @since 21/03/2012
 *
 */
public class I18N extends BodyTagSupport {

    private static final long serialVersionUID = 2714552161457953214L;

    private static final Logger LOGGER = Logger.getLogger(I18N.class);

    private String key;
    private String locale = UtilI18N.PORTUGUESE_SIGLA;

    @Override
    public int doStartTag() throws JspException {
        try {
            String siglaLocale = this.getLocale();

            final HttpSession session = pageContext.getSession();
            if (session != null && session.getAttribute("locale") != null) {
                final String sessionLocale = session.getAttribute("locale").toString().trim();
                if (StringUtils.isNotBlank(sessionLocale)) {
                    siglaLocale = sessionLocale;
                }
            }

            final String value = UtilI18N.internacionaliza(siglaLocale, this.getKey());
            pageContext.getOut().print(value);
        } catch (final Exception e) {
            LOGGER.warn("Erro nas taglibs: " + e.getMessage(), e);
        }

        return SKIP_BODY;
    }

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(final String locale) {
        this.locale = locale;
    }

}
