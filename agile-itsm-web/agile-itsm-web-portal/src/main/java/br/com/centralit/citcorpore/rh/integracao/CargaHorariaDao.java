package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class CargaHorariaDao extends CrudDaoDefaultImpl {

    public CargaHorariaDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection find(final IDto obj) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idcargahoraria", "idCargaHoraria", true, true, false, false));
        listFields.add(new Field("codcargahor", "codCargaHor", false, false, false, false));
        listFields.add(new Field("diasemana", "diaSemana", false, false, false, false));
        listFields.add(new Field("entrada", "entrada", false, false, false, false));
        listFields.add(new Field("saida", "saida", false, false, false, false));
        listFields.add(new Field("descansoem", "descansoEm", false, false, false, false));
        listFields.add(new Field("turno", "turno", false, false, false, false));
        listFields.add(new Field("dataalter", "dataAlter", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return "rh_cargahoraria";
    }

    @Override
    public Collection list() throws PersistenceException {
        final List list = new ArrayList();
        list.add(new Order("descricao"));
        return super.list(list);
    }

    @Override
    public Class getBean() {
        return CargaHorariaDao.class;
    }

}
