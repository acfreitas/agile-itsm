package br.com.centralit.bpm.dto;

public class ElementoFluxoEmailDTO extends ElementoFluxoDTO {

    private static final long serialVersionUID = 7072776670004568782L;

    private String[] colGrupos;
    private String[] colUsuarios;
    private String[] colDestinatarios;

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

    @Override
    public void setDestinatariosEmail(final String destinatariosEmail) {
        if (destinatariosEmail != null) {
            colDestinatarios = destinatariosEmail.split(";");
        } else {
            colDestinatarios = new String[] {};
        }
        this.destinatariosEmail = destinatariosEmail;
    }

    public String[] getColDestinatarios() {
        return colDestinatarios;
    }

    public void setColDestinatarios(final String[] colDestinatarios) {
        destinatariosEmail = "";
        if (colDestinatarios != null && colDestinatarios.length > 0) {
            for (int i = 0; i < colDestinatarios.length; i++) {
                if (i > 0) {
                    destinatariosEmail += ";";
                }
                destinatariosEmail += colDestinatarios[i];
            }
        }
        this.colDestinatarios = colDestinatarios;
    }

}
