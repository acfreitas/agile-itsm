package br.com.centralit.citcorpore.negocio;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.AlcadaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;
public interface AlcadaService extends CrudService {
    public void deletarAlcada(IDto model, DocumentHTML document) throws ServiceException, Exception;
    public boolean existeIgual(AlcadaDTO alcada) throws Exception;
}
