package br.com.centralit.citcorpore.negocio;

import java.util.List;

import br.com.centralit.citcorpore.bean.AnaliseTendenciasDTO;
import br.com.centralit.citcorpore.bean.TendenciaDTO;
import br.com.centralit.citcorpore.bean.TendenciaGanttDTO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

/**
 * @author euler.ramos
 *
 */
public interface AnaliseTendenciasService extends CrudService {
	public List<TendenciaDTO> buscarTendenciasServico(AnaliseTendenciasDTO analiseTendenciasDTO) throws ServiceException;
	
	public List<TendenciaDTO> buscarTendenciasCausa(AnaliseTendenciasDTO analiseTendenciasDTO) throws ServiceException;
	
	public List<TendenciaDTO> buscarTendenciasItemConfiguracao(AnaliseTendenciasDTO analiseTendenciasDTO) throws ServiceException;
	
	public List<TendenciaGanttDTO> listarGraficoGanttServico(AnaliseTendenciasDTO analiseTendenciasDTO, Integer idServico) throws ServiceException;
	
	public List<TendenciaGanttDTO> listarGraficoGanttCausa(AnaliseTendenciasDTO analiseTendenciasDTO, Integer idCausa) throws ServiceException;

	public List<TendenciaGanttDTO> listarGraficoGanttItemConfiguracao(AnaliseTendenciasDTO analiseTendenciasDTO, Integer idItemConfiguracao) throws ServiceException;
}
