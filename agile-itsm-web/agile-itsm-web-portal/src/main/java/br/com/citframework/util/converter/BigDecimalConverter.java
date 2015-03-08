package br.com.citframework.util.converter;

import org.apache.commons.beanutils.Converter;

public class BigDecimalConverter implements Converter {

    @Override
    public Object convert(final Class classe, final Object value) {
        if (value == null) {
            return null;
        }
        try {
            return ConverterUtils.strFormatToBigDecimal(value.toString());
        } catch (final Exception e) {
            System.out.println("Valor Bigdecimal :" + value);
            e.printStackTrace();
            return null;
        }

    }

}
