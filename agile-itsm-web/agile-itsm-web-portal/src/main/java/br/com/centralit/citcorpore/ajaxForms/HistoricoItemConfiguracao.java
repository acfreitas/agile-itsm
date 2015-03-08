package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.HistoricoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.HistoricoValorDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoTreeDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.bean.ValorDTO;
import br.com.centralit.citcorpore.negocio.HistoricoItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.HistoricoValorService;
import br.com.centralit.citcorpore.negocio.ItemConfiguracaoService;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Reflexao;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.WebUtil;

public class HistoricoItemConfiguracao extends ItemConfiguracaoTree {

	@SuppressWarnings("unchecked")
	public void saveBaseline(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usrDto = (UsuarioDTO) br.com.centralit.citcorpore.util.WebUtil.getUsuario(request);
    	if(usrDto == null){
    		return;
    	}
		HistoricoItemConfiguracaoDTO histItem = (HistoricoItemConfiguracaoDTO) document.getBean();
		HistoricoItemConfiguracaoService service = (HistoricoItemConfiguracaoService) ServiceLocator.getInstance().getService(HistoricoItemConfiguracaoService.class, null);
		List<HistoricoItemConfiguracaoDTO> set = (ArrayList<HistoricoItemConfiguracaoDTO>) WebUtil.deserializeCollectionFromRequest(HistoricoItemConfiguracaoDTO.class, "baselinesSerializadas", request);
		if(set!=null) {
			for (HistoricoItemConfiguracaoDTO historicoItemConfiguracaoDTO : set) {
				HistoricoItemConfiguracaoDTO novo = new HistoricoItemConfiguracaoDTO();
				novo.setBaseLine("SIM");
				if(historicoItemConfiguracaoDTO.getIdHistoricoIC()!=null) {
					novo.setIdHistoricoIC(historicoItemConfiguracaoDTO.getIdHistoricoIC());
					service.updateNotNull(novo);
				}
			}
			document.alert(UtilI18N.internacionaliza(request, "itemConfiguracaoTree.baselineGravadasSucesso"));
		}		
		
		if(histItem.getIdItemConfiguracao()!=null)
		{
			ItemConfiguracaoTreeDTO i = new ItemConfiguracaoTreeDTO();
			i.setIdItemConfiguracao(histItem.getIdItemConfiguracao());
			document.setBean(i);
			super.load(document, request, response);
		}
		/**
		 * Oculta o janela aguarde
		 * @author flavio.santana
		 * 25/10/2013
		 */
		document.executeScript("JANELA_AGUARDE_MENU.hide();");
	}

	/**
	 * Alterado por
	 * desenvolvedor: rcs (Rafael César Soyer)
	 * data: 12/01/2015
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void restaurarBaseline(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usrDto = (UsuarioDTO) br.com.centralit.citcorpore.util.WebUtil.getUsuario(request);
    	if(usrDto == null){
    		return;
    	}
		HistoricoItemConfiguracaoDTO histItem = (HistoricoItemConfiguracaoDTO) document.getBean();
		
		HistoricoItemConfiguracaoService serviceHistItem = (HistoricoItemConfiguracaoService) ServiceLocator.getInstance().getService(HistoricoItemConfiguracaoService.class, null);
		HistoricoValorService serviceHistValor = (HistoricoValorService) ServiceLocator.getInstance().getService(HistoricoValorService.class, null);
		ItemConfiguracaoService serviceItem = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
		
		histItem = (HistoricoItemConfiguracaoDTO) serviceHistItem.restore(histItem);
			
		List<HistoricoValorDTO> listValoresDtos = serviceHistValor.listHistoricoValorByIdHistoricoIc(histItem.getIdHistoricoIC());

		/*Realizando a Reflexão de Item de Configuração*/
		ItemConfiguracaoDTO item = new ItemConfiguracaoDTO();
		Reflexao.copyPropertyValues(histItem, item);
		
		List<ValorDTO> listValorDto = new ArrayList<ValorDTO>();
		
		for (HistoricoValorDTO historicoValorDTO : listValoresDtos) {
			ValorDTO v = new ValorDTO();
				Reflexao.copyPropertyValues(historicoValorDTO, v);
				v.getIdValor();
				listValorDto.add(v);
		}
		
		item.setValores(listValorDto);
		serviceItem.restaurarBaseline(item, usrDto);
		
		ItemConfiguracaoTreeDTO i = new ItemConfiguracaoTreeDTO();
		i.setIdItemConfiguracao(histItem.getIdItemConfiguracao());
		document.setBean(i);
		super.load(document, request, response);
		document.alert(UtilI18N.internacionaliza(request, "itemConfiguracaoTree.restauraHistoricoSuceso"));
	        document.executeScript("JANELA_AGUARDE_MENU.hide();");
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Class getBeanClass() {
		return HistoricoItemConfiguracaoDTO.class;
		
	}
	

}