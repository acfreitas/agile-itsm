/**

 * CentralIT - CITSmart
 */
package br.com.centralit.citcorpore.negocio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.AnexoIncidenteDTO;
import br.com.centralit.citcorpore.bean.AnexoMudancaDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.integracao.AnexoMudancaDAO;
import br.com.citframework.integracao.Condition;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;

/**
 * @author breno.guimaraes
 */
public class AnexoMudancaServiceEjb extends CrudServiceImpl implements AnexoMudancaService {

    private AnexoMudancaDAO dao;
    private File diretorioAnexosMudancas;

    @Override
    protected AnexoMudancaDAO getDao() {
        if (dao == null) {
            dao = new AnexoMudancaDAO();
        }
        return dao;
    }

    @Override
    public void create(final Collection<UploadDTO> arquivosUpados, final Integer idMudanca) throws Exception {
        this.copiaArquivosEPersisteReferencias(arquivosUpados, idMudanca);
    }

    /**
     * Copia da pasta temporária para a pasta definitiva definida nas constantes e persiste no banco
     * os relacionamentos entre anexos e serviços.
     *
     * @author breno.guimaraes
     * @param arquivosUpados
     * @param idIncidente
     * @throws IOException
     * @throws Exception
     */
    private void copiaArquivosEPersisteReferencias(final Collection<UploadDTO> arquivosUpados, final Integer idMudanca) throws IOException, Exception {
        if (arquivosUpados == null || arquivosUpados.isEmpty()) {
            return;
        }
        for (final UploadDTO arquivo : arquivosUpados) {
            final File file = new File(arquivo.getPath());

            if (!file.getAbsolutePath().equalsIgnoreCase(this.getDiretorioAnexosMudancas().getAbsoluteFile() + File.separator + file.getName())) {
                this.copiarArquivo(file, this.getDiretorioAnexosMudancas() + File.separator + file.getName());

                if (file.getAbsolutePath().indexOf("tempUpload") > 1) {
                    file.delete();
                }
                this.criarAnexoBaseConhecimento(idMudanca, arquivo);
            }
        }
    }

    private void criarAnexoBaseConhecimento(final Integer idIncidente, final UploadDTO arquivo) throws Exception {
        final AnexoIncidenteDTO anexoIncidente = new AnexoIncidenteDTO();
        anexoIncidente.setDataInicio(UtilDatas.getDataAtual());

        final String extensao[] = arquivo.getNameFile().split("\\.");
        if (extensao.length > 1) {
            anexoIncidente.setExtensao(extensao[extensao.length - 1]);
        }
        anexoIncidente.setNomeAnexo(extensao[0]);

        anexoIncidente.setIdIncidente(idIncidente);
        anexoIncidente.setLink(this.getDiretorioAnexosMudancas().getAbsolutePath() + File.separator + arquivo.getNameFile());
        anexoIncidente.setDescricao(arquivo.getDescricao());

        this.getDao().create(anexoIncidente);
    }

    public void copiarArquivo(final File fonte, final String destino) throws IOException {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(fonte);
            out = new FileOutputStream(new File(destino));

            final byte[] buf = new byte[1024];
            int len;
            try {
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }

        } catch (final FileNotFoundException e) {
            System.out.println("Arquivo não encontrado");
            e.printStackTrace();
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }

    private File getDiretorioAnexosMudancas() {
        if (diretorioAnexosMudancas == null) {
            diretorioAnexosMudancas = new File(Constantes.getValue("DIRETORIO_ANEXOS_MUDANCAS"));
        }
        return diretorioAnexosMudancas;
    }

    @Override
    public Collection<AnexoMudancaDTO> consultarAnexosMudanca(final Integer idMudanca) throws Exception {
        final ArrayList<Condition> condicoes = new ArrayList<Condition>();
        condicoes.add(new Condition("idMudanca", "=", idMudanca));
        return this.getDao().findByCondition(condicoes, null);
    }

}
