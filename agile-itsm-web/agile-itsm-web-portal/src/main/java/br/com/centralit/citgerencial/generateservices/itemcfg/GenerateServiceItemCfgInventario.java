package br.com.centralit.citgerencial.generateservices.itemcfg;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import br.com.centralit.citcorpore.bean.GrupoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.negocio.GrupoItemConfiguracaoService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citgerencial.bean.GerencialGenerateService;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.JdbcEngine;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.SQLConfig;
import br.com.citframework.util.UtilDatas;

/**
 * Adaptado método para utilizar corretamento os filtros de busca
 * 29/12/2014 - 10:42
 * @author thyen.chang
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class GenerateServiceItemCfgInventario extends GerencialGenerateService {

	public List execute(HashMap parametersValues, Collection paramtersDefinition) throws ParseException {

		String datainicial = (String) parametersValues.get("PARAM.dataInicial");
		String datafinal = (String) parametersValues.get("PARAM.dataFinal");

		String idGrupoItemConfiguracaoPaiStr = (String) parametersValues.get("PARAM.idGrupoItemConfiguracaoPai");
		Integer idGrupoItemConfiguracaoPai = null;
		try {
			idGrupoItemConfiguracaoPai = new Integer(idGrupoItemConfiguracaoPaiStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String situacaoStr = (String) parametersValues.get("PARAM.situacao");
		Integer situacao = null;
		try {
			situacao = new Integer(situacaoStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String idCriticidadeStr = (String) parametersValues.get("PARAM.idCriticidade");
		Integer idCriticidade = null;
		try {
			idCriticidade = new Integer(idCriticidadeStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String idTipoItemConfiguracaoStr = (String) parametersValues.get("PARAM.idTipoItemConfiguracao");
		Integer idTipoItemConfiguracao = null;
		try {
			idTipoItemConfiguracao = new Integer(idTipoItemConfiguracaoStr);
		} catch (Exception e) {
			e.printStackTrace();
		}

		List parametros = new ArrayList();
		String sql = "Select identificacao, datainicio, dataexpiracao, numeroSerie, localidade, versao, status from itemconfiguracao where 1=1 ";

		if(datainicial != null && !datainicial.isEmpty()){
			sql += (" AND datainicio >= ? ");
			if(CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equalsIgnoreCase(SQLConfig.ORACLE))
				parametros.add(datainicial);
			else
				parametros.add(UtilDatas.convertStringToSQLDate(TipoDate.DATE_DEFAULT, datainicial, null));
		}
		if(datafinal != null && !datafinal.isEmpty()){
			sql += (" AND datafim <= ? ");
			if(CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equalsIgnoreCase(SQLConfig.ORACLE))
				parametros.add(datafinal);
			else
				parametros.add(UtilDatas.convertStringToSQLDate(TipoDate.DATE_DEFAULT, datafinal, null));
		}
		
		if (situacao != null && situacao.intValue() != -1) {
			sql += " AND status = ? ";
			parametros.add(situacao);
		}

		if (idCriticidade != null && idCriticidade.intValue() != -1) {
			sql += "AND criticidade = ? ";
			parametros.add(idCriticidade);
		}
		if (idTipoItemConfiguracao != null && idTipoItemConfiguracao.intValue() != -1) {
			sql += "AND idtipoitemconfiguracao = ? ";
			parametros.add(idTipoItemConfiguracao);
		}
		GrupoItemConfiguracaoService grupoItemConfiguracaoService = null;
		try {
			grupoItemConfiguracaoService = (GrupoItemConfiguracaoService) ServiceLocator.getInstance().getService(GrupoItemConfiguracaoService.class, null);
		} catch (ServiceException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		Collection colGrupos = null;
		try {
			if (grupoItemConfiguracaoService != null) {
				colGrupos = grupoItemConfiguracaoService.listHierarquiaGruposByIdGrupo(idGrupoItemConfiguracaoPai, null);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if (idGrupoItemConfiguracaoPai != null && idGrupoItemConfiguracaoPai.intValue() == 997) {
			Collection colGrupos2 = null;
			try {
				colGrupos2 = grupoItemConfiguracaoService.listHierarquiaGrupoPaiNull();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (colGrupos2 != null) {
				colGrupos.addAll(colGrupos2);
			}
		}

		if (idGrupoItemConfiguracaoPai != null && idGrupoItemConfiguracaoPai.intValue() != -1) {
			sql += "AND idgrupoitemconfiguracao IN (";
			String strGrp = "";
			for (Iterator it = colGrupos.iterator(); it.hasNext();) {
				GrupoItemConfiguracaoDTO grupoItemConfiguracaoDTO = (GrupoItemConfiguracaoDTO) it.next();
				if (!strGrp.equalsIgnoreCase("")) {
					strGrp = strGrp + ",";
				}
				strGrp = strGrp + grupoItemConfiguracaoDTO.getIdGrupoItemConfiguracao();
			}
			sql += strGrp;
			sql += ")";
		}

		List lista = null;
		List listaNew = new ArrayList();

		JdbcEngine jdbcEngine = new JdbcEngine(Constantes.getValue("DATABASE_ALIAS"), null);
		try {
			lista = jdbcEngine.execSQL(sql.toString(), parametros.toArray(), 0);
			if (lista != null) {
				for (Iterator it = lista.iterator(); it.hasNext();) {
					Object[] objsNew = new Object[7];
					Object[] objs = (Object[]) it.next();

					int x = 0;
					if (objs[6] != null) {
						try {
							if (objs[6] instanceof Integer) {
								x = (Integer) (objs[6]);
							}
							if (objs[6] instanceof Long) {
								x = new Integer(((Long) (objs[6])).intValue());
							}
						} catch (Exception e) {
						}
					}

					objsNew[0] = objs[0];
					objsNew[1] = objs[1];
					objsNew[2] = objs[2];
					objsNew[3] = objs[3];
					objsNew[4] = objs[4];
					objsNew[5] = objs[5];
					Enumerados.StatusIC st = Enumerados.StatusIC.getStatus(x);
					if (st != null) {
						objsNew[6] = st.getDescricao();
					} else {
						objsNew[6] = "";
					}
					listaNew.add(objsNew);
				}
			}
		} catch (PersistenceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (listaNew == null || listaNew.size() == 0) {
			// listaNew = new ArrayList();
			// Object[] objFinal = { "", "", "", null, "", "", "" };
			// listaNew.add(objFinal);
		}

		return listaNew;
	}
	
	

}
