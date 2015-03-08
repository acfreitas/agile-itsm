package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.HistManualFuncaoDTO;
import br.com.centralit.citcorpore.rh.bean.ManualFuncaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

public class HistManualFuncaoDao extends CrudDaoDefaultImpl {

    public HistManualFuncaoDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idhistManualFuncao", "idhistManualFuncao", true, true, false, false));
        listFields.add(new Field("idManualFuncao", "idManualFuncao", false, false, false, false));
        listFields.add(new Field("tituloCargo", "tituloCargo", false, false, false, false));
        listFields.add(new Field("tituloFuncao", "tituloFuncao", false, false, false, false));
        listFields.add(new Field("resumoFuncao", "resumoFuncao", false, false, false, false));
        listFields.add(new Field("CBO", "codCBO", false, false, false, false));
        listFields.add(new Field("codigo", "codigo", false, false, false, false));
        listFields.add(new Field("idFormacaoRA", "idFormacaoRA", false, false, false, false));
        listFields.add(new Field("idIdiomaRA", "idIdiomaRA", false, false, false, false));
        listFields.add(new Field("idNivelEscritaRA", "idNivelEscritaRA", false, false, false, false));
        listFields.add(new Field("idNivelLeituraRA", "idNivelLeituraRA", false, false, false, false));
        listFields.add(new Field("idNivelConversaRA", "idNivelConversaRA", false, false, false, false));
        listFields.add(new Field("expAnteriorRA", "expAnteriorRA", false, false, false, false));
        listFields.add(new Field("idFormacaoRF", "idFormacaoRF", false, false, false, false));
        listFields.add(new Field("idIdiomaRF", "idIdiomaRF", false, false, false, false));
        listFields.add(new Field("idNivelEscritaRF", "idNivelEscritaRF", false, false, false, false));
        listFields.add(new Field("idNivelLeituraRF", "idNivelLeituraRF", false, false, false, false));
        listFields.add(new Field("idNivelConversaRF", "idNivelConversaRF", false, false, false, false));
        listFields.add(new Field("expAnteriorRF", "expAnteriorRF", false, false, false, false));
        listFields.add(new Field("pesoComplexidade", "pesoComplexidade", false, false, false, false));
        listFields.add(new Field("pesoTecnica", "pesoTecnica", false, false, false, false));
        listFields.add(new Field("pesoComportamental", "pesoComportamental", false, false, false, false));
        listFields.add(new Field("pesoResultados", "pesoResultados", false, false, false, false));
        listFields.add(new Field("dataAlteracao", "dataAlteracao", false, false, false, false));
        listFields.add(new Field("horaAlteracao", "horaAlteracao", false, false, false, false));
        listFields.add(new Field("idUsuarioAlteracao", "idUsuarioAlteracao", false, false, false, false));
        listFields.add(new Field("versao", "versao", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return this.getOwner() + "rh_histmanualFuncao";
    }

    @Override
    public Collection find(final IDto obj) throws PersistenceException {
        return null;
    }

    @Override
    public Collection list() throws PersistenceException {
        return null;
    }

    @Override
    public Class getBean() {
        return HistManualFuncaoDTO.class;
    }

    public Collection findByIdManualFuncao(final int idManualFuncal) throws PersistenceException {
        final List parametro = new ArrayList();
        final List listRetorno = new ArrayList();
        List list = new ArrayList();
        final StringBuilder sql = new StringBuilder();

        sql.append(" SELECT  idManualFuncao, tituloCargo, tituloFuncao, ");
        sql.append(" CBO, codigo, versao, idhistManualFuncao FROM RH_HISTMANUALFUNCAO ");
        sql.append(" where idManualFuncao = ? order by idhistManualFuncao desc");

        parametro.add(idManualFuncal);

        list = this.execSQL(sql.toString(), parametro.toArray());

        // listRetorno.add("idhistManualFuncao");
        listRetorno.add("idManualFuncao");
        listRetorno.add("tituloCargo");
        listRetorno.add("tituloFuncao");
        listRetorno.add("codCBO");
        listRetorno.add("codigo");
        listRetorno.add("versao");
        listRetorno.add("idhistManualFuncao");

        if (list != null && !list.isEmpty()) {
            return this.listConvertion(this.getBean(), list, listRetorno);
        }
        return null;
    }

    public HistManualFuncaoDTO maxIdHistorico(final ManualFuncaoDTO manualFuncao) throws PersistenceException {
        final List parametro = new ArrayList();
        final List listRetorno = new ArrayList();
        final StringBuilder sql = new StringBuilder();

        sql.append("SELECT MAX(idhistmanualfuncao) AS idhistmanualfuncao FROM " + this.getTableName() + " WHERE idmanualfuncao = ?");
        parametro.add(manualFuncao.getIdManualFuncao());
        final List resultado = this.execSQL(sql.toString(), parametro.toArray());
        listRetorno.add("idhistManualFuncao");
        final List result = this.listConvertion(HistManualFuncaoDTO.class, resultado, listRetorno);
        return (HistManualFuncaoDTO) result.get(0);
    }

}
