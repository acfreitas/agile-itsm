package br.com.citframework.util;

import java.sql.Timestamp;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Utilitário para trabalhar com datas gregorianas no padrão XML, conforme <a href="http://www.w3.org/TR/xmlschema-2/#isoformats">W3C XML Schema 1.0 Part 2, Appendix D,
 * <i>ISO 8601 Date and Time Formats</i></a>
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 01/10/2014
 *
 */
public final class UtilXMLDate {

    private static final Logger LOGGER = Logger.getLogger(UtilXMLDate.class.getName());

    private UtilXMLDate() {}

    /**
     * Converte uma data para o formato de data XML
     *
     * @param date
     *            data a ser convertida
     * @return {@link XMLGregorianCalendar}
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 01/10/2014
     */
    public static XMLGregorianCalendar toXMLGregorianCalendar(final Date date) {
        Assert.notNull(date, "Date must not be null.");

        final GregorianCalendar gCalendar = new GregorianCalendar();
        gCalendar.setTime(date);
        XMLGregorianCalendar xmlCalendar = null;
        try {
            xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gCalendar);
        } catch (final DatatypeConfigurationException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return xmlCalendar;
    }

    /**
     * Converte uma data {@link XMLGregorianCalendar} para {@link Date}
     *
     * @param calendar
     *            data XML a ser convertida
     * @return {@link Date}
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 01/10/2014
     */
    public static Date toDate(final XMLGregorianCalendar calendar) {
        Assert.notNull(calendar, "XML Calendar must not be null.");
        return calendar.toGregorianCalendar().getTime();
    }

    /**
     * Converte uma data {@link XMLGregorianCalendar} para {@link Timestamp}
     *
     * @param calendar
     *            data XML a ser convertida
     * @return {@link Timestamp}
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 06/10/2014
     */
    public static Timestamp toTimeStamp(final XMLGregorianCalendar calendar) {
        final Date date = toDate(calendar);
        return new Timestamp(date.getTime());
    }

}
