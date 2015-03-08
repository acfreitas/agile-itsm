package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.JustificativaRequisicaoLiberacaoDTO;
import br.com.centralit.citcorpore.integracao.JustificativaRequisicaoLiberacaoDao;
import br.com.citframework.service.CrudServiceImpl;

public class JustificativaRequisicaoLiberacaoServiceEjb extends CrudServiceImpl implements JustificativaRequisicaoLiberacaoService {

    private JustificativaRequisicaoLiberacaoDao dao;

    @Override
    protected JustificativaRequisicaoLiberacaoDao getDao() {
        if (dao == null) {
            dao = new JustificativaRequisicaoLiberacaoDao();
        }
        return dao;
    }

    @Override
    public Collection<JustificativaRequisicaoLiberacaoDTO> listAtivasParaSuspensao() throws Exception {
        return this.getDao().listAtivasParaSuspensao();
    }

}
