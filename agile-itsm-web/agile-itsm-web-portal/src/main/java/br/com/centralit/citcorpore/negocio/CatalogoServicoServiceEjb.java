package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.CatalogoServicoDTO;
import br.com.centralit.citcorpore.bean.InfoCatalogoServicoDTO;
import br.com.centralit.citcorpore.bean.ServContratoCatalogoServDTO;
import br.com.centralit.citcorpore.integracao.CatalogoServicoDao;
import br.com.centralit.citcorpore.integracao.InfoCatalogoServicoDao;
import br.com.centralit.citcorpore.integracao.ServContratoCatalogoServDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;

public class CatalogoServicoServiceEjb extends CrudServiceImpl implements CatalogoServicoService {

    private CatalogoServicoDao dao;

    @Override
    protected CatalogoServicoDao getDao() {
        if (dao == null) {
            dao = new CatalogoServicoDao();
        }
        return dao;
    }

    @Override
    public IDto create(final IDto model) throws ServiceException, LogicException {
        CatalogoServicoDTO catalogoServicoDto = (CatalogoServicoDTO) model;
        final CatalogoServicoDao catalogoServicodao = this.getDao();

        final ServContratoCatalogoServDao servContratoCatalogoServDao = new ServContratoCatalogoServDao();
        final InfoCatalogoServicoDao infoCatalogoServicoDao = new InfoCatalogoServicoDao();

        final TransactionControler tc = new TransactionControlerImpl(catalogoServicodao.getAliasDB());

        try {
            this.validaCreate(model);
            catalogoServicodao.setTransactionControler(tc);
            infoCatalogoServicoDao.setTransactionControler(tc);
            servContratoCatalogoServDao.setTransactionControler(tc);

            tc.start();

            catalogoServicoDto = (CatalogoServicoDTO) catalogoServicodao.create(model);

            if (catalogoServicoDto.getColServicoContrato() != null && !catalogoServicoDto.getColServicoContrato().isEmpty()) {
                ServContratoCatalogoServDTO item = null;
                // varre a lista de serviços
                for (int i = 0; i < catalogoServicoDto.getColServicoContrato().size(); i++) {
                    item = catalogoServicoDto.getColServicoContrato().get(i);
                    item.setIdCatalogoServico(catalogoServicoDto.getIdCatalogoServico());

                    // grava cada item da lista
                    servContratoCatalogoServDao.create(item);
                }
            }

            if (catalogoServicoDto.getColInfoCatalogoServico() != null && !catalogoServicoDto.getColInfoCatalogoServico().isEmpty()) {
                InfoCatalogoServicoDTO item = null;
                // varre a lista de serviços
                for (int i = 0; i < catalogoServicoDto.getColInfoCatalogoServico().size(); i++) {
                    item = catalogoServicoDto.getColInfoCatalogoServico().get(i);
                    if (catalogoServicoDto.getIdCatalogoServico() != null) {
                        item.setIdCatalogoServico(catalogoServicoDto.getIdCatalogoServico());
                    }
                    if (catalogoServicoDto.getNomeInfoCatalogoServico() != null) {
                        item.setNomeInfoCatalogoServico(catalogoServicoDto.getNomeInfoCatalogoServico());
                    }
                    if (catalogoServicoDto.getDescInfoCatalogoServico() != null) {
                        item.setDescInfoCatalogoServico(catalogoServicoDto.getDescInfoCatalogoServico());
                    }
                    // grava cada item da lista
                    if (item.getIdInfoCatalogoServico() == null) {
                        infoCatalogoServicoDao.create(item);
                    } else {
                        infoCatalogoServicoDao.update(item);
                    }
                }
            }

            tc.commit();
        } catch (final Exception e) {
            e.printStackTrace();
            this.rollbackTransaction(tc, e);
        } finally {
            try {
                tc.close();
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }

        return catalogoServicoDto;
    }

    @Override
    public void update(final IDto model) throws ServiceException, LogicException {
        final CatalogoServicoDTO catalogoServicoDto = (CatalogoServicoDTO) model;
        final CatalogoServicoDao catalogoServicodao = this.getDao();

        final ServContratoCatalogoServDao servContratoCatalogoServDao = new ServContratoCatalogoServDao();
        final InfoCatalogoServicoDao infoCatalogoServicoDao = new InfoCatalogoServicoDao();

        final TransactionControler tc = new TransactionControlerImpl(catalogoServicodao.getAliasDB());

        try {
            this.validaCreate(model);
            catalogoServicodao.setTransactionControler(tc);
            infoCatalogoServicoDao.setTransactionControler(tc);
            servContratoCatalogoServDao.setTransactionControler(tc);

            tc.start();

            catalogoServicodao.update(catalogoServicoDto);
            servContratoCatalogoServDao.deleteByIdServContratoCatalogo(catalogoServicoDto);
            if (catalogoServicoDto.getColServicoContrato() != null && !catalogoServicoDto.getColServicoContrato().isEmpty()) {
                ServContratoCatalogoServDTO item = null;
                // varre a lista de serviços
                for (int i = 0; i < catalogoServicoDto.getColServicoContrato().size(); i++) {
                    item = catalogoServicoDto.getColServicoContrato().get(i);
                    item.setIdCatalogoServico(catalogoServicoDto.getIdCatalogoServico());
                    // grava cada item da lista
                    servContratoCatalogoServDao.create(item);
                }
            }

            infoCatalogoServicoDao.deleteByIdInfoCatalogo(catalogoServicoDto);
            if (catalogoServicoDto.getColInfoCatalogoServico() != null && !catalogoServicoDto.getColInfoCatalogoServico().isEmpty()) {

                InfoCatalogoServicoDTO item = null;
                // varre a lista de serviços
                for (int i = 0; i < catalogoServicoDto.getColInfoCatalogoServico().size(); i++) {
                    item = catalogoServicoDto.getColInfoCatalogoServico().get(i);
                    item.setIdCatalogoServico(catalogoServicoDto.getIdCatalogoServico());
                    // grava cada item da lista
                    infoCatalogoServicoDao.create(item);
                }
            }

            tc.commit();

        } catch (final Exception e) {
            e.printStackTrace();
            this.rollbackTransaction(tc, e);
        } finally {
            try {
                tc.close();
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public IDto restore(final IDto model) throws ServiceException, LogicException {
        CatalogoServicoDTO catalogoServicoDto = null;
        final CatalogoServicoDao dao = this.getDao();
        try {
            catalogoServicoDto = (CatalogoServicoDTO) dao.restore(model);
            final ServContratoCatalogoServDTO servContratoCatalogoServDTO = new ServContratoCatalogoServDTO();
            // SERVCONTRATOCATALOGOSERVICO
            servContratoCatalogoServDTO.setIdCatalogoServico(catalogoServicoDto.getIdCatalogoServico());
            catalogoServicoDto.setColServicoContrato(new ArrayList(this.getServContratoCatalogoServDao().findByIdCatalogo(servContratoCatalogoServDTO)));

            final InfoCatalogoServicoDTO infoCatalogoServicoDTO = new InfoCatalogoServicoDTO();
            infoCatalogoServicoDTO.setIdCatalogoServico(catalogoServicoDto.getIdCatalogoServico());
            catalogoServicoDto.setColInfoCatalogoServico(new ArrayList(this.getInfoCatalogoServicoDao().findByIdInfoCatalogo(infoCatalogoServicoDTO)));
        } catch (final Exception e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
        return catalogoServicoDto;
    }

    private InfoCatalogoServicoDao infoCatalogoServicoDao;
    private ServContratoCatalogoServDao servContratoCatalogoServDao;

    private ServContratoCatalogoServDao getServContratoCatalogoServDao() {
        if (servContratoCatalogoServDao == null) {
            servContratoCatalogoServDao = new ServContratoCatalogoServDao();
        }
        return servContratoCatalogoServDao;
    }

    private InfoCatalogoServicoDao getInfoCatalogoServicoDao() {
        if (infoCatalogoServicoDao == null) {
            infoCatalogoServicoDao = new InfoCatalogoServicoDao();
        }
        return infoCatalogoServicoDao;
    }

    @Override
    public Collection<CatalogoServicoDTO> listAllCatalogos() throws ServiceException, Exception {
        return this.getDao().listAllCatalogos();
    }

    @Override
    public boolean verificaSeCatalogoExiste(final CatalogoServicoDTO catalogoServicoDTO) throws PersistenceException, ServiceException {
        return this.getDao().verificaSeCatalogoExiste(catalogoServicoDTO);
    }

    @Override
    public Collection<CatalogoServicoDTO> listByIdContrato(final Integer idContrato) throws ServiceException, Exception {
        return this.getDao().listByIdContrato(idContrato);
    }

}
