package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLElement;
import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.CatalogoServicoDTO;
import br.com.centralit.citcorpore.bean.ContratosGruposDTO;
import br.com.centralit.citcorpore.bean.InfoCatalogoServicoDTO;
import br.com.centralit.citcorpore.bean.PastaDTO;
import br.com.centralit.citcorpore.bean.PedidoPortalDTO;
import br.com.centralit.citcorpore.bean.PortalDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoQuestionarioDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.BaseConhecimentoService;
import br.com.centralit.citcorpore.negocio.CatalogoServicoService;
import br.com.centralit.citcorpore.negocio.ComentariosService;
import br.com.centralit.citcorpore.negocio.ContratosGruposService;
import br.com.centralit.citcorpore.negocio.InfoCatalogoServicoService;
import br.com.centralit.citcorpore.negocio.PastaService;
import br.com.centralit.citcorpore.negocio.PerfilAcessoPastaService;
import br.com.centralit.citcorpore.negocio.PortalService;
import br.com.centralit.citcorpore.negocio.ServicoContratoService;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.Enumerados.PermissaoAcessoPasta;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citquestionario.negocio.QuestionarioService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.core.PageRequest;
import br.com.citframework.integracao.core.Pageable;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.Reflexao;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

public class Portal extends AjaxFormAction {

    private static final String NAO = "N";
	private static final String SCRIPT_SHOW_MODAL_ANEXOS = "$('#modal_upload_files').modal('show');";

    private BaseConhecimentoService baseConhecimentoService;
    private PastaService pastaService;
    private PerfilAcessoPastaService perfilAcessoPastaService;
    private Integer quantidadePaginasBaseConhecimento;
    private Integer quantidadePaginasFaq;

    
	@Override
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final UsuarioDTO usrDto = WebUtil.getUsuario(request);

