package br.com.centralit.citcorpore.util;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.citcorpore.constantes.Constantes;
import br.com.centralit.citcorpore.exception.BusinessException;
import br.com.centralit.citcorpore.exception.SystemException;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilNumbersAndDecimals;
import br.com.citframework.util.UtilStrings;

/**
 * @author tellus SA
 */
public class Util implements Serializable {

    public static int DIA = 1;
    public static int MES = 2;
    public static int ANO = 3;

    private static final String STMT_EXISTE = "SELECT * FROM T#";

    // Time constants (in milliseconds)
    private static final long SECOND = 1000;
    private static final long MINUTE = 60 * SECOND;
    private static final long HOUR = 60 * MINUTE;
    private static final long DAY = 24 * HOUR;
    private static final long WEEK = 7 * DAY;
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("EEEE, MMM d 'as' h:mm a");
    private static final SimpleDateFormat yesterdayFormatter = new SimpleDateFormat("'Ontém ' h:mm a");

    /**
     * Dependendo do horário retorna a mensagem de saudacao.
     *
     * @return
     */
    public static final String saudacao() {
        final Calendar time = Calendar.getInstance();
        String retorno = null;
        if (time.get(Calendar.HOUR_OF_DAY) >= 6 && time.get(Calendar.HOUR_OF_DAY) < 12) {
            retorno = "Bom dia, ";
        } else {
            if (time.get(Calendar.HOUR_OF_DAY) >= 12 && time.get(Calendar.HOUR_OF_DAY) < 18) {
                retorno = "Boa tarde, ";
            } else {
                retorno = "Boa noite, ";
            }
        }
        return retorno;
    }

