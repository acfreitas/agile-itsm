package br.com.centralit.citcorpore.metainfo.complementos;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;

import br.com.centralit.citcorpore.bean.AcordoNivelServicoDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.integracao.AcordoNivelServicoDao;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.UnidadeService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;

public class ComplementoSLA_Grupos {
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
		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		Collection col = grupoService.listaGruposAtivos();
		if (col != null){
			retorno = retorno + "<option value=''>--</option>";
			for (Iterator it = col.iterator(); it.hasNext();){
				GrupoDTO grupoDTO = (GrupoDTO)it.next();
				int idGrupo1 = 0;
				if (acordoNivelServicoDTO != null && acordoNivelServicoDTO.getIdGrupo1() != null){
					idGrupo1 = acordoNivelServicoDTO.getIdGrupo1();
				}
				String x = "";
				if (idGrupo1 == grupoDTO.getIdGrupo().intValue()){
					x = " selected ";
				}
				retorno = retorno + "<option value='" + grupoDTO.getIdGrupo() + "' " + x + ">" + grupoDTO.getNome() + "</option>";
			}
		}
		
		return retorno;
	}
}
