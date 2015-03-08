package br.com.centralit.citcorpore.rh.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.rh.integracao.DescricaoAtribuicaoResponsabilidadeDao;
import br.com.citframework.service.CrudServiceImpl;

@SuppressWarnings("rawtypes")
public class DescricaoAtribuicaoResponsabilidadeServiceEjb extends CrudServiceImpl implements DescricaoAtribuicaoResponsabilidadeService {

    private DescricaoAtribuicaoResponsabilidadeDao dao;

    @Override
    protected DescricaoAtribuicaoResponsabilidadeDao getDao() {
        if (dao == null) {
            dao = new DescricaoAtribuicaoResponsabilidadeDao();
        }
        return dao;
    }

    @Override
    public Collection listAtivos() throws Exception {
        return this.getDao().listAtivos();
    }

    @Override
    public Collection findByNome(final String nome) throws Exception {
        return this.getDao().findByNome(nome);
    }

}
