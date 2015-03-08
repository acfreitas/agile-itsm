package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.LinhaBaseProjetoDTO;
import br.com.centralit.citcorpore.bean.MarcoPagamentoPrjDTO;
import br.com.centralit.citcorpore.bean.PagamentoProjetoDTO;
import br.com.centralit.citcorpore.bean.TarefaLinhaBaseProjetoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.LinhaBaseProjetoService;
import br.com.centralit.citcorpore.negocio.MarcoPagamentoPrjService;
import br.com.centralit.citcorpore.negocio.PagamentoProjetoService;
import br.com.centralit.citcorpore.negocio.ProjetoService;
import br.com.centralit.citcorpore.negocio.RecursoTarefaLinBaseProjService;
import br.com.centralit.citcorpore.negocio.TarefaLinhaBaseProjetoService;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilFormatacao;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

public class PagamentoProjeto extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ProjetoService projetoService = (ProjetoService) ServiceLocator.getInstance().getService(ProjetoService.class, null);
		Collection colProjetos = projetoService.list();
		HTMLSelect idProjeto = document.getSelectById("idProjetoAux");
		idProjeto.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		if (colProjetos != null){
			idProjeto.addOptions(colProjetos, "idProjeto", "nomeProjeto", null);
		}		
	}
	
	@Override
	public Class getBeanClass() {
		return PagamentoProjetoDTO.class;
	}
	
	public void save(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoexpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}		
		PagamentoProjetoDTO pagamentoProjetoDTO = (PagamentoProjetoDTO)document.getBean();
		if (pagamentoProjetoDTO.getDataPagamento() != null){
			if (pagamentoProjetoDTO.getDataPagamento().after(UtilDatas.getDataAtual())){
				document.alert(UtilI18N.internacionaliza(request, "carteiraTrabalho.dataNaoMaiorAtual"));
				return;
			}
		}		
		PagamentoProjetoService pagamentoProjetoService = (PagamentoProjetoService) ServiceLocator.getInstance().getService(PagamentoProjetoService.class, null);	
		String hora = UtilDatas.getHoraHHMM(UtilDatas.getDataHoraAtual()).replaceAll(":", "");
		java.sql.Date data = UtilDatas.getDataAtual();
		pagamentoProjetoDTO.setUsuarioUltAlteracao(usuario.getNomeUsuario());
		pagamentoProjetoDTO.setDataUltAlteracao(data);
		pagamentoProjetoDTO.setHoraUltAlteracao(hora);
		
		if (pagamentoProjetoDTO.getIdTarefasParaPagamento() == null){
			document.alert(UtilI18N.internacionaliza(request, "pagamentoProjeto.informeTarefaPagar"));
			return;
		}
		if (pagamentoProjetoDTO.getValorGlosa() == null){
			pagamentoProjetoDTO.setValorGlosa(new Double(0));
		}
		if (pagamentoProjetoDTO.getIdPagamentoProjeto() == null){
			pagamentoProjetoService.create(pagamentoProjetoDTO);
		}else{
			pagamentoProjetoService.update(pagamentoProjetoDTO);
		}
		document.alert(UtilI18N.internacionaliza(request, "pagamentoProjeto.pagamentoGravado"));
		document.executeScript("refreshInfo()");
	}
	
	public void atualizaPagamento(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoexpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}		
		PagamentoProjetoDTO pagamentoProjetoDTO = (PagamentoProjetoDTO)document.getBean();
		PagamentoProjetoService pagamentoProjetoService = (PagamentoProjetoService) ServiceLocator.getInstance().getService(PagamentoProjetoService.class, null);	
		String hora = UtilDatas.getHoraHHMM(UtilDatas.getDataHoraAtual()).replaceAll(":", "");
		java.sql.Date data = UtilDatas.getDataAtual();
		pagamentoProjetoDTO.setUsuarioUltAlteracao(usuario.getNomeUsuario());
		pagamentoProjetoDTO.setDataUltAlteracao(data);
		pagamentoProjetoDTO.setHoraUltAlteracao(hora);		
		pagamentoProjetoService.updateSituacao(pagamentoProjetoDTO);
		document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		document.executeScript("refreshInfo()");		
	}
	
	public void getMarcosFinanceiros(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoexpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		PagamentoProjetoDTO pagamentoProjetoDTO = (PagamentoProjetoDTO)document.getBean();
		MarcoPagamentoPrjService marcoPagamentoPrjService = (MarcoPagamentoPrjService)ServiceLocator.getInstance().getService(MarcoPagamentoPrjService.class, null);
		Collection col = marcoPagamentoPrjService.findByIdProjeto(pagamentoProjetoDTO.getIdProjeto());
		HTMLSelect idMarcoPagamentoPrj = document.getSelectById("idMarcoPagamentoPrj");
		idMarcoPagamentoPrj.removeAllOptions();
		if (col != null){
			idMarcoPagamentoPrj.addOption("", "--");
			for (Iterator it = col.iterator(); it.hasNext();){
				MarcoPagamentoPrjDTO marcoPagamentoPrjDTO = (MarcoPagamentoPrjDTO)it.next();
				String str = marcoPagamentoPrjDTO.getNomeMarcoPag();
				str = str.replaceAll("\'", "").replaceAll("\"", "");
				idMarcoPagamentoPrj.addOption("" + marcoPagamentoPrjDTO.getIdMarcoPagamentoPrj(), str);
			}
		}
	}
	public void setaTarefasMarcoFinanceiro(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoexpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		LinhaBaseProjetoService linhaBaseProjetoService = (LinhaBaseProjetoService) ServiceLocator.getInstance().getService(LinhaBaseProjetoService.class, null);
		TarefaLinhaBaseProjetoService tarefaLinhaBaseProjetoService = (TarefaLinhaBaseProjetoService) ServiceLocator.getInstance().getService(TarefaLinhaBaseProjetoService.class, null);
		PagamentoProjetoDTO pagamentoProjetoDTO = (PagamentoProjetoDTO)document.getBean();
		if (pagamentoProjetoDTO.getIdMarcoPagamentoPrj() == null){
			return;
		}
		Collection colLinhasBase = linhaBaseProjetoService.findByIdProjeto(pagamentoProjetoDTO.getIdProjeto());
		LinhaBaseProjetoDTO linhaBaseProjetoDTO = null;
		if (colLinhasBase != null){
			for (Iterator it = colLinhasBase.iterator(); it.hasNext();){
				linhaBaseProjetoDTO = (LinhaBaseProjetoDTO) it.next();
				break;
			}
		}
		String[] values = null;
		String strValues = "";
		Collection colTarefasLnBase = null;
		if(linhaBaseProjetoDTO != null){
			colTarefasLnBase = tarefaLinhaBaseProjetoService.findByIdLinhaBaseProjeto(linhaBaseProjetoDTO.getIdLinhaBaseProjeto());
		}
		if (colTarefasLnBase != null && colTarefasLnBase.size() > 0){
			for (Iterator it = colTarefasLnBase.iterator(); it.hasNext();){
				TarefaLinhaBaseProjetoDTO tarefaLinhaBaseProjetoDTO = (TarefaLinhaBaseProjetoDTO)it.next();
				if (tarefaLinhaBaseProjetoDTO.getIdMarcoPagamentoPrj() != null){
					if (tarefaLinhaBaseProjetoDTO.getIdMarcoPagamentoPrj().intValue() == pagamentoProjetoDTO.getIdMarcoPagamentoPrj().intValue()){
						if (!strValues.trim().equalsIgnoreCase("")){
							strValues += ",";
						}
						strValues += "" + tarefaLinhaBaseProjetoDTO.getIdTarefaLinhaBaseProjeto();
					}
				}
			}
		}
		if (strValues != null && !strValues.trim().equalsIgnoreCase("")){
			strValues += ",";
			values = strValues.split(",");
			document.getCheckboxById("idTarefasParaPagamento").setValue(values);
		}
	}
	/* Desenvolvedor: Pedro Lino - Data: 28/10/2013 - Horário: 14:20 - ID Citsmart: 120948 - 
	* Motivo/Comentário: Tabela com dificil visualização/ alterado layout: retirado algumas bordas */
	
	/* Desenvolvedor: Pedro Lino - Data: 29/10/2013 - Horário: 09:45 - ID Citsmart: 120948 - 
	* Motivo/Comentário: Mesclado coluna pagamento e situação pois estavam redundantes */
	public void getInformacoes(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PagamentoProjetoDTO pagamentoProjetoDTO = (PagamentoProjetoDTO)document.getBean();
		ProjetoService projetoService = (ProjetoService) ServiceLocator.getInstance().getService(ProjetoService.class, null);
		PagamentoProjetoService pagamentoProjetoService = (PagamentoProjetoService) ServiceLocator.getInstance().getService(PagamentoProjetoService.class, null);	
		LinhaBaseProjetoService linhaBaseProjetoService = (LinhaBaseProjetoService) ServiceLocator.getInstance().getService(LinhaBaseProjetoService.class, null);
		TarefaLinhaBaseProjetoService tarefaLinhaBaseProjetoService = (TarefaLinhaBaseProjetoService) ServiceLocator.getInstance().getService(TarefaLinhaBaseProjetoService.class, null);
		RecursoTarefaLinBaseProjService recursoTarefaLinBaseProjService = (RecursoTarefaLinBaseProjService)ServiceLocator.getInstance().getService(RecursoTarefaLinBaseProjService.class, null);
		if (pagamentoProjetoDTO.getIdProjeto() == null){
			document.alert(UtilI18N.internacionaliza(request, "pagamentoProjeto.informeProjeto"));
			document.getElementById("divTarefasParaPagamento").setInnerHTML("");
			document.getElementById("divTarefasParaPagamentoVis").setInnerHTML("");
			document.getElementById("divPagamentosEfetuados").setInnerHTML("");
			return;
		}
		document.getElementById("divTarefasParaPagamento").setInnerHTML("");
		document.getElementById("divTarefasParaPagamentoVis").setInnerHTML("");
		document.getElementById("divPagamentosEfetuados").setInnerHTML("");
		
		StringBuilder strBufferPags =  new StringBuilder();
		strBufferPags.append("<table width='100%' class='table table-bordered table-striped'>");
		strBufferPags.append("<tr>");		
		strBufferPags.append("<td><b>"+UtilI18N.internacionaliza(request, "pagamentoProjeto.dataPagamento")+"</b></td>");
		strBufferPags.append("<td><b>"+UtilI18N.internacionaliza(request, "pagamentoProjeto.valorPagamento")+"</b></td>");
		strBufferPags.append("<td><b>"+UtilI18N.internacionaliza(request, "pagamentoProjeto.valorGlosa")+"</b></td>");
		strBufferPags.append("<td><b>"+UtilI18N.internacionaliza(request, "pagamentoProjeto.ultAlteracao")+"</b></td>");
		strBufferPags.append("<td><b>"+UtilI18N.internacionaliza(request, "pagamentoProjeto.usuario")+"</b></td>");
		/*strBufferPags.append("<td><b>"+UtilI18N.internacionaliza(request, "citcorpore.controleContrato.pagamento")+"</b></td>");*/
		strBufferPags.append("<td><b>"+UtilI18N.internacionaliza(request, "pagamentoProjeto.situacao")+"</b></td>");
		strBufferPags.append("</tr>");
		Collection colPags = pagamentoProjetoService.findByIdProjeto(pagamentoProjetoDTO.getIdProjeto());
		if (colPags != null && colPags.size() > 0){
			double valorPag = 0;
			double valorGlosa = 0;
			for (Iterator it = colPags.iterator(); it.hasNext();){
				PagamentoProjetoDTO pagamentoProjetoAux = (PagamentoProjetoDTO) it.next();
				strBufferPags.append("<tr>");		
				strBufferPags.append("<td>" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, pagamentoProjetoAux.getDataPagamento(), WebUtil.getLanguage(request)) + "</td>");
				strBufferPags.append("<td>" + UtilFormatacao.formatDouble(pagamentoProjetoAux.getValorPagamento(), 2) + "</td>");
				strBufferPags.append("<td>" + UtilFormatacao.formatDouble(pagamentoProjetoAux.getValorGlosa(), 2) + "</td>");
				strBufferPags.append("<td>" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, pagamentoProjetoAux.getDataUltAlteracao(), WebUtil.getLanguage(request)) + " " + pagamentoProjetoAux.getHoraUltAlteracao() + "</td>");
				strBufferPags.append("<td>" + pagamentoProjetoAux.getUsuarioUltAlteracao() + "</td>");
				if (pagamentoProjetoAux.getSituacao().equalsIgnoreCase("S")){
					//strBufferPags.append("<td><img src='" + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/interrog.gif' style='cursor:pointer' border='0' onclick='indicarPagamento(\"" + pagamentoProjetoAux.getIdPagamentoProjeto() + "\")'/></td>");
					strBufferPags.append("<td><img src='" + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/interrog.gif' style='cursor:pointer' border='0' onclick='indicarPagamento(\"" + pagamentoProjetoAux.getIdPagamentoProjeto() + "\")'/> "+UtilI18N.internacionaliza(request, "pagamentoProjeto.solicitado")+"</td>");
				}else{
					/*strBufferPags.append("<td><img src='" + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/accept.png' border='0'/></td>");*/
					strBufferPags.append("<td><img src='" + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/accept.png' border='0'/> "+UtilI18N.internacionaliza(request, "pagamentoProjeto.pago")+"</td>");
				}
				strBufferPags.append("</tr>");	
				
				valorPag = valorPag + pagamentoProjetoAux.getValorPagamento().doubleValue();
				valorGlosa = valorGlosa + pagamentoProjetoAux.getValorGlosa().doubleValue();
			}
			strBufferPags.append("<tr>");		
			strBufferPags.append("<td><b>"+UtilI18N.internacionaliza(request, "pagamentoProjeto.total")+"</b></td>");
			strBufferPags.append("<td><b>" + UtilFormatacao.formatDouble(valorPag, 2) + "</b></td>");
			strBufferPags.append("<td><b>" + UtilFormatacao.formatDouble(valorGlosa, 2) + "</b></td>");
			strBufferPags.append("<td>&nbsp;</td>");
			strBufferPags.append("<td>&nbsp;</td>");
			strBufferPags.append("<td>&nbsp;</td>");
			/*strBufferPags.append("<td>&nbsp;</td>");*/
			strBufferPags.append("</tr>");				
		}else{
			strBufferPags.append("<tr>");
			strBufferPags.append("<td  colspan='20'>"+UtilI18N.internacionaliza(request, "pagamentoProjeto.naoExistePagamento")+"</td>");
			strBufferPags.append("</tr>");
		}
		strBufferPags.append("</table>");
		document.getElementById("divPagamentosEfetuados").setInnerHTML(strBufferPags.toString());
		
		Collection colLinhasBase = linhaBaseProjetoService.findByIdProjeto(pagamentoProjetoDTO.getIdProjeto());
		LinhaBaseProjetoDTO linhaBaseProjetoDTO = null;
		if (colLinhasBase != null){
			for (Iterator it = colLinhasBase.iterator(); it.hasNext();){
				linhaBaseProjetoDTO = (LinhaBaseProjetoDTO) it.next();
				break;
			}
		}	
		StringBuilder strBuffer =  new StringBuilder();
		StringBuilder strBuffer2 =  new StringBuilder();
		strBuffer.append("<table width='100%' class='table table-bordered table-striped'>");
		strBuffer.append("<tr>");
		strBuffer.append("<td>&nbsp;</td>");
		strBuffer.append("<td><b>"+UtilI18N.internacionaliza(request, "pagamentoProjeto.nomeTarefa")+"</b></td>");
		strBuffer.append("<td><b>"+UtilI18N.internacionaliza(request, "pagamentoProjeto.inicio")+"</b></td>");
		strBuffer.append("<td><b>"+UtilI18N.internacionaliza(request, "pagamentoProjeto.fim")+"</b></td>");
		strBuffer.append("<td><b>"+UtilI18N.internacionaliza(request, "pagamentoProjeto.duracaoDias")+"</b></td>");
		strBuffer.append("<td><b>"+UtilI18N.internacionaliza(request, "pagamentoProjeto.status")+"</b></td>");
		strBuffer.append("<td><b>"+UtilI18N.internacionaliza(request, "pagamentoProjeto.porcTrabalho")+"</b></td>");
		strBuffer.append("<td><b>"+UtilI18N.internacionaliza(request, "pagamentoProjeto.custo")+"</b></td>");
		strBuffer.append("</tr>");
		
		strBuffer2.append(strBuffer.toString());
		if (linhaBaseProjetoDTO != null){
			Collection colTarefasLnBase = tarefaLinhaBaseProjetoService.findByIdLinhaBaseProjeto(linhaBaseProjetoDTO.getIdLinhaBaseProjeto());
			if (colTarefasLnBase != null){
				int i = 0;
				for (Iterator it = colTarefasLnBase.iterator(); it.hasNext();){
					TarefaLinhaBaseProjetoDTO tarefaLinhaBaseProjetoDTO = (TarefaLinhaBaseProjetoDTO)it.next();
					Collection colRecsVinc = recursoTarefaLinBaseProjService.findByIdTarefaLinhaBaseProjeto(tarefaLinhaBaseProjetoDTO.getIdTarefaLinhaBaseProjeto());
					
					strBuffer.append("<tr>");
					String nome = tarefaLinhaBaseProjetoDTO.getNomeTarefaNivelHTML();
					nome = UtilStrings.nullToVazio(nome).replaceAll("\"", "");
					nome = nome.replaceAll("\'", "");
					String code = tarefaLinhaBaseProjetoDTO.getCodeTarefa();
					code = UtilStrings.nullToVazio(code).replaceAll("\"", "");
					code = code.replaceAll("\'", "");	
					
					double progresso = 0;
					if (tarefaLinhaBaseProjetoDTO.getProgresso() != null){
						progresso = tarefaLinhaBaseProjetoDTO.getProgresso();
					}
					
					if (progresso >= 100){
						nome = "<font color='green'>" + nome + "</font>";
					}else{
						if (tarefaLinhaBaseProjetoDTO.getDataFim().before(UtilDatas.getDataAtual())){
							nome = "<font color='red'>" + nome + "</font>";
						}
					}
					
					boolean temRecursos = false;
					if (colRecsVinc != null && colRecsVinc.size() > 0){
						temRecursos = true;
					}
					
					if (tarefaLinhaBaseProjetoDTO.getIdPagamentoProjeto() == null && progresso >= 100 && temRecursos){
						strBuffer.append("<td><input type='checkbox' name='idTarefasParaPagamento' id='idTarefasParaPagamento' value='" + tarefaLinhaBaseProjetoDTO.getIdTarefaLinhaBaseProjeto() + "'/></td>");
						strBuffer2.append("<td>&nbsp;</td>");
					}else{
						if (tarefaLinhaBaseProjetoDTO.getIdPagamentoProjeto() != null){
							strBuffer.append("<td><img src='" + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/accept.png' title='"+UtilI18N.internacionaliza(request, "pagamentoProjeto.pagamentoEfetuado")+"'border='0'/></td>");
							strBuffer2.append("<td><img src='" + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/accept.png' title='"+UtilI18N.internacionaliza(request, "pagamentoProjeto.pagamentoEfetuado")+"'border='0'/></td>");
						}else{
							strBuffer.append("<td>&nbsp;</td>");
							strBuffer2.append("<td>&nbsp;</td>");
						}
					}
					
					strBuffer.append("<td>" + nome + "</td>");
					strBuffer2.append("<td>" + nome + "</td>");
					
					int nivel = 0;
					long start = 0;
					long end = 0;
					double duracao = 0;
					double custoPerfil = 0;
					if (tarefaLinhaBaseProjetoDTO.getNivel() != null){
						nivel = tarefaLinhaBaseProjetoDTO.getNivel();
					}
					String status = UtilI18N.internacionaliza(request, "pagamentoProjeto.emExecucao");
					if (tarefaLinhaBaseProjetoDTO.getSituacao().trim().equalsIgnoreCase(TarefaLinhaBaseProjetoDTO.PRONTO)){
						status = UtilI18N.internacionaliza(request, "pagamentoProjeto.pronta");
					}	
					if (tarefaLinhaBaseProjetoDTO.getSituacao().trim().equalsIgnoreCase(TarefaLinhaBaseProjetoDTO.FALHA)){
						status = UtilI18N.internacionaliza(request, "pagamentoProjeto.falha");
					}
					if (tarefaLinhaBaseProjetoDTO.getSituacao().trim().equalsIgnoreCase(TarefaLinhaBaseProjetoDTO.SUSPENSA)){
						status = UtilI18N.internacionaliza(request, "pagamentoProjeto.suspensa");
					}
					if (tarefaLinhaBaseProjetoDTO.getSituacao().trim().equalsIgnoreCase(TarefaLinhaBaseProjetoDTO.SEMDEFINICAO)){
						status = UtilI18N.internacionaliza(request, "pagamentoProjeto.semDefinicao");
					}
					if (tarefaLinhaBaseProjetoDTO.getDataInicio() != null){
						start = tarefaLinhaBaseProjetoDTO.getDataInicio().getTime();
					}
					if (tarefaLinhaBaseProjetoDTO.getDataFim() != null){
						end = tarefaLinhaBaseProjetoDTO.getDataFim().getTime();
					}	
					if (tarefaLinhaBaseProjetoDTO.getDuracaoHora() != null){
						end = tarefaLinhaBaseProjetoDTO.getDataFim().getTime();
					}		
					if (tarefaLinhaBaseProjetoDTO.getDuracaoHora() != null){
						duracao = tarefaLinhaBaseProjetoDTO.getDuracaoHora();
					}
//					//if (tarefaLinhaBaseProjetoDTO.getEsforcoPorOS() != null && Double.parseDouble(tarefaLinhaBaseProjetoDTO.getEsforcoPorOS()) == 0 && tarefaLinhaBaseProjetoDTO.getCustoPerfil() != null){
//						custoPerfil = tarefaLinhaBaseProjetoDTO.getCustoPerfil();
//					} else {
//						custoPerfil = tarefaLinhaBaseProjetoDTO.getCusto();
//					}
					if (tarefaLinhaBaseProjetoDTO.getCustoPerfil() != null){
						custoPerfil = tarefaLinhaBaseProjetoDTO.getCustoPerfil();
					}
					
					strBuffer.append("<td>" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, tarefaLinhaBaseProjetoDTO.getDataInicio(), WebUtil.getLanguage(request)) + "</td>");
					strBuffer.append("<td>" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, tarefaLinhaBaseProjetoDTO.getDataFim(), WebUtil.getLanguage(request)) + "</td>");
					strBuffer.append("<td>" + UtilFormatacao.formatDouble(duracao,2) + "</td>");
					strBuffer.append("<td>" + status + "</td>");
					strBuffer.append("<td>" + UtilFormatacao.formatDouble(progresso, 2) + "</td>");
					strBuffer.append("<td>" + UtilFormatacao.formatDouble(custoPerfil, 2) + "</td>");
					
					strBuffer2.append("<td>" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, tarefaLinhaBaseProjetoDTO.getDataInicio(), WebUtil.getLanguage(request)) + "</td>");
					strBuffer2.append("<td>" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, tarefaLinhaBaseProjetoDTO.getDataFim(), WebUtil.getLanguage(request)) + "</td>");
					strBuffer2.append("<td>" + UtilFormatacao.formatDouble(duracao,2) + "</td>");
					strBuffer2.append("<td>" + status + "</td>");
					strBuffer2.append("<td>" + UtilFormatacao.formatDouble(progresso, 2) + "</td>");
					strBuffer2.append("<td>" + UtilFormatacao.formatDouble(custoPerfil, 2) + "</td>");		
					
					/*
					Collection colRecsVinc = recursoTarefaLinBaseProjService.findByIdTarefaLinhaBaseProjeto(tarefaLinhaBaseProjetoDTO.getIdTarefaLinhaBaseProjeto());
					String strRecsVinc = "";
					if (colRecsVinc != null){
						for (Iterator itRec = colRecsVinc.iterator(); itRec.hasNext();){
							RecursoTarefaLinBaseProjDTO recursoTarefaLinBaseProjDTO = (RecursoTarefaLinBaseProjDTO)itRec.next();
							if (!strRecsVinc.trim().equalsIgnoreCase("")){
								strRecsVinc += ",";
							}
							strRecsVinc += "{\"id\":\"" + recursoTarefaLinBaseProjDTO.getIdRecursoTarefaLinBaseProj() + "\",";
							strRecsVinc += "\"resourceId\":\"" + recursoTarefaLinBaseProjDTO.getIdEmpregado() + "\",";
							strRecsVinc += "\"roleId\":\"" + recursoTarefaLinBaseProjDTO.getIdPerfilContrato() + "\",";
							strRecsVinc += "\"effort\":" + geraTempoMiliSegundos(recursoTarefaLinBaseProjDTO.getTempoAloc()) + "}";
							
						}
					}
					Collection colProdsVinc = produtoTarefaLinBaseProjService.findByIdTarefaLinhaBaseProjeto(tarefaLinhaBaseProjetoDTO.getIdTarefaLinhaBaseProjeto());
					String strProdsVinc = "";
					if (colProdsVinc != null){
						for (Iterator itRec = colProdsVinc.iterator(); itRec.hasNext();){
							ProdutoTarefaLinBaseProjDTO produtoTarefaLinBaseProjDTO = (ProdutoTarefaLinBaseProjDTO)itRec.next();
							if (!strProdsVinc.trim().equalsIgnoreCase("")){
								strProdsVinc += ",";
							}
							strProdsVinc += "{\"id\":\"" + produtoTarefaLinBaseProjDTO.getIdTarefaLinhaBaseProjeto() + "_" + produtoTarefaLinBaseProjDTO.getIdProdutoContrato() + "\",\"productId\":\"" + produtoTarefaLinBaseProjDTO.getIdProdutoContrato() + "\"}";
						}
					}					
					*/
					i++;
					strBuffer.append("</tr>");
					strBuffer2.append("</tr>");
				}
			}
			strBuffer.append("</table>");
			strBuffer2.append("</table>");
			document.getElementById("divTarefasParaPagamento").setInnerHTML(strBuffer.toString());
			document.getElementById("divTarefasParaPagamentoVis").setInnerHTML(strBuffer2.toString());
		}		
	}	

}
