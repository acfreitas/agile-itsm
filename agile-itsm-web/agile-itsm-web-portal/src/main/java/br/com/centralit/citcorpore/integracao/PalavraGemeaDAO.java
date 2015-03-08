/**
 * 
 */
package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.PalavraGemeaDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.SQLConfig;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class PalavraGemeaDAO extends CrudDaoDefaultImpl {

	public PalavraGemeaDAO() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Collection find(IDto obj) throws PersistenceException {
		return null;
	}

	@Override
	public Collection<Field> getFields() {

		Collection<Field> listFields = new ArrayList<>();

		listFields.add(new Field("idpalavragemea", "idPalavraGemea", true, true, false, false));
		listFields.add(new Field("palavra", "palavra", false, false, false, false));
		listFields.add(new Field("palavracorrespondente", "palavraCorrespondente", false, false, false, false));

		return listFields;
	}

	@Override
	public String getTableName() {
		return "palavragemea";
	}

	@Override
	public Collection list() throws PersistenceException {
		return super.list("palavra");
	}

	@Override
	public Class getBean() {
		return PalavraGemeaDTO.class;
	}
    
	/**
	 * Verifica se existe outro registro igual criado.
	 * Se existir retorna 'true', senao retorna 'false';
	 * 
	 * @param palavraGemea
	 * @return estaCadastrado
	 * @throws PersistenceException
	 * @author edu.braz
	 */
	public boolean VerificaSeCadastrado(PalavraGemeaDTO palavraGemeaDTO) throws PersistenceException {
		boolean estaCadastrado = false;
		List parametro = new ArrayList();
		List list = new ArrayList();
		StringBuilder sql = new StringBuilder();
		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.POSTGRESQL)) { 
			//Se o banco for POSTGRESQL utilizar 'ilike' se outro banco utilizar 'like'.
		    sql.append("select palavra from " + getTableName() + "  where palavra ilike ? ");
		}else{
			sql.append("select palavra from " + getTableName() + "  where palavra like ? ");
		}
		parametro.add(palavraGemeaDTO.getPalavra());
		list = this.execSQL(sql.toString(), parametro.toArray());
		if (list != null && !list.isEmpty()) {
			estaCadastrado = true;
		} 
		return estaCadastrado;
	}
	 
	/**
	 * Verifica se a palavra correspondente existe.
	 * Se existir retorna 'true', senao retorna 'false';
	 * 
	 * @param palavraGemea
	 * @return existe
	 * @throws PersistenceException
	 * @author edu.braz
	 */
	public boolean VerificaSePalavraCorrespondenteExiste(PalavraGemeaDTO palavraGemeaDTO) throws PersistenceException {
		boolean existe = false;
		List parametro = new ArrayList();
		List list = new ArrayList();
		StringBuilder sql = new StringBuilder();
		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.POSTGRESQL)) { 
			//Se o banco for POSTGRESQL utilizar 'ilike' se outro banco utilizar 'like'.
		    sql.append("select palavracorrespondente from " + getTableName() + "  where palavracorrespondente ilike ? ");
		}else{
			sql.append("select palavracorrespondente from " + getTableName() + "  where palavracorrespondente like ? ");
		}
		parametro.add(palavraGemeaDTO.getPalavraCorrespondente());
		list = this.execSQL(sql.toString(), parametro.toArray());
		if (list != null && !list.isEmpty()) {
			existe = true;
		} 
		return existe;
	}

}
