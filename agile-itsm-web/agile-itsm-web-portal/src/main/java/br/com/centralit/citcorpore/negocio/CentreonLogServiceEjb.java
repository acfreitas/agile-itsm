package br.com.centralit.citcorpore.negocio;

import java.sql.Date;
import java.util.Collection;

import br.com.centralit.citcorpore.integracao.CentreonLogDao;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServiceImpl;

public class CentreonLogServiceEjb extends CrudServiceImpl implements CentreonLogService {

    private String jndiName;

    @Override
    protected CrudDAO getDao() {
        return new CentreonLogDao(jndiName);
    }

    @Override
    public void setJndiName(final String jndiNameParm) {
        jndiName = jndiNameParm;
    }

    @Override
    public Collection findByHostServiceStatus(final String jndiNameParm, final String hostName, final String serviceName, final String status, final Date dataInicial,
            final Date dataFinal) throws Exception {
        final CentreonLogDao centreonLogDao = new CentreonLogDao(jndiNameParm);
        return centreonLogDao.findByHostServiceStatus(hostName, serviceName, status, dataInicial, dataFinal);
    }

    @Override
    public Collection findByHostServiceStatusAndServiceNull(final String jndiNameParm, final String hostName, final String status, final Date dataInicial, final Date dataFinal)
            throws Exception {
        final CentreonLogDao centreonLogDao = new CentreonLogDao(jndiNameParm);
        return centreonLogDao.findByHostServiceStatusAndServiceNull(hostName, status, dataInicial, dataFinal);
    }

}
