package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.LocalExecucaoServicosDto;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.SQLConfig;

public class LocalExecucaoServicosDao extends CrudDaoDefaultImpl {

	public LocalExecucaoServicosDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Collection<?> find(IDto obj) throws PersistenceException {
				return null;
	}

	@Override
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<Field>();

		listFields.add(new Field("idlocalexecucaoservico", "idlocalexecucaoservico", true, true, false, false));
		listFields.add(new Field("nomelocalexecucaoservico", "nomelocalexecucaoservico", false, false, false, false));
		listFields.add(new Field("deleted", "deleted", false, false, false, false));

		return listFields;
	}

	@Override
	public String getTableName() {
				return "localexecucaoservicos";
	}

	@Override
	public Collection<?> list() throws PersistenceException {
				return null;
	}

	@Override
	public Class<LocalExecucaoServicosDto> getBean() {
				return LocalExecucaoServicosDto.class;
	}

	@SuppressWarnings("unchecked")
	public boolean verificarSeLocalExecucaoServicoPossuiServico(LocalExecucaoServicosDto localExecucaoServicosDto) throws PersistenceException {
		StringBuilder sql = new StringBuilder();
		List<Integer> parametros = new ArrayList<Integer>();

		sql.append("SELECT localexecucaoservico.idlocalexecucaoservico FROM localexecucaoservico localexecucaoservico ");
		sql.append("INNER JOIN servico servico ON localexecucaoservico.idlocalexecucaoservico = servico.idlocalexecucaoservico ");
		sql.append("WHERE localexecucaoservico.idlocalexecucaoservico = ?");
		parametros.add(localExecucaoServicosDto.getIdLocalExecucaoServico());

		List<LocalExecucaoServicosDto> categoriasEncontradas = execSQL(sql.toString(), parametros.toArray());

		if (categoriasEncontradas != null && !categoriasEncontradas.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	public Collection verificaDescricaoDuplicadaAoCriar(String Descricao) throws PersistenceException {
	    String sql = "select * from localexecucaoservico where nomelocalexecucaoservico = ? AND ";
	    if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.POSTGRESQL))
			sql += "(UPPER(deleted) IS NULL OR UPPER(deleted) = 'N') ";
		 else if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER))
			sql += "(deleted IS NULL OR deleted = 'N') ";
		 else
			 sql += "(deleted IS NULL OR deleted = 'N') ";

	    List colDados = this.execSQL(sql, new Object[] {Descricao});
	    if (colDados != null){
		List fields = new ArrayList();
		fields.add("idLocalExecucaoServico");
		return this.listConvertion(LocalExecucaoServicosDto.class, colDados, fields);
	    }
	    return null;
	}

	public Collection verificaDescricaoDuplicadaAoAlterar(Integer id, String descricao) throws PersistenceException {
		 String sql = "select * from localexecucaoservico where nomelocalexecucaoservico = ? AND idlocalexecucaoservico <> ? AND ";
		    if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.POSTGRESQL))
				sql += "(UPPER(deleted) IS NULL OR UPPER(deleted) = 'N') ";
			 else if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER))
				sql += "(deleted IS NULL OR deleted = 'N') ";
			 else
				 sql += "(deleted IS NULL OR deleted = 'N') ";

		    List colDados = this.execSQL(sql, new Object[] {descricao,id});
		    if (colDados != null){
			List fields = new ArrayList();
			fields.add("idLocalExecucaoServico");
			return this.listConvertion(LocalExecucaoServicosDto.class, colDados, fields);
		    }
		    return null;
	}

}
