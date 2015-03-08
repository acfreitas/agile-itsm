package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.CriterioAvaliacaoFornecedorDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class CriterioAvaliacaoFornecedorDao extends CrudDaoDefaultImpl {
	public CriterioAvaliacaoFornecedorDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idAvaliacaoFornecedor", "idAvaliacaoFornecedor", true, false, false, false));
		listFields.add(new Field("idCriterio", "idCriterio", true, false, false, false));
		listFields.add(new Field("valor", "valorInteger", false, false, false, false));
		listFields.add(new Field("observacoes", "obs", false, false, false, false));
		return listFields;
	}

	public String getTableName() {
		return this.getOwner() + "criterioavaliacaofornecedor";
	}

	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return CriterioAvaliacaoFornecedorDTO.class;
	}

	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}

	public Collection findByIdCriterio(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idCriterio", "=", parm));
		ordenacao.add(new Order(""));
		return super.findByCondition(condicao, ordenacao);
	}

	public void deleteByIdCriterio(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idCriterio", "=", parm));
		super.deleteByCondition(condicao);
	}

	public void deleteByIdAvaliacaoFornecedor(Integer idAvaliacaoFornecedor) throws PersistenceException {
		List lstCondicao = new ArrayList();
		lstCondicao.add(new Condition("idAvaliacaoFornecedor", "=", idAvaliacaoFornecedor));
		super.deleteByCondition(lstCondicao);
	}

	/**
	 * Lista CriterioAvaliacaoFornecedorDTO por idAvaliacaoFornecedor.
	 * 
	 * @param idBaseConhecimento
	 * @return Collection<EventoMonitConhecimentoDTO>
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Collection<CriterioAvaliacaoFornecedorDTO> listByIdAvaliacaoFornecedor(Integer idAvaliacaoFornecedor) throws PersistenceException {
		List parametros = new ArrayList();
		List listRetorno = new ArrayList();

		List list = new ArrayList();

		StringBuilder sql = new StringBuilder();

		sql.append("select criterioavaliacao.idcriterio,criterioavaliacaofornecedor.idavaliacaofornecedor , criterioavaliacao.descricao, criterioavaliacaofornecedor.valor , criterioavaliacaofornecedor.observacoes  ");
		sql.append("from criterioavaliacaofornecedor ");
		sql.append("inner join criterioavaliacao on criterioavaliacao.idcriterio = criterioavaliacaofornecedor.idcriterio ");

		if (idAvaliacaoFornecedor != null) {
			sql.append("where idavaliacaofornecedor = ?");
			parametros.add(idAvaliacaoFornecedor);
		}

		list = this.execSQL(sql.toString(), parametros.toArray());

		listRetorno.add("idCriterio");
		listRetorno.add("idAvaliacaoFornecedor");
		listRetorno.add("descricao");
		listRetorno.add("valorInteger");
		listRetorno.add("observacoes");

		if (list != null && !list.isEmpty()) {
			Collection<CriterioAvaliacaoFornecedorDTO> listCriterioAvaliacaoFornecedor = this.listConvertion(CriterioAvaliacaoFornecedorDTO.class, list, listRetorno);
			return listCriterioAvaliacaoFornecedor;
		}

		return null;
	}
}
