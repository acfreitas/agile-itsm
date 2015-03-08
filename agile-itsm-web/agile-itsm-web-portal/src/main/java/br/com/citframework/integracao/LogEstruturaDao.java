package br.com.citframework.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.citframework.dto.IDto;
import br.com.citframework.dto.LogEstrutura;
import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.util.Constantes;

public class LogEstruturaDao extends CrudDaoDefaultImpl {

    public LogEstruturaDao(final Usuario usuario) {
        super(Constantes.getValue("CONEXAO_DEFAUT"), usuario);
    }

    @Override
    public Collection<LogEstrutura> find(final IDto dto) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final List<Field> lista = new ArrayList<>();
        lista.add(new Field("logTabela_idlog", "logTabela_idlog", false, false, false, true));
        lista.add(new Field("estrutura", "estrutura", false, false, false, false));
        return lista;
    }

    @Override
    public String getTableName() {
        return "logestrutura";
    }

    @Override
    public Collection list() throws PersistenceException {
        final List<Order> ordenacao = new ArrayList<>();
        ordenacao.add(new Order("logTabela_idlog"));
        return this.list(ordenacao);
    }

    @Override
    public Class<LogEstrutura> getBean() {
        return LogEstrutura.class;
    }

}
