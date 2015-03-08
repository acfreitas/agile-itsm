/**
 * 
 */
package br.com.centralit.citcorpore.integracao;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.PesquisaSatisfacaoDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.SQLConfig;

/**
 * @author valdoilo
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class PesquisaSatisfacaoDAO extends CrudDaoDefaultImpl {

	public PesquisaSatisfacaoDAO() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}

	@Override
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList();

		listFields.add(new Field("idpesquisasatisfacao", "idPesquisaSatisfacao", true, true, false, false));
		listFields.add(new Field("idsolicitacaoservico", "idSolicitacaoServico", false, false, false, false));
		listFields.add(new Field("nota", "nota", false, false, false, false));
		listFields.add(new Field("comentario", "comentario", false, false, false, false));

		return listFields;
	}

	@Override
	public String getTableName() {
		return "pesquisasatisfacao";
	}

	@Override
	public Collection list() throws PersistenceException {
		List ordenacao = new ArrayList();
		ordenacao.add(new Order("idAvaliacaoSatisfacao"));
		return super.list(ordenacao);
	}

	@Override
	public Class getBean() {
		return PesquisaSatisfacaoDTO.class;
	}

	public Collection<PesquisaSatisfacaoDTO> getPesquisaByIdSolicitacao(int idServico) {
		ArrayList<Condition> condicoes = new ArrayList<Condition>();
		condicoes.add(new Condition("idSolicitacaoServico", "=", idServico));
		Collection c = null;
		try {
			c = this.findByCondition(condicoes, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;
	}

	/**
	 * Alterado consulta para incluir responsável atual
	 * 22/12/2014 - 10:00
	 * @author thyen.chang
	 * @param pesquisaSatisfacaoDTO
	 * @return
	 * @throws Exception
	 */
	public Collection<PesquisaSatisfacaoDTO> relatorioPesquisaSatisfacao(PesquisaSatisfacaoDTO pesquisaSatisfacaoDTO) throws Exception {
		
		List parametro = new ArrayList();
		
		StringBuilder sql = new StringBuilder();
			
		boolean seLimita = false;
		if(pesquisaSatisfacaoDTO != null && pesquisaSatisfacaoDTO.getValorTopList() != null)
			seLimita = pesquisaSatisfacaoDTO.getValorTopList() != 0;
		
		/**
		 * Limita consulta no SQLServer
		 * 
		 * @author thyen.chang
		 */
			
		sql.append("SELECT DISTINCT ");
		if ((seLimita)&&(CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.SQLSERVER))){
			sql.append("TOP " + pesquisaSatisfacaoDTO.getValorTopList().toString() + " ");
		}
		sql.append(" 				ps.idpesquisasatisfacao, ");
		sql.append("                ps.idsolicitacaoservico, ");
		sql.append("                ps.nota, ");		
		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) { 
			sql.append("cast(ps.comentario as  varchar(4000)) comentario, ");
		} else if(CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)){
			sql.append("cast(ps.comentario as varchar2(4000)) comentario, ");
		} else{
			sql.append(" ps.comentario, ");
		}		
		sql.append("                ss.idsolicitante, ");
		sql.append("                e.nome, ");
		sql.append("                sc.idcontrato, ");
		sql.append("                C.numero, ");
		sql.append("                u.nome, ");
		sql.append("                ss.datahorainicio, ");
		sql.append("                ss.datahorafim, ");
		sql.append("                us.nome ");
		sql.append("FROM   pesquisasatisfacao ps ");
		sql.append("       INNER JOIN solicitacaoservico ss ");
		sql.append("               ON ss.idsolicitacaoservico = ps.idsolicitacaoservico ");
		sql.append("       INNER JOIN empregados e ");
		sql.append("               ON ss.idsolicitante = e.idempregado ");
		sql.append("       INNER JOIN servicocontrato sc ");
		sql.append("               ON sc.idservicocontrato = ss.idservicocontrato ");
		sql.append("       INNER JOIN contratos C ");
		sql.append("               ON C.idcontrato = sc.idcontrato ");
		sql.append("       INNER JOIN usuario u ");
		sql.append("               ON u.idusuario = ss.idresponsavel ");
		sql.append("       INNER JOIN execucaosolicitacao es ");
		sql.append("               ON es.idsolicitacaoservico = ss.idsolicitacaoservico ");
		sql.append("       INNER JOIN bpm_itemtrabalhofluxo itf ");
		sql.append("               ON es.idinstanciafluxo = itf.idinstancia ");
		sql.append("                  AND ( C.deleted IS NULL ");
		sql.append("                         OR UPPER(C.deleted) = UPPER('N') ) ");
		sql.append("		JOIN usuario us ");
		sql.append("			ON us.idusuario = ss.idsolicitante ");
		
		if (pesquisaSatisfacaoDTO.getIdSolicitacaoServico() != null) {
			sql.append("AND   ps.idsolicitacaoservico = ? ");
			parametro.add(pesquisaSatisfacaoDTO.getIdSolicitacaoServico());
		}
		
		if (pesquisaSatisfacaoDTO.getDataInicio() != null && pesquisaSatisfacaoDTO.getDataFim() != null) {
			sql.append("AND ss.datahorasolicitacao BETWEEN ? AND ? ");
			parametro.add(pesquisaSatisfacaoDTO.getDataInicio());
			if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
				parametro.add(pesquisaSatisfacaoDTO.getDataFim());
			} else {
				parametro.add(transformaHoraFinal(pesquisaSatisfacaoDTO.getDataFim()));
			}
		}
		
		if (pesquisaSatisfacaoDTO.getIdContrato() != null) {
			sql.append("AND   sc.idcontrato = ? ");
			parametro.add(pesquisaSatisfacaoDTO.getIdContrato());
		}
		
		if (pesquisaSatisfacaoDTO.getIdSolicitante() != null) {
			sql.append("AND   ss.idsolicitante = ? ");
			parametro.add(pesquisaSatisfacaoDTO.getIdSolicitante());
		}
		
		if (pesquisaSatisfacaoDTO.getNota() != null) {
			sql.append("AND   ps.nota = ? ");
			parametro.add(pesquisaSatisfacaoDTO.getNota());
		}
		
		/**
		 * Limita consulta no Oracle
		 * 
		 * @author thyen.chang
		 */
		
		if ((seLimita)&&(CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.ORACLE))){
			sql.append("WHERE ROWNUM <= ? ");
			parametro.add(pesquisaSatisfacaoDTO.getValorTopList());
		}
		
		/**
		 * Determina limite de resultados da consulta no Postrgre e MySql
		 * 
		 * @author thyen.chang
		 */
		if((seLimita) && ((CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.POSTGRESQL))||(CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.MYSQL))) ){
			sql.append(" LIMIT ? ");
			parametro.add(pesquisaSatisfacaoDTO.getValorTopList());
		}
		
		List lista = this.execSQL(sql.toString(), parametro.toArray());
		List<String> listRetorno = new ArrayList<String>();
		listRetorno.add("idPesquisaSatisfacao");
		listRetorno.add("idSolicitacaoServico");
		listRetorno.add("nota");
		listRetorno.add("comentario");
		listRetorno.add("idSolicitante");
		listRetorno.add("nomeSolicitante");
		listRetorno.add("idContrato");
		listRetorno.add("contrato");
		listRetorno.add("operador");
		listRetorno.add("dataHoraInicio");
		listRetorno.add("dataHoraFim");
		listRetorno.add("nomeResponsavelAtual");
		List result = this.engine.listConvertion(PesquisaSatisfacaoDTO.class, lista, listRetorno);
		if (result != null) {
			return (Collection<PesquisaSatisfacaoDTO>) result;
		} else {
			return null;
		}

	}

	private Timestamp transformaHoraFinal(Date data) throws ParseException {
		String dataHora = data + " 23:59:59";
		String pattern = "yyyy-MM-dd hh:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		java.util.Date d = sdf.parse(dataHora);
		java.sql.Timestamp sqlDate = new java.sql.Timestamp(d.getTime());
		return sqlDate;
	}
	
	public PesquisaSatisfacaoDTO findByIdSolicitacaoServico(Integer idSolicitacaoServico) throws Exception {
		List parametro = new ArrayList();
		List fields = new ArrayList();
		List list = new ArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT Max(bpmitem.datahorafinalizacao) AS datahorafim, ");
		sql.append("       bpmitem.idresponsavelatual, ");
		sql.append("       u.nome ");
		sql.append("FROM   bpm_itemtrabalhofluxo bpmitem ");
		sql.append("       INNER JOIN execucaosolicitacao es ");
		sql.append("               ON bpmitem.idinstancia = es.idinstanciafluxo ");
		sql.append("       INNER JOIN usuario u ");
		sql.append("               ON bpmitem.idresponsavelatual = u.idusuario ");
		sql.append("WHERE  es.idsolicitacaoservico = ? ");
		sql.append(" group by bpmitem.idresponsavelatual, u.nome");
		parametro.add(idSolicitacaoServico);
		list = this.execSQL(sql.toString(), parametro.toArray());
		fields.add("dataHoraFim");
		fields.add("idResponsavelAtual");
		fields.add("nomeResponsavelAtual");
		if (list != null && !list.isEmpty()) {
			return (PesquisaSatisfacaoDTO) this.listConvertion(getBean(), list, fields).get(0);
		} else {
			return null;
		}
	}

}
