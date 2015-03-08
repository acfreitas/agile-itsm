package br.com.centralit.bpm.servico;

import java.util.Collection;

import br.com.citframework.service.CrudService;

public interface HistoricoItemTrabalhoService extends CrudService {

    Collection findByIdItemTrabalho(final Integer parm) throws Exception;

    Collection findByIdUsuario(final Integer parm) throws Exception;

}
