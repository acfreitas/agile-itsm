package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.util.CitAjaxWebUtil;
import br.com.centralit.citcorpore.bean.BICategoriasDTO;
import br.com.centralit.citcorpore.bean.BIConsultaDTO;
import br.com.centralit.citcorpore.bean.BIDashBoardDTO;
import br.com.centralit.citcorpore.bean.BIItemDashBoardDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.BICategoriasService;
import br.com.centralit.citcorpore.negocio.BIConsultaService;
import br.com.centralit.citcorpore.negocio.BIDashBoardService;
import br.com.centralit.citcorpore.negocio.BIItemDashBoardService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

public class DashBoardBuilderInternal extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UsuarioDTO usuarioDto = WebUtil.getUsuario(request);
		if (usuarioDto == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}		
		BIDashBoardDTO biDashBoardDTO = (BIDashBoardDTO)document.getBean();
		document.executeScript("HTMLUtils.setForm(document.form)");
		BICategoriasService biCategoriasService = (BICategoriasService) ServiceLocator.getInstance().getService(BICategoriasService.class, null);
		Collection lst = biCategoriasService.findSemPai();
		int nivel = 1;
		if (lst != null){
			for (Iterator it = lst.iterator(); it.hasNext();){
				BICategoriasDTO biCategoriaDto = (BICategoriasDTO) it.next();
				document.getSelectById("idConsulta").addOption("", geraNivel(nivel - 1) + "<" + biCategoriaDto.getNomeCategoria() + ">");
				preencheConsultasByIdCategoria(biCategoriaDto.getIdCategoria(), document, nivel);
				preencheCategoriasFilhasByIdCategoria(biCategoriaDto.getIdCategoria(), document, biCategoriasService, nivel);
			}
		}
		BIDashBoardService biDashBoardService = (BIDashBoardService) ServiceLocator.getInstance().getService(BIDashBoardService.class, null);
		BIConsultaService biConsultaService = (BIConsultaService) ServiceLocator.getInstance().getService(BIConsultaService.class, null);
		BIItemDashBoardService biItemDashBoardService = (BIItemDashBoardService) ServiceLocator.getInstance().getService(BIItemDashBoardService.class, null);
		int iAux = 0;
		if (biDashBoardDTO.getIdDashBoard() != null && biDashBoardDTO.getIdDashBoard()>0){
			document.executeScript("hash = new HashMap()");
			document.executeScript("hashTitulo = new HashMap()");
			document.executeScript("hashSubst = new HashMap()");
			biDashBoardDTO = (BIDashBoardDTO) biDashBoardService.restore(biDashBoardDTO);
			if (biDashBoardDTO != null){
				document.getForm("formSalvar").setValues(biDashBoardDTO);
			}
			Collection col = biItemDashBoardService.findByIdDashBoard(biDashBoardDTO.getIdDashBoard());
			if (col != null){
				for (Iterator it = col.iterator(); it.hasNext();){
					BIItemDashBoardDTO biItemDashBoardDTO = (BIItemDashBoardDTO)it.next();
					document.executeScript("document.getElementById('testdiv_" + (iAux + 1) + "').style.top = '" + biItemDashBoardDTO.getItemTop() + "px'");
					document.executeScript("document.getElementById('testdiv_" + (iAux + 1) + "').style.left = '" + biItemDashBoardDTO.getItemLeft() + "px'");
					document.executeScript("document.getElementById('testdiv_" + (iAux + 1) + "').style.width = '" + biItemDashBoardDTO.getItemWidth() + "px'");
					document.executeScript("document.getElementById('testdiv_" + (iAux + 1) + "').style.height = '" + biItemDashBoardDTO.getItemHeight() + "px'");
					
					if (biItemDashBoardDTO.getIdConsulta() != null){
						BIConsultaDTO biConsultaDTO = new BIConsultaDTO();
						biConsultaDTO.setIdConsulta(biItemDashBoardDTO.getIdConsulta());
						biConsultaDTO = (BIConsultaDTO) biConsultaService.restore(biConsultaDTO);
						if (biConsultaDTO != null){
							String str = biConsultaDTO.getNomeConsulta().replaceAll("\"", "");
							str = biConsultaDTO.getNomeConsulta().replaceAll("\'", "");
							document.executeScript("document.getElementById('testdiv_" + (iAux + 1) + "_internal').innerHTML = \"" + str + "\"");
							
							document.executeScript("hash['testdiv_" + (iAux + 1) + "'] = " + biConsultaDTO.getIdConsulta());
							if (biConsultaDTO.getNomeConsulta() != null){
								document.executeScript("hashTitulo['testdiv_" + (iAux + 1) + "'] = '" + biConsultaDTO.getNomeConsulta().replaceAll("\"", "").replaceAll("\'", "") + "'");
							}
						}
						if (biItemDashBoardDTO.getParmsSubst() != null){
							document.executeScript("hashSubst['testdiv_" + (iAux + 1) + "'] = ObjectUtils.decodificaEnter('" + CitAjaxWebUtil.codificaEnter(biItemDashBoardDTO.getParmsSubst().replaceAll("\"", "").replaceAll("\'", "")) + "')");
						}
					}
					iAux++;
				}
			}
		}		
	}
	
	public void preencheConsultasByIdCategoria(Integer idCategoria, DocumentHTML document, int nivel) throws ServiceException, Exception{
		BIConsultaService biConsultaService = (BIConsultaService) ServiceLocator.getInstance().getService(BIConsultaService.class, null);
		Collection colConsultas = biConsultaService.findByIdCategoria(idCategoria);
		if (colConsultas != null){
			for (Iterator it = colConsultas.iterator(); it.hasNext();){
				BIConsultaDTO biConsultaDTO = (BIConsultaDTO)it.next();
				document.getSelectById("idConsulta").addOption("" + biConsultaDTO.getIdConsulta(), geraNivel(nivel) + biConsultaDTO.getNomeConsulta());
			}
		}
	}
	
	public void preencheCategoriasFilhasByIdCategoria(Integer idCategoria, DocumentHTML document, BICategoriasService biCategoriasService, int nivel) throws ServiceException, Exception{
		Collection col = biCategoriasService.findByIdCategoriaPai(idCategoria);
		if (col != null){
			for (Iterator it = col.iterator(); it.hasNext();){
				BICategoriasDTO categDto = (BICategoriasDTO)it.next();
				document.getSelectById("idConsulta").addOption("", geraNivel(nivel)  + "<" + categDto.getNomeCategoria() + ">");
				preencheConsultasByIdCategoria(categDto.getIdCategoria(), document, nivel + 1);
				preencheCategoriasFilhasByIdCategoria(categDto.getIdCategoria(), document, biCategoriasService, nivel + 1);
			}
		}
	}
	
	private String geraNivel(int nivel){
		String nivelStr = "";
		for (int i = 0; i < nivel; i++){
			nivelStr = nivelStr + ".....";
		}
		return nivelStr;
	}
	
	@Override
	public Class getBeanClass() {
		return BIDashBoardDTO.class;
	}	

}
