package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.TelefoneDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

public class TelefoneDao extends CrudDaoDefaultImpl {

    public TelefoneDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<TelefoneDTO> find(final IDto arg0) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final List<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idTelefone", "idTelefone", true, true, false, false));
        listFields.add(new Field("idTipoTelefone", "idTipoTelefone", false, false, false, false));
        listFields.add(new Field("ddd", "ddd", false, false, false, false));
        listFields.add(new Field("numeroTelefone", "numeroTelefone", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return "RH_Telefone";
    }

    @Override
    public Collection<TelefoneDTO> list() throws PersistenceException {
        return null;
    }

    @Override
    public Class<TelefoneDTO> getBean() {
        return TelefoneDTO.class;
    }

}
