/**
 *
 */
package br.com.citframework.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.citframework.dto.IDto;
import br.com.citframework.dto.LogAcessoUsuario;
import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.util.Constantes;

/**
 * @author karem.ricarte
 *
 */
public class LogAcessoUsuarioDao extends CrudDaoDefaultImpl {

    public LogAcessoUsuarioDao(final Usuario usuario) {
        super(Constantes.getValue("CONEXAO_DEFAUT"), usuario);
    }

    @Override
    public Collection<LogAcessoUsuario> find(final IDto dto) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final List<Field> lista = new ArrayList<>();
        lista.add(new Field("dtAcessoUsuario", "dtAcessoUsuario", false, false, false, false));
        lista.add(new Field("HistAtualUsuario_idUsuario", "HistAtualUsuario_idUsuario", false, false, false, false));
        lista.add(new Field("login", "login", false, false, false, false));
        return lista;
    }

    @Override
    public String getTableName() {
        return "logacessousuario";
    }

    @Override
    public Collection list() throws PersistenceException {
        final List<Order> ordenacao = new ArrayList<>();
        ordenacao.add(new Order("dtAcessoUsuario"));
        return super.list(ordenacao);
    }

    @Override
    public Class<LogAcessoUsuario> getBean() {
        return LogAcessoUsuario.class;
    }

}
