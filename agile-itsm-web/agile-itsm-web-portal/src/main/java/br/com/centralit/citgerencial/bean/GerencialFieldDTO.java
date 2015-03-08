package br.com.centralit.citgerencial.bean;

import br.com.citframework.dto.IDto;

public class GerencialFieldDTO implements IDto {

    private static final long serialVersionUID = 2517333768182961561L;

    private String name;
    private String width;
    private String type;
    private String title;
    private boolean totals;
    private boolean count;
    private Integer decimals;
    private String mask;
    private Class<?> classField;

    public boolean isCount() {
        return count;
    }

    public void setCount(final boolean count) {
        this.count = count;
    }

    public Integer getDecimals() {
        return decimals;
    }

    public void setDecimals(final Integer decimals) {
        this.decimals = decimals;
    }

    public String getMask() {
        return mask;
    }

    public void setMask(final String mask) {
        this.mask = mask;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public boolean isTotals() {
        return totals;
    }

    public void setTotals(final boolean totals) {
        this.totals = totals;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(final String width) {
        this.width = width;
    }

    public Class<?> getClassField() {
        return classField;
    }

    public void setClassField(final Class<?> classField) {
        this.classField = classField;
    }

}
