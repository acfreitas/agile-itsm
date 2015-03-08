package br.com.citframework.tld;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import br.com.citframework.util.FindFieldUtil;

public class FindField extends BodyTagSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 259097494202966188L;
	private String lockupName;
	private int top;
	private int left;
	private int len;
	private int heigth;
	private String formName;
	private String javascriptCode;
	private String htmlCode;
	private String id;
	private String value;
	private String text;
	private String disabled;
	private String checkbox;
	
	public int doStartTag() throws JspException {
		FindFieldUtil lckUtil = new FindFieldUtil();
		StringBuilder strBuff;
		try {
			strBuff = lckUtil.generate(pageContext.getRequest(), getLockupName(), getId(), getTop(), getLeft(), getLen(), getHeigth(), getFormName(), getValue(), getText(), getJavascriptCode(), getHtmlCode(), getDisabled(), getCheckbox());
		} catch (SecurityException e1) {
			e1.printStackTrace();
			throw new JspException(e1);
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
			throw new JspException(e1);
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
			throw new JspException(e1);
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
			throw new JspException(e1);
		}
		try {
			pageContext.getOut().println(strBuff.toString());
		} catch (IOException e) {
			e.printStackTrace();
			throw new JspException(e);
		}
		return SKIP_BODY;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public int getHeigth() {
		return heigth;
	}

	public void setHeigth(int heigth) {
		this.heigth = heigth;
	}

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public int getLen() {
		return len;
	}

	public void setLen(int len) {
		this.len = len;
	}

	public String getLockupName() {
		return lockupName;
	}

	public void setLockupName(String lockupName) {
		this.lockupName = lockupName;
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public String getHtmlCode() {
		return htmlCode;
	}

	public void setHtmlCode(String htmlCode) {
		this.htmlCode = htmlCode;
	}

	public String getJavascriptCode() {
		return javascriptCode;
	}

	public void setJavascriptCode(String javascriptCode) {
		this.javascriptCode = javascriptCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public String getCheckbox() {
		return checkbox;
	}

	public void setCheckbox(String checkbox) {
		this.checkbox = checkbox;
	}
}
