package br.com.centralit.citcorpore.ajaxForms;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.citframework.integracao.ConnectionProvider;
import br.com.citframework.util.Constantes;

public class ConfiguracaoAmbiente extends AjaxFormAction {

    @Override
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        document.focusInFirstActivateField(null);

        final Connection connection = ConnectionProvider.getConnection(Constantes.getValue("DATABASE_ALIAS"));
        final DatabaseMetaData meta = connection.getMetaData();

        request.setAttribute("versao_driver_jdbc", meta.getDriverVersion());

        connection.close();
    }

    @Override
    public Class<Object> getBeanClass() {
        return Object.class;
    }

}
