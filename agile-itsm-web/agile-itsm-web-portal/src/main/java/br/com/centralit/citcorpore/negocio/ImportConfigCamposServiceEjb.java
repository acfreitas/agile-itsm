package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ImportarDadosDTO;
import br.com.centralit.citcorpore.integracao.ImportConfigCamposDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class ImportConfigCamposServiceEjb extends CrudServiceImpl implements ImportConfigCamposService {

    private ImportConfigCamposDao dao;

    @Override
    protected ImportConfigCamposDao getDao() {
        if (dao == null) {
            dao = new ImportConfigCamposDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdImportConfig(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdImportConfig(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdImportConfig(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdImportConfig(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdImportarDados(final ImportarDadosDTO importarDadosDTO) throws Exception {
        try {
            return this.getDao().findByIdImportarDados(importarDadosDTO.getIdImportarDados());
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
