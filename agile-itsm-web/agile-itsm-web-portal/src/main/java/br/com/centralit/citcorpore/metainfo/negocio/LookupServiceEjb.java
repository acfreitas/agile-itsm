package br.com.centralit.citcorpore.metainfo.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringEscapeUtils;

import br.com.centralit.citcorpore.metainfo.bean.CamposObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.GrupoVisaoCamposNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.GrupoVisaoCamposNegocioLigacaoDTO;
import br.com.centralit.citcorpore.metainfo.bean.LookupDTO;
import br.com.centralit.citcorpore.metainfo.bean.ObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.ReturnLookupDTO;
import br.com.centralit.citcorpore.metainfo.integracao.CamposObjetoNegocioDao;
import br.com.centralit.citcorpore.metainfo.integracao.GrupoVisaoCamposNegocioDao;
import br.com.centralit.citcorpore.metainfo.integracao.GrupoVisaoCamposNegocioLigacaoDao;
import br.com.centralit.citcorpore.metainfo.integracao.LookupDao;
import br.com.centralit.citcorpore.metainfo.integracao.ObjetoNegocioDao;
import br.com.centralit.citcorpore.metainfo.util.MetaUtil;
import br.com.citframework.service.CrudServiceImpl;

public class LookupServiceEjb extends CrudServiceImpl implements LookupService {
	
	private LookupDao lookupDao;
	
	protected LookupDao getDao() {
		if(lookupDao == null) {
			lookupDao = new LookupDao();
		}
		return lookupDao;
	}

	public Collection findSimple(LookupDTO parm) throws Exception{
		GrupoVisaoCamposNegocioDao grupoVisaoCamposNegocioDao = new GrupoVisaoCamposNegocioDao();
		GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = new GrupoVisaoCamposNegocioDTO();
		
		GrupoVisaoCamposNegocioLigacaoDao grupoVisaoCamposNegocioLigacaoDao = new GrupoVisaoCamposNegocioLigacaoDao();
		GrupoVisaoCamposNegocioLigacaoDTO grupoVisaoCamposNegocioLigacaoDTO = new GrupoVisaoCamposNegocioLigacaoDTO();
		
		CamposObjetoNegocioDao camposObjetoNegocioDao = new CamposObjetoNegocioDao();
		CamposObjetoNegocioDTO camposObjetoNegocioDTO = new CamposObjetoNegocioDTO();
		
		grupoVisaoCamposNegocioDTO.setIdGrupoVisao(parm.getIdGrupoVisao());
		grupoVisaoCamposNegocioDTO.setIdCamposObjetoNegocio(parm.getIdCamposObjetoNegocio());
		grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO) grupoVisaoCamposNegocioDao.restore(grupoVisaoCamposNegocioDTO);
		if (grupoVisaoCamposNegocioDTO == null){
			return null;
		}
		
		if (grupoVisaoCamposNegocioDTO.getTipoLigacao() == null){
			grupoVisaoCamposNegocioDTO.setTipoLigacao(GrupoVisaoCamposNegocioDTO.RELATION_SIMPLE);
		}
		if (!grupoVisaoCamposNegocioDTO.getTipoLigacao().equalsIgnoreCase(GrupoVisaoCamposNegocioDTO.RELATION_SIMPLE) &&
				!grupoVisaoCamposNegocioDTO.getTipoLigacao().equalsIgnoreCase(GrupoVisaoCamposNegocioDTO.RELATION_COMBO)){
			grupoVisaoCamposNegocioDTO.setTipoLigacao(GrupoVisaoCamposNegocioDTO.RELATION_COMBO);
		}
		
		Collection colPresentation = new ArrayList();
		Collection colValue = new ArrayList();
		Collection colOrder = new ArrayList();
		Collection colFilter = new ArrayList();
		
