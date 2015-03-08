package br.com.centralit.citcorpore.metainfo.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.metainfo.bean.VisaoRelacionadaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class VisaoRelacionadaDao extends CrudDaoDefaultImpl {
	public VisaoRelacionadaDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		final Collection<Field> listFields = new ArrayList<>();
		
		listFields.add(new Field("idVisaoRelacionada" ,"idVisaoRelacionada", true, true, false, false));
		listFields.add(new Field("idVisaoPai" ,"idVisaoPai", false, false, false, false));
		listFields.add(new Field("idVisaoFilha" ,"idVisaoFilha", false, false, false, false));
		listFields.add(new Field("ordem" ,"ordem", false, false, false, false));
		listFields.add(new Field("titulo" ,"titulo", false, false, false, false));
		listFields.add(new Field("situacao" ,"situacao", false, false, false, false));
		listFields.add(new Field("tipoVinculacao" ,"tipoVinculacao", false, false, false, false));
		listFields.add(new Field("acaoEmSelecaoPesquisa" ,"acaoEmSelecaoPesquisa", false, false, false, false));
		listFields.add(new Field("idObjetoNegocioNN" ,"idObjetoNegocioNN", false, false, false, false));
		
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "VisaoRelacionada";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return VisaoRelacionadaDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public Collection findByIdVisaoPaiAtivos(Integer idVisaoPai) throws Exception {
		List lstCond = new ArrayList();
		List lstOrder = new ArrayList();
		
		lstCond.add(new Condition("idVisaoPai", "=", idVisaoPai));
		lstCond.add(new Condition("situacao", "=", "A"));
		lstOrder.add(new Order("ordem"));
		
		return super.findByCondition(lstCond, lstOrder);
	}
	public void deleteByIdVisaoPai(Integer idVisaoParm) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idVisaoPai", "=", idVisaoParm));
		super.deleteByCondition(condicao);
	}	
}
