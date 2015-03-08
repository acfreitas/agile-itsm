package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.centralit.bpm.dto.ElementoFluxoDTO;
import br.com.centralit.bpm.dto.FluxoDTO;
import br.com.centralit.bpm.dto.ObjetoInstanciaFluxoDTO;
import br.com.centralit.bpm.dto.TarefaFluxoDTO;
import br.com.centralit.bpm.dto.TipoFluxoDTO;
import br.com.centralit.bpm.integracao.ElementoFluxoDao;
import br.com.centralit.bpm.integracao.FluxoDao;
import br.com.centralit.bpm.integracao.ObjetoInstanciaFluxoDao;
import br.com.centralit.bpm.integracao.TarefaFluxoDao;
import br.com.centralit.bpm.integracao.TipoFluxoDao;
import br.com.centralit.citcorpore.bean.CategoriaProblemaDTO;
import br.com.centralit.citcorpore.bean.ExecucaoProblemaDTO;
import br.com.centralit.citcorpore.bean.ProblemaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.bpm.negocio.ExecucaoProblema;
import br.com.centralit.citcorpore.integracao.CategoriaProblemaDAO;
import br.com.centralit.citcorpore.integracao.ExecucaoProblemaDao;
import br.com.centralit.citcorpore.metainfo.bean.CamposObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.GrupoVisaoCamposNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.ObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.integracao.ObjetoNegocioDao;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;

import com.google.gson.Gson;

@SuppressWarnings({"unchecked", "rawtypes"})
public class ExecucaoProblemaServiceEjb extends CrudServiceImpl implements ExecucaoProblemaService {

    private ExecucaoProblemaDao dao;

    @Override
    protected ExecucaoProblemaDao getDao() {
        if (dao == null) {
            dao = new ExecucaoProblemaDao();
        }
        return dao;
    }

    @Override
    public List<TarefaFluxoDTO> recuperaTarefas(final String loginUsuario) throws Exception {
        return new ExecucaoProblema().recuperaTarefas(loginUsuario);
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
        final ExecucaoProblemaDTO execucaoProblemaDTO = this.getDao().findByIdInstanciaFluxo(tarefaDto.getIdInstancia());

        final ProblemaDTO problemaDto = new ProblemaServiceEjb().restoreAll(execucaoProblemaDTO.getIdProblema());
        final TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());
        try {
            tc.start();

            final ExecucaoProblema execucaoProblema = this.getExecucaoProblema(problemaDto, tc);
            execucaoProblema.delega(loginUsuario, problemaDto, idTarefa, usuarioDestino, grupoDestino);
            tc.commit();

        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        } finally {
            try {
                tc.close();
            } catch (final Exception e) {
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
            final ExecucaoProblemaDTO execucaoProblemaDto = this.getDao().findByIdInstanciaFluxo(tarefaDto.getIdInstancia());
            if (execucaoProblemaDto != null) {
                final ObjetoNegocioDTO objetoNegocioDto = new ObjetoNegocioDao().findByNomeObjetoNegocio("Problema");
                if (objetoNegocioDto == null) {
                    return null;
                }

                final GrupoVisaoCamposNegocioDTO grupoCampoDto = new GrupoVisaoCamposNegocioDTO();
                final CamposObjetoNegocioDTO campoDto = new CamposObjetoNegocioDTO();
                campoDto.setNome("IDPROBLEMA");
                campoDto.setNomeDB("IDPROBLEMA");
                campoDto.setIdObjetoNegocio(objetoNegocioDto.getIdObjetoNegocio());
                campoDto.setPk("S");
                campoDto.setValue(execucaoProblemaDto.getIdProblema());
                grupoCampoDto.setCamposObjetoNegocioDto(campoDto);
                result.add(grupoCampoDto);
            }
        }
        return result;
    }

    public TipoFluxoDTO recuperaFluxoServico(final ProblemaDTO problemaDto) throws Exception {
        // Integer idServicoContrato = problemaDto.getIdServicoContrato();
        /*
         * if (problemaDto.getIdServicoContrato() == null || problemaDto.getIdServicoContrato().intValue() <= 0) {
         * ProblemaDTO problemaAuxDto = new ProblemaDTO();
         * problemaAuxDto.setIdProblema(problemaDto.getIdProblema());
         * problemaAuxDto = (ProblemaDTO) new ProblemaDAO().restore(problemaAuxDto);
         * idServicoContrato = problemaAuxDto.getIdServicoContrato();
         * }
         */
        TipoFluxoDTO tipoFluxoDto = null;
        new ExecucaoProblema();
        final CategoriaProblemaDAO categoriaProblemaDao = new CategoriaProblemaDAO();
        if (problemaDto.getIdCategoriaProblema() != null) {
            CategoriaProblemaDTO categoriaProblemaDto = new CategoriaProblemaDTO();
            categoriaProblemaDto.setIdCategoriaProblema(problemaDto.getIdCategoriaProblema());
            categoriaProblemaDto = (CategoriaProblemaDTO) categoriaProblemaDao.restore(categoriaProblemaDto);

            if (categoriaProblemaDto != null) {

                final FluxoDTO fluxoDto = new FluxoDao().findByTipoFluxo(categoriaProblemaDto.getIdTipoFluxo());
                if (fluxoDto != null) {
                    tipoFluxoDto = new TipoFluxoDTO();
                    tipoFluxoDto.setIdTipoFluxo(fluxoDto.getIdTipoFluxo());
                    tipoFluxoDto = (TipoFluxoDTO) new TipoFluxoDao().restore(tipoFluxoDto);
                } else {
                    final String fluxoPadrao = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.NomeFluxoPadraoProblema, null);
                    if (fluxoPadrao != null) {
                        tipoFluxoDto = new TipoFluxoDao().findByNome(fluxoPadrao);
                    }

                }
            }
        }

