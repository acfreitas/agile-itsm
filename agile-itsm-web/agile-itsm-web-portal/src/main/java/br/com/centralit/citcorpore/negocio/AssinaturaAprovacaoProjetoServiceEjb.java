package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.AssinaturaAprovacaoProjetoDTO;
import br.com.centralit.citcorpore.integracao.AssinaturaAprovacaoProjetoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class AssinaturaAprovacaoProjetoServiceEjb extends CrudServiceImpl implements AssinaturaAprovacaoProjetoService {

    private AssinaturaAprovacaoProjetoDao dao;

    @Override
    protected AssinaturaAprovacaoProjetoDao getDao() {
        if (dao == null) {
            dao = new AssinaturaAprovacaoProjetoDao();
        }
        return dao;
    }

    @Override
    public Collection<AssinaturaAprovacaoProjetoDTO> findByIdProjeto(final Integer parm) throws ServiceException {
        try {
            return this.getDao().findByIdProjeto(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdProjeto(final Integer parm) throws ServiceException {
        try {
            this.getDao().deleteByIdProjeto(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection<AssinaturaAprovacaoProjetoDTO> findByIdEmpregado(final Integer parm) throws ServiceException {
        try {
            return this.getDao().findByIdEmpregado(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdEmpregado(final Integer parm) throws ServiceException {
        try {
            this.getDao().deleteByIdEmpregado(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
