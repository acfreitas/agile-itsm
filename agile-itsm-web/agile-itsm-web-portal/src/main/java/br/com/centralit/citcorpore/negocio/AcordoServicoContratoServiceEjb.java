package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.AcordoNivelServicoDTO;
import br.com.centralit.citcorpore.bean.AcordoServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.integracao.AcordoNivelServicoDao;
import br.com.centralit.citcorpore.integracao.AcordoServicoContratoDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilDatas;

@SuppressWarnings("rawtypes")
public class AcordoServicoContratoServiceEjb extends CrudServiceImpl implements AcordoServicoContratoService {

    private AcordoServicoContratoDao dao;

    @Override
    protected AcordoServicoContratoDao getDao() {
        if (dao == null) {
            dao = new AcordoServicoContratoDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdAcordoNivelServico(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdAcordoNivelServico(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdAcordoNivelServicoAndContrato(final Integer idAcordoNivelServico, final Integer idContrato) throws Exception {
        try {
            final List<AcordoServicoContratoDTO> lista = this.getDao().findBylistByIdAcordoNivelServicoAndContrato(idAcordoNivelServico, idContrato);
            if (lista != null) {
                for (final AcordoServicoContratoDTO acordoServicoContratoDTO : lista) {
                    this.getDao().deleteByIdAcordoServicoContrato(acordoServicoContratoDTO.getIdAcordoServicoContrato());
                }
            }
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdServicoContrato(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdServicoContrato(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdServicoContrato(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdServicoContrato(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public IDto create(final IDto model) throws ServiceException, LogicException {
        final AcordoServicoContratoDTO acordoServicoContratoDTO = (AcordoServicoContratoDTO) model;
        AcordoNivelServicoDTO acordoNivelServico = new AcordoNivelServicoDTO();
        acordoNivelServico.setIdAcordoNivelServico(acordoServicoContratoDTO.getIdAcordoNivelServico());
        final TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());

        final AcordoNivelServicoDao acordoNivelServicoDao = new AcordoNivelServicoDao();
        try {
            tc.start();

            acordoNivelServicoDao.setTransactionControler(tc);

            if (acordoServicoContratoDTO.getListaServicoContrato() != null) {
                acordoNivelServico = (AcordoNivelServicoDTO) acordoNivelServicoDao.restore(acordoNivelServico);

                for (final ServicoContratoDTO servicoContratoDTO : acordoServicoContratoDTO.getListaServicoContrato()) {

                    if (acordoServicoContratoDTO.getHabilitado() != null && acordoNivelServico.getTipo() != null && acordoNivelServico.getTipo().equalsIgnoreCase("T")) {
                        final List<AcordoServicoContratoDTO> acordoServicoContratoDTOs = this.getDao().listAtivoByIdServicoContrato(servicoContratoDTO.getIdServicoContrato(), "T");
                        if (acordoServicoContratoDTOs != null) {
                            for (final AcordoServicoContratoDTO acordo : acordoServicoContratoDTOs) {
                                acordo.setHabilitado("N");
                                this.getDao().updateNotNull(acordo);
                            }
                        }
                    }

                    final AcordoServicoContratoDTO obj = new AcordoServicoContratoDTO();
                    obj.setIdServicoContrato(servicoContratoDTO.getIdServicoContrato());
                    obj.setIdAcordoNivelServico(acordoServicoContratoDTO.getIdAcordoNivelServico());
                    if (acordoServicoContratoDTO.getHabilitado() != null && acordoNivelServico.getTipo() != null && acordoNivelServico.getTipo().equalsIgnoreCase("T")) {
                        obj.setHabilitado("S");
                    }
                    obj.setDataCriacao(UtilDatas.getDataAtual());
                    obj.setDataInicio(UtilDatas.getDataAtual());
                    super.create(obj);
                }
            }

            tc.commit();

        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
            throw new ServiceException(e);
        } finally {
            tc.closeQuietly();
        }

        return acordoServicoContratoDTO;
    }

    @Override
    public void update(final IDto model) throws ServiceException, LogicException {
        final AcordoServicoContratoDTO acordoServicoContratoDTO = (AcordoServicoContratoDTO) model;
        AcordoNivelServicoDTO acordoNivelServico = new AcordoNivelServicoDTO();
        acordoNivelServico.setIdAcordoNivelServico(acordoServicoContratoDTO.getIdAcordoNivelServico());
        final TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());

        final AcordoNivelServicoDao acordoNivelServicoDao = new AcordoNivelServicoDao();
        try {
            tc.start();

            acordoNivelServicoDao.setTransactionControler(tc);

            this.deleteByIdAcordoNivelServicoAndContrato(acordoServicoContratoDTO.getIdAcordoNivelServico(), acordoServicoContratoDTO.getIdContrato());
            if (acordoServicoContratoDTO.getListaServicoContrato() != null) {
                acordoNivelServico = (AcordoNivelServicoDTO) acordoNivelServicoDao.restore(acordoNivelServico);

                for (final ServicoContratoDTO servicoContratoDTO : acordoServicoContratoDTO.getListaServicoContrato()) {

                    if (acordoServicoContratoDTO.getHabilitado() != null && acordoNivelServico.getTipo() != null && acordoNivelServico.getTipo().equalsIgnoreCase("T")) {
                        final List<AcordoServicoContratoDTO> acordoServicoContratoDTOs = this.getDao().listAtivoByIdServicoContrato(servicoContratoDTO.getIdServicoContrato(), "T");
                        if (acordoServicoContratoDTOs != null) {
                            for (final AcordoServicoContratoDTO acordo : acordoServicoContratoDTOs) {
                                acordo.setHabilitado("N");
                                this.getDao().updateNotNull(acordo);
                            }
                        }
                    }

                    final AcordoServicoContratoDTO obj = new AcordoServicoContratoDTO();
                    obj.setIdServicoContrato(servicoContratoDTO.getIdServicoContrato());
                    obj.setIdAcordoNivelServico(acordoServicoContratoDTO.getIdAcordoNivelServico());
                    if (acordoServicoContratoDTO.getHabilitado() != null && acordoNivelServico.getTipo() != null && acordoNivelServico.getTipo().equalsIgnoreCase("T")) {
                        obj.setHabilitado("S");
                    }
                    obj.setDataCriacao(UtilDatas.getDataAtual());
                    obj.setDataInicio(UtilDatas.getDataAtual());
                    super.create(obj);
                }
            }

            tc.commit();

        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
            throw new ServiceException(e);
        } finally {
            tc.closeQuietly();
        }
    }

    @Override
    public AcordoServicoContratoDTO findAtivoByIdServicoContrato(final Integer idServicoContrato, final String tipo) throws Exception {
        try {
            return this.getDao().findAtivoByIdServicoContrato(idServicoContrato, tipo);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean existeAcordoServicoContrato(final Integer idAcordoNivelServico, final Integer idContrato) throws Exception {
        try {
            return this.getDao().existeAcordoServicoContrato(idAcordoNivelServico, idContrato);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection<AcordoServicoContratoDTO> findByIdAcordoNivelServicoIdServicoContrato(final Integer idAcordoNivelServico, final Integer idServicoContrato) throws Exception {
        try {
            return this.getDao().findByIdAcordoNivelServicoIdServicoContrato(idAcordoNivelServico, idServicoContrato);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateNotNull(final IDto obj) throws Exception {
        final AcordoServicoContratoDTO acordoServicoContratoDTO = (AcordoServicoContratoDTO) obj;
        try {
            if (acordoServicoContratoDTO.getHabilitado().equalsIgnoreCase("S")) {
                final List<AcordoServicoContratoDTO> lista = this.getDao().listAtivoByIdServicoContrato(acordoServicoContratoDTO.getIdAcordoServicoContrato(),
                        acordoServicoContratoDTO.getIdServicoContrato(), "T");
                if (lista != null) {
                    for (final AcordoServicoContratoDTO acordo : lista) {
                        acordo.setHabilitado("N");
                        this.getDao().updateNotNull(acordo);
                    }
                }
            }
            this.getDao().updateNotNull(obj);

        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<AcordoServicoContratoDTO> listAtivoByIdServicoContrato(final Integer idAcordoServicoContrato, final Integer idServicoContrato, final String tipo) throws Exception {
        try {
            return this.getDao().listAtivoByIdServicoContrato(idAcordoServicoContrato, idServicoContrato, tipo);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
