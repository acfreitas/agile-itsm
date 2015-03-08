package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLElement;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.GrupoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.InformacaoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.ParametroCorporeDTO;
import br.com.centralit.citcorpore.bean.TipoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.ValorDTO;
import br.com.centralit.citcorpore.negocio.GrupoItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.InformacaoItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.ItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.ParametroCorporeService;
import br.com.centralit.citcorpore.negocio.ValorService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;

public class InformacaoItemConfiguracaoTree extends AjaxFormAction {

    private InformacaoItemConfiguracaoDTO informacaoItemConfiguracao;

    private TipoItemConfiguracaoDTO tipoItemConfiguracao = new TipoItemConfiguracaoDTO();

    private ItemConfiguracaoDTO itemConfiguracao = new ItemConfiguracaoDTO();

    @SuppressWarnings("rawtypes")
    @Override
    public Class getBeanClass() {
	return InformacaoItemConfiguracaoDTO.class;
    }

    @Override
    public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	this.informacao(document, request, response);
    }

    @SuppressWarnings("unused")
	public void informacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	this.setInformacaoItemConfiguracao((InformacaoItemConfiguracaoDTO) document.getBean());
	this.getItemConfiguracao().setIdItemConfiguracao(Integer.parseInt(request.getParameter("id")));
	this.setItemConfiguracao((ItemConfiguracaoDTO) this.getItemConfiguracaoService().restore(this.getItemConfiguracao()));
	this.setInformacaoItemConfiguracao(this.getInformacaoItemConfiguracaoService().listByInformacao(this.getItemConfiguracao()));
	
	GrupoItemConfiguracaoDTO grupoItemConfiguracaoDTO = new GrupoItemConfiguracaoDTO();
	ParametroCorporeService parametroService = (ParametroCorporeService) ServiceLocator.getInstance().getService(ParametroCorporeService.class, null);
	ParametroCorporeDTO parametroDTO = new ParametroCorporeDTO();
	
	if(this.getItemConfiguracao().getIdGrupoItemConfiguracao() != null && this.getItemConfiguracao().getIdGrupoItemConfiguracao() > 0){
		grupoItemConfiguracaoDTO.setIdGrupoItemConfiguracao(this.getItemConfiguracao().getIdGrupoItemConfiguracao());
		GrupoItemConfiguracaoService grupoItemConfiguracaoService = (GrupoItemConfiguracaoService) ServiceLocator.getInstance().getService(GrupoItemConfiguracaoService.class, null);
		grupoItemConfiguracaoDTO = (GrupoItemConfiguracaoDTO) grupoItemConfiguracaoService.restore(grupoItemConfiguracaoDTO);
	} else {
		
	}

	StringBuilder subDiv = new StringBuilder();
	/* Cabeçalho */
	subDiv.append("<div id='cabecalhoInf'>");
	subDiv.append("<h2>Descri&ccedil;&atilde;o dos Ativos da M&aacute;quina</h2><hr />");
	subDiv.append("<label>");
	subDiv.append("Identificação: <input id='input' readonly='readonly' value='" + this.getItemConfiguracao().getIdentificacao() + "' type='text' maxlength='80' name='unidade'/>");
	subDiv.append("</label>");
	subDiv.append("<label>");
	if(this.getItemConfiguracao().getIdGrupoItemConfiguracao() != null && this.getItemConfiguracao().getIdGrupoItemConfiguracao() > 0){
		subDiv.append("Grupo: <input id='input' readonly='readonly' value='" + grupoItemConfiguracaoDTO.getNomeGrupoItemConfiguracao() + "' type='text' maxlength='80' name='unidade'/>");
	} else {

		if (!ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.NOME_GRUPO_ITEM_CONFIG_NOVOS, " ").trim().equalsIgnoreCase("")) {
			subDiv.append("Grupo: <input id='input' readonly='readonly' value='" + ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.NOME_GRUPO_ITEM_CONFIG_NOVOS, " ") + "' type='text' maxlength='80' name='unidade'/>");
		}
	}
	subDiv.append("</label>");
	subDiv.append("<hr />");
	subDiv.append("</div>");
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
	subDiv.append("Data do &Uacute;ltimo Ivent&aacute;rio");
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
	subDiv.append("Caracter&iacute;sticas");
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
	subDiv.append("Data do &Uacute;ltimo Ivent&aacute;rio");
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
	subDiv.append("Caracter&iacute;sticas");
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
	subDiv.append("Data do &Uacute;ltimo Ivent&aacute;rio");
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
	subDiv.append("Caracter&iacute;sticas");
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
	HTMLElement divPrincipal = document.getElementById("principalInf");
	divPrincipal.setInnerHTML(subDiv.toString());
	document.executeScript("tree('#browser'); $('#loading_overlay').hide();");
	HTMLForm form = CITCorporeUtil.limparFormulario(document);
	form.setValues(this.getInformacaoItemConfiguracao());
    }

    public void prepararHtmlBios(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
	this.setInformacaoItemConfiguracao((InformacaoItemConfiguracaoDTO) document.getBean());
	this.getItemConfiguracao().setIdItemConfiguracao(this.getInformacaoItemConfiguracao().getIdItemConfiguracao());

	StringBuilder htmlBios = new StringBuilder();

	for (ValorDTO valor : this.getListaCaracteristica(this.getItemConfiguracao(), "BIOS")) {
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
	for (ValorDTO valor : this.getListaCaracteristica(this.getItemConfiguracao(), "HARDWARE")) {
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
	for (ValorDTO valor : this.getListaCaracteristica(this.getItemConfiguracao(), "SOFTWARES")) {
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
