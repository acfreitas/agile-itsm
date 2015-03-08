package br.com.citframework.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Arrays;

/**
 * Utilitários para reflection utilizados
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 18/08/2014
 *
 */
public final class ReflectionUtils {

    private ReflectionUtils() {}

    /**
     * Attempt to find a {@link Field field} on the supplied {@link Class} with the
     * supplied <code>name</code>. Searches all superclasses up to {@link Object}.
     *
     * @param clazz
     *            the class to introspect
     * @param name
     *            the name of the field
     * @return the corresponding Field object, or <code>null</code> if not found
     */
    public static Field findField(final Class<?> clazz, final String name) {
        return findField(clazz, name, null);
    }

    /**
     * Attempt to find a {@link Field field} on the supplied {@link Class} with the
     * supplied <code>name</code> and/or {@link Class type}. Searches all superclasses
     * up to {@link Object}.
     *
     * @param clazz
     *            the class to introspect
     * @param name
     *            the name of the field (may be <code>null</code> if type is specified)
     * @param type
     *            the type of the field (may be <code>null</code> if name is specified)
     * @return the corresponding Field object, or <code>null</code> if not found
     */
    public static Field findField(final Class<?> clazz, final String name, final Class<?> type) {
        Class<?> searchType = clazz;
        while (!Object.class.equals(searchType) && searchType != null) {
            final Field[] fields = searchType.getDeclaredFields();
            for (final Field field : fields) {
                if ((name == null || name.equals(field.getName())) && (type == null || type.equals(field.getType()))) {
                    return field;
                }
            }
            searchType = searchType.getSuperclass();
        }
        return null;
    }

    /**
     * Set the field represented by the supplied {@link Field field object} on the
     * specified {@link Object target object} to the specified <code>value</code>.
     * In accordance with {@link Field#set(Object, Object)} semantics, the new value
     * is automatically unwrapped if the underlying field has a primitive type.
     * <p>
     * Thrown exceptions are handled via a call to {@link #handleReflectionException(Exception)}.
     *
     * @param field
     *            the field to set
     * @param target
     *            the target object on which to set the field
     * @param value
     *            the value to set; may be <code>null</code>
     */
    public static void setField(final Field field, final Object target, final Object value) {
        try {
            field.set(target, value);
        } catch (final IllegalAccessException ex) {
            handleReflectionException(ex);
            throw new IllegalStateException("Unexpected reflection exception - " + ex.getClass().getName() + ": " + ex.getMessage());
        }
    }

    /**
     * Seta em um atributo de uma classe o atributo com nome '{@code name}' o valor '{@code value}' no objeto desta instância
     *
     * @param target
     *            objeto a ter o campo e valores setados
     * @param name
     *            nome do atributo a ser setado o conteúdo
     * @param value
     *            conteúdo a ser setado
     * @see ReflectionUtils#findField(Class, String)
     * @see ReflectionUtils#makeAccessible(Field)
     * @see ReflectionUtils#setField(Field, Object, Object)
     */
    public static void setField(final Object target, final String name, final Object value) {
        final Field field = ReflectionUtils.findField(target.getClass(), name);
        ReflectionUtils.makeAccessible(field);
        ReflectionUtils.setField(field, target, value);
    }

    /**
     * Seta um atributo em um método estático com um novo valor
     *
     * @param field
     *            atributo a ter o valor alterado
     * @param newValue
     *            novo valor a ser setado
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 20/08/2014
     */
    public static void setFieldOnStatic(final Field field, final Object newValue) {
        try {
            final Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

            field.set(null, newValue);
        } catch (final IllegalAccessException ex) {
            handleReflectionException(ex);
            throw new IllegalStateException("Unexpected reflection exception - " + ex.getClass().getName() + ": " + ex.getMessage());
        } catch (final NoSuchFieldException ex) {
            throw new IllegalStateException("Unexpected reflection exception - " + ex.getClass().getName() + ": " + ex.getMessage());
        }
    }

    /**
     * Seta um atributo em um método estático com um novo valor
     *
     * @param clazz
     *            classe a ser refletida
     * @param name
     *            nome do atributo a ser setado o conteúdo
     * @param newValue
     *            novo valor a ser setado
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 20/08/2014
     */
    public static void setFieldOnStatic(final Class<?> clazz, final String name, final Object newValue) {
        final Field field = ReflectionUtils.findField(clazz, name);
        field.setAccessible(true);

        setFieldOnStatic(field, newValue);
    }

    /**
     * Get the field represented by the supplied {@link Field field object} on the
     * specified {@link Object target object}. In accordance with {@link Field#get(Object)} semantics, the returned value is automatically wrapped if the underlying field
     * has a primitive type.
     * <p>
     * Thrown exceptions are handled via a call to {@link #handleReflectionException(Exception)}.
     *
     * @param field
     *            the field to get
     * @param target
     *            the target object from which to get the field
     * @return the field's current value
     */
    public static Object getField(final Field field, final Object target) {
        try {
            return field.get(target);
        } catch (final IllegalAccessException ex) {
            handleReflectionException(ex);
            throw new IllegalStateException("Unexpected reflection exception - " + ex.getClass().getName() + ": " + ex.getMessage());
        }
    }

