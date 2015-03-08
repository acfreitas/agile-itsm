package br.com.centralit.citcorpore.integracao;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.UnidadeDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.integracao.core.DataBase;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.SQLConfig;

/**
 * @author thays.araujo e daniel
 *
 */
public class EmpregadoDao extends CrudDaoDefaultImpl {

    private static final String TABLE_NAME = "empregados";

    public EmpregadoDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Class<EmpregadoDTO> getBean() {
        return EmpregadoDTO.class;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idEmpregado", "idEmpregado", true, true, false, false));
        listFields.add(new Field("idUnidade", "idUnidade", false, false, false, false));
        listFields.add(new Field("idCargo", "idCargo", false, false, false, false));
        listFields.add(new Field("Nome", "nome", false, false, false, false));
        listFields.add(new Field("NomeProcura", "nomeProcura", false, false, false, false));
        listFields.add(new Field("DataNascimento", "dataNascimento", false, false, false, false));
        listFields.add(new Field("Sexo", "sexo", false, false, false, false));
        listFields.add(new Field("CPF", "cpf", false, false, false, false));
        listFields.add(new Field("RG", "rg", false, false, false, false));
        listFields.add(new Field("DataEmissaoRG", "dataEmissaoRG", false, false, false, false));
        listFields.add(new Field("OrgExpedidor", "orgExpedidor", false, false, false, false));
        listFields.add(new Field("idUFOrgExpedidor", "idUFOrgExpedidor", false, false, false, false));
        listFields.add(new Field("Pai", "pai", false, false, false, false));
        listFields.add(new Field("Mae", "mae", false, false, false, false));
        listFields.add(new Field("Conjuge", "conjuge", false, false, false, false));
        listFields.add(new Field("Observacoes", "observacoes", false, false, false, false));
        listFields.add(new Field("EstadoCivil", "estadoCivil", false, false, false, false));
        listFields.add(new Field("Email", "email", false, false, false, false));
        listFields.add(new Field("DataCadastro", "dataCadastro", false, false, false, false));
        listFields.add(new Field("Fumante", "fumante", false, false, false, false));
        listFields.add(new Field("CTPSNumero", "ctpsNumero", false, false, false, false));
        listFields.add(new Field("CTPSSerie", "ctpsSerie", false, false, false, false));
        listFields.add(new Field("CTPSIdUf", "ctpsIdUf", false, false, false, false));
        listFields.add(new Field("CTPSDataEmissao", "ctpsDataEmissao", false, false, false, false));
        listFields.add(new Field("NIT", "nit", false, false, false, false));
        listFields.add(new Field("DataAdmissao", "dataAdmissao", false, false, false, false));
        listFields.add(new Field("DataFim", "dataFim", false, false, false, false));
        listFields.add(new Field("idSituacaoFuncional", "idSituacaoFuncional", false, false, false, false));
        listFields.add(new Field("DataDemissao", "dataDemissao", false, false, false, false));
        listFields.add(new Field("tipo", "tipo", false, false, false, false));
        listFields.add(new Field("custoPorHora", "custoPorHora", false, false, false, false));
        listFields.add(new Field("custoTotalMes", "custoTotalMes", false, false, false, false));
        listFields.add(new Field("valorSalario", "valorSalario", false, false, false, false));
        listFields.add(new Field("valorProdutividadeMedia", "valorProdutividadeMedia", false, false, false, false));
        listFields.add(new Field("valorPlanoSaudeMedia", "valorPlanoSaudeMedia", false, false, false, false));
        listFields.add(new Field("valorVTraMedia", "valorVTraMedia", false, false, false, false));
        listFields.add(new Field("valorVRefMedia", "valorVRefMedia", false, false, false, false));
        listFields.add(new Field("agencia", "agencia", false, false, false, false));
        listFields.add(new Field("contaSalario", "contaSalario", false, false, false, false));
        listFields.add(new Field("telefone", "telefone", false, false, false, false));
        listFields.add(new Field("ramal", "ramal", false, false, false, false));
        return listFields;
    }

    @Override
    public void updateNotNull(final IDto obj) throws PersistenceException {
        super.updateNotNull(obj);
    }

    @Override
    public String getTableName() {
        return EmpregadoDao.TABLE_NAME;
    }

    @Override
    public Collection<EmpregadoDTO> find(final IDto obj) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<EmpregadoDTO> list() throws PersistenceException {
        final List<Order> list = new ArrayList<>();
        list.add(new Order("nome"));
        return super.list(list);
    }

    /**
     * Retorna empregado Ativo por id.
     *
     * @param idEmpregado
     * @return EmpregadoDTO
     * @throws Exception
     */
    public EmpregadoDTO restoreEmpregadoAtivoById(final Integer idEmpregado) throws PersistenceException {
        final List<Condition> condicoes = new ArrayList<>();
        condicoes.add(new Condition("idEmpregado", "=", idEmpregado));
        condicoes.add(new Condition("dataFim", "is", null));
        final List<EmpregadoDTO> resultados = (List<EmpregadoDTO>) this.findByCondition(condicoes, null);
        return resultados.get(0);
    }

    /**
     * @param idGrupo
     * @return
     * @throws Exception
     * @author daniel
     */
    public Collection<EmpregadoDTO> listEmpregadosByIdGrupo(final Integer idGrupo) throws PersistenceException {
        final String sql = "select gruposempregados.idempregado from gruposempregados INNER JOIN usuario ON gruposempregados.idempregado = usuario.idempregado where usuario.status LIKE 'A' AND idgrupo = ?";
        final List<?> dados = this.execSQL(sql, new Object[] {idGrupo});
        final List<String> fields = new ArrayList<>();
        fields.add("idEmpregado");
        return this.listConvertion(this.getBean(), dados, fields);
    }

