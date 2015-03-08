package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.TipoMudancaDTO;
import br.com.centralit.citcorpore.integracao.TipoMudancaDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.service.CrudServiceImpl;

@SuppressWarnings({"rawtypes", "unchecked"})
public class TipoMudancaServiceEjb extends CrudServiceImpl implements TipoMudancaService {

    private TipoMudancaDAO dao;

    @Override
    protected TipoMudancaDAO getDao() {
        if (dao == null) {
            dao = new TipoMudancaDAO();
        }
        return dao;
    }

    @Override
    public Collection findByIdTipoMudanca(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdTipoMudanca(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdTipoMudanca(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdTipoMudanca(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByNomeTipoMudanca(final Integer parm) throws Exception {
        return this.getDao().findByNomeTipoMudanca(parm);
    }

    @Override
    public void deleteByNomeTipoMudanca(final Integer parm) throws Exception {
        this.getDao().findByNomeTipoMudanca(parm);
    }

    @Override
    public Collection<TipoMudancaDTO> tiposAtivosPorNome(final String nome) {
        final List condicoes = new ArrayList<Condition>();
        condicoes.add(new Condition("nomeTipoMudanca", "=", nome));
        condicoes.add(new Condition("datafim", "!=", "null"));
        try {
            return this.getDao().findByCondition(condicoes, null);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public boolean verificarTipoMudancaAtivos(final TipoMudancaDTO obj) throws Exception {
        return this.getDao().verificarTipoMudancaAtivos(obj);
    }

    @Override
    public Collection encontrarPorNomeTipoMudanca(final TipoMudancaDTO obj) throws Exception {
        return this.getDao().encontrarPorNomeTipoMudanca(obj);
    }

    @Override
    public Collection getAtivos() throws Exception {
        return this.getDao().getAtivos();
    }

}
