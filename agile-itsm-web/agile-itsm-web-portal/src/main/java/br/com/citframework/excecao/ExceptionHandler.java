package br.com.citframework.excecao;

import br.com.citframework.util.Mensagens;

public class ExceptionHandler {

    public static String handleException(Throwable e) {
        String msg = "";

        if (e instanceof LogicException) {
            msg = e.getMessage();
        } else {
            boolean val = true;
            while (val) {
                if (e.getCause() != null && e.getCause() instanceof LogicException) {
                    msg = e.getMessage();
                    val = false;
                } else if (e.getCause() != null && e.getCause() instanceof DuplicateUniqueException) {
                    msg = e.getMessage();
                    msg = msg.substring(msg.indexOf("Field:") + "Field:".length());
                    msg += " " + Mensagens.getValue("MSE01");
                    val = false;
                } else if (e.getCause() != null) {
                    e = e.getCause();
                } else {
                    val = false;
                }
            }
        }

        if (msg.trim().length() == 0) {
            msg = Mensagens.getValue("MSE02");
        }

        return msg;
    }

}
