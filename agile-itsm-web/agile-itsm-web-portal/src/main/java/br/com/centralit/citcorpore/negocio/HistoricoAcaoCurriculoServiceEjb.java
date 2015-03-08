package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.HistoricoAcaoCurriculoDTO;
import br.com.centralit.citcorpore.integracao.HistoricoAcaoCurriculoDao;
import br.com.centralit.citcorpore.rh.bean.CurriculoDTO;
import br.com.centralit.citcorpore.rh.integracao.CurriculoDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;

/**
 * @author ygor.magalhaes
 *
 */
@SuppressWarnings("rawtypes")
public class HistoricoAcaoCurriculoServiceEjb extends CrudServiceImpl implements HistoricoAcaoCurriculoService {

    private HistoricoAcaoCurriculoDao dao;

    @Override
    protected HistoricoAcaoCurriculoDao getDao() {
        if (dao == null) {
            dao = new HistoricoAcaoCurriculoDao();
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
    public IDto create(IDto model) throws ServiceException, LogicException {
        final CrudDAO crudDao = this.getDao();

        final CurriculoDao curriculoDao = new CurriculoDao();
        final TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());

        final HistoricoAcaoCurriculoDTO acaoCurriculoDTO = (HistoricoAcaoCurriculoDTO) model;
        CurriculoDTO curriculoDTO = new CurriculoDTO();

        try {
            tc.start();

            // Faz validacao, caso exista.

            this.validaCreate(model);

            // Seta o TransactionController para os DAOs

            crudDao.setTransactionControler(tc);
            curriculoDao.setTransactionControler(tc);

            curriculoDTO.setIdCurriculo(acaoCurriculoDTO.getIdCurriculo());
            curriculoDTO = (CurriculoDTO) curriculoDao.restore(curriculoDTO);

            curriculoDTO.setListaNegra(acaoCurriculoDTO.getAcao());
            curriculoDao.update(curriculoDTO);

            model = crudDao.create(acaoCurriculoDTO);

        } catch (final Exception ex) {
            this.rollbackTransaction(tc, ex);
        }

        return model;
    }

    @Override
    public Collection listByIdCurriculo(final Integer idCurriculo) throws Exception {
        return this.getDao().listByIdCurriculo(idCurriculo);
    }

}
