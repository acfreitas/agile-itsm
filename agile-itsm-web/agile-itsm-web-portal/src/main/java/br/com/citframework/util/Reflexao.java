package br.com.citframework.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.math.NumberUtils;

import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.citframework.dto.ReflexaoCopyProperty;
import br.com.citframework.excecao.LogicException;

/**
 * @author ney
 */
public class Reflexao {

    public static void executeAll(final Object obj) {
        final Iterator<String> it = findSets(obj).iterator();
        while (it.hasNext()) {
            findExecuteGet(it.next().toString(), obj);
        }
    }

    /**
     * Limpa todas as propriedades de um objeto.
     *
     * Exemplo:
     * Usuario usuario = new Usuario();
     * usuario.setNomeUsuario("EMAURI");
     * usuario.setIdEmpresa(new Integer(1));
     *
     * Reflexao.clearAllProperties(usuario);
     *
     * Apos a execucao do metodo anterior, o objeto usuario estará limpo (todas as propriedades nulas).
     *
     * @param obj
     * @throws Exception
     */
    public static void clearAllProperties(final Object obj) throws Exception {
        final List<String> lista = getAllProperties(obj);
        final Iterator<String> it = lista.iterator();
        while (it.hasNext()) {
            setPropertyValue(obj, it.next().toString(), null);
        }
    }

    public static boolean findExecuteGet(final String name, final Object obj) {
        final Method[] mtd = obj.getClass().getMethods();

        for (final Method element : mtd) {
            if (element.getName().equalsIgnoreCase("get" + name)) {
                try {
                    element.invoke(obj);
                } catch (final Exception e) {
                    return false;
                }
                return true;
            }
        }

        return false;
    }

    public static boolean findGet(final String name, final Object obj) {
        final Method[] mtd = obj.getClass().getMethods();

        for (final Method element : mtd) {
            if (element.getName().equalsIgnoreCase("get" + name)) {
                return true;
            }
        }

        return false;
    }

    public static Method findMethod(final String name, final Object obj) {
        final Method[] mtd = obj.getClass().getMethods();

        for (final Method element : mtd) {
            if (element.getName().equalsIgnoreCase(name)) {
                return element;
            }
        }

        return null;
    }

    public static Collection<Method> findMethodByPalavra(final String palavra, final Object obj) {
        final Collection<Method> colMethods = new ArrayList<>();
        final Method[] mtd = obj.getClass().getMethods();

        for (final Method element : mtd) {
            if (element.getName().indexOf(palavra) > -1) {
                colMethods.add(element);
            }
        }

        return colMethods;
    }

    public static Method findMethod(final String name, final Object obj, final int index) {
        final Method[] mtd = obj.getClass().getMethods();
        int param = 0;
        for (final Method element : mtd) {
            if (element.getName().equalsIgnoreCase(name)) {
                if (param == index) {
                    return element;
                }
                param++;
            }
        }

        return null;
    }

    public static List<String> findSets(final Object obj) {
        final List<String> result = new ArrayList<>();
        final Method[] mtd = obj.getClass().getMethods();

        for (final Method element : mtd) {
            if (element.getName().startsWith("set")) {
                result.add(element.getName().substring(3));
            }
        }

        return result;
    }

    public static List<String> findGets(final Object obj) {
        final List<String> result = new ArrayList<>();
        final Method[] mtd = obj.getClass().getMethods();

        for (final Method element : mtd) {
            if (element.getName().startsWith("get")) {
                result.add(element.getName().substring(3));
            }
        }

        return result;
    }

    public static Class<?> getReturnType(final Object obj, final String propName) {
        final Method[] mtd = obj.getClass().getMethods();

        for (final Method element : mtd) {
            if (element.getName().equalsIgnoreCase("get" + propName)) {
                return element.getReturnType();
            }
        }

        return null;
    }

    public static List<String> getAllProperties(final Object obj) {
        final List<String> lista = new ArrayList<>();
        final Iterator<String> it = findSets(obj).iterator();

        while (it.hasNext()) {
            final String prop = it.next().toString();
            if (findGet(prop, obj)) {
                lista.add(prop.substring(0, 1).toLowerCase() + prop.substring(1));
            }
        }

        return lista;
    }

