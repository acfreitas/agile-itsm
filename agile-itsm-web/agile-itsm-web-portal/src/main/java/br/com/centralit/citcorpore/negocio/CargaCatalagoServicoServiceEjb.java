package br.com.centralit.citcorpore.negocio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import br.com.centralit.citcorpore.bean.CargaCatalagoServicoDTO;
import br.com.centralit.citcorpore.bean.CategoriaServicoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.bean.TipoDemandaServicoDTO;
import br.com.centralit.citcorpore.bean.TipoEventoServicoDTO;
import br.com.centralit.citcorpore.bean.TipoServicoDTO;
import br.com.centralit.citcorpore.integracao.CategoriaServicoDao;
import br.com.centralit.citcorpore.integracao.ServicoDao;
import br.com.centralit.citcorpore.integracao.TipoDemandaServicoDao;
import br.com.centralit.citcorpore.integracao.TipoEventoServicoDao;
import br.com.centralit.citcorpore.integracao.TipoServicoDao;
import br.com.centralit.citcorpore.util.Util;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;

public class CargaCatalagoServicoServiceEjb extends CrudServiceImpl implements CargaCatalagoServicoService {

    private ServicoDao dao;

    @Override
    protected ServicoDao getDao() {
        if (dao == null) {
            dao = new ServicoDao();
        }
        return dao;
    }

    @Override
    public List<CargaCatalagoServicoDTO> gerarCarga(final String[] carga) throws ServiceException, Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<CargaCatalagoServicoDTO> gerarCarga(final File carga, final Integer idEmpresa) throws ServiceException, Exception {
        System.out.println("Iniciando carga de Catalogo de Servicos...");
        // Daos das Transação
        final CategoriaServicoDao categoriaServicoDao = new CategoriaServicoDao();
        final TipoServicoDao tipoServicoDao = new TipoServicoDao();
        final TipoDemandaServicoDao tipoDemandaServicoDao = new TipoDemandaServicoDao();
        final TipoEventoServicoDao tipoEventoServicoDao = new TipoEventoServicoDao();

        new ServicoDTO();
        CategoriaServicoDTO categoriaServicoDTO = new CategoriaServicoDTO();
        TipoServicoDTO tipoServicoDTO = new TipoServicoDTO();
        TipoDemandaServicoDTO tipoDemandaServicoDTO = new TipoDemandaServicoDTO();
        TipoEventoServicoDTO tipoEventoServicoDTO = new TipoEventoServicoDTO();
        final TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());

