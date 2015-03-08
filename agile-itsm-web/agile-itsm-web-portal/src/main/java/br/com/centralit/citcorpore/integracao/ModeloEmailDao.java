package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ModeloEmailDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({"unchecked", "rawtypes"})
public class ModeloEmailDao extends CrudDaoDefaultImpl {

    public ModeloEmailDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
    
	@Override
	public Collection find(IDto obj) throws PersistenceException {
		return null;
	}

	@Override
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		
		listFields.add(new Field("idModeloEmail" ,"idModeloEmail", true, true, false, false) );
		listFields.add(new Field("titulo" ,"titulo", false, false, false, false) );
		listFields.add(new Field("texto" ,"texto", false, false, false, false) );
		listFields.add(new Field("situacao" ,"situacao", false, false, false, false) );
		listFields.add(new Field("identificador" ,"identificador", false, false, false, false) );
		
		return listFields;
	}

	@Override
	public String getTableName() {
		return "ModelosEmails";
	}

	@Override
	public Collection list() throws PersistenceException {
		return null;
	}

	@Override
	public Class getBean() {
		return ModeloEmailDTO.class;
	}
	
	public Collection getAtivos() throws PersistenceException {
		List lstOrder = new ArrayList();
		List lstCondicao = new ArrayList();
		
		lstCondicao.add(new Condition("situacao", "=", "A") );
		
		lstOrder.add(new Order("titulo") );
		
		return super.findByCondition(lstCondicao, lstOrder);
	}
	
	public ModeloEmailDTO findByIdentificador(String identificador) throws PersistenceException {
		List lstOrder = null;
		List lstCondicao = null;
		List result = null;
		
		if (identificador != null && !identificador.trim().equals("") ) {
			lstOrder = new ArrayList();
			lstCondicao = new ArrayList();
			
			lstCondicao.add(new Condition("identificador", "=", identificador) );
			lstOrder.add(new Order("titulo") );
			
			result = (List) super.findByCondition(lstCondicao, lstOrder);
		}
		
		if (result != null && !result.isEmpty() )
			return (ModeloEmailDTO) result.get(0);
		else
			return null;
	}	
}