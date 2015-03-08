package br.com.centralit.citquestionario.negocio;

import br.com.centralit.citquestionario.integracao.ListagemDao;
import br.com.citframework.service.CrudServiceImpl;

public class ListagemServiceBean extends CrudServiceImpl implements ListagemService {

    private ListagemDao dao;

    @Override
    protected ListagemDao getDao() {
        if (dao == null) {
            dao = new ListagemDao();
        }
        return dao;
    }

}
