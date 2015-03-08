package br.com.centralit.citcorpore.metainfo.negocio;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import br.com.centralit.citcorpore.metainfo.bean.CamposObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.ObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.integracao.CamposObjetoNegocioDao;
import br.com.centralit.citcorpore.metainfo.integracao.ObjetoNegocioDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;

public class ObjetoNegocioServiceEjb extends CrudServiceImpl implements ObjetoNegocioService {

    private ObjetoNegocioDao objetoNegocioDao;

    @Override
    protected ObjetoNegocioDao getDao() {
        if (objetoNegocioDao == null) {
            objetoNegocioDao = new ObjetoNegocioDao();
        }
        return objetoNegocioDao;
    }

    @Override
    public Collection listAtivos() throws Exception {
        return this.getDao().listAtivos();
    }

    @Override
    public Collection findByNomeTabelaDB(final String nomeTabelaDBParm) throws Exception {
        return this.getDao().findByNomeTabelaDB(nomeTabelaDBParm);
    }

    @Override
    public IDto create(IDto model) throws ServiceException, LogicException {
        // Instancia Objeto controlador de transacao
        final CrudDAO crudDao = this.getDao();
        final CamposObjetoNegocioDao camposObjetoNegocioDao = new CamposObjetoNegocioDao();
        final TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
        try {
            // Faz validacao, caso exista.
            this.validaCreate(model);

            // Instancia ou obtem os DAOs necessarios.

            // Seta o TransactionController para os DAOs
            crudDao.setTransactionControler(tc);
            camposObjetoNegocioDao.setTransactionControler(tc);

            // Inicia transacao
            tc.start();

            // Executa operacoes pertinentes ao negocio.
            model = crudDao.create(model);
            final ObjetoNegocioDTO objNegocioDto = (ObjetoNegocioDTO) model;
            if (objNegocioDto.getColCampos() != null) {
                for (final Iterator it = objNegocioDto.getColCampos().iterator(); it.hasNext();) {
                    final CamposObjetoNegocioDTO camposObjetoNegocioDTO = (CamposObjetoNegocioDTO) it.next();
                    camposObjetoNegocioDTO.setIdObjetoNegocio(objNegocioDto.getIdObjetoNegocio());
                    camposObjetoNegocioDao.create(camposObjetoNegocioDTO);
                }
            }

            // Faz commit e fecha a transacao.
            tc.commit();
            tc.close();

            return model;
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
        return model;
    }

    @Override
    public void update(final IDto model) throws ServiceException, LogicException {
        // Instancia Objeto controlador de transacao
        final CrudDAO crudDao = this.getDao();
        final CamposObjetoNegocioDao camposObjetoNegocioDao = new CamposObjetoNegocioDao();
        final TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
        try {
            // Faz validacao, caso exista.
            this.validaCreate(model);

            // Instancia ou obtem os DAOs necessarios.

            // Seta o TransactionController para os DAOs
            crudDao.setTransactionControler(tc);
            camposObjetoNegocioDao.setTransactionControler(tc);

            // Inicia transacao
            tc.start();

            // Executa operacoes pertinentes ao negocio.
            crudDao.update(model);
            String strCamposDB = "";
            final ObjetoNegocioDTO objNegocioDto = (ObjetoNegocioDTO) model;
            if (objNegocioDto.getColCampos() != null) {
                for (final Iterator it = objNegocioDto.getColCampos().iterator(); it.hasNext();) {
                    final CamposObjetoNegocioDTO camposObjetoNegocioDTO = (CamposObjetoNegocioDTO) it.next();
                    camposObjetoNegocioDTO.setIdObjetoNegocio(objNegocioDto.getIdObjetoNegocio());

                    final Collection colCampos = camposObjetoNegocioDao.findByIdObjetoNegocioAndNomeDB(camposObjetoNegocioDTO.getIdObjetoNegocio(),
                            camposObjetoNegocioDTO.getNomeDB());
                    if (colCampos == null || colCampos.size() == 0) {
                        camposObjetoNegocioDao.create(camposObjetoNegocioDTO);
                    } else {
                        final CamposObjetoNegocioDTO camposObjetoNegocioAux = (CamposObjetoNegocioDTO) ((List) colCampos).get(0);
                        camposObjetoNegocioDTO.setIdCamposObjetoNegocio(camposObjetoNegocioAux.getIdCamposObjetoNegocio());
                        camposObjetoNegocioDao.update(camposObjetoNegocioDTO);
                    }
                    if (strCamposDB != null && !strCamposDB.trim().equalsIgnoreCase("")) {
                        strCamposDB = strCamposDB + ",";
                    }
                    strCamposDB = strCamposDB + "'" + camposObjetoNegocioDTO.getNomeDB() + "'";
                }
            }
            // Faz commit e fecha a transacao.
            tc.commit();
            tc.close();
            try {
                camposObjetoNegocioDao.deleteFromNOTRelNomeDB(strCamposDB, objNegocioDto.getIdObjetoNegocio());
            } catch (final Exception e) {
                e.printStackTrace();
            }
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
    }

    @Override
    public ObjetoNegocioDTO findByNomeObjetoNegocio(final String nomeObjetoNegocio) throws Exception {
        return this.getDao().findByNomeObjetoNegocio(nomeObjetoNegocio);
    }

    @Override
    public ObjetoNegocioDTO getByNomeTabelaDB(final String nomeObjetoNegocio) throws Exception {
        return this.getDao().getByNomeTabelaDB(nomeObjetoNegocio);
    }

    @Override
    public void updateDisable(final ObjetoNegocioDTO objetoNegocioDTO) throws Exception {
        super.update(objetoNegocioDTO);
    }

}
