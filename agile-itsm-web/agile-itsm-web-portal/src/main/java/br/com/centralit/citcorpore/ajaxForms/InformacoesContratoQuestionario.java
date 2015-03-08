package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.ContratoQuestionariosDTO;
import br.com.centralit.citcorpore.bean.InformacoesContratoConfigDTO;
import br.com.centralit.citcorpore.bean.InformacoesContratoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ContratoQuestionariosService;
import br.com.centralit.citcorpore.negocio.InformacoesContratoConfigService;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;

public class InformacoesContratoQuestionario extends AjaxFormAction {

	public Class getBeanClass() {
		return InformacoesContratoDTO.class;
	}

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		InformacoesContratoDTO informacoesContratoDTO = (InformacoesContratoDTO) document.getBean();
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null){
			document.alert("Sessão expirada! Favor efetuar logon novamente!");
			return;
		}
		
		if (informacoesContratoDTO.getSubForm() == null){
			informacoesContratoDTO.setSubForm("N");
		}
		
		boolean bloquearParaNovo = false;
		
		request.getSession().setAttribute("TEMP_LISTA_CERTIFICADO_DIGITAL", null);
		
		ContratoQuestionariosService imagemHistoricoService = (ContratoQuestionariosService) ServiceLocator.getInstance().getService(ContratoQuestionariosService.class, null);
		//ProfissionalService profissionalService = (ProfissionalService) ServiceLocator.getInstance().getService(ProfissionalService.class, null);
		InformacoesContratoConfigService informacoesContratoConfigService = (InformacoesContratoConfigService) ServiceLocator.getInstance().getService(InformacoesContratoConfigService.class, null);
		
		List lstProntCfg = (List)informacoesContratoConfigService.findByNome(informacoesContratoDTO.getAba());
		InformacoesContratoConfigDTO informacoesContratoConfigDTO = null;
		if (lstProntCfg != null && lstProntCfg.size() > 0){
			informacoesContratoConfigDTO = (InformacoesContratoConfigDTO)lstProntCfg.get(0);
		}
		if (informacoesContratoConfigDTO == null){
			informacoesContratoConfigDTO = new InformacoesContratoConfigDTO();
			informacoesContratoConfigDTO.setSegurancaUnidade("N");
		}
		
		Collection colQuestHist = imagemHistoricoService.listByIdContratoAndAba(informacoesContratoDTO.getIdContrato(), informacoesContratoDTO.getAba());
		boolean	acessoInclusaoAlteracaoPermitido = true;
		
		String strTable = "";
		
		boolean verificaBloqueioParaNovo = false;
		if (informacoesContratoConfigDTO.getValidacoes() != null && !informacoesContratoConfigDTO.getValidacoes().trim().equalsIgnoreCase("")){
			String strAux = informacoesContratoConfigDTO.getValidacoes() + ",";
			String[] str = strAux.split(",");
			informacoesContratoConfigDTO.setValidacoesAux(str);
		}		
		if (informacoesContratoConfigDTO.getValidacoesAux() != null){
			for(int i = 0; i < informacoesContratoConfigDTO.getValidacoesAux().length; i++){
				if (informacoesContratoConfigDTO.getValidacoesAux()[i].equalsIgnoreCase("002")){
					verificaBloqueioParaNovo = true;
				}
			}
		}
		
		strTable = "<table width=\"100%\">";
		
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
			strTable += "<td  width=\"69%\" class=\"linhaSubtituloGrid\">";
				strTable += "Profissional";
			strTable += "</td>";			
	
		strTable += "</tr>";
		
		if (colQuestHist != null){
		    Integer seq = colQuestHist.size();
			for(Iterator it = colQuestHist.iterator(); it.hasNext();){
				ContratoQuestionariosDTO contratoQuestDTO = (ContratoQuestionariosDTO)it.next();
				
				strTable += "<tr>";
				
					strTable += "<td class='tdPontilhada'>";
					if (informacoesContratoDTO.getSubForm().equalsIgnoreCase("S")){
						if ("F".equalsIgnoreCase(contratoQuestDTO.getSituacao())){
							strTable += "<img src=\"" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/produtos/citsaude/imagens/documentoOK.gif\" border=\"0\" onclick=\"chamaEdicaoQuestionario(" + contratoQuestDTO.getIdContrato() + "," + contratoQuestDTO.getIdQuestionario() + ",0, " + contratoQuestDTO.getIdContratoQuestionario() + ", true, 'S', '" + informacoesContratoDTO.getAba() + "', '" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, contratoQuestDTO.getDataQuestionario(), WebUtil.getLanguage(request)) + "')\" style=\"cursor:pointer\" >";
						}else{
							if (acessoInclusaoAlteracaoPermitido){ //TEM ACESSO DE ALTERACAO
								strTable += "<img src=\"" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/produtos/citsaude/imagens/agendado.gif\" border=\"0\" onclick=\"chamaEdicaoQuestionario(" + contratoQuestDTO.getIdContrato() + "," + contratoQuestDTO.getIdQuestionario() + ",0, " + contratoQuestDTO.getIdContratoQuestionario() + ", false, 'S', '" + informacoesContratoDTO.getAba() + "', '" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, contratoQuestDTO.getDataQuestionario(), WebUtil.getLanguage(request)) + "')\" style=\"cursor:pointer\" >";
							}else{//SEM ACESSO DE ALTERACAO
								strTable += "<img src=\"" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/produtos/citsaude/imagens/exclamacao.gif\" border=\"0\" onclick=\"chamaEdicaoQuestionario(" + contratoQuestDTO.getIdContrato() + "," + contratoQuestDTO.getIdQuestionario() + ",0, " + contratoQuestDTO.getIdContratoQuestionario() + ", true, 'S', '" + informacoesContratoDTO.getAba() + "', '" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, contratoQuestDTO.getDataQuestionario(), WebUtil.getLanguage(request)) + "')\" style=\"cursor:pointer\" title=\"Sem permissão de alteração\">";
							}
						}
					}else{
						if ("F".equalsIgnoreCase(contratoQuestDTO.getSituacao())){
							strTable += "<img src=\"" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/produtos/citsaude/imagens/documentoOK.gif\" border=\"0\" onclick=\"chamaEdicaoQuestionario(" + contratoQuestDTO.getIdContrato() + "," + contratoQuestDTO.getIdQuestionario() + ",0, " + contratoQuestDTO.getIdContratoQuestionario() + ", true, 'N', '', '" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, contratoQuestDTO.getDataQuestionario(), WebUtil.getLanguage(request)) + "')\" style=\"cursor:pointer\" >";
						}else{
							if (verificaBloqueioParaNovo){
								bloquearParaNovo = true;
							}
							if (acessoInclusaoAlteracaoPermitido){ //TEM ACESSO DE ALTERACAO
								strTable += "<img src=\"" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/produtos/citsaude/imagens/agendado.gif\" border=\"0\" onclick=\"chamaEdicaoQuestionario(" + contratoQuestDTO.getIdContrato() + "," + contratoQuestDTO.getIdQuestionario() + ",0, " + contratoQuestDTO.getIdContratoQuestionario() + ", false, 'N', '', '" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, contratoQuestDTO.getDataQuestionario(), WebUtil.getLanguage(request)) + "')\" style=\"cursor:pointer\" >";
							}else{//SEM ACESSO DE ALTERACAO
								strTable += "<img src=\"" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/produtos/citsaude/imagens/exclamacao.gif\" border=\"0\" onclick=\"chamaEdicaoQuestionario(" + contratoQuestDTO.getIdContrato() + "," + contratoQuestDTO.getIdQuestionario() + ",0, " + contratoQuestDTO.getIdContratoQuestionario() + ", true, 'N', '', '" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, contratoQuestDTO.getDataQuestionario(), WebUtil.getLanguage(request)) + "')\" style=\"cursor:pointer\" title=\"Sem permissão de alteração\">";
							}
						}						
					}
					if (informacoesContratoConfigDTO != null){
						if (informacoesContratoConfigDTO.getFuncAdicionalAposGravacao() != null &&
								!informacoesContratoConfigDTO.getFuncAdicionalAposGravacao().trim().equalsIgnoreCase("")){
							if (informacoesContratoConfigDTO.getChamarFuncAddHistorico() != null &&
									informacoesContratoConfigDTO.getChamarFuncAddHistorico().equalsIgnoreCase("S")){
								if (informacoesContratoConfigDTO.getIconeFuncHistorico() == null ||
										informacoesContratoConfigDTO.getIconeFuncHistorico().trim().equalsIgnoreCase("")){
									informacoesContratoConfigDTO.setIconeFuncHistorico("complementa.gif");
								}
								if (contratoQuestDTO.getSituacaoComplemento() == null){
									contratoQuestDTO.setSituacaoComplemento("");
								}
								String icone = informacoesContratoConfigDTO.getIconeFuncHistorico();
								if ("F".equalsIgnoreCase(contratoQuestDTO.getSituacaoComplemento())){
									if (informacoesContratoConfigDTO.getIconeFuncHistoricoFinal() != null &&
											!informacoesContratoConfigDTO.getIconeFuncHistoricoFinal().trim().equalsIgnoreCase("")){
										icone = informacoesContratoConfigDTO.getIconeFuncHistoricoFinal();
									}
								}									
								
								strTable += "&nbsp;";
								strTable += "<img src=\"" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/produtos/citsaude/imagens/iconesProntuario/" + icone + "\" border=\"0\" onclick=\"chamaEdicaoComplemento(" + contratoQuestDTO.getIdContrato() + "," + contratoQuestDTO.getIdQuestionario() + ",0, " + contratoQuestDTO.getIdContratoQuestionario() + ", true, 'N', '" + informacoesContratoDTO.getAba() + "')\" style=\"cursor:pointer\" >";
								strTable += "&nbsp;";
								strTable += "&nbsp;";
							}
						}
					}
					
					if (informacoesContratoDTO.getSubForm().equalsIgnoreCase("S")){
						strTable += "<img src=\"" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/produtos/citsaude/imagens/impressora.gif\" border=\"0\" onclick=\"imprimeQuestionario(" + contratoQuestDTO.getIdContrato() + "," + contratoQuestDTO.getIdQuestionario() + ",0, " + contratoQuestDTO.getIdContratoQuestionario() + ", false, 'S')\" style=\"cursor:pointer\" >";
					}else{
						strTable += "<img src=\"" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/produtos/citsaude/imagens/impressora.gif\" border=\"0\" onclick=\"imprimeQuestionario(" + contratoQuestDTO.getIdContrato() + "," + contratoQuestDTO.getIdQuestionario() + ",0, " + contratoQuestDTO.getIdContratoQuestionario() + ", false)\" style=\"cursor:pointer\" >";
					}
					
					/*
					if (asoDto != null){
						strTable += "&nbsp;";
						strTable += "<img src=\"" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/produtos/citsaude/imagens/aceitaLanc.gif\" border=\"0\" onclick=\"abreASO(" + asoDto.getIdASO() + ")\" style=\"cursor:pointer\" >";
					}
					*/
					strTable += "</td>";
					
					strTable += "<td class='tdPontilhada' style=\"text-align:center\">"+seq.toString()+"</td>";
					strTable += "<td class='tdPontilhada'>";
					strTable += UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, contratoQuestDTO.getDataQuestionario(), WebUtil.getLanguage(request));
					strTable += "</td>";
					strTable += "<td class='tdPontilhada'>";
					if (contratoQuestDTO.getProfissional() != null){
						strTable += contratoQuestDTO.getProfissional();
					}else{
						strTable += "&nbsp;--";			
					}
					strTable += "</td>";			
		
				strTable += "</tr>";				
				seq = seq - 1;
			}
		}
		strTable += "</table>";
		
		if (!acessoInclusaoAlteracaoPermitido){
			document.getElementById("divBotaoNovoProntEleQuest").setVisible(false);
		}
		if (bloquearParaNovo){
			document.getElementById("divBotaoNovoProntEleQuest").setVisible(false);
		}
		if (informacoesContratoDTO.getSubForm().equalsIgnoreCase("S")){
			document.getElementById("divQuestSubForm").setInnerHTML(strTable);
			//document.executeScript("document.getElementById('divQuestSubForm').innerHTML = '" + strTable + "'");		
		}else{
			document.getElementById("divQuest").setInnerHTML(strTable);
			//document.executeScript("document.getElementById('divQuest').innerHTML = '" + strTable + "'");		
		}
	}
}
