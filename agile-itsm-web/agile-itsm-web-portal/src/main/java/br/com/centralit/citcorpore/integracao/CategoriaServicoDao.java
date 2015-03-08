/**
 * CentralIT - CITSmart
 */
package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import br.com.centralit.citcorpore.bean.CategoriaServicoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

/**
 * @author rosana.godinho
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class CategoriaServicoDao extends CrudDaoDefaultImpl {

	public CategoriaServicoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();

		listFields.add(new Field("idCategoriaServico", "idCategoriaServico", true, true, false, false));
		listFields.add(new Field("idCategoriaServicoPai", "idCategoriaServicoPai", false, false, false, false));
		listFields.add(new Field("idEmpresa", "idEmpresa", false, false, false, false));
		listFields.add(new Field("nomeCategoriaServico", "nomeCategoriaServico", false, false, false, false));
		listFields.add(new Field("DataInicio", "dataInicio", false, false, false, false));
		listFields.add(new Field("DataFim", "dataFim", false, false, false, false));
		listFields.add(new Field("nomeCategoriaServicoConcatenado", "nomeCategoriaServicoConcatenado", false, false, false, false));
		

		return listFields;
	}

	public String getTableName() {
		return "CATEGORIASERVICO";
	}

	public Collection find(IDto obj) throws PersistenceException {
		return null;
	}

	public Collection list() throws PersistenceException {
		List list = new ArrayList();
		list.add(new Order("nomeCategoriaServico"));
		return super.list(list);
	}

	public Class getBean() {
		return CategoriaServicoDTO.class;
	}
	public Collection findSemPai() throws PersistenceException {
		String sql = "SELECT idCategoriaServico, idCategoriaServicoPai, idEmpresa, nomeCategoriaServico, DataInicio FROM CATEGORIASERVICO WHERE idCategoriaServicoPai IS NULL AND dataFim IS NULL ORDER BY nomeCategoriaServico ";
		List colDados = this.execSQL(sql, null);
		if (colDados != null) {
			List fields = new ArrayList();
			fields.add("idCategoriaServico");
			fields.add("idCategoriaServicoPai");
			fields.add("idEmpresa");
			fields.add("nomeCategoriaServico");
			fields.add("dataInicio");
			return this.listConvertion(CategoriaServicoDTO.class, colDados, fields);
		}
		return null;
	}

	public Collection findByIdPai(Integer idCategoriaPaiParm) throws PersistenceException {
		String sql = "SELECT idCategoriaServico, idCategoriaServicoPai, idEmpresa, nomeCategoriaServico, DataInicio FROM CATEGORIASERVICO WHERE idCategoriaServicoPai = ? AND dataFim IS NULL ORDER BY nomeCategoriaServico ";
		List colDados = this.execSQL(sql, new Object[] { idCategoriaPaiParm });
		if (colDados != null) {
			List fields = new ArrayList();
			fields.add("idCategoriaServico");
			fields.add("idCategoriaServicoPai");
			fields.add("idEmpresa");
			fields.add("nomeCategoriaServico");
			fields.add("dataInicio");
			return this.listConvertion(CategoriaServicoDTO.class, colDados, fields);
		}
		return null;
	}
	/**
	 * Lista os nomes da empresa.
	 * 
	 * @param idEmpresa
	 * @return
	 * @throws Exception
	 */
	public Collection listByEmpresa(Integer idEmpresa) throws PersistenceException {
		List list = new ArrayList();
		list.add(new Order("nomeCategoriaServico"));
		CategoriaServicoDTO obj = new CategoriaServicoDTO();
		obj.setIdEmpresa(idEmpresa);
		return super.find(obj, list);
	}

	/**
	 * Retorna lista de Categoria Serviço ativas.
	 * 
	 * @return Collection
	 * @throws Exception
	 */
	public Collection listCategoriasAtivas() throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();

		condicao.add(new Condition("dataFim", "is", null));
		ordenacao.add(new Order("nomeCategoriaServico"));

		return super.findByCondition(condicao, ordenacao);
	}
	
	/**
	 * Retorna lista de Categoria Serviço setado idPai e idFilho.
	 * 
	 * @return Collection
	 * @throws Exception
	 */
	public List<CategoriaServicoDTO> listCategoriasServicoidPaiFilho(CategoriaServicoDTO bean) throws PersistenceException {
		
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("nomeCategoriaServico", "=", bean.getNomeCategoriaServico()));
		condicao.add(new Condition("idCategoriaServicoPai", "=", bean.getIdCategoriaServicoPai()));
		return (List<CategoriaServicoDTO>) super.findByCondition(condicao, ordenacao);
	}
	
	/**
	 * Retorna lista de Categoria Serviço setado idCategoria e pai isnull.
	 * 
	 * @return Collection
	 * @throws Exception
	 */
	public List<CategoriaServicoDTO> listCategoriasServicoidPaiIsNull(CategoriaServicoDTO bean) throws PersistenceException {
		
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("nomeCategoriaServico", "=", bean.getNomeCategoriaServico()));
		condicao.add(new Condition("idCategoriaServicoPai", "is",null ));
		return (List<CategoriaServicoDTO>) super.findByCondition(condicao, ordenacao);
	}

	
	
	/**
	 * Retorna lista de Categoria Serviço ativas.
	 * 
	 * @return Collection
	 * @throws Exception
	 */
	public Collection listCategoriasAtivasByNomeConcatenado() throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();

		condicao.add(new Condition("dataFim", "is", null));
		ordenacao.add(new Order("idCategoriaServico"));

		return super.findByCondition(condicao, ordenacao);
	}

	/**
	 * Verifica se Categoria possui filho.
	 * 
	 * @param categoriaServico
	 * @return - <b>True:</b> Possui filho. - <b>False: </b>Não possui.
	 * @throws PersistenceException
	 */
	public boolean verificarSeCategoriaPossuiFilho(CategoriaServicoDTO categoriaServico) throws PersistenceException {
		StringBuilder sql = new StringBuilder();
		List parametros = new ArrayList();

		sql.append("SELECT DISTINCT categoriafilho.nomecategoriaservico FROM categoriaservico categoriapai ");
		sql.append("INNER JOIN categoriaservico categoriafilho ON categoriapai.idcategoriaservico = categoriafilho.idcategoriaservicopai ");
		sql.append("WHERE categoriapai.idcategoriaservico = ? AND categoriafilho.datafim IS NULL");
		parametros.add(categoriaServico.getIdCategoriaServico());

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
	public boolean verificarSeCategoriaPossuiServico(CategoriaServicoDTO categoriaServico) throws PersistenceException {
		StringBuilder sql = new StringBuilder();
		List parametros = new ArrayList();

		sql.append("SELECT DISTINCT servico.nomeservico FROM categoriaservico ");
		sql.append("INNER JOIN servico ON categoriaservico.idcategoriaservico = servico.idcategoriaservico ");
		sql.append("WHERE categoriaservico.idcategoriaservico = ?");
		parametros.add(categoriaServico.getIdCategoriaServico());

		List categoriasEncontradas = execSQL(sql.toString(), parametros.toArray());

		if (categoriasEncontradas != null && !categoriasEncontradas.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Verifica se categoria informada já existe.
	 * 
	 * @param categoriaServicoDTO
	 * @return true - existe; false - não existe;
	 * @throws PersistenceException
	 */
	public boolean verificarSeCategoriaExiste(CategoriaServicoDTO categoriaServicoDTO) throws PersistenceException {
		StringBuilder sql = new StringBuilder();
		List parametros = new ArrayList();

		sql.append("SELECT nomecategoriaservico FROM categoriaservico ");
		sql.append("WHERE datafim is null AND nomecategoriaservico like ?");

		parametros.add(categoriaServicoDTO.getNomeCategoriaServico());

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
	public Collection findByNomeCategoria(CategoriaServicoDTO categoriaServicoDTO) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();

		condicao.add(new Condition("nomeCategoriaServico", "=", categoriaServicoDTO.getNomeCategoriaServico())); 
		ordenacao.add(new Order("nomeCategoriaServico"));

		return super.findByCondition(condicao, ordenacao);
	}
	

	/**
	 * Encontra a categoria de serviço pelo ID
	 * @author euler.ramos
	 */
	public List<CategoriaServicoDTO> findByIdCategoriaServico(Integer id) throws PersistenceException {
		List resp = new ArrayList();

		Collection fields = getFields();
		List parametro = new ArrayList();
		List listRetorno = new ArrayList();
		String campos = "";
		for (Iterator it = fields.iterator(); it.hasNext();) {
			Field field = (Field) it.next();
			if (!campos.trim().equalsIgnoreCase("")) {
				campos = campos + ",";
			}
			campos = campos + field.getFieldDB();
			listRetorno.add(field.getFieldClass());
		}

		String sql = "SELECT " + campos + " FROM " + getTableName() + " WHERE idcategoriaservico=? and (datafim IS NULL) ORDER BY idcategoriaservico";
		parametro.add(id);
		resp = this.execSQL(sql, parametro.toArray());
		
		List result = this.engine.listConvertion(getBean(), resp, listRetorno);
		return (result == null ? new ArrayList<CategoriaServicoDTO>() : result);
	}
	
	/**
	 * Encontra a categoria de serviço pelo nome
	 * @author euler.ramos
	 */
	public List<CategoriaServicoDTO> findByNomeCategoria(String titulo) throws PersistenceException {
		List resp = new ArrayList();

		Collection fields = getFields();
		List parametro = new ArrayList();
		List listRetorno = new ArrayList();
		String campos = "";
		for (Iterator it = fields.iterator(); it.hasNext();) {
			Field field = (Field) it.next();
			if (!campos.trim().equalsIgnoreCase("")) {
				campos = campos + ",";
			}
			campos = campos + field.getFieldDB();
			listRetorno.add(field.getFieldClass());
		}

		String sql = "SELECT " + campos + " FROM " + getTableName() + " WHERE nomecategoriaservico=? and (datafim IS NULL) ORDER BY nomecategoriaservico";
		parametro.add(titulo);
		resp = this.execSQL(sql, parametro.toArray());
		
		List result = this.engine.listConvertion(getBean(), resp, listRetorno);
		return (result == null ? new ArrayList<CategoriaServicoDTO>() : result);
	}
}
