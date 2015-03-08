package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.ImagemItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.ImagemItemConfiguracaoRelacaoDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.MapaDesenhoServicoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.negocio.ImagemItemConfiguracaoRelacaoService;
import br.com.centralit.citcorpore.negocio.ImagemItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.ItemConfiguracaoService;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;
import br.com.citframework.util.WebUtil;

@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
public class MapaDesenhoServico extends AjaxFormAction {

	private String idServico;
	private String selecaoIc;

	ArrayList<ServicoDTO> listaServicos;
	private ArrayList<Integer> itensDeletadosInesperadamente;

	@Override
	public Class getBeanClass() {
		return MapaDesenhoServicoDTO.class;
	}

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		if (isIdServicoNoParametro(request)) {
			MapaDesenhoServicoDTO mapa = (MapaDesenhoServicoDTO) document.getBean();
			mapa.setIdServico(Integer.parseInt(idServico));
			selecionarServico(document, request, response);
		}
	}

	/**
	 * Verifica se o idServico foi passado como parâmetro de URL e inicializa a página com o serviço carregado.
	 * 
	 * @param request
	 * @return
	 */
	private boolean isIdServicoNoParametro(HttpServletRequest request) {
		idServico = request.getParameter("idServico");
		return idServico != null && !idServico.equals("") ? true : false;
	}

	public void selecionarServico(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		MapaDesenhoServicoDTO mapa = (MapaDesenhoServicoDTO) document.getBean();

		String imagensItemConfiguracaoSerializado = getImagensItemConfiguracaoSerializado(mapa.getIdServico(), request);
		imagensItemConfiguracaoSerializado = StringEscapeUtils.escapeJavaScript(imagensItemConfiguracaoSerializado);
		mapa.setListaItensConfiguracaoSerializada(imagensItemConfiguracaoSerializado);

		itensDeletadosInesperadamente = new ArrayList<Integer>();
		if (this.itensDeletadosInesperadamente.size() > 0) {
			document.alert(UtilI18N.internacionaliza(request, "mapaDesenhoServico.algunsItensForamDeletadosPorOutrasFontes") + itensDeletadosInesperadamente.toString());
		}
		HTMLForm form = document.getForm("form");
		form.setValues(mapa);

		document.executeScript("atualizaServicoMapa('"+imagensItemConfiguracaoSerializado+"');");
	}

	/**
	 * @param idServico
	 * @param request 
	 * @return
	 * @throws Exception
	 */
	private String getImagensItemConfiguracaoSerializado(int idServico, HttpServletRequest request) throws Exception {

		ImagemItemConfiguracaoService imagemItemConfiguracaoService = (ImagemItemConfiguracaoService) ServiceLocator.getInstance().getService(ImagemItemConfiguracaoService.class, null);
		
		ImagemItemConfiguracaoRelacaoService imagemItemConfiguracaoRelacaoService = (ImagemItemConfiguracaoRelacaoService) ServiceLocator.getInstance().getService(ImagemItemConfiguracaoRelacaoService.class, null);

		ArrayList<ImagemItemConfiguracaoDTO> imagensItemConfiguracao = (ArrayList) imagemItemConfiguracaoService.findByIdServico(idServico);

		String serializado = null;

		if (imagensItemConfiguracao != null) {

			for (ImagemItemConfiguracaoDTO img : imagensItemConfiguracao) {

				ItemConfiguracaoService itemService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);

				ItemConfiguracaoDTO item = new ItemConfiguracaoDTO();
				item.setIdItemConfiguracao(img.getIdItemConfiguracao());
				item = (ItemConfiguracaoDTO) itemService.restore(item);

				if (item == null) {
					itensDeletadosInesperadamente.add(img.getIdItemConfiguracao());
					imagemItemConfiguracaoService.delete(img);
				} else {
					img.setIdentificacao(item.getIdentificacao());
				}
				
				if(img.getIdImagemItemConfiguracao()!= null){
					Collection<ImagemItemConfiguracaoRelacaoDTO> colIdImagemItemConfiguracaoPai = new ArrayList<ImagemItemConfiguracaoRelacaoDTO>();
					Collection<ImagemItemConfiguracaoRelacaoDTO> imgItemConfiguracaoRel = null;
					imgItemConfiguracaoRel = imagemItemConfiguracaoRelacaoService.findByIdImagemItemConfiguracao(img.getIdImagemItemConfiguracao());
					
					//Trata itens que já existiam itens pai
					if(img.getIdImagemItemConfiguracaoPai()!= null){
						ImagemItemConfiguracaoRelacaoDTO imgItemPaiAux = null;
						imgItemPaiAux = imagemItemConfiguracaoRelacaoService.findByIdImagemItemConfiguracaoAndIdItemPai(img.getIdImagemItemConfiguracao(), img.getIdImagemItemConfiguracaoPai());
						if(imgItemPaiAux == null){
							imgItemPaiAux = new ImagemItemConfiguracaoRelacaoDTO();
							imgItemPaiAux.setIdImagemItemConfiguracaoPai(img.getIdImagemItemConfiguracaoPai());
							imgItemPaiAux.setIdImagemItemConfiguracao(img.getIdImagemItemConfiguracao());
							colIdImagemItemConfiguracaoPai.add(imgItemPaiAux);
						}
					}
					//Adiciona na lista de itens pais.
					if(imgItemConfiguracaoRel!= null && !imgItemConfiguracaoRel.isEmpty()){
						for(ImagemItemConfiguracaoRelacaoDTO imgRel:imgItemConfiguracaoRel){
							if(imgRel.getIdImagemItemConfiguracaoPai()!= null){
								colIdImagemItemConfiguracaoPai.add(imgRel);
							}
						}
					}
					if(colIdImagemItemConfiguracaoPai!= null && !colIdImagemItemConfiguracaoPai.isEmpty()){
						String colSerializado = WebUtil.serializeObjects(colIdImagemItemConfiguracaoPai, WebUtil.getLanguage(request));
						img.setIdImagemItemConfiguracaoPaiColSerializado(colSerializado);
					}
				}
				
				

				// System.out.println("id imagem: " +
				// img.getIdImagemItemConfiguracao());
				// System.out.println("id item: " +
				// img.getIdItemConfiguracao());
				// System.out.println("id serviço: " + img.getIdServico());
				// System.out.println("posx: " + img.getPosx());
				// System.out.println("posy: " + img.getPosy());
				// System.out.println("descricao: " + img.getDescricao());
				// System.out.println("----------------------------------------");  WebUtil.serializeObjects(
			}
		}
		serializado = WebUtil.serializeObjects(imagensItemConfiguracao, WebUtil.getLanguage(request));

		return serializado;
	}

	/**
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void selecionarItemConfigurcao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		MapaDesenhoServicoDTO mapa = (MapaDesenhoServicoDTO) document.getBean();
		ItemConfiguracaoService itemService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
		ItemConfiguracaoDTO item = new ItemConfiguracaoDTO();
		item.setIdItemConfiguracao(mapa.getIdItemConfiguracao());
		item = (ItemConfiguracaoDTO) itemService.restore(item);
		mapa.setIdentificacao(item.getIdentificacao());
		mapa.setIdItemConfiguracao(item.getIdItemConfiguracao());
		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(mapa);
	}

	public void salvarServico(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<ImagemItemConfiguracaoDTO> listaItens = (ArrayList) WebUtil.deserializeCollectionFromRequest(ImagemItemConfiguracaoDTO.class, "listaItensConfiguracaoSerializada", request);

		ImagemItemConfiguracaoService imagemItemService = (ImagemItemConfiguracaoService) ServiceLocator.getInstance().getService(ImagemItemConfiguracaoService.class, null);
		
		ImagemItemConfiguracaoRelacaoService imagemItemConfiguracaoRelacaoServico = (ImagemItemConfiguracaoRelacaoService) ServiceLocator.getInstance().getService(ImagemItemConfiguracaoRelacaoService.class, null);
		if (listaItens != null) {
			for (ImagemItemConfiguracaoDTO img : listaItens) {
				// System.out.println("id imagem: " +
				// img.getIdImagemItemConfiguracao());
				// System.out.println("id item: " +
				// img.getIdItemConfiguracao());
				// System.out.println("id serviço: " + img.getIdServico());
				// System.out.println("posx: " + img.getPosx());
				// System.out.println("posy: " + img.getPosy());
				// System.out.println("descricao: " + img.getDescricao());
				// System.out.println("----------------------------------------");

				if (img.getIdImagemItemConfiguracao() == 0) {
					imagemItemService.create(img);
				} else {
					imagemItemService.update(img);
				}
				//Salvar itens na tabela de relação
				if(img.getIdImagemItemConfiguracaoPaiColSerializado()!= null && UtilStrings.isNotVazio(img.getIdImagemItemConfiguracaoPaiColSerializado())){
					Collection<ImagemItemConfiguracaoRelacaoDTO> listaItensItemRel = (ArrayList) WebUtil.deserializeCollectionFromString(ImagemItemConfiguracaoRelacaoDTO.class, img.getIdImagemItemConfiguracaoPaiColSerializado());
					for(ImagemItemConfiguracaoRelacaoDTO imgRel : listaItensItemRel){
						if(imgRel!= null && imgRel.getIdImagemItemConfiguracao()!= null && imgRel.getIdImagemItemConfiguracaoPai()!= null){
							ImagemItemConfiguracaoRelacaoDTO imgAux = imagemItemConfiguracaoRelacaoServico.findByIdImagemItemConfiguracaoAndIdItemPai(imgRel.getIdImagemItemConfiguracao(), imgRel.getIdImagemItemConfiguracaoPai());
							if(imgAux == null){
								imagemItemConfiguracaoRelacaoServico.create(imgRel);
							}
						}
					}
				}

			}
		}
		selecionarServico(document, request, response);
		document.executeScript("UtilMapa.mostrarMsgTemporaria('msg', 4," + UtilI18N.internacionaliza(request, "MSG06") + " );");

	}

	public void excluirImagemItemConfiguracao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		MapaDesenhoServicoDTO mapa = (MapaDesenhoServicoDTO) document.getBean();
		ImagemItemConfiguracaoService imagemItemService = (ImagemItemConfiguracaoService) ServiceLocator.getInstance().getService(ImagemItemConfiguracaoService.class, null);
		ImagemItemConfiguracaoDTO img = new ImagemItemConfiguracaoDTO();
		img.setIdImagemItemConfiguracao(mapa.getIdImagemItemConfiguracao());
		imagemItemService.delete(img);
		mapa.setListaItensConfiguracaoSerializada(getImagensItemConfiguracaoSerializado(mapa.getIdServico(), request));
		HTMLForm form = document.getForm("form");
		form.setValues(mapa);

		document.executeScript("atualizaServicoMapa(); " + "UtilMapa.mostrarMsgTemporaria('msg', 4, " + UtilI18N.internacionaliza(request, "MSG07") + ");");
	}

	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

	}

}
