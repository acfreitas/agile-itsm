package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.AlcadaProcessoNegocioDTO;
import br.com.centralit.citcorpore.bean.CentroResultadoDTO;
import br.com.centralit.citcorpore.bean.DelegacaoCentroResultadoDTO;
import br.com.centralit.citcorpore.bean.DelegacaoCentroResultadoFluxoDTO;
import br.com.centralit.citcorpore.bean.DelegacaoCentroResultadoProcessoDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.ExecucaoSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.ProcessoNegocioDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CentroResultadoService;
import br.com.centralit.citcorpore.negocio.DelegacaoCentroResultadoFluxoService;
import br.com.centralit.citcorpore.negocio.DelegacaoCentroResultadoProcessoService;
import br.com.centralit.citcorpore.negocio.DelegacaoCentroResultadoService;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.ExecucaoSolicitacaoService;
import br.com.centralit.citcorpore.negocio.ProcessoNegocioService;
import br.com.centralit.citcorpore.negocio.alcada.AlcadaProcessoNegocio;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class DelegacaoCentroResultado extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		
        CentroResultadoService centroResultadoService = (CentroResultadoService) ServiceLocator.getInstance().getService(CentroResultadoService.class, WebUtil.getUsuarioSistema(request));
        HTMLSelect idCentroResultado = (HTMLSelect) document.getSelectById("idCentroResultado");
        idCentroResultado.removeAllOptions();
        idCentroResultado.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
        Collection colCCusto = centroResultadoService.listAtivos();
        if(colCCusto != null && !colCCusto.isEmpty())
        	idCentroResultado.addOptions(colCCusto, "idCentroResultado", "nomeHierarquizado", null);

	}

	@Override
	public Class getBeanClass() {
		return DelegacaoCentroResultadoDTO.class;
	}

	public void pesquisa(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String str = 
                "<table id='tblResponsaveis' class=\"table\" width=\"100%\">";
         str += "    <tr>";
         str += "        <th >"+UtilI18N.internacionaliza(request, "delegacaoCentroResultado.empregado") + "</th>";
         str += "        <th width=\"30%\">"+UtilI18N.internacionaliza(request, "delegacaoCentroResultado.processo") + "</th>";
         str += "        <th width=\"20%\">"+UtilI18N.internacionaliza(request, "delegacaoCentroResultado.autoridade") + "</th>";
         str += "    </tr>";

        try{
			UsuarioDTO usuario = WebUtil.getUsuario(request);
			if (usuario == null) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
				document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
				return;
			}
			
	        DelegacaoCentroResultadoDTO delegacaoCentroResultadoDto = (DelegacaoCentroResultadoDTO) document.getBean();
	        if (delegacaoCentroResultadoDto.getIdCentroResultado() == null)
	            return;
	        
	        CentroResultadoDTO centroResultadoDto = new CentroResultadoDTO();
	        centroResultadoDto.setIdCentroResultado(delegacaoCentroResultadoDto.getIdCentroResultado());
	        
	        Collection<AlcadaProcessoNegocioDTO> colAlcadas = AlcadaProcessoNegocio.getInstance().getAlcadasCentroResultado(centroResultadoDto, null);
	        if (colAlcadas != null) {
	        	for (AlcadaProcessoNegocioDTO alcadaProcessoNegocioDto : colAlcadas) {
	        		if (delegacaoCentroResultadoDto.getIdResponsavel() != null && alcadaProcessoNegocioDto.getEmpregadoDto().getIdEmpregado().intValue() != delegacaoCentroResultadoDto.getIdResponsavel().intValue())
	        			continue;
	        		String serialize_processos = br.com.citframework.util.WebUtil.serializeObjects(alcadaProcessoNegocioDto.getProcessosNegocio());
					str += "<tr id='tr"+alcadaProcessoNegocioDto.getEmpregadoDto().getIdEmpregado()+"' onclick='exibirDelegacoes(this,"+alcadaProcessoNegocioDto.getEmpregadoDto().getIdEmpregado()+",\""+serialize_processos+"\")' >";
					str += "	<td rowspan='"+alcadaProcessoNegocioDto.getProcessosNegocio().size()+"'>";
					str += alcadaProcessoNegocioDto.getEmpregadoDto().getNome();
					str += "	</td>";
					int i = 0;
					for (ProcessoNegocioDTO processoNegocioDto : alcadaProcessoNegocioDto.getProcessosNegocio()) {
                        if (i > 0)
                            str += "<tr id='tr"+alcadaProcessoNegocioDto.getEmpregadoDto().getIdEmpregado()+"' onclick='exibirDelegacoes(this,"+alcadaProcessoNegocioDto.getEmpregadoDto().getIdEmpregado()+",\""+serialize_processos+"\")' >";
                        i++;
    					str += "	<td>";
    					str += processoNegocioDto.getNomeProcessoNegocio();
    					str += "	</td>";
    					str += "	<td>";
    					str += processoNegocioDto.getNivelAutoridadeDto().getNomeNivelAutoridade();
    					str += "	</td>";
                        str += "</tr>";
					}
				}
	        }
		}finally{
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			document.getElementById("divResponsaveis").setInnerHTML(str);
			document.getElementById("divDelegacoes").setInnerHTML("");
		}
	}
	
	public void exibeDelegacoes(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String str = 
                "<table id='tblDelegacoes' class=\"table\" width=\"100%\">";
         str += "    <tr>";
         str += "        <th >"+UtilI18N.internacionaliza(request, "delegacaoCentroResultado.empregado") + "</th>";
         str += "        <th width=\"15%\">"+UtilI18N.internacionaliza(request, "delegacaoCentroResultado.processo") + "</th>";
         str += "        <th width=\"5%\">"+UtilI18N.internacionaliza(request, "delegacaoCentroResultado.inicio") + "</th>";
         str += "        <th width=\"5%\">"+UtilI18N.internacionaliza(request, "delegacaoCentroResultado.termino") + "</th>";
         str += "        <th width=\"30%\">"+UtilI18N.internacionaliza(request, "delegacaoCentroResultado.abrangencia") + "</th>";
         str += "        <th width=\"10%\">"+UtilI18N.internacionaliza(request, "delegacaoCentroResultado.situacao") + "</th>";
         str += "        <th width=\"5%\">&nbsp;</th>";
         str += "    </tr>";

        try{
			UsuarioDTO usuario = WebUtil.getUsuario(request);
			if (usuario == null) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
				document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
				return;
			}
			
	        DelegacaoCentroResultadoDTO delegacaoCentroResultadoDto = (DelegacaoCentroResultadoDTO) document.getBean();
	        if (delegacaoCentroResultadoDto.getIdCentroResultado() == null || delegacaoCentroResultadoDto.getIdResponsavel() == null)
	            return;
	        
	        DelegacaoCentroResultadoService delegacaoCentroResultadoService = (DelegacaoCentroResultadoService) ServiceLocator.getInstance().getService(DelegacaoCentroResultadoService.class, WebUtil.getUsuarioSistema(request));
	        Collection<DelegacaoCentroResultadoDTO> colDelegacoes = delegacaoCentroResultadoService.findByIdResponsavelAndIdCentroResultado(delegacaoCentroResultadoDto.getIdResponsavel(), delegacaoCentroResultadoDto.getIdCentroResultado());
	        if (colDelegacoes != null) {
		        EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, WebUtil.getUsuarioSistema(request));
		        ProcessoNegocioService processoNegocioService = (ProcessoNegocioService) ServiceLocator.getInstance().getService(ProcessoNegocioService.class, WebUtil.getUsuarioSistema(request));
		        DelegacaoCentroResultadoProcessoService delegacaoCentroResultadoProcessoService = (DelegacaoCentroResultadoProcessoService) ServiceLocator.getInstance().getService(DelegacaoCentroResultadoProcessoService.class, WebUtil.getUsuarioSistema(request));
		        DelegacaoCentroResultadoFluxoService delegacaoCentroResultadoFluxoService = (DelegacaoCentroResultadoFluxoService) ServiceLocator.getInstance().getService(DelegacaoCentroResultadoFluxoService.class, WebUtil.getUsuarioSistema(request));
		        ExecucaoSolicitacaoService execucaoSolicitacaoService = (ExecucaoSolicitacaoService) ServiceLocator.getInstance().getService(ExecucaoSolicitacaoService.class, WebUtil.getUsuarioSistema(request));
	        	
		        for (DelegacaoCentroResultadoDTO delegacaoDto : colDelegacoes) {
	        		EmpregadoDTO empregadoDto = new EmpregadoDTO();
	        		empregadoDto.setIdEmpregado(delegacaoDto.getIdEmpregado());
	        		empregadoDto = (EmpregadoDTO) empregadoService.restore(empregadoDto);
	        		
	        		Collection<DelegacaoCentroResultadoProcessoDTO> colProcessos = delegacaoCentroResultadoProcessoService.findByIdDelegacaoCentroResultado(delegacaoDto.getIdDelegacaoCentroResultado());
					str += "<tr>";
					str += "	<td>";
					str += empregadoDto.getNome();
					str += "	</td>";
					str += "	<td>";
					String processos = "&nbsp;";
					if (colProcessos != null && colProcessos.size() > 0) {
						processos = "";
						for (DelegacaoCentroResultadoProcessoDTO delegacaoCentroResultadoProcessoDto : colProcessos) {
							ProcessoNegocioDTO processoDto = new ProcessoNegocioDTO();
							processoDto.setIdProcessoNegocio(delegacaoCentroResultadoProcessoDto.getIdProcessoNegocio());
							processoDto = (ProcessoNegocioDTO) processoNegocioService.restore(processoDto);
							processos += processoDto.getNomeProcessoNegocio()+"<br>";
						}
					}
					str += processos+"</td>"; 
					str += "	<td>"+UtilDatas.dateToSTR(delegacaoDto.getDataInicio())+"</td>";
					str += "	<td>"+UtilDatas.dateToSTR(delegacaoDto.getDataFim())+"</td>";
					
					if (delegacaoDto.getAbrangencia().equalsIgnoreCase(DelegacaoCentroResultadoDTO.NOVAS_EXISTENTES)) {
						str += "	<td>"+UtilI18N.internacionaliza(request, "delegacaoCentroResultado.abrangencia.novasEEmAndamento")+"</td>";
					}else if (delegacaoDto.getAbrangencia().equalsIgnoreCase(DelegacaoCentroResultadoDTO.NOVAS)) {
						str += "	<td>"+UtilI18N.internacionaliza(request, "delegacaoCentroResultado.abrangencia.novas")+"</td>";
					}else if (delegacaoDto.getAbrangencia().equalsIgnoreCase(DelegacaoCentroResultadoDTO.ESPECIFICAS)) {
						Collection<DelegacaoCentroResultadoFluxoDTO> colInstancias = delegacaoCentroResultadoFluxoService.findByIdDelegacaoCentroResultado(delegacaoDto.getIdDelegacaoCentroResultado());
						if (colInstancias != null) {
							String ids = "";
							int i = 0;
							for (DelegacaoCentroResultadoFluxoDTO delegacaoCentroResultadoFluxoDto : colInstancias) {
								ExecucaoSolicitacaoDTO execucaoSolicitacaoDto = execucaoSolicitacaoService.findByIdInstanciaFluxo(delegacaoCentroResultadoFluxoDto.getIdInstanciaFluxo());
								if (execucaoSolicitacaoDto != null) {
									if (i > 0)
										ids += ", ";
									ids += execucaoSolicitacaoDto.getIdSolicitacaoServico(); 
									i++;
								}
							}
							if (!ids.equals(""))
								str += "	<td>"+UtilI18N.internacionaliza(request, "delegacaoCentroResultado.abrangencia.especificas")+": "+ids+"</td>";
							else
								str += "	<td>&nbsp;</td>";
						}
					}else{
						str += "	<td>&nbsp;</td>";
					}
					
					if (UtilStrings.nullToVazio(delegacaoDto.getRevogada()).equalsIgnoreCase("S")) {
						str += "	<td>"+UtilI18N.internacionaliza(request, "delegacaoCentroResultado.situacao.revogada")+"</td>";
						str += "	<td>&nbsp;</td>";
					}else if (delegacaoDto.getDataFim().compareTo(UtilDatas.getDataAtual()) < 0) {
						str += "	<td>"+UtilI18N.internacionaliza(request, "delegacaoCentroResultado.situacao.expirada")+"</td>";
						str += "	<td>&nbsp;</td>";
					}else{
						str += "	<td>"+UtilI18N.internacionaliza(request, "delegacaoCentroResultado.situacao.emVigor")+"</td>";
						str += "	<td><button type='button' class='light img_icon has_text' onclick='revogar("+delegacaoDto.getIdDelegacaoCentroResultado()+")'>"
                              +UtilI18N.internacionaliza(request, "delegacaoCentroResultado.revogar")+"</button></td>";
					}
				}
	        }
		}finally{
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			document.getElementById("divDelegacoes").setInnerHTML(str);
		}
	}
	
	public void gravaDelegacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try{
			UsuarioDTO usuario = WebUtil.getUsuario(request);
			if (usuario == null) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
				document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
				return;
			}
			
	        DelegacaoCentroResultadoDTO delegacaoCentroResultadoDto = (DelegacaoCentroResultadoDTO) document.getBean();
	        if (delegacaoCentroResultadoDto.getIdCentroResultado() == null || delegacaoCentroResultadoDto.getIdResponsavel() == null)
	            return;
	        
	        if (delegacaoCentroResultadoDto.getIdEmpregado() == null) {
	        	document.alert(UtilI18N.internacionaliza(request, "delegacaoCentroResultado.empregado")+" "+UtilI18N.internacionaliza(request, "citcorpore.comum.naoInformado"));
	        	return;
	        }
	        if (delegacaoCentroResultadoDto.getIdProcessoNegocio() == null || delegacaoCentroResultadoDto.getIdProcessoNegocio().length == 0) {
	        	document.alert(UtilI18N.internacionaliza(request, "delegacaoCentroResultado.processo")+" "+UtilI18N.internacionaliza(request, "citcorpore.comum.naoInformado"));
	        	return;
	        }
	        if (delegacaoCentroResultadoDto.getAbrangencia() == null) {
	        	document.alert(UtilI18N.internacionaliza(request, "delegacaoCentroResultado.abrangencia")+" "+UtilI18N.internacionaliza(request, "citcorpore.comum.naoInformado"));
	        	return;
	        }
	        if (delegacaoCentroResultadoDto.getDataInicio() == null || delegacaoCentroResultadoDto.getDataInicio().compareTo(UtilDatas.getDataAtual()) < 0) {
	        	document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.datainvalida"));
	        	return;
	        }
	        if (delegacaoCentroResultadoDto.getDataFim() == null || delegacaoCentroResultadoDto.getDataFim().compareTo(delegacaoCentroResultadoDto.getDataInicio()) < 0) {
	        	document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.dataFinalInvalida"));
	        	return;
	        }
	        
	        delegacaoCentroResultadoDto.setDataHoraRegistro(UtilDatas.getDataHoraAtual());
	        delegacaoCentroResultadoDto.setIdResponsavelRegistro(usuario.getIdEmpregado());
	        
	        DelegacaoCentroResultadoService delegacaoCentroResultadoService = (DelegacaoCentroResultadoService) ServiceLocator.getInstance().getService(DelegacaoCentroResultadoService.class, WebUtil.getUsuarioSistema(request));
	        delegacaoCentroResultadoService.create(delegacaoCentroResultadoDto);
	        document.executeScript("$(\"#POPUP_DELEGACAO\").dialog(\"close\")");
	        exibeDelegacoes(document,request,response);
		}finally{
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
		}
	}
	
	public void revogaDelegacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try{
			UsuarioDTO usuario = WebUtil.getUsuario(request);
			if (usuario == null) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
				document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
				return;
			}
			
	        DelegacaoCentroResultadoDTO delegacaoCentroResultadoDto = (DelegacaoCentroResultadoDTO) document.getBean();
	        if (delegacaoCentroResultadoDto.getIdDelegacaoCentroResultado() == null)
	            return;
	        
	        delegacaoCentroResultadoDto.setIdResponsavelRevogacao(usuario.getIdEmpregado());
	        
	        DelegacaoCentroResultadoService delegacaoCentroResultadoService = (DelegacaoCentroResultadoService) ServiceLocator.getInstance().getService(DelegacaoCentroResultadoService.class, WebUtil.getUsuarioSistema(request));
	        delegacaoCentroResultadoService.revoga(delegacaoCentroResultadoDto);
	        exibeDelegacoes(document,request,response);
		}finally{
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
		}
	}

}