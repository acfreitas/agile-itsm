/**
 * 
 */
package br.com.centralit.citcorpore.ajaxForms;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.ContatoSolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.bean.PesquisaSatisfacaoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.mail.MensagemEmail;
import br.com.centralit.citcorpore.negocio.ContatoSolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.GrupoEmpregadoService;
import br.com.centralit.citcorpore.negocio.PesquisaSatisfacaoService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.CriptoUtils;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

/**
 * @author valdoilo
 * 
 */
@SuppressWarnings("rawtypes")
public class PesquisaSatisfacao extends AjaxFormAction {

	SolicitacaoServicoDTO solicitacaoServicoDTO;

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HTMLForm form = document.getForm("form");
		PesquisaSatisfacaoDTO pesquisaSatisfacaoDto = (PesquisaSatisfacaoDTO) document.getBean();

		// ///////// apenas para testes
		/*
		 * pesquisaSatisfacaoDto.setIdPesquisaSatisfacao(2); pesquisaSatisfacaoDto.setIdSolicitacaoServico(2); pesquisaSatisfacaoDto.setHash(CriptoUtils.generateHash("CODED" +
		 * pesquisaSatisfacaoDto.getIdSolicitacaoServico(), "MD5"));
		 */
		// /////////

		ArrayList<PesquisaSatisfacaoDTO> dtoListTemporario = (ArrayList<PesquisaSatisfacaoDTO>) getPesquisaSatisfacaoService().getPesquisaByIdSolicitacao(
				pesquisaSatisfacaoDto.getIdSolicitacaoServico());

		if (pesquisaSatisfacaoDto.getHash() == null) {
			document.alert("Atenção! Não foi possível identificar o código da validação para a pesquisa de satisfação!");
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		String idHashValidacao = CriptoUtils.generateHash("CODED" + pesquisaSatisfacaoDto.getIdSolicitacaoServico(), "MD5");
		if (!pesquisaSatisfacaoDto.getHash().equalsIgnoreCase(idHashValidacao)) {
			document.alert("Atenção! O código de validação da pesquisa de satisfação não confere!");
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		this.solicitacaoServicoDTO = new SolicitacaoServicoDTO();

		this.solicitacaoServicoDTO.setIdSolicitacaoServico(pesquisaSatisfacaoDto.getIdSolicitacaoServico());

		this.solicitacaoServicoDTO = (SolicitacaoServicoDTO) this.getSolicitacaoServicoService(request).restore(this.solicitacaoServicoDTO);

		if (this.solicitacaoServicoDTO != null) {
			document.getElementById("divId").setInnerHTML("" + pesquisaSatisfacaoDto.getIdSolicitacaoServico() + " - " + formataDataHora(this.solicitacaoServicoDTO.getDataHoraSolicitacao()));

			request.setAttribute("descricao", this.solicitacaoServicoDTO.getDescricao());
			request.setAttribute("resposta", UtilStrings.unescapeJavaString(this.solicitacaoServicoDTO.getResposta()));

			pesquisaSatisfacaoDto.setIdSolicitacaoServico(this.solicitacaoServicoDTO.getIdSolicitacaoServico());

			this.gerarComboNotas(document, request);

			form.setValues(pesquisaSatisfacaoDto);

			if (dtoListTemporario != null && dtoListTemporario.size() > 0) {
				form.setValues(dtoListTemporario.get(0));
				form.lockForm();
				document.executeScript("$('#btnEnviar').addClass('disabledButtons')");
				if (pesquisaSatisfacaoDto.getFrame() == null)
					document.executeScript("showing('pagemsg');");
				else
					document.executeScript("showing('page');");
			} else {
				document.executeScript("showing('page');");
			}

		}
		document.executeScript("JANELA_AGUARDE_MENU.hide()");

	}

	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	}