        try {

            this.getDao().setTransactionControler(tc);
            categoriaServicoDao.setTransactionControler(tc);
            tipoServicoDao.setTransactionControler(tc);
            tipoDemandaServicoDao.setTransactionControler(tc);
            tipoEventoServicoDao.setTransactionControler(tc);

            tc.start();

            BufferedReader arq = new BufferedReader(new InputStreamReader(new FileInputStream(carga), "WINDOWS-1252"));

            // Scanner arq = new Scanner(carga);
            boolean primeiraLinha = true;
            final int qtdNivel = 5;
            String nomeServico = "";
            int status = 0;

            while (arq.ready()) {
                final String linhaAux = arq.readLine();
                String linha = null;
                if (linhaAux != null) {
                    linha = new String(linhaAux);
                    linha = linha.trim();
                }
                // System.out.println("Carga de Catalogo de Servicos... Linha: " + linha);
                nomeServico = "";
                ServicoDTO beanServicoDTO = null;

                String[] linhaQuebrada = null;
                if (linha != null) {
                    linhaQuebrada = linha.split("\n");
                }
                if (linhaQuebrada != null && linhaQuebrada.length > 0) {
                    for (final String string : linhaQuebrada) {
                        final String[] colunasArray = string.split(";");

                        // Verifica se já inicio dos dados
                        if (colunasArray.length > 24) {
                            System.out.println("Linha OK... " + linha);
                            if (primeiraLinha) {
                                primeiraLinha = false;
                                break;
                            }

                            int j = 0;
                            int nivel = 0;
                            for (final String coluna : colunasArray) {
                                CategoriaServicoDTO beanCategoriaServicoDTO = null;
                                TipoServicoDTO beanTipoServicoDTO = null;
                                TipoDemandaServicoDTO beanTipoDemandaServicoDTO = null;
                                TipoEventoServicoDTO beanTipoEventoServicoDTO = null;
                                if (j < qtdNivel) {
                                    beanCategoriaServicoDTO = new CategoriaServicoDTO();
                                    if (coluna != null && coluna.length() > 0) {
                                        beanCategoriaServicoDTO = this.existeCategoriaPorNome(coluna.trim(), categoriaServicoDao);
                                        beanCategoriaServicoDTO.setIdEmpresa(idEmpresa);

                                        if (beanCategoriaServicoDTO.getIdCategoriaServico() == null) {
                                            if (nivel > 0) {
                                                beanCategoriaServicoDTO.setNomeCategoriaServico(coluna.trim());
                                                beanCategoriaServicoDTO.setIdCategoriaServicoPai(categoriaServicoDTO.getIdCategoriaServico());
                                                categoriaServicoDTO = this.criaCategoriaServicoDTO(beanCategoriaServicoDTO, categoriaServicoDao);

                                                System.out.println("Criando categoria de servicos...(2) " + beanCategoriaServicoDTO.getNomeCategoriaServicoConcatenado());
                                            } else {
                                                beanCategoriaServicoDTO.setNomeCategoriaServico(coluna);
                                                categoriaServicoDTO = this.criaCategoriaServicoDTO(beanCategoriaServicoDTO, categoriaServicoDao);

                                                System.out.println("Criando categoria de servicos...(1) " + beanCategoriaServicoDTO.getNomeCategoriaServicoConcatenado());
                                            }
                                            nivel++;
                                        } else {
                                            if (nivel > 0) {
                                                final List<CategoriaServicoDTO> listCatalagoServico = this.listCategoriasServicoidPaiFilho(categoriaServicoDTO,
                                                        beanCategoriaServicoDTO, categoriaServicoDao);

                                                if (listCatalagoServico != null) {
                                                    categoriaServicoDTO = listCatalagoServico.get(0);
                                                } else {
                                                    beanCategoriaServicoDTO.setNomeCategoriaServico(coluna);
                                                    beanCategoriaServicoDTO.setIdCategoriaServicoPai(categoriaServicoDTO.getIdCategoriaServico());
                                                    categoriaServicoDTO = this.criaCategoriaServicoDTO(beanCategoriaServicoDTO, categoriaServicoDao);

                                                    System.out.println("Criando categoria de servicos...(4) " + beanCategoriaServicoDTO.getNomeCategoriaServicoConcatenado());
                                                }
                                            } else {
                                                categoriaServicoDTO = beanCategoriaServicoDTO;
                                                final List<CategoriaServicoDTO> listCatalagoServico = this.listCategoriasServicoidPaiIsNull(beanCategoriaServicoDTO,
                                                        categoriaServicoDao);

                                                if (listCatalagoServico != null) {
                                                    categoriaServicoDTO = listCatalagoServico.get(0);
                                                } else {
                                                    beanCategoriaServicoDTO = new CategoriaServicoDTO();
                                                    beanCategoriaServicoDTO.setNomeCategoriaServico(coluna);
                                                    beanCategoriaServicoDTO.setIdEmpresa(idEmpresa);

                                                    categoriaServicoDTO = this.criaCategoriaServicoDTO(beanCategoriaServicoDTO, categoriaServicoDao);
                                                    System.out.println("Criando categoria de servicos...(3) " + beanCategoriaServicoDTO.getNomeCategoriaServicoConcatenado());
                                                }
                                            }
                                            nivel++;
                                        }
                                    }
                                }

                                if (j == 5) {
                                    nomeServico = coluna;
                                }

                                if (j == 6) {
                                    beanTipoServicoDTO = new TipoServicoDTO();
                                    beanTipoServicoDTO = this.existeTipoServicoPorNome(coluna, tipoServicoDao);
                                    if (beanTipoServicoDTO.getIdTipoServico() == null) {
                                        beanTipoServicoDTO.setIdEmpresa(idEmpresa);
                                        beanTipoServicoDTO.setIdTipoServico(tipoServicoDTO.getIdTipoServico());
                                        beanTipoServicoDTO.setNomeTipoServico(coluna);
                                        beanTipoServicoDTO.setSituacao("A");
                                        // System.out.println("Criando tipo de servicos... " + beanTipoServicoDTO.getNomeTipoServico());
                                        tipoServicoDTO = this.criaTipoServicoDTO(beanTipoServicoDTO, tipoServicoDao);
                                    } else {
                                        tipoServicoDTO = beanTipoServicoDTO;
                                    }
                                }

                                if (j == 7) {
                                    if (coluna.equalsIgnoreCase("Ativo")) {
                                        status = 1;
                                    } else if (coluna.equalsIgnoreCase("Inativo")) {
                                        status = 2;
                                    } else {
                                        status = 1;
                                    }
                                }

                                if (j == 8) {
                                    beanTipoDemandaServicoDTO = new TipoDemandaServicoDTO();
                                    beanTipoDemandaServicoDTO.setNomeTipoDemandaServico(coluna);

                                    if (coluna.equalsIgnoreCase("Incidente")) {
                                        beanTipoDemandaServicoDTO.setClassificacao("I");
                                    } else if (coluna.equalsIgnoreCase("Solicitação")) {
                                        beanTipoDemandaServicoDTO.setClassificacao("R");
                                        beanTipoDemandaServicoDTO.setNomeTipoDemandaServico("Requisição");
                                    } else if (coluna.equalsIgnoreCase("Requisição")) {
                                        beanTipoDemandaServicoDTO.setClassificacao("R");
                                    } else if (coluna.toLowerCase().contains("Ordem de Serviço".toLowerCase())) {
                                        beanTipoDemandaServicoDTO.setClassificacao("O");
                                    }
                                    beanTipoDemandaServicoDTO = this.existeTipoDemandaPorNome(beanTipoDemandaServicoDTO.getNomeTipoDemandaServico(), tipoDemandaServicoDao);
                                    if (beanTipoDemandaServicoDTO.getIdTipoDemandaServico() == null) {

                                        /* tipoDemandaDTO.setIdTipoDemanda(tipoServicoDTO.getIdTipoServico()); */
                                        if (coluna.equalsIgnoreCase("Incidente")) {
                                            beanTipoDemandaServicoDTO.setClassificacao("I");
                                        } else if (coluna.equalsIgnoreCase("Solicitação")) {
                                            beanTipoDemandaServicoDTO.setClassificacao("R");
                                            beanTipoDemandaServicoDTO.setNomeTipoDemandaServico("Requisição");
                                        } else if (coluna.equalsIgnoreCase("Requisição")) {
                                            beanTipoDemandaServicoDTO.setClassificacao("R");
                                        } else if (coluna.toLowerCase().contains("Ordem de Serviço".toLowerCase())) {
                                            beanTipoDemandaServicoDTO.setClassificacao("O");
                                        }
                                        // System.out.println("Criando tipo de demanda de servicos... " + beanTipoDemandaServicoDTO.getNomeTipoDemandaServico());
                                        tipoDemandaServicoDTO = this.criaTipoDemandaServicoDTO(beanTipoDemandaServicoDTO, tipoDemandaServicoDao);

                                    } else {
                                        tipoDemandaServicoDTO = beanTipoDemandaServicoDTO;
                                    }
                                }

                                if (j == 12) {
                                    beanTipoEventoServicoDTO = new TipoEventoServicoDTO();
                                    beanTipoEventoServicoDTO = this.existeTipoEventoServicoPorNome(coluna, tipoEventoServicoDao);
                                    if (beanTipoEventoServicoDTO.getIdTipoEventoServico() == null) {
                                        /* tipoEventoServicoDTO.setIdTipoEventoServico(tipoEventoServicoDTO.getIdTipoEventoServico()); */
                                        beanTipoEventoServicoDTO.setNomeTipoEventoServico(coluna);
                                        // System.out.println("Criando tipo de evento de servicos... " + beanTipoEventoServicoDTO.getNomeTipoEventoServico());
                                        tipoEventoServicoDTO = this.criaTipoEventoServicoDTO(beanTipoEventoServicoDTO, tipoEventoServicoDao);
                                    } else {
                                        tipoEventoServicoDTO = beanTipoEventoServicoDTO;
                                    }
                                }
                                j++;
                            }

                            beanServicoDTO = new ServicoDTO();
                            beanServicoDTO.setNomeServico(nomeServico);
                            beanServicoDTO = this.existeServicoDTOPorNome(beanServicoDTO);
                            if (beanServicoDTO.getIdServico() == null) {
                                beanServicoDTO.setDataInicio(Util.getSqlDataAtual());
                                beanServicoDTO.setDetalheServico(nomeServico);
                                beanServicoDTO.setDispPortal("N");
                                beanServicoDTO.setIdCategoriaServico(categoriaServicoDTO.getIdCategoriaServico());
                                beanServicoDTO.setIdEmpresa(idEmpresa);
                                beanServicoDTO.setIdSituacaoServico(status);
                                beanServicoDTO.setIdTipoDemandaServico(tipoDemandaServicoDTO.getIdTipoDemandaServico());
                                beanServicoDTO.setIdTipoEventoServico(tipoEventoServicoDTO.getIdTipoEventoServico());
                                beanServicoDTO.setIdTipoServico(tipoServicoDTO.getIdTipoServico());
                                beanServicoDTO.setNomeServico(nomeServico);
                                this.criaServico(beanServicoDTO, this.getDao());
                            } else {
                                beanServicoDTO.setDataInicio(Util.getSqlDataAtual());
                                beanServicoDTO.setDetalheServico(nomeServico);
                                beanServicoDTO.setDispPortal("N");
                                beanServicoDTO.setIdCategoriaServico(categoriaServicoDTO.getIdCategoriaServico());
                                beanServicoDTO.setIdEmpresa(idEmpresa);
                                beanServicoDTO.setIdSituacaoServico(status);
                                beanServicoDTO.setIdTipoDemandaServico(tipoDemandaServicoDTO.getIdTipoDemandaServico());
                                beanServicoDTO.setIdTipoEventoServico(tipoEventoServicoDTO.getIdTipoEventoServico());
                                beanServicoDTO.setIdTipoServico(tipoServicoDTO.getIdTipoServico());
                                beanServicoDTO.setNomeServico(nomeServico);
                                this.criaServico(beanServicoDTO, this.getDao());

                            }
                        } else {
                            System.out.println("Linha NAO OK... " + linha);
                        }
                    }
                }
            }

            arq.close();
            arq = null;

            tc.commit();

        } catch (final Exception e) {
            System.out.println("PROBLEMAS NA CARGA... ");
            tc.rollback();
            e.printStackTrace();
            throw new ServiceException(e);
        } finally {
            tc.closeQuietly();
        }

