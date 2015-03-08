package br.com.centralit.citcorpore.negocio;
import java.sql.Date;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.NetMapDTO;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;
public interface NetMapService extends CrudService {
    
    public List<NetMapDTO> verificarExistenciaIp(NetMapDTO netMapDTO) throws ServiceException, LogicException, Exception;
    public List<NetMapDTO> listIpByDataInventario(Date dataInventario) throws ServiceException, LogicException, Exception;
    public List<NetMapDTO> listIp() throws Exception;
    public Collection existeByNome(Date dataInventario, String nome) throws Exception;
}
