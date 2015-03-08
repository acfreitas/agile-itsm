package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.TipoLiberacaoDTO;
import br.com.centralit.citcorpore.integracao.TipoLiberacaoDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.service.CrudServiceImpl;

@SuppressWarnings({"rawtypes", "unchecked"})
public class TipoLiberacaoServiceEjb extends CrudServiceImpl implements TipoLiberacaoService {

    private TipoLiberacaoDAO dao;

    @Override
    protected TipoLiberacaoDAO getDao() {
        if (dao == null) {
            dao = new TipoLiberacaoDAO();
        }
        return dao;
    }

    @Override
    public Collection findByIdTipoLiberacao(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdTipoLiberacao(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdTipoLiberacao(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdTipoLiberacao(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByNomeTipoLiberacao(final Integer parm) throws Exception {
        return this.getDao().findByNomeTipoLiberacao(parm);
    }

    @Override
    public void deleteByNomeTipoLiberacao(final Integer parm) throws Exception {
        this.getDao().findByNomeTipoLiberacao(parm);
    }

    @Override
    public Collection<TipoLiberacaoDTO> tiposAtivosPorNome(final String nome) {
        final List condicoes = new ArrayList<Condition>();
        condicoes.add(new Condition("nomeTipoLiberacao", "=", nome));
        condicoes.add(new Condition("datafim", "!=", "null"));
        try {
            return this.getDao().findByCondition(condicoes, null);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<TipoLiberacaoDTO>();

    }

    @Override
    public boolean verificarTipoLiberacaoAtivos(final TipoLiberacaoDTO obj) throws Exception {
        return this.getDao().verificarTipoLiberacaoAtivos(obj);
    }

    @Override
    public Collection encontrarPorNomeTipoLiberacao(final TipoLiberacaoDTO obj) throws Exception {
        return this.getDao().encontrarPorNomeTipoLiberacao(obj);
    }

    @Override
    public Collection getAtivos() throws Exception {
        return this.getDao().getAtivos();
    }

}
