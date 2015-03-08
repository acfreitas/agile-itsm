package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.JustificativaRequisicaoMudancaDTO;
import br.com.centralit.citcorpore.integracao.JustificativaRequisicaoMudancaDao;
import br.com.citframework.service.CrudServiceImpl;

public class JustificativaRequisicaoMudancaServiceEjb extends CrudServiceImpl implements JustificativaRequisicaoMudancaService {

    private JustificativaRequisicaoMudancaDao dao;

    @Override
    protected JustificativaRequisicaoMudancaDao getDao() {
        if (dao == null) {
            dao = new JustificativaRequisicaoMudancaDao();
        }
        return dao;
    }

    @Override
    public Collection<JustificativaRequisicaoMudancaDTO> listAtivasParaSuspensao() throws Exception {
        return this.getDao().listAtivasParaSuspensao();
    }

}
