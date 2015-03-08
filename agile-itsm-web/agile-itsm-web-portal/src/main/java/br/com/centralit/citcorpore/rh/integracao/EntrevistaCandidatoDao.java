package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.EntrevistaCandidatoDTO;
import br.com.centralit.citcorpore.rh.bean.TriagemRequisicaoPessoalDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class EntrevistaCandidatoDao extends CrudDaoDefaultImpl {

    public EntrevistaCandidatoDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection find(final IDto arg0) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();

        listFields.add(new Field("idEntrevista", "idEntrevista", true, true, false, false));
        listFields.add(new Field("idCurriculo", "idCurriculo", false, false, false, false));
        listFields.add(new Field("idEntrevistadorRH", "idEntrevistadorRH", false, false, false, false));
        listFields.add(new Field("idEntrevistadorGestor", "idEntrevistadorGestor", false, false, false, false));
        listFields.add(new Field("idTriagem", "idTriagem", false, false, false, false));
        listFields.add(new Field("dataHora", "dataHora", false, false, false, false));
        listFields.add(new Field("caracteristicas", "caracteristicas", false, false, false, false));
        listFields.add(new Field("trabalhoEmEquipe", "trabalhoEmEquipe", false, false, false, false));
        listFields.add(new Field("possuiOutraAtividade", "possuiOutraAtividade", false, false, false, false));
        listFields.add(new Field("outraAtividade", "outraAtividade", false, false, false, false));
        listFields.add(new Field("concordaExclusividade", "concordaExclusividade", false, false, false, false));
        listFields.add(new Field("salarioAtual", "salarioAtual", false, false, false, false));
        listFields.add(new Field("pretensaoSalarial", "pretensaoSalarial", false, false, false, false));
        listFields.add(new Field("dataDisponibilidade", "dataDisponibilidade", false, false, false, false));
        listFields.add(new Field("competencias", "competencias", false, false, false, false));
        listFields.add(new Field("observacoes", "observacoes", false, false, false, false));
        listFields.add(new Field("resultado", "resultado", false, false, false, false));
        listFields.add(new Field("planoCarreira", "planoCarreira", false, false, false, false));
        listFields.add(new Field("metodosAdicionais", "metodosAdicionais", false, false, false, false));
        listFields.add(new Field("notaAvaliacao", "notaAvaliacao", false, false, false, false));
        listFields.add(new Field("notaGestor", "notaGestor", false, false, false, false));
        listFields.add(new Field("classificacao", "classificacao", false, false, false, false));
        listFields.add(new Field("adimitido", "adimitido", false, false, false, false));
        listFields.add(new Field("observacaogestor", "observacaoGestor", false, false, false, false));

        return listFields;
    }

    @Override
    public String getTableName() {
        return "RH_EntrevistaCandidato";
    }

    @Override
    public Class getBean() {
        return EntrevistaCandidatoDTO.class;
    }

    @Override
    public Collection list() throws PersistenceException {
        final List list = new ArrayList();
        list.add(new Order("idCurriculo"));
        return super.list(list);
    }

    public EntrevistaCandidatoDTO findByIdTriagemAndIdCurriculo(final Integer idTriagem, final Integer idCurriculo) throws PersistenceException {
        final List condicao = new ArrayList();
        final List ordenacao = new ArrayList();
        condicao.add(new Condition("idTriagem", "=", idTriagem));
        condicao.add(new Condition("idCurriculo", "=", idCurriculo));
        ordenacao.add(new Order("idTriagem"));
        final List<EntrevistaCandidatoDTO> result = (List<EntrevistaCandidatoDTO>) super.findByCondition(condicao, ordenacao);
        if (result != null && !result.isEmpty()) {
            return result.get(0);
        } else {
            return null;
        }

    }

    public Collection findByIdTriagemAndResultado(final Integer idTriagem, final String resultado) throws PersistenceException {
        final List condicao = new ArrayList();
        final List ordenacao = new ArrayList();
        condicao.add(new Condition("idTriagem", "=", idTriagem));
        condicao.add(new Condition("resultado", "=", resultado));
        ordenacao.add(new Order("idTriagem"));
        return super.findByCondition(condicao, ordenacao);
    }

    public Collection findFinalizadasByIdTriagemAndResultado(final Integer idTriagem, final String resultado) throws PersistenceException {
        final List condicao = new ArrayList();
        final List ordenacao = new ArrayList();
        condicao.add(new Condition("idEntrevistadorRH", ">", new Integer(0)));
        condicao.add(new Condition("idEntrevistadorGestor", ">", new Integer(0)));
        condicao.add(new Condition("idTriagem", "=", idTriagem));
        condicao.add(new Condition("resultado", "=", resultado));
        ordenacao.add(new Order("idTriagem"));
        return super.findByCondition(condicao, ordenacao);
    }

    public Collection listCurriculosAprovadosPorOrdemMaiorNota(final Integer idSocilitacao) throws PersistenceException {
        final List listRetorno = (ArrayList) this.getFields();

        final List parametro = new ArrayList();
        final StringBuilder sb = new StringBuilder();

        sb.append("SELECT ec.idEntrevista, ec.idCurriculo, ec.idEntrevistadorRH, ec.idEntrevistadorGestor, ec.idTriagem, ec.dataHora, ec.caracteristicas, ec.trabalhoEmEquipe, ec.possuiOutraAtividade, ec.outraAtividade, ec.concordaExclusividade, ec.salarioAtual, ec.pretensaoSalarial, ec.dataDisponibilidade, ec.competencias, ec.observacoes, ec.resultado, ec.planoCarreira, ec.metodosAdicionais, ec.notaAvaliacao, ec.notaGestor, ec.classificacao, ec.adimitido, ec.observacaogestor ");
        sb.append("FROM rh_entrevistacandidato ec join rh_triagemrequisicaopessoal trp ON ec.idtriagem=trp.idtriagem and trp.idsolicitacaoservico=? AND ec.resultado = 'A' ");
        sb.append("ORDER BY	((CASE WHEN ec.notaAvaliacao IS NULL THEN 0 ELSE ec.notaAvaliacao END + CASE WHEN ec.notaGestor IS NULL THEN 0 ELSE ec.notaGestor END)/2) DESC");

        parametro.add(idSocilitacao);

        final List list = this.execSQL(sb.toString(), parametro.toArray());

        if (list != null && !list.isEmpty()) {
            return this.listConvertion(EntrevistaCandidatoDTO.class, list, listRetorno);
        }
        return null;
    }

    @Override
    public void updateNotNull(final IDto obj) throws PersistenceException {
        super.updateNotNull(obj);
    }

    public Boolean seCandidatoAprovado(final TriagemRequisicaoPessoalDTO triagemRequisicaoPessoalDTO) throws PersistenceException {
        final List parametro = new ArrayList();
        final String sql = "SELECT * FROM rh_entrevistacandidato WHERE idtriagem = ? and resultado = 'A' ";

        if (triagemRequisicaoPessoalDTO != null && triagemRequisicaoPessoalDTO.getIdTriagem() != null) {
            parametro.add(triagemRequisicaoPessoalDTO.getIdTriagem());
        }
        final List list = this.execSQL(sql.toString(), parametro.toArray());
        if (list == null || list.size() == 0) {
            return false;
        }
        return true;
    }

}
