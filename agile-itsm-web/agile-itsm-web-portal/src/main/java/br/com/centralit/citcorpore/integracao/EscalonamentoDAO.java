package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.EscalonamentoDTO;
import br.com.centralit.citcorpore.bean.RegraEscalonamentoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class EscalonamentoDAO extends CrudDaoDefaultImpl {

	public EscalonamentoDAO(){
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Collection find(IDto obj) throws PersistenceException {
		return null;
	}

	@Override
	public Collection<Field> getFields() {
		
		Collection<Field> listFields = new ArrayList<>();
		
		listFields.add(new Field("idescalonamento", "idEscalonamento", true, true, false, false));
		listFields.add(new Field("idregraescalonamento", "idRegraEscalonamento", false, false, false, false));
		listFields.add(new Field("idgrupoexecutor", "idGrupoExecutor", false, false, false, false));
		listFields.add(new Field("prazoexecucao", "prazoExecucao", false, false, false, false));
		listFields.add(new Field("idprioridade", "idPrioridade", false, false, false, false));
		listFields.add(new Field("datainicio", "dataInicio", false, false, false, false));
		listFields.add(new Field("datafim", "dataFim", false, false, false, false));

		return listFields;
	}

	@Override
	public String getTableName() {
		return "ESCALONAMENTO";
	}

	@Override
	public Collection list() throws PersistenceException{
		List list = new ArrayList();
		list.add(new Order("idGrupoExecutor"));
		return super.list(list);
	    }


	@Override
	public Class getBean() {
		return EscalonamentoDTO.class;
	}
	
	public Collection findByRegraEscalonamento(RegraEscalonamentoDTO regraEscalonamentoDTO) throws PersistenceException {
		List campos = new ArrayList();
		List param = new ArrayList();
		List list = new ArrayList();
		param.add(regraEscalonamentoDTO.getIdRegraEscalonamento());
		param.add(regraEscalonamentoDTO.getIdTipoGerenciamento());
		StringBuilder sql = new StringBuilder(); 
		sql.append("SELECT idescalonamento, escalonamento.idregraescalonamento, idgrupoexecutor, prazoexecucao, nome as descricao, escalonamento.datainicio, escalonamento.idprioridade, nomeprioridade AS descrprioridade ");
		sql.append("FROM escalonamento JOIN grupo ON idgrupo = idgrupoexecutor AND idRegraEscalonamento = ? AND (escalonamento.datafim is null) ");
		sql.append("INNER JOIN regraescalonamento ON escalonamento.idregraescalonamento = regraescalonamento.idregraescalonamento ");
		sql.append("LEFT JOIN prioridade ON escalonamento.idprioridade = prioridade.idprioridade ");
		sql.append(" where regraescalonamento.idtipogerenciamento = ? ");
		sql.append("order by prazoexecucao");
		
		list = this.execSQL(sql.toString(), param.toArray());
		campos.add("idEscalonamento");
		campos.add("idRegraEscalonamento");
		campos.add("idGrupoExecutor");
		campos.add("prazoExecucao");
		campos.add("descricao");
		campos.add("dataInicio");
		campos.add("idPrioridade");
		campos.add("descrPrioridade");
		
		if (list.isEmpty()) {
			return null;
		} else {
			return this.listConvertion(getBean(), list, campos);
		}
	}
	
	public void deleteByIdRegraEscalonamento(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idRegraEscalonamento", "=", parm));
		super.deleteByCondition(condicao);
	}
	
	public void gravarDataFim(Integer parm) throws PersistenceException {
		super.execUpdate("update escalonamento set datafim=? where idregraescalonamento=? AND (datafim is null)", new Object[]{UtilDatas.getDataAtual(), parm});
	}
}