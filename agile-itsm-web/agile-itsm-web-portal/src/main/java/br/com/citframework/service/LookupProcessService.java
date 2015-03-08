package br.com.citframework.service;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;

public interface LookupProcessService extends IService {

    Collection process(final Object obj, final HttpServletRequest request) throws LogicException, ServiceException;

}
