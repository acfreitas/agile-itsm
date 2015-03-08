package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ComandoDTO;
import br.com.centralit.citcorpore.bean.JustificativaAcaoCurriculoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

/**
 * @author ygor.magalhaes
 *
 */
public class JustificativaAcaoCurriculoDao extends CrudDaoDefaultImpl {

    public JustificativaAcaoCurriculoDao() {
	super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @SuppressWarnings("rawtypes")
    public Class getBean() {
	return JustificativaAcaoCurriculoDTO.class;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Collection<Field> getFields() {
	Collection<Field> listFields = new ArrayList<>();

	listFields.add(new Field("idJustificativaAcaoCurriculo", "idJustificativaAcaoCurriculo", true, true, false, false));
	listFields.add(new Field("nomeJustificativaAcaoCurriculo", "nomeJustificativaAcaoCurriculo", false, false, false, true,"Descrição!"));

	return listFields;
    }

    public String getTableName() {
	return "rh_justificativaacaocurriculo";
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Collection find(IDto obj) throws PersistenceException {
	List ordem = new ArrayList();
	ordem.add(new Order("nomeJustificativaAcaoCurriculo"));
	return super.find(obj, ordem);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Collection list() throws PersistenceException {
	List list = new ArrayList();
	list.add(new Order("nomeJustificativaAcaoCurriculo"));
	return super.list(list);
    }
    
	public ComandoDTO listItemCadastrado(String descricao) throws PersistenceException {
		List parametro = new ArrayList();
		List fields = new ArrayList();
		List list = new ArrayList();
		String sql = "select descricao from " + getTableName() + " where nomeJustificativaAcaoCurriculo LIKE ? ";
		parametro.add(descricao);
		list = this.execSQL(sql, parametro.toArray());
		fields.add("nomeJustificativaAcaoCurriculo");
		if (list.isEmpty()) {
			return null;
		} else {
			return (ComandoDTO) this.listConvertion(getBean(), list, fields).get(0);

		}
	}
    

}
