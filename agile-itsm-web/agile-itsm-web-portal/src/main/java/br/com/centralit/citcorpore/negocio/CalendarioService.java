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
	 * Método para verificar se existe calendário com a mesma descrição
	 * 
	 * @author rodrigo.oliveira
	 * @param calendarioDTO
	 * @return Se caso exista calendario com a mesma descrição retorna true
	 * @throws Exception
	 */
	public boolean verificaSeExisteCalendario(CalendarioDTO calendarioDTO) throws Exception;

	public Object verificaSePermiteExcluir(DocumentHTML document, HttpServletRequest request, CalendarioDTO calendario) throws Exception;
	
	public CalendarioDTO recuperaCalendario(Integer idCalendario) throws Exception;
	
	public JornadaTrabalhoDTO recuperaJornada(CalendarioDTO calendarioDto, Date dataRef) throws Exception;

}
