package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import br.com.centralit.citcorpore.bean.HistoricoServicoDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.SQLConfig;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class HistoricoServicoDao extends CrudDaoDefaultImpl {
	public HistoricoServicoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idHistoricoServico", "idHistoricoServico", true, true, false, false));
		listFields.add(new Field("idServico", "idServico", false, false, false, false));
		listFields.add(new Field("idCategoriaServico", "idCategoriaServico", false, false, false, false));
		listFields.add(new Field("idSituacaoServico", "idSituacaoServico", false, false, false, false));
		listFields.add(new Field("idTipoServico", "idTipoServico", false, false, false, false));
		listFields.add(new Field("idImportanciaNegocio", "idImportanciaNegocio", false, false, false, false));
		listFields.add(new Field("idEmpresa", "idEmpresa", false, false, false, false));
		listFields.add(new Field("idTipoEventoServico", "idTipoEventoServico", false, false, false, false));
		listFields.add(new Field("idTipoDemandaServico", "idTipoDemandaServico", false, false, false, false));
		listFields.add(new Field("idLocalExecucaoServico", "idLocalExecucaoServico", false, false, false, false));
		listFields.add(new Field("nomeServico", "nomeServico", false, false, false, false));
		listFields.add(new Field("detalheServico", "detalheServico", false, false, false, false));
		listFields.add(new Field("objetivo", "objetivo", false, false, false, false));
		listFields.add(new Field("passosServico", "passosServico", false, false, false, false));
		listFields.add(new Field("dataInicio", "dataInicio", false, false, false, false));
		listFields.add(new Field("linkProcesso", "linkProcesso", false, false, false, false));
		listFields.add(new Field("descricaoProcesso", "descricaoProcesso", false, false, false, false));
		listFields.add(new Field("tipoDescProcess", "tipoDescProcess", false, false, false, false));
		listFields.add(new Field("dispPortal", "dispPortal", false, false, false, false));
		listFields.add(new Field("siglaAbrev", "siglaAbrev", false, false, false, false));
		// listFields.add(new Field("quadroOrientPortal" ,"quadroOrientPortal", false, false, false, false));
		listFields.add(new Field("deleted", "deleted", false, false, false, false));
		listFields.add(new Field("idBaseconhecimento", "idBaseconhecimento", false, false, false, false));
        listFields.add(new Field("idTemplateSolicitacao", "idTemplateSolicitacao", false, false, false, false));
        listFields.add(new Field("idTemplateAcompanhamento", "idTemplateAcompanhamento", false, false, false, false));
		listFields.add(new Field("criadoEm" ,"criadoEm", false, false, false, false));
		listFields.add(new Field("criadoPor" ,"criadoPor", false, false, false, false));
		listFields.add(new Field("modificadoEm" ,"modificadoEm", false, false, false, false));
		listFields.add(new Field("modificadoPor" ,"modificadoPor", false, false, false, false));
		listFields.add(new Field("conteudodados" ,"conteudodados", false, false, false, false));        
		return listFields;
	}

	public String getTableName() {
		return this.getOwner() + "Servico_Hist";
	}

	public Collection list() throws PersistenceException {
		List ordenacao = new ArrayList();
		ordenacao.add(new Order("idServico"));
		return super.list(ordenacao);
	}
	
	public Collection findByIdServico(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idServico", "=", parm));
		return super.findByCondition(condicao, ordenacao);
	}	

	public Class getBean() {
		return HistoricoServicoDTO.class;
	}

	public Collection find(IDto arg0) throws PersistenceException {
		List ordenacao = new ArrayList();
		ordenacao.add(new Order("idServico"));
		return super.find(arg0, ordenacao);
	}

	public Collection findByIdCategoriaServico(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idCategoriaServico", "=", parm));
		ordenacao.add(new Order("idServicoContrato"));
		return super.findByCondition(condicao, ordenacao);
	}

	public void deleteByIdCategoriaServico(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idCategoriaServico", "=", parm));
		super.deleteByCondition(condicao);
	}

	public Collection findByIdSituacaoServico(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idSituacaoServico", "=", parm));
		ordenacao.add(new Order("idServico"));
		return super.findByCondition(condicao, ordenacao);
	}

	public void deleteByIdSituacaoServico(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idSituacaoServico", "=", parm));
		super.deleteByCondition(condicao);
	}

	public Collection findByIdTipoDemandaAndIdCategoria(Integer idTipoDemanda, Integer idCategoria) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idTipoDemandaServico", "=", idTipoDemanda));
		if (idCategoria != null)
			condicao.add(new Condition("idCategoriaServico", "=", idCategoria));
		ordenacao.add(new Order("nomeServico"));
		return super.findByCondition(condicao, ordenacao);
	}

	public Collection findByIdTipoDemandaAndIdContrato(Integer idTipoDemanda, Integer idContrato, Integer idCategoria) throws PersistenceException {
		Collection fields = getFields();
		List listaNomes = new ArrayList();
		String sql = "SELECT ";
		int i = 0;
		for (Iterator it = fields.iterator(); it.hasNext();) {
			Field field = (Field) it.next();
			if (i > 0) {
				sql += ",";
			}
			sql += field.getFieldDB();
			listaNomes.add(field.getFieldClass());
			i++;
		}
		sql += " FROM " + this.getTableName();
		sql += " WHERE idTipoDemandaServico = " + idTipoDemanda + " AND ";
		if (idCategoria != null) {
			sql += " idCategoriaServico = " + idCategoria + " AND ";
		}
		sql += " idServico IN (SELECT idservico FROM servicocontrato WHERE idcontrato = " + idContrato + " AND ";
		
		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.POSTGRESQL)) 
			sql += "(UPPER(deleted) IS NULL OR UPPER(deleted) = 'N')) ";
		 else 
			sql += "(deleted IS NULL OR deleted = 'N')) ";
		sql += " ORDER BY nomeServico";

		List listaR = this.execSQL(sql, null);
		return this.listConvertion(HistoricoServicoDTO.class, listaR, listaNomes);
	}

	/**
	 * Retorna sigla do servico pelo idOs.
	 * 
	 * @param idOs
	 * @return
	 * @throws Exception
	 */
	public String retornaSiglaPorIdOs(Integer idOs) throws PersistenceException {
		List lstParametros = new ArrayList();
		String sql = "SELECT servico.siglaabrev FROM servicocontrato";
		sql = sql + " INNER JOIN servico ON servico.idservico = servicocontrato.idservico";
		sql = sql + " INNER JOIN os ON os.idservicocontrato = servicocontrato.idservicocontrato";
		sql = sql + " WHERE os.idos = ? ";
		lstParametros.add(idOs);

		Object[] parametros = lstParametros.toArray();

		List lstDados = super.execSQL(sql, parametros);

		List fields = new ArrayList();
		fields.add("siglaAbrev");

		Collection<HistoricoServicoDTO> listDeFaturas = super.listConvertion(HistoricoServicoDTO.class, lstDados, fields);

		if (listDeFaturas != null && !listDeFaturas.isEmpty()) {
			for (HistoricoServicoDTO servico : listDeFaturas) {
				if (servico.getSiglaAbrev() != null && !servico.getSiglaAbrev().trim().equals("")) {
					return servico.getSiglaAbrev();
				} else {
					return "           -";
				}
			}
			return null;
		} else {
			return null;
		}

	}

	/**
	 * Retorna lista Serviço por nome.
	 * 
	 * @return Collection
	 * @throws Exception
	 */
	public Collection findByNome(HistoricoServicoDTO servicoDTO) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();

		condicao.add(new Condition("nomeServico", "=", servicoDTO.getNomeServico()));
		ordenacao.add(new Order("nomeServico"));
		return super.findByCondition(condicao, ordenacao);
	}

	/**
	 * Método para retornar apenas os serviços referente a unidade do solicitante
	 * 
	 * @author rodrigo.oliveira
	 * @param idServico
	 * @param idTipoDemanda
	 * @param idCategoria
	 * @return
	 * @throws Exception
	 */
	public Collection findByIdServicoAndIdTipoDemandaAndIdCategoria(Integer idServico, Integer idTipoDemanda, Integer idCategoria) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idTipoDemandaServico", "=", idTipoDemanda));
		if (idCategoria != null)
			condicao.add(new Condition("idCategoriaServico", "=", idCategoria));
		condicao.add(new Condition("idServico", "=", idServico));
		ordenacao.add(new Order("nomeServico"));
		return super.findByCondition(condicao, ordenacao);
	}
	
	public HistoricoServicoDTO restoreByIdServico(Integer idServico){
		
		return null;
	}

	public Collection<HistoricoServicoDTO> findByServico(Integer idServico) throws PersistenceException {
		List parametro = new ArrayList();
		List fields = new ArrayList();
		List list = new ArrayList();
		String sql = "   select nometiposervico, nomeservico, nomecategoriaservico, idServico from servico " + "inner join tiposervico  on servico.idtiposervico = tiposervico.idtiposervico "
				+ "inner join categoriaservico on servico.idcategoriaservico = categoriaservico.idcategoriaservico "
				+ "where ";
		
		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.POSTGRESQL)) 
			sql += "UPPER(servico.deleted) IS NULL ";
		else 
			sql += "servico.deleted IS NULL ";
		
		sql += "and situacao = 'A' and servico.idServico = ? and (dispportal = 'Y' or dispportal = 'S') order by nomecategoriaservico ";
		
		parametro.add(idServico);
		list = this.execSQL(sql, parametro.toArray());
		fields.add("nomeTipoServico");
		fields.add("nomeServico");
		fields.add("nomeCategoriaServico");
		fields.add("idServico");
		if (list != null && !list.isEmpty()) {
			return (Collection<HistoricoServicoDTO>) this.listConvertion(getBean(), list, fields);
		} else {
			return null;
		}
	}
	
	public Collection<HistoricoServicoDTO> findByServico(Integer idServico, String nome) throws PersistenceException {
		List parametro = new ArrayList();
		List fields = new ArrayList();
		List list = new ArrayList();
		String sql = "   select nometiposervico, nomeservico, nomecategoriaservico, idServico from servico " + "inner join tiposervico  on servico.idtiposervico = tiposervico.idtiposervico "
				+ "inner join categoriaservico on servico.idcategoriaservico = categoriaservico.idcategoriaservico "
				+ "where ";
		
		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.POSTGRESQL)) 
			sql += "UPPER(servico.deleted) IS NULL ";
		else 
			sql += "servico.deleted IS NULL ";
		
		sql += "and situacao = 'A' and servico.idServico = ? and (dispportal = 'Y' or dispportal = 'S') and servico.nomeservico like '%" + nome + "%' order by nomecategoriaservico ";
		
		parametro.add(idServico);
		list = this.execSQL(sql, parametro.toArray());
		fields.add("nomeTipoServico");
		fields.add("nomeServico");
		fields.add("nomeCategoriaServico");
		fields.add("idServico");
		if (list != null && !list.isEmpty()) {
			return (Collection<HistoricoServicoDTO>) this.listConvertion(getBean(), list, fields);
		} else {
			return null;
		}

	}
}
