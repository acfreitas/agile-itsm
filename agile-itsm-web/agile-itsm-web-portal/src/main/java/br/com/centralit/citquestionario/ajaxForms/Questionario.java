package br.com.centralit.citquestionario.ajaxForms;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
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
import br.com.centralit.citajax.html.HTMLTextBox;
import br.com.centralit.citcorpore.negocio.GaleriaImagensService;
import br.com.centralit.citquestionario.bean.GrupoQuestionarioDTO;
import br.com.centralit.citquestionario.bean.OpcaoRespostaQuestionarioDTO;
import br.com.centralit.citquestionario.bean.QuestaoQuestionarioDTO;
import br.com.centralit.citquestionario.bean.QuestionarioDTO;
import br.com.centralit.citquestionario.negocio.CategoriaQuestionarioService;
import br.com.centralit.citquestionario.negocio.GrupoQuestionarioService;
import br.com.centralit.citquestionario.negocio.QuestionarioService;
import br.com.centralit.citquestionario.util.ListagemConfig;
import br.com.centralit.citquestionario.util.Upload;
import br.com.centralit.citquestionario.util.WebUtilQuestionario;
import br.com.citframework.dto.Usuario;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;
import br.com.citframework.util.WebUtil;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class Questionario extends AjaxFormAction {

	public Class getBeanClass() {
		return QuestionarioDTO.class;
	}

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Usuario user = WebUtilQuestionario.getUsuario(request);
		if (user == null){
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			return;
		}

		CategoriaQuestionarioService categoriaQuestionarioService = (CategoriaQuestionarioService) ServiceLocator.getInstance().getService(CategoriaQuestionarioService.class, null);
		Collection col = categoriaQuestionarioService.list();

		GaleriaImagensService galeriaImagensService = (GaleriaImagensService) ServiceLocator.getInstance().getService(GaleriaImagensService.class, null);
		Collection colImagens = galeriaImagensService.listOrderByCategoria();

		QuestionarioService questionarioService = (QuestionarioService) ServiceLocator.getInstance().getService(QuestionarioService.class, null);
		Collection colQuestionarios = questionarioService.listByIdEmpresa(user.getIdEmpresa());
		HTMLSelect idSubQuestionario = document.getSelectById("idSubQuestionario");
		idSubQuestionario.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		idSubQuestionario.addOptions(colQuestionarios, "idQuestionario", "nomeQuestionario", null);

		HTMLSelect idCategoriaQuestionario = document.getSelectById("idCategoriaQuestionarioAux");
		idCategoriaQuestionario.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		idCategoriaQuestionario.addOptions(col, "idCategoriaQuestionario", "nomeCategoriaQuestionario", null);

		HTMLSelect idImagem = document.getSelectById("idImagem");
		idImagem.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		idImagem.addOptions(colImagens, "idImagem", "descricaoAndCategoriaImagem", null);

		QuestionarioDTO questionario = (QuestionarioDTO) document.getBean();

		if(questionario.getIdQuestionario() == null){
			document.getElementById("btnCapGrupoQuest").setVisible(false);
			document.getElementById("btnOrdenarGrupos").setVisible(false);
			document.getElementById("btnVisualiza").setVisible(false);
			document.getElementById("btnExcluirGrupo").setVisible(false);
		}else{
			document.getElementById("btnCapGrupoQuest").setVisible(true);
			document.getElementById("btnOrdenarGrupos").setVisible(true);
			document.getElementById("btnVisualiza").setVisible(true);
			document.getElementById("btnExcluirGrupo").setVisible(true);
		}
		if (questionario.getAcao()!=null){
			if (questionario.getAcao().equalsIgnoreCase("restore")){
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
        ListagemConfig listagemConfig = ListagemConfig.getInstance();
        Collection colListagens = listagemConfig.getListagens();

        HTMLSelect comboListagem = document.getSelectById("nomeListagem");
        comboListagem.addOptions(colListagens, "nome", "descricao", null);

        HTMLSelect comboListagemAgrupada = document.getSelectById("nomeListagemAgrupada");
        comboListagemAgrupada.addOptions(colListagens, "nome", "descricao", null);

        HTMLSelect comboListagemComplemento = document.getSelectById("nomeListagemComplemento");
        comboListagemComplemento.addOptions(colListagens, "nome", "descricao", null);
	}

	public void gravarOrdemGrupos(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		QuestionarioDTO questionario = (QuestionarioDTO) document.getBean();
		Usuario user = WebUtilQuestionario.getUsuario(request);
		if (user == null){
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			return;
		}
		questionario.setIdEmpresa(user.getIdEmpresa());

		Collection colGruposQuestao = WebUtil.deserializeCollectionFromRequest(GrupoQuestionarioDTO.class, "colQuestionariosSerialize", request);
		questionario.setColGrupos(colGruposQuestao);

		if (colGruposQuestao == null || colGruposQuestao.size() == 0){
			document.alert("Informe os Grupos e Questões!");
			return;
		}
		QuestionarioService questionarioService = (QuestionarioService) ServiceLocator.getInstance().getService(QuestionarioService.class, null);
		try{
   			questionarioService.updateOrdemGrupos(questionario);

    		if (questionario.getReload().equalsIgnoreCase("true")){
    			document.executeScript("abre(" + questionario.getIdQuestionario() + ")");
    		}
    		document.alert(UtilI18N.internacionaliza(request, "MSG05"));
	    }catch(Exception e){
	        document.alert(e.getMessage());
	        document.executeScript("JANELA_AGUARDE_MENU.hide()");
	    }
	}
	public void atualizarNomeGrupo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		QuestionarioDTO questionario = (QuestionarioDTO) document.getBean();
		Usuario user = WebUtilQuestionario.getUsuario(request);
		if (user == null){
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			return;
		}
		questionario.setIdEmpresa(user.getIdEmpresa());

		QuestionarioService questionarioService = (QuestionarioService) ServiceLocator.getInstance().getService(QuestionarioService.class, null);
		try{
			if (questionario != null && questionario.getIdGrupoQuestionario() != null) {
				questionarioService.updateNomeGrupo(questionario);

				//document.executeScript("abrirQuestPosOrder()");
				//document.alert("Registro alterado com Sucesso!");
			}
			document.alert("Registro alterado com Sucesso!");
		}catch(Exception e){
			document.alert(e.getMessage());
		}
		document.executeScript("JANELA_AGUARDE_MENU.hide()");
	}
	public void exportarQuestionario(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		QuestionarioDTO questionario = (QuestionarioDTO) document.getBean();
		Usuario user = WebUtilQuestionario.getUsuario(request);
		if (user == null){
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			return;
		}
		questionario.setIdEmpresa(user.getIdEmpresa());

		Collection colGruposQuestao = WebUtil.deserializeCollectionFromRequest(GrupoQuestionarioDTO.class, "colQuestionariosSerialize", request);
		questionario.setColGrupos(colGruposQuestao);

		if (colGruposQuestao == null){
			colGruposQuestao = new ArrayList();
		}
		for(Iterator it = colGruposQuestao.iterator(); it.hasNext();){
			GrupoQuestionarioDTO grupo = (GrupoQuestionarioDTO) it.next();
			Collection col = WebUtil.deserializeCollectionFromString(QuestaoQuestionarioDTO.class, grupo.getSerializeQuestoesGrupo());

			if (col != null){
				for(Iterator itOp = col.iterator(); itOp.hasNext();){
					QuestaoQuestionarioDTO questao = (QuestaoQuestionarioDTO)itOp.next();

					if (questao.getSerializeOpcoesResposta() != null){
						Collection colOpcoes = WebUtil.deserializeCollectionFromString(OpcaoRespostaQuestionarioDTO.class, questao.getSerializeOpcoesResposta());
                        if (colOpcoes != null) {
                            for(Iterator itOpcao = colOpcoes.iterator(); itOpcao.hasNext();){
                                OpcaoRespostaQuestionarioDTO opcaoResposta = (OpcaoRespostaQuestionarioDTO)itOpcao.next();
                                if (opcaoResposta.getExibeComplemento().equalsIgnoreCase("S") && opcaoResposta.getSerializeQuestaoComplemento() != null){
                                    QuestaoQuestionarioDTO questaoComplementoDto = (QuestaoQuestionarioDTO) WebUtil.deserializeObject(QuestaoQuestionarioDTO.class, opcaoResposta.getSerializeQuestaoComplemento());
                                    Collection colOpcoesComp = WebUtil.deserializeCollectionFromString(OpcaoRespostaQuestionarioDTO.class, questaoComplementoDto.getSerializeOpcoesResposta());
                                    questaoComplementoDto.setColOpcoesResposta(colOpcoesComp);
                                    opcaoResposta.setQuestaoComplementoDto(questaoComplementoDto);
                                }
                            }
                        }
						questao.setColOpcoesResposta(colOpcoes);
					}

					if (questao.getSerializeQuestoesAgrupadas() != null){
						Collection colQuestoesAgrupadas = WebUtil.deserializeCollectionFromString(QuestaoQuestionarioDTO.class, questao.getSerializeQuestoesAgrupadas());
                        questao.setColQuestoesAgrupadas(colQuestoesAgrupadas);
                        if (colQuestoesAgrupadas != null){
                            for(Iterator itOpAgrup = colQuestoesAgrupadas.iterator(); itOpAgrup.hasNext();){
                                QuestaoQuestionarioDTO questaoAgrup = (QuestaoQuestionarioDTO)itOpAgrup.next();

                                if (questaoAgrup.getSerializeOpcoesResposta() != null){
                                    Collection colOpcoesAgrup = WebUtil.deserializeCollectionFromString(OpcaoRespostaQuestionarioDTO.class, questaoAgrup.getSerializeOpcoesResposta());
                                    questaoAgrup.setColOpcoesResposta(colOpcoesAgrup);
                                }
                            }
                        }
                        if (questao.getSerializeCabecalhosLinha() != null){
                            Collection colCabecalhos = WebUtil.deserializeCollectionFromString(QuestaoQuestionarioDTO.class, questao.getSerializeCabecalhosLinha());
                            questao.setColCabecalhosLinha(colCabecalhos);
                        }
                        if (questao.getSerializeCabecalhosColuna() != null){
                            Collection colCabecalhos = WebUtil.deserializeCollectionFromString(QuestaoQuestionarioDTO.class, questao.getSerializeCabecalhosColuna());
                            questao.setColCabecalhosColuna(colCabecalhos);
                        }
					}
				}
			}

			grupo.setColQuestoes(col);
		}

		questionario.setIdQuestionario(null);
		questionario.setIdQuestionarioOrigem(null);

		String diretorioExport = request.getSession().getServletContext().getRealPath("/");
		String diretorioRelativoExport = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/";

		diretorioExport = diretorioExport + "exportXML";
		diretorioRelativoExport = diretorioRelativoExport + "exportXML";
		File f = new File(diretorioExport);
		if (!f.exists()){
			f.mkdirs();
		}

		String name = UtilStrings.generateNomeBusca(questionario.getNomeQuestionario());
		name = name + ".citform";
		FileOutputStream fOut = new FileOutputStream(diretorioExport + "/" + name);

		XStream x = new XStream(new DomDriver("ISO-8859-1"));
		x.omitField(GrupoQuestionarioDTO.class, "serializeQuestoesGrupo");
		x.omitField(GrupoQuestionarioDTO.class, "infoGrupo");
		x.omitField(QuestionarioDTO.class, "acao");
		x.omitField(QuestionarioDTO.class, "reload");
		x.omitField(QuestaoQuestionarioDTO.class, "serializeQuestoesAgrupadas");
		x.omitField(QuestaoQuestionarioDTO.class, "serializeOpcoesResposta");
		x.omitField(QuestaoQuestionarioDTO.class, "serializeCabecalhosColuna");
		x.omitField(QuestaoQuestionarioDTO.class, "serializeCabecalhosLinha");
		x.omitField(OpcaoRespostaQuestionarioDTO.class, "serializeQuestaoComplemento");
		x.toXML(questionario, fOut);

		fOut.close();

		document.executeScript("JANELA_AGUARDE_MENU.hide()");
		document.executeScript("window.open('" + diretorioRelativoExport + "/" + name + "')");
	}
	public void gravar(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		QuestionarioDTO questionario = (QuestionarioDTO) document.getBean();
		Usuario user = WebUtilQuestionario.getUsuario(request);
		if (user == null){
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			return;
		}
		questionario.setIdEmpresa(user.getIdEmpresa());

		Collection colGruposQuestao = WebUtil.deserializeCollectionFromRequest(GrupoQuestionarioDTO.class, "colQuestionariosSerialize", request);
		questionario.setColGrupos(colGruposQuestao);

		if (colGruposQuestao == null || colGruposQuestao.size() == 0){
			document.alert(UtilI18N.internacionaliza(request, "questionario.grupoQuestao"));
			return;
		}

		for(Iterator it = colGruposQuestao.iterator(); it.hasNext();){
			GrupoQuestionarioDTO grupo = (GrupoQuestionarioDTO) it.next();
			Collection col = WebUtil.deserializeCollectionFromString(QuestaoQuestionarioDTO.class, grupo.getSerializeQuestoesGrupo());

			if (col != null){
				for(Iterator itOp = col.iterator(); itOp.hasNext();){
					QuestaoQuestionarioDTO questao = (QuestaoQuestionarioDTO)itOp.next();

					if (questao.getSerializeOpcoesResposta() != null){
						Collection colOpcoes = WebUtil.deserializeCollectionFromString(OpcaoRespostaQuestionarioDTO.class, questao.getSerializeOpcoesResposta());
						if (colOpcoes != null) {
                            for(Iterator itOpcao = colOpcoes.iterator(); itOpcao.hasNext();){
                                OpcaoRespostaQuestionarioDTO opcaoResposta = (OpcaoRespostaQuestionarioDTO)itOpcao.next();
                                if (opcaoResposta.getExibeComplemento().equalsIgnoreCase("S") && opcaoResposta.getSerializeQuestaoComplemento() != null){
                                    QuestaoQuestionarioDTO questaoComplementoDto = (QuestaoQuestionarioDTO) WebUtil.deserializeObject(QuestaoQuestionarioDTO.class, opcaoResposta.getSerializeQuestaoComplemento());
                                    Collection colOpcoesComp = WebUtil.deserializeCollectionFromString(OpcaoRespostaQuestionarioDTO.class, questaoComplementoDto.getSerializeOpcoesResposta());
                                    questaoComplementoDto.setColOpcoesResposta(colOpcoesComp);
                                    opcaoResposta.setQuestaoComplementoDto(questaoComplementoDto);
                                }
                            }
						}
						questao.setColOpcoesResposta(colOpcoes);
					}

					if (questao.getSerializeQuestoesAgrupadas() != null){
						Collection colQuestoesAgrupadas = WebUtil.deserializeCollectionFromString(QuestaoQuestionarioDTO.class, questao.getSerializeQuestoesAgrupadas());
                        questao.setColQuestoesAgrupadas(colQuestoesAgrupadas);
                        if (colQuestoesAgrupadas != null){
                            for(Iterator itOpAgrup = colQuestoesAgrupadas.iterator(); itOpAgrup.hasNext();){
                                QuestaoQuestionarioDTO questaoAgrup = (QuestaoQuestionarioDTO)itOpAgrup.next();

                                if (questaoAgrup.getSerializeOpcoesResposta() != null){
                                    Collection colOpcoesAgrup = WebUtil.deserializeCollectionFromString(OpcaoRespostaQuestionarioDTO.class, questaoAgrup.getSerializeOpcoesResposta());
                                    questaoAgrup.setColOpcoesResposta(colOpcoesAgrup);
                                }
                            }
                        }
                        if (questao.getSerializeCabecalhosLinha() != null){
                            Collection colCabecalhos = WebUtil.deserializeCollectionFromString(QuestaoQuestionarioDTO.class, questao.getSerializeCabecalhosLinha());
                            questao.setColCabecalhosLinha(colCabecalhos);
                        }
                        if (questao.getSerializeCabecalhosColuna() != null){
                            Collection colCabecalhos = WebUtil.deserializeCollectionFromString(QuestaoQuestionarioDTO.class, questao.getSerializeCabecalhosColuna());
                            questao.setColCabecalhosColuna(colCabecalhos);
                        }
					}
				}
			}

			grupo.setColQuestoes(col);
		}

		QuestionarioService questionarioService = (QuestionarioService) ServiceLocator.getInstance().getService(QuestionarioService.class, null);
		try{
    		if (questionario.getIdQuestionario() == null || questionario.getIdQuestionario().intValue() == 0){
    			questionario = (QuestionarioDTO) questionarioService.create(questionario);

    			document.executeScript("$('idQuestionario').value = " + questionario.getIdQuestionario());
    		}else{
    			questionarioService.update(questionario);
    		}

    		if (questionario.getReload().equalsIgnoreCase("true")){
    			HTMLForm form = document.getForm("form");
    			form.clear();
    			form.setValues(questionario);
    			document.executeScript("abre(" + questionario.getIdQuestionario() + ")");
    		}
    		document.alert(UtilI18N.internacionaliza(request, "questionario.questionarioGravado"));

	    }catch(Exception e){
	        document.alert(e.getMessage());
	        document.executeScript("JANELA_AGUARDE_MENU.hide()");
	    }
	}

	public void listar(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		QuestionarioDTO questionario = (QuestionarioDTO) document.getBean();
		Usuario user = WebUtilQuestionario.getUsuario(request);
		if (user == null){
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			return;
		}
		questionario.setIdEmpresa(user.getIdEmpresa());
		QuestionarioService questionarioService = (QuestionarioService) ServiceLocator.getInstance().getService(QuestionarioService.class, null);
		Collection colQuestionariosEmpresa = questionarioService.listByIdEmpresa(user.getIdEmpresa());

		HTMLSelect lstQuestionarios = document.getSelectById("lstQuestionarios");
		lstQuestionarios.removeAllOptions();
		lstQuestionarios.addOptions(colQuestionariosEmpresa, "idQuestionario", "nomeQuestionarioAndCodigo", null);
	}

	public void listarQuestionarios(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		QuestionarioDTO questionario = (QuestionarioDTO) document.getBean();
		Usuario user = WebUtilQuestionario.getUsuario(request);
		if (user == null){
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			return;
		}
		questionario.setIdEmpresa(user.getIdEmpresa());
		QuestionarioService questionarioService = (QuestionarioService) ServiceLocator.getInstance().getService(QuestionarioService.class, null);
		Collection colQuestionariosEmpresa = questionarioService.listByIdEmpresa(user.getIdEmpresa());

		HTMLSelect lstQuestionarios = document.getSelectById("idQuestionarioCopiarGrupo");
		lstQuestionarios.removeAllOptions();
		lstQuestionarios.addOption("0", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		lstQuestionarios.addOptions(colQuestionariosEmpresa, "idQuestionario", "nomeQuestionario", null);
	}

	public void listarGruposQuestionarios(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		QuestionarioDTO questionario = (QuestionarioDTO) document.getBean();
		Usuario user = WebUtilQuestionario.getUsuario(request);
		if (user == null){
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			return;
		}
		questionario.setIdEmpresa(user.getIdEmpresa());
		GrupoQuestionarioService grupoQuestionarioService = (GrupoQuestionarioService) ServiceLocator.getInstance().getService(GrupoQuestionarioService.class, null);
		Collection colGruposQuestionariosEmpresa = grupoQuestionarioService.listByIdQuestionario(questionario.getIdQuestionario());

		HTMLSelect lstQuestionarios = document.getSelectById("idGrupoQuestionarioCopiarGrupo");
		lstQuestionarios.removeAllOptions();
		lstQuestionarios.addOptions(colGruposQuestionariosEmpresa, "idGrupoQuestionario", "nomeGrupoQuestionario", null);
	}

	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		QuestionarioDTO questionario = (QuestionarioDTO) document.getBean();
		QuestionarioService questService = (QuestionarioService) ServiceLocator.getInstance().getService(QuestionarioService.class, null);
		questionario = (QuestionarioDTO) questService.restore(questionario);

		HTMLTextBox nomeQuestionario = document.getTextBoxById("nomeQuestionario");
		nomeQuestionario.setValue(questionario.getNomeQuestionario());

		request.setAttribute("grupos", questionario.getColGrupos());

		document.executeScript("$('idQuestionario').value = '" + questionario.getIdQuestionario() + "'");
		document.executeScript("HTMLUtils.setValue('idCategoriaQuestionarioAux', '" + questionario.getIdCategoriaQuestionario() + "')");
		document.executeScript("document.form.nomeQuestionario.value = '" + questionario.getNomeQuestionario() + "'");
		document.executeScript("document.form.idQuestionarioOrigem.value = '" + questionario.getIdQuestionarioOrigem() + "'");
		document.executeScript("document.form.idCategoriaQuestionario.value = '" + questionario.getIdCategoriaQuestionario() + "'");
		document.executeScript("document.form.ativo.value = '" + questionario.getAtivo() + "'");

		document.setTitle("N.o: <b>" + questionario.getIdQuestionario() + "</b>");

		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(questionario);

		//document.alert("Registro recuperado !");
	}
	public void importar(Collection fileItems, DocumentHTML document, HttpServletRequest request) throws Exception{
		QuestionarioDTO questionario = null;
		FileItem fi;
		if (!fileItems.isEmpty()){
			Iterator it = fileItems.iterator();
			File arquivo;
			while(it.hasNext()){
				fi = (FileItem)it.next();

				XStream x = new XStream(new DomDriver("ISO-8859-1"));

				//System.out.println("Dados importados: " + fi.get().toString());

				String str = new String(fi.get(), "ISO-8859-1");
				questionario = (QuestionarioDTO) x.fromXML(str);
			}
		}

		if(questionario != null){
			questionario.setNomeQuestionario("<"+UtilI18N.internacionaliza(request, "questionario.importadoAlterarNome")+">");
		}

		HTMLTextBox nomeQuestionario = document.getTextBoxById("nomeQuestionario");
		if(questionario != null){
			nomeQuestionario.setValue(questionario.getNomeQuestionario());
			request.setAttribute("grupos", questionario.getColGrupos());
		}

		document.executeScript("$('idQuestionario').value = ''");

		if(questionario != null){
			document.executeScript("HTMLUtils.setValue('idCategoriaQuestionarioAux', '" + questionario.getIdCategoriaQuestionario() + "')");
			document.executeScript("document.form.nomeQuestionario.value = '" + questionario.getNomeQuestionario() + "'");
			document.executeScript("document.form.idQuestionarioOrigem.value = ''");
			document.executeScript("document.form.idCategoriaQuestionario.value = '" + questionario.getIdCategoriaQuestionario() + "'");
			document.executeScript("document.form.ativo.value = '" + questionario.getAtivo() + "'");
		}
		document.setTitle("N.o: <b>"+UtilI18N.internacionaliza(request, "questionario.importado") +"</b>");
		document.alert(UtilI18N.internacionaliza(request, "questionario.questionarioImportado"));
	}

	public void copiarGrupoQuestionario(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		QuestionarioDTO questionario = (QuestionarioDTO) document.getBean();
		Usuario user = WebUtilQuestionario.getUsuario(request);
		if (user == null){
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			return;
		}

		questionario.setIdEmpresa(user.getIdEmpresa());
		QuestionarioService questionarioService = (QuestionarioService) ServiceLocator.getInstance().getService(QuestionarioService.class, null);
		questionarioService.copyGroup(questionario);

		document.executeScript("window.location = '" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") + "/pages/questionario/questionario.load?acao=restore&idQuestionario=" + questionario.getIdQuestionarioCopiar() + "'");
	}

}
