package br.com.centralit.citcorpore.metainfo.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudService;

public interface BotaoAcaoVisaoService extends CrudService {

    Collection findByIdVisao(final Integer parm) throws Exception;

    void deleteByIdVisao(final Integer parm) throws Exception;

}
