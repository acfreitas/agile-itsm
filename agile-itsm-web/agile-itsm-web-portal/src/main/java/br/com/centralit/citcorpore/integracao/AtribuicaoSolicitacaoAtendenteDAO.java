package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.AtribuicaoSolicitacaoAtendenteDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

/**
 * DAO para persistência de {@link AtribuicaoSolicitacaoAtendenteDTO}
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 06/10/2014
 *
 */
public class AtribuicaoSolicitacaoAtendenteDAO extends CrudDaoDefaultImpl {

    public AtribuicaoSolicitacaoAtendenteDAO() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<Field> getFields() {
        final List<Field> fields = new ArrayList<>();
        fields.add(new Field("id", "id", true, true, false, true));
        fields.add(new Field("idsolicitacao", "idSolicitacao", false, false, false, false));
        fields.add(new Field("idusuario", "idUsuario", false, false, false, false));
        fields.add(new Field("priorityorder", "priorityOrder", false, false, false, false));
        fields.add(new Field("latitude", "latitude", false, false, false, false));
        fields.add(new Field("longitude", "longitude", false, false, false, false));
        fields.add(new Field("dataexecucao", "dataExecucao", false, false, false, false));
        fields.add(new Field("datainicioatendimento", "dataInicioAtendimento", false, false, false, false));
        fields.add(new Field("active", "active", false, false, false, false));
        return fields;
    }

    @Override
    public String getTableName() {
        return "atribuicaosolicitacao";
    }

    @Override
    public Collection<AtribuicaoSolicitacaoAtendenteDTO> find(final IDto posicionamento) throws PersistenceException {
        return null;
    }

    public List<AtribuicaoSolicitacaoAtendenteDTO> findByIDUsuarioAndIDSolicitacao(final Integer idUsuario, final Integer idSolicitacao) throws PersistenceException {
        final List<Integer> parametros = new ArrayList<>();

        final StringBuilder sql = new StringBuilder();
        sql.append("SELECT id, ");
        sql.append("       idsolicitacao, ");
        sql.append("       idusuario, ");
        sql.append("       priorityorder, ");
        sql.append("       latitude, ");
        sql.append("       longitude, ");
        sql.append("       dataexecucao, ");
        sql.append("       datainicioatendimento, ");
        sql.append("       active ");
        sql.append("FROM   atribuicaosolicitacao ");
        sql.append("WHERE  idsolicitacao = ? ");
        sql.append("       AND idusuario = ? ");
        sql.append("       AND active = 1");

        parametros.add(idSolicitacao);
        parametros.add(idUsuario);

        final List<?> result = this.execSQL(sql.toString(), parametros.toArray());

        List<AtribuicaoSolicitacaoAtendenteDTO> converted = new ArrayList<>();
        if (result != null && result.size() > 0) {
            converted = this.listConvertion(this.getBean(), result, (List<Field>) this.getFields());
        }

        return converted;
    }

    public boolean existeAtribuicao(final Integer taskId, final UsuarioDTO user) throws PersistenceException {
        final StringBuilder sql = new StringBuilder();
        sql.append("SELECT atriSolicitacao.idsolicitacao ");
        sql.append("FROM   atribuicaosolicitacao atriSolicitacao ");
        sql.append("       INNER JOIN execucaosolicitacao execSolicitacao ");
        sql.append("               ON atriSolicitacao.idsolicitacao = ");
        sql.append("                  execSolicitacao.idsolicitacaoservico ");
        sql.append("       INNER JOIN bpm_itemtrabalhofluxo bpmItem ");
        sql.append("               ON execSolicitacao.idinstanciafluxo = bpmItem.idinstancia ");
        sql.append("WHERE  bpmItem.iditemtrabalho = ? ");
        sql.append("       AND atriSolicitacao.idusuario <> ? ");
        sql.append("       AND atriSolicitacao.active = 1 ");

        final List<Integer> parametros = new ArrayList<>();
        parametros.add(taskId);
        parametros.add(user.getIdUsuario());

        final List<?> result = this.execSQL(sql.toString(), parametros.toArray());

        if (result != null && !result.isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public Collection<AtribuicaoSolicitacaoAtendenteDTO> list() throws PersistenceException {
        final List<Order> ordenacao = new ArrayList<>();
        ordenacao.add(new Order("idsolicitacao"));
        return super.list(ordenacao);
    }

    @Override
    public Class<AtribuicaoSolicitacaoAtendenteDTO> getBean() {
        return AtribuicaoSolicitacaoAtendenteDTO.class;
    }

}
