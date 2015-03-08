package br.com.citframework.util;

/**
 * Faz o encoding do html
 * Rodrigo Pecci Acorse / 03/12/2013 / Adicionado outros tratamentos de caracteres e para crase
 *
 */
public class UtilHTML {
	public static final String encodeHTML(String string) {
		if (string == null) {
			return "";
		}
		char c;
		int length = string.length();
		StringBuilder encoded = new StringBuilder(2 * length);
		for (int i = 0; i < length; i++) {
			c = string.charAt(i);
			switch (c) {
			case '�':
				encoded.append("&sup1;");
				break;
			case '�':
				encoded.append("&sup2;");
				break;	
			case '�':
				encoded.append("&sup3;");
				break;	
			case '�':
				encoded.append("&ordm;");
				break;
			case '�':
				encoded.append("&deg;");
				break;
			case '�':
				encoded.append("&ccedil;");
				break;
			case '�':
				encoded.append("&Ccedil;");
				break;
			case '�':
				encoded.append("<sup>a</sup>");
				break;
			case '�':
				encoded.append("&ntilde;");
				break;
			case '�':
				encoded.append("&Ntilde;");
				break;
			case '�':
				encoded.append("&yacute;");
				break;
			case '�':
				encoded.append("&Yacute;");
				break;
			case '!':
				encoded.append("&#33;");
				break;
			case '#':
				encoded.append("&#35;");
				break;
			case '*':
				encoded.append("&#42;");
				break;
			case '+':
				encoded.append("&#43;");
				break;
			case '-':
				encoded.append("&#45;");
				break;
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
				encoded.append("&" + getLetraCorrespondente(c) + "acute;");
				break;
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
				encoded.append("&" + getLetraCorrespondente(c) + "circ;");
				break;
			case '�':
			case '�':
			case '�':
			case '�':
				encoded.append("&" + getLetraCorrespondente(c) + "tilde;");
				break;
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
				encoded.append("&" + getLetraCorrespondente(c) + "grave;");
				break;
			default:
				encoded.append(c);
				break;
			}
		}

		String strRet = encoded.toString();
		strRet = strRet.replaceAll(" ", "&nbsp;&nbsp;");

		return strRet;
	}

	public static final String encodeHTMLComEspacos(String string) {
		if (string == null) {
			return "";
		}
		char c;
		int length = string.length();
		StringBuilder encoded = new StringBuilder(2 * length);
		for (int i = 0; i < length; i++) {
			c = string.charAt(i);
			switch (c) {
			case '�':
				encoded.append("&sup1;");
				break;
			case '�':
				encoded.append("&sup2;");
				break;	
			case '�':
				encoded.append("&sup3;");
				break;	
			case '�':
				encoded.append("&ordm;");
				break;
			case '�':
				encoded.append("&deg;");
				break;
			case '�':
				encoded.append("&ccedil;");
				break;
			case '�':
				encoded.append("&Ccedil;");
				break;
			case '�':
				encoded.append("<sup>a</sup>");
				break;
			case '�':
				encoded.append("&ntilde;");
				break;
			case '�':
				encoded.append("&Ntilde;");
				break;
			case '�':
				encoded.append("&yacute;");
				break;
			case '�':
				encoded.append("&Yacute;");
				break;
			case '!':
				encoded.append("&#33;");
				break;
			case '#':
				encoded.append("&#35;");
				break;
			case '*':
				encoded.append("&#42;");
				break;
			case '+':
				encoded.append("&#43;");
				break;
			case '-':
				encoded.append("&#45;");
				break;
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
				encoded.append("&" + getLetraCorrespondente(c) + "acute;");
				break;
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
				encoded.append("&" + getLetraCorrespondente(c) + "circ;");
				break;
			case '�':
			case '�':
			case '�':
			case '�':
				encoded.append("&" + getLetraCorrespondente(c) + "tilde;");
				break;
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
			case '�':
				encoded.append("&" + getLetraCorrespondente(c) + "grave;");
				break;
			default:
				encoded.append(c);
				break;
			}
		}

		String strRet = encoded.toString();
		return strRet;
	}

