package br.com.centralit.citajax.util;

public class JavaScriptUtil {
    public static String escapeJavaScript(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder writer = new StringBuilder();
        int sz;
        sz = str.length();
        for (int i = 0; i < sz; i++) {
            char ch = str.charAt(i);
            switch (ch) {
	            case '\b':
	                writer.append('\\');
	                writer.append('b');
	                break;
	            case '\n':
	                writer.append('\\');
	                writer.append('n');
	                break;
	            case '\t':
	                writer.append('\\');
	                writer.append('t');
	                break;
	            case '\f':
	                writer.append('\\');
	                writer.append('f');
	                break;
	            case '\r':
	                writer.append('\\');
	                writer.append('r');
	                break;
	            case '\'':
	                writer.append('\\');
	                writer.append('\'');
	                break;
	            case '"':
	                writer.append('\\');
	                writer.append('"');
	                break;
	            case '\\':
	                writer.append('\\');
	                writer.append('\\');
	                break;
                default :
                    writer.append(ch);
                    break;
            }
        }
        return writer.toString();
    }
}
