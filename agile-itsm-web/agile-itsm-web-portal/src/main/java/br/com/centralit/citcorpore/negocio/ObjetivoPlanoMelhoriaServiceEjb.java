package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ObjetivoPlanoMelhoriaDTO;
import br.com.centralit.citcorpore.integracao.ObjetivoPlanoMelhoriaDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

@SuppressWarnings("rawtypes")
public class ObjetivoPlanoMelhoriaServiceEjb extends CrudServiceImpl implements ObjetivoPlanoMelhoriaService {

    private ObjetivoPlanoMelhoriaDao dao;

    @Override
    protected ObjetivoPlanoMelhoriaDao getDao() {
        if (dao == null) {
            dao = new ObjetivoPlanoMelhoriaDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdPlanoMelhoria(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdPlanoMelhoria(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdPlanoMelhoria(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdPlanoMelhoria(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection<ObjetivoPlanoMelhoriaDTO> listObjetivosPlanoMelhoria(final ObjetivoPlanoMelhoriaDTO obj) throws Exception {
        try {
            return this.getDao().listObjetivosPlanoMelhoria(obj);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
