package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudService;
public interface InspecaoPedidoCompraService extends CrudService {
	public Collection findByIdPedido(Integer parm) throws Exception;
	public void deleteByIdPedido(Integer parm) throws Exception;
}
