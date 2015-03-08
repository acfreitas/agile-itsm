package br.com.centralit.citcorpore.integracao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import br.com.centralit.citcorpore.bean.MatrizVisaoDTO;
import br.com.centralit.citcorpore.bean.ValorRecuperadoMatrizDTO;
import br.com.centralit.citcorpore.metainfo.bean.CamposObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.ObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.integracao.CamposObjetoNegocioDao;
import br.com.centralit.citcorpore.metainfo.integracao.ObjetoNegocioDao;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilFormatacao;

public class MatrizVisaoDao extends CrudDaoDefaultImpl {

    public MatrizVisaoDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idMatriz", "idMatriz", true, true, false, false));
        listFields.add(new Field("idVisao", "idVisao", false, false, false, false));
        listFields.add(new Field("idObjetoNegocio", "idObjetoNegocio", false, false, false, false));
        listFields.add(new Field("idCamposObjetoNegocio1", "idCamposObjetoNegocio1", false, false, false, false));
        listFields.add(new Field("idCamposObjetoNegocio2", "idCamposObjetoNegocio2", false, false, false, false));
        listFields.add(new Field("idCamposObjetoNegocio3", "idCamposObjetoNegocio3", false, false, false, false));
        listFields.add(new Field("strInfo", "strInfo", false, false, false, false));
        listFields.add(new Field("nomeCampo1", "nomeCampo1", false, false, false, false));
        listFields.add(new Field("nomeCampo2", "nomeCampo2", false, false, false, false));
        listFields.add(new Field("nomeCampo3", "nomeCampo3", false, false, false, false));
        listFields.add(new Field("descricaoCampo1", "descricaoCampo1", false, false, false, false));
        listFields.add(new Field("descricaoCampo2", "descricaoCampo2", false, false, false, false));
        listFields.add(new Field("descricaoCampo3", "descricaoCampo3", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return this.getOwner() + "MatrizVisao";
    }

    @Override
    public Class<MatrizVisaoDTO> getBean() {
        return MatrizVisaoDTO.class;
    }

    public Collection findByIdVisao(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("idVisao", "=", parm));
        ordenacao.add(new Order("idMatriz"));
        return super.findByCondition(condicao, ordenacao);
    }

    public void deleteByIdVisao(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        condicao.add(new Condition("idVisao", "=", parm));
        super.deleteByCondition(condicao);
    }

    public String recuperaValor(final String sql, final Object[] parms) throws PersistenceException {
        final Collection colDados = this.execSQL(sql, parms);
        if (colDados != null && colDados.size() > 0) {
            for (final Iterator it = colDados.iterator(); it.hasNext();) {
                final Object[] obj = (Object[]) it.next();
                if (obj != null && obj[0] == null) {
                    continue;
                } else if (obj != null && Double.class.isInstance(obj[0])) {
                    final String str = UtilFormatacao.formatDouble((Double) obj[0], 2);
                    return str;
                } else if (obj != null && BigDecimal.class.isInstance(obj[0])) {
                    final String str = UtilFormatacao.formatBigDecimal((BigDecimal) obj[0], 2);
                    return str;
                } else {
                    if (obj != null) {
                        return obj[0].toString();
                    }
                }
            }
        }
        return "";
    }

    public ValorRecuperadoMatrizDTO recuperaDadosMatriz(final MatrizVisaoDTO matrizVisaoDTO) throws PersistenceException {
        final ValorRecuperadoMatrizDTO valorRecuperadoMatrizDTO = new ValorRecuperadoMatrizDTO();
        final ObjetoNegocioDao objetoNegocioDao = new ObjetoNegocioDao();
        final CamposObjetoNegocioDao camposObjetoNegocioDao = new CamposObjetoNegocioDao();
        ObjetoNegocioDTO objetoNegocioDTO = new ObjetoNegocioDTO();
        objetoNegocioDTO.setIdObjetoNegocio(matrizVisaoDTO.getIdObjetoNegocio());
        objetoNegocioDTO = (ObjetoNegocioDTO) objetoNegocioDao.restore(objetoNegocioDTO);
        String paramOrder = "1";
        if (objetoNegocioDTO != null) {
            valorRecuperadoMatrizDTO.setObjetoNegocioDTO(objetoNegocioDTO);
            final StringBuilder sql = new StringBuilder();
            sql.append("SELECT ");

            CamposObjetoNegocioDTO camposObjetoNegocioDTO = new CamposObjetoNegocioDTO();
            camposObjetoNegocioDTO.setIdCamposObjetoNegocio(matrizVisaoDTO.getIdCamposObjetoNegocio1());
            camposObjetoNegocioDTO = (CamposObjetoNegocioDTO) camposObjetoNegocioDao.restore(camposObjetoNegocioDTO);
            if (camposObjetoNegocioDTO != null) {
                sql.append(camposObjetoNegocioDTO.getNomeDB());
                valorRecuperadoMatrizDTO.setCamposObjetoNegocioChaveDTO(camposObjetoNegocioDTO);
            }

            camposObjetoNegocioDTO = new CamposObjetoNegocioDTO();
            camposObjetoNegocioDTO.setIdCamposObjetoNegocio(matrizVisaoDTO.getIdCamposObjetoNegocio2());
            if (matrizVisaoDTO.getIdCamposObjetoNegocio2() != null) {
                camposObjetoNegocioDTO = (CamposObjetoNegocioDTO) camposObjetoNegocioDao.restore(camposObjetoNegocioDTO);
                if (camposObjetoNegocioDTO != null) {
                    sql.append(",");
                    sql.append(camposObjetoNegocioDTO.getNomeDB());
                    valorRecuperadoMatrizDTO.setCamposObjetoNegocioApres1DTO(camposObjetoNegocioDTO);
                    paramOrder = "2";
                }
            }

            camposObjetoNegocioDTO = new CamposObjetoNegocioDTO();
            camposObjetoNegocioDTO.setIdCamposObjetoNegocio(matrizVisaoDTO.getIdCamposObjetoNegocio3());
            if (matrizVisaoDTO.getIdCamposObjetoNegocio3() != null) {
                camposObjetoNegocioDTO = (CamposObjetoNegocioDTO) camposObjetoNegocioDao.restore(camposObjetoNegocioDTO);
                if (camposObjetoNegocioDTO != null) {
                    sql.append(",");
                    sql.append(camposObjetoNegocioDTO.getNomeDB());
                    valorRecuperadoMatrizDTO.setCamposObjetoNegocioApres2DTO(camposObjetoNegocioDTO);
                    paramOrder = "2";
                }
            }

            sql.append(" FROM ");
            sql.append(objetoNegocioDTO.getNomeTabelaDB());
            sql.append(" ");
            sql.append("ORDER BY ");
            sql.append(paramOrder);

            final Collection colDados = this.execSQL(sql.toString(), null);
            valorRecuperadoMatrizDTO.setColDados(colDados);
        }
        return valorRecuperadoMatrizDTO;
    }

}
