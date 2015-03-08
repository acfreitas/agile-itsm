package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.TempoAcordoNivelServicoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class TempoAcordoNivelServicoServiceEjb extends CrudServiceImpl implements TempoAcordoNivelServicoService {

    private TempoAcordoNivelServicoDao dao;

    @Override
    protected TempoAcordoNivelServicoDao getDao() {
        if (dao == null) {
            dao = new TempoAcordoNivelServicoDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdAcordoAndIdPrioridade(final Integer idAcordoNivelServico, final Integer idPrioridade) throws Exception {
        try {
            return this.getDao().findByIdAcordoAndIdPrioridade(idAcordoNivelServico, idPrioridade);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdAcordoAndFaseAndIdPrioridade(final Integer idAcordoNivelServico, final Integer idFase, final Integer idPrioridade) throws Exception {
        try {
            return this.getDao().findByIdAcordoAndFaseAndIdPrioridade(idAcordoNivelServico, idFase, idPrioridade);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
