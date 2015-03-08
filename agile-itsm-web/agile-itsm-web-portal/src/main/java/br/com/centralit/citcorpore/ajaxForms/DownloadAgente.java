package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.DicionarioDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({ "rawtypes",  })
public class DownloadAgente extends AjaxFormAction {

	static final int TAMANHO_BUFFER = 65536;
	private UsuarioDTO usuario;
	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		usuario = br.com.centralit.citcorpore.util.WebUtil.getUsuario(request);

		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		document.executeScript("$('#btnGravar').hide();");

		//this.preencherComboIdioma(document, request, response);
	}

//	public void criarMensagensNovos(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//		DicionarioDTO dicionario = (DicionarioDTO) document.getBean();
//		if (dicionario.getIdLingua() == null || dicionario.getIdLingua().intValue() == 0){
//			document.alert(UtilI18N.internacionaliza(request, "dicionario.selecioneUmIdioma"));
//			return;
//		}
//		DicionarioService dicionarioService = (DicionarioService) ServiceLocator.getInstance().getService(DicionarioService.class, null);
//		if (dicionario.getIdLingua() != null) {
//			LinguaService linguaService = (LinguaService) ServiceLocator.getInstance().getService(LinguaService.class, null);
//			LinguaDTO linguaDto = new LinguaDTO();
//			linguaDto.setIdLingua(dicionario.getIdLingua());
//			linguaDto = (LinguaDTO) linguaService.restore(linguaDto);
//			if (linguaDto != null){
//				/* auxNome e' usado para se trabalhar com nome da lingua. */
//				String auxNome = "";
//				if(linguaDto.getNome() != null){
//					auxNome = linguaDto.getNome().toLowerCase();
//				}
//
//				/* Acrescentada a verificacao se o nome e' similiar a "portug" para obter o arquivo correto. */
//				if (linguaDto.getSigla() == null || auxNome.contains("portug")){
//					linguaDto.setSigla("");
//				}
//				dicionarioService.criarMensagensNovos(request, linguaDto.getSigla(), linguaDto.getIdLingua());
//				this.montarTabelaDicionario(document, request, response);
//			}
//		}
//
//	}
//
//	public void montarTabelaDicionario(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//		DicionarioDTO dicionario = (DicionarioDTO) document.getBean();
//
//		Integer contIdDicionario = 0;
//		Integer contIdLingua = 0;
//		Integer contNome = 0;
//		Integer contValor = 0;
//
//		DicionarioService dicionarioService = (DicionarioService) ServiceLocator.getInstance().getService(DicionarioService.class, null);
//
//		LinguaService linguaService = (LinguaService) ServiceLocator.getInstance().getService(LinguaService.class, null);
//
//		LinguaDTO linguaDto = new LinguaDTO();
//
//		Collection<DicionarioDTO> listaDicionario = null;
//
//		if (dicionario.getIdLingua() != null) {
//
//			listaDicionario = dicionarioService.listaDicionario(dicionario);
//		}
//
//		StringBuilder html = new StringBuilder();
//
//		html.append("<table class='table' id='tabelaRetorno' width='100%' >");
//		if (listaDicionario != null) {
//
//			for (DicionarioDTO dicionarioDto : listaDicionario) {
//
//				linguaDto.setIdLingua(dicionarioDto.getIdLingua());
//
//				linguaDto = (LinguaDTO) linguaService.restore(linguaDto);
//
//				html.append("<tr>");
//				html.append("<th style='background:#FFFFFF; border:0px solid black;'>&nbsp;</th>");
//				html.append("<th>" + linguaDto.getNome() + "</th>");
//				html.append("</tr>");
//				break;
//			}
//
//			html.append("<tr>");
//			html.append("<th>"+UtilI18N.internacionaliza(request, "citcorpore.comum.key")+"</th>");
//			html.append("<th>"+UtilI18N.internacionaliza(request, "citcorpore.comum.valor")+"</th>");
//			html.append("</tr>");
//
//			for (DicionarioDTO dicionarioDto : listaDicionario) {
//				contIdDicionario++;
//				contIdLingua++;
//				contNome++;
//				contValor++;
//
//				html.append("<tr>");
//				html.append("<input type='hidden'  id='idDicionario" + contIdDicionario + "'  name='idDicionario" + contIdDicionario + "' value='"
//						+ dicionarioDto.getIdDicionario() + "' />");
//				html.append("<input type='hidden'  id='idLingua" + contIdLingua + "'  name='idLingua" + contIdLingua + "' value='" + dicionarioDto.getIdLingua() + "' />");
//				html.append("<td><input name='nome" + contNome + "' readonly='readonly' id='nome" + contNome + "' size='100' value='" + dicionarioDto.getNome()
//						+ "' type='text' maxlength='256' /></td>");
//				html.append("<td><input name='valor" + contValor + "' id='valor" + contValor + "' size='100' value='" + dicionarioDto.getValor()
//						+ "' type='text' maxlength='256' /></td>");
//				html.append("</tr>");
//			}
//
//			for (int i = 0; i < 20; i++) {
//				contIdDicionario++;
//				contIdLingua++;
//				contNome++;
//				contValor++;
//
//				html.append("<tr>");
//				html.append("<input type='hidden'  id='idDicionario" + contIdDicionario + "'  name='idDicionario" + contIdDicionario + "' value='' />");
//				html.append("<input type='hidden'  id='idLingua" + contIdLingua + "'  name='idLingua" + contIdLingua + "' value='" + dicionario.getIdLingua() + "' />");
//				html.append("<td><input name='nome" + contNome + "' id='nome" + contNome + "' size='100' value='' type='text' maxlength='256' /></td>");
//				html.append("<td><input name='valor" + contValor + "' id='valor" + contValor + "' size='100' value='' type='text' maxlength='256' /></td>");
//				html.append("</tr>");
//			}
//
//		}
//		html.append("</table >");
//		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
//		HTMLElement divPrincipal = document.getElementById("tabelaDicionario");
//		divPrincipal.setInnerHTML(html.toString());
//		document.executeScript("$('#btnGravar').show();");
//
//	}
//
//	private void inicializaCombo(HTMLSelect componenteCombo, HttpServletRequest request) {
//		componenteCombo.removeAllOptions();
//		componenteCombo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
//	}

