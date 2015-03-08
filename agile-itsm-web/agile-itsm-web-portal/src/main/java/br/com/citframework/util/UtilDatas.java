package br.com.citframework.util;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.citframework.excecao.LogicException;

public class UtilDatas {

    private static final String ZERO_HORAS_E_MINUTOS_4DGT = "00:00h";
    private static String[] dias = {"31", "30", "31", "30", "31", "30", "31", "31", "30", "31", "30", "31"};
    public static String[] meses = {"citcorpore.texto.mes.janeiro", "citcorpore.texto.mes.fevereiro", "citcorpore.texto.mes.marco", "citcorpore.texto.mes.abril",
        "citcorpore.texto.mes.maio", "citcorpore.texto.mes.junho", "citcorpore.texto.mes.julho", "citcorpore.texto.mes.agosto", "citcorpore.texto.mes.setembro",
        "citcorpore.texto.mes.outubro", "citcorpore.texto.mes.novembro", "citcorpore.texto.mes.dezembro"};

    /**
     * Retorna a data corrente
     *
     * @return String
     */
    @Deprecated
    public static String getDataCorrenteStr() {
        final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(new Date());
    }

    /**
     * Retorna o dia da semana domingo = 1 segunda = 2 terca = 3 quarta = 4 quinta = 5 sexta = 6 sabado = 7
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
            throw new LogicException(Mensagens.getValue("MSG03"));
        }
        return data.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * Retorna o dia da semana domingo = dom segunda = seg terca = ter quarta = qua quinta = qui sexta = sex sabado = sab
     *
     * @param data
     *            Date
     * @return String
     * @throws LogicException
     */
    public static String getDiaSemana(final Date data) throws LogicException {
        final int dia = getDiaSemana(UtilDatas.dateToSTR(data));
        if (dia == 1) {
            return "dom";
        } else if (dia == 2) {
            return "seg";
        } else if (dia == 3) {
            return "ter";
        } else if (dia == 4) {
            return "qua";
        } else if (dia == 5) {
            return "qui";
        } else if (dia == 6) {
            return "sex";
        } else if (dia == 7) {
            return "sab";
        } else {
            return "";
        }
    }

    /**
     * Retorna a hora formatada
     *
     * @return String
     */
    public static String formatHoraFormatadaStr(final Date hora) {
        final SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(hora);
    }

    public static String formatHoraFormatadaHHMMSSStr(final Date hora) {
        final SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(hora);
    }

    /**
     * DEPRECATED dd/MM/yyyy HH:mm:ss
     *
     * @param dataHora
     * @return
     */
    @Deprecated
    public static String formatTimestamp(final Timestamp dataHora) {
        if (dataHora == null) {
            return "";
        }
        final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return format.format(dataHora);
    }

    @Deprecated
    public static String formatTimestampUS(final Timestamp dataHora) {
        if (dataHora == null) {
            return "";
        }
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(dataHora);
    }

    /**
     * Formata uma hora String passada como parametro. Exemplo: 1000 e retorna 10:00
     *
     * @param hora
     * @return
     */
    public static String formatHoraStr(final String hora) {
        return hora.substring(0, 2) + ":" + hora.substring(2, 4);
    }

    /**
     * Formata uma hora String passada como parametro. Exemplo: 8:00 e retorna 0800
     *
     * @param hora
     * @return
     */
    public static String formatHoraHHMM(final String hora) {
        if (UtilStrings.isNotVazio(hora)) {
            String aux = hora.replaceFirst(":", "");
            if (UtilStrings.isNotVazio(aux)) {
                for (int i = aux.length(); i < 4; i++) {
                    aux = "0" + aux;
                }
                return aux;
            }
        }
        return "";
    }

