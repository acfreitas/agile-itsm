package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudService;

public interface ProjetoService extends CrudService {
    public Collection findByIdCliente(Integer parm) throws Exception;
    public Collection listHierarquia(Integer idCliente, boolean acrescentarInativos) throws Exception;

}
