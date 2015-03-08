package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ControleContratoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

/**
 *
 * @author pedro
 *
 */

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ControleContratoDao extends CrudDaoDefaultImpl {

	/**
     *
     */
	public ControleContratoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();

		listFields.add(new Field("idControleContrato", "idControleContrato", true, true, false, false));
		listFields.add(new Field("idContrato", "idContrato", false, false, false, false));
		listFields.add(new Field("cliente", "cliente", false, false, false, false));
		listFields.add(new Field("numeroSubscricao", "numeroSubscricao", false, false, false, false));
		listFields.add(new Field("endereco", "endereco", false, false, false, false));
		listFields.add(new Field("contato", "contato", false, false, false, false));
		listFields.add(new Field("email", "email", false, false, false, false));
		listFields.add(new Field("telefone1", "telefone1", false, false, false, false));
		listFields.add(new Field("telefone2", "telefone2", false, false, false, false));
		listFields.add(new Field("tipoSubscricao", "tipoSubscricao", false, false, false, false));
		listFields.add(new Field("url", "url", false, false, false, false));
		listFields.add(new Field("login", "login", false, false, false, false));
		listFields.add(new Field("senha", "senha", false, false, false, false));
		listFields.add(new Field("datainicio", "dataInicio", false, false, false, false));
		listFields.add(new Field("datafim", "dataFim", false, false, false, false));

		return listFields;
	}

	public String getTableName() {
		return "CONTROLECONTRATO";
	}

	public Collection find(IDto obj) throws PersistenceException {
		List ordem = new ArrayList();

		return super.find(obj, ordem);
	}

	public Collection list() throws PersistenceException {
		List list = new ArrayList();

		return super.list(list);
	}

/*	public Collection<CatalogoServicoDTO> listAllCatalogos() throws PersistenceException {
		List parametro = new ArrayList();
		String sql = "SELECT idControleContrato, tituloCatalogoServico FROM " + getTableName() + "  WHERE dataFim IS NULL";
		List<InfoCatalogoServicoDTO> dados = this.execSQL(sql, parametro.toArray());
		List<String> fields = new ArrayList<String>();
		fields.add("idControleContrato");
		fields.add("tituloCatalogoServico");
		return this.listConvertion(getBean(), dados, fields);
	}*/

/*	public boolean verificaSeCatalogoExiste(CatalogoServicoDTO catalogoServicoDTO) throws PersistenceException {

		List parametro = new ArrayList();
		List list = new ArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("select idControleContrato from " + getTableName() + "  where  tituloCatalogoServico = ? and datafim is null ");
		parametro.add(catalogoServicoDTO.getTituloCatalogoServico());

		if (catalogoServicoDTO.getidControleContrato() != null) {
			sql.append("and idControleContrato <> ?");
			parametro.add(catalogoServicoDTO.getidControleContrato());
		}
		list = this.execSQL(sql.toString(), parametro.toArray());

		if (list != null && !list.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}*/

	public Class getBean() {
		return ControleContratoDTO.class;
	}

}
