package br.com.citframework.tld;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import br.com.citframework.util.Reflexao;

public class TreeMenu extends TagSupport {

    private static final long serialVersionUID = 5622076804636058198L;

    private String target;
    private String rootLabel;
    private String jsBaseDir;
    private String cssBaseDir;
    private String collection;
    private String idAttrib;
    private String idFatherAttrib;
    private String descAttrib;
    private String urlAttrib;
    private String hintAttrib;
    private boolean makeNull = true;

    public boolean isMakeNull() {
        return makeNull;
    }

    public void setMakeNull(final boolean makeNull) {
        this.makeNull = makeNull;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(final String collection) {
        this.collection = collection;
    }

    public String getCssBaseDir() {
        return cssBaseDir;
    }

    public void setCssBaseDir(final String cssBaseDir) {
        this.cssBaseDir = cssBaseDir;
    }

    public String getDescAttrib() {
        return descAttrib;
    }

    public void setDescAttrib(final String descAttrib) {
        this.descAttrib = descAttrib;
    }

    public String getHintAttrib() {
        return hintAttrib;
    }

    public void setHintAttrib(final String hintAttrib) {
        this.hintAttrib = hintAttrib;
    }

    public String getIdAttrib() {
        return idAttrib;
    }

    public void setIdAttrib(final String idAttrib) {
        this.idAttrib = idAttrib;
    }

    public String getIdFatherAttrib() {
        return idFatherAttrib;
    }

    public void setIdFatherAttrib(final String idFatherAttrib) {
        this.idFatherAttrib = idFatherAttrib;
    }

    public String getJsBaseDir() {
        return jsBaseDir;
    }

    public void setJsBaseDir(final String jsBaseDir) {
        this.jsBaseDir = jsBaseDir;
    }

    public String getRootLabel() {
        return rootLabel;
    }

    public void setRootLabel(final String rootLabel) {
        this.rootLabel = rootLabel;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(final String target) {
        this.target = target;
    }

    public String getUrlAttrib() {
        return urlAttrib;
    }

    public void setUrlAttrib(final String urlAttrib) {
        this.urlAttrib = urlAttrib;
    }

    @Override
    public int doStartTag() throws JspException {
        final Collection<Object> col = this.getCollectionImpl();
        if (col == null) {
            return SKIP_BODY;
        }

        if (col.size() > 0) {
            try {
                pageContext.getOut().println("	<link rel='StyleSheet' href='" + this.getCssBaseDir() + "/dtree.css' type='text/css' />");
                pageContext.getOut().println("	<script type='text/javascript' src='" + this.getJsBaseDir() + "/dtree.js'></script>");
                pageContext.getOut().println("	<script>");
                pageContext.getOut().println("	d = new dTree('d');");
                pageContext.getOut().println("	d.config.useIcons = false;");
                pageContext.getOut().println("	d.add(0,-1,'" + this.getRootLabel() + "');");
                final Iterator<Object> it = col.iterator();
                while (it.hasNext()) {
                    final Object obj = it.next();
                    final Object id = Reflexao.getPropertyValue(obj, this.getIdAttrib());
                    if (id == null) {
                        throw new JspException("o valor do atributo " + this.getIdAttrib() + " não pode ser nulo ");
                    }
                    final Object desc = Reflexao.getPropertyValue(obj, this.getDescAttrib());
                    if (desc == null) {
                        throw new JspException("o valor do atributo " + this.getDescAttrib() + " não pode ser nulo ");
                    }

                    Object idPai = Reflexao.getPropertyValue(obj, this.getIdFatherAttrib());
                    if (idPai == null) {
                        idPai = "0";
                    }

                    Object url = null;
                    if (this.getUrlAttrib() != null) {
                        url = Reflexao.getPropertyValue(obj, this.getUrlAttrib());
                    }

                    if (url == null) {
                        url = "";
                    }

                    Object hint = null;
                    if (this.getHintAttrib() != null) {
                        hint = Reflexao.getPropertyValue(obj, this.getHintAttrib());
                    }
                    if (hint == null) {
                        hint = desc;
                    }

                    if (this.getTarget() == null) {
                        this.setTarget("");
                    }

                    pageContext.getOut().println("	d.add(" + id + "," + idPai + ",'" + desc + "','" + url + "','" + hint + "','" + target + "','','');");

                }
                pageContext.getOut().println("	document.write(d);");
                pageContext.getOut().println("	</script>");
            } catch (final Exception e) {
                e.printStackTrace();
                throw new JspException(e);
            }
        }

        return SKIP_BODY;
    }

    private Collection<Object> getCollectionImpl() throws JspException {
        Object obj = pageContext.getRequest().getAttribute(this.getCollection());
        if (obj != null) {
            if (obj instanceof Collection) {
                return (Collection<Object>) obj;
            } else {
                throw new JspException("Objeto " + this.getCollection() + " deve ser do tipo java.util.Collection");
            }
        } else {
            obj = pageContext.getSession().getAttribute(this.getCollection());
            if (obj != null) {
                if (obj instanceof Collection) {
                    return (Collection<Object>) obj;
                } else {
                    throw new JspException("Objeto " + this.getCollection() + " deve ser do tipo java.util.Collection");
                }
            } else {
                if (makeNull) {
                    throw new JspException("Objeto " + this.getCollection() + " não encontrado em nenhum escopo");
                } else {
                    return null;
                }
            }
        }
    }

}
