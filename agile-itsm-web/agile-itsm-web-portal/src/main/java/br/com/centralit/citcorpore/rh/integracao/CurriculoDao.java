package br.com.centralit.citcorpore.rh.integracao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.citcorpore.rh.bean.CurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.PesquisaCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoPessoalDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.SQLConfig;

public class CurriculoDao extends CrudDaoDefaultImpl {

    public CurriculoDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection find(final IDto obj) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();

        listFields.add(new Field("idCurriculo", "idCurriculo", true, true, false, false));
        listFields.add(new Field("dataNascimento", "dataNascimento", false, false, false, false));
        listFields.add(new Field("portadorNecessidadeEspecial", "portadorNecessidadeEspecial", false, false, false, false));
        listFields.add(new Field("idItemListaTipoDeficiencia", "idItemListaTipoDeficiencia", false, false, false, false));
        listFields.add(new Field("qtdeFilhos", "qtdeFilhos", false, false, false, false));
        listFields.add(new Field("filhos", "filhos", false, false, false, false));
        listFields.add(new Field("idNacionalidade", "idNacionalidade", false, false, false, false));
        listFields.add(new Field("nacionalidade", "nacionalidade", false, false, false, false));
        listFields.add(new Field("idCidadeNatal", "idCidadeNatal", false, false, false, false));
        listFields.add(new Field("idEstadoNatal", "idEstadoNatal", false, false, false, false));
        listFields.add(new Field("nome", "nome", false, false, false, false));
        listFields.add(new Field("sexo", "sexo", false, false, false, false));
        listFields.add(new Field("cpf", "cpf", false, false, false, false));
        listFields.add(new Field("estadoCivil", "estadoCivil", false, false, false, false));
        listFields.add(new Field("observacoesEntrevista", "observacoesEntrevista", false, false, false, false));
        listFields.add(new Field("listaNegra", "listaNegra", false, false, false, false));
        listFields.add(new Field("pretensaoSalarial", "pretensaoSalarial", false, false, false, false));
        // listFields.add(new Field("indicacao" ,"indicacao", false, false, false, false));
        // listFields.add(new Field("idEmpregadoIndicacao" ,"idEmpregadoIndicacao", false, false, false, false));
        // listFields.add(new Field("observacoes" ,"observacoes", false, false, false, false));

