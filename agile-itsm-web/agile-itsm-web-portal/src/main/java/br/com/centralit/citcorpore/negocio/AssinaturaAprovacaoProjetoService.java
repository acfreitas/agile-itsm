package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.AssinaturaAprovacaoProjetoDTO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

public interface AssinaturaAprovacaoProjetoService extends CrudService {

    Collection<AssinaturaAprovacaoProjetoDTO> findByIdProjeto(final Integer parm) throws ServiceException;

    void deleteByIdProjeto(final Integer parm) throws ServiceException;

    Collection<AssinaturaAprovacaoProjetoDTO> findByIdEmpregado(final Integer parm) throws ServiceException;

    void deleteByIdEmpregado(final Integer parm) throws ServiceException;

}
