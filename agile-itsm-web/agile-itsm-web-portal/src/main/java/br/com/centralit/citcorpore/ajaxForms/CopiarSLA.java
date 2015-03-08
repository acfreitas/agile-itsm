package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.AcordoNivelServicoDTO;
import br.com.centralit.citcorpore.bean.InformacoesContratoDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.negocio.AcordoNivelServicoService;
import br.com.centralit.citcorpore.negocio.ServicoContratoService;
import br.com.centralit.citcorpore.negocio.ServicoService;
import br.com.citframework.comparacao.ObjectSimpleComparator;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;

public class CopiarSLA extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		InformacoesContratoDTO informacoesContratoDTO = (InformacoesContratoDTO) document.getBean();
		ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
		ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
		AcordoNivelServicoService acordoNivelServicoService = (AcordoNivelServicoService) ServiceLocator.getInstance().getService(AcordoNivelServicoService.class, null);
		Collection colServicosContrato = servicoContratoService.findByIdContrato(informacoesContratoDTO.getIdContrato());
		String strBuffer = "<table>";
		List colFinal = new ArrayList();

		// Busca de serviços que já possuem o SLA copiado
		List colServicosContratoSLAJaCopiados = acordoNivelServicoService.buscaServicosComContrato(informacoesContratoDTO.getTituloSLA());

		if (colServicosContrato != null && colServicosContratoSLAJaCopiados != null) {
			for (Iterator it = colServicosContrato.iterator(); it.hasNext();) {
				ServicoContratoDTO servicoContratoDTO = (ServicoContratoDTO) it.next();
				if (informacoesContratoDTO.getIdServicoContrato().intValue() != servicoContratoDTO.getIdServicoContrato().intValue()) {
					ServicoDTO servicoDto = new ServicoDTO();
					servicoDto.setIdServico(servicoContratoDTO.getIdServico());
					servicoDto = (ServicoDTO) servicoService.restore(servicoDto);
					if (servicoDto != null) {
						servicoContratoDTO.setNomeServico(servicoDto.getNomeServico());
						if (servicoContratoDTO.getDeleted() == null || !servicoContratoDTO.getDeleted().equalsIgnoreCase("Y")) {
							if (servicoDto.getDeleted() == null || !servicoDto.getDeleted().equalsIgnoreCase("Y")) {
								colFinal.add(servicoContratoDTO);
							}
						}
					}
				}
			}
		}

		// Retira os serviços que já possuem o SLA copiado para evitar duplicações
		List listFinal = new ArrayList();
		listFinal = removerServicosJaCopiados(colFinal, colServicosContratoSLAJaCopiados);

		if (listFinal.isEmpty()) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.naoHaServicosDisponiveis"));
			// document.executeScript("parent.limparAreaInformacao()");
			document.executeScript("parent.fecharVisao()");
			return;
		}

		Collections.sort(listFinal, new ObjectSimpleComparator("getNomeServico", ObjectSimpleComparator.ASC));

		if (listFinal != null) {
			for (Iterator it = colFinal.iterator(); it.hasNext();) {
				ServicoContratoDTO servicoContratoDTO = (ServicoContratoDTO) it.next();
				if (informacoesContratoDTO.getIdServicoContrato().intValue() != servicoContratoDTO.getIdServicoContrato().intValue()) {
					strBuffer += "<tr>";
					strBuffer += "<td>";
					strBuffer += "<input type='checkbox' name='idServicoCopiarPara' id='idServicoCopiarPara' value='" + +servicoContratoDTO.getIdServicoContrato() + "'/>"
							+ servicoContratoDTO.getNomeServico();
					strBuffer += "</td>";
					strBuffer += "</tr>";
				}
			}
		}

		strBuffer += "<table>";
		document.getElementById("copiarPara").setInnerHTML(strBuffer);

		AcordoNivelServicoDTO acordoNivelServicoDTO = new AcordoNivelServicoDTO();
		acordoNivelServicoDTO.setIdAcordoNivelServico(informacoesContratoDTO.getIdAcordoNivelServico());
		acordoNivelServicoDTO = (AcordoNivelServicoDTO) acordoNivelServicoService.restore(acordoNivelServicoDTO);
		strBuffer = "";
		if (acordoNivelServicoDTO != null) {
			strBuffer = UtilI18N.internacionaliza(request, "sla.titulo")+": " + acordoNivelServicoDTO.getTituloSLA() + "<br><br>";
			strBuffer += UtilI18N.internacionaliza(request, "solicitacaoServico.descricao")+": " + acordoNivelServicoDTO.getDescricaoSLA();
		}
		document.getElementById("slaCopiar").setInnerHTML(strBuffer);

		document.getForm("form").setValues(informacoesContratoDTO);
	}

	/**
	 * Método para remover serviços com SLA já copiados
	 * 
	 * @param colFinal
	 * @param colServicosContratoSLAJaCopiados
	 * @return List
	 */
	private List removerServicosJaCopiados(List<ServicoContratoDTO> colFinal, List<ServicoContratoDTO> colServicosContratoSLAJaCopiados) {

		for (int i = 0; i < colFinal.size(); i++) {
			for (int j = 0; j < colServicosContratoSLAJaCopiados.size(); j++) {
				if (!colFinal.isEmpty() && colFinal.get(i).getIdServicoContrato().equals(colServicosContratoSLAJaCopiados.get(j).getIdServicoContrato())) {
					colFinal.remove(i);
					// break;
				}
			}
		}

		return colFinal;
	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		InformacoesContratoDTO informacoesContratoDTO = (InformacoesContratoDTO) document.getBean();
		if (informacoesContratoDTO.getIdServicoCopiarPara() == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.informeServicoParaOndeCopiadoSLA"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}
		AcordoNivelServicoService acordoNivelServicoService = (AcordoNivelServicoService) ServiceLocator.getInstance().getService(AcordoNivelServicoService.class, null);
		acordoNivelServicoService.copiarSLA(informacoesContratoDTO.getIdAcordoNivelServico(), informacoesContratoDTO.getIdServicoContrato(), informacoesContratoDTO.getIdServicoCopiarPara());
		document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.acordoCopiadoSucesso"));
		document.executeScript("parent.limparAreaInformacao()");
		document.executeScript("parent.fecharVisao()");
	}

	@Override
	public Class getBeanClass() {
		return InformacoesContratoDTO.class;
	}

}
