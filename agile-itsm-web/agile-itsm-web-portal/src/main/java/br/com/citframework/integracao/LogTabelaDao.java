/**
 *
 */
package br.com.citframework.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.citframework.dto.IDto;
import br.com.citframework.dto.LogTabela;
import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.util.Constantes;

/**
 * @author karem.ricarte
 *
 */
public class LogTabelaDao extends CrudDaoDefaultImpl {

    public LogTabelaDao(final Usuario usuario) {
        super(Constantes.getValue("DATABASE_ALIAS"), usuario);
    }

    @Override
    public Collection<LogTabela> find(final IDto dto) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final List<Field> lista = new ArrayList<>();
        lista.add(new Field("idLog", "idLog", true, true, false, false));
        lista.add(new Field("nomeTabela", "nomeTabela", false, false, false, false));
        return lista;
    }

    @Override
    public String getTableName() {
        return "logtabela";
    }

    @Override
    public Collection list() throws PersistenceException {
        final List<Order> ordenacao = new ArrayList<>();
        ordenacao.add(new Order("idLog"));
        return this.list(ordenacao);
    }

    @Override
    public Class<LogTabela> getBean() {
        return LogTabela.class;
    }

    public LogTabela getLogByTabela(final String nomeTabela) throws Exception {
        final List<Condition> condicoes = new ArrayList<>();
        condicoes.add(new Condition("nomeTabela", "=", nomeTabela));
        final Collection resultado = this.findByCondition(condicoes, null);
        if (resultado == null || resultado.size() == 0) {
            return null;
        }
        return (LogTabela) ((List) resultado).get(0);

    }

}
