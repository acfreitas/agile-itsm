package br.com.citframework.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import org.apache.commons.lang.StringUtils;

/**
 * Utilit�rio para persist�ncia de arquivos no file system
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 10/02/2015
 *
 */
public final class FileUtil {

    private FileUtil() {}

    public static final Charset DEFAULT_CHARSET = Charset.defaultCharset();

    private static final String PATH_MUST_NOT_BE_NULL_OR_EMPTY = "Path must not be null or empty.";

    /**
     * Cria, se inexistente, a barra no fim de uma pasta no filesystem
     *
     * @param path
     *            pasta a ser verificada a exist�ncia de barra no fim
     * @return pasta com o nome contendo a barra no fim
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 10/02/2015
     */
    public static String garantirBarraFim(String path) {
        Assert.notNullAndNotEmpty(path, PATH_MUST_NOT_BE_NULL_OR_EMPTY);

        if (!path.matches(".*[\\\\/]")) {
            path += path.indexOf('\\') >= 0 ? "\\" : "/";
        }
        return path;
    }

    /**
     * Cria, se inexistente, a barra no in�cio de uma pasta no filesystem
     *
     * @param path
     *            pasta a ser verificada a exist�ncia de barra no in�cio
     * @return pasta com o nome contendo a barra no in�cio
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 10/02/2015
     */
    public static String garantirBarraInicio(String path) {
        Assert.notNullAndNotEmpty(path, PATH_MUST_NOT_BE_NULL_OR_EMPTY);

        if (!(path.startsWith("/") || path.startsWith("\\") || path.indexOf(":") > 0)) {
            path = (path.indexOf("\\") > 0 ? "\\" : "/") + path;
        }
        return path;
    }

    /**
     * Garante a exist�ncia de um arquivo/diret�rio no filesystem
     *
     * @param path
     *            pasta a ser garantida a exist�ncia
     * @param isToCreate
     *            se o arquivo/diret�erio deve ou n�o ser criado
     * @return {@code true} caso a pasta j� exista ou foi criada, {@code false}, caso contr�rio
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 10/02/2015
     */
    public static boolean garantirExistencia(final String path, final boolean isToCreate) {
        boolean exists = false;
        final File directory = new File(path);
        if (!directory.exists()) {
            if (isToCreate) {
                exists = directory.mkdirs();
            }
        }
        return exists;
    }

    /**
     * Grava um arquivo e seu respectivo conte�do, com encode padr�o, no file system
     *
     * @param content
     *            conte�do do arquivo a ser salvo
     * @param fileName
     *            nome do arquivo a ser criado
     * @param path
     *            diret�rio em que o arquivo ser� criado
     * @param appendToEnd
     *            se o conte�do deve ser escrito no final do arquivo. Se {@code false}, escreve no in�cio do arquivo
     * @throws IOException
     *             caso algum problema de I/O aconte�a
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 10/02/2015
     */
    public static void salvarArquivo(final String content, final String fileName, final String path, final boolean appendToEnd) throws IOException {
        salvarArquivo(content, fileName, path, appendToEnd, DEFAULT_CHARSET);
    }

    /**
     * Grava um arquivo e seu respectivo conte�do, com encode especificado, no file system
     *
     * @param content
     *            conte�do do arquivo a ser salvo
     * @param fileName
     *            nome do arquivo a ser criado
     * @param path
     *            diret�rio em que o arquivo ser� criado
     * @param appendToEnd
     *            se o conte�do deve ser escrito no final do arquivo. Se {@code false}, escreve no in�cio do arquivo
     * @param encoding
     *            charset no qual o conte�do deve ser salvo
     * @throws IOException
     *             caso algum problema de I/O aconte�a
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 11/02/2015
     * @see Charset
     * @see #salvarArquivo(String, String, String, boolean, Charset)
     */
    public static void salvarArquivo(final String content, final String fileName, final String path, final boolean appendToEnd, final String encoding) throws IOException {
        final Charset charSet = StringUtils.isBlank(encoding) ? DEFAULT_CHARSET : Charset.forName(encoding);
        salvarArquivo(content, fileName, path, appendToEnd, charSet);
    }

    /**
     * Grava um arquivo e seu respectivo conte�do, com encode especificado, no file system
     *
     * @param content
     *            conte�do do arquivo a ser salvo
     * @param fileName
     *            nome do arquivo a ser criado
     * @param path
     *            diret�rio em que o arquivo ser� criado
     * @param appendToEnd
     *            se o conte�do deve ser escrito no final do arquivo. Se {@code false}, escreve no in�cio do arquivo
     * @param encoding
     *            charset no qual o conte�do deve ser salvo
     * @throws IOException
     *             caso algum problema de I/O aconte�a
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 11/02/2015
     * @see Charset
     * @see #salvarArquivo(String, String, String, boolean, String)
     */
    public static void salvarArquivo(final String content, final String fileName, String path, final boolean appendToEnd, final Charset encoding) throws IOException {
        path = garantirBarraFim(path);
        path = garantirBarraInicio(path);
        garantirExistencia(path, true);
        try (final FileOutputStream fos = new FileOutputStream(path + fileName, appendToEnd);
                final OutputStreamWriter osw = new OutputStreamWriter(fos, encoding);
                final Writer out = new BufferedWriter(osw)) {
            out.write(content);
            out.flush();
        } catch (final IOException e) {
            throw new IOException("Problema ao salvar o arquivo: " + e.getMessage());
        }
    }

}
