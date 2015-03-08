package br.com.centralit.citcorpore.integracao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import br.com.centralit.citcorpore.bean.ConexaoBIDTO;
import br.com.centralit.citcorpore.bean.ProcessamentoBatchDTO;
import br.com.centralit.citcorpore.negocio.ProcessamentoBatchService;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ConexaoBIDAO extends CrudDaoDefaultImpl {

    public ConexaoBIDAO() {
        super(Constantes.getValue("DATABASE_BI_ALIAS"), null);
    }

    @Override
    public String getTableName() {
        return "CONEXAOBI";
    }

    @Override
    public Collection find(IDto obj) throws PersistenceException {

        return null;
    }

    @Override
    public Collection<Field> getFields() {
        Collection<Field> listFields = new ArrayList<>();

        listFields.add(new Field("IDCONEXAOBI", "idConexaoBI", true, true, false, false));
        listFields.add(new Field("NOME", "nome", false, false, false, false));
        listFields.add(new Field("LINK", "link", false, false, false, false));
        listFields.add(new Field("LOGIN", "login", false, false, false, false));
        listFields.add(new Field("SENHA", "senha", false, false, false, false));
        listFields.add(new Field("STATUS", "status", false, false, false, false));
        listFields.add(new Field("DATAHORAULTIMAIMPORTACAO", "dataHoraUltimaImportacao", false, false, false, false));
        listFields.add(new Field("EMAILNOTIFICACAO", "emailNotificacao", false, false, false, false));
        listFields.add(new Field("CAMINHOPASTALOG", "caminhoPastaLog", false, false, false, false));
        listFields.add(new Field("QTDEDIASATRASO", "qtdeDiasAtraso", false, false, false, false));
        listFields.add(new Field("IDPROCESSAMENTOBATCHESPECIFICO", "idProcessamentoBatchEspecifico", false, false, false, false));
        listFields.add(new Field("IDPROCESSAMENTOBATCHEXCECAO", "idProcessamentoBatchExcecao", false, false, false, false));
        listFields.add(new Field("TIPOIMPORTACAO", "tipoImportacao", false, false, false, false));

        return listFields;
    }

    /**
     * Responsável por listar todas as conexões existentes ativas e inativas na tela Painel de Controle.
     *
     * @author thiago.barbosa
     */
    @Override
    public Collection list() throws PersistenceException {
        List list = new ArrayList();
        list.add(new Order("nome"));
        return super.list(list);
    }

    @Override
    public Class getBean() {
        return ConexaoBIDTO.class;
    }

    /**
     * Retorna o Total de Páginas (Quantidade Total de Conexões dividido pela QTDE de Itens por Página).
     *
     * @param itensPorPagina
     * @param listConexao
     * @param gerenciamentoServicosDTO
     * @return Integer - Número Total de Páginas
     * @throws Exception
     * @author valdoilo.damasceno
     * @since 05.11.2013
     */
    public Integer totalDePaginas(Integer itensPorPagina, Collection<ConexaoBIDTO> listConexao, ConexaoBIDTO conexaoBIDTO) throws PersistenceException {

        StringBuilder sql = new StringBuilder();
        List parametros = new ArrayList();
        /**
         * condição para verificar o total real de paginas quando for por meio do filtro, sem esta condição sempre estava trazendo o total geral de dados da tabela
         */
        if (conexaoBIDTO != null && conexaoBIDTO.getStatusFiltro() != null && !conexaoBIDTO.getStatusFiltro().equalsIgnoreCase("T")) {
            sql.append(" SELECT COUNT(*) ");
            sql.append(" FROM conexaobi  WHERE status = '"+ conexaoBIDTO.getStatusFiltro() + "'");
        } else {
            sql.append(" SELECT COUNT(*) ");
            sql.append(" FROM conexaobi");
        }

        Long totalLinhaLong = 0l;
        Long totalPagina = 0l;
        Integer total = 0;
        BigDecimal totalLinhaBigDecimal;
        Integer totalLinhaInteger;
        int intLimite = itensPorPagina;
        List lista = new ArrayList();

        lista = this.execSQL(sql.toString(), parametros.toArray());

        if (lista != null) {
            Object[] totalLinha = (Object[]) lista.get(0);
            if (totalLinha != null && totalLinha.length > 0) {
                totalLinhaInteger = (Integer) totalLinha[0];
                totalLinhaLong = Long.valueOf(totalLinhaInteger);
            }
        }

        if (totalLinhaLong > 0) {
            totalPagina = (totalLinhaLong / intLimite);
            if (totalLinhaLong % intLimite != 0) {
                totalPagina = totalPagina + 1;
            }
        }

        total = Integer.valueOf(totalPagina.toString());

        return total;
    }

    /**
     * Encontra a ConexãoBI relacionada ao Processamento Batch executado
     * @param idProcessamentoEspecifico
     * @return
     * @throws Exception
     * @author euler.ramos
     */
    public ConexaoBIDTO findByIdProcessBatch(Integer idProcessamentoBatch) throws PersistenceException {
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
        //and status <> 'I' : É necessário saber se há conexão vinculada a este processamento, mesmo estando inativa!
        String sql = "SELECT " + campos + " FROM " + getTableName() + " WHERE ((idprocessamentobatchespecifico = ?) or (idprocessamentobatchexcecao = ?))";
        parametro.add(idProcessamentoBatch);
        parametro.add(idProcessamentoBatch);
        resp = this.execSQL(sql, parametro.toArray());

        List result = engine.listConvertion(getBean(), resp, listRetorno);
        return (((result == null)||(result.size()<=0)) ? new ConexaoBIDTO() : (ConexaoBIDTO) result.get(0));
    }

    /**
     * Verifica se o registro informado já consta gravado no BD.
     *
     * @param conexaoBIDTO
     * @return boolean
     * @throws Exception
     */
    public boolean jaExisteRegistroComMesmoNome(ConexaoBIDTO conexaoBIDTO) throws PersistenceException {
        ArrayList<Condition> condicoes = new ArrayList<Condition>();
        condicoes.add(new Condition("nome", "=", conexaoBIDTO.getNome()));
        condicoes.add(new Condition("idConexaoBI", "<>", conexaoBIDTO.getIdConexaoBI()));
        Collection retorno = null;
        retorno = super.findByCondition(condicoes, null);
        if (retorno != null) {
            if (retorno.size() > 0) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * Verifica se o registro informado já consta gravado no BD.
     *
     * @param conexaoBIDTO
     * @return boolean
     * @throws Exception
     */
    public boolean jaExisteRegistroComMesmoLink(ConexaoBIDTO conexaoBIDTO) throws PersistenceException {
        ArrayList<Condition> condicoes = new ArrayList<Condition>();
        condicoes.add(new Condition("link", "=", conexaoBIDTO.getLink()));
        condicoes.add(new Condition("link", "<>", ""));

        if (conexaoBIDTO != null && conexaoBIDTO.getIdConexaoBI() != null) {
            condicoes.add(new Condition("idConexaoBI", "<>", conexaoBIDTO.getIdConexaoBI()));
        }

        Collection retorno = null;
        retorno = super.findByCondition(condicoes, null);
        if (retorno != null) {
            if (retorno.size() > 0) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * Metodo para tratar paginação
     * @author thiago.barbosa
     * @param conexaoBI
     * @param pgAtual
     * @param qtdPaginacao
     * @return
     * @throws Exception
     */
    public Collection<ConexaoBIDTO> listarConexoesPaginadas(Collection<ConexaoBIDTO> conexaoBIDTO, Integer pgAtual, Integer qtdPaginacao) throws PersistenceException {
        List listRetorno = new ArrayList();
        listRetorno.add("idConexaoBI");
        listRetorno.add("nome");
        listRetorno.add("link");
        listRetorno.add("login");
        listRetorno.add("senha");
        listRetorno.add("status");
        listRetorno.add("dataHoraUltimaImportacao");
        listRetorno.add("emailNotificacao");
        listRetorno.add("caminhoPastaLog");
        listRetorno.add("qtdeDiasAtraso");
        listRetorno.add("idProcessamentoBatchEspecifico");
        listRetorno.add("idProcessamentoBatchExcecao");
        listRetorno.add("tipoImportacao");

        StringBuilder sql = new StringBuilder();
        sql.append(" ;WITH TabelaTemporaria AS ( ");
        sql.append("SELECT idConexaoBI, nome, link, login, senha, status, dataHoraUltimaImportacao, emailNotificacao, ");
        sql.append("caminhoPastaLog, qtdeDiasAtraso, idProcessamentoBatchEspecifico, idProcessamentoBatchExcecao, tipoImportacao");
        sql.append(" , ROW_NUMBER() OVER (ORDER BY idConexaoBI) AS Row ");
        sql.append("  FROM conexaobi ");
        Integer quantidadePaginator2 = new Integer(0);
        if (pgAtual > 0) {
            quantidadePaginator2 = qtdPaginacao * pgAtual;
            pgAtual = (pgAtual * qtdPaginacao) - qtdPaginacao;
        } else {
            quantidadePaginator2 = qtdPaginacao;
            pgAtual = 0;
        }
        sql.append(" ) SELECT * FROM TabelaTemporaria WHERE Row> " + pgAtual + " and Row<" + (quantidadePaginator2 + 1) + " ");
        List lista = new ArrayList();

        lista = this.execSQL(sql.toString(), null);

        if (lista != null && !lista.isEmpty()) {
            return engine.listConvertion(ConexaoBIDTO.class, lista, listRetorno);
        } else {
            return null;
        }
    }

    /**
     * Metodo para tratar paginação
     * @author thiago.barbosa
     * @param conexaoBI
     * @param pgAtual
     * @param qtdPaginacao
     * @return
     * @throws Exception
     */
    public Collection<ConexaoBIDTO> listarConexoesPaginadasFiltradas(ConexaoBIDTO conexaoBIDTO, Integer pgAtual, Integer qtdPaginacao) throws PersistenceException {
        List listRetorno = new ArrayList();
        listRetorno.add("idConexaoBI");
        listRetorno.add("nome");
        listRetorno.add("link");
        listRetorno.add("login");
        listRetorno.add("senha");
        listRetorno.add("status");
        listRetorno.add("dataHoraUltimaImportacao");
        listRetorno.add("emailNotificacao");
        listRetorno.add("caminhoPastaLog");
        listRetorno.add("qtdeDiasAtraso");
        listRetorno.add("idProcessamentoBatchEspecifico");
        listRetorno.add("idProcessamentoBatchExcecao");
        listRetorno.add("tipoImportacao");

        StringBuilder sql = new StringBuilder();
        sql.append(" ;WITH TabelaTemporaria AS ( ");
        sql.append("SELECT idConexaoBI, nome, link, login, senha, status, dataHoraUltimaImportacao, emailNotificacao, ");
        sql.append("caminhoPastaLog, qtdeDiasAtraso, idProcessamentoBatchEspecifico, idProcessamentoBatchExcecao, tipoImportacao");
        sql.append(" , ROW_NUMBER() OVER (ORDER BY idConexaoBI) AS Row ");
        sql.append("  FROM conexaobi WHERE status ='"+ conexaoBIDTO.getStatusFiltro() + "'");
        Integer quantidadePaginator2 = new Integer(0);
        if (pgAtual > 0) {
            quantidadePaginator2 = qtdPaginacao * pgAtual;
            pgAtual = (pgAtual * qtdPaginacao) - qtdPaginacao;
        } else {
            quantidadePaginator2 = qtdPaginacao;
            pgAtual = 0;
        }
        sql.append(" ) SELECT * FROM TabelaTemporaria WHERE Row> " + pgAtual + " and Row<" + (quantidadePaginator2 + 1) + " ");
        List lista = new ArrayList();

        lista = this.execSQL(sql.toString(), null);

        if (lista != null && !lista.isEmpty()) {
            return engine.listConvertion(ConexaoBIDTO.class, lista, listRetorno);
        } else {
            return null;
        }
    }

    /**
     * @return
     * @author euler.ramos
     */
    public Collection<ConexaoBIDTO> listarConexoesAtivas() {
        List result;
        result = null;
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

            String sql = "SELECT " + campos + " FROM " + getTableName()+" where status<>'I'";

            resp = this.execSQL(sql, parametro.toArray());

            result = engine.listConvertion(getBean(), resp, listRetorno);
        } catch (PersistenceException e) {
            e.printStackTrace();
            result = null;
        } catch (Exception e) {
            e.printStackTrace();
            result = null;
        }
        return (((result == null)||(result.size()<=0)) ? new ArrayList<ConexaoBIDTO>() : result);
    }

    /**
     * @param conexaoBIDTO
     * @return
     * @throws ServiceException
     * @throws Exception
     * @author euler.ramos
     */
    public boolean temAgendamentoAtivo(ConexaoBIDTO conexaoBIDTO) throws ServiceException {
        ProcessamentoBatchService processamentoBatchService = (ProcessamentoBatchService) ServiceLocator.getInstance().getService(ProcessamentoBatchService.class, null);
        ProcessamentoBatchDTO processamentoBatchDTO;
        if (conexaoBIDTO.getIdProcessamentoBatchEspecifico()!=null){
            processamentoBatchDTO =processamentoBatchService.findById(conexaoBIDTO.getIdProcessamentoBatchEspecifico());
            if ((processamentoBatchDTO!=null)&&(processamentoBatchDTO.getSituacao()!=null)&&(processamentoBatchDTO.getSituacao().equalsIgnoreCase("A"))){
                return true;
            }
        }
        if (conexaoBIDTO.getIdProcessamentoBatchExcecao()!=null){
            processamentoBatchDTO =processamentoBatchService.findById(conexaoBIDTO.getIdProcessamentoBatchExcecao());
            if ((processamentoBatchDTO!=null)&&(processamentoBatchDTO.getSituacao()!=null)&&(processamentoBatchDTO.getSituacao().equalsIgnoreCase("A"))){
                return true;
            }
        }
        return false;
    }

    /**
     * @return
     * @throws ServiceException
     * @throws Exception
     * @author euler.ramos
     * Retorna as Conexões Ativas, Automáticas e que não possuem agendamento ativo nem específico e nem de exceção
     */
    public ArrayList<ConexaoBIDTO> listarConexoesAutomaticasSemAgendEspOuExcecao() throws ServiceException {
        ArrayList<ConexaoBIDTO> result = new ArrayList<ConexaoBIDTO>();
        ArrayList<ConexaoBIDTO> todasConexoes = (ArrayList<ConexaoBIDTO>) this.listarConexoesAtivas();
        for (ConexaoBIDTO conexaoBIDTO : todasConexoes) {
            //Filtrando somente as Automáticas
            if ((conexaoBIDTO.getTipoImportacao()!=null)&&(conexaoBIDTO.getTipoImportacao().equalsIgnoreCase("A"))){
                if (!this.temAgendamentoAtivo(conexaoBIDTO)) {
                    result.add(conexaoBIDTO);
                }
            }
        }
        return result;
    }

    public Collection findByIdConexao(ConexaoBIDTO conexaoBIDTO) throws PersistenceException {
        List condicao = new ArrayList();
        //List ordenacao = new ArrayList();

        condicao.add(new Condition("idconexaobi", "=", conexaoBIDTO.getIdConexaoBI()));
        //ordenacao.add(new Order("nome"));
        //condicao.add(new Condition(Condition.AND, "dataFim", "is", null));

        return super.findByCondition(condicao, null);
    }
}