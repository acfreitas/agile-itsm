package br.com.centralit.citcorpore.metainfo.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.metainfo.bean.HtmlCodeVisaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class HtmlCodeVisaoDao extends CrudDaoDefaultImpl {
	public HtmlCodeVisaoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		final List<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idHtmlCodeVisao" ,"idHtmlCodeVisao", true, true, false, false));
		listFields.add(new Field("idVisao" ,"idVisao", false, false, false, false));
		listFields.add(new Field("htmlCodeType" ,"htmlCodeType", false, false, false, false));
		listFields.add(new Field("htmlCode" ,"htmlCode", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "HtmlCodeVisao";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return HtmlCodeVisaoDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public Collection findByIdVisao(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idVisao", "=", parm)); 
		ordenacao.add(new Order("htmlCodeType"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdVisao(Integer parm) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idVisao", "=", parm));
		super.deleteByCondition(condicao);
	}
}
