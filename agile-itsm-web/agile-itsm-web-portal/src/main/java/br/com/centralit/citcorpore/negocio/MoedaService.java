package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.MoedaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.service.CrudService;

public interface MoedaService extends CrudService {

	public boolean verificaSeCadastrado(MoedaDTO moedaDTO) throws PersistenceException;
	
	public boolean verificaRelacionamento(MoedaDTO moedaDTO) throws Exception;
	
	public void updateNotNull(IDto obj) throws Exception;
	@SuppressWarnings("rawtypes")
	public Collection findAllAtivos() throws Exception;
}
