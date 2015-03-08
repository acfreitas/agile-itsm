package br.com.centralit.citcorpore.batch;

import java.io.File;

public class LimpaTemporarios {

    public void executar() throws Exception {
        System.out.println("CITSMart -> INICIANDO PROCESSO DE LIMPEZA DE ARQUIVOS TEMPORARIOS...");

        final String userDir = System.getProperty("user.dir");
        File dir = new File(userDir + "/tempReceitas");
        if (dir.exists() && dir.isDirectory()) {
            this.apagarDoDiretorio(dir.listFiles());
        }
        dir = new File(userDir + "/tempRelatorio");
        if (dir.exists() && dir.isDirectory()) {
            this.apagarDoDiretorio(dir.listFiles());
        }
        dir = new File(userDir + "/tempUpload");
        if (dir.exists() && dir.isDirectory()) {
            this.apagarDoDiretorio(dir.listFiles());
        }
        dir = new File(userDir + "/tempUploadAutoCadastro");
        if (dir.exists() && dir.isDirectory()) {
            this.apagarDoDiretorio(dir.listFiles());
        }
        dir = new File(userDir + "/temporario");
        if (dir.exists() && dir.isDirectory()) {
            this.apagarDoDiretorio(dir.listFiles());
        }
        dir = new File(userDir + "/tempInventario");
        if (dir.exists() && dir.isDirectory()) {
            this.apagarDoDiretorio(dir.listFiles());
        }

        System.out.println("CITSMart -> FINALIZANDO PROCESSO DE LIMPEZA DE ARQUIVOS TEMPORARIOS...");
    }

    private void apagarDoDiretorio(final File[] files) {
        for (final File file : files) {
            if (file.exists() && file.isDirectory()) {
                this.apagarDoDiretorio(file.listFiles());
            } else {
                final String name = file.getName();
                System.out.println("CITSMart -> Preparando para apagar arquivo temporario '" + name + "'...");
                try {
                    file.delete();
                    System.out.println("CITSMart -> arquivo temporario '" + name + "' apagado!");
                } catch (final Exception e) {
                    System.out.println("CITSMart -> Problemas ao apagar o arquivo temporario '" + name + "' !");
                }
            }
        }
    }

    public static void main(final String[] args) throws Exception {
        final LimpaTemporarios l = new LimpaTemporarios();
        l.executar();
    }

}
