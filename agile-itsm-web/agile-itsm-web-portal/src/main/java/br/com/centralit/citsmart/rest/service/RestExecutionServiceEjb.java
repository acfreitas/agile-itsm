package br.com.centralit.citsmart.rest.service;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.citsmart.rest.bean.RestExecutionDTO;
import br.com.centralit.citsmart.rest.bean.RestOperationDTO;
import br.com.centralit.citsmart.rest.bean.RestSessionDTO;
import br.com.centralit.citsmart.rest.dao.RestExecutionDao;
import br.com.centralit.citsmart.rest.schema.CtMessageResp;
import br.com.centralit.citsmart.rest.util.RestEnum.ExecutionStatus;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilDatas;

import com.google.gson.Gson;

public class RestExecutionServiceEjb extends CrudServiceImpl implements RestExecutionService {

    private static final Gson GSON = new Gson();

    private RestExecutionDao dao;

    @Override
    protected RestExecutionDao getDao() {
        if (dao == null) {
            dao = new RestExecutionDao();
        }
        return dao;
    }

    @Override
    public Collection<RestExecutionDTO> findByIdRestOperation(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdRestOperation(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdRestOperation(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdRestOperation(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public RestExecutionDTO start(final RestSessionDTO session, final RestOperationDTO operation, final Object input) throws Exception {
        final RestExecutionDTO restExecution = this.makeRestExecution(operation, GSON.toJson(input));
        restExecution.setIdUser(session.getUserId());
        return (RestExecutionDTO) this.getExecutionService().create(restExecution);
    }

    @Override
    public RestExecutionDTO start(final RestOperationDTO operation, final String input) throws Exception {
        final RestExecutionDTO execution = this.makeRestExecution(operation, input);
        return (RestExecutionDTO) this.getExecutionService().create(execution);
    }

    private RestExecutionDTO makeRestExecution(final RestOperationDTO operation, final String input) {
        final RestExecutionDTO restExecution = new RestExecutionDTO();
        restExecution.setIdRestOperation(operation.getIdRestOperation());
        restExecution.setInputClass(String.class.getName());
        restExecution.setInputData(input);
        restExecution.setInputDateTime(UtilDatas.getDataHoraAtual());
        restExecution.setStatus(ExecutionStatus.NotInitiated.name());
        return restExecution;
    }

    @Override
    public void end(final RestOperationDTO operation, final RestExecutionDTO execution, final CtMessageResp output) throws Exception {
        ExecutionStatus status = ExecutionStatus.Processed;
        if (output.getError() != null) {
            status = ExecutionStatus.Error;
        }
        execution.setStatus(status.name());
        this.getExecutionService().update(execution);
        if (StringUtils.isBlank(operation.getGenerateLog()) || "Y".equalsIgnoreCase(operation.getGenerateLog())) {
            this.getLogService().create(execution, output, status);
        }
    }

    private RestExecutionService executionService;
    private RestLogService logService;

    private RestExecutionService getExecutionService() {
        if (executionService == null) {
            executionService = new RestExecutionServiceEjb();
        }
        return executionService;
    }

    private RestLogService getLogService() {
        if (logService == null) {
            logService = new RestLogServiceEjb();
        }
        return logService;
    }

}
