package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.AprovacaoRequisicaoLiberacaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class AprovacaoRequisicaoLiberacaoDao extends CrudDaoDefaultImpl {
	public AprovacaoRequisicaoLiberacaoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idAprovacaoRequisicaoLiberacao" ,"idAprovacaoRequisicaoLiberacao", true, true, false, false));
		listFields.add(new Field("idRequisicaoLiberacao" ,"idRequisicaoLiberacao", false, false, false, false));
		listFields.add(new Field("idResponsavel" ,"idResponsavel", false, false, false, false));
		listFields.add(new Field("idTarefa" ,"idTarefa", false, false, false, false));
		listFields.add(new Field("idJustificativa" ,"idJustificativa", false, false, false, false));
		listFields.add(new Field("dataHora" ,"dataHora", false, false, false, false));
		listFields.add(new Field("complementoJustificativa" ,"complementoJustificativa", false, false, false, false));
		listFields.add(new Field("observacoes" ,"observacoes", false, false, false, false));
		listFields.add(new Field("aprovacao" ,"aprovacao", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "AprovacaoRequisicaoLiberacao";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return AprovacaoRequisicaoLiberacaoDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public Collection findByIdSolicitacaoServico(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idRequisicaoLiberacao", "=", parm)); 
		ordenacao.add(new Order("idParecer"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdSolicitacaoServico(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idRequisicaoLiberacao", "=", parm));
		super.deleteByCondition(condicao);
	}
}

