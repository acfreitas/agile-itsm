/**
 * 
 */
package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.bean.ImportanciaConhecimentoGrupoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

/**
 * @author Vadoilo Damasceno
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ImportanciaConhecimentoGrupoDAO extends CrudDaoDefaultImpl {

	public ImportanciaConhecimentoGrupoDAO() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Collection find(IDto obj) throws PersistenceException {
		return null;
	}

	@Override
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();

		listFields.add(new Field("IDBASECONHECIMENTO", "idBaseConhecimento", true, false, false, false));
		listFields.add(new Field("IDGRUPO", "idGrupo", true, false, false, false));
		listFields.add(new Field("GRAUIMPORTANCIAGRUPO", "grauImportanciaGrupo", false, false, false, false));

		return listFields;
	}

	@Override
	public String getTableName() {
		return "IMPORTANCIACONHECIMENTOGRUPO";
	}

	@Override
	public Class getBean() {
		return ImportanciaConhecimentoGrupoDTO.class;
	}

	@Override
	public Collection list() throws PersistenceException {
		List ordenacao = new ArrayList();
		ordenacao.add(new Order("idConhecimento"));
		return super.list(ordenacao);
	}

	/**
	 * Deleta ImportanciaConhecimentoUsuário da Base de Conhecimento.
	 * 
	 * @param idBaseConhecimento
	 * @throws Exception
	 * @author Vadoilo Damasceno
	 */
	public void deleteByIdConhecimento(Integer idBaseConhecimento) throws PersistenceException {

		List condicao = new ArrayList();

		condicao.add(new Condition("idBaseConhecimento", "=", idBaseConhecimento));

		this.deleteByCondition(condicao);

	}

	/**
	 * Lista ImportanciaConhecimentoGrupo por idBaseConhecimento.
	 * 
	 * @param idBaseConhecimento
	 * @return Collection<ImportanciaConhecimentoUsuarioDTO>
	 * @throws Exception
	 * @author Vadoilo Damasceno
	 */
	public Collection<ImportanciaConhecimentoGrupoDTO> listByIdBaseConhecimento(Integer idBaseConhecimento) throws PersistenceException {

		List condicao = new ArrayList();

		List ordenacao = new ArrayList();

		condicao.add(new Condition("idBaseConhecimento", "=", idBaseConhecimento));

		ordenacao.add(new Order("idBaseConhecimento", "ASC"));

		return findByCondition(condicao, ordenacao);
	}

	/**
	 * Retorna lista de ImportanciaConhecimentoGrupo de acordo com a BaseConhecimento e a Lista de Grupos do Usuário.
	 * 
	 * @param baseConhecimentoDto
	 * @param listGrupoEmpregado
	 * @return Collection<ImportanciaConhecimentoGrupoDTO>
	 * @throws Exception
	 */
	public ImportanciaConhecimentoGrupoDTO obterGrauDeImportancia(BaseConhecimentoDTO baseConhecimentoDto, Collection<GrupoEmpregadoDTO> listGrupoEmpregado, UsuarioDTO usuarioDto)
			throws PersistenceException {

		List<ImportanciaConhecimentoGrupoDTO> listImportanciaConhecimentoGrupo = new ArrayList<ImportanciaConhecimentoGrupoDTO>();

		List parametros = new ArrayList();

		List listRetorno = new ArrayList();

		StringBuilder sql = new StringBuilder();

		sql.append("(select distinct idbaseconhecimento, grauimportanciagrupo from importanciaconhecimentogrupo  ");

		if (baseConhecimentoDto.getIdBaseConhecimento() != null) {
			sql.append("where  idbaseconhecimento = ? ");
			parametros.add(baseConhecimentoDto.getIdBaseConhecimento());
		}

		boolean aux = false;
		if (listGrupoEmpregado != null && !listGrupoEmpregado.isEmpty()) {

			for (GrupoEmpregadoDTO grupoEmpregadoDto : listGrupoEmpregado) {

				if (!aux) {
					sql.append(" AND (idgrupo = ? ");
					parametros.add(grupoEmpregadoDto.getIdGrupo());
					aux = true;
				} else {

					sql.append(" OR idgrupo = ? ");
					parametros.add(grupoEmpregadoDto.getIdGrupo());
				}
			}

			sql.append(")");
		}

		sql.append(") union ");
		sql.append("(select distinct idbaseconhecimento, grauimportanciausuario ");
		sql.append("from importanciaconhecimentousuario ");

		if (baseConhecimentoDto.getIdBaseConhecimento() != null) {
			sql.append("where idbaseconhecimento = ? ");
			parametros.add(baseConhecimentoDto.getIdBaseConhecimento());
		}
		if (usuarioDto.getIdUsuario() != null) {
			sql.append("and idusuario = ? ");
			parametros.add(usuarioDto.getIdUsuario());
		}
		sql.append(")");
		sql.append(" order by grauimportanciagrupo desc ");

		List list = this.execSQL(sql.toString(), parametros.toArray());

		listRetorno.add("idBaseConhecimento");
		listRetorno.add("grauImportancia");

		if (list != null && !list.isEmpty()) {
			listImportanciaConhecimentoGrupo = this.listConvertion(ImportanciaConhecimentoGrupoDTO.class, list, listRetorno);
			return listImportanciaConhecimentoGrupo.get(0);
		}
		return null;
	}

}
