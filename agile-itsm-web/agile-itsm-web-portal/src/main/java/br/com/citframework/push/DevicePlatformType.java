package br.com.citframework.push;

import org.apache.commons.lang.StringUtils;

import br.com.citframework.util.Assert;

/**
 * Enumerado dos tipos de SO de device para os quais podem ser enviadas push messages
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 15/11/2014
 */
public enum DevicePlatformType {

    ANDROID(1, "Android"),
    IOS(2, "iOS");

    private final Integer id;
    private final String description;

    private DevicePlatformType(final Integer id, final String description) {
        this.id = id;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Recupera uma {@link DevicePlatformType} de acordo com seu identificador
     *
     * @param id
     *            identificador a ser verificada se há uma {@link DevicePlatformType}
     * @return {@link DevicePlatformType} caso encontre. {@link IllegalArgumentException}, caso contrário
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 15/11/2014
     */
    public static DevicePlatformType fromId(final Integer id) {
        Assert.notNull(id, "Id must not be null");
        final DevicePlatformType[] values = DevicePlatformType.values();
        for (final DevicePlatformType type : values) {
            if (id.equals(type.getId())) {
                return type;
            }
        }
        throw new IllegalArgumentException(String.format("DevicePlatformType not found for identifier '%s'", id));
    }

    /**
     * Recupera uma {@link DevicePlatformType} de acordo com sua descrição
     *
     * @param description
     *            descricão a ser verificada se há uma {@link DevicePlatformType}
     * @return {@link DevicePlatformType} caso encontre. {@link IllegalArgumentException}, caso contrário
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 15/11/2014
     */
    public static DevicePlatformType fromDescription(final String description) {
        Assert.notNullAndNotEmpty(description, "Description must not be null or empty");
        final DevicePlatformType[] values = DevicePlatformType.values();
        for (final DevicePlatformType type : values) {
            if (StringUtils.equalsIgnoreCase(description, type.getDescription())) {
                return type;
            }
        }
        throw new IllegalArgumentException(String.format("DevicePlatformType not found for description '%s'", description));
    }

}