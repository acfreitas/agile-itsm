package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ControleContratoDTO;
import br.com.centralit.citcorpore.bean.ControleContratoPagamentoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

/**
 * @author Pedro
 *
 */
public class ControleContratoPagamentoDao extends CrudDaoDefaultImpl {

    public ControleContratoPagamentoDao() {
	super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @SuppressWarnings("rawtypes")
    public Class getBean() {
	return ControleContratoPagamentoDTO.class;
    }

    public Collection<Field> getFields() {
	Collection<Field> listFields = new ArrayList<>();

	listFields.add(new Field("idccpagamento", "idCcPagamento", true, true, false, false));
	listFields.add(new Field("parcelaccpagamento", "parcelaCcPagamento", false, false, false, false));
	listFields.add(new Field("idcontrolecontrato", "idControleContrato", false, false, false, false));
	listFields.add(new Field("dataatrasoccpagamento", "dataAtrasoCcPagamento", false, false, false, false));
	listFields.add(new Field("dataccpagamento", "dataCcPagamento", false, false, false, false));



	return listFields;
    }

    public String getTableName() {
	return "CONTROLECONTRATOPAGAMENTO";
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Collection find(IDto obj) throws PersistenceException {
	List ordem = new ArrayList();
	ordem.add(new Order("nomeModuloSistema"));
	return super.find(obj, ordem);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Collection list() throws PersistenceException {
	List list = new ArrayList();
	list.add(new Order("nomeModuloSistema"));
	return super.list(list);
    }

    private static final String SQL_DELETE =
	          "DELETE FROM CONTROLECONTRATOPAGAMENTO WHERE idcontrolecontrato = ? ";

	public void deleteByIdControleContrato(ControleContratoDTO controleContrato)
		    throws PersistenceException {
		        super.execUpdate(SQL_DELETE, new Object[]{controleContrato.getIdControleContrato()});
		    }

	 private static final String SQL_FIND =
		      "SELECT * FROM CONTROLECONTRATOPAGAMENTO WHERE idcontrolecontrato = ? ";

	public Collection findByIdControleContrato(ControleContratoPagamentoDTO dto) throws PersistenceException {
       return super.listConvertion(getBean(),
               super.execSQL(SQL_FIND, new Object[]{dto.getIdControleContrato()}),
               new ArrayList(getFields()));
}
}
