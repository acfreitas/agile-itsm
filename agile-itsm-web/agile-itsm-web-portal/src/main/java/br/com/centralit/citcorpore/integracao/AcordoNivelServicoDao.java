package br.com.centralit.citcorpore.integracao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import br.com.centralit.citcorpore.bean.AcordoNivelServicoDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class AcordoNivelServicoDao extends CrudDaoDefaultImpl {

	public AcordoNivelServicoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idAcordoNivelServico", "idAcordoNivelServico", true, true, false, false));
		listFields.add(new Field("idServicoContrato", "idServicoContrato", false, false, false, false));
		listFields.add(new Field("idPrioridadePadrao", "idPrioridadePadrao", false, false, false, false));
		listFields.add(new Field("situacao", "situacao", false, false, false, false));
		listFields.add(new Field("tituloSLA", "tituloSLA", false, false, false, false));
		listFields.add(new Field("disponibilidade", "disponibilidade", false, false, false, false));
		listFields.add(new Field("descricaoSLA", "descricaoSLA", false, false, false, false));
		listFields.add(new Field("escopoSLA", "escopoSLA", false, false, false, false));
		listFields.add(new Field("dataInicio", "dataInicio", false, false, false, false));
		listFields.add(new Field("dataFim", "dataFim", false, false, false, false));
		listFields.add(new Field("avaliarEm", "avaliarEm", false, false, false, false));
		listFields.add(new Field("tipo", "tipo", false, false, false, false));
		listFields.add(new Field("valorLimite", "valorLimite", false, false, false, false));
		listFields.add(new Field("detalheGlosa", "detalheGlosa", false, false, false, false));
		listFields.add(new Field("detalheLimiteGlosa", "detalheLimiteGlosa", false, false, false, false));
		listFields.add(new Field("unidadeValorLimite", "unidadeValorLimite", false, false, false, false));
		listFields.add(new Field("impacto", "impacto", false, false, false, false));
		listFields.add(new Field("urgencia", "urgencia", false, false, false, false));
		listFields.add(new Field("permiteMudarImpUrg", "permiteMudarImpUrg", false, false, false, false));
		// listFields.add(new Field("idFormula" ,"idFormula", false, false, false, false));
		listFields.add(new Field("contatos", "contatos", false, false, false, false));
		listFields.add(new Field("deleted", "deleted", false, false, false, false));
		listFields.add(new Field("tempoAuto", "tempoAuto", false, false, false, false));
		listFields.add(new Field("idPrioridadeAuto1", "idPrioridadeAuto1", false, false, false, false));
		listFields.add(new Field("idGrupo1", "idGrupo1", false, false, false, false));
		listFields.add(new Field("criadoEm", "criadoEm", false, false, false, false));
		listFields.add(new Field("criadoPor", "criadoPor", false, false, false, false));
		listFields.add(new Field("modificadoEm", "modificadoEm", false, false, false, false));
		listFields.add(new Field("modificadoPor", "modificadoPor", false, false, false, false));
		listFields.add(new Field("idEmail", "idEmail", false, false, false, false));
		return listFields;
	}

	public String getTableName() {
		return this.getOwner() + "AcordoNivelServico";
	}

	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return AcordoNivelServicoDTO.class;
	}

	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}

	public Collection findByIdServicoContrato(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idServicoContrato", "=", parm));
		ordenacao.add(new Order("dataInicio"));
		return super.findByCondition(condicao, ordenacao);
	}

	public void deleteByIdServicoContrato(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idServicoContrato", "=", parm));
		super.deleteByCondition(condicao);
	}

	public Collection findByIdPrioridadePadrao(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idPrioridadePadrao", "=", parm));
		ordenacao.add(new Order("dataInicio"));
		return super.findByCondition(condicao, ordenacao);
	}

	public void deleteByIdPrioridadePadrao(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idPrioridadePadrao", "=", parm));
		super.deleteByCondition(condicao);
	}

	public AcordoNivelServicoDTO findAtivoByIdServicoContrato(Integer idServicoContrato, String tipo) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idServicoContrato", "=", idServicoContrato));
		condicao.add(new Condition("tipo", "=", tipo));
		condicao.add(new Condition("situacao", "=", "A"));

		Collection<AcordoNivelServicoDTO> col = super.findByCondition(condicao, null);
		if (col == null || col.size() == 0)
			return null;
		AcordoNivelServicoDTO result = null;
		for (AcordoNivelServicoDTO acordoNivelServicoDto : col) {
			if ((acordoNivelServicoDto.getDataFim() == null || UtilDatas.getDataAtual().before(acordoNivelServicoDto.getDataFim()))
					&& (acordoNivelServicoDto.getDeleted() == null || acordoNivelServicoDto.getDeleted().equalsIgnoreCase("N")))
				result = acordoNivelServicoDto;
		}
		return result;
	}

	/**
	 * Método para retornar os serviços que possuem o SLA selecionado já copiado, para ser tratado evitando duplicação de SLA.
	 * 
	 * @param titulo
	 *            do SLA selecionado
	 * @return retorna os serviços que possuem o SLA selecionado
	 * @throws Exception
	 * @author rodrigo.oliveira
	 */
	public List<ServicoContratoDTO> buscaServicosComContrato(String tituloSla) throws PersistenceException {

		List parametro = new ArrayList();
		List resp = new ArrayList();

		String sql = "SELECT DISTINCT idServicoContrato FROM " + getTableName() + " WHERE titulosla LIKE ? AND idservicocontrato IS NOT NULL ORDER BY idservicocontrato ";

		parametro.add(tituloSla);

		resp = this.execSQL(sql, parametro.toArray());

		List listRetorno = new ArrayList();
		listRetorno.add("idServicoContrato");

		List<ServicoContratoDTO> listconvertion = this.engine.listConvertion(ServicoContratoDTO.class, resp, listRetorno);

		return listconvertion;
	}

	/**
	 * Método para verificar se existe cadastrado um cadastro o mesmo nome.
	 * 
	 * @param String
	 *            tituloSLA
	 * @return true se o nome exisite e false se não existir
	 * @throws Exception
	 * @author rodrigo.oliveira
	 */
	public boolean verificaSeNomeExiste(String tituloSLA) throws PersistenceException {

		List parametro = new ArrayList();
		List list = new ArrayList();
		String sql = "SELECT idacordonivelservico FROM " + getTableName() + " WHERE titulosla = ? ";

		parametro.add(tituloSLA);

		list = this.execSQL(sql, parametro.toArray());

		if (list != null && !list.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Retorna os SLAs que não possuem vinculação direta com ServicoContrato
	 * 
	 */
	public List<AcordoNivelServicoDTO> findAcordosSemVinculacaoDireta() throws PersistenceException {
		List resp = new ArrayList();

		Collection fields = getFields();
		List listRetorno = new ArrayList();
		String campos = "";
		for (Iterator it = fields.iterator(); it.hasNext();) {
			Field field = (Field) it.next();
			if (!campos.trim().equalsIgnoreCase("")) {
				campos = campos + ",";
			}
			campos = campos + field.getFieldDB();
			listRetorno.add(field.getFieldClass());
		}

		String sql = "SELECT " + campos + " FROM " + getTableName() + " WHERE idservicocontrato IS NULL ORDER BY titulosla ";

		resp = this.execSQL(sql, null);

		Collection<AcordoNivelServicoDTO> col = this.listConvertion(AcordoNivelServicoDTO.class, resp, listRetorno);
		if (col == null || col.size() == 0)
			return null;
		List<AcordoNivelServicoDTO> result = new ArrayList<AcordoNivelServicoDTO>();
		for (AcordoNivelServicoDTO acordoNivelServicoDTO : col) {
			if (acordoNivelServicoDTO.getDeleted() == null || acordoNivelServicoDTO.getDeleted().equalsIgnoreCase("N")) {
				if (acordoNivelServicoDTO.getDataFim() == null || acordoNivelServicoDTO.getDataFim().after(UtilDatas.getDataAtual())) {
					result.add(acordoNivelServicoDTO);
				}
			}
		}
		return result;

	}

	/**
	 * Encontra o SLAs pelo ID
	 * @author euler.ramos
	 */
	public List<AcordoNivelServicoDTO> findByIdAcordoSemVinculacaoDireta(Integer id) throws PersistenceException {
		List resp = new ArrayList();

		Collection fields = getFields();
		List parametro = new ArrayList();
		List listRetorno = new ArrayList();
		String campos = "";
		for (Iterator it = fields.iterator(); it.hasNext();) {
			Field field = (Field) it.next();
			if (!campos.trim().equalsIgnoreCase("")) {
				campos = campos + ",";
			}
			campos = campos + field.getFieldDB();
			listRetorno.add(field.getFieldClass());
		}

		String sql = "SELECT " + campos + " FROM " + getTableName() + " WHERE idacordonivelservico=? and (idservicocontrato IS NULL) and ((deleted is null) or (deleted = 'N') or (deleted = 'n')) ORDER BY titulosla ";
		parametro.add(id);
		resp = this.execSQL(sql, parametro.toArray());
		
		List result = this.engine.listConvertion(getBean(), resp, listRetorno);
		return (result == null ? new ArrayList<AcordoNivelServicoDTO>() : result);
	}

	/**
	 * Encontra o SLAs pelo Titulo
	 * @author euler.ramos
	 */
	public List<AcordoNivelServicoDTO> findByTituloSLA(String titulo) throws PersistenceException {
		List resp = new ArrayList();

		Collection fields = getFields();
		List parametro = new ArrayList();
		List listRetorno = new ArrayList();
		String campos = "";
		for (Iterator it = fields.iterator(); it.hasNext();) {
			Field field = (Field) it.next();
			if (!campos.trim().equalsIgnoreCase("")) {
				campos = campos + ",";
			}
			campos = campos + field.getFieldDB();
			listRetorno.add(field.getFieldClass());
		}

		String sql = "SELECT " + campos + " FROM " + getTableName() + " WHERE titulosla=? and (idservicocontrato IS NULL) and ((deleted is null) or (deleted = 'N') or (deleted = 'n')) ORDER BY titulosla ";
		parametro.add(titulo);
		resp = this.execSQL(sql, parametro.toArray());
		
		List result = this.engine.listConvertion(getBean(), resp, listRetorno);
		return (result == null ? new ArrayList<AcordoNivelServicoDTO>() : result);
	}
	
	public void updateTemposAcoes(AcordoNivelServicoDTO acordoNivelServicoDTO) throws PersistenceException {
		AcordoNivelServicoDTO acordoNivelServicoAux = new AcordoNivelServicoDTO();
		acordoNivelServicoAux.setIdGrupo1(acordoNivelServicoDTO.getIdGrupo1());
		acordoNivelServicoAux.setIdPrioridadeAuto1(acordoNivelServicoDTO.getIdPrioridadeAuto1());
		acordoNivelServicoAux.setTempoAuto(acordoNivelServicoDTO.getTempoAuto());
		acordoNivelServicoAux.setIdAcordoNivelServico(acordoNivelServicoDTO.getIdAcordoNivelServico());
		acordoNivelServicoAux.setDeleted(acordoNivelServicoDTO.getDeleted());
		super.updateNotNull(acordoNivelServicoAux);
	}

	/**
	 * @param idServicoContrato
	 * @param data
	 * @throws PersistenceException
	 * @author cledson.junior
	 */
	public void updateAcordoNivelServico(Integer idServicoContrato, Date data) throws PersistenceException {
		List parametros = new ArrayList();
		if (data != null) {
			parametros.add(data);
		} else {
			parametros.add(null);
		}
		parametros.add("y");
		parametros.add(idServicoContrato);
		String sql = "UPDATE " + getTableName() + " SET datafim = ?, deleted = ? WHERE idServicoContrato = ?";
		execUpdate(sql, parametros.toArray());
	}

	public void updateNotNull(AcordoNivelServicoDTO acordoNivelServicoDTO) throws PersistenceException {
		super.updateNotNull(acordoNivelServicoDTO);
	}

	public List<AcordoNivelServicoDTO> findIdEmailByIdSolicitacaoServico(Integer idSolicitacaoServico) throws PersistenceException {

		List parametro = new ArrayList();
		List resp = new ArrayList();

		String sql = "SELECT DISTINCT idEmail FROM " + getTableName() + " inner join  solicitacaoservico on solicitacaoservico.idservicocontrato = acordonivelservico.idservicocontrato"
				+ " AND solicitacaoservico.idsolicitacaoservico = " + idSolicitacaoServico;

		resp = this.execSQL(sql, parametro.toArray());

		List listRetorno = new ArrayList();
		listRetorno.add("idEmail");

		List<AcordoNivelServicoDTO> listconvertion = this.engine.listConvertion(ServicoContratoDTO.class, resp, listRetorno);

		return listconvertion;
	}
	
	public AcordoNivelServicoDTO findByIdAcordoNivelServicoEServicoContrato(Integer idAcordoNivelServico, Integer idServicoContrato) throws PersistenceException {
		List parametro = new ArrayList();
		List fields = new ArrayList();
		List list = new ArrayList();
		String sql = "select idacordonivelservico, idservicocontrato from acordonivelservico where idacordonivelservico = ? and idservicocontrato = ?";
		parametro.add(idAcordoNivelServico);
		parametro.add(idServicoContrato);
		list = this.execSQL(sql, parametro.toArray());
		fields.add("idAcordoNivelServico");
		fields.add("idServicoContrato");
		
		if (list != null && !list.isEmpty()) {
			return (AcordoNivelServicoDTO) this.listConvertion(getBean(), list, fields).get(0);
		} else {
			return null;
		}
	}

}
