package br.com.centralit.citcorpore.ajaxForms;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.reflexao.CitAjaxReflexao;
import br.com.centralit.citcertdigital.CITAssinadorDigital;
import br.com.centralit.citcorpore.bean.InformacoesContratoConfigDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoQuestionarioDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.bean.ValidacaoCertificadoDigitalDTO;
import br.com.centralit.citcorpore.negocio.ContratoQuestionariosService;
import br.com.centralit.citcorpore.negocio.InformacoesContratoConfigService;
import br.com.centralit.citcorpore.negocio.InformacoesContratoPerfSegService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.CriptoUtils;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.bean.ControleGEDExternoDTO;
import br.com.centralit.citged.negocio.ControleGEDService;
import br.com.centralit.citquestionario.ajaxForms.QuestionarioResponser;
import br.com.centralit.citquestionario.bean.AplicacaoQuestionarioDTO;
import br.com.centralit.citquestionario.bean.ArquivoMultimidiaDTO;
import br.com.centralit.citquestionario.bean.GrupoQuestionarioDTO;
import br.com.centralit.citquestionario.bean.ItemSubQuestionarioDTO;
import br.com.centralit.citquestionario.bean.LinhaSpoolQuestionario;
import br.com.centralit.citquestionario.bean.QuestaoQuestionarioDTO;
import br.com.centralit.citquestionario.bean.QuestionarioDTO;
import br.com.centralit.citquestionario.bean.RespostaItemAuxiliarDTO;
import br.com.centralit.citquestionario.bean.RespostaItemQuestionarioAnexosDTO;
import br.com.centralit.citquestionario.bean.RespostaItemQuestionarioDTO;
import br.com.centralit.citquestionario.bean.RespostaItemQuestionarioOpcoesDTO;
import br.com.centralit.citquestionario.negocio.AplicacaoQuestionarioService;
import br.com.centralit.citquestionario.negocio.GrupoQuestionarioService;
import br.com.centralit.citquestionario.negocio.OpcaoRespostaQuestionarioService;
import br.com.centralit.citquestionario.negocio.QuestaoQuestionarioService;
import br.com.centralit.citquestionario.negocio.QuestionarioService;
import br.com.centralit.citquestionario.negocio.RespostaItemQuestionarioService;
import br.com.centralit.citquestionario.util.WebValuesQuestionario;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.Html2Pdf;
import br.com.citframework.util.Reflexao;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilFormatacao;
import br.com.citframework.util.UtilHTML;
import br.com.citframework.util.UtilStrings;
import br.com.citframework.util.UtilTratamentoArquivos;

import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;


public class SolicitacaoServicoQuestionario extends QuestionarioResponser {
	
	private static final Color COR_FUNDO = Color.WHITE;
	private static final Color COR_TITULO = Color.BLACK;
	
	public Class getBeanClass() {
		return SolicitacaoServicoQuestionarioDTO.class;
	}

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getSession().setAttribute("HASH_CONTEUDO", null);
		request.getSession().setAttribute("TABELA_ASS_DIGITAL", null);
		request.getSession().setAttribute("KEY_ASS_DIGITAL", null);
	    request.getSession(true).setAttribute("dados_solicit_quest", null);
	      
	    String mulitploQuestionario = request.getParameter("mulitploQuestionario");
	    
		Integer idEmpresa = 1;
		
		SolicitacaoServicoQuestionarioDTO solQuestBean = (SolicitacaoServicoQuestionarioDTO) document.getBean();
		String continuarEdt = solQuestBean.getContinuarEdt();
		if (continuarEdt == null){
			continuarEdt = "N";
		}
		if (solQuestBean.getAba() == null){
			solQuestBean.setAba("");
		}

		
		Collection colValoresRequisicao = WebValuesQuestionario.getFormValues(request);
		
		boolean responderQuestionarioPorLinha = Boolean.valueOf(mulitploQuestionario);
		
		if (!responderQuestionarioPorLinha){
		
			solQuestBean.setColValores(colValoresRequisicao);			
		
		}else{
			
			solQuestBean.setColValores(colValoresRequisicao);

			solQuestBean.setRespostaObrigatoria(request.getParameter("respostaObrigatoria"));
			
			solQuestBean.setQuestionarioRespondido("S");
			
			montarListaQuestionarioRespondido(request, solQuestBean);
			
			
		}
		
		Collection colAnexos = new ArrayList();
		
		List lst = (List) request.getSession(true).getAttribute("TEMP_LISTA_ARQ_MULTIMIDIA");
		if (lst == null){
			lst = new ArrayList();
		}
		
		Collection TEMP_LISTA_CERTIFICADO_DIGITAL = (Collection) request.getSession().getAttribute("TEMP_LISTA_CERTIFICADO_DIGITAL");
		solQuestBean.setColCertificados(TEMP_LISTA_CERTIFICADO_DIGITAL);		
		
