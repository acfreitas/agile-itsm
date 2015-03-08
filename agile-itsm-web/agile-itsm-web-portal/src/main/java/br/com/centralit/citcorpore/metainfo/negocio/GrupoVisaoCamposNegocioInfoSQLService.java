package br.com.centralit.citcorpore.metainfo.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudService;

public interface GrupoVisaoCamposNegocioInfoSQLService extends CrudService {

    Collection findByIdGrupoVisao(final Integer parm) throws Exception;

    void deleteByIdGrupoVisao(final Integer parm) throws Exception;

    Collection findByIdCamposObjetoNegocio(final Integer parm) throws Exception;

    void deleteByIdCamposObjetoNegocio(final Integer parm) throws Exception;

}