    /**
     * @param idUnidade
     * @return
     * @throws Exception
     * @author daniel
     */
    public Collection<EmpregadoDTO> listEmpregadosByIdUnidade(final Integer idUnidade) throws PersistenceException {
        final String sql = "select idempregado from empregados where idunidade = ?";
        final List<?> dados = this.execSQL(sql, new Object[] {idUnidade});
        final List<String> fields = new ArrayList<>();
        fields.add("idEmpregado");
        return this.listConvertion(this.getBean(), dados, fields);
    }

    public Collection<EmpregadoDTO> listarIdEmpregados(final Integer limit, final Integer offset) throws PersistenceException {
        String sql = null;

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            sql = "  SELECT IDEMPREGADO";
            sql += " FROM   (SELECT ROWNUM RNUM,";
            sql += "               A.*";
            sql += "         FROM   (SELECT *";
            sql += "                 FROM   EMPREGADOS) A";
            sql += "         WHERE  ROWNUM <= " + (offset + limit) + ")";
            sql += " WHERE  RNUM > " + offset;
            sql += " ORDER BY IDEMPREGADO";
        } else {
            sql = "select idempregado from empregados";
            sql += " order by idempregado";
            if (limit != null) {
                sql += " limit " + limit;
            }
            if (offset != null) {
                sql += " offset " + offset;
            }
        }

