package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.SolucaoContornoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({"rawtypes" , "unchecked"})
public class SolucaoContornoDao extends CrudDaoDefaultImpl {

	public SolucaoContornoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}



	@Override
	public Collection find(IDto obj) throws PersistenceException {
				return null;
	}


	@Override
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();

		listFields.add(new Field("IDSOLUCAOCONTORNO", "idSolucaoContorno", true, true, false, false));
		listFields.add(new Field("TITULO", "titulo", false, false, false, false));
		listFields.add(new Field("DESCRICAO", "descricao", false, false, false, false));
		listFields.add(new Field("DATAHORACRIACAO", "dataHoraCriacao", false, false, false, false));
		listFields.add(new Field("idproblema", "idProblema", false, false, false, false));

		return listFields;
	}

	@Override
	public String getTableName() {
		return "solucaocontorno";
	}

	@Override
	public Collection list() throws PersistenceException {
		List list = new ArrayList();
		list.add(new Order("titulo"));
		return super.list(list);
	}

	@Override
	public Class getBean() {
		return SolucaoContornoDTO.class;
	}

	public IDto restore(IDto obj) throws PersistenceException{
		SolucaoContornoDTO solucaoContornoDTO = (SolucaoContornoDTO) obj;

		List condicao = new ArrayList();
		condicao.add(new Condition("idSolucaoContorno", "=", solucaoContornoDTO.getIdSolucaoContorno()));

		ArrayList<SolucaoContornoDTO> res = (ArrayList<SolucaoContornoDTO>) super.findByCondition(condicao, null);

		if(res != null){
			return res.get(0);
		}else{
			return null;
		}
	}
	public IDto findByIdProblema(IDto obj) throws PersistenceException {
		SolucaoContornoDTO solucaoContornoDTO = (SolucaoContornoDTO) obj;

		List condicao = new ArrayList();
		condicao.add(new Condition("idProblema", "=", solucaoContornoDTO.getIdProblema()));

		ArrayList<SolucaoContornoDTO> res = (ArrayList<SolucaoContornoDTO>) super.findByCondition(condicao, null);

		if(res != null){
			return res.get(0);
		}else{
			return null;
		}
	}

}
