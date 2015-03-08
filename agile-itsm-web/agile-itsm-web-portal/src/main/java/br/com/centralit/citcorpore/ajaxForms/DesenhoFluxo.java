package br.com.centralit.citcorpore.ajaxForms;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringEscapeUtils;

import br.com.centralit.bpm.config.Config;
import br.com.centralit.bpm.config.Design;
import br.com.centralit.bpm.dto.ElementoFluxoDTO;
import br.com.centralit.bpm.dto.FluxoDTO;
import br.com.centralit.bpm.dto.SequenciaFluxoDTO;
import br.com.centralit.bpm.servico.FluxoService;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citquestionario.util.Upload;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;
import br.com.citframework.util.UtilTratamentoArquivos;
import br.com.citframework.util.WebUtil;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class DesenhoFluxo extends AjaxFormAction {

	@Override
	public Class<FluxoDTO> getBeanClass() {
		return FluxoDTO.class;
	}

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try{
			Design render = Config.getRender();

			String elementosStr = new Gson().toJson(render.getElementos());
			elementosStr = elementosStr.replaceAll("\n", " ");
			elementosStr = elementosStr.replaceAll("\r", " ");
			elementosStr = elementosStr.replaceAll("\\\\n", " ");

			carregaFluxos(request, document);
			document.executeScript("exibirElementos('"+elementosStr+"');");

			FluxoDTO fluxoDto = (FluxoDTO) document.getBean();
			Upload upload = new Upload();
			HashMap hshRetorno[] = null;

			try{
				hshRetorno = upload.doUploadAll(request);
			}catch (Exception e) {
			}

			if (hshRetorno != null){
				Collection fileItems = hshRetorno[1].values();
				HashMap formItems = hshRetorno[0];

				String acao = (String) formItems.get("ACAO");
				if (acao != null && acao.equalsIgnoreCase("I")){
					importaFluxo(fileItems, document, request);
				}else{
					carregaFluxos(request, document);
				}
			}else{
				carregaFluxos(request, document);
			}
		}finally{
	        document.executeScript("JANELA_AGUARDE_MENU.hide()");
		}
	}

	private void carregaFluxos(HttpServletRequest request, DocumentHTML document) throws Exception {
		FluxoDTO fluxoDto = (FluxoDTO) document.getBean();
		FluxoService fluxoService = (FluxoService) ServiceLocator.getInstance().getService(FluxoService.class, null);
		Collection<FluxoDTO> colFluxos = fluxoService.list();
		String str = "";
		int i = 0;
		int linhaAtual = 0;
		if (colFluxos != null) {
			str = "<table width='100%' class='table table-bordered table-condensed'";
			str += "<tr><th  class='th' style='text-align:center'>Fluxos</th></tr>";
			for (FluxoDTO fluxoAuxDto : colFluxos) {
				i++;
				if (fluxoDto.getIdFluxo() != null && fluxoAuxDto.getIdFluxo().intValue() == fluxoDto.getIdFluxo().intValue())
					linhaAtual = i;
				str += "<tr class='tr' id='tr"+i+"'>";
				str += "<td class='td' style='cursor: pointer;' title='"+UtilI18N.internacionaliza(request, "desenhoFluxo.msg.editarDesenho")+"' onclick='exibeElementosFluxo("+i+","+fluxoAuxDto.getIdFluxo()+")' ><img src='imagens/fluxo.png'>&nbsp;"+fluxoAuxDto.getDescricao()+"</td>";
				str += "</tr>";
			}
			str += "</table>";
		}
		document.getElementById("divFluxos").setInnerHTML(str);
		if (linhaAtual > 0) {
			fluxoDto.setLinhaAtual(""+linhaAtual);
		    HTMLForm form = document.getForm("form");
		    form.setValues(fluxoDto);
		}
	}

	public void exibeElementosFluxo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try{
			FluxoDTO fluxoDto = (FluxoDTO) document.getBean();
			if (fluxoDto.getIdFluxo() == null) {
				document.executeScript("desenhoFluxo.limpa()");
				return;
			}

			FluxoService fluxoService = (FluxoService) ServiceLocator.getInstance().getService(FluxoService.class, null);
			/** Chamada do método específico que recupera o fluxo com os elementos. Operação Usain Bolt - 27.01.2015 - carlos.santos */
			fluxoDto = (FluxoDTO) fluxoService.restoreComEstrutura(fluxoDto);

			exibeElementos(document, fluxoDto);
		}finally{
	        document.executeScript("JANELA_AGUARDE_MENU.hide()");
		}

		document.executeScript("desenhoFluxo.resetScroll()");
	}

	private void exibeElementos(DocumentHTML document, FluxoDTO fluxoDto) throws Exception {
		if (fluxoDto != null && fluxoDto.getColElementos() != null) {
			for (ElementoFluxoDTO elementoDto : fluxoDto.getColElementos()) {
				ElementoFluxoDTO elementoDesignDto = Config.getRender().getElemento(elementoDto.getTipoElemento());
				elementoDto.setBorda(elementoDesignDto.getBorda());
				elementoDto.setAjustavel(elementoDesignDto.getAjustavel());
				elementoDto.setLabel(elementoDesignDto.getLabel());
				elementoDto.setIcone(elementoDesignDto.getIcone());
				elementoDto.setImagem(elementoDesignDto.getImagem());
				elementoDto.setLarguraPadrao(elementoDesignDto.getLarguraPadrao());
				elementoDto.setAlturaPadrao(elementoDesignDto.getAlturaPadrao());
				elementoDto.setPropriedades(elementoDesignDto.getPropriedades());
				if (elementoDto.getTipoInteracao() != null) {
					if (elementoDto.getTipoInteracao().equalsIgnoreCase("U"))
						elementoDto.setInteracao(elementoDto.getUrl());
					else
						elementoDto.setInteracao(elementoDto.getVisao());
				}
				elementoDto.setPropriedades_serializadas(new Gson().toJson(elementoDto.getPropriedades()));
			}

			String elementos_serializados = WebUtil.serializeObjects(fluxoDto.getColElementos(), document.getLanguage());
			String sequencias_serializadas = WebUtil.serializeObjects(fluxoDto.getColSequenciamentos(), document.getLanguage());

			fluxoDto.setElementos_serializados(elementos_serializados);
			fluxoDto.setSequencias_serializadas(sequencias_serializadas);

			document.executeScript("desenhoFluxo.renderizaElementos('"+StringEscapeUtils.escapeJavaScript(elementos_serializados)+"','"+sequencias_serializadas+"');");
			document.executeScript("redimensionaAreaDesenho()");
		}else{
			document.executeScript("desenhoFluxo.resetaLista();");
			document.executeScript("desenhoFluxo.limpa()");
		}
		if (fluxoDto != null)
			document.executeScript("controlarExibicaoBotoes('block');");
	}

	public void atualizaDiagrama(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try{
			FluxoDTO fluxoDto = (FluxoDTO) document.getBean();
			if (fluxoDto.getIdFluxo() == null)
				return;

			FluxoService fluxoService = (FluxoService) ServiceLocator.getInstance().getService(FluxoService.class, null);
			/** Chamada do método específico que recupera o fluxo com os elementos. Operação Usain Bolt - 27.01.2015 - carlos.santos */
			fluxoDto = (FluxoDTO) fluxoService.restoreComEstrutura(fluxoDto);

			fluxoDto.setColElementos((List) WebUtil.deserializeCollectionFromRequest(ElementoFluxoDTO.class,"elementos_serializados", request));
			fluxoDto.setColSequenciamentos((List) WebUtil.deserializeCollectionFromRequest(SequenciaFluxoDTO.class,"sequencias_serializadas", request));

			fluxoDto = fluxoService.criaEstrutura(fluxoDto);
			document.alert(UtilI18N.internacionaliza(request, "desenhoFluxo.msg.diagramaAtualizado"));
			document.executeScript("atualizar("+fluxoDto.getIdFluxo()+");");
		}finally{
	        document.executeScript("JANELA_AGUARDE_MENU.hide()");
		}
	}

    private FluxoService getFluxoService() throws ServiceException, Exception {
    	return (FluxoService) ServiceLocator.getInstance().getService(FluxoService.class, null);
    }

    public void gravaCadastroFluxo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		FluxoDTO fluxoDto = (FluxoDTO) document.getBean();
		try{
			String variaveis = fluxoDto.getVariaveis();
			variaveis = variaveis.replaceAll("\n", ";");
			fluxoDto.setVariaveis(variaveis);
			if (fluxoDto.getIdFluxo() == null || fluxoDto.getIdFluxo().intValue() == 0) {
				if (fluxoDto.getAcao() != null && fluxoDto.getAcao().equalsIgnoreCase("I")) {
					fluxoDto.setColElementos((List) WebUtil.deserializeCollectionFromRequest(ElementoFluxoDTO.class,"elementos_serializados", request));
					fluxoDto.setColSequenciamentos((List) WebUtil.deserializeCollectionFromRequest(SequenciaFluxoDTO.class,"sequencias_serializadas", request));

					fluxoDto = getFluxoService().criaFluxoEEstrutura(fluxoDto);
				}else{
					fluxoDto = (FluxoDTO) getFluxoService().create(fluxoDto);
				}
				document.alert(UtilI18N.internacionaliza(request, "desenhoFluxo.msg.fluxoIncluido"));
			    HTMLForm form = document.getForm("form");
			    form.setValues(fluxoDto);
			} else {
				getFluxoService().update(fluxoDto);
				document.alert(UtilI18N.internacionaliza(request, "desenhoFluxo.msg.fluxoAtualizado"));
			}
		}finally{
	        document.executeScript("$(\"#POPUP_FLUXO\").dialog(\"close\")");
			document.executeScript("atualizar("+fluxoDto.getIdFluxo()+");");
		}
    }

    public void recuperaCadastroFluxo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	try{
			FluxoDTO fluxoDto = (FluxoDTO) document.getBean();
			fluxoDto = (FluxoDTO) getFluxoService().restore(fluxoDto);
			if (fluxoDto.getDataFim() != null)
				fluxoDto = getFluxoService().findByTipoFluxo(fluxoDto.getIdTipoFluxo());

			if (fluxoDto != null) {
				if (fluxoDto.getVariaveis() != null) {
					String variaveis = fluxoDto.getVariaveis();
					variaveis = variaveis.replaceAll(";", "\n");
					fluxoDto.setVariaveis(variaveis);
				}
			    HTMLForm form = document.getForm("form");
			    form.setValues(fluxoDto);
			}
		}finally{
	        document.executeScript("JANELA_AGUARDE_MENU.hide()");
		}
    }

    public void excluiCadastroFluxo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	try{
			FluxoDTO fluxoDto = (FluxoDTO) document.getBean();

			if (fluxoDto.getIdFluxo() == null || fluxoDto.getIdFluxo().intValue() == 0)
				return;

			getFluxoService().delete(fluxoDto);
			document.alert(UtilI18N.internacionaliza(request, "desenhoFluxo.msg.fluxoExcluido"));
			document.executeScript("parent.atualizar('');");
    	}finally{
	        document.executeScript("JANELA_AGUARDE_MENU.hide()");
		}
    }

	public void exportaFluxo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try{
			FluxoDTO fluxoDto = (FluxoDTO) document.getBean();
			if (fluxoDto.getIdFluxo() == null)
				return;

			FluxoService fluxoService = (FluxoService) ServiceLocator.getInstance().getService(FluxoService.class, null);
			/** Chamada do método específico que recupera o fluxo com os elementos. Operação Usain Bolt - 27.01.2015 - carlos.santos */
			fluxoDto = (FluxoDTO) fluxoService.restoreComEstrutura(fluxoDto);
			if (fluxoDto == null)
				return;

			if (fluxoDto.getColElementos() != null) {
				for (ElementoFluxoDTO elementoDto : fluxoDto.getColElementos()) {
					ElementoFluxoDTO elementoDesignDto = Config.getRender().getElemento(elementoDto.getTipoElemento());
					elementoDto.setBorda(elementoDesignDto.getBorda());
					elementoDto.setAjustavel(elementoDesignDto.getAjustavel());
					elementoDto.setLabel(elementoDesignDto.getLabel());
					elementoDto.setIcone(elementoDesignDto.getIcone());
					elementoDto.setImagem(elementoDesignDto.getImagem());
					elementoDto.setLarguraPadrao(elementoDesignDto.getLarguraPadrao());
					elementoDto.setAlturaPadrao(elementoDesignDto.getAlturaPadrao());
					elementoDto.setPropriedades(elementoDesignDto.getPropriedades());
					if (elementoDto.getTipoInteracao() != null) {
						if (elementoDto.getTipoInteracao().equalsIgnoreCase("U"))
							elementoDto.setInteracao(elementoDto.getUrl());
						else
							elementoDto.setInteracao(elementoDto.getVisao());
					}
				}
			}

			fluxoDto.setIdFluxo(null);
			fluxoDto.setIdTipoFluxo(null);

			String diretorioExport = request.getSession().getServletContext().getRealPath("/");
			String diretorioRelativoExport = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/";

			diretorioExport = diretorioExport + "exportXML";
			diretorioRelativoExport = diretorioRelativoExport + "exportXML";
			File f = new File(diretorioExport);
			if (!f.exists()){
				f.mkdirs();
			}

			String name = UtilStrings.generateNomeBusca(fluxoDto.getNomeFluxo());
			name = "export_flow_" + name + ".xml";
			FileOutputStream fOut = new FileOutputStream(diretorioExport + "/" + name);

			XStream x = new XStream(new DomDriver("ISO-8859-1"));
			x.toXML(fluxoDto, fOut);

			String str = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>"+UtilTratamentoArquivos.getStringTextFromFileTxt(diretorioExport + "/" + name,"ISO-8859-1");
			fOut = new FileOutputStream(diretorioExport + "/" + name);
			//codigo abaixo necessário para tratar o encode nos servidores linux
			BufferedWriter out = null;
			try {
				out = new BufferedWriter(new OutputStreamWriter(fOut,"ISO-8859-1"));
			} catch (Exception e) {
				out = new BufferedWriter(new OutputStreamWriter(fOut));
				e.printStackTrace();
			}
			out.write(str);
			out.close();

	        document.executeScript("window.open('" + diretorioRelativoExport + "/" + name + "')");
	    }finally{
	        document.executeScript("JANELA_AGUARDE_MENU.hide()");
		}
	}

	public void importaFluxo(Collection fileItems, DocumentHTML document, HttpServletRequest request) throws Exception{
		FluxoDTO fluxoDto = null;
		FileItem fi;
		if (!fileItems.isEmpty()){
			Iterator it = fileItems.iterator();
			File arquivo;
			while(it.hasNext()){
				fi = (FileItem)it.next();

				XStream x = new XStream(new DomDriver("ISO-8859-1"));

				//System.out.println("Dados importados: " + fi.get().toString());

				String str = new String(fi.get(), "ISO-8859-1");
				if (!str.equals("")){
					fluxoDto = (FluxoDTO) x.fromXML(str);
				} else {
					document.executeScript("alert('"+ UtilI18N.internacionaliza(request, "desenhoFluxo.alertSelecione")+"')");
					document.executeScript("$('#POPUP_IMPORTACAO').dialog('open')");
				}
			}
		}

		if (fluxoDto != null) {
			if (fluxoDto.getVariaveis() != null) {
				String variaveis = fluxoDto.getVariaveis();
				variaveis = variaveis.replaceAll(";", "\n");
				fluxoDto.setVariaveis(variaveis);
			}
			fluxoDto.setIdFluxo(null);
			fluxoDto.setDescricao(UtilI18N.internacionaliza(request, "desenhoFluxo.label.novoFluxo"));
			fluxoDto.setVersao(null);
			fluxoDto.setAcao("I");
		    exibeElementos(document, fluxoDto);
		    HTMLForm form = document.getForm("form");
		    form.setValues(fluxoDto);
		}

	}

}
