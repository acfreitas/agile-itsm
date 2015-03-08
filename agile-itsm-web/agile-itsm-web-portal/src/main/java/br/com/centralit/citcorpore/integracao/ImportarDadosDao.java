package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ImportarDadosDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({"rawtypes", "unchecked"})
public class ImportarDadosDao extends CrudDaoDefaultImpl {
	
	public ImportarDadosDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	public Collection<Field> getFields() {
		
		Collection<Field> listFields = new ArrayList<>();
		
		listFields.add(new Field("idimportardados" ,"idImportarDados", true, true, false, false));
		listFields.add(new Field("idexternalconnection" ,"idExternalConnection", false, false, false, false));
		listFields.add(new Field("importarpor" ,"importarPor", false, false, false, false));
		listFields.add(new Field("tipo" ,"tipo", false, false, false, false));
		listFields.add(new Field("nome" ,"nome", false, false, false, false));
		listFields.add(new Field("script" ,"script", false, false, false, false));
		listFields.add(new Field("agendarrotina" ,"agendarRotina", false, false, false, false));
		listFields.add(new Field("executarpor" ,"executarPor", false, false, false, false));
		listFields.add(new Field("horaexecucao" ,"horaExecucao", false, false, false, false));
		listFields.add(new Field("periodohora" ,"periodoHora", false, false, false, false));
		listFields.add(new Field("datafim" ,"dataFim", false, false, false, false));
		listFields.add(new Field("tabelaorigem" ,"tabelaOrigem", false, false, false, false));
		listFields.add(new Field("tabeladestino" ,"tabelaDestino", false, false, false, false));
		listFields.add(new Field("jsonmatriz" ,"jsonMatriz", false, false, false, false));
		
		return listFields;

	}
	
	public String getTableName() {
		return this.getOwner() + "ImportarDados";
	}
	
	public Collection list() throws PersistenceException {
		return super.list("nome");
	}
	
	public Collection listAtivos() throws PersistenceException {
		
		List condicao = new ArrayList();
		condicao.add(new Condition("dataFim", "is", null));
		
		return (List<ImportarDadosDTO>) super.findByCondition(condicao, null);
	}
	
	public Collection listAtivosEComRotinaAgendada() throws PersistenceException {
		
		List condicao = new ArrayList();
		condicao.add(new Condition("dataFim", "is", null));
		condicao.add(new Condition("agendarRotina", "=", "S"));
		
		return (List<ImportarDadosDTO>) super.findByCondition(condicao, null);
	}

	public Class getBean() {
		return ImportarDadosDTO.class;
	}
	
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	
	/**
	 * 
	 * Consulta os registros vinculados ao idExternalConnection
	 * 
	 * @param idExternalConnection
	 * @return
	 * @throws Exception
	 */
	public Collection<ImportarDadosDTO> consultarImportarDadosPeloExternalConnection(Integer idExternalConnection) throws PersistenceException {
		
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();

		condicao.add(new Condition("idExternalConnection", "=", idExternalConnection));
		ordenacao.add(new Order("nome"));
		
		return super.findByCondition(condicao, ordenacao);
	}
}