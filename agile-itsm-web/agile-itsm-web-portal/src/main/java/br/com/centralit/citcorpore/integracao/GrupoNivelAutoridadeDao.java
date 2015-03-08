package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.GrupoNivelAutoridadeDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class GrupoNivelAutoridadeDao extends CrudDaoDefaultImpl {
	public GrupoNivelAutoridadeDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idNivelAutoridade" ,"idNivelAutoridade", true, false, false, false));
		listFields.add(new Field("idGrupo" ,"idGrupo", true, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "GrupoNivelAutoridade";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return GrupoNivelAutoridadeDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public Collection findByIdNivelAutoridade(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idNivelAutoridade", "=", parm)); 
		ordenacao.add(new Order("idGrupo"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdNivelAutoridade(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idNivelAutoridade", "=", parm));
		super.deleteByCondition(condicao);
	}
}