    /**
     * Reencaminha para a URL passada como parâmetro.
     *
     * @param url
     */
    public static final void ReencaminhaURL(final HttpServletRequest request, final HttpServletResponse response, final String url) throws ServletException, IOException {
        final RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    /**
     * define o nome do objeto para retorno.
     *
     * @param obj
     * @param acao
     * @return
     */
    public static final String defineNomeObjetoRetorno(final String obj, final String acao) {
        return obj + "." + acao + ".RO";
    }

    /**
     * Cria uma coleção a partir de um array de objetos
     *
     * @param objetos
     * @return
     */
    public static final Collection createCollection(final Object[] objetos) {
        final Collection c = new ArrayList();
        int i;

        for (i = 0; i < c.size(); i++) {
            c.add(objetos[i]);
        }
        return c;
    }

    /**
     * Retorna informações do dia
     *
     * @return
     */
    public static final String getInformacoesDia() {
        String retorno = null;
        final Calendar hoje = Calendar.getInstance();
        final String diaSemana = getDiaSemana(hoje);
        final String nomeMes = getNomeMes(hoje.get(Calendar.MONTH));

        retorno = diaSemana + ", " + hoje.get(Calendar.DAY_OF_MONTH) + " de " + nomeMes + " de " + hoje.get(Calendar.YEAR);
        return retorno;
    }

    /**
     * Pega a atual (hoje)
     *
     * @return
     */
    public static final java.util.Date getDataAtual() {
        final Calendar hoje = Calendar.getInstance();
        return hoje.getTime();
    }

    /**
     * Retorna o nome do mês
     *
     * @param mes
     * @return
     */
    public static final String getNomeMes(final int mes) {
        switch (mes) {
        case Calendar.JANUARY:
            return "Janeiro";
        case Calendar.FEBRUARY:
            return "Fevereiro";
        case Calendar.MARCH:
            return "Março";
        case Calendar.APRIL:
            return "Abril";
        case Calendar.MAY:
            return "Maio";
        case Calendar.JUNE:
            return "Junho";
        case Calendar.JULY:
            return "Julho";
        case Calendar.AUGUST:
            return "Agosto";
        case Calendar.SEPTEMBER:
            return "Setembro";
        case Calendar.OCTOBER:
            return "Outubro";
        case Calendar.NOVEMBER:
            return "Novembro";
        case Calendar.DECEMBER:
            return "Dezembro";

        }
        return null;
    }

    /**
     * Obtem o dia da semana
     *
     * @param dia
     * @return
     */
    public static final String getDiaSemana(final Calendar dia) {
        switch (dia.get(Calendar.DAY_OF_WEEK)) {
        case Calendar.SUNDAY:
            return "Domingo";
        case Calendar.MONDAY:
            return "Segunda-feira";
        case Calendar.TUESDAY:
            return "Terça-feira";
        case Calendar.WEDNESDAY:
            return "Quarta-feira";
        case Calendar.THURSDAY:
            return "Quinta-feira";
        case Calendar.FRIDAY:
            return "Sexta-feira";
        case Calendar.SATURDAY:
            return "Sabado";
        }
        return null;
    }

    /**
     * Formata HTML, segundo caracteres especiais
     *
     * @param string
     * @return
     */
    public static final String encodeHTML(final String string) {
        if (string == null) {
            return null;
        }
        char c;
        final int length = string.length();
        final StringBuilder encoded = new StringBuilder(2 * length);
        for (int i = 0; i < length; i++) {
            c = string.charAt(i);
            if (c == 'ç') {
                encoded.append("&ccedil;");
            } else if (c == 'Ç') {
                encoded.append("&Ccedil;");
            } else if (c == 'á' || c == 'Á' || c == 'é' || c == 'É' || c == 'í' || c == 'Í' || c == 'ó' || c == 'Ó' || c == 'ú' || c == 'Ú') {
                encoded.append("&" + getLetraCorrespondente(c) + "acute;");
            } else if (c == 'â' || c == 'Â' || c == 'ê' || c == 'Ê' || c == 'î' || c == 'Î' || c == 'ô' || c == 'Ô' || c == 'û' || c == 'Û') {
                encoded.append("&" + getLetraCorrespondente(c) + "circ;");
            } else if (c == 'ã' || c == 'Ã' || c == 'õ' || c == 'Õ') {
                encoded.append("&" + getLetraCorrespondente(c) + "tilde;");
            } else {
                encoded.append(c);
            }
        }
        return Util.replaceInvalid(encoded.toString());
    }

    /**
     * Pega a letra correspondente ao caracter.
     *
     * @param c
     * @return
     */
    public static String getLetraCorrespondente(final char c) {
        if (c == 'á' || c == 'â' || c == 'ã') {
            return "a";
        } else if (c == 'Á' || c == 'Â' || c == 'Ã') {
            return "A";
        } else if (c == 'é' || c == 'ê') {
            return "e";
        } else if (c == 'É' || c == 'Ê') {
            return "E";
        } else if (c == 'í' || c == 'î') {
            return "i";
        } else if (c == 'Í' || c == 'Î') {
            return "I";
        } else if (c == 'ó' || c == 'ô' || c == 'õ') {
            return "o";
        } else if (c == 'Ó' || c == 'Ô' || c == 'Õ') {
            return "O";
        } else if (c == 'ú' || c == 'û') {
            return "u";
        } else if (c == 'Ú' || c == 'Û') {
            return "U";
        } else {
            final char auxChar[] = new char[1];
            auxChar[0] = c;
            final String aux = new String(auxChar);
            return aux;
        }
    }

    /**
     * Converte o texto para HTML
     *
     * @param textoConverter
     * @return
     */
    public static String converterTextoInstitucionalHTML(final String textoConverter) {
        String retorno;

        retorno = textoConverter;
        retorno = retorno.replaceAll("&#39;", "" + (char) 39);
        retorno = retorno.replaceAll("left", "justify");
        retorno = retorno.replaceAll("<P>", "<p align='justify'>");
        retorno = retorno.replaceAll("MARGIN-RIGHT:", "text-align:justify:");
        retorno = retorno.replaceAll(": 0px", "");
        retorno = retorno.replaceAll("<A href=\"file://", "<A Target=_blank href=\"http://");

        retorno = Util.encodeHTML(retorno);

        return retorno;
    }

    public static String converterTextoHTML(final String textoConverter) {
        return converterTextoInstitucionalHTML(textoConverter);
    }

    public static String converterHtmlText(final String texto) {
        String retorno;

        retorno = texto;
        retorno = retorno.replaceAll("&#34;", "" + (char) 34);
        retorno = retorno.replaceAll("<br>", "" + (char) 10);
        retorno = retorno.replaceAll("<BR>", "" + (char) 10);
        retorno = retorno.replaceAll("<Br>", "" + (char) 10);
        retorno = retorno.replaceAll("<bR>", "" + (char) 10);

        return retorno;
    }

    public static String converterHtmlQuadro(final String texto) {
        String retorno;

        retorno = texto;
        if (retorno != null) {
            retorno = retorno.replaceAll("&#34;", "" + (char) 34);
            retorno = retorno.replaceAll("<A href=\"file://", "<A Target=_blank href=\"http://");
        }

        return retorno;
    }

    public static String converterParaHD(final String texto) {
        String retorno;

        if (texto == null) {
            return "";
        }

        retorno = texto;
        retorno = retorno.replaceAll("" + (char) 34, "&#34;");
        retorno = retorno.replaceAll("" + (char) 10, "<br>");
        retorno = retorno.replaceAll("" + (char) 39, "&#39;");

        retorno = encodeHTML(retorno);

        return retorno;
    }

    /**
     * gera o nome da pasta, retirando os caracteres não utiliizaveis.
     *
     * @param texto
     * @return
     */
    public static String getNomePasta(final String texto) {
        return removeSpecialChar(texto);
    }

    /**
     * Remove os caracteres especiais.
     *
     * @param texto
     * @return
     */
    public static String removeSpecialChar(final String texto) {
        String string;

        int i;
        char letra;
        string = texto;
        String textoValido = "";
        for (i = 0; i < string.length(); i++) {
            letra = string.charAt(i);
            if (letra == ' ' || letra == '>' || letra == '<' || letra == '*' || letra == '|' || letra == '\\' || letra == '/' || letra == ':' || letra == '&' || letra == '#'
                    || letra == '$' || letra == '@' || letra == '!' || letra == '"' || letra == '\'' || letra == '?' || letra == ';') {
                textoValido = textoValido + '_';
            } else {
                textoValido = textoValido + Util.getLetraCorrespondente(letra);
            }
        }
        return textoValido;
    }

    /**
     * Apaga os diretorios e subdiretorios informados
     *
     * @param path
     * @return
     */
    public static boolean deleteDiretorioAndSubdiretorios(final String path) {
        final File diretorio = new File(path);
        File fileDir;

        if (diretorio.isDirectory()) {
            if (diretorio.exists()) {
                final File[] filesDiretorios = diretorio.listFiles();
                for (final File filesDiretorio : filesDiretorios) {
                    fileDir = filesDiretorio;
                    if (fileDir.isDirectory()) {
                        deleteDiretorioAndSubdiretorios(fileDir.getPath());
                    } else {
                        fileDir.delete();
                    }
                }
                return diretorio.delete();
            }
        }

        return false;
    }

    /**
     * Cria diretorio, caso não exista.
     *
     * @param path
     * @return
     */
    public static boolean createDiretorio(final String path) {
        final File diretorio = new File(path);
        if (!diretorio.exists()) {
            return diretorio.mkdirs();
        }
        return true;
    }

    /**
     * Apaga o arquivo especificado
     *
     * @param arquivo
     * @return
     */
    public static boolean deleteFile(final String arquivoStr) {
        final File arquivo = new File(arquivoStr);
        if (arquivo.isFile()) {
            return arquivo.delete();
        }
        return false;
    }

    /**
     * Pega o nome do arquivo
     *
     * @return
     */
    public static String getNameFile(final String fullPathFile) {
        final int tam = fullPathFile.length() - 1;
        String nomeFile = "";
        for (int i = tam; i >= 0; i--) {
            if (fullPathFile.charAt(i) == '\\' || fullPathFile.charAt(i) == '/') {
                break;
            } else {
                nomeFile = fullPathFile.charAt(i) + nomeFile;
            }
        }
        return nomeFile;
    }

    public static String replaceInvalid(final String string) {
        String retorno = "";
        retorno = string.replace((char) 150, '-');
        retorno = retorno.replace((char) 147, '"');
        retorno = retorno.replace((char) 148, '"');
        retorno = retorno.replace((char) 8220, '"');
        retorno = retorno.replace((char) 8221, '"');
        retorno = retorno.replace((char) 8211, '-');
        retorno = retorno.replace((char) 8217, ' ');

        return retorno;
    }

    public static String pegaCor(final String qualAba, final String aba) {
        String cor = "#F2F2F2";
        if (qualAba.equalsIgnoreCase("receita")) {
            if (aba.equalsIgnoreCase(qualAba)) {
                cor = "#99CCCC";
            }
        } else if (qualAba.equalsIgnoreCase("procedimentos")) {
            if (aba.equalsIgnoreCase(qualAba)) {
                cor = "#99CCCC";
            }
        } else if (qualAba.equalsIgnoreCase("anamnese")) {
            if (aba.equalsIgnoreCase(qualAba)) {
                cor = "#99CCCC";
            }
        } else if (qualAba.equalsIgnoreCase("laudo")) {
            if (aba.equalsIgnoreCase(qualAba)) {
                cor = "#99CCCC";
            }
        } else if (qualAba.equalsIgnoreCase("dietas")) {
            if (aba.equalsIgnoreCase(qualAba)) {
                cor = "#99CCCC";
            }
        } else if (qualAba.equalsIgnoreCase("imagens")) {
            if (aba.equalsIgnoreCase(qualAba)) {
                cor = "#99CCCC";
            }
        } else if (qualAba.equalsIgnoreCase("cid")) {
            if (aba.equalsIgnoreCase(qualAba)) {
                cor = "#99CCCC";
            }
        } else if (qualAba.equalsIgnoreCase("evolucao")) {
            if (aba.equalsIgnoreCase(qualAba)) {
                cor = "#99CCCC";
            }
        } else if (qualAba.equalsIgnoreCase("atestados")) {
            if (aba.equalsIgnoreCase(qualAba)) {
                cor = "#99CCCC";
            }
        } else if (qualAba.equalsIgnoreCase("declaracoes")) {
            if (aba.equalsIgnoreCase(qualAba)) {
                cor = "#99CCCC";
            }
        }

        return cor;
    }

    /**
     * verifa
     *
     * @param qualAba
     * @param aba
     * @param fecha
     * @return
     */
    public static String pegaNegrito(final String qualAba, final String aba, final boolean fecha) {
        String cor = "";
        if (qualAba.equalsIgnoreCase("receita")) {
            if (aba.equalsIgnoreCase(qualAba)) {
                if (fecha) {
                    cor = "</strong>";
                } else {
                    cor = "<strong>";
                }
            }
        } else if (qualAba.equalsIgnoreCase("procedimentos")) {
            if (aba.equalsIgnoreCase(qualAba)) {
                if (fecha) {
                    cor = "</strong>";
                } else {
                    cor = "<strong>";
                }
            }
        } else if (qualAba.equalsIgnoreCase("anamnese")) {
            if (aba.equalsIgnoreCase(qualAba)) {
                if (fecha) {
                    cor = "</strong>";
                } else {
                    cor = "<strong>";
                }
            }
        } else if (qualAba.equalsIgnoreCase("laudo")) {
            if (aba.equalsIgnoreCase(qualAba)) {
                if (fecha) {
                    cor = "</strong>";
                } else {
                    cor = "<strong>";
                }
            }
        } else if (qualAba.equalsIgnoreCase("dietas")) {
            if (aba.equalsIgnoreCase(qualAba)) {
                if (fecha) {
                    cor = "</strong>";
                } else {
                    cor = "<strong>";
                }
            }
        } else if (qualAba.equalsIgnoreCase("imagens")) {
            if (aba.equalsIgnoreCase(qualAba)) {
                if (fecha) {
                    cor = "</strong>";
                } else {
                    cor = "<strong>";
                }
            }
        } else if (qualAba.equalsIgnoreCase("cid")) {
            if (aba.equalsIgnoreCase(qualAba)) {
                if (fecha) {
                    cor = "</strong>";
                } else {
                    cor = "<strong>";
                }
            }
        } else if (qualAba.equalsIgnoreCase("evolucao")) {
            if (aba.equalsIgnoreCase(qualAba)) {
                if (fecha) {
                    cor = "</strong>";
                } else {
                    cor = "<strong>";
                }
            }
        } else if (qualAba.equalsIgnoreCase("atestados")) {
            if (aba.equalsIgnoreCase(qualAba)) {
                if (fecha) {
                    cor = "</strong>";
                } else {
                    cor = "<strong>";
                }
            }
        } else if (qualAba.equalsIgnoreCase("declaracoes")) {
            if (aba.equalsIgnoreCase(qualAba)) {
                if (fecha) {
                    cor = "</strong>";
                } else {
                    cor = "<strong>";
                }
            }
        }

        return cor;
    }

    /**
     * Formata a string no padrão DD/MM/YYYY em Date.
     *
     * @param data
     * @return
     */
    public static Date formatStringToDate(final String data) {
        if (!UtilStrings.isNotVazio(data)) {
            return null;
        }
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(data);
        } catch (final ParseException pe) {
            return null;
        }
    }

