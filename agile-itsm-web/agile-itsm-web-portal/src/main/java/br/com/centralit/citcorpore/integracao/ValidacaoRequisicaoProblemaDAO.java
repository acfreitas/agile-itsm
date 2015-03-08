package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ValidacaoRequisicaoProblemaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

/**
 * 
 * @author geber.costa
 *
 */

@SuppressWarnings({"rawtypes","unchecked"})
public class ValidacaoRequisicaoProblemaDAO extends CrudDaoDefaultImpl {
	public ValidacaoRequisicaoProblemaDAO() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}

	@Override
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		
		listFields.add(new Field("idValidacaoRequisicaoProblema","idValidacaoRequisicaoProblema",true,true,false,false));
		listFields.add(new Field("observacaoproblema","observacaoProblema",false,false,false,false));
		listFields.add(new Field("datainicio","dataInicio",false,false,false,false));
		listFields.add(new Field("datafim","dataFim",false,false,false,false));
		listFields.add(new Field("idproblema","idProblema",false,false,false,false));
		return listFields;
	}
	
	public ValidacaoRequisicaoProblemaDTO findByIdProblema(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idProblema", "=", parm)); 
		 List result = (List) super.findByCondition(condicao, null);
		 
		 if (result != null && !result.isEmpty()){
			 return (ValidacaoRequisicaoProblemaDTO) result.get(0);
		 }else{
			   return null;
		 }
	         
	}

	public ValidacaoRequisicaoProblemaDTO findByObservacaoProblema(String observacaoProblema) throws PersistenceException {
        List condicao = new ArrayList();
        condicao.add(new Condition("observacaoProblema", "=", observacaoProblema));
        
        List result = (List) super.findByCondition(condicao, null);
        if (result != null && !result.isEmpty())
            return (ValidacaoRequisicaoProblemaDTO) result.get(0);
        else
            return null;
    }  
	
	@Override
	public String getTableName() {
		
		return "validacaoRequisicaoProblema";
	}

	@Override
	public Collection list() throws PersistenceException {
				return null;
	}

	@Override
	public Class getBean() {
				return ValidacaoRequisicaoProblemaDTO.class;
	}
}
