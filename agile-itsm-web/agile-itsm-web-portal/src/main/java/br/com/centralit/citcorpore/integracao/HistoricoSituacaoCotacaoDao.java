package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.HistoricoSituacaoCotacaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class HistoricoSituacaoCotacaoDao extends CrudDaoDefaultImpl {
	public HistoricoSituacaoCotacaoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idHistorico" ,"idHistorico", true, true, false, false));
		listFields.add(new Field("idCotacao" ,"idCotacao", false, false, false, false));
		listFields.add(new Field("idResponsavel" ,"idResponsavel", false, false, false, false));
		listFields.add(new Field("dataHora" ,"dataHora", false, false, false, false));
		listFields.add(new Field("situacao" ,"situacao", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "HistoricoSituacaoCotacao";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return HistoricoSituacaoCotacaoDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public Collection findByIdCotacao(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idCotacao", "=", parm)); 
		ordenacao.add(new Order("idResponsavel"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdCotacao(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idCotacao", "=", parm));
		super.deleteByCondition(condicao);
	}
	public Collection findByIdResponsavel(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idResponsavel", "=", parm)); 
		ordenacao.add(new Order("idCotacao"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdResponsavel(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idResponsavel", "=", parm));
		super.deleteByCondition(condicao);
	}
}
