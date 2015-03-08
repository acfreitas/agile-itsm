package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.DelegacaoCentroResultadoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class DelegacaoCentroResultadoDao extends CrudDaoDefaultImpl {
	public DelegacaoCentroResultadoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idDelegacaoCentroResultado" ,"idDelegacaoCentroResultado", true, true, false, false));
		listFields.add(new Field("idResponsavel" ,"idResponsavel", false, false, false, false));
		listFields.add(new Field("idCentroResultado" ,"idCentroResultado", false, false, false, false));
		listFields.add(new Field("idEmpregado" ,"idEmpregado", false, false, false, false));
		listFields.add(new Field("idResponsavelRegistro" ,"idResponsavelRegistro", false, false, false, false));
		listFields.add(new Field("idResponsavelRevogacao" ,"idResponsavelRevogacao", false, false, false, false));
		listFields.add(new Field("dataHoraRegistro" ,"dataHoraRegistro", false, false, false, false));
		listFields.add(new Field("dataInicio" ,"dataInicio", false, false, false, false));
		listFields.add(new Field("dataFim" ,"dataFim", false, false, false, false));
		listFields.add(new Field("abrangencia" ,"abrangencia", false, false, false, false));
		listFields.add(new Field("revogada" ,"revogada", false, false, false, false));
		listFields.add(new Field("dataHoraRevogacao" ,"dataHoraRevogacao", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "DelegacaoCentroResultado";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return DelegacaoCentroResultadoDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	
	public Collection findByIdResponsavelAndIdCentroResultado(Integer idResponsavel, Integer idCentroResultado) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idResponsavel", "=", idResponsavel)); 
		condicao.add(new Condition("idCentroResultado", "=", idCentroResultado)); 
		ordenacao.add(new Order("dataInicio",Order.DESC));
		ordenacao.add(new Order("dataFim",Order.DESC));
		return super.findByCondition(condicao, ordenacao);
	}
}
