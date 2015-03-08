package br.com.centralit.citcorpore.metainfo.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudService;

public interface GrupoVisaoService extends CrudService {

    Collection findByIdVisao(final Integer idVisao) throws Exception;

    Collection findByIdVisaoAtivos(final Integer idVisao) throws Exception;

}
