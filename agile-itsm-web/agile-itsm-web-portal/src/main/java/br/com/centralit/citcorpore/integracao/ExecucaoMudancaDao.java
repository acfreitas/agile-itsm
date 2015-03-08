package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ExecucaoMudancaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class ExecucaoMudancaDao extends CrudDaoDefaultImpl {
	public ExecucaoMudancaDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idExecucao" ,"idExecucao", true, true, false, false));
		listFields.add(new Field("idRequisicaoMudanca" ,"idRequisicaoMudanca", false, false, false, false));
		listFields.add(new Field("idFluxo" ,"idFluxo", false, false, false, false));
		listFields.add(new Field("idInstanciaFluxo" ,"idInstanciaFluxo", false, false, false, false));
		listFields.add(new Field("seqReabertura" ,"seqReabertura", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "ExecucaoMudanca";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return ExecucaoMudancaDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	@SuppressWarnings("rawtypes")
	public ExecucaoMudancaDTO findByIdInstanciaFluxo(Integer idInstanciaFluxo) throws PersistenceException {
		
		List condicao = new ArrayList();
		//System.out.println("\n --> " + idInstanciaFluxo + " \n");
		condicao.add(new Condition("idInstanciaFluxo", "=", idInstanciaFluxo));
		
		Collection col = super.findByCondition(condicao, null);
		if (col == null || col.size() == 0) return null;
		return (ExecucaoMudancaDTO) ((List) col).get(0);
	}		
	public Collection<ExecucaoMudancaDTO> listByIdRequisicaoMudanca(Integer idRequisicaoMudanca) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idRequisicaoMudanca", "=", idRequisicaoMudanca));
		ordenacao.add(new Order("idExecucao", Order.DESC));
		return super.findByCondition(condicao, ordenacao);
	}		
}
