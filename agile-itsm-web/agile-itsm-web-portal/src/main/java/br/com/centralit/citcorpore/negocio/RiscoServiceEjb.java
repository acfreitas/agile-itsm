package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.RiscoDTO;
import br.com.centralit.citcorpore.integracao.RiscoDAO;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class RiscoServiceEjb extends CrudServiceImpl implements RiscoService {

    private RiscoDAO dao;

    @Override
    protected RiscoDAO getDao() {
        if (dao == null) {
            dao = new RiscoDAO();
        }
        return dao;
    }

    @Override
    protected void validaCreate(final Object obj) throws Exception {
        if (this.existeNoBanco((RiscoDTO) obj)) {
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
    public boolean jaExisteRegistroComMesmoNome(final RiscoDTO risco) {
        try {
            return this.getDao().jaExisteRegistroComMesmoNome(risco);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean existeNoBanco(final RiscoDTO risco) {
        try {
            return this.getDao().existeNoBanco(risco);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public ArrayList<RiscoDTO> riscoAtivo() {
        try {
            return this.getDao().riscoAtivo();
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
