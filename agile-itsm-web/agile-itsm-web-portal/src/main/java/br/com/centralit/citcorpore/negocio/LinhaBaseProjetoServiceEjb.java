package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.Iterator;

import br.com.centralit.citcorpore.bean.LinhaBaseProjetoDTO;
import br.com.centralit.citcorpore.bean.PerfilContratoDTO;
import br.com.centralit.citcorpore.bean.ProdutoTarefaLinBaseProjDTO;
import br.com.centralit.citcorpore.bean.RecursoProjetoDTO;
import br.com.centralit.citcorpore.bean.RecursoTarefaLinBaseProjDTO;
import br.com.centralit.citcorpore.bean.TarefaLinhaBaseProjetoDTO;
import br.com.centralit.citcorpore.integracao.LinhaBaseProjetoDao;
import br.com.centralit.citcorpore.integracao.PerfilContratoDao;
import br.com.centralit.citcorpore.integracao.ProdutoTarefaLinBaseProjDao;
import br.com.centralit.citcorpore.integracao.RecursoProjetoDao;
import br.com.centralit.citcorpore.integracao.RecursoTarefaLinBaseProjDao;
import br.com.centralit.citcorpore.integracao.TarefaLinhaBaseProjetoDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilStrings;

public class LinhaBaseProjetoServiceEjb extends CrudServiceImpl implements LinhaBaseProjetoService {

    private LinhaBaseProjetoDao dao;

