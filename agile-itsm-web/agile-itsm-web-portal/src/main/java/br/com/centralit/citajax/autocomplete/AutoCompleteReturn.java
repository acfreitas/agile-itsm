package br.com.centralit.citajax.autocomplete;

import java.util.List;

public class AutoCompleteReturn {

    private List<?> lstRetorno;
    private String[] columnsReturn;
    private String columnId;
    private String columnDescription;

    public String[] getColumnsReturn() {
        return columnsReturn;
    }

    public void setColumnsReturn(final String[] columnsReturn) {
        this.columnsReturn = columnsReturn;
    }

    public List<?> getLstRetorno() {
        return lstRetorno;
    }

    public void setLstRetorno(final List<?> lstRetorno) {
        this.lstRetorno = lstRetorno;
    }

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(final String columnId) {
        this.columnId = columnId;
    }

    public String getColumnDescription() {
        return columnDescription;
    }

    public void setColumnDescription(final String columnDescription) {
        this.columnDescription = columnDescription;
    }

}
