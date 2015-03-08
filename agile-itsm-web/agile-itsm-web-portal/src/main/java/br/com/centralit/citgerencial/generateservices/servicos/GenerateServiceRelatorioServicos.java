package br.com.centralit.citgerencial.generateservices.servicos;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.integracao.ContratoDao;
import br.com.centralit.citcorpore.integracao.ServicoContratoDao;
import br.com.centralit.citcorpore.integracao.SolicitacaoServicoDao;
import br.com.centralit.citcorpore.negocio.AcordoNivelServicoService;
import br.com.centralit.citcorpore.negocio.AcordoServicoContratoService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citgerencial.bean.GerencialGenerateService;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.JdbcEngine;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.SQLConfig;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilStrings;

/**
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class GenerateServiceRelatorioServicos extends GerencialGenerateService {

	public List execute(HashMap parametersValues, Collection paramtersDefinition) throws ParseException {

		String datainicial = (String) parametersValues.get("PARAM.dataInicial");
		String datafinal = (String) parametersValues.get("PARAM.dataFinal");

		Date datafim = new Date();
		Date datainicio = new Date();

		try {
			datainicio = UtilDatas.convertStringToDate(TipoDate.DATE_DEFAULT, datainicial, super.getLanguage(paramtersDefinition));
			datafim = UtilDatas.convertStringToDate(TipoDate.DATE_DEFAULT, datafinal, super.getLanguage(paramtersDefinition));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		String idImportanciaNegocioStr = (String) parametersValues.get("PARAM.idImportanciaNegocio");
		Integer idImportanciaNegocio = null;
		try {
			idImportanciaNegocio = new Integer(idImportanciaNegocioStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// ---
		String idSituacaoServicoStr = (String) parametersValues.get("PARAM.idSituacaoServico");
		Integer idSituacaoServico = null;
		try {
			idSituacaoServico = new Integer(idSituacaoServicoStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// ---
		String idTipoServicoStr = (String) parametersValues.get("PARAM.idTipoServico");
		Integer idTipoServico = null;
		try {
			idTipoServico = new Integer(idTipoServicoStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// ---
		String idCategoriaServicoStr = (String) parametersValues.get("PARAM.idCategoriaServico");
		Integer idCategoriaServico = null;
		try {
			idCategoriaServico = new Integer(idCategoriaServicoStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// ---

		List parametros = new ArrayList();
		String sql = "Select nomesituacaoservico, nomeservico, detalheservico, s.datainicio, nometiposervico, nomeimportancianegocio, nomeCategoriaServico, s.idservico from servico s inner join tiposervico ts on ts.idtiposervico = s.idtiposervico ";
		sql += "left join importancianegocio inn on inn.idimportancianegocio = s.idimportancianegocio ";
		sql += "inner join situacaoservico ss on ss.idsituacaoservico = s.idsituacaoservico ";
		sql += "inner join categoriaservico cs on cs.idCategoriaServico = s.idCategoriaServico ";

		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
			sql += ("where to_char(s.datainicio, 'YYYY-MM-DD') BETWEEN ? AND ? ");
			String dataInicialDate = UtilDatas.convertDateToString(TipoDate.FORMAT_DATABASE, datainicio, super.getLanguage(paramtersDefinition));
			String dataFinalDate = UtilDatas.convertDateToString(TipoDate.FORMAT_DATABASE, datafim, super.getLanguage(paramtersDefinition));
			parametros.add(dataInicialDate);
			parametros.add(dataFinalDate);
		} else {
			sql += "where s.datainicio between ? and ? ";
			parametros.add(UtilDatas.convertStringToSQLDate(TipoDate.DATE_DEFAULT, datainicial, super.getLanguage(paramtersDefinition)));
			parametros.add(UtilDatas.convertStringToSQLDate(TipoDate.DATE_DEFAULT, datafinal, super.getLanguage(paramtersDefinition)));
		}

		if (idImportanciaNegocio != null && idImportanciaNegocio.intValue() != -1) {
			sql += "AND s.idimportancianegocio = ? ";
			parametros.add(idImportanciaNegocio);
		}

		if (idSituacaoServico != null && idSituacaoServico.intValue() != -1) {
			sql += "AND s.idsituacaoservico = ? ";
			parametros.add(idSituacaoServico);
		}

		if (idTipoServico != null && idTipoServico.intValue() != -1) {
			sql += "AND s.idtiposervico = ? ";
			parametros.add(idTipoServico);
		}

		if (idCategoriaServico != null && idCategoriaServico.intValue() != -1) {
			sql += "AND s.idCategoriaServico = ? ";
			parametros.add(idCategoriaServico);
		}

		List lista = null;

		JdbcEngine jdbcEngine = new JdbcEngine(Constantes.getValue("DATABASE_ALIAS"), null);
		try {
			lista = jdbcEngine.execSQL(sql.toString(), parametros.toArray(), 0);
		} catch (PersistenceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		List listaFinal = null;
		if (lista != null) {
			listaFinal = new ArrayList();
			ServicoContratoDao servicoContratoDao = new ServicoContratoDao();
			ContratoDao contratoDao = new ContratoDao();
			SolicitacaoServicoDao solicitacaoServicoDao = new SolicitacaoServicoDao();
			AcordoNivelServicoService acordoNivelServicoService = null;
			try {
				acordoNivelServicoService = (AcordoNivelServicoService) ServiceLocator.getInstance().getService(AcordoNivelServicoService.class, null);
			} catch (ServiceException e1) {
				e1.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			AcordoServicoContratoService acordoServicoContratoService = null;
			try {
				acordoServicoContratoService = (AcordoServicoContratoService) ServiceLocator.getInstance().getService(AcordoServicoContratoService.class, null);
			} catch (ServiceException e1) {
				e1.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			String contratos = (String) parametersValues.get("grupovisao.contratos");
			String temSLA = (String) parametersValues.get("citcorpore.comum.temSLA");
			String naoTemSLA = (String) parametersValues.get("citcorpore.comum.naoTemSLA");
			String numeroSolicitacoesIncidentes = (String) parametersValues.get("citcorpore.comum.numeroSolicitacoesIncidentes");

			for (Iterator it = lista.iterator(); it.hasNext();) {
				Object[] objs = (Object[]) it.next();
				Object[] objFinal = new Object[8];
				for (int i = 0; i <= 6; i++) {
					objFinal[i] = objs[i];
				}
				try {
					Integer idServ = null;
					if (objs[7] instanceof Long) {
						idServ = new Integer(((Long) objs[7]).intValue());
					} else if (objs[7] instanceof Integer) {
						idServ = new Integer(((Integer) objs[7]).intValue());
					}
					Collection col = servicoContratoDao.findByIdServico(idServ);
					if (col != null && col.size() > 0) {
						String aux = "";
						aux += contratos + ":\n";
						for (Iterator it2 = col.iterator(); it2.hasNext();) {
							ServicoContratoDTO servicoContratoDTO = (ServicoContratoDTO) it2.next();
							ContratoDTO contratoDTO = new ContratoDTO();
							contratoDTO.setIdContrato(servicoContratoDTO.getIdContrato());
							contratoDTO = (ContratoDTO) contratoDao.restore(contratoDTO);
							if (contratoDTO != null) {
								aux += "" + contratoDTO.getNumero() + "  (" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, servicoContratoDTO.getDataInicio(), super.getLanguage(paramtersDefinition)) + " - "
										+ UtilStrings.nullToVazio(UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, servicoContratoDTO.getDataFim(), super.getLanguage(paramtersDefinition))) + ") ";
								Collection colX = acordoNivelServicoService.findByIdServicoContrato(servicoContratoDTO.getIdServicoContrato());
								Collection colVincs = acordoServicoContratoService.findByIdServicoContrato(servicoContratoDTO.getIdServicoContrato());
								if ((colX != null && colX.size() > 0) || (colVincs != null && colVincs.size() > 0)) {
									aux += " - " + temSLA;
								} else {
									aux += " - " + naoTemSLA;
								}
							}
						}
						Integer qtde = solicitacaoServicoDao.getQuantidadeByIdServico(idServ);
						if (qtde != null) {
							aux += "\n\n" + numeroSolicitacoesIncidentes + ": " + qtde;
						}
						objFinal[7] = aux;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				listaFinal.add(objFinal);
			}
		}
		if (listaFinal == null || listaFinal.size() == 0) {
			return null;
		}
		return listaFinal;
	}

}