		String sql = "";
		if (grupoVisaoCamposNegocioDTO.getTipoLigacao().equalsIgnoreCase(GrupoVisaoCamposNegocioDTO.RELATION_SIMPLE) ||
				grupoVisaoCamposNegocioDTO.getTipoLigacao().equalsIgnoreCase(GrupoVisaoCamposNegocioDTO.RELATION_COMBO)){
			Collection colItens = grupoVisaoCamposNegocioLigacaoDao.findByIdGrupoVisaoAndIdCamposObjetoNegocio(parm.getIdGrupoVisao(), 
					parm.getIdCamposObjetoNegocio());
			if (colItens != null){
				for(Iterator it = colItens.iterator(); it.hasNext();){
					GrupoVisaoCamposNegocioLigacaoDTO grupoVisaoCamposNegocioLigacaoAux = (GrupoVisaoCamposNegocioLigacaoDTO)it.next();
					
					if (grupoVisaoCamposNegocioLigacaoAux.getTipoLigacao().equalsIgnoreCase(GrupoVisaoCamposNegocioLigacaoDTO.PRESENTATION)){
						camposObjetoNegocioDTO.setIdCamposObjetoNegocio(grupoVisaoCamposNegocioLigacaoAux.getIdCamposObjetoNegocioLigacao());
						camposObjetoNegocioDTO = (CamposObjetoNegocioDTO) camposObjetoNegocioDao.restore(camposObjetoNegocioDTO);
						if (camposObjetoNegocioDTO != null){
							camposObjetoNegocioDTO.setDescricao(grupoVisaoCamposNegocioLigacaoAux.getDescricao());
							colPresentation.add(camposObjetoNegocioDTO);
						}
					}
					if (grupoVisaoCamposNegocioLigacaoAux.getTipoLigacao().equalsIgnoreCase(GrupoVisaoCamposNegocioLigacaoDTO.VALUE)){
						camposObjetoNegocioDTO.setIdCamposObjetoNegocio(grupoVisaoCamposNegocioLigacaoAux.getIdCamposObjetoNegocioLigacao());
						camposObjetoNegocioDTO = (CamposObjetoNegocioDTO) camposObjetoNegocioDao.restore(camposObjetoNegocioDTO);
						if (camposObjetoNegocioDTO != null){
							colValue.add(camposObjetoNegocioDTO);
						}
					}
					if (grupoVisaoCamposNegocioLigacaoAux.getTipoLigacao().equalsIgnoreCase(GrupoVisaoCamposNegocioLigacaoDTO.ORDER)){
						camposObjetoNegocioDTO.setIdCamposObjetoNegocio(grupoVisaoCamposNegocioLigacaoAux.getIdCamposObjetoNegocioLigacao());
						camposObjetoNegocioDTO = (CamposObjetoNegocioDTO) camposObjetoNegocioDao.restore(camposObjetoNegocioDTO);
						if (camposObjetoNegocioDTO != null){
							colOrder.add(camposObjetoNegocioDTO);
						}
					}
					if (grupoVisaoCamposNegocioLigacaoAux.getTipoLigacao().equalsIgnoreCase(GrupoVisaoCamposNegocioLigacaoDTO.FILTER)){
						camposObjetoNegocioDTO.setIdCamposObjetoNegocio(grupoVisaoCamposNegocioLigacaoAux.getIdCamposObjetoNegocioLigacao());
						camposObjetoNegocioDTO = (CamposObjetoNegocioDTO) camposObjetoNegocioDao.restore(camposObjetoNegocioDTO);
						if (camposObjetoNegocioDTO != null){
							camposObjetoNegocioDTO.setFiltro(grupoVisaoCamposNegocioLigacaoAux.getFiltro().replace("\"", "\'") );
							colFilter.add(camposObjetoNegocioDTO);
						}
					}					
				}
			}
			
			sql = generateSQL(colPresentation, colValue, colFilter, colOrder, parm.getTermoPesquisa());
		}
		
