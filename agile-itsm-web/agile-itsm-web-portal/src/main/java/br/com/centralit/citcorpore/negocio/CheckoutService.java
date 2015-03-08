package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.CheckoutDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

public interface CheckoutService extends CrudService {

    Integer realizarCheckout(final CheckoutDTO checkout, final UsuarioDTO user) throws ServiceException, PersistenceException;

}
