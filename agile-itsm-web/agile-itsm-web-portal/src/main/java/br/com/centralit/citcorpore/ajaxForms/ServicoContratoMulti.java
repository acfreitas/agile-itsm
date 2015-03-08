package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.FluxoServicoDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.integracao.ServicoDao;
import br.com.centralit.citcorpore.negocio.ServicoContratoService;
import br.com.centralit.citcorpore.negocio.ServicoService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;

/**
 * 
 * @author Cledson.junior
 * 
 */
@SuppressWarnings({ "unused", "unchecked" })
public class ServicoContratoMulti extends ServicoContrato {

	/**
	 * Inclui registro.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServicoContratoDTO servicoContratoDTO = (ServicoContratoDTO) document.getBean();
		
		ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, WebUtil.getUsuarioSistema(request));
		
		List<FluxoServicoDTO> listFluxoServicoDTO = (ArrayList<FluxoServicoDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(FluxoServicoDTO.class, "fluxosSerializados", request);
		List<ServicoDTO> listServicoDto = (ArrayList<ServicoDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(ServicoDTO.class, "servicosSerializados", request);

		if (listServicoDto == null || listServicoDto.isEmpty()) {
			document.alert(UtilI18N.internacionaliza(request, "servicoContrato.selecioneServicos"));
			document.executeScript("JANELA_AGUARDE_MENU.hide()");
			return;
		}

		servicoContratoDTO.setListaFluxo((listFluxoServicoDTO == null ? new ArrayList<FluxoServicoDTO>() : listFluxoServicoDTO));
		servicoContratoDTO.setListaServico((listServicoDto == null ? new ArrayList<ServicoDTO>() : listServicoDto));
		
		try {
			if (servicoContratoDTO.getIdServicoContrato() == null || servicoContratoDTO.getIdServicoContrato().intValue() == 0) {
				for (ServicoDTO servicoDto : servicoContratoDTO.getListaServico()) {
					servicoContratoDTO.setIdServico(servicoDto.getIdServico());
					servicoContratoDTO = (ServicoContratoDTO) servicoContratoService.create(servicoContratoDTO);
				}
			}
			
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		document.executeScript("closePopup();");
		document.executeScript("JANELA_AGUARDE_MENU.show()");
	}

	/**
	 * Restaura a lista de todos os serviços para a tela de Adicionar Vários Serviços ao Contrato.
	 * alteração: de servicos ativos que ainda não foram adicionados a este contrato
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author valdoilo.damasceno
	 * @author thays.araujo
	 * 
	 */
	public void adicionarTodosOsServicos(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ServicoContratoDTO servicoContratoDto = (ServicoContratoDTO) document.getBean();
		
		ServicoDTO servicoDto = new ServicoDTO();
		
		ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
		
		Collection<ServicoDTO> listAtivosDiferenteContrato = null;
		
		if(servicoContratoDto.getIdContrato()!=null){
			servicoDto.setIdContrato(servicoContratoDto.getIdContrato());
			 listAtivosDiferenteContrato = servicoService.listAtivosDiferenteContrato(servicoDto);
		}
		

		if (listAtivosDiferenteContrato != null && !listAtivosDiferenteContrato.isEmpty()) {

			for (ServicoDTO servicoDTO : listAtivosDiferenteContrato) {

				document.executeScript("addLinhaTabelaServico(" + servicoDTO.getIdServico() + ",'" + servicoDTO.getNomeServico() + "')");
			}
			
		}else{
			
		}
		
		document.executeScript("JANELA_AGUARDE_MENU.hide()");

	}
	
	/**
	 * Metodo para adicionar na grid os servicos checkados.
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thays.araujo
	 */
	public void adicionaTabelaLOOKUP_SERVICOATIVOS_DIFERENTE_CONTRATO(DocumentHTML document, HttpServletRequest request, HttpServletResponse response)throws Exception{
		
		ServicoContratoDTO servicoContratoDto = (ServicoContratoDTO) document.getBean();
		ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, WebUtil.getUsuarioSistema(request));
		ServicoDao servicoDao = new ServicoDao();
		
		servicoContratoDto.setColAllLOOKUP_SERVICOATIVOS_DIFERENTE_CONTRATO(servicoContratoDto.getColAllLOOKUP_SERVICOATIVOS_DIFERENTE_CONTRATO());
		servicoContratoDto.setColServicosCheckado(servicoContratoDto.getColAllLOOKUP_SERVICOATIVOS_DIFERENTE_CONTRATO());
		//Declarando arrays de strings
		ArrayList<String> listaValoresGrid = new ArrayList<String>();
		ArrayList<String> listaValoresCheckados = new ArrayList<String>();
		
		List<ServicoDTO> listServicoDto = (ArrayList<ServicoDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(ServicoDTO.class, "servicosSerializados", request);
		
		if (listServicoDto != null){
			for (ServicoDTO servicoDto : listServicoDto){
				listaValoresGrid.add(servicoDto.getIdServico().toString());
			}
		}
		if(servicoContratoDto.getColServicosCheckado()!=null ){
			for (String servicoCheckado : servicoContratoDto.getColServicosCheckado()){
				listaValoresCheckados.add(servicoCheckado);
			}
		}
		
		
		//Neste trecho do codigo adiciona a uma Collection os valores que NAO estão se repetindo entre os dois Arrays
		Set<String> valoresUnicosGrid = new HashSet<String>();
		valoresUnicosGrid.addAll(listaValoresGrid);
		valoresUnicosGrid.addAll(listaValoresCheckados);
		
		//chamando pelo DAO o objeto referente aos ids checkados e setando na collection problemas retorno
		Set<ServicoDTO> servicoRetorno = new HashSet<ServicoDTO>();
		ServicoDTO servicoDTO = new ServicoDTO();
		document.executeScript("limparTabelaServicos();");
		for (String idServicoCheckado : valoresUnicosGrid){
			if (idServicoCheckado != null && !idServicoCheckado.equals("")){
				Integer idServicoCheckados = Integer.parseInt(idServicoCheckado);
				servicoDTO.setIdServico(Integer.parseInt(idServicoCheckado));
				servicoDTO = (ServicoDTO) servicoDao.restore(servicoDTO);
				document.executeScript("addLinhaTabelaServico(" + servicoDTO.getIdServico() + ",'" + servicoDTO.getNomeServico() + "')");
			}else {
				continue;
			}
		}
	}

	/**
	 * Restaura os dados ao clicar em um registro.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */

	public Class<ServicoContratoDTO> getBeanClass() {
		return ServicoContratoDTO.class;
	}

}
