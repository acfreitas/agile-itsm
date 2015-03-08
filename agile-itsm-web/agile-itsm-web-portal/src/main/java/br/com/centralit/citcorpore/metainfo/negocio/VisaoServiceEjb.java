package br.com.centralit.citcorpore.metainfo.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import br.com.centralit.citcorpore.integracao.MatrizVisaoDao;
import br.com.centralit.citcorpore.metainfo.bean.BotaoAcaoVisaoDTO;
import br.com.centralit.citcorpore.metainfo.bean.CamposObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.GrupoVisaoCamposNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.GrupoVisaoCamposNegocioLigacaoDTO;
import br.com.centralit.citcorpore.metainfo.bean.GrupoVisaoDTO;
import br.com.centralit.citcorpore.metainfo.bean.HtmlCodeVisaoDTO;
import br.com.centralit.citcorpore.metainfo.bean.ObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.ScriptsVisaoDTO;
import br.com.centralit.citcorpore.metainfo.bean.ValorVisaoCamposNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.VinculoVisaoDTO;
import br.com.centralit.citcorpore.metainfo.bean.VisaoDTO;
import br.com.centralit.citcorpore.metainfo.bean.VisaoRelacionadaDTO;
import br.com.centralit.citcorpore.metainfo.integracao.BotaoAcaoVisaoDao;
import br.com.centralit.citcorpore.metainfo.integracao.CamposObjetoNegocioDao;
import br.com.centralit.citcorpore.metainfo.integracao.GrupoVisaoCamposNegocioDao;
import br.com.centralit.citcorpore.metainfo.integracao.GrupoVisaoCamposNegocioLigacaoDao;
import br.com.centralit.citcorpore.metainfo.integracao.GrupoVisaoDao;
import br.com.centralit.citcorpore.metainfo.integracao.HtmlCodeVisaoDao;
import br.com.centralit.citcorpore.metainfo.integracao.ObjetoNegocioDao;
import br.com.centralit.citcorpore.metainfo.integracao.ScriptsVisaoDao;
import br.com.centralit.citcorpore.metainfo.integracao.ValorVisaoCamposNegocioDao;
import br.com.centralit.citcorpore.metainfo.integracao.VinculoVisaoDao;
import br.com.centralit.citcorpore.metainfo.integracao.VisaoDao;
import br.com.centralit.citcorpore.metainfo.integracao.VisaoRelacionadaDao;
import br.com.centralit.citcorpore.metainfo.util.MetaUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;

@SuppressWarnings("rawtypes")
public class VisaoServiceEjb extends CrudServiceImpl implements VisaoService {

    private VisaoDao dao;

    protected VisaoDao getDao() {
        if (dao == null) {
            dao = new VisaoDao();
        }
        return dao;
    }

    public Collection listAtivos() throws Exception {
        return getDao().listAtivos();
    }

