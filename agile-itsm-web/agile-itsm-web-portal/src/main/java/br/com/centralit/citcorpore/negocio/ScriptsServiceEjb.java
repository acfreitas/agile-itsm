package br.com.centralit.citcorpore.negocio;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.ScriptsDTO;
import br.com.centralit.citcorpore.bean.VersaoDTO;
import br.com.centralit.citcorpore.integracao.ScriptsDao;
import br.com.centralit.citcorpore.integracao.VersaoDao;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.PersistenceEngine;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

public class ScriptsServiceEjb extends CrudServiceImpl implements ScriptsService {

    private static final Logger LOGGER = Logger.getLogger(ScriptsServiceEjb.class);

    private ScriptsDao dao;

    @Override
    protected ScriptsDao getDao() {
        if (dao == null) {
            dao = new ScriptsDao();
        }
        return dao;
    }

    private Map<String, String> chaves;

    @Override
    public ScriptsDTO buscaScriptPorId(final Integer id) throws Exception {
        return this.getDao().buscaScriptPorId(id);
    }

    @Override
    public void deletarScript(final IDto model, final DocumentHTML document) throws Exception {
        final ScriptsDTO scriptsDto = (ScriptsDTO) model;
        final TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());
        final ScriptsDao scriptsDao = new ScriptsDao();

        try {
            this.validaUpdate(scriptsDto);
            scriptsDao.setTransactionControler(tc);
            tc.start();
            scriptsDto.setDataFim(UtilDatas.getDataAtual());
            scriptsDao.update(scriptsDto);
            document.alert(this.i18nMessage("MSG07"));
            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
    }

