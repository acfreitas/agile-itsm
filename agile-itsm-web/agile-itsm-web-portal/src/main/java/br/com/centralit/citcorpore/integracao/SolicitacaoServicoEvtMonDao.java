package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.SolicitacaoServicoEvtMonDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class SolicitacaoServicoEvtMonDao extends CrudDaoDefaultImpl {
	public SolicitacaoServicoEvtMonDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idSolicitacaoServico" ,"idSolicitacaoServico", true, false, false, false));
		listFields.add(new Field("idEventoMonitoramento" ,"idEventoMonitoramento", true, false, false, false));
		listFields.add(new Field("idRecurso" ,"idRecurso", false, false, false, false));
		listFields.add(new Field("nomeHost" ,"nomeHost", false, false, false, false));
		listFields.add(new Field("nomeService" ,"nomeService", false, false, false, false));
		listFields.add(new Field("infoAdd" ,"infoAdd", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "SolicitacaoServicoEvtMon";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return SolicitacaoServicoEvtMonDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public Collection findByIdSolicitacao(Integer idSolicitacaoServico) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		
		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacaoServico));
		ordenacao.add(new Order("idEventoMonitoramento"));
		
		return super.findByCondition(condicao, ordenacao);
	}
	public Collection findByIdRecursoAndSolicitacaoAberta(Integer idRecurso) throws PersistenceException {
		List parametros = new ArrayList();
		parametros.add(idRecurso);
		parametros.add(idRecurso);
		String sql = "select " + this.getNamesFieldsStr() + " from " + this.getTableName();
		sql += " where idRecurso = ? and idSolicitacaoServico in (select idSolicitacaoServico from solicitacaoservico where idsolicitacaoservico in (select idSolicitacaoServico from " + this.getTableName() + " where idRecurso = ?) and UPPER(situacao) <> 'FECHADA')";
		List lstDados = super.execSQL(sql, parametros.toArray());
		return super.listConvertion(getBean(), lstDados, this.getListNamesFieldClass());
	}	
}
