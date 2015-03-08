package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.CategoriaProdutoDTO;
import br.com.centralit.citcorpore.bean.CriterioCotacaoCategoriaDTO;
import br.com.centralit.citcorpore.integracao.CategoriaProdutoDao;
import br.com.centralit.citcorpore.integracao.CriterioCotacaoCategoriaDao;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.integracao.ControleGEDDao;
import br.com.centralit.citged.negocio.ControleGEDServiceBean;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.Constantes;

public class CategoriaProdutoServiceEjb extends CrudServiceImpl implements CategoriaProdutoService {

    private CategoriaProdutoDao dao;

    @Override
    protected CategoriaProdutoDao getDao() {
        if (dao == null) {
            dao = new CategoriaProdutoDao();
        }
        return dao;
    }

    @Override
    protected void validaCreate(final Object arg0) throws Exception {
        this.validaPesos(arg0);
    }

    @Override
    protected void validaUpdate(final Object arg0) throws Exception {
        this.validaPesos(arg0);
    }

    private void validaPesos(final Object arg0) throws Exception {
        final CategoriaProdutoDTO categoriaProdutoDto = (CategoriaProdutoDTO) arg0;

        if (categoriaProdutoDto.getPesoCotacaoPreco() == null) {
            throw new Exception("Peso para o critério preço não foi informado");
        }
        if (categoriaProdutoDto.getPesoCotacaoPrazoEntrega() == null) {
            throw new Exception("Peso para o critério prazo de entrega não foi informado");
        }
        if (categoriaProdutoDto.getPesoCotacaoPrazoGarantia() == null) {
            throw new Exception("Peso para o critério prazo de garantia não foi informado");
        }
        if (categoriaProdutoDto.getPesoCotacaoPrazoPagto() == null) {
            throw new Exception("Peso para o critério prazo de pagamento não foi informado");
        }
        if (categoriaProdutoDto.getPesoCotacaoTaxaJuros() == null) {
            throw new Exception("Peso para o critério taxa de juros não foi informado");
        }

        if (categoriaProdutoDto.getPesoCotacaoPreco().intValue() > 10) {
            throw new Exception("Peso para o critério preço deve estar entre 0 e 10");
        }
        if (categoriaProdutoDto.getPesoCotacaoPrazoEntrega().intValue() > 10) {
            throw new Exception("Peso para o critério prazo deve estar entre 0 e 10");
        }
        if (categoriaProdutoDto.getPesoCotacaoPrazoGarantia().intValue() > 10) {
            throw new Exception("Peso para o critério prazo deve estar entre 0 e 10");
        }
        if (categoriaProdutoDto.getPesoCotacaoPrazoPagto().intValue() > 10) {
            throw new Exception("Peso para o critério prazo deve estar entre 0 e 10");
        }
        if (categoriaProdutoDto.getPesoCotacaoTaxaJuros().intValue() > 10) {
            throw new Exception("Peso para o critério taxa deve estar entre 0 e 10");
        }

    }

    @Override
    public Collection listAtivas() throws Exception {
        return this.getHierarquia(false, false);
    }

    @Override
    public Collection list() throws ServiceException, LogicException {
        try {
            return this.getHierarquia(true, false);
        } catch (final Exception e) {}
        return null;
    }

    public Collection getHierarquia(final boolean acrescentarInativos, final boolean somenteRequisicaoProdutos) throws Exception {
        final Collection<CategoriaProdutoDTO> colSemPai = this.getDao().findSemPai();
        if (colSemPai == null) {
            return null;
        }

        final Collection colRetorno = new ArrayList();
        boolean bAcrescenta;
        for (final CategoriaProdutoDTO categoriaProdutoDto : colSemPai) {
            bAcrescenta = true;
            if (!acrescentarInativos && !categoriaProdutoDto.getSituacao().equalsIgnoreCase("A")) {
                bAcrescenta = false;
            }
            if (bAcrescenta) {
                categoriaProdutoDto.setNivel(new Integer(0));
                colRetorno.add(categoriaProdutoDto);

                final Collection colFilhos = this.carregaFilhos(categoriaProdutoDto.getIdCategoria(), 0, acrescentarInativos);
                if (colFilhos != null) {
                    colRetorno.addAll(colFilhos);
                }
            }
        }
        return colRetorno;
    }

