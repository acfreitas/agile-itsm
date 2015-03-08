package br.com.citframework.integracao;

import java.util.Collection;

import br.com.citframework.dto.Usuario;

public interface IDaoTransact {

    TransactionControler getTransactionControler();

    void setTransactionControler(final TransactionControler tc);

    String getAliasDB();

    String getTableName();

    Collection<Field> getFields();

    Usuario getUsuario();

}
