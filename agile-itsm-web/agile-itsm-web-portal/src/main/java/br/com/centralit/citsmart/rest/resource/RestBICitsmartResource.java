/**
 * 2013 - CentralIT - Citsmart - BI Citsmart
 * 
 */
package br.com.centralit.citsmart.rest.resource;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import br.com.centralit.citsmart.rest.schema.BICitsmart;
import br.com.centralit.citsmart.rest.util.RestBICitsmartOperationUtil;

/**
 * Classe Resource responsável pelas Requisições do BICitsmart.
 * 
 * @author valdoilo.damasceno
 * @since 06.12.2013
 */
@Path("/services")
public class RestBICitsmartResource {

	/**
	 * Trata Requisição de Recuperaração da Tabela Contratos.
	 * 
	 * @param input
	 *            - BICitsmart
	 * @return Response
	 * @author valdoilo.damasceno
	 * @since 06.12.2013
	 */
	
	 @POST
	 @Path("/bicitsmart/recuperarTabelas")
	public Response recuperarTabelas(BICitsmart input) {
		 input.setMessageID("bicitsmart_recuperarTabelas");
		 return RestBICitsmartOperationUtil.execute(input);
	}
	
}
