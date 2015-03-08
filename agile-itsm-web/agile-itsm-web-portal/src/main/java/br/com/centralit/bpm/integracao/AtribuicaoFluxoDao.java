package br.com.centralit.bpm.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.bpm.dto.AtribuicaoFluxoDTO;
import br.com.centralit.bpm.dto.GrupoBpmDTO;
import br.com.centralit.bpm.util.Enumerados.SituacaoItemTrabalho;
import br.com.centralit.bpm.util.Enumerados.TipoAtribuicao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class AtribuicaoFluxoDao extends CrudDaoDefaultImpl {

    private static final String TABLE_NAME = "bpm_atribuicaofluxo";

    private static final String SQL_RESTORE = "SELECT A.idAtribuicao, A.idItemTrabalho, A.tipo, A.idUsuario, A.idGrupo, A.dataHora FROM Bpm_AtribuicaoFluxo A INNER JOIN Bpm_ItemTrabalhoFluxo I ON A.idItemTrabalho = I.idItemTrabalho ";

    public AtribuicaoFluxoDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    private List<String> getListaDeCampos() {
        final List<String> listRetorno = new ArrayList<>();
        listRetorno.add("idAtribuicao");
        listRetorno.add("idItemTrabalho");
        listRetorno.add("tipo");
        listRetorno.add("idUsuario");
        listRetorno.add("idGrupo");
        listRetorno.add("dataHora");
        return listRetorno;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idAtribuicao", "idAtribuicao", true, true, false, false));
        listFields.add(new Field("idItemTrabalho", "idItemTrabalho", false, false, false, false));
        listFields.add(new Field("tipo", "tipo", false, false, false, false));
        listFields.add(new Field("idUsuario", "idUsuario", false, false, false, false));
        listFields.add(new Field("idGrupo", "idGrupo", false, false, false, false));
        listFields.add(new Field("dataHora", "dataHora", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public Collection<AtribuicaoFluxoDTO> list() throws PersistenceException {
        return null;
    }

    @Override
    public Class<AtribuicaoFluxoDTO> getBean() {
        return AtribuicaoFluxoDTO.class;
    }

    @Override
    public Collection<AtribuicaoFluxoDTO> find(final IDto arg0) throws PersistenceException {
        return null;
    }

    public Collection<AtribuicaoFluxoDTO> findDisponiveis(final Integer idUsuario, final Collection<GrupoBpmDTO> grupos) throws PersistenceException {
        String sql = SQL_RESTORE + " WHERE I.situacao <> ? AND I.situacao <> ? AND (A.idUsuario = ? ";
        if (grupos != null && !grupos.isEmpty()) {
            sql += " OR A.idGrupo IN (";
            int i = 0;
            for (final GrupoBpmDTO grupoBpmDto : grupos) {
                if (i > 0) {
                    sql += ",";
                }
                sql += grupoBpmDto.getIdGrupo();
                i++;
            }
            sql += ") ";
        }
        sql += " OR I.idResponsavelAtual = ? )";

        final List<?> lista = this.execSQL(sql, new Object[] {SituacaoItemTrabalho.Executado.name(), SituacaoItemTrabalho.Cancelado.name(), idUsuario, idUsuario});

        return engine.listConvertion(this.getBean(), lista, this.getListaDeCampos());
    }

    public Collection<AtribuicaoFluxoDTO> findDisponiveisByIdUsuario(final Integer idUsuario) throws PersistenceException {
        final String sql = SQL_RESTORE + " WHERE I.situacao <> ? AND I.situacao <> ? AND A.idUsuario = ? ORDER BY A.dataHora";

        final List<?> lista = this.execSQL(sql, new Object[] {SituacaoItemTrabalho.Executado.name(), SituacaoItemTrabalho.Cancelado.name(), idUsuario});

        return engine.listConvertion(this.getBean(), lista, this.getListaDeCampos());
    }

    public Collection<AtribuicaoFluxoDTO> findDisponiveisByIdGrupo(final Integer idGrupo) throws Exception {
        final String sql = SQL_RESTORE + " WHERE I.situacao <> ? AND I.situacao <> ?  AND idGrupo = ? ORDER BY A.dataHora";

        final List<?> lista = this.execSQL(sql, new Object[] {SituacaoItemTrabalho.Executado.name(), SituacaoItemTrabalho.Cancelado.name(), idGrupo});

        return engine.listConvertion(this.getBean(), lista, this.getListaDeCampos());
    }

    public Collection<AtribuicaoFluxoDTO> findByDisponiveisByIdInstancia(final Integer idInstancia) throws PersistenceException {
        final String sql = SQL_RESTORE + " WHERE I.situacao <> ? AND I.situacao <> ? AND I.idInstancia = ? ORDER BY A.dataHora";

        final List<?> lista = this.execSQL(sql, new Object[] {SituacaoItemTrabalho.Executado.name(), SituacaoItemTrabalho.Cancelado.name(), idInstancia});

        return engine.listConvertion(this.getBean(), lista, this.getListaDeCampos());
    }

    public Collection<AtribuicaoFluxoDTO> findByDisponiveisByIdItemTrabalho(final Integer idItemTrabalho) throws PersistenceException {
        final String sql = SQL_RESTORE + " WHERE I.situacao <> ? AND I.situacao <> ? AND I.idItemTrabalho = ? ORDER BY A.dataHora";

        final List<?> lista = this.execSQL(sql, new Object[] {SituacaoItemTrabalho.Executado.name(), SituacaoItemTrabalho.Cancelado.name(), idItemTrabalho});

        return engine.listConvertion(this.getBean(), lista, this.getListaDeCampos());
    }

    public Collection<AtribuicaoFluxoDTO> findByIdUsuario(final Integer idUsuario) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("idUsuario", "=", idUsuario));
        ordenacao.add(new Order("idAtribuicao"));
        return super.findByCondition(condicao, ordenacao);
    }

    public Collection<AtribuicaoFluxoDTO> findByIdGrupo(final Integer idGrupo) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("idGrupo", "=", idGrupo));
        ordenacao.add(new Order("idAtribuicao"));
        return super.findByCondition(condicao, ordenacao);
    }

    public Collection<AtribuicaoFluxoDTO> findByIdItemTrabalhoAndTipo(final Integer idItemTrabalho, final String tipo) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("idItemTrabalho", "=", idItemTrabalho));
        condicao.add(new Condition("tipo", "=", tipo));
        ordenacao.add(new Order("idAtribuicao"));
        return super.findByCondition(condicao, ordenacao);
    }

    public Collection<AtribuicaoFluxoDTO> findByIdItemTrabalhoAndIdUsuario(final Integer idItemTrabalho, final Integer idUsuario, final TipoAtribuicao tipo)
            throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("idItemTrabalho", "=", idItemTrabalho));
        condicao.add(new Condition("tipo", "=", tipo.name()));
        condicao.add(new Condition("idUsuario", "=", idUsuario));
        ordenacao.add(new Order("idAtribuicao"));
        return super.findByCondition(condicao, ordenacao);
    }

    public void deleteDelegacao(final Integer idItemTrabalho) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        condicao.add(new Condition("idItemTrabalho", "=", idItemTrabalho));
        condicao.add(new Condition("tipo", "=", TipoAtribuicao.Delegacao.name()));
        super.deleteByCondition(condicao);
    }

    /**
     *
     * Consulta na tabela a existencia de registros cadastrados com os itens do parametro
     *
     * @param idItemTrabalho
     * @param idUsuario
     * @param tipo
     * @return
     * @throws PersistenceException
     */
    public Collection<AtribuicaoFluxoDTO> findByIdItemTrabalhoAndIdUsuarioAndTipo(final Integer idItemTrabalho, final Integer idUsuario, final String tipo)
            throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        condicao.add(new Condition("idItemTrabalho", "=", idItemTrabalho));
        condicao.add(new Condition("idUsuario", "=", idUsuario));
        condicao.add(new Condition("tipo", "=", tipo));

        final List<Order> ordenacao = new ArrayList<>();
        ordenacao.add(new Order("idAtribuicao"));

        return super.findByCondition(condicao, ordenacao);
    }

    public Collection<AtribuicaoFluxoDTO> findByDisponiveisByIdInstanciaAndIdUsuario(final Integer idInstancia, final Integer idUsuario) throws PersistenceException {
        final String sql = SQL_RESTORE + " WHERE I.situacao <> ? AND I.situacao <> ? AND I.idInstancia = ? AND A.idUsuario = ? ORDER BY A.dataHora";

        final List<?> lista = this.execSQL(sql, new Object[] {SituacaoItemTrabalho.Executado.name(), SituacaoItemTrabalho.Cancelado.name(), idInstancia, idUsuario});

        return engine.listConvertion(this.getBean(), lista, this.getListaDeCampos());
    }

}
