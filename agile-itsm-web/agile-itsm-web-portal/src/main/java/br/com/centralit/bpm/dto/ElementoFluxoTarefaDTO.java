package br.com.centralit.bpm.dto;

public class ElementoFluxoTarefaDTO extends ElementoFluxoDTO {

    private static final long serialVersionUID = 1249652310414642178L;

    private String[] colGrupos;
    private String[] colUsuarios;

    @Override
    public void setGrupos(final String grupos) {
        if (grupos != null) {
            colGrupos = grupos.split(";");
        } else {
            colGrupos = new String[] {};
        }
        this.grupos = grupos;
    }

    @Override
    public void setUsuarios(final String usuarios) {
        if (usuarios != null) {
            colUsuarios = usuarios.split(";");
        } else {
            colUsuarios = new String[] {};
        }
        this.usuarios = usuarios;
    }

    public void setColGrupos(final String[] colGrupos) {
        grupos = "";
        if (colGrupos != null && colGrupos.length > 0) {
            for (int i = 0; i < colGrupos.length; i++) {
                if (i > 0) {
                    grupos += ";";
                }
                grupos += colGrupos[i];
            }
        }
        this.colGrupos = colGrupos;
    }

    public void setColUsuarios(final String[] colUsuarios) {
        usuarios = "";
        if (colUsuarios != null && colUsuarios.length > 0) {
            for (int i = 0; i < colUsuarios.length; i++) {
                if (i > 0) {
                    usuarios += ";";
                }
                usuarios += colUsuarios[i];
            }
        }
        this.colUsuarios = colUsuarios;
    }

    public String[] getColGrupos() {
        return colGrupos;
    }

    public String[] getColUsuarios() {
        return colUsuarios;
    }

}
