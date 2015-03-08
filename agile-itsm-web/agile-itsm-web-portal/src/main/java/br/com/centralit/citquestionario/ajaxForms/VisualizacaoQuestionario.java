package br.com.centralit.citquestionario.ajaxForms;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.GaleriaImagensDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.negocio.ContratoQuestionariosService;
import br.com.centralit.citcorpore.negocio.GaleriaImagensService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.util.CriptoUtils;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.negocio.ControleGEDService;
import br.com.centralit.citquestionario.bean.ArquivoMultimidiaDTO;
import br.com.centralit.citquestionario.bean.GrupoQuestionarioDTO;
import br.com.centralit.citquestionario.bean.LinhaSpoolQuestionario;
import br.com.centralit.citquestionario.bean.ListagemDTO;
import br.com.centralit.citquestionario.bean.ListagemLinhaDTO;
import br.com.centralit.citquestionario.bean.OpcaoRespostaQuestionarioDTO;
import br.com.centralit.citquestionario.bean.QuestaoQuestionarioDTO;
import br.com.centralit.citquestionario.bean.QuestionarioDTO;
import br.com.centralit.citquestionario.bean.RespostaItemQuestionarioAnexosDTO;
import br.com.centralit.citquestionario.bean.RespostaItemQuestionarioDTO;
import br.com.centralit.citquestionario.bean.RespostaItemQuestionarioOpcoesDTO;
import br.com.centralit.citquestionario.bean.ValidacaoGeralQuestionarioDTO;
import br.com.centralit.citquestionario.negocio.GrupoQuestionarioService;
import br.com.centralit.citquestionario.negocio.ListagemService;
import br.com.centralit.citquestionario.negocio.OpcaoRespostaQuestionarioService;
import br.com.centralit.citquestionario.negocio.QuestaoQuestionarioService;
import br.com.centralit.citquestionario.negocio.QuestionarioService;
import br.com.centralit.citquestionario.negocio.RespostaItemQuestionarioService;
import br.com.centralit.citquestionario.util.ListagemConfig;
import br.com.centralit.citquestionario.util.RenderDynamicForm;
import br.com.centralit.citquestionario.util.WebUtilQuestionario;
import br.com.citframework.dto.Usuario;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilFormatacao;
import br.com.citframework.util.UtilHTML;
import br.com.citframework.util.UtilStrings;

public class VisualizacaoQuestionario extends AjaxFormAction {	
    private String QUESTIONARIO_COR_FUNDO_GRUPO = "#F1F1F1";
    private String QUESTIONARIO_SIZE_QUESTAO = "50px";
    
	public Class getBeanClass() {
		return QuestionarioDTO.class;
	}

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		QuestionarioDTO questionario = (QuestionarioDTO) document.getBean();
		Usuario user = WebUtilQuestionario.getUsuario(request);
		if (user == null){
			document.alert("O usuário não está logado! Favor logar no sistema!"); 
			return;
		}	
		Integer idProfissinal = user.getIdProfissional();
		
		String QUESTIONARIO_CELLPADDING_TABELA_QUESTOES = "1";
		String QUESTIONARIO_CELLSPACING_TABELA_QUESTOES = "1";
		String QUESTIONARIO_WIDTH_COLUNA_AVANCO = "10%";
		

		Collection colQuestoesComSigla = new ArrayList();
		
		LinhaSpoolQuestionario linhaSpoolQuestionario; 
		Collection colLinhas = new ArrayList();
		
		boolean somenteLeitura = false;
		String modo = request.getParameter("modo");
		if ("somenteleitura".equalsIgnoreCase(modo)){
			somenteLeitura = true;
		}
		
		Integer idSolicitacaoServico = questionario.getIdSolicitacaoServico();
		Integer idItem = null;
		
		String detalhaHistorico = "S";
		if (questionario.getDetalhaHistorico() != null && !questionario.getDetalhaHistorico().equalsIgnoreCase("")){
			detalhaHistorico = questionario.getDetalhaHistorico();
		}
		String mostrarAbreFecha = "S";
		if (questionario.getMostrarAbreFecha() != null && !questionario.getMostrarAbreFecha().equalsIgnoreCase("")){
			mostrarAbreFecha = questionario.getMostrarAbreFecha();
		}
		String subQuestionario = "N";
		if (questionario.getSubQuestionario() != null && !questionario.getSubQuestionario().equalsIgnoreCase("")){
			subQuestionario = questionario.getSubQuestionario();
		}
		if (questionario.getIdItem() != null && questionario.getIdItem().intValue() > 0){
			idItem = questionario.getIdItem();
		}
		
		request.setAttribute("idSolicitacaoServicoVisualizacaoHistCampos", "" + idSolicitacaoServico);
		
		//String avanco = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
		String avanco = "&nbsp;";
		
		ValidacaoGeralQuestionarioDTO validaGeral = new ValidacaoGeralQuestionarioDTO();

		//VERIFICAR SE REALMENTE TEM NECESSIDADE DAS LINHAS ABAIXO!!!!!!!!!!!!!!!!!!!!!!!!!
		//linhaSpoolQuestionario = new LinhaSpoolQuestionario("<table border = '0' width='100%'>");
		//colLinhas.add(linhaSpoolQuestionario);
		
		Integer idProfissional = questionario.getIdProfissional();
		Date dataQuestionario = questionario.getDataQuestionario();
		
		QuestionarioService questionarioService = (QuestionarioService) ServiceLocator.getInstance().getService(QuestionarioService.class, null);
		QuestaoQuestionarioService questaoService = (QuestaoQuestionarioService) ServiceLocator.getInstance().getService(QuestaoQuestionarioService.class, null);
		GrupoQuestionarioService grupoQuestionarioService = (GrupoQuestionarioService) ServiceLocator.getInstance().getService(GrupoQuestionarioService.class, null);
		ContratoQuestionariosService contratoQuestionariosService = (ContratoQuestionariosService) ServiceLocator.getInstance().getService(ContratoQuestionariosService.class, null);
		questionario.setModo(QuestionarioDTO.MODO_VISUALIZACAO);
		Integer idIdentificadorResposta = questionario.getIdIdentificadorResposta();
		if(questionario.getIdQuestionario() != null || questionario.getIdQuestionarioOrigem() != null){
			questionario = (QuestionarioDTO) questionarioService.restore(questionario);
		}
        
        if (questionario == null){
        	document.alert("Formulário não encontrado!"); 
        	return;
        }

        questionario.setIdIdentificadorResposta(idIdentificadorResposta);
        questionario.setIdItem(idItem);
        questionario.setDetalhaHistorico(detalhaHistorico);

        SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
        SolicitacaoServicoDTO solicitacaoServicoDto = new SolicitacaoServicoDTO();
        if (idSolicitacaoServico != null){
	        solicitacaoServicoDto.setIdSolicitacaoServico(idSolicitacaoServico);
	        solicitacaoServicoDto = (SolicitacaoServicoDTO) solicitacaoServicoService.restore(solicitacaoServicoDto);
        }else{
        	solicitacaoServicoDto = null;
        }
        
        HTMLSelect idQuestoesCalculadas = document.getSelectById("idQuestoesCalculadas");
        idQuestoesCalculadas.removeAllOptions();   
        
