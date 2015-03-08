package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.AvaliacaoColetaPrecoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class AvaliacaoColetaPrecoDao extends CrudDaoDefaultImpl {
	public AvaliacaoColetaPrecoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idCriterio" ,"idCriterio", true, false, false, false));
		listFields.add(new Field("idColetaPreco" ,"idColetaPreco", true, false, false, false));
		listFields.add(new Field("avaliacao" ,"avaliacao", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "AvaliacaoColetaPreco";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return AvaliacaoColetaPrecoDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public Collection findByIdColetaPreco(Integer idColetaPreco) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idColetaPreco", "=", idColetaPreco));
		ordenacao.add(new Order("idCriterio"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdColetaPreco(Integer idColetaPreco) throws PersistenceException {
		List condicao = new ArrayList();
        condicao.add(new Condition("idColetaPreco", "=", idColetaPreco));
		super.deleteByCondition(condicao);
	}
}
