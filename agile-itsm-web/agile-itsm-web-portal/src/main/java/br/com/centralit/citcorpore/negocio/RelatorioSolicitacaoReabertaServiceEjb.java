package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;

import br.com.centralit.citcorpore.bean.RelatorioSolicitacaoReabertaDTO;
import br.com.centralit.citcorpore.integracao.RelatorioSolicitacaoReabertaDAO;
import br.com.citframework.service.CrudServiceImpl;

public class RelatorioSolicitacaoReabertaServiceEjb extends CrudServiceImpl implements RelatorioSolicitacaoReabertaService {

    private RelatorioSolicitacaoReabertaDAO dao;

    @Override
    protected RelatorioSolicitacaoReabertaDAO getDao() {
        if (dao == null) {
            dao = new RelatorioSolicitacaoReabertaDAO();
        }
        return dao;
    }

    @Override
    public ArrayList<RelatorioSolicitacaoReabertaDTO> listSolicitacaoReaberta(final RelatorioSolicitacaoReabertaDTO relatorioSolicitacaoReabertaDTO) {
        return this.getDao().listSolicitacaoReaberta(relatorioSolicitacaoReabertaDTO);
    }

}