    /**
     * Formata a data com DD/MM/YYYY.
     *
     * @param data
     * @return
     */
    public static String formatDateDDMMYYYY(final Date data) {
        if (data == null) {
            return "";
        }

        final Calendar c = Calendar.getInstance();

        c.setTime(data);

        final int diaMes = c.get(Calendar.DAY_OF_MONTH);
        final int mesAno = c.get(Calendar.MONTH) + 1;
        final int ano = c.get(Calendar.YEAR);

        String retorno;

        final NumberFormat formato2 = new DecimalFormat("00");
        final NumberFormat formato4 = new DecimalFormat("0000");
        retorno = formato2.format(diaMes);
        retorno = retorno + "/";
        retorno = retorno + formato2.format(mesAno);
        retorno = retorno + "/";
        retorno = retorno + formato4.format(ano);

        return retorno;
    }

    /**
     * Formata a data com MM/DD/YYYY.
     *
     * @param data
     * @return
     */
    public static String formatDateMMDDYYYY(final Date data) {
        final Calendar c = Calendar.getInstance();

        c.setTime(data);

        final int diaMes = c.get(Calendar.DAY_OF_MONTH);
        final int mesAno = c.get(Calendar.MONTH) + 1;
        final int ano = c.get(Calendar.YEAR);

        String retorno;

        final NumberFormat formato2 = new DecimalFormat("00");
        final NumberFormat formato4 = new DecimalFormat("0000");

        retorno = formato2.format(mesAno);
        retorno = retorno + "/";
        retorno = retorno + formato2.format(diaMes);
        retorno = retorno + "/";
        retorno = retorno + formato4.format(ano);

        return retorno;
    }

    /**
     * Formata a data com DD/MM/YYYY HH:MM:SS.
     *
     * @param data
     * @return
     */
    public static String formatDateDDMMYYYYHHMMSS(final Date data) {
        final Calendar c = Calendar.getInstance();
        if (data == null) {
            return "";
        }
        c.setTime(data);

        final int diaMes = c.get(Calendar.DAY_OF_MONTH);
        final int mesAno = c.get(Calendar.MONTH) + 1;
        final int ano = c.get(Calendar.YEAR);
        final int hora = c.get(Calendar.HOUR_OF_DAY);
        final int min = c.get(Calendar.MINUTE);
        final int sec = c.get(Calendar.SECOND);

        String retorno;

        final NumberFormat formato2 = new DecimalFormat("00");
        final NumberFormat formato4 = new DecimalFormat("0000");
        retorno = formato2.format(diaMes);
        retorno = retorno + "/";
        retorno = retorno + formato2.format(mesAno);
        retorno = retorno + "/";
        retorno = retorno + formato4.format(ano);
        retorno = retorno + " ";
        retorno = retorno + formato2.format(hora);
        retorno = retorno + ":";
        retorno = retorno + formato2.format(min);
        retorno = retorno + ":";
        retorno = retorno + formato2.format(sec);

        return retorno;
    }

    /**
     * Formata a data com DD de MMM de YYYY.
     *
     * @param data
     * @return
     */
    public static String formatDateDDMMMYYYY(final Date data) {
        final Calendar c = Calendar.getInstance();

        c.setTime(data);

        final int diaMes = c.get(Calendar.DAY_OF_MONTH);
        final int mesAno = c.get(Calendar.MONTH);
        final int ano = c.get(Calendar.YEAR);

        String retorno;

        final NumberFormat formato2 = new DecimalFormat("00");
        final NumberFormat formato4 = new DecimalFormat("0000");
        retorno = formato2.format(diaMes);
        retorno = retorno + " de ";
        retorno = retorno + getNomeMes(mesAno);
        retorno = retorno + " de ";
        retorno = retorno + formato4.format(ano);

        return retorno;
    }

    public static int getYear(final Date data) {
        final Calendar c = Calendar.getInstance();

        c.setTime(data);

        return c.get(Calendar.YEAR);
    }

    public static int getMonth(final Date data) {
        final Calendar c = Calendar.getInstance();

        c.setTime(data);

        return c.get(Calendar.MONTH) + 1;
    }

    public static int getDay(final Date data) {
        final Calendar c = Calendar.getInstance();

        c.setTime(data);

        return c.get(Calendar.DAY_OF_MONTH);
    }

    public static String getDayOfWeek(final Date data) {
        final Calendar c = Calendar.getInstance();

        c.setTime(data);

        final int dia = c.get(Calendar.DAY_OF_WEEK);
        switch (dia) {
        case Calendar.SUNDAY:
            return "Domingo";
        case Calendar.MONDAY:
            return "Segunda-feira";
        case Calendar.TUESDAY:
            return "Terça-feira";
        case Calendar.WEDNESDAY:
            return "Quarta-feira";
        case Calendar.THURSDAY:
            return "Quinta-feira";
        case Calendar.FRIDAY:
            return "Sexta-feira";
        case Calendar.SATURDAY:
            return "Sabádo";
        }
        return "";
    }