    public static List<String> getCommonProperties(final Object obj1, final Object obj2) {
        final List<String> lista1 = getAllProperties(obj1);
        final List<String> lista2 = getAllProperties(obj2);
        final List<String> result = new ArrayList<>();
        for (int i = 0; i < lista1.size(); i++) {
            final String prop = lista1.get(i);
            final int ind = lista2.indexOf(prop);
            if (ind >= 0) {
                if (getReturnType(obj1, prop).getName().equals(getReturnType(obj2, prop).getName())) {
                    result.add(prop);
                }
            }
        }
        return result;
    }

    public static List<String> getCommonPropertyNames(final Object obj1, final Object obj2) {
        final List<String> lista1 = getAllProperties(obj1);
        final List<String> lista2 = getAllProperties(obj2);
        final List<String> result = new ArrayList<>();
        for (int i = 0; i < lista1.size(); i++) {
            final String prop = lista1.get(i);
            final int ind = lista2.indexOf(prop);
            if (ind >= 0) {
                result.add(prop);
            }
        }
        return result;
    }

    /**
     * Copia todas propriedades de um HashMap que sao comuns em outro objeto.
     * As propriedades que serao copiadas devem ter o mesmo nome (Independente de maiusculas e minusculas).
     *
     * @param source
     *            - HashMap origem (de onde serao copiadas as propriedades)
     * @param dest
     *            - Objeto destino (para onde serao copiadas as propriedades)
     * @throws Exception
     */
    public static void copyPropertyValuesFromMap(final Map source, final Object dest) throws Exception {
        if (source == null || dest == null) {
            throw new Exception("Source and Destination Object can not be null.");
        }

        final Set setHM = source.entrySet();
        final Iterator i = setHM.iterator();
        final List lista2 = getAllProperties(dest);
        while (i.hasNext()) {
            final Map.Entry me = (Map.Entry) i.next();
            final String propName1 = me.getKey().toString().trim().toUpperCase();
            System.out.println("copyPropertyValuesFromMap: " + propName1 + "---> " + me.getValue());
            for (int iX = 0; iX < lista2.size(); iX++) {
                final String propName = (String) lista2.get(iX);
                if (propName.trim().toUpperCase().equalsIgnoreCase(propName1)) {
                    System.out.println("copyPropertyValuesFromMap: " + propName1 + "---> Encontrou... ");
                    final Object value = me.getValue();
                    if (value != null) {
                        final Object[] param = new Object[1];
                        param[0] = value;

                        setPropertyValue(dest, propName, value);
                    }
                    break;
                }
            }
        }
        System.out.println(dest);
    }

    /**
     * Copia todas propriedades de um HashMap que sao comuns em outro objeto. As propriedades que serao copiadas devem ter o mesmo nome (Independente de maiusculas e minusculas).
     *
     * @param source
     *            - HashMap origem (de onde serao copiadas as propriedades)
     * @param dest
     *            - dest - Objeto destino (para onde serao copiadas as propriedades)
     * @param language
     *            - Linguagem do usuário logado que será considerado para converter os campos do tipo Data.
     * @throws Exception
     * @author valdoilo.damasceno
     * @since 17.02.2014
     */
    public static void copyPropertyValuesFromMap(final Map source, final Object dest, final String language) throws Exception {
        if (source == null || dest == null) {
            throw new Exception("Source and Destination Object can not be null.");
        }

        final Set setHM = source.entrySet();
        final Iterator i = setHM.iterator();
        final List lista2 = getAllProperties(dest);
        while (i.hasNext()) {
            final Map.Entry me = (Map.Entry) i.next();
            final String propName1 = me.getKey().toString().trim().toUpperCase();
            System.out.println("copyPropertyValuesFromMap: " + propName1 + "---> " + me.getValue());
            for (int iX = 0; iX < lista2.size(); iX++) {
                final String propName = (String) lista2.get(iX);
                if (propName.trim().toUpperCase().equalsIgnoreCase(propName1)) {
                    System.out.println("copyPropertyValuesFromMap: " + propName1 + "---> Encontrou... ");
                    final Object value = me.getValue();
                    if (value != null) {
                        final Object[] param = new Object[1];
                        param[0] = value;

                        setPropertyValue(dest, propName, value, language);
                    }
                    break;
                }
            }
        }
        System.out.println(dest);
    }

