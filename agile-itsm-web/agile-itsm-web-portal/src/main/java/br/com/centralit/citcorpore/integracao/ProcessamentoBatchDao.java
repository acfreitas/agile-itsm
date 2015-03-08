package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ProcessamentoBatchDTO;
import br.com.centralit.citcorpore.negocio.ConexaoBIService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.SQLConfig;

public class ProcessamentoBatchDao extends CrudDaoDefaultImpl {

    public ProcessamentoBatchDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection find(IDto arg0) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        Collection<Field> listFields = new ArrayList<>();

        listFields.add(new Field("idProcessamentoBatch", "idProcessamentoBatch", true, true, false, false));
        listFields.add(new Field("descricao", "descricao", false, false, false, false));
        listFields.add(new Field("expressaoCRON", "expressaoCRON", false, false, false, false));
        listFields.add(new Field("conteudo", "conteudo", false, false, false, false));
        listFields.add(new Field("tipo", "tipo", false, false, false, false));
        listFields.add(new Field("situacao", "situacao", false, false, false, false));

        return listFields;
    }

    @Override
    public String getTableName() {
        return "ProcessamentoBatch";
    }

    @Override
    public Collection list() throws PersistenceException {
        List<Order> list = new ArrayList<>();
        list.add(new Order("descricao"));
        return super.list(list);
    }

    @Override
    public Class<ProcessamentoBatchDTO> getBean() {
        return ProcessamentoBatchDTO.class;
    }

    public Collection getAtivos() throws Exception {
        List<Order> lstOrder = new ArrayList<>();
        List<Condition> lstCondicao = new ArrayList<>();

        lstCondicao.add(new Condition("situacao", "=", "A"));
        lstOrder.add(new Order("descricao"));

        return super.findByCondition(lstCondicao, lstOrder);
    }

    /**
     * Metodo que verifica se existe um registro com os mesmos dados na base de dados.
     *
     * @param processamentoBatch
     * @return
     * @throws Exception
     */
    public boolean existeDuplicidade(ProcessamentoBatchDTO processamentoBatch) throws Exception {
        List<Condition> condicao = new ArrayList<>();
        List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("descricao", "=", processamentoBatch.getDescricao()));
        ordenacao.add(new Order("descricao"));

        List result = (List) super.findByCondition(condicao, ordenacao);

        if (result != null && !result.isEmpty()) {
            return true;
        }

        return false;
    }

    public boolean existeDuplicidadeClasse(ProcessamentoBatchDTO processamentoBatch) throws Exception {
        List<Condition> condicao = new ArrayList<>();
        List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("conteudo", "like", processamentoBatch.getConteudo()));
        ordenacao.add(new Order("descricao"));

        List result = (List) super.findByCondition(condicao, ordenacao);

        if (result != null && !result.isEmpty()) {
            return true;
        }
        return false;
    }

    public ProcessamentoBatchDTO findById(Integer id) {
        List result;
        try {
            if (id == null) {
                id = 0;
            }
            List resp = new ArrayList<>();
            Collection<Field> fields = getFields();
            List<Integer> parametro = new ArrayList<>();
            List<String> listRetorno = new ArrayList<>();
            String campos = "";
            for (Field field : fields) {
                if (!campos.trim().equalsIgnoreCase("")) {
                    campos = campos + ",";
                }
                campos = campos + field.getFieldDB();
                listRetorno.add(field.getFieldClass());
            }
            String sql = "SELECT " + campos + " FROM " + getTableName() + " WHERE idprocessamentobatch=?";
            parametro.add(id);
            resp = this.execSQL(sql, parametro.toArray());
            result = engine.listConvertion(getBean(), resp, listRetorno);
        } catch (PersistenceException e) {
            e.printStackTrace();
            result = null;
        } catch (Exception e) {
            e.printStackTrace();
            result = null;
        }
        return (ProcessamentoBatchDTO) (((result == null) || (result.size() <= 0)) ? new ProcessamentoBatchDTO() : result.get(0));
    }

    public ProcessamentoBatchDTO getAgendamentoPadrao() throws Exception {
        ConexaoBIService conexaoBIService = (ConexaoBIService) ServiceLocator.getInstance().getService(ConexaoBIService.class, null);
        String idProcEspecificoOuExcecao = conexaoBIService.getIdProcEspecificoOuExcecao();
        List result;
        try {
            List resp = new ArrayList<>();
            Collection<Field> fields = getFields();
            List<Integer> parametro = new ArrayList<>();
            List<String> listRetorno = new ArrayList<>();
            String campos = "";
            for (Field field : fields) {
                if (!campos.trim().equalsIgnoreCase("")) {
                    campos = campos + ",";
                }
                campos = campos + field.getFieldDB();
                listRetorno.add(field.getFieldClass());
            }
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT " + campos + " FROM " + getTableName());

            if (CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.SQLSERVER)) {
                sql.append(" WHERE (CONVERT(VARCHAR(MAX), conteudo) = 'br.com.centralit.citcorpore.quartz.job.ImportacaoAutoBiCitsmart') and (situacao = 'A') ");
            } else if (CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.ORACLE)) {
                sql.append(" WHERE (TO_NCHAR(conteudo) = 'br.com.centralit.citcorpore.quartz.job.ImportacaoAutoBiCitsmart') and (situacao = 'A') ");
            } else {
                sql.append(" WHERE (conteudo = 'br.com.centralit.citcorpore.quartz.job.ImportacaoAutoBiCitsmart') and (situacao = 'A') ");
            }

            if ((idProcEspecificoOuExcecao != null) && (idProcEspecificoOuExcecao.length() > 0)) {
                sql.append("and (idprocessamentobatch not in (" + idProcEspecificoOuExcecao + "))");
            }
            resp = this.execSQL(sql.toString(), parametro.toArray());
            result = engine.listConvertion(getBean(), resp, listRetorno);
        } catch (PersistenceException e) {
            e.printStackTrace();
            result = null;
        } catch (Exception e) {
            e.printStackTrace();
            result = null;
        }
        return (ProcessamentoBatchDTO) (((result == null) || (result.size() <= 0)) ? new ProcessamentoBatchDTO() : result.get(0));
    }

}
