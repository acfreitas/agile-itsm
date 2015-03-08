package br.com.centralit.citajax.html;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;

import br.com.centralit.citajax.reflexao.CitAjaxReflexao;
import br.com.centralit.citajax.util.CitAjaxWebUtil;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.citframework.dto.IDto;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilFormatacao;
import br.com.citframework.util.UtilStrings;

public class HTMLForm {

    private DocumentHTML document;

    private String name;
    private String action;
    private String target;
    private String encoding;

    public HTMLForm() {}

    public HTMLForm(final String nameForm, final DocumentHTML documentParm) {
        this.setName(nameForm);
        this.setDocument(documentParm);
    }

    protected void setCommandExecute(final String comand) {
        document.getComandsExecute().add(comand);
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getAction() {
        return action;
    }

    public void setAction(final String action) {
        this.action = action;
        this.setCommandExecute("document." + this.getName() + ".action='" + action + "'");
    }

    public DocumentHTML getDocument() {
        return document;
    }

    public void setDocument(final DocumentHTML document) {
        this.document = document;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(final String target) {
        this.target = target;
        this.setCommandExecute("document." + this.getName() + ".target='" + target + "'");
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(final String encoding) {
        this.encoding = encoding;
        this.setCommandExecute("document." + this.getName() + ".encoding='" + encoding + "'");
    }

    public void lockForm() {
        this.setCommandExecute("HTMLUtils.lockForm(document." + this.getName() + ")");
    }

    public void unLockForm() {
        this.setCommandExecute("HTMLUtils.unLockForm(document." + this.getName() + ")");
    }

    public void clear() {
        this.setCommandExecute("HTMLUtils.clearForm(document." + this.getName() + ")");
    }

    public void lockButtonsForm(final String[] excep) {
        String str = "";
        if (excep != null) {
            for (final String element : excep) {
                if (!str.equalsIgnoreCase("")) {
                    str += ",";
                }
                str += "'" + element + "'";
            }
        }
        if (!str.equalsIgnoreCase("")) {
            str = "[" + str + "]";
        } else {
            str = "null";
        }
        this.setCommandExecute("HTMLUtils.lockButtonsForm(document." + this.getName() + ", " + str + ")");
    }

    public void unLockButtonsForm(final String[] excep) {
        String str = "";
        if (excep != null) {
            for (final String element : excep) {
                if (!str.equalsIgnoreCase("")) {
                    str += ",";
                }
                str += "'" + element + "'";
            }
        }
        if (!str.equalsIgnoreCase("")) {
            str = "[" + str + "]";
        } else {
            str = "null";
        }
        this.setCommandExecute("HTMLUtils.unlockButtonsForm(document." + this.getName() + ", " + str + ")");
    }

    public void setValues(final IDto bean) {
        this.setValues(bean, true);
    }

    public void setValues(final IDto bean, final Boolean formatDouble) {
        final List listSets = CitAjaxReflexao.findSets(bean);
        this.setCommandExecute("document.fAux_HTMLForm_temp = HTMLUtils.getForm()");
        this.setCommandExecute("HTMLUtils.setForm(document." + this.getName() + ")");
        for (int i = 0; i < listSets.size(); i++) {
            String property = (String) listSets.get(i);
            Object valor = null;
            try {
                valor = CitAjaxReflexao.getPropertyValue(bean, property);
            } catch (final Exception e) {
                e.printStackTrace();
                System.out.println("Erro ao obter valor da propriedade: " + bean.getClass().getName() + " - " + property);
            }
            property = UtilStrings.convertePrimeiraLetra(property, "L");

            String valorTransf = null;
            if (valor instanceof BigInteger) {
                valorTransf = UtilFormatacao.formatInt(((BigInteger) valor).intValue(), "################");
            }

            if (valor instanceof Long) {
                valorTransf = UtilFormatacao.formatInt(((Long) valor).intValue(), "################");
            }
            if (valor instanceof Integer) {
                valorTransf = UtilFormatacao.formatInt(((Integer) valor).intValue(), "################");
            }
            if (valor instanceof Double) {
                if (formatDouble) {
                    valorTransf = UtilFormatacao.formatBigDecimal(new BigDecimal(((Double) valor).doubleValue()), 2);
                } else {
                    final Double nValue = (Double) valor;
                    valorTransf = nValue.toString();
                }
            }
            if (valor instanceof BigDecimal) {
                valorTransf = UtilFormatacao.formatBigDecimal((BigDecimal) valor, 2);
            }
            if (valor instanceof String) {
                valorTransf = (String) valor;
            }
            if (valor instanceof Date) {
                /** Adicionado por valdoilo.damasceno */
                valorTransf = UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, (Date) valor, this.getDocument().getLanguage());
            }
            if (valor instanceof Timestamp) {
                /** Adicionado por valdoilo.damasceno */
                valorTransf = UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, (Timestamp) valor, this.getDocument().getLanguage());
            }

            if (valorTransf != null) {
                this.setCommandExecute("HTMLUtils.setValue('" + property + "', ObjectUtils.decodificaEnter(ObjectUtils.decodificaAspasApostrofe('"
                        + CitAjaxWebUtil.codificaAspasApostrofe(CitAjaxWebUtil.codificaEnter(StringEscapeUtils.escapeJavaScript(valorTransf))) + "')))");
            }
        }
        this.setCommandExecute("HTMLUtils.setForm(document.fAux_HTMLForm_temp)");
    }

    public void setValueText(final String fieldName, final Integer index, final String value) {
        if (index == null) {
            this.setCommandExecute("document." + this.getName() + "." + fieldName + ".value = ObjectUtils.decodificaAspasApostrofe(ObjectUtils.decodificaEnter('"
                    + CitAjaxWebUtil.codificaAspasApostrofe(CitAjaxWebUtil.codificaEnter(value)) + "'))");
        } else {
            this.setCommandExecute("document." + this.getName() + "." + fieldName + "[" + index.intValue()
                    + "].value = ObjectUtils.decodificaAspasApostrofe(ObjectUtils.decodificaEnter('" + CitAjaxWebUtil.codificaAspasApostrofe(CitAjaxWebUtil.codificaEnter(value))
                    + "'))");
        }
    }

}
