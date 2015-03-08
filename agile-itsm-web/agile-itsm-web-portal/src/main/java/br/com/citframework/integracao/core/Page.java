package br.com.citframework.integracao.core;

import java.util.List;

/**
 * Representação de página, que é uma sublista de uma lista de objetos.
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 01/10/2014
 * @see <a href="http://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Page.html">org.springframework.data.domain.Page</a>
 */
public interface Page<T> extends Iterable<T> {

    /**
     * Retorna o número total de páginas
     *
     * @return {@code int}: número total de páginas
     */
    int getTotalPages();

    /**
     * Retorna a quantidade total de elementos
     *
     * @return {@code long}: quantidade total de elementos
     */
    long getTotalElements();

    /**
     * Retorna o número da {@link Page} atual. É sempre não negativa
     *
     * @return {@code int}: o número da {@link Page} atual
     */
    int getNumber();

    /**
     * Retorna o tamanho da {@link Page}
     *
     * @return the size of the {@link Page}
     */
    int getSize();

    /**
     * Retorna o número de elementos da {@link Page}
     *
     * @return {@code int} número de elementos da {@link Page}
     */
    int getNumberOfElements();

    /**
     * Recupera, como uma {@link List}, o conteúdo de uma página
     *
     * @return {@link List}: conteúdo da {@link Page}
     */
    List<T> getContent();

    /**
     * Verifica se a {@link Page} possui conteúdo
     *
     * @return {@code true}, caso possua conteúdo {@link Page}. {@code false}, caso contrário
     */
    boolean hasContent();

    /**
     * Retorna se a {@link Page} é a primeira
     *
     * @return {@code true}, caso seja a primeira {@link Page}. {@code false}, caso contrário
     */
    boolean isFirst();

    /**
     * Retorna se a {@link Page} é a última
     *
     * @return {@code true}, caso seja a última {@link Page}. {@code false}, caso contrário
     */
    boolean isLast();

    /**
     * Retorna existência de próxima página
     *
     * @return {@code true}, caso tenha próxima {@link Page}. {@code false}, caso contrário
     */
    boolean hasNext();

    /**
     * Retorna existência de página anterior
     *
     * @return {@code true}, caso tenha uma {@link Page} anterior. {@code false}, caso contrário
     */
    boolean hasPrevious();

    /**
     * Recupera a {@link Pageable} para solicita a próxima {@link Page}. Pode ser {@code null} caso a {@link Page} atual seja a última. Usuários devem checar, chamando
     * {@link #hasNext()} antes de chamar este método para ter certeza que o valor retornado não será {@code null}
     *
     * @return {@link Pageable}
     */
    Pageable nextPageable();

    /**
     * Recupera a {@link Pageable} para solicita a {@link Page} anterior. Pode ser {@code null} caso a {@link Page} atual seja a primeira. Usuários devem checar, chamando
     * {@link #hasPrevious()} antes de chamar este método para ter certeza que o valor retornado não será {@code null}
     *
     * @return {@link Pageable}
     */
    Pageable previousPageable();

}
