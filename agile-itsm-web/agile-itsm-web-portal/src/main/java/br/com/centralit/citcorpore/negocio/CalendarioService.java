package br.com.centralit.citcorpore.negocio;
import java.sql.Date;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.CalendarioDTO;
import br.com.centralit.citcorpore.bean.JornadaTrabalhoDTO;
import br.com.citframework.service.CrudService;
public interface CalendarioService extends CrudService {
	
	public boolean jornadaDeTrabalhoEmUso(JornadaTrabalhoDTO jornadaTrabalhoDTO) throws Exception;
	
	/**
	 * M�todo para verificar se existe calend�rio com a mesma descri��o
	 * 
	 * @author rodrigo.oliveira
	 * @param calendarioDTO
	 * @return Se caso exista calendario com a mesma descri��o retorna true
	 * @throws Exception
	 */
	public boolean verificaSeExisteCalendario(CalendarioDTO calendarioDTO) throws Exception;

	public Object verificaSePermiteExcluir(DocumentHTML document, HttpServletRequest request, CalendarioDTO calendario) throws Exception;
	
	public CalendarioDTO recuperaCalendario(Integer idCalendario) throws Exception;
	
	public JornadaTrabalhoDTO recuperaJornada(CalendarioDTO calendarioDto, Date dataRef) throws Exception;

}
