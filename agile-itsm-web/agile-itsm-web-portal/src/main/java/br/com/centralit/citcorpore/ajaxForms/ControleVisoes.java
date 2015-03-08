package br.com.centralit.citcorpore.ajaxForms;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.VisaoPersonalizadaDTO;
import br.com.centralit.citcorpore.metainfo.bean.VisaoDTO;
import br.com.centralit.citcorpore.metainfo.negocio.VisaoService;
import br.com.centralit.citcorpore.negocio.VisaoPersonalizadaService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilTratamentoArquivos;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class ControleVisoes extends AjaxFormAction {

    @Override
    public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	VisaoPersonalizadaService visaoPersonalizadaService = (VisaoPersonalizadaService) ServiceLocator.getInstance().getService(VisaoPersonalizadaService.class, null);
	VisaoService visaoService = (VisaoService) ServiceLocator.getInstance().getService(VisaoService.class, null);
	
	String dirPrinc = CITCorporeUtil.CAMINHO_REAL_APP + "/visoesXML";
	File fileDir = new File(dirPrinc);
	String strBuffer = "<table>";
	strBuffer += "<tr>";
	strBuffer += "<td>";
		strBuffer += "<b>Visão</b>";
	strBuffer += "</td>";
	strBuffer += "<td>";
		strBuffer += "<b>Personalizada nesta instalação?</b>&nbsp;&nbsp;";
	strBuffer += "</td>"; 
	strBuffer += "<td>";
		strBuffer += "<b>Carregar agora ?</b>";
	strBuffer += "</td>";				
	strBuffer += "</tr>";	
	if (fileDir.isDirectory()){
	    File[] files = fileDir.listFiles();
	    if (files != null){
        	    for(int i = 0; i < files.length; i++){
        		byte[] bytes = UtilTratamentoArquivos.getBytesFromFile(files[i]);
			XStream x = new XStream(new DomDriver("ISO-8859-1"));
			
			String str = new String(bytes, "ISO-8859-1");
			VisaoDTO visaoAux = (VisaoDTO) x.fromXML(str);
			
        		VisaoDTO visaoDto = visaoService.findByIdentificador(visaoAux.getIdentificador());
        		String personalizada = "N";
        		if (visaoDto != null){
        		    VisaoPersonalizadaDTO visaoPersonalizadaAux = new VisaoPersonalizadaDTO();
        		    visaoPersonalizadaAux.setIdvisao(visaoDto.getIdVisao());
        		    try{
        			visaoPersonalizadaAux = (VisaoPersonalizadaDTO) visaoPersonalizadaService.restore(visaoPersonalizadaAux);
        			if (visaoPersonalizadaAux != null){
        			    if (visaoPersonalizadaAux.getPersonalizada() != null
        				    && visaoPersonalizadaAux.getPersonalizada().equalsIgnoreCase("S")){
        				personalizada = "S";
        			    }
        			}
        		    }catch (Exception e) {
			    }
        		}
        		strBuffer += "<tr>";
        			strBuffer += "<td>";
        				strBuffer += "" + files[i].getName();
        			strBuffer += "</td>";
        			strBuffer += "<td>";
					strBuffer += "<input type='checkbox' name='identifPersonalizado' id='identifPersonalizado' value='" + files[i].getName() + "' " + (personalizada.equalsIgnoreCase("S") ? "checked=checked" : "") + "/>";
				strBuffer += "</td>"; 
        			strBuffer += "<td>";
					strBuffer += "<input type='checkbox' name='carregar' id='carregar' value='" + files[i].getName() + "'/>";
				strBuffer += "</td>";
        			strBuffer += "<td>";
					strBuffer += "<div id='" + files[i].getName() + "'></div>";
				strBuffer += "</td>";				
        		strBuffer += "</tr>";
        	    }
	    }
	}
	strBuffer += "<tr>";
	strBuffer += "<td>";
		strBuffer += "&nbsp;";
	strBuffer += "</td>";
	strBuffer += "<td>";
		strBuffer += "<input type='button' name='btnGravarPers' id='btnGravarPers' value='Gravar' onclick='gravar()'/>";
	strBuffer += "</td>"; 
	strBuffer += "<td>";
		strBuffer += "<input type='button' name='btnCarregar' id='btnCarregar' value='Carregar'/>";
	strBuffer += "</td>";				
	strBuffer += "</tr>";	
	strBuffer += "</table>";
	document.getElementById("divVisoes").setInnerHTML(strBuffer);
    }

    @Override
    public Class getBeanClass() {
	return VisaoPersonalizadaDTO.class;
    }

    public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	VisaoPersonalizadaDTO visaoPersonalizadaDTO = (VisaoPersonalizadaDTO)document.getBean();
	VisaoPersonalizadaService visaoPersonalizadaService = (VisaoPersonalizadaService) ServiceLocator.getInstance().getService(VisaoPersonalizadaService.class, null);
	VisaoService visaoService = (VisaoService) ServiceLocator.getInstance().getService(VisaoService.class, null);
	visaoPersonalizadaService.deleteAll();
	if (visaoPersonalizadaDTO.getIdentifPersonalizado() != null){
	    for (int i = 0; i < visaoPersonalizadaDTO.getIdentifPersonalizado().length; i++){
		File file = new File(CITCorporeUtil.CAMINHO_REAL_APP + "/visoesXML/" + visaoPersonalizadaDTO.getIdentifPersonalizado()[i]);
		byte[] bytes = UtilTratamentoArquivos.getBytesFromFile(file);
		XStream x = new XStream(new DomDriver("ISO-8859-1"));
		
		String str = new String(bytes, "ISO-8859-1");
		VisaoDTO visaoAux = (VisaoDTO) x.fromXML(str);		
		
		VisaoDTO visaoDto = visaoService.findByIdentificador(visaoAux.getIdentificador());
		if (visaoDto != null){
		    VisaoPersonalizadaDTO visaoPersonalizadaAux = new VisaoPersonalizadaDTO();
		    visaoPersonalizadaAux.setIdvisao(visaoDto.getIdVisao());
		    try{
			visaoPersonalizadaAux = (VisaoPersonalizadaDTO) visaoPersonalizadaService.restore(visaoPersonalizadaAux);
        		    if (visaoPersonalizadaAux == null){
        			visaoPersonalizadaAux = new VisaoPersonalizadaDTO();
        			visaoPersonalizadaAux.setIdvisao(visaoDto.getIdVisao());
        			visaoPersonalizadaAux.setPersonalizada("S");
        			visaoPersonalizadaAux.setDataModif(UtilDatas.getDataAtual());			
        			visaoPersonalizadaService.create(visaoPersonalizadaAux);
        		    }else{
        			visaoPersonalizadaAux.setPersonalizada("S");
        			visaoPersonalizadaAux.setDataModif(UtilDatas.getDataAtual());
        			visaoPersonalizadaService.update(visaoPersonalizadaAux);
        		    }
        		    document.getElementById(visaoPersonalizadaDTO.getIdentifPersonalizado()[i]).setInnerHTML("Visão atualizada com sucesso!");
		    }catch (Exception e) {
			document.getElementById(visaoPersonalizadaDTO.getIdentifPersonalizado()[i]).setInnerHTML("Ocorreu um erro ao atualizar!");
		    }
		}else{
		    document.getElementById(visaoPersonalizadaDTO.getIdentifPersonalizado()[i]).setInnerHTML("Visão não encontrada. Faça a importação.");
		}
	    }
	}
    }
}
