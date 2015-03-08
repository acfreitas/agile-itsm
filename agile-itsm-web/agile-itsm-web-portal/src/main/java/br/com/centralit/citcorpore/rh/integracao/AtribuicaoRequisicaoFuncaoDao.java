package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.AtribuicaoRequisicaoFuncaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class AtribuicaoRequisicaoFuncaoDao extends CrudDaoDefaultImpl {

    public AtribuicaoRequisicaoFuncaoDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection find(final IDto obj) throws PersistenceException {

        return null;
    }

    @Override
    public Collection getFields() {

        final Collection listFields = new ArrayList();

        listFields.add(new Field("IDATRIBUICAO", "idAtribuicao", true, true, false, false));
        listFields.add(new Field("DESCRICAO", "descricao", false, false, false, false));
        listFields.add(new Field("DETALHE", "detalhe", false, false, false, false));
        listFields.add(new Field("DATAINICIO", "dataInicio", false, false, false, false));
        listFields.add(new Field("DATAFIM", "dataFim", false, false, false, false));

        return listFields;
    }

    @Override
    public String getTableName() {
        return "rh_atribuicao";
    }

    @Override
    public Collection list() throws PersistenceException {
        final List list = new ArrayList();
        list.add(new Order("descricao"));
        return super.list(list);
    }

    @Override
    public Class getBean() {
        return AtribuicaoRequisicaoFuncaoDTO.class;
    }

    /**
     * Retorna lista de status de usuário.
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public boolean consultarAtribuicoesAtivas(final AtribuicaoRequisicaoFuncaoDTO obj) throws PersistenceException {
        final List parametro = new ArrayList();
        List list = new ArrayList();
        String sql = "select idatribuicao From " + this.getTableName() + "  where  descricao = ? ";

        if (obj.getIdAtribuicao() != null) {
            sql += " and idatribuicao <> " + obj.getIdAtribuicao();
        }

        parametro.add(obj.getDescricao());
        list = this.execSQL(sql, parametro.toArray());
        if (list != null && !list.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public Collection<AtribuicaoRequisicaoFuncaoDTO> seAtribuicaoJaCadastrada(final AtribuicaoRequisicaoFuncaoDTO atribuicaoRequisicaoFuncaoDTO) throws PersistenceException {
        final List parametro = new ArrayList();
        List list = new ArrayList();
        String sql = "";
        sql = " select lower(descricao) from rh_atribuicao where descricao = lower(?) ";

        parametro.add(atribuicaoRequisicaoFuncaoDTO.getDescricao().trim().toLowerCase());
        list = this.execSQL(sql, parametro.toArray());
        return list;
    }

    public Collection<AtribuicaoRequisicaoFuncaoDTO> listarAtivos() throws PersistenceException {
        final List condicao = new ArrayList();
        final List ordenacao = new ArrayList();

        ordenacao.add(new Order("descricao"));
        condicao.add(new Condition("datafim", "=", null));
        return super.findByCondition(condicao, ordenacao);
    }

}