    @Override
    protected LinhaBaseProjetoDao getDao() {
        if (dao == null) {
            dao = new LinhaBaseProjetoDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdProjeto(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdProjeto(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdProjeto(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdProjeto(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public IDto create(IDto model) throws ServiceException, LogicException {
        // Instancia Objeto controlador de transacao
        final LinhaBaseProjetoDao crudDao = new LinhaBaseProjetoDao();
        final TarefaLinhaBaseProjetoDao tarefaLinhaBaseProjetoDao = new TarefaLinhaBaseProjetoDao();
        final RecursoTarefaLinBaseProjDao recursoTarefaLinBaseProjDao = new RecursoTarefaLinBaseProjDao();
        final ProdutoTarefaLinBaseProjDao produtoTarefaLinBaseProjDao = new ProdutoTarefaLinBaseProjDao();
        final PerfilContratoDao perfilContratoDao = new PerfilContratoDao();
        final RecursoProjetoDao recursoProjetoDao = new RecursoProjetoDao();
        final TarefaLinhaBaseProjetoServiceEjb tarefaLinhaBaseProjetoServiceEjb = new TarefaLinhaBaseProjetoServiceEjb();
        final TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
        try {
            // Faz validacao, caso exista.
            this.validaCreate(model);

            // Instancia ou obtem os DAOs necessarios.

            // Seta o TransactionController para os DAOs
            crudDao.setTransactionControler(tc);
            tarefaLinhaBaseProjetoDao.setTransactionControler(tc);
            recursoTarefaLinBaseProjDao.setTransactionControler(tc);
            produtoTarefaLinBaseProjDao.setTransactionControler(tc);
            perfilContratoDao.setTransactionControler(tc);
            recursoProjetoDao.setTransactionControler(tc);

            // Inicia transacao
            tc.start();

            // Executa operacoes pertinentes ao negocio.
            LinhaBaseProjetoDTO linhaBaseProjetoDTO = (LinhaBaseProjetoDTO) model;
            /*
             * if (linhaBaseProjetoDTO.getIdLinhaBaseProjetoUpdate() != null){
             * LinhaBaseProjetoDTO linhaBaseProjetoAux = new LinhaBaseProjetoDTO();
             * linhaBaseProjetoAux.setIdLinhaBaseProjeto(linhaBaseProjetoDTO.getIdLinhaBaseProjetoUpdate());
             * linhaBaseProjetoAux.setSituacao(LinhaBaseProjetoDTO.INATIVO);
             * crudDao.updateNotNull(linhaBaseProjetoAux);
             * }
             */
            linhaBaseProjetoDTO = (LinhaBaseProjetoDTO) model;
            crudDao.inativaLinhasBaseAnteriorByIdProjeto(linhaBaseProjetoDTO.getIdProjeto()); // ISSO ACONTECE, POIS PODE OCORRER DE ESTAR MODIFICANDO O PROJETO.

            model = crudDao.create(model);
            linhaBaseProjetoDTO = (LinhaBaseProjetoDTO) model;
            if (linhaBaseProjetoDTO.getColTarefas() != null) {
                int seq = 0;
                final int[] idAnterior = {-999, -999, -999, -999, -999, -999, -999, -999, -999, -999, -999, -999, -999, -999, -999, -999, -999, -999, -999, -999, -999, -999, -999,
                        -999, -999, -999, -999, -999, -999, -999, -999, -999, -999, -999, -999, -999, -999, -999, -999, -999};
                int nivel = -1;
                for (final Iterator it = linhaBaseProjetoDTO.getColTarefas().iterator(); it.hasNext();) {
                    TarefaLinhaBaseProjetoDTO tarefaLinhaBaseProjetoDTO = (TarefaLinhaBaseProjetoDTO) it.next();
                    tarefaLinhaBaseProjetoDTO.setIdLinhaBaseProjeto(linhaBaseProjetoDTO.getIdLinhaBaseProjeto());
                    tarefaLinhaBaseProjetoDTO.setIdTarefaLinhaBaseProjetoMigr(tarefaLinhaBaseProjetoDTO.getIdTarefaLinhaBaseProjeto());
                    tarefaLinhaBaseProjetoDTO.setSequencia(seq);
                    nivel = tarefaLinhaBaseProjetoDTO.getNivel().intValue();
                    if (nivel >= 1) {
                        nivel = nivel - 1;
                        if (idAnterior[nivel] > 0) {
                            tarefaLinhaBaseProjetoDTO.setIdTarefaLinhaBaseProjetoPai(idAnterior[nivel]);
                        }
                    }

                    if (tarefaLinhaBaseProjetoDTO.getIdTarefaLinhaBaseProjetoMigr() != null) {
                        TarefaLinhaBaseProjetoDTO tarefaLinhaBaseProjetoAux = new TarefaLinhaBaseProjetoDTO();
                        tarefaLinhaBaseProjetoAux.setIdTarefaLinhaBaseProjeto(tarefaLinhaBaseProjetoDTO.getIdTarefaLinhaBaseProjetoMigr());
                        tarefaLinhaBaseProjetoAux = (TarefaLinhaBaseProjetoDTO) tarefaLinhaBaseProjetoDao.restore(tarefaLinhaBaseProjetoAux);
                        if (tarefaLinhaBaseProjetoAux != null) {
                            tarefaLinhaBaseProjetoDTO.setIdPagamentoProjeto(tarefaLinhaBaseProjetoAux.getIdPagamentoProjeto());
                        }
                    }
                    if (tarefaLinhaBaseProjetoDTO.getIdMarcoPagamentoPrj() != null && tarefaLinhaBaseProjetoDTO.getIdMarcoPagamentoPrj().intValue() <= 0) {
                        tarefaLinhaBaseProjetoDTO.setIdMarcoPagamentoPrj(null);
                    }
                    tarefaLinhaBaseProjetoDTO = (TarefaLinhaBaseProjetoDTO) tarefaLinhaBaseProjetoDao.create(tarefaLinhaBaseProjetoDTO);
                    nivel = tarefaLinhaBaseProjetoDTO.getNivel().intValue();
                    idAnterior[nivel] = tarefaLinhaBaseProjetoDTO.getIdTarefaLinhaBaseProjeto();
                    if (tarefaLinhaBaseProjetoDTO.getColRecursos() != null) {
                        double tempoTotal = 0;
                        for (final Iterator itRec = tarefaLinhaBaseProjetoDTO.getColRecursos().iterator(); itRec.hasNext();) {
                            final RecursoTarefaLinBaseProjDTO recursoTarefaLinBaseProjDTO = (RecursoTarefaLinBaseProjDTO) itRec.next();
                            if (!recursoTarefaLinBaseProjDTO.getTempoAloc().equalsIgnoreCase("00:00")) {
                                recursoTarefaLinBaseProjDTO.setTempoAlocEmMinutos(this.transformaEmMinutos(recursoTarefaLinBaseProjDTO.getTempoAloc()));
                                tempoTotal = tempoTotal + recursoTarefaLinBaseProjDTO.getTempoAlocEmMinutos();
                            } else {
                                recursoTarefaLinBaseProjDTO.setTempoAlocEmMinutos(Double.parseDouble(recursoTarefaLinBaseProjDTO.getEsforcoPorOS()) * 60);
                                tempoTotal = tempoTotal + recursoTarefaLinBaseProjDTO.getTempoAlocEmMinutos();
                            }
                        }
                        double custoHoraPerfilTotal = 0;
                        double custoHoraRecursoTotal = 0;
                        double custoEsforcoPorOSTotal = 0;
                        double esforcoPorOSTotal = 0;
                        for (final Iterator itRec = tarefaLinhaBaseProjetoDTO.getColRecursos().iterator(); itRec.hasNext();) {
                            final RecursoTarefaLinBaseProjDTO recursoTarefaLinBaseProjDTO = (RecursoTarefaLinBaseProjDTO) itRec.next();
                            if (!recursoTarefaLinBaseProjDTO.getTempoAloc().equalsIgnoreCase("00:00")) {
                                recursoTarefaLinBaseProjDTO.setTempoAlocEmMinutos(this.transformaEmMinutos(recursoTarefaLinBaseProjDTO.getTempoAloc()));
                            } else {
                                recursoTarefaLinBaseProjDTO.setTempoAlocEmMinutos(Double.parseDouble(recursoTarefaLinBaseProjDTO.getEsforcoPorOS()) * 60);
                            }
                            recursoTarefaLinBaseProjDTO.setIdTarefaLinhaBaseProjeto(tarefaLinhaBaseProjetoDTO.getIdTarefaLinhaBaseProjeto());
                            if (tempoTotal > 0) {
                                recursoTarefaLinBaseProjDTO.setPercentualAloc(recursoTarefaLinBaseProjDTO.getTempoAlocEmMinutos() / tempoTotal * 100);
                            } else {
                                recursoTarefaLinBaseProjDTO.setPercentualAloc(new Double(0));
                            }
                            recursoTarefaLinBaseProjDTO.setTempoAlocMinutos(recursoTarefaLinBaseProjDTO.getTempoAlocEmMinutos());
                            recursoTarefaLinBaseProjDTO.setTempoAloc(UtilStrings.nullToVazio(recursoTarefaLinBaseProjDTO.getTempoAloc()).replaceAll(":", ""));
                            PerfilContratoDTO perfilContratoDTO = new PerfilContratoDTO();
                            perfilContratoDTO.setIdPerfilContrato(recursoTarefaLinBaseProjDTO.getIdPerfilContrato());
                            perfilContratoDTO = (PerfilContratoDTO) perfilContratoDao.restore(perfilContratoDTO);
                            final double tempoHoras = recursoTarefaLinBaseProjDTO.getTempoAlocEmMinutos() / 60;
                            double custoHoraPerfil = 0;
                            if (perfilContratoDTO != null) {
                                if (perfilContratoDTO.getCustoHora() != null) {
                                    if (tempoHoras == 0) {
                                        custoHoraPerfil = perfilContratoDTO.getCustoHora().doubleValue();
                                    } else {
                                        custoHoraPerfil = perfilContratoDTO.getCustoHora().doubleValue() * tempoHoras;
                                    }
                                    custoHoraPerfilTotal = custoHoraPerfilTotal + custoHoraPerfil;

                                }
                            }

                            RecursoProjetoDTO recursoProjetoDTO = new RecursoProjetoDTO();
                            recursoProjetoDTO.setIdEmpregado(recursoTarefaLinBaseProjDTO.getIdEmpregado());
                            recursoProjetoDTO.setIdProjeto(linhaBaseProjetoDTO.getIdProjeto());
                            recursoProjetoDTO = (RecursoProjetoDTO) recursoProjetoDao.restore(recursoProjetoDTO);
                            double custoHoraRecurso = 0;
                            if (recursoProjetoDTO != null) {
                                if (recursoProjetoDTO.getCustoHora() != null) {
                                    custoHoraRecurso = recursoProjetoDTO.getCustoHora().doubleValue() * tempoHoras;
                                    custoHoraRecursoTotal = custoHoraRecursoTotal + custoHoraRecurso;
                                    custoEsforcoPorOSTotal += custoHoraPerfil * Double.parseDouble(recursoTarefaLinBaseProjDTO.getEsforcoPorOS());
                                }
                            }

                            if ((recursoTarefaLinBaseProjDTO.getEsforcoPorOS() != null || !recursoTarefaLinBaseProjDTO.getEsforcoPorOS().isEmpty())
                                    && Double.parseDouble(recursoTarefaLinBaseProjDTO.getEsforcoPorOS()) != 0) {
                                recursoTarefaLinBaseProjDTO.setCustoPerfil(custoHoraPerfil);
                                recursoTarefaLinBaseProjDTO.setCusto(custoHoraPerfil);
                            } else {
                                recursoTarefaLinBaseProjDTO.setCustoPerfil(custoHoraPerfil);
                                recursoTarefaLinBaseProjDTO.setCusto(custoHoraRecurso);
                            }

                            esforcoPorOSTotal = Double.parseDouble(recursoTarefaLinBaseProjDTO.getEsforcoPorOS());
                            recursoTarefaLinBaseProjDao.create(recursoTarefaLinBaseProjDTO);
                        }
                        final TarefaLinhaBaseProjetoDTO tarefaLinhaBaseProjetoAux = new TarefaLinhaBaseProjetoDTO();
                        tarefaLinhaBaseProjetoAux.setIdTarefaLinhaBaseProjeto(tarefaLinhaBaseProjetoDTO.getIdTarefaLinhaBaseProjeto());
                        tarefaLinhaBaseProjetoAux.setCustoPerfil(custoHoraPerfilTotal);
                        if (custoHoraRecursoTotal == 0) {
                            tarefaLinhaBaseProjetoAux.setCusto(custoEsforcoPorOSTotal);
                        } else {
                            tarefaLinhaBaseProjetoAux.setCusto(custoHoraRecursoTotal);
                        }
                        tarefaLinhaBaseProjetoAux.setTempoTotAlocMinutos(tempoTotal);
                        tarefaLinhaBaseProjetoAux.setEsforcoPorOS(esforcoPorOSTotal + "");
                        tarefaLinhaBaseProjetoDao.updateNotNull(tarefaLinhaBaseProjetoAux);
                    }
                    if (tarefaLinhaBaseProjetoDTO.getColProdutos() != null) {
                        for (final Iterator itRec = tarefaLinhaBaseProjetoDTO.getColProdutos().iterator(); itRec.hasNext();) {
                            final ProdutoTarefaLinBaseProjDTO produtoTarefaLinBaseProjDTO = (ProdutoTarefaLinBaseProjDTO) itRec.next();
                            produtoTarefaLinBaseProjDTO.setIdTarefaLinhaBaseProjeto(tarefaLinhaBaseProjetoDTO.getIdTarefaLinhaBaseProjeto());
                            produtoTarefaLinBaseProjDao.create(produtoTarefaLinBaseProjDTO);
                        }
                    }
                    //
                    tarefaLinhaBaseProjetoServiceEjb.atualizaInfoProporcionais(tc, tarefaLinhaBaseProjetoDTO.getIdTarefaLinhaBaseProjeto());
                    //
                    seq++;
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

    private int transformaEmMinutos(final String tempoAloc) {
        if (tempoAloc == null || tempoAloc.trim().equalsIgnoreCase("")) {
            return 0;
        }
        if (tempoAloc.indexOf(":") >= 0) {
            final String[] str = tempoAloc.split(":");
            int x1 = 0;
            int x2 = 0;
            try {
                x1 = Integer.parseInt(str[0]);
            } catch (final Exception e) {
                return 0;
            }
            try {
                x2 = Integer.parseInt(str[1]);
            } catch (final Exception e) {
                return 0;
            }
            return x1 * 60 + x2;
        } else {
            try {
                return Integer.parseInt(UtilStrings.apenasNumeros(tempoAloc));
            } catch (final Exception e) {
                return 0;
            }
        }
    }

    @Override
    public void update(final IDto model) throws ServiceException, LogicException {
        // Instancia Objeto controlador de transacao
        final LinhaBaseProjetoDao crudDao = new LinhaBaseProjetoDao();
        final TarefaLinhaBaseProjetoDao tarefaLinhaBaseProjetoDao = new TarefaLinhaBaseProjetoDao();
        final RecursoTarefaLinBaseProjDao recursoTarefaLinBaseProjDao = new RecursoTarefaLinBaseProjDao();
        final ProdutoTarefaLinBaseProjDao produtoTarefaLinBaseProjDao = new ProdutoTarefaLinBaseProjDao();
        final PerfilContratoDao perfilContratoDao = new PerfilContratoDao();
        final RecursoProjetoDao recursoProjetoDao = new RecursoProjetoDao();
        final TarefaLinhaBaseProjetoServiceEjb tarefaLinhaBaseProjetoServiceEjb = new TarefaLinhaBaseProjetoServiceEjb();
        final TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
        try {
            // Faz validacao, caso exista.
            this.validaUpdate(model);

            // Instancia ou obtem os DAOs necessarios.

            // Seta o TransactionController para os DAOs
            crudDao.setTransactionControler(tc);
            tarefaLinhaBaseProjetoDao.setTransactionControler(tc);
            recursoTarefaLinBaseProjDao.setTransactionControler(tc);
            produtoTarefaLinBaseProjDao.setTransactionControler(tc);
            perfilContratoDao.setTransactionControler(tc);
            recursoProjetoDao.setTransactionControler(tc);

            // Inicia transacao
            tc.start();

            // Executa operacoes pertinentes ao negocio.
            crudDao.updateNotNull(model);
            final LinhaBaseProjetoDTO linhaBaseProjetoDTO = (LinhaBaseProjetoDTO) model;
            recursoTarefaLinBaseProjDao.deleteByIdLinhaBaseProjeto(linhaBaseProjetoDTO.getIdLinhaBaseProjeto());
            produtoTarefaLinBaseProjDao.deleteByIdLinhaBaseProjeto(linhaBaseProjetoDTO.getIdLinhaBaseProjeto());
            tarefaLinhaBaseProjetoDao.deleteFilhasByIdLinhaBaseProjeto(linhaBaseProjetoDTO.getIdLinhaBaseProjeto());
            tarefaLinhaBaseProjetoDao.deleteByIdLinhaBaseProjeto(linhaBaseProjetoDTO.getIdLinhaBaseProjeto());
            if (linhaBaseProjetoDTO.getColTarefas() != null) {
                int seq = 0;
                final int[] idAnterior = {-999, -999, -999, -999, -999, -999, -999, -999, -999, -999, -999, -999, -999, -999, -999, -999, -999, -999, -999, -999, -999, -999, -999,
                        -999, -999, -999, -999, -999, -999, -999, -999, -999, -999, -999, -999};
                int nivel = -1;
                for (final Iterator it = linhaBaseProjetoDTO.getColTarefas().iterator(); it.hasNext();) {
                    TarefaLinhaBaseProjetoDTO tarefaLinhaBaseProjetoDTO = (TarefaLinhaBaseProjetoDTO) it.next();
                    tarefaLinhaBaseProjetoDTO.setIdLinhaBaseProjeto(linhaBaseProjetoDTO.getIdLinhaBaseProjeto());
                    tarefaLinhaBaseProjetoDTO.setSequencia(seq);
                    nivel = tarefaLinhaBaseProjetoDTO.getNivel().intValue();
                    if (nivel >= 1) {
                        nivel = nivel - 1;
                        if (idAnterior[nivel] > 0) {
                            tarefaLinhaBaseProjetoDTO.setIdTarefaLinhaBaseProjetoPai(idAnterior[nivel]);
                        }
                    }
                    tarefaLinhaBaseProjetoDTO = (TarefaLinhaBaseProjetoDTO) tarefaLinhaBaseProjetoDao.create(tarefaLinhaBaseProjetoDTO);
                    nivel = tarefaLinhaBaseProjetoDTO.getNivel().intValue();
                    idAnterior[nivel] = tarefaLinhaBaseProjetoDTO.getIdTarefaLinhaBaseProjeto();
                    if (tarefaLinhaBaseProjetoDTO.getColRecursos() != null) {
                        double tempoTotal = 0;
                        for (final Iterator itRec = tarefaLinhaBaseProjetoDTO.getColRecursos().iterator(); itRec.hasNext();) {
                            final RecursoTarefaLinBaseProjDTO recursoTarefaLinBaseProjDTO = (RecursoTarefaLinBaseProjDTO) itRec.next();
                            if (recursoTarefaLinBaseProjDTO.getTempoAloc() != null && !recursoTarefaLinBaseProjDTO.getTempoAloc().equalsIgnoreCase("00:00")) {
                                recursoTarefaLinBaseProjDTO.setTempoAlocEmMinutos(this.transformaEmMinutos(recursoTarefaLinBaseProjDTO.getTempoAloc()));
                                tempoTotal = tempoTotal + recursoTarefaLinBaseProjDTO.getTempoAlocEmMinutos();
                            } else {
                                recursoTarefaLinBaseProjDTO.setTempoAlocEmMinutos(Double.parseDouble(recursoTarefaLinBaseProjDTO.getEsforcoPorOS()) * 60);
                                tempoTotal = tempoTotal + recursoTarefaLinBaseProjDTO.getTempoAlocEmMinutos();
                            }

                        }
                        double custoHoraPerfilTotal = 0;
                        double custoHoraRecursoTotal = 0;
                        double custoEsforcoPorOSTotal = 0;
                        double esforcoPorOSTotal = 0;
                        for (final Iterator itRec = tarefaLinhaBaseProjetoDTO.getColRecursos().iterator(); itRec.hasNext();) {
                            final RecursoTarefaLinBaseProjDTO recursoTarefaLinBaseProjDTO = (RecursoTarefaLinBaseProjDTO) itRec.next();
                            if (recursoTarefaLinBaseProjDTO.getTempoAloc() != null && !recursoTarefaLinBaseProjDTO.getTempoAloc().equalsIgnoreCase("00:00")) {
                                recursoTarefaLinBaseProjDTO.setTempoAlocEmMinutos(this.transformaEmMinutos(recursoTarefaLinBaseProjDTO.getTempoAloc()));
                            } else {
                                recursoTarefaLinBaseProjDTO.setTempoAlocEmMinutos(Double.parseDouble(recursoTarefaLinBaseProjDTO.getEsforcoPorOS()) * 60);
                            }
                            recursoTarefaLinBaseProjDTO.setIdTarefaLinhaBaseProjeto(tarefaLinhaBaseProjetoDTO.getIdTarefaLinhaBaseProjeto());
                            if (tempoTotal > 0) {
                                recursoTarefaLinBaseProjDTO.setPercentualAloc(recursoTarefaLinBaseProjDTO.getTempoAlocEmMinutos() / tempoTotal * 100);
                            } else {
                                recursoTarefaLinBaseProjDTO.setPercentualAloc(new Double(0));
                            }
                            recursoTarefaLinBaseProjDTO.setTempoAlocMinutos(recursoTarefaLinBaseProjDTO.getTempoAlocEmMinutos());
                            recursoTarefaLinBaseProjDTO.setTempoAloc(UtilStrings.nullToVazio(recursoTarefaLinBaseProjDTO.getTempoAloc()).replaceAll(":", ""));
                            PerfilContratoDTO perfilContratoDTO = new PerfilContratoDTO();
                            perfilContratoDTO.setIdPerfilContrato(recursoTarefaLinBaseProjDTO.getIdPerfilContrato());
                            perfilContratoDTO = (PerfilContratoDTO) perfilContratoDao.restore(perfilContratoDTO);
                            final double tempoHoras = recursoTarefaLinBaseProjDTO.getTempoAlocEmMinutos() / 60;
                            double custoHoraPerfil = 0;
                            if (perfilContratoDTO != null) {
                                if (perfilContratoDTO.getCustoHora() != null) {
                                    if (tempoHoras == 0) {
                                        custoHoraPerfil = perfilContratoDTO.getCustoHora().doubleValue();
                                    } else {
                                        custoHoraPerfil = perfilContratoDTO.getCustoHora().doubleValue() * tempoHoras;
                                    }

                                    custoHoraPerfilTotal = custoHoraPerfilTotal + custoHoraPerfil;
                                }
                            }
                            RecursoProjetoDTO recursoProjetoDTO = new RecursoProjetoDTO();
                            recursoProjetoDTO.setIdEmpregado(recursoTarefaLinBaseProjDTO.getIdEmpregado());
                            recursoProjetoDTO.setIdProjeto(linhaBaseProjetoDTO.getIdProjeto());
                            recursoProjetoDTO = (RecursoProjetoDTO) recursoProjetoDao.restore(recursoProjetoDTO);
                            double custoHoraRecurso = 0;
                            if (recursoProjetoDTO != null) {
                                if (recursoProjetoDTO.getCustoHora() != null) {
                                    custoHoraRecurso = recursoProjetoDTO.getCustoHora().doubleValue() * tempoHoras;
                                    custoHoraRecursoTotal = custoHoraRecursoTotal + custoHoraRecurso;
                                    custoEsforcoPorOSTotal += custoHoraPerfil * Double.parseDouble(recursoTarefaLinBaseProjDTO.getEsforcoPorOS());
                                }
                            }

                            if ((recursoTarefaLinBaseProjDTO.getEsforcoPorOS() != null || !recursoTarefaLinBaseProjDTO.getEsforcoPorOS().isEmpty())
                                    && Double.parseDouble(recursoTarefaLinBaseProjDTO.getEsforcoPorOS()) != 0) {
                                // recursoTarefaLinBaseProjDTO.setCustoPerfil(custoHoraPerfil * Double.parseDouble(recursoTarefaLinBaseProjDTO.getEsforcoPorOS()));
                                // recursoTarefaLinBaseProjDTO.setCusto(custoHoraPerfil * Double.parseDouble(recursoTarefaLinBaseProjDTO.getEsforcoPorOS()));
                                recursoTarefaLinBaseProjDTO.setCustoPerfil(custoHoraPerfil);
                                recursoTarefaLinBaseProjDTO.setCusto(custoHoraPerfil);
                            } else {
                                recursoTarefaLinBaseProjDTO.setCustoPerfil(custoHoraPerfil);
                                recursoTarefaLinBaseProjDTO.setCusto(custoHoraRecurso);
                            }

                            esforcoPorOSTotal = Double.parseDouble(recursoTarefaLinBaseProjDTO.getEsforcoPorOS());
                            recursoTarefaLinBaseProjDao.create(recursoTarefaLinBaseProjDTO);
                        }
                        final TarefaLinhaBaseProjetoDTO tarefaLinhaBaseProjetoAux = new TarefaLinhaBaseProjetoDTO();
                        tarefaLinhaBaseProjetoAux.setIdTarefaLinhaBaseProjeto(tarefaLinhaBaseProjetoDTO.getIdTarefaLinhaBaseProjeto());
                        tarefaLinhaBaseProjetoAux.setCustoPerfil(custoHoraPerfilTotal);
                        if (custoHoraRecursoTotal == 0) {
                            tarefaLinhaBaseProjetoAux.setCusto(custoEsforcoPorOSTotal);
                        } else {
                            tarefaLinhaBaseProjetoAux.setCusto(custoHoraRecursoTotal);
                        }
                        tarefaLinhaBaseProjetoAux.setTempoTotAlocMinutos(tempoTotal);
                        tarefaLinhaBaseProjetoAux.setEsforcoPorOS(esforcoPorOSTotal + "");
                        tarefaLinhaBaseProjetoDao.updateNotNull(tarefaLinhaBaseProjetoAux);
                    }
                    if (tarefaLinhaBaseProjetoDTO.getColProdutos() != null) {
                        for (final Iterator itRec = tarefaLinhaBaseProjetoDTO.getColProdutos().iterator(); itRec.hasNext();) {
                            final ProdutoTarefaLinBaseProjDTO produtoTarefaLinBaseProjDTO = (ProdutoTarefaLinBaseProjDTO) itRec.next();
                            produtoTarefaLinBaseProjDTO.setIdTarefaLinhaBaseProjeto(tarefaLinhaBaseProjetoDTO.getIdTarefaLinhaBaseProjeto());
                            produtoTarefaLinBaseProjDao.create(produtoTarefaLinBaseProjDTO);
                        }
                    }
                    //
                    tarefaLinhaBaseProjetoServiceEjb.atualizaInfoProporcionais(tc, tarefaLinhaBaseProjetoDTO.getIdTarefaLinhaBaseProjeto());
                    //
                    seq++;
                }
            }

            // Faz commit e fecha a transacao.
            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
    }

    @Override
    public void updateAutorizacaoMudanca(final IDto model) throws Exception {
        final LinhaBaseProjetoDTO linhaBaseProjetoParm = (LinhaBaseProjetoDTO) model;
        final LinhaBaseProjetoDTO linhaBaseProjetoDTO = new LinhaBaseProjetoDTO();
        linhaBaseProjetoDTO.setIdLinhaBaseProjeto(linhaBaseProjetoParm.getIdLinhaBaseProjeto());
        linhaBaseProjetoDTO.setJustificativaMudanca(linhaBaseProjetoParm.getJustificativaMudanca());
        linhaBaseProjetoDTO.setDataSolMudanca(linhaBaseProjetoParm.getDataSolMudanca());
        linhaBaseProjetoDTO.setHoraSolMudanca(linhaBaseProjetoParm.getHoraSolMudanca());
        linhaBaseProjetoDTO.setUsuarioSolMudanca(linhaBaseProjetoParm.getUsuarioSolMudanca());
        this.getDao().updateNotNull(linhaBaseProjetoDTO);
    }
}
