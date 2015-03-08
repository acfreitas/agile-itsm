package br.com.centralit.citcorpore.negocio;

import java.util.List;

import br.com.centralit.citcorpore.bean.HistoricoMudancaDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO;
import br.com.centralit.citcorpore.integracao.HistoricoMudancaDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.service.CrudServiceImpl;

public class HistoricoMudancaServiceEjb extends CrudServiceImpl implements HistoricoMudancaService {

    private HistoricoMudancaDao dao;

    @Override
    protected HistoricoMudancaDao getDao() {
        if (dao == null) {
            dao = new HistoricoMudancaDao();
        }
        return dao;
    }

    @Override
    public void updateNotNull(final IDto obj) throws Exception {
        this.getDao().updateNotNull(obj);
    }

    @Override
    public List<HistoricoMudancaDTO> listHistoricoMudancaByIdRequisicaoMudanca(final Integer idRequisicaoMudanca) throws Exception {
        return this.getDao().listHistoricoMudancaByIdRequisicaoMudanca(idRequisicaoMudanca);
    }

    @Override
    public HistoricoMudancaDTO maxIdHistorico(final RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception {
        return this.getDao().maxIdHistorico(requisicaoMudancaDTO);
    }

}
