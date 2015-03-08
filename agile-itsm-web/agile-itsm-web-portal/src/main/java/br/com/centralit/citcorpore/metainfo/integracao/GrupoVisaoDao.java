package br.com.centralit.citcorpore.metainfo.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.metainfo.bean.GrupoVisaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class GrupoVisaoDao extends CrudDaoDefaultImpl {
	public GrupoVisaoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		final List<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idGrupoVisao" ,"idGrupoVisao", true, true, false, false));
		listFields.add(new Field("idVisao" ,"idVisao", false, false, false, false));
		listFields.add(new Field("descricaoGrupoVisao" ,"descricaoGrupoVisao", false, false, false, false));
		listFields.add(new Field("forma" ,"forma", false, false, false, false));
		listFields.add(new Field("ordem" ,"ordem", false, false, false, false));
		listFields.add(new Field("situacao" ,"situacao", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "GrupoVisao";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return GrupoVisaoDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public Collection findByIdVisao(Integer idVisao) throws Exception {
		List lstCond = new ArrayList();
		List lstOrder = new ArrayList();
		
		lstCond.add(new Condition("idVisao", "=", idVisao));
		lstOrder.add(new Order("ordem"));
		
		return super.findByCondition(lstCond, lstOrder);
	}	
	public Collection findByIdVisaoAtivos(Integer idVisao) throws Exception {
		List lstCond = new ArrayList();
		List lstOrder = new ArrayList();
		
		lstCond.add(new Condition("idVisao", "=", idVisao));
		lstCond.add(new Condition("situacao", "=", "A"));
		lstOrder.add(new Order("ordem"));
		
		return super.findByCondition(lstCond, lstOrder);
	}
	public void deleteByIdVisao(Integer idVisaoParm) throws Exception{
		List condicao = new ArrayList();
		condicao.add(new Condition("idVisao", "=", idVisaoParm));
		super.deleteByCondition(condicao);
	}
}
