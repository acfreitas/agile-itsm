package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.AssinaturaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

/**
 * @author euler.ramos
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class AssinaturaDAO extends CrudDaoDefaultImpl {

    public AssinaturaDAO() {
	super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection find(IDto obj) throws PersistenceException {
	List order =  new ArrayList();
	order.add(new Order("idAssinatura", "ASC"));
	return super.find(obj, order);
    }

    @Override
    public Collection<Field> getFields() {
	Collection<Field> listFields = new ArrayList<>();
	listFields.add(new Field("idassinatura", "idAssinatura", true, true, false, false));
	listFields.add(new Field("idempregado", "idEmpregado", false, false, false, false));
	listFields.add(new Field("papel", "papel", false, false, false, false));
	listFields.add(new Field("fase", "fase", false, false, false, false));
	listFields.add(new Field("datainicio", "dataInicio", false, false, false, false));
	listFields.add(new Field("datafim", "dataFim", false, false, false, false));
	return listFields;
    }

    @Override
    public String getTableName() {
	return "assinatura";
    }

    @Override
    public Collection list() throws PersistenceException {
	List condicao = new ArrayList();
	condicao.add(new Condition("dataFim", "is", null));
	return super.findByCondition(condicao, null);
    }

    @Override
    public Class getBean() {
	return AssinaturaDTO.class;
    }

    public boolean violaIndiceUnico(AssinaturaDTO assinaturaDTO) {
	boolean encontrou = false;

	List result;
	try {
	    List resp = new ArrayList();

	    List parametro = new ArrayList();

	    StringBuilder sql = new StringBuilder();

	    sql.append("SELECT idassinatura FROM " + this.getTableName()+" ");
	    sql.append("WHERE (datafim is null) AND ");

	    if (assinaturaDTO.getIdAssinatura() != null) {
		sql.append("idassinatura <> ? AND ");
		parametro.add(assinaturaDTO.getIdAssinatura());
	    }

	    // Pode-se cadastrar assinatura sem empregado
	    if (assinaturaDTO.getIdEmpregado() != null) {
		sql.append("idempregado = ? AND ");
		parametro.add(assinaturaDTO.getIdEmpregado());
	    } else {
		sql.append("(idempregado is null) AND ");
	    }

	    sql.append("papel = ? AND ");
	    parametro.add(assinaturaDTO.getPapel());
	    sql.append("fase = ?");
	    parametro.add(assinaturaDTO.getFase());

	    resp = this.execSQL(sql.toString(), parametro.toArray());

	    List listRetorno = new ArrayList();
	    listRetorno.add("idassinatura");

	    result = this.engine.listConvertion(this.getBean(), resp,
		    listRetorno);
	} catch (PersistenceException e) {
	    e.printStackTrace();
	    result = null;
	} catch (Exception e) {
	    e.printStackTrace();
	    result = null;
	}
	encontrou = ((result != null) && (result.size() > 0));
	return encontrou;
    }

}
