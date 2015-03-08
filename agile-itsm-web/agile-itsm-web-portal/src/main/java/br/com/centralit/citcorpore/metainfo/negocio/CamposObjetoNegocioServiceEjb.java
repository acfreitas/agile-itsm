package br.com.centralit.citcorpore.metainfo.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.metainfo.integracao.CamposObjetoNegocioDao;
import br.com.citframework.service.CrudServiceImpl;

public class CamposObjetoNegocioServiceEjb extends CrudServiceImpl implements CamposObjetoNegocioService {

    private CamposObjetoNegocioDao dao;

    @Override
    protected CamposObjetoNegocioDao getDao() {
        if (dao == null) {
            dao = new CamposObjetoNegocioDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdObjetoNegocio(final Integer idObjetoNegocioParm) throws Exception {
        return this.getDao().findByIdObjetoNegocio(idObjetoNegocioParm);
    }

    @Override
    public Collection findByIdObjetoNegocioAndNomeDB(final Integer idObjetoNegocioParm, final String nomeDBParm) throws Exception {
        return this.getDao().findByIdObjetoNegocioAndNomeDB(idObjetoNegocioParm, nomeDBParm);
    }

    @Override
    public void updateComplexidade(final Integer idCampoObjNegocio1, final Integer idCampoObjNegocio2) throws Exception {
        this.getDao().updateComplexidade(idCampoObjNegocio1, idCampoObjNegocio2);
    }

    @Override
    public void updateFluxoServico(final Integer idCampoObjNegocio1, final Integer idCampoObjNegocio2, final Integer idCampoObjNegocio3) throws Exception {
        this.getDao().updateFluxoServico(idCampoObjNegocio1, idCampoObjNegocio2, idCampoObjNegocio3);
    }

}
