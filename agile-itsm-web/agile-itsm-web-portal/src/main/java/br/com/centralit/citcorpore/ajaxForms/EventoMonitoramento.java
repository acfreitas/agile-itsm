package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.EventoMonitoramentoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.EventoMonitConhecimentoService;
import br.com.centralit.citcorpore.negocio.EventoMonitoramentoService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

/**
 * 
 * @author Augusto
 *
 */
public class EventoMonitoramento extends AjaxFormAction {
	
	/**
	 * Inicializa os dados ao carregar a tela.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request,"citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '"+ Constantes.getValue("SERVER_ADDRESS")+ request.getContextPath() + "'");
			return;
		}
	}
	
	/**
	 * Inclui registro.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		EventoMonitoramentoDTO eventoMonitoramentoDTO = (EventoMonitoramentoDTO) document.getBean();
		UsuarioDTO usuarioDTO;
		
		// - Tratar caracteres
//		eventoMonitoramentoDTO.setNomeEvento(Util.tratarAspasSimples(eventoMonitoramentoDTO.getNomeEvento().trim()));
//		eventoMonitoramentoDTO.setDetalhamento(Util.tratarAspasSimples(eventoMonitoramentoDTO.getDetalhamento().trim()));
		
		EventoMonitoramentoService eventoMonitoramentoService = (EventoMonitoramentoService) ServiceLocator.getInstance().getService(EventoMonitoramentoService.class, null);
		
		usuarioDTO = (UsuarioDTO) br.com.centralit.citcorpore.util.WebUtil.getUsuario(request);
		if (eventoMonitoramentoDTO.getIdEventoMonitoramento() == null || eventoMonitoramentoDTO.getIdEventoMonitoramento().intValue() == 0) {
			if (usuarioDTO != null) {
				eventoMonitoramentoDTO.setCriadoPor(usuarioDTO.getNomeUsuario());
				eventoMonitoramentoDTO.setModificadoPor(usuarioDTO.getNomeUsuario());
				eventoMonitoramentoDTO.setDataCriacao(UtilDatas.getDataAtual());
				eventoMonitoramentoDTO.setUltimaModificacao(UtilDatas.getDataAtual());
				eventoMonitoramentoService.create(eventoMonitoramentoDTO);
				document.alert(UtilI18N.internacionaliza(request, "MSG05"));
			}
		} else {
			eventoMonitoramentoDTO.setCriadoPor(EventoMonitoramentoDTO.staticCriadoPor);
			eventoMonitoramentoDTO.setModificadoPor(usuarioDTO.getNomeUsuario());
			eventoMonitoramentoDTO.setDataCriacao(EventoMonitoramentoDTO.staticDataCriacao);
			eventoMonitoramentoDTO.setUltimaModificacao(UtilDatas.getDataAtual());
			
			eventoMonitoramentoService.update(eventoMonitoramentoDTO);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}
		HTMLForm form = document.getForm("form");
		form.clear();
	}
	
	/**
	 * Restaura os dados ao clicar em um registro.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		EventoMonitoramentoDTO eventoMonitoramentoDTO = (EventoMonitoramentoDTO) document.getBean();
		EventoMonitoramentoService eventoMonitoramentoService = (EventoMonitoramentoService) ServiceLocator.getInstance().getService(EventoMonitoramentoService.class, null);
		
		eventoMonitoramentoDTO = (EventoMonitoramentoDTO) eventoMonitoramentoService.restore(eventoMonitoramentoDTO);
		
		EventoMonitoramentoDTO.staticCriadoPor = eventoMonitoramentoDTO.getCriadoPor();
		EventoMonitoramentoDTO.staticDataCriacao = eventoMonitoramentoDTO.getDataCriacao();
		
		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(eventoMonitoramentoDTO);
	}
	
	public Class<EventoMonitoramentoDTO> getBeanClass() {
		return EventoMonitoramentoDTO.class;
	}
	
	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		EventoMonitoramentoDTO eventoMonitoramentoDTO = (EventoMonitoramentoDTO) document.getBean();

		EventoMonitoramentoService eventoMonitoramentoService = (EventoMonitoramentoService) ServiceLocator.getInstance().getService(EventoMonitoramentoService.class, WebUtil.getUsuarioSistema(request));

		EventoMonitConhecimentoService eventoMonitConhecimentoService = (EventoMonitConhecimentoService) ServiceLocator.getInstance().getService(EventoMonitConhecimentoService.class, null);
		
		if (eventoMonitoramentoDTO.getIdEventoMonitoramento().intValue() > 0) {
			
			if(eventoMonitConhecimentoService.verificarEventoMonitoramentoComConhecimento(eventoMonitoramentoDTO.getIdEventoMonitoramento())){
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroNaoPodeSerExcluido"));
				return;
			}
			
			eventoMonitoramentoService.delete(eventoMonitoramentoDTO);
		}
		document.alert(UtilI18N.internacionaliza(request, "MSG07"));
		
		HTMLForm form = document.getForm("form");
		form.clear();
	}

}
