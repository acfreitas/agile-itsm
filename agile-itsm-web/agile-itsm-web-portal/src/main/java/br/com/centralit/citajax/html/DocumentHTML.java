package br.com.centralit.citajax.html;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import br.com.centralit.citajax.framework.ParserRequest;
import br.com.centralit.citajax.util.JavaScriptUtil;

public class DocumentHTML {

    private final Map hashElementos;
    private List comandsExecute;
    private final Collection metodosTratamento;
    private int scriptCount;
    private Object bean;
    private Map forms;
    private Map valuesForm = null;
    private Object returnScriptSystem;
    private Object returnScriptUser;
    private boolean ignoreNextMethod;

    /**
     * Adicionado por valdoilo.damasceno
     *
     * @since 03.02.2014
     */
    private String language;

    public DocumentHTML() {
        forms = null;
        hashElementos = new HashMap<>();
        comandsExecute = new ArrayList<>();
        metodosTratamento = new ArrayList<>();
    }

    public HTMLElement getElementById(final String objId) throws Exception {
        return this.getElementById(objId, HTMLElement.class);
    }

    public void setElement(final String objId, final HTMLElement element) {
        if (!hashElementos.containsKey(objId)) {
            hashElementos.put(objId, element);
        }
    }

    private HTMLElement getElementById(final String objId, final Class classeElement) throws Exception {
        HTMLElement element;
        if (hashElementos.containsKey(objId)) {
            element = (HTMLElement) hashElementos.get(objId);
        } else {
            final Class[] parmClassConstrutor = {String.class, DocumentHTML.class};
            Constructor construtor = null;
            try {
                construtor = classeElement.getConstructor(parmClassConstrutor);
            } catch (final SecurityException e) {
                throw new Exception("Problema com o Construtor do Objeto HTML: " + e.getMessage());
            } catch (final NoSuchMethodException e) {
                throw new Exception("Problema com o Construtor do Objeto HTML: " + e.getMessage());
            }
            final Object[] parmsInitObj = {objId, this};
            element = (HTMLElement) construtor.newInstance(parmsInitObj);
            this.setElement(objId, element);
        }
        return element;
    }

    public HTMLSelect getSelectById(final String objId) throws Exception {
        return (HTMLSelect) this.getElementById(objId, HTMLSelect.class);
    }

    public HTMLTextBox getTextBoxById(final String objId) throws Exception {
        return (HTMLTextBox) this.getElementById(objId, HTMLTextBox.class);
    }

    public HTMLTextArea getTextAreaById(final String objId) throws Exception {
        return (HTMLTextArea) this.getElementById(objId, HTMLTextArea.class);
    }

    public HTMLCheckbox getCheckboxById(final String objId) throws Exception {
        return (HTMLCheckbox) this.getElementById(objId, HTMLCheckbox.class);
    }

    public HTMLRadio getRadioById(final String objId) throws Exception {
        return (HTMLRadio) this.getElementById(objId, HTMLRadio.class);
    }

    public HTMLTable getTableById(final String objId) throws Exception {
        return (HTMLTable) this.getElementById(objId, HTMLTable.class);
    }

    public HTMLTreeView getTreeViewById(final String objId) throws Exception {
        return (HTMLTreeView) this.getElementById(objId, HTMLTreeView.class);
    }

    public HTMLJanelaPopup getJanelaPopupById(final String objId) throws Exception {
        return (HTMLJanelaPopup) this.getElementById(objId, HTMLJanelaPopup.class);
    }

    public void executeScript(final String script) {
        scriptCount++;
        comandsExecute.add(script);
    }

    public HTMLForm getForm(final String nameForm) {
        if (forms == null) {
            forms = new HashMap();
        }
        HTMLForm form = (HTMLForm) forms.get(nameForm);
        if (form == null) {
            form = new HTMLForm(nameForm, this);
            forms.put(nameForm, form);
        }
        return form;
    }

