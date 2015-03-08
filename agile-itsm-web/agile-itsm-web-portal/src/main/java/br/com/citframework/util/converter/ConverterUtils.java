/*
 * Created on 12/01/2006
 *

 * Window - Preferences - Java - Code Style - Code Templates
 */
package br.com.citframework.util.converter;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.SqlTimeConverter;

import br.com.citframework.util.Reflexao;

/**
 * @author ney
 *
 */
public final class ConverterUtils {

    private ConverterUtils() {}

    private static final BigDecimalConverter BIG_DECIMAL_CONVERTER = new BigDecimalConverter();
    private static final DoubleConverter DOUBLE_CONVERTER = new DoubleConverter();
    private static final IntegerConverter INTEGER_CONVERTER = new IntegerConverter();
    private static final LongConverter LONG_CONVERTER = new LongConverter();
    private static final SqlDateConverter SQL_DATE_CONVERTER = new SqlDateConverter();
    private static final SqlTimeConverter SQL_TIME_CONVERTER = new SqlTimeConverter();
    private static final TimeConverter TIME_CONVERTER = new TimeConverter();

    public static void copyFromStringObject(final Object strObject, final Object dest) throws Exception {
        if (strObject == null || dest == null) {
            return;
        }

        final Map<String, String> map = mapProperties(strObject, dest);
        ConvertUtils.register(BIG_DECIMAL_CONVERTER, BigDecimal.class);
        ConvertUtils.register(DOUBLE_CONVERTER, Double.class);
        ConvertUtils.register(INTEGER_CONVERTER, Integer.class);
        ConvertUtils.register(LONG_CONVERTER, Long.class);
        ConvertUtils.register(SQL_DATE_CONVERTER, Date.class);
        ConvertUtils.register(SQL_TIME_CONVERTER, Time.class);
        ConvertUtils.register(TIME_CONVERTER, Time.class);

        BeanUtils.populate(dest, map);
    }

    public static void copyToStringObject(final Object strObject, final Object src) throws Exception {
        if (strObject == null || src == null) {
            return;
        }

        final List lista = Reflexao.getCommonPropertyNames(strObject, src);
        final Iterator it = lista.iterator();

        while (it.hasNext()) {
            final String atribName = it.next().toString();
            final Object obj = Reflexao.getPropertyValue(src, atribName);
            if (obj != null) {
                Reflexao.setPropertyValue(strObject, atribName, obj.toString());
            }
        }

    }

    private static Map<String, String> mapProperties(final Object strObject, final Object dest) throws Exception {
        final List lista = Reflexao.getCommonPropertyNames(strObject, dest);
        final Iterator it = lista.iterator();
        final Map<String, String> map = new HashMap<>();
        while (it.hasNext()) {
            final String atribName = it.next().toString();
            final Object obj = Reflexao.getPropertyValue(strObject, atribName);
            if (obj != null) {
                map.put(atribName, obj.toString());
            }
        }
        return map;
    }

    // aplica a mascara com # em String com valores numéricos
    public static String aplicaMascara(String numero, final String mascara) {
        if (possuiMascara(numero)) {
            return numero;
        }
        numero = ajustaValor(numero, mascara, '#');
        String result = "";
        int j = 0;
        for (int i = 0; i < numero.length(); i++) {
            if (mascara.charAt(j) == '#') {
                result += numero.charAt(i);
                j++;

            } else {
                char tmp = mascara.charAt(j);
                while (tmp != '#') {
                    result += mascara.charAt(j);
                    j++;
                    tmp = mascara.charAt(j);
                }
                result += numero.charAt(i);
                j++;
            }
        }
        return result;
    }

    // Retira mascara de String com valores numéricos
    public static String retiraMascara(final String valor) {
        if (valor == null || valor.length() == 0) {
            return valor;
        }
        String resp = "";
        for (int i = 0; i < valor.length(); i++) {
            final char tmp = valor.charAt(i);
            if (tmp == '0' || tmp == '1' || tmp == '2' || tmp == '3' || tmp == '4' || tmp == '5' || tmp == '6' || tmp == '7' || tmp == '8' || tmp == '9') {
                resp += tmp;
            }
        }
        return resp;
    }

    // Verifica se String com valores numericos possui mascara
    public static boolean possuiMascara(final String valor) {
        if (valor == null || valor.length() == 0) {
            return false;
        }

        for (int i = 0; i < valor.length(); i++) {
            final char tmp = valor.charAt(i);
            if (tmp != '0' && tmp != '1' && tmp != '2' && tmp != '3' && tmp != '4' && tmp != '5' && tmp != '6' && tmp != '7' && tmp != '8' && tmp != '9' && tmp != '.'
                    && tmp != ',' && tmp != 'E') {
                return true;
            }
        }
        return false;

    }

