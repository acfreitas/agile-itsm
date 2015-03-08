package br.com.centralit.citcorpore.bpm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.bpm.dto.GrupoBpmDTO;
import br.com.centralit.bpm.dto.UsuarioBpmDTO;
import br.com.centralit.bpm.negocio.IUsuarioGrupo;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.GrupoEmailDTO;
import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.EmpregadoDao;
import br.com.centralit.citcorpore.integracao.GrupoDao;
import br.com.centralit.citcorpore.integracao.GrupoEmailDao;
import br.com.centralit.citcorpore.integracao.GrupoEmpregadoDao;
import br.com.centralit.citcorpore.integracao.UsuarioDao;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;

public class BPMUsuarioGrupo implements IUsuarioGrupo {

    private TransactionControler transacao;

    @Override
    public UsuarioBpmDTO recuperaUsuario(final Integer idUsuario) throws Exception {
        final UsuarioDao usuarioDao = new UsuarioDao();
        this.setTransacaoDao(usuarioDao);
        UsuarioBpmDTO usuarioBpmDto = null;
        UsuarioDTO usuarioDto = new UsuarioDTO();
        usuarioDto.setIdUsuario(idUsuario);
        usuarioDto = (UsuarioDTO) usuarioDao.restore(usuarioDto);
        if (usuarioDto != null) {
            usuarioBpmDto = new UsuarioBpmDTO();
            usuarioBpmDto.setIdUsuario(usuarioDto.getIdUsuario());
            usuarioBpmDto.setLogin(usuarioDto.getLogin());
            usuarioBpmDto.setNome(usuarioDto.getNomeUsuario());
            final EmpregadoDao empregadoDao = new EmpregadoDao();
            this.setTransacaoDao(empregadoDao);
            final EmpregadoDTO empregadoDto = empregadoDao.restoreByIdEmpregado(usuarioDto.getIdEmpregado());
            if (empregadoDto != null) {
                usuarioBpmDto.setEmails(new String[] {empregadoDto.getEmail()});
            }
        }
        return usuarioBpmDto;
    }

    @Override
    public UsuarioBpmDTO recuperaUsuario(final String login) throws Exception {
        if (login == null) {
            return null;
        }
        final UsuarioDao usuarioDao = new UsuarioDao();
        this.setTransacaoDao(usuarioDao);
        UsuarioBpmDTO usuarioBpmDto = null;
        final UsuarioDTO usuarioDto = usuarioDao.restoreByLogin(login.trim());
        if (usuarioDto != null) {
            usuarioBpmDto = new UsuarioBpmDTO();
            usuarioBpmDto.setIdUsuario(usuarioDto.getIdUsuario());
            usuarioBpmDto.setLogin(login);
            usuarioBpmDto.setNome(usuarioDto.getNomeUsuario());
            final EmpregadoDao empregadoDao = new EmpregadoDao();
            this.setTransacaoDao(empregadoDao);
            final EmpregadoDTO empregadoDto = empregadoDao.restoreByIdEmpregado(usuarioDto.getIdEmpregado());
            if (empregadoDto != null) {
                usuarioBpmDto.setEmails(new String[] {empregadoDto.getEmail()});
            }
        }
        return usuarioBpmDto;
    }

    @Override
    public GrupoBpmDTO recuperaGrupo(final String siglaGrupo) throws Exception {
        if (siglaGrupo == null) {
            return null;
        }
        final GrupoDao grupoDao = new GrupoDao();
        this.setTransacaoDao(grupoDao);
        GrupoBpmDTO grupoBpmDto = null;
        final GrupoDTO grupoDto = grupoDao.restoreBySigla(siglaGrupo.trim());
        if (grupoDto != null) {
            grupoBpmDto = new GrupoBpmDTO();
            grupoBpmDto.setIdGrupo(grupoDto.getIdGrupo());
            grupoBpmDto.setSigla(siglaGrupo);
            grupoBpmDto.setEmails(this.recuperaEmailsGrupo(grupoDto.getIdGrupo()));
        }
        return grupoBpmDto;
    }