    public void focusInFirstActivateField(final HTMLForm form) {
        final List lst = this.getComandsExecute();
        if (form == null) {
            lst.add("HTMLUtils.focusInFirstActivateField()");
        } else {
            lst.add("HTMLUtils.focusInFirstActivateField(document." + form.getName() + ")");
        }
    }

    public void setTitle(final String title) {
        final List lst = this.getComandsExecute();
        lst.add("document.getElementById('spanTitulo').innerHTML = '" + JavaScriptUtil.escapeJavaScript(title) + "'");
    }

    public void alert(final String msg) {
        final List lst = this.getComandsExecute();
        lst.add("alert('" + JavaScriptUtil.escapeJavaScript(msg) + "')");
    }

    public Collection getAllScripts() {
        final Collection col = comandsExecute;
        final Collection colRetorno = new ArrayList();
        ScriptExecute script;

        String mName;
        for (final Iterator it = metodosTratamento.iterator(); it.hasNext();) {
            mName = (String) it.next();
            script = new ScriptExecute();
            if (mName.indexOf("click") > -1) {
                final String nameObj[] = mName.split("_");
                if (nameObj != null && nameObj.length > 0) {
                    script.setScript("addEvent(document.getElementById('" + nameObj[0] + "'), 'click', DEFINEALLPAGES_trataEventos_Click, false)");
                    colRetorno.add(script);
                }
            } else if (mName.indexOf("change") > -1) {
                final String nameObj[] = mName.split("_");
                if (nameObj != null && nameObj.length > 0) {
                    script.setScript("addEvent(document.getElementById('" + nameObj[0] + "'), 'change', DEFINEALLPAGES_trataEventos_Change, false)");
                    colRetorno.add(script);
                }
            }
        }

        for (final Iterator it = col.iterator(); it.hasNext();) {
            script = new ScriptExecute();
            script.setScript((String) it.next());
            colRetorno.add(script);
        }
        return colRetorno;
    }

    public List getComandsExecute() {
        return comandsExecute;
    }

    public void setComandsExecute(final List comandsExecute) {
        this.comandsExecute = comandsExecute;
    }

    public Collection getMetodosTratamento() {
        return metodosTratamento;
    }

    public void setMetodosTratamentoByMethods(final Collection col) {
        Method m;
        for (final Iterator it = col.iterator(); it.hasNext();) {
            m = (Method) it.next();
            metodosTratamento.add(m.getName());
        }
    }

    public Collection getInternalBeanCollection(final String internalForm, final Class classe) {
        final ParserRequest parser = new ParserRequest();
        Collection col = null;
        try {
            col = parser.converteValoresRequestToInternalBean(valuesForm, classe, internalForm);
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
        return col;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(final Object bean) {
        this.bean = bean;
    }

    public Map getForms() {
        return forms;
    }

    public void setForms(final Map forms) {
        this.forms = forms;
    }

    public Map getValuesForm() {
        return valuesForm;
    }

    public void setValuesForm(final Map valuesForm) {
        this.valuesForm = valuesForm;
    }

    public Object getReturnScriptSystem() {
        return returnScriptSystem;
    }

    public void setReturnScriptSystem(final Object returnScriptSystem) {
        this.returnScriptSystem = returnScriptSystem;
    }

    public Object getReturnScriptUser() {
        return returnScriptUser;
    }

    public void setReturnScriptUser(final Object returnScriptUser) {
        this.returnScriptUser = returnScriptUser;
    }

    public boolean isIgnoreNextMethod() {
        return ignoreNextMethod;
    }

    public void setIgnoreNextMethod(final boolean ignoreNextMethod) {
        this.ignoreNextMethod = ignoreNextMethod;
    }

    /**
     * Retorna a language do usuário logado.
     *
     * @return language - Linguagem do usuário logado.
     * @author valdoilo.damasceno
     * @since 03.02.2014
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Atribui valor da language do usuário logado.
     *
     * @param language
     *            - Linguagem do usuário logado.
     * @author valdoilo.damasceno
     * @since 03.02.2014
     */
    public void setLanguage(final String language) {
        this.language = language;
    }
}
