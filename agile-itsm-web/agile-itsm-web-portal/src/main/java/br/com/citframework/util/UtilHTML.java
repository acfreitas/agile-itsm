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
			case '¹':
				encoded.append("&sup1;");
				break;
			case '²':
				encoded.append("&sup2;");
				break;	
			case '³':
				encoded.append("&sup3;");
				break;	
			case 'º':
				encoded.append("&ordm;");
				break;
			case '°':
				encoded.append("&deg;");
				break;
			case 'ç':
				encoded.append("&ccedil;");
				break;
			case 'Ç':
				encoded.append("&Ccedil;");
				break;
			case 'ª':
				encoded.append("<sup>a</sup>");
				break;
			case 'ñ':
				encoded.append("&ntilde;");
				break;
			case 'Ñ':
				encoded.append("&Ntilde;");
				break;
			case 'ý':
				encoded.append("&yacute;");
				break;
			case 'Ý':
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
			case 'á':
			case 'Á':
			case 'é':
			case 'É':
			case 'í':
			case 'Í':
			case 'ó':
			case 'Ó':
			case 'ú':
			case 'Ú':
				encoded.append("&" + getLetraCorrespondente(c) + "acute;");
				break;
			case 'â':
			case 'Â':
			case 'ê':
			case 'Ê':
			case 'î':
			case 'Î':
			case 'ô':
			case 'Ô':
			case 'û':
			case 'Û':
				encoded.append("&" + getLetraCorrespondente(c) + "circ;");
				break;
			case 'ã':
			case 'Ã':
			case 'õ':
			case 'Õ':
				encoded.append("&" + getLetraCorrespondente(c) + "tilde;");
				break;
			case 'à':
			case 'À':
			case 'è':
			case 'È':
			case 'ì':
			case 'Ì':
			case 'ò':
			case 'Ò':
			case 'ù':
			case 'Ù':
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
			case '¹':
				encoded.append("&sup1;");
				break;
			case '²':
				encoded.append("&sup2;");
				break;	
			case '³':
				encoded.append("&sup3;");
				break;	
			case 'º':
				encoded.append("&ordm;");
				break;
			case '°':
				encoded.append("&deg;");
				break;
			case 'ç':
				encoded.append("&ccedil;");
				break;
			case 'Ç':
				encoded.append("&Ccedil;");
				break;
			case 'ª':
				encoded.append("<sup>a</sup>");
				break;
			case 'ñ':
				encoded.append("&ntilde;");
				break;
			case 'Ñ':
				encoded.append("&Ntilde;");
				break;
			case 'ý':
				encoded.append("&yacute;");
				break;
			case 'Ý':
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
			case 'á':
			case 'Á':
			case 'é':
			case 'É':
			case 'í':
			case 'Í':
			case 'ó':
			case 'Ó':
			case 'ú':
			case 'Ú':
				encoded.append("&" + getLetraCorrespondente(c) + "acute;");
				break;
			case 'â':
			case 'Â':
			case 'ê':
			case 'Ê':
			case 'î':
			case 'Î':
			case 'ô':
			case 'Ô':
			case 'û':
			case 'Û':
				encoded.append("&" + getLetraCorrespondente(c) + "circ;");
				break;
			case 'ã':
			case 'Ã':
			case 'õ':
			case 'Õ':
				encoded.append("&" + getLetraCorrespondente(c) + "tilde;");
				break;
			case 'à':
			case 'À':
			case 'è':
			case 'È':
			case 'ì':
			case 'Ì':
			case 'ò':
			case 'Ò':
			case 'ù':
			case 'Ù':
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
		if (c == 'á' || c == 'â' || c == 'ã') {
			return "a";
		}
		if (c == 'à') {
			return "a";
		} else if (c == 'Á' || c == 'Â' || c == 'Ã') {
			return "A";
		} else if (c == 'À') {
			return "A";
		} else if (c == 'é' || c == 'ê' || c == 'è') {
			return "e";
		} else if (c == 'É' || c == 'Ê' || c == 'È') {
			return "E";
		} else if (c == 'í' || c == 'î' || c == 'ì') {
			return "i";
		} else if (c == 'Í' || c == 'Î' || c == 'Ì') {
			return "I";
		} else if (c == 'ó' || c == 'ô' || c == 'õ' || c == 'ò') {
			return "o";
		} else if (c == 'Ó' || c == 'Ô' || c == 'Õ' || c == 'Ò') {
			return "O";
		} else if (c == 'ú' || c == 'û' || c == 'ù') {
			return "u";
		} else if (c == 'Ú' || c == 'Û' || c == 'Ù') {
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

		msg = msg.replaceAll("&ccedil;", "ç");
		msg = msg.replaceAll("&Ccedil;", "Ç");

		msg = msg.replaceAll("&aacute;", "á");
		msg = msg.replaceAll("&Aacute;", "Á");
		msg = msg.replaceAll("&eacute;", "é");
		msg = msg.replaceAll("&Eacute;", "É");
		msg = msg.replaceAll("&iacute;", "í");
		msg = msg.replaceAll("&Iacute;", "Í");
		msg = msg.replaceAll("&oacute;", "o");
		msg = msg.replaceAll("&Oacute;", "Ó");
		msg = msg.replaceAll("&uacute;", "ú");
		msg = msg.replaceAll("&Uacute;", "Ú");

		msg = msg.replaceAll("&acirc;", "â");
		msg = msg.replaceAll("&Acirc;", "Â");
		msg = msg.replaceAll("&ecirc;", "ê");
		msg = msg.replaceAll("&Ecirc;", "Ê");
		msg = msg.replaceAll("&icirc;", "î");
		msg = msg.replaceAll("&Icirc;", "Î");
		msg = msg.replaceAll("&ocirc;", "ô");
		msg = msg.replaceAll("&Ocirc;", "Ô");
		msg = msg.replaceAll("&ucirc;", "û");
		msg = msg.replaceAll("&Ucirc;", "Û");

		msg = msg.replaceAll("&atilde;", "ã");
		msg = msg.replaceAll("&Atilde;", "Ã");
		msg = msg.replaceAll("&otilde;", "õ");
		msg = msg.replaceAll("&Otilde;", "Õ");

		msg = msg.replaceAll("&agrave;", "à");
		msg = msg.replaceAll("&Agrave;", "À");
		msg = msg.replaceAll("&egrave;", "é");
		msg = msg.replaceAll("&Egrave;", "É");
		msg = msg.replaceAll("&igrave;", "í");
		msg = msg.replaceAll("&Igrave;", "Í");
		msg = msg.replaceAll("&ograve;", "o");
		msg = msg.replaceAll("&Ograve;", "Ó");
		msg = msg.replaceAll("&ugrave;", "ú");
		msg = msg.replaceAll("&Ugrave;", "Ú");

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