    private Collection carregaFilhos(final Integer idPai, final int nivel, final boolean acrescentarInativos) throws Exception {
        final Collection<CategoriaProdutoDTO> colFilhos = this.getDao().findByIdPai(idPai);
        if (colFilhos == null) {
            return null;
        }

        final Collection colRetorno = new ArrayList();

        boolean bAcrescenta;
        for (final CategoriaProdutoDTO categoriaProdutoDto : colFilhos) {
            bAcrescenta = true;
            if (!acrescentarInativos && !categoriaProdutoDto.getSituacao().equalsIgnoreCase("A")) {
                bAcrescenta = false;
            }
            if (bAcrescenta) {
                categoriaProdutoDto.setNivel(new Integer(nivel + 1));
                colRetorno.add(categoriaProdutoDto);

                final Collection colFilhosFilhos = this.carregaFilhos(categoriaProdutoDto.getIdCategoria(), nivel + 1, acrescentarInativos);
                if (colFilhosFilhos != null) {
                    colRetorno.addAll(colFilhosFilhos);
                }
            }
        }
        return colRetorno;
    }

    public Collection listPermiteRequisicaoProduto() throws Exception {
        return this.getHierarquia(false, true);
    }

    @Override
    public void recuperaImagem(final CategoriaProdutoDTO categoriaProdutoDto) throws Exception {
        categoriaProdutoDto.setImagem(null);
        if (categoriaProdutoDto.getIdCategoria() != null && categoriaProdutoDto.getIdCategoria() > 0) {
            final List<ControleGEDDTO> colGed = (List<ControleGEDDTO>) new ControleGEDDao().listByIdTabelaAndID(ControleGEDDTO.TABELA_CATEGORIAPRODUTO,
                    categoriaProdutoDto.getIdCategoria());
            if (colGed != null && !colGed.isEmpty()) {
                try {
                    categoriaProdutoDto.setImagem(new ControleGEDServiceBean().getRelativePathFromGed(colGed.get(0)));
                } catch (final Exception e) {}
            }
        }
    }

    @Override
    public Collection findByIdPai(final Integer idPai) throws Exception {
        return this.getDao().findByIdPai(idPai);
    }

    @Override
    public boolean existeIgual(final CategoriaProdutoDTO categoriaProduto) throws Exception {
        return this.getDao().existeIgual(categoriaProduto);
    }

    @Override
    public String getHierarquiaHTML(final String acao) throws Exception {
        String result = "";
        final Collection<CategoriaProdutoDTO> colCategorias = this.listAtivas();
        if (colCategorias != null) {
            int maiorNivel = 0;
            for (final CategoriaProdutoDTO categoriaProdutoDto : colCategorias) {
                if (categoriaProdutoDto.getNivel().intValue() > maiorNivel) {
                    maiorNivel = categoriaProdutoDto.getNivel().intValue();
                }
            }
            result = "<table>";
            for (final CategoriaProdutoDTO categoriaProdutoDto : colCategorias) {
                this.recuperaImagem(categoriaProdutoDto);
                String path = categoriaProdutoDto.getImagem();
                if (path == null || path.equals("")) {
                    path = Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/folder.png";
                }
                if (acao != null && !acao.trim().equals("")) {
                    result += "<tr onclick='"
                            + acao
                            + "("
                            + categoriaProdutoDto.getIdCategoria()
                            + ")' id='trCateg_"
                            + categoriaProdutoDto.getIdCategoria()
                            + "' style=\"cursor: pointer;height: 18px !important; padding: 0px 0px 0px 0px !important\" onMouseOver='TrowOn(this,\"#eee\");' onMouseOut='TrowOff(this)'>";
                } else {
                    result += "<tr id='trCateg_" + categoriaProdutoDto.getIdCategoria()
                            + "' style=\"cursor: pointer;height: 18px !important; padding: 0px 0px 0px 0px !important\" >";
                }
                result += "<td><table><tr>";
                for (int i = 0; i <= categoriaProdutoDto.getNivel().intValue(); i++) {
                    if (i < categoriaProdutoDto.getNivel().intValue()) {
                        result += "<td width='16px'>&nbsp;</td>";
                    } else {
                        result += "<td><img style='width:16px;height:16px' src=\"" + path + "\" />" + categoriaProdutoDto.getNomeCategoria() + "</td>";
                    }
                }
                result += "</tr></table></td>";
                result += "</tr>";
            }
            result += "</table>";
        }
        return result;
    }

