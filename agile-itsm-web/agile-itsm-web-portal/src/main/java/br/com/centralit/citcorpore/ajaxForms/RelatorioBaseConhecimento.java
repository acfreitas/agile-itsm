package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.PastaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.BaseConhecimentoService;
import br.com.centralit.citcorpore.negocio.ContadorAcessoService;
import br.com.centralit.citcorpore.negocio.PastaService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.LogoRel;
import br.com.centralit.citcorpore.util.UtilRelatorio;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
public class RelatorioBaseConhecimento extends AjaxFormAction {

	private UsuarioDTO usuario;
	private  String localeSession = null;

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		usuario = WebUtil.getUsuario(request);

		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		this.preencherComboPasta(document, request, response);
		this.preencherComboAprovadas(document, request, response);

	}
	
	public List<BaseConhecimentoDTO> listarBaseConhecimento(HttpServletRequest request,Collection<BaseConhecimentoDTO> listaBaseConhecimento , BaseConhecimentoDTO baseConhecimentoDto,BaseConhecimentoService baseConhecimentoService,ContadorAcessoService contadorAcessoService) throws Exception{
		List<BaseConhecimentoDTO> listaGeral = new ArrayList<BaseConhecimentoDTO>();
		List<BaseConhecimentoDTO> listaBaseConhecimentoRetorno = new ArrayList<BaseConhecimentoDTO>();
		
		for (BaseConhecimentoDTO base : listaBaseConhecimento) {
			if (baseConhecimentoDto.getTermoPesquisaNota() != null && !baseConhecimentoDto.getTermoPesquisaNota().equalsIgnoreCase("S") && !baseConhecimentoDto.getTermoPesquisaNota().equalsIgnoreCase("")) {
				base.setMedia(this.calcularMdia(base, baseConhecimentoService));
				base.setContadorCliques(contadorAcessoService.quantidadesDeAcessoPorBaseConhecimnto(base));
				if (base.getMedia().equalsIgnoreCase(baseConhecimentoDto.getTermoPesquisaNota())) {
					listaBaseConhecimentoRetorno.add(base);
					listaGeral = listaBaseConhecimentoRetorno;
				}
			} else {
				base.setContadorCliques(contadorAcessoService.quantidadesDeAcessoPorBaseConhecimnto(base));
				base.setMedia(this.calcularMdia(base, baseConhecimentoService));
				listaBaseConhecimentoRetorno.add(base);
				listaGeral = listaBaseConhecimentoRetorno;
			}
			base.setStatus(base.getStatus()!=null&&base.getStatus().equals("S") ? 
					UtilI18N.internacionaliza(request, "pesquisaBaseConhecimento.publicado") : UtilI18N.internacionaliza(request, "pesquisaBaseConhecimento.naoPublicado"));
		}
		
		return listaGeral;
	}

	public void imprimirRelatorioBaseConhecimento(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		BaseConhecimentoDTO baseConhecimentoDto = (BaseConhecimentoDTO) document.getBean();
		HttpSession session = ((HttpServletRequest) request).getSession();
		BaseConhecimentoDTO baseConhecimento = new BaseConhecimentoDTO();
		BaseConhecimentoService baseConhecimentoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, null);
		ContadorAcessoService contadorAcessoService = (ContadorAcessoService) ServiceLocator.getInstance().getService(ContadorAcessoService.class, null);
		usuario = WebUtil.getUsuario(request);
		PastaService pastaService = (PastaService) ServiceLocator.getInstance().getService(PastaService.class, null);
		PastaDTO pasta = new PastaDTO();
		CompararBaseConhecimento comparaAcesso = new CompararBaseConhecimento();
		CompararBaseConhecimentoMedia comparaMedia = new CompararBaseConhecimentoMedia();
		CompararBaseConhecimentoPorVersao comparaPorVersao = new CompararBaseConhecimentoPorVersao();
		List<BaseConhecimentoDTO> mapaBaseConhecimento = new ArrayList<BaseConhecimentoDTO>();
		List<BaseConhecimentoDTO> listaGeral = new ArrayList<BaseConhecimentoDTO>();
		List<BaseConhecimentoDTO> listaBaseConhecimentoMedia = new ArrayList<BaseConhecimentoDTO>();
		List<BaseConhecimentoDTO> listaBaseConhecimentoUltimasVersoes = new ArrayList<BaseConhecimentoDTO>();
		Collection<BaseConhecimentoDTO> listaBaseConhecimento = baseConhecimentoService.listaBaseConhecimento(baseConhecimentoDto);
		Collection<BaseConhecimentoDTO> listaUltimasVersoes = baseConhecimentoService.listaBaseConhecimentoUltimasVersoes(baseConhecimentoDto);

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);
		
		parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "relatorioBaseConhecimento.relatorioBaseConhecimento"));
		parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
		parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
		parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
		parametros.put("ocularCampoConteudo", baseConhecimentoDto.getOcultarConteudo());
		parametros.put("dataInicio", baseConhecimentoDto.getDataInicio());
		parametros.put("dataFim", baseConhecimentoDto.getDataFim());
		parametros.put("dataInicioPublicada", baseConhecimentoDto.getDataInicioPublicacao());
		parametros.put("dataFimPublicada", baseConhecimentoDto.getDataFimPublicacao());
		parametros.put("dataInicioExpiracao", baseConhecimentoDto.getDataInicioExpiracao());
		parametros.put("dataFimExpiracao", baseConhecimentoDto.getDataFimExpiracao());
		parametros.put("dataInicioAcesso", baseConhecimentoDto.getDataInicioAcesso());
		parametros.put("dataFimAcesso", baseConhecimentoDto.getDataFimAcesso());
		parametros.put("Logo", LogoRel.getFile());
		if (baseConhecimentoDto.getIdPasta() != null) {
			pasta.setId(baseConhecimentoDto.getIdPasta());
			pasta = (PastaDTO) pastaService.restore(pasta);
			parametros.put("nomePasta", pasta.getNome());
		} else {
			parametros.put("nomePasta", null);
		}
		if (baseConhecimentoDto.getIdBaseConhecimento() != null) {
			baseConhecimento = (BaseConhecimentoDTO) baseConhecimentoService.restore(baseConhecimentoDto);
			parametros.put("baseConhecimentoTitulo", baseConhecimento.getTitulo());
		} else {
			parametros.put("baseConhecimentoTitulo", null);
		}
		if (baseConhecimentoDto.getTermoPesquisaNota() != null) {
			if (baseConhecimentoDto.getTermoPesquisaNota().equalsIgnoreCase("S")) {
				parametros.put("nota", UtilI18N.internacionaliza(request, "relatorioBaseConhecimento.semAvaliacao"));
			} else {
				if (baseConhecimentoDto.getTermoPesquisaNota().equalsIgnoreCase("")) {
					parametros.put("nota",UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
				} else {
					parametros.put("nota", baseConhecimentoDto.getTermoPesquisaNota());
				}
			}
		} else {
			parametros.put("nota", null);
		}
		if (baseConhecimentoDto.getStatus() != null) {
			if (baseConhecimentoDto.getStatus().equalsIgnoreCase("")) {
				parametros.put("situacao",UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
			} else {
				baseConhecimentoDto.setStatus(baseConhecimentoDto.getStatus()!=null&&baseConhecimentoDto.getStatus().equals("S") ? 
						UtilI18N.internacionaliza(request, "pesquisaBaseConhecimento.publicado") : UtilI18N.internacionaliza(request, "pesquisaBaseConhecimento.naoPublicado"));
				parametros.put("situacao", baseConhecimentoDto.getStatus());
			}
		} else {
			parametros.put("situacao", null);
		}

		if (baseConhecimentoDto.getUltimoAcesso() != null && baseConhecimentoDto.getUltimoAcesso().equals("S")) {
			parametros.put("ultimoacesso",UtilI18N.internacionaliza(request, "citcorpore.comum.sim"));
		} else {
			parametros.put("ultimoacesso",UtilI18N.internacionaliza(request, "citcorpore.comum.nao"));
		}
		
		JRDataSource dataSource = null;
		if (listaBaseConhecimento == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		if (baseConhecimentoDto.getAcessado() == null) {
			listaGeral.addAll(this.listarBaseConhecimento(request,listaBaseConhecimento, baseConhecimentoDto, baseConhecimentoService, contadorAcessoService));
			/*for (BaseConhecimentoDTO base : listaBaseConhecimento) {
				if (baseConhecimentoDto.getTermoPesquisaNota() != null && !baseConhecimentoDto.getTermoPesquisaNota().equalsIgnoreCase("S")
						&& !baseConhecimentoDto.getTermoPesquisaNota().equalsIgnoreCase("")) {
					base.setMedia(this.calcularMdia(base, baseConhecimentoService));
					base.setContadorCliques(contadorAcessoService.quantidadesDeAcessoPorBaseConhecimnto(base));
					if (base.getMedia().equalsIgnoreCase(baseConhecimentoDto.getTermoPesquisaNota())) {
						listaBaseConhecimentoMedia.add(base);
						listaGeral = listaBaseConhecimentoMedia;
					}
				} else {
					base.setContadorCliques(contadorAcessoService.quantidadesDeAcessoPorBaseConhecimnto(base));
					base.setMedia(this.calcularMdia(base, baseConhecimentoService));
					listaGeral = (List<BaseConhecimentoDTO>) listaBaseConhecimento;

				}

			}*/
		}

		else if (baseConhecimentoDto.getAcessado() != null && baseConhecimentoDto.getAcessado().equalsIgnoreCase("A")) {
			parametros.put("ordenacao", UtilI18N.internacionaliza(request, "relatorioBaseConhecimento.ordenarPorQuantidadeAcessos"));
			
			listaGeral.addAll(this.listarBaseConhecimento(request,listaBaseConhecimento, baseConhecimentoDto, baseConhecimentoService, contadorAcessoService));
			Collections.sort(listaGeral, comparaAcesso);
		
			/*for (BaseConhecimentoDTO base : listaBaseConhecimento) {
				if (baseConhecimentoDto.getTermoPesquisaNota() != null && !baseConhecimentoDto.getTermoPesquisaNota().equalsIgnoreCase("S") && !baseConhecimentoDto.getTermoPesquisaNota().equalsIgnoreCase("")) {
					base.setMedia(this.calcularMdia(base, baseConhecimentoService));
					base.setContadorCliques(contadorAcessoService.quantidadesDeAcessoPorBaseConhecimnto(base));
					if (base.getMedia().equalsIgnoreCase(baseConhecimentoDto.getTermoPesquisaNota())) {
						listaBaseConhecimentoMedia.add(base);
						listaGeral = listaBaseConhecimentoMedia;
						Collections.sort(listaGeral, comparaAcesso);
					}
				} else {
					base.setContadorCliques(contadorAcessoService.quantidadesDeAcessoPorBaseConhecimnto(base));
					base.setMedia(this.calcularMdia(base, baseConhecimentoService));
					mapaBaseConhecimento.add(base);
					Collections.sort(mapaBaseConhecimento, comparaAcesso);
					listaGeral = mapaBaseConhecimento;
				}
			}*/
		}

		else if (baseConhecimentoDto.getAcessado() != null && baseConhecimentoDto.getAcessado().equalsIgnoreCase("M")) {
			parametros.put("ordenacao", UtilI18N.internacionaliza(request, "relatorioBaseConhecimento.ordenarPorMediaAvaliacao"));
			listaGeral.addAll(this.listarBaseConhecimento(request,listaBaseConhecimento, baseConhecimentoDto, baseConhecimentoService, contadorAcessoService));
			Collections.sort(listaGeral, comparaMedia);
			/*for (BaseConhecimentoDTO base : listaBaseConhecimento) {
				if (baseConhecimentoDto.getTermoPesquisaNota() != null && !baseConhecimentoDto.getTermoPesquisaNota().equalsIgnoreCase("S") && !baseConhecimentoDto.getTermoPesquisaNota().equalsIgnoreCase("")) {
					base.setMedia(this.calcularMdia(base, baseConhecimentoService));
					base.setContadorCliques(contadorAcessoService.quantidadesDeAcessoPorBaseConhecimnto(base));
					if (base.getMedia().equalsIgnoreCase(baseConhecimentoDto.getTermoPesquisaNota())) {
						listaBaseConhecimentoMedia.add(base);
						listaGeral = listaBaseConhecimentoMedia;
						Collections.sort(listaGeral, comparaMedia);

					}
				} else {
					base.setContadorCliques(contadorAcessoService.quantidadesDeAcessoPorBaseConhecimnto(base));
					base.setMedia(this.calcularMdia(base, baseConhecimentoService));
					mapaBaseConhecimento.add(base);
					listaGeral = mapaBaseConhecimento;
					Collections.sort(listaGeral, comparaMedia);

				}
			}*/
		} else {
			parametros.put("ordenacao", UtilI18N.internacionaliza(request, "relatorioBaseConhecimento.ordenarPorVersao"));
			listaGeral.addAll(this.listarBaseConhecimento(request,listaBaseConhecimento, baseConhecimentoDto, baseConhecimentoService, contadorAcessoService));
			Collections.sort(listaGeral, comparaPorVersao);
			/*for (BaseConhecimentoDTO base : listaBaseConhecimento) {
				if (baseConhecimentoDto.getTermoPesquisaNota() != null && !baseConhecimentoDto.getTermoPesquisaNota().equalsIgnoreCase("S") && !baseConhecimentoDto.getTermoPesquisaNota().equalsIgnoreCase("")) {
					base.setMedia(this.calcularMdia(base, baseConhecimentoService));
					base.setContadorCliques(contadorAcessoService.quantidadesDeAcessoPorBaseConhecimnto(base));
					if (base.getMedia().equalsIgnoreCase(baseConhecimentoDto.getTermoPesquisaNota())) {
						listaBaseConhecimentoMedia.add(base);
						listaGeral = listaBaseConhecimentoMedia;
						Collections.sort(listaGeral, comparaPorVersao);

					}
				} else {
					base.setContadorCliques(contadorAcessoService.quantidadesDeAcessoPorBaseConhecimnto(base));
					base.setMedia(this.calcularMdia(base, baseConhecimentoService));
					mapaBaseConhecimento.add(base);
					listaGeral = mapaBaseConhecimento;
					Collections.sort(listaGeral, comparaPorVersao);

				}
			}*/
		}
		if (baseConhecimentoDto.getUltimaVersao() != null && baseConhecimentoDto.getUltimaVersao().equals("S")) {
			parametros.put("ultimasVersoes", UtilI18N.internacionaliza(request, "relatorioBaseConhecimento.ultimasVersoes"));
			if (listaUltimasVersoes != null) {
				listaGeral = new ArrayList<BaseConhecimentoDTO>();
				listaGeral.addAll(this.listarBaseConhecimento(request,listaUltimasVersoes, baseConhecimentoDto, baseConhecimentoService, contadorAcessoService));
				if (baseConhecimentoDto.getAcessado() != null && baseConhecimentoDto.getAcessado().equalsIgnoreCase("A")) {
					Collections.sort(listaGeral, comparaAcesso);
				} else if (baseConhecimentoDto.getAcessado() != null && baseConhecimentoDto.getAcessado().equalsIgnoreCase("M")) {
					Collections.sort(listaGeral, comparaMedia);
				} else {
					Collections.sort(listaGeral, comparaPorVersao);
				}
				/*for (BaseConhecimentoDTO base : listaUltimasVersoes) {
					if (baseConhecimentoDto.getTermoPesquisaNota() != null && !baseConhecimentoDto.getTermoPesquisaNota().equalsIgnoreCase("S") && !baseConhecimentoDto.getTermoPesquisaNota().equalsIgnoreCase("")) {
						base.setMedia(this.calcularMdia(base, baseConhecimentoService));
						base.setContadorCliques(contadorAcessoService.quantidadesDeAcessoPorBaseConhecimnto(base));
						if (base.getMedia().equalsIgnoreCase(baseConhecimentoDto.getTermoPesquisaNota())) {
							listaBaseConhecimentoUltimasVersoes.add(base);
							listaGeral = listaBaseConhecimentoUltimasVersoes;
							if (baseConhecimentoDto.getAcessado() != null && baseConhecimentoDto.getAcessado().equalsIgnoreCase("A")) {
								Collections.sort(listaGeral, comparaAcesso);
							} else if (baseConhecimentoDto.getAcessado() != null && baseConhecimentoDto.getAcessado().equalsIgnoreCase("M")) {
								Collections.sort(listaGeral, comparaMedia);
							} else {
								Collections.sort(listaGeral, comparaPorVersao);
							}

						}
					} else {
						base.setContadorCliques(contadorAcessoService.quantidadesDeAcessoPorBaseConhecimnto(base));
						base.setMedia(this.calcularMdia(base, baseConhecimentoService));
						listaBaseConhecimentoUltimasVersoes.add(base);
						listaGeral = listaBaseConhecimentoUltimasVersoes;
						if (baseConhecimentoDto.getAcessado() != null && baseConhecimentoDto.getAcessado().equalsIgnoreCase("A")) {
							Collections.sort(listaGeral, comparaAcesso);
						} else if (baseConhecimentoDto.getAcessado() != null && baseConhecimentoDto.getAcessado().equalsIgnoreCase("M")) {
							Collections.sort(listaGeral, comparaMedia);
						} else {
							Collections.sort(listaGeral, comparaPorVersao);
						}
					}
				}*/
			}

		}
		
		

		if (listaGeral.size() == 0) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		} /*else {
			for (BaseConhecimentoDTO baseAux : listaGeral) {
				if (baseAux.getMedia().equalsIgnoreCase("-1")) {
					baseAux.setMedia(UtilI18N.internacionaliza(request, "relatorioBaseConhecimento.semAvaliacao"));
				}
			}
			parametros.put("totalBaseConhecimento", listaGeral.size());
		}*/
		parametros.put("totalBaseConhecimento", listaGeral.size());
		try
		{
			dataSource = new JRBeanCollectionDataSource(listaGeral);
		
			Date dt = new Date();
			String strCompl = "" + dt.getTime();
			String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioBaseConhecimento.jasper";
			String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
			String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";
			
			JRSwapFile arquivoSwap = new JRSwapFile(diretorioReceita, 4096, 25);
			 
			// Instancia o virtualizador
			JRAbstractLRUVirtualizer virtualizer = new JRSwapFileVirtualizer(25, arquivoSwap, true);
			 
			//Seta o parametro REPORT_VIRTUALIZER com a instância da virtualização
			parametros.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
			 
			//Preenche o relatório e exibe numa GUI
			JasperPrint print = JasperFillManager.fillReport(caminhoJasper, parametros, dataSource);
			//JasperViewer.viewReport(print,false);
			
			JasperExportManager.exportReportToPdfFile(print, diretorioReceita + "/RelatorioBaseConhecimento" + strCompl + "_" + usuario.getIdUsuario() + ".pdf");
	
			document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS + "/RelatorioBaseConhecimento"
					+ strCompl + "_" + usuario.getIdUsuario() + ".pdf')");
		
		} catch(OutOfMemoryError e) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.erro.erroServidor"));
		}
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();

	}

	@Override
	public Class getBeanClass() {
		return BaseConhecimentoDTO.class;
	}

	public void preencherComboPasta(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		PastaService pastaService = (PastaService) ServiceLocator.getInstance().getService(PastaService.class, null);
		HTMLSelect comboPasta = (HTMLSelect) document.getSelectById("idPasta");

		ArrayList<PastaDTO> listaPastas = (ArrayList) pastaService.list();

		inicializaCombo(comboPasta, request);
		for (PastaDTO pasta : listaPastas)
			if (pasta.getDataFim() == null)
				comboPasta.addOption(pasta.getId().toString(), pasta.getNome());
	}

	private void inicializaCombo(HTMLSelect componenteCombo, HttpServletRequest request) {
		componenteCombo.removeAllOptions();
		componenteCombo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
	}

	public void preencherComboBaseConhecimentoPorPasta(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BaseConhecimentoDTO baseConhecimentoDto = (BaseConhecimentoDTO) document.getBean();
		BaseConhecimentoService baseConhecimentoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, null);

		HTMLSelect comboBaseConhecimento = (HTMLSelect) document.getSelectById("idBaseConhecimento");

		PastaDTO pastaDto = new PastaDTO();

		pastaDto.setId(baseConhecimentoDto.getIdPasta());

		ArrayList<BaseConhecimentoDTO> listaBaseConhecimentoPorPasta = (ArrayList) baseConhecimentoService.listarBaseConhecimentoByPastaRelatorio(pastaDto);

		inicializaCombo(comboBaseConhecimento, request);

		if (listaBaseConhecimentoPorPasta != null) {
			for (BaseConhecimentoDTO base : listaBaseConhecimentoPorPasta) {
				comboBaseConhecimento.addOption(base.getIdBaseConhecimento().toString(), base.getTitulo());
			}

		}

	}

	public void preencherComboAprovadas(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HTMLSelect comboAprovados = (HTMLSelect) document.getSelectById("status");
		inicializaCombo(comboAprovados, request);
		comboAprovados.addOption("S",UtilI18N.internacionaliza(request, "pesquisaBaseConhecimento.publicado"));
		comboAprovados.addOption("N",UtilI18N.internacionaliza(request, "pesquisaBaseConhecimento.naoPublicado"));

	}

	public String calcularMdia(BaseConhecimentoDTO baseConhecimento, BaseConhecimentoService baseConhecimentoService) throws Exception {
		Double media = baseConhecimentoService.calcularNota(baseConhecimento.getIdBaseConhecimento());
		if (media != null) {
			return media.toString();
		} else {
			return "-1";
		}
	}

	public void imprimirRelatorioBaseConhecimentoXls(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BaseConhecimentoDTO baseConhecimentoDto = (BaseConhecimentoDTO) document.getBean();
		HttpSession session = ((HttpServletRequest) request).getSession();
		BaseConhecimentoDTO baseConhecimento = new BaseConhecimentoDTO();
		BaseConhecimentoService baseConhecimentoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, null);
		ContadorAcessoService contadorAcessoService = (ContadorAcessoService) ServiceLocator.getInstance().getService(ContadorAcessoService.class, null);
		usuario = WebUtil.getUsuario(request);
		PastaService pastaService = (PastaService) ServiceLocator.getInstance().getService(PastaService.class, null);
		PastaDTO pasta = new PastaDTO();
		CompararBaseConhecimento comparaAcesso = new CompararBaseConhecimento();
		CompararBaseConhecimentoMedia comparaMedia = new CompararBaseConhecimentoMedia();
		CompararBaseConhecimentoPorVersao comparaPorVersao = new CompararBaseConhecimentoPorVersao();
		List<BaseConhecimentoDTO> mapaBaseConhecimento = new ArrayList<BaseConhecimentoDTO>();
		List<BaseConhecimentoDTO> listaGeral = new ArrayList<BaseConhecimentoDTO>();
		List<BaseConhecimentoDTO> listaBaseConhecimentoMedia = new ArrayList<BaseConhecimentoDTO>();
		List<BaseConhecimentoDTO> listaBaseConhecimentoUltimasVersoes = new ArrayList<BaseConhecimentoDTO>();
		Collection<BaseConhecimentoDTO> listaBaseConhecimento = baseConhecimentoService.listaBaseConhecimento(baseConhecimentoDto);
		Collection<BaseConhecimentoDTO> listaUltimasVersoes = baseConhecimentoService.listaBaseConhecimentoUltimasVersoes(baseConhecimentoDto);

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);
		
		parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "relatorioBaseConhecimento.relatorioBaseConhecimento"));
		parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
		parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
		parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
		parametros.put("ocularCampoConteudo", baseConhecimentoDto.getOcultarConteudo());
		parametros.put("dataInicio", baseConhecimentoDto.getDataInicio());
		parametros.put("dataFim", baseConhecimentoDto.getDataFim());
		parametros.put("dataInicioPublicada", baseConhecimentoDto.getDataInicioPublicacao());
		parametros.put("dataFimPublicada", baseConhecimentoDto.getDataFimPublicacao());
		parametros.put("dataInicioExpiracao", baseConhecimentoDto.getDataInicioExpiracao());
		parametros.put("dataFimExpiracao", baseConhecimentoDto.getDataFimExpiracao());
		parametros.put("dataInicioAcesso", baseConhecimentoDto.getDataInicioAcesso());
		parametros.put("dataFimAcesso", baseConhecimentoDto.getDataFimAcesso());
		parametros.put("Logo", LogoRel.getFile());
		if (baseConhecimentoDto.getIdPasta() != null) {
			pasta.setId(baseConhecimentoDto.getIdPasta());
			pasta = (PastaDTO) pastaService.restore(pasta);
			parametros.put("nomePasta", pasta.getNome());
		} else {
			parametros.put("nomePasta", null);
		}

		if (baseConhecimentoDto.getIdBaseConhecimento() != null) {
			baseConhecimento = (BaseConhecimentoDTO) baseConhecimentoService.restore(baseConhecimentoDto);
			parametros.put("baseConhecimentoTitulo", baseConhecimento.getTitulo());
		} else {
			parametros.put("baseConhecimentoTitulo", null);
		}

		if (baseConhecimentoDto.getTermoPesquisaNota() != null) {
			if (baseConhecimentoDto.getTermoPesquisaNota().equalsIgnoreCase("S")) {
				parametros.put("nota", "Sem Comentário");
			} else {
				if (baseConhecimentoDto.getTermoPesquisaNota().equalsIgnoreCase("")) {
					parametros.put("nota", "Todos");
				} else {
					parametros.put("nota", baseConhecimentoDto.getTermoPesquisaNota());
				}
			}
		} else {
			parametros.put("nota", null);
		}
		
		if(baseConhecimentoDto.getUltimoAcesso()!=null && baseConhecimentoDto.getUltimoAcesso().equals("S")){
			parametros.put("ultimoacesso", "Sim");
		}else{
			parametros.put("ultimoacesso", "Não");
		}

		if (baseConhecimentoDto.getUltimoAcesso() != null && baseConhecimentoDto.getUltimoAcesso().equals("S")) {
			parametros.put("ultimoacesso", "Sim");
		} else {
			parametros.put("ultimoacesso", "Não");
		}

		if (baseConhecimentoDto.getStatus() != null) {
			if (baseConhecimentoDto.getStatus().equalsIgnoreCase("")) {
				parametros.put("situacao", "Todos");
			} else {
				baseConhecimentoDto.setStatus(baseConhecimentoDto.getStatus()!=null&&baseConhecimentoDto.getStatus().equals("S") ? 
						UtilI18N.internacionaliza(request, "pesquisaBaseConhecimento.publicado") : UtilI18N.internacionaliza(request, "pesquisaBaseConhecimento.naoPublicado"));
				parametros.put("situacao", baseConhecimentoDto.getStatus());
			}
		} else {
			parametros.put("situacao", null);
		}

		JRDataSource dataSource = null;
		if (listaBaseConhecimento == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		if (baseConhecimentoDto.getAcessado() == null) {
			listaGeral.addAll(this.listarBaseConhecimento(request,listaBaseConhecimento, baseConhecimentoDto, baseConhecimentoService, contadorAcessoService));
			/*for (BaseConhecimentoDTO base : listaBaseConhecimento) {
				if (baseConhecimentoDto.getTermoPesquisaNota() != null && !baseConhecimentoDto.getTermoPesquisaNota().equalsIgnoreCase("S")
						&& !baseConhecimentoDto.getTermoPesquisaNota().equalsIgnoreCase("")) {
					base.setMedia(this.calcularMdia(base, baseConhecimentoService));
					base.setContadorCliques(contadorAcessoService.quantidadesDeAcessoPorBaseConhecimnto(base));
					if (base.getMedia().equalsIgnoreCase(baseConhecimentoDto.getTermoPesquisaNota())) {
						listaBaseConhecimentoMedia.add(base);
						listaGeral = listaBaseConhecimentoMedia;
					}
				} else {
					base.setContadorCliques(contadorAcessoService.quantidadesDeAcessoPorBaseConhecimnto(base));
					base.setMedia(this.calcularMdia(base, baseConhecimentoService));
					listaGeral = (List<BaseConhecimentoDTO>) listaBaseConhecimento;

				}

			}*/
		}

		else if (baseConhecimentoDto.getAcessado() != null && baseConhecimentoDto.getAcessado().equalsIgnoreCase("A")) {
			parametros.put("ordenacao", UtilI18N.internacionaliza(request, "relatorioBaseConhecimento.ordenarPorQuantidadeAcessos"));
			
			listaGeral.addAll(this.listarBaseConhecimento(request,listaBaseConhecimento, baseConhecimentoDto, baseConhecimentoService, contadorAcessoService));
			Collections.sort(listaGeral, comparaAcesso);
		
			/*for (BaseConhecimentoDTO base : listaBaseConhecimento) {
				if (baseConhecimentoDto.getTermoPesquisaNota() != null && !baseConhecimentoDto.getTermoPesquisaNota().equalsIgnoreCase("S") && !baseConhecimentoDto.getTermoPesquisaNota().equalsIgnoreCase("")) {
					base.setMedia(this.calcularMdia(base, baseConhecimentoService));
					base.setContadorCliques(contadorAcessoService.quantidadesDeAcessoPorBaseConhecimnto(base));
					if (base.getMedia().equalsIgnoreCase(baseConhecimentoDto.getTermoPesquisaNota())) {
						listaBaseConhecimentoMedia.add(base);
						listaGeral = listaBaseConhecimentoMedia;
						Collections.sort(listaGeral, comparaAcesso);
					}
				} else {
					base.setContadorCliques(contadorAcessoService.quantidadesDeAcessoPorBaseConhecimnto(base));
					base.setMedia(this.calcularMdia(base, baseConhecimentoService));
					mapaBaseConhecimento.add(base);
					Collections.sort(mapaBaseConhecimento, comparaAcesso);
					listaGeral = mapaBaseConhecimento;
				}
			}*/
		}

		else if (baseConhecimentoDto.getAcessado() != null && baseConhecimentoDto.getAcessado().equalsIgnoreCase("M")) {
			parametros.put("ordenacao", UtilI18N.internacionaliza(request, "relatorioBaseConhecimento.ordenarPorMediaAvaliacao"));
			listaGeral.addAll(this.listarBaseConhecimento(request,listaBaseConhecimento, baseConhecimentoDto, baseConhecimentoService, contadorAcessoService));
			Collections.sort(listaGeral, comparaMedia);
			/*for (BaseConhecimentoDTO base : listaBaseConhecimento) {
				if (baseConhecimentoDto.getTermoPesquisaNota() != null && !baseConhecimentoDto.getTermoPesquisaNota().equalsIgnoreCase("S") && !baseConhecimentoDto.getTermoPesquisaNota().equalsIgnoreCase("")) {
					base.setMedia(this.calcularMdia(base, baseConhecimentoService));
					base.setContadorCliques(contadorAcessoService.quantidadesDeAcessoPorBaseConhecimnto(base));
					if (base.getMedia().equalsIgnoreCase(baseConhecimentoDto.getTermoPesquisaNota())) {
						listaBaseConhecimentoMedia.add(base);
						listaGeral = listaBaseConhecimentoMedia;
						Collections.sort(listaGeral, comparaMedia);

					}
				} else {
					base.setContadorCliques(contadorAcessoService.quantidadesDeAcessoPorBaseConhecimnto(base));
					base.setMedia(this.calcularMdia(base, baseConhecimentoService));
					mapaBaseConhecimento.add(base);
					listaGeral = mapaBaseConhecimento;
					Collections.sort(listaGeral, comparaMedia);

				}
			}*/
		} else {
			parametros.put("ordenacao", UtilI18N.internacionaliza(request, "relatorioBaseConhecimento.ordenarPorVersao"));
			listaGeral.addAll(this.listarBaseConhecimento(request,listaBaseConhecimento, baseConhecimentoDto, baseConhecimentoService, contadorAcessoService));
			Collections.sort(listaGeral, comparaPorVersao);
			/*for (BaseConhecimentoDTO base : listaBaseConhecimento) {
				if (baseConhecimentoDto.getTermoPesquisaNota() != null && !baseConhecimentoDto.getTermoPesquisaNota().equalsIgnoreCase("S") && !baseConhecimentoDto.getTermoPesquisaNota().equalsIgnoreCase("")) {
					base.setMedia(this.calcularMdia(base, baseConhecimentoService));
					base.setContadorCliques(contadorAcessoService.quantidadesDeAcessoPorBaseConhecimnto(base));
					if (base.getMedia().equalsIgnoreCase(baseConhecimentoDto.getTermoPesquisaNota())) {
						listaBaseConhecimentoMedia.add(base);
						listaGeral = listaBaseConhecimentoMedia;
						Collections.sort(listaGeral, comparaPorVersao);

					}
				} else {
					base.setContadorCliques(contadorAcessoService.quantidadesDeAcessoPorBaseConhecimnto(base));
					base.setMedia(this.calcularMdia(base, baseConhecimentoService));
					mapaBaseConhecimento.add(base);
					listaGeral = mapaBaseConhecimento;
					Collections.sort(listaGeral, comparaPorVersao);

				}
			}*/
		}
		if (baseConhecimentoDto.getUltimaVersao() != null && baseConhecimentoDto.getUltimaVersao().equals("S")) {
			parametros.put("ultimasVersoes", UtilI18N.internacionaliza(request, "relatorioBaseConhecimento.ultimasVersoes"));
			if (listaUltimasVersoes != null) {
				listaGeral.addAll(this.listarBaseConhecimento(request,listaBaseConhecimentoUltimasVersoes, baseConhecimentoDto, baseConhecimentoService, contadorAcessoService));
				if (baseConhecimentoDto.getAcessado() != null && baseConhecimentoDto.getAcessado().equalsIgnoreCase("A")) {
					Collections.sort(listaGeral, comparaAcesso);
				} else if (baseConhecimentoDto.getAcessado() != null && baseConhecimentoDto.getAcessado().equalsIgnoreCase("M")) {
					Collections.sort(listaGeral, comparaMedia);
				} else {
					Collections.sort(listaGeral, comparaPorVersao);
				}
				/*for (BaseConhecimentoDTO base : listaUltimasVersoes) {
					if (baseConhecimentoDto.getTermoPesquisaNota() != null && !baseConhecimentoDto.getTermoPesquisaNota().equalsIgnoreCase("S") && !baseConhecimentoDto.getTermoPesquisaNota().equalsIgnoreCase("")) {
						base.setMedia(this.calcularMdia(base, baseConhecimentoService));
						base.setContadorCliques(contadorAcessoService.quantidadesDeAcessoPorBaseConhecimnto(base));
						if (base.getMedia().equalsIgnoreCase(baseConhecimentoDto.getTermoPesquisaNota())) {
							listaBaseConhecimentoUltimasVersoes.add(base);
							listaGeral = listaBaseConhecimentoUltimasVersoes;
							if (baseConhecimentoDto.getAcessado() != null && baseConhecimentoDto.getAcessado().equalsIgnoreCase("A")) {
								Collections.sort(listaGeral, comparaAcesso);
							} else if (baseConhecimentoDto.getAcessado() != null && baseConhecimentoDto.getAcessado().equalsIgnoreCase("M")) {
								Collections.sort(listaGeral, comparaMedia);
							} else {
								Collections.sort(listaGeral, comparaPorVersao);
							}

						}
					} else {
						base.setContadorCliques(contadorAcessoService.quantidadesDeAcessoPorBaseConhecimnto(base));
						base.setMedia(this.calcularMdia(base, baseConhecimentoService));
						listaBaseConhecimentoUltimasVersoes.add(base);
						listaGeral = listaBaseConhecimentoUltimasVersoes;
						if (baseConhecimentoDto.getAcessado() != null && baseConhecimentoDto.getAcessado().equalsIgnoreCase("A")) {
							Collections.sort(listaGeral, comparaAcesso);
						} else if (baseConhecimentoDto.getAcessado() != null && baseConhecimentoDto.getAcessado().equalsIgnoreCase("M")) {
							Collections.sort(listaGeral, comparaMedia);
						} else {
							Collections.sort(listaGeral, comparaPorVersao);
						}
					}
				}*/
			}

		}
		
		if (listaGeral.size() == 0) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		} /*else {
			for (BaseConhecimentoDTO baseAux : listaGeral) {
				if (baseAux.getMedia().equalsIgnoreCase("-1")) {
					baseAux.setMedia(UtilI18N.internacionaliza(request, "relatorioBaseConhecimento.semAvaliacao"));
				}
			}
			
		}*/
		parametros.put("totalBaseConhecimento", listaGeral.size());
		dataSource = new JRBeanCollectionDataSource(listaGeral);

		Date dt = new Date();
		String strCompl = "" + dt.getTime();
		String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioBaseConhecimentoXls.jasper";
		String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
		String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

		JasperDesign desenho = JRXmlLoader.load(CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioBaseConhecimentoXls.jrxml");

		JasperReport relatorio = JasperCompileManager.compileReport(desenho);

		JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros, dataSource);

		JRXlsExporter exporter = new JRXlsExporter();
		exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, impressao);
		exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.IS_FONT_SIZE_FIX_ENABLED, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, diretorioReceita + "/RelatorioBaseConhecimentoXls" + strCompl + "_" + usuario.getIdUsuario() + ".xls");

		exporter.exportReport();

		document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS + "/RelatorioBaseConhecimentoXls"
				+ strCompl + "_" + usuario.getIdUsuario() + ".xls')");
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();

	}
	
	

}
