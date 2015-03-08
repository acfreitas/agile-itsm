/*
 * Created on 12/01/2006
 *

 * Window - Preferences - Java - Code Style - Code Templates
 */
package br.com.citframework.tld;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.html.TextTag;

import br.com.citframework.util.Reflexao;
import br.com.citframework.util.converter.ConverterUtils;

/**
 * @author ney
 * 
 Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class TextFormat extends TextTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8053748134801507517L;
	private String	decimal;
	private String	mask;

	public String getDecimal() {
		return decimal;
	}
	public void setDecimal(String decimal) {
		this.decimal = decimal;
	}
	public String getMask() {
		return mask;
	}
	public void setMask(String mask) {
		this.mask = mask;
	}

	public int doStartTag() throws JspException {
		
		try {
			// Edita Máscaras
			if (mask != null && mask.length() > 0) {
				// System.out.println("Possui mascara");
				if (getOnkeypress() == null || getOnkeypress().length() == 0) {
					// System.out.println("Vai formatar");
					setOnkeypress("return formataCampo(document." + getName() + ", '" + getProperty() + "', '" + getMask().replaceAll("d", "9").replaceAll("M", "9").replaceAll("y", "9") + "', event);");
					setMaxlength(mask.length() + "");
				} else {

				}
				// System.out.println("key press :"+getOnkeypress());

			}

			if (getProperty().toUpperCase().indexOf("CNPJ") > -1) {
				String mascaraCnpj = "99.999.999/9999-99";
				setOnkeypress("return formataCampo(document." + getName() + ", '" + getProperty() + "', '" + mascaraCnpj + "', event);");
				setMaxlength(mascaraCnpj.length() + "");

			}

			if (getProperty().toUpperCase().indexOf("CPF") > -1) {
				String mascaraCpf = "999.999.999-99";
				setOnkeypress("return formataCampo(document." + getName() + ", '" + getProperty() + "', '" + mascaraCpf + "', event);");
				setMaxlength(mascaraCpf.length() + "");

			}

			if (getProperty().toUpperCase().indexOf("CEP") > -1) {
				String mascaraCep = "99.999-999";
				setOnkeypress("return formataCampo(document." + getName() + ", '" + getProperty() + "', '" + mascaraCep + "', event);");
				setMaxlength(mascaraCep.length() + "");

			}

			setValue(null);
			// System.out.println("Etapa 1");
			if ((decimal == null || decimal.length() == 0) && (mask == null || mask.length() == 0))
				return super.doStartTag();

			String formName = getName();
			// System.out.println("Etapa 2"+formName);
			Object obj = pageContext.getRequest().getAttribute(formName);
			if (obj == null) {
				// System.out.println("Etapa 3");
				obj = pageContext.getSession().getAttribute(formName);
				if (obj == null) {
					return super.doStartTag();
				}
			}
			// System.out.println("Etapa 4");
			Object val = Reflexao.getPropertyValue(obj, getProperty());
			// System.out.print("Atrb: "+getProperty()+" Valor:"+val);

			if (val == null || val.toString().trim().length() == 0) {
				return super.doStartTag();
			}

			// System.out.println("Etapa 5");
			if (decimal != null && decimal.length() > 0) {
				// System.out.println("Etapa 6");
				/*
				 * if(getDecimal().equals("0")){ String tmp = null;
				 * if(val.toString().indexOf(".")>-1){ tmp =
				 * val.toString().substring(0,val.toString().indexOf("."));
				 * }else{ tmp = val.toString(); }
				 * 
				 * 
				 * Integer result = new Integer(tmp);
				 * setValue(result.toString());
				 * 
				 *  }
				 */
				if (ConverterUtils.possuiMascara(val.toString())) {
					val = ConverterUtils.retiraMascara(val.toString());

				}
				if (val == null || val.toString().trim().length() == 0) {
					return super.doStartTag();
				}

				if (val.toString().indexOf(",") > -1) {
					return super.doStartTag();
				}
				BigDecimal valor = new BigDecimal(val.toString());
				NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));
				nf.setMaximumFractionDigits(new Integer(decimal).intValue());
				nf.setMinimumFractionDigits(new Integer(decimal).intValue());
				String tmp = nf.format(valor);
				if (getDecimal().equals("0") && tmp.indexOf(".") > -1) {
					// System.out.print("Atrb_ant: "+getProperty()+"
					// Valor:"+tmp+" ind "+toString().indexOf("."));
					StringTokenizer tok = new StringTokenizer(tmp, ".");
					String tmp2 = "";
					while (tok.hasMoreTokens()) {
						tmp2 += tok.nextToken();
					}

					// System.out.print("AtrbFormatado: "+getProperty()+"
					// Valor:"+tmp2);
					tmp = tmp2;
					if (getProperty().toUpperCase().indexOf("CGC") > -1) {
						String mascaraCnpj = "##.###.###/####-##";
						tmp = ConverterUtils.aplicaMascara(tmp, mascaraCnpj);

					}else if (getProperty().toUpperCase().indexOf("CNPJ") > -1) {
						String mascaraCnpj = "##.###.###/####-##";
						tmp = ConverterUtils.aplicaMascara(tmp, mascaraCnpj);

					} else if (getProperty().toUpperCase().indexOf("CPF") > -1) {
						String mascaraCpf = "###.###.###-##";
						tmp = ConverterUtils.aplicaMascara(tmp, mascaraCpf);
					} else if (getProperty().toUpperCase().indexOf("CEP") > -1) {
						String mascaraCpf = "##.###-###";
						tmp = ConverterUtils.aplicaMascara(tmp, mascaraCpf);
					}

				}

				setValue(tmp);
				// System.out.println("Etapa 7 :"+nf.format(valor));

			} else if (mask != null && mask.length() > 0) {
				if (val.toString().indexOf("-") > -1) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					java.util.Date dat = sdf.parse(val.toString());
					sdf = new SimpleDateFormat(mask);
					setValue(sdf.format(dat));

				} else
					setValue(val.toString());

			}

			return super.doStartTag();
		} catch (Exception e) {

			throw new JspException(e);
		}
	}

	public int doEndTag() throws JspException {
		
		int result = super.doEndTag();
		setOnkeypress(null);
		return result;
	}

}
