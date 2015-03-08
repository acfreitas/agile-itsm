package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.ConexaoBIDTO;
import br.com.centralit.citcorpore.bean.DeParaCatalogoServicosBIDTO;
import br.com.centralit.citcorpore.bean.ServicoBIDTO;
import br.com.centralit.citcorpore.bean.ServicoCorporeBIDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ConexaoBIService;
import br.com.centralit.citcorpore.negocio.DeParaCatalogoServicosBIService;
import br.com.centralit.citcorpore.negocio.ServicoBIService;
import br.com.centralit.citcorpore.negocio.ServicoCorporeBIService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

public class DeParaCatalogoServicosBI extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request,	HttpServletResponse response) throws Exception {
		DeParaCatalogoServicosBIDTO deParaCatalogoServicosBIDTO = new DeParaCatalogoServicosBIDTO();
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request,"citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '"+ Constantes.getValue("SERVER_ADDRESS")+ request.getContextPath() + "'");
			return;
		}
		HTMLSelect idConexaoBI = (HTMLSelect) document.getSelectById("idConexaoBI");
		idConexaoBI.removeAllOptions();
		ConexaoBIService conexaoBIService = (ConexaoBIService) ServiceLocator.getInstance().getService(ConexaoBIService.class, WebUtil.getUsuarioSistema(request));
		Collection<ConexaoBIDTO> listaConexaoBI = null;
		try{
			listaConexaoBI = conexaoBIService.list();
		} catch (Exception e){
			if(e.getMessage().contains("PersistenceException")){
				document.alert(UtilI18N.internacionaliza(request, "conexaoBI.testeConexaoErroConexaoNaoEstabelecida"));
			}
		}
		if ((listaConexaoBI!=null)&&(listaConexaoBI.size()>0)){
			Object[] conexoesBI = listaConexaoBI.toArray();
			for (Object object : conexoesBI) {
				ConexaoBIDTO conexaoBIDTO = (ConexaoBIDTO) object;
				idConexaoBI.addOption(conexaoBIDTO.getIdConexaoBI().toString(), conexaoBIDTO.getNome());
			}
			idConexaoBI.setSelectedIndex(0);
			deParaCatalogoServicosBIDTO.setIdConexaoBI(((ConexaoBIDTO)conexoesBI[0]).getIdConexaoBI());
			document.setBean(deParaCatalogoServicosBIDTO);
			prepararTela(document, request,	response);
		}
	}

	@Override
	public Class<DeParaCatalogoServicosBIDTO> getBeanClass() {
		return DeParaCatalogoServicosBIDTO.class;
	}

	public void relacionar(DocumentHTML document, HttpServletRequest request,	HttpServletResponse response) throws Exception {
		DeParaCatalogoServicosBIDTO deParaCatalogoServicosBIDTO = (DeParaCatalogoServicosBIDTO) document.getBean();
		if (deParaCatalogoServicosBIDTO!=null){
			if((deParaCatalogoServicosBIDTO.getIdConexaoBI()!=null) && (deParaCatalogoServicosBIDTO.getIdServicoDe()!=null) && (deParaCatalogoServicosBIDTO.getIdServicoPara()!=null)) {

				ServicoBIService servicoBIService = (ServicoBIService) ServiceLocator.getInstance().getService(ServicoBIService.class, WebUtil.getUsuarioSistema(request));
				ServicoBIDTO servicoBIDTO = servicoBIService.findByIdEconexaoBI(deParaCatalogoServicosBIDTO.getIdServicoDe(),deParaCatalogoServicosBIDTO.getIdConexaoBI());

				if ((servicoBIDTO!=null) && (servicoBIDTO.getIdServico()!=null) && (servicoBIDTO.getIdServico() > 0)){

					ServicoCorporeBIService servicoCorporeBIService = (ServicoCorporeBIService) ServiceLocator.getInstance().getService(ServicoCorporeBIService.class, WebUtil.getUsuarioSistema(request));
					ServicoCorporeBIDTO servicoCorporeBIDTO = servicoCorporeBIService.findById(deParaCatalogoServicosBIDTO.getIdServicoPara());

					if ((servicoCorporeBIDTO!=null) && (servicoCorporeBIDTO.getIdServicoCorpore()!=null) && (servicoCorporeBIDTO.getIdServicoCorpore() > 0)){

						DeParaCatalogoServicosBIService service = (DeParaCatalogoServicosBIService) ServiceLocator.getInstance().getService(DeParaCatalogoServicosBIService.class, WebUtil.getUsuarioSistema(request));
						DeParaCatalogoServicosBIDTO deParaLocalizado = service.findByidServicoDe(deParaCatalogoServicosBIDTO.getIdServicoDe(), deParaCatalogoServicosBIDTO.getIdConexaoBI());

						if ((deParaLocalizado != null) && (deParaLocalizado.getIdServicoDe() != null) && (deParaLocalizado.getIdServicoDe() > 0)) {

							if ((deParaLocalizado.getIdServicoDe().equals(deParaCatalogoServicosBIDTO.getIdServicoDe())) &&	(!deParaLocalizado.getIdServicoPara().equals(deParaCatalogoServicosBIDTO.getIdServicoPara()))){
								service.delete(deParaLocalizado);
								service.create(deParaCatalogoServicosBIDTO);
							} else {
								document.alert(UtilI18N.internacionaliza(request,"deParaCatalogoServicos.relacionamentoJaExiste"));
								document.getElementById("idServicoDe").setValue("");
								document.getElementById("idServicoPara").setValue("");
								document.getElementById("servicoDe").setValue("");
								document.getElementById("servicoPara").setValue("");
								deParaCatalogoServicosBIDTO.setIdServicoDe(null);
								deParaCatalogoServicosBIDTO.setIdServicoPara(null);
								deParaCatalogoServicosBIDTO.setNomeServicoDe(null);
								deParaCatalogoServicosBIDTO.setNomeServicoPara(null);
								document.setBean(deParaCatalogoServicosBIDTO);
								document.getElementById("idServicoDe").setFocus();
								document.executeScript("JANELA_AGUARDE_MENU.hide();");
								return;
							}
						} else {
							service.create(deParaCatalogoServicosBIDTO);
						}
						prepararTela(document, request,	response);
					}
				}
			}
		}

		document.executeScript("JANELA_AGUARDE_MENU.hide();");
	}

	public void excluirDePara(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		DeParaCatalogoServicosBIDTO deParaCatalogoServicosBIDTO = (DeParaCatalogoServicosBIDTO) document.getBean();
		if (deParaCatalogoServicosBIDTO!=null){
			if((deParaCatalogoServicosBIDTO.getIdConexaoBI()!=null)&&
			   (deParaCatalogoServicosBIDTO.getIdServicoDe()!=null)&&
			   (deParaCatalogoServicosBIDTO.getIdServicoPara()!=null)){
				DeParaCatalogoServicosBIService service = (DeParaCatalogoServicosBIService) ServiceLocator.getInstance().getService(DeParaCatalogoServicosBIService.class, WebUtil.getUsuarioSistema(request));
				service.delete(deParaCatalogoServicosBIDTO);
				prepararTela(document, request,	response);
			}
		}

		document.executeScript("JANELA_AGUARDE_MENU.hide();");
	}

	public void carregarListaDePara(DocumentHTML document, HttpServletRequest request,	HttpServletResponse response) throws Exception {
		DeParaCatalogoServicosBIDTO deParaCatalogoServicosBIDTO = (DeParaCatalogoServicosBIDTO) document.getBean();
		DeParaCatalogoServicosBIService service = (DeParaCatalogoServicosBIService) ServiceLocator.getInstance().getService(DeParaCatalogoServicosBIService.class, WebUtil.getUsuarioSistema(request));
		Collection<DeParaCatalogoServicosBIDTO> listaDePara = service.findByidConexao(deParaCatalogoServicosBIDTO.getIdConexaoBI());
		HTMLTable tblDePara = document.getTableById("tblDePara");
		tblDePara.deleteAllRows();
		StringBuilder tabela = new StringBuilder();
		String btnExcluir = "";
		String txtIdDe = "";
		String txtNomeDe = "";
		String txtIdPara = "";
		String txtNomePara = "";
		tabela.append("<table id='tblDePara' name='tblDePara' class='table  table-bordered'>");
		tabela.append("<tr>");
		tabela.append("<th height='10px' width='2%'></th>");
		tabela.append("<th height='10px' width='8%'>"+UtilI18N.internacionaliza(request,"lookup.idServico")+"</th>");
		tabela.append("<th height='10px' width='41%'>"+UtilI18N.internacionaliza(request,"servico.nome")+"</th>");
		tabela.append("<th height='10px' width='8%'>"+UtilI18N.internacionaliza(request,"lookup.idservicocorpore")+"</th>");
		tabela.append("<th height='10px' width='41%'>"+UtilI18N.internacionaliza(request,"servicoCorporeBI.nomeServico")+"</th>");
		tabela.append("</tr>");
		if ((tblDePara != null)&&((listaDePara != null)&&(listaDePara.size()>0))) {
			for (DeParaCatalogoServicosBIDTO objeto : listaDePara) {
				if ((deParaCatalogoServicosBIDTO.getIdConexaoBI()!=null)&&(objeto.getIdServicoDe()!=null)&&(objeto.getIdServicoPara()!=null)){
					btnExcluir = "<img style=\"cursor: pointer;\" src=\""+br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")+"/imagens/delete.png\" onclick=\"excluirDePara("+deParaCatalogoServicosBIDTO.getIdConexaoBI()+","+objeto.getIdServicoDe()+","+objeto.getIdServicoPara()+ ")\" />";
				} else {
					btnExcluir = "";
				}

				txtIdDe = (String) ((objeto.getIdServicoDe()!=null)?objeto.getIdServicoDe().toString():"");
				txtNomeDe = (String) ((objeto.getNomeServicoDe()!=null)?objeto.getNomeServicoDe().toString():"");
				txtIdPara = (String) ((objeto.getIdServicoPara()!=null)?objeto.getIdServicoPara().toString():"");
				txtNomePara = (String) ((objeto.getNomeServicoPara()!=null)?objeto.getNomeServicoPara().toString():"");

				tabela.append("<tr>");
				tabela.append("<th>"+btnExcluir+"</th>");
				tabela.append("<th>"+txtIdDe+"</th>");
				tabela.append("<th>"+txtNomeDe+"</th>");
				tabela.append("<th>"+txtIdPara+"</th>");
				tabela.append("<th>"+txtNomePara+"</th>");
				tabela.append("</tr>");
			}
		}
		tabela.append("</table>");
		tblDePara.setInnerHTML(tabela.toString());
		//document.getele
		document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblDePara', 'tblDePara');");
    }

	public void prepararTela(DocumentHTML document, HttpServletRequest request,	HttpServletResponse response){
		DeParaCatalogoServicosBIDTO deParaCatalogoServicosBIDTO = (DeParaCatalogoServicosBIDTO) document.getBean();
		try {
			carregarListaDePara(document, request,	response);
			document.getElementById("idServicoDe").setValue("");
			document.getElementById("idServicoPara").setValue("");
			document.getElementById("servicoDe").setValue("");
			document.getElementById("servicoPara").setValue("");
			deParaCatalogoServicosBIDTO.setIdServicoDe(null);
			deParaCatalogoServicosBIDTO.setIdServicoPara(null);
			deParaCatalogoServicosBIDTO.setNomeServicoDe(null);
			deParaCatalogoServicosBIDTO.setNomeServicoPara(null);
			document.setBean(deParaCatalogoServicosBIDTO);
			document.getElementById("idServicoDe").setFocus();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onExitIdDe(DocumentHTML document, HttpServletRequest request,	HttpServletResponse response){
		DeParaCatalogoServicosBIDTO deParaCatalogoServicosBIDTO = (DeParaCatalogoServicosBIDTO) document.getBean();
		if ((deParaCatalogoServicosBIDTO!=null)&&(deParaCatalogoServicosBIDTO.getIdServicoDe()!=null)&&(deParaCatalogoServicosBIDTO.getIdServicoDe()>0)&&(deParaCatalogoServicosBIDTO.getIdConexaoBI()!=null)){
			try {
				ServicoBIService service = (ServicoBIService) ServiceLocator.getInstance().getService(ServicoBIService.class, WebUtil.getUsuarioSistema(request));
				ServicoBIDTO servicoBIDTO = service.findByIdEconexaoBI(deParaCatalogoServicosBIDTO.getIdServicoDe(),deParaCatalogoServicosBIDTO.getIdConexaoBI());
				if ((servicoBIDTO==null)||(servicoBIDTO.getIdServico()==null)||(servicoBIDTO.getIdServico()<=0)){
					document.getElementById("idServicoDe").setValue("");
					document.getElementById("servicoDe").setValue("");
					document.getElementById("idServicoDe").setFocus();
				} else {
					document.getElementById("servicoDe").setValue(servicoBIDTO.getNomeServico());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void onExitIdPara(DocumentHTML document, HttpServletRequest request,	HttpServletResponse response){
		DeParaCatalogoServicosBIDTO deParaCatalogoServicosBIDTO = (DeParaCatalogoServicosBIDTO) document.getBean();
		if ((deParaCatalogoServicosBIDTO!=null)&&(deParaCatalogoServicosBIDTO.getIdServicoPara()!=null)&&(deParaCatalogoServicosBIDTO.getIdServicoPara()>0)){
			try {
				ServicoCorporeBIService service = (ServicoCorporeBIService) ServiceLocator.getInstance().getService(ServicoCorporeBIService.class, WebUtil.getUsuarioSistema(request));
				ServicoCorporeBIDTO servicoCorporeBIDTO = service.findById(deParaCatalogoServicosBIDTO.getIdServicoPara());
				if ((servicoCorporeBIDTO==null)||(servicoCorporeBIDTO.getIdServicoCorpore()==null)||(servicoCorporeBIDTO.getIdServicoCorpore()<=0)){
					document.getElementById("idServicoPara").setValue("");
					document.getElementById("servicoPara").setValue("");
					document.getElementById("idServicoPara").setFocus();
				} else {
					document.getElementById("servicoPara").setValue(servicoCorporeBIDTO.getNomeServico());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}