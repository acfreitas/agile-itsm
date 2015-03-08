package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ProcessoNivelAutoridadeDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class ProcessoNivelAutoridadeDao extends CrudDaoDefaultImpl {
	public ProcessoNivelAutoridadeDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idProcessoNegocio" ,"idProcessoNegocio", true, false, false, false));
		listFields.add(new Field("idNivelAutoridade" ,"idNivelAutoridade", true, false, false, false));
		listFields.add(new Field("permiteAprovacaoPropria" ,"permiteAprovacaoPropria", false, false, false, false));
		listFields.add(new Field("permiteSolicitacao" ,"permiteSolicitacao", false, false, false, false));
		listFields.add(new Field("antecedenciaMinimaAprovacao" ,"antecedenciaMinimaAprovacao", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "ProcessoNivelAutoridade";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return ProcessoNivelAutoridadeDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public Collection findByIdProcessoNegocio(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idProcessoNegocio", "=", parm)); 
		ordenacao.add(new Order("idNivelAutoridade"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdProcessoNegocio(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idProcessoNegocio", "=", parm));
		super.deleteByCondition(condicao);
	}
}
