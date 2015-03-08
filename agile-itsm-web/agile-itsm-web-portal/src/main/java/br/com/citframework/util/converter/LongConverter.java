package br.com.citframework.util.converter;

import org.apache.commons.beanutils.Converter;

public class LongConverter implements Converter {

    @Override
    public Object convert(final Class classe, final Object value) {

        if (value == null) {
            return null;
        }
        try {
            return ConverterUtils.strFormatToLong(value.toString());
        } catch (final Exception e) {
            System.out.println("Valor Long :" + value);
            e.printStackTrace();
            return null;
        }
    }

}