    /**
     * Copia todas propriedades de um objeto que sao comuns em outro objeto.
     * As propriedades que serao copiadas devem ter o mesmo nome (Inclusive letras maiusculas e minusculas).
     *
     * @param source
     *            - Objeto origem (de onde serao copiadas as propriedades)
     * @param dest
     *            - Objeto destino (para onde serao copiadas as propriedades)
     * @throws Exception
     */
    public static void copyPropertyValues(final Object source, final Object dest) throws Exception {
        if (source == null || dest == null) {
            throw new Exception("Source and Destination Object can not be null.");
        }

        final List<String> tmp = getCommonProperties(source, dest);
        final Iterator<String> it = tmp.iterator();
        while (it.hasNext()) {
            final String prop = it.next();

            final Method get = findMethod("get" + prop, source);

            Object value = null;
            try {
                value = get.invoke(source);
            } catch (final IllegalArgumentException e1) {
                e1.printStackTrace();
            } catch (final IllegalAccessException e1) {
                e1.printStackTrace();
            } catch (final InvocationTargetException e1) {
                e1.printStackTrace();
            }
            if (value != null) {
                final Object[] param = new Object[1];
                param[0] = value;

                final Method set = findMethod("set" + prop, dest);
                try {
                    set.invoke(dest, param);
                } catch (final IllegalArgumentException e) {
                    System.out.println("Erro de Conversao....1");
                    e.printStackTrace();
                } catch (final IllegalAccessException e) {
                    System.out.println("Erro de Conversao....2");
                    e.printStackTrace();
                } catch (final InvocationTargetException e) {
                    System.out.println("Erro de Conversao....3");
                    e.printStackTrace();
                }

            }

        }

    }

    /**
     * Copia todas propriedades de um objeto que estao relacionadas no array passado como parametro.
     * As propriedades podem ter nomes diferentes (a origem do destino).
     *
     * Exemplo:
     * Usuario usuario = new Usuario();
     * LogAcessoUsuario logAcessoUsuario = new LogAcessoUsuario();
     *
     * usuario.setNomeUsuario("EMAURI");
     * usuario.setIdEmpresa(new Integer(1));
     *
     * Reflexao.copyPropertyValues(usuario, logAcessoUsuario, new ReflexaoCopyProperty[] {
     * new ReflexaoCopyProperty("NomeUsuario","login"),
     * new ReflexaoCopyProperty("idEmpresa","HistAtualUsuario_idUsuario")});
     *
     * @param source
     *            - Objeto origem (de onde serao copiadas as propriedades)
     * @param dest
     *            - Objeto destino (para onde serao copiadas as propriedades)
     * @param ReflexaoCopyProperty
     *            [] - Este parametro define as propriedades que devem ser copiadas (origem e destino)
     * @throws Exception
     */
    public static void copyPropertyValues(final Object source, final Object dest, final ReflexaoCopyProperty[] propertiesCopy) throws Exception {
        if (propertiesCopy == null) {
            return;
        }
        for (final ReflexaoCopyProperty element : propertiesCopy) {
            final Method get = findMethod("get" + element.getNamePropertySource(), source);

            Object value = null;
            try {
                value = get.invoke(source);
            } catch (final IllegalArgumentException e1) {
                e1.printStackTrace();
            } catch (final IllegalAccessException e1) {
                e1.printStackTrace();
            } catch (final InvocationTargetException e1) {
                e1.printStackTrace();
            }

            if (value != null) {
                final Object[] param = new Object[1];
                param[0] = value;

                final Method set = findMethod("set" + element.getNamePropertyDest(), dest);
                try {
                    set.invoke(dest, param);
                } catch (final IllegalArgumentException e) {
                    System.out.println("Erro de Conversao....1");
                    e.printStackTrace();
                } catch (final IllegalAccessException e) {
                    System.out.println("Erro de Conversao....2");
                    e.printStackTrace();
                } catch (final InvocationTargetException e) {
                    System.out.println("Erro de Conversao....3");
                    e.printStackTrace();
                }
            }
        }
    }

