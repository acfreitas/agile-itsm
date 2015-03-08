package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.MoedaDTO;
import br.com.centralit.citcorpore.integracao.MoedaDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class MoedaServiceEjb extends CrudServiceImpl implements MoedaService {

    private MoedaDao dao;

    @Override
    protected MoedaDao getDao() {
        if (dao == null) {
            dao = new MoedaDao();
        }
        return dao;
    }

    @Override
    public boolean verificaSeCadastrado(final MoedaDTO moedaDTO) throws PersistenceException {
        return this.getDao().verificaSeCadastrado(moedaDTO);
    }

    @Override
    public boolean verificaRelacionamento(final MoedaDTO moedaDTO) throws Exception {
        try {
            return this.getDao().verificaRelacionamento(moedaDTO);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateNotNull(final IDto obj) throws Exception {
        try {
            this.getDao().updateNotNull(obj);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Collection findAllAtivos() throws Exception {
        return this.getDao().findAtivos();
    }

}
