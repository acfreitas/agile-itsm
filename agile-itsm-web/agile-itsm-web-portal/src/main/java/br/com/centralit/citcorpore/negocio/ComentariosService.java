/**
 * 
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.ComentariosDTO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

/**
 * @author valdoilo.damasceno
 * 
 */
public interface ComentariosService extends CrudService {
	
    public void restaurarGridComentarios(DocumentHTML documennt, Collection<ComentariosDTO> comentarios);
    
    public Collection<ComentariosDTO> consultarComentarios(BaseConhecimentoDTO baseConhecimentoBean) throws ServiceException, Exception;
    
    public Double calcularNota(Integer idBaseConhecimento) throws Exception ;
    
    //public ArrayList<ComentariosDTO> restoreByIdBaseConhecimentoByNota(Integer idBaseConhecimento, Integer nota) throws ServiceException, Exception;
    
    public Integer consultarComentariosPorPeriodo(BaseConhecimentoDTO baseConhecimentoDTO) throws ServiceException, Exception;
    
}
