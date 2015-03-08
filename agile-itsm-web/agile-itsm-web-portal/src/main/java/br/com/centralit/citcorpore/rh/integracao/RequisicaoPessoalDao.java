package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.RequisicaoPessoalDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class RequisicaoPessoalDao extends CrudDaoDefaultImpl {

    public RequisicaoPessoalDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<RequisicaoPessoalDTO> find(final IDto arg0) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idSolicitacaoServico", "idSolicitacaoServico", true, false, false, false));
        listFields.add(new Field("idCargo", "idCargo", false, false, false, false));
        listFields.add(new Field("idFuncao", "idFuncao", false, false, false, false));
        listFields.add(new Field("vagas", "vagas", false, false, false, false));
        listFields.add(new Field("tipoContratacao", "tipoContratacao", false, false, false, false));
        listFields.add(new Field("motivoContratacao", "motivoContratacao", false, false, false, false));
        listFields.add(new Field("salario", "salario", false, false, false, false));
        listFields.add(new Field("idCentroCusto", "idCentroCusto", false, false, false, false));
        listFields.add(new Field("idProjeto", "idProjeto", false, false, false, false));
        listFields.add(new Field("idParecerValidacao", "idParecerValidacao", false, false, false, false));
        listFields.add(new Field("rejeitada", "rejeitada", false, false, false, false));
        listFields.add(new Field("confidencial", "confidencial", false, false, false, false));
        listFields.add(new Field("beneficios", "beneficios", false, false, false, false));
        listFields.add(new Field("folgas", "folgas", false, false, false, false));
        listFields.add(new Field("idJornada", "idJornada", false, false, false, false));
        listFields.add(new Field("idCidade", "idCidade", false, false, false, false));
        listFields.add(new Field("idUnidade", "idUnidade", false, false, false, false));
        listFields.add(new Field("idlotacao", "idLotacao", false, false, false, false));
        listFields.add(new Field("preRequisitoEntrevistaGestor", "preRequisitoEntrevistaGestor", false, false, false, false));
        listFields.add(new Field("idUf", "idUf", false, false, false, false));
        listFields.add(new Field("idPais", "idPais", false, false, false, false));
        listFields.add(new Field("qtdCandidatosAprovados", "qtdCandidatosAprovados", false, false, false, false));
        listFields.add(new Field("observacoes", "observacoes", false, false, false, false));
        listFields.add(new Field("justificativaRejeicao", "justificativaRejeicao", false, false, false, false));
        listFields.add(new Field("motivoDesistenciaCandidato", "motivoDesistenciaCandidato", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return "RH_RequisicaoPessoal";
    }

    @Override
    public Class<RequisicaoPessoalDTO> getBean() {
        return RequisicaoPessoalDTO.class;
    }

    @Override
    public Collection<RequisicaoPessoalDTO> list() throws PersistenceException {
        final List<Order> list = new ArrayList<>();
        list.add(new Order("idFuncao"));
        return super.list(list);
    }

    @Override
    public void updateNotNull(final IDto obj) throws PersistenceException {
        super.updateNotNull(obj);
    }

}
