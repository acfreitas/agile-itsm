package br.com.centralit.citsmart.rest.service;

import java.util.Collection;

import br.com.centralit.citsmart.rest.bean.RestDomainDTO;
import br.com.citframework.service.CrudService;

public interface RestDomainService extends CrudService {

    Collection<RestDomainDTO> findByIdRestOperation(final Integer parm) throws Exception;

    void deleteByIdRestOperation(final Integer parm) throws Exception;

}
