package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.RegraEscalonamentoDTO;
import br.com.centralit.citcorpore.integracao.EscalonamentoDAO;
import br.com.citframework.service.CrudServiceImpl;

public class EscalonamentoServiceEjb extends CrudServiceImpl implements EscalonamentoService {

    private EscalonamentoDAO dao;

    @Override
    protected EscalonamentoDAO getDao() {
        if (dao == null) {
            dao = new EscalonamentoDAO();
        }
        return dao;
    }

    @Override
    public Collection findByRegraEscalonamento(final RegraEscalonamentoDTO regraEscalonamentoDTO) {
        try {
            return this.getDao().findByRegraEscalonamento(regraEscalonamentoDTO);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
