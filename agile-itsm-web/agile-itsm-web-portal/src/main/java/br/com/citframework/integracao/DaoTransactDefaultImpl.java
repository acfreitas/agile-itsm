package br.com.citframework.integracao;

import java.util.List;

import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.core.DataBase;

public abstract class DaoTransactDefaultImpl implements IDaoTransact {

    protected String aliasDB;
    protected Usuario usuario;
    protected PersistenceEngine engine;

    protected static final DataBase MAIN_SGBD = DataBase.fromStringId(CITCorporeUtil.SGBD_PRINCIPAL.trim());

    public DaoTransactDefaultImpl(final String aliasDB, final Usuario usuario) {
        this.aliasDB = aliasDB;
        this.usuario = usuario;
        engine = new PersistenceEngine(aliasDB, this.getTableName(), this.getBean(), this.getFields(), this.usuario);
    }

    public DaoTransactDefaultImpl(final TransactionControler tc, final Usuario usuario) {
        this.usuario = usuario;
        aliasDB = tc.getDataBaseAlias();
        engine = new PersistenceEngine(tc, this.getTableName(), this.getBean(), this.getFields(), this.usuario);
    }

    protected List execSQL(final String sql, final Object[] parametros) throws PersistenceException {
        return engine.execSQL(sql, parametros);
    }

    protected int execUpdate(final String sql, final Object[] parametros) throws PersistenceException {
        return engine.execUpdate(sql, parametros);
    }

    protected List listConvertion(final Class<?> classe, final List dados, final List fields) throws PersistenceException {
        return engine.listConvertion(classe, dados, fields);
    }

    @Override
    public String getAliasDB() {
        return aliasDB;
    }

    @Override
    public TransactionControler getTransactionControler() {
        return engine.getTransactionControler();
    }

    @Override
    public void setTransactionControler(final TransactionControler tc) {
        engine.setTransactionControler(tc);
    }

    public abstract Class<?> getBean();

    @Override
    public Usuario getUsuario() {
        return usuario;
    }

}
