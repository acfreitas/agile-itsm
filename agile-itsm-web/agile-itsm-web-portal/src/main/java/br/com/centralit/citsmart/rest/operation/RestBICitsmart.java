package br.com.centralit.citsmart.rest.operation;

import javax.xml.bind.JAXBException;

import br.com.centralit.citcorpore.bi.utils.BICitsmartUtils;
import br.com.centralit.citsmart.rest.bean.RestOperationDTO;
import br.com.centralit.citsmart.rest.bean.RestSessionDTO;
import br.com.centralit.citsmart.rest.schema.BICitsmart;
import br.com.centralit.citsmart.rest.schema.BICitsmartResp;
import br.com.centralit.citsmart.rest.util.RestBICitsmartOperationUtil;
import br.com.centralit.citsmart.rest.util.RestEnum;

/**
 * Responsável por Executar as Operações da Classe RestBICitsmartResource.
 *
 * @author valdoilo.damasceno
 * @since 06.12.2013
 */
public class RestBICitsmart implements IRestOperation<BICitsmart, BICitsmartResp> {

    /**
     * Busca o xml das tabelas utilizadas no BI do Citsmart, seta e retorna o objeto BI.
     *
     * @param session
     * @param operation
     * @param message
     * @return BICitsmartResp
     * @throws JAXBException
     * @author rodrigo.acorse
     */
    protected BICitsmartResp getTabelas(final RestSessionDTO session, final RestOperationDTO operation, final BICitsmart message) throws JAXBException {
        final BICitsmartResp resp = new BICitsmartResp();

        if (session.getUser() == null || session.getUser().getLogin() == null || session.getUser().getLogin().trim().equals("")) {
            resp.setError(RestBICitsmartOperationUtil.buildError(RestEnum.INPUT_ERROR, "Login do usuário não identificado"));
            return resp;
        }

        try {
            final String xml = BICitsmartUtils.recuperaXmlTabelasBICitsmart(false);
            resp.setXml(xml);
        } catch (final Exception e) {
            e.printStackTrace();
        }

        return resp;
    }

    /*
     * (non-Javadoc)
     * @see br.com.centralit.citsmart.rest.operation.IRestOperation#execute(br.com.centralit.citsmart.rest.bean.RestSessionDTO,
     * br.com.centralit.citsmart.rest.bean.RestExecutionDTO,
     * br.com.centralit.citsmart.rest.bean.RestOperationDTO, br.com.centralit.citsmart.rest.schema.CtMessage)
     */
    @Override
    public BICitsmartResp execute(final RestSessionDTO session, final RestOperationDTO operation, final BICitsmart message) throws JAXBException {
        return this.getTabelas(session, operation, message);
    }

}
