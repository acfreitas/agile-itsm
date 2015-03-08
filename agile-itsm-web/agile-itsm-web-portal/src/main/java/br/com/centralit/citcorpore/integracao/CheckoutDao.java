package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.CheckoutDTO;
import br.com.centralit.citcorpore.bean.result.QuantidadeResultDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

public class CheckoutDao extends CrudDaoDefaultImpl {

    public CheckoutDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<CheckoutDTO> find(final IDto obj) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<CheckoutDTO> list() throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<Field>();
        listFields.add(new Field("idcheckout", "idcheckout", true, true, false, false));
        listFields.add(new Field("idsolicitacao", "idSolicitacao", false, false, false, false));
        listFields.add(new Field("idtarefa", "idTarefa", false, false, false, false));
        listFields.add(new Field("idusuario", "idUsuario", false, false, false, false));
        listFields.add(new Field("status", "status", false, false, false, false));
        listFields.add(new Field("latitude", "latitude", false, false, false, false));
        listFields.add(new Field("longitude", "longitude", false, false, false, false));
        listFields.add(new Field("datahoracheckout", "dataHoraCheckout", false, false, false, false));
        return listFields;
    }

    /**
     * Retorna as quantidades existentes no banco para checkin e checkout. O primeiro objeto da lista será sempre a quantidade de checkins
     *
     * @return {@link List} lista com as quantidades
     * @throws Exception
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 16/11/2014
     */
    public List<QuantidadeResultDTO> listQuantidadesCheckoutAndCheckin(final CheckoutDTO checkout) throws PersistenceException {
        final StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(chkin.idsolicitacao) AS quantidade ");
        sql.append("FROM   checkin chkin ");
        sql.append("WHERE  chkin.idsolicitacao = ? ");
        sql.append("       AND chkin.idtarefa = ? ");
        sql.append("       AND chkin.idusuario = ? ");
        sql.append("UNION ALL ");
        sql.append("SELECT COUNT(chkout.idsolicitacao) AS quantidade ");
        sql.append("FROM   checkout chkout ");
        sql.append("WHERE  chkout.idsolicitacao = ? ");
        sql.append("       AND chkout.idtarefa = ? ");
        sql.append("       AND chkout.idusuario = ? ");

        List<QuantidadeResultDTO> quantidades = new ArrayList<>();

        final Integer idSolicitacao = checkout.getIdSolicitacao();
        final Integer idTarefa = checkout.getIdTarefa();
        final Integer idUsuario = checkout.getIdUsuario();

        final List<?> result = this.execSQL(sql.toString(), new Object[] {idSolicitacao, idTarefa, idUsuario, idSolicitacao, idTarefa, idUsuario});

        if (result.size() > 0) {
            final List<String> fields = new ArrayList<>();
            fields.add("quantidade");
            quantidades = this.listConvertion(QuantidadeResultDTO.class, result, fields);
        }

        return quantidades;
    }

    @Override
    public String getTableName() {
        return "checkout";
    }

    @Override
    public Class<CheckoutDTO> getBean() {
        return CheckoutDTO.class;
    }

}
