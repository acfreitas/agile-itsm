package br.com.centralit.citcorpore.integracao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ExecucaoAtividadePeriodicaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class ExecucaoAtividadePeriodicaDao extends CrudDaoDefaultImpl {
	public ExecucaoAtividadePeriodicaDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idExecucaoAtividadePeriodica" ,"idExecucaoAtividadePeriodica", true, true, false, false));
		listFields.add(new Field("idAtividadePeriodica" ,"idAtividadePeriodica", false, false, false, false));
        listFields.add(new Field("idProgramacaoAtividade" ,"idProgramacaoAtividade", false, false, false, false));
		listFields.add(new Field("dataProgramada" ,"dataProgramada", false, false, false, false));
		listFields.add(new Field("horaProgramada" ,"horaProgramada", false, false, false, false));
		listFields.add(new Field("situacao" ,"situacao", false, false, false, false));
		listFields.add(new Field("detalhamento" ,"detalhamento", false, false, false, false));
		listFields.add(new Field("usuario" ,"usuario", false, false, false, false));
		listFields.add(new Field("idEmpregado" ,"idEmpregado", false, false, false, false));
		listFields.add(new Field("dataExecucao" ,"dataExecucao", false, false, false, false));
		listFields.add(new Field("horaExecucao" ,"horaExecucao", false, false, false, false));
		listFields.add(new Field("dataRegistro" ,"dataRegistro", false, false, false, false));
		listFields.add(new Field("horaRegistro" ,"horaRegistro", false, false, false, false));
        listFields.add(new Field("idMotivoSuspensao" ,"idMotivoSuspensao", false, false, false, false));
        listFields.add(new Field("complementoMotivoSuspensao" ,"complementoMotivoSuspensao", false, false, false, false));
		
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "ExecucaoAtividadePeriodica";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return ExecucaoAtividadePeriodicaDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public Collection findByAtvDataHora(Integer idAtividadePeriodicaParm, Date dataProgramadaParm, String horaProgramadaParm, Integer idProgramacaoAtividade) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		
		condicao.add(new Condition("idAtividadePeriodica", "=", idAtividadePeriodicaParm));
		condicao.add(new Condition("dataProgramada", "=", dataProgramadaParm));
		condicao.add(new Condition("horaProgramada", "=", horaProgramadaParm));
		if (idProgramacaoAtividade != null)
	        condicao.add(new Condition("idProgramacaoAtividade", "=", idProgramacaoAtividade));
		
		ordenacao.add(new Order("idExecucaoAtividadePeriodica"));
		return super.findByCondition(condicao, ordenacao);
	}
	public Collection findByIdAtividadePeriodica(Integer idAtividadePeriodicaParm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		
		condicao.add(new Condition("idAtividadePeriodica", "=", idAtividadePeriodicaParm));
		
		ordenacao.add(new Order("idExecucaoAtividadePeriodica"));
		return super.findByCondition(condicao, ordenacao);
	}	
	public void deleteByIdAtividadePeriodica(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idAtividadePeriodica", "=", parm));
		super.deleteByCondition(condicao);
	}
	public void deleteByIdEmpregado(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idEmpregado", "=", parm));
		super.deleteByCondition(condicao);
	}
	public Collection findBlackoutByIdMudancaAndPeriodo(Integer idMudanca, Date dataInicio, Date dataFim) throws PersistenceException {
		String sql = "select " + this.getNamesFieldsStr("ex") + " from " + this.getTableName() + " ex ";
		sql += " inner join atividadeperiodica ag on ag.idatividadeperiodica = ex.idatividadeperiodica ";
		sql += " where idrequisicaomudanca = ? and dataexecucao between ? and ? and ag.blackout = 'S' order by dataexecucao, horaexecucao";
		List parametros = new ArrayList();
		parametros.add(idMudanca);
		parametros.add(dataInicio);
		parametros.add(dataFim);
		List lista = this.execSQL(sql, parametros.toArray());
		return this.listConvertion(this.getBean(), lista, this.getListNamesFieldClass());
	}
}
