package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.JustificativaProblemaDTO;
import br.com.centralit.citcorpore.integracao.JustificativaProblemaDao;
import br.com.citframework.service.CrudServiceImpl;

public class JustificativaProblemaServiceEjb extends CrudServiceImpl implements JustificativaProblemaService {

    private JustificativaProblemaDao dao;

    @Override
    protected JustificativaProblemaDao getDao() {
        if (dao == null) {
            dao = new JustificativaProblemaDao();
        }
        return dao;
    }

    @Override
    public Collection<JustificativaProblemaDTO> listAtivasParaSuspensao() throws Exception {
        return this.getDao().listAtivasParaSuspensao();
    }

}
