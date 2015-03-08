package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.GrupoNivelAutoridadeDTO;
import br.com.centralit.citcorpore.bean.NivelAutoridadeDTO;
import br.com.centralit.citcorpore.integracao.GrupoNivelAutoridadeDao;
import br.com.centralit.citcorpore.integracao.NivelAutoridadeDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;

public class NivelAutoridadeServiceEjb extends CrudServiceImpl implements NivelAutoridadeService {

    private NivelAutoridadeDao dao;

    @Override
    protected NivelAutoridadeDao getDao() {
        if (dao == null) {
            dao = new NivelAutoridadeDao();
        }
        return dao;
    }

    @Override
    protected void validaCreate(final Object arg0) throws Exception {
        this.validaHierarquia((NivelAutoridadeDTO) arg0);
    }

    @Override
    protected void validaUpdate(final Object arg0) throws Exception {
        this.validaHierarquia((NivelAutoridadeDTO) arg0);
    }

    private void validaHierarquia(final NivelAutoridadeDTO arg0) throws Exception {
        if (arg0.getHierarquia() == null) {
            throw new LogicException("Hierarquia não informdada");
        }
        final Collection<NivelAutoridadeDTO> col = this.getDao().findByHierarquiaAndNotIdNivelAutoridade(arg0.getHierarquia(), arg0.getIdNivelAutoridade());
        if (col != null && !col.isEmpty()) {
            throw new LogicException("Hierarquia já existente");
        }
    }

    @Override
    public IDto create(final IDto model) throws ServiceException, LogicException {
        final NivelAutoridadeDao nivelAutoridadeDao = new NivelAutoridadeDao();
        final GrupoNivelAutoridadeDao grupoNivelAutoridadeDao = new GrupoNivelAutoridadeDao();
        final TransactionControler tc = new TransactionControlerImpl(nivelAutoridadeDao.getAliasDB());

        try {
            this.validaCreate(model);

            nivelAutoridadeDao.setTransactionControler(tc);
            grupoNivelAutoridadeDao.setTransactionControler(tc);

            tc.start();

            NivelAutoridadeDTO nivelAutoridadeDto = (NivelAutoridadeDTO) model;
            nivelAutoridadeDto = (NivelAutoridadeDTO) nivelAutoridadeDao.create(nivelAutoridadeDto);

            this.atualizaGrupos(nivelAutoridadeDto, grupoNivelAutoridadeDao);

            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
        return model;
    }

    private void atualizaGrupos(final NivelAutoridadeDTO nivelAutoridadeDto, final GrupoNivelAutoridadeDao grupoNivelAutoridadeDao) throws Exception {
        grupoNivelAutoridadeDao.deleteByIdNivelAutoridade(nivelAutoridadeDto.getIdNivelAutoridade());
        if (nivelAutoridadeDto.getColGrupos() != null) {
            for (final GrupoNivelAutoridadeDTO grupoDto : nivelAutoridadeDto.getColGrupos()) {
                if (grupoDto.getIdGrupo() == null) {
                    throw new Exception("Grupo não informado");
                }
                grupoDto.setIdNivelAutoridade(nivelAutoridadeDto.getIdNivelAutoridade());
                grupoNivelAutoridadeDao.create(grupoDto);
            }
        }
    }

    @Override
    public void update(final IDto model) throws ServiceException, LogicException {
        final NivelAutoridadeDao nivelAutoridadeDao = new NivelAutoridadeDao();
        final GrupoNivelAutoridadeDao grupoNivelAutoridadeDao = new GrupoNivelAutoridadeDao();
        final TransactionControler tc = new TransactionControlerImpl(nivelAutoridadeDao.getAliasDB());

        try {
            this.validaUpdate(model);

            nivelAutoridadeDao.setTransactionControler(tc);
            grupoNivelAutoridadeDao.setTransactionControler(tc);

            tc.start();

            final NivelAutoridadeDTO nivelAutoridadeDto = (NivelAutoridadeDTO) model;
            nivelAutoridadeDao.update(nivelAutoridadeDto);

            this.atualizaGrupos(nivelAutoridadeDto, grupoNivelAutoridadeDao);

            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
    }

}