        String bufferAposLoad = "";
        if(questionario != null && questionario.getColGrupos() != null && questionario.getColGrupos().size() >0){
			for(Iterator it = questionario.getColGrupos().iterator(); it.hasNext();){
				GrupoQuestionarioDTO grupoQuestDto = (GrupoQuestionarioDTO)it.next();
				
				if (mostrarAbreFecha.equalsIgnoreCase("S")){
					if (grupoQuestDto.getNomeGrupoQuestionario() == null 
							|| grupoQuestDto.getNomeGrupoQuestionario().trim().equalsIgnoreCase("")
							|| grupoQuestDto.getNomeGrupoQuestionario().trim().equalsIgnoreCase("&nbsp;")){
						linhaSpoolQuestionario = new LinhaSpoolQuestionario("<tr><td colspan='20' width='100%'><b>" + grupoQuestDto.getNomeGrupoQuestionario() + "</b></td></tr>");
					}else{
						linhaSpoolQuestionario = new LinhaSpoolQuestionario("<tr><td width='30%'><img id='linha_div' src='"+ Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/linha_divisao.png' border='0' heigth='12px'><br/><br/></td></tr><tr><td bgcolor='" + QUESTIONARIO_COR_FUNDO_GRUPO + "' colspan='20' width='100%'><img id='imgQUEST_GRP_" + grupoQuestDto.getIdGrupoQuestionario() + "' src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/menos1.png' border='0' onclick='abreMaisMenosGrp(this, \"divQUEST_GRP_" + grupoQuestDto.getIdGrupoQuestionario() + "\")' style='cursor:pointer;  font-size: 10pt !important;  font-family: arial, helvetica !important;'/>&nbsp;<b>" + grupoQuestDto.getNomeGrupoQuestionario() + "</b><br/><br/></td></tr>");

					}
				}else{
					if (grupoQuestDto.getNomeGrupoQuestionario() == null 
							|| grupoQuestDto.getNomeGrupoQuestionario().trim().equalsIgnoreCase("")
							|| grupoQuestDto.getNomeGrupoQuestionario().trim().equalsIgnoreCase("&nbsp;")){
						linhaSpoolQuestionario = new LinhaSpoolQuestionario("<tr><td colspan='20' width='100%'><b>" + grupoQuestDto.getNomeGrupoQuestionario() + "</b></td></tr>");
					}else{
						linhaSpoolQuestionario = new LinhaSpoolQuestionario("<tr><td bgcolor='" + QUESTIONARIO_COR_FUNDO_GRUPO + "' colspan='20' width='100%'><b>" + grupoQuestDto.getNomeGrupoQuestionario() + "</b></td></tr>");
					}
				}
				linhaSpoolQuestionario.setGenerateTR(false);
				colLinhas.add(linhaSpoolQuestionario);
				if (mostrarAbreFecha.equalsIgnoreCase("S")){
					if (grupoQuestDto.getNomeGrupoQuestionario() == null 
							|| grupoQuestDto.getNomeGrupoQuestionario().trim().equalsIgnoreCase("")
							|| grupoQuestDto.getNomeGrupoQuestionario().trim().equalsIgnoreCase("&nbsp;")){
						linhaSpoolQuestionario = new LinhaSpoolQuestionario("<tr><td>");
					}else{
						linhaSpoolQuestionario = new LinhaSpoolQuestionario("<tr><td><div id='divQUEST_GRP_" + grupoQuestDto.getIdGrupoQuestionario() + "' class='tableLess'>");
					}
				}else{
					linhaSpoolQuestionario = new LinhaSpoolQuestionario("<tr><td>");
				}
				linhaSpoolQuestionario.setGenerateTR(false);
				colLinhas.add(linhaSpoolQuestionario);
				linhaSpoolQuestionario = new LinhaSpoolQuestionario("<table border='0' width='100%' style='border-collapse: collapse;' cellpadding='" + QUESTIONARIO_CELLPADDING_TABELA_QUESTOES + "' cellspacing='" + QUESTIONARIO_CELLSPACING_TABELA_QUESTOES + "'>");
				linhaSpoolQuestionario.setGenerateTR(false);
				colLinhas.add(linhaSpoolQuestionario);
				if (grupoQuestDto.getColQuestoes() != null){
					for (Iterator itQuest = grupoQuestDto.getColQuestoes().iterator(); itQuest.hasNext();){
						QuestaoQuestionarioDTO questaoDto = (QuestaoQuestionarioDTO)itQuest.next();
						
						//document.alert(questaoDto.getTipo());
						if (questaoDto.getSigla() != null && !questaoDto.getSigla().trim().equals("")) 
						    colQuestoesComSigla.add(questaoDto);
						
						questaoDto.setIdSolicitacaoServico(idSolicitacaoServico);
						if (questaoDto.getTipo().equalsIgnoreCase("T") || questaoDto.getTipo().equalsIgnoreCase("Q") || questaoDto.getTipo().equalsIgnoreCase("C") || questaoDto.getTipo().equalsIgnoreCase("P")){   // Texto Fixo, Questão, Compartilhada ou HTML
					        linhaSpoolQuestionario = new LinhaSpoolQuestionario(montaLinhaQuestao(questaoDto, null, null, request, validaGeral, questionario, somenteLeitura, subQuestionario));
						    colLinhas.add(linhaSpoolQuestionario);
					        if (questaoDto.getCalculada() != null && questaoDto.getCalculada().equalsIgnoreCase("S")) {
					            idQuestoesCalculadas.addOption(questaoDto.getIdQuestaoQuestionario().toString(), questaoDto.getIdQuestaoQuestionario().toString());
					        }
						}else if (questaoDto.getTipo().equalsIgnoreCase("D")){  // Formulario dinamico
							String bufferQuestao = "<tr><td width='" + QUESTIONARIO_WIDTH_COLUNA_AVANCO + "'>" + avanco + "</td><td colspan='20'>";
							bufferQuestao += RenderDynamicForm.render(questaoDto.getAbaResultSubForm(), "XML", idSolicitacaoServico, idProfissinal);
							bufferQuestao += "</td></tr>";
							linhaSpoolQuestionario = new LinhaSpoolQuestionario(bufferQuestao);
							linhaSpoolQuestionario.setGenerateTR(false);
							colLinhas.add(linhaSpoolQuestionario);						
						}else if (questaoDto.getTipo().equalsIgnoreCase("H")){  // Historico de Sessoes
							String bufferQuestao = "<tr><td width='" + QUESTIONARIO_WIDTH_COLUNA_AVANCO + "'>" + avanco + "</td><td colspan='4' align='left' style='text-align:left'>";
							bufferQuestao += "<input type='hidden' name='campoDyn_" + questaoDto.getIdQuestaoQuestionario() + "' value=''/>";
							bufferQuestao += "<input type='button' style='BACKGROUND-COLOR: yellow' name='btnDyn_" + questaoDto.getIdQuestaoQuestionario() + "' onclick='parent.showHistoricoAba(\""+questaoDto.getAbaResultSubForm()+"\");' value='Histórico'/>";
							bufferQuestao += "</td></tr>";
							linhaSpoolQuestionario = new LinhaSpoolQuestionario(bufferQuestao);
							colLinhas.add(linhaSpoolQuestionario);
						/*}else if (questaoDto.getTipo().equalsIgnoreCase("I")){  // Informações Históricas
							ProntuarioEletronicoConfigService peCfgService = (ProntuarioEletronicoConfigService) ServiceLocator.getInstance().getService(ProntuarioEletronicoConfigService.class, null);
							ProntuarioEletronicoConfigDTO peCfgDto = new ProntuarioEletronicoConfigDTO();
							peCfgDto.setNome(questaoDto.getAbaResultSubForm());
							List lst = (List) peCfgService.findByNome(questaoDto.getAbaResultSubForm());
							if (lst != null && lst.size() > 0){
								peCfgDto = (ProntuarioEletronicoConfigDTO) lst.get(0);
								if (peCfgDto != null){
									String bufferQuestao = "<tr><td width='" + QUESTIONARIO_WIDTH_COLUNA_AVANCO + "'>" + avanco + "</td><td colspan='4'>";
									
									bufferQuestao += "<input type='hidden' name='campoDyn_" + questaoDto.getIdQuestaoQuestionario() + "' value=''/>";
									bufferQuestao += "<input type='button' name='btnDyn_" + questaoDto.getIdQuestaoQuestionario() + "' onclick='parent.funcaoChamaInformacoesHistoricas(\""+questaoDto.getAbaResultSubForm()+"\", \"" + peCfgDto.getIdProntuarioEletronicoConfig() + "\");' value='Informações Históricas'/>";
									bufferQuestao += "</td></tr>";
									linhaSpoolQuestionario = new LinhaSpoolQuestionario(bufferQuestao);
									colLinhas.add(linhaSpoolQuestionario);
								}
							}*/
						}else if (questaoDto.getTipo().equalsIgnoreCase("F")){  // SubFormulario
							String bufferQuestao = "";
							try{
								QuestionarioDTO questDto = new QuestionarioDTO();
								questDto.setIdQuestionario(questaoDto.getIdSubQuestionario());
								questDto = (QuestionarioDTO) questionarioService.restore(questDto);
								
								bufferQuestao += "<tr><td width='" + QUESTIONARIO_WIDTH_COLUNA_AVANCO + "'>" + avanco + "</td><td colspan='4'>";
								bufferQuestao += "<input type='button' name='btnDyn_" + questaoDto.getIdQuestaoQuestionario() + "' onclick='parent.chamaHistoricoQuestionario("+questaoDto.getIdSubQuestionario()+",\"" + questaoDto.getAbaResultSubForm() + "\");' value='" + questDto.getNomeQuestionario() + "'/>";
								bufferQuestao += "</td></tr>";
								bufferQuestao += "<tr><td colspan='4'>";
								RespostaItemQuestionarioDTO resposta = questaoDto.obtemRespostaItemDto(null);
								String texto = "";
								if (resposta != null){
									if (resposta.getRespostaTextual() != null){
										texto = resposta.getRespostaTextual();
									}
								}
								bufferQuestao += "</td></tr>";
							}catch (Exception e) {
								e.printStackTrace();
								bufferQuestao += "<tr><td colspan='20'><b>OCORREU ERRO AO RECUPERAR DOS DO FORMULÁRIO ASSOCIADO!</b></td></tr>";
							}
							linhaSpoolQuestionario = new LinhaSpoolQuestionario(bufferQuestao);
							colLinhas.add(linhaSpoolQuestionario);
						}else if (questaoDto.getTipo().equalsIgnoreCase("M")){  // Matriz
						    int altura = questaoDto.getQtdeLinhas() * 75;
						    if (altura > 300) {
						        altura = 300;
						    }
						    String strAux = "";
						    if (questaoDto.getIdSolicitacaoServico() != null){
						    	//strAux = "<a href='#ancoraVisualizaHistoricoCampo'><img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/limparPeq.gif' border='0' style='cursor:pointer' title='Clique aqui para obter o histórico deste campo' onclick='chamaTelaHistoricoQuestMatTabela(\"" + questaoDto.getIdQuestaoQuestionario() + "\", \"" + questaoDto.getIdQuestaoOrigem() + "\")'></a> ";
						    }
						    String bufferQuestao = "<tr><td width='" + QUESTIONARIO_WIDTH_COLUNA_AVANCO + "'>" + avanco + "</td><td colspan='20'><fieldset><legend>" + strAux + montaTituloQuestao(questaoDto, request) + "</legend>";
						    //bufferQuestao += "<div id='divQuestoesMatriz_" + questaoDto.getIdQuestaoQuestionario() + "' style='width: 720px; height: "+altura+"px; overflow: auto;'>";
						    bufferQuestao += "<div id='divQuestoesMatriz_" + questaoDto.getIdQuestaoQuestionario() + "' style='width: 100%; height: 100%; overflow: auto;'>";
						    bufferQuestao += "<table id='tblQuestoesMatriz_" + questaoDto.getIdQuestaoQuestionario() + "' cellpadding='0' cellspacing='0' width='100%' height='100%' border = '1'>";
				            if (questaoDto.getCabecalhoColunas().equalsIgnoreCase("S")){
				                bufferQuestao += "<tr><td></td>";
				                for(Iterator itCol = questaoDto.getColCabecalhosColuna().iterator(); itCol.hasNext();){
				                    QuestaoQuestionarioDTO questaoAgrup = (QuestaoQuestionarioDTO)itCol.next();
	                                bufferQuestao += "<td bgcolor='" + QUESTIONARIO_COR_FUNDO_GRUPO + "'>"+questaoAgrup.getTituloQuestaoQuestionarioSemFmt()+"</td>";         
				                }
				                bufferQuestao += "</tr>";
				            }
	
				            Iterator itLin = questaoDto.getColCabecalhosLinha().iterator();
	                        Iterator itCel = questaoDto.getColQuestoesAgrupadas().iterator();			            
				            for(Integer linha = 1; linha <= questaoDto.getQtdeLinhas(); linha++){
		                        bufferQuestao += "<tr><td></td>";			                
				                if (questaoDto.getCabecalhoLinhas().equalsIgnoreCase("S")){
				                	try{
				                		QuestaoQuestionarioDTO questaoAgrupada = (QuestaoQuestionarioDTO) itLin.next();
				                		bufferQuestao += "<td bgcolor='" + QUESTIONARIO_COR_FUNDO_GRUPO + "'>"+questaoAgrupada.getTituloQuestaoQuestionarioSemFmt()+"</td>";
				                	}catch (Exception e) {
										e.printStackTrace();
									}
				                }
				                for(Integer coluna = 1; coluna <= questaoDto.getQtdeColunas(); coluna++){ 
				                	try{
			                            QuestaoQuestionarioDTO questaoAgrupada = (QuestaoQuestionarioDTO) itCel.next();
			                            bufferQuestao += "<td><table border = '0' cellpadding='" + QUESTIONARIO_CELLPADDING_TABELA_QUESTOES + "' cellspacing='" + QUESTIONARIO_CELLSPACING_TABELA_QUESTOES + "'>";
		                                bufferQuestao += montaLinhaQuestao(questaoAgrupada, questaoDto, null, request, validaGeral, questionario, somenteLeitura, subQuestionario);
			                            bufferQuestao += "</table></td>";
				                	}catch (Exception e) {
				                		bufferQuestao += "<td></td>";
									}
				                }
	                            bufferQuestao += "</tr>";			                
				            }
				            bufferQuestao += "</table></div></fieldset></td></tr>";
				            linhaSpoolQuestionario = new LinhaSpoolQuestionario(bufferQuestao);
				            colLinhas.add(linhaSpoolQuestionario);
						}else if (questaoDto.getTipo().equalsIgnoreCase("L")){  // Tabela
							String strAux = "";
						    if (questaoDto.getIdSolicitacaoServico() != null){
						    	//strAux = "<a href='#ancoraVisualizaHistoricoCampo'><img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/limparPeq.gif' border='0' style='cursor:pointer' title='Clique aqui para obter o histórico deste campo' onclick='chamaTelaHistoricoQuestMatTabela(\"" + questaoDto.getIdQuestaoQuestionario() + "\", \"" + questaoDto.getIdQuestaoOrigem() + "\")'></a> ";
						    }						
	                        String bufferQuestao = "<tr><td width='" + QUESTIONARIO_WIDTH_COLUNA_AVANCO + "'>" + avanco + "</td><td colspan='20'><fieldset><legend>" + strAux + montaTituloQuestao(questaoDto, request) + "</legend>";
	                        //bufferQuestao += "<table style='width: 715px;' border = '0'>";
	                        bufferQuestao += "<table style='width: 100%;' border = '0'>";
	                        bufferQuestao += "<td align='right'><input type='button' name='btnAddResposta' value='Adicionar' onclick='adicionarRespostaTabela("+questaoDto.getIdQuestaoQuestionario()+")'/></td>";
	                        bufferQuestao += "</table>";
	                        bufferQuestao += "<div style='display:none;'><table id='tblQuestoesTabela_" + questaoDto.getIdQuestaoQuestionario() + "' cellpadding='" + QUESTIONARIO_CELLPADDING_TABELA_QUESTOES + "' cellspacing='" + QUESTIONARIO_CELLSPACING_TABELA_QUESTOES + "'>";
	                        for (Iterator itCel = questaoDto.getColQuestoesAgrupadas().iterator(); itCel.hasNext();){
	                            QuestaoQuestionarioDTO questaoAgrupada = (QuestaoQuestionarioDTO) itCel.next();
	                            bufferQuestao += "<td><table>";
	                            bufferQuestao += montaLinhaQuestao(questaoAgrupada, questaoDto, 0, request, validaGeral, questionario, somenteLeitura, subQuestionario);
	                            bufferQuestao += "</table></td>";
	                        }    
	                        bufferQuestao += "</table></div>";
	                        //bufferQuestao += "<div style='width: 720px; height: 100%; overflow: auto;'>";
	                        bufferQuestao += "<div style='width: 100%; height: 100%; overflow: auto;'>";
	                        bufferQuestao += "<table id='tblRespostasTabela_" + questaoDto.getIdQuestaoQuestionario() + "' cellpadding='0' cellspacing='0' width='100%' border = '1'>";
	                        bufferQuestao += "<td bgcolor='" + QUESTIONARIO_COR_FUNDO_GRUPO + "'>&nbsp;</td>";                        
	                        for (Iterator itCel = questaoDto.getColQuestoesAgrupadas().iterator(); itCel.hasNext();){
	                            QuestaoQuestionarioDTO questaoAgrupada = (QuestaoQuestionarioDTO) itCel.next();
	                            bufferQuestao += "<td bgcolor='" + QUESTIONARIO_COR_FUNDO_GRUPO + "'>"+montaTituloQuestao(questaoAgrupada, request)+"</td>";
	                        }
	                        int ultimoSequencial = 0;                        
	                        if (questaoDto.getColRespostas() != null && questaoDto.getColRespostas().size() > 0) {
	                            int sequencial = 0;
	                            for (Iterator itResp = questaoDto.getColRespostas().iterator(); itResp.hasNext();){
	                                RespostaItemQuestionarioDTO resp = (RespostaItemQuestionarioDTO) itResp.next();
	                                if (resp.getSequencialResposta() != null && resp.getSequencialResposta().intValue() != sequencial) { 
	                                    if (sequencial > 0){
	                                        bufferQuestao += "</tr>";
	                                    } 
	                                    sequencial = resp.getSequencialResposta();
	                                    if (sequencial > ultimoSequencial){
	                                        ultimoSequencial = sequencial;
	                                    }                                    
	                                    bufferQuestao += "<tr><td><img src='"+br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")+br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") + "/produtos/citsaude/imagens/btnExcluirRegistro.gif' title='Clique aqui para excluir esta linha' border='0' onclick='excluirRespostaTabela("+questaoDto.getIdQuestaoQuestionario()+",this)'/></td>";
	                                    for (Iterator itCel = questaoDto.getColQuestoesAgrupadas().iterator(); itCel.hasNext();){
	                                        QuestaoQuestionarioDTO questaoTabela = (QuestaoQuestionarioDTO) itCel.next();
	                                        bufferQuestao += "<td><table border = '0'>";
	                                        bufferQuestao += montaLinhaQuestao(questaoTabela, questaoDto, sequencial,  request, validaGeral, questionario, somenteLeitura, subQuestionario);
	                                        bufferQuestao += "</table></td>";
	                                    }
	                                } 
	                            }
	                            bufferQuestao += "</tr>";
	                        }    
	                        bufferQuestao += "</table>";
	                        bufferQuestao += "<input type='hidden' name='ultimoSequencial_" + questaoDto.getIdQuestaoQuestionario() + "' value='" + ultimoSequencial + "'/>";
	                        bufferQuestao += "</div></fieldset></td>";
	                        linhaSpoolQuestionario = new LinhaSpoolQuestionario(bufferQuestao);
	                        colLinhas.add(linhaSpoolQuestionario);
						}
					}
				}
				linhaSpoolQuestionario = new LinhaSpoolQuestionario("</table>");
				linhaSpoolQuestionario.setGenerateTR(false);
				colLinhas.add(linhaSpoolQuestionario);	
				if (mostrarAbreFecha.equalsIgnoreCase("S")){
					if (grupoQuestDto.getNomeGrupoQuestionario() == null 
							|| grupoQuestDto.getNomeGrupoQuestionario().trim().equalsIgnoreCase("")
							|| grupoQuestDto.getNomeGrupoQuestionario().trim().equalsIgnoreCase("&nbsp;")){
						linhaSpoolQuestionario = new LinhaSpoolQuestionario("</td></tr>");
					}else{
						linhaSpoolQuestionario = new LinhaSpoolQuestionario("</td></tr></div>");
					}
				}else{
					linhaSpoolQuestionario = new LinhaSpoolQuestionario("</td></tr>");
				}
				linhaSpoolQuestionario.setGenerateTR(false);
				colLinhas.add(linhaSpoolQuestionario);			
			}
        }
		linhaSpoolQuestionario = new LinhaSpoolQuestionario("");
		colLinhas.add(linhaSpoolQuestionario);		
		
