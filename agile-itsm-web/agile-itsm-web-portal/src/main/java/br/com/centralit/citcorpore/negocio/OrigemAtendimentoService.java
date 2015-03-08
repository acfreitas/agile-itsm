package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.OrigemAtendimentoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;
public interface OrigemAtendimentoService extends CrudService {
	
	public void deletarOrigemAtendimento(IDto model, DocumentHTML document) throws ServiceException, Exception;
	public boolean consultarOrigemAtendimentoAtivos(OrigemAtendimentoDTO obj) throws Exception;
	public Collection<OrigemAtendimentoDTO> recuperaAtivos() throws Exception;
	public OrigemAtendimentoDTO buscarOrigemAtendimento(String descricao) throws ServiceException;
}
