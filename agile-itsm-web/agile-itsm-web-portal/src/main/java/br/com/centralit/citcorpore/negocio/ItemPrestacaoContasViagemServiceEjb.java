package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ItemPrestacaoContasViagemDTO;
import br.com.centralit.citcorpore.bean.PrestacaoContasViagemDTO;
import br.com.centralit.citcorpore.integracao.ItemPrestacaoContasViagemDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

/**
 * @author ronnie.lopes
 *
 */
@SuppressWarnings("rawtypes")
public class ItemPrestacaoContasViagemServiceEjb extends CrudServiceImpl implements ItemPrestacaoContasViagemService {

    private ItemPrestacaoContasViagemDao dao;

    @Override
    protected ItemPrestacaoContasViagemDao getDao() {
        if (dao == null) {
            dao = new ItemPrestacaoContasViagemDao();
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
    public Collection<ItemPrestacaoContasViagemDTO> recuperaItensPrestacao(final PrestacaoContasViagemDTO prestacaoContasViagemDto) throws Exception {
        return this.getDao().listByPrestacaoContas(prestacaoContasViagemDto.getIdPrestacaoContasViagem());
    }

}
