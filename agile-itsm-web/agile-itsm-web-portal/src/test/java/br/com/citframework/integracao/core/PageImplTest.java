package br.com.citframework.integracao.core;

import static br.com.centralit.UnitTestUtils.assertEqualsAndHashcode;
import static br.com.centralit.UnitTestUtils.assertNotEqualsAndHashcode;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

/**
 * Classe de testes para validação do comportamento {@link PageImpl}
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 01/10/2014
 *
 */
public final class PageImplTest {

    @Test
    public void assertEqualsForSimpleSetup() throws Exception {
        final PageImpl<String> page = new PageImpl<>(Arrays.asList("Foo"));
        assertEqualsAndHashcode(page, page);
        assertEqualsAndHashcode(page, new PageImpl<>(Arrays.asList("Foo")));
    }

    @Test
    public void assertEqualsForComplexSetup() throws Exception {
        final Pageable pageable = new PageRequest(0, 10);
        final List<String> content = Arrays.asList("Foo");
        final PageImpl<String> page = new PageImpl<>(content, pageable, 100);
        assertEqualsAndHashcode(page, page);
        assertEqualsAndHashcode(page, new PageImpl<>(content, pageable, 100));
        assertNotEqualsAndHashcode(page, new PageImpl<>(content, pageable, 90));
        assertNotEqualsAndHashcode(page, new PageImpl<>(content, new PageRequest(1, 10), 100));
        assertNotEqualsAndHashcode(page, new PageImpl<>(content, new PageRequest(0, 15), 100));
    }

    @Test(expected = IllegalArgumentException.class)
    public void preventsNullContentForSimpleSetup() throws Exception {
        new PageImpl<>(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void preventsNullContentForAdvancedSetup() throws Exception {
        new PageImpl<>(null, null, 0);
    }

    @Test
    public void returnsNextPageable() {
        final Page<Object> page = new PageImpl<>(Arrays.asList(new Object()), new PageRequest(0, 1), 10);
        assertThat(page.isFirst(), is(true));
        assertThat(page.hasPrevious(), is(false));
        assertThat(page.previousPageable(), is(nullValue()));
        assertThat(page.isLast(), is(false));
        assertThat(page.hasNext(), is(true));
        assertThat(page.nextPageable(), is((Pageable) new PageRequest(1, 1)));
    }

    @Test
    public void returnsPreviousPageable() {
        final Page<Object> page = new PageImpl<>(Arrays.asList(new Object()), new PageRequest(1, 1), 2);
        assertThat(page.isFirst(), is(false));
        assertThat(page.hasPrevious(), is(true));
        assertThat(page.previousPageable(), is((Pageable) new PageRequest(0, 1)));
        assertThat(page.isLast(), is(true));
        assertThat(page.hasNext(), is(false));
        assertThat(page.nextPageable(), is(nullValue()));
    }

    @Test
    public void createsPageForEmptyContentCorrectly() {
        final List<String> list = Collections.emptyList();
        final Page<String> page = new PageImpl<>(list);
        assertThat(page.getContent(), is(list));
        assertThat(page.getNumber(), is(0));
        assertThat(page.getNumberOfElements(), is(0));
        assertThat(page.getSize(), is(0));
        assertThat(page.getTotalElements(), is(0L));
        assertThat(page.getTotalPages(), is(1));
        assertThat(page.hasNext(), is(false));
        assertThat(page.hasPrevious(), is(false));
        assertThat(page.isFirst(), is(true));
        assertThat(page.isLast(), is(true));
        assertThat(page.hasContent(), is(false));
    }

    @Test
    public void returnsCorrectTotalPages() {
        final Page<String> page = new PageImpl<>(Arrays.asList("a"));
        assertThat(page.getTotalPages(), is(1));
        assertThat(page.hasNext(), is(false));
        assertThat(page.hasPrevious(), is(false));
    }

}
