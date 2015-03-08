package br.com.centralit.citcorpore.metainfo.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudService;

public interface CamposObjetoNegocioService extends CrudService {

    Collection findByIdObjetoNegocio(final Integer idObjetoNegocioParm) throws Exception;

    Collection findByIdObjetoNegocioAndNomeDB(final Integer idObjetoNegocioParm, final String nomeDBParm) throws Exception;

    void updateComplexidade(final Integer idCampoObjNegocio1, final Integer idCampoObjNegocio2) throws Exception;

    void updateFluxoServico(final Integer idCampoObjNegocio1, final Integer idCampoObjNegocio2, final Integer idCampoObjNegocio3) throws Exception;

}
