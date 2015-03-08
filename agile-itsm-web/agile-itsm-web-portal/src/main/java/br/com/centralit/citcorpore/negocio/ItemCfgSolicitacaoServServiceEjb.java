package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.ItemCfgSolicitacaoServDAO;
import br.com.citframework.service.CrudServiceImpl;

@SuppressWarnings("rawtypes")
public class ItemCfgSolicitacaoServServiceEjb extends CrudServiceImpl implements ItemCfgSolicitacaoServService {

    private ItemCfgSolicitacaoServDAO dao;

    @Override
    protected ItemCfgSolicitacaoServDAO getDao() {
        if (dao == null) {
            dao = new ItemCfgSolicitacaoServDAO();
        }
        return dao;
    }

    @Override
    public Collection findByIdItemConfiguracao(final Integer parm) throws Exception {
        return this.getDao().findByIdItemConfiguracao(parm);
    }

    @Override
    public void deleteByIdItemConfiguracao(final Integer parm) throws Exception {
        this.getDao().deleteByIdItemConfiguracao(parm);

    }

    @Override
    public Collection findByIdSolicitacaoServico(final Integer parm) throws Exception {
        return this.getDao().findByIdSolicitacaoServico(parm);

    }

}
