package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.LimiteAprovacaoAutoridadeDTO;
import br.com.centralit.citcorpore.bean.LimiteAprovacaoDTO;
import br.com.centralit.citcorpore.bean.LimiteAprovacaoProcessoDTO;
import br.com.centralit.citcorpore.bean.ValorLimiteAprovacaoDTO;
import br.com.centralit.citcorpore.integracao.LimiteAprovacaoAutoridadeDao;
import br.com.centralit.citcorpore.integracao.LimiteAprovacaoDao;
import br.com.centralit.citcorpore.integracao.LimiteAprovacaoProcessoDao;
import br.com.centralit.citcorpore.integracao.ValorLimiteAprovacaoDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;

public class LimiteAprovacaoServiceEjb extends CrudServiceImpl implements LimiteAprovacaoService {

    private LimiteAprovacaoDao dao;

    @Override
    protected LimiteAprovacaoDao getDao() {
        if (dao == null) {
            dao = new LimiteAprovacaoDao();
        }
        return new LimiteAprovacaoDao();
    }

    @Override
    protected void validaCreate(final Object arg0) throws Exception {
        this.validaUpdate(arg0);
    }

    @Override
    protected void validaUpdate(final Object arg0) throws Exception {
        final LimiteAprovacaoDTO limiteAprovacaoDto = (LimiteAprovacaoDTO) arg0;
        if (limiteAprovacaoDto.getIdProcessoNegocio() == null || limiteAprovacaoDto.getIdProcessoNegocio().length == 0) {
            throw new LogicException("Não foi selecionado nenhum processo de negócio");
        }
        if (limiteAprovacaoDto.getIdNivelAutoridade() == null || limiteAprovacaoDto.getIdNivelAutoridade().length == 0) {
            throw new LogicException("Não foi selecionado nenhum nível de autoridade");
        }
    }

