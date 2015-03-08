package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citcorpore.bean.FornecedorProdutoDTO;
import br.com.centralit.citcorpore.bean.RelacionamentoProdutoDTO;
import br.com.centralit.citcorpore.bean.TipoProdutoDTO;
import br.com.centralit.citcorpore.integracao.FornecedorProdutoDao;
import br.com.centralit.citcorpore.integracao.RelacionamentoProdutoDao;
import br.com.centralit.citcorpore.integracao.TipoProdutoDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilDatas;

@SuppressWarnings("unchecked")
public class TipoProdutoServiceEjb extends CrudServiceImpl implements TipoProdutoService {

    private TipoProdutoDao dao;

    @Override
    protected TipoProdutoDao getDao() {
        if (dao == null) {
            dao = new TipoProdutoDao();
        }
        return dao;
    }

    @Override
    public List<TipoProdutoDTO> findByIdCategoria(final Integer idCategoria) throws Exception {
        return this.getDao().findByIdCategoria(idCategoria);
    }

    @Override
    public boolean consultarTiposProdutos(final TipoProdutoDTO tipoProdutoDTO) throws Exception {
        return this.getDao().consultarTiposProdutos(tipoProdutoDTO);
    }

    @Override
    public IDto create(final IDto model, final HttpServletRequest request) throws Exception {

        final TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());

        try {

            TipoProdutoDTO tipoProdutoDTO = (TipoProdutoDTO) model;

            this.getDao().setTransactionControler(tc);

            final RelacionamentoProdutoDao relacionamentoProdutoDao = new RelacionamentoProdutoDao();
            relacionamentoProdutoDao.setTransactionControler(tc);

            final FornecedorProdutoDao fornecedorProdutoDao = new FornecedorProdutoDao();
            fornecedorProdutoDao.setTransactionControler(tc);

            tc.start();

            tipoProdutoDTO = (TipoProdutoDTO) this.getDao().create(tipoProdutoDTO);

            final ArrayList<RelacionamentoProdutoDTO> listaRelacionamentos = (ArrayList<RelacionamentoProdutoDTO>) br.com.citframework.util.WebUtil
                    .deserializeCollectionFromRequest(RelacionamentoProdutoDTO.class, "relacionamentosSerializados", request);

            final ArrayList<FornecedorProdutoDTO> listaFornecedores = (ArrayList<FornecedorProdutoDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(
                    FornecedorProdutoDTO.class, "fornecedoresSerializados", request);

            if (listaRelacionamentos != null && !listaRelacionamentos.isEmpty()) {

                for (final RelacionamentoProdutoDTO relacionamentoProdutoDTO : listaRelacionamentos) {
                    relacionamentoProdutoDTO.setIdTipoProduto(tipoProdutoDTO.getIdTipoProduto());
                    final RelacionamentoProdutoDTO relacionamentoprodutoAux = (RelacionamentoProdutoDTO) relacionamentoProdutoDao.restore(relacionamentoProdutoDTO);
                    if (relacionamentoprodutoAux == null) {
                        relacionamentoProdutoDao.create(relacionamentoProdutoDTO);
                    }
                }
            }

            if (listaFornecedores != null && !listaFornecedores.isEmpty()) {

                for (final FornecedorProdutoDTO fornecedorProdutoDTO : listaFornecedores) {
                    fornecedorProdutoDTO.setDataInicio(UtilDatas.getDataAtual());
                    fornecedorProdutoDTO.setIdTipoProduto(tipoProdutoDTO.getIdTipoProduto());
                    final FornecedorProdutoDTO fornecedorProdutoDTOAux = fornecedorProdutoDao.findByIdTipoProdutoAndFornecedor(fornecedorProdutoDTO.getIdTipoProduto(),
                            fornecedorProdutoDTO.getIdFornecedor());
                    if (fornecedorProdutoDTOAux == null) {
                        fornecedorProdutoDao.create(fornecedorProdutoDTO);
                    }
                }
            }

            tc.commit();
            tc.close();
        } catch (final Exception e) {
            e.printStackTrace();
            this.rollbackTransaction(tc, e);
        }
        return null;

    }

