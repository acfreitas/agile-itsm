package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.DepartamentoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class DepartamentoDao extends CrudDaoDefaultImpl {

    public DepartamentoDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection find(final IDto obj) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {

        final Collection<Field> listFields = new ArrayList<>();

        listFields.add(new Field("iddepartamento", "idDepartamento", true, true, false, false));
        listFields.add(new Field("descricao", "descricao", false, false, false, false));
        listFields.add(new Field("idcentrocusto", "idCentroCusto", false, false, false, false));
        listFields.add(new Field("lotacaopai", "lotacaoPai", false, false, false, false));
        listFields.add(new Field("situacao", "situacao", false, false, false, false));
        listFields.add(new Field("analitico", "analitico", false, false, false, false));
        listFields.add(new Field("idparceiro", "idParceiro", false, false, false, false));
        listFields.add(new Field("coddep", "codDep", false, false, false, false));

        return listFields;
    }

    @Override
    public String getTableName() {
        return "rh_departamento";
    }

    @Override
    public Collection list() throws PersistenceException {
        final List list = new ArrayList();
        list.add(new Order("descricao"));
        return super.list(list);
    }

    @Override
    public Class getBean() {
        return DepartamentoDTO.class;
    }

}
