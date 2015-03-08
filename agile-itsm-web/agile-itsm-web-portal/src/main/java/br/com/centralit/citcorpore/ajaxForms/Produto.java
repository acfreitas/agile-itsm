package br.com.centralit.citcorpore.ajaxForms;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.ProdutoDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.negocio.CategoriaProdutoService;
import br.com.centralit.citcorpore.negocio.ProdutoService;
import br.com.centralit.citcorpore.util.CriptoUtils;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.Util;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.integracao.ControleGEDDao;
import br.com.centralit.citged.negocio.ControleGEDService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

@SuppressWarnings("rawtypes")
public class Produto extends AjaxFormAction {

	public void load(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.getSession(true).setAttribute("colUploadsGED", null);

	}

	@Override
	public Class getBeanClass() {
		return ProdutoDTO.class;
	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ProdutoDTO produtos = (ProdutoDTO) document.getBean();
		ProdutoService produtosService = (ProdutoService) ServiceLocator.getInstance().getService(ProdutoService.class, null);
		
		//if (produtos.getPrecoMercado() == null || produtos.getPrecoMercado().doubleValue() == 0)
		//    document.alert(UtilI18N.internacionaliza(request, "produto.precoMercado")+" "+UtilI18N.internacionaliza(request,("citcorpore.comum.naoInformado")));
		
		if (produtos.getIdProduto() == null || produtos.getIdProduto() == 0) {
			// Verificar se um DTO com o mesmo codigoProduto já existe
		    if (produtos.getCodigoProduto() != null && produtos.getCodigoProduto().trim().length() > 0) {
    			List lista = (List) produtosService.validaNovoProduto(produtos); 
    			if (lista != null && !lista.isEmpty() ) {
    				document.alert(UtilI18N.internacionaliza(request, "produto.produto.jaCadastrado") );
    				return;
    			}
		    }
			produtosService.create(produtos);
			this.gravarGed(document, request, produtos.getIdProduto());
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
		} else {
			produtosService.update(produtos);
			this.gravarGed(document, request, produtos.getIdProduto());
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}
		HTMLForm form = document.getForm("form");
		clear(document, request, response);
	}

	public void delete(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ProdutoDTO produtos = (ProdutoDTO) document.getBean();

		ProdutoService produtosService = (ProdutoService) ServiceLocator
				.getInstance().getService(ProdutoService.class,
						WebUtil.getUsuarioSistema(request));

		
		 if (produtos.getIdProduto().intValue() > 0) {
			 produtosService.delete(produtos); 
			 document.alert(UtilI18N.internacionaliza(request, "MSG07"));
		 }
		 
		HTMLForm form = document.getForm("form");
		form.clear();
		request.getSession(true).setAttribute("colUploadsGED", null);
	}

	public void restore(DocumentHTML document, HttpServletRequest request,	HttpServletResponse response) throws Exception {
		request.getSession(true).setAttribute("colUploadsGED", null);
		document.executeScript("uploadAnexos.refresh()");
		ProdutoDTO produtos = (ProdutoDTO) document.getBean();
		ProdutoService produtosService = (ProdutoService) ServiceLocator
				.getInstance().getService(ProdutoService.class, null);
		produtos = (ProdutoDTO) produtosService.restore(produtos);
		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(produtos);
		document.executeScript("uploadAnexos.refresh()");
		restaurarAnexos(request, produtos.getIdProduto());
	}
	
	protected void restaurarAnexos(HttpServletRequest request, Integer idCategoria) throws ServiceException, Exception {
		ControleGEDService controleGedService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
		Collection colAnexos = controleGedService.listByIdTabelaAndID(ControleGEDDTO.TABELA_PRODUTO, idCategoria);
		Collection colAnexosUploadDTO = controleGedService.convertListControleGEDToUploadDTO(colAnexos);

		request.getSession(true).setAttribute("colUploadsGED", colAnexosUploadDTO);
	}
	
