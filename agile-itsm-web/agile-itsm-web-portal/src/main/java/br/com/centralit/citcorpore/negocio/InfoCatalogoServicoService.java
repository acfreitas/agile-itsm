package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.InfoCatalogoServicoDTO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudService;

/**
 * 
 * @author pedro
 *
 */
public interface InfoCatalogoServicoService extends CrudService {
	
	public Collection<InfoCatalogoServicoDTO> findByCatalogoServico(Integer idCatalogoServico) throws ServiceException, Exception;
	
	public boolean findByContratoServico(Integer idContratoServico) throws ServiceException, Exception;
	
	public InfoCatalogoServicoDTO findById(Integer idInfoCatalogo) throws ServiceException, Exception;

	@SuppressWarnings("rawtypes")
	public void gravaInformacoesGED(Collection colArquivosUpload, Integer idEmpresa, InfoCatalogoServicoDTO infoCatalogoServicoDTO,	TransactionControler tc) throws Exception;
}