//	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//		DicionarioDTO dicionario = (DicionarioDTO) document.getBean();
//
//		dicionario.setListDicionario(((Collection<DicionarioDTO>) WebUtil.deserializeCollectionFromRequest(DicionarioDTO.class, "dicionarioSerializados", request)));
//
//		DicionarioService dicionarioService = (DicionarioService) ServiceLocator.getInstance().getService(DicionarioService.class, null);
//
//		if(dicionario.getListDicionario()!=null ){
//
//			for (DicionarioDTO dicionarioDto : dicionario.getListDicionario()) {
//
//				if (dicionarioDto.getIdDicionario()!=null) {
//					if(dicionarioDto.getIdDicionario() != null){
//						dicionarioService.update(dicionarioDto);
//					}
//				}else{
//					if (dicionarioDto.getNome() != null && !dicionarioDto.getNome().trim().equalsIgnoreCase("")){
//						dicionarioService.create(dicionarioDto);
//					}
//				}
//
//			}
//
//			this.montarTabelaDicionario(document, request, response);
//		}
//
//		document.alert(UtilI18N.internacionaliza(request, "MSG05"));
//		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
//
//		I18N i18n = new I18N();
//		i18n.carregaMaps();
//	}
//
//	public void preencherComboIdioma(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//		LinguaService linguaService = (LinguaService) ServiceLocator.getInstance().getService(LinguaService.class, null);
//
//		HTMLSelect comboIdioma = (HTMLSelect) document.getSelectById("idLingua");
//
//		Collection<LinguaDTO> listaIdioma = linguaService.list();
//
//		inicializaCombo(comboIdioma, request);
//
//		if (listaIdioma != null && listaIdioma.size() != 0) {
//
//			for (LinguaDTO linguaDto : listaIdioma) {
//
//				comboIdioma.addOption(linguaDto.getIdLingua().toString(), linguaDto.getNome());
//			}
//		}
//
//	}

	@Override
	public Class getBeanClass() {
		return DicionarioDTO.class;
	}

