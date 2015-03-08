package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.FormulaDTO;
import br.com.centralit.citcorpore.integracao.FormulaDao;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.service.CrudServiceImpl;

@SuppressWarnings("rawtypes")
public class FormulaServiceEjb extends CrudServiceImpl implements FormulaService {

    private FormulaDao dao;

    @Override
    protected FormulaDao getDao() {
        if (dao == null) {
            dao = new FormulaDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdentificador(final String parm) throws Exception {
        try {
            return this.getDao().findByIdentificador(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdentificador(final String parm) throws Exception {
        try {
            this.getDao().deleteByIdentificador(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean existeRegistro(final String nome) {
        final ArrayList<Condition> condicoes = new ArrayList<Condition>();
        condicoes.add(new Condition("nome", "=", nome));
        Collection retorno = null;
        try {
            retorno = this.getDao().findByCondition(condicoes, null);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return retorno == null ? false : true;

    }

    @Override
    public boolean verificarSeIdentificadorExiste(final FormulaDTO formula) throws PersistenceException {
        return this.getDao().verificarSeIdentificadorExiste(formula);
    }

    @Override
    public boolean verificarSeNomeExiste(final FormulaDTO formula) throws PersistenceException {
        return this.getDao().verificarSeNomeExiste(formula);
    }

}
