package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.RequisitoSLADTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class RequisitoSLADao extends CrudDaoDefaultImpl {
	public RequisitoSLADao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idRequisitoSLA" ,"idRequisitoSLA", true, true, false, false));
		listFields.add(new Field("idEmpregado" ,"idEmpregado", false, false, false, false));
		listFields.add(new Field("assunto" ,"assunto", false, false, false, false));
		listFields.add(new Field("detalhamento" ,"detalhamento", false, false, false, false));
		listFields.add(new Field("situacao" ,"situacao", false, false, false, false));
		listFields.add(new Field("requisitadoEm" ,"requisitadoEm", false, false, false, false));
		listFields.add(new Field("criadoEm" ,"criadoEm", false, false, false, false));
		listFields.add(new Field("modificadoEm" ,"modificadoEm", false, false, false, false));
		listFields.add(new Field("criadoPor" ,"criadoPor", false, false, false, false));
		listFields.add(new Field("modificadoPor" ,"modificadoPor", false, false, false, false));
		listFields.add(new Field("deleted" ,"deleted", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "RequisitoSLA";
	}
	public Collection list() throws PersistenceException {
		return super.list("assunto");
	}

	public Class getBean() {
		return RequisitoSLADTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public Collection findByIdEmpregado(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idEmpregado", "=", parm)); 
		ordenacao.add(new Order("assunto"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdEmpregado(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idEmpregado", "=", parm));
		super.deleteByCondition(condicao);
	}
	/**
	 * Filtra unidade pelo id
	 * 
	 * @param idRequisitoSla
	 * @return Collection
	 * @throws Exception 
	 */
	public Collection findById(Integer idRequisitoSla) throws PersistenceException {
		StringBuilder sql = new StringBuilder();
		List parametro = new ArrayList();
		sql.append("SELECT idRequisitoSla,assunto FROM " + getTableName() + " where idRequisitoSla = ? and (deleted is null or deleted = 'n')");
		parametro.add(idRequisitoSla);
		List list = execSQL(sql.toString(), parametro.toArray());
		List listRetorno = new ArrayList();
		listRetorno.add("idRequisitoSla");
		listRetorno.add("assunto");
		List result = this.engine.listConvertion(getBean(), list, listRetorno);
		return result;
	}
}
