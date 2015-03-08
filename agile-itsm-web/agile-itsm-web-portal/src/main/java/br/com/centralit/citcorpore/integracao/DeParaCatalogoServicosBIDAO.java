package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import br.com.centralit.citcorpore.bean.DeParaCatalogoServicosBIDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class DeParaCatalogoServicosBIDAO extends CrudDaoDefaultImpl {

    public DeParaCatalogoServicosBIDAO() {
        super(Constantes.getValue("DATABASE_BI_ALIAS"), null);
    }

    @Override
    public Collection find(IDto obj) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idservicode", "idServicoDe", true, false, false, false));
        listFields.add(new Field("idservicopara", "idServicoPara", true, false, false, false));
        listFields.add(new Field("idconexaobi", "idConexaoBI", true, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return "catalogodeparaservico";
    }

    @Override
    public Collection list() throws PersistenceException {
        List ordenacao = new ArrayList();
        ordenacao.add(new Order("idservicode"));
        return super.list(ordenacao);
    }

    @Override
    public Class getBean() {
        return DeParaCatalogoServicosBIDTO.class;
    }

    public DeParaCatalogoServicosBIDTO findByidServicoDe(Integer id, Integer idConexaoBI) {
        List result;
        try {
            List resp = new ArrayList();

            Collection fields = getFields();
            List parametro = new ArrayList();
            List listRetorno = new ArrayList();
            String campos = "";
            for (Iterator it = fields.iterator(); it.hasNext();) {
                Field field = (Field) it.next();
                if (!campos.trim().equalsIgnoreCase("")) {
                    campos = campos + ",";
                }
                campos = campos + field.getFieldDB();
                listRetorno.add(field.getFieldClass());
            }

            String sql = "SELECT " + campos + " FROM " + getTableName() + " WHERE idservicode = ? AND idconexaobi = ? ";
            parametro.add(id);
            parametro.add(idConexaoBI);

            resp = this.execSQL(sql, parametro.toArray());

            result = engine.listConvertion(getBean(), resp, listRetorno);
        } catch (PersistenceException e) {
            e.printStackTrace();
            result = null;
        } catch (Exception e) {
            e.printStackTrace();
            result = null;
        }
        return (DeParaCatalogoServicosBIDTO) (((result == null)||(result.size()<=0)) ? new DeParaCatalogoServicosBIDTO() : result.get(0));
    }

    public Collection<DeParaCatalogoServicosBIDTO> findByidServicoPara(Integer id, Integer idConexaoBI) {
        List result;
        try {
            List resp = new ArrayList();

            Collection fields = getFields();
            List parametro = new ArrayList();
            List listRetorno = new ArrayList();
            String campos = "";
            for (Iterator it = fields.iterator(); it.hasNext();) {
                Field field = (Field) it.next();
                if (!campos.trim().equalsIgnoreCase("")) {
                    campos = campos + ",";
                }
                campos = campos + field.getFieldDB();
                listRetorno.add(field.getFieldClass());
            }

            String sql = "";
            if (idConexaoBI != null) {
                sql = "SELECT " + campos + " FROM " + getTableName() + " WHERE idservicopara = ? AND idconexaobi = ? ";
                parametro.add(id);
                parametro.add(idConexaoBI);
            } else {
                sql = "SELECT " + campos + " FROM " + getTableName() + " WHERE idservicopara = ? ";
                parametro.add(id);
            }

            resp = this.execSQL(sql, parametro.toArray());

            result = engine.listConvertion(getBean(), resp, listRetorno);
        } catch (PersistenceException e) {
            e.printStackTrace();
            result = null;
        } catch (Exception e) {
            e.printStackTrace();
            result = null;
        }

        return (((result == null)||(result.size()<=0)) ? new ArrayList<DeParaCatalogoServicosBIDTO>() : result);
    }

    public Collection<DeParaCatalogoServicosBIDTO> findByidConexao(Integer idConexaoBI) {
        List result;
        try {
            List resp = new ArrayList();
            List parametro = new ArrayList();
            List listRetorno = new ArrayList();

            listRetorno.add("idServicoDe");
            listRetorno.add("nomeServicoDe");
            listRetorno.add("idServicoPara");
            listRetorno.add("nomeServicoPara");

            String sql = "SELECT S.idservicode,S.nomeservicode,SC.idservicopara,SC.nomeservicopara "+
                    "FROM ("+
                    "(SELECT servico.idservico AS idservicode,servico.nomeservico AS nomeservicode "+
                    "FROM servico "+
                    "WHERE servico.idconexaobi = ?) AS S "+
                    "FULL JOIN "+
                    "(SELECT * "+
                    "FROM catalogodeparaservico "+
                    "WHERE catalogodeparaservico.idconexaobi = ?) AS C ON S.idservicode = C.idservicode "+
                    "FULL JOIN "+
                    "(SELECT servicocorpore.idservicocorpore AS idservicopara,servicocorpore.nomeservico as nomeservicopara "+
                    "FROM servicocorpore) AS SC ON SC.idservicopara = C.idservicopara "+
                    ")";
            parametro.add(idConexaoBI);
            parametro.add(idConexaoBI);

            resp = this.execSQL(sql, parametro.toArray());

            result = engine.listConvertion(getBean(), resp, listRetorno);
        } catch (PersistenceException e) {
            e.printStackTrace();
            result = null;
        } catch (Exception e) {
            e.printStackTrace();
            result = null;
        }
        return (((result == null)||(result.size()<=0)) ? new ArrayList<DeParaCatalogoServicosBIDTO>() : result);
    }

}