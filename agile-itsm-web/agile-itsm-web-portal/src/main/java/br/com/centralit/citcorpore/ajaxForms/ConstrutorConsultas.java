package br.com.centralit.citcorpore.ajaxForms;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.BICategoriasDTO;
import br.com.centralit.citcorpore.bean.BIConsultaColunasDTO;
import br.com.centralit.citcorpore.bean.BIConsultaDTO;
import br.com.centralit.citcorpore.negocio.BICategoriasService;
import br.com.centralit.citcorpore.negocio.BIConsultaColunasService;
import br.com.centralit.citcorpore.negocio.BIConsultaService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citquestionario.util.Upload;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class ConstrutorConsultas extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BIConsultaDTO biConsultaDTO = (BIConsultaDTO) document.getBean();
		BICategoriasService biCategoriasService = (BICategoriasService) ServiceLocator.getInstance().getService(BICategoriasService.class, null);
		Collection col = biCategoriasService.findSemPai();

		HTMLSelect idCategoria = document.getSelectById("idCategoria");
		preencheComboCateg(idCategoria, col, biCategoriasService, 0);

		if (biConsultaDTO.getAcao()!=null){
			if (biConsultaDTO.getAcao().equalsIgnoreCase("restore")){
				restore(document, request, response);
			}
		}else{
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
				if (acao != null && acao.equalsIgnoreCase("importar")){
					importar(fileItems, document, request);
				}
			}
		}
	}

	private void preencheComboCateg(HTMLSelect idCategoria, Collection col, BICategoriasService biCategoriasService, int nivel) throws Exception {
		if (col != null){
			for (Iterator it = col.iterator(); it.hasNext();){
				BICategoriasDTO biCategoriasDTO = (BICategoriasDTO)it.next();
				idCategoria.addOption(geraNivel(nivel) + biCategoriasDTO.getIdCategoria(), biCategoriasDTO.getNomeCategoria());
				Collection colAux = biCategoriasService.findByIdCategoriaPai(biCategoriasDTO.getIdCategoria());
				if (colAux != null){
					preencheComboCateg(idCategoria, colAux, biCategoriasService, nivel + 1);
				}
			}
		}
	}
	private String geraNivel(int niv){
		String ret  = "";
		for (int i = 0; i < niv; i++){
			ret = ret + "...";
		}
		return ret;
	}

	@Override
	public Class getBeanClass() {
		return BIConsultaDTO.class;
	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BIConsultaDTO biConsultaDTO = (BIConsultaDTO) document.getBean();
		BIConsultaService biConsultaService = (BIConsultaService) ServiceLocator.getInstance().getService(BIConsultaService.class, null);

		Collection<BIConsultaColunasDTO> colColunas = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(BIConsultaColunasDTO.class, "colCriterios_Serialize", request);
		biConsultaDTO.setColColunas(colColunas);
		if (biConsultaDTO.getIdConsulta() == null || biConsultaDTO.getIdConsulta().intValue() == 0) {
			biConsultaService.create(biConsultaDTO);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
		} else {
			biConsultaService.update(biConsultaDTO);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}

		HTMLForm form = document.getForm("form");
		form.clear();
		document.executeScript("GRID_COLUNAS.deleteAllRows();");
	}

	public void exportar(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BIConsultaDTO biConsultaDTO = (BIConsultaDTO) document.getBean();
		BIConsultaService biConsultaService = (BIConsultaService) ServiceLocator.getInstance().getService(BIConsultaService.class, null);

		Collection<BIConsultaColunasDTO> colColunas = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(BIConsultaColunasDTO.class, "colCriterios_Serialize", request);
		biConsultaDTO.setColColunas(colColunas);
		if (biConsultaDTO.getIdConsulta() == null || biConsultaDTO.getIdConsulta().intValue() == 0) {
			document.alert(UtilI18N.internacionaliza(request, "construtorconsultas.gravarAntes"));
			return;
		}

		String diretorioExport = request.getSession().getServletContext().getRealPath("/");
		String diretorioRelativoExport = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/";

		diretorioExport = diretorioExport + "exportXML";
		diretorioRelativoExport = diretorioRelativoExport + "exportXML";
		File f = new File(diretorioExport);
		if (!f.exists()){
			f.mkdirs();
		}

		String name = UtilStrings.generateNomeBusca(biConsultaDTO.getIdentificacao());
		name = name + ".citreport";
		FileOutputStream fOut = new FileOutputStream(diretorioExport + "/" + name);

		XStream x = new XStream(new DomDriver("ISO-8859-1"));
		biConsultaDTO.setIdConsulta(null);
		x.toXML(biConsultaDTO, fOut);

		fOut.close();

		//document.executeScript("JANELA_AGUARDE_MENU.hide()");
		document.executeScript("window.open('" + diretorioRelativoExport + "/" + name + "')");

	}

	public void importar(Collection fileItems, DocumentHTML document, HttpServletRequest request) throws Exception{
		BIConsultaService biConsultaService = (BIConsultaService) ServiceLocator.getInstance().getService(BIConsultaService.class, null);
		BIConsultaDTO biConsultaDTO = null;
		FileItem fi;
		if (!fileItems.isEmpty()){
			Iterator it = fileItems.iterator();
			File arquivo;
			while(it.hasNext()){
				fi = (FileItem)it.next();

				XStream x = new XStream(new DomDriver("ISO-8859-1"));

				//System.out.println("Dados importados: " + fi.get().toString());

				String str = new String(fi.get(), "ISO-8859-1");
				biConsultaDTO = (BIConsultaDTO) x.fromXML(str);
			}
		}

		document.executeScript("GRID_COLUNAS.deleteAllRows();");
		HTMLForm form = document.getForm("form");
		try{
			BIConsultaDTO biConsultaAux = biConsultaService.getByIdentificacao(biConsultaDTO.getIdentificacao());
			if (biConsultaAux != null){
				biConsultaDTO.setIdConsulta(biConsultaAux.getIdConsulta());
			}
		}catch(Exception e){}
		form.clear();
		form.setValues(biConsultaDTO);


		Collection<BIConsultaColunasDTO> colColunas = biConsultaDTO.getColColunas();

		if (colColunas != null) {
			int i = 0;
			for (BIConsultaColunasDTO colunaDto : colColunas) {
				i++;
				document.executeScript("GRID_COLUNAS.addRow()");
				document.executeScript("seqColuna = NumberUtil.zerosAEsquerda(" + i + ",5)");
				document.executeScript("exibeColuna('" + br.com.citframework.util.WebUtil.serializeObject(colunaDto, WebUtil.getLanguage(request)) + "')");
			}
		}
		document.alert(UtilI18N.internacionaliza(request, "construtorconsultas.importado"));
	}

	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BIConsultaDTO biConsultaDTO = (BIConsultaDTO) document.getBean();
		BIConsultaService biConsultaService = (BIConsultaService) ServiceLocator.getInstance().getService(BIConsultaService.class, null);
		BIConsultaColunasService biConsultaColunasService = (BIConsultaColunasService) ServiceLocator.getInstance().getService(BIConsultaColunasService.class, null);

		biConsultaDTO = (BIConsultaDTO) biConsultaService.restore(biConsultaDTO);
		document.executeScript("GRID_COLUNAS.deleteAllRows();");
		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(biConsultaDTO);

		Collection<BIConsultaColunasDTO> colColunas = biConsultaColunasService.findByIdConsulta(biConsultaDTO.getIdConsulta());

		if (colColunas != null) {
			int i = 0;
			for (BIConsultaColunasDTO colunaDto : colColunas) {
				i++;
				document.executeScript("GRID_COLUNAS.addRow()");
				document.executeScript("seqColuna = NumberUtil.zerosAEsquerda(" + i + ",5)");
				document.executeScript("exibeColuna('" + br.com.citframework.util.WebUtil.serializeObject(colunaDto, WebUtil.getLanguage(request)) + "')");
			}
		}
	}

}
