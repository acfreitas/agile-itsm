package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.HistoricoFuncionalDTO;
import br.com.centralit.citcorpore.rh.bean.ItemHistoricoFuncionalDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;

/**
 * @author david.silva
 *
 */
public class ItemHistoricoFuncionalDao extends CrudDaoDefaultImpl {

    private static final String TABLE_NAME = "rh_itemhistoricofuncional";

    public ItemHistoricoFuncionalDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Class getBean() {
        return ItemHistoricoFuncionalDTO.class;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();

        listFields.add(new Field("IDITEMHISTORICO", "idItemHistorico", true, true, false, false));
        listFields.add(new Field("IDHISTORICOFUNCIONAL", "idHistoricoFuncional", false, false, false, false));
        listFields.add(new Field("TITULO", "titulo", false, false, false, false));
        listFields.add(new Field("DESCRICAO", "descricao", false, false, false, false));
        listFields.add(new Field("DTCRIACAO", "dtCriacao", false, false, false, false));
        listFields.add(new Field("IDRESPONSAVEL", "idResponsavel", false, false, false, false));
        listFields.add(new Field("TIPO", "tipo", false, false, false, false));

        return listFields;
    }

    public Collection findByIdItemHistorico(final Integer idHistorico) throws PersistenceException {
        final List parametro = new ArrayList();
        final List fields = new ArrayList();
        List list = new ArrayList();

        final StringBuilder sql = new StringBuilder();
        sql.append("select idItemHistorico, idHistoricofuncional, titulo, descricao, dtcriacao, idresponsavel, tipo ");
        sql.append("from rh_itemhistoricofuncional ");
        sql.append("WHERE idhistoricofuncional = ?");

        parametro.add(idHistorico);
        list = this.execSQL(sql.toString(), parametro.toArray());
        fields.add("idItemHistorico");
        fields.add("idHistoricofuncional");
        fields.add("titulo");
        fields.add("descricao");
        fields.add("dtCriacao");
        fields.add("idResponsavel");
        fields.add("tipo");

        if (list != null && !list.isEmpty()) {
            return this.listConvertion(this.getBean(), list, fields);
        }
        return null;
    }

    public Collection findByIdResponsavel(final Integer id) throws PersistenceException {
        final List parametro = new ArrayList();
        final List fields = new ArrayList();
        List list = new ArrayList();

        final StringBuilder sql = new StringBuilder();
        sql.append("select idusuario, nome ");
        sql.append("from usuario ");
        sql.append("WHERE idusuario = ?");

        parametro.add(id);
        list = this.execSQL(sql.toString(), parametro.toArray());
        fields.add("idResponsavel");
        fields.add("nomeResponsavel");

        if (list != null && !list.isEmpty()) {
            return this.listConvertion(this.getBean(), list, fields);
        }
        return null;
    }

    @Override
    public Collection find(final IDto obj) throws PersistenceException {
        return null;
    }

    @Override
    public Collection list() throws PersistenceException {
        return null;
    }

    /**
     * @param idCandidato
     * @param idResponsavel
     * @param titulo
     *            - Titulo do Item Historico Funcional
     * @param descricao
     *            - Descricao do Item Historico Funcional
     * @param tc
     *
     * @author david.silva
     *
     */
    public void inserirRegistroHistoricoAutomatico(final Integer idCandidato, final Integer idResponsavel, final String titulo, final String descricao,
            final TransactionControler tc) throws PersistenceException {
        HistoricoFuncionalDTO historicoFuncionalDto = new HistoricoFuncionalDTO();
        final ItemHistoricoFuncionalDTO itemHistoricoFuncionalDto = new ItemHistoricoFuncionalDTO();

        final HistoricoFuncionalDao historicoFuncionalDao = new HistoricoFuncionalDao();
        final ItemHistoricoFuncionalDao itemHistoricoFuncionalDao = new ItemHistoricoFuncionalDao();

        if (tc != null) {
            historicoFuncionalDao.setTransactionControler(tc);
            itemHistoricoFuncionalDao.setTransactionControler(tc);
        }

        historicoFuncionalDto = historicoFuncionalDao.restoreByIdCandidato(idCandidato);

        if (historicoFuncionalDto != null) {
            itemHistoricoFuncionalDto.setIdHistoricoFuncional(historicoFuncionalDto.getIdHistoricoFuncional());
            itemHistoricoFuncionalDto.setDtCriacao(UtilDatas.getDataAtual());
            itemHistoricoFuncionalDto.setIdResponsavel(idResponsavel);
            itemHistoricoFuncionalDto.setTitulo(titulo);
            itemHistoricoFuncionalDto.setDescricao(descricao);
            itemHistoricoFuncionalDto.setTipo("A");

            itemHistoricoFuncionalDao.create(itemHistoricoFuncionalDto);
        }
    }

}