	/**
	 * Grava uma nova pesquisa de satisfação.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PesquisaSatisfacaoDTO pesquisaSatisfacaoDto = (PesquisaSatisfacaoDTO) document.getBean();
		boolean avaliacaoRuimOuRegular = pesquisaSatisfacaoDto.getNota() == Enumerados.Nota.RUIM.getNota().intValue()
				|| pesquisaSatisfacaoDto.getNota() == Enumerados.Nota.REGULAR.getNota().intValue();

		if (avaliacaoRuimOuRegular && (pesquisaSatisfacaoDto.getComentario() == null || pesquisaSatisfacaoDto.getComentario().trim().isEmpty())) {
			document.alert("Comente sua avaliação!");
		} else {
			if (pesquisaSatisfacaoDto.getIdSolicitacaoServico() != null) {
				this.getPesquisaSatisfacaoService().create(pesquisaSatisfacaoDto);
				if (avaliacaoRuimOuRegular) {
					GrupoEmpregadoService grupoEmpregadoService = (GrupoEmpregadoService) ServiceLocator.getInstance().getService(GrupoEmpregadoService.class, null);
					EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
					SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
					ContatoSolicitacaoServicoService contatoSolicitacaoServicoService = (ContatoSolicitacaoServicoService) ServiceLocator.getInstance().getService(
							ContatoSolicitacaoServicoService.class, null);

					String idModeloEmailAvisarPesquisaSatisfacaoRuimOuRegular = ParametroUtil.getValorParametroCitSmartHashMap(
							ParametroSistema.ID_MODELO_EMAIL_AVISAR_PESQUISA_SATISFACAO_RUIM_OU_REGULAR, "54");

					Map<String, String> map = new HashMap<String, String>();

					if (pesquisaSatisfacaoDto.getNota() == Enumerados.Nota.RUIM.getNota().intValue()) {
						map.put("NOTA", Enumerados.Nota.RUIM.getDescricao());
					} else {
						map.put("NOTA", Enumerados.Nota.REGULAR.getDescricao());
					}

					SolicitacaoServicoDTO solicitacaoServico = solicitacaoServicoService.restoreAll(pesquisaSatisfacaoDto.getIdSolicitacaoServico());
					ContatoSolicitacaoServicoDTO contatoSolicitacaoServicoDTO = new ContatoSolicitacaoServicoDTO();
					contatoSolicitacaoServicoDTO.setIdcontatosolicitacaoservico(solicitacaoServico.getIdContatoSolicitacaoServico());
					contatoSolicitacaoServicoDTO = (ContatoSolicitacaoServicoDTO) contatoSolicitacaoServicoService.restore(contatoSolicitacaoServicoDTO);

					map.put("USUARIO", contatoSolicitacaoServicoDTO.getNomecontato());
					map.put("IDSOLICITACAOSERVICO", pesquisaSatisfacaoDto.getIdSolicitacaoServico().toString());
					map.put("COMENTARIO", pesquisaSatisfacaoDto.getComentario());

					MensagemEmail mensagem = new MensagemEmail(Integer.parseInt(idModeloEmailAvisarPesquisaSatisfacaoRuimOuRegular), map);
					try {
						String idGrupoAvisarPesquisaSatisfacaoRuimOuRegular = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_AVISAR_PESQUISA_SATISFACAO_RUIM_OU_REGULAR, "");

						Collection<GrupoEmpregadoDTO> grupoEmpregados = grupoEmpregadoService.findByIdGrupo(Integer.parseInt(idGrupoAvisarPesquisaSatisfacaoRuimOuRegular));
						for (GrupoEmpregadoDTO grupoEmpregado : grupoEmpregados) {
							EmpregadoDTO empregado = new EmpregadoDTO();
							empregado.setIdEmpregado(grupoEmpregado.getIdEmpregado());
							empregado = (EmpregadoDTO) empregadoService.restoreEmpregadoSeAtivo(empregado);
							if (empregado != null && empregado.getEmail() != null && !empregado.getEmail().trim().equalsIgnoreCase("")) {
								mensagem.envia(empregado.getEmail(), "", ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.RemetenteNotificacoesSolicitacao, "10"));
							}
						}
					} catch (Exception e) {
					}

				}
			}
			CITCorporeUtil.limparFormulario(document);
			
			if (pesquisaSatisfacaoDto.getFrame().trim().equals("sim")) {
				document.executeScript("fecharPopup()");
				document.executeScript("parent.fechaModalOpiniaoEPesquisa()");
			} else {
				document.executeScript("hidden('page');");
				document.executeScript("showing('pagemsg');");
				/**
				 * Ao clicar em OK a janela fecha.
				 * Melhoria a pedido de Igor Oliveira nucleo de consultoria
				 * Solicitacao 117595
				 * **/
				
			}
			document.alert("Obrigado por participar. Sua opinião é muito importante!");
			document.executeScript("closeW()");
		}
	}

	/**
	 * Retorna service de pesquisaSatisfacao.
	 * 
	 * @return <code>PesquisaSatisfacaoService</code>
	 * @throws ServiceException
	 * @throws Exception
	 * @author valdoilo
	 */
	public PesquisaSatisfacaoService getPesquisaSatisfacaoService() throws ServiceException, Exception {
		return (PesquisaSatisfacaoService) ServiceLocator.getInstance().getService(PesquisaSatisfacaoService.class, null);
	}

	/**
	 * Retorna service de solicitacao servico.
	 * 
	 * @return SolicitacaoServicoService
	 * @throws ServiceException
	 * @throws Exception
	 * @author valdoilo
	 */
	public SolicitacaoServicoService getSolicitacaoServicoService(HttpServletRequest request) throws ServiceException, Exception {
		return (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
	}

	@Override
	public Class getBeanClass() {
		return PesquisaSatisfacaoDTO.class;
	}

	/**
	 * Gera Combo de Notas da Pesquisa de satisfação.
	 * 
	 * @param document
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public void gerarComboNotas(DocumentHTML document, HttpServletRequest request) throws Exception {
		HTMLSelect comboNota = (HTMLSelect) document.getSelectById("comboNotas");
		comboNota.removeAllOptions();
		comboNota.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		for (Enumerados.Nota nota : Enumerados.Nota.values()) {
			comboNota.addOption(nota.getNota().toString(), UtilI18N.internacionaliza(request, nota.getChaveInternacionalizacao()));
		}
	}

	private String formataDataHora(Timestamp dateDate) {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return df.format(dateDate);
	}
	
	/**
	 * Internacionaliza a página de pesquisa de satisfação
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author rodrigo.acorse
	 */
	public void internacionaliza(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PesquisaSatisfacaoDTO bean = (PesquisaSatisfacaoDTO) document.getBean();
		
		String IDIOMAPADRAO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.IDIOMAPADRAO," ");
		
		if(IDIOMAPADRAO == null){
			IDIOMAPADRAO= "";
		}
		
		if(bean != null){
			if(bean.getLocale() != null){
				WebUtil.setLocale(bean.getLocale().trim(), request);
				//XmlReadLookup.getInstance(new Locale(bean.getLocale().trim()));
			}else{
				WebUtil.setLocale(IDIOMAPADRAO, request);
				//XmlReadLookup.getInstance(new Locale(IDIOMAPADRAO));
			}
		}else{
			WebUtil.setLocale(IDIOMAPADRAO, request);
			//XmlReadLookup.getInstance(new Locale(IDIOMAPADRAO));
		}
		document.executeScript("window.location.reload()");
	}
	
	
	
	
}
