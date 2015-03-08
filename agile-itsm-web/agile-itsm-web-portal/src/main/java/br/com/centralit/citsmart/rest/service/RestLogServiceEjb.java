package br.com.centralit.citsmart.rest.service;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.centralit.citsmart.rest.bean.RestExecutionDTO;
import br.com.centralit.citsmart.rest.bean.RestLogDTO;
import br.com.centralit.citsmart.rest.dao.RestLogDao;
import br.com.centralit.citsmart.rest.schema.CtError;
import br.com.centralit.citsmart.rest.util.RestEnum;
import br.com.centralit.citsmart.rest.util.RestEnum.ExecutionStatus;
import br.com.centralit.citsmart.rest.util.RestUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilDatas;

import com.google.gson.Gson;

public class RestLogServiceEjb extends CrudServiceImpl implements RestLogService {

    private static final Gson GSON = new Gson();

    private static final Logger LOGGER = Logger.getLogger(RestLogServiceEjb.class.getName());

    private RestLogDao dao;

    @Override
    protected RestLogDao getDao() {
        if (dao == null) {
            dao = new RestLogDao();
        }
        return dao;
    }

    @Override
    public Collection<RestLogDTO> findByIdRestExecution(final Integer parm) throws ServiceException {
        try {
            return this.getDao().findByIdRestExecution(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdRestExecution(final Integer parm) throws ServiceException {
        try {
            this.getDao().deleteByIdRestExecution(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public CtError create(final RestExecutionDTO restExecution, final Exception e) {
        final CtError error = new CtError();
        error.setCode(RestEnum.INTERNAL_ERROR);
        error.setDescription(RestUtil.stackToString(e));
        this.create(restExecution, error, ExecutionStatus.Error);
        return error;
    }

    @Override
    public CtError create(final RestExecutionDTO restExecution, final String code, final String description) {
        final CtError error = new CtError();
        error.setCode(code);
        error.setDescription(description);
        this.create(restExecution, error, ExecutionStatus.Error);
        return error;
    }

    @Override
    public RestLogDTO create(final RestExecutionDTO restExecution, final Object result, final ExecutionStatus status) {
        RestLogDTO restLog = new RestLogDTO();
        restLog.setIdRestExecution(restExecution.getIdRestExecution());
        restLog.setDateTime(UtilDatas.getDataHoraAtual());
        restLog.setStatus(status.name());
        restLog.setResultClass(result.getClass().getName());
        restLog.setResultData(GSON.toJson(result));

        try {
            restLog = (RestLogDTO) this.create(restLog);
        } catch (final Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }

        return restLog;
    }

}
