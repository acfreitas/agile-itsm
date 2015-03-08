package br.com.centralit.citcorpore.metainfo.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudService;

public interface GrupoVisaoCamposNegocioService extends CrudService {

    Collection findByIdGrupoVisao(final Integer idGrupoVisao) throws Exception;

    Collection findByIdGrupoVisaoAtivos(final Integer idGrupoVisao) throws Exception;

}
