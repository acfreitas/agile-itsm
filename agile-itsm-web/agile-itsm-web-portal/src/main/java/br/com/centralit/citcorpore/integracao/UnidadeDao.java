package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.UnidadeDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.UnidadeService;
import br.com.centralit.citcorpore.util.Arvore;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.SQLConfig;

public class UnidadeDao extends CrudDaoDefaultImpl {

    public UnidadeDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    private static final String SQL_NOMEUNIDADE = "select idunidade, nome from unidade where idunidade not in(select idunidade from usuario)";

    private static final String SQL_NOMEEMPREGADO = "select idunidade, nome from unidade where idunidade not in(select idunidade from empregados)";

    @Override
    public Class<UnidadeDTO> getBean() {
        return UnidadeDTO.class;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idUnidade", "idUnidade", true, true, false, false));
        listFields.add(new Field("idUnidadePai", "idUnidadePai", false, false, false, false));
        listFields.add(new Field("idTipoUnidade", "idTipoUnidade", false, false, false, false));
        listFields.add(new Field("idEmpresa", "idEmpresa", false, false, false, false));
        listFields.add(new Field("nome", "nome", false, false, false, false));
        listFields.add(new Field("dataInicio", "dataInicio", false, false, false, false));
        listFields.add(new Field("dataFim", "dataFim", false, false, false, false));
        listFields.add(new Field("descricao", "descricao", false, false, false, false));
        listFields.add(new Field("email", "email", false, false, false, false));
        listFields.add(new Field("idEndereco", "idEndereco", false, false, false, false));
        listFields.add(new Field("aceitaEntregaProduto", "aceitaEntregaProduto", false, false, false, false));
        return listFields;
    }

    public Collection findSemPai() throws Exception {
        final String sql = "SELECT idUnidade, idUnidadePai, nome, descricao, dataInicio FROM Unidade WHERE idUnidadePai IS NULL AND dataFim IS NULL ORDER BY nome ";
        final List<?> colDados = this.execSQL(sql, null);
        if (colDados != null) {
            final List<String> fields = new ArrayList<>();
            fields.add("idUnidade");
            fields.add("idUnidadePai");
            fields.add("nome");
            fields.add("descricao");
            fields.add("dataInicio");
            return this.listConvertion(UnidadeDTO.class, colDados, fields);
        }
        return null;
    }

    public Collection findSemPaiMultContrato(final Integer idContrato) throws Exception {
        final String sql = "SELECT idUnidade, idUnidadePai, nome, descricao, dataInicio FROM Unidade WHERE idUnidadePai IS NULL AND dataFim IS NULL and idUnidade in "
                + "(select idUnidade from ContratosUnidades where idContrato = ?) ORDER BY nome ";
        final List<?> colDados = this.execSQL(sql, new Object[] {idContrato});
        if (colDados != null) {
            final List<String> fields = new ArrayList<>();
            fields.add("idUnidade");
            fields.add("idUnidadePai");
            fields.add("nome");
            fields.add("descricao");
            fields.add("dataInicio");
            return this.listConvertion(UnidadeDTO.class, colDados, fields);
        }
        return null;
    }

    public Collection findByIdPai(final Integer idPai) throws Exception {
        final String sql = "SELECT idUnidade, idUnidadePai, nome, descricao, dataInicio FROM Unidade WHERE idUnidadePai = ? AND dataFim IS NULL ORDER BY nome ";
        final List<?> colDados = this.execSQL(sql, new Object[] {idPai});
        if (colDados != null) {
            final List<String> fields = new ArrayList<>();
            fields.add("idUnidade");
            fields.add("idUnidadePai");
            fields.add("nome");
            fields.add("descricao");
            fields.add("dataInicio");
            return this.listConvertion(UnidadeDTO.class, colDados, fields);
        }
        return null;
    }

    @Override
    public String getTableName() {
        return "Unidade";
    }

    @Override
    public Collection<UnidadeDTO> find(final IDto obj) throws PersistenceException {
        return null;
    }

