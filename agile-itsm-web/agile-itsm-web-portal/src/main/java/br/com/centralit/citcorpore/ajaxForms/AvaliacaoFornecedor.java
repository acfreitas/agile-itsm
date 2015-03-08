package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.AvaliacaoFornecedorDTO;
import br.com.centralit.citcorpore.bean.AvaliacaoReferenciaFornecedorDTO;
import br.com.centralit.citcorpore.bean.CriterioAvaliacaoFornecedorDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.EnderecoDTO;
import br.com.centralit.citcorpore.bean.FornecedorDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.AvaliacaoFornecedorService;
import br.com.centralit.citcorpore.negocio.AvaliacaoReferenciaFornecedorService;
import br.com.centralit.citcorpore.negocio.CriterioAvaliacaoFornecedorService;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.EnderecoService;
import br.com.centralit.citcorpore.negocio.FornecedorService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings("rawtypes")
public class AvaliacaoFornecedor extends AjaxFormAction {

	UsuarioDTO usuario;

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		document.focusInFirstActivateField(null);

	}

	@Override
	public Class getBeanClass() {
		return AvaliacaoFornecedorDTO.class;
	}

	/**
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 *             Metodo de salvar
	 * @author thays.araujo
	 */

	@SuppressWarnings("unchecked")
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		AvaliacaoFornecedorDTO avaliacaoFornecedorDto = (AvaliacaoFornecedorDTO) document.getBean();

		AvaliacaoFornecedorService avaliacaoFornecedorService = (AvaliacaoFornecedorService) ServiceLocator.getInstance().getService(AvaliacaoFornecedorService.class, null);

		avaliacaoFornecedorDto.setListCriterioAvaliacaoFornecedor((br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(CriterioAvaliacaoFornecedorDTO.class,
				"listCriteriosQualidadeSerializado", request) == null ? new ArrayList<CriterioAvaliacaoFornecedorDTO>() : br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(
				CriterioAvaliacaoFornecedorDTO.class, "listCriteriosQualidadeSerializado", request)));

		avaliacaoFornecedorDto.setListAvaliacaoReferenciaFornecedor((br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(AvaliacaoReferenciaFornecedorDTO.class,
				"listAprovacaoReferenciaSerializado", request) == null ? new ArrayList<AvaliacaoReferenciaFornecedorDTO>() : br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(
				AvaliacaoReferenciaFornecedorDTO.class, "listAprovacaoReferenciaSerializado", request)));

		if ((avaliacaoFornecedorDto.getListAvaliacaoReferenciaFornecedor() == null || avaliacaoFornecedorDto.getListAvaliacaoReferenciaFornecedor().isEmpty())
				&& (avaliacaoFornecedorDto.getListCriterioAvaliacaoFornecedor() == null || avaliacaoFornecedorDto.getListCriterioAvaliacaoFornecedor().isEmpty())) {
			document.alert(UtilI18N.internacionaliza(request, "avaliacaoFornecedor.obrigatoriedade"));
			return;
		}

		avaliacaoFornecedorDto.setDecisaoQualificacao("Q");
		if (avaliacaoFornecedorDto.getIdAvaliacaoFornecedor() == null || avaliacaoFornecedorDto.getIdAvaliacaoFornecedor() == 0) {

			avaliacaoFornecedorService.create(avaliacaoFornecedorDto);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));

		} else {

			avaliacaoFornecedorService.update(avaliacaoFornecedorDto);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}

		HTMLForm form = document.getForm("form");
		form.clear();
		document.executeScript("limpar()");
		/*HTMLTable tblCriterio = document.getTableById("tblCriterio");
		tblCriterio.deleteAllRows();
		HTMLTable tblAprovacao = document.getTableById("tblAprovacao");
		tblAprovacao.deleteAllRows();*/
	}

	public void preencheFornecedor(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		AvaliacaoFornecedorDTO avaliacaoFornecedordto = (AvaliacaoFornecedorDTO) document.getBean();
		FornecedorService fornecedorService = (FornecedorService) ServiceLocator.getInstance().getService(FornecedorService.class, null);
		Collection<FornecedorDTO> listEscopoFornecimento = null;
		FornecedorDTO fornecedorDto = new FornecedorDTO();
		fornecedorDto.setIdFornecedor(avaliacaoFornecedordto.getIdFornecedor());
		fornecedorDto = (FornecedorDTO) fornecedorService.restore(fornecedorDto);

		EnderecoService enderecoService = (EnderecoService) ServiceLocator.getInstance().getService(EnderecoService.class, null);
		EnderecoDTO enderecoDto = new EnderecoDTO();

		StringBuilder html = new StringBuilder();

		if (fornecedorDto != null) {
			listEscopoFornecimento = fornecedorService.listEscopoFornecimento(fornecedorDto);
		}

		if (listEscopoFornecimento != null) {

			html.append("<table class='table' id='tblEscopo' width='100%' >");
			html.append("<tr>");

			html.append("<th>Produto</th>");
			html.append("<th>Marca</th>");

			html.append("</tr>");
			for (FornecedorDTO fornecedor : listEscopoFornecimento) {
				html.append("<tr>");
				html.append("<td>" + fornecedor.getNomeProduto() + "</td>");
				if (fornecedor.getMarca() != null)
					html.append("<td>" + fornecedor.getMarca() + "</td>");
				else
					html.append("<td>&nbsp;</td>");
				html.append("</tr>");
			}
			html.append("</table>");
		}

		document.getElementById("divEscopo").setInnerHTML(html.toString());
		HTMLForm form = document.getForm("form");
		if(fornecedorDto != null){
			form.setValues(fornecedorDto);
		}

		if (fornecedorDto != null && fornecedorDto.getIdEndereco() != null) {
			enderecoDto.setIdEndereco(fornecedorDto.getIdEndereco());
			enderecoDto = enderecoService.recuperaEnderecoCompleto(enderecoDto);
			form.setValues(enderecoDto);
		}

		document.executeScript("$('#POPUP_FORNECEDOR').dialog('close');");
	}

	/**
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 *             Metodo para restaura os campos.
	 */
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		AvaliacaoFornecedorDTO avaliacaoFornecedordto = (AvaliacaoFornecedorDTO) document.getBean();

		EnderecoDTO enderecoDto = new EnderecoDTO();

		FornecedorDTO fornecedorDto = new FornecedorDTO();

		AvaliacaoFornecedorService avaliacaoFornecedorService = (AvaliacaoFornecedorService) ServiceLocator.getInstance().getService(AvaliacaoFornecedorService.class, null);

		FornecedorService fornecedorService = (FornecedorService) ServiceLocator.getInstance().getService(FornecedorService.class, null);

		EnderecoService enderecoService = (EnderecoService) ServiceLocator.getInstance().getService(EnderecoService.class, null);
		EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);

		AvaliacaoReferenciaFornecedorService avaliacaoReferenciaFornecedorService = (AvaliacaoReferenciaFornecedorService) ServiceLocator.getInstance().getService(
				AvaliacaoReferenciaFornecedorService.class, null);

		CriterioAvaliacaoFornecedorService criterioAvaliacaoFornecedorService = (CriterioAvaliacaoFornecedorService) ServiceLocator.getInstance().getService(CriterioAvaliacaoFornecedorService.class,
				null);

		Collection<FornecedorDTO> listEscopoFornecimento = null;

		Collection<AvaliacaoReferenciaFornecedorDTO> listAvaliacaoReferenciaFornecedor = null;

		Collection<CriterioAvaliacaoFornecedorDTO> listCriterioAvaliacaoFornecedor = null;

		StringBuilder html = new StringBuilder();

		avaliacaoFornecedordto = (AvaliacaoFornecedorDTO) avaliacaoFornecedorService.restore(avaliacaoFornecedordto);

		if (avaliacaoFornecedordto.getIdAvaliacaoFornecedor() != null) {
			listAvaliacaoReferenciaFornecedor = avaliacaoReferenciaFornecedorService.listByIdAvaliacaoFornecedor(avaliacaoFornecedordto.getIdAvaliacaoFornecedor());
			listCriterioAvaliacaoFornecedor = criterioAvaliacaoFornecedorService.listByIdAvaliacaoFornecedor(avaliacaoFornecedordto.getIdAvaliacaoFornecedor());
		}

		HTMLTable tblAprovacao = document.getTableById("tblAprovacao");
		tblAprovacao.deleteAllRows();

		if (listAvaliacaoReferenciaFornecedor != null) {

			tblAprovacao.addRowsByCollection(listAvaliacaoReferenciaFornecedor, new String[] { "", "", "nome", "telefone", "decisao", "observacoes" }, new String[] { "idempregado" },
					"Empregado já cadastrado!!", new String[] { "exibeIconesAprovacao" }, null, null);
			document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblAprovacao', 'tblAprovacao');");
		}

		HTMLTable tblCriterio = document.getTableById("tblCriterio");
		tblCriterio.deleteAllRows();

		if (listCriterioAvaliacaoFornecedor != null) {
			tblCriterio.addRowsByCollection(listCriterioAvaliacaoFornecedor, new String[] { "", "", "descricao", "valor", "obs" }, new String[] { "idCriterio" }, "Criterio já cadastrado!!",
					new String[] { "exibeIconesCriterio" }, null, null);
			document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblCriterio', 'tblCriterio');");
		}

		if (avaliacaoFornecedordto.getIdFornecedor() != null) {
			fornecedorDto.setIdFornecedor(avaliacaoFornecedordto.getIdFornecedor());

			fornecedorDto = (FornecedorDTO) fornecedorService.restore(fornecedorDto);
		}

		if (avaliacaoFornecedordto.getIdResponsavel() != null) {
			EmpregadoDTO empregadoDto = empregadoService.restoreByIdEmpregado(avaliacaoFornecedordto.getIdResponsavel());
			if (empregadoDto != null) {
				avaliacaoFornecedordto.setNomeResponsavel(empregadoDto.getNome());
			}
		}

		if (fornecedorDto != null) {
			listEscopoFornecimento = fornecedorService.listEscopoFornecimento(fornecedorDto);

		}

		if (listEscopoFornecimento != null) {

			html.append("<table class='table' id='tblEscopo' width='100%' >");
			html.append("<tr>");

			html.append("<th>Produto</th>");
			html.append("<th>Marca</th>");

			html.append("</tr>");
			html.append("</tr>");
			for (FornecedorDTO fornecedor : listEscopoFornecimento) {
				html.append("<tr>");
				html.append("<td>" + fornecedor.getNomeProduto() + "</td>");
				if (fornecedor.getMarca() != null)
					html.append("<td>" + fornecedor.getMarca() + "</td>");
				else
					html.append("<td>&nbsp;</td>");
				html.append("</tr>");
			}
			html.append("</table>");
		}

		document.getElementById("divEscopo").setInnerHTML(html.toString());
		HTMLForm form = document.getForm("form");
		form.clear();
		document.getForm("form").setValues(fornecedorDto);
		if(avaliacaoFornecedordto != null){
			form.setValues(avaliacaoFornecedordto);
		}

		if (fornecedorDto != null && fornecedorDto.getIdEndereco() != null) {
			enderecoDto.setIdEndereco(fornecedorDto.getIdEndereco());
			enderecoDto = enderecoService.recuperaEnderecoCompleto(enderecoDto);
			form.setValues(enderecoDto);
		}

	}

	/**
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * 
	 *             Metodo colocar status Inativo quando for solicitado a exclusão do usuario.
	 */

	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		AvaliacaoFornecedorDTO avaliacaoFornecedorDto = (AvaliacaoFornecedorDTO) document.getBean();

		AvaliacaoFornecedorService avaliacaoFornecedorService = (AvaliacaoFornecedorService) ServiceLocator.getInstance().getService(AvaliacaoFornecedorService.class,
				WebUtil.getUsuarioSistema(request));

		AvaliacaoReferenciaFornecedorService avaliacaoReferenciaFornecedorService = (AvaliacaoReferenciaFornecedorService) ServiceLocator.getInstance().getService(
				AvaliacaoReferenciaFornecedorService.class, null);

		CriterioAvaliacaoFornecedorService criterioAvaliacaoFornecedorService = (CriterioAvaliacaoFornecedorService) ServiceLocator.getInstance().getService(CriterioAvaliacaoFornecedorService.class,
				null);

		if (avaliacaoFornecedorDto.getIdAvaliacaoFornecedor().intValue() > 0) {

			avaliacaoReferenciaFornecedorService.deleteByIdAvaliacaoFornecedor(avaliacaoFornecedorDto.getIdAvaliacaoFornecedor());

			criterioAvaliacaoFornecedorService.deleteByIdAvaliacaoFornecedor(avaliacaoFornecedorDto.getIdAvaliacaoFornecedor());

			avaliacaoFornecedorService.delete(avaliacaoFornecedorDto);

			document.alert(UtilI18N.internacionaliza(request, "MSG07"));
		}

		HTMLForm form = document.getForm("form");
		form.clear();
		document.executeScript("limpar()");
		/*HTMLTable tblCriterio = document.getTableById("tblCriterio");
		tblCriterio.deleteAllRows();
		HTMLTable tblAprovacao = document.getTableById("tblAprovacao");
		tblAprovacao.deleteAllRows();*/
	}

	public void limpar(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HTMLTable tblCriterio = document.getTableById("tblCriterio");
		tblCriterio.deleteAllRows();
		HTMLTable tblAprovacao = document.getTableById("tblAprovacao");
		tblAprovacao.deleteAllRows();

	}

}
