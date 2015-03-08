package br.com.centralit.citsmart.rest.service;

import java.util.Collection;

import br.com.centralit.citsmart.rest.bean.RestExecutionDTO;
import br.com.centralit.citsmart.rest.bean.RestOperationDTO;
import br.com.centralit.citsmart.rest.bean.RestSessionDTO;
import br.com.centralit.citsmart.rest.schema.CtMessageResp;
import br.com.citframework.service.CrudService;

public interface RestExecutionService extends CrudService {

    Collection<RestExecutionDTO> findByIdRestOperation(final Integer parm) throws Exception;

    void deleteByIdRestOperation(final Integer parm) throws Exception;

    RestExecutionDTO start(final RestSessionDTO restSession, final RestOperationDTO restOperation, final Object input) throws Exception;

    RestExecutionDTO start(final RestOperationDTO restOperation, final String input) throws Exception;

    void end(final RestOperationDTO restOperation, final RestExecutionDTO restExecution, final CtMessageResp output) throws Exception;

}