    public static Collection<Object> convertList(final Collection<Object> source, final Class<?> classeDest, final ReflexaoCopyProperty[] propertiesCopy) throws Exception {
        if (source == null) {
            return null;
        }
        final Collection<Object> dest = new ArrayList<>();
        for (Object objSource : source) {
            final Object objDest = classeDest.newInstance();
            copyPropertyValues(objSource, objDest, propertiesCopy);
            dest.add(objDest);
        }
        return dest;
    }

    public static Object getPropertyValue(final Object obj, final String propName) throws Exception {
        final Method met = findMethod("get" + propName, obj);
        if (met == null) {
            throw new Exception("Propriedade [" + propName + "] nao encontrada na classe " + obj.getClass().getName());
        }
        return met.invoke(obj);
    }

    public static void setPropertyValueAsString(final Object obj, final String propName, final String propValue) throws Exception {
        final Method set = findMethod("set" + propName, obj);

        final Class<?> retorno = getReturnType(obj, propName);
        Object valorConvertido = null;
        final Constructor<?>[] cons = retorno.getConstructors();

        for (final Constructor<?> con : cons) {
            if (con.toString().indexOf("(java.lang.String)") > -1) {
                valorConvertido = con.newInstance(new Object[] {propValue});
            }
        }
        set.invoke(obj, new Object[] {valorConvertido});
    }

    public static void setPropertyValue(final Object obj, final String propName, final Object value) throws Exception {
        setNested(obj, value, propName, null);
    }

    /**
     * Seta valor a um atributo de um Objeto.
     *
     * @param obj
     *            - Objeto.
     * @param propName
     *            - Nome do atributo que será atribuído o valor.
     * @param value
     *            - Valor a ser atribuído.
     * @param language
     *            - Linguagem do usuário.
     * @throws Exception
     * @author valdoilo.damasceno
     * @since 27.02.2014
     */
    public static void setPropertyValue(final Object obj, final String propName, final Object value, final String language) throws Exception {
        setNested(obj, value, propName, language);
    }

