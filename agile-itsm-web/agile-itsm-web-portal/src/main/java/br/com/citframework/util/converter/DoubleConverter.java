/*
 * Created on 12/01/2006
 *

 * Window - Preferences - Java - Code Style - Code Templates
 */
package br.com.citframework.util.converter;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;

/**
 * @author ney
 *
 *
 */
public class DoubleConverter implements Converter {

    /*
     * (non-Javadoc)
     * @see org.apache.commons.beanutils.Converter#convert(java.lang.Class, java.lang.Object)
     */
    @Override
    public Object convert(final Class classe, final Object value) throws ConversionException {
        return ConverterUtils.strFormatToDouble(value.toString());
    }

}
