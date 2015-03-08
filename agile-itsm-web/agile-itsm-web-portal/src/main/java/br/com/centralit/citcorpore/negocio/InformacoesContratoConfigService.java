package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudService;

public interface InformacoesContratoConfigService extends CrudService {
    public Collection getAtivos() throws Exception;
    public Collection getCollectonHierarquica(Integer idEmpresa, boolean acrescentarInativos) throws Exception;
    public Collection findSemPai(Integer idEmpresa) throws Exception;
    public Collection findByPai(Integer idCentroCustoPai) throws Exception;
    public Collection findByNome(String nome) throws Exception;
}
