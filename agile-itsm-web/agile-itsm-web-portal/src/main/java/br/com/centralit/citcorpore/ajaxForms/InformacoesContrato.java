package br.com.centralit.citcorpore.ajaxForms;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsAbstractExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.fill.JRAbstractLRUVirtualizer;
import net.sf.jasperreports.engine.fill.JRSwapFileVirtualizer;
import net.sf.jasperreports.engine.util.JRSwapFile;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.AcordoNivelServicoContratoDTO;
import br.com.centralit.citcorpore.bean.AcordoNivelServicoDTO;
import br.com.centralit.citcorpore.bean.AcordoServicoContratoDTO;
import br.com.centralit.citcorpore.bean.AtividadePeriodicaDTO;
import br.com.centralit.citcorpore.bean.AtividadesOSDTO;
import br.com.centralit.citcorpore.bean.AtividadesServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ClienteDTO;
import br.com.centralit.citcorpore.bean.ComplexidadeDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.ContratoQuestionariosDTO;
import br.com.centralit.citcorpore.bean.ContratosGruposDTO;
import br.com.centralit.citcorpore.bean.EventoDTO;
import br.com.centralit.citcorpore.bean.ExecucaoAtividadePeriodicaDTO;
import br.com.centralit.citcorpore.bean.FaturaApuracaoANSDTO;
import br.com.centralit.citcorpore.bean.FaturaDTO;
import br.com.centralit.citcorpore.bean.FornecedorDTO;
import br.com.centralit.citcorpore.bean.GlosaOSDTO;
import br.com.centralit.citcorpore.bean.GrupoAtvPeriodicaDTO;
import br.com.centralit.citcorpore.bean.InformacoesContratoConfigDTO;
import br.com.centralit.citcorpore.bean.InformacoesContratoDTO;
import br.com.centralit.citcorpore.bean.MoedaDTO;
import br.com.centralit.citcorpore.bean.OSDTO;
import br.com.centralit.citcorpore.bean.ResultadosEsperadosDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.bean.TipoEventoServicoDTO;
import br.com.centralit.citcorpore.bean.TipoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.bean.ValoresServicoContratoDTO;
import br.com.centralit.citcorpore.negocio.AcordoNivelServicoContratoService;
import br.com.centralit.citcorpore.negocio.AcordoNivelServicoService;
import br.com.centralit.citcorpore.negocio.AcordoServicoContratoService;
import br.com.centralit.citcorpore.negocio.AtividadePeriodicaService;
import br.com.centralit.citcorpore.negocio.AtividadesOSService;
import br.com.centralit.citcorpore.negocio.AtividadesServicoContratoService;
import br.com.centralit.citcorpore.negocio.ClienteService;
import br.com.centralit.citcorpore.negocio.ContratoQuestionariosService;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.ContratosGruposService;
import br.com.centralit.citcorpore.negocio.ExecucaoAtividadePeriodicaService;
import br.com.centralit.citcorpore.negocio.FaturaApuracaoANSService;
import br.com.centralit.citcorpore.negocio.FaturaService;
import br.com.centralit.citcorpore.negocio.FornecedorService;
import br.com.centralit.citcorpore.negocio.GlosaOSService;
import br.com.centralit.citcorpore.negocio.GrupoAssinaturaService;
import br.com.centralit.citcorpore.negocio.GrupoAtvPeriodicaService;
import br.com.centralit.citcorpore.negocio.InformacoesContratoConfigService;
import br.com.centralit.citcorpore.negocio.InformacoesContratoPerfSegService;
import br.com.centralit.citcorpore.negocio.MoedaService;
import br.com.centralit.citcorpore.negocio.OSService;
import br.com.centralit.citcorpore.negocio.ProgramacaoAtividadeService;
import br.com.centralit.citcorpore.negocio.ServicoContratoService;
import br.com.centralit.citcorpore.negocio.ServicoService;
import br.com.centralit.citcorpore.negocio.TipoEventoServicoService;
import br.com.centralit.citcorpore.negocio.TipoServicoService;
import br.com.centralit.citcorpore.negocio.ValoresServicoContratoService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.Util;
import br.com.centralit.citcorpore.util.UtilRelatorio;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.Html2Pdf;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilFormatacao;
import br.com.citframework.util.UtilHTML;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
public class InformacoesContrato extends AjaxFormAction {

    private int contLinha = 0;
    private double glosaTotal = 0;
    private double custoTotalOs = 0;
    private ServicoContratoDTO servicoContratoDTO = new ServicoContratoDTO();

    @Override
    public Class getBeanClass() {
	return InformacoesContratoDTO.class;
    }

    public void imprimirConteudoDiv(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute(Constantes.getValue("USUARIO_SESSAO"));
	if (usuario == null) {
	    document.alert("O usuário não está logado! Favor logar no sistema!");
	    return;
	}

	InformacoesContratoDTO contratoDto = (InformacoesContratoDTO) document.getBean();
	usuario.getIdEmpresa();
	usuario.getNomeUsuario();

	String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempReceitas/" + usuario.getIdEmpregado();
	String diretorioRelativoReceita = Constantes.getValue("DIRETORIO_TEMPORARIO_RECEITAS_RELATIVO") + "/" + usuario.getIdEmpregado();
	File f = new File(diretorioReceita);
	if (!f.exists()) {
	    f.mkdirs();
	}

	String arquivoForm = diretorioReceita + "/infoPaciente.pdf";
	String arquivoRelativoFormRefact = diretorioRelativoReceita + "/infoPaciente.pdf";
	f = new File(arquivoForm);
	if (f.exists()) {
	    f.delete();
	}
	String strImprimir = "";
	strImprimir += "<br>Contrato: " + contratoDto.getNumeroContrato();
	strImprimir += contratoDto.getConteudoDivImprimir();

	OutputStream os = new FileOutputStream(arquivoForm);
	if ((strImprimir == null) || strImprimir.trim().equalsIgnoreCase("")) {
	    strImprimir = "<p>&nbsp;</p>"; // Este item eh para nao gerar erro
	    // no comando abaixo.
	}
	strImprimir = UtilHTML.encodeHTML(strImprimir);
	Html2Pdf.convert(strImprimir, os, "<style>@page {size: 4.18in 6.88in; margin: 30px 20px 15px 35px; size:landscape;}</style>");
	os.close();

	document.executeScript("window.open('" + arquivoRelativoFormRefact + "')");
    }

    public void setaContrato(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	InformacoesContratoDTO informacoesDto = (InformacoesContratoDTO) document.getBean();
	request.getSession(true).setAttribute("NUMERO_CONTRATO_EDICAO", "" + informacoesDto.getIdContrato());
    }

