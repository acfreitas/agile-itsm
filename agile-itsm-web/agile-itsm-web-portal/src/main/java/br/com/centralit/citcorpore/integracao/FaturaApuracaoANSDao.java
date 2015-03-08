package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.FaturaApuracaoANSDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class FaturaApuracaoANSDao extends CrudDaoDefaultImpl {

    public FaturaApuracaoANSDao() {
	super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    public Collection<Field> getFields() {
	Collection<Field> listFields = new ArrayList<>();
	listFields.add(new Field("idFaturaApuracaoANS", "idFaturaApuracaoANS", true, true, false, false));
	listFields.add(new Field("idFatura", "idFatura", false, false, false, false));
	listFields.add(new Field("idAcordoNivelServicoContrato", "idAcordoNivelServicoContrato", false, false, false, false));
	listFields.add(new Field("valorApurado", "valorApurado", false, false, false, false));
	listFields.add(new Field("detalhamento", "detalhamento", false, false, false, false));
	listFields.add(new Field("percentualGlosa", "percentualGlosa", false, false, false, false));
	listFields.add(new Field("valorGlosa", "valorGlosa", false, false, false, false));
	listFields.add(new Field("dataApuracao", "dataApuracao", false, false, false, false));
	return listFields;
    }

    public String getTableName() {
	return this.getOwner() + "FaturaApuracaoANS";
    }

    public Collection list() throws PersistenceException {
	return null;
    }

    public Class getBean() {
	return FaturaApuracaoANSDTO.class;
    }

    public Collection find(IDto arg0) throws PersistenceException {
	return null;
    }

    public Collection findByIdFatura(Integer parm) throws PersistenceException {
	List condicao = new ArrayList();
	List ordenacao = new ArrayList();
	condicao.add(new Condition("idFatura", "=", parm));
	ordenacao.add(new Order("idFaturaApuracaoANS"));
	return super.findByCondition(condicao, ordenacao);
    }

    public void deleteByIdFatura(Integer parm) throws PersistenceException {
	List condicao = new ArrayList();
	condicao.add(new Condition("idFatura", "=", parm));
	super.deleteByCondition(condicao);
    }

    public FaturaApuracaoANSDTO findByIdAcordoNivelServicoContrato(Integer parm) throws PersistenceException {
	List condicao = new ArrayList();
	List ordenacao = new ArrayList();
	condicao.add(new Condition("idAcordoNivelServicoContrato", "=", parm));
	ordenacao.add(new Order("idFaturaApuracaoANS"));

	List faturas = (List) super.findByCondition(condicao, ordenacao);

	if (faturas != null && !faturas.isEmpty()) {
	    return (FaturaApuracaoANSDTO) faturas.get(0);
	} else {
	    return null;
	}
    }

    public void deleteByIdAcordoNivelServicoContrato(Integer parm) throws PersistenceException {
	List condicao = new ArrayList();
	condicao.add(new Condition("idAcordoNivelServicoContrato", "=", parm));
	super.deleteByCondition(condicao);
    }
}
