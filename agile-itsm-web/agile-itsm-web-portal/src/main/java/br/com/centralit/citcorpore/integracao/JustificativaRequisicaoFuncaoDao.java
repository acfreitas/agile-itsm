package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.JustificativaRequisicaoFuncaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class JustificativaRequisicaoFuncaoDao extends CrudDaoDefaultImpl {

	public JustificativaRequisicaoFuncaoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Collection find(IDto obj) throws PersistenceException {

		return null;
	}

	@Override
	public Collection<Field> getFields() {

		Collection<Field> listFields = new ArrayList<>();

		listFields.add(new Field("IDJUSTIFICATIVA", "idJustificativa", true, true, false, false));
		listFields.add(new Field("DESCRICAO", "descricao", false, false, false, false));
		listFields.add(new Field("SITUACAO", "situacao", false, false, false, false));

		return listFields;
	}

	@Override
	public String getTableName() {
		return "JUSTIFICATIVAREQUISICAOFUNCAO";
	}

	@Override
	public Collection list() throws PersistenceException {
		List list = new ArrayList();
		list.add(new Order("descricao"));
		return super.list(list);
	}

	@Override
	public Class getBean() {
		return JustificativaRequisicaoFuncaoDTO.class;
	}

	/**
	 * Retorna lista de status de usuário.
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public boolean consultarJustificativasAtivas(JustificativaRequisicaoFuncaoDTO obj) throws PersistenceException {
		List parametro = new ArrayList();
		List list = new ArrayList();
		String sql = "select idjustificativa From " + getTableName() + "  where  descricao = ? ";
		
		if(obj.getIdJustificativa() != null){
			sql+=" and idjustificativa <> "+ obj.getIdJustificativa();
		}
		
		parametro.add(obj.getDescricao());
		list = this.execSQL(sql, parametro.toArray());
		if (list != null && !list.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	
	//TODO
	// remoção do método
	public boolean validaInsert(JustificativaRequisicaoFuncaoDTO obj){
		
		
		return false;
		
	}

	public Collection findByDescricao(JustificativaRequisicaoFuncaoDTO justificativaRequisicaoFuncaoDTO) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();

		condicao.add(new Condition("descricao", "=", justificativaRequisicaoFuncaoDTO.getDescricao())); 
		ordenacao.add(new Order("descricao"));
		return super.findByCondition(condicao, ordenacao);
	}
	
	public Collection<JustificativaRequisicaoFuncaoDTO>  seJustificativaJaCadastrada(JustificativaRequisicaoFuncaoDTO justificativaRequisicaoFuncaoDTO) throws PersistenceException {
		List parametro = new ArrayList();
		List list = new ArrayList();
		String sql = "";
		sql = " select lower(descricao) from justificativaRequisicaoFuncao where descricao = lower(?) ";

		parametro.add(justificativaRequisicaoFuncaoDTO.getDescricao().trim().toLowerCase());
		list = this.execSQL(sql, parametro.toArray());
		return list;
	}
	
	public Collection<JustificativaRequisicaoFuncaoDTO> listarAtivos() throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();

		ordenacao.add(new Order("descricao"));
		condicao.add(new Condition("situacao", "=", "A"));
		return super.findByCondition(condicao, ordenacao);
	}
	
}
