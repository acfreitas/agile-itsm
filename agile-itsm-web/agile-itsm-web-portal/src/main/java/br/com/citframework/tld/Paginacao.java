package br.com.citframework.tld;

import java.io.IOException;
import java.util.Collection;
import java.util.StringTokenizer;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.logic.IterateTag;

import br.com.citframework.excecao.LogicException;
import br.com.citframework.util.Constantes;

public class Paginacao extends IterateTag {

    private static final long serialVersionUID = 678136298587564530L;

    private String form;
    private String parametros;
    private String onclick;
    private String url;

    @Override
    public int doEndTag() throws JspException {
        final int result = super.doEndTag();
        try {
            final int tamanho = this.getTamanho();

            if (tamanho > Integer.parseInt("0" + Constantes.getValue("LIMITE_CONSULTA"))) {
                this.setLength("0");
                this.setOffset("0");
                return result;

            }
            pageContext.getOut().print("<tr><td class='valor' colspan='6'>P&aacute;gina: ");
            int pagina = 1;

            for (int i = 0; i < tamanho; i += Integer.parseInt(this.getLength())) {
                pageContext.getOut().println("<a href='#' onclick=\"" + this.renderizaClick(i) + "\">" + pagina + "</a>|");

                pagina++;
            }

            pageContext.getOut().print("</td></tr>");
        } catch (final IOException e) {
            throw new JspException(e);
        } catch (final Exception e) {
            this.setLength("0");
            this.setOffset("0");
            return result;
        }
        return result;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            if (this.getTamanho() > Integer.parseInt("0" + Constantes.getValue("LIMITE_CONSULTA"))) {
                this.setLength("0");
                this.setOffset("0");
                try {
                    pageContext.getOut().print("<script>alert('" + Constantes.getValue("MT001") + "');</script>");
                    return super.doStartTag();
                } catch (final IOException e) {
                    throw new JspException(e);
                }
            }
        } catch (final Exception e) {
            try {
                e.printStackTrace();
                pageContext.getOut().print("<script>alert('" + e.getMessage() + "');</script>");
                return super.doStartTag();
            } catch (final IOException e1) {
                throw new JspException(e1);
            }
        }

        if (pageContext.getRequest().getParameter("offset") == null || pageContext.getRequest().getParameter("offset").trim().length() == 0) {
            this.setOffset("0");
        } else {
            this.setOffset(pageContext.getRequest().getParameter("offset").trim());
        }

        return super.doStartTag();
    }

    public String getParametros() {
        return parametros;
    }

    public void setParametros(final String parametros) {
        this.parametros = parametros;
    }

    public String getForm() {
        return form;
    }

    public void setForm(final String url) {
        form = url;
    }

    @Override
    public int doAfterBody() throws JspException {
        int tamanho = 0;
        try {
            tamanho = this.getTamanho();
        } catch (final Exception e) {

            tamanho = 0;
        }

        if (tamanho > Integer.parseInt("0" + Constantes.getValue("LIMITE_CONSULTA"))) {
            this.setLength("0");
            this.setOffset("0");
            return SKIP_BODY;

        }
        return super.doAfterBody();
    }

    private int getTamanho() throws Exception {
        this.renderizaClick(1);

        int tamanho = 0;

        Object obj = pageContext.getRequest().getAttribute(this.getName());
        if (obj == null) {
            obj = pageContext.getSession().getAttribute(this.getName());
        }

        Collection<?> col = null;
        if (obj != null) {
            col = (Collection<?>) obj;
            tamanho = col.size();
        }
        return tamanho;

    }

    private String renderizaClick(final int offset) throws Exception {
        if (this.getUrl() != null && this.getUrl().trim().length() > 0) {
            if (this.getUrl().indexOf("?") == -1) {
                return "window.location='" + this.getUrl() + "?offset=" + offset + "';";
            } else {
                return "window.location='" + this.getUrl() + "&offset=" + offset + "';";
            }
        }

        String params = "";

        if (this.getParametros().indexOf(";") == -1) {
            if (pageContext.getRequest().getParameter(this.getParametros()) != null && pageContext.getRequest().getParameter(this.getParametros()).trim().length() > 0) {
                final String tmp = pageContext.getRequest().getParameter(this.getParametros());
                params += "document." + this.getForm() + "." + this.getParametros() + ".value='" + tmp + "';";
            }
        } else {
            final StringTokenizer tok = new StringTokenizer(this.getParametros(), ";");
            while (tok.hasMoreTokens()) {
                final String tmp = tok.nextToken();
                if (pageContext.getRequest().getParameter(tmp) != null && pageContext.getRequest().getParameter(tmp).trim().length() > 0) {
                    final String tmp2 = pageContext.getRequest().getParameter(tmp);
                    params += "document." + this.getForm() + "." + tmp + ".value='" + tmp2 + "';";
                }
            }
        }
        if (params.length() > 0) {
            if (this.getOnclick() == null || this.getOnclick().trim().length() == 0) {
                return params + "document." + this.getForm() + ".action = document." + this.getForm() + ".action+'?offset=" + offset + "';" + "document." + this.getForm()
                        + ".submit();";
            } else {
                return params + "document." + this.getForm() + ".action = document." + this.getForm() + ".action+'?offset=" + offset + "';" + onclick + ";";
            }
        } else {
            throw new LogicException(Constantes.getValue("MSG98"));
        }
    }

    public String getOnclick() {
        return onclick;
    }

    public void setOnclick(final String onclick) {
        this.onclick = onclick;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

}
