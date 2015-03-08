package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.TipoOSDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class TipoOSDao extends CrudDaoDefaultImpl {
	public TipoOSDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idClassificacaoOS" ,"idClassificacaoOS", true, true, false, false));
		listFields.add(new Field("idContrato" ,"idContrato", false, false, false, false));
		listFields.add(new Field("descricao" ,"descricao", false, false, false, false));
		listFields.add(new Field("detalhamento" ,"detalhamento", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "TipoOS";
	}
	public Collection list() throws PersistenceException {
		return super.list("descricao");
	}

	public Class getBean() {
		return TipoOSDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public Collection findByIdContrato(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idContrato", "=", parm)); 
		ordenacao.add(new Order("descricao"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdContrato(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idContrato", "=", parm));
		super.deleteByCondition(condicao);
	}
}
