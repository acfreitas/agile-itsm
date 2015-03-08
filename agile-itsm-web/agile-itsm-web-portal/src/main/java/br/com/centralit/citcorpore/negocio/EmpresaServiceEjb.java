package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.EmpresaDTO;
import br.com.centralit.citcorpore.integracao.EmpresaDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

/**
 * @author rosana.godinho
 *
 */
@SuppressWarnings("rawtypes")
public class EmpresaServiceEjb extends CrudServiceImpl implements EmpresaService {

    private EmpresaDao dao;

    @Override
    protected EmpresaDao getDao() {
        if (dao == null) {
            dao = new EmpresaDao();
        }
        return dao;
    }

    @Override
    protected void validaCreate(final Object obj) throws Exception {
        if (this.jaExisteRegistroComMesmoNome((EmpresaDTO) obj)) {
            throw new LogicException(this.i18nMessage("citcorpore.comum.registroJaCadastrado"));
        }
    }

    public Collection list(final List ordenacao) throws LogicException, ServiceException {
        return null;
    }

    public Collection list(final String ordenacao) throws LogicException, ServiceException {
        return null;
    }

    @Override
    public boolean jaExisteRegistroComMesmoNome(final EmpresaDTO empresa) {
        try {
            return this.getDao().jaExisteRegistroComMesmoNome(empresa);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
