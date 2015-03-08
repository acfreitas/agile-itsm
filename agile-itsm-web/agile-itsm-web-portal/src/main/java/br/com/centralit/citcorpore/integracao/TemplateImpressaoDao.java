package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.TemplateImpressaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class TemplateImpressaoDao extends CrudDaoDefaultImpl {
	public TemplateImpressaoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idTemplateImpressao" ,"idTemplateImpressao", true, true, false, false));
		listFields.add(new Field("nomeTemplate" ,"nomeTemplate", false, false, false, false));
		listFields.add(new Field("htmlCabecalho" ,"htmlCabecalho", false, false, false, false));
		listFields.add(new Field("htmlCorpo" ,"htmlCorpo", false, false, false, false));
		listFields.add(new Field("htmlRodape" ,"htmlRodape", false, false, false, false));
		listFields.add(new Field("idTipoTemplateImp" ,"idTipoTemplateImp", false, false, false, false));
		listFields.add(new Field("tamCabecalho" ,"tamCabecalho", false, false, false, false));
		listFields.add(new Field("tamRodape" ,"tamRodape", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "TemplateImpressao";
	}
	public Collection list() throws PersistenceException {
		return super.list("nomeTemplate");
	}

	public Class getBean() {
		return TemplateImpressaoDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public Collection findByIdTipoTemplateImp(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idTipoTemplateImp", "=", parm)); 
		ordenacao.add(new Order("nomeTemplate"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdTipoTemplateImp(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idTipoTemplateImp", "=", parm));
		super.deleteByCondition(condicao);
	}
}