        System.out.println("Fim - carga de Catalogo de Servicos...");

        return null;
    }

    private ServicoDTO criaServico(final ServicoDTO servicoDTO, final ServicoDao dao) throws Exception {
        if (servicoDTO.getIdServico() == null) {
            return (ServicoDTO) dao.create(servicoDTO);
        } else if (servicoDTO.getIdServico() != null) {
            dao.update(servicoDTO);
        }

        return null;
    }

    private String nomeConcatenado(final List<CategoriaServicoDTO> listCategoriaHierarquia) {
        String nomeConcatenado = "";

        for (int i = listCategoriaHierarquia.size() - 1; i >= 0; i--) {
            final CategoriaServicoDTO categoriaServicoDTO = listCategoriaHierarquia.get(i);

            if (i == 0) {
                nomeConcatenado += categoriaServicoDTO.getNomeCategoriaServico();
            } else {
                nomeConcatenado += categoriaServicoDTO.getNomeCategoriaServico() + " - ";
            }

        }
        return nomeConcatenado;
    }

    private List<CategoriaServicoDTO> listCategoriaHierarquia(final CategoriaServicoDTO categoriaServicoDTO, final CategoriaServicoDao dao,
            final List<CategoriaServicoDTO> listCategoriaHierarquia) throws Exception {
        CategoriaServicoDTO bean = new CategoriaServicoDTO();

        listCategoriaHierarquia.add(categoriaServicoDTO);

        if (categoriaServicoDTO.getIdCategoriaServicoPai() != null) {
            bean.setIdCategoriaServico(categoriaServicoDTO.getIdCategoriaServicoPai());
            bean = (CategoriaServicoDTO) dao.restore(bean);

            if (bean.getIdCategoriaServicoPai() != null) {
                this.listCategoriaHierarquia(bean, dao, listCategoriaHierarquia);
            } else {
                listCategoriaHierarquia.add(bean);
            }
        }
        return listCategoriaHierarquia;
    }

    private List<CategoriaServicoDTO> listCategoriasServicoidPaiFilho(final CategoriaServicoDTO categoriaServicoDTO, final CategoriaServicoDTO beanCategoriaServicoDTO,
            final CategoriaServicoDao categoriaServicoDao) throws Exception {
        CategoriaServicoDTO categoriaServicoDtoExiteServicoPai = null;
        categoriaServicoDtoExiteServicoPai = new CategoriaServicoDTO();

        categoriaServicoDtoExiteServicoPai.setNomeCategoriaServico(beanCategoriaServicoDTO.getNomeCategoriaServico());
        categoriaServicoDtoExiteServicoPai.setIdCategoriaServicoPai(categoriaServicoDTO.getIdCategoriaServico());
        final List<CategoriaServicoDTO> listCatalagoServico = categoriaServicoDao.listCategoriasServicoidPaiFilho(categoriaServicoDtoExiteServicoPai);

        return listCatalagoServico;
    }

    private List<CategoriaServicoDTO> listCategoriasServicoidPaiIsNull(final CategoriaServicoDTO beanCategoriaServicoDTO, final CategoriaServicoDao categoriaServicoDao)
            throws Exception {
        CategoriaServicoDTO categoriaServicoDtoExiteServicoPai = null;
        categoriaServicoDtoExiteServicoPai = new CategoriaServicoDTO();
        categoriaServicoDtoExiteServicoPai.setNomeCategoriaServico(beanCategoriaServicoDTO.getNomeCategoriaServico());

        final List<CategoriaServicoDTO> listCatalagoServico = categoriaServicoDao.listCategoriasServicoidPaiIsNull(categoriaServicoDtoExiteServicoPai);

        return listCatalagoServico;
    }

    private CategoriaServicoDTO criaCategoriaServicoDTO(final CategoriaServicoDTO categoriaServicoDTO, final CategoriaServicoDao dao) throws Exception {
        final List<CategoriaServicoDTO> listCategoriaHierarquia = new ArrayList<CategoriaServicoDTO>();

        if (categoriaServicoDTO.getIdCategoriaServicoPai() != null) {
            categoriaServicoDTO.setNomeCategoriaServicoConcatenado(this.nomeConcatenado(this.listCategoriaHierarquia(categoriaServicoDTO, dao, listCategoriaHierarquia)));
        } else {
            categoriaServicoDTO.setNomeCategoriaServicoConcatenado(categoriaServicoDTO.getNomeCategoriaServico());
        }

        categoriaServicoDTO.setDataInicio(Util.getSqlDataAtual());
        return (CategoriaServicoDTO) dao.create(categoriaServicoDTO);
    }

    private TipoServicoDTO criaTipoServicoDTO(final TipoServicoDTO tipoServicoDTO, final TipoServicoDao dao) throws Exception {

        return (TipoServicoDTO) dao.create(tipoServicoDTO);
    }

    private TipoDemandaServicoDTO criaTipoDemandaServicoDTO(final TipoDemandaServicoDTO tipoDemandaServicoDTO, final TipoDemandaServicoDao dao) throws Exception {

        return (TipoDemandaServicoDTO) dao.create(tipoDemandaServicoDTO);
    }

    private TipoEventoServicoDTO criaTipoEventoServicoDTO(final TipoEventoServicoDTO tipoEventoServicoDTO, final TipoEventoServicoDao dao) throws Exception {

        return (TipoEventoServicoDTO) dao.create(tipoEventoServicoDTO);

    }

    private ServicoDTO existeServicoDTOPorNome(final ServicoDTO servicoDTO) throws Exception {
        final List<ServicoDTO> listServico = (List<ServicoDTO>) this.getDao().findByNome(servicoDTO);

        if (listServico != null) {
            return listServico.get(0);
        }
        return servicoDTO;
    }

    private CategoriaServicoDTO existeCategoriaPorNome(final String nome, final CategoriaServicoDao categoriaServicoDao) throws Exception {

        final CategoriaServicoDTO categoriaServicoDTO = new CategoriaServicoDTO();
        categoriaServicoDTO.setNomeCategoriaServico(nome);
        final List<CategoriaServicoDTO> listCategoriaServico = (List<CategoriaServicoDTO>) categoriaServicoDao.findByNomeCategoria(categoriaServicoDTO);

        if (listCategoriaServico != null) {
            return listCategoriaServico.get(0);
        }
        return categoriaServicoDTO;

    }

    private TipoServicoDTO existeTipoServicoPorNome(final String nome, final TipoServicoDao tipoServicoDao) throws Exception {
        final TipoServicoDTO tipoServicoDTO = new TipoServicoDTO();

        tipoServicoDTO.setNomeTipoServico(nome);

        final List<TipoServicoDTO> listTipoServico = (List<TipoServicoDTO>) tipoServicoDao.findByNome(tipoServicoDTO);

        if (listTipoServico != null) {
            return listTipoServico.get(0);
        }
        return tipoServicoDTO;
    }

    private TipoDemandaServicoDTO existeTipoDemandaPorNome(final String nome, final TipoDemandaServicoDao tipoDemandaServicoDao) throws Exception {

        final TipoDemandaServicoDTO tipoDemandaServicoDTO = new TipoDemandaServicoDTO();
        tipoDemandaServicoDTO.setNomeTipoDemandaServico(nome);

        final List<TipoDemandaServicoDTO> listTipoDemanda = (List<TipoDemandaServicoDTO>) tipoDemandaServicoDao.findByNome(tipoDemandaServicoDTO);

        if (listTipoDemanda != null) {
            return listTipoDemanda.get(0);
        }
        return tipoDemandaServicoDTO;

    }

    private TipoEventoServicoDTO existeTipoEventoServicoPorNome(final String nome, final TipoEventoServicoDao tipoEventoServicoDao) throws Exception {
        final TipoEventoServicoDTO tipoEventoServicoDTO = new TipoEventoServicoDTO();
        tipoEventoServicoDTO.setNomeTipoEventoServico(nome);

        final List<TipoEventoServicoDTO> listTipoEventoServico = (List<TipoEventoServicoDTO>) tipoEventoServicoDao.findByNome(tipoEventoServicoDTO);

        if (listTipoEventoServico != null) {
            return listTipoEventoServico.get(0);
        }
        return tipoEventoServicoDTO;
    }
}
