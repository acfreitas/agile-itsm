package br.com.centralit.citcorpore.negocio;

import java.sql.Date;
import java.util.Collection;
import java.util.Iterator;

import br.com.centralit.citcorpore.bean.FaturaApuracaoANSDTO;
import br.com.centralit.citcorpore.bean.FaturaDTO;
import br.com.centralit.citcorpore.bean.FaturaOSDTO;
import br.com.centralit.citcorpore.bean.GlosaOSDTO;
import br.com.centralit.citcorpore.bean.OSDTO;
import br.com.centralit.citcorpore.integracao.FaturaApuracaoANSDao;
import br.com.centralit.citcorpore.integracao.FaturaDao;
import br.com.centralit.citcorpore.integracao.FaturaOSDao;
import br.com.centralit.citcorpore.integracao.GlosaOSDao;
import br.com.centralit.citcorpore.integracao.OSDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilDatas;

public class FaturaServiceEjb extends CrudServiceImpl implements FaturaService {

    private FaturaDao dao;

    @Override
    protected FaturaDao getDao() {
        if (dao == null) {
            dao = new FaturaDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdContrato(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdContrato(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdContrato(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdContrato(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdContratoAndPeriodoAndSituacao(final Integer idContrato, final Date dataInicio, final Date dataFim, final String[] situacao) throws Exception {
        return this.getDao().findByIdContratoAndPeriodoAndSituacao(idContrato, dataInicio, dataFim, situacao);
    }

    @Override
    public IDto create(IDto model) throws ServiceException, LogicException {
        // Instancia Objeto controlador de transacao
        final CrudDAO crudDao = this.getDao();
        final FaturaApuracaoANSDao faturaApuracaoANSDao = new FaturaApuracaoANSDao();
        final FaturaOSDao faturaOSDao = new FaturaOSDao();
        final TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
        try {
            // Faz validacao, caso exista.
            this.validaCreate(model);
            final FaturaDTO faturaDTO = (FaturaDTO) model;
            // Instancia ou obtem os DAOs necessarios.

            // Seta o TransactionController para os DAOs
            crudDao.setTransactionControler(tc);
            faturaApuracaoANSDao.setTransactionControler(tc);
            faturaOSDao.setTransactionControler(tc);

            // Inicia transacao
            tc.start();

            double valorGlosado = 0;

            // Executa operacoes pertinentes ao negocio.
            model = crudDao.create(model);
            if (faturaDTO.getColItens() != null) {
                for (final Iterator it = faturaDTO.getColItens().iterator(); it.hasNext();) {
                    final FaturaApuracaoANSDTO faturaApuracaoANSDTO = (FaturaApuracaoANSDTO) it.next();
                    faturaApuracaoANSDTO.setIdFatura(faturaDTO.getIdFatura());
                    faturaApuracaoANSDTO.setDataApuracao(UtilDatas.getDataAtual());

                    if (faturaApuracaoANSDTO.getValorGlosa() != null) {
                        valorGlosado = valorGlosado + faturaApuracaoANSDTO.getValorGlosa().doubleValue();
                    }

                    faturaApuracaoANSDao.create(faturaApuracaoANSDTO);
                }
            }

            if (faturaDTO.getIdOSFatura() != null) {
                for (int i = 0; i < faturaDTO.getIdOSFatura().length; i++) {
                    final FaturaOSDTO faturaOSDTO = new FaturaOSDTO();
                    faturaOSDTO.setIdFatura(faturaDTO.getIdFatura());
                    faturaOSDTO.setIdOs(faturaDTO.getIdOSFatura()[i]);
                    faturaOSDao.create(faturaOSDTO);
                }
            }

            final OSDao osSDao = new OSDao();
            final GlosaOSDao glosaOSDao = new GlosaOSDao();
            final Collection colOSs = osSDao.listOSByIds(faturaDTO.getIdContrato(), faturaDTO.getIdOSFatura());
            double valorPrevistoOS = 0;
            double valorExecutadoOS = 0;
            if (colOSs != null) {
                for (final Iterator itOs = colOSs.iterator(); itOs.hasNext();) {
                    final OSDTO osDto = (OSDTO) itOs.next();
                    // if (osDto.getGlosaOS() != null){
                    // valorGlosado = valorGlosado + osDto.getGlosaOS().doubleValue();
                    // }
                    if (osDto.getCustoOS() != null) {
                        valorPrevistoOS = valorPrevistoOS + osDto.getCustoOS().doubleValue();
                    }
                    if (osDto.getExecutadoOS() != null) {
                        valorExecutadoOS = valorExecutadoOS + osDto.getExecutadoOS().doubleValue();
                    }
                    final Collection colGlosasOS = glosaOSDao.findByIdOs(osDto.getIdOS());
                    if (colGlosasOS != null) {
                        for (final Iterator it = colGlosasOS.iterator(); it.hasNext();) {
                            final GlosaOSDTO glosaOSDTO = (GlosaOSDTO) it.next();
                            if (glosaOSDTO.getCustoGlosa() != null) {
                                valorGlosado = valorGlosado + glosaOSDTO.getCustoGlosa().doubleValue();
                            }
                        }
                    }
                }
            }

            faturaDTO.setValorSomaGlosasOS(valorGlosado);
            faturaDTO.setValorPrevistoSomaOS(valorPrevistoOS);
            faturaDTO.setValorExecutadoSomaOS(valorExecutadoOS);
            crudDao.update(faturaDTO);

            // Faz commit e fecha a transacao.
            tc.commit();
            tc.close();

            return model;
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
        return model;
    }

    @Override
    public void update(final IDto model) throws ServiceException, LogicException {
        // Instancia Objeto controlador de transacao
        final CrudDAO crudDao = this.getDao();
        final FaturaApuracaoANSDao faturaApuracaoANSDao = new FaturaApuracaoANSDao();
        final FaturaOSDao faturaOSDao = new FaturaOSDao();
        final TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
        try {
            // Faz validacao, caso exista.
            this.validaCreate(model);
            final FaturaDTO faturaDTO = (FaturaDTO) model;
            // Instancia ou obtem os DAOs necessarios.

            // Seta o TransactionController para os DAOs
            crudDao.setTransactionControler(tc);
            faturaApuracaoANSDao.setTransactionControler(tc);
            faturaOSDao.setTransactionControler(tc);

            // Inicia transacao
            tc.start();

            FaturaDTO faturaAux = new FaturaDTO();
            faturaAux.setIdFatura(faturaDTO.getIdFatura());
            faturaAux = (FaturaDTO) crudDao.restore(faturaAux);
            if (faturaAux != null) {
                faturaDTO.setDataCriacao(faturaAux.getDataCriacao());
            }

            double valorGlosado = 0;

            // Executa operacoes pertinentes ao negocio.
            crudDao.update(model);
            faturaApuracaoANSDao.deleteByIdFatura(faturaDTO.getIdFatura());
            if (faturaDTO.getColItens() != null) {
                for (final Iterator it = faturaDTO.getColItens().iterator(); it.hasNext();) {
                    final FaturaApuracaoANSDTO faturaApuracaoANSDTO = (FaturaApuracaoANSDTO) it.next();
                    faturaApuracaoANSDTO.setIdFatura(faturaDTO.getIdFatura());
                    faturaApuracaoANSDTO.setDataApuracao(UtilDatas.getDataAtual());

                    if (faturaApuracaoANSDTO.getValorGlosa() != null) {
                        valorGlosado = valorGlosado + faturaApuracaoANSDTO.getValorGlosa().doubleValue();
                    }

                    faturaApuracaoANSDao.create(faturaApuracaoANSDTO);
                }
            }

            faturaOSDao.deleteByIdFatura(faturaDTO.getIdFatura());
            if (faturaDTO.getIdOSFatura() != null) {
                for (int i = 0; i < faturaDTO.getIdOSFatura().length; i++) {
                    final FaturaOSDTO faturaOSDTO = new FaturaOSDTO();
                    faturaOSDTO.setIdFatura(faturaDTO.getIdFatura());
                    faturaOSDTO.setIdOs(faturaDTO.getIdOSFatura()[i]);
                    faturaOSDao.create(faturaOSDTO);
                }
            }

            final OSDao osSDao = new OSDao();
            final GlosaOSDao glosaOSDao = new GlosaOSDao();
            final Collection colOSs = osSDao.listOSByIds(faturaDTO.getIdContrato(), faturaDTO.getIdOSFatura());
            double valorPrevistoOS = 0;
            double valorExecutadoOS = 0;
            if (colOSs != null) {
                for (final Iterator itOs = colOSs.iterator(); itOs.hasNext();) {
                    final OSDTO osDto = (OSDTO) itOs.next();
                    // if (osDto.getGlosaOS() != null){
                    // valorGlosado = valorGlosado + osDto.getGlosaOS().doubleValue();
                    // }
                    if (osDto.getCustoOS() != null) {
                        valorPrevistoOS = valorPrevistoOS + osDto.getCustoOS().doubleValue();
                    }
                    if (osDto.getExecutadoOS() != null) {
                        valorExecutadoOS = valorExecutadoOS + osDto.getExecutadoOS().doubleValue();
                    }
                    final Collection colGlosasOS = glosaOSDao.findByIdOs(osDto.getIdOS());
                    if (colGlosasOS != null) {
                        for (final Iterator it = colGlosasOS.iterator(); it.hasNext();) {
                            final GlosaOSDTO glosaOSDTO = (GlosaOSDTO) it.next();
                            if (glosaOSDTO.getCustoGlosa() != null) {
                                valorGlosado = valorGlosado + glosaOSDTO.getCustoGlosa().doubleValue();
                            }
                        }
                    }
                }
            }

            faturaDTO.setValorSomaGlosasOS(valorGlosado);
            faturaDTO.setValorPrevistoSomaOS(valorPrevistoOS);
            faturaDTO.setValorExecutadoSomaOS(valorExecutadoOS);
            crudDao.update(faturaDTO);

            // Faz commit e fecha a transacao.
            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
    }

    @Override
    public void updateSituacao(final Integer idFatura, final String situacao, final String aprovacaoGestor, final String aprovacaoFiscal) throws Exception {
        this.getDao().updateSituacao(idFatura, situacao, aprovacaoGestor, aprovacaoFiscal);
    }

}
