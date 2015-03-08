package br.com.centralit.citcorpore.metainfo.complementos;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;

import br.com.centralit.citcorpore.bean.AcordoNivelServicoDTO;
import br.com.centralit.citcorpore.integracao.AcordoNivelServicoDao;
import br.com.centralit.citcorpore.negocio.PrioridadeService;
import br.com.centralit.citcorpore.negocio.UnidadeService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilFormatacao;

public class ComplementoSLA_TempoAuto {
	public String execute(JspWriter out, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);
		Collection colUnidades = unidadeService.list();
		String retorno = "";
		
		String idServicoContratoStr = (String)request.getParameter("saveInfo");
		Integer idServicoContrato = null;
		if (idServicoContratoStr != null && !idServicoContratoStr.trim().equalsIgnoreCase("")){
		    idServicoContrato = new Integer(idServicoContratoStr);
		}
		String idAcordoNivelServicoSTR = (String)request.getParameter("id");
		Integer idAcordoNivelServico = null;
		if (idAcordoNivelServicoSTR != null && !idAcordoNivelServicoSTR.trim().equalsIgnoreCase("")){
		    idAcordoNivelServico = new Integer(idAcordoNivelServicoSTR);
		}	
		AcordoNivelServicoDao acordoNivelServicoDao = new AcordoNivelServicoDao();
		AcordoNivelServicoDTO acordoNivelServicoDTO = new AcordoNivelServicoDTO();
		acordoNivelServicoDTO.setIdAcordoNivelServico(idAcordoNivelServico);
		if (idAcordoNivelServico != null && idAcordoNivelServico.intValue() > 0){
			acordoNivelServicoDTO = (AcordoNivelServicoDTO) acordoNivelServicoDao.restore(acordoNivelServicoDTO);
		}
		PrioridadeService prioridadeService = (PrioridadeService) ServiceLocator.getInstance().getService(PrioridadeService.class, null);
		Collection col = prioridadeService.list();
		if (col != null){
			String tempo = "";
			if (acordoNivelServicoDTO != null && acordoNivelServicoDTO.getTempoAuto() != null){
				tempo = UtilFormatacao.formatDouble(acordoNivelServicoDTO.getTempoAuto(), 2);
			}
			retorno = retorno + "<input type='text' name='TEMPOAUTO' id='TEMPOAUTO' size='12' maxlength='8' class='Format[Money]' value='" + tempo + "'/>min";
		}
		
		return retorno;
	}
}
