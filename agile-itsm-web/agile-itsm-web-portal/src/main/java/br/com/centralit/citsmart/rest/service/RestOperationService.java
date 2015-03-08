package br.com.centralit.citsmart.rest.service;

import java.util.Collection;

import br.com.centralit.citsmart.rest.bean.RestOperationDTO;
import br.com.citframework.service.CrudService;

public interface RestOperationService extends CrudService {

    Collection<RestOperationDTO> findByIdBatchProcessing(final Integer parm) throws Exception;

    void deleteByIdBatchProcessing(final Integer parm) throws Exception;

    RestOperationDTO findByName(final String name);

}
