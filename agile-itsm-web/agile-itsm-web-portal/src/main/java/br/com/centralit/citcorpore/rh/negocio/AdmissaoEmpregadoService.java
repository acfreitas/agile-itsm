package br.com.centralit.citcorpore.rh.negocio;

import br.com.centralit.citcorpore.rh.bean.AdmissaoEmpregadoDTO;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

public interface AdmissaoEmpregadoService extends CrudService {

    AdmissaoEmpregadoDTO calcularCustos(final AdmissaoEmpregadoDTO admissaoEmpregado) throws ServiceException, LogicException;

}
