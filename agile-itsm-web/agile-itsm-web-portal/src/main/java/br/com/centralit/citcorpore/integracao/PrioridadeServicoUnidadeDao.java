package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.PrioridadeServicoUnidadeDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class PrioridadeServicoUnidadeDao extends CrudDaoDefaultImpl {
    public PrioridadeServicoUnidadeDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }
    @Override
    public Collection<Field> getFields() {
        Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idUnidade" ,"idUnidade", true, false, false, false));
        listFields.add(new Field("idServicoContrato" ,"idServicoContrato", true, false, false, false));
        listFields.add(new Field("idPrioridade" ,"idPrioridade", false, false, false, false));
        listFields.add(new Field("dataInicio" ,"dataInicio", false, false, false, false));
        listFields.add(new Field("dataFim" ,"dataFim", false, false, false, false));
        return listFields;
    }
    @Override
    public String getTableName() {
        return this.getOwner() + "PrioridadeServicoUnidade";
    }
    @Override
    public Collection list() throws PersistenceException {
        return null;
    }

    @Override
    public Class getBean() {
        return PrioridadeServicoUnidadeDTO.class;
    }
    @Override
    public Collection find(IDto arg0) throws PersistenceException {
        return null;
    }

    public Collection findByIdServicoContrato(Integer idServicoContrato) throws PersistenceException {
        List condicao = new ArrayList();
        List ordenacao = new ArrayList();

        condicao.add(new Condition("idServicoContrato", "=", idServicoContrato));
        ordenacao.add(new Order("idUnidade"));

        return super.findByCondition(condicao, ordenacao);
    }

    public PrioridadeServicoUnidadeDTO restore(Integer idServicoContrato, Integer idUnidade) throws PersistenceException {

        List parametro = new ArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append(" select idunidade,idservicocontrato,idprioridade,datainicio from prioridadeservicounidade where  idservicocontrato = ?  and idunidade = ? ");
        parametro.add(idServicoContrato);
        parametro.add(idUnidade);
        List lista = this.execSQL(sql.toString(), parametro.toArray());
        List<String> listRetorno = new ArrayList<String>();
        listRetorno.add("idUnidade");
        listRetorno.add("idServicoContrato");
        listRetorno.add("idPrioridade");
        listRetorno.add("dataInicio");
        List result = engine.listConvertion(PrioridadeServicoUnidadeDTO.class, lista, listRetorno);
        if(result != null && !result.isEmpty()){
            return (PrioridadeServicoUnidadeDTO) result.get(0);
        } else{
            return null;
        }
    }

}
