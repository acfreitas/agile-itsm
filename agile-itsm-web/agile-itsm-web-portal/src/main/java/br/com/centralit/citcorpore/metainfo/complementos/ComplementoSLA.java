package br.com.centralit.citcorpore.metainfo.complementos;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;

import br.com.centralit.citcorpore.bean.PrioridadeAcordoNivelServicoDTO;
import br.com.centralit.citcorpore.bean.PrioridadeServicoUnidadeDTO;
import br.com.centralit.citcorpore.bean.UnidadeDTO;
import br.com.centralit.citcorpore.negocio.PrioridadeAcordoNivelServicoService;
import br.com.centralit.citcorpore.negocio.PrioridadeServicoUnidadeService;
import br.com.centralit.citcorpore.negocio.UnidadeService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;

public class ComplementoSLA {
	public String execute(JspWriter out, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);
		Collection colUnidades = unidadeService.list();
		String retorno = "";

		String idServicoContratoStr = (String) request.getParameter("saveInfo");
		Integer idServicoContrato = null;
		if (idServicoContratoStr != null && !idServicoContratoStr.trim().equalsIgnoreCase("")) {
			idServicoContrato = new Integer(idServicoContratoStr);
		}
		String idAcordoNivelServicoSTR = (String) request.getParameter("id");
		Integer idAcordoNivelServico = null;
		if (idAcordoNivelServicoSTR != null && !idAcordoNivelServicoSTR.trim().equalsIgnoreCase("")) {
			idAcordoNivelServico = new Integer(idAcordoNivelServicoSTR);
		}
		PrioridadeServicoUnidadeService prioridadeServicoUnidadeService = (PrioridadeServicoUnidadeService) ServiceLocator.getInstance().getService(PrioridadeServicoUnidadeService.class, null);
		PrioridadeAcordoNivelServicoService prioridadeAcordoNivelServicoService = (PrioridadeAcordoNivelServicoService) ServiceLocator.getInstance().getService(
				PrioridadeAcordoNivelServicoService.class, null);

		retorno = retorno + "<table>";
		retorno = retorno + "<tr>";
		retorno = retorno + "<td colspan='4'>";
		retorno = retorno + "<b>" + UtilI18N.internacionaliza(request, "sla.prioridadeunidades") + "</b> (" + UtilI18N.internacionaliza(request, "sla.prioridadepadrao") + "):";
		retorno = retorno + "</td>";
		retorno = retorno + "</tr>";
		if (colUnidades != null) {
			for (Iterator it = colUnidades.iterator(); it.hasNext();) {
				UnidadeDTO unidadeDTO = (UnidadeDTO) it.next();
				int prioridade = 0;
				if (idServicoContrato != null) {
					PrioridadeServicoUnidadeDTO prioridadeServicoUnidadeDTO = prioridadeServicoUnidadeService.restore(idServicoContrato, unidadeDTO.getIdUnidade());
					if (prioridadeServicoUnidadeDTO != null) {
						if (prioridadeServicoUnidadeDTO.getDataFim() == null) {
							prioridade = prioridadeServicoUnidadeDTO.getIdPrioridade();
						}
					}
				} else {
					PrioridadeAcordoNivelServicoDTO prioridadeAcordoNivelServicoDTO = new PrioridadeAcordoNivelServicoDTO();
					prioridadeAcordoNivelServicoDTO.setIdAcordoNivelServico(idAcordoNivelServico);
					prioridadeAcordoNivelServicoDTO.setIdUnidade(unidadeDTO.getIdUnidade());
					if (idAcordoNivelServico != null) {
						prioridadeAcordoNivelServicoDTO = (PrioridadeAcordoNivelServicoDTO) prioridadeAcordoNivelServicoService.restore(prioridadeAcordoNivelServicoDTO);
						if (prioridadeAcordoNivelServicoDTO != null) {
							if (prioridadeAcordoNivelServicoDTO.getDataFim() == null) {
								prioridade = prioridadeAcordoNivelServicoDTO.getIdPrioridade();
							}
						}
					}
				}

				retorno = retorno + "<tr>";
				retorno = retorno + "<td>";
				retorno = retorno + unidadeDTO.getNome();
				retorno = retorno + "</td>";
				retorno = retorno + "<td>";
				retorno = retorno + "<select name='IDUNIDADE_" + unidadeDTO.getIdUnidade() + "' class='noClearCITAjax'/>";
				retorno = retorno + "<option value=''>--</option>";
				retorno = retorno + "<option value='1'" + (prioridade == 1 ? " selected" : "") + ">1</option>";
				retorno = retorno + "<option value='2'" + (prioridade == 2 ? " selected" : "") + ">2</option>";
				retorno = retorno + "<option value='3'" + (prioridade == 3 ? " selected" : "") + ">3</option>";
				retorno = retorno + "<option value='4'" + (prioridade == 4 ? " selected" : "") + ">4</option>";
				retorno = retorno + "<option value='5'" + (prioridade == 5 ? " selected" : "") + ">5</option>";
				retorno = retorno + "</select>";
				retorno = retorno + "</td>";
				retorno = retorno + "</tr>";
			}
		}
		retorno = retorno + "</table>";

		return retorno;
	}
}
