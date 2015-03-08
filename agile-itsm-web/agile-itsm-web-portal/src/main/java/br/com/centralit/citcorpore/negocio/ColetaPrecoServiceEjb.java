package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.AvaliacaoColetaPrecoDTO;
import br.com.centralit.citcorpore.bean.ColetaPrecoDTO;
import br.com.centralit.citcorpore.bean.ItemCotacaoDTO;
import br.com.centralit.citcorpore.integracao.AvaliacaoColetaPrecoDao;
import br.com.centralit.citcorpore.integracao.ColetaPrecoDao;
import br.com.centralit.citcorpore.integracao.ItemCotacaoDao;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.negocio.ControleGEDServiceBean;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;

public class ColetaPrecoServiceEjb extends CrudServiceImpl implements ColetaPrecoService {

    private ColetaPrecoDao dao;

    @Override
    protected ColetaPrecoDao getDao() {
        if (dao == null) {
            dao = new ColetaPrecoDao();
        }
        return dao;
    }

    @Override
    protected void validaCreate(final Object arg0) throws Exception {
        this.validaAtualizacao((ColetaPrecoDTO) arg0);
    }

    @Override
    protected void validaUpdate(final Object arg0) throws Exception {
        this.validaAtualizacao((ColetaPrecoDTO) arg0);
    }

    private void validaAtualizacao(final ColetaPrecoDTO coletaPrecoDto) throws Exception {
        if (coletaPrecoDto.getDataValidade() != null && coletaPrecoDto.getDataValidade().compareTo(coletaPrecoDto.getDataColeta()) < 0) {
            throw new LogicException("Data da validade não pode ser menor que a data da coleta");
        }
        if (coletaPrecoDto.getIdColetaPreco() == null) {
            final Collection<ColetaPrecoDTO> colColetas = this.findByIdItemCotacaoAndIdFornecedor(coletaPrecoDto.getIdFornecedor(), coletaPrecoDto.getIdItemCotacao());
            if (colColetas != null && !colColetas.isEmpty()) {
                throw new LogicException("Já existe uma coleta de preço para este fornecedor e este item");
            }
        }
        ItemCotacaoDTO itemCotacaoDto = new ItemCotacaoDTO();
        itemCotacaoDto.setIdItemCotacao(coletaPrecoDto.getIdItemCotacao());
        itemCotacaoDto = (ItemCotacaoDTO) new ItemCotacaoDao().restore(itemCotacaoDto);
        if (itemCotacaoDto.getExigeFornecedorQualificado() == null || !itemCotacaoDto.getExigeFornecedorQualificado().equalsIgnoreCase("S")) {
            return;
        }
        if (!new AvaliacaoFornecedorServiceEjb().fornecedorQualificado(coletaPrecoDto.getIdFornecedor())) {
            throw new LogicException("Este item de cotação exige que o fornecedor seja qualificado");
        }
    }