    public static String getDayOfWeekResumido(final Date data) {
        final Calendar c = Calendar.getInstance();

        c.setTime(data);

        final int dia = c.get(Calendar.DAY_OF_WEEK);
        switch (dia) {
        case Calendar.SUNDAY:
            return "Domingo";
        case Calendar.MONDAY:
            return "Segunda";
        case Calendar.TUESDAY:
            return "Terça";
        case Calendar.WEDNESDAY:
            return "Quarta";
        case Calendar.THURSDAY:
            return "Quinta";
        case Calendar.FRIDAY:
            return "Sexta";
        case Calendar.SATURDAY:
            return "Sabádo";
        }
        return "";
    }

    public static String getDayWeekNameOfNumber(final int day) {
        switch (day) {
        case 1:
            return "Domingo";
        case 2:
            return "Segunda";
        case 3:
            return "Terça";
        case 4:
            return "Quarta";
        case 5:
            return "Quinta";
        case 6:
            return "Sexta";
        case 7:
            return "Sábado";
        }
        return "";
    }

    public static String getDayOfWeekSigla(final Date data) {
        final Calendar c = Calendar.getInstance();

        c.setTime(data);

        final int dia = c.get(Calendar.DAY_OF_WEEK);
        switch (dia) {
        case Calendar.SUNDAY:
            return "Dom";
        case Calendar.MONDAY:
            return "Seg";
        case Calendar.TUESDAY:
            return "Ter";
        case Calendar.WEDNESDAY:
            return "Qua";
        case Calendar.THURSDAY:
            return "Qui";
        case Calendar.FRIDAY:
            return "Sex";
        case Calendar.SATURDAY:
            return "Sab";
        }
        return "";
    }

    public static int getDayNumberOfWeekBySigla(final String sigla) {
        if ("DOM".equalsIgnoreCase(sigla)) {
            return 1;
        } else if ("SEG".equalsIgnoreCase(sigla)) {
            return 2;
        } else if ("TER".equalsIgnoreCase(sigla)) {
            return 3;
        } else if ("QUA".equalsIgnoreCase(sigla)) {
            return 4;
        } else if ("QUI".equalsIgnoreCase(sigla)) {
            return 5;
        } else if ("SEX".equalsIgnoreCase(sigla)) {
            return 6;
        } else if ("SAB".equalsIgnoreCase(sigla)) {
            return 7;
        }
        return 0;
    }

    public static int getDayOfWeekNumber(final Date data) {
        final Calendar c = Calendar.getInstance();

        c.setTime(data);

        final int dia = c.get(Calendar.DAY_OF_WEEK);
        switch (dia) {
        case Calendar.SUNDAY:
            return 1;
        case Calendar.MONDAY:
            return 2;
        case Calendar.TUESDAY:
            return 3;
        case Calendar.WEDNESDAY:
            return 4;
        case Calendar.THURSDAY:
            return 5;
        case Calendar.FRIDAY:
            return 6;
        case Calendar.SATURDAY:
            return 7;
        }
        return 0;
    }

    /**
     * Formata a data com DD/MM/YYYY.
     *
     * @param data
     * @return
     */
    public static String converteDataUtilToString(final Date data) {
        String retorno = "";
        final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        retorno = sdf.format(data);
        return retorno;
    }

    /**
     * Formata a data com DD de MMM de YYYY.
     *
     * @param data
     * @return
     */
    public static String converteDataUtilToStringSpecial(final Date data) {
        String retorno = "";
        final SimpleDateFormat sdf = new SimpleDateFormat("dd' de 'MMM' de 'yyyy");
        retorno = sdf.format(data);
        return retorno;
    }

    /**
     * Formata a data em uma String conforme o padrão especificado na variável
     * pattern.
     *
     * @param data
     * @return
     */
    public static String converteDataUtilToString(final Date data, final String pattern) {
        String retorno = "";
        final SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        retorno = sdf.format(data);
        return retorno;
    }

    /**
     * Transforma uma String em java.util.Date. O padrão da String deve estar
     * especificado na atributo pattern conforme documentação da classe
     * SimpleDateFormat
     *
     * @param data
     * @param pattern
     * @return
     */
    public static Date converteStringToDataUtil(final String data, final String pattern) throws ParseException {
        Date retorno = null;
        final SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        retorno = sdf.parse(data);
        return retorno;
    }

    /**
     * Transforma uma String em java.util.Date. O padrão da String deve estar
     * especificado na atributo pattern conforme documentação da classe
     * SimpleDateFormat
     *
     * @param data
     * @param pattern
     * @return
     */
    public static java.sql.Date converteStringToDataSQL(final String data, final String pattern) throws ParseException {
        Date retorno = null;
        final SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        retorno = sdf.parse(data);
        return new java.sql.Date(retorno.getTime());
    }

    /**
     * Pega o nome do diretorio, retirando o nome do arquivo.
     *
     * @param pathNameDiretorio
     * @return
     */
    public static String getPathRelativo(final String pathNameDiretorio) {
        char c;
        final int length = pathNameDiretorio.length();

        for (int i = length - 1; i >= 0; i--) {
            c = pathNameDiretorio.charAt(i);
            if (c == '/') {
                return pathNameDiretorio.substring(0, i);
            }
        }
        return "";
    }

    /**
     * Faz replace de ../ para /ouvidoria/
     *
     * @param pathNameDiretorio
     * @return
     */
    public static String replacePathOuvidoria(final String pathNameDiretorio) {
        if (pathNameDiretorio == null) {
            return "";
        }
        String retorno = "";

        if (pathNameDiretorio.substring(0, 3).trim().equals("../")) {
            retorno = pathNameDiretorio.replaceFirst("../", "/ouvidoria/");
        }
        return retorno;
    }

    /**
     * Retorna o tipo do arquivo pela extensão
     *
     * @param fileName
     * @return
     */
    public static String getFileType(final String fileName) {
        final String ext = getFileExtension(fileName);
        if (ext.toUpperCase().equals("DOC")) {
            return "Word Document";
        } else if (ext.toUpperCase().equals("XLS")) {
            return "Excel Document";
        } else if (ext.toUpperCase().equals("HTML")) {
            return "Html Document";
        } else if (ext.toUpperCase().equals("HTM")) {
            return "Html Document";
        } else if (ext.toUpperCase().equals("TXT")) {
            return "Text File";
        } else {
            return ext + " file";
        }
    }

    /**
     * Obtem a extensão de um arquivo.
     *
     * @param fileName
     * @return
     */
    public static String getFileExtension(final String fileName) {
        String ext = "";
        boolean encontrado = false;
        for (int i = fileName.length() - 1; i > 0; i--) {
            if (fileName.charAt(i) == '.') {
                encontrado = true;
                break;
            } else {
                ext = fileName.charAt(i) + ext;
            }
        }
        if (encontrado) {
            return ext;
        } else {
            return "";
        }
    }

