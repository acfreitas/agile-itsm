package br.com.citframework.service;

import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.ServiceException;

public interface IService {

    void setUsuario(final Usuario usuario) throws ServiceException;

}
