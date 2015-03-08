package br.com.centralit.citcorpore.metainfo.complementos;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import br.com.centralit.citcorpore.bean.AcordoNivelServicoDTO;
import br.com.centralit.citcorpore.bean.PrioridadeAcordoNivelServicoDTO;
import br.com.centralit.citcorpore.bean.PrioridadeServicoUnidadeDTO;
import br.com.centralit.citcorpore.bean.ResultadosEsperadosDTO;
import br.com.centralit.citcorpore.bean.TempoAcordoNivelServicoDTO;
import br.com.centralit.citcorpore.bean.UnidadeDTO;
import br.com.centralit.citcorpore.bean.ValorAjusteGlosaDTO;
import br.com.centralit.citcorpore.integracao.AcordoNivelServicoDao;
import br.com.centralit.citcorpore.integracao.PrioridadeAcordoNivelServicoDao;
import br.com.centralit.citcorpore.integracao.PrioridadeServicoUnidadeDao;
import br.com.centralit.citcorpore.integracao.ResultadosEsperadosDAO;
import br.com.centralit.citcorpore.integracao.TempoAcordoNivelServicoDao;
import br.com.centralit.citcorpore.integracao.UnidadeDao;
import br.com.centralit.citcorpore.integracao.ValorAjusteGlosaDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.WebUtil;