    /**
     * Gera os itens da combo.
     *
     * @param col
     * @param classe
     * @param campoId
     * @param campoDesc
     * @param valor
     * @return
     * @throws SystemException
     */
    public static StringBuilder createItemsCombo(final Collection col, final Class classe, String campoId, String campoDesc, final int valor) throws SystemException {
        final StringBuilder retorno = new StringBuilder();
        final Iterator it = col.iterator();
        Object objeto, objAux, objAux2;
        Integer intAux;
        String strAux, aux;
        int iAux;
        Method metodo = null;

        while (it.hasNext()) {
            objeto = it.next();
            try {
                campoId = campoId.substring(0, 1).toUpperCase() + campoId.substring(1);
                campoDesc = campoDesc.substring(0, 1).toUpperCase() + campoDesc.substring(1);
                metodo = classe.getMethod("get" + campoId, null);
                objAux = metodo.invoke(objeto, null);

                metodo = classe.getMethod("get" + campoDesc, null);
                objAux2 = metodo.invoke(objeto, null);

                intAux = (Integer) objAux;
                strAux = (String) objAux2;

                iAux = intAux.intValue();
                aux = "";
                if (iAux == valor) {
                    aux = "selected";
                }
                retorno.append("<option " + aux + " value=\"" + iAux + "\">" + strAux + "</option>\n");
            } catch (final SecurityException e) {
                e.printStackTrace();
                throw new SystemException("sis9999", "ERRO: Classe: " + classe.getName() + "Metodo: " + metodo.getName() + " Exc: " + e.getMessage());
            } catch (final NoSuchMethodException e) {
                e.printStackTrace();
                throw new SystemException("sis9999", "ERRO: Classe: " + classe.getName() + "Metodo: " + metodo.getName() + " Exc: " + e.getMessage());
            } catch (final IllegalArgumentException e) {
                e.printStackTrace();
                throw new SystemException("sis9999", "ERRO: Classe: " + classe.getName() + "Metodo: " + metodo.getName() + " Exc: " + e.getMessage());
            } catch (final IllegalAccessException e) {
                e.printStackTrace();
                throw new SystemException("sis9999", "ERRO: Classe: " + classe.getName() + "Metodo: " + metodo.getName() + " Exc: " + e.getMessage());
            } catch (final InvocationTargetException e) {
                e.printStackTrace();
                throw new SystemException("sis9999", "ERRO: Classe: " + classe.getName() + "Metodo: " + metodo.getName() + " Exc: " + e.getMessage());
            }
        }
        return retorno;
    }

    /**
     * Gera os itens da combo.
     *
     * @param col
     * @param classe
     * @param campoId
     * @param campoDesc
     * @param valor
     * @return
     * @throws SystemException
     */
    public static StringBuilder createItemsComboStrStr(final Collection col, final Class classe, String campoId, String campoDesc, final String valor) throws SystemException {
        final StringBuilder retorno = new StringBuilder();
        final Iterator it = col.iterator();
        Object objeto, objAux1, objAux2;
        String strAux1;
        String strAux2, aux;
        Method metodo = null;

        while (it.hasNext()) {
            objeto = it.next();
            try {
                campoId = campoId.substring(0, 1).toUpperCase() + campoId.substring(1);
                campoDesc = campoDesc.substring(0, 1).toUpperCase() + campoDesc.substring(1);
                metodo = classe.getMethod("get" + campoId, null);
                objAux1 = metodo.invoke(objeto, null);

                metodo = classe.getMethod("get" + campoDesc, null);
                objAux2 = metodo.invoke(objeto, null);

                strAux1 = "" + objAux1;
                strAux2 = (String) objAux2;

                aux = "";
                if (strAux1.trim().equalsIgnoreCase(valor.trim())) {
                    aux = "selected";
                }
                retorno.append("<option " + aux + " value=\"" + strAux1 + "\">" + strAux2 + "</option>\n");
            } catch (final SecurityException e) {
                e.printStackTrace();
                throw new SystemException("sis9999", "ERRO: Classe: " + classe.getName() + "Metodo: " + metodo.getName() + " Exc: " + e.getMessage());
            } catch (final NoSuchMethodException e) {
                e.printStackTrace();
                throw new SystemException("sis9999", "ERRO: Classe: " + classe.getName() + "Metodo: " + metodo.getName() + " Exc: " + e.getMessage());
            } catch (final IllegalArgumentException e) {
                e.printStackTrace();
                throw new SystemException("sis9999", "ERRO: Classe: " + classe.getName() + "Metodo: " + metodo.getName() + " Exc: " + e.getMessage());
            } catch (final IllegalAccessException e) {
                e.printStackTrace();
                throw new SystemException("sis9999", "ERRO: Classe: " + classe.getName() + "Metodo: " + metodo.getName() + " Exc: " + e.getMessage());
            } catch (final InvocationTargetException e) {
                e.printStackTrace();
                throw new SystemException("sis9999", "ERRO: Classe: " + classe.getName() + "Metodo: " + metodo.getName() + " Exc: " + e.getMessage());
            }
        }
        return retorno;
    }

    /**
     * Verifica se o param é null para substituir por ""
     *
     * @param conteudo
     * @return
     */
    public static String nullPorString(final String conteudo) {
        String string = "";

        string = conteudo == null ? "" : conteudo;

        return string;
    }

    /**
     * Transforma a hora em formato 0000
     *
     * @param horaPar
     * @return
     */
    public static String transformHora(final String horaPar) {
        String horaAux = horaPar;
        if (horaAux == null) {
            horaAux = "0000";
        }
        horaAux = horaAux.trim();
        String newHora = "";
        int i = 0;
        for (i = 0; i < horaAux.length(); i++) {
            if (horaAux.charAt(i) != ':') {
                newHora = newHora + horaAux.charAt(i);
            }
        }
        if (newHora.length() < 4) {
            for (i = newHora.length() - 1; i < 3; i++) {
                newHora = "0" + newHora;
            }
        }
        return newHora;
    }

    /**
     * Formata a hora em formato 00:00
     *
     * @param horaPar
     * @return
     */
    public static String formatHora(final String horaPar) {
        String newHora = "";
        if (horaPar.length() == 3) {
            newHora = "0" + horaPar.substring(0, 1) + ":" + horaPar.substring(1, 3);
        } else {
            newHora = horaPar.substring(0, 2) + ":" + horaPar.substring(2, 4);
        }
        return newHora;
    }

    /**
     * Obtem o valor do identificador que está na requisição.
     *
     * @param request
     * @param identificador
     * @return
     */
    public static String getStringRequest(final HttpServletRequest request, final String identificador) {
        String aux = (String) request.getAttribute(identificador);
        if (aux == null) { // Se getParameter for nulo, tenta pegar em
                           // getAttribute.
            aux = request.getParameter(identificador);
        }
        if (aux == null) {
            aux = "";
        }
        return aux;
    }

    public static String getStringRequest(final HttpServletRequest request, final String identificador, final int i) {
        String aux = (String) request.getAttribute(identificador);
        if (aux == null) { // Se getParameter for nulo, tenta pegar em
                           // getAttribute.
            aux = request.getParameter(identificador);
        }
        if (aux == null) {
            aux = "";
        }

        final String[] arrayReq = aux.split(Constantes.CARACTER_SEPARADOR);
        if (arrayReq != null) {
            if (arrayReq.length > i) {
                return arrayReq[i];
            }
        }
        return null;
    }

    public static int getLenStringRequest(final HttpServletRequest request, final String identificador) {
        String aux = (String) request.getAttribute(identificador);
        if (aux == null) { // Se getParameter for nulo, tenta pegar em
                           // getAttribute.
            aux = request.getParameter(identificador);
        }
        if (aux == null) {
            aux = "";
        }

        final String[] arrayReq = aux.split(Constantes.CARACTER_SEPARADOR);
        if (arrayReq != null) {
            try {
                return arrayReq.length;
            } catch (final Exception e) {
                return 0;
            }
        }
        return 0;
    }