		ControleGEDService controleGEDService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
		for(Iterator it = lst.iterator(); it.hasNext();){
			ArquivoMultimidiaDTO arquivoMultimidia = (ArquivoMultimidiaDTO)it.next();
			ControleGEDDTO controleGEDDTO = new ControleGEDDTO();
			
			String GED_INTERNO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedInterno, "S");
			if (GED_INTERNO == null){
				GED_INTERNO = "S";
			}
			String prontuarioGedInternoBancoDados = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedInternoBD, "N");
		    if(!UtilStrings.isNotVazio(prontuarioGedInternoBancoDados))
		        prontuarioGedInternoBancoDados = "N";	
		    
			String GED_DIRETORIO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedDiretorio,"");
			if (GED_DIRETORIO == null || GED_DIRETORIO.trim().equalsIgnoreCase("")) {
			    GED_DIRETORIO = "";
			}	
			if (GED_DIRETORIO.equalsIgnoreCase("")) {
			    GED_DIRETORIO = Constantes.getValue("DIRETORIO_GED");
			}
			if (GED_DIRETORIO == null || GED_DIRETORIO.equalsIgnoreCase("")) {
			    GED_DIRETORIO = "/ged";
			}
			
			String pasta = "";
			if (GED_INTERNO.equalsIgnoreCase("S")){
				pasta = controleGEDService.getProximaPastaArmazenar();
				
				File fileDir = new File(GED_DIRETORIO);
				if (!fileDir.exists()){
				    fileDir.mkdirs();
				}
				fileDir = new File(GED_DIRETORIO + "/" + idEmpresa);
				if (!fileDir.exists()){
				    fileDir.mkdirs();
				}
				fileDir = new File(GED_DIRETORIO + "/" + idEmpresa + "/" + pasta);
				if (!fileDir.exists()){
				    fileDir.mkdirs();
				}
			}
			
			controleGEDDTO.setIdTabela(ControleGEDDTO.TABELA_RESPOSTAITEMQUESTIONARIOANEXOS);
			controleGEDDTO.setId(new Integer(0));
			controleGEDDTO.setDataHora(UtilDatas.getDataAtual());
			controleGEDDTO.setDescricaoArquivo(arquivoMultimidia.getNomeArquivo());
			controleGEDDTO.setExtensaoArquivo(br.com.centralit.citcorpore.util.Util.getFileExtension(arquivoMultimidia.getNomeArquivo()));
			controleGEDDTO.setPasta(pasta);
			controleGEDDTO.setNomeArquivo(arquivoMultimidia.getNomeArquivo());
			
			if (!arquivoMultimidia.getCaminhoArquivo().startsWith("IDCITGED=")){
				if(GED_INTERNO.equalsIgnoreCase("S") && "S".equalsIgnoreCase(prontuarioGedInternoBancoDados)){
					java.util.Date now = new java.util.Date();
					CriptoUtils.encryptFile(arquivoMultimidia.getCaminhoArquivo(), 
							GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/TMP_" + now.getTime() + ".ged", 
							this.getClass().getResourceAsStream(Constantes.getValue("CAMINHO_CHAVE_PUBLICA")));
					
					controleGEDDTO.setPathArquivo(GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/TMP_" + now.getTime() + ".ged");
				}				
				controleGEDDTO = (ControleGEDDTO) controleGEDService.create(controleGEDDTO);
				if (GED_INTERNO.equalsIgnoreCase("S") && !"S".equalsIgnoreCase(prontuarioGedInternoBancoDados)){ //Se utiliza GED interno
					if (controleGEDDTO != null){
						CriptoUtils.encryptFile(arquivoMultimidia.getCaminhoArquivo(), 
								GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + ".ged", 
								this.getClass().getResourceAsStream(Constantes.getValue("CAMINHO_CHAVE_PUBLICA")));
						
						if (solQuestBean.getColCertificados() != null && solQuestBean.getColCertificados().size() > 0){
							int ordem = 1;
							CITAssinadorDigital d = new CITAssinadorDigital();
							d.inicializar();				
							for(Iterator itCert = solQuestBean.getColCertificados().iterator(); itCert.hasNext();){
								ValidacaoCertificadoDigitalDTO validacaoCertificadoDigitalDTO = (ValidacaoCertificadoDigitalDTO)itCert.next();
								
								InputStream inStream = new FileInputStream(validacaoCertificadoDigitalDTO.getCaminhoCompleto());
								CertificateFactory cf = CertificateFactory.getInstance("X.509", "BC");
								X509Certificate cert = (X509Certificate)cf.generateCertificate(inStream);
								d.assinar(validacaoCertificadoDigitalDTO.getInfoCertificadoDigital().getCpf(), arquivoMultimidia.getCaminhoArquivo(), GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + ".signed" + ordem, cert, false);
								
								ordem++;
							}
						}						
					}
				}else if(!GED_INTERNO.equalsIgnoreCase("S")){ //Se utiliza GED externo
					String CLASSE_GED_EXTERNO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedExternoClasse, "");
					Class classe = Class.forName(CLASSE_GED_EXTERNO);
					Object objeto = classe.newInstance();
					Method mtd = CitAjaxReflexao.findMethod("create", objeto);
					
					ControleGEDExternoDTO controleGedExternoDto = new ControleGEDExternoDTO();
					Reflexao.copyPropertyValues(controleGEDDTO, controleGedExternoDto);
					
					HashMap hshInfo = new HashMap();
					
					File file = new File(arquivoMultimidia.getCaminhoArquivo());
					controleGedExternoDto.setConteudoDocumento(UtilTratamentoArquivos.getBytesFromFile(file));
					
					byte[] conteudoAssinaturaDigital = null;
					if (solQuestBean.getColCertificados() != null && solQuestBean.getColCertificados().size() > 0){
						int ordem = 1;
						CITAssinadorDigital d = new CITAssinadorDigital();
						d.inicializar();				
						for(Iterator itCert = solQuestBean.getColCertificados().iterator(); itCert.hasNext();){
							ValidacaoCertificadoDigitalDTO validacaoCertificadoDigitalDTO = (ValidacaoCertificadoDigitalDTO)itCert.next();
							
							InputStream inStream = new FileInputStream(validacaoCertificadoDigitalDTO.getCaminhoCompleto());
							CertificateFactory cf = CertificateFactory.getInstance("X.509", "BC");
							X509Certificate cert = (X509Certificate)cf.generateCertificate(inStream);
							conteudoAssinaturaDigital = d.assinar(validacaoCertificadoDigitalDTO.getInfoCertificadoDigital().getCpf(), arquivoMultimidia.getCaminhoArquivo(), cert, false);
							
							ordem++;
							break; //Faz so a primeira.
						}
					}					
					controleGedExternoDto.setConteudoAssinaturaDigital(conteudoAssinaturaDigital);
					
					mtd.invoke(objeto, new Object[] {controleGedExternoDto, hshInfo} );
				}
			}else{
				controleGEDDTO.setIdControleGED(new Integer(UtilStrings.apenasNumeros(arquivoMultimidia.getCaminhoArquivo())));
			}
			
			RespostaItemQuestionarioAnexosDTO respItemQuestAnexoDto = new RespostaItemQuestionarioAnexosDTO();
			if(controleGEDDTO != null){
				respItemQuestAnexoDto.setCaminhoAnexo(controleGEDDTO.getIdControleGED().toString());
			}
			respItemQuestAnexoDto.setObservacao(arquivoMultimidia.getObservacao());
			respItemQuestAnexoDto.setIdQuestaoQuestionario(arquivoMultimidia.getIdQuestaoQuest());
			
			colAnexos.add(respItemQuestAnexoDto);
		}
		
			solQuestBean.setColAnexos(colAnexos);		
				
			request.getSession(true).setAttribute("dados_solicit_quest", solQuestBean);
		
		
	}	
	
	
	/**
	 * Metodo criado para atender a validação de amarzenar na sessão todos os questionarios respondidos para os serviços adicionados ao carrinho.
	 * 
	 * Verifica se a lista "colecao_dados_solicit_quest" já existe na sessão, se não o objeto pe inicializado, após os dados são adicionados a 
	 * 
	 * coleção e a coleção é armazenada novamente na sessão.
	 * 
	 * @param request
	 * @param dto
	 * @author Ezequiel
	 */
	@SuppressWarnings("unchecked")
	private void montarListaQuestionarioRespondido(HttpServletRequest request, SolicitacaoServicoQuestionarioDTO dto){
		
		Collection<SolicitacaoServicoQuestionarioDTO> dtoRequest = (Collection<SolicitacaoServicoQuestionarioDTO>) request.getSession(true).getAttribute("colecao_dados_solicit_quest");
		
		if (dtoRequest == null){
		
			dtoRequest = new ArrayList<SolicitacaoServicoQuestionarioDTO>();
		}
		
		dtoRequest.add(dto);
				
		request.getSession(true).setAttribute("colecao_dados_solicit_quest", dtoRequest);
		
	}
	
	public void validate(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoQuestionarioDTO pesQuestBean = (SolicitacaoServicoQuestionarioDTO) document.getBean();
		InformacoesContratoConfigService prontuarioEletronicoConfigService = (InformacoesContratoConfigService) ServiceLocator.getInstance().getService(InformacoesContratoConfigService.class, null);
		Collection col = prontuarioEletronicoConfigService.findByNome(pesQuestBean.getAba());
		InformacoesContratoConfigDTO prontuarioEletronicoConfigDto = null;
		if (col != null){
			for(Iterator it = col.iterator(); it.hasNext();){
				prontuarioEletronicoConfigDto = (InformacoesContratoConfigDTO)it.next();
				break;
			}
		}
		if (prontuarioEletronicoConfigDto == null){
			document.executeScript("parent.finalizacao()");
			return;
		}
		if (prontuarioEletronicoConfigDto.getValidacoes() != null && !prontuarioEletronicoConfigDto.getValidacoes().trim().equalsIgnoreCase("")){
			String strAux = prontuarioEletronicoConfigDto.getValidacoes() + ",";
			String[] str = strAux.split(",");
			prontuarioEletronicoConfigDto.setValidacoesAux(str);
		}		
		document.executeScript("parent.finalizacao()");
		return;		
	}
	
	public Collection organizaSubQuestionariosPorIdItem(Collection colValores){
		HashMap map = new HashMap();
		Collection colSubQuestionarios = new ArrayList();
		if (colValores != null){
			for(Iterator it = colValores.iterator(); it.hasNext();){
				RespostaItemAuxiliarDTO respItem = (RespostaItemAuxiliarDTO)it.next();
				if (respItem.getFieldName().length()<9) continue;
				if (respItem.getFieldName().startsWith("SUBQUESTIONARIO#")){
					String[] str = respItem.getFieldName().split("#");
					if (str != null){
						if (str.length > 2){
							int x = 0;
							try{
								x = Integer.parseInt(str[1]);
							}catch (Exception e) {
							}
							if (x > 0){
								Collection col = null;
								ItemSubQuestionarioDTO itemSubQuestionarioAuxDTO = null;
								boolean bIncluir = false;
								if (map.containsKey("ITEM" + x)){
									itemSubQuestionarioAuxDTO = (ItemSubQuestionarioDTO) map.get("ITEM" + x);
									col = itemSubQuestionarioAuxDTO.getColSubQuestionario();
								}else{
									col = new ArrayList();
									bIncluir = true;
								}
								respItem.setFieldName(str[2]);
								col.add(respItem);
								if (bIncluir){
									ItemSubQuestionarioDTO itemSubQuestionarioDTO = new ItemSubQuestionarioDTO();
									itemSubQuestionarioDTO.setIdItem(x);
									itemSubQuestionarioDTO.setColSubQuestionario(col);
									colSubQuestionarios.add(itemSubQuestionarioDTO);
									map.put("ITEM" + x, itemSubQuestionarioDTO);
								}
							}
						}
					}
				}
			}
		}
		return colSubQuestionarios;
	}	
	
	public void listarCertificados(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null){
			document.alert("Sessão expirada! Favor efetuar logon novamente!");
			return;
		}
		SolicitacaoServicoQuestionarioDTO pesQuestBean = (SolicitacaoServicoQuestionarioDTO) document.getBean();
		
		List lst = (List) request.getSession(true).getAttribute("TEMP_LISTA_CERTIFICADO_DIGITAL");
		if (lst == null){
			lst = new ArrayList();
		}
		
		String strTable = "<table border='1' width='100%'>";
		for(Iterator it = lst.iterator(); it.hasNext();){
			ValidacaoCertificadoDigitalDTO vCertDigDto = (ValidacaoCertificadoDigitalDTO) it.next();
			if (vCertDigDto.getInfoCertificadoDigital() == null){
				strTable += "<tr>";
				strTable += "<td>";
				strTable += "</td>";
				if (vCertDigDto.getFileName() == null || vCertDigDto.getFileName().equalsIgnoreCase("")){
					strTable += "<td>";
						strTable += "<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/produtos/citsaude/imagens/iconesProntuario/9.jpg' border='0'/>";
					strTable += "</td>";					
				}else{
					strTable += "<td>";
						strTable += "<b><font color='red'>" + vCertDigDto.getFileName() + "</font></b>";
					strTable += "</td>";
				}
				strTable += "</tr>";
			}else{
				String strUso = "";
				String strAdequado = "Não";
				String strAtencao = "";
				if (vCertDigDto.getInfoCertificadoDigital().isKeyUsageDigitalSignature()){
					strUso += "Assinatura digital";
				}
				if (vCertDigDto.getInfoCertificadoDigital().isKeyUsageNonRepudiation()){
					if (!strUso.equalsIgnoreCase("")){
						strUso += ",";
					}
					strUso += "Non Repudiation";
				}
				if (vCertDigDto.getInfoCertificadoDigital().isKeyUsageKeyEncipherment()){
					if (!strUso.equalsIgnoreCase("")){
						strUso += ",";
					}				
					strUso += "Key Encipherment";
				}
				if (vCertDigDto.getInfoCertificadoDigital().isKeyUsageDataEncipherment()){
					if (!strUso.equalsIgnoreCase("")){
						strUso += ",";
					}				
					strUso += "Data Encipherment";
				}
				if (vCertDigDto.getInfoCertificadoDigital().isKeyUsageKeyAgreement()){
					if (!strUso.equalsIgnoreCase("")){
						strUso += ",";
					}				
					strUso += "Key Agreement";
				}
				if (vCertDigDto.getInfoCertificadoDigital().isKeyUsageKeyCertSign()){
					if (!strUso.equalsIgnoreCase("")){
						strUso += ",";
					}				
					strUso += "CertSign";
				}
				if (vCertDigDto.getInfoCertificadoDigital().isKeyUsageCRLSign()){
					if (!strUso.equalsIgnoreCase("")){
						strUso += ",";
					}				
					strUso += "CRLSign";
				}
				if (vCertDigDto.getInfoCertificadoDigital().isKeyUsageEncipherOnly()){
					if (!strUso.equalsIgnoreCase("")){
						strUso += ",";
					}				
					strUso += "Encipher Only";
				}
				if (vCertDigDto.getInfoCertificadoDigital().isKeyUsageDecipherOnly()){
					if (!strUso.equalsIgnoreCase("")){
						strUso += ",";
					}				
					strUso += "Decipher Only";
				}
				
				if (vCertDigDto.getInfoCertificadoDigital().isKeyUsageDigitalSignature() && 
						vCertDigDto.getInfoCertificadoDigital().isKeyUsageNonRepudiation()){
					if (UtilDatas.getDataAtual().compareTo(vCertDigDto.getInfoCertificadoDigital().getDataFimValidade()) <= 0){
						if (UtilDatas.getDataAtual().compareTo(vCertDigDto.getInfoCertificadoDigital().getDataInicioValidade()) >= 0){
							strAdequado = "Sim";
						}else{
							strAtencao = "<font color='red'><b>Certificado ainda não entrou em validade!</font></b>";
						}
					}else{
						strAtencao = "<font color='red'><b>Certificado vencido!</font></b>";
					}
				}
				
				strTable += "<tr>";
					strTable += "<td>";
					strTable += "</td>";
					if (vCertDigDto.getFileName() == null || vCertDigDto.getFileName().equalsIgnoreCase("")){
						if (strAdequado.equalsIgnoreCase("Sim")){
							strTable += "<td>";
								strTable += "<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/produtos/citsaude/imagens/iconesProntuario/9.jpg' border='0'/>";
							strTable += "</td>";	
						}else{
							strTable += "<td>";
								strTable += "<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/produtos/citsaude/imagens/iconesProntuario/pare.gif' border='0'/>";
							strTable += "</td>";							
						}
					}else{
						strTable += "<td>";
							strTable += "<b><font color='red'>" + vCertDigDto.getFileName() + "</font></b>";
						strTable += "</td>";
					}					
					strTable += "<td>";
						strTable += "Certificado em nome de: <b>" + vCertDigDto.getInfoCertificadoDigital().getNomeTitular() + "</b> CPF: <b>" + vCertDigDto.getInfoCertificadoDigital().getCpf() + "</b><br>";
						strTable += "Raiz: <b>" + vCertDigDto.getInfoCertificadoDigital().getRaiz() + "</b> Emissor: <b>" + vCertDigDto.getInfoCertificadoDigital().getNomeEmissor() + "</b><br>";
						strTable += "<b>" + vCertDigDto.getInfoCertificadoDigital().getPais() + "</b> Tipo: <b>" + vCertDigDto.getInfoCertificadoDigital().getTipo() + "</b> Validade entre: <b>" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, vCertDigDto.getInfoCertificadoDigital().getDataInicioValidade(), WebUtil.getLanguage(request)) + " a " + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, vCertDigDto.getInfoCertificadoDigital().getDataFimValidade(), WebUtil.getLanguage(request)) + "</b><br>";
						strTable += "Utilizações permitidas: <b>" + strUso + "</b><br>";
						strTable += "Adequado para informações médicas: <b>" + strAdequado  + "</b><br>";
						strTable += strAtencao;
					strTable += "</td>";
				strTable += "</tr>";	
			}
		}
		strTable += "</table>";
		
		document.getElementById(pesQuestBean.getDivAtualizarCertificado()).setInnerHTML(strTable);
	}
	
	public String geraDadosFormulario(Integer idEmpresa, SolicitacaoServicoQuestionarioDTO contQuestBean, boolean geraCabecalho, boolean imprimeDemaisDados, boolean imprimeLogo) throws Exception {		
		ContratoQuestionariosService contratoQuestionariosService = (ContratoQuestionariosService) ServiceLocator.getInstance().getService(ContratoQuestionariosService.class, null);
		SolicitacaoServicoQuestionarioDTO contQuestPesqBean = (SolicitacaoServicoQuestionarioDTO) contratoQuestionariosService.restore(contQuestBean);
		
		QuestionarioService questionarioService = (QuestionarioService) ServiceLocator.getInstance().getService(QuestionarioService.class, null);
		QuestionarioDTO questionario = new QuestionarioDTO();
		questionario.setIdIdentificadorResposta(contQuestBean.getIdSolicitacaoQuestionario());
		questionario.setIdQuestionario(contQuestBean.getIdQuestionario());
		questionario = (QuestionarioDTO) questionarioService.restore(questionario);		
		
		GrupoQuestionarioService grupoQuestionarioService = (GrupoQuestionarioService) ServiceLocator.getInstance().getService(GrupoQuestionarioService.class, null);
		
		String strImprimir = "";
		
		strImprimir += "<table width='100%' border='1px solid black' cellpadding='0' cellspacing='0'>";
		
		if (questionario != null){
			Collection colGrupos = questionario.getColGrupos();
			for(Iterator it = colGrupos.iterator(); it.hasNext();){
				 GrupoQuestionarioDTO grupoQuestDto = (GrupoQuestionarioDTO)it.next();
				 Collection colRetorno = grupoQuestionarioService.geraImpressaoFormatadaHTML(grupoQuestDto.getColQuestoes(), contQuestPesqBean.getDataQuestionario(), contQuestPesqBean.getIdSolicitacaoServico(), contQuestPesqBean.getIdResponsavel());
				 if (colRetorno != null && colRetorno.size() > 0){
					 String strAux = "<tr><td colspan='20' bgcolor='#20b2aa'><b>" + grupoQuestDto.getNomeGrupoQuestionario() + "</b></td></tr>";
					 String strAux2 = "";
					 for(Iterator itQuest = colRetorno.iterator(); itQuest.hasNext();){
						 LinhaSpoolQuestionario linha = (LinhaSpoolQuestionario)itQuest.next();
						 if (linha.isGenerateTR()){
							 if (linha.getLinha() != null && !linha.getLinha().trim().equalsIgnoreCase("")){
								 strAux2 += "<tr>" + linha.getLinha() + "\n</tr>";
							 }
						 }else{
							 strAux2 += linha.getLinha() + "\n";
						 }
					 }
					 if (!strAux2.trim().equalsIgnoreCase("")){
						 strImprimir += strAux + strAux2;
					 }
				 }
			}
		}
		strImprimir += "</table>";	
		
		return strImprimir;
	}
	public String geraDadosFormularioSemAssinatura(Integer idEmpresa, SolicitacaoServicoQuestionarioDTO contQuestBean, boolean geraCabecalho, boolean imprimeDemaisDados, boolean imprimeLogo) throws Exception {		
		ContratoQuestionariosService contratoQuestionariosService = (ContratoQuestionariosService) ServiceLocator.getInstance().getService(ContratoQuestionariosService.class, null);
		SolicitacaoServicoQuestionarioDTO contQuestPesqBean = (SolicitacaoServicoQuestionarioDTO) contratoQuestionariosService.restore(contQuestBean);
		
		QuestionarioService questionarioService = (QuestionarioService) ServiceLocator.getInstance().getService(QuestionarioService.class, null);
		QuestionarioDTO questionario = new QuestionarioDTO();
		questionario.setIdIdentificadorResposta(contQuestBean.getIdSolicitacaoQuestionario());
		questionario.setIdQuestionario(contQuestBean.getIdQuestionario());
		questionario = (QuestionarioDTO) questionarioService.restore(questionario);		
		
		GrupoQuestionarioService grupoQuestionarioService = (GrupoQuestionarioService) ServiceLocator.getInstance().getService(GrupoQuestionarioService.class, null);
		
		String strImprimir = "";
		
		strImprimir += "<table width='100%' border='1px solid black' cellpadding='0' cellspacing='0'>";
		
		if (questionario != null){
			Collection colGrupos = questionario.getColGrupos();
			for(Iterator it = colGrupos.iterator(); it.hasNext();){
				 GrupoQuestionarioDTO grupoQuestDto = (GrupoQuestionarioDTO)it.next();
				 Collection colRetorno = grupoQuestionarioService.geraImpressaoFormatadaHTML(grupoQuestDto.getColQuestoes(), contQuestPesqBean.getDataQuestionario(), contQuestPesqBean.getIdSolicitacaoServico(), contQuestPesqBean.getIdResponsavel());
				 if (colRetorno != null && colRetorno.size() > 0){
					 String strAux = "<tr><td colspan='20' bgcolor='#20b2aa'><b>" + grupoQuestDto.getNomeGrupoQuestionario() + "</b></td></tr>";
					 String strAux2 = "";
					 for(Iterator itQuest = colRetorno.iterator(); itQuest.hasNext();){
						 LinhaSpoolQuestionario linha = (LinhaSpoolQuestionario)itQuest.next();
						 if (linha.isGenerateTR()){
							 if (linha.getLinha() != null && !linha.getLinha().trim().equalsIgnoreCase("")){
								 strAux2 += "<tr>" + linha.getLinha() + "\n</tr>";
							 }
						 }else{
							 strAux2 += linha.getLinha() + "\n";
						 }
					 }
					 if (!strAux2.trim().equalsIgnoreCase("")){
						 strImprimir += strAux + strAux2;
					 }
				 }
			}
		}
		strImprimir += "</table>";	
		
		return strImprimir;
	}
	
	public String gerarHistoricoGeralPacienteHTML(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null){
			document.alert("Sessão expirada! Favor efetuar logon novamente!");
			return "";
		}
		
		Integer idEmpresa = 1;
		String usuarioImpressao = usuario.getNomeUsuario();

		SolicitacaoServicoQuestionarioDTO pesQuestBean = (SolicitacaoServicoQuestionarioDTO) document.getBean();
		
		ContratoQuestionariosService contratoQuestionariosService = (ContratoQuestionariosService) ServiceLocator.getInstance().getService(ContratoQuestionariosService.class, null);
		InformacoesContratoConfigService prontuarioEletronicoConfigService = (InformacoesContratoConfigService) ServiceLocator.getInstance().getService(InformacoesContratoConfigService.class, null);
		InformacoesContratoPerfSegService prontuarioEletronicoPerfSegService = (InformacoesContratoPerfSegService) ServiceLocator.getInstance().getService(InformacoesContratoPerfSegService.class, null);
		
		QuestionarioService questService = (QuestionarioService) ServiceLocator.getInstance().getService(QuestionarioService.class, null);
			
		Collection col = null;
		if (pesQuestBean.getOrdemHistorico() != null && pesQuestBean.getOrdemHistorico().equalsIgnoreCase("D")){
			if (pesQuestBean.getAba() == null || pesQuestBean.getAba().trim().equalsIgnoreCase("")){
				col = contratoQuestionariosService.listByIdContratoOrderDecrescente(pesQuestBean.getIdSolicitacaoServico());
			}else{
				col = contratoQuestionariosService.listByIdContratoAndAba(pesQuestBean.getIdSolicitacaoServico(), pesQuestBean.getAba());
			}			
		}else{
			if (pesQuestBean.getAba() == null || pesQuestBean.getAba().trim().equalsIgnoreCase("")){
				col = contratoQuestionariosService.listByIdContrato(pesQuestBean.getIdSolicitacaoServico());
			}else{
				col = contratoQuestionariosService.listByIdContratoAndAbaOrdemCrescente(pesQuestBean.getIdSolicitacaoServico(), pesQuestBean.getAba());
			}
		}
		
		String strImprimir = "";
		boolean bPrimeiroItem = true;
		if (col != null){
			for(Iterator it = col.iterator(); it.hasNext();){
				SolicitacaoServicoQuestionarioDTO contratoQuestionariosAux = (SolicitacaoServicoQuestionarioDTO)it.next();
				Collection colItem = null;
				try{
					colItem = prontuarioEletronicoConfigService.findByNome(contratoQuestionariosAux.getAba());
				}catch (Exception e) {
					e.printStackTrace();
					colItem = null;
				}
				if (colItem != null && colItem.size() > 0){
					InformacoesContratoConfigDTO prontuarioEletronicoConfigDTO = (InformacoesContratoConfigDTO) ((List)colItem).get(0);
					Collection colPerfisAssociados = prontuarioEletronicoPerfSegService.findByIdProntuarioEletronicoConfig(prontuarioEletronicoConfigDTO.getIdInformacoesContratoConfig());
					if (isPerfilUsuarioLogadoInCollection(colPerfisAssociados, usuario)){ //Se tiver acesso a aquela ABA, então gera historico.
						String str = "";
						if (bPrimeiroItem){
							String nomeQuestionario = contratoQuestionariosAux.getNomeQuestionario();
							if (contratoQuestionariosAux.getNomeQuestionario() == null ||
									contratoQuestionariosAux.getNomeQuestionario().trim().equalsIgnoreCase("")){
								QuestionarioDTO questDto = new QuestionarioDTO();
								questDto.setIdQuestionario(contratoQuestionariosAux.getIdSolicitacaoQuestionario());
								questDto = (QuestionarioDTO) questService.restore(questDto);
								if (questDto != null){
									nomeQuestionario = questDto.getNomeQuestionario();
								}
							}
						}
						if (contratoQuestionariosAux.getConteudoImpresso() != null &&
								!contratoQuestionariosAux.getConteudoImpresso().trim().equalsIgnoreCase("")){
							str += contratoQuestionariosAux.getConteudoImpresso();
						}else{
							String conteudoImp = geraDadosFormularioSemAssinatura(idEmpresa, contratoQuestionariosAux, true, false, false);
							
							//-- Se chegar é que nao havia sido gravado, entao grava.
							try{
								pesQuestBean.setConteudoImpresso(conteudoImp);
								contratoQuestionariosService.updateConteudoImpresso(contratoQuestionariosAux.getIdSolicitacaoQuestionario(), conteudoImp);
							}catch (Exception e) {
								e.printStackTrace();
							}
							str += conteudoImp;
						}
						
						str += "<br><br>";
						
						bPrimeiroItem = false;
						strImprimir += str;
					}
				}
			}
		}
		return strImprimir;
	}
	public void gerarHistoricoGeralPaciente(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null){
			document.alert("Sessão expirada! Favor efetuar logon novamente!");
			return;
		}		
		String usuarioImpressao = usuario.getNomeUsuario();
		
		String strImprimir = gerarHistoricoGeralPacienteHTML(document, request, response);
		
		String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempReceitas/" + usuario.getIdUsuario();
		String diretorioRelativoReceita = Constantes.getValue("DIRETORIO_TEMPORARIO_RECEITAS_RELATIVO") + "/" + usuario.getIdUsuario();
		File f = new File(diretorioReceita);
		if (!f.exists()){
			f.mkdirs();
		}
		
		String arquivoForm = diretorioReceita + "/formulario.pdf";
		String arquivoFormRefact = diretorioReceita + "/formulario2.pdf";
		String arquivoRelativoForm = diretorioRelativoReceita + "/formulario.pdf";
		String arquivoRelativoFormRefact = diretorioRelativoReceita + "/formulario2.pdf";
		f = new File(arquivoForm);
		if (f.exists()){
			f.delete();
		}	
		
		//UtilTratamentoArquivos.geraFileTxtFromString("c:\\log.txt", strImprimir);
		
		OutputStream os = new FileOutputStream(arquivoForm);   
		if (strImprimir == null || strImprimir.trim().equalsIgnoreCase("")){
			strImprimir = "<p>&nbsp;</p>"; //Este item eh para nao gerar erro no comando abaixo.
		}
		strImprimir = UtilHTML.encodeHTML(strImprimir);
		Html2Pdf.convert(strImprimir, os);           
		os.close();
		
		PdfReader reader = new PdfReader(arquivoForm);
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(arquivoFormRefact));
		PdfContentByte over;
		int total = reader.getNumberOfPages() + 1;
		for (int i = 1; i < total; i++) {
			over = stamper.getUnderContent(i);
			over.beginText();
			BaseFont bf = BaseFont.createFont("Helvetica", BaseFont.WINANSI, false);
			over.setFontAndSize(bf, 8);
			over.setTextMatrix(30, 30);
			over.showText("Pagina: " + i + " de " + (total - 1) + "     prontuário impresso em: " + UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, UtilDatas.getDataHoraAtual(), WebUtil.getLanguage(request)) + " por: " + usuarioImpressao);
			over.endText();
			//over.setRGBColorStroke(0xFF, 0x00, 0x00);
			//over.setLineWidth(5f);
			//over.ellipse(250, 450, 350, 550);
			over.stroke();
		}
		stamper.close();
		
		File file = (new File(arquivoFormRefact));
		
		byte[] buffer = (UtilTratamentoArquivos.getBytesFromFile(file));		
		
		ServletOutputStream outputStream = response.getOutputStream();
		response.setContentLength(buffer.length);
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=Historico.pdf");
		outputStream.write(buffer);
		outputStream.flush();
		outputStream.close();
	}
	private boolean isPerfilUsuarioLogadoInCollection(Collection colPerfisAssociados, UsuarioDTO user){
	    return true;
	}
	/*
	private boolean isPerfilUsuarioLogadoInCollection(Collection colPerfisAssociados, UsuarioDTO user){
		if (colPerfisAssociados != null){
			for(Iterator it = colPerfisAssociados.iterator(); it.hasNext();){
				InformacoesContratoPerfSegDTO prontuarioEletronicoPerfSegDTO = (InformacoesContratoPerfSegDTO) it.next();
				if (user.getColPerfis() != null){
					for(Iterator itPerfUser = user.getColPerfis().iterator(); itPerfUser.hasNext();){
						PessoasPerfilSegurancaDTO pessoasPerfilSegurancaDTO = (PessoasPerfilSegurancaDTO) itPerfUser.next();
						if (pessoasPerfilSegurancaDTO.getIdPerfilSeguranca().intValue() == prontuarioEletronicoPerfSegDTO.getIdPerfilSeguranca().intValue()){
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	*/
		
	public void imprimir(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoQuestionarioDTO pesQuestBean = (SolicitacaoServicoQuestionarioDTO) document.getBean();
		
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null){
			document.alert("Sessão expirada! Favor efetuar logon novamente!");
			return;
		}
		
		Integer idEmpresa = 1;
		String usuarioImpressao = usuario.getNomeUsuario();
		/*if (usuario.getIdProfissional() != null){
			ProfissionalService profissionalService = (ProfissionalService) ServiceLocator.getInstance().getService(ProfissionalService.class, null);
			ProfissionalDTO profDto = new ProfissionalDTO();
			profDto.setIdProfissional(usuario.getIdProfissional());
			profDto.setIdEmpresa(idEmpresa);
			try{
				profDto = (ProfissionalDTO) profissionalService.restore(profDto);
			}catch (Exception e) {
				e.printStackTrace();
				profDto = null;
			}
			
			if (profDto != null){
				usuarioImpressao = usuarioImpressao + " - " + profDto.getNome();
			}
		}*/
		String strImprimir = "";
		ContratoQuestionariosService profissionalService = (ContratoQuestionariosService) ServiceLocator.getInstance().getService(ContratoQuestionariosService.class, null);
		try{
			pesQuestBean = (SolicitacaoServicoQuestionarioDTO) profissionalService.restore(pesQuestBean);
			strImprimir = geraDadosFormulario(idEmpresa, pesQuestBean, true, true, true);
		}catch (Exception e) {
			e.printStackTrace();
			strImprimir = "OCORREU PROBLEMA AO IMPRIMIR! FAVOR AVISAR AO ADMINISTRADOR DO SISTEMA\n<br>" + e.getMessage();
		}
		
		String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempReceitas/" + usuario.getIdUsuario();
		String diretorioRelativoReceita = Constantes.getValue("DIRETORIO_TEMPORARIO_RECEITAS_RELATIVO") + "/" + usuario.getIdUsuario();
		File f = new File(diretorioReceita);
		if (!f.exists()){
			f.mkdirs();
		}
		
		String arquivoForm = diretorioReceita + "/formulario.pdf";
		String arquivoFormRefact = diretorioReceita + "/formulario2.pdf";
		String arquivoRelativoForm = diretorioRelativoReceita + "/formulario.pdf";
		String arquivoRelativoFormRefact = diretorioRelativoReceita + "/formulario2.pdf";
		f = new File(arquivoForm);
		if (f.exists()){
			f.delete();
		}	
		
		//UtilTratamentoArquivos.geraFileTxtFromString("c:\\log.txt", strImprimir);
		
		OutputStream os = new FileOutputStream(arquivoForm);   
		strImprimir = UtilHTML.encodeHTML(strImprimir);
		Html2Pdf.convert(strImprimir, os);           
		os.close();
		
		PdfReader reader = new PdfReader(arquivoForm);
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(arquivoFormRefact));
		PdfContentByte over;
		int total = reader.getNumberOfPages() + 1;
		for (int i = 1; i < total; i++) {
			over = stamper.getUnderContent(i);
			over.beginText();
			BaseFont bf = BaseFont.createFont("Helvetica", BaseFont.WINANSI, false);
			over.setFontAndSize(bf, 8);
			over.setTextMatrix(30, 30);
			over.showText("Pagina: " + i + " de " + (total - 1) + "      impresso em: " + UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, UtilDatas.getDataHoraAtual(), WebUtil.getLanguage(request)) + "      por: " + usuarioImpressao);
			over.endText();
			//over.setRGBColorStroke(0xFF, 0x00, 0x00);
			//over.setLineWidth(5f);
			//over.ellipse(250, 450, 350, 550);
			over.stroke();
		}
		stamper.close();
		
		File file = (new File(arquivoFormRefact));
		
		byte[] buffer = (UtilTratamentoArquivos.getBytesFromFile(file));		
		
		try{
			ServletOutputStream outputStream = response.getOutputStream();
			response.setContentLength(buffer.length);
			response.setContentType("application/pdf");
			outputStream.write(buffer);
			outputStream.flush();
			outputStream.close();
		}catch (Exception e) {
			e.printStackTrace();
			document.alert("Ocorreu um erro ao realizar a impressão! " + e.getMessage());
		}
	}

    public void listarProdutosQuestionario(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null){
            document.alert("Sessão expirada! Favor efetuar logon novamente!");
            return;
        }
        
        String strTable = "";
        
        SolicitacaoServicoQuestionarioDTO pesQuestBean = (SolicitacaoServicoQuestionarioDTO) document.getBean();
        AplicacaoQuestionarioService aplicacaoQuestionarioService = (AplicacaoQuestionarioService) ServiceLocator.getInstance().getService(AplicacaoQuestionarioService.class, null);
        Collection col = aplicacaoQuestionarioService.listByIdQuestionarioAndAplicacao(pesQuestBean.getIdQuestionario(), "O");
        AplicacaoQuestionarioDTO aplicacaoQuestionarioDTO = null;
        if (col != null){
            for(Iterator it = col.iterator(); it.hasNext();){
                aplicacaoQuestionarioDTO = (AplicacaoQuestionarioDTO)it.next();
                break;
            }
        }
        if (aplicacaoQuestionarioDTO == null){
            document.getElementById("divSelecaoProdutos").setInnerHTML(strTable);
            document.getElementById("divSelecaoProdutos").setVisible(false);
            document.executeScript("document.getElementByid('divCertificados').style.height = '330px';");
            return;
        }
        
        document.getElementById("divSelecaoProdutos").setInnerHTML(strTable);
    }    

    public void visualizarHistoricoCampo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null){
            document.alert("Sessão expirada! Favor efetuar logon novamente!");
            return;
        }
        SolicitacaoServicoQuestionarioDTO contrQuestBean = (SolicitacaoServicoQuestionarioDTO) document.getBean();
        
        String strTable = "<table width='100%' border='1'>";
        
        strTable += "<tr><td><b>Data do Atendimento</b></td><td><b>Conteúdo</b></td></tr>";
        
        String strItem = "";
        
        QuestaoQuestionarioService questaoQuestionarioService = (QuestaoQuestionarioService) ServiceLocator.getInstance().getService(QuestaoQuestionarioService.class, null);
        QuestionarioService questionarioService = (QuestionarioService) ServiceLocator.getInstance().getService(QuestionarioService.class, null);
        RespostaItemQuestionarioService respostaItemQuestionarioService = (RespostaItemQuestionarioService) ServiceLocator.getInstance().getService(RespostaItemQuestionarioService.class, null);
        Collection col = questaoQuestionarioService.listByIdQuestaoAndContrato(contrQuestBean.getIdQuestaoVisHistorico(), contrQuestBean.getIdContratoVisHistorico());
        if (col != null){
        	for(Iterator it = col.iterator(); it.hasNext();){
        		QuestaoQuestionarioDTO questaoDto = (QuestaoQuestionarioDTO)it.next();
        		RespostaItemQuestionarioDTO resposta = new RespostaItemQuestionarioDTO();
        		resposta.setIdRespostaItemQuestionario(questaoDto.getIdRespostaItemQuestionario());
        		resposta = (RespostaItemQuestionarioDTO) respostaItemQuestionarioService.restore(resposta);
        		strItem = "";
        		if (questaoDto.getTipoQuestao().equalsIgnoreCase("T") || questaoDto.getTipoQuestao().equalsIgnoreCase("A")){ 
        			strItem += resposta.getRespostaTextual();
        		}
        		if (questaoDto.getTipoQuestao().equalsIgnoreCase("D")){ 
        			if (resposta.getRespostaData() != null){
        				strItem += UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, resposta.getRespostaData(), WebUtil.getLanguage(request));
        			}
        		}
        		if (questaoDto.getTipoQuestao().equalsIgnoreCase("H")){ 
        			if (resposta.getRespostaHora() != null){
        				strItem += UtilDatas.formatHoraStr(resposta.getRespostaHora());
        			}
        		}
        		if (questaoDto.getTipoQuestao().equalsIgnoreCase("N")){ 
        			if (resposta.getRespostaNumero() != null){
        				strItem += UtilFormatacao.formatDouble(resposta.getRespostaNumero(), 0);
        			}
        		}
        		if (questaoDto.getTipoQuestao().equalsIgnoreCase("V") || questaoDto.getTipoQuestao().equalsIgnoreCase("%")){
                    int qtdeDecimais = 0;
                    if (questaoDto.getDecimais()!=null){
                        qtdeDecimais = questaoDto.getDecimais().intValue();
                    }
                    if (resposta.getRespostaValor() != null){
                    	strItem += UtilFormatacao.formatDouble(resposta.getRespostaValor(), qtdeDecimais);
                    }        			
        		}
        		if (questaoDto.getTipoQuestao().equalsIgnoreCase("L")){
        			strItem += resposta.getRespostaTextual();
        		}
        		if (questaoDto.getTipoQuestao().equalsIgnoreCase("R") ||
        				questaoDto.getTipoQuestao().equalsIgnoreCase("C") ||
        				questaoDto.getTipoQuestao().equalsIgnoreCase("X")){ //Radio, checkbox e combobox
        			OpcaoRespostaQuestionarioService opcaoRespService = (OpcaoRespostaQuestionarioService) ServiceLocator.getInstance().getService(OpcaoRespostaQuestionarioService.class, null);
					Collection colOpcoesResposta = opcaoRespService.listByIdQuestaoQuestionario(questaoDto.getIdQuestaoQuestionario());
					questaoDto.setColOpcoesResposta(colOpcoesResposta);
        			
					Collection colAux = respostaItemQuestionarioService.getRespostasOpcoesByIdRespostaItemQuestionario(questaoDto.getIdRespostaItemQuestionario());
	                for(Iterator itOpcResp = colAux.iterator(); itOpcResp.hasNext();){
	                	RespostaItemQuestionarioOpcoesDTO opcRespDto =  (RespostaItemQuestionarioOpcoesDTO)itOpcResp.next();
	                    strItem += opcRespDto.getTitulo() + "";
	                }					
        		}
        		if (strItem == null || strItem.equalsIgnoreCase("null")){
        			strItem = "";
        		}
        		
        		strTable += "<tr><td>" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, questaoDto.getDataRegistro(), WebUtil.getLanguage(request)) + "</td><td>" + strItem + "</td></tr>";
        	}
        }
        
        strTable += "</table>";
        
        document.getElementById("divHistoricoCampoVisualizacao").setInnerHTML(strTable);
    }
    
    public void visualizarGraficoHistoricoCampo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

        SolicitacaoServicoQuestionarioDTO contrQuestBean = (SolicitacaoServicoQuestionarioDTO) document.getBean();
        
        JFreeChart chart;
		DefaultCategoryDataset dados = new DefaultCategoryDataset();
		Double objDouble;
		Double objDouble2;
		Double objDouble3;
		
        String cabQuestao = "";
        
        QuestaoQuestionarioService questaoQuestionarioService = (QuestaoQuestionarioService) ServiceLocator.getInstance().getService(QuestaoQuestionarioService.class, null);
        QuestionarioService questionarioService = (QuestionarioService) ServiceLocator.getInstance().getService(QuestionarioService.class, null);
        RespostaItemQuestionarioService respostaItemQuestionarioService = (RespostaItemQuestionarioService) ServiceLocator.getInstance().getService(RespostaItemQuestionarioService.class, null);
        Collection col = questaoQuestionarioService.listByIdQuestaoAndContratoOrderDataASC(contrQuestBean.getIdQuestaoVisHistorico(), contrQuestBean.getIdContratoVisHistorico());
        if (col != null){
        	for(Iterator it = col.iterator(); it.hasNext();){
        		QuestaoQuestionarioDTO questaoDto = (QuestaoQuestionarioDTO)it.next();
        		RespostaItemQuestionarioDTO resposta = new RespostaItemQuestionarioDTO();
        		resposta.setIdRespostaItemQuestionario(questaoDto.getIdRespostaItemQuestionario());
        		resposta = (RespostaItemQuestionarioDTO) respostaItemQuestionarioService.restore(resposta);
        		if (resposta == null){
        			continue;
        		}
        		try{
        			String m = questaoDto.getTituloQuestaoQuestionarioSemFmt();
        			if (m == null){
        				m = "";
        			}
        			cabQuestao = UtilHTML.decodeHTML(m);
        		}catch (Exception e) {
					e.printStackTrace();
					cabQuestao = "--";
				}
        		if (cabQuestao.equalsIgnoreCase("")){
        			cabQuestao = "--";
        		}
        		
    			objDouble = null;
    			objDouble2 = null;
    			objDouble3 = null;
    			try{
	    			if (questaoDto.getTipoQuestao().equalsIgnoreCase("N")){
	    				objDouble = resposta.getRespostaNumero();
	    				if (objDouble == null)
	    					objDouble = new Double(0);
	    				dados.addValue(objDouble.doubleValue(), "Valor", UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, questaoDto.getDataRegistro(), WebUtil.getLanguage(request)));
	    			}
	    			if (questaoDto.getTipoQuestao().equalsIgnoreCase("V")){
	    				objDouble = resposta.getRespostaValor();
	    				if (objDouble == null)
	    					objDouble = new Double(0);
	    				dados.addValue(objDouble.doubleValue(), "Valor", UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, questaoDto.getDataRegistro(), WebUtil.getLanguage(request)));
	    			}
	    			if (questaoDto.getTipoQuestao().equalsIgnoreCase("%")){
	    				objDouble = resposta.getRespostaPercentual();
	    				if (objDouble == null)
	    					objDouble = new Double(0);
	    				dados.addValue(objDouble.doubleValue(), "Valor", UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, questaoDto.getDataRegistro(), WebUtil.getLanguage(request)));
	    			}
	    			if (questaoDto.getTipoQuestao().equalsIgnoreCase("*")){
	    				objDouble = resposta.getRespostaPercentual();
	    				if (objDouble == null)
	    					objDouble = new Double(0);
	    				dados.addValue(objDouble.doubleValue(), "%", UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, questaoDto.getDataRegistro(), WebUtil.getLanguage(request)));
	
	    				objDouble = resposta.getRespostaValor();
	    				if (objDouble == null)
	    					objDouble = new Double(0);
	    				dados.addValue(objDouble.doubleValue(), "Valor", UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, questaoDto.getDataRegistro(), WebUtil.getLanguage(request)));
	    			}
	    			if (questaoDto.getTipoQuestao().equalsIgnoreCase("1")){
	    				objDouble = resposta.getRespostaNumero();
	    				if (objDouble == null)
	    					objDouble = new Double(0);
	    				dados.addValue(objDouble.doubleValue(), "Valor 1", UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, questaoDto.getDataRegistro(), WebUtil.getLanguage(request)));
	
	    				objDouble = resposta.getRespostaNumero2();
	    				if (objDouble == null)
	    					objDouble = new Double(0);
	    				dados.addValue(objDouble.doubleValue(), "Valor 2", UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, questaoDto.getDataRegistro(), WebUtil.getLanguage(request)));
	    			}
	    			if (questaoDto.getTipoQuestao().equalsIgnoreCase("2")){
	    				objDouble = resposta.getRespostaValor();
	    				if (objDouble == null)
	    					objDouble = new Double(0);
	    				dados.addValue(objDouble.doubleValue(), "Valor 1", UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, questaoDto.getDataRegistro(), WebUtil.getLanguage(request)));
	
	    				objDouble = resposta.getRespostaValor2();
	    				if (objDouble == null)
	    					objDouble = new Double(0);
	    				dados.addValue(objDouble.doubleValue(), "Valor 2", UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, questaoDto.getDataRegistro(), WebUtil.getLanguage(request)));
	    			}
    			}catch (Exception e) {
					e.printStackTrace();
				}
        	}
        }
        
        cabQuestao = cabQuestao.replaceAll("\n", "");
        cabQuestao = cabQuestao.replaceAll("\r", "");
        boolean is3D = false;
		if (is3D){
			chart = ChartFactory.createLineChart3D(cabQuestao, null, null, dados, PlotOrientation.VERTICAL, true, true, false);
		}else{
			chart = ChartFactory.createLineChart(cabQuestao, null, null, dados, PlotOrientation.VERTICAL, true, true, false);
		}
	
		// Setando o valor maximo para nunca passar de 100, ja q se trata de
		// porcentagem
		CategoryPlot plot = chart.getCategoryPlot();
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setUpperMargin(0.09);

		// Formatando cores de fundo, fonte do titulo, etc...
		chart.setBackgroundPaint(COR_FUNDO); // Cor do fundo do grafico
		chart.getTitle().setPaint(COR_TITULO); // Cor do titulo
		chart.getTitle().setFont(new java.awt.Font("arial", Font.BOLD, 12)); // Fonte do
																																// titulo
		chart.getPlot().setBackgroundPaint(new Color(204, 255, 204));// Cor de
																																	// fundo da
																																	// plot (area
																																	// do grafico)
		chart.setBorderVisible(false); // Visibilidade da borda do grafico

		// Marcador de Mídia de Resolubilidade
		// IntervalMarker target = new IntervalMarker(y - 0.3, y + 0.3);// A
		// principio, a mídia será o TOTAL-MF
		/*
		 * target.setLabel(" Resolubilidade Mídia"); target.setLabelFont(new
		 * Font("arial", Font.BOLD, 12)); target.setLabelPaint(Color.RED);
		 * target.setLabelAnchor(RectangleAnchor.CENTER);
		 * target.setLabelTextAnchor(TextAnchor.BOTTOM_CENTER);
		 */
		// target.setPaint(Color.RED); // Cor da linha marcadora
		// plot.addRangeMarker(target, Layer.FOREGROUND);
		
		String caminhoRelativo = "";
		String caminho = "";
		/*try {
			File arquivo = new File(CITCorporeUtil.caminho_real_app + "/tempReceitas");
			if (!arquivo.exists()) {
				arquivo.mkdirs();
			}
			File arquivoVer = new File(CITCorporeUtil.caminho_real_app + "/tempReceitas/" + usuario.getIdUsuario());
			if (!arquivoVer.exists()) {
				arquivoVer.mkdirs();
			}		
			String hora = UtilDatas.formatHoraFormatadaStr(UtilDatas.getHoraAtual());
			hora = UtilStrings.apenasNumeros(hora);
			caminho = CITCorporeUtil.caminho_real_app + "/tempReceitas/" + usuario.getIdUsuario() + "/visHistField_" + UtilStrings.generateNomeBusca(UtilStrings.removeCaracteresEspeciais(cabQuestao)) + hora + ".png";
			caminhoRelativo = Constantes.getValue("DIRETORIO_TEMPORARIO_RECEITAS_RELATIVO") + "/" + usuario.getIdUsuario() + "/visHistField_" + UtilStrings.generateNomeBusca(UtilStrings.removeCaracteresEspeciais(cabQuestao)) + hora + ".png";
			arquivo = new File(caminho);
			if (arquivo.exists()) {
				arquivo.delete();
			}
			ChartUtilities.saveChartAsPNG(arquivo, chart, 
					800, 
					450);

		} catch (Exception e) {
			e.printStackTrace();
		}*/	
		
        document.getElementById("divHistoricoCampoVisualizacao").setInnerHTML("<img src=\"" + caminhoRelativo + "\"/>");
    }    
    
	private boolean isInCollection(Integer idValor, Collection colVerificar){
		if (colVerificar == null) return false;
		for(Iterator it = colVerificar.iterator(); it.hasNext();){
			RespostaItemQuestionarioOpcoesDTO respItemQuestDto = (RespostaItemQuestionarioOpcoesDTO)it.next();
			if (respItemQuestDto.getIdOpcaoRespostaQuestionario().intValue() == idValor.intValue()){
				return true;
			}
		}
		return false;
	}    
}
