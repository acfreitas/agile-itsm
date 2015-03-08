package br.com.centralit.citgerencial.generateservices.incidentes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class ControleGenerateSLA extends AjaxFormAction {

	public List execute(HashMap parametersValues, Collection paramtersDefinition) throws ServiceException, Exception {
		String datainicial = (String) parametersValues.get("PARAM.dataInicial");
		String datafinal = (String) parametersValues.get("PARAM.dataFinal");
		List lstRetorno = new ArrayList();

		SolicitacaoServicoDTO solicitacaoServicoDTO = new SolicitacaoServicoDTO();

		if (Integer.parseInt((String) parametersValues.get("PARAM.idContrato")) != -1) {
			solicitacaoServicoDTO.setIdContrato(Integer.parseInt((String) parametersValues.get("PARAM.idContrato")));
		}
		if (parametersValues.get("PARAM.situacao") != null && !parametersValues.get("PARAM.situacao").equals("") && !parametersValues.get("PARAM.situacao").equals("*")) {
			solicitacaoServicoDTO.setSituacao(parametersValues.get("PARAM.situacao").toString());
		}
		if (Integer.parseInt((String) parametersValues.get("PARAM.idPrioridade")) != -1) {
			solicitacaoServicoDTO.setIdPrioridade(Integer.parseInt((String) parametersValues.get("PARAM.idPrioridade")));
		}
		if (Integer.parseInt((String) parametersValues.get("PARAM.idOrigem")) != -1) {
			solicitacaoServicoDTO.setIdOrigem(Integer.parseInt((String) parametersValues.get("PARAM.idOrigem")));
		}
		if (Integer.parseInt((String) parametersValues.get("PARAM.idUnidade")) != -1) {
			solicitacaoServicoDTO.setIdUnidade(Integer.parseInt((String) parametersValues.get("PARAM.idUnidade")));
		}
		if (Integer.parseInt((String) parametersValues.get("PARAM.idServico")) != -1) {
			solicitacaoServicoDTO.setIdServico(Integer.parseInt((String) parametersValues.get("PARAM.idServico")));
		}
		if (Integer.parseInt((String) parametersValues.get("PARAM.idTipoServico")) != -1) {
			solicitacaoServicoDTO.setIdTipoServico(Integer.parseInt((String) parametersValues.get("PARAM.idTipoServico")));
		}
		if (parametersValues.get("PARAM.classificacao") != null && !parametersValues.get("PARAM.classificacao").equals("") && !parametersValues.get("PARAM.classificacao").equals("*")) {
			solicitacaoServicoDTO.setClassificacao(parametersValues.get("PARAM.classificacao").toString());
		}
		solicitacaoServicoDTO.setDataInicio(UtilDatas.convertStringToSQLDate(TipoDate.DATE_DEFAULT, datainicial, super.getLanguage(paramtersDefinition)));
		solicitacaoServicoDTO.setDataFim(UtilDatas.convertStringToSQLDate(TipoDate.DATE_DEFAULT, datafinal, super.getLanguage(paramtersDefinition)));

		double qtdePrazo = 0;
		double qtdeForaPrazo = 0;

		Collection<SolicitacaoServicoDTO> listSla = new ArrayList();

		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);

		listSla = solicitacaoServicoService.relatorioControleSla(solicitacaoServicoDTO);

		if (listSla != null) {
			for (SolicitacaoServicoDTO sla : listSla) {
				solicitacaoServicoService.verificaSituacaoSLA(sla);
				
				if ((sla.getAtrasoSLAStr() != null && sla.getAtrasoSLAStr().equalsIgnoreCase("S") && !sla.getSituacao().equalsIgnoreCase("Cancelada")) || sla.getAtrasoSLA() > 0) {
					qtdeForaPrazo++;
				} else {
					qtdePrazo++;
				}
			}
		}
		try {
			if (listSla != null && listSla.size() != 0) {
				lstRetorno = new ArrayList();
				lstRetorno.add(new Object[] { "Dentro do SLA", (qtdePrazo / (qtdePrazo + qtdeForaPrazo)) * 100, qtdePrazo });
				lstRetorno.add(new Object[] { "Fora do SLA", (qtdeForaPrazo / (qtdePrazo + qtdeForaPrazo)) * 100, qtdeForaPrazo });
			}
			return lstRetorno;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	}

	@Override
	public Class getBeanClass() {
		return null;
	}
}