    public static boolean horarioExisteEmCollecao(final String horaInicial, final String horaFinal, final Collection col) {
        if (col == null) {
            return false;
        }
        if (col.isEmpty()) {
            return false;
        }
        final int horaInicialInt = Integer.parseInt("0" + Util.transformHora(horaInicial.trim()));
        final int horaFinalInt = Integer.parseInt("0" + Util.transformHora(horaFinal.trim()));
        final Iterator itAux = col.iterator();
        String[] valor;
        int valor1;
        int valor2;
        while (itAux.hasNext()) {
            valor = ((String) itAux.next()).split("@");
            valor1 = Integer.parseInt("0" + Util.transformHora(valor[0]));
            valor2 = Integer.parseInt("0" + Util.transformHora(valor[1]));
            if (horaInicialInt >= valor1 && horaInicialInt < valor2) {
                return true;
            }
            if (horaFinalInt > valor1 && horaFinalInt <= valor2) {
                return true;
            }
            if (horaInicialInt <= valor1 && horaFinalInt >= valor2) {
                return true;
            }
        }
        return false;
    }

    public static String generateHTMLHidden(final String nome, final String valor) {
        return "<input type='hidden' name='" + nome + "' value='" + valor + "'>";
    }

    public static String generateHTMLHidden(final HttpServletRequest request, final Collection colIds) {
        String acum = "";
        String nome = "";
        final Iterator itAux = colIds.iterator();
        while (itAux.hasNext()) {
            nome = (String) itAux.next();
            if (!acum.equalsIgnoreCase("")) {
                acum = acum + "\n";
            }
            acum = acum + Util.generateHTMLHidden(nome, Util.nullPorString(Util.getStringRequest(request, nome)));
        }
        return acum;
    }

    public static String generateHTMLOptions(final String strValores, final String charSep, final String defaultValue, final String defaultStr) {
        final String[] auxStr = strValores.split(charSep);
        String saida = "";
        for (int i = 0; i < auxStr.length; i++) {
            if (!Util.nullPorString(auxStr[i]).trim().equalsIgnoreCase("")) {
                saida = saida + "<option value=\"" + i + "\">" + auxStr[i] + "</option>\n";
            }
        }
        if (saida.equalsIgnoreCase("")) {
            saida = "<option value=\"" + defaultValue + "\">" + defaultStr + "</option>\n";
        }
        return saida;
    }

    public static String dateToText(final Date date) {
        if (date == null) {
            return "";
        }

        final long delta = System.currentTimeMillis() - date.getTime();

        // within the last hour
        if (delta / HOUR < 1) {
            final long minutes = delta / MINUTE;
            if (minutes == 0) {
                return "menos de 1 minuto atrás";
            } else if (minutes == 1) {
                return "1 minuto atrás";
            } else {
                return minutes + " minutos atrás";
            }
        }

        // sometime today
        if (delta / DAY < 1) {
            final long hours = delta / HOUR;
            if (hours <= 1) {
                return "1 hora atrás";
            } else {
                return hours + " horas atrás";
            }
        }

        // within the last week
        if (delta / WEEK < 1) {
            final double days = (double) delta / (double) DAY;
            if (days <= 1.0) {
                return yesterdayFormatter.format(date);
            } else {
                return dateFormatter.format(date);
            }
        }

        // before a week ago
        else {
            return dateFormatter.format(date);
        }
    }

    public static String tiraEspacos(final String nomePar) {
        int i;
        String strSaida = "";
        for (i = 0; i < nomePar.length(); i++) {
            if (nomePar.charAt(i) != ' ') {
                strSaida = strSaida + nomePar.charAt(i);
            }
        }
        return strSaida;
    }

    public static String generateNomeBusca(final String nomePar) {
        int i;
        String strSaida = "";
        for (i = 0; i < nomePar.length(); i++) {
            if (IsValidCharFind(nomePar.charAt(i))) {
                strSaida = strSaida + nomePar.charAt(i);
            } else {
                strSaida = strSaida + ChangeCharInvalid(nomePar.charAt(i));
            }
        }
        return strSaida;
    }

    public static boolean IsValidCharFind(final char c) {
        switch (c) {
        case 'a':
        case 'b':
        case 'c':
        case 'd':
        case 'e':
        case 'f':
        case 'g':
        case 'h':
        case 'i':
        case 'j':
        case 'k':
        case 'l':
        case 'm':
        case 'n':
        case 'o':
        case 'p':
        case 'q':
        case 'r':
        case 's':
        case 't':
        case 'u':
        case 'v':
        case 'w':
        case 'x':
        case 'y':
        case 'z':
        case 'A':
        case 'B':
        case 'C':
        case 'D':
        case 'E':
        case 'F':
        case 'G':
        case 'H':
        case 'I':
        case 'J':
        case 'K':
        case 'L':
        case 'M':
        case 'N':
        case 'O':
        case 'P':
        case 'Q':
        case 'R':
        case 'S':
        case 'T':
        case 'U':
        case 'V':
        case 'W':
        case 'X':
        case 'Y':
        case 'Z':
        case '%':
            return true;
        }
        return false;
    }

    /**
     * Substitui um caracter especial por uma String
     */
    public static String ChangeCharInvalid(final char c) {
        if (c == 'á' || c == 'â' || c == 'ã') {
            return "a";
        } else if (c == 'Á' || c == 'Â' || c == 'Ã') {
            return "A";
        } else if (c == 'é' || c == 'ê') {
            return "e";
        } else if (c == 'É' || c == 'Ê') {
            return "E";
        } else if (c == 'í' || c == 'î') {
            return "i";
        } else if (c == 'Í' || c == 'Î') {
            return "I";
        } else if (c == 'ó' || c == 'ô' || c == 'õ') {
            return "o";
        } else if (c == 'Ó' || c == 'Ô' || c == 'Õ') {
            return "O";
        } else if (c == 'ú' || c == 'û') {
            return "u";
        } else if (c == 'Ú' || c == 'Û') {
            return "U";
        } else if (c == 'Ç') {
            return "C";
        } else if (c == 'ç') {
            return "c";
        } else {
            return "";
        }
    }

    public static String formataNumero(final String sValue, final String sMask) {
        final int tamCampo = sValue.length();
        int i = 0, i2 = 0;
        boolean bolMask = false;
        String retorno = "";
        while (i < tamCampo) {
            if (i2 < sMask.length()) {
                bolMask = sMask.charAt(i2) == '-' || sMask.charAt(i2) == '.' || sMask.charAt(i2) == '/' || sMask.charAt(i2) == ':';
                bolMask = bolMask || sMask.charAt(i2) == '(' || sMask.charAt(i2) == ')' || sMask.charAt(i2) == ' ';
                if (bolMask) {
                    retorno = retorno + sMask.charAt(i2);
                } else {
                    retorno = retorno + sValue.charAt(i);
                    i++;
                }
                i2++;
            } else {
                retorno = retorno + sValue.charAt(i);
                i++;
            }
        }
        return retorno;
    }

    public static String retiraMascara(final String value) {
        int i = 0;
        boolean bolMask = false;
        String retorno = "";
        while (i < value.length()) {
            bolMask = value.charAt(i) == '-' || value.charAt(i) == '.' || value.charAt(i) == '/' || value.charAt(i) == ':';
            bolMask = bolMask || value.charAt(i) == '(' || value.charAt(i) == ')' || value.charAt(i) == ' ';
            if (!bolMask) {
                retorno = retorno + value.charAt(i);
            }
            i++;
        }
        return retorno;
    }

