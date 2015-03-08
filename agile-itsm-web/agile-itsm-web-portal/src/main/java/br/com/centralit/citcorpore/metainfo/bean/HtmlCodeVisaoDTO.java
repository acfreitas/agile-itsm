package br.com.centralit.citcorpore.metainfo.bean;

import java.util.ArrayList;
import java.util.Collection;

import br.com.citframework.dto.IDto;

public class HtmlCodeVisaoDTO implements IDto {
	public static HtmlCodePartDTO HTMLCODE_INIT = new HtmlCodePartDTO("INIT", "visaoAdm.inicioAreaCentral");
	public static HtmlCodePartDTO HTMLCODE_END  = new HtmlCodePartDTO("END", "visaoAdm.finalAreaCentral");
	public static HtmlCodePartDTO HTMLCODE_INIT_FORM = new HtmlCodePartDTO("INIT_FORM", "visaoAdm.inicioFormulario");
	public static HtmlCodePartDTO HTMLCODE_END_FORM  = new HtmlCodePartDTO("END_FORM", "visaoAdm.finalFormulario");
	public static HtmlCodePartDTO HTMLCODE_INIT_BUTTONS = new HtmlCodePartDTO("INIT_BUTTONS", "visaoAdm.inicioAreaBotao");
	public static HtmlCodePartDTO HTMLCODE_END_BUTTONS  = new HtmlCodePartDTO("END_BUTTONS", "visaoAdm.finalAreaBotao");	
	public static HtmlCodePartDTO HTMLCODE_SUPERIOR  = new HtmlCodePartDTO("SUPERIOR", "visaoAdm.areaSuperior");	
	
	private Integer idHtmlCodeVisao;
	private Integer idVisao;
	private String htmlCodeType;
	private String htmlCode;

	public static Collection<HtmlCodePartDTO> colHtmlCodeParts = new ArrayList<HtmlCodePartDTO>();
	
	static {
		colHtmlCodeParts.add(HTMLCODE_INIT);
		colHtmlCodeParts.add(HTMLCODE_END);
		colHtmlCodeParts.add(HTMLCODE_INIT_FORM);
		colHtmlCodeParts.add(HTMLCODE_END_FORM);
		colHtmlCodeParts.add(HTMLCODE_INIT_BUTTONS);
		colHtmlCodeParts.add(HTMLCODE_END_BUTTONS);
		colHtmlCodeParts.add(HTMLCODE_SUPERIOR);
	}
	
	public Integer getIdHtmlCodeVisao(){
		return this.idHtmlCodeVisao;
	}
	public void setIdHtmlCodeVisao(Integer parm){
		this.idHtmlCodeVisao = parm;
	}

	public Integer getIdVisao(){
		return this.idVisao;
	}
	public void setIdVisao(Integer parm){
		this.idVisao = parm;
	}

	public String getHtmlCodeType(){
		return this.htmlCodeType;
	}
	public void setHtmlCodeType(String parm){
		this.htmlCodeType = parm;
	}

	public String getHtmlCode(){
		return this.htmlCode;
	}
	public void setHtmlCode(String parm){
		this.htmlCode = parm;
	}
}
