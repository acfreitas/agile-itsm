package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ProgramacaoAtividadeDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class ProgramacaoAtividadeDao extends CrudDaoDefaultImpl {
	public ProgramacaoAtividadeDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
        Collection<Field> listFields = new ArrayList<>();
        
        listFields.add(new Field("idProgramacaoAtividade" ,"idProgramacaoAtividade", true, true, false, false));
        listFields.add(new Field("idAtividadePeriodica" ,"idAtividadePeriodica", false, false, false, false));
        listFields.add(new Field("idAtividadesOs" ,"idAtividadesOs", false, false, false, false));
	    listFields.add(new Field("tipoAgendamento","tipoAgendamento", false, false, false, false));	    
	    listFields.add(new Field("dataInicio","dataInicio", false, false, false, false));
	    listFields.add(new Field("dataFim","dataFim", false, false, false, false));
	    listFields.add(new Field("duracaoEstimada","duracaoEstimada", false, false, false, false));
	    listFields.add(new Field("periodicidadeDiaria","periodicidadeDiaria", false, false, false, false));
        listFields.add(new Field("periodicidadeSemanal","periodicidadeSemanal", false, false, false, false));
        listFields.add(new Field("periodicidadeMensal","periodicidadeMensal", false, false, false, false));
        listFields.add(new Field("dia","dia", false, false, false, false));
        listFields.add(new Field("diaUtil","diaUtil", false, false, false, false));
        listFields.add(new Field("diaSemana","diaSemana", false, false, false, false));
        listFields.add(new Field("seqDiaSemana","seqDiaSemana", false, false, false, false));
	    listFields.add(new Field("seg","seg", false, false, false, false));
	    listFields.add(new Field("ter","ter", false, false, false, false));
	    listFields.add(new Field("qua","qua", false, false, false, false));
	    listFields.add(new Field("qui","qui", false, false, false, false));
	    listFields.add(new Field("sex","sex", false, false, false, false));
	    listFields.add(new Field("sab","sab", false, false, false, false));
	    listFields.add(new Field("dom","dom", false, false, false, false));
	    listFields.add(new Field("jan","jan", false, false, false, false));
	    listFields.add(new Field("fev","fev", false, false, false, false));
	    listFields.add(new Field("mar","mar", false, false, false, false));
	    listFields.add(new Field("abr","abr", false, false, false, false));
	    listFields.add(new Field("mai","mai", false, false, false, false));
	    listFields.add(new Field("jun","jun", false, false, false, false));
	    listFields.add(new Field("jul","jul", false, false, false, false));
	    listFields.add(new Field("ago","ago", false, false, false, false));
	    listFields.add(new Field("setem","set", false, false, false, false));
	    listFields.add(new Field("outub","out", false, false, false, false));
	    listFields.add(new Field("nov","nov", false, false, false, false));
	    listFields.add(new Field("dez","dez", false, false, false, false));
	    listFields.add(new Field("repeticao","repeticao", false, false, false, false));
	    listFields.add(new Field("repeticaoIntervalo","repeticaoIntervalo", false, false, false, false));
	    listFields.add(new Field("repeticaoTipoIntervalo","repeticaoTipoIntervalo", false, false, false, false));
	    listFields.add(new Field("horaInicio","horaInicio", false, false, false, false));
	    listFields.add(new Field("horaFim","horaFim", false, false, false, false));	
	    
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "ProgramacaoAtividade";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return ProgramacaoAtividadeDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public Collection findByIdAtividadePeriodica(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idAtividadePeriodica", "=", parm)); 
		ordenacao.add(new Order("dia"));
		ordenacao.add(new Order("horaInicio"));
		return super.findByCondition(condicao, ordenacao);
	}
	public Collection findByIdAtividadePeriodicaOrderDataHora(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idAtividadePeriodica", "=", parm)); 
		ordenacao.add(new Order("dataInicio"));
		ordenacao.add(new Order("horaInicio"));
		return super.findByCondition(condicao, ordenacao);
	}	
	public void deleteByIdAtividadePeriodica(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idAtividadePeriodica", "=", parm));
		super.deleteByCondition(condicao);
	}
}
