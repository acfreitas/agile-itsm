package br.com.centralit.citsmart.rest.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.centralit.citsmart.rest.bean.RestDomainDTO;
import br.com.centralit.citsmart.rest.bean.RestOperationDTO;
import br.com.centralit.citsmart.rest.bean.RestParameterDTO;
import br.com.centralit.citsmart.rest.bean.RestSessionDTO;
import br.com.centralit.citsmart.rest.dao.RestParameterDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.service.ServiceLocator;

public class RestParameterServiceEjb extends CrudServiceImpl implements RestParameterService {

    private RestParameterDao dao;

    @Override
    protected RestParameterDao getDao() {
        if (dao == null) {
            dao = new RestParameterDao();
        }
        return dao;
    }

    @Override
    public IDto create(final IDto model) throws ServiceException, LogicException {
        RestParameterDTO restDto = (RestParameterDTO) model;

        final TransactionControler transaction = new TransactionControlerImpl(this.getDao().getAliasDB());

        final RestParameterDao crudDao = this.getDao();

        try {
            transaction.start();

            crudDao.setTransactionControler(transaction);

            restDto = (RestParameterDTO) crudDao.create(restDto);

            transaction.commit();
            transaction.close();

        } catch (final Exception e) {
            this.rollbackTransaction(transaction, e);
        }

        return restDto;
    }

    @Override
    public RestParameterDTO findByIdentifier(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdentifier(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Map<String, RestDomainDTO> findParameters(final RestSessionDTO restSessionDto, final RestOperationDTO restOperationDto) {
        final Map<String, RestDomainDTO> mapParam = new HashMap<String, RestDomainDTO>();
        try {
            final RestDomainService restDomainService = (RestDomainService) ServiceLocator.getInstance().getService(RestDomainService.class, null);
            final List<RestDomainDTO> params = (List<RestDomainDTO>) restDomainService.findByIdRestOperation(restOperationDto.getIdRestOperation());
            if (params != null) {
                final RestParameterService restParameterService = (RestParameterService) ServiceLocator.getInstance().getService(RestParameterService.class, null);
                for (final RestDomainDTO domainDto : params) {
                    RestParameterDTO paramDto = new RestParameterDTO();
                    paramDto.setIdRestParameter(domainDto.getIdRestParameter());
                    paramDto = (RestParameterDTO) restParameterService.restore(paramDto);
                    if (paramDto != null) {
                        mapParam.put(paramDto.getIdentifier(), domainDto);
                    }
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
        return mapParam;
    }

    @Override
    public String getParamValue(final Map<String, RestDomainDTO> mapParam, final String key) {
        final RestDomainDTO restDomainDto = mapParam.get(key);
        if (restDomainDto == null || restDomainDto.getValue() == null) {
            return null;
        }
        return restDomainDto.getValue().trim();
    }

}
