package br.com.centralit.lucene;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopFieldCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.NumericUtils;
import org.apache.lucene.util.Version;

import br.com.centralit.citcorpore.bean.AnexoBaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.PalavraGemeaDTO;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.excecao.LogicException;

/**
 * Trata todas as pesquisas por similaridade usando o Apache LUCENE como mecanismo de busca. Nós precisamos do Lucene porque ele ao contrário do banco de dados é capaz de retornar um resultado aproximado da pesquisa, mesmo quando o que foi pesquisado não existe no cadastro e ainda os classifica por similaridade e por outros campos que quisermos ordenar o resultado; existem outros fatores, mas dentre eles o mais importante, também, é ele possuir uma maior velocidade no retorno das pesquisas.
 * versão da classe 1.0
 *
 * @author euler.ramos
 */
public class Lucene {
    private String dirAnexos;
    private String dirBaseConhecimento;
    private String dirGemeas;

    public Lucene() {
        this.dirAnexos = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.LUCENE_DIR_ANEXOBASECONHECIMENTO, "/usr/local/arquivosLucene/AnexoBaseConhecimento");
        this.dirBaseConhecimento = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.LUCENE_DIR_BASECONHECIMENTO, "/usr/local/arquivosLucene/BaseConhecimento");
        this.dirGemeas = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.LUCENE_DIR_PALAVRAGEMEA, "/usr/local/arquivosLucene/PalavraGemea");
    }

    public String getDirAnexos() {
        return dirAnexos;
    }

    public String getDirBaseConhecimento() {
        return dirBaseConhecimento;
    }

    public String getDirGemeas() {
        return dirGemeas;
    }

    private boolean indexarDocGemeo(PalavraGemeaDTO palGemeaDTO) throws IOException {
        this.excluirPalavraGemea(palGemeaDTO);

        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_35);
        Directory indexDir = FSDirectory.open(new File(dirGemeas));

        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_35,
                analyzer);
        config.setOpenMode(OpenMode.CREATE_OR_APPEND);
        IndexWriter indexWriter = new IndexWriter(indexDir, config);

        Document doc = new Document();

        doc.add(new Field("palavra", palGemeaDTO.getPalavra(), Store.YES, Index.ANALYZED));
        doc.add(new Field("correspondente", palGemeaDTO.getPalavraCorrespondente(), Store.YES, Index.ANALYZED));

        NumericField id = new NumericField("id", Store.YES, true);
        id.setLongValue(palGemeaDTO.getIdPalavraGemea());
        doc.add(id);

        indexWriter.addDocument(doc);

        indexWriter.close();
        indexDir.close();
        return true;
    }

    public boolean indexarListaPalavrasGemeas(Collection<PalavraGemeaDTO> palGemeas) {
        try {
            if (palGemeas != null && palGemeas.size() > 0) {
                for (PalavraGemeaDTO palGemea : palGemeas) {
                    if (palGemea.getIdPalavraGemea() != null
                            && palGemea.getPalavra() != null
                            && palGemea.getPalavraCorrespondente() != null) {
                        indexarDocGemeo(palGemea);
                    }
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean indexarPalavraGemea(PalavraGemeaDTO palavraGemeaDTO) {
        try {
            if (palavraGemeaDTO != null) {
                if (palavraGemeaDTO.getIdPalavraGemea() != null
                        && palavraGemeaDTO.getPalavra() != null
                        && palavraGemeaDTO.getPalavraCorrespondente() != null){
                    indexarDocGemeo(palavraGemeaDTO);
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean indexarDocumentoAnexoBaseConhecimento(AnexoBaseConhecimentoDTO anexoBaseConhecimentoDTO) throws IOException {
        this.excluirAnexoBaseConhecimento(anexoBaseConhecimentoDTO);

        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_35);
        Directory indexDir = FSDirectory.open(new File(dirAnexos));

        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_35, analyzer);
        config.setOpenMode(OpenMode.CREATE_OR_APPEND);
        IndexWriter indexWriter = new IndexWriter(indexDir, config);

        Document doc = new Document();

        if ((anexoBaseConhecimentoDTO.getDescricao()==null)||(anexoBaseConhecimentoDTO.getDescricao().length() <= 0)) {
            doc.add(new Field("descricao", "vazio", Store.YES, Index.ANALYZED));
        } else {
            doc.add(new Field("descricao", anexoBaseConhecimentoDTO.getDescricao(), Store.YES, Index.ANALYZED));
        }

        if ((anexoBaseConhecimentoDTO.getNomeAnexo()==null)||(anexoBaseConhecimentoDTO.getNomeAnexo().length() <= 0)) {
            doc.add(new Field("nome", "vazio", Store.YES, Index.ANALYZED));
        } else {
            doc.add(new Field("nome", anexoBaseConhecimentoDTO.getNomeAnexo(), Store.YES, Index.ANALYZED));
        }

        if ((anexoBaseConhecimentoDTO.getTextoDocumento()==null)||(anexoBaseConhecimentoDTO.getTextoDocumento().length() <= 0)) {
            doc.add(new Field("texto", "vazio", Store.YES, Index.ANALYZED));
        } else {
            doc.add(new Field("texto", anexoBaseConhecimentoDTO.getTextoDocumento(), Store.YES, Index.ANALYZED));
        }

        NumericField idbaseconhecimento = new NumericField("idbaseconhecimento", Store.YES, true);
        idbaseconhecimento.setLongValue(anexoBaseConhecimentoDTO.getIdBaseConhecimento());
        doc.add(idbaseconhecimento);

        NumericField id = new NumericField("id", Store.YES, true);
        id.setLongValue(anexoBaseConhecimentoDTO.getIdAnexoBaseConhecimento());
        doc.add(id);

        indexWriter.addDocument(doc);

        indexWriter.close();
        indexDir.close();
        return true;
    }

    private boolean indexarAnexoBaseConhecimento(List<AnexoBaseConhecimentoDTO> listaAnexoBaseConhecimento) {
        try {
            if (listaAnexoBaseConhecimento != null) {
                for (AnexoBaseConhecimentoDTO anexoBaseConhecimentoDTO : listaAnexoBaseConhecimento) {
                    if (anexoBaseConhecimentoDTO.getDescricao() != null
                            && anexoBaseConhecimentoDTO.getIdBaseConhecimento() != null
                            && anexoBaseConhecimentoDTO
                            .getIdAnexoBaseConhecimento() != null) {
                        indexarDocumentoAnexoBaseConhecimento(anexoBaseConhecimentoDTO);
                    }
                }
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean indexarDocumentoBaseConhecimento(BaseConhecimentoDTO baseConhecimentoDTO,boolean excluiAnexos) throws IOException {
        this.excluirBaseConhecimento(baseConhecimentoDTO,excluiAnexos);
        if (baseConhecimentoDTO.ativa()){
            Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_35);
            Directory indexDir = FSDirectory.open(new File(dirBaseConhecimento));

            IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_35, analyzer);
            config.setOpenMode(OpenMode.CREATE_OR_APPEND);
            IndexWriter indexWriter = new IndexWriter(indexDir, config);

            Document doc = new Document();
            if ((baseConhecimentoDTO.getTitulo()==null)||(baseConhecimentoDTO.getTitulo().length() <= 0)) {
                doc.add(new Field("titulo", "vazio", Store.YES, Index.ANALYZED));
            } else {
                doc.add(new Field("titulo", escape(baseConhecimentoDTO.getTitulo()),
                        Store.YES, Index.ANALYZED));
            }

            if ((baseConhecimentoDTO.getConteudoSemFormatacao()==null)||(baseConhecimentoDTO.getConteudoSemFormatacao().length() <= 0)) {
                doc.add(new Field("conteudo", "vazio", Store.YES, Index.ANALYZED));
            } else {
                doc.add(new Field("conteudo", baseConhecimentoDTO.getConteudoSemFormatacao(), Store.YES, Index.ANALYZED));
            }

            NumericField idusuarioautor = new NumericField("idusuarioautor", Store.YES, true);
            if (baseConhecimentoDTO.getIdUsuarioAutor() == null) {
                idusuarioautor.setLongValue(0);
            } else {
                idusuarioautor.setLongValue(baseConhecimentoDTO.getIdUsuarioAutor());
            }
            doc.add(idusuarioautor);

            NumericField idusuarioaprovador = new NumericField("idusuarioaprovador", Store.YES, true);
            if (baseConhecimentoDTO.getIdUsuarioAprovador() == null) {
                idusuarioaprovador.setLongValue(0);
            } else {
                idusuarioaprovador.setLongValue(baseConhecimentoDTO.getIdUsuarioAprovador());
            }
            doc.add(idusuarioaprovador);

            NumericField datainicio = new NumericField("datainicio", Store.YES, true);
            if (baseConhecimentoDTO.getDataInicio() == null) {
                datainicio.setLongValue(0);
            } else {
                datainicio.setLongValue(baseConhecimentoDTO.getDataInicio().getTime());
            }
            doc.add(datainicio);

            NumericField datapublicacao = new NumericField("datapublicacao", Store.YES, true);
            if (baseConhecimentoDTO.getDataPublicacao() == null) {
                datapublicacao.setLongValue(0);
            } else {
                datapublicacao.setLongValue(baseConhecimentoDTO.getDataPublicacao().getTime());
            }
            doc.add(datapublicacao);

            NumericField dataexpiracao = new NumericField("dataexpiracao", Store.YES, true);
            if (baseConhecimentoDTO.getDataExpiracao() == null) {
                dataexpiracao.setLongValue(0);
            } else {
                dataexpiracao.setLongValue(baseConhecimentoDTO.getDataExpiracao().getTime());
            }
            doc.add(dataexpiracao);

            if ((baseConhecimentoDTO.getMedia()==null)||(baseConhecimentoDTO.getMedia().length() <= 0)) {
                doc.add(new Field("avaliacao", "vazio", Store.YES, Index.NOT_ANALYZED));
            } else {
                doc.add(new Field("avaliacao", baseConhecimentoDTO.getMedia(), Store.YES, Index.NOT_ANALYZED));
            }

            NumericField cliques = new NumericField("cliques", Store.YES, true);
            if (baseConhecimentoDTO.getContadorCliques() == null) {
                cliques.setLongValue(0);
            } else {
                cliques.setLongValue(baseConhecimentoDTO.getContadorCliques());
            }
            doc.add(cliques);

            NumericField idpasta = new NumericField("idpasta", Store.YES, true);
            if (baseConhecimentoDTO.getIdPasta() == null) {
                idpasta.setLongValue(0);
            } else {
                idpasta.setLongValue(baseConhecimentoDTO.getIdPasta());
            }
            doc.add(idpasta);

            if (baseConhecimentoDTO.getGerenciamentoDisponibilidade() == null || !baseConhecimentoDTO.getGerenciamentoDisponibilidade().equalsIgnoreCase("S")) {
                doc.add(new Field("gerdisponibilidade", "N", Store.YES, Index.NOT_ANALYZED));
            } else {
                doc.add(new Field("gerdisponibilidade", "S", Store.YES, Index.NOT_ANALYZED));
            }

            NumericField id = new NumericField("id", Store.YES, true);
            id.setLongValue(baseConhecimentoDTO.getIdBaseConhecimento());
            doc.add(id);

            indexWriter.addDocument(doc);

            indexWriter.close();
            indexDir.close();
            return true;
        } else {
            return false;
        }
    }

    public boolean indexarBaseConhecimento(BaseConhecimentoDTO baseConhecimentoDTO) {
        try {
            if (baseConhecimentoDTO != null && baseConhecimentoDTO.getIdBaseConhecimento() != null) {
                indexarDocumentoBaseConhecimento(baseConhecimentoDTO,false);
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean indexarBaseConhecimento(BaseConhecimentoDTO baseConhecimentoDTO,List<AnexoBaseConhecimentoDTO> listaAnexoBaseConhecimento) {
        try {
            if (baseConhecimentoDTO != null && baseConhecimentoDTO.getIdBaseConhecimento() != null) {
                indexarDocumentoBaseConhecimento(baseConhecimentoDTO,true); //Já exclui a Base e seus anexos se ela não estiver ativa!
                if (baseConhecimentoDTO.ativa()){
                    indexarAnexoBaseConhecimento(listaAnexoBaseConhecimento);
                }
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Exclui documento da indexação
     *
     * @param diretorio
     * @param indice
     * @param campo
     * @return
     */
    private boolean excluiDoc(String diretorio, Long id) {
        try {
            Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_35);
            Directory indexDir = FSDirectory.open(new File(diretorio));
            IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_35,
                    analyzer);
            config.setOpenMode(OpenMode.CREATE_OR_APPEND);
            IndexWriter indexWriter = new IndexWriter(indexDir, config);

            indexWriter.deleteDocuments(new Term("id", NumericUtils.longToPrefixCoded(id)));

            indexWriter.close();
            indexDir.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluirPalavraGemea(PalavraGemeaDTO palGemea) {
        if (palGemea != null && palGemea.getIdPalavraGemea() > 0) {
            this.excluiDoc(dirGemeas, Long.parseLong(String.valueOf(palGemea.getIdPalavraGemea())));
        }
        return true;
    }

    //Exclui usando o ID do Anexo!!!!!
    private boolean excluirAnexoBaseConhecimento(AnexoBaseConhecimentoDTO anexoBaseConhecimentoDTO){
        if (anexoBaseConhecimentoDTO != null && anexoBaseConhecimentoDTO.getIdAnexoBaseConhecimento() > 0) {
            this.excluiDoc(dirAnexos,Long.parseLong(String.valueOf(anexoBaseConhecimentoDTO.getIdAnexoBaseConhecimento())));
        }
        return true;
    }

    //Exclui usando o ID da base de Conhecimento!!!!!
    public boolean excluiAnexosDaBaseConhecimento(Long id) {
        try {
            Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_35);
            Directory indexDir = FSDirectory.open(new File(dirAnexos));
            IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_35,analyzer);
            config.setOpenMode(OpenMode.CREATE_OR_APPEND);
            IndexWriter indexWriter = new IndexWriter(indexDir, config);

            indexWriter.deleteDocuments(new Term("idbaseconhecimento", NumericUtils.longToPrefixCoded(id)));

            indexWriter.close();
            indexDir.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Exclui a Base de Conhecimento e todos os seus anexos!!!
    public boolean excluirBaseConhecimento(BaseConhecimentoDTO baseConhecimentoDTO, boolean excluiAnexos) {
        if (baseConhecimentoDTO != null && baseConhecimentoDTO.getIdBaseConhecimento() > 0) {
            if (excluiAnexos) {
                this.excluiAnexosDaBaseConhecimento(Long.parseLong(String.valueOf(baseConhecimentoDTO.getIdBaseConhecimento())));
            }
            this.excluiDoc(dirBaseConhecimento, Long.parseLong(String.valueOf(baseConhecimentoDTO.getIdBaseConhecimento())));
        }
        return true;
    }

    /*	public boolean baseConhecimentoIndexada(Long id) {
		boolean resultado = false;
		try {
			Directory indexDir = FSDirectory.open(new File(this.dirBaseConhecimento));
			IndexSearcher isearcher = new IndexSearcher(indexDir);

			TermQuery termQuery = new TermQuery(new Term("id", NumericUtils.longToPrefixCoded(id)));

			ScoreDoc[] hits = isearcher.search(termQuery, null, 1).scoreDocs;

			if (hits.length > 0) {
				resultado = true;
			}

			isearcher.close();
			indexDir.close();
			return resultado;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
     */

    private ArrayList<BaseConhecimentoDTO> pesquisaShould(String indice, ArrayList<String> gemeas, ArrayList<Long> anexos, String avaliacao, String gerDisp, long idAutor, long idAprovador, long dtInicioi, long dtIniciof, long dtPublicacaoi, long dtPublicacaof, long dtExpiracaoi, long dtExpiracaof, long idPasta) throws ParseException, LogicException {
        try {
            ArrayList<BaseConhecimentoDTO> resultado = new ArrayList<BaseConhecimentoDTO>();
            Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_35);
            Directory indexDir = FSDirectory.open(new File(dirBaseConhecimento));
            // Tratando para ver se existe arquivo de índice no diretório
            // if (indexDir.listAll().length > 0) { Não vou utilizar por questão
            // de performance, trazer uma lista com todos os arquivos do
            // diretório pode demorar!
            IndexSearcher isearcher = new IndexSearcher(indexDir);
            QueryParser parser;
            Query query;
            BooleanQuery booleanQuery = new BooleanQuery();

            if (indice != null && !indice.isEmpty() && indice.length() > 2) {
                // Pesquisa principal informada pelo usuário no campo titulo
                parser = new QueryParser(Version.LUCENE_35, "titulo", analyzer);
                query = parser.parse(indice);
                booleanQuery.add(query, BooleanClause.Occur.SHOULD);

                // Busca por referências às palavras gêmeas envolvidas no campo titulo
                if (gemeas != null && gemeas.size() > 0) {
                    for (String palavra : gemeas) {
                        parser = new QueryParser(Version.LUCENE_35, "titulo", analyzer);
                        query = parser.parse(palavra);
                        booleanQuery.add(query, BooleanClause.Occur.SHOULD);
                    }
                }

                // Pesquisa principal informada pelo usuário no campo conteudo
                parser = new QueryParser(Version.LUCENE_35, "conteudo", analyzer);
                query = parser.parse(indice);
                booleanQuery.add(query, BooleanClause.Occur.SHOULD);

                // Busca por referências às palavras gêmeas envolvidas no campo conteudo
                if (gemeas != null && gemeas.size() > 0) {
                    for (String palavra : gemeas) {
                        parser = new QueryParser(Version.LUCENE_35, "conteudo", analyzer);
                        query = parser.parse(palavra);
                        booleanQuery.add(query, BooleanClause.Occur.SHOULD);
                    }
                }
            }

            // Busca Base de conhecimento que tem ANEXO se referindo ao conteúdo da pesquisa
            if (anexos != null && anexos.size() > 0) {
                for (long idBcAnx : anexos) {
                    query = new TermQuery(new Term("id", NumericUtils.longToPrefixCoded(idBcAnx)));
                    booleanQuery.add(query, BooleanClause.Occur.SHOULD);
                }
            }

            // Busca por avaliaçao
            if (avaliacao != null && !avaliacao.isEmpty() && avaliacao.length() > 0) {
                query = new TermQuery(new Term("avaliacao", avaliacao));
                booleanQuery.add(query, BooleanClause.Occur.MUST);
            }

            // Busca por Gera Disponibilidade
            if (gerDisp != null && !gerDisp.isEmpty() && gerDisp.length() > 0) {
                query = new TermQuery(new Term("gerdisponibilidade", gerDisp));
                booleanQuery.add(query, BooleanClause.Occur.MUST);
            }

            // Busca por ID do Usuário Autor
            if (idAutor > 0) {
                query = new TermQuery(new Term("idusuarioautor", NumericUtils.longToPrefixCoded(idAutor)));
                booleanQuery.add(query, BooleanClause.Occur.MUST);
            }

            // Busca por ID do Usuário Aprovador
            if (idAprovador > 0) {
                query = new TermQuery(new Term("idusuarioaprovador", NumericUtils.longToPrefixCoded(idAprovador)));
                booleanQuery.add(query, BooleanClause.Occur.MUST);
            }

            // Busca por período de data de início
            if (dtInicioi > 0) {
                query = NumericRangeQuery.newLongRange("datainicio", dtInicioi, dtIniciof, true, true);
                booleanQuery.add(query, BooleanClause.Occur.MUST);
            }

            // Busca por período de data de publicação
            if (dtPublicacaoi > 0) {
                query = NumericRangeQuery.newLongRange("datapublicacao", dtPublicacaoi, dtPublicacaof, true, true);
                booleanQuery.add(query, BooleanClause.Occur.MUST);
            }

            // Busca por período de data de expiração
            if (dtExpiracaoi > 0) {
                query = NumericRangeQuery.newLongRange("dataexpiracao", dtExpiracaoi, dtExpiracaof, true, true);
                booleanQuery.add(query, BooleanClause.Occur.MUST);
            }

            // Busca por ID da Pasta
            if (idPasta > 0) {
                query = new TermQuery(new Term("idpasta", NumericUtils.longToPrefixCoded(idPasta)));
                booleanQuery.add(query, BooleanClause.Occur.MUST);
            }

            // Ordenando por grau de similaridade e depois por número de cliques decrescente
            Sort sort = new Sort(new SortField[] { SortField.FIELD_SCORE,
                    new SortField("cliques", SortField.LONG, true) });
            TopFieldCollector topField = TopFieldCollector.create(sort, 100,
                    true, true, true, false);
            isearcher.search(booleanQuery, topField);
            ScoreDoc[] hits = topField.topDocs().scoreDocs;

            BaseConhecimentoDTO baseConhecimentoDTO;

            Long dataCampo;
            if (hits.length > 0) {
                for (int i = 0; i < hits.length; i++) {
                    Document hitDoc = isearcher.doc(hits[i].doc);
                    baseConhecimentoDTO = new BaseConhecimentoDTO();
                    baseConhecimentoDTO.setTitulo(hitDoc.get("titulo"));
                    baseConhecimentoDTO.setConteudo(hitDoc.get("conteudo"));
                    baseConhecimentoDTO.setMedia(hitDoc.get("avaliacao"));
                    baseConhecimentoDTO.setGerenciamentoDisponibilidade(hitDoc.get("gerdisponibilidade"));
                    baseConhecimentoDTO.setIdUsuarioAutor(Integer.parseInt(hitDoc.get("idusuarioautor")));
                    baseConhecimentoDTO.setIdUsuarioAprovador(Integer.parseInt(hitDoc.get("idusuarioaprovador")));

                    dataCampo = Long.parseLong(hitDoc.get("datainicio"));
                    baseConhecimentoDTO.setDataInicio(new Date(dataCampo));

                    dataCampo = Long.parseLong(hitDoc.get("datapublicacao"));
                    baseConhecimentoDTO.setDataPublicacao(new Date(dataCampo));

                    dataCampo = Long.parseLong(hitDoc.get("dataexpiracao"));
                    baseConhecimentoDTO.setDataExpiracao(new Date(dataCampo));

                    baseConhecimentoDTO.setIdPasta(Integer.parseInt(hitDoc.get("idpasta")));
                    baseConhecimentoDTO.setContadorCliques(Integer.parseInt(hitDoc.get("cliques")));
                    baseConhecimentoDTO.setIdBaseConhecimento(Integer.parseInt(hitDoc.get("id")));
                    resultado.add(baseConhecimentoDTO);
                }
            }
            indexDir.close();
            dataCampo = null;

            isearcher.close();

            return resultado;

        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<BaseConhecimentoDTO>();
        }
    }

    private ArrayList<Long> pesquisaAnexos(String indice, ArrayList<String> gemeas) throws ParseException {
        try {
            ArrayList<Long> resultado = new ArrayList<Long>();
            Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_35);
            Directory indexDir = FSDirectory.open(new File(dirAnexos));
            IndexSearcher isearcher = new IndexSearcher(indexDir);
            QueryParser parser;
            Query query;
            BooleanQuery booleanQuery = new BooleanQuery();

            // Pesquisa principal informada pelo usuário no campo nome
            parser = new QueryParser(Version.LUCENE_35, "nome", analyzer);
            query = parser.parse(indice);
            booleanQuery.add(query, BooleanClause.Occur.SHOULD);

            // Busca por referências às palavras gêmeas envolvidas no campo nome
            if (gemeas != null && gemeas.size() > 0) {
                for (String palavra : gemeas) {
                    parser = new QueryParser(Version.LUCENE_35, "nome", analyzer);
                    query = parser.parse(palavra);
                    booleanQuery.add(query, BooleanClause.Occur.SHOULD);
                }
            }

            // Pesquisa principal informada pelo usuário no campo descricao
            parser = new QueryParser(Version.LUCENE_35, "descricao", analyzer);
            query = parser.parse(indice);
            booleanQuery.add(query, BooleanClause.Occur.SHOULD);

            // Busca por referências às palavras gêmeas envolvidas no campo descricao
            if (gemeas != null && gemeas.size() > 0) {
                for (String palavra : gemeas) {
                    parser = new QueryParser(Version.LUCENE_35, "descricao", analyzer);
                    query = parser.parse(palavra);
                    booleanQuery.add(query, BooleanClause.Occur.SHOULD);
                }
            }

            // Pesquisa principal informada pelo usuário no campo texto
            parser = new QueryParser(Version.LUCENE_35, "texto", analyzer);
            query = parser.parse(indice);
            booleanQuery.add(query, BooleanClause.Occur.SHOULD);

            // Busca por referências às palavras gêmeas envolvidas no campo texto
            if (gemeas != null && gemeas.size() > 0) {
                for (String palavra : gemeas) {
                    parser = new QueryParser(Version.LUCENE_35, "texto", analyzer);
                    query = parser.parse(palavra);
                    booleanQuery.add(query, BooleanClause.Occur.SHOULD);
                }
            }

            // Ordenando por grau de similaridade e depois por número de
            // cliques
            // decrescente
            Sort sort = new Sort(new SortField[] { SortField.FIELD_SCORE,
                    new SortField("cliques", SortField.LONG, true) });
            TopFieldCollector topField = TopFieldCollector.create(sort, 500,
                    true, true, true, false);
            isearcher.search(booleanQuery, topField);
            ScoreDoc[] hits = topField.topDocs().scoreDocs;

            if (hits.length > 0) {
                for (int i = 0; i < hits.length; i++) {
                    Document hitDoc = isearcher.doc(hits[i].doc);
                    resultado.add(Long.parseLong(hitDoc.get("idbaseconhecimento")));
                }
            }

            isearcher.close();
            indexDir.close();
            return resultado;

        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<Long>();
        }
    }

    private ArrayList<String> pesquisaPalavrasGemeas(String indice) throws ParseException {
        try {
            ArrayList<String> resultado = new ArrayList<String>();
            Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_35);
            Directory indexDir = FSDirectory.open(new File(dirGemeas));
            IndexSearcher isearcher = new IndexSearcher(indexDir);
            QueryParser parser;
            Query query;
            BooleanQuery booleanQuery = new BooleanQuery();

            // Localizando as palavras gêmeas envolvidas a partir do que o
            // usuário digitou
            // pesquisamos palavra por palavra nos dois campos da tabela
            // palavragemea

            parser = new QueryParser(Version.LUCENE_35, "palavra", analyzer);
            query = parser.parse(escape(indice));
            booleanQuery.add(query, BooleanClause.Occur.SHOULD);

            parser = new QueryParser(Version.LUCENE_35, "correspondente", analyzer);
            query = parser.parse(escape(indice));
            booleanQuery.add(query, BooleanClause.Occur.SHOULD);

            ScoreDoc[] hits = isearcher.search(booleanQuery, null, 1000).scoreDocs;

            if (hits.length > 0) {
                for (int i = 0; i < hits.length; i++) {
                    Document hitDoc = isearcher.doc(hits[i].doc);
                    resultado.add(hitDoc.get("palavra"));
                    resultado.add(hitDoc.get("correspondente"));
                }
            }

            isearcher.close();
            indexDir.close();
            return resultado;

        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<String>();
        }

    }

    private String termoPesquisaLimpo(String termo){
        termo = escape(termo);
        return termo;
    }

    public ArrayList<BaseConhecimentoDTO> pesquisaBaseConhecimento(BaseConhecimentoDTO baseConhecimentoDTO) throws ParseException, LogicException {
        String indice;
        String avaliacao;
        String gerDisp;
        long idAutor;
        long idAprovador;
        long dtInicioi;
        long dtIniciof;
        long dtPublicacaoi;
        long dtPublicacaof;
        long dtExpiracaoi;
        long dtExpiracaof;
        long idPasta;

        if (baseConhecimentoDTO.getTermoPesquisa() == null || baseConhecimentoDTO.getTermoPesquisa().isEmpty()) {
            indice = "";
        } else {
            indice = baseConhecimentoDTO.getTermoPesquisa();
            indice = termoPesquisaLimpo(indice+"*");
        }

        if (baseConhecimentoDTO.getTermoPesquisaNota() == null || baseConhecimentoDTO.getTermoPesquisaNota().isEmpty()) {
            avaliacao = "";
        } else {
            if (baseConhecimentoDTO.getTermoPesquisaNota().equals("S")){
                avaliacao = "vazio";
            } else {
                avaliacao = baseConhecimentoDTO.getTermoPesquisaNota();
            }
        }

        if (baseConhecimentoDTO.getAmDoc() == null || baseConhecimentoDTO.getAmDoc().isEmpty()) {
            gerDisp = "";
        } else {
            gerDisp = baseConhecimentoDTO.getAmDoc();
        }

        if (baseConhecimentoDTO.getIdUsuarioAutorPesquisa() == null || baseConhecimentoDTO.getIdUsuarioAutorPesquisa()<=0) {
            idAutor = 0;
        } else {
            idAutor = baseConhecimentoDTO.getIdUsuarioAutorPesquisa();
        }

        if (baseConhecimentoDTO.getIdUsuarioAprovadorPesquisa() == null || baseConhecimentoDTO.getIdUsuarioAprovadorPesquisa()<=0) {
            idAprovador = 0;
        } else {
            idAprovador = baseConhecimentoDTO.getIdUsuarioAprovadorPesquisa();
        }

        if (baseConhecimentoDTO.getDataInicioPesquisa() == null) {
            dtInicioi = 0;
        } else {
            dtInicioi = baseConhecimentoDTO.getDataInicioPesquisa().getTime();
        }

        if (baseConhecimentoDTO.getDataInicioPesquisa() == null) { //Criar o data fim Pesquisa!
            dtIniciof = 0;
        } else {
            dtIniciof = baseConhecimentoDTO.getDataInicioPesquisa().getTime();
        }

        dtPublicacaoi = 0;
        if (baseConhecimentoDTO.getDataInicioPublicacao() != null) {
            dtPublicacaoi = baseConhecimentoDTO.getDataInicioPublicacao().getTime();
        } else if (baseConhecimentoDTO.getDataPublicacaoPesquisa() != null) {
            dtPublicacaoi = baseConhecimentoDTO.getDataPublicacaoPesquisa().getTime();
        }

        dtPublicacaof = 0;
        if (baseConhecimentoDTO.getDataFimPublicacao() != null) {
            dtPublicacaof = baseConhecimentoDTO.getDataFimPublicacao().getTime();
        } else if (baseConhecimentoDTO.getDataPublicacaoPesquisa() != null) {
            dtPublicacaof = baseConhecimentoDTO.getDataPublicacaoPesquisa().getTime();
        }

        if (baseConhecimentoDTO.getDataInicioExpiracao() == null) {
            dtExpiracaoi = 0;
        } else {
            dtExpiracaoi = baseConhecimentoDTO.getDataInicioExpiracao().getTime();
        }

        if (baseConhecimentoDTO.getDataFimExpiracao() == null) {
            dtExpiracaof = 0;
        } else {
            dtExpiracaof = baseConhecimentoDTO.getDataFimExpiracao().getTime();
        }

        if (baseConhecimentoDTO.getIdPasta() == null || baseConhecimentoDTO.getIdPasta()<=0) {
            idPasta = 0;
        } else {
            idPasta = baseConhecimentoDTO.getIdPasta();
        }

        ArrayList<String> gemeas;
        ArrayList<Long> listaIdsBasesConhecimentoanexas;
        if (indice == null || indice.isEmpty() || indice.length() <= 1) {
            gemeas = new ArrayList<String>();
            listaIdsBasesConhecimentoanexas = new ArrayList<Long>();
        } else {
            gemeas = pesquisaPalavrasGemeas(indice);
            listaIdsBasesConhecimentoanexas = pesquisaAnexos(indice, gemeas);
        }

        return pesquisaShould(indice, gemeas, listaIdsBasesConhecimentoanexas, avaliacao, gerDisp, idAutor, idAprovador, dtInicioi, dtIniciof, dtPublicacaoi, dtPublicacaof, dtExpiracaoi, dtExpiracaof, idPasta);
    }

    private static final Pattern unescape = Pattern.compile("\\\\([+\\-\"])");

    private static String escape(String s) {
        s = QueryParser.escape(s);
        s = unescape.matcher(s).replaceAll("$1");
        if ((count(s, '"') & 1) != 0) {
            s += " \"";
        }
        return s;
    }

    private static int count(String s, char c) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == c) {
                count++;
            }
        }
        return count;
    }

}