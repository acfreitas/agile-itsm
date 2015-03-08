package br.com.centralit.citcorpore.rh.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudService;

public interface MovimentacaoPessoalService extends CrudService {

    Collection findByIdEmpregado(final Integer parm) throws Exception;

    void deleteByIdEmpregado(final Integer parm) throws Exception;

}
