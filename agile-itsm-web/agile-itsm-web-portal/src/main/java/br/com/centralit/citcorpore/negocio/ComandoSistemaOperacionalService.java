package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.ComandoSistemaOperacionalDTO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

/**
 * @author ygor.magalhaes
 * 
 */
public interface ComandoSistemaOperacionalService extends CrudService {

    public ComandoSistemaOperacionalDTO pegarComandoSO(String so, String tipoExecucao) throws ServiceException;
    
    public boolean pesquisarExistenciaComandoSO(ComandoSistemaOperacionalDTO comandoSODTO) throws ServiceException;
}
