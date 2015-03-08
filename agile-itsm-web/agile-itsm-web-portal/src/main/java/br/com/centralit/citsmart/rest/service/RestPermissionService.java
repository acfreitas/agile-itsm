package br.com.centralit.citsmart.rest.service;

import java.util.Collection;

import br.com.centralit.citsmart.rest.bean.RestOperationDTO;
import br.com.centralit.citsmart.rest.bean.RestPermissionDTO;
import br.com.centralit.citsmart.rest.bean.RestSessionDTO;
import br.com.citframework.service.CrudService;

public interface RestPermissionService extends CrudService {

    Collection<RestPermissionDTO> findByIdOperation(final Integer parm) throws Exception;

    void deleteByIdOperation(final Integer parm) throws Exception;

    Collection<RestPermissionDTO> findByIdGroup(final Integer parm) throws Exception;

    void deleteByIdGroup(final Integer parm) throws Exception;

    boolean allowedAccess(final RestSessionDTO restSessionDto, final RestOperationDTO restOperationDto);

}
