package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.AcordoNivelServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.FaturaApuracaoANSDTO;
import br.com.centralit.citcorpore.bean.FaturaDTO;
import br.com.centralit.citcorpore.bean.FormulaDTO;
import br.com.centralit.citcorpore.bean.GlosaOSDTO;
import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.bean.MoedaDTO;
import br.com.centralit.citcorpore.bean.OSDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoGrupoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.metainfo.script.ScriptRhinoJSExecute;
import br.com.centralit.citcorpore.metainfo.util.RuntimeScript;
import br.com.centralit.citcorpore.negocio.AcordoNivelServicoContratoService;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.FaturaApuracaoANSService;
import br.com.centralit.citcorpore.negocio.FaturaService;
import br.com.centralit.citcorpore.negocio.FormulaService;
import br.com.centralit.citcorpore.negocio.GlosaOSService;
import br.com.centralit.citcorpore.negocio.GrupoEmpregadoService;
import br.com.centralit.citcorpore.negocio.MoedaService;
import br.com.centralit.citcorpore.negocio.OSService;
import br.com.centralit.citcorpore.negocio.PerfilAcessoGrupoService;
import br.com.centralit.citcorpore.negocio.PerfilAcessoSituacaoFaturaService;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.FormulasUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilFormatacao;
import br.com.citframework.util.UtilHTML;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class Fatura extends AjaxFormAction {

	@Override
	public Class getBeanClass() {
		return FaturaDTO.class;
	}

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			return;
		}
		FaturaDTO faturaDTO = (FaturaDTO) document.getBean();
		AcordoNivelServicoContratoService acordoNivelServicoContratoService = (AcordoNivelServicoContratoService) ServiceLocator.getInstance()
				.getService(AcordoNivelServicoContratoService.class, null);

		Collection col = acordoNivelServicoContratoService.findByIdContrato(faturaDTO.getIdContrato());
		if (col != null) {
			for (Iterator it = col.iterator(); it.hasNext();) {
				AcordoNivelServicoContratoDTO acordoNivelServicoContratoDTO = (AcordoNivelServicoContratoDTO) it.next();
				if (acordoNivelServicoContratoDTO.getDataFim() == null || acordoNivelServicoContratoDTO.getDataFim().after(UtilDatas.getDataAtual())) {
					document.executeScript("GRID_ITENS.addRow()");
					document.executeScript("seqSelecionada = NumberUtil.zerosAEsquerda(GRID_ITENS.getMaxIndex(),5)");
					String strDet = acordoNivelServicoContratoDTO.getDescricaoAcordo();
					if (strDet != null) {
						strDet = strDet.replaceAll("'", "");
					} else {
						strDet = "";
					}
					String strCompl = UtilI18N.internacionaliza(request, "visao.valorLimite") + ": " + UtilFormatacao.formatDouble(acordoNivelServicoContratoDTO.getValorLimite(), 2) + " "
							+ UtilStrings.nullToVazio(acordoNivelServicoContratoDTO.getUnidadeValorLimite());
					String detAcordo = acordoNivelServicoContratoDTO.getDetalhamentoAcordo();
					if (detAcordo != null) {
						detAcordo = detAcordo.replaceAll("'", "");
					} else {
						detAcordo = "";
					}
					detAcordo = br.com.citframework.util.WebUtil.codificaEnter(detAcordo);
					document.executeScript("setaRestoreDesc('" + strDet + "','" + strCompl + "', '" + detAcordo + "', '" + acordoNivelServicoContratoDTO.getIdAcordoNivelServicoContrato() + "')");
				}
			}
		}

		PerfilAcessoSituacaoFaturaService perfilAcessoSituacaoFaturaService = (PerfilAcessoSituacaoFaturaService) ServiceLocator.getInstance()
				.getService(PerfilAcessoSituacaoFaturaService.class, null);
		GrupoEmpregadoService grupoEmpregadoService = (GrupoEmpregadoService) ServiceLocator.getInstance().getService(GrupoEmpregadoService.class, null);
		PerfilAcessoGrupoService perfilAcessoGrupoService = (PerfilAcessoGrupoService) ServiceLocator.getInstance().getService(PerfilAcessoGrupoService.class, null);

		Collection colSituacoesPermitidasFinal = new ArrayList();
		Collection<GrupoEmpregadoDTO> colGruposUsuario = null;

		if (usuario != null) {
			// Retorna as permissões do usuário
			colSituacoesPermitidasFinal = perfilAcessoSituacaoFaturaService.getSituacoesFaturaPermitidasByUsuario(usuario);

			// Retorna os grupos do usuário
			colGruposUsuario = grupoEmpregadoService.findByIdEmpregado(usuario.getIdEmpregado());
			if (colGruposUsuario != null) {
				Collection<Integer> colSituacoesPermitidasTemp = null;
				for (GrupoEmpregadoDTO grupoEmpregadoDTO : colGruposUsuario) {
					PerfilAcessoGrupoDTO perfilAcessoGrupoDTO = new PerfilAcessoGrupoDTO();
					perfilAcessoGrupoDTO.setIdGrupo(grupoEmpregadoDTO.getIdGrupo());
					perfilAcessoGrupoDTO = perfilAcessoGrupoService.listByIdGrupo(perfilAcessoGrupoDTO);
					colSituacoesPermitidasTemp = perfilAcessoSituacaoFaturaService.getSituacoesFaturaPermitidasByGrupo(perfilAcessoGrupoDTO);
					// Percorre temporário e verifica se já existe na lista final, se não existir adiciona
					if (colSituacoesPermitidasTemp != null) {
						for (Integer object : colSituacoesPermitidasTemp) {
							if (colSituacoesPermitidasFinal == null) {
								colSituacoesPermitidasFinal = new ArrayList();
								colSituacoesPermitidasFinal.add(object);
							} else if (!colSituacoesPermitidasFinal.contains(object)) {
								colSituacoesPermitidasFinal.add(object);
							}
						}
					}
				}
			}
		}

		generateComboSituacoes(document, request, colSituacoesPermitidasFinal);

		document.focusInFirstActivateField(null);

		if (faturaDTO.getIdFatura() != null) {
			restore(document, request, response);
		}
	}

	public void generateComboSituacoes(DocumentHTML document, HttpServletRequest request, Collection colSituacoesPermitidas) throws Exception {
		document.getSelectById("situacaoFatura").addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		if (colSituacoesPermitidas == null) {
			return;
		}
		for (Iterator it = colSituacoesPermitidas.iterator(); it.hasNext();) {
			Integer situacao = (Integer) it.next();
			FaturaDTO faturaDTO = new FaturaDTO();
			faturaDTO.setSituacaoFatura(situacao.toString());
			document.getSelectById("situacaoFatura").addOption("" + situacao.toString(), faturaDTO.getDescricaoSituacaoFatura(request));
		}
	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		FaturaDTO faturaDTO = (FaturaDTO) document.getBean();
		FaturaService faturaService = (FaturaService) ServiceLocator.getInstance().getService(FaturaService.class, null);
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		Collection colItens = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(FaturaApuracaoANSDTO.class, "colItens_Serialize", request);

		Integer idContrato = -1;
		try {
			idContrato = Integer.parseInt((String) request.getSession(true).getAttribute("NUMERO_CONTRATO_EDICAO"));
		} catch (Exception e) {
		}
		if (idContrato == -1) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.naoIdentificaContrato"));
			return;
		}

		faturaDTO.setIdContrato(idContrato);
		faturaDTO.setColItens(colItens);
		faturaDTO.setDataUltModificacao(UtilDatas.getDataAtual());
		boolean bAlterar = false;
		ContratoDTO contratoDto = new ContratoDTO();
		contratoDto.setIdContrato(idContrato);
		contratoDto = (ContratoDTO) contratoService.restore(contratoDto);
		faturaDTO.setValorCotacaoMoeda(contratoDto.getCotacaoMoeda());
		if (faturaDTO.getIdFatura() == null || faturaDTO.getIdFatura().intValue() == 0) {
			faturaDTO.setDataCriacao(UtilDatas.getDataAtual());
			faturaService.create(faturaDTO);
		} else {
			faturaService.update(faturaDTO);
			bAlterar = true;
		}
		HTMLForm form = document.getForm("form");
		form.clear();

		document.executeScript("GRID_ITENS.deleteAllRows()");

		document.alert(UtilI18N.internacionaliza(request, "periodica.registro_gravado_sucesso"));
		document.executeScript("parent.atualizaFaturas()");
		document.executeScript("parent.fecharVisaoFatura()");
	}

	public void updateSituacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		FaturaDTO faturaDTO = (FaturaDTO) document.getBean();
		if (faturaDTO.getIdFatura() == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.informeIdentificacaoFatura"));
			return;
		}
		if (faturaDTO.getSituacaoFatura() == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.informeSituacaoFatura"));
			return;
		}
		FaturaService faturaService = (FaturaService) ServiceLocator.getInstance().getService(FaturaService.class, null);

		faturaService.updateSituacao(faturaDTO.getIdFatura(), faturaDTO.getSituacaoFatura(), faturaDTO.getAprovacaoGestor(), faturaDTO.getAprovacaoFiscal());

		document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.situacaoAlteradaSucesso"));
		document.executeScript("parent.atualizaFaturas()");
		document.executeScript("parent.fecharVisaoFatura()");
	}

	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		FaturaDTO faturaDTO = (FaturaDTO) document.getBean();
		FaturaService faturaService = (FaturaService) ServiceLocator.getInstance().getService(FaturaService.class, null);
		FaturaApuracaoANSService faturaApuracaoANSService = (FaturaApuracaoANSService) ServiceLocator.getInstance().getService(FaturaApuracaoANSService.class, null);
		AcordoNivelServicoContratoService acordoNivelServicoContratoService = (AcordoNivelServicoContratoService) ServiceLocator.getInstance()
				.getService(AcordoNivelServicoContratoService.class, null);

		faturaDTO = (FaturaDTO) faturaService.restore(faturaDTO);

		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(faturaDTO);

		document.executeScript("GRID_ITENS.deleteAllRows()");

		if (faturaDTO != null) {
			int i = 0;
			Collection col = faturaApuracaoANSService.findByIdFatura(faturaDTO.getIdFatura());
			if (col != null) {
				for (Iterator it = col.iterator(); it.hasNext();) {
					i++;
					FaturaApuracaoANSDTO faturaApuracaoANSDTO = (FaturaApuracaoANSDTO) it.next();

					AcordoNivelServicoContratoDTO acordoNivelServicoContratoDTO = new AcordoNivelServicoContratoDTO();
					acordoNivelServicoContratoDTO.setIdAcordoNivelServicoContrato(faturaApuracaoANSDTO.getIdAcordoNivelServicoContrato());
					acordoNivelServicoContratoDTO = (AcordoNivelServicoContratoDTO) acordoNivelServicoContratoService.restore(acordoNivelServicoContratoDTO);

					document.executeScript("GRID_ITENS.addRow()");
					document.executeScript("seqSelecionada = NumberUtil.zerosAEsquerda(GRID_ITENS.getMaxIndex(),5)");
					String strValorApurado = UtilFormatacao.formatDouble(faturaApuracaoANSDTO.getValorApurado(), 2);
					String strPerc = UtilFormatacao.formatDouble(faturaApuracaoANSDTO.getPercentualGlosa(), 2);
					String strValorGlosa = UtilFormatacao.formatDouble(faturaApuracaoANSDTO.getValorGlosa(), 2);
					String strDet = faturaApuracaoANSDTO.getDetalhamento();
					String strDescricao = acordoNivelServicoContratoDTO.getDescricaoAcordo();
					if (strDet != null) {
						strDet = strDet.replaceAll("'", "");
					} else {
						strDet = "";
					}
					if (strDescricao != null) {
						strDescricao = strDescricao.replaceAll("'", "");
					} else {
						strDescricao = "";
					}

					String strCompl = "Valor limite: " + UtilFormatacao.formatDouble(acordoNivelServicoContratoDTO.getValorLimite(), 2) + " "
							+ UtilStrings.nullToVazio(acordoNivelServicoContratoDTO.getUnidadeValorLimite());

					String detAcordo = acordoNivelServicoContratoDTO.getDetalhamentoAcordo();
					if (detAcordo != null) {
						detAcordo = detAcordo.replaceAll("'", "");
						detAcordo = br.com.citframework.util.WebUtil.codificaEnter(detAcordo);
					}

					document.executeScript("setaRestoreItem(" + "'" + br.com.citframework.util.WebUtil.codificaEnter(strDescricao) + "'," + "'"
							+ br.com.citframework.util.WebUtil.codificaEnter(strDet) + "'," + "'" + strValorApurado + "'," + "'" + UtilStrings.nullToVazio(strPerc) + "'," + "'"
							+ UtilStrings.nullToVazio(strValorGlosa) + "', " + "'" + UtilStrings.nullToVazio(strCompl) + "', '" + UtilStrings.nullToVazio(detAcordo) + "', " + "'"
							+ acordoNivelServicoContratoDTO.getIdAcordoNivelServicoContrato() + "'" + ")");
				}
			}
		}

		document.executeScript("chamaAssociarOS()");

		if (faturaDTO != null) {
			if (faturaDTO.getSituacaoFatura() != null && !faturaDTO.getSituacaoFatura().equalsIgnoreCase(FaturaDTO.EM_CRIACAO)) {
				// Se não estiver em CRIAÇÃO, a tela fica bloqueada!
				form.lockForm();
				document.getElementById("btnAddListaOSFaturamento").setVisible(false);
				document.getElementById("pareceres").setVisible(true);
				document.getSelectById("situacaoFatura").setDisabled(false);
				document.getElementById("idFatura").setDisabled(false);
				document.getElementById("divBotaoGravar").setVisible(false);
				document.getElementById("divBotaoGravarSituacao").setVisible(true);
				document.getElementById("btnGravarSituacao").setDisabled(false);
				if (faturaDTO.getSituacaoFatura().equalsIgnoreCase(FaturaDTO.AGUARDANDO_APROVACAO)) {
					document.executeScript("HTMLUtils.unlockField(document.form.aprovacaoGestor)");
					document.executeScript("HTMLUtils.unlockField(document.form.aprovacaoFiscal)");
				}
			}
		}

		if (faturaDTO != null) {

			document.getElementById("valorReceberOS").setValue(UtilFormatacao.formatDouble(faturaDTO.getValorReceberOS(), 2));
		}

		// document.alert("Registro recuperado !");
	}

	public void listOSParaFaturamento(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// FaturaDTO faturaDTO = (FaturaDTO) document.getBean();
		Integer idContrato = -1;
		try {
			idContrato = Integer.parseInt((String) request.getSession(true).getAttribute("NUMERO_CONTRATO_EDICAO"));
		} catch (Exception e) {
		}
		if (idContrato == -1) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.naoIdentificaContrato"));
			return;
		}
		OSService oSService = (OSService) ServiceLocator.getInstance().getService(OSService.class, null);
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		MoedaService moedaService = (MoedaService) ServiceLocator.getInstance().getService(MoedaService.class, null);
		GlosaOSService glosaOSService = (GlosaOSService) ServiceLocator.getInstance().getService(GlosaOSService.class, null);
		Collection colOsNaoAssociadasFatura = oSService.listOSHomologadasENaoAssociadasFatura(idContrato);

		ContratoDTO contratoDto = new ContratoDTO();
		contratoDto.setIdContrato(idContrato);
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

		String strTable = "<table width='98%'>";
		strTable += "<tr>";
		strTable += "<td class='linhaSubtituloGrid'>";
		strTable += "&nbsp;";
		strTable += "</td>";
		strTable += "<td class='linhaSubtituloGrid'>";
		strTable += UtilI18N.internacionaliza(request, "agenda.numeroOS");
		strTable += "</td>";
		strTable += "<td class='linhaSubtituloGrid'>";
		strTable += UtilI18N.internacionaliza(request, "citcorpore.comum.servico");
		strTable += "</td>";
		strTable += "<td class='linhaSubtituloGrid'>";
		strTable += UtilI18N.internacionaliza(request, "citcorpore.comum.datainicio");
		strTable += "</td>";
		strTable += "<td class='linhaSubtituloGrid'>";
		strTable += UtilI18N.internacionaliza(request, "citcorpore.comum.datafim");
		strTable += "</td>";
		strTable += "<td class='linhaSubtituloGrid'>";
		strTable += UtilI18N.internacionaliza(request, "citcorpore.comum.situacao");
		strTable += "</td>";
		strTable += "<td class='linhaSubtituloGrid'>";
		strTable += UtilI18N.internacionaliza(request, "citcorpore.comum.custo") + nomeMoeda;
		strTable += "</td>";
		strTable += "<td class='linhaSubtituloGrid'>";
		strTable += UtilI18N.internacionaliza(request, "visao.glosa") + nomeMoeda;
		strTable += "</td>";
		strTable += "</tr>";
		if (colOsNaoAssociadasFatura != null && colOsNaoAssociadasFatura.size() > 0) {
			for (Iterator itOs = colOsNaoAssociadasFatura.iterator(); itOs.hasNext();) {
				OSDTO osDto = (OSDTO) itOs.next();
				strTable += "<tr>";
				strTable += "<td>";
				strTable += "<input type='checkbox' name='idOSFatura' value='" + osDto.getIdOS() + "'/>";
				strTable += "</td>";
				strTable += "<td>";
				strTable += osDto.getNumero() + "/" + osDto.getAno();
				strTable += "</td>";
				strTable += "<td>";
				strTable += UtilHTML.encodeHTML(osDto.getNomeServico());
				strTable += "</td>";
				strTable += "<td>";
				strTable += UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, osDto.getDataInicio(), WebUtil.getLanguage(request));
				strTable += "</td>";
				strTable += "<td>";
				strTable += UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, osDto.getDataFim(), WebUtil.getLanguage(request));
				strTable += "</td>";
				strTable += "<td>";
				strTable += UtilStrings.nullToVazio(osDto.getDescricaoSituacaoOS());
				strTable += "</td>";
				strTable += "<td>";
				if (osDto.getCustoOS() != null) {
					strTable += UtilFormatacao.formatDouble(osDto.getCustoOS(), 2);
				} else {
					strTable += "--";
				}
				strTable += "</td>";
				strTable += "<td>";
				Double valorGlosado = 0.0;
				// if (osDto.getGlosaOS() != null) {
				// valorGlosado = osDto.getGlosaOS().doubleValue();
				// }
				Collection colGlosasOS = glosaOSService.findByIdOs(osDto.getIdOS());
				if (colGlosasOS != null) {
					for (Iterator it = colGlosasOS.iterator(); it.hasNext();) {
						GlosaOSDTO glosaOSDTO = (GlosaOSDTO) it.next();
						if (glosaOSDTO.getCustoGlosa() != null) {
							valorGlosado = valorGlosado + glosaOSDTO.getCustoGlosa();
						}
					}
				}
				if (valorGlosado > 0) {
					strTable += UtilFormatacao.formatDouble(valorGlosado, 2);
				} else {
					strTable += "--";
				}
				strTable += "</td>";
				strTable += "</tr>";
			}
		} else {
			strTable += "<tr>";
			strTable += "<td colspan='20'>";
			strTable += UtilI18N.internacionaliza(request, "citcorpore.comum.naoAhOSHolologadasAssociarFatura");
			strTable += "</td>";
			strTable += "</tr>";
		}
		strTable += "</table>";

		document.getElementById("divOsSelecao").setInnerHTML(strTable);
	}

	public void associarOSParaFaturamento(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		FaturaDTO faturaDTO = (FaturaDTO) document.getBean();
		Integer idContrato = -1;
		try {
			idContrato = Integer.parseInt((String) request.getSession(true).getAttribute("NUMERO_CONTRATO_EDICAO"));
		} catch (Exception e) {
		}
		if (idContrato == -1) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.naoIdentificaContrato"));
			return;
		}
		OSService oSService = (OSService) ServiceLocator.getInstance().getService(OSService.class, null);
		FaturaService faturaService = (FaturaService) ServiceLocator.getInstance().getService(FaturaService.class, null);
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		MoedaService moedaService = (MoedaService) ServiceLocator.getInstance().getService(MoedaService.class, null);
		GlosaOSService glosaOSService = (GlosaOSService) ServiceLocator.getInstance().getService(GlosaOSService.class, null);
		Collection colOSs = null;
		if (faturaDTO.getIdOSFatura() != null) {
			colOSs = oSService.listOSByIds(idContrato, faturaDTO.getIdOSFatura());
		}
		if (faturaDTO.getIdFatura() != null) {
			Collection colOSJaAssociadass = oSService.listOSAssociadasFatura(faturaDTO.getIdFatura());
			if (colOSJaAssociadass != null) {
				if (colOSs == null) {
					colOSs = new ArrayList();
				}
				colOSs.addAll(colOSJaAssociadass);
			}
		}

		boolean permiteExclusaoOS = true;
		if (faturaDTO.getIdFatura() != null) {
			FaturaDTO faturaAux = (FaturaDTO) faturaService.restore(faturaDTO);
			if (faturaAux != null && faturaAux.getSituacaoFatura() != null && !faturaAux.getSituacaoFatura().equalsIgnoreCase(FaturaDTO.EM_CRIACAO)) {
				permiteExclusaoOS = false;
			}
		}

		ContratoDTO contratoDto = new ContratoDTO();
		contratoDto.setIdContrato(idContrato);
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

		String strTable = "<table width='98%'>";
		strTable += "<tr>";
		strTable += "<td class='linhaSubtituloGrid'>";
		if (permiteExclusaoOS) {
			strTable += "Ação";
		} else {
			strTable += "&nbsp;";
		}
		strTable += "</td>";
		strTable += "<td class='linhaSubtituloGrid'>";
		strTable += UtilI18N.internacionaliza(request, "agenda.numeroOS");
		strTable += "</td>";
		strTable += "<td class='linhaSubtituloGrid'>";
		strTable += UtilI18N.internacionaliza(request, "citcorpore.comum.servico");
		strTable += "</td>";
		strTable += "<td class='linhaSubtituloGrid'>";
		strTable += UtilI18N.internacionaliza(request, "citcorpore.comum.datainicio");
		strTable += "</td>";
		strTable += "<td class='linhaSubtituloGrid'>";
		strTable += UtilI18N.internacionaliza(request, "citcorpore.comum.datafim");
		strTable += "</td>";
		strTable += "<td class='linhaSubtituloGrid'>";
		strTable += UtilI18N.internacionaliza(request, "citcorpore.comum.situacao");
		strTable += "</td>";
		strTable += "<td class='linhaSubtituloGrid'>";
		strTable += UtilI18N.internacionaliza(request, "citcorpore.comum.custo") + nomeMoeda;
		strTable += "</td>";
		strTable += "<td class='linhaSubtituloGrid'>";
		strTable += UtilI18N.internacionaliza(request, "citcorpore.comum.executado") + nomeMoeda;
		strTable += "</td>";
		strTable += "<td class='linhaSubtituloGrid'>";
		strTable += UtilI18N.internacionaliza(request, "visao.glosa") + nomeMoeda;
		strTable += "</td>";
		strTable += "</tr>";
		if (colOSs != null && colOSs.size() > 0) {
			for (Iterator itOs = colOSs.iterator(); itOs.hasNext();) {
				OSDTO osDto = (OSDTO) itOs.next();
				if (faturaDTO.getIdOSExcluir() != null) {
					if (faturaDTO.getIdOSExcluir().intValue() == osDto.getIdOS().intValue()) {
						continue;
					}
				}
				strTable += "<tr>";
				strTable += "<td>";
				if (permiteExclusaoOS) {
					strTable += "<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/excluirPeq.gif' border='0' title='"
							+ UtilI18N.internacionaliza(request, "citcorpore.comum.excluiOSFatura") + "' onclick='retiraOSDaFatura(\"" + osDto.getIdOS() + "\")' style='cursor:pointer'/>";
				} else {
					strTable += "&nbsp;";
				}
				strTable += "</td>";
				strTable += "<td>";
				strTable += "<input type='hidden' name='idOSFatura' value='" + osDto.getIdOS() + "'/>";
				// strTable += osDto.getNumero() + "/" + osDto.getAno();
				strTable += osDto.getNumero();
				strTable += "</td>";
				strTable += "<td>";
				strTable += UtilHTML.encodeHTML(osDto.getNomeServico());
				strTable += "</td>";
				strTable += "<td>";
				strTable += UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, osDto.getDataInicio(), WebUtil.getLanguage(request));
				strTable += "</td>";
				strTable += "<td>";
				strTable += UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, osDto.getDataFim(), WebUtil.getLanguage(request));
				strTable += "</td>";
				strTable += "<td>";
				strTable += UtilStrings.nullToVazio(osDto.getDescricaoSituacaoOS());
				strTable += "</td>";
				strTable += "<td>";
				if (osDto.getCustoOS() != null) {
					strTable += UtilFormatacao.formatDouble(osDto.getCustoOS(), 2);
				} else {
					strTable += "--";
				}
				strTable += "</td>";
				strTable += "<td>";
				if (osDto.getExecutadoOS() != null) {
					strTable += UtilFormatacao.formatDouble(osDto.getExecutadoOS(), 2);
				} else {
					strTable += "--";
				}
				strTable += "</td>";
				strTable += "<td>";
				Double valorGlosado = 0.0;
				// if (osDto.getGlosaOS() != null) {
				// valorGlosado = osDto.getGlosaOS().doubleValue();
				// }
				Collection colGlosasOS = glosaOSService.findByIdOs(osDto.getIdOS());
				if (colGlosasOS != null) {
					for (Iterator it = colGlosasOS.iterator(); it.hasNext();) {
						GlosaOSDTO glosaOSDTO = (GlosaOSDTO) it.next();
						if (glosaOSDTO.getCustoGlosa() != null) {
							valorGlosado = valorGlosado + glosaOSDTO.getCustoGlosa();
						}
					}
				}
				if (valorGlosado > 0) {
					strTable += UtilFormatacao.formatDouble(valorGlosado, 2);
				} else {
					strTable += "--";
				}
				strTable += "</td>";
				strTable += "</tr>";
			}
		} else {
			strTable += "<tr>";
			strTable += "<td colspan='20'>";
			strTable += UtilI18N.internacionaliza(request, "citcorpore.comum.naoHaOSAssociadasFatura");
			strTable += "</td>";
			strTable += "</tr>";
		}
		strTable += "</table>";

		document.getElementById("divOsSelecionadas").setInnerHTML(strTable);

		document.getJanelaPopupById("POPUP_LISTA_OS_FATURAMENTO").hide();
	}

	public void calculaFormulaANS(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}
		FaturaDTO faturaDTO = (FaturaDTO) document.getBean();
		Integer idContrato = -1;
		try {
			idContrato = Integer.parseInt((String) request.getSession(true).getAttribute("NUMERO_CONTRATO_EDICAO"));
		} catch (Exception e) {
		}
		if (idContrato == -1) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.naoIdentificaContrato"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}
		AcordoNivelServicoContratoService acordoNivelServicoContratoService = (AcordoNivelServicoContratoService) ServiceLocator.getInstance()
				.getService(AcordoNivelServicoContratoService.class, null);
		FormulaService formulaService = (FormulaService) ServiceLocator.getInstance().getService(FormulaService.class, null);
		OSService oSService = (OSService) ServiceLocator.getInstance().getService(OSService.class, null);
		GlosaOSService glosaOSService = (GlosaOSService) ServiceLocator.getInstance().getService(GlosaOSService.class, null);
		AcordoNivelServicoContratoDTO acordoNivelServicoDTO = new AcordoNivelServicoContratoDTO();
		acordoNivelServicoDTO.setIdAcordoNivelServicoContrato(faturaDTO.getIdANS());
		acordoNivelServicoDTO = (AcordoNivelServicoContratoDTO) acordoNivelServicoContratoService.restore(acordoNivelServicoDTO);
		if (acordoNivelServicoDTO != null) {
			if (acordoNivelServicoDTO.getIdFormula() == null) {
				document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
				return;
			}
			Collection colOSs = oSService.listOSByIds(faturaDTO.getIdContrato(), faturaDTO.getIdOSFatura());
			double valorPrevistoOS = 0;
			double valorExecutadoOS = 0;
			Double valorGlosado = 0.0;
			if (colOSs != null) {
				for (Iterator itOs = colOSs.iterator(); itOs.hasNext();) {
					OSDTO osDto = (OSDTO) itOs.next();
					// if (osDto.getGlosaOS() != null) {
					// valorGlosado = osDto.getGlosaOS();
					// }
					if (osDto.getCustoOS() != null) {
						valorPrevistoOS = valorPrevistoOS + osDto.getCustoOS().doubleValue();
					}
					if (osDto.getExecutadoOS() != null) {
						valorExecutadoOS = valorExecutadoOS + osDto.getExecutadoOS().doubleValue();
					}
					Collection colGlosasOS = glosaOSService.findByIdOs(osDto.getIdOS());
					if (colGlosasOS != null) {
						for (Iterator it = colGlosasOS.iterator(); it.hasNext();) {
							GlosaOSDTO glosaOSDTO = (GlosaOSDTO) it.next();
							if (glosaOSDTO.getCustoGlosa() != null) {
								valorGlosado = valorGlosado + glosaOSDTO.getCustoGlosa().doubleValue();
							}
						}
					}
				}
			}
			faturaDTO.setValorSomaGlosasOS(valorGlosado);
			faturaDTO.setValorPrevistoSomaOS(valorPrevistoOS);
			faturaDTO.setValorExecutadoSomaOS(valorExecutadoOS);

			FormulaDTO formulaDto = new FormulaDTO();
			formulaDto.setIdFormula(acordoNivelServicoDTO.getIdFormula());
			formulaDto = (FormulaDTO) formulaService.restore(formulaDto);
			if (formulaDto != null) {
				ScriptRhinoJSExecute scriptExecute = new ScriptRhinoJSExecute();
				RuntimeScript runtimeScript = new RuntimeScript();
				Context cx = Context.enter();
				Scriptable scope = cx.initStandardObjects();
				scope.put("document", scope, document);
				scope.put("request", scope, request);
				scope.put("response", scope, response);
				scope.put("faturaDTO", scope, faturaDTO);
				scope.put("FormulasUtil", scope, new FormulasUtil());
				scope.put("acordoNivelServicoDTO", scope, acordoNivelServicoDTO);
				scope.put("ACTION", scope, "calculaFormulaANS");
				scope.put("userLogged", scope, usuario);
				scope.put("RuntimeScript", scope, runtimeScript);
				try {
					Object retorno = scriptExecute.processScript(cx, scope, formulaDto.getConteudo(), Fatura.class.getName() + "_calculaFormulaANS");
				} catch (Exception e) {
					document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.erroExecutarFormula"));
					e.printStackTrace();
				}
			}
		}
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
	}
}
