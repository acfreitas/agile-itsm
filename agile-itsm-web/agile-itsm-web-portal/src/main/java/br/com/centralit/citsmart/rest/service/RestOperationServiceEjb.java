package br.com.centralit.citsmart.rest.service;

import java.util.Collection;

import br.com.centralit.citsmart.rest.bean.RestDomainDTO;
import br.com.centralit.citsmart.rest.bean.RestOperationDTO;
import br.com.centralit.citsmart.rest.bean.RestPermissionDTO;
import br.com.centralit.citsmart.rest.dao.RestDomainDao;
import br.com.centralit.citsmart.rest.dao.RestOperationDao;
import br.com.centralit.citsmart.rest.dao.RestPermissionDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;

public class RestOperationServiceEjb extends CrudServiceImpl implements RestOperationService {

    private RestOperationDao dao;

    @Override
    protected RestOperationDao getDao() {
        if (dao == null) {
            dao = new RestOperationDao();
        }
        return dao;
    }

    /**
     * Cria usuário e perfil acesso.
     *
     * @see br.com.citframework.service.CrudServiceImpl#create(br.com.citframework.dto.IDto)
     */
    @Override
    public IDto create(final IDto model) throws ServiceException, LogicException {
        RestOperationDTO restDto = (RestOperationDTO) model;

        final TransactionControler transaction = new TransactionControlerImpl(this.getDao().getAliasDB());
        final RestPermissionDao restPerDao = new RestPermissionDao();
        final RestDomainDao restDomainDao = new RestDomainDao();
        final RestOperationDao crudDao = this.getDao();

        try {
            transaction.start();

            this.validaCreate(restDto);

            restPerDao.setTransactionControler(transaction);
            crudDao.setTransactionControler(transaction);
            restDomainDao.setTransactionControler(transaction);

            restDto = (RestOperationDTO) crudDao.create(restDto);
            if (restDto.getColGrupos() != null) {
                for (final RestPermissionDTO grupoDTO : restDto.getColGrupos()) {
                    grupoDTO.setIdRestOperation(restDto.getIdRestOperation());
                    grupoDTO.setIdGroup(grupoDTO.getIdGroup());
                    grupoDTO.setStatus(restDto.getStatus());

                    restPerDao.create(grupoDTO);
                }
            }

            if (restDto.getColDominios() != null) {
                for (final RestDomainDTO dominioDto : restDto.getColDominios()) {
                    dominioDto.setIdRestOperation(restDto.getIdRestOperation());
                    restDomainDao.create(dominioDto);
                }
            }

            transaction.commit();
            transaction.close();

        } catch (final Exception e) {
            this.rollbackTransaction(transaction, e);
        }

        return restDto;
    }

    @Override
    public void update(final IDto model) throws ServiceException, LogicException {
        final RestOperationDTO restDto = (RestOperationDTO) model;

        final TransactionControler transaction = new TransactionControlerImpl(this.getDao().getAliasDB());
        final RestPermissionDao restPerDao = new RestPermissionDao();
        final RestDomainDao restDomainDao = new RestDomainDao();
        final RestOperationDao crudDao = this.getDao();

        try {
            this.validaUpdate(model);

            restPerDao.setTransactionControler(transaction);
            crudDao.setTransactionControler(transaction);
            restDomainDao.setTransactionControler(transaction);

            transaction.start();

            crudDao.update(restDto);
            restPerDao.deleteByIdOperation(restDto.getIdRestOperation());
            if (restDto.getColGrupos() != null) {
                for (final RestPermissionDTO grupoDTO : restDto.getColGrupos()) {
                    grupoDTO.setIdRestOperation(restDto.getIdRestOperation());
                    grupoDTO.setIdGroup(grupoDTO.getIdGroup());
                    grupoDTO.setStatus(restDto.getStatus());

                    restPerDao.create(grupoDTO);
                }
            }

            restDomainDao.deleteByIdRestOperation(restDto.getIdRestOperation());
            if (restDto.getColDominios() != null) {
                for (final RestDomainDTO dominioDto : restDto.getColDominios()) {
                    dominioDto.setIdRestOperation(restDto.getIdRestOperation());
                    restDomainDao.create(dominioDto);
                }
            }

            transaction.commit();
            transaction.close();

        } catch (final Exception e) {
            this.rollbackTransaction(transaction, e);
        }
    }

    @Override
    public Collection<RestOperationDTO> findByIdBatchProcessing(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdBatchProcessing(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdBatchProcessing(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdBatchProcessing(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public RestOperationDTO findByName(final String name) {
        try {
            return this.getDao().findByName(name);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
