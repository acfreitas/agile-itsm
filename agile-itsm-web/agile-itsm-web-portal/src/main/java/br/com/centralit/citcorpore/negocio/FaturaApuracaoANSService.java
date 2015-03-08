package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.FaturaApuracaoANSDTO;
import br.com.citframework.service.CrudService;

public interface FaturaApuracaoANSService extends CrudService {
    public Collection findByIdFatura(Integer parm) throws Exception;

    public void deleteByIdFatura(Integer parm) throws Exception;

    public FaturaApuracaoANSDTO findByIdAcordoNivelServicoContrato(Integer parm) throws Exception;

    public void deleteByIdAcordoNivelServicoContrato(Integer parm) throws Exception;
}
