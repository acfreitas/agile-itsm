/**
 * CentralIT - CITSmart
 */
package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.PerfilAcessoUsuarioDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;

/**
 * DAO de PerfilAcessoUsuario.
 * 
 * @author valdoilo.damasceno
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class PerfilAcessoUsuarioDAO extends CrudDaoDefaultImpl {

	public PerfilAcessoUsuarioDAO() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}

	@Override
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();

		listFields.add(new Field("IDPERFIL", "idPerfilAcesso", true, false, false, false));
		listFields.add(new Field("IDUSUARIO", "idUsuario", true, false, false, false));
		listFields.add(new Field("DATAINICIO", "dataInicio", false, false, false, false));
		listFields.add(new Field("DATAFIM", "dataFim", false, false, false, false));

		return listFields;
	}

	/**
	 * Verifica se Usuário possui Perfi de Acesso específico.
	 * 
	 * @param usuario
	 * @return boolean
	 * @author valdoilo.damasceno
	 * @throws PersistenceException
	 */
	public boolean verificarSeUsuarioPossuiPerfilAcessoEspecifico(UsuarioDTO usuario) throws PersistenceException {
		if (usuario == null) {
			return false;
		}
		StringBuilder sql = new StringBuilder();
		List parametro = new ArrayList();

		sql.append("SELECT idusuario FROM perfilacessousuario ");
		sql.append("WHERE idusuario = ? and datafim IS NULL");

		parametro.add(usuario.getIdUsuario());

		List list = this.execSQL(sql.toString(), parametro.toArray());

		if (list != null && !list.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Retorna PerfilAcessoUsuario.
	 * 
	 * @param usuario
	 * @return PerfilAcessoUsuarioDTO
	 * @throws Exception
	 */
	public PerfilAcessoUsuarioDTO obterPerfilAcessoUsuario(UsuarioDTO usuario) throws PersistenceException {
		StringBuilder sql = new StringBuilder();
		List parametro = new ArrayList();
		List fields = gerarFieldsRetornoPerfilAcessoUsuario();

		sql.append("SELECT idPerfil, idUsuario, dataInicio, dataFim FROM perfilacessousuario ");
		sql.append("WHERE idusuario = ? and datafim IS NULL");

		parametro.add(usuario.getIdUsuario());

		List list = this.execSQL(sql.toString(), parametro.toArray());

		if (list != null && !list.isEmpty()) {
			List novaLista = this.listConvertion(PerfilAcessoUsuarioDTO.class, list, fields);
			return (PerfilAcessoUsuarioDTO) novaLista.get(0);
		} else {
			return null;
		}
	}

	@Override
	public String getTableName() {
		return "PERFILACESSOUSUARIO";
	}

	@Override
	public Collection list() throws PersistenceException {
		return null;
	}

	@Override
	public Class getBean() {
		return PerfilAcessoUsuarioDTO.class;
	}

	public PerfilAcessoUsuarioDTO listByIdUsuario(PerfilAcessoUsuarioDTO obj) throws PersistenceException {
		List list = new ArrayList();
		List fields = new ArrayList();
		String sql = "select idperfil from " + getTableName() + " where idUsuario = " + obj.getIdUsuario() + " AND dataFim IS NULL";
		fields.add("idPerfilAcesso");
		list = this.execSQL(sql, null);
		List novaLista = this.listConvertion(getBean(), list, fields);

		if (novaLista != null && !novaLista.isEmpty()) {
			return (PerfilAcessoUsuarioDTO) novaLista.get(0);
		} else {
			return null;
		}
	}
	
	public Collection listPerfilByIdUsuario(UsuarioDTO usuarioDTO) throws PersistenceException {
		List list = new ArrayList();
		List fields = new ArrayList();
		String sql = "select idperfil from " + getTableName() + " where idUsuario = " + usuarioDTO.getIdUsuario() + " AND dataFim IS NULL";
		fields.add("idPerfilAcesso");
		list = this.execSQL(sql, null);
		List novaLista = this.listConvertion(getBean(), list, fields);

		if (novaLista != null && !novaLista.isEmpty()) {
			return novaLista;
		} else {
			return null;
		}
	}

	public void updateDataFim(PerfilAcessoUsuarioDTO obj) throws PersistenceException {
		PerfilAcessoUsuarioDTO perfilAcessoUsuarioDTO = (PerfilAcessoUsuarioDTO) obj;
		List parametros = new ArrayList();
		parametros.add(UtilDatas.getDataAtual());
		parametros.add(perfilAcessoUsuarioDTO.getIdUsuario());
		
		String sql = "UPDATE " + getTableName() + " SET DATAFIM = ? WHERE IDUSUARIO = ?";
		
		this.execUpdate(sql, parametros.toArray());
	}

	/**
	 * Atualiza o Perfil de Acesso do usuário.
	 * 
	 * @param perfilAcessoUsuario
	 * @throws Exception
	 */
	public void update(PerfilAcessoUsuarioDTO perfilAcessoUsuario) throws PersistenceException {
		PerfilAcessoUsuarioDTO perfilAcessoUsuarioDTO = (PerfilAcessoUsuarioDTO) perfilAcessoUsuario;
		List parametros = new ArrayList();
		parametros.add(perfilAcessoUsuario.getIdPerfilAcesso());
		parametros.add(perfilAcessoUsuarioDTO.getIdUsuario());
		
		String sql = "UPDATE " + getTableName() + " SET idperfil = ? WHERE IDUSUARIO = ? ";

		this.execUpdate(sql, parametros.toArray());
	}

	@Override
	public IDto restore(IDto obj) throws PersistenceException {
		PerfilAcessoUsuarioDTO perfilAcessoUsuarioDTO = (PerfilAcessoUsuarioDTO) obj;
		List fields = gerarFieldsRetornoPerfilAcessoUsuario();
		String sql = "SELECT idPerfil, idUsuario, dataInicio, dataFim FROM " + getTableName() + " WHERE dataFim IS NULL AND idPerfil = " + perfilAcessoUsuarioDTO.getIdUsuario();
		List dados = this.execSQL(sql, null);
		return (IDto) this.listConvertion(getBean(), dados, fields);
	}

	public IDto restorePerfilAcessoUsuario(IDto obj) throws PersistenceException {
		PerfilAcessoUsuarioDTO perfilAcessoUsuarioDTO = (PerfilAcessoUsuarioDTO) obj;
		List fields = gerarFieldsRetornoPerfilAcessoUsuario();
		String sql = "SELECT idPerfil, idUsuario, dataInicio, dataFim FROM " + getTableName() + " WHERE dataFim IS NULL AND idUsuario = " + perfilAcessoUsuarioDTO.getIdUsuario();
		List dados = this.execSQL(sql, null);
		if (dados != null && !dados.isEmpty()) {
			return (IDto) this.listConvertion(getBean(), dados, fields).get(0);
		} else {
			return null;
		}
	}

	private List gerarFieldsRetornoPerfilAcessoUsuario() {
		List fields = new ArrayList();
		fields.add("idPerfilAcesso");
		fields.add("idUsuario");
		fields.add("dataInicio");
		fields.add("dataFim");
		return fields;
	}

	@Override
	public void delete(IDto obj) throws PersistenceException {
		PerfilAcessoUsuarioDTO dto = (PerfilAcessoUsuarioDTO) obj;
		String sql = "DELETE FROM " + getTableName() + " WHERE IDUSUARIO = ? ";
		this.execUpdate(sql, new Object[] {dto.getIdUsuario()});
	}

	/**
	 * Retorna PerfilAcessoUsuario Ativos por idPerfilAcesso.
	 * 
	 * @param idPerfilAcesso
	 * @return
	 * @throws Exception
	 */
	public Collection findByIdPerfil(Integer idPerfilAcesso) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idPerfilAcesso", "=", idPerfilAcesso));
		condicao.add(new Condition("dataFim", "is", null));
		ordenacao.add(new Order("idPerfilAcesso"));
		return super.findByCondition(condicao, ordenacao);
	}

	public void reativaPerfisUsuario(Integer idUsuario) throws PersistenceException {
		
		String sql = "UPDATE " + getTableName() + " SET DATAFIM = null WHERE IDUSUARIO = ? ";
		
		this.execUpdate(sql,  new Object[] { idUsuario });

	}

}