		Collection colRetorno = null;
		if (!sql.trim().equalsIgnoreCase("SELECT  FROM")){
			colRetorno = this.getDao().execSQL(sql, null);
		}
		Collection colRetornoLookup = new ArrayList();
		if (colRetorno != null){
			int tamCamposValue = colValue.size();
			for(Iterator it = colRetorno.iterator(); it.hasNext();){
				String lineLabel = "";
				String lineValue = "";
				ReturnLookupDTO returnLookupDTO = new ReturnLookupDTO();
				Object[] objs = (Object[])it.next();
				for(int i = 0; i < objs.length; i++){
					if(objs[i] != null){
						if (i >= tamCamposValue){
							if (!lineLabel.equalsIgnoreCase("")){
								lineLabel += ", ";
							}
							lineLabel += objs[i].toString();
						}else{
							if (!lineValue.equalsIgnoreCase("")){
								lineValue += "#";
							}
							lineValue += objs[i].toString();						
						}						
					}				
				}
				returnLookupDTO.setLabel(lineLabel);
				returnLookupDTO.setValue(lineValue);
				
				colRetornoLookup.add(returnLookupDTO);
			}
		}
		return colRetornoLookup;
	}
	public String findSimpleString(LookupDTO parm) throws Exception{
		GrupoVisaoCamposNegocioDao grupoVisaoCamposNegocioDao = new GrupoVisaoCamposNegocioDao();
		GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = new GrupoVisaoCamposNegocioDTO();
		
		GrupoVisaoCamposNegocioLigacaoDao grupoVisaoCamposNegocioLigacaoDao = new GrupoVisaoCamposNegocioLigacaoDao();
		GrupoVisaoCamposNegocioLigacaoDTO grupoVisaoCamposNegocioLigacaoDTO = new GrupoVisaoCamposNegocioLigacaoDTO();
		
		CamposObjetoNegocioDao camposObjetoNegocioDao = new CamposObjetoNegocioDao();
		CamposObjetoNegocioDTO camposObjetoNegocioDTO = new CamposObjetoNegocioDTO();
		
		grupoVisaoCamposNegocioDTO.setIdGrupoVisao(parm.getIdGrupoVisao());
		grupoVisaoCamposNegocioDTO.setIdCamposObjetoNegocio(parm.getIdCamposObjetoNegocio());
		grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO) grupoVisaoCamposNegocioDao.restore(grupoVisaoCamposNegocioDTO);
		if (grupoVisaoCamposNegocioDTO == null){
			return null;
		}
		
		if (grupoVisaoCamposNegocioDTO.getTipoLigacao() == null){
			grupoVisaoCamposNegocioDTO.setTipoLigacao(GrupoVisaoCamposNegocioDTO.RELATION_SIMPLE);
		}
		
		Collection colPresentation = new ArrayList();
		Collection colValue = new ArrayList();
		Collection colOrder = new ArrayList();
		Collection colFilter = new ArrayList();
		
		String sql = "";
		if (grupoVisaoCamposNegocioDTO.getTipoLigacao().equalsIgnoreCase(GrupoVisaoCamposNegocioDTO.RELATION_SIMPLE) ||
				grupoVisaoCamposNegocioDTO.getTipoLigacao().equalsIgnoreCase(GrupoVisaoCamposNegocioDTO.RELATION_COMBO) ||
				grupoVisaoCamposNegocioDTO.getTipoLigacao().equalsIgnoreCase(GrupoVisaoCamposNegocioDTO.RELATION_NONE)){
			Collection colItens = grupoVisaoCamposNegocioLigacaoDao.findByIdGrupoVisaoAndIdCamposObjetoNegocio(parm.getIdGrupoVisao(), 
					parm.getIdCamposObjetoNegocio());
			if (colItens != null){
				for(Iterator it = colItens.iterator(); it.hasNext();){
					GrupoVisaoCamposNegocioLigacaoDTO grupoVisaoCamposNegocioLigacaoAux = (GrupoVisaoCamposNegocioLigacaoDTO)it.next();
					
					if (grupoVisaoCamposNegocioLigacaoAux.getTipoLigacao().equalsIgnoreCase(GrupoVisaoCamposNegocioLigacaoDTO.PRESENTATION)){
						camposObjetoNegocioDTO.setIdCamposObjetoNegocio(grupoVisaoCamposNegocioLigacaoAux.getIdCamposObjetoNegocioLigacao());
						camposObjetoNegocioDTO = (CamposObjetoNegocioDTO) camposObjetoNegocioDao.restore(camposObjetoNegocioDTO);
						if (camposObjetoNegocioDTO != null){
							camposObjetoNegocioDTO.setDescricao(grupoVisaoCamposNegocioLigacaoAux.getDescricao());
							colPresentation.add(camposObjetoNegocioDTO);
						}
					}
					if (grupoVisaoCamposNegocioLigacaoAux.getTipoLigacao().equalsIgnoreCase(GrupoVisaoCamposNegocioLigacaoDTO.VALUE)){
						camposObjetoNegocioDTO.setIdCamposObjetoNegocio(grupoVisaoCamposNegocioLigacaoAux.getIdCamposObjetoNegocioLigacao());
						camposObjetoNegocioDTO = (CamposObjetoNegocioDTO) camposObjetoNegocioDao.restore(camposObjetoNegocioDTO);
						if (camposObjetoNegocioDTO != null){
							colValue.add(camposObjetoNegocioDTO);
						}
					}
					if (grupoVisaoCamposNegocioLigacaoAux.getTipoLigacao().equalsIgnoreCase(GrupoVisaoCamposNegocioLigacaoDTO.ORDER)){
						camposObjetoNegocioDTO.setIdCamposObjetoNegocio(grupoVisaoCamposNegocioLigacaoAux.getIdCamposObjetoNegocioLigacao());
						camposObjetoNegocioDTO = (CamposObjetoNegocioDTO) camposObjetoNegocioDao.restore(camposObjetoNegocioDTO);
						if (camposObjetoNegocioDTO != null){
							colOrder.add(camposObjetoNegocioDTO);
						}
					}
					if (grupoVisaoCamposNegocioLigacaoAux.getTipoLigacao().equalsIgnoreCase(GrupoVisaoCamposNegocioLigacaoDTO.FILTER)){
						camposObjetoNegocioDTO.setIdCamposObjetoNegocio(grupoVisaoCamposNegocioLigacaoAux.getIdCamposObjetoNegocioLigacao());
						camposObjetoNegocioDTO = (CamposObjetoNegocioDTO) camposObjetoNegocioDao.restore(camposObjetoNegocioDTO);
						if (camposObjetoNegocioDTO != null){
							camposObjetoNegocioDTO.setFiltro(grupoVisaoCamposNegocioLigacaoAux.getFiltro().replace("\"", "'"));
							colFilter.add(camposObjetoNegocioDTO);
						}
					}					
				}
			}
			
			sql = generateSQL(colPresentation, colValue, colFilter, colOrder, parm.getTermoPesquisa());
		}
		
		Collection colGeral = new ArrayList();
		if (colValue != null){
			colGeral.addAll(colValue);
		}
		if (colPresentation != null){
			colGeral.addAll(colPresentation);
		}		
		Collection colRetorno = this.getDao().execSQL(sql, null);
		Collection colRetornoLookup = new ArrayList();
		String retorno = "";
		boolean prim = true;
		if (colRetorno != null){
			int tamCamposValue = colValue.size();
			for(Iterator it = colRetorno.iterator(); it.hasNext();){
				Object[] objs = (Object[])it.next();
				if (!prim){
					retorno += ",";
				}
				retorno += "{";
				Iterator it2 = colGeral.iterator();
				
				for(int i = 0; i < objs.length; i++){
					if (i > 0){
						retorno += ",";
					}
					if (it2.hasNext()){
						camposObjetoNegocioDTO = (CamposObjetoNegocioDTO)it2.next();
						retorno += "\"" + camposObjetoNegocioDTO.getNomeDB() + "\":";
						retorno += "\"" + StringEscapeUtils.escapeJava(objs[i].toString()) + "\"";
					}
				}
				retorno += "}";
				prim = false;
			}
		}
		return retorno;
	}
	
	public ReturnLookupDTO restoreSimple(LookupDTO parm) throws Exception{
		GrupoVisaoCamposNegocioDao grupoVisaoCamposNegocioDao = new GrupoVisaoCamposNegocioDao();
		GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = new GrupoVisaoCamposNegocioDTO();
		
		GrupoVisaoCamposNegocioLigacaoDao grupoVisaoCamposNegocioLigacaoDao = new GrupoVisaoCamposNegocioLigacaoDao();
		GrupoVisaoCamposNegocioLigacaoDTO grupoVisaoCamposNegocioLigacaoDTO = new GrupoVisaoCamposNegocioLigacaoDTO();
		
		CamposObjetoNegocioDao camposObjetoNegocioDao = new CamposObjetoNegocioDao();
		CamposObjetoNegocioDTO camposObjetoNegocioDTO = new CamposObjetoNegocioDTO();
		
		grupoVisaoCamposNegocioDTO.setIdGrupoVisao(parm.getIdGrupoVisao());
		grupoVisaoCamposNegocioDTO.setIdCamposObjetoNegocio(parm.getIdCamposObjetoNegocio());
		grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO) grupoVisaoCamposNegocioDao.restore(grupoVisaoCamposNegocioDTO);
		if (grupoVisaoCamposNegocioDTO == null){
			return null;
		}
		
		if (grupoVisaoCamposNegocioDTO.getTipoLigacao() == null){
			grupoVisaoCamposNegocioDTO.setTipoLigacao(GrupoVisaoCamposNegocioDTO.RELATION_SIMPLE);
		}
		
		Collection colPresentation = new ArrayList();
		Collection colValue = new ArrayList();
		Collection colOrder = new ArrayList();
		Collection colFilter = new ArrayList();
		
		String sql = "";
		Collection colItens = grupoVisaoCamposNegocioLigacaoDao.findByIdGrupoVisaoAndIdCamposObjetoNegocio(parm.getIdGrupoVisao(), 
				parm.getIdCamposObjetoNegocio());
		if (colItens != null){
			for(Iterator it = colItens.iterator(); it.hasNext();){
				GrupoVisaoCamposNegocioLigacaoDTO grupoVisaoCamposNegocioLigacaoAux = (GrupoVisaoCamposNegocioLigacaoDTO)it.next();
				
				if (grupoVisaoCamposNegocioLigacaoAux.getTipoLigacao().equalsIgnoreCase(GrupoVisaoCamposNegocioLigacaoDTO.PRESENTATION)){
					camposObjetoNegocioDTO.setIdCamposObjetoNegocio(grupoVisaoCamposNegocioLigacaoAux.getIdCamposObjetoNegocioLigacao());
					camposObjetoNegocioDTO = (CamposObjetoNegocioDTO) camposObjetoNegocioDao.restore(camposObjetoNegocioDTO);
					if (camposObjetoNegocioDTO != null){
						camposObjetoNegocioDTO.setDescricao(grupoVisaoCamposNegocioLigacaoAux.getDescricao());
						colPresentation.add(camposObjetoNegocioDTO);
					}
				}
				if (grupoVisaoCamposNegocioLigacaoAux.getTipoLigacao().equalsIgnoreCase(GrupoVisaoCamposNegocioLigacaoDTO.VALUE)){
					camposObjetoNegocioDTO.setIdCamposObjetoNegocio(grupoVisaoCamposNegocioLigacaoAux.getIdCamposObjetoNegocioLigacao());
					camposObjetoNegocioDTO = (CamposObjetoNegocioDTO) camposObjetoNegocioDao.restore(camposObjetoNegocioDTO);
					if (camposObjetoNegocioDTO != null){
						camposObjetoNegocioDTO.setFiltro("${TERMO_PESQUISA}");
						colValue.add(camposObjetoNegocioDTO);
						colFilter.add(camposObjetoNegocioDTO);
					}
				}
				if (grupoVisaoCamposNegocioLigacaoAux.getTipoLigacao().equalsIgnoreCase(GrupoVisaoCamposNegocioLigacaoDTO.ORDER)){
					camposObjetoNegocioDTO.setIdCamposObjetoNegocio(grupoVisaoCamposNegocioLigacaoAux.getIdCamposObjetoNegocioLigacao());
					camposObjetoNegocioDTO = (CamposObjetoNegocioDTO) camposObjetoNegocioDao.restore(camposObjetoNegocioDTO);
					if (camposObjetoNegocioDTO != null){
						colOrder.add(camposObjetoNegocioDTO);
					}
				}					
			}
		}
		
		sql = generateSQL(colPresentation, colValue, colFilter, colOrder, parm.getTermoPesquisa());
		
		Collection colRetorno = this.getDao().execSQL(sql, null);
		Collection colRetornoLookup = new ArrayList();
		if (colRetorno != null){
			int tamCamposValue = colValue.size();
			for(Iterator it = colRetorno.iterator(); it.hasNext();){
				String lineLabel = "";
				String lineValue = "";
				ReturnLookupDTO returnLookupDTO = new ReturnLookupDTO();
				Object[] objs = (Object[])it.next();
				for(int i = 0; i < objs.length; i++){
					if (i >= tamCamposValue){
						if (!lineLabel.equalsIgnoreCase("")){
							lineLabel += ", ";
						}
						lineLabel += objs[i].toString();
					}else{
						if (!lineValue.equalsIgnoreCase("")){
							lineValue += "#";
						}
						lineValue += objs[i].toString();						
					}
				}
				returnLookupDTO.setLabel(lineLabel);
				returnLookupDTO.setValue(lineValue);
				
				colRetornoLookup.add(returnLookupDTO);
			}
		}
		
		if (colRetornoLookup == null){
			return null;
		}
		if (colRetornoLookup.size() == 0){
			return null;
		}
		
		return (ReturnLookupDTO) ((List)colRetornoLookup).get(0);
	}	
	
	private String generateSQL(Collection colPresentation, Collection colValue, Collection colFilter, Collection colOrder, String termoPesquisa) throws Exception{
		String sql = "SELECT ";
		
		sql += generateFields(colPresentation, colValue);
		sql += " FROM ";
		sql += generateFromWithRelatios(colPresentation, colFilter);
		
		String strFilter = generateFilter(colFilter, termoPesquisa);
		if (!strFilter.equalsIgnoreCase("")){
			sql += " WHERE " + strFilter;
		}
		
		String strOrder = generateOrder(colOrder);
		if (!strOrder.equalsIgnoreCase("")){
			sql += " ORDER BY " + strOrder;
		}		
		
		return sql;
	}
	
	private String generateFields(Collection colPresentation, Collection colValue) throws Exception{
		ObjetoNegocioDao objetoNegocioDao = new ObjetoNegocioDao();
		String sqlFields = "";
		if (colPresentation != null){
			int i = 1;
			for(Iterator it = colValue.iterator(); it.hasNext();){
				CamposObjetoNegocioDTO camposObjetoNegocioDTO = (CamposObjetoNegocioDTO)it.next();
				ObjetoNegocioDTO objetoNegocioDTO = new ObjetoNegocioDTO();
				
				objetoNegocioDTO.setIdObjetoNegocio(camposObjetoNegocioDTO.getIdObjetoNegocio());
				objetoNegocioDTO = (ObjetoNegocioDTO) objetoNegocioDao.restore(objetoNegocioDTO);
				
				if (objetoNegocioDTO != null){
					if (!sqlFields.equalsIgnoreCase("")){
						sqlFields += ", ";
					}
					sqlFields += objetoNegocioDTO.getNomeTabelaDB() + "." + camposObjetoNegocioDTO.getNomeDB() + " Val_" + i;
				}
				i++;
			}	
			i = 1;
			for(Iterator it = colPresentation.iterator(); it.hasNext();){
				CamposObjetoNegocioDTO camposObjetoNegocioDTO = (CamposObjetoNegocioDTO)it.next();
				ObjetoNegocioDTO objetoNegocioDTO = new ObjetoNegocioDTO();
				
				objetoNegocioDTO.setIdObjetoNegocio(camposObjetoNegocioDTO.getIdObjetoNegocio());
				objetoNegocioDTO = (ObjetoNegocioDTO) objetoNegocioDao.restore(objetoNegocioDTO);
				
				if (objetoNegocioDTO != null){
					if (!sqlFields.equalsIgnoreCase("")){
						sqlFields += ", ";
					}
					sqlFields += objetoNegocioDTO.getNomeTabelaDB() + "." + camposObjetoNegocioDTO.getNomeDB() + " Fld_" + i;
				}
				i++;
			}
		}
		return sqlFields;
	}
	
	private String generateFromWithRelatios(Collection colPresentation, Collection colFilter) throws Exception{
		ObjetoNegocioDao objetoNegocioDao = new ObjetoNegocioDao();
		HashMap map = new HashMap();
		
		Collection colGeral = new ArrayList();
		if (colPresentation != null){
			colGeral.addAll(colPresentation);
		}
		if (colFilter != null){
			colGeral.addAll(colFilter);
		}
		
		if (colGeral != null){
			for(Iterator it = colGeral.iterator(); it.hasNext();){
				CamposObjetoNegocioDTO camposObjetoNegocioDTO = (CamposObjetoNegocioDTO)it.next();
				ObjetoNegocioDTO objetoNegocioDTO = new ObjetoNegocioDTO();
				
				objetoNegocioDTO.setIdObjetoNegocio(camposObjetoNegocioDTO.getIdObjetoNegocio());
				objetoNegocioDTO = (ObjetoNegocioDTO) objetoNegocioDao.restore(objetoNegocioDTO);
				
				if (objetoNegocioDTO != null){
					if (!map.containsKey(objetoNegocioDTO.getNomeTabelaDB())){
						map.put(objetoNegocioDTO.getNomeTabelaDB(), objetoNegocioDTO.getNomeTabelaDB());
					}
				}
			}
		}
		
		Set set = map.entrySet(); 
		Iterator i = set.iterator(); 
		
		String fromSql = "";
		while(i.hasNext()) { 
			Map.Entry me = (Map.Entry)i.next(); 
			if (!fromSql.equalsIgnoreCase("")){
				fromSql += ",";
			}
			fromSql += me.getKey();
		}
		
		return fromSql;
	}
	
	private String generateFilter(Collection colFilter, String termoPesquisa) throws Exception{
		ObjetoNegocioDao objetoNegocioDao = new ObjetoNegocioDao();
		String sqlFilter = "";
		if (colFilter != null){
			for(Iterator it = colFilter.iterator(); it.hasNext();){
				CamposObjetoNegocioDTO camposObjetoNegocioDTO = (CamposObjetoNegocioDTO)it.next();
				ObjetoNegocioDTO objetoNegocioDTO = new ObjetoNegocioDTO();
				
				objetoNegocioDTO.setIdObjetoNegocio(camposObjetoNegocioDTO.getIdObjetoNegocio());
				objetoNegocioDTO = (ObjetoNegocioDTO) objetoNegocioDao.restore(objetoNegocioDTO);
				
				if (objetoNegocioDTO != null){
					if (!sqlFilter.equalsIgnoreCase("")){
						sqlFilter += " AND ";
					}
					String pref = "";
					String suf = "";
					String comp = "=";
					if (MetaUtil.isStringType(camposObjetoNegocioDTO.getTipoDB())){
						pref = "'%";
						suf = "%'";		
						comp = "LIKE";
					}
					if (camposObjetoNegocioDTO.getFiltro() != null && (camposObjetoNegocioDTO.getFiltro().equalsIgnoreCase("${TERMO_PESQUISA}") ||
							camposObjetoNegocioDTO.getFiltro().equalsIgnoreCase("${termo_pesquisa}"))){
						if (termoPesquisa != null && !termoPesquisa.trim().equalsIgnoreCase("")){
							sqlFilter += objetoNegocioDTO.getNomeTabelaDB() + "." + camposObjetoNegocioDTO.getNomeDB() + " " + comp + " " +
							pref + termoPesquisa.trim() + suf;							
						}
					}else{
					    	if (camposObjetoNegocioDTO.getFiltro() != null){
        					    	if (!camposObjetoNegocioDTO.getFiltro().trim().equalsIgnoreCase("")){
                						sqlFilter += camposObjetoNegocioDTO.getFiltro();
        					    	}
        						if (termoPesquisa != null && !termoPesquisa.trim().equalsIgnoreCase("")){
        							String strAnd = "";
        							if (!sqlFilter.trim().equalsIgnoreCase("")){
        								strAnd = " AND ";
        							}						    
        							sqlFilter = sqlFilter.replaceAll("\\$\\{TERMO_PESQUISA\\}", strAnd + objetoNegocioDTO.getNomeTabelaDB() + "." + camposObjetoNegocioDTO.getNomeDB() + " " + comp + " " +
        								pref + termoPesquisa.trim() + suf);	
        							sqlFilter = sqlFilter.replaceAll("\\$\\{termo_pesquisa\\}", strAnd + objetoNegocioDTO.getNomeTabelaDB() + "." + camposObjetoNegocioDTO.getNomeDB() + " " + comp + " " +
            								pref + termoPesquisa.trim() + suf);	        							
        						}
					    	}
					}
				}
			}
		}
		sqlFilter = sqlFilter.replaceAll("\\$\\{TERMO_PESQUISA\\}", "");	
		sqlFilter = sqlFilter.replaceAll("\\$\\{termo_pesquisa\\}", "");	        							
		return sqlFilter;
	}	

	private String generateOrder(Collection colOrder) throws Exception{
		ObjetoNegocioDao objetoNegocioDao = new ObjetoNegocioDao();
		String sqlOrder = "";
		if (colOrder != null){
			for(Iterator it = colOrder.iterator(); it.hasNext();){
				CamposObjetoNegocioDTO camposObjetoNegocioDTO = (CamposObjetoNegocioDTO)it.next();
				ObjetoNegocioDTO objetoNegocioDTO = new ObjetoNegocioDTO();
				
				objetoNegocioDTO.setIdObjetoNegocio(camposObjetoNegocioDTO.getIdObjetoNegocio());
				objetoNegocioDTO = (ObjetoNegocioDTO) objetoNegocioDao.restore(objetoNegocioDTO);
				
				if (objetoNegocioDTO != null){
					if (!sqlOrder.equalsIgnoreCase("")){
						sqlOrder += ", ";
					}
					sqlOrder += objetoNegocioDTO.getNomeTabelaDB() + "." + camposObjetoNegocioDTO.getNomeDB();
				}
			}
		}
		return sqlOrder;
	}	
}
