package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.CtrlAsteriskDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.util.Telefone;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;

/**
 * @author euler.ramos
 */
public class CtrlAsterisk extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	}

	public void abrePopUpAsterisk(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CtrlAsteriskDTO ctrlAsteriskDTO = (CtrlAsteriskDTO) document.getBean();
		//Este número vem da sessão:
		if (request.getSession().getAttribute("ramalTelefone")!=null){
			ctrlAsteriskDTO.setRamalTelefone(request.getSession().getAttribute("ramalTelefone").toString());
		}
		if ((ctrlAsteriskDTO.getListaChamadas()!=null)&&(ctrlAsteriskDTO.getListaChamadas().length()>0)){
			if ((ctrlAsteriskDTO.getRamalTelefone()!=null)&&(ctrlAsteriskDTO.getRamalTelefone().length()>0)){
				//Quebrando por Ligações
				String[] arrayListaTelefones = ctrlAsteriskDTO.getListaChamadas().split("#");
				StringBuilder html = new StringBuilder();
				StringBuilder campoNome = new StringBuilder();
				StringBuilder campoTelefone = new StringBuilder();
				EmpregadoDTO empregadoDto = new EmpregadoDTO();
				StringBuilder condicaoProcura;
				EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, WebUtil.getUsuarioSistema(request));
				for(String ligacao : arrayListaTelefones){
					//Separando os campos
					String[] chamada = ligacao.split(",");
					//Se Destino for o ramal deste cliente
					if (chamada[1].equals(ctrlAsteriskDTO.getRamalTelefone())){
						condicaoProcura = new StringBuilder();
						condicaoProcura.append(Telefone.mascaraProcuraSql(chamada[0]));
						empregadoDto = empregadoService.findByTelefoneOrRamal(condicaoProcura.toString());
						if (empregadoDto != null && empregadoDto.getNome() != null && org.apache.commons.lang.StringUtils.isNotBlank(empregadoDto.getNome())) {
							campoNome.append("<p>" + empregadoDto.getNome() + "</p>");
							html.append("<input id='idEmpregado' type='hidden' value='" + empregadoDto.getIdEmpregado() + "'/>");
						} else {
							campoNome.append("<p>" + UtilI18N.internacionaliza(WebUtil.getUsuarioSistema(request).getLocale(), "citcorpore.comum.telefone.CliNaoIdentificado") + "</p>");
						}
						campoTelefone.append("<p>" + Telefone.numeroMascarado(chamada[0], false) + "</p>");
						html.append("<div class='box-generic'>");
						html.append("	<div class='row-fluid'>");
						html.append("		<div class='span6'>");
						html.append("			<label class='strong'>"+UtilI18N.internacionaliza(request, "citcorpore.comum.nome")+"</label>");
						html.append(campoNome.toString());
						html.append("		</div>");
						html.append("		<div class='span6'>");
						html.append("			<label class='strong'>"+UtilI18N.internacionaliza(request, "citcorpore.comum.telefone")+"</label>");
						html.append(campoTelefone.toString());
						html.append("		</div>");
						html.append("	</div>");
						html.append("	<div class='row-fluid'>");
						html.append("		<div class='span12 innerTB'>");
						html.append("			<button class='btn btn-block btn-primary' onclick='abrirSolicitacao();' type='button'>"+UtilI18N.internacionaliza(request, "solicitacaoServico.cadastrosolicitacao")+"</button>");
						html.append("		</div>");
						html.append("	</div>");
						html.append("</div>");
						html.append("<style>");
						html.append("div.container{width:100%;margin:0px;border:1px solid gray;line-height:25%;}");
						html.append("div.tituloleft{float:left;padding:0.5em;color:white;background-color:#CED8E6;width:400px;margin:0;}");
						html.append("div.tituloright{margin-left:160px;padding:0.5em;color:white;background-color:#CED8E6;}");
						html.append("div.conteudoleft{float:left;padding:0.5em;color:black;width:400px;margin:0;}");
						html.append("div.conteudoright{margin-left:390px;padding:0.5em;color:black;border-left:1px solid #CED8E6;}");
						html.append("button.btnAbrirSolicitacao{width:30%;margin:0px; padding-left: 0px;}");
						html.append("</style>");
						document.getElementById("conteudoframeTelefonia").setInnerHTML(html.toString());
						document.executeScript("$('#modal_Telefonia').modal('show');");
						break; //Não haverá mais do que uma chamada ativa para este ramal, outras tentativas receberão sinal de ocupado.
					}
				}
			}
		}
	}
	
	public void abreRamalTelefone(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CtrlAsteriskDTO ctrlAsteriskDTO = (CtrlAsteriskDTO) document.getBean();
		if (request.getSession().getAttribute("ramalTelefone") != null) {
			ctrlAsteriskDTO.setRamalTelefone(request.getSession().getAttribute("ramalTelefone").toString());
		}
		HTMLForm form = document.getForm("formCtrlAsterisk");
		form.clear();
		form.setValues(ctrlAsteriskDTO);
		
		document.executeScript("$('#modal_ramalTelefone').modal('show');");
	}

	public void gravarRamalTelefone(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CtrlAsteriskDTO ctrlAsteriskDTO = (CtrlAsteriskDTO) document.getBean();
		if (ctrlAsteriskDTO != null) {
			request.getSession().setAttribute("ramalTelefone", ctrlAsteriskDTO.getRamalTelefone());
			if ((ctrlAsteriskDTO.getRamalTelefone() != null) || (ctrlAsteriskDTO.getRamalTelefone().length() > 0)) {
				document.alert(UtilI18N.internacionaliza(WebUtil.getUsuarioSistema(request).getLocale(), "citcorpore.comum.ramal.ramalGravadoSucesso"));
				// document.alert((String)request.getSession().getAttribute("ramalTelefone").toString());
				document.executeScript("$('#modal_ramalTelefone').modal('hide');");
			}
		} else {
			// Quando o usuário esvaziou o campo o sistema retira este cliente da Thread MonitoraAsterisk, para não sobrecarregá-la!
			request.getSession().setAttribute("ramalTelefone", null);
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getBeanClass() {
		return CtrlAsteriskDTO.class;
	}
}