package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.SolucaoDefinitivaDTO;
import br.com.centralit.citcorpore.integracao.SolucaoDefinitivaDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilDatas;

public class SolucaoDefinitivaServiceEjb extends CrudServiceImpl implements SolucaoDefinitivaService {

    private SolucaoDefinitivaDAO dao;

    @Override
    protected SolucaoDefinitivaDAO getDao() {
        if (dao == null) {
            dao = new SolucaoDefinitivaDAO();
        }
        return dao;
    }

    @Override
    public SolucaoDefinitivaDTO findSolucaoDefinitiva(final SolucaoDefinitivaDTO solucaoDefinitiva) throws Exception {
        return (SolucaoDefinitivaDTO) this.getDao().restore(solucaoDefinitiva);
    }

    @Override
    public SolucaoDefinitivaDTO create(final SolucaoDefinitivaDTO solucaoDefinitivaDto, TransactionControler tc) throws Exception {
        solucaoDefinitivaDto.setDataHoraCriacao(UtilDatas.getDataHoraAtual());
        final SolucaoDefinitivaDAO solucaoDefinitivaDao = new SolucaoDefinitivaDAO();

        if (tc == null) {
            tc = new TransactionControlerImpl(this.getDao().getAliasDB());
            tc.start();
        }
        solucaoDefinitivaDao.setTransactionControler(tc);
        return (SolucaoDefinitivaDTO) solucaoDefinitivaDao.create(solucaoDefinitivaDto);

    }

    @Override
    public SolucaoDefinitivaDTO findByIdProblema(final SolucaoDefinitivaDTO solucaoDefinitiva) throws Exception {
        return (SolucaoDefinitivaDTO) this.getDao().findByIdProblema(solucaoDefinitiva);
    }

}
