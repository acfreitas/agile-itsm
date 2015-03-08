package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.LigacaoRequisicaoMudancaHistoricoProblemaDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaResponsavelDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

@SuppressWarnings({"rawtypes","unchecked"})
public class LigacaoRequisicaoMudancaAprovacaoDao extends CrudDaoDefaultImpl {


	public LigacaoRequisicaoMudancaAprovacaoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idligacao_mud_hist_aprov_mud", "idligacao_mud_hist_aprov_mud", true, true, false, false));
		listFields.add(new Field("idRequisicaoMudanca" ,"idRequisicaoMudanca", false, false, false, false));
		listFields.add(new Field("idHistoricoMudanca", "idHistoricoMudanca", false, false, false, false));
		listFields.add(new Field("idAprovacaoMudanca", "idAprovacaoMudanca", false, false, false, false));

		return listFields;
	}

	public String getTableName() {
		return this.getOwner() + "ligacao_mud_hist_aprov_mud";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return LigacaoRequisicaoMudancaHistoricoProblemaDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}

	public void deleteByIdRequisicaoMudanca(Integer idRequisicaoMudanca) throws ServiceException, Exception{
		ArrayList<Condition> condicoes = new ArrayList<Condition>();

		condicoes.add(new Condition("idRequisicaoMudanca", "=", idRequisicaoMudanca));

		super.deleteByCondition(condicoes);
	}

	public Collection<RequisicaoMudancaResponsavelDTO> findByIdMudanca(Integer idRequisicaoMudanca) throws Exception {
		List fields = new ArrayList();


		String sql = "select rqResponsavel.iditemconfiguracao, rqResponsavel.idRequisicaoMudanca, responsavel.nome, responsavel.telefone, responsavel.email, cargo.nomecargo , rqResponsavel.descricao "+
				"from requisicaomudancaitemconfiguracao rqResponsavel "+
				"inner join requisicaomudanca lib on rqResponsavel.idRequisicaoMudanca = lib.idRequisicaomudanca "+
				"inner join itemconfiguracao responsavel on rqResponsavel.iditemconfiguracao = responsavel.iditemconfiguracao "+
				"inner join cargos cargo on responsavel.idcargo = cargo.idcargo"+
				"where rqResponsavel.idRequisicaomudanca = ? ";

	  List resultado = 	execSQL(sql, new Object[]{idRequisicaoMudanca	});

	  fields.add("idResponsavel");
	  fields.add("idRequisicaoMudanca");
	  fields.add("nomeResponsavel");
	  fields.add("telResponsavel");
	  fields.add("emailResponsavel");
	  fields.add("nomeCargo");
	  fields.add("papelResponsavel");

	  return listConvertion(RequisicaoMudancaResponsavelDTO.class, resultado,fields) ;
	}

}
