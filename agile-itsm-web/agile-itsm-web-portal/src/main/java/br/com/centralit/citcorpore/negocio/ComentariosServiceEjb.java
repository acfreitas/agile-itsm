/**
 *
 */
package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.ComentariosDTO;
import br.com.centralit.citcorpore.integracao.ComentariosDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

/**
 * @author valdoilo.damasceno
 *
 */
public class ComentariosServiceEjb extends CrudServiceImpl implements ComentariosService {

    private ComentariosDAO dao;

    @Override
    protected ComentariosDAO getDao() {
        if (dao == null) {
            dao = new ComentariosDAO();
        }
        return dao;
    }

    @Override
    public Collection<ComentariosDTO> consultarComentarios(final BaseConhecimentoDTO baseConhecimentoBean) throws ServiceException, Exception {
        return this.getDao().consultarComentarios(baseConhecimentoBean);
    }

    @Override
    public void restaurarGridComentarios(final DocumentHTML document, final Collection<ComentariosDTO> comentarios) {
        document.executeScript("deleteAllRows()");
        if (comentarios != null && !comentarios.isEmpty()) {
            int count = 0;
            document.executeScript("countComentario = 0");
            for (final ComentariosDTO comentarioBean : comentarios) {
                count++;

                document.executeScript("restoreRow()");
                document.executeScript("seqSelecionada = " + count);

                final String comentario = comentarioBean.getComentario() != null ? comentarioBean.getComentario() : "";
                final String nome = comentarioBean.getNome() != null ? comentarioBean.getNome() : "";
                final String email = comentarioBean.getEmail() != null ? comentarioBean.getEmail() : "";
                final String dataInicio = comentarioBean.getDataInicio() != null ? comentarioBean.getDataInicio().toString() : "";
                final String nota = comentarioBean.getNota() != null ? comentarioBean.getNota().toString() : "";

                document.executeScript("setRestoreComentario('" + comentarioBean.getIdComentario() + "'," + "'" + br.com.citframework.util.WebUtil.codificaEnter(comentario) + "',"
                        + "'" + br.com.citframework.util.WebUtil.codificaEnter(nome) + "'," + "'" + br.com.citframework.util.WebUtil.codificaEnter(email) + "'," + "'"
                        + br.com.citframework.util.WebUtil.codificaEnter(nota) + "'," + "'" + br.com.citframework.util.WebUtil.codificaEnter(dataInicio) + "')");
            }
            document.executeScript("exibeGrid()");
        } else {
            document.executeScript("ocultaGrid()");
        }
    }

    @Override
    public Double calcularNota(final Integer idBaseConhecimento) throws Exception {
        return this.getDao().calcularNota(idBaseConhecimento);
    }

    @Override
    public Integer consultarComentariosPorPeriodo(final BaseConhecimentoDTO baseConhecimentoDTO) throws ServiceException, Exception {
        List<ComentariosDTO> listaComentarios = new ArrayList<>();
        try {
            listaComentarios = (List<ComentariosDTO>) this.getDao().consultarComentariosPorPeriodo(baseConhecimentoDTO);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return listaComentarios.size();
    }

}