    @SuppressWarnings("resource")
    @Override
    public String executaRotinaScripts() throws Exception {
        String erro = null;

        // LEITURA PROGRESSIVA DO XML

        final String separator = System.getProperty("file.separator");
        String diretorio = CITCorporeUtil.CAMINHO_REAL_APP + "XMLs" + separator;
        File file = new File(diretorio + "historicoDeVersoes.xml");
        final SAXBuilder sb = new SAXBuilder();
        final Document doc = sb.build(file);
        final Element historicoDeVersoes = doc.getRootElement();

        final List<Element> versoes = historicoDeVersoes.getChildren();

        final TransactionControler tc = new TransactionControlerImpl(Constantes.getValue("DATABASE_ALIAS"));

        final ScriptsDao scriptsDao = new ScriptsDao();
        final VersaoDao versaoDao = new VersaoDao();

        try {
            tc.start();

            scriptsDao.setTransactionControler(tc);
            versaoDao.setTransactionControler(tc);
            /**
             * Recupera lista com todos os scripts e as ordena em ordem crescente para se realizar uma busca binária
             *
             * @author thyen.chang
             * @since 03/02/2015 - OPERAÇÃO USAIN BOLT
             */
            final ArrayList<ScriptsDTO> listaScripts = new ArrayList<ScriptsDTO>(scriptsDao.listaTodosScripts());
            Collections.sort(listaScripts, new ScriptsDTO());

            if (versoes != null & !versoes.isEmpty()) {
                for (final Element versao : versoes) {
                    // limpa o HashMap de chaves
                    this.setChaves(null);
                    VersaoDTO versaoDTO = versaoDao.buscaVersaoPorNome(versao.getText());
                    if (versaoDTO == null) {
                        versaoDTO = new VersaoDTO();
                        versaoDTO.setNomeVersao(versao.getText());
                        versaoDTO = (VersaoDTO) versaoDao.create(versaoDTO);
                        tc.commit();
                    }

                    diretorio = CITCorporeUtil.CAMINHO_REAL_APP + "scripts_deploy" + separator;
                    final String nomeArquivo = "deploy_versao_" + versaoDTO.getNomeVersao() + "_" + CITCorporeUtil.SGBD_PRINCIPAL + ".sql";

                    file = new File(diretorio + nomeArquivo);
                    Scanner scanner = null;
                    try {
                        scanner = new Scanner(file, "ISO-8859-1");
                        scanner = scanner.useDelimiter("\n");
                    } catch (final FileNotFoundException e) {
                        // VERSAO NÃO ENCONTROU SCRIPT
                        continue;
                    }

                    // TIRA COMENTARIOS
                    String sqlQuery = new String();
                    boolean comentario = false;
                    while (scanner.hasNext()) {
                        String linha = scanner.next();
                        if (comentario && linha.contains("*/")) {
                            linha = linha.substring(linha.indexOf("*/") + 2);
                            comentario = false;
                        }
                        if (linha.contains("/*")) {
                            final String str = linha;
                            linha = str.substring(0, str.indexOf("/*"));
                            if (linha.contains("*/")) {
                                linha += str.substring(str.indexOf("*/") + 2);
                            } else {
                                comentario = true;
                                sqlQuery += " " + linha + "\n";
                            }
                        }
                        if (!comentario && !linha.startsWith("--") && !linha.startsWith("//")) {
                            sqlQuery += " " + linha + "\n";
                        }
                    }
                    scanner.close();

                    // DIVIDE O SCRIPT USANDO ";" SEGUIDO DE QUEBRA DE LINHA
                    scanner = new Scanner(sqlQuery).useDelimiter(";(\r)?\n");
                    while (scanner.hasNext()) {
                        String sql = scanner.next();
                        sql = sql.replaceAll("(\r\n|\n\r|\r|\n)", " ");
                        sql = sql.trim();
                        if (!sql.isEmpty()) {
                            final String nomeScript = nomeArquivo + "#" + sql.hashCode();
                            ScriptsDTO scriptsDTO = new ScriptsDTO();
                            scriptsDTO.setNome(nomeScript);
                            /**
                             * Busca o script via busca binária.
                             * Se não o encontrar, o índice retornado é negativo
                             *
                             * @author thyen.chang
                             * @since 03/02/2015 - OPERAÇÃO USAIN BOLT
                             */
                            final int indexScript = Collections.binarySearch(listaScripts, scriptsDTO, new ScriptsDTO());
                            if (indexScript < 0) {
                                sql = this.preencheChavesPrimarias(sql);
                                scriptsDTO = new ScriptsDTO();
                                scriptsDTO.setNome(nomeScript);
                                scriptsDTO.setDataInicio(UtilDatas.getDataAtual());
                                scriptsDTO.setSqlQuery(sql);
                                scriptsDTO.setTipo(ScriptsDTO.TIPO_UPDATE);
                                scriptsDTO.setHistorico("INSTRUÇÃO EXECUTADA PELA ROTINA DO SISTEMA AO INICIALIZAR A APLICAÇÃO. DATA / HORA: " + UtilDatas.getDataHoraAtual());
                                scriptsDTO.setIdVersao(versaoDTO.getIdVersao());
                                try {
                                    LOGGER.info("Versão -> " + versaoDTO.getNomeVersao() + " SQL -> " + scriptsDTO.getSqlQuery());
                                    final int quantidadeRegistrosAfetados = tc.getConnection().createStatement().executeUpdate(sql);
                                    scriptsDTO.setDescricao("SUCESSO: " + quantidadeRegistrosAfetados + " REGISTROS AFETADOS!");
                                } catch (final SQLException e) {
                                    scriptsDTO.setDescricao("ERRO: " + e.getLocalizedMessage());
                                } finally {
                                    try {
                                        tc.commit();
                                    } catch (final Exception e) {
                                        LOGGER.warn(e.getMessage(), e);
                                    }

                                    LOGGER.info("resultado: " + scriptsDTO.getDescricao());

                                    if (scriptsDTO.getIdScript() == null) {
                                        try {
                                            scriptsDao.create(scriptsDTO);
                                        } catch (final Exception e) {
                                            LOGGER.warn(e.getMessage(), e);
                                        }
                                    } else {
                                        try {
                                            scriptsDao.update(scriptsDTO);
                                        } catch (final Exception e) {
                                            LOGGER.warn(e.getMessage(), e);
                                        }
                                    }
                                    tc.commit();
                                }
                            }
                        }
                    }
                    scanner.close();
                }
            }
        } catch (final Exception e) {
            LOGGER.warn(e.getMessage(), e);
            erro = e.getLocalizedMessage();
        } finally {
            tc.closeQuietly();
        }
        return erro;
    }

