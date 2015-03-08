package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.AlcadaCentroResultadoDTO;
import br.com.centralit.citcorpore.integracao.AlcadaCentroResultadoDAO;
import br.com.citframework.service.CrudServiceImpl;

public class AlcadaCentroResultadoServiceEjb extends CrudServiceImpl implements AlcadaCentroResultadoService {

    private AlcadaCentroResultadoDAO dao;

    @Override
    protected AlcadaCentroResultadoDAO getDao() {
        if (dao == null) {
            dao = new AlcadaCentroResultadoDAO();
        }
        return dao;
    }

    @Override
    public boolean verificarVinculoCentroResultado(final Integer obj) throws Exception {
        return this.getDao().verificarVinculoCentroResultado(obj);
    }

    @Override
    public Collection<AlcadaCentroResultadoDTO> findByIdCentroResultado(final Integer idCentroResultado) throws Exception {
        return this.getDao().findByIdCentroResultado(idCentroResultado);
    }

}
