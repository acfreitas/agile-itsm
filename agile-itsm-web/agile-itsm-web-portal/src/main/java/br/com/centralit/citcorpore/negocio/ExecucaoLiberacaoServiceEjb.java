package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.centralit.bpm.dto.ElementoFluxoDTO;
import br.com.centralit.bpm.dto.ObjetoInstanciaFluxoDTO;
import br.com.centralit.bpm.dto.TarefaFluxoDTO;
import br.com.centralit.bpm.integracao.ElementoFluxoDao;
import br.com.centralit.bpm.integracao.ObjetoInstanciaFluxoDao;
import br.com.centralit.bpm.integracao.TarefaFluxoDao;
import br.com.centralit.citcorpore.bean.ExecucaoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.bpm.negocio.ExecucaoLiberacao;
import br.com.centralit.citcorpore.integracao.ExecucaoLiberacaoDao;
import br.com.centralit.citcorpore.integracao.RequisicaoLiberacaoDao;
import br.com.centralit.citcorpore.metainfo.bean.CamposObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.GrupoVisaoCamposNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.ObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.integracao.ObjetoNegocioDao;
import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;

import com.google.gson.Gson;

@SuppressWarnings({"unchecked", "rawtypes", "unused"})
public class ExecucaoLiberacaoServiceEjb extends CrudServiceImpl implements ExecucaoLiberacaoService {

    private ExecucaoLiberacaoDao dao;

    @Override
    protected ExecucaoLiberacaoDao getDao() {
        if (dao == null) {
            dao = new ExecucaoLiberacaoDao();
        }
        return dao;
    }

    @Override
    public List<TarefaFluxoDTO> recuperaTarefas(final String loginUsuario) throws Exception {
        return new ExecucaoLiberacao().recuperaTarefas(loginUsuario);
    }

