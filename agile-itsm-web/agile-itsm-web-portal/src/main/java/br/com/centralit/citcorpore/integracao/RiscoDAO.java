package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.RiscoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class RiscoDAO extends CrudDaoDefaultImpl{

	public RiscoDAO(String aliasDB, Usuario usuario) {
		super(aliasDB, usuario);
	}

	public RiscoDAO() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	@Override
	public Collection find(IDto obj) throws PersistenceException {
		List ordem = new ArrayList();
		ordem.add(new Order("nomeRisco"));
		return super.find(obj, ordem);
	}

	@Override
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();

		listFields.add(new Field("idRisco", "idRisco", true, true, false, true));
		listFields.add(new Field("nomeRisco", "nomeRisco", false, false, false, false));
		listFields.add(new Field("detalhamento", "detalhamento", false, false, false, false));
		listFields.add(new Field("nivelRisco", "nivelRisco", false, false, false, false));
		listFields.add(new Field("dataInicio", "dataInicio", false, false, false, false));
		listFields.add(new Field("dataFim", "dataFim", false, false, false, false));

		return listFields;
	}

	@Override
	public String getTableName() {
		return "risco";
	}

	@Override
	public Collection list() throws PersistenceException {
		List list = new ArrayList();
		list.add(new Order("nomeRisco"));
		return super.list(list);
	}

	@Override
	public Class getBean() {
		return RiscoDTO.class;
	}

	public boolean jaExisteRegistroComMesmoNome(RiscoDTO risco)throws PersistenceException {
		ArrayList<Condition> condicoes = new ArrayList<Condition>();
		condicoes.add(new Condition("nomeRisco", "=", risco.getNomeRisco() ));
		Collection retorno = null;
		retorno = super.findByCondition(condicoes, null);
		if(retorno != null){
			if(retorno.size() > 0){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}
	
	public boolean existeNoBanco(RiscoDTO risco) throws PersistenceException {
		
		//List fields = new ArrayList();
		List list = new ArrayList();
		String sql = "select * from risco where nomerisco = '"+risco.getNomeRisco()+"' and datafim is null ORDER BY nomerisco ";

		list = this.execSQL(sql, null);
		
		if(list.size()>0) return true;
		
		return false;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ArrayList<RiscoDTO> riscoAtivo() throws PersistenceException {
		List fields = new ArrayList();
		List list = new ArrayList();
		StringBuilder sql =  new StringBuilder();
		sql.append("SELECT idrisco, nomerisco FROM risco WHERE datafim is null ORDER BY nomerisco ");
		
		fields.add("idRisco");
		fields.add("nomeRisco");
		
		list = this.execSQL(sql.toString(), null);
		return (ArrayList<RiscoDTO>) this.listConvertion(getBean(), list, fields);
	}
	
}