        final List<?> lista = this.execSQL(sql, null);
        final List<String> listRetorno = new ArrayList<>();
        listRetorno.add("idEmpregado");
        return engine.listConvertion(this.getBean(), lista, listRetorno);
    }

    public EmpregadoDTO restoreByIdEmpregado(final Integer idEmpregado) throws PersistenceException {
        final List<Order> ordem = new ArrayList<>();
        final EmpregadoDTO empregadoDto = new EmpregadoDTO();
        empregadoDto.setIdEmpregado(idEmpregado);
        final List<EmpregadoDTO> col = (List<EmpregadoDTO>) super.find(empregadoDto, ordem);
        if (col == null || col.size() == 0) {
            return null;
        }
        return col.get(0);
    }

    public Collection<EmpregadoDTO> listEmpregadosGrupo(final Integer idEmpregado, final Integer idGrupo) throws PersistenceException {
        final List<Integer> parametro = new ArrayList<>();
        final String sql = "SELECT empregados.idempregado,empregados.nome, gruposempregados.enviaemail FROM empregados, gruposempregados "
                + " WHERE empregados.idempregado = gruposempregados.idempregado AND empregados.idEmpregado = ? "
                + " AND gruposempregados.idgrupo = ? and empregados.datafim is null";
        parametro.add(idEmpregado);
        parametro.add(idGrupo);
        final List<?> lista = this.execSQL(sql.toString(), parametro.toArray());
        final List<String> listRetorno = new ArrayList<>();
        listRetorno.add("idEmpregado");
        listRetorno.add("nome");
        listRetorno.add("enviaEmail");
        final List<EmpregadoDTO> result = engine.listConvertion(this.getBean(), lista, listRetorno);
        if (result != null) {
            return result;
        }
        return null;
    }

    public Collection<EmpregadoDTO> listEmailContrato(final Integer idContrato) throws PersistenceException {
        final List<Integer> parametro = new ArrayList<>();
        final StringBuilder sql = new StringBuilder();
        sql.append("SELECT e.email ");
        sql.append(" FROM GRUPOSEMPREGADOS ge, EMPREGADOS e, GRUPO g, CONTRATOSGRUPOS cg ");
        sql.append(" WHERE g.idgrupo = ge.idgrupo ");
        sql.append(" AND e.idempregado = ge.idempregado ");
        sql.append(" AND g.idgrupo = cg.idgrupo ");
        sql.append("AND idcontrato = ?");
        parametro.add(idContrato);
        final List<?> lista = this.execSQL(sql.toString(), parametro.toArray());
        final List<String> listRetorno = new ArrayList<>();
        listRetorno.add("email");
        final List<EmpregadoDTO> result = engine.listConvertion(this.getBean(), lista, listRetorno);
        if (result != null) {
            return result;
        }
        return null;
    }

    public Collection<EmpregadoDTO> listEmailContrato() throws PersistenceException {
        final List<?> parametro = new ArrayList<>();
        final StringBuilder sql = new StringBuilder();
        sql.append("SELECT e.email ");
        sql.append(" FROM GRUPOSEMPREGADOS ge, EMPREGADOS e, GRUPO g, CONTRATOSGRUPOS cg ");
        sql.append(" WHERE g.idgrupo = ge.idgrupo ");
        sql.append(" AND e.idempregado = ge.idempregado ");
        sql.append(" AND g.idgrupo = cg.idgrupo ");
        final List<?> lista = this.execSQL(sql.toString(), parametro.toArray());
        final List<String> listRetorno = new ArrayList<>();
        listRetorno.add("email");
        final List<EmpregadoDTO> result = engine.listConvertion(this.getBean(), lista, listRetorno);
        if (result != null) {
            return result;
        }
        return null;
    }

    public EmpregadoDTO listEmpregadoContrato(final Integer idContrato, final String email) throws PersistenceException {
        final List<Object> parametro = new ArrayList<>();
        final StringBuilder sql = new StringBuilder();
        sql.append("SELECT ge.idgrupo, ge.idempregado, e.nome, e.email , e.telefone, e.idunidade, cg.idcontrato ");
        sql.append(" FROM GRUPOSEMPREGADOS ge, EMPREGADOS e, GRUPO g, CONTRATOSGRUPOS cg ");
        sql.append(" WHERE g.idgrupo = ge.idgrupo ");
        sql.append(" AND e.idempregado = ge.idempregado ");
        sql.append(" AND g.idgrupo = cg.idgrupo ");
        sql.append(" AND idcontrato = ? ");
        sql.append(" AND e.email = ?");
        parametro.add(idContrato);
        parametro.add(email);
        final List<?> lista = this.execSQL(sql.toString(), parametro.toArray());
        final List<String> listRetorno = new ArrayList<>();
        listRetorno.add("idGrupo");
        listRetorno.add("idEmpregado");
        listRetorno.add("nome");
        listRetorno.add("email");
        listRetorno.add("telefone");
        listRetorno.add("idUnidade");
        listRetorno.add("idContrato");
        final List<EmpregadoDTO> result = engine.listConvertion(this.getBean(), lista, listRetorno);
        if (result != null && !result.isEmpty()) {
            return result.get(0);
        }
        return null;
    }

    public Collection<EmpregadoDTO> listEmpregadoByContratoAndUnidadeAndEmpregados(final Integer idContrato, final Integer idUnidade, final Integer[] idEmpregados,
            final UsuarioDTO usuario, final ArrayList<UnidadeDTO> listUnidadeContrato) throws PersistenceException {
        final StringBuilder sql = new StringBuilder();
        final List parametro = new ArrayList();

        sql.append(" SELECT ge.idempregado, e.nome, e.email , e.telefone, u.idunidade, cg.idcontrato ");
        sql.append(" FROM grupo g, gruposempregados ge, empregados e, contratosgrupos cg, unidade u ");
        sql.append(" WHERE g.idgrupo = ge.idgrupo ");
        sql.append(" AND e.idempregado = ge.idempregado ");
        sql.append(" AND g.idgrupo = cg.idgrupo ");
        sql.append(" AND e.idunidade = u.idunidade ");
        sql.append(" AND e.datafim is null ");
        sql.append(" AND u.datafim is null ");
        sql.append(" AND e.idsituacaofuncional <> 2");
        if (idContrato != null) {
            sql.append(" AND cg.idcontrato = ? ");
            parametro.add(idContrato);
        }
        if (idUnidade != null) {
            sql.append("AND u.idunidade = ? ");
            parametro.add(idUnidade);
        } else {
            sql.append(" AND u.idunidade in ( ");
            for (int i = 0; i < listUnidadeContrato.size(); i++) {
                sql.append(listUnidadeContrato.get(i).getIdUnidade());
                if (i != listUnidadeContrato.size() - 1) {
                    sql.append(", ");
                }
            }
            sql.append(" ) ");
        }
        if (idEmpregados != null) {
            sql.append(" AND ge.idempregado IN (" + StringUtils.join(idEmpregados, ",") + ") ");
        }

        sql.append(" GROUP BY ge.idempregado, e.nome, e.email , e.telefone, u.idunidade, cg.idcontrato ");
        final List lista = this.execSQL(sql.toString(), parametro.toArray());

        final List<String> listRetorno = new ArrayList<String>();
        listRetorno.add("idEmpregado");
        listRetorno.add("nome");
        listRetorno.add("email");
        listRetorno.add("telefone");
        listRetorno.add("idUnidade");
        listRetorno.add("idContrato");

        final List result = engine.listConvertion(EmpregadoDTO.class, lista, listRetorno);
        if (result != null && !result.isEmpty()) {
            return result;
        } else {
            return null;
        }
    }

    public Collection<EmpregadoDTO> listEmpregadoContrato(final Integer idContrato) throws PersistenceException {
        final List<Integer> parametro = new ArrayList<>();
        final StringBuilder sql = new StringBuilder();
        sql.append("SELECT ge.idgrupo, ge.idempregado, e.nome, e.email , e.telefone, e.idunidade, cg.idcontrato ");
        sql.append(" FROM GRUPOSEMPREGADOS ge, EMPREGADOS e, GRUPO g, CONTRATOSGRUPOS cg ");
        sql.append(" WHERE g.idgrupo = ge.idgrupo ");
        sql.append(" AND e.idempregado = ge.idempregado ");
        sql.append(" AND g.idgrupo = cg.idgrupo ");
        sql.append("AND idcontrato = ? ");
        parametro.add(idContrato);
        final List<?> lista = this.execSQL(sql.toString(), parametro.toArray());
        final List<String> listRetorno = new ArrayList<>();
        listRetorno.add("idGrupo");
        listRetorno.add("idEmpregado");
        listRetorno.add("nome");
        listRetorno.add("email");
        listRetorno.add("telefone");
        listRetorno.add("idUnidade");
        listRetorno.add("idContrato");
        final List<EmpregadoDTO> result = engine.listConvertion(this.getBean(), lista, listRetorno);
        if (result != null && !result.isEmpty()) {
            return result;
        }
        return null;
    }

    public EmpregadoDTO listEmpregadoContrato(final String email) throws PersistenceException {
        final List<String> parametro = new ArrayList<>();
        final StringBuilder sql = new StringBuilder();
        sql.append("SELECT ge.idgrupo, ge.idempregado, e.nome, e.email , e.telefone, e.idunidade, cg.idcontrato ");
        sql.append(" FROM GRUPOSEMPREGADOS ge, EMPREGADOS e, GRUPO g, CONTRATOSGRUPOS cg ");
        sql.append(" WHERE g.idgrupo = ge.idgrupo ");
        sql.append(" AND e.idempregado = ge.idempregado ");
        sql.append(" AND g.idgrupo = cg.idgrupo ");
        sql.append("AND e.email = ?");
        parametro.add(email);
        final List<?> lista = this.execSQL(sql.toString(), parametro.toArray());
        final List<String> listRetorno = new ArrayList<>();
        listRetorno.add("idGrupo");
        listRetorno.add("idEmpregado");
        listRetorno.add("nome");
        listRetorno.add("email");
        listRetorno.add("telefone");
        listRetorno.add("idUnidade");
        listRetorno.add("idContrato");
        final List<EmpregadoDTO> result = engine.listConvertion(this.getBean(), lista, listRetorno);
        if (result != null && !result.isEmpty()) {
            return result.get(0);
        }
        return null;
    }

    /**
     * Retorna uma lista de empregados associados a um cargo
     *
     * @param idCargo
     * @return
     * @throws Exception
     * @author thays.araujo
     */
    public boolean verificarSeCargoPossuiEmpregado(final Integer idCargo) throws PersistenceException {
        final List<Integer> parametro = new ArrayList<>();
        final String sql = "select idempregado,nome from " + this.getTableName() + " where idCargo = ? and datafim is null ";
        parametro.add(idCargo);
        final List<?> lista = this.execSQL(sql.toString(), parametro.toArray());
        if (lista != null && !lista.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * Verifica se idUnidade informado possui empregado.
     *
     * @param idCargo
     * @return
     * @throws Exception
     * @author thays.araujo
     */
    public boolean verificarSeUnidadePossuiEmpregado(final Integer idUnidade) throws PersistenceException {
        final List<Integer> parametro = new ArrayList<>();
        final String sql = "select idempregado,nome from " + this.getTableName() + " where idUnidade = ? and datafim is null ";
        parametro.add(idUnidade);
        final List<?> lista = this.execSQL(sql.toString(), parametro.toArray());
        if (lista != null && !lista.isEmpty()) {
            return true;
        }
        return false;
    }

    public EmpregadoDTO restoreEmpregadoSeAtivo(final EmpregadoDTO empregadoDto) {
        final List<Condition> condicoes = new ArrayList<>();
        condicoes.add(new Condition("idEmpregado", "=", empregadoDto.getIdEmpregado()));
        condicoes.add(new Condition("dataFim", "is", null));
        try {
            final List<EmpregadoDTO> listaEmpregados = (List<EmpregadoDTO>) this.findByCondition(condicoes, null);
            if (listaEmpregados != null && listaEmpregados.size() > 0) {
                return listaEmpregados.get(0);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return new EmpregadoDTO();
    }

    /**
     * MÈtodo para consultar Unidade do Empregado
     *
     * @author rodrigo.oliveira
     * @param idEmpregado
     * @return Integer - idUnidade
     * @throws Exception
     */
    public Integer consultaUnidadeDoEmpregado(final Integer idEmpregado) throws PersistenceException {
        final List<Condition> condicoes = new ArrayList<>();
        condicoes.add(new Condition("idEmpregado", "=", idEmpregado));
        condicoes.add(new Condition("dataFim", "is", null));
        final List<EmpregadoDTO> resultados = (List<EmpregadoDTO>) this.findByCondition(condicoes, null);
        final EmpregadoDTO resp = resultados.get(0);
        return resp.getIdUnidade();
    }

    public Collection<EmpregadoDTO> findByNomeEmpregado(final EmpregadoDTO empregadoDTO) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("nome", "=", empregadoDTO.getNome()));
        ordenacao.add(new Order("nome"));
        condicao.add(new Condition(Condition.AND, "dataFim", "is", null));
        return super.findByCondition(condicao, ordenacao);
    }

    public EmpregadoDTO restoreByNomeEmpregado(final EmpregadoDTO empregadoDTO) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("nome", "=", empregadoDTO.getNome()));
        ordenacao.add(new Order("nome"));
        condicao.add(new Condition(Condition.AND, "dataFim", "is", null));
        final List<EmpregadoDTO> resultados = (List<EmpregadoDTO>) this.findByCondition(condicao, ordenacao);
        return resultados.get(0);
    }

    /**
     * Restaura o EmpregadoDTO com o Nome cargo a partir do ID Empregado informado.
     *
     * @param idEmpregado
     * @return EmpregadoDTO com NomeCargo
     * @author maycon.fernandes
     */
    public EmpregadoDTO restoreEmpregadoAndNomeCargoByIdEmpegado(final Integer idEmpregado) throws PersistenceException {
        final List<Integer> parametro = new ArrayList<>();
        final StringBuilder sql = new StringBuilder();

        sql.append(" select em.idEmpregado, em.nome, em.telefone, em.email, ca.nomecargo  from ");
        sql.append(" empregados em inner join cargos ca on em.idCargo = ca.idCargo ");
        sql.append(" where em.idempregado = ? ");

        parametro.add(idEmpregado);
        final List<?> listaDados = this.execSQL(sql.toString(), parametro.toArray());
        final List<String> listRetorno = new ArrayList<>();

        listRetorno.add("idEmpregado");
        listRetorno.add("nome");
        listRetorno.add("telefone");
        listRetorno.add("email");
        listRetorno.add("nomeCargo");

        final List<EmpregadoDTO> result = engine.listConvertion(this.getBean(), listaDados, listRetorno);
        return result.get(0);
    }

    public Collection<EmpregadoDTO> listarEmailsNotificacoesPasta(final Integer idPasta) throws PersistenceException {
        final StringBuilder sql = new StringBuilder();
        final List<Integer> parametro = new ArrayList<>();
        sql.append("select email from empregados e where idempregado in(select e.idempregado from pasta p ");
        sql.append("inner join notificacaogrupo ng on p.idnotificacao = ng.idnotificacao and p.idpasta = ? ");
        sql.append("inner join gruposempregados ge on ng.idgrupo = ge.idgrupo and enviaemail = 'S' ");
        sql.append("inner join empregados e on ge.idempregado = e.idempregado) or e.idempregado in(select e.idempregado from pasta p ");
        sql.append("inner join notificacaousuario nu on p.idnotificacao = nu.idnotificacao and p.idpasta = ? ");
        sql.append("inner join usuario u on nu.idusuario = u.idusuario ");
        sql.append("inner join empregados e on u.idempregado = e.idempregado) ");
        sql.append("group by email");
        parametro.add(idPasta);
        parametro.add(idPasta);
        final List<?> lista = this.execSQL(sql.toString(), parametro.toArray());
        final List<String> listRetorno = new ArrayList<>();
        listRetorno.add("email");
        return engine.listConvertion(this.getBean(), lista, listRetorno);
    }

    public Collection<EmpregadoDTO> listarEmailsNotificacoesConhecimento(final Integer idConhecimento) throws PersistenceException {
        final StringBuilder sql = new StringBuilder();
        final List<Integer> parametro = new ArrayList<>();
        sql.append("select email from empregados e where idempregado in(select e.idempregado from baseconhecimento bc ");
        sql.append("inner join notificacaogrupo ng on bc.idnotificacao = ng.idnotificacao and bc.idbaseconhecimento = ? ");
        sql.append("inner join gruposempregados ge on ng.idgrupo = ge.idgrupo and enviaemail = 'S' ");
        sql.append("inner join empregados e on ge.idempregado = e.idempregado) or e.idempregado in (select e.idempregado from baseconhecimento bc ");
        sql.append("inner join notificacaousuario nu on bc.idnotificacao = nu.idnotificacao and bc.idbaseconhecimento = ? ");
        sql.append("inner join usuario u on nu.idusuario = u.idusuario ");
        sql.append("inner join empregados e on u.idempregado = e.idempregado) ");
        sql.append("group by email");
        parametro.add(idConhecimento);
        parametro.add(idConhecimento);
        final List<?> lista = this.execSQL(sql.toString(), parametro.toArray());
        final List<String> listRetorno = new ArrayList<>();
        listRetorno.add("email");
        return engine.listConvertion(this.getBean(), lista, listRetorno);
    }

    public Collection<EmpregadoDTO> listarEmailsNotificacoesServico(final Integer idServico) throws PersistenceException {
        final StringBuilder sql = new StringBuilder();
        final List<Integer> parametro = new ArrayList<>();
        sql.append("SELECT distinct email ");
        sql.append("FROM   empregados ");
        sql.append("       JOIN (SELECT * ");
        sql.append("             FROM   (SELECT ge.idempregado ");
        sql.append("                     FROM   notificacaogrupo ng ");
        sql.append("                            JOIN gruposempregados ge ");
        sql.append("                              ON ng.idgrupo = ge.idgrupo ");
        sql.append("                            JOIN (SELECT idnotificacao ");
        sql.append("                                  FROM   notificacaoservico ");
        sql.append("                                  WHERE  idservico = ?) s ");
        sql.append("                              ON ng.idnotificacao = s.idnotificacao ");
        sql.append("                     UNION ");
        sql.append("                     SELECT u.idempregado ");
        sql.append("                     FROM   notificacaousuario nu ");
        sql.append("                            JOIN usuario u ");
        sql.append("                              ON nu.idusuario = u.idusuario ");
        sql.append("                            JOIN (SELECT idnotificacao ");
        sql.append("                                  FROM   notificacaoservico ");
        sql.append("                                  WHERE  idservico = ?) us ");
        sql.append("                              ON nu.idnotificacao = us.idnotificacao) emps) ");
        sql.append("            empperm ");
        sql.append("         ON empregados.idempregado = empperm.idempregado ");
        parametro.add(idServico);
        parametro.add(idServico);
        final List<?> lista = this.execSQL(sql.toString(), parametro.toArray());
        final List<String> listRetorno = new ArrayList<>();
        listRetorno.add("email");
        return engine.listConvertion(this.getBean(), lista, listRetorno);
    }

    /**
     * Retorna verdadeiro para ativo ou falso para inativo de acordo com nome do empregado passado.
     *
     * @param obj
     * @return
     * @throws Exception
     * @author thays.araujo
     */
    public boolean verificarEmpregadosAtivos(final EmpregadoDTO obj) throws PersistenceException {
        final List<String> parametro = new ArrayList<>();
        String sql = "select idempregado from " + this.getTableName() + "  where  (nome like ? or nome like ?)  and dataFim is null ";

        if (obj.getIdEmpregado() != null) {
            sql += " and idempregado <> " + obj.getIdEmpregado();
        }

        parametro.add(" " + obj.getNome());
        parametro.add(obj.getNome());
        final List<?> list = this.execSQL(sql, parametro.toArray());
        if (list != null && !list.isEmpty()) {
            return true;
        }
        return false;
    }

    public EmpregadoDTO restoreByEmail(final String email) throws PersistenceException {
        final List<Order> ordem = new ArrayList<>();
        ordem.add(new Order("email"));
        final EmpregadoDTO empregadoDTO = new EmpregadoDTO();
        empregadoDTO.setEmail(email);

        final List<EmpregadoDTO> col = (List<EmpregadoDTO>) super.find(empregadoDTO, null);
        if (col == null || col.size() == 0) {
            return null;
        }
        return col.get(0);
    }

    public IDto restauraTodos(final IDto obj) throws PersistenceException {
        final EmpregadoDTO empregadoDto = (EmpregadoDTO) obj;

        final List<Condition> condicao = new ArrayList<>();
        condicao.add(new Condition("idEmpregado", "=", empregadoDto.getIdEmpregado()));
        final List<EmpregadoDTO> p = (List<EmpregadoDTO>) super.findByCondition(condicao, null);

        if (p != null && p.size() > 0) {
            return p.get(0);
        }
        return null;
    }

    /**
     * Retorna EmpregadoDTO (idEmpregado e Nome). Esta consulta È a mesma da LOOKUP_SOLICITANTE_CONTRATO.
     *
     * @param nome
     *            - Nome do Empregado (Campo Nome da tabela Empregados)
     * @param idContrato
     *            - Identificador do Contrato.
     * @return Collection<EmpregadoDTO> - Lista de Empregados com Id e Nome.
     * @throws Exception
     * @author valdoilo.damasceno 29.10.2013
     */
    public Collection<EmpregadoDTO> findSolicitanteByNomeAndIdContratoAndIdUnidade(String nome, final Integer idContrato, final Integer idUnidade) throws PersistenceException {
        if (nome == null) {
            nome = "";
        }

        String text = nome;
        text = Normalizer.normalize(text, Normalizer.Form.NFD);
        text = text.replaceAll("[^\\p{ASCII}]", "");
        text = text.replaceAll("·‡„‚ÈÍÌÛÙı˙¸Á¡¿√¬… Õ”‘’⁄‹«¥`^''-+=", "aaaaeeiooouucAAAAEEIOOOUUC");
        nome = text;
        nome = "%" + nome.toUpperCase() + "%";

        final List<Object> parametros = new ArrayList<>();

        final StringBuilder sql = new StringBuilder();

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
            sql.append(" SELECT DISTINCT empregados.idEmpregado, empregados.nome as nome ");
        } else {
            sql.append(" SELECT DISTINCT empregados.idEmpregado, trim(empregados.nome) as nome ");
        }
        sql.append(" FROM empregados ");
        sql.append(" INNER JOIN gruposempregados ON empregados.idempregado = gruposempregados.idempregado ");
        sql.append(" INNER JOIN contratosgrupos ON gruposempregados.idgrupo = contratosgrupos.idgrupo ");
        sql.append(" WHERE (empregados.datafim is null) AND (empregados.idsituacaofuncional <> 2) AND (empregados.nome <> 'Administrador') AND contratosgrupos.idcontrato = ? ");

        parametros.add(idContrato);

        if (idUnidade != null) {
            sql.append(" AND empregados.idunidade = ? ");
            parametros.add(idUnidade);
        }

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.POSTGRESQL)) {
            sql.append(" and (UPPER(remove_acento(empregados.nome)) LIKE UPPER(remove_acento(?)))");
        } else {
            sql.append(" and UPPER(empregados.nome) LIKE UPPER(?)");
        }

        parametros.add(nome);

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
            sql.append(" COLLATE Latin1_General_CI_AI ");
        }

        sql.append(" ORDER BY nome ");

        final List<?> list = this.execSQL(sql.toString(), parametros.toArray());

        final List<String> listRetorno = new ArrayList<>();
        listRetorno.add("idEmpregado");
        listRetorno.add("nome");

        return engine.listConvertion(this.getBean(), list, listRetorno);
    }

    /**
     * Pesquisa Empregado por Telefone ou Ramal. Retorna o primeiro Empregado encontrado para o Ramal ou Telefone informado. <<< ATEN«√O >> o par‚metro Telefone antes de ser
     * enviado para o mÈtodo,
     * deve ser tratado com o MÈtodo mascaraProcuraSql() da Classe Utilit·ria br.com.centralit.citcorpore.util.Telefone.java;
     *
     * @param telefone
     * @return EmpregadoDTO
     * @author valdoilo.damasceno
     */
    public EmpregadoDTO findByTelefoneOrRamal(final String ramal) {
        EmpregadoDTO empregadoDto = new EmpregadoDTO();
        final StringBuilder sql = new StringBuilder();

        try {
            sql.append(" select " + this.getNamesFieldsStr());
            sql.append(" from empregados ");
            sql.append(" where (telefone " + ramal + ") or (ramal " + ramal + ") and dataFim is null");

            final List<?> lista = this.execSQL(sql.toString(), null);

            final List<EmpregadoDTO> listEmpregadoDto = engine.listConvertion(this.getBean(), lista, (List) this.getFields());

            if (listEmpregadoDto != null && !listEmpregadoDto.isEmpty()) {
                empregadoDto = listEmpregadoDto.get(0);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }

        return empregadoDto;
    }

    /**
     * Restaura o EmpregadoDTO com o ID do Contrato Padr„o (Primeiro contrato encontrado para o Empregado) a partir do ID Empregado informado.
     *
     * @param idEmpregado
     * @return EmpregadoDTO com IDContrato
     * @author valdoilo.damasceno
     */
    public EmpregadoDTO restoreEmpregadoWithIdContratoPadraoByIdEmpregado(final Integer idEmpregado) {
        if (idEmpregado != null && idEmpregado.intValue() > 0) {
            final StringBuilder sql = new StringBuilder();

            final List<Integer> parametros = new ArrayList<>();
            final List<Object> listRetorno = new ArrayList<>();

            sql.append(" select " + this.getNamesFieldsStr("empregados") + ", contratosgrupos.idcontrato ");
            sql.append(" from empregados inner join gruposempregados on empregados.idempregado = gruposempregados.idempregado ");
            sql.append(" inner join contratosgrupos on gruposempregados.idgrupo = contratosgrupos.idgrupo where empregados.idempregado = ?");

            parametros.add(idEmpregado);

            try {
                final List<?> list = this.execSQL(sql.toString(), parametros.toArray());

                List<EmpregadoDTO> listEmpregadoWithIdContrato = null;
                if (list != null && !list.isEmpty()) {
                    listRetorno.addAll(this.getFields());
                    listRetorno.add("idContrato");

                    listEmpregadoWithIdContrato = this.listConvertion(this.getBean(), list, listRetorno);
                    if (listEmpregadoWithIdContrato != null && !listEmpregadoWithIdContrato.isEmpty()) {
                        return listEmpregadoWithIdContrato.get(0);
                    }
                }
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Encontra o Empregado pelo ID
     *
     * @author euler.ramos
     * @throws Exception
     */
    public List<EmpregadoDTO> findByIdEmpregado(final Integer id) throws PersistenceException {
        List resp = new ArrayList<>();

        final Collection fields = this.getFields();
        final List parametro = new ArrayList<>();
        final List listRetorno = new ArrayList<>();
        String campos = "";
        for (final Iterator it = fields.iterator(); it.hasNext();) {
            final Field field = (Field) it.next();
            if (!campos.trim().equalsIgnoreCase("")) {
                campos = campos + ",";
            }
            campos = campos + field.getFieldDB();
            listRetorno.add(field.getFieldClass());
        }

        final String sql = "SELECT " + campos + " FROM " + this.getTableName() + " WHERE idempregado=? ORDER BY idempregado";
        parametro.add(id);
        resp = this.execSQL(sql, parametro.toArray());

        final List<EmpregadoDTO> result = engine.listConvertion(this.getBean(), resp, listRetorno);
        return result == null ? new ArrayList<EmpregadoDTO>() : result;
    }

    /**
     * Encontra o Empregado pelo nome
     *
     * @author euler.ramos
     * @throws Exception
     */
    public List<EmpregadoDTO> findByNomeEmpregado(final String nome) throws PersistenceException {
        final Collection<Field> fields = this.getFields();
        final List<String> parametro = new ArrayList<>();
        final List<String> listRetorno = new ArrayList<>();
        String campos = "";
        for (final Field field : fields) {
            if (!campos.trim().equalsIgnoreCase("")) {
                campos = campos + ",";
            }
            campos = campos + field.getFieldDB();
            listRetorno.add(field.getFieldClass());
        }

        final String sql = "SELECT " + campos + " FROM " + this.getTableName() + " WHERE nomeprocura = ? ORDER BY nome";
        parametro.add(nome);

        final List<?> resp = this.execSQL(sql, parametro.toArray());

        final List<EmpregadoDTO> result = engine.listConvertion(this.getBean(), resp, listRetorno);
        return result == null ? new ArrayList<EmpregadoDTO>() : result;
    }

    /**
     * Consulta o nome dos empregados com nome diferente de Administrador e data fim diferente de zero com limit de 10 registros
     *
     * @param nomeEmpregado
     * @return Collection<EmpregadoDTO> Com empregados com nome diferente de Administrador e data fim diferente de zero
     * @throws Exception
     */
    public Collection<EmpregadoDTO> consultarNomeEmpregadoSemAdministrador(String nomeEmpregado) throws PersistenceException {
        if (nomeEmpregado == null) {
            nomeEmpregado = "";
        }

        String texto = Normalizer.normalize(nomeEmpregado, Normalizer.Form.NFD);
        texto = texto.replaceAll("[^\\p{ASCII}]", "");
        texto = texto.replaceAll("·‡„‚ÈÍÌÛÙı˙¸Á¡¿√¬… Õ”‘’⁄‹«¥`^''-+=", "aaaaeeiooouucAAAAEEIOOOUUC ");
        texto = "%" + texto.toUpperCase() + "%";

        final Object[] objs = new Object[] {texto};

        final StringBuilder sql = new StringBuilder("select ");
        if (CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.SQLSERVER)) {
            sql.append("TOP 10 ");
        }
        sql.append("idempregado, nome from empregados ");

        sql.append(" where ");
        if (CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.ORACLE)) {
            sql.append("ROWNUM <= 10 and ");
        }
        sql.append("upper(nome) like upper(?) and nome <> 'Administrador' and datafim is null and (tipo <> 'N' OR tipo IS NULL)");
        sql.append(" order by nome");
        if (CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.POSTGRESQL)
                || CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.MYSQL)) {
            sql.append(" LIMIT 10");
        }

        final List<?> list = this.execSQL(sql.toString(), objs);

        final List<String> listRetorno = new ArrayList<>();
        listRetorno.add("idEmpregado");
        listRetorno.add("nome");

        return engine.listConvertion(this.getBean(), list, listRetorno);
    }

    /**
     * Consulta o nome dos empregados com nome diferente de Administrador e data fim diferente de zero com limit de 10 registros
     *
     * @param nomeEmpregado
     * @return Collection<EmpregadoDTO> Com empregados com nome diferente de Administrador e data fim diferente de zero
     * @throws Exception
     */
    public Collection<EmpregadoDTO> consultarNomeNaoEmpregado(String nomeEmpregado) throws PersistenceException {
        if (nomeEmpregado == null) {
            nomeEmpregado = "";
        }

        String texto = Normalizer.normalize(nomeEmpregado, Normalizer.Form.NFD);
        texto = texto.replaceAll("[^\\p{ASCII}]", "");
        texto = texto.replaceAll("·‡„‚ÈÍÌÛÙı˙¸Á¡¿√¬… Õ”‘’⁄‹«¥`^''-+=", "aaaaeeiooouucAAAAEEIOOOUUC ");
        texto = "%" + texto.toUpperCase() + "%";

        final Object[] objs = new Object[] {texto};

        final StringBuilder sql = new StringBuilder("select ");
        if (CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.SQLSERVER)) {
            sql.append("TOP 10 ");
        }
        sql.append("idempregado, nome, tipo from empregados ");
        sql.append("where ");
        if (CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.ORACLE)) {
            sql.append("ROWNUM <= 10 and ");
        }
        sql.append("upper(nome) like upper(?) and nome <> 'Administrador' and datafim is null and tipo = 'N'");
        sql.append(" order by nome");
        if (CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.POSTGRESQL)
                || CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.MYSQL)) {
            sql.append(" LIMIT 10");
        }

        final List<?> list = this.execSQL(sql.toString(), objs);

        final List<String> listRetorno = new ArrayList<>();
        listRetorno.add("idEmpregado");
        listRetorno.add("nome");
        listRetorno.add("tipo");

        return engine.listConvertion(this.getBean(), list, listRetorno);
    }

    public EmpregadoDTO restoreByCPF(final String cpf) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("cpf", "=", cpf));
        ordenacao.add(new Order("nome"));
        final List<EmpregadoDTO> col = (List<EmpregadoDTO>) super.findByCondition(condicao, ordenacao);
        if (col == null || col.size() == 0) {
            return null;
        }
        return col.get(0);
    }

    /**
     * Restaura Empregado pelo ID do Usu·rio.
     *
     * @param idUsuario
     *            - Identificador do Usu·rio.
     * @return EmpregadoDTO - Empregado do Usu·rio.
     * @author valdoilo.damasceno
     * @since 16.06.2014
     */
    public EmpregadoDTO restoreByIdUsuario(final Integer idUsuario) {
        if (idUsuario != null) {
            final StringBuilder sql = new StringBuilder();

            final List<Integer> parametros = new ArrayList<>();

            sql.append(" select " + this.getNamesFieldsStr("em"));
            sql.append(" from empregados em ");
            sql.append(" inner join usuario us ");
            sql.append(" on em.idempregado = us.idempregado ");
            sql.append(" where us.idusuario = ? ");

            parametros.add(idUsuario);

            try {
                final List<?> dados = this.execSQL(sql.toString(), parametros.toArray());

                final List<EmpregadoDTO> resultado = this.listConvertion(EmpregadoDTO.class, dados, this.getListNamesFieldClass());

                if (resultado != null && !resultado.isEmpty()) {
                    return resultado.get(0);
                }
            } catch (final Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    /**
     * Restaura EmpregadosDTO pelo idGrupo.
     *
     * @param idGrupo
     *            - Identificador do Grupo.
     * @return Collection<EmpregadoDTO> - Lista de usu·rios do Grupo informado.
     * @author valdoilo.damasceno
     * @since 16.06.2014
     */
    public Collection<EmpregadoDTO> restoreByIdGrupo(final Integer idGrupo) {
        final StringBuilder sql = new StringBuilder();
        final List<Integer> parametros = new ArrayList<>();

        sql.append(" select " + this.getNamesFieldsStr("em"));
        sql.append(" from empregados em ");
        sql.append(" inner join gruposempregados ge on em.idempregado = ge.idempregado ");
        sql.append(" where ge.idgrupo = ?  and em.datafim is null");

        parametros.add(idGrupo);

        try {
            final List<?> dados = this.execSQL(sql.toString(), parametros.toArray());

            return this.listConvertion(EmpregadoDTO.class, dados, this.getListNamesFieldClass());
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static final String PATTERN_LIKE_NAME = "%%%s%%";

    public List<EmpregadoDTO> findByNomeEmpregadoAndGrupo(final String nomeEmpregado, final Integer idGrupo) throws PersistenceException {
        final DataBase dataBase = DataBase.fromStringId(CITCorporeUtil.SGBD_PRINCIPAL);

        final StringBuilder sql = new StringBuilder("SELECT ");
        if (dataBase.equals(DataBase.MSSQLSERVER)) {
            sql.append("TOP 20 ");
        }
        sql.append("emp.idempregado, emp.nome ");
        sql.append("FROM ").append(this.getTableName()).append(" emp ");
        sql.append("     LEFT JOIN gruposempregados gemp ");
        sql.append("            ON gemp.idempregado = emp.idempregado ");
        sql.append("WHERE gemp.idgrupo = ? ");
        if (dataBase.equals(DataBase.ORACLE)) {
            sql.append("      AND ROWNUM <= 20 ");
        }
        sql.append("      AND UPPER(emp.nome) LIKE UPPER(?) ");
        sql.append("      AND emp.datafim IS NULL ");
        sql.append("ORDER BY emp.nome");
        if (dataBase.equals(DataBase.POSTGRESQL) || dataBase.equals(DataBase.MYSQL)) {
            sql.append(" LIMIT 20");
        }

        final List<String> listRetorno = new ArrayList<>();
        listRetorno.add("idEmpregado");
        listRetorno.add("nome");

        List<EmpregadoDTO> result = new ArrayList<>();
        final List<?> list = this.execSQL(sql.toString(), new Object[] {idGrupo, String.format(PATTERN_LIKE_NAME, nomeEmpregado)});
        if (list != null && !list.isEmpty()) {
            result = engine.listConvertion(this.getBean(), list, listRetorno);
        }

        return result;
    }

}