    /**
     * Seta valor a um atributo de um Objeto.
     *
     * @param bean
     *            - Objeto.
     * @param value
     *            - Valor a ser atribuído.
     * @param attributeName
     *            - Nome do atributo.
     * @param language
     *            - Linguagem do usuário.
     * @throws Exception
     * @author valdoilo.damasceno
     * @since 17.02.2014
     */
    private static void setNested(final Object bean, Object value, final String attributeName, final String language) throws Exception {
        final int dotIndex = attributeName.indexOf('.');
        if (dotIndex == -1) {
            final Method setter = getSetter(bean, attributeName);
            if (setter != null) {
                final Class<?> parameterClass = setter.getParameterTypes()[0];
                if (value instanceof Long && parameterClass == BigInteger.class) {
                    value = new BigInteger(value.toString());
                }
                if (value instanceof Long && parameterClass == Integer.class) {
                    value = new Integer(value.toString());
                }
                if (value instanceof Short && parameterClass == Integer.class) {
                    value = new Integer(value.toString());
                }
                if (value instanceof Short && parameterClass == Long.class) {
                    value = new Long(value.toString());
                }
                if (value instanceof Integer && parameterClass == Long.class) {
                    value = new Long(value.toString());
                }
                if (value instanceof String && parameterClass == Integer.class) {
                    try {
                        value = new Integer(value.toString());
                    } catch (final Exception e) {
                        value = null;
                    }
                }
                if (value instanceof String && parameterClass == BigInteger.class) {
                    value = new BigInteger(value.toString());
                }
                if (value instanceof Long && parameterClass == Double.class) {
                    value = new Double(value.toString());
                }
                if (value instanceof Integer && parameterClass == Double.class) {
                    value = new Double(value.toString());
                }
                if (value instanceof String && parameterClass == Double.class) {
                    value = UtilNumbersAndDecimals.strFormatToDouble(value.toString());
                }
                if (value instanceof BigDecimal && parameterClass == Integer.class) {
                    value = new Integer(value.toString());
                }
                if (value instanceof BigDecimal && parameterClass == String.class) {
                    value = new String(value.toString());
                }

                /*
                 * Rodrigo Pecci Acorse - 14/11/2013 - #124212 A reflexão não previa (e não fazia a conversão) caso o valor a ser setado fosse do tipo Long e o tipo no DTO fosse
                 * String.
                 */
                if (value instanceof Long && parameterClass == String.class) {
                    value = new String(value.toString());
                }

                if (value instanceof BigInteger && parameterClass == Long.class) {
                    value = new Long(((BigInteger) value).longValue());
                }
                if (value instanceof Long && parameterClass == BigDecimal.class) {
                    value = new BigDecimal(((Long) value).longValue());
                } else if (value instanceof BigDecimal && parameterClass == Long.class) {
                    value = new Long(((BigDecimal) value).longValue());
                }
                if (value instanceof Integer && parameterClass == BigDecimal.class) {
                    value = new BigDecimal(((Integer) value).intValue());
                } else if (value instanceof BigDecimal && parameterClass == Integer.class) {
                    value = new Integer(((BigDecimal) value).intValue());
                } else if (value instanceof BigDecimal && parameterClass == Double.class) {
                    value = new Double(((BigDecimal) value).doubleValue());
                } else if (value instanceof Float && parameterClass == Double.class) {
                    value = new Double(((Float) value).doubleValue());
                }
                if (value instanceof Timestamp && parameterClass == Date.class) {
                    value = new Date(((Timestamp) value).getTime());
                }
                if (value instanceof String && parameterClass == Date.class) {
                    value = UtilDatas.convertStringToSQLDate(TipoDate.DATE_DEFAULT, value.toString(), language);
                }
                if (value instanceof Date && parameterClass == Timestamp.class) {
                    value = new Timestamp(((Date) value).getTime());
                }
                if (value instanceof String && parameterClass == Timestamp.class) {
                    value = UtilDatas.convertStringToTimestamp(TipoDate.TIMESTAMP_WITH_SECONDS, value.toString(), language);
                }
                if (value instanceof Integer && parameterClass == Short.class) {
                    value = new Short(((Integer) value).shortValue());
                } else if (value != null && value.getClass().getName().indexOf("com.ibm.db2.jcc") != -1) { // Tipo do DB2 para campo memo que merece tratamento.
                    if (value.getClass().getName().indexOf("com.ibm.db2.jcc.am.ie") == -1) {
                        final Method lengthMethod = value.getClass().getMethod("length");
                        final Integer length = (Integer) lengthMethod.invoke(value);
                        if (length.intValue() != 0) {
                            final Method substringMethod = value.getClass().getMethod("substring", new Class[] {int.class});
                            value = substringMethod.invoke(value, new Object[] {NumberUtils.INTEGER_ZERO});
                        } else {
                            value = null;
                        }
                    }
                } else if (value != null && value.getClass().getName().indexOf("oracle.sql.TIMESTAMP") != -1) {
                    final Method timeValueMethod = value.getClass().getMethod("timestampValue");
                    Timestamp timeValue = null;
                    if (timeValueMethod.getReturnType() == Timestamp.class) {
                        timeValue = (Timestamp) timeValueMethod.invoke(value);
                    }
                    if (timeValue != null) {
                        value = new Date(timeValue.getTime());
                    } else {
                        value = null;
                    }
                }

                try {
                    setter.invoke(bean, new Object[] {value});
                } catch (final Exception e) {
                    if (value != null) {
                        throw new Exception(
                                "tipo de dado incompatível com o banco de dados " + value.getClass().getName() + " :" + bean.getClass().getName() + " " + attributeName, e);
                    }
                    throw e;
                }
            }
        } else {
            final String currentAttributeName = attributeName.substring(0, dotIndex);
            final Method getter = getGetter(bean, currentAttributeName);
            if (getter != null) {
                Object valueOfCurrent = getter.invoke(bean);
                if (valueOfCurrent == null) {
                    final Method setter = getSetter(bean, currentAttributeName);
                    if (setter == null) {
                        throw new RuntimeException("Não foi encontrado setter para o atributo '" + currentAttributeName + "' em '" + bean.getClass() + "'.");
                    }
                    final Class[] params = setter.getParameterTypes();
                    if (params.length != 1) {
                        throw new RuntimeException("Mais de um setter para o atributo '" + currentAttributeName + "' da classe '" + bean.getClass() + "'.");
                    }
                    valueOfCurrent = params[0].newInstance();
                    try {
                        setter.invoke(bean, new Object[] {valueOfCurrent});
                    } catch (final Exception e) {
                        throw new Exception("tipo de dado incompatível com o banco de dados " + value.getClass().getName() + " :" + bean.getClass().getName() + " " + attributeName);
                    }
                }
                setNested(valueOfCurrent, value, attributeName.substring(dotIndex + 1), language);
            }
        }
    }

