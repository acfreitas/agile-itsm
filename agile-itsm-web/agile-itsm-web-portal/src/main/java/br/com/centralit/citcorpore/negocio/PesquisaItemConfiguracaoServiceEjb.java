package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.PesquisaItemConfiguracaoDTO;
import br.com.centralit.citcorpore.integracao.ItemConfiguracaoDao;
import br.com.citframework.service.CrudServiceImpl;

public class PesquisaItemConfiguracaoServiceEjb extends CrudServiceImpl implements PesquisaItemConfiguracaoService {

    private ItemConfiguracaoDao dao;

    @Override
    protected ItemConfiguracaoDao getDao() {
        if (dao == null) {
            dao = new ItemConfiguracaoDao();
        }
        return dao;
    }

    @Override
    public Collection<ItemConfiguracaoDTO> listByIdItemconfiguracao(final PesquisaItemConfiguracaoDTO pesquisa) throws Exception {
        return this.getDao().listByIdentificacao(pesquisa);
    }

    @Override
    public Collection<ItemConfiguracaoDTO> listByIdItemconfiguracaoNaoDesativados(final PesquisaItemConfiguracaoDTO pesquisa) throws Exception {
        return this.getDao().listByIdentificacaoNaoDesativados(pesquisa);
    }

}