	/**
	 * Pega a letra correspondente ao caracter. Para gerar o codigo HTML
	 * correspondente.
	 * 
	 * Rodrigo Pecci Acorse / 03/12/2013 / Adicionado outros tratamentos para crase
	 * 
	 * @param c
	 * @return
	 */
	public static String getLetraCorrespondente(char c) {
		if (c == '�' || c == '�' || c == '�') {
			return "a";
		}
		if (c == '�') {
			return "a";
		} else if (c == '�' || c == '�' || c == '�') {
			return "A";
		} else if (c == '�') {
			return "A";
		} else if (c == '�' || c == '�' || c == '�') {
			return "e";
		} else if (c == '�' || c == '�' || c == '�') {
			return "E";
		} else if (c == '�' || c == '�' || c == '�') {
			return "i";
		} else if (c == '�' || c == '�' || c == '�') {
			return "I";
		} else if (c == '�' || c == '�' || c == '�' || c == '�') {
			return "o";
		} else if (c == '�' || c == '�' || c == '�' || c == '�') {
			return "O";
		} else if (c == '�' || c == '�' || c == '�') {
			return "u";
		} else if (c == '�' || c == '�' || c == '�') {
			return "U";
		} else {
			char auxChar[] = new char[1];
			auxChar[0] = c;
			String aux = new String(auxChar);
			return aux;
		}
	}

	public static final String decodeHTML(String msg) {

		msg = msg.replaceAll("&nbsp;", " ");

		msg = msg.replaceAll("&ccedil;", "�");
		msg = msg.replaceAll("&Ccedil;", "�");

		msg = msg.replaceAll("&aacute;", "�");
		msg = msg.replaceAll("&Aacute;", "�");
		msg = msg.replaceAll("&eacute;", "�");
		msg = msg.replaceAll("&Eacute;", "�");
		msg = msg.replaceAll("&iacute;", "�");
		msg = msg.replaceAll("&Iacute;", "�");
		msg = msg.replaceAll("&oacute;", "o");
		msg = msg.replaceAll("&Oacute;", "�");
		msg = msg.replaceAll("&uacute;", "�");
		msg = msg.replaceAll("&Uacute;", "�");

		msg = msg.replaceAll("&acirc;", "�");
		msg = msg.replaceAll("&Acirc;", "�");
		msg = msg.replaceAll("&ecirc;", "�");
		msg = msg.replaceAll("&Ecirc;", "�");
		msg = msg.replaceAll("&icirc;", "�");
		msg = msg.replaceAll("&Icirc;", "�");
		msg = msg.replaceAll("&ocirc;", "�");
		msg = msg.replaceAll("&Ocirc;", "�");
		msg = msg.replaceAll("&ucirc;", "�");
		msg = msg.replaceAll("&Ucirc;", "�");

		msg = msg.replaceAll("&atilde;", "�");
		msg = msg.replaceAll("&Atilde;", "�");
		msg = msg.replaceAll("&otilde;", "�");
		msg = msg.replaceAll("&Otilde;", "�");

		msg = msg.replaceAll("&agrave;", "�");
		msg = msg.replaceAll("&Agrave;", "�");
		msg = msg.replaceAll("&egrave;", "�");
		msg = msg.replaceAll("&Egrave;", "�");
		msg = msg.replaceAll("&igrave;", "�");
		msg = msg.replaceAll("&Igrave;", "�");
		msg = msg.replaceAll("&ograve;", "o");
		msg = msg.replaceAll("&Ograve;", "�");
		msg = msg.replaceAll("&ugrave;", "�");
		msg = msg.replaceAll("&Ugrave;", "�");

		return msg;

	}

	public static final String retiraFormatacaoHTML(String str) {
		str = str.replaceAll("<p>", "");
		str = str.replaceAll("</p>", "");

		str = str.replaceAll("<br>", "\n");
		str = str.replaceAll("</br>", "\n");
		str = str.replaceAll("<br />", "\n");
		str = str.replaceAll("<br/>", "\n");

		str = str.replaceAll("<b>", "");
		str = str.replaceAll("</b>", "");

		str = str.replaceAll("<i>", "");
		str = str.replaceAll("</i>", "");

		str = str.replaceAll("<s>", "");
		str = str.replaceAll("</s>", "");

		str = str.replaceAll("<u>", "");
		str = str.replaceAll("</u>", "");

		str = str.replaceAll("<em>", "");
		str = str.replaceAll("</em>", "");

		str = str.replaceAll("<strong>", "");
		str = str.replaceAll("</strong>", "");

		str = str.replaceAll("<span>", "");
		str = str.replaceAll("</span>", "");

		str = str.replaceAll("<html>", "");
		str = str.replaceAll("<body>", "");
		str = str.replaceAll("<head>", "");

		str = str.replaceAll("<font>", "");
		str = str.replaceAll("</font>", "");

		return str;
	}

}