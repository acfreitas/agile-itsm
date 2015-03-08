package br.com.centralit.citsmart.rest.service;

import java.util.Collection;

import br.com.centralit.citsmart.rest.bean.RestExecutionDTO;
import br.com.centralit.citsmart.rest.bean.RestLogDTO;
import br.com.centralit.citsmart.rest.schema.CtError;
import br.com.centralit.citsmart.rest.util.RestEnum.ExecutionStatus;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

public interface RestLogService extends CrudService {

    Collection<RestLogDTO> findByIdRestExecution(final Integer parm) throws ServiceException;

    void deleteByIdRestExecution(final Integer parm) throws ServiceException;

    CtError create(final RestExecutionDTO restExecutionDto, final String code, final String description);

    CtError create(final RestExecutionDTO restExecutionDto, final Exception e);

    RestLogDTO create(final RestExecutionDTO restExecutionDto, final Object result, final ExecutionStatus status);

}
