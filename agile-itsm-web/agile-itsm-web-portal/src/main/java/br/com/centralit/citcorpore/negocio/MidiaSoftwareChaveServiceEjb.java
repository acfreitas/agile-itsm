package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.MidiaSoftwareChaveDTO;
import br.com.centralit.citcorpore.integracao.MidiaSoftwareChaveDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class MidiaSoftwareChaveServiceEjb extends CrudServiceImpl implements MidiaSoftwareChaveService {

    private MidiaSoftwareChaveDao dao;

    @Override
    protected MidiaSoftwareChaveDao getDao() {
        if (dao == null) {
            dao = new MidiaSoftwareChaveDao();
        }
        return dao;
    }

    @Override
    public Collection<MidiaSoftwareChaveDTO> findByMidiaSoftware(final Integer idMidiaSoftware) throws ServiceException, Exception {
        return this.getDao().findByMidiaSoftware(idMidiaSoftware);
    }

    @Override
    public void deleteByIdMidiaSoftware(final Integer idMidiaSoftware) throws Exception {
        this.getDao().deleteByIdMidiaSoftware(idMidiaSoftware);

    }

}
