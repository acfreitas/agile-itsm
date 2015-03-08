package br.com.centralit.citcorpore.rh.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.CargosDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CargosService;
import br.com.centralit.citcorpore.rh.bean.CurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.EntrevistaCandidatoDTO;
import br.com.centralit.citcorpore.rh.bean.HistoricoCandidatoDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoPessoalDTO;
import br.com.centralit.citcorpore.rh.integracao.HistoricoCandidatoDao;
import br.com.centralit.citcorpore.rh.negocio.CurriculoService;
import br.com.centralit.citcorpore.rh.negocio.EntrevistaCandidatoService;
import br.com.centralit.citcorpore.rh.negocio.RequisicaoPessoalService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

public class HistoricoCandidato extends AjaxFormAction {

	public void load(DocumentHTML document, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request,
					"citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '"
					+ Constantes.getValue("SERVER_ADDRESS")
					+ request.getContextPath() + "'");
			return;
		}

		restore(document,request,response);
	}

	 @SuppressWarnings({ "unchecked" })
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	        UsuarioDTO usuario = WebUtil.getUsuario(request);
	        if (usuario == null){
	              document.alert("Sessão expirada! Favor efetuar logon novamente!");
	              return;
	        }

	        HistoricoCandidatoDTO historicoCandidatoDTO = (HistoricoCandidatoDTO) document.getBean();
	        HistoricoCandidatoDao historicoCandidatoDao = new HistoricoCandidatoDao();
	        CurriculoService curriculoService = (CurriculoService) ServiceLocator.getInstance().getService(CurriculoService.class, WebUtil.getUsuarioSistema(request));
	        EntrevistaCandidatoService entrevistaCandidatoService = (EntrevistaCandidatoService) ServiceLocator.getInstance().getService(EntrevistaCandidatoService.class, WebUtil.getUsuarioSistema(request));
	        RequisicaoPessoalService requisicaoPessoalService = (RequisicaoPessoalService) ServiceLocator.getInstance().getService(RequisicaoPessoalService.class, WebUtil.getUsuarioSistema(request));
	        CargosService cargosService = (CargosService) ServiceLocator.getInstance().getService(CargosService.class, WebUtil.getUsuarioSistema(request));

	        CurriculoDTO curriculoDTO = new CurriculoDTO();
	        RequisicaoPessoalDTO requisicaoPessoalDTO ;
	        EntrevistaCandidatoDTO entrevistaCandidatoDTO ;
	        CargosDTO cargosDTO;

	        Collection<HistoricoCandidatoDTO> lista = (Collection<HistoricoCandidatoDTO>) historicoCandidatoDao.listByIdCurriculo(historicoCandidatoDTO.getIdCurriculo());

	        if(lista != null && lista.size() > 0){
	        	for (HistoricoCandidatoDTO historicoCandidatoDTO2 : lista) {
	        		requisicaoPessoalDTO = new RequisicaoPessoalDTO();
	        		entrevistaCandidatoDTO = new EntrevistaCandidatoDTO();
	        		cargosDTO = new CargosDTO();
	        		curriculoDTO.setIdCurriculo(historicoCandidatoDTO2.getIdCurriculo());
	        		curriculoDTO = (CurriculoDTO) curriculoService.restore(curriculoDTO);
	        		entrevistaCandidatoDTO.setIdEntrevista(historicoCandidatoDTO2.getIdEntrevista());
	        		entrevistaCandidatoDTO = (EntrevistaCandidatoDTO) entrevistaCandidatoService.restore(entrevistaCandidatoDTO);

	        		if(historicoCandidatoDTO2.getIdSolicitacaoServico() != null){
	        			requisicaoPessoalDTO.setIdSolicitacaoServico(historicoCandidatoDTO2.getIdSolicitacaoServico());
	        			requisicaoPessoalDTO = (RequisicaoPessoalDTO) requisicaoPessoalService.restore(requisicaoPessoalDTO);
	        		}

	        		if(requisicaoPessoalDTO != null){
	        			if(requisicaoPessoalDTO.getIdCargo() != null){
	        				cargosDTO.setIdCargo(requisicaoPessoalDTO.getIdCargo());
	        				cargosDTO = (CargosDTO) cargosService.restore(cargosDTO);
	        			}
	        		}

	        		historicoCandidatoDTO2.setNome(curriculoDTO.getNome());
	        		historicoCandidatoDTO2.setFuncao(cargosDTO.getNomeCargo());

	        		if(historicoCandidatoDTO2.getResultado().equals("A")){
	        			historicoCandidatoDTO2.setResultado("Aprovado");
	        		}
	        		if(historicoCandidatoDTO2.getResultado().equals("R")){
	        			historicoCandidatoDTO2.setResultado("Reprovado");
	        		}
	        		if(historicoCandidatoDTO2.getResultado().equals("S")){
	        			historicoCandidatoDTO2.setResultado("2ª Oportunidade");
	        		}
	        		if(historicoCandidatoDTO2.getResultado().equals("D")){
	        			historicoCandidatoDTO2.setResultado("Descarte");
	        		}
	        	}

	        	HTMLTable tblHistorico = document.getTableById("tblHistoricoCandidato");
	  	        tblHistorico.deleteAllRows();

	  	        	tblHistorico.addRowsByCollection(lista,
	  	                                                new String[] {"idSolicitacaoServico", "nome", "funcao", "resultado" },
	  	                                                null,
	  	                                                "",
	  	                                                null,
	  	                                                null,
	  	                                                null);
	        }

	  }


	@Override
	public Class getBeanClass() {
		return HistoricoCandidatoDTO.class;
	}

}