    /**
     * Exclui serviços do contrato de acordo com a seleção efetuada pelo usuário em tela.
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     * @author cledson.junior
     */
    public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	try {
	    List<ServicoContratoDTO> listTeste = (List) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(ServicoContratoDTO.class, "servicosContratosSerializadas", request);
	    ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
	    Iterator i = new ArrayList(listTeste).iterator();

	    for(ServicoContratoDTO servicoContrato : listTeste){
		servicoContrato = (ServicoContratoDTO) servicoContratoService.restore(servicoContrato);
		boolean seExisteSolicitacao = servicoContratoService.verificaSeExisteSolicitacaoAbertaVinculadoComServico(servicoContrato.getIdServico(), servicoContrato.getIdContrato());
		if(seExisteSolicitacao){
		    document.executeScript("fechar_aguarde();");
		    document.executeScript("showServicosContrato('" + servicoContrato.getIdContrato() + "');");
		    document.alert(UtilI18N.internacionaliza(request, "informacoesContrato.servicoNaoPodeSerRemovido"));
		    return;
		}
	    }

	    while (i.hasNext()) {
		this.setServicoContratoDTO((ServicoContratoDTO) i.next());
		System.out.println(this.getServicoContratoDTO());
		servicoContratoService.deletarByIdServicoContrato(this.getServicoContratoDTO(), document);
	    }
	    document.executeScript("fechar_aguarde();");
	    document.executeScript("showServicosContrato('" + this.getServicoContratoDTO().getIdContrato() + "');");
	} catch (Exception e) {
	    e.printStackTrace();
	    document.executeScript("fechar_aguarde();");
	}
    }

    @Override
    public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	UsuarioDTO user = WebUtil.getUsuario(request);
	if (user == null) {
	    document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
	    document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
	    return;
	}
	InformacoesContratoDTO informacoesDto = (InformacoesContratoDTO) document.getBean();

	request.getSession(true).setAttribute("TEMP_LISTA_CERTIFICADO_DIGITAL", null);
	request.getSession(true).setAttribute("NUMERO_CONTRATO_EDICAO", null);

	InformacoesContratoConfig prontuarioConfig = InformacoesContratoConfig.getInstance(user);
	Collection colItens = prontuarioConfig.getProntuarioItens();

	request.setAttribute("itensProntuario", colItens);

	request.setAttribute("idContrato", "");
	request.setAttribute("numeroContrato", "");
	request.setAttribute("funcaoStart", "");

	if ((request.getSession(true).getAttribute("idAdministracaoContrato") != null) && (request.getSession(true).getAttribute("numeroContratoAdministracaoContrato") != null)) {
	    document.executeScript("document.getElementById('nomeContrato').value='" + request.getSession(true).getAttribute("numeroContratoAdministracaoContrato").toString() + "'");
	    document.executeScript("document.getElementById('idContrato').value=" + request.getSession(true).getAttribute("idAdministracaoContrato"));

	}

	if (informacoesDto.getIdContrato() != null) {
	    request.setAttribute("idContrato", "" + informacoesDto.getIdContrato());
	    request.setAttribute("numeroContrato", informacoesDto.getNumeroContrato());
	    String numero = informacoesDto.getNumeroContrato();
	    numero = numero.replaceAll("'", "");
	    numero = numero.replaceAll("\\'", "");

	    request.setAttribute("funcaoStart", "LOOKUP_CONTRATOS_select(" + informacoesDto.getIdContrato() + ",'" + numero + "');");
	}

	document.executeScript("JANELA_AGUARDE_MENU.hide();$('#loading_overlay').hide();");
	if(request.getParameter("portal")!=null){
	    ContratosGruposService contratoGrupoContratoService = (ContratosGruposService) ServiceLocator.getInstance().getService(ContratosGruposService.class, null);
	    ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
	    List<ContratosGruposDTO> retorno = (List) contratoGrupoContratoService.findByGrupos(user.getColGrupos());
	    Integer idContratoGrupo = null;
	    if(retorno != null){
		for(ContratosGruposDTO dadosGrupo : retorno){
		    idContratoGrupo = dadosGrupo.getIdContrato();
		    break;
		}
		ContratoDTO contratoDto = new ContratoDTO();
		contratoDto.setIdContrato(idContratoGrupo);
		contratoDto = (ContratoDTO) contratoService.restore(contratoDto);
		document.executeScript("acaoD('"+idContratoGrupo+"', '"+contratoDto.getNumero()+"')");
	    }
	}
	document.getElementById("nomeContrato").setValue("");
	document.getElementById("idContrato").setValue("");

    }

    public void listarSLAsContrato(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	InformacoesContratoDTO informacoesDto = (InformacoesContratoDTO) document.getBean();
	if (informacoesDto.getIdServicoContrato() == null) {
	    document.alert("Não foi identificado o Identificador do Serviço Contrato!");
	    return;
	}
	AcordoNivelServicoService acordoNivelServicoService = (AcordoNivelServicoService) ServiceLocator.getInstance().getService(AcordoNivelServicoService.class, null);
	AcordoServicoContratoService acordoServicoContratoService = (AcordoServicoContratoService) ServiceLocator.getInstance().getService(AcordoServicoContratoService.class, null);
	Collection col = acordoNivelServicoService.findByIdServicoContrato(informacoesDto.getIdServicoContrato());
	Collection colVincs = acordoServicoContratoService.findByIdServicoContrato(informacoesDto.getIdServicoContrato());
	String table = "";

	table += "<table class='table table-bordered table-striped'>";
	table += "<tr>";
	table += "<td>";
	table += "<button type='button' name='btnVincSLAServCont_" + informacoesDto.getIdServicoContrato() + "' class='light'  onclick='vincularSLA(" + informacoesDto.getIdServicoContrato() + ")'>";
	table += "<img src='" + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") + "/template_new/images/icons/small/grey/plane_suitcase.png'>";
	table += "<span>" + UtilI18N.internacionaliza(request, "citcorpore.comum.vincularAcordo") + "</span>";
	table += "</button>";
	table += "</td>";

	table += "<td>";
	table += "<button type='button' name='btnAddSLAServCont_" + informacoesDto.getIdServicoContrato() + "' class='light'  onclick='adicionarSLA(" + informacoesDto.getIdServicoContrato() + ")'>";
	table += "<img src='" + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") + "/template_new/images/icons/small/grey/plane_suitcase.png'>";
	table += "<span>" + UtilI18N.internacionaliza(request, "citcorpore.comum.criarAcordoEspecifico") + "</span>";
	table += "</button>";
	table += "</td>";
	table += "</tr>";
	table += "</table>";

	table += "<table class='table table-bordered table-striped' width='100%'>";
	table += "<thead>";
	table += "<tr class='th'>";
	table += "<td>";
	table += UtilI18N.internacionaliza(request, "plano.melhoria.editar");
	table += "</td>";
	table += "<td>";
	table += UtilI18N.internacionaliza(request, "citcorpore.comum.vinculo");
	table += "</td>";
	table += "<td>";
	table += UtilI18N.internacionaliza(request, "citcorpore.comum.tituloSLA");
	table += "</td>";
	table += "<td>";
	table += UtilI18N.internacionaliza(request, "citcorpore.comum.tipo");
	table += "</td>";
	table += "<td>";
	table += UtilI18N.internacionaliza(request, "citcorpore.comum.situacao");
	table += "</td>";
	table += "<td>";
	table += UtilI18N.internacionaliza(request, "citcorpore.comum.datainicio");
	table += "</td>";
	table += "<td>";
	table += UtilI18N.internacionaliza(request, "citcorpore.comum.datafim");
	table += "</td>";
	table += "<td>";
	table += UtilI18N.internacionaliza(request, "sla.avaliarem");
	table += "</td>";
	table += "<td>";
	table += "";
	table += "<td>";
	table += "</tr>";
	table += "</thead>";
	table += "</tbody>";
	if (((col != null) && (col.size() > 0)) || ((colVincs != null) && (colVincs.size() > 0))) {
	    if (col == null) {
		col = new ArrayList(); // Isto eh so para nao gerar erro no for
		// abaixo.
	    }
	    if (colVincs == null) {
		colVincs = new ArrayList(); // Isto eh so para nao gerar erro no
		// for abaixo.
	    }
	    boolean flag = false;
	    for (Iterator it = col.iterator(); it.hasNext();) {
		AcordoNivelServicoDTO acordoNivelServicoDTO = (AcordoNivelServicoDTO) it.next();
		if ((acordoNivelServicoDTO.getDeleted() != null) && !acordoNivelServicoDTO.getDeleted().equalsIgnoreCase("N")) {
		    continue;
		}

		/* Flag para tratamento dos SLAs globais */
		if ((acordoNivelServicoDTO.getTipo() != null) && acordoNivelServicoDTO.getTipo().equalsIgnoreCase("T") && acordoNivelServicoDTO.getSituacao().equals("A")) {
		    flag = true;
		}

		table += "<tr>";
		table += "<td>";
		table += "<img src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")
			+ "/imagens/write.png' border='0' style='cursor:pointer' title='" + UtilI18N.internacionaliza(request, "citcorpore.comum.editarAcordo") + "' onclick='editarSLA("
			+ acordoNivelServicoDTO.getIdAcordoNivelServico() + "," + acordoNivelServicoDTO.getIdServicoContrato() + ")'/>";
		table += "</td>";
		table += "<td>";
		table += "<img src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")
			+ "/imagens/semvinculo.png' border='0'  title='" + UtilI18N.internacionaliza(request, "citcorpore.comum.SLASemVinculacao") + "'/>";
		table += UtilI18N.internacionaliza(request, "citcorpore.comum.especifico");
		table += "</td>";
		table += "<td>";
		table += acordoNivelServicoDTO.getTituloSLA();
		table += "</td>";
		table += "<td>";
		if ((acordoNivelServicoDTO.getTipo() != null) && acordoNivelServicoDTO.getTipo().equalsIgnoreCase("T")) {
		    table += "<img src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")
			    + "/imagens/relogio.png' border='0'  title='" + UtilI18N.internacionaliza(request, "sla.tipo.tempo") + "'/>";
		    table += UtilI18N.internacionaliza(request, "sla.tipo.tempo");
		} else if ((acordoNivelServicoDTO.getTipo() != null) && acordoNivelServicoDTO.getTipo().equalsIgnoreCase("D")) {
		    table += "<img src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")
			    + "/imagens/servidorup.png' border='0'  title='" + UtilI18N.internacionaliza(request, "sla.tipo.disponibilidade") + "'/>";
		    table += UtilI18N.internacionaliza(request, "sla.tipo.disponibilidade");
		} else if ((acordoNivelServicoDTO.getTipo() != null) && acordoNivelServicoDTO.getTipo().equalsIgnoreCase("V")) {
		    table += "<img src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")
			    + "/imagens/outrasfontes.png' border='0'  title='" + UtilI18N.internacionaliza(request, "sla.tipo.outros") + "'/>";
		    table += UtilI18N.internacionaliza(request, "sla.tipo.outros");
		}
		table += "</td>";
		table += "<td>";

		if ((acordoNivelServicoDTO.getDataFim() != null) && acordoNivelServicoDTO.getDataFim().before(UtilDatas.getDataAtual())) {
		    table += "<img src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")
			    + "/imagens/bolavermelha.png' border='0'  title='" + UtilI18N.internacionaliza(request, "sla.avaliacao.inativo") + "'/>";
		    table += UtilI18N.internacionaliza(request, "sla.inativo");
		} else if (acordoNivelServicoDTO.getSituacao().equalsIgnoreCase("I")) {
		    table += "<img src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")
			    + "/imagens/bolavermelha.png' border='0'  title='" + UtilI18N.internacionaliza(request, "sla.avaliacao.inativo") + "'/>";
		    table += UtilI18N.internacionaliza(request, "sla.inativo");
		} else {
		    table += "<img src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")
			    + "/imagens/bolaverde.png' border='0'  title='" + UtilI18N.internacionaliza(request, "sla.avaliacao.ativo") + "'/>";
		    table += UtilI18N.internacionaliza(request, "sla.ativo");
		}

		table += "</td>";
		table += "<td>";
		table += UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, acordoNivelServicoDTO.getDataInicio(), WebUtil.getLanguage(request));
		table += "</td>";
		table += "<td>";
		table += UtilStrings.nullToVazio(UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, acordoNivelServicoDTO.getDataFim(), WebUtil.getLanguage(request)));
		table += "</td>";
		table += "<td>";
		table += UtilStrings.nullToVazio(UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, acordoNivelServicoDTO.getAvaliarEm(), WebUtil.getLanguage(request)));
		table += "</td>";
		table += "<td>";
		table += "<img src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")
			+ "/imagens/copy.png' border='0' style='cursor:pointer' title='" + UtilI18N.internacionaliza(request, "citcorpore.comum.copiarAcordoOutroServico") + "' onclick='copiarSLA(\""
			+ acordoNivelServicoDTO.getTituloSLA() + "\"," + acordoNivelServicoDTO.getIdAcordoNivelServico() + "," + acordoNivelServicoDTO.getIdServicoContrato() + ")'/>";
		table += "</td>";
		table += "<td>";
		table += "</tr>";
	    }
	    for (Iterator it = colVincs.iterator(); it.hasNext();) {
		AcordoServicoContratoDTO acordoServicoContratoDTO = (AcordoServicoContratoDTO) it.next();
		if ((acordoServicoContratoDTO.getDeleted() != null) && !acordoServicoContratoDTO.getDeleted().equalsIgnoreCase("N")) {
		    continue;
		}
		AcordoNivelServicoDTO acordoNivelServicoDTO = new AcordoNivelServicoDTO();
		acordoNivelServicoDTO.setIdAcordoNivelServico(acordoServicoContratoDTO.getIdAcordoNivelServico());
		acordoNivelServicoDTO = (AcordoNivelServicoDTO) acordoNivelServicoService.restore(acordoNivelServicoDTO);
		if (acordoNivelServicoDTO == null) {
		    continue;
		}
		Date dataFim = null;
		if ((acordoNivelServicoDTO.getDataFim() != null) && (acordoServicoContratoDTO.getDataFim() != null)) {
		    if (acordoNivelServicoDTO.getDataFim().before(acordoServicoContratoDTO.getDataFim())) {
			dataFim = acordoNivelServicoDTO.getDataFim();
		    } else {
			dataFim = acordoServicoContratoDTO.getDataFim();
		    }
		} else {
		    if (acordoNivelServicoDTO.getDataFim() != null) {
			dataFim = acordoNivelServicoDTO.getDataFim();
		    }
		    if (acordoServicoContratoDTO.getDataFim() != null) {
			dataFim = acordoServicoContratoDTO.getDataFim();
		    }
		}
		boolean classe = ((acordoServicoContratoDTO.getHabilitado() != null) && acordoServicoContratoDTO.getHabilitado().equalsIgnoreCase("S") ? true : false);
		String clasS = ((classe && (acordoNivelServicoDTO.getTipo() != null) && acordoNivelServicoDTO.getTipo().equalsIgnoreCase("T")) ? "tr-sel" : "");

		if (flag) {
		    clasS = "";
		}

		table += "<tr class='" + clasS + "'>";
		table += "<td>";
		table += "<img src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")
			+ "/imagens/write.png' border='0' style='cursor:pointer' title='" + UtilI18N.internacionaliza(request, "citcorpore.comum.editarAcordo") + "' onclick='editarVincSLA("
			+ acordoServicoContratoDTO.getIdAcordoServicoContrato() + "," + acordoServicoContratoDTO.getIdServicoContrato() + ")'/>";

		/* Se existir acordo específico não mostra a administração dos vinculos */
		if (!flag) {
		    if ((acordoNivelServicoDTO.getTipo() != null) && acordoNivelServicoDTO.getTipo().equalsIgnoreCase("T") && !((dataFim != null) && dataFim.before(UtilDatas.getDataAtual()))) {
			if ((acordoServicoContratoDTO.getHabilitado() != null) && acordoServicoContratoDTO.getHabilitado().equalsIgnoreCase("S")) {
			    table += "<input type='button' value='" + UtilI18N.internacionaliza(request, "sla.desabilita") + "' " + "onclick='desabilitarVinculoAtivo("
				    + acordoServicoContratoDTO.getIdAcordoServicoContrato() + "," + acordoServicoContratoDTO.getIdServicoContrato() + ")'/>";
			} else {
			    table += "<input type='button' value='" + UtilI18N.internacionaliza(request, "sla.habilita") + "' " + "onclick='habilitarVinculoAtivo("
				    + acordoServicoContratoDTO.getIdAcordoServicoContrato() + "," + acordoServicoContratoDTO.getIdServicoContrato() + ")'/> ";
			}
		    }
		}

		table += "</td>";
		table += "<td>";
		table += "<img src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")
			+ "/imagens/vinculo.png' border='0'  title='" + UtilI18N.internacionaliza(request, "citcorpore.comum.SLAvinculadoServico") + "'/>";
		table += UtilI18N.internacionaliza(request, "citcorpore.comum.global");
		table += "</td>";
		table += "<td>";
		table += acordoNivelServicoDTO.getTituloSLA();
		table += "</td>";
		table += "<td>";
		if ((acordoNivelServicoDTO.getTipo() != null) && acordoNivelServicoDTO.getTipo().equalsIgnoreCase("T")) {
		    table += "<img src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")
			    + "/imagens/relogio.png' border='0'  title='" + UtilI18N.internacionaliza(request, "sla.tipo.tempo") + "'/>";
		    table += UtilI18N.internacionaliza(request, "sla.tipo.tempo");
		} else if ((acordoNivelServicoDTO.getTipo() != null) && acordoNivelServicoDTO.getTipo().equalsIgnoreCase("D")) {
		    table += "<img src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")
			    + "/imagens/servidorup.png' border='0'  title='" + UtilI18N.internacionaliza(request, "sla.tipo.disponibilidade") + "'/>";
		    table += UtilI18N.internacionaliza(request, "sla.tipo.disponibilidade");
		} else if ((acordoNivelServicoDTO.getTipo() != null) && acordoNivelServicoDTO.getTipo().equalsIgnoreCase("V")) {
		    table += "<img src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")
			    + "/imagens/outrasfontes.png' border='0'  title='" + UtilI18N.internacionaliza(request, "sla.tipo.outros") + "'/>";
		    table += UtilI18N.internacionaliza(request, "sla.tipo.outros");
		}
		table += "</td>";
		table += "<td>";
		if ((dataFim != null) && dataFim.before(UtilDatas.getDataAtual())) {
		    table += "<img src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")
			    + "/imagens/bolavermelha.png' border='0'  title='" + UtilI18N.internacionaliza(request, "sla.avaliacao.inativo") + "'/>";
		    table += UtilI18N.internacionaliza(request, "sla.inativo");
		} else {
		    table += "<img src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")
			    + "/imagens/bolaverde.png' border='0'  title='" + UtilI18N.internacionaliza(request, "sla.avaliacao.ativo") + "'/>";
		    table += UtilI18N.internacionaliza(request, "sla.ativo");
		}
		table += "</td>";
		table += "<td>";
		table += UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, acordoServicoContratoDTO.getDataInicio(), WebUtil.getLanguage(request));
		table += "</td>";
		table += "<td>";
		table += UtilStrings.nullToVazio(UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, dataFim, WebUtil.getLanguage(request)));
		table += "</td>";
		table += "<td>";
		table += UtilStrings.nullToVazio(UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, acordoNivelServicoDTO.getAvaliarEm(), WebUtil.getLanguage(request)));
		table += "</td>";
		table += "<td>";
		table += "";
		table += "</td>";
		table += "<td>";
		table += "</tr>";
	    }
	} else {
	    table += "<tr>";
	    table += "<td colspan='10'>";
	    table += "<b>" + UtilI18N.internacionaliza(request, "citcorpore.comum.naoHaAcordosAssociados") + "</b>";
	    table += "</td>";
	    table += "</tr>";
	}
	table += "</tbody>";
	table += "</table>";

	document.getElementById("divContratoServico_" + informacoesDto.getIdServicoContrato()).setInnerHTML(table);
    }

    public void listarAtividadesContrato(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	InformacoesContratoDTO informacoesDto = (InformacoesContratoDTO) document.getBean();
	if (informacoesDto.getIdServicoContrato() == null) {
	    document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.naoIdentificadorServico"));
	    return;
	}
	AtividadesServicoContratoService atividadesServicoContratoService = (AtividadesServicoContratoService) ServiceLocator.getInstance().getService(AtividadesServicoContratoService.class, null);
	Collection col = atividadesServicoContratoService.obterAtividadesAtivasPorIdServicoContrato(informacoesDto.getIdServicoContrato());
	String table = "";

	table += "<table class='table table-bordered table-striped'><tr><td>";
	table += "<button type='button' name='btnAddAtvServCont_" + informacoesDto.getIdServicoContrato() + "' class='light'  onclick='adicionarAtividade(" + informacoesDto.getIdServicoContrato()
		+ ")'>";
	table += "<img src='" + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") + "/template_new/images/icons/small/grey/plane_suitcase.png'>";
	table += "<span>" + UtilI18N.internacionaliza(request, "citcorpore.comum.novaAtividade") + "</span>";
	table += "</button>";
	table += "</td></tr></table>";

	table += "<table class='table table-bordered table-striped'>";
	if ((col != null) && (col.size() > 0)) {
	    for (Iterator it = col.iterator(); it.hasNext();) {
		AtividadesServicoContratoDTO atividadesServicoContratoDTO = (AtividadesServicoContratoDTO) it.next();
		table += "<tr>";
		table += "<td style='width: 3% !important;'>";
		table += "<img src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")
			+ "/imagens/write.png' border='0' style='cursor:pointer' title='"+UtilI18N.internacionaliza(request,"informacoesContrato.editarAtividade")+"' onclick='editarAtividade(" + atividadesServicoContratoDTO.getIdAtividadeServicoContrato()
			+ "," + atividadesServicoContratoDTO.getIdServicoContrato() + ")'/>";
		table += "</td>";
		table += "<td>";
		table += atividadesServicoContratoDTO.getDescricaoAtividade();
		table += "</td>";
		table += "</tr>";
	    }
	} else {
	    table += "<tr>";
	    table += "<td>";
	    table += "<b>" + UtilI18N.internacionaliza(request, "citcorpore.comum.naoHaAtividadesAssociadas") + "</b>";
	    table += "</td>";
	    table += "</tr>";
	}
	table += "</table>";

	document.getElementById("divContratoServico_" + informacoesDto.getIdServicoContrato()).setInnerHTML(table);
    }

    public void listarValoresContrato(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	InformacoesContratoDTO informacoesDto = (InformacoesContratoDTO) document.getBean();
	if (informacoesDto.getIdServicoContrato() == null) {
	    document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.naoIdentificadorServico"));
	    return;
	}
	ValoresServicoContratoService valoresServicoContratoService = (ValoresServicoContratoService) ServiceLocator.getInstance().getService(ValoresServicoContratoService.class, null);
	Collection col = valoresServicoContratoService.obterValoresAtivosPorIdServicoContrato(informacoesDto.getIdServicoContrato());
	String table = "";

	if (!valoresServicoContratoService.existeAtivos(informacoesDto.getIdServicoContrato())) {
	    table += "<table class='table table-bordered table-striped'><tr><td>";
	    table += "<button type='button' name='btnAddAtvServCont_" + informacoesDto.getIdServicoContrato() + "' class='light'  onclick='adicionarValorServico("
		    + informacoesDto.getIdServicoContrato() + ")'>";
	    table += "<img src='" + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") + "/template_new/images/icons/small/grey/plane_suitcase.png'>";
	    table += "<span>"+UtilI18N.internacionaliza(request, "informacoesContrato.adicionarNovoValor")+"</span>";
	    table += "</button>";
	    table += "</td></tr></table>";
	}
	table += "<table class='table table-bordered table-striped' width='50%'>";
	table += "<thead>";
	table += "<tr class='th'>";
	table += "<td></td>";
	table += "<td>"+UtilI18N.internacionaliza(request, "informacoesContrato.valorServico")+"</td>";
	table += "<td>"+UtilI18N.internacionaliza(request, "citcorpore.comum.datainicio")+"</td>";
	table += "<td>"+UtilI18N.internacionaliza(request, "citcorpore.comum.datafim")+"</td>";
	table += "</tr>";
	table += "</thead>";
	table += "<tbody>";
	if ((col != null) && (col.size() > 0)) {
	    for (Iterator it = col.iterator(); it.hasNext();) {
		ValoresServicoContratoDTO valoresServicoContratoDTO = (ValoresServicoContratoDTO) it.next();
		table += "<tr>";
		table += "<td>";
		if (valoresServicoContratoDTO.getDataFim() == null) {
		    table += "<img src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")
			    + "/imagens/write.png' border='0' style='cursor:pointer; height: 15px;' title='Editar valor serviço' onclick='editarValorServico("
			    + valoresServicoContratoDTO.getIdValorServicoContrato() + "," + valoresServicoContratoDTO.getIdServicoContrato() + ")'/>";
		}
		table += "</td>";
		table += "<td>" + this.retornaValorFormatado(valoresServicoContratoDTO.getValorServico().doubleValue()) + "</td>";
		table += "<td>" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, valoresServicoContratoDTO.getDataInicio(), WebUtil.getLanguage(request)) + "</td>";
		table += "<td>" + (valoresServicoContratoDTO.getDataFim() == null ? "" : UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, valoresServicoContratoDTO.getDataFim(), WebUtil.getLanguage(request))) + "</td>";
		table += "</tr>";
	    }
	} else {
	    table += "<tr>";
	    table += "<td colspan='4'>";
	    table += "<b>"+UtilI18N.internacionaliza(request, "informacoesContrato.naoHaValoresAssociadosServico")+".</b>";
	    table += "</td>";
	    table += "</tr>";
	}
	table += "</tbody>";
	table += "</table>";

	document.getElementById("divContratoServico_" + informacoesDto.getIdServicoContrato()).setInnerHTML(table);
    }

    public void listarOSContrato(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	InformacoesContratoDTO informacoesDto = (InformacoesContratoDTO) document.getBean();
	Integer[] situacao = null;
	Integer[] situacaoOs = null;

	if (informacoesDto.getIdContrato() == null) {
	    document.alert(UtilI18N.internacionaliza(request, "contrato.alerta.informe_contrato"));
	    return;
	}

	Integer funcaoListarOs = null;

	if (!informacoesDto.getFuncaoListarOS().equals("")) {
	    funcaoListarOs = Integer.parseInt(informacoesDto.getFuncaoListarOS());
	}

	if (funcaoListarOs == null) {
	    funcaoListarOs = 1;
	}

	if (funcaoListarOs.equals(OSDTO.EM_CRIACAO) || funcaoListarOs.equals(OSDTO.SOLICITADA) || funcaoListarOs.equals(OSDTO.APROVADA) || funcaoListarOs.equals(OSDTO.AUTORIZADA)
		|| funcaoListarOs.equals(OSDTO.CANCELADA)) {
	    situacaoOs = new Integer[1];
	    if ((informacoesDto.getFuncaoListarOS() != null) && !informacoesDto.getFuncaoListarOS().equals("")) {
		situacaoOs[0] = Integer.parseInt(informacoesDto.getFuncaoListarOS());
	    } else {
		situacaoOs[0] = 1;
	    }
	}

	OSService osService = (OSService) ServiceLocator.getInstance().getService(OSService.class, null);
	ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
	MoedaService moedaService = (MoedaService) ServiceLocator.getInstance().getService(MoedaService.class, null);
	ServiceLocator.getInstance().getService(GlosaOSService.class, null);

	Collection colOSs = null;

	if (funcaoListarOs.equals(OSDTO.CANCELADA)) {
	    colOSs = osService.findByIdContratoAndPeriodoAndSituacao(informacoesDto.getIdContrato(), informacoesDto.getDataOS1(), informacoesDto.getDataOS2(), situacaoOs);
	} else {
	    colOSs = osService.findByIdContratoAndPeriodoAndSituacao(informacoesDto.getIdContrato(), informacoesDto.getDataOS1(), informacoesDto.getDataOS2(), situacaoOs, null);
	}

	ContratoDTO contratoDto = new ContratoDTO();
	contratoDto.setIdContrato(informacoesDto.getIdContrato());
	contratoDto = (ContratoDTO) contratoService.restore(contratoDto);

	String nomeMoeda = "";
	String coricone = "";
	if (contratoDto.getIdMoeda() != null) {
	    MoedaDTO moedaDto = new MoedaDTO();
	    moedaDto.setIdMoeda(contratoDto.getIdMoeda());
	    moedaDto = (MoedaDTO) moedaService.restore(moedaDto);
	    if (moedaDto != null) {
		nomeMoeda = " (" + moedaDto.getNomeMoeda() + ")";
	    }
	}

	String strTable = "<table cellpadding='0' cellspacing='0' width='100%' style='width: 98%' class='table table-bordered table-striped' >";
	strTable += "<tr>";
	strTable += "<td>";
	strTable += "&nbsp;";
	strTable += "</td>";
	strTable += "<td>";
	strTable += UtilI18N.internacionaliza(request, "lookup.numeroOS");
	strTable += "</td>";
	strTable += "<td>";
	strTable += UtilI18N.internacionaliza(request, "citcorpore.comum.servico");
	strTable += "</td>";
	strTable += "<td>";
	strTable += UtilI18N.internacionaliza(request, "citcorpore.comum.datainicio");
	strTable += "</td>";
	strTable += "<td>";
	strTable += UtilI18N.internacionaliza(request, "citcorpore.comum.datafim");
	strTable += "</td>";

	if (!funcaoListarOs.equals(OSDTO.EM_EXECUCAO) && !funcaoListarOs.equals(OSDTO.EXECUTADA)) {
	    strTable += "<td>";
	    strTable += UtilI18N.internacionaliza(request, "citcorpore.comum.situacao");
	    strTable += "</td>";
	}

	strTable += "<td>";
	strTable += UtilI18N.internacionaliza(request, "citcorpore.comum.custo") + nomeMoeda;
	strTable += "</td>";
	strTable += "<td>";
	strTable += UtilI18N.internacionaliza(request, "tipoServico.tipoServico");
	strTable += "</td>";
	strTable += "<td>";
	strTable += UtilI18N.internacionaliza(request, "visao.glosa") + nomeMoeda;
	strTable += "</td>";
	strTable += "</tr>";

	if ((colOSs != null) && (colOSs.size() > 0)) {
	    for (Iterator itOs = colOSs.iterator(); itOs.hasNext();) {
		OSDTO osDto = (OSDTO) itOs.next();
		Collection colOSFilho = null;

		if (((situacaoOs == null) && !(funcaoListarOs.equals(OSDTO.AUTORIZADA)))) {
		    situacao = new Integer[1];
		    situacao[0] = Integer.parseInt(informacoesDto.getFuncaoListarOS());
		    colOSFilho = osService.findByIdContratoAndPeriodoAndSituacao(informacoesDto.getIdContrato(), informacoesDto.getDataOS1(), informacoesDto.getDataOS2(), situacao, osDto.getIdOS());
		} else {
		    if (funcaoListarOs.equals(OSDTO.AUTORIZADA)) {
			situacao = new Integer[2];
			situacao[0] = Integer.parseInt(informacoesDto.getFuncaoListarOS());
			situacao[1] = new Integer(5);
			colOSFilho = osService.findByIdContratoAndPeriodoAndSituacao(informacoesDto.getIdContrato(), informacoesDto.getDataOS1(), informacoesDto.getDataOS2(), situacao,
				osDto.getIdOS());
		    }
		}

		if (funcaoListarOs.equals(OSDTO.TODAS) || funcaoListarOs.equals(OSDTO.CANCELADA)) {
		    colOSFilho = osService.findByIdContratoAndPeriodoAndSituacao(informacoesDto.getIdContrato(), informacoesDto.getDataOS1(), informacoesDto.getDataOS2(), situacaoOs, osDto.getIdOS());
		}

		if ((colOSFilho != null) && (colOSFilho.size() > 0)) {
		    strTable += this.montaTabelaContratoOs(colOSFilho, osDto, coricone, nomeMoeda, funcaoListarOs, request);
		} else {
		    if (!funcaoListarOs.equals(OSDTO.EM_EXECUCAO) && !funcaoListarOs.equals(OSDTO.EXECUTADA)) {
			strTable += this.montaTabelaContratoOs(colOSFilho, osDto, coricone, nomeMoeda, funcaoListarOs, request);
		    }
		}

	    }
	} else {
	    strTable += "<tr>";
	    strTable += "<td>&nbsp;</td>";
	    strTable += "<td colspan='20'>";
	    strTable += "<br/><b>" + UtilI18N.internacionaliza(request, "contrato.naoHaInformacoesCriteriosInformados") + "</b>";
	    strTable += "</td>";
	    strTable += "</tr>";
	}

	strTable += "</table>";
	document.getElementById("divListaOS").setInnerHTML(strTable);
    }

    public boolean verificaSituacaoOSValida(OSDTO osDto) throws Exception {

	ServiceLocator.getInstance().getService(OSService.class, null);
	ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
	ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);

	String mostraOSDataAnterior = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.LIBERAR_ORDEM_SERVICO_DATA_ANTERIOR, "N");

	// valida se existe registro de execução para OS diferentes de Suporte
	if (osDto != null) {
	    ServicoContratoDTO servicoContratoDTO = new ServicoContratoDTO();
	    servicoContratoDTO.setIdServicoContrato(osDto.getIdServicoContrato());
	    servicoContratoDTO = (ServicoContratoDTO) servicoContratoService.restore(servicoContratoDTO);

	    ServicoDTO servicoDto = new ServicoDTO();
	    if (servicoContratoDTO != null) {
		servicoDto.setIdServico(servicoContratoDTO.getIdServico());
	    }
	    servicoDto = (ServicoDTO) servicoService.restore(servicoDto);

	    if ((servicoDto != null) && (servicoContratoDTO != null)) {

		/**
		 * REQUISIÇÃO 115719 - Se colocarmos o parametro com data atual, o mesmo deve mostrar as OS´s com data atual e futuras. Se for escolhido com data retroativa, precisa mostrar além das
		 * atuais e futuras as que possuam as datas antigas.
		 */
		if (mostraOSDataAnterior.trim().equalsIgnoreCase("S")) {
		    return true;
		} else if (!UtilDatas.getDataAtual().after(osDto.getDataFim())) {
		    return true;
		}
	    }
	}
	return false;
    }

    public String montaTabelaContratoOs(Collection colOSFilho, OSDTO osDto, String coricone, String nomeMoeda, Integer funcaoListarOs, HttpServletRequest request) throws ServiceException, Exception {
	OSService osService = (OSService) ServiceLocator.getInstance().getService(OSService.class, null);
	String strTable = "";

	// Verifica se OS Aprovada já existe Registro de Execução ou se OS de
	// Suporte já venceu o prazo estipulado
	if (funcaoListarOs == OSDTO.APROVADA) {
	    boolean flag = this.verificaSituacaoOSValida(osDto);
	    if (!flag) {
		return strTable;
	    }
	}

	if (osDto.getSituacaoOS().equals(OSDTO.CANCELADA)) {
	    strTable += "<tr style='border: none; background:#f5f5f5; color: red;'>";
	    strTable += "<td>";
	} else {
	    strTable += "<tr style='border: none; background:#f5f5f5;'>";
	    strTable += "<td>";
	}

	strTable += "<tr style='border: none; background:#f5f5f5;'>";
	strTable += "<td>";

	if ((colOSFilho != null) && (colOSFilho.size() > 0) && (osDto.getSituacaoOS() != null)) {
	    strTable += "<img style='margin: 1px' src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/go-rt-off.gif' border='0' title='"
		    + UtilI18N.internacionaliza(request, "contrato.expandirOS") + "' onclick='expandirOS(\"" + osDto.getIdOS() + "\",this)' style='cursor:pointer'/>";
	}

	if (!funcaoListarOs.equals(OSDTO.EM_EXECUCAO) && !funcaoListarOs.equals(OSDTO.EXECUTADA) && !funcaoListarOs.equals(OSDTO.CANCELADA) && !funcaoListarOs.equals(OSDTO.TODAS)) {
	    strTable += "<img style='margin: 1px' src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/btnAlterarRegistro.gif' border='0' title='"
		    + UtilI18N.internacionaliza(request, "contrato.editarOS") + "' onclick='editaOS(\"" + osDto.getIdOS() + "\")' style='cursor:pointer'/>";
	}

	if (funcaoListarOs.equals(OSDTO.APROVADA)) {
	    strTable += "<img style='margin: 1px' src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/executarTarefa.png' border='0' title='"
		    + UtilI18N.internacionaliza(request, "contrato.gerarRA") + "' onclick='addRegistroExecucao(\"" + osDto.getIdOS() + "\")' style='cursor:pointer'/>";
	}

	if (!funcaoListarOs.equals(OSDTO.CANCELADA)) {
	    strTable += "<img style='margin: 1px;cursor:pointer;' src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO")
		    + "/imagens/file_pdf.png' border='0' title='" + UtilI18N.internacionaliza(request, "contrato.imprimirOS") + "' onclick='imprimirOS(\"" + osDto.getIdOS()
		    + "\")' style='cursor:pointer;'/>";

	    strTable += "<img style='margin: 1px;cursor:pointer;' src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO")
		    + "/imagens/Excel-icon.png' border='0' title='" + UtilI18N.internacionaliza(request, "contrato.imprimirOS") + "'  onclick='imprimirOSXls(\"" + osDto.getIdOS() + "\")' />";
	}

	strTable += "</td>";
	strTable += "<td>";

	if (osDto.getNumero() != null) {
	    // strTable += osDto.getNumero() + "/" + osDto.getAno();
	    strTable += osDto.getNumero();
	}

	strTable += "</td>";
	strTable += "<td>";
	//necessario para quebra de linha quando em resolução menores
	strTable += Util.converterHtmlText((osDto.getNomeServico()));
	//strTable += UtilHTML.encodeHTML(osDto.getNomeServico());
	strTable += "</td>";
	String strTableFilho = "";
	if ((colOSFilho != null) && (colOSFilho.size() > 0)) {

	    strTableFilho = "<div id='divExpandeOs" + osDto.getIdOS() + "' style='display: none;'>";
	    strTableFilho += "<table class='table table-bordered table-striped'  width='100%'>";
	    strTableFilho += "<tr>";
	    strTableFilho += "<td class='linhaSubtituloGridEmExecucao'>";
	    strTableFilho += UtilI18N.internacionaliza(request, "contrato.RA");
	    strTableFilho += "</td>";
	    strTableFilho += "<td class='linhaSubtituloGridEmExecucao'>";
	    strTableFilho += UtilI18N.internacionaliza(request, "citcorpore.comum.datainicio");
	    strTableFilho += "</td>";
	    strTableFilho += "<td class='linhaSubtituloGridEmExecucao'>";
	    strTableFilho += UtilI18N.internacionaliza(request, "citcorpore.comum.datafim");
	    strTableFilho += "</td>";
	    strTableFilho += "<td class='linhaSubtituloGridEmExecucao'>";
	    strTableFilho += UtilI18N.internacionaliza(request, "citcorpore.comum.situacao");
	    strTableFilho += "</td>";
	    strTableFilho += "<td class='linhaSubtituloGridEmExecucao'>";
	    strTableFilho += UtilI18N.internacionaliza(request, "contrato.numeroVezesExecutada");
	    strTableFilho += "</td>";
	    strTableFilho += "<td class='linhaSubtituloGridEmExecucao'>";
	    strTableFilho += UtilI18N.internacionaliza(request, "citcorpore.comum.custo") + nomeMoeda;
	    strTableFilho += "</td>";
	    strTableFilho += "<td class='linhaSubtituloGridEmExecucao'>";
	    strTableFilho += UtilI18N.internacionaliza(request, "visao.glosa") + nomeMoeda;
	    strTableFilho += "</td>";
	    strTableFilho += "</tr>";

	    this.glosaTotal = 0;
	    this.custoTotalOs = 0;

	    for (Iterator itOsFilho = colOSFilho.iterator(); itOsFilho.hasNext();) {
		OSDTO osFilhoDto = (OSDTO) itOsFilho.next();
		if (osFilhoDto.getSituacaoOS().equals(OSDTO.CANCELADA)) {
		    strTableFilho += "<tr style='color: red;'>";
		} else {
		    strTableFilho += "<tr>";
		}
		strTableFilho += "<td style='padding-left:30px;'>";

		osService.listAtividadePeridodicaByIdos(osFilhoDto.getIdOS());

		if (!osFilhoDto.getSituacaoOS().equals(OSDTO.CANCELADA)) {
		    strTableFilho += "<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/f_moved.gif' border='0' title='"
			    + UtilI18N.internacionaliza(request, "contrato.executarOS") + "' onclick='setaSituacaoOS(\"" + osFilhoDto.getIdOS() + "\", \"" + osFilhoDto.getCustoOS()
			    + "\")' style='cursor:pointer'/>";

		    strTableFilho += "<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/file_pdf.png' border='0' title='"
			    + UtilI18N.internacionaliza(request, "contrato.executarOS") + "' onclick='imprimirRA(\"" + osFilhoDto.getIdOS() + "\")' style='cursor:pointer'/>";

		    strTableFilho += "<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/Excel-icon.png' border='0' title='"
			    + UtilI18N.internacionaliza(request, "contrato.executarOS") + "' onclick='imprimirRAXls(\"" + osFilhoDto.getIdOS() + "\")' style='cursor:pointer'/>";
		    if (!osFilhoDto.getSituacaoOS().equals(OSDTO.EXECUTADA)) {
			strTableFilho += "<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/button_cancel.png' border='0' title='"
				+ UtilI18N.internacionaliza(request, "contrato.excluirRA") + "' onclick='excluiRAOS(\"" + osFilhoDto.getIdOS() + "\", \"" + osFilhoDto.getCustoOS()
				+ "\")' style='cursor:pointer'/>";
		    }
		}

		strTableFilho += coricone;
		strTableFilho += this.tabelaRetornaColunaOs(osFilhoDto, funcaoListarOs, request);
		strTableFilho += "</td>";
		strTableFilho += "</tr>";
	    }
	    strTableFilho += "<tr>";
	    strTableFilho += "<td colspan='4'></td>";
	    strTableFilho += "<td colspan='1' style='font:12px arial,sans-serif; font-weight: bold; text-align:right'>Total" + nomeMoeda + "</td>";
	    strTableFilho += "<td colspan='1'  style='font:12px arial,sans-serif; font-weight: bold; text-align:center'>" + UtilFormatacao.formatDouble(this.custoTotalOs, 2) + "</td>";
	    strTableFilho += "<td colspan='1'></td>";
	    strTableFilho += "</tr>";

	    strTable += this.tabelaRetornaColunaOs(osDto, funcaoListarOs, request);
	    strTable += "</tr>";
	    strTableFilho += "</div>";
	    strTableFilho += "</table>";

	} else {
	    strTable += this.tabelaRetornaColunaOs(osDto, funcaoListarOs, request);
	}

	strTable += "<tr>";
	strTable += "<td  colspan='9' style='border: none;background:#f5f5f5;'>";
	strTable += strTableFilho;

	strTable += "</td>";
	strTable += "</tr>";

	strTable += "<tr style='height: 10px;'";
	strTable += "</tr>";

	return strTable;
    }

    public String tabelaRetornaColunaOs(OSDTO osDto, Integer funcaoListarOs, HttpServletRequest request) throws ServiceException, Exception {
	GlosaOSService glosaOSService = (GlosaOSService) ServiceLocator.getInstance().getService(GlosaOSService.class, null);
	String strTable = "";

	double valorGlosado = 0;
	Collection colGlosasOS = null;
	if (osDto != null) {
	    colGlosasOS = glosaOSService.findByIdOs(osDto.getIdOS());
	}
	if (colGlosasOS != null) {
	    for (Iterator it = colGlosasOS.iterator(); it.hasNext();) {
		GlosaOSDTO glosaOSDTO = (GlosaOSDTO) it.next();
		if (glosaOSDTO.getCustoGlosa() != null) {
		    valorGlosado = valorGlosado + glosaOSDTO.getCustoGlosa().doubleValue();
		}
	    }
	}

	strTable += "<td style='text-align: center;'>";

	if ((osDto != null) && (osDto.getDataInicio() != null)) {
	    strTable += UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, osDto.getDataInicio(), WebUtil.getLanguage(request));
	}

	strTable += "</td>";

	boolean atrasado = false;
	String imagemGifAlerta = "";
	if ((osDto != null) && (osDto.getDataFim() != null)) {
	    if (osDto.getSituacaoOS().equals(OSDTO.EM_EXECUCAO)) {
		if (osDto.getDataFim().before(UtilDatas.getDataAtual())) {
		    atrasado = true;
		    imagemGifAlerta = "<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/b.gif' border='0'/>";
		}
	    }
	}
	strTable += "<td style='text-align: center;'>";

	if ((osDto != null) && (osDto.getDataFim() != null)) {
	    if (atrasado) {
		strTable += imagemGifAlerta+"<font color='red'>  " + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, osDto.getDataFim(), WebUtil.getLanguage(request)) + "</font>";
	    } else {
		strTable += UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, osDto.getDataFim(), WebUtil.getLanguage(request));
	    }
	}

	strTable += "</td>";

	if ((osDto != null) && (osDto.getIdOSPai() == null)) {
	    if (!funcaoListarOs.equals(OSDTO.EM_EXECUCAO) && !funcaoListarOs.equals(OSDTO.EXECUTADA)) {
		if(osDto.getDescricaoSituacaoOS().equalsIgnoreCase("Em Criação")){
		    strTable += "<td style='text-align: center;'>";
		    strTable += UtilI18N.internacionaliza(request, "perfil.criacao");
		    strTable += "</td>";
		} else if(osDto.getDescricaoSituacaoOS().equalsIgnoreCase("Aprovada")){
		    strTable += "<td style='text-align: center;'>";
		    strTable += UtilI18N.internacionaliza(request, "perfil.aprovada");
		    strTable += "</td>";
		} else if(osDto.getDescricaoSituacaoOS().equalsIgnoreCase("Autorizada")){
		    strTable += "<td style='text-align: center;'>";
		    strTable += UtilI18N.internacionaliza(request, "perfil.autorizada");
		    strTable += "</td>";
		} else {
		    if(osDto.getDescricaoSituacaoOS().equalsIgnoreCase("Executada")){
			strTable += "<td style='text-align: center;'>";
			strTable += osDto.getDescricaoSituacaoOS();
			strTable += "</td>";
		    }
		}
	    }
	} else {
	    strTable += "<td style='text-align: center;'>";
	    if (osDto != null) {
		strTable += osDto.getDescricaoSituacaoOS();
	    }
	    strTable += "</td>";
	}

	if ((osDto != null) && (osDto.getIdOSPai() != null)) {
	    strTable += "<td style='text-align: center;'>";
	    if (osDto != null) {
		strTable += osDto.getQuantidade();
	    }
	    strTable += "</td>";
	}

	strTable += "<td style='text-align: center;'>";
	if ((osDto != null) && (osDto.getCustoOS() != null)) {
	    strTable += UtilFormatacao.formatDouble(osDto.getCustoOS(), 2);
	    if (osDto.getIdOSPai() != null) {
		this.custoTotalOs += osDto.getCustoOS();
	    }
	} else {
	    strTable += "--";
	}
	strTable += "</td>";

	if ((osDto != null) && (osDto.getIdOSPai() == null)) {
	    strTable += "<td style='text-align: center;'>";

	    ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
	    TipoServicoService tipoServicoService = (TipoServicoService) ServiceLocator.getInstance().getService(TipoServicoService.class, null);

	    if (osDto != null) {
		ServicoContratoDTO servicoContratoDTO = new ServicoContratoDTO();
		servicoContratoDTO.setIdServicoContrato(osDto.getIdServicoContrato());
		servicoContratoDTO = (ServicoContratoDTO) servicoContratoService.restore(servicoContratoDTO);
		ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);

		ServicoDTO servicoDto = new ServicoDTO();
		if (servicoContratoDTO != null) {
		    servicoDto.setIdServico(servicoContratoDTO.getIdServico());
		}
		servicoDto = (ServicoDTO) servicoService.restore(servicoDto);

		if ((servicoDto != null) && (servicoContratoDTO != null)) {
		    String nomeTipoServico = "";
		    TipoServicoDTO tipoServicoDTO = new TipoServicoDTO();
		    if (servicoDto.getIdTipoServico() != null) {
			tipoServicoDTO.setIdTipoServico(servicoDto.getIdTipoServico());

			tipoServicoDTO = (TipoServicoDTO) tipoServicoService.restore(tipoServicoDTO);

			if (tipoServicoDTO != null) {
			    nomeTipoServico = UtilStrings.nullToVazio(tipoServicoDTO.getNomeTipoServico());
			}
		    }
		    strTable += nomeTipoServico;
		} else {
		    strTable += "--";
		}

	    } else {
		strTable += "--";
	    }
	    strTable += "</td>";

	}

	strTable += "<td style='text-align: center;'>";

	if ((osDto != null) && (osDto.getIdOSPai() == null)) {
	    if (this.glosaTotal > 0) {
		strTable += UtilFormatacao.formatDouble(this.glosaTotal, 2);
	    } else {
		strTable += "--";
	    }

	} else {
	    if (valorGlosado > 0) {
		strTable += UtilFormatacao.formatDouble(valorGlosado, 2);
		this.glosaTotal += valorGlosado;
	    } else {
		strTable += "--";
	    }
	}

	strTable += "</td>";

	return strTable;
    }

    public void listarFaturaContrato(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	InformacoesContratoDTO informacoesDto = (InformacoesContratoDTO) document.getBean();
	if (informacoesDto.getIdContrato() == null) {
	    document.alert("Informe o contrato!");
	    return;
	}
	String[] situacao = null;
	if (!informacoesDto.getFuncaoListarFatura().equals("0")) {
	    situacao = new String[1];
	    situacao[0] = informacoesDto.getFuncaoListarFatura();
	}

	FaturaService faturaService = (FaturaService) ServiceLocator.getInstance().getService(FaturaService.class, null);
	ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
	MoedaService moedaService = (MoedaService) ServiceLocator.getInstance().getService(MoedaService.class, null);
	Collection colOSs = faturaService.findByIdContratoAndPeriodoAndSituacao(informacoesDto.getIdContrato(), informacoesDto.getDataFatura1(), informacoesDto.getDataFatura2(), situacao);

	ContratoDTO contratoDto = new ContratoDTO();
	contratoDto.setIdContrato(informacoesDto.getIdContrato());
	contratoDto = (ContratoDTO) contratoService.restore(contratoDto);

	String nomeMoeda = "";
	if (contratoDto.getIdMoeda() != null) {
	    MoedaDTO moedaDto = new MoedaDTO();
	    moedaDto.setIdMoeda(contratoDto.getIdMoeda());
	    moedaDto = (MoedaDTO) moedaService.restore(moedaDto);
	    if (moedaDto != null) {
		nomeMoeda = " (" + moedaDto.getNomeMoeda() + ")";
	    }
	}
	String strTable = "<table class='table table-bordered table-striped' width='98%'>";
	strTable += "<tr>";
	strTable += "<td>";
	strTable += "&nbsp;";
	strTable += "</td>";
	strTable += "<td>";
	strTable += UtilI18N.internacionaliza(request, "citcorpore.comum.descricao");
	strTable += "</td>";
	strTable += "<td>";
	strTable += UtilI18N.internacionaliza(request, "citcorpore.comum.qtdeOS");
	strTable += "</td>";
	strTable += "<td>";
	strTable += UtilI18N.internacionaliza(request, "citcorpore.comum.datainicio");
	strTable += "</td>";
	strTable += "<td>";
	strTable += UtilI18N.internacionaliza(request, "citcorpore.comum.datafim");
	strTable += "</td>";
	strTable += "<td>";
	strTable += UtilI18N.internacionaliza(request, "citcorpore.comum.dataCriacao");
	strTable += "</td>";
	strTable += "<td>";
	strTable += UtilI18N.internacionaliza(request, "contrato.moeda");
	strTable += "</td>";
	strTable += "<td>";
	strTable += UtilI18N.internacionaliza(request, "citcorpore.comum.situacao");
	strTable += "</td>";
	strTable += "<td>";
	strTable += UtilI18N.internacionaliza(request, "contrato.valorestimado") + nomeMoeda;
	strTable += "</td>";
	strTable += "<td>";
	strTable += UtilI18N.internacionaliza(request, "contrato.valorExecutado") + nomeMoeda;
	strTable += "</td>";
	strTable += "<td>";
	strTable += UtilI18N.internacionaliza(request, "visao.glosa") + nomeMoeda;
	strTable += "</td>";
	strTable += "</tr>";
	if ((colOSs != null) && (colOSs.size() > 0)) {
	    for (Iterator itOs = colOSs.iterator(); itOs.hasNext();) {
		FaturaDTO faturaDto = (FaturaDTO) itOs.next();
		strTable += "<tr>";
		strTable += "<td>";
		if (faturaDto.getSituacaoFatura().equalsIgnoreCase("1")) {
		    strTable += "<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/btnAlterarRegistro.gif' border='0' title='"
			    + UtilI18N.internacionaliza(request, "contrato.editarFatura") + "' onclick='editaFatura(\"" + faturaDto.getIdFatura() + "\")' style='cursor:pointer'/>";
		} else {
		    strTable += "<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/f_moved.gif' border='0' title='"
			    + UtilI18N.internacionaliza(request, "contrato.apontarSituacaoFatura") + "' onclick='editaFatura(\"" + faturaDto.getIdFatura() + "\")' style='cursor:pointer'/>";
		}
		strTable += "<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/file_pdf.png' border='0' title='"
			+ UtilI18N.internacionaliza(request, "contrato.imprimirFatura") + "' onclick='imprimirFatura(\"" + faturaDto.getIdFatura() + "\")' style='cursor:pointer'/>";

		strTable += "<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/Excel-icon.png' border='0' title='"
			+ UtilI18N.internacionaliza(request, "contrato.imprimirFatura") + "' onclick='imprimirFaturaXls(\"" + faturaDto.getIdFatura() + "\")' style='cursor:pointer'/>";

		strTable += "</td>";
		strTable += "<td>";
		strTable += faturaDto.getDescricaoFatura();
		strTable += "</td>";
		strTable += "<td>";
		strTable += faturaDto.getQtdeOS();
		strTable += "</td>";
		strTable += "<td>";
		strTable += UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, faturaDto.getDataInicial(), WebUtil.getLanguage(request));
		strTable += "</td>";
		strTable += "<td>";
		strTable += UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, faturaDto.getDataFinal(), WebUtil.getLanguage(request));
		strTable += "</td>";
		strTable += "<td>";
		strTable += UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, faturaDto.getDataCriacao(), WebUtil.getLanguage(request));
		strTable += "</td>";
		strTable += "<td>";
		strTable += nomeMoeda;
		strTable += "</td>";
		strTable += "<td>";
		strTable += faturaDto.getDescricaoSituacaoFatura(request);
		strTable += "</td>";
		strTable += "<td>";
		if (faturaDto.getValorPrevistoSomaOS() != null) {
		    strTable += UtilFormatacao.formatDouble(faturaDto.getValorPrevistoSomaOS(), 2);
		} else {
		    strTable += "--";
		}
		strTable += "</td>";
		strTable += "<td>";
		if (faturaDto.getValorExecutadoSomaOS() != null) {
		    strTable += UtilFormatacao.formatDouble(faturaDto.getValorExecutadoSomaOS(), 2);
		} else {
		    strTable += "--";
		}
		strTable += "</td>";
		strTable += "<td>";
		if (faturaDto.getValorSomaGlosasOS() != null) {
		    strTable += UtilFormatacao.formatDouble(faturaDto.getValorSomaGlosasOS(), 2);
		} else {
		    strTable += "--";
		}
		strTable += "</td>";
		strTable += "</tr>";
	    }
	} else {
	    strTable += "<tr>";
	    strTable += "<td>&nbsp;</td>";
	    strTable += "<td colspan='20'>";
	    strTable += "<br/><b>" + UtilI18N.internacionaliza(request, "contrato.naoHaInformacoesCriteriosInformados") + "</b>";
	    strTable += "</td>";
	    strTable += "</tr>";
	}
	strTable += "</table>";
	document.getElementById("divListaFaturas").setInnerHTML(strTable);
    }

    public void imprimirRAOSContrato(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	InformacoesContratoDTO informacoesDto = (InformacoesContratoDTO) document.getBean();
	double custos = 0;
	double realizado = 0;
	double custoPrevisto = 0;
	double custoRealizado = 0;
	double custoGlosaGeral = 0;
	double custoAprovado = 0;
	if (informacoesDto.getIdContrato() == null) {
	    document.alert("Informe o contrato!");
	    return;
	}
	UsuarioDTO user = WebUtil.getUsuario(request);
	if (user == null) {
	    document.alert("O usuário não está logado! Favor logar no sistema!");
	    return;
	}
	String usuarioImpressao = user.getNomeUsuario();
	String nomeEmpresa = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.EMPRESA_Nome, "CITSMart");

	OSService osService = (OSService) ServiceLocator.getInstance().getService(OSService.class, null);
	ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
	FornecedorService fornecedorService = (FornecedorService) ServiceLocator.getInstance().getService(FornecedorService.class, null);
	ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
	AtividadesOSService atividadesOSService = (AtividadesOSService) ServiceLocator.getInstance().getService(AtividadesOSService.class, null);
	ServiceLocator.getInstance().getService(AcordoNivelServicoService.class, null);
	GlosaOSService glosaOSService = (GlosaOSService) ServiceLocator.getInstance().getService(GlosaOSService.class, null);
	MoedaService moedaService = (MoedaService) ServiceLocator.getInstance().getService(MoedaService.class, null);
	ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
	TipoEventoServicoService tipoEventoServicoService = (TipoEventoServicoService) ServiceLocator.getInstance().getService(TipoEventoServicoService.class, null);
	OSDTO osDto = new OSDTO();
	osDto.setIdOS(informacoesDto.getIdOS());
	osDto = (OSDTO) osService.restore(osDto);

	ServicoContratoDTO servicoContratoDTO = new ServicoContratoDTO();
	servicoContratoDTO.setIdServicoContrato(osDto.getIdServicoContrato());
	servicoContratoDTO = (ServicoContratoDTO) servicoContratoService.restore(servicoContratoDTO);

	ServicoDTO servicoDto = new ServicoDTO();
	servicoDto.setIdServico(servicoContratoDTO.getIdServico());
	servicoDto = (ServicoDTO) servicoService.restore(servicoDto);

	TipoEventoServicoDTO tipoEventoServicoDTO = new TipoEventoServicoDTO();
	if (servicoDto.getIdTipoEventoServico() != null) {
	    tipoEventoServicoDTO.setIdTipoEventoServico(servicoDto.getIdTipoEventoServico());
	    tipoEventoServicoDTO = (TipoEventoServicoDTO) tipoEventoServicoService.restore(tipoEventoServicoDTO);
	    UtilStrings.nullToVazio(tipoEventoServicoDTO.getNomeTipoEventoServico());
	}

	ContratoDTO contratoDto = new ContratoDTO();
	contratoDto.setIdContrato(informacoesDto.getIdContrato());
	contratoDto = (ContratoDTO) contratoService.restore(contratoDto);

	FornecedorDTO fornecedorDto = new FornecedorDTO();
	fornecedorDto.setIdFornecedor(contratoDto.getIdFornecedor());
	fornecedorDto = (FornecedorDTO) fornecedorService.restore(fornecedorDto);

	if (contratoDto.getIdMoeda() != null) {
	    MoedaDTO moedaDto = new MoedaDTO();
	    moedaDto.setIdMoeda(contratoDto.getIdMoeda());
	    moedaDto = (MoedaDTO) moedaService.restore(moedaDto);
	}

	String strBuffer = "";
	if (osDto != null) {
	    Collection<AtividadesOSDTO> colAtividades = atividadesOSService.findByIdOS(informacoesDto.getIdOS());
	    if (colAtividades != null) {
		for (AtividadesOSDTO atividade : colAtividades) {
		    atividade = (AtividadesOSDTO) atividadesOSService.restore(atividade);
		    custoPrevisto = custoPrevisto + atividade.getCustoAtividade();
		    if (atividade.getGlosaAtividade() != null) {
			custoGlosaGeral = custoGlosaGeral + atividade.getGlosaAtividade();
		    }
		    if (atividade.getQtdeExecutada() != null) {
			custoRealizado = custoRealizado + atividade.getQtdeExecutada();
		    }
		    if ((atividade.getGlosaAtividade() != null) && (atividade.getCustoAtividade() != null)) {
			double qtdeExec = 0;
			if (atividade.getQtdeExecutada() != null) {
			    qtdeExec = atividade.getQtdeExecutada();
			}
			custos = (qtdeExec - atividade.getGlosaAtividade());
			custoAprovado = custoAprovado + custos;
		    }
		}
	    }

	    strBuffer += "<table class='table table-bordered table-striped' width='100%' cellpadding='0' cellspacing='0'>";
	    strBuffer += "<tr>";
	    this.setContLinha();
	    strBuffer += "<td colspan='2' style='border:1px 1px 0 1px; font-size: font:20px arial,sans-serif;' align='center'>";
	    strBuffer += "<b>" + nomeEmpresa + "</b>";
	    strBuffer += "</td>";
	    strBuffer += "<br>";
	    this.setContLinha();
	    strBuffer += "</tr>";
	    strBuffer += "<table class='table table-bordered table-striped' width='100%' cellpadding='0' cellspacing='0'>";
	    strBuffer += "<tr>";
	    this.setContLinha();
	    strBuffer += "<td style='border:1px solid black; background-color: #F2F2F2;font:12px arial,sans-serif;' align='center'>";
	    strBuffer += "<b>RELAT&Oacute;RIO DE ATIVIDADES<b><br>";
	    strBuffer += "<br>";
	    this.setContLinha();
	    strBuffer += "<b>Contrato:<b>" + contratoDto.getNumero() + "<br>";
	    this.setContLinha();
	    strBuffer += "<b>Contratada:<b>" + fornecedorDto.getNomeFantasia() + "<br>";
	    this.setContLinha();
	    strBuffer += "<br>";
	    this.setContLinha();
	    strBuffer += "</td>";
	    strBuffer += "<td style='font:12px arial,sans-serif;border:1px solid black; background-color: #F2F2F2'>";
	    strBuffer += "<b>OS N<sup>o</sup>:</b>    " + osDto.getNumero() + "<br>";
	    this.setContLinha();
	    strBuffer += "<b>TAREFA:</b>  " + UtilStrings.nullToVazio(servicoDto.getSiglaAbrev()) + "<br>";
	    this.setContLinha();
	    strBuffer += "<br>";
	    this.setContLinha();
	    strBuffer += "<br>";
	    this.setContLinha();
	    strBuffer += "<b>Per&iacute;odo:</b>" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, osDto.getDataInicio(), WebUtil.getLanguage(request)) + " " + "a " + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, osDto.getDataFim(), WebUtil.getLanguage(request)) + "<br>";
	    this.setContLinha();
	    strBuffer += "</td>";
	    strBuffer += "</tr>";
	    strBuffer += "</table><br><br>";
	    this.setContLinha();
	    this.setContLinha();
	    strBuffer += "<table class='table table-bordered table-striped'  width='100%' cellpadding='0' cellspacing='0'>";
	    // inicia uma linha;
	    strBuffer += "<tr>";
	    this.setContLinha();
	    strBuffer += "<td>";
	    strBuffer += "<tr>";
	    this.setContLinha();
	    strBuffer += "<td valign='top' style='font:12px arial,sans-serif;border:1px solid black; width:60%' width='60%'>";
	    strBuffer += "<b>&Aacute;rea Requisitante: </b><br><br>";
	    this.setContLinha();
	    this.setContLinha();
	    strBuffer += "" + UtilStrings.nullToVazio(osDto.getNomeAreaRequisitante()) + "<br>";
	    this.setContLinha();
	    strBuffer += "</td>";

	    strBuffer += "<td>";
	    strBuffer += "<table class='table table-bordered table-striped' border='1' width='100%' cellpadding='0' cellspacing='0'>";
	    strBuffer += "<tr>";
	    this.setContLinha();
	    strBuffer += "<td style='font:12px arial,sans-serif;border-right:0px solid black;border-bottom:0px solid black; '>";
	    strBuffer += "<b>Custo Total Previsto:</b>                    ";
	    strBuffer += "</td>";
	    strBuffer += "<td style='font:12px arial,sans-serif;border-right:1px solid black;border-bottom:0px solid black;text-align:right '>";
	    strBuffer += UtilFormatacao.formatDouble(custoPrevisto, 2) + "";
	    strBuffer += "</td>";
	    strBuffer += "</tr>";

	    strBuffer += "<tr>";
	    this.setContLinha();
	    strBuffer += "<td style='font:12px arial,sans-serif;border-right:0px solid black;border-bottom:0px solid black; '>";
	    strBuffer += "<b>Custo Total Realizado : </b>";
	    this.setContLinha();
	    strBuffer += "</td>";
	    strBuffer += "<td style='font:12px arial,sans-serif;border-right:1px solid black;border-bottom:0px solid black;text-align:right '>";
	    strBuffer += "" + UtilFormatacao.formatDouble(custoRealizado, 2) + "<br>";
	    strBuffer += "</td>";
	    strBuffer += "</tr>";

	    strBuffer += "<tr>";
	    this.setContLinha();
	    strBuffer += "<td style='font:12px arial,sans-serif;border-right:0px solid black;border-bottom:0px solid black; '>";
	    strBuffer += "<b>Glosas Aplicadas: </b>";
	    this.setContLinha();
	    strBuffer += "</td>";
	    strBuffer += "<td style='font:12px arial,sans-serif;border-right:1px solid black;border-bottom:0px solid black;text-align:right '>";
	    strBuffer += "#GLOSASAPLICADAS#";
	    strBuffer += "</td>";
	    strBuffer += "</tr>";

	    strBuffer += "<tr >";
	    this.setContLinha();
	    strBuffer += "<td style='font:12px arial,sans-serif;border-right:0px solid black;border-bottom:1px solid black; '>";
	    strBuffer += "<b>Custo Final Aprovado:</b>";
	    this.setContLinha();
	    strBuffer += "</td>";
	    strBuffer += "<td style='font:12px arial,sans-serif;border-right:1px solid black;border-bottom:1px solid black;text-align:right '>";
	    strBuffer += "#CUSTOAPROVADO#";
	    strBuffer += "</td>";
	    strBuffer += "</tr>";

	    strBuffer += "</table>";
	    strBuffer += "</td>";
	    strBuffer += "</tr>";

	    strBuffer += "</td>";
	    strBuffer += "</tr>";

	    // fecha
	    strBuffer += "</table>";
	    strBuffer += "<br><br>";
	    this.setContLinha();
	    this.setContLinha();

	    strBuffer += "<table class='table table-bordered table-striped' width='100%' cellpadding='0' cellspacing='0'>";
	    strBuffer += "<tr>";
	    this.setContLinha();
	    strBuffer += "<td style='font:12px arial,sans-serif;border:1px solid black; '>";
	    strBuffer += "<b>Tarefa/Demanda:</b> <br>";
	    this.setContLinha();
	    strBuffer += osDto.getDemanda();
	    strBuffer += "<td>";
	    strBuffer += "</tr>";
	    strBuffer += "</table>";
	    strBuffer += "<br>";
	    this.setContLinha();
	    this.setContLinha();

	    strBuffer += "<br>";
	    this.setContLinha();
	    this.setContLinha();
	    strBuffer += "<b style='font:12px bold  arial,sans-serif;'>LISTA DE ATIVIDADES</b><br>";
	    strBuffer += "<table class='table table-bordered table-striped' width='100%' cellpadding='0' cellspacing='0'>";
	    strBuffer += "<tr>";
	    this.setContLinha();
	    strBuffer += "<td style='font:12px arial,sans-serif;border:1px solid black;text-align:center'>";
	    strBuffer += "<b>Item</b>";
	    strBuffer += "</td>";
	    strBuffer += "<td style='font:12px arial,sans-serif;border:1px solid black;text-align:center'>";
	    strBuffer += "<b>Custo Previsto</b>";
	    strBuffer += "</td>";
	    strBuffer += "<td style='font:12px arial,sans-serif;border:1px solid black;text-align:center'>";
	    strBuffer += "<b>Realizado</b>";
	    strBuffer += "</td>";
	    strBuffer += "<td style='font:12px arial,sans-serif;border:1px solid black;text-align:center'>";
	    strBuffer += "<b>Glosas</b>";
	    strBuffer += "</td>";
	    strBuffer += "<td style='font:12px arial,sans-serif;border:1px solid black;text-align:center'>";
	    strBuffer += "<b>Custo Aprovado</b>";
	    strBuffer += "</td>";
	    strBuffer += "</tr>";

	    // double totalOS = 0;
	    if (colAtividades != null) {
		custos = 0;
		realizado = 0;
		custoPrevisto = 0;
		custoRealizado = 0;
		double custoGlosa = 0;
		custoAprovado = 0;
		for (Object element : colAtividades) {
		    AtividadesOSDTO atividadesOSDto = (AtividadesOSDTO) element;

		    atividadesOSDto = (AtividadesOSDTO) atividadesOSService.restore(atividadesOSDto);
		    realizado = 0;
		    if (atividadesOSDto.getQtdeExecutada() != null) {
			realizado = atividadesOSDto.getQtdeExecutada();
			custoRealizado = custoRealizado + realizado;
		    }

		    strBuffer += "<tr>";
		    this.setContLinha();
		    this.setContLinha();
		    strBuffer += "<td style='font:12px bold  arial,sans-serif;border:1px solid black;text-align:center'>";
		    strBuffer += atividadesOSDto.getSequencia();
		    strBuffer += "</td>";
		    strBuffer += "<td style='font:12px bold  arial,sans-serif;border:1px solid black;text-align:center'>";
		    strBuffer += UtilFormatacao.formatDouble(atividadesOSDto.getCustoAtividade(), 2);
		    custoPrevisto = custoPrevisto + atividadesOSDto.getCustoAtividade();
		    strBuffer += "</td>";
		    strBuffer += "<td style='font:12px bold  arial,sans-serif;border:1px solid black;text-align:center'>";
		    strBuffer += UtilFormatacao.formatDouble(realizado, 2);
		    strBuffer += "</td>";
		    if (atividadesOSDto.getGlosaAtividade() != null) {
			custoGlosa = custoGlosa + atividadesOSDto.getGlosaAtividade();
			strBuffer += "<td style='font:12px bold  arial,sans-serif;border:1px solid black;text-align:center'>";
			strBuffer += UtilFormatacao.formatDouble(atividadesOSDto.getGlosaAtividade(), 2);
			strBuffer += "</td>";
		    } else {
			strBuffer += "<td style='font:12px bold  arial,sans-serif;border:1px solid black;text-align:center'>";
			strBuffer += "0,00";
			strBuffer += "</td>";
		    }
		    if ((atividadesOSDto.getGlosaAtividade() != null) && (atividadesOSDto.getCustoAtividade() != null)) {
			double qtdeExec = 0;
			if (atividadesOSDto.getQtdeExecutada() != null) {
			    qtdeExec = atividadesOSDto.getQtdeExecutada();
			}
			custos = (qtdeExec - atividadesOSDto.getGlosaAtividade());
			custoAprovado = custoAprovado + custos;
			strBuffer += "<td style='font:12px bold  arial,sans-serif;border:1px solid black;text-align:center'>";
			strBuffer += UtilFormatacao.formatDouble(custos, 2);
			strBuffer += "</td>";
		    } else {
			strBuffer += "<td style='font:12px bold  arial,sans-serif;border:1px solid black;text-align:center'>";
			if (atividadesOSDto.getQtdeExecutada() != null) {
			    strBuffer += UtilFormatacao.formatDouble(atividadesOSDto.getQtdeExecutada(), 2);
			} else {
			    strBuffer += "0,00";
			}
			strBuffer += "</td>";
			if (atividadesOSDto.getQtdeExecutada() != null) {
			    custoAprovado = custoAprovado + atividadesOSDto.getQtdeExecutada();
			}
		    }

		    strBuffer += "</tr>";
		}
		strBuffer += "<tr>";
		this.setContLinha();
		this.setContLinha();
		strBuffer += "<td style=' font:12px bold  arial,sans-serif;border:1px solid black;text-align:center;background-color: #90EE90;'>";
		strBuffer += "<b>TOTAL</b>";
		strBuffer += "</td>";
		strBuffer += "<td style='font:12px bold  arial,sans-serif;border:1px solid black;text-align:center; background-color: #90EE90;'>";
		strBuffer += UtilFormatacao.formatDouble(custoPrevisto, 2);
		strBuffer += "</td>";
		strBuffer += "<td style='font:12px bold  arial,sans-serif;border:1px solid black;text-align:center; background-color: #90EE90;'>";
		strBuffer += UtilFormatacao.formatDouble(custoRealizado, 2);
		strBuffer += "</td>";
		strBuffer += "<td style='font:12px bold  arial,sans-serif;border:1px solid black;text-align:center; background-color: #90EE90;'>";
		strBuffer += UtilFormatacao.formatDouble(custoGlosa, 2);
		strBuffer += "</td>";
		strBuffer += "<td style='font:12px bold  arial,sans-serif;border:1px solid black;text-align:center; background-color: #90EE90;'>";
		strBuffer += UtilFormatacao.formatDouble(custoAprovado, 2);
		strBuffer += "</td>";
		strBuffer += "</tr>";

	    }

	    strBuffer += "</table >";
	    strBuffer += "<br><br>";
	    this.setContLinha();
	    this.setContLinha();
	}

	strBuffer += "<table class='table table-bordered table-striped' width='100%' cellpadding='0' cellspacing='0'>";
	strBuffer += "<tr>";
	this.setContLinha();
	strBuffer += "<td style='font:12px bold  arial,sans-serif;border:1px solid black;background-color: #F2F2F2; align:center'>";
	strBuffer += "<b>GLOSAS APLICADAS NA OS</b>";
	strBuffer += "</td>";
	strBuffer += "<td style='font:12px bold  arial,sans-serif;border:1px solid black;background-color: #F2F2F2; align:center'>";
	strBuffer += "<b>N<sup>o</sup>DE OCORR&Ecirc;NCIAS</b>";
	strBuffer += "</td>";
	strBuffer += "<td style='font:12px bold  arial,sans-serif;border:1px solid black;background-color: #F2F2F2; align:center'>";
	strBuffer += "<b>%APLICADO</b>";
	strBuffer += "</td>";
	strBuffer += "<td style='font:12px bold  arial,sans-serif;border:1px solid black;background-color: #F2F2F2; align:center'>";
	strBuffer += "<b>CUSTO TOTAL DA GLOSA</b>";
	strBuffer += "</td>";
	strBuffer += "</tr>";
	Collection<GlosaOSDTO> colGlosa = glosaOSService.findByIdOs(informacoesDto.getIdOS());
	if (colGlosa != null) {
	    for (GlosaOSDTO glosa : colGlosa) {
		glosa = (GlosaOSDTO) glosaOSService.restore(glosa);
		strBuffer += "<tr>";
		this.setContLinha();
		strBuffer += "<td style='font:12px bold  arial,sans-serif;border:1px solid black;text-align:center'>";
		strBuffer += glosa.getDescricaoGlosa();
		strBuffer += "</td>";
		strBuffer += "<td style='font:12px bold  arial,sans-serif;border:1px solid black;text-align:center'>";
		strBuffer += UtilFormatacao.formatDouble(glosa.getNumeroOcorrencias(), 2);
		strBuffer += "</td>";
		strBuffer += "<td style='font:12px bold  arial,sans-serif;border:1px solid black;text-align:center'>";
		strBuffer += UtilFormatacao.formatDouble(glosa.getPercAplicado(), 2);
		strBuffer += "</td>";
		strBuffer += "<td style='font:12px bold  arial,sans-serif;border:1px solid black;text-align:center'>";
		strBuffer += UtilFormatacao.formatDouble(glosa.getCustoGlosa(), 2);
		strBuffer += "</td>";
		strBuffer += "</tr>";

		if (glosa.getCustoGlosa() != null) {
		    custoGlosaGeral = custoGlosaGeral + glosa.getCustoGlosa();
		}

	    }
	}

	strBuffer += "</table><br />";
	this.setContLinha();

	strBuffer += "<table class='table table-bordered table-striped' width='100%' cellpadding='0' cellspacing='0'>";
	strBuffer += "<tr>";
	this.setContLinha();
	this.setContLinha();
	strBuffer += "<td style='font:12px bold  arial,sans-serif;border:1px solid black; '>";
	strBuffer += "<b>Observa&ccedil;&otilde;es:</b><br>";
	this.setContLinha();
	if (osDto.getObsFinalizacao() != null) {
	    strBuffer += "" + osDto.getObsFinalizacao() + "<br>";
	    this.setContLinha();
	} else {
	    strBuffer += "<br>";
	    this.setContLinha();
	}

	strBuffer += "<td>";
	strBuffer += "</tr>";
	strBuffer += "</table>";
	strBuffer += "<br><br>";
	this.setContLinha();
	this.setContLinha();

	strBuffer += "<table class='table table-bordered table-striped' width='100%' cellpadding='0' cellspacing='0'>";
	if (this.getContLinha() >= 48) {
	    strBuffer += this.getQuebarLinha();
	}
	strBuffer += "<br/>";
	strBuffer += "<table class='table table-bordered table-striped' width='100%' cellpadding='0' cellspacing='0'>";
	strBuffer += "<tr>";
	this.setContLinha();
	strBuffer += "<td style='font:12px bold  arial,sans-serif;border:1px solid black;background-color: #F2F2F2; ' align='center'>";
	strBuffer += "<b>Aprova&ccedil;&atilde;o dos Servi&ccedil;os da OS </b>                                           ";
	strBuffer += "</td>";
	strBuffer += "<td style='font:12px bold  arial,sans-serif;border:1px solid black;background-color: #F2F2F2; ' align='center'>";
	strBuffer += "<b>Confer&ecirc;ncia e Atualiza&ccedil;&atilde;o</b>";
	strBuffer += "</td>";
	strBuffer += "</tr>";

	strBuffer += "<tr>";
	this.setContLinha();
	strBuffer += "<td style='font:12px bold  arial,sans-serif;border:1px solid black;' align='center'>";
	strBuffer += "<br>";
	strBuffer += "<br>";
	strBuffer += "<br>";
	strBuffer += "<br><br>";
	this.setContLinha();
	this.setContLinha();
	this.setContLinha();
	this.setContLinha();
	this.setContLinha();
	strBuffer += "Solicitante dos Serviços<br>";
	strBuffer += "Carimbo/Data<br>";
	this.setContLinha();
	this.setContLinha();
	strBuffer += "</td>";
	strBuffer += "<td style='font:12px bold  arial,sans-serif;border:1px solid black;' align='center'>";
	strBuffer += "<br>";
	strBuffer += "<br>";
	strBuffer += "<br>";
	strBuffer += "<br><br>";
	this.setContLinha();
	this.setContLinha();
	this.setContLinha();
	this.setContLinha();
	this.setContLinha();
	strBuffer += "Gestor Operacional do Contrato<br>";
	strBuffer += "Carimbo/Data<br>";
	strBuffer += "</td>";
	strBuffer += "</tr>";

	strBuffer += "<tr>";
	this.setContLinha();
	strBuffer += "<td style='font:12px bold  arial,sans-serif;border:1px solid black;background-color: #F2F2F2; ' align='center'>";
	strBuffer += "<b>Atestado Final</b>";
	strBuffer += "</td>";
	strBuffer += "<td style='font:12px bold  arial,sans-serif;border:1px solid black;background-color: #F2F2F2; ' align='center'>";
	strBuffer += "<b>Aceita&ccedil;&atilde;o</b>";
	strBuffer += "</td>";
	strBuffer += "</tr>";

	strBuffer += "<tr>";
	strBuffer += "<td style='font:12px bold  arial,sans-serif;border:1px solid black;' align='center'>";
	strBuffer += "<br>";
	strBuffer += "<br>";
	strBuffer += "<br>";
	strBuffer += "<br><br>";
	this.setContLinha();
	this.setContLinha();
	this.setContLinha();
	this.setContLinha();
	this.setContLinha();
	strBuffer += "Gestor do Contrato<br>";
	strBuffer += "Carimbo/Data<br>";
	this.setContLinha();
	this.setContLinha();
	strBuffer += "</td>";
	strBuffer += "<td style='font:12px bold  arial,sans-serif;border:1px solid black;' align='center'>";
	strBuffer += "Aceite da Contratada: ( ) Total () Parcial - Apresentará recurso<br>";
	strBuffer += "<br>";
	strBuffer += "<br>";
	strBuffer += "<br><br>";
	this.setContLinha();
	this.setContLinha();
	this.setContLinha();
	this.setContLinha();
	this.setContLinha();
	strBuffer += "Preposto da Contratada<br>";
	strBuffer += "Carimbo/Data<br>";
	strBuffer += "</td>";
	strBuffer += "</tr>";

	strBuffer += "</table>";
	strBuffer += "<br><br>";
	this.setContLinha();
	this.setContLinha();
	strBuffer += "</table >";

	strBuffer = strBuffer.replaceAll("#GLOSASAPLICADAS#", UtilFormatacao.formatDouble(custoGlosaGeral, 2));
	strBuffer = strBuffer.replaceAll("#CUSTOAPROVADO#", UtilFormatacao.formatDouble(custoRealizado - custoGlosaGeral, 2));

	String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles/" + user.getIdEmpregado();
	String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles" + "/" + user.getIdEmpregado();
	File f = new File(diretorioReceita);
	if (!f.exists()) {
	    f.mkdirs();
	}
	Date dt = new Date();
	String strCompl = "" + dt.getTime();
	String arquivoReceita = diretorioReceita + "/raAux_" + strCompl + ".pdf";
	String arquivoFormRefact = diretorioReceita + "/ra_" + strCompl + ".pdf";
	String arquivoRelativoFatura = diretorioRelativoOS + "/ra_" + strCompl + ".pdf";
	f = new File(arquivoReceita);
	if (f.exists()) {
	    f.delete();
	}
	f = null;
	Thread.sleep(50);
	{
	    // Imprimir em pdf
	    OutputStream os = new FileOutputStream(arquivoReceita);
	    strBuffer = UtilHTML.encodeHTML(strBuffer);
	    Html2Pdf.convert(strBuffer, os);
	    os.flush();
	    os.close();
	    os = null;
	    Thread.sleep(100);

	    PdfReader reader = new PdfReader(arquivoReceita);
	    PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(arquivoFormRefact));
	    PdfContentByte over;
	    int total = reader.getNumberOfPages() + 1;
	    for (int i2 = 1; i2 < total; i2++) {
		over = stamper.getUnderContent(i2);
		over.beginText();
		BaseFont bf = BaseFont.createFont("Helvetica", BaseFont.WINANSI, false);
		over.setFontAndSize(bf, 8);
		over.setTextMatrix(30, 30);
		over.showText("Pagina: " + i2 + " de " + (total - 1) + "     impresso em: " + UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, UtilDatas.getDataHoraAtual(), WebUtil.getLanguage(request)) + " por: " + usuarioImpressao);
		over.endText();
		over.stroke();
	    }
	    stamper.close();
	    stamper = null;
	    Thread.sleep(200);

	    document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + arquivoRelativoFatura + "')");
	}
    }

    public String getQuebarLinha() throws Exception {
	String nomeEmpresa = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.EMPRESA_Nome, "CITSMart");
	String strBuffer = "<div style='page-break-before: always;'></div>";
	strBuffer += "<tr>";
	this.setContLinha();
	strBuffer += "<td colspan='2' style='font:20px bold  arial,sans-serif;border:1px 1px 0 1px; font-size: 1cm' align='center'>";
	strBuffer += "<b>" + nomeEmpresa + "</b>";
	strBuffer += "</td>";
	strBuffer += "</tr>";
	return strBuffer;

    }

    public void duplicarOS(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	InformacoesContratoDTO informacoesDto = (InformacoesContratoDTO) document.getBean();

	if (informacoesDto.getIdContrato() == null) {
	    document.alert("Informe o contrato!");
	    return;
	}

	UsuarioDTO user = WebUtil.getUsuario(request);

	if (user == null) {
	    document.alert("O usuário não está logado! Favor logar no sistema!");
	    return;
	}

	OSService osService = (OSService) ServiceLocator.getInstance().getService(OSService.class, null);

	osService.duplicarOS(informacoesDto.getIdOS());

	document.alert("OS duplicada!");

	// TODO Retorna listando todas as OS.
	this.listarOSContrato(document, request, response);

    }

    /**
     * Metodo para gravar Registro da Atividade
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     */

    public void gravarRegistroExecucao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	InformacoesContratoDTO informacoesDto = (InformacoesContratoDTO) document.getBean();
	document.executeScript("aguarde();");
	try {
	    OSService osService = (OSService) ServiceLocator.getInstance().getService(OSService.class, null);
	    OSDTO osDto = new OSDTO();
	    osDto.setIdOS(informacoesDto.getIdOS());
	    osDto = (OSDTO) osService.restore(osDto);
	    document.executeScript("fechar_aguarde();");
	} catch (Exception e) {
	    document.executeScript("fechar_aguarde();");
	    e.printStackTrace();
	}

    }

    /**
     * Metodo para retornar os agendada e atividades da os
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     */

    public void agendamentoOS(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	InformacoesContratoDTO informacoesDto = (InformacoesContratoDTO) document.getBean();
	ProgramacaoAtividadeService programacaoAtividadeService = (ProgramacaoAtividadeService) ServiceLocator.getInstance().getService(ProgramacaoAtividadeService.class, null);
	OSService osService = (OSService) ServiceLocator.getInstance().getService(OSService.class, null);
	ServiceLocator.getInstance().getService(AtividadesOSService.class, null);
	AtividadePeriodicaService atividadePeriodicaService = (AtividadePeriodicaService) ServiceLocator.getInstance().getService(AtividadePeriodicaService.class, null);
	GrupoAtvPeriodicaService grupoAtvPeriodicaService = (GrupoAtvPeriodicaService) ServiceLocator.getInstance().getService(GrupoAtvPeriodicaService.class, null);

	List<OSDTO> listAtividadeOs = (List<OSDTO>) osService.listAtividadePeridodicaByIdos(informacoesDto.getIdOS());

	ExecucaoAtividadePeriodicaService execucaoAtividadePeriodicaService = (ExecucaoAtividadePeriodicaService) ServiceLocator.getInstance()
		.getService(ExecucaoAtividadePeriodicaService.class, null);

	GrupoAtvPeriodicaDTO grupoAtvPeriodicaDTO = new GrupoAtvPeriodicaDTO();
	StringBuilder table = new StringBuilder();
	StringBuilder tableInterna = new StringBuilder();
	StringBuilder tableTotalOs = new StringBuilder();

	if (informacoesDto.getDataInicioAtividade() == null) {
	    document.alert("Data Início: Campo Obrigatório");
	    return;
	}
	if (informacoesDto.getDataFimAtividade() == null) {
	    document.alert("Data Fim: Campo Obrigatório");
	    return;
	}

	table.append("<table class='table table-bordered table-striped' width='100%' cellpadding='0' cellspacing='0'>");
	int contAgendadoTotal = 0;
	int contSuspensoTotal = 0;
	int contExecutadoTotal = 0;
	int contEmExecucaoTotal = 0;
	int qtdeDias = UtilDatas.dataDiff(informacoesDto.getDataInicioAtividade(), informacoesDto.getDataFimAtividade());
	if (listAtividadeOs != null) {

	    for (OSDTO osdto : listAtividadeOs) {
		AtividadePeriodicaDTO atividadePeriodicaDTO = new AtividadePeriodicaDTO();
		atividadePeriodicaDTO.setIdAtividadePeriodica(osdto.getIdAtividadePeriodica());
		atividadePeriodicaDTO = (AtividadePeriodicaDTO) atividadePeriodicaService.restore(atividadePeriodicaDTO);

		Collection<EventoDTO> colEventos = programacaoAtividadeService.findEventosAgenda(atividadePeriodicaDTO, informacoesDto.getDataInicioAtividade(), qtdeDias);
		if ((colEventos != null) && (colEventos.size() > 0)) {
		    int contAgendado = 0;
		    int contSuspenso = 0;
		    int contExecutado = 0;
		    int contEmExecucao = 0;

		    grupoAtvPeriodicaDTO.setIdGrupoAtvPeriodica(atividadePeriodicaDTO.getIdGrupoAtvPeriodica());
		    grupoAtvPeriodicaDTO = (GrupoAtvPeriodicaDTO) grupoAtvPeriodicaService.restore(grupoAtvPeriodicaDTO);

		    tableInterna.append("<tr>");
		    tableInterna.append("<td  style='font:12px; text-align: left; ' class='linhaSubtituloGrid' colspan='5'>");
		    tableInterna.append("Descrição Atividade: <span style='font-weight: normal;'>" + atividadePeriodicaDTO.getTituloAtividade() + "</span>");
		    tableInterna.append("</td>");
		    tableInterna.append("</tr>");
		    tableInterna.append("<td  style='font:12px; text-align: left;' class='linhaSubtituloGrid' colspan='5'>");
		    tableInterna.append("Grupo de Atividades:<span style='font-weight: normal;'> " + grupoAtvPeriodicaDTO.getNomeGrupoAtvPeriodica() + "</span>");
		    tableInterna.append("</td>");
		    tableInterna.append("</tr>");

		    tableInterna.append("<tr>");
		    tableInterna.append("<td>");
		    tableInterna.append("Descrição Atividade");
		    tableInterna.append("</td>");
		    tableInterna.append("<td>");
		    tableInterna.append("Situação");
		    tableInterna.append("</td>");
		    tableInterna.append("<td>");
		    tableInterna.append("Data/hora programada");
		    tableInterna.append("</td>");
		    tableInterna.append("<td>");
		    tableInterna.append("Data/hora programada execução");
		    tableInterna.append("</td>");
		    tableInterna.append("<td>");
		    tableInterna.append("Detalhamento");
		    tableInterna.append("</td>");
		    tableInterna.append("</tr>");

		    for (EventoDTO eventoDto : colEventos) {
			String situacao = "";
			String titulo = "";
			ExecucaoAtividadePeriodicaDTO execucaoAtividadePeriodicaDTO = new ExecucaoAtividadePeriodicaDTO();
			if (eventoDto.getIdExecucao() > 0) {
			    execucaoAtividadePeriodicaDTO.setIdExecucaoAtividadePeriodica(eventoDto.getIdExecucao());
			    execucaoAtividadePeriodicaDTO = (ExecucaoAtividadePeriodicaDTO) execucaoAtividadePeriodicaService.restore(execucaoAtividadePeriodicaDTO);
			}

			if (eventoDto.getClassName().equalsIgnoreCase("suspenso")) {
			    contSuspenso++;
			    situacao = "Registrado";
			    titulo = UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, execucaoAtividadePeriodicaDTO.getDataExecucao(), WebUtil.getLanguage(request)) + " - " + execucaoAtividadePeriodicaDTO.getHoraExecucao();
			}
			if (eventoDto.getClassName().equalsIgnoreCase("executado")) {
			    situacao = "Executado";
			    contExecutado++;
			    titulo = UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, execucaoAtividadePeriodicaDTO.getDataExecucao(), WebUtil.getLanguage(request)) + " - " + execucaoAtividadePeriodicaDTO.getHoraExecucao();
			}
			if (eventoDto.getClassName().equalsIgnoreCase("emExecucao")) {
			    contEmExecucao++;
			    situacao = "Atualizado";
			    titulo = UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, execucaoAtividadePeriodicaDTO.getDataExecucao(), WebUtil.getLanguage(request)) + " - " + execucaoAtividadePeriodicaDTO.getHoraExecucao();
			}

			if (titulo.trim().equalsIgnoreCase("")) {
			    contAgendado++;
			    situacao = "Agendado";

			}

			tableInterna.append("<tr>");
			tableInterna.append("<td style='font:12px heidth bold  arial,sans-serif;border:1px solid #B3B3B3; height:20px' align='center'>");
			tableInterna.append(eventoDto.getDescricaoAtividadeOS());
			tableInterna.append("</td>");
			tableInterna.append("<td style='font:12px bold  arial,sans-serif;border:1px solid #B3B3B3; height:20px' align='center'>");
			tableInterna.append(situacao);
			tableInterna.append("</td>");
			tableInterna.append("<td style='font:12px bold  arial,sans-serif;border:1px solid #B3B3B3; height:20px' align='center'>");
			tableInterna.append(UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, eventoDto.getData(), WebUtil.getLanguage(request)) + " - " + eventoDto.getHoraInicio());
			tableInterna.append("</td>");
			tableInterna.append("<td style='font:12px bold  arial,sans-serif;border:1px solid #B3B3B3; height:20px' align='center'>");
			tableInterna.append(titulo);
			tableInterna.append("</td>");
			tableInterna.append("<td style='font:12px bold  arial,sans-serif;border:1px solid #B3B3B3; height:20px'width:90px; align='center'>");
			if (execucaoAtividadePeriodicaDTO.getDetalhamento() != null) {
			    tableInterna.append(execucaoAtividadePeriodicaDTO.getDetalhamento());
			} else {
			    tableInterna.append("");
			}
			tableInterna.append("</td>");
			tableInterna.append("</tr>");
		    }
		    contAgendadoTotal += contAgendado;
		    contSuspensoTotal += contSuspenso;
		    contExecutadoTotal += contExecutado;
		    contEmExecucaoTotal += contEmExecucao;
		}

	    }
	} else {
	    table.append("<tr>");
	    table.append("<td>");
	    table.append("Não existe atividade para esta O.S");
	    table.append("<td>");
	    table.append("</tr>");
	}

	tableTotalOs.append("<table class='table table-bordered table-striped' width='100%' cellpadding='0' cellspacing='0'>");

	tableTotalOs.append("<tr>");
	tableTotalOs.append("<td  style='font:12px' class='linhaSubtituloGridOs' colspan='8'>");
	tableTotalOs.append("Número de Ocorrencia no período.");
	tableTotalOs.append("</td>");
	tableTotalOs.append("</tr>");

	tableTotalOs.append("<tr>");
	tableTotalOs.append("<td style='font:12px' class='linhaSubtituloGridOs'>");
	tableTotalOs.append("Agendada:");
	tableTotalOs.append("</td>");
	tableTotalOs.append("<td style='font:12px; width: 20px ' class='linhaSubtituloGridOs'>");
	tableTotalOs.append(contAgendadoTotal);
	tableTotalOs.append("</td>");

	tableTotalOs.append("<td style='font:12px' class='linhaSubtituloGridOs'>");
	tableTotalOs.append("Registrada:");
	tableTotalOs.append("</td>");
	tableTotalOs.append("<td style='font:12px; width: 20px ' class='linhaSubtituloGridOs'>");
	tableTotalOs.append(contSuspensoTotal);
	tableTotalOs.append("</td>");

	tableTotalOs.append("<td style='font:12px' class='linhaSubtituloGridOs' >");
	tableTotalOs.append("Executada:");
	tableTotalOs.append("</td>");
	tableTotalOs.append("<td style='font:12px; width: 20px ' class='linhaSubtituloGridOs'>");
	tableTotalOs.append(contExecutadoTotal);
	tableTotalOs.append("</td>");

	tableTotalOs.append("<td style='font:12px' class='linhaSubtituloGridOs'>");
	tableTotalOs.append("Atualizada:");
	tableTotalOs.append("</td>");
	tableTotalOs.append("<td style='font:12px; width: 20px ' class='linhaSubtituloGridOs'>");
	tableTotalOs.append(contEmExecucaoTotal);
	tableTotalOs.append("</td>");
	tableTotalOs.append("</tr>");
	tableTotalOs.append("</table>");

	table.append(tableInterna.toString());
	table.append("</table>");

	tableTotalOs.append(table.toString());

	document.executeScript("renderizaList(" + table.toString() + ")");

	document.getElementById("AgendamentoOS").setInnerHTML(tableTotalOs.toString());
    }

    /**
     * Metodo pra fazer a impressão OS Contratos
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     */
    public void imprimirOSContrato(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	InformacoesContratoDTO informacoesDto = (InformacoesContratoDTO) document.getBean();
	if (informacoesDto.getIdContrato() == null) {
	    document.alert("Informe o contrato!");
	    return;
	}
	UsuarioDTO user = WebUtil.getUsuario(request);
	if (user == null) {
	    document.alert("O usuário não está logado! Favor logar no sistema!");
	    return;
	}
	String usuarioImpressao = user.getNomeUsuario();

	OSService osService = (OSService) ServiceLocator.getInstance().getService(OSService.class, null);
	ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
	FornecedorService fornecedorService = (FornecedorService) ServiceLocator.getInstance().getService(FornecedorService.class, null);
	ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
	AtividadesOSService atividadesOSService = (AtividadesOSService) ServiceLocator.getInstance().getService(AtividadesOSService.class, null);
	AcordoNivelServicoService acordoNivelServicoService = (AcordoNivelServicoService) ServiceLocator.getInstance().getService(AcordoNivelServicoService.class, null);
	MoedaService moedaService = (MoedaService) ServiceLocator.getInstance().getService(MoedaService.class, null);
	ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
	TipoEventoServicoService tipoEventoServicoService = (TipoEventoServicoService) ServiceLocator.getInstance().getService(TipoEventoServicoService.class, null);
	OSDTO osDto = new OSDTO();
	osDto.setIdOS(informacoesDto.getIdOS());
	osDto = (OSDTO) osService.restore(osDto);

	ServicoContratoDTO servicoContratoDTO = new ServicoContratoDTO();
	servicoContratoDTO.setIdServicoContrato(osDto.getIdServicoContrato());
	servicoContratoDTO = (ServicoContratoDTO) servicoContratoService.restore(servicoContratoDTO);

	ServicoDTO servicoDto = new ServicoDTO();
	servicoDto.setIdServico(servicoContratoDTO.getIdServico());
	servicoDto = (ServicoDTO) servicoService.restore(servicoDto);

	String nomeTipoEventoServico = "";
	TipoEventoServicoDTO tipoEventoServicoDTO = new TipoEventoServicoDTO();
	if (servicoDto.getIdTipoEventoServico() != null) {
	    tipoEventoServicoDTO.setIdTipoEventoServico(servicoDto.getIdTipoEventoServico());

	    tipoEventoServicoDTO = (TipoEventoServicoDTO) tipoEventoServicoService.restore(tipoEventoServicoDTO);

	    nomeTipoEventoServico = UtilStrings.nullToVazio(tipoEventoServicoDTO.getNomeTipoEventoServico());
	}

	ContratoDTO contratoDto = new ContratoDTO();
	contratoDto.setIdContrato(informacoesDto.getIdContrato());
	contratoDto = (ContratoDTO) contratoService.restore(contratoDto);

	FornecedorDTO fornecedorDto = new FornecedorDTO();
	fornecedorDto.setIdFornecedor(contratoDto.getIdFornecedor());
	fornecedorDto = (FornecedorDTO) fornecedorService.restore(fornecedorDto);

	String nomeMoeda = "";
	String nomeEmpresa = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.EMPRESA_Nome, "CITSMart");
	if (contratoDto.getIdMoeda() != null) {
	    MoedaDTO moedaDto = new MoedaDTO();
	    moedaDto.setIdMoeda(contratoDto.getIdMoeda());
	    moedaDto = (MoedaDTO) moedaService.restore(moedaDto);
	    if (moedaDto != null) {
		nomeMoeda = "" + moedaDto.getNomeMoeda() + "";
	    }
	}

	String strBuffer = "";
	if (osDto != null) {
	    Collection colAtividades = atividadesOSService.findByIdOS(informacoesDto.getIdOS());
	    strBuffer += "<table class='table table-bordered table-striped' width='100%' cellpadding='0' cellspacing='0'>";
	    strBuffer += "<table class='table table-bordered table-striped' width='100%' cellpadding='0' cellspacing='0'>";
	    strBuffer += "<tr>";
	    this.setContLinha();
	    strBuffer += "<td colspan='2' style='font:20px arial,sans-serif;border:1px 1px 0 1px; ' align='center'>";
	    strBuffer += "<b>" + nomeEmpresa + "</b>";
	    strBuffer += "</td>";
	    strBuffer += "<br>";
	    strBuffer += "<br>";
	    this.setContLinha();
	    this.setContLinha();
	    strBuffer += "</tr>";
	    strBuffer += "<table class='table table-bordered table-striped' width='100%' cellpadding='0' cellspacing='0'>";
	    strBuffer += "<tr>";
	    this.setContLinha();
	    strBuffer += "<td style='font:12px arial,sans-serif;border:1px solid black; background-color: #F2F2F2' align='center'>";
	    strBuffer += "<b>ORDEM DE SERVIÇO N<sup>o</sup></b>" + (osDto.getNumero() != null ? osDto.getNumero() : "") + "/" + osDto.getAno() + "<br><br>";
	    this.setContLinha();
	    this.setContLinha();
	    strBuffer += "<b>Contrato Número:</b>" + contratoDto.getNumero() + "<br>";
	    this.setContLinha();
	    strBuffer += "<b>Contratada:</b>" + fornecedorDto.getNomeFantasia() + "<br>";
	    this.setContLinha();
	    strBuffer += "</td>";
	    strBuffer += "<td style='font:12px arial,sans-serif;border:1px solid black; background-color: #F2F2F2'>";
	    strBuffer += "TAREFA: " + UtilStrings.nullToVazio(servicoDto.getSiglaAbrev()) + "<br>";
	    this.setContLinha();
	    strBuffer += "Execução Início: " + (osDto.getDataInicio() != null ? UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, osDto.getDataInicio(), WebUtil.getLanguage(request)) : "") + "<br>";
	    this.setContLinha();
	    strBuffer += "Execução Final: " + (osDto.getDataFim() != null ? UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, osDto.getDataFim(), WebUtil.getLanguage(request)) : "") + "<br>";
	    this.setContLinha();
	    strBuffer += "</td>";
	    strBuffer += "</tr>";
	    strBuffer += "</table><br><br>";
	    this.setContLinha();
	    this.setContLinha();
	    strBuffer += "</table>";

	    strBuffer += "<table class='table table-bordered table-striped' width='100%' cellpadding='0' cellspacing='0'>";
	    strBuffer += "<tr>";
	    this.setContLinha();
	    strBuffer += "<td style='font:12px arial,sans-serif;border:1px solid black; width:60%' width='60%'>";
	    strBuffer += "<b>Área Requisitante: </b><br>";
	    this.setContLinha();
	    strBuffer += "<br>";
	    this.setContLinha();
	    strBuffer += "" + UtilStrings.nullToVazio(osDto.getNomeAreaRequisitante()) + "<br>";
	    this.setContLinha();
	    strBuffer += "<td>";
	    strBuffer += "<td style='font:12px arial,sans-serif;border:1px solid black; width:40%' width='40%'>";
	    strBuffer += "<b>Tipo:</b>" + nomeTipoEventoServico + "<br>";
	    this.setContLinha();
	    strBuffer += "<b>Custo Previsto em " + nomeMoeda + ": #CUSTOTOTAL#</b> <br>";
	    this.setContLinha();
	    strBuffer += "<td>";
	    strBuffer += "</tr>";
	    strBuffer += "<tr>";
	    this.setContLinha();
	    strBuffer += "<td style='font:12px arial,sans-serif;border:1px solid black;'>";
	    strBuffer += "<b>Tarefa/Demanda:</b> <br>";
	    this.setContLinha();
	    strBuffer += "" + osDto.getDemanda() + "";
	    strBuffer += "<td>";
	    strBuffer += "<td style='font:12px arial,sans-serif;border:1px solid black;'>";
	    strBuffer += "<b>Objetivo:</b><br>";
	    this.setContLinha();
	    strBuffer += osDto.getObjetivo();
	    strBuffer += "<td>";
	    strBuffer += "</tr>";
	    strBuffer += "</table>";

	    strBuffer += "<br><br>";
	    this.setContLinha();
	    this.setContLinha();
	    strBuffer += "<b style='font:12px arial,sans-serif;'>LISTA DE ATIVIDADES</b><br>";
	    strBuffer += "<table class='table table-bordered table-striped' width='100%' cellpadding='0' cellspacing='0'>";
	    strBuffer += "<tr>";
	    this.setContLinha();
	    strBuffer += "<td style='font:12px arial,sans-serif;border:1px solid black; background-color: #F2F2F2; text-align:center'>";
	    strBuffer += "Item";
	    strBuffer += "</td>";
	    strBuffer += "<td style='font:12px arial,sans-serif;border:1px solid black; background-color: #F2F2F2; text-align:center'>";
	    strBuffer += "Complexidade";
	    strBuffer += "</td>";
	    strBuffer += "<td style='font:12px arial,sans-serif;border:1px solid black; background-color: #F2F2F2'>";
	    strBuffer += "Atividade";
	    strBuffer += "</td>";
	    strBuffer += "<td style='font:12px arial,sans-serif;border:1px solid black; background-color: #F2F2F2; text-align:center'>";
	    strBuffer += "Custo Total (" + nomeMoeda + ")";
	    strBuffer += "</td>";
	    strBuffer += "</tr>";

	    double totalOS = 0;
	    if (colAtividades != null) {
		for (Iterator it = colAtividades.iterator(); it.hasNext();) {
		    AtividadesOSDTO atividadesOSDTO = (AtividadesOSDTO) it.next();
		    strBuffer += "<tr>";
		    this.setContLinha();
		    strBuffer += "<td style='font:12px arial,sans-serif;border:1px solid black;text-align:center' rowspan='2'>";
		    strBuffer += atividadesOSDTO.getSequencia();
		    strBuffer += "</td>";
		    strBuffer += "<td style='font:12px arial,sans-serif;border:1px solid black;text-align:center' rowspan='2'>";
		    if (atividadesOSDTO.getComplexidade() == null) {
			atividadesOSDTO.setComplexidade("");
		    }
		    String strComplexidade = "";
		    if (atividadesOSDTO.getComplexidade().equalsIgnoreCase("1")) {
			strComplexidade = "B";
		    }
		    if (atividadesOSDTO.getComplexidade().equalsIgnoreCase("2")) {
			strComplexidade = "I";
		    }
		    if (atividadesOSDTO.getComplexidade().equalsIgnoreCase("3")) {
			strComplexidade = "M";
		    }
		    if (atividadesOSDTO.getComplexidade().equalsIgnoreCase("4")) {
			strComplexidade = "A";
		    }
		    if (atividadesOSDTO.getComplexidade().equalsIgnoreCase("5")) {
			strComplexidade = "E";
		    }
		    strBuffer += strComplexidade;
		    strBuffer += "</td>";
		    strBuffer += "<td style='font:12px arial,sans-serif;border:1px solid black;'>";
		    strBuffer += atividadesOSDTO.getDescricaoAtividade();
		    strBuffer += "</td>";
		    strBuffer += "<td style='font:12px arial,sans-serif;border:1px solid black;text-align:center' rowspan='2'>";
		    strBuffer += UtilFormatacao.formatDouble(atividadesOSDTO.getCustoAtividade(), 2);
		    strBuffer += "</td>";
		    strBuffer += "</tr>";

		    strBuffer += "<tr>";
		    this.setContLinha();
		    strBuffer += "<td style='font:12px arial,sans-serif;border:1px solid black;'>";
		    strBuffer += "<span style='font:12px arial,sans-serif;'><b>Observações: </b></span><br>" + atividadesOSDTO.getObsAtividade();
		    this.setContLinha();
		    strBuffer += "</td>";
		    strBuffer += "</tr>";

		    totalOS = totalOS + atividadesOSDTO.getCustoAtividade();
		}
	    }

	    strBuffer = strBuffer.replaceAll("#CUSTOTOTAL#", UtilFormatacao.formatDouble(totalOS, 2));

	    strBuffer += "<tr>";
	    this.setContLinha();
	    strBuffer += "<td colspan='3' style='font:12px arial,sans-serif;border:1px solid black; text-align:center'>";
	    strBuffer += "TOTAL PREVISTO PARA A OS";
	    strBuffer += "</td>";
	    strBuffer += "<td style='font:12px arial,sans-serif;border:1px solid black; text-align:center'>";
	    strBuffer += UtilFormatacao.formatDouble(totalOS, 2);
	    strBuffer += "</td>";
	    strBuffer += "</tr>";

	    strBuffer += "</table>";

	    strBuffer += "<span style='font:8px arial,sans-serif;'>(*) Baixa(1); Intermediária (1,5); Mediana (3,5); Alta (6); Especialista (10)</span>";
	    strBuffer += "<br><br>";
	    this.setContLinha();
	    this.setContLinha();
	}

	Collection colAcordosServicoContrato = acordoNivelServicoService.consultaPorIdServicoContrato(osDto.getIdServicoContrato());
	if (colAcordosServicoContrato != null) {
	    strBuffer += "<table class='table table-bordered table-striped' width='100%' cellpadding='0' cellspacing='0'>";
	    strBuffer += "<tr>";
	    this.setContLinha();
	    strBuffer += "<td style='font:12px arial,sans-serif;border:1px solid black;background-color: #F2F2F2;'>";
	    strBuffer += "<b>RESULTADOS ESPERADOS E NÍVEIS DE QUALIDADE EXIGIDOS</b>";
	    strBuffer += "</td>";
	    strBuffer += "<td style='font:12px arial,sans-serif;border:1px solid black;background-color: #F2F2F2;'>";
	    strBuffer += "<b>LIMITES</b>";
	    strBuffer += "</td>";
	    strBuffer += "<td style='font:12px arial,sans-serif;border:1px solid black;background-color: #F2F2F2;'>";
	    strBuffer += "<b>GLOSA</b>";
	    strBuffer += "</td>";
	    strBuffer += "<td style='font:12px arial,sans-serif;border:1px solid black;background-color: #F2F2F2;'>";
	    strBuffer += "<b>LIMITE GLOSA</b>";
	    strBuffer += "</td>";
	    strBuffer += "</tr>";
	    for (Iterator it = colAcordosServicoContrato.iterator(); it.hasNext();) {
		AcordoNivelServicoDTO acordoNivelServicoDTO = (AcordoNivelServicoDTO) it.next();
		if ((acordoNivelServicoDTO.getDeleted() == null) || acordoNivelServicoDTO.getDeleted().equalsIgnoreCase("N")) {
		    if ((acordoNivelServicoDTO.getDataFim() == null) || acordoNivelServicoDTO.getDataFim().after(UtilDatas.getDataAtual())) {
			strBuffer += "<tr>";
			this.setContLinha();
			strBuffer += "<td style='font:12px arial,sans-serif;border:1px solid black;'>";
			strBuffer += acordoNivelServicoDTO.getTituloSLA();
			strBuffer += "</td>";
			strBuffer += "<td style='font:12px arial,sans-serif;border:1px solid black;'>";
			if (acordoNivelServicoDTO.getValorLimite() == null) {
			    strBuffer += "";
			} else {
			    strBuffer += UtilFormatacao.formatDouble(acordoNivelServicoDTO.getValorLimite(), 2) + " ";
			    strBuffer += acordoNivelServicoDTO.getUnidadeValorLimite();
			}

			strBuffer += "</td>";
			strBuffer += "<td style='font:12px arial,sans-serif;border:1px solid black;'>";
			if (acordoNivelServicoDTO.getDetalheGlosa() == null) {
			    strBuffer += "";
			} else {
			    strBuffer += acordoNivelServicoDTO.getDetalheGlosa();
			}

			strBuffer += "</td>";
			strBuffer += "<td style='font:12px arial,sans-serif;border:1px solid black;'>";
			if (acordoNivelServicoDTO.getDetalheLimiteGlosa() == null) {
			    strBuffer += "";
			} else {
			    strBuffer += acordoNivelServicoDTO.getDetalheLimiteGlosa();
			}
			strBuffer += "</td>";
			strBuffer += "</tr>";
		    }
		}
	    }
	    strBuffer += "</table>";
	    strBuffer += "<br><br>";
	    this.setContLinha();
	    this.setContLinha();
	}

	strBuffer += "<table class='table table-bordered table-striped' width='100%' cellpadding='0' cellspacing='0'>";
	strBuffer += "<tr>";
	this.setContLinha();
	strBuffer += "<td style='font:12px arial,sans-serif;border:1px solid black;background-color: #F2F2F2;'>";
	strBuffer += "PRESSUPOSTOS E RESTRIÇÕES";
	strBuffer += "</td>";
	strBuffer += "</tr>";
	strBuffer += "<tr>";
	this.setContLinha();
	strBuffer += "<td style='font:12px arial,sans-serif;border:1px solid black;'>";
	strBuffer += UtilStrings.nullToVazio(servicoContratoDTO.getRestricoesPressup());
	strBuffer += "</td>";
	strBuffer += "</tr>";
	strBuffer += "</table>";

	strBuffer += "<table class='table table-bordered table-striped' width='100%' cellpadding='0' cellspacing='0'>";

	if (this.getContLinha() >= 40) {
	    strBuffer += this.getQuebarLinha();
	}

	strBuffer += "<br/>";
	strBuffer += "<table class='table table-bordered table-striped' width='100%' cellpadding='0' cellspacing='0'>";
	strBuffer += "<tr>";
	this.setContLinha();
	strBuffer += "<td style='font:12px arial,sans-serif;border:1px solid black;background-color: #F2F2F2; ' align='center'>";
	strBuffer += "<b> Solicita&ccedil;&atilde;o </b>                                                                                  ";
	strBuffer += "</td>";
	strBuffer += "<td style='font:12px arial,sans-serif;border:1px solid black;background-color: #F2F2F2; ' align='center'>";
	strBuffer += "<b>Autoriza&ccedil;&atilde;o</b>";
	strBuffer += "</td>";
	strBuffer += "</tr>";

	strBuffer += "<tr>";
	this.setContLinha();
	strBuffer += "<td style='font:12px arial,sans-serif;border:1px solid black;' align='center'>";
	strBuffer += "<br>";
	strBuffer += "<br>";
	strBuffer += "<br>";
	strBuffer += "<br><br>";
	this.setContLinha();
	this.setContLinha();
	this.setContLinha();
	this.setContLinha();
	this.setContLinha();
	strBuffer += "Solicitante dos Serviços<br>";
	strBuffer += "Carimbo/Data<br>";
	this.setContLinha();
	this.setContLinha();
	strBuffer += "</td>";
	strBuffer += "<td style='font:12px arial,sans-serif;border:1px solid black;' align='center'>";
	strBuffer += "<br>";
	strBuffer += "<br>";
	strBuffer += "<br>";
	strBuffer += "<br><br>";
	this.setContLinha();
	this.setContLinha();
	this.setContLinha();
	this.setContLinha();
	this.setContLinha();
	strBuffer += "Gestor Operacional do Contrato<br>";
	strBuffer += "Carimbo/Data<br>";
	strBuffer += "</td>";
	strBuffer += "</tr>";

	strBuffer += "<tr>";
	this.setContLinha();
	strBuffer += "<td style='font:12px arial,sans-serif;border:1px solid black;background-color: #F2F2F2; ' align='center'>";
	strBuffer += "<b>Aprova&ccedil;&atilde;o</b>";
	strBuffer += "</td>";
	strBuffer += "<td style='font:12px arial,sans-serif;border:1px solid black;background-color: #F2F2F2; ' align='center'>";
	strBuffer += "<b>Execu&ccedil;&atilde;o</b>";
	strBuffer += "</td>";
	strBuffer += "</tr>";

	strBuffer += "<tr>";
	strBuffer += "<td style='font:12px arial,sans-serif;border:1px solid black;' align='center'>";
	strBuffer += "<br>";
	strBuffer += "<br>";
	strBuffer += "<br>";
	strBuffer += "<br><br>";
	this.setContLinha();
	this.setContLinha();
	this.setContLinha();
	this.setContLinha();
	this.setContLinha();
	strBuffer += "Gestor do Contrato<br>";
	strBuffer += "Carimbo/Data<br>";
	this.setContLinha();
	this.setContLinha();
	strBuffer += "</td>";
	strBuffer += "<td style='font:12px arial,sans-serif;border:1px solid black;' align='center'>";
	strBuffer += "Aceite da Contratada: ( ) Total () Parcial - Apresentará recurso<br>";
	strBuffer += "<br>";
	strBuffer += "<br>";
	strBuffer += "<br><br>";
	this.setContLinha();
	this.setContLinha();
	this.setContLinha();
	this.setContLinha();
	this.setContLinha();
	strBuffer += "Preposto da Contratada<br>";
	strBuffer += "Carimbo/Data<br>";
	strBuffer += "</td>";
	strBuffer += "</tr>";

	strBuffer += "</table>";
	strBuffer += "<br><br>";
	this.setContLinha();
	this.setContLinha();
	strBuffer += "</table >";

	String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles/" + user.getIdEmpregado();
	String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles" + "/" + user.getIdEmpregado();
	File f = new File(diretorioReceita);
	if (!f.exists()) {
	    f.mkdirs();
	}
	Date dt = new Date();
	String strCompl = "" + dt.getTime();
	String arquivoReceita = diretorioReceita + "/osAux_" + strCompl + ".pdf";
	String arquivoFormRefact = diretorioReceita + "/os_" + strCompl + ".pdf";
	String arquivoRelativoOS = diretorioRelativoOS + "/os_" + strCompl + ".pdf";
	f = new File(arquivoReceita);
	if (f.exists()) {
	    f.delete();
	}
	f = null;
	Thread.sleep(50);
	{
	    // Imprimir em pdf

	    OutputStream os = new FileOutputStream(arquivoReceita);
	    strBuffer = UtilHTML.encodeHTML(strBuffer);
	    Html2Pdf.convert(strBuffer, os);
	    os.flush();
	    os.close();
	    os = null;
	    Thread.sleep(100);

	    PdfReader reader = new PdfReader(arquivoReceita);
	    PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(arquivoFormRefact));
	    PdfContentByte over;
	    int total = reader.getNumberOfPages() + 1;
	    for (int i2 = 1; i2 < total; i2++) {
		over = stamper.getUnderContent(i2);
		over.beginText();
		BaseFont bf = BaseFont.createFont("Helvetica", BaseFont.WINANSI, false);
		over.setFontAndSize(bf, 8);
		over.setTextMatrix(30, 30);
		over.showText("Pagina: " + i2 + " de " + (total - 1) + "     impresso em: " + UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, UtilDatas.getDataHoraAtual(), WebUtil.getLanguage(request)) + " por: " + usuarioImpressao);
		over.endText();
		over.stroke();
	    }
	    stamper.close();
	    stamper = null;
	    Thread.sleep(200);

	    document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + arquivoRelativoOS + "')");
	}
    }

    /**
     * Metodo para fazer a impressão de Fatura Contratos
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     */
    public void imprimirFaturaContrato(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	InformacoesContratoDTO informacoesDto = (InformacoesContratoDTO) document.getBean();
	if (informacoesDto.getIdContrato() == null) {
	    document.alert("Informe o contrato!");
	    return;
	}
	UsuarioDTO user = WebUtil.getUsuario(request);
	if (user == null) {
	    document.alert("O usuário não está logado! Favor logar no sistema!");
	    return;
	}
	String usuarioImpressao = user.getNomeUsuario();
	double valorApurado = 0;
	double percentualGlosa = 0;
	double valorGlosa = 0;
	double totalQuantidadeDemanda = 0;
	double totalUstPrevista = 0;
	double totalUstRealizadas = 0;
	double totalUstGlosadas = 0;
	double totalUstTotal = 0;
	double totalValorAutorizado = 0;
	double ustRealizada = 0;
	double realizado = 0;
	double ustTotal = 0;
	double valorAutorizado = 0;
	double cotacaoMoeda = 0;
	double totalUstPrevistoPerido = 0;
	int cont = 0;
	int cont2 = 0;
	OSService osService = (OSService) ServiceLocator.getInstance().getService(OSService.class, null);
	AtividadesOSService atividadesOSService = (AtividadesOSService) ServiceLocator.getInstance().getService(AtividadesOSService.class, null);
	GlosaOSService glosaOSService = (GlosaOSService) ServiceLocator.getInstance().getService(GlosaOSService.class, null);
	ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
	FornecedorService fornecedorService = (FornecedorService) ServiceLocator.getInstance().getService(FornecedorService.class, null);
	ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
	FaturaService faturaService = (FaturaService) ServiceLocator.getInstance().getService(FaturaService.class, null);
	AcordoNivelServicoContratoService acordoNivelServicoContratoService = (AcordoNivelServicoContratoService) ServiceLocator.getInstance()
		.getService(AcordoNivelServicoContratoService.class, null);
	FaturaApuracaoANSService faturaApuracaoANSService = (FaturaApuracaoANSService) ServiceLocator.getInstance().getService(FaturaApuracaoANSService.class, null);

	new OSDTO();
	Collection<OSDTO> listIdOs = osService.listOSAssociadasFatura(informacoesDto.getIdFatura());

	FaturaDTO faturaDto = new FaturaDTO();
	faturaDto.setIdFatura(informacoesDto.getIdFatura());
	faturaDto = (FaturaDTO) faturaService.restore(faturaDto);

	ContratoDTO contratoDto = new ContratoDTO();
	contratoDto.setIdContrato(informacoesDto.getIdContrato());
	contratoDto = (ContratoDTO) contratoService.restore(contratoDto);

	FornecedorDTO fornecedorDto = new FornecedorDTO();
	fornecedorDto.setIdFornecedor(contratoDto.getIdFornecedor());
	fornecedorDto = (FornecedorDTO) fornecedorService.restore(fornecedorDto);

	new FaturaApuracaoANSDTO();
	Collection<FaturaApuracaoANSDTO> ColecaoIdFaturaApuracao = faturaApuracaoANSService.findByIdFatura(informacoesDto.getIdFatura());

	String strBuffer = "";

	strBuffer += "<table class='table table-bordered table-striped' width='100%' cellpadding='0' cellspacing='0'>";
	strBuffer += "<tr>";
	strBuffer += "<td style='border:1px solid black; background-color: #FFFFE0; font:12px arial,sans-serif;' align='center'>";
	strBuffer += "RELATÓRIO MENSAL DE SERVIÇOS" + "<br>";
	this.setContLinha();
	strBuffer += "RESULTADOS GLOBAIS" + "<br><br>";
	this.setContLinha();
	this.setContLinha();
	strBuffer += "Contrato Número " + contratoDto.getNumero() + "<br>";
	this.setContLinha();
	strBuffer += fornecedorDto.getNomeFantasia();
	strBuffer += "</td>";
	strBuffer += "<td style='border:1px solid black; background-color: #FFFFE0;font:12px arial,sans-serif;' align='center'>";
	strBuffer += "Período de Validação:" + "<br><br>";
	this.setContLinha();
	this.setContLinha();
	strBuffer += faturaDto.getDescricaoFatura() + "<br>";
	this.setContLinha();
	strBuffer += "In&iacute;cio: " + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, faturaDto.getDataInicial(), WebUtil.getLanguage(request)) + "<br>";
	this.setContLinha();
	strBuffer += "Final: " + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, faturaDto.getDataFinal(), WebUtil.getLanguage(request)) + "<br>";
	this.setContLinha();
	strBuffer += "</td>";
	strBuffer += "</tr>";
	strBuffer += "</table><br>";

	strBuffer += "<table class='table table-bordered table-striped' width='100%' cellpadding='0' cellspacing='0'>";
	strBuffer += "<tr>";
	strBuffer += "<td style='border:1px solid black; text-align=right; width:40%; font:12px arial,sans-serif;' width='40%'>";
	strBuffer += "<b>Histórico</b>";
	strBuffer += "</td>";
	strBuffer += "<td style='border:1px solid black; text-align=right; width:20%; font:12px arial,sans-serif;'' width='20%' ALIGN=RIGHT>";
	strBuffer += "<b>Quantidade UST</b>";
	strBuffer += "</td>";
	strBuffer += "<td style='border:1px solid black; text-align=right; width:30%; font:12px arial,sans-serif;'' width='30%' ALIGN=RIGHT>";
	strBuffer += "<b>Valor Financeiro R$</b>";
	strBuffer += "</td>";
	strBuffer += "</tr>";

	for (OSDTO os : listIdOs) {
	    Double quantidadeDemanda = atividadesOSService.retornarCustoAtividadeOSByIdOs(os.getIdOS());
	    if (quantidadeDemanda != null) {
		totalUstPrevista = totalUstPrevista + quantidadeDemanda;
	    }
	    if (atividadesOSService.retornarQtdExecucao(os.getIdOS()) != null) {
		realizado = atividadesOSService.retornarQtdExecucao(os.getIdOS());
		ustRealizada = ustRealizada + realizado;
	    }
	    if (ustRealizada != 0) {
		totalUstRealizadas = totalUstRealizadas + ustRealizada;
	    }
	    Double glosaAtividade = atividadesOSService.retornarGlosaAtividadeOSByIdOs(os.getIdOS());
	    Double custoGlosa = glosaOSService.retornarCustoGlosaOSByIdOs(os.getIdOS());
	    Double totalglosasAtividades = 0.0;
	    if (glosaAtividade != null) {
		totalglosasAtividades = glosaAtividade + custoGlosa;
	    }
	    ustTotal = ustRealizada - totalglosasAtividades;
	    cotacaoMoeda = faturaDto.getValorCotacaoMoeda();
	    valorAutorizado = ustTotal * cotacaoMoeda;
	    if (valorAutorizado != 0) {
		totalValorAutorizado = totalValorAutorizado + valorAutorizado;
	    }
	}
	if (faturaDto.getSaldoPrevisto() != null) {
	    this.setContLinha();
	}

	strBuffer += "<tr>";
	strBuffer += "<td style='border:1px solid black; width:40%; font:12px arial,sans-serif;'' width='40%'>";
	strBuffer += "Total de UST prevista no per&iacute;odo";
	strBuffer += "</td>";
	strBuffer += "<td style='border:1px solid black; text-align=right; width:20%;font:12px arial,sans-serif;'' width='20%' ALIGN=RIGHT>";
	if (totalUstPrevista != 0) {
	    strBuffer += UtilFormatacao.formatDouble(totalUstPrevista, 2) + "<br>";
	} else {
	    strBuffer += "0,00";
	}

	strBuffer += "</td>";
	strBuffer += "<td style='border:1px solid black; text-align=right; width:30%;font:12px arial,sans-serif;'' width='30%' ALIGN=RIGHT>";
	if (totalUstPrevista != 0) {
	    totalUstPrevistoPerido = totalUstPrevista * faturaDto.getValorCotacaoMoeda();
	    strBuffer += UtilFormatacao.formatDouble(totalUstPrevistoPerido, 2) + "<br>";
	    this.setContLinha();
	} else {
	    strBuffer += "";
	}
	strBuffer += "</td>";
	strBuffer += "</tr>";

	strBuffer += "<tr>";
	strBuffer += "<td style='border:1px solid black; width:40%;font:12px arial,sans-serif;'' width='40%'>";
	strBuffer += "Total de UST aprovado no per&iacute;odo";
	strBuffer += "</td>";
	strBuffer += "<td style='border:1px solid black; background-color: #FFFFE0; width:20%;font:12px arial,sans-serif;' width='20%' ALIGN=RIGHT>";
	if (totalUstRealizadas != 0) {
	    strBuffer += "<b>" + UtilFormatacao.formatDouble(totalUstRealizadas, 2) + "</b><br>";
	    this.setContLinha();
	} else {
	    strBuffer += "0,00";
	}

	strBuffer += "</td>";
	strBuffer += "<td style='border:1px solid black; background-color: #FFFFE0; width:30%;font:12px arial,sans-serif;' width='30%' ALIGN=RIGHT>";
	if (totalValorAutorizado != 0) {
	    strBuffer += "<b>" + UtilFormatacao.formatDouble(totalValorAutorizado, 2) + "</b><br>";
	    this.setContLinha();
	} else {
	    strBuffer += "";
	}

	strBuffer += "</td>";
	strBuffer += "</tr>";
	strBuffer += "</table>";
	strBuffer += "<span style='font:8px arial,sans-serif; background-color: #FFFFE0;'></span><br>";
	this.setContLinha();

	strBuffer += "<td style='font:12px arial,sans-serif;'>";
	strBuffer += "<b>LISTA DE ORDENS DE SERVIÇOS ATESTADAS NO PERÍODO</b>";
	strBuffer += "<table class='table table-bordered table-striped' width='100%' cellpadding='0' cellspacing='0'>";
	strBuffer += "<tr>";
	strBuffer += "<td valign='top' style='border:1px solid black; font:12px arial,sans-serif;' width='13%'>";
	strBuffer += "<b>OS</b>";
	strBuffer += "</td>";
	strBuffer += "<td valign='top' style='border:1px solid black;font:12px arial,sans-serif;' width='12%' align=center>";
	strBuffer += "<b>TAREFA</b>";
	strBuffer += "</td>";
	strBuffer += "<td valign='top' style='border:1px solid black; font:12px arial,sans-serif;' width='12%' align=center>";
	strBuffer += "<b>Quantidade Demanda</b>";
	strBuffer += "</td>";
	strBuffer += "<td valign='top' style='border:1px solid black; font:12px arial,sans-serif;' width='13%' align=center>";
	strBuffer += "<b>UST Prevista</b>";
	strBuffer += "</td>";
	strBuffer += "<td valign='top' style='border:1px solid black; font:12px arial,sans-serif;' width='12%' align=center>";
	strBuffer += "<b>UST Realizada</b>";
	strBuffer += "</td>";
	strBuffer += "<td valign='top' style='border:1px solid black; font:12px arial,sans-serif;' width='12%' align=center>";
	strBuffer += "<b>UST Glosada</b>";
	strBuffer += "</td>";
	strBuffer += "<td valign='top' style='border:1px solid black; font:12px arial,sans-serif;' width='13%' align=center>";
	strBuffer += "<b>UST Total</b>";
	strBuffer += "</td>";
	strBuffer += "<td valign='top' style='border:1px solid black; font:12px arial,sans-serif;' width='13%' align=center>";
	strBuffer += "<b>Valor Autorizado R$</b>";
	strBuffer += "</td>";
	strBuffer += "</tr>";
	totalUstPrevista = 0;
	realizado = 0;
	ustRealizada = 0;
	totalUstRealizadas = 0;
	ustTotal = 0;
	cotacaoMoeda = 0;
	valorAutorizado = 0;
	totalValorAutorizado = 0;

	for (OSDTO os : listIdOs) {

	    if (atividadesOSService.retornarQtdExecucao(os.getIdOS()) != null) {
		realizado = atividadesOSService.retornarQtdExecucao(os.getIdOS());
		ustRealizada = ustRealizada + realizado;
	    }

	    strBuffer += "<tr>";
	    strBuffer += "<td style='border:1px solid black; font:12px arial,sans-serif;' width='13%' >";
	    strBuffer += os.getNumero();
	    strBuffer += "</td>";
	    strBuffer += "<td style='border:1px solid black; font:12px arial,sans-serif;' width='12%' >";
	    String servico = servicoService.retornarSiglaPorIdOs(os.getIdOS());
	    strBuffer += servico;
	    strBuffer += "</td>";
	    strBuffer += "<td style='border:1px solid black; font:12px arial,sans-serif;' width='12%' ALIGN=RIGHT>";
	    Double quantidadeDemanda = atividadesOSService.retornarCustoAtividadeOSByIdOs(os.getIdOS());
	    if (quantidadeDemanda != null) {
		strBuffer += UtilFormatacao.formatDouble(quantidadeDemanda, 2) + "<br>";
		totalQuantidadeDemanda = totalQuantidadeDemanda + quantidadeDemanda;
	    } else {
		strBuffer += "0,00";
	    }

	    strBuffer += "</td>";
	    strBuffer += "<td style='border:1px solid black; font:12px arial,sans-serif;' width='13%' ALIGN=RIGHT>";
	    if (quantidadeDemanda != null) {
		strBuffer += UtilFormatacao.formatDouble(quantidadeDemanda, 2) + "<br>";
		totalUstPrevista = totalUstPrevista + quantidadeDemanda;
	    } else {
		strBuffer += "0,00";
	    }
	    strBuffer += "</td>";
	    strBuffer += "<td style='border:1px solid black; font:12px arial,sans-serif;' width='12%' ALIGN=RIGHT>";
	    if (ustRealizada != 0) {
		strBuffer += UtilFormatacao.formatDouble(ustRealizada, 2);
		totalUstRealizadas = totalUstRealizadas + ustRealizada;
	    } else {
		strBuffer += "0,00";
	    }
	    strBuffer += "</td>";
	    strBuffer += "<td style='border:1px solid black; font:12px arial,sans-serif;' width='12%' ALIGN=RIGHT>";

	    Double glosaAtividade = atividadesOSService.retornarGlosaAtividadeOSByIdOs(os.getIdOS());
	    Double custoGlosa = glosaOSService.retornarCustoGlosaOSByIdOs(os.getIdOS());
	    Double totalglosasAtividades = 0.0;
	    if (glosaAtividade != null) {
		totalglosasAtividades = glosaAtividade + custoGlosa;
	    } else {
		strBuffer += "0,00";
	    }
	    strBuffer += UtilFormatacao.formatDouble(totalglosasAtividades, 2);
	    totalUstGlosadas = totalUstGlosadas + totalglosasAtividades;
	    strBuffer += "</td>";
	    strBuffer += "<td style='border:1px solid black; font:12px arial,sans-serif;' width='13%' ALIGN=RIGHT>";
	    ustTotal = ustRealizada - totalglosasAtividades;
	    if (ustTotal != 0) {
		strBuffer += UtilFormatacao.formatDouble(ustTotal, 2);
		totalUstTotal = totalUstTotal + ustTotal;
	    } else {
		strBuffer += "0,00";
	    }
	    strBuffer += "</td>";
	    strBuffer += "<td style='border:1px solid black; font:12px arial,sans-serif;' width='13%' ALIGN=RIGHT>";
	    strBuffer += "<b style ='float:left;font:12px arial,sans-serif;'></b>";
	    cotacaoMoeda = faturaDto.getValorCotacaoMoeda();
	    valorAutorizado = ustTotal * cotacaoMoeda;
	    if (valorAutorizado != 0) {
		strBuffer += UtilFormatacao.formatDouble(valorAutorizado, 2);
		totalValorAutorizado = totalValorAutorizado + valorAutorizado;
	    } else {
		strBuffer += "0,00";
	    }
	    strBuffer += "</td>";
	    strBuffer += "</tr>";
	    cont = cont + 1;

	}
	strBuffer += "<tr>";
	strBuffer += "<td style='border:1px solid black; font:12px arial,sans-serif;' width='15%' colspan='2'>";
	strBuffer += "<b>TOTAL DAS O.S.</b>";
	strBuffer += "</td>";
	strBuffer += "<td style='border:1px solid black; font:12px arial,sans-serif;' width='13,5%' ALIGN=RIGHT >";
	strBuffer += "<b>" + UtilFormatacao.formatDouble(totalQuantidadeDemanda, 2) + "</b>";
	strBuffer += "</td>";
	strBuffer += "<td style='border:1px solid black;font:12px arial,sans-serif;' width='13,5%' ALIGN=RIGHT >";
	strBuffer += "<b>" + UtilFormatacao.formatDouble(totalUstPrevista, 2) + "</b>";
	strBuffer += "</td>";
	strBuffer += "<td style='border:1px solid black; font:12px arial,sans-serif;' width='14%' ALIGN=RIGHT>";
	strBuffer += "<b>" + UtilFormatacao.formatDouble(totalUstRealizadas, 2) + "</b>";
	strBuffer += "</td>";
	strBuffer += "<td style='border:1px solid black; font:12px arial,sans-serif;' width='14%' ALIGN=RIGHT>";
	strBuffer += "<b>" + UtilFormatacao.formatDouble(totalUstGlosadas, 2) + "</b>";
	strBuffer += "</td>";
	strBuffer += "<td style='border:1px solid black; font:12px arial,sans-serif;' width='15%' ALIGN=RIGHT>";
	strBuffer += "<b>" + UtilFormatacao.formatDouble(totalUstTotal, 2) + "</b>";
	strBuffer += "</td>";
	strBuffer += "<td style='border:1px solid black; font:12px arial,sans-serif;' width='15%' ALIGN=RIGHT>";
	strBuffer += "<b style ='float:left;font:12px arial,sans-serif;'></b>";
	strBuffer += "<b>" + UtilFormatacao.formatDouble(totalValorAutorizado, 2) + "</b>";
	strBuffer += "</td>";
	strBuffer += "</tr>";
	strBuffer += "<tr>";
	strBuffer += "<td style='border:1px solid black; font:12px arial,sans-serif;' width='30%' ' colspan='4'>";
	strBuffer += "<b>QUALIDADE (Base de Cálculo)</b>";
	strBuffer += "</td>";
	strBuffer += "<td style='border:1px solid black; font:12px arial,sans-serif;' width='10%' ALIGN=RIGHT>";
	strBuffer += "<b>" + UtilFormatacao.formatDouble(totalUstRealizadas, 2) + "</b>";
	strBuffer += "</td>";
	strBuffer += "<td style='border:1px solid black; font:12px arial,sans-serif;' width='10%' ALIGN=RIGHT>";
	strBuffer += "<b>" + UtilFormatacao.formatDouble(totalUstGlosadas, 2) + "</b>";
	strBuffer += "</td>";
	strBuffer += "<td style='border:1px solid black; font:12px arial,sans-serif;' width='15%' ALIGN=RIGHT>";
	strBuffer += "<b>" + UtilFormatacao.formatDouble(totalUstTotal, 2) + "</b>";
	strBuffer += "</td>";
	strBuffer += "<td style='border:1px solid black; font:12px arial,sans-serif;' width='15%' ALIGN=RIGHT>";
	strBuffer += "<b style ='float:left;font:12px arial,sans-serif;'></b>";
	strBuffer += "<b>" + UtilFormatacao.formatDouble(totalValorAutorizado, 2) + "</b>";
	strBuffer += "</td>";
	strBuffer += "</tr>";
	strBuffer += "<tr>";
	strBuffer += "<td style='border:1px solid black; font:12px arial,sans-serif;' width='30%' colspan='4' >";
	strBuffer += "<b>TOTAL ATESTE</b>";
	strBuffer += "</td>";
	strBuffer += "<td style='border:1px solid black;font:12px arial,sans-serif;' width='10%' ALIGN=RIGHT>";
	strBuffer += "<b>" + UtilFormatacao.formatDouble(totalUstRealizadas, 2) + "</b>";
	strBuffer += "</td>";
	strBuffer += "<td style='border:1px solid black;font:12px arial,sans-serif;' width='10%' ALIGN=RIGHT>";
	strBuffer += "<b></b>";
	strBuffer += "</td>";
	strBuffer += "<td style='border:1px solid black;font:12px arial,sans-serif;' width='15%' ALIGN=RIGHT>";
	strBuffer += "<b>" + UtilFormatacao.formatDouble(totalUstTotal, 2) + "</b>";
	strBuffer += "</td>";
	strBuffer += "<td style='border:1px solid black;font:12px arial,sans-serif;' width='15%' ALIGN=RIGHT>";
	strBuffer += "<b style ='float:left;font:12px arial,sans-serif;'></b>";
	strBuffer += "<b>" + UtilFormatacao.formatDouble(totalValorAutorizado, 2) + "</b>";
	strBuffer += "</td>";
	strBuffer += "</tr>";
	strBuffer += "</table><br>";

	strBuffer += "<table class='table table-bordered table-striped' width='100%' cellpadding='0' cellspacing='0'>";
	strBuffer += "<td style='font:12px arial,sans-serif;'>";
	strBuffer += "<b>Análise de desempenho de qualidade do contrato</b>";
	strBuffer += "</td>";
	strBuffer += "</table>";
	strBuffer += "<table class='table table-bordered table-striped' width='100%' cellpadding='0' cellspacing='0'>";
	strBuffer += "<tr>";
	strBuffer += "<td style='border:1px solid black; width:50%;font:12px arial,sans-serif;' width='50%'>";
	strBuffer += "<b>GLOSAS DE QUALIDADE APLICADAS</b>";
	strBuffer += "</td>";
	strBuffer += "<td style='border:1px solid black; width:20%;font:12px arial,sans-serif;' width='20%' ALIGN=CENTER>";
	strBuffer += "<b>Nº DE OCORRÊNCIAS</b>";
	strBuffer += "</td>";
	strBuffer += "<td style='border:1px solid black; width:20%;font:12px arial,sans-serif;' width='20%' ALIGN=CENTER>";
	strBuffer += "<b>% APLICADO</b>";
	strBuffer += "</td>";
	strBuffer += "<td style='border:1px solid black; width:40%;font:12px arial,sans-serif;' width='40%' ALIGN=CENTER>";
	strBuffer += "<b>UST Glosadas</b>";
	strBuffer += "</td>";
	strBuffer += "</tr>";

	for (FaturaApuracaoANSDTO faturaApuracaoANSDTO : ColecaoIdFaturaApuracao) {
	    AcordoNivelServicoContratoDTO acordoNivelServicoContratoDTO = new AcordoNivelServicoContratoDTO();
	    acordoNivelServicoContratoDTO.setIdAcordoNivelServicoContrato(faturaApuracaoANSDTO.getIdAcordoNivelServicoContrato());
	    acordoNivelServicoContratoDTO = (AcordoNivelServicoContratoDTO) acordoNivelServicoContratoService.restore(acordoNivelServicoContratoDTO);

	    strBuffer += "<tr>";
	    strBuffer += "<td style='border:1px solid black; width:50%;font:12px arial,sans-serif;' width='50%' >";
	    strBuffer += acordoNivelServicoContratoDTO.getDescricaoAcordo();
	    strBuffer += "</td>";
	    strBuffer += "<td style='border:1px solid black; width:20%;font:12px arial,sans-serif;' width='20%' ALIGN=CENTER>";
	    if (faturaApuracaoANSDTO.getValorApurado() != null) {
		strBuffer += UtilFormatacao.formatDouble(faturaApuracaoANSDTO.getValorApurado(), 2) + "<br>";
		valorApurado = valorApurado + faturaApuracaoANSDTO.getValorApurado();
	    } else {
		strBuffer += "0,00";
	    }
	    strBuffer += "</td>";
	    strBuffer += "<td style='border:1px solid black; width:20%;font:12px arial,sans-serif;' width='20%' ALIGN=RIGHT>";
	    if (faturaApuracaoANSDTO.getPercentualGlosa() != null) {
		strBuffer += UtilFormatacao.formatDouble(faturaApuracaoANSDTO.getPercentualGlosa(), 2) + "<br>";
		percentualGlosa = percentualGlosa + faturaApuracaoANSDTO.getPercentualGlosa();

	    } else {
		strBuffer += "0,00";
	    }
	    strBuffer += "</td>";
	    strBuffer += "<td style='border:1px solid black; width:40%;font:12px arial,sans-serif;' width='40%' ALIGN=RIGHT>";
	    if (faturaApuracaoANSDTO.getValorGlosa() != null) {
		strBuffer += UtilFormatacao.formatDouble(faturaApuracaoANSDTO.getValorGlosa(), 2) + "<br>";
		valorGlosa = valorGlosa + faturaApuracaoANSDTO.getValorGlosa();
	    } else {
		strBuffer += "0,00";
	    }

	    strBuffer += "</td>";
	    strBuffer += "</tr>";
	    cont2 = cont2 + 1;

	}

	strBuffer += "<tr>";
	strBuffer += "<td style='border:1px solid white; width:40%;font:12px arial,sans-serif;' width='40%' colspan='2'>";
	strBuffer += "TOTAL A SER APLICADO OU MÁXIMO PERMITIDO";
	strBuffer += "</td>";
	strBuffer += "<td style='border:1px solid black; width:20%;font:12px arial,sans-serif;' width='20%' ALIGN=RIGHT>";
	strBuffer += UtilFormatacao.formatDouble(percentualGlosa, 2);
	strBuffer += "</td>";
	strBuffer += "<td style='border:1px solid black; width:40%;font:12px arial,sans-serif;' width='40%' ALIGN=RIGHT>";
	strBuffer += UtilFormatacao.formatDouble(valorGlosa, 2);
	strBuffer += "</td>";
	strBuffer += "</tr>";
	strBuffer += "</table><br>";
	this.setContLinha();

	strBuffer += "<table class='table table-bordered table-striped' width='100%' cellpadding='0' cellspacing='0'>";
	strBuffer += "<tr>";
	strBuffer += "<td style='border:1px solid black; width:50%;font:12px arial,sans-serif;' width='50%'>";
	strBuffer += "<b style='font:12px arial,sans-serif;font-weight:bold;'>Observações:</b>" + "<br><br>";
	strBuffer += faturaDto.getObservacao() + "<br>";
	strBuffer += "</td>";
	strBuffer += "</tr>";
	strBuffer += "</table><br>";
	this.setContLinha();

	strBuffer += "<table class='table table-bordered table-striped' width='100%' cellpadding='0' cellspacing='0'>";
	if (cont >= 23) {
	    strBuffer += "<div style='page-break-before: always;'></div>";
	} else {
	    if (cont2 >= 2) {
		strBuffer += "<div style='page-break-before: always;'></div>";
	    }
	}
	strBuffer += "<tr>";
	strBuffer += "<td style='font:12px arial,sans-serif;'>";
	strBuffer += "<b>APROVAÇÃO DO FISCAL</b><br>";
	this.setContLinha();
	strBuffer += "</td>";
	strBuffer += "</tr>";
	strBuffer += "</table>";
	strBuffer += "<table style='border:1px solid black; width=100%;' width='100%' cellpadding='0' cellspacing='0'>";
	strBuffer += "<tr>";
	strBuffer += "<td>";
	strBuffer += "<b style='font: bold 12px arial,serif;' >Ao Gestor do Contrato</b><br>";
	this.setContLinha();
	strBuffer += "<b style='font:italic bold 12px arial,serif;'>" + faturaDto.getAprovacaoGestor() + "</b>";

	strBuffer += "" + "<br><br><br>";
	this.setContLinha();
	this.setContLinha();
	this.setContLinha();
	strBuffer += "</tr>";
	strBuffer += "<tr>";
	strBuffer += "<td style='font:10px arial,sans-serif;' ALIGN=CENTER>";
	strBuffer += "Fiscal do Contrato" + "<br>";
	this.setContLinha();
	strBuffer += "Carimbo/Data";
	strBuffer += "</td>";
	strBuffer += "</tr>";
	strBuffer += "</table><br>";
	this.setContLinha();

	strBuffer += "<table class='table table-bordered table-striped' width='100%' cellpadding='0' cellspacing='0'>";
	strBuffer += "<td style='font:12px arial,sans-serif;'>";
	strBuffer += "<b>ATESTE DO GESTOR</b><br>";
	strBuffer += "</td>";
	strBuffer += "</table>";
	strBuffer += "<table style='border:1px solid black; width=100%;' width='100%' cellpadding='0' cellspacing='0'>";
	strBuffer += "<tr>";
	strBuffer += "<td >";
	strBuffer += "<b style='font: bold 12px arial,serif;' >Ao Preposto</b><br>";
	this.setContLinha();
	strBuffer += "<b style='font:italic bold 12px arial,serif;'>" + faturaDto.getAprovacaoFiscal() + "</b><br>";
	this.setContLinha();
	strBuffer += "" + "<br><br><br>";
	this.setContLinha();
	this.setContLinha();
	this.setContLinha();
	strBuffer += "</td>";
	strBuffer += "</tr>";
	strBuffer += "<tr>";
	strBuffer += "<td style='font:10px arial,sans-serif;' ALIGN=CENTER>";
	strBuffer += "Gestor do Contrato" + "<br>";
	strBuffer += "Carimbo/Data";
	strBuffer += "</td>";
	strBuffer += "</tr>";
	strBuffer += "</table><br>";
	this.setContLinha();

	String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles/" + user.getIdEmpregado();
	String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles" + "/" + user.getIdEmpregado();
	File f = new File(diretorioReceita);
	if (!f.exists()) {
	    f.mkdirs();
	}
	Date dt = new Date();
	String strCompl = "" + dt.getTime();
	String arquivoReceita = diretorioReceita + "/fatuaAux_" + strCompl + ".pdf";
	String arquivoFormRefact = diretorioReceita + "/fatura_" + strCompl + ".pdf";
	String arquivoRelativoFatura = diretorioRelativoOS + "/fatura_" + strCompl + ".pdf";
	f = new File(arquivoReceita);
	if (f.exists()) {
	    f.delete();
	}
	f = null;
	Thread.sleep(50);
	{
	    // Imprimir em pdf
	    OutputStream os = new FileOutputStream(arquivoReceita);
	    strBuffer = UtilHTML.encodeHTML(strBuffer);
	    Html2Pdf.convert(strBuffer, os);
	    os.flush();
	    os.close();
	    os = null;
	    Thread.sleep(100);

	    PdfReader reader = new PdfReader(arquivoReceita);
	    PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(arquivoFormRefact));
	    PdfContentByte over;
	    int total = reader.getNumberOfPages() + 1;
	    for (int i2 = 1; i2 < total; i2++) {
		over = stamper.getUnderContent(i2);
		over.beginText();
		BaseFont bf = BaseFont.createFont("Helvetica", BaseFont.WINANSI, false);
		over.setFontAndSize(bf, 8);
		over.setTextMatrix(30, 30);
		over.showText("Pagina: " + i2 + " de " + (total - 1) + "     impresso em: " + UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, UtilDatas.getDataHoraAtual(), WebUtil.getLanguage(request)) + " por: " + usuarioImpressao);
		over.endText();
		over.stroke();
	    }
	    stamper.close();
	    stamper = null;
	    Thread.sleep(200);

	    document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + arquivoRelativoFatura + "')");
	}
    }

    public void listarRegistrosPaciente(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	InformacoesContratoDTO informacoesContratoDTO = (InformacoesContratoDTO) document.getBean();
	UsuarioDTO usuario = WebUtil.getUsuario(request);
	if (usuario == null) {
	    document.alert("Sessão expirada! Favor efetuar logon novamente!");
	    return;
	}
	ContratoQuestionariosService contratoQuestionariosService = (ContratoQuestionariosService) ServiceLocator.getInstance().getService(ContratoQuestionariosService.class, null);
	InformacoesContratoConfigService informacoesContratoConfigService = (InformacoesContratoConfigService) ServiceLocator.getInstance().getService(InformacoesContratoConfigService.class, null);
	InformacoesContratoPerfSegService prontuarioEletronicoPerfSegService = (InformacoesContratoPerfSegService) ServiceLocator.getInstance().getService(InformacoesContratoPerfSegService.class,
		null);
	Collection colQuestHist = contratoQuestionariosService.listByIdContratoOrderIdDecrescente(informacoesContratoDTO.getIdContrato());

	String strTable = "<table width=\"100%\">";

	strTable += "<tr>";

	strTable += "<td width=\"8%\" class=\"linhaSubtituloGrid\">";
	strTable += "&nbsp;";
	strTable += "</td>";
	strTable += "<td  width=\"5%\" class=\"linhaSubtituloGrid\" >";
	strTable += "Seq";
	strTable += "</td>";
	strTable += "<td  width=\"18%\" class=\"linhaSubtituloGrid\">";
	strTable += "Data";
	strTable += "</td>";

	strTable += "<td th=\"69%\" class=\"linhaSubtituloGrid\">";
	strTable += "Tipo de Registro";
	strTable += "</td>";
	strTable += "<td  width=\"69%\" class=\"linhaSubtituloGrid\">";
	strTable += "Profissional";
	strTable += "</td>";

	strTable += "</tr>";
	if (colQuestHist != null) {
	    Integer seq = colQuestHist.size();
	    for (Iterator it = colQuestHist.iterator(); it.hasNext();) {
		ContratoQuestionariosDTO contratoQuestDTO = (ContratoQuestionariosDTO) it.next();

		String descricao = "";
		Collection col = null;
		if ((contratoQuestDTO.getAba() != null) && !contratoQuestDTO.getAba().trim().equalsIgnoreCase("")) {
		    col = informacoesContratoConfigService.findByNome(contratoQuestDTO.getAba());
		}
		if ((col != null) && (col.size() > 0)) {
		    InformacoesContratoConfigDTO peCfg = (InformacoesContratoConfigDTO) ((List) col).get(0);

		    Collection colPerfisAssociados = prontuarioEletronicoPerfSegService.findByIdProntuarioEletronicoConfig(peCfg.getIdInformacoesContratoConfig());
		    if (!this.isPerfilUsuarioLogadoInCollection(colPerfisAssociados, usuario)) {
			continue;
		    }
		    descricao = peCfg.getDescricao();
		    if (peCfg.getIdInformacoesContratoConfigPai() != null) {
			InformacoesContratoConfigDTO peCfgAux = new InformacoesContratoConfigDTO();
			peCfgAux.setIdInformacoesContratoConfig(peCfg.getIdInformacoesContratoConfigPai());
			peCfgAux = (InformacoesContratoConfigDTO) informacoesContratoConfigService.restore(peCfgAux);
			if (peCfgAux != null) {
			    descricao = peCfgAux.getDescricao() + " -> " + descricao;
			}
		    }
		} else {
		    continue;
		}

		strTable += "<tr>";

		strTable += "<td class='tdPontilhada'>";
		if ("F".equalsIgnoreCase(contratoQuestDTO.getSituacao())) {
		    strTable += "<img src=\"" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO")
			    + "/produtos/citsmart/imagens/documentoOK.gif\" border=\"0\" onclick=\"chamaEdicaoQuestionario(" + contratoQuestDTO.getIdContrato() + ","
			    + contratoQuestDTO.getIdQuestionario() + ",0, " + contratoQuestDTO.getIdContratoQuestionario() + ", true, 'N', '" + contratoQuestDTO.getAba()
			    + "')\" style=\"cursor:pointer\" >";
		} else {
		    strTable += "<img src=\"" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO")
			    + "/produtos/citsmart/imagens/agendado.gif\" border=\"0\" onclick=\"chamaEdicaoQuestionario(" + contratoQuestDTO.getIdContrato() + ","
			    + contratoQuestDTO.getIdQuestionario() + ",0, " + contratoQuestDTO.getIdContratoQuestionario() + ", false, 'N', '" + contratoQuestDTO.getAba()
			    + "')\" style=\"cursor:pointer\" >";
		}

		strTable += "<img src=\"" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO")
			+ "/produtos/citsmart/imagens/impressora.gif\" border=\"0\" onclick=\"imprimeQuestionario(" + contratoQuestDTO.getIdContrato() + "," + contratoQuestDTO.getIdQuestionario()
			+ ",0, " + contratoQuestDTO.getIdContratoQuestionario() + ", false, 'N', '" + contratoQuestDTO.getAba() + "')\" style=\"cursor:pointer\" >";

		strTable += "</td>";

		strTable += "<td class='tdPontilhada' style=\"text-align:center\">" + seq.toString() + "</td>";
		strTable += "<td class='tdPontilhada'>";
		strTable += UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, contratoQuestDTO.getDataQuestionario(), WebUtil.getLanguage(request));
		strTable += "</td>";
		strTable += "<td class='tdPontilhada'>";
		strTable += UtilStrings.nullToVazio(descricao) + "&nbsp;";
		strTable += "</td>";
		strTable += "<td class='tdPontilhada'>";
		strTable += UtilStrings.nullToVazio(contratoQuestDTO.getProfissional()) + "&nbsp;";
		strTable += "</td>";

		strTable += "</tr>";
		seq = seq - 1;
	    }
	}
	strTable += "</table>";

	document.getElementById("divRegistros").setInnerHTML(strTable);
    }

    private boolean isPerfilUsuarioLogadoInCollection(Collection colPerfisAssociados, UsuarioDTO usuario) {
	return true;
    }

    public static boolean temAcessoAoRecurso(String strRecurso, HttpServletRequest request, HttpServletResponse response) {
	Collection col = (Collection) request.getSession().getAttribute("acessosUsuario");
	if (col == null) {
	    return false;
	}
	String path = strRecurso;
	if ((path != null) && !path.trim().equalsIgnoreCase("") && !path.endsWith("/sair.load")) {
	    boolean bAutorizado = col.contains(path);
	    if (!bAutorizado) {
		if (path.startsWith("/")) {
		    path = path.substring(1);
		    bAutorizado = col.contains(path); // Faz mais uma tentativa,
		    // mas agora sem a barra
		    // que havia.
		    if (!bAutorizado) {
			return false;
		    } else {
			return true;
		    }
		} else {
		    return false;
		}
	    } else {
		return true;
	    }
	}
	return false;
    }

    /**
     * @return the contLinha
     */
    public int getContLinha() {
	return this.contLinha;
    }

    /**
     * @param contLinha
     *            the contLinha to set
     */
    public void setContLinha() {
	this.contLinha = this.contLinha + 1;
    }

    public void imprimirRelatorioOrdemServico(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, String saida) throws Exception {

	UsuarioDTO user = WebUtil.getUsuario(request);
	if (user == null) {
	    document.alert("O usuário não está logado! Favor logar no sistema!");
	    document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
	    return;
	}

	InformacoesContratoDTO informacoesDto = (InformacoesContratoDTO) document.getBean();
	if (informacoesDto.getIdContrato() == null) {
	    document.alert("Informe o contrato!");
	    document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
	    return;
	}

	OSService osService = (OSService) ServiceLocator.getInstance().getService(OSService.class, null);
	ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
	FornecedorService fornecedorService = (FornecedorService) ServiceLocator.getInstance().getService(FornecedorService.class, null);
	ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
	AtividadesOSService atividadesOSService = (AtividadesOSService) ServiceLocator.getInstance().getService(AtividadesOSService.class, null);
	AcordoNivelServicoService acordoNivelServicoService = (AcordoNivelServicoService) ServiceLocator.getInstance().getService(AcordoNivelServicoService.class, null);
	MoedaService moedaService = (MoedaService) ServiceLocator.getInstance().getService(MoedaService.class, null);
	ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
	ServiceLocator.getInstance().getService(TipoEventoServicoService.class, null);
	TipoServicoService tipoServicoService = (TipoServicoService) ServiceLocator.getInstance().getService(TipoServicoService.class, null);
	ClienteService clienteService = (ClienteService) ServiceLocator.getInstance().getService(ClienteService.class, null);

	OSDTO osDto = new OSDTO();
	osDto.setIdOS(informacoesDto.getIdOS());
	osDto = (OSDTO) osService.restore(osDto);

	String ATIVAR_ASSINATURA_PERSONALIZADA_REL_OS = ParametroUtil.getValorParametroCitSmartHashMap(br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.ATIVAR_ASSINATURA_PERSONALIZADA_REL_OS, "N");

	HttpSession session = request.getSession();
	String complexidadeB = "";
	String complexidadeI = "";
	String complexidadeM = "";
	String complexidadeA = "";
	String complexidadeE = "";
	String valorComplexidadeB = "";
	String valorComplexidadeI = "";
	String valorComplexidadeM = "";
	String valorComplexidadeA = "";
	String valorComplexidadeE = "";
	DecimalFormat formatacao = new DecimalFormat("0.##");

	ServicoContratoDTO servicoContratoDTO = new ServicoContratoDTO();
	servicoContratoDTO.setIdServicoContrato(osDto.getIdServicoContrato());
	servicoContratoDTO = (ServicoContratoDTO) servicoContratoService.restore(servicoContratoDTO);

	ServicoDTO servicoDto = new ServicoDTO();
	servicoDto.setIdServico(servicoContratoDTO.getIdServico());
	servicoDto = (ServicoDTO) servicoService.restore(servicoDto);

	String nomeTipoServico = "";
	TipoServicoDTO tipoServicoDTO = new TipoServicoDTO();
	if (servicoDto.getIdTipoServico() != null) {
	    tipoServicoDTO.setIdTipoServico(servicoDto.getIdTipoServico());

	    tipoServicoDTO = (TipoServicoDTO) tipoServicoService.restore(tipoServicoDTO);

	    if (tipoServicoDTO != null) {
		nomeTipoServico = UtilStrings.nullToVazio(tipoServicoDTO.getNomeTipoServico());
	    }
	}

	ContratoDTO contratoDto = new ContratoDTO();
	contratoDto.setIdContrato(informacoesDto.getIdContrato());
	contratoDto = (ContratoDTO) contratoService.restore(contratoDto);

	FornecedorDTO fornecedorDto = new FornecedorDTO();
	fornecedorDto.setIdFornecedor(contratoDto.getIdFornecedor());
	fornecedorDto = (FornecedorDTO) fornecedorService.restore(fornecedorDto);

	ClienteDTO clienteDTO = new ClienteDTO();
	clienteDTO.setIdCliente(contratoDto.getIdCliente());
	clienteDTO = (ClienteDTO) clienteService.restore(clienteDTO);
	if (clienteDTO.getNomeFantasia() == null) {
	    clienteDTO.setNomeFantasia("");
	}

	String nomeMoeda = "";
	String nomeEmpresa = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.EMPRESA_Nome, "CITSMart");
	if (contratoDto.getIdMoeda() != null) {
	    MoedaDTO moedaDto = new MoedaDTO();
	    moedaDto.setIdMoeda(contratoDto.getIdMoeda());
	    moedaDto = (MoedaDTO) moedaService.restore(moedaDto);
	    if (moedaDto != null) {
		nomeMoeda = "" + moedaDto.getNomeMoeda() + "";
	    }
	}

	Collection<ResultadosEsperadosDTO> colAcordosServicoContrato = null;
	Collection<ComplexidadeDTO> listaComplexidadePorContrato = null;

	Collection colAtividades = null;
	if (osDto.getIdServicoContrato() != null) {
	    colAcordosServicoContrato = acordoNivelServicoService.consultaPorIdServicoContrato(osDto.getIdServicoContrato());
	    listaComplexidadePorContrato = contratoService.listaComplexidadePorContrato(osDto.getIdServicoContrato());
	}

	if (informacoesDto.getIdOS() != null) {
	    colAtividades = atividadesOSService.findByIdOS(informacoesDto.getIdOS());
	}

	if (colAtividades == null) {
	    document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
	    document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
	    return;
	}

	if (listaComplexidadePorContrato != null) {
	    for (ComplexidadeDTO complexidadeDto : listaComplexidadePorContrato) {
		if (complexidadeDto.getComplexidade().equalsIgnoreCase("B")) {
		    complexidadeB = "Baixa";
		    if (complexidadeDto.getValorComplexidade() == null) {
			complexidadeDto.setValorComplexidade(new BigDecimal(0));
		    } else {
			valorComplexidadeB = formatacao.format(complexidadeDto.getValorComplexidade().doubleValue());
		    }
		}
		if (complexidadeDto.getComplexidade().equalsIgnoreCase("I")) {
		    complexidadeI = "Intermediaria";
		    if (complexidadeDto.getValorComplexidade() == null) {
			complexidadeDto.setValorComplexidade(new BigDecimal(0));
		    } else {
			valorComplexidadeI = formatacao.format(complexidadeDto.getValorComplexidade().doubleValue());
		    }
		}
		if (complexidadeDto.getComplexidade().equalsIgnoreCase("M")) {
		    complexidadeM = "Mediana";
		    if (complexidadeDto.getValorComplexidade() == null) {
			complexidadeDto.setValorComplexidade(new BigDecimal(0));
		    } else {
			valorComplexidadeM = formatacao.format(complexidadeDto.getValorComplexidade().doubleValue());
		    }
		}
		if (complexidadeDto.getComplexidade().equalsIgnoreCase("A")) {
		    complexidadeA = "Alta";
		    if (complexidadeDto.getValorComplexidade() == null) {
			complexidadeDto.setValorComplexidade(new BigDecimal(0));
		    } else {
			valorComplexidadeA = formatacao.format(complexidadeDto.getValorComplexidade().doubleValue());
		    }
		}
		if (complexidadeDto.getComplexidade().equalsIgnoreCase("E")) {
		    complexidadeE = "Especialista";
		    if (complexidadeDto.getValorComplexidade() == null) {
			complexidadeDto.setValorComplexidade(new BigDecimal(0));
		    } else {
			valorComplexidadeE = formatacao.format(complexidadeDto.getValorComplexidade().doubleValue());
		    }
		}
	    }
	}

	double totalOS = 0;
	if (colAtividades != null) {
	    for (Iterator it = colAtividades.iterator(); it.hasNext();) {
		AtividadesOSDTO atividadesOSDTO = (AtividadesOSDTO) it.next();
		if (atividadesOSDTO.getComplexidade().equalsIgnoreCase("1")) {
		    atividadesOSDTO.setComplexidade("B");
		}
		if (atividadesOSDTO.getComplexidade().equalsIgnoreCase("2")) {
		    atividadesOSDTO.setComplexidade("I");
		}
		if (atividadesOSDTO.getComplexidade().equalsIgnoreCase("3")) {
		    atividadesOSDTO.setComplexidade("M");
		}
		if (atividadesOSDTO.getComplexidade().equalsIgnoreCase("4")) {
		    atividadesOSDTO.setComplexidade("A");
		}
		if (atividadesOSDTO.getComplexidade().equalsIgnoreCase("5")) {
		    atividadesOSDTO.setComplexidade("E");
		}

		if (atividadesOSDTO.getFormula() == null) {
		    atividadesOSDTO.setFormula("");
		}

		totalOS = totalOS + atividadesOSDTO.getCustoAtividade();

		atividadesOSDTO.setObsAtividade(UtilStrings.nullToVazio(atividadesOSDTO.getObsAtividade()));

		JRDataSource listaAtividadeOs = new JRBeanCollectionDataSource(colAtividades);
		atividadesOSDTO.setListaAtividadeOs(listaAtividadeOs);

		if (colAcordosServicoContrato != null) {
		    JRDataSource listaAcordoNivelServico = new JRBeanCollectionDataSource(colAcordosServicoContrato);
		    atividadesOSDTO.setListaAcordoNivelServico(listaAcordoNivelServico);
		}

	    }
	}

	// Configurando dados para geração do Relatório
	StringBuilder jasperArqRel = new StringBuilder();
	jasperArqRel.append("RelatorioOrdemServico");

	Date dt = new Date();
	String strMiliSegundos = Long.toString(dt.getTime());
	String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS");
	String diretorioTemp = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
	String diretorioRelativo = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";
	String arquivoRelatorio = "/" + jasperArqRel.toString() + strMiliSegundos + "_" + user.getIdUsuario();

	Map<String, Object> parametros = new HashMap<String, Object>();
	parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);
	parametros.put("NOME_EMPRESA", nomeEmpresa);
	parametros.put("NOME_CLIENTE", clienteDTO.getNomeFantasia());
	parametros.put("CIDADE", "Brasília,");
	parametros.put("APLICACAO", "CITSMart");
	parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
	parametros.put("NOME_USUARIO", user.getNomeUsuario());
	parametros.put("numeroOs", osDto.getNumero());
	parametros.put("ano", (contratoDto.getDataContrato() != null ? this.converterData(contratoDto.getDataContrato()).toString() : ""));
	parametros.put("contratoNumero", contratoDto.getNumero());
	parametros.put("nomeFantasia", fornecedorDto.getNomeFantasia());
	parametros.put("nomeMoeda", nomeMoeda);
	parametros.put("siglaAbrev", servicoDto.getSiglaAbrev());
	parametros.put("dataInicio", osDto.getDataInicio());
	parametros.put("dataFim", osDto.getDataFim());
	parametros.put("areaRequisitante", osDto.getNomeAreaRequisitante());
	parametros.put("nomeTipoEventoServico", nomeTipoServico);
	parametros.put("demanda", osDto.getDemanda());
	parametros.put("objetivo", osDto.getObjetivo());
	parametros.put("totalOs", totalOS);
	parametros.put("restricoesPressup", servicoContratoDTO.getRestricoesPressup());
	parametros.put("SUBREPORT_DIR", caminhoJasper);

	if (complexidadeB != "") {
	    parametros.put("complexidadeBaixa", complexidadeB);
	    parametros.put("valorComplexidadeBaixa", valorComplexidadeB);
	} else {
	    parametros.put("complexidadeBaixa", "Baixa");
	    parametros.put("valorComplexidadeBaixa", "0");
	}
	if (complexidadeI != "") {
	    parametros.put("complexidadeIntermediaria", complexidadeI);
	    parametros.put("valorComplexidadeIntermediaria", valorComplexidadeI);
	} else {
	    parametros.put("complexidadeIntermediaria", "Intermediaria");
	    parametros.put("valorComplexidadeIntermediaria", "0");
	}
	if (complexidadeM != "") {
	    parametros.put("complexidadeMediana", complexidadeM);
	    parametros.put("valorComplexidadeMediana", valorComplexidadeM);
	} else {
	    parametros.put("complexidadeMediana", "Mediana");
	    parametros.put("valorComplexidadeMediana", "0");
	}
	if (complexidadeA != "") {
	    parametros.put("complexidadeAlta", complexidadeA);
	    parametros.put("valorComplexidadeAlta", valorComplexidadeA);
	} else {
	    parametros.put("complexidadeAlta", "Alta");
	    parametros.put("valorComplexidadeAlta", "0");
	}
	if (complexidadeE != "") {
	    parametros.put("complexidadeEspecialista", complexidadeE);
	    parametros.put("valorComplexidadeEspecialista", valorComplexidadeE);
	} else {
	    parametros.put("complexidadeEspecialista", "Especialista");
	    parametros.put("valorComplexidadeEspecialista", "0");
	}

	GrupoAssinaturaService grupoAssinaturaService = (GrupoAssinaturaService) ServiceLocator.getInstance().getService(GrupoAssinaturaService.class, null);
	Collection colAssinaturas = grupoAssinaturaService.geraListaCamposAssinatura(osDto.getIdGrupoAssinatura(), request);
	JRDataSource dsAssinaturas = new JRBeanCollectionDataSource(colAssinaturas);
	parametros.put("colAssinaturas", dsAssinaturas);
	parametros.put("ATIVAR_ASSINATURA_PERSONALIZADA", ((ATIVAR_ASSINATURA_PERSONALIZADA_REL_OS != null) && (ATIVAR_ASSINATURA_PERSONALIZADA_REL_OS.equalsIgnoreCase("S"))));

	JRDataSource dataSource = new JRBeanCollectionDataSource(colAtividades);

	// Chamando o relatório
	if (saida.equalsIgnoreCase("PDF")) {
	    this.abreRelatorioPDF(dataSource, parametros, diretorioTemp, caminhoJasper, jasperArqRel.toString(), diretorioRelativo, arquivoRelatorio, document, request, response);
	} else {
	    this.abreRelatorioXLS(dataSource, parametros, diretorioTemp, caminhoJasper, jasperArqRel.toString(), diretorioRelativo, arquivoRelatorio, document, request, response);
	}
	document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();

    }

    public void imprimirRelOSPDF(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	this.imprimirRelatorioOrdemServico(document, request, response, "PDF");
    }

    public void imprimirRelOSXLS(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	this.imprimirRelatorioOrdemServico(document, request, response, "XLS");
    }

    public void imprimirRelatorioRAOrdemServicoContrato(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, String saida) throws Exception {

	UsuarioDTO user = WebUtil.getUsuario(request);
	if (user == null) {
	    document.alert("O usuário não está logado! Favor logar no sistema!");
	    document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
	    return;
	}

	InformacoesContratoDTO informacoesDto = (InformacoesContratoDTO) document.getBean();
	if (informacoesDto.getIdContrato() == null) {
	    document.alert("Informe o contrato!");
	    document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
	    return;
	}

	OSService osService = (OSService) ServiceLocator.getInstance().getService(OSService.class, null);
	ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
	FornecedorService fornecedorService = (FornecedorService) ServiceLocator.getInstance().getService(FornecedorService.class, null);
	ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
	AtividadesOSService atividadesOSService = (AtividadesOSService) ServiceLocator.getInstance().getService(AtividadesOSService.class, null);
	ServiceLocator.getInstance().getService(AcordoNivelServicoService.class, null);
	GlosaOSService glosaOSService = (GlosaOSService) ServiceLocator.getInstance().getService(GlosaOSService.class, null);
	MoedaService moedaService = (MoedaService) ServiceLocator.getInstance().getService(MoedaService.class, null);
	ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
	TipoEventoServicoService tipoEventoServicoService = (TipoEventoServicoService) ServiceLocator.getInstance().getService(TipoEventoServicoService.class, null);
	ClienteService clienteService = (ClienteService) ServiceLocator.getInstance().getService(ClienteService.class, null);

	OSDTO osDto = new OSDTO();
	osDto.setIdOS(informacoesDto.getIdOS());
	osDto = (OSDTO) osService.restore(osDto);

	String ATIVAR_ASSINATURA_PERSONALIZADA_REL_OS = ParametroUtil.getValorParametroCitSmartHashMap(br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.ATIVAR_ASSINATURA_PERSONALIZADA_REL_OS, "N");

	HttpSession session = request.getSession();
	double totalCustoPrevisto = 0;
	double totalCustoRealizado = 0;
	double totalCustoGlosaGeral = 0;
	double totalcustoAprovado = 0;
	double totalCustoGlosa = 0;
	double totalCustoRealizadoSegundoParametro = 0;

	String nomeEmpresa = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.EMPRESA_Nome, "CITSMart");

	ServicoContratoDTO servicoContratoDTO = new ServicoContratoDTO();
	servicoContratoDTO.setIdServicoContrato(osDto.getIdServicoContrato());
	servicoContratoDTO = (ServicoContratoDTO) servicoContratoService.restore(servicoContratoDTO);

	ServicoDTO servicoDto = new ServicoDTO();
	servicoDto.setIdServico(servicoContratoDTO.getIdServico());
	servicoDto = (ServicoDTO) servicoService.restore(servicoDto);

	TipoEventoServicoDTO tipoEventoServicoDTO = new TipoEventoServicoDTO();
	if (servicoDto.getIdTipoEventoServico() != null) {
	    tipoEventoServicoDTO.setIdTipoEventoServico(servicoDto.getIdTipoEventoServico());
	    tipoEventoServicoDTO = (TipoEventoServicoDTO) tipoEventoServicoService.restore(tipoEventoServicoDTO);
	    UtilStrings.nullToVazio(tipoEventoServicoDTO.getNomeTipoEventoServico());
	}

	ContratoDTO contratoDto = new ContratoDTO();
	contratoDto.setIdContrato(informacoesDto.getIdContrato());
	contratoDto = (ContratoDTO) contratoService.restore(contratoDto);

	FornecedorDTO fornecedorDto = new FornecedorDTO();
	fornecedorDto.setIdFornecedor(contratoDto.getIdFornecedor());
	fornecedorDto = (FornecedorDTO) fornecedorService.restore(fornecedorDto);

	ClienteDTO clienteDTO = new ClienteDTO();
	clienteDTO.setIdCliente(contratoDto.getIdCliente());
	clienteDTO = (ClienteDTO) clienteService.restore(clienteDTO);
	if (clienteDTO.getNomeFantasia() == null) {
	    clienteDTO.setNomeFantasia("");
	}

	if (contratoDto.getIdMoeda() != null) {
	    MoedaDTO moedaDto = new MoedaDTO();
	    moedaDto.setIdMoeda(contratoDto.getIdMoeda());
	    moedaDto = (MoedaDTO) moedaService.restore(moedaDto);
	}
	Collection<AtividadesOSDTO> colAtividades = atividadesOSService.findByIdOS(informacoesDto.getIdOS());

	if (colAtividades == null) {
	    document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
	    document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
	    return;
	}

	Collection<GlosaOSDTO> colGlosa = glosaOSService.listaDeGlosas(informacoesDto.getIdOS());
	if (colAtividades != null) {
	    for (Object element : colAtividades) {
		AtividadesOSDTO atividade = (AtividadesOSDTO) element;
		totalCustoPrevisto = totalCustoPrevisto + atividade.getCustoAtividade();
		if (atividade.getGlosaAtividade() != null) {
		    // totalCustoGlosaGeral = totalCustoGlosaGeral +
		    // atividade.getGlosaAtividade();
		    totalCustoGlosa = totalCustoGlosa + atividade.getGlosaAtividade();

		    if ((atividade.getGlosaAtividade() != null) && (atividade.getCustoAtividade() != null)) {
			double qtdeExec = 0;
			if (atividade.getQtdeExecutada() != null) {
			    qtdeExec = atividade.getQtdeExecutada();
			    atividade.setCustoRealizado(qtdeExec);
			}
			atividade.setCustos(qtdeExec - atividade.getGlosaAtividade());
			totalcustoAprovado = totalcustoAprovado + atividade.getCustos();
		    } else {
			if (atividade.getQtdeExecutada() != null) {
			    atividade.setCustos(atividade.getQtdeExecutada());
			    totalcustoAprovado = totalcustoAprovado + atividade.getQtdeExecutada();
			}
		    }

		}
		if (atividade.getQtdeExecutada() != null) {
		    atividade.setCustoRealizado(atividade.getQtdeExecutada());
		    totalCustoRealizado = totalCustoRealizado + atividade.getCustoRealizado();
		    totalCustoRealizadoSegundoParametro = totalCustoRealizado;
		}
		JRDataSource listaGlosas = new JRBeanCollectionDataSource(colGlosa);
		atividade.setListaGlosasOs(listaGlosas);
	    }
	}

	if (colGlosa != null) {
	    for (GlosaOSDTO glosa : colGlosa) {
		if (glosa.getCustoGlosa() != null) {
		    totalCustoGlosaGeral = totalCustoGlosaGeral + glosa.getCustoGlosa();
		}
	    }
	}

	// Configurando dados para geração do Relatório
	StringBuilder jasperArqRel = new StringBuilder();
	jasperArqRel.append("RelatorioRAOrdemServico");

	Date dt = new Date();
	String strMiliSegundos = Long.toString(dt.getTime());
	String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS");
	String diretorioTemp = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
	String diretorioRelativo = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";
	String arquivoRelatorio = "/" + jasperArqRel.toString() + strMiliSegundos + "_" + user.getIdUsuario();

	Map<String, Object> parametros = new HashMap<String, Object>();
	parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);
	parametros.put("NOME_EMPRESA", nomeEmpresa);
	parametros.put("NOME_CLIENTE", clienteDTO.getNomeFantasia());
	parametros.put("CIDADE", "Brasília,");
	parametros.put("APLICACAO", "CITSMart");
	parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
	parametros.put("NOME_USUARIO", user.getNomeUsuario());
	parametros.put("numeroOs", osDto.getNumero());
	parametros.put("contratoNumero", contratoDto.getNumero());
	parametros.put("nomeFantasia", fornecedorDto.getNomeFantasia());
	parametros.put("siglaAbrev", servicoDto.getSiglaAbrev());
	parametros.put("ano", (contratoDto.getDataContrato() != null ? this.converterData(contratoDto.getDataContrato()).toString() : ""));
	parametros.put("dataInicio", osDto.getDataInicio());
	parametros.put("dataFim", osDto.getDataFim());
	parametros.put("areaRequisitante", osDto.getNomeAreaRequisitante());
	parametros.put("obsFinalizacao", osDto.getObsFinalizacao());
	parametros.put("demanda", osDto.getDemanda());
	parametros.put("totalCustoPrevisto", totalCustoPrevisto);
	parametros.put("totalCustoRealizado", totalCustoRealizado);
	parametros.put("totalCustoGlosaGeral", totalCustoGlosaGeral);
	parametros.put("totalCustoAprovado", totalcustoAprovado);
	parametros.put("totalCustoGlosa", totalCustoGlosa);
	parametros.put("totalCustoRealizadoSegundoParametro", totalCustoRealizadoSegundoParametro);
	parametros.put("SUBREPORT_DIR", caminhoJasper);

	GrupoAssinaturaService grupoAssinaturaService = (GrupoAssinaturaService) ServiceLocator.getInstance().getService(GrupoAssinaturaService.class, null);
	Collection colAssinaturas = grupoAssinaturaService.geraListaCamposAssinatura(osDto.getIdGrupoAssinatura(), request);
	JRDataSource dsAssinaturas = new JRBeanCollectionDataSource(colAssinaturas);
	parametros.put("colAssinaturas", dsAssinaturas);
	parametros.put("ATIVAR_ASSINATURA_PERSONALIZADA", ((ATIVAR_ASSINATURA_PERSONALIZADA_REL_OS != null) && (ATIVAR_ASSINATURA_PERSONALIZADA_REL_OS.equalsIgnoreCase("S"))));

	JRDataSource dataSource = new JRBeanCollectionDataSource(colAtividades);

	// Chamando o relatório
	if (saida.equalsIgnoreCase("PDF")) {
	    this.abreRelatorioPDF(dataSource, parametros, diretorioTemp, caminhoJasper, jasperArqRel.toString(), diretorioRelativo, arquivoRelatorio, document, request, response);
	} else {
	    this.abreRelatorioXLS(dataSource, parametros, diretorioTemp, caminhoJasper, jasperArqRel.toString(), diretorioRelativo, arquivoRelatorio, document, request, response);
	}
	document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
    }

    public void imprimirRelRAOSPDF(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	this.imprimirRelatorioRAOrdemServicoContrato(document, request, response, "PDF");
    }

    public void imprimirRelRAOSXLS(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	this.imprimirRelatorioRAOrdemServicoContrato(document, request, response, "XLS");
    }

    /**
     * Metodo para fazer a impressão de Fatura Contratos
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     */
    public void imprimirRelatorioFaturaContrato(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	HttpSession session = request.getSession();
	InformacoesContratoDTO informacoesDto = (InformacoesContratoDTO) document.getBean();

	if (informacoesDto.getIdContrato() == null) {
	    document.alert("Informe o contrato!");
	    return;
	}
	UsuarioDTO user = WebUtil.getUsuario(request);
	if (user == null) {
	    document.alert("O usuário não está logado! Favor logar no sistema!");
	    return;
	}
	double valorApurado = 0;
	Double percentualGlosa = 0.0;
	Double valorGlosa = 0.0;
	double totalQuantidadeDemanda = 0;
	double totalUstPrevista = 0;
	double totalUstRealizadas = 0;
	double totalUstGlosadas = 0;
	double totalUstTotal = 0;
	double totalValorAutorizado = 0;
	double totalUstPrevistoPerido = 0;

	String nomeEmpresa = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.EMPRESA_Nome, "CITSMart");

	OSService osService = (OSService) ServiceLocator.getInstance().getService(OSService.class, null);
	AtividadesOSService atividadesOSService = (AtividadesOSService) ServiceLocator.getInstance().getService(AtividadesOSService.class, null);
	GlosaOSService glosaOSService = (GlosaOSService) ServiceLocator.getInstance().getService(GlosaOSService.class, null);
	ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
	FornecedorService fornecedorService = (FornecedorService) ServiceLocator.getInstance().getService(FornecedorService.class, null);
	ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
	FaturaService faturaService = (FaturaService) ServiceLocator.getInstance().getService(FaturaService.class, null);
	FaturaApuracaoANSService faturaApuracaoANSService = (FaturaApuracaoANSService) ServiceLocator.getInstance().getService(FaturaApuracaoANSService.class, null);
	ClienteService clienteService = (ClienteService) ServiceLocator.getInstance().getService(ClienteService.class, null);
	AcordoNivelServicoContratoService acordoNivelServicoContratoService = (AcordoNivelServicoContratoService) ServiceLocator.getInstance()
		.getService(AcordoNivelServicoContratoService.class, null);
	MoedaService moedaService = (MoedaService) ServiceLocator.getInstance().getService(MoedaService.class, null);

	Collection<OSDTO> listIdOs = osService.listOSAssociadasFatura(informacoesDto.getIdFatura());

	FaturaDTO faturaDto = new FaturaDTO();
	faturaDto.setIdFatura(informacoesDto.getIdFatura());
	faturaDto = (FaturaDTO) faturaService.restore(faturaDto);

	ContratoDTO contratoDto = new ContratoDTO();
	contratoDto.setIdContrato(informacoesDto.getIdContrato());
	contratoDto = (ContratoDTO) contratoService.restore(contratoDto);

	FornecedorDTO fornecedorDto = new FornecedorDTO();
	fornecedorDto.setIdFornecedor(contratoDto.getIdFornecedor());
	fornecedorDto = (FornecedorDTO) fornecedorService.restore(fornecedorDto);

	OSDTO osDto = new OSDTO();

	ClienteDTO clienteDTO = new ClienteDTO();
	clienteDTO.setIdCliente(contratoDto.getIdCliente());
	clienteDTO = (ClienteDTO) clienteService.restore(clienteDTO);
	if (clienteDTO.getNomeFantasia() == null) {
	    clienteDTO.setNomeFantasia("");
	}

	String nomeMoeda = "";
	MoedaDTO moedaDto = new MoedaDTO();
	if (contratoDto.getIdMoeda() != null) {
	    moedaDto.setIdMoeda(contratoDto.getIdMoeda());
	    moedaDto = (MoedaDTO) moedaService.restore(moedaDto);
	    if (moedaDto != null) {
		nomeMoeda = "" + moedaDto.getNomeMoeda() + "";
	    }
	}

	Collection<FaturaApuracaoANSDTO> colecaoIdFaturaApuracao = faturaApuracaoANSService.findByIdFatura(informacoesDto.getIdFatura());

	if (listIdOs != null) {
	    for (OSDTO os : listIdOs) {
		Double retornoQtdExec = atividadesOSService.retornarQtdExecucao(os.getIdOS());
		if (retornoQtdExec != null) {
		    os.setUstRealizada((double) 0);
		    os.setRealizado(retornoQtdExec);
		    os.setUstRealizada(os.getUstRealizada() + os.getRealizado());
		}

		os.setServico(servicoService.retornarSiglaPorIdOs(os.getIdOS()));
		os.setQuantidadeDemanda(atividadesOSService.retornarCustoAtividadeOSByIdOs(os.getIdOS()));

		if (os.getAno() != null) {
		    osDto.setAno(os.getAno());
		} else {
		    osDto.setAno(0);
		}

		if (os.getQuantidadeDemanda() != null) {
		    totalQuantidadeDemanda = totalQuantidadeDemanda + os.getQuantidadeDemanda();
		    totalUstPrevista = totalUstPrevista + os.getQuantidadeDemanda();
		}

		if (totalUstPrevista != 0) {
		    // Removido, agora a moeda utiliza um flag de uso de cotação
		    // if(moedaDto != null && moedaDto.getNomeMoeda().equalsIgnoreCase(Enumerados.Moeda.UST.getDescricao())){
		    if ((moedaDto != null) && (moedaDto.getUsarCotacao() != null)) {
			totalUstPrevistoPerido = totalUstPrevista * faturaDto.getValorCotacaoMoeda();
		    } else {
			totalUstPrevistoPerido = totalUstPrevista;
		    }
		}

		if (os.getUstRealizada() != null) {
		    os.setTotalglosasAtividades((double) 0);
		    totalUstRealizadas = totalUstRealizadas + os.getUstRealizada();
		    os.setGlosaAtividade(atividadesOSService.retornarGlosaAtividadeOSByIdOs(os.getIdOS()));
		    os.setCustoGlosa(glosaOSService.retornarCustoGlosaOSByIdOs(os.getIdOS()));

		    if ((os.getGlosaAtividade() != null) && (os.getCustoGlosa() != null)) {
			os.setTotalglosasAtividades(os.getCustoGlosa());
		    }

		    totalUstGlosadas = totalUstGlosadas + os.getTotalglosasAtividades();

		    os.setUstTotal(os.getUstRealizada() - os.getTotalglosasAtividades());

		    if (os.getUstTotal() != 0) {
			totalUstTotal = totalUstTotal + os.getUstTotal();
		    }

		    os.setCotacaoMoeda(faturaDto.getValorCotacaoMoeda());

		    // Removido, agora a moeda utiliza um flag de uso de cotação
		    // if(moedaDto != null && moedaDto.getNomeMoeda().equalsIgnoreCase(Enumerados.Moeda.UST.getDescricao())){
		    if ((moedaDto != null) && (moedaDto.getUsarCotacao() != null)) {
			os.setValorAutorizado(os.getUstTotal() * os.getCotacaoMoeda());
		    } else {
			os.setValorAutorizado(os.getUstTotal());
		    }

		    if (os.getValorAutorizado() != 0) {
			totalValorAutorizado = totalValorAutorizado + os.getValorAutorizado();
		    }

		}

		if (colecaoIdFaturaApuracao != null) {
		    valorGlosa = 0.0;
		    for (FaturaApuracaoANSDTO faturaApuracaoANSDTO : colecaoIdFaturaApuracao) {
			AcordoNivelServicoContratoDTO acordoNivelServicoContratoDTO = new AcordoNivelServicoContratoDTO();
			acordoNivelServicoContratoDTO.setIdAcordoNivelServicoContrato(faturaApuracaoANSDTO.getIdAcordoNivelServicoContrato());
			acordoNivelServicoContratoDTO = (AcordoNivelServicoContratoDTO) acordoNivelServicoContratoService.restore(acordoNivelServicoContratoDTO);
			if (acordoNivelServicoContratoDTO.getDescricaoAcordo() != null) {
			    faturaApuracaoANSDTO.setDescricao(acordoNivelServicoContratoDTO.getDescricaoAcordo());
			}
			if (faturaApuracaoANSDTO.getValorApurado() != null) {
			    valorApurado = valorApurado + faturaApuracaoANSDTO.getValorApurado();
			}
			if (faturaApuracaoANSDTO.getPercentualGlosa() != null) {
			    percentualGlosa = percentualGlosa + faturaApuracaoANSDTO.getPercentualGlosa();
			}
			if (faturaApuracaoANSDTO.getValorGlosa() != null) {
			    valorGlosa = valorGlosa + faturaApuracaoANSDTO.getValorGlosa();
			}
		    }
		}
		JRDataSource listaFaturaApuracaoANS = new JRBeanCollectionDataSource(colecaoIdFaturaApuracao);
		os.setListaFaturaApuracaoANS(listaFaturaApuracaoANS);
	    }
	}

	Date dt = new Date();
	String strCompl = "" + dt.getTime();
	String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioFaturaContrato.jasper";
	String caminhoSubRelatorioJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS");
	String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
	String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

	String valorGlosa2 = UtilFormatacao.formatDouble(valorGlosa, 2);
	String percentualGlosa2 = UtilFormatacao.formatDouble(percentualGlosa, 2);

	Map<String, Object> parametros = new HashMap<String, Object>();
	parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);

	parametros.put("NOME_EMPRESA", nomeEmpresa);
	parametros.put("NOME_CLIENTE", clienteDTO.getNomeFantasia());
	parametros.put("APLICACAO", "CITSMart");
	parametros.put("CIDADE", "Brasília,");
	parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
	parametros.put("NOME_USUARIO", user.getNomeUsuario());
	parametros.put("contratoNumero", contratoDto.getNumero());
	parametros.put("ano", (contratoDto.getDataContrato() != null ? this.converterData(contratoDto.getDataContrato()).toString() : ""));
	parametros.put("nomeFantasia", fornecedorDto.getNomeFantasia());
	parametros.put("descricaoFatura", faturaDto.getDescricaoFatura());
	parametros.put("dataInicio", faturaDto.getDataInicial());
	parametros.put("dataFim", faturaDto.getDataFinal());
	parametros.put("observacao", faturaDto.getObservacao());
	parametros.put("totalQuantidadeDemanda", totalQuantidadeDemanda);
	parametros.put("totalUstPrevista", totalUstPrevista);
	parametros.put("totalUstRealizadas", totalUstRealizadas);
	parametros.put("totalUstGlosadas", totalUstGlosadas);
	parametros.put("totalUstTotal", totalUstTotal);
	parametros.put("totalValorAutorizado", totalValorAutorizado);
	parametros.put("totalUstPrevistoPerido", totalUstPrevistoPerido);
	parametros.put("percentualGlosa", percentualGlosa2);
	parametros.put("valorGlosa", valorGlosa2);
	parametros.put("nomeMoeda", nomeMoeda);
	parametros.put("aprovacaoGestor", faturaDto.getAprovacaoGestor());
	parametros.put("aprovacaoFiscal", faturaDto.getAprovacaoFiscal());
	parametros.put("SUBREPORT_DIR", caminhoSubRelatorioJasper);

	if (listIdOs == null) {
	    document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
	    return;
	}

	JRDataSource dataSource = new JRBeanCollectionDataSource(listIdOs);

	JasperPrint print = JasperFillManager.fillReport(caminhoJasper, parametros, dataSource);
	JasperExportManager.exportReportToPdfFile(print, diretorioReceita + "/RelatorioFaturaContrato" + strCompl + "_" + user.getIdUsuario() + ".pdf");

	document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
		+ "/RelatorioFaturaContrato" + strCompl + "_" + user.getIdUsuario() + ".pdf')");

    }

    /**
     * Metodo para fazer a impressão de Fatura Contratos
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     */
    public void imprimirRelatorioFaturaContratoXls(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	HttpSession session = request.getSession();
	InformacoesContratoDTO informacoesDto = (InformacoesContratoDTO) document.getBean();

	if (informacoesDto.getIdContrato() == null) {
	    document.alert("Informe o contrato!");
	    return;
	}
	UsuarioDTO user = WebUtil.getUsuario(request);
	if (user == null) {
	    document.alert("O usuário não está logado! Favor logar no sistema!");
	    return;
	}
	double valorApurado = 0;
	Double percentualGlosa = 0.0;
	Double valorGlosa = 0.0;
	double totalQuantidadeDemanda = 0;
	double totalUstPrevista = 0;
	double totalUstRealizadas = 0;
	double totalUstGlosadas = 0;
	double totalUstTotal = 0;
	double totalValorAutorizado = 0;
	double totalUstPrevistoPerido = 0;

	String nomeEmpresa = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.EMPRESA_Nome, "CITSMart");

	OSService osService = (OSService) ServiceLocator.getInstance().getService(OSService.class, null);
	AtividadesOSService atividadesOSService = (AtividadesOSService) ServiceLocator.getInstance().getService(AtividadesOSService.class, null);
	GlosaOSService glosaOSService = (GlosaOSService) ServiceLocator.getInstance().getService(GlosaOSService.class, null);
	ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
	FornecedorService fornecedorService = (FornecedorService) ServiceLocator.getInstance().getService(FornecedorService.class, null);
	ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
	FaturaService faturaService = (FaturaService) ServiceLocator.getInstance().getService(FaturaService.class, null);
	FaturaApuracaoANSService faturaApuracaoANSService = (FaturaApuracaoANSService) ServiceLocator.getInstance().getService(FaturaApuracaoANSService.class, null);
	ClienteService clienteService = (ClienteService) ServiceLocator.getInstance().getService(ClienteService.class, null);
	AcordoNivelServicoContratoService acordoNivelServicoContratoService = (AcordoNivelServicoContratoService) ServiceLocator.getInstance()
		.getService(AcordoNivelServicoContratoService.class, null);
	MoedaService moedaService = (MoedaService) ServiceLocator.getInstance().getService(MoedaService.class, null);

	/* OSDTO osDto = new OSDTO(); */
	Collection<OSDTO> listIdOs = osService.listOSAssociadasFatura(informacoesDto.getIdFatura());

	FaturaDTO faturaDto = new FaturaDTO();
	faturaDto.setIdFatura(informacoesDto.getIdFatura());
	faturaDto = (FaturaDTO) faturaService.restore(faturaDto);

	ContratoDTO contratoDto = new ContratoDTO();
	contratoDto.setIdContrato(informacoesDto.getIdContrato());
	contratoDto = (ContratoDTO) contratoService.restore(contratoDto);

	FornecedorDTO fornecedorDto = new FornecedorDTO();
	fornecedorDto.setIdFornecedor(contratoDto.getIdFornecedor());
	fornecedorDto = (FornecedorDTO) fornecedorService.restore(fornecedorDto);

	OSDTO osDto = new OSDTO();

	ClienteDTO clienteDTO = new ClienteDTO();
	clienteDTO.setIdCliente(contratoDto.getIdCliente());
	clienteDTO = (ClienteDTO) clienteService.restore(clienteDTO);
	if (clienteDTO.getNomeFantasia() == null) {
	    clienteDTO.setNomeFantasia("");
	}

	String nomeMoeda = "";
	MoedaDTO moedaDto = new MoedaDTO();
	if (contratoDto.getIdMoeda() != null) {
	    moedaDto.setIdMoeda(contratoDto.getIdMoeda());
	    moedaDto = (MoedaDTO) moedaService.restore(moedaDto);
	    if (moedaDto != null) {
		nomeMoeda = "" + moedaDto.getNomeMoeda() + "";
	    }
	}

	/*
	 * FaturaApuracaoANSDTO faturaApuracaoANSDto = new FaturaApuracaoANSDTO();
	 */
	Collection<FaturaApuracaoANSDTO> colecaoIdFaturaApuracao = faturaApuracaoANSService.findByIdFatura(informacoesDto.getIdFatura());

	if (listIdOs != null) {
	    for (OSDTO os : listIdOs) {
		Double retornoQtdExec = atividadesOSService.retornarQtdExecucao(os.getIdOS());
		if (retornoQtdExec != null) {
		    os.setUstRealizada((double) 0);
		    os.setRealizado(retornoQtdExec);
		    os.setUstRealizada(os.getUstRealizada() + os.getRealizado());
		}

		os.setServico(servicoService.retornarSiglaPorIdOs(os.getIdOS()));
		os.setQuantidadeDemanda(atividadesOSService.retornarCustoAtividadeOSByIdOs(os.getIdOS()));

		if (os.getAno() != null) {
		    osDto.setAno(os.getAno());
		} else {
		    osDto.setAno(0);
		}

		if (os.getQuantidadeDemanda() != null) {
		    totalQuantidadeDemanda = totalQuantidadeDemanda + os.getQuantidadeDemanda();
		    totalUstPrevista = totalUstPrevista + os.getQuantidadeDemanda();
		}

		if (totalUstPrevista != 0) {
		    // Removido, agora a moeda utiliza um flag de uso de cotação
		    // if(moedaDto != null && moedaDto.getNomeMoeda().equalsIgnoreCase(Enumerados.Moeda.UST.getDescricao())){
		    if ((moedaDto != null) && (moedaDto.getUsarCotacao() != null)) {
			totalUstPrevistoPerido = totalUstPrevista * faturaDto.getValorCotacaoMoeda();
		    } else {
			totalUstPrevistoPerido = totalUstPrevista;
		    }
		}

		if (os.getUstRealizada() != null) {
		    os.setTotalglosasAtividades((double) 0);
		    totalUstRealizadas = totalUstRealizadas + os.getUstRealizada();
		    os.setGlosaAtividade(atividadesOSService.retornarGlosaAtividadeOSByIdOs(os.getIdOS()));
		    os.setCustoGlosa(glosaOSService.retornarCustoGlosaOSByIdOs(os.getIdOS()));

		    if ((os.getGlosaAtividade() != null) && (os.getCustoGlosa() != null)) {
			os.setTotalglosasAtividades(os.getCustoGlosa());
		    }

		    totalUstGlosadas = totalUstGlosadas + os.getTotalglosasAtividades();

		    os.setUstTotal(os.getUstRealizada() - os.getTotalglosasAtividades());

		    if (os.getUstTotal() != 0) {
			totalUstTotal = totalUstTotal + os.getUstTotal();
		    }

		    os.setCotacaoMoeda(faturaDto.getValorCotacaoMoeda());

		    // Removido, agora a moeda utiliza um flag de uso de cotação
		    // if(moedaDto != null && moedaDto.getNomeMoeda().equalsIgnoreCase(Enumerados.Moeda.UST.getDescricao())){
		    if ((moedaDto != null) && (moedaDto.getUsarCotacao() != null)) {
			os.setValorAutorizado(os.getUstTotal() * os.getCotacaoMoeda());
		    } else {
			os.setValorAutorizado(os.getUstTotal());
		    }

		    if (os.getValorAutorizado() != 0) {
			totalValorAutorizado = totalValorAutorizado + os.getValorAutorizado();
		    }

		}

		if (colecaoIdFaturaApuracao != null) {
		    valorGlosa = 0.0;
		    for (FaturaApuracaoANSDTO faturaApuracaoANSDTO : colecaoIdFaturaApuracao) {
			AcordoNivelServicoContratoDTO acordoNivelServicoContratoDTO = new AcordoNivelServicoContratoDTO();
			acordoNivelServicoContratoDTO.setIdAcordoNivelServicoContrato(faturaApuracaoANSDTO.getIdAcordoNivelServicoContrato());
			acordoNivelServicoContratoDTO = (AcordoNivelServicoContratoDTO) acordoNivelServicoContratoService.restore(acordoNivelServicoContratoDTO);
			if (acordoNivelServicoContratoDTO.getDescricaoAcordo() != null) {
			    faturaApuracaoANSDTO.setDescricao(acordoNivelServicoContratoDTO.getDescricaoAcordo());
			}
			if (faturaApuracaoANSDTO.getValorApurado() != null) {
			    valorApurado = valorApurado + faturaApuracaoANSDTO.getValorApurado();
			}
			if (faturaApuracaoANSDTO.getPercentualGlosa() != null) {
			    percentualGlosa = percentualGlosa + faturaApuracaoANSDTO.getPercentualGlosa();
			}
			if (faturaApuracaoANSDTO.getValorGlosa() != null) {
			    valorGlosa = valorGlosa + faturaApuracaoANSDTO.getValorGlosa();
			}
		    }
		}
		JRDataSource listaFaturaApuracaoANS = new JRBeanCollectionDataSource(colecaoIdFaturaApuracao);
		os.setListaFaturaApuracaoANS(listaFaturaApuracaoANS);
	    }
	}

	Date dt = new Date();
	String strCompl = "" + dt.getTime();
	Constantes.getValue("CAMINHO_RELATORIOS");
	String caminhoSubRelatorioJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS");
	String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
	String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

	String valorGlosa2 = UtilFormatacao.formatDouble(valorGlosa, 2);
	String percentualGlosa2 = UtilFormatacao.formatDouble(percentualGlosa, 2);

	Map<String, Object> parametros = new HashMap<String, Object>();
	parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);

	parametros.put("NOME_EMPRESA", nomeEmpresa);
	parametros.put("NOME_CLIENTE", clienteDTO.getNomeFantasia());
	parametros.put("APLICACAO", "CITSMart");
	parametros.put("CIDADE", "Brasília,");
	parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
	parametros.put("NOME_USUARIO", user.getNomeUsuario());
	parametros.put("contratoNumero", contratoDto.getNumero());
	parametros.put("ano", (contratoDto.getDataContrato() != null ? this.converterData(contratoDto.getDataContrato()).toString() : ""));
	parametros.put("nomeFantasia", fornecedorDto.getNomeFantasia());
	parametros.put("descricaoFatura", faturaDto.getDescricaoFatura());
	parametros.put("dataInicio", faturaDto.getDataInicial());
	parametros.put("dataFim", faturaDto.getDataFinal());
	parametros.put("observacao", faturaDto.getObservacao());
	parametros.put("totalQuantidadeDemanda", totalQuantidadeDemanda);
	parametros.put("totalUstPrevista", totalUstPrevista);
	parametros.put("totalUstRealizadas", totalUstRealizadas);
	parametros.put("totalUstGlosadas", totalUstGlosadas);
	parametros.put("totalUstTotal", totalUstTotal);
	parametros.put("totalValorAutorizado", totalValorAutorizado);
	parametros.put("totalUstPrevistoPerido", totalUstPrevistoPerido);
	parametros.put("percentualGlosa", percentualGlosa2);
	parametros.put("valorGlosa", valorGlosa2);
	parametros.put("nomeMoeda", nomeMoeda);
	parametros.put("aprovacaoGestor", faturaDto.getAprovacaoGestor());
	parametros.put("aprovacaoFiscal", faturaDto.getAprovacaoFiscal());
	parametros.put("SUBREPORT_DIR", caminhoSubRelatorioJasper);

	if (listIdOs == null) {
	    document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
	    return;
	}

	JRDataSource dataSource = new JRBeanCollectionDataSource(listIdOs);

	JasperDesign desenho = JRXmlLoader.load(CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioFaturaContratoXls.jrxml");

	JasperReport relatorio = JasperCompileManager.compileReport(desenho);

	JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros, dataSource);

	JRXlsExporter exporter = new JRXlsExporter();
	exporter.setParameter(JRExporterParameter.JASPER_PRINT, impressao);
	exporter.setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
	exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, diretorioReceita + "/RelatorioFaturaContratoXls" + strCompl + "_" + user.getIdUsuario() + ".xls");

	exporter.exportReport();

	document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
		+ "/RelatorioFaturaContratoXls" + strCompl + "_" + user.getIdUsuario() + ".xls')");

    }

    public void excluiRAOrdemServicoContrato(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	InformacoesContratoDTO informacoesDto = (InformacoesContratoDTO) document.getBean();
	OSService osService = (OSService) ServiceLocator.getInstance().getService(OSService.class, null);
	OSDTO osDto = new OSDTO();
	osDto.setIdOS(informacoesDto.getIdOS());
	osService.delete(osDto);
	document.executeScript("listarOS()");
    }

    public ServicoContratoDTO getServicoContratoDTO() {
	return this.servicoContratoDTO;
    }

    public void setServicoContratoDTO(ServicoContratoDTO servicoContratoDTO) {
	this.servicoContratoDTO = servicoContratoDTO;
    }

    /**
     * Método para converter de Date para Integer pegando o ano.
     *
     * @param data
     * @return cledson.junior
     */
    public Integer converterData(Date data) {
	Calendar calendario = Calendar.getInstance();
	calendario.setTime(data);
	return calendario.get(Calendar.YEAR);
    }

    /**
     * Método que desabilita o vinculo ativo do Acordo Serviço Contrato
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     */
    public void desabilitarVinculoAtivo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	InformacoesContratoDTO informacoesDto = (InformacoesContratoDTO) document.getBean();
	AcordoServicoContratoDTO acordoServicoContratoDTO = new AcordoServicoContratoDTO();
	AcordoServicoContratoService acordoServicoContratoService = (AcordoServicoContratoService) ServiceLocator.getInstance().getService(AcordoServicoContratoService.class, null);

	acordoServicoContratoDTO.setIdAcordoServicoContrato(informacoesDto.getIdAcordoServicoContrato());
	acordoServicoContratoDTO.setHabilitado("N");
	acordoServicoContratoService.updateNotNull(acordoServicoContratoDTO);

	this.listarSLAsContrato(document, request, response);
    }

    /**
     * Método que habilita o vinculo ativo do Acordo Serviço Contrato
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     */
    public void habilitarVinculoAtivo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	InformacoesContratoDTO informacoesDto = (InformacoesContratoDTO) document.getBean();
	AcordoServicoContratoDTO acordoServicoContratoDTO = new AcordoServicoContratoDTO();
	AcordoServicoContratoService acordoServicoContratoService = (AcordoServicoContratoService) ServiceLocator.getInstance().getService(AcordoServicoContratoService.class, null);

	acordoServicoContratoDTO.setIdAcordoServicoContrato(informacoesDto.getIdAcordoServicoContrato());
	acordoServicoContratoDTO = (AcordoServicoContratoDTO) acordoServicoContratoService.restore(acordoServicoContratoDTO);
	if (acordoServicoContratoDTO != null) {
	    acordoServicoContratoDTO.setHabilitado("S");
	    acordoServicoContratoService.updateNotNull(acordoServicoContratoDTO);
	}

	this.listarSLAsContrato(document, request, response);
    }

    public void setarSessao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	InformacoesContratoDTO informacoesDto = (InformacoesContratoDTO) document.getBean();

	ContratoDTO contratoDTO = new ContratoDTO();
	contratoDTO.setIdContrato(informacoesDto.getIdContrato());
	ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
	contratoDTO = (ContratoDTO) contratoService.restore(contratoDTO);
	request.getSession(true).setAttribute("idAdministracaoContrato", contratoDTO.getIdContrato());
	request.getSession(true).setAttribute("numeroContratoAdministracaoContrato", contratoDTO.getNumero());
    }

    public void setarValoresViaPortal(DocumentHTML document, HttpServletRequest request, HttpServletResponse response,UsuarioDTO user) throws Exception{

    }

    public String retornaValorFormatado(double valor){
	NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
	DecimalFormat decimal = (DecimalFormat)nf;
	decimal.applyPattern("#,##0.00");

	return decimal.format(valor);
    }

    public void abreRelatorioPDF(JRDataSource dataSource, Map<String, Object> parametros, String diretorioTemp, String caminhoJasper, String jasperArqRel, String diretorioRelativo, String arquivoRelatorio, DocumentHTML document, HttpServletRequest request, HttpServletResponse response) {
	try {
	    JRSwapFile arquivoSwap = new JRSwapFile(diretorioTemp, 4096, 25);
	    JRAbstractLRUVirtualizer virtualizer = new JRSwapFileVirtualizer(25, arquivoSwap, true);
	    parametros.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
	    JasperPrint print = JasperFillManager.fillReport(caminhoJasper + jasperArqRel + ".jasper", parametros, dataSource);
	    // JasperViewer.viewReport(print,false);

	    JasperExportManager.exportReportToPdfFile(print, diretorioTemp + arquivoRelatorio + ".pdf");

	    document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativo + arquivoRelatorio + ".pdf')");
	} catch (OutOfMemoryError e) {
	    document.alert(UtilI18N.internacionaliza(request, "citcorpore.erro.erroServidor"));
	} catch (JRException e) {
	    e.printStackTrace();
	}
    }

    public void abreRelatorioXLS(JRDataSource dataSource, Map<String, Object> parametros, String diretorioTemp, String caminhoJasper, String jasperArqRel, String diretorioRelativo, String arquivoRelatorio, DocumentHTML document, HttpServletRequest request, HttpServletResponse response) {
	try {
	    JasperDesign desenho = JRXmlLoader.load(caminhoJasper + jasperArqRel + "Xls.jrxml");
	    JasperReport relatorio = JasperCompileManager.compileReport(desenho);
	    JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros, dataSource);
	    JRXlsExporter exporter = new JRXlsExporter();
	    exporter.setParameter(JRExporterParameter.JASPER_PRINT, impressao);
	    exporter.setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
	    exporter.setParameter(JRXlsAbstractExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
	    exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, diretorioTemp + arquivoRelatorio + ".xls");
	    exporter.exportReport();
	    document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativo + arquivoRelatorio + ".xls')");
	} catch (OutOfMemoryError e) {
	    document.alert(UtilI18N.internacionaliza(request, "citcorpore.erro.erroServidor"));
	} catch (JRException e) {
	    e.printStackTrace();
	}
    }

}