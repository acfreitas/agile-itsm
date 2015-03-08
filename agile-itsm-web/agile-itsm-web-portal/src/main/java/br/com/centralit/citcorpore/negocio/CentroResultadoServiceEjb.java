package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import br.com.centralit.citcorpore.bean.AlcadaCentroResultadoDTO;
import br.com.centralit.citcorpore.bean.CentroResultadoDTO;
import br.com.centralit.citcorpore.bean.HistoricoRespCentroResultadoDTO;
import br.com.centralit.citcorpore.bean.ResponsavelCentroResultadoDTO;
import br.com.centralit.citcorpore.bean.ResponsavelCentroResultadoProcessoDTO;
import br.com.centralit.citcorpore.integracao.AlcadaCentroResultadoDAO;
import br.com.centralit.citcorpore.integracao.CentroResultadoDao;
import br.com.centralit.citcorpore.integracao.HistoricoRespCentroResultadoDao;
import br.com.centralit.citcorpore.integracao.ResponsavelCentroResultadoDao;
import br.com.centralit.citcorpore.integracao.ResponsavelCentroResultadoProcessoDao;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.integracao.ControleGEDDao;
import br.com.centralit.citged.negocio.ControleGEDServiceBean;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilDatas;

@SuppressWarnings({"rawtypes", "unchecked"})
public class CentroResultadoServiceEjb extends CrudServiceImpl implements CentroResultadoService {

    private CentroResultadoDao dao;

    @Override
    protected CentroResultadoDao getDao() {
        if (dao == null) {
            dao = new CentroResultadoDao();
        }
        return dao;
    }

