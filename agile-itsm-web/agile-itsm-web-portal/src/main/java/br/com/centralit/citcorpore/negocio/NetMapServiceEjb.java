package br.com.centralit.citcorpore.negocio;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.NetMapDTO;
import br.com.centralit.citcorpore.integracao.NetMapDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class NetMapServiceEjb extends CrudServiceImpl implements NetMapService {

    private NetMapDao dao;

    @Override
    protected NetMapDao getDao() {
        if (dao == null) {
            dao = new NetMapDao();
        }
        return dao;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<NetMapDTO> verificarExistenciaIp(final NetMapDTO netMapDTO) throws ServiceException, LogicException, Exception {
        try {
            return (List<NetMapDTO>) this.getDao().verificarExistenciaIp(netMapDTO);
        } catch (final Exception e) {
            e.getStackTrace();
        }
        return (List<NetMapDTO>) this.getDao().verificarExistenciaIp(netMapDTO);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<NetMapDTO> listIpByDataInventario(final Date dataInventario) throws ServiceException, LogicException, Exception {
        return (List<NetMapDTO>) this.getDao().listIpByDataInventario(dataInventario);
    }

    @Override
    public List<NetMapDTO> listIp() throws Exception {
        return this.getDao().listIp();
    }

    @Override
    public Collection existeByNome(final Date dataInventario, final String nome) throws Exception {
        return this.getDao().existeByNome(dataInventario, nome);
    }
}
