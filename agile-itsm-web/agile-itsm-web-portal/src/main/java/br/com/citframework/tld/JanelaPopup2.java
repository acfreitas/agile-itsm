package br.com.citframework.tld;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * @author danillo.lisboa
 * 
 */
public class JanelaPopup2 extends BodyTagSupport {

	private static final long serialVersionUID = 1L;

	private String id;
	private String titulo;

	public int doAfterBody() throws JspException {
		try {
			BodyContent bc = getBodyContent();
			String body = bc.getString();
			body = this.tratarLinhas(body);
			body = this.tratarAspas(body);
			body = this.getDialog(body);
			body = this.getDiv(body);
			JspWriter out = bc.getEnclosingWriter();
			out.print(body);

		} catch (IOException e) {
			throw new JspException("erro: " + e.getMessage());
		}
		return SKIP_BODY;

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String tratarLinhas(String menssagem) {
		menssagem = menssagem.replace("\r\n\t\t\t", "");
		menssagem = menssagem.replace("\n", "");
		menssagem = menssagem.replace("\r", "");
		menssagem = menssagem.replace("\t", "");
		return menssagem;
	}

	public String tratarAspas(String menssagem) {
		menssagem = org.apache.commons.lang.StringEscapeUtils
				.escapeJava(menssagem);
		return menssagem;
	}

	public String getDiv(String body) {

		return "<div id=\"" + this.getId() + "\" title=\" " + this.getTitulo()
				+ "\">\n" + body + "\n</div>";

	}

	public String getDialog(String body) {
		String dialog = null;

		dialog = "<script>" + "$(\'#btn_" + getId() + "\').click(function(){"
				+ "bootbox.dialog(\"" + body + "\", [{"
				+ "\"label\" : \"Sair\"," + "\"class\" : \"btn-default\","
				+ "\"callback\": function() {" + "$.gritter.add({"
				+ "title: \'" + this.getTitulo() + "\',text: \"Cancelado\""
				+ "});" + "}}])});" + "</script>";

		return dialog;
	}

}
