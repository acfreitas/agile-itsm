package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.CheckinDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

/**
 * @author maycon.silva
 *
 */
public class CheckinDao extends CrudDaoDefaultImpl {

    public CheckinDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<Field>();
        listFields.add(new Field("idcheckin", "idCheckin", true, true, false, false));
        listFields.add(new Field("idsolicitacao", "idSolicitacao", false, false, false, false));
        listFields.add(new Field("idtarefa", "idTarefa", false, false, false, false));
        listFields.add(new Field("idusuario", "idUsuario", false, false, false, false));
        listFields.add(new Field("latitude", "latitude", false, false, false, false));
        listFields.add(new Field("longitude", "longitude", false, false, false, false));
        listFields.add(new Field("datahoracheckin", "dataHoraCheckin", false, false, false, false));
        return listFields;
    }

    @Override
    public Collection<CheckinDTO> find(final IDto obj) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<CheckinDTO> list() throws PersistenceException {
        return null;
    }

    public List<CheckinDTO> listCheckinDoUsuarioSemCheckout(final UsuarioDTO usuario) throws PersistenceException {
        final StringBuilder sql = new StringBuilder();
        sql.append("SELECT chkin.idsolicitacao ");
        sql.append("FROM   checkin chkin ");
        sql.append("       LEFT JOIN checkout chkout ");
        sql.append("              ON chkout.idsolicitacao = chkin.idsolicitacao ");
        sql.append("                 AND chkout.idtarefa = chkin.idtarefa ");
        sql.append("                 AND chkout.idusuario = chkin.idusuario ");
        sql.append("WHERE  chkout.idsolicitacao IS NULL ");
        sql.append("       AND chkin.idusuario = ? ");
        sql.append("GROUP  BY chkin.idsolicitacao");

        List<CheckinDTO> checkins = new ArrayList<>();
        final List<?> result = this.execSQL(sql.toString(), new Object[] {usuario.getIdUsuario()});

        if (result.size() > 0) {
            final List<String> fields = new ArrayList<>();
            fields.add("idSolicitacao");
            checkins = this.listConvertion(CheckinDTO.class, result, fields);
        }

        return checkins;
    }

    public List<CheckinDTO> listCheckinSolicitacaoSemCheckout(final CheckinDTO checkin) throws PersistenceException {
        final StringBuilder sql = new StringBuilder();
        sql.append("SELECT chkin.idsolicitacao, ");
        sql.append("       emp.nome AS nomeEmpregado ");
        sql.append("FROM   checkin chkin ");
        sql.append("       LEFT JOIN checkout chkout ");
        sql.append("              ON chkout.idsolicitacao = chkin.idsolicitacao ");
        sql.append("                 AND chkout.idtarefa = chkin.idtarefa ");
        sql.append("                 AND chkout.idusuario = chkin.idusuario ");
        sql.append("       LEFT JOIN usuario usu ");
        sql.append("              ON usu.idusuario = chkin.idusuario ");
        sql.append("       LEFT JOIN empregados emp ");
        sql.append("              ON emp.idempregado = usu.idempregado ");
        sql.append("WHERE  chkout.idsolicitacao IS NULL ");
        sql.append("       AND chkin.idsolicitacao = ? ");
        sql.append("GROUP  BY chkin.idsolicitacao, ");
        sql.append("          emp.nome");

        List<CheckinDTO> checkins = new ArrayList<>();
        final List<?> result = this.execSQL(sql.toString(), new Object[] {checkin.getIdSolicitacao()});

        if (result.size() > 0) {
            final List<String> fields = new ArrayList<>();
            fields.add("idsolicitacao");
            fields.add("nomeEmpregado");
            checkins = this.listConvertion(CheckinDTO.class, result, fields);
        }

        return checkins;
    }

    public boolean isCheckinAtivo(final Integer idTarefa, final Integer idSolicitacao, final Integer idUsuario) throws PersistenceException {
        final StringBuilder sql = new StringBuilder();
        sql.append("select idcheckin from checkin ");
        sql.append("where idtarefa = ? and idsolicitacao = ? and idusuario = ? ");

        final List<?> resultado = this.execSQL(sql.toString(), new Object[] {idTarefa, idSolicitacao, idUsuario});

        if (resultado.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public String getTableName() {
        return "checkin";
    }

    @Override
    public Class<CheckinDTO> getBean() {
        return CheckinDTO.class;
    }

}
