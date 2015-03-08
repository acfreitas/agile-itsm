package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.centralit.bpm.dto.ObjetoInstanciaFluxoDTO;
import br.com.centralit.bpm.dto.TarefaFluxoDTO;
import br.com.centralit.bpm.integracao.ObjetoInstanciaFluxoDao;
import br.com.centralit.bpm.integracao.TarefaFluxoDao;
import br.com.centralit.citcorpore.bean.ExecucaoMudancaDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.bpm.negocio.ExecucaoMudanca;
import br.com.centralit.citcorpore.integracao.ExecucaoMudancaDao;
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
public class ExecucaoMudancaServiceEjb extends CrudServiceImpl implements ExecucaoMudancaService {

    private ExecucaoMudancaDao dao;

    @Override
    protected ExecucaoMudancaDao getDao() {
        if (dao == null) {
            dao = new ExecucaoMudancaDao();
        }
        return dao;
    }

    @Override
    public List<TarefaFluxoDTO> recuperaTarefas(final String loginUsuario) throws Exception {
        return new ExecucaoMudanca().recuperaTarefas(loginUsuario);
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
        final ExecucaoMudancaDTO execucaoMudancaDto = this.getDao().findByIdInstanciaFluxo(tarefaDto.getIdInstancia());

        final RequisicaoMudancaDTO solicitacaoDto = new RequisicaoMudancaServiceEjb().restoreAll(execucaoMudancaDto.getIdRequisicaoMudanca());
        final TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());
        try {
            tc.start();

            final ExecucaoMudanca execucaoSolicitacao = new ExecucaoMudanca(tc);
            execucaoSolicitacao.delega(loginUsuario, solicitacaoDto, idTarefa, usuarioDestino, grupoDestino);
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
        final TarefaFluxoDTO tarefaFluxoDto = new TarefaFluxoDTO();
        tarefaFluxoDto.setIdItemTrabalho(idTarefa);
        return (TarefaFluxoDTO) tarefaFluxoDao.restore(tarefaFluxoDto);
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
            final ExecucaoMudancaDTO execucaoMudancaDto = this.getDao().findByIdInstanciaFluxo(tarefaDto.getIdInstancia());
            if (execucaoMudancaDto != null) {
                final ObjetoNegocioDTO objetoNegocioDto = new ObjetoNegocioDao().findByNomeObjetoNegocio("RequisicaoMudanca");
                if (objetoNegocioDto == null) {
                    return null;
                }

                final GrupoVisaoCamposNegocioDTO grupoCampoDto = new GrupoVisaoCamposNegocioDTO();
                final CamposObjetoNegocioDTO campoDto = new CamposObjetoNegocioDTO();
                campoDto.setNome("IDREQUISICAOMUDANCA");
                campoDto.setNomeDB("IDREQUISICAOMUDANCA");
                campoDto.setIdObjetoNegocio(objetoNegocioDto.getIdObjetoNegocio());
                campoDto.setPk("S");
                campoDto.setValue(execucaoMudancaDto.getIdRequisicaoMudanca());
                grupoCampoDto.setCamposObjetoNegocioDto(campoDto);
                result.add(grupoCampoDto);
            }
        }
        return result;
    }

    @Override
    public void registraMudanca(final RequisicaoMudancaDTO requisicaoMudancaDto, final TransactionControler tc, final Usuario usuario) throws Exception {
        final ExecucaoMudanca execucaoMudanca = new ExecucaoMudanca(requisicaoMudancaDto, tc, usuario);
        execucaoMudanca.inicia();
    }

    public void registraMudanca(final RequisicaoMudancaDTO requisicaoMudancaDto, final TransactionControler tc, final UsuarioDTO usuarioDto) throws Exception {
        if (usuario == null) {
            usuario = new Usuario();
        }
        usuario.setLocale(usuarioDto.getLocale());
        final ExecucaoMudanca execucaoMudanca = new ExecucaoMudanca(requisicaoMudancaDto, tc, usuario);
        execucaoMudanca.inicia();
    }

    @Override
    public void executa(final UsuarioDTO usuarioDto, final TransactionControler tc, final Integer idFluxo, final Integer idTarefa, final String acaoFluxo,
            final HashMap<String, String> params, final Collection<CamposObjetoNegocioDTO> colCamposTodosPrincipal, final Collection<CamposObjetoNegocioDTO> colCamposTodosVinc)
            throws Exception {
        final HashMap<String, Object> map = new HashMap();
        this.trataCamposTarefa(params, colCamposTodosPrincipal, map, "S");
        this.trataCamposTarefa(params, colCamposTodosVinc, map, "N");
        final Integer idSolicitacao = new Integer((String) map.get("IDREQUISICAOMUDANCA"));
        final RequisicaoMudancaDTO requisicaoMudancaDto = new RequisicaoMudancaServiceEjb().restoreAll(idSolicitacao, tc);

        new ExecucaoMudanca(tc).executa(usuarioDto.getLogin(), requisicaoMudancaDto, idTarefa, acaoFluxo, map);
    }

    @Override
    public void executa(final RequisicaoMudancaDTO requisicaoMudancaDto, final Integer idTarefa, final String acaoFluxo, final TransactionControler tc) throws Exception {
        if (requisicaoMudancaDto.getAcaoFluxo() != null) {
            requisicaoMudancaDto.getAcaoFluxo();
        }
        final HashMap<String, Object> objetos = new HashMap();
        final RequisicaoMudancaDTO mudancaAuxDto = new RequisicaoMudancaServiceEjb().restoreAll(requisicaoMudancaDto.getIdRequisicaoMudanca(), tc);
        new ExecucaoMudanca(tc).executa(requisicaoMudancaDto.getUsuarioDto().getLogin(), mudancaAuxDto, idTarefa, acaoFluxo, objetos);
    }

    public void direcionaAtendimento(final RequisicaoMudancaDTO requisicaoMudancaDto, final TransactionControler tc) throws Exception {
        if (requisicaoMudancaDto.getIdGrupoAtual() == null) {
            return;
        }

        final RequisicaoMudancaDTO mudancaAuxDto = new RequisicaoMudancaServiceEjb().restoreAll(requisicaoMudancaDto.getIdRequisicaoMudanca(), tc);

        if (mudancaAuxDto == null) {
            throw new Exception(this.i18nMessage("requisicaoMudanca.problemaRecuperacao"));
        }

        if (mudancaAuxDto.getNomeGrupoAtual() == null || mudancaAuxDto.getNomeGrupoAtual().length() == 0) {
            throw new Exception(this.i18nMessage("requisicaoMudanca.grupoNaoEncontrado"));
        }

        new ExecucaoMudanca(mudancaAuxDto, tc, usuario).direcionaAtendimento(requisicaoMudancaDto.getUsuarioDto().getLogin(), mudancaAuxDto, mudancaAuxDto.getNomeGrupoAtual());
    }

    public void direcionaAtendimentoAutomatico(final RequisicaoMudancaDTO requisicaoMudancaDto, final TransactionControler tc) throws Exception {
        if (requisicaoMudancaDto.getIdGrupoAtual() == null) {
            return;
        }

        final RequisicaoMudancaDTO mudancaAuxDto = new RequisicaoMudancaServiceEjb().restoreAll(requisicaoMudancaDto.getIdRequisicaoMudanca(), tc);

        if (mudancaAuxDto != null && mudancaAuxDto.getIdResponsavel() == null) {
            mudancaAuxDto.setIdResponsavel(mudancaAuxDto.getIdProprietario());
        }

        if (mudancaAuxDto == null) {
            throw new Exception(this.i18nMessage("requisicaoMudanca.problemaRecuperacao"));
        }

        if (mudancaAuxDto.getNomeGrupoAtual() == null || mudancaAuxDto.getNomeGrupoAtual().length() == 0) {
            throw new Exception(this.i18nMessage("requisicaoMudanca.grupoNaoEncontrado"));
        }

        new ExecucaoMudanca(mudancaAuxDto, tc, usuario).direcionaAtendimento("admin", mudancaAuxDto, mudancaAuxDto.getNomeGrupoAtual());
    }

    @Override
    public void executa(final UsuarioDTO usuarioDto, final Integer idTarefa, final String acaoFluxo) throws Exception {
        final TarefaFluxoDTO tarefaDto = this.recuperaTarefa(idTarefa);
        if (tarefaDto == null) {
            return;
        }

        final ExecucaoMudancaDTO execucaoMudancaDto = this.getDao().findByIdInstanciaFluxo(tarefaDto.getIdInstancia());
        if (execucaoMudancaDto == null) {
            return;
        }

        final RequisicaoMudancaDTO requisicaoMudancaDto = new RequisicaoMudancaDTO();
        requisicaoMudancaDto.setIdRequisicaoMudanca(execucaoMudancaDto.getIdRequisicaoMudanca());
        requisicaoMudancaDto.setUsuarioDto(usuarioDto);
        final TransactionControlerImpl tc = new TransactionControlerImpl(this.getDao().getAliasDB());
        this.executa(requisicaoMudancaDto, idTarefa, acaoFluxo, tc);
        try {
            if (tc != null) {
                tc.close();
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public void suspende(final UsuarioDTO usuarioDto, final RequisicaoMudancaDTO requisicaoMudancaDto, final TransactionControler tc) throws Exception {
        if (usuario == null) {
            usuario = new Usuario();
        }
        usuario.setLocale(usuarioDto.getLocale());
        final ExecucaoMudanca execucaoMudanca = new ExecucaoMudanca(requisicaoMudancaDto, tc, usuario);
        execucaoMudanca.suspende(usuarioDto.getLogin());
    }

    public void encerra(final UsuarioDTO usuarioDto, final RequisicaoMudancaDTO requisicaoMudancaDto, final TransactionControler tc) throws Exception {
        if (usuario == null) {
            usuario = new Usuario();
        }
        usuario.setLocale(usuarioDto.getLocale());
        final ExecucaoMudanca execucaoMudanca = new ExecucaoMudanca(requisicaoMudancaDto, tc, usuario);
        execucaoMudanca.encerra();
    }

    public void reativa(final UsuarioDTO usuarioDto, final RequisicaoMudancaDTO requisicaoMudancaDto, final TransactionControler tc) throws Exception {
        if (usuario == null) {
            usuario = new Usuario();
        }
        usuario.setLocale(usuarioDto.getLocale());
        final ExecucaoMudanca execucaoMudanca = new ExecucaoMudanca(requisicaoMudancaDto, tc, usuario);
        execucaoMudanca.reativa(usuarioDto.getLogin());
    }

}
