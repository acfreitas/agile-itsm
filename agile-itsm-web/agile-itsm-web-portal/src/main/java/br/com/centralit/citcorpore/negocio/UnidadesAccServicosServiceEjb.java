package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citcorpore.bean.UnidadesAccServicosDTO;
import br.com.centralit.citcorpore.integracao.UnidadesAccServicosDao;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.WebUtil;

@SuppressWarnings({"rawtypes", "unchecked"})
public class UnidadesAccServicosServiceEjb extends CrudServiceImpl implements UnidadesAccServicosService {

    private UnidadesAccServicosDao dao;

    @Override
    protected UnidadesAccServicosDao getDao() {
        if (dao == null) {
            dao = new UnidadesAccServicosDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdUnidade(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdUnidade(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdUnidade(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdUnidade(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdServico(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdServico(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdServico(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdServico(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List deserealizaObjetosDoRequest(final HttpServletRequest request) throws Exception {
        return (List) WebUtil.deserializeCollectionFromRequest(UnidadesAccServicosDTO.class, "servicosSerializados", request);
    }

    @Override
    public Collection<UnidadesAccServicosDTO> consultarServicosAtivosPorUnidade(final Integer idUnidade) throws Exception {
        try {
            return this.getDao().consultaServicosPorUnidade(idUnidade);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void excluirAssociacaoServicosUnidade(final Integer idUnidade, final Integer idServico) throws PersistenceException, ServiceException, Exception {
        try {
            this.getDao().deleteByIdServicoUnidade(idUnidade, idServico);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
