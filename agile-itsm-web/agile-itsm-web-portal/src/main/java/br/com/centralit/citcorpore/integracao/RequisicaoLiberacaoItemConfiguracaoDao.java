package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoItemConfiguracaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class RequisicaoLiberacaoItemConfiguracaoDao extends CrudDaoDefaultImpl {

	public RequisicaoLiberacaoItemConfiguracaoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idrequisicaoliberacaoitemconfiguracao", "idRequisicaoLiberacaoItemConfiguracao", true, true, false, false));
		listFields.add(new Field("idrequisicaoliberacao", "idRequisicaoLiberacao", false, false, false, false));
		listFields.add(new Field("iditemconfiguracao", "idItemConfiguracao", false, false, false, false));
		listFields.add(new Field("descricao", "descricao", false, false, false, false));
		listFields.add(new Field("idhistoricoliberacao", "idHistoricoLiberacao", false, false, false, false));

		return listFields;
	}

	public String getTableName() {
		return this.getOwner() + "requisicaoliberacaoitemconfiguracao";
	}

	/**
	 * Verifica se existe outro item igual criado.
	 * Se existir retorna 'true', senao retorna 'false';
	 */
	public boolean verificaSeCadastrado(RequisicaoLiberacaoItemConfiguracaoDTO itemDTO) throws Exception {
		boolean estaCadastrado;
		List parametro = new ArrayList();
		List list = new ArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("select * from " + getTableName() + " where iditemconfiguracao = ? and idrequisicaoliberacao = ?  ");
		parametro.add(itemDTO.getIdItemConfiguracao());
		parametro.add(itemDTO.getIdRequisicaoLiberacao());
		list = this.execSQL(sql.toString(), parametro.toArray());
		if (list != null && !list.isEmpty()) {
			estaCadastrado = true;
		} else {
			estaCadastrado = false;
		}
		return estaCadastrado;
	}

	public Class getBean() {
		return RequisicaoLiberacaoItemConfiguracaoDTO.class;
	}

	public Collection find(IDto arg0) throws PersistenceException {
		return super.find(arg0, null);
	}

	@Override
	public void updateNotNull(IDto obj) throws PersistenceException {
		super.updateNotNull(obj);
	}

	@Override
	public int deleteByCondition(List condicao) throws PersistenceException {
		return super.deleteByCondition(condicao);
	}

	@Override
	public Collection list() throws PersistenceException {
		return super.list("idRequisicaoLiberacaoItemConfiguracao");
	}

    public Collection findByIdRequisicaoLiberacao(Integer parm) throws Exception {

    	List parametro = new ArrayList();
		List fields = new ArrayList();
		List list = new ArrayList();
		StringBuilder sql =  new StringBuilder();
		sql.append(" SELECT idrequisicaoliberacaoitemconfiguracao, idrequisicaoliberacao, iditemconfiguracao, descricao, idhistoricoliberacao ");
		sql.append(" FROM requisicaoliberacaoitemconfiguracao ");
		sql.append(" WHERE idrequisicaoliberacao = ?");
		parametro.add(parm);
		list = this.execSQL(sql.toString(), parametro.toArray());
		fields.add("idRequisicaoLiberacaoItemConfiguracao");
		fields.add("idRequisicaoliberacao");
		fields.add("idItemConfiguracao");
		fields.add("descricao");
		fields.add("idHistoricoLiberacao");
		if (list != null && !list.isEmpty()) {
			List listaIc = this.listConvertion(RequisicaoLiberacaoItemConfiguracaoDTO.class, list, fields);
			return listaIc;
		}

		return null;
    }

    public RequisicaoLiberacaoItemConfiguracaoDTO findByIdReqLiberacao(Integer parm) throws Exception {

    	RequisicaoLiberacaoItemConfiguracaoDTO requisicaoLiberacaoItemConfiguracaoDTO = new RequisicaoLiberacaoItemConfiguracaoDTO();
    	List parametro = new ArrayList();
		List fields = new ArrayList();
		List list = new ArrayList();
		StringBuilder sql =  new StringBuilder();
		sql.append(" SELECT idrequisicaoliberacaoitemconfiguracao, idrequisicaoliberacao, iditemconfiguracao, descricao, idhistoricoliberacao ");
		sql.append(" FROM requisicaoliberacaoitemconfiguracao ");
		sql.append(" WHERE idrequisicaoliberacao = ? AND idhistoricoliberacao is null");
		parametro.add(parm);
		list = this.execSQL(sql.toString(), parametro.toArray());
		fields.add("idRequisicaoLiberacaoItemConfiguracao");
		fields.add("idRequisicaoliberacao");
		fields.add("idItemConfiguracao");
		fields.add("descricao");
		fields.add("idHistoricoLiberacao");
		if (list != null && !list.isEmpty()) {
			List<RequisicaoLiberacaoItemConfiguracaoDTO> listaIc = this.listConvertion(RequisicaoLiberacaoItemConfiguracaoDTO.class, list, fields);
			return listaIc.get(0);
		}

		return requisicaoLiberacaoItemConfiguracaoDTO;
    }

    public Collection findByIdItemConfiguracao(Integer parm) throws Exception {
        List condicao = new ArrayList();
        List ordenacao = new ArrayList();
        condicao.add(new Condition("idItemConfiguracao", "=", parm));
        ordenacao.add(new Order("idRequisicaoLiberacao"));
        return super.findByCondition(condicao, ordenacao);
    }
	public ArrayList<RequisicaoLiberacaoItemConfiguracaoDTO> listByIdRequisicaoLiberacao(Integer idrequisicaoliberacao) throws ServiceException, Exception {
		ArrayList<Condition> condicoes = new ArrayList<Condition>();

		condicoes.add(new Condition("idRequisicaoLiberacao", "=", idrequisicaoliberacao));

		return (ArrayList<RequisicaoLiberacaoItemConfiguracaoDTO>) super.findByCondition(condicoes, null);
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
	public RequisicaoLiberacaoItemConfiguracaoDTO restoreByChaveComposta(RequisicaoLiberacaoItemConfiguracaoDTO dto) throws ServiceException, Exception{
		ArrayList<Condition> condicoes = new ArrayList<Condition>();

		condicoes.add(new Condition("idRequisicaoLiberacao", "=", dto.getIdRequisicaoLiberacao()));
		condicoes.add(new Condition("idItemConfiguracao", "=", dto.getIdItemConfiguracao()));

		ArrayList<RequisicaoLiberacaoItemConfiguracaoDTO> retorno =
					(ArrayList<RequisicaoLiberacaoItemConfiguracaoDTO>)	super.findByCondition(condicoes, null);

		if(retorno != null){
			return retorno.get(0);
		}

		return null;
	}

	public void deleteByIdRequisicaoMudanca(Integer idRequisicaoLiberacao) throws ServiceException, Exception{
		ArrayList<Condition> condicoes = new ArrayList<Condition>();

		condicoes.add(new Condition("idRequisicaoLiberacao", "=", idRequisicaoLiberacao));

		super.deleteByCondition(condicoes);
	}

	  public ArrayList<RequisicaoLiberacaoItemConfiguracaoDTO> findByIdHistoricoLiberacao(Integer parm) throws Exception {
	        List condicao = new ArrayList();
	        List ordenacao = new ArrayList();
	        condicao.add(new Condition("idHistoricoLiberacao", "=", parm));
	        ordenacao.add(new Order("idItemConfiguracao"));
	        return (ArrayList<RequisicaoLiberacaoItemConfiguracaoDTO>) super.findByCondition(condicao, ordenacao);
	    }

}
