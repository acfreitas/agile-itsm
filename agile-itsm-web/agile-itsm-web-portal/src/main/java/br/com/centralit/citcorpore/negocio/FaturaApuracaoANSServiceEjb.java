package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.FaturaApuracaoANSDTO;
import br.com.centralit.citcorpore.integracao.FaturaApuracaoANSDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class FaturaApuracaoANSServiceEjb extends CrudServiceImpl implements FaturaApuracaoANSService {

    private FaturaApuracaoANSDao dao;

    @Override
    protected FaturaApuracaoANSDao getDao() {
        if (dao == null) {
            dao = new FaturaApuracaoANSDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdFatura(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdFatura(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdFatura(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdFatura(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public FaturaApuracaoANSDTO findByIdAcordoNivelServicoContrato(final Integer idAcordoNivelServicoContrato) throws Exception {
        try {
            return this.getDao().findByIdAcordoNivelServicoContrato(idAcordoNivelServicoContrato);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdAcordoNivelServicoContrato(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdAcordoNivelServicoContrato(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
