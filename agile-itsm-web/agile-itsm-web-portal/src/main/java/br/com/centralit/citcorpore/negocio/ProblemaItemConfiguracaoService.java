package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ProblemaItemConfiguracaoDTO;
import br.com.citframework.service.CrudService;


@SuppressWarnings("rawtypes")
public interface ProblemaItemConfiguracaoService extends CrudService {
	public Collection findByIdProblemaItemConfiguracao(Integer parm) throws Exception;

	public void deleteByIdProblemaItemConfiguracao(Integer parm) throws Exception;

	public Collection findByIdProblema(Integer parm) throws Exception;

	public void deleteByIdProblema(Integer parm) throws Exception;

	public Collection findByIdItemConfiguracao(Integer parm) throws Exception;

	public void deleteByIdItemConfiguracao(Integer parm) throws Exception;
	
	public ProblemaItemConfiguracaoDTO restoreByIdProblema(Integer idProblema) throws Exception;
}
