package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.integracao.DemandaDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class DemandaServiceEjb extends CrudServiceImpl implements DemandaService {

    private DemandaDao dao;

    @Override
    protected DemandaDao getDao() {
        if (dao == null) {
            dao = new DemandaDao();
        }
        return dao;
    }

    public Collection list(final List ordenacao) throws LogicException, ServiceException {
        return null;
    }

    public Collection list(final String ordenacao) throws LogicException, ServiceException {
        return null;
    }

    @Override
    public IDto create(final IDto model) throws ServiceException, LogicException {
        /*
         * //Instancia Objeto controlador de transacao
         * CrudDAO crudDao = getDao();
         * ExecucaoDemandaDao execucaoDemandaDao = new ExecucaoDemandaDao();
         * TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
         * FluxoDao fluxoDao = new FluxoDao();
         * DemandaDTO demanda = (DemandaDTO)model;
         * try{
         * //Faz validacao, caso exista.
         * validaCreate(model);
         * //Seta o TransactionController para os DAOs
         * crudDao.setTransactionControler(tc);
         * execucaoDemandaDao.setTransactionControler(tc);
         * //Inicia transacao
         * tc.start();
         * FluxoDTO fluxo = fluxoDao.getNextAtividadeByFluxo(demanda.getIdFluxo(), null);
         * //Executa operacoes pertinentes ao negocio.
         * model = crudDao.create(model);
         * ExecucaoDemandaDTO execucaoDemanda = new ExecucaoDemandaDTO();
         * execucaoDemanda.setIdAtividade(fluxo.getIdAtividade());
         * execucaoDemanda.setIdDemanda(demanda.getIdDemanda());
         * execucaoDemanda.setSituacao("N");
         * execucaoDemanda.setGrupoExecutor(fluxo.getGrupoExecutor());
         * execucaoDemanda = (ExecucaoDemandaDTO) execucaoDemandaDao.create(execucaoDemanda);
         * //Faz commit e fecha a transacao.
         * tc.commit();
         * tc.close();
         * return model;
         * }catch(Exception e){
         * this.rollbackTransaction(tc, e);
         * }
         */

        return model;
    }

    @Override
    public Collection findByIdOS(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdOS(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
