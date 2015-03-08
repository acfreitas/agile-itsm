package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.InformacaoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.integracao.ItemConfiguracaoDao;
import br.com.citframework.service.CrudServiceImpl;

/**
 * @author rosana.godinho
 *
 */
public class InformacaoItemConfiguracaoServiceEjb extends CrudServiceImpl implements InformacaoItemConfiguracaoService {

    private ItemConfiguracaoDao dao;

    @Override
    protected ItemConfiguracaoDao getDao() {
        if (dao == null) {
            dao = new ItemConfiguracaoDao();
        }
        return dao;
    }

    @Override
    public InformacaoItemConfiguracaoDTO listByInformacao(final ItemConfiguracaoDTO itemConfiguracao) throws Exception {
        return this.getDao().listByInformacao(itemConfiguracao);
    }

}
