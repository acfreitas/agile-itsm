package br.com.centralit.citsmart.rest.service;

import br.com.centralit.citsmart.rest.dao.RestTranslationDao;
import br.com.citframework.service.CrudServiceImpl;

public class RestTranslationServiceEjb extends CrudServiceImpl implements RestTranslationService {

    private RestTranslationDao dao;

    @Override
    protected RestTranslationDao getDao() {
        if (dao == null) {
            dao = new RestTranslationDao();
        }
        return dao;
    }

}
