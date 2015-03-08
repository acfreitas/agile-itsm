package br.com.citframework.integracao.core;

import java.io.Serializable;

import br.com.citframework.util.Assert;

/**
 * Implementação básica de {@link Pageable}
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 01/10/2014
 * @see <a href="http://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/PageRequest.html">org.springframework.data.domain.PageRequest</a>
 */
public class PageRequest implements Pageable, Serializable {

    private static final long serialVersionUID = -5059751141333468480L;

    private final int page;
    private final int size;

    /**
     * Cria uma nova {@link PageRequest}. Páginas são iniciadas de zero, então informando {@code 0} for {@code page} irá retornar a primeira página
     *
     * @param page
     *            baseado em zero (ou seja, inicia em 0) índice
     * @param size
     *            tamanho da página a ser retornado
     */
    public PageRequest(final int page, final int size) {
        Assert.isTrue(page >= 0, "Page index must not be less than zero!");
        Assert.isTrue(size >= 1, "Page size must not be less than one!");

        this.page = page;
        this.size = size;
    }

    @Override
    public Pageable next() {
        return new PageRequest(this.getPageNumber() + 1, this.getPageSize());
    }

    @Override
    public Pageable previous() {
        return this.getPageNumber() == 0 ? this : new PageRequest(this.getPageNumber() - 1, this.getPageSize());
    }

    @Override
    public Pageable first() {
        return new PageRequest(0, this.getPageSize());
    }

    @Override
    public int getPageSize() {
        return size;
    }

    @Override
    public int getPageNumber() {
        return page;
    }

    @Override
    public int getOffset() {
        return page * size;
    }

    @Override
    public boolean hasPrevious() {
        return page > 0;
    }

    @Override
    public Pageable previousOrFirst() {
        return this.hasPrevious() ? this.previous() : this.first();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof PageRequest)) {
            return false;
        }

        final PageRequest that = (PageRequest) obj;

        return page == that.page && size == that.size;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + page;
        result = prime * result + size;
        return result;
    }

    @Override
    public String toString() {
        return String.format("Page request [number: %d, size %d]", this.getPageNumber(), this.getPageSize());
    }

}
