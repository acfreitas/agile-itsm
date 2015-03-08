package br.com.citframework.log;

public interface Log {

    String DEBUG = "DEBUG";
    String ERROR = "ERROR";
    String FATAL = "FATAL";
    String INFO = "INFO";
    String WARN = "WARN";
    String SEPARADOR = "#|#";

    void registraLog(String mensagem, Class<?> classe, String tipoMensagem) throws Exception;

    void registraLog(Exception e, Class<?> classe, String tipoMensagem) throws Exception;

}
