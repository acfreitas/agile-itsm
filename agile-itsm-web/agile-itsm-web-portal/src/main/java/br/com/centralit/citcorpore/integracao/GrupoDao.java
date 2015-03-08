package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class GrupoDao extends CrudDaoDefaultImpl {

	private static final String SQL_GET_GRUPOS_PESSOA = "SELECT G.nome, " + "G.sigla, G.idGrupo, G.serviceDesk " + "FROM GRUPO G " + "INNER JOIN GRUPOSEMPREGADOS GE ON GE.idGrupo = G.idGrupo ";

	private static final String SQL_GET_GRUPOS_EMPREGADO = "SELECT G.nome, " + "G.sigla, G.idGrupo, G.abertura, G.andamento, G.encerramento, G.serviceDesk " + "FROM GRUPO G "
			+ "INNER JOIN GRUPOSEMPREGADOS GE ON GE.idGrupo = G.idGrupo ";

	public GrupoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	/**
	 * Select para lista GRUPO que ainda não forma associados a EMPREGADOS.
	 */
	private static final String SQL_NOMEGRUPO = "select idgrupo,nome " + "from grupo where idgrupo not in(select idgrupo from empregados)";

	/**
	 * Select para lista GRUPO que ainda não forma associados a USUï¿½RIOS.
	 */
	private static final String SQL_NOMEUSUARIO = "select idgrupo,nome " + "from grupo where idgrupo not in(select idgrupo from usuario)";

	public Class getBean() {
		return GrupoDTO.class;
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();

		listFields.add(new Field("IDGRUPO", "idGrupo", true, true, false, false));
		listFields.add(new Field("IDEMPRESA", "idEmpresa", false, false, false, false));
		listFields.add(new Field("NOME", "nome", false, false, false, false));
		listFields.add(new Field("DATAINICIO", "dataInicio", false, false, false, false));
		listFields.add(new Field("DATAFIM", "dataFim", false, false, false, false));
		listFields.add(new Field("DESCRICAO", "descricao", false, false, false, false));
		listFields.add(new Field("sigla", "sigla", false, false, false, false));
		listFields.add(new Field("servicedesk", "serviceDesk", false, false, false, false));
		listFields.add(new Field("abertura", "abertura", false, false, false, false));
		listFields.add(new Field("andamento", "andamento", false, false, false, false));
		listFields.add(new Field("encerramento", "encerramento", false, false, false, false));
		listFields.add(new Field("comiteconsultivomudanca", "comiteConsultivoMudanca", false, false, false, false));
		listFields.add(new Field("permitesuspensaoreativacao", "permiteSuspensaoReativacao", false, false, false, false));

		return listFields;
	}

	public String getTableName() {
		return "GRUPO";
	}

	public Collection find(IDto obj) throws PersistenceException {
		List ordem = new ArrayList();
		ordem.add(new Order("nome"));
		return super.find(obj, ordem);
	}

	public Collection list() throws PersistenceException {
		List list = new ArrayList();
		list.add(new Order("nome"));
		return super.list(list);
	}

	public Collection listarGruposAtivos() throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("dataFim", "is", null));
		ordenacao.add(new Order("nome"));
		return super.findByCondition(condicao, ordenacao);
	}

	/**
	 * Retorna lista de GRUPO que ainda não estï¿½o associados a EMPREGADOS.
	 * 
	 * @return
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Collection listarGrupoEmpregado() throws PersistenceException {
		String sql = SQL_NOMEGRUPO;
		List lista = new ArrayList();
		lista = this.execSQL(sql, null);
		List listRetorno = new ArrayList();
		listRetorno.add("idGrupo");
		listRetorno.add("nome");
		List result = this.engine.listConvertion(getBean(), lista, listRetorno);
		return result;
	}

	/**
	 * Retorna lista de GRUPO que ainda não estï¿½o associados a USUï¿½RIO.
	 * 
	 * @return
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Collection listarGrupoUsuario() throws PersistenceException {
		String sql = SQL_NOMEUSUARIO;
		List lista = new ArrayList();
		lista = this.execSQL(sql, null);
		List listRetorno = new ArrayList();
		listRetorno.add("idGrupo");
		listRetorno.add("nome");
		List result = this.engine.listConvertion(getBean(), lista, listRetorno);
		return result;
	}

	/**
	 * Retorna Lista de Grupo do Empregado.
	 * 
	 * @param idEmpregado
	 * @return Collection<GrupoDTO>
	 * @throws Exception
	 */
	public Collection<GrupoDTO> getGruposByIdEmpregado(Integer idEmpregado) throws PersistenceException {
		Object[] objs = new Object[] { idEmpregado };

		String sql = SQL_GET_GRUPOS_PESSOA;
		sql += " WHERE ";
		sql += " (GE.idEmpregado = ?) AND datafim is NULL";
		sql += " ORDER BY G.nome";

		List lista = this.execSQL(sql, objs);

		List listRetorno = new ArrayList();
		listRetorno.add("nome");
		listRetorno.add("sigla");
		listRetorno.add("idGrupo");
		listRetorno.add("serviceDesk");

		List result = this.engine.listConvertion(getBean(), lista, listRetorno);
		if (result == null || result.size() == 0)
			return null;
		return result;
	}

	/**
	 * Retorna Grupos do Empregado com todos os dados de retorno.
	 * 
	 * @param idEmpregado
	 * @return Collection<GrupoDTO>
	 * @throws Exception
	 */
	public Collection getGruposByIdEmpregadoAll(Integer idEmpregado) throws PersistenceException {
		Object[] objs = new Object[] { idEmpregado };

		String sql = SQL_GET_GRUPOS_EMPREGADO;
		sql += " WHERE ";
		sql += " (GE.idEmpregado = ?) AND datafim is NULL";
		sql += " ORDER BY G.nome";

		List lista = this.execSQL(sql, objs);

		List listRetorno = new ArrayList();
		listRetorno.add("nome");
		listRetorno.add("sigla");
		listRetorno.add("idGrupo");
		listRetorno.add("abertura");
		listRetorno.add("andamento");
		listRetorno.add("encerramento");
		listRetorno.add("serviceDesk");

		List result = this.engine.listConvertion(getBean(), lista, listRetorno);
		if (result == null || result.size() == 0)
			return null;
		return result;
	}

	/**
	 * Retorna Grupos do Contrato.
	 * 
	 * @param idContrato
	 * @return Collection<GrupoDTO>
	 * @throws Exception
	 */
	public Collection<GrupoDTO> listGrupoByIdContrato(Integer idContrato) throws PersistenceException {
		StringBuilder sql = new StringBuilder();
		List parametro = new ArrayList();
		List list = new ArrayList();

		sql.append("select grupo.idgrupo, grupo.nome from contratosgrupos ");
		sql.append("inner join grupo on contratosgrupos.idgrupo = grupo.idgrupo ");
		sql.append("where idcontrato = ? ");
		parametro.add(idContrato);

		list = this.execSQL(sql.toString(), parametro.toArray());

		List listRetorno = new ArrayList();
		listRetorno.add("idGrupo");
		listRetorno.add("nome");

		List result = this.engine.listConvertion(getBean(), list, listRetorno);
		if (result == null || result.size() == 0) {
			return null;
		} else {
			return result;
		}

	}
	
	/**
	 * Retorna Grupos do Contrato.
	 * 
	 * @param idContrato
	 * @return Collection<GrupoDTO>
	 * @throws Exception
	 */
	public Collection<GrupoDTO> listGruposAtivosByIdContrato(Integer idContrato) throws PersistenceException {
		StringBuilder sql = new StringBuilder();
		List parametro = new ArrayList();
		List list = new ArrayList();

		sql.append("select grupo.idgrupo, grupo.nome from contratosgrupos ");
		sql.append("inner join grupo on contratosgrupos.idgrupo = grupo.idgrupo ");
		sql.append("where datafim is NULL and idcontrato = ? ");
		parametro.add(idContrato);

		list = this.execSQL(sql.toString(), parametro.toArray());

		List listRetorno = new ArrayList();
		listRetorno.add("idGrupo");
		listRetorno.add("nome");

		List result = this.engine.listConvertion(getBean(), list, listRetorno);
		if (result == null || result.size() == 0) {
			return null;
		} else {
			return result;
		}

	}

	public GrupoDTO restoreBySigla(String sigla) throws PersistenceException {
		List ordem = new ArrayList();
		ordem.add(new Order("sigla"));
		GrupoDTO grupoDto = new GrupoDTO();
		grupoDto.setSigla(sigla);
		List<GrupoDTO> col = (List) super.find(grupoDto, ordem);
		if (col == null || col.size() == 0)
			return null;
		grupoDto = null;
		for (GrupoDTO grupoAuxDto : col) {
			if (grupoAuxDto.getDataFim() == null || (grupoAuxDto.getDataFim() != null && grupoAuxDto.getDataFim().compareTo(UtilDatas.getDataAtual()) >= 0)) {
				grupoDto = grupoAuxDto;
				break;
			}
		}
		return grupoDto;
	}

	public GrupoDTO restoreBySigla(GrupoDTO grupoDto) throws PersistenceException {
		String sql = "SELECT * FROM " + getTableName() + " WHERE sigla = ? AND idgrupo <> ? ";
		List param = new ArrayList();
		List retorno = new ArrayList();
		param.add(grupoDto.getSigla());
		param.add(grupoDto.getIdGrupo());
		retorno = execSQL(sql, param.toArray());
		if (retorno != null && !retorno.isEmpty()) {
			return (GrupoDTO) this.listConvertion(getBean(), retorno, (List) getFields()).get(0);
		} else {
			return null;
		}
	}

	public Collection<GrupoDTO> listGruposServiceDesk() throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("serviceDesk", "=", "S"));
		condicao.add(new Condition("dataFim", "is", null));
		ordenacao.add(new Order("nome"));
		return super.findByCondition(condicao, ordenacao);
		
	}
	
	/**
	 * Bruno.Aquino - Listar todos os grupos que o usuário estiver vinculado.
	 * 
	 * @param idUsuário
	 * @return Lista de Grupos
	 * @throws PersistenceException
	 */
	public Collection<GrupoDTO> listGruposPorUsuario(int idUsuario) throws PersistenceException {
		List parametro = new ArrayList();
		List fields = (List) getFields();
		List list = new ArrayList();
		StringBuilder sql = new StringBuilder();
		List retorno = new ArrayList();
		for (Iterator it = fields.iterator(); it.hasNext();) {
			Field field = (Field) it.next();
			retorno.add(field.getFieldClass());
		}
		sql.append("select gr.idgrupo,gr.idempresa,gr.nome,gr.datainicio,gr.datafim,gr.descricao,gr.sigla,gr.servicedesk,gr.abertura,gr.andamento,gr.encerramento,gr.comiteconsultivomudanca, gr.permitesuspensaoreativacao from ");
		sql.append("grupo gr ");
		sql.append("INNER JOIN gruposempregados ge ON gr.idgrupo = ge.idgrupo ");
		sql.append("INNER JOIN usuario u ON u.idempregado = ge.idempregado ");
		sql.append("WHERE ");
		sql.append("u.idusuario = ? ");
		sql.append("AND gr.datafim IS NULL ");
		sql.append("ORDER BY gr.nome ");
		parametro.add(idUsuario);
		list = this.execSQL(sql.toString(), parametro.toArray());
		return listConvertion(getBean(), list, retorno);
	}
	
	public Collection<GrupoDTO> listGruposServiceDeskByIdContrato(Integer idContratoParm) throws PersistenceException {
		List list = new ArrayList();
		String sql = "SELECT ";
		List fields = (List) getFields();
		List retorno = new ArrayList();
		boolean b = false;
		for (Iterator it = fields.iterator(); it.hasNext();) {
			Field field = (Field) it.next();
			if (b) {
				sql += ",";
			}
			sql += field.getFieldDB();
			b = true;

			retorno.add(field.getFieldClass());
		}
		sql += " FROM " + this.getTableName();
		sql += " WHERE serviceDesk = 'S' AND dataFim IS NULL ";
		sql += " AND idgrupo in (SELECT idgrupo FROM contratosgrupos where IDCONTRATO = ?) ";
		sql += "order by sigla  ";

		list = this.execSQL(sql, new Object[] { idContratoParm });
		return this.listConvertion(getBean(), list, retorno);
	}

	/**
	 * Verifica se Grupo informada existe.
	 * 
	 * @param grupo
	 * @return true - existe; false - não existe;
	 * @throws PersistenceException
	 */
	public boolean verificarSeGrupoExiste(GrupoDTO grupo) throws PersistenceException {
		List parametro = new ArrayList();
		List list = new ArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("select idGrupo from " + getTableName() + "  where  nome = ? and datafim is null ");
		parametro.add(grupo.getNome());

		if (grupo.getIdGrupo() != null) {
			sql.append("and idgrupo <> ?");
			parametro.add(grupo.getIdGrupo());
		}
		list = this.execSQL(sql.toString(), parametro.toArray());

		if (list != null && !list.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Retorna lista de e-mails que estão cadastrados para receber notificação
	 * 
	 * @param idGrupo
	 * @return List<String> emails
	 * @throws Exception
	 */
	public List<String> listarEmailsPorGrupo(Integer idGrupo) throws PersistenceException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT emp.email ");
		sql.append("FROM gruposempregados AS ge ");
		sql.append("INNER JOIN empregados AS emp ");
		sql.append("ON ge.idempregado = emp.idempregado ");
		sql.append("WHERE 	ge.idgrupo = ? ");
		sql.append("AND emp.email IS NOT NULL ");
		sql.append("AND enviaemail = 'S' ");
		sql.append("UNION ");
		sql.append("SELECT email ");
		sql.append("FROM gruposemails ");
		sql.append("WHERE idgrupo = ? ");
		sql.append("AND email IS NOT NULL ");

		List<Object[]> dados = this.execSQL(sql.toString(), new Object[] { idGrupo, idGrupo });

		List<String> retorno = new ArrayList<String>();
		for (Object[] object : dados) {
			retorno.add(object[0].toString().trim());
		}

		return retorno;

	}

	public List<String> listarPessoasEmailPorGrupo(Integer idGrupo) throws PersistenceException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT emp.nome, emp.email ");
		sql.append("FROM gruposempregados AS ge ");
		sql.append("INNER JOIN empregados AS emp ");
		sql.append("ON ge.idempregado = emp.idempregado ");
		sql.append("WHERE 	ge.idgrupo = ? ");
		sql.append("AND emp.email IS NOT NULL ");
		sql.append("AND enviaemail = 'S' ");
		sql.append("UNION ");
		sql.append("SELECT nome, email ");
		sql.append("FROM gruposemails ");
		sql.append("WHERE idgrupo = ? ");
		sql.append("AND email IS NOT NULL ");

		List<Object[]> dados = this.execSQL(sql.toString(), new Object[] { idGrupo, idGrupo });

		List<String> retorno = new ArrayList<String>();
		for (Object[] object : dados) {
			retorno.add(object[0].toString().trim());
			retorno.add(object[1].toString().trim());
		}

		return retorno;

	}

	public Collection<GrupoDTO> listGruposComite() throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("comiteConsultivoMudanca", "=", "S"));
		condicao.add(new Condition("dataFim", "is", null));
		ordenacao.add(new Order("sigla"));
		return super.findByCondition(condicao, ordenacao);
	}

	public Collection<GrupoDTO> listGruposNaoComite() throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("dataFim", "is", null));
		ordenacao.add(new Order("sigla"));
		return super.findByCondition(condicao, ordenacao);
	}

	/**
	 * Retorna o nome do grupo pelo ID
	 * 
	 * @param idGrupo
	 * @return
	 * @throws Exception
	 */
	public GrupoDTO listGrupoById(Integer idGrupo) throws PersistenceException {
		List ordem = new ArrayList();
		ordem.add(new Order("idgrupo"));
		GrupoDTO grupo = new GrupoDTO();
		grupo.setIdGrupo(idGrupo);
		List col = (List) find(grupo);
		if (col == null || col.size() == 0)
			return null;
		return (GrupoDTO) col.get(0);
	}

	public Collection<GrupoDTO> listAllGrupos() throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("dataFim", "is", null));
		ordenacao.add(new Order("nome"));
		return super.findByCondition(condicao, ordenacao);
	}

	/**
	 * Retonra uma lista de grupos ativos
	 * 
	 * @return
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Collection<GrupoDTO> listaGruposAtivos() throws PersistenceException {
		List lista = new ArrayList();
		List listRetorno = new ArrayList();

		StringBuilder sql = new StringBuilder();

		sql.append("select nome,idgrupo from grupo where datafim is null order by nome,idgrupo ");

		lista = this.execSQL(sql.toString(), null);
		listRetorno.add("nome");
		listRetorno.add("idGrupo");
		if (lista != null && !lista.isEmpty()) {
			Collection<GrupoDTO> listaGruposAtivos = this.engine.listConvertion(getBean(), lista, listRetorno);
			return listaGruposAtivos;
		}
		return null;

	}

	public Collection<GrupoDTO> listaGrupoEmpregado(Integer idEmpregado) throws PersistenceException {
		List lista = new ArrayList();
		List listRetorno = new ArrayList();
		List param = new ArrayList();
		StringBuilder sql = new StringBuilder();

		sql.append(" SELECT gr.idgrupo as idGrupo,gr.nome as nomeGrupo ");
		sql.append(" FROM gruposempregados gre ");
		sql.append(" inner join grupo gr on gre.idgrupo = gr.idgrupo ");
		sql.append(" inner join empregados em on gre.idempregado=em.idempregado ");
		sql.append(" where em.idempregado = ? ");

		param.add(idEmpregado);

		lista = this.execSQL(sql.toString(), param.toArray());
		listRetorno.add("idGrupo");
		listRetorno.add("nome");
		if (lista != null && !lista.isEmpty()) {
			Collection<GrupoDTO> listResultado = this.engine.listConvertion(GrupoDTO.class, lista, listRetorno);
			return listResultado;
		}
		return null;

	}

}
