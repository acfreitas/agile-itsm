package br.com.centralit.bpm.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.bpm.dto.GrupoBpmDTO;
import br.com.centralit.bpm.dto.TarefaFluxoDTO;
import br.com.centralit.bpm.util.Enumerados.SituacaoItemTrabalho;
import br.com.centralit.bpm.util.Enumerados.TipoAtribuicao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;

public class TarefaFluxoDao extends ItemTrabalhoFluxoDao {

    @Override
    public Class<?> getBean() {
        return TarefaFluxoDTO.class;
    }

    public Collection<TarefaFluxoDTO> findByIdResponsavel(final Integer idResponsavel) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        condicao.add(new Condition("idResponsavelAtual", "=", idResponsavel));
        condicao.add(new Condition("situacao", "<>", SituacaoItemTrabalho.Executado.name()));
        return super.findByCondition(condicao, null);
    }

    @Override
    public void updateNotNull(final IDto obj) throws PersistenceException {
        super.updateNotNull(obj);
    }

    /**
     * Retorna as Tarefas do Fluxo disponíveis para o Usuário logado de acordo com o seu ID ou os Grupos em que está inserido.
     *
     * Alteração realizada em 15.01.2015 para as tarefas do tipo Delegação fossem retornadas caso não haja nenhuma do tipo Automática ou Acompanhamento.
     *
     * @param idUsuario
     *            - Identificador único do Usuário.
     * @param listGrupoBpmDTO
     *            - Lista de Grupos do Usuário.
     * @param idTarefa
     *            - Identificador único da tarefa.
     * @return List<TarefaFluxoDTO>
     * @throws PersistenceException
     * @author valdoilo.damasceno
     *
     */
    public List<TarefaFluxoDTO> findDisponiveisByIdUsuarioAndGruposBpm(final Integer idUsuario, final Collection<GrupoBpmDTO> listGrupoBpmDTO, final Integer idTarefa)
            throws PersistenceException {
        final List<Object> parametros = new ArrayList<>();

        final StringBuilder sql = new StringBuilder();
        sql.append("SELECT DISTINCT i.iditemtrabalho, ");
        sql.append("                i.idinstancia, ");
        sql.append("                i.idelemento, ");
        sql.append("                i.idresponsavelatual, ");
        sql.append("                i.datahoracriacao, ");
        sql.append("                i.datahorainicio, ");
        sql.append("                i.datahorafinalizacao, ");
        sql.append("                i.datahoraexecucao, ");
        sql.append("                i.situacao, ");
        sql.append("                a.tipo, ");
        sql.append("                instancia.idfluxo ");
        sql.append("FROM   bpm_atribuicaofluxo a ");
        sql.append("       INNER JOIN bpm_itemtrabalhofluxo i ");
        sql.append("               ON a.iditemtrabalho = i.iditemtrabalho ");
        sql.append("       INNER JOIN bpm_instanciafluxo instancia ");
        sql.append("               ON i.idinstancia = instancia.idinstancia ");
        sql.append("WHERE  i.situacao NOT IN ( ?, ? ) ");
        parametros.add(SituacaoItemTrabalho.Executado.name());
        parametros.add(SituacaoItemTrabalho.Cancelado.name());

        /* Incluido por Carlos Santos em 06.11.2013 */
        if (idTarefa != null) {
            sql.append(" AND i.idItemTrabalho = ? ");
            parametros.add(idTarefa);
        }

        sql.append("       AND ( a.idusuario = ?");
        parametros.add(idUsuario);

        if (listGrupoBpmDTO != null && !listGrupoBpmDTO.isEmpty()) {
            sql.append("              OR a.idgrupo IN ( ");

            int i = 0;
            for (final GrupoBpmDTO grupoBpmDto : listGrupoBpmDTO) {
                if (i > 0) {
                    sql.append(",");
                }
                sql.append("?");
                parametros.add(grupoBpmDto.getIdGrupo());
                i++;
            }
            sql.append(" ) ");
        }

        sql.append("              OR i.idresponsavelatual = ? ) ");
        parametros.add(idUsuario);
        sql.append("       AND a.tipo = ? ");
        parametros.add(TipoAtribuicao.Automatica.name());
        sql.append(" UNION ALL ");
        sql.append("SELECT DISTINCT i.iditemtrabalho, ");
        sql.append("                                  i.idinstancia, ");
        sql.append("                                  i.idelemento, ");
        sql.append("                                  i.idresponsavelatual, ");
        sql.append("                                  i.datahoracriacao, ");
        sql.append("                                  i.datahorainicio, ");
        sql.append("                                  i.datahorafinalizacao, ");
        sql.append("                                  i.datahoraexecucao, ");
        sql.append("                                  i.situacao, ");
        sql.append("                                  a.tipo, ");
        sql.append("                                  instancia.idfluxo ");
        sql.append("                  FROM   bpm_atribuicaofluxo a ");
        sql.append("                         INNER JOIN bpm_itemtrabalhofluxo i ");
        sql.append("                                 ON a.iditemtrabalho = i.iditemtrabalho ");
        sql.append("                         INNER JOIN bpm_instanciafluxo instancia ");
        sql.append("                                 ON i.idinstancia = instancia.idinstancia ");
        sql.append("                  WHERE  i.situacao NOT IN ( ?, ? ) ");
        parametros.add(SituacaoItemTrabalho.Executado.name());
        parametros.add(SituacaoItemTrabalho.Cancelado.name());

        /* Incluido por Carlos Santos em 06.11.2013 */
        if (idTarefa != null) {
            sql.append(" AND i.idItemTrabalho = ? ");
            parametros.add(idTarefa);
        }

        sql.append("                         AND ( a.idusuario = ? ");
        parametros.add(idUsuario);

        if (listGrupoBpmDTO != null && !listGrupoBpmDTO.isEmpty()) {
            sql.append("              OR a.idgrupo IN ( ");

            int i = 0;
            for (final GrupoBpmDTO grupoBpmDto : listGrupoBpmDTO) {
                if (i > 0) {
                    sql.append(",");
                }
                sql.append("?");
                parametros.add(grupoBpmDto.getIdGrupo());
                i++;
            }
            sql.append(" ) ");
        }

        sql.append("                                OR i.idresponsavelatual = ? ) ");
        parametros.add(idUsuario);
        sql.append("                         AND a.tipo = ? ");
        parametros.add(TipoAtribuicao.Acompanhamento.name());
        sql.append("                         AND a.iditemtrabalho NOT IN (SELECT i.iditemtrabalho ");
        sql.append("                                                      FROM ");
        sql.append("                             bpm_atribuicaofluxo a ");
        sql.append("                             INNER JOIN bpm_itemtrabalhofluxo i ");
        sql.append("                                     ON a.iditemtrabalho = ");
        sql.append("                                        i.iditemtrabalho ");
        sql.append("                             INNER JOIN bpm_instanciafluxo ");
        sql.append("                                        instancia ");
        sql.append("                                     ON i.idinstancia = ");
        sql.append("                                        instancia.idinstancia ");
        sql.append("                                                      WHERE ");
        sql.append("                             i.situacao NOT IN ( ?, ? ) ");
        parametros.add(SituacaoItemTrabalho.Executado.name());
        parametros.add(SituacaoItemTrabalho.Cancelado.name());

        /* Incluido por Carlos Santos em 06.11.2013 */
        if (idTarefa != null) {
            sql.append(" AND i.idItemTrabalho = ? ");
            parametros.add(idTarefa);
        }

        sql.append("                             AND ( a.idusuario = ? ");
        parametros.add(idUsuario);

        if (listGrupoBpmDTO != null && !listGrupoBpmDTO.isEmpty()) {
            sql.append("              OR a.idgrupo IN ( ");

            int i = 0;
            for (final GrupoBpmDTO grupoBpmDto : listGrupoBpmDTO) {
                if (i > 0) {
                    sql.append(",");
                }
                sql.append("?");
                parametros.add(grupoBpmDto.getIdGrupo());
                i++;
            }
            sql.append(" ) ");
        }
        sql.append("                                    OR i.idresponsavelatual = ? ");
        parametros.add(idUsuario);
        sql.append("                                 ) ");
        sql.append("                             AND a.tipo = ? ) ");
        parametros.add(TipoAtribuicao.Automatica.name());
        sql.append("                  UNION ALL ");
        sql.append("                  SELECT DISTINCT i.iditemtrabalho, ");
        sql.append("                                  i.idinstancia, ");
        sql.append("                                  i.idelemento, ");
        sql.append("                                  i.idresponsavelatual, ");
        sql.append("                                  i.datahoracriacao, ");
        sql.append("                                  i.datahorainicio, ");
        sql.append("                                  i.datahorafinalizacao, ");
        sql.append("                                  i.datahoraexecucao, ");
        sql.append("                                  i.situacao, ");
        sql.append("                                  a.tipo, ");
        sql.append("                                  instancia.idfluxo ");
        sql.append("                  FROM   bpm_atribuicaofluxo a ");
        sql.append("                         INNER JOIN bpm_itemtrabalhofluxo i ");
        sql.append("                                 ON a.iditemtrabalho = i.iditemtrabalho ");
        sql.append("                         INNER JOIN bpm_instanciafluxo instancia ");
        sql.append("                                 ON i.idinstancia = instancia.idinstancia ");
        sql.append("                  WHERE  i.situacao NOT IN ( ?, ? ) ");
        parametros.add(SituacaoItemTrabalho.Executado.name());
        parametros.add(SituacaoItemTrabalho.Cancelado.name());

        /* Incluido por Carlos Santos em 06.11.2013 */
        if (idTarefa != null) {
            sql.append(" AND i.idItemTrabalho = ? ");
            parametros.add(idTarefa);
        }

        sql.append("                         AND ( a.idusuario = ? ");
        parametros.add(idUsuario);

        if (listGrupoBpmDTO != null && !listGrupoBpmDTO.isEmpty()) {
            sql.append("              OR a.idgrupo IN ( ");

            int i = 0;
            for (final GrupoBpmDTO grupoBpmDto : listGrupoBpmDTO) {
                if (i > 0) {
                    sql.append(",");
                }
                sql.append("?");
                parametros.add(grupoBpmDto.getIdGrupo());
                i++;
            }
            sql.append(" ) ");
        }

        sql.append("                                OR i.idresponsavelatual = ? ) ");
        parametros.add(idUsuario);
        sql.append("                         AND a.tipo = ? ");
        parametros.add(TipoAtribuicao.Delegacao.name());
        sql.append("                         AND a.iditemtrabalho NOT IN (SELECT i.iditemtrabalho ");
        sql.append("                                                      FROM ");
        sql.append("                             bpm_atribuicaofluxo a ");
        sql.append("                             INNER JOIN bpm_itemtrabalhofluxo i ");
        sql.append("                                     ON a.iditemtrabalho = ");
        sql.append("                                        i.iditemtrabalho ");
        sql.append("                             INNER JOIN bpm_instanciafluxo ");
        sql.append("                                        instancia ");
        sql.append("                                     ON i.idinstancia = ");
        sql.append("                                        instancia.idinstancia ");
        sql.append("                                                      WHERE ");
        sql.append("                             i.situacao NOT IN ( ?, ? ) ");
        parametros.add(SituacaoItemTrabalho.Executado.name());
        parametros.add(SituacaoItemTrabalho.Cancelado.name());

        /* Incluido por Carlos Santos em 06.11.2013 */
        if (idTarefa != null) {
            sql.append(" AND i.idItemTrabalho = ? ");
            parametros.add(idTarefa);
        }

        sql.append("                             AND ( a.idusuario = ? ");
        parametros.add(idUsuario);

        if (listGrupoBpmDTO != null && !listGrupoBpmDTO.isEmpty()) {
            sql.append("              OR a.idgrupo IN ( ");

            int i = 0;
            for (final GrupoBpmDTO grupoBpmDto : listGrupoBpmDTO) {
                if (i > 0) {
                    sql.append(",");
                }
                sql.append("?");
                parametros.add(grupoBpmDto.getIdGrupo());
                i++;
            }
            sql.append(" ) ");
        }

        sql.append("                                    OR i.idresponsavelatual = ? ");
        parametros.add(idUsuario);
        sql.append("                                 ) ");
        sql.append("                             AND ( a.tipo = ? OR a.tipo = ? ) ");
        parametros.add(TipoAtribuicao.Automatica.name());
        parametros.add(TipoAtribuicao.Acompanhamento.name());
        sql.append("                                                     )");

        final List<?> lista = this.execSQL(sql.toString(), parametros.toArray());

        final List<String> listRetorno = new ArrayList(this.getListNamesFieldClass());

        listRetorno.add("tipoAtribuicao");
        listRetorno.add("idFluxo");

        return engine.listConvertion(this.getBean(), lista, listRetorno);
    }

    /**
     * @param idUsuario
     * @param listGrupoBpmDTO
     * @param idTarefa
     * @return
     * @throws PersistenceException
     * @author Thiago.borges
     */
    public List<TarefaFluxoDTO> findDisponiveisByIdTarefaEstado(final Integer idSolicictacaoServico, final String estado) throws PersistenceException {
        final List<Object> parametros = new ArrayList<>();
        final List<String> fields = new ArrayList<>();

        final StringBuilder sql = new StringBuilder();
        sql.append(" select i.iditemtrabalho from  bpm_itemtrabalhofluxo i ");
        sql.append(" inner join execucaosolicitacao ex on ex.idinstanciafluxo = i.idinstancia ");
        sql.append(" inner join bpm_elementofluxo bef on bef.idelemento = i.idelemento ");
        sql.append(" where ex.idsolicitacaoservico = ? and (i.situacao = 'disponivel' or i.situacao = 'Criado') ");
        sql.append(" and bef.nome = ? ");

        parametros.add(idSolicictacaoServico);
        parametros.add(estado);

        final List<?> list = this.execSQL(sql.toString(), parametros.toArray());

        fields.add("idItemTrabalho");
        final List<TarefaFluxoDTO> listTarefaFluxoDto = this.listConvertion(TarefaFluxoDTO.class, list, fields);

        final List<TarefaFluxoDTO> listTarefaFluxoAux = new ArrayList<TarefaFluxoDTO>();
        if (listTarefaFluxoDto != null && !listTarefaFluxoDto.isEmpty()) {
            for (final TarefaFluxoDTO dto : listTarefaFluxoDto) {
                listTarefaFluxoAux.add((TarefaFluxoDTO) this.restore(dto));
            }
        }

        return listTarefaFluxoAux;
    }

}