    /**
     * Attempt to find a {@link Method} on the supplied class with the supplied name
     * and parameter types. Searches all superclasses up to <code>Object</code>.
     * <p>
     * Returns <code>null</code> if no {@link Method} can be found.
     *
     * @param clazz
     *            the class to introspect
     * @param name
     *            the name of the method
     * @param paramTypes
     *            the parameter types of the method
     *            (may be <code>null</code> to indicate any signature)
     * @return the Method object, or <code>null</code> if none found
     */
    public static Method findMethod(final Class<?> clazz, final String name, final Class<?>... paramTypes) {
        Class<?> searchType = clazz;
        while (searchType != null) {
            final Method[] methods = searchType.isInterface() ? searchType.getMethods() : searchType.getDeclaredMethods();
            for (final Method method : methods) {
                if (name.equals(method.getName()) && (paramTypes == null || Arrays.equals(paramTypes, method.getParameterTypes()))) {
                    return method;
                }
            }
            searchType = searchType.getSuperclass();
        }
        throw new IllegalStateException(String.format("Should never get here. Method '%s' not found.", name));
    }

    /**
     * Invoca o método especificado {@link Method} no objeto com os argumentos informados
     * O objeto alvo pode ser nulo quando invocando um método estático.
     * <p>
     * Exceções lançadas são tratadas por {@link #handleReflectionException}.
     *
     * @param method
     *            o método a ser executado
     * @param target
     *            objeto alvo para ser executado o método
     * @param args
     *            parâmetros do método a ser executado (pode ser <code>null</code>)
     * @return resultado da execução, se existir
     */
    public static Object invokeMethod(final Method method, final Object target, final Object... args) {
        try {
            return method.invoke(target, args);
        } catch (final Exception ex) {
            handleReflectionException(ex);
        }
        throw new IllegalStateException("Should never get here");
    }

    /**
     * Trata a exceção lançada na operação de reflexão. Deve ser chamada apenas se se nenhuma exceção "checked" seja lançada no método alvo.
     *
     * @param ex
     *            exceção de reflexão a ser tratada
     */
    public static void handleReflectionException(final Exception ex) {
        if (ex instanceof NoSuchMethodException) {
            throw new IllegalStateException("Method not found: " + ex.getMessage());
        }
        if (ex instanceof IllegalAccessException) {
            throw new IllegalStateException("Could not access method: " + ex.getMessage());
        }
        if (ex instanceof InvocationTargetException) {
            handleInvocationTargetException((InvocationTargetException) ex);
        }
        if (ex instanceof RuntimeException) {
            throw (RuntimeException) ex;
        }
        throw new UndeclaredThrowableException(ex);
    }

    /**
     * Trata execeção {@link InvocationTargetException}. Deve ser chamada apenas se se nenhuma exceção "checked" seja lançada no método alvo.
     *
     * @param ex
     *            a {@link InvocationTargetException} a ser tratada
     */
    public static void handleInvocationTargetException(final InvocationTargetException ex) {
        rethrowRuntimeException(ex.getTargetException());
    }

    /**
     * Relança {@link Throwable}, que é presumivelmente a <em>exception alvo</em> de uma {@link InvocationTargetException}. Deve ser chamada apenas se se nenhuma exceção "checked"
     * seja lançada no método alvo.
     * <p>
     * Rethrows the underlying exception cast to an {@link RuntimeException} or {@link Error} if appropriate; otherwise, throws an {@link IllegalStateException}.
     *
     * @param ex
     *            the exception to rethrow
     * @throws RuntimeException
     *             the rethrown exception
     */
    public static void rethrowRuntimeException(final Throwable ex) {
        if (ex instanceof RuntimeException) {
            throw (RuntimeException) ex;
        }
        if (ex instanceof Error) {
            throw (Error) ex;
        }
        throw new UndeclaredThrowableException(ex);
    }

    /**
     * Torna o campo explicitamente acessível, caso necessário. {@code setAccessible(true)} é chamado apenas quando necessário, evitando conflitos com a JVM {@link SecurityManager}
     * (se ativo)
     *
     * @param field
     *            campo a ser tornado acessível
     * @see java.lang.reflect.Field#setAccessible
     */
    public static void makeAccessible(final Field field) {
        if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers()) || Modifier.isFinal(field.getModifiers()))
                && !field.isAccessible()) {
            field.setAccessible(true);
        }
    }

    /**
     * Torna o método explicitamente acessível, caso necessário. {@code setAccessible(true)} é chamado apenas quando necessário, evitando conflitos com a JVM
     * {@link SecurityManager} (se ativo)
     *
     * @param method
     *            método a ser tornado acessível
     * @see java.lang.reflect.Method#setAccessible
     */
    public static void makeAccessible(final Method method) {
        if ((!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers())) && !method.isAccessible()) {
            method.setAccessible(true);
        }
    }

    /**
     * Recupera um array de parâmetros, de acordo com os '{@code types}' informados. '{@code types}' sempre deve ser menor ou igual a '{@code values}'
     *
     * @param types
     *            tipos a serem procurandos em '{@code values}'
     * @param values
     *            '{@code }' para filtro
     * @return array de parâmetros
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 23/09/2014
     */
    public static Object[] getListParameterForTypes(final Class<?>[] types, final Object... values) {
        if (types == null || types.length <= 0 || values == null || values.length <= 0) {
            return null;
        }

        final int typesLength = types.length;
        final int valuesLength = values.length;

        Assert.isTrue(typesLength > 0, "types can't be less or equal zero ");
        Assert.isTrue(valuesLength > 0, "values can't be less or equal zero ");
        Assert.isTrue(typesLength <= valuesLength, "values lenght can't be less than types length.");

        final Object[] result = new Object[typesLength];
        for (int i = 0; i < typesLength; i++) {
            final Class<?> type = types[i];
            for (int j = 0; j < valuesLength; j++) {
                final Object value = values[j];
                if (value.getClass().isAssignableFrom(type)) {
                    result[i] = value;
                }
            }
        }
        return result;
    }

}
