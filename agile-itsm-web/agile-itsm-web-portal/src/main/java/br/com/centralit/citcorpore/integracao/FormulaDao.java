package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.FormulaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;
@SuppressWarnings({ "unchecked","rawtypes" })
public class FormulaDao extends CrudDaoDefaultImpl {
    public FormulaDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<Field> getFields() {
        Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idFormula" ,"idFormula", true, true, false, false));
        listFields.add(new Field("identificador" ,"identificador", false, false, false, false));
        listFields.add(new Field("nome" ,"nome", false, false, false, false));
        listFields.add(new Field("conteudo" ,"conteudo", false, false, false, false));
        listFields.add(new Field("datacriacao" ,"datacriacao", false, false, false, false));
        return listFields;
    }
    @Override
    public String getTableName() {
        return this.getOwner() + "Formula";
    }
    @Override
    public Collection list() throws PersistenceException {
        return null;
    }

    @Override
    public Class getBean() {
        return FormulaDTO.class;
    }
    @Override
    public Collection find(IDto arg0) throws PersistenceException {
        return null;
    }
    public Collection findByIdentificador(String parm) throws PersistenceException {
        List condicao = new ArrayList();
        List ordenacao = new ArrayList();
        condicao.add(new Condition("identificador", "=", parm));
        ordenacao.add(new Order("nome"));
        return super.findByCondition(condicao, ordenacao);
    }
    public Collection findByIdentificadorAndIdDiferente(String parm1, String parm2) throws PersistenceException {
        List condicao = new ArrayList();
        List ordenacao = new ArrayList();
        condicao.add(new Condition("identificador", "=", parm1));
        condicao.add(new Condition("idFormula", "!=", Integer.parseInt(parm2)));
        ordenacao.add(new Order("nome"));
        return super.findByCondition(condicao, ordenacao);
    }
   public void deleteByIdentificador(String parm) throws PersistenceException {
        List condicao = new ArrayList();
        condicao.add(new Condition("identificador", "=", parm));
        super.deleteByCondition(condicao);
    }

    public boolean verificarSeIdentificadorExiste(FormulaDTO formula) throws PersistenceException {
        List parametro = new ArrayList();
        List list = new ArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("select identificador from " + getTableName() + "  where  identificador = ?");
        parametro.add(formula.getIdentificador());

        if (formula.getIdFormula() != null) {
            sql.append(" and idformula <> ? ");
            parametro.add(formula.getIdFormula());
        }
        list = this.execSQL(sql.toString(), parametro.toArray());

        if (list != null && !list.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean verificarSeNomeExiste(FormulaDTO formula) throws PersistenceException {
        List parametro = new ArrayList();
        List list = new ArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("select nome from formula where  nome = ? ");
        parametro.add(formula.getNome());

        if (formula.getIdFormula() != null) {
            sql.append("and idformula <> ?");
            parametro.add(formula.getIdFormula());
        }
        list = this.execSQL(sql.toString(), parametro.toArray());

        if (list != null && !list.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

}
