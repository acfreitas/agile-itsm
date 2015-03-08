package br.com.centralit.citcorpore.negocio;

import java.util.List;

import br.com.centralit.citcorpore.bean.HistoricoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoDTO;
import br.com.centralit.citcorpore.integracao.HistoricoLiberacaoDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.service.CrudServiceImpl;

public class HistoricoLiberacaoServiceEjb extends CrudServiceImpl implements HistoricoLiberacaoService {

    private HistoricoLiberacaoDao dao;

    @Override
    protected HistoricoLiberacaoDao getDao() {
        if (dao == null) {
            dao = new HistoricoLiberacaoDao();
        }
        return dao;
    }

    @Override
    public List<HistoricoLiberacaoDTO> listHistoricoLiberacaoByIdRequisicaoLiberacao(final Integer idRequisicaoLiberacao) throws Exception {
        return this.getDao().listHistoricoLiberacaoByIdRequisicaoLiberacao(idRequisicaoLiberacao);
    }

    @Override
    public void updateNotNull(final IDto obj) throws Exception {
        this.getDao().updateNotNull(obj);
    }

    @Override
    public HistoricoLiberacaoDTO maxIdHistorico(final RequisicaoLiberacaoDTO i) throws Exception {
        return this.getDao().maxIdHistorico(i);
    }

}
