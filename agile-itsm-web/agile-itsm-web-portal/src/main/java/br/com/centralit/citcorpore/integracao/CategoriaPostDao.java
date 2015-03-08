/**
 * CentralIT - CITSmart
 */
package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.CategoriaPostDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

/**
 * @author Flávio.santana
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class CategoriaPostDao extends CrudDaoDefaultImpl {

	public CategoriaPostDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();

		listFields.add(new Field("idCategoriaPost", "idCategoriaPost", true, true, false, false));
		listFields.add(new Field("idCategoriaPostPai", "idCategoriaPostPai", false, false, false, false));
		listFields.add(new Field("nomeCategoria", "nomeCategoria", false, false, false, false));
		listFields.add(new Field("dataInicio", "dataInicio", false, false, false, false));
		listFields.add(new Field("dataFim", "dataFim", false, false, false, false));		

		return listFields;
	}

	public String getTableName() {
		return "categoriapost";
	}

	public Collection find(IDto obj) throws PersistenceException {
		return null;
	}

	public Collection list() throws PersistenceException {
		List list = new ArrayList();
		list.add(new Order("nomeCategoria"));
		return super.list(list);
	}

	public Class getBean() {
		return CategoriaPostDTO.class;
	}
	public Collection findSemPai() throws PersistenceException {
		String sql = "SELECT idCategoriaPost, idCategoriaPostPai, nomeCategoria, dataInicio FROM categoriapost WHERE idCategoriaPostPai IS NULL AND dataFim IS NULL ORDER BY nomeCategoria ";
		List colDados = this.execSQL(sql, null);
		if (colDados != null) {
			List fields = new ArrayList();
			fields.add("idCategoriaPost");
			fields.add("idCategoriaPostPai");
			fields.add("nomeCategoria");
			fields.add("dataInicio");
			return this.listConvertion(getBean(), colDados, fields);
		}
		return null;
	}

	public Collection findByIdPai(Integer idCategoriaPaiParm) throws PersistenceException {
		String sql = "SELECT idCategoriaPost, idCategoriaPostPai, nomeCategoria, dataInicio FROM categoriapost WHERE idCategoriaPostPai = ? AND AND dataFim IS NULL ORDER BY nomeCategoria";
		List colDados = this.execSQL(sql, new Object[] { idCategoriaPaiParm });
		if (colDados != null) {
			List fields = new ArrayList();
			fields.add("idCategoriaPost");
			fields.add("idCategoriaPostPai");
			fields.add("nomeCategoria");
			fields.add("dataInicio");
			return this.listConvertion(getBean(), colDados, fields);
		}
		return null;
	}

	/**
	 * Retorna lista de Categoria Posts ativas.
	 * 
	 * @return Collection
	 * @throws Exception
	 */
	public Collection listCategoriasAtivas() throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();

		condicao.add(new Condition("dataFim", "is", null));
		ordenacao.add(new Order("nomeCategoria"));

		return super.findByCondition(condicao, ordenacao);
	}
	
	/**
	 * Retorna lista de Categoria Serviço setado idPai e idFilho.
	 * 
	 * @return Collection
	 * @throws Exception
	 */
	public List<CategoriaPostDTO> listCategoriasPostidPaiFilho(CategoriaPostDTO bean) throws PersistenceException {
		
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("nomeCategoria", "=", bean.getNomeCategoria()));
		condicao.add(new Condition("idCategoriaPostPai", "=", bean.getIdCategoriaPostPai()));
		return (List<CategoriaPostDTO>) super.findByCondition(condicao, ordenacao);
	}
	
	/**
	 * Retorna lista de Categoria Serviço setado idCategoria e pai isnull.
	 * 
	 * @return Collection
	 * @throws Exception
	 */
	public List<CategoriaPostDTO> listCategoriasPostidPaiIsNull(CategoriaPostDTO bean) throws PersistenceException {
		
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("nomeCategoria", "=", bean.getNomeCategoria()));
		condicao.add(new Condition("idCategoriaPostPai", "is",null ));
		return (List<CategoriaPostDTO>) super.findByCondition(condicao, ordenacao);
	}


	/**
	 * Verifica se categoria informada já existe.
	 * 
	 * @param categoriaPostDTO
	 * @return true - existe; false - não existe;
	 * @throws PersistenceException
	 */
	public boolean verificarSeCategoriaExiste(CategoriaPostDTO categoriaPostDTO) throws PersistenceException {
		StringBuilder sql = new StringBuilder();
		List parametros = new ArrayList();

		sql.append("SELECT nomecategoria FROM categoriapost ");
		sql.append("WHERE datafim is null AND nomecategoria like ?");

		parametros.add(categoriaPostDTO.getNomeCategoria());

		List categorias = execSQL(sql.toString(), parametros.toArray());

		if (categorias != null && !categorias.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Retorna lista de Categoria Serviço por nome.
	 * 
	 * @return Collection
	 * @throws Exception
	 */
	public Collection findByNomeCategoria(CategoriaPostDTO categoriaPostDTO) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();

		condicao.add(new Condition("nomeCategoria", "=", categoriaPostDTO.getNomeCategoria())); 
		ordenacao.add(new Order("nomeCategoria"));

		return super.findByCondition(condicao, ordenacao);
	}
	
	/**
	 * Verifica se Categoria possui filho.
	 * 
	 * @param categoriaServico
	 * @return - <b>True:</b> Possui filho. - <b>False: </b>Não possui.
	 * @throws PersistenceException
	 */
	public boolean verificarSeCategoriaPossuiFilho(CategoriaPostDTO categoriaPost) throws PersistenceException {
		StringBuilder sql = new StringBuilder();
		List parametros = new ArrayList();

		sql.append("SELECT DISTINCT categoriafilho.nomecategoria FROM categoriapost categoriapai ");
		sql.append("INNER JOIN categoriapost categoriafilho ON categoriapai.idcategoriapost = categoriafilho.idcategoriapostpai ");
		sql.append("WHERE categoriapai.idcategoriapost = ? AND categoriafilho.datafim IS NULL");
		parametros.add(categoriaPost.getIdCategoriaPost());

		List filhos = execSQL(sql.toString(), parametros.toArray());

		if (filhos != null && !filhos.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Verifica se Categoria possui Serviço associado
	 * 
	 * @param categoriaServico
	 * @return - <b>True:</b> Possui filho. - <b>False: </b>Não possui.
	 * @throws PersistenceException
	 */
	public boolean verificarSeCategoriaPossuiPost(CategoriaPostDTO categoriaServico) throws PersistenceException {
		StringBuilder sql = new StringBuilder();
		List parametros = new ArrayList();

		sql.append("SELECT DISTINCT post.titulo FROM categoriapost ");
		sql.append("INNER JOIN post ON categoriapost.idcategoriapost = post.idcategoriapost ");
		sql.append("WHERE categoriapost.idcategoriapost = ?");
		parametros.add(categoriaServico.getIdCategoriaPost());

		List categoriasEncontradas = execSQL(sql.toString(), parametros.toArray());

		if (categoriasEncontradas != null && !categoriasEncontradas.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	
}
