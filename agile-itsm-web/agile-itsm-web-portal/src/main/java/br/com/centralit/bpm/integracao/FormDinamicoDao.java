package br.com.centralit.bpm.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.bpm.dto.FormDinamicoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class FormDinamicoDao extends CrudDaoDefaultImpl {

    public FormDinamicoDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<Field>();
        listFields.add(new Field("idVisao", "idVisao", true, true, false, false));
        listFields.add(new Field("descricao", "descricao", false, false, false, false));
        listFields.add(new Field("tipoVisao", "tipoVisao", false, false, false, false));
        listFields.add(new Field("situacao", "situacao", false, false, false, false));
        listFields.add(new Field("classeName", "classeName", false, false, false, false));
        listFields.add(new Field("identificador", "identificador", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return this.getOwner() + "Visao";
    }

    @Override
    public Collection<FormDinamicoDTO> list() throws PersistenceException {
        return null;
    }

    public FormDinamicoDTO findByIdentificador(final String identificador) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();

        condicao.add(new Condition("identificador", "=", identificador));
        ordenacao.add(new Order("descricao"));

        final List<FormDinamicoDTO> result = (List<FormDinamicoDTO>) super.findByCondition(condicao, ordenacao);
        if (result != null) {
            return result.get(0);
        }
        return null;
    }

    @Override
    public Class<FormDinamicoDTO> getBean() {
        return FormDinamicoDTO.class;
    }

    @Override
    public Collection<FormDinamicoDTO> find(final IDto arg0) throws PersistenceException {
        return null;
    }

}
