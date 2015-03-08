package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.FaixaValoresRecursoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class FaixaValoresRecursoDao extends CrudDaoDefaultImpl {
	public FaixaValoresRecursoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idFaixaValoresRecurso" ,"idFaixaValoresRecurso", true, true, false, false));
		listFields.add(new Field("idRecurso" ,"idRecurso", false, false, false, false));
		listFields.add(new Field("valorInicio" ,"valorInicio", false, false, false, false));
		listFields.add(new Field("valorFim" ,"valorFim", false, false, false, false));
		listFields.add(new Field("cor" ,"cor", false, false, false, false));
		listFields.add(new Field("descricao" ,"descricao", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "FaixaValoresRecurso";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return FaixaValoresRecursoDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public Collection findByIdRecurso(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idRecurso", "=", parm)); 
		ordenacao.add(new Order("descricao"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdRecurso(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idRecurso", "=", parm));
		super.deleteByCondition(condicao);
	}
}
