package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import br.com.centralit.citcorpore.bean.BIDashBoardDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class BIDashBoardDao extends CrudDaoDefaultImpl {
	public BIDashBoardDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idDashBoard" ,"idDashBoard", true, true, false, false));
		listFields.add(new Field("nomeDashBoard" ,"nomeDashBoard", false, false, false, false));
		listFields.add(new Field("tipo" ,"tipo", false, false, false, false));
		listFields.add(new Field("identificacao" ,"identificacao", false, false, false, false));
		listFields.add(new Field("situacao" ,"situacao", false, false, false, false));
		listFields.add(new Field("idUsuario" ,"idUsuario", false, false, false, false));
		listFields.add(new Field("parametros" ,"parametros", false, false, false, false));
		listFields.add(new Field("naoAtualizBase" ,"naoAtualizBase", false, false, false, false));
		listFields.add(new Field("tempoRefresh" ,"tempoRefresh", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "BI_DashBoard";
	}
	public Collection list() throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		
		condicao.add(new Condition("situacao", "=", "A"));
		ordenacao.add(new Order("nomeDashBoard"));
		return super.findByCondition(condicao, ordenacao);
	}

	public Class getBean() {
		return BIDashBoardDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public IDto getByIdentificacao(String ident) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		
		condicao.add(new Condition("identificacao", "=", ident));
		condicao.add(new Condition("situacao", "=", "A"));
		ordenacao.add(new Order("idDashBoard"));
		Collection col = super.findByCondition(condicao, ordenacao);
		if (col != null){
			for (Iterator it = col.iterator(); it.hasNext();){
				IDto ret = (IDto) it.next();
				return ret;
			}
		}
		return null;
	}	
}
