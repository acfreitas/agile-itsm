package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.MeuCatalogoDTO;
import br.com.centralit.citcorpore.integracao.MeuCatalogoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class MeuCatalogoServiceEjb extends CrudServiceImpl implements MeuCatalogoService {

    private MeuCatalogoDao dao;

    @Override
    protected MeuCatalogoDao getDao() {
        if (dao == null) {
            dao = new MeuCatalogoDao();
        }
        return dao;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public Collection findByCondition(final Integer id) throws ServiceException, Exception {
        return this.getDao().findByCondition(id);
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public Collection<MeuCatalogoDTO> findByIdServicoAndIdUsuario(final Integer idServico, final Integer idUsuario) throws ServiceException, Exception {
        return this.getDao().findByIdServicoAndIdUsuario(idServico, idUsuario);
    }

}