	public void gravarGed(DocumentHTML document, HttpServletRequest request, Integer id) throws ServiceException, Exception{
		CategoriaProdutoService categoriaService = (CategoriaProdutoService) ServiceLocator.getInstance().getService(CategoriaProdutoService.class, null);
		ControleGEDService controleGEDService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
		ControleGEDDao controleGEDDao = new ControleGEDDao();
		String prontuarioGedInternoBancoDados = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedInternoBD, "N");

		if (!UtilStrings.isNotVazio(prontuarioGedInternoBancoDados)) {
			prontuarioGedInternoBancoDados = "N";
		}

		String PRONTUARIO_GED_DIRETORIO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedDiretorio, "/usr/local/gedCitsmart/");

		String PRONTUARIO_GED_INTERNO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedInterno, "S");
		
		Collection<UploadDTO> fotos = (Collection<UploadDTO>) request.getSession(true).getAttribute("colUploadsGED");
		
		
		String pasta = controleGEDService.getProximaPastaArmazenar();
		if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S")) {
			pasta = controleGEDService.getProximaPastaArmazenar();
			File fileDir = new File(PRONTUARIO_GED_DIRETORIO);

			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}

			fileDir = new File(PRONTUARIO_GED_DIRETORIO + "/1");

			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}

			fileDir = new File(PRONTUARIO_GED_DIRETORIO + "/1/" + pasta);
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}
		}
		
		ControleGEDService controleGedService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
		Collection colAnexos = controleGedService.listByIdTabelaAndID(ControleGEDDTO.TABELA_PRODUTO, id);
		Collection colAnexosUploadDTO = controleGedService.convertListControleGEDToUploadDTO(colAnexos);

		if(colAnexosUploadDTO != null) {
			for (Iterator iterator = colAnexosUploadDTO.iterator(); iterator.hasNext();) {
				UploadDTO object = (UploadDTO) iterator.next();
				controleGedService.delete(object);
			}
		}
		
		if (fotos != null && !fotos.isEmpty()) {
			ControleGEDDTO controleGEDDTO = new ControleGEDDTO();
			for (UploadDTO uploadDto : fotos) {
				//if(uploadDto.getIdControleGED() == null || uploadDto.getIdControleGED() == 0) {
					controleGEDDTO.setIdTabela(ControleGEDDTO.TABELA_PRODUTO);
					controleGEDDTO.setId(id);
					controleGEDDTO.setDataHora(UtilDatas.getDataAtual());
					controleGEDDTO.setDescricaoArquivo(uploadDto.getDescricao());
					controleGEDDTO.setExtensaoArquivo(Util.getFileExtension(uploadDto.getNameFile()));
					controleGEDDTO.setPasta(pasta);
					controleGEDDTO.setNomeArquivo(uploadDto.getNameFile());
					controleGEDDTO = (ControleGEDDTO) controleGEDDao.create(controleGEDDTO);

					if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S") && !"S".equalsIgnoreCase(prontuarioGedInternoBancoDados)) {

						try {
							if (controleGEDDTO != null) {

								File arquivo = new File(PRONTUARIO_GED_DIRETORIO + "/1/" + pasta + "/" + controleGEDDTO.getIdControleGED() + "."
										+ Util.getFileExtension(uploadDto.getNameFile()));

								CriptoUtils.encryptFile(uploadDto.getPath(), PRONTUARIO_GED_DIRETORIO + "/1/" + pasta + "/" + controleGEDDTO.getIdControleGED()
										+ ".ged", System.getProperties().get("user.dir") + Constantes.getValue("CAMINHO_CHAVE_PUBLICA"));

								arquivo.delete();
							}
						} catch (Exception e) {
							e.printStackTrace();
							System.out.println("Não foi possível encriptar o arquivo");
						}
					}
				//}				
			}
			
		}
	}
	
	public void clear(DocumentHTML document, HttpServletRequest request,	HttpServletResponse response) throws ServiceException, Exception{
		request.getSession(true).setAttribute("colUploadsGED", null);
		HTMLForm form = document.getForm("form");
		form.clear();
		document.executeScript("uploadAnexos.refresh()");
	}
}
