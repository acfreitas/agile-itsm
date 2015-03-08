package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.ItemRequisicaoProdutoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoProdutoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ItemRequisicaoProdutoService;
import br.com.centralit.citcorpore.negocio.JustificativaParecerService;
import br.com.centralit.citcorpore.negocio.RequisicaoProdutoService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class AcompRequisicaoProduto extends RequisicaoProduto {

	@Override
	public Class getBeanClass() {
		return RequisicaoProdutoDTO.class;
	}

	@Override
    protected String getAcao() {
        return RequisicaoProdutoDTO.ACAO_ACOMPANHAMENTO;
    }

    @Override
    public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        JustificativaParecerService justificativaService = (JustificativaParecerService) ServiceLocator.getInstance().getService(JustificativaParecerService.class, WebUtil.getUsuarioSistema(request));
        HTMLSelect idJustificativa = (HTMLSelect) document.getSelectById("item#idJustificativaValidacao");
        idJustificativa.removeAllOptions();
        idJustificativa.addOption("", "---");
        Collection colJustificativas = justificativaService.list();
        if(colJustificativas != null && !colJustificativas.isEmpty())
            idJustificativa.addOptions(colJustificativas, "idJustificativa", "descricaoJustificativa", null);

        idJustificativa = (HTMLSelect) document.getSelectById("item#idJustificativaAutorizacao");
        idJustificativa.removeAllOptions();
        idJustificativa.addOption("", "---");
        if(colJustificativas != null && !colJustificativas.isEmpty())
            idJustificativa.addOptions(colJustificativas, "idJustificativa", "descricaoJustificativa", null);

        super.load(document, request, response);
    }

    public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

        RequisicaoProdutoDTO requisicaoProdutoDto = (RequisicaoProdutoDTO) document.getBean();
        String editar = requisicaoProdutoDto.getEditar();
		RequisicaoProdutoService requisicaoProdutoService = (RequisicaoProdutoService) ServiceLocator.getInstance().getService(RequisicaoProdutoService.class, WebUtil.getUsuarioSistema(request));

		requisicaoProdutoDto = (RequisicaoProdutoDTO) requisicaoProdutoService.restore(requisicaoProdutoDto);
		if (requisicaoProdutoDto == null)
			return;

		requisicaoProdutoDto.setEditar(editar);
        if (requisicaoProdutoDto.getEditar() == null)
            requisicaoProdutoDto.setEditar("S");

        HTMLTable tblItensRequisicao = document.getTableById("tblItensRequisicao");
        tblItensRequisicao.deleteAllRows();

        ItemRequisicaoProdutoService itemRequisicaoProdutoService = (ItemRequisicaoProdutoService) ServiceLocator.getInstance().getService(ItemRequisicaoProdutoService.class, WebUtil.getUsuarioSistema(request));
        Collection<ItemRequisicaoProdutoDTO> itensRequisicao = itemRequisicaoProdutoService.findByIdSolicitacaoServico(requisicaoProdutoDto.getIdSolicitacaoServico());
        if (itensRequisicao != null) {
            tblItensRequisicao.addRowsByCollection(itensRequisicao,
                                                new String[] {"","","quantidade","descrSituacao"},
                                                null,
                                                "",
                                                new String[] {"gerarImg"},
                                                "editarItem",
                                                null);
        }

        requisicaoProdutoDto.setAcao(getAcao());
        recuperaAutorizadores(document, request, response, requisicaoProdutoDto);

        HTMLForm form = document.getForm("form");
        form.clear();
        form.setValues(requisicaoProdutoDto);
	}
}
