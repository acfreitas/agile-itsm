package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ObjetivoMonitoramentoDTO;
import br.com.centralit.citcorpore.integracao.ObjetivoMonitoramentoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

@SuppressWarnings("rawtypes")
public class ObjetivoMonitoramentoServiceEjb extends CrudServiceImpl implements ObjetivoMonitoramentoService {

    private ObjetivoMonitoramentoDao dao;

    @Override
    protected ObjetivoMonitoramentoDao getDao() {
        if (dao == null) {
            dao = new ObjetivoMonitoramentoDao();
        }
        return dao;
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
    public Collection<ObjetivoMonitoramentoDTO> listObjetivosMonitoramento(final ObjetivoMonitoramentoDTO obj) throws Exception {
        try {
            return this.getDao().listObjetivosMonitoramento(obj);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
