package br.com.centralit.asterisk;

public class ChamadaDTO {

    private String numeroOrigem;
    private String numeroDestino;

    public String getNumeroOrigem() {
        return numeroOrigem;
    }

    public void setNumeroOrigem(String numeroOrigem) {
        this.numeroOrigem = numeroOrigem;
    }

    public String getNumeroDestino() {
        return numeroDestino;
    }

    public void setNumeroDestino(String numeroDestino) {
        this.numeroDestino = numeroDestino;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((numeroDestino == null) ? 0 : numeroDestino.hashCode());
        result = prime * result + ((numeroOrigem == null) ? 0 : numeroOrigem.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            ChamadaDTO objeto = (ChamadaDTO) obj;
            return ((this.getNumeroOrigem().equals(objeto.getNumeroOrigem())) && (this.getNumeroDestino().equals(objeto.getNumeroDestino())));
        } else {
            return false;
        }
    }

}