        if (tipoFluxoDto == null) {
            throw new Exception("O fluxo associado ao serviço não foi parametrizado");
        }
        return tipoFluxoDto;
    }

    public ExecucaoProblema getExecucaoProblema(final ProblemaDTO problemaDto, final TransactionControler tc) throws Exception {
        final TipoFluxoDTO tipoFluxoDto = this.recuperaFluxoServico(problemaDto);
        if (tipoFluxoDto.getNomeClasseFluxo() != null) {
            final ExecucaoProblema execucaoProblema = (ExecucaoProblema) Class.forName(tipoFluxoDto.getNomeClasseFluxo()).newInstance();
            execucaoProblema.setTransacao(tc);
            execucaoProblema.setObjetoNegocioDto(problemaDto);
            return execucaoProblema;
        } else {
            return new ExecucaoProblema(problemaDto, tc);
        }
    }

    @Override
    public void registraProblema(final ProblemaDTO problemaDto, final TransactionControler tc) throws Exception {
        final ExecucaoProblema execucaoProblema = new ExecucaoProblema(problemaDto, tc);
        execucaoProblema.inicia();
    }

    @Override
    public void executa(final UsuarioDTO usuarioDto, final TransactionControler tc, final Integer idFluxo, final Integer idTarefa, final String acaoFluxo,
            final HashMap<String, String> params, final Collection<CamposObjetoNegocioDTO> colCamposTodosPrincipal, final Collection<CamposObjetoNegocioDTO> colCamposTodosVinc)
            throws Exception {
        final HashMap<String, Object> map = new HashMap();
        this.trataCamposTarefa(params, colCamposTodosPrincipal, map, "S");
        this.trataCamposTarefa(params, colCamposTodosVinc, map, "N");
        final Integer idProblema = new Integer((String) map.get("IDPROBLEMA"));
        final ProblemaDTO problemaDto = new ProblemaServiceEjb().restoreAll(idProblema, tc);

        final ExecucaoProblema execucaoProblema = this.getExecucaoProblema(problemaDto, tc);
        execucaoProblema.executa(usuarioDto.getLogin(), problemaDto, idTarefa, acaoFluxo, map);
    }

    @Override
    public void executa(final UsuarioDTO usuarioDto, final Integer idTarefa, final String acaoFluxo) throws Exception {
        final TarefaFluxoDTO tarefaDto = this.recuperaTarefa(idTarefa);
        if (tarefaDto == null) {
            return;
        }

        final ExecucaoProblemaDTO execucaoProblemaDto = this.getDao().findByIdInstanciaFluxo(tarefaDto.getIdInstancia());
        if (execucaoProblemaDto == null) {
            return;
        }

        final ProblemaDTO problemaDto = new ProblemaDTO();
        problemaDto.setIdProblema(execucaoProblemaDto.getIdProblema());
        problemaDto.setIdProprietario(usuarioDto.getIdUsuario());
        problemaDto.setUsuarioDto(usuarioDto);
        final TransactionControlerImpl tc = new TransactionControlerImpl(this.getDao().getAliasDB());
        this.executa(problemaDto, idTarefa, acaoFluxo, tc);
        try {
            if (tc != null) {
                tc.close();
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cancelaTarefa(final String loginUsuario, final Integer idTarefa, final String motivo, final TransactionControler tc) throws Exception {
        final TarefaFluxoDTO tarefaDto = this.recuperaTarefa(idTarefa);
        if (tarefaDto == null) {
            return;
        }

        final ExecucaoProblemaDTO execucaoProblemaDto = this.getDao().findByIdInstanciaFluxo(tarefaDto.getIdInstancia());
        if (execucaoProblemaDto == null) {
            return;
        }

        final ProblemaDTO problemaDto = new ProblemaServiceEjb().restoreAll(execucaoProblemaDto.getIdProblema(), null);
        final ExecucaoProblema execucaoProblema = this.getExecucaoProblema(problemaDto, tc);
        execucaoProblema.cancelaTarefa(loginUsuario, problemaDto, tarefaDto, motivo);
    }

    @Override
    public void executa(final ProblemaDTO problemaDto, final Integer idTarefa, final String acaoFluxo, final TransactionControler tc) throws Exception {
        if (problemaDto.getAcaoFluxo() != null) {
            problemaDto.getAcaoFluxo();
        }
        final HashMap<String, Object> objetos = new HashMap();

        final ProblemaDTO problemaAuxDto = new ProblemaServiceEjb().restoreAll(problemaDto.getIdProblema(), tc);
        if (problemaAuxDto.getEmailContato() == null || problemaAuxDto.getEmailContato().equalsIgnoreCase("")) {
            problemaAuxDto.setEmailContato(problemaDto.getEmailContato());
            problemaAuxDto.setNomeContato(problemaDto.getNomeContato());
        }
        new ExecucaoProblema(tc).executa(problemaDto.getUsuarioDto().getLogin(), problemaAuxDto, idTarefa, acaoFluxo, objetos);
    }

    public void direcionaAtendimento(final ProblemaDTO problemaDto, final TransactionControler tc) throws Exception {
        if (problemaDto.getIdGrupoAtual() == null) {
            return;
        }

        final ProblemaDTO problemaAuxDto = new ProblemaServiceEjb().restoreAll(problemaDto.getIdProblema(), tc);

        if (problemaAuxDto == null) {
            throw new Exception("Problemas na recuperação da solicitação");
        }

        if (problemaAuxDto.getNomeGrupoAtual() == null || problemaAuxDto.getNomeGrupoAtual().length() == 0) {
            throw new Exception("Grupo executor não encontrado");
        }

        this.getExecucaoProblema(problemaAuxDto, tc).direcionaAtendimento(problemaDto.getUsuarioDto().getLogin(), problemaAuxDto, problemaAuxDto.getNomeGrupoAtual());
    }

    public void direcionaAtendimentoAutomatico(final ProblemaDTO problemaDto, final TransactionControler tc) throws Exception {
        if (problemaDto.getIdGrupoAtual() == null) {
            return;
        }

        final ProblemaDTO problemaAuxDto = new ProblemaServiceEjb().restoreAll(problemaDto.getIdProblema(), tc);

        if (problemaAuxDto != null && problemaAuxDto.getIdResponsavel() == null) {
            problemaAuxDto.setIdResponsavel(problemaAuxDto.getIdProprietario());
        }

        if (problemaAuxDto == null) {
            throw new Exception("Problemas na recuperação da solicitação");
        }

        if (problemaAuxDto.getNomeGrupoAtual() == null || problemaAuxDto.getNomeGrupoAtual().length() == 0) {
            throw new Exception("Grupo executor não encontrado");
        }

        this.getExecucaoProblema(problemaAuxDto, tc).direcionaAtendimento("admin", problemaAuxDto, problemaAuxDto.getNomeGrupoAtual());
    }

    public void encerra(final UsuarioDTO usuarioDto, final ProblemaDTO problemaDto, final TransactionControler tc) throws Exception {
        final ExecucaoProblema execucaoProblema = this.getExecucaoProblema(problemaDto, tc);
        execucaoProblema.encerra();
    }

    public void encerra(final ProblemaDTO problemaDto, final TransactionControler tc) throws Exception {
        final ExecucaoProblema execucaoProblema = this.getExecucaoProblema(problemaDto, tc);
        execucaoProblema.encerra();
    }

    public void reabre(final UsuarioDTO usuarioDto, final ProblemaDTO problemaDto, final TransactionControler tc) throws Exception {
        final ExecucaoProblema execucaoProblema = this.getExecucaoProblema(problemaDto, tc);
        execucaoProblema.reabre(usuarioDto.getLogin());
    }

    public void suspende(final UsuarioDTO usuarioDto, final ProblemaDTO problemaDto, final TransactionControler tc) throws Exception {
        final ExecucaoProblema execucaoProblema = this.getExecucaoProblema(problemaDto, tc);
        execucaoProblema.suspende(usuarioDto.getLogin());
    }

    public void reativa(final UsuarioDTO usuarioDto, final ProblemaDTO problemaDto, final TransactionControler tc) throws Exception {
        final ExecucaoProblema execucaoProblema = this.getExecucaoProblema(problemaDto, tc);
        execucaoProblema.reativa(usuarioDto.getLogin());
    }

    public void determinaPrazoLimite(final ProblemaDTO problemaDto, final Integer idCalendario, final TransactionControler tc) throws Exception {
        final ExecucaoProblema execucaoProblema = this.getExecucaoProblema(problemaDto, tc);
        execucaoProblema.determinaPrazoLimite(problemaDto, idCalendario);
    }

    @Override
    public TarefaFluxoDTO recuperaTarefa(final String loginUsuario, final ProblemaDTO problemaDto) throws Exception {
        TarefaFluxoDTO result = null;
        ProblemaDTO problemaAux = null;
        final List<TarefaFluxoDTO> lstTarefas = this.recuperaTarefas(loginUsuario);
        if (!lstTarefas.isEmpty()) {
            for (final TarefaFluxoDTO tarefaDto : lstTarefas) {
                problemaAux = (ProblemaDTO) tarefaDto.getProblemaDto();
                if (problemaAux.getIdProblema().intValue() == problemaDto.getIdProblema().intValue()) {
                    result = tarefaDto;
                    break;
                }
            }
        }
        return result;
    }

}
