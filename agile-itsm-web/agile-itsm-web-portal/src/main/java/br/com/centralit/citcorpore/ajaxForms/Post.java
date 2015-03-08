package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.CategoriaPostDTO;
import br.com.centralit.citcorpore.bean.PostDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CategoriaPostService;
import br.com.centralit.citcorpore.negocio.PostService;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

/**
 * @author Flávio.santana
 *
 */
public class Post extends AjaxFormAction {

    @SuppressWarnings("unchecked")
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response)
	    throws Exception {
    	
    	UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
    	if(usrDto == null){
    		return;
    	}
    	
    	request.getSession(true).setAttribute("colUploadsGED",null);
    	
    	CategoriaPostService categoriaServicoService = (CategoriaPostService) ServiceLocator.getInstance().getService(CategoriaPostService.class, null);
    	HTMLSelect comboCategoriaServicoSuperior = (HTMLSelect) document.getSelectById("idCategoriaPost");
		comboCategoriaServicoSuperior.removeAllOptions();

		Collection<CategoriaPostDTO> categoriasServicoSuperior = categoriaServicoService.listCategoriasAtivas();
		
		if (categoriasServicoSuperior != null && !categoriasServicoSuperior.isEmpty()) {
			comboCategoriaServicoSuperior.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
			comboCategoriaServicoSuperior.addOptions(categoriasServicoSuperior, "idCategoriaPost", "nomeCategoria", null);
		} else {
			comboCategoriaServicoSuperior.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		}
		
		trocaImagem(document, request, response);
	
    }

    public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	request.getSession(true).setAttribute("colUploadsGED",null);
    	
		PostDTO post = (PostDTO) document.getBean();
		PostService PostService = (PostService) ServiceLocator.getInstance().getService(PostService.class, null);
	
		if (post.getIdPost() == null || post.getIdPost().intValue() == 0) {
			post.setDataInicio(UtilDatas.getDataAtual());
		    PostService.create(post);		    
		    document.alert(UtilI18N.internacionaliza(request, "MSG05"));
		} else {
		    PostService.update(post);
		    document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}
		document.executeScript("limpar()");		
		trocaImagem(document, request, response);
    }

    public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PostDTO post = (PostDTO) document.getBean();
		PostService PostService = (PostService) ServiceLocator.getInstance().getService(PostService.class, null);
	
		post = (PostDTO) PostService.restore(post);
	
		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(post);
		document.executeScript("setDataEditor()");
		document.executeScript("$('#img').attr('src', '" + ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.URL_Sistema, "") + "/tempUpload/" + post.getImagem() + "');" +
  				"$('#img').show();");
    }

	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getSession(true).setAttribute("colUploadsGED",null);
		PostDTO post = (PostDTO) document.getBean();
		PostService postService = (PostService) ServiceLocator.getInstance().getService(PostService.class, null);

		if (post != null && post.getIdPost() != null) 
		{
			post.setDataFim(UtilDatas.getDataAtual());
			postService.update(post);
		}
		trocaImagem(document, request, response);
		document.executeScript("limpar()");
		document.alert(UtilI18N.internacionaliza(request, "MSG07"));

	}

    @SuppressWarnings("unchecked")
	public void trocaImagem(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	PostDTO post = (PostDTO) document.getBean();
		ArrayList<UploadDTO> colUploadsGED = (ArrayList<UploadDTO>)request.getSession(true).getAttribute("colUploadsGED");
		if (colUploadsGED == null){
			colUploadsGED = new ArrayList<UploadDTO>();
		}
		if(!colUploadsGED.isEmpty()) {

      		document.executeScript("$('#img').attr('src', '" + ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.URL_Sistema, "") + "/tempUpload/" + colUploadsGED.get(0).getNameFile() + "');" +
      				"$('#img').show();$('#imagem').val('"+ colUploadsGED.get(0).getNameFile() + "')");
      	} 
      	else
      	{
      		if(post.getImagem() == null || post.getImagem().equals(""))
			{
      			document.executeScript("$('#img').attr('src', '');$('#img').hide();$('#imagem').val('')");
			}      		
      		
		 }
    }
    
    public void limpar(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HTMLForm form = document.getForm("form");
		form.clear();
		document.executeScript("$('#img').attr('src', '');$('#img').hide();$('#imagem').val('')");
    }
    
    @SuppressWarnings("rawtypes")
    public Class getBeanClass() {
		return PostDTO.class;
    }
}
