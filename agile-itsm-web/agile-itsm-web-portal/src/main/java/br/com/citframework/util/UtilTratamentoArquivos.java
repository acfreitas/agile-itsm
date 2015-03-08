package br.com.citframework.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class UtilTratamentoArquivos {

    private static final Logger LOGGER = Logger.getLogger(UtilTratamentoArquivos.class);

    /**
     * Obtem o nome de um arquivo
     *
     * @param path
     * @param separador
     * @return
     */
    public static final String getFileName(final String path, final String separador) {
        final StringTokenizer st = new StringTokenizer(path, separador);
        String result = "";
        while (st.hasMoreElements()) {
            result = st.nextElement().toString();
        }
        return result;
    }

    /**
     * Pega o nome do arquivo
     *
     * @return
     */
    public static final String getFileName(final String fullPathFile) {
        final int tam = fullPathFile.length() - 1;
        final StringBuilder nomeFile = new StringBuilder();
        for (int i = tam; i >= 0; i--) {
            if (fullPathFile.charAt(i) == '\\' || fullPathFile.charAt(i) == '/') {
                break;
            } else {
                nomeFile.append(fullPathFile.charAt(i));
            }
        }
        return nomeFile.toString();
    }

    /**
     * Faz a leitura de um arqivo texto e retorna a String que contem no arquivo.
     *
     * @param arquivo
     * @return
     */
    public static String getStringTextFromFileTxt(final String arquivo) {
        final StringBuilder retorno = new StringBuilder();

        try (final FileInputStream arq = new FileInputStream(arquivo); final BufferedReader br = new BufferedReader(new InputStreamReader(arq, "ISO-8859-1"))) {
            while (br.ready()) {
                retorno.append(br.readLine()).append("\n");
            }
        } catch (final IOException e) {
            LOGGER.warn(e.getMessage(), e);
        }

        return retorno.toString();
    }

    public static String getStringTextFromFileTxtTemplate(final String arquivo) {
        final StringBuilder retorno = new StringBuilder();

        try (final FileInputStream arq = new FileInputStream(arquivo)) {
            BufferedReader br = null;
            if (System.getProperty("os.name").contains("Windows")) {
                br = new BufferedReader(new InputStreamReader(arq, "ISO-8859-1"));
            } else {
                br = new BufferedReader(new InputStreamReader(arq, "UTF-8"));
            }
            while (br.ready()) {
                retorno.append(br.readLine()).append("\n");
            }
            br.close();
            arq.close();
        } catch (final IOException e) {
            LOGGER.warn(e.getMessage(), e);
        }

        return retorno.toString();
    }

    public static String getStringTextFromFileTxt(final String arquivo, final String encode) {
        final StringBuilder retorno = new StringBuilder();

        try (final FileInputStream arq = new FileInputStream(arquivo)) {
            BufferedReader br = null;
            if (StringUtils.isBlank(encode)) {
                br = new BufferedReader(new InputStreamReader(arq));
            } else {
                br = new BufferedReader(new InputStreamReader(arq, encode));
            }
            while (br.ready()) {
                retorno.append(br.readLine()).append("\n");
            }
            br.close();
        } catch (final IOException e) {
            LOGGER.warn(e.getMessage(), e);
        }

        return retorno.toString();
    }

    /**
     * Faz a leitura de um arqivo texto e retorna a String que contem no arquivo, mas sem quebras de linha.
     *
     * @param arquivo
     * @return
     */
    public static String getStringTextFromFileTxtSemQuebra(final String arquivo) {
        final StringBuilder retorno = new StringBuilder();

        try (final FileInputStream arq = new FileInputStream(arquivo); final BufferedReader br = new BufferedReader(new InputStreamReader(arq))) {
            while (br.ready()) {
                retorno.append(br.readLine()).append(" ");
            }
        } catch (final IOException e) {
            LOGGER.warn(e.getMessage(), e);
        }

        return retorno.toString();
    }

    /**
     * Faz a leitura de um arqivo texto e retorna a String que contem no arquivo, mas tudo em uma mesma linha.
     *
     * @param arquivo
     * @return
     */
    public static String getStringTextFromFileTxtInline(final String arquivo) {
        final StringBuilder retorno = new StringBuilder();

        try (final FileInputStream arq = new FileInputStream(arquivo); final BufferedReader br = new BufferedReader(new InputStreamReader(arq))) {
            while (br.ready()) {
                retorno.append(br.readLine());
            }
        } catch (final IOException e) {
            LOGGER.warn(e.getMessage(), e);
        }

        return retorno.toString();
    }

    public static String fromInputStreamToString(final InputStream arq) throws Exception {
        String resposta = "";

        try (final BufferedReader br = new BufferedReader(new InputStreamReader(arq))) {
            while (br.ready()) {
                resposta = br.readLine();
            }
        }

        return resposta;
    }

    public static List<String> fromInputStreamToList(final InputStream arq) throws Exception {
        final List<String> lista = new ArrayList<>();

        try (final BufferedReader br = new BufferedReader(new InputStreamReader(arq))) {
            while (br.ready()) {
                lista.add(br.readLine());
            }
        }

        return lista;
    }

    /**
     * Faz a leitura de um arquivo Texto e gera um List com o conteudo.
     *
     * @param arquivo
     * @return
     * @throws Exception
     */
    public static List<String> lerFileTXTGenerateList(final String arquivo) throws Exception {
        final List<String> lista = new ArrayList<>();

        try (final FileInputStream arq = new FileInputStream(arquivo); final BufferedReader br = new BufferedReader(new InputStreamReader(arq))) {
            while (br.ready()) {
                lista.add(br.readLine());
            }
        }

        return lista;
    }

    /**
     * Faz a leitura de um arquivo Texto e gera um List com o conteudo.
     *
     * @param arquivo
     * @return
     * @throws Exception
     */
    public static StringBuilder lerFileTXTGenerateStringBuilder(final String arquivo) throws Exception {
        final StringBuilder strBuffer = new StringBuilder();

        try (final FileInputStream arq = new FileInputStream(arquivo); final BufferedReader br = new BufferedReader(new InputStreamReader(arq))) {
            if (br != null) {
                while (br.ready()) {
                    strBuffer.append(br.readLine());
                }
            }
        } catch (final Exception e) {
            LOGGER.warn(e.getMessage(), e);
        }

        return strBuffer;
    }

    /**
     * Gera um lista de beans atraves de um arquivo texto.
     *
     * @param arquivo
     * @param separador
     * @param campos
     *            (Lista de campos)
     * @param classe
     *            (Classe a converter)
     * @return
     * @throws Exception
     */
    public static List carregaBeansFromFileTxt(final String arquivo, final String separador, final List campos, final Class<?> classe) throws Exception {
        final List<String> lstArq = lerFileTXTGenerateList(arquivo);
        final List<Object> result = new ArrayList<>();

        final Iterator<String> itArq = lstArq.iterator();
        while (itArq.hasNext()) {
            final String linha = itArq.next();
            int i = 0;
            final StringTokenizer stok = new StringTokenizer(linha, separador);
            final Object obj = classe.newInstance();
            while (stok.hasMoreTokens()) {
                final String valor = stok.nextToken();
                final String campo = campos.get(i).toString();
                Reflexao.setPropertyValueAsString(obj, campo, valor);
                i++;
            }
            result.add(obj);
        }
        return result;

    }

    /**
     * gera um arquivo TXT atraves de uma lista.
     *
     * @param arquivo
     * @param lista
     * @throws IOException
     */
    public static final void geraFileTXT(final String arquivo, final List lista, final FileOutputStream fos) throws IOException {
        final PrintStream out = new PrintStream(fos);
        final Iterator it = lista.iterator();
        while (it.hasNext()) {
            out.println(it.next().toString());
        }
    }

    public static final void geraFileTXT(final String arquivo, final List lista, final PrintStream out) throws IOException {
        final Iterator it = lista.iterator();
        while (it.hasNext()) {
            out.println(it.next().toString());
        }
    }

    public static final void geraFileTXT(final String arquivo, final List lista) throws IOException {
        try (final FileOutputStream fos = new FileOutputStream(arquivo)) {
            geraFileTXT(arquivo, lista, fos);
        } catch (final Exception e) {
            LOGGER.warn(e.getMessage(), e);
        }
    }

    public static final void geraFileTXT(final String arquivo, final List lista, final boolean append) throws IOException {
        final File f = new File(arquivo);
        f.createNewFile();
        final FileOutputStream fos = new FileOutputStream(arquivo, append); // Faz append de acordo com o parametro passado.
        geraFileTXT(arquivo, lista, fos);
        try {
            fos.close();
        } catch (final Exception e) {
            throw new IOException("Erro ao fechar arquivo " + arquivo);
        }
    }

    /**
     * Gera um arquivo atraves de uma string.
     *
     * @param arquivo
     * @param texto
     * @throws IOException
     */
    public static final void geraFileTxtFromString(final String arquivo, final String texto) throws IOException {
        try (final FileOutputStream fos = new FileOutputStream(arquivo); final PrintStream out = new PrintStream(fos)) {
            out.print(texto);
        } catch (final Exception e) {
            LOGGER.warn(e.getMessage(), e);
        }
    }

    /**
     * Copia um arquivo.
     *
     * @param inFile
     * @param outFile
     * @return
     */
    public static boolean copyFile(final String inFile, final String outFile) {
        boolean success = true;
        try (final InputStream is = new FileInputStream(inFile); final OutputStream os = new FileOutputStream(outFile)) {
            final byte[] buffer = new byte[is.available()];
            is.read(buffer);
            os.write(buffer);
        } catch (final IOException e) {
            success = false;
        }
        return success;
    }

    /*
     * Faz a leitura de um arquivo e retorna o Array de Butes
     */
    public static byte[] getBytesFromFile(final File file) throws Exception {
        long length = 0;
        try (InputStream is = new FileInputStream(file)) {
            length = file.length();

            // You cannot create an array using a long type.
            // It needs to be an int type.
            // Before converting to an int type, check
            // to ensure that file is not larger than Integer.MAX_VALUE.
            if (length > Integer.MAX_VALUE) {
                throw new Exception("Arquivo muito grande >>> " + file.getName());
            }

            final byte[] bytes = new byte[(int) length];
            // Read in the bytes
            int offset = 0;
            int numRead = 0;
            while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }
            // Ensure all the bytes have been read in
            if (offset < bytes.length) {
                throw new Exception("Não foi possível ler o arquivo completamente >>> " + file.getName());
            }
            // Close the input stream and return bytes
            return bytes;
        } catch (final IOException e) {
            throw e;
        }
    }

    /**
     * @author euler.ramos
     *
     *         Faz a leitura de um arquivo Texto usando o CharSet SO-8859-1 e gera uma String com o conteudo
     * @param arquivo
     * @return
     * @throws Exception
     */
    public static StringBuilder lerArquivoCharUTF8(final String arquivo) throws Exception {
        final StringBuilder textoArquivo = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(arquivo), Charset.forName("UTF-8")))) {
            if (br != null) {
                while (br.ready()) {
                    textoArquivo.append(br.readLine());
                }
            }
        } catch (final Exception e) {
            LOGGER.warn(e.getMessage(), e);
        }

        return textoArquivo;
    }

    /**
     * @author euler.ramos
     *
     *         Grava arquivo usando CharSet ISO-8859-1
     *
     * @param arquivo
     * @param lista
     * @throws IOException
     */
    public static final void gravaArquivoCharSetISO(final String arquivo, final List lista) throws IOException {
        try (final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(arquivo), "ISO8859_1")), true)) {
            final Iterator it = lista.iterator();
            while (it.hasNext()) {
                out.println(it.next().toString());
            }
            out.flush();
        } catch (final Exception e) {
            LOGGER.warn(e.getMessage(), e);
        }
    }

    /**
     * @author euler.ramos
     *
     *         Gera um arquivo CharSet ISO-8859-1 atraves de uma string.
     *
     * @param arquivo
     * @param texto
     * @throws IOException
     */
    public static final void gravaArquivoCharSetISO(final String arquivo, final String texto) throws IOException {
        try (final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(arquivo), "ISO8859_1")), true)) {
            out.print(texto);
            out.flush();
        } catch (final Exception e) {
            LOGGER.warn(e.getMessage(), e);
        }
    }

}