    @Override
    public TarefaFluxoDTO recuperaTarefa(final String loginUsuario, final Integer idTarefa) throws Exception {
        TarefaFluxoDTO result = null;
        final List<TarefaFluxoDTO> lstTarefas = this.recuperaTarefas(loginUsuario);
        if (!lstTarefas.isEmpty()) {
            for (final TarefaFluxoDTO tarefaDto : lstTarefas) {
                if (tarefaDto.getIdItemTrabalho().intValue() == idTarefa.intValue()) {
                    result = tarefaDto;
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public void delegaTarefa(final String loginUsuario, final Integer idTarefa, final String usuarioDestino, final String grupoDestino) throws Exception {
        if (idTarefa == null) {
            return;
        }

        final TarefaFluxoDTO tarefaDto = this.recuperaTarefa(idTarefa);
        final ExecucaoLiberacaoDTO execucaoLiberacaoDto = this.getDao().findByIdInstanciaFluxo(tarefaDto.getIdInstancia());

        final RequisicaoLiberacaoDTO solicitacaoDto = new RequisicaoLiberacaoServiceEjb().restoreAll(execucaoLiberacaoDto.getIdRequisicaoLiberacao());
        final TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());
        try {
            tc.start();

            final ExecucaoLiberacao execucaoLiberacao = new ExecucaoLiberacao(tc);
            execucaoLiberacao.delega(loginUsuario, solicitacaoDto, idTarefa, usuarioDestino, grupoDestino);
            tc.commit();

        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        } finally {
            try {
                tc.close();
            } catch (final PersistenceException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    public TarefaFluxoDTO recuperaTarefa(final Integer idTarefa) throws Exception {
        final TarefaFluxoDao tarefaFluxoDao = new TarefaFluxoDao();
        TarefaFluxoDTO tarefaFluxoDto = new TarefaFluxoDTO();
        tarefaFluxoDto.setIdItemTrabalho(idTarefa);
        tarefaFluxoDto = (TarefaFluxoDTO) tarefaFluxoDao.restore(tarefaFluxoDto);
        final ElementoFluxoDTO elementoDto = new ElementoFluxoDao().restore(tarefaFluxoDto.getIdElemento());
        tarefaFluxoDto.setElementoFluxoDto(elementoDto);
        return tarefaFluxoDto;
    }

    @Override
    public void trataCamposTarefa(final Map<String, String> params, final Collection<CamposObjetoNegocioDTO> colCampos, final Map<String, Object> map, final String principal)
            throws Exception {
        if (colCampos == null) {
            return;
        }

        final Gson gson = new Gson();

        final ObjetoNegocioDao objetoNegocioDao = new ObjetoNegocioDao();
        final HashMap<Integer, ObjetoNegocioDTO> mapObjetos = new HashMap();

        for (final CamposObjetoNegocioDTO campoDto : colCampos) {
            final String value = params.get(campoDto.getNome());
            if (value == null) {
                return;
            }

            String nomeTabelaBD = "";
            if (campoDto.getNomeTabelaDB() != null) {
                nomeTabelaBD = campoDto.getNomeTabelaDB();
                nomeTabelaBD = campoDto.getNomeTabelaDB().toLowerCase();
            } else if (campoDto.getIdObjetoNegocio() != null) {
                ObjetoNegocioDTO objetoNegocioDto = mapObjetos.get(campoDto.getIdObjetoNegocio());
                if (objetoNegocioDto == null) {
                    objetoNegocioDto = new ObjetoNegocioDTO();
                    objetoNegocioDto.setIdObjetoNegocio(campoDto.getIdObjetoNegocio());
                    objetoNegocioDto = (ObjetoNegocioDTO) objetoNegocioDao.restore(objetoNegocioDto);
                    if (objetoNegocioDto != null) {
                        mapObjetos.put(campoDto.getIdObjetoNegocio(), objetoNegocioDto);
                    }
                }
                if (objetoNegocioDto != null) {
                    nomeTabelaBD = objetoNegocioDto.getNomeTabelaDB();
                }
            }

            final ObjetoInstanciaFluxoDTO objetoInstanciaDto = new ObjetoInstanciaFluxoDTO();
            objetoInstanciaDto.setIdObjetoNegocio(campoDto.getIdObjetoNegocio());
            objetoInstanciaDto.setObjetoPrincipal(principal);
            objetoInstanciaDto.setCampoChave(campoDto.getPk());
            objetoInstanciaDto.setNomeObjeto(campoDto.getNome().toLowerCase());
            objetoInstanciaDto.setNomeTabelaBD(nomeTabelaBD);
            objetoInstanciaDto.setNomeCampoBD(campoDto.getNomeDB());
            objetoInstanciaDto.setTipoCampoBD(campoDto.getTipoDB());
            objetoInstanciaDto.setNomeClasse(String.class.getName());
            objetoInstanciaDto.setValor(gson.toJson(value));

            map.put(objetoInstanciaDto.getNomeObjeto(), objetoInstanciaDto);
        }
    }

    @Override
    public Collection<GrupoVisaoCamposNegocioDTO> findCamposTarefa(final Integer idTarefa) throws Exception {
        final TarefaFluxoDTO tarefaDto = this.recuperaTarefa(idTarefa);
        if (tarefaDto == null) {
            return null;
        }

        Collection<GrupoVisaoCamposNegocioDTO> result = null;
        result = new ArrayList();
        final Collection<ObjetoInstanciaFluxoDTO> colCampos = new ObjetoInstanciaFluxoDao().findByIdTarefa(idTarefa);
        if (colCampos != null) {
            final Gson gson = new Gson();
            for (final ObjetoInstanciaFluxoDTO campoTarefaDto : colCampos) {
                if (campoTarefaDto.getObjetoPrincipal().equalsIgnoreCase("S") && campoTarefaDto.getCampoChave().equalsIgnoreCase("S")) {
                    final GrupoVisaoCamposNegocioDTO grupoCampoDto = new GrupoVisaoCamposNegocioDTO();
                    final CamposObjetoNegocioDTO campoDto = new CamposObjetoNegocioDTO();
                    campoDto.setIdObjetoNegocio(campoTarefaDto.getIdObjetoNegocio());
                    campoDto.setNome(campoTarefaDto.getNomeObjeto());
                    campoDto.setNomeDB(campoTarefaDto.getNomeCampoBD());
                    campoDto.setPk("S");
                    campoDto.setValue(gson.fromJson(campoTarefaDto.getValor(), String.class));
                    campoDto.setTipoDB(campoTarefaDto.getTipoCampoBD());
                    grupoCampoDto.setCamposObjetoNegocioDto(campoDto);
                    result.add(grupoCampoDto);
                }
            }
        } else {
            final ExecucaoLiberacaoDTO execucaoLiberacaoDto = this.getDao().findByIdInstanciaFluxo(tarefaDto.getIdInstancia());
            if (execucaoLiberacaoDto != null) {
                final ObjetoNegocioDTO objetoNegocioDto = new ObjetoNegocioDao().findByNomeObjetoNegocio("RequisicaoLiberacao");
                if (objetoNegocioDto == null) {
                    return null;
                }

                final GrupoVisaoCamposNegocioDTO grupoCampoDto = new GrupoVisaoCamposNegocioDTO();
                final CamposObjetoNegocioDTO campoDto = new CamposObjetoNegocioDTO();
                campoDto.setNome("IDREQUISICAOLIBERACAO");
                campoDto.setNomeDB("IDLIBERACAO");
                campoDto.setIdObjetoNegocio(objetoNegocioDto.getIdObjetoNegocio());
                campoDto.setPk("S");
                campoDto.setValue(execucaoLiberacaoDto.getIdRequisicaoLiberacao());
                grupoCampoDto.setCamposObjetoNegocioDto(campoDto);
                result.add(grupoCampoDto);
            }
        }
        return result;
    }

    @Override
    public void registraLiberacao(final RequisicaoLiberacaoDTO requisicaoLiberacaoDto, final TransactionControler tc, final Usuario usuario) throws Exception {
        final ExecucaoLiberacao execucaoLiberacao = new ExecucaoLiberacao(requisicaoLiberacaoDto, tc, usuario);
        execucaoLiberacao.inicia();
    }

    @Override
    public void executa(final UsuarioDTO usuarioDto, final TransactionControler tc, final Integer idFluxo, final Integer idTarefa, final String acaoFluxo,
            final HashMap<String, String> params, final Collection<CamposObjetoNegocioDTO> colCamposTodosPrincipal, final Collection<CamposObjetoNegocioDTO> colCamposTodosVinc)
            throws Exception {
        if (usuario == null) {
            usuario = new Usuario();
        }
        usuario.setLocale(usuarioDto.getLocale());

        final HashMap<String, Object> map = new HashMap();
        this.trataCamposTarefa(params, colCamposTodosPrincipal, map, "S");
        this.trataCamposTarefa(params, colCamposTodosVinc, map, "N");
        final Integer idSolicitacao = new Integer((String) map.get("IDREQUISICAOLIBERACAO"));
        final RequisicaoLiberacaoDTO requisicaoLiberacaoDto = new RequisicaoLiberacaoServiceEjb().restoreAll(idSolicitacao, tc);

        new ExecucaoLiberacao(tc).executa(usuarioDto.getLogin(), requisicaoLiberacaoDto, idTarefa, acaoFluxo, map);
    }

    @Override
    public void executa(final RequisicaoLiberacaoDTO requisicaoLiberacaoDto, final Integer idTarefa, final String acaoFluxo, final TransactionControler tc) throws Exception {
        if (requisicaoLiberacaoDto.getAcaoFluxo() != null) {
            requisicaoLiberacaoDto.getAcaoFluxo();
        }
        final HashMap<String, Object> objetos = new HashMap();
        final RequisicaoLiberacaoDTO liberacaoAuxDto = new RequisicaoLiberacaoServiceEjb().restoreAll(requisicaoLiberacaoDto.getIdRequisicaoLiberacao(), tc);
        new ExecucaoLiberacao(tc).executa(requisicaoLiberacaoDto.getUsuarioDto().getLogin(), liberacaoAuxDto, idTarefa, acaoFluxo, objetos);
    }

    public void direcionaAtendimento(final RequisicaoLiberacaoDTO requisicaoLiberacaoDto, final TransactionControler tc) throws Exception {
        if (requisicaoLiberacaoDto.getIdGrupoAtual() == null) {
            return;
        }

        final RequisicaoLiberacaoDTO liberacaoAuxDto = new RequisicaoLiberacaoServiceEjb().restoreAll(requisicaoLiberacaoDto.getIdRequisicaoLiberacao(), tc);

        if (liberacaoAuxDto == null) {
            throw new Exception("Problemas na recuperação da solicitação");
        }

        if (liberacaoAuxDto.getNomeGrupoAtual() == null || liberacaoAuxDto.getNomeGrupoAtual().length() == 0) {
            throw new Exception("Grupo executor não encontrado");
        }

        new ExecucaoLiberacao(liberacaoAuxDto, tc).direcionaAtendimento(requisicaoLiberacaoDto.getUsuarioDto().getLogin(), liberacaoAuxDto, liberacaoAuxDto.getNomeGrupoAtual());
    }

    public void direcionaAtendimentoSolicitante(final RequisicaoLiberacaoDTO requisicaoLiberacaoDto, final TransactionControler tc) throws Exception {
        if (requisicaoLiberacaoDto.getIdGrupoAprovador() == null) {
            return;
        } else {
            requisicaoLiberacaoDto.setIdGrupoAtual(requisicaoLiberacaoDto.getIdGrupoAprovador());
            final RequisicaoLiberacaoDao requisicaoLiberacaoDao = new RequisicaoLiberacaoDao();
            requisicaoLiberacaoDao.setTransactionControler(tc);
            requisicaoLiberacaoDao.updateNotNull(requisicaoLiberacaoDto);
        }
        final RequisicaoLiberacaoDTO liberacaoAuxDto = new RequisicaoLiberacaoServiceEjb().restoreAll(requisicaoLiberacaoDto.getIdRequisicaoLiberacao(), tc);

        if (liberacaoAuxDto == null) {
            throw new Exception("Problemas na recuperação da solicitação");
        }

        if (liberacaoAuxDto.getNomeGrupoAprovador() == null || liberacaoAuxDto.getNomeGrupoAprovador().length() == 0) {
            throw new Exception("Grupo Aprovador não encontrado");
        }

        new ExecucaoLiberacao(liberacaoAuxDto, tc)
                .direcionaAtendimento(requisicaoLiberacaoDto.getUsuarioDto().getLogin(), liberacaoAuxDto, liberacaoAuxDto.getNomeGrupoAprovador());
    }

    @Override
    public void executa(final UsuarioDTO usuarioDto, final Integer idTarefa, final String acaoFluxo) throws Exception {
        if (usuario == null) {
            usuario = new Usuario();
        }
        usuario.setLocale(usuarioDto.getLocale());

        final TarefaFluxoDTO tarefaDto = this.recuperaTarefa(idTarefa);
        if (tarefaDto == null) {
            return;
        }

        final ExecucaoLiberacaoDTO execucaoLiberacaoDto = this.getDao().findByIdInstanciaFluxo(tarefaDto.getIdInstancia());
        if (execucaoLiberacaoDto == null) {
            return;
        }

        final RequisicaoLiberacaoDTO requisicaoLiberacaoDto = new RequisicaoLiberacaoDTO();
        requisicaoLiberacaoDto.setIdRequisicaoLiberacao(execucaoLiberacaoDto.getIdRequisicaoLiberacao());
        requisicaoLiberacaoDto.setUsuarioDto(usuarioDto);
        final TransactionControlerImpl tc = new TransactionControlerImpl(this.getDao().getAliasDB());
        this.executa(requisicaoLiberacaoDto, idTarefa, acaoFluxo, tc);
        try {
            if (tc != null) {
                tc.close();
            }
        } catch (final Exception e) {}
    }

    public void suspende(final UsuarioDTO usuarioDto, final RequisicaoLiberacaoDTO requisicaoLiberacaoDto, final TransactionControler tc) throws Exception {
        if (usuario == null) {
            usuario = new Usuario();
        }
        usuario.setLocale(usuarioDto.getLocale());

        final ExecucaoLiberacao execucaoLiberacao = new ExecucaoLiberacao(requisicaoLiberacaoDto, tc, usuario);
        execucaoLiberacao.suspende(usuarioDto.getLogin());
    }

    public void encerra(final UsuarioDTO usuarioDto, final RequisicaoLiberacaoDTO requisicaoLiberacaoDto, final TransactionControler tc) throws Exception {
        if (usuario == null) {
            usuario = new Usuario();
        }
        usuario.setLocale(usuarioDto.getLocale());
        final ExecucaoLiberacao execucaoLiberacao = new ExecucaoLiberacao(requisicaoLiberacaoDto, tc);
        execucaoLiberacao.encerra();
    }

    public void reativa(final UsuarioDTO usuarioDto, final RequisicaoLiberacaoDTO requisicaoLiberacaoDto, final TransactionControler tc) throws Exception {
        if (usuario == null) {
            usuario = new Usuario();
        }
        usuario.setLocale(usuarioDto.getLocale());
        final ExecucaoLiberacao execucaoLiberacao = new ExecucaoLiberacao(requisicaoLiberacaoDto, tc);
        execucaoLiberacao.reativa(usuarioDto.getLogin());
    }

    public void reabre(final UsuarioDTO usuarioDto, final RequisicaoLiberacaoDTO requisicaoLiberacaoDto, final TransactionControler tc) throws Exception {
        if (usuario == null) {
            usuario = new Usuario();
        }
        usuario.setLocale(usuarioDto.getLocale());
        final ExecucaoLiberacao execucaoRequisicaoLiberacao = new ExecucaoLiberacao(requisicaoLiberacaoDto, null);
        execucaoRequisicaoLiberacao.reabre(usuarioDto.getLogin());
    }

}
