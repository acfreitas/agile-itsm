package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;

import br.com.centralit.citcorpore.bean.FormulaOsDTO;
import br.com.centralit.citcorpore.integracao.FormulaOsDao;
import br.com.citframework.service.CrudServiceImpl;

/**
 * @author CentralIT
 *
 */
/**
 * @author Centralit
 *
 */
public class FormulaOsServiceEjb extends CrudServiceImpl implements FormulaOsService {

    private FormulaOsDao dao;

    @Override
    protected FormulaOsDao getDao() {
        if (dao == null) {
            dao = new FormulaOsDao();
        }
        return dao;
    }

    @Override
    public ArrayList<FormulaOsDTO> listar(final int idContrato) {
        try {
            final ArrayList<FormulaOsDTO> listar = (ArrayList<FormulaOsDTO>) this.getDao().listar(idContrato);
            return listar;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public FormulaOsDTO buscarPorFormula(final String formula) throws Exception {
        try {
            final FormulaOsDTO formulaOsDTO = this.getDao().buscarPorFormula(formula);
            return formulaOsDTO;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean verificaSerExisteFormulaIgual(final String formula, final int idFormula) throws Exception {
        return this.getDao().verificaSerExisteFormulaIgual(formula, idFormula);
    }

}
