package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ControleContratoDTO;
import br.com.centralit.citcorpore.bean.ControleContratoVersaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

/**
 * @author Pedro
 *
 */
public class ControleContratoVersaoDao extends CrudDaoDefaultImpl {

    public ControleContratoVersaoDao() {
	super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @SuppressWarnings("rawtypes")
    public Class getBean() {
	return ControleContratoVersaoDTO.class;
    }

    public Collection<Field> getFields() {

	Collection<Field> listFields = new ArrayList<>();
	listFields.add(new Field("idcontrolecontrato", "idControleContrato", false, false, false, false));
	listFields.add(new Field("idccversao", "idCcVersao", true, true, false, false));
	listFields.add(new Field("nomeccversao", "nomeCcVersao", false, false, false, false));

	return listFields;
    }

    public String getTableName() {
	return "CONTROLECONTRATOVERSAO";
    }

    @SuppressWarnings({ "rawtypes" })
    public Collection find(IDto obj) throws PersistenceException {
	List ordem = new ArrayList();
	return super.find(obj, ordem);
    }

    @SuppressWarnings({ "rawtypes" })
    public Collection list() throws PersistenceException {
	List list = new ArrayList();
	return super.list(list);
    }

    private static final String SQL_DELETE =
	          "DELETE FROM CONTROLECONTRATOVERSAO WHERE idcontrolecontrato = ? ";
    private static final String SQL_FIND =
		      "SELECT * FROM CONTROLECONTRATOVERSAO WHERE idcontrolecontrato = ? ";

	public void deleteByIdControleContrato(ControleContratoDTO controleContrato)
		    throws PersistenceException {
		        super.execUpdate(SQL_DELETE, new Object[]{controleContrato.getIdControleContrato()});
		    }

	public Collection findByIdControleContrato(ControleContratoVersaoDTO controleContratoVersaoDTO) throws PersistenceException {
        return super.listConvertion(getBean(),
                super.execSQL(SQL_FIND, new Object[]{controleContratoVersaoDTO.getIdControleContrato()}),
                new ArrayList(getFields()));
}

}
