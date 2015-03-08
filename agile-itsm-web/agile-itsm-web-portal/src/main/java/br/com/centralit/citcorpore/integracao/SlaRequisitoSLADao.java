package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.SlaRequisitoSlaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class SlaRequisitoSLADao extends CrudDaoDefaultImpl {
	public SlaRequisitoSLADao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idRequisitoSLA" ,"idRequisitoSLA", true, false, false, false));
		listFields.add(new Field("idAcordoNivelServico" ,"idAcordoNivelServico", true, false, false, false));
		listFields.add(new Field("dataVinculacao" ,"dataVinculacao", false, false, false, false));
		listFields.add(new Field("dataUltModificacao" ,"dataUltModificacao", false, false, false, false));
		listFields.add(new Field("deleted" ,"deleted", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "SlaRequisitoSla";
	}
	public Collection list() throws PersistenceException {
		return super.list("idRequisitoSLA");
	}

	public Class getBean() {
		return SlaRequisitoSlaDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public Collection findByIdAcordoNivelServico(Integer idAcordoNivelServico) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idAcordoNivelServico", "=", idAcordoNivelServico)); 
		ordenacao.add(new Order("idRequisitoSLA"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdAcordoNivelServico(Integer idAcordoNivelServico) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idAcordoNivelServico", "=", idAcordoNivelServico));
		super.deleteByCondition(condicao);
	}
	
	public boolean existeAcordoByRequisito(Integer idRequisito) throws PersistenceException {
		StringBuilder sql = new StringBuilder();
		List parametro = new ArrayList();
		sql.append("SELECT * FROM " + getTableName());
		sql.append(" WHERE idrequisitosla = ? ");
		parametro.add(idRequisito);
		List list = execSQL(sql.toString(), parametro.toArray());
		if(list != null && !list.isEmpty())
			return true;
		else
			return false;
	}
}
