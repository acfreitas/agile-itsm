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
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.integracao.AnexoIncidenteDAO;
import br.com.citframework.integracao.Condition;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;

/**
 * @author breno.guimaraes
 */
public class AnexoIncidenteServiceEjb extends CrudServiceImpl implements AnexoIncidenteService {

    private AnexoIncidenteDAO dao;
    private File diretorioAnexosIncidentes;

    @Override
    protected AnexoIncidenteDAO getDao() {
        if (dao == null) {
            dao = new AnexoIncidenteDAO();
        }
        return dao;
    }

    @Override
    public void create(final Collection<UploadDTO> arquivosUpados, final Integer idIncidente) throws Exception {
        this.copiaArquivosEPersisteReferencias(arquivosUpados, idIncidente);
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
    private void copiaArquivosEPersisteReferencias(final Collection<UploadDTO> arquivosUpados, final Integer idIncidente) throws IOException, Exception {
        if (arquivosUpados == null || arquivosUpados.isEmpty()) {
            return;
        }
        for (final UploadDTO arquivo : arquivosUpados) {
            final File file = new File(arquivo.getPath());

            if (!file.getAbsolutePath().equalsIgnoreCase(this.getDiretorioAnexosIncidentes().getAbsoluteFile() + File.separator + file.getName())) {
                this.copiarArquivo(file, this.getDiretorioAnexosIncidentes() + File.separator + file.getName());

                if (file.getAbsolutePath().indexOf("tempUpload") > 1) {
                    file.delete();
                }
                this.criarAnexoBaseConhecimento(idIncidente, arquivo);
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
        anexoIncidente.setLink(this.getDiretorioAnexosIncidentes().getAbsolutePath() + File.separator + arquivo.getNameFile());
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

    private File getDiretorioAnexosIncidentes() {
        if (diretorioAnexosIncidentes == null) {
            diretorioAnexosIncidentes = new File(Constantes.getValue("DIRETORIO_ANEXOS_INCIDENTES"));
        }
        return diretorioAnexosIncidentes;
    }

    @Override
    public Collection<AnexoIncidenteDTO> consultarAnexosIncidentes(final Integer idIncidente) throws Exception {
        final ArrayList<Condition> condicoes = new ArrayList<Condition>();
        condicoes.add(new Condition("idIncidente", "=", idIncidente));
        return this.getDao().findByCondition(condicoes, null);
    }

}