    private void atualizaAnexos(final CategoriaProdutoDTO categoriaProdutoDto, final TransactionControler tc) throws Exception {
        new ControleGEDServiceBean().atualizaAnexos(categoriaProdutoDto.getFotos(), ControleGEDDTO.TABELA_CATEGORIAPRODUTO, categoriaProdutoDto.getIdCategoria(), tc);
    }

    @Override
    public IDto create(final IDto model) throws ServiceException, LogicException {
        final CriterioCotacaoCategoriaDao criterioCotacaoCategoriaDao = new CriterioCotacaoCategoriaDao();
        final TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());

        try {
            this.validaCreate(model);

            this.getDao().setTransactionControler(tc);
            criterioCotacaoCategoriaDao.setTransactionControler(tc);

            tc.start();

            CategoriaProdutoDTO categoriaProdutoDto = (CategoriaProdutoDTO) model;
            categoriaProdutoDto = (CategoriaProdutoDTO) this.getDao().create(categoriaProdutoDto);

            this.atualizaCriterios(categoriaProdutoDto, criterioCotacaoCategoriaDao);
            this.atualizaAnexos(categoriaProdutoDto, tc);

            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
        return model;
    }

    private void atualizaCriterios(final CategoriaProdutoDTO categoriaProdutoDto, final CriterioCotacaoCategoriaDao criterioCotacaoCategoriaDao) throws Exception {
        criterioCotacaoCategoriaDao.deleteByIdCategoria(categoriaProdutoDto.getIdCategoria());
        if (categoriaProdutoDto.getColCriterios() != null) {
            for (final CriterioCotacaoCategoriaDTO criterioDto : categoriaProdutoDto.getColCriterios()) {
                if (criterioDto.getPesoCotacao() == null) {
                    throw new Exception("Peso não informado");
                }
                if (criterioDto.getPesoCotacao().intValue() > 10) {
                    throw new Exception("O peso deve estar entre 0 e 10");
                }
                criterioDto.setIdCategoria(categoriaProdutoDto.getIdCategoria());
                criterioCotacaoCategoriaDao.create(criterioDto);
            }
        }
    }

    @Override
    public void update(final IDto model) throws ServiceException, LogicException {
        final CriterioCotacaoCategoriaDao criterioCotacaoCategoriaDao = new CriterioCotacaoCategoriaDao();
        final TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());

        try {
            this.validaUpdate(model);

            this.getDao().setTransactionControler(tc);
            criterioCotacaoCategoriaDao.setTransactionControler(tc);

            tc.start();

            final CategoriaProdutoDTO categoriaProdutoDto = (CategoriaProdutoDTO) model;
            this.getDao().update(categoriaProdutoDto);

            this.atualizaCriterios(categoriaProdutoDto, criterioCotacaoCategoriaDao);
            this.atualizaAnexos(categoriaProdutoDto, tc);

            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
    }

}