    public static void setPropertyValueFromString(final Object bean, Object value, final String attributeName) throws Exception {
        final Method setter = getSetter(bean, attributeName);
        if (setter != null) {
            final Class<?> parameterClass = setter.getParameterTypes()[0];
            if (parameterClass == BigDecimal.class) {
                if (value != null) {
                    if (!((String) value).trim().equalsIgnoreCase("")) {
                        String aux = (String) value;
                        aux = aux.replaceAll("\\.", "");
                        aux = aux.replaceAll("\\,", "\\.");
                        value = new BigDecimal(Double.parseDouble(aux));
                    } else {
                        value = null;
                    }
                }
            } else if (parameterClass == Double.class) {
                if (value != null) {
                    if (!((String) value).trim().equalsIgnoreCase("")) {
                        String aux = (String) value;
                        aux = aux.replaceAll("\\.", "");
                        aux = aux.replaceAll("\\,", "\\.");
                        value = new Double(Double.parseDouble(aux));
                    } else {
                        value = null;
                    }
                }
            } else if (parameterClass == Integer.class) {
                if (value != null) {
                    if (!((String) value).trim().equalsIgnoreCase("")) {
                        value = new Integer(Integer.parseInt((String) value));
                    } else {
                        value = null;
                    }
                }
            } else if (parameterClass == Long.class) {
                if (!((String) value).trim().equalsIgnoreCase("")) {
                    value = new Long(Long.parseLong((String) value));
                } else {
                    value = null;
                }

            } else if (parameterClass == java.sql.Timestamp.class) {
                if (!((String) value).trim().equalsIgnoreCase("")) {
                    value = UtilDatas.strToTimestamp((String) value);
                } else {
                    value = null;
                }

            } else if (parameterClass == java.sql.Date.class) {
                if (!((String) value).trim().equalsIgnoreCase("")) {
                    value = UtilDatas.strToSQLDate((String) value);
                } else {
                    value = null;
                }

            }
            if (value != null) {
                setter.invoke(bean, new Object[] {value});
            }
        }
    }

