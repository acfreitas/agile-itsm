package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

public interface ExecucaoDemandaService extends CrudService {
	public Collection getAtividadesByGrupoAndPessoa(Integer idEmpregado, String[] grupo) throws LogicException, ServiceException;
	public void updateAtribuir(IDto bean) throws LogicException, ServiceException;
	public void updateStatus(IDto bean) throws LogicException, ServiceException;
	public void updateFinalizar(IDto bean) throws LogicException, ServiceException;
	public boolean temAtividadeNaSequencia(IDto bean) throws LogicException, ServiceException;
}
