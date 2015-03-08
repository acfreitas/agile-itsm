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
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.bean.TipoDemandaServicoDTO;
import br.com.centralit.citcorpore.negocio.AcordoNivelServicoService;
import br.com.centralit.citcorpore.negocio.AcordoServicoContratoService;
import br.com.centralit.citcorpore.negocio.ServicoContratoService;
import br.com.centralit.citcorpore.negocio.ServicoService;
import br.com.centralit.citcorpore.negocio.TipoDemandaServicoService;
import br.com.centralit.citgerencial.generateservices.incidentes.ControleGenerateSLAPorServicoContrato;
import br.com.citframework.comparacao.ObjectSimpleComparator;
import br.com.citframework.service.ServiceLocator;

public class VisualizarDesempenhoServicosContrato extends AjaxFormAction {

	@Override
	public Class getBeanClass() {
		return ServicoContratoDTO.class;
	}

	@Override
	public void load(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ServicoContratoDTO servicoContratoDTO = (ServicoContratoDTO)document.getBean();
		ServicoContratoService serviceContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
		ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
		TipoDemandaServicoService tipoDemandaServicoService = (TipoDemandaServicoService) ServiceLocator.getInstance().getService(TipoDemandaServicoService.class, null);
		AcordoNivelServicoService acordoNivelServicoService = (AcordoNivelServicoService) ServiceLocator.getInstance().getService(AcordoNivelServicoService.class, null);
		AcordoServicoContratoService acordoServicoContratoService = (AcordoServicoContratoService) ServiceLocator.getInstance().getService(AcordoServicoContratoService.class, null);
		Collection colServicosContrato = serviceContratoService.findByIdContrato(servicoContratoDTO.getIdContrato());
		List colFinal = new ArrayList();
		
		ControleGenerateSLAPorServicoContrato controleGenerateSLAPorServicoContrato = new ControleGenerateSLAPorServicoContrato();
		if (colServicosContrato != null){
			for(Iterator it = colServicosContrato.iterator(); it.hasNext();){
				ServicoContratoDTO servicoContratoAux = (ServicoContratoDTO)it.next();
				if (servicoContratoAux.getDeleted() != null && !servicoContratoAux.getDeleted().equalsIgnoreCase("N")){
				    continue;
				}
				if (servicoContratoAux.getIdServico() != null){
					ServicoDTO servicoDto = new ServicoDTO();
					servicoDto.setIdServico(servicoContratoAux.getIdServico());
					servicoDto = (ServicoDTO) servicoService.restore(servicoDto);
					if (servicoDto != null){
						if (servicoDto.getDeleted() != null && !servicoDto.getDeleted().equalsIgnoreCase("N")){
						    continue;
						}		
						servicoContratoAux.setTemSLA(false);
						servicoContratoAux.setNomeServico(servicoDto.getNomeServico());
						servicoContratoAux.setServicoDto(servicoDto);
						servicoContratoAux.setSituacaoServico(servicoDto.getIdSituacaoServico());
						if (servicoDto.getIdTipoDemandaServico() != null){
						    TipoDemandaServicoDTO tipoDemandaServicoDto = new TipoDemandaServicoDTO();
						    tipoDemandaServicoDto.setIdTipoDemandaServico(servicoDto.getIdTipoDemandaServico());
						    tipoDemandaServicoDto = (TipoDemandaServicoDTO) tipoDemandaServicoService.restore(tipoDemandaServicoDto);
						    if (tipoDemandaServicoDto != null){
						    	servicoContratoAux.setNomeTipoDemandaServico(tipoDemandaServicoDto.getNomeTipoDemandaServico());
						    }
						}
						Collection col = acordoNivelServicoService.findByIdServicoContrato(servicoContratoAux.getIdServicoContrato());
						Collection colVincs = acordoServicoContratoService.findByIdServicoContrato(servicoContratoAux.getIdServicoContrato());		
						if ((col != null && col.size() > 0) || (colVincs != null && colVincs.size() > 0)){
							servicoContratoAux.setTemSLA(true);	
						}
						List lst = controleGenerateSLAPorServicoContrato.execute(servicoContratoAux.getIdServicoContrato());
						double qtdeDentroPrazo = 0;
						double qtdeForaPrazo = 0;
						if (lst != null && lst.size() > 0){
							for (Iterator itSLA = lst.iterator(); itSLA.hasNext();){
								Object[] objs = (Object[]) itSLA.next();
								if (((String)objs[0]).indexOf("Fora") > -1 || ((String)objs[0]).indexOf("Out") > -1){
									qtdeForaPrazo = (Double)objs[2];
								}else{
									qtdeDentroPrazo = (Double)objs[2];
								}
							}
						}
						double qtdeDentroPrazoPerc = qtdeDentroPrazo / (qtdeDentroPrazo + qtdeForaPrazo);
						double qtdeForaPrazoPerc = qtdeForaPrazo / (qtdeDentroPrazo + qtdeForaPrazo);
						servicoContratoAux.setDentroPrazo((qtdeDentroPrazoPerc * 100));
						servicoContratoAux.setForaPrazo((qtdeForaPrazoPerc * 100));
						
						servicoContratoAux.setQtdeDentroPrazo((int)qtdeDentroPrazo);
						servicoContratoAux.setQtdeForaPrazo((int)qtdeForaPrazo);
						colFinal.add(servicoContratoAux);
					}
				}
			}
		}
		Collections.sort(colFinal, new ObjectSimpleComparator("getNomeServico", ObjectSimpleComparator.ASC));
		request.setAttribute("listaServicos", colFinal);
	}

}