    /**
     * Atribui valor ao bean de acordo com o set. Para os campos do tipo Data são consi
     *
     * @param bean
     *            - DTO
     * @param value
     *            - Valor a ser atribuído.
     * @param attributeName
     *            - Nome do atributo.
     * @param language
     *            - Linguagem do usuário logado.
     * @throws Exception
     * @author valdoilo.damasceno
     * @since 11.02.2014
     */
    public static void setPropertyValueFromString(final Object bean, Object value, final String attributeName, final String language) throws Exception {
        final Method setter = getSetter(bean, attributeName);

        if (setter != null) {
            final Class<?> parameterClass = setter.getParameterTypes()[0];

            if (parameterClass == BigDecimal.class) {
                if (value != null) {
                    if (!((String) value).trim().equalsIgnoreCase("")) {
                        String aux = (String) value;
                        aux = aux.replaceAll("\\.", "");
                        aux = aux.replaceAll("\\,", "\\.");
                        value = new BigDecimal(Double.parseDouble(aux));
                    } else {
                        value = null;
                    }
                }
            } else if (parameterClass == Double.class) {
                if (value != null) {
                    if (!((String) value).trim().equalsIgnoreCase("")) {
                        String aux = (String) value;
                        aux = aux.replaceAll("\\.", "");
                        aux = aux.replaceAll("\\,", "\\.");
                        value = new Double(Double.parseDouble(aux));
                    } else {
                        value = null;
                    }
                }
            } else if (parameterClass == Integer.class) {
                if (value != null) {
                    if (!((String) value).trim().equalsIgnoreCase("")) {
                        value = new Integer(Integer.parseInt((String) value));
                    } else {
                        value = null;
                    }
                }
            } else if (parameterClass == Long.class) {
                if (!((String) value).trim().equalsIgnoreCase("")) {
                    value = new Long(Long.parseLong((String) value));
                } else {
                    value = null;
                }

            } else if (parameterClass == java.sql.Timestamp.class) {
                if (!((String) value).trim().equalsIgnoreCase("")) {
                    value = UtilDatas.convertStringToTimestamp(TipoDate.TIMESTAMP_WITH_SECONDS, (String) value, language);
                } else {
                    value = null;
                }

            } else if (parameterClass == java.sql.Date.class) {
                if (!((String) value).trim().equalsIgnoreCase("")) {
                    value = UtilDatas.convertStringToSQLDate(TipoDate.DATE_DEFAULT, (String) value, language);
                } else {
                    value = null;
                }

            }
            if (value != null) {
                setter.invoke(bean, new Object[] {value});
            }
        }
    }

    private static Method getGetter(final Object bean, final String attributeName) throws SecurityException, NoSuchMethodException {
        final Class<?> clazz = bean.getClass();
        final String getterName = "get" + attributeName.substring(0, 1).toUpperCase() + attributeName.substring(1);
        return clazz.getMethod(getterName);
    }

    private static Method getSetter(final Object bean, final String attributeName) throws SecurityException, NoSuchMethodException {
        final Class<?> clazz = bean.getClass();
        final String setterName = "set" + attributeName.substring(0, 1).toUpperCase() + attributeName.substring(1);
        final Method[] methods = clazz.getMethods();
        for (final Method method : methods) {
            if (method.getName().equals(setterName)) {
                return method;
            }
        }
        return null;
    }

    /**
     * Converte um valor (em string) para o parametro correto da classe.
     *
     * @param value
     * @param parameterClass
     * @return
     */
    public static Object converteTipo(final String value, final Class<?> parameterClass) {
        Object valueRetorno = null;
        if (parameterClass == BigDecimal.class) {
            if (value != null) {
                if (!value.equalsIgnoreCase("")) {
                    String aux = value;
                    aux = aux.replaceAll("\\.", "");
                    aux = aux.replaceAll("\\,", "\\.");
                    valueRetorno = new BigDecimal(Double.parseDouble(aux));
                } else {
                    valueRetorno = null;
                }
            }
        } else if (parameterClass == Double.class) {
            if (value != null) {
                if (!value.equalsIgnoreCase("")) {
                    String aux = value;
                    aux = aux.replaceAll("\\.", "");
                    aux = aux.replaceAll("\\,", "\\.");
                    valueRetorno = new Double(Double.parseDouble(aux));
                } else {
                    valueRetorno = null;
                }
            }
        } else if (parameterClass == Integer.class) {
            if (value != null) {
                if (!value.equalsIgnoreCase("")) {
                    valueRetorno = new Integer(Integer.parseInt(value));
                } else {
                    valueRetorno = null;
                }
            }
        } else if (parameterClass == Long.class) {
            valueRetorno = new Long(Long.parseLong(value));
        } else if (parameterClass == int.class) {
            if (value != null) {
                if (!value.equalsIgnoreCase("")) {
                    valueRetorno = new Integer(Integer.parseInt(value));
                } else {
                    valueRetorno = null;
                }
            }
        } else if (parameterClass == java.sql.Date.class) {
            try {
                valueRetorno = UtilDatas.strToSQLDate(value);
            } catch (final LogicException e) {
                valueRetorno = null;
            }
        } else {
            valueRetorno = value;
        }

        return valueRetorno;
    }

}
