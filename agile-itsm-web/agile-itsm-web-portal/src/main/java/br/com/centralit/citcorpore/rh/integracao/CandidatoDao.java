package br.com.centralit.citcorpore.rh.integracao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.CandidatoDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.SQLConfig;

/**
 * @author thiago.borges
 *
 */
public class CandidatoDao extends CrudDaoDefaultImpl {

    public CandidatoDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection find(final IDto arg0) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();

        listFields.add(new Field("IDCANDIDATO", "idCandidato", true, true, false, false));
        listFields.add(new Field("NOME", "nome", false, false, false, false));
        listFields.add(new Field("CPF", "cpf", false, false, false, false));
        listFields.add(new Field("EMAIL", "email", false, false, false, false));
        listFields.add(new Field("SITUACAO", "situacao", false, false, false, false));
        listFields.add(new Field("TIPO", "tipo", false, false, false, false));
        listFields.add(new Field("SENHA", "senha", false, false, false, false));
        listFields.add(new Field("DATAINICIO", "dataInicio", false, false, false, false));
        listFields.add(new Field("DATAFIM", "dataFim", false, false, false, false));
        listFields.add(new Field("AUTENTICADO", "autenticado", false, false, false, false));
        listFields.add(new Field("HASHID", "hashID", false, false, false, false));
        listFields.add(new Field("IDEMPREGADO", "idEmpregado", false, false, false, false));

