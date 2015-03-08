package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ContatoProblemaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;


@SuppressWarnings({ "rawtypes", "unchecked" })
public class ContatoProblemaDAO extends CrudDaoDefaultImpl {

	public ContatoProblemaDAO() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();

		listFields.add(new Field("idcontatoproblema", "idcontatoproblema",
				true, true, false, false));
		listFields.add(new Field("nomecontato", "nomecontato", false, false,
				false, false));
		listFields.add(new Field("telefonecontato", "telefonecontato", false,
				false, false, false));
		listFields.add(new Field("emailcontato", "emailcontato", false, false,
				false, false));
		listFields.add(new Field("observacao", "observacao", false, false,
				false, false));
		listFields.add(new Field("idlocalidade", "idLocalidade", false, false,
				false, false));
		listFields.add(new Field("ramal", "ramal", false, false, false, false));

		return listFields;
	}

	@Override
	public String getTableName() {

		return "contatoproblema";
	}

	@Override
	public Collection find(IDto obj) throws PersistenceException {
				return null;
	}

	@Override
	public Collection list() throws PersistenceException {
				return null;
	}

	@Override
	public Class getBean() {
		return ContatoProblemaDTO.class;
	}
	
	public ContatoProblemaDTO restoreById(Integer id) throws PersistenceException {
		List ordem = new ArrayList();
		ContatoProblemaDTO contatoProblemaDto = new ContatoProblemaDTO();
		contatoProblemaDto.setIdContatoProblema(id);
		List col = (List) super.find(contatoProblemaDto, ordem);
		if (col == null || col.size() == 0)
			return null;
		return (ContatoProblemaDTO) col.get(0);
	}
	
	public ContatoProblemaDTO restoreById(ContatoProblemaDTO obj) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idcontatoproblema", "=", obj.getIdContatoProblema()));
		ordenacao.add(new Order("nomecontato"));
		Collection<ContatoProblemaDTO> col =  super.findByCondition(condicao, ordenacao);
		if (col == null || col.size() == 0)
			return null;
		return (ContatoProblemaDTO) ((ArrayList) col).get(0);
		
	}
	
	
}
