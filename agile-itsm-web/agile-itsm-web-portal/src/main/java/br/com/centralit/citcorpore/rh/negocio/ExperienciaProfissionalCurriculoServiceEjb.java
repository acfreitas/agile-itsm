package br.com.centralit.citcorpore.rh.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.rh.bean.ExperienciaProfissionalCurriculoDTO;
import br.com.centralit.citcorpore.rh.integracao.ExperienciaProfissionalCurriculoDao;
import br.com.centralit.citcorpore.rh.integracao.FuncaoExperienciaProfissionalCurriculoDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class ExperienciaProfissionalCurriculoServiceEjb extends CrudServiceImpl implements ExperienciaProfissionalCurriculoService {

    private ExperienciaProfissionalCurriculoDao dao;
    private FuncaoExperienciaProfissionalCurriculoDao funcaoDao;

    @Override
    protected ExperienciaProfissionalCurriculoDao getDao() {
        if (dao == null) {
            dao = new ExperienciaProfissionalCurriculoDao();
        }
        return dao;
    }

    private FuncaoExperienciaProfissionalCurriculoDao getFuncaoDao() {
        if (funcaoDao == null) {
            funcaoDao = new FuncaoExperienciaProfissionalCurriculoDao();
        }
        return funcaoDao;
    }

    @Override
    public IDto restore(final IDto model) throws ServiceException, LogicException {
        try {
            ExperienciaProfissionalCurriculoDTO experienciaProfissionalCurriculoDto = (ExperienciaProfissionalCurriculoDTO) model;

            final Collection funcaoExperiencia = this.getFuncaoDao().findByIdExperienciaProfissional(experienciaProfissionalCurriculoDto.getIdExperienciaProfissional());

            experienciaProfissionalCurriculoDto = (ExperienciaProfissionalCurriculoDTO) this.getDao().restore(experienciaProfissionalCurriculoDto);

            experienciaProfissionalCurriculoDto.setColFuncao(funcaoExperiencia);

            return experienciaProfissionalCurriculoDto;
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
