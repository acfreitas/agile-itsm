package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaRiscoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

@SuppressWarnings({"rawtypes", "unused", "unchecked"})
public class RequisicaoMudancaLiberacaoDao extends CrudDaoDefaultImpl {

	public RequisicaoMudancaLiberacaoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idrequisicaomudancaLiberacao", "idRequisicaoMudancaLiberacao", true, true, false, false));
		listFields.add(new Field("idLiberacao" ,"idLiberacao", false, false, false, false));
		listFields.add(new Field("idrequisicaomudanca", "idRequisicaoMudanca", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "requisicaomudancaliberacao";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return RequisicaoLiberacaoDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	
	public void deleteByIdRequisicaoMudanca(Integer idRequisicaoMudanca) throws ServiceException, Exception{
		ArrayList<Condition> condicoes = new ArrayList<Condition>();
		
		condicoes.add(new Condition("idRequisicaoMudanca", "=", idRequisicaoMudanca));
		
		super.deleteByCondition(condicoes);
	}
	
	public Collection findByIdRequisicaoMudanca(Integer parm) throws Exception {
		List parametro = new ArrayList();
		List fields = new ArrayList();
		List list = new ArrayList();
		StringBuilder sql =  new StringBuilder();
		sql.append(" SELECT rml.idliberacao as idLiberacao, l.titulo, l.descricao FROM liberacao l JOIN requisicaomudancaliberacao rml ON l.idliberacao = rml.idliberacao WHERE rml.idrequisicaomudanca = ? ORDER BY rml.idliberacao ");
		parametro.add(parm);
		list = this.execSQL(sql.toString(), parametro.toArray());
		fields.add("idLiberacao");
		fields.add("titulo");
		fields.add("descricao");
		if (list != null && !list.isEmpty()) {
			return (List<RequisicaoMudancaRiscoDTO>) this.listConvertion(getBean(), list, fields);
		} else {
			return null;
		}		
	}
	
	
}
