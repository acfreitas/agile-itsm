package br.com.centralit.citcorpore.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.citcorpore.bean.ParametroCorporeDTO;
import br.com.centralit.citcorpore.negocio.ParametroCorporeService;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Assert;

public class ParametroUtil {

    private static final Map<Integer, String> parametroCitSmart = new HashMap<Integer, String>();

    /**
     * Atualiza HashMap stático que armazena os parâmetros do CITSMart.
     *
     * @param parametroSistema
     *            - Parâmetro do sistema.
     * @param valor
     *            - Valor do parâmetro.
     * @author valdoilo.damasceno
     */
    public static void atualizarHashMapParametroCitSmart(final Integer id, final String valor) {
        if (id != null) {
            parametroCitSmart.put(id, valor);
        }
    }

    /**
     * Realiza consulta do valor do Parâmetro no BD.
     *
     * @param parametro
     * @param valorDefault
     * @return valorParametro
     * @throws Exception
     * @author valdoilo.damasceno
     */
    public static String getValor(final ParametroSistema parametro) throws Exception {
        final ParametroCorporeDTO parametroDto = getService().getParamentroAtivo(parametro.id());

        if (StringUtils.isBlank(parametroDto.getValor())) {
            return null;
        }
        return parametroDto.getValor().trim();
    }

    public static String getValor(final ParametroSistema parametro, final TransactionControler tc, final String valorDefault) throws Exception {
        return getValorParametroCitSmartHashMap(parametro, valorDefault);
    }

    /**
     * Retorna valor do Parâmetro que está armazenado no HashMap stático. Caso não haja valor armazenado retorna valor default informado.
     *
     * @param parametro
     *            - ParametroSistema informado.
     * @param valorDefault
     *            - Valor padrão que deverá ser assumido caso não haja nenhuma armazenado.
     * @return ValorParametroCitSmart
     * @author valdoilo.damasceno
     */
    public static String getValorParametroCitSmartHashMap(final ParametroSistema parametro, final String valorDefault) {
        final String valorParametroCitSmart = parametroCitSmart.get(parametro.id());

        if (StringUtils.isNotBlank(valorParametroCitSmart)) {
            return valorParametroCitSmart;
        }
        return valorDefault;
    }

    /**
     * Retorna valor numérico do parâmetro que está armazenado no Map de cache. Caso não haja valor armazenado retorna valor default informado.
     *
     * @param param
     *            parâmetro a ser recuperado
     * @param defaultValue
     *            valor padrão, caso não exista valor para o parâmetro
     * @return
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 28/10/2014
     */
    public static Integer getValorParametro(final ParametroSistema param, final String defaultValue) {
        Assert.notNull(param, "Parameter must not be null");
        Assert.notNullAndNotEmpty(defaultValue, "Default value must not be null or empty");

        final boolean isTipoNumerico = param.tipoCampo().equals(Enumerados.NUMERO);

        Assert.isTrue(isTipoNumerico, "Parameter type must be 'NUMERO'");

        final String result = StringUtils.trim(ParametroUtil.getValorParametroCitSmartHashMap(param, defaultValue));
        return Integer.parseInt(result);
    }

    private static ParametroCorporeService service;

    public static ParametroCorporeService getService() throws Exception {
        if (service == null) {
            service = (ParametroCorporeService) ServiceLocator.getInstance().getService(ParametroCorporeService.class, null);
        }
        return service;
    }

}
