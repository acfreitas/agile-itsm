package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.bpm.dto.TipoFluxoDTO;
import br.com.centralit.bpm.integracao.TipoFluxoDao;
import br.com.centralit.citcorpore.bean.ProcessoNegocioDTO;
import br.com.centralit.citcorpore.bean.ProcessoNivelAutoridadeDTO;
import br.com.centralit.citcorpore.integracao.ProcessoNegocioDao;
import br.com.centralit.citcorpore.integracao.ProcessoNivelAutoridadeDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;

public class ProcessoNegocioServiceEjb extends CrudServiceImpl implements ProcessoNegocioService {

    private ProcessoNegocioDao dao;

    @Override
    protected ProcessoNegocioDao getDao() {
        if (dao == null) {
            dao = new ProcessoNegocioDao();
        }
        return dao;
    }

    @Override
    public IDto create(final IDto model) throws ServiceException, LogicException {
        final ProcessoNegocioDao processoNegocioDao = new ProcessoNegocioDao();
        final ProcessoNivelAutoridadeDao processoNivelAutoridadeDao = new ProcessoNivelAutoridadeDao();
        final TransactionControler tc = new TransactionControlerImpl(processoNegocioDao.getAliasDB());

        try {
            this.validaCreate(model);

            processoNegocioDao.setTransactionControler(tc);
            processoNivelAutoridadeDao.setTransactionControler(tc);

            tc.start();

            ProcessoNegocioDTO processoNegocioDto = (ProcessoNegocioDTO) model;

            if (processoNegocioDto.getPercDispensaNovaAprovacao() == null) {
                processoNegocioDto.setPercDispensaNovaAprovacao(new Double(0));
            }

            processoNegocioDto = (ProcessoNegocioDTO) processoNegocioDao.create(processoNegocioDto);

            this.atualizaAutoridades(processoNegocioDto, processoNivelAutoridadeDao);
            this.atualizaFluxos(processoNegocioDto, tc);

            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
        return model;
    }

    private void atualizaAutoridades(final ProcessoNegocioDTO processoNegocioDto, final ProcessoNivelAutoridadeDao processoNivelAutoridadeDao) throws Exception {
        processoNivelAutoridadeDao.deleteByIdProcessoNegocio(processoNegocioDto.getIdProcessoNegocio());
        if (processoNegocioDto.getColAutoridades() != null) {
            for (final ProcessoNivelAutoridadeDTO autoridadeDto : processoNegocioDto.getColAutoridades()) {
                if (autoridadeDto.getIdNivelAutoridade() == null) {
                    throw new Exception("Nível de autoridade não informada");
                }
                if (autoridadeDto.getAntecedenciaMinimaAprovacao() == null) {
                    autoridadeDto.setAntecedenciaMinimaAprovacao(new Integer(0));
                }
                if (autoridadeDto.getPermiteSolicitacao() == null || autoridadeDto.getPermiteSolicitacao().trim().equals("")) {
                    autoridadeDto.setPermiteSolicitacao("S");
                }
                autoridadeDto.setIdProcessoNegocio(processoNegocioDto.getIdProcessoNegocio());
                processoNivelAutoridadeDao.create(autoridadeDto);
            }
        }
    }

    @Override
    public void update(final IDto model) throws ServiceException, LogicException {
        final ProcessoNegocioDao processoNegocioDao = new ProcessoNegocioDao();
        final ProcessoNivelAutoridadeDao processoNivelAutoridadeDao = new ProcessoNivelAutoridadeDao();
        final TransactionControler tc = new TransactionControlerImpl(processoNegocioDao.getAliasDB());

        try {
            this.validaUpdate(model);

            processoNegocioDao.setTransactionControler(tc);
            processoNivelAutoridadeDao.setTransactionControler(tc);

            tc.start();

            final ProcessoNegocioDTO processoNegocioDto = (ProcessoNegocioDTO) model;
            processoNegocioDao.update(processoNegocioDto);

            this.atualizaAutoridades(processoNegocioDto, processoNivelAutoridadeDao);
            this.atualizaFluxos(processoNegocioDto, tc);

            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
    }

    private void atualizaFluxos(final ProcessoNegocioDTO processoNegocioDto, final TransactionControler tc) throws Exception {
        final TipoFluxoDao tipoFluxoDao = new TipoFluxoDao();
        tipoFluxoDao.setTransactionControler(tc);
        final Collection<TipoFluxoDTO> colTipoFluxo = tipoFluxoDao.findByIdProcessoNegocio(processoNegocioDto.getIdProcessoNegocio());
        if (colTipoFluxo != null) {
            for (final TipoFluxoDTO tipoFluxoDto : colTipoFluxo) {
                tipoFluxoDto.setIdProcessoNegocio(null);
                tipoFluxoDao.update(tipoFluxoDto);
            }
        }
        if (processoNegocioDto.getIdTipoFluxo() != null && processoNegocioDto.getIdTipoFluxo().length > 0) {
            final TipoFluxoDTO tipoFluxoDto = new TipoFluxoDTO();
            for (int i = 0; i < processoNegocioDto.getIdTipoFluxo().length; i++) {
                tipoFluxoDto.setIdTipoFluxo(processoNegocioDto.getIdTipoFluxo()[i]);
                tipoFluxoDto.setIdProcessoNegocio(processoNegocioDto.getIdProcessoNegocio());
                tipoFluxoDao.updateNotNull(tipoFluxoDto);
            }
        }
    }

}
