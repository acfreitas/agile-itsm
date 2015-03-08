package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ReuniaoRequisicaoMudancaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class ReuniaoRequisicaoMudancaDAO extends CrudDaoDefaultImpl{

	public ReuniaoRequisicaoMudancaDAO(String aliasDB, Usuario usuario) {
		super(aliasDB, usuario);
	}

	
	public ReuniaoRequisicaoMudancaDAO(){
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Collection find(IDto obj) throws PersistenceException {
		List ordem = new ArrayList();
		ordem.add(new Order("idReuniaoRequisicaoMudanca"));
		return super.find(obj, ordem);
	}

	@Override
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();

		listFields.add(new Field("idReuniaoRequisicaoMudanca", "idReuniaoRequisicaoMudanca", true, true, false, true));
		listFields.add(new Field("idRequisicaoMudanca", "idRequisicaoMudanca", false, false, false, false));
		listFields.add(new Field("localReuniao", "localReuniao", false, false, false, false));
		listFields.add(new Field("criadoPor", "criadoPor", false, false, false, false));
		listFields.add(new Field("descricao", "descricao", false, false, false, false));
		listFields.add(new Field("duracaoEstimada", "duracaoEstimada", false, false, false, false));
		listFields.add(new Field("dataCriacao", "dataCriacao", false, false, false, false));
		listFields.add(new Field("dataInicio", "dataInicio", false, false, false, false));
		listFields.add(new Field("horaInicio", "horaInicio", false, false, false, false));
		listFields.add(new Field("status", "status", false, false, false, false));

		return listFields;
	}

	@Override
	public String getTableName() {
		return "reuniaorequisicaomudanca";
	}

	@Override
	public Collection list() throws PersistenceException {
		List list = new ArrayList();
		list.add(new Order("idReuniaoRequisicaoMudanca"));
		return super.list(list);
	}

	@Override
	public Class getBean() {
		return ReuniaoRequisicaoMudancaDTO.class;
	}

	public Collection findByIdRequisicaoMudanca(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("status", "!=", "Cancelada"));
		condicao.add(new Condition("status", "!=", "Finalizada"));
		condicao.add(new Condition("idRequisicaoMudanca", "=", parm)); 
		ordenacao.add(new Order("dataCriacao"));
		return super.findByCondition(condicao, ordenacao);
	}
	
}
