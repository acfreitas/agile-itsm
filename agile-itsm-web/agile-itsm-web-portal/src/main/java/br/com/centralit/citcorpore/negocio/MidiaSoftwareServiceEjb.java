package br.com.centralit.citcorpore.negocio;

import java.util.List;

import br.com.centralit.citcorpore.bean.MidiaSoftwareChaveDTO;
import br.com.centralit.citcorpore.bean.MidiaSoftwareDTO;
import br.com.centralit.citcorpore.integracao.MidiaSoftwareChaveDao;
import br.com.centralit.citcorpore.integracao.MidiaSoftwareDAO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;

public class MidiaSoftwareServiceEjb extends CrudServiceImpl implements MidiaSoftwareService {

    private MidiaSoftwareDAO dao;

    @Override
    protected MidiaSoftwareDAO getDao() {
        if (dao == null) {
            dao = new MidiaSoftwareDAO();
        }
        return dao;
    }

    @Override
    public IDto create(final IDto model) throws ServiceException, LogicException {
        MidiaSoftwareDTO midiaSoftwareDTO = (MidiaSoftwareDTO) model;
        final MidiaSoftwareDAO dao = this.getDao();

        final MidiaSoftwareChaveDao midiaSoftwareChaveDao = new MidiaSoftwareChaveDao();

        final TransactionControler tc = new TransactionControlerImpl(dao.getAliasDB());

        try {
            midiaSoftwareChaveDao.setTransactionControler(tc);
            tc.start();
            midiaSoftwareDTO = (MidiaSoftwareDTO) dao.create(model);

            if (midiaSoftwareDTO.getMidiaSoftwareChaves() != null && !midiaSoftwareDTO.getMidiaSoftwareChaves().isEmpty()) {
                for (final MidiaSoftwareChaveDTO midiaSoftwareChaveDTO : midiaSoftwareDTO.getMidiaSoftwareChaves()) {
                    midiaSoftwareChaveDTO.setIdMidiaSoftware(midiaSoftwareDTO.getIdMidiaSoftware());
                    midiaSoftwareChaveDao.create(midiaSoftwareChaveDTO);
                }
            }
            tc.commit();
            tc.close();
        } catch (final Exception e) {
            e.printStackTrace();
            this.rollbackTransaction(tc, e);
        }

        return midiaSoftwareDTO;
    }

    @Override
    public void update(final IDto model) throws ServiceException, LogicException {
        final MidiaSoftwareDTO midiaSoftwareDTO = (MidiaSoftwareDTO) model;
        final MidiaSoftwareDAO dao = this.getDao();

        final MidiaSoftwareChaveDao midiaSoftwareChaveDao = new MidiaSoftwareChaveDao();

        final TransactionControler tc = new TransactionControlerImpl(dao.getAliasDB());

        try {
            midiaSoftwareChaveDao.setTransactionControler(tc);
            tc.start();
            dao.update(midiaSoftwareDTO);
            midiaSoftwareChaveDao.deleteByIdMidiaSoftware(midiaSoftwareDTO.getIdMidiaSoftware());
            if (midiaSoftwareDTO.getMidiaSoftwareChaves() != null && !midiaSoftwareDTO.getMidiaSoftwareChaves().isEmpty()) {
                for (final MidiaSoftwareChaveDTO midiaSoftwareChaveDTO : midiaSoftwareDTO.getMidiaSoftwareChaves()) {
                    midiaSoftwareChaveDTO.setIdMidiaSoftware(midiaSoftwareDTO.getIdMidiaSoftware());
                    midiaSoftwareChaveDao.create(midiaSoftwareChaveDTO);
                }
            }
            tc.commit();
            tc.close();
        } catch (final Exception e) {
            e.printStackTrace();
            this.rollbackTransaction(tc, e);
        }
    }

    @Override
    public IDto restore(final IDto model) throws ServiceException, LogicException {
        MidiaSoftwareDTO midiaSoftwareDTO = null;
        try {
            midiaSoftwareDTO = (MidiaSoftwareDTO) this.getDao().restore(model);

            final MidiaSoftwareChaveDTO midiaSoftwareChaveDTO = new MidiaSoftwareChaveDTO();

            midiaSoftwareChaveDTO.setIdMidiaSoftware(midiaSoftwareDTO.getIdMidiaSoftware());
            midiaSoftwareDTO.setMidiaSoftwareChaves((List<MidiaSoftwareChaveDTO>) new MidiaSoftwareChaveDao().findByMidiaSoftware(midiaSoftwareDTO.getIdMidiaSoftware()));

        } catch (final Exception e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
        return midiaSoftwareDTO;
    }

    @Override
    public boolean consultarMidiasAtivas(final MidiaSoftwareDTO midiaSoftware) throws Exception {
        return this.getDao().consultarMidiasAtivas(midiaSoftware);
    }

}
