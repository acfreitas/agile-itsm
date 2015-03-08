package br.com.citframework.util;

import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

/**
 * Classe de testes para validar comportamento de utilitários de
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 28/10/2014
 *
 */
public final class UtilDatasTest {

    private static final Integer DAY_28 = 28;
    private static final Integer DAY_27 = 27;
    private static final Integer YEAR_2014 = 2014;
    private static final Integer DAYS_TO_ADD_SUBTRACT = 30;

    @Test
    public void testAddDaysOnDate() {
        final Date datePlus30 = UtilDatas.addDaysOnDate(this.getDate(), DAYS_TO_ADD_SUBTRACT);

        final Calendar cal = Calendar.getInstance();
        cal.setTime(datePlus30);

        Assert.assertEquals(cal.get(Calendar.DAY_OF_MONTH), DAY_27.intValue());
        Assert.assertEquals(cal.get(Calendar.MONTH), Calendar.NOVEMBER);
        Assert.assertEquals(cal.get(Calendar.YEAR), YEAR_2014.intValue());
    }

    @Test
    public void testSubtractDaysFromDate() {
        final Date dateMinus30 = UtilDatas.subtractDaysFromDate(this.getDate(), DAYS_TO_ADD_SUBTRACT);

        final Calendar cal = Calendar.getInstance();
        cal.setTime(dateMinus30);

        Assert.assertEquals(cal.get(Calendar.DAY_OF_MONTH), DAY_28.intValue());
        Assert.assertEquals(cal.get(Calendar.MONTH), Calendar.SEPTEMBER);
        Assert.assertEquals(cal.get(Calendar.YEAR), YEAR_2014.intValue());
    }

    private Date date;

    private Date getDate() {
        if (date == null) {
            final Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_MONTH, DAY_28);
            cal.set(Calendar.MONTH, Calendar.OCTOBER);
            cal.set(Calendar.YEAR, YEAR_2014);
            date = cal.getTime();
        }
        return date;
    }

}