        return listFields;
    }

    @Override
    public String getTableName() {
        return "RH_Curriculo";
    }

    @Override
    public Collection list() throws PersistenceException {
        final List list = new ArrayList();
        list.add(new Order("nome"));
        return super.list(list);
    }

    @Override
    public Class<CurriculoDTO> getBean() {
        return CurriculoDTO.class;
    }

    /**
     * @param id
     *            - idCurriculo
     * @return curriculoDTO - Coleção com o Nome do Candidato.
     * @author david.silva
     *
     *         Iniciativa 074
     *         Adaptação necessaria para recuperação do nome
     *
     */
    public CurriculoDTO restoreByID(final Integer id) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("idCurriculo", "=", id));
        ordenacao.add(new Order("idCurriculo"));
        final List list = (List) super.findByCondition(condicao, ordenacao);
        final CurriculoDTO curriculoDTO = (CurriculoDTO) list.get(0);
        return curriculoDTO;
    }

    public CurriculoDTO findIdByCpf(final String cpf) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("cpf", "=", cpf));
        ordenacao.add(new Order("idCurriculo"));
        final List list = (List) super.findByCondition(condicao, ordenacao);
        if (list != null) {
            return (CurriculoDTO) list.get(0);
        }
        return null;
    }

    /**
     * Método utilizado para retorno de busca de acordo com a idsCurriculosTriados, pgAtual e qtdPaginacao
     *
     * @param requisicaoPessoalDTO
     * @param idsCurriculosTriados
     * @return Collection<CurriculoDTO>
     * @throws Exception
     *
     */
    public Collection<CurriculoDTO> listaCurriculosPorCriterios(final RequisicaoPessoalDTO requisicaoPessoalDTO, final String idsCurriculosTriados, Integer pgAtual,
            final Integer qtdPaginacao) throws PersistenceException {
        final List<String> listRetorno = new ArrayList<>();

        final StringBuilder sql = new StringBuilder();
        final List<String> parametros = new ArrayList<>();

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
            sql.append(" ;WITH TabelaTemporaria AS ( ");
        }

        sql.append("SELECT distinct c.idCurriculo, c.dataNascimento, c.cidadeNatal, c.nome, c.cpf ");

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
            sql.append(" , ROW_NUMBER() OVER (ORDER BY c.idCurriculo) AS Row ");
        }

        sql.append(" FROM rh_curriculo c WHERE 1=1 ");

        // -- Inicio -- Formação
        if (requisicaoPessoalDTO.getPesquisa_formacao() != null && !requisicaoPessoalDTO.getPesquisa_formacao().equals("")) {
            final String[] parametroE = requisicaoPessoalDTO.getPesquisa_formacao().toLowerCase().split(" and ");

            sql.append(" AND ( ");
            for (final String element : parametroE) {
                final String[] parametroOU = element.toLowerCase().split(" or ");

                if (parametroOU.length <= 1) {
                    if (sql.toString().endsWith(") ")) {
                        sql.append(" AND ");
                    }
                    sql.append(" ( c.idcurriculo IN ( SELECT fc.idcurriculo FROM rh_formacaocurriculo fc WHERE c.idcurriculo = fc.idcurriculo AND fc.descricao LIKE ? ) ) ");
                    if (element.indexOf("\"") >= 0) {
                        parametros.add(element.replaceAll("\"", ""));
                    } else {
                        parametros.add("%" + element + "%");
                    }
                } else {
                    if (sql.toString().endsWith(") ")) {
                        sql.append(" OR ( ");
                    } else {
                        sql.append(" ( ");
                    }
                    for (int j = 0; j < parametroOU.length; j++) {
                        if (j > 0) {
                            sql.append(" OR ");
                        }
                        sql.append(" c.idcurriculo IN ( SELECT idcurriculo FROM rh_formacaocurriculo WHERE descricao LIKE ? ) ");
                        if (parametroOU[j].indexOf("\"") >= 0) {
                            parametros.add(parametroOU[j].replaceAll("\"", ""));
                        } else {
                            parametros.add("%" + parametroOU[j] + "%");
                        }
                    }
                    sql.append(" ) ");
                }
            }
            sql.append(" ) ");
        }
        // -- Fim -- Formação

        // -- Inicio -- Idioma
        if (requisicaoPessoalDTO.getPesquisa_idiomas() != null && !requisicaoPessoalDTO.getPesquisa_idiomas().equals("")) {
            final String[] parametroE = requisicaoPessoalDTO.getPesquisa_idiomas().toLowerCase().split(" and ");

            sql.append(" AND ( ");
            for (final String element : parametroE) {
                final String[] parametroOU = element.toLowerCase().split(" or ");

                if (parametroOU.length <= 1) {
                    if (sql.toString().endsWith(") ")) {
                        sql.append(" AND ");
                    }
                    sql.append(" ( c.idcurriculo IN ( SELECT idic.idcurriculo FROM rh_idiomacurriculo idic JOIN rh_idioma idi ON idic.ididioma = idi.ididioma WHERE c.idcurriculo = idic.idcurriculo AND idi.descricao LIKE ? ) ) ");
                    if (element.indexOf("\"") >= 0) {
                        parametros.add(element.replaceAll("\"", ""));
                    } else {
                        parametros.add("%" + element + "%");
                    }
                } else {
                    if (sql.toString().endsWith(") ")) {
                        sql.append(" OR ( ");
                    } else {
                        sql.append(" ( ");
                    }
                    for (int j = 0; j < parametroOU.length; j++) {
                        if (j > 0) {
                            sql.append(" OR ");
                        }
                        sql.append(" c.idcurriculo IN ( SELECT idic.idcurriculo FROM rh_idiomacurriculo idic JOIN rh_idioma idi ON idic.ididioma = idi.ididioma WHERE idi.descricao LIKE ? ) ");
                        if (parametroOU[j].indexOf("\"") >= 0) {
                            parametros.add(parametroOU[j].replaceAll("\"", ""));
                        } else {
                            parametros.add("%" + parametroOU[j] + "%");
                        }
                    }
                    sql.append(" ) ");
                }
            }
            sql.append(" ) ");
        }
        // -- Fim -- Idioma

        // -- Inicio -- Certificação
        if (requisicaoPessoalDTO.getPesquisa_certificacao() != null && !requisicaoPessoalDTO.getPesquisa_certificacao().equals("")) {
            final String[] parametroE = requisicaoPessoalDTO.getPesquisa_certificacao().toLowerCase().split(" and ");

            sql.append(" AND ( ");
            for (final String element : parametroE) {
                final String[] parametroOU = element.toLowerCase().split(" or ");

                if (parametroOU.length <= 1) {
                    if (sql.toString().endsWith(") ")) {
                        sql.append(" AND ");
                    }
                    sql.append(" ( c.idcurriculo IN ( SELECT cc.idcurriculo FROM rh_certificacaocurriculo cc where c.idcurriculo = cc.idcurriculo AND cc.descricao LIKE ? ) ) ");
                    if (element.indexOf("\"") >= 0) {
                        parametros.add(element.replaceAll("\"", ""));
                    } else {
                        parametros.add("%" + element + "%");
                    }
                } else {
                    if (sql.toString().endsWith(") ")) {
                        sql.append(" OR ( ");
                    } else {
                        sql.append(" ( ");
                    }
                    for (int j = 0; j < parametroOU.length; j++) {
                        if (j > 0) {
                            sql.append(" OR ");
                        }
                        sql.append(" c.idcurriculo IN ( SELECT cc.idcurriculo FROM rh_certificacaocurriculo cc where cc.descricao LIKE ? ) ");
                        if (parametroOU[j].indexOf("\"") >= 0) {
                            parametros.add(parametroOU[j].replaceAll("\"", ""));
                        } else {
                            parametros.add("%" + parametroOU[j] + "%");
                        }
                    }
                    sql.append(" ) ");
                }
            }
            sql.append(" ) ");
        }
        // -- Fim -- Certificação

        // -- Inicio -- Cidade
        if (requisicaoPessoalDTO.getPesquisa_cidade() != null && !requisicaoPessoalDTO.getPesquisa_cidade().equals("")) {
            final String[] parametroE = requisicaoPessoalDTO.getPesquisa_cidade().toLowerCase().split(" and ");

            sql.append(" AND ( ");
            for (final String element : parametroE) {
                final String[] parametroOU = element.toLowerCase().split(" or ");

                if (parametroOU.length <= 1) {
                    if (sql.toString().endsWith(") ")) {
                        sql.append(" AND ");
                    }
                    sql.append(" ( c.idcurriculo IN ( SELECT ec.idcurriculo FROM rh_enderecocurriculo ec where c.idcurriculo = ec.idcurriculo AND ec.nomecidade LIKE ? ) ) ");
                    if (element.indexOf("\"") >= 0) {
                        parametros.add(element.replaceAll("\"", ""));
                    } else {
                        parametros.add("%" + element + "%");
                    }
                } else {
                    if (sql.toString().endsWith(") ")) {
                        sql.append(" OR ( ");
                    } else {
                        sql.append(" ( ");
                    }
                    for (int j = 0; j < parametroOU.length; j++) {
                        if (j > 0) {
                            sql.append(" OR ");
                        }
                        sql.append(" c.idcurriculo IN ( SELECT ec.idcurriculo FROM rh_enderecocurriculo ec where ec.nomecidade LIKE ? ) ");
                        if (parametroOU[j].indexOf("\"") >= 0) {
                            parametros.add(parametroOU[j].replaceAll("\"", ""));
                        } else {
                            parametros.add("%" + parametroOU[j] + "%");
                        }
                    }
                    sql.append(" ) ");
                }
            }
            sql.append(" ) ");
        }
        // -- Fim -- Cidade

        if (StringUtils.isNotBlank(idsCurriculosTriados) && !idsCurriculosTriados.equals("0")) {
            sql.append(" and c.idCurriculo not in (" + idsCurriculosTriados + ") ");
        }

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.MYSQL)) {
            sql.append(" ORDER BY c.idCurriculo ");
            final Integer pgTotal = pgAtual * qtdPaginacao;
            pgAtual = pgTotal - qtdPaginacao;
            sql.append(" LIMIT " + pgAtual + ", " + qtdPaginacao);
        }

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.POSTGRESQL)) {
            sql.append(" ORDER BY c.idCurriculo");
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

        List lista;
        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            lista = this.execSQL(sqlOracle, parametros.toArray());
        } else {
            lista = this.execSQL(sql.toString(), parametros.toArray());
        }

        listRetorno.add("idCurriculo");
        listRetorno.add("dataNascimento");
        listRetorno.add("cidadeNatal");
        listRetorno.add("nome");
        listRetorno.add("cpf");

        return engine.listConvertion(this.getBean(), lista, listRetorno);
    }

    public String paginacaoOracle(String strSQL, final int intInicio, final int intLimite) {
        strSQL = strSQL + " order by idCurriculo ";
        return "SELECT * FROM " + "(SELECT PAGING.*, ROWNUM PAGING_RN FROM" + " (" + strSQL + ") PAGING WHERE (ROWNUM <= " + intLimite + "))" + " WHERE (PAGING_RN >= " + intInicio
                + ") ";
    }

    /**
     * Método apenas para contar a quantidade de curriculo por criterios.
     *
     * @param requisicaoPessoalDTO
     * @param idsCurriculosTriados
     * @return Collection<CurriculoDTO>
     * @throws Exception
     *
     */
    public Integer contaCurriculosPorCriterios(final RequisicaoPessoalDTO requisicaoPessoalDTO, final String idsCurriculosTriados, final Integer itensPorPagina)
            throws PersistenceException {
        List lista = new ArrayList();

        final StringBuilder sql = new StringBuilder();
        final List<String> parametros = new ArrayList<>();

        sql.append("SELECT count(distinct c.idCurriculo) FROM rh_curriculo c WHERE 1=1");

        // -- Inicio -- Formação
        if (requisicaoPessoalDTO.getPesquisa_formacao() != null && !requisicaoPessoalDTO.getPesquisa_formacao().equals("")) {
            final String[] parametroE = requisicaoPessoalDTO.getPesquisa_formacao().toLowerCase().split(" and ");

            sql.append(" AND ( ");
            for (final String element : parametroE) {
                final String[] parametroOU = element.toLowerCase().split(" or ");

                if (parametroOU.length <= 1) {
                    if (sql.toString().endsWith(") ")) {
                        sql.append(" AND ");
                    }
                    sql.append(" ( c.idcurriculo IN ( SELECT fc.idcurriculo FROM rh_formacaocurriculo fc WHERE c.idcurriculo = fc.idcurriculo AND fc.descricao LIKE ? ) ) ");
                    if (element.indexOf("\"") >= 0) {
                        parametros.add(element.replaceAll("\"", ""));
                    } else {
                        parametros.add("%" + element + "%");
                    }
                } else {
                    if (sql.toString().endsWith(") ")) {
                        sql.append(" OR ( ");
                    } else {
                        sql.append(" ( ");
                    }
                    for (int j = 0; j < parametroOU.length; j++) {
                        if (j > 0) {
                            sql.append(" OR ");
                        }
                        sql.append(" c.idcurriculo IN ( SELECT idcurriculo FROM rh_formacaocurriculo WHERE descricao LIKE ? ) ");
                        if (parametroOU[j].indexOf("\"") >= 0) {
                            parametros.add(parametroOU[j].replaceAll("\"", ""));
                        } else {
                            parametros.add("%" + parametroOU[j] + "%");
                        }
                    }
                    sql.append(" ) ");
                }
            }
            sql.append(" ) ");
        }
        // -- Fim -- Formação

        // -- Inicio -- Idioma
        if (requisicaoPessoalDTO.getPesquisa_idiomas() != null && !requisicaoPessoalDTO.getPesquisa_idiomas().equals("")) {
            final String[] parametroE = requisicaoPessoalDTO.getPesquisa_idiomas().toLowerCase().split(" and ");

            sql.append(" AND ( ");
            for (final String element : parametroE) {
                final String[] parametroOU = element.toLowerCase().split(" or ");

                if (parametroOU.length <= 1) {
                    if (sql.toString().endsWith(") ")) {
                        sql.append(" AND ");
                    }
                    sql.append(" ( c.idcurriculo IN ( SELECT idic.idcurriculo FROM rh_idiomacurriculo idic JOIN rh_idioma idi ON idic.ididioma = idi.ididioma WHERE c.idcurriculo = idic.idcurriculo AND idi.descricao LIKE ? ) ) ");
                    if (element.indexOf("\"") >= 0) {
                        parametros.add(element.replaceAll("\"", ""));
                    } else {
                        parametros.add("%" + element + "%");
                    }
                } else {
                    if (sql.toString().endsWith(") ")) {
                        sql.append(" OR ( ");
                    } else {
                        sql.append(" ( ");
                    }
                    for (int j = 0; j < parametroOU.length; j++) {
                        if (j > 0) {
                            sql.append(" OR ");
                        }
                        sql.append(" c.idcurriculo IN ( SELECT idic.idcurriculo FROM rh_idiomacurriculo idic JOIN rh_idioma idi ON idic.ididioma = idi.ididioma WHERE idi.descricao LIKE ? ) ");
                        if (parametroOU[j].indexOf("\"") >= 0) {
                            parametros.add(parametroOU[j].replaceAll("\"", ""));
                        } else {
                            parametros.add("%" + parametroOU[j] + "%");
                        }
                    }
                    sql.append(" ) ");
                }
            }
            sql.append(" ) ");
        }
        // -- Fim -- Idioma

        // -- Inicio -- Certificação
        if (requisicaoPessoalDTO.getPesquisa_certificacao() != null && !requisicaoPessoalDTO.getPesquisa_certificacao().equals("")) {
            final String[] parametroE = requisicaoPessoalDTO.getPesquisa_certificacao().toLowerCase().split(" and ");

            sql.append(" AND ( ");
            for (final String element : parametroE) {
                final String[] parametroOU = element.toLowerCase().split(" or ");

                if (parametroOU.length <= 1) {
                    if (sql.toString().endsWith(") ")) {
                        sql.append(" AND ");
                    }
                    sql.append(" ( c.idcurriculo IN ( SELECT cc.idcurriculo FROM rh_certificacaocurriculo cc where c.idcurriculo = cc.idcurriculo AND cc.descricao LIKE ? ) ) ");
                    if (element.indexOf("\"") >= 0) {
                        parametros.add(element.replaceAll("\"", ""));
                    } else {
                        parametros.add("%" + element + "%");
                    }
                } else {
                    if (sql.toString().endsWith(") ")) {
                        sql.append(" OR ( ");
                    } else {
                        sql.append(" ( ");
                    }
                    for (int j = 0; j < parametroOU.length; j++) {
                        if (j > 0) {
                            sql.append(" OR ");
                        }
                        sql.append(" c.idcurriculo IN ( SELECT cc.idcurriculo FROM rh_certificacaocurriculo cc where cc.descricao LIKE ? ) ");
                        if (parametroOU[j].indexOf("\"") >= 0) {
                            parametros.add(parametroOU[j].replaceAll("\"", ""));
                        } else {
                            parametros.add("%" + parametroOU[j] + "%");
                        }
                    }
                    sql.append(" ) ");
                }
            }
            sql.append(" ) ");
        }
        // -- Fim -- Certificação

        // -- Inicio -- Cidade
        if (requisicaoPessoalDTO.getPesquisa_cidade() != null && !requisicaoPessoalDTO.getPesquisa_cidade().equals("")) {
            final String[] parametroE = requisicaoPessoalDTO.getPesquisa_cidade().toLowerCase().split(" and ");

            sql.append(" AND ( ");
            for (final String element : parametroE) {
                final String[] parametroOU = element.toLowerCase().split(" or ");

                if (parametroOU.length <= 1) {
                    if (sql.toString().endsWith(") ")) {
                        sql.append(" AND ");
                    }
                    sql.append(" ( c.idcurriculo IN ( SELECT ec.idcurriculo FROM rh_enderecocurriculo ec where c.idcurriculo = ec.idcurriculo AND ec.nomecidade LIKE ? ) ) ");
                    if (element.indexOf("\"") >= 0) {
                        parametros.add(element.replaceAll("\"", ""));
                    } else {
                        parametros.add("%" + element + "%");
                    }
                } else {
                    if (sql.toString().endsWith(") ")) {
                        sql.append(" OR ( ");
                    } else {
                        sql.append(" ( ");
                    }
                    for (int j = 0; j < parametroOU.length; j++) {
                        if (j > 0) {
                            sql.append(" OR ");
                        }
                        sql.append(" c.idcurriculo IN ( SELECT ec.idcurriculo FROM rh_enderecocurriculo ec where ec.nomecidade LIKE ? ) ");
                        if (parametroOU[j].indexOf("\"") >= 0) {
                            parametros.add(parametroOU[j].replaceAll("\"", ""));
                        } else {
                            parametros.add("%" + parametroOU[j] + "%");
                        }
                    }
                    sql.append(" ) ");
                }
            }
            sql.append(" ) ");
        }
        // -- Fim -- Cidade

        if (StringUtils.isNotBlank(idsCurriculosTriados) && !idsCurriculosTriados.equals("0")) {
            sql.append(" and c.idCurriculo not in (" + idsCurriculosTriados + ") ");
        }

        // sql.append(" ORDER BY c.nome");

        lista = this.execSQL(sql.toString(), parametros.toArray());

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

    /**
     * ***********************************************WARNING*******************************************************
     *
     * @author thiago.borges metodos de busca de curriculos
     * @param pesquisaCurriculoDto
     * @return
     * @throws Exception
     */
    public Collection<CurriculoDTO> listaCurriculosPorCriterios(final PesquisaCurriculoDTO pesquisaCurriculoDto) throws PersistenceException {
        Boolean auxSql = false;

        final List listRetorno = new ArrayList();

        List lista = new ArrayList();

        final List parametros = new ArrayList();

        // listas para busca por certificacoes
        final String[] parametroCertificacaoE = pesquisaCurriculoDto.getPesquisa_certificacao().split(";");
        final String[] parametroCertificacaoOU = pesquisaCurriculoDto.getPesquisa_certificacao().split(":");

        // listas para busca por Formações
        final String[] parametroFormacaoE = pesquisaCurriculoDto.getPesquisa_formacao().split(";");
        final String[] parametroFormacaoOU = pesquisaCurriculoDto.getPesquisa_formacao().split(":");

        // listas para busca por Idiomas
        final String[] parametroIdiomaE = pesquisaCurriculoDto.getPesquisa_idiomas().split(";");
        final String[] parametroIdiomaOU = pesquisaCurriculoDto.getPesquisa_idiomas().split(":");

        // listas para busca por cidades
        final String[] parametroCidadeE = pesquisaCurriculoDto.getPesquisa_cidade().split(";");
        final String[] parametroCidadeOU = pesquisaCurriculoDto.getPesquisa_cidade().split(":");

        final StringBuilder var1 = new StringBuilder();
        var1.append("SELECT distinct c.idCurriculo, c.dataNascimento, c.cidadeNatal, c.nome, c.cpf FROM rh_curriculo c where c.idcurriculo ");

        // Inicio bloco de select por formação
        if (pesquisaCurriculoDto.getPesquisa_formacao() != null && !pesquisaCurriculoDto.getPesquisa_formacao().equals("")) {

            if (parametroFormacaoOU.length > 1) {
                for (int i = 0; i < parametroFormacaoOU.length; i++) {
                    pesquisaCurriculoDto.setPesquisa_formacao(parametroFormacaoOU[i]);
                    if (i == 0) {
                        var1.append("in (SELECT idcurriculo FROM rh_formacaocurriculo where descricao like ?)");
                    } else {
                        var1.append("or c.idcurriculo in");
                        var1.append("(SELECT idcurriculo FROM rh_formacaocurriculo where descricao like ?)");
                    }

                    // Verifica aspas e passa parametro de busca sem % %
                    if (pesquisaCurriculoDto.getPesquisa_formacao().indexOf("\"") >= 0) {
                        final String pesquisaFormacao = pesquisaCurriculoDto.getPesquisa_formacao().replaceAll("\"", "");
                        final String formacao = pesquisaFormacao;
                        parametros.add(formacao);
                    } else {
                        final String formacao = "%" + pesquisaCurriculoDto.getPesquisa_formacao() + "%";
                        parametros.add(formacao);
                    }
                }
            } else {
                if (parametroFormacaoE.length > 1) {
                    for (int i = 0; i < parametroFormacaoE.length; i++) {
                        pesquisaCurriculoDto.setPesquisa_formacao(parametroFormacaoE[i]);
                        if (i == 0) {
                            var1.append("in (SELECT idcurriculo FROM rh_formacaocurriculo where descricao like ?)");
                        } else {
                            var1.append("and c.idcurriculo in");
                            var1.append("(SELECT idcurriculo FROM rh_formacaocurriculo where descricao like ?)");
                        }

                        // Verifica aspas e passa parametro de busca sem % %
                        if (pesquisaCurriculoDto.getPesquisa_formacao().indexOf("\"") >= 0) {
                            final String pesquisaFormacao = pesquisaCurriculoDto.getPesquisa_formacao().replaceAll("\"", "");
                            final String formacao = pesquisaFormacao;
                            parametros.add(formacao);
                        } else {
                            final String formacao = "%" + pesquisaCurriculoDto.getPesquisa_formacao() + "%";
                            parametros.add(formacao);
                        }
                    }
                } else {
                    pesquisaCurriculoDto.setPesquisa_formacao(parametroFormacaoE[0]);
                    var1.append("in (SELECT idcurriculo FROM rh_formacaocurriculo where descricao like ?)");

                    // Verifica aspas e passa parametro de busca sem % %
                    if (pesquisaCurriculoDto.getPesquisa_formacao().indexOf("\"") >= 0) {
                        final String pesquisaFormacao = pesquisaCurriculoDto.getPesquisa_formacao().replaceAll("\"", "");
                        final String formacao = pesquisaFormacao;
                        parametros.add(formacao);
                    } else {
                        final String formacao = "%" + pesquisaCurriculoDto.getPesquisa_formacao() + "%";
                        parametros.add(formacao);
                    }
                }
            }

            auxSql = true;
        }
        // Fim bloco de select por formação

        // Inicio bloco de select por idioma
        if (pesquisaCurriculoDto.getPesquisa_idiomas() != null && !pesquisaCurriculoDto.getPesquisa_idiomas().equals("")) {
            if (parametroIdiomaOU.length > 1) {
                for (int i = 0; i < parametroIdiomaOU.length; i++) {
                    pesquisaCurriculoDto.setPesquisa_idiomas(parametroIdiomaOU[i]);
                    if (auxSql) {
                        var1.append("or c.idcurriculo ");
                    }
                    if (i == 0) {
                        var1.append("in (SELECT idcurriculo FROM rh_idiomacurriculo idic INNER JOIN rh_idioma idi ON idic.ididioma = idi.ididioma where descricao like ?)");
                    } else {
                        var1.append("or c.idcurriculo in");
                        var1.append("(SELECT idcurriculo FROM rh_idiomacurriculo idic INNER JOIN rh_idioma idi ON idic.ididioma = idi.ididioma where descricao like ?)");
                    }

                    // Verifica aspas e passa parametro de busca sem % %
                    if (pesquisaCurriculoDto.getPesquisa_idiomas().indexOf("\"") >= 0) {
                        final String pesquisaIdiomas = pesquisaCurriculoDto.getPesquisa_idiomas().replaceAll("\"", "");
                        final String idiomas = pesquisaIdiomas;
                        parametros.add(idiomas);
                    } else {
                        final String idiomas = "%" + pesquisaCurriculoDto.getPesquisa_idiomas() + "%";
                        parametros.add(idiomas);
                    }
                }

            } else {
                if (parametroIdiomaE.length > 1) {
                    for (int i = 0; i < parametroIdiomaE.length; i++) {
                        pesquisaCurriculoDto.setPesquisa_idiomas(parametroIdiomaE[i]);
                        if (auxSql) {
                            var1.append("and c.idcurriculo ");
                        }
                        if (i == 0) {
                            var1.append("in (SELECT idcurriculo FROM rh_idiomacurriculo idic INNER JOIN rh_idioma idi ON idic.ididioma = idi.ididioma where descricao like ?)");
                        } else {
                            var1.append("and c.idcurriculo in");
                            var1.append("(SELECT idcurriculo FROM rh_idiomacurriculo idic INNER JOIN rh_idioma idi ON idic.ididioma = idi.ididioma where descricao like ?)");
                        }

                        // Verifica aspas e passa parametro de busca sem % %
                        if (pesquisaCurriculoDto.getPesquisa_idiomas().indexOf("\"") >= 0) {
                            final String pesquisaIdiomas = pesquisaCurriculoDto.getPesquisa_idiomas().replaceAll("\"", "");
                            final String idiomas = pesquisaIdiomas;
                            parametros.add(idiomas);
                        } else {
                            final String idiomas = "%" + pesquisaCurriculoDto.getPesquisa_idiomas() + "%";
                            parametros.add(idiomas);
                        }
                    }
                } else {
                    pesquisaCurriculoDto.setPesquisa_idiomas(parametroIdiomaE[0]);
                    if (auxSql) {
                        var1.append("and c.idcurriculo ");
                    }
                    var1.append("in (SELECT idcurriculo FROM rh_idiomacurriculo idic INNER JOIN rh_idioma idi ON idic.ididioma = idi.ididioma where descricao like ?)");

                    // Verifica aspas e passa parametro de busca sem % %
                    if (pesquisaCurriculoDto.getPesquisa_idiomas().indexOf("\"") >= 0) {
                        final String pesquisaIdiomas = pesquisaCurriculoDto.getPesquisa_idiomas().replaceAll("\"", "");
                        final String idiomas = pesquisaIdiomas;
                        parametros.add(idiomas);
                    } else {
                        final String idiomas = "%" + pesquisaCurriculoDto.getPesquisa_idiomas() + "%";
                        parametros.add(idiomas);
                    }
                }
            }

            auxSql = true;
        }
        // Fim bloco de select por idioma

        // Inicio bloco de select por certificação
        if (pesquisaCurriculoDto.getPesquisa_certificacao() != null && !pesquisaCurriculoDto.getPesquisa_certificacao().equals("")) {
            if (parametroCertificacaoOU.length > 1) {
                for (int i = 0; i < parametroCertificacaoOU.length; i++) {
                    pesquisaCurriculoDto.setPesquisa_certificacao(parametroCertificacaoOU[i]);
                    if (auxSql) {
                        var1.append("or c.idcurriculo ");
                    }
                    if (i == 0) {
                        var1.append("in (SELECT idcurriculo FROM rh_certificacaocurriculo where descricao like ?)");
                    } else {
                        var1.append("or c.idcurriculo in");
                        var1.append("(SELECT idcurriculo FROM rh_certificacaocurriculo where descricao like ?)");
                    }

                    // Verifica aspas e passa parametro de busca sem % %
                    if (pesquisaCurriculoDto.getPesquisa_certificacao().indexOf("\"") >= 0) {
                        final String pesquisaCertificacao = pesquisaCurriculoDto.getPesquisa_certificacao().replaceAll("\"", "");
                        final String certificacao = pesquisaCertificacao;
                        parametros.add(certificacao);
                    } else {
                        final String certificacao = "%" + pesquisaCurriculoDto.getPesquisa_certificacao() + "%";
                        parametros.add(certificacao);
                    }

                }
            } else {
                if (parametroCertificacaoE.length > 1) {
                    for (int i = 0; i < parametroCertificacaoE.length; i++) {
                        pesquisaCurriculoDto.setPesquisa_certificacao(parametroCertificacaoE[i]);
                        if (auxSql) {
                            var1.append("and c.idcurriculo ");
                        }
                        if (i == 0) {
                            var1.append("in (SELECT idcurriculo FROM rh_certificacaocurriculo where descricao like ?)");
                        } else {
                            var1.append("and c.idcurriculo in");
                            var1.append("(SELECT idcurriculo FROM rh_certificacaocurriculo where descricao like ?)");
                        }

                        // Verifica aspas e passa parametro de busca sem % %
                        if (pesquisaCurriculoDto.getPesquisa_certificacao().indexOf("\"") >= 0) {
                            final String pesquisaCertificacao = pesquisaCurriculoDto.getPesquisa_certificacao().replaceAll("\"", "");
                            final String certificacao = pesquisaCertificacao;
                            parametros.add(certificacao);
                        } else {
                            final String certificacao = "%" + pesquisaCurriculoDto.getPesquisa_certificacao() + "%";
                            parametros.add(certificacao);
                        }

                    }
                } else {
                    pesquisaCurriculoDto.setPesquisa_certificacao(parametroCertificacaoE[0]);
                    if (auxSql) {
                        var1.append("and c.idcurriculo ");
                    }
                    var1.append("in (SELECT idcurriculo FROM rh_certificacaocurriculo where descricao like ?)");

                    // Verifica aspas e passa parametro de busca sem % %
                    if (pesquisaCurriculoDto.getPesquisa_certificacao().indexOf("\"") >= 0) {
                        final String pesquisaCertificacao = pesquisaCurriculoDto.getPesquisa_certificacao().replaceAll("\"", "");
                        final String certificacao = pesquisaCertificacao;
                        parametros.add(certificacao);
                    } else {
                        final String certificacao = "%" + pesquisaCurriculoDto.getPesquisa_certificacao() + "%";
                        parametros.add(certificacao);
                    }
                }
            }

            auxSql = true;
        }
        // Fim bloco de select por certificação

        // Inicio bloco de select por cidade
        if (pesquisaCurriculoDto.getPesquisa_cidade() != null && !pesquisaCurriculoDto.getPesquisa_cidade().equals("")) {
            if (parametroCidadeE.length > 1) {
                for (int i = 0; i < parametroCidadeE.length; i++) {
                    pesquisaCurriculoDto.setPesquisa_cidade(parametroCidadeE[i]);
                    if (auxSql) {
                        var1.append("and c.idcurriculo ");
                    }
                    if (i == 0) {
                        var1.append("in (SELECT idcurriculo FROM rh_enderecocurriculo where nomecidade like ?)");
                    } else {
                        var1.append("and c.idcurriculo in");
                        var1.append("(SELECT idcurriculo FROM rh_enderecocurriculo where nomecidade like ?)");
                    }

                    // Verifica aspas e passa parametro de busca sem % %
                    if (pesquisaCurriculoDto.getPesquisa_cidade().indexOf("\"") >= 0) {
                        final String pesquisaCidade = pesquisaCurriculoDto.getPesquisa_cidade().replaceAll("\"", "");
                        final String cidade = pesquisaCidade;
                        parametros.add(cidade);
                    } else {
                        final String cidade = "%" + pesquisaCurriculoDto.getPesquisa_cidade() + "%";
                        parametros.add(cidade);
                    }

                }
            } else {
                if (parametroCidadeOU.length > 1) {
                    for (int i = 0; i < parametroCidadeOU.length; i++) {
                        pesquisaCurriculoDto.setPesquisa_cidade(parametroCidadeOU[i]);
                        if (auxSql) {
                            var1.append("or c.idcurriculo ");
                        }
                        if (i == 0) {
                            var1.append("in (SELECT idcurriculo FROM rh_enderecocurriculo where nomecidade like ?)");
                        } else {
                            var1.append("or c.idcurriculo in");
                            var1.append("(SELECT idcurriculo FROM rh_enderecocurriculo where nomecidade like ?)");
                        }

                        // Verifica aspas e passa parametro de busca sem % %
                        if (pesquisaCurriculoDto.getPesquisa_cidade().indexOf("\"") >= 0) {
                            final String pesquisaCidade = pesquisaCurriculoDto.getPesquisa_cidade().replaceAll("\"", "");
                            final String cidade = pesquisaCidade;
                            parametros.add(cidade);
                        } else {
                            final String cidade = "%" + pesquisaCurriculoDto.getPesquisa_cidade() + "%";
                            parametros.add(cidade);
                        }

                    }
                } else {
                    pesquisaCurriculoDto.setPesquisa_cidade(parametroCidadeE[0]);
                    if (auxSql) {
                        var1.append("and c.idcurriculo ");
                    }
                    var1.append("in (SELECT idcurriculo FROM rh_enderecocurriculo where nomecidade like ?)");

                    // Verifica aspas e passa parametro de busca sem % %
                    if (pesquisaCurriculoDto.getPesquisa_cidade().indexOf("\"") >= 0) {
                        final String pesquisaCidade = pesquisaCurriculoDto.getPesquisa_cidade().replaceAll("\"", "");
                        final String cidade = pesquisaCidade;
                        parametros.add(cidade);
                    } else {
                        final String cidade = "%" + pesquisaCurriculoDto.getPesquisa_cidade() + "%";
                        parametros.add(cidade);
                    }
                }
            }

            auxSql = true;
        }
        // fim bloco de select por cidade

        lista = this.execSQL(var1.toString(), parametros.toArray());

        listRetorno.add("idCurriculo");
        listRetorno.add("dataNascimento");
        listRetorno.add("cidadeNatal");
        listRetorno.add("nome");
        listRetorno.add("cpf");

        final List listaCurriculos = engine.listConvertion(this.getBean(), lista, listRetorno);

        return listaCurriculos;
    }

    public Collection<CurriculoDTO> listaCurriculosAprovados() throws PersistenceException {
        return null;

    }

    public boolean verificaCPFJaCadastrado(final Integer idCurriculo, final String cpf) throws PersistenceException {
        final List condicao = new ArrayList();
        final List ordenacao = new ArrayList();
        condicao.add(new Condition("cpf", "=", cpf));
        condicao.add(new Condition("idCurriculo", "<>", idCurriculo));
        ordenacao.add(new Order("idCurriculo"));

        final Collection retorno = super.findByCondition(condicao, ordenacao);

        return retorno != null && !retorno.isEmpty();
    }

}
