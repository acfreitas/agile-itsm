package br.com.centralit.citajax.html;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.lang.StringEscapeUtils;

import br.com.centralit.citajax.reflexao.CitAjaxReflexao;

public class HTMLSelect extends HTMLElement {
	private int iIndice = 0;

	public HTMLSelect(String idParm, DocumentHTML documentParm) {
		super(idParm, documentParm);
		iIndice = 0;
	}

	public String getType() {
		return SELECT;
	}

	public void addOption(String value, String text) {
		setCommandExecute("HTMLUtils.addOption('" + this.getId() + "', '" + text + "', '" + value + "')");
		iIndice++;
	}

	public void addOptionIfNotExists(String value, String text) {
		setCommandExecute("HTMLUtils.addOptionIfNotExists('" + this.getId() + "', '" + text + "', '" + value + "')");
	}

	public void addOptions(Collection colOptions, String namePropertyValue, String namePropertyText, String valueSelected) throws Exception {
		if (colOptions == null)
			return;
		if (namePropertyValue == null)
			return;
		if (namePropertyText == null)
			return;

		Object obj;
		Object value, text;
		int i = iIndice;
		for (Iterator it = colOptions.iterator(); it.hasNext();) {
			obj = it.next();
			value = CitAjaxReflexao.getPropertyValue(obj, namePropertyValue);
			text = CitAjaxReflexao.getPropertyValue(obj, namePropertyText);

			if (value == null) {
				value = "";
			}
			if (text == null) {
				text = "";
			}
			this.addOption(value.toString(), StringEscapeUtils.escapeJavaScript(text.toString()));

			if (valueSelected != null) {
				if (valueSelected.equalsIgnoreCase(value.toString())) {
					this.setSelectedIndex(i);
				}
			}
			i++;
		}
	}

	public boolean addOptions(Collection colOptions, String namePropertyValue, String namePropertyText, String valueSelected, int numberLines) throws Exception {
		if (colOptions == null)
			return false;
		if (namePropertyValue == null)
			return false;
		if (namePropertyText == null)
			return false;

		Object obj;
		Object value, text;
		int i = iIndice;
		for (Iterator it = colOptions.iterator(); it.hasNext();) {
			if (i > numberLines) {
				return true;
			}
			obj = it.next();
			value = CitAjaxReflexao.getPropertyValue(obj, namePropertyValue);
			text = CitAjaxReflexao.getPropertyValue(obj, namePropertyText);

			if (value == null) {
				value = "";
			}
			if (text == null) {
				text = "";
			}
			this.addOption(value.toString(), text.toString());

			if (valueSelected != null) {
				if (valueSelected.equalsIgnoreCase(value.toString())) {
					this.setSelectedIndex(i);
				}
			}
			i++;
		}
		return false;
	}

	public void removeOption(int indice) {
		setCommandExecute("HTMLUtils.removeOption('" + this.getId() + "', " + indice + ")");
	}

	public void removeAllOptions() {
		setCommandExecute("HTMLUtils.removeAllOptions('" + this.getId() + "')");
		iIndice = 0;
	}

	public void removeOptionSelected() {
		setCommandExecute("HTMLUtils.removeOptionSelected('" + this.getId() + "')");
	}

	public void setSelectedIndex(int indice) {
		setCommandExecute("HTMLUtils.setOptionSelected('" + this.getId() + "', " + indice + ")");
	}
}
