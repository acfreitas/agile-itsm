package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.LigacaoRequisicaoLiberacaoHistoricoComprasDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

public class LigacaoRequisicaoLiberacaoComprasDao extends CrudDaoDefaultImpl {
	public LigacaoRequisicaoLiberacaoComprasDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idLigacaoLibHistCompras", "idLigacaoLibHistCompras", true, true, false, false));
		listFields.add(new Field("idRequisicaoLiberacao" ,"idRequisicaoLiberacao", false, false, false, false));
		listFields.add(new Field("idHistoricoLiberacao", "idHistoricoLiberacao", false, false, false, false));
		listFields.add(new Field("idRequisicaoLiberacaoCompras", "idRequisicaoLiberacaoCompras", false, false, false, false));
		return listFields;
	}
	
	public String getTableName() {
		return this.getOwner() + "ligacaolibhistcompras";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return LigacaoRequisicaoLiberacaoHistoricoComprasDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	
	public void deleteByIdRequisicaoLiberacao(Integer idRequisicaoLiberacao) throws ServiceException, Exception{
		ArrayList<Condition> condicoes = new ArrayList<Condition>();
		
		condicoes.add(new Condition("idRequisicaoLiberacao", "=", idRequisicaoLiberacao));
		
		super.deleteByCondition(condicoes);
	}
	
	public Collection findByIdLiberacao(Integer parm) throws Exception {
		List parametro = new ArrayList();
		List fields = new ArrayList(); 
		
		
		String sql = " select rqMidia.idRequisicaoLiberacaoMidia, rqMidia.idMidiaSoftware, rqMidia.idRequisicaoLiberacao, midia.nome "+
				" from requisicaoliberacaomidia rqMidia "+ 
				" inner join liberacao lib on rqMidia.idRequisicaoLiberacao = lib.idLiberacao "+
				" inner join midiasoftware midia on rqMidia.idMidiaSoftware = midia.idmidiasoftware "+ 
				" where rqMidia.idRequisicaoLiberacao = ? ";
		
	  List resultado = 	execSQL(sql, new Object[]{parm});
	  
	  fields.add("idRequisicaoLiberacaoMidia");
	  fields.add("idMidiaSoftware");
	  fields.add("idRequisicaoLiberacao");
	  fields.add("nomeMidia");
	  

		return listConvertion(getBean(), resultado,fields) ;
	}

}
