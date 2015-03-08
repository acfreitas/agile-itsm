package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ExcecaoEmpregadoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

public class ExcecaoEmpregadoDao extends CrudDaoDefaultImpl {

	public ExcecaoEmpregadoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	public Class getBean() {
		return ExcecaoEmpregadoDTO.class;
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		
		listFields.add(new Field("IDEVENTO", "idEvento", true, false, false, false));
		listFields.add(new Field("IDEMPREGADO", "idEmpregado", true, false, false, false));
		listFields.add(new Field("IDGRUPO", "idGrupo", false, false, false, false));
		listFields.add(new Field("IDUNIDADE", "idUnidade", false, false, false, false));
		
		return listFields;
	}

	public String getTableName() {
		return "EXCECAOEMPREGADO";
	}

	public Collection find(IDto obj) throws PersistenceException {
		return null;
	}

	public Collection list() throws PersistenceException {
		return null;
	}
	
	public Collection<ExcecaoEmpregadoDTO> listByIdEventoIdGrupo(Integer idEvento, Integer idGrupo) throws PersistenceException {
		String sql = "select e.idempregado, e.nome from " + getTableName() + " ee join empregados e on e.idempregado = ee.idempregado where ee.idevento = ? and ee.idgrupo = ?";
		List dados = this.execSQL(sql, new Object[]{ idEvento, idGrupo });
    	List fields = new ArrayList();
    	fields.add("idEmpregado");
    	fields.add("nomeEmpregado");
    	return this.listConvertion(getBean(), dados, fields);
	}
	
	public Collection<ExcecaoEmpregadoDTO> listByIdEventoIdUnidade(Integer idEvento, Integer idUnidade) throws PersistenceException {
		String sql = "select e.idempregado, e.nome from " + getTableName() + " ee join empregados e on e.idempregado = ee.idempregado where ee.idevento = ? and ee.idunidade = ?";
		List dados = this.execSQL(sql, new Object[]{ idEvento, idUnidade });
    	List fields = new ArrayList();
    	fields.add("idEmpregado");
    	fields.add("nomeEmpregado");
    	return this.listConvertion(getBean(), dados, fields);
	}
	
	public void deleteByIdEvento(Integer idEvento) throws PersistenceException {
		List lstCondicao = new ArrayList();
		lstCondicao.add(new Condition(Condition.AND, "idEvento", "=", idEvento));
		super.deleteByCondition(lstCondicao);
	}

}
