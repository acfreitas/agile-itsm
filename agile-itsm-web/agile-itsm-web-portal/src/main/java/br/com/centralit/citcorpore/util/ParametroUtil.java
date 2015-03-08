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
     * Atualiza HashMap st�tico que armazena os par�metros do CITSMart.
     *
     * @param parametroSistema
     *            - Par�metro do sistema.
     * @param valor
     *            - Valor do par�metro.
     * @author valdoilo.damasceno
     */
    public static void atualizarHashMapParametroCitSmart(final Integer id, final String valor) {
        if (id != null) {
            parametroCitSmart.put(id, valor);
        }
    }

    /**
     * Realiza consulta do valor do Par�metro no BD.
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
     * Retorna valor do Par�metro que est� armazenado no HashMap st�tico. Caso n�o haja valor armazenado retorna valor default informado.
     *
     * @param parametro
     *            - ParametroSistema informado.
     * @param valorDefault
     *            - Valor padr�o que dever� ser assumido caso n�o haja nenhuma armazenado.
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
     * Retorna valor num�rico do par�metro que est� armazenado no Map de cache. Caso n�o haja valor armazenado retorna valor default informado.
     *
     * @param param
     *            par�metro a ser recuperado
     * @param defaultValue
     *            valor padr�o, caso n�o exista valor para o par�metro
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