    @Override
    public List<String[]> executarScriptConsulta(final ScriptsDTO script) throws Exception {
        List<String[]> retorno = null;

        final TransactionControler tc = new TransactionControlerImpl(Constantes.getValue("DATABASE_ALIAS"));

        tc.start();

        final Connection connection = tc.getConnection();

        try (Statement statement = connection.createStatement();) {
            statement.setMaxRows(1024);
            retorno = new ArrayList<String[]>();
            String sql = script.getSqlQuery();
            sql = sql.trim();
            if (sql.endsWith(";")) {
                sql = sql.substring(0, sql.length() - 1);
            }

            try (ResultSet resultSet = statement.executeQuery(sql);) {
                final ResultSetMetaData rsmd = resultSet.getMetaData();
                final int quantidadeColunas = rsmd.getColumnCount();
                boolean primeiro = true;
                while (resultSet.next()) {
                    if (primeiro) {
                        primeiro = false;
                        final String colunas[] = new String[quantidadeColunas];
                        for (int i = 1; i <= quantidadeColunas; i++) {
                            colunas[i - 1] = rsmd.getColumnName(i);
                        }
                        retorno.add(colunas);
                    }
                    final String dados[] = new String[quantidadeColunas];
                    for (int i = 1; i <= quantidadeColunas; i++) {
                        dados[i - 1] = resultSet.getString(i);
                    }
                    retorno.add(dados);
                }
            }

            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
            throw e;
        }
        return retorno;
    }

