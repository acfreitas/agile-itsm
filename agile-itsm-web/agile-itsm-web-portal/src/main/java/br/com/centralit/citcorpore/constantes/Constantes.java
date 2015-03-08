package br.com.centralit.citcorpore.constantes;

/**
 * @author Central IT
 */
public class Constantes {

    public static boolean LOADED = false;
    // Definicoes para usuarios
    public static final String USUARIO_INDEFINIDO = "0";
    public static final String USUARIO_NORMAL = "1";
    public static final String USUARIO_PUBLICO = "2";

    public static final String FORM_NORMAL = "0";
    public static final String MULT_FORM_DATA = "1";

    public static final String VERSAO = "CITSmart V.1.0 Beta";

    public static final String SIMBOLO_MOEDA = "R$";
    public static final String SENHA_PADRAO = "mudar";

    public static final boolean IMPRIMIR_ERROS = true;

    public static String RAIZ_SITE = br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + "/citcorpore";

    public static String PROTOCOLO = "http://";

    public static String ENDERECO_SITE = br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS");

    public static boolean IMPRIMIR_FORMULARIO_POSTADO = false;

    // Controle de erros - redirecionamentos
    public static String PAGINA_LOGIN = RAIZ_SITE + "/login.jsp";
    public static String PAGINA_SEM_ACESSO = RAIZ_SITE + "/erro/semacesso.jsp";

    // Outras Constantes
    public static final String SPACE = " ";
    public static final String DESC = "desc";
    public static final String CARACTER_PESQUISA = "%";
    public static final String CARACTER_SEPARADOR = "\5";

    public static final String CARACTER_SEPARADOR_PIPE = "|";

    public static final String INSERT = "I";
    public static final String UPDATE = "U";

    public static final String EMAIL_DE = "local@portal.com.br";

    public static final int INTERVALO_PADRAO = 15; // 15 minutos

    public static final int CODIGO_CONVENIO_PARTICULAR = 1;

    /**
     * Definição de campos utilizados no sistema.
     */
    public static final int CAMPO_TEXTO = 1;
    public static final int CAMPO_NUMERO = 2;
    public static final int CAMPO_DATA = 3;
    public static final int CAMPO_LISTA = 4;
    public static final int CAMPO_TABELA = 5;

    public static final String ESTILO_LISTA = "L";
    public static final String ESTILO_RADIO = "R";
    public static final String ESTILO_CHECK = "C";

    public static final int SEQUENCIA_OUTROS = 1000;
    public static final String TEXTO_OUTROS = "Outro";

    public static final int TEMPLATE_APLICACAO_ANAMNESE = 1;

    public static String CAMINHO_GERAL_FOTO_INDV = "/empresas";
    public static String CAMINHO_GERAL_TMP_IMG = "/empresas";
    public static String CAMINHO_GERAL_BD_IMG = "/empresas";
    public static String CAMINHO_JASPER = "/citsmart/tempFiles/";

    public static final int CID_9 = 9;
    public static final int CID_10 = 10;

    public static final int CODIGO_CH = 9;

    public static final Integer OPER_AGENDAMENTO = 0x1;
    public static final Integer OPER_REMARCACAO = 0x2;
    public static final Integer OPER_DESMARCACAO = 0x3;

}
