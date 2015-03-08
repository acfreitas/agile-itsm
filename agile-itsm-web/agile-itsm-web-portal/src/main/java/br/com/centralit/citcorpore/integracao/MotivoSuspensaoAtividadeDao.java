package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.MotivoSuspensaoAtividadeDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class MotivoSuspensaoAtividadeDao extends CrudDaoDefaultImpl {

    public MotivoSuspensaoAtividadeDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idMotivo", "idMotivo", true, true, false, true));
        listFields.add(new Field("descricao", "descricao", false, false, false, false));
        listFields.add(new Field("dataFim", "dataFim", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return this.getOwner() + "MotivoSuspensaoAtivid";
    }

    @Override
    public Collection<MotivoSuspensaoAtividadeDTO> list() throws PersistenceException {
        return super.list("descricao");
    }

    @Override
    public Class<MotivoSuspensaoAtividadeDTO> getBean() {
        return MotivoSuspensaoAtividadeDTO.class;
    }

    @Override
    public Collection<MotivoSuspensaoAtividadeDTO> find(final IDto arg0) throws PersistenceException {
        return null;
    }

    /**
     * Verifica se o registro informado já consta gravado no BD. Considera apenas registros ativos.
     *
     * @param motivoSuspensaoAtividadeDTO
     * @return boolean
     * @throws Exception
     */
    public boolean jaExisteRegistroComMesmoNome(final MotivoSuspensaoAtividadeDTO motivoSuspensaoAtividadeDTO) throws PersistenceException {
        final List<Condition> condicoes = new ArrayList<>();
        condicoes.add(new Condition("descricao", "=", motivoSuspensaoAtividadeDTO.getDescricao()));
        condicoes.add(new Condition("dataFim", "is", null));
        final Collection retorno = super.findByCondition(condicoes, null);
        if (retorno != null) {
            if (retorno.size() > 0) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public Collection<MotivoSuspensaoAtividadeDTO> listarMotivosSuspensaoAtividadeAtivos() throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("dataFim", "is", null));
        ordenacao.add(new Order("descricao"));
        return super.findByCondition(condicao, ordenacao);
    }

}