    @Override
    public IDto create(IDto model) throws ServiceException, LogicException {
        // Instancia Objeto controlador de transacao
        VisaoDao crudDao = this.getDao();
        GrupoVisaoDao grupoVisaoDao = getGrupoVisaoDao();
        GrupoVisaoCamposNegocioDao grupoVisaoCamposNegocioDao = new GrupoVisaoCamposNegocioDao();
        ValorVisaoCamposNegocioDao valorVisaoCamposNegocioDao = new ValorVisaoCamposNegocioDao();
        GrupoVisaoCamposNegocioLigacaoDao grupoVisaoCamposNegocioLigacaoDao = new GrupoVisaoCamposNegocioLigacaoDao();
        VisaoRelacionadaDao visaoRelacionadaDao = new VisaoRelacionadaDao();
        VinculoVisaoDao vinculoVisaoDao = new VinculoVisaoDao();
        ScriptsVisaoDao scriptsVisaoDao = new ScriptsVisaoDao();
        BotaoAcaoVisaoDao botaoAcaoVisaoDao = new BotaoAcaoVisaoDao();
        HtmlCodeVisaoDao htmlCodeVisaoDao = new HtmlCodeVisaoDao();
        MatrizVisaoDao matrizVisaoDao = new MatrizVisaoDao();
        TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
        try {
            // Faz validacao, caso exista.
            validaCreate(model);

            // Instancia ou obtem os DAOs necessarios.

            // Seta o TransactionController para os DAOs
            crudDao.setTransactionControler(tc);
            grupoVisaoDao.setTransactionControler(tc);
            grupoVisaoCamposNegocioDao.setTransactionControler(tc);
            valorVisaoCamposNegocioDao.setTransactionControler(tc);
            grupoVisaoCamposNegocioLigacaoDao.setTransactionControler(tc);
            visaoRelacionadaDao.setTransactionControler(tc);
            vinculoVisaoDao.setTransactionControler(tc);
            scriptsVisaoDao.setTransactionControler(tc);
            botaoAcaoVisaoDao.setTransactionControler(tc);
            htmlCodeVisaoDao.setTransactionControler(tc);
            matrizVisaoDao.setTransactionControler(tc);

            // Inicia transacao
            tc.start();

            // Executa operacoes pertinentes ao negocio.
            VisaoDTO visaoDTO = (VisaoDTO) model;
            model = crudDao.create(model);
            if (visaoDTO != null) {
                if (visaoDTO.getTipoVisao().equalsIgnoreCase(VisaoDTO.MATRIZ)) {
                    visaoDTO.getMatrizVisaoDTO().setIdVisao(visaoDTO.getIdVisao());
                    visaoDTO.getMatrizVisaoDTO().setIdMatriz(null);
                    matrizVisaoDao.create(visaoDTO.getMatrizVisaoDTO());
                }
                if (visaoDTO.getColGrupos() != null) {
                    for (Iterator it = visaoDTO.getColGrupos().iterator(); it.hasNext();) {
                        GrupoVisaoDTO grupoVisaoDTO = (GrupoVisaoDTO) it.next();
                        grupoVisaoDTO.setIdVisao(visaoDTO.getIdVisao());
                        grupoVisaoDTO = (GrupoVisaoDTO) grupoVisaoDao.create(grupoVisaoDTO);
                        if (grupoVisaoDTO != null) {
                            if (grupoVisaoDTO.getColCamposVisao() != null) {
                                int ordem = 0;
                                for (Iterator itCampos = grupoVisaoDTO.getColCamposVisao().iterator(); itCampos.hasNext();) {
                                    ordem++;
                                    GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO) itCampos.next();
                                    grupoVisaoCamposNegocioDTO.setIdGrupoVisao(grupoVisaoDTO.getIdGrupoVisao());
                                    grupoVisaoCamposNegocioDTO.setVisivel("S");
                                    grupoVisaoCamposNegocioDTO.setOrdem(ordem);
                                    if (grupoVisaoCamposNegocioDTO.getTipoNegocio().equalsIgnoreCase(MetaUtil.HIDDEN)) {
                                        grupoVisaoCamposNegocioDTO.setVisivel("N");
                                    }
                                    if (grupoVisaoCamposNegocioDTO.getTamanho() == null) {
                                        grupoVisaoCamposNegocioDTO.setTamanho(0);
                                    }
                                    if (grupoVisaoCamposNegocioDTO.getDecimais() == null) {
                                        grupoVisaoCamposNegocioDTO.setDecimais(0);
                                    }
                                    grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO) grupoVisaoCamposNegocioDao.create(grupoVisaoCamposNegocioDTO);
                                    if (grupoVisaoCamposNegocioDTO.getValoresOpcoes() != null) {
                                        for (int i = 0; i < grupoVisaoCamposNegocioDTO.getValoresOpcoes().length; i++) {
                                            String str = grupoVisaoCamposNegocioDTO.getValoresOpcoes()[i];
                                            String str2[] = str.split("#");

                                            if (str2 != null && str2.length > 0) {
                                                ValorVisaoCamposNegocioDTO valorVisaoCamposNegocioDTO = new ValorVisaoCamposNegocioDTO();
                                                valorVisaoCamposNegocioDTO.setIdGrupoVisao(grupoVisaoDTO.getIdGrupoVisao());
                                                valorVisaoCamposNegocioDTO.setIdCamposObjetoNegocio(grupoVisaoCamposNegocioDTO.getIdCamposObjetoNegocio());
                                                valorVisaoCamposNegocioDTO.setSituacao("A");
                                                valorVisaoCamposNegocioDTO.setValor(str2[0]);
                                                if (str2.length > 1) {
                                                    valorVisaoCamposNegocioDTO.setDescricao(str2[1]);
                                                } else {
                                                    valorVisaoCamposNegocioDTO.setDescricao(" ");
                                                }
                                                valorVisaoCamposNegocioDao.create(valorVisaoCamposNegocioDTO);
                                            }
                                        }
                                    }
                                    if (grupoVisaoCamposNegocioDTO.getTipoNegocio().equalsIgnoreCase(MetaUtil.RELATION)) {
                                        GrupoVisaoCamposNegocioLigacaoDTO grupoVisaoCamposNegocioLigacaoDTOValue = new GrupoVisaoCamposNegocioLigacaoDTO();
                                        grupoVisaoCamposNegocioLigacaoDTOValue.setIdGrupoVisao(grupoVisaoDTO.getIdGrupoVisao());
                                        grupoVisaoCamposNegocioLigacaoDTOValue.setIdCamposObjetoNegocio(grupoVisaoCamposNegocioDTO.getIdCamposObjetoNegocio());
                                        grupoVisaoCamposNegocioLigacaoDTOValue.setIdCamposObjetoNegocioLigacao(grupoVisaoCamposNegocioDTO.getIdCamposObjetoNegocioLigacaoVinc());
                                        grupoVisaoCamposNegocioLigacaoDTOValue.setDescricao(grupoVisaoCamposNegocioDTO.getDescricaoRelacionamento());
                                        grupoVisaoCamposNegocioLigacaoDTOValue.setTipoLigacao(GrupoVisaoCamposNegocioLigacaoDTO.VALUE);
                                        grupoVisaoCamposNegocioLigacaoDao.create(grupoVisaoCamposNegocioLigacaoDTOValue);

                                        GrupoVisaoCamposNegocioLigacaoDTO grupoVisaoCamposNegocioLigacaoDTOApres = new GrupoVisaoCamposNegocioLigacaoDTO();
                                        grupoVisaoCamposNegocioLigacaoDTOApres.setIdGrupoVisao(grupoVisaoDTO.getIdGrupoVisao());
                                        grupoVisaoCamposNegocioLigacaoDTOApres.setIdCamposObjetoNegocio(grupoVisaoCamposNegocioDTO.getIdCamposObjetoNegocio());
                                        grupoVisaoCamposNegocioLigacaoDTOApres.setIdCamposObjetoNegocioLigacao(grupoVisaoCamposNegocioDTO.getIdCamposObjetoNegocioLigacao());
                                        grupoVisaoCamposNegocioLigacaoDTOApres.setDescricao(grupoVisaoCamposNegocioDTO.getDescricaoRelacionamento());
                                        grupoVisaoCamposNegocioLigacaoDTOApres.setTipoLigacao(GrupoVisaoCamposNegocioLigacaoDTO.PRESENTATION);
                                        grupoVisaoCamposNegocioLigacaoDao.create(grupoVisaoCamposNegocioLigacaoDTOApres);

                                        GrupoVisaoCamposNegocioLigacaoDTO grupoVisaoCamposNegocioLigacaoDTOFilter = new GrupoVisaoCamposNegocioLigacaoDTO();
                                        grupoVisaoCamposNegocioLigacaoDTOFilter.setIdGrupoVisao(grupoVisaoDTO.getIdGrupoVisao());
                                        grupoVisaoCamposNegocioLigacaoDTOFilter.setIdCamposObjetoNegocio(grupoVisaoCamposNegocioDTO.getIdCamposObjetoNegocio());
                                        grupoVisaoCamposNegocioLigacaoDTOFilter.setIdCamposObjetoNegocioLigacao(grupoVisaoCamposNegocioDTO.getIdCamposObjetoNegocioLigacao());
                                        grupoVisaoCamposNegocioLigacaoDTOFilter.setDescricao(grupoVisaoCamposNegocioDTO.getDescricaoRelacionamento());
                                        grupoVisaoCamposNegocioLigacaoDTOFilter.setTipoLigacao(GrupoVisaoCamposNegocioLigacaoDTO.FILTER);
                                        if (grupoVisaoCamposNegocioDTO.getFiltro() == null || grupoVisaoCamposNegocioDTO.getFiltro().trim().equalsIgnoreCase("")) {
                                            grupoVisaoCamposNegocioLigacaoDTOFilter.setFiltro("${TERMO_PESQUISA}");
                                        } else {
                                            grupoVisaoCamposNegocioLigacaoDTOFilter.setFiltro(grupoVisaoCamposNegocioDTO.getFiltro());
                                        }
                                        grupoVisaoCamposNegocioLigacaoDao.create(grupoVisaoCamposNegocioLigacaoDTOFilter);

                                        if (grupoVisaoCamposNegocioDTO.getIdCamposObjetoNegocioLigacaoOrder() != null) {
                                            GrupoVisaoCamposNegocioLigacaoDTO grupoVisaoCamposNegocioLigacaoDTOOrder = new GrupoVisaoCamposNegocioLigacaoDTO();
                                            grupoVisaoCamposNegocioLigacaoDTOOrder.setIdGrupoVisao(grupoVisaoDTO.getIdGrupoVisao());
                                            grupoVisaoCamposNegocioLigacaoDTOOrder.setIdCamposObjetoNegocio(grupoVisaoCamposNegocioDTO.getIdCamposObjetoNegocio());
                                            grupoVisaoCamposNegocioLigacaoDTOOrder.setIdCamposObjetoNegocioLigacao(grupoVisaoCamposNegocioDTO
                                                    .getIdCamposObjetoNegocioLigacaoOrder());
                                            grupoVisaoCamposNegocioLigacaoDTOOrder.setDescricao(grupoVisaoCamposNegocioDTO.getDescricaoRelacionamento());
                                            grupoVisaoCamposNegocioLigacaoDTOOrder.setTipoLigacao(GrupoVisaoCamposNegocioLigacaoDTO.ORDER);
                                            grupoVisaoCamposNegocioLigacaoDao.create(grupoVisaoCamposNegocioLigacaoDTOOrder);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                if (visaoDTO.getColVisoesRelacionadas() != null) {
                    for (Iterator itColVisRel = visaoDTO.getColVisoesRelacionadas().iterator(); itColVisRel.hasNext();) {
                        VisaoRelacionadaDTO visaoRelacionadaDTO = (VisaoRelacionadaDTO) itColVisRel.next();
                        visaoRelacionadaDTO.setIdVisaoPai(visaoDTO.getIdVisao());
                        visaoRelacionadaDTO = (VisaoRelacionadaDTO) visaoRelacionadaDao.create(visaoRelacionadaDTO);
                        if (visaoRelacionadaDTO.getColVinculosVisao() != null) {
                            int x = 0;
                            for (Iterator it = visaoRelacionadaDTO.getColVinculosVisao().iterator(); it.hasNext();) {
                                VinculoVisaoDTO vinculoVisaoDTO = (VinculoVisaoDTO) it.next();
                                if (vinculoVisaoDTO.getControle() != null && vinculoVisaoDTO.getControle().equalsIgnoreCase("P")) {
                                    Collection col1 = grupoVisaoDao.findByIdVisaoAtivos(visaoDTO.getIdVisao());
                                    if (col1 != null && col1.size() > 0) {
                                        GrupoVisaoDTO grupoVisaoDTO = (GrupoVisaoDTO) ((List) (col1)).get(0);
                                        vinculoVisaoDTO.setIdGrupoVisaoPai(grupoVisaoDTO.getIdGrupoVisao());
                                    }
                                }
                                if (vinculoVisaoDTO.getControle() != null && vinculoVisaoDTO.getControle().equalsIgnoreCase("F")) {
                                    Collection col1 = grupoVisaoDao.findByIdVisaoAtivos(visaoRelacionadaDTO.getIdVisaoFilha());
                                    if (col1 != null && col1.size() > 0) {
                                        GrupoVisaoDTO grupoVisaoDTO = (GrupoVisaoDTO) ((List) (col1)).get(0);
                                        vinculoVisaoDTO.setIdGrupoVisaoFilho(grupoVisaoDTO.getIdGrupoVisao());
                                    }
                                }
                                vinculoVisaoDTO.setSeq(x);
                                vinculoVisaoDTO.setIdVisaoRelacionada(visaoRelacionadaDTO.getIdVisaoRelacionada());
                                vinculoVisaoDao.create(vinculoVisaoDTO);
                                x++;
                            }
                        }
                    }
                }

                if (visaoDTO.getColScripts() != null) {
                    for (Iterator it = visaoDTO.getColScripts().iterator(); it.hasNext();) {
                        ScriptsVisaoDTO scriptsVisaoDTO = (ScriptsVisaoDTO) it.next();
                        scriptsVisaoDTO.setIdVisao(visaoDTO.getIdVisao());
                        scriptsVisaoDao.create(scriptsVisaoDTO);
                    }
                }

                if (visaoDTO.getColHtmlCode() != null) {
                    for (Iterator it = visaoDTO.getColHtmlCode().iterator(); it.hasNext();) {
                        HtmlCodeVisaoDTO htmlCodeVisaoDTO = (HtmlCodeVisaoDTO) it.next();
                        htmlCodeVisaoDTO.setIdVisao(visaoDTO.getIdVisao());
                        htmlCodeVisaoDao.create(htmlCodeVisaoDTO);
                    }
                }

                if (visaoDTO.getColBotoes() != null) {
                    int i = 0;
                    for (Iterator it = visaoDTO.getColBotoes().iterator(); it.hasNext();) {
                        BotaoAcaoVisaoDTO botaoAcaoVisaoDto = (BotaoAcaoVisaoDTO) it.next();
                        botaoAcaoVisaoDto.setIdVisao(visaoDTO.getIdVisao());
                        botaoAcaoVisaoDto.setOrdem(i);
                        botaoAcaoVisaoDao.create(botaoAcaoVisaoDto);
                        i++;
                    }
                }
            }

            // Faz commit e fecha a transacao.
            tc.commit();
            tc.close();

            return model;
        } catch (Exception e) {
            this.rollbackTransaction(tc, e);
        }
        return model;
    }

    @Override
    public void update(IDto model) throws ServiceException, LogicException {
        // Instancia Objeto controlador de transacao
        VisaoDao crudDao = getDao();
        GrupoVisaoDao grupoVisaoDao = getGrupoVisaoDao();
        GrupoVisaoCamposNegocioDao grupoVisaoCamposNegocioDao = getGrupoVisaoCamposNegocioDao();
        ValorVisaoCamposNegocioDao valorVisaoCamposNegocioDao = getValorVisaoCamposNegocioDao();
        GrupoVisaoCamposNegocioLigacaoDao grupoVisaoCamposNegocioLigacaoDao = getGrupoVisaoCamposNegocioLigacaoDao();
        VisaoRelacionadaDao visaoRelacionadaDao = getVisaoRelacionadaDao();
        VinculoVisaoDao vinculoVisaoDao = getVinculoVisaoDao();
        ScriptsVisaoDao scriptsVisaoDao = getScriptsVisaoDao();
        BotaoAcaoVisaoDao botaoAcaoVisaoDao = getBotaoAcaoVisaoDao();
        MatrizVisaoDao matrizVisaoDao = getMatrizVisaoDao();
        HtmlCodeVisaoDao htmlCodeVisaoDao = getHtmlCodeVisaoDao();

        TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
        try {
            // Faz validacao, caso exista.
            validaUpdate(model);

            // Instancia ou obtem os DAOs necessarios.

            // Seta o TransactionController para os DAOs
            crudDao.setTransactionControler(tc);
            grupoVisaoDao.setTransactionControler(tc);
            grupoVisaoCamposNegocioDao.setTransactionControler(tc);
            valorVisaoCamposNegocioDao.setTransactionControler(tc);
            grupoVisaoCamposNegocioLigacaoDao.setTransactionControler(tc);
            visaoRelacionadaDao.setTransactionControler(tc);
            vinculoVisaoDao.setTransactionControler(tc);
            scriptsVisaoDao.setTransactionControler(tc);
            botaoAcaoVisaoDao.setTransactionControler(tc);
            htmlCodeVisaoDao.setTransactionControler(tc);
            matrizVisaoDao.setTransactionControler(tc);

            // Inicia transacao
            tc.start();

            // Executa operacoes pertinentes ao negocio.
            VisaoDTO visaoDTO = (VisaoDTO) model;
            crudDao.update(model);
            if (visaoDTO != null) {
                if (visaoDTO.getTipoVisao().equalsIgnoreCase(VisaoDTO.MATRIZ)) {
                    matrizVisaoDao.deleteByIdVisao(visaoDTO.getIdVisao());
                    visaoDTO.getMatrizVisaoDTO().setIdVisao(visaoDTO.getIdVisao());
                    matrizVisaoDao.create(visaoDTO.getMatrizVisaoDTO());
                }
                vinculoVisaoDao.deleteByIdVisaoPai(visaoDTO.getIdVisao());
                visaoRelacionadaDao.deleteByIdVisaoPai(visaoDTO.getIdVisao());
                valorVisaoCamposNegocioDao.deleteByIdVisao(visaoDTO.getIdVisao());
                grupoVisaoCamposNegocioLigacaoDao.deleteByIdVisao(visaoDTO.getIdVisao());
                grupoVisaoCamposNegocioDao.deleteByIdVisao(visaoDTO.getIdVisao());
                grupoVisaoDao.deleteByIdVisao(visaoDTO.getIdVisao());
                scriptsVisaoDao.deleteByIdVisao(visaoDTO.getIdVisao());
                botaoAcaoVisaoDao.deleteByIdVisao(visaoDTO.getIdVisao());
                htmlCodeVisaoDao.deleteByIdVisao(visaoDTO.getIdVisao());
                if (visaoDTO.getColGrupos() != null) {
                    for (Iterator it = visaoDTO.getColGrupos().iterator(); it.hasNext();) {
                        GrupoVisaoDTO grupoVisaoDTO = (GrupoVisaoDTO) it.next();
                        grupoVisaoDTO.setIdVisao(visaoDTO.getIdVisao());
                        grupoVisaoDTO = (GrupoVisaoDTO) grupoVisaoDao.create(grupoVisaoDTO);
                        if (grupoVisaoDTO != null) {
                            if (grupoVisaoDTO.getColCamposVisao() != null) {
                                int ordem = 0;
                                for (Iterator itCampos = grupoVisaoDTO.getColCamposVisao().iterator(); itCampos.hasNext();) {
                                    ordem++;
                                    GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO) itCampos.next();
                                    grupoVisaoCamposNegocioDTO.setIdGrupoVisao(grupoVisaoDTO.getIdGrupoVisao());
                                    grupoVisaoCamposNegocioDTO.setVisivel("S");
                                    grupoVisaoCamposNegocioDTO.setOrdem(ordem);
                                    if (grupoVisaoCamposNegocioDTO.getTipoNegocio().equalsIgnoreCase(MetaUtil.HIDDEN)) {
                                        grupoVisaoCamposNegocioDTO.setVisivel("N");
                                    }
                                    if (grupoVisaoCamposNegocioDTO.getTamanho() == null) {
                                        grupoVisaoCamposNegocioDTO.setTamanho(0);
                                    }
                                    if (grupoVisaoCamposNegocioDTO.getDecimais() == null) {
                                        grupoVisaoCamposNegocioDTO.setDecimais(0);
                                    }
                                    grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO) grupoVisaoCamposNegocioDao.create(grupoVisaoCamposNegocioDTO);
                                    if (grupoVisaoCamposNegocioDTO.getValoresOpcoes() != null) {
                                        for (int i = 0; i < grupoVisaoCamposNegocioDTO.getValoresOpcoes().length; i++) {
                                            String str = grupoVisaoCamposNegocioDTO.getValoresOpcoes()[i];
                                            String str2[] = str.split("#");

                                            if (str2 != null && str2.length > 0) {
                                                ValorVisaoCamposNegocioDTO valorVisaoCamposNegocioDTO = new ValorVisaoCamposNegocioDTO();
                                                valorVisaoCamposNegocioDTO.setIdGrupoVisao(grupoVisaoDTO.getIdGrupoVisao());
                                                valorVisaoCamposNegocioDTO.setIdCamposObjetoNegocio(grupoVisaoCamposNegocioDTO.getIdCamposObjetoNegocio());
                                                valorVisaoCamposNegocioDTO.setSituacao("A");
                                                valorVisaoCamposNegocioDTO.setValor(str2[0]);
                                                if (str2.length > 1) {
                                                    valorVisaoCamposNegocioDTO.setDescricao(str2[1]);
                                                } else {
                                                    valorVisaoCamposNegocioDTO.setDescricao(" ");
                                                }
                                                valorVisaoCamposNegocioDao.create(valorVisaoCamposNegocioDTO);
                                            }
                                        }
                                    }
                                    if (grupoVisaoCamposNegocioDTO.getTipoNegocio().equalsIgnoreCase(MetaUtil.RELATION)) {
                                        GrupoVisaoCamposNegocioLigacaoDTO grupoVisaoCamposNegocioLigacaoDTOValue = new GrupoVisaoCamposNegocioLigacaoDTO();
                                        grupoVisaoCamposNegocioLigacaoDTOValue.setIdGrupoVisao(grupoVisaoDTO.getIdGrupoVisao());
                                        grupoVisaoCamposNegocioLigacaoDTOValue.setIdCamposObjetoNegocio(grupoVisaoCamposNegocioDTO.getIdCamposObjetoNegocio());
                                        grupoVisaoCamposNegocioLigacaoDTOValue.setIdCamposObjetoNegocioLigacao(grupoVisaoCamposNegocioDTO.getIdCamposObjetoNegocioLigacaoVinc());
                                        grupoVisaoCamposNegocioLigacaoDTOValue.setDescricao(grupoVisaoCamposNegocioDTO.getDescricaoRelacionamento());
                                        grupoVisaoCamposNegocioLigacaoDTOValue.setTipoLigacao(GrupoVisaoCamposNegocioLigacaoDTO.VALUE);
                                        grupoVisaoCamposNegocioLigacaoDao.create(grupoVisaoCamposNegocioLigacaoDTOValue);

                                        GrupoVisaoCamposNegocioLigacaoDTO grupoVisaoCamposNegocioLigacaoDTOApres = new GrupoVisaoCamposNegocioLigacaoDTO();
                                        grupoVisaoCamposNegocioLigacaoDTOApres.setIdGrupoVisao(grupoVisaoDTO.getIdGrupoVisao());
                                        grupoVisaoCamposNegocioLigacaoDTOApres.setIdCamposObjetoNegocio(grupoVisaoCamposNegocioDTO.getIdCamposObjetoNegocio());
                                        grupoVisaoCamposNegocioLigacaoDTOApres.setIdCamposObjetoNegocioLigacao(grupoVisaoCamposNegocioDTO.getIdCamposObjetoNegocioLigacao());
                                        grupoVisaoCamposNegocioLigacaoDTOApres.setDescricao(grupoVisaoCamposNegocioDTO.getDescricaoRelacionamento());
                                        grupoVisaoCamposNegocioLigacaoDTOApres.setTipoLigacao(GrupoVisaoCamposNegocioLigacaoDTO.PRESENTATION);
                                        grupoVisaoCamposNegocioLigacaoDao.create(grupoVisaoCamposNegocioLigacaoDTOApres);

                                        GrupoVisaoCamposNegocioLigacaoDTO grupoVisaoCamposNegocioLigacaoDTOFilter = new GrupoVisaoCamposNegocioLigacaoDTO();
                                        grupoVisaoCamposNegocioLigacaoDTOFilter.setIdGrupoVisao(grupoVisaoDTO.getIdGrupoVisao());
                                        grupoVisaoCamposNegocioLigacaoDTOFilter.setIdCamposObjetoNegocio(grupoVisaoCamposNegocioDTO.getIdCamposObjetoNegocio());
                                        grupoVisaoCamposNegocioLigacaoDTOFilter.setIdCamposObjetoNegocioLigacao(grupoVisaoCamposNegocioDTO.getIdCamposObjetoNegocioLigacao());
                                        grupoVisaoCamposNegocioLigacaoDTOFilter.setDescricao(grupoVisaoCamposNegocioDTO.getDescricaoRelacionamento());
                                        grupoVisaoCamposNegocioLigacaoDTOFilter.setTipoLigacao(GrupoVisaoCamposNegocioLigacaoDTO.FILTER);
                                        if (grupoVisaoCamposNegocioDTO.getFiltro() == null || grupoVisaoCamposNegocioDTO.getFiltro().trim().equalsIgnoreCase("")) {
                                            grupoVisaoCamposNegocioLigacaoDTOFilter.setFiltro("${TERMO_PESQUISA}");
                                        } else {
                                            grupoVisaoCamposNegocioLigacaoDTOFilter.setFiltro(grupoVisaoCamposNegocioDTO.getFiltro());
                                        }
                                        grupoVisaoCamposNegocioLigacaoDao.create(grupoVisaoCamposNegocioLigacaoDTOFilter);

                                        if (grupoVisaoCamposNegocioDTO.getIdCamposObjetoNegocioLigacaoOrder() != null) {
                                            GrupoVisaoCamposNegocioLigacaoDTO grupoVisaoCamposNegocioLigacaoDTOOrder = new GrupoVisaoCamposNegocioLigacaoDTO();
                                            grupoVisaoCamposNegocioLigacaoDTOOrder.setIdGrupoVisao(grupoVisaoDTO.getIdGrupoVisao());
                                            grupoVisaoCamposNegocioLigacaoDTOOrder.setIdCamposObjetoNegocio(grupoVisaoCamposNegocioDTO.getIdCamposObjetoNegocio());
                                            grupoVisaoCamposNegocioLigacaoDTOOrder.setIdCamposObjetoNegocioLigacao(grupoVisaoCamposNegocioDTO
                                                    .getIdCamposObjetoNegocioLigacaoOrder());
                                            grupoVisaoCamposNegocioLigacaoDTOOrder.setDescricao(grupoVisaoCamposNegocioDTO.getDescricaoRelacionamento());
                                            grupoVisaoCamposNegocioLigacaoDTOOrder.setTipoLigacao(GrupoVisaoCamposNegocioLigacaoDTO.ORDER);
                                            grupoVisaoCamposNegocioLigacaoDao.create(grupoVisaoCamposNegocioLigacaoDTOOrder);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (visaoDTO.getColVisoesRelacionadas() != null) {
                    for (Iterator itColVisRel = visaoDTO.getColVisoesRelacionadas().iterator(); itColVisRel.hasNext();) {
                        VisaoRelacionadaDTO visaoRelacionadaDTO = (VisaoRelacionadaDTO) itColVisRel.next();
                        visaoRelacionadaDTO.setIdVisaoPai(visaoDTO.getIdVisao());
                        visaoRelacionadaDTO = (VisaoRelacionadaDTO) visaoRelacionadaDao.create(visaoRelacionadaDTO);
                        if (visaoRelacionadaDTO.getColVinculosVisao() != null) {
                            int x = 0;
                            for (Iterator it = visaoRelacionadaDTO.getColVinculosVisao().iterator(); it.hasNext();) {
                                VinculoVisaoDTO vinculoVisaoDTO = (VinculoVisaoDTO) it.next();
                                if (vinculoVisaoDTO.getControle() != null && vinculoVisaoDTO.getControle().equalsIgnoreCase("P")) {
                                    Collection col1 = grupoVisaoDao.findByIdVisaoAtivos(visaoDTO.getIdVisao());
                                    if (col1 != null && col1.size() > 0) {
                                        GrupoVisaoDTO grupoVisaoDTO = (GrupoVisaoDTO) ((List) (col1)).get(0);
                                        vinculoVisaoDTO.setIdGrupoVisaoPai(grupoVisaoDTO.getIdGrupoVisao());
                                    }
                                }
                                if (vinculoVisaoDTO.getControle() != null && vinculoVisaoDTO.getControle().equalsIgnoreCase("F")) {
                                    Collection col1 = grupoVisaoDao.findByIdVisaoAtivos(visaoRelacionadaDTO.getIdVisaoFilha());
                                    if (col1 != null && col1.size() > 0) {
                                        GrupoVisaoDTO grupoVisaoDTO = (GrupoVisaoDTO) ((List) (col1)).get(0);
                                        vinculoVisaoDTO.setIdGrupoVisaoFilho(grupoVisaoDTO.getIdGrupoVisao());
                                    }
                                }
                                vinculoVisaoDTO.setSeq(x);
                                vinculoVisaoDTO.setIdVisaoRelacionada(visaoRelacionadaDTO.getIdVisaoRelacionada());
                                vinculoVisaoDao.create(vinculoVisaoDTO);
                                x++;
                            }
                        }
                    }
                }

                if (visaoDTO.getColScripts() != null) {
                    for (Iterator it = visaoDTO.getColScripts().iterator(); it.hasNext();) {
                        ScriptsVisaoDTO scriptsVisaoDTO = (ScriptsVisaoDTO) it.next();
                        scriptsVisaoDTO.setIdVisao(visaoDTO.getIdVisao());
                        scriptsVisaoDao.create(scriptsVisaoDTO);
                    }
                }

                if (visaoDTO.getColHtmlCode() != null) {
                    for (Iterator it = visaoDTO.getColHtmlCode().iterator(); it.hasNext();) {
                        HtmlCodeVisaoDTO htmlCodeVisaoDTO = (HtmlCodeVisaoDTO) it.next();
                        htmlCodeVisaoDTO.setIdVisao(visaoDTO.getIdVisao());
                        htmlCodeVisaoDao.create(htmlCodeVisaoDTO);
                    }
                }

                if (visaoDTO.getColBotoes() != null) {
                    int i = 0;
                    for (Iterator it = visaoDTO.getColBotoes().iterator(); it.hasNext();) {
                        BotaoAcaoVisaoDTO botaoAcaoVisaoDto = (BotaoAcaoVisaoDTO) it.next();
                        botaoAcaoVisaoDto.setIdVisao(visaoDTO.getIdVisao());
                        botaoAcaoVisaoDto.setOrdem(i);
                        botaoAcaoVisaoDao.create(botaoAcaoVisaoDto);
                        i++;
                    }
                }
            }

            // Faz commit e fecha a transacao.
            tc.commit();
            tc.close();
        } catch (Exception e) {
            this.rollbackTransaction(tc, e);
        }
    }

    public VisaoDTO findByIdentificador(String identificador) throws Exception {
        return getDao().findByIdentificador(identificador);
    }

    @Override
    public void deleteVisao(IDto model) throws Exception {
        // Instancia Objeto controlador de transacao
        VisaoDao crudDao = getDao();

        // Instancia ou obtem os DAOs necessarios.
        GrupoVisaoDao grupoVisaoDao = getGrupoVisaoDao();
        GrupoVisaoCamposNegocioDao grupoVisaoCamposNegocioDao = getGrupoVisaoCamposNegocioDao();
        ValorVisaoCamposNegocioDao valorVisaoCamposNegocioDao = getValorVisaoCamposNegocioDao();
        GrupoVisaoCamposNegocioLigacaoDao grupoVisaoCamposNegocioLigacaoDao = getGrupoVisaoCamposNegocioLigacaoDao();
        VisaoRelacionadaDao visaoRelacionadaDao = getVisaoRelacionadaDao();
        VinculoVisaoDao vinculoVisaoDao = getVinculoVisaoDao();
        ScriptsVisaoDao scriptsVisaoDao = getScriptsVisaoDao();
        BotaoAcaoVisaoDao botaoAcaoVisaoDao = getBotaoAcaoVisaoDao();
        HtmlCodeVisaoDao htmlCodeVisaoDao = getHtmlCodeVisaoDao();
        MatrizVisaoDao matrizVisaoDao = getMatrizVisaoDao();

        TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
        try {
            // Faz validacao, caso exista.
            validaDelete(model);

            // Seta o TransactionController para os DAOs
            crudDao.setTransactionControler(tc);
            grupoVisaoDao.setTransactionControler(tc);
            grupoVisaoCamposNegocioDao.setTransactionControler(tc);
            valorVisaoCamposNegocioDao.setTransactionControler(tc);
            grupoVisaoCamposNegocioLigacaoDao.setTransactionControler(tc);
            visaoRelacionadaDao.setTransactionControler(tc);
            vinculoVisaoDao.setTransactionControler(tc);
            scriptsVisaoDao.setTransactionControler(tc);
            botaoAcaoVisaoDao.setTransactionControler(tc);
            htmlCodeVisaoDao.setTransactionControler(tc);
            matrizVisaoDao.setTransactionControler(tc);

            // Inicia transacao
            tc.start();

            // Executa operacoes pertinentes ao negocio.
            VisaoDTO visaoDTO = (VisaoDTO) model;
            crudDao.delete(model);
            if (visaoDTO != null) {
                if (visaoDTO.getTipoVisao() != null && visaoDTO.getTipoVisao().equalsIgnoreCase(VisaoDTO.MATRIZ)) {
                    matrizVisaoDao.deleteByIdVisao(visaoDTO.getIdVisao());
                }
                vinculoVisaoDao.deleteByIdVisaoPai(visaoDTO.getIdVisao());
                visaoRelacionadaDao.deleteByIdVisaoPai(visaoDTO.getIdVisao());
                valorVisaoCamposNegocioDao.deleteByIdVisao(visaoDTO.getIdVisao());
                grupoVisaoCamposNegocioLigacaoDao.deleteByIdVisao(visaoDTO.getIdVisao());
                grupoVisaoCamposNegocioDao.deleteByIdVisao(visaoDTO.getIdVisao());
                grupoVisaoDao.deleteByIdVisao(visaoDTO.getIdVisao());
                scriptsVisaoDao.deleteByIdVisao(visaoDTO.getIdVisao());
                botaoAcaoVisaoDao.deleteByIdVisao(visaoDTO.getIdVisao());
                htmlCodeVisaoDao.deleteByIdVisao(visaoDTO.getIdVisao());
            }

            // Faz commit e fecha a transacao.
            tc.commit();
            tc.close();
        } catch (Exception e) {
            this.rollbackTransaction(tc, e);
        }
    }

    public void importar(VisaoDTO visaoXML) throws Exception {

        List<GrupoVisaoCamposNegocioDTO> colCampos = new ArrayList<GrupoVisaoCamposNegocioDTO>();

        if (visaoXML.getColGrupos() != null && visaoXML.getColGrupos().size() > 0) {
            GrupoVisaoDTO grupoVisaoDTO = (GrupoVisaoDTO) ((List) (visaoXML.getColGrupos())).get(0);
            colCampos = (List<GrupoVisaoCamposNegocioDTO>) grupoVisaoDTO.getColCamposVisao();
            if (colCampos != null) {
                for (GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO : colCampos) {
                    if (grupoVisaoCamposNegocioDTO.getNomeTabelaDB() != null) {// Restaura id do Grupo Visão
                        List<GrupoVisaoDTO> listGrupoVisaoTemp = (List<GrupoVisaoDTO>) getGrupoVisaoDao().findByIdVisao(visaoXML.getIdVisao());
                        if (listGrupoVisaoTemp != null) {
                            for (GrupoVisaoDTO grupoVisaoDTO2 : listGrupoVisaoTemp) {
                                grupoVisaoCamposNegocioDTO.setIdGrupoVisao(grupoVisaoDTO2.getIdGrupoVisao());
                            }
                        }
                        // Restaura id dos Campos Objeto Negócio
                        Integer idObjetoNegocio = null;
                        List<ObjetoNegocioDTO> colObjNegocio = (List<ObjetoNegocioDTO>) getObjetoNegocioDao().findByNomeTabelaDB(grupoVisaoCamposNegocioDTO.getNomeTabelaDB());
                        if (colObjNegocio != null) {
                            for (ObjetoNegocioDTO objetoNegocioDTO : colObjNegocio) {
                                idObjetoNegocio = objetoNegocioDTO.getIdObjetoNegocio();
                            }
                            if (idObjetoNegocio != null) {
                                grupoVisaoCamposNegocioDTO.setIdObjetoNegocio(idObjetoNegocio);
                            }
                        }
                        if (idObjetoNegocio != null) {
                            List<CamposObjetoNegocioDTO> colCamposObjNeg = (List<CamposObjetoNegocioDTO>) getCamposObjetoNegocioDao().findByIdObjetoNegocioAndNomeDB(
                                    idObjetoNegocio, grupoVisaoCamposNegocioDTO.getNomeDB());
                            if (colCamposObjNeg != null) {
                                for (CamposObjetoNegocioDTO camposObjetoNegocioDTO : colCamposObjNeg) {
                                    grupoVisaoCamposNegocioDTO.setIdCamposObjetoNegocio(camposObjetoNegocioDTO.getIdCamposObjetoNegocio());

                                }
                            }
                        }
                        // Restaura visões de ligação caso exista
                        if (grupoVisaoCamposNegocioDTO.getTipoNegocio().equalsIgnoreCase(MetaUtil.RELATION)) {
                            // Restaura id do objeto negócio ligação
                            List<ObjetoNegocioDTO> listObjNegTemp = (List<ObjetoNegocioDTO>) getObjetoNegocioDao().findByNomeTabelaDB(
                                    grupoVisaoCamposNegocioDTO.getNomeTabelaDBLigacao());
                            if (listObjNegTemp != null) {
                                for (ObjetoNegocioDTO objetoNegocioDTO : listObjNegTemp) {
                                    grupoVisaoCamposNegocioDTO.setIdObjetoNegocioLigacao(objetoNegocioDTO.getIdObjetoNegocio());
                                }
                            }
                            // Restaura id do campo objeto negócio ligação
                            List<CamposObjetoNegocioDTO> listCamposObjNegTemp2 = (List<CamposObjetoNegocioDTO>) getCamposObjetoNegocioDao().findByIdObjetoNegocioAndNomeDB(
                                    grupoVisaoCamposNegocioDTO.getIdObjetoNegocioLigacao(), grupoVisaoCamposNegocioDTO.getNomeDBLigacao());
                            if (listCamposObjNegTemp2 != null) {
                                for (CamposObjetoNegocioDTO camposObjetoNegocioDTO : listCamposObjNegTemp2) {
                                    grupoVisaoCamposNegocioDTO.setIdCamposObjetoNegocioLigacao(camposObjetoNegocioDTO.getIdCamposObjetoNegocio());
                                }
                            }
                            // Restaura id do campo objeto negócio ligação vinculado
                            List<CamposObjetoNegocioDTO> listCamposObjNegTemp3 = (List<CamposObjetoNegocioDTO>) getCamposObjetoNegocioDao().findByIdObjetoNegocioAndNomeDB(
                                    grupoVisaoCamposNegocioDTO.getIdObjetoNegocioLigacao(), grupoVisaoCamposNegocioDTO.getNomeDBLigacaoVinc());
                            if (listCamposObjNegTemp3 != null) {
                                for (CamposObjetoNegocioDTO camposObjetoNegocioDTO : listCamposObjNegTemp3) {
                                    grupoVisaoCamposNegocioDTO.setIdCamposObjetoNegocioLigacaoVinc(camposObjetoNegocioDTO.getIdCamposObjetoNegocio());
                                }
                            }
                            // Restaura id do campo objeto negócio ligação order
                            List<CamposObjetoNegocioDTO> listCamposObjNegTemp4 = (List<CamposObjetoNegocioDTO>) getCamposObjetoNegocioDao().findByIdObjetoNegocioAndNomeDB(
                                    grupoVisaoCamposNegocioDTO.getIdObjetoNegocioLigacao(), grupoVisaoCamposNegocioDTO.getNomeDBLigacaoOrder());
                            if (listCamposObjNegTemp4 != null) {
                                for (CamposObjetoNegocioDTO camposObjetoNegocioDTO : listCamposObjNegTemp4) {
                                    grupoVisaoCamposNegocioDTO.setIdCamposObjetoNegocioLigacaoOrder(camposObjetoNegocioDTO.getIdCamposObjetoNegocio());
                                }
                            }
                        }
                    }
                }
            }

            if (visaoXML.getTipoVisao().equalsIgnoreCase(VisaoDTO.MATRIZ)) {
                // Restaura ObjetoNegocio
                ObjetoNegocioDTO obNeg = new ObjetoNegocioDTO();
                Integer idObjetoNegocio = null;
                List<ObjetoNegocioDTO> colObjNegocio = (List<ObjetoNegocioDTO>) getObjetoNegocioDao().findByNomeTabelaDB(visaoXML.getMatrizVisaoDTO().getNomeTabelaDB());
                if (colObjNegocio != null) {
                    for (ObjetoNegocioDTO objetoNegocioDTO : colObjNegocio) {
                        idObjetoNegocio = objetoNegocioDTO.getIdObjetoNegocio();
                    }
                    visaoXML.getMatrizVisaoDTO().setIdObjetoNegocio(idObjetoNegocio);
                }
                // Restaura CampoObjetoNegocio1
                Integer idCampNeg1 = null;
                List<CamposObjetoNegocioDTO> colCampNeg1 = (List<CamposObjetoNegocioDTO>) getCamposObjetoNegocioDao().findByIdObjetoNegocioAndNomeDB(
                        visaoXML.getMatrizVisaoDTO().getIdObjetoNegocio(), visaoXML.getMatrizVisaoDTO().getNomeDB1());
                if (colCampNeg1 != null) {
                    for (CamposObjetoNegocioDTO camposObjetoNegocioDTO : colCampNeg1) {
                        idCampNeg1 = camposObjetoNegocioDTO.getIdCamposObjetoNegocio();
                    }
                    visaoXML.getMatrizVisaoDTO().setIdCamposObjetoNegocio1(idCampNeg1);
                }
                // Restaura CampoObjetoNegocio2
                Integer idCampNeg2 = null;
                List<CamposObjetoNegocioDTO> colCampNeg2 = (List<CamposObjetoNegocioDTO>) getCamposObjetoNegocioDao().findByIdObjetoNegocioAndNomeDB(
                        visaoXML.getMatrizVisaoDTO().getIdObjetoNegocio(), visaoXML.getMatrizVisaoDTO().getNomeDB2());
                if (colCampNeg2 != null) {
                    for (CamposObjetoNegocioDTO camposObjetoNegocioDTO : colCampNeg2) {
                        idCampNeg2 = camposObjetoNegocioDTO.getIdCamposObjetoNegocio();
                    }
                    visaoXML.getMatrizVisaoDTO().setIdCamposObjetoNegocio2(idCampNeg2);
                }
                // Restaura CampoObjetoNegocio3
                Integer idCampNeg3 = null;
                List<CamposObjetoNegocioDTO> colCampNeg3 = (List<CamposObjetoNegocioDTO>) getCamposObjetoNegocioDao().findByIdObjetoNegocioAndNomeDB(
                        visaoXML.getMatrizVisaoDTO().getIdObjetoNegocio(), visaoXML.getMatrizVisaoDTO().getNomeDB3());
                if (colCampNeg3 != null) {
                    for (CamposObjetoNegocioDTO camposObjetoNegocioDTO : colCampNeg3) {
                        idCampNeg3 = camposObjetoNegocioDTO.getIdCamposObjetoNegocio();
                    }
                    visaoXML.getMatrizVisaoDTO().setIdCamposObjetoNegocio3(idCampNeg3);
                }
            }
        }

        if (visaoXML.getColVisoesRelacionadas() != null && visaoXML.getColVisoesRelacionadas().size() > 0) {
            List<VisaoRelacionadaDTO> listVisaoRelacionada = (List<VisaoRelacionadaDTO>) visaoXML.getColVisoesRelacionadas();
            for (VisaoRelacionadaDTO visaoRelacionadaDTO : listVisaoRelacionada) {
                // Restaura ID da Visão Relacionada Filha
                VisaoDTO visaoDTOFilha = findByIdentificador(visaoRelacionadaDTO.getIdentificacaoVisaoFilha());
                if (visaoDTOFilha != null) {
                    visaoRelacionadaDTO.setIdVisaoFilha(visaoDTOFilha.getIdVisao());
                }

                // Preenche campos do vínculo
                List<VinculoVisaoDTO> listVinculoVisao = (List<VinculoVisaoDTO>) visaoRelacionadaDTO.getColVinculosVisao();
                if (listVinculoVisao != null) {
                    GrupoVisaoDTO grupoVisaoDTO = (GrupoVisaoDTO) ((List) (visaoXML.getColGrupos())).get(0);
                    for (VinculoVisaoDTO vinculoVisaoDTO : listVinculoVisao) {
                        if (vinculoVisaoDTO != null) {
                            vinculoVisaoDTO.setIdGrupoVisaoPai(grupoVisaoDTO.getIdGrupoVisao());
                            if (vinculoVisaoDTO.getIdCamposObjetoNegocioPaiNN() != null) {
                                // Recuperando CampoObjetoNegocio do Pai
                                Integer idObjetoNegocio = null;
                                List<ObjetoNegocioDTO> colObjNegocio = (List<ObjetoNegocioDTO>) getObjetoNegocioDao().findByNomeTabelaDB(vinculoVisaoDTO.getNomeTabelaPai());
                                if (colObjNegocio != null) {
                                    for (ObjetoNegocioDTO objetoNegocioDTO : colObjNegocio) {
                                        idObjetoNegocio = objetoNegocioDTO.getIdObjetoNegocio();
                                    }
                                    List<CamposObjetoNegocioDTO> colCamposObjNeg = (List<CamposObjetoNegocioDTO>) getCamposObjetoNegocioDao().findByIdObjetoNegocioAndNomeDB(
                                            idObjetoNegocio, vinculoVisaoDTO.getNomeDBPai());
                                    if (colCamposObjNeg != null) {
                                        for (CamposObjetoNegocioDTO camposObjetoNegocioDTO : colCamposObjNeg) {
                                            vinculoVisaoDTO.setIdCamposObjetoNegocioPai(camposObjetoNegocioDTO.getIdCamposObjetoNegocio());
                                        }
                                    }
                                }
                                // Recuperando CampoObjetoNegocio do PaiNN
                                colObjNegocio = (List<ObjetoNegocioDTO>) getObjetoNegocioDao().findByNomeTabelaDB(vinculoVisaoDTO.getNomeTabelaPaiNN());
                                if (colObjNegocio != null) {
                                    for (ObjetoNegocioDTO objetoNegocioDTO : colObjNegocio) {
                                        idObjetoNegocio = objetoNegocioDTO.getIdObjetoNegocio();
                                    }
                                    List<CamposObjetoNegocioDTO> colCamposObjNeg = (List<CamposObjetoNegocioDTO>) getCamposObjetoNegocioDao().findByIdObjetoNegocioAndNomeDB(
                                            idObjetoNegocio, vinculoVisaoDTO.getNomeDBPaiNN());
                                    if (colCamposObjNeg != null) {
                                        for (CamposObjetoNegocioDTO camposObjetoNegocioDTO : colCamposObjNeg) {
                                            vinculoVisaoDTO.setIdCamposObjetoNegocioPaiNN(camposObjetoNegocioDTO.getIdCamposObjetoNegocio());
                                        }
                                    }
                                }
                            }

                            if (vinculoVisaoDTO.getIdCamposObjetoNegocioFilhoNN() != null) {
                                // Recuperando CampoObjetoNegocio do Filho
                                Integer idObjetoNegocio = null;
                                List<ObjetoNegocioDTO> colObjNegocio = (List<ObjetoNegocioDTO>) getObjetoNegocioDao().findByNomeTabelaDB(vinculoVisaoDTO.getNomeTabelaFilho());
                                if (colObjNegocio != null) {
                                    for (ObjetoNegocioDTO objetoNegocioDTO : colObjNegocio) {
                                        idObjetoNegocio = objetoNegocioDTO.getIdObjetoNegocio();
                                    }
                                    List<CamposObjetoNegocioDTO> colCamposObjNeg = (List<CamposObjetoNegocioDTO>) getCamposObjetoNegocioDao().findByIdObjetoNegocioAndNomeDB(
                                            idObjetoNegocio, vinculoVisaoDTO.getNomeDBPaiFilho());
                                    if (colCamposObjNeg != null) {
                                        for (CamposObjetoNegocioDTO camposObjetoNegocioDTO : colCamposObjNeg) {
                                            vinculoVisaoDTO.setIdCamposObjetoNegocioFilho(camposObjetoNegocioDTO.getIdCamposObjetoNegocio());
                                        }
                                    }
                                }
                                // Recuperando CampoObjetoNegocio do FilhoNN
                                colObjNegocio = (List<ObjetoNegocioDTO>) getObjetoNegocioDao().findByNomeTabelaDB(vinculoVisaoDTO.getNomeTabelaFilhoNN());
                                if (colObjNegocio != null) {
                                    for (ObjetoNegocioDTO objetoNegocioDTO : colObjNegocio) {
                                        idObjetoNegocio = objetoNegocioDTO.getIdObjetoNegocio();
                                    }
                                    List<CamposObjetoNegocioDTO> colCamposObjNeg = (List<CamposObjetoNegocioDTO>) getCamposObjetoNegocioDao().findByIdObjetoNegocioAndNomeDB(
                                            idObjetoNegocio, vinculoVisaoDTO.getNomeDBPaiFilhoNN());
                                    if (colCamposObjNeg != null) {
                                        for (CamposObjetoNegocioDTO camposObjetoNegocioDTO : colCamposObjNeg) {
                                            vinculoVisaoDTO.setIdCamposObjetoNegocioFilhoNN(camposObjetoNegocioDTO.getIdCamposObjetoNegocio());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (visaoXML != null) {
            create(visaoXML);
        }
    }

    public void atualizarVisao(VisaoDTO visaoAtual, VisaoDTO visaoXML) throws Exception {

        // Primeiro obtem visão atual a ser atualizada
        if (visaoAtual != null && visaoAtual.getIdVisao() != null) {
            visaoXML.setIdVisao(visaoAtual.getIdVisao());
            // Segundo obtem visão vinda do XML
            List<GrupoVisaoCamposNegocioDTO> colCamposVisaoXML = new ArrayList<GrupoVisaoCamposNegocioDTO>();

            if (visaoXML.getColGrupos() != null && visaoXML.getColGrupos().size() > 0) {
                GrupoVisaoDTO grupoVisaoDTO = (GrupoVisaoDTO) ((List) (visaoXML.getColGrupos())).get(0);
                colCamposVisaoXML = (List<GrupoVisaoCamposNegocioDTO>) grupoVisaoDTO.getColCamposVisao();
                if (colCamposVisaoXML != null) {
                    for (GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO : colCamposVisaoXML) {
                        if (grupoVisaoCamposNegocioDTO.getNomeTabelaDB() != null) {
                            // Restaura id do Grupo Visão
                            List<GrupoVisaoDTO> listGrupoVisaoTemp = (List<GrupoVisaoDTO>) getGrupoVisaoDao().findByIdVisao(visaoXML.getIdVisao());
                            if (listGrupoVisaoTemp != null) {
                                for (GrupoVisaoDTO grupoVisaoDTO2 : listGrupoVisaoTemp) {
                                    grupoVisaoCamposNegocioDTO.setIdGrupoVisao(grupoVisaoDTO2.getIdGrupoVisao());
                                }
                            }
                            // Restaura id dos Campos Objeto Negócio
                            Integer idObjetoNegocio = null;
                            List<ObjetoNegocioDTO> colObjNegocio = (List<ObjetoNegocioDTO>) getObjetoNegocioDao().findByNomeTabelaDB(grupoVisaoCamposNegocioDTO.getNomeTabelaDB());
                            if (colObjNegocio != null) {
                                for (ObjetoNegocioDTO objetoNegocioDTO : colObjNegocio) {
                                    idObjetoNegocio = objetoNegocioDTO.getIdObjetoNegocio();
                                }
                                if (idObjetoNegocio != null) {
                                    grupoVisaoCamposNegocioDTO.setIdObjetoNegocio(idObjetoNegocio);
                                }
                            }
                            if (idObjetoNegocio != null) {
                                List<CamposObjetoNegocioDTO> colCamposObjNeg = (List<CamposObjetoNegocioDTO>) getCamposObjetoNegocioDao().findByIdObjetoNegocioAndNomeDB(
                                        idObjetoNegocio, grupoVisaoCamposNegocioDTO.getNomeDB());
                                if (colCamposObjNeg != null) {
                                    for (CamposObjetoNegocioDTO camposObjetoNegocioDTO : colCamposObjNeg) {
                                        grupoVisaoCamposNegocioDTO.setIdCamposObjetoNegocio(camposObjetoNegocioDTO.getIdCamposObjetoNegocio());
                                    }
                                }
                            }
                            // Restaura visões de ligação caso exista
                            if (grupoVisaoCamposNegocioDTO.getTipoNegocio().equalsIgnoreCase(MetaUtil.RELATION)) {
                                // Restaura id do objeto negócio ligação
                                List<ObjetoNegocioDTO> listObjNegTemp = (List<ObjetoNegocioDTO>) getObjetoNegocioDao().findByNomeTabelaDB(
                                        grupoVisaoCamposNegocioDTO.getNomeTabelaDBLigacao());
                                if (listObjNegTemp != null) {
                                    for (ObjetoNegocioDTO objetoNegocioDTO : listObjNegTemp) {
                                        grupoVisaoCamposNegocioDTO.setIdObjetoNegocioLigacao(objetoNegocioDTO.getIdObjetoNegocio());
                                    }
                                }
                                // Restaura id do campo objeto negócio ligação
                                List<CamposObjetoNegocioDTO> listCamposObjNegTemp2 = (List<CamposObjetoNegocioDTO>) getCamposObjetoNegocioDao().findByIdObjetoNegocioAndNomeDB(
                                        grupoVisaoCamposNegocioDTO.getIdObjetoNegocioLigacao(), grupoVisaoCamposNegocioDTO.getNomeDBLigacao());
                                if (listCamposObjNegTemp2 != null) {
                                    for (CamposObjetoNegocioDTO camposObjetoNegocioDTO : listCamposObjNegTemp2) {
                                        grupoVisaoCamposNegocioDTO.setIdCamposObjetoNegocioLigacao(camposObjetoNegocioDTO.getIdCamposObjetoNegocio());
                                    }
                                }
                                // Restaura id do campo objeto negócio ligação vinculado
                                List<CamposObjetoNegocioDTO> listCamposObjNegTemp3 = (List<CamposObjetoNegocioDTO>) getCamposObjetoNegocioDao().findByIdObjetoNegocioAndNomeDB(
                                        grupoVisaoCamposNegocioDTO.getIdObjetoNegocioLigacao(), grupoVisaoCamposNegocioDTO.getNomeDBLigacaoVinc());
                                if (listCamposObjNegTemp3 != null) {
                                    for (CamposObjetoNegocioDTO camposObjetoNegocioDTO : listCamposObjNegTemp3) {
                                        grupoVisaoCamposNegocioDTO.setIdCamposObjetoNegocioLigacaoVinc(camposObjetoNegocioDTO.getIdCamposObjetoNegocio());
                                    }
                                }
                                // Restaura id do campo objeto negócio ligação order
                                List<CamposObjetoNegocioDTO> listCamposObjNegTemp4 = (List<CamposObjetoNegocioDTO>) getCamposObjetoNegocioDao().findByIdObjetoNegocioAndNomeDB(
                                        grupoVisaoCamposNegocioDTO.getIdObjetoNegocioLigacao(), grupoVisaoCamposNegocioDTO.getNomeDBLigacaoOrder());
                                if (listCamposObjNegTemp4 != null) {
                                    for (CamposObjetoNegocioDTO camposObjetoNegocioDTO : listCamposObjNegTemp4) {
                                        grupoVisaoCamposNegocioDTO.setIdCamposObjetoNegocioLigacaoOrder(camposObjetoNegocioDTO.getIdCamposObjetoNegocio());
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (visaoXML.getTipoVisao().equalsIgnoreCase(VisaoDTO.MATRIZ)) {
                // Restaura ObjetoNegocio
                ObjetoNegocioDTO obNeg = new ObjetoNegocioDTO();
                Integer idObjetoNegocio = null;
                List<ObjetoNegocioDTO> colObjNegocio = (List<ObjetoNegocioDTO>) getObjetoNegocioDao().findByNomeTabelaDB(visaoXML.getMatrizVisaoDTO().getNomeTabelaDB());
                if (colObjNegocio != null) {
                    for (ObjetoNegocioDTO objetoNegocioDTO : colObjNegocio) {
                        idObjetoNegocio = objetoNegocioDTO.getIdObjetoNegocio();
                    }
                    visaoXML.getMatrizVisaoDTO().setIdObjetoNegocio(idObjetoNegocio);
                }
                // Restaura CampoObjetoNegocio1
                Integer idCampNeg1 = null;
                List<CamposObjetoNegocioDTO> colCampNeg1 = (List<CamposObjetoNegocioDTO>) getCamposObjetoNegocioDao().findByIdObjetoNegocioAndNomeDB(
                        visaoXML.getMatrizVisaoDTO().getIdObjetoNegocio(), visaoXML.getMatrizVisaoDTO().getNomeDB1());
                if (colCampNeg1 != null) {
                    for (CamposObjetoNegocioDTO camposObjetoNegocioDTO : colCampNeg1) {
                        idCampNeg1 = camposObjetoNegocioDTO.getIdCamposObjetoNegocio();
                    }
                    visaoXML.getMatrizVisaoDTO().setIdCamposObjetoNegocio1(idCampNeg1);
                }
                // Restaura CampoObjetoNegocio2
                Integer idCampNeg2 = null;
                List<CamposObjetoNegocioDTO> colCampNeg2 = (List<CamposObjetoNegocioDTO>) getCamposObjetoNegocioDao().findByIdObjetoNegocioAndNomeDB(
                        visaoXML.getMatrizVisaoDTO().getIdObjetoNegocio(), visaoXML.getMatrizVisaoDTO().getNomeDB2());
                if (colCampNeg2 != null) {
                    for (CamposObjetoNegocioDTO camposObjetoNegocioDTO : colCampNeg2) {
                        idCampNeg2 = camposObjetoNegocioDTO.getIdCamposObjetoNegocio();
                    }
                    visaoXML.getMatrizVisaoDTO().setIdCamposObjetoNegocio2(idCampNeg2);
                }
                // Restaura CampoObjetoNegocio3
                Integer idCampNeg3 = null;
                List<CamposObjetoNegocioDTO> colCampNeg3 = (List<CamposObjetoNegocioDTO>) getCamposObjetoNegocioDao().findByIdObjetoNegocioAndNomeDB(
                        visaoXML.getMatrizVisaoDTO().getIdObjetoNegocio(), visaoXML.getMatrizVisaoDTO().getNomeDB3());
                if (colCampNeg3 != null) {
                    for (CamposObjetoNegocioDTO camposObjetoNegocioDTO : colCampNeg3) {
                        idCampNeg3 = camposObjetoNegocioDTO.getIdCamposObjetoNegocio();
                    }
                    visaoXML.getMatrizVisaoDTO().setIdCamposObjetoNegocio3(idCampNeg3);
                }
            }

            if (visaoXML.getColVisoesRelacionadas() != null && visaoXML.getColVisoesRelacionadas().size() > 0) {
                List<VisaoRelacionadaDTO> listVisaoRelacionada = (List<VisaoRelacionadaDTO>) visaoXML.getColVisoesRelacionadas();
                for (VisaoRelacionadaDTO visaoRelacionadaDTO : listVisaoRelacionada) {
                    // Restaura ID da Visão Relacionada Filha
                    VisaoDTO visaoDTOFilha = findByIdentificador(visaoRelacionadaDTO.getIdentificacaoVisaoFilha());
                    if (visaoDTOFilha != null) {
                        visaoRelacionadaDTO.setIdVisaoFilha(visaoDTOFilha.getIdVisao());
                    }

                    // Preenche campos do vínculo
                    List<VinculoVisaoDTO> listVinculoVisao = (List<VinculoVisaoDTO>) visaoRelacionadaDTO.getColVinculosVisao();
                    if (listVinculoVisao != null) {
                        GrupoVisaoDTO grupoVisaoDTO = (GrupoVisaoDTO) ((List) (visaoXML.getColGrupos())).get(0);
                        for (VinculoVisaoDTO vinculoVisaoDTO : listVinculoVisao) {
                            if (vinculoVisaoDTO != null) {
                                vinculoVisaoDTO.setIdGrupoVisaoPai(grupoVisaoDTO.getIdGrupoVisao());
                                if (vinculoVisaoDTO.getIdCamposObjetoNegocioPaiNN() != null) {
                                    // Recuperando CampoObjetoNegocio do Pai
                                    Integer idObjetoNegocio = null;
                                    List<ObjetoNegocioDTO> colObjNegocio = (List<ObjetoNegocioDTO>) getObjetoNegocioDao().findByNomeTabelaDB(vinculoVisaoDTO.getNomeTabelaPai());
                                    if (colObjNegocio != null) {
                                        for (ObjetoNegocioDTO objetoNegocioDTO : colObjNegocio) {
                                            idObjetoNegocio = objetoNegocioDTO.getIdObjetoNegocio();
                                        }
                                        List<CamposObjetoNegocioDTO> colCamposObjNeg = (List<CamposObjetoNegocioDTO>) getCamposObjetoNegocioDao().findByIdObjetoNegocioAndNomeDB(
                                                idObjetoNegocio, vinculoVisaoDTO.getNomeDBPai());
                                        if (colCamposObjNeg != null) {
                                            for (CamposObjetoNegocioDTO camposObjetoNegocioDTO : colCamposObjNeg) {
                                                vinculoVisaoDTO.setIdCamposObjetoNegocioPai(camposObjetoNegocioDTO.getIdCamposObjetoNegocio());
                                            }
                                        }
                                    }
                                    // Recuperando CampoObjetoNegocio do PaiNN
                                    colObjNegocio = (List<ObjetoNegocioDTO>) getObjetoNegocioDao().findByNomeTabelaDB(vinculoVisaoDTO.getNomeTabelaPaiNN());
                                    if (colObjNegocio != null) {
                                        for (ObjetoNegocioDTO objetoNegocioDTO : colObjNegocio) {
                                            idObjetoNegocio = objetoNegocioDTO.getIdObjetoNegocio();
                                        }
                                        List<CamposObjetoNegocioDTO> colCamposObjNeg = (List<CamposObjetoNegocioDTO>) getCamposObjetoNegocioDao().findByIdObjetoNegocioAndNomeDB(
                                                idObjetoNegocio, vinculoVisaoDTO.getNomeDBPaiNN());
                                        if (colCamposObjNeg != null) {
                                            for (CamposObjetoNegocioDTO camposObjetoNegocioDTO : colCamposObjNeg) {
                                                vinculoVisaoDTO.setIdCamposObjetoNegocioPaiNN(camposObjetoNegocioDTO.getIdCamposObjetoNegocio());
                                            }
                                        }
                                    }
                                }

                                if (vinculoVisaoDTO.getIdCamposObjetoNegocioFilhoNN() != null) {
                                    // Recuperando CampoObjetoNegocio do Filho
                                    Integer idObjetoNegocio = null;
                                    List<ObjetoNegocioDTO> colObjNegocio = (List<ObjetoNegocioDTO>) getObjetoNegocioDao().findByNomeTabelaDB(vinculoVisaoDTO.getNomeTabelaFilho());
                                    if (colObjNegocio != null) {
                                        for (ObjetoNegocioDTO objetoNegocioDTO : colObjNegocio) {
                                            idObjetoNegocio = objetoNegocioDTO.getIdObjetoNegocio();
                                        }
                                        List<CamposObjetoNegocioDTO> colCamposObjNeg = (List<CamposObjetoNegocioDTO>) getCamposObjetoNegocioDao().findByIdObjetoNegocioAndNomeDB(
                                                idObjetoNegocio, vinculoVisaoDTO.getNomeDBPaiFilho());
                                        if (colCamposObjNeg != null) {
                                            for (CamposObjetoNegocioDTO camposObjetoNegocioDTO : colCamposObjNeg) {
                                                vinculoVisaoDTO.setIdCamposObjetoNegocioFilho(camposObjetoNegocioDTO.getIdCamposObjetoNegocio());
                                            }
                                        }
                                    }
                                    // Recuperando CampoObjetoNegocio do FilhoNN
                                    colObjNegocio = (List<ObjetoNegocioDTO>) getObjetoNegocioDao().findByNomeTabelaDB(vinculoVisaoDTO.getNomeTabelaFilhoNN());
                                    if (colObjNegocio != null) {
                                        for (ObjetoNegocioDTO objetoNegocioDTO : colObjNegocio) {
                                            idObjetoNegocio = objetoNegocioDTO.getIdObjetoNegocio();
                                        }
                                        List<CamposObjetoNegocioDTO> colCamposObjNeg = (List<CamposObjetoNegocioDTO>) getCamposObjetoNegocioDao().findByIdObjetoNegocioAndNomeDB(
                                                idObjetoNegocio, vinculoVisaoDTO.getNomeDBPaiFilhoNN());
                                        if (colCamposObjNeg != null) {
                                            for (CamposObjetoNegocioDTO camposObjetoNegocioDTO : colCamposObjNeg) {
                                                vinculoVisaoDTO.setIdCamposObjetoNegocioFilhoNN(camposObjetoNegocioDTO.getIdCamposObjetoNegocio());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }

            // Salva alterações
            if (visaoXML != null) {
                update(visaoXML);
            }

        } else {
            throw new Exception();
        }

    }

    public VisaoDTO visaoExistente(String identificadorVisao) throws ServiceException, Exception {
        VisaoDTO visaoDto = null;
        visaoDto = findByIdentificador(identificadorVisao);
        if (visaoDto != null) {
            return visaoDto;
        } else {
            return null;
        }
    }

    private BotaoAcaoVisaoDao botaoAcaoVisaoDAO;

    private BotaoAcaoVisaoDao getBotaoAcaoVisaoDao() {
        if (botaoAcaoVisaoDAO == null) {
            botaoAcaoVisaoDAO = new BotaoAcaoVisaoDao();
        }
        return botaoAcaoVisaoDAO;
    }

    private CamposObjetoNegocioDao camposObjetoNegocioDAO;

    private CamposObjetoNegocioDao getCamposObjetoNegocioDao() {
        if (camposObjetoNegocioDAO == null) {
            camposObjetoNegocioDAO = new CamposObjetoNegocioDao();
        }
        return camposObjetoNegocioDAO;
    }

    private GrupoVisaoDao grupoVisaoDAO;

    private GrupoVisaoDao getGrupoVisaoDao() {
        if (grupoVisaoDAO == null) {
            grupoVisaoDAO = new GrupoVisaoDao();
        }
        return grupoVisaoDAO;
    }

    private GrupoVisaoCamposNegocioDao grupoVisaoCamposNegocioDAO;

    private GrupoVisaoCamposNegocioDao getGrupoVisaoCamposNegocioDao() {
        if (grupoVisaoCamposNegocioDAO == null) {
            grupoVisaoCamposNegocioDAO = new GrupoVisaoCamposNegocioDao();
        }
        return grupoVisaoCamposNegocioDAO;
    }

    private GrupoVisaoCamposNegocioLigacaoDao grupoVisaoCamposNegocioLigacaoDAO;

    private GrupoVisaoCamposNegocioLigacaoDao getGrupoVisaoCamposNegocioLigacaoDao() {
        if (grupoVisaoCamposNegocioLigacaoDAO == null) {
            grupoVisaoCamposNegocioLigacaoDAO = new GrupoVisaoCamposNegocioLigacaoDao();
        }
        return grupoVisaoCamposNegocioLigacaoDAO;
    }

    private HtmlCodeVisaoDao htmlCodeVisaoDAO;

    private HtmlCodeVisaoDao getHtmlCodeVisaoDao() {
        if (htmlCodeVisaoDAO == null) {
            htmlCodeVisaoDAO = new HtmlCodeVisaoDao();
        }
        return htmlCodeVisaoDAO;
    }

    private MatrizVisaoDao matrizVisaoDAO;

    private MatrizVisaoDao getMatrizVisaoDao() {
        if (matrizVisaoDAO == null) {
            matrizVisaoDAO = new MatrizVisaoDao();
        }
        return matrizVisaoDAO;
    }

    private ObjetoNegocioDao objetoNegocioDAO;

    private ObjetoNegocioDao getObjetoNegocioDao() {
        if (objetoNegocioDAO == null) {
            objetoNegocioDAO = new ObjetoNegocioDao();
        }
        return objetoNegocioDAO;
    }

    private ScriptsVisaoDao scriptsVisaoDAO;

    private ScriptsVisaoDao getScriptsVisaoDao() {
        if (scriptsVisaoDAO == null) {
            scriptsVisaoDAO = new ScriptsVisaoDao();
        }
        return scriptsVisaoDAO;
    }

    private ValorVisaoCamposNegocioDao valorVisaoCamposNegocioDAO;

    private ValorVisaoCamposNegocioDao getValorVisaoCamposNegocioDao() {
        if (valorVisaoCamposNegocioDAO == null) {
            valorVisaoCamposNegocioDAO = new ValorVisaoCamposNegocioDao();
        }
        return valorVisaoCamposNegocioDAO;
    }

    private VinculoVisaoDao vinculoVisaoDAO;

    private VinculoVisaoDao getVinculoVisaoDao() {
        if (vinculoVisaoDAO == null) {
            vinculoVisaoDAO = new VinculoVisaoDao();
        }
        return vinculoVisaoDAO;
    }

    private VisaoRelacionadaDao visaoRelacionadaDAO;

    private VisaoRelacionadaDao getVisaoRelacionadaDao() {
        if (visaoRelacionadaDAO == null) {
            visaoRelacionadaDAO = new VisaoRelacionadaDao();
        }
        return visaoRelacionadaDAO;
    }

}
