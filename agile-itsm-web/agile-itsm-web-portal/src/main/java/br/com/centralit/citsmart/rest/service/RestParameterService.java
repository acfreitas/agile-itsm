package br.com.centralit.citsmart.rest.service;

import java.util.Map;

import br.com.centralit.citsmart.rest.bean.RestDomainDTO;
import br.com.centralit.citsmart.rest.bean.RestOperationDTO;
import br.com.centralit.citsmart.rest.bean.RestParameterDTO;
import br.com.centralit.citsmart.rest.bean.RestSessionDTO;
import br.com.citframework.service.CrudService;

public interface RestParameterService extends CrudService {

    RestParameterDTO findByIdentifier(final Integer parm) throws Exception;

    Map<String, RestDomainDTO> findParameters(final RestSessionDTO restSessionDto, final RestOperationDTO restOperationDto);

    String getParamValue(final Map<String, RestDomainDTO> mapParam, final String key);

}
