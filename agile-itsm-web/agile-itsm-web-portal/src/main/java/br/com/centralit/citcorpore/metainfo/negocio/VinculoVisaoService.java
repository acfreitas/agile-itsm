package br.com.centralit.citcorpore.metainfo.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudService;

public interface VinculoVisaoService extends CrudService {

    Collection findByIdGrupoVisaoPai(final Integer parm) throws Exception;

    void deleteByIdGrupoVisaoPai(final Integer parm) throws Exception;

    Collection findByIdCamposObjetoNegocioPai(final Integer parm) throws Exception;

    void deleteByIdCamposObjetoNegocioPai(final Integer parm) throws Exception;

    Collection findByIdGrupoVisaoFilho(final Integer parm) throws Exception;

    void deleteByIdGrupoVisaoFilho(final Integer parm) throws Exception;

    Collection findByIdCamposObjetoNegocioFilho(final Integer parm) throws Exception;

    void deleteByIdCamposObjetoNegocioFilho(final Integer parm) throws Exception;

    Collection findByIdCamposObjetoNegocioPaiNN(final Integer parm) throws Exception;

    void deleteByIdCamposObjetoNegocioPaiNN(final Integer parm) throws Exception;

    Collection findByIdCamposObjetoNegocioFilhoNN(final Integer parm) throws Exception;

    void deleteByIdCamposObjetoNegocioFilhoNN(final Integer parm) throws Exception;

    Collection findByIdVisaoRelacionada(final Integer parm) throws Exception;

}
