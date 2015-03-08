package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLElement;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.GrupoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.InformacaoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.bean.TipoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.bean.ValorDTO;
import br.com.centralit.citcorpore.negocio.GrupoItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.ImagemItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.InformacaoItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.ItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.ValorService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

/**
 * @author rosana.godinho
 * 
 */
@SuppressWarnings({ "unchecked", "rawtypes" ,"unused"})
public class InformacaoItemConfiguracao extends AjaxFormAction {

    private InformacaoItemConfiguracaoDTO informacaoItemConfiguracao;

    private TipoItemConfiguracaoDTO tipoItemConfiguracao = new TipoItemConfiguracaoDTO();

    private ItemConfiguracaoDTO itemConfiguracao = new ItemConfiguracaoDTO();

    @Override
    public Class getBeanClass() {
	return InformacaoItemConfiguracaoDTO.class;
    }

    @Override
    public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	this.informacao(document, request, response);
    }

	public void informacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		this.setInformacaoItemConfiguracao((InformacaoItemConfiguracaoDTO) document.getBean());
	this.getItemConfiguracao().setIdItemConfiguracao(Integer.parseInt(request.getParameter("id")));
	this.setItemConfiguracao((ItemConfiguracaoDTO) this.getItemConfiguracaoService().restore(this.getItemConfiguracao()));
	this.setInformacaoItemConfiguracao(this.getInformacaoItemConfiguracaoService().listByInformacao(this.getItemConfiguracao()));

	this.getInformacaoItemConfiguracao().setIdentificacaoItemConfiguracao(this.getItemConfiguracao().getIdentificacao());

	GrupoItemConfiguracaoService grupoItemConfiguracaoService = (GrupoItemConfiguracaoService) ServiceLocator.getInstance().getService(GrupoItemConfiguracaoService.class, null);
	GrupoItemConfiguracaoDTO grupoItemConfiguracaoDTO = new GrupoItemConfiguracaoDTO();
	if(this.getItemConfiguracao().getIdGrupoItemConfiguracao() != null){
		grupoItemConfiguracaoDTO.setIdGrupoItemConfiguracao(this.getItemConfiguracao().getIdGrupoItemConfiguracao());
		grupoItemConfiguracaoDTO = (GrupoItemConfiguracaoDTO) grupoItemConfiguracaoService.restore(grupoItemConfiguracaoDTO);			
		this.getInformacaoItemConfiguracao().setNomeGrupoItemConfiguracao(grupoItemConfiguracaoDTO.getNomeGrupoItemConfiguracao());
	}else{
		if (!ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.NOME_GRUPO_ITEM_CONFIG_NOVOS, " ").trim().equalsIgnoreCase("")) 
			this.getInformacaoItemConfiguracao().setNomeGrupoItemConfiguracao(ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.NOME_GRUPO_ITEM_CONFIG_NOVOS, " "));
	}
	
	/* Desenvolvedor: Pedro Lino - Data: 23/10/2013 - Horário: 10:54 - ID Citsmart: 120948 - 
	 * Motivo/Comentário: Grupo estavaparecendo NULL/ Utilizado nullToVazio para aparesentar vazio na tela
	 * Obs.: Tratado tbm para identificação - Linha(s): 90 e 93 */
	StringBuilder subDiv = new StringBuilder();
	/* Cabeçalho */
	subDiv.append("<div id='cabecalhoInf'>");
	subDiv.append("<h2>"+UtilI18N.internacionaliza(request, "pesquisaItemConfiguracao.descricaoAtivosMaquina")+"</h2><hr />");
	subDiv.append("<label>");
	subDiv.append(""+UtilI18N.internacionaliza(request, "citcorpore.comum.identificacao")+": "+ UtilStrings.nullToVazio(this.getInformacaoItemConfiguracao().getIdentificacaoItemConfiguracao()));
	subDiv.append("</label>");
	subDiv.append("<label>");
	subDiv.append(""+UtilI18N.internacionaliza(request, "grupo.grupo")+": "+  UtilStrings.nullToVazio(this.getInformacaoItemConfiguracao().getNomeGrupoItemConfiguracao()));
	subDiv.append("</label>");
	subDiv.append("<hr />");
	subDiv.append("</div>");
	
	//subDiv.append("<div id='divImpactos'>");
	//subDiv.append("</div>");
	
	/* Treeview */
	subDiv.append("<div id='corpoInf'>");
	subDiv.append("<ul id='browser' class='filetree treeview'>");
	subDiv.append("<li class='closed' >");
	subDiv.append("<div class='hitarea closed-hitarea collapsable-hitarea'>");
	subDiv.append("</div>");
	subDiv.append("<span class='folder'>");
	subDiv.append("BIOS");
	subDiv.append("</span>");
	subDiv.append("<ul>");
	subDiv.append("<li class='closed'>");
	subDiv.append("<div class='hitarea closed-hitarea collapsable-hitarea'>");
	subDiv.append("</div>");
	subDiv.append("<span class='folder'>");
	subDiv.append(""+UtilI18N.internacionaliza(request, "pesquisaItemConfiguracao.dataUltimoIventario")+"");
	subDiv.append("</span>");
	subDiv.append("<ul>");
	subDiv.append("<li>");
	subDiv.append("<span class='file'>");
	subDiv.append(UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, this.getItemConfiguracao().getDataInicio(), WebUtil.getLanguage(request)));
	subDiv.append("</span>");
	subDiv.append("</li>");
	subDiv.append("</ul>");
	subDiv.append("</li>");
	subDiv.append("<li class='closed'>");
	subDiv.append("<div class='hitarea' onclick='restaurarValoresBios();'>");
	subDiv.append("</div>");
	subDiv.append("<span class='folder' onclick='restaurarValoresBios();'>");
	subDiv.append(""+UtilI18N.internacionaliza(request, "pesquisaItemConfiguracao.caracteristicas")+"");
	subDiv.append("</span>");
	subDiv.append("<ul id='subBios'>");
	// subDiv.append("<div id='subBios'></div>");
	// aqui sera incluso via InnerHTML quando o ator clicar em BIOS.
	subDiv.append("</ul>");
	subDiv.append("</li>");
	subDiv.append("</ul>");
	subDiv.append("</li>");
	subDiv.append("<li class='closed'>");
	subDiv.append("<div class='hitarea closed-hitarea collapsable-hitarea'>");
	subDiv.append("</div>");
	subDiv.append("<span class='folder' >");
	subDiv.append("Hardware");
	subDiv.append("</span>");
	subDiv.append("<ul>");
	subDiv.append("<li class='closed'>");
	subDiv.append("<div class='hitarea closed-hitarea collapsable-hitarea'>");
	subDiv.append("</div>");
	subDiv.append("<span class='folder'>");
	subDiv.append(""+UtilI18N.internacionaliza(request, "pesquisaItemConfiguracao.dataUltimoIventario")+"");
	subDiv.append("</span>");
	subDiv.append("<ul >");
	subDiv.append("<li>");
	subDiv.append("<span class='file'>");
	subDiv.append(UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, this.getItemConfiguracao().getDataInicio(), WebUtil.getLanguage(request)));
	subDiv.append("</span>");
	subDiv.append("</li>");
	subDiv.append("</ul>");
	subDiv.append("</li>");
	subDiv.append("<li class='closed' >");
	subDiv.append("<div class='hitarea closed-hitarea collapsable-hitarea' onclick='restaurarValoresHardware();'>");
	subDiv.append("</div>");
	subDiv.append("<span class='folder' onclick='restaurarValoresHardware();'>");
	subDiv.append(""+UtilI18N.internacionaliza(request, "pesquisaItemConfiguracao.caracteristicas")+"");
	subDiv.append("</span>");
	subDiv.append("<ul>");
	subDiv.append("<div id='subHardware'></div>");
	// aqui sera incluso via InnerHTML quando o ator clicar em HARDWARE.
	subDiv.append("</ul>");
	subDiv.append("</li>");
	subDiv.append("</ul>");
	subDiv.append("</li>");
	subDiv.append("<li class='closed'>");
	subDiv.append("<div class='hitarea closed-hitarea collapsable-hitarea'>");
	subDiv.append("</div>");
	subDiv.append("<span class='folder' name='bios' >");
	subDiv.append("Software");
	subDiv.append("</span>");
	subDiv.append("<ul>");
	subDiv.append("<li class='closed'>");
	subDiv.append("<div class='hitarea closed-hitarea collapsable-hitarea'>");
	subDiv.append("</div>");
	subDiv.append("<span class='folder'>");
	subDiv.append(""+UtilI18N.internacionaliza(request, "pesquisaItemConfiguracao.dataUltimoIventario")+"");
	subDiv.append("</span>");
	subDiv.append("<ul >");
	subDiv.append("<li>");
	subDiv.append("<span class='file'>");
	subDiv.append(UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, this.getItemConfiguracao().getDataInicio(), WebUtil.getLanguage(request)));
	subDiv.append("</span>");
	subDiv.append("</li>");
	subDiv.append("</ul>");
	subDiv.append("</li>");
	subDiv.append("<li  class='closed'>");
	subDiv.append("<div class='hitarea closed-hitarea collapsable-hitarea' onclick='restaurarValoresSoftware();'>");
	subDiv.append("</div>");
	subDiv.append("<span class='folder' onclick='restaurarValoresSoftware();'>");
	subDiv.append(""+UtilI18N.internacionaliza(request, "pesquisaItemConfiguracao.caracteristicas")+"");
	subDiv.append("</span>");
	subDiv.append("<ul>");
	subDiv.append("<div id='subSoftware'></div>");
	// aqui sera incluso via InnerHTML quando o ator clicar em SOFTWARE.
	subDiv.append("</ul>");
	subDiv.append("</li>");
	subDiv.append("</ul>");
	subDiv.append("</li>");
	subDiv.append("</ul>");
	subDiv.append("</div>");
	
	subDiv.append("<div id='divImpactos'></div>");
	
	HTMLElement divPrincipal = document.getElementById("principalInf");
	divPrincipal.setInnerHTML(subDiv.toString());
	
	if(request.getParameter("mostraItensVinculados") == null || request.getParameter("mostraItensVinculados").equals("true"))
		verificaImpactos(this.getInformacaoItemConfiguracao().getIdItemConfiguracao(), document, request, response);
	
	document.executeScript("tree('#browser'); $('#loading_overlay').hide();");
	HTMLForm form = CITCorporeUtil.limparFormulario(document);
	form.setValues(this.getInformacaoItemConfiguracao());
    }

    public void prepararHtmlBios(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
	this.setInformacaoItemConfiguracao((InformacaoItemConfiguracaoDTO) document.getBean());
	this.getItemConfiguracao().setIdItemConfiguracao(this.getInformacaoItemConfiguracao().getIdItemConfiguracao());

	StringBuilder htmlBios = new StringBuilder();
	//Setando dados de pesquisa
	TipoItemConfiguracaoDTO tipoItemConfiguracao = new TipoItemConfiguracaoDTO();
	tipoItemConfiguracao.setTag("Bios");
	tipoItemConfiguracao.setCategoria(3);
	
	for (ValorDTO valor : this.getListaCaracteristica(this.getItemConfiguracao(), tipoItemConfiguracao)) {
	    if (!valor.getValorStr().equalsIgnoreCase("")) {
		htmlBios.append("<li class='closed'>");
		htmlBios.append("<div class='hitarea'>");
		htmlBios.append("</div>");
		htmlBios.append("<span class='file'>");
		htmlBios.append(valor.getNomeCaracteristica() + " - " + valor.getValorStr());
		htmlBios.append("</span>");
		htmlBios.append("</li>");
	    }
	}
	HTMLElement divPrincipal = document.getElementById("subBios");
	divPrincipal.setInnerHTML(htmlBios.toString());
	document.executeScript("tree('#subBios'); $('#loading_overlay').hide();");
    }

    public void prepararHtmlHardware(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
	this.setInformacaoItemConfiguracao((InformacaoItemConfiguracaoDTO) document.getBean());
	this.getItemConfiguracao().setIdItemConfiguracao(this.getInformacaoItemConfiguracao().getIdItemConfiguracao());
	StringBuilder htmlHardware = new StringBuilder();
	String agrupAnterior = "";
	Integer idItemAnterior = -9999;
	 htmlHardware.append("<li class='closed'>");
	 
	//Setando dados de pesquisa
	TipoItemConfiguracaoDTO tipoItemConfiguracao = new TipoItemConfiguracaoDTO();
	tipoItemConfiguracao.setTag("HARDWARE");
	tipoItemConfiguracao.setCategoria(1);
		
	for (ValorDTO valor : this.getListaCaracteristica(this.getItemConfiguracao(), tipoItemConfiguracao)) {
	    if (!valor.getValorStr().equalsIgnoreCase("")) {
		if (!agrupAnterior.equalsIgnoreCase(valor.getTagtipoitemconfiguracao())){
		    if (!agrupAnterior.equalsIgnoreCase("")){
			htmlHardware.append("</ul></li>");
			htmlHardware.append("</ul></li>");
			idItemAnterior = -9999;
		    }
		    htmlHardware.append("<li class='closed'>");
		    htmlHardware.append("<span class='folder'>" + valor.getTagtipoitemconfiguracao() + "</span>");
		    htmlHardware.append("<ul>");
		}
    		if (idItemAnterior.intValue() != valor.getIdItemConfiguracao().intValue()){
    		    if (idItemAnterior.intValue() != -9999){
    			htmlHardware.append("</ul></li>");
    		    }
    		    htmlHardware.append("<li  class='closed'>");
    		    htmlHardware.append("<span class='folder'>" + valor.getTagtipoitemconfiguracao() + " - Id: " + valor.getIdItemConfiguracao() + "</span>");
    		    htmlHardware.append("<ul>");
    		}
		htmlHardware.append("<span class='file'>");
		htmlHardware.append(valor.getNomeCaracteristica() + " - " + valor.getValorStr());
		htmlHardware.append("</span>");
		agrupAnterior = valor.getTagtipoitemconfiguracao();
		idItemAnterior = valor.getIdItemConfiguracao();
	    }
	}
        if (!agrupAnterior.equalsIgnoreCase("")){
            htmlHardware.append("</ul></li>");
	    htmlHardware.append("</ul></li>");
	}
        htmlHardware.append("</li>");
	HTMLElement divPrincipal = document.getElementById("subHardware");
	divPrincipal.setInnerHTML(htmlHardware.toString());
	document.executeScript("tree('#subHardware'); $('#loading_overlay').hide();");
    }

    
    public void prepararHtmlSoftware(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
	this.setInformacaoItemConfiguracao((InformacaoItemConfiguracaoDTO) document.getBean());
	this.getItemConfiguracao().setIdItemConfiguracao(this.getInformacaoItemConfiguracao().getIdItemConfiguracao());
	StringBuilder htmlSoftware = new StringBuilder();
	htmlSoftware.append("<li class='closed'>");
	
	//Setando dados de pesquisa
	TipoItemConfiguracaoDTO tipoItemConfiguracao = new TipoItemConfiguracaoDTO();
	tipoItemConfiguracao.setTag("SOFTWARES");
	tipoItemConfiguracao.setCategoria(2);
		
	for (ValorDTO valor : this.getListaCaracteristica(this.getItemConfiguracao(), tipoItemConfiguracao)) {
	    if (!valor.getValorStr().equalsIgnoreCase("")) {
		if (valor.getTag().equalsIgnoreCase("NAME")) {
		    htmlSoftware.append("</ul>" );
		    htmlSoftware.append("</li>" );
		    htmlSoftware.append("<li  class='closed'>");
		    htmlSoftware.append("<span  class='folder'>");
		    htmlSoftware.append(valor.getValorStr());
		    htmlSoftware.append("</span>");
		    htmlSoftware.append("<ul>");
		}
		htmlSoftware.append("<span class='file'>");
		htmlSoftware.append(valor.getNomeCaracteristica() + " - " + valor.getValorStr());
		htmlSoftware.append("</span>");
		
	    }
	}
	htmlSoftware.append("</li>");
	HTMLElement divPrincipal = document.getElementById("subSoftware");
	divPrincipal.setInnerHTML(htmlSoftware.toString());
	document.executeScript("tree('#subSoftware'); $('#loading_overlay').hide();");
    }
    
	
	public void verificaImpactos(Integer idItemCfg, DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usrDto = (UsuarioDTO) br.com.centralit.citcorpore.util.WebUtil.getUsuario(request);
    	if(usrDto == null){
    		return;
    	}
    	HashMap map = new HashMap();
    	HashMap map2 = new HashMap();
    	ImagemItemConfiguracaoService imagemItemConfiguracaoService = (ImagemItemConfiguracaoService) ServiceLocator.getInstance().getService(ImagemItemConfiguracaoService.class, null);
    	Collection colHierarq = imagemItemConfiguracaoService.findItensRelacionadosHierarquia(idItemCfg);
    	String strTables = "<b><u>"+UtilI18N.internacionaliza(request, "pesquisaItemConfiguracao.iCsVinculados")+"</u></b>";
    	strTables += "<table>";
    	if (colHierarq != null && colHierarq.size() > 0){
    		for (Iterator it = colHierarq.iterator(); it.hasNext();){
    			ItemConfiguracaoDTO itemConfiguracaoAux = (ItemConfiguracaoDTO)it.next();
    			ItemConfiguracaoDTO itemConfiguracaoAux2 = (ItemConfiguracaoDTO) map.get("" + itemConfiguracaoAux.getIdItemConfiguracao());
    			if (itemConfiguracaoAux2 != null){
    				continue;
    			}
    			map.put("" + itemConfiguracaoAux.getIdItemConfiguracao(), itemConfiguracaoAux);
    			strTables += "<tr>";
					strTables += "<td>";
					if (itemConfiguracaoAux.getTipoVinculo().equalsIgnoreCase("FILHO")){
						strTables += "<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/pages/gerenciaConfiguracaoTree/images/item_relation.png' border='0'/>";						
					}else{
						strTables += "<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/pages/gerenciaConfiguracaoTree/images/item_menu_relation.png' border='0'/>";
					}
					strTables += "</td>";    			
    				strTables += "<td>";
    					strTables += "" + itemConfiguracaoAux.getIdentificacao();
    				strTables += "</td>";
    			strTables += "</tr>";
    		}
    	}else{
			strTables += "<tr>";
				strTables += "<td>";  
					strTables += ""+UtilI18N.internacionaliza(request, "MSG04")+"";    
				strTables += "</td>";
			strTables += "</tr>";				
    	}
    	strTables += "</table>";
    	
    	strTables += "<b><u>"+UtilI18N.internacionaliza(request, "pesquisaItemConfiguracao.servicosVinculados")+"</u></b>";
    	strTables += "<table>";
    	colHierarq = imagemItemConfiguracaoService.findServicosRelacionadosHierarquia(idItemCfg);
    	if (colHierarq != null && colHierarq.size() > 0){
    		for (Iterator it = colHierarq.iterator(); it.hasNext();){
    			ServicoDTO servicoDTO = (ServicoDTO)it.next();
    			ServicoDTO servicoAux2 = (ServicoDTO) map2.get("" + servicoDTO.getIdServico());
    			if (servicoAux2 != null){
    				continue;
    			}
    			map2.put("" + servicoDTO.getIdServico(), servicoDTO);
    			strTables += "<tr>";
					strTables += "<td>";
						strTables += "<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/pages/gerenciaConfiguracaoTree/images/item_relation.png' border='0'/>";						
					strTables += "</td>";    			
    				strTables += "<td>";
    					strTables += "" + servicoDTO.getNomeServico();
    				strTables += "</td>";
    			strTables += "</tr>";
    		}
    	}else{
			strTables += "<tr>";
				strTables += "<td>";  
					strTables += ""+UtilI18N.internacionaliza(request, "MSG04")+"";    
				strTables += "</td>";
			strTables += "</tr>";	
    	}
    	strTables += "</table>";
    	
    	document.getElementById("divImpactos").setInnerHTML(strTables);
	}
    

    /**
     * Retorna lista de características.
     * 
     * @param idItemConfiguracao
     * @param tagTipoItemConfiguracao
     * @return listaCaracteristica
     * @throws ServiceException
     * @throws Exception
     * @author rosana.godinho
     */
    public Collection<ValorDTO> getListaCaracteristica(ItemConfiguracaoDTO itemConfiguracao, String tagTipoItemConfiguracao) throws ServiceException, Exception {
	TipoItemConfiguracaoDTO tipoItemConfiguracao = new TipoItemConfiguracaoDTO();
	tipoItemConfiguracao.setTag(tagTipoItemConfiguracao);
	return this.getValorService().findByItemAndTipoItemConfiguracao(itemConfiguracao, tipoItemConfiguracao);

    }
    
    /**
     * Retorna lista de características.
     * 
     * @param idItemConfiguracao
     * @param tagTipoItemConfiguracao
     * @return listaCaracteristica
     * @throws ServiceException
     * @throws Exception
     * @author rosana.godinho
     */
    public Collection<ValorDTO> getListaCaracteristica(ItemConfiguracaoDTO itemConfiguracao, TipoItemConfiguracaoDTO tipoItemConfiguracao) throws ServiceException, Exception {
	return this.getValorService().findByItemAndTipoItemConfiguracao(itemConfiguracao, tipoItemConfiguracao);

    }

    /**
     * Retorna Service de Valor.
     * 
     * @return ValorService
     * @throws Exception
     * @author rosana.godinho
     */
    public ValorService getValorService() throws Exception {
	return (ValorService) ServiceLocator.getInstance().getService(ValorService.class, null);
    }

    /**
     * Retorna Service de InformacaoItemConfiguracao.
     * 
     * @return InformacaoItemConfiguracaoService
     * @throws ServiceException
     * @throws Exception
     * @author rosana.godinho
     */
    public InformacaoItemConfiguracaoService getInformacaoItemConfiguracaoService() throws ServiceException, Exception {
	return (InformacaoItemConfiguracaoService) ServiceLocator.getInstance().getService(InformacaoItemConfiguracaoService.class, null);
    }

    /**
     * @return valor do atributo tipoItemConfiguracao.
     */
    public TipoItemConfiguracaoDTO getTipoItemConfiguracao() {
	return tipoItemConfiguracao;
    }

    /**
     * Define valor do atributo tipoItemConfiguracao.
     * 
     * @param tipoItemConfiguracao
     */
    public void setTipoItemConfiguracao(TipoItemConfiguracaoDTO tipoItemConfiguracao) {
	this.tipoItemConfiguracao = tipoItemConfiguracao;
    }

    public ItemConfiguracaoService getItemConfiguracaoService() throws Exception {
	return (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
    }

    /**
     * @return valor do atributo itemConfiguracao.
     */
    public ItemConfiguracaoDTO getItemConfiguracao() {
	return itemConfiguracao;
    }

    /**
     * Define valor do atributo itemConfiguracao.
     * 
     * @param itemConfiguracao
     */
    public void setItemConfiguracao(ItemConfiguracaoDTO itemConfiguracao) {
	this.itemConfiguracao = itemConfiguracao;
    }

    /**
     * @return valor do atributo informacaoItemConfiguracao.
     */
    public InformacaoItemConfiguracaoDTO getInformacaoItemConfiguracao() {
	return informacaoItemConfiguracao;
    }

    /**
     * Define valor do atributo informacaoItemConfiguracao.
     * 
     * @param informacaoItemConfiguracao
     */
    public void setInformacaoItemConfiguracao(InformacaoItemConfiguracaoDTO informacaoItemConfiguracao) {
	this.informacaoItemConfiguracao = informacaoItemConfiguracao;
    }

}
