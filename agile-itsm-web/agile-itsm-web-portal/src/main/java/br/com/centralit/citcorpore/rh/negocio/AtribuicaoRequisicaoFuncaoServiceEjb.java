package br.com.centralit.citcorpore.rh.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.rh.bean.AtribuicaoRequisicaoFuncaoDTO;
import br.com.centralit.citcorpore.rh.integracao.AtribuicaoRequisicaoFuncaoDao;
import br.com.citframework.service.CrudServiceImpl;

public class AtribuicaoRequisicaoFuncaoServiceEjb extends CrudServiceImpl implements AtribuicaoRequisicaoFuncaoService {

    private AtribuicaoRequisicaoFuncaoDao dao;

    @Override
    protected AtribuicaoRequisicaoFuncaoDao getDao() {
        if (dao == null) {
            dao = new AtribuicaoRequisicaoFuncaoDao();
        }
        return dao;
    }

    @Override
    public boolean consultarAtribuicoesAtivas(final AtribuicaoRequisicaoFuncaoDTO obj) throws Exception {
        return this.getDao().consultarAtribuicoesAtivas(obj);
    }

    @Override
    public Collection<AtribuicaoRequisicaoFuncaoDTO> seAtribuicaoJaCadastrada(final AtribuicaoRequisicaoFuncaoDTO atribuicaoRequisicaoFuncaoDTO) throws Exception {
        return this.getDao().seAtribuicaoJaCadastrada(atribuicaoRequisicaoFuncaoDTO);
    }

    @Override
    public Collection<AtribuicaoRequisicaoFuncaoDTO> listarAtivos() throws Exception {
        return this.getDao().listarAtivos();
    }

}
