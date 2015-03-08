package br.com.centralit.citcorpore.negocio;

import java.sql.Date;
import java.util.Collection;

import br.com.centralit.citcorpore.integracao.NagiosNDOStateHistoryDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class NagiosNDOStateHistoryServiceEjb extends CrudServiceImpl implements NagiosNDOStateHistoryService {

    private String jndiName;

    @Override
    protected NagiosNDOStateHistoryDao getDao() {
        return new NagiosNDOStateHistoryDao(jndiName);
    }

    @Override
    public void setJndiName(final String jndiNameParm) {
        jndiName = jndiNameParm;
    }

    @Override
    public Collection findByObject_id(final Integer parm) throws Exception {
        final NagiosNDOStateHistoryDao dao = new NagiosNDOStateHistoryDao(jndiName);
        try {
            return dao.findByObject_id(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByObject_id(final Integer parm) throws Exception {
        final NagiosNDOStateHistoryDao dao = new NagiosNDOStateHistoryDao(jndiName);
        try {
            dao.deleteByObject_id(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByHostServiceStatus(final String jndiNameParm, final String hostName, final String serviceName, final String status, final Date dataInicial,
            final Date dataFinal) throws Exception {
        final NagiosNDOStateHistoryDao nagiosNDOStateHistoryDao = new NagiosNDOStateHistoryDao(jndiNameParm);
        return nagiosNDOStateHistoryDao.findByHostServiceStatus(hostName, serviceName, status, dataInicial, dataFinal);
    }

    @Override
    public Collection findByHostServiceStatusAndServiceNull(final String jndiNameParm, final String hostName, final String status, final Date dataInicial, final Date dataFinal)
            throws Exception {
        final NagiosNDOStateHistoryDao nagiosNDOStateHistoryDao = new NagiosNDOStateHistoryDao(jndiNameParm);
        return nagiosNDOStateHistoryDao.findByHostServiceStatusAndServiceNull(hostName, status, dataInicial, dataFinal);
    }

}
