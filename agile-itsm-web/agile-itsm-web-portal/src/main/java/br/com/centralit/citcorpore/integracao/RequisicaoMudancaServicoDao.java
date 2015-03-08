package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.RequisicaoMudancaServicoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class RequisicaoMudancaServicoDao extends CrudDaoDefaultImpl {

	public RequisicaoMudancaServicoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idrequisicaomudancaservico", "idRequisicaoMudancaServico", true, true, false, false));
		listFields.add(new Field("idrequisicaomudanca", "idRequisicaoMudanca", false, false, false, false));
		listFields.add(new Field("idservico", "idServico", false, false, false, false));
		listFields.add(new Field("dataFim", "dataFim", false, false, false, false));

		return listFields;
	}

	public String getTableName() {
		return this.getOwner() + "requisicaomudancaservico";
	}

	public Class getBean() {
		return RequisicaoMudancaServicoDTO.class;
	}

	public Collection find(IDto arg0) throws PersistenceException {
		return super.find(arg0, null);
	}

	@Override
	public void updateNotNull(IDto obj) throws PersistenceException {
		super.updateNotNull(obj);
	}

	@Override
	public Collection list() throws PersistenceException {
		return super.list("idrequisicaomudancaservico");
	}

	@Override
	public int deleteByCondition(List condicao) throws PersistenceException {
		return super.deleteByCondition(condicao);
	}

	public ArrayList<RequisicaoMudancaServicoDTO> listByIdRequisicaoMudanca(Integer idRequisicaoMudanca) throws ServiceException, Exception {
		ArrayList<Condition> condicoes = new ArrayList<Condition>();

		condicoes.add(new Condition("idRequisicaoMudanca", "=", idRequisicaoMudanca));

		return (ArrayList<RequisicaoMudancaServicoDTO>) super.findByCondition(condicoes, null);
	}

	/**
	 * Retorna o item de relacionamento específico sem a chave primária da tabela.
	 * Uma espécie de consulta por chave composta.
	 *
	 * @param dto
	 * @return
	 * @throws Exception
	 * @throws ServiceException
	 */
	public RequisicaoMudancaServicoDTO restoreByChaveComposta(RequisicaoMudancaServicoDTO dto) throws ServiceException, Exception{
		ArrayList<Condition> condicoes = new ArrayList<Condition>();

		condicoes.add(new Condition("idRequisicaoMudanca", "=", dto.getIdRequisicaoMudanca()));
		condicoes.add(new Condition("idServico", "=", dto.getIdServico()));

		ArrayList<RequisicaoMudancaServicoDTO> retorno = (ArrayList<RequisicaoMudancaServicoDTO>) super.findByCondition(condicoes, null);
		if(retorno != null){
			return retorno.get(0);
		}
		return null;
	}

	public void deleteByIdRequisicaoMudanca(Integer idRequisicaoMudanca) throws ServiceException, Exception{
		ArrayList<Condition> condicoes = new ArrayList<Condition>();

		condicoes.add(new Condition("idRequisicaoMudanca", "=", idRequisicaoMudanca));

		super.deleteByCondition(condicoes);
	}


	public Collection findByIdMudancaEDataFim(Integer idRequisicaoMudanca) throws Exception {
		List fields = new ArrayList();


		String sql = " select idrequisicaomudancaservico,idrequisicaomudanca,idservico,datafim from requisicaomudancaservico where  idrequisicaomudanca = ? and datafim is null";

	  List resultado = 	execSQL(sql, new Object[]{idRequisicaoMudanca});



	  fields.add("idRequisicaoMudancaServico");
	  fields.add("idRequisicaoMudanca");
	  fields.add("idServico");
	  fields.add("dataFim");

	  return listConvertion(getBean(), resultado,fields) ;
	}
	public Collection listByIdHistoricoMudanca(Integer idRequisicaoMudanca) throws Exception {
		List fields = new ArrayList();


		String sql = " select distinct rs.idrequisicaomudancaservico, rs.idrequisicaomudanca, rs.idservico,datafim "+
				"from requisicaomudancaservico rs "+
				"inner join ligacao_mud_hist_se ligser on ligser.idrequisicaomudancaservico = rs.idrequisicaomudancaservico "+
				"where  ligser.idhistoricomudanca = ?";

		List resultado = 	execSQL(sql, new Object[]{idRequisicaoMudanca});



		fields.add("idRequisicaoMudancaServico");
		fields.add("idRequisicaoMudanca");
		fields.add("idServico");
		fields.add("dataFim");

		return listConvertion(getBean(), resultado,fields) ;
	}

}
