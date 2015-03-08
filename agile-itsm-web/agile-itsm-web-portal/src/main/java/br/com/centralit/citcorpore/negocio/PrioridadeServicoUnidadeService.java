package br.com.centralit.citcorpore.negocio;
import br.com.centralit.citcorpore.bean.PrioridadeServicoUnidadeDTO;
import br.com.citframework.service.CrudService;
public interface PrioridadeServicoUnidadeService extends CrudService {
    public PrioridadeServicoUnidadeDTO restore(Integer idServicoContrato, Integer idUnidade) throws Exception;
}
