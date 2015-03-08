package br.com.centralit.citcorpore.metainfo.complementos;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citcorpore.bean.ClienteDTO;
import br.com.centralit.citcorpore.bean.ContratoHistoricoDTO;
import br.com.centralit.citcorpore.bean.FornecedorDTO;
import br.com.centralit.citcorpore.bean.MoedaDTO;
import br.com.centralit.citcorpore.integracao.ClienteDao;
import br.com.centralit.citcorpore.integracao.ContratoHistoricoDao;
import br.com.centralit.citcorpore.integracao.FornecedorDao;
import br.com.centralit.citcorpore.integracao.MoedaDao;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilFormatacao;
import br.com.citframework.util.UtilHTML;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

public class ComplementoContratos_MostrarHistAudit {
	public void execute(HttpServletRequest request, HttpServletResponse response){
		PrintWriter out = null;
		try{
			out = response.getWriter();
			
			String IDCONTRATO_STR = request.getParameter("IDCONTRATO");
			int IDCONTRATO = 0;
			if (IDCONTRATO_STR != null){
				try{
					IDCONTRATO = Integer.parseInt(IDCONTRATO_STR);					
				}catch(Exception e){
				}
			}
			
			String strTable = "<table width='100%'>";
			strTable += "<tr>";
			strTable += "<td style='border:1px solid black'>";
				strTable += "&nbsp;";
			strTable += "</td>";			
			strTable += "<td style='border:1px solid black'>";
				strTable += "<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "contrato.cliente")) + "</b>";
			strTable += "</td>";
			strTable += "<td style='border:1px solid black'>";
				strTable += "<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "contrato.fornecedor")) + "</b>";
			strTable += "</td>";	
			strTable += "<td style='border:1px solid black'>";
				strTable += "<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "contrato.tipo")) + "</b>";
			strTable += "</td>";
			strTable += "<td style='border:1px solid black'>";
				strTable += "<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "contrato.numero")) + "</b>";
			strTable += "</td>";	
			strTable += "<td style='border:1px solid black'>";
				strTable += "<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "contrato.datacontrato")) + "</b>";
			strTable += "</td>";	
			strTable += "<td style='border:1px solid black'>";
				strTable += "<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "contrato.situacao")) + "</b>";
			strTable += "</td>";	
			strTable += "<td style='border:1px solid black'>";
				strTable += "<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "contrato.datafimcontrato")) + "</b>";
			strTable += "</td>";	
			strTable += "<td style='border:1px solid black'>";
				strTable += "<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "contrato.modificadopor")) + "</b>";
			strTable += "</td>";	
			strTable += "<td style='border:1px solid black'>";
				strTable += "<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "contrato.modificadoem")) + "</b>";
			strTable += "</td>";			
			strTable += "</tr>";	
			if (IDCONTRATO > 0){
				ContratoHistoricoDao contratoHistoricoDao = new ContratoHistoricoDao();
				Collection col = contratoHistoricoDao.findByIdContratoOrderHist(IDCONTRATO);
				ClienteDao clienteDao = new ClienteDao();
				FornecedorDao fornecedorDao = new FornecedorDao();
				MoedaDao moedaDao = new MoedaDao();
				if (col != null){
					for (Iterator it = col.iterator(); it.hasNext();){
						ContratoHistoricoDTO contratoHistoricoDTO = (ContratoHistoricoDTO)it.next();
						String nomeCliente = "";
						String nomeFornecedor = "";
						ClienteDTO clienteDto = new ClienteDTO();
						clienteDto.setIdCliente(contratoHistoricoDTO.getIdCliente());
						clienteDto = (ClienteDTO) clienteDao.restore(clienteDto);
						if (clienteDto != null){
							nomeCliente = clienteDto.getNomeRazaoSocial();
						}
						FornecedorDTO fornecedorDTO = new FornecedorDTO();
						fornecedorDTO.setIdFornecedor(contratoHistoricoDTO.getIdFornecedor());
						fornecedorDTO = (FornecedorDTO) fornecedorDao.restore(fornecedorDTO);
						if (fornecedorDTO != null){
							nomeFornecedor = fornecedorDTO.getRazaoSocial();
						}
						
						strTable += "<tr>";
						strTable += "<td style='border:1px solid black'>";
							strTable += "<img id='img_trHISTCONT_" + contratoHistoricoDTO.getIdContrato_Hist() + "' src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/mais.jpg' border='0' onclick=\"abreFechaMaisMenos(this, 'trHISTCONT_" + contratoHistoricoDTO.getIdContrato_Hist() + "')\"/>";
						strTable += "</td>";			
						strTable += "<td style='border:1px solid black'>";
							strTable += "" + UtilHTML.encodeHTML(nomeCliente) + "";
						strTable += "</td>";
						strTable += "<td style='border:1px solid black'>";
							strTable += "" + UtilHTML.encodeHTML(nomeFornecedor) + "";
						strTable += "</td>";	
						strTable += "<td style='border:1px solid black'>";
						String tipo = contratoHistoricoDTO.getTipo();
						if (contratoHistoricoDTO.getTipo().equalsIgnoreCase("A")){
							tipo = UtilI18N.internacionaliza(request, "contrato.tipo.ano");
						}
						if (contratoHistoricoDTO.getTipo().equalsIgnoreCase("C")){
							tipo = UtilI18N.internacionaliza(request, "contrato.tipo.cliente");
						}
						if (contratoHistoricoDTO.getTipo().equalsIgnoreCase("U")){
							tipo = UtilI18N.internacionaliza(request, "contrato.tipo.terceiro");
						}						
						strTable += "" + UtilHTML.encodeHTML(tipo) + "";
						strTable += "</td>";
						strTable += "<td style='border:1px solid black'>";
							strTable += "" + UtilStrings.nullToVazio(contratoHistoricoDTO.getNumero()) + "";
						strTable += "</td>";	
						strTable += "<td style='border:1px solid black'>";
							if (contratoHistoricoDTO.getDataContrato() != null){
								strTable += "" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, contratoHistoricoDTO.getDataContrato(), WebUtil.getLanguage(request)) + "";
							}else{
								strTable += "&nbsp;";
							}
						strTable += "</td>";
						strTable += "<td style='border:1px solid black'>";
						String situacao = contratoHistoricoDTO.getSituacao();
						if (situacao.equalsIgnoreCase("A")){
							situacao = UtilI18N.internacionaliza(request, "sla.contrato.ativo");
						}else if (situacao.equalsIgnoreCase("F")){
							situacao = UtilI18N.internacionaliza(request, "sla.contrato.finalizado");
						}else if (situacao.equalsIgnoreCase("C")){
							situacao = UtilI18N.internacionaliza(request, "sla.contrato.cancelado");
						}else if (situacao.equalsIgnoreCase("P")){
							situacao = UtilI18N.internacionaliza(request, "sla.contrato.paralisado");
						}					
						strTable += "" + UtilHTML.encodeHTML(situacao) + "";
						strTable += "</td>";		
						strTable += "<td style='border:1px solid black'>";
							if (contratoHistoricoDTO.getDataFimContrato() != null){
								strTable += "" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, contratoHistoricoDTO.getDataFimContrato(), WebUtil.getLanguage(request)) + "";
							}else{
								strTable += "&nbsp;";
							}
						strTable += "</td>";						
						strTable += "<td style='border:1px solid black'>";
							strTable += "" + UtilHTML.encodeHTML(contratoHistoricoDTO.getModificadoPor()) + "";
						strTable += "</td>";	
						strTable += "<td style='border:1px solid black'>";
							strTable += "" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, contratoHistoricoDTO.getModificadoEm(), WebUtil.getLanguage(request)) + "";
						strTable += "</td>";	
						strTable += "</tr>";	
						
						strTable += "<tr>";
						strTable += "<td colspan='10' style='border:1px solid black'>";
							strTable += "<div id='trHISTCONT_" + contratoHistoricoDTO.getIdContrato_Hist() + "' style='display:none'>";
								String nomeMoeda = "";
								if (contratoHistoricoDTO.getIdMoeda() != null){
									MoedaDTO moedaDto = new MoedaDTO();
									moedaDto.setIdMoeda(contratoHistoricoDTO.getIdMoeda());
									moedaDto = (MoedaDTO) moedaDao.restore(moedaDto);
									if (moedaDto != null){
										nomeMoeda = moedaDto.getNomeMoeda();
									}
								}
								
								strTable += "<table width='100%'>";
								strTable += "<tr>";
								strTable += "<td colspan='2'>";
									strTable += "<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "contrato.objeto")) + "</b>";
								strTable += "</td>";
								strTable += "</tr>";
								strTable += "<tr>";
								strTable += "<td colspan='2'>";
									strTable += "" + UtilHTML.encodeHTML(contratoHistoricoDTO.getObjeto()) + "";
								strTable += "</td>";								
								strTable += "</tr>";	
								strTable += "<tr>";
								strTable += "<td colspan='2'>";
									strTable += "<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "contrato.moeda")) + "</b>";
								strTable += "</td>";
								strTable += "</tr>";
								strTable += "<tr>";
								strTable += "<td colspan='2'>";
									strTable += "" + UtilHTML.encodeHTML(nomeMoeda) + "";
								strTable += "</td>";								
								strTable += "</tr>";
								strTable += "<tr>";
								strTable += "<td>";
									strTable += "<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "contrato.cotacaomoeda")) + "</b>";
								strTable += "</td>";
								strTable += "<td>";
									if (contratoHistoricoDTO.getCotacaoMoeda() != null){
										strTable += UtilFormatacao.formatDouble(contratoHistoricoDTO.getCotacaoMoeda(),2);
									}else{
										strTable += "&nbsp;";
									}
								strTable += "</td>";								
								strTable += "</tr>";
								strTable += "<tr>";
								strTable += "<td>";
									strTable += "<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "contrato.valorestimado")) + "</b>";
								strTable += "</td>";
								strTable += "<td>";
									if (contratoHistoricoDTO.getValorEstimado() != null){
										strTable += UtilFormatacao.formatDouble(contratoHistoricoDTO.getValorEstimado(),2);
									}else{
										strTable += "&nbsp;";
									}
								strTable += "</td>";								
								strTable += "</tr>";	
								strTable += "<tr>";
								strTable += "<td colspan='2'>";
									strTable += "<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "contrato.dadoslog")) + "</b>";
								strTable += "</td>";
								strTable += "</tr>";	
								strTable += "<tr>";
								strTable += "<td colspan='2'>";
									if (contratoHistoricoDTO.getConteudodados() != null){
										/*
										 * Rodrigo Pecci Acorse - 02/12/2013 16h30 - #126028
										 * A string estava retornando erro de encoding. Foi feita a conversão para corrigir a acentuação. 
										 */
										String conteudo = contratoHistoricoDTO.getConteudodados().replaceAll("\n", "<br>");
										strTable += UtilHTML.encodeHTML(conteudo);
									}else{
										strTable += "&nbsp;";
									}
								strTable += "</td>";								
								strTable += "</tr>";								
								strTable += "</table>";
							strTable += "</div>";
						strTable += "</td>";								
						strTable += "</tr>";
					}
				}
			}
			strTable += "</table>";
			
			out.write(strTable);			
		}catch(Exception e){
		}
		response.setContentType("text/html; charset=UTF-8");
	}
}
