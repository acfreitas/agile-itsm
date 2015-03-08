package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.PerfilContratoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class PerfilContratoServiceEjb extends CrudServiceImpl implements PerfilContratoService {

    private PerfilContratoDao dao;

    @Override
    protected PerfilContratoDao getDao() {
        if (dao == null) {
            dao = new PerfilContratoDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdContrato(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdContrato(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdContrato(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdContrato(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection getTotaisByIdMarcoPagamentoPrj(final Integer idMarcoPagamentoPrjParm, final Integer idLinhaBaseProjetoParm) throws Exception {
        try {
            return this.getDao().getTotaisByIdMarcoPagamentoPrj(idMarcoPagamentoPrjParm, idLinhaBaseProjetoParm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection getTotaisByIdTarefaLinhaBaseProjeto(final Integer idTarefaLinhaBaseProjetoParm) throws Exception {
        try {
            return this.getDao().getTotaisByIdTarefaLinhaBaseProjeto(idTarefaLinhaBaseProjetoParm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection getTotaisByIdLinhaBaseProjeto(final Integer idLinhaBaseProjetoParm) throws Exception {
        try {
            return this.getDao().getTotaisByIdLinhaBaseProjeto(idLinhaBaseProjetoParm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection getTotaisSEMIdMarcoPagamentoPrj(final Integer idLinhaBaseProjetoParm) throws Exception {
        try {
            return this.getDao().getTotaisSEMIdMarcoPagamentoPrj(idLinhaBaseProjetoParm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
