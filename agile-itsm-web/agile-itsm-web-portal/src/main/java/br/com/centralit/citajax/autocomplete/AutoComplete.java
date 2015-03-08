package br.com.centralit.citajax.autocomplete;

import br.com.centralit.citajax.framework.AjaxFacade;

public abstract class AutoComplete extends AjaxFacade {

    public abstract AutoCompleteReturn process(final String strInformada);

}