    /**
     * Faz o calculo da idade com base na data passada como parametro.
     *
     * @param dDataNasc
     * @return
     */
    public static String calculaIdade(final Date dDataNasc, final String type, final Date ateData) {
        if (dDataNasc == null) {
            return "";
        }

        int anoResult = 0;
        int mesResult = 0;
        int diaResult = 0;
        Date now = null;
        if (ateData == null) {
            now = Util.getDataAtual();
        } else {
            now = ateData;
        }
        String retorno = "";
        String strAno;
        String strAnos;
        String strMes;
        String strMeses;
        String strDia;
        String strDias;

        final int anoHoje = getYear(now);
        final int anoDataParm = getYear(dDataNasc);
        final int mesHoje = getMonth(now);
        final int mesDataParm = getMonth(dDataNasc);
        final int diaHoje = getDay(now);
        final int diaDataParm = getDay(dDataNasc);

        if ("SHORT".equalsIgnoreCase(type)) {
            strAno = strAnos = "a";
            strMes = strMeses = "m";
            strDia = strDias = "d";
        } else {
            strAno = " ano";
            strAnos = " anos";
            strMes = " mês";
            strMeses = " meses";
            strDia = " dia";
            strDias = " dias";
        }

        anoResult = anoHoje - anoDataParm;
        if (mesHoje < mesDataParm || mesHoje == mesDataParm && diaHoje < diaDataParm) {
            anoResult = anoResult - 1;
        }

        mesResult = mesHoje - mesDataParm;
        if (mesResult < 0 || mesHoje == mesDataParm && diaHoje < diaDataParm) {
            mesResult = mesResult + 12;
        }

        diaResult = diaHoje - diaDataParm;
        if (diaResult < 0) {
            mesResult = mesResult - 1;
            diaResult = 30 + diaResult;
        }

        retorno = "";
        if (anoResult > 0) {
            if (anoResult == 1) {
                retorno = "1" + strAno + " ";
            } else {
                retorno = String.valueOf(anoResult) + strAnos + " ";
            }
        }

        if (mesResult > 0) {
            if (mesResult == 1) {
                retorno = retorno + "1" + strMes + " ";
            } else {
                retorno = retorno + String.valueOf(mesResult) + strMeses + " ";
            }
        }

        if (diaResult > 0) {
            if (diaResult == 1) {
                retorno = retorno + "1" + strDia + " ";
            } else {
                retorno = retorno + String.valueOf(diaResult) + strDias + " ";
            }
        }

        return retorno;
    }

    /**
     * Recebe e gera um string com apenas numeros nela.
     *
     * @param num
     * @return
     */
    public static String apenasNumeros(final String num) {
        if (num == null) {
            return "";
        }
        String aux = "";
        for (int i = 0; i < num.length(); i++) {
            if (num.charAt(i) == '0' || num.charAt(i) == '1' || num.charAt(i) == '2' || num.charAt(i) == '3' || num.charAt(i) == '4' || num.charAt(i) == '5'
                    || num.charAt(i) == '6' || num.charAt(i) == '7' || num.charAt(i) == '8' || num.charAt(i) == '9') {
                aux = aux + num.charAt(i);
            }
        }
        return aux;
    }

    public static String formatMoney(final double value, final String mask) {
        final DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator(',');
        dfs.setGroupingSeparator('.');

        final DecimalFormat df = new DecimalFormat(mask, dfs);
        return df.format(value);
    }

    /**
     * Formata numero inteiro.
     *
     * @param numero
     * @param formato
     * @return
     */
    public static String formatInt(final int numero, final String formato) {
        String retorno;

        final NumberFormat formatoAux = new DecimalFormat(formato);
        retorno = formatoAux.format(numero);
        return retorno;
    }

    public static String formatLong(final long numero, final String formato) {
        String retorno;

        final NumberFormat formatoAux = new DecimalFormat(formato);
        retorno = formatoAux.format(numero);
        return retorno;
    }

    public static String generateEspacosPlanoContas(final int qtde) {
        String ret = "";
        for (int i = 0; i <= qtde; i++) {
            ret = ret + "&nbsp;&nbsp;&nbsp;&nbsp;";
        }
        return ret;
    }

    public static String formataCelulaVaziaHTML(final String string) {
        if (string == null || string.trim().equals("")) {
            return "&nbsp;";
        } else {
            return string;
        }
    }

    public static String limpaCaracter(final String texto) {
        final String string = texto;
        char letra;
        String textoValido = "";

        for (int i = 0; i < string.length(); i++) {
            letra = string.charAt(i);
            if (Constantes.CARACTER_SEPARADOR.equals(String.valueOf(letra))) {
                textoValido = textoValido + "";
            } else {
                textoValido = textoValido + letra;
            }
        }
        return textoValido;
    }

    public static String trataReferenciaImagem(String nome) {
        String strRetorno = "";
        String aux = "";
        final StringBuilder stringB = new StringBuilder("NNNNN");

        nome = limpaCaracter(nome);

        aux = nome.substring(0);

        for (int i = 0; i < aux.length(); i++) {
            switch (aux.charAt(i)) {
            case '1':
                stringB.setCharAt(0, 'S');
                break;
            case '2':
                stringB.setCharAt(1, 'S');
                break;
            case '3':
                stringB.setCharAt(2, 'S');
                break;
            case '4':
                stringB.setCharAt(3, 'S');
                break;
            case '5':
                stringB.setCharAt(4, 'S');
                break;
            }
        }
        strRetorno = "lados" + stringB.toString() + ".gif";
        return strRetorno;
    }

    public static String trocaStrDataVaziaPorAtual(final String strData) {
        if (strData == null || strData.trim().length() == 0) {
            return Util.formatDateDDMMYYYY(new Date());
        }
        return strData;
    }

    public static String trocaStrValorVazioPorZero(final String strValor) {
        if (strValor == null || strValor.trim().length() == 0) {
            return "0";
        }
        return strValor;
    }

    public static void verificaValorInexistente(final Object[] array, final int indice, final String msgErro) throws BusinessException {
        if (array == null) {
            throw new BusinessException("sis9999", msgErro);
        }

        if (array.length <= indice) {
            throw new BusinessException("sis9999", msgErro);
        }

        if (array[indice] == null) {
            throw new BusinessException("sis9999", msgErro);
        }

        if (array[indice].toString().trim().length() == 0) {
            throw new BusinessException("sis9999", msgErro);
        }
    }

    public static long subitraiDatas(final int iTipo, final Date dataInicial, final Date dataFim) {
        if (dataInicial == null || dataFim == null) {
            return 0;
        }

        final long inic = dataInicial.getTime();
        final long fim = dataFim.getTime();
        final long total = fim - inic;
        final long dia = total / 86400000L;
        if (iTipo == DIA) {
            return dia;
        } else if (iTipo == MES) {
            return dia / 30;
        } else if (iTipo == ANO) {
            return dia / 365;
        } else {
            return 0;
        }
    }

