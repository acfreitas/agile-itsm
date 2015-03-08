package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.rh.bean.ManualFuncaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

/**
 * Classe DAO da tabela rh_manualFuncao. Responsável por editar as informações de RequisicaoFuncao. Vínculo realizado pelo idSolicitacaoServico.
 *
 * @author CentralIT
 *
 */
public class ManualFuncaoDao extends CrudDaoDefaultImpl {

    public ManualFuncaoDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public String getAliasDB() {
        return super.getAliasDB();
    }

    @Override
    public Collection find(final IDto obj) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();

        listFields.add(new Field("idManualFuncao", "idManualFuncao", true, true, false, false));
        listFields.add(new Field("idRequisicaoFuncao", "idRequisicaoFuncao", false, false, false, false));
        listFields.add(new Field("idCargo", "idCargo", false, false, false, false));

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

        return listFields;
    }

    @Override
    public String getTableName() {
        return "rh_manualFuncao";
    }

    @Override
    public Collection list() throws PersistenceException {
        return null;
    }

    @Override
    public Class getBean() {
        return ManualFuncaoDTO.class;
    }

}
