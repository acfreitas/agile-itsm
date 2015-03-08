package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.bpm.dto.FluxoDTO;
import br.com.centralit.bpm.dto.PermissoesFluxoDTO;
import br.com.centralit.bpm.integracao.FluxoDao;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.PermissoesFluxoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.Reflexao;

public class PermissoesFluxoServiceEjb extends CrudServiceImpl implements PermissoesFluxoService {

    private PermissoesFluxoDao dao;

    @Override
    protected PermissoesFluxoDao getDao() {
        if (dao == null) {
            dao = new PermissoesFluxoDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdTipoFluxo(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdTipoFluxo(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdTipoFluxo(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdTipoFluxo(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdGrupo(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdGrupo(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdGrupo(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdGrupo(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection<FluxoDTO> findFluxosByUsuario(final UsuarioDTO usuarioDto) throws Exception {
        final Collection<FluxoDTO> result = new ArrayList<>();
        if (usuarioDto.getColGrupos() != null) {
            for (final GrupoDTO grupoDto : usuarioDto.getColGrupos()) {
                final Collection<PermissoesFluxoDTO> colAux = this.findByIdGrupo(grupoDto.getIdGrupo());
                if (colAux != null) {
                    final FluxoDao fluxoDao = new FluxoDao();
                    for (final PermissoesFluxoDTO permissoesFluxoDto : colAux) {
                        final FluxoDTO fluxoDto = fluxoDao.findByTipoFluxo(permissoesFluxoDto.getIdTipoFluxo());
                        if (fluxoDto != null) {
                            Reflexao.copyPropertyValues(permissoesFluxoDto, fluxoDto);
                            fluxoDto.setConteudoXml(null);
                            result.add(fluxoDto);
                        }
                    }
                }
            }
        }
        return result;
    }

    @Override
    public PermissoesFluxoDTO findByUsuarioAndFluxo(final UsuarioDTO usuarioDto, final FluxoDTO fluxoDto) throws Exception {
        PermissoesFluxoDTO permissoesDto = null;
        if (usuarioDto.getColGrupos() != null && fluxoDto != null) {
            String criar = "N";
            String executar = "N";
            String delegar = "N";
            String suspender = "N";
            String reativar = "N";
            String alterarSLA = "N";
            String reabrir = "N";
            for (final GrupoDTO grupoDto : usuarioDto.getColGrupos()) {

                // TODO
                final Collection<PermissoesFluxoDTO> colPermissoes = this.findByIdGrupo(grupoDto.getIdGrupo());

                if (colPermissoes != null) {

                    final FluxoDao fluxoDao = new FluxoDao();

                    for (final PermissoesFluxoDTO permissoesAuxDto : colPermissoes) {

                        // TODO
                        final FluxoDTO fluxoAuxDto = fluxoDao.findByTipoFluxo(permissoesAuxDto.getIdTipoFluxo());

                        if (fluxoAuxDto != null && fluxoAuxDto.getIdFluxo().intValue() == fluxoDto.getIdFluxo().intValue()) {
                            if (permissoesDto == null) {
                                permissoesDto = permissoesAuxDto;
                            }

                            if (permissoesAuxDto.getCriar() != null && permissoesAuxDto.getCriar().equalsIgnoreCase("S")) {
                                criar = "S";
                            }
                            if (permissoesAuxDto.getExecutar() != null && permissoesAuxDto.getExecutar().equalsIgnoreCase("S")) {
                                executar = "S";
                            }
                            if (permissoesAuxDto.getDelegar() != null && permissoesAuxDto.getDelegar().equalsIgnoreCase("S")) {
                                delegar = "S";
                            }
                            if (permissoesAuxDto.getSuspender() != null && permissoesAuxDto.getSuspender().equalsIgnoreCase("S")) {
                                suspender = "S";
                            }
                            if (permissoesAuxDto.getReativar() != null && permissoesAuxDto.getReativar().equalsIgnoreCase("S")) {
                                reativar = "S";
                            }
                            if (permissoesAuxDto.getSuspender() != null && permissoesAuxDto.getSuspender().equalsIgnoreCase("S")) {
                                suspender = "S";
                            }
                            if (permissoesAuxDto.getAlterarSLA() != null && permissoesAuxDto.getAlterarSLA().equalsIgnoreCase("S")) {
                                alterarSLA = "S";
                            }
                            if (permissoesAuxDto.getReabrir() != null && permissoesAuxDto.getReabrir().equalsIgnoreCase("S")) {
                                reabrir = "S";
                            }
                        }
                    }
                }
            }
            if (permissoesDto != null) { // Retorna as permissões para o fluxo, independentemente do grupo
                permissoesDto.setIdGrupo(null);
                permissoesDto.setCriar(criar);
                permissoesDto.setExecutar(executar);
                permissoesDto.setDelegar(delegar);
                permissoesDto.setSuspender(suspender);
                permissoesDto.setReativar(reativar);
                permissoesDto.setSuspender(suspender);
                permissoesDto.setAlterarSLA(alterarSLA);
                permissoesDto.setReabrir(reabrir);
            }

        }
        return permissoesDto;
    }

    @Override
    public PermissoesFluxoDTO findByIdFluxoAndIdUsuario(final Integer idUsuario, final Integer idItemtrabalho) {

        final PermissoesFluxoDTO permissoesDto = new PermissoesFluxoDTO();
        permissoesDto.setIdGrupo(null);
        permissoesDto.setCriar("N");
        permissoesDto.setExecutar("N");
        permissoesDto.setDelegar("N");
        permissoesDto.setSuspender("N");
        permissoesDto.setReativar("N");
        permissoesDto.setSuspender("N");
        permissoesDto.setAlterarSLA("N");
        permissoesDto.setReabrir("N");

        try {
            final List<PermissoesFluxoDTO> listPermissoes = this.getDao().findByIdFluxoAndIdUsuario(idUsuario, idItemtrabalho);
            for (final PermissoesFluxoDTO permissoesAuxDto : listPermissoes) {

                if (permissoesAuxDto.getCriar() != null && permissoesAuxDto.getCriar().equalsIgnoreCase("S")) {
                    permissoesDto.setCriar("S");
                }

                if (permissoesAuxDto.getExecutar() != null && permissoesAuxDto.getExecutar().equalsIgnoreCase("S")) {
                    permissoesDto.setExecutar("S");
                }

                if (permissoesAuxDto.getDelegar() != null && permissoesAuxDto.getDelegar().equalsIgnoreCase("S")) {
                    permissoesDto.setDelegar("S");
                }

                if (permissoesAuxDto.getSuspender() != null && permissoesAuxDto.getSuspender().equalsIgnoreCase("S")) {
                    permissoesDto.setSuspender("S");
                }

                if (permissoesAuxDto.getReativar() != null && permissoesAuxDto.getReativar().equalsIgnoreCase("S")) {
                    permissoesDto.setReativar("S");
                }

                if (permissoesAuxDto.getAlterarSLA() != null && permissoesAuxDto.getAlterarSLA().equalsIgnoreCase("S")) {
                    permissoesDto.setAlterarSLA("S");
                }

                if (permissoesAuxDto.getReabrir() != null && permissoesAuxDto.getReabrir().equalsIgnoreCase("S")) {
                    permissoesDto.setReabrir("S");
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }

        return permissoesDto;
    }

    @Override
    public boolean permissaoGrupoExecutor(final Integer idTipoMudanca, final Integer idGrupoExecutor) throws Exception {
        return this.getDao().permissaoGrupoExecutor(idTipoMudanca, idGrupoExecutor);
    }

    @Override
    public boolean permissaoGrupoExecutorLiberacao(final Integer idTipoMudanca, final Integer idGrupoExecutor) throws Exception {
        return this.getDao().permissaoGrupoExecutorLiberacao(idTipoMudanca, idGrupoExecutor);
    }

    @Override
    public boolean permissaoGrupoExecutorProblema(final Integer idCategoriaProblema, final Integer idGrupoExecutor) throws Exception {
        return this.getDao().permissaoGrupoExecutorProblema(idCategoriaProblema, idGrupoExecutor);
    }

    @Override
    public boolean permissaoGrupoExecutorLiberacaoServico(final Integer idCategoriaProblema, final Integer idGrupoExecutor) throws Exception {
        return this.getDao().permissaoGrupoExecutorLiberacaoServico(idCategoriaProblema, idGrupoExecutor);
    }

}
