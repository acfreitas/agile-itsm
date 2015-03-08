package br.com.centralit.citged.integracao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citged.bean.AssinaturaControleGEDDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.ConnectionProvider;
import br.com.citframework.integracao.ConnectionReadOnlyProvider;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

public class AssinaturaControleGEDDao extends CrudDaoDefaultImpl {

    private static final String SQL_NEXT_KEY = "SELECT MAX(IDASSINATURA) + 1 FROM ASSINATURA_CONTROLEGED";

    private static final String SQL_INSERT = "INSERT INTO ASSINATURA_CONTROLEGED (IDASSINATURA, IDCONTROLEGED, CONTEUDOASSINATURA) VALUES (?, ?, ?)";

    private static final String SQL_RESTORE = "SELECT IDASSINATURA, IDCONTROLEGED, CONTEUDOASSINATURA FROM ASSINATURA_CONTROLEGED WHERE IDASSINATURA = ?";

    @Override
    public String getTableName() {
        return "ASSINATURA_CONTROLEGED";
    }

    @Override
    public Class<AssinaturaControleGEDDTO> getBean() {
        return AssinaturaControleGEDDTO.class;
    }

    public AssinaturaControleGEDDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> fields = new ArrayList<>();
        fields.add(new Field("idAssinatura", "idAssinatura", true, true, false, false));
        fields.add(new Field("idControleGED", "idControleGED", false, false, false, false));
        fields.add(new Field("pathAssinatura", "pathAssinatura", false, false, false, false));
        return fields;
    }

    private Integer nextKey(final Connection conn) throws Exception {
        Integer key = null;
        try (final PreparedStatement ps = conn.prepareStatement(SQL_NEXT_KEY); final ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                key = new Integer(rs.getInt(1));
            }
        }
        return key != null ? key : 1;
    }

    @Override
    public IDto create(final IDto obj) throws PersistenceException {
        File f = null;
        final AssinaturaControleGEDDTO ass = (AssinaturaControleGEDDTO) obj;
        try (Connection conn = ConnectionProvider.getConnection(this.getDataBaseAlias()); PreparedStatement ps = conn.prepareStatement(SQL_INSERT);) {
            ass.setIdAssinatura(this.nextKey(conn));
            ps.setInt(2, ass.getIdControleGED());
            f = new File(ass.getPathAssinatura());
            final InputStream is = new FileInputStream(f);
            if (is.available() > 0) {
                ps.setBinaryStream(3, is, is.available());
            } else {
                ps.setNull(3, Types.BLOB);
            }
            ps.setInt(1, ass.getIdAssinatura());
            ps.executeUpdate();
        } catch (final Exception e) {
            throw new PersistenceException(e);
        } finally {
            if (f != null) {
                f.delete();
            }
        }
        return ass;
    }

    @Override
    public IDto restore(final IDto obj) throws PersistenceException {
        File f = null;
        AssinaturaControleGEDDTO ret = null;
        final AssinaturaControleGEDDTO ass = (AssinaturaControleGEDDTO) obj;
        try (Connection conn = ConnectionReadOnlyProvider.getConnection(this.getDataBaseAlias()); PreparedStatement ps = conn.prepareStatement(SQL_RESTORE)) {
            ps.setInt(1, ass.getIdControleGED());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ret = new AssinaturaControleGEDDTO();
                    ret.setIdAssinatura(rs.getInt(1));
                    ret.setIdControleGED(rs.getInt(2));
                    final InputStream is = rs.getBinaryStream(3);
                    int qtd = 0;
                    byte[] b = null;
                    f = new File(Constantes.getValue("DIRETORIO_GED") + "/" + Constantes.getValue("ID_EMPRESA_PROC_BATCH") + "/" + ret.getPastaControleGed());
                    f.mkdirs();
                    f = new File(f.getAbsolutePath() + "/" + ret.getIdControleGED() + ".ged");
                    f.createNewFile();
                    ret.setPathAssinatura(f.getAbsolutePath());
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
        } catch (final Exception e) {
            throw new PersistenceException(e);
        }
        return ret;
    }

    @Override
    @Deprecated
    public Collection<AssinaturaControleGEDDTO> find(final IDto obj) throws PersistenceException {
        return null;
    }

    @Override
    @Deprecated
    public Collection<AssinaturaControleGEDDTO> list() throws PersistenceException {
        return null;
    }

    private String dataBaseAlias;

    private String getDataBaseAlias() {
        if (dataBaseAlias == null) {
            dataBaseAlias = Constantes.getValue("CONTEXTO_CONEXAO") + this.getAliasDB();
        }
        return dataBaseAlias;
    }

}
