package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.centralit.citcorpore.bean.TipoDemandaServicoDTO;
import br.com.centralit.citcorpore.integracao.TipoDemandaServicoDao;
import br.com.citframework.service.CrudServiceImpl;

public class TipoDemandaServicoServiceEjb extends CrudServiceImpl implements TipoDemandaServicoService {

    private TipoDemandaServicoDao dao;

    @Override
    protected TipoDemandaServicoDao getDao() {
        if (dao == null) {
            dao = new TipoDemandaServicoDao();
        }
        return dao;
    }

    @Override
    public Collection<TipoDemandaServicoDTO> listSolicitacoes() throws Exception {
        return this.getDao().listSolicitacoes();
    }

    /**
     * @see br.com.centralit.citcorpore.negocio.TipoDemandaService#validarExclusaoVinculada(HashMap)
     *
     * @author Ezequiel
     */
    @Override
    public boolean existeCadastroServicoVinculado(final Map mapFields) throws Exception {
        final List resultado = this.getDao().validarExclusaoVinculada(mapFields);

        final TipoDemandaServicoDTO tp = (TipoDemandaServicoDTO) resultado.get(0);

        return tp.getQuantidade() != null && tp.getQuantidade() > 0;
    }

}
