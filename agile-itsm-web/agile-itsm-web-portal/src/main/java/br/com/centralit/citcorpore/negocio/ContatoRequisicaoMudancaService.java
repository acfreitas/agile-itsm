package br.com.centralit.citcorpore.negocio;
import br.com.centralit.citcorpore.bean.ContatoRequisicaoMudancaDTO;
import br.com.citframework.service.CrudService;
public interface ContatoRequisicaoMudancaService extends CrudService {
	public ContatoRequisicaoMudancaDTO restoreContatosById(Integer idContatoRequisicaoMudanca);
}
