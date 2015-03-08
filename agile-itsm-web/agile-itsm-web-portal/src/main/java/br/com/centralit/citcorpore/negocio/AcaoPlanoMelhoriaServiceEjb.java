package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.AcaoPlanoMelhoriaDTO;
import br.com.centralit.citcorpore.integracao.AcaoPlanoMelhoriaDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

@SuppressWarnings("rawtypes")
public class AcaoPlanoMelhoriaServiceEjb extends CrudServiceImpl implements AcaoPlanoMelhoriaService {

    private AcaoPlanoMelhoriaDao dao;

    @Override
    protected AcaoPlanoMelhoriaDao getDao() {
        if (dao == null) {
            dao = new AcaoPlanoMelhoriaDao();
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
    public Collection findByIdObjetivoPlanoMelhoria(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdObjetivoPlanoMelhoria(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdObjetivoPlanoMelhoria(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdObjetivoPlanoMelhoria(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection<AcaoPlanoMelhoriaDTO> listAcaoPlanoMelhoria(final AcaoPlanoMelhoriaDTO acaoPlanoMelhoriaDto) throws Exception {
        try {
            return this.getDao().listAcaoPlanoMelhoria(acaoPlanoMelhoriaDto);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
