package br.com.centralit.citcorpore.metainfo.complementos;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import br.com.centralit.citcorpore.bean.TempoAcordoNivelServicoDTO;
import br.com.centralit.citcorpore.integracao.ResultadosEsperadosDAO;
import br.com.centralit.citcorpore.integracao.TempoAcordoNivelServicoDao;
import br.com.centralit.citcorpore.integracao.ValorAjusteGlosaDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.util.WebUtil;

@SuppressWarnings({"unchecked", "rawtypes"})
public class ComplementoSLA_Restore {

	/**
	 * @param mapFields
	 * @param transactionControler
	 * @return String
	 * @throws ServiceException
	 * @throws Exception
	 * @author rodrigo.acorse
	 */
	public String execute(HashMap mapFields, TransactionControler transactionControler) throws ServiceException, Exception {
		return execute(mapFields, transactionControler, null);
	}

	/**
	 * @param mapFields
	 * @param transactionControler
	 * @param language
	 * @return String
	 * @throws ServiceException
	 * @throws Exception
	 */
	public String execute(HashMap mapFields, TransactionControler transactionControler, String language) throws ServiceException, Exception{
		Object idAcordoNivelServicoStr = mapFields.get("IDACORDONIVELSERVICO");

		Integer idAcordoNivelServico = 0;
		try{
			idAcordoNivelServico = Integer.parseInt(idAcordoNivelServicoStr.toString());
		}catch (Exception e) {
		}

		TempoAcordoNivelServicoDao tempoAcordoNivelServicoDao = new TempoAcordoNivelServicoDao();

		for(int i = 1; i <= 5; i++){
			Collection colAux1 = tempoAcordoNivelServicoDao.findByIdAcordoAndFaseAndIdPrioridade(idAcordoNivelServico, 1, i);
			if (colAux1 != null && colAux1.size() > 0){
				TempoAcordoNivelServicoDTO tempoAcordoNivelServicoDTO = (TempoAcordoNivelServicoDTO) ((List)colAux1).get(0);
				mapFields.put("HH-1-" + i, "" + tempoAcordoNivelServicoDTO.getTempoHH());
				mapFields.put("MM-1-" + i, "" + tempoAcordoNivelServicoDTO.getTempoMM());
			}
			colAux1 = tempoAcordoNivelServicoDao.findByIdAcordoAndFaseAndIdPrioridade(idAcordoNivelServico, 2, i);
			if (colAux1 != null && colAux1.size() > 0){
				TempoAcordoNivelServicoDTO tempoAcordoNivelServicoDTO = (TempoAcordoNivelServicoDTO) ((List)colAux1).get(0);
				mapFields.put("HH-2-" + i, "" + tempoAcordoNivelServicoDTO.getTempoHH());
				mapFields.put("MM-2-" + i, "" + tempoAcordoNivelServicoDTO.getTempoMM());
			}
		}

		Object idServicoContratoStr = mapFields.get("IDSERVICOCONTRATO");

		Integer idServicoContrato = 0;
		try{
			idServicoContrato = Integer.parseInt(idServicoContratoStr.toString());
		}catch (Exception e) {
		}

		ValorAjusteGlosaDAO valorAjusteGlosaDAO = new ValorAjusteGlosaDAO();
		ResultadosEsperadosDAO resultadosEsperadosDAO = new ResultadosEsperadosDAO();

		if(idServicoContrato != null && idAcordoNivelServico != null){
			String tipoAcordo = (String) mapFields.get("TIPO");
			if(tipoAcordo != null && tipoAcordo.equalsIgnoreCase("F")){
				Collection resp = valorAjusteGlosaDAO.consultaQuantidadesPorAcordoEServicoContrato(idServicoContrato, idAcordoNivelServico);
				if(resp!=null && !resp.isEmpty()){
					if (language != null && !language.equals("")) {
						mapFields.put("VALORAJUSTESERIALIZADO", WebUtil.serializeObjects(resp, language));
					} else {
						mapFields.put("VALORAJUSTESERIALIZADO", WebUtil.serializeObjects(resp));
					}
				}
			}

			if(tipoAcordo != null && tipoAcordo.equalsIgnoreCase("R")){
				Collection resp = resultadosEsperadosDAO.consultaResultadosPorAcordoEServicoContrato(idServicoContrato, idAcordoNivelServico);
				if(resp!=null && !resp.isEmpty()){
					if (language != null && !language.equals("")) {
						mapFields.put("RESULTADOSESPERADOSSERIALIZADO", WebUtil.serializeObjects(resp, language));
					} else {
						mapFields.put("RESULTADOSESPERADOSSERIALIZADO", WebUtil.serializeObjects(resp));
					}
				}
			}

		}

		return "OK";
	}
}
