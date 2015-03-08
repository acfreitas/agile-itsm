package br.com.centralit.citcorpore.integracao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.bean.RelatorioGruposUsuarioDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.SQLConfig;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class GrupoEmpregadoDao extends CrudDaoDefaultImpl {

	public GrupoEmpregadoDao() {

		super(Constantes.getValue("DATABASE_ALIAS"), null);

	}

	public Class getBean() {
		return GrupoEmpregadoDTO.class;
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();

		listFields.add(new Field("idGrupo", "idGrupo", true, false, false, false));
		listFields.add(new Field("idEmpregado", "idEmpregado", true, false, false, false));
		listFields.add(new Field("enviaEmail", "enviaEmail", true, false, false, false));

		return listFields;
	}

	public String getTableName() {
		return "GRUPOSEMPREGADOS";
	}

	public Collection find(IDto obj) throws PersistenceException {
		return null;
	}

	public Collection list() throws PersistenceException {
		return null;
	}

	public void deleteByIdEmpregado(Integer idEmpregado) throws PersistenceException {
		List lstCondicao = new ArrayList();
		lstCondicao.add(new Condition(Condition.AND, "idEmpregado", "=", idEmpregado));
		super.deleteByCondition(lstCondicao);
	}

	public void deleteByIdGrupo(Integer idGrupo) throws PersistenceException {
		List lstCondicao = new ArrayList();
		lstCondicao.add(new Condition("idGrupo", "=", idGrupo));
		super.deleteByCondition(lstCondicao);
	}

	/**
	 * Deleta GrupoEmpregado por idGrupo e idEmpregado.
	 *
	 * @param idGrupo
	 * @param idEmpregado
	 * @throws Exception
	 */
	public void deleteByIdGrupoAndEmpregado(Integer idGrupo, Integer idEmpregado) throws PersistenceException {
		List lstCondicao = new ArrayList();
		lstCondicao.add(new Condition("idGrupo", "=", idGrupo));
		lstCondicao.add(new Condition(Condition.AND, "idEmpregado", "=", idEmpregado));
		super.deleteByCondition(lstCondicao);
	}

	public Collection findByIdEmpregado(Integer idEmpregado) throws PersistenceException {
		Object[] objs = new Object[] { idEmpregado };
		String sql = "SELECT G.idGrupo, G.sigla, GE.idEmpregado  FROM gruposempregados GE INNER JOIN grupo G ON G.idGrupo = GE.idGrupo  WHERE GE.idEmpregado = ? ";
		List lista = this.execSQL(sql, objs);

		List listRetorno = new ArrayList();
		listRetorno.add("idGrupo");
		listRetorno.add("sigla");
		listRetorno.add("idEmpregado");

		return this.engine.listConvertion(getBean(), lista, listRetorno);
	}

	public Collection findAtivosByIdEmpregado(Integer idEmpregado) throws PersistenceException {
		Object[] objs = new Object[] { idEmpregado };
		String sql = "SELECT G.idGrupo, G.sigla, GE.idEmpregado  FROM GruposEmpregados GE INNER JOIN Grupo G ON G.idGrupo = GE.idGrupo  WHERE G.dataFim IS NULL AND GE.idEmpregado = ? ";
		List lista = this.execSQL(sql, objs);

		List listRetorno = new ArrayList();
		listRetorno.add("idGrupo");
		listRetorno.add("sigla");
		listRetorno.add("idEmpregado");

		return this.engine.listConvertion(getBean(), lista, listRetorno);
	}

	public Collection<GrupoEmpregadoDTO> findByIdGrupo(Integer idGrupo) throws PersistenceException {
		Object[] objs = new Object[] { idGrupo };
		StringBuilder sql = new StringBuilder();
		String orderBy = "";
		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
			orderBy = "ORDER BY LTRIM(RTRIM(EM.NOME))";
		} else {
			orderBy = "ORDER BY TRIM(EM.NOME)";
		}
		sql.append("SELECT G.idGrupo, G.sigla, GE.idEmpregado  FROM GruposEmpregados GE INNER JOIN Grupo G ON G.idGrupo = GE.idGrupo "
				+ "INNER JOIN EMPREGADOS EM ON GE.IDEMPREGADO = EM.IDEMPREGADO WHERE GE.idGrupo = ? " + orderBy + " ");

		List lista = this.execSQL(sql.toString(), objs);

		List listRetorno = new ArrayList();
		listRetorno.add("idGrupo");
		listRetorno.add("sigla");
		listRetorno.add("idEmpregado");
		if (lista != null && !lista.isEmpty()) {
			return this.engine.listConvertion(getBean(), lista, listRetorno);
		} else {
			return null;
		}
	}

	/**
	 * Retorna Lista de GrupoEmpregadoDTO com informações do Grupo e Empregados.
	 *
	 * @param idGrupo
	 *            - Identificador único do Grupo.
	 * @return listGrupoEmpregadoDTO - Lista de GrupoEmpregadoDTO com informações do empregado.
	 * @throws PersistenceException
	 * @author valdoilo.damasceno
	 */
	public Collection<GrupoEmpregadoDTO> findGrupoAndEmpregadoByIdGrupo(Integer idGrupo) throws PersistenceException {

		List camposRetorno = new ArrayList();
		List parametros = new ArrayList();
		StringBuilder orderBy = new StringBuilder();
		StringBuilder sql = new StringBuilder();

		parametros.add(idGrupo);

		camposRetorno.add("idGrupo");
		camposRetorno.add("sigla");
		camposRetorno.add("idEmpregado");
		camposRetorno.add("nomeEmpregado");
		camposRetorno.add("enviaEmail");

		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
			orderBy.append(" ORDER BY LTRIM(RTRIM(empregado.nome)) ");
		} else {
			orderBy.append(" ORDER BY TRIM(empregado.nome)");
		}

		sql.append("SELECT grupo.idgrupo, grupo.sigla, empregado.idempregado, empregado.nome, grupoemp.enviaemail ");
		sql.append(" FROM gruposempregados grupoemp ");
		sql.append(" INNER JOIN empregados empregado ON empregado.idempregado = grupoemp.idempregado ");
		sql.append(" INNER JOIN grupo grupo ON grupo.idgrupo = grupoemp.idgrupo ");
		sql.append(" WHERE grupo.idgrupo = ? AND empregado.datafim IS NULL ");
		sql.append(orderBy.toString());

		List list = this.execSQL(sql.toString(), parametros.toArray());

		return this.engine.listConvertion(this.getBean(), list, camposRetorno);

	}

	public Collection<GrupoEmpregadoDTO> findUsariosGrupo() throws PersistenceException {
		Object[] objs = new Object[] {};
		String sql;
		if (CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase("SQLSERVER")) {

			sql = " select em.idEmpregado from GruposEmpregados gp inner join empregados em on gp.idempregado = em.idempregado "
					+ " where idgrupo in (select idgrupo from Grupo where servicedesk  = 'S' and datafim is null) " + " group by em.idEmpregado, em.nome order by RTRIM(LTRIM(em.nome))";

		} else {
			sql = "select em.idEmpregado " + "from GruposEmpregados gp " + "inner join empregados em " + "on gp.idempregado = em.idempregado "
					+ "where idgrupo in (select idgrupo from Grupo where servicedesk  = 'S' and datafim is null) " + "group by em.idEmpregado, em.nome " + "order by trim(em.nome) ";
		}

		List lista = this.execSQL(sql, objs);

		List listRetorno = new ArrayList();
		listRetorno.add("idEmpregado");
		if (lista != null && !lista.isEmpty()) {
			return this.engine.listConvertion(getBean(), lista, listRetorno);
		} else {
			return null;
		}
	}

	/**
	 * Retorna GrupoEmpregado do Tipo HelpDesk de acordo com o ID Contrato informado.
	 *
	 * @param idContrato
	 *            - Identificador do contrato.
	 * @return Collection<GrupoEmpregadoDTO>
	 * @throws PersistenceException
	 * @author valdoilo.damasceno
	 */
	public Collection<GrupoEmpregadoDTO> findGrupoEmpregadoHelpDeskByIdContrato(Integer idContrato) throws PersistenceException {

		if (idContrato != null) {

			Object[] parametros = new Object[] { idContrato };
			StringBuilder sql = new StringBuilder();

			if (CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase("SQLSERVER")) {

				sql.append("select em.idempregado from gruposempregados gp inner join empregados em on gp.idempregado = em.idempregado where idgrupo in ( ");
				sql.append("select grupo.idgrupo from grupo inner join contratosgrupos on grupo.idgrupo = contratosgrupos.idgrupo where servicedesk  = 'S' and datafim is null and idcontrato = ?)");
				sql.append("group by em.idempregado, em.nome order by RTRIM(LTRIM(em.nome))");

			} else {
				sql.append("select em.idempregado from gruposempregados gp inner join empregados em on gp.idempregado = em.idempregado where idgrupo in ( ");
				sql.append("select grupo.idgrupo from grupo inner join contratosgrupos on grupo.idgrupo = contratosgrupos.idgrupo where servicedesk  = 'S' and datafim is null and idcontrato = ?)");
				sql.append("group by em.idempregado, em.nome order by trim(em.nome)");
			}

			List lista = null;
			lista = this.execSQL(sql.toString(), parametros);

			List listRetorno = new ArrayList();
			listRetorno.add("idEmpregado");
			if (lista != null && !lista.isEmpty()) {

				return this.engine.listConvertion(getBean(), lista, listRetorno);

			} else {

				return null;

			}

		} else {

			return null;
		}
	}

	public Collection<RelatorioGruposUsuarioDTO> listaRelatorioGruposUsuario(Integer idColaborador) throws PersistenceException {
		List listRetorno = new ArrayList();
		List param = new ArrayList();
		List lista = new ArrayList();
		StringBuilder sql = new StringBuilder();

		sql.append(" SELECT em.nome as nomeColaborador, gr.idgrupo as idGrupo,gr.nome as nomeGrupo ");
		sql.append(" FROM gruposempregados gre ");
		sql.append(" inner join grupo gr on gre.idgrupo = gr.idgrupo ");
		sql.append(" inner join empregados em on gre.idempregado=em.idempregado ");
		if (idColaborador != null) {
			sql.append(" where em.idempregado = ? ");
		}
		sql.append(" order by em.idempregado, idgrupo ");
		if (idColaborador != null) {
			param.add(idColaborador);
		}
		if (param != null) {
			lista = this.execSQL(sql.toString(), param.toArray());
		}

		listRetorno.add("nomeColaborador");
		listRetorno.add("idGrupo");
		listRetorno.add("nomeGrupo");

		if (lista != null && !lista.isEmpty()) {
			Collection<RelatorioGruposUsuarioDTO> listResultado = this.engine.listConvertion(RelatorioGruposUsuarioDTO.class, lista, listRetorno);
			return listResultado;
		}
		return null;
	}

	public Collection findByIdEmpregadoNome(Integer idEmpregado) throws PersistenceException {
		Object[] objs = new Object[] { idEmpregado };
		String sql = "SELECT G.idGrupo, G.nome as sigla, GE.idEmpregado " + "  FROM gruposempregados GE INNER JOIN grupo G ON G.idGrupo = GE.idGrupo " + " WHERE GE.idEmpregado = ? order by sigla ";
		List lista = this.execSQL(sql, objs);

		List listRetorno = new ArrayList();
		listRetorno.add("idGrupo");
		listRetorno.add("sigla");
		listRetorno.add("idEmpregado");

		return this.engine.listConvertion(getBean(), lista, listRetorno);
	}

	/**
	 * Método responsável por verificar se há alguma solicitação de serviço em andamento neste grupo que esteja vinculada ao empregado a ser excluido do grupo
	 *
	 * @param idGrupo
	 * @param idEmpregado
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<GrupoEmpregadoDTO> verificacaoResponsavelPorSolicitacao(Integer idGrupo, Integer idEmpregado) throws PersistenceException {
		Object[] objs = new Object[] { idGrupo, idEmpregado, idGrupo };
		List listRetorno = new ArrayList();
		List lista = new ArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT atr.idGrupo, it.idresponsavelatual, us.nome as nomeEmpregado FROM gruposempregados ge ");
		sql.append(" INNER JOIN bpm_itemtrabalhofluxo it ON it.idresponsavelatual = ge.idempregado ");
		sql.append(" INNER JOIN execucaosolicitacao ex ON ex.idinstanciafluxo = it.idinstancia ");
		sql.append(" INNER JOIN bpm_atribuicaofluxo atr ON atr.iditemtrabalho = it.iditemtrabalho ");
		sql.append(" INNER JOIN usuario us ON us.idempregado = ge.idempregado ");
		sql.append(" WHERE it.situacao <> 'Executado' AND it.situacao <> 'Cancelado'");
		sql.append(" AND it.idresponsavelatual IS NOT NULL AND atr.tipo = 'Automatica' AND atr.idgrupo = ? AND it.idresponsavelatual = ? AND ge.idGrupo = ?;");
		lista = this.execSQL(sql.toString(), objs);


		listRetorno.add("idGrupo");
		listRetorno.add("idEmpregado");
		listRetorno.add("nomeEmpregado");

		return this.engine.listConvertion(getBean(), lista, listRetorno);
	}

	public Integer calculaTotalPaginas(Integer itensPorPagina, Integer idGrupo) throws PersistenceException {
		List parametro = new ArrayList();
		StringBuilder sql = new StringBuilder();

        sql.append(" select COUNT(*) ");
        sql.append(" from gruposempregados ");
        sql.append(" where idgrupo = ? ");

        parametro.add(idGrupo);

        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());

        Long totalLinhaLong = 0l;
        Long totalPagina = 0l;
        Integer total = 0;
        BigDecimal totalLinhaBigDecimal;
        Integer totalLinhaInteger;
        int intLimite = itensPorPagina;
        if(lista != null){
        	Object[] totalLinha = (Object[]) lista.get(0);
        	if(totalLinha != null && totalLinha.length > 0){
        		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.POSTGRESQL) || CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.MYSQL)) {
        			totalLinhaLong = (Long) totalLinha[0];
        		}
        		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
        			totalLinhaBigDecimal = (BigDecimal) totalLinha[0];
        			totalLinhaLong = totalLinhaBigDecimal.longValue();
        		}
        		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
        			totalLinhaInteger = (Integer) totalLinha[0];
        			totalLinhaLong = Long.valueOf(totalLinhaInteger);
        		}
        	}
        }

        if (totalLinhaLong > 0) {
        	totalPagina = (totalLinhaLong / intLimite);
        	if(totalLinhaLong % intLimite != 0){
        		totalPagina = totalPagina + 1;
        	}
        }
        total = Integer.valueOf(totalPagina.toString());
        return total;

	}

	public Collection<GrupoEmpregadoDTO> paginacaoGrupoEmpregado(Integer idGrupo, Integer pgAtual, Integer qtdPaginacao) throws PersistenceException {

		List parametro = new ArrayList();
		List listRetorno = new ArrayList();
		StringBuilder sql = new StringBuilder();

		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
			sql.append(" ;WITH TabelaTemporaria AS ( ");
		}

		sql.append(" SELECT grupo.idgrupo, grupo.sigla, empregado.idempregado, empregado.nome, grupoemp.enviaemail ");

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
        	sql.append(" , ROW_NUMBER() OVER (ORDER BY empregado.nome) AS Row ");
    	}
        sql.append(" FROM gruposempregados grupoemp ");
        sql.append(" INNER JOIN empregados empregado ON grupoemp.idempregado = empregado.idempregado ");
		sql.append(" INNER JOIN grupo grupo ON grupoemp.idgrupo = grupo.idgrupo ");
		sql.append(" WHERE grupoemp.idgrupo = ? AND empregado.datafim IS NULL ");

		parametro.add(idGrupo);


        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.POSTGRESQL)) {
        	sql.append(" ORDER BY empregado.nome");
        	Integer pgTotal = pgAtual * qtdPaginacao;
        	pgAtual = pgTotal - qtdPaginacao;
        	sql.append(" LIMIT " + qtdPaginacao + " OFFSET " +pgAtual);
        }

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.MYSQL)){
        	sql.append(" ORDER BY empregado.nome");
        	Integer pgTotal = pgAtual * qtdPaginacao;
        	pgAtual = pgTotal - qtdPaginacao;
        	sql.append(" LIMIT " +pgAtual+ ", "+qtdPaginacao);
        }

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)){
        	Integer quantidadePaginator2 = new Integer(0);
        	if (pgAtual > 0) {
        		quantidadePaginator2 = qtdPaginacao * pgAtual;
        		pgAtual = (pgAtual * qtdPaginacao) - qtdPaginacao;
        	}else{
        		quantidadePaginator2 = qtdPaginacao;
        		pgAtual = 0;
        	}
        	sql.append(" ) SELECT * FROM TabelaTemporaria WHERE Row> "+pgAtual+" and Row<"+(quantidadePaginator2+1)+" ");
        }

        String sqlOracle = "";
        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)){
        	Integer quantidadePaginator2 = new Integer(0);
        	if (pgAtual > 1) {
        		quantidadePaginator2 = qtdPaginacao * pgAtual;
        		pgAtual = (pgAtual * qtdPaginacao) - qtdPaginacao;
        		pgAtual = pgAtual + 1;
        	}else{
        		quantidadePaginator2 = qtdPaginacao;
        		pgAtual = 0;
        	}
        	int intInicio = pgAtual;
        	int intLimite = quantidadePaginator2;
        	sqlOracle = paginacaoOracle(sql.toString(), intInicio, intLimite);
        }

        List lista = new ArrayList();

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)){
        	lista = this.execSQL(sqlOracle, parametro.toArray());
    	}else{
    		/* Desenvolvedor: Euler Data: 28/10/2013 Horário: 10h57min ID Citsmart: 120393 Motivo/Comentário: Loop infinito ao selecionar um grupo sem empregados */
    		lista = this.execSQL(sql.toString(), parametro.toArray());
    	}

        listRetorno.add("idGrupo");
        listRetorno.add("sigla");
        listRetorno.add("idEmpregado");
        listRetorno.add("nomeEmpregado");
        listRetorno.add("enviaEmail");

        List result = this.engine.listConvertion(getBean(), lista, listRetorno);
		return (result == null ? new ArrayList<GrupoEmpregadoDTO>() : result);
	}

	public String paginacaoOracle(String strSQL, int intInicio, int intLimite) {
		strSQL = strSQL + " order by empregado.nome ";
		return "SELECT * FROM (SELECT PAGING.*, ROWNUM PAGING_RN FROM" +
		" (" + strSQL + ") PAGING WHERE (ROWNUM <= " + intLimite + "))" +
		" WHERE (PAGING_RN >= " + intInicio + ") ";
	}


	/**
	 * Metodo que valida na tabela gruposempregados a existencia de registro com os parametros informados
	 *
	 * @param idEmpregado
	 * @param idGrupo
	 *
	 * @return TRUE: Existe registro com os parametros informados || FALSE: Não existe registro com os parametros informados
	 *
	 * @throws PersistenceException
	 */
	public boolean grupoempregado (Integer idEmpregado, Integer idGrupo) throws PersistenceException {

		List lista = new ArrayList();
		List param = new ArrayList();

		StringBuilder sql = new StringBuilder(" select * from gruposempregados ge where ge.idempregado=? and ge.idgrupo=?  ");

	    param.add(idEmpregado);
	    param.add(idGrupo);

		lista =  this.execSQL(sql.toString(), param.toArray());

		if (lista != null && !lista.isEmpty())
			return true;
		else
			return false;

	}

	public Collection<GrupoEmpregadoDTO> findEmpregado(Integer idGrupo, Integer idEmpregado) throws PersistenceException {
		List param = new ArrayList();
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT *  FROM GruposEmpregados GE  WHERE GE.idGrupo = ?  and idEmpregado = ?");

		param.add(idGrupo);
		param.add(idEmpregado);

		List lista = this.execSQL(sql.toString(), param.toArray());

		List listRetorno = new ArrayList();
		listRetorno.add("idGrupo");
		listRetorno.add("idEmpregado");
		listRetorno.add("enviaemail");

		if (lista != null && !lista.isEmpty()) {
			return this.engine.listConvertion(getBean(), lista, listRetorno);
		} else {
			return null;
		}
	}

}
