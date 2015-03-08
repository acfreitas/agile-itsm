package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.DeParaCatalogoServicosBIDTO;
import br.com.centralit.citcorpore.integracao.DeParaCatalogoServicosBIDAO;
import br.com.citframework.service.CrudServiceImpl;

public class DeParaCatalogoServicosBIServiceEjb extends CrudServiceImpl implements DeParaCatalogoServicosBIService {

    private DeParaCatalogoServicosBIDAO dao;

    @Override
    protected DeParaCatalogoServicosBIDAO getDao() {
        if (dao == null) {
            dao = new DeParaCatalogoServicosBIDAO();
        }
        return dao;
    }

    @Override
    public DeParaCatalogoServicosBIDTO findByidServicoDe(final Integer id, final Integer idConexaoBI) {
        return this.getDao().findByidServicoDe(id, idConexaoBI);
    }

    @Override
    public Collection<DeParaCatalogoServicosBIDTO> findByidServicoPara(final Integer id, final Integer idConexaoBI) {
        return this.getDao().findByidServicoPara(id, idConexaoBI);
    }

    @Override
    public Collection<DeParaCatalogoServicosBIDTO> findByidConexao(final Integer idConexaoBI) {
        return this.getDao().findByidConexao(idConexaoBI);
    }

}
