package br.com.citframework.integracao.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Implementação básica de {@link Page}
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 01/10/2014
 * @see <a href="http://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/PageImpl.html">org.springframework.data.domain.PageImpl</a>
 */
public class PageImpl<T> implements Page<T>, Serializable {

    private static final long serialVersionUID = 3177625856508331195L;

    private final long total;
    private final Pageable pageable;
    private final List<T> content = new ArrayList<>();

    /**
     * Cria uma nova {@link PageImpl} com o conteúdo informado. Como resultado, terá uma {@link Page} being identical
     * to the entire {@link List}.
     *
     * @param content
     *            não deve ser {@code null}.
     */
    public PageImpl(final List<T> content) {
        this(content, null, null == content ? 0 : content.size());
    }

    /**
     * Cria uma nova {@link Page} with the given content and the given governing {@link Pageable}.
     *
     * @param content
     *            conteúdo a ser incluído na {@link Page}. Não deve ser {@code null}.
     * @param pageable
     *            {@link Pageable}: pode ser {@code null}.
     */
    public PageImpl(final List<T> content, final Pageable pageable) {
        this(content, pageable, null == content ? 0 : content.size());
    }

    /**
     * Cria uma {@link Page}.
     *
     * @param content
     *            conteúdo desta página, não deve ser {@code null}.
     * @param pageable
     *            informações da paginação, pode ser {@code null}.
     * @param total
     *            o total de itens disponíveis
     */
    public PageImpl(final List<T> content, final Pageable pageable, final long total) {
        if (content == null) {
            throw new IllegalArgumentException("Content must not be null!");
        }
        this.content.addAll(content);
        this.pageable = pageable;
        this.total = total;
    }

    @Override
    public Iterator<T> iterator() {
        return content.iterator();
    }

    @Override
    public int getTotalPages() {
        return this.getSize() == 0 ? 1 : (int) Math.ceil((double) total / (double) this.getSize());
    }

    @Override
    public long getTotalElements() {
        return total;
    }

    @Override
    public int getNumber() {
        return pageable == null ? 0 : pageable.getPageNumber();
    }

    @Override
    public int getSize() {
        return pageable == null ? 0 : pageable.getPageSize();
    }

    @Override
    public int getNumberOfElements() {
        return content.size();
    }

    @Override
    public List<T> getContent() {
        return Collections.unmodifiableList(content);
    }

    @Override
    public boolean hasContent() {
        return !content.isEmpty();
    }

    @Override
    public boolean isFirst() {
        return !this.hasPrevious();
    }

    @Override
    public boolean isLast() {
        return !this.hasNext();
    }

    @Override
    public boolean hasNext() {
        return this.getNumber() + 1 < this.getTotalPages();
    }

    @Override
    public boolean hasPrevious() {
        return this.getNumber() > 0;
    }

    @Override
    public Pageable nextPageable() {
        return this.hasNext() ? pageable.next() : null;
    }

    @Override
    public Pageable previousPageable() {
        if (this.hasPrevious()) {
            return pageable.previousOrFirst();
        }
        return null;
    }

    @Override
    public String toString() {
        String contentType = "UNKNOWN";
        final List<T> content = this.getContent();

        if (content.size() > 0) {
            contentType = content.get(0).getClass().getName();
        }

        return String.format("Page %s of %d containing %s instances", this.getNumber(), this.getTotalPages(), contentType);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof PageImpl<?>)) {
            return false;
        }

        final PageImpl<?> that = (PageImpl<?>) obj;

        final boolean contentEqual = this.content.equals(that.content);
        final boolean pageableEqual = this.pageable == null ? that.pageable == null : this.pageable.equals(that.pageable);

        return this.total == that.total && contentEqual && pageableEqual;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result += 31 * (pageable == null ? 0 : pageable.hashCode());
        result += 31 * content.hashCode();
        result += 31 * (int) (total ^ total >>> 32);
        return result;
    }

}
