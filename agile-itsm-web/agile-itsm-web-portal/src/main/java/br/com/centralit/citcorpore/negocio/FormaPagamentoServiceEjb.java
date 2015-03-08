package br.com.centralit.citcorpore.negocio;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.FormaPagamentoDTO;
import br.com.centralit.citcorpore.integracao.FormaPagamentoDAO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilI18N;

public class FormaPagamentoServiceEjb extends CrudServiceImpl implements FormaPagamentoService {

    private FormaPagamentoDAO dao;

    @Override
    protected FormaPagamentoDAO getDao() {
        if (dao == null) {
            dao = new FormaPagamentoDAO();
        }
        return dao;
    }

    @Override
    public boolean consultarFormaPagamento(final FormaPagamentoDTO obj) throws Exception {
        return this.getDao().consultarFormaPagamento(obj);
    }

    @Override
    public void deletarFormaPagamento(final IDto model, final DocumentHTML document, final HttpServletRequest request) throws ServiceException, Exception {
        final FormaPagamentoDTO formaPagamentoDto = (FormaPagamentoDTO) model;
        try {
            formaPagamentoDto.setSituacao("I");
            this.getDao().update(model);
            document.alert(UtilI18N.internacionaliza(request, "MSG07"));
        } catch (final Exception e) {
            throw new ServiceException(e);
        }

    }

}