    @SuppressWarnings("rawtypes")
    @Override
    public void update(final IDto model, final HttpServletRequest request) throws ServiceException, LogicException {
        final TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());

        try {

            Collection<FornecedorProdutoDTO> colFornecedorProdutoDTO = new ArrayList();

            final TipoProdutoDTO tipoProdutoDTO = (TipoProdutoDTO) model;

            this.getDao().setTransactionControler(tc);

            final FornecedorProdutoDao fornecedorProdutoDao = new FornecedorProdutoDao();
            fornecedorProdutoDao.setTransactionControler(tc);

            final RelacionamentoProdutoDao relacionamentoProdutoDao = new RelacionamentoProdutoDao();
            relacionamentoProdutoDao.setTransactionControler(tc);

            tc.start();

            this.getDao().update(tipoProdutoDTO);

            relacionamentoProdutoDao.deleteByIdTipoProduto(tipoProdutoDTO.getIdTipoProduto());

            final ArrayList<RelacionamentoProdutoDTO> listaRelacionamentos = (ArrayList<RelacionamentoProdutoDTO>) br.com.citframework.util.WebUtil
                    .deserializeCollectionFromRequest(RelacionamentoProdutoDTO.class, "relacionamentosSerializados", request);

            final ArrayList<FornecedorProdutoDTO> listaFornecedores = (ArrayList<FornecedorProdutoDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(
                    FornecedorProdutoDTO.class, "fornecedoresSerializados", request);

            if (listaRelacionamentos != null && !listaRelacionamentos.isEmpty()) {

                for (final RelacionamentoProdutoDTO relacionamentoProdutoDTO : listaRelacionamentos) {
                    relacionamentoProdutoDTO.setIdTipoProduto(tipoProdutoDTO.getIdTipoProduto());
                    final RelacionamentoProdutoDTO relacionamentoprodutoAux = (RelacionamentoProdutoDTO) relacionamentoProdutoDao.restore(relacionamentoProdutoDTO);
                    if (relacionamentoprodutoAux == null) {
                        relacionamentoProdutoDao.create(relacionamentoProdutoDTO);
                    }
                }
            }
            colFornecedorProdutoDTO = fornecedorProdutoDao.findByIdTipoProduto(tipoProdutoDTO.getIdTipoProduto());

            if (listaFornecedores != null && !listaFornecedores.isEmpty()) {

                for (final FornecedorProdutoDTO fornecedorProdutoDTO : listaFornecedores) {

                    if (colFornecedorProdutoDTO != null && !colFornecedorProdutoDTO.isEmpty()) {

                        for (final FornecedorProdutoDTO fornecedor : colFornecedorProdutoDTO) {
                            if (fornecedorProdutoDTO.getIdFornecedor().equals(fornecedor.getIdFornecedor())) {
                                fornecedorProdutoDTO.setDataInicio(fornecedor.getDataInicio());
                                fornecedorProdutoDTO.setIdTipoProduto(tipoProdutoDTO.getIdTipoProduto());
                                fornecedorProdutoDTO.setIdFornecedorProduto(fornecedor.getIdFornecedorProduto());
                                fornecedorProdutoDao.update(fornecedorProdutoDTO);
                                break;
                            }
                        }
                    }
                    if (fornecedorProdutoDTO.getIdTipoProduto() == null) {
                        fornecedorProdutoDTO.setDataInicio(UtilDatas.getDataAtual());
                        fornecedorProdutoDTO.setIdTipoProduto(tipoProdutoDTO.getIdTipoProduto());
                        final FornecedorProdutoDTO fornecedorProdutoDTOAux = fornecedorProdutoDao.findByIdTipoProdutoAndFornecedor(fornecedorProdutoDTO.getIdTipoProduto(),
                                fornecedorProdutoDTO.getIdFornecedor());
                        if (fornecedorProdutoDTOAux == null) {
                            fornecedorProdutoDao.create(fornecedorProdutoDTO);
                        }
                    }
                }
            }

            tc.commit();
            tc.close();
        } catch (final Exception e) {
            e.printStackTrace();
            this.rollbackTransaction(tc, e);
        }

    }

}
