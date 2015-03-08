package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.AtitudeOrganizacionalDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class AtitudeOrganizacionalDao extends CrudDaoDefaultImpl {

    public AtitudeOrganizacionalDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<AtitudeOrganizacionalDTO> find(final IDto arg0) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idAtitudeOrganizacional", "idAtitudeOrganizacional", true, true, false, false));
        listFields.add(new Field("descricao", "descricao", false, false, false, false));
        listFields.add(new Field("detalhe", "detalhe", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return "RH_AtitudeOrganizacional";
    }

    @Override
    public Class<AtitudeOrganizacionalDTO> getBean() {
        return AtitudeOrganizacionalDTO.class;
    }

    @Override
    public Collection<AtitudeOrganizacionalDTO> list() throws PersistenceException {
        final List list = new ArrayList();
        list.add(new Order("descricao"));
        return super.list(list);
    }

}
