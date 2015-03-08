package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudService;
public interface ItemPedidoCompraService extends CrudService {
	public Collection findByIdPedido(Integer parm) throws Exception;
	public void deleteByIdPedido(Integer parm) throws Exception;
	public double obtemQtdePedidoColetaPreco(Integer idColetaPreco) throws Exception;
	public Collection findByIdPedidoOrderByIdSolicitacao(Integer idPedido) throws Exception;
}