        return listFields;
    }

    @Override
    public String getTableName() {
        return "RH_CANDIDATO";
    }

    @Override
    public Collection list() throws PersistenceException {
        final List list = new ArrayList();
        list.add(new Order("nome"));
        return super.list(list);
    }

    @Override
    public Class getBean() {
        return CandidatoDTO.class;
    }

    /**
     * Retorna lista de status de candidato.
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public boolean consultarCandidatosAtivos(final CandidatoDTO obj) throws PersistenceException {
        final List parametro = new ArrayList();
        List list = new ArrayList();
        String sql = "select idcandidato From " + this.getTableName() + "  where  cpf = ?   and dataFim is null ";

        if (obj.getIdCandidato() != null) {
            sql += " and idcandidato <> " + obj.getIdCandidato();
        }

        parametro.add(obj.getCpf());
        list = this.execSQL(sql, parametro.toArray());
        if (list != null && !list.isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean validaInsert(final CandidatoDTO obj) {

        return false;

    }

    public Collection findByNome(final CandidatoDTO candidatoDTO) throws PersistenceException {
        final List condicao = new ArrayList();
        final List ordenacao = new ArrayList();

        condicao.add(new Condition("nome", "=", candidatoDTO.getNome()));
        ordenacao.add(new Order("nome"));
        condicao.add(new Condition(Condition.AND, "dataFim", "is", null));
        return super.findByCondition(condicao, ordenacao);
    }

    public Collection<CandidatoDTO> seCandidatoJaCadastrado(final CandidatoDTO candidatoDTO) throws PersistenceException {
        final List parametro = new ArrayList();
        List list = new ArrayList();
        String sql = "";
        sql = " select lower(nome) from rh_candidato where nome = lower(?) ";

        parametro.add(candidatoDTO.getNome().trim().toLowerCase());
        list = this.execSQL(sql, parametro.toArray());
        return list;
    }

    public Collection<CandidatoDTO> listarAtivos() throws PersistenceException {
        final List condicao = new ArrayList();
        final List ordenacao = new ArrayList();

        ordenacao.add(new Order("nome"));
        condicao.add(new Condition("dataFim", "is", null));
        return super.findByCondition(condicao, ordenacao);
    }

    /**
     * Metodos para Iniciativa 74
     */

    /**
     * @param id
     *            - idCurriculo
     * @return - CandidatoDTO - coleção de candidatos na primeira posição
     *
     * @author david.silva
     */
    public CandidatoDTO restoreByID(final Integer id) throws PersistenceException {
        final List ordem = new ArrayList();
        ordem.add(new Order("idCandidato"));
        ordem.add(new Order("nome"));
        final CandidatoDTO candidatoDto = new CandidatoDTO();
        candidatoDto.setIdCandidato(id);
        final List col = (List) super.find(candidatoDto, ordem);
        if (col == null || col.size() == 0) {
            return null;
        }
        return (CandidatoDTO) col.get(0);
    }

    public CandidatoDTO findByCpfCurriculo(final String cpf) throws PersistenceException {
        final List ordem = new ArrayList();
        ordem.add(new Order("idCandidato"));
        ordem.add(new Order("nome"));
        final CandidatoDTO candidatoDto = new CandidatoDTO();
        candidatoDto.setCpf(cpf);
        final List col = (List) super.find(candidatoDto, ordem);
        if (col == null || col.size() == 0) {
            return null;
        }
        return (CandidatoDTO) col.get(0);
    }

    public Collection findListTodosCandidatos() throws PersistenceException {
        final String sql = "SELECT nome, cpf, idcandidato, email, situacao, tipo FROM rh_candidato ORDER BY nome ASC;";

        List lista = new ArrayList();
        lista = this.execSQL(sql, null);
        final List listRetorno = new ArrayList();

        listRetorno.add("nome");
        listRetorno.add("cpf");
        listRetorno.add("idCandidato");
        listRetorno.add("email");
        listRetorno.add("situacao");
        listRetorno.add("tipo");

        final List result = engine.listConvertion(this.getBean(), lista, listRetorno);
        return result;
    }

    public Collection findByIdCandidatoJoinIdHistorico(final Integer idCandidato) throws PersistenceException {
        final List parametro = new ArrayList();
        final List fields = new ArrayList();
        List list = new ArrayList();

        final StringBuilder sql = new StringBuilder();
        sql.append("select idHistoricoFuncional from rh_historicofuncional h ");
        sql.append("inner join rh_candidato c ON c.idcandidato = h.idcandidato ");
        sql.append("where c.idcandidato = ?");

        parametro.add(idCandidato);
        list = this.execSQL(sql.toString(), parametro.toArray());
        fields.add("idHistoricoFuncional");

        if (list != null && !list.isEmpty()) {
            return this.listConvertion(this.getBean(), list, fields);
        } else {
            return null;
        }
    }

    public Collection findByCPF(final String cpf) throws PersistenceException {
        final List condicao = new ArrayList();
        final List ordenacao = new ArrayList();

        condicao.add(new Condition("cpf", "=", cpf));
        condicao.add(new Condition(Condition.AND, "dataFim", "is", null));

        ordenacao.add(new Order("nome"));

        return super.findByCondition(condicao, ordenacao);
    }

    public Collection findByNome(final String nome) throws PersistenceException {
        final String sql = "SELECT nome, cpf, idcandidato, email, situacao, tipo FROM rh_candidato WHERE UPPER(nome) LIKE UPPER('%" + nome + "%');";

        List lista = new ArrayList();
        lista = this.execSQL(sql, null);
        final List listRetorno = new ArrayList();

        listRetorno.add("nome");
        listRetorno.add("cpf");
        listRetorno.add("idCandidato");
        listRetorno.add("email");
        listRetorno.add("situacao");
        listRetorno.add("tipo");

        final List result = engine.listConvertion(this.getBean(), lista, listRetorno);
        return result;
    }

    public Collection<CandidatoDTO> recuperaColecaoCandidatos(final CandidatoDTO candidatoDto, Integer pgAtual, final Integer qtdPaginacao) throws PersistenceException {

        final List parametro = new ArrayList();
        final List listRetorno = new ArrayList();
        final StringBuilder sql = new StringBuilder();

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
            sql.append(" ;WITH TabelaTemporaria AS ( ");
        }

        if (candidatoDto.getCandidatoNaListaNegra() != null && candidatoDto.getCandidatoNaListaNegra().equalsIgnoreCase("S")) {
            sql.append("SELECT distinct l.idcandidato, c.nome, c.cpf, c.email, c.situacao, c.tipo, c.email ");
            sql.append("FROM rh_candidato c ");
            sql.append("INNER JOIN rh_historicofuncional hf ");
            sql.append("on hf.idcandidato = c.idcandidato ");
            sql.append("INNER JOIN rh_listanegra l ");
            sql.append("on c.idcandidato = l.idcandidato ");
            sql.append("AND l.datafim IS NULL ");
        } else {
            sql.append(" SELECT distinct c.idcandidato, c.nome, c.cpf, c.email, c.situacao, c.tipo, c.email ");
            sql.append(" FROM rh_candidato c ");
            sql.append(" INNER JOIN rh_historicofuncional hf ");
            sql.append(" on c.idcandidato = hf.idcandidato ");
        }

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
            sql.append(" , ROW_NUMBER() OVER (ORDER BY idhistoricofuncional) AS Row ");
        }

        if (candidatoDto.getNome() != null && !candidatoDto.getNome().equalsIgnoreCase("")) {
            sql.append(" AND c.nome like ? ");
            parametro.add("%" + candidatoDto.getNome() + "%");
        }

        if (candidatoDto.getCpf() != null && !candidatoDto.getCpf().equalsIgnoreCase("")) {
            sql.append(" AND c.cpf = ? ");
            parametro.add(candidatoDto.getCpf());
        }

        if (candidatoDto.getTipo() != null && !candidatoDto.getTipo().equalsIgnoreCase("")) {
            sql.append(" AND c.tipo = ? ");
            parametro.add(candidatoDto.getTipo());
        }

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.MYSQL)) {
            sql.append(" ORDER BY c.idcandidato ");
            final Integer pgTotal = pgAtual * qtdPaginacao;
            pgAtual = pgTotal - qtdPaginacao;
            sql.append(" LIMIT " + pgAtual + ", " + qtdPaginacao);
        }

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.POSTGRESQL)) {
            sql.append(" ORDER BY c.idcandidato");
            final Integer pgTotal = pgAtual * qtdPaginacao;
            pgAtual = pgTotal - qtdPaginacao;
            sql.append(" LIMIT " + qtdPaginacao + " OFFSET " + pgAtual);
        }

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
            Integer quantidadePaginator2 = new Integer(0);
            if (pgAtual > 0) {
                quantidadePaginator2 = qtdPaginacao * pgAtual;
                pgAtual = pgAtual * qtdPaginacao - qtdPaginacao;
            } else {
                quantidadePaginator2 = qtdPaginacao;
                pgAtual = 0;
            }
            sql.append(" ) SELECT * FROM TabelaTemporaria WHERE Row> " + pgAtual + " and Row<" + (quantidadePaginator2 + 1) + " ");
        }

        String sqlOracle = "";

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            Integer quantidadePaginator2 = new Integer(0);
            if (pgAtual > 1) {
                quantidadePaginator2 = qtdPaginacao * pgAtual;
                pgAtual = pgAtual * qtdPaginacao - qtdPaginacao;
                pgAtual = pgAtual + 1;
            } else {
                quantidadePaginator2 = qtdPaginacao;
                pgAtual = 0;
            }
            final int intInicio = pgAtual;
            final int intLimite = quantidadePaginator2;
            sqlOracle = this.paginacaoOracle(sql.toString(), intInicio, intLimite);
        }

        List lista = new ArrayList();

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            lista = this.execSQL(sqlOracle, parametro.toArray());
        } else {
            lista = this.execSQL(sql.toString(), parametro.toArray());
        }

        listRetorno.add("idCandidato");
        listRetorno.add("nome");
        listRetorno.add("cpf");
        listRetorno.add("email");
        listRetorno.add("situacao");
        listRetorno.add("tipo");

        return engine.listConvertion(this.getBean(), lista, listRetorno);
    }

    public String paginacaoOracle(String strSQL, final int intInicio, final int intLimite) {
        strSQL = strSQL + " order by idcandidato ";
        return "SELECT * FROM " + "(SELECT PAGING.*, ROWNUM PAGING_RN FROM" + " (" + strSQL + ") PAGING WHERE (ROWNUM <= " + intLimite + "))" + " WHERE (PAGING_RN >= " + intInicio
                + ") ";
    }

    public Integer calculaTotalPaginas(final Integer itensPorPagina, final CandidatoDTO candidatoDto) throws PersistenceException {
        final List parametro = new ArrayList();
        final StringBuilder sql = new StringBuilder();

        if (candidatoDto.getCandidatoNaListaNegra() != null && candidatoDto.getCandidatoNaListaNegra().equalsIgnoreCase("S")) {
            sql.append("SELECT COUNT(*) ");
            sql.append("FROM rh_candidato c ");
            sql.append("INNER JOIN rh_listanegra l ");
            sql.append("on c.idcandidato = l.idcandidato ");
            sql.append("AND l.datafim IS NULL ");
        } else {
            sql.append("SELECT COUNT(*) ");
            sql.append("FROM rh_candidato c ");
            sql.append("INNER JOIN rh_historicofuncional hf ");
            sql.append("on hf.idcandidato = c.idcandidato ");
        }

        if (candidatoDto.getNome() != null && !candidatoDto.getNome().equalsIgnoreCase("")) {
            sql.append(" AND c.nome like ? ");
            parametro.add("%" + candidatoDto.getNome() + "%");
        }

        if (candidatoDto.getCpf() != null && !candidatoDto.getCpf().equalsIgnoreCase("")) {
            sql.append(" AND c.cpf = ? ");
            parametro.add(candidatoDto.getCpf());
        }

        if (candidatoDto.getTipo() != null && !candidatoDto.getTipo().equalsIgnoreCase("")) {
            sql.append(" AND c.tipo = ? ");
            parametro.add(candidatoDto.getTipo());
        }

        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());

        Long totalLinhaLong = 0l;
        Long totalPagina = 0l;
        Integer total = 0;
        BigDecimal totalLinhaBigDecimal;
        Integer totalLinhaInteger;
        final int intLimite = itensPorPagina;

        if (lista != null) {
            final Object[] totalLinha = (Object[]) lista.get(0);
            if (totalLinha != null && totalLinha.length > 0) {
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
            totalPagina = totalLinhaLong / intLimite;
            if (totalLinhaLong % intLimite != 0) {
                totalPagina = totalPagina + 1;
            }
        }
        total = Integer.valueOf(totalPagina.toString());
        return total;
    }

    public CandidatoDTO findByEmail(final String email) throws PersistenceException {
        final List parametro = new ArrayList();
        final List listRetorno = new ArrayList();
        final StringBuilder sql = new StringBuilder();

        sql.append(" SELECT C.NOME, C.CPF, C.IDCANDIDATO, ");
        sql.append(" C.EMAIL, C.SITUACAO, C.TIPO, C.HASHID, C.AUTENTICADO, C.DATAINICIO, C.SENHA ");
        sql.append(" FROM RH_CANDIDATO C ");
        sql.append(" WHERE C.EMAIL = ? AND C.DATAFIM is NULL ");

        parametro.add(email);
        listRetorno.add("nome");
        listRetorno.add("cpf");
        listRetorno.add("idCandidato");
        listRetorno.add("email");
        listRetorno.add("situacao");
        listRetorno.add("tipo");
        listRetorno.add("hashID");
        listRetorno.add("autenticado");
        listRetorno.add("dataInicio");
        listRetorno.add("senha");

        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());

        if (lista != null && lista.size() > 0) {
            final List result = engine.listConvertion(this.getBean(), lista, listRetorno);
            return (CandidatoDTO) result.get(0);
        } else {
            return null;
        }
    }

    public CandidatoDTO restoreByCpf(final String cpf) throws PersistenceException {
        final List parametro = new ArrayList();
        final List listRetorno = new ArrayList();
        final StringBuilder sql = new StringBuilder();

        sql.append(" SELECT C.NOME, C.CPF, C.IDCANDIDATO, ");
        sql.append(" C.EMAIL, C.SITUACAO, C.TIPO, C.HASHID, C.AUTENTICADO, C.DATAINICIO, C.SENHA ");
        sql.append(" FROM RH_CANDIDATO C ");
        sql.append(" WHERE C.CPF  = ? AND C.DATAFIM is NULL ");

        parametro.add(cpf);
        listRetorno.add("nome");
        listRetorno.add("cpf");
        listRetorno.add("idCandidato");
        listRetorno.add("email");
        listRetorno.add("situacao");
        listRetorno.add("tipo");
        listRetorno.add("hashID");
        listRetorno.add("autenticado");
        listRetorno.add("dataInicio");
        listRetorno.add("senha");

        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());

        final List result = engine.listConvertion(this.getBean(), lista, listRetorno);
        if (result != null && !result.isEmpty()) {
            return (CandidatoDTO) result.get(0);
        }
        return null;
    }

    public CandidatoDTO findByHashID(final String nome) throws PersistenceException {
        final List parametro = new ArrayList();
        final List listRetorno = new ArrayList();
        final StringBuilder sql = new StringBuilder();

        sql.append(" SELECT C.NOME, C.CPF, C.IDCANDIDATO, ");
        sql.append(" C.EMAIL, C.SITUACAO, C.TIPO, C.HASHID, C.AUTENTICADO, C.DATAINICIO, C.SENHA ");
        sql.append(" FROM RH_CANDIDATO C ");
        sql.append(" WHERE C.HASHID = ? AND C.DATAFIM is NULL ");

        parametro.add(nome);
        listRetorno.add("nome");
        listRetorno.add("cpf");
        listRetorno.add("idCandidato");
        listRetorno.add("email");
        listRetorno.add("situacao");
        listRetorno.add("tipo");
        listRetorno.add("hashID");
        listRetorno.add("autenticado");
        listRetorno.add("dataInicio");
        listRetorno.add("senha");

        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());

        if (lista != null && lista.size() > 0) {
            final List result = engine.listConvertion(this.getBean(), lista, listRetorno);
            return (CandidatoDTO) result.get(0);
        } else {
            return null;
        }
    }

    public CandidatoDTO restoreByIdEmpregado(final Integer idEmpregado) throws PersistenceException {
        final List parametro = new ArrayList();
        final List listRetorno = new ArrayList();
        final StringBuilder sql = new StringBuilder();

        sql.append(" SELECT C.NOME, C.CPF, C.IDCANDIDATO, ");
        sql.append(" C.EMAIL, C.SITUACAO, C.TIPO, C.HASHID, C.AUTENTICADO, C.DATAINICIO, C.SENHA, C.IDEMPREGADO ");
        sql.append(" FROM RH_CANDIDATO C ");
        sql.append(" WHERE C.IDEMPREGADO = ? AND C.DATAFIM is NULL ");

        parametro.add(idEmpregado);
        listRetorno.add("nome");
        listRetorno.add("cpf");
        listRetorno.add("idCandidato");
        listRetorno.add("email");
        listRetorno.add("situacao");
        listRetorno.add("tipo");
        listRetorno.add("hashID");
        listRetorno.add("autenticado");
        listRetorno.add("dataInicio");
        listRetorno.add("senha");
        listRetorno.add("idEmpregado");

        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());

        final List result = engine.listConvertion(this.getBean(), lista, listRetorno);
        if (result != null && !result.isEmpty()) {
            return (CandidatoDTO) result.get(0);
        }
        return null;
    }

    @Override
    public void updateNotNull(final IDto obj) throws PersistenceException {
        super.updateNotNull(obj);
    }
}
