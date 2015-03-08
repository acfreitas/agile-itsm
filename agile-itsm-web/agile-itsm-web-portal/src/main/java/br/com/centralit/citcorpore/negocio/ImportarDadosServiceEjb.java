package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ImportarDadosDTO;
import br.com.centralit.citcorpore.integracao.ImportarDadosDao;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.negocio.ControleGEDServiceBean;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;

public class ImportarDadosServiceEjb extends CrudServiceImpl implements ImportarDadosService {

    private ImportarDadosDao dao;

    @Override
    protected ImportarDadosDao getDao() {
        if (dao == null) {
            dao = new ImportarDadosDao();
        }
        return dao;
    }

    @Override
    public IDto create(final IDto model) throws ServiceException, LogicException {
        final ImportarDadosDao importarDadosDao = new ImportarDadosDao();
        final TransactionControler tc = new TransactionControlerImpl(importarDadosDao.getAliasDB());

        try {
            this.validaCreate(model);

            importarDadosDao.setTransactionControler(tc);

            tc.start();

            ImportarDadosDTO importarDadosDto = (ImportarDadosDTO) model;
            importarDadosDto = (ImportarDadosDTO) importarDadosDao.create(importarDadosDto);

            this.atualizaAnexos(importarDadosDto, tc);

            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
        return model;
    }

    @Override
    public void update(final IDto model) throws ServiceException, LogicException {
        final ImportarDadosDao importarDadosDao = new ImportarDadosDao();
        final TransactionControler tc = new TransactionControlerImpl(importarDadosDao.getAliasDB());

        try {
            this.validaUpdate(model);

            importarDadosDao.setTransactionControler(tc);

            tc.start();

            final ImportarDadosDTO importarDadosDto = (ImportarDadosDTO) model;
            importarDadosDao.update(importarDadosDto);

            this.atualizaAnexos(importarDadosDto, tc);

            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
    }

    private void atualizaAnexos(final ImportarDadosDTO importarDadosDto, final TransactionControler tc) throws Exception {
        new ControleGEDServiceBean().atualizaAnexos(importarDadosDto.getAnexos(), ControleGEDDTO.TABELA_IMPORTARDADOS, importarDadosDto.getIdImportarDados(), tc);
    }

    /**
     * Retorna lista com os registros da tabela ImportarDados relacionados ao idExternalConnection
     */
    @Override
    public Collection<ImportarDadosDTO> consultarImportarDadosRelacionados(final Integer idExternalConnection) throws Exception {
        return this.getDao().consultarImportarDadosPeloExternalConnection(idExternalConnection);
    }

}
