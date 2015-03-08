package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.AtividadesOSDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

@SuppressWarnings("rawtypes")
public class AtividadesOSServiceEjb extends CrudServiceImpl implements AtividadesOSService {

    private AtividadesOSDao dao;

    @Override
    protected AtividadesOSDao getDao() {
        if (dao == null) {
            dao = new AtividadesOSDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdOS(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdOS(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdOS(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdOS(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Double retornarCustoAtividadeOSByIdOs(final Integer idOs) throws Exception {
        try {
            return this.getDao().retornarCustoAtividadeOSByIdOs(idOs);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public Double retornarGlosaAtividadeOSByIdOs(final Integer idOs) throws Exception {
        try {
            return this.getDao().retornarGlosaAtividadeOSByIdOs(idOs);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public Double retornarQtdExecucao(final Integer idOs) throws Exception {
        try {
            return this.getDao().retornarQtdExecucao(idOs);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public Collection findByIdOsServicoContratoContabil(final Integer idOs, final Integer servicoContratoContabil) throws Exception {
        try {
            return this.getDao().findByIdOsServicoContratoContabil(idOs, servicoContratoContabil);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
