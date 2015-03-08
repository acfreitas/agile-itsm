package br.com.centralit.citsmart.rest.service;

import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citsmart.rest.bean.RestOperationDTO;
import br.com.centralit.citsmart.rest.bean.RestPermissionDTO;
import br.com.centralit.citsmart.rest.bean.RestSessionDTO;
import br.com.centralit.citsmart.rest.dao.RestPermissionDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class RestPermissionServiceEjb extends CrudServiceImpl implements RestPermissionService {

    private RestPermissionDao dao;

    @Override
    protected RestPermissionDao getDao() {
        if (dao == null) {
            dao = new RestPermissionDao();
        }
        return dao;
    }

    @Override
    public Collection<RestPermissionDTO> findByIdOperation(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdOperation(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdOperation(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdOperation(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection<RestPermissionDTO> findByIdGroup(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdGroup(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdGroup(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdGroup(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean allowedAccess(final RestSessionDTO restSessionDto, final RestOperationDTO restOperationDto) {
        final UsuarioDTO usuarioDto = restSessionDto.getUser();
        if (usuarioDto == null || usuarioDto.getColGrupos() == null) {
            return false;
        }
        boolean result = false;
        for (final GrupoDTO grupoDto : usuarioDto.getColGrupos()) {
            final RestPermissionDTO restPermissionDto = new RestPermissionDTO();
            restPermissionDto.setIdRestOperation(restOperationDto.getIdRestOperation());
            restPermissionDto.setIdGroup(grupoDto.getIdGrupo());
            List<RestPermissionDTO> list = null;
            try {
                list = (List<RestPermissionDTO>) this.find(restPermissionDto);
            } catch (final Exception e) {
                e.printStackTrace();
            }
            if (list != null && !list.isEmpty() && list.get(0).getStatus().trim().equals("A")) {
                result = true;
                break;
            }
        }
        return result;
    }

}
