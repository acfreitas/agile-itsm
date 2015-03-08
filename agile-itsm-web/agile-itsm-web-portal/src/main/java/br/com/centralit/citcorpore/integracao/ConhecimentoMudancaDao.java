package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ConhecimentoMudancaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ConhecimentoMudancaDao extends CrudDaoDefaultImpl {


	public ConhecimentoMudancaDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Collection find(IDto obj) throws PersistenceException {

		return null;
	}

	@Override
	public Collection<Field> getFields() {

		Collection<Field> listFields = new ArrayList<>();

		listFields.add(new Field("IDREQUISICAOMUDANCA", "idRequisicaoMudanca", true, false, false, false));
		listFields.add(new Field("IDBASECONHECIMENTO", "idBaseConhecimento", true, false, false, false));

		return listFields;
	}

	@Override
	public String getTableName() {
		return "CONHECIMENTOMUDANCA";
	}

	@Override
	public Class getBean() {
		return ConhecimentoMudancaDTO.class;
	}

	@Override
	public Collection list() throws PersistenceException {
		return null;
	}

	public void deleteByidRequisicaoMudanca(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idRequisicaoMudanca", "=", parm));
		super.deleteByCondition(condicao);
	}

	public void deleteByIdBaseConhecimento(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idBaseConhecimento", "=", parm));
		super.deleteByCondition(condicao);
	}

	public Collection findByIdBaseConhecimento(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idBaseConhecimento", "=", parm));
		return super.findByCondition(condicao, ordenacao);
	}

	public Collection findByidRequisicaoMudanca(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idRequisicaoMudanca", "=", parm));
		return super.findByCondition(condicao, ordenacao);
	}

}
