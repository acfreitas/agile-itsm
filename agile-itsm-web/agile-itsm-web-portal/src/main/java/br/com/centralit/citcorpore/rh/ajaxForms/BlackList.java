package br.com.centralit.citcorpore.rh.ajaxForms;

import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.rh.bean.BlackListDTO;
import br.com.centralit.citcorpore.rh.bean.CandidatoDTO;
import br.com.centralit.citcorpore.rh.bean.JustificativaListaNegraDTO;
import br.com.centralit.citcorpore.rh.integracao.CandidatoDao;
import br.com.centralit.citcorpore.rh.negocio.BlackListService;
import br.com.centralit.citcorpore.rh.negocio.JustificativaListaNegraService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

/**
 * @author david.silva
 *
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class BlackList extends AjaxFormAction{

	@Override
	public void load(DocumentHTML document, HttpServletRequest request,	HttpServletResponse response) throws Exception {
		WebUtil.validarSeUsuarioEstaNaSessao(request, document);

		if(!(isUserInGroup(request, "RH"))){
			document.executeScript("alert('Voce não tem permição para usar essa Funcionalidade. Apenas Participantes do Grupo RH');");
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "/pages/index/index.jsp'");
			return;
		}

		boolean isOk = false;

		CandidatoDao candidatoDao = new CandidatoDao();
		CandidatoDTO candidatoDto = new CandidatoDTO();
		String idCandidatoStr = request.getParameter("idCandidato");

		BlackListDTO blackListDTO = (BlackListDTO) document.getBean();
		BlackListService blackService = (BlackListService) ServiceLocator.getInstance().getService(BlackListService.class, null);

		isOk = blackService.isCandidatoBlackList(Integer.parseInt(idCandidatoStr));

		if(isOk){
			blackListDTO = blackService.retornaBlackList(blackListDTO.getIdCandidato());
		}

		this.preencherComboJustificativa( document, request, response);

		if(idCandidatoStr != null && idCandidatoStr != ""){

			candidatoDto.setIdCandidato(Integer.parseInt(idCandidatoStr));
			candidatoDto = (CandidatoDTO) candidatoDao.restore(candidatoDto);

			isOk = blackService.isCandidatoBlackList(candidatoDto.getIdCandidato());

			if(isOk){
				document.getElementById("divRetirarListaNegra").setVisible(true);
				document.getElementById("divInserirListaNegra").setVisible(false);
			}else{
				document.getElementById("divInserirListaNegra").setVisible(true);
				document.getElementById("divRetirarListaNegra").setVisible(false);
			}

	    	StringBuilder stringBuffer = new StringBuilder();

	    		if(candidatoDto != null){
	    			stringBuffer.append("<div class='row-fluid'>");
	    				stringBuffer.append("<div class='span12'>");
		    				stringBuffer.append("<h3>"+candidatoDto.getNome()+"</h3>");
	    				stringBuffer.append("</div>");
		    			stringBuffer.append("<div class='span12 '>");

		    			stringBuffer.append("<div class='span6 strong'>CPF: "+candidatoDto.getCpfFormatado()+"</div>");
	    				stringBuffer.append("</div>");
	    				if(isOk){
	    				stringBuffer.append("<div class='span8'>");
	    					stringBuffer.append("<div class='alert alert-warning strong' style='text-align: center;'>"+ UtilI18N.internacionaliza(request, "rh.avisoListaNegra") +"</div>");
	    				stringBuffer.append("</div>");
	    				}
	    			stringBuffer.append("</div>");
	    			document.getElementById("informacoesCandidato").setInnerHTML(stringBuffer.toString());
	    		}


		}else{
			document.alert(UtilI18N.internacionaliza(request, "dinamicview.naofoipossivelrecuperar"));
		}

		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(blackListDTO);

    }

	public void testaAcao(DocumentHTML document, HttpServletRequest request,	HttpServletResponse response) throws Exception {
		WebUtil.validarSeUsuarioEstaNaSessao(request, document);

		BlackListDTO blackListDTO = (BlackListDTO) document.getBean();

		if(blackListDTO.getIdJustificativa() == null || blackListDTO.getIdJustificativa() == 0 ){
			document.alert(UtilI18N.internacionaliza(request, "suspensaoReativacaoSolicitacao.alertaCampoVaziojustificativa"));
			return;
		}

		if(StringUtils.isBlank(blackListDTO.getDescricao())){
			document.alert(UtilI18N.internacionaliza(request, "pesquisa.obrigatorio"));
			return;
		}

		if(blackListDTO.getAcao().equalsIgnoreCase("N")){
			retirarListaNegra(document, request, response, blackListDTO);
		}else{
			inserirListaNegra(document, request, response, blackListDTO);
		}

	}

	public void retirarListaNegra(DocumentHTML document, HttpServletRequest request,HttpServletResponse response, BlackListDTO blackListDTO) throws Exception {
		UsuarioDTO usuarioDto = WebUtil.getUsuario(request);
		WebUtil.validarSeUsuarioEstaNaSessao(request, document);

		BlackListService blackListService = (BlackListService) ServiceLocator.getInstance().getService(BlackListService.class, null);

		Date dataFim = UtilDatas.getDataAtual();
		Integer idUsuario = usuarioDto.getIdUsuario();

		if(dataFim != null && !dataFim.equals("") && idUsuario != null && !idUsuario.equals("")){
			blackListDTO.setDataFim(UtilDatas.getDataAtual());
		}

		if(blackListDTO.getIdListaNegra() != null || blackListDTO.getIdListaNegra() > 0){
			blackListService.update(blackListDTO);
			blackListService.inserirRegistroHistorico(blackListDTO.getIdCandidato(), idUsuario, false);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}

		HTMLForm form = document.getForm("form");
		form.clear();
		document.executeScript("parent.atualizarTabela();");
		document.executeScript("parent.fecharModalAddBlackList();");


	}

	public void inserirListaNegra(DocumentHTML document, HttpServletRequest request,HttpServletResponse response, BlackListDTO blackListDTO) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		WebUtil.validarSeUsuarioEstaNaSessao(request, document);

		BlackListService blackListService = (BlackListService) ServiceLocator.getInstance().getService(BlackListService.class, null);

		Date dataAtual = UtilDatas.getDataAtual();
		Integer idUsuario = usuario.getIdUsuario();

		if(dataAtual != null && !dataAtual.equals("") && idUsuario != null && !idUsuario.equals("")){
			blackListDTO.setDataInicio(UtilDatas.getDataAtual());
			blackListDTO.setIdResponsavel(usuario.getIdUsuario());
		}

		if(blackListDTO.getIdListaNegra() == null || blackListDTO.getIdListaNegra() == 0){
			blackListService.create(blackListDTO);
			blackListService.inserirRegistroHistorico(blackListDTO.getIdCandidato(), blackListDTO.getIdResponsavel(), true);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
		}

		HTMLForm form = document.getForm("form");
		form.clear();
		document.executeScript("parent.atualizarTabela();");
		document.executeScript("parent.fecharModalAddBlackList();");

	}

	public void save(DocumentHTML document, HttpServletRequest request,	HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		WebUtil.validarSeUsuarioEstaNaSessao(request, document);

		BlackListDTO blackListDTO = (BlackListDTO) document.getBean();
		BlackListService blackListService = (BlackListService) ServiceLocator.getInstance().getService(BlackListService.class, null);

		Date dataAtual = UtilDatas.getDataAtual();
		Integer idUsuario = usuario.getIdUsuario();

		if(dataAtual != null && !dataAtual.equals("") && idUsuario != null && !idUsuario.equals("")){
			blackListDTO.setDataInicio(UtilDatas.getDataAtual());
			blackListDTO.setIdResponsavel(usuario.getIdUsuario());
		}

		if(blackListDTO.getIdListaNegra() == null || blackListDTO.getIdListaNegra() == 0){
			blackListService.create(blackListDTO);

			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
		} else {
			blackListService.update(blackListDTO);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}

		HTMLForm form = document.getForm("form");
		form.clear();
		document.executeScript("parent.fecharModalAddBlackList();");

	}

	public void restore(DocumentHTML document, HttpServletRequest request,	HttpServletResponse response) throws Exception {
		WebUtil.validarSeUsuarioEstaNaSessao(request, document);

		BlackListDTO blackListDTO = (BlackListDTO) document.getBean();
		BlackListService blackListService = (BlackListService) ServiceLocator.getInstance().getService(BlackListService.class, null);

		blackListDTO = (BlackListDTO) blackListService.restore(blackListDTO);

		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(blackListDTO);

	}

	private void preencherComboJustificativa(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HTMLSelect comboJustificativa = (HTMLSelect) document.getSelectById("idJustificativa");

		if(comboJustificativa != null){
			this.inicializarCombo(comboJustificativa, request);

			JustificativaListaNegraService justificativaListaNegraService = (JustificativaListaNegraService) ServiceLocator.getInstance().getService(JustificativaListaNegraService.class, null);
			HTMLSelect cmbJustificativa = (HTMLSelect) document.getSelectById("idJustificativa");
			ArrayList<JustificativaListaNegraDTO> justificativaBlackList = (ArrayList) justificativaListaNegraService.list();

			inicializarCombo(cmbJustificativa, request);

			for (JustificativaListaNegraDTO justificativaDto : justificativaBlackList) {
				cmbJustificativa.addOption(justificativaDto.getIdJustificativa().toString(), StringEscapeUtils.escapeJavaScript(justificativaDto.getJustificativa()));
			}

		}
	}

	/**
	 * Iniciliza combo.
	 *
	 * @param componenteCombo
	 */
	private void inicializarCombo(HTMLSelect componenteCombo, HttpServletRequest request) {
		componenteCombo.removeAllOptions();
		componenteCombo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
	}

	public boolean isUserInGroup(HttpServletRequest req, String grupo) {
		UsuarioDTO usuario = WebUtil.getUsuario(req);
		if (usuario == null) {
			return false;
		}

		String[] grupos = usuario.getGrupos();
		String grpAux = UtilStrings.nullToVazio(grupo);
		for (int i = 0; i < grupos.length; i++) {
			if (grupos[i] != null) {
				if (grupos[i].trim().indexOf(grpAux.trim()) > 0) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public Class getBeanClass() {
		return BlackListDTO.class;
	}

}
