package br.com.centralit.citcorpore.metainfo.complementos;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;

import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.negocio.ServicoService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;

public class ComplementoServicoContrato {
    public String execute(JspWriter out, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
	ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
	String retorno = "";
	Integer ATIVO = 1;
	
	Collection<ServicoDTO> col = servicoService.findByIdSituacaoServico(new Integer(ATIVO));
	retorno = retorno + "<table>";
	for (ServicoDTO servicoDTO : col){
		retorno = retorno + "<tr>";
		retorno = retorno + "<td>";
			retorno = retorno + "<input type='checkbox' name='idServico' class='valid[Check] Description[notificacao.servicos]' id='idServico"+servicoDTO.getIdServico()+"' value='" + servicoDTO.getIdServico() + "'/>";
		retorno = retorno + "</td>";		
		retorno = retorno + "<td>";
			retorno = retorno + servicoDTO.getNomeServico();
		retorno = retorno + "</td>";
		retorno = retorno + "</tr>";
	}
	retorno = retorno + "</table>";
	
	return retorno;
    }
}
