package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CalendarioService;
import br.com.centralit.citcorpore.negocio.JustificativaSolicitacaoService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

public class MudarSLA2 extends AjaxFormAction {

    @Override
    public Class getBeanClass() {
	return SolicitacaoServicoDTO.class;
    }

    @SuppressWarnings("rawtypes")
	@Override
    public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	UsuarioDTO usuario = WebUtil.getUsuario(request);
	if (usuario == null){
		document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
		document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
		return;
	}
	HTMLForm form = document.getForm("form");
	form.clear();	
	SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO)document.getBean();
	SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
	CalendarioService calendarioService = (CalendarioService) ServiceLocator.getInstance().getService(CalendarioService.class, null);
	JustificativaSolicitacaoService justificativaService = (JustificativaSolicitacaoService) ServiceLocator.getInstance().getService(JustificativaSolicitacaoService.class, null);
	solicitacaoServicoDto = solicitacaoServicoService.restoreAll(solicitacaoServicoDto.getIdSolicitacaoServico());
	request.setAttribute("dataHoraSolicitacao", solicitacaoServicoDto.getDataHoraSolicitacaoStr());
	
	HTMLSelect slaACombinar = (HTMLSelect) document.getSelectById("slaACombinar");
		slaACombinar.addOption("S", "-- "+UtilI18N.internacionaliza(request, "citcorpore.comum.acombinar")+" --");
		slaACombinar.addOption("N", UtilI18N.internacionaliza(request, "citcorpore.comum.definirtempo"));
	
	@SuppressWarnings("rawtypes")
	Collection colJustificativas = justificativaService.listAtivasParaSuspensao();
	document.getSelectById("idJustificativa").removeAllOptions();
	document.getSelectById("idJustificativa").addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
	if (colJustificativas != null){
	    document.getSelectById("idJustificativa").addOptions(colJustificativas, "idJustificativa", "descricaoJustificativa", null);
	}	
	
	Collection colCalendarios = calendarioService.list();
	document.getSelectById("idCalendario").removeAllOptions();
	document.getSelectById("idCalendario").addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
	if (colCalendarios != null){
	    document.getSelectById("idCalendario").addOptions(colCalendarios, "idCalendario", "descricao", null);
	}
	form.setValues(solicitacaoServicoDto);
	document.executeScript("verificaMudarTipoSLA()");
    }
    public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	UsuarioDTO usuario = WebUtil.getUsuario(request);
	if (usuario == null){
		document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
		document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
		return;
	}	
		
	SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO)document.getBean();
	SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
	SolicitacaoServicoDTO solicitacaoServicoAux = solicitacaoServicoService.restoreAll(solicitacaoServicoDto.getIdSolicitacaoServico());
	solicitacaoServicoDto.setIdSolicitante(solicitacaoServicoAux.getIdSolicitante());
	solicitacaoServicoDto.setDataInicio(solicitacaoServicoAux.getDataInicio());
	solicitacaoServicoDto.setDataHoraSolicitacao(solicitacaoServicoAux.getDataHoraSolicitacao());
	solicitacaoServicoDto.setUsuarioDto(usuario);
	solicitacaoServicoDto.setRegistradoPor(usuario.getNomeUsuario());	
	solicitacaoServicoDto.setHouveMudanca("S");
	solicitacaoServicoDto.setSituacao(solicitacaoServicoAux.getSituacao());
	solicitacaoServicoDto.setPrazohhAnterior(solicitacaoServicoAux.getPrazoHH());
	solicitacaoServicoDto.setPrazommAnterior(solicitacaoServicoAux.getPrazoMM());
	solicitacaoServicoDto.setTempoDecorridoHH(solicitacaoServicoAux.getTempoDecorridoHH());
	solicitacaoServicoDto.setTempoDecorridoMM(solicitacaoServicoAux.getTempoDecorridoMM());
	
	if (solicitacaoServicoDto.getSlaACombinar().equalsIgnoreCase("S")){
        solicitacaoServicoDto.setPrazoCapturaHH(0);
        solicitacaoServicoDto.setPrazoCapturaMM(0);	    
	    solicitacaoServicoDto.setPrazoHH(0);
	    solicitacaoServicoDto.setPrazoMM(0);
	}
	solicitacaoServicoService.updateSLA(solicitacaoServicoDto);
	document.executeScript("parent.fecharModalMudarSLA();");
    }
}
