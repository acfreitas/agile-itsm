package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ProblemaDTO;
import br.com.centralit.citcorpore.bean.ProblemaRelacionadoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

public class ProblemaRelacionadoDao  extends CrudDaoDefaultImpl {


	public ProblemaRelacionadoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Collection find(IDto obj) throws PersistenceException {
		return null;
	}

	@Override
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		
		listFields.add(new Field("idproblema", "idProblema", false, false, false, false));
		listFields.add(new Field("idproblemarelacionado", "idProblemaRelacionado", false, false, false, false));

		return listFields;
	}

	@Override
	public String getTableName() {
		return "problemarelacionado" ;
	}
	
	public void deleteByIdProblema(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idProblema", "=", parm));
		super.deleteByCondition(condicao);
	}
	
	public Collection findByIdProblema(Integer idProblema) throws PersistenceException {
		List listRetorno = new ArrayList();
		List paramentro = new ArrayList();	
		
	String sql = " select pro.idproblema, pro.titulo, pro.dataHoraInicio,  " +
		" pro.idProprietario, pro.status, pro.descricao, pro.causaRaiz,  " +
		" pro.solucaoContorno, pro.solucaoDefinitiva   from problema pro where pro.idproblema in (select idproblemarelacionado from problemarelacionado where idproblema = ? ) " ;
		
		paramentro.add(idProblema);
		List list = execSQL(sql, paramentro.toArray());
		listRetorno.add("idProblema");
		listRetorno.add("titulo");
		listRetorno.add("dataHoraInicio");
		listRetorno.add("idProprietario");
		listRetorno.add("status");
		listRetorno.add("descricao");
		listRetorno.add("causaRaiz");
		listRetorno.add("solucaoContorno");
		listRetorno.add("solucaoDefinitiva");
		return listConvertion(ProblemaDTO.class, list, listRetorno);
	}

	@Override
	public Collection list() throws PersistenceException {
		return null;
	}

	@Override
	public Class getBean() {
		return ProblemaRelacionadoDTO.class;
	}

}