    /**
     * DEPRECATED
     *
     * @param data
     * @return
     * @throws LogicException
     * @author valdoilo.damasceno
     */
    public static final Date strTodate(final String data) throws LogicException {
        if (data == null || data.length() == 0) {
            return null;
        }
        try {
            SimpleDateFormat date = null;
            if (data.indexOf("-") > -1) {
                if (data.length() == 10) {
                    date = new SimpleDateFormat("yyyy-MM-dddd");
                } else {
                    date = new SimpleDateFormat("yyyy-MM-dddd HH:mm:ss");
                }
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
            throw new LogicException(Mensagens.getValue("MSG03"));
        }
    }

    /**
     * DEPRECATED
     * Gera uma timestamp a partir de uma string passada como parametro
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static final Timestamp strToTimestamp(final String data) throws Exception {
        if (data == null || data.length() == 0) {
            return null;
        }
        final Timestamp result = new Timestamp(strTodate(data).getTime());

        return result;
    }

    /**
     * DEPRECATED
     * Gera um java.sql.Date a partir de uma Data String
     *
     * @param data
     * @return
     * @throws LogicException
     */
    public static final java.sql.Date strToSQLDate(final String data) throws LogicException {
        if (data == null || data.length() == 0) {
            return null;
        }
        return new java.sql.Date(strTodate(data).getTime());
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

    /**
     * Retorna a Ultima data do mes/ano - de acordo com o mes (Considerando ano bisexto) Exemplo: se passar a data 23/01/2007, retornará 31/01/2007.
     *
     * @param data
     * @return
     * @throws Exception
     */
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

    /**
     * Acrescenta Meses em uma data, caso queira subtrair meses, basta passar o segundo parametro negativo. Exemplo: acrescentaSubtraiMesesData(new Date(), 3);
     * acrescentaSubtraiMesesData(new Date(),
     * -4);
     *
     * @param data
     * @param mes
     *            (numero de meses a acrescentar ou retirar), pode ser negativo.
     * @return retorna a data final calculada.
     * @throws LogicException
     */
    public static final Date acrescentaSubtraiMesesData(final Date data, final int mes) throws LogicException {
        if (data == null) {
            return null;
        }

        final int mesCalculo = getDiaMesAno(data, 2); // Pega o mes
        int anoCalculo = getDiaMesAno(data, 3); // Pega o ano
        final int dia = getDiaMesAno(data, 1); // Pega o dia
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

        return strTodate(sDia + "/" + sMes + "/" + anoCalculo);
    }

    /**
     * Extrai dia, mes ou ano de uma data )
     *
     * @param data
     * @param ind
     *            (Sendo 1 - Dia; 2 - Mes; 3 - Ano)
     * @return Retorna dia , mes ou ano da data passada como parametro, segundo o Indice Passado. Ex: 1 - Dia 2 - Mes 3 - Ano
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

    /**
     * Obtem a diferenca em mses de 2 datas.
     *
     * @param inic
     * @param fim
     * @return quantidade de meses
     */
    public final static int getDifMeses(final Date inic, final Date fim) {
        final int anoInic = getDiaMesAno(inic, 3);
        final int anoFim = getDiaMesAno(fim, 3);
        final int mesInic = getDiaMesAno(inic, 2);
        final int mesFim = getDiaMesAno(fim, 2);
        int difAno = 0;
        int difMes = 0;
        if (anoInic < anoFim) {
            difAno = anoFim - anoInic;
            difAno = difAno * 12;
        }
        difMes = mesFim + difAno - mesInic;
        return difMes + 1;
    }

    /**
     * Obtem a descricao do Mes passado como parametro Exemplo: getDescricaoMes(12), retorna: Dezembro
     *
     * @param mes
     * @return
     */
    public static final String getDescricaoMes(final Integer mes) {
        if (mes.intValue() > 0 && mes.intValue() < 13) {
            final int iMes = mes.intValue() - 1;
            return meses[iMes];
        }
        return "";
    }

    /**
     * Altera uma data de acordo com os parametros
     *
     * @param data
     * @param quantidade
     * @param unidade
     * @return
     */
    public static final Date alteraData(final Date data, final int quantidade, final int unidade) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        calendar.add(unidade, quantidade);
        return calendar.getTime();
    }

    /**
     * Obtem uma data que representa o proximo mes de uma data passada como parametro.
     *
     * @param data
     * @return
     */
    public static final Date getProximoMes(final Date data) {
        int mes = getDiaMesAno(data, 2);
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        calendar.set(Calendar.MONTH, mes++);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * Obtem o ultimo dia do mes da data passada como parametro.
     *
     * @param data
     * @return
     * @throws ParseException
     */
    public static final int getUltimoDiaMes(final Date data) throws ParseException {
        final Date datTmp = getUltimaDataMes(data);
        return getDiaMesAno(datTmp, 1);
    }

    /**
     * Gera um java.sql.Date a partir de um objeto Date
     *
     * @param data
     * @return
     * @throws ParseException
     */
    public static final java.sql.Date getSqlDate(Date data) throws ParseException {
        final SimpleDateFormat spd = new SimpleDateFormat("dd/MM/yyyy 00:00:00");
        final SimpleDateFormat spd1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        data = spd1.parse(spd.format(data));
        final java.sql.Date datasql = new java.sql.Date(data.getTime());
        return datasql;
    }

    /**
     * DEPRECATED
     * Formata um objeto java.sql.Date para String no formato dd/MM/yyyy
     *
     * @param data
     * @return
     * @author valdoilo.damasceno
     * @since 03.02.2014
     */
    public static final String dateToSTR(final java.sql.Date data) {
        if (data == null) {
            return null;
        }
        return dateToSTR(new Date(data.getTime()));
    }

    /**
     * DEPRECATED
     * Formata um objeto Date para String no formato dd/MM/yyyy
     *
     * @param data
     *            - Date
     * @return Data no formato dd/MM/yyyy String
     * @author valdoilo.damasceno
     * @since 03.02.2014
     */
    public static final String dateToSTR(final Date data) {
        return dateToSTRWithFormat(data, "dd/MM/yyyy");
    }

    /**
     * Formata um objeto java.sql.Date para String no formato passado como parametro.
     *
     * @param data
     * @return Data no formato String informado.
     * @author valdoilo.damasceno
     * @since 03.02.2014
     */
    public static final String dateToSTRWithFormat(final Date data, final String formato) {
        if (data == null) {
            return "";
        }
        final SimpleDateFormat spd = new SimpleDateFormat(formato);
        return spd.format(data).trim();
    }

    /**
     * Formata um objeto java.sql.Date para String no formato passado como parametro.
     *
     * @param data
     * @return
     */
    public static final String dateToSTR(final Date data, final String formato) {
        if (data == null) {
            return "";
        }
        final SimpleDateFormat spd = new SimpleDateFormat(formato);
        return spd.format(data).trim();
    }

    public static String dateToSTRExtenso(final java.sql.Date data, final boolean withDescDiaSemana) {
        String diaf = null;
        String mesf = null;
        String retorno = null;
        final Calendar calendar = new GregorianCalendar();
        calendar.setTime(data);
        final int semana = calendar.get(Calendar.DAY_OF_WEEK);
        final int mes = calendar.get(Calendar.MONTH);
        final int dia = calendar.get(Calendar.DAY_OF_MONTH);
        final int ano = calendar.get(Calendar.YEAR);

        // semana
        switch (semana) {
        case 1:
            diaf = "Domingo";
            break;
        case 2:
            diaf = "Segunda";
            break;
        case 3:
            diaf = "Terça";
            break;
        case 4:
            diaf = "Quarta";
            break;
        case 5:
            diaf = "Quinta";
            break;
        case 6:
            diaf = "Sexta";
            break;
        case 7:
            diaf = "Sábado";
            break;
        }
        // mês
        switch (mes) {
        case 0:
            mesf = "Janeiro";
            break;
        case 1:
            mesf = "Fevereiro";
            break;
        case 2:
            mesf = "Março";
            break;
        case 3:
            mesf = "Abril";
            break;
        case 4:
            mesf = "Maio";
            break;
        case 5:
            mesf = "Junho";
            break;
        case 6:
            mesf = "Julho";
            break;
        case 7:
            mesf = "Agosto";
            break;
        case 8:
            mesf = "Setembro";
            break;
        case 9:
            mesf = "Outubro";
            break;
        case 10:
            mesf = "Novembro";
            break;
        case 11:
            mesf = "Dezembro";
            break;
        }

        if (withDescDiaSemana) {
            retorno = diaf + ", " + dia + " de " + mesf + " de " + ano;
        } else {
            retorno = "" + dia + " de " + mesf + " de " + ano;
        }
        return retorno;
    }

    /**
     * Retorna uma string no formato MM/yyyy de uma data passada como parametro.
     *
     * @param data
     * @return
     */
    public static final String getMesAno(final Date data) {
        if (data == null) {
            return null;
        }
        final SimpleDateFormat spd = new SimpleDateFormat("MM/yyyy");
        return spd.format(data).trim();
    }

    /**
     * Verifica se uma data eh Util, ou seja, nao cai no sabado e no domingo (nao considera feriados).
     *
     * @param data
     * @return
     */
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

    /**
     * Faz o calculo da idade com base na data passada como parametro.
     *
     * @param dDataNasc
     * @param type
     *            - pode ser "SHORT" ou "LONG" (para determinar se saira: "19a 13m 5d" ou "19 anos 13 meses 5 dias"
     * @return
     */
    public static String calculaIdade(final Date dDataNasc, final String type) {
        final Calendar hoje = Calendar.getInstance();
        final Date now = hoje.getTime();
        return calculaIdade(dDataNasc, now, type);
    }

    public static String calculaIdade(final Date dDataNasc, final Date ateData, final String type) {
        if (dDataNasc == null) {
            return "";
        }

        int anoResult = 0;
        int mesResult = 0;
        int diaResult = 0;
        String retorno = "";
        String strAno;
        String strAnos;
        String strMes;
        String strMeses;
        String strDia;
        String strDias;

        final int anoHoje = getYear(ateData);
        final int anoDataParm = getYear(dDataNasc);
        final int mesHoje = getMonth(ateData);
        final int mesDataParm = getMonth(dDataNasc);
        final int diaHoje = getDay(ateData);
        final int diaDataParm = getDay(dDataNasc);

        if ("SHORT".equalsIgnoreCase(type)) {
            strAno = strAnos = "a";
            strMes = strMeses = "m";
            strDia = strDias = "d";
        } else {
            strAno = " ano";
            strAnos = " anos";
            strMes = " mes";
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
            int numDiasMes = 30;
            try {
                numDiasMes = UtilDatas.getUltimoDiaMes(dDataNasc);
            } catch (final ParseException e) {
                numDiasMes = 30;
            }
            diaResult = numDiasMes + diaResult;
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
     * Faz o calculo da idade com base na data passada como parametro (retorna apenas mes e ano)
     *
     * @param dDataNasc
     * @param type
     *            - pode ser "SHORT" ou "LONG" (para determinar se saira: "19a 13m" ou "19 anos 13 meses"
     * @return Retorna apenas mes e ano
     */
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
            strMes = " mes";
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

    /**
     * Faz o calculo da idade com base na data passada como parametro e a data de referencia. retorna apenas os anos retorna um numero representando a quantidade de anos.
     *
     * @param dDataNasc
     *            - Data de Nascimento
     * @param dataRef
     *            - Data de referencia para calculo da idade.
     * @return
     */
    public static Integer calculaIdadeEmAnos(final Date dDataNasc, final Date dataRef) {
        if (dDataNasc == null) {
            return new Integer(0);
        }
        if (dataRef == null) {
            return new Integer(0);
        }

        int anoResult = 0;

        final int anoHoje = getYear(dataRef);
        final int anoDataParm = getYear(dDataNasc);
        final int mesHoje = getMonth(dataRef);
        final int mesDataParm = getMonth(dDataNasc);
        final int diaHoje = getDay(dataRef);
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

    /**
     * Faz o calculo da idade com base na data passada como parametro. retorna apenas os anos retorna um numero representando a quantidade de anos.
     *
     * @param dDataNasc
     * @return
     */
    public static Integer calculaIdadeEmAnos(final Date dDataNasc) {
        if (dDataNasc == null) {
            return new Integer(0);
        }

        final Calendar hoje = Calendar.getInstance();
        final Date now = hoje.getTime();

        return calculaIdadeEmAnos(dDataNasc, now);
    }

    /**
     * Faz o calculo da idade com base na data passada como parametro. retorna apenas os meses retorna um numero representando a quantidade de meses.
     *
     * @param dDataNasc
     * @return
     */
    public static Integer calculaIdadeEmMeses(final Date dDataNasc) {
        if (dDataNasc == null) {
            return new Integer(0);
        }

        int anoResult = 0;
        int mesResult = 0;
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

        mesResult = mesHoje - mesDataParm;
        if (mesResult < 0 || mesHoje == mesDataParm && diaHoje < diaDataParm) {
            mesResult = mesResult + 12;
        }

        if (anoResult > 0) {
            if (anoResult == 1) {
                return new Integer(1 * 12 + mesResult);
            } else {
                return new Integer(anoResult * 12 + mesResult);
            }
        }
        return new Integer(mesResult);
    }

    public static Integer calculaIdadeEmMeses(final Date dDataNasc, final Date dataRef) {
        if (dDataNasc == null) {
            return new Integer(0);
        }
        if (dataRef == null) {
            return new Integer(0);
        }

        int anoResult = 0;
        int mesResult = 0;
        final Date dataReferencia = dataRef;

        final int anoHoje = getYear(dataReferencia);
        final int anoDataParm = getYear(dDataNasc);
        final int mesHoje = getMonth(dataReferencia);
        final int mesDataParm = getMonth(dDataNasc);
        final int diaHoje = getDay(dataReferencia);
        final int diaDataParm = getDay(dDataNasc);

        anoResult = anoHoje - anoDataParm;
        if (mesHoje < mesDataParm || mesHoje == mesDataParm && diaHoje < diaDataParm) {
            anoResult = anoResult - 1;
        }

        mesResult = mesHoje - mesDataParm;
        if (mesResult < 0 || mesHoje == mesDataParm && diaHoje < diaDataParm) {
            mesResult = mesResult + 12;
        }

        if (anoResult > 0) {
            if (anoResult == 1) {
                return new Integer(1 * 12 + mesResult);
            } else {
                return new Integer(anoResult * 12 + mesResult);
            }
        }
        return new Integer(mesResult);
    }

    /**
     * Faz o calculo da idade com base na data passada como parametro. Retorna a quantidade de anos. retorna um int.
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

    /**
     * Incrementa uma quantidade de dias em uma data
     *
     * @param data
     * @param numDias
     * @return
     */
    public static Date incrementaDiasEmData(final Date data, final int numDias) {
        final GregorianCalendar c = new GregorianCalendar();
        c.setTime(data);
        c.add(Calendar.DATE, numDias);
        return c.getTime();
    }

    /**
     * Pega o ano de uma data
     *
     * @param data
     * @return
     */
    public static int getYear(final Date data) {
        final Calendar c = Calendar.getInstance();
        c.setTime(data);
        return c.get(Calendar.YEAR);
    }

    /**
     * Pega o mes de uma data
     *
     * @param data
     * @return
     */
    public static int getMonth(final Date data) {
        final Calendar c = Calendar.getInstance();
        c.setTime(data);
        return c.get(Calendar.MONTH) + 1;
    }

    /**
     * Pega o dia de uma data
     *
     * @param data
     * @return
     */
    public static int getDay(final Date data) {
        final Calendar c = Calendar.getInstance();
        c.setTime(data);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Pega a data Data Atual em java.sql.Date
     *
     * @param data
     * @return
     */
    public static java.sql.Date getDataAtual() {
        final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return strToSQLDate(sdf.format(new Date()));
        } catch (final LogicException e) {
            return new java.sql.Date(new Date().getTime());
        }
    }

    /**
     * Pega a hora atual (java.sql.Time)
     *
     * @return
     */
    public static java.sql.Time getHoraAtual() {
        return new java.sql.Time(new Date().getTime());
    }

    /**
     * Pega data e hora atual (timestamp)
     *
     * @return
     */
    public static Timestamp getDataHoraAtual() {
        return new Timestamp(new Date().getTime());
    }

    /**
     * Compara 2 datas
     *
     * @param datInicio
     * @param datFim
     * @param descDatas
     * @param req
     * @param foco
     * @throws LogicException
     */
    public static void comparaDatas(final Date datInicio, final Date datFim, final String descDatas) throws LogicException {
        if (datFim != null && datInicio != null) {
            if (datInicio.compareTo(datFim) > 0) {
                throw new LogicException("A Data inicial(" + descDatas + ") nao pode ser maior que a data final");
            }
        }
    }

    /**
     * Valida uma data em comparacao com a data atual.
     *
     * @param datInicio
     * @param nomeCampo
     * @throws LogicException
     */
    public static void validateDataMenorIgualAtual(final Date datInicio, final String nomeCampo) throws LogicException {
        if (datInicio != null) {
            if (datInicio.compareTo(getDataAtual()) > 0) {
                throw new LogicException(nomeCampo + " nao pode ser maior que a data atual");
            }
        }
    }

    /**
     * Obtem um ano seguinte atraves de uma data passada como parametro. Retona a data calculada.
     *
     * @param data
     * @return
     * @throws LogicException
     */
    public static Date geraUmAnoSeguinte(final Date data) throws LogicException {
        if (data == null) {
            return null;
        }
        final String dataStr = dateToSTR(data);

        int ano = getDiaMesAno(data, 3);
        ano++;

        final NumberFormat formatoAux = new DecimalFormat("0000");
        final String anoStr = formatoAux.format(ano);

        final String dataAux = dataStr.substring(0, 6) + anoStr;
        return strTodate(dataAux);
    }

    /**
     * Formata hora em 4 digitos com h no final Exemplo: 1000 retorna 10:00h
     *
     * @param hora4digitos
     * @return
     */
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

    /**
     * Acrescenta uma quantidade de anos em uma data
     *
     * @param data
     *            em formato DD/MM/YYYY (String)
     * @param qtdAnos
     * @return
     */
    public static String acrecentaAnoEmData(String data, final int qtdAnos) {
        // Retorna uma data com o ano alterado, dependendo do parametro passado, por exemplo, se os parametros passados forem (15/06/2006, 2) , o
        // retorno será: 15/06/2008 note que dois anos foram adicionado a data
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

        // Para cada mes e ano, vai de mes em mes pegar o ultimo dia para import acumulando no total de dias. Ja leva em consideracao ano bissesto
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
        }
        return data2;
    }

    /**
     * Verifica se uma data esta no intervalo (data inicio e fim)
     *
     * @param dataComparar
     * @param data1
     * @param data2
     * @return
     */
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
        }
        return false;
    }

    /**
     * Verifica se uma data esta no intervalo (data inicio e fim)
     *
     * @param dataComparar
     * @param data1
     * @param data2
     * @return
     */
    public static boolean dataEntreIntervalo(final Date dataComparar, final Date data1, final Date data2) {
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
     * Retorna o numero de minutos
     *
     * @param horaFim
     * @param horaIni
     * @param formatoHora
     *            - hh:mm:ss
     * @return
     */
    public static double subtraiHora(final String horaFim, final String horaIni, final String formatoHora) {
        final SimpleDateFormat formatter = new SimpleDateFormat(formatoHora);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));

        double min_1 = 0;
        double min_2 = 0;
        double result = 0;

        min_1 = getHoras(horaFim, formatter);
        min_2 = getHoras(horaIni, formatter);

        result = (min_1 - min_2) * 60;
        if (min_1 < min_2) {
            result = (min_2 - min_1) * 60;
        }

        if (result == 0) {
            return 0;
        }
        final double x = result / 3600 / 1000;
        return x * 60;
    }

    private static final String DAYS_MUST_GREATER_THAN_ZERO = "Days must be greater than zero";
    private static final String DATE_MUST_NOT_BE_NULL = "Date must not be null";

    /**
     * Adiciona dias em uma data
     *
     * @param date
     *            data na qual serão adicionados os dias
     * @param days
     *            dias a serem adicionados
     * @return {@link java.sql.Date} com os dias adicionados
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 28/10/2014
     */
    public static java.sql.Date addDaysOnDate(final Date date, final Integer days) {
        Assert.isTrue(days > 0, DAYS_MUST_GREATER_THAN_ZERO);
        return addSubtractDaysFromDate(date, days);
    }

    /**
     * Subtrai dias de uma data
     *
     * @param date
     *            data da qual serão subtraídos os dias
     * @param days
     *            dias a serem subtraídos
     * @return {@link java.sql.Date} com os dias subtraídos
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 28/10/2014
     */
    public static java.sql.Date subtractDaysFromDate(final Date date, final Integer days) {
        Assert.isTrue(days > 0, DAYS_MUST_GREATER_THAN_ZERO);
        return addSubtractDaysFromDate(date, -days);
    }

    /**
     * Subtrai ou adiciona dias de/em uma data
     *
     * @param date
     *            data da qual serão subtraídos os dias
     * @param days
     *            dias a serem subtraídos
     * @return {@link java.sql.Date} com os dias subtraídos
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 28/10/2014
     */
    private static java.sql.Date addSubtractDaysFromDate(final Date date, final Integer days) {
        Assert.notNull(date, DATE_MUST_NOT_BE_NULL);
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, days);
        return new java.sql.Date(cal.getTime().getTime());
    }

    /**
     * Converte um {@link Date} para um {@link java.sql.Date}
     *
     * @param date
     *            {@link Date} a ser convertido
     * @return {@link java.sql.Date}
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 29/10/2014
     */
    public static java.sql.Date toDateSQL(final Date date) {
        Assert.notNull(date, DATE_MUST_NOT_BE_NULL);
        return new java.sql.Date(date.getTime());
    }

    /**
     * Converte um {@link Date} para um {@link java.sql.Timestamp}
     *
     * @param date
     *            {@link Date} a ser convertido
     * @return {@link java.sql.Timestamp}
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 08/12/2014
     */
    public static java.sql.Timestamp toTimestampSQL(final Date date) {
        Assert.notNull(date, DATE_MUST_NOT_BE_NULL);
        return new java.sql.Timestamp(date.getTime());
    }

    private static long getHoras(final String hora, final SimpleDateFormat formatter) {
        final String hor = hora.substring(0, 2);
        final String min = hora.substring(3, 5);
        final String sec = hora.substring(6);

        final int intHor = Integer.parseInt(hor);
        final int intMin = Integer.parseInt(min);
        final int intSec = Integer.parseInt(sec);

        final long ret = intSec * 1000 + intMin * 60 * 1000 + intHor * 60 * 60 * 1000;
        final long rhora = ret / 60;
        return rhora;
    }

    public static String getHoraHHMMSS(final Timestamp dataHoraRef) {
        final StringBuilder sb = new StringBuilder();
        final GregorianCalendar d = new GregorianCalendar();
        d.setTime(dataHoraRef);

        String aux = "" + d.get(Calendar.HOUR_OF_DAY);
        if (aux.length() < 2) {
            aux = "0" + aux;
        }
        sb.append(aux);

        aux = "" + d.get(Calendar.MINUTE);
        if (aux.length() < 2) {
            aux = "0" + aux;
        }
        sb.append(":");
        sb.append(aux);

        aux = "" + d.get(Calendar.SECOND);
        if (aux.length() < 2) {
            aux = "0" + aux;
        }
        sb.append(":");
        sb.append(aux);

        return sb.toString();
    }

    public static String getHoraHHMM(final Timestamp dataHoraRef) {
        final StringBuilder sb = new StringBuilder();
        final GregorianCalendar d = new GregorianCalendar();
        d.setTime(dataHoraRef);

        String aux = "" + d.get(Calendar.HOUR_OF_DAY);
        if (aux.length() < 2) {
            aux = "0" + aux;
        }
        sb.append(aux);

        aux = "" + d.get(Calendar.MINUTE);
        if (aux.length() < 2) {
            aux = "0" + aux;
        }
        sb.append(":");
        sb.append(aux);

        return sb.toString();
    }

    public static long calculaDiferencaTempoEmMilisegundos(final Timestamp dataHoraFinal, final Timestamp dataHoraInicial) throws Exception {
        if (dataHoraFinal.compareTo(dataHoraInicial) < 0) {
            return 0;
        }

        final long difDias = dataDiff(new Date(dataHoraInicial.getTime()), new Date(dataHoraFinal.getTime()));

        final GregorianCalendar dInicial = new GregorianCalendar();
        dInicial.setTime(dataHoraInicial);

        final GregorianCalendar dFinal = new GregorianCalendar();
        dFinal.setTime(dataHoraFinal);

        int intHor = dInicial.get(Calendar.HOUR_OF_DAY);
        int intMin = dInicial.get(Calendar.MINUTE);
        int intSec = dInicial.get(Calendar.SECOND);

        final long tempoInicial = intSec * 1000 + intMin * 60 * 1000 + intHor * 60 * 60 * 1000;

        intHor = dFinal.get(Calendar.HOUR_OF_DAY);
        intMin = dFinal.get(Calendar.MINUTE);
        intSec = dFinal.get(Calendar.SECOND);
        final long tempoFinal = intSec * 1000 + intMin * 60 * 1000 + intHor * 60 * 60 * 1000;

        return difDias * 24 * 60 * 60 * 1000 + tempoFinal - tempoInicial;
    }

    public static Timestamp somaSegundos(final Timestamp ts, final int segundos) {
        final GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(ts);
        calendar.add(Calendar.SECOND, segundos);
        return new Timestamp(calendar.getTime().getTime());
    }

    /**
     * Retorna um timeStamp no formato String
     *
     * @author rodrigo.oliveira
     */
    public String geraTimeStamp() throws Exception {
        final Timestamp time = new Timestamp(System.currentTimeMillis());
        return time.toString();
    }

    /**
     * Valida o formato de uma determinada Data retornando true se a data informada é do formato informado.
     *
     * @param dateToValidate
     * @param dateFromat
     * @return true - Data válida para o formato informado; false - Data inválida para o formato informado.
     * @author flavio.santana
     * @author valdoilo.damasceno
     * @since 06.03.2014
     */
    public static boolean isThisDateValid(final String dateToValidate, final String dateFromat) {
        if (dateToValidate == null) {
            return false;
        }
        final SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
        sdf.setLenient(false);
        try {
            sdf.parse(dateToValidate);
        } catch (final ParseException e) {
            return false;
        }
        return true;
    }

    public static final String formataDataParaOracle(final String data) {
        if (data == null) {
            return "";
        }
        String dataFormatada = "";

        final String[] partes = data.split("/");
        dataFormatada += partes[2] + "/";
        dataFormatada += partes[1] + "/";
        dataFormatada += partes[0];

        return dataFormatada;
    }

    /**
     * Retorna o enumerado TipoDate da Data String informada por parâmetro.
     *
     * @param data
     *            - Data no formato String.
     * @param language
     *            - Linguagem do usuário.
     * @return TipoDate - Enum TipoDate.
     * @throws LogicException
     * @author rodrigo.acorse
     * @since 27.02.2014
     */
    public static TipoDate getTipoDate(final String data, final String language) throws LogicException {
        if (data == null || data.length() == 0) {
            return null;
        }

        if (data.indexOf("-") > -1) {
            if (data.split(" ").length > 1) {
                return TipoDate.FORMAT_DATABASE_WITH_HOUR_AND_SECOND;
            } else {
                return TipoDate.FORMAT_DATABASE;
            }
        } else if (data.split(" ").length > 1) {
            final String hora = data.split(" ")[1];

            if (hora.split(":").length > 2) {
                return TipoDate.TIMESTAMP_WITH_SECONDS;
            } else {
                return TipoDate.TIMESTAMP_DEFAULT;
            }
        }
        return TipoDate.DATE_DEFAULT;
    }

    /**
     * Valida se a data String informada é uma data válida para o Formato.
     *
     * @param dateToValidate
     *            - Data String a ser validada.
     * @param sdf
     *            - Formato da Data.
     * @return true - Data válida; false - Data inválida.
     * @author rodrigo.acorse
     * @since 27.02.2014
     * @since 06.03.2014
     */
    public static boolean isThisDateValid(final String dateToValidate, final SimpleDateFormat sdf) {
        if (dateToValidate == null) {
            return false;
        }
        sdf.setLenient(false);
        try {
            sdf.parse(dateToValidate);
        } catch (final ParseException e) {
            return false;
        }
        return true;
    }

    /**
     * Converte Data do tipo Date para String de acordo com o {@link TipoDate}.
     *
     * @param tipoDate
     *            tipo de formatação a ser aplicada
     * @param date
     *            data a ser formatada
     * @return data em {@link String}
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 06/11/2014
     */
    public static String convertDateToString(final TipoDate tipoDate, final Date date) {
        return convertDateToString(tipoDate, date, null);
    }

    /**
     * Converte Data do tipo Date para String de acordo com o Tipo e a linguagem.
     *
     * @param tipoDate
     *            DATE_DEFAULT: dd/MM/yyyy ou MM/dd/yyyy,
     *            TIMESTAMP_DEFAULT: dd/MM/yyyy HH:mm ou MM/dd/yyyy HH:mm,
     *            TIMESTAMP_WITH_SECONDS: dd/MM/yyyy HH:mm:ss ou MM/dd/yyyy HH:mm:ss,
     *            FORMAT_DATABASE: yyyy-MM-dd
     * @param date
     *            - Data do tipo Date.
     * @param language
     *            - Linguagem do Usuário
     * @return Data no formato String
     * @author valdoilo.damasceno
     * @since 06.02.2014
     */
    public static String convertDateToString(final TipoDate tipoDate, final Date date, final String language) {
        if (date != null) {
            final SimpleDateFormat simpleDateFormat = getSimpleDateFormatByTipoDataAndLanguage(tipoDate, language);
            return simpleDateFormat.format(date);
        }
        return "";
    }

    /**
     * Converte String para Data de acordo com o TipoDate.
     *
     * @param tipoDate
     *            DATE_DEFAULT: dd/MM/yyyy ou MM/dd/yyyy,
     *            TIMESTAMP_DEFAULT: dd/MM/yyyy HH:mm ou MM/dd/yyyy HH:mm,
     *            TIMESTAMP_WITH_SECONDS: dd/MM/yyyy HH:mm:ss ou MM/dd/yyyy HH:mm:ss
     *            FORMAT_DATABASE: yyyy-MM-dd
     * @param data
     *            - Date em String.
     * @param language
     *            - Linguagem do Usuário.
     * @return Date
     * @throws ParseException
     * @author valdoilo.damasceno
     * @since 06.02.2014
     */
    public static Date convertStringToDate(final TipoDate tipoDate, final String data, final String language) throws ParseException {
        final SimpleDateFormat simpleDateFormat = getSimpleDateFormatByTipoDataAndLanguage(tipoDate, language);
        return simpleDateFormat.parse(data);
    }

    /**
     * Converte Data no formato String para java.sql.Date de acordo com o TipoDate e language.
     *
     * @param tipoDate
     *            DATE_DEFAULT: dd/MM/yyyy ou MM/dd/yyyy,
     *            TIMESTAMP_DEFAULT: dd/MM/yyyy HH:mm ou MM/dd/yyyy HH:mm,
     *            TIMESTAMP_WITH_SECONDS: dd/MM/yyyy HH:mm:ss ou MM/dd/yyyy HH:mm:ss,
     *            FORMAT_DATABASE: yyyy-MM-dd
     * @param data
     *            - Data no formato String.
     * @param language
     *            - Locale do usuário (PT, EN)
     * @return java.sql.Date
     * @throws ParseException
     * @author valdoilo.damasceno
     * @since 07.02.2014
     */
    public static java.sql.Date convertStringToSQLDate(final TipoDate tipoDate, final String data, final String language) throws ParseException {
        final SimpleDateFormat simpleDateFormat = getSimpleDateFormatByTipoDataAndLanguage(tipoDate, language);
        final Date date = simpleDateFormat.parse(data);
        return new java.sql.Date(date.getTime());
    }

    /**
     * TODO
     *
     * @param tipoDate
     * @param data
     * @param language
     * @return
     * @throws LogicException
     * @throws ParseException
     * @author valdoilo.damasceno
     * @since 11.02.2014
     */
    public static final Timestamp convertStringToTimestamp(final TipoDate tipoDate, final String data, final String language) throws LogicException, ParseException {
        if (data == null || data.length() == 0) {
            return null;
        }
        return new Timestamp(convertStringToDate(tipoDate, data, language).getTime());
    }

    /**
     * Retorna a Data informada, no formato Timestamp, com a última hora do dia, portanto 23:59:59. Método utilizado para as consultas em que é informado a DataFim.
     *
     * @param data
     *            - Data do tipo Date.
     * @return Timestamp - Data com a última hora do dia 23:59:59.
     * @throws LogicException
     * @throws ParseException
     * @author valdoilo.damasceno
     */
    public static final Timestamp getTimeStampComUltimaHoraDoDia(final Date data) throws LogicException, ParseException {
        final String dataHora = data + " 23:59:59";
        return convertStringToTimestamp(TipoDate.FORMAT_DATABASE_WITH_HOUR_AND_SECOND, dataHora, "pt");
    }

    /**
     * Retorna SimpleDateFormat de acordo com a Linguagem e o TipoData informados.
     *
     * @param language
     *            - Linguagem do usuário (EN, PT)
     * @param tipoDate
     *            (DATE_DEFAULT: dd/MM/yyyy ou MM/dd/yyyy),
     *            (TIMESTAMP_DEFAULT: dd/MM/yyyy HH:mm ou MM/dd/yyyy HH:mm),
     *            (TIMESTAMP_WITH_SECONDS: dd/MM/yyyy HH:mm:ss ou MM/dd/yyyy HH:mm:ss),
     *            (FORMAT_DATABASE: yyyy-MM-dd),
     *            (FORMAT_DATABASE_WITH_HOUR_AND_SECOND: yyyy-MM-dd HH:mm:ss)
     * @return SimpleDateFormat
     * @author valdoilo.damasceno
     * @since 04.02.2014
     * @since 06.03.2014
     */
    public static SimpleDateFormat getSimpleDateFormatByTipoDataAndLanguage(final TipoDate tipoDate, final String language) {
        SimpleDateFormat simpleDateFormat = null;

        switch (tipoDate) {
        case DATE_DEFAULT:
            simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            if (StringUtils.isNotBlank(language)) {
                switch (language.trim().toUpperCase()) {
                case "EN":
                    simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
                    break;
                }
            }
            break;
        case TIMESTAMP_DEFAULT:
            simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            if (StringUtils.isNotBlank(language)) {
                switch (language.trim().toUpperCase()) {
                case "EN":
                    simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
                    break;
                }
            }
            break;
        case TIMESTAMP_WITH_SECONDS:
            simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            if (StringUtils.isNotBlank(language)) {
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
        case FORMAT_DATABASE_WITH_HOUR_AND_SECOND:
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }

        return simpleDateFormat;
    }

    /**
     * Adiciona Zero a esquerda da Hr, min, sec
     *
     * @param date
     * @param hourOfDay
     * @param minute
     * @param second
     * @return
     *
     */
    public static String formatterHrMinSec(final int hourOfDay, final int minute, final int second) {
        return adicionaZeroAEsquerda(hourOfDay, minute, second);
    }

    public static String adicionaZeroAEsquerda(final int... num) {
        String str = "";
        for (int i = 0; i < num.length; i++) {
            if (num[i] < 10) {
                str += "0" + num[i];
            } else {
                str += "" + num[i];
            }

            if (i < num.length - 1) {
                str += ":";
            }
        }
        return str;

    }

}
