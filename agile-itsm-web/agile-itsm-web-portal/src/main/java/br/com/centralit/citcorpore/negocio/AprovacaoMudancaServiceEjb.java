package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.AprovacaoMudancaDTO;
import br.com.centralit.citcorpore.integracao.AprovacaoMudancaDao;
import br.com.citframework.service.CrudServiceImpl;

public class AprovacaoMudancaServiceEjb extends CrudServiceImpl implements AprovacaoMudancaService {

    private AprovacaoMudancaDao dao;

    @Override
    protected AprovacaoMudancaDao getDao() {
        if (dao == null) {
            dao = new AprovacaoMudancaDao();
        }
        return dao;
    }

    @Override
    public Collection<AprovacaoMudancaDTO> listaAprovacaoMudancaPorIdRequisicaoMudanca(final Integer idRequisicaoMudanca, final Integer idGrupo, final Integer idEmpregado)
            throws Exception {
        return this.getDao().listaAprovacaoMudancaPorIdRequisicaoMudanca(idRequisicaoMudanca, idGrupo, idEmpregado);
    }

    @Override
    public Integer quantidadeAprovacaoMudancaPorVotoAprovada(final AprovacaoMudancaDTO aprovacao, final Integer idGrupo) throws Exception {
        return this.getDao().quantidadeAprovacaoMudancaPorVotoAprovada(aprovacao, idGrupo);
    }

    @Override
    public Integer quantidadeAprovacaoMudancaPorVotoRejeitada(final AprovacaoMudancaDTO aprovacao, final Integer idGrupo) throws Exception {
        return this.getDao().quantidadeAprovacaoMudancaPorVotoRejeitada(aprovacao, idGrupo);
    }

    @Override
    public Boolean validacaoAprovacaoMudanca(final Integer idRequisicaoMudanca) throws Exception {
        return this.getDao().validacaoAprovacaoMudanca(idRequisicaoMudanca);
    }

    @Override
    public Integer quantidadeAprovacaoMudanca(final AprovacaoMudancaDTO aprovacao, final Integer idGrupo) throws Exception {
        return this.getDao().quantidadeAprovacaoMudanca(aprovacao, idGrupo);
    }

}