		request.setAttribute("strValidaFaixaValoresGeral", validaGeral.getValidacao());
		
		request.setAttribute("linhasQuestionario", colLinhas);
		request.setAttribute("bufferAposLoad", bufferAposLoad);
		request.setAttribute("colQuestoesComSigla", colQuestoesComSigla);

        request.setAttribute("javaScript", questionario.getJavaScript());
		
		document.executeScript("document.formQuestionario.idQuestionario.value = '" + questionario.getIdQuestionario() + "'");		
		
		String idServicoContrato = request.getParameter("idServicoContrato");
		
		String idServico = request.getParameter("idServico");
		
		String obrigatorio = request.getParameter("respostaObrigatoria");
		
		if (idServicoContrato != null && idServico != null){
		
			document.executeScript("document.formQuestionario.idServicoContrato.value = '" + idServicoContrato + "'");
		
			document.executeScript("document.formQuestionario.idServico.value = '" + idServico + "'");

			document.executeScript("document.formQuestionario.obrigatorio.value = '" + obrigatorio + "'");
			
		}
		
    }	

    private String montaLinhaQuestao(QuestaoQuestionarioDTO questaoDto, QuestaoQuestionarioDTO questaoAgrupadoraDto, Integer sequencialResposta, HttpServletRequest request, ValidacaoGeralQuestionarioDTO validaGeral, QuestionarioDTO questionario, boolean somenteLeitura, String subQuestionario) throws Exception {
		String PRONTUARIO_FORMA_EDICAO = "P";
		String QUESTIONARIO_WIDTH_COLUNA_TITULO_QUESTAO = "30%";
		String QUESTIONARIO_WIDTH_COLUNA_AVANCO = "1%";
		String QUESTIONARIO_QUEBRA_LINHA_FINAL_TIT_QUESTAO = "S";
		
    	Usuario user = WebUtilQuestionario.getUsuario(request);
        String linhaSpoolQuestionario; 
        String avanco = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
        String bufferQuestao = "";
        
        if (questaoDto.getTextoInicial() != null && !questaoDto.getTextoInicial().trim().equalsIgnoreCase("")){
        	bufferQuestao = "<tr><td width='" + QUESTIONARIO_WIDTH_COLUNA_AVANCO + "'>" + avanco + "</td><td>" + questaoDto.getTextoInicial() + "</td></tr>";
        }
        
        RespostaItemQuestionarioDTO resposta = questaoDto.obtemRespostaItemDto(sequencialResposta);
        
        RespostaItemQuestionarioService respostaItemQuestionarioService = (RespostaItemQuestionarioService) ServiceLocator.getInstance().getService(RespostaItemQuestionarioService.class, null);
        QuestionarioService questionarioService = (QuestionarioService) ServiceLocator.getInstance().getService(QuestionarioService.class, null);
        OpcaoRespostaQuestionarioService opcaoRespostaQuestionarioServiceBean = (OpcaoRespostaQuestionarioService) ServiceLocator.getInstance().getService(OpcaoRespostaQuestionarioService.class, null);
   
        String indicadorTabela = "";
        String value = "";
        String value2 = "";
        String value3 = "";
        String value4 = "";
        String value5 = "";
        String value6 = "";
        
        String dadosAdicionais = "";
        String classOpcoes = "";
        
        String infoObr = "";
        String readonly = "";

        String colsTextArea = "50";
        String avancoTitulo = "";
        String idCampo = "";
        if (subQuestionario.equalsIgnoreCase("S")){
        	idCampo = "SUBQUESTIONARIO#" + questionario.getIdItem() + "#campoDyn_";
        }else{
        	idCampo = "campoDyn_";
        }
        boolean montaTitulo = true;
        boolean campoTabela = false;
        
        if (questaoAgrupadoraDto == null) {  // Verifica se é Questão de Matriz ou Tabela
            //avancoTitulo = "<td width='" + QUESTIONARIO_WIDTH_COLUNA_AVANCO + "'>" + avanco + "</td>";
        }else{
            colsTextArea = "40";    
            montaTitulo = questaoDto.getTipo().equalsIgnoreCase("T");
            if (questaoAgrupadoraDto.getTipo().trim().equalsIgnoreCase("L")) {
                if (sequencialResposta == 0) {
                    campoTabela = true;
                    idCampo = "campoTbl_";
                    indicadorTabela = "#seq#";
                }else{
                    idCampo = "campoDyn_"+sequencialResposta+"#";
                    indicadorTabela = sequencialResposta.toString();
                }
            }
        }

        if (questaoDto.getCalculada() != null && questaoDto.getCalculada().equalsIgnoreCase("S")) {
            readonly = " readonly='readonly' ";
        }
        
        if ("S".equalsIgnoreCase(questaoDto.getObrigatoria()) && (!(questaoDto.getTipo().equalsIgnoreCase("T") || questaoDto.getTipoQuestao().equalsIgnoreCase("F") || questaoDto.getTipoQuestao().equalsIgnoreCase("L")) )){
            classOpcoes += "Valid[Required";
            infoObr = "*";
        }
        if (questaoDto.getTipoQuestao().equalsIgnoreCase("T") || questaoDto.getTipoQuestao().equalsIgnoreCase("A")){ //Texto
            if (!classOpcoes.equalsIgnoreCase("")){
                classOpcoes += "] ";
            }
            if (resposta!=null){
                value = resposta.getRespostaTextual();
            }
        }else if (questaoDto.getTipoQuestao().equalsIgnoreCase("D")){ //Data
            if (!classOpcoes.equalsIgnoreCase("")){
                classOpcoes += ",";
            }else{
                classOpcoes += "Valid[";
            }
            classOpcoes += "Date";
            
            classOpcoes += "] Format[Date]";
            
            if (resposta!=null){
                if (resposta.getRespostaData() != null){
                    value = UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, resposta.getRespostaData(), WebUtil.getLanguage(request));
                }
            }                       
        }else if (questaoDto.getTipoQuestao().equalsIgnoreCase("H")){ //Data
            if (!classOpcoes.equalsIgnoreCase("")){
                classOpcoes += ",";
            }else{
                classOpcoes += "Valid[";                        
            }
            classOpcoes += "Hora";
            
            classOpcoes += "] Format[Hora]";
            
            if (resposta!=null){
                if(resposta.getRespostaHora() != null){
                    if(resposta.getRespostaHora().length() == 4)
                        value = UtilDatas.formatHoraStr(resposta.getRespostaHora());
                    else if(resposta.getRespostaHora().length() == 3)
                        value = resposta.getRespostaHora().substring(0, 2) + ":" 
                              + resposta.getRespostaHora().substring(2);
                    else value = resposta.getRespostaHora();
                }
            }
        }else if (questaoDto.getTipoQuestao().equalsIgnoreCase("N")){ //Número (sem casas decimais) ou Frequencia Cardiaca
            if (!classOpcoes.equalsIgnoreCase("")){
                classOpcoes += "] ";
            }
            classOpcoes += "Format[Numero]";
    
            if (resposta!=null){
                if (resposta.getRespostaNumero() != null){
                    value = UtilFormatacao.formatDouble(resposta.getRespostaNumero(), 0);
                }
            } 
        }else if (questaoDto.getTipoQuestao().equalsIgnoreCase("Q")){
            if (!classOpcoes.equalsIgnoreCase("")){
                classOpcoes += "] ";
            }
            classOpcoes += "Format[Numero]";
    
            if (resposta!=null){
                if (resposta.getRespostaValor() != null){
                    value = UtilFormatacao.formatDouble(resposta.getRespostaValor(), 0);
                }
            }                       
        }else if (questaoDto.getTipoQuestao().equalsIgnoreCase("V")){ //Valor Decimal 
            if (!classOpcoes.equalsIgnoreCase("")){
                classOpcoes += "] ";
            }                   
            classOpcoes += "Format[Moeda]";
            
            if (resposta!=null){
                int qtdeDecimais = 0;
                if (questaoDto.getDecimais()!=null){
                    qtdeDecimais = questaoDto.getDecimais().intValue();
                }
                if (resposta.getRespostaValor() != null){
                    value = UtilFormatacao.formatDouble(resposta.getRespostaValor(), qtdeDecimais);
                }
            }                       
        }else if (questaoDto.getTipoQuestao().equalsIgnoreCase("%")){ // %
        	if (!classOpcoes.equalsIgnoreCase("")){
        		classOpcoes += "] ";
        	}                   
        	classOpcoes += "Format[Moeda]";
        	
        	if (resposta!=null){
        		int qtdeDecimais = 0;
        		if (questaoDto.getDecimais()!=null){
        			qtdeDecimais = questaoDto.getDecimais().intValue();
        		}
        		if (resposta.getRespostaPercentual() != null){
        			value = UtilFormatacao.formatDouble(resposta.getRespostaPercentual(), qtdeDecimais);
        		}
        	}                       
        }else{
            if (!classOpcoes.equalsIgnoreCase("")){
                classOpcoes += "] ";
            }                       
        }
        if (questaoDto.getTipoQuestao().equalsIgnoreCase("1")){ //Faixa de Números (sem casas decimais)
            if (resposta!=null){
                value = UtilFormatacao.formatDouble(resposta.getRespostaValor(), 0);
                value2 = UtilFormatacao.formatDouble(resposta.getRespostaValor2(), 0);
            }    
        }
        if (questaoDto.getTipoQuestao().equalsIgnoreCase("2")){ //Faixa de Valores (com casas decimais)
            if (resposta!=null){
                int qtdeDecimais = 0;
                if (questaoDto.getDecimais()!=null){
                    qtdeDecimais = questaoDto.getDecimais().intValue();
                }
                value = UtilFormatacao.formatDouble(resposta.getRespostaValor(), qtdeDecimais);
                value2 = UtilFormatacao.formatDouble(resposta.getRespostaValor2(), qtdeDecimais);
            }                       
        }
        if (questaoDto.getTipoQuestao().equalsIgnoreCase("G")){ //Mes/Ano
            if (resposta!=null){
                if (resposta.getRespostaMes() != null){
                    value = UtilFormatacao.formatInt(resposta.getRespostaMes().intValue(), "00");
                }
                if (resposta.getRespostaAno() != null){
                    value2 = UtilFormatacao.formatInt(resposta.getRespostaAno().intValue(), "0000");
                }
            }                       
        }
        if (questaoDto.getTipoQuestao().equalsIgnoreCase("*")){ //Percentual e Valor
            if (resposta!=null){
                if (resposta.getRespostaPercentual() != null){
                    value = UtilFormatacao.formatDouble(resposta.getRespostaPercentual(), 2);
                }
                if (resposta.getRespostaValor() != null){
                    value2 = UtilFormatacao.formatDouble(resposta.getRespostaValor(), 2);
                }
            }                       
        }
        if (questaoDto.getTipoQuestao().equalsIgnoreCase("L")){ //Campo Longo - Editor
            if (resposta!=null){
                value = resposta.getRespostaTextual();
            }                        
        }
        if (questaoDto.getTipoQuestao().equalsIgnoreCase("9")){ //Desenho
            if (resposta!=null){
                value = resposta.getRespostaTextual();
            }
        }
        if (questaoDto.getTipoQuestao().equalsIgnoreCase("G")){ //Mes/Ano
            classOpcoes += " Format[Numero] ";                                                                              
        }
        if (questaoDto.getTipoQuestao().equalsIgnoreCase("*")){ //% e Valor
        	classOpcoes += " Format[Moeda] ";                                                                               
        }   
        
        String strAux = questaoDto.getTituloQuestaoQuestionarioSemFmt();
        strAux = strAux.replaceAll("\n", "");
        strAux = strAux.replaceAll("\r", "");
        strAux = UtilHTML.decodeHTML(strAux);
        classOpcoes += " Description[" + strAux + "] ";
        
        if (!classOpcoes.equalsIgnoreCase("")){
            if (campoTabela){
                classOpcoes = "#class#='" + classOpcoes + "'";
            }else{
                classOpcoes = "class='" + classOpcoes + "'";
            }
        }
        
        value = UtilStrings.nullToVazio(value);
        String tituloQuestao = "";
        tituloQuestao="";
        
        if (questaoDto.getTipoQuestao().equalsIgnoreCase("9")){ //Desenho
        	montaTitulo = false;
        }
        
        String dimensionamentoCampo = "40%";
        
        if (montaTitulo){
            tituloQuestao += montaTituloQuestao(questaoDto, request);
            if (questaoDto.getTipo().equalsIgnoreCase("T") || questaoDto.getTipoQuestao().equalsIgnoreCase("F")){ //Texto Fixo
                bufferQuestao += "<tr>" + avancoTitulo + "<td align='left' style='text-align:left;' colspan='20' ><div id='divTitulo_"+questaoDto.getIdQuestaoQuestionario()+"'> " + tituloQuestao + "</div></td></tr>";
            }if (questaoDto.getTipo().equalsIgnoreCase("P")){ //HTML
                String valorDefault = questaoDto.getValorDefaultNoEnter();
                valorDefault = valorDefault.replaceAll("\\\\'", "'");
                bufferQuestao += "<tr>" + avancoTitulo + "<td align='left' style='text-align:left;' colspan='20' ><div>" + valorDefault + "</div></td></tr>";
            }else{
                if ("B".equalsIgnoreCase(questaoDto.getInfoResposta())){ //Em baixo da pergunta
                    bufferQuestao += "<tr>" + avancoTitulo + "<td></td><td align='left' style='text-align:left' colspan='4'><div id='divTitulo_"+questaoDto.getIdQuestaoQuestionario()+"'>" + tituloQuestao + "</div></td></tr>";
                    
                    //bufferQuestao += "<tr><td>" + avanco;
                    bufferQuestao += "<tr>" + avancoTitulo + "<td align='left' style='text-align:left' >";
                    
                    dimensionamentoCampo = "90%";
                }else{
                	if (QUESTIONARIO_QUEBRA_LINHA_FINAL_TIT_QUESTAO.equalsIgnoreCase("S")){
                		bufferQuestao += "<tr>" + avancoTitulo + "<td align='left' style='text-align:left;' width='" + QUESTIONARIO_WIDTH_COLUNA_TITULO_QUESTAO + "' ><div id='divTitulo_"+questaoDto.getIdQuestaoQuestionario()+"'>" + tituloQuestao + "</div>";
                	}else{
                		if (tituloQuestao.length() <= 40){
                			bufferQuestao += "<tr>" + avancoTitulo + "<td align='left' nowrap='true' style='text-align:left; white-space: nowrap;' width='" + QUESTIONARIO_WIDTH_COLUNA_TITULO_QUESTAO + "' ><div id='divTitulo_"+questaoDto.getIdQuestaoQuestionario()+"'>" + tituloQuestao + "</div>";
                		}else{
                			bufferQuestao += "<tr>" + avancoTitulo + "<td align='left' style='text-align:left;' width='" + QUESTIONARIO_WIDTH_COLUNA_TITULO_QUESTAO + "' ><div id='divTitulo_"+questaoDto.getIdQuestaoQuestionario()+"'>" + tituloQuestao + "</div>";
                		}
                	}
                }  
                
                /*if (questaoDto.getIdSolicitacaoServico() != null && questionario.getDetalhaHistorico().equalsIgnoreCase("S")){
                	bufferQuestao += "<div id='divHist_"+questaoDto.getIdQuestaoQuestionario()+"'><a href='#ancoraVisualizaHistoricoCampo'><img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/histCampo.gif' border='0' style='cursor:pointer' title='Clique aqui para obter o histórico deste campo' onclick='chamaTelaHistoricoQuest(\"" + idCampo + questaoDto.getIdQuestaoQuestionario() + "\", \"" + questaoDto.getIdQuestaoOrigem() + "\")'></a></div>";
        	    	if (questaoDto.getTipoQuestao().equalsIgnoreCase("N") ||
        	    			questaoDto.getTipoQuestao().equalsIgnoreCase("V") ||
        	    			questaoDto.getTipoQuestao().equalsIgnoreCase("%") ||
        	    			questaoDto.getTipoQuestao().equalsIgnoreCase("P") ||
        	    			questaoDto.getTipoQuestao().equalsIgnoreCase("Q") ||
        	    			questaoDto.getTipoQuestao().equalsIgnoreCase("!") ||
        	    			questaoDto.getTipoQuestao().equalsIgnoreCase("*") ||
        	    			questaoDto.getTipoQuestao().equalsIgnoreCase("1") ||
        	    			questaoDto.getTipoQuestao().equalsIgnoreCase("2")){
        	    		bufferQuestao += "<div id='divHistGraf_"+questaoDto.getIdQuestaoQuestionario()+"'><a href='#ancoraVisualizaHistoricoCampo'><img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/grafico_peq.gif' border='0' style='cursor:pointer' title='Clique aqui para visualizar o gráfico do histórico deste campo' onclick='chamaTelaHistoricoQuestGrafico(\"" + idCampo + questaoDto.getIdQuestaoQuestionario() + "\", \"" + questaoDto.getIdQuestaoOrigem() + "\")'></a></div>";
        	    	}                	
                }*/
                
                if (questaoDto.getUnidade()!=null && !questaoDto.getUnidade().equalsIgnoreCase("")){
                    dadosAdicionais += " " + questaoDto.getUnidade();
                }
                if (questaoDto.getValoresReferencia()!=null && !questaoDto.getValoresReferencia().equalsIgnoreCase("")){
                    dadosAdicionais += "</td><td>" + questaoDto.getValoresReferencia() + "";
                }                   
            }
	    }else {
	    	if (questaoDto.getIdSolicitacaoServico() != null && questionario.getDetalhaHistorico().equalsIgnoreCase("S")){
	    		if (!questaoDto.getTipoQuestao().equalsIgnoreCase("9")){ //Desenho
	    			bufferQuestao += "<div id='divHist_"+questaoDto.getIdQuestaoQuestionario()+"'><a href='#ancoraVisualizaHistoricoCampo'><img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/seta_link.gif' border='0' style='cursor:pointer' title='Clique aqui para obter o histórico deste campo' onclick='chamaTelaHistoricoQuest(\"" + idCampo + questaoDto.getIdQuestaoQuestionario() + "\", \"" + questaoDto.getIdQuestaoOrigem() + "\")'></a></div>";
	    		}
		    	if (questaoDto.getTipoQuestao().equalsIgnoreCase("N") ||
		    			questaoDto.getTipoQuestao().equalsIgnoreCase("V") ||
		    			questaoDto.getTipoQuestao().equalsIgnoreCase("%") ||
		    			questaoDto.getTipoQuestao().equalsIgnoreCase("P") ||
		    			questaoDto.getTipoQuestao().equalsIgnoreCase("Q") ||
		    			questaoDto.getTipoQuestao().equalsIgnoreCase("!") ||
		    			questaoDto.getTipoQuestao().equalsIgnoreCase("*") ||
		    			questaoDto.getTipoQuestao().equalsIgnoreCase("1") ||
		    			questaoDto.getTipoQuestao().equalsIgnoreCase("2")){
		    		bufferQuestao += "<div id='divHistGraf_"+questaoDto.getIdQuestaoQuestionario()+"'><a href='#ancoraVisualizaHistoricoCampo'><img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/grafico.gif' border='0' style='cursor:pointer' title='Clique aqui para visualizar o gráfico do histórico deste campo' onclick='chamaTelaHistoricoQuestGrafico(\"" + idCampo + questaoDto.getIdQuestaoQuestionario() + "\", \"" + questaoDto.getIdQuestaoOrigem() + "\")'></a></div>";
		    	}	    		
	    	}
	        if (questaoDto.getUnidade()!=null && !questaoDto.getUnidade().equalsIgnoreCase("")){
	            dadosAdicionais += " " + questaoDto.getUnidade();
	        }    
	        if (questaoDto.getValoresReferencia()!=null && !questaoDto.getValoresReferencia().equalsIgnoreCase("")){
	            dadosAdicionais += "</td><td>" + questaoDto.getValoresReferencia() + "";
	        }                   
	    }         
        
        if (questaoDto.getTipoQuestao().equalsIgnoreCase("T")){ //Texto Curto (Tamanho delimitado)
            bufferQuestao += "</td><td width='" + dimensionamentoCampo + "' colspan='10' align='left' style='text-align:left'  ><input type='text' " + classOpcoes + " onchange='limpaCamposCalculados(this);'" + " name='" + idCampo + questaoDto.getIdQuestaoQuestionario() + "'" + readonly + " size='" + QUESTIONARIO_SIZE_QUESTAO + "' maxlength='" + questaoDto.getTamanho() + "' value='" + value + "' />" + dadosAdicionais + "</td></tr>";              
        }
        if (questaoDto.getTipoQuestao().equalsIgnoreCase("A")){ //Texto Longo (Campo de observações, etc.)
        	if (questaoAgrupadoraDto == null) {
        		if (PRONTUARIO_FORMA_EDICAO.equalsIgnoreCase("P")){
        			bufferQuestao += montaIconeModeloTextual(idCampo + questaoDto.getIdQuestaoQuestionario(), "A") + "</td><td width='" + dimensionamentoCampo + "' colspan='10' align='left' style='text-align:left'  ><div id='listagemModelosTextuais" + idCampo + questaoDto.getIdQuestaoQuestionario() + "' style='width: 595px; height:195px; overflow: auto; display:none; border:1px solid black'></div><textarea " + classOpcoes + " onchange='limpaCamposCalculados(this);'" + " name='" + idCampo + questaoDto.getIdQuestaoQuestionario() + "' id='" + idCampo + questaoDto.getIdQuestaoQuestionario() + "'" + readonly + " rows='1' cols='" + colsTextArea + "' >" + value + "</textarea>" + dadosAdicionais + "</td></tr>";
        		}else{
        			bufferQuestao += montaIconeModeloTextual(idCampo + questaoDto.getIdQuestaoQuestionario(), "A") + "</td><td width='" + dimensionamentoCampo + "' colspan='10' align='left' style='text-align:left'  ><div id='listagemModelosTextuais" + idCampo + questaoDto.getIdQuestaoQuestionario() + "' style='width: 595px; height:195px; overflow: auto; display:none; border:1px solid black'></div><textarea " + classOpcoes + " onchange='limpaCamposCalculados(this);'" + " name='" + idCampo + questaoDto.getIdQuestaoQuestionario() + "' id='" + idCampo + questaoDto.getIdQuestaoQuestionario() + "'" + readonly + " rows='1' cols='" + colsTextArea + "' onkeydown='resizeTextArea(event, this)' onblur='resizeTextArea(event, this)' style='width:100%'>" + value + "</textarea>" + dadosAdicionais + "</td></tr>";
        		}
        	}else{
        		bufferQuestao += "</td><td width='" + dimensionamentoCampo + "' colspan='10' align='left' style='text-align:left'  ><div id='listagemModelosTextuais" + idCampo + questaoDto.getIdQuestaoQuestionario() + "' style='width: 595px; height:195px; overflow: auto; display:none; border:1px solid black'></div><textarea " + classOpcoes + " onchange='limpaCamposCalculados(this);'" + " name='" + idCampo + questaoDto.getIdQuestaoQuestionario() + "' id='" + idCampo + questaoDto.getIdQuestaoQuestionario() + "'" + readonly + " rows='1' cols='" + colsTextArea + "' >" + value + "</textarea>" + dadosAdicionais + "</td></tr>";
        	}
        }
        if (questaoDto.getTipoQuestao().equalsIgnoreCase("L")){ //Texto Longo com Editor (Campo de Laudos, etc.)
        	if (questaoAgrupadoraDto == null) {
        		bufferQuestao += montaIconeModeloTextual(idCampo + questaoDto.getIdQuestaoQuestionario(), "L") + "</td><td width='" + dimensionamentoCampo + "' colspan='10' align='left' style='text-align:left'  ><div id='listagemModelosTextuais" + idCampo + questaoDto.getIdQuestaoQuestionario() + "' style='width: 595px; height:195px; overflow: auto; display:none; border:1px solid black'></div><textarea " + classOpcoes + " onchange='limpaCamposCalculados(this);'" + " name='" + idCampo + questaoDto.getIdQuestaoQuestionario() + "' id='" + idCampo + questaoDto.getIdQuestaoQuestionario() + "'" + readonly + " rows='1' cols='" + colsTextArea + "' style='width: 95%' >" + value + "</textarea>" + dadosAdicionais + "</td></tr>";
        	}else{
        		bufferQuestao += "</td><td width='" + dimensionamentoCampo + "' colspan='10' align='left' style='text-align:left'  ><div id='listagemModelosTextuais" + idCampo + questaoDto.getIdQuestaoQuestionario() + "' style='width: 595px; height:195px; overflow: auto; display:none; border:1px solid black'></div><textarea " + classOpcoes + " onchange='limpaCamposCalculados(this);'" + " name='" + idCampo + questaoDto.getIdQuestaoQuestionario() + "' id='" + idCampo + questaoDto.getIdQuestaoQuestionario() + "'" + readonly + " rows='1' cols='" + colsTextArea + "' style='width: 95%' >" + value + "</textarea>" + dadosAdicionais + "</td></tr>";
        	}
            
            List lst = (List) request.getAttribute("LST_CAMPOS_EDITOR");
            if (lst == null)
                lst = new ArrayList();
            lst.add("" + idCampo + questaoDto.getIdQuestaoQuestionario());       
            
            request.setAttribute("LST_CAMPOS_EDITOR", lst);
        }
        if (questaoDto.getTipoQuestao().equalsIgnoreCase("R")){ //Radio
            if (questaoDto.getColOpcoesResposta()!=null){
                String aux = "";
                if ("B".equalsIgnoreCase(questaoDto.getInfoResposta())){ //Em baixo da pergunta
                    aux = " colspan='5' ";
                }                           
                String bufferOut = "</td><td width='" + dimensionamentoCampo + "' align='left' style='text-align:left' " + aux + " ><div id='divOpcoes_"+questaoDto.getIdQuestaoQuestionario()+"'>";
                
                for(Iterator itOpcResp = questaoDto.getColOpcoesResposta().iterator(); itOpcResp.hasNext();){
                    OpcaoRespostaQuestionarioDTO opcRespDto =  (OpcaoRespostaQuestionarioDTO)itOpcResp.next();
                    opcRespDto.setSelecionada(false);
                    
                    String checked = "";
                    if (resposta!=null){
                        if (isInCollection(opcRespDto, resposta.getColOpcoesResposta())){
                            checked = " checked='checked' ";
                            opcRespDto.setSelecionada(true);
                        }
                    }                               
                    
                    bufferOut += "<input type='radio' " + classOpcoes + " onclick='limpaCamposCalculados(this);trataComplementoOpcao(this,\""+questaoDto.getIdQuestaoQuestionario().toString()+"\",\""+opcRespDto.getIdOpcaoRespostaQuestionario().toString()+"\")'" + " name='" + idCampo + questaoDto.getIdQuestaoQuestionario() + "' value='" + opcRespDto.getIdOpcaoRespostaQuestionario() + "' " + checked + "/><span id='tituloOpc_" + opcRespDto.getIdOpcaoRespostaQuestionario() + "' class='ml2 mr5'>" + opcRespDto.getTitulo() + "</span>";
                    if (opcRespDto.getQuestaoComplementoDto() != null) {
                        String display = "none";
                        if (opcRespDto.getQuestaoComplementoDto().getRespostaItemDto() != null) {
                            if (opcRespDto.isSelecionada()) {
                                display = "block";
                            }else{
                                opcRespDto.getQuestaoComplementoDto().setRespostaItemDto(null);
                            }    
                        }
                        bufferOut += "<div id='divOpcao"+questaoDto.getIdQuestaoQuestionario().toString()+"_"+opcRespDto.getIdOpcaoRespostaQuestionario().toString()+"' style='display:"+display+"'><table width='100%'>";
                        bufferOut += montaLinhaQuestao(opcRespDto.getQuestaoComplementoDto(), null, null, request, validaGeral, questionario, somenteLeitura, subQuestionario);
                        bufferOut += "</table></div>"; 
                    }                    
                }
                bufferOut += "" + dadosAdicionais + "</td></tr>";

                /*
                for(Iterator itOpcResp = questaoDto.getColOpcoesResposta().iterator(); itOpcResp.hasNext();){
                    OpcaoRespostaQuestionarioDTO opcRespDto =  (OpcaoRespostaQuestionarioDTO)itOpcResp.next();
                    
                    if (opcRespDto.getQuestaoComplementoDto() != null) {
                        String display = "none";
                        if (opcRespDto.getQuestaoComplementoDto().getRespostaItemDto() != null) {
                            if (opcRespDto.isSelecionada()) {
                                display = "block";
                            }else{
                                opcRespDto.getQuestaoComplementoDto().setRespostaItemDto(null);
                            }    
                        }
                        bufferOut += "<tr><td>&nbsp;</td><td>&nbsp;</td><td><div id='divOpcao"+questaoDto.getIdQuestaoQuestionario().toString()+"_"+opcRespDto.getIdOpcaoRespostaQuestionario().toString()+"' style='display:"+display+"'><table width='100%'>";
                        bufferOut += montaLinhaQuestao(opcRespDto.getQuestaoComplementoDto(), null, null, request, validaFaixaValoresGeral, questionario, somenteLeitura);
                        bufferOut += "</table></div></td></tr>"; 
                    }
                }
                */

                bufferOut += "</div>";
                bufferQuestao += bufferOut;                                                                                 
            }
        }
        if (questaoDto.getTipoQuestao().equalsIgnoreCase("C")){ //CheckBox
            if (questaoDto.getColOpcoesResposta()!=null){
                String aux = "";
                if ("B".equalsIgnoreCase(questaoDto.getInfoResposta())){ //Em baixo da pergunta
                    aux = " colspan='5' ";
                }
                
                String bufferOut = "</td><td width='" + dimensionamentoCampo + "' align='left' style='text-align:left' " + aux + " ><div id='divOpcoes_"+questaoDto.getIdQuestaoQuestionario()+"'>";
                for(Iterator itOpcResp = questaoDto.getColOpcoesResposta().iterator(); itOpcResp.hasNext();){
                    OpcaoRespostaQuestionarioDTO opcRespDto =  (OpcaoRespostaQuestionarioDTO)itOpcResp.next();
                    opcRespDto.setSelecionada(false);
                    
                    String checked = "";
                    if (resposta!=null){
                        if (isInCollection(opcRespDto, resposta.getColOpcoesResposta())){
                            checked = " checked='checked' ";
                            opcRespDto.setSelecionada(true);                            
                        }
                    }
                    
                    bufferOut += "<input type='checkbox' " + classOpcoes + " onclick='limpaCamposCalculados(this);trataComplementoOpcao(this,\""+questaoDto.getIdQuestaoQuestionario().toString()+"\",\""+opcRespDto.getIdOpcaoRespostaQuestionario().toString()+"\")'" + " name='" + idCampo + questaoDto.getIdQuestaoQuestionario() + "' value='" + opcRespDto.getIdOpcaoRespostaQuestionario() + "' " + checked + "/><span id='tituloOpc_" + opcRespDto.getIdOpcaoRespostaQuestionario() + "' class='ml2 mr5'>" + opcRespDto.getTitulo() + "</span>";
                    if (opcRespDto.getQuestaoComplementoDto() != null) {
                        String display = "none";
                        if (opcRespDto.getQuestaoComplementoDto().getRespostaItemDto() != null) {
                            if (opcRespDto.isSelecionada()) {
                                display = "block";
                            }else{
                                opcRespDto.getQuestaoComplementoDto().setRespostaItemDto(null);
                            }    
                        }
                        bufferOut += "<div id='divOpcao"+questaoDto.getIdQuestaoQuestionario().toString()+"_"+opcRespDto.getIdOpcaoRespostaQuestionario().toString()+"' style='display:"+display+"'><table width='100%'>";
                        bufferOut += montaLinhaQuestao(opcRespDto.getQuestaoComplementoDto(), null, null, request, validaGeral, questionario, somenteLeitura, subQuestionario);
                        bufferOut += "</table></div>"; 
                    }
                }
                bufferOut += "" + dadosAdicionais + "</td></tr>";

                /*
                for(Iterator itOpcResp = questaoDto.getColOpcoesResposta().iterator(); itOpcResp.hasNext();){
                    OpcaoRespostaQuestionarioDTO opcRespDto =  (OpcaoRespostaQuestionarioDTO)itOpcResp.next();
                    
                    if (opcRespDto.getQuestaoComplementoDto() != null) {
                        String display = "none";
                        if (opcRespDto.getQuestaoComplementoDto().getRespostaItemDto() != null) {
                            if (opcRespDto.isSelecionada()) {
                                display = "block";
                            }else{
                                opcRespDto.getQuestaoComplementoDto().setRespostaItemDto(null);
                            }    
                        }
                        bufferOut += "<tr><td>&nbsp;</td><td>&nbsp;</td><td><div id='divOpcao"+questaoDto.getIdQuestaoQuestionario().toString()+"_"+opcRespDto.getIdOpcaoRespostaQuestionario().toString()+"' style='display:"+display+"'><table width='100%'>";
                        bufferOut += montaLinhaQuestao(opcRespDto.getQuestaoComplementoDto(), null, null, request, validaFaixaValoresGeral, questionario, somenteLeitura);
                        bufferOut += "</table></div></td></tr>"; 
                    }
                }
                */
                
                bufferOut += "</div>";
                bufferQuestao += bufferOut;                                                                                 
            }                   
        }
        if (questaoDto.getTipoQuestao().equalsIgnoreCase("X")){ //ComboBox (Caixa de Seleção)
            if (questaoDto.getColOpcoesResposta()!=null){
                String aux = "";
                if ("B".equalsIgnoreCase(questaoDto.getInfoResposta())){ //Em baixo da pergunta
                    aux = " colspan='5' ";
                }
                String bufferOut = "</td><td width='" + dimensionamentoCampo + "' align='left' style='text-align:left' " + aux + "  ><div id='divOpcoes_"+questaoDto.getIdQuestaoQuestionario()+"'>";
                bufferOut += "<select " + classOpcoes + " onclick='limpaCamposCalculados(this);trataComplementoOpcao(this,\""+questaoDto.getIdQuestaoQuestionario().toString()+"\",\"0\")'" + " name='" + idCampo + questaoDto.getIdQuestaoQuestionario() + "'>";
                bufferOut += "<option value=''>-- Selecione --</option>";
                for(Iterator itOpcResp = questaoDto.getColOpcoesResposta().iterator(); itOpcResp.hasNext();){
                    OpcaoRespostaQuestionarioDTO opcRespDto =  (OpcaoRespostaQuestionarioDTO)itOpcResp.next();
                    opcRespDto.setSelecionada(false);
                    
                    String selected = "";
                    if (resposta!=null){
                        if (isInCollection(opcRespDto, resposta.getColOpcoesResposta())){
                            selected = " selected='selected' ";
                            opcRespDto.setSelecionada(true);                            
                        }
                    }
                    
                    bufferOut += "<option value='" + opcRespDto.getIdOpcaoRespostaQuestionario() + "' " + selected + ">" + opcRespDto.getTitulo() + "</option>";                                                        
                }
                bufferOut += "</select>";
                bufferOut += "" + dadosAdicionais + "</td></tr>";

                for(Iterator itOpcResp = questaoDto.getColOpcoesResposta().iterator(); itOpcResp.hasNext();){
                    OpcaoRespostaQuestionarioDTO opcRespDto =  (OpcaoRespostaQuestionarioDTO)itOpcResp.next();
                    
                    if (opcRespDto.getQuestaoComplementoDto() != null) {
                        String display = "none";
                        if (opcRespDto.getQuestaoComplementoDto().getRespostaItemDto() != null) {
                            if (opcRespDto.isSelecionada()) {
                                display = "block";
                            }else{
                                opcRespDto.getQuestaoComplementoDto().setRespostaItemDto(null);
                            }    
                        }
                        bufferOut += "<tr><td>&nbsp;</td><td>&nbsp;</td><td><div id='divOpcao"+questaoDto.getIdQuestaoQuestionario().toString()+"_"+opcRespDto.getIdOpcaoRespostaQuestionario().toString()+"' style='display:"+display+"'><table width='100%'>";
                        bufferOut += montaLinhaQuestao(opcRespDto.getQuestaoComplementoDto(), null, null, request, validaGeral, questionario, somenteLeitura, subQuestionario);
                        bufferOut += "</table></div></td></tr>"; 
                    }
                }
                
                bufferOut += "</div>";
                bufferQuestao += bufferOut;                                                                                 
            }                   
        }
        if (questaoDto.getTipoQuestao().equalsIgnoreCase("N")){ //Número (sem casas decimais)
        	String strValidaFaixaValores = "";
        	if (questaoDto.getValorPermitido1() != null && questaoDto.getValorPermitido2() != null){
        		if (questaoDto.getValorPermitido1().doubleValue() > 0 || questaoDto.getValorPermitido2().doubleValue() > 0){
        			strValidaFaixaValores = "onblur=\"validaFaixaValores(this, '" + UtilFormatacao.formatDouble(questaoDto.getValorPermitido1(),3) + "','" + UtilFormatacao.formatDouble(questaoDto.getValorPermitido2(),3) + "', false)\"";
        			validaGeral.addValidacao("ret = validaFaixaValores(document.formQuestionario." + idCampo + questaoDto.getIdQuestaoQuestionario() + ", '" + UtilFormatacao.formatDouble(questaoDto.getValorPermitido1(),3) + "','" + UtilFormatacao.formatDouble(questaoDto.getValorPermitido2(),3) + "', true);\nif (!ret) {return false;}\n");
        		}
        	}
        	String tamanhoStr = "15"; 
        	if (questaoDto.getTamanho() != null && questaoDto.getTamanho().intValue() > 0){
        		tamanhoStr = "" + questaoDto.getTamanho();
        	}
            bufferQuestao += "</td><td width='" + dimensionamentoCampo + "' align='left' style='text-align:left'  ><input type='text' " + classOpcoes + " name='" + idCampo + questaoDto.getIdQuestaoQuestionario() + "'" + readonly + " size='" + tamanhoStr + "' maxlength='" + tamanhoStr + "' value='" + value + "' " + strValidaFaixaValores + "/>" + dadosAdicionais + "</td></tr>";                                      
        }
        if (questaoDto.getTipoQuestao().equalsIgnoreCase("V")){ //Valor Decimal
        	String strValidaFaixaValores = "";
        	if (questaoDto.getValorPermitido1() != null && questaoDto.getValorPermitido2() != null){
        		if (questaoDto.getValorPermitido1().doubleValue() > 0 || questaoDto.getValorPermitido2().doubleValue() > 0){
        			strValidaFaixaValores = "onblur=\"validaFaixaValores(this, '" + UtilFormatacao.formatDouble(questaoDto.getValorPermitido1(),3) + "','" + UtilFormatacao.formatDouble(questaoDto.getValorPermitido2(),3) + "', false)\"";
        			validaGeral.addValidacao("ret = validaFaixaValores(document.formQuestionario." + idCampo + questaoDto.getIdQuestaoQuestionario() + ", '" + UtilFormatacao.formatDouble(questaoDto.getValorPermitido1(),3) + "','" + UtilFormatacao.formatDouble(questaoDto.getValorPermitido2(),3) + "', true);\nif (!ret) {return false;}\n");
        		}
        	}       	
            bufferQuestao += "</td><td width='" + dimensionamentoCampo + "' align='left' style='text-align:left'  ><input type='text' " + classOpcoes + " name='" + idCampo + questaoDto.getIdQuestaoQuestionario() + "'" + readonly + " size='" + QUESTIONARIO_SIZE_QUESTAO + "' maxlength='" + questaoDto.getTamanho() + "' value='" + value + "' " + strValidaFaixaValores + "/>" + dadosAdicionais + "</td></tr>";                                        
        }
        if (questaoDto.getTipoQuestao().equalsIgnoreCase("%")){ //% (Percentual)
        	String strValidaFaixaValores = "";
        	if (questaoDto.getValorPermitido1() != null && questaoDto.getValorPermitido2() != null){
        		if (questaoDto.getValorPermitido1().doubleValue() > 0 || questaoDto.getValorPermitido2().doubleValue() > 0){
        			strValidaFaixaValores = "onblur=\"validaFaixaValores(this, '" + UtilFormatacao.formatDouble(questaoDto.getValorPermitido1(),3) + "','" + UtilFormatacao.formatDouble(questaoDto.getValorPermitido2(),3) + "', false)\"";
        			validaGeral.addValidacao("ret = validaFaixaValores(document.formQuestionario." + idCampo + questaoDto.getIdQuestaoQuestionario() + ", '" + UtilFormatacao.formatDouble(questaoDto.getValorPermitido1(),3) + "','" + UtilFormatacao.formatDouble(questaoDto.getValorPermitido2(),3) + "', true);\nif (!ret) {return false;}\n");
        		}
        	}         	
            bufferQuestao += "</td><td width='" + dimensionamentoCampo + "' align='left' style='text-align:left'  ><input type='text' " + classOpcoes + " onchange='limpaCamposCalculados(this);'" + " name='" + idCampo + questaoDto.getIdQuestaoQuestionario() + "'" + readonly + " size='" + QUESTIONARIO_SIZE_QUESTAO + "' maxlength='" + questaoDto.getTamanho() + "' value='" + value + "' " + strValidaFaixaValores + "/>" + dadosAdicionais + "</td></tr>";                                                            
        }
        if (questaoDto.getTipoQuestao().equalsIgnoreCase("*")){ //% (Percentual)  +   Valor Absoluto
            bufferQuestao += "</td><td width='" + dimensionamentoCampo + "' align='left' style='text-align:left'  ><input type='text' " + classOpcoes + " onchange='limpaCamposCalculados(this);'" + " name='" + idCampo + questaoDto.getIdQuestaoQuestionario() + "'" + readonly + " size='6' maxlength='6' value='" + value + "'/>%&nbsp;Valor: <input type='text' " + classOpcoes + " onchange='limpaCamposCalculados(this);'" + " name='" + idCampo + questaoDto.getIdQuestaoQuestionario() + "' size='15' maxlength='15' value='" + value2 + "'/>" + dadosAdicionais + "</td></tr>";                                                           
        }
        if (questaoDto.getTipoQuestao().equalsIgnoreCase("1")){ //Faixa de Valores (Números)
            bufferQuestao += "</td><td width='" + dimensionamentoCampo + "' align='left' style='text-align:left'  >Valor Inicial: <input type='text' " + classOpcoes + " onchange='limpaCamposCalculados(this);'" + " name='" + idCampo + questaoDto.getIdQuestaoQuestionario() + "' size='6' maxlength='6' value='" + value + "'/>&nbsp;Valor Final: <input type='text' " + classOpcoes + " onchange='limpaCamposCalculados(this);'" + " name='" + idCampo + questaoDto.getIdQuestaoQuestionario() + "' size='15' maxlength='15' value='" + value2 + "'/>" + dadosAdicionais + "</td></tr>";                                                                                
        }
        if (questaoDto.getTipoQuestao().equalsIgnoreCase("2")){ //Faixa de Valores (Valores decimais)
            bufferQuestao += "</td><td width='" + dimensionamentoCampo + "' align='left' style='text-align:left'  >Valor Inicial: <input type='text' " + classOpcoes + " onchange='limpaCamposCalculados(this);'" + " name='" + idCampo + questaoDto.getIdQuestaoQuestionario() + "' size='6' maxlength='6' value='" + value + "'/>&nbsp;Valor Final: <input type='text' " + classOpcoes + " onchange='limpaCamposCalculados(this);'" + " name='" + idCampo + questaoDto.getIdQuestaoQuestionario() + "' size='15' maxlength='15' value='" + value2 + "'/>" + dadosAdicionais + "</td></tr>";                                                                                                    
        }
        if (questaoDto.getTipoQuestao().equalsIgnoreCase("I")){ //Galeria de Imagens
            bufferQuestao += "</td><td width='" + dimensionamentoCampo + "' align='left' style='text-align:left'  ><a href='#'>[Clique aqui para inserir uma imagem]</a><div id='divGalImg" + questaoDto.getIdQuestaoQuestionario() + "'></div></td></tr>";                                                                                                  
        }
        if (questaoDto.getTipoQuestao().equalsIgnoreCase("M")){ //Galeria Multimídia (Videos,etc.)          }
            bufferQuestao += "</td><td width='" + dimensionamentoCampo + "' align='left' style='text-align:left'  ><a href='#' onclick=\"chamaTelaUploadArquivoMultimidia('divGalMulti" + questaoDto.getIdQuestaoQuestionario() + "'," + questaoDto.getIdQuestaoQuestionario() + ")\">[Clique aqui para inserir um arquivo multímidia]</a><div id='divGalMulti" + questaoDto.getIdQuestaoQuestionario() + "' style='border:1px solid black; height: 200px; width: 100%; background-color:white; overflow:auto; margin: 0px 10px 10px 10px;'></div></td></tr>";
            Collection colAnexos = new ArrayList();
            if (resposta != null){
	            if (resposta.getColRelacaoAnexos() != null){
	            	for(Iterator it = resposta.getColRelacaoAnexos().iterator(); it.hasNext();){
	            		RespostaItemQuestionarioAnexosDTO respAnexoDto = (RespostaItemQuestionarioAnexosDTO)it.next();
	            		ArquivoMultimidiaDTO arquivoMultimidia = new ArquivoMultimidiaDTO();
	            		arquivoMultimidia.setCaminhoArquivo("IDCITGED=" + respAnexoDto.getCaminhoAnexo());
	            		arquivoMultimidia.setObservacao(respAnexoDto.getObservacao());
	            		arquivoMultimidia.setIdQuestaoQuest(questaoDto.getIdQuestaoQuestionario());
	            		arquivoMultimidia.setNomeArquivo(respAnexoDto.getNomeArquivo());
	            		arquivoMultimidia.setUrlArquivo(Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/recuperaFromGed/recuperaFromGed.load?idControleGED=" + respAnexoDto.getCaminhoAnexo());
	            		
	            		colAnexos.add(arquivoMultimidia);
	            	}
	            }
            }
            request.getSession(true).setAttribute("TEMP_LISTA_ARQ_MULTIMIDIA", colAnexos);
            bufferQuestao += "<script>function chamaAtualicaoLista_" + questaoDto.getIdQuestaoQuestionario() + "(){document.formArquivosMultimidia.idDIV.value = 'divGalMulti" + questaoDto.getIdQuestaoQuestionario() + "';}</script>";
            bufferQuestao += "<script>addEvent(window, \"load\", chamaAtualicaoLista_" + questaoDto.getIdQuestaoQuestionario() + ", false);</script>";
            bufferQuestao += "<script>addEvent(window, \"load\", apresentaListaItensGED, true);</script>";
        }
        if (questaoDto.getTipoQuestao().equalsIgnoreCase("D")){ //Data
        	String aux = "";
        	
        	if (!somenteLeitura){
        		aux = "<img src=\"" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/cal.gif\" title=\"Clique aqui para selecionar uma data\" style=\"cursor:pointer\" align=\"top\" height=\"18\" onclick=\"return QUESTIONARIO_showCalendar('" + idCampo + questaoDto.getIdQuestaoQuestionario() + readonly + "', 'dd/mm/y');\">";
        	}
        	
            bufferQuestao += "</td><td width='" + dimensionamentoCampo + "' align='left' style='text-align:left'  ><input type='text' " + classOpcoes + " onchange='limpaCamposCalculados(this);'" + " name='" + idCampo + questaoDto.getIdQuestaoQuestionario() + readonly + "' id='" + idCampo + questaoDto.getIdQuestaoQuestionario() + readonly + "' size='10' maxlength='10' value='" + value + "'/>" + aux + " " + dadosAdicionais + "</td></tr>";              
        }
        if (questaoDto.getTipoQuestao().equalsIgnoreCase("H")){ //Hora
            bufferQuestao += "</td><td width='" + dimensionamentoCampo + "' align='left' style='text-align:left'  ><input type='text' " + classOpcoes + " onchange='limpaCamposCalculados(this);'" + " name='" + idCampo + questaoDto.getIdQuestaoQuestionario() + "'" + readonly + " size='5' maxlength='5' value='" + value + "'/>" + dadosAdicionais + "</td></tr>";                
        } 
        if (questaoDto.getTipoQuestao().equalsIgnoreCase("G")){ //Mes/Ano
            classOpcoes += "Format[Numero]";
            bufferQuestao += "</td><td width='" + dimensionamentoCampo + "' align='left' style='text-align:left'  >Mês: <input type='text' " + classOpcoes + " onchange='limpaCamposCalculados(this);'" + " name='" + idCampo + questaoDto.getIdQuestaoQuestionario() + "'" + readonly + "size='2' maxlength='2' value='" + value + "'/>&nbsp;Ano:<input type='text' " + classOpcoes + " onchange='limpaCamposCalculados(this);'" + " name='" + idCampo + questaoDto.getIdQuestaoQuestionario() + "'" + readonly + "size='4' maxlength='4' value='" + value2 + "'/>" + dadosAdicionais + "</td></tr>";                                                                             
        }
        if (questaoDto.getTipoQuestao().equalsIgnoreCase("8")){ //Listagem
            ListagemDTO listagem = ListagemConfig.getInstance().find(questaoDto.getNomeListagem());
            if (listagem != null){
                ListagemService listagemService = (ListagemService) ServiceLocator.getInstance().getService(ListagemService.class, null);
                listagem = (ListagemDTO) listagemService.restore(listagem);
                
                String aux = "";
                if ("B".equalsIgnoreCase(questaoDto.getInfoResposta())){ //Em baixo da pergunta
                    aux = " colspan='5' ";
                }
                String bufferOut = "</td><td width='" + dimensionamentoCampo + "' align='left' style='text-align:left' " + aux + "  >";
                bufferOut += "<select " + classOpcoes + " onchange='limpaCamposCalculados(this);'" + " name='" + idCampo + questaoDto.getIdQuestaoQuestionario() + "'>";
                bufferOut += "<option value=''>-- Selecione --</option>";
                for(Iterator it = listagem.getLinhas().iterator(); it.hasNext();){ 
                    ListagemLinhaDTO linha =  (ListagemLinhaDTO)it.next();
                    
                    String selected = "";
                    if (resposta != null && resposta.getRespostaIdListagem() != null){
                        if (linha.getId().trim().equalsIgnoreCase(resposta.getRespostaIdListagem().trim())){
                            selected = " selected='selected' "; 
                        }
                    }
                    
                    bufferOut += "<option value='" + linha.getId() + "' " + selected + ">" + linha.getDescricao() + "</option>";                                                        
                }
                bufferOut += "</select>";
                bufferOut += "" + dadosAdicionais + "</td></tr>";
                
                bufferQuestao += bufferOut;                                                                                 
            }
        }     
        if (questaoDto.getTipoQuestao().equalsIgnoreCase("9")){ //Desenho
    		GaleriaImagensService galeriaImagensService = (GaleriaImagensService) ServiceLocator.getInstance().getService(GaleriaImagensService.class, null);
    		ControleGEDService controleGEDService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
    		GaleriaImagensDTO galeriaImagensDTO = new GaleriaImagensDTO();
    		if (questaoDto.getIdImagem() != null){
    			galeriaImagensDTO.setIdImagem(questaoDto.getIdImagem());
    			galeriaImagensDTO = (GaleriaImagensDTO) galeriaImagensService.restore(galeriaImagensDTO);
    		}
    		if (galeriaImagensDTO == null){
    			galeriaImagensDTO = new GaleriaImagensDTO();
    		}
    		String caminhoAnexo = "";
    		if (galeriaImagensDTO.getIdImagem() != null){
				ControleGEDDTO controleGEDDTO = new ControleGEDDTO();
				controleGEDDTO.setIdControleGED(new Integer(galeriaImagensDTO.getNomeImagem()));
				controleGEDDTO = (ControleGEDDTO) controleGEDService.restore(controleGEDDTO);
				if (controleGEDDTO != null){
                    //String urlSistema = ParametroUtil.getValor(ParametroSistema.URL_Sistema, null, "");
					String urlSistema = Constantes.getValue("CONTEXTO_APLICACAO");
					File fileDir2 = new File(urlSistema + "/galeriaImagens");
					if (!fileDir2.exists()){
						fileDir2.mkdirs();
					}
					fileDir2 = new File(urlSistema + "/galeriaImagens/" + user.getIdEmpresa());
					if (!fileDir2.exists()){
						fileDir2.mkdirs();
					}
					fileDir2 = new File(urlSistema + "/galeriaImagens/" + user.getIdEmpresa() + "/" + galeriaImagensDTO.getIdCategoriaGaleriaImagem());
					if (!fileDir2.exists()){
						fileDir2.mkdirs();
					}
					
					try{
		                CriptoUtils.decryptFile(Constantes.getValue("DIRETORIO_GED") + "/" + user.getIdEmpresa() + "/" + controleGEDDTO.getPasta() + "/" + controleGEDDTO.getIdControleGED() + ".ged", 
		                        urlSistema + "/galeriaImagens/" + user.getIdEmpresa() + "/" + galeriaImagensDTO.getIdCategoriaGaleriaImagem() + "/" + controleGEDDTO.getIdControleGED() + "." + galeriaImagensDTO.getExtensao(), 
		                        System.getProperties().get("user.dir") + Constantes.getValue("CAMINHO_CHAVE_PRIVADA"));
					}catch (Exception e) {
						e.printStackTrace();
					}
	                caminhoAnexo = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/galeriaImagens/" + user.getIdEmpresa() + "/" + galeriaImagensDTO.getIdCategoriaGaleriaImagem() + "/" + controleGEDDTO.getIdControleGED() + "." + galeriaImagensDTO.getExtensao();
				}    			
    		}
        	
			bufferQuestao += "<tr><td width='" + QUESTIONARIO_WIDTH_COLUNA_AVANCO + "'>" + avanco + "</td><td colspan='20'>";
			
			if (galeriaImagensDTO.getIdImagem() == null){
				bufferQuestao += "<a href='#ancoraGaleriaImagens' onclick='chamaGaleriaImagens("+questaoDto.getIdQuestaoQuestionario()+"," + questaoDto.getIdImagem() + ", \"" + idCampo + questaoDto.getIdQuestaoQuestionario() + "\");'>" + questaoDto.getTituloQuestaoQuestionarioSemFmt() + "<a/>";
			}else{
				bufferQuestao += "<input type='button' name='btnDyn_" + questaoDto.getIdQuestaoQuestionario() + "' onclick='chamaEditorImagens("+questaoDto.getIdQuestaoQuestionario()+"," + questaoDto.getIdImagem() + ",\"" + caminhoAnexo + "\", \"" + idCampo + questaoDto.getIdQuestaoQuestionario() + "\");' value='" + questaoDto.getTituloQuestaoQuestionarioSemFmt() + "'/>";
			}
			bufferQuestao += "</td></tr>";
			bufferQuestao += "<tr><td colspan='20'>";
			
			bufferQuestao += "<textarea style='display:none' name='" + idCampo + questaoDto.getIdQuestaoQuestionario() + "' id='" + idCampo + questaoDto.getIdQuestaoQuestionario() + "'>" + value + "</textarea>";
			bufferQuestao += "<iframe id='ifrImagem_" + questaoDto.getIdQuestaoQuestionario() + "' name='ifrImagem_" + questaoDto.getIdQuestaoQuestionario() + "' src='about:blank' height='480%' width='640' frameborder='1'></iframe>";

			bufferQuestao += "</td></tr>";
			if (value != null && !value.trim().equalsIgnoreCase("")){
				bufferQuestao += "<script>document.getElementById('ifrImagem_" + questaoDto.getIdQuestaoQuestionario() + "').src = 'data:image/svg+xml;base64,'+encode64_questionario(document.getElementById('campoDyn_" + questaoDto.getIdQuestaoQuestionario() + "').value);</script>";
			}
        }
        
        /*
        if ("B".equalsIgnoreCase(questaoDto.getInfoResposta())){ //Em baixo da pergunta
            //OK
        }else{
            bufferQuestao += "</td></tr>";
        }
        */
        //bufferQuestao += "</td></tr>";
        
        return bufferQuestao;
    }
    
    private String montaIconeModeloTextual(String campo, String tipoCampo){
    	//return "<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/modelo.gif' border='0' style='cursor:pointer' title='Clique aqui para acessar a biblioteca de modelos textuais' onclick='chamaTelaModelosTextuaisQuest(\"" + campo + "\", \"" + tipoCampo + "\")'>";
    	return "";
    }

    private String montaTituloQuestao(QuestaoQuestionarioDTO questaoDto, HttpServletRequest request){
		String QUESTIONARIO_QUEBRA_LINHA_FINAL_TIT_QUESTAO = "S";
        String tituloQuestao = questaoDto.getTituloQuestaoQuestionario();        
        if (!tituloQuestao.equalsIgnoreCase("")){

            String infoObr = "";
            
            if ("S".equalsIgnoreCase(questaoDto.getObrigatoria()) && (!(questaoDto.getTipo().equalsIgnoreCase("T") || questaoDto.getTipoQuestao().equalsIgnoreCase("F")) )){
                infoObr = "*";
            }
            
            tituloQuestao = tituloQuestao.trim();
            tituloQuestao = tituloQuestao.replaceAll("<p>", "");
            tituloQuestao = tituloQuestao.replaceAll("</p>", "<br/>");
            
            if (tituloQuestao.length() > 6){
            	if (QUESTIONARIO_QUEBRA_LINHA_FINAL_TIT_QUESTAO.equalsIgnoreCase("S")){
	            	if (tituloQuestao.substring(tituloQuestao.length()-5, tituloQuestao.length()).equalsIgnoreCase("</br>") ||
	            		 tituloQuestao.substring(tituloQuestao.length()-5, tituloQuestao.length()).equalsIgnoreCase("<br/>")){
		                tituloQuestao = tituloQuestao.substring(0, tituloQuestao.length()-5) 
	                    + infoObr
	                    + "<br/>";            		
	            	}else if (tituloQuestao.substring(tituloQuestao.length()-6, tituloQuestao.length()).equalsIgnoreCase("<br />")){
		                tituloQuestao = tituloQuestao.substring(0, tituloQuestao.length()-6) 
		                                + infoObr
		                                + "<br/>";
	            	}
            	}else{
	            	if (tituloQuestao.substring(tituloQuestao.length()-5, tituloQuestao.length()).equalsIgnoreCase("</br>") ||
		            		 tituloQuestao.substring(tituloQuestao.length()-5, tituloQuestao.length()).equalsIgnoreCase("<br/>")){
			                tituloQuestao = tituloQuestao.substring(0, tituloQuestao.length()-5) 
		                    + infoObr
		                    + "";   
	            	}else if (tituloQuestao.substring(tituloQuestao.length()-4, tituloQuestao.length()).equalsIgnoreCase("<br>") ||
				            tituloQuestao.substring(tituloQuestao.length()-4, tituloQuestao.length()).equalsIgnoreCase("<br>")){
					        tituloQuestao = tituloQuestao.substring(0, tituloQuestao.length()-4) 
				            + infoObr
				            + ""; 			                
		            }else if (tituloQuestao.substring(tituloQuestao.length()-6, tituloQuestao.length()).equalsIgnoreCase("<br />")){
			                tituloQuestao = tituloQuestao.substring(0, tituloQuestao.length()-6) 
			                                + infoObr
			                                + "";
		            }
            	}
            }else{
                tituloQuestao = tituloQuestao + infoObr;
            }
        }
        return tituloQuestao;
    }
    
	private boolean isInCollection(OpcaoRespostaQuestionarioDTO opcRespDto, Collection colVerificar){
		if (colVerificar == null) return false;
		for(Iterator it = colVerificar.iterator(); it.hasNext();){
			RespostaItemQuestionarioOpcoesDTO respItemQuestDto = (RespostaItemQuestionarioOpcoesDTO)it.next();
			if (respItemQuestDto.getIdOpcaoRespostaQuestionario().intValue() == opcRespDto.getIdOpcaoRespostaQuestionario().intValue()){
				return true;
			}
		}
        for(Iterator it = colVerificar.iterator(); it.hasNext();){
            RespostaItemQuestionarioOpcoesDTO respItemQuestDto = (RespostaItemQuestionarioOpcoesDTO)it.next();
            if (respItemQuestDto.getTitulo().equalsIgnoreCase(opcRespDto.getTitulo())){
                return true;
            }
        }
		return false;
	}
}
