package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.fill.JRAbstractLRUVirtualizer;
import net.sf.jasperreports.engine.fill.JRSwapFileVirtualizer;
import net.sf.jasperreports.engine.util.JRSwapFile;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLElement;
import br.com.centralit.citcorpore.bean.CaracteristicaDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.MidiaSoftwareChaveDTO;
import br.com.centralit.citcorpore.bean.MidiaSoftwareDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CaracteristicaService;
import br.com.centralit.citcorpore.negocio.ItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.MidiaSoftwareService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.LogoRel;
import br.com.centralit.citcorpore.util.UtilRelatorio;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

public class RelatorioItemConfiguracaoPacoteOffice extends AjaxFormAction {

	UsuarioDTO usuario;
	String localeSession = null;
	
	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
	}
	
	public void imprimirRelatorioPacoteOffice(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = ((HttpServletRequest) request).getSession();
		ItemConfiguracaoService itemConfiguracaoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
		CaracteristicaService caracteristicaService = (CaracteristicaService) ServiceLocator.getInstance().getService(CaracteristicaService.class, null);
		ItemConfiguracaoDTO configuracaoDTO = (ItemConfiguracaoDTO) document.getBean();
		usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}
		@SuppressWarnings("unchecked")
		List<MidiaSoftwareChaveDTO> midiaSoftwareChaves =  (List<MidiaSoftwareChaveDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(MidiaSoftwareChaveDTO.class, "midiaSoftwareChavesSerealizadas", request);
		configuracaoDTO.setMidiaSoftwareChaves(midiaSoftwareChaves);
		/*Lista os itens de configuração que possuem o pacote office instalado*/
		
		List<ItemConfiguracaoDTO> listItemConfiguracaoDTO = null;
		if(configuracaoDTO.getDuplicado() != null && (midiaSoftwareChaves != null && !midiaSoftwareChaves.isEmpty())) {
			listItemConfiguracaoDTO = new ArrayList<ItemConfiguracaoDTO>();
			for (MidiaSoftwareChaveDTO midiaSoftwareChaveDTO : midiaSoftwareChaves) {
				List<ItemConfiguracaoDTO> chaves = itemConfiguracaoService.listaItemConfiguracaoOfficePak(configuracaoDTO, midiaSoftwareChaveDTO.getChave());
				if(chaves!=null && chaves.size() > 1)
					listItemConfiguracaoDTO.addAll(chaves);
			}
		} else {
			listItemConfiguracaoDTO = itemConfiguracaoService.listaItemConfiguracaoOfficePak(configuracaoDTO);			
		}
		
		if (listItemConfiguracaoDTO == null || listItemConfiguracaoDTO.size() == 0) {
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
			return;
		}
		List<ItemConfiguracaoDTO> listItemConfiguracaoDTO2 = new ArrayList<ItemConfiguracaoDTO>();
		for (ItemConfiguracaoDTO itemConfiguracaoDTO : listItemConfiguracaoDTO) {
			List<CaracteristicaDTO> list =  (List<CaracteristicaDTO>) caracteristicaService.consultarCaracteristicasComValoresItemConfiguracao(
					itemConfiguracaoDTO.getIdTipoItemConfiguracao(), itemConfiguracaoDTO.getIdItemConfiguracao(), new String[] {"PRODUCT", "OFFICEKEY", "OFFICEVERSION"});
			if(list != null && !list.isEmpty()) {
				itemConfiguracaoDTO.setListCaracteristicas(list);
				listItemConfiguracaoDTO2.add(itemConfiguracaoDTO);
			}
		}
		
		listItemConfiguracaoDTO = listItemConfiguracaoDTO2;
		
		Date dt = new Date();
		String strCompl = "" + dt.getTime();
		String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioItemConfiguracaoPacoteOffice.jasper";
		String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
		String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);

		parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "RelatorioItemConfiguracaoPacoteOffice.RelatorioItemConfiguracaoPacoteOffice"));
		parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
		parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
		parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
		parametros.put("SUBREPORT_DIR", CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS"));
		parametros.put("NUMERO_REGISTROS", listItemConfiguracaoDTO.size());
		parametros.put("Logo", LogoRel.getFile());

		try {
			JRDataSource dataSource = new JRBeanCollectionDataSource(listItemConfiguracaoDTO);
			JRSwapFile arquivoSwap = new JRSwapFile(diretorioReceita, 4096, 25);
			JRAbstractLRUVirtualizer virtualizer = new JRSwapFileVirtualizer(25, arquivoSwap, true);
			parametros.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
			JasperPrint print = JasperFillManager.fillReport(caminhoJasper, parametros, dataSource);
			JasperExportManager.exportReportToPdfFile(print, diretorioReceita + "/RelatorioItemConfiguracaoPacoteOffice" + strCompl + "_" + usuario.getIdUsuario() + ".pdf");

			document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
					+ "/RelatorioItemConfiguracaoPacoteOffice" + strCompl + "_" + usuario.getIdUsuario() + ".pdf')");
		} catch (OutOfMemoryError e) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.erro.erroServidor"));
		}
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
	}
	
	public void imprimirRelatorioPacoteOfficeXls(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = ((HttpServletRequest) request).getSession();
		ItemConfiguracaoService itemConfiguracaoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
		CaracteristicaService caracteristicaService = (CaracteristicaService) ServiceLocator.getInstance().getService(CaracteristicaService.class, null);
		ItemConfiguracaoDTO configuracaoDTO = (ItemConfiguracaoDTO) document.getBean();
		usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}
		@SuppressWarnings("unchecked")
		List<MidiaSoftwareChaveDTO> midiaSoftwareChaves =  (List<MidiaSoftwareChaveDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(MidiaSoftwareChaveDTO.class, "midiaSoftwareChavesSerealizadas", request);
		configuracaoDTO.setMidiaSoftwareChaves(midiaSoftwareChaves);
		/*Lista os itens de configuração que possuem o pacote office instalado*/
		List<ItemConfiguracaoDTO> listItemConfiguracaoDTO = null;
		
		if(configuracaoDTO.getDuplicado() != null && (midiaSoftwareChaves != null && !midiaSoftwareChaves.isEmpty())) {
			listItemConfiguracaoDTO = new ArrayList<ItemConfiguracaoDTO>();
			for (MidiaSoftwareChaveDTO midiaSoftwareChaveDTO : midiaSoftwareChaves) {
				List<ItemConfiguracaoDTO> chaves = itemConfiguracaoService.listaItemConfiguracaoOfficePak(configuracaoDTO, midiaSoftwareChaveDTO.getChave());
				if(chaves!=null && chaves.size() > 1)
					listItemConfiguracaoDTO.addAll(chaves);
			}
		} else {
			listItemConfiguracaoDTO = itemConfiguracaoService.listaItemConfiguracaoOfficePak(configuracaoDTO);			
		}
		
		if (listItemConfiguracaoDTO == null || listItemConfiguracaoDTO.size() == 0) {
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
			return;
		}
		
		for (ItemConfiguracaoDTO itemConfiguracaoDTO : listItemConfiguracaoDTO) {
			itemConfiguracaoDTO.setListCaracteristicas((List<CaracteristicaDTO>) caracteristicaService.consultarCaracteristicasComValoresItemConfiguracao(
					itemConfiguracaoDTO.getIdTipoItemConfiguracao(), itemConfiguracaoDTO.getIdItemConfiguracao() , new String[] {"PRODUCT", "OFFICEKEY", "OFFICEVERSION"}));
		}
		
		Date dt = new Date();
		String strCompl = "" + dt.getTime();
		String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
		String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);

		parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "RelatorioItemConfiguracaoPacoteOffice.RelatorioItemConfiguracaoPacoteOffice"));
		parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
		parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
		parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
		parametros.put("SUBREPORT_DIR", CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS"));
		parametros.put("Logo", LogoRel.getFile());
		

		JRDataSource dataSource = new JRBeanCollectionDataSource(listItemConfiguracaoDTO);									   
		JasperDesign desenho = JRXmlLoader.load(CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioItemConfiguracaoPacoteOfficeXls.jrxml");
		JasperReport relatorio = JasperCompileManager.compileReport(desenho);

		JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros, dataSource);

		JRXlsExporter exporter = new JRXlsExporter();
		exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, impressao);
		exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, diretorioReceita + "/RelatorioItemConfiguracaoPacoteOffice" + strCompl + "_" + usuario.getIdUsuario() + ".xls");

		exporter.exportReport();

		document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url="
				+ diretorioRelativoOS + "/RelatorioItemConfiguracaoPacoteOffice" + strCompl + "_" + usuario.getIdUsuario() + ".xls')");
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
	}	

	@SuppressWarnings("rawtypes")
	@Override
	public Class getBeanClass() {
		return ItemConfiguracaoDTO.class;
	}
	
	public void listaChavesMidiaSoftware(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ItemConfiguracaoDTO configuracaoDTO = (ItemConfiguracaoDTO) document.getBean();
		MidiaSoftwareService midiaSoftwareService = (MidiaSoftwareService) ServiceLocator.getInstance().getService(MidiaSoftwareService.class, null);
		MidiaSoftwareDTO midiaSoftware = new MidiaSoftwareDTO();
		if(configuracaoDTO.getIdMidiaSoftware()!=null) {
			midiaSoftware.setIdMidiaSoftware(configuracaoDTO.getIdMidiaSoftware());
			midiaSoftware = (MidiaSoftwareDTO) midiaSoftwareService.restore(midiaSoftware);
			
			HTMLElement divPrincipal = document.getElementById("contentChaves");
			StringBuilder subDiv = new StringBuilder();
			subDiv.append("" +
					"	<table id='tblMidiaSoftwareChave' class='tableLess'> 	" +
					"		<thead>" +
					"			<tr>" +
					"				<th></th>	" +
					"				<th>"+UtilI18N.internacionaliza(request, "midiaSoftware.chave")+"</th>	" + 
					"		</thead>" +
					"");
			int count = 0;
			if(midiaSoftware.getMidiaSoftwareChaves() != null) {
				for (MidiaSoftwareChaveDTO midiaSoftwareChaves : midiaSoftware.getMidiaSoftwareChaves()) {
					subDiv.append(
							"	<tr>"+
							"		<td>" + "<input type='checkbox' checked='checked'  id='idMidiaSoftwareChave" + count + "' name='idMidiaSoftwareChave" + count + "' value='0"+midiaSoftwareChaves.getIdMidiaSoftwareChave()+ "'/>" +
											"<input type='hidden'  id='chave" + count + "' name='chave" + count + "' value='"+midiaSoftwareChaves.getChave()+ "'/>" +
									"</td>" +
							"		<td>" + midiaSoftwareChaves.getChave() + "</td>" +
							"	</tr>");
					count++;
				}
			}
			subDiv.append("" +
					"	</table>");
			divPrincipal.setInnerHTML(subDiv.toString());
		}
	}

}
