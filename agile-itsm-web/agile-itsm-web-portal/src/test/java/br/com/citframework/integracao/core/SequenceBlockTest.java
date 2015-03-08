package br.com.citframework.integracao.core;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.citframework.util.ReflectionUtils;

/**
 * Classe de testes para validação do comportamento de {@link SequenceBlock}
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 18/08/2014
 *
 */
public final class SequenceBlockTest {

    private SequenceBlock sq = null;

    private final long END_OF_BLOCK = 100;

    @Before
    public void setUp() {
        sq = new SequenceBlock(1, END_OF_BLOCK);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStartBlockMinorZero() {
        new SequenceBlock(-1, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStartBlockEqualsEndBlock() {
        new SequenceBlock(10, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStartBlockMajorEndBlock() {
        new SequenceBlock(10, 9);
    }

    @Test
    public void testLastReturnedValueBeforeFirstGetNextId() {
        final Field lastReturned = ReflectionUtils.findField(SequenceBlock.class, "lastReturnedValue");
        lastReturned.setAccessible(true);

        Assert.assertNotNull(lastReturned);

        final AtomicLong lastReturnedAL = (AtomicLong) ReflectionUtils.getField(lastReturned, sq);
        Assert.assertEquals(lastReturnedAL.get(), new AtomicLong(-0L).get());
        Assert.assertEquals(sq.toString(), "SequenceBlock(startOfBlock: 1, endOfBlock: 100, lastReturnedValue: N/A, remaining: 99)");
    }

    @Test
    public void testLastReturnedValueAfterGetNextId() {
        for (long i = END_OF_BLOCK; i > 90; i--) {
            sq.getNextId();
        }

        final Field lastReturned = ReflectionUtils.findField(SequenceBlock.class, "lastReturnedValue");
        lastReturned.setAccessible(true);

        Assert.assertNotNull(lastReturned);

        final AtomicLong lastReturnedAL = (AtomicLong) ReflectionUtils.getField(lastReturned, sq);
        Assert.assertEquals(lastReturnedAL.get(), new AtomicLong(10L).get());
        Assert.assertEquals(sq.toString(), "SequenceBlock(startOfBlock: 1, endOfBlock: 100, lastReturnedValue: 10, remaining: 89)");
    }

    @Test
    public void testLastReturnedValueBeforeFirstGetNextIdTwo() {
        for (long i = END_OF_BLOCK; i > 10; i--) {
            sq.getNextId();
        }

        final Field lastReturned = ReflectionUtils.findField(SequenceBlock.class, "lastReturnedValue");
        lastReturned.setAccessible(true);

        Assert.assertNotNull(lastReturned);

        final AtomicLong lastReturnedAL = (AtomicLong) ReflectionUtils.getField(lastReturned, sq);
        Assert.assertEquals(lastReturnedAL.get(), new AtomicLong(90L).get());
        Assert.assertEquals(sq.toString(), "SequenceBlock(startOfBlock: 1, endOfBlock: 100, lastReturnedValue: 90, remaining: 9)");
    }

    @Test
    public void testGetNextId() {
        final long nextId = sq.getNextId();
        Assert.assertEquals(nextId, 1);
        Assert.assertFalse(sq.isExhausted());
    }

    @Test
    public void testGetNextIdNegative() {
        for (long i = END_OF_BLOCK; i >= 0; i--) {
            sq.getNextId();
        }
        final long nextId = sq.getNextId();
        Assert.assertEquals(nextId, -1);
        Assert.assertTrue(sq.isExhausted());
    }

    @Test
    public void testIsExhausted() {
        for (long i = END_OF_BLOCK; i >= 0; i--) {
            sq.getNextId();
        }
        Assert.assertTrue(sq.isExhausted());
        Assert.assertEquals(sq.toString(), "SequenceBlock(startOfBlock: 1, endOfBlock: 100, lastReturnedValue: 99, remaining: 0)");
    }

}
