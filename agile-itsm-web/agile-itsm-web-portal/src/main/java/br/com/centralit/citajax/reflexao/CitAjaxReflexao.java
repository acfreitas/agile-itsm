package br.com.centralit.citajax.reflexao;

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

import org.apache.log4j.Logger;

import br.com.centralit.citajax.util.CitAjaxUtil;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.util.UtilDatas;

/**
 * @author ney
 */
public class CitAjaxReflexao {

    private static final Logger LOGGER = Logger.getLogger(CitAjaxReflexao.class);

    public static void executeAll(final Object obj) {
        final Iterator<String> it = findSets(obj).iterator();
        while (it.hasNext()) {
            findExecuteGet(it.next().toString(), obj);
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
            } catch (final IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                LOGGER.warn(e.getMessage(), e);
            }
            if (value != null) {
                final Object[] param = new Object[1];
                param[0] = value;

                final Method set = findMethod("set" + prop, dest);
                try {
                    set.invoke(dest, param);
                } catch (final IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                    LOGGER.warn("Erro de Conversao....:" + e.getMessage(), e);
                }
            }
        }
    }

    public static Object getPropertyValue(final Object obj, final String propName) throws Exception {
        final Method met = findMethod("get" + propName, obj);
        if (met == null) {
            throw new Exception("Propriedade " + propName + " não encontrada na classe " + obj.getClass().getName());
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
        setNested(obj, value, propName);
    }

    private static void setNested(final Object bean, Object value, final String attributeName) throws Exception {
        final int dotIndex = attributeName.indexOf('.');
        if (dotIndex == -1) {
            final Method setter = getSetter(bean, attributeName);
            if (setter != null) {
                final Class<?> parameterClass = setter.getParameterTypes()[0];
                if (parameterClass == BigInteger.class) {
                    value = new BigInteger(value.toString());
                } else if (parameterClass == Timestamp.class) {
                    try {
                        value = Timestamp.valueOf(value.toString());
                    } catch (final Exception e) {
                        value = UtilDatas.convertStringToTimestamp(TipoDate.TIMESTAMP_DEFAULT, value.toString(), null);
                    }
                }

                if (value instanceof Long && parameterClass == BigDecimal.class) {
                    value = new BigDecimal(((Long) value).longValue());
                } else if (value instanceof BigDecimal && parameterClass == Long.class) {
                    value = new Long(((BigDecimal) value).longValue());
                } else if (value instanceof Integer && parameterClass == BigDecimal.class) {
                    value = new BigDecimal(((Integer) value).intValue());
                } else if (value instanceof BigDecimal && parameterClass == Integer.class) {
                    value = new Integer(((BigDecimal) value).intValue());
                }
                setter.invoke(bean, value);
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
                    final Class<?>[] params = setter.getParameterTypes();
                    if (params.length != 1) {
                        throw new RuntimeException("Mais de um setter para o atributo '" + currentAttributeName + "' da classe '" + bean.getClass() + "'.");
                    }
                    valueOfCurrent = params[0].newInstance();
                    setter.invoke(bean, valueOfCurrent);
                }
                setNested(valueOfCurrent, value, attributeName.substring(dotIndex + 1));
            }
        }
    }

    public static void setPropertyValueFromString(final Object bean, Object value, final String attributeName) throws Exception {
        final Method setter = getSetter(bean, attributeName);
        if (setter != null) {
            final Class<?> parameterClass = setter.getParameterTypes()[0];
            if (parameterClass == BigDecimal.class) {
                if (value != null) {
                    if (!((String) value).equalsIgnoreCase("")) {
                        String aux = (String) value;
                        aux = aux.replaceAll("\\,", "\\.");
                        value = new BigDecimal(aux);
                    } else {
                        value = null;
                    }
                }
            } else if (parameterClass == Double.class) {
                if (value != null) {
                    if (!((String) value).equalsIgnoreCase("")) {
                        String aux = (String) value;
                        aux = aux.replaceAll("\\,", "\\.");
                        value = Double.valueOf(aux);
                    } else {
                        value = null;
                    }
                }
            } else if (parameterClass == Integer.class) {
                if (value != null) {
                    if (!((String) value).equalsIgnoreCase("")) {
                        value = Integer.valueOf((String) value);
                    } else {
                        value = null;
                    }
                }
            } else if (parameterClass == Long.class) {
                value = Long.valueOf((String) value);
            } else if (parameterClass == java.sql.Date.class) {
                value = CitAjaxUtil.strToSQLDate((String) value);
            }
            setter.invoke(bean, value);
        }
    }

    public static Method getGetter(final Object bean, final String attributeName) throws SecurityException, NoSuchMethodException {
        final Class<?> clazz = bean.getClass();
        final String getterName = "get" + attributeName.substring(0, 1).toUpperCase() + attributeName.substring(1);
        return clazz.getMethod(getterName);
    }

    public static Method getSetter(final Object bean, final String attributeName) throws SecurityException, NoSuchMethodException {
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
                if (value.trim().length() > 0) {
                    String aux = value;
                    aux = aux.replaceAll("\\,", "\\.");
                    valueRetorno = new BigDecimal(aux);
                }
            }
        } else if (parameterClass == Double.class) {
            if (value != null) {
                if (value.trim().length() > 0) {
                    String aux = value;
                    aux = aux.replaceAll("\\,", "\\.");
                    valueRetorno = Double.valueOf(aux);
                }
            }
        } else if (parameterClass == Integer.class) {
            if (value != null) {
                if (value.trim().length() > 0) {
                    valueRetorno = Integer.valueOf(value);
                }
            }
        } else if (parameterClass == Long.class) {
            if (value.trim().length() > 0) {
                valueRetorno = Long.valueOf(value);
            }
        } else if (parameterClass == int.class) {
            if (value != null) {
                if (value.trim().length() > 0) {
                    valueRetorno = Integer.valueOf(value);
                }
            }
        } else if (parameterClass == Date.class) {
            try {
                valueRetorno = CitAjaxUtil.strToSQLDate(value);
            } catch (final LogicException e) {
                valueRetorno = null;
            }
        } else {
            valueRetorno = value;
        }
        return valueRetorno;
    }

}
