package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.EventoEmpregadoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

public class EventoEmpregadoDao extends CrudDaoDefaultImpl {

    public EventoEmpregadoDao() {
	super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    public Class getBean() {
	return EventoEmpregadoDTO.class;
    }

    public Collection<Field> getFields() {
	Collection<Field> listFields = new ArrayList<>();

	listFields.add(new Field("IDEVENTO", "idEvento", true, false, false, false));
	listFields.add(new Field("IDEMPREGADO", "idEmpregado", true, false, false, false));
	listFields.add(new Field("IDUNIDADE", "idUnidade", false, false, false, false));
	listFields.add(new Field("IDGRUPO", "idGrupo", false, false, false, false));
	listFields.add(new Field("IDITEMCONFIGURACAOPAI", "idItemConfiguracaoPai", false, false, false, false));

	return listFields;
    }

    public String getTableName() {
	return "EVENTOEMPREGADO";
    }

    public Collection find(IDto obj) throws PersistenceException {
	return null;
    }

    public Collection list() throws PersistenceException {
	return null;
    }

    public Collection<EventoEmpregadoDTO> listByIdEvento(Integer idEvento) throws PersistenceException {
	String sql = "select distinct idempregado, iditemconfiguracaopai from " + getTableName() + " where idevento = ?";
	List dados = this.execSQL(sql, new Object[] { idEvento });
	List fields = new ArrayList();
	fields.add("idEmpregado");
	fields.add("idItemConfiguracaoPai");
	return this.listConvertion(getBean(), dados, fields);
    }

    public Collection<EventoEmpregadoDTO> listByIdEventoGrupo(Integer idEvento) throws PersistenceException {
	String sql = "select distinct ee.idgrupo, g.nome from " + getTableName() + " ee join grupo g on g.idgrupo = ee.idgrupo " + "where ee.idevento = ? and ee.idgrupo is not null";
	List dados = this.execSQL(sql, new Object[] { idEvento });
	List fields = new ArrayList();
	fields.add("idGrupo");
	fields.add("nome");
	return this.listConvertion(getBean(), dados, fields);
    }

    public Collection<EventoEmpregadoDTO> listByIdEventoUnidade(Integer idEvento) throws PersistenceException {
	String sql = "select distinct ee.idunidade, u.nome from " + getTableName() + " ee join unidade u on u.idunidade = ee.idunidade "
		+ "where ee.idevento = ? and ee.idunidade is not null";
	List dados = this.execSQL(sql, new Object[] { idEvento });
	List fields = new ArrayList();
	fields.add("idUnidade");
	fields.add("nome");
	return this.listConvertion(getBean(), dados, fields);
    }

    public Collection<EventoEmpregadoDTO> listByIdEventoEmpregado(Integer idEvento) throws PersistenceException {
	String sql = "select e.idempregado, e.nome from " + getTableName() + " ee join empregados e on e.idempregado = ee.idempregado "
		+ "where ee.idevento = ? and ee.idgrupo is null and ee.idunidade is null";
	List dados = this.execSQL(sql, new Object[] { idEvento });
	List fields = new ArrayList();
	fields.add("idEmpregado");
	fields.add("nome");
	return this.listConvertion(getBean(), dados, fields);
    }

    public void deleteByIdEvento(Integer idEvento) throws PersistenceException {
	List lstCondicao = new ArrayList();
	lstCondicao.add(new Condition(Condition.AND, "idEvento", "=", idEvento));
	super.deleteByCondition(lstCondicao);
    }
    
}
