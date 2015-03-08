package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.AprovacaoPropostaDTO;
import br.com.centralit.citcorpore.integracao.AprovacaoPropostaDao;
import br.com.citframework.service.CrudServiceImpl;

public class AprovacaoPropostaServiceEjb extends CrudServiceImpl implements AprovacaoPropostaService {

    private AprovacaoPropostaDao dao;

    @Override
    protected AprovacaoPropostaDao getDao() {
        if (dao == null) {
            dao = new AprovacaoPropostaDao();
        }
        return dao;
    }

    @Override
    public Collection<AprovacaoPropostaDTO> listaAprovacaoPropostaPorIdRequisicaoMudanca(final Integer idRequisicaoMudanca, final Integer idGrupo, final Integer idEmpregado)
            throws Exception {
        return this.getDao().listaAprovacaoPropostaPorIdRequisicaoMudanca(idRequisicaoMudanca, idGrupo, idEmpregado);
    }

    @Override
    public Integer quantidadeAprovacaoPropostaPorVotoAprovada(final AprovacaoPropostaDTO aprovacao, final Integer idGrupo) throws Exception {
        return this.getDao().quantidadeAprovacaoPropostaPorVotoAprovada(aprovacao, idGrupo);
    }

    @Override
    public Integer quantidadeAprovacaoPropostaPorVotoRejeitada(final AprovacaoPropostaDTO aprovacao, final Integer idGrupo) throws Exception {
        return this.getDao().quantidadeAprovacaoPropostaPorVotoRejeitada(aprovacao, idGrupo);
    }

    @Override
    public Boolean validacaoAprovacaoProposta(final Integer idRequisicaoMudanca) throws Exception {
        return this.getDao().validacaoAprovacaoProposta(idRequisicaoMudanca);
    }

    @Override
    public Integer quantidadeAprovacaoProposta(final AprovacaoPropostaDTO aprovacao, final Integer idGrupo) throws Exception {
        return this.getDao().quantidadeAprovacaoProposta(aprovacao, idGrupo);
    }

}
