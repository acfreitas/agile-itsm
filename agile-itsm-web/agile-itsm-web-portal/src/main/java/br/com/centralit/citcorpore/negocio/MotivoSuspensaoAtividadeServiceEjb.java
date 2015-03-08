package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.MotivoSuspensaoAtividadeDTO;
import br.com.centralit.citcorpore.integracao.MotivoSuspensaoAtividadeDao;
import br.com.citframework.service.CrudServiceImpl;

@SuppressWarnings("rawtypes")
public class MotivoSuspensaoAtividadeServiceEjb extends CrudServiceImpl implements MotivoSuspensaoAtividadeService {

    private MotivoSuspensaoAtividadeDao dao;

    @Override
    protected MotivoSuspensaoAtividadeDao getDao() {
        if (dao == null) {
            dao = new MotivoSuspensaoAtividadeDao();
        }
        return dao;
    }

    @Override
    public boolean jaExisteRegistroComMesmoNome(final MotivoSuspensaoAtividadeDTO motivoSuspensaoAtividadeDTO) {
        try {
            return this.getDao().jaExisteRegistroComMesmoNome(motivoSuspensaoAtividadeDTO);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Collection listarMotivosSuspensaoAtividadeAtivos() throws Exception {
        return this.getDao().listarMotivosSuspensaoAtividadeAtivos();
    }

}
