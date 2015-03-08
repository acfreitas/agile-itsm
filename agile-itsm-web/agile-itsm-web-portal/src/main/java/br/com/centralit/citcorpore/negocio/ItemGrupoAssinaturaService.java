package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

public interface ItemGrupoAssinaturaService extends CrudService {

    Collection findByIdGrupoAssinatura(Integer idGrupoAssinatura) throws ServiceException;

    Collection findByIdAssinatura(Integer idAssinatura) throws ServiceException;

}
