package br.com.citframework.menu;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IRenderMenu {

    String render(final Collection colMenus, final String contextName, final HttpServletRequest request, final HttpServletResponse response) throws Exception;

}
