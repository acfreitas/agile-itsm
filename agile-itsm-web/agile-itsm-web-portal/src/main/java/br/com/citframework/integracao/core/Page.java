package br.com.citframework.integracao.core;

import java.util.List;

/**
 * Representa��o de p�gina, que � uma sublista de uma lista de objetos.
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 01/10/2014
 * @see <a href="http://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Page.html">org.springframework.data.domain.Page</a>
 */
public interface Page<T> extends Iterable<T> {

    /**
     * Retorna o n�mero total de p�ginas
     *
     * @return {@code int}: n�mero total de p�ginas
     */
    int getTotalPages();

    /**
     * Retorna a quantidade total de elementos
     *
     * @return {@code long}: quantidade total de elementos
     */
    long getTotalElements();

    /**
     * Retorna o n�mero da {@link Page} atual. � sempre n�o negativa
     *
     * @return {@code int}: o n�mero da {@link Page} atual
     */
    int getNumber();

    /**
     * Retorna o tamanho da {@link Page}
     *
     * @return the size of the {@link Page}
     */
    int getSize();

    /**
     * Retorna o n�mero de elementos da {@link Page}
     *
     * @return {@code int} n�mero de elementos da {@link Page}
     */
    int getNumberOfElements();

    /**
     * Recupera, como uma {@link List}, o conte�do de uma p�gina
     *
     * @return {@link List}: conte�do da {@link Page}
     */
    List<T> getContent();

    /**
     * Verifica se a {@link Page} possui conte�do
     *
     * @return {@code true}, caso possua conte�do {@link Page}. {@code false}, caso contr�rio
     */
    boolean hasContent();

    /**
     * Retorna se a {@link Page} � a primeira
     *
     * @return {@code true}, caso seja a primeira {@link Page}. {@code false}, caso contr�rio
     */
    boolean isFirst();

    /**
     * Retorna se a {@link Page} � a �ltima
     *
     * @return {@code true}, caso seja a �ltima {@link Page}. {@code false}, caso contr�rio
     */
    boolean isLast();

    /**
     * Retorna exist�ncia de pr�xima p�gina
     *
     * @return {@code true}, caso tenha pr�xima {@link Page}. {@code false}, caso contr�rio
     */
    boolean hasNext();

    /**
     * Retorna exist�ncia de p�gina anterior
     *
     * @return {@code true}, caso tenha uma {@link Page} anterior. {@code false}, caso contr�rio
     */
    boolean hasPrevious();

    /**
     * Recupera a {@link Pageable} para solicita a pr�xima {@link Page}. Pode ser {@code null} caso a {@link Page} atual seja a �ltima. Usu�rios devem checar, chamando
     * {@link #hasNext()} antes de chamar este m�todo para ter certeza que o valor retornado n�o ser� {@code null}
     *
     * @return {@link Pageable}
     */
    Pageable nextPageable();

    /**
     * Recupera a {@link Pageable} para solicita a {@link Page} anterior. Pode ser {@code null} caso a {@link Page} atual seja a primeira. Usu�rios devem checar, chamando
     * {@link #hasPrevious()} antes de chamar este m�todo para ter certeza que o valor retornado n�o ser� {@code null}
     *
     * @return {@link Pageable}
     */
    Pageable previousPageable();

}
