package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.bpm.dto.FluxoDTO;
import br.com.centralit.bpm.servico.FluxoService;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.AlcadaProcessoNegocioDTO;
import br.com.centralit.citcorpore.bean.CentroResultadoDTO;
import br.com.centralit.citcorpore.bean.SimulacaoAlcadaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CentroResultadoService;
import br.com.centralit.citcorpore.negocio.alcada.AlcadaProcessoNegocio;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class SimulacaoAlcada extends AjaxFormAction {

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

		FluxoService fluxoService = (FluxoService) ServiceLocator.getInstance().getService(FluxoService.class, WebUtil.getUsuarioSistema(request));
        HTMLSelect idTipoFluxo = (HTMLSelect) document.getSelectById("idTipoFluxo");
        idTipoFluxo.removeAllOptions();
        idTipoFluxo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
        Collection colTiposFluxo = fluxoService.list();
        if(colTiposFluxo != null && !colTiposFluxo.isEmpty())
        	idTipoFluxo.addOptions(colTiposFluxo, "idTipoFluxo", "descricao", null);

		HTMLSelect finalidade = (HTMLSelect) document.getSelectById("finalidade");
		finalidade.removeAllOptions();
		finalidade.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
        finalidade.addOption("I", UtilI18N.internacionaliza(request, "requisicaoProduto.finalidade.usoInterno"));
		finalidade.addOption("C", UtilI18N.internacionaliza(request, "requisicaoProduto.finalidade.atendimentoCliente"));

	}

	@Override
	public Class getBeanClass() {
		return SimulacaoAlcadaDTO.class;
	}

	public void pesquisa(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String str = 
                "<table id='tblResponsaveis' class=\"table\" width=\"100%\">";
         str += "    <tr>";
         str += "        <th >"+UtilI18N.internacionaliza(request, "delegacaoCentroResultado.empregado") + "</th>";
         str += "        <th width=\"10%\">"+UtilI18N.internacionaliza(request, "delegacaoCentroResultado.autoridade") + "</th>";
         str += "        <th width=\"5%\">Delegação</th>";
         str += "        <th width=\"5%\">Rejeitado</th>";
         str += "        <th width=\"40%\">Motivo da rejeição</th>";
         str += "    </tr>";

        try{
			UsuarioDTO usuario = WebUtil.getUsuario(request);
			if (usuario == null) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
				document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
				return;
			}
			
	        SimulacaoAlcadaDTO simulacaoAlcadaDto = (SimulacaoAlcadaDTO) document.getBean();
	        if (simulacaoAlcadaDto.getIdCentroResultado() == null || simulacaoAlcadaDto.getIdTipoFluxo() == null || simulacaoAlcadaDto.getDataHoraSolicitacao() == null
	        		|| simulacaoAlcadaDto.getIdSolicitante() == null || simulacaoAlcadaDto.getFinalidade() == null)
	            return;
	        
	        if (simulacaoAlcadaDto.getValor() == null)
	        	simulacaoAlcadaDto.setValor(0.0);
	        if (simulacaoAlcadaDto.getValorOutrasAlcadas() == null)
	        	simulacaoAlcadaDto.setValorOutrasAlcadas(0.0);
	        if (simulacaoAlcadaDto.getValorMensal() == null)
	        	simulacaoAlcadaDto.setValorMensal(0.0);
	        
	        CentroResultadoService centroResultadoService = (CentroResultadoService) ServiceLocator.getInstance().getService(CentroResultadoService.class, WebUtil.getUsuarioSistema(request));
	        CentroResultadoDTO centroResultadoDto = new CentroResultadoDTO();
	        centroResultadoDto.setIdCentroResultado(simulacaoAlcadaDto.getIdCentroResultado());
	        centroResultadoDto = (CentroResultadoDTO) centroResultadoService.restore(centroResultadoDto);
	        
	        FluxoService fluxoService = (FluxoService) ServiceLocator.getInstance().getService(FluxoService.class, WebUtil.getUsuarioSistema(request));
	        FluxoDTO fluxoDto = fluxoService.findByTipoFluxo(simulacaoAlcadaDto.getIdTipoFluxo());
	        
	        Collection<AlcadaProcessoNegocioDTO> colAlcadas = AlcadaProcessoNegocio.getInstance().getSimulacaoAlcada(simulacaoAlcadaDto, centroResultadoDto, fluxoDto);
	        if (colAlcadas != null) {
	        	for (AlcadaProcessoNegocioDTO alcadaProcessoNegocioDto : colAlcadas) {
					str += "<tr>";
					str += "	<td>";
					str += alcadaProcessoNegocioDto.getEmpregadoDto().getNome();
					str += "	</td>";
					str += "	<td>";
					str += alcadaProcessoNegocioDto.getProcessosNegocio().get(0).getNivelAutoridadeDto().getNomeNivelAutoridade();
					str += "	</td>";
					str += "	<td>";
					if (!alcadaProcessoNegocioDto.isDelegacao()) {
						str += "Não";
					}else{
						str += "<font color='red'>Sim</font>";
					}
					str += "	</td>";
					str += "	<td>";
					if (!alcadaProcessoNegocioDto.isAlcadaRejeitada()) {
						str += "Não";
					}else{
						str += "<font color='red'>Sim</font>";
					}
					str += "	</td>";
					String motivo = "&nbsp;";
					if (alcadaProcessoNegocioDto.getMotivoRejeicao() != null) {
						motivo = alcadaProcessoNegocioDto.getMotivoRejeicao().getDescricao();
						if (alcadaProcessoNegocioDto.getComplementoRejeicao() != null)
							motivo += " - "+alcadaProcessoNegocioDto.getComplementoRejeicao();
					}
					str += "	<td><font color='red'>"+motivo+"</font></td>";
				}
	        }
		}finally{
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			document.getElementById("divResponsaveis").setInnerHTML(str);
			document.getElementById("divDelegacoes").setInnerHTML("");
		}
	}

}