package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.RelatorioListaNegraDTO;
import br.com.centralit.citcorpore.bean.SoftwaresListaNegraDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

/**
 * @author ronnie.lopes
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class SoftwaresListaNegraDao extends CrudDaoDefaultImpl {

	public SoftwaresListaNegraDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Class getBean() {
		return SoftwaresListaNegraDTO.class;
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();

		listFields.add(new Field("IDSOFTWARESLISTANEGRA", "idSoftwaresListaNegra", true, true, false, false));
		listFields.add(new Field("NOMESOFTWARESLISTANEGRA", "nomeSoftwaresListaNegra", false, false, false, false));
		return listFields;
	}

	public String getTableName() {
		return "SoftwaresListaNegra";
	}

	public Collection find(IDto obj) throws PersistenceException {
		return null;
	}

	public Collection list() throws PersistenceException {
		List list = new ArrayList();
		list.add(new Order("nomeSoftwaresListaNegra"));
		return super.list(list);
	}

	/**
	 * Retorna String com lista de Softwares da Lista Negra.
	 * 
	 * @return String - Lista de Softwares da lista negra.
	 * @throws Exception
	 * @author ronnie.lopes
	 */
	public String recuperaStringSoftwaresListaNegra() throws PersistenceException {
		List lista = new ArrayList();
		StringBuilder sql = new StringBuilder();
		List  listRetorno = new ArrayList();
		
		listRetorno.add("nomeSoftwaresListaNegra");

		sql.append(" SELECT nomesoftwareslistanegra");
		sql.append(" FROM softwareslistanegra");
		sql.append(" ORDER BY nomesoftwareslistanegra");

		lista = this.execSQL(sql.toString(), null);

		List<SoftwaresListaNegraDTO> listSoftwareListaNegra = this.listConvertion(SoftwaresListaNegraDTO.class, lista, listRetorno);

		StringBuilder texto = new StringBuilder();
		
		if (listSoftwareListaNegra != null && !listSoftwareListaNegra.isEmpty()) {

			for(SoftwaresListaNegraDTO palavra : listSoftwareListaNegra) {
				texto.append(palavra.getNomeSoftwaresListaNegra());
				texto.append("|");
			}

			texto.deleteCharAt(texto.length()-1);

			return texto.toString();

		}else {
			return "";
		}

	}
	
	/**
	 * Retorna Collection com lista de Softwares da Lista Negra.
	 * 
	 * @return Collection - Lista de Softwares da lista negra.
	 * @throws Exception
	 * @author ronnie.lopes
	 */
	public Collection<SoftwaresListaNegraDTO> recuperaCollectionSoftwaresListaNegra() throws PersistenceException {
		List lista = new ArrayList();
		StringBuilder sql = new StringBuilder();
		List  listRetorno = new ArrayList();
		
		listRetorno.add("idSoftwaresListaNegra");
		listRetorno.add("nomeSoftwaresListaNegra");

		sql.append(" SELECT idsoftwareslistanegra, nomesoftwareslistanegra");
		sql.append(" FROM softwareslistanegra");
		sql.append(" ORDER BY nomesoftwareslistanegra");

		lista = this.execSQL(sql.toString(), null);

		List<SoftwaresListaNegraDTO> listSoftwareListaNegra = this.listConvertion(SoftwaresListaNegraDTO.class, lista, listRetorno);

		if (listSoftwareListaNegra != null && !listSoftwareListaNegra.isEmpty()) {
			return listSoftwareListaNegra;
		}else {
			return null;
		}

	}
	
	/**
	 * Mario Hayasaki
	 * @param 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public Collection<RelatorioListaNegraDTO> listaRelatorioListaNegra(RelatorioListaNegraDTO relatorioListaNegraDTO) throws PersistenceException {
		List listRetorno = new ArrayList();
		List param = new ArrayList();
		List lista = new ArrayList();
		StringBuilder sql = new StringBuilder();
		String filtro = "";
		
		sql.append(" select distinct itemconfiguracao.iditemconfiguracao, itemconfiguracao.identificacao as descricao, ");
		sql.append(" softwareslistanegra.nomesoftwareslistanegra ");
		sql.append(" from itemconfiguracao itemconfiguracao ");
		sql.append(" inner join softwareslistanegraencontrados softwareslistanegraencontrados  ");
		sql.append(" on softwareslistanegraencontrados.iditemconfiguracao = itemconfiguracao.iditemconfiguracao ");
		sql.append(" inner join  softwareslistanegra softwareslistanegra ");
		sql.append(" on softwareslistanegra.idsoftwareslistanegra = softwareslistanegraencontrados.idsoftwareslistanegra ");
		sql.append(" left join grupoitemconfiguracao grupoitemconfiguracao ");
		sql.append(" on itemconfiguracao.idgrupoitemconfiguracao = grupoitemconfiguracao.idgrupoitemconfiguracao  ");
		
	    
	    
	    if(relatorioListaNegraDTO.getDataInicio()!=null){
	    filtro +=" and softwareslistanegraencontrados.data between ? and ? ";	
	    }
	    if(relatorioListaNegraDTO.getIdSoftwaresListaNegra()!=null){
	    filtro+= " and softwareslistanegra.idsoftwareslistanegra = ? ";	
	    }
	    if(relatorioListaNegraDTO.getIdGrupoItemConfiguracao()!=null){
	    filtro+=" and grupoitemconfiguracao.idgrupoitemconfiguracao = ? ";
	    }
	    if(!relatorioListaNegraDTO.getLocalidade().equals("")){
	     filtro+= "and itemconfiguracao.localidade like('%"+relatorioListaNegraDTO.getLocalidade()+"%') ";
	    }	    
		if (!filtro.equals("")) {
			filtro = filtro.replaceFirst("and", "");
			sql.append(" where " + filtro);
		}
		if (relatorioListaNegraDTO.getDataInicio() != null) {
			param.add(relatorioListaNegraDTO.getDataInicio());
			param.add(relatorioListaNegraDTO.getDataFim());
		}
		if (relatorioListaNegraDTO.getIdSoftwaresListaNegra() != null) {
			param.add(relatorioListaNegraDTO.getIdSoftwaresListaNegra());
		}
		if(relatorioListaNegraDTO.getIdGrupoItemConfiguracao()!=null){
		   param.add(relatorioListaNegraDTO.getIdGrupoItemConfiguracao());
		}
//		if(!relatorioListaNegraDTO.getLocalidade().equals("")){
//		   param.add(relatorioListaNegraDTO.getLocalidade());
//		}
		
		if (param != null) {
			lista = this.execSQL(sql.toString(), param.toArray());
		} else {
			lista = this.execSQL(sql.toString(), null);
		}
		
		listRetorno.add("idItemConfiguracao");
		listRetorno.add("descricao");
		listRetorno.add("nomeSoftwaresListaNegra");
		
		if (lista != null && !lista.isEmpty()) {
			Collection<RelatorioListaNegraDTO> listResultado = this.engine.listConvertion(RelatorioListaNegraDTO.class, lista, listRetorno);
			return listResultado;
		}
		return null;
	}

}
