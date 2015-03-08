package br.com.centralit.citged.integracao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.AnexoBaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.ConnectionReadOnlyProvider;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class ControleGEDDao extends CrudDaoDefaultImpl {

    private static final String SQL_RESTORE = "SELECT IDCONTROLEGED, IDTABELA, ID, NOMEARQUIVO, DESCRICAOARQUIVO, EXTENSAOARQUIVO, DATAHORA, PASTA, CONTEUDOARQUIVO, VERSAO FROM controleged WHERE IDCONTROLEGED = ?";

    public ControleGEDDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<ControleGEDDTO> find(final IDto obj) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();

        listFields.add(new Field("idControleGED", "idControleGED", true, true, false, false));
        listFields.add(new Field("idTabela", "idTabela", false, false, false, false));
        listFields.add(new Field("id", "id", false, false, false, false));
        listFields.add(new Field("nomeArquivo", "nomeArquivo", false, false, false, false));
        listFields.add(new Field("descricaoArquivo", "descricaoArquivo", false, false, false, false));
        listFields.add(new Field("extensaoArquivo", "extensaoArquivo", false, false, false, false));
        listFields.add(new Field("dataHora", "dataHora", false, false, false, false));
        listFields.add(new Field("pasta", "pasta", false, false, false, false));
        listFields.add(new Field("versao", "versao", false, false, false, false));

        return listFields;
    }

    @Override
    public String getTableName() {
        return "controleged";
    }

    @Override
    public Collection<ControleGEDDTO> list() throws PersistenceException {
        return null;
    }

    public Collection listByIdTabelaAndID(final Integer idTabela, final Integer id) throws Exception {
        final List<Order> list = new ArrayList<>();
        list.add(new Order("dataHora", Order.DESC));
        final ControleGEDDTO obj = new ControleGEDDTO();
        obj.setIdTabela(idTabela);
        obj.setId(id);
        return super.find(obj, list);
    }

    /**
     * Pesquisa utilizada somente para arquivos anexados na Base de Conhecimento. idTabela = 4.
     *
     * @param idTabela
     * @param idBasePai
     * @param idBaseFilho
     * @return
     * @throws Exception
     */
    public Collection listByIdTabelaAndIdBaseConhecimentoPaiEFilho(final Integer idTabela, final Integer idBasePai, final Integer idBaseFilho) throws Exception {
        final List<Integer> parametros = new ArrayList<>();

        final StringBuilder sql = new StringBuilder();

        sql.append("SELECT IDCONTROLEGED, IDTABELA, ID, NOMEARQUIVO, DESCRICAOARQUIVO, EXTENSAOARQUIVO, DATAHORA, PASTA, CONTEUDOARQUIVO FROM controleged WHERE idtabela = ? AND (id = ? or id = ?)");
        parametros.add(idTabela);
        parametros.add(idBasePai);
        parametros.add(idBaseFilho);

        final List list = this.execSQL(sql.toString(), parametros.toArray());

        return engine.listConvertion(this.getBean(), list, (List) this.getFields());
    }

    /**
     * Pesquisa GEDs da Base de Conhecimento.
     *
     * @param idTabela
     * @param idBaseConhecimento
     * @return Collection
     * @throws Exception
     */
    public Collection listByIdTabelaAndIdBaseConhecimento(final Integer idTabela, final Integer idBaseConhecimento) throws Exception {
        final List<Integer> parametros = new ArrayList<>();

        final StringBuilder sql = new StringBuilder();
        sql.append("SELECT IDCONTROLEGED, IDTABELA, ID, NOMEARQUIVO, DESCRICAOARQUIVO, EXTENSAOARQUIVO, DATAHORA, PASTA, CONTEUDOARQUIVO FROM controleged WHERE idtabela = ? ");
        parametros.add(idTabela);
        if (idBaseConhecimento != null) {
            sql.append("AND id = ?");
            parametros.add(idBaseConhecimento);
        }
        final List list = this.execSQL(sql.toString(), parametros.toArray());

        return engine.listConvertion(this.getBean(), list, (List) this.getFields());
    }

    @Override
    public Class<ControleGEDDTO> getBean() {
        return ControleGEDDTO.class;
    }

    public String getProximaPastaArmazenar() throws Exception {
        final String sql = "SELECT PASTA, COUNT(*) FROM controleged GROUP BY PASTA ORDER BY 2";
        final List lista = this.execSQL(sql, null);

        final List<String> listRetorno = new ArrayList<String>();
        listRetorno.add("pasta");
        listRetorno.add("qtdeObjetos");

        final List result = engine.listConvertion(this.getBean(), lista, listRetorno);
        if (result == null || result.size() == 0) {
            return "A";
        }
        final ControleGEDDTO controleGEDDTO = (ControleGEDDTO) result.get(0);
        if (controleGEDDTO == null) {
            return "A";
        }
        if (controleGEDDTO.getQtdeObjetos().intValue() > 14999) {
            return this.generateProximaPasta(controleGEDDTO.getPasta());
        }
        return "A";
    }

    private String generateProximaPasta(final String pasta) throws Exception {
        if (pasta.length() == 1) {
            if (pasta.equalsIgnoreCase("Z")) {
                return "AA";
            } else {
                int x = pasta.charAt(0);
                x++;
                return new String((char) x + "");
            }
        } else {
            int x = pasta.charAt(pasta.length() - 1);
            char aux = (char) x;
            if (aux == 'Z') {
                x = pasta.charAt(pasta.length() - 2);
                aux = (char) x;
                if (aux == 'Z') {
                    return pasta + "A";
                } else {
                    String ret = "";
                    if (pasta.length() > 2) {
                        ret = pasta.substring(0, pasta.length() - 2);
                        x++;
                        ret += (char) x + "A";
                    } else {
                        x++;
                        ret = (char) x + "A";
                    }
                    return ret;
                }
            } else {
                final String ret = pasta.substring(0, pasta.length() - 1);
                int y = aux;
                y++;
                return ret + (char) y;
            }
        }
    }

    @Override
    public IDto restore(final IDto obj) throws PersistenceException {
        File f = null;
        ControleGEDDTO ret = null;
        final ControleGEDDTO ged = (ControleGEDDTO) obj;
        try (Connection conn = ConnectionReadOnlyProvider.getConnection(this.getDataBaseAlias()); PreparedStatement ps = conn.prepareStatement(SQL_RESTORE)) {
            ps.setInt(1, ged.getIdControleGED());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ret = new ControleGEDDTO();
                    ret.setIdControleGED(rs.getInt(1));
                    ret.setIdTabela(rs.getInt(2));
                    ret.setId(rs.getInt(3));
                    ret.setNomeArquivo(rs.getString(4));
                    ret.setDescricaoArquivo(rs.getString(5));
                    ret.setExtensaoArquivo(rs.getString(6));
                    ret.setDataHora(rs.getDate(7));
                    ret.setPasta(rs.getString(8));
                    InputStream is = null;
                    try {
                        is = rs.getBinaryStream(9);
                    } catch (final Exception e) {}

                    if (is != null) {
                        int qtd = 0;
                        byte[] b = null;
                        f = new File(Constantes.getValue("DIRETORIO_GED") + "/" + Constantes.getValue("ID_EMPRESA_PROC_BATCH") + "/" + ret.getPasta());
                        f.mkdirs();
                        f = new File(f.getAbsolutePath() + "/" + ret.getIdControleGED() + ".ged");
                        f.createNewFile();
                        ret.setPathArquivo(f.getAbsolutePath());

                        try (final OutputStream os = new FileOutputStream(f)) {
                            do {
                                b = new byte[1024];
                                qtd = is.read(b);
                                if (qtd >= 0) {
                                    os.write(b, 0, qtd);
                                    os.flush();
                                }
                            } while (qtd >= 0);
                        }
                    }
                }
            }
        } catch (final Exception e) {
            throw new PersistenceException(e);
        }
        return ret;
    }

    public void deleteByIdRequisicaoLiberacao(final Integer idRequisicao, final Integer idTabela) throws Exception {
        final String sql = "DELETE FROM " + this.getTableName() + " WHERE id = ? AND idTabela = ?";
        super.execUpdate(sql, new Object[] {idRequisicao, idTabela});
    }

    public Collection<UploadDTO> listByIdTabelaAndIdHistorico(final Integer idTabela, final Integer id) throws Exception {
        final List<String> lstRetorno = new ArrayList<>();
        List list = new ArrayList();
        final StringBuilder sql = new StringBuilder();
        final List<Integer> parametro = new ArrayList<>();

        parametro.add(idTabela);
        parametro.add(id);
        sql.append("select ged.IDCONTROLEGED, ged.NOMEARQUIVO, ged.DESCRICAOARQUIVO ");
        sql.append("from ligacao_historico_ged lig ");
        sql.append("inner join controleged ged on lig.idcontroleged = ged.idcontroleged  ");
        sql.append("where ged.idtabela = ? AND lig.idhistoricoliberacao = ? ");

        list = this.execSQL(sql.toString(), parametro.toArray());

        lstRetorno.add("idControleGED");
        lstRetorno.add("nameFile");
        lstRetorno.add("descricao");

        if (list != null && !list.isEmpty()) {
            return this.listConvertion(UploadDTO.class, list, lstRetorno);
        }
        return null;
    }

    /**
     * Pesquisa GEDs da Requisicao de Liberação.
     *
     * @param idTabela
     * @param idRequisicaoLiberacao
     * @return Collection
     * @throws Exception
     */
    public Collection listByIdTabelaAndIdLiberacao(final Integer idTabela, final Integer idLiberacao) throws Exception {
        final List<Integer> parametros = new ArrayList<>();

        final StringBuilder sql = new StringBuilder();
        sql.append("SELECT IDCONTROLEGED, IDTABELA, ID, NOMEARQUIVO, DESCRICAOARQUIVO, EXTENSAOARQUIVO, DATAHORA, PASTA, CONTEUDOARQUIVO FROM controleged WHERE idtabela = ? ");
        parametros.add(idTabela);
        if (idLiberacao != null) {
            sql.append("AND id = ?");
            parametros.add(idLiberacao);
        }
        final List list = this.execSQL(sql.toString(), parametros.toArray());

        return engine.listConvertion(this.getBean(), list, (List) this.getFields());
    }

    public Collection listByIdTabelaAndIdLiberacaoAndLigacao(final Integer idTabela, final Integer idRequisicaoLiberacao) throws Exception {
        final List<Integer> parametros = new ArrayList<>();

        final StringBuilder sql = new StringBuilder();
        sql.append("SELECT ged.IDCONTROLEGED, ged.IDTABELA, ged.ID, ged.NOMEARQUIVO, ged.DESCRICAOARQUIVO, ged.EXTENSAOARQUIVO, ged.DATAHORA, ged.PASTA, ged.VERSAO ");
        sql.append("FROM controleged  ged ");
        sql.append("WHERE ged.idtabela = ? ");
        parametros.add(idTabela);
        if (idRequisicaoLiberacao != null) {
            sql.append("AND ged.id = ?");
            parametros.add(idRequisicaoLiberacao);
        }

        final List list = this.execSQL(sql.toString(), parametros.toArray());

        return engine.listConvertion(this.getBean(), list, (List) this.getFields());
    }

    public ControleGEDDTO getControleGED(final AnexoBaseConhecimentoDTO anexoBaseConhecimento) throws Exception {
        final List<Condition> condicao = new ArrayList<>();
        condicao.add(new Condition("idTabela", "=", ControleGEDDTO.TABELA_BASECONHECIMENTO));
        condicao.add(new Condition("id", "=", anexoBaseConhecimento.getIdBaseConhecimento()));
        condicao.add(new Condition("nomeArquivo", "=", anexoBaseConhecimento.getNomeAnexo() + "." + anexoBaseConhecimento.getExtensao()));
        final List<ControleGEDDTO> resultado = (List<ControleGEDDTO>) super.findByCondition(condicao, null);
        return resultado == null ? new ControleGEDDTO() : resultado.get(0);
    }

    private String dataBaseAlias;

    private String getDataBaseAlias() {
        if (dataBaseAlias == null) {
            dataBaseAlias = Constantes.getValue("CONTEXTO_CONEXAO") + this.getAliasDB();
        }
        return dataBaseAlias;
    }

}
