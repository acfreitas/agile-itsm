package br.com.centralit.citcorpore.ajaxForms;

import lombok.Getter;
import br.com.centralit.citajax.html.AjaxFormAction;

import com.google.gson.Gson;

/**
 * Abstract {@link AjaxFormAction} for autocompletes
 *
 * @author Bruno César - <a href="mailto:bruno@brunocesar.com">bruno@brunocesar.com</a>
 * @since 09/03/2015
 */
public abstract class AbstractAutoComplete extends AjaxFormAction {

    @Getter
    private static final Gson GSON = new Gson();

}
