package br.com.centralit.citcorpore.negocio;

import java.util.List;

import br.com.centralit.citcorpore.bean.HistoricoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.integracao.HistoricoItemConfiguracaoDAO;
import br.com.citframework.dto.IDto;
import br.com.citframework.service.CrudServiceImpl;

public class HistoricoItemConfiguracaoServiceEjb extends CrudServiceImpl implements HistoricoItemConfiguracaoService {

    private HistoricoItemConfiguracaoDAO dao;

    @Override
    protected HistoricoItemConfiguracaoDAO getDao() {
        if (dao == null) {
            dao = new HistoricoItemConfiguracaoDAO();
        }
        return dao;
    }

    @Override
    public List<HistoricoItemConfiguracaoDTO> listHistoricoItemByIditemconfiguracao(final Integer idItemconfiguracao) throws Exception {
        return this.getDao().listHistoricoItemByIditemconfiguracao(idItemconfiguracao);
    }

    @Override
    public List<HistoricoItemConfiguracaoDTO> listHistoricoItemCfValorByIdHistoricoIC(final Integer idHistoricoIC) throws Exception {
        return this.getDao().listHistoricoItemCfValorByIdHistoricoIC(idHistoricoIC);
    }

    @Override
    public void updateNotNull(final IDto obj) throws Exception {
        this.getDao().updateNotNull(obj);
    }

    @Override
    public HistoricoItemConfiguracaoDTO maxIdHistorico(final ItemConfiguracaoDTO i) throws Exception {
        return this.getDao().maxIdHistorico(i);
    }

}
