package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.centralit.citcorpore.bean.MotivoNegacaoCheckinDTO;
import br.com.centralit.citcorpore.integracao.MotivoNegacaoCheckinDao;
import br.com.citframework.service.CrudServiceImpl;

public class MotivoNegacaoCheckinServiceEjb extends CrudServiceImpl implements MotivoNegacaoCheckinService {

    private static final Logger LOGGER = Logger.getLogger(MotivoNegacaoCheckinServiceEjb.class.getName());

    private MotivoNegacaoCheckinDao dao;

    @Override
    protected MotivoNegacaoCheckinDao getDao() {
        if (dao == null) {
            dao = new MotivoNegacaoCheckinDao();
        }
        return dao;
    }

    @Override
    public boolean hasWithSameName(final MotivoNegacaoCheckinDTO motivo) {
        try {
            return this.getDao().hasWithSameName(motivo);
        } catch (final Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }
        return false;
    }

    @Override
    public Collection<MotivoNegacaoCheckinDTO> listAllActiveDeniedReasons() throws Exception {
        return this.getDao().listAllActiveDeniedReasons();
    }

}
