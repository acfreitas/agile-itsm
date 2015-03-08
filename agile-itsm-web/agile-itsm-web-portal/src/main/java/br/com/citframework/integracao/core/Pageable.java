package br.com.citframework.integracao.core;

/**
 * Interface para informa��o de pagina��o
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 01/10/2014
 * @see <a href="http://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Pageable.html">org.springframework.data.domain.Pageable</a>
 */
public interface Pageable {

    /**
     * Retorna a p�gina a ser retornada
     *
     * @return {@code int}
     */
    int getPageNumber();

    /**
     * Retorna a n�mero de itens da p�gina
     *
     * @return {@code int} n�mero de itens da p�gina
     */
    int getPageSize();

    /**
     * Retorna the offset to be taken according to the underlying page and page size
     *
     * @return {@code int} offset a ser mantido
     */
    int getOffset();

    /**
     * Retorna a {@link Pageable} pedindo a pr�xima {@link Page}
     *
     * @return {@link Pageable}
     */
    Pageable next();

    Pageable previous();

    /**
     * Retorna a {@link Pageable} anterior ou a primeira {@link Pageable} se a atual � a primeira
     *
     * @return {@link Pageable}
     */
    Pageable previousOrFirst();

    /**
     * Retorna {@link Pageable} pedindo a primeira {@link Page}
     *
     * @return {@link Pageable}
     */
    Pageable first();

    /**
     * Returns whether there's a previous {@link Pageable} we can access from the current one. Will return {@literal false} in case the current {@link Pageable} already refers to
     * the first page.
     *
     * @return {@code true}, caso tenha uma {@link Pageable} anterior. {@code false}, caso seja a {@link Pageable} atual
     */
    boolean hasPrevious();

}
