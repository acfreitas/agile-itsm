package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.NivelAutoridadeDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class NivelAutoridadeDao extends CrudDaoDefaultImpl {

	public NivelAutoridadeDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idNivelAutoridade" ,"idNivelAutoridade", true, true, false, false));
		listFields.add(new Field("nomeNivelAutoridade" ,"nomeNivelAutoridade", false, false, false, false));
		listFields.add(new Field("hierarquia" ,"hierarquia", false, false, false, false));
		listFields.add(new Field("situacao" ,"situacao", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "NivelAutoridade";
	}
	public Collection list() throws PersistenceException {
        List ordenacao = new ArrayList();        
        ordenacao.add(new Order("nomeNivelAutoridade") );
        return super.list(ordenacao);
	}

	public Class getBean() {
		return NivelAutoridadeDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public Collection findByHierarquiaAndNotIdNivelAutoridade(Integer hierarquia, Integer idNivelAutoridade) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("hierarquia", "=", hierarquia)); 
		if (idNivelAutoridade != null)
			condicao.add(new Condition("idNivelAutoridade", "<>", idNivelAutoridade)); 
		ordenacao.add(new Order("idNivelAutoridade"));
		return super.findByCondition(condicao, ordenacao);
	}

}
