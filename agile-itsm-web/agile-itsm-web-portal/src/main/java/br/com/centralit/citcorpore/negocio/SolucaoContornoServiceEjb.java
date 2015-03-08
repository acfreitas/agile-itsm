package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.SolucaoContornoDTO;
import br.com.centralit.citcorpore.integracao.SolucaoContornoDao;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilDatas;

public class SolucaoContornoServiceEjb extends CrudServiceImpl implements SolucaoContornoService {

    private SolucaoContornoDao dao;

    @Override
    protected SolucaoContornoDao getDao() {
        if (dao == null) {
            dao = new SolucaoContornoDao();
        }
        return dao;
    }

    @Override
    public SolucaoContornoDTO findSolucaoContorno(final SolucaoContornoDTO solucaoContorno) throws Exception {
        return (SolucaoContornoDTO) this.getDao().restore(solucaoContorno);
    }

    @Override
    public SolucaoContornoDTO findByIdProblema(final SolucaoContornoDTO solucaoContorno) throws Exception {
        return (SolucaoContornoDTO) this.getDao().findByIdProblema(solucaoContorno);
    }

    @Override
    public SolucaoContornoDTO create(final SolucaoContornoDTO solucaoContornoDto, TransactionControler tc) throws Exception {
        solucaoContornoDto.setDataHoraCriacao(UtilDatas.getDataHoraAtual());
        final SolucaoContornoDao solucaoContornoDao = new SolucaoContornoDao();

        if (tc == null) {
            tc = new TransactionControlerImpl(this.getDao().getAliasDB());
            tc.start();
        }

        solucaoContornoDao.setTransactionControler(tc);

        return (SolucaoContornoDTO) solucaoContornoDao.create(solucaoContornoDto);
    }

}