    @Override
    public Collection list() throws ServiceException, LogicException {
        Collection lista = null;
        try {
            lista = this.getDao().list();
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public Collection findByIdPai(final Integer idPai) throws Exception {
        return this.getDao().findByIdPai(idPai);
    }

    @Override
    public void recuperaImagem(final CentroResultadoDTO centroResultadoDTO) throws Exception {
        centroResultadoDTO.setImagem(null);
        final List<ControleGEDDTO> colGed = (List<ControleGEDDTO>) new ControleGEDDao().listByIdTabelaAndID(ControleGEDDTO.TABELA_CENTRORESULTADO,
                centroResultadoDTO.getIdCentroResultado());

        if (colGed != null & !colGed.isEmpty()) {
            centroResultadoDTO.setImagem(new ControleGEDServiceBean().getRelativePathFromGed(colGed.get(0)));
        }
    }

    @Override
    public Collection findSemPai() throws Exception {
        return this.getDao().findSemPai();
    }

    @Override
    public boolean temFilhos(final int idCentroResultado) throws Exception {
        return this.getDao().temFilhos(idCentroResultado);
    }

    @Override
    public Collection find(final CentroResultadoDTO centroResultadoDTO) throws Exception {
        return this.getDao().find(centroResultadoDTO);
    }

    @Override
    public Collection listAtivos() throws Exception {
        return this.getHierarquia(false, false);
    }

    public Collection getHierarquia(final boolean acrescentarInativos, final boolean somenteRequisicaoProdutos) throws Exception {
        final Collection<CentroResultadoDTO> colSemPai = this.getDao().findSemPai();
        if (colSemPai == null) {
            return null;
        }

        final Collection colRetorno = new ArrayList();
        boolean bAcrescenta;
        for (final CentroResultadoDTO centroResultadoDto : colSemPai) {
            bAcrescenta = true;
            /*
             * if (!acrescentarInativos && !centroResultadoDto.getSituacao().equalsIgnoreCase("A"))
             * bAcrescenta = false;
             * if (bAcrescenta) {
             * if (somenteRequisicaoProdutos && centroResultadoDto.getPermiteRequisicaoProduto() != null && !centroResultadoDto.getPermiteRequisicaoProduto().equalsIgnoreCase("S"))
             * bAcrescenta = false;
             * }
             */
            if (bAcrescenta) {
                centroResultadoDto.setNivel(new Integer(0));
                colRetorno.add(centroResultadoDto);

                final Collection colFilhos = this.carregaFilhos(centroResultadoDto.getIdCentroResultado(), 0, acrescentarInativos, somenteRequisicaoProdutos);
                if (colFilhos != null) {
                    colRetorno.addAll(colFilhos);
                }
            }
        }
        return colRetorno;
    }

    @Override
    public Collection listAtivosVinculados(final Integer idSolicitante, final String tipoAlcada) throws Exception {
        return this.getHierarquiaSomenteVinculados(idSolicitante, tipoAlcada);
    }

    public Collection getHierarquiaSomenteVinculados(final Integer idSolicitante, final String tipoAlcada) throws Exception {
        final Collection<CentroResultadoDTO> colSemPai = this.getDao().findPaisVinculados(idSolicitante, tipoAlcada);
        if (colSemPai == null) {
            return null;
        }
        final Collection colRetorno = new ArrayList();
        for (final CentroResultadoDTO centroResultadoDto : colSemPai) {
            centroResultadoDto.setNivel(new Integer(0));
            final Collection colFilhos = this.carregaFilhosVinculados(centroResultadoDto.getIdCentroResultado(), idSolicitante, 0, tipoAlcada);
            if (colFilhos != null) {
                colRetorno.add(centroResultadoDto);
                colRetorno.addAll(colFilhos);
            }
        }
        return colRetorno;
    }

    /**
     * Lista centros de custos que possuem vinculo com a alçada
     *
     * @param acrescentarInativos
     * @param somenteRequisicaoProdutos
     * @return
     * @throws Exception
     */
    @Override
    public Collection getHierarquiaCentroDeCustoAtivo(final boolean acrescentarInativos, final boolean somenteRequisicaoProdutos, final boolean somenteRequisicaoViagem)
            throws Exception {

        Collection<CentroResultadoDTO> colSemPai;

        if (somenteRequisicaoViagem) {
            colSemPai = this.getDao().findPaisVinculados("Viagem");
        } else {
            colSemPai = this.getDao().findSemPai();
        }

        if (colSemPai == null) {
            return null;
        }

        final Collection colRetorno = new ArrayList();
        boolean bAcrescenta;

        for (final CentroResultadoDTO centroResultadoDto : colSemPai) {

            bAcrescenta = true;
            if (bAcrescenta) {
                centroResultadoDto.setNivel(new Integer(0));
                colRetorno.add(centroResultadoDto);

                Collection colFilhos;

                if (somenteRequisicaoViagem) {
                    colFilhos = this.carregaFilhosComAlcadaVinculado(centroResultadoDto.getIdCentroResultado(), 0, acrescentarInativos, somenteRequisicaoProdutos);
                } else {
                    colFilhos = this.carregaFilhosComAlcadaViagemVinculado(centroResultadoDto.getIdCentroResultado(), 0, acrescentarInativos, somenteRequisicaoProdutos);
                }

                if (colFilhos != null) {
                    colRetorno.addAll(colFilhos);
                }

            }
        }

        return colRetorno;
    }

    /**
     * Lista centros de custos que possuem vinculo com a alçada
     *
     * @param idPai
     * @param nivel
     * @param acrescentarInativos
     * @param somenteRequisicaoProdutos
     * @return
     * @throws Exception
     */
    private Collection carregaFilhosComAlcadaVinculado(final Integer idPai, final int nivel, final boolean acrescentarInativos, final boolean somenteRequisicaoProdutos)
            throws Exception {

        // Conforme requisito da iniciativa 078 - Melhorias no módulo de viagem. Somente serão exibidos centro de custo com alçada vinculada
        final Collection<CentroResultadoDTO> colFilhos = this.getDao().consultarCentroDeCustoComVinculoViagemNaAlcada(idPai);

        if (colFilhos == null) {
            return null;
        }

        final Collection colRetorno = new ArrayList();

        boolean bAcrescenta;
        for (final CentroResultadoDTO centroResultadoDto : colFilhos) {
            bAcrescenta = true;
            if (!acrescentarInativos && !centroResultadoDto.getSituacao().equalsIgnoreCase("A")) {
                bAcrescenta = false;
            }
            if (bAcrescenta) {
                if (somenteRequisicaoProdutos && !centroResultadoDto.getPermiteRequisicaoProduto().equalsIgnoreCase("S")) {
                    bAcrescenta = false;
                }
            }
            if (bAcrescenta) {
                centroResultadoDto.setNivel(new Integer(nivel + 1));
                colRetorno.add(centroResultadoDto);

                final Collection colFilhosFilhos = this.carregaFilhos(centroResultadoDto.getIdCentroResultado(), nivel + 1, acrescentarInativos, somenteRequisicaoProdutos);
                if (colFilhosFilhos != null) {
                    colRetorno.addAll(colFilhosFilhos);
                }
            }
        }

        return colRetorno;

    }

    /**
     * Lista centros de custos que possuem vinculo com a alçada
     *
     * @param idPai
     * @param nivel
     * @param acrescentarInativos
     * @param somenteRequisicaoProdutos
     * @return
     * @throws Exception
     */
    private Collection carregaFilhosComAlcadaViagemVinculado(final Integer idPai, final int nivel, final boolean acrescentarInativos, final boolean somenteRequisicaoProdutos)
            throws Exception {

        // Conforme requisito da iniciativa 078 - Melhorias no módulo de viagem. Somente serão exibidos centro de custo com alçada vinculada
        final Collection<CentroResultadoDTO> colFilhos = this.getDao().consultarCentroDeCustoComVinculoViagemNaAlcada(idPai);

        if (colFilhos == null) {
            return null;
        }

        final Collection colRetorno = new ArrayList();

        boolean bAcrescenta;
        for (final CentroResultadoDTO centroResultadoDto : colFilhos) {
            bAcrescenta = true;
            if (!acrescentarInativos && !centroResultadoDto.getSituacao().equalsIgnoreCase("A")) {
                bAcrescenta = false;
            }
            if (bAcrescenta) {
                if (somenteRequisicaoProdutos && !centroResultadoDto.getPermiteRequisicaoProduto().equalsIgnoreCase("S")) {
                    bAcrescenta = false;
                }
            }
            if (bAcrescenta) {
                centroResultadoDto.setNivel(new Integer(nivel + 1));
                colRetorno.add(centroResultadoDto);

                final Collection colFilhosFilhos = this.carregaFilhos(centroResultadoDto.getIdCentroResultado(), nivel + 1, acrescentarInativos, somenteRequisicaoProdutos);
                if (colFilhosFilhos != null) {
                    colRetorno.addAll(colFilhosFilhos);
                }
            }
        }

        return colRetorno;

    }

    private Collection carregaFilhos(final Integer idPai, final int nivel, final boolean acrescentarInativos, final boolean somenteRequisicaoProdutos) throws Exception {

        final Collection<CentroResultadoDTO> colFilhos = this.getDao().findByIdPai(idPai);

        if (colFilhos == null) {
            return null;
        }

        final Collection colRetorno = new ArrayList();

        boolean bAcrescenta;
        for (final CentroResultadoDTO centroResultadoDto : colFilhos) {
            bAcrescenta = true;
            if (!acrescentarInativos && !centroResultadoDto.getSituacao().equalsIgnoreCase("A")) {
                bAcrescenta = false;
            }
            if (bAcrescenta) {
                if (somenteRequisicaoProdutos && !centroResultadoDto.getPermiteRequisicaoProduto().equalsIgnoreCase("S")) {
                    bAcrescenta = false;
                }
            }
            if (bAcrescenta) {
                centroResultadoDto.setNivel(new Integer(nivel + 1));
                colRetorno.add(centroResultadoDto);

                final Collection colFilhosFilhos = this.carregaFilhos(centroResultadoDto.getIdCentroResultado(), nivel + 1, acrescentarInativos, somenteRequisicaoProdutos);
                if (colFilhosFilhos != null) {
                    colRetorno.addAll(colFilhosFilhos);
                }
            }
        }
        return colRetorno;

    }

    private Collection carregaFilhosVinculados(final Integer idPai, final Integer idSolicitante, final int nivel, final String tipoAlcada) throws Exception {

        final Collection<CentroResultadoDTO> colFilhos = this.getDao().findFilhosVinculados(idPai, idSolicitante, tipoAlcada);

        if (colFilhos == null) {
            return null;
        }

        final Collection colRetorno = new ArrayList();

        for (final CentroResultadoDTO centroResultadoDto : colFilhos) {
            centroResultadoDto.setNivel(new Integer(nivel + 1));
            colRetorno.add(centroResultadoDto);

            final Collection colFilhosFilhos = this.carregaFilhos(centroResultadoDto.getIdCentroResultado(), nivel + 1, false, false);
            if (colFilhosFilhos != null) {
                colRetorno.addAll(colFilhosFilhos);
            }
        }
        return colRetorno;

    }

    @Override
    public Collection listPermiteRequisicaoProduto() throws Exception {
        return this.getHierarquia(false, true);
    }

    @Override
    public Collection listPermiteRequisicaoProdutoAlcadaAtivo() throws Exception {
        return this.getHierarquiaCentroDeCustoAtivo(false, false, true);
    }

    @Override
    public Collection findByIdAlcada(final Integer idAlcada) throws Exception {
        return this.getDao().findByIdAlcada(idAlcada);
    }

    @Override
    public IDto create(final IDto model) throws ServiceException, LogicException {
        final ResponsavelCentroResultadoDao responsavelCentroResultadoDao = new ResponsavelCentroResultadoDao();
        final TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());

        try {
            this.validaCreate(model);

            this.getDao().setTransactionControler(tc);
            responsavelCentroResultadoDao.setTransactionControler(tc);

            tc.start();

            CentroResultadoDTO centroResultadoDto = (CentroResultadoDTO) model;
            centroResultadoDto = (CentroResultadoDTO) this.getDao().create(centroResultadoDto);

            this.atualizaResponsaveis(centroResultadoDto, responsavelCentroResultadoDao);

            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
        return model;
    }

    private void atualizaResponsaveis(final CentroResultadoDTO centroResultadoDto, final ResponsavelCentroResultadoDao responsavelCentroResultadoDao) throws Exception {
        final Collection<ResponsavelCentroResultadoDTO> colExistentes = responsavelCentroResultadoDao.findByIdCentroResultado(centroResultadoDto.getIdCentroResultado());
        final HashMap<String, ResponsavelCentroResultadoDTO> mapExistentes = new HashMap();
        final HashMap<String, ResponsavelCentroResultadoDTO> mapAtuais = new HashMap();
        if (colExistentes != null) {
            for (final ResponsavelCentroResultadoDTO responsavelCentroResultadoDto : colExistentes) {
                mapExistentes.put("" + responsavelCentroResultadoDto.getIdResponsavel(), responsavelCentroResultadoDto);
            }
        }
        final HistoricoRespCentroResultadoDao historicoRespCentroResultadoDao = new HistoricoRespCentroResultadoDao();
        historicoRespCentroResultadoDao.setTransactionControler(responsavelCentroResultadoDao.getTransactionControler());

        final ResponsavelCentroResultadoProcessoDao respCentroResultadoProcessoDao = new ResponsavelCentroResultadoProcessoDao();
        respCentroResultadoProcessoDao.setTransactionControler(responsavelCentroResultadoDao.getTransactionControler());
        respCentroResultadoProcessoDao.deleteByIdCentroResultado(centroResultadoDto.getIdCentroResultado());
        responsavelCentroResultadoDao.deleteByIdCentroResultado(centroResultadoDto.getIdCentroResultado());
        if (centroResultadoDto.getColResponsaveis() != null) {
            for (final ResponsavelCentroResultadoDTO responsavelDto : centroResultadoDto.getColResponsaveis()) {
                if (responsavelDto.getIdProcessoNegocio() == null || responsavelDto.getIdProcessoNegocio().length == 0) {
                    throw new Exception("Processo de negócio não informado");
                }
                if (responsavelDto.getIdResponsavel() == null) {
                    throw new Exception("Empregado não informado");
                }
                responsavelDto.setIdCentroResultado(centroResultadoDto.getIdCentroResultado());
                responsavelCentroResultadoDao.create(responsavelDto);
                for (int i = 0; i < responsavelDto.getIdProcessoNegocio().length; i++) {
                    final ResponsavelCentroResultadoProcessoDTO respCentroResultadoProcessoDto = new ResponsavelCentroResultadoProcessoDTO();
                    respCentroResultadoProcessoDto.setIdCentroResultado(responsavelDto.getIdCentroResultado());
                    respCentroResultadoProcessoDto.setIdResponsavel(responsavelDto.getIdResponsavel());
                    respCentroResultadoProcessoDto.setIdProcessoNegocio(responsavelDto.getIdProcessoNegocio()[i]);
                    respCentroResultadoProcessoDao.create(respCentroResultadoProcessoDto);
                }
                if (mapExistentes.get("" + responsavelDto.getIdResponsavel()) == null) {
                    final HistoricoRespCentroResultadoDTO historicoDto = new HistoricoRespCentroResultadoDTO();
                    historicoDto.setIdCentroResultado(responsavelDto.getIdCentroResultado());
                    historicoDto.setIdResponsavel(responsavelDto.getIdResponsavel());
                    historicoDto.setDataInicio(UtilDatas.getDataAtual());
                    historicoRespCentroResultadoDao.create(historicoDto);
                }
                mapAtuais.put("" + responsavelDto.getIdResponsavel(), responsavelDto);
            }
        }

        final Collection<HistoricoRespCentroResultadoDTO> colHistorico = historicoRespCentroResultadoDao.findAtuaisByIdCentroResultado(centroResultadoDto.getIdCentroResultado());
        if (colHistorico != null) {
            for (final HistoricoRespCentroResultadoDTO historicoDto : colHistorico) {
                if (mapAtuais.get("" + historicoDto.getIdResponsavel()) == null) {
                    historicoDto.setDataFim(UtilDatas.getDataAtual());
                    historicoRespCentroResultadoDao.update(historicoDto);
                }
            }
        }
    }

    @Override
    public void update(final IDto model) throws ServiceException, LogicException {
        final ResponsavelCentroResultadoDao responsavelCentroResultadoDao = new ResponsavelCentroResultadoDao();
        final TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());

        try {
            this.validaUpdate(model);

            this.getDao().setTransactionControler(tc);
            responsavelCentroResultadoDao.setTransactionControler(tc);

            tc.start();

            final CentroResultadoDTO centroResultadoDto = (CentroResultadoDTO) model;
            this.getDao().update(centroResultadoDto);

            this.atualizaResponsaveis(centroResultadoDto, responsavelCentroResultadoDao);

            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
    }

    @Override
    public IDto createAntigo(final IDto model) throws ServiceException, LogicException {
        final AlcadaCentroResultadoDAO alcadaCentroResultadoDao = new AlcadaCentroResultadoDAO();
        final TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());

        try {
            this.validaCreate(model);

            this.getDao().setTransactionControler(tc);
            alcadaCentroResultadoDao.setTransactionControler(tc);

            tc.start();

            CentroResultadoDTO centroResultadoDto = (CentroResultadoDTO) model;
            centroResultadoDto = (CentroResultadoDTO) this.getDao().create(centroResultadoDto);

            this.atualizaAlcadas(centroResultadoDto, alcadaCentroResultadoDao);

            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
        return model;
    }

    private void atualizaAlcadas(final CentroResultadoDTO centroResultadoDto, final AlcadaCentroResultadoDAO alcadaCentroResultadoDao) throws Exception {
        alcadaCentroResultadoDao.deleteByIdCentroResultado(centroResultadoDto.getIdCentroResultado());
        if (centroResultadoDto.getColAlcadas() != null) {
            for (final AlcadaCentroResultadoDTO alcadaDto : centroResultadoDto.getColAlcadas()) {
                if (alcadaDto.getIdAlcada() == null) {
                    throw new Exception("Alçada não informada");
                }
                if (alcadaDto.getIdEmpregado() == null) {
                    throw new Exception("Empregado não informado");
                }
                if (alcadaDto.getDataInicio() == null) {
                    throw new Exception("Data de início informada");
                }
                if (alcadaDto.getDataFim() != null && alcadaDto.getDataFim().compareTo(alcadaDto.getDataInicio()) < 0) {
                    throw new Exception("Data de início não pode ser maior que a data fim");
                }
                alcadaDto.setIdCentroResultado(centroResultadoDto.getIdCentroResultado());
                alcadaCentroResultadoDao.create(alcadaDto);
            }
        }
    }

    @Override
    public void updateAntigo(final IDto model) throws ServiceException, LogicException {
        final AlcadaCentroResultadoDAO alcadaCentroResultadoDao = new AlcadaCentroResultadoDAO();
        final TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());

        try {
            this.validaUpdate(model);

            this.getDao().setTransactionControler(tc);
            alcadaCentroResultadoDao.setTransactionControler(tc);

            tc.start();

            final CentroResultadoDTO centroResultadoDto = (CentroResultadoDTO) model;
            this.getDao().update(centroResultadoDto);

            this.atualizaAlcadas(centroResultadoDto, alcadaCentroResultadoDao);

            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
    }

    @Override
    public Collection consultarCentroDeCustoComVinculoViagemNaAlcada(final Integer idPai) throws Exception {
        return null;
    }

}