    public static Date somaDiaData(final Date dataInicio, final int numDias) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(dataInicio);
        cal.add(Calendar.DATE, numDias - 1);
        return new Date(cal.getTimeInMillis());
    }

    public static Date subtraiDiaData(final Date dataInicio, int numDias) {
        numDias = numDias * -1;
        final Calendar cal = Calendar.getInstance();
        cal.setTime(dataInicio);
        cal.add(Calendar.DATE, numDias);
        return new Date(cal.getTimeInMillis());
    }

    public static String getStringRequestValue(final HttpServletRequest request, final String identificador, final String valor, final String tipoSel) {
        String aux = (String) request.getAttribute(identificador);
        if (aux == null) { // Se getParameter for nulo, tenta pegar em getAttribute.
            aux = request.getParameter(identificador);
        }
        aux = Util.nullPorString(aux);
        final String aux2 = Util.nullPorString(valor);
        if (aux.equalsIgnoreCase(aux2)) {
            return " " + tipoSel + " ";
        }
        return "";
    }

    public static int geraHoraAgenda(final int horaInicial, final int intervaloAdicionar) {
        final String h = horaInicial + "";
        int horas = 0;
        int minutos = 0;
        if (h.length() == 3) {
            horas = Integer.parseInt(h.substring(0, 1));
            minutos = Integer.parseInt(h.substring(1, 3));
        } else {
            horas = Integer.parseInt(h.substring(0, 2));
            minutos = Integer.parseInt(h.substring(2, 4));
        }
        minutos = minutos + intervaloAdicionar;
        if (minutos >= 60) {
            final int x = minutos / 60;
            horas = horas + x;
            minutos = minutos - x * 60;
            if (minutos < 0) {
                minutos = 0;
            }
        }
        return horas * 100 + minutos;
    }

    public static boolean isHoraValida(final String hora) {
        if (hora == null) {
            return true;
        }
        final String horaParm = hora.trim();
        final int tam = horaParm.length();
        final double horas = new Double(horaParm.substring(0, 2));
        final double minutos = new Double(horaParm.substring(tam - 2, tam));
        return horas <= 23.0 && minutos <= 59.0;
    }

    public static double getHoraDbl(final String hora) throws Exception {
        if (hora == null || hora.trim().length() == 0) {
            throw new Exception("Erro Util.getHoraDbl() - > Hora não informada");
        }
        String horaParm = hora.trim();
        horaParm = horaParm.replaceAll(":", "");
        final int tam = horaParm.length();
        final double horas = new Double(horaParm.substring(0, tam - 2)).intValue();
        final double minutos = new Double(horaParm.substring(tam - 2, tam));
        return UtilNumbersAndDecimals.setRound(horas + minutos / 60, 4);
    }

    public static String getHoraFmtStr(final double hora) {
        double horaParm = hora;
        final boolean bNegativo = horaParm < 0;
        if (bNegativo) {
            horaParm = horaParm * -1;
        }
        final long s = Math.round(horaParm * 3600);
        final long h = s / 3600;
        final long m = (s - 3600 * h) / 60;
        String result = "";
        if (h < 10) {
            result += "0";
        }
        result += h + ":";
        if (m < 10) {
            result += "0";
        }
        result += m + "";
        if (bNegativo && !result.equals("00:00")) {
            result = "-" + result;
        }
        return result;
    }

    public static java.util.Date getData(final int dia, final int mes, final int ano) throws LogicException {
        String diaStr = "" + dia;
        String mesStr = "" + mes;
        if (dia < 10) {
            diaStr = "0" + diaStr;
        }
        if (mes < 10) {
            mesStr = "0" + mesStr;
        }
        return UtilDatas.strTodate(diaStr + "/" + mesStr + "/" + ano);
    }

    public static int getDiaSemana(final Date dataConsulta) throws LogicException {
        return UtilDatas.getDiaSemana(UtilDatas.dateToSTR(dataConsulta));
    }

    public static String getHoraStr(final double hora) {
        double horaParm = hora;
        if (horaParm < 0) {
            horaParm = horaParm * -1;
        }
        final long s = Math.round(horaParm * 3600);
        final long h = s / 3600;
        final long m = (s - 3600 * h) / 60;
        String result = "";
        if (h < 10) {
            result += "0";
        }
        result += h + "";
        if (m < 10) {
            result += "0";
        }
        result += m + "";
        return result;
    }

    public static int getHora(final double hora) {
        double horaParm = hora;
        if (horaParm < 0) {
            horaParm = horaParm * -1;
        }
        final long s = Math.round(horaParm * 3600);
        final long h = s / 3600;
        return new Long(h).intValue();
    }

    public static int getMinuto(final double hora) {
        double horaParm = hora;
        if (horaParm < 0) {
            horaParm = horaParm * -1;
        }
        final long s = Math.round(horaParm * 3600);
        final long h = s / 3600;
        final long m = (s - 3600 * h) / 60;
        return new Long(m).intValue();
    }

    public static double calculaDuracao(final String horaInicialStr, final String horaFinalStr) throws Exception {
        final double horaInicial = Util.getHoraDbl(horaInicialStr);
        final double horaFinal = Util.getHoraDbl(horaFinalStr);
        if (horaFinal < horaInicial) {
            throw new Exception("Hora final é menor que hora a inicial");
        }
        final double duracao = horaFinal - horaInicial;
        return UtilNumbersAndDecimals.setRound(duracao, 4);
    }

    public static java.sql.Date getSqlDataAtual() {
        final java.util.Date dataUtil = new java.util.Date();
        final java.sql.Date dataSql = new java.sql.Date(dataUtil.getTime());
        return dataSql;
    }

    public static double calculaDuracaoEmMinutos(final Date dataInicial, final String horaInicialStr, final Date dataFinal, final String horaFinalStr) throws Exception {
        final double horaInicial = Util.getHoraDbl(horaInicialStr);
        final double horaFinal = Util.getHoraDbl(horaFinalStr);
        double duracao = 0.0;
        if (dataFinal.compareTo(dataInicial) > 0) {
            duracao += 24 - horaInicial + horaFinal;
        } else {
            duracao += horaFinal - horaInicial;
        }
        return UtilNumbersAndDecimals.setRound(duracao, 4);
    }

    public static byte[] gerarHash(final String senha, final String algoritmo) {
        try {
            final MessageDigest md = MessageDigest.getInstance(algoritmo);
            md.update(senha.getBytes());
            return md.digest();
        } catch (final NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     * Trata aspas simples.
     *
     * @param valor
     *            a ser tratado.
     * @return valor tratado.
     */
    public static String tratarAspasSimples(final String valor) {
        if (valor != null && !StringUtils.isEmpty(valor)) {
            if (StringUtils.contains(valor, "'")) {
                return StringUtils.replace(valor, "'", "");
            } else {
                if (StringUtils.contains(valor, "\"")) {
                    return StringUtils.replace(valor, "\"", "");
                } else {
                    return valor;
                }
            }
        } else {
            return valor;
        }
    }

    /**
     * Trata barra invertida.
     *
     * @param valor
     *            a ser tratado.
     * @return valor tratado.
     */
    public static String retiraBarraInvertida(final String valor) {
        if (valor != null && !StringUtils.isEmpty(valor)) {
            if (StringUtils.contains(valor, "\\")) {
                return StringUtils.replace(valor, "\\", "");
            } else {
                return valor;
            }
        } else {
            return valor;
        }
    }

    /*
     * Método que recebe uma String no formato informado e retorna uma nova String também no formato informado.
     * @author ruither borba
     * @data 18/12/2012
     * @param stringISO é a string que será convertida.
     * @param encodingEntrada é a codificação em que a string está vindo.
     * @param encodingSaida é a codificação da string de retorno.
     * @throws UnsupportedEncodingException é disparada se algum problema
     * ocorrer durante a conversão.
     */
    public static String converteFormato(final String stringISO, final String encodingEntrada, final String encodingSaida) throws UnsupportedEncodingException {
        final byte[] bISO = stringISO.getBytes(encodingEntrada);
        final String s = new String(bISO, encodingSaida);
        return s;
    }

    public static boolean isValidEmailAddress(final String email) {
        boolean result = true;
        try {
            final InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (final AddressException ex) {
            result = false;
        }
        return result;
    }

    public static String converterCaracteresInadequadosParaTexto(final String texto) {
        String retorno;

        retorno = texto;
        if (retorno != null) {
            retorno = retorno.replaceAll("\n", "<br>");
            retorno = retorno.replaceAll("<img>", "");
        }

        return retorno;
    }

}
