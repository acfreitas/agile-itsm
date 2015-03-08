/*
 * Created on 12/01/2006
 *

 * Window - Preferences - Java - Code Style - Code Templates
 */
package br.com.citframework.util.converter;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;

/**
 * @author ney
 *
 *
 */
public class SqlDateConverter implements Converter {

    /*
     * (non-Javadoc)
     * @see org.apache.commons.beanutils.Converter#convert(java.lang.Class, java.lang.Object)
     */
    @Override
    public Object convert(final Class classe, final Object value) throws ConversionException {
        if (value == null || value.toString().length() == 0) {
            return null;
        }

        String valor = value.toString();
        if (valor.length() == 7) {
            valor = "01/" + valor;
        }
        final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            return new Date(sdf.parse(valor).getTime());
        } catch (final ParseException e) {
            throw new ConversionException("Formato de data inválida. " + valor);
        }
    }

}