    @SuppressWarnings("resource")
    @Override
    public String executarScriptUpdate(final ScriptsDTO script) throws Exception {
        final String retorno = null;

        final TransactionControler tc = new TransactionControlerImpl(Constantes.getValue("DATABASE_ALIAS"));

        tc.start();

        final Connection connection = tc.getConnection();

        try (Statement statement = connection.createStatement();) {
            final Scanner scanner = new Scanner(script.getSqlQuery()).useDelimiter(";(\r)?\n");
            while (scanner.hasNext()) {
                String sql = scanner.next();
                sql = sql.replaceAll("(\r\n|\n\r|\r|\n)", " ");
                sql = sql.trim();
                if (sql.endsWith(";")) {
                    sql = sql.substring(0, sql.length() - 1);
                }
                if (!sql.isEmpty()) {
                    statement.executeUpdate(sql);
                }
            }
            scanner.close();

            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
        return retorno;
    }

    private Map<String, String> getChaves() {
        if (chaves == null) {
            chaves = new HashMap<>();
        }
        return chaves;
    }

    @Override
    public boolean haScriptDeVersaoComErro() throws Exception {
        return this.getDao().haScriptDeVersaoComErro();
    }

    @Override
    public void marcaErrosScriptsComoCorrigidos() throws Exception {
        final ScriptsDao scriptsDao = new ScriptsDao();

        final TransactionControler tc = new TransactionControlerImpl(Constantes.getValue("DATABASE_ALIAS"));

        scriptsDao.setTransactionControler(tc);

        try {
            tc.start();

            scriptsDao.marcaErrosScriptsComoCorrigidos();

            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }

    }

    public String obterColunaCorrespondenteAChave(final String sql, final String chave) {
        String coluna = "";
        // pega o conteudo dos parenteses
        final Pattern pattern = Pattern.compile("\\(([^\\)]+)\\)");
        final Matcher matcher = pattern.matcher(sql);
        // se existem dois grupos de parenteses. (colunas e valores)
        if (matcher.find()) {
            String colunasString = matcher.group();
            if (matcher.find()) {
                String valoresString = matcher.group();

                // remove os parenteses
                colunasString = colunasString.replaceAll("\\(|\\)", "");
                valoresString = valoresString.replaceAll("\\(|\\)", "");
                final List<String> colunas = Arrays.asList(colunasString.split(","));
                final List<String> valores = Arrays.asList(valoresString.split(","));

                int indice = 0;
                for (final String valor : valores) {
                    if (chave.trim().equalsIgnoreCase(valor.trim())) {
                        break;
                    }
                    indice++;
                }
                coluna = colunas.get(indice);
                coluna = coluna.trim();
            }
        }
        return coluna;
    }

    /**
     * Retorna o nome da tabela de acordo com o sql(insert) informado
     *
     * @author Murilo Gabriel Rodrigues
     */
    private String obterNomeDaTabela(final String sql) {
        final List<String> palavras = Arrays.asList(sql.split(" |\n|\r"));
        boolean insert = false;
        boolean into = false;
        for (String palavra : palavras) {
            if (insert & into) {
                if (palavra.contains(".")) {
                    palavra = palavra.substring(palavra.lastIndexOf(".") + 1);
                }
                return palavra.trim();
            }
            if (palavra.trim().equalsIgnoreCase("insert")) {
                insert = true;
            }
            if (insert && palavra.trim().equalsIgnoreCase("into")) {
                into = true;
            }
        }
        return null;
    }

    /**
     * Faz a leitura da SQL substituindo as chaves pelos próximos valores disponíveis no banco de dados. Essas chaves
     * tem o padrão $id_[texto] Ex.: $id_idusuario01, $id_idusuario20,
     * $id_texto_qualquer...
     *
     * @author Murilo Gabriel Rodrigues
     */
    private String preencheChavesPrimarias(String sql) throws Exception {
        final Pattern pattern = Pattern.compile("(\\$id_\\w+)\\b");
        final Matcher matcher = pattern.matcher(sql);

        while (matcher.find()) {
            final String chave = matcher.group();
            if (chave != null && !chave.trim().isEmpty()) {
                String indice = this.getChaves().get(chave);
                // se a chave ainda não foi obtida
                if (indice == null || indice.trim().isEmpty()) {
                    final String coluna = this.obterColunaCorrespondenteAChave(sql, chave);
                    final String tabela = this.obterNomeDaTabela(sql);
                    if (tabela != null && !tabela.isEmpty() && coluna != null && !coluna.isEmpty()) {
                        final Integer nextKey = PersistenceEngine.getNextKey(this.getDao().getAliasDB(), tabela, coluna);
                        if (nextKey != null && nextKey.intValue() != 0) {
                            indice = nextKey.toString();
                            this.getChaves().put(chave, indice);
                        }
                    }
                }
                if (indice != null && !indice.trim().isEmpty()) {
                    sql = sql.replaceAll("\\" + chave, indice);
                }
            }
        }
        return sql;
    }

    private void setChaves(final Map<String, String> chaves) {
        this.chaves = chaves;
    }

    @Override
    public boolean temScriptsAtivos(final ScriptsDTO script) throws Exception {
        return this.getDao().temScriptsAtivos(script);
    }

    @Override
    public String verificaPermissoesUsuarioBanco(final HttpServletRequest request) throws ServiceException {
        final String resultadoTesteCriacaoTabela = this.getDao().testaPermissaoCriacaoTabela();
        final String resultadoTesteInsercaoRegistroTabela = this.getDao().testaPermissaoInsercaoRegistroTabela();
        final String resultadoTesteConsultaTabela = this.getDao().testaPermissaoConsultaTabela();
        final String resultadoTesteExclusaoRegistroTabela = this.getDao().testaPermissaoExclusaoRegistroTabela();
        final String resultadoTesteCriacaoColuna = this.getDao().testaPermissaoCriacaoColuna();
        final String resultadoTesteAlteracaoColuna = this.getDao().testaPermissaoAlteracaoColuna();
        final String resultadoTesteExclusaoColuna = this.getDao().testaPermissaoExclusaoColuna();
        final String resultadoTesteExclusaoTabela = this.getDao().testaPermissaoExclusaoTabela();

        final List<String> acoesSemPermissao = new ArrayList<String>();

        if (this.resultadoNegativoPermissoes(resultadoTesteCriacaoTabela)) {
            acoesSemPermissao.add(UtilI18N.internacionaliza(request, "citcorpore.comum.criacaoTabelas"));
        }
        if (this.resultadoNegativoPermissoes(resultadoTesteInsercaoRegistroTabela)) {
            acoesSemPermissao.add(UtilI18N.internacionaliza(request, "citcorpore.comum.incersaoRegistrosTabelas"));
        }
        if (this.resultadoNegativoPermissoes(resultadoTesteConsultaTabela)) {
            acoesSemPermissao.add(UtilI18N.internacionaliza(request, "citcorpore.comum.consultaTabelas"));
        }
        if (this.resultadoNegativoPermissoes(resultadoTesteExclusaoRegistroTabela)) {
            acoesSemPermissao.add(UtilI18N.internacionaliza(request, "citcorpore.comum.exclusaoRegistrosTabelas"));
        }
        if (this.resultadoNegativoPermissoes(resultadoTesteCriacaoColuna)) {
            acoesSemPermissao.add(UtilI18N.internacionaliza(request, "citcorpore.comum.criacaoColunasTabelas"));
        }
        if (this.resultadoNegativoPermissoes(resultadoTesteAlteracaoColuna)) {
            acoesSemPermissao.add(UtilI18N.internacionaliza(request, "citcorpore.comum.alteracaoColunasTabelas"));
        }
        if (this.resultadoNegativoPermissoes(resultadoTesteExclusaoColuna)) {
            acoesSemPermissao.add(UtilI18N.internacionaliza(request, "citcorpore.comum.exclusaoColunasTabelas"));
        }
        if (this.resultadoNegativoPermissoes(resultadoTesteExclusaoTabela)) {
            acoesSemPermissao.add(UtilI18N.internacionaliza(request, "citcorpore.comum.ExclusaoTabelas"));
        }

        final StringBuilder retorno = new StringBuilder();

        if (acoesSemPermissao.isEmpty()) {
            return "sucesso";
        } else {
            retorno.append(UtilI18N.internacionaliza(request, "citcorpore.comum.encontradaFaltaPermissao") + "<br>");
            for (final String acao : acoesSemPermissao) {
                retorno.append("&nbsp;&nbsp;&nbsp;<b>").append(acao).append("</b>,<br>");
            }
            return retorno.substring(0, retorno.lastIndexOf(",")) + ".";
        }
    }

    private boolean resultadoNegativoPermissoes(final String resultadoTestePermissoes) {
        return resultadoTestePermissoes != null
                && !resultadoTestePermissoes.trim().equalsIgnoreCase("sucesso")
                && (resultadoTestePermissoes.toLowerCase().contains("command denied") || resultadoTestePermissoes.toLowerCase().contains("permission denied")
                        || resultadoTestePermissoes.toLowerCase().contains("permissão negada") || resultadoTestePermissoes.toLowerCase().contains("must be owner")
                        || resultadoTestePermissoes.toLowerCase().contains("deve ser o dono") || resultadoTestePermissoes.toLowerCase().contains("insufficient privileges") || resultadoTestePermissoes
                        .toLowerCase().contains("no privileges"));
    }

    @Override
    public List<ScriptsDTO> pesquisaScriptsComFaltaPermissao() throws Exception {
        final List<ScriptsDTO> scriptsNaoExecutadosManualmente = new ArrayList<>();

        final List<ScriptsDTO> scriptsComFaltaPermissao = this.getDao().pesquisaScriptsComFaltaPermissao();
        if (scriptsComFaltaPermissao != null && !scriptsComFaltaPermissao.isEmpty()) {
            for (final ScriptsDTO script : scriptsComFaltaPermissao) {
                if (!this.scriptFoiExecutado(script)) {
                    scriptsNaoExecutadosManualmente.add(script);
                }
            }
        }

        return scriptsNaoExecutadosManualmente;
    }

    private boolean scriptFoiExecutado(final ScriptsDTO scriptsDTO) throws Exception {
        boolean scriptFoiExecutado = false;

        final String sqlLowerCase = scriptsDTO.getSqlQuery().toLowerCase();
        Matcher matcher = null;
        switch (this.obtemTipoSQL(scriptsDTO)) {
        case ScriptsDTO.TIPO_CRIAR_TABELA:
            matcher = Pattern.compile("create table").matcher(sqlLowerCase);
            if (matcher.find()) {
                final String nomeTabela = sqlLowerCase.substring(matcher.end()).trim().split(" ", 2)[0];
                scriptFoiExecutado = this.getDao().verificaExistenciaTabela(nomeTabela);
            }
            break;
        case ScriptsDTO.TIPO_INSERIR_REGISTRO:
            // verificacao para esse caso atualmente não é viável
            scriptFoiExecutado = true;
            break;
        case ScriptsDTO.TIPO_DELETAR_REGISTRO:
            // verificacao para esse caso atualmente não é viável
            scriptFoiExecutado = true;
            break;
        case ScriptsDTO.TIPO_ADICIONAR_COLUNA:
            String nomeTabela = null;
            String nomeColuna = null;
            matcher = Pattern.compile("alter table").matcher(sqlLowerCase);
            if (matcher.find()) {
                nomeTabela = sqlLowerCase.substring(matcher.end()).trim().split(" ", 2)[0];
            }
            matcher = Pattern.compile("add column").matcher(sqlLowerCase);
            if (matcher.find()) {
                nomeColuna = sqlLowerCase.substring(matcher.end()).trim().split(" ", 2)[0];
            }
            if (nomeTabela != null && nomeColuna != null) {
                scriptFoiExecutado = this.getDao().verificaExistenciaColuna(nomeTabela, nomeColuna);
            }
            break;
        case ScriptsDTO.TIPO_ADICIONAR_CONSTRAINT:
            // verificacao para esse caso atualmente não é viável
            scriptFoiExecutado = true;
            break;
        case ScriptsDTO.TIPO_ALTERAR_COLUNA:
            // verificacao para esse caso atualmente não é viável
            scriptFoiExecutado = true;
            break;
        case ScriptsDTO.TIPO_DELETAR_COLUNA:
            // verificacao para esse caso atualmente não é viável
            scriptFoiExecutado = true;
            break;
        case ScriptsDTO.TIPO_DELETAR_TABELA:
            // verificacao para esse caso atualmente não é viável
            scriptFoiExecutado = true;
            break;
        }

        return scriptFoiExecutado;
    }

    private int obtemTipoSQL(final ScriptsDTO scriptsDTO) {
        int tipo = 0;
        if (scriptsDTO != null && scriptsDTO.getSqlQuery() != null) {
            if (scriptsDTO.getSqlQuery().trim().toUpperCase().startsWith("CREATE TABLE")) {
                tipo = ScriptsDTO.TIPO_CRIAR_TABELA;
            } else if (scriptsDTO.getSqlQuery().trim().toUpperCase().startsWith("INSERT INTO")) {
                tipo = ScriptsDTO.TIPO_INSERIR_REGISTRO;
            } else if (scriptsDTO.getSqlQuery().trim().toUpperCase().startsWith("DELETE FROM")) {
                tipo = ScriptsDTO.TIPO_DELETAR_REGISTRO;
            } else if (scriptsDTO.getSqlQuery().trim().toUpperCase().startsWith("ALTER TABLE")) {
                if (scriptsDTO.getSqlQuery().trim().toUpperCase().contains(" ADD CONSTRAINT ")) {
                    tipo = ScriptsDTO.TIPO_ADICIONAR_CONSTRAINT;
                } else if (scriptsDTO.getSqlQuery().trim().toUpperCase().contains(" ADD ")) {
                    tipo = ScriptsDTO.TIPO_ADICIONAR_COLUNA;
                }
            } else if (scriptsDTO.getSqlQuery().trim().toUpperCase().startsWith("ALTER TABLE")
                    && (scriptsDTO.getSqlQuery().trim().toUpperCase().contains("CHANGE COLUMN") || scriptsDTO.getSqlQuery().trim().toUpperCase().contains("RENAME COLUMN"))) {
                tipo = ScriptsDTO.TIPO_ALTERAR_COLUNA;
            } else if (scriptsDTO.getSqlQuery().trim().toUpperCase().startsWith("ALTER TABLE") && scriptsDTO.getSqlQuery().trim().toUpperCase().contains("DROP COLUMN")) {
                tipo = ScriptsDTO.TIPO_DELETAR_COLUNA;
            } else if (scriptsDTO.getSqlQuery().trim().toUpperCase().startsWith("DROP TABLE")) {
                tipo = ScriptsDTO.TIPO_DELETAR_TABELA;
            }
        }

        return tipo;
    }

}