    @Override
    public IDto create(final IDto model) throws ServiceException, LogicException {
        final LimiteAprovacaoDao limiteAprovacaoDao = new LimiteAprovacaoDao();
        final LimiteAprovacaoProcessoDao limiteAprovacaoProcessoDao = new LimiteAprovacaoProcessoDao();
        final LimiteAprovacaoAutoridadeDao limiteAprovacaoAutoridadeDao = new LimiteAprovacaoAutoridadeDao();
        final ValorLimiteAprovacaoDao valorLimiteAprovacaoDao = new ValorLimiteAprovacaoDao();
        final TransactionControler tc = new TransactionControlerImpl(limiteAprovacaoDao.getAliasDB());

        try {
            this.validaCreate(model);

            limiteAprovacaoDao.setTransactionControler(tc);
            valorLimiteAprovacaoDao.setTransactionControler(tc);
            limiteAprovacaoProcessoDao.setTransactionControler(tc);
            limiteAprovacaoAutoridadeDao.setTransactionControler(tc);

            tc.start();

            LimiteAprovacaoDTO limiteAprovacaoDto = (LimiteAprovacaoDTO) model;
            limiteAprovacaoDto = (LimiteAprovacaoDTO) limiteAprovacaoDao.create(limiteAprovacaoDto);

            this.atualizaValores(limiteAprovacaoDto, valorLimiteAprovacaoDao);
            this.atualizaProcessos(limiteAprovacaoDto, limiteAprovacaoProcessoDao);
            this.atualizaAutoridades(limiteAprovacaoDto, limiteAprovacaoAutoridadeDao);

            this.validaLimiteAprovacao(limiteAprovacaoDto, limiteAprovacaoProcessoDao);

            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
        return model;
    }

    private void atualizaProcessos(final LimiteAprovacaoDTO limiteAprovacaoDto, final LimiteAprovacaoProcessoDao limiteAprovacaoProcessoDao) throws Exception {
        limiteAprovacaoProcessoDao.deleteByIdLimiteAprovacao(limiteAprovacaoDto.getIdLimiteAprovacao());
        for (int i = 0; i < limiteAprovacaoDto.getIdProcessoNegocio().length; i++) {
            final LimiteAprovacaoProcessoDTO limiteDto = new LimiteAprovacaoProcessoDTO();
            limiteDto.setIdLimiteAprovacao(limiteAprovacaoDto.getIdLimiteAprovacao());
            limiteDto.setIdProcessoNegocio(limiteAprovacaoDto.getIdProcessoNegocio()[i]);
            limiteAprovacaoProcessoDao.create(limiteDto);
        }
    }

    private void atualizaAutoridades(final LimiteAprovacaoDTO limiteAprovacaoDto, final LimiteAprovacaoAutoridadeDao limiteAprovacaoAutoridadeDao) throws Exception {
        limiteAprovacaoAutoridadeDao.deleteByIdLimiteAprovacao(limiteAprovacaoDto.getIdLimiteAprovacao());
        for (int i = 0; i < limiteAprovacaoDto.getIdNivelAutoridade().length; i++) {
            final LimiteAprovacaoAutoridadeDTO limiteDto = new LimiteAprovacaoAutoridadeDTO();
            limiteDto.setIdLimiteAprovacao(limiteAprovacaoDto.getIdLimiteAprovacao());
            limiteDto.setIdNivelAutoridade(limiteAprovacaoDto.getIdNivelAutoridade()[i]);
            limiteAprovacaoAutoridadeDao.create(limiteDto);
        }
    }

    private void atualizaValores(final LimiteAprovacaoDTO limiteAprovacaoDto, final ValorLimiteAprovacaoDao valorLimiteAprovacaoDao) throws Exception {
        valorLimiteAprovacaoDao.deleteByIdLimiteAprovacao(limiteAprovacaoDto.getIdLimiteAprovacao());
        if (limiteAprovacaoDto.getColValores() != null) {
            for (final ValorLimiteAprovacaoDTO valorDto : limiteAprovacaoDto.getColValores()) {
                valorDto.setIdLimiteAprovacao(limiteAprovacaoDto.getIdLimiteAprovacao());
                valorLimiteAprovacaoDao.create(valorDto);
            }
        }
    }

    @Override
    public void update(final IDto model) throws ServiceException, LogicException {
        final LimiteAprovacaoDao limiteAprovacaoDao = new LimiteAprovacaoDao();
        final LimiteAprovacaoProcessoDao limiteAprovacaoProcessoDao = new LimiteAprovacaoProcessoDao();
        final LimiteAprovacaoAutoridadeDao limiteAprovacaoAutoridadeDao = new LimiteAprovacaoAutoridadeDao();
        final ValorLimiteAprovacaoDao valorLimiteAprovacaoDao = new ValorLimiteAprovacaoDao();
        final TransactionControler tc = new TransactionControlerImpl(limiteAprovacaoDao.getAliasDB());

        try {
            this.validaUpdate(model);

            limiteAprovacaoDao.setTransactionControler(tc);
            valorLimiteAprovacaoDao.setTransactionControler(tc);
            limiteAprovacaoProcessoDao.setTransactionControler(tc);
            limiteAprovacaoAutoridadeDao.setTransactionControler(tc);

            tc.start();

            final LimiteAprovacaoDTO limiteAprovacaoDto = (LimiteAprovacaoDTO) model;
            limiteAprovacaoDao.update(limiteAprovacaoDto);

            this.atualizaValores(limiteAprovacaoDto, valorLimiteAprovacaoDao);
            this.atualizaProcessos(limiteAprovacaoDto, limiteAprovacaoProcessoDao);
            this.atualizaAutoridades(limiteAprovacaoDto, limiteAprovacaoAutoridadeDao);

            this.validaLimiteAprovacao(limiteAprovacaoDto, limiteAprovacaoProcessoDao);

            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
    }

    private void validaLimiteAprovacao(final LimiteAprovacaoDTO limiteAprovacaoDto, final LimiteAprovacaoProcessoDao limiteAprovacaoProcessoDao) throws Exception {
        final Collection<LimiteAprovacaoProcessoDTO> colLimiteAprovacaoProcesso = limiteAprovacaoProcessoDao.findByIdLimiteAprovacao(limiteAprovacaoDto.getIdLimiteAprovacao());
        for (final LimiteAprovacaoProcessoDTO limiteAprovacaoProcessoDto : colLimiteAprovacaoProcesso) {
            final Collection<LimiteAprovacaoProcessoDTO> colDuplicados = limiteAprovacaoProcessoDao.findDuplicados(limiteAprovacaoDto.getIdLimiteAprovacao(),
                    limiteAprovacaoProcessoDto.getIdProcessoNegocio());
            if (colDuplicados != null && colDuplicados.size() > 0) {
                throw new LogicException("Existe mais de um limite de aprovação vinculado ao mesmo processo e autoridade");
            }
        }
    }

}