//	public void exportarDicionarioXml(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
//
//		DicionarioService dicionarioService = (DicionarioService) ServiceLocator.getInstance().getService(DicionarioService.class, null);
//
//		DicionarioDTO dicionario = (DicionarioDTO) document.getBean();
//
//		LinguaService linguaService = (LinguaService) ServiceLocator.getInstance().getService(LinguaService.class, null);
//
//		LinguaDTO linguaDto = new LinguaDTO();
//
//		Collection<DicionarioDTO> listaDicionario = null;
//
//		if (dicionario.getIdLingua() != null) {
//
//			listaDicionario = dicionarioService.listaDicionario(dicionario);
//		}
//
//
//		Element tagDicionarioSuperior = new Element("dicionarioMensagens");
//
//		Element tagDicionario = new Element("dicionario");
//
//
//		if (listaDicionario != null && !listaDicionario.isEmpty()) {
//
//			for (DicionarioDTO dicionarioDto : listaDicionario) {
//
//				linguaDto.setIdLingua(dicionarioDto.getIdLingua());
//
//				linguaDto = (LinguaDTO) linguaService.restore(linguaDto);
//
//				Element tagMensagens = new Element("mensagens");
//				Element idLingua = new Element("idLingua");
//				Element keyMensagens = new Element("key");
//				Element valor = new Element("valor");
//
//				idLingua.setText(dicionarioDto.getIdLingua().toString());
//				keyMensagens.setText(dicionarioDto.getNome());
//				valor.setText(dicionarioDto.getValor());
//
//				tagMensagens.addContent(idLingua);
//				tagMensagens.addContent(keyMensagens);
//				tagMensagens.addContent(valor);
//
//				tagDicionario.addContent(tagMensagens);
//
//
//			}
//		}else{
//			document.alert(UtilI18N.internacionaliza(request, "dicionario.precisoGerarMensagens"));
//			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
//			return;
//		}
//
//		tagDicionarioSuperior.addContent(tagDicionario);
//
//		Document doc = new Document();
//
//		doc.setRootElement(tagDicionarioSuperior);
//
//		try {
//			String separator = System.getProperty("file.separator");
//
//			String diretorioReceita = CITCorporeUtil.caminho_real_app + separator + "tempFiles" + separator;
//
//
//
//			File file = new File(diretorioReceita + "dicionario"+linguaDto.getSigla()+".xml");
//
//			Writer out = new OutputStreamWriter(new FileOutputStream(file));
//
//			XMLOutputter xout = new XMLOutputter();
//
//			xout.setFormat(Format.getCompactFormat().setEncoding("ISO-8859-1"));
//
//			xout.output(doc, out);
//
//
//			int cont;
//			byte[] dados = new byte[TAMANHO_BUFFER];
//
//			BufferedInputStream origem = null;
//			FileInputStream streamDeEntrada = null;
//			FileOutputStream destino = null;
//			ZipOutputStream saida = null;
//			ZipEntry entry = null;
//
//
//				 destino = new FileOutputStream(new File(diretorioReceita+ "dicionario" +linguaDto.getSigla()+ ".zip"));
//				 saida = new ZipOutputStream(new BufferedOutputStream(destino));
//				 streamDeEntrada = new FileInputStream(file);
//				 origem = new BufferedInputStream(streamDeEntrada, TAMANHO_BUFFER);
//				 entry = new ZipEntry(file.getName());
//				 saida.putNextEntry(entry);
//
//
//				 while((cont = origem.read(dados, 0, TAMANHO_BUFFER)) != -1) {
//		                saida.write(dados, 0, cont);
//		          }
//				 origem.close();
//		         saida.close();
//
//
//			document.alert(UtilI18N.internacionaliza(request, "menu.criarXml"));
//
//			document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url="
//					+ Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles" + "/dicionario"+linguaDto.getSigla()+".zip')");
//
//			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
//
//		} catch (UnsupportedEncodingException e) {
//
//			e.printStackTrace();
//
//		} catch (IOException e) {
//
//			e.printStackTrace();
//
//		}

//	}

//	public void importarDicionarioXml(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
//
//		DicionarioService dicionarioService = (DicionarioService) ServiceLocator.getInstance().getService(DicionarioService.class, null);
//
//		DicionarioDTO dicionario = (DicionarioDTO) document.getBean();
//
//		LinguaService linguaService = (LinguaService) ServiceLocator.getInstance().getService(LinguaService.class, null);
//
//		LinguaDTO linguaDto = new LinguaDTO();
//
//		if (dicionario.getIdLingua() != null) {
//
//			linguaDto.setIdLingua(dicionario.getIdLingua());
//
//			linguaDto = (LinguaDTO) linguaService.restore(linguaDto);
//		}
//
//		String separator = System.getProperty("file.separator");
//
//		String diretorioReceita = CITCorporeUtil.caminho_real_app + "XMLs"  + separator;
//
//		File file = new File(diretorioReceita + "dicionario"+linguaDto.getSigla()+".xml");
//
//		dicionarioService.gerarCarga(file);
//
//		document.alert(UtilI18N.internacionaliza(request, "dicionario.diconarioAtualizado"));
//
//		this.montarTabelaDicionario(document, request, response);
//
//		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
//	}


}
