package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.BlackListDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

/**
 * @author david.silva
 *
 */
public class BlackListDao extends CrudDaoDefaultImpl {

    private static final String TABLE_NAME = "rh_listanegra";

    public BlackListDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Class getBean() {
        return BlackListDTO.class;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();

        listFields.add(new Field("IDLISTANEGRA", "idListaNegra", true, true, false, false));
        listFields.add(new Field("IDCANDIDATO", "idCandidato", false, false, false, false));
        listFields.add(new Field("IDJUSTIFICATIVA", "idJustificativa", false, false, false, false));
        listFields.add(new Field("IDRESPONSAVEL", "idResponsavel", false, false, false, false));
        listFields.add(new Field("DESCRICAO", "descricao", false, false, false, false));
        listFields.add(new Field("DATAINICIO", "dataInicio", false, false, false, false));
        listFields.add(new Field("DATAFIM", "dataFim", false, false, false, false));

        return listFields;
    }

    public Collection findBlackListByIdCandidato(final Integer idCandidato) throws PersistenceException {
        final List parametro = new ArrayList();
        final List fields = new ArrayList();
        List list = new ArrayList();

        final StringBuilder sql = new StringBuilder();
        sql.append("select idListaNegra, idCandidato from rh_listanegra ");
        sql.append("where idCandidato = ? and datafim is null");

        parametro.add(idCandidato);
        list = this.execSQL(sql.toString(), parametro.toArray());
        fields.add("idListaNegra");
        fields.add("idCandidato");

        if (list != null && !list.isEmpty()) {
            return this.listConvertion(this.getBean(), list, fields);
        }
        return null;
    }

    public Collection findBlackList(final Integer idCandidato) throws PersistenceException {
        final List parametro = new ArrayList();
        final List fields = new ArrayList();
        List list = new ArrayList();

        final StringBuilder sql = new StringBuilder();
        sql.append("select idListaNegra, idCandidato, idJustificativa, idResponsavel, descricao from rh_listanegra ");
        sql.append("where idCandidato = ? and datafim is null");

        parametro.add(idCandidato);
        list = this.execSQL(sql.toString(), parametro.toArray());
        fields.add("idListaNegra");
        fields.add("idCandidato");
        fields.add("idJustificativa");
        fields.add("idResponsavel");
        fields.add("descricao");

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

    public boolean isCandidatoListaNegra(final Integer idCandidato) throws PersistenceException {
        final List listCandidato = (List) this.findBlackListByIdCandidato(idCandidato);
        return listCandidato != null && !listCandidato.isEmpty();
    }

    public BlackListDTO retornaBlackList(final Integer idCandidato) throws PersistenceException {
        final Collection<BlackListDTO> colBlackListDTO = this.findBlackList(idCandidato);
        final List<BlackListDTO> list = new ArrayList<>();
        for (final BlackListDTO objBlackListDTO : colBlackListDTO) {
            list.add(objBlackListDTO);
        }
        return list.get(0);
    }

}
