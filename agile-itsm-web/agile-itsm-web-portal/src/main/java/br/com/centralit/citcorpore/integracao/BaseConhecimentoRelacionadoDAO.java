/**
 * 
 */
package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.BaseConhecimentoRelacionadoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

/**
 * @author Vadoilo Damasceno
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class BaseConhecimentoRelacionadoDAO extends CrudDaoDefaultImpl {

	@Override
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();

		listFields.add(new Field("IDBASECONHECIMENTO", "idBaseConhecimento", true, false, false, false));
		listFields.add(new Field("IDBASECONHECIMENTORELACIONADO", "idBaseConhecimentoRelacionado", true, false, false, false));

		return listFields;
	}

	@Override
	public String getTableName() {
		return "BASECONHECIMENTORELACIONADO";
	}

	@Override
	public Collection list() throws PersistenceException {
		List ordenacao = new ArrayList();
		ordenacao.add(new Order("idBaseConhecimento"));
		return super.list(ordenacao);
	}

	@Override
	public Class getBean() {
		return BaseConhecimentoRelacionadoDTO.class;
	}

	public BaseConhecimentoRelacionadoDAO() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}

	/**
	 * Deleta BaseConhecimentoRelacionado da Base de Conhecimento.
	 * 
	 * @param idBaseConhecimento
	 * @throws Exception
	 * @author Vadoilo Damasceno
	 */
	public void deleteByIdConhecimento(Integer idBaseConhecimento) throws PersistenceException {

		List condicao = new ArrayList();

		condicao.add(new Condition("idBaseConhecimento", "=", idBaseConhecimento));

		this.deleteByCondition(condicao);

	}

	/**
	 * Lista BaseConhecimentoRelacionadoDTO por idBaseConhecimento.
	 * 
	 * @param idBaseConhecimento
	 * @return Collection<ImportanciaConhecimentoUsuarioDTO>
	 * @throws Exception
	 * @author Vadoilo Damasceno
	 */
	public Collection<BaseConhecimentoRelacionadoDTO> listByIdBaseConhecimento(Integer idBaseConhecimento) throws PersistenceException {

		List condicao = new ArrayList();

		List ordenacao = new ArrayList();

		condicao.add(new Condition("idBaseConhecimento", "=", idBaseConhecimento));

		ordenacao.add(new Order("idBaseConhecimento", "ASC"));

		return findByCondition(condicao, ordenacao);
	}
	
	public Collection<BaseConhecimentoRelacionadoDTO> listByIdBaseConhecimentoRelacionado(Integer idBaseConhecimentoRelacionado) throws PersistenceException {

		List condicao = new ArrayList();

		List ordenacao = new ArrayList();

		condicao.add(new Condition("idBaseConhecimentoRelacionado", "=", idBaseConhecimentoRelacionado));

		ordenacao.add(new Order("idBaseConhecimento", "ASC"));

		return findByCondition(condicao, ordenacao);
	}	

}
