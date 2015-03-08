package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ImagemHistoricoDTO;
import br.com.citframework.service.CrudService;

public interface ImagemHistoricoService extends CrudService {
	public Collection listByIdContrato(Integer idContrato) throws Exception;
	public Collection listByIdContratoAndAba(Integer idContrato, String aba) throws Exception;
	public ImagemHistoricoDTO findByNomeArquivo(final ImagemHistoricoDTO filter) 
	        throws Exception;
}