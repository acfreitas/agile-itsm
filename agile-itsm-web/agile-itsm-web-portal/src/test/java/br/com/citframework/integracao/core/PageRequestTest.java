package br.com.citframework.integracao.core;

import static br.com.centralit.UnitTestUtils.assertEqualsAndHashcode;
import static br.com.centralit.UnitTestUtils.assertNotEqualsAndHashcode;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * Classe de testes para validação do comportamento {@link PageRequest}
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 01/10/2014
 *
 */
public final class PageRequestTest {

    private PageRequest newPageRequest(final int page, final int size) {
        return new PageRequest(page, size);
    }

    @Test(expected = IllegalArgumentException.class)
    public void preventsNegativePage() {
        this.newPageRequest(-1, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void preventsNegativeSize() {
        this.newPageRequest(0, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void preventsPageSizeLessThanOne() {
        this.newPageRequest(0, 0);
    }

    @Test
    public void navigatesPageablesCorrectly() {
        final Pageable request = this.newPageRequest(1, 10);
        assertThat(request.hasPrevious(), is(true));
        assertThat(request.next(), is((Pageable) this.newPageRequest(2, 10)));

        final Pageable first = request.previousOrFirst();
        assertThat(first.hasPrevious(), is(false));
        assertThat(first, is((Pageable) this.newPageRequest(0, 10)));
        assertThat(first, is(request.first()));
        assertThat(first.previousOrFirst(), is(first));
    }

    @Test
    public void equalsHonoursPageAndSize() {
        final PageRequest request = this.newPageRequest(0, 10);
        assertEqualsAndHashcode(request, request);
        assertEqualsAndHashcode(request, this.newPageRequest(0, 10));
        assertNotEqualsAndHashcode(request, this.newPageRequest(1, 10));
        assertNotEqualsAndHashcode(request, this.newPageRequest(0, 11));
    }

}
