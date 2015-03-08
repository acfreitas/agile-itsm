package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.HistoricoRespCentroResultadoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class HistoricoRespCentroResultadoDao extends CrudDaoDefaultImpl {
	public HistoricoRespCentroResultadoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idHistoricoRespCentroResultado" ,"idHistoricoRespCentroResultado", true, true, false, false));
		listFields.add(new Field("idResponsavel" ,"idResponsavel", false, false, false, false));
		listFields.add(new Field("idCentroResultado" ,"idCentroResultado", false, false, false, false));
		listFields.add(new Field("dataInicio" ,"dataInicio", false, false, false, false));
		listFields.add(new Field("dataFim" ,"dataFim", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "HistoricoRespCentroResultado";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return HistoricoRespCentroResultadoDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	
	public Collection findAtuaisByIdCentroResultado(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idCentroResultado", "=", parm)); 
		condicao.add(new Condition("dataFim", "is", null));
		ordenacao.add(new Order("idResponsavel"));
		return super.findByCondition(condicao, ordenacao);
	}
	
	public Collection findByIdCentroResultado(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idCentroResultado", "=", parm)); 
		ordenacao.add(new Order("dataInicio"));
		ordenacao.add(new Order("idResponsavel"));
		return super.findByCondition(condicao, ordenacao);
	}

}
