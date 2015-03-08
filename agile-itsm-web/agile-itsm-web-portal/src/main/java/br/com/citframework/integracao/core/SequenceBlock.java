package br.com.citframework.integracao.core;

import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

/**
 * {@link SequenceBlock} mantém um pedaço de número sequenciais, um bloco únic
 */
public class SequenceBlock {

    private static final Logger LOGGER = Logger.getLogger(SequenceBlock.class.getName());

    private final AtomicLong lastReturnedValue;

    private final long endOfBlock;

    private final long startOfBlock;

    /**
     * Cria um novo {@link SequenceBlock} informando valores iniciais e finais do bloco
     *
     * @param startOfBlock
     *            valor, positivo, que é o primeiro valor a ser retornado por {@link #getNextId()}
     * @param endOfBlock
     *            (exclusive) valor, positivo, ( (endOfBlock -1) que é o último valor a ser retornado por {@link #getNextId()}
     */
    public SequenceBlock(final long startOfBlock, final long endOfBlock) {
        if (startOfBlock < 0 || endOfBlock <= startOfBlock) {
            throw new IllegalArgumentException("Unable to create sequence block with negative or conflicting arguments (initialValue: " + startOfBlock + ", maxValue: "
                    + endOfBlock + ").");
        }
        LOGGER.fine(this.getClass().getSimpleName() + " created (" + startOfBlock + " <= val < " + endOfBlock + " ).");
        this.startOfBlock = startOfBlock;
        lastReturnedValue = new AtomicLong(startOfBlock - 1);
        this.endOfBlock = endOfBlock;
    }

    /**
     * {@code THREAD-SAFE} - Recupera o próximo valor do bloco<br>
     *
     * @return {@link Long} próximo valor do bloco. {@code -1}, caso o bloco esteja esgotado
     */
    public long getNextId() {
        LOGGER.fine(this.getClass().getSimpleName() + ".getNextId() called.");
        long res = lastReturnedValue.addAndGet(1);
        if (res >= endOfBlock) {
            res = -1;
        }
        return res;
    }

    /**
     * Verifica se o bloco está esgotado
     *
     * @return {@code true}, se o bloco está esgotado. {@code false}, caso contrário
     */
    public boolean isExhausted() {
        return lastReturnedValue.get() >= endOfBlock - 1;
    }

    @Override
    public String toString() {
        final long lastValue = lastReturnedValue.get();
        long remaining = lastValue < startOfBlock ? endOfBlock - startOfBlock : endOfBlock - lastValue - 1;
        if (remaining < 0) {
            remaining = 0;
        }
        String debugLast = lastValue < startOfBlock ? "N/A" : "" + lastValue;
        if (remaining == 0) {
            debugLast = "" + (endOfBlock - 1);
        }
        return SequenceBlock.class.getSimpleName() + "(startOfBlock: " + startOfBlock + ", endOfBlock: " + endOfBlock + ", lastReturnedValue: " + debugLast + ", remaining: "
                + remaining + ")";
    }

}
