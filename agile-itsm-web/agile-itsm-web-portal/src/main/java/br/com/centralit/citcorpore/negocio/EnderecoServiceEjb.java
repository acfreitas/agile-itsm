package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.EnderecoDTO;
import br.com.centralit.citcorpore.integracao.EnderecoDao;
import br.com.citframework.service.CrudServiceImpl;

public class EnderecoServiceEjb extends CrudServiceImpl implements EnderecoService {

    private EnderecoDao dao;

    @Override
    protected EnderecoDao getDao() {
        if (dao == null) {
            dao = new EnderecoDao();
        }
        return dao;
    }

    @Override
    public Collection<EnderecoDTO> recuperaEnderecosEntregaProduto() throws Exception {
        return this.getDao().recuperaEnderecosEntregaProduto();
    }

    @Override
    public EnderecoDTO recuperaEnderecoCompleto(final EnderecoDTO endereco) throws Exception {
        return this.getDao().recuperaEnderecoCompleto(endereco);
    }

    @Override
    public EnderecoDTO recuperaEnderecoComUnidade(final Integer idEndereco) throws Exception {
        return this.getDao().recuperaEnderecoComUnidade(idEndereco);
    }

}