public class ComplementoSLA_Insert {
	public String execute(HashMap mapFields, TransactionControler transactionControler) throws ServiceException, Exception{
		String idAcordoNivelServicoStr = (String)mapFields.get("IDACORDONIVELSERVICO");
		
		Integer idAcordoNivelServico = 0;
		try{
			idAcordoNivelServico = Integer.parseInt(idAcordoNivelServicoStr);
		}catch (Exception e) {
		}
		
		Integer[] hhCaptura = new Integer[5];
		Integer[] hhResolucao = new Integer[5];
		Integer[] mmCaptura = new Integer[5];
		Integer[] mmResolucao = new Integer[5];
		for(int i = 1; i <= 5; i++){
			hhCaptura[i - 1] = 0;
			hhResolucao[i - 1] = 0;
			
			mmCaptura[i - 1] = 0;
			mmResolucao[i - 1] = 0;
			
			try{
				hhCaptura[i - 1] = new Integer((String)mapFields.get("HH-1-" + i));
			}catch (Exception e) {
			}
			try{
				hhResolucao[i - 1] = new Integer((String)mapFields.get("HH-2-" + i));
			}catch (Exception e) {
			}
			try{
				mmCaptura[i - 1] = new Integer((String)mapFields.get("MM-1-" + i));
			}catch (Exception e) {
			}
			try{
				mmResolucao[i - 1] = new Integer((String)mapFields.get("MM-2-" + i));
			}catch (Exception e) {
			}
		}
		TempoAcordoNivelServicoDao tempoAcordoNivelServicoDao = new TempoAcordoNivelServicoDao();
		tempoAcordoNivelServicoDao.setTransactionControler(transactionControler);
		
		for(int i = 1; i <= 5; i++){
			TempoAcordoNivelServicoDTO tempoAcordoNivelServicoDTO = new TempoAcordoNivelServicoDTO();
			tempoAcordoNivelServicoDTO.setIdAcordoNivelServico(idAcordoNivelServico);
			tempoAcordoNivelServicoDTO.setIdFase(1);
			tempoAcordoNivelServicoDTO.setIdPrioridade(i);
			tempoAcordoNivelServicoDTO.setTempoHH(hhCaptura[i - 1]);
			tempoAcordoNivelServicoDTO.setTempoMM(mmCaptura[i - 1]);
			tempoAcordoNivelServicoDao.create(tempoAcordoNivelServicoDTO);
		}
		for(int i = 1; i <= 5; i++){
			TempoAcordoNivelServicoDTO tempoAcordoNivelServicoDTO = new TempoAcordoNivelServicoDTO();
			tempoAcordoNivelServicoDTO.setIdAcordoNivelServico(idAcordoNivelServico);
			tempoAcordoNivelServicoDTO.setIdFase(2);
			tempoAcordoNivelServicoDTO.setIdPrioridade(i);
			tempoAcordoNivelServicoDTO.setTempoHH(hhResolucao[i - 1]);
			tempoAcordoNivelServicoDTO.setTempoMM(mmResolucao[i - 1]);
			tempoAcordoNivelServicoDao.create(tempoAcordoNivelServicoDTO);
		}
		
		String idServicoContratoStr = (String)mapFields.get("IDSERVICOCONTRATO");
		Integer idServicoContrato = null;
		if (idServicoContratoStr != null && !idServicoContratoStr.trim().equalsIgnoreCase("")){
		    idServicoContrato = new Integer(idServicoContratoStr);
		}
		
		if (idServicoContrato != null){
    		UnidadeDao unidadeDao = new UnidadeDao();
    		PrioridadeServicoUnidadeDao prioridadeServicoUnidadeDao = new PrioridadeServicoUnidadeDao();
    		prioridadeServicoUnidadeDao.setTransactionControler(transactionControler);
    		Collection colUnidades = unidadeDao.list();
    		if (colUnidades != null){
    		    for(Iterator it = colUnidades.iterator(); it.hasNext();){
        			UnidadeDTO unidadeDTO = (UnidadeDTO)it.next();
        			String prioridade = (String)mapFields.get("IDUNIDADE_" + unidadeDTO.getIdUnidade());
        			if (prioridade != null && !prioridade.trim().equalsIgnoreCase("")){
        			    PrioridadeServicoUnidadeDTO prioridadeServicoUnidadeDTO = new PrioridadeServicoUnidadeDTO();
        			    prioridadeServicoUnidadeDTO.setIdPrioridade(new Integer(prioridade));
        			    prioridadeServicoUnidadeDTO.setIdUnidade(unidadeDTO.getIdUnidade());
        			    prioridadeServicoUnidadeDTO.setDataInicio(UtilDatas.getDataAtual());
        			    prioridadeServicoUnidadeDTO.setIdServicoContrato(idServicoContrato);
        			    //prioridadeServicoUnidadeDTO = (PrioridadeServicoUnidadeDTO) prioridadeServicoUnidadeDao.create(prioridadeServicoUnidadeDTO);
        			    PrioridadeServicoUnidadeDTO prioridadeServicoUnidadeAux = prioridadeServicoUnidadeDao.restore(idServicoContrato, unidadeDTO.getIdUnidade());
        			    if (prioridadeServicoUnidadeAux != null){
        			    	prioridadeServicoUnidadeDao.update(prioridadeServicoUnidadeDTO);
        			    }else{
        			    	prioridadeServicoUnidadeDTO = (PrioridadeServicoUnidadeDTO) prioridadeServicoUnidadeDao.create(prioridadeServicoUnidadeDTO);
        			    }
        			}
    		    }
    		}
		}else{
    		UnidadeDao unidadeDao = new UnidadeDao();
    		PrioridadeAcordoNivelServicoDao prioridadeAcordoNivelServicoDao = new PrioridadeAcordoNivelServicoDao();
    		prioridadeAcordoNivelServicoDao.setTransactionControler(transactionControler);
    		Collection colUnidades = unidadeDao.list();
    		if (colUnidades != null){
    		    for(Iterator it = colUnidades.iterator(); it.hasNext();){
	    			UnidadeDTO unidadeDTO = (UnidadeDTO)it.next();
	    			String prioridade = (String)mapFields.get("IDUNIDADE_" + unidadeDTO.getIdUnidade());
	    			if (prioridade != null && !prioridade.trim().equalsIgnoreCase("")){
	    			    PrioridadeAcordoNivelServicoDTO prioridadeAcordoNivelServicoDTO = new PrioridadeAcordoNivelServicoDTO();
	    			    prioridadeAcordoNivelServicoDTO.setIdPrioridade(new Integer(prioridade));
	    			    prioridadeAcordoNivelServicoDTO.setIdUnidade(unidadeDTO.getIdUnidade());
	    			    prioridadeAcordoNivelServicoDTO.setDataInicio(UtilDatas.getDataAtual());
	    			    prioridadeAcordoNivelServicoDTO.setIdAcordoNivelServico(idAcordoNivelServico);
	    			    prioridadeAcordoNivelServicoDTO = (PrioridadeAcordoNivelServicoDTO) prioridadeAcordoNivelServicoDao.create(prioridadeAcordoNivelServicoDTO);
	    			}
    		    }
    		}
		}
		
		String tipoAcordo = (String) mapFields.get("TIPO");
		
		if(tipoAcordo != null && tipoAcordo.equalsIgnoreCase("F")){
			String valorSerializado = (String) mapFields.get("VALORAJUSTESERIALIZADO");
			if(valorSerializado!=null){
				List<ValorAjusteGlosaDTO> listResp = (List) WebUtil.deserializeCollectionFromString(ValorAjusteGlosaDTO.class, valorSerializado);
				if(listResp != null){
					ValorAjusteGlosaDAO valorAjusteDAO = new ValorAjusteGlosaDAO();
					valorAjusteDAO.setTransactionControler(transactionControler);
					for (ValorAjusteGlosaDTO valorObject : listResp) {
						valorObject.setIdAcordoNivelServico(idAcordoNivelServico);
						valorObject.setIdServicoContrato(idServicoContrato);
						valorAjusteDAO.create(valorObject);
					}
				}
			}
		}
		
		if(tipoAcordo != null && tipoAcordo.equalsIgnoreCase("R")){
			String resultadoSerializado = (String) mapFields.get("RESULTADOSESPERADOSSERIALIZADO");
			if(resultadoSerializado!=null){
				List<ResultadosEsperadosDTO> listResp = (List) WebUtil.deserializeCollectionFromString(ResultadosEsperadosDTO.class, resultadoSerializado);
				if(listResp != null){
					ResultadosEsperadosDAO resultadosEsperadosDAO = new ResultadosEsperadosDAO();
					resultadosEsperadosDAO.setTransactionControler(transactionControler);
					for (ResultadosEsperadosDTO resultObject : listResp) {
						resultObject.setIdAcordoNivelServico(idAcordoNivelServico);
						resultObject.setIdServicoContrato(idServicoContrato);
						resultadosEsperadosDAO.create(resultObject);
					}
				}
			}
		}
		
		String TEMPOAUTO_STR = (String)mapFields.get("TEMPOAUTO");
		Double TEMPOAUTO_VAL = null;
		if (TEMPOAUTO_STR != null && !TEMPOAUTO_STR.trim().equalsIgnoreCase("")){
			TEMPOAUTO_STR = TEMPOAUTO_STR.replaceAll("\\.", "");
			TEMPOAUTO_STR = TEMPOAUTO_STR.replaceAll("\\,", ".");
			try{
				TEMPOAUTO_VAL = new Double(TEMPOAUTO_STR);
			}catch(Exception e){}
		}
		
		String IDPRIORIDADEAUTO1_STR = (String)mapFields.get("IDPRIORIDADEAUTO1");
		Integer IDPRIORIDADEAUTO1_INT = null;
		if (IDPRIORIDADEAUTO1_STR != null && !IDPRIORIDADEAUTO1_STR.trim().equalsIgnoreCase("")){
			try{
				IDPRIORIDADEAUTO1_INT = new Integer(IDPRIORIDADEAUTO1_STR);
			}catch(Exception e){}
		}
		
		String IDGRUPO1_STR = (String)mapFields.get("IDGRUPO1");
		Integer IDGRUPO1_INT = null;
		if (IDGRUPO1_STR != null && !IDGRUPO1_STR.trim().equalsIgnoreCase("")){
			try{
				IDGRUPO1_INT = new Integer(IDGRUPO1_STR);
			}catch(Exception e){}
		}		
		
		AcordoNivelServicoDao acordoNivelServicoDao = new AcordoNivelServicoDao();
		acordoNivelServicoDao.setTransactionControler(transactionControler);
		AcordoNivelServicoDTO acordoNivelServicoAux = new AcordoNivelServicoDTO(); 
		acordoNivelServicoAux.setTempoAuto(TEMPOAUTO_VAL);
		acordoNivelServicoAux.setIdPrioridadeAuto1(IDPRIORIDADEAUTO1_INT);
		acordoNivelServicoAux.setIdGrupo1(IDGRUPO1_INT);
		acordoNivelServicoAux.setIdAcordoNivelServico(idAcordoNivelServico);
		acordoNivelServicoDao.updateTemposAcoes(acordoNivelServicoAux);
		
		return "OK";
	}
}