    @Override
    public Collection list() throws PersistenceException {
        final List<Order> list = new ArrayList<>();
        list.add(new Order("nome"));
        return super.list(list);
    }

    /**
     * @return
     * @throws Exception
     *             Lista idunidade e nome de unidade que não estejam cadastrado na tabela USUARIO
     */
    public Collection findByIdUnidade() throws Exception {
        final String sql = SQL_NOMEUNIDADE;
        final List<Object> lista = this.execSQL(sql, null);
        final List<String> listRetorno = new ArrayList<>();
        listRetorno.add("idUnidade");
        listRetorno.add("nome");
        return engine.listConvertion(this.getBean(), lista, listRetorno);
    }

    /**
     * @return
     * @throws Exception
     *             Lista idunidade e nome de unidade que não estejam cadastrado na tabela EMPREGADOS
     */
    public Collection findByIdEmpregado() throws Exception {
        final String sql = SQL_NOMEEMPREGADO;
        final List<?> lista = this.execSQL(sql, null);
        final List<String> listRetorno = new ArrayList<>();
        listRetorno.add("idUnidade");
        listRetorno.add("nome");
        return engine.listConvertion(this.getBean(), lista, listRetorno);
    }

    /**
     * Verifica se idUnidade informado possui filho.
     *
     * @param idUnidade
     * @return true - possui; false - não possui
     * @throws PersistenceException
     */
    public boolean verificarSeUnidadePossuiFilho(final Integer idUnidade) throws PersistenceException {
        final StringBuilder sql = new StringBuilder();
        final List<Integer> parametro = new ArrayList<>();
        sql.append("SELECT idunidade FROM " + this.getTableName() + " where idunidadepai = ? and datafim is null ");
        parametro.add(idUnidade);
        final List<?> list = this.execSQL(sql.toString(), parametro.toArray());
        if (list != null && !list.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * Verifica se Unidade informada existe.
     *
     * @param unidadeDTO
     * @return true - existe; false - não existe;
     * @throws PersistenceException
     */
    public boolean verificarSeUnidadeExiste(final UnidadeDTO unidadeDTO) throws PersistenceException {
        final List<Object> parametro = new ArrayList<>();
        final StringBuilder sql = new StringBuilder();

        sql.append("SELECT idunidade FROM " + this.getTableName() + "  WHERE  nome = ? AND datafim IS NULL AND idunidade <> ? ");
        parametro.add(unidadeDTO.getNome());
        parametro.add(unidadeDTO.getIdUnidade() == null ? 0 : unidadeDTO.getIdUnidade());

        final List<?> list = this.execSQL(sql.toString(), parametro.toArray());

        if (list != null && !list.isEmpty()) {
            return true;
        }
        return false;
    }

    public Collection findByNomeUnidade(final UnidadeDTO unidadeDTO) throws Exception {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("nome", "=", unidadeDTO.getNome()));
        condicao.add(new Condition(Condition.AND, "dataFim", "is", null));
        ordenacao.add(new Order("nome"));
        return super.findByCondition(condicao, ordenacao);
    }

    /**
     * Filtra unidade pelo id
     *
     * @param idUnidade
     * @return Collection
     * @throws Exception
     */
    public Collection findById(final Integer idUnidade) throws Exception {
        final StringBuilder sql = new StringBuilder();
        final List<Integer> parametro = new ArrayList<>();
        sql.append("SELECT idunidade,nome,idunidadepai FROM " + this.getTableName() + " where idUnidade = ? and datafim is null ");
        parametro.add(idUnidade);
        final List<?> list = this.execSQL(sql.toString(), parametro.toArray());
        final List<String> listRetorno = new ArrayList<>();
        listRetorno.add("idUnidade");
        listRetorno.add("nome");
        listRetorno.add("idUnidadePai");
        return engine.listConvertion(this.getBean(), list, listRetorno);
    }

    /**
     * Lista as unidades ativas do contrato se o id passado for maior que zero do contrário traz todas as unidades ativas.
     *
     * @author euler.ramos
     * @param idContrato
     * @return
     * @throws Exception
     */
    public Collection<UnidadeDTO> listarAtivasPorContrato(final Integer idContrato) {
        List result;
        try {
            final List<Integer> parametro = new ArrayList<>();
            final List<String> listRetorno = new ArrayList<>();

            listRetorno.add("idUnidade");
            listRetorno.add("nome");

            final StringBuilder sql = new StringBuilder();
            sql.append("select u.idunidade,u.nome from unidade as u ");
            if (idContrato > 0) {
                sql.append("join contratosunidades as c on c.idcontrato=? and u.idunidade = c.idunidade ");
                parametro.add(idContrato);
            }
            sql.append("where u.dataFim IS NULL ");
            sql.append("order by u.nome");

            final List<?> resp = this.execSQL(sql.toString(), parametro.toArray());

            result = engine.listConvertion(this.getBean(), resp, listRetorno);
        } catch (final PersistenceException e) {
            e.printStackTrace();
            result = null;
        } catch (final Exception e) {
            e.printStackTrace();
            result = null;
        }
        return result == null || result.size() <= 0 ? new ArrayList<UnidadeDTO>() : result;
    }

    public Collection<UnidadeDTO> listUnidadePorContrato(final Integer idcontrato) throws Exception {
        final StringBuilder sql = new StringBuilder();
        final List<Integer> parametro = new ArrayList<>();
        sql.append("SELECT uni.idunidade, uni.nome FROM unidade uni ");
        sql.append("LEFT JOIN contratosunidades contratoUnidade ON uni.idunidade = contratoUnidade.idunidade ");
        sql.append("LEFT JOIN contratos contr ON contratoUnidade.idcontrato = contr.idcontrato ");
        sql.append("WHERE contr.idcontrato = ?");
        parametro.add(idcontrato);
        final List<?> list = this.execSQL(sql.toString(), parametro.toArray());
        final List<String> listRetorno = new ArrayList<>();
        listRetorno.add("idUnidade");
        listRetorno.add("nome");
        return engine.listConvertion(this.getBean(), list, listRetorno);
    }

    /**
     * Procura pelas unidades ativas do contrato se o id passado for maior que zero do contrário traz todas as unidades ativas que possuem no nome o texto passado como parâmetro.
     *
     * @author euler.ramos
     * @param nome
     * @param idContrato
     * @return
     * @throws Exception
     */
    public Collection<UnidadeDTO> findByNomeEcontrato(String nome, final Integer idContrato, final Integer limite) {
        List result;
        try {
            final List<Object> parametro = new ArrayList<>();
            final List<String> listRetorno = new ArrayList<>();

            listRetorno.add("idUnidade");
            listRetorno.add("nome");
            listRetorno.add("idUnidadePai");

            final StringBuilder sql = new StringBuilder();
            sql.append("select ");

            //Limitando registros para não pesar a construção da lista hierárquica no autocomplete
			if (CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.SQLSERVER) && limite != null && limite.intValue() > 0 ){
				if ((limite != null)&&(limite.intValue()>0)){
					sql.append("TOP "+limite.toString()+" ");
				}
			}

            sql.append("u.idunidade, u.nome, u.idunidadepai from unidade as u ");

            if (idContrato != 0 && idContrato != -1) {
                sql.append("join contratosunidades as c on c.idcontrato=? and u.idunidade = c.idunidade ");
                parametro.add(idContrato);
            }

            sql.append("where ");

            if (CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.ORACLE) && limite != null && limite.intValue() > 0) {
                sql.append("(ROWNUM <= ?) and ");
                parametro.add(limite);
            }

            sql.append("(u.dataFim IS NULL) ");

            if (nome != null && nome.length() > 0) {
                nome = "%" + nome.toUpperCase() + "%";
                sql.append("and (UPPER(u.nome) like UPPER(?)) ");
                parametro.add(nome);
            }

            sql.append("order by u.nome");

            if ((CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.POSTGRESQL)
                    || CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.MYSQL)) && limite != null && limite.intValue() > 0) {
                sql.append(" LIMIT ? ");
                parametro.add(limite);
            }

            final List resp = this.execSQL(sql.toString(), parametro.toArray());

            result = engine.listConvertion(this.getBean(), resp, listRetorno);
        } catch (final PersistenceException e) {
            e.printStackTrace();
            result = null;
        } catch (final Exception e) {
            e.printStackTrace();
            result = null;
        }
        return result == null || result.size() <= 0 ? new ArrayList<UnidadeDTO>() : result;
    }

    /**
	 * @author euler.ramos
	 * @param idUnidade
	 * @param idContrato
	 * @return Retorna a unidade pesquisada pelo seu id, se o idContrato está vinculado a ela
	 */
	public Collection<UnidadeDTO> findByIdEcontrato(Integer idUnidade, Integer idContrato) {
		List result;
		try {
			
			List resp = new ArrayList();
			List parametro = new ArrayList();
			List listRetorno = new ArrayList();
			
			listRetorno.add("idUnidade");
			listRetorno.add("nome");
			listRetorno.add("idUnidadePai");
			
			StringBuilder sql = new StringBuilder();
			sql.append("select ");
			sql.append("u.idunidade, u.nome, u.idunidadepai from unidade as u ");
			
			if ((idContrato!=null)&&(idContrato!=0)){
				sql.append("join contratosunidades as c on c.idcontrato=? and u.idunidade = c.idunidade ");
				parametro.add(idContrato);
			}
			
			sql.append("where ");
			
			if ((idUnidade!=null)&&(idUnidade>0)){
				sql.append("(u.idunidade=?) and ");
				parametro.add(idUnidade);
			}
			
			sql.append("(u.dataFim IS NULL)");
			
			resp = this.execSQL(sql.toString(), parametro.toArray());
			
			result = this.engine.listConvertion(getBean(), resp, listRetorno);
		} catch (PersistenceException e) {
			e.printStackTrace();
			result = null;
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		return (((result == null)||(result.size()<=0)) ? new ArrayList<UnidadeDTO>() : result);
	}

    /**
     * Recupera a lista hierárquica da unidade passada como parâmetro
     *
     * @author euler.ramos
     * @param unidadeDTO
     * @return
     * @throws Exception
     */
    public List<UnidadeDTO> recuperaHierarquiaUnidade(final UnidadeDTO unidadeDTO) throws Exception {
        final List<UnidadeDTO> hierarquiaUnidade = new ArrayList<UnidadeDTO>();
        if (unidadeDTO != null) {
            UnidadeDTO unAux = unidadeDTO;
            do {
                hierarquiaUnidade.add(0, unAux); // Desta forma ficará na ordem de hierarquia
                unAux = this.retornaUnidadePai(unAux);
            } while (unAux != null && this.naoFoiAdicionado(unAux, hierarquiaUnidade)); // Pensar como evitar looping, por causa de elementos já presentes na hierarquia (Erro
                                                                                        // de dados no banco)!!!!
        }
        return hierarquiaUnidade;
    }

    private boolean naoFoiAdicionado(final UnidadeDTO unAux, final List<UnidadeDTO> hierarquiaUnidade) {
        boolean encontrou = false;
        for (final UnidadeDTO unidadeDTO : hierarquiaUnidade) {
            if (unidadeDTO.getIdUnidade().equals(unAux.getIdUnidade())) {
                encontrou = true;
                break;
            }
        }
        return !encontrou;
    }

    /**
     * Retorna o pai da unidade passada como parâmetro
     *
     * @author euler.ramos
     * @param unidadeDTO
     * @return
     * @throws Exception
     */
    public UnidadeDTO retornaUnidadePai(final UnidadeDTO unidadeDTO) throws Exception {
        // Se for o próprio retorna o mesmo objeto
        if (unidadeDTO.getIdUnidade().equals(unidadeDTO.getIdUnidadePai())) {
            return unidadeDTO;
        } else {
            if (unidadeDTO.getIdUnidadePai() == null) {
                return null;
            } else {
                // Buscando no banco o pai da unidade
                UnidadeDTO unidadePaiDTO;
                final List<UnidadeDTO> listaPai = (List<UnidadeDTO>) this.findById(unidadeDTO.getIdUnidadePai());
                if (listaPai != null && listaPai.size() > 0) {
                    unidadePaiDTO = listaPai.get(0);
                } else {
                    unidadePaiDTO = null;
                }
                return unidadePaiDTO;
            }
        }
    }

    public String retornaNomeUnidadeByID(final Integer id) throws Exception {
        UnidadeDTO unidadeDTO = new UnidadeDTO();
        final List<UnidadeDTO> listaPai = (ArrayList<UnidadeDTO>) this.findById(id);
        if ((listaPai != null) && (listaPai.size() > 0)) {
            unidadeDTO = listaPai.get(0);
        }
        if (unidadeDTO != null) {
            return unidadeDTO.getNome();
        }
        return null;
    }

    public String obtenIDsUnidadesUsuario(UsuarioDTO usuarioLogado) throws ServiceException, Exception {
		String resultado = "";
		if ((usuarioLogado!=null)&&(usuarioLogado.getIdUsuario()!=null)&&(usuarioLogado.getIdUsuario().intValue()>0)) {
			EmpregadoDTO empregadoDTO = new EmpregadoDTO();
			try {
				EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
				empregadoDTO.setIdEmpregado(usuarioLogado.getIdEmpregado());
				empregadoDTO = (EmpregadoDTO) empregadoService.restore(empregadoDTO);
			} catch (LogicException e1) {
				e1.printStackTrace();
			} catch (ServiceException e1) {
				e1.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			Integer idUnidadeColaborador = ((empregadoDTO.getIdUnidade() != null) && (empregadoDTO.getIdUnidade().intValue() > 0)) ? empregadoDTO.getIdUnidade() : 0;

			String tipoHierarquia = ParametroUtil.getValorParametroCitSmartHashMap(br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.TIPO_HIERARQUIA_UNIDADE, "1");

			Arvore arvore = new Arvore();
			UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);
			arvore = unidadeService.obtemArvoreUnidades("", 0, idUnidadeColaborador, tipoHierarquia, 0);

			List<Integer> listaID = arvore.getListaID();
			for (Integer id : listaID) {
				if (resultado.length()>0){
					resultado +=',';
				}
				resultado += id.toString();
			}
		}
		return (resultado.length()>0)?resultado:"0";
	}

    public UnidadeDTO checkIsInContrato(Integer idUnidade, Integer idContrato) throws Exception{
    	StringBuilder sql = new StringBuilder();
    	List parametros = new ArrayList();
    	List retorno = new ArrayList();
    	
    	sql.append("SELECT u.idUnidade, u.nome, u.idUnidadePai ");
    	sql.append("FROM unidade u ");
    	sql.append("JOIN contratosunidades c ON c.idUnidade = u.idUnidade ");
    	if(idUnidade != null){
    		sql.append("AND u.idUnidade = ? ");
    		parametros.add(idUnidade);
    	}
    	if(idContrato != null){
    		sql.append("AND c.idcontrato = ? ");
    		parametros.add(idContrato);
    	}
    	
    	List resposta = execSQL(sql.toString(), parametros.toArray());
    	
    	retorno.add("idUnidade");
    	retorno.add("nome");
    	retorno.add("idUnidadePai");
    	
    	retorno = this.engine.listConvertion(getBean(), resposta, retorno);
    	
    	if(retorno != null && !retorno.isEmpty()){
    		return (UnidadeDTO) retorno.toArray()[0];
    	}
    	return null;
    	
    	
    }
}
