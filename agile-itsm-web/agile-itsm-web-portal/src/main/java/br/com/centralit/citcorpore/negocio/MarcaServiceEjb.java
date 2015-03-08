package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.MarcaDTO;
import br.com.centralit.citcorpore.integracao.MarcaDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

@SuppressWarnings("rawtypes")
public class MarcaServiceEjb extends CrudServiceImpl implements MarcaService {

    private MarcaDao dao;

    @Override
    protected MarcaDao getDao() {
        if (dao == null) {
            dao = new MarcaDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdFabricante(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdFabricante(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdFabricante(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdFabricante(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean consultarMarcas(final MarcaDTO marca) throws Exception {
        return this.getDao().consultarMarcas(marca);
    }

}
