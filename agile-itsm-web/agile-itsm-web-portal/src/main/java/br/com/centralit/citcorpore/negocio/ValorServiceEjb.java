package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.TipoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.ValorDTO;
import br.com.centralit.citcorpore.integracao.ValorDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

@SuppressWarnings("rawtypes")
public class ValorServiceEjb extends CrudServiceImpl implements ValorService {

    private ValorDao dao;

    @Override
    protected ValorDao getDao() {
        if (dao == null) {
            dao = new ValorDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdItemConfiguracao(final Integer idItemConfiguracao) throws Exception {
        try {
            return this.getDao().findByIdItemConfiguracao(idItemConfiguracao);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdItemConfiguracao(final Integer idItemConfiguracao) throws Exception {
        try {
            this.getDao().deleteByIdItemConfiguracao(idItemConfiguracao);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdCaracteristica(final Integer idCaracteristica) throws Exception {
        try {
            return this.getDao().findByIdCaracteristica(idCaracteristica);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdCaracteristica(final Integer idCaracteristica) throws Exception {
        try {
            this.getDao().deleteByIdCaracteristica(idCaracteristica);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ValorDTO restore(final Integer idBaseItemConfiguracao, final Integer idCaracteristica) throws Exception {
        return this.getDao().restore(false, idBaseItemConfiguracao, idCaracteristica);
    }

    @Override
    public Collection<ValorDTO> findByItemAndTipoItemConfiguracao(final ItemConfiguracaoDTO itemConfiguracao, final TipoItemConfiguracaoDTO tipoItemConfiguracao)
            throws ServiceException, Exception {
        return this.getDao().findByItemAndTipoItemConfiguracao(itemConfiguracao, tipoItemConfiguracao);
    }

    @Override
    public Collection<ValorDTO> findByItemAndTipoItemConfiguracaoSofware(final ItemConfiguracaoDTO itemConfiguracao, final TipoItemConfiguracaoDTO tipoItemConfiguracao)
            throws Exception {
        return this.getDao().findByItemAndTipoItemConfiguracaoSofware(itemConfiguracao, tipoItemConfiguracao);

    }

    @Override
    public ValorDTO restoreItemConfiguracao(final Integer idItemConfiguracao, final Integer idCaracteristica) throws Exception {
        return this.getDao().restoreItemConfiguracao(idItemConfiguracao, idCaracteristica);
    }

    @Override
    public Collection listByItemConfiguracaoAndTagCaracteristica(final Integer idItemConfiguracao, final String tag) throws Exception {
        return this.getDao().listByItemConfiguracaoAndTagCaracteristica(idItemConfiguracao, tag);
    }

    @Override
    public Collection listUniqueValuesByTagCaracteristica(final String tag) throws Exception {
        return this.getDao().listUniqueValuesByTagCaracteristica(tag);
    }

}
