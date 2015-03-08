/*
 * Created on 15/04/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package br.com.centralit.citajax.util;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import br.com.centralit.citajax.reflexao.CitAjaxReflexao;
import br.com.centralit.citajax.util.Enumerados.TipoDate;
import br.com.citframework.excecao.LogicException;

/**
 * @author ney
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class CitAjaxUtil {

    private static final Logger LOGGER = Logger.getLogger(CitAjaxUtil.class);

    public static String CAMINHO_REAL_APP = "";

    private static final String ZERO_HORAS_E_MINUTOS_4DGT = "00:00h";
    private static String[] dias = {"31", "30", "31", "30", "31", "30", "31", "31", "30", "31", "30", "31"};
    public static String[] meses = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
    public static String DIREITA = "D";
    public static String ESQUERDA = "E";

    public static String CHAR_SIM = "S";
    public static String CHAR_NAO = "N";

    /**
     * Retorna a data corrente
     *
     * @return String
     */
    public static String getDataCorrente() {
        final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(new Date());
    }

    /**
     * Retorna o dia da semana
     * domingo = 1
     * segunda = 2
     * terca = 3
     * quarta = 4
     * quinta = 5
     * sexta = 6
     * sabado = 7
     *
     * @param dataConsulta
     *            Date
     * @return int
     * @throws LogicException
     */
    public static int getDiaSemana(final String dataConsulta) throws LogicException {
        final GregorianCalendar data = new GregorianCalendar();
        SimpleDateFormat dt = null;
        try {
            if (dataConsulta.indexOf("-") > -1) {
                dt = new SimpleDateFormat("yyyy-MM-dddd");
            } else {
                dt = new SimpleDateFormat("dd/MM/yyyy");
            }
            data.setTime(dt.parse(dataConsulta));
        } catch (final ParseException e) {
            throw new LogicException("Erro ao converter data: getDiaSemana");
        }
        return data.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * Retorna uma lista de programções semanais
     *
     * @param hrIncial
     *            String
     * @param hrFinal
     *            String
     * @param intervalo
     *            BigDecimal
     * @return ArrayList
     * @throws LogicException
     * @throws ParseException
     */
    public static ArrayList getHorasProgramacaoSemanal(final String hrIncial, final String hrFinal, final BigDecimal intervalo) throws LogicException {
        Date horaInicial = formataHoraBigDecimalToDate(hrIncial);
        final Date horaFinal = formataHoraBigDecimalToDate(hrFinal);

        final GregorianCalendar calendarHoraInicial = new GregorianCalendar();
        calendarHoraInicial.setTime(horaInicial);

        final GregorianCalendar calendarHoraFinal = new GregorianCalendar();
        final ArrayList horas = new ArrayList();
        final String minuto = String.valueOf(intervalo);

        calendarHoraFinal.setTime(horaFinal);
        while (true) {
            horas.add(horaInicial);
            calendarHoraInicial.add(Calendar.MINUTE, Integer.valueOf(minuto).intValue());
            horaInicial = calendarHoraInicial.getTime();
            if (horaInicial.getTime() >= horaFinal.getTime()) {
                horas.add(horaInicial);
                break;
            }
        }
        return horas;
    }

    /**
     * Retorna a hora do usuário(interface)
     *
     * @return String
     */
    public static String getHoraUsuario(final Date hora) {
        final SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(hora);
    }

    public static Date formataHoraBigDecimalToDate(final String hora) throws LogicException {
        Date horaRetorno = null;
        final SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        final String h = hora.substring(0, 2);
        final String m = hora.substring(2, 4);
        try {
            horaRetorno = format.parse(h + ":" + m);
        } catch (final ParseException e) {
            throw new LogicException("Erro ao converter data: formataHoraBigDecimalToDate");
        }
        return horaRetorno;
    }

    public static String getHoraFormatada(final String hora) {
        return hora.substring(0, 2) + ":" + hora.substring(2, 4);
    }

    public static final Date strTodate(final String data) throws LogicException {
        if (data == null || data.length() == 0) {
            return null;
        }
        try {
            SimpleDateFormat date = null;
            if (data.indexOf("-") > -1) {
                date = new SimpleDateFormat("yyyy-MM-dddd");
            } else if (data.length() == 10) {
                date = new SimpleDateFormat("dd/MM/yyyy");
            } else {
                date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            }
            if (data.equals("") || data.length() == 0) {
                return null;
            } else {
                return date.parse(data);
            }
        } catch (final Exception e) {
            throw new LogicException("Erro ao converter data: strTodate");
        }
    }

    /**
     * Converte Date (String) em java.sql.Date de acordo com a Language do usuário.
     *
     * @param data
     *            - Data no formato String.
     * @param language
     *            - Linguagem do usuário logado (EN, PT)
     * @return java.sql.Date
     * @throws ParseException
     * @author valdoilo.damasceno
     * @since 03.02.2014
     */
    public static final java.sql.Date strToSqlDateWithLanguage(final String dateStr, final String language) throws ParseException {
        if (dateStr == null || dateStr.length() == 0) {
            return null;
        }

        final SimpleDateFormat simpleDateFormat = getSimpleDateFormatByTipoDataAndLanguage(TipoDate.DATE_DEFAULT, language);

        return new java.sql.Date(simpleDateFormat.parse(dateStr).getTime());
    }

    /**
     * Converte Date (String) em java.sql.Timestamp de acordo com a Language do usuário.
     *
     * @param data
     *            - Data no formato String.
     * @param language
     *            - Linguagem do usuário logado (EN, PT)
     * @return java.sql.Date
     * @throws ParseException
     * @author valdoilo.damasceno
     * @since 14.02.2014
     */
    public static final java.sql.Timestamp strToTimestampWithLanguage(String dateStr, final String language) throws ParseException {

        if (dateStr == null || dateStr.length() < 10) {
            return null;
        }

        /**
         * Alterado por Cristian. Alguns campos estão como timestamp no DTO, porém na JSP estão com o CSS Format[Date]
         */
        if (dateStr.length() < 19) {
            dateStr = dateStr.substring(0, 10) + " 00:00:00";
        }

        final SimpleDateFormat simpleDateFormat = getSimpleDateFormatByTipoDataAndLanguage(TipoDate.TIMESTAMP_WITH_SECONDS, language);

        return new java.sql.Timestamp(simpleDateFormat.parse(dateStr).getTime());
    }

    public static String removeCaracteresEspeciais(String string) {
        string = string.replaceAll("á", "a");
        string = string.replaceAll("é", "e");
        string = string.replaceAll("í", "i");
        string = string.replaceAll("ó", "o");
        string = string.replaceAll("ú", "u");

        string = string.replaceAll("Á", "A");
        string = string.replaceAll("É", "E");
        string = string.replaceAll("Í", "I");
        string = string.replaceAll("Ó", "O");
        string = string.replaceAll("Ú", "U");

        string = string.replaceAll("à", "a");
        string = string.replaceAll("è", "e");
        string = string.replaceAll("ì", "i");
        string = string.replaceAll("ò", "o");
        string = string.replaceAll("ù", "u");

        string = string.replaceAll("À", "A");
        string = string.replaceAll("È", "E");
        string = string.replaceAll("Ì", "I");
        string = string.replaceAll("Ò", "O");
        string = string.replaceAll("Ù", "U");

        string = string.replaceAll("â", "a");
        string = string.replaceAll("ê", "e");
        string = string.replaceAll("î", "i");
        string = string.replaceAll("ô", "o");
        string = string.replaceAll("û", "u");

        string = string.replaceAll("Â", "A");
        string = string.replaceAll("Ê", "E");
        string = string.replaceAll("Î", "I");
        string = string.replaceAll("Ô", "O");
        string = string.replaceAll("Û", "U");

        string = string.replaceAll("ã", "a");
        string = string.replaceAll("õ", "o");

        string = string.replaceAll("Ã", "A");
        string = string.replaceAll("Õ", "O");

        string = string.replaceAll("ç", "c");
        string = string.replaceAll("Ç", "C");

        return string;
    }

    public static final Timestamp strToTimestamp(final String data) throws Exception {
        if (data == null || data.length() == 0) {
            return null;
        }
        final Timestamp result = new Timestamp(strTodate(data).getTime());

        return result;

    }

    public static final java.sql.Date strToSQLDate(final String data) throws LogicException {
        if (data == null || data.length() == 0) {
            return null;
        }
        return new java.sql.Date(strTodate(data).getTime());

    }

    public static final String formatDouble(final Double valor, final int decimal) {
        if (valor == null) {
            return null;
        }
        final NumberFormat format = NumberFormat.getInstance(new Locale("pt", "BR"));
        format.setMaximumFractionDigits(decimal);
        format.setMinimumFractionDigits(decimal);
        return format.format(valor);
    }

    public static final String formatBigDecimal(final BigDecimal valor, final int decimal) {
        if (valor == null) {
            return null;
        }
        final NumberFormat format = NumberFormat.getInstance(new Locale("pt", "BR"));
        format.setMaximumFractionDigits(decimal);
        format.setMinimumFractionDigits(decimal);
        return format.format(valor);
    }

    /**
     * Retorna a 1.a data do mes/ano Exemplo: se passar a data 23/01/2007, retornará 01/01/2007.
     *
     * @param data
     * @return
     * @throws Exception
     */
    @Deprecated
    public static final Date getPrimeiraDataMes(final Date data) throws Exception {
        SimpleDateFormat spd = new SimpleDateFormat("MM/yyyy");
        String mesAno = spd.format(data);
        mesAno = "01/" + mesAno;
        spd = new SimpleDateFormat("dd/MM/yyyy");
        return spd.parse(mesAno);

    }

    public static final Date getUltimaDataMes(final Date data) throws ParseException {
        SimpleDateFormat spd = new SimpleDateFormat("MM");
        String tmp = spd.format(data);
        final int ind = Integer.parseInt(tmp);
        if (ind != 2) {
            tmp = dias[ind - 1];
        } else {
            spd = new SimpleDateFormat("yyyy");
            final String sAno = spd.format(data);
            final int iAno = Integer.parseInt(sAno);
            if (iAno % 4 == 0 && iAno % 100 != 0) {
                tmp = "29";
            } else {
                tmp = "28";
            }
        }

        spd = new SimpleDateFormat("MM/yyyy");
        String mesAno = spd.format(data);
        mesAno = tmp + "/" + mesAno;
        spd = new SimpleDateFormat("dd/MM/yyyy");
        return spd.parse(mesAno);

    }

    public static final Date calculaData(final Date data, final int mes) throws LogicException {
        if (data == null) {
            return null;
        }

        final int mesCalculo = getDiaMesAno(data, 2);
        int anoCalculo = getDiaMesAno(data, 3);
        final int dia = getDiaMesAno(data, 1);
        int soma = mesCalculo + mes;
        if (soma < 0) {
            soma = soma + 1;
            soma = 12 + soma;
            anoCalculo = anoCalculo - 1;
        }

        if (soma == 0) {
            soma = 12;
            anoCalculo--;
        }

        while (soma > 12) {
            soma = soma - 12;
            anoCalculo++;
        }
        String sDia = new Integer(dia).toString();
        if (sDia.length() == 1) {
            sDia = "0" + sDia;
        }
        String sMes = new Integer(soma).toString();
        if (sMes.length() == 1) {
            sMes = "0" + sMes;
        }

        return CitAjaxUtil.strTodate(sDia + "/" + sMes + "/" + anoCalculo);

    }

    /**
     * @param data
     * @param ind
     * @return Retorna dia , mes ou ano da data passada como parï¿½metro,
     *         segundo o Indice Passado. Ex: 1 - Dia 2 - Mes 3 - Ano
     */
    public final static int getDiaMesAno(final Date data, final int ind) {
        if (data == null) {
            return 0;
        }
        if (ind == 1)// dia
        {
            final SimpleDateFormat spd = new SimpleDateFormat("dd");
            return new Integer(spd.format(data)).intValue();
        }
        if (ind == 2)// mes
        {
            final SimpleDateFormat spd = new SimpleDateFormat("MM");
            return new Integer(spd.format(data)).intValue();
        }
        if (ind == 3)// ano
        {
            final SimpleDateFormat spd = new SimpleDateFormat("yyyy");
            return new Integer(spd.format(data)).intValue();
        }
        return 0;
    }

    public final static int getDifMeses(final Date inic, final Date fim) {

        final int anoInic = CitAjaxUtil.getDiaMesAno(inic, 3);
        final int anoFim = CitAjaxUtil.getDiaMesAno(fim, 3);
        final int mesInic = CitAjaxUtil.getDiaMesAno(inic, 2);
        final int mesFim = CitAjaxUtil.getDiaMesAno(fim, 2);
        int difAno = 0;
        int difMes = 0;
        if (anoInic < anoFim) {
            difAno = anoFim - anoInic;
            difAno = difAno * 12;
        }
        difMes = mesFim + difAno - mesInic;
        return difMes + 1;
    }

    public static final String getDescMes(final Integer mes) {
        if (mes.intValue() > 0 && mes.intValue() < 13) {
            final int iMes = mes.intValue() - 1;
            return meses[iMes];
        } else {
            return "";
        }

    }

    public static Object getValorAtributo(final Object obj, final String atrib) {
        Object result = null;
        try {
            result = CitAjaxReflexao.getPropertyValue(obj, atrib);
        } catch (final Exception e) {
            return result;
        }
        return result;

    }

    public static final Date alteraData(final Date data, final int quantidade, final int unidade) {
        if (data == null) {
            return null;
        }
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        calendar.add(unidade, quantidade);
        return calendar.getTime();
    }

    public static final java.sql.Date alteraDataSQLDate(final Date data, final int quantidade, final int unidade) {
        if (data == null) {
            return null;
        }
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        calendar.add(unidade, quantidade);
        return new java.sql.Date(calendar.getTime().getTime());
    }

    public static final Date getProximoMes(final Date data) {

        int mes = getDiaMesAno(data, 2);
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        calendar.set(Calendar.MONTH, mes++);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    public static final void setValorAtributo(final String atributo, final Object obj, Object valor) throws Exception {

        final StringTokenizer str = new StringTokenizer(atributo, ".");
        int elementos = str.countTokens();
        String elemento = "";
        if (valor instanceof BigDecimal) {
            valor = changeToDouble((BigDecimal) valor);
        }
        try {

            while (str.hasMoreElements()) {
                elemento = elemento + str.nextElement().toString();
                if (elementos > 1) {

                    // Verificar se ja foi instanciado
                    final Object objAux = CitAjaxReflexao.getPropertyValue(obj, elemento);
                    if (objAux == null) {
                        CitAjaxReflexao.setPropertyValue(obj, elemento, CitAjaxReflexao.getReturnType(obj, elemento).newInstance());
                    }
                    elementos = elementos - 1;
                    elemento = elemento + ".";

                } else {
                    CitAjaxReflexao.setPropertyValue(obj, elemento, valor);
                }
            }
        } catch (final IllegalArgumentException iaex) {
            throw new Exception("O tipo de dado do atributo " + elemento + " da classe " + obj.getClass().getName() + " Ã© diferente do tipo usado no banco de dados "
                    + valor.getClass().getName());
        }

    }

    private static Double changeToDouble(final BigDecimal big) {
        if (big != null) {
            return new Double(big.doubleValue());
        } else {
            return null;
        }
    }

    public static int getMaxValue(final int val1, final int val2) {
        if (val1 > val2) {
            return val1;
        } else {
            return val2;
        }
    }

    public static int getMinValue(final int val1, final int val2) {
        if (val1 < val2) {
            return val1;
        } else {
            return val2;
        }
    }

    /**
     * Retorna vazio ("") caso a string passada seja null,
     * caso contrario, retorna a propria string.
     *
     * @param string
     * @return
     */
    public static String nullToVazio(final String string) {
        return string == null ? "" : string;
    }

    public static Object nullToValue(final Object obj, final Object value) {
        if (obj == null) {
            return value;
        }
        return obj;
    }

    public static final int trunca(final double numero) {
        String str = new Double(numero).toString();
        final StringTokenizer tk = new StringTokenizer(str, ".");
        str = tk.nextToken();
        return new Integer(str).intValue();

    }

    public static final double frac(final double numero) {
        String str = new Double(numero).toString();
        final StringTokenizer tk = new StringTokenizer(str, ".");
        str = tk.nextToken();
        if (tk.hasMoreElements()) {
            if (numero <= 0) {
                str = "-0." + tk.nextToken();
            } else {
                str = "0." + tk.nextToken();
            }
        }
        return new Double(str).doubleValue();

    }

    public static final double setRound(final double valor, final int decimal) {
        final NumberFormat format = NumberFormat.getInstance(new Locale("pt", "BR"));
        format.setMaximumFractionDigits(decimal);
        format.setMaximumFractionDigits(decimal);
        StringBuilder resultTmp = new StringBuilder(format.format(valor));
        final int ponto = resultTmp.indexOf(".");
        if (ponto > -1) {
            resultTmp = resultTmp.deleteCharAt(ponto);
        }
        return new Double(resultTmp.toString().replaceAll(",", ".")).doubleValue();
    }

    public static final int getUltimoDiaMes(final Date data) throws ParseException {
        final Date datTmp = getUltimaDataMes(data);
        return getDiaMesAno(datTmp, 1);
    }

    public static final java.sql.Date getSqlDate(Date data) throws ParseException {
        final SimpleDateFormat spd = new SimpleDateFormat("dd/MM/yyyy 00:00:00");
        final SimpleDateFormat spd1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        data = spd1.parse(spd.format(data));
        final java.sql.Date datasql = new java.sql.Date(data.getTime());
        return datasql;
    }

    public static final String dateToSTR(final java.sql.Date data) {
        if (data == null) {
            return null;
        }
        return dateToSTR(new Date(data.getTime()));
    }

    public static final String dateToSTR(final Date data) {
        if (data == null) {
            return "";
        }
        final SimpleDateFormat spd = new SimpleDateFormat("dd/MM/yyyy");
        return spd.format(data).trim();
    }

    public static final String dateToSTR(final Date data, final String formato) {
        if (data == null) {
            return "";
        }
        final SimpleDateFormat spd = new SimpleDateFormat(formato);
        return spd.format(data).trim();
    }

    public static final String getMesAno(final Date data) {
        if (data == null) {
            return null;
        }
        final SimpleDateFormat spd = new SimpleDateFormat("MM/yyyy");
        return spd.format(data).trim();
    }

    public static final Object getObjectValue(final Collection lista, final String atributo, final Object valor) {
        final Iterator it = lista.iterator();
        while (it.hasNext()) {
            final Object obj = it.next();
            final Object val = getValorAtributo(obj, atributo);
            if (val.equals(valor)) {
                return obj;
            }
        }
        return null;
    }

    public static final boolean verificaDiaUtil(final Date data) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        boolean result;
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            result = false;
        } else {
            result = true;
        }
        return result;

    }

    public static final boolean stringVazia(final String valor) {
        if (valor != null && valor.length() > 0) {
            return false;
        }
        return true;

    }

    public static final void geraXml(final String arquivo, final List lista) throws FileNotFoundException {
        final XMLEncoder e = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(arquivo)));
        e.writeObject(lista);
        e.close();

    }

    public static final List recuperaXML(final String arquivo) throws FileNotFoundException {
        final XMLDecoder d = new XMLDecoder(new BufferedInputStream(new FileInputStream(arquivo)));
        final Object result = d.readObject();
        d.close();
        return (List) result;
    }

    public static final String getFileName(final String path, final String separador) {

        final StringTokenizer st = new StringTokenizer(path, separador);
        String result = "";
        while (st.hasMoreElements()) {

            result = st.nextElement().toString();
        }

        return result;

    }

    public static final Double strFormatToDouble(final String valor) {

        if (valor == null || valor.length() == 0) {
            return null;
        }

        StringBuilder str = new StringBuilder(valor);

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '.') {
                str = str.deleteCharAt(i);
            }
        }

        final String tmp = str.toString().replace(',', '.');
        final Double result = new Double(tmp);
        return result;

    }

    public static String completaString(String origem, final String complemento, final int tamanho, final String direcao) {

        for (int i = origem.length(); i < tamanho; i++) {

            if (direcao.equals(CitAjaxUtil.DIREITA)) {
                origem = origem + complemento;
            } else if (direcao.equals(CitAjaxUtil.ESQUERDA)) {
                origem = complemento + origem;
            }
        }

        return origem;

    }

    public static List converteExcessao(final Throwable e) {

        final List result = new ArrayList();
        final StackTraceElement[] ste = e.getStackTrace();

        final SimpleDateFormat spd = new SimpleDateFormat("dd/MM/yyyy  hh:mm:ss");
        result.add(e.getClass() + ": " + e.getMessage() + ": " + spd.format(new Date()));
        for (final StackTraceElement element : ste) {
            result.add("	at " + element.getClassName() + "." + element.getMethodName() + "(" + element.getFileName() + ":" + element.getLineNumber() + ')');
        }

        return result;

    }

    /**
     * Remove uma substring da string de origem e devolve a String origem
     * formatada
     *
     * @return
     */
    public static String removeSubstring(final String origem, final String substring) {

        StringBuilder str = new StringBuilder(origem);
        int idx = str.indexOf(substring);

        while (idx > -1) {
            str = str.delete(idx, idx + substring.length());
            idx = str.indexOf(substring);
        }

        return str.toString();

    }

    public static String getStrTextoTxt(final String arquivo) {
        String retorno = "";
        try {
            final FileInputStream arq = new FileInputStream(arquivo);

            final BufferedReader br = new BufferedReader(new InputStreamReader(arq));
            while (br.ready()) {
                retorno += br.readLine() + "\n";
            }
            br.close();
        } catch (final IOException e) {
            LOGGER.warn(e.getMessage(), e);
        }

        return retorno;
    }

    public static String getStrTextoTxtInline(final String arquivo) {
        String retorno = "";
        try {
            final FileInputStream arq = new FileInputStream(arquivo);

            final BufferedReader br = new BufferedReader(new InputStreamReader(arq));
            while (br.ready()) {
                retorno += br.readLine();
            }
            br.close();

        } catch (final IOException e) {
            LOGGER.warn(e.getMessage(), e);
        }

        return retorno;
    }

    public static List lerTXT(final String arquivo) throws Exception {
        final List lista = new ArrayList();
        final FileInputStream arq = new FileInputStream(arquivo);

        final BufferedReader br = new BufferedReader(new InputStreamReader(arq));
        while (br.ready()) {
            lista.add(br.readLine());
        }
        br.close();

        return lista;
    }

    public static List carregaTxt(final String arquivo, final String separador, final List campos, final Class classe) throws Exception {

        final List lstArq = lerTXT(arquivo);
        final List result = new ArrayList();

        final Iterator itArq = lstArq.iterator();
        while (itArq.hasNext()) {
            final String linha = itArq.next().toString();
            int i = 0;
            final StringTokenizer stok = new StringTokenizer(linha, separador);
            final Object obj = classe.newInstance();
            while (stok.hasMoreTokens()) {
                final String valor = stok.nextToken();
                final String campo = campos.get(i).toString();
                CitAjaxReflexao.setPropertyValueAsString(obj, campo, valor);
                i++;
            }
            result.add(obj);
        }
        return result;

    }

    public static final void geraTXT(final String arquivo, final List lista) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(arquivo); PrintStream out = new PrintStream(fos)) {
            final Iterator it = lista.iterator();
            while (it.hasNext()) {
                out.println(it.next().toString());
            }
        }
    }

    public static final void geraTxtFromString(final String arquivo, final String texto) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(arquivo); PrintStream out = new PrintStream(fos)) {
            out.print(texto);
        }
    }

    public static Collection getResultadoImplosao(final String conta, final String mascara) {
        final String contaMask = setMascaraContabil(conta, mascara);
        final List lista = new ArrayList();
        final StringTokenizer tok = new StringTokenizer(contaMask, ".");
        String contaImplosao = "";
        while (tok.hasMoreTokens()) {
            contaImplosao = contaImplosao + tok.nextToken();
            lista.add(ajustaContaContabil(contaImplosao, mascara));
        }
        return lista;

    }

    public static String setMascaraContabil(String conta, final String mascara) {

        String result = "";
        final StringTokenizer tok = new StringTokenizer(mascara, ".");
        conta = ajustaContaContabil(conta, mascara);
        int anterior = 0;
        while (tok.hasMoreTokens()) {
            final String elemento = tok.nextToken();
            if (anterior != 0) {
                result = result + "." + conta.substring(anterior, anterior + elemento.length());
            } else {
                result = result + conta.substring(anterior, anterior + elemento.length());
            }

            anterior = anterior + elemento.length();

        }

        return result;

    }

    public static String ajustaContaContabil(final String conta, final String mascara) {

        final StringTokenizer tok = new StringTokenizer(mascara, ".");
        String tmp = "";
        String result = conta;
        while (tok.hasMoreTokens()) {
            final String elemento = tok.nextToken();
            tmp = tmp + elemento;
        }

        final int iTamanhoConta = conta.length();
        final int iTamanhoMascara = tmp.length();
        if (iTamanhoConta < iTamanhoMascara) {
            final int resto = iTamanhoMascara - iTamanhoConta;

            for (int i = 0; i < resto; i++) {
                result = result + "0";
            }

        }

        return result;

    }

    public static String getJavaType(final int type) {
        String result = null;
        switch (type) {
        case Types.BIGINT:
            result = "Integer";
            break;
        case Types.DATE:

            result = "java.sql.Date";
            break;
        case Types.BLOB:

            result = "java.sql.Blob";
            break;
        case Types.BOOLEAN:

            result = "Boolean";
            break;
        case Types.CHAR:

            result = "String";
            break;
        case Types.DOUBLE:

            result = "Double";
            break;
        case Types.FLOAT:

            result = "Float";
            break;
        case Types.INTEGER:

            result = "Integer";
            break;
        case Types.JAVA_OBJECT:

            result = "Object";
            break;
        case Types.LONGVARCHAR:

            result = "String";
            break;
        case Types.NUMERIC:

            result = "Double";
            break;
        case Types.REAL:

            result = "Double";
            break;
        case Types.SMALLINT:

            result = "Integer";
            break;
        case Types.TIME:

            result = "java.sql.Timestamp";
            break;

        case Types.TIMESTAMP:

            result = "java.sql.Timestamp";
            break;
        case Types.TINYINT:

            result = "Integer";
            break;

        case Types.VARCHAR:
            result = "String";
            break;
        default:
            result = null;
            break;
        }

        return result;

    }

    /**
     * Faz o calculo da idade com base na data passada como parametro.
     *
     * @param dDataNasc
     * @return
     */
    public static String calculaIdade(final Date dDataNasc, final String type) {
        if (dDataNasc == null) {
            return "";
        }

        int anoResult = 0;
        int mesResult = 0;
        int diaResult = 0;
        final Calendar hoje = Calendar.getInstance();
        final Date now = hoje.getTime();
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
            strMes = " mÃªs";
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

    public static String calculaIdadeMesAno(final Date dDataNasc, final String type) {
        if (dDataNasc == null) {
            return "";
        }

        int anoResult = 0;
        int mesResult = 0;
        int diaResult = 0;
        final Calendar hoje = Calendar.getInstance();
        final Date now = hoje.getTime();
        String retorno = "";
        String strAno;
        String strAnos;
        String strMes;
        String strMeses;

        final int anoHoje = getYear(now);
        final int anoDataParm = getYear(dDataNasc);
        final int mesHoje = getMonth(now);
        final int mesDataParm = getMonth(dDataNasc);
        final int diaHoje = getDay(now);
        final int diaDataParm = getDay(dDataNasc);

        if ("SHORT".equalsIgnoreCase(type)) {
            strAno = strAnos = "a";
            strMes = strMeses = "m";
        } else {
            strAno = " ano";
            strAnos = " anos";
            strMes = " mÃªs";
            strMeses = " meses";
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

        return retorno;
    }

    public static Integer calculaIdadeEmAnos(final Date dDataNasc) {
        if (dDataNasc == null) {
            return new Integer(0);
        }

        int anoResult = 0;
        final Calendar hoje = Calendar.getInstance();
        final Date now = hoje.getTime();

        final int anoHoje = getYear(now);
        final int anoDataParm = getYear(dDataNasc);
        final int mesHoje = getMonth(now);
        final int mesDataParm = getMonth(dDataNasc);
        final int diaHoje = getDay(now);
        final int diaDataParm = getDay(dDataNasc);

        anoResult = anoHoje - anoDataParm;
        if (mesHoje < mesDataParm || mesHoje == mesDataParm && diaHoje < diaDataParm) {
            anoResult = anoResult - 1;
        }

        if (anoResult > 0) {
            if (anoResult == 1) {
                return new Integer(1);
            } else {
                return new Integer(anoResult);
            }
        }

        return new Integer(0);
    }

    public static Date incrementaData(final Date data, final int numDias) {
        final GregorianCalendar c = new GregorianCalendar();
        c.setTime(data);
        c.add(Calendar.DATE, numDias);
        return c.getTime();
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

    public static java.sql.Date getDataAtual() {

        final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            return strToSQLDate(sdf.format(new Date()));
        } catch (final LogicException e) {

            return new java.sql.Date(new Date().getTime());
        }
    }

    public static java.sql.Time getHoraAtual() {

        return new java.sql.Time(new Date().getTime());
    }

    public static Timestamp getDataHoraAtual() {
        return new Timestamp(new Date().getTime());
    }

    /**
     * Faz o calculo da idade com base na data passada como parametro.
     *
     * @param dDataNasc
     * @return
     */
    public static int calculaIdade(final Date dDataNasc) {
        if (dDataNasc == null) {
            return 0;
        }
        int anoResult = 0;
        final Calendar hoje = Calendar.getInstance();
        final Date now = hoje.getTime();

        final int anoHoje = getYear(now);
        final int anoDataParm = getYear(dDataNasc);
        final int mesHoje = getMonth(now);
        final int mesDataParm = getMonth(dDataNasc);
        final int diaHoje = getDay(now);
        final int diaDataParm = getDay(dDataNasc);

        anoResult = anoHoje - anoDataParm;
        if (mesHoje < mesDataParm || mesHoje == mesDataParm && diaHoje < diaDataParm) {
            anoResult = anoResult - 1;
        }

        return anoResult;
    }

    /*
     * public static void verificaTamanho(String valor, String label, int
     * tamanho) throws LogicException{ if(valor!=null &&
     * valor.length()>tamanho){ throw new LogicException("Tamanho do campo
     * "+label+"("+valor.length()+") Ã© maior do que o
     * permitido("+tamanho+")."); } }
     */

    public static void verificaTamanho(final String valor, final String label, final int tamanho, final HttpServletRequest req, final String foco) throws LogicException {

        if (valor != null && valor.length() > tamanho) {
            req.setAttribute("FOCO_TEXTO", foco);
            throw new LogicException("Tamanho do campo " + label + "(" + valor.length() + ") Ã© maior do que o permitido(" + tamanho + ").");
        }

    }

    public static void verificaBranco(final String valor, final String nomeCampo, final String label, final HttpServletRequest req) throws LogicException {

        if (valor == null || valor.trim().length() == 0) {
            req.setAttribute("FOCO_TEXTO", nomeCampo);
            throw new LogicException(label + " : Campo Obrigatório");
        }

    }

    public static void comparaDatas(final Date datInicio, final Date datFim, final String descDatas, final HttpServletRequest req, final String foco) throws LogicException {
        if (datFim != null && datInicio != null) {
            if (datInicio.compareTo(datFim) > 0) {
                req.setAttribute("FOCO_DATA", foco);
                throw new LogicException("A Data inicial(" + descDatas + ") nao pode ser maior que a data final");
            }
        }

    }

    public static void validateDataMenorIgualAtual(final Date datInicio, final String nomeCampo, final HttpServletRequest req, final String foco) throws LogicException {
        if (datInicio != null) {
            if (datInicio.compareTo(getDataAtual()) > 0) {
                req.setAttribute("FOCO_DATA", foco);
                throw new LogicException(nomeCampo + " nao pode ser maior que a data atual");
            }
        }
    }

    public static void comparaInteiros(final Integer inic, final Integer fim, final String descValores, final HttpServletRequest req, final String foco) throws LogicException {
        if (inic != null && fim != null) {
            if (inic.intValue() > fim.intValue()) {
                req.setAttribute("FOCO_TEXTO", foco);
                throw new LogicException(descValores + " inicial nÃ£o pode ser maior que " + descValores + " final");
            }
        }

    }

    public static String convertePrimeiraLetra(final String str, final String tipo) {
        // str = str.toLowerCase();
        try {
            if (tipo.equals("U")) {
                return str.substring(0, 1).toUpperCase() + str.substring(1);
            } else {
                return str.substring(0, 1).toLowerCase() + str.substring(1);
            }
        } catch (final Exception e) {
            return str;
        }
    }

    public static String iniciaisMaiusculas(final String str) {
        String ultimaLetra = " ";
        String novaLetra = "";
        String resultado = "";
        for (int i = 0; i < str.length(); ++i) {
            if (ultimaLetra.equals(" ")) {
                novaLetra = str.substring(i, i + 1).toUpperCase();
            } else {
                novaLetra = str.substring(i, i + 1).toLowerCase();
            }
            ultimaLetra = str.substring(i, i + 1);
            resultado = resultado + novaLetra;
        }
        return resultado;
    }

    public boolean copyFile(final String inFile, final String outFile) {
        InputStream is = null;
        OutputStream os = null;
        byte[] buffer;
        boolean success = true;
        try {
            is = new FileInputStream(inFile);
            os = new FileOutputStream(outFile);
            buffer = new byte[is.available()];
            is.read(buffer);
            os.write(buffer);
        } catch (final IOException e) {
            success = false;
        } catch (final OutOfMemoryError e) {
            success = false;
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (final IOException e) {}
        }
        return success;
    }

    public static Integer[] convertStrToArrayInteger(final String strParaConvert, final String token) {
        if (strParaConvert == null) {
            return null;
        }
        if (strParaConvert.equalsIgnoreCase("")) {
            return null;
        }
        final String strAux[] = strParaConvert.split(token);
        if (strAux == null) {
            return null;
        }
        final Integer[] intRetorno = new Integer[strAux.length];

        for (int i = 0; i < strAux.length; i++) {
            try {
                if ("".equalsIgnoreCase(strAux[i].trim())) {
                    intRetorno[i] = new Integer(0);
                } else {
                    intRetorno[i] = new Integer(Integer.parseInt(strAux[i]));
                }
            } catch (final Exception e) {
                intRetorno[i] = null;
            }
        }
        return intRetorno;
    }

    public static BigDecimal[] convertStrToArrayBigDecimal(final String strParaConvert, final String token) {
        if (strParaConvert == null) {
            return null;
        }
        if (strParaConvert.equalsIgnoreCase("")) {
            return null;
        }
        final String strAux[] = strParaConvert.split(token);
        if (strAux == null) {
            return null;
        }
        final BigDecimal[] intRetorno = new BigDecimal[strAux.length];

        for (int i = 0; i < strAux.length; i++) {
            try {
                if ("".equalsIgnoreCase(strAux[i].trim())) {
                    final double x = 0;
                    intRetorno[i] = new BigDecimal(x);
                } else {
                    intRetorno[i] = new BigDecimal(strAux[i]);
                }
            } catch (final Exception e) {
                intRetorno[i] = null;
            }
        }
        return intRetorno;
    }

    public static Integer convertStringToInteger(final String strParaConvert) {
        if (strParaConvert == null) {
            return new Integer(0);
        }
        if (strParaConvert.trim().equalsIgnoreCase("")) {
            return new Integer(0);
        }

        try {
            final int aux = Integer.parseInt(strParaConvert);
            return new Integer(aux);
        } catch (final Exception e) {
            return new Integer(0);
        }

    }

    public static String getTituloHistoricoExtintor(final Integer id) {
        return getMapHistoricoExtintor().get(id.toString()).toString();
    }

    private static Map getMapHistoricoExtintor() {
        final Map mapHistoricoExtintor = new HashMap();
        mapHistoricoExtintor.put("2", "Inspecionado");
        mapHistoricoExtintor.put("3", "Reparado");
        mapHistoricoExtintor.put("4", "Instrucao");
        mapHistoricoExtintor.put("5", "Incendio");
        mapHistoricoExtintor.put("2", "Inspecionado");
        return mapHistoricoExtintor;
    }

    public static Date geraUmAnoSeguinte(final Date data) throws LogicException {
        if (data == null) {
            return null;
        }
        final String dataStr = CitAjaxUtil.dateToSTR(data);

        int ano = CitAjaxUtil.getDiaMesAno(data, 3);
        ano++;

        final NumberFormat formatoAux = new DecimalFormat("0000");
        final String anoStr = formatoAux.format(ano);

        final String dataAux = dataStr.substring(0, 6) + anoStr;
        return CitAjaxUtil.strTodate(dataAux);
    }

    /**
     * Retorna lista de uma String separada por um token qualquer
     *
     * @param acessos
     * @return
     * @author wagner.filho
     */
    public static List getListaDeToken(final String riscos, final String token) {
        final List listaRetorno = new ArrayList();
        final StringTokenizer st = new StringTokenizer(riscos, token);
        while (st.hasMoreTokens()) {
            listaRetorno.add(st.nextToken());
        }
        return listaRetorno;
    }

    public static String generateNomeBusca(final String nomePar) {
        if (nomePar == null) {
            return null;
        }
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

    public static String formataCnpj(String cnpj) {
        if (cnpj == null || cnpj.trim().length() == 0) {
            return "";
        }
        cnpj = cnpj.trim();
        cnpj = CitAjaxUtil.removeSubstring(cnpj, ".");
        cnpj = CitAjaxUtil.removeSubstring(cnpj, "-");
        cnpj = CitAjaxUtil.removeSubstring(cnpj, "/");

        if (cnpj.length() > 0) {
            cnpj = cnpj.substring(0, 14);
        }
        while (cnpj.length() < 14) {
            cnpj = "0" + cnpj;
        }
        final int f = cnpj.length();
        final String p4 = cnpj.substring(f - 2);
        final String p3 = cnpj.substring(f - 6, f - 2);
        final String p2 = cnpj.substring(f - 9, f - 6);
        final String p1 = cnpj.substring(f - 12, f - 9);
        final String p0 = cnpj.substring(f - 14, f - 12);

        cnpj = p0 + "." + p1 + "." + p2 + "/" + p3 + "-" + p4;

        return cnpj;
    }

    public static boolean isNotVazio(final String valor) {
        return valor != null && !valor.equals("");
    }

    public static String formataSimNao(final String valor) {
        final boolean campoNaoVazio = valor != null && valor.trim().length() > 0;
        return campoNaoVazio && valor.equalsIgnoreCase(CHAR_SIM) ? "Sim" : "Não";
    }

    public static String formatarHora4Digitos(String hora4digitos) {
        if (hora4digitos == null) {
            return ZERO_HORAS_E_MINUTOS_4DGT;
        }
        if (hora4digitos.length() > 4) {
            throw new IllegalArgumentException("A String de hora não pode ser 'null' nem conter mais que 4 caracteres.");
        }
        while (hora4digitos.length() < 4) {
            hora4digitos = '0' + hora4digitos;
        }
        hora4digitos = hora4digitos.substring(0, 2) + ':' + hora4digitos.substring(2) + 'h';
        return hora4digitos;
    }

    public static String getDataAnoMaior(String data, final int qtdAnos) {
        // Retorna uma data com o ano alterado, dependendo do parametro passado,
        // por exemplo, se os parametros passados forem (15/06/2006, 2) , o retorno será: 15/06/2008
        // note que dois anos foram adicionado a data
        try {
            final String anoAntes = data.substring(6, 10);
            int ano = new Integer(anoAntes).shortValue();
            ano = ano + qtdAnos;
            data = data.replaceAll(anoAntes, "" + ano);

            return data;
        } catch (final Exception e) {
            return "";
        }
    }

    /**
     * Retorna quantidade de dias entre as duas datas. As datas devem ser passadas no seguinte formato: dd/mm/yyyy
     *
     * @param String
     *            data maior
     * @param String
     *            data menor
     * @return int dias
     */
    public static int getDiasEntreDatas(final String dataMaior, final String dataMenor) throws LogicException {

        if (dataMaior.length() != 10 || dataMenor.length() != 10) {
            return 0;
        }

        final Date data1 = strTodate(dataMaior);
        final Date data2 = strTodate(dataMenor);

        // Difference in milliseconds between the two times.
        final long timeDifference = data1.getTime() - data2.getTime();

        // Convert milliseconds to days.
        final long seconds = timeDifference / 1000;
        final long minutes = seconds / 60;
        final long hours = minutes / 60;
        final long days = hours / 24;

        return (int) days;

    }

    /**
     * Retorna quantidade de dias entre as duas datas
     *
     * @param Date
     *            data maior
     * @param Date
     *            data menor
     * @return int dias
     */
    public static int getDiasEntreDatas(final Date data1, final Date data2) throws LogicException {

        // Difference in milliseconds between the two times.
        final long timeDifference = data1.getTime() - data2.getTime();

        // Convert milliseconds to days.
        final long seconds = timeDifference / 1000;
        final long minutes = seconds / 60;
        final long hours = minutes / 60;
        final long days = hours / 24;

        return (int) days;

    }

    public static String formatInt(final int numero, final String formato) {
        String retorno;

        final NumberFormat formatoAux = new DecimalFormat(formato);
        retorno = formatoAux.format(numero);
        return retorno;
    }

    public static String formatDecimal(final double value, final String mask) {
        final DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator(',');
        dfs.setGroupingSeparator('.');

        final DecimalFormat df = new DecimalFormat(mask, dfs);
        return df.format(value);
    }

    public static final String encodeHTML(final String string) {
        if (string == null) {
            return "";
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

        String strRet = encoded.toString();
        strRet = strRet.replaceAll("  ", "&nbsp;&nbsp;");

        return strRet;
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

    public static String retiraCodigoInvalido(final String s) {
        if (s == null) {
            return "";
        }
        final String result = s.replaceAll("%20", " ");
        return result;
    }

    public static String formataCep(String cep) {
        if (cep != null) {
            cep = cep.replaceAll("-", "");
            cep = StringUtils.rightPad(cep.trim(), 8, "0");
            final String p1 = cep.substring(0, 5);
            final String p2 = cep.substring(5, 8);
            cep = p1 + "-" + p2;
            return cep;
        } else {
            return "";
        }

    }

    public static String apenasNumeros(final String num) {
        if (num == null) {
            return "";
        }
        String retorno = "";
        for (int i = 0; i < num.length(); i++) {
            if (num.charAt(i) >= '0' && num.charAt(i) <= '9') {
                retorno += num.charAt(i);
            }
        }
        return retorno;
    }

    public static String percentual(final Integer fracao, final Integer total) {
        String retorno = "";
        if (fracao.intValue() > 0 && total.intValue() > 0) {
            final Double percentual = new Double(fracao.doubleValue() * 100 / total.doubleValue());
            retorno = formatDouble(percentual, 2);
            if (retorno.length() < 6) {
                StringUtils.leftPad(retorno, 6);
            }
        } else {
            retorno = "  0.00";
        }
        return "(" + retorno + "%)";

    }

    /**
     * Método para comparar as das e retornar o numero de dias de diferença entre elas
     *
     * Compare two date and return the difference between them in days.
     *
     * @param dataLow
     *            The lowest date
     * @param dataHigh
     *            The highest date
     *
     * @return int
     */
    public static int dataDiff(final Date dataInicio, final Date dataFim) {

        final GregorianCalendar startTime = new GregorianCalendar();
        final GregorianCalendar endTime = new GregorianCalendar();

        final GregorianCalendar curTime = new GregorianCalendar();
        final GregorianCalendar baseTime = new GregorianCalendar();

        startTime.setTime(dataInicio);
        endTime.setTime(dataFim);

        int dif_multiplier = 1;

        // Verifica a ordem de inicio das datas
        if (dataInicio.compareTo(dataFim) < 0) {
            baseTime.setTime(dataFim);
            curTime.setTime(dataInicio);
            dif_multiplier = 1;
        } else {
            baseTime.setTime(dataInicio);
            curTime.setTime(dataFim);
            dif_multiplier = -1;
        }

        final int result_years = 0;
        int result_months = 0;
        int result_days = 0;

        // Para cada mes e ano, vai de mes em mes pegar o ultimo dia para import acumulando
        // no total de dias. Ja leva em consideracao ano bissesto
        while (curTime.get(Calendar.YEAR) < baseTime.get(Calendar.YEAR) || curTime.get(Calendar.MONTH) < baseTime.get(Calendar.MONTH)) {

            final int max_day = curTime.getActualMaximum(Calendar.DAY_OF_MONTH);
            result_months += max_day;
            curTime.add(Calendar.MONTH, 1);

        }

        // Marca que é um saldo negativo ou positivo
        result_months = result_months * dif_multiplier;

        // Retirna a diferenca de dias do total dos meses
        result_days += endTime.get(Calendar.DAY_OF_MONTH) - startTime.get(Calendar.DAY_OF_MONTH);

        return result_years + result_months + result_days;
    }

    /**
     * Compara 2 datas e retorna a maior, verificando os nulos.
     *
     * @param data1
     * @param data2
     * @return
     */
    public static java.sql.Date getDataMaior(final java.sql.Date data1, final java.sql.Date data2) {
        if (data1 == null) {
            return data2;
        }
        if (data2 == null) {
            return data1;
        }
        if (data1.after(data2)) {
            return data1;
        } else {
            return data2;
        }
    }

    public static boolean dataEntreIntervalo(final java.sql.Date dataComparar, final java.sql.Date data1, final java.sql.Date data2) {
        if (dataComparar == null) {
            return false;
        }
        if (data1 == null) {
            return false;
        }
        if (data2 == null) {
            return false;
        }
        if (dataComparar.compareTo(data1) >= 0 && dataComparar.compareTo(data2) <= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Retorna SimpleDateFormat de acordo com a Linguagem e o TipoData informados.
     *
     * @param language
     *            - Linguagem do usuário (EN, PT)
     * @param tipoDate
     *            - TipoData (DATE_DEFAULT: dd/MM/yyyy ou MM/dd/yyyy), (TIMESTAMP_DEFAULT: dd/MM/yyyy HH:mm ou MM/dd/yyyy HH:mm), (TIMESTAMP_WITH_SECONDS: dd/MM/yyyy HH:mm:ss ou
     *            MM/dd/yyyy HH:mm:ss),
     *            (FORMAT_DATABASE: yyyy-MM-dd)
     * @return SimpleDateFormat
     * @author valdoilo.damasceno
     * @since 14.02.2014
     */
    public static SimpleDateFormat getSimpleDateFormatByTipoDataAndLanguage(final TipoDate tipoDate, final String language) {

        SimpleDateFormat simpleDateFormat = null;

        switch (tipoDate) {

        case DATE_DEFAULT:
            simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            if (language != null && StringUtils.isNotBlank(language)) {

                switch (language.trim().toUpperCase()) {
                case "EN":
                    simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
                    break;
                }
            }
            break;

        case TIMESTAMP_DEFAULT:
            simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            if (language != null && StringUtils.isNotBlank(language)) {
                switch (language.trim().toUpperCase()) {
                case "EN":
                    simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
                    break;
                }
            }
            break;

        case TIMESTAMP_WITH_SECONDS:
            simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            if (language != null && StringUtils.isNotBlank(language)) {
                switch (language.trim().toUpperCase()) {
                case "EN":
                    simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                    break;
                }
            }
            break;

        case FORMAT_DATABASE:
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            break;

        }

        return simpleDateFormat;
    }

}