        if (usrDto == null) {
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "/pages/login/login.load'");
            return;
        }

        if (request.getParameter("logout") != null && "yes".equalsIgnoreCase(request.getParameter("logout"))) {
            request.getSession().setAttribute(Constantes.getValue("USUARIO_SESSAO") + "_CITCORPORE", null);
            request.getSession().setAttribute("acessosUsuario", null);
        }

        this.contentCatalogoServico(document, request, response);

        limparObjetosSessao(document, request);
    }


    @Override
    public Class<PortalDTO> getBeanClass() {
        return PortalDTO.class;
    }

    public void contentCatalogoServico(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final UsuarioDTO usrDto = WebUtil.getUsuario(request);
        final Integer idEmpregado = new Integer(usrDto.getIdEmpregado());

        final ContratosGruposService contratosGruposService = (ContratosGruposService) ServiceLocator.getInstance().getService(ContratosGruposService.class, null);

        final Collection<ContratosGruposDTO> contratosGrupos = contratosGruposService.findByIdEmpregado(idEmpregado);

        final StringBuilder sbCatSer = new StringBuilder();
        final CatalogoServicoService catalogoServicoService = (CatalogoServicoService) ServiceLocator.getInstance().getService(CatalogoServicoService.class, null);
        final Collection<CatalogoServicoDTO> listCatalogos = new ArrayList<CatalogoServicoDTO>();

        if (contratosGrupos != null) {
            for (final ContratosGruposDTO contratoGrupoDTO : contratosGrupos) {
                List<CatalogoServicoDTO> temp = null;
                temp = (List<CatalogoServicoDTO>) catalogoServicoService.listByIdContrato(contratoGrupoDTO.getIdContrato());
                if (temp != null) {
                    for (final CatalogoServicoDTO catalogoServicoDTO : temp) {
                        listCatalogos.add(catalogoServicoDTO);
                    }
                }
            }
        }

        sbCatSer.append("	<ul class='row-fluid'>");
        for (final CatalogoServicoDTO catalogoServicoDTO : listCatalogos) {
            // Criando lista de Serviços
            sbCatSer.append("	<li class='span4'>");
            sbCatSer.append("		<a href='javascript:carregarServicos(" + catalogoServicoDTO.getIdCatalogoServico() + "," + catalogoServicoDTO.getIdContrato() + ");' >");
            sbCatSer.append("			<span class='pull-right glyphicons shopping_cart'><i></i></span>");
            sbCatSer.append("			<h5>");
            sbCatSer.append(StringEscapeUtils.escapeHtml(catalogoServicoDTO.getTituloCatalogoServico()));
            sbCatSer.append("			</h5>");
            sbCatSer.append("			<span class='price'>");
            sbCatSer.append("				");
            sbCatSer.append("			</span>");
            sbCatSer.append("			<span class='clearfix'></span>");
            sbCatSer.append("		</a>");
            sbCatSer.append("	</li>");
        }
        sbCatSer.append("	</ul>");

        // atribuindo informações da lista de Serviços
        final HTMLElement divPrincipal = document.getElementById("listaServicos");
        divPrincipal.setInnerHTML(sbCatSer.toString());

    }

    /**
     * Modificado 09/12/2014. Adicionado condicional para exibição completa
     * @author thyen.chang
     */
    public void contentServicos(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final InfoCatalogoServicoService infoCatalogoServicoService = (InfoCatalogoServicoService) ServiceLocator.getInstance().getService(InfoCatalogoServicoService.class, null);
        final StringBuilder sbDesc = new StringBuilder();
        // Criando tabela Descrição
        sbDesc.append("<h5>" + UtilI18N.internacionaliza(request, "portal.carrinho.listagem") + "</h5>");
        sbDesc.append("<table class='table table-bordered uniformjs tabelaFixa' id='tblDescricao'>");
        sbDesc.append("		<button type='button' class='close' data-dismiss='modal' aria-hidden='true'>×</button>");
        sbDesc.append("		<tr>");
        sbDesc.append("			<th class='span1'>");
        sbDesc.append("				<input class='checkbox' "
    		+ (ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.DESMARCAR_SERVICOS_CARRINHO_PORTAL, "S").equals(NAO) ? " checked='checked' " : "" )
    		+ " type='checkbox' id='checkboxCheckAll' name='checkboxCheckAll' onclick='marcarTodos(this.checked);' />");
        sbDesc.append("			</th>");
        sbDesc.append("			<th class='span2'>");
        sbDesc.append("				" + UtilI18N.internacionaliza(request, "portal.carrinho.nomeAmigavel.servico") + "");
        sbDesc.append("			</th>");
        if(obterParametroHabilitaCarrinhoPortal().equals("S")){
	        sbDesc.append("			<th class='span2'>");
	        sbDesc.append("				" + UtilI18N.internacionaliza(request, "portal.carrinho.nomeTecnico.servico") + "");
	        sbDesc.append("			</th>");
        }
        sbDesc.append("			<th class='span4'>");
        sbDesc.append("				" + UtilI18N.internacionaliza(request, "portal.carrinho.descricao") + "");
        sbDesc.append("			</th>");
        sbDesc.append("			<th class='span3'>");
        sbDesc.append("				" + UtilI18N.internacionaliza(request, "portal.carrinho.observacao") + "");
        sbDesc.append("			</th>");
        sbDesc.append("		</tr>");
        final PortalDTO portalDTO = (PortalDTO) document.getBean();
        final Collection<InfoCatalogoServicoDTO> listInfoCatalogoServico = infoCatalogoServicoService.findByCatalogoServico(portalDTO.getIdCatalogoServico());
        if (listInfoCatalogoServico != null && !listInfoCatalogoServico.isEmpty()) {
            int i = 1;
            for (final InfoCatalogoServicoDTO info : listInfoCatalogoServico) {
                if (info.getIdServicoCatalogo() != null && info.getIdServicoCatalogo() != 0) {
                    sbDesc.append("<tr>");
                    sbDesc.append("<td>");
                    sbDesc.append("		<input type='checkbox' "
                    		+ (ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.DESMARCAR_SERVICOS_CARRINHO_PORTAL, "S").equals(NAO) ? " checked='checked' " : "" )
                    		+ " class='perm checkbox' id='idServicoCatalogo" + i + "' name='idServicoCatalogo' value='"
                            + info.getIdServicoCatalogo() + "' />");
                    sbDesc.append("		<input type='hidden'id='idContrato" + i + "' name='idContrato' value='" + portalDTO.getIdContratoUsuario() + "' />");
                    sbDesc.append("		<input type='hidden'id='descInfoCatalogoServico" + i + "' name='descInfoCatalogoServico' value='"
                            + StringEscapeUtils.escapeJavaScript(info.getDescInfoCatalogoServico()) + "' />");
                    sbDesc.append("		<input type='hidden'id='observacaoInfoCatalogoServico" + i + "' name='observacaoInfoCatalogoServico' value='' />");
                    sbDesc.append("		<input type='hidden'id='nomeServico" + i + "' name='nomeServico' value='"
                    		+ StringEscapeUtils.escapeJavaScript(info.getNomeInfoCatalogoServico()) + "' />");
                    sbDesc.append("		<input type='hidden'id='idInfoCatalogoServico" + i + "' name='idInfoCatalogoServico' value='"
                    		+info.getIdInfoCatalogoServico()+ "' /></td>");
                    sbDesc.append("<td class ='quebraPalavras' >" + StringEscapeUtils.escapeJavaScript(info.getNomeInfoCatalogoServico()) + "</td>");

                    	sbDesc.append("<td class ='quebraPalavras'>" + StringEscapeUtils.escapeJavaScript(info.getNomeServicoContrato()) + "</td>");
                    sbDesc.append("<td class ='quebraPalavras'>" + StringEscapeUtils.escapeJavaScript(info.getDescInfoCatalogoServico()) + "</td>");
                    sbDesc.append("<td>");
                    sbDesc.append("		<textarea onkeyup='javascript:limita(this.id);'   id='observacaoPortal" + i
                            + "' name='observacaoPortal' class='' style='width: 96%;' rows='2' maxlength = '3000'></textarea>");
                    sbDesc.append("</td>");

                    i++;
                }
            }
            /*
             * Desenvolvedor: Thiago Matias - Data: 07/11/2013 - Horário: 14:50 - ID Citsmart: 123357 - Motivo/Comentário: uma forma de verificar se no catálogo de Negócio o campo
             * Serviço foi
             * preenchido, pois pode haver dados antigos no banco que não tenha essa vinculação do Cat. Negócio com o Serviço
             */
            if (i == 1) {
                document.alert(UtilI18N.internacionaliza(request, "portal.naohaserviconocatalogo"));
                return;
            }
        } else {
            /*
             * Desenvolvedor: Thiago Matias - Data: 07/11/2013 - Horário: 14:50 - ID Citsmart: 123357 - Motivo/Comentário: Exibir um alert se no catálogo de Negócio não houver
             * nenhum serviço
             * vinculados
             */
            document.alert(UtilI18N.internacionaliza(request, "portal.naohaserviconocatalogo"));
            return;
        }
        sbDesc.append("</tr>");
        sbDesc.append("</table>");

        if( NAO.equalsIgnoreCase(obterParametroHabilitaCarrinhoPortal())){
	        sbDesc.append("<br/>");
	        sbDesc.append("<div>");
	        sbDesc.append("<label class='checkbox'>");
	        sbDesc.append("<input type='checkbox' class='radio' name='checkAnexarArquivos' value='S' id='checkAnexarArquivos' onclick='controlarCheckAnexos(this)'/>");
	        sbDesc.append(UtilI18N.internacionaliza(request, "portal.servicos.desejaAnexarArquivos"));
	        sbDesc.append("</label>");
	        sbDesc.append("</div>");
        }
        document.executeScript("setarValoresTabela(\"" + sbDesc.toString() + "\")");
    }

    @SuppressWarnings("unchecked")
	private List<ServicoContratoDTO> adicionaItensCarrinho(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final List<ServicoContratoDTO> listaServicosContrato = new ArrayList<ServicoContratoDTO>();
        final ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
        final List<InfoCatalogoServicoDTO> infoCatalogoServicoDTOs = (ArrayList<InfoCatalogoServicoDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(
                InfoCatalogoServicoDTO.class, "servicosEscolhidos", request);

        PortalDTO portalDto = (PortalDTO) document.getBean();

        PortalService portalService = (PortalService) ServiceLocator.getInstance().getService(PortalService.class, null);


        if (infoCatalogoServicoDTOs != null) {
            for (final InfoCatalogoServicoDTO infoCatalogoServicoDTO : infoCatalogoServicoDTOs) {
                final ServicoContratoDTO servicoContratoDto = servicoContratoService.findByIdServicoContrato(infoCatalogoServicoDTO.getIdServicoCatalogo(),
                        infoCatalogoServicoDTO.getIdContrato());
                if (servicoContratoDto != null) {
                    servicoContratoDto.setIdServico(infoCatalogoServicoDTO.getIdServicoCatalogo());
                    servicoContratoDto.setIdContrato(infoCatalogoServicoDTO.getIdContrato());
                    servicoContratoDto.setDescricao(infoCatalogoServicoDTO.getDescInfoCatalogoServico());
                    servicoContratoDto.setObservacaoPortal(StringEscapeUtils.escapeJavaScript(br.com.citframework.util.WebUtil.codificaEnter(infoCatalogoServicoDTO.getObservacaoPortal())));
                    servicoContratoDto.setNomeServico(infoCatalogoServicoDTO.getNomeServico());
                    servicoContratoDto.setIdInfoCatalogoServico(infoCatalogoServicoDTO.getIdInfoCatalogoServico());

                    /*
                     * Obtem o idQuestionario
                     */
                    servicoContratoDto.setIdQuestionario(portalService.obterIdQuestionarioServico(infoCatalogoServicoDTO.getIdServicoCatalogo()));

                    montarAnexosCarrinho(document, request, portalDto, servicoContratoDto, obterParametroHabilitaCarrinhoPortal());

                    montarQuestionarioCarrinho(document, request, portalService, infoCatalogoServicoDTO, servicoContratoDto, obterParametroHabilitaCarrinhoPortal());

                    verificarQuestaoObrigatoria(servicoContratoDto);

                    listaServicosContrato.add(servicoContratoDto);
                    
                    adicionarObjetoCarrinhoNaSessao(servicoContratoDto, request);

                }

            }
            
            
            
            if (listaServicosContrato != null && listaServicosContrato.size() > 0) {
                document.executeScript("adicionarColecaoItensItens(\"" + br.com.citframework.util.WebUtil.serializeObjects(listaServicosContrato, WebUtil.getLanguage(request))
                        + "\")");
            }

            document.executeScript("$('.tabsbar a[href=\"#tab2-4\"]').tab('show');");



        }

        /*
         * Parametro bloqueiaAcoes é recupearado, caso esteja com o valor falso significa que o
         *
         * usuario não selecionou em nenhum serviço a opção "Deseja anexar arquivo(s)" e o serviço
         *
         * não possua questionario vinculado, então continua com as rotinas normais.
         *
         * @author Ezequiel
         *
         */
        boolean existeAnexo = (boolean) request.getSession().getAttribute("existeAnexo");

        boolean existeQuestionario = (boolean) request.getSession().getAttribute("existeQuestionario");

    	if (!existeAnexo && !existeQuestionario){

    		 if (portalDto != null && portalDto.getFinalizaCompra() != null && "s".equalsIgnoreCase(portalDto.getFinalizaCompra())) {

                 	document.executeScript("ocultarColunaQuestionario()");

                 	document.executeScript("ocultarColunaAnexar()");

    	            document.executeScript("finalizarCarrinho()");
    	     }
    	}

    	if (!existeAnexo){

    		document.executeScript("ocultarColunaAnexar()");
    	}

    	if (!existeQuestionario){

    		document.executeScript("ocultarColunaQuestionario()");
    	}


    	atualizarListaQuestionarios(document, request, response, listaServicosContrato);

    	return listaServicosContrato;
    }

    /**
     * Não existia este método para atualizar o valor do atributo "colecao_dados_solicit_quest"
     * @author cristian.guedes
     * @param document
     * @param request
     * @param response
     * @param listaServicosContrato
     */
    private void atualizarListaQuestionarios(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response, final List<ServicoContratoDTO> listaServicosContrato) {
    	Collection<SolicitacaoServicoQuestionarioDTO> colecaoDeQuestionarios = (Collection<SolicitacaoServicoQuestionarioDTO>) request.getSession(true).getAttribute("colecao_dados_solicit_quest");

    	if (colecaoDeQuestionarios==null) {
    		colecaoDeQuestionarios = new ArrayList<SolicitacaoServicoQuestionarioDTO>();
    	}

    	try {
            for (ServicoContratoDTO servico : listaServicosContrato) {

            	SolicitacaoServicoQuestionarioDTO solicitacaoServicoQuestionarioDTO = new SolicitacaoServicoQuestionarioDTO();
            	Reflexao.copyPropertyValues(servico, solicitacaoServicoQuestionarioDTO);


            	if (objetoJaFoiAdicionado(request, solicitacaoServicoQuestionarioDTO, colecaoDeQuestionarios)==false) {
                	colecaoDeQuestionarios.add(solicitacaoServicoQuestionarioDTO);

            	}


    		}
            request.getSession(true).setAttribute("colecao_dados_solicit_quest", colecaoDeQuestionarios);

		} catch (Exception e) {
			// TODO: handle exception
		}

    }

    /**
     * A requisição no citsmart sempre é executada 2 vezes, por isto criei este método para não inserir o mesmo
     * questionário mais de uma vez na lista
     * @param request
     * @param solicitacaoServicoQuestionarioDTO
     * @param colecaoDeQuestionarios
     * @return
     */
    private boolean objetoJaFoiAdicionado(final HttpServletRequest request,
			SolicitacaoServicoQuestionarioDTO solicitacaoServicoQuestionarioDTO, Collection<SolicitacaoServicoQuestionarioDTO> colecaoDeQuestionarios) {

    	if (colecaoDeQuestionarios!=null) {
    		for (SolicitacaoServicoQuestionarioDTO objetoDaColecao : colecaoDeQuestionarios) {
    			if (solicitacaoServicoQuestionarioDTO.getIdServico().equals(objetoDaColecao.getIdServico())) {
    				return true;
    			}

			}

    	}

    	return false;
	}


    public void adicionaItensCarrinhoDeCompraEContinuar(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
    	verificarExisteQuestSemResposta(document, request, adicionaItensCarrinho(document, request, response), obterParametroHabilitaCarrinhoPortal(), false);

//    	adicionaItensCarrinho(document, request, response);

        document.executeScript("reativarObjetos();");
    }

    public void adicionaItensCarrinhoDeCompra(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
    	verificarExisteQuestSemResposta(document, request, adicionaItensCarrinho(document, request, response), obterParametroHabilitaCarrinhoPortal(), true);

    	
    	
    	
//    	adicionaItensCarrinho(document, request, response);
        document.executeScript("reativarObjetos();");
    }

    
	/**
     * @author cristian.guedes
     * @param servicoContratoDTO
     * @param request
     */
    private void adicionarObjetoCarrinhoNaSessao(final ServicoContratoDTO servicoContratoDTO, final HttpServletRequest request) {
    	
    	ArrayList<ServicoContratoDTO> listaObjetosCarrinho = (ArrayList<ServicoContratoDTO>) request.getSession().getAttribute("listaObjetosCarrinho");
   	 	if (listaObjetosCarrinho==null || listaObjetosCarrinho.isEmpty()) {
            listaObjetosCarrinho = new ArrayList<ServicoContratoDTO>();
        }
        
   	 	listaObjetosCarrinho.add(servicoContratoDTO);
   	
   	 	request.getSession().setAttribute("listaObjetosCarrinho", listaObjetosCarrinho);
   	
    }
    
    /**
     * @author cristian.guedes
     * @param servicoContratoDTO
     * @param request
     */
    private void removerObjetoCarrinhoDaSessao(final ServicoContratoDTO servicoContratoDTO, final HttpServletRequest request) {
    	ArrayList<ServicoContratoDTO> listaObjetosCarrinho = (ArrayList<ServicoContratoDTO>) request.getSession().getAttribute("listaObjetosCarrinho");
   	 	if (listaObjetosCarrinho==null || listaObjetosCarrinho.isEmpty()) {
            listaObjetosCarrinho = new ArrayList<ServicoContratoDTO>();
        }
        
   	 	ServicoContratoDTO objetoCarrinhoRemover = null;
   	 
   	 	for (ServicoContratoDTO objetoCarrinho : listaObjetosCarrinho) {
   	 		
   	 		if (
//   	 				objetoCarrinho.getIdContrato().equals(servicoContratoDTO.getIdContrato())
//   	 				&&
   	 				objetoCarrinho.getIdServico().equals(servicoContratoDTO.getIdServico())
//   	 				&& 
//   	 				objetoCarrinho.getObservacaoPortal().equalsIgnoreCase(servicoContratoDTO.getObservacaoPortal())
//   	 				&& 
//   	 				objetoCarrinho.getNomeCategoriaServico().equalsIgnoreCase(servicoContratoDTO.getNomeCategoriaServico())
//   	 				&& 
//   	 				objetoCarrinho.getNomeServico().equalsIgnoreCase(servicoContratoDTO.getNomeServico())
  	 				) {
   	 			objetoCarrinhoRemover = objetoCarrinho;
   	   	 		
   	 			
   	 		}
		}
   	 	
   	 	if (objetoCarrinhoRemover!=null) {
   	   	 	listaObjetosCarrinho.remove(objetoCarrinhoRemover);
  	 		
   	 	}
   	 	
   	 	request.getSession().setAttribute("listaObjetosCarrinho", listaObjetosCarrinho);
    	
    }
    
	/**
	 * Metodo responsavel por obter o valor do parametro HABILITA_PRECO_CARRINHO_PORTAL
	 *
	 * O parâmetro 245 deverá estar setado em "N". Caso esteja setado em "S" o sistema apresentará
	 *
	 * a tela completa que não possui as funções de "Responder Questionário e Anexar Arquivos".
	 *
	 * @author Ezequiel
	 *
	 * @return <code>String</code>
	 */
	private String obterParametroHabilitaCarrinhoPortal() {

		try {

			return ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.HABILITA_PRECO_CARRINHO_PORTAL, "S");
		} catch (Exception ex) {

			return "";
		}
	}

	public void validarParametroUpload(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception{

		try {

			String parametro44 = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.DISKFILEUPLOAD_REPOSITORYPATH, "");

			if (parametro44 == null || parametro44.trim().isEmpty()){

				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.anexosUploadSemParametro"));
			}else{

				document.executeScript("continueUpload()");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo responsavel por verificar se existe questionario para o serviço especifico
	 *
	 * Se sim e o parametro 245 esteja com valor 'N', emtão será setado um valor 'S' para
	 *
	 * o atributo existe questionario, esse atributo é responsavel por renderizar os botões
	 *
	 * na grid, foi criado um atributo na sessão para amarrar a existencia do questionario.
	 *
	 * No complete do metodo uma função javascript e executada para exibir a coluna de
	 *
	 * questionario.
	 *
	 * @param document
	 * @param request
	 * @param portalService
	 * @param infoCatalogoServicoDTO
	 * @param servicoContratoDto
	 * @throws ServiceException
	 * @throws Exception
	 *
	 * @author Ezequiel
	 */
	private void montarQuestionarioCarrinho(final DocumentHTML document, final HttpServletRequest request, PortalService portalService, final InfoCatalogoServicoDTO infoCatalogoServicoDTO, final ServicoContratoDTO servicoContratoDto,
			String exibirPrecoCarrinhoCompra) throws ServiceException, Exception {

		boolean existeQuestionario = portalService.existeQuestionarioServico(infoCatalogoServicoDTO.getIdServicoCatalogo());

		if (existeQuestionario && NAO.equalsIgnoreCase(exibirPrecoCarrinhoCompra)){

			servicoContratoDto.setExisteQuestionario("S");

			request.getSession().setAttribute("existeQuestionario", Boolean.TRUE);

			document.executeScript("exibirColunaQuestionario()");
		}
	}

	/**
	 * Verifica se a opção de anexar arquivos foi marcada.
	 *
	 * Se sim, o sistema popula o campo permiteAnexo, para cada linha da tabela que tera a opção de anexar, caso não a linha não
	 *
	 * tera o botão de anexar.
	 *
	 * Foi criado um objeto na sessão com o nome "bloqueiaAcoes" remetendo ao campo "Deseja anexar arquivo(s)", esse atributo foi
	 *
	 * criado com a função de controlar se pelo menos um dos serviços possui opção de anexar, isso foi feito para disabilitar algumas
	 *
	 * funções já existentes.
	 *
	 * @param document
	 * @param request
	 * @param portalDto
	 * @param servicoContratoDto
	 *
	 * @autor Ezequiel
	 */
	private void montarAnexosCarrinho(final DocumentHTML document, final HttpServletRequest request, PortalDTO portalDto, final ServicoContratoDTO servicoContratoDto, String exibirPrecoCarrinhoCompra) {

		if ("s".equalsIgnoreCase(portalDto.getAnexarArquivos()) && NAO.equalsIgnoreCase(exibirPrecoCarrinhoCompra)){

			servicoContratoDto.setPermiteAnexar(Boolean.TRUE);

			request.getSession().setAttribute("existeAnexo", Boolean.TRUE);

			document.executeScript("exibirColunaAnexar()");
		}
	}


    /**
     * Metodo responsavel por verificar se existe questionario a ser respondido, nos serviços do carrinho.
     *
     * Essa validação só será feita se o parametro 245 estiver com o valor setado como 'N'.
     *
     * @param document
     * @param request
     *
     * @author Ezequiel
     * @throws Exception
     */
    private void verificarExisteQuestSemResposta(final DocumentHTML document, final HttpServletRequest request, List<ServicoContratoDTO> servicoDTOs, String exibirPrecoCarrinhoCompra, boolean alertar) {

    	try {

	    	if (NAO.equalsIgnoreCase(exibirPrecoCarrinhoCompra)){

	    		for(ServicoContratoDTO servicoContratoDTO : servicoDTOs){

	    			if (servicoContratoDTO.getIdServico() != null){

				    	if (
				    			"s".equalsIgnoreCase(servicoContratoDTO.getExisteQuestionario()) &&
				    			"s".equalsIgnoreCase(servicoContratoDTO.getRespostaObrigatoria())

				    		){

				    		String mensagem = UtilI18N.internacionaliza(request, "portal.servicos.existeQuestionarios");

				    		if (request.getSession().getAttribute("qtdQuestionarioObrigatorios") == null){

				    			request.getSession().setAttribute("qtdQuestionarioObrigatorios", 1);

				    		}else{

				    			Integer quantidade = (Integer) request.getSession().getAttribute("qtdQuestionarioObrigatorios");

				    			quantidade++;

				    			request.getSession().setAttribute("qtdQuestionarioObrigatorios", quantidade);
				    		}


				    		mensagem = mensagem.replace("\\", "");

				    		if (alertar) {
					    		document.alert(mensagem);

				    		}

				    		return;
				    	}
	    			}
	    		}
	    	}
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }



    @SuppressWarnings("unchecked")
    public void finalizarCarrinho(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {


        final UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }
        final PortalService portalService = (PortalService) ServiceLocator.getInstance().getService(PortalService.class, null);
        
        ArrayList<ServicoContratoDTO> servicoDTOs = (ArrayList<ServicoContratoDTO>) request.getSession().getAttribute("listaObjetosCarrinho");
        
        if (servicoDTOs != null) {



            /* Pedido */
            PedidoPortalDTO pedidoPortalDTO = new PedidoPortalDTO();
            pedidoPortalDTO.setIdEmpregado(usuario.getIdEmpregado());
            pedidoPortalDTO.setDataPedido(UtilDatas.getDataAtual());
            pedidoPortalDTO.setListaServicoContrato(servicoDTOs);

            Collection<UploadDTO> arquivosUpados = (Collection<UploadDTO>) request.getSession(true).getAttribute("colUploadsGED");

            Collection<SolicitacaoServicoQuestionarioDTO> colecaoRespQuestionario = obterListaQuestionarioSession(request);

            if (existeQuestaoObrigatoriaNaoRespondida(document, request, colecaoRespQuestionario)) {
            	return;
            }

            if(!existeQuestaoObrigatoriaNaoRespondida(document, request, colecaoRespQuestionario)){

	            try {
	                pedidoPortalDTO = portalService.criarPedidoSolicitacao(pedidoPortalDTO, usuario, colecaoRespQuestionario, arquivosUpados);

	                request.getSession().setAttribute("idServicoUp", null);

	                String mensagem = "";
	                mensagem += "<h4>" + UtilI18N.internacionaliza(request, "MSG05") + "</h4>";
	                for (final SolicitacaoServicoDTO solicitacaoServicoDTO : pedidoPortalDTO.getListaSolicitacoes()) {
	                    mensagem += "<br/>";
	                    mensagem += UtilI18N.internacionaliza(request, "gerenciaservico.numerosolicitacao") + " <b><u>" + solicitacaoServicoDTO.getIdSolicitacaoServico() + "</u></b> "
	                            + UtilI18N.internacionaliza(request, "citcorpore.comum.crida") + ".<br>" + UtilI18N.internacionaliza(request, "prioridade.prioridade") + ": "
	                            + solicitacaoServicoDTO.getIdPrioridade();
	                    if (solicitacaoServicoDTO.getPrazoHH() > 0 || solicitacaoServicoDTO.getPrazoMM() > 0) {
	                        mensagem += " - SLA: " + solicitacaoServicoDTO.getSLAStr() + "<br/>";
	                    }
	                }

	                document.executeScript("mostrarMensagemSolicitacoes(\"" + mensagem + "\")");



	                limparObjetosSessao(document, request);

	            } catch (final Exception e) {
	                e.printStackTrace();
	                document.alert(UtilI18N.internacionaliza(request, "portal.pendenciaFinalizaCarrinhoCompras"));
	            }
            }
        }



        /**
         * Cristian: Solicitação 165383
         * Os arquivos que haviam sido previamente "upados" continuavam a aparecer na próxima tentativa de upload
         */
        request.getSession(true).setAttribute("colUploadsGED", null);

        /**
         * Cristian: solicitação 165505
         * Reativa os objetos que foram desativados durante o processamento de fechamento da compra
         */
        document.executeScript("reativarObjetos();");

        request.getSession(true).setAttribute("listaObjetosCarrinho", new ArrayList<ServicoContratoDTO>());

    }

    /**
     * Verifica as Permissões de Acesso do Usuário a Pasta informada
     *
     * @param document
     * @param request
     * @param response
     * @param baseConhecimentoDto
     * @return
     * @throws Exception
     * @author thays.araujo
     */
    public boolean verificarPermissaoDeAcesso(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response,
            BaseConhecimentoDTO baseConhecimentoDto) throws Exception {
        final UsuarioDTO usuarioDto = WebUtil.getUsuario(request);

        PastaDTO pastaDto = new PastaDTO();

        if (baseConhecimentoDto.getIdBaseConhecimento() != null) {
            baseConhecimentoDto = (BaseConhecimentoDTO) this.getBaseConhecimentoService().restore(baseConhecimentoDto);
        }

        if (baseConhecimentoDto.getIdPasta() != null) {
            pastaDto.setId(baseConhecimentoDto.getIdPasta());

            pastaDto = (PastaDTO) this.getPastaService().restore(pastaDto);
        }

        PermissaoAcessoPasta permissao = this.getPerfilAcessoPastaService().verificarPermissaoDeAcessoPasta(usuarioDto, pastaDto);

        if (permissao != null) {
            if (PermissaoAcessoPasta.LEITURA.equals(permissao) || PermissaoAcessoPasta.LEITURAGRAVACAO.equals(permissao)) {
                return true;
            } else {
                if (PermissaoAcessoPasta.SEMPERMISSAO.equals(permissao)) {
                    return false;
                }
            }
        }
        return false;
    }


    private PastaService getPastaService() throws Exception {
        if (pastaService == null) {
            pastaService = (PastaService) ServiceLocator.getInstance().getService(PastaService.class, null);
        }
        return pastaService;
    }

    private PerfilAcessoPastaService getPerfilAcessoPastaService() throws Exception {
        if (perfilAcessoPastaService == null) {
            perfilAcessoPastaService = (PerfilAcessoPastaService) ServiceLocator.getInstance().getService(PerfilAcessoPastaService.class, null);
        }
        return perfilAcessoPastaService;
    }

    private BaseConhecimentoService getBaseConhecimentoService() throws Exception {
        if (baseConhecimentoService == null) {
            baseConhecimentoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, null);
        }
        return baseConhecimentoService;
    }

    /**
     * Metodo responsavel por controlar olaguns objetos vinculados a sessão e abrir um popup para anexos de arquivos.
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     *
     * @author Ezequiel Bispo Nunes
     * @date 2014-12-04
     */
	@SuppressWarnings("rawtypes")
    public void ajustarObjetosSessao(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception{

			Map map = document.getForm("formUpload").getDocument().getValuesForm();

			if (map.get("IDSERVICOUP") != null && !map.get("IDSERVICOUP").toString().isEmpty()){

				Integer idServicoUp = Integer.valueOf((String) map.get("IDSERVICOUP"));

				request.getSession().setAttribute("idServicoUp", idServicoUp);

		    	document.executeScript(SCRIPT_SHOW_MODAL_ANEXOS);
			}

    }


	/**
	 * Metodo responsavel por exibir o modal de questionario, o questionario só será apresentado se ainda não foi respondido.
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void abrirModalQuestionario(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception{


		try{

			Integer idQuestionario = Integer.valueOf((String) request.getParameter("idQuestionarioQuest"));

			Integer idServicoContrato = Integer.valueOf((String) request.getParameter("idServicoContratoQuest"));

			Integer idServico = Integer.valueOf((String) request.getParameter("idServicoUp"));

			String respostaObrigatoria = request.getParameter("respostaObrigatoria");

			if (!questionarioServicoRespondido(request, idQuestionario, idServico)) {

				StringBuilder scriptFrame = new StringBuilder();

				scriptFrame.append("document.getElementById('fraInformacoesComplementares').src = '../../pages/visualizacaoQuestionario/visualizacaoQuestionario.load?modo=edicao&idQuestionarioOrigem=");

				scriptFrame.append(idQuestionario + "&idServicoContrato=" + idServicoContrato + "&idServico=" + idServico + "&respostaObrigatoria="+respostaObrigatoria+ "'");

				String scriptShowModal = "$('#modal_questionario').modal('show');";

				document.executeScript(scriptFrame.toString());

				document.executeScript(scriptShowModal);

			}

			else {
				/**
				 * Cristian: solicitação 165475
				 */
				String mensagem = UtilI18N.internacionaliza(request, "questionario.jafoirespondido");
				document.executeScript("alert('"+mensagem+"')");

			}

		}catch(Exception e){


		}

	}


	/**
	 * Metodo responsavel por desabilitar o botão caso o questionario do relatorio já tenha sido respondido.
	 *
	 * @param request
	 * @param idQuestionario
	 * @param idServico
	 * @return
	 *
	 * @author Ezequiel
	 */
	private boolean questionarioServicoRespondido(HttpServletRequest request,final Integer idQuestionario, final Integer idServico){

		 Collection<SolicitacaoServicoQuestionarioDTO> list = obterListaQuestionarioSession(request);

		 if (list != null && list.size() > 0){

			 for (SolicitacaoServicoQuestionarioDTO dto : list) {

				 if (dto.getIdServico().equals(idServico) && dto.getIdQuestionario().equals(idQuestionario) && dto.getQuestionarioRespondido()!=null && dto.getQuestionarioRespondido().equalsIgnoreCase("S")){

					 return Boolean.TRUE;
				 }
			}
		 }

		 return Boolean.FALSE;
	}


	/**
	 * Metodo responsavel por verificar se existe alguma questão obrigatoria no questionario.
	 *
	 * @param idQuestionario
	 * @author Ezequiel
	 */
	private void verificarQuestaoObrigatoria(final ServicoContratoDTO servicoContratoDTO){

		try {

			QuestionarioService service = (QuestionarioService) ServiceLocator.getInstance().getService(QuestionarioService.class, null);

			boolean eObrigatoria = service.existeQuestaoObrigatoria(servicoContratoDTO.getIdQuestionario());

			servicoContratoDTO.setRespostaObrigatoria(eObrigatoria ? "S" : "N");

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * Metodo responsavel por verificar se existe questão obrigatoria não respondida.
	 *
	 * @param document
	 * @param request
	 * @param listQuestionarioSessao
	 * @return <code>boolean</code>
	 *
	 * @author Ezequiel
	 */
	private boolean existeQuestaoObrigatoriaNaoRespondida(final DocumentHTML document, HttpServletRequest request, Collection<SolicitacaoServicoQuestionarioDTO> listQuestionarioSessao) {

		try {

			Integer countSessao = (Integer) request.getSession().getAttribute("qtdQuestionarioObrigatorios");

			Integer countRespondidas = 0;

			if (listQuestionarioSessao != null && listQuestionarioSessao.size() > 0) {

				for (SolicitacaoServicoQuestionarioDTO dto : listQuestionarioSessao) {

					if ("S".equalsIgnoreCase(dto.getRespostaObrigatoria()) && "S".equalsIgnoreCase(dto.getQuestionarioRespondido())) {

						countRespondidas++;
					}
				}
			}

			if (countSessao!= null && (countSessao > countRespondidas)) {

				String mensagem = UtilI18N.internacionaliza(request, "portal.servicos.existeQuestionarios");

				mensagem = mensagem.replace("\\", "");

				document.alert(mensagem);

				return Boolean.TRUE;
			}

		} catch (Exception e) {

		}

		return Boolean.FALSE;
	}


	/**
	 * Metodo responsavel por remover da sessão o questionario vinculado a um serviço que foi removido da grid.
	 *
	 * Existe um contado na sessão com o nome "qtdQuestionarioObrigatorios", quando esse metodo for assionando
	 *
	 * será decrementado 1 do contatod.
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 *
	 * @author Ezequiel
	 */
	public void removerQuestionarioSessao(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception{

		Collection<SolicitacaoServicoQuestionarioDTO> list = obterListaQuestionarioSession(request);

		SolicitacaoServicoQuestionarioDTO questionarioRemover = new SolicitacaoServicoQuestionarioDTO();
		ServicoContratoDTO objetoCarrinho = new ServicoContratoDTO();

		Integer idServico = Integer.valueOf((String)request.getParameter("idServicoUp"));

		 if (list != null && list.size() > 0){

			 for (SolicitacaoServicoQuestionarioDTO dto : list) {

				 if (dto.getIdServico().equals(idServico)){

					 questionarioRemover = dto;
					 objetoCarrinho.setIdServico(dto.getIdServico());
				 }
			 }

			 if ((questionarioRemover!=null) && (questionarioRemover.getRespostaObrigatoria()!=null) && (questionarioRemover.getRespostaObrigatoria().equalsIgnoreCase("s"))) {
				 Integer countSessao = (Integer) request.getSession().getAttribute("qtdQuestionarioObrigatorios");

				 if (countSessao != null && countSessao > 0){

					 countSessao--;

					 request.getSession().setAttribute("qtdQuestionarioObrigatorios", countSessao);
				 }

			 }

			 list.remove(questionarioRemover);
			 removerObjetoCarrinhoDaSessao(objetoCarrinho, request);

			 request.getSession(true).setAttribute("colecao_dados_solicit_quest", list);

		 }


	}


	/**
	 * Metodo responsavel por remover da sessão o anexo do serviço excluido da grid do carrinho de compras.
	 * <p>
	 * Metodo verifica se o <code>idServicoSelecionado</code> é igual ao <code>idLinhaPai</code>(vinculo do anexo ao servico) se sim monta o objeto <code>anexosARemover</code>.
	 *
	 * Após é chamado o metodo <code>remove</code> da <code>Collection</code> passando o objeto a ser removido.
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 *
	 * @author Ezequiel
	 */
	@SuppressWarnings("unchecked")
	public void removerAnexoSessao(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception{

		final Collection<UploadDTO> arquivosUploadSession = (Collection<UploadDTO>) request.getSession(true).getAttribute("colUploadsGED");

		try {

			final Integer idServicoSelecionado = Integer.valueOf((String) request.getParameter("idServicoUp"));

			Collection<UploadDTO> anexosARemover = new ArrayList<UploadDTO>();

			if (arquivosUploadSession != null && arquivosUploadSession.size() > 0) {

				for (UploadDTO upload : arquivosUploadSession) {

					if (upload.getIdLinhaPai().equals(idServicoSelecionado)) {

						anexosARemover.add(upload);
					}
				}

				arquivosUploadSession.removeAll(anexosARemover);

				request.getSession(true).setAttribute("colUploadsGED", arquivosUploadSession);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo responsavel por limpar objetos adicionados a sessão, para controle do questionario e anexo na tela de carrinho
	 *
	 * Esse metodo só será assionado no load da pagina.
	 *
	 * @param document
	 * @param request
	 *
	 * @author Ezequiel
	 */
	private void limparObjetosSessao(final DocumentHTML document, final HttpServletRequest request) {

        request.getSession().setAttribute("existeAnexo", Boolean.FALSE);

        request.getSession().setAttribute("existeQuestionario", Boolean.FALSE);

        request.getSession(true).setAttribute("colecao_dados_solicit_quest", null);

        request.getSession().setAttribute("idServicoUp", null);

        request.getSession().setAttribute("qtdQuestionarioObrigatorios", null);

        document.executeScript("ocultarColunaQuestionario()");

     	document.executeScript("ocultarColunaAnexar()");
	}

	/**
	 * Metodo responsavel por limpar da sessão o objeto "existeQuestionario", após a exclusão de um serviço da grid que contenha questionario.
	 *
	 * Esse metodo só será assionado se não conter na grid nenhum serviço que contenha questionario.
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 *
	 * @author Ezequiel
	 */
	public void limparObjetoQuestionarioCarrinho(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception{

		request.getSession().setAttribute("existeQuestionario", Boolean.FALSE);
	}

	/**
	 * Metodo responsavel por limparda sessão o objeto "existeAnexo", após a exclusão de um serviço da grid que contenha a função de anexar.
	 *
	 * Esse metodo só será assionado se não conter na grid nenhum serviço que contenha anexo.
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 *
	 * @author Ezequiel
	 */
	public void limparObjetoAnexarCarrinho(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception{

		request.getSession().setAttribute("existeAnexo", Boolean.FALSE);

		request.getSession().setAttribute("colUploadsGED", null);
	}

	/**
	 * Metodo responsavel por recuperar da sessão a lista de questionarios.
	 *
	 * @param request
	 * @return
	 *
	 * @author Ezequiel
	 */
	@SuppressWarnings("unchecked")
	private Collection<SolicitacaoServicoQuestionarioDTO> obterListaQuestionarioSession(HttpServletRequest request) {

		return (Collection<SolicitacaoServicoQuestionarioDTO>) request.getSession(true).getAttribute("colecao_dados_solicit_quest");
	}
	
	/**
	 * Método para carregar Base de Conhecimento paginado
	 * 
	 * @author thyen.chang
	 * @since 06/02/2015
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void carregaBaseConhecimento(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception{
        final BaseConhecimentoService baseServicoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, null);
		final ComentariosService comentariosService = (ComentariosService) ServiceLocator.getInstance().getService(ComentariosService.class, null);
		
		final int quantidadePorPagina = Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.QUANT_RETORNO_PESQUISA, "10"));
		Integer paginaAtual = Integer.parseInt(request.getParameter("paginaAtualBaseConhecimento"));
		if(paginaAtual == null){
			paginaAtual = 0;
		}
		Pageable pageable = new PageRequest(paginaAtual - 1, quantidadePorPagina);
		
		if(this.quantidadePaginasBaseConhecimento == null){
			this.quantidadePaginasBaseConhecimento = baseServicoService.listarBaseConhecimentoPortal(pageable, true).getTotalPages();
		}
		final List<BaseConhecimentoDTO> listBase = baseServicoService.listarBaseConhecimentoPortal(pageable, false).getContent();

		final StringBuilder strBase = new StringBuilder();
		final StringBuilder colBase = new StringBuilder();
		strBase.append("<table>");
		strBase.append("	<tr>");
		strBase.append("		<form onsubmit='javascript:;' action='javascript:;'>");
		strBase.append("	</tr>");
		strBase.append("</table>");
		strBase.append("<div id ='tableBc'><table class='table table-bordered table-striped' id='tblBaseConhecimento'>");
		strBase.append("<thead><tr><th>Titulo</th><th>Nota</th></tr></thead><tbody>");
		if (listBase != null) {
			for (final BaseConhecimentoDTO baseDTO : listBase) {
				boolean permissao;
				permissao = this.verificarPermissaoDeAcesso(document, request, response, baseDTO);
				if (permissao) {
					if (baseDTO.getDataFim() == null) {
						final Double nota = comentariosService.calcularNota(baseDTO.getIdBaseConhecimento());
						String nt = null;
						if (nota == null) {
							nt = "S/N";
						} else {
							nt = String.valueOf(nota);
						}
						strBase.append("<tr><td style='text-align: left;'>	<a href='javascript:;' class='passar_mouse' onclick='executaModal(");
						strBase.append(baseDTO.getIdBaseConhecimento());
						strBase.append(")' >");
						strBase.append(UtilStrings.unescapeJavaString(baseDTO.getTitulo()));
						strBase.append("</a></td><td  style='text-align: left;'>");
						strBase.append(nt);
						strBase.append("</td></tr>");
					}
				}
			}
		}
		strBase.append("</tbody></table>");
		colBase.append("	<li class='span12'>" + "<div id='portlet-content-03' class='portlet-content'>" + strBase + "</div>" + "</li>");
		geraPaginacao(this.quantidadePaginasBaseConhecimento, colBase, paginaAtual, request, "carregaBaseConhecimento(this.value)");
		final HTMLElement divBase = document.getElementById("column4");
		divBase.setInnerHTML(colBase.toString());
		document.executeScript("JANELA_AGUARDE_MENU.hide()");
	}
	
	/**
	 * Método para carregar FAQ paginado
	 * 
	 * @author thyen.chang
	 * @since 06/02/2015
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void carregaFaq(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final BaseConhecimentoService baseServicoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, null);

		final int quantidadePorPagina = Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.QUANT_RETORNO_PESQUISA, "10"));
		Integer paginaAtual = Integer.parseInt(request.getParameter("paginaAtualFaq"));
		if(paginaAtual == null){
			paginaAtual = 0;
		}
		Pageable pageable = new PageRequest(paginaAtual - 1, quantidadePorPagina);
		
		if(this.quantidadePaginasFaq == null){
			this.quantidadePaginasFaq = baseServicoService.listarBaseConhecimentoFAQPortal(pageable, true).getTotalPages();
		}
		

		final Collection<BaseConhecimentoDTO> listBaseFaq = baseServicoService.listarBaseConhecimentoFAQPortal(pageable, false).getContent();

		final UsuarioDTO usuarioDto = WebUtil.getUsuario(request);

		final StringBuilder strFaq = new StringBuilder();
		if (listBaseFaq != null) {
			final HTMLElement divFaq = document.getElementById("faqs");// Encontrando o elemento no html
			for (final BaseConhecimentoDTO baseDTO : listBaseFaq) {
				if (baseDTO.getDataFim() == null) {

					PastaDTO pastaDto = new PastaDTO();
					if (baseDTO.getIdPasta() != null) {
						pastaDto.setId(baseDTO.getIdPasta());
						pastaDto = (PastaDTO) getPastaService().restore(pastaDto);
					}

					final PermissaoAcessoPasta permissao = getPerfilAcessoPastaService().verificarPermissaoDeAcessoPasta(usuarioDto, pastaDto);

					strFaq.append("<div class='accordion-heading'>");// Inicio div Cabeçalho
					strFaq.append("<a class='accordion-toggle glyphicons circle_question_mark' data-toggle='collapse' data-parent='#accordion' href='#collapse-" + baseDTO.getIdBaseConhecimento()
							+ "'>");
					strFaq.append("<i></i>" + UtilStrings.unescapeJavaString(baseDTO.getTitulo()));// Titulo
					strFaq.append("</a>");
					strFaq.append("</div>");// Fim div cabeçalho

					strFaq.append("<div id='collapse-" + baseDTO.getIdBaseConhecimento() + "' class='accordion-body collapse'>");// Inicio div Corpo
					if (permissao != null) {
						if (PermissaoAcessoPasta.SEMPERMISSAO.equals(permissao)) {
							strFaq.append("<div class='accordion-inner' sytle='text-align:center;'>" + UtilI18N.internacionaliza(request, "baseconhecimento.validacao.usuariosemacesso") + "<br></div>");
						} else {
							strFaq.append("<div class='accordion-inner' sytle='text-align:center;'>" + baseDTO.getConteudo() + "<br></div>");// Corpo do conteudo
						}
					} else {
						strFaq.append("<div class='accordion-inner' sytle='text-align:center;'>" + UtilI18N.internacionaliza(request, "baseconhecimento.validacao.usuariosemacesso") + "<br></div>");
					}

					strFaq.append("</div>");// Fim div corpo

				}// Fim do if
			}// Fim do for
			geraPaginacao(quantidadePaginasFaq, strFaq, paginaAtual, request, "carregaFaq(this.value)");
			divFaq.setInnerHTML(strFaq.toString());// Adicionando o elemento no html
		}// Fim do if
		document.executeScript("JANELA_AGUARDE_MENU.hide()");
	}
	
	/**
	 * Método que gera div com páginas
	 * 
	 * @author thyen.chang
	 * @since 06/02/2015
	 * @param totalPaginas - Número total de páginas da div
	 * @param sb - StringBuilder contendo os elementos HTML
	 * @param paginaSelecionada - Qual a páginada selecionada
	 * @param request
	 * @param functionName - Nome da função para os botões de página
	 * @return
	 * @throws Exception
	 */
	public String geraPaginacao(Integer totalPaginas, StringBuilder sb, Integer paginaSelecionada, HttpServletRequest request, String functionName) throws Exception {
		String locale = (String) request.getSession().getAttribute("locale");
		if (locale == null) {
			locale = "pt";
		}

		final Integer adjacentes = 2;
		if (paginaSelecionada == null)
			paginaSelecionada = 1;
		sb.append("	<div id='itenPaginacaoGerenciamento' class='pagination pagination-right margin-none'>");
		sb.append("		<ul>");
		sb.append("			<li " + (totalPaginas == 0 || paginaSelecionada == 1 ? "class='disabled'" : "value='1' onclick='"+functionName+"'") + " ><a>"
				+ UtilI18N.internacionaliza(locale, "citcorpore.comum.primeiro") + "</a></li>");
		sb.append("			<li " + ((totalPaginas == 0 || totalPaginas == 1 || paginaSelecionada == 1) ? "class='disabled'" : "value='" + (paginaSelecionada - 1) + "' onclick='paginarItens(this.value);'")
				+ "><a>&laquo;</a></li>");
		if (totalPaginas <= 5) {
			for (int i = 1; i <= totalPaginas; i++) {
				if (i == paginaSelecionada) {
					sb.append("<li id='" + i + "' value='" + i + "' onclick='"+functionName+"' class='active'><a >" + i + "</a></li> ");
				} else {
					sb.append("<li id='" + i + "' value='" + i + "' onclick='"+functionName+"'><a >" + i + "</a></li> ");
				}
			}
		} else {
			if (totalPaginas > 5) {
				if (paginaSelecionada < 1 + (2 * adjacentes)) {
					for (int i = 1; i < 2 + (2 * adjacentes); i++) {
						if (i == paginaSelecionada) {
							sb.append("<li id='" + i + "' value='" + i + "' onclick='"+functionName+"' class='active'><a >" + i + "</a></li> ");
						} else {
							sb.append("<li id='" + i + "' value='" + i + "' onclick='"+functionName+"'><a >" + i + "</a></li> ");
						}
					}
				} else if (paginaSelecionada > (2 * adjacentes) && paginaSelecionada < totalPaginas - 3) {
					for (int i = paginaSelecionada - adjacentes; i <= paginaSelecionada + adjacentes; i++) {
						if (i == paginaSelecionada) {
							sb.append("<li id='" + i + "' value='" + i + "' onclick='"+functionName+"' class='active'><a >" + i + "</a></li> ");
						} else {
							sb.append("<li id='" + i + "' value='" + i + "' onclick='"+functionName+"'><a >" + i + "</a></li> ");
						}
					}
				} else {
					for (int i = totalPaginas - (/* 4 + */(2 * adjacentes)); i <= totalPaginas; i++) {
						if (i == paginaSelecionada) {
							sb.append("<li id='" + i + "' value='" + i + "' onclick='"+functionName+"' class='active'><a >" + i + "</a></li> ");
						} else {
							sb.append("<li id='" + i + "' value='" + i + "' onclick='"+functionName+"'><a >" + i + "</a></li> ");
						}
					}
				}
			}
		}
		sb.append("			<li "
				+ ((totalPaginas == 0 || totalPaginas == 1 || paginaSelecionada.equals(totalPaginas)) ? "class='disabled'" : "value='" + (paginaSelecionada + 1)
						+ "' onclick='"+functionName+"(this.value);'") + " ><a >&raquo;</a></li>");
		sb.append("			<li " + (totalPaginas == 0 || paginaSelecionada.equals(totalPaginas) ? "class='disabled'" : "value='" + totalPaginas + "' onclick='"+functionName+"(this.value);'") + " ><a >"
				+ UtilI18N.internacionaliza(locale, "citcorpore.comum.ultimo") + "</a></li> ");
		sb.append("		</ul>");
		sb.append("	</div>");
		return sb.toString();
	}
	
	/**
	 * Pesquisa os itens da base de conhecimento do portal
	 * 
	 * @author thyen.chang
	 * @since 09/02/2015 - OPERAÇÃO USAIN BOLT
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void pesquisaBaseConhecimento(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception{
		final BaseConhecimentoService baseServicoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, null);
		final ComentariosService comentariosService = (ComentariosService) ServiceLocator.getInstance().getService(ComentariosService.class, null);
		
		final int quantidadePorPagina = Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.QUANT_RETORNO_PESQUISA, "10"));
		document.getElementById("paginaAtualBaseConhecimento").getValue();
		Integer paginaAtual = Integer.parseInt(request.getParameter("paginaAtualBaseConhecimento"));
		if(paginaAtual == null){
			paginaAtual = 0;
		}
		Pageable pageable = new PageRequest(paginaAtual - 1, quantidadePorPagina);
		
		String titulo = request.getParameter("tituloPesquisaBaseConhecimento");
		
		if(this.quantidadePaginasBaseConhecimento == null){
			this.quantidadePaginasBaseConhecimento = baseServicoService.pesquisaBaseConhecimentoPortal(pageable, true, titulo).getTotalPages();
		}
		final List<BaseConhecimentoDTO> listBase = baseServicoService.pesquisaBaseConhecimentoPortal(pageable, false, titulo).getContent();

		final StringBuilder strBase = new StringBuilder();
		final StringBuilder colBase = new StringBuilder();
		strBase.append("<table>");
		strBase.append("	<tr>");
		strBase.append("		<form onsubmit='javascript:;' action='javascript:;'>");
		strBase.append("	</tr>");
		strBase.append("</table>");
		strBase.append("<div id ='tableBc'><table class='table table-bordered table-striped' id='tblBaseConhecimento'>");
		strBase.append("<thead><tr><th>Titulo</th><th>Nota</th></tr></thead><tbody>");
		if (listBase != null) {
			for (final BaseConhecimentoDTO baseDTO : listBase) {
				boolean permissao;
				permissao = this.verificarPermissaoDeAcesso(document, request, response, baseDTO);
				if (permissao) {
					if (baseDTO.getDataFim() == null) {
						final Double nota = comentariosService.calcularNota(baseDTO.getIdBaseConhecimento());
						String nt = null;
						if (nota == null) {
							nt = "S/N";
						} else {
							nt = String.valueOf(nota);
						}
						strBase.append("<tr><td style='text-align: left;'>	<a href='javascript:;' class='passar_mouse' onclick='executaModal(");
						strBase.append(baseDTO.getIdBaseConhecimento());
						strBase.append(")' >");
						strBase.append(UtilStrings.unescapeJavaString(baseDTO.getTitulo()));
						strBase.append("</a></td><td  style='text-align: left;'>");
						strBase.append(nt);
						strBase.append("</td></tr>");
					}
				}
			}
		}
		strBase.append("</tbody></table>");
		colBase.append("	<li class='span12'>" + "<div id='portlet-content-03' class='portlet-content'>" + strBase + "</div>" + "</li>");
		geraPaginacao(this.quantidadePaginasBaseConhecimento, colBase, paginaAtual, request, "pesquisaBaseConhecimento(\"" + titulo + "\", this.value);");
		final HTMLElement divBase = document.getElementById("column4");
		divBase.setInnerHTML(colBase.toString());
		document.executeScript("JANELA_AGUARDE_MENU.hide()");
	}
	
	/**
	 * Pesquisa os itens do FAQ do portal
	 * 
	 * @author thyen.chang
	 * @since 09/02/2015
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void pesquisaFaq(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception{
		final BaseConhecimentoService baseServicoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, null);

		final int quantidadePorPagina = Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.QUANT_RETORNO_PESQUISA, "10"));
		Integer paginaAtual = Integer.parseInt(request.getParameter("paginaAtualFaq"));
		if(paginaAtual == null){
			paginaAtual = 0;
		}
		Pageable pageable = new PageRequest(paginaAtual - 1, quantidadePorPagina);
		
		String titulo = request.getParameter("tituloPesquisaFaq");
		
		if(this.quantidadePaginasFaq == null){
			this.quantidadePaginasFaq = baseServicoService.pesquisaBaseConhecimentoFAQPortal(pageable, true, titulo).getTotalPages();
		}
		

		final Collection<BaseConhecimentoDTO> listBaseFaq = baseServicoService.pesquisaBaseConhecimentoFAQPortal(pageable, false, titulo).getContent();

		final UsuarioDTO usuarioDto = WebUtil.getUsuario(request);

		final StringBuilder strFaq = new StringBuilder();
		if (listBaseFaq != null) {
			final HTMLElement divFaq = document.getElementById("faqs");// Encontrando o elemento no html
			for (final BaseConhecimentoDTO baseDTO : listBaseFaq) {
				if (baseDTO.getDataFim() == null) {

					PastaDTO pastaDto = new PastaDTO();
					if (baseDTO.getIdPasta() != null) {
						pastaDto.setId(baseDTO.getIdPasta());
						pastaDto = (PastaDTO) getPastaService().restore(pastaDto);
					}

					final PermissaoAcessoPasta permissao = getPerfilAcessoPastaService().verificarPermissaoDeAcessoPasta(usuarioDto, pastaDto);

					strFaq.append("<div class='accordion-heading'>");// Inicio div Cabeçalho
					strFaq.append("<a class='accordion-toggle glyphicons circle_question_mark' data-toggle='collapse' data-parent='#accordion' href='#collapse-" + baseDTO.getIdBaseConhecimento()
							+ "'>");
					strFaq.append("<i></i>" + UtilStrings.unescapeJavaString(baseDTO.getTitulo()));// Titulo
					strFaq.append("</a>");
					strFaq.append("</div>");// Fim div cabeçalho

					strFaq.append("<div id='collapse-" + baseDTO.getIdBaseConhecimento() + "' class='accordion-body collapse'>");// Inicio div Corpo
					if (permissao != null) {
						if (PermissaoAcessoPasta.SEMPERMISSAO.equals(permissao)) {
							strFaq.append("<div class='accordion-inner' sytle='text-align:center;'>" + UtilI18N.internacionaliza(request, "baseconhecimento.validacao.usuariosemacesso") + "<br></div>");
						} else {
							strFaq.append("<div class='accordion-inner' sytle='text-align:center;'>" + baseDTO.getConteudo() + "<br></div>");// Corpo do conteudo
						}
					} else {
						strFaq.append("<div class='accordion-inner' sytle='text-align:center;'>" + UtilI18N.internacionaliza(request, "baseconhecimento.validacao.usuariosemacesso") + "<br></div>");
					}

					strFaq.append("</div>");// Fim div corpo

				}// Fim do if
			}// Fim do for
			geraPaginacao(quantidadePaginasFaq, strFaq, paginaAtual, request, "pesquisaFaq(\"" + titulo + "\", this.value);");
			divFaq.setInnerHTML(strFaq.toString());// Adicionando o elemento no html
		}// Fim do if
		document.executeScript("JANELA_AGUARDE_MENU.hide()");
	}

}