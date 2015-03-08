package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.EscalonamentoDTO;
import br.com.centralit.citcorpore.bean.RegraEscalonamentoDTO;
import br.com.centralit.citcorpore.integracao.EscalonamentoDAO;
import br.com.centralit.citcorpore.integracao.RegraEscalonamentoDAO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilDatas;

public class RegraEscalonamentoServiceEjb extends CrudServiceImpl implements RegraEscalonamentoService {

    private RegraEscalonamentoDAO dao;

    @Override
    protected RegraEscalonamentoDAO getDao() {
        if (dao == null) {
            dao = new RegraEscalonamentoDAO();
        }
        return dao;
    }

    @Override
    public IDto create(final IDto model) throws ServiceException, LogicException {
        RegraEscalonamentoDTO regraEscalonamentoDTO = (RegraEscalonamentoDTO) model;
        final RegraEscalonamentoDAO regraEscalonamentoDAO = this.getDao();
        final EscalonamentoDAO escalonamentoDAO = new EscalonamentoDAO();

        final TransactionControler tc = new TransactionControlerImpl(regraEscalonamentoDAO.getAliasDB());
        try {
            regraEscalonamentoDAO.setTransactionControler(tc);
            escalonamentoDAO.setTransactionControler(tc);

            tc.start();

            regraEscalonamentoDTO.setDataInicio(UtilDatas.getDataAtual());
            regraEscalonamentoDTO = (RegraEscalonamentoDTO) regraEscalonamentoDAO.create(regraEscalonamentoDTO);

            this.mantemEscalonamentos(regraEscalonamentoDTO, escalonamentoDAO);

            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
        return regraEscalonamentoDTO;
    }

    @Override
    public void update(final IDto model) throws ServiceException, LogicException {
        final RegraEscalonamentoDTO regraEscalonamentoDTO = (RegraEscalonamentoDTO) model;
        final RegraEscalonamentoDAO regraEscalonamentoDAO = this.getDao();
        final EscalonamentoDAO escalonamentoDAO = new EscalonamentoDAO();

        final TransactionControler tc = new TransactionControlerImpl(regraEscalonamentoDAO.getAliasDB());
        try {
            regraEscalonamentoDAO.setTransactionControler(tc);
            escalonamentoDAO.setTransactionControler(tc);

            tc.start();

            regraEscalonamentoDAO.update(regraEscalonamentoDTO);

            this.mantemEscalonamentos(regraEscalonamentoDTO, escalonamentoDAO);

            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
    }

    @Override
    public void delete(final IDto model) throws ServiceException, LogicException {
        final RegraEscalonamentoDTO regraEscalonamentoDTO = (RegraEscalonamentoDTO) model;
        final RegraEscalonamentoDAO regraEscalonamentoDAO = this.getDao();
        final EscalonamentoDAO escalonamentoDAO = new EscalonamentoDAO();
        final TransactionControler tc = new TransactionControlerImpl(regraEscalonamentoDAO.getAliasDB());
        try {
            regraEscalonamentoDAO.setTransactionControler(tc);
            escalonamentoDAO.setTransactionControler(tc);

            tc.start();

            regraEscalonamentoDTO.setDataFim(UtilDatas.getDataAtual());

            if (regraEscalonamentoDTO.getColEscalonamentoDTOs() != null) {
                regraEscalonamentoDTO.getColEscalonamentoDTOs().clear();
            }
            regraEscalonamentoDAO.update(regraEscalonamentoDTO);

            this.mantemEscalonamentos(regraEscalonamentoDTO, escalonamentoDAO);

            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
    }

    private void mantemEscalonamentos(final RegraEscalonamentoDTO regraEscalonamentoDTO, final EscalonamentoDAO escalonamentoDAO) throws Exception {
        escalonamentoDAO.gravarDataFim(regraEscalonamentoDTO.getIdRegraEscalonamento());
        final Collection<EscalonamentoDTO> colEscalonamentoDTOs = regraEscalonamentoDTO.getColEscalonamentoDTOs();
        if (colEscalonamentoDTOs != null && colEscalonamentoDTOs.size() > 0) {
            for (final EscalonamentoDTO escalonamentoDTO : colEscalonamentoDTOs) {
                escalonamentoDTO.setIdRegraEscalonamento(regraEscalonamentoDTO.getIdRegraEscalonamento());
                escalonamentoDTO.setDataInicio(UtilDatas.getDataAtual());
                escalonamentoDAO.create(escalonamentoDTO);
            }
        }
    }

}
