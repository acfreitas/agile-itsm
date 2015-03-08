package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.integracao.EmpregadoDao;
import br.com.centralit.citcorpore.integracao.RequisitoSLADao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilStrings;

public class RequisitoSLAServiceEjb extends CrudServiceImpl implements RequisitoSLAService {

    private RequisitoSLADao dao;

    @Override
    protected RequisitoSLADao getDao() {
        if (dao == null) {
            dao = new RequisitoSLADao();
        }
        return dao;
    }

    @Override
    public Collection findByIdEmpregado(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdEmpregado(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdEmpregado(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdEmpregado(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findById(final Integer idRequisitoSla) throws Exception {
        try {
            return this.getDao().findById(idRequisitoSla);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public String verificaIdSolicitante(final HashMap mapFields) throws Exception {
        final EmpregadoDao empregadoDao = new EmpregadoDao();
        List<EmpregadoDTO> listaEmpregado = null;
        String id = mapFields.get("IDEMPREGADO").toString();

        if (id == null || id.equals("")) {
            id = "0";
        }

        if (UtilStrings.soContemNumeros(id.trim())) {
            Integer idEmpregado;
            idEmpregado = Integer.parseInt(id.trim());
            listaEmpregado = empregadoDao.findByIdEmpregado(idEmpregado);
        } else {
            listaEmpregado = empregadoDao.findByNomeEmpregado(id);
        }

        if (listaEmpregado != null && listaEmpregado.size() > 0) {
            return String.valueOf(listaEmpregado.get(0).getIdEmpregado());
        } else {
            return "0";
        }
    }

}
