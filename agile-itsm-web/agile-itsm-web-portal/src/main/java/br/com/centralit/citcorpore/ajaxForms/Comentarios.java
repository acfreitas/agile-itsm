package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.ComentariosDTO;
import br.com.centralit.citcorpore.negocio.BaseConhecimentoService;
import br.com.centralit.citcorpore.negocio.ComentariosService;
import br.com.centralit.citcorpore.negocio.ContadorAcessoService;
import br.com.centralit.lucene.Lucene;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;



public class Comentarios extends AjaxFormAction {

    @SuppressWarnings("rawtypes")
    @Override
    public Class getBeanClass() {

	return ComentariosDTO.class;
    }

    @Override
    public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	
    }
    
    public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	   
		ComentariosDTO comentariosDto = (ComentariosDTO) document.getBean();
	
		ComentariosService comentariosService = (ComentariosService) ServiceLocator.getInstance().getService(ComentariosService.class, null);
		if (comentariosDto.getIdComentario() == null || comentariosDto.getIdComentario().intValue() == 0) {
		    if(comentariosDto.getNota()==null){
			comentariosDto.setNota("0");
		    }
		    if(!comentariosDto.getComentario().equalsIgnoreCase("")){
			comentariosDto.setDataInicio(UtilDatas.getDataAtual());
			Integer idBaseConhecimento = (Integer)request.getSession().getAttribute("idBaseConhecimento");
			comentariosDto.setIdBaseConhecimento(idBaseConhecimento);
			comentariosService.create(comentariosDto);
			
			//Atualizando índice Lucene
			// Avaliação - Média da nota dada pelos usuários
			BaseConhecimentoService baseConhecimentoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, null);
			BaseConhecimentoDTO baseConhecimentoDto = new BaseConhecimentoDTO();
			baseConhecimentoDto.setIdBaseConhecimento(idBaseConhecimento);
			baseConhecimentoDto = (BaseConhecimentoDTO) baseConhecimentoService.restore(baseConhecimentoDto);
			Double media = baseConhecimentoService.calcularNota(baseConhecimentoDto.getIdBaseConhecimento());
			if (media != null)
				baseConhecimentoDto.setMedia(media.toString());
			else
				baseConhecimentoDto.setMedia(null);
			ContadorAcessoService contadorAcessoService = (ContadorAcessoService) ServiceLocator.getInstance().getService(ContadorAcessoService.class, null);
			// Qtde de cliques
			Integer quantidadeDeCliques = contadorAcessoService.quantidadesDeAcessoPorBaseConhecimnto(baseConhecimentoDto);
			if (quantidadeDeCliques != null)
				baseConhecimentoDto.setContadorCliques(quantidadeDeCliques);
			else
				baseConhecimentoDto.setContadorCliques(0);
			Lucene lucene = new Lucene();
			lucene.indexarBaseConhecimento(baseConhecimentoDto);
			
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
			
		    }else{
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.camposObrigatorios"));
			document.executeScript("document.getElementById('comentario').focus();");
			return;
		    }
		    
			
		} else {
		    comentariosService.update(comentariosDto);
		    document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}
		
		 HTMLForm form = document.getForm("formPopup");
		    
		 form.clear();
		 document.executeScript("fecharPopup()");
		 
			/* @autor edu.braz
			 *  05/03/2014 */	
			/* atualiza a quantidade de comentarios.*/
		 Integer idBaseConhecimento = (Integer)request.getSession().getAttribute("idBaseConhecimento");	 
		 document.executeScript("comentariosAbertosPorBaseConhecimnto(" + idBaseConhecimento + ");");//Chamada da função que esta dentro de baseConhecimentoView.jsp 		 
    }
}
