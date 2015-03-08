package br.com.citframework.util.converter;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.SqlTimeConverter;

public class TimeConverter implements Converter {

    @Override
    public Object convert(final Class classe, final Object value) {
        if (value == null || value.toString().length() == 0) {
            return null;
        }
        final SqlTimeConverter time = new SqlTimeConverter();
        if (value.toString().trim().length() == 5) {
            return time.convert(classe, value + ":00");
        }
        return time.convert(classe, value);
    }

}