    @Override
    public Collection findHabilitadasByIdCotacao(final Integer parm) throws Exception {
        try {
            return this.getDao().findHabilitadasByIdCotacao(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdItemCotacao(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdItemCotacao(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdItemCotacao(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdItemCotacao(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdPedido(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdPedido(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdFornecedor(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdFornecedor(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdFornecedor(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdFornecedor(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    private void atualizaCriterios(final ColetaPrecoDTO coletaPrecoDto, final AvaliacaoColetaPrecoDao avaliacaoColetaPrecoDao) throws Exception {
        if (coletaPrecoDto.getIdCriterioColeta() != null) {
            final Integer[] idCriterioColeta = coletaPrecoDto.getIdCriterioColeta();
            final Integer[] pesoCriterioColeta = coletaPrecoDto.getPesoCriterioColeta();
            for (int i = 0; i < idCriterioColeta.length; i++) {
                if (idCriterioColeta[i] != null) {
                    if (pesoCriterioColeta[i] == null) {
                        throw new Exception("Avaliação não informada");
                    }
                    if (pesoCriterioColeta[i].intValue() > 10) {
                        throw new Exception("A avaliação deve estar entre 0 e 10");
                    }

                    final AvaliacaoColetaPrecoDTO avaliacaoCotacaoDto = new AvaliacaoColetaPrecoDTO();
                    avaliacaoCotacaoDto.setIdColetaPreco(coletaPrecoDto.getIdColetaPreco());
                    avaliacaoCotacaoDto.setIdCriterio(idCriterioColeta[i]);
                    avaliacaoCotacaoDto.setAvaliacao(pesoCriterioColeta[i]);
                    avaliacaoColetaPrecoDao.create(avaliacaoCotacaoDto);
                }
            }
        }
    }

    private void atualizaAnexos(final ColetaPrecoDTO coletaPrecoDto, final TransactionControler tc) throws Exception {
        new ControleGEDServiceBean().atualizaAnexos(coletaPrecoDto.getAnexos(), ControleGEDDTO.TABELA_COLETAPRECOS, coletaPrecoDto.getIdColetaPreco(), tc);
    }

    @Override
    public IDto create(final IDto model) throws ServiceException, LogicException {
        final AvaliacaoColetaPrecoDao avaliacaoColetaPrecoDao = new AvaliacaoColetaPrecoDao();
        final TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());

        try {
            this.validaCreate(model);

            this.getDao().setTransactionControler(tc);
            avaliacaoColetaPrecoDao.setTransactionControler(tc);

            tc.start();

            ColetaPrecoDTO coletaPrecoDto = (ColetaPrecoDTO) model;
            coletaPrecoDto = (ColetaPrecoDTO) this.getDao().create(coletaPrecoDto);

            this.atualizaCriterios(coletaPrecoDto, avaliacaoColetaPrecoDao);
            this.atualizaAnexos(coletaPrecoDto, tc);

            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
        return model;
    }

    @Override
    public void update(final IDto model) throws ServiceException, LogicException {
        final AvaliacaoColetaPrecoDao avaliacaoColetaPrecoDao = new AvaliacaoColetaPrecoDao();
        final TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());

        try {
            this.validaUpdate(model);

            this.getDao().setTransactionControler(tc);
            avaliacaoColetaPrecoDao.setTransactionControler(tc);

            tc.start();

            final ColetaPrecoDTO coletaPrecoDto = (ColetaPrecoDTO) model;
            this.getDao().update(coletaPrecoDto);

            avaliacaoColetaPrecoDao.deleteByIdColetaPreco(coletaPrecoDto.getIdColetaPreco());
            this.atualizaCriterios(coletaPrecoDto, avaliacaoColetaPrecoDao);
            this.atualizaAnexos(coletaPrecoDto, tc);

            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
    }

    @Override
    public void deleteByIdItemCotacaoAndIdFornecedor(final Integer idFornecedor, final Integer idItemCotacao) throws Exception {
        this.getDao().deleteByIdItemCotacaoAndIdFornecedor(idFornecedor, idItemCotacao);
    }

    @Override
    public Collection<ColetaPrecoDTO> findByIdItemCotacaoAndIdFornecedor(final Integer idFornecedor, final Integer idItemCotacao) throws Exception {
        return this.getDao().findByIdItemCotacaoAndIdFornecedor(idFornecedor, idItemCotacao);
    }

    @Override
    public Collection<ColetaPrecoDTO> findByIdCotacao(final Integer idCotacao) throws Exception {
        return this.getDao().findByIdCotacao(idCotacao);
    }

    @Override
    public Collection findResultadoByIdItemCotacao(final Integer idItemCotacao) throws Exception {
        return this.getDao().findResultadoByIdItemCotacao(idItemCotacao);
    }

    @Override
    public void defineResultado(final ColetaPrecoDTO coletaPrecoDto) throws Exception {
        if (coletaPrecoDto.getResultadoFinal() == null || coletaPrecoDto.getResultadoFinal().equals(ColetaPrecoDTO.RESULT_EMPATE)) {
            throw new LogicException("Resultado não definido");
        }
        if (coletaPrecoDto.getQuantidadeCompra() == null) {
            throw new LogicException("Quantidade para compra não definida");
        }

        ColetaPrecoDTO coletaPrecoBean = new ColetaPrecoDTO();
        coletaPrecoBean = (ColetaPrecoDTO) this.restore(coletaPrecoDto);

        if (coletaPrecoDto.getQuantidadeCompra().doubleValue() > coletaPrecoBean.getQuantidadeCotada().doubleValue()) {
            throw new LogicException("Quantidade para compra é maior que a quantidade cotada");
        }

        if (coletaPrecoDto.getResultadoFinal().equals(ColetaPrecoDTO.RESULT_MELHOR_COTACAO)
                && (coletaPrecoDto.getQuantidadeCompra() == null || coletaPrecoDto.getQuantidadeCompra().doubleValue() == 0)) {
            throw new LogicException("Quantidade para compra não definida");
        }

        if (coletaPrecoDto.getIdJustifResultado() == null) {
            boolean bExibeJustificativa = !coletaPrecoDto.getResultadoCalculo().equalsIgnoreCase(coletaPrecoDto.getResultadoFinal());
            if (!bExibeJustificativa) {
                bExibeJustificativa = coletaPrecoDto.getQuantidadeCompra().doubleValue() != coletaPrecoBean.getQuantidadeCalculo().doubleValue();
            }

            if (bExibeJustificativa) {
                throw new LogicException("Justificativa não informada");
            }
        }

        final TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());
        try {
            this.getDao().setTransactionControler(tc);

            /*
             * if (coletaPrecoBean.getResultadoCalculo().equals(ColetaPrecoDTO.RESULT_EMPATE)) {
             * if (coletaPrecoDto.getResultadoFinal().equals(ColetaPrecoDTO.RESULT_EMPATE))
             * throw new LogicException("Deve ser definido o vencedor do empate");
             * List<ColetaPrecoDTO> colEmpate = (List<ColetaPrecoDTO>) this.getDao().findByIdItemCotacaoAndPontuacao(coletaPrecoBean.getIdItemCotacao(),
             * coletaPrecoBean.getPontuacao());
             * if (colEmpate != null) {
             * ColetaPrecoDTO coletaEmpateDto = null;
             * for (ColetaPrecoDTO coletaAux : colEmpate) {
             * if (coletaAux.getIdColetaPreco().intValue() != coletaPrecoDto.getIdColetaPreco().intValue() && coletaAux.getResultadoFinal().equals(ColetaPrecoDTO.RESULT_EMPATE)) {
             * coletaEmpateDto = coletaAux;
             * break;
             * }
             * }
             * if (coletaEmpateDto != null) {
             * coletaEmpateDto.setResultadoFinal(ColetaPrecoDTO.RESULT_DESCLASSIFICADA);
             * if (coletaPrecoDto.getResultadoFinal().equals(ColetaPrecoDTO.RESULT_DESCLASSIFICADA))
             * coletaEmpateDto.setResultadoFinal(ColetaPrecoDTO.RESULT_MELHOR_COTACAO);
             * coletaEmpateDto.setIdRespResultado(coletaPrecoDto.getIdRespResultado());
             * coletaEmpateDto.setIdJustifResultado(coletaPrecoDto.getIdJustifResultado());
             * coletaEmpateDto.setComplemJustifResultado(coletaPrecoDto.getComplemJustifResultado());
             * this.getDao().atualizaResultadoFinal(coletaEmpateDto);
             * }
             * }
             * }
             */

            tc.start();

            this.getDao().atualizaResultadoFinal(coletaPrecoDto);
            new ItemCotacaoServiceEjb().validaEAtualiza(tc, coletaPrecoDto.getIdItemCotacao());

            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
    }

}
