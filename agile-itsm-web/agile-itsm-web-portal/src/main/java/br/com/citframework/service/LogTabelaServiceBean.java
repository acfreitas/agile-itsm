package br.com.citframework.service;

import java.util.Collection;
import java.util.Iterator;

import br.com.citframework.dto.IDto;
import br.com.citframework.dto.LogEstrutura;
import br.com.citframework.dto.LogTabela;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.LogEstruturaDao;
import br.com.citframework.integracao.LogTabelaDao;
import br.com.citframework.integracao.MetaDataDao;
import br.com.citframework.integracao.TransactionControler;

/**
 * @author karem.ricarte
 *
 */
public class LogTabelaServiceBean extends CrudServiceImpl implements LogTabelaService {

    private LogTabelaDao dao;

    @Override
    protected LogTabelaDao getDao() {
        if (dao == null) {
            dao = new LogTabelaDao(usuario);
        }
        return dao;
    }

    @Override
    public LogTabela getLogByTabela(final String nomeTabela) throws ServiceException {
        final LogTabelaDao tBdao = this.getDao();
        try {
            LogTabela lt = tBdao.getLogByTabela(nomeTabela);
            if (lt != null) {
                return lt;
            } else {
                lt = new LogTabela();
                lt.setNomeTabela(nomeTabela);
                lt = (LogTabela) this.create(lt);
                return lt;
            }
        } catch (final Exception e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    @Override
    public IDto create(IDto model) throws ServiceException, LogicException {
        final LogEstruturaDao eSDao = new LogEstruturaDao(usuario);
        final MetaDataDao mdDao = new MetaDataDao();

        TransactionControler tc = null;

        try {
            tc = this.getDao().getTransactionControler();

            mdDao.setTransactionControler(tc);
            eSDao.setTransactionControler(tc);
            model = this.getDao().create(model);

            final Collection col = mdDao.getCamposByTabela(((LogTabela) model).getNomeTabela());
            String str = "";
            if (col != null) {
                final Iterator it = col.iterator();
                while (it.hasNext()) {
                    if (str.trim().length() > 0) {
                        str += ";";
                    }
                    str += it.next();
                }
            }
            final LogEstrutura le = new LogEstrutura();
            le.setLogTabela_idlog(((LogTabela) model).getIdLog());
            le.setEstrutura(str);
            eSDao.create(le);

            tc.commit();
            tc.close();
        } catch (final Exception e) {
            e.printStackTrace();
            this.rollbackTransaction(tc, e);
        }
        return model;
    }

}
