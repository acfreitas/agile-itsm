/**
 * 
 */
package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ImportanciaConhecimentoUsuarioDTO;
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
public class ImportanciaConhecimentoUsuarioDAO extends CrudDaoDefaultImpl {

	public ImportanciaConhecimentoUsuarioDAO() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Collection find(IDto obj) throws PersistenceException {
		return null;
	}

	@Override
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();

		listFields.add(new Field("IDBASECONHECIMENTO", "idBaseConhecimento", true, false, false, false));
		listFields.add(new Field("IDUSUARIO", "idUsuario", true, false, false, false));
		listFields.add(new Field("GRAUIMPORTANCIAUSUARIO", "grauImportanciaUsuario", false, false, false, false));

		return listFields;
	}

	@Override
	public String getTableName() {
		return "IMPORTANCIACONHECIMENTOUSUARIO";
	}

	@Override
	public Collection list() throws PersistenceException {
		List ordenacao = new ArrayList();
		ordenacao.add(new Order("idBaseConhecimento"));
		return super.list(ordenacao);
	}

	@Override
	public Class getBean() {
		return ImportanciaConhecimentoUsuarioDTO.class;
	}

	/**
	 * Deleta ImportanciaConhecimentoUsuário da Base de Conhecimento.
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
	 * Lista ImportanciaConhecimentoUsuario por idBaseConhecimento.
	 * 
	 * @param idBaseConhecimento
	 * @return Collection<ImportanciaConhecimentoUsuarioDTO>
	 * @throws Exception
	 * @author Vadoilo Damasceno
	 */
	public Collection<ImportanciaConhecimentoUsuarioDTO> listByIdBaseConhecimento(Integer idBaseConhecimento) throws PersistenceException {

		List condicao = new ArrayList();

		List ordenacao = new ArrayList();

		condicao.add(new Condition("idBaseConhecimento", "=", idBaseConhecimento));

		ordenacao.add(new Order("idBaseConhecimento", "ASC"));

		return findByCondition(condicao, ordenacao);
	}
}
