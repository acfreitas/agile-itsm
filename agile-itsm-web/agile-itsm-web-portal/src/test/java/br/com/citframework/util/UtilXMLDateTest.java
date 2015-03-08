package br.com.citframework.util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Assert;
import org.junit.Test;

/**
 * Classe de testes para validação do comportamento de {@link UtilXMLDate}
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 01/10/2014
 *
 */
public final class UtilXMLDateTest {

    private final int YEAR = 2014;
    private final int MONTH = Calendar.JANUARY;
    private final int DAY_OF_MONTH = 1;
    private final int HOUR_OF_DAY = 13;
    private final int MINUTE = 27;
    private final int SECOND = 43;
    private final int MILLISECOND = 483;

    private Date date;

    private Date getDate() {
        if (date == null) {
            final Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, YEAR);
            cal.set(Calendar.MONTH, MONTH);
            cal.set(Calendar.DAY_OF_MONTH, DAY_OF_MONTH);
            cal.set(Calendar.HOUR_OF_DAY, HOUR_OF_DAY);
            cal.set(Calendar.MINUTE, MINUTE);
            cal.set(Calendar.SECOND, SECOND);
            cal.set(Calendar.MILLISECOND, MILLISECOND);

            date = cal.getTime();
        }
        return date;
    }

    private XMLGregorianCalendar getXMLGregorianCalendar() {
        return UtilXMLDate.toXMLGregorianCalendar(this.getDate());
    }

    @Test
    public void testToDate() {
        final XMLGregorianCalendar cal = this.getXMLGregorianCalendar();
        final Date newDate = UtilXMLDate.toDate(cal);
        Assert.assertEquals(date, newDate);
    }

    @Test
    public void testToXMLGregorianCalendar() {
        final XMLGregorianCalendar cal = UtilXMLDate.toXMLGregorianCalendar(this.getDate());

        final int[] result = {cal.getYear(), cal.getMonth(), cal.getDay(), cal.getHour(), cal.getMinute(), cal.getSecond(), cal.getMillisecond()};
        final int[] expected = {YEAR, MONTH + 1, DAY_OF_MONTH, HOUR_OF_DAY, MINUTE, SECOND, MILLISECOND};

        Assert.assertArrayEquals(result, expected);
    }

    @Test
    public void testToTimeStamp() {
        final XMLGregorianCalendar cal = this.getXMLGregorianCalendar();
        final Timestamp dateTime = UtilXMLDate.toTimeStamp(cal);
        Assert.assertTrue(dateTime.compareTo(this.getDate()) == 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testToXMLGregorianCalendarWhenDateIsNull() {
        UtilXMLDate.toXMLGregorianCalendar(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testToDateWhenXMLGregorianCalendarIsNull() {
        UtilXMLDate.toDate(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTimeStampWhenXMLGregorianCalendarIsNull() {
        UtilXMLDate.toTimeStamp(null);
    }

}
