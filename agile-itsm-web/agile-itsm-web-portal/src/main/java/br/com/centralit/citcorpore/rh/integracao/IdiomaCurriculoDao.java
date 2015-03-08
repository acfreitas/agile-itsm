package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.IdiomaCurriculoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

public class IdiomaCurriculoDao extends CrudDaoDefaultImpl {

    public IdiomaCurriculoDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection find(final IDto obj) throws PersistenceException {
        final List lst = new ArrayList();
        lst.add("idIdioma");
        return super.find(obj, lst);
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();

        listFields.add(new Field("idIdioma", "idIdioma", true, false, false, false));
        listFields.add(new Field("idCurriculo", "idCurriculo", true, false, false, false));
        listFields.add(new Field("idNivelConversa", "idNivelConversa", false, false, false, false));
        listFields.add(new Field("idNivelEscrita", "idNivelEscrita", false, false, false, false));
        listFields.add(new Field("idNivelLeitura", "idNivelLeitura", false, false, false, false));

        return listFields;
    }

    @Override
    public String getTableName() {
        return "RH_IdiomaCurriculo";
    }

    @Override
    public Collection list() throws PersistenceException {
        return null;
    }

    @Override
    public Class getBean() {
        return IdiomaCurriculoDTO.class;
    }

    public void deleteByIdCurriculo(final Integer parm) throws PersistenceException {
        final List condicao = new ArrayList();
        condicao.add(new Condition("idCurriculo", "=", parm));
        super.deleteByCondition(condicao);
    }

    private List getColunasRestoreAll() {

        final List listRetorno = new ArrayList();

        listRetorno.add("idIdioma");
        listRetorno.add("idCurriculo");
        listRetorno.add("idNivelConversa");
        listRetorno.add("idNivelLeitura");
        listRetorno.add("idNivelEscrita");
        listRetorno.add("descricaoIdioma");
        return listRetorno;
    }

    public Collection findByIdCurriculo(final Integer parm) throws PersistenceException {
        final StringBuilder sql = new StringBuilder();
        sql.append("SELECT ic.idIdioma,ic.idCurriculo,ic.idNivelConversa,ic.idNivelLeitura,ic.idNivelEscrita,i.descricao");
        sql.append("  FROM rh_idiomacurriculo ic ");
        sql.append("        INNER JOIN rh_idioma i ON i.ididioma = ic.ididioma");
        sql.append("  WHERE ic.idCurriculo = ? ");
        sql.append(" ORDER BY i.descricao");

        final List parametro = new ArrayList();

        parametro.add(parm);

        final List lista = this.execSQL(sql.toString(), parametro.toArray());

        return engine.listConvertion(this.getBean(), lista, this.getColunasRestoreAll());
    }

}
