package br.com.centralit.citsmart.rest.service;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citsmart.rest.bean.RestSessionDTO;
import br.com.centralit.citsmart.rest.schema.CtLogin;

public interface RestSessionService {

    RestSessionDTO newSession(final HttpServletRequest httpRequest, final CtLogin login) throws Exception;

    RestSessionDTO getSession(final String sessionID);

}
