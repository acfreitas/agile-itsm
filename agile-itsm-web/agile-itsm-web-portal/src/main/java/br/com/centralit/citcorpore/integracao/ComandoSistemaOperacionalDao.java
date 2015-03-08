package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ComandoSistemaOperacionalDTO;
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
public class ComandoSistemaOperacionalDao extends CrudDaoDefaultImpl {

    public ComandoSistemaOperacionalDao() {
	super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @SuppressWarnings("rawtypes")
    public Class getBean() {
	return ComandoSistemaOperacionalDTO.class;
    }

    public Collection<Field> getFields() {
	Collection<Field> listFields = new ArrayList<>();

	listFields.add(new Field("ID", "id", true, true, false, false) );
	listFields.add(new Field("IDCOMANDO", "idComando", false, false, false, false) );
	listFields.add(new Field("IDSISTEMAOPERACIONAL", "idSistemaOperacional", false, false, false, false) );
	listFields.add(new Field("COMANDO", "comando", false, false, false, false, "Comando!") );

	return listFields;
    }

    public String getTableName() {
	return "COMANDOSISTEMAOPERACIONAL";
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Collection find(IDto obj) throws PersistenceException {
	List ordem = new ArrayList();
	ordem.add(new Order("comando"));
	return super.find(obj, ordem);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Collection list() throws PersistenceException {
	List list = new ArrayList();
	list.add(new Order("comando"));
	return super.list(list);
    }

    public ComandoSistemaOperacionalDTO pegarComandoSO(String so, String tipoExecucao) throws PersistenceException {
	String sql = "select cso.comando from comandosistemaoperacional cso join comando c on c.id = cso.idcomando "
		+ "join sistemaoperacional so on so.id = cso.idsistemaoperacional where upper(so.nome) = ? and upper(c.descricao) = ?";
	List dados = this.execSQL(sql, new Object[] { so, tipoExecucao });
	List fields = new ArrayList();
	fields.add("comando");
	List result = this.listConvertion(ComandoSistemaOperacionalDTO.class, dados, fields);
	if (result != null && result.size() > 0) {
	    return (ComandoSistemaOperacionalDTO) result.get(0);
	}
	return null;
    }

}
