package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ReaberturaMudancaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class ReaberturaMudancaDao extends CrudDaoDefaultImpl {
	public ReaberturaMudancaDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idrequisicaomudanca" ,"idRequisicaoMudanca", true, false, false, false));
		listFields.add(new Field("seqreabertura" ,"seqReabertura", true, false, false, false));
		listFields.add(new Field("idresponsavel" ,"idResponsavel", false, false, false, false));
		listFields.add(new Field("datahora" ,"dataHora", false, false, false, false));
		listFields.add(new Field("observacoes" ,"observacoes", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "ReaberturaMudanca";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return ReaberturaMudancaDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public Collection findByIdRequisicaoMudanca(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idRequisicaoMudanca", "=", parm)); 
		ordenacao.add(new Order("seqReabertura"));
		return super.findByCondition(condicao, ordenacao);
	}
}