    @Override
    public boolean existeUsuario(final String login) throws Exception {
        return this.recuperaUsuario(login) != null;
    }

    @Override
    public boolean existeGrupo(final String siglaGrupo) throws Exception {
        return this.recuperaGrupo(siglaGrupo) != null;
    }

    @Override
    public List<GrupoBpmDTO> getGruposDoUsuario(final String login) throws Exception {
        if (login == null) {
            return null;
        }
        List<GrupoBpmDTO> result = null;
        final UsuarioDao usuarioDao = new UsuarioDao();
        this.setTransacaoDao(usuarioDao);
        final UsuarioDTO usuarioDto = usuarioDao.restoreByLogin(login.trim());
        if (usuarioDto != null) {
            final GrupoEmpregadoDao grupoEmpregadoDao = new GrupoEmpregadoDao();
            this.setTransacaoDao(grupoEmpregadoDao);
            try {
                final Collection<GrupoEmpregadoDTO> colGrupos = grupoEmpregadoDao.findAtivosByIdEmpregado(usuarioDto.getIdEmpregado());
                if (colGrupos != null) {
                    result = new ArrayList<>();
                    for (final GrupoEmpregadoDTO grupoDto : colGrupos) {
                        final GrupoBpmDTO grupoBpmDto = new GrupoBpmDTO();
                        grupoBpmDto.setIdGrupo(grupoDto.getIdGrupo());
                        grupoBpmDto.setSigla(grupoDto.getSigla());
                        grupoBpmDto.setEmails(this.recuperaEmailsGrupo(grupoDto.getIdGrupo()));
                        result.add(grupoBpmDto);
                    }
                }
            } catch (final Exception e) {}
        }
        return result;
    }

    @Override
    public GrupoBpmDTO recuperaGrupo(final Integer idGrupo) throws Exception {
        final GrupoDao grupoDao = new GrupoDao();
        this.setTransacaoDao(grupoDao);
        GrupoBpmDTO grupoBpmDto = null;
        GrupoDTO grupoDto = new GrupoDTO();
        grupoDto.setIdGrupo(idGrupo);
        grupoDto = (GrupoDTO) grupoDao.restore(grupoDto);
        if (grupoDto != null) {
            grupoBpmDto = new GrupoBpmDTO();
            grupoBpmDto.setIdGrupo(grupoDto.getIdGrupo());
            grupoBpmDto.setSigla(grupoDto.getSigla());
            grupoBpmDto.setEmails(this.recuperaEmailsGrupo(grupoDto.getIdGrupo()));
        }
        return grupoBpmDto;
    }

    @Override
    public boolean pertenceAoGrupo(final String login, final String siglaGrupo) throws Exception {
        boolean bResult = false;
        final List<GrupoBpmDTO> grupos = this.getGruposDoUsuario(login);
        if (grupos != null) {
            for (final GrupoBpmDTO grupoDto : grupos) {
                if (grupoDto.getSigla().equalsIgnoreCase(siglaGrupo)) {
                    bResult = true;
                    break;
                }
            }
        }
        return bResult;
    }

    private String[] recuperaEmailsGrupo(final Integer idGrupo) throws Exception {
        final GrupoEmailDao grupoEmailDao = new GrupoEmailDao();
        this.setTransacaoDao(grupoEmailDao);
        final Collection<GrupoEmailDTO> colEmails = grupoEmailDao.findByIdGrupo(idGrupo);
        if (colEmails != null) {
            int i = 0;
            final String[] emails = new String[colEmails.size()];
            for (final GrupoEmailDTO grupoEmailDto : colEmails) {
                emails[i] = grupoEmailDto.getEmail();
                i++;
            }
            return emails;
        } else {
            return null;
        }
    }

    @Override
    public void setTransacao(final TransactionControler transacao) throws Exception {
        this.transacao = transacao;
    }

    protected void setTransacaoDao(final CrudDAO dao) throws Exception {
        if (transacao != null) {
            dao.setTransactionControler(transacao);
        }
    }

}
