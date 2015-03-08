package br.com.centralit.citquestionario.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import br.com.centralit.citquestionario.bean.ListagemDTO;
import br.com.centralit.citquestionario.bean.ListagemItemDTO;
import br.com.centralit.citquestionario.bean.ListagemLinhaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

public class ListagemDao extends CrudDaoDefaultImpl {

    public ListagemDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<ListagemDTO> find(final IDto obj) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        return null;
    }

    @Override
    public String getTableName() {
        return null;
    }

    @Override
    public Collection<ListagemDTO> list() throws PersistenceException {
        return null;
    }

    @Override
    public Class<ListagemDTO> getBean() {
        return ListagemDTO.class;
    }

    @Override
    public IDto restore(final IDto obj) throws PersistenceException {
        if (obj != null) {
            final Collection linhas = new ArrayList();
            final List lista = this.execSQL(((ListagemDTO) obj).getSQL(), null);

            for (Integer l = 0; l <= lista.size() - 1; l++) {
                final Object[] row = (Object[]) lista.get(l);

                final ListagemLinhaDTO linha = new ListagemLinhaDTO();
                final Collection dados = new ArrayList();
                String descricao = "";

                int c = 0;
                for (final Iterator it = ((ListagemDTO) obj).getCampos().iterator(); it.hasNext();) {
                    final ListagemItemDTO campo = (ListagemItemDTO) it.next();
                    final ListagemItemDTO item = new ListagemItemDTO();
                    item.setNome(campo.getNome());
                    item.setDescricao(campo.getDescricao());
                    item.setValor(row[c].toString());
                    dados.add(item);
                    if (c > 0) {
                        if (descricao != "") {
                            descricao += " - ";
                        }
                        descricao += row[c].toString();
                    }
                    c += 1;
                }
                linha.setId(row[0].toString());
                linha.setDescricao(descricao);
                linha.setDados(dados);
                linhas.add(linha);
            }
            ((ListagemDTO) obj).setLinhas(linhas);
        }
        return obj;
    }

}