    // Transforma uma String Com mascara em double
    public static final Double strFormatToDouble(final String value) {
        System.out.println("###########################Converssor");
        if (value == null || value.toString().length() == 0) {
            return null;
        }
        if (ConverterUtils.possuiMascara(value.toString())) {
            System.out.println("-----------Possui Mascara");
            final String tmp = ConverterUtils.retiraMascara(value.toString());
            System.out.println("-----------Valor sem mascara" + tmp);
            if (tmp == null || tmp.trim().length() == 0) {
                return null;
            }
            final Double result = new Double(tmp);
            System.out.println("-----------Valor convertido" + result);
            return result;

        }
        System.out.println("-----------Não possui mascara");

        if (value.toString().indexOf(",") > -1) {
            StringBuilder str = new StringBuilder(value.toString());

            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) == '.') {
                    str = str.deleteCharAt(i);
                }
            }

            final String tmp = str.toString().replace(',', '.');

            Double result;
            try {
                result = new Double(tmp);
            } catch (final NumberFormatException e) {
                throw new ConversionException("Formato numérico inválido. " + value);
            }

            return result;
        } else {
            return new Double(value.toString());
        }

    }

    // Transforma uma String Com mascara em BigDecimal
    public static final Long strFormatToLong(final String value) {
        System.out.println("###########################Converssor");
        if (value == null || value.toString().length() == 0) {
            return null;
        }
        if (ConverterUtils.possuiMascara(value.toString())) {
            System.out.println("-----------Possui Mascara");
            final String tmp = ConverterUtils.retiraMascara(value.toString());
            System.out.println("-----------Valor sem mascara" + tmp);
            if (tmp == null || tmp.trim().length() == 0) {
                return null;
            }
            final Long result = new Long(tmp);
            System.out.println("-----------Valor convertido" + result);
            return result;

        }
        System.out.println("-----------Não possui mascara");

        if (value.toString().indexOf(",") > -1) {
            StringBuilder str = new StringBuilder(value.toString());

            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) == '.') {
                    str = str.deleteCharAt(i);
                }
            }

            final String tmp = str.toString().replace(',', '.');

            Long result;
            try {
                result = new Long(tmp);
            } catch (final NumberFormatException e) {
                throw new ConversionException("Formato numérico inválido. " + value);
            }

            return result;
        } else {
            return new Long(value.toString());
        }

    }

    // Transforma uma String Com mascara em BigDecimal
    public static final BigDecimal strFormatToBigDecimal(final String value) {
        System.out.println("###########################Converssor");
        if (value == null || value.toString().length() == 0) {
            return null;
        }
        if (ConverterUtils.possuiMascara(value.toString())) {
            System.out.println("-----------Possui Mascara");
            final String tmp = ConverterUtils.retiraMascara(value.toString());
            System.out.println("-----------Valor sem mascara" + tmp);
            if (tmp == null || tmp.trim().length() == 0) {
                return null;
            }
            final BigDecimal result = new BigDecimal(tmp);
            System.out.println("-----------Valor convertido" + result);
            return result;

        }
        System.out.println("-----------Não possui mascara");

        if (value.toString().indexOf(",") > -1) {
            StringBuilder str = new StringBuilder(value.toString());

            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) == '.') {
                    str = str.deleteCharAt(i);
                }
            }

            final String tmp = str.toString().replace(',', '.');

            BigDecimal result;
            try {
                result = new BigDecimal(tmp);
            } catch (final NumberFormatException e) {
                throw new ConversionException("Formato numérico inválido. " + value);
            }

            return result;
        } else {
            return new BigDecimal(value.toString());
        }

    }

    // Retorna a quantidade de marcadores existentes na mascara
    private static int contaMarcador(final String mascara, final char marcador) {

        int result = 0;
        for (int i = 0; i < mascara.length(); i++) {
            if (mascara.charAt(i) == marcador) {
                result++;
            }
        }
        return result;

    }

    // Insere 0 a direita do valor a ser mascarado
    private static String ajustaValor(final String numero, final String mascara, final char marcador) {
        if (contaMarcador(mascara, marcador) > numero.length()) {
            final int sobra = contaMarcador(mascara, marcador) - numero.length();
            String result = numero;
            for (int i = 0; i < sobra; i++) {
                result = "0" + result;
            }
            return result;
        }
        return numero;
    }

}
