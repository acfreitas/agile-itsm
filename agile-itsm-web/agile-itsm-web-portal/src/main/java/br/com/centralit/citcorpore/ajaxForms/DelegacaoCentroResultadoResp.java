package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.CentroResultadoDTO;
import br.com.centralit.citcorpore.bean.DelegacaoCentroResultadoDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.ResponsavelCentroResultadoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CentroResultadoService;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.ResponsavelCentroResultadoService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class DelegacaoCentroResultadoResp extends DelegacaoCentroResultado {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		
        EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, WebUtil.getUsuarioSistema(request));
		EmpregadoDTO empregadoDto = empregadoService.restoreByIdEmpregado(usuario.getIdEmpregado());
		if (empregadoDto == null) {
			document.alert(UtilI18N.internacionaliza(request, "delegacaoCentroResultado.empregadoNaoEncontrado"));
			return;
		}

		ResponsavelCentroResultadoService responsavelCentroResultadoService = (ResponsavelCentroResultadoService) ServiceLocator.getInstance().getService(ResponsavelCentroResultadoService.class, null);
		Collection<ResponsavelCentroResultadoDTO> colResp = responsavelCentroResultadoService.findByIdResponsavel(empregadoDto.getIdEmpregado());
		if (colResp == null || colResp.isEmpty()) {
			document.alert(UtilI18N.internacionaliza(request, "delegacaoCentroResultado.empregadoNaoPossuiCentroResultado"));
			return;			
		}
		
        DelegacaoCentroResultadoDTO delegacaoCentroResultadoDto = (DelegacaoCentroResultadoDTO) document.getBean();
        delegacaoCentroResultadoDto.setNomeResponsavel(empregadoDto.getNome());
        delegacaoCentroResultadoDto.setIdResponsavel(empregadoDto.getIdEmpregado());
		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(delegacaoCentroResultadoDto);
		
		Collection colCCusto = new ArrayList();
		CentroResultadoService centroResultadoService = (CentroResultadoService) ServiceLocator.getInstance().getService(CentroResultadoService.class, WebUtil.getUsuarioSistema(request));
		
		for (ResponsavelCentroResultadoDTO respDto : colResp) {
			CentroResultadoDTO centroResultadoDto = new CentroResultadoDTO();
			centroResultadoDto.setIdCentroResultado(respDto.getIdCentroResultado());
			centroResultadoDto = (CentroResultadoDTO) centroResultadoService.restore(centroResultadoDto);
			if (centroResultadoDto != null)
				colCCusto.add(centroResultadoDto);
		}
		
        HTMLSelect idCentroResultado = (HTMLSelect) document.getSelectById("idCentroResultado");
        idCentroResultado.removeAllOptions();
        idCentroResultado.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
        if(colCCusto != null && !colCCusto.isEmpty())
        	idCentroResultado.addOptions(colCCusto, "idCentroResultado", "nomeHierarquizado", null);

	}

}